/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
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
/*     */ public class IORLPEnterPanel
/*     */   extends IORLPActionPanel
/*     */ {
/*     */   private int num_variable;
/*     */   private int num_constrain;
/*  30 */   private WholeNumberField wnf_num_variable = null;
/*  31 */   private WholeNumberField wnf_num_constrain = null;
/*  32 */   private IORLPObjectiveFunction objective_function = null;
/*  33 */   private IORLinearEquation[] linear_equations = null;
/*  34 */   private IORBound bound = null;
/*     */   
/*  36 */   private JPanel mainPanel = null;
/*  37 */   private JPanel outputPanel = null;
/*     */   
/*  39 */   private Component bound_separator = null;
/*  40 */   private JPanel bound_prompt_panel = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */ 
/*     */ 
/*     */   private int getMaxNumVariable()
/*     */   {
/*  51 */     return this.controller.data.max_num_variable;
/*     */   }
/*     */   
/*     */ 
/*     */   private int getMaxNumConstrain()
/*     */   {
/*  57 */     return this.controller.data.max_num_constrain;
/*     */   }
/*     */   
/*     */   private void setNumVariable()
/*     */   {
/*  62 */     int max = getMaxNumVariable();
/*  63 */     if (this.wnf_num_variable.getValue() > max) {
/*  64 */       this.wnf_num_variable.setValue(max);
/*     */     }
/*     */     
/*  67 */     int last = this.num_variable;
/*     */     
/*  69 */     if (this.wnf_num_variable.getValue() <= max) {
/*  70 */       this.num_variable = this.wnf_num_variable.getValue();
/*     */     }
/*  72 */     if (last != this.num_variable) {
/*  73 */       this.wnf_num_variable.setValue(this.num_variable);
/*  74 */       this.objective_function.setNumVariable(this.num_variable);
/*  75 */       for (int i = 0; i < this.num_constrain; i++) {
/*  76 */         this.linear_equations[i].setNumVariable(this.num_variable);
/*     */       }
/*  78 */       this.bound.setNumVariable(this.num_variable);
/*  79 */       revalidate();
/*  80 */       repaint();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void setNumConstrain()
/*     */   {
/*  87 */     int max = getMaxNumConstrain();
/*  88 */     if (this.wnf_num_constrain.getValue() > max) {
/*  89 */       this.wnf_num_constrain.setValue(max);
/*     */     }
/*     */     
/*  92 */     int last = this.num_constrain;
/*  93 */     if (this.wnf_num_constrain.getValue() <= max) {
/*  94 */       this.num_constrain = this.wnf_num_constrain.getValue();
/*     */     }
/*  96 */     if (last != this.num_constrain) {
/*  97 */       this.wnf_num_constrain.setValue(this.num_constrain);
/*  98 */       if (last > this.num_constrain) {
/*  99 */         this.outputPanel.remove(this.bound_separator);
/* 100 */         this.outputPanel.remove(this.bound_prompt_panel);
/* 101 */         this.outputPanel.remove(this.bound);
/* 102 */         for (int i = this.num_constrain; i < last; i++) {
/* 103 */           this.outputPanel.remove(this.linear_equations[i]);
/* 104 */           this.linear_equations[i] = null;
/*     */         }
/*     */       }
/*     */       
/* 108 */       for (int i = last; i < this.num_constrain; i++) {
/* 109 */         this.linear_equations[i] = new IORLinearEquation(this.num_variable);
/* 110 */         this.outputPanel.add(this.linear_equations[i]);
/*     */       }
/*     */       
/*     */ 
/* 114 */       this.outputPanel.add(this.bound_separator);
/* 115 */       this.outputPanel.add(this.bound_prompt_panel);
/* 116 */       this.outputPanel.add(this.bound);
/* 117 */       revalidate();
/* 118 */       repaint();
/*     */     }
/*     */   }
/*     */   
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
/* 133 */     Vector p = new Vector();
/*     */     
/* 135 */     p.addElement(new Integer(this.num_variable));
/*     */     
/* 137 */     p.addElement(new Integer(this.num_constrain));
/*     */     
/* 139 */     p.addElement(this.objective_function.isMax());
/*     */     
/* 141 */     double[] c = new double[this.num_variable];
/* 142 */     this.objective_function.getCoefficient(c);
/* 143 */     p.addElement(c);
/*     */     
/* 145 */     double[][] c2 = new double[this.num_constrain][this.num_variable + 1];
/* 146 */     for (int i = 0; i < this.num_constrain; i++) {
/* 147 */       this.linear_equations[i].getCoefficient(c2[i]);
/*     */     }
/* 149 */     p.addElement(c2);
/*     */     
/*     */ 
/* 152 */     int[] op = new int[this.num_constrain];
/* 153 */     for (int i = 0; i < this.num_constrain; i++) {
/* 154 */       op[i] = this.linear_equations[i].getOperator();
/*     */     }
/* 156 */     p.addElement(op);
/*     */     
/*     */ 
/* 159 */     boolean[] b = new boolean[this.num_variable];
/*     */     
/* 161 */     double[] rhs = new double[this.num_variable];
/* 162 */     this.bound.getBound(b, rhs);
/* 163 */     p.addElement(b);
/* 164 */     p.addElement(rhs);
/*     */     
/* 166 */     IOROperation operation = new IOROperation(10101, p);
/*     */     
/*     */ 
/* 169 */     this.controller.solver.doWork(operation);
/* 170 */     this.controller.setMenuState(10);
/* 171 */     this.actionStatus.setText("The current procedure is finished. Please go to the Procedure menu to continue!");
/*     */     
/* 173 */     setPanelEditable(false);
/* 174 */     setFinishEnabled(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPanelEditable(boolean b)
/*     */   {
/* 182 */     this.wnf_num_constrain.setEditable(b);
/* 183 */     this.wnf_num_variable.setEditable(b);
/* 184 */     this.bn_back.setEnabled(b);
/*     */     
/* 186 */     this.objective_function.setEditable(b);
/* 187 */     for (int i = 0; i < this.num_constrain; i++) {
/* 188 */       this.linear_equations[i].setEditable(b);
/*     */     }
/* 190 */     this.bound.setEditable(b);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORLPEnterPanel(IORLPProcessController c)
/*     */   {
/* 201 */     super(c);
/* 202 */     c.solver.doWork(new IOROperation(10102, null), c.data);
/*     */     
/* 204 */     this.bn_back.setVisible(false);
/*     */     
/* 206 */     this.mainPanel = new JPanel();
/* 207 */     add(this.mainPanel, "Center");
/*     */     
/* 209 */     this.num_variable = this.controller.data.num_variable;
/* 210 */     IORWholeNumberPanel wnp_num_variable = new IORWholeNumberPanel(this.num_variable, 2, "Number of Decision Variables (<= 6):        ");
/*     */     
/*     */ 
/* 213 */     this.wnf_num_variable = wnp_num_variable.getWholeNumberField();
/* 214 */     this.wnf_num_variable.addActionListener(new ActionListener() {
/* 215 */       public void actionPerformed(ActionEvent e) { IORLPEnterPanel.this.setNumVariable(); }
/* 216 */     });
/* 217 */     this.num_constrain = this.controller.data.num_constrain;
/* 218 */     IORWholeNumberPanel wnp_num_constrain = new IORWholeNumberPanel(this.num_constrain, 2, "Number of Functional Constraints (<= 6): ");
/*     */     
/*     */ 
/* 221 */     this.wnf_num_constrain = wnp_num_constrain.getWholeNumberField();
/* 222 */     this.wnf_num_constrain.addActionListener(new ActionListener() {
/* 223 */       public void actionPerformed(ActionEvent e) { IORLPEnterPanel.this.setNumConstrain(); }
/* 224 */     });
/* 225 */     this.objective_function = new IORLPObjectiveFunction(this.num_variable, this.controller.data.objective_is_max, this.controller.data.objective_coefficient);
/*     */     
/*     */ 
/* 228 */     JPanel inputPanel = new JPanel();
/* 229 */     inputPanel.setLayout(new BoxLayout(inputPanel, 1));
/* 230 */     inputPanel.add(wnp_num_variable);
/* 231 */     inputPanel.add(wnp_num_constrain);
/*     */     
/* 233 */     JLabel lb1 = new JLabel("(To make a new number above take effect, you must press the ENTER key.)");
/*     */     
/* 235 */     inputPanel.add(lb1);
/*     */     
/*     */ 
/*     */ 
/* 239 */     this.outputPanel = new JPanel();
/* 240 */     this.outputPanel.setLayout(new BoxLayout(this.outputPanel, 1));
/*     */     
/* 242 */     this.outputPanel.add(this.objective_function);
/*     */     
/*     */ 
/* 245 */     JLabel lb2 = new JLabel("Subject to:");
/* 246 */     JPanel p2 = new JPanel(new GridLayout(1, 3));
/* 247 */     p2.add(new JLabel());
/* 248 */     p2.add(lb2);
/* 249 */     p2.add(new JLabel());
/* 250 */     this.outputPanel.add(p2);
/*     */     
/*     */ 
/* 253 */     this.linear_equations = new IORLinearEquation[getMaxNumConstrain()];
/*     */     
/* 255 */     for (int i = 0; i < this.num_constrain; i++) {
/* 256 */       this.linear_equations[i] = new IORLinearEquation(this.num_variable, this.controller.data.constrain_operator[(i + 1)], this.controller.data.constrain_coefficient[(i + 1)]);
/*     */       
/* 258 */       this.outputPanel.add(this.linear_equations[i]);
/*     */     }
/*     */     
/* 261 */     this.bound_separator = Box.createRigidArea(new Dimension(15, 10));
/* 262 */     this.outputPanel.add(this.bound_separator);
/* 263 */     JLabel lb3 = new JLabel("And");
/* 264 */     this.bound_prompt_panel = new JPanel(new GridLayout(1, 3));
/* 265 */     this.bound_prompt_panel.add(new JLabel());
/* 266 */     this.bound_prompt_panel.add(lb3);
/* 267 */     this.bound_prompt_panel.add(new JLabel());
/* 268 */     this.outputPanel.add(this.bound_prompt_panel);
/*     */     
/* 270 */     this.bound = new IORBound(this.num_variable, this.controller.data.bound_operator, this.controller.data.bound);
/*     */     
/* 272 */     this.outputPanel.add(this.bound);
/*     */     
/* 274 */     JPanel p = new JPanel();
/* 275 */     p.setLayout(new BoxLayout(p, 1));
/* 276 */     p.add(inputPanel);
/*     */     
/* 278 */     p.add(this.outputPanel);
/*     */     
/* 280 */     this.mainPanel.setLayout(new BorderLayout());
/* 281 */     this.mainPanel.add(p, "North");
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
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 306 */     String str = "Enter or Revise a General Linear Programming Model\n\n";
/* 307 */     str = String.valueOf(String.valueOf(str)).concat("Enter the linear programming model in its original form (i.e., without slack ");
/* 308 */     str = String.valueOf(String.valueOf(str)).concat("variables, artificial variables, etc.). Any of the legitimate forms described ");
/* 309 */     str = String.valueOf(String.valueOf(str)).concat("in Sec 3.2 can be used. When you finish entering or revising the ");
/* 310 */     str = String.valueOf(String.valueOf(str)).concat("model, there are two techniques available for solving the model ");
/* 311 */     str = String.valueOf(String.valueOf(str)).concat("under the Procedure menu, ");
/* 312 */     str = String.valueOf(String.valueOf(str)).concat("Automatic Interior Point and Interactive Simplex Method ");
/* 313 */     str = String.valueOf(String.valueOf(str)).concat("(for the Interactive Simplex Method procedure, you must first choose \"Set up for the Simplex Method");
/* 314 */     str = String.valueOf(String.valueOf(str)).concat(" - Interactive Only\").\n\n");
/* 315 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of decision variables\n\n");
/* 316 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of decision variables in the original model (not including slack variables, ");
/* 317 */     str = String.valueOf(String.valueOf(str)).concat("artificial variables, etc.), and then press the ENTER key. Note that the maximum size allowed for ");
/* 318 */     str = String.valueOf(String.valueOf(str)).concat("the number of decision variables is 6. In subsequent procedures under the Procedure menu, you will be ");
/* 319 */     str = String.valueOf(String.valueOf(str)).concat("further limited to 10 variables after adding slack variables, ");
/* 320 */     str = String.valueOf(String.valueOf(str)).concat("surplus variables, etc. (If you will be using Sensitivity Analysis procedure and will consider ");
/* 321 */     str = String.valueOf(String.valueOf(str)).concat("the case where a new variable is introduced into the model, you should include the new variable now ");
/* 322 */     str = String.valueOf(String.valueOf(str)).concat("with all its coefficients set equal to zero.)\n\n");
/* 323 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of functional constraints\n\n");
/* 324 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of functional constraints in the original model, and then press the ENTER key. Note that the maximum size allowed for ");
/* 325 */     str = String.valueOf(String.valueOf(str)).concat("the number of functional constraints is 6. (If you will be using the Sensitivity Analysis procedure and will consider ");
/* 326 */     str = String.valueOf(String.valueOf(str)).concat("the case where a new variable is introduced into the model, you should include an extra constraint now ");
/* 327 */     str = String.valueOf(String.valueOf(str)).concat("with all its coefficients and right-hand side set equal to zero.)\n\n");
/* 328 */     str = String.valueOf(String.valueOf(str)).concat("Enter the objective function\n\n");
/* 329 */     str = String.valueOf(String.valueOf(str)).concat("       Select either Max or Min depending on whether the objective is to maximize or minimize Z.\n");
/* 330 */     str = String.valueOf(String.valueOf(str)).concat("       Enter the coefficients of the variables in the objective function. Enter either integers or decimal numbers.\n\n");
/* 331 */     str = String.valueOf(String.valueOf(str)).concat("Enter functional constraints\n\n");
/* 332 */     str = String.valueOf(String.valueOf(str)).concat("For each functional constraint\n");
/* 333 */     str = String.valueOf(String.valueOf(str)).concat("       Enter the coefficients of the variables in the functional constraint. Enter either integers or decimal numbers.\n");
/* 334 */     str = String.valueOf(String.valueOf(str)).concat("       Select  <=,  >=, or  =, depending on the type of the constraint.\n");
/* 335 */     str = String.valueOf(String.valueOf(str)).concat("       Enter the value of the right-hand side in the functional constraint. Enter either an integer or a decimal number.\n\n");
/* 336 */     str = String.valueOf(String.valueOf(str)).concat("Enter the lower bounds of the decision variables\n\n");
/* 337 */     str = String.valueOf(String.valueOf(str)).concat("The procedure for entering the model assumes that all of the variables have nonnegativity constraints and no other lower-bound constraints. ");
/* 338 */     str = String.valueOf(String.valueOf(str)).concat("If this assumption is correct, you need to do nothing more and can go on with other entries of the current procedure. However, if a variable has a lower bound other ");
/* 339 */     str = String.valueOf(String.valueOf(str)).concat("than zero, then enter the lower bound. In this case, the entry should be either an integer or a decimal number. If the variable is unbounded ");
/* 340 */     str = String.valueOf(String.valueOf(str)).concat("below, then select the operator, \"n/a\" (bound not available.)\n\n");
/* 341 */     str = String.valueOf(String.valueOf(str)).concat("\nPress the ENTER key to continue the current procedure.");
/*     */     
/*     */ 
/* 344 */     this.help_content_step.setText(str);
/* 345 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 353 */     String str = "Enter or Revise a General Linear Programming Model\n\n";
/* 354 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to quickly enter a linear programming model, ");
/* 355 */     str = String.valueOf(String.valueOf(str)).concat("or to revise a previously entered model.\n\n");
/* 356 */     str = String.valueOf(String.valueOf(str)).concat("The model is entered in its original form as described in Section 3.2, ");
/* 357 */     str = String.valueOf(String.valueOf(str)).concat("without slack variables, etc. The maximum size allowed (counting just ");
/* 358 */     str = String.valueOf(String.valueOf(str)).concat("decision variables and functional constraints) is 6 variables and 6 constraints.\n\n");
/* 359 */     str = String.valueOf(String.valueOf(str)).concat("Detailed computer instructions are available (Help menu).\n\n");
/* 360 */     str = String.valueOf(String.valueOf(str)).concat("Press the ENTER key to complete the entering of the number of variables and the number of constraints.");
/* 361 */     str = String.valueOf(String.valueOf(str)).concat(" (It's not necessary to press the ENTER key when editing the other fields.)\n\n");
/* 362 */     str = String.valueOf(String.valueOf(str)).concat("If you simply wish to revise the model you entered last (or one you saved and ");
/* 363 */     str = String.valueOf(String.valueOf(str)).concat("just recalled with the Save and Open commands under the File menu), you do not ");
/* 364 */     str = String.valueOf(String.valueOf(str)).concat("need to reenter the entire model. Instead, locate the entry by the mouse where you want to revise ");
/* 365 */     str = String.valueOf(String.valueOf(str)).concat("and then do so.\n\n");
/* 366 */     str = String.valueOf(String.valueOf(str)).concat("When you finish entering the model, press the FINISH button, and then return to the Procedure menu. ");
/* 367 */     str = String.valueOf(String.valueOf(str)).concat("If you wish to solve the model interactively by ");
/* 368 */     str = String.valueOf(String.valueOf(str)).concat("the Simplex Method, first choose the \"Set Up for the Simplex Method - Interactive Only\" procedure");
/* 369 */     str = String.valueOf(String.valueOf(str)).concat(" (to introduce slack variables, etc.). ");
/* 370 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 372 */     this.help_content_procedure.setText(str);
/* 373 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveEnterProblem()
/*     */   {
/* 382 */     Vector p = new Vector();
/*     */     
/* 384 */     p.addElement(new Integer(this.num_variable));
/*     */     
/* 386 */     p.addElement(new Integer(this.num_constrain));
/*     */     
/* 388 */     p.addElement(this.objective_function.isMax());
/*     */     
/* 390 */     double[] c = new double[this.num_variable];
/* 391 */     this.objective_function.getCoefficient(c);
/* 392 */     p.addElement(c);
/*     */     
/* 394 */     double[][] c2 = new double[this.num_constrain][this.num_variable + 1];
/* 395 */     for (int i = 0; i < this.num_constrain; i++) {
/* 396 */       this.linear_equations[i].getCoefficient(c2[i]);
/*     */     }
/* 398 */     p.addElement(c2);
/*     */     
/*     */ 
/* 401 */     int[] op = new int[this.num_constrain];
/* 402 */     for (int i = 0; i < this.num_constrain; i++) {
/* 403 */       op[i] = this.linear_equations[i].getOperator();
/*     */     }
/* 405 */     p.addElement(op);
/*     */     
/*     */ 
/* 408 */     boolean[] b = new boolean[this.num_variable];
/*     */     
/* 410 */     double[] rhs = new double[this.num_variable];
/* 411 */     this.bound.getBound(b, rhs);
/* 412 */     p.addElement(b);
/* 413 */     p.addElement(rhs);
/*     */     
/* 415 */     IOROperation operation = new IOROperation(10101, p);
/*     */     
/*     */ 
/* 418 */     this.controller.solver.doWork(operation);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPEnterPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */