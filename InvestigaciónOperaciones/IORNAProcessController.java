/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.File;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
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
/*     */ public class IORNAProcessController
/*     */   extends IORProcessController
/*     */ {
/*     */   private JMenuItem mi_network_simplex_method_interactive;
/*  32 */   public IORNAProcessController.NAData data = new IORNAProcessController.NAData();
/*     */   
/*  34 */   public ORNASolver solver = new ORNASolver();
/*     */   
/*  36 */   private IORNAProblemPanel problemPanel = null;
/*  37 */   private IORNAActionPanel netPanel = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORNAProcessController(IORTutorial t)
/*     */   {
/*  47 */     super(t);
/*  48 */     initializeAreaHelpPanel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeAreaHelpPanel()
/*     */   {
/*  55 */     String str = "\nThe current area is \"Network Analysis.\" You can select ";
/*  56 */     str = String.valueOf(String.valueOf(str)).concat("the procedure, Network Simplex Method - Interactive, from the Procedure menu to start. ");
/*  57 */     str = String.valueOf(String.valueOf(str)).concat(" Before executing this procedure for the first time, we recommend that you go through the ");
/*  58 */     str = String.valueOf(String.valueOf(str)).concat("demonstration example entitled Network Simplex Method in OR Tutor. ");
/*  59 */     str = String.valueOf(String.valueOf(str)).concat("\n\n");
/*  60 */     str = String.valueOf(String.valueOf(str)).concat("If you would like to choose an area other than \"Network Analysis\" within which ");
/*  61 */     str = String.valueOf(String.valueOf(str)).concat("to work, select the Area menu.\n");
/*     */     
/*  63 */     MultilineLabel content = new MultilineLabel(str);
/*  64 */     content.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*     */     
/*  66 */     this.areaHelpPanel = new MultilinePanel(content, this.tutorial.scrollPane);
/*     */   }
/*     */   
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
/*     */ 
/*     */   protected void createMyMenus()
/*     */   {
/*  88 */     this.mi_network_simplex_method_interactive = new JMenuItem("Network Simplex Method -- Interactive");
/*  89 */     this.tutorial.procedure.add(this.mi_network_simplex_method_interactive);
/*  90 */     this.mi_network_simplex_method_interactive.setMnemonic('N');
/*  91 */     this.mi_network_simplex_method_interactive.setEnabled(true);
/*  92 */     this.mi_network_simplex_method_interactive.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  94 */         IORNAProcessController.this.procedure = "Network Simplex Method -- Interactive";
/*  95 */         IORNAProcessController.this.setStatusBar();
/*  96 */         IORNAProcessController.this.solveProblem();
/*  97 */         IORNAProcessController.this.tutorial.closeDemoController();
/*     */       }
/*  99 */     });
/* 100 */     this.tutorial.option.setEnabled(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void solveInteractive()
/*     */   {
/* 108 */     this.solver.getData(this.data);
/* 109 */     this.netPanel = new IORNAActionPanel(this);
/*     */     
/* 111 */     this.currentActionPanel = this.netPanel;
/* 112 */     this.tutorial.scrollPane.setViewportView(this.netPanel);
/*     */     
/* 114 */     this.netPanel.setFinishEnabled(true);
/*     */     
/* 116 */     this.netPanel.revalidate();
/* 117 */     this.netPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   private void solveProblem()
/*     */   {
/* 123 */     this.problemPanel = new IORNAProblemPanel(this);
/*     */     
/* 125 */     this.currentActionPanel = this.problemPanel;
/* 126 */     this.tutorial.scrollPane.setViewportView(this.problemPanel);
/*     */     
/* 128 */     setHelpEnabled(true);
/*     */     
/* 130 */     this.problemPanel.revalidate();
/* 131 */     this.problemPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   public static class Arc
/*     */     implements Serializable
/*     */   {
/*     */     public int begin;
/*     */     public int end;
/*     */     public String arcName;
/*     */     public boolean is_basic;
/*     */     public boolean is_reversed;
/*     */     public double capacity;
/*     */     public double flow;
/*     */     public double cost;
/*     */     
/*     */     public String getArcName()
/*     */     {
/* 149 */       String name = new String("");
/*     */       
/*     */ 
/* 152 */       if ((this.begin <= 0) || (this.end <= 0) || (this.begin >= 25) || (this.end >= 25))
/* 153 */         return "UU";
/* 154 */       char first = (char)(65 + this.begin - 1);
/* 155 */       char second = (char)(65 + this.end - 1);
/*     */       
/* 157 */       if (this.is_reversed) {
/* 158 */         name = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(name))).append(second).append(first)));
/*     */       }
/*     */       else {
/* 161 */         name = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(name))).append(first).append(second)));
/*     */       }
/* 163 */       return name;
/*     */     }
/*     */     
/*     */     public boolean isEqualTo(int b, int e) {
/* 167 */       return (this.begin == b) && (this.end == e);
/*     */     }
/*     */     
/*     */     public int compareTo(Arc a) {
/* 171 */       return getArcName().compareToIgnoreCase(a.getArcName());
/*     */     }
/*     */   }
/*     */   
/*     */   public static class NAData implements Serializable {
/* 176 */     public final int MAX_ARCS = 18;
/*     */     
/*     */     public int which_problem;
/*     */     
/*     */     public int num_nodes;
/*     */     
/*     */     public int num_arcs;
/*     */     
/*     */     public double[] node_flow;
/*     */     
/*     */     public double[] deltaZ;
/*     */     public double total_inc;
/*     */     public double num_segment;
/*     */     public double[] segment_inc;
/*     */     public double Z;
/*     */     public int[][] cycle_arcs;
/* 192 */     public IORNAProcessController.Arc[] arcs = new IORNAProcessController.Arc[18];
/*     */     
/*     */     public String[] notation;
/*     */     
/*     */     public NAData()
/*     */     {
/* 198 */       for (int i = 0; i < 18; i++) {
/* 199 */         this.arcs[i] = new IORNAProcessController.Arc();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected String getArea()
/*     */   {
/* 208 */     return "Network Analysis";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/*     */     try
/*     */     {
/* 219 */       out.writeObject(this.solver);
/* 220 */       out.writeObject(this.data);
/* 221 */       out.writeObject(this.procedure);
/* 222 */       out.writeObject(this.option);
/*     */       
/* 224 */       if ((this.currentActionPanel instanceof IORNAProblemPanel)) {
/* 225 */         out.writeObject("IORNAProblemPanel");
/*     */       }
/* 227 */       else if ((this.currentActionPanel instanceof IORNAActionPanel)) {
/* 228 */         out.writeObject("IORNAActionPanel");
/*     */       }
/*     */       else {
/* 231 */         System.out.println("Save file fails");
/* 232 */         return;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 236 */       System.out.println("Save file fails");
/* 237 */       return;
/*     */     }
/*     */     
/* 240 */     this.currentActionPanel.SaveFile(out);
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
/* 253 */       this.solver = ((ORNASolver)in.readObject());
/* 254 */       this.data = ((IORNAProcessController.NAData)in.readObject());
/* 255 */       this.procedure = ((String)in.readObject());
/* 256 */       this.option = ((String)in.readObject());
/*     */       
/* 258 */       ActionPanelName = (String)in.readObject();
/*     */     }
/*     */     catch (Exception e) {
/*     */       String ActionPanelName;
/* 262 */       System.out.println("Open file fails"); return;
/*     */     }
/*     */     
/*     */     String ActionPanelName;
/*     */     
/* 267 */     setHelpEnabled(true);
/* 268 */     if (ActionPanelName.equalsIgnoreCase("IORNAProblemPanel")) {
/* 269 */       this.currentActionPanel = new IORNAProblemPanel(this);
/*     */     }
/* 271 */     else if (ActionPanelName.equalsIgnoreCase("IORNAActionPanel")) {
/* 272 */       this.currentActionPanel = new IORNAActionPanel(this);
/*     */     }
/*     */     else {
/* 275 */       setHelpEnabled(false);
/* 276 */       System.out.println("Open file fails");
/* 277 */       return;
/*     */     }
/*     */     
/* 280 */     setStatusBar();
/* 281 */     this.currentActionPanel.LoadFile(in);
/*     */     
/*     */ 
/* 284 */     this.tutorial.scrollPane.setViewportView(this.currentActionPanel);
/* 285 */     this.currentActionPanel.revalidate();
/* 286 */     this.currentActionPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void print()
/*     */   {
/* 294 */     PrinterJob printJob = PrinterJob.getPrinterJob();
/* 295 */     printJob.setPrintable(this.solver);
/* 296 */     boolean pDialogState = true;
/* 297 */     pDialogState = printJob.printDialog();
/* 298 */     if (pDialogState) {
/*     */       try {
/* 300 */         printJob.print();
/*     */       } catch (Exception ex) {
/* 302 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void printToFile()
/*     */   {
/* 311 */     JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
/* 312 */     int result = fc.showSaveDialog(null);
/* 313 */     File chosenFile = fc.getSelectedFile();
/* 314 */     if ((result == 0) && (chosenFile != null)) {
/* 315 */       this.solver.PrintToFile(chosenFile.getPath(), 1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNAProcessController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */