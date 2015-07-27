/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Vector;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
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
/*     */ public class IORIPEnterPanel
/*     */   extends IORActionPanel
/*     */ {
/*  28 */   public IORIPProcessController controller = null;
/*     */   
/*     */   private int num_variable;
/*     */   
/*     */   private int num_binary;
/*     */   private int num_integer;
/*     */   private int num_continuous;
/*     */   private int num_constrain;
/*  36 */   private WholeNumberField wnf_num_binary = null;
/*  37 */   private WholeNumberField wnf_num_integer = null;
/*  38 */   private WholeNumberField wnf_num_continuous = null;
/*  39 */   private WholeNumberField wnf_num_constrain = null;
/*     */   
/*  41 */   private IORLPObjectiveFunction objective_function = null;
/*  42 */   private IORLinearEquation[] linear_equations = null;
/*     */   
/*  44 */   private JPanel mainPanel = null;
/*  45 */   private JPanel numPanel = new JPanel();
/*  46 */   private JPanel transtPanel = new JPanel();
/*  47 */   private JPanel interPanel = new JPanel();
/*  48 */   private JPanel inputPanel = null;
/*  49 */   private JPanel outputPanel = null;
/*  50 */   private JPanel boundPanel = null;
/*  51 */   private JLabel comtlabel = new JLabel("(To make a new number above take effect, you must press the ENTER key.)");
/*     */   
/*     */ 
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */ 
/*     */ 
/*     */   private int getMaxNumBinary()
/*     */   {
/*  61 */     return this.controller.data.max_num_binary;
/*     */   }
/*     */   
/*     */ 
/*     */   private int getMaxNumInteger()
/*     */   {
/*  67 */     return this.controller.data.max_num_integer;
/*     */   }
/*     */   
/*     */ 
/*     */   private int getMaxNumContinuous()
/*     */   {
/*  73 */     return this.controller.data.max_num_continuous;
/*     */   }
/*     */   
/*     */ 
/*     */   private int getMaxNumConstrain()
/*     */   {
/*  79 */     return this.controller.data.max_num_constrain;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void setParameter()
/*     */   {
/*  90 */     if (this.wnf_num_constrain.getValue() > getMaxNumConstrain()) {
/*  91 */       this.wnf_num_constrain.setValue(getMaxNumConstrain());
/*     */     }
/*  93 */     if (this.wnf_num_binary.getValue() + this.wnf_num_integer.getValue() + this.wnf_num_continuous.getValue() > 5) {
/*  94 */       this.wnf_num_binary.setValue(this.num_binary);
/*  95 */       this.wnf_num_integer.setValue(this.num_integer);
/*  96 */       this.wnf_num_continuous.setValue(this.num_continuous);
/*     */     }
/*  98 */     this.num_binary = this.wnf_num_binary.getValue();
/*  99 */     this.num_integer = this.wnf_num_integer.getValue();
/* 100 */     this.num_continuous = this.wnf_num_continuous.getValue();
/*     */     
/* 102 */     this.num_variable = (this.num_binary + this.num_integer + this.num_continuous);
/* 103 */     if (this.num_variable > 0)
/*     */     {
/* 105 */       this.objective_function.setNumVariable(this.num_variable);
/* 106 */       if (!this.outputPanel.isAncestorOf(this.objective_function)) {
/* 107 */         this.outputPanel.add(this.objective_function);
/* 108 */         this.outputPanel.add(Box.createRigidArea(new Dimension(20, 20)));
/*     */       }
/*     */       
/* 111 */       for (int i = 0; i < this.num_constrain; i++) {
/* 112 */         this.outputPanel.remove(this.linear_equations[i]);
/*     */       }
/* 114 */       this.linear_equations = null;
/* 115 */       this.num_constrain = this.wnf_num_constrain.getValue();
/* 116 */       this.linear_equations = new IORLinearEquation[this.num_constrain];
/* 117 */       for (int i = 0; i < this.num_constrain; i++) {
/* 118 */         this.linear_equations[i] = new IORLinearEquation(this.num_variable, this.controller.data.constrain_operator[(i + 1)], this.controller.data.constrain_coefficient[(i + 1)]);
/* 119 */         this.outputPanel.add(this.linear_equations[i]);
/*     */       }
/* 121 */       String char_string = "";
/*     */       
/* 123 */       for (int i = 1; i <= this.num_binary; i++) {
/* 124 */         char_string = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(char_string))).append("x").append(i).append(" = (0 ,1),  ")));
/*     */       }
/* 126 */       for (int i = this.num_binary + 1; i <= this.num_integer + this.num_binary; i++) {
/* 127 */         char_string = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(char_string))).append("x").append(i).append(" = (0, 1, ...),  ")));
/*     */       }
/* 129 */       for (int i = this.num_binary + this.num_integer + 1; i <= this.num_continuous + this.num_integer + this.num_binary; i++) {
/* 130 */         char_string = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(char_string))).append("x").append(i).append(" >=").append(" 0,  ")));
/*     */       }
/* 132 */       char_string = char_string.substring(0, char_string.length() - 3);
/*     */       
/* 134 */       this.boundPanel.removeAll();
/* 135 */       this.boundPanel.add(new JLabel(char_string));
/*     */     }
/*     */     else {
/* 138 */       this.outputPanel.removeAll();
/* 139 */       this.boundPanel.removeAll();
/*     */     }
/*     */     
/* 142 */     revalidate();
/* 143 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updatePanel() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finishProcedure()
/*     */   {
/* 156 */     Vector p = new Vector();
/*     */     
/*     */ 
/* 159 */     p.addElement(new Integer(this.num_binary));
/* 160 */     p.addElement(new Integer(this.num_integer));
/* 161 */     p.addElement(new Integer(this.num_continuous));
/* 162 */     p.addElement(new Integer(this.num_constrain));
/* 163 */     p.addElement(this.objective_function.isMax());
/* 164 */     double[] c = new double[this.num_variable];
/* 165 */     this.objective_function.getCoefficient(c);
/* 166 */     p.addElement(c);
/* 167 */     double[][] c2 = new double[this.num_constrain][this.num_variable + 1];
/* 168 */     for (int i = 0; i < this.num_constrain; i++) {
/* 169 */       this.linear_equations[i].getCoefficient(c2[i]);
/*     */     }
/* 171 */     p.addElement(c2);
/* 172 */     int[] op = new int[this.num_constrain];
/* 173 */     for (int i = 0; i < this.num_constrain; i++) {
/* 174 */       op[i] = this.linear_equations[i].getOperator();
/*     */     }
/* 176 */     p.addElement(op);
/*     */     
/* 178 */     IOROperation operation = new IOROperation(40101, p);
/*     */     
/*     */ 
/* 181 */     this.controller.solver.doWork(operation);
/*     */     
/* 183 */     if ((this.num_integer == 0) && (this.num_continuous == 0)) {
/* 184 */       this.controller.setMenuState(2);
/*     */     } else
/* 186 */       this.controller.setMenuState(3);
/* 187 */     this.actionStatus.setText("The current procedure is finished. Please go to the Procedure menu to continue!");
/* 188 */     setFinishEnabled(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORIPEnterPanel(IORIPProcessController c)
/*     */   {
/* 198 */     super(c);
/* 199 */     this.controller = c;
/*     */     
/* 201 */     this.mainPanel = new JPanel();
/* 202 */     add(this.mainPanel);
/*     */     
/* 204 */     this.comtlabel.setAlignmentX(0.5F);
/*     */     
/*     */ 
/* 207 */     this.num_binary = this.controller.data.NumBinaryVariables;
/* 208 */     IORWholeNumberPanel wnp_num_binary = new IORWholeNumberPanel(this.num_binary, 2, "Number of binary (0-1) variables:         ");
/*     */     
/*     */ 
/* 211 */     this.wnf_num_binary = wnp_num_binary.getWholeNumberField();
/* 212 */     this.wnf_num_binary.addActionListener(new ActionListener() {
/* 213 */       public void actionPerformed(ActionEvent e) { IORIPEnterPanel.this.setParameter();
/*     */       }
/* 215 */     });
/* 216 */     this.num_integer = this.controller.data.NumIntegerVariables;
/* 217 */     IORWholeNumberPanel wnp_num_integer = new IORWholeNumberPanel(this.num_integer, 2, "Number of general integer variables:  ");
/*     */     
/*     */ 
/* 220 */     this.wnf_num_integer = wnp_num_integer.getWholeNumberField();
/* 221 */     this.wnf_num_integer.addActionListener(new ActionListener() {
/* 222 */       public void actionPerformed(ActionEvent e) { IORIPEnterPanel.this.setParameter();
/*     */       }
/* 224 */     });
/* 225 */     this.num_continuous = this.controller.data.NumRealVariables;
/* 226 */     IORWholeNumberPanel wnp_num_continuous = new IORWholeNumberPanel(this.num_continuous, 2, "Number of continuous variables:         ");
/*     */     
/*     */ 
/* 229 */     this.wnf_num_continuous = wnp_num_continuous.getWholeNumberField();
/* 230 */     this.wnf_num_continuous.addActionListener(new ActionListener() {
/* 231 */       public void actionPerformed(ActionEvent e) { IORIPEnterPanel.this.setParameter();
/*     */       }
/* 233 */     });
/* 234 */     this.num_constrain = this.controller.data.NumConstraints;
/* 235 */     IORWholeNumberPanel wnp_num_constrain = new IORWholeNumberPanel(this.num_constrain, 2, "Number of functional constraints:       ");
/*     */     
/*     */ 
/* 238 */     this.wnf_num_constrain = wnp_num_constrain.getWholeNumberField();
/* 239 */     this.wnf_num_constrain.addActionListener(new ActionListener() {
/* 240 */       public void actionPerformed(ActionEvent e) { IORIPEnterPanel.this.setParameter();
/*     */       }
/* 242 */     });
/* 243 */     this.num_variable = (this.num_binary + this.num_integer + this.num_continuous);
/* 244 */     this.objective_function = new IORLPObjectiveFunction(this.num_variable, this.controller.data.objective_is_max, this.controller.data.objective_coefficient);
/*     */     
/*     */ 
/* 247 */     JPanel inputPanel = new JPanel();
/* 248 */     inputPanel.setLayout(new GridLayout(4, 1));
/* 249 */     inputPanel.add(wnp_num_binary);
/* 250 */     inputPanel.add(wnp_num_integer);
/* 251 */     inputPanel.add(wnp_num_continuous);
/* 252 */     inputPanel.add(wnp_num_constrain);
/*     */     
/* 254 */     this.outputPanel = new JPanel();
/* 255 */     this.outputPanel.setLayout(new BoxLayout(this.outputPanel, 1));
/* 256 */     this.outputPanel.add(this.objective_function);
/* 257 */     this.outputPanel.add(Box.createRigidArea(new Dimension(20, 20)));
/*     */     
/* 259 */     this.linear_equations = new IORLinearEquation[this.num_constrain];
/* 260 */     for (int i = 0; i < this.num_constrain; i++) {
/* 261 */       this.linear_equations[i] = new IORLinearEquation(this.num_variable, this.controller.data.constrain_operator[(i + 1)], this.controller.data.constrain_coefficient[(i + 1)]);
/* 262 */       this.outputPanel.add(this.linear_equations[i]);
/*     */     }
/*     */     
/* 265 */     String char_string = "";
/* 266 */     for (int i = 1; i <= this.num_binary; i++) {
/* 267 */       char_string = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(char_string))).append("X").append(i).append(" = (0 ,1),  ")));
/*     */     }
/* 269 */     for (int i = this.num_binary + 1; i <= this.num_integer + this.num_binary; i++) {
/* 270 */       char_string = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(char_string))).append("X").append(i).append(" = (0, 1, ...),  ")));
/*     */     }
/* 272 */     for (int i = this.num_binary + this.num_integer + 1; i <= this.num_continuous + this.num_integer + this.num_binary; i++) {
/* 273 */       char_string = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(char_string))).append("X").append(i).append(" >=").append(" 0,  ")));
/*     */     }
/* 275 */     char_string = char_string.substring(0, char_string.length() - 3);
/* 276 */     this.boundPanel = new JPanel();
/* 277 */     this.boundPanel.add(new JLabel(char_string));
/*     */     
/* 279 */     this.interPanel.setLayout(new BoxLayout(this.interPanel, 1));
/* 280 */     this.interPanel.add(this.outputPanel);
/* 281 */     this.interPanel.add(Box.createVerticalStrut(20));
/* 282 */     this.interPanel.add(this.boundPanel);
/*     */     
/* 284 */     this.transtPanel.setLayout(new BorderLayout());
/* 285 */     this.transtPanel.add(this.interPanel, "North");
/*     */     
/* 287 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/* 288 */     this.mainPanel.add(inputPanel);
/* 289 */     this.mainPanel.add(Box.createVerticalStrut(10));
/* 290 */     this.mainPanel.add(this.comtlabel);
/* 291 */     this.mainPanel.add(Box.createVerticalStrut(30));
/* 292 */     this.mainPanel.add(this.transtPanel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setFinishEnabled(boolean b)
/*     */   {
/* 301 */     this.bn_finish.setEnabled(b);
/* 302 */     if (b) {
/* 303 */       this.actionStatus.setText("Note: The total number of variables of all types cannot exceed 5!");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 310 */     String str = "Enter or Revise an Integer Programming Model\n\n";
/* 311 */     str = String.valueOf(String.valueOf(str)).concat("Enter the integer programming model in its original form (i.e., ");
/* 312 */     str = String.valueOf(String.valueOf(str)).concat("without slack variables, artificial variables, etc.). When you are finished ");
/* 313 */     str = String.valueOf(String.valueOf(str)).concat("entering or revising the model, there are two interactive solution ");
/* 314 */     str = String.valueOf(String.valueOf(str)).concat("techniques which are potentially available to you under the Procedure menu. If ");
/* 315 */     str = String.valueOf(String.valueOf(str)).concat("the model has only binary variables, then the BIP Branch-and-Bound algorithm will ");
/* 316 */     str = String.valueOf(String.valueOf(str)).concat("be available. Otherwise, the MIP Branch-and-Bound algorithm will be available. ");
/* 317 */     str = String.valueOf(String.valueOf(str)).concat("If an option ");
/* 318 */     str = String.valueOf(String.valueOf(str)).concat("is not available, it will be dimmed under the Procedure menu.\n\n");
/* 319 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of binary variables \n\n");
/* 320 */     str = String.valueOf(String.valueOf(str)).concat("Enter the total number of variables which are restricted to be binary (0-1) in ");
/* 321 */     str = String.valueOf(String.valueOf(str)).concat("the original model (not including slack variables, artificial variables, etc.). ");
/* 322 */     str = String.valueOf(String.valueOf(str)).concat("Note that the maximum sizes allowed for the two ");
/* 323 */     str = String.valueOf(String.valueOf(str)).concat("solution techniques are as follows:\n\n");
/* 324 */     str = String.valueOf(String.valueOf(str)).concat("    1.   Interactive BIP: A maximum of 5 binary variables are allowed,\n");
/* 325 */     str = String.valueOf(String.valueOf(str)).concat("    2.   Interactive MIP: A maximum of 5 total variables are allowed.\n\n");
/* 326 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of general integer variables \n\n");
/* 327 */     str = String.valueOf(String.valueOf(str)).concat("Enter the total number of variables which are restricted to be integer (0,1,...) but not ");
/* 328 */     str = String.valueOf(String.valueOf(str)).concat("just binary in the original model (not including slack variables, artificial variables, ");
/* 329 */     str = String.valueOf(String.valueOf(str)).concat("etc.). Note that the maximum sizes allowed for the two solution techniques are as follows:\n\n");
/* 330 */     str = String.valueOf(String.valueOf(str)).concat("    1.   Interactive BIP: 0 general integer variables are allowed,\n");
/* 331 */     str = String.valueOf(String.valueOf(str)).concat("    2.   Interactive MIP: A maximum of 5 total variables are allowed.\n\n");
/* 332 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of functional constraints\n\n");
/* 333 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of functional constraints in the original model (not including nonnegativity ");
/* 334 */     str = String.valueOf(String.valueOf(str)).concat("constraints). Note that the maximum size allowed for both solution techniques is 5.\n\n");
/* 335 */     str = String.valueOf(String.valueOf(str)).concat("Press the ENTER key to make the new numbers above take effect.\n\n");
/* 336 */     str = String.valueOf(String.valueOf(str)).concat("Selecting the objective\n\n");
/* 337 */     str = String.valueOf(String.valueOf(str)).concat("Select either Max or Min, depending on whether the objective is to maximize or minimize Z.\n\n");
/* 338 */     str = String.valueOf(String.valueOf(str)).concat("Entering the objective function\n\n");
/* 339 */     str = String.valueOf(String.valueOf(str)).concat("Enter the coefficient of each variable in the objective function. Each number entered may be ");
/* 340 */     str = String.valueOf(String.valueOf(str)).concat("an integer or a decimal number. If a cost of Big M is desired, instead enter a ");
/* 341 */     str = String.valueOf(String.valueOf(str)).concat("large number (M is not allowed).\n\n");
/* 342 */     str = String.valueOf(String.valueOf(str)).concat("Selecting the type of functional constraint\n\n");
/* 343 */     str = String.valueOf(String.valueOf(str)).concat("Select <=, =, or >= , depending on the form of constraint.");
/* 344 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 345 */     this.help_content_step.setText(str);
/* 346 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 353 */     String str = "Enter or Revise an Integer Programming Model\n\n";
/* 354 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to quickly enter (or revise) an integer programming ");
/* 355 */     str = String.valueOf(String.valueOf(str)).concat("model (either pure or mixed, with either binary or general integer variables) ");
/* 356 */     str = String.valueOf(String.valueOf(str)).concat("in preparation for solving it by the branch-and-bound algorithm for pure binary ");
/* 357 */     str = String.valueOf(String.valueOf(str)).concat("programming (BIP) or general mixed integer programming (MIP) presented in ");
/* 358 */     str = String.valueOf(String.valueOf(str)).concat("Sections 12.6 and 12.7, respectively.\n\n");
/* 359 */     str = String.valueOf(String.valueOf(str)).concat("The maximum sizes allowed depend on how you later wish to solve the model. ");
/* 360 */     str = String.valueOf(String.valueOf(str)).concat("These limits on the number of variables and functional constraints are outlined below:\n\n");
/* 361 */     str = String.valueOf(String.valueOf(str)).concat("           Interactive BIP Branch-and-Bound: 5 binary variables, 5 constraints\n");
/* 362 */     str = String.valueOf(String.valueOf(str)).concat("           Interactive MIP Branch-and-Bound: 5 total variables, 5 constraints\n\n");
/* 363 */     str = String.valueOf(String.valueOf(str)).concat("If you simply wish to revise the model you entered last (or one you saved and just ");
/* 364 */     str = String.valueOf(String.valueOf(str)).concat("recalled with the Save and Open commands under the File menu), you do not need to ");
/* 365 */     str = String.valueOf(String.valueOf(str)).concat("reenter the entire model. Instead, just reenter the new numbers.\n\n");
/* 366 */     str = String.valueOf(String.valueOf(str)).concat("When you finish entering the model, return to the Procedure menu to choose the appropriate ");
/* 367 */     str = String.valueOf(String.valueOf(str)).concat("procedure from the two listed above.");
/* 368 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 369 */     this.help_content_procedure.setText(str);
/* 370 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveEnterProblem()
/*     */   {
/* 378 */     Vector p = new Vector();
/*     */     
/*     */ 
/* 381 */     p.addElement(new Integer(this.num_binary));
/* 382 */     p.addElement(new Integer(this.num_integer));
/* 383 */     p.addElement(new Integer(this.num_continuous));
/* 384 */     p.addElement(new Integer(this.num_constrain));
/* 385 */     p.addElement(this.objective_function.isMax());
/* 386 */     double[] c = new double[this.num_variable];
/* 387 */     this.objective_function.getCoefficient(c);
/* 388 */     p.addElement(c);
/* 389 */     double[][] c2 = new double[this.num_constrain][this.num_variable + 1];
/* 390 */     for (int i = 0; i < this.num_constrain; i++) {
/* 391 */       this.linear_equations[i].getCoefficient(c2[i]);
/*     */     }
/* 393 */     p.addElement(c2);
/* 394 */     int[] op = new int[this.num_constrain];
/* 395 */     for (int i = 0; i < this.num_constrain; i++) {
/* 396 */       op[i] = this.linear_equations[i].getOperator();
/*     */     }
/* 398 */     p.addElement(op);
/*     */     
/* 400 */     IOROperation operation = new IOROperation(40101, p);
/*     */     
/*     */ 
/* 403 */     this.controller.solver.doWork(operation);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORIPEnterPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */