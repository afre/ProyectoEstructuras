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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORTPProcessController
/*     */   extends IORProcessController
/*     */ {
/*  35 */   public static final int METHOD_NORTHWEST = 0;
/*     */   
/*  37 */   public static final int METHOD_VOGEL = 1;
/*     */   
/*  39 */   public static final int METHOD_RUSSELL = 2;
/*     */   
/*     */   private JRadioButtonMenuItem mi_enter_or_revise_a_transportation_problem;
/*     */   
/*     */   private JRadioButtonMenuItem mi_find_initial_basic_feasible_solution_for_interactive_method;
/*     */   
/*     */   private JRadioButtonMenuItem mi_sovle_interactively_by_the_transportation_simplex_method;
/*     */   
/*     */   private JRadioButtonMenuItem mi_sovle_automaticlly_by_the_transportation_simplex_method;
/*     */   
/*     */   private JRadioButtonMenuItem mi_northwest_corner_rule_Other;
/*     */   
/*     */   private JRadioButtonMenuItem mi_vogels_method;
/*     */   private JRadioButtonMenuItem mi_russells_method;
/*  53 */   private ButtonGroup option_group = null;
/*     */   
/*     */ 
/*  56 */   public IORTPProcessController.TPData data = null;
/*     */   
/*     */ 
/*     */   public ORTPSolver solver;
/*     */   
/*  61 */   private IORTPEnterPanel enterPanel = null;
/*  62 */   private IORTPFindFeasiblePanel findFeasiblePanel = null;
/*  63 */   private IORTPSolvePanel solvePanel = null;
/*  64 */   private IORTPAutoSolverPane autoSolvePane = null;
/*     */   
/*     */ 
/*  67 */   public static final int TP_START_ENTER_PROBLEM = 0;
/*  68 */   public static final int TP_FINISH_ENTER_PROBLEM = 10;
/*     */   
/*  70 */   public static final int TP_START_FIND_FEASIBLE = 1;
/*  71 */   public static final int TP_FINISH_FIND_FEASIBLE = 11;
/*     */   
/*  73 */   public static final int TP_START_SOLVE_PROBLEM = 2;
/*  74 */   public static final int TP_FINISH_SOLVE_PROBLEM = 21;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORTPProcessController(IORTutorial t)
/*     */   {
/*  83 */     super(t);
/*  84 */     this.solver = new ORTPSolver();
/*     */     
/*  86 */     this.data = new IORTPProcessController.TPData();
/*     */     
/*     */ 
/*  89 */     this.solver.getData(this.data);
/*     */     
/*  91 */     initializeAreaHelpPanel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeAreaHelpPanel()
/*     */   {
/*  99 */     String str = "The current area is \"The Transportation Problem.\" You can select ";
/*     */     
/* 101 */     str = String.valueOf(String.valueOf(str)).concat("a procedure from the Procedure menu to start. Before executing these ");
/*     */     
/* 103 */     str = String.valueOf(String.valueOf(str)).concat("procedures for the first time, we recommend that you go through the ");
/*     */     
/* 105 */     str = String.valueOf(String.valueOf(str)).concat("demonstration example entitled The Transportation Problem in OR Tutor.");
/*     */     
/* 107 */     str = String.valueOf(String.valueOf(str)).concat("\n\n");
/* 108 */     str = String.valueOf(String.valueOf(str)).concat("If you would like to choose an area other than \"The Transportation Problem\" within which ");
/* 109 */     str = String.valueOf(String.valueOf(str)).concat("to work, select the Area menu.\n");
/*     */     
/* 111 */     MultilineLabel content = new MultilineLabel(str);
/* 112 */     content.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*     */     
/* 114 */     this.areaHelpPanel = new MultilinePanel(content, this.tutorial.scrollPane);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setMenuState(int currentStep)
/*     */   {
/* 122 */     switch (currentStep) {
/*     */     case 0: 
/* 124 */       setMenuStateTPStartEnterProblem();
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
/* 138 */       break;
/*     */     case 10: 
/* 127 */       setMenuStateTPFinishEnterProblem();
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
/* 138 */       break;
/*     */     case 1: 
/* 130 */       setMenuStateTPStartFindFeasibleProblem();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 138 */       break;
/*     */     case 11: 
/* 133 */       setMenuStateTPFinishFindFeasibleProblem();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 138 */       break; }
/*     */   }
/*     */   
/*     */   private void setMenuStateTPStartEnterProblem() {
/* 142 */     this.mi_enter_or_revise_a_transportation_problem.setEnabled(true);
/* 143 */     this.mi_find_initial_basic_feasible_solution_for_interactive_method.setEnabled(false);
/*     */     
/* 145 */     this.mi_sovle_interactively_by_the_transportation_simplex_method.setEnabled(false);
/* 146 */     this.mi_sovle_automaticlly_by_the_transportation_simplex_method.setEnabled(false);
/*     */     
/* 148 */     this.tutorial.option.setEnabled(false);
/*     */   }
/*     */   
/*     */ 
/*     */   private void setMenuStateTPFinishEnterProblem()
/*     */   {
/* 154 */     this.mi_find_initial_basic_feasible_solution_for_interactive_method.setEnabled(true);
/*     */     
/* 156 */     this.mi_sovle_automaticlly_by_the_transportation_simplex_method.setEnabled(true);
/*     */   }
/*     */   
/*     */ 
/*     */   private void setMenuStateTPStartFindFeasibleProblem()
/*     */   {
/* 162 */     this.tutorial.option.setEnabled(true);
/*     */   }
/*     */   
/*     */ 
/*     */   private void setMenuStateTPFinishFindFeasibleProblem()
/*     */   {
/* 168 */     this.mi_sovle_interactively_by_the_transportation_simplex_method.setEnabled(true);
/* 169 */     this.mi_sovle_automaticlly_by_the_transportation_simplex_method.setEnabled(true);
/* 170 */     this.tutorial.option.setEnabled(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void setDefaultMenuState()
/*     */   {
/* 177 */     setMenuStateTPStartEnterProblem();
/*     */   }
/*     */   
/*     */   private void enterProblem()
/*     */   {
/* 182 */     if (this.enterPanel == null) {
/* 183 */       this.enterPanel = new IORTPEnterPanel(this);
/*     */     }
/*     */     
/* 186 */     this.currentActionPanel = this.enterPanel;
/* 187 */     this.currentActionPanel.actionStatus.setText(" ");
/* 188 */     this.enterPanel.setPanelEnable(true);
/* 189 */     setHelpEnabled(true);
/*     */     
/* 191 */     this.tutorial.scrollPane.setViewportView(this.enterPanel);
/* 192 */     setMenuState(0);
/* 193 */     this.enterPanel.setFinishEnabled(true);
/*     */     
/* 195 */     this.enterPanel.revalidate();
/* 196 */     this.enterPanel.updatePanel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void findBasicFeasible()
/*     */   {
/* 206 */     IOROperation op = new IOROperation(20200, null);
/*     */     
/* 208 */     this.solver.doWork(op, this.data);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 213 */     this.findFeasiblePanel = new IORTPFindFeasiblePanel(this);
/*     */     
/* 215 */     this.currentActionPanel = this.findFeasiblePanel;
/* 216 */     this.currentActionPanel.actionStatus.setText(" ");
/*     */     
/*     */ 
/* 219 */     this.tutorial.scrollPane.setViewportView(this.findFeasiblePanel);
/* 220 */     setMenuState(1);
/* 221 */     this.findFeasiblePanel.setFinishEnabled(true);
/* 222 */     this.findFeasiblePanel.setInitFocus();
/*     */     
/* 224 */     this.findFeasiblePanel.revalidate();
/* 225 */     this.findFeasiblePanel.updatePanel();
/*     */   }
/*     */   
/*     */   private void solveProblem()
/*     */   {
/* 230 */     IOROperation op = new IOROperation(20300, null);
/*     */     
/* 232 */     this.solver.doWork(op, this.data);
/*     */     
/* 234 */     this.solvePanel = new IORTPSolvePanel(this);
/* 235 */     this.currentActionPanel = this.solvePanel;
/* 236 */     this.currentActionPanel.actionStatus.setText(" ");
/*     */     
/* 238 */     this.tutorial.scrollPane.setViewportView(this.solvePanel);
/* 239 */     setMenuState(2);
/* 240 */     this.solvePanel.setFinishEnabled(true);
/* 241 */     this.solvePanel.setInitFocus();
/*     */     
/* 243 */     this.solvePanel.setEnterUV(true);
/* 244 */     this.solvePanel.revalidate();
/* 245 */     this.solvePanel.updatePanel();
/*     */   }
/*     */   
/*     */   private void solveAutoProblem() {
/* 249 */     IOROperation op = new IOROperation(20310, null);
/*     */     
/* 251 */     this.solver.doWork(op, this.data);
/*     */     
/* 253 */     this.autoSolvePane = new IORTPAutoSolverPane(this);
/* 254 */     this.currentActionPanel = this.autoSolvePane;
/* 255 */     this.currentActionPanel.actionStatus.setText(" ");
/*     */     
/*     */ 
/* 258 */     this.tutorial.scrollPane.setViewportView(this.autoSolvePane);
/*     */     
/* 260 */     this.autoSolvePane.setFinishEnabled(true);
/*     */     
/*     */ 
/* 263 */     this.autoSolvePane.updatePanel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void createMyMenus()
/*     */   {
/* 270 */     ButtonGroup group = new ButtonGroup();
/*     */     
/*     */ 
/* 273 */     this.mi_enter_or_revise_a_transportation_problem = new JRadioButtonMenuItem("Enter or Revise a Transportation Problem");
/*     */     
/* 275 */     this.tutorial.procedure.add(this.mi_enter_or_revise_a_transportation_problem);
/* 276 */     group.add(this.mi_enter_or_revise_a_transportation_problem);
/* 277 */     this.mi_enter_or_revise_a_transportation_problem.setMnemonic('E');
/* 278 */     this.mi_enter_or_revise_a_transportation_problem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 281 */         IORTPProcessController.this.procedure = "Enter or Revise a Transportation Problem";
/* 282 */         IORTPProcessController.this.setStatusBar();
/* 283 */         IORTPProcessController.this.enterProblem();
/* 284 */         IORTPProcessController.this.tutorial.closeDemoController();
/*     */ 
/*     */       }
/*     */       
/*     */ 
/* 289 */     });
/* 290 */     this.mi_find_initial_basic_feasible_solution_for_interactive_method = new JRadioButtonMenuItem("Find Initial Basic Feasible Solution--for Interactive Method");
/*     */     
/* 292 */     this.tutorial.procedure.add(this.mi_find_initial_basic_feasible_solution_for_interactive_method);
/*     */     
/* 294 */     group.add(this.mi_find_initial_basic_feasible_solution_for_interactive_method);
/*     */     
/* 296 */     this.mi_find_initial_basic_feasible_solution_for_interactive_method.setMnemonic('F');
/*     */     
/* 298 */     this.mi_find_initial_basic_feasible_solution_for_interactive_method.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 301 */         IORTPProcessController.this.procedure = "Find Initial Basic Feasible Solution--for Interactive Method";
/* 302 */         IORTPProcessController.this.setStatusBar();
/* 303 */         IORTPProcessController.this.findBasicFeasible();
/* 304 */         IORTPProcessController.this.tutorial.closeDemoController();
/*     */ 
/*     */       }
/*     */       
/*     */ 
/* 309 */     });
/* 310 */     this.mi_sovle_interactively_by_the_transportation_simplex_method = new JRadioButtonMenuItem("Solve Interactively by the Transportation Simplex Method");
/*     */     
/* 312 */     this.tutorial.procedure.add(this.mi_sovle_interactively_by_the_transportation_simplex_method);
/*     */     
/* 314 */     group.add(this.mi_sovle_interactively_by_the_transportation_simplex_method);
/* 315 */     this.mi_sovle_interactively_by_the_transportation_simplex_method.setMnemonic('S');
/*     */     
/* 317 */     this.mi_sovle_interactively_by_the_transportation_simplex_method.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 320 */         IORTPProcessController.this.procedure = "Solve Interactively by the Transportation Simplex Method";
/* 321 */         IORTPProcessController.this.setStatusBar();
/* 322 */         IORTPProcessController.this.solveProblem();
/* 323 */         IORTPProcessController.this.tutorial.closeDemoController();
/*     */ 
/*     */       }
/*     */       
/*     */ 
/* 328 */     });
/* 329 */     this.mi_sovle_automaticlly_by_the_transportation_simplex_method = new JRadioButtonMenuItem("Solve Automatically by the Transportation Simplex Method");
/*     */     
/* 331 */     this.tutorial.procedure.add(this.mi_sovle_automaticlly_by_the_transportation_simplex_method);
/*     */     
/* 333 */     group.add(this.mi_sovle_automaticlly_by_the_transportation_simplex_method);
/*     */     
/* 335 */     this.mi_sovle_automaticlly_by_the_transportation_simplex_method.setMnemonic('A');
/*     */     
/* 337 */     this.mi_sovle_automaticlly_by_the_transportation_simplex_method.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 340 */         IORTPProcessController.this.procedure = "Solve Automatically by the Transportation Simplex Method";
/* 341 */         IORTPProcessController.this.setStatusBar();
/* 342 */         IORTPProcessController.this.solveAutoProblem();
/* 343 */         IORTPProcessController.this.tutorial.closeDemoController();
/* 344 */         IORTPProcessController.this.setHelpEnabled(true);
/*     */       }
/*     */       
/*     */ 
/* 348 */     });
/* 349 */     this.tutorial.option.setEnabled(false);
/* 350 */     this.option_group = new ButtonGroup();
/*     */     
/*     */ 
/* 353 */     this.mi_northwest_corner_rule_Other = new JRadioButtonMenuItem("Northwest Corner Rule/Other ");
/* 354 */     this.option_group.add(this.mi_northwest_corner_rule_Other);
/* 355 */     this.tutorial.option.add(this.mi_northwest_corner_rule_Other);
/* 356 */     this.mi_northwest_corner_rule_Other.setMnemonic('N');
/* 357 */     this.mi_northwest_corner_rule_Other.setEnabled(true);
/*     */     
/* 359 */     this.mi_northwest_corner_rule_Other.setSelected(true);
/* 360 */     this.option = "Northwest Corner Rule/Other ";
/*     */     
/*     */ 
/* 363 */     this.mi_northwest_corner_rule_Other.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 365 */         IORTPProcessController.this.option = "Northwest Corner Rule/Other ";
/* 366 */         IORTPProcessController.this.setStatusBar();
/* 367 */         IORTPProcessController.this.setOption(0);
/* 368 */         IORTPProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/*     */ 
/* 372 */     });
/* 373 */     this.mi_vogels_method = new JRadioButtonMenuItem("Vogel's Method");
/* 374 */     this.option_group.add(this.mi_vogels_method);
/* 375 */     this.tutorial.option.add(this.mi_vogels_method);
/* 376 */     this.mi_vogels_method.setMnemonic('V');
/* 377 */     this.mi_vogels_method.setEnabled(true);
/* 378 */     this.mi_vogels_method.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 380 */         IORTPProcessController.this.option = "Vogel's Method";
/* 381 */         IORTPProcessController.this.setStatusBar();
/* 382 */         IORTPProcessController.this.setOption(1);
/* 383 */         IORTPProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/*     */ 
/* 387 */     });
/* 388 */     this.mi_russells_method = new JRadioButtonMenuItem("Russell's Method ");
/* 389 */     this.option_group.add(this.mi_russells_method);
/* 390 */     this.tutorial.option.add(this.mi_russells_method);
/* 391 */     this.mi_russells_method.setMnemonic('R');
/* 392 */     this.mi_russells_method.setEnabled(true);
/* 393 */     this.mi_russells_method.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 395 */         IORTPProcessController.this.option = "Russell's Method ";
/* 396 */         IORTPProcessController.this.setStatusBar();
/* 397 */         IORTPProcessController.this.setOption(2);
/* 398 */         IORTPProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 401 */     });
/* 402 */     this.mi_northwest_corner_rule_Other.setSelected(true);
/* 403 */     setOption(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void setOption(int option)
/*     */   {
/* 410 */     Vector p = new Vector();
/*     */     
/* 412 */     p.addElement(new Integer(option));
/*     */     
/* 414 */     IOROperation operation = new IOROperation(20102, p);
/*     */     
/*     */ 
/* 417 */     this.solver.doWork(operation);
/*     */     
/*     */ 
/* 420 */     if (this.currentActionPanel != null) {
/* 421 */       this.currentActionPanel.updatePanel();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public int getOption()
/*     */   {
/* 428 */     return this.data.method_option;
/*     */   }
/*     */   
/*     */ 
/*     */   public static class TPData
/*     */     implements Serializable
/*     */   {
/*     */     public int method_option;
/*     */     
/*     */     public int max_num_supply;
/*     */     
/*     */     public int max_num_demand;
/*     */     
/*     */     public int num_supply;
/*     */     
/*     */     public int num_demand;
/*     */     
/*     */     public double[][] cost;
/*     */     
/*     */     public double[][] M_cost;
/*     */     
/*     */     public int[] supply;
/*     */     
/*     */     public int[] demand;
/*     */     
/*     */     public double[] remain_supply;
/*     */     
/*     */     public double[] remain_demand;
/*     */     
/*     */     public double[] u;
/*     */     
/*     */     public double[] M_u;
/*     */     
/*     */     public double[] v;
/*     */     
/*     */     public double[] M_v;
/*     */     
/*     */     public boolean[][] is_basic;
/*     */     
/*     */     public boolean[][] is_entering_basic;
/*     */     
/*     */     public double[][] flow;
/*     */     
/*     */     public double[][] cuv;
/*     */     
/*     */     public double[][] M_cuv;
/*     */     
/*     */     public boolean[][] is_deleted;
/*     */     
/*     */     public Vector loop;
/*     */     
/*     */     public double z;
/*     */     
/*     */     public double M_z;
/*     */     
/*     */     public Vector outputV;
/*     */     
/* 485 */     public boolean auto_ShowResult = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected String getArea()
/*     */   {
/* 492 */     return "The Transportation Problem";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/* 501 */     if ((this.currentActionPanel instanceof IORTPEnterPanel)) {
/* 502 */       ((IORTPEnterPanel)this.currentActionPanel).SaveProblem();
/*     */     }
/*     */     try {
/* 505 */       out.writeObject(this.solver);
/* 506 */       out.writeObject(this.data);
/* 507 */       out.writeObject(this.procedure);
/* 508 */       out.writeObject(this.option);
/*     */       
/* 510 */       if ((this.currentActionPanel instanceof IORTPEnterPanel)) {
/* 511 */         out.writeObject("IORTPEnterPanel");
/*     */       }
/* 513 */       else if ((this.currentActionPanel instanceof IORTPFindFeasiblePanel)) {
/* 514 */         out.writeObject("IORTPFindFeasiblePanel");
/*     */       }
/* 516 */       else if ((this.currentActionPanel instanceof IORTPSolvePanel)) {
/* 517 */         out.writeObject("IORTPSolvePanel");
/*     */       }
/* 519 */       else if ((this.currentActionPanel instanceof IORTPAutoSolverPane)) {
/* 520 */         out.writeObject("IORTPAutoSolverPane");
/*     */       }
/*     */       else
/*     */       {
/* 524 */         System.out.println("Save file fails");
/* 525 */         return;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 529 */       System.out.println("Save file fails");
/* 530 */       return;
/*     */     }
/*     */     
/* 533 */     this.currentActionPanel.SaveFile(out);
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
/* 546 */       this.solver = ((ORTPSolver)in.readObject());
/* 547 */       this.data = ((IORTPProcessController.TPData)in.readObject());
/* 548 */       this.procedure = ((String)in.readObject());
/* 549 */       this.option = ((String)in.readObject());
/*     */       
/* 551 */       ActionPanelName = (String)in.readObject();
/*     */     }
/*     */     catch (Exception e) {
/*     */       String ActionPanelName;
/* 555 */       System.out.println("Open file fails"); return;
/*     */     }
/*     */     
/*     */     String ActionPanelName;
/*     */     
/* 560 */     this.mi_enter_or_revise_a_transportation_problem.setEnabled(true);
/* 561 */     this.mi_find_initial_basic_feasible_solution_for_interactive_method.setEnabled(false);
/*     */     
/* 563 */     this.mi_sovle_interactively_by_the_transportation_simplex_method.setEnabled(false);
/* 564 */     this.tutorial.option.setEnabled(true);
/*     */     
/* 566 */     setHelpEnabled(true);
/* 567 */     if (ActionPanelName.equalsIgnoreCase("IORTPEnterPanel")) {
/* 568 */       this.solver.getData(this.data);
/* 569 */       this.currentActionPanel = new IORTPEnterPanel(this);
/* 570 */       this.mi_enter_or_revise_a_transportation_problem.setSelected(true);
/* 571 */       this.tutorial.option.setEnabled(false);
/*     */     }
/* 573 */     else if (ActionPanelName.equalsIgnoreCase("IORTPFindFeasiblePanel")) {
/* 574 */       this.currentActionPanel = new IORTPFindFeasiblePanel(this);
/* 575 */       this.mi_find_initial_basic_feasible_solution_for_interactive_method.setEnabled(true);
/*     */       
/* 577 */       this.mi_find_initial_basic_feasible_solution_for_interactive_method.setSelected(true);
/*     */ 
/*     */     }
/* 580 */     else if (ActionPanelName.equalsIgnoreCase("IORTPSolvePanel")) {
/* 581 */       this.currentActionPanel = new IORTPSolvePanel(this);
/* 582 */       this.mi_find_initial_basic_feasible_solution_for_interactive_method.setEnabled(true);
/*     */       
/* 584 */       this.mi_sovle_interactively_by_the_transportation_simplex_method.setEnabled(true);
/*     */       
/* 586 */       this.mi_sovle_interactively_by_the_transportation_simplex_method.setSelected(true);
/*     */ 
/*     */     }
/* 589 */     else if (ActionPanelName.equalsIgnoreCase("IORTPAutoSolverPane")) {
/* 590 */       this.currentActionPanel = new IORTPAutoSolverPane(this);
/* 591 */       this.mi_sovle_automaticlly_by_the_transportation_simplex_method.setEnabled(true);
/*     */       
/* 593 */       this.mi_sovle_automaticlly_by_the_transportation_simplex_method.setSelected(true);
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 598 */       setHelpEnabled(false);
/* 599 */       System.out.println("Open file fails");
/* 600 */       return;
/*     */     }
/*     */     
/* 603 */     if (this.tutorial.option.isEnabled()) {
/* 604 */       if (this.data.method_option == 0) {
/* 605 */         this.mi_northwest_corner_rule_Other.setSelected(true);
/* 606 */       } else if (this.data.method_option == 1) {
/* 607 */         this.mi_vogels_method.setSelected(true);
/*     */       } else {
/* 609 */         this.mi_russells_method.setSelected(true);
/*     */       }
/*     */     }
/* 612 */     setStatusBar();
/* 613 */     this.currentActionPanel.LoadFile(in);
/*     */     
/*     */ 
/* 616 */     this.tutorial.scrollPane.setViewportView(this.currentActionPanel);
/* 617 */     this.currentActionPanel.revalidate();
/* 618 */     this.currentActionPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void print()
/*     */   {
/* 625 */     int p = 1;
/* 626 */     if (this.mi_enter_or_revise_a_transportation_problem.isSelected()) {
/* 627 */       p = 1;
/* 628 */     } else if ((this.mi_find_initial_basic_feasible_solution_for_interactive_method.isEnabled()) && (this.mi_find_initial_basic_feasible_solution_for_interactive_method.isSelected()))
/*     */     {
/*     */ 
/*     */ 
/* 632 */       p = 2;
/* 633 */     } else if ((this.mi_sovle_interactively_by_the_transportation_simplex_method.isEnabled()) && (this.mi_sovle_interactively_by_the_transportation_simplex_method.isSelected()))
/*     */     {
/*     */ 
/*     */ 
/* 637 */       p = 3;
/* 638 */     } else if ((this.mi_sovle_automaticlly_by_the_transportation_simplex_method.isEnabled()) && (this.mi_sovle_automaticlly_by_the_transportation_simplex_method.isSelected()))
/*     */     {
/*     */ 
/*     */ 
/* 642 */       p = 4; }
/* 643 */     this.solver.setPrintProcedure(p);
/*     */     
/* 645 */     PrinterJob printJob = PrinterJob.getPrinterJob();
/* 646 */     printJob.setPrintable(this.solver);
/* 647 */     boolean pDialogState = true;
/* 648 */     pDialogState = printJob.printDialog();
/* 649 */     if (pDialogState) {
/*     */       try {
/* 651 */         printJob.print();
/*     */       }
/*     */       catch (Exception ex) {
/* 654 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void printToFile()
/*     */   {
/* 663 */     int p = 1;
/* 664 */     if (this.mi_enter_or_revise_a_transportation_problem.isSelected()) {
/* 665 */       p = 1;
/* 666 */     } else if ((this.mi_find_initial_basic_feasible_solution_for_interactive_method.isEnabled()) && (this.mi_find_initial_basic_feasible_solution_for_interactive_method.isSelected()))
/*     */     {
/*     */ 
/*     */ 
/* 670 */       p = 2;
/* 671 */     } else if ((this.mi_sovle_interactively_by_the_transportation_simplex_method.isEnabled()) && (this.mi_sovle_interactively_by_the_transportation_simplex_method.isSelected()))
/*     */     {
/*     */ 
/*     */ 
/* 675 */       p = 3;
/* 676 */     } else if ((this.mi_sovle_automaticlly_by_the_transportation_simplex_method.isEnabled()) && (this.mi_sovle_automaticlly_by_the_transportation_simplex_method.isSelected()))
/*     */     {
/*     */ 
/*     */ 
/* 680 */       p = 4;
/*     */     }
/* 682 */     JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
/* 683 */     int result = fc.showSaveDialog(null);
/* 684 */     File chosenFile = fc.getSelectedFile();
/* 685 */     if ((result == 0) && (chosenFile != null)) {
/* 686 */       this.solver.PrintToFile(chosenFile.getPath(), p);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTPProcessController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */