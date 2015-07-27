/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Vector;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextArea;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.ListSelectionModel;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.table.AbstractTableModel;
/*      */ import javax.swing.table.DefaultTableCellRenderer;
/*      */ import javax.swing.table.TableColumn;
/*      */ import javax.swing.table.TableModel;
/*      */ 
/*      */ public class IORLPInteractPanel extends IORLPActionPanel implements java.io.Serializable
/*      */ {
/*   30 */   private final int ALGEBRA_FORM = 1;
/*   31 */   private final int TABULAR_FORM = 2;
/*   32 */   private String GREATTHAN = ">=";
/*      */   
/*      */   protected IORLPProcessController controller;
/*      */   
/*   36 */   private JPanel mainPanel = new JPanel();
/*   37 */   private IORLPInteractPanel.IORTableModel interModel = null;
/*   38 */   private IORLPInteractPanel.IORAlgebraModel algbModel = null;
/*   39 */   private JTable table = null;
/*      */   private DefaultTableCellRenderer selectedRenderer;
/*   41 */   private JScrollPane upscrollPane = null;
/*   42 */   private JPanel tablePanel = new JPanel();
/*   43 */   private JLabel zerolabel = new JLabel();
/*   44 */   private JLabel oklabel = new JLabel("Is the current feasible solution optimal? ");
/*   45 */   private JTextArea seltext = new JTextArea("You have completed phase 1. \nWhich statement is true?");
/*      */   
/*      */ 
/*   48 */   private JTextArea statetext = new JTextArea();
/*   49 */   private JPanel statePanel = new JPanel();
/*   50 */   private JPanel multiPanel = new JPanel();
/*   51 */   private JPanel dividPanel = new JPanel();
/*   52 */   private JPanel buttPanel = new JPanel();
/*   53 */   private JButton backbutt = new JButton("Back");
/*   54 */   private JButton nextbutt = new JButton("Next");
/*      */   
/*      */ 
/*   57 */   private int step = 1;
/*   58 */   private Vector opseq = new Vector();
/*   59 */   private Vector selcell = new Vector();
/*      */   
/*      */ 
/*   62 */   private JTextField yorn = new JTextField("N", 2);
/*   63 */   private JTextField factor = new JTextField(6);
/*   64 */   private JLabel[] inputlabel = null;
/*   65 */   private JTextField[] multiple = null;
/*   66 */   private JPanel[] inputPanel = null;
/*   67 */   private JComboBox resultcomb = new JComboBox();
/*      */   public boolean isTwoPhase;
/*      */   private int nBasic;
/*      */   private int nAllvar;
/*      */   private int row;
/*   72 */   private int col; private int dispForm; private int equation = 0;
/*   73 */   private int result = 0;
/*   74 */   private boolean hasFinishPhase1 = false;
/*      */   
/*   76 */   private String str_selbas = "Select the entering and the leaving basic variable by clicking on the corresponding column \nand row (pivot equation) above. Then enter the factor by which to divide the pivot equation.\nThen press the NEXT button.";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void backStep() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updatePanel() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public IORLPInteractPanel(IORLPProcessController c)
/*      */   {
/*   95 */     super(c);
/*   96 */     this.controller = c;
/*      */     
/*   98 */     this.state = new IORState(c.solver);
/*   99 */     add(this.mainPanel, "Center");
/*  100 */     this.bn_back.setVisible(false);
/*  101 */     this.bn_finish.setVisible(true);
/*  102 */     this.isTwoPhase = (!this.controller.data.is_big_M);
/*  103 */     this.nBasic = this.controller.data.num_constrain;
/*  104 */     this.nAllvar = this.controller.data.num_variable;
/*  105 */     this.dispForm = this.controller.data.DisplayForm;
/*      */     
/*      */ 
/*  108 */     String xstr = " ";
/*  109 */     this.tablePanel.setLayout(new BoxLayout(this.tablePanel, 1));
/*  110 */     for (int i = 1; i < this.controller.data.num_variable + 1; i++) {
/*  111 */       if (this.controller.data.is_artificial[i] != 0) {
/*  112 */         xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("X").append(i).append(this.GREATTHAN).append("0"))))));
/*      */       } else
/*  114 */         xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("x").append(i).append(this.GREATTHAN).append("0"))))));
/*  115 */       if (i < this.controller.data.num_variable)
/*  116 */         xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*      */     }
/*  118 */     this.zerolabel.setText(xstr);
/*      */     
/*      */ 
/*  121 */     this.selectedRenderer = new DefaultTableCellRenderer()
/*      */     {
/*      */       public void setValue(Object value) {
/*  124 */         if ((value instanceof IORTableCell))
/*      */         {
/*  126 */           if (((IORTableCell)value).isBlock == true) {
/*  127 */             setBackground(Color.cyan);
/*      */           } else {
/*  129 */             setBackground(Color.white);
/*      */           }
/*      */         }
/*  132 */         super.setValue(value);
/*      */       }
/*      */       
/*  135 */     };
/*  136 */     this.interModel = new IORLPInteractPanel.IORTableModel();
/*  137 */     this.algbModel = new IORLPInteractPanel.IORAlgebraModel();
/*  138 */     if (this.dispForm == 2) {
/*  139 */       this.table = new JTable(this.interModel);
/*  140 */       this.upscrollPane = new JScrollPane(this.table);
/*  141 */       this.tablePanel.add(this.upscrollPane);
/*  142 */       for (i = 3; i < this.table.getColumnCount(); i++) {
/*  143 */         this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*  144 */         this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*      */       }
/*  146 */       for (i = 0; i < 3; i++) {
/*  147 */         this.table.getColumn(this.table.getColumnName(i)).setPreferredWidth(20);
/*      */       }
/*      */     }
/*      */     
/*  151 */     this.table = new JTable(this.algbModel);
/*  152 */     this.table.setShowGrid(false);
/*  153 */     this.tablePanel.setBorder(new javax.swing.border.EtchedBorder());
/*  154 */     this.tablePanel.add(this.table);
/*  155 */     this.tablePanel.add(Box.createVerticalStrut(10));
/*  156 */     this.tablePanel.add(this.zerolabel);
/*  157 */     this.tablePanel.add(Box.createVerticalStrut(30));
/*  158 */     for (i = 3; i < this.table.getColumnCount(); i++) {
/*  159 */       this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*  160 */       this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*      */     }
/*  162 */     this.table.getColumn(this.table.getColumnName(0)).setPreferredWidth(1);
/*  163 */     this.table.getColumn(this.table.getColumnName(1)).setPreferredWidth(20);
/*  164 */     this.table.getColumn(this.table.getColumnName(2)).setPreferredWidth(20);
/*      */     
/*      */ 
/*  167 */     this.table.setPreferredScrollableViewportSize(new Dimension(500, 100));
/*  168 */     this.table.setCellSelectionEnabled(true);
/*  169 */     this.table.setSelectionMode(0);
/*      */     
/*  171 */     this.tablePanel.setAlignmentX(0.5F);
/*  172 */     this.tablePanel.setPreferredSize(new Dimension(700, 20 * (this.nBasic + 1) + 40));
/*  173 */     this.tablePanel.setMaximumSize(new Dimension(700, 20 * (this.nBasic + 1) + 60));
/*      */     
/*  175 */     addSelectionListener();
/*      */     
/*      */ 
/*  178 */     this.seltext.setBackground(getBackground());
/*  179 */     this.seltext.setForeground(Color.blue);
/*  180 */     this.seltext.setEditable(false);
/*  181 */     this.statetext.setBackground(getBackground());
/*  182 */     this.statetext.setForeground(Color.blue);
/*  183 */     this.statetext.setEditable(false);
/*  184 */     this.statetext.setText(this.str_selbas);
/*  185 */     this.statetext.setPreferredSize(new Dimension(500, 60));
/*      */     
/*      */ 
/*  188 */     this.inputlabel = new JLabel[this.nBasic + 1];
/*  189 */     this.multiple = new JTextField[this.nBasic + 1];
/*  190 */     this.inputPanel = new JPanel[this.nBasic + 1];
/*  191 */     for (i = 0; i < this.nBasic + 1; i++) {
/*  192 */       this.inputlabel[i] = new JLabel();
/*  193 */       this.multiple[i] = new JTextField(6);
/*  194 */       this.inputPanel[i] = new JPanel();
/*      */     }
/*      */     
/*      */ 
/*  198 */     this.dividPanel.setLayout(new BoxLayout(this.dividPanel, 1));
/*  199 */     this.multiPanel.setLayout(new java.awt.BorderLayout());
/*  200 */     this.multiPanel.setPreferredSize(new Dimension(600, this.nBasic * 50));
/*  201 */     this.multiPanel.setMaximumSize(new Dimension(600, this.nBasic * 50));
/*  202 */     this.multiPanel.add(this.dividPanel, "North");
/*      */     
/*      */ 
/*  205 */     this.resultcomb.addItem("All artificial variables are nonbasic.");
/*  206 */     this.resultcomb.addItem("Some artificial variables are basic, but all of them have value zero.");
/*  207 */     this.resultcomb.addItem("Some artificial variables are basic and have positive value.");
/*  208 */     this.resultcomb.setSelectedIndex(0);
/*      */     
/*      */ 
/*  211 */     this.statePanel.setPreferredSize(new Dimension(660, 200));
/*  212 */     this.statePanel.setMaximumSize(new Dimension(660, 200));
/*  213 */     this.statePanel.add(this.oklabel);
/*  214 */     this.statePanel.add(this.yorn);
/*      */     
/*      */ 
/*  217 */     this.buttPanel.add(this.backbutt);
/*      */     
/*  219 */     this.buttPanel.add(Box.createRigidArea(new Dimension(10, 30)));
/*      */     
/*  221 */     this.buttPanel.add(this.nextbutt);
/*      */     
/*  223 */     this.buttPanel.setPreferredSize(new Dimension(300, 60));
/*  224 */     this.buttPanel.setMaximumSize(new Dimension(300, 60));
/*      */     
/*  226 */     this.nextbutt.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/*  229 */         IORLPInteractPanel.this.doNext();
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  235 */     });
/*  236 */     this.backbutt.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/*  239 */         IORLPInteractPanel.this.doBack();
/*      */       }
/*  241 */     });
/*  242 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*  243 */     this.mainPanel.setBorder(new javax.swing.border.EmptyBorder(0, 20, 0, 20));
/*  244 */     this.mainPanel.add(this.tablePanel);
/*  245 */     this.mainPanel.add(this.statePanel);
/*  246 */     this.mainPanel.add(this.buttPanel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void switchForm(int newform)
/*      */   {
/*  256 */     if ((newform < 1) || (newform > 2)) {
/*  257 */       return;
/*      */     }
/*  259 */     if (newform != this.dispForm) {
/*  260 */       this.dispForm = newform;
/*  261 */       if (newform == 2) {
/*  262 */         clrTableColor(this.table);
/*  263 */         this.table.setModel(this.interModel);
/*  264 */         this.table.setShowGrid(true);
/*  265 */         for (int i = 3; i < this.table.getColumnCount(); i++) {
/*  266 */           this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*  267 */           this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*      */         }
/*  269 */         for (i = 0; i < 3; i++) {
/*  270 */           this.table.getColumn(this.table.getColumnName(i)).setPreferredWidth(20);
/*      */         }
/*  272 */         this.table.revalidate();
/*  273 */         this.table.repaint();
/*  274 */         if ((this.step == 2) || (this.step == 3)) {
/*  275 */           SelBlock(this.table, this.row, this.col);
/*      */         }
/*  277 */         this.tablePanel.setBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0));
/*  278 */         this.tablePanel.removeAll();
/*  279 */         this.upscrollPane = new JScrollPane(this.table);
/*  280 */         this.tablePanel.add(this.upscrollPane);
/*      */       }
/*  282 */       if (newform == 1) {
/*  283 */         clrTableColor(this.table);
/*  284 */         this.table.setModel(this.algbModel);
/*  285 */         this.table.setShowGrid(false);
/*  286 */         for (int i = 3; i < this.table.getColumnCount(); i++) {
/*  287 */           this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*  288 */           this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*      */         }
/*  290 */         this.table.getColumn(this.table.getColumnName(0)).setPreferredWidth(1);
/*  291 */         this.table.getColumn(this.table.getColumnName(1)).setPreferredWidth(20);
/*  292 */         this.table.getColumn(this.table.getColumnName(2)).setPreferredWidth(20);
/*  293 */         this.table.revalidate();
/*  294 */         this.table.repaint();
/*  295 */         if ((this.step == 2) || (this.step == 3)) {
/*  296 */           SelBlock(this.table, this.row, this.col);
/*      */         }
/*  298 */         this.tablePanel.setBorder(new javax.swing.border.EtchedBorder());
/*  299 */         this.tablePanel.removeAll();
/*  300 */         this.tablePanel.add(this.table);
/*  301 */         this.tablePanel.add(Box.createVerticalStrut(10));
/*  302 */         this.tablePanel.add(this.zerolabel);
/*  303 */         this.tablePanel.add(Box.createVerticalStrut(30));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void finishProcedure()
/*      */   {
/*  312 */     this.controller.setMenuState(12);
/*  313 */     this.actionStatus.setText("The current procedure is finished. Please go to the Procedure menu to continue!");
/*  314 */     setFinishEnabled(false);
/*      */   }
/*      */   
/*      */ 
/*      */   private void addInputPanel(int eq)
/*      */   {
/*  320 */     if (eq == 0) {
/*  321 */       return;
/*      */     }
/*  323 */     this.dividPanel.removeAll();
/*  324 */     for (int i = 0; i < this.nBasic + 1; i++) {
/*  325 */       if (i != eq) {
/*  326 */         this.inputPanel[i].removeAll();
/*  327 */         this.inputlabel[i].setText(String.valueOf(String.valueOf(new StringBuffer("Enter the multiple of Equation ").append(eq).append(" to subtract from Equation ").append(i).append(":"))));
/*  328 */         this.inputlabel[i].setLabelFor(this.multiple[i]);
/*  329 */         this.inputPanel[i].add(this.inputlabel[i]);
/*  330 */         this.inputPanel[i].add(this.multiple[i]);
/*  331 */         this.dividPanel.add(this.inputPanel[i]);
/*  332 */         this.dividPanel.add(Box.createRigidArea(new Dimension(10, 5)));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doBack()
/*      */   {
/*  343 */     this.actionStatus.setText(" ");
/*  344 */     this.nextbutt.setEnabled(true);
/*  345 */     if (this.opseq.isEmpty() == false) {
/*  346 */       int last = ((Integer)this.opseq.lastElement()).intValue();
/*  347 */       this.opseq.removeElementAt(this.opseq.size() - 1);
/*  348 */       setFinishEnabled(true);
/*      */       
/*  350 */       switch (last) {
/*      */       case 1: 
/*  352 */         if (this.step == 4) {
/*  353 */           this.hasFinishPhase1 = false;
/*      */         }
/*  355 */         clrTableColor(this.table);
/*      */         
/*  357 */         this.statePanel.removeAll();
/*  358 */         this.statePanel.add(this.oklabel);
/*  359 */         this.statePanel.add(this.yorn);
/*  360 */         this.step = 1;
/*      */         
/*  362 */         revalidate();
/*  363 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  521 */         break;
/*      */       case 2: 
/*  367 */         this.controller.solver.reset();
/*  368 */         this.state.back();
/*      */         
/*  370 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  373 */         this.mainPanel.remove(this.multiPanel);
/*  374 */         this.mainPanel.remove(this.buttPanel);
/*      */         
/*      */ 
/*  377 */         this.statetext.setText(this.str_selbas);
/*  378 */         this.statePanel.removeAll();
/*  379 */         this.statePanel.add(this.statetext);
/*  380 */         this.statePanel.add(this.factor);
/*  381 */         this.mainPanel.add(this.statePanel);
/*  382 */         this.mainPanel.add(this.buttPanel);
/*      */         
/*  384 */         this.step = 2;
/*      */         
/*  386 */         this.interModel.fireTableDataChanged();
/*  387 */         this.algbModel.fireTableDataChanged();
/*  388 */         revalidate();
/*  389 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  521 */         break;
/*      */       case 3: 
/*  393 */         this.controller.solver.reset();
/*  394 */         this.state.back();
/*      */         
/*  396 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  399 */         this.step = 3;
/*  400 */         this.interModel.fireTableDataChanged();
/*  401 */         this.algbModel.fireTableDataChanged();
/*  402 */         if (this.selcell.isEmpty() == false) {
/*  403 */           Object scel = this.selcell.elementAt(this.selcell.size() - 1);
/*  404 */           this.selcell.removeElementAt(this.selcell.size() - 1);
/*  405 */           this.row = ((int[])scel)[0];
/*  406 */           this.col = ((int[])scel)[1];
/*  407 */           SelBlock(this.table, this.row, this.col);
/*      */         }
/*      */         
/*  410 */         this.mainPanel.remove(this.statePanel);
/*  411 */         this.mainPanel.remove(this.buttPanel);
/*  412 */         addInputPanel(this.row);
/*  413 */         this.equation = this.row;
/*  414 */         this.mainPanel.add(this.multiPanel);
/*  415 */         this.mainPanel.add(this.buttPanel);
/*      */         
/*  417 */         revalidate();
/*  418 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  521 */         break;
/*      */       case 4: 
/*  422 */         this.statePanel.removeAll();
/*  423 */         this.statePanel.add(this.seltext);
/*  424 */         this.statePanel.add(this.resultcomb);
/*  425 */         if (this.step == 8)
/*  426 */           this.nextbutt.setEnabled(true);
/*  427 */         this.step = 4;
/*      */         
/*  429 */         revalidate();
/*  430 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  521 */         break;
/*      */       case 5: 
/*  434 */         this.controller.solver.reset();
/*  435 */         this.state.back();
/*      */         
/*  437 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  439 */         if (this.result == 0)
/*      */         {
/*  441 */           this.statePanel.removeAll();
/*  442 */           this.statetext.setText("Now the tableau needs to be prepared for Phase 2 of the Two-Phase method.\nPress the NEXT button to remove artificial variables from the tableau.");
/*      */           
/*  444 */           this.statePanel.add(this.statetext);
/*      */ 
/*      */         }
/*  447 */         else if (this.result == 1)
/*      */         {
/*  449 */           this.statePanel.removeAll();
/*  450 */           this.statetext.setText("In order to force the artificial variables to remain at zero, a new constraint \nwill be added, forcing the sum of the artificial variables to be less than or \nequal to zero. Press the NEXT button to have the computer do this.");
/*      */           
/*      */ 
/*  453 */           this.statePanel.add(this.statetext);
/*      */         }
/*      */         else {
/*  456 */           System.out.println("The program should not be backed here!");
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  521 */           break;
/*      */         }
/*  460 */         changeTableStrct();
/*      */         
/*  462 */         String xstr = " ";
/*  463 */         for (int i = 1; i < this.controller.data.num_variable + 1; i++) {
/*  464 */           if (this.controller.data.is_artificial[i] != 0) {
/*  465 */             xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("X").append(i).append(this.GREATTHAN).append("0"))))));
/*      */           } else
/*  467 */             xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("x").append(i).append(this.GREATTHAN).append("0"))))));
/*  468 */           if (i < this.controller.data.num_variable)
/*  469 */             xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*      */         }
/*  471 */         this.zerolabel.setText(xstr);
/*      */         
/*  473 */         this.step = 5;
/*      */         
/*  475 */         revalidate();
/*  476 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  521 */         break;
/*      */       case 6: 
/*  480 */         this.controller.solver.reset();
/*  481 */         this.state.back();
/*      */         
/*  483 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  485 */         this.statetext.setText("Press the NEXT button to replace the current objective function with the objective \nfunction of the original model.");
/*      */         
/*      */ 
/*  488 */         this.step = 6;
/*      */         
/*      */ 
/*  491 */         this.interModel.fireTableDataChanged();
/*  492 */         this.algbModel.fireTableDataChanged();
/*  493 */         revalidate();
/*  494 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  521 */         break;
/*      */       case 7: 
/*  498 */         this.controller.solver.reset();
/*  499 */         this.state.back();
/*      */         
/*  501 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  504 */         this.statePanel.removeAll();
/*  505 */         this.statetext.setText("Press the NEXT button to convert the tableau to proper form from Gaussian elimination \nand to begin Phase 2 of the Two-Phase method.");
/*      */         
/*  507 */         this.statePanel.add(this.statetext);
/*  508 */         this.step = 7;
/*      */         
/*      */ 
/*  511 */         this.interModel.fireTableDataChanged();
/*  512 */         this.algbModel.fireTableDataChanged();
/*  513 */         revalidate();
/*  514 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  521 */         break;
/*      */       default: 
/*  517 */         System.out.println("The program should not be backed here");
/*      */         
/*      */ 
/*      */ 
/*  521 */         break; }
/*      */     }
/*      */   }
/*      */   
/*      */   private void doNext() {
/*  526 */     int[] selPos = new int[2];
/*  527 */     int[] remPos = new int[2];
/*      */     
/*      */ 
/*      */ 
/*  531 */     double[] eqc = new double[this.nBasic + 1];
/*  532 */     double[] eqm = new double[this.nBasic + 1];
/*      */     
/*  534 */     Vector par = new Vector();
/*  535 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*  537 */     switch (this.step) {
/*      */     case 1: 
/*  539 */       char sel = this.yorn.getText().charAt(0);
/*      */       
/*  541 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*  543 */       if ((sel != 'y') && (sel != 'Y'))
/*      */       {
/*      */ 
/*      */ 
/*  547 */         this.statetext.setText(this.str_selbas);
/*  548 */         this.statePanel.removeAll();
/*  549 */         this.statePanel.add(this.statetext);
/*  550 */         this.statePanel.add(this.factor);
/*  551 */         this.table.clearSelection();
/*      */         
/*  553 */         this.step = 2;
/*      */       }
/*  555 */       else if (this.isTwoPhase) {
/*  556 */         if (this.hasFinishPhase1)
/*      */         {
/*  558 */           this.statetext.setText("You have now completed the Simplex Method for this problem.\nYou may print your work by choosing Print to File under the File menu.");
/*      */           
/*  560 */           this.statePanel.removeAll();
/*  561 */           this.statePanel.add(this.statetext);
/*  562 */           this.actionStatus.setText("After you are done, press the FINISH button, and then choose another procedure from the Procedure menu to continue!");
/*  563 */           this.nextbutt.setEnabled(false);
/*      */           
/*  565 */           this.step = 8;
/*      */         }
/*      */         else
/*      */         {
/*  569 */           this.statePanel.removeAll();
/*  570 */           this.statePanel.add(this.seltext);
/*  571 */           this.statePanel.add(this.resultcomb);
/*      */           
/*  573 */           this.hasFinishPhase1 = true;
/*  574 */           this.step = 4;
/*      */         }
/*      */       }
/*      */       else {
/*  578 */         this.statePanel.removeAll();
/*  579 */         this.statetext.setText("You have now completed the Simplex Method for this problem.\nYou may print your work by choosing Print to File under the File menu.");
/*      */         
/*  581 */         this.statePanel.add(this.statetext);
/*  582 */         this.actionStatus.setText("After you are done, press the FINISH button, and then go to the Procedure menu to continue.");
/*  583 */         this.nextbutt.setEnabled(false);
/*  584 */         this.step = 8;
/*      */       }
/*  586 */       revalidate();
/*  587 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  802 */       break;
/*      */     case 2: 
/*  590 */       if ((this.row < 1) || (this.col < 3) || (this.col >= this.nAllvar + 3)) {
/*  591 */         JOptionPane.showMessageDialog(this, "This row or column cannot be selected!");
/*      */       }
/*      */       else {
/*  594 */         selPos[0] = this.row;
/*  595 */         selPos[1] = (this.col - 2);
/*  596 */         String input = this.factor.getText();
/*      */         try {
/*  598 */           dblPar = Double.parseDouble(input);
/*      */         } catch (NumberFormatException e) { double dblPar;
/*  600 */           JOptionPane.showMessageDialog(this, "Invalid Input. Please try again!");
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  802 */           break;
/*      */         }
/*      */         double dblPar;
/*  603 */         par.addElement(selPos);
/*  604 */         par.addElement(new Double(dblPar));
/*      */         
/*  606 */         opr.operation_code = 10401;
/*  607 */         opr.parameters = par;
/*  608 */         if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  609 */           String err = this.controller.solver.getErrInfo();
/*  610 */           JOptionPane.showMessageDialog(this, err);
/*      */         }
/*      */         else
/*      */         {
/*  614 */           this.state.addStep(opr);
/*      */           
/*  616 */           this.opseq.addElement(new Integer(this.step));
/*      */           
/*      */ 
/*  619 */           this.mainPanel.remove(this.statePanel);
/*  620 */           this.mainPanel.remove(this.buttPanel);
/*  621 */           addInputPanel(this.row);
/*  622 */           this.mainPanel.add(this.multiPanel);
/*  623 */           this.mainPanel.add(this.buttPanel);
/*  624 */           this.equation = this.row;
/*      */           
/*  626 */           this.step = 3;
/*  627 */           this.interModel.fireTableDataChanged();
/*  628 */           this.algbModel.fireTableDataChanged();
/*  629 */           revalidate();
/*  630 */           repaint();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  802 */       break;
/*      */     case 3: 
/*  636 */       if (prcMultiple(eqc, eqm) != false)
/*      */       {
/*      */ 
/*  639 */         par.addElement(eqm);
/*  640 */         par.addElement(eqc);
/*  641 */         opr.operation_code = 10402;
/*  642 */         opr.parameters = par;
/*      */         
/*  644 */         if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  645 */           String err = this.controller.solver.getErrInfo();
/*  646 */           JOptionPane.showMessageDialog(this, err);
/*      */         }
/*      */         else
/*      */         {
/*  650 */           this.state.addStep(opr);
/*      */           
/*  652 */           this.opseq.addElement(new Integer(this.step));
/*      */           
/*      */ 
/*  655 */           remPos[0] = this.row;
/*  656 */           remPos[1] = this.col;
/*  657 */           this.selcell.addElement(remPos);
/*      */           
/*      */ 
/*  660 */           this.statePanel.removeAll();
/*  661 */           this.statePanel.add(this.oklabel);
/*  662 */           this.statePanel.add(this.yorn);
/*  663 */           this.mainPanel.remove(this.multiPanel);
/*  664 */           this.mainPanel.remove(this.buttPanel);
/*  665 */           this.mainPanel.add(this.statePanel);
/*  666 */           this.mainPanel.add(this.buttPanel);
/*      */           
/*  668 */           this.step = 1;
/*      */           
/*      */ 
/*  671 */           clrTableColor(this.table);
/*  672 */           this.interModel.fireTableDataChanged();
/*  673 */           this.algbModel.fireTableDataChanged();
/*  674 */           revalidate();
/*  675 */           repaint();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  802 */       break;
/*      */     case 4: 
/*  680 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*  682 */       this.result = this.resultcomb.getSelectedIndex();
/*  683 */       if (this.result == 0) {
/*  684 */         this.statePanel.removeAll();
/*  685 */         this.statetext.setText("Now the tableau needs to be prepared for Phase 2 of the Two-Phase method.\nPress the NEXT button to remove artificial variables from the tableau.");
/*      */         
/*  687 */         this.statePanel.add(this.statetext);
/*      */         
/*  689 */         this.step = 5;
/*      */       }
/*  691 */       else if (this.result == 1) {
/*  692 */         this.statePanel.removeAll();
/*  693 */         this.statetext.setText("In order to force the artificial variables to remain at zero, a new constraint \nwill be added, forcing the sum of the artificial variables to be less than or \nequal to zero. Press the NEXT button to have the computer do this.");
/*      */         
/*      */ 
/*  696 */         this.statePanel.add(this.statetext);
/*      */         
/*  698 */         this.step = 5;
/*      */       }
/*      */       else {
/*  701 */         this.statePanel.removeAll();
/*  702 */         this.statetext.setText("Since the artificial variables could not all be forced to zero, \nthe original model must have no feasible solutions.");
/*      */         
/*  704 */         this.statePanel.add(this.statetext);
/*      */         
/*      */ 
/*  707 */         this.step = 8;
/*      */       }
/*  709 */       revalidate();
/*  710 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  802 */       break;
/*      */     case 5: 
/*  713 */       if (this.result == 0) {
/*  714 */         opr.operation_code = 10406;
/*      */       }
/*  716 */       else if (this.result == 1) {
/*  717 */         opr.operation_code = 10403;
/*      */       }
/*      */       else {
/*  720 */         System.out.println("The program should not get here");
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  802 */         break;
/*      */       }
/*  723 */       opr.parameters = par;
/*  724 */       this.controller.solver.doWork(opr, this.controller.data);
/*      */       
/*      */ 
/*  727 */       this.state.addStep(opr);
/*      */       
/*  729 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*      */ 
/*  732 */       changeTableStrct();
/*      */       
/*      */ 
/*  735 */       String xstr = " ";
/*  736 */       for (int i = 1; i < this.controller.data.num_variable + 1; i++) {
/*  737 */         xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("x").append(i).append(this.GREATTHAN).append("0"))))));
/*  738 */         if (i < this.controller.data.num_variable)
/*  739 */           xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*      */       }
/*  741 */       this.zerolabel.setText(xstr);
/*      */       
/*  743 */       this.statetext.setText("Press the NEXT button to replace the current objective function with the objective \nfunction of the original model.");
/*      */       
/*      */ 
/*      */ 
/*  747 */       this.step = 6;
/*  748 */       revalidate();
/*  749 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  802 */       break;
/*      */     case 6: 
/*  752 */       opr.operation_code = 10404;
/*  753 */       opr.parameters = par;
/*  754 */       this.controller.solver.doWork(opr, this.controller.data);
/*      */       
/*      */ 
/*  757 */       this.state.addStep(opr);
/*      */       
/*      */ 
/*  760 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*  762 */       this.statetext.setText("Press the NEXT button to convert the tableau to proper form from Gaussian elimination \nand to begin Phase 2 of the Two-Phase method.");
/*      */       
/*      */ 
/*  765 */       this.interModel.fireTableDataChanged();
/*  766 */       this.algbModel.fireTableDataChanged();
/*      */       
/*  768 */       this.step = 7;
/*  769 */       revalidate();
/*  770 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  802 */       break;
/*      */     case 7: 
/*  774 */       this.opseq.addElement(new Integer(this.step));
/*  775 */       opr.operation_code = 10405;
/*  776 */       opr.parameters = par;
/*  777 */       this.controller.solver.doWork(opr, this.controller.data);
/*      */       
/*      */ 
/*  780 */       this.state.addStep(opr);
/*      */       
/*      */ 
/*  783 */       this.statePanel.removeAll();
/*  784 */       this.statePanel.add(this.oklabel);
/*  785 */       this.statePanel.add(this.yorn);
/*      */       
/*  787 */       this.interModel.fireTableDataChanged();
/*  788 */       this.algbModel.fireTableDataChanged();
/*      */       
/*  790 */       this.step = 1;
/*  791 */       revalidate();
/*  792 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  802 */       break;
/*      */     case 8: 
/*      */       break;
/*      */     default: 
/*  798 */       System.out.println("should not arrive this");
/*      */       
/*      */ 
/*      */ 
/*  802 */       break;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void LoadFile(ObjectInputStream in)
/*      */   {
/*  812 */     int[] para = new int[5];
/*      */     
/*      */     try
/*      */     {
/*  816 */       this.state = ((IORState)in.readObject());
/*  817 */       para = (int[])in.readObject();
/*  818 */       this.opseq = ((Vector)in.readObject());
/*  819 */       this.selcell = ((Vector)in.readObject());
/*  820 */       this.state.setSolverStepVector();
/*      */     }
/*      */     catch (Exception e1) {
/*  823 */       e1.printStackTrace();
/*  824 */       System.out.println("Open fails");
/*      */     }
/*      */     
/*  827 */     this.step = para[0];
/*  828 */     this.row = para[1];
/*  829 */     this.col = para[2];
/*  830 */     this.equation = para[3];
/*  831 */     this.result = para[4];
/*      */     
/*  833 */     this.mainPanel.removeAll();
/*  834 */     switch (this.step) {
/*      */     case 1: 
/*  836 */       this.mainPanel.add(this.tablePanel);
/*  837 */       this.mainPanel.add(this.statePanel);
/*  838 */       this.mainPanel.add(this.buttPanel);
/*  839 */       break;
/*      */     
/*      */ 
/*      */     case 2: 
/*  843 */       this.statetext.setText(this.str_selbas);
/*  844 */       this.statePanel.removeAll();
/*  845 */       this.statePanel.add(this.statetext);
/*  846 */       this.statePanel.add(this.factor);
/*  847 */       this.mainPanel.add(this.tablePanel);
/*  848 */       this.mainPanel.add(this.statePanel);
/*  849 */       this.mainPanel.add(this.buttPanel);
/*      */       
/*  851 */       this.interModel.fireTableDataChanged();
/*  852 */       this.algbModel.fireTableDataChanged();
/*  853 */       SelBlock(this.table, this.row, this.col);
/*  854 */       break;
/*      */     case 3: 
/*  856 */       addInputPanel(this.row);
/*  857 */       this.mainPanel.add(this.tablePanel);
/*  858 */       this.mainPanel.add(this.multiPanel);
/*  859 */       this.mainPanel.add(this.buttPanel);
/*  860 */       this.equation = this.row;
/*  861 */       this.interModel.fireTableDataChanged();
/*  862 */       this.algbModel.fireTableDataChanged();
/*  863 */       SelBlock(this.table, this.row, this.col);
/*  864 */       break;
/*      */     case 4: 
/*  866 */       this.statePanel.removeAll();
/*  867 */       this.statePanel.add(this.seltext);
/*  868 */       this.statePanel.add(this.resultcomb);
/*  869 */       this.hasFinishPhase1 = true;
/*  870 */       this.mainPanel.add(this.tablePanel);
/*  871 */       this.mainPanel.add(this.statePanel);
/*  872 */       this.mainPanel.add(this.buttPanel);
/*  873 */       break;
/*      */     case 5: 
/*  875 */       if (this.result == 0) {
/*  876 */         this.statePanel.removeAll();
/*  877 */         this.statetext.setText("Now the tableau needs to be prepared for Phase 2 of the Two-Phase method.\nPress the NEXT button to remove artificial variables from the tableau.");
/*      */         
/*  879 */         this.statePanel.add(this.statetext);
/*      */       }
/*  881 */       else if (this.result == 1) {
/*  882 */         this.statePanel.removeAll();
/*  883 */         this.statetext.setText("In order to force the artificial variables to remain at zero, a new constraint \nwill be added, forcing the sum of the artificial variables to be less than or \nequal to zero. Press the NEXT button to have the computer do this.");
/*      */         
/*      */ 
/*  886 */         this.statePanel.add(this.statetext);
/*      */       }
/*  888 */       this.mainPanel.add(this.tablePanel);
/*  889 */       this.mainPanel.add(this.statePanel);
/*  890 */       this.mainPanel.add(this.buttPanel);
/*  891 */       break;
/*      */     case 6: 
/*  893 */       changeTableStrct();
/*      */       
/*  895 */       String xstr = " ";
/*  896 */       for (int i = 1; i < this.controller.data.num_variable + 1; i++) {
/*  897 */         xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("x").append(i).append(this.GREATTHAN).append("0"))))));
/*  898 */         if (i < this.controller.data.num_variable)
/*  899 */           xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*      */       }
/*  901 */       this.zerolabel.setText(xstr);
/*      */       
/*  903 */       this.statetext.setText("Press the NEXT button to replace the current objective function with the objective \nfunction of the original model.");
/*      */       
/*  905 */       this.statePanel.removeAll();
/*  906 */       this.statePanel.add(this.statetext);
/*  907 */       this.mainPanel.add(this.tablePanel);
/*  908 */       this.mainPanel.add(this.statePanel);
/*  909 */       this.mainPanel.add(this.buttPanel);
/*  910 */       break;
/*      */     case 7: 
/*  912 */       this.statetext.setText("Press the NEXT button to convert the tableau to proper form from Gaussian Elimination \nand to begin Phase 2 of the Two-Phase method.");
/*      */       
/*      */ 
/*  915 */       this.interModel.fireTableDataChanged();
/*  916 */       this.algbModel.fireTableDataChanged();
/*  917 */       this.statePanel.removeAll();
/*  918 */       this.statePanel.add(this.statetext);
/*  919 */       this.mainPanel.add(this.tablePanel);
/*  920 */       this.mainPanel.add(this.statePanel);
/*  921 */       this.mainPanel.add(this.buttPanel);
/*  922 */       break;
/*      */     case 8: 
/*  924 */       this.statetext.setText("You have now completed the Simplex Method for this problem.\nYou may print your work by choosing Print to File under the File menu.");
/*      */       
/*  926 */       this.statePanel.removeAll();
/*  927 */       this.statePanel.add(this.statetext);
/*  928 */       this.actionStatus.setText("After you are done, press the FINISH button, and then go to the Procedure menu to continue.");
/*  929 */       this.mainPanel.add(this.tablePanel);
/*  930 */       this.mainPanel.add(this.statePanel);
/*  931 */       this.mainPanel.add(this.buttPanel);
/*  932 */       break;
/*      */     default: 
/*  934 */       System.out.println("LP interactive panel has no this step.");
/*      */     }
/*  936 */     revalidate();
/*  937 */     repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFile(ObjectOutputStream out)
/*      */   {
/*  947 */     int[] interpara = new int[5];
/*      */     
/*  949 */     interpara[0] = this.step;
/*  950 */     interpara[1] = this.row;
/*  951 */     interpara[2] = this.col;
/*  952 */     interpara[3] = this.equation;
/*  953 */     interpara[4] = this.result;
/*      */     try {
/*  955 */       out.writeObject(this.state);
/*  956 */       out.writeObject(interpara);
/*  957 */       out.writeObject(this.opseq);
/*  958 */       out.writeObject(this.selcell);
/*      */     }
/*      */     catch (Exception e1) {
/*  961 */       e1.printStackTrace();
/*  962 */       System.out.println("Save fails");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void changeTableStrct()
/*      */   {
/*  971 */     this.interModel.fireTableStructureChanged();
/*  972 */     this.algbModel.fireTableStructureChanged();
/*  973 */     if (this.dispForm == 2) {
/*  974 */       this.table.setModel(this.interModel);
/*  975 */       for (int i = 3; i < this.table.getColumnCount(); i++) {
/*  976 */         this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*  977 */         this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*      */       }
/*  979 */       for (i = 0; i < 3; i++) {
/*  980 */         this.table.getColumn(this.table.getColumnName(i)).setPreferredWidth(20);
/*      */       }
/*      */     }
/*      */     
/*  984 */     this.table.setModel(this.algbModel);
/*  985 */     for (int i = 3; i < this.table.getColumnCount(); i++) {
/*  986 */       this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*  987 */       this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*      */     }
/*  989 */     this.table.getColumn(this.table.getColumnName(0)).setPreferredWidth(1);
/*  990 */     this.table.getColumn(this.table.getColumnName(1)).setPreferredWidth(20);
/*  991 */     this.table.getColumn(this.table.getColumnName(2)).setPreferredWidth(20);
/*      */     
/*  993 */     this.table.setPreferredScrollableViewportSize(new Dimension(500, 100));
/*  994 */     this.table.setCellSelectionEnabled(true);
/*  995 */     this.table.setSelectionMode(0);
/*  996 */     this.table.sizeColumnsToFit(-1);
/*  997 */     this.table.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean prcMultiple(double[] ec, double[] em)
/*      */   {
/* 1005 */     for (int i = 0; i < this.nBasic + 1; i++) {
/* 1006 */       if (i == this.equation) {
/* 1007 */         ec[i] = 0.0D;
/* 1008 */         em[i] = 0.0D;
/*      */       }
/*      */       else {
/* 1011 */         String input = this.multiple[i].getText();
/* 1012 */         if (input.length() <= 0) {
/* 1013 */           JOptionPane.showMessageDialog(this, "Invalid input multiple!");
/* 1014 */           return false;
/*      */         }
/* 1016 */         int ps = input.indexOf('m');
/* 1017 */         boolean bool; if (ps == -1) {
/* 1018 */           int ls = input.indexOf('M');
/* 1019 */           if (ls == -1) {
/*      */             try {
/* 1021 */               ec[i] = Double.parseDouble(input);
/*      */             } catch (NumberFormatException e) {
/* 1023 */               JOptionPane.showMessageDialog(this, "Invalid input number");
/* 1024 */               return false;
/*      */             }
/* 1026 */             em[i] = 0.0D;
/*      */           }
/* 1028 */           else if (ls > 0) {
/* 1029 */             if (ls >= input.length() - 1) {
/* 1030 */               ec[i] = 0.0D;
/*      */             } else {
/*      */               try {
/* 1033 */                 ec[i] = Double.parseDouble(input.substring(ls + 1));
/*      */               } catch (NumberFormatException e) {
/* 1035 */                 JOptionPane.showMessageDialog(this, "Invalid input number");
/* 1036 */                 return false;
/*      */               }
/*      */             }
/*      */             try {
/* 1040 */               em[i] = Double.parseDouble(input.substring(0, ls));
/*      */             } catch (NumberFormatException e) {
/* 1042 */               JOptionPane.showMessageDialog(this, "Invalid input number");
/* 1043 */               return false;
/*      */             }
/*      */           }
/*      */           else {
/* 1047 */             JOptionPane.showMessageDialog(this, "Invalid input multiple!");
/* 1048 */             return false;
/*      */           }
/*      */         }
/* 1051 */         else if (ps > 0) {
/* 1052 */           if (ps >= input.length() - 1) {
/* 1053 */             ec[i] = 0.0D;
/*      */           } else {
/*      */             try {
/* 1056 */               ec[i] = Double.parseDouble(input.substring(ps + 1));
/*      */             } catch (NumberFormatException e) {
/* 1058 */               JOptionPane.showMessageDialog(this, "Invalid input number");
/* 1059 */               return false;
/*      */             }
/*      */           }
/*      */           try {
/* 1063 */             em[i] = Double.parseDouble(input.substring(0, ps));
/*      */           } catch (NumberFormatException e) {
/* 1065 */             JOptionPane.showMessageDialog(this, "Invalid input number");
/* 1066 */             return false;
/*      */           }
/*      */         }
/*      */         else {
/* 1070 */           JOptionPane.showMessageDialog(this, "Invalid input multiple!");
/* 1071 */           return false;
/*      */         }
/*      */       }
/*      */     }
/* 1075 */     return true;
/*      */   }
/*      */   
/*      */   private void addSelectionListener()
/*      */   {
/* 1080 */     int span = this.nBasic;
/*      */     
/*      */ 
/* 1083 */     ListSelectionModel rowSM = this.table.getSelectionModel();
/* 1084 */     rowSM.addListSelectionListener(new ListSelectionListener()
/*      */     {
/*      */       public void valueChanged(ListSelectionEvent e)
/*      */       {
/* 1088 */         ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/* 1089 */         if (!lsm.isSelectionEmpty())
/*      */         {
/* 1091 */           int selrow = IORLPInteractPanel.this.table.getSelectedRow();
/* 1092 */           int selcol = IORLPInteractPanel.this.table.getSelectedColumn();
/* 1093 */           if ((selrow >= 0) && (selcol >= 3) && (selcol < IORLPInteractPanel.this.nAllvar + 3) && (IORLPInteractPanel.this.step == 2)) {
/* 1094 */             IORLPInteractPanel.this.row = selrow;
/* 1095 */             IORLPInteractPanel.this.col = selcol;
/* 1096 */             IORLPInteractPanel.this.SelBlock(IORLPInteractPanel.this.table, selrow, selcol);
/*      */           }
/*      */           
/*      */         }
/*      */       }
/* 1101 */     });
/* 1102 */     ListSelectionModel colSM = this.table.getColumnModel().getSelectionModel();
/*      */     
/* 1104 */     colSM.addListSelectionListener(new ListSelectionListener()
/*      */     {
/*      */       public void valueChanged(ListSelectionEvent e)
/*      */       {
/* 1108 */         ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/* 1109 */         if (!lsm.isSelectionEmpty())
/*      */         {
/* 1111 */           int selrow = IORLPInteractPanel.this.table.getSelectedRow();
/* 1112 */           int selcol = IORLPInteractPanel.this.table.getSelectedColumn();
/* 1113 */           if ((selrow >= 0) && (selcol >= 3) && (selcol < IORLPInteractPanel.this.nAllvar + 3) && (IORLPInteractPanel.this.step == 2)) {
/* 1114 */             IORLPInteractPanel.this.row = selrow;
/* 1115 */             IORLPInteractPanel.this.col = selcol;
/* 1116 */             IORLPInteractPanel.this.SelBlock(IORLPInteractPanel.this.table, selrow, selcol);
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void clrTableColor(JTable table)
/*      */   {
/* 1130 */     for (int i = 0; i < table.getRowCount(); i++) {
/* 1131 */       for (int j = 0; j < table.getColumnCount(); j++) {
/* 1132 */         Object anycel = table.getModel().getValueAt(i, j);
/* 1133 */         if ((anycel instanceof IORTableCell)) {
/* 1134 */           ((IORTableCell)anycel).isBlock = false;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void SelBlock(JTable table, int selrow, int selcol) {
/* 1141 */     JTable tmptable = table;
/*      */     
/*      */ 
/*      */ 
/* 1145 */     if ((selrow > 0) && (selcol >= 3) && (selcol < this.nAllvar + 3) && ((this.step == 2) || (this.step == 3)))
/*      */     {
/* 1147 */       clrTableColor(tmptable);
/* 1148 */       for (int j = 3; j < this.nAllvar + 4; j++) {
/* 1149 */         Object anycel = tmptable.getModel().getValueAt(selrow, j);
/* 1150 */         if ((anycel instanceof IORTableCell)) {
/* 1151 */           ((IORTableCell)anycel).isBlock = true;
/*      */         }
/*      */       }
/* 1154 */       for (int i = 0; i < this.nBasic + 1; i++) {
/* 1155 */         Object anycel = tmptable.getModel().getValueAt(i, selcol);
/* 1156 */         if ((anycel instanceof IORTableCell))
/* 1157 */           ((IORTableCell)anycel).isBlock = true;
/*      */       }
/*      */     }
/* 1160 */     tmptable.repaint();
/*      */   }
/*      */   
/*      */   class IORTableModel extends AbstractTableModel {
/*      */     private int i;
/*      */     private int j;
/*      */     private String weirdstr;
/* 1167 */     private IORTableCell[][] data = new IORTableCell[IORLPInteractPanel.this.controller.data.num_constrain + 2][IORLPInteractPanel.this.controller.data.num_variable + 2];
/*      */     
/*      */     public IORTableModel() {
/* 1170 */       for (this.i = 0; this.i < IORLPInteractPanel.this.controller.data.num_constrain + 2; this.i += 1) {
/* 1171 */         for (this.j = 0; this.j < IORLPInteractPanel.this.controller.data.num_variable + 2; this.j += 1) {
/* 1172 */           this.data[this.i][this.j] = new IORTableCell(0.0D, 0.0D);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public int getColumnCount() {
/* 1178 */       return IORLPInteractPanel.this.controller.data.num_variable + 4;
/*      */     }
/*      */     
/*      */     public int getRowCount() {
/* 1182 */       return IORLPInteractPanel.this.controller.data.num_constrain + 1;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col) {
/* 1186 */       if (col == 0)
/* 1187 */         return "Bas Var";
/* 1188 */       if (col == 1)
/* 1189 */         return "Eq.";
/* 1190 */       if (col == 2)
/* 1191 */         return "Z";
/* 1192 */       if ((col >= 3) && (col < 3 + IORLPInteractPanel.this.controller.data.num_variable)) {
/* 1193 */         if (IORLPInteractPanel.this.controller.data.is_artificial[(col - 2)] != 0) {
/* 1194 */           return "X".concat(String.valueOf(String.valueOf(col - 2)));
/*      */         }
/*      */         
/* 1197 */         return "x".concat(String.valueOf(String.valueOf(col - 2)));
/*      */       }
/*      */       
/* 1200 */       return "Right Side";
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col)
/*      */     {
/* 1205 */       int xsub = 0;
/*      */       
/*      */ 
/* 1208 */       if (col == 1)
/* 1209 */         return new Integer(row);
/* 1210 */       if (col == 2) {
/* 1211 */         if ((row == 0) && (IORLPInteractPanel.this.controller.data.objective_is_max))
/* 1212 */           return new Integer(1);
/* 1213 */         if (row == 0) {
/* 1214 */           return new Integer(-1);
/*      */         }
/* 1216 */         return new Integer(0);
/*      */       }
/* 1218 */       if (col == 0) {
/* 1219 */         if (row == 0) {
/* 1220 */           return "Z";
/*      */         }
/* 1222 */         xsub = IORLPInteractPanel.this.controller.data.basic_variable_index[row];
/* 1223 */         if (IORLPInteractPanel.this.controller.data.is_artificial[xsub] != 0) {
/* 1224 */           return "X".concat(String.valueOf(String.valueOf(xsub)));
/*      */         }
/* 1226 */         return "x".concat(String.valueOf(String.valueOf(xsub)));
/*      */       }
/*      */       
/* 1229 */       if ((col >= 3) && (col < IORLPInteractPanel.this.controller.data.num_variable + 3)) {
/* 1230 */         if (row == 0) {
/* 1231 */           double mc = IORLPInteractPanel.this.controller.data.objective_M_coefficient[(col - 2)];
/* 1232 */           double ec = IORLPInteractPanel.this.controller.data.objective_coefficient[(col - 2)];
/* 1233 */           this.data[row][(col - 3)].setPara(mc, ec);
/* 1234 */           return this.data[row][(col - 3)];
/*      */         }
/*      */         
/* 1237 */         this.data[row][(col - 3)].setPara(0.0D, IORLPInteractPanel.this.controller.data.constrain_coefficient[row][(col - 2)]);
/* 1238 */         return this.data[row][(col - 3)];
/*      */       }
/*      */       
/*      */ 
/* 1242 */       if (row == 0) {
/* 1243 */         double mc = IORLPInteractPanel.this.controller.data.objective_M_coefficient[0];
/* 1244 */         double ec = IORLPInteractPanel.this.controller.data.objective_coefficient[0];
/* 1245 */         this.data[row][(col - 3)].setPara(mc, ec);
/* 1246 */         return this.data[row][(col - 3)];
/*      */       }
/*      */       
/* 1249 */       this.data[row][(col - 3)].setPara(0.0D, IORLPInteractPanel.this.controller.data.constrain_coefficient[row][0]);
/* 1250 */       return this.data[row][(col - 3)];
/*      */     }
/*      */     
/*      */ 
/*      */     public Class getColumnClass(int c)
/*      */     {
/* 1256 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1262 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */   class IORAlgebraModel extends AbstractTableModel
/*      */   {
/*      */     private int i;
/*      */     private int j;
/* 1270 */     private IORTableCell[][] data = new IORTableCell[IORLPInteractPanel.this.controller.data.num_constrain + 2][IORLPInteractPanel.this.controller.data.num_variable + 2];
/*      */     
/*      */     public IORAlgebraModel() {
/* 1273 */       for (this.i = 0; this.i < IORLPInteractPanel.this.controller.data.num_constrain + 2; this.i += 1) {
/* 1274 */         for (this.j = 0; this.j < IORLPInteractPanel.this.controller.data.num_variable + 2; this.j += 1) {
/* 1275 */           this.data[this.i][this.j] = new IORTableCell(0.0D, 0.0D);
/* 1276 */           this.data[this.i][this.j].setDisplayForm(1);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public int getColumnCount() {
/* 1282 */       return IORLPInteractPanel.this.controller.data.num_variable + 4;
/*      */     }
/*      */     
/*      */     public int getRowCount() {
/* 1286 */       return IORLPInteractPanel.this.controller.data.num_constrain + 1;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col) {
/* 1290 */       if (col == 0)
/* 1291 */         return "empty";
/* 1292 */       if (col == 1)
/* 1293 */         return "Eq.";
/* 1294 */       if (col == 2)
/* 1295 */         return "Z";
/* 1296 */       if ((col >= 3) && (col < 3 + IORLPInteractPanel.this.controller.data.num_variable)) {
/* 1297 */         return "x".concat(String.valueOf(String.valueOf(col - 2)));
/*      */       }
/*      */       
/* 1300 */       return "Right Side";
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col) {
/* 1304 */       int xsub = 0;
/*      */       
/*      */ 
/* 1307 */       if (col == 0)
/* 1308 */         return " ";
/* 1309 */       if (col == 1) {
/* 1310 */         return String.valueOf(String.valueOf(row)).concat(")");
/*      */       }
/* 1312 */       if (col == 2) {
/* 1313 */         if (row == 0) {
/* 1314 */           if (IORLPInteractPanel.this.controller.data.original_objective_is_max) {
/* 1315 */             return "Z";
/*      */           }
/* 1317 */           return "-Z";
/*      */         }
/*      */         
/* 1320 */         return " ";
/*      */       }
/*      */       
/* 1323 */       if ((col >= 3) && (col < IORLPInteractPanel.this.controller.data.num_variable + 3)) {
/* 1324 */         if (row == 0) {
/* 1325 */           double mc = IORLPInteractPanel.this.controller.data.objective_M_coefficient[(col - 2)];
/* 1326 */           double ec = IORLPInteractPanel.this.controller.data.objective_coefficient[(col - 2)];
/* 1327 */           this.data[row][(col - 3)].setPara(mc, ec);
/* 1328 */           if (IORLPInteractPanel.this.controller.data.is_artificial[(col - 2)] != 0) {
/* 1329 */             this.data[row][(col - 3)].setXstring("X".concat(String.valueOf(String.valueOf(col - 2))));
/*      */           } else
/* 1331 */             this.data[row][(col - 3)].setXstring("x".concat(String.valueOf(String.valueOf(col - 2))));
/* 1332 */           return this.data[row][(col - 3)];
/*      */         }
/*      */         
/* 1335 */         this.data[row][(col - 3)].setPara(0.0D, IORLPInteractPanel.this.controller.data.constrain_coefficient[row][(col - 2)]);
/* 1336 */         if (IORLPInteractPanel.this.controller.data.is_artificial[(col - 2)] != 0) {
/* 1337 */           this.data[row][(col - 3)].setXstring("X".concat(String.valueOf(String.valueOf(col - 2))));
/*      */         } else
/* 1339 */           this.data[row][(col - 3)].setXstring("x".concat(String.valueOf(String.valueOf(col - 2))));
/* 1340 */         return this.data[row][(col - 3)];
/*      */       }
/*      */       
/*      */ 
/* 1344 */       if (row == 0) {
/* 1345 */         double mc = IORLPInteractPanel.this.controller.data.objective_M_coefficient[0];
/* 1346 */         double ec = IORLPInteractPanel.this.controller.data.objective_coefficient[0];
/* 1347 */         this.data[row][(col - 3)].setPara(mc, ec);
/* 1348 */         this.data[row][(col - 3)].setXstring(" = ");
/* 1349 */         return this.data[row][(col - 3)];
/*      */       }
/*      */       
/* 1352 */       this.data[row][(col - 3)].setPara(0.0D, IORLPInteractPanel.this.controller.data.constrain_coefficient[row][0]);
/* 1353 */       this.data[row][(col - 3)].setXstring(" = ");
/* 1354 */       return this.data[row][(col - 3)];
/*      */     }
/*      */     
/*      */ 
/*      */     public Class getColumnClass(int c)
/*      */     {
/* 1360 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1366 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/* 1375 */     String str = "Solve Interactively by the Simplex Method\n\n";
/* 1376 */     str = String.valueOf(String.valueOf(str)).concat("This procedure allows you to apply the Simplex Method interactively ");
/* 1377 */     str = String.valueOf(String.valueOf(str)).concat("to solve a linear programming problem. At each iteration, the computer ");
/* 1378 */     str = String.valueOf(String.valueOf(str)).concat("does the calculations while you make the following sequence of decisions:\n");
/* 1379 */     str = String.valueOf(String.valueOf(str)).concat("    1.  Decide whether or not the current solution is optimal\n");
/* 1380 */     str = String.valueOf(String.valueOf(str)).concat("    2.  Choose the entering basic variable\n");
/* 1381 */     str = String.valueOf(String.valueOf(str)).concat("    3.  Choose the pivot equation containing the leaving basic variable\n");
/* 1382 */     str = String.valueOf(String.valueOf(str)).concat("    4.  Choose the factor by which to divide the pivot equation\n");
/* 1383 */     str = String.valueOf(String.valueOf(str)).concat("    5.  Choose the multiple of the pivot equation to subtract from each of ");
/* 1384 */     str = String.valueOf(String.valueOf(str)).concat("the other equations in turn.\n\n");
/* 1385 */     str = String.valueOf(String.valueOf(str)).concat("Is the current solution optimal?\n\n");
/* 1386 */     str = String.valueOf(String.valueOf(str)).concat("Enter y or Y and then press the FINISH button if the current solution is optimal. ");
/* 1387 */     str = String.valueOf(String.valueOf(str)).concat("Otherwise, enter either n or N and then press the NEXT button. If there are multiple ");
/* 1388 */     str = String.valueOf(String.valueOf(str)).concat("optima, you can investigate the other optimal solutions. Just enter n or N, and ");
/* 1389 */     str = String.valueOf(String.valueOf(str)).concat("then press the NEXT button.");
/* 1390 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/* 1392 */     this.help_content_step.setText(str);
/* 1393 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/* 1400 */     String str = "Solve Interactively by the Simplex Method\n\n";
/* 1401 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to interactively execute the Simplex Method as ");
/* 1402 */     str = String.valueOf(String.valueOf(str)).concat("presented in Sec.4.3. Your role is to apply the logic of the algorithm, ");
/* 1403 */     str = String.valueOf(String.valueOf(str)).concat("and the computer will do the number crunching. The computer also will ");
/* 1404 */     str = String.valueOf(String.valueOf(str)).concat("inform you if you make a mistake on the first iteration.\n\n");
/* 1405 */     str = String.valueOf(String.valueOf(str)).concat("This procedure can handle up to 6 functional constraints and 10 variables, ");
/* 1406 */     str = String.valueOf(String.valueOf(str)).concat("(including slack, surplus, and artificial variables),");
/* 1407 */     str = String.valueOf(String.valueOf(str)).concat("which encompasses all relevant problems at the end of Chap. 4.\n\n");
/* 1408 */     str = String.valueOf(String.valueOf(str)).concat("When you finish a problem, you can print out all your work by choosing Print to File ");
/* 1409 */     str = String.valueOf(String.valueOf(str)).concat("under the File menu. If you are interrupted before you finish, you can ");
/* 1410 */     str = String.valueOf(String.valueOf(str)).concat("save your work (choose Save under the File menu) and return later (choose Open).");
/* 1411 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/* 1413 */     this.help_content_procedure.setText(str);
/* 1414 */     this.help_content_procedure.revalidate();
/*      */   }
/*      */   
/* 1417 */   private String str0 = "Solve Interactively by the Simplex Method\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1427 */   private String str1 = "Is the current solution optimal?\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1432 */   private String str2 = "Selecting the entering and the leaving basic variable \n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1438 */   private String str3 = "Choosing the multiple of the pivot equation to subtract from another equation\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1443 */   private String str8 = "What to do once an optimal solution has been reached\n\n";
/*      */   
/*      */ 
/*      */ 
/* 1447 */   private String str81 = "What to do once an optimal solution has been reached\n\n";
/*      */   
/*      */ 
/* 1450 */   private String str4 = "Determine which case holds\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1461 */   private String str5 = "Pressing the NEXT button to remove the artificial variables from the tableau\n\n";
/*      */   
/*      */ 
/*      */ 
/* 1465 */   private String str6 = "Pressing the NEXT button to replace Eq.0 by the original objective function\n\n";
/*      */   
/*      */ 
/*      */ 
/* 1469 */   private String str7 = "Pressing the NEXT button to convert the tableau to proper form from Gaussian elimination\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void updateStepHelpPanel()
/*      */   {
/* 1480 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/* 1481 */     switch (this.step) {
/*      */     case 1: 
/* 1483 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str1).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1511 */       break;
/*      */     case 2: 
/* 1486 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str2).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1511 */       break;
/*      */     case 3: 
/* 1489 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str3).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1511 */       break;
/*      */     case 4: 
/* 1492 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str4).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1511 */       break;
/*      */     case 5: 
/* 1495 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str5).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1511 */       break;
/*      */     case 6: 
/* 1498 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str6).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1511 */       break;
/*      */     case 7: 
/* 1501 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str7).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1511 */       break;
/*      */     case 8: 
/* 1504 */       if (this.isTwoPhase) this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str81).append(str)))); else {
/* 1505 */         this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str8).append(str))));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1511 */       break;
/*      */     default: 
/* 1508 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPInteractPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */