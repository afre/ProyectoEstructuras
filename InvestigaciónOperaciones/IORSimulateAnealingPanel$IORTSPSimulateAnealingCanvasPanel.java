/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollBar;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORSimulateAnealingPanel$IORTSPSimulateAnealingCanvasPanel
/*     */   extends JPanel
/*     */ {
/*     */   public JTable table;
/*     */   public int tableRow;
/*     */   public int tableCol;
/*     */   public IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.TSPTableModel tspModel;
/*     */   private Color default_color;
/*     */   private Color select_color;
/* 318 */   public static final int STATE_NONE = -1;
/*     */   
/*     */   private int state;
/*     */   
/*     */   private IORState iorstate;
/*     */   
/*     */   private JScrollPane canPanel;
/*     */   
/*     */   private IORTSPActionPanel actionPanel;
/*     */   
/*     */   private boolean is_panel_enabled;
/*     */   
/*     */   private JPanel tablePanel;
/*     */   
/*     */   private JPanel butPane;
/*     */   private JButton interactiveBut;
/*     */   private JButton nextBut;
/*     */   private JButton backBut;
/*     */   private JButton autoBut;
/*     */   private JButton resetBut;
/*     */   private JLabel finishInfoLab;
/*     */   private JLabel resultLab;
/*     */   private JScrollBar bar;
/* 341 */   private static final int canWid = 600;
/* 342 */   private static final int canHei = 450;
/*     */   private boolean isFinishInter;
/*     */   
/*     */   public IORSimulateAnealingPanel$IORTSPSimulateAnealingCanvasPanel(IORSimulateAnealingPanel this$0, IORTSPActionPanel p, int s) {
/* 346 */     this.this$0 = this$0;this.table = null;this.tableRow = 1;this.tableCol = 6;this.tspModel = null;this.default_color = Color.lightGray;this.select_color = Color.white;this.state = -1;this.iorstate = null;this.canPanel = null;this.actionPanel = null;this.is_panel_enabled = true;this.tablePanel = null;this.butPane = null;this.interactiveBut = null;this.nextBut = null;this.backBut = null;this.autoBut = null;this.resetBut = null;this.finishInfoLab = null;this.resultLab = null;this.isFinishInter = false;this.scrollShow = false;
/* 347 */     this.state = s;
/*     */     
/* 349 */     this.actionPanel = p;
/* 350 */     this.iorstate = p.getState();
/*     */     
/* 352 */     this$0.controller.solver.getInitSolution(this$0.controller.data);
/*     */     
/* 354 */     this.tspModel = new IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.TSPTableModel(null);
/* 355 */     this.table = new JTable(this.tspModel);
/* 356 */     this.table.setCellSelectionEnabled(false);
/* 357 */     this.table.setSelectionMode(0);
/* 358 */     this.table.setEnabled(false);
/*     */     
/* 360 */     this.canPanel = new JScrollPane(this.table);
/* 361 */     this.canPanel.setPreferredSize(new Dimension(600, 450));
/* 362 */     this.canPanel.setMaximumSize(new Dimension(600, 450));
/* 363 */     this.canPanel.setAlignmentX(0.5F);
/* 364 */     this.canPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
/*     */     
/* 366 */     this.canPanel.getVerticalScrollBar().addAdjustmentListener(new IORSimulateAnealingPanel.1((IORTSPSimulateAnealingCanvasPanel)this));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 375 */     this.interactiveBut = new JButton("Interactive");
/* 376 */     this.nextBut = new JButton("Next");
/* 377 */     this.backBut = new JButton("Back");
/*     */     
/*     */ 
/* 380 */     this.autoBut = new JButton("Auto");
/* 381 */     this.resetBut = new JButton("Reset");
/* 382 */     this.finishInfoLab = new JLabel("");
/* 383 */     this.resultLab = new JLabel("");
/* 384 */     this.resultLab.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
/* 385 */     this.finishInfoLab.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
/*     */     
/*     */ 
/*     */ 
/* 389 */     this.backBut.addActionListener(new IORSimulateAnealingPanel.2((IORTSPSimulateAnealingCanvasPanel)this));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 400 */     this.nextBut.addActionListener(new IORSimulateAnealingPanel.3((IORTSPSimulateAnealingCanvasPanel)this));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 446 */     this.resetBut.addActionListener(new IORSimulateAnealingPanel.4((IORTSPSimulateAnealingCanvasPanel)this));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 457 */     this.interactiveBut.addActionListener(new IORSimulateAnealingPanel.5((IORTSPSimulateAnealingCanvasPanel)this));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 471 */     this.autoBut.addActionListener(new IORSimulateAnealingPanel.6((IORTSPSimulateAnealingCanvasPanel)this));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 513 */     this.butPane = new JPanel();
/* 514 */     this.butPane.setLayout(new FlowLayout());
/* 515 */     this.butPane.add(this.backBut);
/*     */     
/* 517 */     this.butPane.add(this.nextBut);
/*     */     
/* 519 */     this.butPane.add(this.resetBut);
/*     */     
/*     */ 
/* 522 */     this.tablePanel = new JPanel();
/* 523 */     this.tablePanel.setLayout(new BorderLayout());
/* 524 */     this.tablePanel.add(this.finishInfoLab, "North");
/* 525 */     this.tablePanel.add(this.canPanel, "Center");
/* 526 */     this.tablePanel.add(this.resultLab, "South");
/*     */     
/* 528 */     setLayout(new BorderLayout());
/* 529 */     add(this.tablePanel, "Center");
/* 530 */     add(this.butPane, "South");
/* 531 */     setBackground(Color.white);
/*     */     
/*     */ 
/* 534 */     this$0.setFinishEnabled(true);
/*     */     
/* 536 */     this$0.controller.data.isAuto = "true";
/*     */     
/*     */ 
/* 539 */     setTableFormat();
/*     */   }
/*     */   
/*     */ 
/*     */   public void intervate()
/*     */   {
/* 545 */     this.this$0.setFinishEnabled(true);
/* 546 */     this.this$0.controller.data.isAuto = "true";
/* 547 */     this.this$0.controller.solver.doWork(getOperation(initVector()), this.this$0.controller.data);
/*     */   }
/*     */   
/*     */   public void initNextPrintData()
/*     */   {
/* 552 */     if (this.this$0.controller.data.SA_Next_PrintData != null) this.this$0.controller.data.SA_Next_PrintData = null;
/* 553 */     this.this$0.controller.data.SA_Next_PrintData = new String[this.tableRow][this.tableCol];
/* 554 */     for (int i = 0; i < this.tableRow; i++) {
/* 555 */       for (int j = 0; j < this.tableCol; j++) {
/* 556 */         this.this$0.controller.data.SA_Next_PrintData[i][j] = this.table.getValueAt(i, j).toString();
/*     */       }
/*     */     }
/* 559 */     this.this$0.controller.data.SA_BestSolutionInfoLab = this.resultLab.getText();
/* 560 */     this.this$0.controller.solver.initPrintData(getOperation(new Vector()), this.this$0.controller.data);
/*     */   }
/*     */   
/*     */   public void initAutoPrintData(int Type) {
/* 564 */     String[][] tempData = new String[this.tableRow][this.tableCol];
/* 565 */     if (Type == 1) {
/* 566 */       if (this.this$0.controller.data.SA_Auto_PrintData == null) this.this$0.controller.data.SA_Auto_PrintData = new Vector();
/* 567 */       for (int i = 0; i < this.tableRow; i++) {
/* 568 */         for (int j = 0; j < this.tableCol; j++) {
/* 569 */           tempData[i][j] = this.table.getValueAt(i, j).toString();
/*     */         }
/*     */       }
/* 572 */       this.this$0.controller.data.SA_Auto_PrintData.addElement(tempData);
/* 573 */       this.this$0.controller.solver.initPrintData(getOperation(new Vector()), this.this$0.controller.data);
/*     */     }
/* 575 */     else if (this.this$0.controller.data.SA_Auto_PrintData != null) {
/* 576 */       this.this$0.controller.data.SA_Auto_PrintData.removeElementAt(this.this$0.controller.data.SA_Auto_PrintData.size() - 1);
/*     */     }
/*     */     
/* 579 */     this.this$0.controller.data.SA_BestSolutionInfoLab = this.resultLab.getText();
/* 580 */     this.this$0.controller.solver.initPrintData(getOperation(new Vector()), this.this$0.controller.data);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setTableFormat()
/*     */   {
/* 586 */     TableColumn column = new TableColumn();
/* 587 */     this.table.getColumnModel().getColumn(0).setPreferredWidth(70);
/* 588 */     this.table.getColumnModel().getColumn(1).setPreferredWidth(70);
/* 589 */     this.table.getColumnModel().getColumn(2).setPreferredWidth(170);
/* 590 */     this.table.getColumnModel().getColumn(3).setPreferredWidth(80);
/* 591 */     this.table.getColumnModel().getColumn(4).setPreferredWidth(130);
/* 592 */     this.table.getColumnModel().getColumn(5).setPreferredWidth(70);
/*     */   }
/*     */   
/*     */   public void countBestSolution()
/*     */   {
/* 597 */     this.this$0.controller.data.bestSolutionNum = new Vector();
/*     */     
/* 599 */     for (int i = 0; i < this.table.getRowCount(); i++) {
/* 600 */       String tableValue = (String)this.table.getValueAt(i, 3);
/* 601 */       if (this.this$0.controller.data.MinDistance == Double.valueOf(tableValue).doubleValue()) {
/* 602 */         this.this$0.controller.data.bestSolutionNum.addElement(new Integer(i));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setColorFonBestSolution() {
/* 608 */     countBestSolution();
/*     */     
/* 610 */     DefaultTableCellRenderer tcr = new IORSimulateAnealingPanel.7((IORTSPSimulateAnealingCanvasPanel)this);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 645 */     String[] identifier = { "Iteration", "T", "Trial Solution", "Distance", "Acceptance Prob.", "Accept" };
/* 646 */     for (int i = 0; i < identifier.length; i++) {
/* 647 */       this.table.getColumn(identifier[i]).setCellRenderer(tcr);
/*     */     }
/*     */   }
/*     */   
/*     */   private Vector initVector()
/*     */   {
/* 653 */     Vector p = new Vector();
/* 654 */     p.addElement(this.this$0.controller.data.isAuto);
/* 655 */     return p;
/*     */   }
/*     */   
/*     */   public IOROperation getOperation(Vector vec) {
/* 659 */     IOROperation operation = new IOROperation(60010, vec);
/*     */     
/* 661 */     return operation;
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean scrollShow;
/*     */   private final IORSimulateAnealingPanel this$0;
/*     */   private String getMinDistance()
/*     */   {
/* 669 */     boolean onlyMinDistance = false;
/* 670 */     this.this$0.controller.data.MinDistance = 0.0D;
/* 671 */     if (this.this$0.controller.data.isAuto.equalsIgnoreCase("true")) {
/* 672 */       if (this.this$0.controller.data.auto_Distance_Vec.size() != 0)
/*     */       {
/* 674 */         for (int i = 0; i < this.this$0.controller.data.auto_Distance_Vec.size(); i++) {
/* 675 */           double tempD = Double.valueOf((String)this.this$0.controller.data.auto_Distance_Vec.elementAt(i)).doubleValue();
/* 676 */           if (this.this$0.controller.data.MinDistance == 0) {
/* 677 */             this.this$0.controller.data.MinDistance = tempD;
/* 678 */             this.this$0.controller.data.bestSolution = ((String)this.table.getValueAt(i + 1, 2));
/* 679 */           } else if (tempD < this.this$0.controller.data.MinDistance) {
/* 680 */             this.this$0.controller.data.MinDistance = tempD;
/* 681 */             this.this$0.controller.data.bestSolution = ((String)this.table.getValueAt(i + 1, 2));
/*     */           }
/*     */         }
/* 684 */         int times = 0;
/* 685 */         double tempD = Double.valueOf((String)this.table.getValueAt(0, 3)).doubleValue();
/* 686 */         if (tempD < this.this$0.controller.data.MinDistance) {
/* 687 */           this.this$0.controller.data.MinDistance = tempD;
/* 688 */           this.this$0.controller.data.bestSolution = ((String)this.table.getValueAt(0, 2));
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 694 */         for (int i = 0; i < this.this$0.controller.data.auto_Distance_Vec.size(); i++) {
/* 695 */           tempD = Double.valueOf((String)this.this$0.controller.data.auto_Distance_Vec.elementAt(i)).doubleValue();
/* 696 */           if (tempD == this.this$0.controller.data.MinDistance) {
/* 697 */             times++;
/*     */           }
/*     */         }
/* 700 */         if (times > 1) {
/* 701 */           onlyMinDistance = false;
/*     */         } else {
/* 703 */           onlyMinDistance = true;
/*     */         }
/*     */       }
/*     */     } else {
/* 707 */       if (this.this$0.controller.data.auto_Distance_Vec.size() != 0) {
/* 708 */         for (int i = 0; i < this.this$0.controller.data.currentLine - 1; i++) {
/* 709 */           double tempD = Double.valueOf((String)this.this$0.controller.data.auto_Distance_Vec.elementAt(i)).doubleValue();
/* 710 */           if (this.this$0.controller.data.MinDistance == 0) {
/* 711 */             this.this$0.controller.data.MinDistance = tempD;
/* 712 */             this.this$0.controller.data.bestSolution = ((String)this.table.getValueAt(i + 1, 2));
/* 713 */           } else if (tempD < this.this$0.controller.data.MinDistance) {
/* 714 */             this.this$0.controller.data.MinDistance = tempD;
/* 715 */             this.this$0.controller.data.bestSolution = ((String)this.table.getValueAt(i + 1, 2));
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 720 */       double tempD = Double.valueOf((String)this.table.getValueAt(0, 3)).doubleValue();
/* 721 */       if (tempD < this.this$0.controller.data.MinDistance) {
/* 722 */         this.this$0.controller.data.MinDistance = tempD;
/* 723 */         this.this$0.controller.data.bestSolution = ((String)this.table.getValueAt(0, 2));
/*     */       }
/*     */       
/* 726 */       int times = 0;
/* 727 */       for (int i = 0; i < this.this$0.controller.data.currentLine - 1; i++) {
/* 728 */         tempD = Double.valueOf((String)this.this$0.controller.data.auto_Distance_Vec.elementAt(i)).doubleValue();
/* 729 */         if (tempD == this.this$0.controller.data.MinDistance) {
/* 730 */           times++;
/*     */         }
/*     */       }
/* 733 */       if (times > 1) {
/* 734 */         onlyMinDistance = false;
/*     */       } else
/* 736 */         onlyMinDistance = true; }
/*     */     String output;
/*     */     String output;
/* 739 */     if (this.this$0.controller.data.MinDistance != 0) { String output;
/* 740 */       if (onlyMinDistance) {
/* 741 */         output = String.valueOf(String.valueOf(new StringBuffer("Best Distance = ").append(this.this$0.controller.data.MinDistance).append("   Best Solution :  ").append(this.this$0.controller.data.bestSolution)));
/*     */       } else {
/* 743 */         output = String.valueOf(String.valueOf(new StringBuffer("Best Distance = ").append(this.this$0.controller.data.MinDistance).append("   Best Solution :  ").append(this.this$0.controller.data.bestSolution)));
/*     */       }
/*     */     }
/*     */     else {
/* 747 */       output = "";
/*     */     }
/* 749 */     return output;
/*     */   }
/*     */   
/* 752 */   private class TSPTableModel extends AbstractTableModel { TSPTableModel(IORSimulateAnealingPanel..8 x$1) { this(); }
/*     */     
/*     */ 
/*     */     public int getColumnCount()
/*     */     {
/* 757 */       return IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.tableCol;
/*     */     }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 762 */       return IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.tableRow;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col)
/*     */     {
/* 767 */       String str = "";
/* 768 */       if (col == 0) {
/* 769 */         str = String.valueOf(String.valueOf(str)).concat("Iteration");
/* 770 */       } else if (col == 1) {
/* 771 */         str = String.valueOf(String.valueOf(str)).concat("T");
/* 772 */       } else if (col == 2) {
/* 773 */         str = String.valueOf(String.valueOf(str)).concat("Trial Solution");
/* 774 */       } else if (col == 3) {
/* 775 */         str = String.valueOf(String.valueOf(str)).concat("Distance");
/* 776 */       } else if (col == 4) {
/* 777 */         str = String.valueOf(String.valueOf(str)).concat("Acceptance Prob.");
/*     */       } else
/* 779 */         str = String.valueOf(String.valueOf(str)).concat("Accept");
/* 780 */       return str;
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 785 */       String str = "";
/* 786 */       int currentIteration = 1;
/* 787 */       int r = -1;int c = -1;
/* 788 */       if ((col == 0) && (row == 0)) {
/* 789 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(0)));
/*     */       }
/* 791 */       else if (col == 0) {
/* 792 */         if (row >= 2) {
/* 793 */           currentIteration = Integer.valueOf((String)getValueAt(row - 1, col)).intValue();
/* 794 */           String accept = (String)IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.this$0.controller.data.auto_Accept_Vec.elementAt(row - 2);
/* 795 */           if (accept.equalsIgnoreCase("true")) {
/* 796 */             currentIteration++;
/* 797 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(currentIteration)));
/*     */           } else {
/* 799 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(currentIteration)));
/*     */           }
/*     */         } else {
/* 802 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(currentIteration)));
/*     */         }
/*     */       }
/* 805 */       else if (col == 1) {
/* 806 */         if (row == 0) {
/* 807 */           str = String.valueOf(String.valueOf(str)).concat("");
/*     */         }
/* 809 */         else if (IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.this$0.controller.data.auto_T_Vec.size() != 0) {
/* 810 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((String)IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.this$0.controller.data.auto_T_Vec.elementAt(row - 1))));
/*     */         }
/*     */       }
/* 813 */       else if (col == 2) {
/* 814 */         if (row == 0) {
/* 815 */           int[] solution = IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.this$0.controller.data.initSolution;
/* 816 */           String s = "";
/* 817 */           for (int i = 0; i < solution.length; i++) {
/* 818 */             if (i != solution.length - 1) {
/* 819 */               s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1).append("-"))))));
/*     */             } else {
/* 821 */               s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1))))));
/*     */             }
/*     */           }
/* 824 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(s)));
/* 825 */           str = String.valueOf(String.valueOf(str)).concat("-1");
/*     */         }
/* 827 */         else if (IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.this$0.controller.data.auto_Solution_Vec.size() != 0) {
/* 828 */           int[] solution = (int[])IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.this$0.controller.data.auto_Solution_Vec.elementAt(row - 1);
/* 829 */           String s = "";
/* 830 */           for (int i = 0; i < solution.length; i++) {
/* 831 */             if (i != solution.length - 1) {
/* 832 */               s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1).append("-"))))));
/*     */             } else {
/* 834 */               s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1))))));
/*     */             }
/*     */           }
/* 837 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(s)));
/* 838 */           str = String.valueOf(String.valueOf(str)).concat("-1");
/*     */         }
/*     */       }
/* 841 */       else if (col == 3) {
/* 842 */         if (row == 0) {
/* 843 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.this$0.controller.data.initDistance)));
/*     */         }
/* 845 */         else if (IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.this$0.controller.data.auto_Distance_Vec.size() != 0) {
/* 846 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((String)IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.this$0.controller.data.auto_Distance_Vec.elementAt(row - 1))));
/*     */         }
/*     */       }
/* 849 */       else if (col == 4)
/*     */       {
/* 851 */         if (row == 0) {
/* 852 */           str = String.valueOf(String.valueOf(str)).concat("");
/*     */         }
/* 854 */         else if (IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.this$0.controller.data.auto_Probability_Vec.size() != 0) {
/* 855 */           double tempD = Double.valueOf((String)IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.this$0.controller.data.auto_Probability_Vec.elementAt(row - 1)).doubleValue();
/* 856 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(IORSimulateAnealingPanel.access$15(IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.this$0).format(tempD)))))));
/*     */         }
/*     */       } else { double tempD;
/* 859 */         if (col == 5) {
/* 860 */           if (row == 0) {
/* 861 */             str = String.valueOf(String.valueOf(str)).concat("");
/*     */           }
/* 863 */           else if (IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.this$0.controller.data.auto_Probability_Vec.size() != 0) {
/* 864 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((String)IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.this$0.controller.data.auto_Accept_Vec.elementAt(row - 1))));
/*     */           }
/*     */         }
/*     */       }
/* 868 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c) {
/* 872 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int col) {
/* 876 */       if (!IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.is_panel_enabled)
/* 877 */         return false;
/* 878 */       if (col == 0) {
/* 879 */         return false;
/*     */       }
/* 881 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */     private int i;
/*     */     private int j;
/*     */     public void setValueAt(Object value, int row, int col)
/*     */     {
/* 889 */       String input = (String)value;
/*     */       
/* 891 */       if ((col != 0) && (row != col - 1)) {
/*     */         try {
/* 893 */           int[] d = IORTSPActionPanel.parseStringInput(input);
/* 894 */           if (d == null) {
/* 895 */             return;
/*     */           }
/*     */           
/*     */         }
/*     */         catch (NumberFormatException e)
/*     */         {
/* 901 */           return;
/*     */         }
/*     */         
/* 904 */         fireTableCellUpdated(row, col - 1);
/*     */       }
/*     */     }
/*     */     
/*     */     private TSPTableModel() {}
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORSimulateAnealingPanel$IORTSPSimulateAnealingCanvasPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */