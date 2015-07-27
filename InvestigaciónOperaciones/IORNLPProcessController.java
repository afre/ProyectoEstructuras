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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORNLPProcessController
/*     */   extends IORProcessController
/*     */ {
/*     */   private JRadioButtonMenuItem mi_interactive_one_dimesional_seacrch_procedure;
/*     */   private JRadioButtonMenuItem mi_automatic_gradient_search_procedure;
/*     */   private JRadioButtonMenuItem mi_interactive_gradient_search_procedure;
/*     */   private JRadioButtonMenuItem mi_interactive_modified_simplex_method;
/*     */   private JRadioButtonMenuItem mi_interactive_frank_Wolfe_algorithm;
/*     */   private JRadioButtonMenuItem mi_sequential_uncostrained_minimization_technique_SUMT;
/*  37 */   public ORNLPSolver solver = new ORNLPSolver();
/*  38 */   public IORNLPProcessController.NLData data = new IORNLPProcessController.NLData();
/*     */   
/*  40 */   protected IORNLOneDimensionPanel onedpanel = null;
/*  41 */   protected IORNLGradientPanel gradpanel = null;
/*  42 */   protected IORNLAutoGradPanel autogradpanel = null;
/*  43 */   protected IORNLFrankWolfPanel fwpanel = null;
/*  44 */   protected IORNLSumtPanel sumtpanel = null;
/*  45 */   protected IORNLModifyPanel modfpanel = null;
/*  46 */   protected IORNLGeneticPanel gepanel = null;
/*  47 */   protected IORNLAnnealingPanel apanel = null;
/*     */   
/*     */   public IORNLPProcessController(IORTutorial t)
/*     */   {
/*  51 */     super(t);
/*  52 */     initializeAreaHelpPanel();
/*     */   }
/*     */   
/*     */   protected void initializeAreaHelpPanel()
/*     */   {
/*  57 */     String str = "The current area is \"Nonlinear Programming.\" You can select ";
/*  58 */     str = String.valueOf(String.valueOf(str)).concat("a procedure from the Procedure menu to start. Whenever a relevant demonstration example is available in OR Tutor, ");
/*  59 */     str = String.valueOf(String.valueOf(str)).concat("we recommend that you go through it before executing the corresponding procedure for the first time.");
/*  60 */     str = String.valueOf(String.valueOf(str)).concat("\n\n");
/*  61 */     str = String.valueOf(String.valueOf(str)).concat("If you would like to choose an area other than \"Nonlinear Programming\" within which ");
/*  62 */     str = String.valueOf(String.valueOf(str)).concat("to work, select the Area menu.\n");
/*     */     
/*  64 */     MultilineLabel content = new MultilineLabel(str);
/*  65 */     content.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*     */     
/*  67 */     this.areaHelpPanel = new MultilinePanel(content, this.tutorial.scrollPane);
/*     */   }
/*     */   
/*     */ 
/*     */   public void solveOned()
/*     */   {
/*  73 */     Vector par = new Vector();
/*  74 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/*     */ 
/*  77 */     par.add(new Integer(1));
/*  78 */     opr.parameters = par;
/*  79 */     opr.operation_code = 50103;
/*  80 */     this.solver.doWork(opr, this.data);
/*     */     
/*  82 */     this.onedpanel = new IORNLOneDimensionPanel(this);
/*  83 */     this.currentActionPanel = this.onedpanel;
/*     */     
/*  85 */     setHelpEnabled(true);
/*     */     
/*  87 */     this.tutorial.scrollPane.setViewportView(this.onedpanel);
/*  88 */     this.onedpanel.revalidate();
/*  89 */     this.onedpanel.repaint();
/*     */   }
/*     */   
/*     */   public void solveGradient()
/*     */   {
/*  94 */     Vector par = new Vector();
/*  95 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/*     */ 
/*  98 */     par.add(new Integer(2));
/*  99 */     opr.parameters = par;
/* 100 */     opr.operation_code = 50103;
/* 101 */     this.solver.doWork(opr, this.data);
/*     */     
/* 103 */     this.gradpanel = new IORNLGradientPanel(this);
/* 104 */     this.currentActionPanel = this.gradpanel;
/*     */     
/* 106 */     setHelpEnabled(true);
/*     */     
/* 108 */     this.tutorial.scrollPane.setViewportView(this.gradpanel);
/* 109 */     this.gradpanel.revalidate();
/* 110 */     this.gradpanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   public void solveFrankWolf()
/*     */   {
/* 116 */     Vector par = new Vector();
/* 117 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/*     */ 
/* 120 */     par.add(new Integer(3));
/* 121 */     opr.parameters = par;
/* 122 */     opr.operation_code = 50103;
/* 123 */     this.solver.doWork(opr, this.data);
/*     */     
/* 125 */     this.fwpanel = new IORNLFrankWolfPanel(this);
/* 126 */     this.currentActionPanel = this.fwpanel;
/*     */     
/* 128 */     setHelpEnabled(true);
/*     */     
/* 130 */     this.tutorial.scrollPane.setViewportView(this.fwpanel);
/* 131 */     this.fwpanel.revalidate();
/* 132 */     this.fwpanel.repaint();
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
/*     */   public void solveSUMT()
/*     */   {
/* 185 */     Vector par = new Vector();
/* 186 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/*     */ 
/* 189 */     par.add(new Integer(4));
/* 190 */     opr.parameters = par;
/* 191 */     opr.operation_code = 50103;
/* 192 */     this.solver.doWork(opr, this.data);
/*     */     
/* 194 */     this.sumtpanel = new IORNLSumtPanel(this);
/* 195 */     this.currentActionPanel = this.sumtpanel;
/*     */     
/* 197 */     setHelpEnabled(true);
/*     */     
/* 199 */     this.tutorial.scrollPane.setViewportView(this.sumtpanel);
/* 200 */     this.sumtpanel.revalidate();
/* 201 */     this.sumtpanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   public void solveAutoGradient()
/*     */   {
/* 207 */     Vector par = new Vector();
/* 208 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/*     */ 
/* 211 */     par.add(new Integer(5));
/* 212 */     opr.parameters = par;
/* 213 */     opr.operation_code = 50103;
/* 214 */     this.solver.doWork(opr, this.data);
/*     */     
/* 216 */     this.autogradpanel = new IORNLAutoGradPanel(this);
/* 217 */     this.currentActionPanel = this.autogradpanel;
/*     */     
/* 219 */     setHelpEnabled(true);
/*     */     
/* 221 */     this.tutorial.scrollPane.setViewportView(this.autogradpanel);
/* 222 */     this.autogradpanel.revalidate();
/* 223 */     this.autogradpanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   public void solveModify()
/*     */   {
/* 229 */     this.modfpanel = new IORNLModifyPanel(this);
/* 230 */     this.currentActionPanel = this.modfpanel;
/*     */     
/* 232 */     setHelpEnabled(false);
/*     */     
/* 234 */     this.tutorial.scrollPane.setViewportView(this.modfpanel);
/* 235 */     this.modfpanel.revalidate();
/* 236 */     this.modfpanel.repaint();
/*     */   }
/*     */   
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
/*     */ 
/*     */   protected void setDefaultMenuState() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void setMenuStateNLPEnterProblem()
/*     */   {
/* 266 */     this.mi_interactive_one_dimesional_seacrch_procedure.setEnabled(true);
/* 267 */     this.mi_interactive_one_dimesional_seacrch_procedure.setEnabled(true);
/* 268 */     this.mi_interactive_gradient_search_procedure.setEnabled(true);
/* 269 */     this.mi_interactive_modified_simplex_method.setEnabled(true);
/*     */   }
/*     */   
/*     */   protected void createMyMenus()
/*     */   {
/* 274 */     ButtonGroup group = new ButtonGroup();
/*     */     
/*     */ 
/* 277 */     this.mi_interactive_one_dimesional_seacrch_procedure = new JRadioButtonMenuItem("Interactive One-Dimensional Search Procedure");
/* 278 */     this.tutorial.procedure.add(this.mi_interactive_one_dimesional_seacrch_procedure);
/* 279 */     group.add(this.mi_interactive_one_dimesional_seacrch_procedure);
/* 280 */     this.mi_interactive_one_dimesional_seacrch_procedure.setMnemonic('I');
/* 281 */     this.mi_interactive_one_dimesional_seacrch_procedure.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 283 */         IORNLPProcessController.this.procedure = "Interactive One-Dimensional Search Procedure";
/* 284 */         IORNLPProcessController.this.setStatusBar();
/* 285 */         IORNLPProcessController.this.solveOned();
/* 286 */         IORNLPProcessController.this.tutorial.closeDemoController();
/*     */       }
/* 288 */     });
/* 289 */     this.mi_automatic_gradient_search_procedure = new JRadioButtonMenuItem("Automatic Gradient Search Procedure");
/* 290 */     this.tutorial.procedure.add(this.mi_automatic_gradient_search_procedure);
/* 291 */     group.add(this.mi_automatic_gradient_search_procedure);
/* 292 */     this.mi_automatic_gradient_search_procedure.setMnemonic('G');
/* 293 */     this.mi_automatic_gradient_search_procedure.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 295 */         IORNLPProcessController.this.procedure = "Automatic Gradient Search Procedure";
/* 296 */         IORNLPProcessController.this.setStatusBar();
/* 297 */         IORNLPProcessController.this.solveAutoGradient();
/* 298 */         IORNLPProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 301 */     });
/* 302 */     this.mi_interactive_gradient_search_procedure = new JRadioButtonMenuItem("Interactive Gradient Search Procedure");
/* 303 */     this.tutorial.procedure.add(this.mi_interactive_gradient_search_procedure);
/* 304 */     group.add(this.mi_interactive_gradient_search_procedure);
/* 305 */     this.mi_interactive_gradient_search_procedure.setMnemonic('P');
/* 306 */     this.mi_interactive_gradient_search_procedure.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 308 */         IORNLPProcessController.this.procedure = "Interactive Gradient Search Procedure";
/* 309 */         IORNLPProcessController.this.setStatusBar();
/* 310 */         IORNLPProcessController.this.solveGradient();
/* 311 */         IORNLPProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 314 */     });
/* 315 */     this.mi_interactive_modified_simplex_method = new JRadioButtonMenuItem("Interactive Modified Simplex Method");
/* 316 */     this.tutorial.procedure.add(this.mi_interactive_modified_simplex_method);
/* 317 */     group.add(this.mi_interactive_modified_simplex_method);
/* 318 */     this.mi_interactive_modified_simplex_method.setMnemonic('M');
/* 319 */     this.mi_interactive_modified_simplex_method.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 321 */         IORNLPProcessController.this.procedure = "Interactive Modified Simplex Method";
/* 322 */         IORNLPProcessController.this.setStatusBar();
/* 323 */         IORNLPProcessController.this.solveModify();
/* 324 */         IORNLPProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/*     */ 
/* 328 */     });
/* 329 */     this.mi_interactive_frank_Wolfe_algorithm = new JRadioButtonMenuItem("Interactive Frank-Wolfe Algorithm");
/* 330 */     this.tutorial.procedure.add(this.mi_interactive_frank_Wolfe_algorithm);
/* 331 */     group.add(this.mi_interactive_frank_Wolfe_algorithm);
/* 332 */     this.mi_interactive_frank_Wolfe_algorithm.setMnemonic('F');
/* 333 */     this.mi_interactive_frank_Wolfe_algorithm.setEnabled(true);
/* 334 */     this.mi_interactive_frank_Wolfe_algorithm.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 336 */         IORNLPProcessController.this.procedure = "Interactive Frank-Wolfe Algorithm";
/* 337 */         IORNLPProcessController.this.setStatusBar();
/* 338 */         IORNLPProcessController.this.solveFrankWolf();
/* 339 */         IORNLPProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 342 */     });
/* 343 */     this.mi_sequential_uncostrained_minimization_technique_SUMT = new JRadioButtonMenuItem("Sequential Unconstrained Minimization Technique-SUMT");
/* 344 */     this.tutorial.procedure.add(this.mi_sequential_uncostrained_minimization_technique_SUMT);
/* 345 */     group.add(this.mi_sequential_uncostrained_minimization_technique_SUMT);
/* 346 */     this.mi_sequential_uncostrained_minimization_technique_SUMT.setMnemonic('S');
/* 347 */     this.mi_sequential_uncostrained_minimization_technique_SUMT.setEnabled(true);
/* 348 */     this.mi_sequential_uncostrained_minimization_technique_SUMT.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 350 */         IORNLPProcessController.this.procedure = "Sequential Unconstrained Minimization Technique-SUMT";
/* 351 */         IORNLPProcessController.this.setStatusBar();
/* 352 */         IORNLPProcessController.this.solveSUMT();
/* 353 */         IORNLPProcessController.this.tutorial.closeDemoController();
/*     */       }
/* 355 */     });
/* 356 */     this.tutorial.option.setEnabled(false);
/*     */   }
/*     */   
/*     */ 
/* 360 */   public void saveFile() { this.tutorial.statusBar.setText("IORNLPProcessController saveFile()"); }
/*     */   
/*     */   public class NLData implements Serializable {
/* 363 */     final int MAX_NUM_VARIABLE = 3;
/* 364 */     final int MAX_NUM_ITERATION = 20;
/*     */     int num_term;
/*     */     boolean is_max;
/*     */     double[] newx;
/*     */     double[] fx;
/*     */     double[] df;
/*     */     double[] low_bound;
/*     */     double[] up_bound;
/*     */     double[] fx_coef;
/*     */     double[] df_coef;
/*     */     int[] fx_power;
/*     */     int[] df_power;
/*     */     double[] oldx1;
/*     */     double[] oldx2;
/*     */     double[] newx1;
/*     */     double[] newx2;
/*     */     double[] gradx1;
/*     */     double[] gradx2;
/* 382 */     double[] a1; double[] b1; double[] a2; double[] b2; double[] tt; double[] fx1x2_coef; double[] dfx1_coef; double[] dfx2_coef; double[] ft_coef; int[] fx_x1exp; int[] fx_x2exp; int[] dfx1_x1exp; int[] dfx1_x2exp; int[] dfx2_x1exp; int[] dfx2_x2exp; int[] ft_texp; int t_function_num_term; double errTolerance; int num_variable; int num_constraint; IORNLPProcessController.NLData.NLEquation fw_objective = new IORNLPProcessController.NLData.NLEquation();
/* 383 */     IORNLPProcessController.NLData.NLEquation[] fw_derivative = new IORNLPProcessController.NLData.NLEquation[3];
/*     */     
/*     */     double[][] fw_oldx;
/*     */     
/*     */     double[][] fw_newx;
/*     */     
/*     */     double[][] fw_a;
/*     */     
/*     */     double[][] fw_b;
/*     */     
/*     */     double[][] fw_xlp;
/*     */     
/*     */     double[][] fw_constraint_coef;
/*     */     
/*     */     int[] fw_constraint_operator;
/*     */     
/*     */     double[][] fw_flp_coef;
/*     */     
/*     */     double[][] fw_gradx;
/*     */     
/*     */     double[] fw_tt;
/*     */     
/*     */     double[][] fw_ht_coef;
/*     */     
/*     */     int[][] fw_ht_texp;
/*     */     
/*     */     int[] fw_ht_num_term;
/*     */     
/*     */     double[] Temp;
/*     */     double[][] variable;
/*     */     double[] function;
/*     */     double[] probability;
/*     */     boolean[] accept;
/*     */     int totalresult;
/*     */     int[] itertag;
/*     */     int pos;
/*     */     double bestresult;
/*     */     double[] bestvarible;
/*     */     int totalrecord;
/*     */     Chromosome[] best;
/*     */     Chromosome[] store;
/*     */     Chromosome[] children;
/*     */     int[] parentindex;
/*     */     byte[][][] bytag;
/*     */     int sumt_num_variable_with_bound;
/*     */     int sumt_num_variable_without_bound;
/*     */     int sumt_num_inequality_constraint;
/*     */     int sumt_num_equality_constraint;
/*     */     double[] sumt_oldx;
/*     */     double[] sumt_newx;
/*     */     double sumt_r;
/* 434 */     IORNLPProcessController.NLData.NLEquation sumt_objective = new IORNLPProcessController.NLData.NLEquation();
/* 435 */     IORNLPProcessController.NLData.NLEquation[] sumt_Bx = new IORNLPProcessController.NLData.NLEquation[3];
/* 436 */     IORNLPProcessController.NLData.NLEquation[] sumt_Lx = new IORNLPProcessController.NLData.NLEquation[3];
/*     */     
/*     */ 
/*     */     public NLData()
/*     */     {
/* 441 */       for (int i = 0; i < 3; i++) {
/* 442 */         this.fw_derivative[i] = new IORNLPProcessController.NLData.NLEquation();
/* 443 */         this.sumt_Bx[i] = new IORNLPProcessController.NLData.NLEquation();
/* 444 */         this.sumt_Lx[i] = new IORNLPProcessController.NLData.NLEquation();
/*     */       }
/*     */     }
/*     */     
/*     */     public class NLEquation implements Serializable {
/* 449 */       final int MAX_EQ_TERM = 10;
/*     */       int num_term;
/* 451 */       int num_var; double[] fxcoef = new double[10];
/* 452 */       int[][] xexp = new int[10][3];
/*     */       
/*     */       public NLEquation() {}
/*     */     }
/*     */   }
/*     */   
/* 458 */   protected String getArea() { return "Nonlinear Programming"; }
/*     */   
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/* 463 */     if ((this.currentActionPanel instanceof IORNLOneDimensionPanel)) {
/* 464 */       ((IORNLOneDimensionPanel)this.currentActionPanel).SaveFunction();
/*     */     }
/* 466 */     else if ((this.currentActionPanel instanceof IORNLAutoGradPanel)) {
/* 467 */       ((IORNLAutoGradPanel)this.currentActionPanel).SaveFunction();
/*     */     }
/* 469 */     else if ((this.currentActionPanel instanceof IORNLGradientPanel)) {
/* 470 */       ((IORNLGradientPanel)this.currentActionPanel).SaveFunction();
/*     */     }
/* 472 */     else if ((this.currentActionPanel instanceof IORNLFrankWolfPanel)) {
/* 473 */       ((IORNLFrankWolfPanel)this.currentActionPanel).SaveFunction();
/*     */     }
/* 475 */     else if ((this.currentActionPanel instanceof IORNLSumtPanel)) {
/* 476 */       ((IORNLSumtPanel)this.currentActionPanel).SaveFunction();
/*     */     }
/*     */     try
/*     */     {
/* 480 */       out.writeObject(this.solver);
/* 481 */       out.writeObject(this.data);
/* 482 */       out.writeObject(this.procedure);
/* 483 */       out.writeObject(this.option);
/*     */       
/* 485 */       if ((this.currentActionPanel instanceof IORNLOneDimensionPanel)) {
/* 486 */         out.writeObject("IORNLOneDimensionPanel");
/*     */       }
/* 488 */       else if ((this.currentActionPanel instanceof IORNLAutoGradPanel)) {
/* 489 */         out.writeObject("IORNLAutoGradPanel");
/*     */       }
/* 491 */       else if ((this.currentActionPanel instanceof IORNLGradientPanel)) {
/* 492 */         out.writeObject("IORNLGradientPanel");
/*     */       }
/* 494 */       else if ((this.currentActionPanel instanceof IORNLFrankWolfPanel)) {
/* 495 */         out.writeObject("IORNLFrankWolfPanel");
/*     */       }
/* 497 */       else if ((this.currentActionPanel instanceof IORNLSumtPanel)) {
/* 498 */         out.writeObject("IORNLSumtPanel");
/*     */       }
/*     */       else {
/* 501 */         System.out.println("Save file fails");
/* 502 */         return;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 506 */       System.out.println("Save file fails");
/* 507 */       return;
/*     */     }
/*     */     
/* 510 */     this.currentActionPanel.SaveFile(out);
/*     */   }
/*     */   
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/*     */     try
/*     */     {
/* 518 */       this.solver = ((ORNLPSolver)in.readObject());
/* 519 */       this.data = ((IORNLPProcessController.NLData)in.readObject());
/* 520 */       this.procedure = ((String)in.readObject());
/* 521 */       this.option = ((String)in.readObject());
/*     */       
/* 523 */       ActionPanelName = (String)in.readObject();
/*     */     }
/*     */     catch (Exception e) {
/*     */       String ActionPanelName;
/* 527 */       System.out.println("Open file fails"); return;
/*     */     }
/*     */     
/*     */     String ActionPanelName;
/*     */     
/* 532 */     setHelpEnabled(true);
/* 533 */     if (ActionPanelName.equalsIgnoreCase("IORNLOneDimensionPanel")) {
/* 534 */       this.currentActionPanel = new IORNLOneDimensionPanel(this);
/* 535 */       this.mi_interactive_one_dimesional_seacrch_procedure.setSelected(true);
/*     */     }
/* 537 */     else if (ActionPanelName.equalsIgnoreCase("IORNLAutoGradPanel")) {
/* 538 */       this.currentActionPanel = new IORNLAutoGradPanel(this);
/* 539 */       this.mi_automatic_gradient_search_procedure.setSelected(true);
/*     */     }
/* 541 */     else if (ActionPanelName.equalsIgnoreCase("IORNLGradientPanel")) {
/* 542 */       this.currentActionPanel = new IORNLGradientPanel(this);
/* 543 */       this.mi_interactive_gradient_search_procedure.setSelected(true);
/*     */     }
/* 545 */     else if (ActionPanelName.equalsIgnoreCase("IORNLFrankWolfPanel")) {
/* 546 */       this.currentActionPanel = new IORNLFrankWolfPanel(this);
/* 547 */       this.mi_interactive_frank_Wolfe_algorithm.setSelected(true);
/*     */     }
/* 549 */     else if (ActionPanelName.equalsIgnoreCase("IORNLSumtPanel")) {
/* 550 */       this.currentActionPanel = new IORNLSumtPanel(this);
/* 551 */       this.mi_sequential_uncostrained_minimization_technique_SUMT.setSelected(true);
/*     */     }
/*     */     else {
/* 554 */       setHelpEnabled(false);
/* 555 */       System.out.println("Open file fails");
/* 556 */       return;
/*     */     }
/*     */     
/* 559 */     setStatusBar();
/* 560 */     this.currentActionPanel.LoadFile(in);
/*     */     
/*     */ 
/* 563 */     this.tutorial.scrollPane.setViewportView(this.currentActionPanel);
/* 564 */     this.currentActionPanel.revalidate();
/* 565 */     this.currentActionPanel.repaint();
/*     */   }
/*     */   
/*     */   public void print()
/*     */   {
/* 570 */     int p = 1;
/* 571 */     if (this.mi_interactive_one_dimesional_seacrch_procedure.isSelected()) {
/* 572 */       p = 1;
/* 573 */     } else if (this.mi_automatic_gradient_search_procedure.isSelected()) {
/* 574 */       p = 2;
/* 575 */     } else if (this.mi_interactive_gradient_search_procedure.isSelected()) {
/* 576 */       p = 3;
/* 577 */     } else if (this.mi_interactive_modified_simplex_method.isSelected()) {
/* 578 */       p = 4;
/* 579 */     } else if (this.mi_interactive_frank_Wolfe_algorithm.isSelected()) {
/* 580 */       p = 5;
/* 581 */     } else if (this.mi_sequential_uncostrained_minimization_technique_SUMT.isSelected())
/* 582 */       p = 6;
/* 583 */     this.solver.setPrintProcedure(p);
/*     */     
/* 585 */     PrinterJob printJob = PrinterJob.getPrinterJob();
/* 586 */     printJob.setPrintable(this.solver);
/* 587 */     boolean pDialogState = true;
/* 588 */     pDialogState = printJob.printDialog();
/* 589 */     if (pDialogState) {
/*     */       try {
/* 591 */         printJob.print();
/*     */       } catch (Exception ex) {
/* 593 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void printToFile() {
/* 599 */     int p = 1;
/* 600 */     if ((this.currentActionPanel instanceof IORNLAnnealingPanel)) {
/* 601 */       p = 7;
/* 602 */     } else if (this.mi_interactive_one_dimesional_seacrch_procedure.isSelected()) {
/* 603 */       p = 1;
/* 604 */     } else if (this.mi_automatic_gradient_search_procedure.isSelected()) {
/* 605 */       p = 2;
/* 606 */     } else if (this.mi_interactive_gradient_search_procedure.isSelected()) {
/* 607 */       p = 3;
/* 608 */     } else if (this.mi_interactive_modified_simplex_method.isSelected()) {
/* 609 */       p = 4;
/* 610 */     } else if (this.mi_interactive_frank_Wolfe_algorithm.isSelected()) {
/* 611 */       p = 5;
/* 612 */     } else if (this.mi_sequential_uncostrained_minimization_technique_SUMT.isSelected()) {
/* 613 */       p = 6;
/*     */     }
/* 615 */     JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
/* 616 */     int result = fc.showSaveDialog(null);
/* 617 */     File chosenFile = fc.getSelectedFile();
/* 618 */     if ((result == 0) && (chosenFile != null)) {
/* 619 */       this.solver.PrintToFile(chosenFile.getPath(), p);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNLPProcessController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */