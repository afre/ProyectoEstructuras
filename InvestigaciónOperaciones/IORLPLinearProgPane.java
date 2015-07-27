/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Font;
/*      */ import java.awt.Frame;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.GridLayout;
/*      */ import java.awt.Point;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.NumberFormat;
/*      */ import java.text.ParseException;
/*      */ import java.util.Vector;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.event.CaretEvent;
/*      */ import javax.swing.event.CaretListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IORLPLinearProgPane
/*      */   extends IORLPActionPanel
/*      */   implements POMTableDataNotify1
/*      */ {
/*   34 */   boolean isStandalone = false;
/*      */   
/*   36 */   private Color bkcolor = new Color(204, 204, 204);
/*      */   
/*      */   private allConstraints lpConstraints;
/*      */   
/*      */   private Objective objectFunction;
/*      */   private float optimalZ;
/*   42 */   private Intersection MaxIntersection = null;
/*   43 */   private int num_constraints = 0;
/*   44 */   private SensData senalysData = null;
/*      */   private JPanel mainPanel;
/*      */   private JPanel copyPanel;
/*      */   private JPanel operatePanel;
/*      */   private JPanel graphPanel;
/*      */   private JPanel graphPanels;
/*      */   private JPanel sensPanel;
/*      */   private JPanel backPanel;
/*      */   private graphCanvasInv gCanvas;
/*      */   private IORLPSensPane sCanvas;
/*      */   private JPanel objectPanel;
/*      */   private JPanel resultPanel;
/*      */   private JPanel consPanel;
/*      */   private JPanel changePanel;
/*      */   private JPanel buttPanel;
/*      */   private JPanel solvePanel;
/*      */   private JPanel analysPanel;
/*   61 */   private JLabel objectLabel; private JLabel subjectLabel; private JLabel noteLabel1; private JLabel resultLabel; private JLabel afterLabel; private JLabel gotoLabel; private JLabel errLabel; private JPanel dymicPanel; private JLabel motionLabel; private JLabel solidLabel; private JLabel dragLabel1; private JLabel dragLabel2; private JLabel effectLabel1; private JLabel effectLabel2; private JLabel copyLabel; private JLabel devLabel; private JTextField obj1Coef; private JTextField obj2Coef; private DecimalField x1Coef; private DecimalField x2Coef; private DecimalField rightCoef; private DecimalFormat decfm = new DecimalFormat();
/*      */   private JButton addButt;
/*      */   private JButton delButt;
/*      */   private JButton optmButt;
/*      */   private JButton resetButt;
/*   66 */   private JButton sensButt; private JButton backButt; private JButton okButt; private JComboBox maxComb; private JComboBox inequComb; private JComboBox consComb; private GridBagLayout gblay = new GridBagLayout(); private GridBagLayout dg = new GridBagLayout();
/*   67 */   private GridBagConstraints gbcon = new GridBagConstraints(); private GridBagConstraints dbc = new GridBagConstraints();
/*      */   
/*      */ 
/*      */   private JPanel equationPanel;
/*      */   
/*   72 */   private GridLayout gridlay = new GridLayout(5, 1);
/*   73 */   private JLabel[] equationLabel = new JLabel[5];
/*   74 */   private String[] equationStr = new String[5];
/*      */   
/*      */   private POMTablePanel1 coefTable;
/*      */   private POMTablePanel1 rhsTable;
/*      */   private JLabel cotabLabel;
/*      */   private JLabel contabLabel;
/*      */   private float testobjc1;
/*      */   private float testobjc2;
/*   82 */   private JPanel showCon = new JPanel();
/*   83 */   private JPanel interactivePane = new JPanel();
/*   84 */   private JPanel showSen = new JPanel();
/*   85 */   public boolean showresult = false;
/*   86 */   private boolean showsens = false;
/*      */   
/*   88 */   private JPanel leftPanel = new JPanel();
/*      */   
/*   90 */   private JLabel infoLab1 = new JLabel("Constraint #1");
/*   91 */   private JLabel infoLab2 = new JLabel("Enter the following for the");
/*   92 */   private JLabel infoLab3 = new JLabel("constraint-boundary line");
/*   93 */   private JLabel infoLab4 = new JLabel("for constraint #1.");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   99 */   private JLabel zLab = new JLabel("Z = ");
/*  100 */   private JTextField zText = new JTextField();
/*      */   
/*  102 */   private JLabel x1Lab = new JLabel("X1 - Intercept (/ if none) : ");
/*  103 */   private JTextField x1Text = new JTextField("0");
/*  104 */   private float x1Value = 0.0F;
/*      */   
/*  106 */   private JLabel x2Lab = new JLabel("X2 - Intercept (/ if none) : ");
/*  107 */   private JTextField x2Text = new JTextField("0");
/*  108 */   private float x2Value = 0.0F;
/*      */   
/*  110 */   private JLabel slopeLab = new JLabel("Slope of the line : ");
/*  111 */   private JTextField slopeText = new JTextField("0");
/*  112 */   private float slopeValue = 0.0F;
/*      */   
/*  114 */   private JButton nextBut = new JButton("Next");
/*  115 */   private JButton backBut = new JButton("Back");
/*      */   
/*      */   private IORLPProcessController controller;
/*      */   
/*  119 */   private JLabel isFeasibleLab = new JLabel("Is (0,0) on feasible side (Y or N)? ");
/*  120 */   private JTextField isFeasibleText = new JTextField("");
/*  121 */   private Vector constraintV = new Vector();
/*  122 */   private int constraintNum = 0;
/*      */   public NumberFormat nf;
/*  124 */   private int step = -1;
/*  125 */   private static final int z = 1;
/*      */   
/*  127 */   private float objFunX1 = 0.0F;
/*  128 */   private float objFunX2 = 0.0F;
/*  129 */   private int rightFunNum = 0;
/*  130 */   public float input_Z = 0.0F;
/*      */   private JPanel currentPane;
/*  132 */   private int nextCounter = 0;
/*      */   
/*  134 */   public Point startPoint = new Point();
/*  135 */   public Point stopPoint = new Point();
/*      */   
/*  137 */   public boolean isShowLine = false;
/*      */   
/*      */ 
/*      */   public void updatePanel() {}
/*      */   
/*      */   protected void backStep() {}
/*      */   
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/*  146 */     String str = "Solve Interactively by the Graphical Method.";
/*  147 */     str = String.valueOf(String.valueOf(str)).concat("\n\nEnter the objective function.");
/*  148 */     str = String.valueOf(String.valueOf(str)).concat("\nSelect Max or Min from the pull-down list; enter coefficients for x1 and x2 in the respective text");
/*  149 */     str = String.valueOf(String.valueOf(str)).concat("fields.");
/*      */     
/*  151 */     str = String.valueOf(String.valueOf(str)).concat("\n\nAdd a constraint");
/*  152 */     str = String.valueOf(String.valueOf(str)).concat("\nEnter coefficients for x1, x2, and the RHS in the respective text");
/*  153 */     str = String.valueOf(String.valueOf(str)).concat("fields; select >=, <=, or = from the pull-down list; click “Add” to add the constraint.");
/*      */     
/*  155 */     str = String.valueOf(String.valueOf(str)).concat("\nDelete a constraint");
/*  156 */     str = String.valueOf(String.valueOf(str)).concat("Select a constraint ID from the pull-down list; click “Delete” to remove the corresponding constraint.");
/*      */     
/*  158 */     str = String.valueOf(String.valueOf(str)).concat("\nStudy constraint-boundary lines");
/*  159 */     str = String.valueOf(String.valueOf(str)).concat("For each constraint-boundary line, compute the x1-intercept and x2-intercept (the slope is ");
/*  160 */     str = String.valueOf(String.valueOf(str)).concat("computed automatically); and then decide whether (0, 0) or (1, 1) is on the feasible side. If your");
/*  161 */     str = String.valueOf(String.valueOf(str)).concat("answers are correct, you will be led to the next step. You can click “Back” to go to the previous step.");
/*      */     
/*      */ 
/*  164 */     str = String.valueOf(String.valueOf(str)).concat("\nStudy the objective function line");
/*  165 */     str = String.valueOf(String.valueOf(str)).concat("Compute the x1-intercept and x2-intercept for the objective function line. If your answers are ");
/*  166 */     str = String.valueOf(String.valueOf(str)).concat("correct, you will be led to the next step. You can click “Back” to go to the previous step.");
/*      */     
/*      */ 
/*  169 */     str = String.valueOf(String.valueOf(str)).concat("\nReset the problem");
/*  170 */     str = String.valueOf(String.valueOf(str)).concat("Click “Reset” to reset the problem. You can then enter and solve a new problem.");
/*      */     
/*      */ 
/*  173 */     str = String.valueOf(String.valueOf(str)).concat("\nSensitivity analysis");
/*  174 */     str = String.valueOf(String.valueOf(str)).concat("After the optimal solution is reached, you can perform the sensitivity analysis interactively on the ");
/*  175 */     str = String.valueOf(String.valueOf(str)).concat("graph. Drag the green triangles at the end of the objective function line to see how far objective function coefficients can change without changing the optimal solution.");
/*      */     
/*  177 */     str = String.valueOf(String.valueOf(str)).concat("\n\nPress the Enter key to continue the current procedure.");
/*      */     
/*  179 */     this.help_content_step.setText(str);
/*  180 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/*  185 */     String str = "Solve Interactively by the Graphical Method.";
/*  186 */     str = String.valueOf(String.valueOf(str)).concat("\n\nThis procedure ask you to determine the intercepts for each constraint boundary line and the ");
/*  187 */     str = String.valueOf(String.valueOf(str)).concat("original objective function line, and then shift the objective function line until it passes through the ");
/*  188 */     str = String.valueOf(String.valueOf(str)).concat("optimal solution(s). After the optimal solution is reached, you can perform the sensitivity analysis ");
/*  189 */     str = String.valueOf(String.valueOf(str)).concat("interactively on the graph.");
/*      */     
/*      */ 
/*  192 */     str = String.valueOf(String.valueOf(str)).concat("\n\nPress the Enter key to continue the current procedure.");
/*      */     
/*  194 */     this.help_content_procedure.setText(str);
/*  195 */     this.help_content_procedure.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void finishProcedure() {}
/*      */   
/*      */ 
/*      */   public void LoadFile(ObjectInputStream in)
/*      */   {
/*  205 */     resetProb();
/*  206 */     reset();
/*  207 */     Vector p = new Vector();
/*      */     try
/*      */     {
/*  210 */       p = (Vector)in.readObject();
/*  211 */       in.close();
/*      */       
/*  213 */       this.obj1Coef.setText((String)p.get(0));
/*  214 */       this.obj2Coef.setText((String)p.get(1));
/*  215 */       this.constraintV = ((Vector)p.get(2));
/*      */       
/*  217 */       for (int i = 0; i < this.constraintV.size(); i++)
/*      */       {
/*  219 */         float[] tempS = (float[])this.constraintV.elementAt(i);
/*  220 */         Constraint newcons = new Constraint(tempS[0], tempS[1], tempS[2], (int)tempS[3]);
/*  221 */         this.num_constraints += 1;
/*  222 */         this.consComb.addItem(new String(String.valueOf(String.valueOf(this.num_constraints)).concat("  ")));
/*  223 */         this.equationStr[(this.num_constraints - 1)] = newcons.toString();
/*  224 */         this.equationLabel[(this.num_constraints - 1)].setText(String.valueOf(String.valueOf(new StringBuffer("(").append(this.num_constraints).append(")").append(this.equationStr[(this.num_constraints - 1)]))));
/*      */       }
/*  226 */       if (this.constraintV.size() > 0) {
/*  227 */         this.step = 1;
/*  228 */         this.x1Text.setEnabled(true);
/*      */       } else {
/*  230 */         this.step = -1;
/*  231 */         this.x1Text.setEnabled(false);
/*      */       }
/*      */       
/*  234 */       this.equationPanel.repaint();
/*  235 */       this.gCanvas.setConstraints(this.lpConstraints);
/*  236 */       this.gCanvas.clearOptimal();
/*  237 */       this.gCanvas.repaint();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  241 */       e.printStackTrace();
/*  242 */       System.out.println("aOpen fails");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void initProblem()
/*      */   {
/*  249 */     this.obj1Coef.setText("1");
/*  250 */     this.obj2Coef.setText("1");
/*      */     
/*      */ 
/*  253 */     this.x1Coef.setText("2");
/*  254 */     this.x2Coef.setText("4");
/*  255 */     this.rightCoef.setText("8");
/*  256 */     addConstraint();
/*      */     
/*      */ 
/*      */ 
/*  260 */     this.x1Coef.setText("4");
/*  261 */     this.x2Coef.setText("2");
/*  262 */     this.rightCoef.setText("8");
/*  263 */     addConstraint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setprintData()
/*      */   {
/*  270 */     String tobj1cf = this.obj1Coef.getText();
/*  271 */     String tobj2cf = this.obj2Coef.getText();
/*  272 */     int type = this.maxComb.getSelectedIndex();
/*  273 */     String stype = "";
/*      */     
/*  275 */     if (type == 0) stype = "Max"; else
/*  276 */       stype = "Min";
/*  277 */     this.controller.solver.gdata.objfunc = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(stype))).append(" Z = ").append(tobj1cf).append("x1 + ").append(tobj2cf).append("x2")));
/*      */     
/*      */ 
/*  280 */     this.controller.solver.gdata.lp = this.lpConstraints;
/*      */     
/*      */ 
/*  283 */     this.controller.solver.gdata.showresult = this.showresult;
/*  284 */     this.controller.solver.gdata.result = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.motionLabel.getText()))).append("    ").append(this.solidLabel.getText())));
/*      */     
/*      */ 
/*  287 */     this.controller.solver.gdata.showsens = this.showsens;
/*  288 */     this.controller.solver.gdata.senalysData = this.senalysData;
/*      */   }
/*      */   
/*      */   public void SaveFile(ObjectOutputStream out) {
/*  292 */     String tobj1cf = this.obj1Coef.getText();
/*  293 */     String tobj2cf = this.obj2Coef.getText();
/*      */     
/*  295 */     Vector p = new Vector();
/*  296 */     p.addElement(tobj1cf);
/*  297 */     p.addElement(tobj2cf);
/*  298 */     p.addElement(this.constraintV);
/*      */     try
/*      */     {
/*  301 */       out.writeObject(p);
/*  302 */       out.close();
/*      */     } catch (Exception e) {
/*  304 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public IORLPLinearProgPane(IORLPProcessController c)
/*      */   {
/*  312 */     super(c);
/*  313 */     this.nf = NumberFormat.getInstance();
/*  314 */     this.nf.setMaximumIntegerDigits(3);
/*  315 */     this.controller = c;
/*  316 */     this.lpConstraints = new allConstraints();
/*  317 */     this.decfm.setGroupingUsed(false);
/*  318 */     this.decfm.setMaximumFractionDigits(2);
/*  319 */     init();
/*  320 */     this.currentPane = this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void init()
/*      */   {
/*      */     try
/*      */     {
/*  330 */       jbInit();
/*      */     }
/*      */     catch (Exception e) {
/*  333 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void jbInit()
/*      */     throws Exception
/*      */   {
/*  341 */     setLayout(new BorderLayout());
/*  342 */     setBackground(this.bkcolor);
/*  343 */     this.sensPanel = new JPanel();
/*  344 */     this.sensPanel.setBackground(this.bkcolor);
/*      */     
/*  346 */     this.backButt = new JButton("Back");
/*  347 */     this.backButt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  349 */         IORLPLinearProgPane.this.turnBack();
/*      */       }
/*      */       
/*  352 */     });
/*  353 */     this.dragLabel1 = new JLabel("Drag the green triangles with the");
/*  354 */     this.dragLabel2 = new JLabel("mouse to see how far objective");
/*      */     
/*  356 */     this.effectLabel1 = new JLabel("function coefficients can change");
/*  357 */     this.effectLabel2 = new JLabel("without changing the optimal solution.");
/*      */     
/*  359 */     this.cotabLabel = new JLabel("Objective Coefficient");
/*      */     
/*      */ 
/*  362 */     this.contabLabel = new JLabel("Coefficient:");
/*      */     
/*  364 */     this.backPanel = new JPanel();
/*  365 */     this.backPanel.add(this.backButt);
/*      */     
/*  367 */     this.operatePanel = new JPanel();
/*  368 */     this.operatePanel.setBackground(this.bkcolor);
/*  369 */     this.operatePanel.setLayout(this.gblay);
/*      */     
/*  371 */     this.objectLabel = new JLabel("Objective Function:");
/*  372 */     this.objectPanel = new JPanel();
/*  373 */     this.maxComb = new JComboBox();
/*  374 */     this.maxComb.addItem("Max");
/*  375 */     this.maxComb.addItem("Min");
/*  376 */     this.objectPanel.add(this.maxComb);
/*  377 */     this.objectPanel.add(new JLabel("Z = "));
/*      */     
/*  379 */     this.subjectLabel = new JLabel("Subject to: x1 >= 0 , x2 >= 0");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  384 */     this.obj1Coef = new JTextField("0", 4);
/*  385 */     this.obj2Coef = new JTextField("0", 4);
/*  386 */     this.x1Coef = new DecimalField(0.0D, this.decfm);
/*  387 */     this.x2Coef = new DecimalField(0.0D, this.decfm);
/*  388 */     this.rightCoef = new DecimalField(0.0D, this.decfm);
/*  389 */     this.objectPanel.add(this.obj1Coef);
/*  390 */     this.objectPanel.add(new JLabel("x1 +"));
/*  391 */     this.objectPanel.add(this.obj2Coef);
/*  392 */     this.objectPanel.add(new JLabel("x2"));
/*      */     
/*  394 */     this.noteLabel1 = new JLabel("Note: The number of constraints can't be more than 5");
/*      */     
/*  396 */     this.addButt = new JButton("Add");
/*  397 */     this.delButt = new JButton("Delete");
/*  398 */     this.consComb = new JComboBox();
/*      */     
/*  400 */     this.changePanel = new JPanel();
/*  401 */     this.changePanel.add(new JLabel("Constraint"));
/*      */     
/*  403 */     this.buttPanel = new JPanel();
/*  404 */     this.buttPanel.setLayout(null);
/*  405 */     this.addButt.setBounds(0, 5, 60, 25);
/*  406 */     this.buttPanel.add(this.addButt);
/*  407 */     this.delButt.setBounds(70, 5, 80, 25);
/*  408 */     this.buttPanel.add(this.delButt);
/*  409 */     this.consComb.setBounds(160, 5, 50, 25);
/*  410 */     this.buttPanel.add(this.consComb);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  417 */     this.addButt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  419 */         IORLPLinearProgPane.this.addConstraint();
/*      */       }
/*  421 */     });
/*  422 */     this.delButt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  424 */         IORLPLinearProgPane.this.delConstraint();
/*      */       }
/*      */     });
/*      */     
/*  428 */     if (this.num_constraints == 0) {
/*  429 */       this.delButt.setEnabled(false);
/*      */     }
/*  431 */     this.optmButt = new JButton("Solve");
/*  432 */     this.optmButt.setEnabled(false);
/*  433 */     this.optmButt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  435 */         IORLPLinearProgPane.this.getResult();
/*      */       }
/*      */       
/*  438 */     });
/*  439 */     this.resetButt = new JButton("Reset");
/*  440 */     this.resetButt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  442 */         IORLPLinearProgPane.this.resetProb();
/*  443 */         IORLPLinearProgPane.this.reset();
/*      */       }
/*      */       
/*  446 */     });
/*  447 */     this.solvePanel = new JPanel();
/*  448 */     this.solvePanel.add(this.optmButt);
/*  449 */     this.solvePanel.add(new JLabel("        "));
/*  450 */     this.solvePanel.add(this.resetButt);
/*      */     
/*  452 */     this.afterLabel = new JLabel("After the optimal solution is reached, you can do ");
/*  453 */     this.gotoLabel = new JLabel("sensitivity analysis by pressing the button below.");
/*      */     
/*  455 */     this.sensButt = new JButton("Sensitivity Analysis");
/*  456 */     this.sensButt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  458 */         IORLPLinearProgPane.this.SensAnalysis();
/*      */       }
/*  460 */     });
/*  461 */     this.sensButt.setEnabled(false);
/*      */     
/*      */ 
/*  464 */     this.analysPanel = new JPanel();
/*  465 */     this.analysPanel.add(this.sensButt);
/*      */     
/*  467 */     this.resultLabel = new JLabel(" ");
/*      */     
/*      */ 
/*  470 */     this.resultPanel = new JPanel();
/*  471 */     this.resultPanel.setLayout(new GridLayout(1, 1));
/*  472 */     this.resultPanel.add(this.resultLabel);
/*      */     
/*      */ 
/*  475 */     this.inequComb = new JComboBox();
/*  476 */     this.inequComb.addItem("<=");
/*  477 */     this.inequComb.addItem(">=");
/*  478 */     this.inequComb.addItem("=");
/*      */     
/*  480 */     this.consPanel = new JPanel();
/*  481 */     this.consPanel.add(this.x1Coef);
/*  482 */     this.consPanel.add(new JLabel("x1 +"));
/*  483 */     this.consPanel.add(this.x2Coef);
/*  484 */     this.consPanel.add(new JLabel("x2"));
/*  485 */     this.consPanel.add(this.inequComb);
/*  486 */     this.consPanel.add(this.rightCoef);
/*      */     
/*  488 */     this.equationPanel = new JPanel();
/*  489 */     this.equationPanel.setLayout(this.gridlay);
/*  490 */     for (int i = 0; i < 5; i++)
/*      */     {
/*  492 */       this.equationLabel[i] = new JLabel("  ");
/*  493 */       this.equationPanel.add(this.equationLabel[i]);
/*      */     }
/*      */     
/*      */ 
/*  497 */     buildOperPanel();
/*      */     
/*  499 */     this.graphPanel = new JPanel();
/*  500 */     this.graphPanel.setLayout(null);
/*  501 */     this.graphPanel.setBackground(this.bkcolor);
/*      */     
/*  503 */     this.gCanvas = new graphCanvasInv(this);
/*  504 */     this.gCanvas.setBounds(0, 0, 340, 340);
/*  505 */     this.gCanvas.setBackground(Color.white);
/*  506 */     this.graphPanel.add(this.gCanvas);
/*  507 */     this.resultPanel.setBounds(20, 340, 300, 30);
/*  508 */     this.graphPanel.add(this.resultPanel);
/*      */     
/*  510 */     this.mainPanel = new JPanel();
/*  511 */     this.mainPanel.setLayout(null);
/*      */     
/*  513 */     this.operatePanel.setBounds(10, 10, 600, 80);
/*  514 */     this.mainPanel.add(this.operatePanel);
/*      */     
/*      */ 
/*  517 */     this.showCon.setBounds(23, 135, 245, 340);
/*  518 */     this.showCon.setBackground(this.bkcolor);
/*  519 */     this.mainPanel.add(this.showCon);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  524 */     this.graphPanel.setBounds(270, 115, 340, 350);
/*  525 */     this.mainPanel.add(this.graphPanel);
/*      */     
/*  527 */     this.showSen.setBounds(110, 520, 400, 80);
/*  528 */     this.mainPanel.add(this.showSen);
/*      */     
/*  530 */     this.noteLabel1.setBounds(150, 85, 560, 20);
/*  531 */     this.mainPanel.add(this.noteLabel1);
/*      */     
/*  533 */     this.subjectLabel.setBounds(20, 110, 260, 20);
/*  534 */     this.mainPanel.add(this.subjectLabel);
/*      */     
/*  536 */     this.solvePanel.setBounds(180, 475, 250, 40);
/*  537 */     this.mainPanel.add(this.solvePanel);
/*      */     
/*      */ 
/*  540 */     this.copyLabel = new JLabel("© 2002 McGraw-Hill, Inc. All rights reserved.");
/*  541 */     this.devLabel = new JLabel("Developed by Accelet Corp.");
/*  542 */     Font curFont = getFont();
/*  543 */     Font bdFont = new Font(curFont.getName(), 1, curFont.getSize());
/*  544 */     this.copyLabel.setFont(bdFont);
/*  545 */     this.copyLabel.setForeground(Color.red);
/*  546 */     this.copyPanel = new JPanel();
/*  547 */     this.copyPanel.add(this.copyLabel);
/*  548 */     this.copyPanel.add(this.devLabel);
/*      */     
/*  550 */     add(this.mainPanel, "Center");
/*      */     
/*  552 */     setVisible(true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void buildOperPanel()
/*      */   {
/*  562 */     this.operatePanel.setLayout(null);
/*  563 */     this.objectLabel.setBounds(10, 5, 150, 20);
/*      */     
/*  565 */     this.operatePanel.add(this.objectLabel);
/*      */     
/*  567 */     this.objectPanel.setBounds(160, 0, 300, 40);
/*  568 */     this.operatePanel.add(this.objectPanel);
/*      */     
/*  570 */     this.changePanel.setBounds(0, 35, 100, 30);
/*  571 */     this.operatePanel.add(this.changePanel);
/*      */     
/*  573 */     this.buttPanel.setBounds(110, 35, 220, 40);
/*  574 */     this.operatePanel.add(this.buttPanel);
/*      */     
/*  576 */     this.consPanel.setBounds(310, 35, 300, 40);
/*  577 */     this.operatePanel.add(this.consPanel);
/*      */     
/*      */ 
/*      */ 
/*  581 */     this.showCon.setLayout(null);
/*  582 */     int w = 245;
/*  583 */     this.showCon.setSize(w, 340);
/*      */     
/*      */ 
/*      */ 
/*  587 */     this.equationPanel.setBounds(0, 0, w, 120);
/*  588 */     this.showCon.add(this.equationPanel);
/*      */     
/*      */ 
/*      */ 
/*  592 */     this.interactivePane.setLayout(null);
/*  593 */     this.interactivePane.setBounds(0, 120, w, 210);
/*      */     
/*      */ 
/*  596 */     int labWid = 205;
/*  597 */     this.x1Lab.setBounds(0, 90, labWid, 20);
/*  598 */     this.x2Lab.setBounds(0, 110, labWid, 20);
/*  599 */     this.slopeLab.setBounds(0, 130, labWid, 20);
/*  600 */     this.isFeasibleLab.setBounds(0, 150, labWid, 20);
/*      */     
/*  602 */     int textWid = 40;
/*  603 */     this.x1Text.setBounds(labWid, 90, textWid, 20);
/*  604 */     this.x2Text.setBounds(labWid, 110, textWid, 20);
/*  605 */     this.slopeText.setBounds(labWid, 130, textWid, 20);
/*  606 */     this.isFeasibleText.setBounds(labWid, 150, textWid, 20);
/*      */     
/*  608 */     int infoWid = 250;
/*  609 */     this.infoLab1.setBounds(0, 0, infoWid, 20);
/*  610 */     this.infoLab2.setBounds(0, 20, infoWid, 20);
/*  611 */     this.infoLab3.setBounds(0, 40, infoWid, 20);
/*  612 */     this.infoLab4.setBounds(0, 60, infoWid, 20);
/*      */     
/*  614 */     this.zLab.setBounds(40, 120, 50, 20);
/*  615 */     this.zText.setBounds(90, 120, 80, 20);
/*      */     
/*  617 */     this.zLab.setVisible(false);
/*  618 */     this.zText.setVisible(false);
/*      */     
/*  620 */     this.interactivePane.add(this.x1Lab);
/*  621 */     this.interactivePane.add(this.x2Lab);
/*  622 */     this.interactivePane.add(this.slopeLab);
/*  623 */     this.interactivePane.add(this.isFeasibleLab);
/*      */     
/*  625 */     this.nextBut.setBounds(30, 180, 70, 23);
/*  626 */     this.backBut.setBounds(120, 180, 70, 23);
/*      */     
/*  628 */     this.nextBut.setEnabled(false);
/*  629 */     this.backBut.setEnabled(false);
/*      */     
/*  631 */     this.interactivePane.add(this.x1Text);
/*  632 */     this.interactivePane.add(this.x2Text);
/*  633 */     this.interactivePane.add(this.slopeText);
/*  634 */     this.interactivePane.add(this.nextBut);
/*  635 */     this.interactivePane.add(this.backBut);
/*  636 */     this.interactivePane.add(this.isFeasibleText);
/*      */     
/*      */ 
/*  639 */     this.interactivePane.add(this.zLab);
/*  640 */     this.interactivePane.add(this.zText);
/*      */     
/*  642 */     this.x1Text.setEnabled(false);
/*  643 */     this.x2Text.setEnabled(false);
/*  644 */     this.slopeText.setEnabled(false);
/*  645 */     this.isFeasibleText.setEnabled(false);
/*      */     
/*  647 */     this.interactivePane.add(this.infoLab1);
/*  648 */     this.interactivePane.add(this.infoLab2);
/*  649 */     this.interactivePane.add(this.infoLab3);
/*  650 */     this.interactivePane.add(this.infoLab4);
/*      */     
/*  652 */     this.showCon.add(this.interactivePane);
/*      */     
/*  654 */     int sw = 400;
/*  655 */     this.showSen.setSize(sw, 100);
/*  656 */     this.showSen.setLayout(null);
/*  657 */     this.afterLabel.setBounds(70, 0, sw, 20);
/*  658 */     this.showSen.add(this.afterLabel);
/*  659 */     this.gotoLabel.setBounds(70, 20, sw, 20);
/*  660 */     this.showSen.add(this.gotoLabel);
/*  661 */     this.analysPanel.setBounds(120, 40, 150, 40);
/*  662 */     this.showSen.add(this.analysPanel);
/*      */     
/*  664 */     this.x1Text.addCaretListener(new CaretListener() {
/*      */       public void caretUpdate(CaretEvent e) {
/*  666 */         if (!IORLPLinearProgPane.this.x1Text.isEnabled()) return;
/*  667 */         IORLPLinearProgPane.this.inputX1();
/*      */       }
/*      */       
/*  670 */     });
/*  671 */     this.x2Text.addCaretListener(new CaretListener() {
/*      */       public void caretUpdate(CaretEvent e) {
/*  673 */         if (!IORLPLinearProgPane.this.x2Text.isEnabled()) return;
/*  674 */         IORLPLinearProgPane.this.inputX2();
/*      */       }
/*      */       
/*  677 */     });
/*  678 */     this.isFeasibleText.addCaretListener(new CaretListener() {
/*      */       public void caretUpdate(CaretEvent e) {
/*  680 */         if (!IORLPLinearProgPane.this.isFeasibleText.isEnabled()) return;
/*  681 */         if (IORLPLinearProgPane.this.checkPointSide(IORLPLinearProgPane.this.constraintNum, IORLPLinearProgPane.this.isFeasibleText.getText())) {
/*  682 */           IORLPLinearProgPane.this.nextBut.setEnabled(true);
/*      */         } else {
/*  684 */           IORLPLinearProgPane.this.nextBut.setEnabled(false);
/*      */         }
/*      */         
/*      */       }
/*  688 */     });
/*  689 */     this.zText.addCaretListener(new CaretListener() {
/*      */       public void caretUpdate(CaretEvent e) {
/*      */         try {
/*  692 */           IORLPLinearProgPane.this.input_Z = Float.valueOf(IORLPLinearProgPane.this.zText.getText()).floatValue();
/*      */         } catch (Exception e1) {
/*  694 */           IORLPLinearProgPane.this.input_Z = IORLPLinearProgPane.this.changeFraction(IORLPLinearProgPane.this.zText.getText());
/*      */         }
/*  696 */         if (IORLPLinearProgPane.this.zText.getText().equalsIgnoreCase("")) {
/*  697 */           IORLPLinearProgPane.this.optmButt.setEnabled(false);
/*      */         } else {
/*  699 */           IORLPLinearProgPane.this.optmButt.setEnabled(true);
/*      */         }
/*      */         
/*      */       }
/*  703 */     });
/*  704 */     this.nextBut.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  706 */         IORLPLinearProgPane.this.next();
/*      */       }
/*      */       
/*  709 */     });
/*  710 */     this.backBut.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  712 */         IORLPLinearProgPane.this.back();
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   private void next()
/*      */   {
/*  719 */     this.addButt.setEnabled(false);
/*  720 */     this.delButt.setEnabled(false);
/*  721 */     if (this.step == 1) {
/*  722 */       this.x1Lab.setVisible(true);
/*  723 */       this.x2Lab.setVisible(true);
/*  724 */       this.slopeLab.setVisible(true);
/*  725 */       this.isFeasibleLab.setVisible(true);
/*      */       
/*  727 */       this.x1Text.setVisible(true);
/*  728 */       this.x2Text.setVisible(true);
/*  729 */       this.slopeText.setVisible(true);
/*  730 */       this.isFeasibleText.setVisible(true);
/*      */       
/*  732 */       this.zLab.setVisible(false);
/*  733 */       this.zText.setVisible(false);
/*      */       
/*      */ 
/*  736 */       if (this.constraintNum + 1 == this.constraintV.size())
/*      */       {
/*      */         try {
/*  739 */           float obj1cf = this.decfm.parse(this.obj1Coef.getText()).floatValue();
/*  740 */           obj2cf = this.decfm.parse(this.obj2Coef.getText()).floatValue();
/*      */         } catch (ParseException e) {
/*      */           float obj2cf;
/*  743 */           JOptionPane.showMessageDialog(this, "Please input valid positive number."); return;
/*      */         }
/*      */         float obj2cf;
/*      */         float obj1cf;
/*  747 */         if ((Math.abs(obj1cf) < 0.001D) || (Math.abs(obj2cf) < 0.001D)) {
/*  748 */           JOptionPane.showMessageDialog(this, "Coefficients of objective can't be zero");
/*  749 */           return;
/*      */         }
/*      */         
/*  752 */         step2();
/*  753 */         this.step = 2;
/*      */       }
/*      */       else {
/*  756 */         this.constraintNum += 1;
/*  757 */         step1();
/*  758 */         this.step = 1;
/*      */       }
/*  760 */     } else if (this.step == 2) {
/*  761 */       step3();
/*  762 */       this.step = 3;
/*      */     }
/*  764 */     this.nextBut.setEnabled(false);
/*  765 */     this.backBut.setEnabled(true);
/*  766 */     this.nextCounter += 1;
/*      */   }
/*      */   
/*      */   private void back() {
/*  770 */     if (this.step == 1) {
/*  771 */       int consno = this.constraintNum;
/*  772 */       if (this.lpConstraints.num_of_constraints != 0)
/*  773 */         this.lpConstraints.removeConstraint(consno);
/*  774 */       this.gCanvas.repaint();
/*  775 */       this.constraintNum -= 1;
/*  776 */       step1();
/*  777 */       this.step = 1;
/*  778 */     } else if (this.step == 2) {
/*  779 */       int consno = this.constraintNum + 1;
/*  780 */       if (this.lpConstraints.num_of_constraints != 0)
/*  781 */         this.lpConstraints.removeConstraint(consno);
/*  782 */       this.gCanvas.repaint();
/*  783 */       step1();
/*  784 */       this.step = 1;
/*  785 */     } else if (this.step == 3) {
/*  786 */       step2();
/*  787 */       this.step = 2;
/*      */     }
/*  789 */     this.nextBut.setEnabled(false);
/*  790 */     if (this.nextCounter == 1) {
/*  791 */       this.backBut.setEnabled(false);
/*      */     }
/*      */     
/*  794 */     this.nextCounter -= 1;
/*      */   }
/*      */   
/*      */   private void step1() {
/*  798 */     this.x1Text.setText("0");
/*  799 */     this.x2Text.setText("0");
/*  800 */     this.slopeText.setText("");
/*  801 */     this.isFeasibleText.setText("");
/*      */     
/*  803 */     this.x1Text.setEnabled(true);
/*  804 */     this.x2Text.setEnabled(false);
/*  805 */     this.slopeText.setEnabled(false);
/*  806 */     this.isFeasibleText.setEnabled(false);
/*      */     
/*  808 */     this.x1Lab.setVisible(true);
/*  809 */     this.x2Lab.setVisible(true);
/*  810 */     this.slopeLab.setVisible(true);
/*  811 */     this.isFeasibleLab.setVisible(true);
/*      */     
/*  813 */     this.x1Text.setVisible(true);
/*  814 */     this.x2Text.setVisible(true);
/*  815 */     this.slopeText.setVisible(true);
/*  816 */     this.isFeasibleText.setVisible(true);
/*  817 */     this.zLab.setVisible(false);
/*  818 */     this.zText.setVisible(false);
/*  819 */     this.isFeasibleText.setVisible(true);
/*      */     
/*  821 */     this.infoLab1.setText(String.valueOf(String.valueOf(new StringBuffer("Constraint #").append(this.constraintNum + 1))));
/*  822 */     this.infoLab4.setText(String.valueOf(String.valueOf(new StringBuffer("for constraint #").append(this.constraintNum + 1).append("."))));
/*      */     
/*  824 */     this.infoLab1.setText(String.valueOf(String.valueOf(new StringBuffer("Constraint #").append(this.constraintNum + 1))));
/*  825 */     this.infoLab4.setText(String.valueOf(String.valueOf(new StringBuffer("for constraint #").append(this.constraintNum + 1).append("."))));
/*      */   }
/*      */   
/*      */   private void step3() {
/*  829 */     this.infoLab1.setText("Now try different values for the objective");
/*  830 */     this.infoLab2.setText("function until you have found the");
/*  831 */     this.infoLab3.setText("optimal solution. Press “Solve”");
/*  832 */     this.infoLab4.setText("after you enter a value.");
/*      */     
/*  834 */     this.x1Lab.setVisible(false);
/*  835 */     this.x2Lab.setVisible(false);
/*  836 */     this.slopeLab.setVisible(false);
/*  837 */     this.isFeasibleLab.setVisible(false);
/*      */     
/*  839 */     this.x1Text.setVisible(false);
/*  840 */     this.x2Text.setVisible(false);
/*  841 */     this.slopeText.setVisible(false);
/*  842 */     this.isFeasibleText.setVisible(false);
/*      */     
/*  844 */     this.zLab.setVisible(true);
/*  845 */     this.zText.setVisible(true);
/*      */   }
/*      */   
/*      */   private void step2() {
/*  849 */     this.infoLab1.setText("Objective Function Line");
/*  850 */     this.infoLab2.setText("Enter the following for the objective");
/*      */     
/*      */ 
/*      */ 
/*  854 */     this.infoLab3.setText("function line when Z=1.");
/*  855 */     this.infoLab4.setText("");
/*      */     
/*  857 */     this.x1Lab.setVisible(true);
/*  858 */     this.x2Lab.setVisible(true);
/*  859 */     this.slopeLab.setVisible(true);
/*      */     
/*  861 */     this.x1Text.setVisible(true);
/*  862 */     this.x2Text.setVisible(true);
/*  863 */     this.slopeText.setVisible(true);
/*      */     
/*  865 */     this.zText.setVisible(false);
/*  866 */     this.zLab.setVisible(false);
/*  867 */     this.isFeasibleText.setVisible(false);
/*  868 */     this.isFeasibleLab.setVisible(false);
/*  869 */     this.x1Text.setText("0");
/*  870 */     this.x2Text.setText("0");
/*  871 */     this.slopeText.setText("");
/*  872 */     this.x1Text.setEnabled(true);
/*  873 */     this.x2Text.setEnabled(false);
/*  874 */     this.slopeText.setEnabled(false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean checkX1Conefficient(int selNum)
/*      */   {
/*  881 */     float[] temp = (float[])this.constraintV.elementAt(selNum);
/*  882 */     if (temp[0] == 0) {
/*  883 */       return true;
/*      */     }
/*  885 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean checkX2Conefficient(int selNum)
/*      */   {
/*  891 */     float[] temp = (float[])this.constraintV.elementAt(selNum);
/*  892 */     if (temp[1] == 0) {
/*  893 */       return true;
/*      */     }
/*  895 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   private void inputX1()
/*      */   {
/*      */     try
/*      */     {
/*  903 */       float tempX1 = Float.valueOf(this.x1Text.getText()).floatValue();
/*  904 */       if (this.step == 1) {
/*  905 */         if (tempX1 == getX1Intercept(this.constraintNum)) {
/*  906 */           this.x2Text.setEnabled(true);
/*      */         }
/*      */         else {
/*  909 */           this.x2Text.setEnabled(false);
/*      */         }
/*  911 */       } else if (this.step == 2) {
/*  912 */         if (getX1objFun(tempX1)) {
/*  913 */           this.x2Text.setEnabled(true);
/*      */         } else {
/*  915 */           this.x2Text.setEnabled(false);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e1)
/*      */     {
/*  921 */       if (this.x1Text.getText().equalsIgnoreCase("/")) {
/*  922 */         if (checkX1Conefficient(this.constraintNum)) {
/*  923 */           this.x2Text.setEnabled(true);
/*  924 */           return;
/*      */         }
/*  926 */         this.x2Text.setEnabled(false);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  931 */       float fraction = changeFraction(this.x1Text.getText());
/*  932 */       if (fraction == Float.MAX_VALUE) {
/*  933 */         this.x2Text.setEnabled(false);
/*      */       }
/*  935 */       else if (this.step == 1) {
/*  936 */         if (fraction == getX1Intercept(this.constraintNum)) {
/*  937 */           this.x2Text.setEnabled(true);
/*      */         }
/*      */         else {
/*  940 */           this.x2Text.setEnabled(false);
/*      */         }
/*      */       }
/*  943 */       else if (getX1objFun(fraction)) {
/*  944 */         this.x2Text.setEnabled(true);
/*      */       } else {
/*  946 */         this.x2Text.setEnabled(false);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void inputX2()
/*      */   {
/*      */     try
/*      */     {
/*  957 */       float tempX2 = Float.valueOf(this.x2Text.getText()).floatValue();
/*  958 */       if (this.step == 1) {
/*  959 */         if (tempX2 == getX2Intercept(this.constraintNum)) {
/*  960 */           this.slopeText.setText(this.nf.format(getSlopeValue(this.constraintNum)));
/*  961 */           this.isFeasibleText.setEnabled(true);
/*  962 */           drawConstraint(this.constraintNum);
/*  963 */           repaint();
/*      */         }
/*      */         else {
/*  966 */           this.isFeasibleText.setEnabled(false);
/*  967 */           this.slopeText.setText("0");
/*  968 */           this.nextBut.setEnabled(false);
/*      */         }
/*  970 */       } else if (this.step == 2) {
/*  971 */         if (getX2objFun(tempX2)) {
/*  972 */           this.slopeText.setText("".concat(String.valueOf(String.valueOf(this.nf.format(-this.objFunX2 / this.objFunX1)))));
/*  973 */           this.nextBut.setEnabled(true);
/*      */         } else {
/*  975 */           this.nextBut.setEnabled(false);
/*  976 */           this.slopeText.setText("");
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e1) {
/*  981 */       if ((this.step == 1) && 
/*  982 */         (this.x2Text.getText().equalsIgnoreCase("/"))) {
/*  983 */         if (checkX2Conefficient(this.constraintNum)) {
/*  984 */           this.slopeText.setText(this.nf.format(getSlopeValue(this.constraintNum)));
/*  985 */           this.isFeasibleText.setEnabled(true);
/*  986 */           drawConstraint(this.constraintNum);
/*  987 */           repaint();
/*  988 */           return;
/*      */         }
/*  990 */         this.isFeasibleText.setEnabled(false);
/*  991 */         this.slopeText.setText("");
/*  992 */         this.nextBut.setEnabled(false);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  998 */       float fraction = changeFraction(this.x2Text.getText());
/*  999 */       if (fraction == Float.MAX_VALUE) {
/* 1000 */         this.isFeasibleText.setEnabled(false);
/* 1001 */         this.slopeText.setText("");
/*      */       }
/* 1003 */       else if (this.step == 1) {
/* 1004 */         if (fraction == getX2Intercept(this.constraintNum)) {
/* 1005 */           this.slopeText.setText(this.nf.format(getSlopeValue(this.constraintNum)));
/* 1006 */           this.isFeasibleText.setEnabled(true);
/* 1007 */           drawConstraint(this.constraintNum);
/* 1008 */           repaint();
/*      */         }
/*      */         else {
/* 1011 */           this.isFeasibleText.setEnabled(false);
/* 1012 */           this.slopeText.setText("");
/* 1013 */           this.nextBut.setEnabled(false);
/*      */         }
/* 1015 */       } else if (this.step == 2) {
/* 1016 */         if (getX2objFun(fraction)) {
/* 1017 */           this.slopeText.setText("".concat(String.valueOf(String.valueOf(this.nf.format(-this.objFunX2 / this.objFunX1)))));
/* 1018 */           this.nextBut.setEnabled(true);
/*      */         } else {
/* 1020 */           this.nextBut.setEnabled(false);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void reset()
/*      */   {
/* 1029 */     if (this.constraintV != null) {
/* 1030 */       this.constraintV = null;
/*      */     }
/* 1032 */     this.constraintV = new Vector();
/* 1033 */     this.constraintNum = 0;
/* 1034 */     step1();
/* 1035 */     this.x1Text.setEnabled(false);
/*      */   }
/*      */   
/*      */   private boolean checkPointSide(int selNum, String inputValue) {
/* 1039 */     boolean onFeasibleSide = false;
/*      */     
/* 1041 */     float[] temp = (float[])this.constraintV.elementAt(selNum);
/*      */     
/* 1043 */     if (((inputValue.toLowerCase().equalsIgnoreCase("y")) && (temp[2] >= 0)) || ((inputValue.toLowerCase().equalsIgnoreCase("n")) && (temp[2] <= 0)))
/*      */     {
/*      */ 
/* 1046 */       onFeasibleSide = true;
/*      */     } else {
/* 1048 */       onFeasibleSide = false;
/*      */     }
/*      */     
/* 1051 */     return onFeasibleSide;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private float getX1Intercept(int selNum)
/*      */   {
/* 1058 */     float[] temp = (float[])this.constraintV.elementAt(selNum);
/* 1059 */     return temp[2] / temp[0];
/*      */   }
/*      */   
/*      */   private boolean getX1objFun(float inputValue) {
/*      */     boolean bool;
/*      */     try {
/* 1065 */       float obj1 = Float.valueOf(this.obj1Coef.getText()).floatValue();
/* 1066 */       if (inputValue == 1 / obj1) {
/* 1067 */         this.objFunX1 = inputValue;
/* 1068 */         return true;
/*      */       }
/* 1070 */       return false;
/*      */     }
/*      */     catch (Exception e) {
/* 1073 */       bool = false; } return bool;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private float getX2Intercept(int selNum)
/*      */   {
/* 1080 */     float[] temp = (float[])this.constraintV.elementAt(selNum);
/* 1081 */     return temp[2] / temp[1];
/*      */   }
/*      */   
/*      */   private boolean getX2objFun(float inputValue) {
/*      */     boolean bool;
/* 1086 */     try { float obj2 = Float.valueOf(this.obj2Coef.getText()).floatValue();
/* 1087 */       if (inputValue == 1 / obj2) {
/* 1088 */         this.objFunX2 = inputValue;
/* 1089 */         return true;
/*      */       }
/* 1091 */       return false;
/*      */     }
/*      */     catch (Exception e) {
/* 1094 */       bool = false; } return bool;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private float getSlopeValue(int selNum)
/*      */   {
/* 1106 */     float[] temp = (float[])this.constraintV.elementAt(selNum);
/* 1107 */     float retValue = -temp[0] / temp[1];
/*      */     
/* 1109 */     return retValue;
/*      */   }
/*      */   
/*      */   private void drawConstraint(int selNum)
/*      */   {
/* 1114 */     float[] temp = (float[])this.constraintV.elementAt(selNum);
/* 1115 */     Constraint newcons = new Constraint(temp[0], temp[1], temp[2], (int)temp[3]);
/* 1116 */     this.lpConstraints.addConstraint(newcons);
/*      */   }
/*      */   
/*      */   private boolean checkFraction(String value)
/*      */   {
/* 1121 */     boolean isFraction = false;
/* 1122 */     float numerator = 0.0F;
/* 1123 */     float denominator = 0.0F;
/*      */     
/*      */ 
/* 1126 */     String line = "/";
/* 1127 */     for (int i = 0; i < value.length() - 1; i++) {
/* 1128 */       String temp = value.substring(i, i + 1);
/* 1129 */       if (temp.equalsIgnoreCase(line)) {
/* 1130 */         isFraction = true;
/*      */       }
/*      */     }
/*      */     
/* 1134 */     if (isFraction) {
/* 1135 */       String numeratorStr = value.substring(0, value.indexOf("/"));
/* 1136 */       String denominatorStr = value.substring(value.indexOf("/") + 1, value.length());
/*      */       try {
/* 1138 */         numerator = Float.valueOf(numeratorStr).floatValue();
/* 1139 */         denominator = Float.valueOf(denominatorStr).floatValue();
/*      */       } catch (Exception e) {
/* 1141 */         isFraction = false;
/*      */       }
/*      */     }
/*      */     
/* 1145 */     return isFraction;
/*      */   }
/*      */   
/*      */   private float changeFraction(String value)
/*      */   {
/* 1150 */     float reF = 0.0F;
/* 1151 */     float numerator = 0.0F;
/* 1152 */     float denominator = 0.0F;
/*      */     
/*      */ 
/* 1155 */     if (checkFraction(value)) {
/* 1156 */       String numeratorStr = value.substring(0, value.indexOf("/"));
/* 1157 */       String denominatorStr = value.substring(value.indexOf("/") + 1, value.length());
/* 1158 */       numerator = Float.valueOf(numeratorStr).floatValue();
/* 1159 */       denominator = Float.valueOf(denominatorStr).floatValue();
/* 1160 */       reF = numerator / denominator;
/*      */     } else {
/* 1162 */       reF = Float.MAX_VALUE;
/*      */     }
/*      */     
/* 1165 */     return reF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void resetProb()
/*      */   {
/* 1177 */     this.showresult = false;
/* 1178 */     this.showsens = false;
/*      */     
/* 1180 */     this.obj1Coef.setText("0");
/* 1181 */     this.obj2Coef.setText("0");
/* 1182 */     this.x1Coef.setValue(0.0D);
/* 1183 */     this.x2Coef.setValue(0.0D);
/* 1184 */     this.rightCoef.setValue(0.0D);
/*      */     
/* 1186 */     this.lpConstraints = null;
/* 1187 */     this.lpConstraints = new allConstraints();
/* 1188 */     this.num_constraints = 0;
/* 1189 */     this.objectFunction = null;
/* 1190 */     this.MaxIntersection = null;
/*      */     
/* 1192 */     this.consComb.removeAllItems();
/* 1193 */     this.addButt.setEnabled(true);
/* 1194 */     this.delButt.setEnabled(false);
/* 1195 */     this.sensButt.setEnabled(false);
/* 1196 */     for (int i = 0; i < 5; i++)
/*      */     {
/* 1198 */       this.equationLabel[i].setText("  ");
/*      */     }
/* 1200 */     this.resultLabel.setText(" ");
/* 1201 */     this.operatePanel.validate();
/* 1202 */     this.operatePanel.repaint();
/*      */     
/* 1204 */     this.gCanvas.setConstraints(null);
/* 1205 */     this.gCanvas.clearOptimal();
/* 1206 */     this.gCanvas.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void addConstraint()
/*      */   {
/* 1214 */     int selsym = this.inequComb.getSelectedIndex() + 1;
/* 1215 */     float x1_coef = (float)this.x1Coef.getValue();
/* 1216 */     float x2_coef = (float)this.x2Coef.getValue();
/* 1217 */     float right_coef = (float)this.rightCoef.getValue();
/* 1218 */     if ((Math.abs(x1_coef) < 0.001D) && (Math.abs(x2_coef) < 0.001D)) {
/* 1219 */       JOptionPane.showMessageDialog(this, "Coefficients of constraints can't be both negative!");
/* 1220 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1225 */     if ((Math.abs(right_coef) < 0.001D) && ((Math.abs(x1_coef) < 0.001D) || (Math.abs(x2_coef) < 0.001D))) {
/* 1226 */       JOptionPane.showMessageDialog(this, "R.H. side of constraints can't be zero or negative!");
/* 1227 */       return;
/*      */     }
/*      */     
/* 1230 */     this.x1Coef.setValue(0.0D);
/* 1231 */     this.x2Coef.setValue(0.0D);
/* 1232 */     this.rightCoef.setValue(0.0D);
/* 1233 */     float[] tempS = new float[4];
/* 1234 */     tempS[0] = x1_coef;
/* 1235 */     tempS[1] = x2_coef;
/* 1236 */     tempS[2] = right_coef;
/* 1237 */     tempS[3] = selsym;
/* 1238 */     this.constraintV.addElement(tempS);
/*      */     
/*      */ 
/* 1241 */     Constraint newcons = new Constraint(x1_coef, x2_coef, right_coef, selsym);
/*      */     
/* 1243 */     this.num_constraints += 1;
/* 1244 */     this.consComb.addItem(new String(String.valueOf(String.valueOf(this.num_constraints)).concat("  ")));
/* 1245 */     this.equationStr[(this.num_constraints - 1)] = newcons.toString();
/* 1246 */     this.equationLabel[(this.num_constraints - 1)].setText(String.valueOf(String.valueOf(new StringBuffer("(").append(this.num_constraints).append(")").append(this.equationStr[(this.num_constraints - 1)]))));
/*      */     
/*      */ 
/* 1249 */     if (this.num_constraints >= 5) {
/* 1250 */       this.addButt.setEnabled(false);
/* 1251 */     } else if (this.num_constraints > 0) {
/* 1252 */       this.delButt.setEnabled(true);
/*      */     }
/* 1254 */     this.resultLabel.setText(" ");
/* 1255 */     this.equationPanel.validate();
/* 1256 */     this.equationPanel.repaint();
/* 1257 */     this.gCanvas.setConstraints(this.lpConstraints);
/* 1258 */     this.gCanvas.clearOptimal();
/* 1259 */     this.gCanvas.repaint();
/* 1260 */     this.x1Text.setEnabled(true);
/* 1261 */     this.step = 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void backConstraints()
/*      */   {
/* 1268 */     this.lpConstraints.removeConstraint(this.num_constraints);
/* 1269 */     this.num_constraints -= 1;
/*      */     
/* 1271 */     for (int i = 0; i < 5; i++) {
/* 1272 */       this.equationLabel[i].setText(" ");
/*      */     }
/*      */     
/* 1275 */     for (i = 0; i < this.lpConstraints.num_of_constraints; i++)
/*      */     {
/* 1277 */       Constraint thecons = (Constraint)this.lpConstraints.vConstraints.elementAt(i);
/* 1278 */       this.equationLabel[i].setText(String.valueOf(String.valueOf(new StringBuffer("(").append(i + 1).append(") ").append(thecons.toString()))));
/*      */       
/* 1280 */       this.consComb.addItem(new String(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(i + 1))).append("  ")))));
/*      */     }
/*      */     
/* 1283 */     if (this.num_constraints < 5)
/* 1284 */       this.addButt.setEnabled(true);
/* 1285 */     if (this.num_constraints == 0) {
/* 1286 */       this.delButt.setEnabled(false);
/*      */     }
/* 1288 */     this.resultLabel.setText(" ");
/*      */     
/* 1290 */     this.equationPanel.repaint();
/* 1291 */     this.gCanvas.setConstraints(this.lpConstraints);
/* 1292 */     this.gCanvas.clearOptimal();
/* 1293 */     this.gCanvas.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void delConstraint()
/*      */   {
/* 1305 */     int consno = this.consComb.getSelectedIndex() + 1;
/* 1306 */     if (this.lpConstraints.num_of_constraints != 0)
/* 1307 */       this.lpConstraints.removeConstraint(consno);
/* 1308 */     this.constraintV.remove(this.consComb.getSelectedIndex());
/* 1309 */     this.equationStr[(consno - 1)] = "";
/*      */     
/* 1311 */     this.consComb.removeAllItems();
/* 1312 */     for (int i = 0; i < 5; i++) {
/* 1313 */       this.equationLabel[i].setText(" ");
/*      */     }
/*      */     
/* 1316 */     for (i = 0; i < this.num_constraints; i++)
/*      */     {
/* 1318 */       if (this.equationStr[i].equalsIgnoreCase("")) {
/* 1319 */         for (int j = i + 1; j < this.num_constraints; j++) {
/* 1320 */           if (!this.equationStr[j].equalsIgnoreCase("")) {
/* 1321 */             this.equationStr[i] = this.equationStr[j];
/* 1322 */             this.equationStr[j] = "";
/* 1323 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1329 */     for (i = 0; i < this.num_constraints; i++) {
/* 1330 */       if (!this.equationStr[i].equalsIgnoreCase("")) {
/* 1331 */         this.equationLabel[i].setText(String.valueOf(String.valueOf(new StringBuffer("(").append(i + 1).append(") ").append(this.equationStr[i]))));
/* 1332 */         this.consComb.addItem(new String(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(i + 1))).append("  ")))));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1337 */     this.num_constraints -= 1;
/* 1338 */     if (this.num_constraints < 5) {
/* 1339 */       this.addButt.setEnabled(true);
/*      */     }
/* 1341 */     if (this.num_constraints == 0) {
/* 1342 */       this.delButt.setEnabled(false);
/* 1343 */       this.backBut.setEnabled(false);
/*      */     }
/* 1345 */     this.resultLabel.setText(" ");
/*      */     
/* 1347 */     this.equationPanel.repaint();
/* 1348 */     this.gCanvas.setConstraints(this.lpConstraints);
/* 1349 */     this.gCanvas.clearOptimal();
/* 1350 */     this.gCanvas.repaint();
/*      */     
/* 1352 */     this.x1Text.setEnabled(false);
/* 1353 */     this.x1Text.setText("0");
/* 1354 */     this.x2Text.setEnabled(false);
/* 1355 */     this.x2Text.setText("0");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void getResult()
/*      */   {
/* 1364 */     this.isShowLine = true;
/*      */     
/* 1366 */     float obj1cf = 1 / this.objFunX1;
/* 1367 */     float obj2cf = 1 / this.objFunX2;
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
/* 1380 */     if ((Math.abs(obj1cf) < 0.001D) || (Math.abs(obj2cf) < 0.001D)) {
/* 1381 */       JOptionPane.showMessageDialog(this, "Coefficients of objective can't be zero");
/* 1382 */       return;
/*      */     }
/*      */     
/* 1385 */     if (this.input_Z == Float.MAX_VALUE)
/*      */     {
/* 1387 */       JOptionPane.showMessageDialog(this.currentPane, "Please input valid positive number!");
/*      */     }
/*      */     
/* 1390 */     int type = this.maxComb.getSelectedIndex() + 1;
/* 1391 */     this.objectFunction = new Objective(obj1cf, obj2cf, type);
/* 1392 */     this.showresult = true;
/*      */     
/* 1394 */     if (!solveProblem())
/*      */     {
/* 1396 */       this.MaxIntersection = new Intersection(2.0F, 2.0F);
/* 1397 */       this.MaxIntersection.x1 = this.senalysData.solution[0];
/* 1398 */       this.MaxIntersection.x2 = this.senalysData.solution[1];
/*      */       
/*      */ 
/*      */ 
/* 1402 */       float z = this.input_Z / this.objFunX1;
/* 1403 */       this.optimalZ = z;
/*      */       
/*      */ 
/* 1406 */       this.resultLabel.setText(String.valueOf(String.valueOf(new StringBuffer("Z = ").append(this.decfm.format(z)).append("  with  x1 = ").append(this.decfm.format(this.MaxIntersection.x1)).append(" , x2 = ").append(this.decfm.format(this.MaxIntersection.x2)))));
/*      */       
/* 1408 */       this.resultLabel.setHorizontalAlignment(0);
/* 1409 */       this.resultPanel.repaint();
/* 1410 */       this.sensButt.setEnabled(true);
/* 1411 */       this.gCanvas.doAction = true;
/* 1412 */       this.gCanvas.dispOptimal(this.objectFunction, this.MaxIntersection);
/* 1413 */       this.gCanvas.repaint();
/* 1414 */       this.controller.solver.gdata.xcoef[0] = this.objectFunction.x1_coef;
/* 1415 */       this.controller.solver.gdata.xcoef[1] = this.objectFunction.x2_coef;
/*      */     }
/*      */     else {
/* 1418 */       this.controller.solver.gdata.xcoef[0] = 0.0F;
/* 1419 */       this.controller.solver.gdata.xcoef[1] = 0.0F;
/* 1420 */       this.resultLabel.setText("No optimal solution for this problem.");
/* 1421 */       this.resultLabel.setHorizontalAlignment(0);
/* 1422 */       this.resultPanel.repaint();
/* 1423 */       this.gCanvas.dispOptimal(null, null);
/*      */       
/*      */ 
/* 1426 */       this.gCanvas.repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SensAnalysis1()
/*      */   {
/* 1436 */     this.MaxIntersection = new Intersection(1.0F, 2.0F);
/* 1437 */     this.num_constraints = 2;
/* 1438 */     Constraint newcons1 = new Constraint(-1.0F, 1.0F, 1.0F, 2);
/* 1439 */     Constraint newcons2 = new Constraint(1.0F, 1.0F, 3.0F, 1);
/* 1440 */     this.lpConstraints.addConstraint(newcons1);
/* 1441 */     this.lpConstraints.addConstraint(newcons2);
/*      */     
/* 1443 */     this.objectFunction = null;
/*      */     
/* 1445 */     float obj1cf = 2.0F;float obj2cf = 1.0F;
/*      */     
/* 1447 */     this.testobjc1 = obj1cf;
/* 1448 */     this.testobjc2 = obj2cf;
/* 1449 */     if ((Math.abs(obj1cf) < 0.001D) || (Math.abs(obj2cf) < 0.001D)) {
/* 1450 */       JOptionPane.showMessageDialog(this, "Coefficients of objective can't be zero !");
/* 1451 */       return;
/*      */     }
/*      */     
/* 1454 */     int type = 1;
/* 1455 */     this.objectFunction = new Objective(obj1cf, obj2cf, type);
/*      */     
/* 1457 */     if (!solveProblem1()) {
/* 1458 */       buildSensPanel();
/*      */     } else {
/* 1460 */       System.out.println("solve error.");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SensAnalysis()
/*      */   {
/* 1471 */     if (this.lpConstraints == null) {
/* 1472 */       return;
/*      */     }
/* 1474 */     this.objectFunction = null;
/*      */     
/*      */ 
/* 1477 */     float obj1cf = 1 / this.objFunX1;
/* 1478 */     float obj2cf = 1 / this.objFunX2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1488 */     if ((Math.abs(obj1cf) < 0.001D) || (Math.abs(obj2cf) < 0.001D)) {
/* 1489 */       JOptionPane.showMessageDialog(this, "Coefficients of objective can't be zero !");
/* 1490 */       return;
/*      */     }
/*      */     
/* 1493 */     int type = this.maxComb.getSelectedIndex() + 1;
/* 1494 */     this.objectFunction = new Objective(obj1cf, obj2cf, type);
/*      */     
/* 1496 */     if (!solveProblem()) {
/* 1497 */       buildSensPanel();
/* 1498 */       this.showsens = true;
/*      */     } else {
/* 1500 */       this.showsens = false;
/* 1501 */       System.out.println("solve error.");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean solveProblem1()
/*      */   {
/* 1511 */     this.senalysData = new SensData(2);
/* 1512 */     Vector p = new Vector();
/*      */     
/* 1514 */     p.addElement(new Integer(2));
/*      */     
/* 1516 */     p.addElement(new Integer(2));
/*      */     
/* 1518 */     p.addElement(new Boolean(true));
/*      */     
/*      */ 
/* 1521 */     double[] c = new double[2];
/* 1522 */     c[0] = this.testobjc1;
/* 1523 */     c[1] = this.testobjc2;
/* 1524 */     p.addElement(c);
/*      */     
/*      */ 
/* 1527 */     double[][] c2 = new double[2][3];
/*      */     
/* 1529 */     for (int i = 0; i < 2; i++) {
/* 1530 */       Constraint tmpCons = (Constraint)this.lpConstraints.vConstraints.elementAt(i);
/* 1531 */       c2[i][0] = tmpCons.right_const;
/* 1532 */       c2[i][1] = tmpCons.x1_const;
/* 1533 */       c2[i][2] = tmpCons.x2_const;
/*      */     }
/* 1535 */     p.addElement(c2);
/*      */     
/*      */ 
/*      */ 
/* 1539 */     int[] op = new int[2];
/* 1540 */     for (int i = 0; i < 2; i++) {
/* 1541 */       Constraint tmpCons = (Constraint)this.lpConstraints.vConstraints.elementAt(i);
/* 1542 */       if (tmpCons.inequating == 1) {
/* 1543 */         op[i] = 0;
/* 1544 */       } else if (tmpCons.inequating == 3) {
/* 1545 */         op[i] = 1;
/*      */       } else
/* 1547 */         op[i] = 2;
/*      */     }
/* 1549 */     p.addElement(op);
/*      */     
/*      */ 
/*      */ 
/* 1553 */     boolean[] b = new boolean[2];
/* 1554 */     b[0] = true;
/* 1555 */     b[1] = true;
/*      */     
/* 1557 */     double[] rhs = new double[2];
/* 1558 */     rhs[0] = 0.0D;
/* 1559 */     rhs[1] = 0.0D;
/*      */     
/* 1561 */     p.addElement(b);
/* 1562 */     p.addElement(rhs);
/* 1563 */     this.controller.solver.autoSolve(p, this.senalysData);
/* 1564 */     return this.senalysData.unbounded;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean solveProblem()
/*      */   {
/* 1573 */     this.senalysData = new SensData(this.num_constraints);
/* 1574 */     Vector p = new Vector();
/*      */     
/* 1576 */     p.addElement(new Integer(2));
/*      */     
/* 1578 */     p.addElement(new Integer(this.num_constraints));
/*      */     
/* 1580 */     if (this.objectFunction.target == 1) {
/* 1581 */       p.addElement(new Boolean(true));
/*      */     } else {
/* 1583 */       p.addElement(new Boolean(false));
/*      */     }
/*      */     
/* 1586 */     double[] c = new double[2];
/* 1587 */     c[0] = this.objectFunction.x1_coef;
/* 1588 */     c[1] = this.objectFunction.x2_coef;
/* 1589 */     p.addElement(c);
/*      */     
/*      */ 
/* 1592 */     double[][] c2 = new double[this.num_constraints][3];
/*      */     
/* 1594 */     for (int i = 0; i < this.num_constraints; i++) {
/* 1595 */       Constraint tmpCons = (Constraint)this.lpConstraints.vConstraints.elementAt(i);
/* 1596 */       c2[i][0] = tmpCons.right_const;
/* 1597 */       c2[i][1] = tmpCons.x1_const;
/* 1598 */       c2[i][2] = tmpCons.x2_const;
/*      */     }
/* 1600 */     p.addElement(c2);
/*      */     
/*      */ 
/*      */ 
/* 1604 */     int[] op = new int[this.num_constraints];
/* 1605 */     for (int i = 0; i < this.num_constraints; i++) {
/* 1606 */       Constraint tmpCons = (Constraint)this.lpConstraints.vConstraints.elementAt(i);
/* 1607 */       if (tmpCons.inequating == 1) {
/* 1608 */         op[i] = 0;
/* 1609 */       } else if (tmpCons.inequating == 3) {
/* 1610 */         op[i] = 1;
/*      */       } else
/* 1612 */         op[i] = 2;
/*      */     }
/* 1614 */     p.addElement(op);
/*      */     
/*      */ 
/*      */ 
/* 1618 */     boolean[] b = new boolean[2];
/* 1619 */     b[0] = true;
/* 1620 */     b[1] = true;
/*      */     
/* 1622 */     double[] rhs = new double[2];
/* 1623 */     rhs[0] = 0.0D;
/* 1624 */     rhs[1] = 0.0D;
/*      */     
/* 1626 */     p.addElement(b);
/* 1627 */     p.addElement(rhs);
/* 1628 */     this.controller.solver.autoSolve(p, this.senalysData);
/*      */     
/* 1630 */     return this.senalysData.unbounded;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void buildSensPanel()
/*      */   {
/* 1639 */     GridBagLayout gbl = new GridBagLayout();
/* 1640 */     GridBagConstraints c = new GridBagConstraints();
/*      */     
/* 1642 */     this.coefTable = new POMTablePanel1(this, 3, 2, 0);
/* 1643 */     this.rhsTable = new POMTablePanel1(this, 6, this.num_constraints, 1);
/*      */     
/*      */     try
/*      */     {
/* 1647 */       String[] coefHeader = new String[2];
/* 1648 */       coefHeader[0] = "Current\n Value";
/* 1649 */       coefHeader[1] = "Allowable Range\nto Stay Optimal\tMinimum\tMaximum";
/* 1650 */       this.coefTable.setHeader(coefHeader);
/*      */       
/* 1652 */       String[] coefData = new String[6];
/* 1653 */       coefData[0] = this.decfm.format(this.objectFunction.x1_coef);
/* 1654 */       coefData[3] = this.decfm.format(this.objectFunction.x2_coef);
/* 1655 */       if (this.senalysData.range_objcoef[0][0] < -999999.9D) {
/* 1656 */         coefData[1] = "-Inf.";
/*      */       }
/*      */       else
/* 1659 */         coefData[1] = this.decfm.format(this.senalysData.range_objcoef[0][0]);
/* 1660 */       if (this.senalysData.range_objcoef[0][1] > 999999.9D) {
/* 1661 */         coefData[2] = "+Inf.";
/*      */       } else
/* 1663 */         coefData[2] = this.decfm.format(this.senalysData.range_objcoef[0][1]);
/* 1664 */       if (this.senalysData.range_objcoef[1][0] < -999999.9D) {
/* 1665 */         coefData[4] = "-Inf.";
/*      */       }
/*      */       else
/* 1668 */         coefData[4] = this.decfm.format(this.senalysData.range_objcoef[1][0]);
/* 1669 */       if (this.senalysData.range_objcoef[1][1] > 999999.9D) {
/* 1670 */         coefData[5] = "+Inf.";
/*      */       } else
/* 1672 */         coefData[5] = this.decfm.format(this.senalysData.range_objcoef[1][1]);
/* 1673 */       this.coefTable.setData(coefData);
/*      */       
/* 1675 */       this.coefTable.setTopLeft(0, 0);
/* 1676 */       this.coefTable.setForeground(Color.black, Color.blue);
/* 1677 */       this.coefTable.setBackground(new Color(120, 200, 20), Color.green);
/* 1678 */       this.coefTable.InitEnd();
/*      */       
/* 1680 */       String[] consHeader = new String[5];
/* 1681 */       consHeader[0] = "No.";
/* 1682 */       consHeader[1] = "Slack or\nSurplus";
/* 1683 */       consHeader[2] = "Shadow\nPrice";
/* 1684 */       consHeader[3] = "RHS";
/* 1685 */       consHeader[4] = "Allowable Range\nfor Right-Hand Side\tMinimum\tMaximum";
/*      */       
/*      */ 
/*      */ 
/* 1689 */       this.rhsTable.setHeader(consHeader);
/*      */       
/* 1691 */       String[] consData = new String[6 * this.num_constraints];
/* 1692 */       for (int i = 0; i < this.num_constraints; i++) {
/* 1693 */         consData[(i * 6 + 0)] = String.valueOf(String.valueOf(new StringBuffer("").append(i + 1)));
/* 1694 */         consData[(i * 6 + 1)] = this.decfm.format(this.senalysData.slack_surplus[i]);
/* 1695 */         consData[(i * 6 + 2)] = this.decfm.format(this.senalysData.shadow_price[i]);
/* 1696 */         consData[(i * 6 + 3)] = this.decfm.format(this.senalysData.cons_value[i]);
/* 1697 */         if (this.senalysData.range_rightside[i][0] < -999999.9D) {
/* 1698 */           consData[(i * 6 + 4)] = "-Inf.";
/*      */         } else
/* 1700 */           consData[(i * 6 + 4)] = this.decfm.format(this.senalysData.range_rightside[i][0]);
/* 1701 */         if (this.senalysData.range_rightside[i][1] > 999999.9D) {
/* 1702 */           consData[(i * 6 + 5)] = "+Inf.";
/*      */         } else
/* 1704 */           consData[(i * 6 + 5)] = this.decfm.format(this.senalysData.range_rightside[i][1]);
/*      */       }
/* 1706 */       this.rhsTable.setData(consData);
/*      */       
/* 1708 */       this.rhsTable.setTopLeft(0, 0);
/* 1709 */       this.rhsTable.setForeground(Color.black, Color.blue);
/* 1710 */       this.rhsTable.setBackground(new Color(120, 200, 20), Color.green);
/* 1711 */       this.rhsTable.InitEnd();
/*      */     }
/*      */     catch (Exception e) {
/* 1714 */       e.printStackTrace();
/* 1715 */       System.out.println(e);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1720 */     this.graphPanels = new JPanel();
/* 1721 */     this.graphPanels.setLayout(null);
/* 1722 */     this.graphPanels.setBackground(this.bkcolor);
/*      */     
/* 1724 */     float resultZ = this.MaxIntersection.x1 * this.objectFunction.x1_coef + this.MaxIntersection.x2 * this.objectFunction.x2_coef;
/* 1725 */     this.motionLabel = new JLabel(String.valueOf(String.valueOf(new StringBuffer(" Z = ").append(this.decfm.format(this.objectFunction.x1_coef)).append(" x1 + ").append(this.decfm.format(this.objectFunction.x2_coef)).append(" x2").append(" = ").append(this.decfm.format(resultZ)))));
/*      */     
/* 1727 */     this.motionLabel.setForeground(Color.red);
/* 1728 */     this.solidLabel = new JLabel(String.valueOf(String.valueOf(new StringBuffer(" with x1 = ").append(this.decfm.format(this.MaxIntersection.x1)).append(", x2 = ").append(this.decfm.format(this.MaxIntersection.x2)))));
/*      */     
/* 1730 */     this.dymicPanel = new JPanel();
/* 1731 */     this.dymicPanel.setLayout(new GridLayout(2, 1));
/* 1732 */     this.dymicPanel.add(this.motionLabel);
/* 1733 */     this.dymicPanel.add(this.solidLabel);
/*      */     
/* 1735 */     this.sCanvas = new IORLPSensPane(this, this.gCanvas.globalZoom, this.lpConstraints, this.objectFunction, this.MaxIntersection, this.senalysData.range_objcoef);
/*      */     
/* 1737 */     this.sCanvas.setBounds(0, 0, 340, 340);
/*      */     
/* 1739 */     this.sCanvas.setBackground(Color.white);
/* 1740 */     this.graphPanels.add(this.sCanvas);
/* 1741 */     this.dymicPanel.setBounds(110, 340, 300, 60);
/* 1742 */     this.graphPanels.add(this.dymicPanel);
/*      */     
/* 1744 */     this.leftPanel.removeAll();
/* 1745 */     this.leftPanel.setLayout(null);
/* 1746 */     int w = 240;
/*      */     
/*      */ 
/* 1749 */     this.cotabLabel.setBounds(0, 0, w, 20);
/* 1750 */     this.leftPanel.add(this.cotabLabel);
/*      */     
/* 1752 */     this.coefTable.setBounds(0, 20, w + 40, 85);
/* 1753 */     this.leftPanel.add(this.coefTable);
/* 1754 */     this.dragLabel1.setBounds(0, 125, w, 20);
/* 1755 */     this.dragLabel2.setBounds(0, 145, w, 20);
/* 1756 */     this.effectLabel1.setBounds(0, 165, w, 20);
/* 1757 */     this.effectLabel2.setBounds(0, 185, w, 20);
/* 1758 */     this.leftPanel.add(this.dragLabel1);
/* 1759 */     this.leftPanel.add(this.dragLabel2);
/* 1760 */     this.leftPanel.add(this.effectLabel1);
/* 1761 */     this.leftPanel.add(this.effectLabel2);
/* 1762 */     this.backPanel.setBounds(0, 220, 200, 40);
/* 1763 */     this.leftPanel.add(this.backPanel);
/*      */     
/*      */ 
/*      */ 
/* 1767 */     this.sensPanel.setLayout(null);
/* 1768 */     this.contabLabel.setBounds(80, 0, 100, 20);
/* 1769 */     this.rhsTable.setBounds(80, 20, 500, 100);
/* 1770 */     this.sensPanel.removeAll();
/* 1771 */     this.sensPanel.add(this.contabLabel);
/* 1772 */     this.sensPanel.add(this.rhsTable);
/*      */     
/* 1774 */     this.mainPanel.removeAll();
/*      */     
/* 1776 */     this.leftPanel.setBounds(10, 70, 270, 280);
/*      */     
/* 1778 */     this.mainPanel.add(this.leftPanel);
/*      */     
/* 1780 */     this.graphPanels.setBounds(280, 10, 340, 400);
/*      */     
/* 1782 */     this.mainPanel.add(this.graphPanels);
/*      */     
/* 1784 */     this.sensPanel.setBounds(10, 400, 500, 130);
/*      */     
/* 1786 */     this.mainPanel.add(this.sensPanel);
/*      */     
/*      */ 
/*      */ 
/* 1790 */     validate();
/* 1791 */     repaint();
/*      */     
/* 1793 */     this.gCanvas.repaint();
/* 1794 */     this.rhsTable.repaint();
/* 1795 */     this.coefTable.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void changeZ(float x1cf, float x2cf, float zval)
/*      */   {
/* 1804 */     if (this.motionLabel != null)
/* 1805 */       this.motionLabel.setText(String.valueOf(String.valueOf(new StringBuffer(" Z = ").append(this.decfm.format(x1cf)).append(" x1 + ").append(this.decfm.format(x2cf)).append(" x2").append(" = ").append(this.decfm.format(zval)))));
/* 1806 */     this.dymicPanel.validate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void turnBack()
/*      */   {
/* 1815 */     this.showsens = false;
/* 1816 */     this.mainPanel.removeAll();
/* 1817 */     this.mainPanel.add(this.operatePanel);
/* 1818 */     this.mainPanel.add(this.showCon);
/* 1819 */     this.mainPanel.add(this.graphPanel);
/* 1820 */     this.mainPanel.add(this.solvePanel);
/*      */     
/* 1822 */     this.graphPanel.repaint();
/* 1823 */     this.mainPanel.add(this.showSen);
/* 1824 */     this.obj1Coef.requestFocus();
/* 1825 */     this.obj2Coef.requestFocus();
/* 1826 */     this.objectPanel.validate();
/* 1827 */     this.objectPanel.repaint();
/* 1828 */     this.operatePanel.validate();
/* 1829 */     this.operatePanel.repaint();
/*      */     
/* 1831 */     validate();
/* 1832 */     repaint();
/* 1833 */     this.gCanvas.repaint();
/* 1834 */     this.graphPanel.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void focusOn(int num) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updateData(int num, String value, int column, int row) {}
/*      */   
/*      */ 
/*      */ 
/*      */   public void start() {}
/*      */   
/*      */ 
/*      */ 
/*      */   public void stop() {}
/*      */   
/*      */ 
/*      */ 
/*      */   public void destroy() {}
/*      */   
/*      */ 
/*      */ 
/*      */   public String getAppletInfo()
/*      */   {
/* 1863 */     return "Graphical Linear Programming";
/*      */   }
/*      */   
/*      */   public String[][] getParameterInfo()
/*      */   {
/* 1868 */     return null;
/*      */   }
/*      */   
/*      */   private Frame getFrame(Component c) {
/* 1872 */     Frame frame = null;
/* 1873 */     while ((c = c.getParent()) != null) {
/* 1874 */       if ((c instanceof Frame))
/* 1875 */         frame = (Frame)c;
/*      */     }
/* 1877 */     return frame;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPLinearProgPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */