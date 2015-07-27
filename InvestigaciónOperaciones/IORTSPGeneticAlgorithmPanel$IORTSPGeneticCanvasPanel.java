/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
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
/*     */ public class IORTSPGeneticAlgorithmPanel$IORTSPGeneticCanvasPanel
/*     */   extends JPanel
/*     */ {
/*     */   public JTable table;
/*     */   public String[] colName;
/*     */   public int tableRow;
/*     */   public int tableCol;
/*     */   public Color[] chromosomeColor;
/*     */   public int[][][] table1ColorInfo;
/*     */   public int[][][] table2ColorInfo;
/*     */   public JTable table1;
/*     */   public String[] colName1;
/*     */   public int tableRow1;
/*     */   public int tableCol1;
/*     */   public JTable table2;
/*     */   public String[] colName2;
/*     */   public int tableRow2;
/*     */   public int tableCol2;
/*     */   public IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.TSPTableModel tspModel;
/*     */   public IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.TSPTableModel1 tspModel1;
/*     */   public IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.TSPTableModel2 tspModel2;
/*     */   private Color default_color;
/*     */   private Color select_color;
/* 244 */   public static final int STATE_NONE = -1;
/*     */   
/*     */   private int state;
/*     */   
/*     */   private IORState iorstate;
/*     */   
/*     */   private JScrollPane canPanel;
/*     */   
/*     */   private JScrollPane canPanel1;
/*     */   
/*     */   private JScrollPane canPanel2;
/*     */   
/*     */   private IORTSPActionPanel actionPanel;
/*     */   
/*     */   private boolean is_panel_enabled;
/*     */   
/*     */   private JPanel tablePanel;
/*     */   
/*     */   private JPanel tablePanel1;
/*     */   private JPanel butPane;
/*     */   private JButton nextBut;
/*     */   private JButton backBut;
/*     */   private JButton resetBut;
/*     */   private JLabel finishInfoLab;
/*     */   private JLabel resultLab;
/* 269 */   private static final int canWid = 300;
/* 270 */   private static final int canHei = 250;
/*     */   
/* 272 */   private static final int canWid1 = 300;
/* 273 */   private static final int canHei1 = 250;
/* 274 */   private static final int canWid2 = 300;
/* 275 */   private static final int canHei2 = 220;
/*     */   public int colorRow;
/*     */   public int colorCol;
/*     */   public boolean workIsFinish;
/*     */   public double bestDis;
/*     */   public String Solution;
/*     */   private final IORTSPGeneticAlgorithmPanel this$0;
/*     */   
/*     */   public IORTSPGeneticAlgorithmPanel$IORTSPGeneticCanvasPanel(IORTSPGeneticAlgorithmPanel this$0, IORTSPActionPanel p)
/*     */   {
/* 285 */     this.this$0 = this$0;this.table = null;this.colName = new String[] { "Iteration", "Best Solution", "Fitness" };this.tableRow = 0;this.chromosomeColor = new Color[] { Color.green, Color.blue, Color.red, Color.black };this.table1ColorInfo = new int[10][3][2];this.table2ColorInfo = new int[6][3][2];this.table1 = null;this.colName1 = new String[] { "Population", "Fitness" };this.tableRow1 = 0;this.table2 = null;this.colName2 = new String[] { "Children", "Fitness" };this.tableRow2 = 0;this.tspModel = null;this.tspModel1 = null;this.tspModel2 = null;this.default_color = Color.lightGray;this.select_color = Color.white;this.state = -1;this.iorstate = null;this.canPanel = null;this.canPanel1 = null;this.canPanel2 = null;this.actionPanel = null;this.is_panel_enabled = true;this.tablePanel = null;this.tablePanel1 = null;this.butPane = null;this.nextBut = null;this.backBut = null;this.resetBut = null;this.finishInfoLab = null;this.resultLab = null;this.workIsFinish = false;this.bestDis = 0.0D;this.Solution = "";
/* 286 */     this.actionPanel = p;
/* 287 */     this.iorstate = p.getState();
/*     */     
/* 289 */     this.tableCol = this.colName.length;
/* 290 */     this.tspModel = new IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.TSPTableModel(null);
/* 291 */     this.table = new JTable(this.tspModel);
/* 292 */     this.table.setCellSelectionEnabled(false);
/* 293 */     this.table.setSelectionMode(0);
/* 294 */     this.table.setEnabled(false);
/*     */     
/* 296 */     this.tableCol1 = this.colName1.length;
/* 297 */     this.tspModel1 = new IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.TSPTableModel1(null);
/* 298 */     this.table1 = new JTable(this.tspModel1);
/* 299 */     this.table1.setCellSelectionEnabled(false);
/* 300 */     this.table1.setSelectionMode(0);
/* 301 */     this.table1.setEnabled(false);
/*     */     
/* 303 */     this.tableCol2 = this.colName2.length;
/* 304 */     this.tspModel2 = new IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.TSPTableModel2(null);
/* 305 */     this.table2 = new JTable(this.tspModel2);
/* 306 */     this.table2.setCellSelectionEnabled(false);
/* 307 */     this.table2.setSelectionMode(0);
/* 308 */     this.table2.setEnabled(false);
/* 309 */     this.table2.setGridColor(Color.black);
/*     */     
/*     */ 
/* 312 */     this.canPanel = new JScrollPane(this.table);
/* 313 */     this.canPanel.setPreferredSize(new Dimension(300, 250));
/* 314 */     this.canPanel.setMaximumSize(new Dimension(300, 250));
/* 315 */     this.canPanel.setAlignmentX(0.5F);
/* 316 */     this.canPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 40));
/*     */     
/* 318 */     this.canPanel1 = new JScrollPane(this.table1);
/* 319 */     this.canPanel1.setPreferredSize(new Dimension(300, 250));
/* 320 */     this.canPanel1.setMaximumSize(new Dimension(300, 250));
/* 321 */     this.canPanel1.setAlignmentX(0.5F);
/*     */     
/*     */ 
/*     */ 
/* 325 */     this.canPanel2 = new JScrollPane(this.table2);
/* 326 */     this.canPanel2.setPreferredSize(new Dimension(300, 220));
/* 327 */     this.canPanel2.setMaximumSize(new Dimension(300, 220));
/* 328 */     this.canPanel2.setAlignmentX(0.5F);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 336 */     this.nextBut = new JButton("Next");
/* 337 */     this.backBut = new JButton("Back");
/* 338 */     this.backBut.setEnabled(false);
/*     */     
/* 340 */     this.resetBut = new JButton("Reset");
/* 341 */     this.finishInfoLab = new JLabel("");
/* 342 */     this.resultLab = new JLabel("");
/* 343 */     this.resultLab.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
/* 344 */     this.finishInfoLab.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
/*     */     
/* 346 */     for (int i = 0; i < this$0.controller.data.GA_Children_TotalLine; i++) {
/* 347 */       this.table2ColorInfo[i][0][0] = i;
/* 348 */       this.table2ColorInfo[i][0][1] = (i / 2);
/*     */     }
/*     */     
/*     */ 
/* 352 */     this.backBut.addActionListener(new IORTSPGeneticAlgorithmPanel.1((IORTSPGeneticCanvasPanel)this));
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
/* 377 */     this.nextBut.addActionListener(new IORTSPGeneticAlgorithmPanel.2((IORTSPGeneticCanvasPanel)this));
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
/* 412 */     this.resetBut.addActionListener(new IORTSPGeneticAlgorithmPanel.3((IORTSPGeneticCanvasPanel)this));
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
/* 434 */     this.tablePanel = new JPanel();
/* 435 */     this.butPane = new JPanel();
/* 436 */     this.butPane.setLayout(new FlowLayout());
/* 437 */     this.butPane.add(this.backBut);
/*     */     
/* 439 */     this.butPane.add(this.nextBut);
/*     */     
/* 441 */     this.butPane.add(this.resetBut);
/* 442 */     this.tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
/*     */     
/* 444 */     JLabel instrucLab = new JLabel("Mutations are denoted by [  ].");
/* 445 */     instrucLab.setForeground(Color.blue);
/* 446 */     instrucLab.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
/* 447 */     JPanel childPane = new JPanel();
/* 448 */     childPane.setLayout(new BorderLayout());
/* 449 */     childPane.add(this.canPanel2, "Center");
/* 450 */     childPane.add(instrucLab, "South");
/*     */     
/* 452 */     this.tablePanel1 = new JPanel();
/* 453 */     this.tablePanel1.setLayout(new FlowLayout());
/* 454 */     this.tablePanel1.add(this.canPanel1);
/* 455 */     this.tablePanel1.add(childPane);
/*     */     
/*     */ 
/* 458 */     this.tablePanel = new JPanel();
/* 459 */     this.tablePanel.setLayout(new BorderLayout());
/*     */     
/* 461 */     this.tablePanel.add(this.canPanel, "Center");
/* 462 */     this.tablePanel.add(this.resultLab, "South");
/*     */     
/* 464 */     setLayout(new BorderLayout());
/* 465 */     add(this.tablePanel, "North");
/* 466 */     add(this.tablePanel1, "Center");
/* 467 */     add(this.butPane, "South");
/* 468 */     setBackground(Color.white);
/*     */     
/* 470 */     this.table.setName("table");
/* 471 */     this.table1.setName("table1");
/* 472 */     setTableFormat();
/*     */   }
/*     */   
/*     */ 
/*     */   public void initNextPrintData(int tpye)
/*     */   {
/* 478 */     if (tpye == 1) {
/* 479 */       if (this.this$0.controller.data.GA_Print_BestSolution_Data != null) this.this$0.controller.data.GA_Print_BestSolution_Data = null;
/* 480 */       this.this$0.controller.data.GA_Print_BestSolution_Data = new String[this.tableRow][this.tableCol];
/*     */       
/* 482 */       for (int i = 0; i < this.tableRow; i++) {
/* 483 */         for (int j = 0; j < this.tableCol; j++) {
/* 484 */           this.this$0.controller.data.GA_Print_BestSolution_Data[i][j] = this.table.getValueAt(i, j).toString();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 490 */       if (this.this$0.controller.data.GA_Print_Pop_Data == null) this.this$0.controller.data.GA_Print_Pop_Data = new Vector();
/* 491 */       String[][] tempV = new String[this.tableRow1][this.tableCol1];
/* 492 */       for (int i = 0; i < this.tableRow1; i++) {
/* 493 */         for (int j = 0; j < this.tableCol1; j++) {
/* 494 */           tempV[i][j] = this.table1.getValueAt(i, j).toString();
/*     */         }
/*     */       }
/* 497 */       this.this$0.controller.data.GA_Print_Pop_Data.addElement(tempV);
/*     */       
/*     */ 
/*     */ 
/* 501 */       if (this.this$0.controller.data.GA_Print_Child_Data == null) this.this$0.controller.data.GA_Print_Child_Data = new Vector();
/* 502 */       tempV = null;
/* 503 */       tempV = new String[this.tableRow2][this.tableCol2];
/* 504 */       for (int i = 0; i < this.tableRow2; i++) {
/* 505 */         for (int j = 0; j < this.tableCol2; j++) {
/* 506 */           tempV[i][j] = this.table2.getValueAt(i, j).toString();
/*     */         }
/*     */       }
/* 509 */       this.this$0.controller.data.GA_Print_Child_Data.addElement(tempV);
/*     */       
/*     */ 
/* 512 */       if (this.this$0.controller.data.GA_Print_P_Connection_C_Data == null) this.this$0.controller.data.GA_Print_P_Connection_C_Data = new Vector();
/* 513 */       int[] colorInt = new int[this.tableRow1];
/* 514 */       for (int i = 0; i < colorInt.length; i++) {
/* 515 */         colorInt[i] = this.table1ColorInfo[i][0][1];
/*     */       }
/* 517 */       this.this$0.controller.data.GA_Print_P_Connection_C_Data.addElement(colorInt);
/*     */       
/* 519 */       if (this.this$0.controller.data.GA_Print_ChildNum == null) this.this$0.controller.data.GA_Print_ChildNum = new Vector();
/* 520 */       this.this$0.controller.data.GA_Print_ChildNum = this.this$0.controller.data.GA_Total_SelectParent;
/*     */       
/* 522 */       this.this$0.controller.solver.initPrintData(this.this$0.getOperation(new Vector()), this.this$0.controller.data);
/*     */     } else {
/* 524 */       if (this.this$0.controller.data.GA_Print_BestSolution_Data != null) this.this$0.controller.data.GA_Print_BestSolution_Data = null;
/* 525 */       this.this$0.controller.data.GA_Print_BestSolution_Data = new String[this.tableRow][this.tableCol];
/*     */       
/* 527 */       for (int i = 0; i < this.tableRow; i++) {
/* 528 */         for (int j = 0; j < this.tableCol; j++) {
/* 529 */           this.this$0.controller.data.GA_Print_BestSolution_Data[i][j] = this.table.getValueAt(i, j).toString();
/*     */         }
/*     */       }
/* 532 */       if (this.this$0.controller.data.GA_Print_Pop_Data != null)
/* 533 */         this.this$0.controller.data.GA_Print_Pop_Data.removeElementAt(this.this$0.controller.data.GA_Print_Pop_Data.size() - 1);
/* 534 */       if (this.this$0.controller.data.GA_Print_Child_Data != null)
/* 535 */         this.this$0.controller.data.GA_Print_Child_Data.removeElementAt(this.this$0.controller.data.GA_Print_Child_Data.size() - 1);
/* 536 */       if (this.this$0.controller.data.GA_Print_P_Connection_C_Data != null) {
/* 537 */         this.this$0.controller.data.GA_Print_P_Connection_C_Data.removeElementAt(this.this$0.controller.data.GA_Print_P_Connection_C_Data.size() - 1);
/*     */       }
/*     */     }
/* 540 */     this.this$0.controller.data.SA_BestSolutionInfoLab = this.resultLab.getText();
/*     */   }
/*     */   
/*     */   public void setTableBackColor(int[][][] matrix, JTable currentTab, Color[] backColor, String[] currentS)
/*     */   {
/* 545 */     DefaultTableCellRenderer tcr = new IORTSPGeneticAlgorithmPanel.4(matrix, backColor, currentTab);
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
/* 562 */     for (int i = 0; i < currentS.length; i++) {
/* 563 */       currentTab.getColumn(currentS[i]).setCellRenderer(tcr);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setTableBackInfo()
/*     */   {
/* 569 */     if (this.this$0.controller.data.GACurrentTime != 0) {
/* 570 */       Vector colorV = (Vector)this.this$0.controller.data.GA_Total_SelectParent.elementAt(this.this$0.controller.data.GACurrentTime - 1);
/* 571 */       for (int i = 0; i < this.this$0.controller.data.GA_Pop_And_Child_TotalLine; i++) {
/* 572 */         this.table1ColorInfo[i][0][0] = i;
/* 573 */         this.table1ColorInfo[i][1][0] = 0;
/* 574 */         this.table1ColorInfo[i][0][1] = (this.chromosomeColor.length - 1);
/*     */       }
/* 576 */       for (int i = 0; i < colorV.size(); i++) {
/* 577 */         Integer colorNum = (Integer)colorV.elementAt(i);
/* 578 */         this.table1ColorInfo[colorNum.intValue()][0][1] = (i / 2);
/*     */       }
/* 580 */       setTableBackColor(this.table1ColorInfo, this.table1, this.chromosomeColor, this.colName1);
/* 581 */       setTableBackColor(this.table2ColorInfo, this.table2, this.chromosomeColor, this.colName2);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setTableFormat() {
/* 586 */     TableColumn column = new TableColumn();
/* 587 */     this.table1.getColumnModel().getColumn(0).setPreferredWidth(170);
/* 588 */     this.table1.getColumnModel().getColumn(1).setPreferredWidth(70);
/* 589 */     this.table2.getColumnModel().getColumn(0).setPreferredWidth(170);
/* 590 */     this.table2.getColumnModel().getColumn(1).setPreferredWidth(70);
/*     */   }
/*     */   
/*     */ 
/*     */   public void countCurrentSolution()
/*     */   {
/* 596 */     double temp = 0.0D;
/* 597 */     if (this.table.getRowCount() == 1) {
/* 598 */       this.bestDis = ((Double)this.this$0.controller.data.GA_Fitness_Vec.elementAt(0)).doubleValue();
/* 599 */       this.resultLab.setText(String.valueOf(String.valueOf(new StringBuffer("Best Distance = ").append(this.bestDis).append(" Best Solution = ").append(this.table.getValueAt(0, 1)))));
/* 600 */       return;
/*     */     }
/* 602 */     for (int i = 0; i < this.table.getRowCount(); i++) {
/* 603 */       temp = ((Double)this.this$0.controller.data.GA_Fitness_Vec.elementAt(i)).doubleValue();
/* 604 */       if (this.bestDis > temp) {
/* 605 */         this.bestDis = temp;
/* 606 */         this.Solution = ((String)this.table.getValueAt(i, 1));
/* 607 */         this.resultLab.setText(String.valueOf(String.valueOf(new StringBuffer("Best Distance = ").append(this.bestDis).append(" Best Solution = ").append(this.Solution))));
/*     */       } } }
/*     */   
/*     */   private class TSPTableModel extends AbstractTableModel { private int i;
/*     */     private int j;
/*     */     
/* 613 */     TSPTableModel(IORTSPGeneticAlgorithmPanel..5 x$1) { this(); }
/*     */     
/*     */ 
/*     */     public int getColumnCount()
/*     */     {
/* 618 */       return IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.tableCol;
/*     */     }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 623 */       return IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.tableRow;
/*     */     }
/*     */     
/*     */ 
/*     */     public String getColumnName(int col)
/*     */     {
/* 629 */       String str = "";
/* 630 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.colName[col])));
/* 631 */       return str;
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 636 */       String str = "";
/* 637 */       int r = -1;int c = -1;
/* 638 */       if ((col == 0) && (row == 0)) {
/* 639 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(1)));
/*     */       }
/* 641 */       else if (col == 0) {
/* 642 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(row + 1)));
/*     */       }
/* 644 */       else if (col == 1) {
/* 645 */         int[] solution = (int[])IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GA_Solution_Vec.elementAt(row);
/*     */         
/* 647 */         for (int i = 0; i < solution.length; i++) {
/* 648 */           if (i != solution.length - 1) {
/* 649 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1).append("-"))))));
/*     */           }
/*     */           else {
/* 652 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1))))));
/*     */           }
/*     */         }
/* 655 */         str = String.valueOf(String.valueOf(str)).concat("-1");
/*     */       }
/* 657 */       else if (col == 2) {
/* 658 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf((Double)IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GA_Fitness_Vec.elementAt(row)))))));
/*     */       }
/*     */       
/* 661 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c) {
/* 665 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int col) {
/* 669 */       if (!IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.is_panel_enabled)
/* 670 */         return false;
/* 671 */       if (col == 0) {
/* 672 */         return false;
/*     */       }
/* 674 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 679 */     public void setValueAt(Object value, int row, int col) { String input = (String)value; }
/*     */     
/*     */     private TSPTableModel() {} }
/*     */   
/*     */   private class TSPTableModel1 extends AbstractTableModel { private int i;
/*     */     
/* 685 */     TSPTableModel1(IORTSPGeneticAlgorithmPanel..5 x$1) { this(); }
/*     */     
/*     */ 
/*     */     public int getColumnCount()
/*     */     {
/* 690 */       return IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.tableCol1;
/*     */     }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 695 */       return IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.tableRow1;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col)
/*     */     {
/* 700 */       String str = "";
/* 701 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.colName1[col])));
/* 702 */       return str;
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 707 */       String str = "";
/* 708 */       Vector tempV = new Vector();
/* 709 */       int r = -1;int c = -1;
/*     */       
/* 711 */       if (col == 0) {
/* 712 */         if (IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GACurrentTime != 0) {
/* 713 */           tempV = (Vector)IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GA_Total_Pop_Vec.elementAt(IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GACurrentTime - 1);
/* 714 */           int[] solution = (int[])tempV.elementAt(row);
/* 715 */           for (int i = 0; i < solution.length; i++) {
/* 716 */             if (i != solution.length - 1) {
/* 717 */               str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1).append("-"))))));
/*     */             }
/*     */             else {
/* 720 */               str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1))))));
/*     */             }
/*     */           }
/* 723 */           str = String.valueOf(String.valueOf(str)).concat("-1");
/*     */         }
/*     */       }
/* 726 */       else if ((col == 1) && 
/* 727 */         (IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GACurrentTime != 0)) {
/* 728 */         tempV = (Vector)IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GA_Total_PopFit_Vec.elementAt(IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GACurrentTime - 1);
/* 729 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf((Double)tempV.elementAt(row)))))));
/*     */       }
/*     */       
/*     */ 
/* 733 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c) {
/* 737 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int col) {
/* 741 */       if (!IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.is_panel_enabled)
/* 742 */         return false;
/* 743 */       if (col == 0) {
/* 744 */         return false;
/*     */       }
/* 746 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */     private int j;
/* 751 */     public void setValueAt(Object value, int row, int col) { String input = (String)value; }
/*     */     
/*     */     private TSPTableModel1() {}
/*     */   }
/*     */   
/* 756 */   private class TSPTableModel2 extends AbstractTableModel { TSPTableModel2(IORTSPGeneticAlgorithmPanel..5 x$1) { this(); }
/*     */     
/*     */     private int i;
/*     */     private int j;
/*     */     public int getColumnCount() {
/* 761 */       return IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.tableCol2;
/*     */     }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 766 */       return IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.tableRow2;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col)
/*     */     {
/* 771 */       String str = "";
/* 772 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.colName2[col])));
/* 773 */       return str;
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 778 */       String str = "";
/*     */       
/* 780 */       int r = -1;int c = -1;
/* 781 */       if (col == 0) {
/* 782 */         if (IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GACurrentTime != 0) {
/* 783 */           Vector breakChild = (Vector)IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GA_Total_ChildBreak_Vec.elementAt(IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GACurrentTime - 1);
/* 784 */           int[] breakIn = (int[])breakChild.elementAt(row);
/*     */           
/* 786 */           Vector tempV1 = (Vector)IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GA_Total_Child_Vec.elementAt(IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GACurrentTime - 1);
/*     */           
/* 788 */           int[] solution = (int[])tempV1.elementAt(row);
/* 789 */           for (int i = 0; i < solution.length; i++)
/*     */           {
/* 791 */             if (i != solution.length - 1) {
/* 792 */               if (breakIn[i] != 0) {
/* 793 */                 str = String.valueOf(String.valueOf(str)).concat("[");
/*     */               }
/* 795 */               str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1))))));
/* 796 */               if (breakIn[i] != 0) {
/* 797 */                 str = String.valueOf(String.valueOf(str)).concat("]");
/*     */               }
/* 799 */               str = String.valueOf(String.valueOf(str)).concat("-");
/*     */             }
/*     */             else {
/* 802 */               if (breakIn[i] != 0) {
/* 803 */                 str = String.valueOf(String.valueOf(str)).concat("[");
/*     */               }
/* 805 */               str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1))))));
/* 806 */               if (breakIn[i] != 0) {
/* 807 */                 str = String.valueOf(String.valueOf(str)).concat("]");
/*     */               }
/*     */             }
/*     */           }
/* 811 */           str = String.valueOf(String.valueOf(str)).concat("-1");
/*     */         }
/*     */       }
/* 814 */       else if ((col == 1) && 
/* 815 */         (IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GACurrentTime != 0)) {
/* 816 */         Vector tempV1 = (Vector)IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GA_Total_ChildFitn_Vec.elementAt(IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.this$0.controller.data.GACurrentTime - 1);
/* 817 */         double temD = ((Double)tempV1.elementAt(row)).doubleValue();
/* 818 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(temD))))));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 823 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c) {
/* 827 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int col) {
/* 831 */       if (!IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.is_panel_enabled)
/* 832 */         return false;
/* 833 */       if (col == 0) {
/* 834 */         return false;
/*     */       }
/* 836 */       return true;
/*     */     }
/*     */     
/*     */     public void setValueAt(Object value, int row, int col)
/*     */     {
/* 841 */       String input = (String)value;
/*     */     }
/*     */     
/*     */     private TSPTableModel2() {}
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTSPGeneticAlgorithmPanel$IORTSPGeneticCanvasPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */