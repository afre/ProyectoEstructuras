/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ 
/*     */ public class IORNLGeneticCanvasPanel extends JPanel
/*     */ {
/*  14 */   public JTable table = null;
/*  15 */   public String[] colName = { "Iter.", "Best Solution", "Fitness" };
/*  16 */   public int tableRow = 4;
/*     */   
/*     */   public int tableCol;
/*  19 */   public JTable table1 = null;
/*  20 */   public String[] colName1 = { "Population", "Solution", "Fitness" };
/*  21 */   public int tableRow1 = 10;
/*     */   
/*     */   public int tableCol1;
/*  24 */   public JTable table2 = null;
/*  25 */   public String[] colName2 = { "Children", "Solution", "Fitness" };
/*  26 */   public int tableRow2 = 6;
/*     */   
/*     */   public int tableCol2;
/*  29 */   public IORNLGeneticCanvasPanel.TSPTableModel tspModel = null;
/*  30 */   public IORNLGeneticCanvasPanel.TSPTableModel1 tspModel1 = null;
/*  31 */   public IORNLGeneticCanvasPanel.TSPTableModel2 tspModel2 = null;
/*     */   
/*  33 */   private Color default_color = Color.lightGray;
/*  34 */   private Color select_color = Color.white;
/*     */   
/*  36 */   private IORState iorstate = null;
/*     */   
/*  38 */   public JScrollPane canPanel = null;
/*  39 */   private JScrollPane canPanel1 = null;
/*  40 */   private JScrollPane canPanel2 = null;
/*     */   
/*  42 */   private IORNLGeneticPanel actionPanel = null;
/*     */   
/*  44 */   private boolean is_panel_enabled = true;
/*     */   
/*  46 */   private JPanel tablePanel = null;
/*     */   
/*  48 */   private JPanel tablePanel1 = null;
/*  49 */   private JPanel butPane = null;
/*  50 */   private JButton interactiveBut = null;
/*  51 */   private JButton nextBut = null;
/*  52 */   private JButton backBut = null;
/*  53 */   private JButton autoBut = null;
/*  54 */   private JButton resetBut = null;
/*  55 */   private JLabel finishInfoLab = null;
/*  56 */   private JLabel resultLab = null;
/*     */   
/*  58 */   private static final int canWid = 430;
/*  59 */   private static final int canHei = 150;
/*  60 */   public boolean scrollShow = false;
/*     */   
/*  62 */   private static final int canWid1 = 400;
/*  63 */   private static final int canHei1 = 181;
/*     */   
/*  65 */   private static final int canWid2 = 400;
/*  66 */   private static final int canHei2 = 117;
/*     */   
/*     */   private double bestDis;
/*     */   private String Solution;
/*     */   
/*     */   public IORNLGeneticCanvasPanel(IORNLGeneticPanel p)
/*     */   {
/*  73 */     this.actionPanel = p;
/*  74 */     this.iorstate = p.getState();
/*     */     
/*     */ 
/*     */ 
/*  78 */     this.tableCol = this.colName.length;
/*  79 */     this.tspModel = new IORNLGeneticCanvasPanel.TSPTableModel(null);
/*  80 */     this.table = new JTable(this.tspModel);
/*  81 */     this.table.setCellSelectionEnabled(false);
/*  82 */     this.table.setSelectionMode(0);
/*  83 */     this.table.setEnabled(false);
/*  84 */     TableColumn column = this.table.getColumnModel().getColumn(0);
/*  85 */     column.setPreferredWidth(20);
/*  86 */     column = this.table.getColumnModel().getColumn(1);
/*  87 */     column.setPreferredWidth(150);
/*  88 */     column = this.table.getColumnModel().getColumn(2);
/*  89 */     column.setPreferredWidth(150);
/*     */     
/*     */ 
/*     */ 
/*  93 */     this.tableCol1 = this.colName1.length;
/*  94 */     this.tspModel1 = new IORNLGeneticCanvasPanel.TSPTableModel1(null);
/*  95 */     this.table1 = new JTable(this.tspModel1);
/*  96 */     this.table1.setCellSelectionEnabled(false);
/*  97 */     this.table1.setSelectionMode(0);
/*  98 */     this.table1.setEnabled(false);
/*     */     
/* 100 */     this.tableCol2 = this.colName2.length;
/* 101 */     this.tspModel2 = new IORNLGeneticCanvasPanel.TSPTableModel2(null);
/* 102 */     this.table2 = new JTable(this.tspModel2);
/* 103 */     this.table2.setCellSelectionEnabled(false);
/* 104 */     this.table2.setSelectionMode(0);
/* 105 */     this.table2.setEnabled(false);
/* 106 */     this.table2.setGridColor(Color.black);
/*     */     
/* 108 */     column = this.table2.getColumnModel().getColumn(0);
/* 109 */     column.setPreferredWidth(200);
/*     */     
/* 111 */     column = this.table1.getColumnModel().getColumn(0);
/* 112 */     column.setPreferredWidth(140);
/* 113 */     column = this.table1.getColumnModel().getColumn(1);
/* 114 */     column.setPreferredWidth(50);
/*     */     
/* 116 */     column = this.table2.getColumnModel().getColumn(0);
/* 117 */     column.setPreferredWidth(140);
/* 118 */     column = this.table2.getColumnModel().getColumn(1);
/* 119 */     column.setPreferredWidth(50);
/*     */     
/*     */ 
/* 122 */     this.canPanel = new JScrollPane(this.table);
/* 123 */     this.canPanel.setPreferredSize(new Dimension(430, 150));
/* 124 */     this.canPanel.setMaximumSize(new Dimension(430, 150));
/* 125 */     this.canPanel.setAlignmentX(0.5F);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 130 */     this.canPanel.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener() {
/*     */       public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e) {
/* 132 */         if (IORNLGeneticCanvasPanel.this.scrollShow) {
/* 133 */           IORNLGeneticCanvasPanel.this.canPanel.getVerticalScrollBar().setValue(IORNLGeneticCanvasPanel.this.canPanel.getVerticalScrollBar().getMaximum());
/* 134 */           IORNLGeneticCanvasPanel.this.scrollShow = false;
/*     */         }
/*     */         
/*     */       }
/*     */       
/* 139 */     });
/* 140 */     this.canPanel1 = new JScrollPane(this.table1);
/* 141 */     this.canPanel1.setPreferredSize(new Dimension(400, 181));
/* 142 */     this.canPanel1.setMaximumSize(new Dimension(400, 181));
/* 143 */     this.canPanel1.setAlignmentX(0.5F);
/*     */     
/*     */ 
/*     */ 
/* 147 */     this.canPanel2 = new JScrollPane(this.table2);
/* 148 */     this.canPanel2.setPreferredSize(new Dimension(400, 117));
/* 149 */     this.canPanel2.setMaximumSize(new Dimension(400, 117));
/* 150 */     this.canPanel2.setAlignmentX(0.5F);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 157 */     this.interactiveBut = new JButton("Interactive");
/* 158 */     this.nextBut = new JButton("Next");
/* 159 */     this.backBut = new JButton("Back");
/* 160 */     this.backBut.setEnabled(false);
/* 161 */     this.nextBut.setEnabled(false);
/* 162 */     this.autoBut = new JButton("Auto");
/* 163 */     this.resetBut = new JButton("Reset");
/* 164 */     this.finishInfoLab = new JLabel("");
/* 165 */     this.resultLab = new JLabel("");
/* 166 */     this.resultLab.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
/* 167 */     this.finishInfoLab.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
/*     */     
/* 169 */     this.backBut.addActionListener(new java.awt.event.ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {}
/*     */ 
/* 173 */     });
/* 174 */     this.nextBut.addActionListener(new java.awt.event.ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {}
/*     */ 
/* 178 */     });
/* 179 */     this.resetBut.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 181 */         IORNLGeneticCanvasPanel.this.nextBut.setEnabled(false);
/* 182 */         IORNLGeneticCanvasPanel.this.autoBut.setEnabled(true);
/* 183 */         IORNLGeneticCanvasPanel.this.interactiveBut.setEnabled(true);
/* 184 */         IORNLGeneticCanvasPanel.this.reset();
/*     */       }
/*     */       
/* 187 */     });
/* 188 */     this.interactiveBut.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 190 */         IORNLGeneticCanvasPanel.this.nextBut.setEnabled(true);
/* 191 */         IORNLGeneticCanvasPanel.this.autoBut.setEnabled(false);
/* 192 */         IORNLGeneticCanvasPanel.this.interactiveBut.setEnabled(false);
/* 193 */         IORNLGeneticCanvasPanel.this.reset();
/*     */       }
/*     */       
/* 196 */     });
/* 197 */     this.autoBut.addActionListener(new java.awt.event.ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {}
/*     */ 
/* 201 */     });
/* 202 */     this.tablePanel = new JPanel();
/* 203 */     this.butPane = new JPanel();
/* 204 */     this.butPane.setLayout(new java.awt.FlowLayout());
/* 205 */     this.butPane.add(this.backBut);
/* 206 */     this.butPane.add(this.interactiveBut);
/* 207 */     this.butPane.add(this.nextBut);
/* 208 */     this.butPane.add(this.autoBut);
/* 209 */     this.butPane.add(this.resetBut);
/* 210 */     this.tablePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 40, 10, 40));
/*     */     
/* 212 */     this.tablePanel1 = new JPanel();
/* 213 */     this.tablePanel1.setLayout(new javax.swing.BoxLayout(this.tablePanel1, 1));
/* 214 */     this.tablePanel1.add(this.canPanel1);
/* 215 */     this.tablePanel1.add(this.canPanel2);
/*     */     
/* 217 */     this.tablePanel = new JPanel();
/* 218 */     this.tablePanel.setLayout(new java.awt.BorderLayout());
/*     */     
/* 220 */     this.tablePanel.add(this.canPanel, "Center");
/* 221 */     this.tablePanel.add(this.resultLab, "South");
/*     */     
/* 223 */     setLayout(new java.awt.BorderLayout());
/* 224 */     add(this.tablePanel, "North");
/* 225 */     add(this.tablePanel1, "Center");
/*     */     
/* 227 */     setBackground(Color.white);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void countCurrentSolution()
/*     */   {
/* 236 */     double temp = 0.0D;
/* 237 */     if (this.table.getRowCount() == 0) {
/* 238 */       this.resultLab.setText("");
/* 239 */       return;
/*     */     }
/* 241 */     if (this.table.getRowCount() == 1) {
/* 242 */       this.bestDis = this.actionPanel.controller.nldata.best[0].getFitness();
/* 243 */       this.resultLab.setText(String.valueOf(String.valueOf(new StringBuffer("Best f(x) = ").append(this.bestDis).append(" Best Solution = ").append(this.table.getValueAt(0, 1)))));
/*     */       
/* 245 */       this.actionPanel.controller.nldata.bestSolution = this.resultLab.getText();
/* 246 */       this.actionPanel.controller.solver.initPrintData(getOperation(new java.util.Vector()), this.actionPanel.controller.nldata);
/* 247 */       return;
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/* 273 */     this.bestDis = Double.valueOf((String)this.table.getValueAt(this.table.getRowCount() - 1, 2)).doubleValue();
/*     */     
/* 275 */     this.Solution = ((String)this.table.getValueAt(this.table.getRowCount() - 1, 1));
/* 276 */     this.resultLab.setText(String.valueOf(String.valueOf(new StringBuffer("Best f(x) = ").append(this.bestDis).append(" Best Solution = ").append(this.Solution))));
/*     */     
/*     */ 
/* 279 */     this.actionPanel.controller.nldata.bestSolution = this.resultLab.getText();
/* 280 */     this.actionPanel.controller.solver.initPrintData(getOperation(new java.util.Vector()), this.actionPanel.controller.nldata);
/*     */   }
/*     */   
/*     */   public IOROperation getOperation(java.util.Vector vec) {
/* 284 */     IOROperation operation = new IOROperation(50701, vec);
/*     */     
/* 286 */     return operation;
/*     */   }
/*     */   
/*     */   public void setColor()
/*     */   {
/* 291 */     int[] a = { 0, 1, 2, 3, 4, 5 };
/* 292 */     int[] b = { 0, 0, 0, 0, 0, 0 };
/* 293 */     Color green = new Color(0, 96, 0);
/* 294 */     Color[] tableBackCol = { green, green, Color.red, Color.red, Color.blue, Color.blue };
/* 295 */     setTableBackColor(a, b, this.table2, this.colName2, tableBackCol);
/*     */     
/* 297 */     int[] c = new int[10];
/* 298 */     int[] d = new int[10];
/* 299 */     Color[] tableBackCol1 = new Color[10];
/* 300 */     for (int i = 0; i < 10; i++) {
/* 301 */       tableBackCol1[i] = Color.magenta;
/* 302 */       c[i] = i;
/* 303 */       d[i] = 0;
/*     */     }
/*     */     
/* 306 */     for (int i = 0; i < 6; i++) {
/* 307 */       int t = this.actionPanel.controller.nldata.parentindex[i];
/* 308 */       if (i == 0) { tableBackCol1[t] = green;
/* 309 */       } else if (i == 1) { tableBackCol1[t] = green;
/* 310 */       } else if (i == 2) { tableBackCol1[t] = Color.red;
/* 311 */       } else if (i == 3) { tableBackCol1[t] = Color.red;
/* 312 */       } else if (i == 4) { tableBackCol1[t] = Color.blue;
/* 313 */       } else if (i == 5) tableBackCol1[t] = Color.blue;
/*     */     }
/* 315 */     setTableBackColor(c, d, this.table1, this.colName1, tableBackCol1);
/*     */   }
/*     */   
/*     */   public void setTableBackColor(int[] rowNum, int[] colNum, JTable currentTab, String[] currentS, Color[] cr) {
/* 319 */     javax.swing.table.DefaultTableCellRenderer tcr = new javax.swing.table.DefaultTableCellRenderer() {
/*     */       private final Color[] val$cr;
/*     */       private final JTable val$currentTab;
/*     */       
/* 323 */       public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { for (int i = 0; i < IORNLGeneticCanvasPanel.this.length; i++)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 333 */           if (IORNLGeneticCanvasPanel.this[i] == row) {
/* 334 */             setForeground(this.val$cr[i]);
/*     */           }
/*     */         }
/* 337 */         return super.getTableCellRendererComponent(this.val$currentTab, value, isSelected, hasFocus, row, column);
/*     */       }
/*     */     };
/* 340 */     for (int i = 0; i < currentS.length; i++) {
/* 341 */       currentTab.getColumn(currentS[i]).setCellRenderer(tcr);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/* 349 */     this.resultLab.setText("");
/* 350 */     this.backBut.setEnabled(false); }
/*     */   
/*     */   private class TSPTableModel extends javax.swing.table.AbstractTableModel { private int i;
/*     */     
/* 354 */     TSPTableModel(IORNLGeneticCanvasPanel..8 x$1) { this(); }
/*     */     
/*     */ 
/*     */     public int getColumnCount()
/*     */     {
/* 359 */       return IORNLGeneticCanvasPanel.this.tableCol;
/*     */     }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 364 */       return IORNLGeneticCanvasPanel.this.tableRow;
/*     */     }
/*     */     
/*     */ 
/*     */     public String getColumnName(int col)
/*     */     {
/* 370 */       String str = "";
/* 371 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORNLGeneticCanvasPanel.this.colName[col])));
/* 372 */       return str;
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 377 */       String str = "";String xstr = "";
/* 378 */       java.text.DecimalFormat decfm = new java.text.DecimalFormat("#.###");
/* 379 */       int r = -1;int c = -1;
/* 380 */       if ((col == 0) && (row == 0)) {
/* 381 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(1)));
/*     */       }
/* 383 */       else if (col == 0) {
/* 384 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(row + 1)));
/*     */       } else {
/* 386 */         if (col == 1) {
/* 387 */           xstr = "( ";
/* 388 */           int[] temp = IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.best[row].getVariables();
/* 389 */           for (this.i = 0; this.i < IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.num_variable; this.i += 1) {
/* 390 */             xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(decfm.format(temp[this.i]))));
/* 391 */             if (this.i < IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.num_variable - 1) {
/* 392 */               xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*     */             }
/*     */           }
/* 395 */           xstr = String.valueOf(String.valueOf(xstr)).concat(" )");
/* 396 */           return xstr;
/*     */         }
/* 398 */         if (col == 2) {
/* 399 */           return "".concat(String.valueOf(String.valueOf(IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.best[row].getFitness())));
/*     */         }
/*     */         
/* 402 */         if (col != 3)
/*     */         {
/*     */ 
/* 405 */           if (col != 4)
/*     */           {
/*     */ 
/* 408 */             if (col != 5) {} }
/*     */         }
/*     */       }
/* 411 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c) {
/* 415 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int col) {
/* 419 */       if (!IORNLGeneticCanvasPanel.this.is_panel_enabled)
/* 420 */         return false;
/* 421 */       if (col == 0) {
/* 422 */         return false;
/*     */       }
/* 424 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */     private int j;
/* 429 */     public void setValueAt(Object value, int row, int col) { String input = (String)value; }
/*     */     
/*     */     private TSPTableModel() {} }
/*     */   
/*     */   private class TSPTableModel1 extends javax.swing.table.AbstractTableModel { private int i;
/*     */     
/* 435 */     TSPTableModel1(IORNLGeneticCanvasPanel..8 x$1) { this(); }
/*     */     
/*     */ 
/*     */     public int getColumnCount()
/*     */     {
/* 440 */       return IORNLGeneticCanvasPanel.this.tableCol1;
/*     */     }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 445 */       return IORNLGeneticCanvasPanel.this.tableRow1;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col)
/*     */     {
/* 450 */       String str = "";
/* 451 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORNLGeneticCanvasPanel.this.colName1[col])));
/* 452 */       return str;
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 457 */       String str = "";
/* 458 */       int r = -1;int c = -1;
/*     */       
/*     */ 
/*     */ 
/* 462 */       if (col == 0) {
/* 463 */         String xstr = "( ";
/* 464 */         byte[][] temp = IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.store[row].getGene();
/* 465 */         for (this.i = 0; this.i < IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.num_variable; this.i += 1) {
/* 466 */           for (this.j = 0; this.j < temp[this.i].length; this.j += 1) {
/* 467 */             xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(temp[this.i][this.j]))))));
/*     */           }
/*     */           
/* 470 */           if (this.i < IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.num_variable - 1) {
/* 471 */             xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*     */           }
/*     */         }
/* 474 */         xstr = String.valueOf(String.valueOf(xstr)).concat(" )");
/* 475 */         return xstr;
/*     */       }
/* 477 */       if (col == 1) {
/* 478 */         String xstr = "( ";
/* 479 */         int[] temp = IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.store[row].getVariables();
/* 480 */         for (this.i = 0; this.i < IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.num_variable; this.i += 1) {
/* 481 */           xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(temp[this.i])));
/* 482 */           if (this.i < IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.num_variable - 1) {
/* 483 */             xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*     */           }
/*     */         }
/* 486 */         xstr = String.valueOf(String.valueOf(xstr)).concat(" )");
/* 487 */         return xstr;
/*     */       }
/* 489 */       if (col == 2) {
/* 490 */         return "".concat(String.valueOf(String.valueOf(IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.store[row].getFitness())));
/*     */       }
/* 492 */       if (col != 3)
/*     */       {
/*     */ 
/* 495 */         if (col != 4)
/*     */         {
/*     */ 
/* 498 */           if (col == 5) {}
/*     */         }
/*     */       }
/* 501 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c) {
/* 505 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int col) {
/* 509 */       if (!IORNLGeneticCanvasPanel.this.is_panel_enabled)
/* 510 */         return false;
/* 511 */       if (col == 0) {
/* 512 */         return false;
/*     */       }
/* 514 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */     private int j;
/* 519 */     public void setValueAt(Object value, int row, int col) { String input = (String)value; }
/*     */     
/*     */     private TSPTableModel1() {}
/*     */   }
/*     */   
/* 524 */   private class TSPTableModel2 extends javax.swing.table.AbstractTableModel { TSPTableModel2(IORNLGeneticCanvasPanel..8 x$1) { this(); }
/*     */     
/*     */     private int i;
/*     */     private int j;
/*     */     public int getColumnCount() {
/* 529 */       return IORNLGeneticCanvasPanel.this.tableCol2;
/*     */     }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 534 */       return IORNLGeneticCanvasPanel.this.tableRow2;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col)
/*     */     {
/* 539 */       String str = "";
/* 540 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORNLGeneticCanvasPanel.this.colName2[col])));
/* 541 */       return str;
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 546 */       String str = "";
/* 547 */       int r = -1;int c = -1;
/* 548 */       if (col == 0) {
/* 549 */         String xstr = "( ";
/* 550 */         byte[][] temp = IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.bytag[row];
/* 551 */         byte[][] show = IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.children[row].getGene();
/*     */         
/*     */ 
/* 554 */         for (this.i = 0; this.i < IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.num_variable; this.i += 1) {
/* 555 */           for (this.j = 0; this.j < temp[this.i].length; this.j += 1) {
/* 556 */             String zstr = "".concat(String.valueOf(String.valueOf(temp[this.i][this.j])));
/* 557 */             if ("1".equals(zstr)) {
/* 558 */               xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("[").append(show[this.i][this.j]).append("]"))))));
/*     */             }
/*     */             else {
/* 561 */               xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(show[this.i][this.j]))))));
/*     */             }
/*     */           }
/*     */           
/* 565 */           if (this.i < IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.num_variable - 1) {
/* 566 */             xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*     */           }
/*     */         }
/* 569 */         xstr = String.valueOf(String.valueOf(xstr)).concat(" )");
/* 570 */         return xstr;
/*     */       }
/* 572 */       if (col == 1) {
/* 573 */         String xstr = "( ";
/* 574 */         int[] temp = IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.children[row].getVariables();
/* 575 */         for (this.i = 0; this.i < IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.num_variable; this.i += 1) {
/* 576 */           xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(temp[this.i])));
/* 577 */           if (this.i < IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.num_variable - 1) {
/* 578 */             xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*     */           }
/*     */         }
/* 581 */         xstr = String.valueOf(String.valueOf(xstr)).concat(" )");
/* 582 */         return xstr;
/*     */       }
/*     */       
/* 585 */       if (col == 2) {
/* 586 */         return "".concat(String.valueOf(String.valueOf(IORNLGeneticCanvasPanel.this.actionPanel.controller.nldata.children[row].getFitness())));
/*     */       }
/* 588 */       if (col != 3)
/*     */       {
/*     */ 
/* 591 */         if (col != 4)
/*     */         {
/*     */ 
/* 594 */           if (col == 5) {}
/*     */         }
/*     */       }
/* 597 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c) {
/* 601 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int col) {
/* 605 */       if (!IORNLGeneticCanvasPanel.this.is_panel_enabled)
/* 606 */         return false;
/* 607 */       if (col == 0) {
/* 608 */         return false;
/*     */       }
/* 610 */       return true;
/*     */     }
/*     */     
/*     */     public void setValueAt(Object value, int row, int col)
/*     */     {
/* 615 */       String input = (String)value;
/*     */     }
/*     */     
/*     */     private TSPTableModel2() {}
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNLGeneticCanvasPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */