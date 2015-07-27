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
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JLabel;
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
/*     */ 
/*     */ public class IORITProcessController
/*     */   extends IORProcessController
/*     */ {
/*     */   private JRadioButtonMenuItem mi_single_period_model_no_setup;
/*     */   private JRadioButtonMenuItem mi_single_period_model_with_setup;
/*     */   private JRadioButtonMenuItem mi_two_period_model_no_setup;
/*     */   private JRadioButtonMenuItem mi_infinite_period_model_no_setup;
/*     */   private JRadioButtonMenuItem mi_eoq_problem;
/*     */   private ButtonGroup buttongroup;
/*     */   private JRadioButtonMenuItem mi_exponential_distribution;
/*     */   private JRadioButtonMenuItem mi_uninform_distributtion;
/*     */   public boolean selectexp;
/*     */   public int problemno;
/*  47 */   private IORITActionPanel itPanel = null;
/*     */   
/*  49 */   public IORITProcessController.ITData data = null;
/*     */   public ORITSolver solver;
/*     */   private IOREOQPanel eoqPanel;
/*     */   
/*     */   private void InitialTheFactors()
/*     */   {
/*  55 */     this.data.lemta = 1.0D;
/*  56 */     this.data.purchasecost = 1.0D;
/*  57 */     this.data.holdcost = 1.0D;
/*  58 */     this.data.shortagecost = 1.0D;
/*  59 */     this.data.setupcost = 1.0D;
/*  60 */     this.data.discount = 0.9D;
/*  61 */     this.data.x0 = 0.0D;
/*  62 */     this.data.x1 = 1.0D;
/*  63 */     this.data.t = 1.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORITProcessController(IORTutorial t)
/*     */   {
/*  73 */     super(t);
/*  74 */     initializeAreaHelpPanel();
/*  75 */     this.selectexp = true;
/*  76 */     this.problemno = 0;
/*  77 */     this.solver = new ORITSolver();
/*  78 */     this.data = new IORITProcessController.ITData();
/*  79 */     InitialTheFactors();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeAreaHelpPanel()
/*     */   {
/*  87 */     String str = "The current area is \"Inventory Theory.\" You can select ";
/*  88 */     str = String.valueOf(String.valueOf(str)).concat("a procedure from the Procedure menu to start.");
/*  89 */     str = String.valueOf(String.valueOf(str)).concat("\n\n");
/*  90 */     str = String.valueOf(String.valueOf(str)).concat("If you would like to choose an area other than \"Inventory Theory\" within which ");
/*  91 */     str = String.valueOf(String.valueOf(str)).concat("to work, select the Area menu.\n");
/*     */     
/*  93 */     MultilineLabel content = new MultilineLabel(str);
/*  94 */     content.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*  95 */     this.areaHelpPanel = new MultilinePanel(content, this.tutorial.scrollPane);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void setMenuState(int currentStep) {}
/*     */   
/*     */ 
/*     */ 
/*     */   protected void setDefaultMenuState() {}
/*     */   
/*     */ 
/*     */ 
/*     */   private void solveproblem1()
/*     */   {
/* 110 */     InitialTheFactors();
/* 111 */     if (this.selectexp) this.problemno = 1; else
/* 112 */       this.problemno = 5;
/* 113 */     if (this.itPanel != null) this.itPanel = null;
/* 114 */     this.itPanel = new IORITActionPanel(this);
/*     */     
/* 116 */     setHelpEnabled(true);
/* 117 */     this.currentActionPanel = this.itPanel;
/* 118 */     this.itPanel.actionStatus.setText(" ");
/*     */     
/* 120 */     this.tutorial.scrollPane.setViewportView(this.itPanel);
/* 121 */     this.itPanel.bn_back.setVisible(false);
/* 122 */     this.itPanel.setFinishEnabled(true);
/*     */     
/* 124 */     this.itPanel.updateHelp();
/* 125 */     this.itPanel.revalidate();
/* 126 */     this.itPanel.repaint();
/*     */   }
/*     */   
/*     */   private void solveproblem2() {
/* 130 */     InitialTheFactors();
/* 131 */     if (this.selectexp) this.problemno = 2; else
/* 132 */       this.problemno = 6;
/* 133 */     if (this.itPanel != null) this.itPanel = null;
/* 134 */     this.itPanel = new IORITActionPanel(this);
/*     */     
/* 136 */     setHelpEnabled(true);
/* 137 */     this.currentActionPanel = this.itPanel;
/* 138 */     this.itPanel.actionStatus.setText(" ");
/*     */     
/* 140 */     this.tutorial.scrollPane.setViewportView(this.itPanel);
/* 141 */     this.itPanel.bn_back.setVisible(false);
/* 142 */     this.itPanel.setFinishEnabled(true);
/*     */     
/* 144 */     this.itPanel.updateHelp();
/* 145 */     this.itPanel.revalidate();
/* 146 */     this.itPanel.repaint();
/*     */   }
/*     */   
/*     */   private void solveproblem3() {
/* 150 */     InitialTheFactors();
/* 151 */     if (this.selectexp) this.problemno = 3; else
/* 152 */       this.problemno = 7;
/* 153 */     if (this.itPanel != null) this.itPanel = null;
/* 154 */     this.itPanel = new IORITActionPanel(this);
/*     */     
/* 156 */     setHelpEnabled(true);
/* 157 */     this.currentActionPanel = this.itPanel;
/* 158 */     this.itPanel.actionStatus.setText(" ");
/*     */     
/* 160 */     this.tutorial.scrollPane.setViewportView(this.itPanel);
/* 161 */     this.itPanel.bn_back.setVisible(false);
/* 162 */     this.itPanel.setFinishEnabled(true);
/*     */     
/* 164 */     this.itPanel.updateHelp();
/* 165 */     this.itPanel.revalidate();
/* 166 */     this.itPanel.repaint();
/*     */   }
/*     */   
/*     */   private void solveproblem4() {
/* 170 */     InitialTheFactors();
/* 171 */     if (this.selectexp) this.problemno = 4; else
/* 172 */       this.problemno = 8;
/* 173 */     if (this.itPanel != null) this.itPanel = null;
/* 174 */     this.itPanel = new IORITActionPanel(this);
/*     */     
/* 176 */     setHelpEnabled(true);
/* 177 */     this.currentActionPanel = this.itPanel;
/* 178 */     this.itPanel.actionStatus.setText(" ");
/*     */     
/* 180 */     this.tutorial.scrollPane.setViewportView(this.itPanel);
/* 181 */     this.itPanel.bn_back.setVisible(false);
/* 182 */     this.itPanel.setFinishEnabled(true);
/*     */     
/* 184 */     this.itPanel.updateHelp();
/* 185 */     this.itPanel.revalidate();
/* 186 */     this.itPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   private void setselecttrue()
/*     */   {
/* 192 */     if (this.selectexp == true) { return;
/*     */     }
/* 194 */     this.selectexp = true;
/*     */     
/* 196 */     if (this.problemno == 0) { return;
/*     */     }
/* 198 */     this.problemno -= 4;
/* 199 */     if (this.itPanel != null) this.itPanel = null;
/* 200 */     this.itPanel = new IORITActionPanel(this);
/* 201 */     this.currentActionPanel = this.itPanel;
/* 202 */     this.tutorial.scrollPane.setViewportView(this.itPanel);
/* 203 */     this.itPanel.setFinishEnabled(true);
/* 204 */     this.itPanel.revalidate();
/* 205 */     this.itPanel.repaint();
/*     */   }
/*     */   
/*     */   private void setselectfalse()
/*     */   {
/* 210 */     if (this.selectexp == false) return;
/* 211 */     this.selectexp = false;
/* 212 */     if (this.problemno == 0) return;
/* 213 */     this.problemno += 4;
/* 214 */     if (this.itPanel != null) this.itPanel = null;
/* 215 */     this.itPanel = new IORITActionPanel(this);
/* 216 */     this.currentActionPanel = this.itPanel;
/* 217 */     this.tutorial.scrollPane.setViewportView(this.itPanel);
/* 218 */     this.itPanel.setFinishEnabled(true);
/* 219 */     this.itPanel.revalidate();
/* 220 */     this.itPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void createMyMenus()
/*     */   {
/* 227 */     ButtonGroup group = new ButtonGroup();
/*     */     
/*     */ 
/* 230 */     this.mi_single_period_model_no_setup = new JRadioButtonMenuItem("Single Period Model, No Setup");
/* 231 */     this.tutorial.procedure.add(this.mi_single_period_model_no_setup);
/* 232 */     group.add(this.mi_single_period_model_no_setup);
/* 233 */     this.mi_single_period_model_no_setup.setMnemonic('S');
/* 234 */     this.mi_single_period_model_no_setup.setEnabled(true);
/* 235 */     this.mi_single_period_model_no_setup.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 237 */         IORITProcessController.this.procedure = "Single Period Model, No Setup";
/* 238 */         IORITProcessController.this.setStatusBar();
/* 239 */         IORITProcessController.this.solveproblem1();
/* 240 */         IORITProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 243 */     });
/* 244 */     this.mi_single_period_model_with_setup = new JRadioButtonMenuItem("Single Period Model, With Setup");
/* 245 */     this.tutorial.procedure.add(this.mi_single_period_model_with_setup);
/* 246 */     group.add(this.mi_single_period_model_with_setup);
/* 247 */     this.mi_single_period_model_with_setup.setMnemonic('W');
/* 248 */     this.mi_single_period_model_with_setup.setEnabled(true);
/* 249 */     this.mi_single_period_model_with_setup.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 251 */         IORITProcessController.this.procedure = "Single Period Model, With Setup";
/* 252 */         IORITProcessController.this.setStatusBar();
/* 253 */         IORITProcessController.this.solveproblem2();
/* 254 */         IORITProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 257 */     });
/* 258 */     this.mi_two_period_model_no_setup = new JRadioButtonMenuItem("Two Period Model, No Setup");
/* 259 */     this.tutorial.procedure.add(this.mi_two_period_model_no_setup);
/* 260 */     group.add(this.mi_two_period_model_no_setup);
/* 261 */     this.mi_two_period_model_no_setup.setMnemonic('T');
/* 262 */     this.mi_two_period_model_no_setup.setEnabled(true);
/* 263 */     this.mi_two_period_model_no_setup.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 265 */         IORITProcessController.this.procedure = "Two Period Model, No Setup";
/* 266 */         IORITProcessController.this.setStatusBar();
/* 267 */         IORITProcessController.this.solveproblem3();
/* 268 */         IORITProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 271 */     });
/* 272 */     this.mi_infinite_period_model_no_setup = new JRadioButtonMenuItem("Infinite Period Model, No Setup");
/* 273 */     this.tutorial.procedure.add(this.mi_infinite_period_model_no_setup);
/* 274 */     group.add(this.mi_infinite_period_model_no_setup);
/* 275 */     this.mi_infinite_period_model_no_setup.setMnemonic('I');
/* 276 */     this.mi_infinite_period_model_no_setup.setEnabled(true);
/* 277 */     this.mi_infinite_period_model_no_setup.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 279 */         IORITProcessController.this.procedure = "Infinite Period Model, No Setup";
/* 280 */         IORITProcessController.this.setStatusBar();
/* 281 */         IORITProcessController.this.solveproblem4();
/* 282 */         IORITProcessController.this.tutorial.closeDemoController();
/*     */       }
/* 284 */     });
/* 285 */     this.buttongroup = new ButtonGroup();
/*     */     
/* 287 */     this.tutorial.option.setEnabled(true);
/*     */     
/* 289 */     this.mi_exponential_distribution = new JRadioButtonMenuItem("Exponential Distribution");
/* 290 */     this.tutorial.option.add(this.mi_exponential_distribution);
/* 291 */     this.mi_exponential_distribution.setMnemonic('E');
/* 292 */     this.mi_exponential_distribution.setEnabled(true);
/* 293 */     this.mi_exponential_distribution.setSelected(true);
/* 294 */     this.mi_exponential_distribution.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 296 */         IORITProcessController.this.option = "Exponential Distribution";
/* 297 */         IORITProcessController.this.setselecttrue();
/* 298 */         IORITProcessController.this.setStatusBar();
/* 299 */         IORITProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 302 */     });
/* 303 */     this.mi_uninform_distributtion = new JRadioButtonMenuItem("Uniform Distribution");
/* 304 */     this.tutorial.option.add(this.mi_uninform_distributtion);
/* 305 */     this.mi_uninform_distributtion.setMnemonic('U');
/* 306 */     this.mi_uninform_distributtion.setEnabled(true);
/* 307 */     this.mi_uninform_distributtion.setSelected(false);
/* 308 */     this.mi_uninform_distributtion.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 310 */         IORITProcessController.this.option = "Uniform Distribution";
/* 311 */         IORITProcessController.this.setselectfalse();
/* 312 */         IORITProcessController.this.tutorial.closeDemoController();
/* 313 */         IORITProcessController.this.setStatusBar();
/*     */       }
/*     */       
/* 316 */     });
/* 317 */     this.mi_eoq_problem = new JRadioButtonMenuItem("EOQ Analysis");
/* 318 */     this.tutorial.procedure.add(this.mi_eoq_problem);
/* 319 */     group.add(this.mi_eoq_problem);
/* 320 */     this.mi_eoq_problem.setMnemonic('E');
/* 321 */     this.mi_eoq_problem.setEnabled(true);
/* 322 */     this.mi_eoq_problem.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 324 */         IORITProcessController.this.procedure = "EOQ Analysis";
/* 325 */         IORITProcessController.this.option = "EOQ Analysis";
/* 326 */         IORITProcessController.this.setStatusBar();
/* 327 */         IORITProcessController.this.initEOQProblem();
/* 328 */         IORITProcessController.this.tutorial.closeDemoController();
/*     */       }
/* 330 */     });
/* 331 */     this.buttongroup.add(this.mi_exponential_distribution);
/* 332 */     this.buttongroup.add(this.mi_uninform_distributtion);
/*     */   }
/*     */   
/*     */   private void initEOQProblem()
/*     */   {
/* 337 */     if (this.eoqPanel == null)
/* 338 */       this.eoqPanel = new IOREOQPanel(this);
/* 339 */     this.currentActionPanel = this.eoqPanel;
/* 340 */     this.currentActionPanel.actionStatus.setText("");
/* 341 */     setHelpEnabled(true);
/*     */     
/* 343 */     this.tutorial.scrollPane.setViewportView(this.eoqPanel);
/* 344 */     this.eoqPanel.setFinishEnabled(true);
/*     */   }
/*     */   
/*     */ 
/*     */   public static class ITData
/*     */     implements Serializable
/*     */   {
/*     */     public double lemta;
/*     */     
/*     */     public double purchasecost;
/*     */     
/*     */     public double holdcost;
/*     */     
/*     */     public double shortagecost;
/*     */     
/*     */     public double setupcost;
/*     */     
/*     */     public double discount;
/*     */     
/*     */     public double x0;
/*     */     public double x1;
/*     */     public double t;
/*     */     public double y0;
/*     */     public double S;
/*     */     public double s;
/*     */     public double y10;
/*     */     public double y20;
/* 371 */     public double EOQ_H = 9.0D;
/* 372 */     public double EOQ_D = 360.0D;
/* 373 */     public double EOQ_S = 15.0D;
/* 374 */     public int Qstar = 312;
/* 375 */     public int Qy = 35;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getArea()
/*     */   {
/* 383 */     return "Inventory Theory";
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
/* 394 */       out.writeObject(this.solver);
/* 395 */       out.writeObject(this.data);
/* 396 */       out.writeObject(this.procedure);
/* 397 */       out.writeObject(this.option);
/*     */       
/* 399 */       out.writeObject(new Boolean(this.selectexp));
/* 400 */       out.writeObject(new Integer(this.problemno));
/*     */       
/* 402 */       if ((this.currentActionPanel instanceof IORITActionPanel)) {
/* 403 */         out.writeObject("IORITActionPanel");
/* 404 */       } else if ((this.currentActionPanel instanceof IOREOQPanel)) {
/* 405 */         out.writeObject("IOREOQPanel");
/*     */       } else {
/* 407 */         System.out.println("Save file fails");
/* 408 */         return;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 412 */       System.out.println("Save file fails");
/* 413 */       return;
/*     */     }
/*     */     
/* 416 */     this.currentActionPanel.SaveFile(out);
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
/* 429 */       this.solver = ((ORITSolver)in.readObject());
/* 430 */       this.data = ((IORITProcessController.ITData)in.readObject());
/* 431 */       this.procedure = ((String)in.readObject());
/* 432 */       this.option = ((String)in.readObject());
/*     */       
/* 434 */       this.selectexp = ((Boolean)in.readObject()).booleanValue();
/* 435 */       this.problemno = ((Integer)in.readObject()).intValue();
/*     */       
/* 437 */       ActionPanelName = (String)in.readObject();
/*     */     }
/*     */     catch (Exception e) {
/*     */       String ActionPanelName;
/* 441 */       System.out.println("Open file fails"); return;
/*     */     }
/*     */     
/*     */     String ActionPanelName;
/*     */     
/* 446 */     setHelpEnabled(true);
/* 447 */     if (ActionPanelName.equalsIgnoreCase("IORITActionPanel")) {
/* 448 */       this.currentActionPanel = new IORITActionPanel(this);
/* 449 */       if (this.selectexp) {
/* 450 */         this.mi_exponential_distribution.setSelected(true);
/*     */       } else
/* 452 */         this.mi_uninform_distributtion.setSelected(true);
/* 453 */       if ((this.problemno == 1) || (this.problemno == 5)) {
/* 454 */         this.mi_single_period_model_no_setup.setSelected(true);
/* 455 */       } else if ((this.problemno == 2) || (this.problemno == 6)) {
/* 456 */         this.mi_single_period_model_with_setup.setSelected(true);
/* 457 */       } else if ((this.problemno == 3) || (this.problemno == 7)) {
/* 458 */         this.mi_two_period_model_no_setup.setSelected(true);
/* 459 */       } else if ((this.problemno == 4) || (this.problemno == 8)) {
/* 460 */         this.mi_infinite_period_model_no_setup.setSelected(true);
/*     */       }
/* 462 */     } else if (ActionPanelName.equalsIgnoreCase("IOREOQPanel")) {
/* 463 */       this.currentActionPanel = new IOREOQPanel(this);
/*     */     } else {
/* 465 */       setHelpEnabled(false);
/* 466 */       System.out.println("Open file fails");
/* 467 */       return;
/*     */     }
/*     */     
/* 470 */     setStatusBar();
/* 471 */     this.currentActionPanel.LoadFile(in);
/*     */     
/*     */ 
/* 474 */     this.tutorial.scrollPane.setViewportView(this.currentActionPanel);
/* 475 */     this.currentActionPanel.revalidate();
/* 476 */     this.currentActionPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void print()
/*     */   {
/* 484 */     int p = 1;
/* 485 */     if (this.mi_single_period_model_no_setup.isSelected()) {
/* 486 */       p = 1;
/* 487 */     } else if (this.mi_single_period_model_with_setup.isSelected()) {
/* 488 */       p = 2;
/* 489 */     } else if (this.mi_two_period_model_no_setup.isSelected()) {
/* 490 */       p = 3;
/* 491 */     } else if (this.mi_infinite_period_model_no_setup.isSelected())
/* 492 */       p = 4;
/* 493 */     this.solver.setPrintProcedure(p);
/*     */     
/* 495 */     PrinterJob printJob = PrinterJob.getPrinterJob();
/* 496 */     printJob.setPrintable(this.solver);
/* 497 */     boolean pDialogState = true;
/* 498 */     pDialogState = printJob.printDialog();
/* 499 */     if (pDialogState) {
/*     */       try {
/* 501 */         printJob.print();
/*     */       } catch (Exception ex) {
/* 503 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void printToFile()
/*     */   {
/* 512 */     int p = 1;
/* 513 */     if (this.mi_single_period_model_no_setup.isSelected()) {
/* 514 */       p = 1;
/* 515 */     } else if (this.mi_single_period_model_with_setup.isSelected()) {
/* 516 */       p = 2;
/* 517 */     } else if (this.mi_two_period_model_no_setup.isSelected()) {
/* 518 */       p = 3;
/* 519 */     } else if (this.mi_infinite_period_model_no_setup.isSelected()) {
/* 520 */       p = 4;
/* 521 */     } else if (this.mi_eoq_problem.isSelected()) {
/* 522 */       p = 5;
/*     */     }
/*     */     
/* 525 */     JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
/* 526 */     int result = fc.showSaveDialog(null);
/* 527 */     File chosenFile = fc.getSelectedFile();
/* 528 */     if ((result == 0) && (chosenFile != null)) {
/* 529 */       this.solver.PrintToFile(chosenFile.getPath(), p);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORITProcessController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */