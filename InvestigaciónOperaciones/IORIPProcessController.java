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
/*     */ public class IORIPProcessController
/*     */   extends IORProcessController
/*     */ {
/*  31 */   public static final int IP_START_ENTER_PROBLEM = 1;
/*  32 */   public static final int IP_FINISH_ENTER_PROBLEM_WITH_ONLY_BINARY_VARIABLES = 2;
/*  33 */   public static final int IP_FINISH_ENTER_PROBLEM_WITH_MIXED_VARIABLES = 3;
/*     */   
/*     */   private JRadioButtonMenuItem mi_enter_or_revise_an_integer_programming_model;
/*     */   
/*     */   private JRadioButtonMenuItem mi_solve_binary_integer_program_interactively;
/*     */   private JRadioButtonMenuItem mi_solve_mixed_integer_program_interactively;
/*  39 */   private IORIPEnterPanel enterPanel = null;
/*  40 */   private IORIPActionPanel ipPanel = null;
/*     */   
/*  42 */   public IORIPProcessController.IPData data = null;
/*     */   
/*  44 */   public ORIPSolver solver = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORIPProcessController(IORTutorial t)
/*     */   {
/*  54 */     super(t);
/*  55 */     this.solver = new ORIPSolver();
/*  56 */     this.data = new IORIPProcessController.IPData();
/*  57 */     this.solver.getData(this.data);
/*  58 */     initializeAreaHelpPanel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeAreaHelpPanel()
/*     */   {
/*  66 */     String str = "The current area is \"Integer Programming.\" You can select ";
/*  67 */     str = String.valueOf(String.valueOf(str)).concat("a procedure from the Procedure menu to start. Before executing these ");
/*  68 */     str = String.valueOf(String.valueOf(str)).concat("procedures for the first time, we recommend that you go through the ");
/*  69 */     str = String.valueOf(String.valueOf(str)).concat("demonstration examples in OR Tutor.");
/*  70 */     str = String.valueOf(String.valueOf(str)).concat("\n\n");
/*  71 */     str = String.valueOf(String.valueOf(str)).concat("If you would like to choose an area other than \"Integer Programming\" within which ");
/*  72 */     str = String.valueOf(String.valueOf(str)).concat("to work, select the Area menu.\n");
/*     */     
/*  74 */     MultilineLabel content = new MultilineLabel(str);
/*  75 */     content.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*     */     
/*  77 */     this.areaHelpPanel = new MultilinePanel(content, this.tutorial.scrollPane);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setMenuState(int currentStep)
/*     */   {
/*  88 */     switch (currentStep)
/*     */     {
/*     */     case 1: 
/*  91 */       this.mi_enter_or_revise_an_integer_programming_model.setEnabled(true);
/*  92 */       this.mi_solve_binary_integer_program_interactively.setEnabled(false);
/*  93 */       this.mi_solve_mixed_integer_program_interactively.setEnabled(false);
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
/* 108 */       break;
/*     */     case 2: 
/*  96 */       this.mi_enter_or_revise_an_integer_programming_model.setEnabled(true);
/*  97 */       this.mi_solve_binary_integer_program_interactively.setEnabled(true);
/*  98 */       this.mi_solve_mixed_integer_program_interactively.setEnabled(false);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 108 */       break;
/*     */     case 3: 
/* 101 */       this.mi_enter_or_revise_an_integer_programming_model.setEnabled(true);
/* 102 */       this.mi_solve_binary_integer_program_interactively.setEnabled(false);
/* 103 */       this.mi_solve_mixed_integer_program_interactively.setEnabled(true);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 108 */       break;
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */   protected void setDefaultMenuState() {}
/*     */   
/*     */ 
/*     */   protected void createMyMenus()
/*     */   {
/* 119 */     ButtonGroup group = new ButtonGroup();
/*     */     
/*     */ 
/* 122 */     this.mi_enter_or_revise_an_integer_programming_model = new JRadioButtonMenuItem("Enter or Revise an Integer Programming Model");
/* 123 */     this.tutorial.procedure.add(this.mi_enter_or_revise_an_integer_programming_model);
/* 124 */     group.add(this.mi_enter_or_revise_an_integer_programming_model);
/* 125 */     this.mi_enter_or_revise_an_integer_programming_model.setMnemonic('E');
/* 126 */     this.mi_enter_or_revise_an_integer_programming_model.setEnabled(true);
/* 127 */     this.mi_enter_or_revise_an_integer_programming_model.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 129 */         IORIPProcessController.this.procedure = "Enter or Revise an Integer Programming Model";
/* 130 */         IORIPProcessController.this.EnterorReviseaGrneralIntegerProgrammingmode();
/* 131 */         IORIPProcessController.this.setStatusBar();
/* 132 */         IORIPProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 135 */     });
/* 136 */     this.mi_solve_binary_integer_program_interactively = new JRadioButtonMenuItem("Solve Binary Integer Program Interactively  ");
/* 137 */     this.tutorial.procedure.add(this.mi_solve_binary_integer_program_interactively);
/* 138 */     group.add(this.mi_solve_binary_integer_program_interactively);
/* 139 */     this.mi_solve_binary_integer_program_interactively.setMnemonic('B');
/* 140 */     this.mi_solve_binary_integer_program_interactively.setEnabled(false);
/* 141 */     this.mi_solve_binary_integer_program_interactively.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 143 */         IORIPProcessController.this.procedure = "Solve Binary Integer Program Interactively  ";
/* 144 */         IORIPProcessController.this.SolveBinaryIntegerProgramInteractively();
/* 145 */         IORIPProcessController.this.setStatusBar();
/* 146 */         IORIPProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 149 */     });
/* 150 */     this.mi_solve_mixed_integer_program_interactively = new JRadioButtonMenuItem("Solve Mixed Integer Program Interactively");
/* 151 */     this.tutorial.procedure.add(this.mi_solve_mixed_integer_program_interactively);
/* 152 */     group.add(this.mi_solve_mixed_integer_program_interactively);
/* 153 */     this.mi_solve_mixed_integer_program_interactively.setMnemonic('P');
/* 154 */     this.mi_solve_mixed_integer_program_interactively.setEnabled(false);
/* 155 */     this.mi_solve_mixed_integer_program_interactively.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 157 */         IORIPProcessController.this.procedure = "Solve Mixed Integer Program Interactively";
/* 158 */         IORIPProcessController.this.SolveMixedIntegerProgramInteractively();
/* 159 */         IORIPProcessController.this.setStatusBar();
/* 160 */         IORIPProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 163 */     });
/* 164 */     this.tutorial.help.getItem(1).setEnabled(false);
/* 165 */     this.tutorial.option.setEnabled(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void EnterorReviseaGrneralIntegerProgrammingmode()
/*     */   {
/* 172 */     if (this.enterPanel == null) {
/* 173 */       this.enterPanel = new IORIPEnterPanel(this);
/*     */     }
/* 175 */     setHelpEnabled(true);
/* 176 */     this.currentActionPanel = this.enterPanel;
/* 177 */     this.tutorial.scrollPane.setViewportView(this.enterPanel);
/* 178 */     setMenuState(1);
/* 179 */     this.enterPanel.setFinishEnabled(true);
/* 180 */     this.enterPanel.revalidate();
/* 181 */     this.enterPanel.repaint();
/*     */   }
/*     */   
/*     */   private void SolveBinaryIntegerProgramInteractively() {
/* 185 */     if (this.ipPanel == null) {
/* 186 */       this.ipPanel = new IORIPActionPanel(this);
/*     */     }
/* 188 */     this.currentActionPanel = this.ipPanel;
/*     */     
/* 190 */     Vector par = new Vector();
/* 191 */     IOROperation opr = new IOROperation(40102, par);
/* 192 */     this.ipPanel.doStep(opr);
/*     */     
/* 194 */     this.tutorial.scrollPane.setViewportView(this.ipPanel);
/* 195 */     this.ipPanel.canvas.requestFocus();
/* 196 */     this.ipPanel.updatePanel();
/* 197 */     this.ipPanel.updateHelp();
/*     */   }
/*     */   
/*     */   private void SolveMixedIntegerProgramInteractively() {
/* 201 */     if (this.ipPanel == null) {
/* 202 */       this.ipPanel = new IORIPActionPanel(this);
/*     */     }
/* 204 */     this.currentActionPanel = this.ipPanel;
/*     */     
/* 206 */     Vector par = new Vector();
/* 207 */     IOROperation opr = new IOROperation(40202, par);
/* 208 */     this.ipPanel.doStep(opr);
/*     */     
/* 210 */     this.tutorial.scrollPane.setViewportView(this.ipPanel);
/* 211 */     this.ipPanel.canvas.requestFocus();
/* 212 */     this.ipPanel.canvas.setFocusNode(0);
/* 213 */     this.ipPanel.updatePanel();
/* 214 */     this.ipPanel.updateHelp();
/*     */   }
/*     */   
/*     */   public static class IPData implements Serializable
/*     */   {
/* 219 */     public int max_num_binary = 5;
/* 220 */     public int max_num_integer = 5;
/* 221 */     public int max_num_continuous = 5;
/* 222 */     public int max_num_constrain = 5;
/*     */     
/* 224 */     public boolean objective_is_max = true;
/* 225 */     public double[] objective_coefficient = null;
/* 226 */     public int[] constrain_operator = null;
/* 227 */     public double[][] constrain_coefficient = null;
/*     */     
/* 229 */     public int MAX_NODE_NUMBER = 63;
/*     */     
/*     */     public boolean isBIP;
/* 232 */     public int NumVariables = 3;
/* 233 */     public int NumBinaryVariables = 1;
/* 234 */     public int NumIntegerVariables = 1;
/* 235 */     public int NumRealVariables = 1;
/* 236 */     public int NumConstraints = 1;
/*     */     
/* 238 */     public boolean IncumbentExists = false;
/* 239 */     public double[] Incumbent = new double[this.MAX_NODE_NUMBER];
/* 240 */     public double IncumbentZ = 0.0D;
/* 241 */     public int IncumbentNodeIndex = -1;
/*     */     
/* 243 */     public int maxLevel = 0;
/* 244 */     public IORIPProcessController.NodeType[] nodes = new IORIPProcessController.NodeType[this.MAX_NODE_NUMBER];
/*     */     
/*     */     public IPData() {
/* 247 */       for (int i = 0; i < this.MAX_NODE_NUMBER; i++)
/* 248 */         this.nodes[i] = new IORIPProcessController.NodeType();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class NodeType implements Serializable {
/*     */     int xSubscript;
/*     */     int xLowerValue;
/*     */     int xUpperValue;
/*     */     boolean fathomed;
/*     */     boolean feasible;
/*     */     boolean leafNode;
/*     */     int level;
/*     */     int offset;
/*     */     boolean valid;
/*     */     double Z;
/* 263 */     double[] solution = new double[31];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected String getArea()
/*     */   {
/* 270 */     return "Integer Programming";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/*     */     try
/*     */     {
/* 281 */       if ((this.currentActionPanel instanceof IORIPEnterPanel)) {
/* 282 */         ((IORIPEnterPanel)this.currentActionPanel).SaveEnterProblem();
/* 283 */         this.solver.getData(this.data);
/*     */       }
/* 285 */       out.writeObject(this.solver);
/* 286 */       out.writeObject(this.data);
/* 287 */       out.writeObject(this.procedure);
/* 288 */       out.writeObject(this.option);
/* 289 */       out.writeObject(new Boolean(this.mi_enter_or_revise_an_integer_programming_model.isEnabled()));
/* 290 */       out.writeObject(new Boolean(this.mi_solve_binary_integer_program_interactively.isEnabled()));
/* 291 */       out.writeObject(new Boolean(this.mi_solve_mixed_integer_program_interactively.isEnabled()));
/*     */       
/* 293 */       if ((this.currentActionPanel instanceof IORIPEnterPanel)) {
/* 294 */         out.writeObject("IORIPEnterPanel");
/*     */       }
/* 296 */       else if ((this.currentActionPanel instanceof IORIPActionPanel)) {
/* 297 */         out.writeObject("IORIPActionPanel");
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
/* 322 */       this.solver = ((ORIPSolver)in.readObject());
/* 323 */       this.data = ((IORIPProcessController.IPData)in.readObject());
/* 324 */       this.procedure = ((String)in.readObject());
/* 325 */       this.option = ((String)in.readObject());
/* 326 */       this.mi_enter_or_revise_an_integer_programming_model.setEnabled(((Boolean)in.readObject()).booleanValue());
/* 327 */       this.mi_solve_binary_integer_program_interactively.setEnabled(((Boolean)in.readObject()).booleanValue());
/* 328 */       this.mi_solve_mixed_integer_program_interactively.setEnabled(((Boolean)in.readObject()).booleanValue());
/*     */       
/* 330 */       ActionPanelName = (String)in.readObject();
/*     */     }
/*     */     catch (Exception e) {
/*     */       String ActionPanelName;
/* 334 */       System.out.println("Open file fails"); return;
/*     */     }
/*     */     
/*     */     String ActionPanelName;
/*     */     
/* 339 */     setHelpEnabled(true);
/* 340 */     if (ActionPanelName.equalsIgnoreCase("IORIPEnterPanel")) {
/* 341 */       this.currentActionPanel = new IORIPEnterPanel(this);
/* 342 */       this.mi_enter_or_revise_an_integer_programming_model.setSelected(true);
/*     */     }
/* 344 */     else if (ActionPanelName.equalsIgnoreCase("IORIPActionPanel")) {
/* 345 */       this.currentActionPanel = new IORIPActionPanel(this);
/* 346 */       if (this.data.isBIP) {
/* 347 */         this.mi_solve_binary_integer_program_interactively.setSelected(true);
/*     */       } else {
/* 349 */         this.mi_solve_mixed_integer_program_interactively.setSelected(true);
/*     */       }
/*     */     } else {
/* 352 */       setHelpEnabled(false);
/* 353 */       System.out.println("Open file fails");
/* 354 */       return;
/*     */     }
/*     */     
/* 357 */     setStatusBar();
/* 358 */     this.currentActionPanel.LoadFile(in);
/*     */     
/*     */ 
/* 361 */     this.tutorial.scrollPane.setViewportView(this.currentActionPanel);
/* 362 */     this.currentActionPanel.revalidate();
/* 363 */     this.currentActionPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void print()
/*     */   {
/* 371 */     int p = 1;
/* 372 */     if (this.mi_enter_or_revise_an_integer_programming_model.isSelected()) {
/* 373 */       p = 1;
/* 374 */     } else if ((this.mi_solve_binary_integer_program_interactively.isEnabled()) && (this.mi_solve_binary_integer_program_interactively.isSelected())) {
/* 375 */       p = 2;
/* 376 */     } else if ((this.mi_solve_mixed_integer_program_interactively.isEnabled()) && (this.mi_solve_mixed_integer_program_interactively.isSelected()))
/* 377 */       p = 3;
/* 378 */     this.solver.setPrintProcedure(p);
/*     */     
/* 380 */     PrinterJob printJob = PrinterJob.getPrinterJob();
/* 381 */     printJob.setPrintable(this.solver);
/* 382 */     boolean pDialogState = true;
/* 383 */     pDialogState = printJob.printDialog();
/* 384 */     if (pDialogState) {
/*     */       try {
/* 386 */         printJob.print();
/*     */       } catch (Exception ex) {
/* 388 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void printToFile()
/*     */   {
/* 397 */     int p = 1;
/* 398 */     if (this.mi_enter_or_revise_an_integer_programming_model.isSelected()) {
/* 399 */       p = 1;
/* 400 */     } else if ((this.mi_solve_binary_integer_program_interactively.isEnabled()) && (this.mi_solve_binary_integer_program_interactively.isSelected())) {
/* 401 */       p = 2;
/* 402 */     } else if ((this.mi_solve_mixed_integer_program_interactively.isEnabled()) && (this.mi_solve_mixed_integer_program_interactively.isSelected())) {
/* 403 */       p = 3;
/*     */     }
/* 405 */     JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
/* 406 */     int result = fc.showSaveDialog(null);
/* 407 */     File chosenFile = fc.getSelectedFile();
/* 408 */     if ((result == 0) && (chosenFile != null)) {
/* 409 */       this.solver.PrintToFile(chosenFile.getPath(), p);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORIPProcessController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */