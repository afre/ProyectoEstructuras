/*     */ import java.awt.Point;
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
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JRadioButtonMenuItem;
/*     */ import javax.swing.JScrollPane;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORHungarianProcessController
/*     */   extends IORProcessController
/*     */ {
/*     */   public IORTutorial iort;
/*  25 */   public boolean hungarianAuto = false;
/*     */   
/*     */ 
/*     */   protected ORHungarianSolver solver;
/*     */   
/*     */   private IORHungarianProcessController HungarianController;
/*     */   
/*     */   private JRadioButtonMenuItem mi_interactive_hungarian_method;
/*     */   
/*     */   private JRadioButtonMenuItem mi_automatic_hungarian_method;
/*     */   
/*  36 */   public ButtonGroup option_group = null;
/*     */   
/*  38 */   private IORHungarianPanel huangarianPanel = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public IORHungarianProcessController.HungarianTableData tableData;
/*     */   
/*     */ 
/*     */   private String ActionPanelName;
/*     */   
/*     */ 
/*  48 */   public boolean isAuto = false;
/*     */   
/*     */ 
/*     */ 
/*     */   public IORHungarianProcessController.HungarianTableData getTableDataInstance()
/*     */   {
/*  54 */     return new IORHungarianProcessController.HungarianTableData();
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
/*     */   public IORHungarianProcessController.HungarianData getDataInstance()
/*     */   {
/*  73 */     return new IORHungarianProcessController.HungarianData();
/*     */   }
/*     */   
/*     */   public IORHungarianProcessController(IORTutorial t) {
/*  77 */     super(t);
/*  78 */     initializeAreaHelpPanel();
/*  79 */     this.iort = t;
/*  80 */     this.solver = new ORHungarianSolver();
/*  81 */     this.tableData = new IORHungarianProcessController.HungarianTableData();
/*     */   }
/*     */   
/*     */ 
/*     */   protected void initializeAreaHelpPanel()
/*     */   {
/*  87 */     String str = "The current area is \"The Assignment Problem.\" You can select ";
/*     */     
/*  89 */     str = String.valueOf(String.valueOf(str)).concat("a procedure from the Procedure menu to start. ");
/*     */     
/*  91 */     str = String.valueOf(String.valueOf(str)).concat("\n\n");
/*  92 */     str = String.valueOf(String.valueOf(str)).concat("If you would like to choose an area other than \"The Assignment Problem\" within which ");
/*  93 */     str = String.valueOf(String.valueOf(str)).concat("to work, select the Area menu.\n");
/*     */     
/*  95 */     MultilineLabel content = new MultilineLabel(str);
/*  96 */     content.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*     */     
/*  98 */     this.areaHelpPanel = new MultilinePanel(content, this.tutorial.scrollPane);
/*     */   }
/*     */   
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
/*     */ 
/*     */ 
/*     */   private void setMenuStateTSPStartEnterProblem()
/*     */   {
/* 117 */     this.mi_interactive_hungarian_method.setEnabled(true);
/* 118 */     this.tutorial.option.setEnabled(false);
/*     */   }
/*     */   
/*     */ 
/*     */   private void setMenuStateTSPFinishEnterProblem()
/*     */   {
/* 124 */     this.tutorial.option.setEnabled(true);
/*     */   }
/*     */   
/*     */   protected void setDefaultMenuState()
/*     */   {
/* 129 */     setMenuStateTSPStartEnterProblem();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void createMyMenus()
/*     */   {
/* 136 */     ButtonGroup group = new ButtonGroup();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 143 */     this.mi_interactive_hungarian_method = new JRadioButtonMenuItem("Solve an Assignment Problem Interactively");
/* 144 */     this.mi_automatic_hungarian_method = new JRadioButtonMenuItem("Solve an Assignment Problem Automatically");
/*     */     
/* 146 */     this.tutorial.procedure.add(this.mi_interactive_hungarian_method);
/* 147 */     this.tutorial.procedure.add(this.mi_automatic_hungarian_method);
/*     */     
/* 149 */     group.add(this.mi_interactive_hungarian_method);
/* 150 */     group.add(this.mi_automatic_hungarian_method);
/*     */     
/* 152 */     this.mi_interactive_hungarian_method.setMnemonic('T');
/* 153 */     this.mi_automatic_hungarian_method.setMnemonic('N');
/*     */     
/* 155 */     this.mi_interactive_hungarian_method.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 158 */         IORHungarianProcessController.this.isAuto = false;
/* 159 */         IORHungarianProcessController.this.procedure = "Solve an Assignment Problem Interactively";
/*     */         
/* 161 */         IORHungarianProcessController.this.setStatusBar();
/* 162 */         IORHungarianProcessController.this.setHelpEnabled(true);
/* 163 */         IORHungarianProcessController.this.hungarian(IORHungarianProcessController.this.isAuto);
/*     */       }
/*     */       
/* 166 */     });
/* 167 */     this.mi_automatic_hungarian_method.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 170 */         IORHungarianProcessController.this.isAuto = true;
/* 171 */         IORHungarianProcessController.this.procedure = "Solve an Assignment Problem Automatically";
/*     */         
/* 173 */         IORHungarianProcessController.this.setStatusBar();
/* 174 */         IORHungarianProcessController.this.setHelpEnabled(true);
/* 175 */         IORHungarianProcessController.this.hungarian(IORHungarianProcessController.this.isAuto);
/*     */ 
/*     */       }
/*     */       
/*     */ 
/* 180 */     });
/* 181 */     this.tutorial.option.setEnabled(false);
/* 182 */     setHelpEnabled(true);
/*     */   }
/*     */   
/*     */   private void hungarian(boolean auto) {
/* 186 */     IOROperation op = new IOROperation(60009, null);
/* 187 */     this.hungarianAuto = auto;
/* 188 */     if (this.huangarianPanel == null)
/* 189 */       this.huangarianPanel = new IORHungarianPanel(this, this.hungarianAuto);
/* 190 */     this.huangarianPanel.changeDemoState(this.hungarianAuto);
/* 191 */     this.currentActionPanel = this.huangarianPanel;
/* 192 */     this.currentActionPanel.actionStatus.setText(" ");
/*     */     
/* 194 */     this.huangarianPanel.auto = auto;
/*     */     
/*     */ 
/* 197 */     this.tutorial.scrollPane.setViewportView(this.huangarianPanel);
/*     */     
/* 199 */     this.huangarianPanel.setFinishEnabled(true);
/* 200 */     this.huangarianPanel.initializeHelpPanels();
/* 201 */     this.huangarianPanel.revalidate();
/* 202 */     this.huangarianPanel.updatePanel();
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
/*     */   private void setOption(int option)
/*     */   {
/* 225 */     Vector p = new Vector();
/* 226 */     p.addElement(new Integer(option));
/*     */     
/* 228 */     IOROperation operation = new IOROperation(60012, p);
/*     */     
/* 230 */     if (this.currentActionPanel != null) {
/* 231 */       this.currentActionPanel.updatePanel();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getOption() {
/* 236 */     return 1;
/*     */   }
/*     */   
/*     */   protected String getArea()
/*     */   {
/* 241 */     return "The Assignment Problem";
/*     */   }
/*     */   
/*     */   public void SaveFile(ObjectOutputStream out) {
/*     */     try {
/* 246 */       out.writeObject(this.solver);
/* 247 */       out.writeObject(this.procedure);
/* 248 */       out.writeObject(this.option);
/*     */       
/* 250 */       if ((this.currentActionPanel instanceof IORHungarianPanel)) {
/* 251 */         out.writeObject("IORHungarianPanel");
/*     */       }
/*     */       else {
/* 254 */         System.out.println("Panel Null Save file fails");
/* 255 */         return;
/*     */       }
/* 257 */       out.writeBoolean(this.hungarianAuto);
/*     */     }
/*     */     catch (Exception e) {
/* 260 */       System.out.println("Exception Save file fails".concat(String.valueOf(String.valueOf(e))));
/* 261 */       return;
/*     */     }
/* 263 */     this.currentActionPanel.SaveFile(out);
/*     */   }
/*     */   
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/*     */     try {
/* 269 */       this.solver = ((ORHungarianSolver)in.readObject());
/* 270 */       this.procedure = ((String)in.readObject());
/* 271 */       this.option = ((String)in.readObject());
/* 272 */       this.ActionPanelName = ((String)in.readObject());
/* 273 */       this.hungarianAuto = in.readBoolean();
/*     */       
/* 275 */       System.out.println("Load hungarianAuto is :".concat(String.valueOf(String.valueOf(this.hungarianAuto))));
/*     */     }
/*     */     catch (Exception e) {
/* 278 */       System.out.println("Open file fails....".concat(String.valueOf(String.valueOf(e))));
/* 279 */       return;
/*     */     }
/*     */     
/* 282 */     this.mi_interactive_hungarian_method.setEnabled(true);
/* 283 */     this.mi_interactive_hungarian_method.setSelected(true);
/* 284 */     this.tutorial.option.setEnabled(true);
/*     */     
/* 286 */     setHelpEnabled(true);
/* 287 */     if (this.ActionPanelName.equalsIgnoreCase("IORHungarianPanel")) {
/* 288 */       this.currentActionPanel = new IORHungarianPanel(this, this.hungarianAuto);
/* 289 */       if (!this.hungarianAuto) {
/* 290 */         this.mi_interactive_hungarian_method.setSelected(true);
/*     */       } else {
/* 292 */         this.mi_automatic_hungarian_method.setSelected(true);
/*     */       }
/* 294 */       this.tutorial.option.setEnabled(false);
/*     */     }
/*     */     else {
/* 297 */       setHelpEnabled(false);
/* 298 */       System.out.println("Open file fails !!!!");
/* 299 */       return;
/*     */     }
/*     */     
/* 302 */     setStatusBar();
/* 303 */     this.currentActionPanel.LoadFile(in);
/*     */     
/*     */ 
/* 306 */     this.tutorial.scrollPane.setViewportView(this.currentActionPanel);
/* 307 */     this.currentActionPanel.revalidate();
/* 308 */     this.currentActionPanel.repaint();
/*     */   }
/*     */   
/*     */   public void print() {
/* 312 */     int p = 1;
/* 313 */     if (((this.currentActionPanel instanceof IORHungarianPanel)) && (this.isAuto)) {
/* 314 */       p = 1;
/* 315 */     } else if (((this.currentActionPanel instanceof IORHungarianPanel)) && (!this.isAuto)) {
/* 316 */       p = 2;
/*     */     }
/* 318 */     this.solver.procedure = p;
/* 319 */     PrinterJob printJob = PrinterJob.getPrinterJob();
/* 320 */     this.solver.setPrintProcedure(p);
/* 321 */     printJob.setPrintable(this.solver);
/*     */     
/* 323 */     boolean pDialogState = true;
/* 324 */     pDialogState = printJob.printDialog();
/* 325 */     if (pDialogState) {
/*     */       try {
/* 327 */         printJob.print();
/*     */       }
/*     */       catch (Exception ex) {
/* 330 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void printToFile()
/*     */   {
/* 337 */     int p = 1;
/* 338 */     if (((this.currentActionPanel instanceof IORHungarianPanel)) && (!this.isAuto)) {
/* 339 */       p = 1;
/* 340 */     } else if (((this.currentActionPanel instanceof IORHungarianPanel)) && (this.isAuto)) {
/* 341 */       p = 2;
/*     */     }
/* 343 */     this.solver.procedure = p;
/*     */     
/* 345 */     JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
/* 346 */     int result = fc.showSaveDialog(null);
/* 347 */     File chosenFile = fc.getSelectedFile();
/* 348 */     if ((result == 0) && (chosenFile != null)) {
/* 349 */       this.solver.PrintToFile(chosenFile.getPath(), p);
/*     */     }
/*     */   }
/*     */   
/*     */   public class HungarianData
/*     */     implements Serializable
/*     */   {
/*     */     int currentStep;
/*     */     int taskNumber;
/*     */     String[][] data;
/*     */     int[] selectLines;
/*     */     int[][] selectPoint;
/*     */     Point min;
/*     */     Point[] bestAssign;
/*     */     String[] bestSolution;
/*     */     
/*     */     public HungarianData() {}
/*     */   }
/*     */   
/*     */   public class HungarianTableData
/*     */     implements Serializable
/*     */   {
/*     */     String[] initData;
/*     */     
/*     */     public HungarianTableData() {}
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORHungarianProcessController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */