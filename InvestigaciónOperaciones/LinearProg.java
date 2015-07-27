/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Font;
/*      */ import java.awt.Frame;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.GridLayout;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.ParseException;
/*      */ import java.util.Vector;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JTextField;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LinearProg
/*      */   extends IORLPActionPanel
/*      */   implements POMTableDataNotify1
/*      */ {
/*   34 */   private boolean isStandalone = false;
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
/*      */   private graphCanvas gCanvas;
/*      */   private sensCanvas sCanvas;
/*      */   private JPanel objectPanel;
/*      */   private JPanel resultPanel;
/*      */   private JPanel consPanel;
/*      */   private JPanel changePanel;
/*      */   private JPanel buttPanel;
/*      */   private JPanel solvePanel;
/*      */   private JPanel analysPanel;
/*   61 */   private JLabel objectLabel; private JLabel subjectLabel; private JLabel xiLabel; private JLabel noteLabel1; private JLabel noteLabel2; private JLabel noteLabel3; private JLabel resultLabel; private JLabel afterLabel; private JLabel gotoLabel; private JLabel errLabel; private JPanel dymicPanel; private JLabel motionLabel; private JLabel solidLabel; private JLabel dragLabel1; private JLabel dragLabel2; private JLabel effectLabel1; private JLabel effectLabel2; private JLabel copyLabel; private JLabel devLabel; private JTextField obj1Coef; private JTextField obj2Coef; private DecimalField x1Coef; private DecimalField x2Coef; private DecimalField rightCoef; private DecimalFormat decfm = new DecimalFormat();
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
/*      */   
/*      */   private POMTablePanel1 coefTable;
/*      */   private POMTablePanel1 rhsTable;
/*      */   private JLabel cotabLabel;
/*      */   private JLabel contabLabel;
/*      */   private float testobjc1;
/*      */   private float testobjc2;
/*   81 */   private JPanel showCon = new JPanel();
/*   82 */   private JPanel showSen = new JPanel();
/*   83 */   private boolean showresult = false;
/*   84 */   private boolean showsens = false;
/*      */   
/*   86 */   private JPanel leftPanel = new JPanel();
/*      */   
/*      */   private IORLPProcessController controller;
/*      */   
/*      */ 
/*      */   public void updatePanel() {}
/*      */   
/*      */   protected void backStep() {}
/*      */   
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/*   97 */     String str = "Graphical Method and Sensitivity Analysis.";
/*   98 */     str = String.valueOf(String.valueOf(str)).concat("\n\nEnter the objective function.");
/*   99 */     str = String.valueOf(String.valueOf(str)).concat("\nSelect Max or Min from the pull-down list; enter coefficients.");
/*  100 */     str = String.valueOf(String.valueOf(str)).concat("for x1 and x2 in the respective text fields.");
/*      */     
/*  102 */     str = String.valueOf(String.valueOf(str)).concat("\n\nAdd a constraint");
/*  103 */     str = String.valueOf(String.valueOf(str)).concat("\nEnter coefficients for x1, x2, and the RHS in the respective text");
/*  104 */     str = String.valueOf(String.valueOf(str)).concat("fields; select >=, <=, or = from the pull-down list; click “Add” to add the constraint.");
/*      */     
/*  106 */     str = String.valueOf(String.valueOf(str)).concat("\nDelete a constraint");
/*  107 */     str = String.valueOf(String.valueOf(str)).concat("Select a constraint ID from the pull-down list; click “Delete” to remove the corresponding constraint.");
/*      */     
/*  109 */     str = String.valueOf(String.valueOf(str)).concat("\nSolve the problem");
/*  110 */     str = String.valueOf(String.valueOf(str)).concat("Click “Solve” to solve the problem. You can edit both the objective function and the constraints, ");
/*  111 */     str = String.valueOf(String.valueOf(str)).concat("and then click “Solve” to solve the new problem.");
/*      */     
/*      */ 
/*  114 */     str = String.valueOf(String.valueOf(str)).concat("\nReset the problem");
/*  115 */     str = String.valueOf(String.valueOf(str)).concat("Click “Reset” to reset the problem. You can then enter and solve a new problem.");
/*      */     
/*      */ 
/*  118 */     str = String.valueOf(String.valueOf(str)).concat("\nSensitivity analysis");
/*  119 */     str = String.valueOf(String.valueOf(str)).concat("Drag the green triangles at the end of the objective function line to see how far objective function ");
/*  120 */     str = String.valueOf(String.valueOf(str)).concat("coefficients can change without changing the optimal solution.");
/*      */     
/*      */ 
/*  123 */     str = String.valueOf(String.valueOf(str)).concat("\n\nPress the Enter key to continue the current procedure.");
/*      */     
/*  125 */     this.help_content_step.setText(str);
/*  126 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/*  131 */     String str = "Graphical Method and Sensitivity Analysis.";
/*  132 */     str = String.valueOf(String.valueOf(str)).concat("\n\nThis procedure allows you to solve a two-variable (with x1 >= 0 and x2 >= 0) linear programming");
/*  133 */     str = String.valueOf(String.valueOf(str)).concat("problem with no more than 5 constraints. You enter or revise a problem first. The procedure will ");
/*  134 */     str = String.valueOf(String.valueOf(str)).concat("solve the problem for you automatically via a graphic animation. You can then change the ");
/*  135 */     str = String.valueOf(String.valueOf(str)).concat("objective function interactively on the graph to perform the sensitivity analysis.");
/*      */     
/*      */ 
/*  138 */     str = String.valueOf(String.valueOf(str)).concat("\n\nPress the Enter key to continue the current procedure.");
/*      */     
/*  140 */     this.help_content_procedure.setText(str);
/*  141 */     this.help_content_procedure.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */   protected void finishProcedure() {}
/*      */   
/*      */ 
/*      */   public void LoadFile(ObjectInputStream in)
/*      */   {
/*  150 */     Vector p = new Vector();
/*      */     try
/*      */     {
/*  153 */       p = (Vector)in.readObject();
/*  154 */       in.close();
/*  155 */       this.obj1Coef.setText((String)p.get(0));
/*  156 */       this.obj2Coef.setText((String)p.get(1));
/*  157 */       this.maxComb.setSelectedIndex(((Integer)p.get(2)).intValue());
/*      */       
/*  159 */       this.inequComb.setSelectedIndex(((Integer)p.get(3)).intValue());
/*  160 */       this.x1Coef.setValue(((Float)p.get(4)).floatValue());
/*  161 */       this.x2Coef.setValue(((Float)p.get(5)).floatValue());
/*  162 */       this.rightCoef.setValue(((Float)p.get(6)).floatValue());
/*      */       
/*  164 */       this.num_constraints = ((Integer)p.get(7)).intValue();
/*  165 */       this.lpConstraints = ((allConstraints)p.get(8));
/*  166 */       this.consComb.removeAllItems();
/*  167 */       for (int i = 0; i < 5; i++) {
/*  168 */         this.equationLabel[i].setText(" ");
/*      */       }
/*  170 */       if (this.lpConstraints != null) {
/*  171 */         for (i = 0; i < this.lpConstraints.num_of_constraints; i++)
/*      */         {
/*  173 */           Constraint thecons = (Constraint)this.lpConstraints.vConstraints.elementAt(i);
/*  174 */           this.equationLabel[i].setText(String.valueOf(String.valueOf(new StringBuffer("(").append(i + 1).append(") ").append(thecons.toString()))));
/*      */           
/*  176 */           this.consComb.addItem(new String(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(i + 1))).append("  ")))));
/*      */         }
/*      */       }
/*  179 */       this.equationPanel.repaint();
/*  180 */       this.gCanvas.setConstraints(this.lpConstraints);
/*  181 */       this.gCanvas.clearOptimal();
/*  182 */       this.gCanvas.repaint();
/*      */       
/*  184 */       this.addButt.setEnabled(((Boolean)p.get(9)).booleanValue());
/*  185 */       this.delButt.setEnabled(((Boolean)p.get(10)).booleanValue());
/*  186 */       this.sensButt.setEnabled(((Boolean)p.get(11)).booleanValue());
/*      */     }
/*      */     catch (Exception e) {
/*  189 */       e.printStackTrace();
/*  190 */       System.out.println("aOpen fails");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setprintData()
/*      */   {
/*  198 */     String tobj1cf = this.obj1Coef.getText();
/*  199 */     String tobj2cf = this.obj2Coef.getText();
/*  200 */     int type = this.maxComb.getSelectedIndex();
/*  201 */     String stype = "";
/*      */     
/*  203 */     if (type == 0) stype = "Max"; else
/*  204 */       stype = "Min";
/*  205 */     this.controller.solver.gdata.objfunc = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(stype))).append(" Z = ").append(tobj1cf).append("x1 + ").append(tobj2cf).append("x2")));
/*      */     
/*      */ 
/*  208 */     this.controller.solver.gdata.lp = this.lpConstraints;
/*      */     
/*      */ 
/*  211 */     this.controller.solver.gdata.showresult = this.showresult;
/*  212 */     this.controller.solver.gdata.result = this.resultLabel.getText();
/*      */     
/*      */ 
/*  215 */     this.controller.solver.gdata.showsens = this.showsens;
/*  216 */     this.controller.solver.gdata.senalysData = this.senalysData;
/*      */   }
/*      */   
/*      */   public void SaveFile(ObjectOutputStream out) {
/*  220 */     String tobj1cf = this.obj1Coef.getText();
/*  221 */     String tobj2cf = this.obj2Coef.getText();
/*  222 */     int type = this.maxComb.getSelectedIndex();
/*      */     
/*  224 */     int selsym = this.inequComb.getSelectedIndex();
/*  225 */     float x1_coef = (float)this.x1Coef.getValue();
/*  226 */     float x2_coef = (float)this.x2Coef.getValue();
/*  227 */     float right_coef = (float)this.rightCoef.getValue();
/*      */     
/*  229 */     Vector p = new Vector();
/*  230 */     p.addElement(tobj1cf);
/*  231 */     p.addElement(tobj2cf);
/*  232 */     p.addElement(new Integer(type));
/*      */     
/*  234 */     p.addElement(new Integer(selsym));
/*  235 */     p.addElement(new Float(x1_coef));
/*  236 */     p.addElement(new Float(x2_coef));
/*  237 */     p.addElement(new Float(right_coef));
/*      */     
/*  239 */     p.addElement(new Integer(this.num_constraints));
/*  240 */     p.addElement(this.lpConstraints);
/*      */     
/*  242 */     p.addElement(new Boolean(this.addButt.isEnabled()));
/*  243 */     p.addElement(new Boolean(this.delButt.isEnabled()));
/*  244 */     p.addElement(new Boolean(this.sensButt.isEnabled()));
/*      */     try
/*      */     {
/*  247 */       out.writeObject(p);
/*  248 */       out.close();
/*      */     } catch (Exception e) {
/*  250 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LinearProg(IORLPProcessController c)
/*      */   {
/*  261 */     super(c);
/*  262 */     this.controller = c;
/*  263 */     this.lpConstraints = new allConstraints();
/*  264 */     this.decfm.setGroupingUsed(false);
/*  265 */     this.decfm.setMaximumFractionDigits(2);
/*  266 */     init();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void init()
/*      */   {
/*      */     try
/*      */     {
/*  275 */       jbInit();
/*      */     }
/*      */     catch (Exception e) {
/*  278 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void jbInit()
/*      */     throws Exception
/*      */   {
/*  286 */     setLayout(new BorderLayout());
/*  287 */     setBackground(this.bkcolor);
/*  288 */     this.sensPanel = new JPanel();
/*  289 */     this.sensPanel.setBackground(this.bkcolor);
/*      */     
/*  291 */     this.backButt = new JButton("Back");
/*  292 */     this.backButt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  294 */         LinearProg.this.turnBack();
/*      */       }
/*      */       
/*  297 */     });
/*  298 */     this.dragLabel1 = new JLabel("Drag the green triangles with the");
/*  299 */     this.dragLabel2 = new JLabel("mouse to see how far objective");
/*      */     
/*  301 */     this.effectLabel1 = new JLabel("function coefficients can change");
/*  302 */     this.effectLabel2 = new JLabel("without changing the optimal solution.");
/*      */     
/*  304 */     this.cotabLabel = new JLabel("Objective Coefficient");
/*  305 */     this.contabLabel = new JLabel("Coefficient:");
/*      */     
/*  307 */     this.backPanel = new JPanel();
/*  308 */     this.backPanel.add(this.backButt);
/*      */     
/*  310 */     this.operatePanel = new JPanel();
/*  311 */     this.operatePanel.setBackground(this.bkcolor);
/*  312 */     this.operatePanel.setLayout(this.gblay);
/*      */     
/*  314 */     this.objectLabel = new JLabel("Objective Function:");
/*  315 */     this.objectPanel = new JPanel();
/*  316 */     this.maxComb = new JComboBox();
/*  317 */     this.maxComb.addItem("Max");
/*  318 */     this.maxComb.addItem("Min");
/*  319 */     this.objectPanel.add(this.maxComb);
/*  320 */     this.objectPanel.add(new JLabel("Z = "));
/*      */     
/*  322 */     this.subjectLabel = new JLabel("Subject to:");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  327 */     this.obj1Coef = new JTextField("0", 4);
/*  328 */     this.obj2Coef = new JTextField("0", 4);
/*  329 */     this.x1Coef = new DecimalField(0.0D, this.decfm);
/*  330 */     this.x2Coef = new DecimalField(0.0D, this.decfm);
/*  331 */     this.rightCoef = new DecimalField(0.0D, this.decfm);
/*  332 */     this.objectPanel.add(this.obj1Coef);
/*  333 */     this.objectPanel.add(new JLabel("x1 +"));
/*  334 */     this.objectPanel.add(this.obj2Coef);
/*  335 */     this.objectPanel.add(new JLabel("x2"));
/*      */     
/*  337 */     this.xiLabel = new JLabel("x1 >= 0 , x2 >= 0");
/*      */     
/*  339 */     this.noteLabel1 = new JLabel("Note:");
/*  340 */     this.noteLabel2 = new JLabel("The number of constraints");
/*  341 */     this.noteLabel3 = new JLabel("can't be more than 5");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  347 */     this.addButt = new JButton("Add");
/*  348 */     this.delButt = new JButton("Delete");
/*  349 */     this.consComb = new JComboBox();
/*      */     
/*  351 */     this.changePanel = new JPanel();
/*  352 */     this.changePanel.add(new JLabel("Constraint"));
/*      */     
/*  354 */     this.buttPanel = new JPanel();
/*  355 */     this.buttPanel.setLayout(null);
/*  356 */     this.addButt.setBounds(0, 5, 60, 25);
/*  357 */     this.buttPanel.add(this.addButt);
/*  358 */     this.delButt.setBounds(70, 5, 80, 25);
/*  359 */     this.buttPanel.add(this.delButt);
/*  360 */     this.consComb.setBounds(160, 5, 50, 25);
/*  361 */     this.buttPanel.add(this.consComb);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  368 */     this.addButt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  370 */         LinearProg.this.addConstraint();
/*      */       }
/*  372 */     });
/*  373 */     this.delButt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  375 */         LinearProg.this.delConstraint();
/*      */       }
/*      */     });
/*      */     
/*  379 */     if (this.num_constraints == 0) {
/*  380 */       this.delButt.setEnabled(false);
/*      */     }
/*  382 */     this.optmButt = new JButton("Solve");
/*  383 */     this.optmButt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  385 */         LinearProg.this.getResult();
/*      */       }
/*      */       
/*  388 */     });
/*  389 */     this.resetButt = new JButton("Reset");
/*  390 */     this.resetButt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  392 */         LinearProg.this.resetProb();
/*      */       }
/*      */       
/*  395 */     });
/*  396 */     this.solvePanel = new JPanel();
/*  397 */     this.solvePanel.add(this.optmButt);
/*  398 */     this.solvePanel.add(this.resetButt);
/*      */     
/*  400 */     this.afterLabel = new JLabel("After the optimal solution is reached, you can do ");
/*  401 */     this.gotoLabel = new JLabel("sensitivity analysis by pressing the button below.");
/*      */     
/*  403 */     this.sensButt = new JButton("Sensitivity Analysis");
/*  404 */     this.sensButt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  406 */         LinearProg.this.SensAnalysis();
/*      */       }
/*  408 */     });
/*  409 */     this.sensButt.setEnabled(false);
/*      */     
/*      */ 
/*  412 */     this.analysPanel = new JPanel();
/*  413 */     this.analysPanel.add(this.sensButt);
/*      */     
/*  415 */     this.resultLabel = new JLabel(" ");
/*      */     
/*      */ 
/*  418 */     this.resultPanel = new JPanel();
/*  419 */     this.resultPanel.setLayout(new GridLayout(1, 1));
/*  420 */     this.resultPanel.add(this.resultLabel);
/*      */     
/*      */ 
/*  423 */     this.inequComb = new JComboBox();
/*  424 */     this.inequComb.addItem("<=");
/*  425 */     this.inequComb.addItem(">=");
/*  426 */     this.inequComb.addItem("=");
/*      */     
/*  428 */     this.consPanel = new JPanel();
/*  429 */     this.consPanel.add(this.x1Coef);
/*  430 */     this.consPanel.add(new JLabel("x1 +"));
/*  431 */     this.consPanel.add(this.x2Coef);
/*  432 */     this.consPanel.add(new JLabel("x2"));
/*  433 */     this.consPanel.add(this.inequComb);
/*  434 */     this.consPanel.add(this.rightCoef);
/*      */     
/*  436 */     this.equationPanel = new JPanel();
/*  437 */     this.equationPanel.setLayout(this.gridlay);
/*  438 */     for (int i = 0; i < 5; i++)
/*      */     {
/*  440 */       this.equationLabel[i] = new JLabel("  ");
/*  441 */       this.equationPanel.add(this.equationLabel[i]);
/*      */     }
/*      */     
/*      */ 
/*  445 */     buildOperPanel();
/*      */     
/*  447 */     this.graphPanel = new JPanel();
/*  448 */     this.graphPanel.setLayout(null);
/*  449 */     this.graphPanel.setBackground(this.bkcolor);
/*      */     
/*  451 */     this.gCanvas = new graphCanvas();
/*  452 */     this.gCanvas.setBounds(0, 0, 340, 340);
/*  453 */     this.gCanvas.setBackground(Color.white);
/*  454 */     this.graphPanel.add(this.gCanvas);
/*  455 */     this.resultPanel.setBounds(20, 340, 300, 30);
/*  456 */     this.graphPanel.add(this.resultPanel);
/*      */     
/*      */ 
/*      */ 
/*  460 */     this.mainPanel = new JPanel();
/*  461 */     this.mainPanel.setLayout(null);
/*  462 */     this.operatePanel.setBounds(10, 10, 600, 80);
/*      */     
/*  464 */     this.mainPanel.add(this.operatePanel);
/*  465 */     this.showCon.setBounds(30, 100, 160, 340);
/*  466 */     this.showCon.setBackground(this.bkcolor);
/*  467 */     this.mainPanel.add(this.showCon);
/*      */     
/*  469 */     this.graphPanel.setBounds(220, 95, 340, 380);
/*  470 */     this.mainPanel.add(this.graphPanel);
/*      */     
/*  472 */     this.showSen.setBounds(110, 480, 400, 80);
/*  473 */     this.mainPanel.add(this.showSen);
/*      */     
/*  475 */     this.copyLabel = new JLabel("© 2002 McGraw-Hill, Inc. All rights reserved.");
/*  476 */     this.devLabel = new JLabel("Developed by Accelet Corp.");
/*  477 */     Font curFont = getFont();
/*  478 */     Font bdFont = new Font(curFont.getName(), 1, curFont.getSize());
/*  479 */     this.copyLabel.setFont(bdFont);
/*  480 */     this.copyLabel.setForeground(Color.red);
/*  481 */     this.copyPanel = new JPanel();
/*  482 */     this.copyPanel.add(this.copyLabel);
/*  483 */     this.copyPanel.add(this.devLabel);
/*      */     
/*  485 */     add(this.mainPanel, "Center");
/*      */     
/*  487 */     setVisible(true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void buildOperPanel()
/*      */   {
/*  497 */     this.operatePanel.setLayout(null);
/*  498 */     this.objectLabel.setBounds(10, 5, 150, 20);
/*      */     
/*  500 */     this.operatePanel.add(this.objectLabel);
/*      */     
/*  502 */     this.objectPanel.setBounds(160, 0, 300, 40);
/*  503 */     this.operatePanel.add(this.objectPanel);
/*      */     
/*      */ 
/*  506 */     this.changePanel.setBounds(5, 35, 100, 30);
/*  507 */     this.operatePanel.add(this.changePanel);
/*      */     
/*  509 */     this.buttPanel.setBounds(110, 35, 220, 40);
/*  510 */     this.operatePanel.add(this.buttPanel);
/*      */     
/*  512 */     this.consPanel.setBounds(310, 35, 300, 40);
/*  513 */     this.operatePanel.add(this.consPanel);
/*      */     
/*      */ 
/*  516 */     this.showCon.setLayout(null);
/*  517 */     int w = 160;
/*  518 */     this.showCon.setSize(w, 340);
/*      */     
/*      */ 
/*  521 */     this.noteLabel1.setBounds(0, 0, w, 20);
/*  522 */     this.showCon.add(this.noteLabel1);
/*  523 */     this.noteLabel2.setBounds(0, 20, w, 20);
/*  524 */     this.showCon.add(this.noteLabel2);
/*  525 */     this.noteLabel3.setBounds(0, 40, w, 20);
/*  526 */     this.showCon.add(this.noteLabel3);
/*      */     
/*  528 */     this.subjectLabel.setBounds(0, 60, w, 20);
/*  529 */     this.showCon.add(this.subjectLabel);
/*      */     
/*  531 */     this.xiLabel.setBounds(0, 80, w, 20);
/*  532 */     this.showCon.add(this.xiLabel);
/*      */     
/*  534 */     this.equationPanel.setBounds(0, 100, w, 120);
/*  535 */     this.showCon.add(this.equationPanel);
/*      */     
/*  537 */     this.solvePanel.setBounds(0, 250, w, 40);
/*      */     
/*  539 */     this.showCon.add(this.solvePanel);
/*      */     
/*  541 */     int sw = 400;
/*  542 */     this.showSen.setSize(sw, 100);
/*  543 */     this.showSen.setLayout(null);
/*  544 */     this.afterLabel.setBounds(70, 0, sw, 20);
/*      */     
/*  546 */     this.showSen.add(this.afterLabel);
/*      */     
/*  548 */     this.gotoLabel.setBounds(70, 20, sw, 20);
/*  549 */     this.showSen.add(this.gotoLabel);
/*  550 */     this.analysPanel.setBounds(120, 40, 150, 40);
/*  551 */     this.showSen.add(this.analysPanel);
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
/*  563 */     this.showresult = false;
/*  564 */     this.showsens = false;
/*      */     
/*  566 */     this.obj1Coef.setText("0");
/*  567 */     this.obj2Coef.setText("0");
/*  568 */     this.x1Coef.setValue(0.0D);
/*  569 */     this.x2Coef.setValue(0.0D);
/*  570 */     this.rightCoef.setValue(0.0D);
/*      */     
/*  572 */     this.lpConstraints = null;
/*  573 */     this.lpConstraints = new allConstraints();
/*  574 */     this.num_constraints = 0;
/*  575 */     this.objectFunction = null;
/*  576 */     this.MaxIntersection = null;
/*      */     
/*  578 */     this.consComb.removeAllItems();
/*  579 */     this.addButt.setEnabled(true);
/*  580 */     this.delButt.setEnabled(false);
/*  581 */     this.sensButt.setEnabled(false);
/*  582 */     for (int i = 0; i < 5; i++)
/*      */     {
/*  584 */       this.equationLabel[i].setText("  ");
/*      */     }
/*  586 */     this.resultLabel.setText(" ");
/*  587 */     this.operatePanel.validate();
/*  588 */     this.operatePanel.repaint();
/*      */     
/*  590 */     this.gCanvas.setConstraints(null);
/*  591 */     this.gCanvas.clearOptimal();
/*  592 */     this.gCanvas.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void addConstraint()
/*      */   {
/*  600 */     int selsym = this.inequComb.getSelectedIndex() + 1;
/*  601 */     float x1_coef = (float)this.x1Coef.getValue();
/*  602 */     float x2_coef = (float)this.x2Coef.getValue();
/*  603 */     float right_coef = (float)this.rightCoef.getValue();
/*  604 */     if ((Math.abs(x1_coef) < 0.001D) && (Math.abs(x2_coef) < 0.001D)) {
/*  605 */       JOptionPane.showMessageDialog(this, "Coefficients of constraints can't be both negative!");
/*      */       
/*  607 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  612 */     if ((Math.abs(right_coef) < 0.001D) && ((Math.abs(x1_coef) < 0.001D) || (Math.abs(x2_coef) < 0.001D))) {
/*  613 */       JOptionPane.showMessageDialog(this, "R.H. side of constraints can't be zero or negative!");
/*  614 */       return;
/*      */     }
/*      */     
/*  617 */     this.x1Coef.setValue(0.0D);
/*  618 */     this.x2Coef.setValue(0.0D);
/*  619 */     this.rightCoef.setValue(0.0D);
/*  620 */     Constraint newcons = new Constraint(x1_coef, x2_coef, right_coef, selsym);
/*  621 */     this.lpConstraints.addConstraint(newcons);
/*      */     
/*  623 */     this.num_constraints += 1;
/*  624 */     this.consComb.addItem(new String(String.valueOf(String.valueOf(this.num_constraints)).concat("  ")));
/*      */     
/*  626 */     this.equationLabel[(this.num_constraints - 1)].setText(String.valueOf(String.valueOf(new StringBuffer("(").append(this.num_constraints).append(")").append(newcons.toString()))));
/*      */     
/*      */ 
/*  629 */     if (this.num_constraints >= 5) {
/*  630 */       this.addButt.setEnabled(false);
/*  631 */     } else if (this.num_constraints > 0) {
/*  632 */       this.delButt.setEnabled(true);
/*      */     }
/*  634 */     this.resultLabel.setText(" ");
/*  635 */     this.equationPanel.validate();
/*  636 */     this.equationPanel.repaint();
/*  637 */     this.gCanvas.setConstraints(this.lpConstraints);
/*  638 */     this.gCanvas.clearOptimal();
/*  639 */     this.gCanvas.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void delConstraint()
/*      */   {
/*  650 */     int consno = this.consComb.getSelectedIndex() + 1;
/*  651 */     this.lpConstraints.removeConstraint(consno);
/*  652 */     this.num_constraints -= 1;
/*      */     
/*  654 */     this.consComb.removeAllItems();
/*  655 */     for (int i = 0; i < 5; i++) {
/*  656 */       this.equationLabel[i].setText(" ");
/*      */     }
/*      */     
/*  659 */     for (i = 0; i < this.lpConstraints.num_of_constraints; i++)
/*      */     {
/*  661 */       Constraint thecons = (Constraint)this.lpConstraints.vConstraints.elementAt(i);
/*  662 */       this.equationLabel[i].setText(String.valueOf(String.valueOf(new StringBuffer("(").append(i + 1).append(") ").append(thecons.toString()))));
/*      */       
/*  664 */       this.consComb.addItem(new String(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(i + 1))).append("  ")))));
/*      */     }
/*      */     
/*  667 */     if (this.num_constraints < 5)
/*  668 */       this.addButt.setEnabled(true);
/*  669 */     if (this.num_constraints == 0) {
/*  670 */       this.delButt.setEnabled(false);
/*      */     }
/*  672 */     this.resultLabel.setText(" ");
/*      */     
/*  674 */     this.equationPanel.repaint();
/*  675 */     this.gCanvas.setConstraints(this.lpConstraints);
/*  676 */     this.gCanvas.clearOptimal();
/*  677 */     this.gCanvas.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void getResult()
/*      */   {
/*      */     try
/*      */     {
/*  690 */       float obj1cf = this.decfm.parse(this.obj1Coef.getText()).floatValue();
/*  691 */       obj2cf = this.decfm.parse(this.obj2Coef.getText()).floatValue();
/*      */     } catch (ParseException e) {
/*      */       float obj2cf;
/*  694 */       JOptionPane.showMessageDialog(this, "Please input valid positive number."); return; }
/*      */     float obj2cf;
/*      */     float obj1cf;
/*  697 */     if ((Math.abs(obj1cf) < 0.001D) || (Math.abs(obj2cf) < 0.001D)) {
/*  698 */       JOptionPane.showMessageDialog(this, "Coefficients of objective can't be zero");
/*      */       
/*  700 */       return;
/*      */     }
/*      */     
/*  703 */     int type = this.maxComb.getSelectedIndex() + 1;
/*  704 */     this.objectFunction = new Objective(obj1cf, obj2cf, type);
/*  705 */     this.showresult = true;
/*      */     
/*  707 */     if (!solveProblem())
/*      */     {
/*  709 */       this.MaxIntersection = new Intersection(1.0F, 1.0F);
/*  710 */       this.MaxIntersection.x1 = this.senalysData.solution[0];
/*  711 */       this.MaxIntersection.x2 = this.senalysData.solution[1];
/*  712 */       this.optimalZ = this.senalysData.z;
/*      */       
/*      */ 
/*  715 */       this.resultLabel.setText(String.valueOf(String.valueOf(new StringBuffer("Z = ").append(this.decfm.format(this.optimalZ)).append("  with  x1 = ").append(this.decfm.format(this.MaxIntersection.x1)).append(" , x2 = ").append(this.decfm.format(this.MaxIntersection.x2)))));
/*  716 */       this.resultLabel.setHorizontalAlignment(0);
/*  717 */       this.resultPanel.repaint();
/*  718 */       this.sensButt.setEnabled(true);
/*  719 */       this.gCanvas.doAction = true;
/*  720 */       this.gCanvas.dispOptimal(this.objectFunction, this.MaxIntersection);
/*  721 */       this.gCanvas.repaint();
/*  722 */       this.controller.solver.gdata.xcoef[0] = this.objectFunction.x1_coef;
/*  723 */       this.controller.solver.gdata.xcoef[1] = this.objectFunction.x2_coef;
/*      */     }
/*      */     else {
/*  726 */       this.controller.solver.gdata.xcoef[0] = 0.0F;
/*  727 */       this.controller.solver.gdata.xcoef[1] = 0.0F;
/*  728 */       this.resultLabel.setText("No optimal solution for this problem.");
/*  729 */       this.resultLabel.setHorizontalAlignment(0);
/*  730 */       this.resultPanel.repaint();
/*  731 */       this.gCanvas.dispOptimal(null, null);
/*  732 */       this.gCanvas.repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SensAnalysis1()
/*      */   {
/*  742 */     this.MaxIntersection = new Intersection(1.0F, 2.0F);
/*  743 */     this.num_constraints = 2;
/*  744 */     Constraint newcons1 = new Constraint(-1.0F, 1.0F, 1.0F, 2);
/*  745 */     Constraint newcons2 = new Constraint(1.0F, 1.0F, 3.0F, 1);
/*  746 */     this.lpConstraints.addConstraint(newcons1);
/*  747 */     this.lpConstraints.addConstraint(newcons2);
/*      */     
/*  749 */     this.objectFunction = null;
/*      */     
/*  751 */     float obj1cf = 2.0F;float obj2cf = 1.0F;
/*      */     
/*  753 */     this.testobjc1 = obj1cf;
/*  754 */     this.testobjc2 = obj2cf;
/*  755 */     if ((Math.abs(obj1cf) < 0.001D) || (Math.abs(obj2cf) < 0.001D)) {
/*  756 */       JOptionPane.showMessageDialog(this, "Coefficients of objective can't be zero !");
/*      */       
/*  758 */       return;
/*      */     }
/*      */     
/*  761 */     int type = 1;
/*  762 */     this.objectFunction = new Objective(obj1cf, obj2cf, type);
/*      */     
/*  764 */     if (!solveProblem1()) {
/*  765 */       buildSensPanel();
/*      */     } else {
/*  767 */       System.out.println("solve error.");
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
/*  778 */     if (this.lpConstraints == null) {
/*  779 */       return;
/*      */     }
/*  781 */     this.objectFunction = null;
/*      */     
/*      */     try
/*      */     {
/*  785 */       float obj1cf = this.decfm.parse(this.obj1Coef.getText()).floatValue();
/*  786 */       obj2cf = this.decfm.parse(this.obj2Coef.getText()).floatValue();
/*      */     } catch (ParseException e) {
/*      */       float obj2cf;
/*  789 */       JOptionPane.showMessageDialog(this, "Please input valid positive number."); return;
/*      */     }
/*      */     float obj2cf;
/*      */     float obj1cf;
/*  793 */     if ((Math.abs(obj1cf) < 0.001D) || (Math.abs(obj2cf) < 0.001D)) {
/*  794 */       JOptionPane.showMessageDialog(this, "Coefficients of objective can't be zero !");
/*      */       
/*  796 */       return;
/*      */     }
/*      */     
/*  799 */     int type = this.maxComb.getSelectedIndex() + 1;
/*  800 */     this.objectFunction = new Objective(obj1cf, obj2cf, type);
/*      */     
/*  802 */     if (!solveProblem()) {
/*  803 */       buildSensPanel();
/*  804 */       this.showsens = true;
/*      */     } else {
/*  806 */       this.showsens = false;
/*  807 */       System.out.println("solve error.");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean solveProblem1()
/*      */   {
/*  817 */     this.senalysData = new SensData(2);
/*  818 */     Vector p = new Vector();
/*      */     
/*  820 */     p.addElement(new Integer(2));
/*      */     
/*  822 */     p.addElement(new Integer(2));
/*      */     
/*  824 */     p.addElement(new Boolean(true));
/*      */     
/*      */ 
/*  827 */     double[] c = new double[2];
/*  828 */     c[0] = this.testobjc1;
/*  829 */     c[1] = this.testobjc2;
/*  830 */     p.addElement(c);
/*      */     
/*      */ 
/*  833 */     double[][] c2 = new double[2][3];
/*      */     
/*  835 */     for (int i = 0; i < 2; i++) {
/*  836 */       Constraint tmpCons = (Constraint)this.lpConstraints.vConstraints.elementAt(i);
/*  837 */       c2[i][0] = tmpCons.right_const;
/*  838 */       c2[i][1] = tmpCons.x1_const;
/*  839 */       c2[i][2] = tmpCons.x2_const;
/*      */     }
/*  841 */     p.addElement(c2);
/*      */     
/*      */ 
/*      */ 
/*  845 */     int[] op = new int[2];
/*  846 */     for (int i = 0; i < 2; i++) {
/*  847 */       Constraint tmpCons = (Constraint)this.lpConstraints.vConstraints.elementAt(i);
/*  848 */       if (tmpCons.inequating == 1) {
/*  849 */         op[i] = 0;
/*  850 */       } else if (tmpCons.inequating == 3) {
/*  851 */         op[i] = 1;
/*      */       } else
/*  853 */         op[i] = 2;
/*      */     }
/*  855 */     p.addElement(op);
/*      */     
/*      */ 
/*      */ 
/*  859 */     boolean[] b = new boolean[2];
/*  860 */     b[0] = true;
/*  861 */     b[1] = true;
/*      */     
/*  863 */     double[] rhs = new double[2];
/*  864 */     rhs[0] = 0.0D;
/*  865 */     rhs[1] = 0.0D;
/*      */     
/*  867 */     p.addElement(b);
/*  868 */     p.addElement(rhs);
/*  869 */     this.controller.solver.autoSolve(p, this.senalysData);
/*  870 */     return this.senalysData.unbounded;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean solveProblem()
/*      */   {
/*  879 */     this.senalysData = new SensData(this.num_constraints);
/*      */     
/*  881 */     Vector p = new Vector();
/*      */     
/*  883 */     p.addElement(new Integer(2));
/*      */     
/*  885 */     p.addElement(new Integer(this.num_constraints));
/*      */     
/*  887 */     if (this.objectFunction.target == 1) {
/*  888 */       p.addElement(new Boolean(true));
/*      */     } else {
/*  890 */       p.addElement(new Boolean(false));
/*      */     }
/*      */     
/*  893 */     double[] c = new double[2];
/*  894 */     c[0] = this.objectFunction.x1_coef;
/*  895 */     c[1] = this.objectFunction.x2_coef;
/*  896 */     p.addElement(c);
/*      */     
/*      */ 
/*  899 */     double[][] c2 = new double[this.num_constraints][3];
/*      */     
/*  901 */     for (int i = 0; i < this.num_constraints; i++) {
/*  902 */       Constraint tmpCons = (Constraint)this.lpConstraints.vConstraints.elementAt(i);
/*  903 */       c2[i][0] = tmpCons.right_const;
/*  904 */       c2[i][1] = tmpCons.x1_const;
/*  905 */       c2[i][2] = tmpCons.x2_const;
/*      */     }
/*  907 */     p.addElement(c2);
/*      */     
/*      */ 
/*      */ 
/*  911 */     int[] op = new int[this.num_constraints];
/*  912 */     for (int i = 0; i < this.num_constraints; i++) {
/*  913 */       Constraint tmpCons = (Constraint)this.lpConstraints.vConstraints.elementAt(i);
/*  914 */       if (tmpCons.inequating == 1) {
/*  915 */         op[i] = 0;
/*  916 */       } else if (tmpCons.inequating == 3) {
/*  917 */         op[i] = 1;
/*      */       } else
/*  919 */         op[i] = 2;
/*      */     }
/*  921 */     p.addElement(op);
/*      */     
/*      */ 
/*      */ 
/*  925 */     boolean[] b = new boolean[2];
/*  926 */     b[0] = true;
/*  927 */     b[1] = true;
/*      */     
/*  929 */     double[] rhs = new double[2];
/*  930 */     rhs[0] = 0.0D;
/*  931 */     rhs[1] = 0.0D;
/*      */     
/*  933 */     p.addElement(b);
/*  934 */     p.addElement(rhs);
/*  935 */     this.controller.solver.autoSolve(p, this.senalysData);
/*      */     
/*  937 */     return this.senalysData.unbounded;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void buildSensPanel()
/*      */   {
/*  946 */     GridBagLayout gbl = new GridBagLayout();
/*  947 */     GridBagConstraints c = new GridBagConstraints();
/*      */     
/*  949 */     this.coefTable = new POMTablePanel1(this, 3, 2, 0);
/*  950 */     this.rhsTable = new POMTablePanel1(this, 6, this.num_constraints, 1);
/*      */     
/*      */     try
/*      */     {
/*  954 */       String[] coefHeader = new String[2];
/*  955 */       coefHeader[0] = "Current\n Value";
/*  956 */       coefHeader[1] = "Allowable Range\nto Stay Optimal\tMinimum\tMaximum";
/*  957 */       this.coefTable.setHeader(coefHeader);
/*      */       
/*  959 */       String[] coefData = new String[6];
/*  960 */       coefData[0] = this.decfm.format(this.objectFunction.x1_coef);
/*  961 */       coefData[3] = this.decfm.format(this.objectFunction.x2_coef);
/*  962 */       if (this.senalysData.range_objcoef[0][0] < -999999.9D) {
/*  963 */         coefData[1] = "-Inf.";
/*      */       }
/*      */       else
/*  966 */         coefData[1] = this.decfm.format(this.senalysData.range_objcoef[0][0]);
/*  967 */       if (this.senalysData.range_objcoef[0][1] > 999999.9D) {
/*  968 */         coefData[2] = "+Inf.";
/*      */       } else
/*  970 */         coefData[2] = this.decfm.format(this.senalysData.range_objcoef[0][1]);
/*  971 */       if (this.senalysData.range_objcoef[1][0] < -999999.9D) {
/*  972 */         coefData[4] = "-Inf.";
/*      */       }
/*      */       else
/*  975 */         coefData[4] = this.decfm.format(this.senalysData.range_objcoef[1][0]);
/*  976 */       if (this.senalysData.range_objcoef[1][1] > 999999.9D) {
/*  977 */         coefData[5] = "+Inf.";
/*      */       } else
/*  979 */         coefData[5] = this.decfm.format(this.senalysData.range_objcoef[1][1]);
/*  980 */       this.coefTable.setData(coefData);
/*      */       
/*  982 */       this.coefTable.setTopLeft(0, 0);
/*  983 */       this.coefTable.setForeground(Color.black, Color.blue);
/*  984 */       this.coefTable.setBackground(new Color(120, 200, 20), Color.green);
/*  985 */       this.coefTable.InitEnd();
/*      */       
/*  987 */       String[] consHeader = new String[5];
/*  988 */       consHeader[0] = "No.";
/*  989 */       consHeader[1] = "Slack or\nSurplus";
/*  990 */       consHeader[2] = "Shadow\nPrice";
/*  991 */       consHeader[3] = "RHS";
/*  992 */       consHeader[4] = "Allowable Range\nfor Right-Hand Side\tMinimum\tMaximum";
/*      */       
/*      */ 
/*      */ 
/*  996 */       this.rhsTable.setHeader(consHeader);
/*      */       
/*  998 */       String[] consData = new String[6 * this.num_constraints];
/*  999 */       for (int i = 0; i < this.num_constraints; i++) {
/* 1000 */         consData[(i * 6 + 0)] = String.valueOf(String.valueOf(new StringBuffer("").append(i + 1)));
/* 1001 */         consData[(i * 6 + 1)] = this.decfm.format(this.senalysData.slack_surplus[i]);
/* 1002 */         consData[(i * 6 + 2)] = this.decfm.format(this.senalysData.shadow_price[i]);
/* 1003 */         consData[(i * 6 + 3)] = this.decfm.format(this.senalysData.cons_value[i]);
/* 1004 */         if (this.senalysData.range_rightside[i][0] < -999999.9D) {
/* 1005 */           consData[(i * 6 + 4)] = "-Inf.";
/*      */         } else
/* 1007 */           consData[(i * 6 + 4)] = this.decfm.format(this.senalysData.range_rightside[i][0]);
/* 1008 */         if (this.senalysData.range_rightside[i][1] > 999999.9D) {
/* 1009 */           consData[(i * 6 + 5)] = "+Inf.";
/*      */         } else
/* 1011 */           consData[(i * 6 + 5)] = this.decfm.format(this.senalysData.range_rightside[i][1]);
/*      */       }
/* 1013 */       this.rhsTable.setData(consData);
/*      */       
/* 1015 */       this.rhsTable.setTopLeft(0, 0);
/* 1016 */       this.rhsTable.setForeground(Color.black, Color.blue);
/* 1017 */       this.rhsTable.setBackground(new Color(120, 200, 20), Color.green);
/* 1018 */       this.rhsTable.InitEnd();
/*      */     }
/*      */     catch (Exception e) {
/* 1021 */       e.printStackTrace();
/* 1022 */       System.out.println(e);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1027 */     this.graphPanels = new JPanel();
/* 1028 */     this.graphPanels.setLayout(null);
/* 1029 */     this.graphPanels.setBackground(this.bkcolor);
/*      */     
/* 1031 */     this.motionLabel = new JLabel(String.valueOf(String.valueOf(new StringBuffer(" Z = ").append(this.decfm.format(this.objectFunction.x1_coef)).append(" x1 + ").append(this.decfm.format(this.objectFunction.x2_coef)).append(" x2").append(" = ").append(this.decfm.format(this.optimalZ)))));
/*      */     
/* 1033 */     this.motionLabel.setForeground(Color.red);
/* 1034 */     this.solidLabel = new JLabel(String.valueOf(String.valueOf(new StringBuffer(" with x1 = ").append(this.decfm.format(this.MaxIntersection.x1)).append(", x2 = ").append(this.decfm.format(this.MaxIntersection.x2)))));
/*      */     
/* 1036 */     this.dymicPanel = new JPanel();
/* 1037 */     this.dymicPanel.setLayout(new GridLayout(2, 1));
/* 1038 */     this.dymicPanel.add(this.motionLabel);
/* 1039 */     this.dymicPanel.add(this.solidLabel);
/*      */     
/* 1041 */     this.sCanvas = new sensCanvas(this, this.gCanvas.globalZoom, this.lpConstraints, this.objectFunction, this.MaxIntersection, this.senalysData.range_objcoef);
/*      */     
/* 1043 */     this.sCanvas.setBounds(0, 0, 340, 340);
/*      */     
/* 1045 */     this.sCanvas.setBackground(Color.white);
/* 1046 */     this.graphPanels.add(this.sCanvas);
/* 1047 */     this.dymicPanel.setBounds(110, 340, 300, 60);
/* 1048 */     this.graphPanels.add(this.dymicPanel);
/*      */     
/* 1050 */     this.leftPanel.removeAll();
/* 1051 */     this.leftPanel.setLayout(null);
/* 1052 */     int w = 240;
/*      */     
/*      */ 
/* 1055 */     this.cotabLabel.setBounds(0, 0, w, 20);
/* 1056 */     this.leftPanel.add(this.cotabLabel);
/*      */     
/* 1058 */     this.coefTable.setBounds(0, 20, w + 40, 85);
/* 1059 */     this.leftPanel.add(this.coefTable);
/* 1060 */     this.dragLabel1.setBounds(0, 125, w, 20);
/* 1061 */     this.dragLabel2.setBounds(0, 145, w, 20);
/* 1062 */     this.effectLabel1.setBounds(0, 165, w, 20);
/* 1063 */     this.effectLabel2.setBounds(0, 185, w, 20);
/* 1064 */     this.leftPanel.add(this.dragLabel1);
/* 1065 */     this.leftPanel.add(this.dragLabel2);
/* 1066 */     this.leftPanel.add(this.effectLabel1);
/* 1067 */     this.leftPanel.add(this.effectLabel2);
/* 1068 */     this.backPanel.setBounds(0, 220, 200, 40);
/* 1069 */     this.leftPanel.add(this.backPanel);
/*      */     
/*      */ 
/*      */ 
/* 1073 */     this.sensPanel.setLayout(null);
/* 1074 */     this.contabLabel.setBounds(80, 0, 100, 20);
/* 1075 */     this.rhsTable.setBounds(80, 20, 500, 100);
/* 1076 */     this.sensPanel.removeAll();
/* 1077 */     this.sensPanel.add(this.contabLabel);
/* 1078 */     this.sensPanel.add(this.rhsTable);
/*      */     
/* 1080 */     this.mainPanel.removeAll();
/*      */     
/* 1082 */     this.leftPanel.setBounds(10, 70, 270, 280);
/*      */     
/* 1084 */     this.mainPanel.add(this.leftPanel);
/*      */     
/* 1086 */     this.graphPanels.setBounds(280, 10, 340, 400);
/*      */     
/* 1088 */     this.mainPanel.add(this.graphPanels);
/*      */     
/* 1090 */     this.sensPanel.setBounds(10, 400, 500, 130);
/*      */     
/* 1092 */     this.mainPanel.add(this.sensPanel);
/*      */     
/*      */ 
/*      */ 
/* 1096 */     validate();
/* 1097 */     repaint();
/*      */     
/* 1099 */     this.gCanvas.repaint();
/* 1100 */     this.rhsTable.repaint();
/* 1101 */     this.coefTable.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void changeZ(float x1cf, float x2cf, float zval)
/*      */   {
/* 1110 */     if (this.motionLabel != null)
/* 1111 */       this.motionLabel.setText(String.valueOf(String.valueOf(new StringBuffer(" Z = ").append(this.decfm.format(x1cf)).append(" x1 + ").append(this.decfm.format(x2cf)).append(" x2").append(" = ").append(this.decfm.format(zval)))));
/* 1112 */     this.dymicPanel.validate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void turnBack()
/*      */   {
/* 1121 */     this.showsens = false;
/* 1122 */     this.mainPanel.removeAll();
/* 1123 */     this.mainPanel.add(this.operatePanel);
/* 1124 */     this.mainPanel.add(this.showCon);
/* 1125 */     this.mainPanel.add(this.graphPanel);
/* 1126 */     this.graphPanel.repaint();
/* 1127 */     this.mainPanel.add(this.showSen);
/* 1128 */     this.obj1Coef.requestFocus();
/* 1129 */     this.obj2Coef.requestFocus();
/* 1130 */     this.objectPanel.validate();
/* 1131 */     this.objectPanel.repaint();
/* 1132 */     this.operatePanel.validate();
/* 1133 */     this.operatePanel.repaint();
/*      */     
/* 1135 */     validate();
/* 1136 */     repaint();
/* 1137 */     this.gCanvas.repaint();
/* 1138 */     this.graphPanel.repaint();
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
/* 1167 */     return "Graphical Linear Programming";
/*      */   }
/*      */   
/*      */   public String[][] getParameterInfo()
/*      */   {
/* 1172 */     return null;
/*      */   }
/*      */   
/*      */   private Frame getFrame(Component c) {
/* 1176 */     Frame frame = null;
/* 1177 */     while ((c = c.getParent()) != null) {
/* 1178 */       if ((c instanceof Frame))
/* 1179 */         frame = (Frame)c;
/*      */     }
/* 1181 */     return frame;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\LinearProg.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */