/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ 
/*     */ public class IORTSPGeneticAlgorithmPanel extends IORTSPActionPanel implements java.awt.print.Printable
/*     */ {
/*     */   private IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel geneticCan;
/*  19 */   private java.text.DecimalFormat decfm = new java.text.DecimalFormat();
/*     */   
/*  21 */   public IORTSPGeneticAlgorithmPanel(IORTSPProcessController c) { super(c);
/*  22 */     setStep(1);
/*  23 */     addStep();
/*  24 */     createCanvasPanel();
/*  25 */     this.state = new IORState(c.solver);
/*  26 */     add(this.geneticCan, "North");
/*  27 */     this.decfm.setMaximumIntegerDigits(1);
/*     */   }
/*     */   
/*     */   private void createCanvasPanel() {
/*  31 */     this.geneticCan = new IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */ 
/*     */   protected void finishProcedure()
/*     */   {
/*  41 */     updatePanel();
/*  42 */     this.actionStatus.setText("The current procedure is finished. Please go to the Procedure menu to continue!");
/*  43 */     setFinishEnabled(false);
/*     */   }
/*     */   
/*     */   public void updatePanel()
/*     */   {
/*  48 */     if (this.controller.data.GACurrentTime != 0) {
/*  49 */       this.geneticCan.workIsFinish = true;
/*  50 */       this.geneticCan.tableRow = this.controller.data.GACurrentTime;
/*  51 */       this.geneticCan.backBut.setEnabled(true);
/*     */       
/*  53 */       if (this.controller.data.GACurrentTime == this.controller.data.GAtotalTimes) {
/*  54 */         this.geneticCan.nextBut.setEnabled(false);
/*     */       }
/*  56 */       this.geneticCan.resetBut.setEnabled(true);
/*     */       
/*  58 */       this.geneticCan.tableRow1 = this.controller.data.GA_Pop_And_Child_TotalLine;
/*  59 */       this.geneticCan.tableRow2 = this.controller.data.GA_Children_TotalLine;
/*  60 */       this.geneticCan.tspModel.fireTableStructureChanged();
/*  61 */       this.geneticCan.tspModel1.fireTableStructureChanged();
/*  62 */       this.geneticCan.tspModel2.fireTableStructureChanged();
/*     */     } else {
/*  64 */       this.geneticCan.tableRow = 0;
/*  65 */       this.geneticCan.backBut.setEnabled(false);
/*  66 */       this.geneticCan.nextBut.setEnabled(true);
/*  67 */       this.geneticCan.resetBut.setEnabled(true);
/*  68 */       this.geneticCan.tspModel.fireTableStructureChanged();
/*  69 */       this.geneticCan.tspModel1.fireTableStructureChanged();
/*  70 */       this.geneticCan.tspModel2.fireTableStructureChanged();
/*     */     }
/*  72 */     this.geneticCan.setTableBackInfo();
/*  73 */     this.geneticCan.countCurrentSolution();
/*  74 */     repaint();
/*     */   }
/*     */   
/*     */   protected void initializeCurrentStepHelpPanel() {
/*  78 */     String str = "Genetic Algorithm for Traveling Salesman Problem";
/*  79 */     str = String.valueOf(String.valueOf(str)).concat("\n\nClick “Next” to proceed to the next iteration; click “Back” to return to the previous iteration; click “Reset” to restart the problem solving procedure.");
/*  80 */     str = String.valueOf(String.valueOf(str)).concat("\n\nPress the ENTER key to continue the current procedure.");
/*  81 */     this.help_content_step.setText(str);
/*  82 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */   protected void initializeCurrentProcedureHelpPanel() {
/*  86 */     String str = "Genetic Algorithm for Traveling Salesman Problem";
/*  87 */     str = String.valueOf(String.valueOf(str)).concat("\n\nThis procedure uses the Genetic algorithm to solve a traveling salesman problem step by step. ");
/*  88 */     str = String.valueOf(String.valueOf(str)).concat("\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/*  90 */     this.help_content_procedure.setText(str);
/*  91 */     this.help_content_procedure.revalidate();
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
/*     */   public int print(java.awt.Graphics g, java.awt.print.PageFormat pf, int pi)
/*     */     throws java.awt.print.PrinterException
/*     */   {
/* 157 */     return super.print(g, pf, pi);
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
/* 170 */       out.writeObject(this.state);
/*     */     }
/*     */     catch (Exception e1) {
/* 173 */       System.out.println("Save fails".concat(String.valueOf(String.valueOf(e1))));
/*     */     }
/*     */   }
/*     */   
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
/* 188 */       this.state = ((IORState)in.readObject());
/*     */ 
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/*     */ 
/* 194 */       System.out.println("Load fails ".concat(String.valueOf(String.valueOf(e1))));
/* 195 */       return;
/*     */     }
/* 197 */     updatePanel();
/*     */   }
/*     */   
/*     */   public IOROperation getOperation(Vector vec)
/*     */   {
/* 202 */     IOROperation operation = new IOROperation(60014, vec);
/*     */     
/* 204 */     return operation;
/*     */   }
/*     */   
/*     */   public Vector initVector() {
/* 208 */     return new Vector();
/*     */   }
/*     */   
/*     */   public class IORTSPGeneticCanvasPanel extends JPanel {
/* 212 */     public JTable table = null;
/*     */     
/* 214 */     public String[] colName = { "Iteration", "Best Solution", "Fitness" };
/* 215 */     public int tableRow = 0;
/*     */     
/*     */     public int tableCol;
/* 218 */     public Color[] chromosomeColor = { Color.green, Color.blue, Color.red, Color.black };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 223 */     public int[][][] table1ColorInfo = new int[10][3][2];
/* 224 */     public int[][][] table2ColorInfo = new int[6][3][2];
/*     */     
/* 226 */     public JTable table1 = null;
/* 227 */     public String[] colName1 = { "Population", "Fitness" };
/* 228 */     public int tableRow1 = 0;
/*     */     
/*     */     public int tableCol1;
/* 231 */     public JTable table2 = null;
/* 232 */     public String[] colName2 = { "Children", "Fitness" };
/* 233 */     public int tableRow2 = 0;
/*     */     
/*     */     public int tableCol2;
/* 236 */     public IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.TSPTableModel tspModel = null;
/* 237 */     public IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.TSPTableModel1 tspModel1 = null;
/* 238 */     public IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.TSPTableModel2 tspModel2 = null;
/*     */     
/* 240 */     private Color default_color = Color.lightGray;
/* 241 */     private Color select_color = Color.white;
/*     */     
/*     */ 
/* 244 */     public static final int STATE_NONE = -1;
/*     */     
/* 246 */     private int state = -1;
/*     */     
/* 248 */     private IORState iorstate = null;
/*     */     
/* 250 */     private JScrollPane canPanel = null;
/* 251 */     private JScrollPane canPanel1 = null;
/* 252 */     private JScrollPane canPanel2 = null;
/*     */     
/* 254 */     private IORTSPActionPanel actionPanel = null;
/*     */     
/* 256 */     private boolean is_panel_enabled = true;
/*     */     
/* 258 */     private JPanel tablePanel = null;
/* 259 */     private JPanel tablePanel1 = null;
/* 260 */     private JPanel butPane = null;
/*     */     
/* 262 */     private JButton nextBut = null;
/* 263 */     private JButton backBut = null;
/*     */     
/* 265 */     private JButton resetBut = null;
/* 266 */     private JLabel finishInfoLab = null;
/* 267 */     private JLabel resultLab = null;
/*     */     
/* 269 */     private static final int canWid = 300;
/* 270 */     private static final int canHei = 250;
/*     */     
/* 272 */     private static final int canWid1 = 300;
/* 273 */     private static final int canHei1 = 250;
/* 274 */     private static final int canWid2 = 300;
/* 275 */     private static final int canHei2 = 220;
/*     */     
/*     */     public int colorRow;
/*     */     
/*     */     public int colorCol;
/* 280 */     public boolean workIsFinish = false;
/*     */     
/* 282 */     public double bestDis = 0.0D;
/* 283 */     public String Solution = "";
/*     */     
/*     */     public IORTSPGeneticCanvasPanel(IORTSPActionPanel p) {
/* 286 */       this.actionPanel = p;
/* 287 */       this.iorstate = p.getState();
/*     */       
/* 289 */       this.tableCol = this.colName.length;
/* 290 */       this.tspModel = new IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.TSPTableModel(null);
/* 291 */       this.table = new JTable(this.tspModel);
/* 292 */       this.table.setCellSelectionEnabled(false);
/* 293 */       this.table.setSelectionMode(0);
/* 294 */       this.table.setEnabled(false);
/*     */       
/* 296 */       this.tableCol1 = this.colName1.length;
/* 297 */       this.tspModel1 = new IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.TSPTableModel1(null);
/* 298 */       this.table1 = new JTable(this.tspModel1);
/* 299 */       this.table1.setCellSelectionEnabled(false);
/* 300 */       this.table1.setSelectionMode(0);
/* 301 */       this.table1.setEnabled(false);
/*     */       
/* 303 */       this.tableCol2 = this.colName2.length;
/* 304 */       this.tspModel2 = new IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.TSPTableModel2(null);
/* 305 */       this.table2 = new JTable(this.tspModel2);
/* 306 */       this.table2.setCellSelectionEnabled(false);
/* 307 */       this.table2.setSelectionMode(0);
/* 308 */       this.table2.setEnabled(false);
/* 309 */       this.table2.setGridColor(Color.black);
/*     */       
/*     */ 
/* 312 */       this.canPanel = new JScrollPane(this.table);
/* 313 */       this.canPanel.setPreferredSize(new Dimension(300, 250));
/* 314 */       this.canPanel.setMaximumSize(new Dimension(300, 250));
/* 315 */       this.canPanel.setAlignmentX(0.5F);
/* 316 */       this.canPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 40));
/*     */       
/* 318 */       this.canPanel1 = new JScrollPane(this.table1);
/* 319 */       this.canPanel1.setPreferredSize(new Dimension(300, 250));
/* 320 */       this.canPanel1.setMaximumSize(new Dimension(300, 250));
/* 321 */       this.canPanel1.setAlignmentX(0.5F);
/*     */       
/*     */ 
/*     */ 
/* 325 */       this.canPanel2 = new JScrollPane(this.table2);
/* 326 */       this.canPanel2.setPreferredSize(new Dimension(300, 220));
/* 327 */       this.canPanel2.setMaximumSize(new Dimension(300, 220));
/* 328 */       this.canPanel2.setAlignmentX(0.5F);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 336 */       this.nextBut = new JButton("Next");
/* 337 */       this.backBut = new JButton("Back");
/* 338 */       this.backBut.setEnabled(false);
/*     */       
/* 340 */       this.resetBut = new JButton("Reset");
/* 341 */       this.finishInfoLab = new JLabel("");
/* 342 */       this.resultLab = new JLabel("");
/* 343 */       this.resultLab.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
/* 344 */       this.finishInfoLab.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
/*     */       
/* 346 */       for (int i = 0; i < IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Children_TotalLine; i++) {
/* 347 */         this.table2ColorInfo[i][0][0] = i;
/* 348 */         this.table2ColorInfo[i][0][1] = (i / 2);
/*     */       }
/*     */       
/*     */ 
/* 352 */       this.backBut.addActionListener(new IORTSPGeneticAlgorithmPanel.1((IORTSPGeneticCanvasPanel)this));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 377 */       this.nextBut.addActionListener(new IORTSPGeneticAlgorithmPanel.2((IORTSPGeneticCanvasPanel)this));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 412 */       this.resetBut.addActionListener(new IORTSPGeneticAlgorithmPanel.3((IORTSPGeneticCanvasPanel)this));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 434 */       this.tablePanel = new JPanel();
/* 435 */       this.butPane = new JPanel();
/* 436 */       this.butPane.setLayout(new java.awt.FlowLayout());
/* 437 */       this.butPane.add(this.backBut);
/*     */       
/* 439 */       this.butPane.add(this.nextBut);
/*     */       
/* 441 */       this.butPane.add(this.resetBut);
/* 442 */       this.tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
/*     */       
/* 444 */       JLabel instrucLab = new JLabel("Mutations are denoted by [  ].");
/* 445 */       instrucLab.setForeground(Color.blue);
/* 446 */       instrucLab.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
/* 447 */       JPanel childPane = new JPanel();
/* 448 */       childPane.setLayout(new java.awt.BorderLayout());
/* 449 */       childPane.add(this.canPanel2, "Center");
/* 450 */       childPane.add(instrucLab, "South");
/*     */       
/* 452 */       this.tablePanel1 = new JPanel();
/* 453 */       this.tablePanel1.setLayout(new java.awt.FlowLayout());
/* 454 */       this.tablePanel1.add(this.canPanel1);
/* 455 */       this.tablePanel1.add(childPane);
/*     */       
/*     */ 
/* 458 */       this.tablePanel = new JPanel();
/* 459 */       this.tablePanel.setLayout(new java.awt.BorderLayout());
/*     */       
/* 461 */       this.tablePanel.add(this.canPanel, "Center");
/* 462 */       this.tablePanel.add(this.resultLab, "South");
/*     */       
/* 464 */       setLayout(new java.awt.BorderLayout());
/* 465 */       add(this.tablePanel, "North");
/* 466 */       add(this.tablePanel1, "Center");
/* 467 */       add(this.butPane, "South");
/* 468 */       setBackground(Color.white);
/*     */       
/* 470 */       this.table.setName("table");
/* 471 */       this.table1.setName("table1");
/* 472 */       setTableFormat();
/*     */     }
/*     */     
/*     */ 
/*     */     public void initNextPrintData(int tpye)
/*     */     {
/* 478 */       if (tpye == 1) {
/* 479 */         if (IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_BestSolution_Data != null) IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_BestSolution_Data = null;
/* 480 */         IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_BestSolution_Data = new String[this.tableRow][this.tableCol];
/*     */         
/* 482 */         for (int i = 0; i < this.tableRow; i++) {
/* 483 */           for (int j = 0; j < this.tableCol; j++) {
/* 484 */             IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_BestSolution_Data[i][j] = this.table.getValueAt(i, j).toString();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 490 */         if (IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_Pop_Data == null) IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_Pop_Data = new Vector();
/* 491 */         String[][] tempV = new String[this.tableRow1][this.tableCol1];
/* 492 */         for (int i = 0; i < this.tableRow1; i++) {
/* 493 */           for (int j = 0; j < this.tableCol1; j++) {
/* 494 */             tempV[i][j] = this.table1.getValueAt(i, j).toString();
/*     */           }
/*     */         }
/* 497 */         IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_Pop_Data.addElement(tempV);
/*     */         
/*     */ 
/*     */ 
/* 501 */         if (IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_Child_Data == null) IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_Child_Data = new Vector();
/* 502 */         tempV = null;
/* 503 */         tempV = new String[this.tableRow2][this.tableCol2];
/* 504 */         for (int i = 0; i < this.tableRow2; i++) {
/* 505 */           for (int j = 0; j < this.tableCol2; j++) {
/* 506 */             tempV[i][j] = this.table2.getValueAt(i, j).toString();
/*     */           }
/*     */         }
/* 509 */         IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_Child_Data.addElement(tempV);
/*     */         
/*     */ 
/* 512 */         if (IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_P_Connection_C_Data == null) IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_P_Connection_C_Data = new Vector();
/* 513 */         int[] colorInt = new int[this.tableRow1];
/* 514 */         for (int i = 0; i < colorInt.length; i++) {
/* 515 */           colorInt[i] = this.table1ColorInfo[i][0][1];
/*     */         }
/* 517 */         IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_P_Connection_C_Data.addElement(colorInt);
/*     */         
/* 519 */         if (IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_ChildNum == null) IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_ChildNum = new Vector();
/* 520 */         IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_ChildNum = IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Total_SelectParent;
/*     */         
/* 522 */         IORTSPGeneticAlgorithmPanel.this.controller.solver.initPrintData(IORTSPGeneticAlgorithmPanel.this.getOperation(new Vector()), IORTSPGeneticAlgorithmPanel.this.controller.data);
/*     */       } else {
/* 524 */         if (IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_BestSolution_Data != null) IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_BestSolution_Data = null;
/* 525 */         IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_BestSolution_Data = new String[this.tableRow][this.tableCol];
/*     */         
/* 527 */         for (int i = 0; i < this.tableRow; i++) {
/* 528 */           for (int j = 0; j < this.tableCol; j++) {
/* 529 */             IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_BestSolution_Data[i][j] = this.table.getValueAt(i, j).toString();
/*     */           }
/*     */         }
/* 532 */         if (IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_Pop_Data != null)
/* 533 */           IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_Pop_Data.removeElementAt(IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_Pop_Data.size() - 1);
/* 534 */         if (IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_Child_Data != null)
/* 535 */           IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_Child_Data.removeElementAt(IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_Child_Data.size() - 1);
/* 536 */         if (IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_P_Connection_C_Data != null) {
/* 537 */           IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_P_Connection_C_Data.removeElementAt(IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Print_P_Connection_C_Data.size() - 1);
/*     */         }
/*     */       }
/* 540 */       IORTSPGeneticAlgorithmPanel.this.controller.data.SA_BestSolutionInfoLab = this.resultLab.getText();
/*     */     }
/*     */     
/*     */     public void setTableBackColor(int[][][] matrix, JTable currentTab, Color[] backColor, String[] currentS)
/*     */     {
/* 545 */       javax.swing.table.DefaultTableCellRenderer tcr = new IORTSPGeneticAlgorithmPanel.4(matrix, backColor, currentTab);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 562 */       for (int i = 0; i < currentS.length; i++) {
/* 563 */         currentTab.getColumn(currentS[i]).setCellRenderer(tcr);
/*     */       }
/*     */     }
/*     */     
/*     */     public void setTableBackInfo()
/*     */     {
/* 569 */       if (IORTSPGeneticAlgorithmPanel.this.controller.data.GACurrentTime != 0) {
/* 570 */         Vector colorV = (Vector)IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Total_SelectParent.elementAt(IORTSPGeneticAlgorithmPanel.this.controller.data.GACurrentTime - 1);
/* 571 */         for (int i = 0; i < IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Pop_And_Child_TotalLine; i++) {
/* 572 */           this.table1ColorInfo[i][0][0] = i;
/* 573 */           this.table1ColorInfo[i][1][0] = 0;
/* 574 */           this.table1ColorInfo[i][0][1] = (this.chromosomeColor.length - 1);
/*     */         }
/* 576 */         for (int i = 0; i < colorV.size(); i++) {
/* 577 */           Integer colorNum = (Integer)colorV.elementAt(i);
/* 578 */           this.table1ColorInfo[colorNum.intValue()][0][1] = (i / 2);
/*     */         }
/* 580 */         setTableBackColor(this.table1ColorInfo, this.table1, this.chromosomeColor, this.colName1);
/* 581 */         setTableBackColor(this.table2ColorInfo, this.table2, this.chromosomeColor, this.colName2);
/*     */       }
/*     */     }
/*     */     
/*     */     public void setTableFormat() {
/* 586 */       TableColumn column = new TableColumn();
/* 587 */       this.table1.getColumnModel().getColumn(0).setPreferredWidth(170);
/* 588 */       this.table1.getColumnModel().getColumn(1).setPreferredWidth(70);
/* 589 */       this.table2.getColumnModel().getColumn(0).setPreferredWidth(170);
/* 590 */       this.table2.getColumnModel().getColumn(1).setPreferredWidth(70);
/*     */     }
/*     */     
/*     */ 
/*     */     public void countCurrentSolution()
/*     */     {
/* 596 */       double temp = 0.0D;
/* 597 */       if (this.table.getRowCount() == 1) {
/* 598 */         this.bestDis = ((Double)IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Fitness_Vec.elementAt(0)).doubleValue();
/* 599 */         this.resultLab.setText(String.valueOf(String.valueOf(new StringBuffer("Best Distance = ").append(this.bestDis).append(" Best Solution = ").append(this.table.getValueAt(0, 1)))));
/* 600 */         return;
/*     */       }
/* 602 */       for (int i = 0; i < this.table.getRowCount(); i++) {
/* 603 */         temp = ((Double)IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Fitness_Vec.elementAt(i)).doubleValue();
/* 604 */         if (this.bestDis > temp) {
/* 605 */           this.bestDis = temp;
/* 606 */           this.Solution = ((String)this.table.getValueAt(i, 1));
/* 607 */           this.resultLab.setText(String.valueOf(String.valueOf(new StringBuffer("Best Distance = ").append(this.bestDis).append(" Best Solution = ").append(this.Solution))));
/*     */         } } }
/*     */     
/*     */     private class TSPTableModel extends AbstractTableModel { private int i;
/*     */       private int j;
/*     */       
/* 613 */       TSPTableModel(IORTSPGeneticAlgorithmPanel..5 x$1) { this(); }
/*     */       
/*     */ 
/*     */       public int getColumnCount()
/*     */       {
/* 618 */         return IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.tableCol;
/*     */       }
/*     */       
/*     */       public int getRowCount()
/*     */       {
/* 623 */         return IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.tableRow;
/*     */       }
/*     */       
/*     */ 
/*     */       public String getColumnName(int col)
/*     */       {
/* 629 */         String str = "";
/* 630 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.colName[col])));
/* 631 */         return str;
/*     */       }
/*     */       
/*     */       public Object getValueAt(int row, int col)
/*     */       {
/* 636 */         String str = "";
/* 637 */         int r = -1;int c = -1;
/* 638 */         if ((col == 0) && (row == 0)) {
/* 639 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(1)));
/*     */         }
/* 641 */         else if (col == 0) {
/* 642 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(row + 1)));
/*     */         }
/* 644 */         else if (col == 1) {
/* 645 */           int[] solution = (int[])IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Solution_Vec.elementAt(row);
/*     */           
/* 647 */           for (int i = 0; i < solution.length; i++) {
/* 648 */             if (i != solution.length - 1) {
/* 649 */               str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1).append("-"))))));
/*     */             }
/*     */             else {
/* 652 */               str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1))))));
/*     */             }
/*     */           }
/* 655 */           str = String.valueOf(String.valueOf(str)).concat("-1");
/*     */         }
/* 657 */         else if (col == 2) {
/* 658 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf((Double)IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Fitness_Vec.elementAt(row)))))));
/*     */         }
/*     */         
/* 661 */         return str;
/*     */       }
/*     */       
/*     */       public Class getColumnClass(int c) {
/* 665 */         return getValueAt(0, c).getClass();
/*     */       }
/*     */       
/*     */       public boolean isCellEditable(int row, int col) {
/* 669 */         if (!IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.is_panel_enabled)
/* 670 */           return false;
/* 671 */         if (col == 0) {
/* 672 */           return false;
/*     */         }
/* 674 */         return true;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 679 */       public void setValueAt(Object value, int row, int col) { String input = (String)value; }
/*     */       
/*     */       private TSPTableModel() {} }
/*     */     
/*     */     private class TSPTableModel1 extends AbstractTableModel { private int i;
/*     */       
/* 685 */       TSPTableModel1(IORTSPGeneticAlgorithmPanel..5 x$1) { this(); }
/*     */       
/*     */ 
/*     */       public int getColumnCount()
/*     */       {
/* 690 */         return IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.tableCol1;
/*     */       }
/*     */       
/*     */       public int getRowCount()
/*     */       {
/* 695 */         return IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.tableRow1;
/*     */       }
/*     */       
/*     */       public String getColumnName(int col)
/*     */       {
/* 700 */         String str = "";
/* 701 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.colName1[col])));
/* 702 */         return str;
/*     */       }
/*     */       
/*     */       public Object getValueAt(int row, int col)
/*     */       {
/* 707 */         String str = "";
/* 708 */         Vector tempV = new Vector();
/* 709 */         int r = -1;int c = -1;
/*     */         
/* 711 */         if (col == 0) {
/* 712 */           if (IORTSPGeneticAlgorithmPanel.this.controller.data.GACurrentTime != 0) {
/* 713 */             tempV = (Vector)IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Total_Pop_Vec.elementAt(IORTSPGeneticAlgorithmPanel.this.controller.data.GACurrentTime - 1);
/* 714 */             int[] solution = (int[])tempV.elementAt(row);
/* 715 */             for (int i = 0; i < solution.length; i++) {
/* 716 */               if (i != solution.length - 1) {
/* 717 */                 str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1).append("-"))))));
/*     */               }
/*     */               else {
/* 720 */                 str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1))))));
/*     */               }
/*     */             }
/* 723 */             str = String.valueOf(String.valueOf(str)).concat("-1");
/*     */           }
/*     */         }
/* 726 */         else if ((col == 1) && 
/* 727 */           (IORTSPGeneticAlgorithmPanel.this.controller.data.GACurrentTime != 0)) {
/* 728 */           tempV = (Vector)IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Total_PopFit_Vec.elementAt(IORTSPGeneticAlgorithmPanel.this.controller.data.GACurrentTime - 1);
/* 729 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf((Double)tempV.elementAt(row)))))));
/*     */         }
/*     */         
/*     */ 
/* 733 */         return str;
/*     */       }
/*     */       
/*     */       public Class getColumnClass(int c) {
/* 737 */         return getValueAt(0, c).getClass();
/*     */       }
/*     */       
/*     */       public boolean isCellEditable(int row, int col) {
/* 741 */         if (!IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.is_panel_enabled)
/* 742 */           return false;
/* 743 */         if (col == 0) {
/* 744 */           return false;
/*     */         }
/* 746 */         return true;
/*     */       }
/*     */       
/*     */ 
/*     */       private int j;
/* 751 */       public void setValueAt(Object value, int row, int col) { String input = (String)value; }
/*     */       
/*     */       private TSPTableModel1() {}
/*     */     }
/*     */     
/* 756 */     private class TSPTableModel2 extends AbstractTableModel { TSPTableModel2(IORTSPGeneticAlgorithmPanel..5 x$1) { this(); }
/*     */       
/*     */       private int i;
/*     */       private int j;
/*     */       public int getColumnCount() {
/* 761 */         return IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.tableCol2;
/*     */       }
/*     */       
/*     */       public int getRowCount()
/*     */       {
/* 766 */         return IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.tableRow2;
/*     */       }
/*     */       
/*     */       public String getColumnName(int col)
/*     */       {
/* 771 */         String str = "";
/* 772 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.colName2[col])));
/* 773 */         return str;
/*     */       }
/*     */       
/*     */       public Object getValueAt(int row, int col)
/*     */       {
/* 778 */         String str = "";
/*     */         
/* 780 */         int r = -1;int c = -1;
/* 781 */         if (col == 0) {
/* 782 */           if (IORTSPGeneticAlgorithmPanel.this.controller.data.GACurrentTime != 0) {
/* 783 */             Vector breakChild = (Vector)IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Total_ChildBreak_Vec.elementAt(IORTSPGeneticAlgorithmPanel.this.controller.data.GACurrentTime - 1);
/* 784 */             int[] breakIn = (int[])breakChild.elementAt(row);
/*     */             
/* 786 */             Vector tempV1 = (Vector)IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Total_Child_Vec.elementAt(IORTSPGeneticAlgorithmPanel.this.controller.data.GACurrentTime - 1);
/*     */             
/* 788 */             int[] solution = (int[])tempV1.elementAt(row);
/* 789 */             for (int i = 0; i < solution.length; i++)
/*     */             {
/* 791 */               if (i != solution.length - 1) {
/* 792 */                 if (breakIn[i] != 0) {
/* 793 */                   str = String.valueOf(String.valueOf(str)).concat("[");
/*     */                 }
/* 795 */                 str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1))))));
/* 796 */                 if (breakIn[i] != 0) {
/* 797 */                   str = String.valueOf(String.valueOf(str)).concat("]");
/*     */                 }
/* 799 */                 str = String.valueOf(String.valueOf(str)).concat("-");
/*     */               }
/*     */               else {
/* 802 */                 if (breakIn[i] != 0) {
/* 803 */                   str = String.valueOf(String.valueOf(str)).concat("[");
/*     */                 }
/* 805 */                 str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1))))));
/* 806 */                 if (breakIn[i] != 0) {
/* 807 */                   str = String.valueOf(String.valueOf(str)).concat("]");
/*     */                 }
/*     */               }
/*     */             }
/* 811 */             str = String.valueOf(String.valueOf(str)).concat("-1");
/*     */           }
/*     */         }
/* 814 */         else if ((col == 1) && 
/* 815 */           (IORTSPGeneticAlgorithmPanel.this.controller.data.GACurrentTime != 0)) {
/* 816 */           Vector tempV1 = (Vector)IORTSPGeneticAlgorithmPanel.this.controller.data.GA_Total_ChildFitn_Vec.elementAt(IORTSPGeneticAlgorithmPanel.this.controller.data.GACurrentTime - 1);
/* 817 */           double temD = ((Double)tempV1.elementAt(row)).doubleValue();
/* 818 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(temD))))));
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 823 */         return str;
/*     */       }
/*     */       
/*     */       public Class getColumnClass(int c) {
/* 827 */         return getValueAt(0, c).getClass();
/*     */       }
/*     */       
/*     */       public boolean isCellEditable(int row, int col) {
/* 831 */         if (!IORTSPGeneticAlgorithmPanel.IORTSPGeneticCanvasPanel.this.is_panel_enabled)
/* 832 */           return false;
/* 833 */         if (col == 0) {
/* 834 */           return false;
/*     */         }
/* 836 */         return true;
/*     */       }
/*     */       
/*     */       public void setValueAt(Object value, int row, int col)
/*     */       {
/* 841 */         String input = (String)value;
/*     */       }
/*     */       
/*     */       private TSPTableModel2() {}
/*     */     }
/*     */   }
/*     */   
/*     */   public void reset() {
/* 849 */     setFinishEnabled(true);
/* 850 */     this.geneticCan.resultLab.setText("");
/* 851 */     this.geneticCan.backBut.setEnabled(false);
/* 852 */     this.geneticCan.nextBut.setEnabled(true);
/* 853 */     this.geneticCan.workIsFinish = false;
/* 854 */     this.controller.data.GACurrentTime = 0;
/* 855 */     this.controller.data.GA_Solution_Vec.clear();
/* 856 */     this.controller.data.GA_Child_Vec.clear();
/* 857 */     this.controller.data.GA_ChildFitn_Vec.clear();
/*     */     
/* 859 */     this.controller.data.GA_Fitness_Vec.clear();
/* 860 */     this.controller.data.GA_Total_PopFit_Vec.clear();
/* 861 */     this.controller.data.GA_Pop_Vec.clear();
/* 862 */     this.controller.data.GA_Total_Pop_Vec.clear();
/* 863 */     this.geneticCan.tableRow = this.controller.data.GACurrentTime;
/* 864 */     this.geneticCan.tspModel.fireTableStructureChanged();
/* 865 */     this.geneticCan.table.sizeColumnsToFit(-1);
/*     */     
/* 867 */     this.controller.data.GA_Pop_And_Child_TotalLine = 0;
/* 868 */     this.geneticCan.tableRow1 = this.controller.data.GA_Pop_And_Child_TotalLine;
/* 869 */     this.geneticCan.tspModel1.fireTableStructureChanged();
/* 870 */     this.geneticCan.table1.sizeColumnsToFit(-1);
/*     */     
/* 872 */     this.controller.data.GA_Child_Vec.clear();
/* 873 */     this.controller.data.GA_ChildFitn_Vec.clear();
/* 874 */     this.controller.data.GA_Total_Child_Vec.clear();
/* 875 */     this.controller.data.GA_Total_ChildFitn_Vec.clear();
/* 876 */     this.controller.data.GA_SelectParent.clear();
/* 877 */     this.controller.data.GA_Total_SelectParent.clear();
/* 878 */     this.controller.data.GA_Total_ChildBreak_Vec.clear();
/*     */     
/* 880 */     this.controller.data.GA_Print_P_Connection_C_Data = null;
/* 881 */     this.controller.data.GA_Print_ChildNum = null;
/* 882 */     this.controller.data.GA_Print_BestSolution_Data = null;
/* 883 */     this.controller.data.GA_Print_Child_Data = null;
/* 884 */     this.controller.data.GA_Print_Pop_Data = null;
/*     */     
/* 886 */     this.geneticCan.tableRow2 = 0;
/* 887 */     this.geneticCan.tspModel2.fireTableStructureChanged();
/* 888 */     this.geneticCan.table2.sizeColumnsToFit(-1);
/* 889 */     this.geneticCan.setTableFormat();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTSPGeneticAlgorithmPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */