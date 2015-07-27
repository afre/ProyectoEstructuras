/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.File;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JRadioButtonMenuItem;
/*     */ import javax.swing.JScrollPane;
/*     */ 
/*     */ public class IORGraphicalProcessController
/*     */   extends IORProcessController
/*     */ {
/*     */   public IORTutorial iort;
/*     */   private JRadioButtonMenuItem mi_exp_problem;
/*     */   private JRadioButtonMenuItem mi_trend_problem;
/*     */   private JRadioButtonMenuItem mi_simple_problem;
/*     */   private JRadioButtonMenuItem mi_last_problem;
/*     */   private JRadioButtonMenuItem mi_average_problem;
/*     */   private JRadioButtonMenuItem mi_moving_average_problem;
/*     */   private IORGForecastPane forecastPanel;
/*  30 */   public IORGraphicalProcessController.ForecastData data = null;
/*  31 */   public ORForecastSolver solver = null;
/*     */   
/*     */   public IORGraphicalProcessController(IORTutorial t) {
/*  34 */     super(t);
/*  35 */     initializeAreaHelpPanel();
/*  36 */     this.iort = t;
/*  37 */     this.data = new IORGraphicalProcessController.ForecastData();
/*  38 */     this.solver = new ORForecastSolver();
/*     */   }
/*     */   
/*     */   protected void initializeAreaHelpPanel()
/*     */   {
/*  43 */     String str = "The current area is \"Forecasting.\" You can select ";
/*     */     
/*  45 */     str = String.valueOf(String.valueOf(str)).concat("a procedure from the Procedure menu to start.  ");
/*     */     
/*     */ 
/*  48 */     str = String.valueOf(String.valueOf(str)).concat("\n\n");
/*  49 */     str = String.valueOf(String.valueOf(str)).concat("If you would like to choose an area other than \"Forecasting\" within which ");
/*  50 */     str = String.valueOf(String.valueOf(str)).concat("to work, select the Area menu.\n");
/*     */     
/*  52 */     MultilineLabel content = new MultilineLabel(str);
/*  53 */     content.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*     */     
/*  55 */     this.areaHelpPanel = new MultilinePanel(content, this.tutorial.scrollPane);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void setMenuState(int currentStep) {}
/*     */   
/*     */ 
/*     */   private void setMenuStateAllModule()
/*     */   {
/*  64 */     this.mi_exp_problem.setEnabled(true);
/*  65 */     this.tutorial.option.setEnabled(false);
/*     */     
/*  67 */     this.mi_trend_problem.setEnabled(true);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void setDefaultMenuState()
/*     */   {
/*  73 */     setMenuStateAllModule();
/*     */   }
/*     */   
/*     */   protected void createMyMenus()
/*     */   {
/*  78 */     ButtonGroup group = new ButtonGroup();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  84 */     this.mi_exp_problem = new JRadioButtonMenuItem("Exponential Smoothing Method");
/*  85 */     group.add(this.mi_exp_problem);
/*  86 */     this.iort.procedure.add(this.mi_exp_problem);
/*  87 */     this.mi_exp_problem.setMnemonic('E');
/*     */     
/*  89 */     this.mi_exp_problem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/*  92 */         IORGraphicalProcessController.this.procedure = "Exponential Smoothing Method";
/*  93 */         IORGraphicalProcessController.this.setStatusBar();
/*  94 */         IORGraphicalProcessController.this.initForecastProblem(0);
/*  95 */         IORGraphicalProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/*     */ 
/*  99 */     });
/* 100 */     this.mi_trend_problem = new JRadioButtonMenuItem("Exponential Smoothing with Trend");
/* 101 */     group.add(this.mi_trend_problem);
/* 102 */     this.iort.procedure.add(this.mi_trend_problem);
/* 103 */     this.mi_trend_problem.setMnemonic('T');
/* 104 */     this.mi_trend_problem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 107 */         IORGraphicalProcessController.this.procedure = "Exponential Smoothing with Trend";
/* 108 */         IORGraphicalProcessController.this.setStatusBar();
/* 109 */         IORGraphicalProcessController.this.initForecastProblem(1);
/* 110 */         IORGraphicalProcessController.this.tutorial.closeDemoController();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       }
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
/* 131 */     });
/* 132 */     this.mi_simple_problem = new JRadioButtonMenuItem("Linear Regression Method");
/* 133 */     group.add(this.mi_simple_problem);
/* 134 */     this.iort.procedure.add(this.mi_simple_problem);
/* 135 */     this.mi_simple_problem.setMnemonic('S');
/* 136 */     this.mi_simple_problem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 139 */         IORGraphicalProcessController.this.procedure = "Linear Regression Method";
/* 140 */         IORGraphicalProcessController.this.setStatusBar();
/* 141 */         IORGraphicalProcessController.this.initForecastProblem(3);
/* 142 */         IORGraphicalProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/*     */ 
/* 146 */     });
/* 147 */     this.mi_last_problem = new JRadioButtonMenuItem("Last Value Method");
/* 148 */     group.add(this.mi_last_problem);
/* 149 */     this.iort.procedure.add(this.mi_last_problem);
/* 150 */     this.mi_last_problem.setMnemonic('V');
/* 151 */     this.mi_last_problem.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 153 */         IORGraphicalProcessController.this.procedure = "Last Value Method";
/* 154 */         IORGraphicalProcessController.this.setStatusBar();
/* 155 */         IORGraphicalProcessController.this.initForecastProblem(4);
/* 156 */         IORGraphicalProcessController.this.tutorial.closeDemoController();
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 162 */     });
/* 163 */     this.mi_average_problem = new JRadioButtonMenuItem("Averaging Method");
/* 164 */     group.add(this.mi_average_problem);
/* 165 */     this.iort.procedure.add(this.mi_average_problem);
/* 166 */     this.mi_average_problem.setMnemonic('A');
/* 167 */     this.mi_average_problem.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 169 */         IORGraphicalProcessController.this.procedure = "Averaging Method";
/* 170 */         IORGraphicalProcessController.this.setStatusBar();
/* 171 */         IORGraphicalProcessController.this.initForecastProblem(5);
/* 172 */         IORGraphicalProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/*     */ 
/* 176 */     });
/* 177 */     this.mi_moving_average_problem = new JRadioButtonMenuItem("Moving-Average Method");
/* 178 */     group.add(this.mi_moving_average_problem);
/* 179 */     this.iort.procedure.add(this.mi_moving_average_problem);
/* 180 */     this.mi_moving_average_problem.setMnemonic('M');
/* 181 */     this.mi_moving_average_problem.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 183 */         IORGraphicalProcessController.this.procedure = "Moving-Average Method";
/* 184 */         IORGraphicalProcessController.this.setStatusBar();
/* 185 */         IORGraphicalProcessController.this.initForecastProblem(6);
/* 186 */         IORGraphicalProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private void initForecastProblem(int num)
/*     */   {
/* 193 */     if (this.forecastPanel == null)
/* 194 */       this.forecastPanel = new IORGForecastPane(this);
/* 195 */     this.currentActionPanel = this.forecastPanel;
/* 196 */     this.currentActionPanel.actionStatus.setText("");
/*     */     
/* 198 */     setHelpEnabled(true);
/* 199 */     this.tutorial.scrollPane.setViewportView(this.forecastPanel);
/* 200 */     this.forecastPanel.methodComb.setSelectedIndex(num);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void setOption(int option)
/*     */   {
/* 207 */     Vector p = new Vector();
/* 208 */     p.addElement(new Integer(option));
/*     */     
/* 210 */     IOROperation operation = new IOROperation(60012, p);
/*     */     
/* 212 */     if (this.currentActionPanel != null) {
/* 213 */       this.currentActionPanel.updatePanel();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ForecastData implements Serializable {
/* 218 */     public float currAlpha = 0.4F; public float currBeta = 0.5F;
/*     */     
/* 220 */     public String[][] tableData = null;
/*     */     public float aincept;
/*     */     public float bincept;
/*     */     public float coef_x;
/*     */     public float coef_y;
/*     */     public float errMAD;
/*     */     public float errMFE; public int forecastingNum; public int lastLooknum; }
/* 227 */   protected String getArea() { return "Forecasting"; }
/*     */   
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/*     */     try {
/* 232 */       out.writeObject(this.solver);
/* 233 */       out.writeObject(this.data);
/* 234 */       out.writeObject(this.procedure);
/* 235 */       out.writeObject(this.option);
/* 236 */       out.writeObject("IORGForecastPane");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 240 */       System.out.println("Save file fails");
/* 241 */       return;
/*     */     }
/*     */     
/* 244 */     this.currentActionPanel.SaveFile(out);
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
/* 257 */       this.solver = ((ORForecastSolver)in.readObject());
/* 258 */       this.data = ((IORGraphicalProcessController.ForecastData)in.readObject());
/* 259 */       this.procedure = ((String)in.readObject());
/* 260 */       this.option = ((String)in.readObject());
/* 261 */       ActionPanelName = (String)in.readObject();
/*     */     }
/*     */     catch (Exception e) {
/*     */       String ActionPanelName;
/* 265 */       System.out.println("Open file fails"); return;
/*     */     }
/*     */     
/*     */     String ActionPanelName;
/*     */     
/* 270 */     setHelpEnabled(true);
/* 271 */     this.currentActionPanel = new IORGForecastPane(this);
/* 272 */     this.mi_exp_problem.setSelected(true);
/* 273 */     setStatusBar();
/* 274 */     this.currentActionPanel.LoadFile(in);
/*     */     
/*     */ 
/* 277 */     this.tutorial.scrollPane.setViewportView(this.currentActionPanel);
/* 278 */     this.currentActionPanel.revalidate();
/* 279 */     this.currentActionPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void print()
/*     */   {
/* 287 */     PrinterJob printJob = PrinterJob.getPrinterJob();
/* 288 */     printJob.setPrintable(this.solver);
/* 289 */     boolean pDialogState = true;
/* 290 */     pDialogState = printJob.printDialog();
/* 291 */     if (pDialogState) {
/*     */       try {
/* 293 */         printJob.print();
/*     */       } catch (Exception ex) {
/* 295 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void printToFile()
/*     */   {
/* 304 */     int p = 1;
/* 305 */     JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
/* 306 */     int result = fc.showSaveDialog(null);
/* 307 */     File chosenFile = fc.getSelectedFile();
/* 308 */     if ((result == 0) && (chosenFile != null)) {
/* 309 */       this.solver.PrintToFile(chosenFile.getPath(), p);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORGraphicalProcessController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */