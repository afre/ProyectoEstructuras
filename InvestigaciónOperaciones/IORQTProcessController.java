/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.File;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JRadioButtonMenuItem;
/*     */ import javax.swing.JScrollPane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORQTProcessController
/*     */   extends IORProcessController
/*     */ {
/*     */   private JRadioButtonMenuItem mi_jackson_network;
/*     */   private JRadioButtonMenuItem mi_waiting_line;
/*  36 */   public ORQTSolver solver = new ORQTSolver();
/*     */   
/*  38 */   public IORQTProcessController.QTData data = new IORQTProcessController.QTData();
/*  39 */   private IORQTJacksonPanel queuepanel = null;
/*  40 */   private IORWaitingLinePane waitLinePane = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORQTProcessController(IORTutorial t)
/*     */   {
/*  49 */     super(t);
/*  50 */     initializeAreaHelpPanel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeAreaHelpPanel()
/*     */   {
/*  57 */     String str = "\nThe current area is \"Queueing Theory.\" You can select ";
/*     */     
/*  59 */     str = String.valueOf(String.valueOf(str)).concat("a procedure from the Procedure menu to start.");
/*     */     
/*  61 */     str = String.valueOf(String.valueOf(str)).concat("\n\n");
/*  62 */     str = String.valueOf(String.valueOf(str)).concat("If you would like to choose an area other than \"Queueing Theory\" within which ");
/*  63 */     str = String.valueOf(String.valueOf(str)).concat("to work, select the Area menu.\n");
/*     */     
/*  65 */     MultilineLabel content = new MultilineLabel(str);
/*  66 */     content.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*     */     
/*  68 */     this.areaHelpPanel = new MultilinePanel(content, this.tutorial.scrollPane);
/*     */   }
/*     */   
/*     */   private void QueueingTheoryJacksonnetwork() {
/*  72 */     this.queuepanel = new IORQTJacksonPanel(this);
/*  73 */     this.currentActionPanel = this.queuepanel;
/*  74 */     setHelpEnabled(true);
/*  75 */     this.tutorial.scrollPane.setViewportView(this.queuepanel);
/*  76 */     this.queuepanel.revalidate();
/*  77 */     this.queuepanel.repaint();
/*     */   }
/*     */   
/*     */   private void WaitingLineWork() {
/*  81 */     this.waitLinePane = new IORWaitingLinePane(this);
/*  82 */     this.currentActionPanel = this.waitLinePane;
/*  83 */     setHelpEnabled(true);
/*  84 */     this.tutorial.scrollPane.setViewportView(this.waitLinePane);
/*  85 */     this.waitLinePane.revalidate();
/*  86 */     this.waitLinePane.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setMenuState(int currentStep) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setDefaultMenuState() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void createMyMenus()
/*     */   {
/* 106 */     ButtonGroup group = new ButtonGroup();
/*     */     
/* 108 */     this.mi_jackson_network = new JRadioButtonMenuItem("Jackson Network");
/* 109 */     this.tutorial.procedure.add(this.mi_jackson_network);
/* 110 */     this.mi_jackson_network.setMnemonic('J');
/* 111 */     this.mi_jackson_network.setEnabled(true);
/* 112 */     this.mi_jackson_network.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 114 */         IORQTProcessController.this.procedure = "Jackson Network";
/* 115 */         IORQTProcessController.this.setStatusBar();
/* 116 */         IORQTProcessController.this.QueueingTheoryJacksonnetwork();
/* 117 */         IORQTProcessController.this.tutorial.closeDemoController();
/*     */       }
/* 119 */     });
/* 120 */     group.add(this.mi_jackson_network);
/*     */     
/*     */ 
/* 123 */     this.tutorial.option.setEnabled(false);
/* 124 */     this.mi_waiting_line = new JRadioButtonMenuItem("Animation of a Queueing System");
/* 125 */     this.tutorial.procedure.add(this.mi_waiting_line);
/* 126 */     this.mi_waiting_line.setMnemonic('W');
/* 127 */     this.mi_waiting_line.setEnabled(true);
/* 128 */     this.mi_waiting_line.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 130 */         IORQTProcessController.this.procedure = "Animation of a Queueing System";
/* 131 */         IORQTProcessController.this.setStatusBar();
/* 132 */         IORQTProcessController.this.WaitingLineWork();
/* 133 */         IORQTProcessController.this.tutorial.closeDemoController();
/*     */       }
/* 135 */     });
/* 136 */     group.add(this.mi_waiting_line);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getArea()
/*     */   {
/* 166 */     return "Queueing Theory";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/*     */     try
/*     */     {
/* 176 */       out.writeObject(this.solver);
/* 177 */       out.writeObject(this.data);
/* 178 */       out.writeObject(this.procedure);
/* 179 */       out.writeObject(this.option);
/*     */       
/* 181 */       if ((this.currentActionPanel instanceof IORQTJacksonPanel)) {
/* 182 */         out.writeObject("IORQTJacksonPanel");
/* 183 */       } else if ((this.currentActionPanel instanceof IORWaitingLinePane)) {
/* 184 */         out.writeObject("IORWaitingLinePane");
/*     */       } else {
/* 186 */         System.out.println("Save file fails");
/* 187 */         return;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 191 */       System.out.println("Save file fails");
/* 192 */       return;
/*     */     }
/*     */     
/* 195 */     this.currentActionPanel.SaveFile(out);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/*     */     try
/*     */     {
/* 208 */       this.solver = ((ORQTSolver)in.readObject());
/* 209 */       this.data = ((IORQTProcessController.QTData)in.readObject());
/* 210 */       this.procedure = ((String)in.readObject());
/* 211 */       this.option = ((String)in.readObject());
/*     */       
/* 213 */       ActionPanelName = (String)in.readObject();
/*     */     }
/*     */     catch (Exception e) {
/*     */       String ActionPanelName;
/* 217 */       System.out.println("Open file fails"); return;
/*     */     }
/*     */     
/*     */     String ActionPanelName;
/*     */     
/* 222 */     setHelpEnabled(true);
/* 223 */     if (ActionPanelName.equalsIgnoreCase("IORQTJacksonPanel")) {
/* 224 */       this.currentActionPanel = new IORQTJacksonPanel(this);
/* 225 */     } else if (ActionPanelName.equalsIgnoreCase("IORWaitingLinePane")) {
/* 226 */       this.currentActionPanel = new IORWaitingLinePane(this);
/*     */     } else {
/* 228 */       setHelpEnabled(false);
/* 229 */       System.out.println("Open file fails");
/* 230 */       return;
/*     */     }
/*     */     
/* 233 */     setStatusBar();
/* 234 */     this.currentActionPanel.LoadFile(in);
/*     */     
/*     */ 
/* 237 */     this.tutorial.scrollPane.setViewportView(this.currentActionPanel);
/* 238 */     this.currentActionPanel.revalidate();
/* 239 */     this.currentActionPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void print()
/*     */   {
/* 246 */     int p = 1;
/* 247 */     if ((this.currentActionPanel instanceof IORQTJacksonPanel)) {
/* 248 */       p = 1;
/* 249 */     } else if ((this.currentActionPanel instanceof IORWaitingLinePane)) {
/* 250 */       p = 2;
/*     */     }
/* 252 */     this.solver.setPrintProcedure(p);
/*     */     
/* 254 */     PrinterJob printJob = PrinterJob.getPrinterJob();
/* 255 */     printJob.setPrintable(this.solver);
/* 256 */     boolean pDialogState = true;
/* 257 */     pDialogState = printJob.printDialog();
/* 258 */     if (pDialogState) {
/*     */       try {
/* 260 */         printJob.print();
/*     */       }
/*     */       catch (Exception ex) {
/* 263 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void printToFile()
/*     */   {
/* 272 */     int p = 1;
/* 273 */     if ((this.currentActionPanel instanceof IORQTJacksonPanel)) {
/* 274 */       p = 1;
/* 275 */     } else if ((this.currentActionPanel instanceof IORWaitingLinePane)) {
/* 276 */       p = 2;
/*     */     }
/* 278 */     JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
/* 279 */     int result = fc.showSaveDialog(null);
/* 280 */     File chosenFile = fc.getSelectedFile();
/* 281 */     if ((result == 0) && (chosenFile != null))
/*     */     {
/* 283 */       this.solver.PrintToFile(chosenFile.getPath(), p);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class QTData
/*     */     implements Serializable
/*     */   {
/*     */     int num_facty;
/*     */     double[] a;
/*     */     double[][] p;
/*     */     double[] y;
/*     */     public String arrivalRate;
/*     */     public String serviceRate;
/*     */     public String ns1Num;
/*     */     public String ns2Num;
/*     */     public String lqStr;
/*     */     public String lsStr;
/*     */     public String wqStr;
/*     */     public String wsStr;
/*     */     public int phaseNum;
/*     */     public boolean isShowData;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORQTProcessController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */