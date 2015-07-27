/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Insets;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.text.DecimalFormat;
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
/*      */ import javax.swing.table.AbstractTableModel;
/*      */ import javax.swing.table.TableColumnModel;
/*      */ 
/*      */ public class IORMKDiscountPanel extends IORActionPanel
/*      */ {
/*   30 */   private char SIGMA = '∑';
/*   31 */   private char ALPHA = 'α';
/*      */   
/*   33 */   private IORMDProcessController controller = null;
/*      */   
/*   35 */   private JPanel mainPanel = new JPanel();
/*      */   
/*   37 */   private JPanel topPanel = null;
/*   38 */   private JPanel funcPanel = null;
/*   39 */   private JPanel equPanel = null;
/*   40 */   private JPanel centerPanel = null;
/*   41 */   private JPanel bottomPanel = null;
/*   42 */   private JPanel tablePanel = new JPanel();
/*   43 */   private JPanel swtPanel = new JPanel();
/*   44 */   private JPanel transtPanel = new JPanel();
/*   45 */   private JPanel buttPanel = new JPanel();
/*   46 */   private JPanel cikPanel = new JPanel();
/*   47 */   private JPanel pijPanel = new JPanel();
/*   48 */   private JPanel diPanel = new JPanel();
/*   49 */   private JPanel stendPanel = new JPanel();
/*   50 */   private JPanel newdesPanel = new JPanel();
/*   51 */   private JPanel oldpoPanel = null;
/*   52 */   private JPanel newpoPanel = null;
/*   53 */   private JPanel soluPanel = null;
/*   54 */   private JPanel yornPanel = new JPanel();
/*      */   
/*   56 */   private JScrollPane costPane = null;
/*   57 */   private JScrollPane probPane = null;
/*   58 */   private JScrollPane desPane = null;
/*      */   
/*   60 */   private IORMKDiscountPanel.IORCostTableModel costModel = null;
/*      */   private JTable costTable;
/*   62 */   private IORMKDiscountPanel.IORProbTableModel probModel = null;
/*      */   private JTable probTable;
/*   64 */   private IORMKDiscountPanel.IORDesTableModel desModel = null;
/*      */   private JTable desTable;
/*      */   private JPanel[] grPanel;
/*      */   private JPanel[] exprPanel;
/*      */   private JTextField[][] grcolabel;
/*      */   private JTextField[][] exprcolabel;
/*   70 */   private JButton backbutt = new JButton("Back");
/*   71 */   private JButton nextbutt = new JButton("Next");
/*   72 */   private JButton enterbutt = new JButton("Enter");
/*      */   
/*   74 */   private JButton incbutt = new JButton("+");
/*   75 */   private JButton decbutt = new JButton("-");
/*   76 */   private JComboBox yorn = new JComboBox();
/*      */   
/*   78 */   private JLabel vdlabel = new JLabel();
/*   79 */   private JLabel ctlabel = new JLabel("Cik");
/*   80 */   private JLabel prlabel = new JLabel(); private JLabel deslabel = new JLabel();
/*   81 */   private JTextArea statetext = new JTextArea();
/*      */   
/*   83 */   private int nState = 4; private int nDecision = 5;
/*   84 */   private DecimalFormat decfm = new DecimalFormat();
/*   85 */   private DecimalFormat fltfm = new DecimalFormat();
/*   86 */   private DecimalFormat expfm = new DecimalFormat("##.#E0");
/*      */   
/*   88 */   private WholeNumberField newdifd = new WholeNumberField(1, 2);
/*      */   
/*   90 */   private int itcnt = 1;
/*   91 */   private int step = 1;
/*      */   private int selected_k;
/*      */   private int current_i;
/*      */   private int loc;
/*      */   private int selected_table;
/*   96 */   private int selected_row; private int selected_col; private int current_eq; private int current_state; private int current_pos; private Vector opseq = new Vector();
/*      */   
/*   98 */   private String histr = "For the highlighted coefficient above , choose the appropriate value from the \ntables below and then press the Enter button. To display the transition matrix (Pij)\nfor a different k , press the \"+\" or \"-\" button. Press the Back button to backtrack.";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IORMKDiscountPanel(IORMDProcessController c)
/*      */   {
/*  109 */     super(c);
/*  110 */     this.controller = c;
/*  111 */     this.state = new IORState(this.controller.solver);
/*  112 */     add(this.mainPanel);
/*  113 */     this.bn_back.setVisible(false);
/*  114 */     this.bn_finish.setVisible(false);
/*  115 */     this.actionStatus.setVisible(false);
/*      */     
/*  117 */     this.nState = this.controller.data.num_states;
/*  118 */     this.nDecision = this.controller.data.num_decisions;
/*      */     
/*  120 */     this.selected_k = 1;
/*  121 */     this.decfm.setMaximumFractionDigits(3);
/*  122 */     this.fltfm.setMaximumFractionDigits(1);
/*  123 */     this.decfm.setGroupingUsed(false);
/*  124 */     this.fltfm.setGroupingUsed(false);
/*  125 */     this.expfm.setGroupingUsed(false);
/*      */     
/*  127 */     this.grPanel = new JPanel[this.nState];
/*  128 */     this.exprPanel = new JPanel[this.nDecision];
/*  129 */     this.grcolabel = new JTextField[this.nState][this.nState + 1];
/*  130 */     this.exprcolabel = new JTextField[this.nDecision][this.nState + 2];
/*      */     
/*  132 */     this.statetext.setEditable(false);
/*  133 */     this.statetext.setBackground(getBackground());
/*  134 */     this.statetext.setAlignmentX(0.0F);
/*      */     
/*      */ 
/*  137 */     for (int i = 0; i < this.nState; i++) {
/*  138 */       this.grPanel[i] = new JPanel();
/*  139 */       this.grcolabel[i][0] = new JTextField(5);
/*  140 */       this.grcolabel[i][0].setEditable(false);
/*  141 */       this.grcolabel[i][0].setBackground(getBackground());
/*  142 */       for (int j = 1; j < this.nState + 1; j++) {
/*  143 */         this.grcolabel[i][j] = new JTextField(4);
/*  144 */         this.grcolabel[i][j].setEditable(false);
/*  145 */         this.grcolabel[i][j].setBackground(getBackground());
/*      */       }
/*      */     }
/*  148 */     for (i = 0; i < this.nDecision; i++) {
/*  149 */       this.exprPanel[i] = new JPanel();
/*  150 */       this.exprcolabel[i][0] = new JTextField(5);
/*  151 */       this.exprcolabel[i][0].setEditable(false);
/*  152 */       this.exprcolabel[i][0].setBackground(getBackground());
/*  153 */       for (int j = 1; j < this.nState + 1; j++) {
/*  154 */         this.exprcolabel[i][j] = new JTextField(4);
/*  155 */         this.exprcolabel[i][j].setEditable(false);
/*  156 */         this.exprcolabel[i][j].setBackground(getBackground());
/*      */       }
/*  158 */       this.exprcolabel[i][(this.nState + 1)] = new JTextField(5);
/*  159 */       this.exprcolabel[i][(this.nState + 1)].setEditable(false);
/*  160 */       this.exprcolabel[i][(this.nState + 1)].setBackground(getBackground());
/*      */     }
/*      */     
/*  163 */     this.nextbutt.setText("Enter");
/*  164 */     this.buttPanel.add(this.backbutt);
/*  165 */     this.buttPanel.add(Box.createHorizontalStrut(20));
/*  166 */     this.buttPanel.add(this.nextbutt);
/*      */     
/*  168 */     this.decbutt.setEnabled(false);
/*  169 */     this.decbutt.setMargin(new Insets(0, 5, 0, 5));
/*  170 */     this.incbutt.setMargin(new Insets(0, 5, 0, 5));
/*  171 */     this.swtPanel.setLayout(new BoxLayout(this.swtPanel, 1));
/*  172 */     this.swtPanel.add(Box.createRigidArea(new Dimension(0, 12)));
/*  173 */     this.swtPanel.add(this.decbutt);
/*  174 */     this.swtPanel.add(new JLabel(" k "));
/*  175 */     this.swtPanel.add(this.incbutt);
/*      */     
/*  177 */     this.yorn.addItem("No");
/*  178 */     this.yorn.addItem("Yes");
/*      */     
/*  180 */     this.topPanel = new JPanel();
/*      */     
/*      */ 
/*      */ 
/*  184 */     this.funcPanel = new JPanel();
/*  185 */     this.funcPanel.setLayout(new BoxLayout(this.funcPanel, 1));
/*  186 */     this.funcPanel.setPreferredSize(new Dimension(this.nState * 100 + 340, this.nState * 30));
/*  187 */     this.funcPanel.setMaximumSize(new Dimension(this.nState * 100 + 340, this.nState * 30));
/*      */     
/*  189 */     this.equPanel = new JPanel();
/*  190 */     this.equPanel.setLayout(new BoxLayout(this.equPanel, 1));
/*  191 */     this.equPanel.setPreferredSize(new Dimension(this.nState * 100 + 340, this.nDecision * 30));
/*  192 */     this.equPanel.setMaximumSize(new Dimension(this.nState * 100 + 340, this.nDecision * 30));
/*      */     
/*  194 */     this.bottomPanel = new JPanel();
/*  195 */     this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel, 1));
/*  196 */     this.cikPanel.setLayout(new BoxLayout(this.cikPanel, 1));
/*  197 */     this.pijPanel.setLayout(new BoxLayout(this.pijPanel, 1));
/*  198 */     this.diPanel.setLayout(new BoxLayout(this.diPanel, 1));
/*      */     
/*  200 */     this.soluPanel = new JPanel();
/*  201 */     this.soluPanel.setLayout(new BoxLayout(this.soluPanel, 1));
/*  202 */     this.stendPanel.setAlignmentX(0.0F);
/*      */     
/*  204 */     this.newdesPanel.setAlignmentX(0.0F);
/*  205 */     this.newdesPanel.setMaximumSize(new Dimension(300, 30));
/*      */     
/*  207 */     this.ctlabel.setAlignmentX(0.5F);
/*  208 */     this.prlabel.setAlignmentX(0.5F);
/*  209 */     this.deslabel.setAlignmentX(0.5F);
/*      */     
/*  211 */     this.costModel = new IORMKDiscountPanel.IORCostTableModel();
/*  212 */     this.costTable = new JTable(this.costModel);
/*  213 */     this.costTable.setCellSelectionEnabled(true);
/*  214 */     this.costTable.setSelectionMode(0);
/*  215 */     this.costTable.setSelectionBackground(Color.cyan);
/*  216 */     this.costTable.setRowHeight(20);
/*  217 */     this.costPane = new JScrollPane(this.costTable);
/*  218 */     this.costPane.setAlignmentX(0.5F);
/*      */     
/*  220 */     this.probModel = new IORMKDiscountPanel.IORProbTableModel();
/*  221 */     this.probTable = new JTable(this.probModel);
/*  222 */     this.probTable.setCellSelectionEnabled(true);
/*  223 */     this.probTable.setSelectionMode(0);
/*  224 */     this.probTable.setSelectionBackground(Color.cyan);
/*  225 */     this.probTable.setRowHeight(20);
/*  226 */     this.probPane = new JScrollPane(this.probTable);
/*  227 */     this.probPane.setAlignmentX(0.5F);
/*      */     
/*  229 */     this.desModel = new IORMKDiscountPanel.IORDesTableModel();
/*  230 */     this.desTable = new JTable(this.desModel);
/*  231 */     this.desTable.setEnabled(false);
/*  232 */     this.desTable.setRowHeight(20);
/*  233 */     this.desPane = new JScrollPane(this.desTable);
/*  234 */     this.desPane.setAlignmentX(0.5F);
/*      */     
/*      */ 
/*  237 */     addSelectionListener(this.costTable);
/*  238 */     addSelectionListener(this.probTable);
/*      */     
/*  240 */     this.costPane.setPreferredSize(new Dimension(50 * (this.nDecision + 1), 24 + 20 * this.nState));
/*  241 */     this.costPane.setMaximumSize(new Dimension(50 * (this.nDecision + 1), 24 + 20 * this.nState));
/*  242 */     this.probPane.setPreferredSize(new Dimension(60 * this.nState, 24 + 20 * this.nState));
/*  243 */     this.probPane.setMaximumSize(new Dimension(60 * this.nState, 24 + 20 * this.nState));
/*  244 */     this.desPane.setPreferredSize(new Dimension(60, 24 + 20 * this.nState));
/*  245 */     this.desPane.setMaximumSize(new Dimension(60, 24 + 20 * this.nState));
/*      */     
/*  247 */     this.centerPanel = new JPanel();
/*  248 */     this.centerPanel.setLayout(new BoxLayout(this.centerPanel, 1));
/*  249 */     this.centerPanel.setAlignmentX(0.5F);
/*  250 */     this.centerPanel.setPreferredSize(new Dimension(560, 150));
/*  251 */     this.centerPanel.setMaximumSize(new Dimension(560, 150));
/*      */     
/*  253 */     this.oldpoPanel = new JPanel();
/*  254 */     this.oldpoPanel.setLayout(new BoxLayout(this.oldpoPanel, 1));
/*      */     
/*  256 */     this.newpoPanel = new JPanel();
/*  257 */     this.newpoPanel.setLayout(new BoxLayout(this.newpoPanel, 1));
/*      */     
/*  259 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*  260 */     this.mainPanel.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
/*      */     
/*  262 */     InitialPanel();
/*      */     
/*      */ 
/*  265 */     this.nextbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  267 */         IORMKDiscountPanel.this.doNext();
/*      */       }
/*  269 */     });
/*  270 */     this.backbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  272 */         IORMKDiscountPanel.this.doBack();
/*      */       }
/*      */     });
/*      */     
/*  276 */     if (this.selected_k > 1) {
/*  277 */       this.decbutt.setEnabled(true);
/*      */     } else
/*  279 */       this.decbutt.setEnabled(false);
/*  280 */     if (this.selected_k < this.nDecision) {
/*  281 */       this.incbutt.setEnabled(true);
/*      */     } else {
/*  283 */       this.incbutt.setEnabled(false);
/*      */     }
/*  285 */     this.incbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  287 */         if (IORMKDiscountPanel.this.selected_k < IORMKDiscountPanel.this.nDecision) {
/*  288 */           IORMKDiscountPanel.access$4(IORMKDiscountPanel.this);
/*  289 */           IORMKDiscountPanel.this.decbutt.setEnabled(true);
/*  290 */           if (IORMKDiscountPanel.this.selected_k >= IORMKDiscountPanel.this.nDecision)
/*  291 */             IORMKDiscountPanel.this.incbutt.setEnabled(false);
/*      */         }
/*  293 */         IORMKDiscountPanel.this.probModel.fireTableDataChanged();
/*  294 */         IORMKDiscountPanel.this.probTable.getSelectionModel().clearSelection();
/*  295 */         IORMKDiscountPanel.this.probTable.getColumnModel().getSelectionModel().clearSelection();
/*  296 */         IORMKDiscountPanel.this.prlabel.setText("Pij(k) , k=".concat(String.valueOf(String.valueOf(IORMKDiscountPanel.this.selected_k))));
/*  297 */         IORMKDiscountPanel.this.pijPanel.revalidate();
/*  298 */         IORMKDiscountPanel.this.pijPanel.repaint();
/*      */       }
/*      */       
/*  301 */     });
/*  302 */     this.decbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  304 */         if (IORMKDiscountPanel.this.selected_k > 1) {
/*  305 */           IORMKDiscountPanel.access$11(IORMKDiscountPanel.this);
/*  306 */           IORMKDiscountPanel.this.incbutt.setEnabled(true);
/*  307 */           if (IORMKDiscountPanel.this.selected_k == 1)
/*  308 */             IORMKDiscountPanel.this.decbutt.setEnabled(false);
/*      */         }
/*  310 */         IORMKDiscountPanel.this.probModel.fireTableDataChanged();
/*  311 */         IORMKDiscountPanel.this.probTable.getSelectionModel().clearSelection();
/*  312 */         IORMKDiscountPanel.this.probTable.getColumnModel().getSelectionModel().clearSelection();
/*  313 */         IORMKDiscountPanel.this.prlabel.setText("Pij(k) , k=".concat(String.valueOf(String.valueOf(IORMKDiscountPanel.this.selected_k))));
/*  314 */         IORMKDiscountPanel.this.pijPanel.revalidate();
/*  315 */         IORMKDiscountPanel.this.pijPanel.repaint();
/*      */       }
/*      */       
/*  318 */     });
/*  319 */     this.costTable.addFocusListener(new java.awt.event.FocusListener() {
/*      */       public void focusLost(FocusEvent e) {
/*  321 */         IORMKDiscountPanel.this.costTable.getSelectionModel().clearSelection();
/*  322 */         IORMKDiscountPanel.this.costTable.getColumnModel().getSelectionModel().clearSelection();
/*      */       }
/*      */       
/*      */ 
/*      */       public void focusGained(FocusEvent e) {}
/*  327 */     });
/*  328 */     this.probTable.addFocusListener(new java.awt.event.FocusListener() {
/*      */       public void focusLost(FocusEvent e) {
/*  330 */         IORMKDiscountPanel.this.probTable.getSelectionModel().clearSelection();
/*  331 */         IORMKDiscountPanel.this.probTable.getColumnModel().getSelectionModel().clearSelection();
/*      */       }
/*      */       
/*      */ 
/*      */       public void focusGained(FocusEvent e) {}
/*      */     });
/*      */   }
/*      */   
/*      */   private void InitialPanel()
/*      */   {
/*  341 */     this.vdlabel.setText("Step 1 :  Value Determination. ");
/*  342 */     this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the equations : Vi(R").append(this.itcnt).append(") = Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k)Vj(R").append(this.itcnt).append(") , i=0,...,").append(this.nState - 1).append("\n").append("to be solved for Vi(R").append(this.itcnt).append(") \n\n").append(this.histr))));
/*      */     
/*      */ 
/*  345 */     this.centerPanel.add(this.vdlabel);
/*  346 */     this.centerPanel.add(Box.createVerticalStrut(10));
/*  347 */     this.centerPanel.add(this.statetext);
/*      */     
/*  349 */     BuildFuncPanel();
/*  350 */     BuildBottomPanel();
/*      */     
/*  352 */     this.topPanel.add(this.funcPanel);
/*      */     
/*  354 */     this.mainPanel.add(this.topPanel);
/*  355 */     this.mainPanel.add(Box.createVerticalStrut(10));
/*  356 */     this.mainPanel.add(this.centerPanel);
/*  357 */     this.mainPanel.add(Box.createVerticalStrut(10));
/*  358 */     this.mainPanel.add(this.bottomPanel);
/*  359 */     this.mainPanel.add(Box.createVerticalStrut(10));
/*  360 */     this.mainPanel.add(this.buttPanel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void BuildFuncPanel()
/*      */   {
/*  367 */     this.funcPanel.removeAll();
/*      */     
/*  369 */     for (int i = 0; i < this.nState; i++) {
/*  370 */       this.grPanel[i].removeAll();
/*  371 */       this.grPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") = ")))));
/*  372 */       this.grcolabel[i][0].setText(this.decfm.format(this.controller.data.grcoef[i][0]));
/*  373 */       if (Math.abs(this.controller.data.grcoef[i][0] - 'ϧ') < 0.01D)
/*  374 */         this.grcolabel[i][0].setText(" --- ");
/*  375 */       this.grPanel[i].add(this.grcolabel[i][0]);
/*  376 */       this.grPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" + ").append(this.decfm.format(this.controller.data.discount)).append(" [")))));
/*  377 */       for (int j = 1; j < this.nState + 1; j++) {
/*  378 */         if (j > 1)
/*  379 */           this.grPanel[i].add(new JLabel(" + "));
/*  380 */         this.grcolabel[i][j].setText(this.decfm.format(this.controller.data.grcoef[i][j]));
/*  381 */         this.grPanel[i].add(this.grcolabel[i][j]);
/*  382 */         this.grPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(j - 1).append("(R").append(this.itcnt).append(")")))));
/*      */       }
/*  384 */       this.grPanel[i].add(new JLabel("]"));
/*  385 */       this.grPanel[i].add(Box.createHorizontalStrut(30));
/*  386 */       this.grPanel[i].add(new JLabel(" i = ".concat(String.valueOf(String.valueOf(i)))));
/*      */       
/*  388 */       this.grcolabel[0][0].setBackground(Color.cyan);
/*      */       
/*  390 */       this.loc = 0;
/*  391 */       this.current_i = 0;
/*      */       
/*  393 */       this.funcPanel.add(this.grPanel[i]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildEquPanel()
/*      */   {
/*  402 */     this.equPanel.removeAll();
/*      */     
/*  404 */     for (int i = 0; i < this.nDecision; i++) {
/*  405 */       this.exprPanel[i].removeAll();
/*  406 */       this.exprcolabel[i][0].setText(this.decfm.format(this.controller.data.exprcoef[i][0]));
/*  407 */       if (Math.abs(this.controller.data.exprcoef[i][0] - 'ϧ') < 0.01D)
/*  408 */         this.exprcolabel[i][0].setText(" --- ");
/*  409 */       this.exprPanel[i].add(this.exprcolabel[i][0]);
/*  410 */       this.exprPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" + (").append(this.decfm.format(this.controller.data.discount)).append(")[")))));
/*  411 */       for (int j = 1; j < this.nState + 1; j++) {
/*  412 */         if (j > 1)
/*  413 */           this.exprPanel[i].add(new JLabel(" + "));
/*  414 */         this.exprcolabel[i][j].setText(this.decfm.format(this.controller.data.exprcoef[i][j]));
/*  415 */         this.exprPanel[i].add(this.exprcolabel[i][j]);
/*  416 */         if (Math.abs(this.controller.data.vr[(j - 1)]) > 10000.0D) {
/*  417 */           this.exprPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" (").append(this.expfm.format(this.controller.data.vr[(j - 1)])).append(")")))));
/*      */         } else
/*  419 */           this.exprPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" (").append(this.fltfm.format(this.controller.data.vr[(j - 1)])).append(")")))));
/*      */       }
/*  421 */       this.exprPanel[i].add(new JLabel("] = "));
/*  422 */       if (Math.abs(this.controller.data.expr[this.current_state][i]) > 10000.0D) {
/*  423 */         this.exprcolabel[i][(this.nState + 1)].setText(this.expfm.format(this.controller.data.expr[this.current_state][i]));
/*      */       } else {
/*  425 */         this.exprcolabel[i][(this.nState + 1)].setText(this.fltfm.format(this.controller.data.expr[this.current_state][i]));
/*      */       }
/*  427 */       if (Math.abs(this.controller.data.expr[this.current_state][i] - 'ϧ') < 0.01D)
/*  428 */         this.exprcolabel[i][(this.nState + 1)].setText(" --- ");
/*  429 */       this.exprPanel[i].add(this.exprcolabel[i][(this.nState + 1)]);
/*  430 */       this.exprPanel[i].add(Box.createHorizontalStrut(30));
/*  431 */       this.exprPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("k = ").append(i + 1)))));
/*      */       
/*  433 */       this.equPanel.add(this.exprPanel[i]);
/*      */     }
/*      */   }
/*      */   
/*      */   private void BuildBottomPanel()
/*      */   {
/*  439 */     this.prlabel.setText("Pij(k) , k=".concat(String.valueOf(String.valueOf(this.selected_k))));
/*  440 */     this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt).append(")"))));
/*      */     
/*  442 */     this.cikPanel.add(this.ctlabel);
/*  443 */     this.cikPanel.add(this.costPane);
/*  444 */     this.pijPanel.add(this.prlabel);
/*  445 */     this.pijPanel.add(this.probPane);
/*  446 */     this.transtPanel.add(this.pijPanel);
/*  447 */     this.transtPanel.add(this.swtPanel);
/*  448 */     this.diPanel.add(this.deslabel);
/*  449 */     this.diPanel.add(this.desPane);
/*      */     
/*  451 */     this.tablePanel.add(this.cikPanel);
/*  452 */     this.tablePanel.add(Box.createHorizontalStrut(20));
/*  453 */     this.tablePanel.add(this.transtPanel);
/*  454 */     this.tablePanel.add(Box.createHorizontalStrut(20));
/*  455 */     this.tablePanel.add(this.diPanel);
/*  456 */     this.bottomPanel.add(this.tablePanel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void finishProcedure() {}
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
/*      */ 
/*      */   private void doEnter()
/*      */   {
/*  483 */     int[] match = new int[5];
/*      */     
/*  485 */     Vector par = new Vector();
/*  486 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*  488 */     match[0] = this.current_i;
/*  489 */     match[1] = this.loc;
/*  490 */     match[2] = this.selected_table;
/*  491 */     match[3] = this.selected_row;
/*  492 */     match[4] = (this.selected_col - 1);
/*      */     
/*  494 */     if (this.selected_col == 0) {
/*  495 */       JOptionPane.showMessageDialog(this, "this column cannot be selected");
/*  496 */       return;
/*      */     }
/*      */     
/*  499 */     par.addElement(match);
/*      */     
/*  501 */     opr.parameters = par;
/*  502 */     opr.operation_code = 40301;
/*  503 */     if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  504 */       String err = this.controller.solver.getErrInfo();
/*  505 */       JOptionPane.showMessageDialog(this, err);
/*  506 */       return;
/*      */     }
/*      */     
/*      */ 
/*  510 */     this.state.addStep(opr);
/*      */     
/*  512 */     this.opseq.addElement(new Integer(this.step));
/*      */     
/*      */ 
/*  515 */     this.grcolabel[this.current_i][this.loc].setText(this.decfm.format(this.controller.data.grcoef[this.current_i][this.loc]));
/*  516 */     if (Math.abs(this.controller.data.grcoef[this.current_i][this.loc] - 'ϧ') < 0.01D)
/*  517 */       this.grcolabel[this.current_i][this.loc].setText(" --- ");
/*  518 */     if ((this.current_i == this.nState - 1) && (this.loc == this.nState)) {
/*  519 */       this.grcolabel[(this.nState - 1)][this.nState].setBackground(getBackground());
/*  520 */       String vrs = " ";
/*  521 */       for (int i = 0; i < this.nState - 1; i++) {
/*  522 */         vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") ,"))))));
/*      */       }
/*  524 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press the NEXT button to solve the set of equations displayed above for the unknown \nvalues : ").append(vrs).append(" and V").append(this.nState - 1).append("(R").append(this.itcnt).append(") \n"))));
/*      */       
/*      */ 
/*  527 */       this.nextbutt.setText("Next");
/*  528 */       this.step = 2;
/*      */     }
/*  530 */     else if (this.loc >= this.nState) {
/*  531 */       if (this.current_i < this.nState - 1) {
/*  532 */         this.current_i += 1;
/*  533 */         this.loc = 0;
/*  534 */         this.grcolabel[(this.current_i - 1)][this.nState].setBackground(getBackground());
/*  535 */         this.grcolabel[this.current_i][0].setBackground(Color.cyan);
/*      */       }
/*  537 */       this.step = 1;
/*      */     }
/*      */     else {
/*  540 */       this.loc += 1;
/*  541 */       this.grcolabel[this.current_i][(this.loc - 1)].setBackground(getBackground());
/*  542 */       this.grcolabel[this.current_i][this.loc].setBackground(Color.cyan);
/*  543 */       this.step = 1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void doRetrace()
/*      */   {
/*  551 */     this.controller.solver.reset();
/*  552 */     this.state.back();
/*  553 */     this.controller.solver.getData(this.controller.data);
/*      */     
/*      */ 
/*  556 */     if (this.step == 2) {
/*  557 */       this.current_i = (this.nState - 1);
/*  558 */       this.loc = this.nState;
/*  559 */       this.grcolabel[this.current_i][this.loc].setBackground(Color.cyan);
/*  560 */       this.topPanel.revalidate();
/*  561 */       this.topPanel.repaint();
/*      */       
/*  563 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the equations : Vi(R").append(this.itcnt).append(") = Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k").append(this.itcnt).append(") Vj(R").append(this.itcnt).append(") , i=0,...,").append(this.nState - 1).append("\n").append("to be solved for Vi(R").append(this.itcnt).append(") \n\n").append(this.histr))));
/*      */       
/*      */ 
/*  566 */       this.centerPanel.removeAll();
/*  567 */       this.centerPanel.add(this.vdlabel);
/*  568 */       this.centerPanel.add(Box.createVerticalStrut(10));
/*  569 */       this.centerPanel.add(this.statetext);
/*  570 */       this.nextbutt.setText("Enter");
/*  571 */       this.buttPanel.revalidate();
/*  572 */       this.buttPanel.repaint();
/*      */     }
/*  574 */     else if (this.loc == 0) {
/*  575 */       if (this.current_i > 0) {
/*  576 */         this.current_i -= 1;
/*  577 */         this.loc = this.nState;
/*  578 */         this.grcolabel[(this.current_i + 1)][0].setBackground(getBackground());
/*  579 */         this.grcolabel[this.current_i][this.loc].setBackground(Color.cyan);
/*      */       }
/*      */     }
/*      */     else {
/*  583 */       this.loc -= 1;
/*  584 */       this.grcolabel[this.current_i][(this.loc + 1)].setBackground(getBackground());
/*  585 */       this.grcolabel[this.current_i][this.loc].setBackground(Color.cyan);
/*      */     }
/*  587 */     this.step = 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doExpress()
/*      */   {
/*  596 */     int[] match = new int[5];
/*      */     
/*  598 */     Vector par = new Vector();
/*  599 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*  601 */     if ((this.itcnt == 1) && (this.current_state == 0))
/*      */     {
/*  603 */       match[0] = (this.current_eq + 1);
/*  604 */       match[1] = this.current_pos;
/*  605 */       match[2] = this.selected_table;
/*  606 */       match[3] = this.selected_row;
/*  607 */       match[4] = (this.selected_col - 1);
/*      */       
/*  609 */       if (this.selected_col == 0) {
/*  610 */         JOptionPane.showMessageDialog(this, "this column cannot be selected");
/*  611 */         return;
/*      */       }
/*      */       
/*      */ 
/*  615 */       par.addElement(match);
/*      */       
/*  617 */       opr.parameters = par;
/*  618 */       opr.operation_code = 40304;
/*  619 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  620 */         String err = this.controller.solver.getErrInfo();
/*  621 */         JOptionPane.showMessageDialog(this, err);
/*  622 */         return;
/*      */       }
/*      */       
/*      */ 
/*  626 */       this.state.addStep(opr);
/*      */       
/*  628 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*      */ 
/*  631 */       this.exprcolabel[this.current_eq][this.current_pos].setText(this.decfm.format(this.controller.data.exprcoef[this.current_eq][this.current_pos]));
/*  632 */       if (Math.abs(this.controller.data.exprcoef[this.current_eq][this.current_pos] - 'ϧ') < 0.01D) {
/*  633 */         this.exprcolabel[this.current_eq][this.current_pos].setText(" --- ");
/*      */       }
/*  635 */       if (Math.abs(this.controller.data.expr[this.current_state][this.current_eq]) > 10000.0D) {
/*  636 */         this.exprcolabel[this.current_eq][(this.nState + 1)].setText(this.expfm.format(this.controller.data.expr[this.current_state][this.current_eq]));
/*      */       } else {
/*  638 */         this.exprcolabel[this.current_eq][(this.nState + 1)].setText(this.fltfm.format(this.controller.data.expr[this.current_state][this.current_eq]));
/*      */       }
/*  640 */       if (Math.abs(this.controller.data.expr[this.current_state][this.current_eq] - 'ϧ') < 0.01D) {
/*  641 */         this.exprcolabel[this.current_eq][(this.nState + 1)].setText(" --- ");
/*      */       }
/*  643 */       if ((this.current_eq == this.nDecision - 1) && (this.current_pos == this.nState)) {
/*  644 */         this.exprcolabel[this.current_eq][this.current_pos].setBackground(getBackground());
/*      */         
/*  646 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k) Vj(R").append(this.itcnt).append(") , k=1, ...,").append(this.nDecision).append("\n").append("evaluated for i = ").append(this.current_state).append(" and for each possible value of k.\n"))));
/*      */         
/*      */ 
/*  649 */         this.newdesPanel.removeAll();
/*  650 */         this.newdesPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Enter the value of d").append(this.current_state).append("(R").append(this.itcnt + 1).append(") : ")))));
/*  651 */         this.newdesPanel.add(this.newdifd);
/*  652 */         this.centerPanel.removeAll();
/*  653 */         this.centerPanel.add(this.vdlabel);
/*  654 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  655 */         this.centerPanel.add(this.statetext);
/*  656 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  657 */         this.centerPanel.add(this.newdesPanel);
/*  658 */         this.centerPanel.add(Box.createVerticalStrut(30));
/*  659 */         this.nextbutt.setText("Next");
/*  660 */         this.step = 5;
/*      */       }
/*  662 */       else if (this.current_pos >= this.nState) {
/*  663 */         if (this.current_eq < this.nDecision - 1) {
/*  664 */           this.current_eq += 1;
/*  665 */           this.current_pos = 0;
/*  666 */           this.exprcolabel[(this.current_eq - 1)][this.nState].setBackground(getBackground());
/*  667 */           this.exprcolabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*      */         }
/*  669 */         this.step = 4;
/*      */       }
/*      */       else {
/*  672 */         this.current_pos += 1;
/*  673 */         this.exprcolabel[this.current_eq][(this.current_pos - 1)].setBackground(getBackground());
/*  674 */         this.exprcolabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*  675 */         this.step = 4;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  680 */       par.addElement(new Integer(this.current_state));
/*  681 */       opr.parameters = par;
/*  682 */       opr.operation_code = 40305;
/*  683 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  684 */         String err = this.controller.solver.getErrInfo();
/*  685 */         JOptionPane.showMessageDialog(this, err);
/*  686 */         return;
/*      */       }
/*      */       
/*      */ 
/*  690 */       this.state.addStep(opr);
/*      */       
/*  692 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*      */ 
/*  695 */       for (int i = 0; i < this.nDecision; i++) {
/*  696 */         this.exprcolabel[i][0].setText(this.decfm.format(this.controller.data.exprcoef[i][0]));
/*  697 */         if (Math.abs(this.controller.data.exprcoef[i][0] - 'ϧ') < 0.01D)
/*  698 */           this.exprcolabel[i][0].setText(" --- ");
/*  699 */         for (int j = 1; j < this.nState + 1; j++) {
/*  700 */           this.exprcolabel[i][j].setText(this.decfm.format(this.controller.data.exprcoef[i][j]));
/*      */         }
/*  702 */         if (Math.abs(this.controller.data.expr[this.current_state][i]) > 10000.0D) {
/*  703 */           this.exprcolabel[i][(this.nState + 1)].setText(this.expfm.format(this.controller.data.expr[this.current_state][i]));
/*      */         } else {
/*  705 */           this.exprcolabel[i][(this.nState + 1)].setText(this.fltfm.format(this.controller.data.expr[this.current_state][i]));
/*      */         }
/*  707 */         if (Math.abs(this.controller.data.expr[this.current_state][i] - 'ϧ') < 0.01D) {
/*  708 */           this.exprcolabel[i][(this.nState + 1)].setText(" --- ");
/*      */         }
/*      */       }
/*  711 */       this.exprcolabel[(this.nDecision - 1)][this.nState].setBackground(getBackground());
/*      */       
/*  713 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k) Vj(R").append(this.itcnt).append(") , k=1, ...,").append(this.nDecision).append("\n").append("evaluated for i = ").append(this.current_state).append(" and for each possible value of k.\n"))));
/*      */       
/*      */ 
/*  716 */       this.newdesPanel.removeAll();
/*  717 */       this.newdesPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Enter the value of d").append(this.current_state).append("(R").append(this.itcnt + 1).append(") : ")))));
/*  718 */       this.newdesPanel.add(this.newdifd);
/*  719 */       this.newdesPanel.setAlignmentX(0.0F);
/*  720 */       this.centerPanel.removeAll();
/*  721 */       this.centerPanel.add(this.vdlabel);
/*  722 */       this.centerPanel.add(Box.createVerticalStrut(10));
/*  723 */       this.centerPanel.add(this.statetext);
/*  724 */       this.centerPanel.add(Box.createVerticalStrut(10));
/*  725 */       this.centerPanel.add(this.newdesPanel);
/*  726 */       this.centerPanel.add(Box.createVerticalStrut(30));
/*  727 */       this.nextbutt.setText("Next");
/*  728 */       this.step = 5;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doUndochose()
/*      */   {
/*  740 */     this.controller.solver.reset();
/*  741 */     this.state.back();
/*  742 */     this.controller.solver.getData(this.controller.data);
/*      */     
/*      */ 
/*  745 */     if (this.step == 5) {
/*  746 */       if ((this.itcnt == 1) && (this.current_state == 0)) {
/*  747 */         this.current_eq = (this.nDecision - 1);
/*  748 */         this.current_pos = this.nState;
/*  749 */         this.exprcolabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*  750 */         this.topPanel.revalidate();
/*  751 */         this.topPanel.repaint();
/*      */         
/*      */ 
/*  754 */         String vrs = " ";
/*  755 */         for (int i = 0; i < this.nState; i++) {
/*  756 */           vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.vr[i])))))));
/*  757 */           if (i < this.nState - 1) {
/*  758 */             vrs = String.valueOf(String.valueOf(vrs)).concat(" , ");
/*      */           }
/*      */         }
/*  761 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k) Vj(R").append(this.itcnt).append(") , k=1, ...,").append(this.nDecision).append("\n").append("to be evaluated for i = ").append(this.current_state).append(" where \n").append(vrs).append("\n\n").append(this.histr))));
/*      */         
/*      */ 
/*      */ 
/*  765 */         this.centerPanel.removeAll();
/*  766 */         this.centerPanel.add(this.vdlabel);
/*  767 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  768 */         this.centerPanel.add(this.statetext);
/*  769 */         this.nextbutt.setText("Enter");
/*  770 */         this.buttPanel.revalidate();
/*  771 */         this.buttPanel.repaint();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  787 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press the NEXT button to have the computer evaluate the expression for state ").append(this.current_state).append("."))));
/*  788 */         this.centerPanel.removeAll();
/*  789 */         this.centerPanel.add(this.vdlabel);
/*  790 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  791 */         this.centerPanel.add(this.statetext);
/*      */       }
/*      */     }
/*  794 */     else if (this.current_pos == 0) {
/*  795 */       if (this.current_eq > 0) {
/*  796 */         this.current_eq -= 1;
/*  797 */         this.current_pos = this.nState;
/*  798 */         this.exprcolabel[(this.current_eq + 1)][0].setBackground(getBackground());
/*  799 */         this.exprcolabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*      */       }
/*      */     }
/*      */     else {
/*  803 */       this.current_pos -= 1;
/*  804 */       this.exprcolabel[this.current_eq][(this.current_pos + 1)].setBackground(getBackground());
/*  805 */       this.exprcolabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*      */     }
/*  807 */     this.step = 4;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doBack()
/*      */   {
/*  816 */     if (this.opseq.isEmpty() == false) {
/*  817 */       this.nextbutt.setEnabled(true);
/*  818 */       int last = ((Integer)this.opseq.lastElement()).intValue();
/*  819 */       this.opseq.removeElementAt(this.opseq.size() - 1);
/*  820 */       if (this.opseq.isEmpty()) {
/*  821 */         this.backbutt.setEnabled(false);
/*      */       }
/*  823 */       switch (last) {
/*      */       case 1: 
/*  825 */         doRetrace();
/*  826 */         revalidate();
/*  827 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  987 */         break;
/*      */       case 2: 
/*  831 */         this.controller.solver.reset();
/*  832 */         this.state.back();
/*  833 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  836 */         String vrs = " ";
/*  837 */         for (int i = 0; i < this.nState - 1; i++) {
/*  838 */           vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") ,"))))));
/*      */         }
/*  840 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press the NEXT button to solve the set of equations displayed above for the unknown \nvalues : ").append(vrs).append(" and V").append(this.nState - 1).append("(R").append(this.itcnt).append(") \n"))));
/*      */         
/*      */ 
/*  843 */         this.centerPanel.removeAll();
/*  844 */         this.centerPanel.add(this.vdlabel);
/*  845 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  846 */         this.centerPanel.add(this.statetext);
/*  847 */         this.step = 2;
/*      */         
/*  849 */         revalidate();
/*  850 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  987 */         break;
/*      */       case 3: 
/*  854 */         this.controller.solver.reset();
/*  855 */         this.state.back();
/*  856 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  859 */         this.vdlabel.setText("Step 1 :  Value Determination. ");
/*  860 */         this.soluPanel.removeAll();
/*  861 */         for (int i = 0; i < this.nState; i++) {
/*  862 */           this.soluPanel.add(Box.createVerticalStrut(5));
/*  863 */           this.soluPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(")  =  ").append(this.fltfm.format(this.controller.data.vr[i]))))));
/*      */         }
/*  865 */         this.stendPanel.removeAll();
/*  866 */         this.stendPanel.add(this.soluPanel);
/*  867 */         this.stendPanel.add(Box.createRigidArea(new Dimension(80, 10)));
/*  868 */         this.stendPanel.add(new JLabel("Press the NEXT button to begin Step 2 : Policy Improvement"));
/*  869 */         this.centerPanel.removeAll();
/*  870 */         this.centerPanel.add(this.vdlabel);
/*  871 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  872 */         this.centerPanel.add(this.stendPanel);
/*      */         
/*  874 */         BuildFuncPanel();
/*  875 */         this.grcolabel[0][0].setBackground(getBackground());
/*  876 */         this.topPanel.removeAll();
/*  877 */         this.topPanel.add(this.funcPanel);
/*  878 */         this.nextbutt.setText("Next");
/*  879 */         this.step = 3;
/*      */         
/*  881 */         revalidate();
/*  882 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  987 */         break;
/*      */       case 4: 
/*  885 */         doUndochose();
/*      */         
/*  887 */         revalidate();
/*  888 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  987 */         break;
/*      */       case 5: 
/*  892 */         this.controller.solver.reset();
/*  893 */         this.state.back();
/*  894 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  896 */         if (this.step == 6) {
/*  897 */           this.current_state = (this.nState - 1);
/*  898 */         } else if (this.step == 4) {
/*  899 */           this.current_state -= 1;
/*      */           
/*  901 */           for (int i = 0; i < this.nDecision; i++) {
/*  902 */             this.exprcolabel[i][0].setText(this.decfm.format(this.controller.data.exprcoef[i][0]));
/*  903 */             if (Math.abs(this.controller.data.exprcoef[i][0] - 'ϧ') < 0.01D)
/*  904 */               this.exprcolabel[i][0].setText(" --- ");
/*  905 */             for (int j = 1; j < this.nState + 1; j++) {
/*  906 */               this.exprcolabel[i][j].setText(this.decfm.format(this.controller.data.exprcoef[i][j]));
/*      */             }
/*  908 */             this.exprcolabel[i][(this.nState + 1)].setText(this.fltfm.format(this.controller.data.expr[this.current_state][i]));
/*  909 */             if (Math.abs(this.controller.data.expr[this.current_state][i] - 'ϧ') < 0.01D) {
/*  910 */               this.exprcolabel[i][(this.nState + 1)].setText(" --- ");
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  915 */         this.vdlabel.setText("Step 2 : Policy Improvement    STATE = ".concat(String.valueOf(String.valueOf(this.current_state))));
/*  916 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k) Vj(R").append(this.itcnt).append(") , k=1, ...,").append(this.nDecision).append("\n").append("evaluated for i = ").append(this.current_state).append(" and for each possible value of k.\n"))));
/*      */         
/*      */ 
/*  919 */         this.newdesPanel.removeAll();
/*  920 */         this.newdesPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Enter the value of d").append(this.current_state).append("(R").append(this.itcnt + 1).append(") : ")))));
/*  921 */         this.newdesPanel.add(this.newdifd);
/*  922 */         this.centerPanel.removeAll();
/*  923 */         this.centerPanel.add(this.vdlabel);
/*  924 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  925 */         this.centerPanel.add(this.statetext);
/*  926 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  927 */         this.centerPanel.add(this.newdesPanel);
/*  928 */         this.centerPanel.add(Box.createVerticalStrut(30));
/*  929 */         this.step = 5;
/*      */         
/*  931 */         revalidate();
/*  932 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  987 */         break;
/*      */       case 6: 
/*  936 */         this.controller.solver.reset();
/*  937 */         this.state.back();
/*  938 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  941 */         if (this.step == 1) {
/*  942 */           this.itcnt -= 1;
/*      */           
/*  944 */           this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt).append(")"))));
/*  945 */           this.current_state = (this.nState - 1);
/*  946 */           BuildEquPanel();
/*  947 */           this.current_eq = 0;
/*  948 */           this.current_pos = 0;
/*      */           
/*      */ 
/*  951 */           this.topPanel.removeAll();
/*  952 */           this.topPanel.add(this.equPanel);
/*  953 */           this.nextbutt.setText("Next");
/*      */         }
/*      */         
/*  956 */         this.oldpoPanel.removeAll();
/*  957 */         this.newpoPanel.removeAll();
/*      */         
/*  959 */         this.oldpoPanel.add(new JLabel("Old Policy"));
/*  960 */         this.oldpoPanel.add(Box.createVerticalStrut(5));
/*  961 */         this.newpoPanel.add(new JLabel("New Policy"));
/*  962 */         this.newpoPanel.add(Box.createVerticalStrut(5));
/*  963 */         for (int i = 0; i < this.nState; i++) {
/*  964 */           this.oldpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt).append(") = ").append(this.decfm.format(this.controller.data.old_decision[i]))))));
/*  965 */           this.newpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt + 1).append(") = ").append(this.decfm.format(this.controller.data.new_decision[i]))))));
/*      */         }
/*      */         
/*  968 */         this.yornPanel.removeAll();
/*  969 */         this.yornPanel.add(new JLabel("Is the current policy optimal?"));
/*  970 */         this.yornPanel.add(this.yorn);
/*  971 */         this.yornPanel.add(Box.createRigidArea(new Dimension(30, 50)));
/*  972 */         this.yornPanel.add(this.oldpoPanel);
/*  973 */         this.yornPanel.add(Box.createRigidArea(new Dimension(10, 50)));
/*  974 */         this.yornPanel.add(this.newpoPanel);
/*      */         
/*  976 */         this.centerPanel.removeAll();
/*  977 */         this.centerPanel.add(this.yornPanel);
/*  978 */         this.step = 6;
/*      */         
/*  980 */         revalidate();
/*  981 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  987 */         break;
/*      */       default: 
/*  984 */         System.out.println("Markov decision cannot back here.");break;
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void doNext()
/*      */   {
/*  994 */     Vector par = new Vector();
/*  995 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*  997 */     if (this.backbutt.isEnabled() == false)
/*  998 */       this.backbutt.setEnabled(true);
/*  999 */     switch (this.step) {
/*      */     case 1: 
/* 1001 */       doEnter();
/* 1002 */       revalidate();
/* 1003 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1233 */       break;
/*      */     case 2: 
/* 1007 */       opr.parameters = par;
/* 1008 */       opr.operation_code = 40302;
/* 1009 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1010 */         String err = this.controller.solver.getErrInfo();
/* 1011 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1016 */         this.state.addStep(opr);
/*      */         
/* 1018 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1021 */         this.soluPanel.removeAll();
/* 1022 */         for (int i = 0; i < this.nState; i++) {
/* 1023 */           this.soluPanel.add(Box.createVerticalStrut(5));
/* 1024 */           this.soluPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(")  =  ").append(this.fltfm.format(this.controller.data.vr[i]))))));
/*      */         }
/* 1026 */         this.stendPanel.removeAll();
/* 1027 */         this.stendPanel.add(this.soluPanel);
/* 1028 */         this.stendPanel.add(Box.createRigidArea(new Dimension(80, 10)));
/* 1029 */         this.stendPanel.add(new JLabel("Press the NEXT button to begin Step 2 : Policy Improvement"));
/* 1030 */         this.centerPanel.removeAll();
/* 1031 */         this.centerPanel.add(this.vdlabel);
/* 1032 */         this.centerPanel.add(Box.createVerticalStrut(10));
/* 1033 */         this.centerPanel.add(this.stendPanel);
/* 1034 */         this.step = 3;
/*      */         
/* 1036 */         revalidate();
/* 1037 */         repaint();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1233 */       break;
/*      */     case 3: 
/* 1041 */       opr.parameters = par;
/* 1042 */       opr.operation_code = 40303;
/* 1043 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1044 */         String err = this.controller.solver.getErrInfo();
/* 1045 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1050 */         this.state.addStep(opr);
/*      */         
/* 1052 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1055 */         if (this.itcnt == 1) {
/* 1056 */           this.current_state = 0;
/*      */           
/* 1058 */           BuildEquPanel();
/* 1059 */           this.exprcolabel[0][0].setBackground(Color.cyan);
/* 1060 */           this.current_eq = 0;
/* 1061 */           this.current_pos = 0;
/*      */           
/* 1063 */           this.topPanel.removeAll();
/* 1064 */           this.topPanel.add(this.equPanel);
/*      */           
/* 1066 */           this.vdlabel.setText("Step 2 : Policy Improvement    STATE = ".concat(String.valueOf(String.valueOf(this.current_state))));
/* 1067 */           String vrs = " ";
/* 1068 */           for (int i = 0; i < this.nState; i++) {
/* 1069 */             vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.vr[i])))))));
/* 1070 */             if (i < this.nState - 1)
/* 1071 */               vrs = String.valueOf(String.valueOf(vrs)).concat(" , ");
/*      */           }
/* 1073 */           this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k) Vj(R").append(this.itcnt).append(") , k=1, ...,").append(this.nDecision).append("\n").append("to be evaluated for i = ").append(this.current_state).append(" where \n").append(vrs).append("\n\n").append(this.histr))));
/*      */           
/*      */ 
/*      */ 
/* 1077 */           this.centerPanel.removeAll();
/* 1078 */           this.centerPanel.add(this.vdlabel);
/* 1079 */           this.centerPanel.add(Box.createVerticalStrut(10));
/* 1080 */           this.centerPanel.add(this.statetext);
/* 1081 */           this.nextbutt.setText("Enter");
/* 1082 */           this.step = 4;
/*      */         }
/*      */         else {
/* 1085 */           this.current_state = 0;
/*      */           
/* 1087 */           BuildEquPanel();
/* 1088 */           this.current_eq = 0;
/* 1089 */           this.current_pos = 0;
/*      */           
/* 1091 */           this.topPanel.removeAll();
/* 1092 */           this.topPanel.add(this.equPanel);
/*      */           
/* 1094 */           this.vdlabel.setText("Step 2 : Policy Improvement    STATE = ".concat(String.valueOf(String.valueOf(this.current_state))));
/* 1095 */           this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press the NEXT button to have the computer evaluate the expression for state ").append(this.current_state).append("."))));
/* 1096 */           this.centerPanel.removeAll();
/* 1097 */           this.centerPanel.add(this.vdlabel);
/* 1098 */           this.centerPanel.add(Box.createVerticalStrut(10));
/* 1099 */           this.centerPanel.add(this.statetext);
/* 1100 */           this.step = 4;
/*      */         }
/*      */         
/* 1103 */         revalidate();
/* 1104 */         repaint();
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
/* 1233 */       break;
/*      */     case 4: 
/* 1107 */       doExpress();
/* 1108 */       revalidate();
/* 1109 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1233 */       break;
/*      */     case 5: 
/* 1113 */       par.addElement(new Integer(this.current_state));
/* 1114 */       par.addElement(new Integer(this.newdifd.getValue()));
/* 1115 */       opr.parameters = par;
/* 1116 */       opr.operation_code = 40306;
/* 1117 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1118 */         String err = this.controller.solver.getErrInfo();
/* 1119 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1124 */         this.state.addStep(opr);
/*      */         
/* 1126 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1129 */         if (this.current_state < this.nState - 1) {
/* 1130 */           this.current_state += 1;
/* 1131 */           this.vdlabel.setText("Step 2 : Policy Improvement    STATE = ".concat(String.valueOf(String.valueOf(this.current_state))));
/* 1132 */           this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press the NEXT button to have the computer evaluate the expression for state ").append(this.current_state).append("."))));
/* 1133 */           this.centerPanel.removeAll();
/* 1134 */           this.centerPanel.add(this.vdlabel);
/* 1135 */           this.centerPanel.add(Box.createVerticalStrut(10));
/* 1136 */           this.centerPanel.add(this.statetext);
/* 1137 */           this.step = 4;
/*      */         }
/*      */         else {
/* 1140 */           this.oldpoPanel.removeAll();
/* 1141 */           this.newpoPanel.removeAll();
/*      */           
/* 1143 */           this.oldpoPanel.add(new JLabel("Old Policy"));
/* 1144 */           this.oldpoPanel.add(Box.createVerticalStrut(5));
/* 1145 */           this.newpoPanel.add(new JLabel("New Policy"));
/* 1146 */           this.newpoPanel.add(Box.createVerticalStrut(5));
/* 1147 */           for (int i = 0; i < this.nState; i++) {
/* 1148 */             this.oldpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt).append(") = ").append(this.decfm.format(this.controller.data.old_decision[i]))))));
/* 1149 */             this.newpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt + 1).append(") = ").append(this.decfm.format(this.controller.data.new_decision[i]))))));
/*      */           }
/* 1151 */           this.yornPanel.removeAll();
/* 1152 */           this.yornPanel.add(new JLabel("Is the current policy optimal?"));
/* 1153 */           this.yornPanel.add(this.yorn);
/* 1154 */           this.yornPanel.add(Box.createRigidArea(new Dimension(30, 50)));
/* 1155 */           this.yornPanel.add(this.oldpoPanel);
/* 1156 */           this.yornPanel.add(Box.createRigidArea(new Dimension(10, 50)));
/* 1157 */           this.yornPanel.add(this.newpoPanel);
/*      */           
/* 1159 */           this.centerPanel.removeAll();
/* 1160 */           this.centerPanel.add(this.yornPanel);
/* 1161 */           this.step = 6;
/*      */         }
/*      */         
/* 1164 */         revalidate();
/* 1165 */         repaint();
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
/* 1233 */       break;
/*      */     case 6: 
/* 1169 */       opr.parameters = par;
/* 1170 */       opr.operation_code = 40307;
/* 1171 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1172 */         String err = this.controller.solver.getErrInfo();
/* 1173 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1178 */         this.state.addStep(opr);
/*      */         
/* 1180 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1183 */         if (this.yorn.getSelectedIndex() == 0) {
/* 1184 */           this.itcnt += 1;
/*      */           
/* 1186 */           this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt).append(")"))));
/* 1187 */           BuildFuncPanel();
/* 1188 */           this.topPanel.removeAll();
/* 1189 */           this.topPanel.add(this.funcPanel);
/*      */           
/* 1191 */           this.vdlabel.setText("Step 1 :  Value Determination. ");
/* 1192 */           this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the equations : Vi(R").append(this.itcnt).append(") = Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k)Vj(R").append(this.itcnt).append(") , i=0,...,").append(this.nState - 1).append("\n").append("to be solved for Vi(R").append(this.itcnt).append(") \n\n").append(this.histr))));
/*      */           
/*      */ 
/* 1195 */           this.centerPanel.removeAll();
/* 1196 */           this.centerPanel.add(this.vdlabel);
/* 1197 */           this.centerPanel.add(Box.createVerticalStrut(10));
/* 1198 */           this.centerPanel.add(this.statetext);
/* 1199 */           this.nextbutt.setText("Enter");
/* 1200 */           this.step = 1;
/*      */         }
/*      */         else {
/* 1203 */           this.oldpoPanel.removeAll();
/* 1204 */           this.newpoPanel.removeAll();
/*      */           
/* 1206 */           for (int i = 0; i < this.nState; i++) {
/* 1207 */             this.newpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt).append(") = ").append(this.decfm.format(this.controller.data.new_decision[i]))))));
/* 1208 */             this.oldpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.vr[i]))))));
/*      */           }
/*      */           
/* 1211 */           this.yornPanel.removeAll();
/* 1212 */           this.yornPanel.add(new JLabel("Optimal policy : "));
/* 1213 */           this.yornPanel.add(Box.createRigidArea(new Dimension(20, 50)));
/* 1214 */           this.yornPanel.add(this.newpoPanel);
/* 1215 */           this.yornPanel.add(Box.createRigidArea(new Dimension(20, 50)));
/* 1216 */           this.yornPanel.add(this.oldpoPanel);
/*      */           
/* 1218 */           this.centerPanel.removeAll();
/* 1219 */           this.centerPanel.add(this.yornPanel);
/* 1220 */           this.step = 7;
/* 1221 */           this.nextbutt.setEnabled(false);
/*      */         }
/*      */         
/* 1224 */         revalidate();
/* 1225 */         repaint();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1233 */       break;
/*      */     case 7: 
/*      */     default: 
/* 1230 */       System.out.println("Markov decision cannot next here.");
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void LoadFile(ObjectInputStream in)
/*      */   {
/* 1244 */     int[] para = new int[11];
/*      */     try
/*      */     {
/* 1247 */       this.state = ((IORState)in.readObject());
/* 1248 */       para = (int[])in.readObject();
/* 1249 */       this.opseq = ((Vector)in.readObject());
/* 1250 */       this.state.setSolverStepVector();
/* 1251 */       in.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1254 */       e1.printStackTrace();
/* 1255 */       System.out.println("Open fails");
/*      */     }
/*      */     
/* 1258 */     this.step = para[0];
/* 1259 */     this.itcnt = para[1];
/* 1260 */     this.selected_k = para[2];
/* 1261 */     this.current_i = para[3];
/* 1262 */     this.loc = para[4];
/* 1263 */     this.selected_table = para[5];
/* 1264 */     this.selected_row = para[6];
/* 1265 */     this.selected_col = para[7];
/* 1266 */     this.current_state = para[8];
/* 1267 */     this.current_eq = para[9];
/* 1268 */     this.current_pos = para[10];
/*      */     
/* 1270 */     if (this.selected_k > 1) {
/* 1271 */       this.decbutt.setEnabled(true);
/*      */     } else
/* 1273 */       this.decbutt.setEnabled(false);
/* 1274 */     if (this.selected_k < this.nDecision) {
/* 1275 */       this.incbutt.setEnabled(true);
/*      */     } else
/* 1277 */       this.incbutt.setEnabled(false);
/* 1278 */     this.prlabel.setText("Pij(k) , k=".concat(String.valueOf(String.valueOf(this.selected_k))));
/* 1279 */     this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt).append(")"))));
/* 1280 */     this.probModel.fireTableDataChanged();
/* 1281 */     this.backbutt.setEnabled(true);
/*      */     
/* 1283 */     if ((this.step >= 1) && (this.step <= 3)) {
/* 1284 */       for (int i = 0; i < this.current_i; i++) {
/* 1285 */         for (int j = 0; j < this.nState + 1; j++) {
/* 1286 */           this.grcolabel[i][j].setText(this.decfm.format(this.controller.data.grcoef[i][j]));
/* 1287 */           if (Math.abs(this.controller.data.grcoef[i][j] - 'ϧ') < 0.01D)
/* 1288 */             this.grcolabel[i][j].setText(" --- ");
/*      */         }
/*      */       }
/* 1291 */       for (int j = 0; j < this.loc; j++) {
/* 1292 */         this.grcolabel[this.current_i][j].setText(this.decfm.format(this.controller.data.grcoef[this.current_i][j]));
/* 1293 */         if (Math.abs(this.controller.data.grcoef[this.current_i][j] - 'ϧ') < 0.01D)
/* 1294 */           this.grcolabel[this.current_i][j].setText(" --- ");
/*      */       }
/* 1296 */       this.grcolabel[0][0].setBackground(getBackground());
/* 1297 */       this.grcolabel[this.current_i][this.loc].setBackground(Color.cyan);
/*      */     }
/* 1299 */     if ((this.step >= 2) && (this.step <= 3)) {
/* 1300 */       this.grcolabel[(this.nState - 1)][this.nState].setText(this.decfm.format(this.controller.data.grcoef[(this.nState - 1)][this.nState]));
/* 1301 */       if (Math.abs(this.controller.data.grcoef[(this.nState - 1)][this.nState] - 'ϧ') < 0.01D)
/* 1302 */         this.grcolabel[(this.nState - 1)][this.nState].setText(" --- ");
/* 1303 */       this.grcolabel[(this.nState - 1)][this.nState].setBackground(getBackground());
/* 1304 */       String vrs = " ";
/* 1305 */       for (int i = 0; i < this.nState - 1; i++) {
/* 1306 */         vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") ,"))))));
/*      */       }
/* 1308 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press the NEXT button to solve the set of equations displayed above for the unknown \nvalues : ").append(vrs).append(" and V").append(this.nState - 1).append("(R").append(this.itcnt).append(") \n"))));
/*      */       
/* 1310 */       this.nextbutt.setText("Next");
/*      */     }
/* 1312 */     if (this.step == 3) {
/* 1313 */       this.soluPanel.removeAll();
/* 1314 */       for (int i = 0; i < this.nState; i++) {
/* 1315 */         this.soluPanel.add(Box.createVerticalStrut(5));
/* 1316 */         this.soluPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(")  =  ").append(this.fltfm.format(this.controller.data.vr[i]))))));
/*      */       }
/*      */       
/* 1319 */       this.stendPanel.removeAll();
/* 1320 */       this.stendPanel.add(this.soluPanel);
/* 1321 */       this.stendPanel.add(Box.createRigidArea(new Dimension(80, 10)));
/* 1322 */       this.stendPanel.add(new JLabel("Press the NEXT button to begin Step 2 : Policy Improvement"));
/* 1323 */       this.centerPanel.removeAll();
/* 1324 */       this.centerPanel.add(this.vdlabel);
/* 1325 */       this.centerPanel.add(Box.createVerticalStrut(10));
/* 1326 */       this.centerPanel.add(this.stendPanel);
/*      */     }
/* 1328 */     if (this.step >= 4) {
/* 1329 */       if ((this.itcnt == 1) && (this.current_state == 0))
/*      */       {
/* 1331 */         BuildEquPanel();
/* 1332 */         this.topPanel.removeAll();
/* 1333 */         this.topPanel.add(this.equPanel);
/*      */         
/*      */ 
/* 1336 */         for (int i = 0; i < this.current_eq; i++) {
/* 1337 */           for (int j = 0; j < this.nState + 1; j++) {
/* 1338 */             this.exprcolabel[i][j].setText(this.decfm.format(this.controller.data.exprcoef[i][j]));
/* 1339 */             if (Math.abs(this.controller.data.exprcoef[i][j] - 'ϧ') < 0.01D)
/* 1340 */               this.exprcolabel[i][j].setText(" --- ");
/*      */           }
/* 1342 */           if (Math.abs(this.controller.data.expr[this.current_state][i]) > 10000.0D) {
/* 1343 */             this.exprcolabel[i][(this.nState + 1)].setText(this.expfm.format(this.controller.data.expr[this.current_state][i]));
/*      */           } else {
/* 1345 */             this.exprcolabel[i][(this.nState + 1)].setText(this.fltfm.format(this.controller.data.expr[this.current_state][i]));
/*      */           }
/* 1347 */           if (Math.abs(this.controller.data.expr[this.current_state][i] - 'ϧ') < 0.01D)
/* 1348 */             this.exprcolabel[i][(this.nState + 1)].setText(" --- ");
/*      */         }
/* 1350 */         for (int j = 0; j < this.current_pos; j++) {
/* 1351 */           this.exprcolabel[this.current_eq][j].setText(this.decfm.format(this.controller.data.exprcoef[this.current_eq][j]));
/* 1352 */           if (Math.abs(this.controller.data.exprcoef[this.current_eq][j] - 'ϧ') < 0.01D)
/* 1353 */             this.exprcolabel[this.current_eq][j].setText(" --- ");
/*      */         }
/* 1355 */         this.exprcolabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*      */         
/* 1357 */         this.vdlabel.setText("Step 2 : Policy Improvement    STATE = ".concat(String.valueOf(String.valueOf(this.current_state))));
/* 1358 */         String vrs = " ";
/* 1359 */         for (i = 0; i < this.nState; i++) {
/* 1360 */           if (i < this.nState - 1) {
/* 1361 */             vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.vr[i])).append(" , "))))));
/*      */           } else
/* 1363 */             vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("and  V").append(i).append("(R").append(this.itcnt).append(") = 0 ."))))));
/*      */         }
/* 1365 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k) Vj(R").append(this.itcnt).append(") , k=1, ...,").append(this.nDecision).append("\n").append("to be evaluated for i = ").append(this.current_state).append(" where \n").append(vrs).append("\n\n").append(this.histr))));
/*      */         
/*      */ 
/*      */ 
/* 1369 */         this.centerPanel.removeAll();
/* 1370 */         this.centerPanel.add(this.vdlabel);
/* 1371 */         this.centerPanel.add(Box.createVerticalStrut(10));
/* 1372 */         this.centerPanel.add(this.statetext);
/* 1373 */         this.nextbutt.setText("Enter");
/*      */       }
/*      */       else
/*      */       {
/* 1377 */         BuildEquPanel();
/* 1378 */         this.topPanel.removeAll();
/* 1379 */         this.topPanel.add(this.equPanel);
/*      */         
/* 1381 */         this.vdlabel.setText("Step 2 : Policy Improvement    STATE = ".concat(String.valueOf(String.valueOf(this.current_state))));
/* 1382 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press the NEXT button to have the computer evaluate the expression for state ").append(this.current_state).append("."))));
/* 1383 */         this.centerPanel.removeAll();
/* 1384 */         this.centerPanel.add(this.vdlabel);
/* 1385 */         this.centerPanel.add(Box.createVerticalStrut(10));
/* 1386 */         this.centerPanel.add(this.statetext);
/*      */       }
/*      */     }
/* 1389 */     if (this.step >= 5) {
/* 1390 */       for (int i = 0; i < this.nDecision; i++) {
/* 1391 */         for (int j = 0; j < this.nState + 1; j++) {
/* 1392 */           this.exprcolabel[i][j].setText(this.decfm.format(this.controller.data.exprcoef[i][j]));
/* 1393 */           if (Math.abs(this.controller.data.exprcoef[i][j] - 'ϧ') < 0.01D)
/* 1394 */             this.exprcolabel[i][j].setText(" --- ");
/*      */         }
/* 1396 */         if (Math.abs(this.controller.data.expr[this.current_state][i]) > 10000.0D) {
/* 1397 */           this.exprcolabel[i][(this.nState + 1)].setText(this.expfm.format(this.controller.data.expr[this.current_state][i]));
/*      */         } else {
/* 1399 */           this.exprcolabel[i][(this.nState + 1)].setText(this.fltfm.format(this.controller.data.expr[this.current_state][i]));
/*      */         }
/* 1401 */         if (Math.abs(this.controller.data.expr[this.current_state][i] - 'ϧ') < 0.01D) {
/* 1402 */           this.exprcolabel[i][(this.nState + 1)].setText(" --- ");
/*      */         }
/*      */       }
/* 1405 */       this.exprcolabel[(this.nDecision - 1)][this.nState].setBackground(getBackground());
/*      */       
/* 1407 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k) Vj(R").append(this.itcnt).append(") , k=1, ...,").append(this.nDecision).append("\n").append("evaluated for i = ").append(this.current_state).append(" and for each possible value of k.\n"))));
/*      */       
/*      */ 
/* 1410 */       this.newdesPanel.removeAll();
/* 1411 */       this.newdesPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Enter the value of d").append(this.current_state).append("(R").append(this.itcnt + 1).append(") : ")))));
/* 1412 */       this.newdesPanel.add(this.newdifd);
/* 1413 */       this.newdesPanel.setAlignmentX(0.0F);
/* 1414 */       this.centerPanel.removeAll();
/* 1415 */       this.centerPanel.add(this.vdlabel);
/* 1416 */       this.centerPanel.add(Box.createVerticalStrut(10));
/* 1417 */       this.centerPanel.add(this.statetext);
/* 1418 */       this.centerPanel.add(Box.createVerticalStrut(10));
/* 1419 */       this.centerPanel.add(this.newdesPanel);
/* 1420 */       this.nextbutt.setText("Next");
/*      */     }
/* 1422 */     if (this.step >= 6) {
/* 1423 */       this.oldpoPanel.removeAll();
/* 1424 */       this.newpoPanel.removeAll();
/*      */       
/* 1426 */       this.oldpoPanel.add(new JLabel("Old Policy"));
/* 1427 */       this.oldpoPanel.add(Box.createVerticalStrut(5));
/* 1428 */       this.newpoPanel.add(new JLabel("New Policy"));
/* 1429 */       this.newpoPanel.add(Box.createVerticalStrut(5));
/* 1430 */       for (int i = 0; i < this.nState; i++) {
/* 1431 */         this.oldpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt).append(") = ").append(this.decfm.format(this.controller.data.old_decision[i]))))));
/* 1432 */         this.newpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt + 1).append(") = ").append(this.decfm.format(this.controller.data.new_decision[i]))))));
/*      */       }
/* 1434 */       this.yornPanel.removeAll();
/* 1435 */       this.yornPanel.add(new JLabel("Is the current policy optimal?"));
/* 1436 */       this.yornPanel.add(this.yorn);
/* 1437 */       this.yornPanel.add(Box.createRigidArea(new Dimension(30, 50)));
/* 1438 */       this.yornPanel.add(this.oldpoPanel);
/* 1439 */       this.yornPanel.add(Box.createRigidArea(new Dimension(10, 50)));
/* 1440 */       this.yornPanel.add(this.newpoPanel);
/*      */       
/* 1442 */       this.centerPanel.removeAll();
/* 1443 */       this.centerPanel.add(this.yornPanel);
/*      */     }
/* 1445 */     if (this.step == 7) {
/* 1446 */       this.oldpoPanel.removeAll();
/* 1447 */       this.newpoPanel.removeAll();
/*      */       
/* 1449 */       for (int i = 0; i < this.nState; i++) {
/* 1450 */         this.newpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt).append(") = ").append(this.decfm.format(this.controller.data.new_decision[i]))))));
/* 1451 */         this.oldpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.vr[i]))))));
/*      */       }
/*      */       
/* 1454 */       this.yornPanel.removeAll();
/* 1455 */       this.yornPanel.add(new JLabel("Optimal policy : "));
/* 1456 */       this.yornPanel.add(Box.createRigidArea(new Dimension(20, 50)));
/* 1457 */       this.yornPanel.add(this.newpoPanel);
/* 1458 */       this.yornPanel.add(Box.createRigidArea(new Dimension(20, 50)));
/* 1459 */       this.yornPanel.add(this.oldpoPanel);
/*      */       
/* 1461 */       this.centerPanel.removeAll();
/* 1462 */       this.centerPanel.add(this.yornPanel);
/* 1463 */       this.nextbutt.setEnabled(false);
/*      */     }
/* 1465 */     revalidate();
/* 1466 */     repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFile(ObjectOutputStream out)
/*      */   {
/* 1475 */     int[] interpara = new int[11];
/*      */     
/* 1477 */     interpara[0] = this.step;
/* 1478 */     interpara[1] = this.itcnt;
/* 1479 */     interpara[2] = this.selected_k;
/* 1480 */     interpara[3] = this.current_i;
/* 1481 */     interpara[4] = this.loc;
/* 1482 */     interpara[5] = this.selected_table;
/* 1483 */     interpara[6] = this.selected_row;
/* 1484 */     interpara[7] = this.selected_col;
/* 1485 */     interpara[8] = this.current_state;
/* 1486 */     interpara[9] = this.current_eq;
/* 1487 */     interpara[10] = this.current_pos;
/*      */     try
/*      */     {
/* 1490 */       out.writeObject(this.state);
/* 1491 */       out.writeObject(interpara);
/* 1492 */       out.writeObject(this.opseq);
/* 1493 */       out.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1496 */       e1.printStackTrace();
/* 1497 */       System.out.println("Save fails");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void addSelectionListener(JTable table)
/*      */   {
/* 1505 */     String tablename = table.getColumnName(0);
/* 1506 */     JTable tmptable = table;
/*      */     
/* 1508 */     ListSelectionModel rowSM = table.getSelectionModel();
/*      */     
/* 1510 */     rowSM.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*      */       private final String val$tablename;
/*      */       private final JTable val$tmptable;
/*      */       
/* 1514 */       public void valueChanged(ListSelectionEvent e) { ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/*      */         
/* 1516 */         if (!lsm.isSelectionEmpty())
/*      */         {
/*      */ 
/* 1519 */           if (this.val$tablename == "i \\ k") {
/* 1520 */             IORMKDiscountPanel.this.selected_table = 0;
/*      */           } else {
/* 1522 */             IORMKDiscountPanel.this.selected_table = IORMKDiscountPanel.this.selected_k;
/*      */           }
/* 1524 */           int selrow = this.val$tmptable.getSelectedRow();
/* 1525 */           int selcol = this.val$tmptable.getSelectedColumn();
/* 1526 */           if (selcol == 0) {
/* 1527 */             lsm.clearSelection();
/*      */           }
/* 1529 */           else if ((selrow >= 0) && (selcol >= 1)) {
/* 1530 */             IORMKDiscountPanel.this.selected_row = selrow;
/* 1531 */             IORMKDiscountPanel.this.selected_col = selcol;
/*      */           }
/*      */           
/*      */         }
/*      */       }
/* 1536 */     });
/* 1537 */     ListSelectionModel colSM = table.getColumnModel().getSelectionModel();
/*      */     
/* 1539 */     colSM.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
/*      */       private final String val$tablename;
/*      */       private final JTable val$tmptable;
/*      */       
/* 1543 */       public void valueChanged(ListSelectionEvent e) { ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/*      */         
/* 1545 */         if (!lsm.isSelectionEmpty())
/*      */         {
/*      */ 
/* 1548 */           if (this.val$tablename == "i \\ k") {
/* 1549 */             IORMKDiscountPanel.this.selected_table = 0;
/*      */           } else {
/* 1551 */             IORMKDiscountPanel.this.selected_table = IORMKDiscountPanel.this.selected_k;
/*      */           }
/* 1553 */           int selrow = this.val$tmptable.getSelectedRow();
/* 1554 */           int selcol = this.val$tmptable.getSelectedColumn();
/* 1555 */           if (selcol == 0) {
/* 1556 */             lsm.clearSelection();
/*      */           }
/* 1558 */           else if ((selrow >= 0) && (selcol >= 1)) {
/* 1559 */             IORMKDiscountPanel.this.selected_row = selrow;
/* 1560 */             IORMKDiscountPanel.this.selected_col = selcol;
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   class IORCostTableModel extends AbstractTableModel
/*      */   {
/*      */     IORCostTableModel() {}
/*      */     
/*      */     public int getColumnCount() {
/* 1572 */       return IORMKDiscountPanel.this.controller.data.num_decisions + 1;
/*      */     }
/*      */     
/*      */     public int getRowCount()
/*      */     {
/* 1577 */       return IORMKDiscountPanel.this.controller.data.num_states;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col)
/*      */     {
/* 1582 */       if (col == 0) {
/* 1583 */         return "i \\ k";
/*      */       }
/* 1585 */       return "   ".concat(String.valueOf(String.valueOf(col)));
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col) {
/* 1589 */       String str = new String();
/* 1590 */       DecimalFormat dfm = new DecimalFormat("#.##");
/* 1591 */       if (col == 0) {
/* 1592 */         return "    ".concat(String.valueOf(String.valueOf(row)));
/*      */       }
/* 1594 */       if (Math.abs(IORMKDiscountPanel.this.controller.data.cost[row][(col - 1)] - 'ϧ') < 0.01D) {
/* 1595 */         return " --- ";
/*      */       }
/* 1597 */       return dfm.format(IORMKDiscountPanel.this.controller.data.cost[row][(col - 1)]);
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c)
/*      */     {
/* 1602 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1608 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   class IORProbTableModel
/*      */     extends AbstractTableModel
/*      */   {
/*      */     IORProbTableModel() {}
/*      */     
/*      */     public int getColumnCount()
/*      */     {
/* 1620 */       return IORMKDiscountPanel.this.controller.data.num_states + 1;
/*      */     }
/*      */     
/*      */     public int getRowCount()
/*      */     {
/* 1625 */       return IORMKDiscountPanel.this.controller.data.num_states;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col)
/*      */     {
/* 1630 */       if (col == 0) {
/* 1631 */         return "i \\ j";
/*      */       }
/* 1633 */       return "   ".concat(String.valueOf(String.valueOf(col - 1)));
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col) {
/* 1637 */       String str = new String();
/* 1638 */       DecimalFormat dfm = new DecimalFormat("#.###");
/* 1639 */       if (col == 0) {
/* 1640 */         return "    ".concat(String.valueOf(String.valueOf(row)));
/*      */       }
/* 1642 */       return dfm.format(IORMKDiscountPanel.this.controller.data.p[(IORMKDiscountPanel.this.selected_k - 1)][row][(col - 1)]);
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c) {
/* 1646 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1652 */     public boolean isCellEditable(int row, int col) { return false; }
/*      */   }
/*      */   
/*      */   class IORDesTableModel extends AbstractTableModel {
/*      */     IORDesTableModel() {}
/*      */     
/* 1658 */     private int[] des = new int[4];
/*      */     
/*      */     public int getColumnCount() {
/* 1661 */       return 2;
/*      */     }
/*      */     
/*      */     public int getRowCount() {
/* 1665 */       return IORMKDiscountPanel.this.controller.data.num_states;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col)
/*      */     {
/* 1670 */       if (col == 0) {
/* 1671 */         return "i";
/*      */       }
/* 1673 */       return "di";
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col) {
/* 1677 */       String str = new String();
/* 1678 */       if (col == 0) {
/* 1679 */         return "    ".concat(String.valueOf(String.valueOf(row)));
/*      */       }
/* 1681 */       return " ".concat(String.valueOf(String.valueOf(IORMKDiscountPanel.this.controller.data.old_decision[row])));
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c) {
/* 1685 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1691 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/* 1699 */     String str = "Interactive Policy Improvement Algorithm-Discounted Cost\n\n";
/* 1700 */     str = String.valueOf(String.valueOf(str)).concat("This algorithm is used to determine an optimal policy for a Markov decision ");
/* 1701 */     str = String.valueOf(String.valueOf(str)).concat("process under the criterion of minimizing expected total discounted cost per ");
/* 1702 */     str = String.valueOf(String.valueOf(str)).concat("unit time. There are two major steps: (1) value determination and (2) policy ");
/* 1703 */     str = String.valueOf(String.valueOf(str)).concat("improvement, performed in each iteration until an optimal solution is found.\n\n");
/* 1704 */     str = String.valueOf(String.valueOf(str)).concat("Choosing the coefficients in the value determination phase\n\n");
/* 1705 */     str = String.valueOf(String.valueOf(str)).concat("In the value determination phase, a set of equations are solved to determine the ");
/* 1706 */     str = String.valueOf(String.valueOf(str)).concat("V(i). Your job is to enter the coefficients of the V(i) in these equations.");
/* 1707 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 1708 */     this.help_content_step.setText(str);
/* 1709 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/* 1716 */     String str = "Interactive Policy Improvement Algorithm-Discounted Cost\n\n";
/* 1717 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to interactively execute the discounted cost policy improvement ");
/* 1718 */     str = String.valueOf(String.valueOf(str)).concat("algorithm, presented in Section 5 of the \"Markov Decision Processes\" chapter.\n\n");
/* 1719 */     str = String.valueOf(String.valueOf(str)).concat("Your role is to apply the logic of the algorithm, and the computer will do the number ");
/* 1720 */     str = String.valueOf(String.valueOf(str)).concat("crunching. The computer also will inform you if you make a mistake on the first iteration.\n\n");
/* 1721 */     str = String.valueOf(String.valueOf(str)).concat("When you finish a problem, you can print out all your work by choosing Print under the File ");
/* 1722 */     str = String.valueOf(String.valueOf(str)).concat("menu. If you are interrupted before you finish, you can save your work (choose Save in the ");
/* 1723 */     str = String.valueOf(String.valueOf(str)).concat("File menu) and return later (choose Open).");
/* 1724 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 1725 */     this.help_content_procedure.setText(str);
/* 1726 */     this.help_content_procedure.revalidate(); }
/*      */   
/* 1728 */   private String str0 = "Interactive Policy Improvement Algorithm-Discounted Cost\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1733 */   private String str1 = "Choosing the coefficients in the value determination phase\n\n";
/*      */   
/*      */ 
/* 1736 */   private String str2 = "To continue\n\n";
/*      */   
/*      */ 
/* 1739 */   private String str3 = "To continue\n\n";
/*      */   
/*      */ 
/* 1742 */   private String str4 = " Choosing the coefficients in the policy improvement phase\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1752 */   private String str41 = "Press the NEXT button to have computer automatically evaluate the expressions for this state.";
/* 1753 */   private String str5 = "Entering the new decision for a given state\n\n";
/*      */   
/*      */ 
/*      */ 
/* 1757 */   private String str6 = "Selecting whether or not the current policy is optimal\n\n";
/*      */   
/*      */ 
/* 1760 */   private String str7 = "The solution\n\nTo print the results: select Print to File from the File menu.";
/*      */   
/*      */ 
/*      */ 
/*      */   protected void updateStepHelpPanel()
/*      */   {
/* 1766 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/* 1767 */     switch (this.step) {
/*      */     case 1: 
/* 1769 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str1).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1798 */       break;
/*      */     case 2: 
/* 1772 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str2).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1798 */       break;
/*      */     case 3: 
/* 1775 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str3).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1798 */       break;
/*      */     case 4: 
/* 1778 */       if ((this.itcnt == 1) && (this.current_state == 0)) {
/* 1779 */         this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str4).append(str))));
/*      */       }
/*      */       else {
/* 1782 */         this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str41).append(str))));
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
/* 1798 */       break;
/*      */     case 5: 
/* 1786 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str5).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1798 */       break;
/*      */     case 6: 
/* 1789 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str6).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1798 */       break;
/*      */     case 7: 
/* 1792 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str7).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1798 */       break;
/*      */     default: 
/* 1795 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORMKDiscountPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */