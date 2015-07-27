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
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
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
/*     */ public class IORMDProcessController
/*     */   extends IORProcessController
/*     */ {
/*  33 */   public IORMDProcessController.MKData data = new IORMDProcessController.MKData();
/*     */   
/*  35 */   public ORMDSolver solver = new ORMDSolver();
/*     */   
/*     */   private JRadioButtonMenuItem mi_enter_markov_decision_model;
/*     */   
/*     */   private JRadioButtonMenuItem mi_policy_improvement_algorithm_average_cost;
/*     */   private JRadioButtonMenuItem mi_policy_improvement_algorithm_discounted_cost;
/*     */   private JRadioButtonMenuItem mi_method_of_successive;
/*  42 */   private IORMKDEnterPanel enterpanel = null;
/*  43 */   private IORMKDAveragePanel averagepanel = null;
/*  44 */   private IORMKDiscountPanel discountpanel = null;
/*  45 */   private IORMKDSuccessivePanel succpanel = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORMDProcessController(IORTutorial t)
/*     */   {
/*  55 */     super(t);
/*  56 */     initializeAreaHelpPanel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeAreaHelpPanel()
/*     */   {
/*  64 */     String str = "The current area is \"Markov Decision Processes.\" You can select ";
/*  65 */     str = String.valueOf(String.valueOf(str)).concat("a procedure from the Procedure menu to start. Before executing these ");
/*  66 */     str = String.valueOf(String.valueOf(str)).concat("procedures for the first time, we recommend that you go through the ");
/*  67 */     str = String.valueOf(String.valueOf(str)).concat("demonstration example, entitled Policy Improvement Algorithm -- Average Cost Case, in OR Tutor.");
/*  68 */     str = String.valueOf(String.valueOf(str)).concat("\n\n");
/*  69 */     str = String.valueOf(String.valueOf(str)).concat("If you would like to choose an area other than \"Markov Decision Processes\" within which ");
/*  70 */     str = String.valueOf(String.valueOf(str)).concat("to work, select the Area menu.\n");
/*     */     
/*  72 */     MultilineLabel content = new MultilineLabel(str);
/*  73 */     content.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*     */     
/*  75 */     this.areaHelpPanel = new MultilinePanel(content, this.tutorial.scrollPane);
/*     */   }
/*     */   
/*     */   private void setMenuStateMKDStartEnter() {
/*  79 */     this.mi_enter_markov_decision_model.setEnabled(true);
/*  80 */     this.mi_policy_improvement_algorithm_average_cost.setEnabled(false);
/*  81 */     this.mi_policy_improvement_algorithm_discounted_cost.setEnabled(false);
/*  82 */     this.mi_method_of_successive.setEnabled(false);
/*     */   }
/*     */   
/*     */   private void mkdecision_enter()
/*     */   {
/*  87 */     if (this.enterpanel == null) {
/*  88 */       this.enterpanel = new IORMKDEnterPanel(this);
/*     */     }
/*  90 */     setHelpEnabled(true);
/*  91 */     this.currentActionPanel = this.enterpanel;
/*     */     
/*  93 */     setMenuStateMKDStartEnter();
/*  94 */     this.tutorial.scrollPane.setViewportView(this.enterpanel);
/*  95 */     this.enterpanel.revalidate();
/*  96 */     this.enterpanel.repaint();
/*     */   }
/*     */   
/*     */   private void average_improve()
/*     */   {
/* 101 */     Vector par = new Vector();
/* 102 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/* 104 */     par.addElement(new Integer(1));
/* 105 */     opr.parameters = par;
/* 106 */     opr.operation_code = 40105;
/* 107 */     this.solver.doWork(opr, this.data);
/*     */     
/* 109 */     this.averagepanel = new IORMKDAveragePanel(this);
/* 110 */     this.currentActionPanel = this.averagepanel;
/* 111 */     this.tutorial.scrollPane.setViewportView(this.averagepanel);
/* 112 */     this.averagepanel.revalidate();
/* 113 */     this.averagepanel.repaint();
/*     */   }
/*     */   
/*     */   private void discount_improve()
/*     */   {
/* 118 */     Vector par = new Vector();
/* 119 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/* 121 */     par.addElement(new Integer(2));
/* 122 */     opr.parameters = par;
/* 123 */     opr.operation_code = 40105;
/* 124 */     this.solver.doWork(opr, this.data);
/*     */     
/* 126 */     this.discountpanel = new IORMKDiscountPanel(this);
/* 127 */     this.currentActionPanel = this.discountpanel;
/* 128 */     this.tutorial.scrollPane.setViewportView(this.discountpanel);
/* 129 */     this.discountpanel.revalidate();
/* 130 */     this.discountpanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   private void method_successive()
/*     */   {
/* 136 */     Vector par = new Vector();
/* 137 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/* 139 */     par.addElement(new Integer(3));
/* 140 */     opr.parameters = par;
/* 141 */     opr.operation_code = 40105;
/* 142 */     this.solver.doWork(opr, this.data);
/*     */     
/* 144 */     this.succpanel = new IORMKDSuccessivePanel(this);
/* 145 */     this.currentActionPanel = this.succpanel;
/* 146 */     this.tutorial.scrollPane.setViewportView(this.succpanel);
/* 147 */     this.succpanel.revalidate();
/* 148 */     this.succpanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void enableMenus()
/*     */   {
/* 156 */     this.mi_enter_markov_decision_model.setEnabled(true);
/* 157 */     this.mi_policy_improvement_algorithm_average_cost.setEnabled(true);
/* 158 */     this.mi_policy_improvement_algorithm_discounted_cost.setEnabled(true);
/* 159 */     this.mi_method_of_successive.setEnabled(true);
/*     */   }
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
/*     */   protected void setDefaultMenuState()
/*     */   {
/* 174 */     setMenuStateMKDStartEnter();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void createMyMenus()
/*     */   {
/* 182 */     ButtonGroup group = new ButtonGroup();
/*     */     
/*     */ 
/* 185 */     this.mi_enter_markov_decision_model = new JRadioButtonMenuItem("Enter Markov Decision Model");
/* 186 */     this.tutorial.procedure.add(this.mi_enter_markov_decision_model);
/* 187 */     group.add(this.mi_enter_markov_decision_model);
/* 188 */     this.mi_enter_markov_decision_model.setMnemonic('E');
/* 189 */     this.mi_enter_markov_decision_model.setEnabled(true);
/* 190 */     this.mi_enter_markov_decision_model.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 192 */         IORMDProcessController.this.mkdecision_enter();
/* 193 */         IORMDProcessController.this.procedure = "Enter Markov Decision Model";
/* 194 */         IORMDProcessController.this.setStatusBar();
/* 195 */         IORMDProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 198 */     });
/* 199 */     this.mi_policy_improvement_algorithm_average_cost = new JRadioButtonMenuItem("Policy Improvement Algorithm -- Average Cost  ");
/* 200 */     this.tutorial.procedure.add(this.mi_policy_improvement_algorithm_average_cost);
/* 201 */     group.add(this.mi_policy_improvement_algorithm_average_cost);
/* 202 */     this.mi_policy_improvement_algorithm_average_cost.setMnemonic('A');
/* 203 */     this.mi_policy_improvement_algorithm_average_cost.setEnabled(true);
/* 204 */     this.mi_policy_improvement_algorithm_average_cost.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 206 */         IORMDProcessController.this.average_improve();
/* 207 */         IORMDProcessController.this.procedure = "Policy Improvement Algorithm -- Average Cost  ";
/* 208 */         IORMDProcessController.this.setStatusBar();
/* 209 */         IORMDProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 212 */     });
/* 213 */     this.mi_policy_improvement_algorithm_discounted_cost = new JRadioButtonMenuItem("Policy Improvement Algorithm -- Discounted Cost");
/* 214 */     this.tutorial.procedure.add(this.mi_policy_improvement_algorithm_discounted_cost);
/* 215 */     group.add(this.mi_policy_improvement_algorithm_discounted_cost);
/* 216 */     this.mi_policy_improvement_algorithm_discounted_cost.setMnemonic('D');
/* 217 */     this.mi_policy_improvement_algorithm_discounted_cost.setEnabled(true);
/* 218 */     this.mi_policy_improvement_algorithm_discounted_cost.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 220 */         IORMDProcessController.this.discount_improve();
/* 221 */         IORMDProcessController.this.procedure = "Policy Improvement Algorithm -- Discounted Cost";
/* 222 */         IORMDProcessController.this.setStatusBar();
/* 223 */         IORMDProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 226 */     });
/* 227 */     this.mi_method_of_successive = new JRadioButtonMenuItem("Method of Successive Approximations");
/* 228 */     this.tutorial.procedure.add(this.mi_method_of_successive);
/* 229 */     group.add(this.mi_method_of_successive);
/* 230 */     this.mi_method_of_successive.setMnemonic('M');
/* 231 */     this.mi_method_of_successive.setEnabled(true);
/* 232 */     this.mi_method_of_successive.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 234 */         IORMDProcessController.this.procedure = "Method of Successive Approximations";
/* 235 */         IORMDProcessController.this.setStatusBar();
/* 236 */         IORMDProcessController.this.method_successive();
/* 237 */         IORMDProcessController.this.tutorial.closeDemoController();
/*     */       }
/* 239 */     });
/* 240 */     this.tutorial.help.getItem(1).setEnabled(false);
/*     */     
/* 242 */     this.tutorial.option.setEnabled(false);
/*     */   }
/*     */   
/*     */   public static class MKData implements Serializable {
/* 246 */     private final int MAXSTATES = 4;
/* 247 */     private final int MAXDECISIONS = 5;
/*     */     
/*     */     public int num_states;
/*     */     
/*     */     public int num_decisions;
/*     */     
/*     */     public double[][] cost;
/*     */     public double[][][] p;
/*     */     public int[] old_decision;
/*     */     public int[] new_decision;
/*     */     public double discount;
/*     */     public double gr;
/*     */     public double[] vr;
/*     */     public double[][] expr;
/*     */     public double[] new_vr;
/*     */     public double[][] grcoef;
/*     */     public double[][] exprcoef;
/*     */   }
/*     */   
/*     */   protected String getArea()
/*     */   {
/* 268 */     return "Markov Decision";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/*     */     try
/*     */     {
/* 278 */       out.writeObject(this.solver);
/* 279 */       out.writeObject(this.data);
/* 280 */       out.writeObject(this.procedure);
/* 281 */       out.writeObject(this.option);
/* 282 */       out.writeObject(new Boolean(this.mi_enter_markov_decision_model.isEnabled()));
/* 283 */       out.writeObject(new Boolean(this.mi_policy_improvement_algorithm_average_cost.isEnabled()));
/* 284 */       out.writeObject(new Boolean(this.mi_policy_improvement_algorithm_discounted_cost.isEnabled()));
/* 285 */       out.writeObject(new Boolean(this.mi_method_of_successive.isEnabled()));
/*     */       
/* 287 */       if ((this.currentActionPanel instanceof IORMKDEnterPanel)) {
/* 288 */         out.writeObject("IORMKDEnterPanel");
/*     */       }
/* 290 */       else if ((this.currentActionPanel instanceof IORMKDAveragePanel)) {
/* 291 */         out.writeObject("IORMKDAveragePanel");
/*     */       }
/* 293 */       else if ((this.currentActionPanel instanceof IORMKDiscountPanel)) {
/* 294 */         out.writeObject("IORMKDiscountPanel");
/*     */       }
/* 296 */       else if ((this.currentActionPanel instanceof IORMKDSuccessivePanel)) {
/* 297 */         out.writeObject("IORMKDSuccessivePanel");
/*     */       }
/*     */       else {
/* 300 */         System.out.println("Save file fails");
/* 301 */         return;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 305 */       System.out.println("Save file fails");
/* 306 */       return;
/*     */     }
/*     */     
/* 309 */     this.currentActionPanel.SaveFile(out);
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
/* 322 */       this.solver = ((ORMDSolver)in.readObject());
/* 323 */       this.data = ((IORMDProcessController.MKData)in.readObject());
/* 324 */       this.procedure = ((String)in.readObject());
/* 325 */       this.option = ((String)in.readObject());
/* 326 */       this.mi_enter_markov_decision_model.setEnabled(((Boolean)in.readObject()).booleanValue());
/* 327 */       this.mi_policy_improvement_algorithm_average_cost.setEnabled(((Boolean)in.readObject()).booleanValue());
/* 328 */       this.mi_policy_improvement_algorithm_discounted_cost.setEnabled(((Boolean)in.readObject()).booleanValue());
/* 329 */       this.mi_method_of_successive.setEnabled(((Boolean)in.readObject()).booleanValue());
/*     */       
/* 331 */       ActionPanelName = (String)in.readObject();
/*     */     }
/*     */     catch (Exception e) {
/*     */       String ActionPanelName;
/* 335 */       System.out.println("Open file fails"); return;
/*     */     }
/*     */     
/*     */     String ActionPanelName;
/*     */     
/* 340 */     setHelpEnabled(true);
/* 341 */     if (ActionPanelName.equalsIgnoreCase("IORMKDEnterPanel")) {
/* 342 */       this.currentActionPanel = new IORMKDEnterPanel(this);
/* 343 */       this.mi_enter_markov_decision_model.setSelected(true);
/*     */     }
/* 345 */     else if (ActionPanelName.equalsIgnoreCase("IORMKDAveragePanel")) {
/* 346 */       this.currentActionPanel = new IORMKDAveragePanel(this);
/* 347 */       this.mi_policy_improvement_algorithm_average_cost.setSelected(true);
/*     */     }
/* 349 */     else if (ActionPanelName.equalsIgnoreCase("IORMKDiscountPanel")) {
/* 350 */       this.currentActionPanel = new IORMKDiscountPanel(this);
/* 351 */       this.mi_policy_improvement_algorithm_discounted_cost.setSelected(true);
/*     */     }
/* 353 */     else if (ActionPanelName.equalsIgnoreCase("IORMKDSuccessivePanel")) {
/* 354 */       this.currentActionPanel = new IORMKDSuccessivePanel(this);
/* 355 */       this.mi_method_of_successive.setSelected(true);
/*     */     }
/*     */     else {
/* 358 */       setHelpEnabled(false);
/* 359 */       System.out.println("Open file fails");
/* 360 */       return;
/*     */     }
/*     */     
/* 363 */     setStatusBar();
/* 364 */     this.currentActionPanel.LoadFile(in);
/*     */     
/*     */ 
/* 367 */     this.tutorial.scrollPane.setViewportView(this.currentActionPanel);
/* 368 */     this.currentActionPanel.revalidate();
/* 369 */     this.currentActionPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void print()
/*     */   {
/* 378 */     int p = 1;
/* 379 */     if (this.mi_enter_markov_decision_model.isSelected()) {
/* 380 */       p = 1;
/* 381 */     } else if ((this.mi_policy_improvement_algorithm_average_cost.isEnabled()) && (this.mi_policy_improvement_algorithm_average_cost.isSelected())) {
/* 382 */       p = 2;
/* 383 */     } else if ((this.mi_policy_improvement_algorithm_discounted_cost.isEnabled()) && (this.mi_policy_improvement_algorithm_discounted_cost.isSelected())) {
/* 384 */       p = 3;
/* 385 */     } else if ((this.mi_method_of_successive.isEnabled()) && (this.mi_method_of_successive.isSelected()))
/* 386 */       p = 4;
/* 387 */     this.solver.setPrintProcedure(p);
/*     */     
/* 389 */     PrinterJob printJob = PrinterJob.getPrinterJob();
/* 390 */     printJob.setPrintable(this.solver);
/* 391 */     boolean pDialogState = true;
/* 392 */     pDialogState = printJob.printDialog();
/* 393 */     if (pDialogState) {
/*     */       try {
/* 395 */         printJob.print();
/*     */       } catch (Exception ex) {
/* 397 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void printToFile()
/*     */   {
/* 406 */     int p = 1;
/* 407 */     if (this.mi_enter_markov_decision_model.isSelected()) {
/* 408 */       p = 1;
/* 409 */     } else if ((this.mi_policy_improvement_algorithm_average_cost.isEnabled()) && (this.mi_policy_improvement_algorithm_average_cost.isSelected())) {
/* 410 */       p = 2;
/* 411 */     } else if ((this.mi_policy_improvement_algorithm_discounted_cost.isEnabled()) && (this.mi_policy_improvement_algorithm_discounted_cost.isSelected())) {
/* 412 */       p = 3;
/* 413 */     } else if ((this.mi_method_of_successive.isEnabled()) && (this.mi_method_of_successive.isSelected())) {
/* 414 */       p = 4;
/*     */     }
/* 416 */     JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
/* 417 */     int result = fc.showSaveDialog(null);
/* 418 */     File chosenFile = fc.getSelectedFile();
/* 419 */     if ((result == 0) && (chosenFile != null)) {
/* 420 */       this.solver.PrintToFile(chosenFile.getPath(), p);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORMDProcessController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */