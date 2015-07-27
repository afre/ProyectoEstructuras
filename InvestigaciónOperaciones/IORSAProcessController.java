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
/*     */ import javax.swing.JButton;
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
/*     */ public class IORSAProcessController
/*     */   extends IORProcessController
/*     */ {
/*     */   private JRadioButtonMenuItem mi_enter_queueing_problem;
/*     */   public JRadioButtonMenuItem mi_interactively_siumlate_queueing_problem;
/*     */   private JRadioButtonMenuItem mi_waiting_line;
/*  37 */   private IORSAEnterPanel enterPanel = null;
/*  38 */   private IORSASolvePanel solvePanel = null;
/*     */   
/*  40 */   private IORSAWaitingLinePane waitLinePane = null;
/*     */   
/*     */ 
/*  43 */   public IORSAProcessController.SAData data = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ORSASolver solver;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORSAProcessController(IORTutorial t)
/*     */   {
/*  55 */     super(t);
/*  56 */     initializeAreaHelpPanel();
/*  57 */     this.solver = new ORSASolver();
/*  58 */     this.data = new IORSAProcessController.SAData();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeAreaHelpPanel()
/*     */   {
/*  66 */     String str = "The current area is \"Simulation.\" You can select ";
/*  67 */     str = String.valueOf(String.valueOf(str)).concat("a procedure from the Procedure menu to start. Before executing these ");
/*  68 */     str = String.valueOf(String.valueOf(str)).concat("procedures for the first time, we recommend that you go through the ");
/*  69 */     str = String.valueOf(String.valueOf(str)).concat("corresponding demonstration examples in OR Tutor.");
/*  70 */     str = String.valueOf(String.valueOf(str)).concat("\n\n");
/*  71 */     str = String.valueOf(String.valueOf(str)).concat("If you would like to choose an area other than \"Simulation\" within which ");
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
/*     */   protected void setMenuState(int currentStep) {}
/*     */   
/*     */ 
/*     */ 
/*     */   protected void setDefaultMenuState() {}
/*     */   
/*     */ 
/*     */   private void enterProblem()
/*     */   {
/*  91 */     if (this.enterPanel == null) {
/*  92 */       this.enterPanel = new IORSAEnterPanel(this);
/*     */     }
/*  94 */     setHelpEnabled(true);
/*     */     
/*  96 */     this.currentActionPanel = this.enterPanel;
/*  97 */     this.tutorial.scrollPane.setViewportView(this.enterPanel);
/*  98 */     this.enterPanel.bn_back.setVisible(false);
/*  99 */     this.enterPanel.actionStatus.setText(" ");
/* 100 */     this.enterPanel.setPanelEnable(true);
/*     */     
/* 102 */     this.mi_interactively_siumlate_queueing_problem.setEnabled(false);
/* 103 */     this.enterPanel.revalidate();
/* 104 */     this.enterPanel.repaint();
/*     */   }
/*     */   
/*     */   private void solveProblem() {
/* 108 */     Vector v = new Vector();
/* 109 */     IOROperation op = new IOROperation(50301, v);
/* 110 */     this.solver.doWork(op, this.data);
/*     */     
/* 112 */     this.solvePanel = new IORSASolvePanel(this);
/* 113 */     this.currentActionPanel = this.solvePanel;
/* 114 */     this.tutorial.scrollPane.setViewportView(this.solvePanel);
/* 115 */     this.solvePanel.resetActionStatus();
/* 116 */     this.solvePanel.setFinishEnabled(true);
/* 117 */     this.solvePanel.revalidate();
/* 118 */     this.solvePanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void createMyMenus()
/*     */   {
/* 126 */     ButtonGroup group = new ButtonGroup();
/*     */     
/*     */ 
/* 129 */     this.mi_enter_queueing_problem = new JRadioButtonMenuItem("Enter Queueing Problem");
/* 130 */     this.tutorial.procedure.add(this.mi_enter_queueing_problem);
/* 131 */     this.mi_enter_queueing_problem.setMnemonic('E');
/* 132 */     group.add(this.mi_enter_queueing_problem);
/* 133 */     this.mi_enter_queueing_problem.setEnabled(true);
/* 134 */     this.mi_enter_queueing_problem.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 136 */         IORSAProcessController.this.procedure = "Enter Queueing Problem";
/* 137 */         IORSAProcessController.this.enterProblem();
/* 138 */         IORSAProcessController.this.setStatusBar();
/* 139 */         IORSAProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 142 */     });
/* 143 */     this.mi_interactively_siumlate_queueing_problem = new JRadioButtonMenuItem("Interactively Simulate Queueing Problem");
/* 144 */     this.tutorial.procedure.add(this.mi_interactively_siumlate_queueing_problem);
/* 145 */     this.mi_interactively_siumlate_queueing_problem.setMnemonic('I');
/* 146 */     group.add(this.mi_interactively_siumlate_queueing_problem);
/* 147 */     this.mi_interactively_siumlate_queueing_problem.setEnabled(false);
/* 148 */     this.mi_interactively_siumlate_queueing_problem.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 150 */         IORSAProcessController.this.procedure = "Interactively Simulate Queueing Problem";
/* 151 */         IORSAProcessController.this.solveProblem();
/* 152 */         IORSAProcessController.this.setStatusBar();
/* 153 */         IORSAProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 156 */     });
/* 157 */     this.tutorial.option.setEnabled(false);
/* 158 */     this.mi_waiting_line = new JRadioButtonMenuItem("Animation of a Queueing System");
/* 159 */     this.tutorial.procedure.add(this.mi_waiting_line);
/* 160 */     this.mi_waiting_line.setMnemonic('W');
/* 161 */     this.mi_waiting_line.setEnabled(true);
/* 162 */     this.mi_waiting_line.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 164 */         IORSAProcessController.this.procedure = "Animation of a Queueing System";
/* 165 */         IORSAProcessController.this.setStatusBar();
/* 166 */         IORSAProcessController.this.WaitingLineWork();
/* 167 */         IORSAProcessController.this.tutorial.closeDemoController();
/*     */       }
/* 169 */     });
/* 170 */     group.add(this.mi_waiting_line);
/*     */     
/*     */ 
/*     */ 
/* 174 */     this.tutorial.option.setEnabled(false);
/*     */   }
/*     */   
/*     */   private void WaitingLineWork() {
/* 178 */     this.waitLinePane = new IORSAWaitingLinePane(this);
/* 179 */     this.currentActionPanel = this.waitLinePane;
/* 180 */     setHelpEnabled(true);
/* 181 */     this.tutorial.scrollPane.setViewportView(this.waitLinePane);
/* 182 */     this.waitLinePane.revalidate();
/* 183 */     this.waitLinePane.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   public static class SAData
/*     */     implements Serializable
/*     */   {
/*     */     public int num_server;
/*     */     
/*     */     public int num_class;
/*     */     
/*     */     public boolean is_customer_event;
/*     */     
/*     */     public boolean is_customer_done;
/*     */     
/*     */     public boolean is_server_done;
/* 199 */     public boolean[] customer_being_served = new boolean[2];
/*     */     
/*     */ 
/* 202 */     public int[] class_of_customer_being_served_by_server1 = new int[2];
/* 203 */     public int[] class_of_customer_being_served_by_server2 = new int[2];
/*     */     
/*     */ 
/*     */ 
/* 207 */     public double[] current_time = new double[2];
/* 208 */     public int[] num_customer1_in_queue = new int[2];
/* 209 */     public int[] num_customer2_in_queue = new int[2];
/*     */     
/*     */ 
/*     */ 
/*     */     public double random_time;
/*     */     
/*     */ 
/*     */     public double customer_wait_time;
/*     */     
/*     */ 
/* 219 */     public double[] customer1_arrive_time = new double[2];
/* 220 */     public double[] server1_finish_time = new double[2];
/* 221 */     public double[] customer2_arrive_time = new double[2];
/* 222 */     public double[] server2_finish_time = new double[2];
/*     */     
/*     */ 
/* 225 */     public double[] average_wait_time_exclude_service = new double[2];
/* 226 */     public double[] average_wait_time_include_service = new double[2];
/* 227 */     public double[] average_num_wait_to_begin_service = new double[2];
/* 228 */     public double[] average_num_wait_or_in_service = new double[2];
/*     */     
/*     */     public String arrivalRate;
/*     */     
/*     */     public String serviceRate;
/*     */     
/*     */     public String ns1Num;
/*     */     
/*     */     public String ns2Num;
/*     */     
/*     */     public String lqStr;
/*     */     public String lsStr;
/*     */     public String wqStr;
/*     */     public String wsStr;
/*     */     public int phaseNum;
/*     */     public boolean isShowData;
/*     */   }
/*     */   
/*     */   protected String getArea()
/*     */   {
/* 248 */     return "Simulation";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/*     */     try
/*     */     {
/* 258 */       if ((this.currentActionPanel instanceof IORIPEnterPanel)) {
/* 259 */         ((IORIPEnterPanel)this.currentActionPanel).SaveEnterProblem();
/* 260 */         this.solver.getData(this.data);
/*     */       }
/* 262 */       out.writeObject(this.solver);
/* 263 */       out.writeObject(this.data);
/* 264 */       out.writeObject(this.procedure);
/* 265 */       out.writeObject(this.option);
/* 266 */       out.writeObject(new Boolean(this.mi_enter_queueing_problem.isEnabled()));
/* 267 */       out.writeObject(new Boolean(this.mi_interactively_siumlate_queueing_problem.isEnabled()));
/*     */       
/* 269 */       if ((this.currentActionPanel instanceof IORSAEnterPanel)) {
/* 270 */         out.writeObject("IORSAEnterPanel");
/*     */       }
/* 272 */       else if ((this.currentActionPanel instanceof IORSASolvePanel)) {
/* 273 */         out.writeObject("IORSASolvePanel");
/* 274 */       } else if ((this.currentActionPanel instanceof IORSAWaitingLinePane)) {
/* 275 */         out.writeObject("IORSAWaitingLinePane");
/*     */       }
/*     */       else
/*     */       {
/* 279 */         System.out.println("Save file fails");
/* 280 */         return;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 284 */       System.out.println("Save file fails");
/* 285 */       return;
/*     */     }
/*     */     
/* 288 */     this.currentActionPanel.SaveFile(out);
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
/* 301 */       this.solver = ((ORSASolver)in.readObject());
/* 302 */       this.data = ((IORSAProcessController.SAData)in.readObject());
/* 303 */       this.procedure = ((String)in.readObject());
/* 304 */       this.option = ((String)in.readObject());
/* 305 */       this.mi_enter_queueing_problem.setEnabled(((Boolean)in.readObject()).booleanValue());
/* 306 */       this.mi_interactively_siumlate_queueing_problem.setEnabled(((Boolean)in.readObject()).booleanValue());
/*     */       
/* 308 */       ActionPanelName = (String)in.readObject();
/*     */     }
/*     */     catch (Exception e) {
/*     */       String ActionPanelName;
/* 312 */       System.out.println("Open file fails"); return;
/*     */     }
/*     */     
/*     */     String ActionPanelName;
/*     */     
/* 317 */     setHelpEnabled(true);
/* 318 */     if (ActionPanelName.equalsIgnoreCase("IORSAEnterPanel")) {
/* 319 */       this.currentActionPanel = new IORSAEnterPanel(this);
/* 320 */       this.mi_enter_queueing_problem.setSelected(true);
/*     */     }
/* 322 */     else if (ActionPanelName.equalsIgnoreCase("IORSASolvePanel")) {
/* 323 */       this.currentActionPanel = new IORSASolvePanel(this);
/* 324 */       this.mi_interactively_siumlate_queueing_problem.setSelected(true);
/* 325 */     } else if (ActionPanelName.equalsIgnoreCase("IORSAWaitingLinePane")) {
/* 326 */       this.currentActionPanel = new IORSAWaitingLinePane(this);
/*     */     }
/*     */     else
/*     */     {
/* 330 */       setHelpEnabled(false);
/* 331 */       System.out.println("Open file fails");
/* 332 */       return;
/*     */     }
/*     */     
/* 335 */     setStatusBar();
/* 336 */     this.tutorial.scrollPane.setViewportView(this.currentActionPanel);
/* 337 */     this.currentActionPanel.LoadFile(in);
/*     */     
/*     */ 
/* 340 */     this.currentActionPanel.revalidate();
/* 341 */     this.currentActionPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void print()
/*     */   {
/* 348 */     int p = 1;
/* 349 */     if (this.mi_enter_queueing_problem.isSelected()) {
/* 350 */       p = 1;
/* 351 */     } else if ((this.mi_interactively_siumlate_queueing_problem.isEnabled()) && (this.mi_interactively_siumlate_queueing_problem.isSelected()))
/* 352 */       p = 2;
/* 353 */     this.solver.setPrintProcedure(p);
/* 354 */     this.solver.setPrintShowResult(((IORSASolvePanel)this.currentActionPanel).is_result);
/*     */     
/* 356 */     PrinterJob printJob = PrinterJob.getPrinterJob();
/* 357 */     printJob.setPrintable(this.solver);
/* 358 */     boolean pDialogState = true;
/* 359 */     pDialogState = printJob.printDialog();
/* 360 */     if (pDialogState) {
/*     */       try {
/* 362 */         printJob.print();
/*     */       } catch (Exception ex) {
/* 364 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void printToFile()
/*     */   {
/* 373 */     int p = 1;
/* 374 */     if (this.mi_enter_queueing_problem.isSelected()) {
/* 375 */       p = 1;
/* 376 */     } else if ((this.mi_interactively_siumlate_queueing_problem.isEnabled()) && (this.mi_interactively_siumlate_queueing_problem.isSelected())) {
/* 377 */       p = 2;
/*     */     }
/* 379 */     if ((this.currentActionPanel != null) && ((this.currentActionPanel instanceof IORSASolvePanel))) {
/* 380 */       this.solver.setPrintShowResult(((IORSASolvePanel)this.currentActionPanel).is_result);
/*     */     }
/* 382 */     JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
/* 383 */     int result = fc.showSaveDialog(null);
/* 384 */     File chosenFile = fc.getSelectedFile();
/* 385 */     if ((result == 0) && (chosenFile != null)) {
/* 386 */       this.solver.PrintToFile(chosenFile.getPath(), p);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORSAProcessController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */