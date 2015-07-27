/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ 
/*     */ public class IORLPAutoSolverPane extends IORLPActionPanel
/*     */ {
/*     */   private IORLPProcessController controller;
/*     */   private Font f;
/*     */   private Font f1;
/*     */   private java.awt.Image offscreen;
/*     */   private java.awt.Graphics gS2;
/*  23 */   private JPanel controlPane = new JPanel();
/*  24 */   private JButton autoBut = new JButton("Auto");
/*  25 */   private JButton resetBut = new JButton("Reset");
/*  26 */   private int leftScape = 100;
/*  27 */   private int topScape = 100;
/*  28 */   private int tableLineWid = 380;
/*  29 */   private int tableRightSpace = 90;
/*  30 */   private boolean showResult = false;
/*     */   
/*  32 */   JPanel tablePane = null;
/*  33 */   JPanel tablePane1 = null;
/*  34 */   JPanel tablePane2 = null;
/*  35 */   JPanel topPane = null;
/*     */   
/*  37 */   JPanel tablePane3 = null;
/*  38 */   JPanel tablePane4 = null;
/*  39 */   JPanel bottonPane = null;
/*     */   
/*  41 */   private JLabel optimalSolutionLab = new JLabel("Optimal Solution");
/*  42 */   private JLabel objectiveFunctionLab = new JLabel("Objective Function: Z = 0");
/*     */   
/*  44 */   private JLabel sensitivityAnalysisLab = new JLabel("Objective Function Coefficients");
/*  45 */   private JLabel ObjectiveFunctionCoefficientsLab = new JLabel("Allowable Range to Stay Optimal");
/*     */   
/*  47 */   private JLabel lowerRightHandLab = new JLabel("Allowable Range for Right-Hand Side");
/*  48 */   private JLabel errorInfoLab = new JLabel();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  55 */   private IORLPAutoSolverPane.LPTablePanel1 table1 = new IORLPAutoSolverPane.LPTablePanel1();
/*  56 */   private IORLPAutoSolverPane.LPTablePanel2 table2 = new IORLPAutoSolverPane.LPTablePanel2();
/*     */   
/*  58 */   private IORLPAutoSolverPane.LPTablePanel3 table3 = new IORLPAutoSolverPane.LPTablePanel3();
/*  59 */   private IORLPAutoSolverPane.LPTablePanel4 table4 = new IORLPAutoSolverPane.LPTablePanel4();
/*     */   
/*  61 */   private DecimalFormat decfm = new DecimalFormat();
/*     */   
/*     */   public IORLPAutoSolverPane(IORLPProcessController c)
/*     */   {
/*  65 */     super(c);
/*  66 */     this.controller = c;
/*  67 */     this.decfm.setGroupingUsed(false);
/*  68 */     this.decfm.setMaximumFractionDigits(2);
/*     */     
/*     */ 
/*  71 */     this.tablePane1.setLayout(new javax.swing.BoxLayout(this.tablePane1, 1));
/*  72 */     this.tablePane1.add(this.optimalSolutionLab);
/*  73 */     this.tablePane1.add(this.objectiveFunctionLab);
/*  74 */     this.tablePane1.add(this.table1);
/*     */     
/*     */ 
/*  77 */     this.tablePane2 = new JPanel();
/*  78 */     this.tablePane2.setLayout(new javax.swing.BoxLayout(this.tablePane2, 1));
/*  79 */     this.tablePane2.add(this.sensitivityAnalysisLab);
/*  80 */     this.tablePane2.add(this.ObjectiveFunctionCoefficientsLab);
/*  81 */     this.tablePane2.add(this.table2);
/*     */     
/*  83 */     this.topPane = new JPanel();
/*  84 */     this.topPane.setLayout(new java.awt.FlowLayout());
/*  85 */     this.topPane.add(this.tablePane1);
/*  86 */     this.topPane.add(this.tablePane2);
/*  87 */     this.topPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
/*     */     
/*  89 */     this.tablePane3 = new JPanel();
/*  90 */     this.tablePane3.add(this.table3);
/*  91 */     this.table3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*     */     
/*  93 */     this.tablePane4 = new JPanel();
/*  94 */     this.tablePane4.setLayout(new java.awt.BorderLayout());
/*  95 */     this.tablePane4.add(this.table4, "Center");
/*  96 */     this.tablePane4.add(this.lowerRightHandLab, "North");
/*     */     
/*  98 */     this.bottonPane = new JPanel();
/*  99 */     this.bottonPane.setLayout(new java.awt.FlowLayout());
/* 100 */     this.bottonPane.add(this.tablePane3);
/* 101 */     this.bottonPane.add(this.tablePane4);
/*     */     
/* 103 */     this.tablePane = new JPanel();
/* 104 */     this.tablePane.setLayout(new javax.swing.BoxLayout(this.tablePane, 1));
/* 105 */     this.tablePane.add(this.topPane);
/* 106 */     this.tablePane.add(this.bottonPane);
/*     */     
/* 108 */     auto();
/*     */     
/* 110 */     this.controlPane.setLayout(new java.awt.FlowLayout());
/* 111 */     this.controlPane.add(this.autoBut);
/* 112 */     this.controlPane.add(new JLabel("      "));
/* 113 */     this.controlPane.add(this.resetBut);
/* 114 */     this.controlPane.setBorder(BorderFactory.createEmptyBorder(20, 30, 60, 30));
/* 115 */     setLayout(new java.awt.BorderLayout());
/* 116 */     add(this.errorInfoLab, "North");
/* 117 */     this.errorInfoLab.setBorder(BorderFactory.createEmptyBorder(20, 180, 0, 50));
/* 118 */     add(this.tablePane, "Center");
/*     */     
/*     */ 
/* 121 */     this.autoBut.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 123 */         IORLPAutoSolverPane.this.auto();
/*     */       }
/*     */       
/* 126 */     });
/* 127 */     this.resetBut.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 129 */         IORLPAutoSolverPane.this.showResult = false;
/* 130 */         IORLPAutoSolverPane.this.tablePane.setVisible(IORLPAutoSolverPane.this.showResult);
/* 131 */         IORLPAutoSolverPane.this.errorInfoLab.setText("");
/*     */       }
/* 133 */     });
/* 134 */     this.tablePane.setVisible(this.showResult);
/*     */   }
/*     */   
/*     */   public void auto()
/*     */   {
/* 139 */     if (this.controller.data.outputInfo.equalsIgnoreCase("right")) {
/* 140 */       this.showResult = true;
/* 141 */       this.tablePane.setVisible(this.showResult);
/* 142 */       this.table1.tableRow1 = this.controller.data.num_original_variable;
/* 143 */       this.table2.tableRow1 = this.controller.data.num_original_variable;
/* 144 */       this.table3.tableRow1 = this.controller.data.num_constrain;
/* 145 */       this.table4.tableRow1 = this.controller.data.num_constrain;
/* 146 */       this.table1.LPtable1.fireTableStructureChanged();
/* 147 */       this.table2.LPtable1.fireTableStructureChanged();
/* 148 */       this.table3.LPtable1.fireTableStructureChanged();
/* 149 */       this.table4.LPtable1.fireTableStructureChanged();
/* 150 */       this.objectiveFunctionLab.setText("Objective Function: Z = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.objective_coefficient[0])))));
/* 151 */       this.errorInfoLab.setText("");
/*     */     } else {
/* 153 */       this.showResult = false;
/* 154 */       this.f = new Font("Arial", 1, 16);
/* 155 */       this.errorInfoLab.setFont(this.f);
/* 156 */       if (this.controller.data.outputInfo.equalsIgnoreCase("Unbounded")) {
/* 157 */         this.errorInfoLab.setText(String.valueOf(String.valueOf(new StringBuffer("Z is ").append(this.controller.data.outputInfo).append(" for this model."))));
/*     */       } else {
/* 159 */         this.errorInfoLab.setText(this.controller.data.outputInfo);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void updatePanel() {}
/*     */   
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel() {}
/*     */   
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel() {}
/*     */   
/*     */ 
/*     */   protected void finishProcedure() {}
/*     */   
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/*     */     try
/*     */     {
/* 185 */       this.showResult = in.readBoolean();
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/* 189 */     auto();
/*     */   }
/*     */   
/*     */   public void savePrintInfo()
/*     */   {
/* 194 */     this.controller.data.printTable1Data = null;this.controller.data.printTable1Data = new String[this.table1.tableRow1][this.table1.tableCol1];
/* 195 */     for (int i = 0; i < this.table1.tableRow1; i++) {
/* 196 */       for (int j = 0; j < this.table1.tableCol1; j++) {
/* 197 */         this.controller.data.printTable1Data[i][j] = ((String)this.table1.LPtable1.getValueAt(i, j));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void SaveFile(ObjectOutputStream out) {
/*     */     try {
/* 204 */       out.writeBoolean(this.showResult);
/*     */     }
/*     */     catch (Exception localException) {}
/*     */   }
/*     */   
/*     */   public class LPTablePanel1
/*     */     extends JPanel
/*     */   {
/* 212 */     private int tableRow1 = 2;
/* 213 */     private int tableCol1 = 2;
/*     */     private IORLPAutoSolverPane.LPTablePanel1.LPTableModel1 LPtable1;
/* 215 */     private JTable table1 = null;
/*     */     
/* 217 */     private JScrollPane canPanel = null;
/*     */     
/*     */     LPTablePanel1() {
/* 220 */       IORLPAutoSolverPane.this.tablePane1 = new JPanel();
/* 221 */       this.LPtable1 = new IORLPAutoSolverPane.LPTablePanel1.LPTableModel1();
/* 222 */       this.table1 = new JTable(this.LPtable1);
/* 223 */       this.table1.setCellSelectionEnabled(true);
/* 224 */       this.table1.setSelectionMode(1);
/* 225 */       this.table1.setEnabled(false);
/* 226 */       this.canPanel = new JScrollPane(this.table1);
/* 227 */       this.canPanel.setPreferredSize(new Dimension(155, 150));
/* 228 */       setPreferredSize(new Dimension(155, 160));
/* 229 */       add(this.canPanel);
/*     */     }
/*     */     
/*     */     public class LPTableModel1 extends AbstractTableModel { private int i;
/*     */       private int j;
/*     */       
/*     */       public LPTableModel1() {}
/*     */       
/* 237 */       public int getColumnCount() { return IORLPAutoSolverPane.LPTablePanel1.this.tableCol1; }
/*     */       
/*     */ 
/*     */       public int getRowCount()
/*     */       {
/* 242 */         return IORLPAutoSolverPane.LPTablePanel1.this.tableRow1;
/*     */       }
/*     */       
/*     */       public String getColumnName(int col)
/*     */       {
/* 247 */         String str = "";
/*     */         
/* 249 */         if (col == 0) {
/* 250 */           str = String.valueOf(String.valueOf(str)).concat("Variable");
/* 251 */         } else if (col == 1) {
/* 252 */           str = String.valueOf(String.valueOf(str)).concat("Value");
/*     */         }
/* 254 */         return str;
/*     */       }
/*     */       
/*     */       public Object getValueAt(int row, int col)
/*     */       {
/* 259 */         String str = "";
/* 260 */         int r = -1;int c = -1;
/* 261 */         if (col == 0) {
/* 262 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("X").append(row + 1))))));
/* 263 */         } else if (col == 1) {
/* 264 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.this.decfm.format((Double)IORLPAutoSolverPane.this.controller.data.variableValeV.elementAt(row)))));
/*     */         }
/*     */         
/* 267 */         return str;
/*     */       }
/*     */       
/*     */       public Class getColumnClass(int c)
/*     */       {
/* 272 */         return getValueAt(0, c).getClass();
/*     */       }
/*     */       
/*     */       public boolean isCellEditable(int row, int col) {
/* 276 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public class LPTablePanel2 extends JPanel {
/* 282 */     private int tableRow1 = 2;
/* 283 */     private int tableCol1 = 3;
/*     */     private IORLPAutoSolverPane.LPTablePanel2.LPTableModel1 LPtable1;
/* 285 */     private JTable table1 = null;
/*     */     
/* 287 */     private JScrollPane canPanel = null;
/*     */     
/*     */     LPTablePanel2() {
/* 290 */       IORLPAutoSolverPane.this.tablePane1 = new JPanel();
/* 291 */       this.LPtable1 = new IORLPAutoSolverPane.LPTablePanel2.LPTableModel1();
/* 292 */       this.table1 = new JTable(this.LPtable1);
/* 293 */       this.table1.setCellSelectionEnabled(true);
/* 294 */       this.table1.setSelectionMode(1);
/* 295 */       this.table1.setEnabled(false);
/* 296 */       this.canPanel = new JScrollPane(this.table1);
/* 297 */       this.canPanel.setPreferredSize(new Dimension(270, 150));
/* 298 */       setPreferredSize(new Dimension(290, 160));
/* 299 */       add(this.canPanel); }
/*     */     
/*     */     public class LPTableModel1 extends AbstractTableModel { private int i;
/*     */       private int j;
/*     */       
/*     */       public LPTableModel1() {}
/*     */       
/* 306 */       public int getColumnCount() { return IORLPAutoSolverPane.LPTablePanel2.this.tableCol1; }
/*     */       
/*     */ 
/*     */       public int getRowCount()
/*     */       {
/* 311 */         return IORLPAutoSolverPane.LPTablePanel2.this.tableRow1;
/*     */       }
/*     */       
/*     */       public String getColumnName(int col)
/*     */       {
/* 316 */         String str = "";
/* 317 */         if (col == 0) {
/* 318 */           str = String.valueOf(String.valueOf(str)).concat("Current Value");
/* 319 */         } else if (col == 1) {
/* 320 */           str = String.valueOf(String.valueOf(str)).concat("Minimum");
/* 321 */         } else if (col == 2)
/* 322 */           str = String.valueOf(String.valueOf(str)).concat("Maximum");
/* 323 */         return str;
/*     */       }
/*     */       
/*     */       public Object getValueAt(int row, int col)
/*     */       {
/* 328 */         String str = "";
/* 329 */         int r = -1;int c = -1;
/*     */         
/* 331 */         if (col == 0) {
/* 332 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.this.decfm.format(IORLPAutoSolverPane.this.controller.data.coefficientsArr[(row + 1)]))));
/* 333 */         } else if (col == 1) {
/* 334 */           if (IORLPAutoSolverPane.this.controller.data.minArr[(row + 1)] > Integer.MAX_VALUE) {
/* 335 */             str = String.valueOf(String.valueOf(str)).concat("infin");
/* 336 */           } else if (IORLPAutoSolverPane.this.controller.data.minArr[(row + 1)] < Integer.MIN_VALUE) {
/* 337 */             str = String.valueOf(String.valueOf(str)).concat("-infin");
/*     */           } else {
/* 339 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.this.decfm.format(IORLPAutoSolverPane.this.controller.data.minArr[(row + 1)]))));
/*     */           }
/* 341 */         } else if (col == 2) {
/* 342 */           if (IORLPAutoSolverPane.this.controller.data.maxArr[(row + 1)] > Integer.MAX_VALUE) {
/* 343 */             str = String.valueOf(String.valueOf(str)).concat("infin");
/* 344 */           } else if (IORLPAutoSolverPane.this.controller.data.maxArr[(row + 1)] < Integer.MIN_VALUE) {
/* 345 */             str = String.valueOf(String.valueOf(str)).concat("-infin");
/*     */           } else {
/* 347 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.this.decfm.format(IORLPAutoSolverPane.this.controller.data.maxArr[(row + 1)]))));
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 352 */         return str;
/*     */       }
/*     */       
/*     */       public Class getColumnClass(int c)
/*     */       {
/* 357 */         return getValueAt(0, c).getClass();
/*     */       }
/*     */       
/*     */       public boolean isCellEditable(int row, int col) {
/* 361 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public class LPTablePanel3
/*     */     extends JPanel
/*     */   {
/* 369 */     private int tableRow1 = 2;
/* 370 */     private int tableCol1 = 3;
/*     */     private IORLPAutoSolverPane.LPTablePanel3.LPTableModel1 LPtable1;
/* 372 */     private JTable table1 = null;
/*     */     
/* 374 */     private JScrollPane canPanel = null;
/*     */     
/*     */     LPTablePanel3() {
/* 377 */       IORLPAutoSolverPane.this.tablePane1 = new JPanel();
/* 378 */       this.LPtable1 = new IORLPAutoSolverPane.LPTablePanel3.LPTableModel1();
/* 379 */       this.table1 = new JTable(this.LPtable1);
/* 380 */       this.table1.setCellSelectionEnabled(true);
/* 381 */       this.table1.setSelectionMode(1);
/* 382 */       this.table1.setEnabled(false);
/* 383 */       this.canPanel = new JScrollPane(this.table1);
/* 384 */       this.canPanel.setPreferredSize(new Dimension(290, 150));
/* 385 */       setPreferredSize(new Dimension(290, 160));
/* 386 */       add(this.canPanel);
/*     */     }
/*     */     
/*     */     public class LPTableModel1 extends AbstractTableModel { private int i;
/*     */       private int j;
/*     */       
/*     */       public LPTableModel1() {}
/*     */       
/* 394 */       public int getColumnCount() { return IORLPAutoSolverPane.LPTablePanel3.this.tableCol1; }
/*     */       
/*     */ 
/*     */       public int getRowCount()
/*     */       {
/* 399 */         return IORLPAutoSolverPane.LPTablePanel3.this.tableRow1;
/*     */       }
/*     */       
/*     */       public String getColumnName(int col)
/*     */       {
/* 404 */         String str = "";
/* 405 */         if (col == 0) {
/* 406 */           str = String.valueOf(String.valueOf(str)).concat("Constraint");
/* 407 */         } else if (col == 1) {
/* 408 */           str = String.valueOf(String.valueOf(str)).concat("Slack or Surplus");
/* 409 */         } else if (col == 2)
/* 410 */           str = String.valueOf(String.valueOf(str)).concat("Shadow Price");
/* 411 */         return str;
/*     */       }
/*     */       
/*     */       public Object getValueAt(int row, int col)
/*     */       {
/* 416 */         String str = "";
/* 417 */         int r = -1;int c = -1;
/* 418 */         if (col == 0) {
/* 419 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(row + 1)));
/* 420 */         } else if (col == 1) {
/* 421 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.this.decfm.format((Double)IORLPAutoSolverPane.this.controller.data.SlackV.elementAt(row)))));
/* 422 */         } else if (col == 2) {
/* 423 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.this.decfm.format((Double)IORLPAutoSolverPane.this.controller.data.ShadowPriceV.elementAt(row)))));
/*     */         }
/*     */         
/* 426 */         return str;
/*     */       }
/*     */       
/*     */       public Class getColumnClass(int c)
/*     */       {
/* 431 */         return getValueAt(0, c).getClass();
/*     */       }
/*     */       
/*     */       public boolean isCellEditable(int row, int col) {
/* 435 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public class LPTablePanel4 extends JPanel
/*     */   {
/* 442 */     private int tableRow1 = 2;
/* 443 */     private int tableCol1 = 3;
/*     */     private IORLPAutoSolverPane.LPTablePanel4.LPTableModel1 LPtable1;
/* 445 */     private JTable table1 = null;
/*     */     
/* 447 */     private JScrollPane canPanel = null;
/*     */     
/*     */     LPTablePanel4() {
/* 450 */       IORLPAutoSolverPane.this.tablePane1 = new JPanel();
/* 451 */       this.LPtable1 = new IORLPAutoSolverPane.LPTablePanel4.LPTableModel1();
/* 452 */       this.table1 = new JTable(this.LPtable1);
/* 453 */       this.table1.setCellSelectionEnabled(true);
/* 454 */       this.table1.setSelectionMode(1);
/* 455 */       this.table1.setEnabled(false);
/* 456 */       this.canPanel = new JScrollPane(this.table1);
/* 457 */       this.canPanel.setPreferredSize(new Dimension(290, 150));
/* 458 */       setPreferredSize(new Dimension(290, 160));
/* 459 */       add(this.canPanel);
/*     */     }
/*     */     
/*     */     public class LPTableModel1 extends AbstractTableModel { private int i;
/*     */       private int j;
/*     */       
/*     */       public LPTableModel1() {}
/*     */       
/* 467 */       public int getColumnCount() { return IORLPAutoSolverPane.LPTablePanel4.this.tableCol1; }
/*     */       
/*     */ 
/*     */       public int getRowCount()
/*     */       {
/* 472 */         return IORLPAutoSolverPane.LPTablePanel4.this.tableRow1;
/*     */       }
/*     */       
/*     */       public String getColumnName(int col)
/*     */       {
/* 477 */         String str = "";
/* 478 */         if (col == 0) {
/* 479 */           str = String.valueOf(String.valueOf(str)).concat("Current Value");
/* 480 */         } else if (col == 1) {
/* 481 */           str = String.valueOf(String.valueOf(str)).concat("Minimum");
/* 482 */         } else if (col == 2)
/* 483 */           str = String.valueOf(String.valueOf(str)).concat("Maximum");
/* 484 */         return str;
/*     */       }
/*     */       
/*     */       public Object getValueAt(int row, int col)
/*     */       {
/* 489 */         String str = "";
/* 490 */         int r = -1;int c = -1;
/* 491 */         if (col == 0) {
/* 492 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.this.decfm.format(IORLPAutoSolverPane.this.controller.data.RHSCurrentValueArr[(row + 1)]))));
/* 493 */         } else if (col == 1) {
/* 494 */           if (IORLPAutoSolverPane.this.controller.data.minRHSArr[(row + 1)] > Integer.MAX_VALUE) {
/* 495 */             str = String.valueOf(String.valueOf(str)).concat("infin");
/* 496 */           } else if (IORLPAutoSolverPane.this.controller.data.minRHSArr[(row + 1)] < Integer.MIN_VALUE) {
/* 497 */             str = String.valueOf(String.valueOf(str)).concat("-infin");
/*     */           } else {
/* 499 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.this.decfm.format(IORLPAutoSolverPane.this.controller.data.minRHSArr[(row + 1)]))));
/*     */           }
/*     */         }
/* 502 */         else if (col == 2) {
/* 503 */           if (IORLPAutoSolverPane.this.controller.data.maxRHSArr[(row + 1)] > Integer.MAX_VALUE) {
/* 504 */             str = String.valueOf(String.valueOf(str)).concat("infin");
/* 505 */           } else if (IORLPAutoSolverPane.this.controller.data.maxRHSArr[(row + 1)] < Integer.MIN_VALUE) {
/* 506 */             str = String.valueOf(String.valueOf(str)).concat("-infin");
/*     */           } else {
/* 508 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.this.decfm.format(IORLPAutoSolverPane.this.controller.data.maxRHSArr[(row + 1)]))));
/*     */           }
/*     */         }
/*     */         
/* 512 */         return str;
/*     */       }
/*     */       
/*     */       public Class getColumnClass(int c)
/*     */       {
/* 517 */         return getValueAt(0, c).getClass();
/*     */       }
/*     */       
/*     */       public boolean isCellEditable(int row, int col) {
/* 521 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPAutoSolverPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */