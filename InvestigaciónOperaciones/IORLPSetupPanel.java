/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Vector;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JRadioButton;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IORLPSetupPanel
/*      */   extends IORLPActionPanel
/*      */ {
/*   29 */   private JRadioButton bn_objective_function = null;
/*   30 */   private IORLPObjectiveFunction objective_function = null;
/*      */   
/*   32 */   private JRadioButton[] bn_linear_equations = null;
/*      */   
/*   34 */   private ButtonGroup bn_group = null;
/*      */   
/*   36 */   private JLabel lb_bound = null;
/*      */   
/*   38 */   private JComboBox cb_operation = null;
/*   39 */   private JButton bn_OK = null;
/*      */   
/*   41 */   private JPanel mainPanel = null;
/*   42 */   private JPanel outputPanel = null;
/*   43 */   private JPanel inputPanel = null;
/*      */   
/*   45 */   private JPanel selectVariablePanel = null;
/*   46 */   private JPanel enterConstantPanel = null;
/*      */   
/*   48 */   private JPanel artificialSelectionPanel = null;
/*   49 */   private JPanel methodSelectionPanel = null;
/*   50 */   private JPanel twoPhasePanel = null;
/*   51 */   private JComboBox cb_method = null;
/*      */   
/*   53 */   private IORLPArtificialPanel artificialPanel = null;
/*      */   
/*   55 */   private DecimalField df_constant = null;
/*   56 */   private WholeNumberField wnf_variable_subscript = null;
/*   57 */   private JButton bn_variable_subscript = null;
/*   58 */   private JButton bn_enter_constant = null;
/*      */   
/*      */ 
/*      */ 
/*      */   protected void backStep()
/*      */   {
/*   64 */     if (this.state != null) {
/*   65 */       this.controller.solver.reset();
/*   66 */       this.state.back();
/*      */       
/*   68 */       hideSelectivePanel();
/*   69 */       updatePanel();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void finishProcedure()
/*      */   {
/*   78 */     this.state.reset();
/*   79 */     updatePanel();
/*   80 */     this.controller.solver.setBookmark();
/*      */     
/*   82 */     if (hasArtificialVariable()) {
/*   83 */       generateArtificialSelectionPanel();
/*      */     }
/*      */     else {
/*   86 */       this.actionStatus.setText("The current procedure is finished. Please go to the Procedure menu to continue!");
/*      */       
/*   88 */       this.controller.setMenuState(11);
/*   89 */       setPanelEditable(false);
/*      */       
/*   91 */       Vector v = new Vector();
/*   92 */       IOROperation op = new IOROperation(10212, v);
/*   93 */       this.controller.solver.doWork(op, this.controller.data);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPanelEditable(boolean b)
/*      */   {
/*  102 */     this.cb_operation.setEnabled(b);
/*  103 */     this.bn_OK.setEnabled(b);
/*  104 */     if (!this.state.isEmpty()) {
/*  105 */       this.bn_back.setEnabled(b);
/*      */     }
/*  107 */     setFinishEnabled(b);
/*      */   }
/*      */   
/*      */   private boolean hasArtificialVariable() {
/*  111 */     int N = this.controller.data.num_variable;
/*  112 */     boolean[] b = this.controller.data.is_artificial;
/*      */     
/*  114 */     for (int i = 1; i <= N; i++) {
/*  115 */       if (b[i] != 0)
/*  116 */         return true;
/*      */     }
/*  118 */     return false;
/*      */   }
/*      */   
/*  121 */   private final String[] methodString = { "Big M", "Two-Phase" };
/*      */   
/*      */   private void generateArtificialSelectionPanel()
/*      */   {
/*  125 */     JPanel p = null;
/*  126 */     if (this.artificialSelectionPanel == null)
/*      */     {
/*  128 */       this.artificialPanel = new IORLPArtificialPanel(this.controller);
/*      */       
/*  130 */       p = new JPanel();
/*  131 */       JLabel prompt = new JLabel("Select one method ");
/*  132 */       this.cb_method = new JComboBox(this.methodString);
/*      */       
/*  134 */       JButton select = new JButton("OK");
/*  135 */       select.addActionListener(new ActionListener() {
/*      */         public void actionPerformed(ActionEvent e) {
/*  137 */           boolean is_big_M = false;
/*  138 */           if (IORLPSetupPanel.this.cb_method.getSelectedIndex() == 0) {
/*  139 */             is_big_M = true;
/*      */           }
/*  141 */           Vector v = new Vector();
/*  142 */           v.addElement(new Boolean(is_big_M));
/*  143 */           IOROperation op = new IOROperation(10209, v);
/*  144 */           IORLPSetupPanel.this.controller.solver.doWork(op, IORLPSetupPanel.this.controller.data);
/*      */           
/*  146 */           if (is_big_M) {
/*  147 */             IORLPSetupPanel.this.switchToArtificialPanel();
/*  148 */             IORLPSetupPanel.this.controller.solver.setBookmark();
/*      */           }
/*      */           else {
/*  151 */             IORLPSetupPanel.this.showTwoPhasePanel();
/*      */           }
/*      */         }
/*  154 */       });
/*  155 */       p.add(prompt);
/*  156 */       p.add(this.cb_method);
/*  157 */       p.add(select);
/*      */       
/*  159 */       String str = "\nWhile setting the model up for the Simplex Method, you added artificial variables.\n";
/*  160 */       str = String.valueOf(String.valueOf(str)).concat("Do you wish to deal with the artificial variables by using the Big M method or the Two-Phase method?\n");
/*  161 */       str = String.valueOf(String.valueOf(str)).concat("\nNOTE: If you later plan to apply the Sensitivity Analysis routine under the Procedure menu, ");
/*  162 */       str = String.valueOf(String.valueOf(str)).concat("then you must use the Big M method.");
/*      */       
/*  164 */       MultilineLabel ta = new MultilineLabel(str);
/*  165 */       ta.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*      */       
/*  167 */       this.methodSelectionPanel = new JPanel(new BorderLayout());
/*  168 */       this.methodSelectionPanel.add(ta, "North");
/*  169 */       this.methodSelectionPanel.add(p, "South");
/*      */       
/*  171 */       String str2 = "\nArtificial variables: ";
/*      */       
/*  173 */       boolean[] b = this.controller.data.is_artificial;
/*  174 */       for (int k = 1; k <= this.controller.data.num_variable; k++) {
/*  175 */         if (b[k] != 0) {
/*  176 */           str2 = String.valueOf(String.valueOf(str2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("X").append(k).append(" "))))));
/*      */         }
/*      */       }
/*  179 */       str2 = String.valueOf(String.valueOf(str2)).concat("\nTo begin Phase 1 of the Two-Phase method to deal with these artificial variables, you now ");
/*  180 */       str2 = String.valueOf(String.valueOf(str2)).concat("need to enter a new objective function below.");
/*      */       
/*  182 */       MultilineLabel ta2 = new MultilineLabel(str2);
/*  183 */       ta2.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*      */       
/*  185 */       JPanel p2 = new JPanel(new BorderLayout());
/*  186 */       p2.add(ta2, "Center");
/*      */       
/*  188 */       JPanel p3 = new JPanel();
/*  189 */       this.objective_function = new IORLPObjectiveFunction(this.controller.data.num_variable);
/*  190 */       p3.add(this.objective_function);
/*  191 */       JButton objective = new JButton("OK");
/*  192 */       p3.add(objective);
/*  193 */       objective.addActionListener(new ActionListener() {
/*      */         public void actionPerformed(ActionEvent e) {
/*  195 */           if (IORLPSetupPanel.this.checkObjectiveFunction()) {
/*  196 */             IORLPSetupPanel.this.hideTwoPhasePanel();
/*  197 */             IORLPSetupPanel.this.switchToArtificialPanel();
/*  198 */             Vector v = new Vector();
/*  199 */             IOROperation op = new IOROperation(10210, v);
/*  200 */             IORLPSetupPanel.this.controller.solver.doWork(op, IORLPSetupPanel.this.controller.data);
/*  201 */             IORLPSetupPanel.this.controller.solver.setBookmark();
/*      */           }
/*      */           else {
/*  204 */             JOptionPane.showMessageDialog(IORLPSetupPanel.this, "Invalid Objective Function for the Two-Phase method!");
/*      */           }
/*      */           
/*      */         }
/*      */         
/*  209 */       });
/*  210 */       this.twoPhasePanel = new JPanel(new BorderLayout());
/*  211 */       this.twoPhasePanel.add(p2, "North");
/*  212 */       this.twoPhasePanel.add(p3, "South");
/*      */       
/*  214 */       this.artificialSelectionPanel = new JPanel(new BorderLayout());
/*  215 */       this.artificialSelectionPanel.add(this.methodSelectionPanel, "North");
/*  216 */       this.artificialSelectionPanel.add(this.twoPhasePanel, "South");
/*      */       
/*  218 */       hideTwoPhasePanel();
/*      */     }
/*      */     
/*  221 */     this.artificialPanel.setPanelEditable(true);
/*      */     
/*      */ 
/*  224 */     this.mainPanel.removeAll();
/*  225 */     this.mainPanel.add(this.artificialSelectionPanel);
/*      */     
/*  227 */     this.artificialSelectionPanel.revalidate();
/*  228 */     this.artificialSelectionPanel.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean checkObjectiveFunction()
/*      */   {
/*  234 */     int N = this.controller.data.num_variable;
/*  235 */     double[] c = new double[N];
/*  236 */     this.objective_function.getCoefficient(c);
/*  237 */     boolean[] b = this.controller.data.is_artificial;
/*  238 */     boolean valid = true;
/*  239 */     for (int k = 1; k <= N; k++) {
/*  240 */       if (b[k] != 0) {
/*  241 */         if (Math.abs(c[(k - 1)] - 1) > 1.0E-13D) {
/*  242 */           valid = false;
/*  243 */           break;
/*      */         }
/*      */         
/*      */       }
/*  247 */       else if (Math.abs(c[(k - 1)]) > 1.0E-13D) {
/*  248 */         valid = false;
/*  249 */         break;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  254 */     if (valid) {
/*  255 */       if (this.objective_function.isMax().booleanValue()) {
/*  256 */         valid = false;
/*      */       } else {
/*  258 */         return true;
/*      */       }
/*      */     }
/*  261 */     valid = true;
/*  262 */     for (int k = 1; k <= N; k++) {
/*  263 */       if (b[k] != 0) {
/*  264 */         if (Math.abs(c[(k - 1)] + 1) > 1.0E-13D) {
/*  265 */           valid = false;
/*  266 */           break;
/*      */         }
/*      */         
/*      */       }
/*  270 */       else if (Math.abs(c[(k - 1)]) > 1.0E-13D) {
/*  271 */         valid = false;
/*  272 */         break;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  277 */     if ((valid) && 
/*  278 */       (!this.objective_function.isMax().booleanValue())) {
/*  279 */       valid = false;
/*      */     }
/*      */     
/*  282 */     return valid;
/*      */   }
/*      */   
/*      */   private void switchToArtificialPanel() {
/*  286 */     this.controller.currentActionPanel = this.artificialPanel;
/*  287 */     this.controller.setView(this.artificialPanel);
/*  288 */     this.artificialPanel.updatePanel();
/*  289 */     this.artificialPanel.revalidate();
/*      */   }
/*      */   
/*  292 */   private final String selectHelpString2 = "Please enter a new objective function: ";
/*      */   
/*      */   private String getObjectiveFunctionText()
/*      */   {
/*  296 */     String t = null;String sign = null;
/*      */     
/*  298 */     boolean isMax = this.controller.data.objective_is_max;
/*  299 */     int N = this.controller.data.num_variable;
/*      */     
/*  301 */     double[] d = this.controller.data.objective_coefficient;
/*  302 */     double[] dm = this.controller.data.objective_M_coefficient;
/*  303 */     boolean[] b = this.controller.data.is_artificial;
/*      */     
/*      */ 
/*  306 */     if (isMax) {
/*  307 */       t = "Maximize Z: ";
/*      */     } else
/*  309 */       t = "Minimize Z: ";
/*      */     String t1;
/*  311 */     String t1; if (d[0] == 0) {
/*  312 */       t1 = " = 0";
/*      */     } else
/*  314 */       t1 = " = ".concat(String.valueOf(String.valueOf(IORActionPanel.trim(d[0]))));
/*      */     String str;
/*  316 */     if (this.controller.data.original_objective_is_max == this.controller.data.objective_is_max) {
/*  317 */       str = " Z ";
/*      */     } else {
/*  319 */       str = " - Z";
/*      */     }
/*  321 */     for (int i = 1; i <= N; i++) {
/*  322 */       if (dm[i] == 0) {
/*  323 */         if (d[i] == 0) {
/*  324 */           sign = " + 0";
/*  325 */         } else if (d[i] > 0) {
/*  326 */           sign = " + ".concat(String.valueOf(String.valueOf(IORActionPanel.trim(d[i]))));
/*  327 */         } else if (d[i] < 0) {
/*  328 */           sign = " - ".concat(String.valueOf(String.valueOf(IORActionPanel.trim(-d[i]))));
/*      */         }
/*  330 */       } else if (dm[i] > 0) {
/*  331 */         if (d[i] == 0) {
/*  332 */           sign = String.valueOf(String.valueOf(new StringBuffer(" + ").append(IORActionPanel.trim(dm[i])).append("M")));
/*  333 */         } else if (d[i] > 0) {
/*  334 */           sign = String.valueOf(String.valueOf(new StringBuffer(" + (").append(IORActionPanel.trim(dm[i])).append("M + ").append(IORActionPanel.trim(d[i])).append(")")));
/*  335 */         } else if (d[i] < 0) {
/*  336 */           sign = String.valueOf(String.valueOf(new StringBuffer(" + (").append(IORActionPanel.trim(dm[i])).append("M - ").append(IORActionPanel.trim(-d[i])).append(")")));
/*      */         }
/*  338 */       } else if (dm[i] < 0) {
/*  339 */         if (d[i] == 0) {
/*  340 */           sign = String.valueOf(String.valueOf(new StringBuffer(" - ").append(IORActionPanel.trim(-dm[i])).append("M")));
/*  341 */         } else if (d[i] > 0) {
/*  342 */           sign = String.valueOf(String.valueOf(new StringBuffer(" - (").append(IORActionPanel.trim(-dm[i])).append("M - ").append(IORActionPanel.trim(d[i])).append(")")));
/*  343 */         } else if (d[i] < 0) {
/*  344 */           sign = String.valueOf(String.valueOf(new StringBuffer(" - (").append(IORActionPanel.trim(-dm[i])).append("M + ").append(IORActionPanel.trim(-d[i])).append(")")));
/*      */         }
/*      */       }
/*  347 */       if (b[i] != 0) {
/*  348 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(sign))).append(" X").append(i))))));
/*      */       } else {
/*  350 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(sign))).append(" x").append(i))))));
/*      */       }
/*      */     }
/*  353 */     String str = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(t))).append(str).append(t1)));
/*  354 */     return str;
/*      */   }
/*      */   
/*      */   private String getBoundText() {
/*  358 */     String str = "";
/*  359 */     int N = this.controller.data.num_variable;
/*      */     
/*  361 */     double[] d = this.controller.data.bound;
/*  362 */     boolean[] b = this.controller.data.bound_operator;
/*  363 */     boolean[] b1 = this.controller.data.is_artificial;
/*      */     
/*      */ 
/*  366 */     for (int i = 1; i <= N; i++) {
/*  367 */       if (b[i] != 0) {
/*  368 */         if (b1[i] != 0) {
/*  369 */           str = String.valueOf(String.valueOf(str)).concat("X");
/*      */         } else {
/*  371 */           str = String.valueOf(String.valueOf(str)).concat("x");
/*      */         }
/*  373 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(i))).append(" >=").append(" ").append(IORActionPanel.trim(d[i])).append("   "))))));
/*      */       }
/*      */     }
/*      */     
/*  377 */     return str;
/*      */   }
/*      */   
/*      */   private String getLinearEquationText(int id) {
/*  381 */     String str = "";
/*  382 */     String sign = "";
/*  383 */     String a = null;
/*      */     
/*  385 */     int op = this.controller.data.constrain_operator[id];
/*  386 */     int N = this.controller.data.num_variable;
/*  387 */     boolean[] b = this.controller.data.is_artificial;
/*  388 */     double[] d = this.controller.data.constrain_coefficient[id];
/*      */     
/*  390 */     if (op == 0) {
/*  391 */       a = " <= ";
/*      */     }
/*  393 */     else if (op == 1) {
/*  394 */       a = " = ";
/*  395 */     } else if (op == 2) {
/*  396 */       a = " >= ";
/*      */     }
/*      */     
/*  399 */     for (int i = 1; i <= N; i++) {
/*  400 */       if (i == 1) {
/*  401 */         sign = String.valueOf(String.valueOf(sign)).concat(String.valueOf(String.valueOf(IORActionPanel.trim(d[i]))));
/*      */       }
/*  403 */       else if (d[i] > 0) {
/*  404 */         sign = " + ".concat(String.valueOf(String.valueOf(IORActionPanel.trim(d[i]))));
/*  405 */       } else if (d[i] < 0) {
/*  406 */         sign = " - ".concat(String.valueOf(String.valueOf(IORActionPanel.trim(-d[i]))));
/*  407 */       } else if (d[i] == 0) {
/*  408 */         sign = " + 0";
/*      */       }
/*      */       
/*  411 */       if (b[i] != 0) {
/*  412 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(sign))).append(" X").append(i))))));
/*      */       } else {
/*  414 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(sign))).append(" x").append(i))))));
/*      */       }
/*      */     }
/*  417 */     str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(a)).concat(String.valueOf(String.valueOf(IORActionPanel.trim(d[0])))))));
/*  418 */     return str;
/*      */   }
/*      */   
/*      */   private IOROperation addSlackVariable(int id)
/*      */   {
/*  423 */     Vector p = new Vector();
/*      */     
/*  425 */     p.addElement(new Integer(id));
/*      */     
/*  427 */     IOROperation operation = new IOROperation(10201, p);
/*      */     
/*      */ 
/*  430 */     return operation;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private IOROperation substractSurplusVariable(int id)
/*      */   {
/*  437 */     Vector p = new Vector();
/*      */     
/*  439 */     p.addElement(new Integer(id));
/*      */     
/*  441 */     IOROperation operation = new IOROperation(10202, p);
/*      */     
/*      */ 
/*  444 */     return operation;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private IOROperation addArtificialVariable(int id)
/*      */   {
/*  451 */     Vector p = new Vector();
/*      */     
/*  453 */     p.addElement(new Integer(id));
/*      */     
/*  455 */     IOROperation operation = new IOROperation(10203, p);
/*      */     
/*      */ 
/*  458 */     return operation;
/*      */   }
/*      */   
/*      */   private IOROperation multiplyEquationByMinusOne(int id)
/*      */   {
/*  463 */     Vector p = new Vector();
/*      */     
/*  465 */     p.addElement(new Integer(id));
/*      */     
/*  467 */     IOROperation operation = new IOROperation(10204, p);
/*      */     
/*      */ 
/*  470 */     return operation;
/*      */   }
/*      */   
/*      */   private IOROperation convertOneVariableToDifferenceOfTwoVariables(int id)
/*      */   {
/*  475 */     Vector p = new Vector();
/*      */     
/*  477 */     p.addElement(new Integer(id));
/*      */     
/*  479 */     IOROperation operation = new IOROperation(10205, p);
/*      */     
/*      */ 
/*  482 */     return operation;
/*      */   }
/*      */   
/*      */   private IOROperation convertOneVariableToVariableAddConstant(int id, double d)
/*      */   {
/*  487 */     Vector p = new Vector();
/*      */     
/*      */ 
/*  490 */     p.addElement(new Integer(id));
/*  491 */     p.addElement(new Double(d));
/*      */     
/*  493 */     IOROperation operation = new IOROperation(10206, p);
/*      */     
/*      */ 
/*  496 */     return operation;
/*      */   }
/*      */   
/*      */   private IOROperation convertToOriginalModel()
/*      */   {
/*  501 */     Vector p = new Vector();
/*  502 */     IOROperation operation = new IOROperation(10208, p);
/*      */     
/*      */ 
/*  505 */     return operation;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updatePanel()
/*      */   {
/*  513 */     this.controller.solver.getData(this.controller.data);
/*  514 */     String str = null;
/*      */     
/*  516 */     this.bn_objective_function.setText(getObjectiveFunctionText());
/*  517 */     for (int i = 1; i <= this.controller.data.num_constrain; i++) {
/*  518 */       str = getLinearEquationText(i);
/*  519 */       this.bn_linear_equations[(i - 1)].setText(str);
/*      */     }
/*      */     
/*  522 */     str = getBoundText();
/*  523 */     this.lb_bound.setText(str);
/*      */     
/*  525 */     if (this.state.isEmpty()) {
/*  526 */       this.bn_back.setEnabled(false);
/*      */     }
/*      */     else {
/*  529 */       this.bn_back.setEnabled(true);
/*      */     }
/*  531 */     repaint();
/*      */   }
/*      */   
/*  534 */   private final String[] functionStrings = { "Add Slack Variable", "Subtract Surplus Variable", "Add Artificial Variable", "Multiply Equation by (-1)", "Convert One Variable to Difference of Two Variables", "Convert One Variable to Variable Plus Constant", "Revert to Original Model" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  542 */   private final int ADD_SLACK = 0;
/*  543 */   private final int SUBSTRACT_SURPLUS = 1;
/*  544 */   private final int ADD_ARTIFICIAL = 2;
/*  545 */   private final int MULTIPLY_MINUS_ONE = 3;
/*  546 */   private final int CONVERT_TO_DIFFERENCE = 4;
/*  547 */   private final int CONVERT_TO_PLUS_CONSTANT = 5;
/*      */   
/*  549 */   private final int CONVERT_TO_ORIGINAL = 6;
/*      */   
/*      */   private int getEquationID()
/*      */   {
/*  553 */     int id = -1;
/*  554 */     Enumeration e = this.bn_group.getElements();
/*      */     
/*  556 */     for (int i = 0; e.hasMoreElements(); i++) {
/*  557 */       if (((JRadioButton)e.nextElement()).isSelected()) {
/*  558 */         id = i;
/*  559 */         break;
/*      */       }
/*      */     }
/*      */     
/*  563 */     return id;
/*      */   }
/*      */   
/*      */   private int getVariableID() {
/*  567 */     int id = this.wnf_variable_subscript.getValue();
/*  568 */     if (id <= 0)
/*  569 */       id = -1;
/*  570 */     return id;
/*      */   }
/*      */   
/*      */   private void doOK() {
/*  574 */     hideSelectivePanel();
/*  575 */     IOROperation operation = null;
/*  576 */     int equation_id = -1;
/*      */     
/*  578 */     switch (this.cb_operation.getSelectedIndex())
/*      */     {
/*      */ 
/*      */ 
/*      */     case 0: 
/*  583 */       equation_id = getEquationID();
/*  584 */       if (equation_id >= 0) {
/*  585 */         operation = addSlackVariable(equation_id);
/*      */       }
/*  587 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 1: 
/*  592 */       equation_id = getEquationID();
/*  593 */       if (equation_id >= 0) {
/*  594 */         operation = substractSurplusVariable(equation_id);
/*      */       }
/*  596 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 2: 
/*  601 */       equation_id = getEquationID();
/*  602 */       if (equation_id >= 0) {
/*  603 */         operation = addArtificialVariable(equation_id);
/*      */       }
/*  605 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 3: 
/*  610 */       equation_id = getEquationID();
/*  611 */       if (equation_id >= 0) {
/*  612 */         operation = multiplyEquationByMinusOne(equation_id);
/*      */       }
/*  614 */       break;
/*      */     
/*      */ 
/*      */     case 4: 
/*  618 */       showSelectVariablePanel();
/*  619 */       break;
/*      */     
/*      */ 
/*      */     case 5: 
/*  623 */       showSelectVariablePanel();
/*  624 */       break;
/*      */     
/*      */ 
/*      */     case 6: 
/*  628 */       operation = convertToOriginalModel();
/*  629 */       break;
/*      */     
/*      */     default: 
/*  632 */       System.out.println("ERROR in doOK()");
/*      */     }
/*      */     
/*  635 */     if ((operation != null) && 
/*  636 */       (this.controller.solver.doWork(operation, this.controller.data))) {
/*  637 */       this.state.addStep(operation);
/*  638 */       updatePanel();
/*      */     }
/*      */   }
/*      */   
/*      */   private void hideSelectivePanel()
/*      */   {
/*  644 */     if ((this.selectVariablePanel != null) && 
/*  645 */       (this.selectVariablePanel.isVisible())) {
/*  646 */       this.selectVariablePanel.setVisible(false);
/*  647 */       this.selectVariablePanel.revalidate();
/*  648 */       repaint();
/*      */     }
/*      */     
/*  651 */     if ((this.enterConstantPanel != null) && 
/*  652 */       (this.enterConstantPanel.isVisible())) {
/*  653 */       this.enterConstantPanel.setVisible(false);
/*  654 */       this.enterConstantPanel.revalidate();
/*  655 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */   private void hideTwoPhasePanel()
/*      */   {
/*  661 */     if ((this.twoPhasePanel != null) && 
/*  662 */       (this.twoPhasePanel.isVisible())) {
/*  663 */       this.twoPhasePanel.setVisible(false);
/*  664 */       this.twoPhasePanel.revalidate();
/*  665 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */   private void showTwoPhasePanel()
/*      */   {
/*  671 */     if ((this.twoPhasePanel != null) && 
/*  672 */       (!this.twoPhasePanel.isVisible())) {
/*  673 */       this.twoPhasePanel.setVisible(true);
/*  674 */       this.twoPhasePanel.revalidate();
/*  675 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */   private void showSelectVariablePanel()
/*      */   {
/*  681 */     if ((this.enterConstantPanel != null) && 
/*  682 */       (this.enterConstantPanel.isVisible())) {
/*  683 */       this.enterConstantPanel.setVisible(false);
/*  684 */       this.enterConstantPanel.revalidate();
/*  685 */       repaint();
/*      */     }
/*      */     
/*  688 */     if ((this.selectVariablePanel != null) && 
/*  689 */       (!this.selectVariablePanel.isVisible())) {
/*  690 */       this.selectVariablePanel.setVisible(true);
/*  691 */       this.selectVariablePanel.revalidate();
/*  692 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void showEnterConstantPanel()
/*      */   {
/*  699 */     if ((this.selectVariablePanel != null) && 
/*  700 */       (this.selectVariablePanel.isVisible())) {
/*  701 */       this.selectVariablePanel.setVisible(false);
/*  702 */       this.selectVariablePanel.revalidate();
/*  703 */       repaint();
/*      */     }
/*      */     
/*      */ 
/*  707 */     if ((this.enterConstantPanel != null) && 
/*  708 */       (!this.enterConstantPanel.isVisible())) {
/*  709 */       this.enterConstantPanel.setVisible(true);
/*  710 */       this.enterConstantPanel.revalidate();
/*  711 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */   private void doOK2()
/*      */   {
/*  717 */     IOROperation operation = null;
/*  718 */     int variable_id = -1;
/*      */     
/*  720 */     switch (this.cb_operation.getSelectedIndex())
/*      */     {
/*      */ 
/*      */ 
/*      */     case 4: 
/*  725 */       variable_id = getVariableID();
/*      */       
/*  727 */       if (variable_id > 0) {
/*  728 */         operation = convertOneVariableToDifferenceOfTwoVariables(variable_id);
/*      */       }
/*  730 */       hideSelectivePanel();
/*      */       
/*  732 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 5: 
/*  738 */       showEnterConstantPanel();
/*      */       
/*  740 */       break;
/*      */     
/*      */     default: 
/*  743 */       System.out.println("ERROR in doOK2()");
/*      */     }
/*      */     
/*  746 */     if ((operation != null) && 
/*  747 */       (this.controller.solver.doWork(operation, this.controller.data))) {
/*  748 */       this.state.addStep(operation);
/*  749 */       updatePanel();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void doOK3()
/*      */   {
/*  756 */     IOROperation operation = null;
/*  757 */     int variable_id = -1;
/*      */     
/*  759 */     switch (this.cb_operation.getSelectedIndex())
/*      */     {
/*      */ 
/*      */ 
/*      */     case 5: 
/*  764 */       variable_id = getVariableID();
/*      */       
/*  766 */       if (variable_id > 0) {
/*  767 */         operation = convertOneVariableToVariableAddConstant(variable_id, this.df_constant.getValue());
/*      */       }
/*      */       
/*  770 */       hideSelectivePanel();
/*      */       
/*  772 */       break;
/*      */     
/*      */     default: 
/*  775 */       System.out.println("ERROR in doOK3()");
/*      */     }
/*      */     
/*  778 */     if ((operation != null) && 
/*  779 */       (this.controller.solver.doWork(operation, this.controller.data))) {
/*  780 */       this.state.addStep(operation);
/*  781 */       updatePanel();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IORLPSetupPanel(IORLPProcessController c)
/*      */   {
/*  795 */     super(c);
/*      */     
/*  797 */     this.state = new IORState(c.solver);
/*      */     
/*  799 */     this.mainPanel = new JPanel();
/*  800 */     add(this.mainPanel, "Center");
/*      */     
/*  802 */     JPanel p0 = new JPanel();
/*  803 */     JLabel prompt = new JLabel("Select one of the following actions, and then press the OK button.");
/*  804 */     p0.add(prompt);
/*      */     
/*  806 */     this.cb_operation = new JComboBox(this.functionStrings);
/*      */     
/*  808 */     this.bn_OK = new JButton("OK");
/*  809 */     this.bn_OK.addActionListener(new ActionListener()
/*      */     {
/*  811 */       public void actionPerformed(ActionEvent e) { IORLPSetupPanel.this.doOK(); }
/*  812 */     });
/*  813 */     JPanel p1 = new JPanel();
/*  814 */     p1.add(this.cb_operation);
/*  815 */     p1.add(this.bn_OK);
/*      */     
/*  817 */     JPanel P1 = new JPanel(new BorderLayout());
/*  818 */     P1.add(p0, "North");
/*  819 */     P1.add(p1, "South");
/*      */     
/*  821 */     this.selectVariablePanel = new JPanel();
/*  822 */     this.selectVariablePanel.add(new JLabel("Please enter variable subscript: "));
/*  823 */     this.wnf_variable_subscript = new WholeNumberField(1, 2);
/*  824 */     this.selectVariablePanel.add(this.wnf_variable_subscript);
/*  825 */     this.bn_variable_subscript = new JButton("OK");
/*  826 */     this.bn_variable_subscript.addActionListener(new ActionListener()
/*      */     {
/*  828 */       public void actionPerformed(ActionEvent e) { IORLPSetupPanel.this.doOK2(); }
/*  829 */     });
/*  830 */     this.selectVariablePanel.add(this.bn_variable_subscript);
/*  831 */     this.selectVariablePanel.setVisible(false);
/*      */     
/*  833 */     this.enterConstantPanel = new JPanel();
/*  834 */     this.enterConstantPanel.add(new JLabel("Please enter the constant: "));
/*  835 */     this.df_constant = new DecimalField(0.0D, new DecimalFormat());
/*  836 */     this.enterConstantPanel.add(this.df_constant);
/*  837 */     this.bn_enter_constant = new JButton("OK");
/*  838 */     this.bn_enter_constant.addActionListener(new ActionListener()
/*      */     {
/*  840 */       public void actionPerformed(ActionEvent e) { IORLPSetupPanel.this.doOK3(); }
/*  841 */     });
/*  842 */     this.enterConstantPanel.add(this.bn_enter_constant);
/*  843 */     this.enterConstantPanel.setVisible(false);
/*      */     
/*  845 */     JPanel selectivePanel = new JPanel();
/*  846 */     selectivePanel.add(this.selectVariablePanel);
/*  847 */     selectivePanel.add(this.enterConstantPanel);
/*      */     
/*  849 */     this.inputPanel = new JPanel();
/*  850 */     this.inputPanel.setLayout(new BorderLayout());
/*  851 */     this.inputPanel.add(P1, "Center");
/*  852 */     this.inputPanel.add(selectivePanel, "South");
/*      */     
/*      */ 
/*  855 */     this.outputPanel = new JPanel();
/*  856 */     this.outputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 50));
/*      */     
/*  858 */     this.outputPanel.setLayout(new BoxLayout(this.outputPanel, 1));
/*  859 */     this.bn_group = new ButtonGroup();
/*      */     
/*  861 */     String str = getObjectiveFunctionText();
/*      */     
/*  863 */     this.bn_objective_function = new JRadioButton(str);
/*  864 */     this.outputPanel.add(this.bn_objective_function);
/*  865 */     this.bn_group.add(this.bn_objective_function);
/*      */     
/*  867 */     this.bn_linear_equations = new JRadioButton[this.controller.data.num_constrain];
/*  868 */     for (int i = 0; i < this.controller.data.num_constrain; i++) {
/*  869 */       str = getLinearEquationText(i + 1);
/*  870 */       this.bn_linear_equations[i] = new JRadioButton(str);
/*  871 */       this.outputPanel.add(this.bn_linear_equations[i]);
/*  872 */       this.bn_group.add(this.bn_linear_equations[i]);
/*      */     }
/*      */     
/*  875 */     this.outputPanel.add(Box.createRigidArea(new Dimension(15, 15)));
/*  876 */     str = getBoundText();
/*  877 */     this.lb_bound = new JLabel(str);
/*  878 */     this.outputPanel.add(this.lb_bound);
/*      */     
/*  880 */     this.mainPanel.setLayout(new BorderLayout());
/*  881 */     this.mainPanel.add(this.inputPanel, "South");
/*  882 */     this.mainPanel.add(this.outputPanel, "Center");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/*  893 */     String str = "Set up for the Simplex Method - Interactive Only \n\n";
/*  894 */     str = String.valueOf(String.valueOf(str)).concat("This procedure is used to implement the initialization step of the ");
/*  895 */     str = String.valueOf(String.valueOf(str)).concat("Simplex Method as described in Secs. 4.2 and 4.6. The linear programming ");
/*  896 */     str = String.valueOf(String.valueOf(str)).concat("model is augmented as needed to be able to apply the Simplex Method as ");
/*  897 */     str = String.valueOf(String.valueOf(str)).concat("it is presented in Chapter 4. The converted model must have the following ");
/*  898 */     str = String.valueOf(String.valueOf(str)).concat("properties:\n");
/*  899 */     str = String.valueOf(String.valueOf(str)).concat("   1.  The objective needs to be to maximize Z or (-Z). \n");
/*  900 */     str = String.valueOf(String.valueOf(str)).concat("   2.  Each functional constraint needs to be in equality form.\n");
/*  901 */     str = String.valueOf(String.valueOf(str)).concat("   3.  Each functional constraint needs an initial basic variable.\n");
/*  902 */     str = String.valueOf(String.valueOf(str)).concat("   4.  Each functional constraint needs a nonnegative right-hand side.\n");
/*  903 */     str = String.valueOf(String.valueOf(str)).concat("   5.  Each variable needs to have a nonnegativity constraint.\n\n");
/*  904 */     str = String.valueOf(String.valueOf(str)).concat("If you introduce artificial variables, you will have the choice of dealing ");
/*  905 */     str = String.valueOf(String.valueOf(str)).concat("with them with either the Big M method or the Two-Phase method. To correct ");
/*  906 */     str = String.valueOf(String.valueOf(str)).concat("a mistake, press the BACK button to backtrack.\n\n");
/*  907 */     str = String.valueOf(String.valueOf(str)).concat("Augmenting the model\n\n");
/*  908 */     str = String.valueOf(String.valueOf(str)).concat("Use the mouse to select an equation or inequality that needs ");
/*  909 */     str = String.valueOf(String.valueOf(str)).concat("to be modified. Then choose one or more of the four commands in turn to perform ");
/*  910 */     str = String.valueOf(String.valueOf(str)).concat("the stated modification:\n");
/*  911 */     str = String.valueOf(String.valueOf(str)).concat("          Add Slack Variable\n");
/*  912 */     str = String.valueOf(String.valueOf(str)).concat("          Subtract Surplus Variable\n");
/*  913 */     str = String.valueOf(String.valueOf(str)).concat("          Add Artificial Variable (represented by a Capital Letter)\n");
/*  914 */     str = String.valueOf(String.valueOf(str)).concat("          Multiply Equation by (-1)\n\n");
/*  915 */     str = String.valueOf(String.valueOf(str)).concat("The following commands are used to begin dealing with the variables allowed ");
/*  916 */     str = String.valueOf(String.valueOf(str)).concat("to be negative:\n");
/*  917 */     str = String.valueOf(String.valueOf(str)).concat("     1.   Convert One Variable to Difference of Two Variables\n");
/*  918 */     str = String.valueOf(String.valueOf(str)).concat("     2.   Convert One Variable to Variable Plus Constant\n\n");
/*  919 */     str = String.valueOf(String.valueOf(str)).concat("Select the first command for a variable with no bound on the negative values allowed. ");
/*  920 */     str = String.valueOf(String.valueOf(str)).concat("Select the second command for a variable with a nonzero lower bound. Select Revert to Original Model to ");
/*  921 */     str = String.valueOf(String.valueOf(str)).concat("restart to correct a mistake prior to the preceding step. Once you are finished ");
/*  922 */     str = String.valueOf(String.valueOf(str)).concat("converting the model to augmented form, press the FINISH button.");
/*  923 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/*  925 */     this.help_content_step.setText(str);
/*  926 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/*  934 */     String str = "Set up for the Simplex Method - Interactive Only\n\n";
/*  935 */     str = String.valueOf(String.valueOf(str)).concat("Once you have entered (or revised) a linear programming model, this procedure ");
/*  936 */     str = String.valueOf(String.valueOf(str)).concat("enables you to quickly set up the model in preparation for solving it ");
/*  937 */     str = String.valueOf(String.valueOf(str)).concat("interactively by the Simplex Method. In particular, you will be introducing ");
/*  938 */     str = String.valueOf(String.valueOf(str)).concat("slack variables as described in Sec.4.2 and, if needed, adapting to nonstandard ");
/*  939 */     str = String.valueOf(String.valueOf(str)).concat("forms as described in Sec.4.6. When you finish adding slack variables, etc., you will ");
/*  940 */     str = String.valueOf(String.valueOf(str)).concat("have a choice. If you introduced no artificial variables, then you should next ");
/*  941 */     str = String.valueOf(String.valueOf(str)).concat("choose the 'Solve Interactively by the Simplex Method' procedudre. If one or more ");
/*  942 */     str = String.valueOf(String.valueOf(str)).concat("artificial variables have been introduced, then you will be asked whether you ");
/*  943 */     str = String.valueOf(String.valueOf(str)).concat("would like to use the Big M or Two-Phase method to handle them. In the latter case, ");
/*  944 */     str = String.valueOf(String.valueOf(str)).concat("you will be asked to enter a new objective function for Phase 1. In either case, ");
/*  945 */     str = String.valueOf(String.valueOf(str)).concat("you will then enter a routine used to eliminate basic variables from equation 0. ");
/*  946 */     str = String.valueOf(String.valueOf(str)).concat("Finally, when you have finished this process, you may choose the 'Solve Interactively ");
/*  947 */     str = String.valueOf(String.valueOf(str)).concat("by the Simplex Method' procedure under the Procedure menu. After completing that procedure, you next may choose the \"Sensitivity Analysis\" ");
/*  948 */     str = String.valueOf(String.valueOf(str)).concat("procedure under the Procedure menu. If you wish to use the \"Sensitivity Analysis\" procedure for a problem with artificial variables, ");
/*  949 */     str = String.valueOf(String.valueOf(str)).concat("you should add the artificial variables and any slack variables LAST in this current procedure AFTER selecting the other necessary actions, ");
/*  950 */     str = String.valueOf(String.valueOf(str)).concat("and then choose the Big M method.");
/*  951 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*  952 */     this.help_content_procedure.setText(str);
/*  953 */     this.help_content_procedure.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void updateStepHelpPanel()
/*      */   {
/*  960 */     String str0 = "Set up for the Simplex Method - Interactive Only \n\n";
/*  961 */     str0 = String.valueOf(String.valueOf(str0)).concat("This procedure is used to implement the initialization step of the ");
/*  962 */     str0 = String.valueOf(String.valueOf(str0)).concat("Simplex Method as described in Secs. 4.2 and 4.6. The linear programming ");
/*  963 */     str0 = String.valueOf(String.valueOf(str0)).concat("model is augmented as needed to be able to apply the Simplex Method as ");
/*  964 */     str0 = String.valueOf(String.valueOf(str0)).concat("it is presented in Chapter 4. The converted model must have the following ");
/*  965 */     str0 = String.valueOf(String.valueOf(str0)).concat("properties:\n");
/*  966 */     str0 = String.valueOf(String.valueOf(str0)).concat("   1.  The objective needs to be to maximize Z or (-Z). \n");
/*  967 */     str0 = String.valueOf(String.valueOf(str0)).concat("   2.  Each functional constraint needs to be in equality form.\n");
/*  968 */     str0 = String.valueOf(String.valueOf(str0)).concat("   3.  Each functional constraint needs an initial basic variable.\n");
/*  969 */     str0 = String.valueOf(String.valueOf(str0)).concat("   4.  Each functional constraint needs a nonnegative right-hand side.\n");
/*  970 */     str0 = String.valueOf(String.valueOf(str0)).concat("   5.  Each variable needs to have a nonnegativity constraint.\n\n");
/*  971 */     str0 = String.valueOf(String.valueOf(str0)).concat("If you introduce artificial variables, you will have the choice of dealing ");
/*  972 */     str0 = String.valueOf(String.valueOf(str0)).concat("with them with either the Big M method or the Two-Phase method. To correct ");
/*  973 */     str0 = String.valueOf(String.valueOf(str0)).concat("a mistake, press the BACK button to backtrack.\n\n");
/*      */     
/*  975 */     String str1 = " ".concat(String.valueOf(String.valueOf(str0)));
/*  976 */     str1 = String.valueOf(String.valueOf(str1)).concat("Augmenting the model\n\n");
/*  977 */     str1 = String.valueOf(String.valueOf(str1)).concat("Use the mouse to select an equation or inequality that needs ");
/*  978 */     str1 = String.valueOf(String.valueOf(str1)).concat("to be modified. Then choose one or more of the four commands in turn to perform ");
/*  979 */     str1 = String.valueOf(String.valueOf(str1)).concat("the stated modification:\n");
/*  980 */     str1 = String.valueOf(String.valueOf(str1)).concat("          Add Slack Variable\n");
/*  981 */     str1 = String.valueOf(String.valueOf(str1)).concat("          Subtract Surplus Variable\n");
/*  982 */     str1 = String.valueOf(String.valueOf(str1)).concat("          Add Artificial Variable (represented by a Capital Letter)\n");
/*  983 */     str1 = String.valueOf(String.valueOf(str1)).concat("          Multiply Equation by (-1)\n");
/*  984 */     str1 = String.valueOf(String.valueOf(str1)).concat("The following commands are used to begin dealing with the variables allowed ");
/*  985 */     str1 = String.valueOf(String.valueOf(str1)).concat("to be negative:\n");
/*  986 */     str1 = String.valueOf(String.valueOf(str1)).concat("     1.   Convert One Variable to Difference of Two Variables\n");
/*  987 */     str1 = String.valueOf(String.valueOf(str1)).concat("     2.   Convert One Variable to Variable Plus Constant\n\n");
/*  988 */     str1 = String.valueOf(String.valueOf(str1)).concat("Select the first command for a variable with no bound on the negative values allowed. ");
/*  989 */     str1 = String.valueOf(String.valueOf(str1)).concat("Select the second command for a variable with a nonzero lower bound. Select Revert to Original Model to ");
/*  990 */     str1 = String.valueOf(String.valueOf(str1)).concat("restart to correct a mistake prior to the preceding step. Once you are finished ");
/*  991 */     str1 = String.valueOf(String.valueOf(str1)).concat("converting the model to augmented form, press the FINISH button.");
/*  992 */     str1 = String.valueOf(String.valueOf(str1)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/*  994 */     String str2 = " ".concat(String.valueOf(String.valueOf(str0)));
/*  995 */     str2 = String.valueOf(String.valueOf(str2)).concat("Selecting either the Two-Phase or the Big M method\n\n");
/*  996 */     str2 = String.valueOf(String.valueOf(str2)).concat("In the process of converting the linear programming model to augmented form, ");
/*  997 */     str2 = String.valueOf(String.valueOf(str2)).concat("you introduced artificial variables.\n");
/*  998 */     str2 = String.valueOf(String.valueOf(str2)).concat("To deal with these variables, you have a choice of the ");
/*  999 */     str2 = String.valueOf(String.valueOf(str2)).concat("Big M method or the Two-Phase method. Both methods are described in Sec. 4.6. ");
/* 1000 */     str2 = String.valueOf(String.valueOf(str2)).concat("\n\n\nPress the ENTER key to continue the current procedure. ");
/*      */     
/* 1002 */     String str4 = " ".concat(String.valueOf(String.valueOf(str0)));
/* 1003 */     str4 = String.valueOf(String.valueOf(str4)).concat("Selecting the objective and entering coefficients of the new objective function \n\n");
/* 1004 */     str4 = String.valueOf(String.valueOf(str4)).concat("In order to perform Phase 1 of the Two-Phase method, a new objective function ");
/* 1005 */     str4 = String.valueOf(String.valueOf(str4)).concat("for just this phase (as described in Sec. 4.6) needs to be entered. Select either ");
/* 1006 */     str4 = String.valueOf(String.valueOf(str4)).concat("Max or Min depending on whether you think the objective is to maximize or minimize ");
/* 1007 */     str4 = String.valueOf(String.valueOf(str4)).concat("this new objective function. A coefficient can be an integer or ");
/* 1008 */     str4 = String.valueOf(String.valueOf(str4)).concat("a decimal number. ");
/* 1009 */     str4 = String.valueOf(String.valueOf(str4)).concat("\n\n\nPress the ENTER key to continue the current procedure. ");
/*      */     
/* 1011 */     if ((this.artificialSelectionPanel == null) || (this.artificialSelectionPanel.isVisible() == false)) {
/* 1012 */       this.help_content_step.setText(str1);
/* 1013 */     } else if ((this.twoPhasePanel != null) && (this.twoPhasePanel.isVisible())) {
/* 1014 */       this.help_content_step.setText(str4);
/*      */     } else {
/* 1016 */       this.help_content_step.setText(str2);
/*      */     }
/* 1018 */     this.help_content_step.revalidate();
/* 1019 */     this.help_content_step.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void LoadFile(ObjectInputStream in)
/*      */   {
/*      */     try
/*      */     {
/* 1030 */       this.state = ((IORState)in.readObject());
/* 1031 */       currentPanel = ((Integer)in.readObject()).intValue();
/*      */     } catch (Exception e) {
/*      */       int currentPanel;
/* 1034 */       System.out.println("Load file fails"); return;
/*      */     }
/*      */     
/*      */     int currentPanel;
/* 1038 */     if (currentPanel == 1) {
/* 1039 */       updatePanel();
/* 1040 */       this.controller.setView(this);
/* 1041 */       setPanelEditable(true);
/*      */     }
/* 1043 */     if (currentPanel == 2) {
/* 1044 */       generateArtificialSelectionPanel();
/* 1045 */       hideTwoPhasePanel();
/* 1046 */       this.cb_method.setSelectedIndex(0);
/*      */     }
/* 1048 */     else if (currentPanel == 3) {
/* 1049 */       generateArtificialSelectionPanel();
/* 1050 */       showTwoPhasePanel();
/* 1051 */       this.cb_method.setSelectedIndex(1);
/*      */     }
/*      */     
/* 1054 */     if (this.bn_back.isVisible()) {
/* 1055 */       if ((this.state == null) || (this.state.isEmpty())) {
/* 1056 */         this.bn_back.setEnabled(false);
/*      */       } else {
/* 1058 */         this.bn_back.setEnabled(true);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void SaveFile(ObjectOutputStream out)
/*      */   {
/*      */     int currentPanel;
/*      */     
/*      */     int currentPanel;
/* 1069 */     if ((this.artificialSelectionPanel == null) || (this.artificialSelectionPanel.isVisible() == false)) {
/* 1070 */       currentPanel = 1; } else { int currentPanel;
/* 1071 */       if ((this.twoPhasePanel == null) || (this.twoPhasePanel.isVisible() == false)) {
/* 1072 */         currentPanel = 2;
/*      */       } else
/* 1074 */         currentPanel = 3;
/*      */     }
/*      */     try {
/* 1077 */       out.writeObject(this.state);
/* 1078 */       out.writeObject(new Integer(currentPanel));
/*      */     }
/*      */     catch (Exception e) {
/* 1081 */       System.out.println("Save file fails");
/* 1082 */       return;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPSetupPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */