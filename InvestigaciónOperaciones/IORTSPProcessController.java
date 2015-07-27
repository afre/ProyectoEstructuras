/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.io.File;
/*      */ import java.io.ObjectInput;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutput;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Vector;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JRadioButtonMenuItem;
/*      */ import javax.swing.JScrollPane;
/*      */ 
/*      */ public class IORTSPProcessController extends IORProcessController
/*      */ {
/*      */   public IORTutorial iort;
/*   22 */   public static final int TABU_SEARCH = 0;
/*   23 */   public static final int SIMULATE_ANEALING = 1;
/*      */   
/*      */ 
/*      */   private JRadioButtonMenuItem tabu_search;
/*      */   
/*      */ 
/*      */   private JRadioButtonMenuItem simulated_annealing;
/*      */   
/*      */ 
/*      */   private JRadioButtonMenuItem genetic_algorithm;
/*      */   
/*   34 */   public ButtonGroup option_group = null;
/*      */   
/*      */   private JRadioButtonMenuItem mi_tsp_problems;
/*      */   private JRadioButtonMenuItem np_problems;
/*      */   private JRadioButtonMenuItem inp_problems;
/*   39 */   public IORTSPProcessController.NLData nldata = null;
/*   40 */   public IORTSPProcessController.TSPData data = null;
/*      */   
/*      */   public ORTSPSolver solver;
/*   43 */   private IORTSPEnterPanel enterPanel = null;
/*   44 */   private IORTSPEnterPanel1 enterPanel1 = null;
/*   45 */   private IORTSPTabuSearchPanel tabuSearchPanel = null;
/*   46 */   private IORSimulateAnealingPanel simulateAnealingPanel = null;
/*   47 */   private IORTSPGeneticAlgorithmPanel geneticAlgorithmPanel = null;
/*      */   
/*   49 */   protected IORNLGeneticPanel gepanel = null;
/*   50 */   protected IORNLAnnealingPanel apanel = null;
/*      */   
/*   52 */   protected IORMInfoPanel infopanel = null;
/*      */   
/*      */ 
/*   55 */   public static final int TSP_START_ENTER_PROBLEM = 0;
/*   56 */   public static final int TSP_FINISH_ENTER_PROBLEM = 10;
/*      */   
/*   58 */   public static final int TSP_START_TABU_SEARCH = 1;
/*   59 */   public static final int TSP_FINISH_TABU_SEARCH = 11;
/*      */   
/*   61 */   public static final int TSP_START_SIMULSTED_ANNEALING = 2;
/*   62 */   public static final int TSP_FINISH_SIMULSTED_ANNEALING = 21;
/*      */   
/*   64 */   public static final int TSP_START_GENETIC_ANNEALING = 3;
/*   65 */   public static final int TSP_FINISH_GENETIC_ANNEALING = 31;
/*      */   
/*   67 */   final String str1 = "Tabu Search";
/*   68 */   final String strn2 = "Simulated Annealing";
/*   69 */   final String strn3 = "Genetic Algorithm";
/*      */   private String ActionPanelName;
/*      */   
/*      */   public IORTSPProcessController(IORTutorial t) {
/*   73 */     super(t);
/*   74 */     this.solver = new ORTSPSolver();
/*   75 */     this.data = new IORTSPProcessController.TSPData();
/*   76 */     this.nldata = new IORTSPProcessController.NLData();
/*   77 */     initializeAreaHelpPanel();
/*   78 */     this.iort = t;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void initializeAreaHelpPanel()
/*      */   {
/*   86 */     String str = "The current area is \"Metaheuristics.\" You can select ";
/*      */     
/*   88 */     str = String.valueOf(String.valueOf(str)).concat("a procedure from the Procedure menu to start. Before executing these ");
/*      */     
/*   90 */     str = String.valueOf(String.valueOf(str)).concat("procedures for the first time, we recommend that you go through the ");
/*      */     
/*   92 */     str = String.valueOf(String.valueOf(str)).concat("demonstration examples from the Demo menu.");
/*      */     
/*   94 */     str = String.valueOf(String.valueOf(str)).concat("\n\n");
/*   95 */     str = String.valueOf(String.valueOf(str)).concat("If you would like to choose an area other than \"Metaheuristics\" within which ");
/*   96 */     str = String.valueOf(String.valueOf(str)).concat("to work, select the Area menu.\n");
/*      */     
/*   98 */     MultilineLabel content = new MultilineLabel(str);
/*   99 */     content.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*      */     
/*  101 */     this.areaHelpPanel = new MultilinePanel(content, this.tutorial.scrollPane);
/*      */   }
/*      */   
/*      */   protected void setMenuState(int currentStep)
/*      */   {
/*  106 */     setMenuStateTSPFinishEnterProblem();
/*      */   }
/*      */   
/*      */   private void setMenuStateTSPStartEnterProblem()
/*      */   {
/*  111 */     this.tabu_search.setEnabled(true);
/*  112 */     this.tutorial.option.setEnabled(false);
/*      */   }
/*      */   
/*      */   private void resetOptionState()
/*      */   {
/*  117 */     this.mi_tsp_problems.setSelected(false);
/*  118 */     this.np_problems.setSelected(false);
/*  119 */     this.inp_problems.setSelected(false);
/*      */   }
/*      */   
/*      */   private void setMenuStateTSPFinishEnterProblem()
/*      */   {
/*  124 */     this.tutorial.option.setEnabled(true);
/*      */     
/*  126 */     if (this.procedure == "Tabu Search") {
/*  127 */       this.mi_tsp_problems.setEnabled(true);
/*  128 */       this.mi_tsp_problems.setSelected(false);
/*      */       
/*  130 */       this.np_problems.setEnabled(false);
/*  131 */       this.np_problems.setSelected(false);
/*      */       
/*  133 */       this.inp_problems.setEnabled(false);
/*  134 */       this.inp_problems.setSelected(false);
/*  135 */     } else if (this.procedure == "Simulated Annealing") {
/*  136 */       this.mi_tsp_problems.setEnabled(true);
/*  137 */       this.mi_tsp_problems.setSelected(false);
/*      */       
/*  139 */       this.np_problems.setEnabled(true);
/*  140 */       this.np_problems.setSelected(false);
/*      */       
/*  142 */       this.inp_problems.setEnabled(false);
/*  143 */       this.inp_problems.setSelected(false);
/*      */     }
/*      */     else {
/*  146 */       this.mi_tsp_problems.setEnabled(true);
/*  147 */       this.mi_tsp_problems.setSelected(false);
/*      */       
/*  149 */       this.np_problems.setEnabled(false);
/*  150 */       this.np_problems.setSelected(false);
/*      */       
/*  152 */       this.inp_problems.setEnabled(true);
/*  153 */       this.inp_problems.setSelected(false);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected void setDefaultMenuState()
/*      */   {
/*  160 */     setMenuStateTSPStartEnterProblem();
/*      */   }
/*      */   
/*      */   private void enterProblem()
/*      */   {
/*  165 */     if (this.procedure.equalsIgnoreCase("Genetic Algorithm")) {
/*  166 */       this.enterPanel1 = new IORTSPEnterPanel1(this);
/*  167 */       this.currentActionPanel = this.enterPanel1;
/*  168 */       this.enterPanel1.setPanelEnable(true);
/*  169 */       setHelpEnabled(true);
/*      */       
/*  171 */       this.tutorial.scrollPane.setViewportView(this.enterPanel1);
/*  172 */       setMenuState(0);
/*  173 */       this.enterPanel1.setFinishEnabled(true);
/*      */       
/*  175 */       this.enterPanel1.revalidate();
/*  176 */       this.enterPanel1.updatePanel();
/*      */     }
/*      */     else {
/*  179 */       this.enterPanel = new IORTSPEnterPanel(this);
/*  180 */       this.currentActionPanel = this.enterPanel;
/*  181 */       this.enterPanel.setPanelEnable(true);
/*  182 */       this.currentActionPanel.actionStatus.setText("");
/*      */       
/*  184 */       setHelpEnabled(true);
/*      */       
/*  186 */       this.tutorial.scrollPane.setViewportView(this.enterPanel);
/*  187 */       setMenuState(0);
/*  188 */       this.enterPanel.setFinishEnabled(true);
/*      */       
/*  190 */       this.enterPanel.revalidate();
/*  191 */       this.enterPanel.updatePanel();
/*      */     }
/*  193 */     this.np_problems.setSelected(false);
/*  194 */     this.inp_problems.setSelected(false);
/*  195 */     this.mi_tsp_problems.setSelected(false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void tabuSearch()
/*      */   {
/*  202 */     IOROperation op = new IOROperation(60009, null);
/*  203 */     if (this.tabuSearchPanel != null)
/*  204 */       this.tabuSearchPanel = null;
/*  205 */     this.tabuSearchPanel = new IORTSPTabuSearchPanel(this);
/*  206 */     this.tabuSearchPanel.initData();
/*  207 */     this.currentActionPanel = this.tabuSearchPanel;
/*  208 */     this.currentActionPanel.actionStatus.setText(" ");
/*      */     
/*      */ 
/*  211 */     this.tutorial.scrollPane.setViewportView(this.tabuSearchPanel);
/*  212 */     setMenuState(1);
/*  213 */     this.tabuSearchPanel.setFinishEnabled(true);
/*      */     
/*  215 */     this.tabuSearchPanel.revalidate();
/*  216 */     this.tabuSearchPanel.updatePanel();
/*      */   }
/*      */   
/*      */   public void simulatedAnnealing() {
/*  220 */     IOROperation op = new IOROperation(60010, null);
/*  221 */     this.simulateAnealingPanel = null;
/*  222 */     this.simulateAnealingPanel = new IORSimulateAnealingPanel(this);
/*  223 */     this.simulateAnealingPanel.reset();
/*  224 */     this.currentActionPanel = this.simulateAnealingPanel;
/*  225 */     this.currentActionPanel.actionStatus.setText(" ");
/*      */     
/*  227 */     this.tutorial.scrollPane.setViewportView(this.simulateAnealingPanel);
/*  228 */     setMenuState(2);
/*  229 */     this.simulateAnealingPanel.setFinishEnabled(true);
/*      */     
/*  231 */     this.simulateAnealingPanel.revalidate();
/*  232 */     this.simulateAnealingPanel.updatePanel();
/*      */   }
/*      */   
/*      */   public void genetic() {
/*  236 */     IOROperation op = new IOROperation(60014, null);
/*  237 */     this.geneticAlgorithmPanel = null;
/*  238 */     this.geneticAlgorithmPanel = new IORTSPGeneticAlgorithmPanel(this);
/*  239 */     this.geneticAlgorithmPanel.reset();
/*  240 */     this.currentActionPanel = this.geneticAlgorithmPanel;
/*  241 */     this.currentActionPanel.actionStatus.setText(" ");
/*      */     
/*      */ 
/*  244 */     this.tutorial.scrollPane.setViewportView(this.geneticAlgorithmPanel);
/*  245 */     setMenuState(3);
/*  246 */     this.geneticAlgorithmPanel.setFinishEnabled(true);
/*      */     
/*  248 */     this.geneticAlgorithmPanel.revalidate();
/*  249 */     this.geneticAlgorithmPanel.updatePanel();
/*      */   }
/*      */   
/*      */ 
/*      */   protected void createMyMenus()
/*      */   {
/*  255 */     ButtonGroup group = new ButtonGroup();
/*  256 */     this.tabu_search = new JRadioButtonMenuItem("Tabu Search");
/*  257 */     this.simulated_annealing = new JRadioButtonMenuItem("Simulated Annealing");
/*  258 */     this.genetic_algorithm = new JRadioButtonMenuItem("Genetic Algorithm");
/*      */     
/*      */ 
/*  261 */     this.tutorial.procedure.add(this.tabu_search);
/*  262 */     this.tutorial.procedure.add(this.simulated_annealing);
/*  263 */     this.tutorial.procedure.add(this.genetic_algorithm);
/*      */     
/*      */ 
/*      */ 
/*  267 */     group.add(this.tabu_search);
/*  268 */     group.add(this.simulated_annealing);
/*  269 */     group.add(this.genetic_algorithm);
/*      */     
/*      */ 
/*      */ 
/*  273 */     this.tabu_search.setMnemonic('T');
/*  274 */     this.simulated_annealing.setMnemonic('S');
/*  275 */     this.genetic_algorithm.setMnemonic('G');
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  293 */     this.tabu_search.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/*  296 */         IORTSPProcessController.this.infoPanel("Tabu");
/*  297 */         IORTSPProcessController.this.procedure = "Tabu Search";
/*  298 */         IORTSPProcessController.this.setStatusBar();
/*  299 */         IORTSPProcessController.this.resetOptionState();
/*  300 */         IORTSPProcessController.this.setMenuState(10);
/*      */       }
/*      */       
/*      */ 
/*  304 */     });
/*  305 */     this.simulated_annealing.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/*  308 */         IORTSPProcessController.this.infoPanel("Sa");
/*  309 */         IORTSPProcessController.this.tutorial.option.setEnabled(false);
/*  310 */         IORTSPProcessController.this.procedure = "Simulated Annealing";
/*  311 */         IORTSPProcessController.this.setMenuState(10);
/*  312 */         IORTSPProcessController.this.resetOptionState();
/*  313 */         IORTSPProcessController.this.setStatusBar();
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*  318 */     });
/*  319 */     this.genetic_algorithm.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/*  322 */         IORTSPProcessController.this.infoPanel("Ga");
/*  323 */         IORTSPProcessController.this.tutorial.option.setEnabled(false);
/*  324 */         IORTSPProcessController.this.procedure = "Genetic Algorithm";
/*  325 */         IORTSPProcessController.this.setMenuState(10);
/*  326 */         IORTSPProcessController.this.setStatusBar();
/*      */         
/*  328 */         IORTSPProcessController.this.resetOptionState();
/*      */ 
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  336 */     });
/*  337 */     this.tutorial.option.setEnabled(false);
/*  338 */     this.option_group = new ButtonGroup();
/*      */     
/*  340 */     this.mi_tsp_problems = new JRadioButtonMenuItem("Traveling Salesman Problems");
/*  341 */     this.option_group.add(this.mi_tsp_problems);
/*  342 */     this.tutorial.option.add(this.mi_tsp_problems);
/*  343 */     this.mi_tsp_problems.setMnemonic('T');
/*  344 */     this.mi_tsp_problems.setEnabled(true);
/*      */     
/*      */ 
/*  347 */     this.mi_tsp_problems.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/*  350 */         IORTSPProcessController.this.option = "Traveling Salesman Problems";
/*  351 */         IORTSPProcessController.this.setStatusBar();
/*  352 */         IORTSPProcessController.this.enterProblem();
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*  357 */     });
/*  358 */     this.np_problems = new JRadioButtonMenuItem("Nonlinear Programming");
/*  359 */     this.option_group.add(this.np_problems);
/*  360 */     this.tutorial.option.add(this.np_problems);
/*  361 */     this.np_problems.setEnabled(true);
/*  362 */     this.np_problems.setMnemonic('N');
/*  363 */     this.np_problems.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/*  366 */         IORTSPProcessController.this.option = "Nonlinear Programming";
/*  367 */         IORTSPProcessController.this.setStatusBar();
/*  368 */         IORTSPProcessController.this.solveAnnealing();
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*  373 */     });
/*  374 */     this.inp_problems = new JRadioButtonMenuItem("Integer Nonlinear Programming");
/*  375 */     this.option_group.add(this.inp_problems);
/*  376 */     this.tutorial.option.add(this.inp_problems);
/*  377 */     this.inp_problems.setEnabled(true);
/*  378 */     this.inp_problems.setMnemonic('I');
/*  379 */     this.inp_problems.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/*  382 */         IORTSPProcessController.this.option = "Integer Nonlinear Programming";
/*  383 */         IORTSPProcessController.this.setStatusBar();
/*  384 */         IORTSPProcessController.this.solveGenetic();
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   public void infoPanel(String mothod) {
/*  390 */     this.currentActionPanel = new IORMInfoPanel(this, mothod);
/*      */     
/*  392 */     this.infopanel = new IORMInfoPanel(this, mothod);
/*      */     
/*  394 */     this.currentActionPanel.initializeHelpPanels();
/*      */     
/*  396 */     this.currentActionPanel = this.infopanel;
/*      */     
/*  398 */     setHelpEnabled(true);
/*      */     
/*  400 */     this.tutorial.scrollPane.setViewportView(this.infopanel);
/*  401 */     this.infopanel.revalidate();
/*  402 */     this.infopanel.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */   public void solveAnnealing()
/*      */   {
/*  408 */     Vector par = new Vector();
/*  409 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*      */ 
/*  412 */     par.add(new Integer(3));
/*  413 */     opr.parameters = par;
/*  414 */     opr.operation_code = 50103;
/*  415 */     this.solver.doWork(opr, this.nldata);
/*      */     
/*  417 */     this.apanel = new IORNLAnnealingPanel(this);
/*  418 */     this.currentActionPanel = this.apanel;
/*      */     
/*  420 */     setHelpEnabled(true);
/*      */     
/*  422 */     this.tutorial.scrollPane.setViewportView(this.apanel);
/*  423 */     this.apanel.revalidate();
/*  424 */     this.apanel.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */   public void solveGenetic()
/*      */   {
/*  430 */     Vector par = new Vector();
/*  431 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*      */ 
/*  434 */     par.add(new Integer(66));
/*  435 */     opr.parameters = par;
/*  436 */     opr.operation_code = 50103;
/*  437 */     this.solver.doWork(opr, this.nldata);
/*      */     
/*  439 */     this.gepanel = new IORNLGeneticPanel(this);
/*  440 */     this.currentActionPanel = this.gepanel;
/*      */     
/*  442 */     setHelpEnabled(true);
/*      */     
/*  444 */     this.tutorial.scrollPane.setViewportView(this.gepanel);
/*  445 */     this.gepanel.revalidate();
/*  446 */     this.gepanel.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void setOption(int option)
/*      */   {
/*  453 */     Vector p = new Vector();
/*  454 */     p.addElement(new Integer(option));
/*      */     
/*  456 */     IOROperation operation = new IOROperation(60012, p);
/*      */     
/*  458 */     if (this.currentActionPanel != null) {
/*  459 */       this.currentActionPanel.updatePanel();
/*      */     }
/*      */   }
/*      */   
/*      */   public int getOption() {
/*  464 */     return this.data.method_option;
/*      */   }
/*      */   
/*      */   public class TSPData implements java.io.Externalizable
/*      */   {
/*      */     public int method_option;
/*      */     
/*      */     TSPData() {
/*  472 */       this.TB_InterTabuList_Vec.addElement("");
/*      */     }
/*      */     
/*      */     public void writeExternal(ObjectOutput stream)
/*      */     {
/*      */       try {
/*  478 */         stream.writeObject(this.matrix);
/*  479 */         stream.writeInt(this.num_city);
/*  480 */         stream.writeObject(this.initSolution);
/*  481 */         stream.writeObject(this.saveSolution);
/*  482 */         stream.writeBoolean(this.isDrawLine);
/*  483 */         stream.writeDouble(this.initDistance);
/*  484 */         if ((IORTSPProcessController.this.currentActionPanel instanceof IORSimulateAnealingPanel))
/*      */         {
/*  486 */           stream.writeObject(this.auto_T_Vec);
/*  487 */           stream.writeObject(this.auto_Solution_Vec);
/*  488 */           stream.writeObject(this.auto_Distance_Vec);
/*  489 */           stream.writeObject(this.auto_Probability_Vec);
/*  490 */           stream.writeObject(this.auto_Accept_Vec);
/*      */           
/*  492 */           stream.writeObject(this.saveAuto_T_Vec);
/*  493 */           stream.writeObject(this.saveAuto_Solution_Vec);
/*  494 */           stream.writeObject(this.saveAuto_Distance_Vec);
/*  495 */           stream.writeObject(this.saveAuto_Probability_Vec);
/*  496 */           stream.writeObject(this.saveAuto_Accept_Vec);
/*  497 */           stream.writeObject(this.saveCurrentTotalLine);
/*      */           
/*  499 */           stream.writeInt(this.autoTimes);
/*  500 */           stream.writeInt(this.totalLine);
/*      */           
/*  502 */           stream.writeBoolean(this.SAisError);
/*      */           
/*  504 */           stream.writeObject(this.step_Vec);
/*  505 */           stream.writeObject(this.step_T_Vec);
/*  506 */           stream.writeObject(this.step_Solution_Vec);
/*  507 */           stream.writeObject(this.step_Distance_Vec);
/*  508 */           stream.writeObject(this.step_Probability_Vec);
/*  509 */           stream.writeObject(this.step_Accept_Vec);
/*      */           
/*  511 */           stream.writeInt(this.currentLine);
/*  512 */           stream.writeBoolean(this.isFinish_SA);
/*      */           
/*  514 */           stream.writeObject(this.SA_Next_PrintData);
/*  515 */           stream.writeObject(this.SA_Auto_PrintData);
/*  516 */           stream.writeUTF(this.SA_BestSolutionInfoLab);
/*  517 */           stream.writeObject(this.bestSolutionNum);
/*      */ 
/*      */         }
/*  520 */         else if ((IORTSPProcessController.this.currentActionPanel instanceof IORTSPTabuSearchPanel))
/*      */         {
/*  522 */           stream.writeObject(this.TB_InterSolution_Vec);
/*  523 */           stream.writeObject(this.TB_InterDistance_Vec);
/*  524 */           stream.writeObject(this.TB_InterTabuList_Vec);
/*  525 */           stream.writeObject(this.TB_InterTabuArray_Vec);
/*  526 */           stream.writeObject(this.TB_comBoxMessageV);
/*  527 */           stream.writeObject(this.TB_comBoxDistanceV);
/*  528 */           stream.writeObject(this.TB_currentSolutionV);
/*  529 */           stream.writeObject(this.TB_moveV);
/*  530 */           stream.writeObject(this.TB_Auto_SolutionV);
/*  531 */           stream.writeObject(this.TB_Auto_DistanceV);
/*  532 */           stream.writeObject(this.TB_Auto_TabuV);
/*  533 */           stream.writeObject(this.TB_Auto_Total_SolutionV);
/*  534 */           stream.writeObject(this.TB_Auto_Total_DistanceV);
/*  535 */           stream.writeObject(this.TB_Auto_Total_TabuV);
/*  536 */           stream.writeInt(this.TB_Auto_totalTimes);
/*  537 */           stream.writeInt(this.TB_Auto_TotalLine);
/*  538 */           stream.writeInt(this.selectDistance);
/*  539 */           stream.writeInt(this.TB_Totaltimes);
/*  540 */           stream.writeInt(this.TB_currentTime);
/*  541 */           stream.writeObject(this.currentSolution);
/*  542 */           stream.writeObject(this.TB_Print_TableV);
/*  543 */           stream.writeObject(this.TB_Auto_Print_Table);
/*  544 */           stream.writeObject(this.bestSolutionNum);
/*      */         }
/*  546 */         else if ((IORTSPProcessController.this.currentActionPanel instanceof IORTSPGeneticAlgorithmPanel))
/*      */         {
/*  548 */           stream.writeObject(this.GAsolution_Vec);
/*  549 */           stream.writeObject(this.fitness_Vec);
/*  550 */           stream.writeObject(this.population_Vec);
/*  551 */           stream.writeObject(this.popFitness_Vec);
/*  552 */           stream.writeObject(this.children_Vec);
/*  553 */           stream.writeObject(this.childFitness_Vec);
/*      */           
/*  555 */           stream.writeObject(this.GA_Print_BestSolution_Data);
/*  556 */           stream.writeObject(this.GA_Print_Pop_Data);
/*  557 */           stream.writeObject(this.GA_Print_Child_Data);
/*  558 */           stream.writeObject(this.GA_Print_P_Connection_C_Data);
/*  559 */           stream.writeObject(this.GA_Print_ChildNum);
/*  560 */           stream.writeInt(this.GAtotalTimes);
/*  561 */           stream.writeInt(this.GACurrentTime);
/*  562 */           stream.writeObject(this.GA_Solution_Vec);
/*  563 */           stream.writeObject(this.GA_Fitness_Vec);
/*  564 */           stream.writeObject(this.GA_Pop_Vec);
/*  565 */           stream.writeObject(this.GA_Total_Pop_Vec);
/*  566 */           stream.writeObject(this.GA_PopFit_Vec);
/*  567 */           stream.writeObject(this.GA_Total_PopFit_Vec);
/*  568 */           stream.writeObject(this.GA_Child_Vec);
/*  569 */           stream.writeObject(this.GA_Total_Child_Vec);
/*  570 */           stream.writeObject(this.GA_ChildBreak_Vec);
/*  571 */           stream.writeObject(this.GA_Total_ChildBreak_Vec);
/*  572 */           stream.writeObject(this.GA_ChildFitn_Vec);
/*  573 */           stream.writeObject(this.GA_Total_ChildFitn_Vec);
/*  574 */           stream.writeObject(this.GA_Total_SelectParent);
/*  575 */           stream.writeObject(this.GA_SelectParent);
/*  576 */           stream.writeInt(this.GA_Pop_And_Child_TotalLine);
/*  577 */           stream.writeInt(this.GA_Children_TotalLine);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  582 */           System.out.println("Panel Null Save file fails");
/*  583 */           return;
/*      */         }
/*  585 */         stream.writeUTF(this.isAuto);
/*  586 */         stream.writeDouble(this.MinDistance);
/*  587 */         stream.writeUTF(this.bestSolution);
/*      */       }
/*      */       catch (Exception localException) {}
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void readExternal(ObjectInput stream)
/*      */     {
/*      */       try
/*      */       {
/*  599 */         this.matrix = ((double[][])stream.readObject());
/*  600 */         this.num_city = stream.readInt();
/*  601 */         this.initSolution = ((int[])stream.readObject());
/*  602 */         this.saveSolution = ((int[])stream.readObject());
/*  603 */         this.isDrawLine = stream.readBoolean();
/*  604 */         this.initDistance = stream.readDouble();
/*      */         
/*  606 */         if (IORTSPProcessController.this.ActionPanelName.equalsIgnoreCase("IORSimulateAnealingPanel"))
/*      */         {
/*  608 */           this.auto_T_Vec = ((Vector)stream.readObject());
/*  609 */           this.auto_Solution_Vec = ((Vector)stream.readObject());
/*  610 */           this.auto_Distance_Vec = ((Vector)stream.readObject());
/*  611 */           this.auto_Probability_Vec = ((Vector)stream.readObject());
/*  612 */           this.auto_Accept_Vec = ((Vector)stream.readObject());
/*      */           
/*  614 */           this.saveAuto_T_Vec = ((Vector)stream.readObject());
/*  615 */           this.saveAuto_Solution_Vec = ((Vector)stream.readObject());
/*  616 */           this.saveAuto_Distance_Vec = ((Vector)stream.readObject());
/*  617 */           this.saveAuto_Probability_Vec = ((Vector)stream.readObject());
/*  618 */           this.saveAuto_Accept_Vec = ((Vector)stream.readObject());
/*  619 */           this.saveCurrentTotalLine = ((Vector)stream.readObject());
/*      */           
/*  621 */           this.autoTimes = stream.readInt();
/*  622 */           this.totalLine = stream.readInt();
/*  623 */           this.SAisError = stream.readBoolean();
/*      */           
/*  625 */           this.step_Vec = ((Vector)stream.readObject());
/*  626 */           this.step_T_Vec = ((Vector)stream.readObject());
/*  627 */           this.step_Solution_Vec = ((Vector)stream.readObject());
/*  628 */           this.step_Distance_Vec = ((Vector)stream.readObject());
/*  629 */           this.step_Probability_Vec = ((Vector)stream.readObject());
/*  630 */           this.step_Accept_Vec = ((Vector)stream.readObject());
/*      */           
/*  632 */           this.currentLine = stream.readInt();
/*  633 */           this.isFinish_SA = stream.readBoolean();
/*      */           
/*  635 */           this.SA_Next_PrintData = ((String[][])stream.readObject());
/*  636 */           this.SA_Auto_PrintData = ((Vector)stream.readObject());
/*  637 */           this.SA_BestSolutionInfoLab = stream.readUTF();
/*      */           
/*  639 */           this.bestSolutionNum = ((Vector)stream.readObject());
/*      */ 
/*      */         }
/*  642 */         else if (IORTSPProcessController.this.ActionPanelName.equalsIgnoreCase("IORTSPTabuSearchPanel")) {
/*  643 */           this.TB_InterSolution_Vec = ((Vector)stream.readObject());
/*  644 */           this.TB_InterDistance_Vec = ((Vector)stream.readObject());
/*  645 */           this.TB_InterTabuList_Vec = ((Vector)stream.readObject());
/*  646 */           this.TB_InterTabuArray_Vec = ((Vector)stream.readObject());
/*  647 */           this.TB_comBoxMessageV = ((Vector)stream.readObject());
/*  648 */           this.TB_comBoxDistanceV = ((Vector)stream.readObject());
/*  649 */           this.TB_currentSolutionV = ((Vector)stream.readObject());
/*  650 */           this.TB_moveV = ((Vector)stream.readObject());
/*  651 */           this.TB_Auto_SolutionV = ((Vector)stream.readObject());
/*  652 */           this.TB_Auto_DistanceV = ((Vector)stream.readObject());
/*  653 */           this.TB_Auto_TabuV = ((Vector)stream.readObject());
/*  654 */           this.TB_Auto_Total_SolutionV = ((Vector)stream.readObject());
/*  655 */           this.TB_Auto_Total_DistanceV = ((Vector)stream.readObject());
/*  656 */           this.TB_Auto_Total_TabuV = ((Vector)stream.readObject());
/*  657 */           this.TB_Auto_totalTimes = stream.readInt();
/*  658 */           this.TB_Auto_TotalLine = stream.readInt();
/*  659 */           this.selectDistance = stream.readInt();
/*  660 */           this.TB_Totaltimes = stream.readInt();
/*  661 */           this.TB_currentTime = stream.readInt();
/*  662 */           this.currentSolution = ((int[])stream.readObject());
/*  663 */           this.TB_Print_TableV = ((Vector)stream.readObject());
/*  664 */           this.TB_Auto_Print_Table = ((String[][])stream.readObject());
/*      */           
/*  666 */           this.bestSolutionNum = ((Vector)stream.readObject());
/*      */ 
/*      */         }
/*  669 */         else if (IORTSPProcessController.this.ActionPanelName.equalsIgnoreCase("IORTSPGeneticAlgorithmPanel"))
/*      */         {
/*  671 */           this.GAsolution_Vec = ((Vector)stream.readObject());
/*  672 */           this.fitness_Vec = ((Vector)stream.readObject());
/*  673 */           this.population_Vec = ((Vector)stream.readObject());
/*  674 */           this.popFitness_Vec = ((Vector)stream.readObject());
/*  675 */           this.children_Vec = ((Vector)stream.readObject());
/*  676 */           this.childFitness_Vec = ((Vector)stream.readObject());
/*      */           
/*  678 */           this.GA_Print_BestSolution_Data = ((String[][])stream.readObject());
/*      */           
/*  680 */           this.GA_Print_Pop_Data = ((Vector)stream.readObject());
/*  681 */           this.GA_Print_Child_Data = ((Vector)stream.readObject());
/*  682 */           this.GA_Print_P_Connection_C_Data = ((Vector)stream.readObject());
/*      */           
/*  684 */           this.GA_Print_ChildNum = ((Vector)stream.readObject());
/*  685 */           this.GAtotalTimes = stream.readInt();
/*  686 */           this.GACurrentTime = stream.readInt();
/*      */           
/*  688 */           this.GA_Solution_Vec = ((Vector)stream.readObject());
/*  689 */           this.GA_Fitness_Vec = ((Vector)stream.readObject());
/*  690 */           this.GA_Pop_Vec = ((Vector)stream.readObject());
/*  691 */           this.GA_Total_Pop_Vec = ((Vector)stream.readObject());
/*  692 */           this.GA_PopFit_Vec = ((Vector)stream.readObject());
/*  693 */           this.GA_Total_PopFit_Vec = ((Vector)stream.readObject());
/*  694 */           this.GA_Child_Vec = ((Vector)stream.readObject());
/*  695 */           this.GA_Total_Child_Vec = ((Vector)stream.readObject());
/*  696 */           this.GA_ChildBreak_Vec = ((Vector)stream.readObject());
/*  697 */           this.GA_Total_ChildBreak_Vec = ((Vector)stream.readObject());
/*  698 */           this.GA_ChildFitn_Vec = ((Vector)stream.readObject());
/*  699 */           this.GA_Total_ChildFitn_Vec = ((Vector)stream.readObject());
/*  700 */           this.GA_Total_SelectParent = ((Vector)stream.readObject());
/*  701 */           this.GA_SelectParent = ((Vector)stream.readObject());
/*  702 */           this.GA_Pop_And_Child_TotalLine = stream.readInt();
/*  703 */           this.GA_Children_TotalLine = stream.readInt();
/*      */         }
/*      */         else
/*      */         {
/*  707 */           return;
/*      */         }
/*      */         
/*  710 */         this.isAuto = stream.readUTF();
/*  711 */         this.MinDistance = stream.readDouble();
/*  712 */         this.bestSolution = stream.readUTF();
/*      */       }
/*      */       catch (Exception localException) {}
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  722 */     public int max_num_city = 10;
/*  723 */     public double[][] matrix = { { 0.0D, 12.0D, 10.0D, 0.0D, 0.0D, 0.0D, 12.0D }, { 12.0D, 0.0D, 8.0D, 12.0D, 0.0D, 0.0D, 0.0D }, { 10.0D, 8.0D, 0.0D, 11.0D, 3.0D, 0.0D, 9.0D }, { 0.0D, 12.0D, 11.0D, 0.0D, 11.0D, 10.0D, 0.0D }, { 0.0D, 0.0D, 3.0D, 11.0D, 0.0D, 6.0D, 7.0D }, { 0.0D, 0.0D, 0.0D, 10.0D, 6.0D, 0.0D, 9.0D }, { 12.0D, 0.0D, 9.0D, 0.0D, 7.0D, 9.0D, 0.0D } };
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  741 */     public int num_city = 7;
/*  742 */     public boolean isDrawLine = false;
/*  743 */     public int[] initSolution = { 1, 2, 3, 4, 5, 6, 7 };
/*      */     
/*  745 */     public WholeNumberField[] tempField = new WholeNumberField[this.num_city - 1];
/*      */     
/*      */     public double initDistance;
/*  748 */     public int[] saveSolution = { 1, 2, 3, 4, 5, 6, 7 };
/*      */     
/*      */ 
/*      */ 
/*  752 */     public Vector auto_T_Vec = new Vector();
/*  753 */     public Vector auto_Solution_Vec = new Vector();
/*  754 */     public Vector auto_Distance_Vec = new Vector();
/*  755 */     public Vector auto_Probability_Vec = new Vector();
/*  756 */     public Vector auto_Accept_Vec = new Vector();
/*      */     
/*  758 */     public Vector saveAuto_T_Vec = new Vector();
/*  759 */     public Vector saveAuto_Solution_Vec = new Vector();
/*  760 */     public Vector saveAuto_Distance_Vec = new Vector();
/*  761 */     public Vector saveAuto_Probability_Vec = new Vector();
/*  762 */     public Vector saveAuto_Accept_Vec = new Vector();
/*  763 */     public Vector saveCurrentTotalLine = new Vector();
/*  764 */     public int autoTimes = 0;
/*  765 */     public int totalLine = 0;
/*      */     
/*  767 */     public boolean SAisError = true;
/*      */     
/*  769 */     public Vector step_Vec = new Vector();
/*  770 */     public Vector step_T_Vec = new Vector();
/*  771 */     public Vector step_Solution_Vec = new Vector();
/*  772 */     public Vector step_Distance_Vec = new Vector();
/*  773 */     public Vector step_Probability_Vec = new Vector();
/*  774 */     public Vector step_Accept_Vec = new Vector();
/*      */     
/*  776 */     public int currentLine = 1;
/*      */     
/*  778 */     public boolean isFinish_SA = false;
/*      */     
/*  780 */     public String[][] SA_Next_PrintData = { { "", "" }, { "", "" } };
/*      */     
/*      */ 
/*      */     public Vector SA_Auto_PrintData;
/*      */     
/*      */ 
/*      */     public String SA_BestSolutionInfoLab;
/*      */     
/*      */ 
/*  789 */     public Vector GAsolution_Vec = new Vector();
/*  790 */     public Vector fitness_Vec = new Vector();
/*  791 */     public Vector population_Vec = new Vector();
/*  792 */     public Vector popFitness_Vec = new Vector();
/*  793 */     public Vector children_Vec = new Vector();
/*  794 */     public Vector childFitness_Vec = new Vector();
/*      */     
/*      */     public String[][] GA_Print_BestSolution_Data;
/*      */     
/*      */     public Vector GA_Print_Pop_Data;
/*      */     
/*      */     public Vector GA_Print_Child_Data;
/*      */     public Vector GA_Print_P_Connection_C_Data;
/*      */     public Vector GA_Print_ChildNum;
/*      */     public int GAtotalTimes;
/*      */     public int GACurrentTime;
/*  805 */     public Vector GA_Solution_Vec = new Vector();
/*  806 */     public Vector GA_Fitness_Vec = new Vector();
/*      */     
/*  808 */     public Vector GA_Pop_Vec = new Vector();
/*  809 */     public Vector GA_Total_Pop_Vec = new Vector();
/*  810 */     public Vector GA_PopFit_Vec = new Vector();
/*  811 */     public Vector GA_Total_PopFit_Vec = new Vector();
/*      */     
/*  813 */     public Vector GA_Child_Vec = new Vector();
/*  814 */     public Vector GA_Total_Child_Vec = new Vector();
/*  815 */     public Vector GA_ChildBreak_Vec = new Vector();
/*  816 */     public Vector GA_Total_ChildBreak_Vec = new Vector();
/*  817 */     public Vector GA_ChildFitn_Vec = new Vector();
/*  818 */     public Vector GA_Total_ChildFitn_Vec = new Vector();
/*  819 */     public Vector GA_Total_SelectParent = new Vector();
/*  820 */     public Vector GA_SelectParent = new Vector();
/*      */     
/*  822 */     public int GA_Pop_And_Child_TotalLine = 10;
/*  823 */     public int GA_Children_TotalLine = 6;
/*      */     
/*      */ 
/*  826 */     public Vector TB_InterSolution_Vec = new Vector();
/*  827 */     public Vector TB_InterDistance_Vec = new Vector();
/*  828 */     public Vector TB_InterTabuList_Vec = new Vector();
/*  829 */     public Vector TB_InterTabuArray_Vec = new Vector();
/*      */     
/*  831 */     public Vector TB_comBoxMessageV = new Vector();
/*  832 */     public Vector TB_comBoxDistanceV = new Vector();
/*  833 */     public Vector TB_currentSolutionV = new Vector();
/*  834 */     public Vector TB_moveV = new Vector();
/*      */     
/*  836 */     public Vector TB_Auto_SolutionV = new Vector();
/*  837 */     public Vector TB_Auto_DistanceV = new Vector();
/*  838 */     public Vector TB_Auto_TabuV = new Vector();
/*      */     
/*  840 */     public Vector TB_Auto_Total_SolutionV = new Vector();
/*  841 */     public Vector TB_Auto_Total_DistanceV = new Vector();
/*  842 */     public Vector TB_Auto_Total_TabuV = new Vector();
/*  843 */     public int TB_Auto_totalTimes = 0;
/*      */     
/*  845 */     public int TB_Auto_TotalLine = 0;
/*      */     
/*  847 */     public int selectDistance = 0;
/*  848 */     public int TB_Totaltimes = 0;
/*  849 */     public int TB_currentTime = 0;
/*  850 */     int[] currentSolution = { 1, 2, 3, 4, 5, 6, 7 };
/*      */     
/*      */ 
/*  853 */     public Vector TB_Print_TableV = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public String[][] TB_Auto_Print_Table;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  863 */     public String isAuto = "true";
/*      */     public double MinDistance;
/*      */     public String bestSolution;
/*      */     Vector bestSolutionNum;
/*      */   }
/*      */   
/*      */   public class NLData
/*      */     implements Serializable
/*      */   {
/*  872 */     final int MAX_NUM_VARIABLE = 3;
/*  873 */     final int MAX_NUM_ITERATION = 20;
/*      */     int num_term;
/*      */     boolean is_max;
/*      */     double[] newx;
/*      */     double[] fx;
/*      */     double[] df;
/*      */     double[] low_bound;
/*      */     double[] up_bound;
/*      */     double[] fx_coef;
/*      */     double[] df_coef;
/*      */     int[] fx_power;
/*      */     int[] df_power;
/*      */     double[] oldx1;
/*      */     double[] oldx2;
/*      */     double[] newx1;
/*      */     double[] newx2;
/*      */     double[] gradx1;
/*      */     double[] gradx2;
/*  891 */     double[] a1; double[] b1; double[] a2; double[] b2; double[] tt; double[] fx1x2_coef; double[] dfx1_coef; double[] dfx2_coef; double[] ft_coef; int[] fx_x1exp; int[] fx_x2exp; int[] dfx1_x1exp; int[] dfx1_x2exp; int[] dfx2_x1exp; int[] dfx2_x2exp; int[] ft_texp; int t_function_num_term; double errTolerance; int num_variable; int num_constraint; IORTSPProcessController.NLData.NLEquation fw_objective = new IORTSPProcessController.NLData.NLEquation();
/*  892 */     IORTSPProcessController.NLData.NLEquation[] fw_derivative = new IORTSPProcessController.NLData.NLEquation[3];
/*      */     double[][] fw_oldx;
/*      */     double[][] fw_newx;
/*      */     double[][] fw_a;
/*      */     double[][] fw_b;
/*      */     double[][] fw_xlp;
/*      */     double[][] fw_constraint_coef;
/*      */     int[] fw_constraint_operator;
/*      */     double[][] fw_flp_coef;
/*      */     double[][] fw_gradx;
/*      */     double[] fw_tt;
/*      */     double[][] fw_ht_coef;
/*      */     int[][] fw_ht_texp;
/*      */     int[] fw_ht_num_term;
/*      */     double[] Temp;
/*      */     double[][] variable;
/*      */     double[] function;
/*      */     double[] probability;
/*      */     boolean[] accept;
/*      */     int totalresult;
/*      */     int[] itertag;
/*      */     int pos;
/*      */     double bestresult;
/*      */     double[] bestvarible;
/*  916 */     boolean isInputPanel = true;
/*      */     
/*      */ 
/*  919 */     String bestSolution = "";
/*      */     
/*      */     String[][] printdata;
/*      */     
/*      */     int totalrecord;
/*      */     Chromosome[] best;
/*      */     Chromosome[] store;
/*      */     Chromosome[] children;
/*      */     int[] parentindex;
/*      */     byte[][][] bytag;
/*      */     int sumt_num_variable_with_bound;
/*      */     int sumt_num_variable_without_bound;
/*      */     int sumt_num_inequality_constraint;
/*      */     int sumt_num_equality_constraint;
/*      */     double[] sumt_oldx;
/*      */     double[] sumt_newx;
/*      */     double sumt_r;
/*  936 */     IORTSPProcessController.NLData.NLEquation sumt_objective = new IORTSPProcessController.NLData.NLEquation();
/*  937 */     IORTSPProcessController.NLData.NLEquation[] sumt_Bx = new IORTSPProcessController.NLData.NLEquation[3];
/*  938 */     IORTSPProcessController.NLData.NLEquation[] sumt_Lx = new IORTSPProcessController.NLData.NLEquation[3];
/*      */     
/*      */ 
/*      */     public NLData()
/*      */     {
/*  943 */       for (int i = 0; i < 3; i++) {
/*  944 */         this.fw_derivative[i] = new IORTSPProcessController.NLData.NLEquation();
/*  945 */         this.sumt_Bx[i] = new IORTSPProcessController.NLData.NLEquation();
/*  946 */         this.sumt_Lx[i] = new IORTSPProcessController.NLData.NLEquation();
/*      */       }
/*      */     }
/*      */     
/*      */     public class NLEquation implements Serializable {
/*  951 */       final int MAX_EQ_TERM = 10;
/*      */       int num_term;
/*  953 */       int num_var; double[] fxcoef = new double[10];
/*  954 */       int[][] xexp = new int[10][3];
/*      */       
/*      */       public NLEquation() {}
/*      */     }
/*      */   }
/*      */   
/*  960 */   protected String getArea() { return "Metaheuristics"; }
/*      */   
/*      */   public void SaveFile(ObjectOutputStream out)
/*      */   {
/*  964 */     if ((this.currentActionPanel instanceof IORTSPEnterPanel)) {
/*  965 */       ((IORTSPEnterPanel)this.currentActionPanel).SaveProblem();
/*  966 */     } else if ((this.currentActionPanel instanceof IORNLAnnealingPanel)) {
/*  967 */       ((IORNLAnnealingPanel)this.currentActionPanel).SaveFunction();
/*  968 */     } else if ((this.currentActionPanel instanceof IORNLGeneticPanel)) {
/*  969 */       ((IORNLGeneticPanel)this.currentActionPanel).SaveFunction();
/*      */     }
/*      */     try {
/*  972 */       out.writeObject(this.solver);
/*  973 */       out.writeObject(this.procedure);
/*  974 */       out.writeObject(this.option);
/*      */       
/*  976 */       if ((this.currentActionPanel instanceof IORTSPEnterPanel)) {
/*  977 */         out.writeObject("IORTSPEnterPanel");
/*  978 */         this.data.writeExternal(out);
/*  979 */       } else if ((this.currentActionPanel instanceof IORTSPEnterPanel1)) {
/*  980 */         out.writeObject("IORTSPEnterPanel1");
/*  981 */         this.data.writeExternal(out);
/*      */ 
/*      */       }
/*  984 */       else if ((this.currentActionPanel instanceof IORNLAnnealingPanel)) {
/*  985 */         out.writeObject("IORNLAnnealingPanel");
/*  986 */         out.writeObject(this.nldata);
/*      */       }
/*  988 */       else if ((this.currentActionPanel instanceof IORNLGeneticPanel)) {
/*  989 */         out.writeObject("IORNLGeneticPanel");
/*  990 */         out.writeObject(this.nldata);
/*      */       }
/*  992 */       else if ((this.currentActionPanel instanceof IORSimulateAnealingPanel)) {
/*  993 */         out.writeObject("IORSimulateAnealingPanel");
/*  994 */         this.data.writeExternal(out);
/*      */       }
/*  996 */       else if ((this.currentActionPanel instanceof IORTSPTabuSearchPanel)) {
/*  997 */         out.writeObject("IORTSPTabuSearchPanel");
/*  998 */         this.data.writeExternal(out);
/*      */       }
/* 1000 */       else if ((this.currentActionPanel instanceof IORTSPGeneticAlgorithmPanel)) {
/* 1001 */         out.writeObject("IORTSPGeneticAlgorithmPanel");
/* 1002 */         this.data.writeExternal(out);
/*      */       }
/*      */       else
/*      */       {
/* 1006 */         return;
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1010 */       System.out.println("Exception Save file fails".concat(String.valueOf(String.valueOf(e))));
/* 1011 */       return;
/*      */     }
/* 1013 */     System.out.println("SAVE FILE");
/* 1014 */     this.currentActionPanel.SaveFile(out);
/*      */   }
/*      */   
/*      */ 
/*      */   public void LoadFile(ObjectInputStream in)
/*      */   {
/*      */     try
/*      */     {
/* 1022 */       this.solver = ((ORTSPSolver)in.readObject());
/* 1023 */       this.procedure = ((String)in.readObject());
/* 1024 */       this.option = ((String)in.readObject());
/* 1025 */       this.ActionPanelName = ((String)in.readObject());
/*      */     }
/*      */     catch (Exception e) {
/* 1028 */       System.out.println("Open file fails....".concat(String.valueOf(String.valueOf(e))));
/* 1029 */       return;
/*      */     }
/*      */     
/* 1032 */     this.tabu_search.setEnabled(true);
/* 1033 */     this.tabu_search.setSelected(true);
/* 1034 */     this.tutorial.option.setEnabled(true);
/*      */     
/* 1036 */     setHelpEnabled(true);
/* 1037 */     if (this.ActionPanelName.equalsIgnoreCase("IORTSPEnterPanel")) {
/* 1038 */       this.data.readExternal(in);
/* 1039 */       this.currentActionPanel = new IORTSPEnterPanel(this);
/* 1040 */       this.tabu_search.setSelected(true);
/*      */ 
/*      */     }
/* 1043 */     else if (this.ActionPanelName.equalsIgnoreCase("IORTSPEnterPanel1")) {
/* 1044 */       this.data.readExternal(in);
/* 1045 */       this.currentActionPanel = new IORTSPEnterPanel1(this);
/*      */       
/* 1047 */       this.genetic_algorithm.setSelected(true);
/*      */       
/* 1049 */       this.np_problems.setEnabled(false);
/* 1050 */       this.inp_problems.setEnabled(true);
/* 1051 */       this.mi_tsp_problems.setEnabled(true);
/*      */ 
/*      */ 
/*      */     }
/* 1055 */     else if (this.ActionPanelName.equalsIgnoreCase("IORNLAnnealingPanel")) {
/*      */       try {
/* 1057 */         this.nldata = ((IORTSPProcessController.NLData)in.readObject());
/*      */       } catch (Exception e) {
/* 1059 */         System.out.println("nldata load fails....".concat(String.valueOf(String.valueOf(e))));
/* 1060 */         return;
/*      */       }
/* 1062 */       this.currentActionPanel = new IORNLAnnealingPanel(this);
/*      */       
/* 1064 */       this.simulated_annealing.setSelected(true);
/* 1065 */       this.np_problems.setSelected(true);
/*      */       
/* 1067 */       this.np_problems.setEnabled(true);
/* 1068 */       this.inp_problems.setEnabled(false);
/* 1069 */       this.mi_tsp_problems.setEnabled(true);
/*      */ 
/*      */     }
/* 1072 */     else if (this.ActionPanelName.equalsIgnoreCase("IORNLGeneticPanel")) {
/*      */       try {
/* 1074 */         this.nldata = ((IORTSPProcessController.NLData)in.readObject());
/*      */       } catch (Exception e) {
/* 1076 */         System.out.println("nldata load fails....".concat(String.valueOf(String.valueOf(e))));
/* 1077 */         return;
/*      */       }
/* 1079 */       this.currentActionPanel = new IORNLGeneticPanel(this);
/*      */       
/* 1081 */       this.genetic_algorithm.setSelected(true);
/* 1082 */       this.inp_problems.setSelected(true);
/*      */       
/* 1084 */       this.np_problems.setEnabled(false);
/* 1085 */       this.inp_problems.setEnabled(true);
/* 1086 */       this.mi_tsp_problems.setEnabled(true);
/*      */ 
/*      */     }
/* 1089 */     else if (this.ActionPanelName.equalsIgnoreCase("IORSimulateAnealingPanel")) {
/* 1090 */       this.data.readExternal(in);
/* 1091 */       this.currentActionPanel = new IORSimulateAnealingPanel(this);
/*      */       
/* 1093 */       this.simulated_annealing.setSelected(true);
/* 1094 */       this.mi_tsp_problems.setSelected(true);
/*      */       
/* 1096 */       this.np_problems.setEnabled(true);
/* 1097 */       this.inp_problems.setEnabled(false);
/* 1098 */       this.mi_tsp_problems.setEnabled(true);
/*      */ 
/*      */     }
/* 1101 */     else if (this.ActionPanelName.equalsIgnoreCase("IORTSPTabuSearchPanel")) {
/* 1102 */       this.data.readExternal(in);
/* 1103 */       this.currentActionPanel = new IORTSPTabuSearchPanel(this);
/*      */       
/* 1105 */       this.tabu_search.setSelected(true);
/* 1106 */       this.mi_tsp_problems.setSelected(true);
/*      */       
/* 1108 */       this.np_problems.setEnabled(false);
/* 1109 */       this.inp_problems.setEnabled(false);
/* 1110 */       this.mi_tsp_problems.setEnabled(true);
/*      */ 
/*      */     }
/* 1113 */     else if (this.ActionPanelName.equalsIgnoreCase("IORTSPGeneticAlgorithmPanel")) {
/* 1114 */       this.data.readExternal(in);
/* 1115 */       this.currentActionPanel = new IORTSPGeneticAlgorithmPanel(this);
/*      */       
/* 1117 */       this.genetic_algorithm.setSelected(true);
/* 1118 */       this.mi_tsp_problems.setSelected(true);
/*      */       
/* 1120 */       this.np_problems.setEnabled(false);
/* 1121 */       this.inp_problems.setEnabled(true);
/* 1122 */       this.mi_tsp_problems.setEnabled(true);
/*      */     }
/*      */     else
/*      */     {
/* 1126 */       setHelpEnabled(false);
/* 1127 */       System.out.println("Open file fails !!!!");
/* 1128 */       return;
/*      */     }
/*      */     
/* 1131 */     setStatusBar();
/* 1132 */     this.currentActionPanel.LoadFile(in);
/*      */     
/*      */ 
/* 1135 */     this.tutorial.scrollPane.setViewportView(this.currentActionPanel);
/* 1136 */     this.currentActionPanel.revalidate();
/* 1137 */     this.currentActionPanel.repaint();
/*      */   }
/*      */   
/*      */   public void print() {
/* 1141 */     int p = 1;
/* 1142 */     if ((this.currentActionPanel instanceof IORNLAnnealingPanel)) {
/* 1143 */       p = 7;
/* 1144 */     } else if ((this.currentActionPanel instanceof IORNLGeneticPanel)) {
/* 1145 */       p = 8;
/* 1146 */     } else if ((this.currentActionPanel instanceof IORTSPEnterPanel)) {
/* 1147 */       p = 1;
/* 1148 */     } else if ((this.currentActionPanel instanceof IORSimulateAnealingPanel)) {
/* 1149 */       p = 2;
/* 1150 */     } else if ((this.currentActionPanel instanceof IORTSPGeneticAlgorithmPanel)) {
/* 1151 */       p = 3;
/* 1152 */     } else if ((this.currentActionPanel instanceof IORTSPTabuSearchPanel)) {
/* 1153 */       p = 4;
/*      */     }
/* 1155 */     this.solver.setPrintProcedure(p);
/*      */     
/* 1157 */     PrinterJob printJob = PrinterJob.getPrinterJob();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1165 */     this.solver.setPrintProcedure(p);
/*      */     
/* 1167 */     printJob.setPrintable(this.solver);
/*      */     
/* 1169 */     boolean pDialogState = true;
/* 1170 */     pDialogState = printJob.printDialog();
/* 1171 */     if (pDialogState) {
/*      */       try {
/* 1173 */         printJob.print();
/*      */       }
/*      */       catch (Exception ex) {
/* 1176 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void printToFile() {
/* 1182 */     int p = 1;
/* 1183 */     if ((this.currentActionPanel instanceof IORNLAnnealingPanel)) {
/* 1184 */       p = 7;
/* 1185 */     } else if ((this.currentActionPanel instanceof IORNLGeneticPanel)) {
/* 1186 */       p = 8;
/* 1187 */     } else if ((this.currentActionPanel instanceof IORTSPEnterPanel)) {
/* 1188 */       p = 1;
/* 1189 */     } else if ((this.currentActionPanel instanceof IORSimulateAnealingPanel)) {
/* 1190 */       p = 2;
/* 1191 */     } else if ((this.currentActionPanel instanceof IORTSPGeneticAlgorithmPanel)) {
/* 1192 */       p = 3;
/* 1193 */     } else if ((this.currentActionPanel instanceof IORTSPTabuSearchPanel)) {
/* 1194 */       p = 4;
/*      */     }
/* 1196 */     JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
/* 1197 */     int result = fc.showSaveDialog(null);
/* 1198 */     File chosenFile = fc.getSelectedFile();
/* 1199 */     if ((result == 0) && (chosenFile != null))
/*      */     {
/*      */ 
/* 1202 */       this.solver.PrintToFile(chosenFile.getPath(), p);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTSPProcessController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */