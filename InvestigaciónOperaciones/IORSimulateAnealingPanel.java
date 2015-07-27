/*     */ import java.awt.Color;
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
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ 
/*     */ public class IORSimulateAnealingPanel extends IORTSPActionPanel implements java.awt.print.Printable
/*     */ {
/*  17 */   private JPanel canP = null;
/*  18 */   private IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel canvasPanel = null;
/*     */   
/*  20 */   private JLabel tableInfo = null;
/*  21 */   private JLabel finishInfo = null;
/*     */   
/*  23 */   private JLabel MAXDistanceLab = null;
/*     */   
/*  25 */   private DecimalFormat nf = new DecimalFormat();
/*  26 */   public int currentAutoTime = 0;
/*     */   
/*     */   public IORSimulateAnealingPanel(IORTSPProcessController c)
/*     */   {
/*  30 */     super(c);
/*  31 */     setStep(1);
/*  32 */     addStep();
/*  33 */     this.state = new IORState(c.solver);
/*  34 */     createCanvasPanel();
/*  35 */     this.nf.setMaximumFractionDigits(2);
/*  36 */     this.nf.setMinimumFractionDigits(2);
/*  37 */     add(this.canvasPanel, "North");
/*  38 */     setDoubleBuffered(false);
/*     */     
/*  40 */     this.canvasPanel.initNextPrintData();
/*     */   }
/*     */   
/*     */   private void createCanvasPanel() {
/*  44 */     this.canvasPanel = new IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel(this, 0);
/*     */   }
/*     */   
/*     */   protected void backStep()
/*     */   {
/*  49 */     if (this.controller.data.isAuto.equalsIgnoreCase("true")) {
/*  50 */       this.currentAutoTime -= 1;
/*  51 */       if (this.currentAutoTime == 0) {
/*  52 */         this.canvasPanel.backBut.setEnabled(false);
/*  53 */         this.canvasPanel.tableRow = 1;
/*     */       } else {
/*  55 */         this.canvasPanel.tableRow = (Integer.valueOf((String)this.controller.data.saveCurrentTotalLine.elementAt(this.currentAutoTime - 1)).intValue() + 1);
/*  56 */         this.controller.data.auto_T_Vec = ((Vector)this.controller.data.saveAuto_T_Vec.elementAt(this.currentAutoTime - 1));
/*  57 */         this.controller.data.auto_Solution_Vec = ((Vector)this.controller.data.saveAuto_Solution_Vec.elementAt(this.currentAutoTime - 1));
/*  58 */         this.controller.data.auto_Distance_Vec = ((Vector)this.controller.data.saveAuto_Distance_Vec.elementAt(this.currentAutoTime - 1));
/*  59 */         this.controller.data.auto_Probability_Vec = ((Vector)this.controller.data.saveAuto_Probability_Vec.elementAt(this.currentAutoTime - 1));
/*  60 */         this.controller.data.auto_Accept_Vec = ((Vector)this.controller.data.saveAuto_Accept_Vec.elementAt(this.currentAutoTime - 1));
/*  61 */         this.canvasPanel.initAutoPrintData(-1);
/*     */       }
/*  63 */       this.canvasPanel.tspModel.fireTableStructureChanged();
/*  64 */       this.canvasPanel.table.sizeColumnsToFit(-1);
/*     */     }
/*     */     else {
/*  67 */       while (this.controller.data.currentLine > 1) {
/*  68 */         this.controller.data.currentLine -= 1;
/*     */         String acceptS;
/*  70 */         if (this.controller.data.currentLine == 1) {
/*  71 */           String acceptS = (String)this.controller.data.auto_Accept_Vec.elementAt(this.controller.data.currentLine - 1);
/*  72 */           this.controller.data.currentLine = 1;
/*  73 */           this.canvasPanel.tableRow = this.controller.data.currentLine;
/*  74 */           this.canvasPanel.backBut.setEnabled(false);
/*     */         }
/*     */         else {
/*  77 */           acceptS = (String)this.controller.data.auto_Accept_Vec.elementAt(this.controller.data.currentLine - 2);
/*     */         }
/*  79 */         if (acceptS.equalsIgnoreCase("true")) {
/*     */           break;
/*     */         }
/*     */       }
/*  83 */       this.canvasPanel.tableRow = this.controller.data.currentLine;
/*  84 */       this.canvasPanel.nextBut.setEnabled(true);
/*     */     }
/*     */     
/*  87 */     this.canvasPanel.tspModel.fireTableStructureChanged();
/*  88 */     if (this.canvasPanel.backBut.isEnabled()) {
/*  89 */       this.canvasPanel.resultLab.setText(this.canvasPanel.getMinDistance());
/*     */     } else {
/*  91 */       this.canvasPanel.resultLab.setText("");
/*     */     }
/*  93 */     this.canvasPanel.initNextPrintData();
/*     */   }
/*     */   
/*     */ 
/*     */   protected void finishProcedure()
/*     */   {
/*  99 */     this.controller.solver.initPrintData(this.canvasPanel.getOperation(this.canvasPanel.initVector()), this.controller.data);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 108 */     this.actionStatus.setText("The current procedure is finished. Please go to the Procedure menu to continue!");
/* 109 */     setFinishEnabled(false);
/*     */   }
/*     */   
/*     */   public void updatePanel()
/*     */   {
/* 114 */     if (this.controller.data.saveAuto_Accept_Vec.size() == 0) {
/* 115 */       return;
/*     */     }
/* 117 */     if (this.controller.data.isAuto.equalsIgnoreCase("false")) {
/* 118 */       if (this.controller.data.currentLine > 1) {
/* 119 */         this.canvasPanel.tableRow = this.controller.data.currentLine;
/* 120 */         this.canvasPanel.backBut.setEnabled(true);
/* 121 */         this.canvasPanel.resultLab.setText(this.canvasPanel.getMinDistance());
/*     */       } else {
/* 123 */         this.canvasPanel.tableRow = 1;
/* 124 */         this.canvasPanel.backBut.setEnabled(false);
/* 125 */         this.canvasPanel.resultLab.setText("");
/*     */       }
/* 127 */       this.canvasPanel.interactiveBut.setEnabled(false);
/* 128 */       if (this.controller.data.auto_T_Vec.size() == this.controller.data.currentLine - 1) {
/* 129 */         this.canvasPanel.nextBut.setEnabled(false);
/*     */       } else {
/* 131 */         this.canvasPanel.nextBut.setEnabled(true);
/*     */       }
/* 133 */       this.canvasPanel.autoBut.setEnabled(false);
/* 134 */       this.canvasPanel.resetBut.setEnabled(true);
/*     */       
/* 136 */       this.canvasPanel.tspModel.fireTableStructureChanged();
/*     */     }
/*     */     else {
/* 139 */       this.currentAutoTime = this.controller.data.saveAuto_Accept_Vec.size();
/*     */       
/*     */ 
/* 142 */       this.controller.data.auto_T_Vec = ((Vector)this.controller.data.saveAuto_T_Vec.elementAt(this.currentAutoTime - 1));
/* 143 */       this.controller.data.auto_Solution_Vec = ((Vector)this.controller.data.saveAuto_Solution_Vec.elementAt(this.currentAutoTime - 1));
/* 144 */       this.controller.data.auto_Distance_Vec = ((Vector)this.controller.data.saveAuto_Distance_Vec.elementAt(this.currentAutoTime - 1));
/* 145 */       this.controller.data.auto_Probability_Vec = ((Vector)this.controller.data.saveAuto_Probability_Vec.elementAt(this.currentAutoTime - 1));
/* 146 */       this.controller.data.auto_Accept_Vec = ((Vector)this.controller.data.saveAuto_Accept_Vec.elementAt(this.currentAutoTime - 1));
/* 147 */       this.canvasPanel.tableRow = (Integer.valueOf((String)this.controller.data.saveCurrentTotalLine.elementAt(this.currentAutoTime - 1)).intValue() + 1);
/* 148 */       this.canvasPanel.tspModel.fireTableStructureChanged();
/*     */       
/* 150 */       if (this.canvasPanel.tableRow == 1) {
/* 151 */         this.canvasPanel.backBut.setEnabled(false);
/* 152 */         this.canvasPanel.resultLab.setText("");
/*     */       } else {
/* 154 */         this.canvasPanel.backBut.setEnabled(true);
/* 155 */         this.canvasPanel.resultLab.setText(this.canvasPanel.getMinDistance());
/*     */       }
/* 157 */       this.canvasPanel.interactiveBut.setEnabled(false);
/* 158 */       this.canvasPanel.nextBut.setEnabled(false);
/* 159 */       this.canvasPanel.autoBut.setEnabled(true);
/* 160 */       this.canvasPanel.resetBut.setEnabled(true);
/*     */     }
/* 162 */     this.canvasPanel.setColorFonBestSolution();
/* 163 */     this.canvasPanel.table.repaint();
/* 164 */     this.canvasPanel.setTableFormat();
/* 165 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 171 */     String str = "Simulated Annealing for Traveling Salesman Problem.";
/*     */     
/* 173 */     str = String.valueOf(String.valueOf(str)).concat("\n\nClick “Next” to proceed to the next iteration; click “Back” to return to the previous iteration; click “Reset” to restart the problem solving procedure.");
/*     */     
/* 175 */     str = String.valueOf(String.valueOf(str)).concat("\n\nPress the Enter key to continue the current procedure.");
/* 176 */     this.help_content_step.setText(str);
/* 177 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */   protected void initializeCurrentProcedureHelpPanel() {
/* 181 */     String str = "Simulated Annealing for Traveling Salesman Problem.";
/* 182 */     str = String.valueOf(String.valueOf(str)).concat("\n\nThis procedure uses the Simulated Annealing algorithm to solve a traveling salesman problem step by step.");
/* 183 */     str = String.valueOf(String.valueOf(str)).concat("\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 185 */     this.help_content_procedure.setText(str);
/* 186 */     this.help_content_procedure.revalidate();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int print(java.awt.Graphics g, java.awt.print.PageFormat pf, int pi)
/*     */     throws java.awt.print.PrinterException
/*     */   {
/* 255 */     return super.print(g, pf, pi);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/*     */     try
/*     */     {
/* 268 */       out.writeObject(this.state);
/*     */ 
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/* 273 */       System.out.println("Save fails".concat(String.valueOf(String.valueOf(e1))));
/*     */     }
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
/* 287 */       this.state = ((IORState)in.readObject());
/*     */ 
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/*     */ 
/* 293 */       System.out.println("Load fails ".concat(String.valueOf(String.valueOf(e1))));
/* 294 */       return;
/*     */     }
/* 296 */     updatePanel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public class IORTSPSimulateAnealingCanvasPanel
/*     */     extends JPanel
/*     */   {
/* 308 */     public JTable table = null;
/* 309 */     public int tableRow = 1;
/* 310 */     public int tableCol = 6;
/*     */     
/* 312 */     public IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.TSPTableModel tspModel = null;
/*     */     
/* 314 */     private Color default_color = Color.lightGray;
/* 315 */     private Color select_color = Color.white;
/*     */     
/*     */ 
/* 318 */     public static final int STATE_NONE = -1;
/*     */     
/* 320 */     private int state = -1;
/*     */     
/* 322 */     private IORState iorstate = null;
/*     */     
/* 324 */     private JScrollPane canPanel = null;
/*     */     
/* 326 */     private IORTSPActionPanel actionPanel = null;
/*     */     
/* 328 */     private boolean is_panel_enabled = true;
/*     */     
/* 330 */     private JPanel tablePanel = null;
/* 331 */     private JPanel butPane = null;
/* 332 */     private JButton interactiveBut = null;
/* 333 */     private JButton nextBut = null;
/* 334 */     private JButton backBut = null;
/* 335 */     private JButton autoBut = null;
/* 336 */     private JButton resetBut = null;
/* 337 */     private JLabel finishInfoLab = null;
/* 338 */     private JLabel resultLab = null;
/*     */     
/*     */     private javax.swing.JScrollBar bar;
/* 341 */     private static final int canWid = 600;
/* 342 */     private static final int canHei = 450;
/*     */     
/* 344 */     private boolean isFinishInter = false;
/* 345 */     private boolean scrollShow = false;
/*     */     
/* 347 */     public IORTSPSimulateAnealingCanvasPanel(IORTSPActionPanel p, int s) { this.state = s;
/*     */       
/* 349 */       this.actionPanel = p;
/* 350 */       this.iorstate = p.getState();
/*     */       
/* 352 */       IORSimulateAnealingPanel.this.controller.solver.getInitSolution(IORSimulateAnealingPanel.this.controller.data);
/*     */       
/* 354 */       this.tspModel = new IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.TSPTableModel(null);
/* 355 */       this.table = new JTable(this.tspModel);
/* 356 */       this.table.setCellSelectionEnabled(false);
/* 357 */       this.table.setSelectionMode(0);
/* 358 */       this.table.setEnabled(false);
/*     */       
/* 360 */       this.canPanel = new JScrollPane(this.table);
/* 361 */       this.canPanel.setPreferredSize(new java.awt.Dimension(600, 450));
/* 362 */       this.canPanel.setMaximumSize(new java.awt.Dimension(600, 450));
/* 363 */       this.canPanel.setAlignmentX(0.5F);
/* 364 */       this.canPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
/*     */       
/* 366 */       this.canPanel.getVerticalScrollBar().addAdjustmentListener(new IORSimulateAnealingPanel.1((IORTSPSimulateAnealingCanvasPanel)this));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 375 */       this.interactiveBut = new JButton("Interactive");
/* 376 */       this.nextBut = new JButton("Next");
/* 377 */       this.backBut = new JButton("Back");
/*     */       
/*     */ 
/* 380 */       this.autoBut = new JButton("Auto");
/* 381 */       this.resetBut = new JButton("Reset");
/* 382 */       this.finishInfoLab = new JLabel("");
/* 383 */       this.resultLab = new JLabel("");
/* 384 */       this.resultLab.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
/* 385 */       this.finishInfoLab.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
/*     */       
/*     */ 
/*     */ 
/* 389 */       this.backBut.addActionListener(new IORSimulateAnealingPanel.2((IORTSPSimulateAnealingCanvasPanel)this));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 400 */       this.nextBut.addActionListener(new IORSimulateAnealingPanel.3((IORTSPSimulateAnealingCanvasPanel)this));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 446 */       this.resetBut.addActionListener(new IORSimulateAnealingPanel.4((IORTSPSimulateAnealingCanvasPanel)this));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 457 */       this.interactiveBut.addActionListener(new IORSimulateAnealingPanel.5((IORTSPSimulateAnealingCanvasPanel)this));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 471 */       this.autoBut.addActionListener(new IORSimulateAnealingPanel.6((IORTSPSimulateAnealingCanvasPanel)this));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 513 */       this.butPane = new JPanel();
/* 514 */       this.butPane.setLayout(new java.awt.FlowLayout());
/* 515 */       this.butPane.add(this.backBut);
/*     */       
/* 517 */       this.butPane.add(this.nextBut);
/*     */       
/* 519 */       this.butPane.add(this.resetBut);
/*     */       
/*     */ 
/* 522 */       this.tablePanel = new JPanel();
/* 523 */       this.tablePanel.setLayout(new java.awt.BorderLayout());
/* 524 */       this.tablePanel.add(this.finishInfoLab, "North");
/* 525 */       this.tablePanel.add(this.canPanel, "Center");
/* 526 */       this.tablePanel.add(this.resultLab, "South");
/*     */       
/* 528 */       setLayout(new java.awt.BorderLayout());
/* 529 */       add(this.tablePanel, "Center");
/* 530 */       add(this.butPane, "South");
/* 531 */       setBackground(Color.white);
/*     */       
/*     */ 
/* 534 */       IORSimulateAnealingPanel.this.setFinishEnabled(true);
/*     */       
/* 536 */       IORSimulateAnealingPanel.this.controller.data.isAuto = "true";
/*     */       
/*     */ 
/* 539 */       setTableFormat();
/*     */     }
/*     */     
/*     */ 
/*     */     public void intervate()
/*     */     {
/* 545 */       IORSimulateAnealingPanel.this.setFinishEnabled(true);
/* 546 */       IORSimulateAnealingPanel.this.controller.data.isAuto = "true";
/* 547 */       IORSimulateAnealingPanel.this.controller.solver.doWork(getOperation(initVector()), IORSimulateAnealingPanel.this.controller.data);
/*     */     }
/*     */     
/*     */     public void initNextPrintData()
/*     */     {
/* 552 */       if (IORSimulateAnealingPanel.this.controller.data.SA_Next_PrintData != null) IORSimulateAnealingPanel.this.controller.data.SA_Next_PrintData = null;
/* 553 */       IORSimulateAnealingPanel.this.controller.data.SA_Next_PrintData = new String[this.tableRow][this.tableCol];
/* 554 */       for (int i = 0; i < this.tableRow; i++) {
/* 555 */         for (int j = 0; j < this.tableCol; j++) {
/* 556 */           IORSimulateAnealingPanel.this.controller.data.SA_Next_PrintData[i][j] = this.table.getValueAt(i, j).toString();
/*     */         }
/*     */       }
/* 559 */       IORSimulateAnealingPanel.this.controller.data.SA_BestSolutionInfoLab = this.resultLab.getText();
/* 560 */       IORSimulateAnealingPanel.this.controller.solver.initPrintData(getOperation(new Vector()), IORSimulateAnealingPanel.this.controller.data);
/*     */     }
/*     */     
/*     */     public void initAutoPrintData(int Type) {
/* 564 */       String[][] tempData = new String[this.tableRow][this.tableCol];
/* 565 */       if (Type == 1) {
/* 566 */         if (IORSimulateAnealingPanel.this.controller.data.SA_Auto_PrintData == null) IORSimulateAnealingPanel.this.controller.data.SA_Auto_PrintData = new Vector();
/* 567 */         for (int i = 0; i < this.tableRow; i++) {
/* 568 */           for (int j = 0; j < this.tableCol; j++) {
/* 569 */             tempData[i][j] = this.table.getValueAt(i, j).toString();
/*     */           }
/*     */         }
/* 572 */         IORSimulateAnealingPanel.this.controller.data.SA_Auto_PrintData.addElement(tempData);
/* 573 */         IORSimulateAnealingPanel.this.controller.solver.initPrintData(getOperation(new Vector()), IORSimulateAnealingPanel.this.controller.data);
/*     */       }
/* 575 */       else if (IORSimulateAnealingPanel.this.controller.data.SA_Auto_PrintData != null) {
/* 576 */         IORSimulateAnealingPanel.this.controller.data.SA_Auto_PrintData.removeElementAt(IORSimulateAnealingPanel.this.controller.data.SA_Auto_PrintData.size() - 1);
/*     */       }
/*     */       
/* 579 */       IORSimulateAnealingPanel.this.controller.data.SA_BestSolutionInfoLab = this.resultLab.getText();
/* 580 */       IORSimulateAnealingPanel.this.controller.solver.initPrintData(getOperation(new Vector()), IORSimulateAnealingPanel.this.controller.data);
/*     */     }
/*     */     
/*     */ 
/*     */     public void setTableFormat()
/*     */     {
/* 586 */       TableColumn column = new TableColumn();
/* 587 */       this.table.getColumnModel().getColumn(0).setPreferredWidth(70);
/* 588 */       this.table.getColumnModel().getColumn(1).setPreferredWidth(70);
/* 589 */       this.table.getColumnModel().getColumn(2).setPreferredWidth(170);
/* 590 */       this.table.getColumnModel().getColumn(3).setPreferredWidth(80);
/* 591 */       this.table.getColumnModel().getColumn(4).setPreferredWidth(130);
/* 592 */       this.table.getColumnModel().getColumn(5).setPreferredWidth(70);
/*     */     }
/*     */     
/*     */     public void countBestSolution()
/*     */     {
/* 597 */       IORSimulateAnealingPanel.this.controller.data.bestSolutionNum = new Vector();
/*     */       
/* 599 */       for (int i = 0; i < this.table.getRowCount(); i++) {
/* 600 */         String tableValue = (String)this.table.getValueAt(i, 3);
/* 601 */         if (IORSimulateAnealingPanel.this.controller.data.MinDistance == Double.valueOf(tableValue).doubleValue()) {
/* 602 */           IORSimulateAnealingPanel.this.controller.data.bestSolutionNum.addElement(new Integer(i));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public void setColorFonBestSolution() {
/* 608 */       countBestSolution();
/*     */       
/* 610 */       javax.swing.table.DefaultTableCellRenderer tcr = new IORSimulateAnealingPanel.7((IORTSPSimulateAnealingCanvasPanel)this);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 645 */       String[] identifier = { "Iteration", "T", "Trial Solution", "Distance", "Acceptance Prob.", "Accept" };
/* 646 */       for (int i = 0; i < identifier.length; i++) {
/* 647 */         this.table.getColumn(identifier[i]).setCellRenderer(tcr);
/*     */       }
/*     */     }
/*     */     
/*     */     private Vector initVector()
/*     */     {
/* 653 */       Vector p = new Vector();
/* 654 */       p.addElement(IORSimulateAnealingPanel.this.controller.data.isAuto);
/* 655 */       return p;
/*     */     }
/*     */     
/*     */     public IOROperation getOperation(Vector vec) {
/* 659 */       IOROperation operation = new IOROperation(60010, vec);
/*     */       
/* 661 */       return operation;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private String getMinDistance()
/*     */     {
/* 669 */       boolean onlyMinDistance = false;
/* 670 */       IORSimulateAnealingPanel.this.controller.data.MinDistance = 0.0D;
/* 671 */       if (IORSimulateAnealingPanel.this.controller.data.isAuto.equalsIgnoreCase("true")) {
/* 672 */         if (IORSimulateAnealingPanel.this.controller.data.auto_Distance_Vec.size() != 0)
/*     */         {
/* 674 */           for (int i = 0; i < IORSimulateAnealingPanel.this.controller.data.auto_Distance_Vec.size(); i++) {
/* 675 */             double tempD = Double.valueOf((String)IORSimulateAnealingPanel.this.controller.data.auto_Distance_Vec.elementAt(i)).doubleValue();
/* 676 */             if (IORSimulateAnealingPanel.this.controller.data.MinDistance == 0) {
/* 677 */               IORSimulateAnealingPanel.this.controller.data.MinDistance = tempD;
/* 678 */               IORSimulateAnealingPanel.this.controller.data.bestSolution = ((String)this.table.getValueAt(i + 1, 2));
/* 679 */             } else if (tempD < IORSimulateAnealingPanel.this.controller.data.MinDistance) {
/* 680 */               IORSimulateAnealingPanel.this.controller.data.MinDistance = tempD;
/* 681 */               IORSimulateAnealingPanel.this.controller.data.bestSolution = ((String)this.table.getValueAt(i + 1, 2));
/*     */             }
/*     */           }
/* 684 */           int times = 0;
/* 685 */           double tempD = Double.valueOf((String)this.table.getValueAt(0, 3)).doubleValue();
/* 686 */           if (tempD < IORSimulateAnealingPanel.this.controller.data.MinDistance) {
/* 687 */             IORSimulateAnealingPanel.this.controller.data.MinDistance = tempD;
/* 688 */             IORSimulateAnealingPanel.this.controller.data.bestSolution = ((String)this.table.getValueAt(0, 2));
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 694 */           for (int i = 0; i < IORSimulateAnealingPanel.this.controller.data.auto_Distance_Vec.size(); i++) {
/* 695 */             tempD = Double.valueOf((String)IORSimulateAnealingPanel.this.controller.data.auto_Distance_Vec.elementAt(i)).doubleValue();
/* 696 */             if (tempD == IORSimulateAnealingPanel.this.controller.data.MinDistance) {
/* 697 */               times++;
/*     */             }
/*     */           }
/* 700 */           if (times > 1) {
/* 701 */             onlyMinDistance = false;
/*     */           } else {
/* 703 */             onlyMinDistance = true;
/*     */           }
/*     */         }
/*     */       } else {
/* 707 */         if (IORSimulateAnealingPanel.this.controller.data.auto_Distance_Vec.size() != 0) {
/* 708 */           for (int i = 0; i < IORSimulateAnealingPanel.this.controller.data.currentLine - 1; i++) {
/* 709 */             double tempD = Double.valueOf((String)IORSimulateAnealingPanel.this.controller.data.auto_Distance_Vec.elementAt(i)).doubleValue();
/* 710 */             if (IORSimulateAnealingPanel.this.controller.data.MinDistance == 0) {
/* 711 */               IORSimulateAnealingPanel.this.controller.data.MinDistance = tempD;
/* 712 */               IORSimulateAnealingPanel.this.controller.data.bestSolution = ((String)this.table.getValueAt(i + 1, 2));
/* 713 */             } else if (tempD < IORSimulateAnealingPanel.this.controller.data.MinDistance) {
/* 714 */               IORSimulateAnealingPanel.this.controller.data.MinDistance = tempD;
/* 715 */               IORSimulateAnealingPanel.this.controller.data.bestSolution = ((String)this.table.getValueAt(i + 1, 2));
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 720 */         double tempD = Double.valueOf((String)this.table.getValueAt(0, 3)).doubleValue();
/* 721 */         if (tempD < IORSimulateAnealingPanel.this.controller.data.MinDistance) {
/* 722 */           IORSimulateAnealingPanel.this.controller.data.MinDistance = tempD;
/* 723 */           IORSimulateAnealingPanel.this.controller.data.bestSolution = ((String)this.table.getValueAt(0, 2));
/*     */         }
/*     */         
/* 726 */         int times = 0;
/* 727 */         for (int i = 0; i < IORSimulateAnealingPanel.this.controller.data.currentLine - 1; i++) {
/* 728 */           tempD = Double.valueOf((String)IORSimulateAnealingPanel.this.controller.data.auto_Distance_Vec.elementAt(i)).doubleValue();
/* 729 */           if (tempD == IORSimulateAnealingPanel.this.controller.data.MinDistance) {
/* 730 */             times++;
/*     */           }
/*     */         }
/* 733 */         if (times > 1) {
/* 734 */           onlyMinDistance = false;
/*     */         } else
/* 736 */           onlyMinDistance = true; }
/*     */       String output;
/*     */       String output;
/* 739 */       if (IORSimulateAnealingPanel.this.controller.data.MinDistance != 0) { String output;
/* 740 */         if (onlyMinDistance) {
/* 741 */           output = String.valueOf(String.valueOf(new StringBuffer("Best Distance = ").append(IORSimulateAnealingPanel.this.controller.data.MinDistance).append("   Best Solution :  ").append(IORSimulateAnealingPanel.this.controller.data.bestSolution)));
/*     */         } else {
/* 743 */           output = String.valueOf(String.valueOf(new StringBuffer("Best Distance = ").append(IORSimulateAnealingPanel.this.controller.data.MinDistance).append("   Best Solution :  ").append(IORSimulateAnealingPanel.this.controller.data.bestSolution)));
/*     */         }
/*     */       }
/*     */       else {
/* 747 */         output = "";
/*     */       }
/* 749 */       return output;
/*     */     }
/*     */     
/* 752 */     private class TSPTableModel extends javax.swing.table.AbstractTableModel { TSPTableModel(IORSimulateAnealingPanel..8 x$1) { this(); }
/*     */       
/*     */ 
/*     */       public int getColumnCount()
/*     */       {
/* 757 */         return IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.tableCol;
/*     */       }
/*     */       
/*     */       public int getRowCount()
/*     */       {
/* 762 */         return IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.tableRow;
/*     */       }
/*     */       
/*     */       public String getColumnName(int col)
/*     */       {
/* 767 */         String str = "";
/* 768 */         if (col == 0) {
/* 769 */           str = String.valueOf(String.valueOf(str)).concat("Iteration");
/* 770 */         } else if (col == 1) {
/* 771 */           str = String.valueOf(String.valueOf(str)).concat("T");
/* 772 */         } else if (col == 2) {
/* 773 */           str = String.valueOf(String.valueOf(str)).concat("Trial Solution");
/* 774 */         } else if (col == 3) {
/* 775 */           str = String.valueOf(String.valueOf(str)).concat("Distance");
/* 776 */         } else if (col == 4) {
/* 777 */           str = String.valueOf(String.valueOf(str)).concat("Acceptance Prob.");
/*     */         } else
/* 779 */           str = String.valueOf(String.valueOf(str)).concat("Accept");
/* 780 */         return str;
/*     */       }
/*     */       
/*     */       public Object getValueAt(int row, int col)
/*     */       {
/* 785 */         String str = "";
/* 786 */         int currentIteration = 1;
/* 787 */         int r = -1;int c = -1;
/* 788 */         if ((col == 0) && (row == 0)) {
/* 789 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(0)));
/*     */         }
/* 791 */         else if (col == 0) {
/* 792 */           if (row >= 2) {
/* 793 */             currentIteration = Integer.valueOf((String)getValueAt(row - 1, col)).intValue();
/* 794 */             String accept = (String)IORSimulateAnealingPanel.this.controller.data.auto_Accept_Vec.elementAt(row - 2);
/* 795 */             if (accept.equalsIgnoreCase("true")) {
/* 796 */               currentIteration++;
/* 797 */               str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(currentIteration)));
/*     */             } else {
/* 799 */               str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(currentIteration)));
/*     */             }
/*     */           } else {
/* 802 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(currentIteration)));
/*     */           }
/*     */         }
/* 805 */         else if (col == 1) {
/* 806 */           if (row == 0) {
/* 807 */             str = String.valueOf(String.valueOf(str)).concat("");
/*     */           }
/* 809 */           else if (IORSimulateAnealingPanel.this.controller.data.auto_T_Vec.size() != 0) {
/* 810 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((String)IORSimulateAnealingPanel.this.controller.data.auto_T_Vec.elementAt(row - 1))));
/*     */           }
/*     */         }
/* 813 */         else if (col == 2) {
/* 814 */           if (row == 0) {
/* 815 */             int[] solution = IORSimulateAnealingPanel.this.controller.data.initSolution;
/* 816 */             String s = "";
/* 817 */             for (int i = 0; i < solution.length; i++) {
/* 818 */               if (i != solution.length - 1) {
/* 819 */                 s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1).append("-"))))));
/*     */               } else {
/* 821 */                 s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1))))));
/*     */               }
/*     */             }
/* 824 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(s)));
/* 825 */             str = String.valueOf(String.valueOf(str)).concat("-1");
/*     */           }
/* 827 */           else if (IORSimulateAnealingPanel.this.controller.data.auto_Solution_Vec.size() != 0) {
/* 828 */             int[] solution = (int[])IORSimulateAnealingPanel.this.controller.data.auto_Solution_Vec.elementAt(row - 1);
/* 829 */             String s = "";
/* 830 */             for (int i = 0; i < solution.length; i++) {
/* 831 */               if (i != solution.length - 1) {
/* 832 */                 s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1).append("-"))))));
/*     */               } else {
/* 834 */                 s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1))))));
/*     */               }
/*     */             }
/* 837 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(s)));
/* 838 */             str = String.valueOf(String.valueOf(str)).concat("-1");
/*     */           }
/*     */         }
/* 841 */         else if (col == 3) {
/* 842 */           if (row == 0) {
/* 843 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORSimulateAnealingPanel.this.controller.data.initDistance)));
/*     */           }
/* 845 */           else if (IORSimulateAnealingPanel.this.controller.data.auto_Distance_Vec.size() != 0) {
/* 846 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((String)IORSimulateAnealingPanel.this.controller.data.auto_Distance_Vec.elementAt(row - 1))));
/*     */           }
/*     */         }
/* 849 */         else if (col == 4)
/*     */         {
/* 851 */           if (row == 0) {
/* 852 */             str = String.valueOf(String.valueOf(str)).concat("");
/*     */           }
/* 854 */           else if (IORSimulateAnealingPanel.this.controller.data.auto_Probability_Vec.size() != 0) {
/* 855 */             double tempD = Double.valueOf((String)IORSimulateAnealingPanel.this.controller.data.auto_Probability_Vec.elementAt(row - 1)).doubleValue();
/* 856 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(IORSimulateAnealingPanel.this.nf.format(tempD)))))));
/*     */           }
/*     */         } else { double tempD;
/* 859 */           if (col == 5) {
/* 860 */             if (row == 0) {
/* 861 */               str = String.valueOf(String.valueOf(str)).concat("");
/*     */             }
/* 863 */             else if (IORSimulateAnealingPanel.this.controller.data.auto_Probability_Vec.size() != 0) {
/* 864 */               str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((String)IORSimulateAnealingPanel.this.controller.data.auto_Accept_Vec.elementAt(row - 1))));
/*     */             }
/*     */           }
/*     */         }
/* 868 */         return str;
/*     */       }
/*     */       
/*     */       public Class getColumnClass(int c) {
/* 872 */         return getValueAt(0, c).getClass();
/*     */       }
/*     */       
/*     */       public boolean isCellEditable(int row, int col) {
/* 876 */         if (!IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.this.is_panel_enabled)
/* 877 */           return false;
/* 878 */         if (col == 0) {
/* 879 */           return false;
/*     */         }
/* 881 */         return true;
/*     */       }
/*     */       
/*     */ 
/*     */       private int i;
/*     */       private int j;
/*     */       public void setValueAt(Object value, int row, int col)
/*     */       {
/* 889 */         String input = (String)value;
/*     */         
/* 891 */         if ((col != 0) && (row != col - 1)) {
/*     */           try {
/* 893 */             int[] d = IORTSPActionPanel.parseStringInput(input);
/* 894 */             if (d == null) {
/* 895 */               return;
/*     */             }
/*     */             
/*     */           }
/*     */           catch (NumberFormatException e)
/*     */           {
/* 901 */             return;
/*     */           }
/*     */           
/* 904 */           fireTableCellUpdated(row, col - 1);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */       private TSPTableModel() {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void reset()
/*     */   {
/* 916 */     this.controller.solver.cleanOutput();
/* 917 */     this.canvasPanel.tableRow = 1;
/* 918 */     this.controller.data.currentLine = 1;
/* 919 */     this.controller.data.totalLine = 1;
/* 920 */     this.canvasPanel.tspModel.fireTableStructureChanged();
/*     */     
/* 922 */     this.controller.data.saveAuto_T_Vec = null;this.controller.data.saveAuto_T_Vec = new Vector();
/* 923 */     this.controller.data.saveAuto_Solution_Vec = null;this.controller.data.saveAuto_Solution_Vec = new Vector();
/* 924 */     this.controller.data.saveAuto_Accept_Vec = null;this.controller.data.saveAuto_Accept_Vec = new Vector();
/* 925 */     this.controller.data.saveAuto_Distance_Vec = null;this.controller.data.saveAuto_Distance_Vec = new Vector();
/* 926 */     this.controller.data.saveAuto_Probability_Vec = null;this.controller.data.saveAuto_Probability_Vec = new Vector();
/* 927 */     this.controller.data.saveCurrentTotalLine = null;this.controller.data.saveCurrentTotalLine = new Vector();
/*     */     
/*     */ 
/* 930 */     this.controller.data.auto_Solution_Vec = null;this.controller.data.auto_Solution_Vec = new Vector();
/* 931 */     this.controller.data.step_Accept_Vec = null;this.controller.data.step_Accept_Vec = new Vector();
/* 932 */     this.controller.data.step_Distance_Vec = null;this.controller.data.step_Distance_Vec = new Vector();
/* 933 */     this.controller.data.step_Probability_Vec = null;this.controller.data.step_Probability_Vec = new Vector();
/* 934 */     this.controller.data.step_Solution_Vec = null;this.controller.data.step_Solution_Vec = new Vector();
/* 935 */     this.controller.data.auto_T_Vec = null;this.controller.data.auto_T_Vec = new Vector();
/*     */     
/* 937 */     this.controller.data.autoTimes = 0;
/* 938 */     this.currentAutoTime = 0;
/* 939 */     this.controller.data.saveCurrentTotalLine = null;this.controller.data.saveCurrentTotalLine = new Vector();
/*     */     
/* 941 */     this.controller.data.SA_Auto_PrintData = null;
/* 942 */     this.controller.data.SA_Next_PrintData = null;
/* 943 */     this.controller.data.SA_BestSolutionInfoLab = "";
/* 944 */     this.controller.data.MinDistance = 0.0D;
/* 945 */     this.canvasPanel.resultLab.setText("");
/* 946 */     this.canvasPanel.backBut.setEnabled(false);
/* 947 */     this.controller.solver.resetSolver();
/* 948 */     this.canvasPanel.setTableFormat();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORSimulateAnealingPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */