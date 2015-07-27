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
/*     */ public class IORLPProcessController
/*     */   extends IORProcessController
/*     */ {
/*  31 */   public static final int LP_START_ENTER_PROBLEM = 0;
/*  32 */   public static final int LP_FINISH_ENTER_PROBLEM = 10;
/*     */   
/*  34 */   public static final int LP_START_SETUP_PROBLEM = 1;
/*  35 */   public static final int LP_FINISH_SETUP_PROBLEM = 11;
/*     */   
/*  37 */   public static final int LP_START_SOLVE_PROBLEM = 2;
/*  38 */   public static final int LP_FINISH_SOLVE_PROBLEM = 12;
/*     */   
/*  40 */   public static final int LP_START_SOLVE_INTERIOR_PROBLEM = 3;
/*  41 */   public static final int LP_FINISH_SOLVE_INTERIOR_PROBLEM = 13;
/*     */   
/*  43 */   public static final int LP_START_SENSITIVITY_ANALYSIS = 4;
/*  44 */   public static final int LP_FINISH_SENSITIVITY_ANALYSIS = 14;
/*     */   
/*     */   private JRadioButtonMenuItem mi_enter_or_revise_a_general_linear_programming_mode;
/*     */   
/*     */   private JRadioButtonMenuItem mi_solve_automatically_by_the_interior_point_algorithm;
/*     */   
/*     */   private JRadioButtonMenuItem mi_set_up_for_the_simplex_method_interactive_only;
/*     */   
/*     */   private JRadioButtonMenuItem mi_solve_interactively_by_the_simplex_method;
/*     */   
/*     */   private JRadioButtonMenuItem mi_sensitivity_analysis;
/*     */   private JRadioButtonMenuItem mi_modified_simplex_method;
/*     */   private JRadioButtonMenuItem mi_solve_automatically_by_the_simplex_method;
/*     */   private JRadioButtonMenuItem graphical_method_and_sensitivity_analysis;
/*     */   private JRadioButtonMenuItem graphical_method_Interactively_and_sensitivity_analysis;
/*     */   private JRadioButtonMenuItem rbmi_display_tableau_in_algebraic_form;
/*     */   private JRadioButtonMenuItem rbmi_display_tableau_in_tabular_form;
/*     */   private JRadioButtonMenuItem rbmi_alpha_5;
/*     */   private JRadioButtonMenuItem rbmi_alpha_9;
/*  63 */   private IORLPEnterPanel enterPanel = null;
/*  64 */   private IORLPSetupPanel setupPanel = null;
/*  65 */   private IORLPInteriorPanel interpPanel = null;
/*  66 */   private IORLPSensitivePanel sensiPanel = null;
/*  67 */   private IORLPInteractPanel sinterPanel = null;
/*  68 */   private IORLPModifyPanel modiPanel = null;
/*  69 */   private IORLPAutoSolverPane autoSolvePane = null;
/*  70 */   private LinearProg linearProg = null;
/*  71 */   private IORLPLinearProgPane lpLPPane = null;
/*     */   
/*     */ 
/*  74 */   public IORLPProcessController.LPData data = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ORLPSolver solver;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORLPProcessController(IORTutorial t)
/*     */   {
/*  86 */     super(t);
/*  87 */     this.solver = new ORLPSolver();
/*     */     
/*  89 */     this.data = new IORLPProcessController.LPData();
/*     */     
/*  91 */     this.solver.getData(this.data);
/*  92 */     initializeAreaHelpPanel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeAreaHelpPanel()
/*     */   {
/*  99 */     String str = "The current area is \"General Linear Programming.\" You can select ";
/* 100 */     str = String.valueOf(String.valueOf(str)).concat("a procedure from the Procedure menu to start. For some of these procedures, ");
/* 101 */     str = String.valueOf(String.valueOf(str)).concat("a corresponding demonstration example is available in OR Tutor to help prepare you ");
/* 102 */     str = String.valueOf(String.valueOf(str)).concat("to go through the procedure for the first time.\n\n");
/* 103 */     str = String.valueOf(String.valueOf(str)).concat("The procedures under the Procedure menu must be executed in a certain order. The first ");
/* 104 */     str = String.valueOf(String.valueOf(str)).concat("procedure must precede the second, third, or seventh procedure. The third procedure must precede ");
/* 105 */     str = String.valueOf(String.valueOf(str)).concat("the fourth, and the fourth must precede the fifth. The sixth procedure will not become ");
/* 106 */     str = String.valueOf(String.valueOf(str)).concat("relevant until you begin using the Nonlinear Programming Area, and instructions for this procedure ");
/* 107 */     str = String.valueOf(String.valueOf(str)).concat("are given under the Procedure menu for that area.");
/* 108 */     str = String.valueOf(String.valueOf(str)).concat("\n\n");
/* 109 */     str = String.valueOf(String.valueOf(str)).concat("If you would like to choose an area other than \"General Linear Programming\" within which ");
/* 110 */     str = String.valueOf(String.valueOf(str)).concat("to work, select the Area menu.\n");
/*     */     
/* 112 */     MultilineLabel content = new MultilineLabel(str);
/* 113 */     content.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*     */     
/* 115 */     this.areaHelpPanel = new MultilinePanel(content, this.tutorial.scrollPane);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMenuState(int currentStep)
/*     */   {
/* 124 */     switch (currentStep)
/*     */     {
/*     */     case 0: 
/* 127 */       setMenuStateLPStartEnterProblem();
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
/* 164 */       break;
/*     */     case 10: 
/* 130 */       setMenuStateLPFinishEnterProblem();
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
/* 164 */       break;
/*     */     case 1: 
/* 134 */       setMenuStateLPStartSetupProblem();
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
/* 164 */       break;
/*     */     case 11: 
/* 137 */       setMenuStateLPFinishSetupProblem();
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
/* 164 */       break;
/*     */     case 2: 
/* 141 */       setMenuStateLPStartSolveProblem();
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
/* 164 */       break;
/*     */     case 12: 
/* 144 */       setMenuStateLPFinishSolveProblem();
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
/* 164 */       break;
/*     */     case 3: 
/* 148 */       setMenuStateLPStartSolveInteriorProblem();
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
/* 164 */       break;
/*     */     case 13: 
/* 151 */       setMenuStateLPFinishSolveInteriorProblem();
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
/* 164 */       break;
/*     */     case 4: 
/* 155 */       setMenuStateLPStartAnalyzeProblem();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 164 */       break;
/*     */     case 14: 
/* 158 */       setMenuStateLPFinishAnalyzeProblem();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 164 */       break; }
/*     */   }
/*     */   
/*     */   private void setMenuStateLPStartEnterProblem() {
/* 168 */     this.mi_enter_or_revise_a_general_linear_programming_mode.setEnabled(true);
/* 169 */     this.mi_solve_automatically_by_the_interior_point_algorithm.setEnabled(false);
/* 170 */     this.mi_set_up_for_the_simplex_method_interactive_only.setEnabled(false);
/* 171 */     this.mi_solve_interactively_by_the_simplex_method.setEnabled(false);
/* 172 */     this.mi_sensitivity_analysis.setEnabled(false);
/* 173 */     this.mi_modified_simplex_method.setEnabled(false);
/* 174 */     this.mi_solve_automatically_by_the_simplex_method.setEnabled(false);
/* 175 */     this.graphical_method_and_sensitivity_analysis.setEnabled(true);
/*     */   }
/*     */   
/*     */ 
/*     */   private void setMenuStateLPFinishEnterProblem()
/*     */   {
/* 181 */     this.mi_enter_or_revise_a_general_linear_programming_mode.setEnabled(true);
/* 182 */     this.mi_solve_automatically_by_the_interior_point_algorithm.setEnabled(true);
/* 183 */     this.mi_set_up_for_the_simplex_method_interactive_only.setEnabled(true);
/* 184 */     this.mi_solve_automatically_by_the_simplex_method.setEnabled(true);
/* 185 */     this.graphical_method_and_sensitivity_analysis.setEnabled(true);
/* 186 */     this.graphical_method_Interactively_and_sensitivity_analysis.setEnabled(true);
/*     */     
/*     */ 
/*     */ 
/* 190 */     setAlgebraicFormEnabled(true);
/*     */   }
/*     */   
/*     */   private void setMenuStateLPFinishSetupProblem()
/*     */   {
/* 195 */     this.mi_solve_interactively_by_the_simplex_method.setEnabled(true);
/* 196 */     this.mi_modified_simplex_method.setEnabled(true);
/*     */   }
/*     */   
/*     */   private void setMenuStateLPStartSetupProblem()
/*     */   {
/* 201 */     this.mi_solve_interactively_by_the_simplex_method.setEnabled(false);
/* 202 */     this.mi_modified_simplex_method.setEnabled(false);
/* 203 */     setAlgebraicFormEnabled(true);
/*     */   }
/*     */   
/*     */   private void setMenuStateLPStartSolveProblem()
/*     */   {
/* 208 */     this.mi_sensitivity_analysis.setEnabled(false);
/* 209 */     setAlgebraicFormEnabled(true);
/*     */   }
/*     */   
/*     */   private void setAlgebraicFormEnabled(boolean b)
/*     */   {
/* 214 */     this.rbmi_display_tableau_in_algebraic_form.setEnabled(b);
/*     */   }
/*     */   
/*     */   private void setMenuStateLPFinishSolveProblem()
/*     */   {
/* 219 */     boolean hasArtificial = false;
/* 220 */     int N = this.data.num_variable;
/* 221 */     for (int i = 1; i <= N; i++) {
/* 222 */       if (this.data.is_artificial[i] != 0) {
/* 223 */         hasArtificial = true;
/* 224 */         break;
/*     */       }
/*     */     }
/*     */     
/* 228 */     if ((!this.data.is_big_M) && (hasArtificial)) {
/* 229 */       this.mi_sensitivity_analysis.setEnabled(false);
/*     */     } else {
/* 231 */       this.mi_sensitivity_analysis.setEnabled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setMenuStateLPStartSolveInteriorProblem() {
/* 236 */     this.mi_solve_automatically_by_the_interior_point_algorithm.setEnabled(false);
/*     */   }
/*     */   
/*     */   private void setMenuStateLPFinishSolveInteriorProblem()
/*     */   {
/* 241 */     this.mi_solve_automatically_by_the_interior_point_algorithm.setEnabled(true);
/*     */   }
/*     */   
/*     */   private void setMenuStateLPStartAnalyzeProblem()
/*     */   {
/* 246 */     setAlgebraicFormEnabled(false);
/*     */   }
/*     */   
/*     */   private void setMenuStateLPFinishAnalyzeProblem()
/*     */   {
/* 251 */     setAlgebraicFormEnabled(true);
/*     */   }
/*     */   
/*     */   private void enterProblem()
/*     */   {
/* 256 */     if (this.enterPanel == null) {
/* 257 */       this.enterPanel = new IORLPEnterPanel(this);
/*     */     }
/*     */     
/* 260 */     setHelpEnabled(true);
/*     */     
/* 262 */     this.currentActionPanel = this.enterPanel;
/* 263 */     this.tutorial.scrollPane.setViewportView(this.enterPanel);
/*     */     
/* 265 */     setMenuState(0);
/*     */     
/* 267 */     this.enterPanel.setPanelEditable(true);
/* 268 */     this.enterPanel.setFinishEnabled(true);
/*     */     
/* 270 */     this.enterPanel.revalidate();
/* 271 */     this.enterPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void setupProblem()
/*     */   {
/* 278 */     IOROperation op = new IOROperation(10211, null);
/* 279 */     this.solver.doWork(op, this.data);
/*     */     
/* 281 */     this.solver.setBookmark();
/* 282 */     this.solver.getData(this.data);
/*     */     
/* 284 */     this.setupPanel = new IORLPSetupPanel(this);
/*     */     
/* 286 */     this.currentActionPanel = this.setupPanel;
/* 287 */     this.setupPanel.updatePanel();
/* 288 */     this.tutorial.scrollPane.setViewportView(this.setupPanel);
/*     */     
/* 290 */     setMenuState(1);
/*     */     
/* 292 */     this.setupPanel.setPanelEditable(true);
/*     */     
/* 294 */     this.setupPanel.revalidate();
/* 295 */     this.setupPanel.repaint();
/*     */   }
/*     */   
/*     */   private void solvebyInteriorPoint()
/*     */   {
/* 300 */     this.solver.resetIP(this.data);
/* 301 */     this.interpPanel = new IORLPInteriorPanel(this);
/*     */     
/* 303 */     this.currentActionPanel = this.interpPanel;
/* 304 */     this.tutorial.scrollPane.setViewportView(this.interpPanel);
/*     */     
/* 306 */     this.interpPanel.setFinishEnabled(true);
/* 307 */     this.interpPanel.revalidate();
/* 308 */     this.interpPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void SensitivityAnalysis()
/*     */   {
/* 315 */     this.solver.setBookmarkSens();
/* 316 */     this.solver.getData(this.data);
/*     */     
/* 318 */     this.sensiPanel = new IORLPSensitivePanel(this);
/* 319 */     this.sensiPanel.updatePanel();
/*     */     
/* 321 */     this.currentActionPanel = this.sensiPanel;
/* 322 */     this.tutorial.scrollPane.setViewportView(this.sensiPanel);
/*     */     
/* 324 */     setMenuState(4);
/* 325 */     this.sensiPanel.setFinishEnabled(true);
/* 326 */     this.sensiPanel.revalidate();
/* 327 */     this.sensiPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void SolveInteractive()
/*     */   {
/* 334 */     IOROperation op = new IOROperation(10407, null);
/* 335 */     this.solver.doWork(op, this.data);
/*     */     
/* 337 */     this.solver.setBookmark();
/* 338 */     this.solver.getData(this.data);
/*     */     
/* 340 */     this.sinterPanel = new IORLPInteractPanel(this);
/* 341 */     this.sinterPanel.updatePanel();
/*     */     
/* 343 */     this.currentActionPanel = this.sinterPanel;
/* 344 */     this.tutorial.scrollPane.setViewportView(this.sinterPanel);
/*     */     
/* 346 */     setMenuState(2);
/* 347 */     setMenuState(12);
/* 348 */     this.sinterPanel.setFinishEnabled(true);
/* 349 */     this.sinterPanel.revalidate();
/* 350 */     this.sinterPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   private void SolveModify()
/*     */   {
/* 356 */     IOROperation op = new IOROperation(10407, null);
/* 357 */     this.solver.doWork(op, this.data);
/*     */     
/* 359 */     this.solver.setBookmark();
/* 360 */     this.solver.getData(this.data);
/*     */     
/* 362 */     this.modiPanel = new IORLPModifyPanel(this);
/* 363 */     this.modiPanel.updatePanel();
/*     */     
/* 365 */     this.currentActionPanel = this.modiPanel;
/* 366 */     this.tutorial.scrollPane.setViewportView(this.modiPanel);
/*     */     
/* 368 */     setMenuState(2);
/* 369 */     setMenuState(12);
/* 370 */     this.modiPanel.setFinishEnabled(true);
/* 371 */     this.modiPanel.revalidate();
/* 372 */     this.modiPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setDefaultMenuState()
/*     */   {
/* 380 */     setMenuStateLPStartEnterProblem();
/*     */   }
/*     */   
/*     */   private void solveAutoProblem()
/*     */   {
/* 385 */     this.autoSolvePane = new IORLPAutoSolverPane(this);
/* 386 */     this.currentActionPanel = this.autoSolvePane;
/* 387 */     this.currentActionPanel.actionStatus.setText(" ");
/*     */     
/* 389 */     this.tutorial.scrollPane.setViewportView(this.autoSolvePane);
/* 390 */     this.autoSolvePane.setFinishEnabled(true);
/* 391 */     this.autoSolvePane.updatePanel();
/*     */   }
/*     */   
/*     */   public void solveGraphicalSensitivityProblem() {
/* 395 */     this.linearProg = new LinearProg(this);
/* 396 */     this.currentActionPanel = this.linearProg;
/* 397 */     this.currentActionPanel.actionStatus.setText(" ");
/*     */     
/* 399 */     this.tutorial.scrollPane.setViewportView(this.linearProg);
/* 400 */     this.linearProg.setFinishEnabled(true);
/* 401 */     this.linearProg.updatePanel();
/*     */   }
/*     */   
/*     */   public void solveInteractiveLyGraphicalProblem() {
/* 405 */     this.lpLPPane = new IORLPLinearProgPane(this);
/* 406 */     this.currentActionPanel = this.lpLPPane;
/* 407 */     this.currentActionPanel.actionStatus.setText(" ");
/*     */     
/* 409 */     this.tutorial.scrollPane.setViewportView(this.lpLPPane);
/* 410 */     this.lpLPPane.setFinishEnabled(true);
/* 411 */     this.lpLPPane.updatePanel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void createMyMenus()
/*     */   {
/* 421 */     ButtonGroup group = new ButtonGroup();
/*     */     
/*     */ 
/* 424 */     this.mi_enter_or_revise_a_general_linear_programming_mode = new JRadioButtonMenuItem("Enter or Revise a General Linear Programming Model");
/* 425 */     this.tutorial.procedure.add(this.mi_enter_or_revise_a_general_linear_programming_mode);
/* 426 */     group.add(this.mi_enter_or_revise_a_general_linear_programming_mode);
/* 427 */     this.mi_enter_or_revise_a_general_linear_programming_mode.setMnemonic('E');
/* 428 */     this.mi_enter_or_revise_a_general_linear_programming_mode.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 430 */         IORLPProcessController.this.procedure = "Enter or Revise a General Linear Programming Model";
/* 431 */         IORLPProcessController.this.enterProblem();
/* 432 */         IORLPProcessController.this.setStatusBar();
/* 433 */         IORLPProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 436 */     });
/* 437 */     this.mi_solve_automatically_by_the_interior_point_algorithm = new JRadioButtonMenuItem("Solve Automatically by the Interior Point Algorithm");
/* 438 */     this.tutorial.procedure.add(this.mi_solve_automatically_by_the_interior_point_algorithm);
/* 439 */     group.add(this.mi_solve_automatically_by_the_interior_point_algorithm);
/* 440 */     this.mi_solve_automatically_by_the_interior_point_algorithm.setMnemonic('A');
/* 441 */     this.mi_solve_automatically_by_the_interior_point_algorithm.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 443 */         IORLPProcessController.this.procedure = "Solve Automatically by the Interior Point Algorithm";
/* 444 */         IORLPProcessController.this.setStatusBar();
/* 445 */         IORLPProcessController.this.tutorial.closeDemoController();
/* 446 */         IORLPProcessController.this.solvebyInteriorPoint();
/*     */       }
/* 448 */     });
/* 449 */     this.mi_set_up_for_the_simplex_method_interactive_only = new JRadioButtonMenuItem("Set Up for the Simplex Method--Interactive only");
/* 450 */     this.tutorial.procedure.add(this.mi_set_up_for_the_simplex_method_interactive_only);
/* 451 */     group.add(this.mi_set_up_for_the_simplex_method_interactive_only);
/* 452 */     this.mi_set_up_for_the_simplex_method_interactive_only.setMnemonic('U');
/* 453 */     this.mi_set_up_for_the_simplex_method_interactive_only.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 455 */         IORLPProcessController.this.procedure = "Set Up for the Simplex Method--Interactive only";
/* 456 */         IORLPProcessController.this.setStatusBar();
/* 457 */         IORLPProcessController.this.tutorial.closeDemoController();
/* 458 */         IORLPProcessController.this.setupProblem();
/*     */       }
/* 460 */     });
/* 461 */     this.mi_solve_interactively_by_the_simplex_method = new JRadioButtonMenuItem("Solve Interactively by the Simplex Method");
/* 462 */     this.tutorial.procedure.add(this.mi_solve_interactively_by_the_simplex_method);
/* 463 */     group.add(this.mi_solve_interactively_by_the_simplex_method);
/* 464 */     this.mi_solve_interactively_by_the_simplex_method.setMnemonic('I');
/* 465 */     this.mi_solve_interactively_by_the_simplex_method.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 467 */         IORLPProcessController.this.procedure = "Solve Interactively by the Simplex Method";
/* 468 */         IORLPProcessController.this.setStatusBar();
/* 469 */         IORLPProcessController.this.tutorial.closeDemoController();
/* 470 */         IORLPProcessController.this.SolveInteractive();
/*     */       }
/* 472 */     });
/* 473 */     this.mi_sensitivity_analysis = new JRadioButtonMenuItem("Sensitivity Analysis");
/* 474 */     this.tutorial.procedure.add(this.mi_sensitivity_analysis);
/* 475 */     group.add(this.mi_sensitivity_analysis);
/* 476 */     this.mi_sensitivity_analysis.setMnemonic('S');
/* 477 */     this.mi_sensitivity_analysis.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 479 */         IORLPProcessController.this.procedure = "Sensitivity Analysis";
/* 480 */         IORLPProcessController.this.setStatusBar();
/* 481 */         IORLPProcessController.this.tutorial.closeDemoController();
/* 482 */         IORLPProcessController.this.SensitivityAnalysis();
/*     */       }
/* 484 */     });
/* 485 */     this.mi_modified_simplex_method = new JRadioButtonMenuItem("Modified Simplex Method");
/* 486 */     this.tutorial.procedure.add(this.mi_modified_simplex_method);
/* 487 */     group.add(this.mi_modified_simplex_method);
/* 488 */     this.mi_modified_simplex_method.setMnemonic('M');
/* 489 */     this.mi_modified_simplex_method.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 491 */         IORLPProcessController.this.procedure = "Modified Simplex Method";
/* 492 */         IORLPProcessController.this.setStatusBar();
/* 493 */         IORLPProcessController.this.SolveModify();
/* 494 */         IORLPProcessController.this.tutorial.closeDemoController();
/* 495 */         IORLPProcessController.this.solver.doWork(new IOROperation(10409, null));
/*     */       }
/* 497 */     });
/* 498 */     this.mi_solve_automatically_by_the_simplex_method = new JRadioButtonMenuItem("Solve Automatically by the Simplex Method");
/* 499 */     this.tutorial.procedure.add(this.mi_solve_automatically_by_the_simplex_method);
/* 500 */     group.add(this.mi_solve_automatically_by_the_simplex_method);
/* 501 */     this.mi_solve_automatically_by_the_simplex_method.setMnemonic('A');
/* 502 */     this.mi_solve_automatically_by_the_simplex_method.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 504 */         IORLPProcessController.this.procedure = "Solve Automatically by the Simplex Method";
/* 505 */         IORLPProcessController.this.setStatusBar();
/* 506 */         IORLPProcessController.this.tutorial.closeDemoController();
/* 507 */         IORLPProcessController.this.solver.doWork(new IOROperation(10523, null), IORLPProcessController.this.data);
/* 508 */         IORLPProcessController.this.solveAutoProblem();
/*     */       }
/*     */       
/* 511 */     });
/* 512 */     this.graphical_method_and_sensitivity_analysis = new JRadioButtonMenuItem("Graphical Method and Sensitivity Analysis");
/*     */     
/* 514 */     this.tutorial.procedure.add(this.graphical_method_and_sensitivity_analysis);
/* 515 */     group.add(this.graphical_method_and_sensitivity_analysis);
/* 516 */     this.graphical_method_and_sensitivity_analysis.setMnemonic('G');
/* 517 */     this.graphical_method_and_sensitivity_analysis.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 519 */         IORLPProcessController.this.procedure = "Graphical Method and Sensitivity Analysis";
/* 520 */         IORLPProcessController.this.setStatusBar();
/* 521 */         IORLPProcessController.this.tutorial.closeDemoController();
/*     */         
/* 523 */         IORLPProcessController.this.solveGraphicalSensitivityProblem();
/* 524 */         IORLPProcessController.this.setHelpEnabled(true);
/*     */       }
/*     */       
/*     */ 
/* 528 */     });
/* 529 */     this.graphical_method_Interactively_and_sensitivity_analysis = new JRadioButtonMenuItem("Solve Interactively by the Graphical Method");
/* 530 */     this.tutorial.procedure.add(this.graphical_method_Interactively_and_sensitivity_analysis);
/* 531 */     group.add(this.graphical_method_Interactively_and_sensitivity_analysis);
/* 532 */     this.graphical_method_Interactively_and_sensitivity_analysis.setMnemonic('I');
/* 533 */     this.graphical_method_Interactively_and_sensitivity_analysis.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 535 */         IORLPProcessController.this.procedure = "Solve Interactively by the Graphical Method";
/* 536 */         IORLPProcessController.this.setStatusBar();
/* 537 */         IORLPProcessController.this.tutorial.closeDemoController();
/*     */         
/* 539 */         IORLPProcessController.this.solveInteractiveLyGraphicalProblem();
/* 540 */         IORLPProcessController.this.setHelpEnabled(true);
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 546 */     });
/* 547 */     this.tutorial.option.setEnabled(true);
/* 548 */     ButtonGroup formgroup = new ButtonGroup();
/*     */     
/* 550 */     this.rbmi_display_tableau_in_algebraic_form = new JRadioButtonMenuItem("Display the Model in Algebraic Form");
/* 551 */     this.rbmi_display_tableau_in_algebraic_form.setMnemonic('D');
/* 552 */     this.rbmi_display_tableau_in_algebraic_form.setMnemonic(68);
/* 553 */     this.rbmi_display_tableau_in_algebraic_form.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 555 */         Vector par = new Vector();
/* 556 */         IOROperation opr = new IOROperation(1, par);
/* 557 */         IORLPProcessController.this.data.DisplayForm = 1;
/* 558 */         IORLPProcessController.this.option = "Algebraic Form";
/* 559 */         if (IORLPProcessController.this.sinterPanel != null) {
/* 560 */           IORLPProcessController.this.sinterPanel.switchForm(1);
/*     */         }
/* 562 */         if (IORLPProcessController.this.modiPanel != null) {
/* 563 */           IORLPProcessController.this.modiPanel.switchForm(1);
/*     */         }
/* 565 */         if ((IORLPProcessController.this.currentActionPanel instanceof IORLPArtificialPanel)) {
/* 566 */           ((IORLPArtificialPanel)IORLPProcessController.this.currentActionPanel).switchForm(1);
/*     */         }
/* 568 */         IORLPProcessController.this.setStatusBar();
/*     */       }
/* 570 */     });
/* 571 */     formgroup.add(this.rbmi_display_tableau_in_algebraic_form);
/* 572 */     this.tutorial.option.add(this.rbmi_display_tableau_in_algebraic_form);
/*     */     
/*     */ 
/* 575 */     this.rbmi_display_tableau_in_tabular_form = new JRadioButtonMenuItem("Display the Model in Tabular Form");
/* 576 */     this.rbmi_display_tableau_in_tabular_form.setSelected(true);
/* 577 */     this.option = "Tabular Form";
/* 578 */     this.rbmi_display_tableau_in_tabular_form.setMnemonic('P');
/* 579 */     this.rbmi_display_tableau_in_tabular_form.setMnemonic(80);
/* 580 */     this.rbmi_display_tableau_in_tabular_form.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 582 */         Vector par = new Vector();
/* 583 */         IOROperation opr = new IOROperation(1, par);
/* 584 */         IORLPProcessController.this.data.DisplayForm = 2;
/* 585 */         IORLPProcessController.this.option = "Tabular Form";
/* 586 */         if (IORLPProcessController.this.sinterPanel != null) {
/* 587 */           IORLPProcessController.this.sinterPanel.switchForm(2);
/*     */         }
/* 589 */         if (IORLPProcessController.this.modiPanel != null) {
/* 590 */           IORLPProcessController.this.modiPanel.switchForm(2);
/*     */         }
/* 592 */         if ((IORLPProcessController.this.currentActionPanel instanceof IORLPArtificialPanel)) {
/* 593 */           ((IORLPArtificialPanel)IORLPProcessController.this.currentActionPanel).switchForm(2);
/*     */         }
/* 595 */         IORLPProcessController.this.setStatusBar();
/*     */       }
/* 597 */     });
/* 598 */     formgroup.add(this.rbmi_display_tableau_in_tabular_form);
/* 599 */     this.tutorial.option.add(this.rbmi_display_tableau_in_tabular_form);
/* 600 */     this.tutorial.option.addSeparator();
/*     */     
/* 602 */     ButtonGroup group2 = new ButtonGroup();
/* 603 */     this.rbmi_alpha_5 = new JRadioButtonMenuItem("Alpha = 0.5");
/* 604 */     this.rbmi_alpha_5.setSelected(true);
/*     */     
/* 606 */     setStatusBar();
/* 607 */     this.rbmi_alpha_5.setMnemonic('A');
/* 608 */     this.rbmi_alpha_5.setMnemonic(65);
/* 609 */     this.rbmi_alpha_5.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 611 */         Vector par = new Vector();
/* 612 */         IOROperation opr = new IOROperation(1, par);
/* 613 */         double alpha = 0.5D;
/* 614 */         par.addElement(new Double(alpha));
/* 615 */         opr.operation_code = 10303;
/* 616 */         opr.parameters = par;
/* 617 */         IORLPProcessController.this.solver.doIPWork(opr, IORLPProcessController.this.data);
/*     */         
/* 619 */         IORLPProcessController.this.setStatusBar();
/*     */       }
/* 621 */     });
/* 622 */     group2.add(this.rbmi_alpha_5);
/* 623 */     this.tutorial.option.add(this.rbmi_alpha_5);
/*     */     
/*     */ 
/*     */ 
/* 627 */     this.rbmi_alpha_9 = new JRadioButtonMenuItem("Alpha = 0.9");
/* 628 */     this.rbmi_alpha_9.setMnemonic('p');
/* 629 */     this.rbmi_alpha_9.setMnemonic(80);
/* 630 */     this.rbmi_alpha_9.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 632 */         Vector par = new Vector();
/* 633 */         IOROperation opr = new IOROperation(1, par);
/* 634 */         double alpha = 0.9D;
/* 635 */         par.addElement(new Double(alpha));
/* 636 */         opr.operation_code = 10303;
/* 637 */         opr.parameters = par;
/* 638 */         IORLPProcessController.this.solver.doIPWork(opr, IORLPProcessController.this.data);
/*     */         
/* 640 */         IORLPProcessController.this.setStatusBar();
/* 641 */       } });
/* 642 */     group2.add(this.rbmi_alpha_9);
/* 643 */     this.tutorial.option.add(this.rbmi_alpha_9);
/*     */   }
/*     */   
/*     */ 
/*     */   public static class LPData
/*     */     implements Serializable
/*     */   {
/*     */     public int max_num_variable;
/*     */     
/*     */     public int max_num_constrain;
/*     */     
/*     */     public int num_original_variable;
/*     */     
/*     */     public int num_variable;
/*     */     
/*     */     public int num_constrain;
/*     */     
/*     */     public boolean original_objective_is_max;
/*     */     
/*     */     public boolean is_big_M;
/*     */     
/*     */     public boolean objective_is_max;
/*     */     
/*     */     public double[] objective_coefficient;
/*     */     
/*     */     public double[] objective_M_coefficient;
/*     */     
/*     */     public double[][] constrain_coefficient;
/*     */     
/*     */     public int[] constrain_operator;
/*     */     
/*     */     public boolean[] bound_operator;
/*     */     public double[] bound;
/*     */     public int[] basic_variable_index;
/*     */     public int[] first_basic_variable_index;
/*     */     public boolean[] is_artificial;
/*     */     public double Alpha;
/* 680 */     public int DisplayForm = 2;
/*     */     
/*     */ 
/*     */ 
/*     */     public double[] first_objective_coefficient;
/*     */     
/*     */ 
/*     */     public double[] first_objective_M_coefficient;
/*     */     
/*     */ 
/*     */     public double[][] first_constrain_coefficient;
/*     */     
/*     */ 
/* 693 */     public Vector variableValeV = null;
/*     */     
/*     */ 
/* 696 */     public Vector SlackV = null;
/* 697 */     public Vector ShadowPriceV = null;
/*     */     
/* 699 */     public double[] minArr = null;
/* 700 */     public double[] maxArr = null;
/* 701 */     public double[] minRHSArr = null;
/* 702 */     public double[] maxRHSArr = null;
/* 703 */     public double[] coefficientsArr = null;
/* 704 */     public double[] RHSCurrentValueArr = null;
/* 705 */     public String outputInfo = null;
/* 706 */     public boolean showResult = false;
/*     */     
/* 708 */     public String[][] printTable1Data = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected String getArea()
/*     */   {
/* 715 */     return "General Linear Programming";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/*     */     try
/*     */     {
/* 725 */       if ((this.currentActionPanel instanceof IORLPEnterPanel)) {
/* 726 */         ((IORLPEnterPanel)this.currentActionPanel).SaveEnterProblem();
/* 727 */         this.solver.getData(this.data);
/*     */       }
/* 729 */       out.writeObject(this.solver);
/* 730 */       out.writeObject(this.data);
/* 731 */       out.writeObject(this.procedure);
/* 732 */       out.writeObject(this.option);
/* 733 */       out.writeObject(new Boolean(this.mi_enter_or_revise_a_general_linear_programming_mode.isEnabled()));
/* 734 */       out.writeObject(new Boolean(this.mi_solve_automatically_by_the_interior_point_algorithm.isEnabled()));
/* 735 */       out.writeObject(new Boolean(this.mi_set_up_for_the_simplex_method_interactive_only.isEnabled()));
/* 736 */       out.writeObject(new Boolean(this.mi_solve_interactively_by_the_simplex_method.isEnabled()));
/* 737 */       out.writeObject(new Boolean(this.mi_sensitivity_analysis.isEnabled()));
/* 738 */       out.writeObject(new Boolean(this.mi_modified_simplex_method.isEnabled()));
/* 739 */       if ((this.mi_modified_simplex_method.isEnabled()) && (this.mi_modified_simplex_method.isSelected())) {
/* 740 */         out.writeObject(new Boolean(true));
/*     */       } else {
/* 742 */         out.writeObject(new Boolean(false));
/*     */       }
/* 744 */       out.writeObject(new Boolean(this.rbmi_display_tableau_in_algebraic_form.isSelected()));
/* 745 */       out.writeObject(new Boolean(this.rbmi_alpha_5.isSelected()));
/*     */       
/* 747 */       if ((this.currentActionPanel instanceof IORLPEnterPanel)) {
/* 748 */         out.writeObject("IORLPEnterPanel");
/*     */       }
/* 750 */       else if ((this.currentActionPanel instanceof IORLPSetupPanel)) {
/* 751 */         out.writeObject("IORLPSetupPanel");
/*     */       }
/* 753 */       else if ((this.currentActionPanel instanceof IORLPArtificialPanel)) {
/* 754 */         out.writeObject("IORLPArtificialPanel");
/*     */       }
/* 756 */       else if ((this.currentActionPanel instanceof IORLPInteractPanel)) {
/* 757 */         out.writeObject("IORLPInteractPanel");
/*     */       }
/* 759 */       else if ((this.currentActionPanel instanceof IORLPInteriorPanel)) {
/* 760 */         out.writeObject("IORLPInteriorPanel");
/*     */       }
/* 762 */       else if ((this.currentActionPanel instanceof IORLPSensitivePanel)) {
/* 763 */         out.writeObject("IORLPSensitivePanel");
/*     */       }
/* 765 */       else if ((this.currentActionPanel instanceof IORLPModifyPanel)) {
/* 766 */         out.writeObject("IORLPModifyPanel");
/*     */       }
/* 768 */       else if ((this.currentActionPanel instanceof IORLPAutoSolverPane)) {
/* 769 */         out.writeObject("IORLPAutoSolverPane");
/*     */       }
/* 771 */       else if ((this.currentActionPanel instanceof LinearProg)) {
/* 772 */         out.writeObject("LinearProg");
/* 773 */       } else if ((this.currentActionPanel instanceof IORLPLinearProgPane)) {
/* 774 */         out.writeObject("IORLPLinearProgPane");
/*     */       }
/*     */       else {
/* 777 */         System.out.println("Save file fails");
/* 778 */         return;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 782 */       e.printStackTrace();
/* 783 */       System.out.println("Save file fails");
/* 784 */       return;
/*     */     }
/*     */     
/* 787 */     this.currentActionPanel.SaveFile(out);
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
/* 800 */       this.solver = ((ORLPSolver)in.readObject());
/* 801 */       this.data = ((IORLPProcessController.LPData)in.readObject());
/* 802 */       this.procedure = ((String)in.readObject());
/* 803 */       this.option = ((String)in.readObject());
/* 804 */       this.mi_enter_or_revise_a_general_linear_programming_mode.setEnabled(((Boolean)in.readObject()).booleanValue());
/* 805 */       this.mi_solve_automatically_by_the_interior_point_algorithm.setEnabled(((Boolean)in.readObject()).booleanValue());
/* 806 */       this.mi_set_up_for_the_simplex_method_interactive_only.setEnabled(((Boolean)in.readObject()).booleanValue());
/* 807 */       this.mi_solve_interactively_by_the_simplex_method.setEnabled(((Boolean)in.readObject()).booleanValue());
/* 808 */       this.mi_sensitivity_analysis.setEnabled(((Boolean)in.readObject()).booleanValue());
/* 809 */       this.mi_modified_simplex_method.setEnabled(((Boolean)in.readObject()).booleanValue());
/* 810 */       if (this.mi_modified_simplex_method.isEnabled()) {
/* 811 */         this.mi_modified_simplex_method.setSelected(((Boolean)in.readObject()).booleanValue());
/*     */       } else {
/* 813 */         in.readObject();
/*     */       }
/* 815 */       if (((Boolean)in.readObject()).booleanValue()) {
/* 816 */         this.rbmi_display_tableau_in_algebraic_form.setSelected(true);
/*     */       } else
/* 818 */         this.rbmi_display_tableau_in_tabular_form.setSelected(true);
/* 819 */       if (((Boolean)in.readObject()).booleanValue()) {
/* 820 */         this.rbmi_alpha_5.setSelected(true);
/*     */       } else {
/* 822 */         this.rbmi_alpha_9.setSelected(true);
/*     */       }
/* 824 */       ActionPanelName = (String)in.readObject();
/*     */     }
/*     */     catch (Exception e) {
/*     */       String ActionPanelName;
/* 828 */       System.out.println("Open file fails"); return;
/*     */     }
/*     */     
/*     */     String ActionPanelName;
/*     */     
/* 833 */     setHelpEnabled(true);
/*     */     
/* 835 */     if (ActionPanelName.equalsIgnoreCase("IORLPEnterPanel")) {
/* 836 */       this.currentActionPanel = new IORLPEnterPanel(this);
/* 837 */       this.mi_enter_or_revise_a_general_linear_programming_mode.setSelected(true);
/*     */     }
/* 839 */     else if (ActionPanelName.equalsIgnoreCase("IORLPSetupPanel")) {
/* 840 */       this.currentActionPanel = new IORLPSetupPanel(this);
/* 841 */       this.mi_set_up_for_the_simplex_method_interactive_only.setSelected(true);
/*     */     }
/* 843 */     else if (ActionPanelName.equalsIgnoreCase("IORLPArtificialPanel")) {
/* 844 */       this.currentActionPanel = new IORLPArtificialPanel(this);
/* 845 */       this.mi_set_up_for_the_simplex_method_interactive_only.setSelected(true);
/*     */     }
/* 847 */     else if (ActionPanelName.equalsIgnoreCase("IORLPInteractPanel")) {
/* 848 */       this.sinterPanel = new IORLPInteractPanel(this);
/* 849 */       this.currentActionPanel = this.sinterPanel;
/* 850 */       this.mi_solve_interactively_by_the_simplex_method.setSelected(true);
/*     */     }
/* 852 */     else if (ActionPanelName.equalsIgnoreCase("IORLPInteriorPanel"))
/*     */     {
/* 854 */       this.currentActionPanel = new IORLPInteriorPanel(this);
/* 855 */       this.mi_solve_automatically_by_the_interior_point_algorithm.setSelected(true);
/*     */     }
/* 857 */     else if (ActionPanelName.equalsIgnoreCase("IORLPSensitivePanel")) {
/* 858 */       this.currentActionPanel = new IORLPSensitivePanel(this);
/* 859 */       this.mi_sensitivity_analysis.setSelected(true);
/*     */     }
/* 861 */     else if (ActionPanelName.equalsIgnoreCase("IORLPModifyPanel")) {
/* 862 */       this.modiPanel = new IORLPModifyPanel(this);
/* 863 */       this.currentActionPanel = this.modiPanel;
/* 864 */       this.mi_modified_simplex_method.setSelected(true);
/*     */     }
/* 866 */     else if (ActionPanelName.equalsIgnoreCase("IORLPAutoSolverPane")) {
/* 867 */       this.currentActionPanel = new IORLPAutoSolverPane(this);
/* 868 */       this.mi_modified_simplex_method.setSelected(true);
/*     */     }
/* 870 */     else if (ActionPanelName.equalsIgnoreCase("LinearProg")) {
/* 871 */       this.currentActionPanel = new LinearProg(this);
/* 872 */       this.graphical_method_and_sensitivity_analysis.setSelected(true);
/* 873 */     } else if (ActionPanelName.equalsIgnoreCase("IORLPLinearProgPane")) {
/* 874 */       this.currentActionPanel = new IORLPLinearProgPane(this);
/* 875 */       this.graphical_method_Interactively_and_sensitivity_analysis.setSelected(true);
/*     */     }
/*     */     else
/*     */     {
/* 879 */       setHelpEnabled(false);
/* 880 */       System.out.println("Open file fails");
/* 881 */       return;
/*     */     }
/*     */     
/* 884 */     setStatusBar();
/* 885 */     this.currentActionPanel.LoadFile(in);
/*     */     
/*     */ 
/* 888 */     if (this.currentActionPanel instanceof IORLPSetupPanel == false)
/* 889 */       this.tutorial.scrollPane.setViewportView(this.currentActionPanel);
/* 890 */     this.currentActionPanel.revalidate();
/* 891 */     this.currentActionPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void print()
/*     */   {
/* 899 */     int p = 1;
/* 900 */     if (this.mi_enter_or_revise_a_general_linear_programming_mode.isSelected()) {
/* 901 */       p = 1;
/* 902 */     } else if ((this.mi_solve_automatically_by_the_interior_point_algorithm.isEnabled()) && (this.mi_solve_automatically_by_the_interior_point_algorithm.isSelected())) {
/* 903 */       p = 2;
/* 904 */     } else if ((this.mi_set_up_for_the_simplex_method_interactive_only.isEnabled()) && (this.mi_set_up_for_the_simplex_method_interactive_only.isSelected())) {
/* 905 */       p = 3;
/* 906 */     } else if ((this.mi_solve_interactively_by_the_simplex_method.isEnabled()) && (this.mi_solve_interactively_by_the_simplex_method.isSelected())) {
/* 907 */       p = 4;
/* 908 */     } else if ((this.mi_sensitivity_analysis.isEnabled()) && (this.mi_sensitivity_analysis.isSelected())) {
/* 909 */       p = 5;
/* 910 */     } else if ((this.mi_modified_simplex_method.isEnabled()) && (this.mi_modified_simplex_method.isSelected())) {
/* 911 */       p = 6;
/* 912 */     } else if ((this.mi_solve_automatically_by_the_simplex_method.isEnabled()) && (this.mi_solve_automatically_by_the_simplex_method.isSelected())) {
/* 913 */       p = 7;
/* 914 */     } else if ((this.graphical_method_and_sensitivity_analysis.isEnabled()) && (this.graphical_method_and_sensitivity_analysis.isSelected()))
/*     */     {
/* 916 */       if (this.currentActionPanel instanceof LinearProg == true) {
/* 917 */         this.currentActionPanel.setprintData();
/* 918 */         p = 8;
/*     */       } else {
/* 920 */         System.out.println("print to file linearProg fails");
/*     */       }
/*     */     }
/* 923 */     else if ((this.graphical_method_Interactively_and_sensitivity_analysis.isEnabled()) && (this.graphical_method_Interactively_and_sensitivity_analysis.isSelected())) {
/* 924 */       if (this.currentActionPanel instanceof IORLPLinearProgPane == true) {
/* 925 */         this.currentActionPanel.setprintData();
/* 926 */         p = 9;
/*     */       } else {
/* 928 */         System.out.println("print to file linearProg fails");
/* 929 */         return;
/*     */       }
/*     */     }
/*     */     
/* 933 */     this.solver.setEqForm(this.data.DisplayForm - 1);
/* 934 */     this.solver.setPrintProcedure(p);
/*     */     
/* 936 */     PrinterJob printJob = PrinterJob.getPrinterJob();
/* 937 */     printJob.setPrintable(this.solver);
/* 938 */     boolean pDialogState = true;
/* 939 */     pDialogState = printJob.printDialog();
/* 940 */     if (pDialogState) {
/*     */       try {
/* 942 */         printJob.print();
/*     */       } catch (Exception ex) {
/* 944 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void printToFile()
/*     */   {
/* 953 */     int p = 1;
/* 954 */     if (this.mi_enter_or_revise_a_general_linear_programming_mode.isSelected()) {
/* 955 */       p = 1;
/* 956 */     } else if ((this.mi_solve_automatically_by_the_interior_point_algorithm.isEnabled()) && (this.mi_solve_automatically_by_the_interior_point_algorithm.isSelected())) {
/* 957 */       p = 2;
/* 958 */     } else if ((this.mi_set_up_for_the_simplex_method_interactive_only.isEnabled()) && (this.mi_set_up_for_the_simplex_method_interactive_only.isSelected())) {
/* 959 */       p = 3;
/* 960 */     } else if ((this.mi_solve_interactively_by_the_simplex_method.isEnabled()) && (this.mi_solve_interactively_by_the_simplex_method.isSelected())) {
/* 961 */       p = 4;
/* 962 */     } else if ((this.mi_sensitivity_analysis.isEnabled()) && (this.mi_sensitivity_analysis.isSelected())) {
/* 963 */       p = 5;
/* 964 */     } else if ((this.mi_modified_simplex_method.isEnabled()) && (this.mi_modified_simplex_method.isSelected())) {
/* 965 */       p = 6;
/* 966 */     } else if ((this.mi_solve_automatically_by_the_simplex_method.isEnabled()) && (this.mi_solve_automatically_by_the_simplex_method.isSelected())) {
/* 967 */       p = 7;
/* 968 */     } else if ((this.graphical_method_and_sensitivity_analysis.isEnabled()) && (this.graphical_method_and_sensitivity_analysis.isSelected())) {
/* 969 */       if (this.currentActionPanel instanceof LinearProg == true) {
/* 970 */         this.currentActionPanel.setprintData();
/* 971 */         p = 8;
/*     */       } else {
/* 973 */         System.out.println("print to file linearProg fails");
/*     */       }
/*     */     }
/* 976 */     else if ((this.graphical_method_Interactively_and_sensitivity_analysis.isEnabled()) && (this.graphical_method_Interactively_and_sensitivity_analysis.isSelected())) {
/* 977 */       if (this.currentActionPanel instanceof IORLPLinearProgPane == true) {
/* 978 */         this.currentActionPanel.setprintData();
/* 979 */         p = 9;
/*     */       } else {
/* 981 */         System.out.println("print to file linearProg fails");
/* 982 */         return;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 987 */     this.solver.setEqForm(this.data.DisplayForm - 1);
/*     */     
/* 989 */     JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
/* 990 */     int result = fc.showSaveDialog(null);
/* 991 */     File chosenFile = fc.getSelectedFile();
/* 992 */     if ((result == 0) && (chosenFile != null)) {
/* 993 */       this.solver.PrintToFile(chosenFile.getPath(), p);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPProcessController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */