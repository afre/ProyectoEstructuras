/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FlowLayout;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.awt.event.ItemListener;
/*      */ import java.util.Vector;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JRadioButton;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.border.TitledBorder;
/*      */ import javax.swing.table.AbstractTableModel;
/*      */ import javax.swing.table.DefaultTableCellRenderer;
/*      */ import javax.swing.table.TableColumn;
/*      */ import org.coinor.opents.Move;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IORTSPTabuSearchPanel$IORTSPTabuCanvasPanel
/*      */   extends JPanel
/*      */   implements ItemListener
/*      */ {
/*      */   public JTable table;
/*      */   public int tableRow;
/*      */   public int tableCol;
/*      */   public IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.TSPTableModel tspModel;
/*      */   private Color default_color;
/*      */   private Color select_color;
/*  387 */   public static final int STATE_NONE = -1;
/*      */   
/*      */   private int state;
/*      */   
/*      */   private IORState iorstate;
/*      */   
/*      */   private JScrollPane canPanel;
/*      */   
/*      */   private IORTSPActionPanel actionPanel;
/*      */   
/*      */   private boolean is_panel_enabled;
/*      */   
/*      */   private JPanel butPane;
/*      */   
/*      */   private JButton interactiveBut;
/*      */   
/*      */   private JButton nextBut;
/*      */   
/*      */   private JButton backBut;
/*      */   
/*      */   private JButton autoBut;
/*      */   
/*      */   private JButton resetBut;
/*      */   
/*      */   private JLabel tableInfoLab;
/*      */   private JPanel inputPanel;
/*      */   private JComboBox solutionBox;
/*      */   private JLabel distanceLab;
/*      */   private JTextField[] tabuListT;
/*      */   private ButtonGroup radio;
/*      */   private JRadioButton autoRadio;
/*      */   private JRadioButton interRadio;
/*      */   private boolean isFirstInteractive;
/*      */   private boolean inputIsWrong;
/*      */   private int[] currentSolution;
/*      */   private String currentDistance;
/*      */   private String currentTabuList;
/*      */   private Vector currentTabuListInt;
/*      */   private Move move;
/*      */   private boolean loadState;
/*      */   private boolean firstStep;
/*      */   private final IORTSPTabuSearchPanel this$0;
/*      */   
/*      */   public void itemStateChanged(ItemEvent e)
/*      */   {
/*  432 */     if (e.getSource() == this.interRadio) {
/*  433 */       this.this$0.controller.data.isAuto = "false";
/*  434 */       if (!this.loadState) {
/*  435 */         if (this.this$0.controller.data.TB_currentTime == 0) {
/*  436 */           this.isFirstInteractive = true;
/*      */         }
/*      */         else {
/*  439 */           this.isFirstInteractive = false;
/*      */         }
/*  441 */         if (this.isFirstInteractive) {
/*  442 */           this.this$0.initData();
/*  443 */           this.this$0.controller.solver.doWork(getOperation(initVector()), this.this$0.controller.data);
/*      */           
/*  445 */           this.isFirstInteractive = false;
/*  446 */           this.this$0.controller.data.TB_currentTime += 1;
/*  447 */           this.this$0.controller.data.TB_Totaltimes += 1;
/*  448 */           this.tableRow = this.this$0.controller.data.TB_currentTime;
/*  449 */           this.tspModel.fireTableStructureChanged();
/*  450 */           addItemWithCombox();
/*  451 */           initPrintData();
/*      */         }
/*      */       }
/*      */       else {
/*  455 */         this.loadState = false;
/*      */       }
/*      */     }
/*  458 */     else if (e.getSource() == this.autoRadio) {
/*  459 */       if (!this.loadState) {
/*  460 */         this.this$0.controller.data.isAuto = "true";
/*  461 */         this.tableRow = 0;
/*  462 */         this.tspModel.fireTableStructureChanged();
/*  463 */         this.nextBut.setEnabled(true);
/*  464 */         this.backBut.setEnabled(false);
/*  465 */         this.this$0.initData();
/*      */       }
/*      */       else {
/*  468 */         this.loadState = false;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IORTSPTabuSearchPanel$IORTSPTabuCanvasPanel(IORTSPTabuSearchPanel this$0, IORTSPActionPanel p, int s)
/*      */   {
/*  480 */     this.this$0 = this$0;this.table = null;this.tableRow = 0;this.tableCol = 4;this.tspModel = null;this.default_color = Color.lightGray;this.select_color = Color.white;this.state = -1;this.iorstate = null;this.canPanel = null;this.actionPanel = null;this.is_panel_enabled = true;this.butPane = null;this.interactiveBut = null;this.nextBut = null;this.backBut = null;this.autoBut = null;this.resetBut = null;this.inputPanel = null;this.solutionBox = null;this.distanceLab = null;this.tabuListT = new JTextField[8];this.isFirstInteractive = true;this.inputIsWrong = false;this.loadState = false;this.firstStep = true;
/*      */     
/*  482 */     this.state = s;
/*      */     
/*  484 */     this.actionPanel = p;
/*  485 */     this.iorstate = p.getState();
/*      */     
/*  487 */     this.tspModel = new IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.TSPTableModel(null);
/*  488 */     this.table = new JTable(this.tspModel);
/*  489 */     this.table.setCellSelectionEnabled(true);
/*  490 */     this.table.setSelectionMode(0);
/*  491 */     this.table.setEnabled(false);
/*  492 */     this.tspModel.fireTableStructureChanged();
/*      */     
/*  494 */     JPanel tablePanel = new JPanel();
/*      */     
/*  496 */     this.canPanel = new JScrollPane(this.table);
/*  497 */     this.canPanel.setPreferredSize(new Dimension(600, 190));
/*  498 */     this.canPanel.setMaximumSize(new Dimension(480, 190));
/*  499 */     this.canPanel.setAlignmentX(0.5F);
/*  500 */     this.canPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
/*      */     
/*  502 */     this.inputPanel = new JPanel();
/*      */     
/*      */ 
/*  505 */     JPanel inputPanel1 = new JPanel();
/*  506 */     this.distanceLab = new JLabel("  Distance        ");
/*      */     
/*  508 */     this.solutionBox = new JComboBox();
/*  509 */     this.solutionBox.addItem("Trial Solution           ");
/*  510 */     this.solutionBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*  511 */     inputPanel1.add(this.solutionBox);
/*  512 */     inputPanel1.add(this.distanceLab);
/*  513 */     inputPanel1.add(new JLabel("         "));
/*  514 */     for (int i = 0; i < this.tabuListT.length; i++) {
/*  515 */       this.tabuListT[i] = new JTextField(null, 2);
/*      */     }
/*      */     
/*  518 */     JPanel tabuListJP1 = new JPanel();
/*  519 */     tabuListJP1.add(this.tabuListT[0]);
/*  520 */     tabuListJP1.add(new JLabel("-"));
/*  521 */     tabuListJP1.add(this.tabuListT[1]);
/*  522 */     tabuListJP1.setBorder(new TitledBorder(null, null, 0, 0));
/*      */     
/*  524 */     JPanel tabuListJP2 = new JPanel();
/*  525 */     tabuListJP2.add(this.tabuListT[2]);
/*  526 */     tabuListJP2.add(new JLabel("-"));
/*  527 */     tabuListJP2.add(this.tabuListT[3]);
/*  528 */     tabuListJP2.setBorder(new TitledBorder(null, null, 0, 0));
/*      */     
/*  530 */     JPanel tabuListJP3 = new JPanel();
/*  531 */     tabuListJP3.add(this.tabuListT[4]);
/*  532 */     tabuListJP3.add(new JLabel("-"));
/*  533 */     tabuListJP3.add(this.tabuListT[5]);
/*  534 */     tabuListJP3.setBorder(new TitledBorder(null, null, 0, 0));
/*      */     
/*  536 */     JPanel tabuListJP4 = new JPanel();
/*  537 */     tabuListJP4.add(this.tabuListT[6]);
/*  538 */     tabuListJP4.add(new JLabel("-"));
/*  539 */     tabuListJP4.add(this.tabuListT[7]);
/*      */     
/*  541 */     tabuListJP4.setBorder(new TitledBorder(null, null, 0, 0));
/*  542 */     this.inputPanel.add(new JLabel("Tabu List :  "));
/*  543 */     this.inputPanel.add(tabuListJP1);
/*      */     
/*      */ 
/*  546 */     this.inputPanel.add(tabuListJP2);
/*  547 */     this.inputPanel.add(tabuListJP3);
/*  548 */     this.inputPanel.add(tabuListJP4);
/*  549 */     this.inputPanel.add(new JLabel("Example 2 - 4"));
/*      */     
/*  551 */     tablePanel.setLayout(new BorderLayout());
/*  552 */     tablePanel.add(this.canPanel, "North");
/*      */     
/*      */ 
/*      */ 
/*  556 */     this.butPane = new JPanel();
/*  557 */     this.interactiveBut = new JButton("Interactive");
/*      */     
/*  559 */     this.radio = new ButtonGroup();
/*  560 */     this.autoRadio = new JRadioButton("Auto   ", true);
/*      */     
/*  562 */     this.interRadio = new JRadioButton("Interactive   ", false);
/*      */     
/*  564 */     this.radio.add(this.autoRadio);
/*  565 */     this.radio.add(this.interRadio);
/*  566 */     this.autoRadio.addItemListener(this);
/*  567 */     this.autoRadio.setFocusPainted(false);
/*      */     
/*  569 */     this.interRadio.addItemListener(this);
/*  570 */     this.interRadio.setFocusPainted(false);
/*  571 */     this.nextBut = new JButton("Next");
/*      */     
/*  573 */     this.backBut = new JButton("Back");
/*  574 */     this.backBut.setEnabled(false);
/*  575 */     this.autoBut = new JButton("Auto");
/*  576 */     this.resetBut = new JButton("Reset");
/*      */     
/*  578 */     this.butPane.setLayout(new FlowLayout());
/*      */     
/*      */ 
/*      */ 
/*  582 */     this.butPane.add(this.backBut);
/*  583 */     this.butPane.add(this.nextBut);
/*  584 */     this.butPane.add(this.resetBut);
/*      */     
/*  586 */     this.butPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
/*      */     
/*  588 */     this.nextBut.addActionListener(new IORTSPTabuSearchPanel.1((IORTSPTabuCanvasPanel)this));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  672 */     this.backBut.addActionListener(new IORTSPTabuSearchPanel.2((IORTSPTabuCanvasPanel)this));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  745 */     this.resetBut.addActionListener(new IORTSPTabuSearchPanel.3((IORTSPTabuCanvasPanel)this));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  758 */     this.solutionBox.addActionListener(new IORTSPTabuSearchPanel.4((IORTSPTabuCanvasPanel)this));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  783 */     this.tableInfoLab = new JLabel("");
/*      */     
/*  785 */     this.tableInfoLab.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
/*      */     
/*  787 */     setLayout(new BorderLayout());
/*  788 */     add(this.tableInfoLab, "North");
/*  789 */     add(tablePanel, "Center");
/*  790 */     add(this.butPane, "South");
/*      */     
/*  792 */     initInputState();
/*  793 */     initInputState();
/*  794 */     this.isFirstInteractive = true;
/*      */   }
/*      */   
/*      */   private void clearTextInput()
/*      */   {
/*  799 */     for (int i = 0; i < this.tabuListT.length; i++) {
/*  800 */       this.tabuListT[i].setText("");
/*      */     }
/*      */   }
/*      */   
/*      */   private void getCurrentSolution() {
/*  805 */     Vector tempSoluV = this.this$0.controller.data.TB_currentSolutionV;
/*  806 */     String tempSolu = (String)tempSoluV.elementAt(this.this$0.controller.data.selectDistance);
/*      */     
/*  808 */     this.currentSolution = new int[tempSolu.length() / 2 + 1];
/*  809 */     for (int i = 0; i < this.currentSolution.length; i++) {
/*  810 */       this.currentSolution[i] = (Integer.valueOf(tempSolu.substring(i * 2, i * 2 + 1)).intValue() - 1);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void addTableItem(Vector data, int[][][] array)
/*      */   {
/*  817 */     this.this$0.controller.data.TB_InterSolution_Vec.addElement(this.currentSolution);
/*  818 */     this.this$0.controller.data.TB_InterDistance_Vec.addElement((Double)this.this$0.controller.data.TB_comBoxDistanceV.elementAt(this.this$0.controller.data.selectDistance));
/*      */     
/*      */ 
/*  821 */     this.this$0.controller.data.TB_InterTabuArray_Vec.addElement(array);
/*      */     
/*  823 */     if (data != null) {
/*  824 */       int[] list = (int[])data.elementAt(0);
/*  825 */       String tabuList = "";
/*  826 */       for (int i = 0; i < list.length; i += 2) {
/*  827 */         if (i + 2 == list.length) {
/*  828 */           tabuList = String.valueOf(String.valueOf(tabuList)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(list[i]))).append("-").append(list[(i + 1)]))))));
/*      */         }
/*      */         else {
/*  831 */           tabuList = String.valueOf(String.valueOf(tabuList)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(list[i]))).append("-").append(list[(i + 1)]).append(","))))));
/*      */         }
/*      */       }
/*  834 */       this.this$0.controller.data.TB_InterTabuList_Vec.addElement(tabuList);
/*      */     }
/*      */     else {
/*  837 */       this.this$0.controller.data.TB_InterTabuList_Vec.addElement("");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void countBestSolution()
/*      */   {
/*  844 */     this.this$0.controller.data.bestSolutionNum = new Vector();
/*  845 */     int bestNum = 0;
/*  846 */     if (this.table.getRowCount() == 0) return;
/*  847 */     double tempMinDis = Double.valueOf((String)this.table.getValueAt(0, 2)).doubleValue();
/*  848 */     this.this$0.controller.data.MinDistance = Double.valueOf((String)this.table.getValueAt(0, 2)).doubleValue();
/*  849 */     this.this$0.controller.data.bestSolution = ((String)this.table.getValueAt(0, 1));
/*  850 */     for (int i = 0; i < this.table.getRowCount(); i++) {
/*  851 */       tempMinDis = Double.valueOf((String)this.table.getValueAt(i, 2)).doubleValue();
/*  852 */       if (tempMinDis < this.this$0.controller.data.MinDistance) {
/*  853 */         this.this$0.controller.data.MinDistance = tempMinDis;
/*  854 */         bestNum = i;
/*  855 */         this.this$0.controller.data.bestSolution = ((String)this.table.getValueAt(i, 1));
/*      */       }
/*      */     }
/*  858 */     this.this$0.controller.data.bestSolutionNum.addElement(new Integer(bestNum));
/*  859 */     this.tableInfoLab.setText(String.valueOf(String.valueOf(new StringBuffer("Best Distance = ").append(this.this$0.controller.data.MinDistance).append("      Best Solution = ").append(this.this$0.controller.data.bestSolution))));
/*      */   }
/*      */   
/*      */   public void setColorFonBestSolution()
/*      */   {
/*  864 */     countBestSolution();
/*      */     
/*  866 */     DefaultTableCellRenderer tcr = new IORTSPTabuSearchPanel.5((IORTSPTabuCanvasPanel)this);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  895 */     String[] identifier = { "Iteration", "Trial Solution", "Distance", "Tabu List" };
/*  896 */     for (int i = 0; i < identifier.length; i++) {
/*  897 */       this.table.getColumn(identifier[i]).setCellRenderer(tcr);
/*      */     }
/*  899 */     this.table.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean showDialog(boolean showDialog)
/*      */   {
/*  905 */     if (showDialog) {
/*  906 */       JOptionPane.showMessageDialog(this, "Incorrect tabu list! Try again!");
/*      */       
/*  908 */       this.inputIsWrong = false;
/*  909 */       return false;
/*      */     }
/*      */     
/*  912 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   private int[][][] checkInputData()
/*      */   {
/*  918 */     this.currentTabuListInt = null;
/*  919 */     this.currentTabuListInt = new Vector();
/*  920 */     int[] temp = new int[8];
/*  921 */     Vector intV = new Vector();
/*  922 */     int[][][] inputData = new int[2][2][1];
/*  923 */     for (int i = 0; i < this.tabuListT.length; i++) {
/*  924 */       if ((this.tabuListT[i].getText().length() != 1) && (this.tabuListT[i].getText().length() != 0))
/*      */       {
/*  926 */         this.inputIsWrong = true;
/*  927 */         break;
/*      */       }
/*      */       try {
/*  930 */         if (this.tabuListT[i].getText().length() == 1) {
/*  931 */           temp[i] = Integer.valueOf(this.tabuListT[i].getText()).intValue();
/*      */         }
/*      */         else
/*      */         {
/*  935 */           temp[i] = Integer.MAX_VALUE;
/*      */         }
/*      */       }
/*      */       catch (NumberFormatException e) {
/*  939 */         JOptionPane.showMessageDialog(this, "Incorrect input formats! Try again!");
/*      */         
/*  941 */         this.tabuListT[i].setText("");
/*      */       }
/*      */     }
/*  944 */     for (int i = 0; i < this.tabuListT.length; i += 2) {
/*  945 */       if ((temp[i] == Integer.MAX_VALUE) && (temp[(i + 1)] != Integer.MAX_VALUE))
/*      */       {
/*  947 */         this.inputIsWrong = true;
/*  948 */         break;
/*      */       }
/*  950 */       if (temp[i] != Integer.MAX_VALUE) {
/*  951 */         intV.addElement(new Integer(temp[i]));
/*      */       }
/*  953 */       if (temp[(i + 1)] != Integer.MAX_VALUE) {
/*  954 */         intV.addElement(new Integer(temp[(i + 1)]));
/*      */       }
/*      */     }
/*  957 */     if (intV.size() % 4 != 0) {
/*  958 */       this.inputIsWrong = true;
/*      */ 
/*      */ 
/*      */     }
/*  962 */     else if (intV.size() == 8) {
/*  963 */       inputData = new int[2][2][2];
/*      */       
/*  965 */       int[] tempIn = new int[8];
/*  966 */       for (int i = 0; i < intV.size(); i++) {
/*  967 */         Integer temp_Input = (Integer)intV.elementAt(i);
/*  968 */         tempIn[i] = temp_Input.intValue();
/*      */       }
/*  970 */       this.currentTabuListInt.addElement(tempIn);
/*  971 */       int[][][] inputData1 = { { { tempIn[0], tempIn[1] }, { tempIn[2], tempIn[3] } }, { { tempIn[4], tempIn[5] }, { tempIn[6], tempIn[7] } } };
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  985 */       inputData = inputData1;
/*      */     }
/*      */     else {
/*  988 */       int[] tempIn = new int[4];
/*  989 */       inputData = new int[2][2][1];
/*  990 */       for (int i = 0; i < intV.size(); i++) {
/*  991 */         Integer temp_Input = (Integer)intV.elementAt(i);
/*  992 */         tempIn[i] = temp_Input.intValue();
/*      */       }
/*      */       
/*      */ 
/*  996 */       int[][][] inputData1 = { { { tempIn[0], tempIn[1] }, { tempIn[2], tempIn[3] } } };
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1004 */       inputData = inputData1;
/* 1005 */       this.currentTabuListInt.addElement(tempIn);
/*      */     }
/*      */     
/*      */ 
/* 1009 */     return inputData;
/*      */   }
/*      */   
/*      */   private int[] getInitSolution() {
/* 1013 */     if (this.firstStep) {
/* 1014 */       if (this.this$0.controller.data.currentSolution != null)
/* 1015 */         this.this$0.controller.data.currentSolution = null;
/* 1016 */       this.this$0.controller.data.currentSolution = new int[this.this$0.controller.data.initSolution.length];
/*      */       
/* 1018 */       for (int i = 0; i < this.this$0.controller.data.currentSolution.length; i++) {
/* 1019 */         this.this$0.controller.data.currentSolution[i] = (this.this$0.controller.data.initSolution[i] + 1);
/*      */       }
/*      */       
/* 1022 */       this.firstStep = false;
/*      */     }
/* 1024 */     int[] solution = new int[this.this$0.controller.data.currentSolution.length];
/* 1025 */     for (int i = 0; i < this.this$0.controller.data.currentSolution.length; i++) {
/* 1026 */       solution[i] = this.this$0.controller.data.currentSolution[i];
/*      */     }
/* 1028 */     return solution;
/*      */   }
/*      */   
/*      */   private Vector initVector() {
/* 1032 */     Vector p = new Vector();
/* 1033 */     p.addElement("isInteractive");
/* 1034 */     p.addElement(getInitSolution());
/* 1035 */     return p;
/*      */   }
/*      */   
/*      */   private IOROperation getOperation(Vector vec) {
/* 1039 */     IOROperation operation = new IOROperation(60009, vec);
/*      */     
/* 1041 */     return operation;
/*      */   }
/*      */   
/*      */   private void addItemWithCombox()
/*      */   {
/* 1046 */     this.solutionBox.removeAllItems();
/* 1047 */     this.solutionBox.addItem("Trial Solution           ");
/* 1048 */     Vector comBoxV = this.this$0.controller.data.TB_comBoxMessageV;
/* 1049 */     for (int i = 0; i < comBoxV.size(); i++) {
/* 1050 */       this.solutionBox.addItem((String)comBoxV.elementAt(i));
/*      */     }
/* 1052 */     this.solutionBox.setSelectedIndex(0);
/*      */   }
/*      */   
/*      */ 
/*      */   private void initInputState()
/*      */   {
/* 1058 */     if (this.autoRadio.isSelected()) {
/* 1059 */       for (int i = 0; i < this.tabuListT.length; i++) {
/* 1060 */         this.tabuListT[i].setEnabled(false);
/* 1061 */         this.tabuListT[i].setText("");
/* 1062 */         this.solutionBox.setEnabled(false);
/* 1063 */         this.solutionBox.setSelectedIndex(0);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1068 */     for (int i = 0; i < this.tabuListT.length; i++) {
/* 1069 */       this.tabuListT[i].setEnabled(true);
/* 1070 */       this.solutionBox.setEnabled(true); } }
/*      */   
/*      */   private class TSPTableModel extends AbstractTableModel { private int i;
/*      */     private int j;
/*      */     
/* 1075 */     TSPTableModel(IORTSPTabuSearchPanel..6 x$1) { this(); }
/*      */     
/*      */ 
/*      */ 
/*      */     public int getColumnCount()
/*      */     {
/* 1081 */       return IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.tableCol;
/*      */     }
/*      */     
/*      */     public int getRowCount()
/*      */     {
/* 1086 */       return IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.tableRow;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col)
/*      */     {
/* 1091 */       String str = "";
/* 1092 */       if (col == 0) {
/* 1093 */         str = String.valueOf(String.valueOf(str)).concat("Iteration");
/* 1094 */       } else if (col == 1) {
/* 1095 */         str = String.valueOf(String.valueOf(str)).concat("Trial Solution");
/* 1096 */       } else if (col == 2) {
/* 1097 */         str = String.valueOf(String.valueOf(str)).concat("Distance");
/*      */       } else
/* 1099 */         str = String.valueOf(String.valueOf(str)).concat("Tabu List");
/* 1100 */       return str;
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col)
/*      */     {
/* 1105 */       String str = "";
/* 1106 */       int r = -1;int c = -1;
/* 1107 */       if ((col == 0) && (row == 0)) {
/* 1108 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(0)));
/*      */       }
/* 1110 */       else if (col == 0) {
/* 1111 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(row)));
/*      */       }
/* 1113 */       else if (col == 1) {
/* 1114 */         if (IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.autoRadio.isSelected()) {
/* 1115 */           if (IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_Auto_totalTimes != 0) {
/* 1116 */             Vector tempv = (Vector)IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_Auto_Total_SolutionV.elementAt(IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_Auto_totalTimes - 1);
/*      */             
/*      */ 
/* 1119 */             int[] solu = (int[])tempv.elementAt(row);
/* 1120 */             String s = "";
/* 1121 */             for (int i = 0; i < solu.length; i++) {
/* 1122 */               if (i != solu.length - 1) {
/* 1123 */                 s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solu[i] + 1).append("-"))))));
/*      */               }
/*      */               else {
/* 1126 */                 s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solu[i] + 1))))));
/*      */               }
/*      */             }
/* 1129 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(s)));
/* 1130 */             str = String.valueOf(String.valueOf(str)).concat("-1");
/*      */           }
/*      */           
/*      */         }
/* 1134 */         else if (IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_currentTime != 0) {
/* 1135 */           int[] solu = (int[])IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_InterSolution_Vec.elementAt(row);
/*      */           
/* 1137 */           String s = "";
/* 1138 */           for (int i = 0; i < solu.length; i++) {
/* 1139 */             if (i != solu.length - 1) {
/* 1140 */               s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solu[i] + 1).append("-"))))));
/*      */             }
/*      */             else {
/* 1143 */               s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solu[i] + 1))))));
/*      */             }
/*      */           }
/* 1146 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(s)));
/* 1147 */           str = String.valueOf(String.valueOf(str)).concat("-1");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 1152 */       else if (col == 2) {
/* 1153 */         if (!IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.autoRadio.isSelected()) {
/* 1154 */           if (IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_currentTime != 0) {
/* 1155 */             Double dis = (Double)IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_InterDistance_Vec.elementAt(row);
/*      */             
/* 1157 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(dis))))));
/*      */           }
/*      */           
/*      */         }
/* 1161 */         else if (IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_Auto_totalTimes != 0) {
/* 1162 */           Vector tempv = (Vector)IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_Auto_Total_DistanceV.elementAt(IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_Auto_totalTimes - 1);
/*      */           
/*      */ 
/* 1165 */           double[] dis = (double[])tempv.elementAt(row);
/* 1166 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(dis[0]))))));
/*      */         }
/*      */         
/*      */       }
/* 1170 */       else if (col == 3) {
/* 1171 */         if (!IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.autoRadio.isSelected()) {
/* 1172 */           if ((IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_currentTime > 1) && (row != 0)) {
/* 1173 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_InterTabuList_Vec.elementAt(row))));
/*      */           }
/*      */           else
/*      */           {
/* 1177 */             str = "";
/*      */           }
/*      */           
/*      */         }
/* 1181 */         else if (IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_Auto_totalTimes != 0) {
/* 1182 */           Vector tempv = (Vector)IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_Auto_Total_TabuV.elementAt(IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.this$0.controller.data.TB_Auto_totalTimes - 1);
/*      */           
/*      */ 
/* 1185 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((String)tempv.elementAt(row))));
/*      */         }
/*      */       }
/*      */       
/* 1189 */       return str;
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c) {
/* 1193 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */     public boolean isCellEditable(int row, int col) {
/* 1197 */       if (!IORTSPTabuSearchPanel.IORTSPTabuCanvasPanel.this.is_panel_enabled)
/* 1198 */         return false;
/* 1199 */       if (col == 0) {
/* 1200 */         return false;
/*      */       }
/* 1202 */       return true;
/*      */     }
/*      */     
/*      */     public void setValueAt(Object value, int row, int col)
/*      */     {
/* 1207 */       String input = (String)value;
/* 1208 */       fireTableCellUpdated(row, col - 1);
/*      */     }
/*      */     
/*      */     private TSPTableModel() {}
/*      */   }
/*      */   
/*      */   public void initPrintData() {
/* 1215 */     String[][] TB_Print_Table = new String[this.tableRow][this.tableCol];
/* 1216 */     for (int i = 0; i < this.tableRow; i++) {
/* 1217 */       for (int j = 0; j < this.tableCol; j++) {
/* 1218 */         TB_Print_Table[i][j] = this.table.getValueAt(i, j).toString();
/*      */       }
/*      */     }
/* 1221 */     if (this.autoRadio.isSelected()) {
/* 1222 */       this.this$0.controller.data.isAuto = "true";
/* 1223 */       this.this$0.controller.data.TB_Auto_Print_Table = TB_Print_Table;
/*      */     }
/*      */     else {
/* 1226 */       this.this$0.controller.data.isAuto = "false";
/* 1227 */       if (this.this$0.controller.data.TB_Print_TableV == null)
/* 1228 */         this.this$0.controller.data.TB_Print_TableV = new Vector();
/* 1229 */       this.this$0.controller.data.TB_Print_TableV.addElement(TB_Print_Table);
/*      */     }
/* 1231 */     this.this$0.controller.solver.initPrintData(getOperation(new Vector()), this.this$0.controller.data);
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTSPTabuSearchPanel$IORTSPTabuCanvasPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */