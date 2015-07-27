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
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.table.AbstractTableModel;
/*      */ import javax.swing.table.TableColumnModel;
/*      */ 
/*      */ public class IORMKDAveragePanel extends IORActionPanel
/*      */ {
/*   31 */   private char SIGMA = '∑';
/*   32 */   private char ALPHA = 'α';
/*      */   
/*   34 */   private IORMDProcessController controller = null;
/*      */   
/*   36 */   private JPanel mainPanel = new JPanel();
/*      */   
/*   38 */   private JPanel topPanel = null;
/*   39 */   private JPanel funcPanel = null;
/*   40 */   private JPanel equPanel = null;
/*   41 */   private JPanel centerPanel = null;
/*   42 */   private JPanel bottomPanel = null;
/*   43 */   private JPanel tablePanel = new JPanel();
/*   44 */   private JPanel swtPanel = new JPanel();
/*   45 */   private JPanel transtPanel = new JPanel();
/*   46 */   private JPanel buttPanel = new JPanel();
/*   47 */   private JPanel cikPanel = new JPanel();
/*   48 */   private JPanel pijPanel = new JPanel();
/*   49 */   private JPanel diPanel = new JPanel();
/*   50 */   private JPanel stendPanel = new JPanel();
/*   51 */   private JPanel newdesPanel = new JPanel();
/*   52 */   private JPanel oldpoPanel = null;
/*   53 */   private JPanel newpoPanel = null;
/*   54 */   private JPanel soluPanel = null;
/*   55 */   private JPanel yornPanel = new JPanel();
/*      */   
/*   57 */   private JScrollPane costPane = null;
/*   58 */   private JScrollPane probPane = null;
/*   59 */   private JScrollPane desPane = null;
/*      */   
/*   61 */   private IORMKDAveragePanel.IORCostTableModel costModel = null;
/*      */   private JTable costTable;
/*   63 */   private IORMKDAveragePanel.IORProbTableModel probModel = null;
/*      */   private JTable probTable;
/*   65 */   private IORMKDAveragePanel.IORDesTableModel desModel = null;
/*      */   private JTable desTable;
/*      */   private JPanel[] grPanel;
/*      */   private JPanel[] exprPanel;
/*      */   private JTextField[][] grcolabel;
/*      */   private JTextField[][] exprcolabel;
/*   71 */   private JButton backbutt = new JButton("Back");
/*   72 */   private JButton nextbutt = new JButton("Next");
/*   73 */   private JButton enterbutt = new JButton("Enter");
/*      */   
/*   75 */   private JButton incbutt = new JButton("+");
/*   76 */   private JButton decbutt = new JButton("-");
/*   77 */   private JComboBox yorn = new JComboBox();
/*      */   
/*   79 */   private JLabel vdlabel = new JLabel();
/*   80 */   private JLabel ctlabel = new JLabel("Cik");
/*   81 */   private JLabel prlabel = new JLabel(); private JLabel deslabel = new JLabel();
/*   82 */   private JTextArea statetext = new JTextArea();
/*      */   
/*   84 */   private int nState = 4; private int nDecision = 5;
/*   85 */   private DecimalFormat decfm = new DecimalFormat();
/*   86 */   private DecimalFormat fltfm = new DecimalFormat();
/*   87 */   private DecimalFormat expfm = new DecimalFormat("##.#E0");
/*      */   
/*   89 */   private WholeNumberField newdifd = new WholeNumberField(1, 2);
/*      */   
/*   91 */   private int itcnt = 1;
/*   92 */   private int step = 1;
/*      */   private int selected_k;
/*      */   private int current_i;
/*      */   private int loc;
/*      */   private int selected_table;
/*   97 */   private int selected_row; private int selected_col; private int current_state; private int current_eq; private int current_pos; private Vector opseq = new Vector();
/*      */   
/*   99 */   private String histr = "For the highlighted coefficient above , choose the appropriate value from the \ntables below and then press the Enter button. To display the transition matrix (Pij)\nfor a different k , press the \"+\" or \"-\" button. Press the Back button to backtrack.";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IORMKDAveragePanel(IORMDProcessController c)
/*      */   {
/*  110 */     super(c);
/*  111 */     this.controller = c;
/*  112 */     this.state = new IORState(this.controller.solver);
/*  113 */     add(this.mainPanel);
/*  114 */     this.bn_back.setVisible(false);
/*  115 */     this.bn_finish.setVisible(false);
/*  116 */     this.actionStatus.setVisible(false);
/*      */     
/*  118 */     this.nState = this.controller.data.num_states;
/*  119 */     this.nDecision = this.controller.data.num_decisions;
/*      */     
/*  121 */     this.selected_k = 1;
/*  122 */     this.decfm.setMaximumFractionDigits(3);
/*  123 */     this.fltfm.setMaximumFractionDigits(1);
/*  124 */     this.decfm.setGroupingUsed(false);
/*  125 */     this.fltfm.setGroupingUsed(false);
/*  126 */     this.expfm.setGroupingUsed(false);
/*      */     
/*  128 */     this.grPanel = new JPanel[this.nState];
/*  129 */     this.exprPanel = new JPanel[this.nDecision];
/*  130 */     this.grcolabel = new JTextField[this.nState][this.nState + 1];
/*  131 */     this.exprcolabel = new JTextField[this.nDecision][this.nState + 2];
/*      */     
/*  133 */     this.statetext.setEditable(false);
/*  134 */     this.statetext.setBackground(getBackground());
/*  135 */     this.statetext.setAlignmentX(0.0F);
/*      */     
/*      */ 
/*  138 */     for (int i = 0; i < this.nState; i++) {
/*  139 */       this.grPanel[i] = new JPanel();
/*  140 */       this.grcolabel[i][0] = new JTextField(5);
/*  141 */       this.grcolabel[i][0].setEditable(false);
/*  142 */       this.grcolabel[i][0].setBackground(getBackground());
/*  143 */       for (int j = 1; j < this.nState + 1; j++) {
/*  144 */         this.grcolabel[i][j] = new JTextField(4);
/*  145 */         this.grcolabel[i][j].setEditable(false);
/*  146 */         this.grcolabel[i][j].setBackground(getBackground());
/*      */       }
/*      */     }
/*  149 */     for (i = 0; i < this.nDecision; i++) {
/*  150 */       this.exprPanel[i] = new JPanel();
/*  151 */       this.exprcolabel[i][0] = new JTextField(5);
/*  152 */       this.exprcolabel[i][0].setEditable(false);
/*  153 */       this.exprcolabel[i][0].setBackground(getBackground());
/*  154 */       for (int j = 1; j < this.nState + 1; j++) {
/*  155 */         this.exprcolabel[i][j] = new JTextField(4);
/*  156 */         this.exprcolabel[i][j].setEditable(false);
/*  157 */         this.exprcolabel[i][j].setBackground(getBackground());
/*      */       }
/*  159 */       this.exprcolabel[i][(this.nState + 1)] = new JTextField(5);
/*  160 */       this.exprcolabel[i][(this.nState + 1)].setEditable(false);
/*  161 */       this.exprcolabel[i][(this.nState + 1)].setBackground(getBackground());
/*      */     }
/*      */     
/*  164 */     this.nextbutt.setText("Enter");
/*  165 */     this.backbutt.setEnabled(false);
/*  166 */     this.buttPanel.add(this.backbutt);
/*  167 */     this.buttPanel.add(Box.createHorizontalStrut(20));
/*  168 */     this.buttPanel.add(this.nextbutt);
/*      */     
/*  170 */     this.decbutt.setEnabled(false);
/*  171 */     this.decbutt.setMargin(new Insets(0, 5, 0, 5));
/*  172 */     this.incbutt.setMargin(new Insets(0, 5, 0, 5));
/*  173 */     this.swtPanel.setLayout(new BoxLayout(this.swtPanel, 1));
/*  174 */     this.swtPanel.add(Box.createRigidArea(new Dimension(0, 12)));
/*  175 */     this.swtPanel.add(this.decbutt);
/*  176 */     this.swtPanel.add(new JLabel(" k "));
/*  177 */     this.swtPanel.add(this.incbutt);
/*      */     
/*  179 */     this.yorn.addItem("No");
/*  180 */     this.yorn.addItem("Yes");
/*      */     
/*  182 */     this.topPanel = new JPanel();
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
/*  211 */     this.costModel = new IORMKDAveragePanel.IORCostTableModel();
/*  212 */     this.costTable = new JTable(this.costModel);
/*  213 */     this.costTable.setCellSelectionEnabled(true);
/*  214 */     this.costTable.setSelectionBackground(Color.cyan);
/*  215 */     this.costTable.setSelectionMode(0);
/*  216 */     this.costTable.setRowHeight(20);
/*  217 */     this.costPane = new JScrollPane(this.costTable);
/*  218 */     this.costPane.setAlignmentX(0.5F);
/*      */     
/*  220 */     this.probModel = new IORMKDAveragePanel.IORProbTableModel();
/*  221 */     this.probTable = new JTable(this.probModel);
/*  222 */     this.probTable.setCellSelectionEnabled(true);
/*  223 */     this.probTable.setSelectionMode(0);
/*  224 */     this.probTable.setSelectionBackground(Color.cyan);
/*  225 */     this.probTable.setRowHeight(20);
/*  226 */     this.probPane = new JScrollPane(this.probTable);
/*  227 */     this.probPane.setAlignmentX(0.5F);
/*      */     
/*  229 */     this.desModel = new IORMKDAveragePanel.IORDesTableModel();
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
/*  252 */     this.oldpoPanel = new JPanel();
/*  253 */     this.oldpoPanel.setLayout(new BoxLayout(this.oldpoPanel, 1));
/*      */     
/*  255 */     this.newpoPanel = new JPanel();
/*  256 */     this.newpoPanel.setLayout(new BoxLayout(this.newpoPanel, 1));
/*      */     
/*  258 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*  259 */     this.mainPanel.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
/*      */     
/*  261 */     InitialPanel();
/*      */     
/*      */ 
/*  264 */     this.nextbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  266 */         IORMKDAveragePanel.this.doNext();
/*      */       }
/*  268 */     });
/*  269 */     this.backbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  271 */         IORMKDAveragePanel.this.doBack();
/*      */       }
/*      */     });
/*      */     
/*  275 */     if (this.selected_k > 1) {
/*  276 */       this.decbutt.setEnabled(true);
/*      */     } else
/*  278 */       this.decbutt.setEnabled(false);
/*  279 */     if (this.selected_k < this.nDecision) {
/*  280 */       this.incbutt.setEnabled(true);
/*      */     } else {
/*  282 */       this.incbutt.setEnabled(false);
/*      */     }
/*  284 */     this.incbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  286 */         if (IORMKDAveragePanel.this.selected_k < IORMKDAveragePanel.this.nDecision) {
/*  287 */           IORMKDAveragePanel.access$4(IORMKDAveragePanel.this);
/*  288 */           IORMKDAveragePanel.this.decbutt.setEnabled(true);
/*  289 */           if (IORMKDAveragePanel.this.selected_k >= IORMKDAveragePanel.this.nDecision)
/*  290 */             IORMKDAveragePanel.this.incbutt.setEnabled(false);
/*      */         }
/*  292 */         IORMKDAveragePanel.this.probModel.fireTableDataChanged();
/*  293 */         IORMKDAveragePanel.this.probTable.getSelectionModel().clearSelection();
/*  294 */         IORMKDAveragePanel.this.probTable.getColumnModel().getSelectionModel().clearSelection();
/*  295 */         IORMKDAveragePanel.this.prlabel.setText("Pij(k) , k=".concat(String.valueOf(String.valueOf(IORMKDAveragePanel.this.selected_k))));
/*  296 */         IORMKDAveragePanel.this.pijPanel.revalidate();
/*  297 */         IORMKDAveragePanel.this.pijPanel.repaint();
/*      */       }
/*      */       
/*  300 */     });
/*  301 */     this.decbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  303 */         if (IORMKDAveragePanel.this.selected_k > 1) {
/*  304 */           IORMKDAveragePanel.access$11(IORMKDAveragePanel.this);
/*  305 */           IORMKDAveragePanel.this.incbutt.setEnabled(true);
/*  306 */           if (IORMKDAveragePanel.this.selected_k == 1)
/*  307 */             IORMKDAveragePanel.this.decbutt.setEnabled(false);
/*      */         }
/*  309 */         IORMKDAveragePanel.this.probModel.fireTableDataChanged();
/*  310 */         IORMKDAveragePanel.this.probTable.getSelectionModel().clearSelection();
/*  311 */         IORMKDAveragePanel.this.probTable.getColumnModel().getSelectionModel().clearSelection();
/*  312 */         IORMKDAveragePanel.this.prlabel.setText("Pij(k) , k=".concat(String.valueOf(String.valueOf(IORMKDAveragePanel.this.selected_k))));
/*  313 */         IORMKDAveragePanel.this.pijPanel.revalidate();
/*  314 */         IORMKDAveragePanel.this.pijPanel.repaint();
/*      */       }
/*      */       
/*  317 */     });
/*  318 */     this.costTable.addFocusListener(new java.awt.event.FocusListener() {
/*      */       public void focusLost(FocusEvent e) {
/*  320 */         IORMKDAveragePanel.this.costTable.getSelectionModel().clearSelection();
/*  321 */         IORMKDAveragePanel.this.costTable.getColumnModel().getSelectionModel().clearSelection();
/*      */       }
/*      */       
/*      */ 
/*      */       public void focusGained(FocusEvent e) {}
/*  326 */     });
/*  327 */     this.probTable.addFocusListener(new java.awt.event.FocusListener() {
/*      */       public void focusLost(FocusEvent e) {
/*  329 */         IORMKDAveragePanel.this.probTable.getSelectionModel().clearSelection();
/*  330 */         IORMKDAveragePanel.this.probTable.getColumnModel().getSelectionModel().clearSelection();
/*      */       }
/*      */       
/*      */ 
/*      */       public void focusGained(FocusEvent e) {}
/*      */     });
/*      */   }
/*      */   
/*      */   private void InitialPanel()
/*      */   {
/*  340 */     this.vdlabel.setText("Step 1 :  Value Determination. ");
/*  341 */     this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the equations : g(R").append(this.itcnt).append(") = Cik + ").append(this.SIGMA).append("j Pij(k)Vj(R").append(this.itcnt).append(") - Vi(R").append(this.itcnt).append(") , i=0,...,").append(this.nState - 1).append("\n").append("to be solved for g(R").append(this.itcnt).append(") and the Vi(R").append(this.itcnt).append(") \n\n").append(this.histr))));
/*      */     
/*      */ 
/*  344 */     this.centerPanel.add(this.vdlabel);
/*  345 */     this.centerPanel.add(Box.createVerticalStrut(10));
/*  346 */     this.centerPanel.add(this.statetext);
/*      */     
/*  348 */     BuildFuncPanel();
/*  349 */     BuildBottomPanel();
/*      */     
/*  351 */     this.topPanel.add(this.funcPanel);
/*      */     
/*  353 */     this.mainPanel.add(this.topPanel);
/*  354 */     this.mainPanel.add(Box.createVerticalStrut(10));
/*  355 */     this.mainPanel.add(this.centerPanel);
/*  356 */     this.mainPanel.add(Box.createVerticalStrut(10));
/*  357 */     this.mainPanel.add(this.bottomPanel);
/*  358 */     this.mainPanel.add(Box.createVerticalStrut(10));
/*  359 */     this.mainPanel.add(this.buttPanel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void BuildFuncPanel()
/*      */   {
/*  366 */     this.funcPanel.removeAll();
/*      */     
/*  368 */     for (int i = 0; i < this.nState; i++) {
/*  369 */       this.grPanel[i].removeAll();
/*  370 */       this.grPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("g(R").append(this.itcnt).append(") = ")))));
/*  371 */       this.grcolabel[i][0].setText(this.decfm.format(this.controller.data.grcoef[i][0]));
/*  372 */       if (Math.abs(this.controller.data.grcoef[i][0] - 'ϧ') < 0.01D)
/*  373 */         this.grcolabel[i][0].setText(" --- ");
/*  374 */       this.grPanel[i].add(this.grcolabel[i][0]);
/*  375 */       for (int j = 1; j < this.nState + 1; j++) {
/*  376 */         this.grPanel[i].add(new JLabel(" + "));
/*  377 */         this.grcolabel[i][j].setText(this.decfm.format(this.controller.data.grcoef[i][j]));
/*  378 */         this.grPanel[i].add(this.grcolabel[i][j]);
/*  379 */         this.grPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(j - 1).append("(R").append(this.itcnt).append(")")))));
/*      */       }
/*  381 */       this.grPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" -  V").append(i).append("(R").append(this.itcnt).append(")")))));
/*  382 */       this.grPanel[i].add(Box.createHorizontalStrut(30));
/*  383 */       this.grPanel[i].add(new JLabel(" i = ".concat(String.valueOf(String.valueOf(i)))));
/*      */       
/*  385 */       this.grcolabel[0][0].setBackground(Color.cyan);
/*      */       
/*  387 */       this.loc = 0;
/*  388 */       this.current_i = 0;
/*      */       
/*  390 */       this.funcPanel.add(this.grPanel[i]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildEquPanel()
/*      */   {
/*  399 */     this.equPanel.removeAll();
/*      */     
/*  401 */     for (int i = 0; i < this.nDecision; i++) {
/*  402 */       this.exprPanel[i].removeAll();
/*  403 */       this.exprcolabel[i][0].setText(this.decfm.format(this.controller.data.exprcoef[i][0]));
/*  404 */       if (Math.abs(this.controller.data.exprcoef[i][0] - 'ϧ') < 0.01D)
/*  405 */         this.exprcolabel[i][0].setText(" --- ");
/*  406 */       this.exprPanel[i].add(this.exprcolabel[i][0]);
/*  407 */       for (int j = 1; j < this.nState + 1; j++) {
/*  408 */         this.exprPanel[i].add(new JLabel(" + "));
/*  409 */         this.exprcolabel[i][j].setText(this.decfm.format(this.controller.data.exprcoef[i][j]));
/*  410 */         this.exprPanel[i].add(this.exprcolabel[i][j]);
/*  411 */         if (Math.abs(this.controller.data.vr[(j - 1)]) > 10000.0D) {
/*  412 */           this.exprPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" (").append(this.expfm.format(this.controller.data.vr[(j - 1)])).append(")")))));
/*      */         } else
/*  414 */           this.exprPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" (").append(this.fltfm.format(this.controller.data.vr[(j - 1)])).append(")")))));
/*      */       }
/*  416 */       if (Math.abs(this.controller.data.vr[this.current_state]) > 10000.0D) {
/*  417 */         this.exprPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" - (").append(this.expfm.format(this.controller.data.vr[this.current_state])).append(") =  ")))));
/*      */       } else {
/*  419 */         this.exprPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" - (").append(this.fltfm.format(this.controller.data.vr[this.current_state])).append(") =  ")))));
/*      */       }
/*  421 */       if (Math.abs(this.controller.data.expr[this.current_state][i]) > 10000.0D) {
/*  422 */         this.exprcolabel[i][(this.nState + 1)].setText(this.expfm.format(this.controller.data.expr[this.current_state][i]));
/*      */       } else {
/*  424 */         this.exprcolabel[i][(this.nState + 1)].setText(this.fltfm.format(this.controller.data.expr[this.current_state][i]));
/*      */       }
/*  426 */       if (Math.abs(this.controller.data.expr[this.current_state][i] - 'ϧ') < 0.01D)
/*  427 */         this.exprcolabel[i][(this.nState + 1)].setText(" --- ");
/*  428 */       this.exprPanel[i].add(this.exprcolabel[i][(this.nState + 1)]);
/*  429 */       this.exprPanel[i].add(Box.createHorizontalStrut(30));
/*  430 */       this.exprPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("k = ").append(i + 1)))));
/*      */       
/*  432 */       this.equPanel.add(this.exprPanel[i]);
/*      */     }
/*      */   }
/*      */   
/*      */   private void BuildBottomPanel()
/*      */   {
/*  438 */     this.prlabel.setText("Pij(k) , k=".concat(String.valueOf(String.valueOf(this.selected_k))));
/*  439 */     this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt).append(")"))));
/*      */     
/*  441 */     this.cikPanel.add(this.ctlabel);
/*  442 */     this.cikPanel.add(this.costPane);
/*  443 */     this.pijPanel.add(this.prlabel);
/*  444 */     this.pijPanel.add(this.probPane);
/*  445 */     this.transtPanel.add(this.pijPanel);
/*  446 */     this.transtPanel.add(this.swtPanel);
/*  447 */     this.diPanel.add(this.deslabel);
/*  448 */     this.diPanel.add(this.desPane);
/*      */     
/*  450 */     this.tablePanel.add(this.cikPanel);
/*  451 */     this.tablePanel.add(Box.createHorizontalStrut(20));
/*  452 */     this.tablePanel.add(this.transtPanel);
/*  453 */     this.tablePanel.add(Box.createHorizontalStrut(20));
/*  454 */     this.tablePanel.add(this.diPanel);
/*  455 */     this.bottomPanel.add(this.tablePanel);
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
/*  482 */     int[] match = new int[5];
/*      */     
/*  484 */     Vector par = new Vector();
/*  485 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*  487 */     match[0] = this.current_i;
/*  488 */     match[1] = this.loc;
/*  489 */     match[2] = this.selected_table;
/*  490 */     match[3] = this.selected_row;
/*  491 */     match[4] = (this.selected_col - 1);
/*      */     
/*  493 */     if (this.selected_col == 0) {
/*  494 */       JOptionPane.showMessageDialog(this, "this column cannot be selected");
/*  495 */       return;
/*      */     }
/*      */     
/*  498 */     par.addElement(match);
/*      */     
/*  500 */     opr.parameters = par;
/*  501 */     opr.operation_code = 40201;
/*  502 */     if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  503 */       String err = this.controller.solver.getErrInfo();
/*  504 */       JOptionPane.showMessageDialog(this, err);
/*  505 */       return;
/*      */     }
/*      */     
/*      */ 
/*  509 */     this.state.addStep(opr);
/*      */     
/*  511 */     this.opseq.addElement(new Integer(this.step));
/*      */     
/*      */ 
/*  514 */     this.grcolabel[this.current_i][this.loc].setText(this.decfm.format(this.controller.data.grcoef[this.current_i][this.loc]));
/*  515 */     if (Math.abs(this.controller.data.grcoef[this.current_i][this.loc] - 'ϧ') < 0.01D)
/*  516 */       this.grcolabel[this.current_i][this.loc].setText(" --- ");
/*  517 */     if ((this.current_i == this.nState - 1) && (this.loc == this.nState)) {
/*  518 */       this.grcolabel[(this.nState - 1)][this.nState].setBackground(getBackground());
/*  519 */       String vrs = " ";
/*  520 */       for (int i = 0; i < this.nState - 1; i++) {
/*  521 */         vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") ,"))))));
/*      */       }
/*  523 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press the NEXT button to solve the set of equations displayed above for the unknown \nvalues : g(R").append(this.itcnt).append(") and ").append(vrs).append(" with V").append(this.nState - 1).append("(R").append(this.itcnt).append(") set equal to zero."))));
/*      */       
/*      */ 
/*  526 */       this.nextbutt.setText("Next");
/*  527 */       this.step = 2;
/*      */     }
/*  529 */     else if (this.loc >= this.nState) {
/*  530 */       if (this.current_i < this.nState - 1) {
/*  531 */         this.current_i += 1;
/*  532 */         this.loc = 0;
/*  533 */         this.grcolabel[(this.current_i - 1)][this.nState].setBackground(getBackground());
/*  534 */         this.grcolabel[this.current_i][0].setBackground(Color.cyan);
/*      */       }
/*  536 */       this.step = 1;
/*      */     }
/*      */     else {
/*  539 */       this.loc += 1;
/*  540 */       this.grcolabel[this.current_i][(this.loc - 1)].setBackground(getBackground());
/*  541 */       this.grcolabel[this.current_i][this.loc].setBackground(Color.cyan);
/*  542 */       this.step = 1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void doRetrace()
/*      */   {
/*  550 */     this.controller.solver.reset();
/*  551 */     this.state.back();
/*  552 */     this.controller.solver.getData(this.controller.data);
/*      */     
/*      */ 
/*  555 */     if (this.step == 2) {
/*  556 */       this.current_i = (this.nState - 1);
/*  557 */       this.loc = this.nState;
/*  558 */       this.grcolabel[this.current_i][this.loc].setBackground(Color.cyan);
/*      */       
/*  560 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the equations : g(R").append(this.itcnt).append(") = Cik + ").append(this.SIGMA).append("j Pij(k").append(this.itcnt).append(") Vj(R").append(this.itcnt).append(") - Vi(R").append(this.itcnt).append(") , i=0,...,").append(this.nState - 1).append("\n").append("to be solved for g(R").append(this.itcnt).append(") and the Vi(R").append(this.itcnt).append(") \n\n").append(this.histr))));
/*      */       
/*      */ 
/*  563 */       this.nextbutt.setText("Enter");
/*  564 */       this.buttPanel.revalidate();
/*  565 */       this.buttPanel.repaint();
/*      */     }
/*  567 */     else if (this.loc == 0) {
/*  568 */       if (this.current_i > 0) {
/*  569 */         this.current_i -= 1;
/*  570 */         this.loc = this.nState;
/*  571 */         this.grcolabel[(this.current_i + 1)][0].setBackground(getBackground());
/*  572 */         this.grcolabel[this.current_i][this.loc].setBackground(Color.cyan);
/*      */       }
/*      */     }
/*      */     else {
/*  576 */       this.loc -= 1;
/*  577 */       this.grcolabel[this.current_i][(this.loc + 1)].setBackground(getBackground());
/*  578 */       this.grcolabel[this.current_i][this.loc].setBackground(Color.cyan);
/*      */     }
/*  580 */     this.step = 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doExpress()
/*      */   {
/*  589 */     int[] match = new int[5];
/*      */     
/*  591 */     Vector par = new Vector();
/*  592 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*  594 */     if ((this.itcnt == 1) && (this.current_state == 0))
/*      */     {
/*  596 */       match[0] = (this.current_eq + 1);
/*  597 */       match[1] = this.current_pos;
/*  598 */       match[2] = this.selected_table;
/*  599 */       match[3] = this.selected_row;
/*  600 */       match[4] = (this.selected_col - 1);
/*      */       
/*  602 */       if (this.selected_col == 0) {
/*  603 */         JOptionPane.showMessageDialog(this, "this column cannot be selected");
/*  604 */         return;
/*      */       }
/*      */       
/*      */ 
/*  608 */       par.addElement(match);
/*      */       
/*  610 */       opr.parameters = par;
/*  611 */       opr.operation_code = 40204;
/*  612 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  613 */         String err = this.controller.solver.getErrInfo();
/*  614 */         JOptionPane.showMessageDialog(this, err);
/*  615 */         return;
/*      */       }
/*      */       
/*      */ 
/*  619 */       this.state.addStep(opr);
/*      */       
/*  621 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*      */ 
/*  624 */       this.exprcolabel[this.current_eq][this.current_pos].setText(this.decfm.format(this.controller.data.exprcoef[this.current_eq][this.current_pos]));
/*  625 */       if (Math.abs(this.controller.data.exprcoef[this.current_eq][this.current_pos] - 'ϧ') < 0.01D) {
/*  626 */         this.exprcolabel[this.current_eq][this.current_pos].setText(" --- ");
/*      */       }
/*  628 */       if (Math.abs(this.controller.data.expr[this.current_state][this.current_eq]) > 10000.0D) {
/*  629 */         this.exprcolabel[this.current_eq][(this.nState + 1)].setText(this.expfm.format(this.controller.data.expr[this.current_state][this.current_eq]));
/*      */       } else {
/*  631 */         this.exprcolabel[this.current_eq][(this.nState + 1)].setText(this.fltfm.format(this.controller.data.expr[this.current_state][this.current_eq]));
/*      */       }
/*  633 */       if (Math.abs(this.controller.data.expr[this.current_state][this.current_eq] - 'ϧ') < 0.01D) {
/*  634 */         this.exprcolabel[this.current_eq][(this.nState + 1)].setText(" --- ");
/*      */       }
/*  636 */       if ((this.current_eq == this.nDecision - 1) && (this.current_pos == this.nState)) {
/*  637 */         this.exprcolabel[this.current_eq][this.current_pos].setBackground(getBackground());
/*      */         
/*  639 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.SIGMA).append("j Pij(k) Vj(R").append(this.itcnt).append(") - Vi(R").append(this.itcnt).append(") , k=1, ...,").append(this.nDecision).append("\n").append("evaluated for i = ").append(this.current_state).append(" and for each possible value of k.\n"))));
/*      */         
/*      */ 
/*  642 */         this.newdesPanel.removeAll();
/*  643 */         this.newdesPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Enter the value of d").append(this.current_state).append("(R").append(this.itcnt + 1).append(") : ")))));
/*  644 */         this.newdesPanel.add(this.newdifd);
/*  645 */         this.centerPanel.removeAll();
/*  646 */         this.centerPanel.add(this.vdlabel);
/*  647 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  648 */         this.centerPanel.add(this.statetext);
/*  649 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  650 */         this.centerPanel.add(this.newdesPanel);
/*  651 */         this.nextbutt.setText("Next");
/*  652 */         this.step = 5;
/*      */       }
/*  654 */       else if (this.current_pos >= this.nState) {
/*  655 */         if (this.current_eq < this.nDecision - 1) {
/*  656 */           this.current_eq += 1;
/*  657 */           this.current_pos = 0;
/*  658 */           this.exprcolabel[(this.current_eq - 1)][this.nState].setBackground(getBackground());
/*  659 */           this.exprcolabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*      */         }
/*  661 */         this.step = 4;
/*      */       }
/*      */       else {
/*  664 */         this.current_pos += 1;
/*  665 */         this.exprcolabel[this.current_eq][(this.current_pos - 1)].setBackground(getBackground());
/*  666 */         this.exprcolabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*  667 */         this.step = 4;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  672 */       par.addElement(new Integer(this.current_state));
/*  673 */       opr.parameters = par;
/*  674 */       opr.operation_code = 40205;
/*  675 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  676 */         String err = this.controller.solver.getErrInfo();
/*  677 */         JOptionPane.showMessageDialog(this, err);
/*  678 */         return;
/*      */       }
/*      */       
/*      */ 
/*  682 */       this.state.addStep(opr);
/*      */       
/*  684 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*      */ 
/*  687 */       for (int i = 0; i < this.nDecision; i++) {
/*  688 */         this.exprcolabel[i][0].setText(this.decfm.format(this.controller.data.exprcoef[i][0]));
/*  689 */         if (Math.abs(this.controller.data.exprcoef[i][0] - 'ϧ') < 0.01D)
/*  690 */           this.exprcolabel[i][0].setText(" --- ");
/*  691 */         for (int j = 1; j < this.nState + 1; j++) {
/*  692 */           this.exprcolabel[i][j].setText(this.decfm.format(this.controller.data.exprcoef[i][j]));
/*      */         }
/*  694 */         if (Math.abs(this.controller.data.expr[this.current_state][i]) > 10000.0D) {
/*  695 */           this.exprcolabel[i][(this.nState + 1)].setText(this.expfm.format(this.controller.data.expr[this.current_state][i]));
/*      */         } else {
/*  697 */           this.exprcolabel[i][(this.nState + 1)].setText(this.fltfm.format(this.controller.data.expr[this.current_state][i]));
/*      */         }
/*  699 */         if (Math.abs(this.controller.data.expr[this.current_state][i] - 'ϧ') < 0.01D) {
/*  700 */           this.exprcolabel[i][(this.nState + 1)].setText(" --- ");
/*      */         }
/*      */       }
/*  703 */       this.exprcolabel[(this.nDecision - 1)][this.nState].setBackground(getBackground());
/*      */       
/*  705 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.SIGMA).append("j Pij(k) Vj(R").append(this.itcnt).append(") - Vi(R").append(this.itcnt).append(") , k=1, ...,").append(this.nDecision).append("\n").append("evaluated for i = ").append(this.current_state).append(" and for each possible value of k.\n"))));
/*      */       
/*      */ 
/*  708 */       this.newdesPanel.removeAll();
/*  709 */       this.newdesPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Enter the value of d").append(this.current_state).append("(R").append(this.itcnt + 1).append(") : ")))));
/*  710 */       this.newdesPanel.add(this.newdifd);
/*  711 */       this.newdesPanel.setAlignmentX(0.0F);
/*  712 */       this.centerPanel.removeAll();
/*  713 */       this.centerPanel.add(this.vdlabel);
/*  714 */       this.centerPanel.add(Box.createVerticalStrut(10));
/*  715 */       this.centerPanel.add(this.statetext);
/*  716 */       this.centerPanel.add(Box.createVerticalStrut(10));
/*  717 */       this.centerPanel.add(this.newdesPanel);
/*  718 */       this.nextbutt.setText("Next");
/*  719 */       this.step = 5;
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
/*  731 */     this.controller.solver.reset();
/*  732 */     this.state.back();
/*  733 */     this.controller.solver.getData(this.controller.data);
/*      */     
/*      */ 
/*  736 */     if (this.step == 5) {
/*  737 */       if ((this.itcnt == 1) && (this.current_state == 0)) {
/*  738 */         this.current_eq = (this.nDecision - 1);
/*  739 */         this.current_pos = this.nState;
/*  740 */         this.exprcolabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*      */         
/*      */ 
/*  743 */         String vrs = " ";
/*  744 */         for (int i = 0; i < this.nState; i++) {
/*  745 */           if (i < this.nState - 1) {
/*  746 */             vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.vr[i])).append(" , "))))));
/*      */           } else {
/*  748 */             vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("and  V").append(i).append("(R").append(this.itcnt).append(") = 0 ."))))));
/*      */           }
/*      */         }
/*  751 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.SIGMA).append("j Pij(k) Vj(R").append(this.itcnt).append(") - Vi(R").append(this.itcnt).append(") , k=1, ...,").append(this.nDecision).append("\n").append("to be evaluated for i = ").append(this.current_state).append(" where \n").append(vrs).append("\n\n").append(this.histr))));
/*      */         
/*      */ 
/*      */ 
/*  755 */         this.centerPanel.removeAll();
/*  756 */         this.centerPanel.add(this.vdlabel);
/*  757 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  758 */         this.centerPanel.add(this.statetext);
/*  759 */         this.nextbutt.setText("Enter");
/*  760 */         this.buttPanel.revalidate();
/*  761 */         this.buttPanel.repaint();
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
/*  777 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press the NEXT button to have the computer evaluate the expression for state ").append(this.current_state).append("."))));
/*  778 */         this.centerPanel.removeAll();
/*  779 */         this.centerPanel.add(this.vdlabel);
/*  780 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  781 */         this.centerPanel.add(this.statetext);
/*      */       }
/*      */     }
/*  784 */     else if (this.current_pos == 0) {
/*  785 */       if (this.current_eq > 0) {
/*  786 */         this.current_eq -= 1;
/*  787 */         this.current_pos = this.nState;
/*  788 */         this.exprcolabel[(this.current_eq + 1)][0].setBackground(getBackground());
/*  789 */         this.exprcolabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*      */       }
/*      */     }
/*      */     else {
/*  793 */       this.current_pos -= 1;
/*  794 */       this.exprcolabel[this.current_eq][(this.current_pos + 1)].setBackground(getBackground());
/*  795 */       this.exprcolabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*      */     }
/*  797 */     this.step = 4;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doBack()
/*      */   {
/*  806 */     if (this.opseq.isEmpty() == false) {
/*  807 */       this.nextbutt.setEnabled(true);
/*  808 */       int last = ((Integer)this.opseq.lastElement()).intValue();
/*  809 */       this.opseq.removeElementAt(this.opseq.size() - 1);
/*  810 */       if (this.opseq.isEmpty()) {
/*  811 */         this.backbutt.setEnabled(false);
/*      */       }
/*  813 */       switch (last) {
/*      */       case 1: 
/*  815 */         doRetrace();
/*  816 */         revalidate();
/*  817 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  976 */         break;
/*      */       case 2: 
/*  821 */         this.controller.solver.reset();
/*  822 */         this.state.back();
/*  823 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  826 */         String vrs = " ";
/*  827 */         for (int i = 0; i < this.nState - 1; i++) {
/*  828 */           vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") ,"))))));
/*      */         }
/*  830 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press the NEXT button to solve the set of equations displayed above for the unknown \nvalues : g(R").append(this.itcnt).append(") and ").append(vrs).append(" with V").append(this.nState - 1).append("(R").append(this.itcnt).append(") set equal to zero."))));
/*      */         
/*  832 */         this.centerPanel.removeAll();
/*  833 */         this.centerPanel.add(this.vdlabel);
/*  834 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  835 */         this.centerPanel.add(this.statetext);
/*  836 */         this.step = 2;
/*      */         
/*  838 */         revalidate();
/*  839 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  976 */         break;
/*      */       case 3: 
/*  843 */         this.controller.solver.reset();
/*  844 */         this.state.back();
/*  845 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  848 */         this.vdlabel.setText("Step 1 :  Value Determination. ");
/*  849 */         this.soluPanel.removeAll();
/*  850 */         this.soluPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("g(R").append(this.itcnt).append(")  =  ").append(this.fltfm.format(this.controller.data.gr))))));
/*  851 */         for (int i = 0; i < this.nState; i++) {
/*  852 */           this.soluPanel.add(Box.createVerticalStrut(5));
/*  853 */           this.soluPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(")  =  ").append(this.fltfm.format(this.controller.data.vr[i]))))));
/*      */         }
/*  855 */         this.stendPanel.removeAll();
/*  856 */         this.stendPanel.add(this.soluPanel);
/*  857 */         this.stendPanel.add(Box.createRigidArea(new Dimension(80, 10)));
/*  858 */         this.stendPanel.add(new JLabel("Press the NEXT button to begin Step 2 : Policy Improvement"));
/*  859 */         this.centerPanel.removeAll();
/*  860 */         this.centerPanel.add(this.vdlabel);
/*  861 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  862 */         this.centerPanel.add(this.stendPanel);
/*      */         
/*  864 */         BuildFuncPanel();
/*  865 */         this.grcolabel[0][0].setBackground(getBackground());
/*  866 */         this.topPanel.removeAll();
/*  867 */         this.topPanel.add(this.funcPanel);
/*  868 */         this.nextbutt.setText("Next");
/*  869 */         this.step = 3;
/*      */         
/*  871 */         revalidate();
/*  872 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  976 */         break;
/*      */       case 4: 
/*  875 */         doUndochose();
/*      */         
/*  877 */         revalidate();
/*  878 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  976 */         break;
/*      */       case 5: 
/*  882 */         this.controller.solver.reset();
/*  883 */         this.state.back();
/*  884 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  886 */         if (this.step == 6) {
/*  887 */           this.current_state = (this.nState - 1);
/*  888 */         } else if (this.step == 4) {
/*  889 */           this.current_state -= 1;
/*      */           
/*  891 */           for (int i = 0; i < this.nDecision; i++) {
/*  892 */             this.exprcolabel[i][0].setText(this.decfm.format(this.controller.data.exprcoef[i][0]));
/*  893 */             if (Math.abs(this.controller.data.exprcoef[i][0] - 'ϧ') < 0.01D)
/*  894 */               this.exprcolabel[i][0].setText(" --- ");
/*  895 */             for (int j = 1; j < this.nState + 1; j++) {
/*  896 */               this.exprcolabel[i][j].setText(this.decfm.format(this.controller.data.exprcoef[i][j]));
/*      */             }
/*  898 */             this.exprcolabel[i][(this.nState + 1)].setText(this.fltfm.format(this.controller.data.expr[this.current_state][i]));
/*  899 */             if (Math.abs(this.controller.data.expr[this.current_state][i] - 'ϧ') < 0.01D) {
/*  900 */               this.exprcolabel[i][(this.nState + 1)].setText(" --- ");
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  905 */         this.vdlabel.setText("Step 2 : Policy Improvement    STATE = ".concat(String.valueOf(String.valueOf(this.current_state))));
/*  906 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.SIGMA).append("j Pij(k) Vj(R").append(this.itcnt).append(") - Vi(R").append(this.itcnt).append(") , k=1, ...,").append(this.nDecision).append("\n").append("evaluated for i = ").append(this.current_state).append(" and for each possible value of k.\n"))));
/*      */         
/*      */ 
/*  909 */         this.newdesPanel.removeAll();
/*  910 */         this.newdesPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Enter the value of d").append(this.current_state).append("(R").append(this.itcnt + 1).append(") : ")))));
/*  911 */         this.newdesPanel.add(this.newdifd);
/*  912 */         this.centerPanel.removeAll();
/*  913 */         this.centerPanel.add(this.vdlabel);
/*  914 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  915 */         this.centerPanel.add(this.statetext);
/*  916 */         this.centerPanel.add(Box.createVerticalStrut(10));
/*  917 */         this.centerPanel.add(this.newdesPanel);
/*  918 */         this.step = 5;
/*      */         
/*  920 */         revalidate();
/*  921 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  976 */         break;
/*      */       case 6: 
/*  925 */         this.controller.solver.reset();
/*  926 */         this.state.back();
/*  927 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  930 */         if (this.step == 1) {
/*  931 */           this.itcnt -= 1;
/*      */           
/*  933 */           this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt).append(")"))));
/*  934 */           this.current_state = (this.nState - 1);
/*  935 */           BuildEquPanel();
/*  936 */           this.current_eq = 0;
/*  937 */           this.current_pos = 0;
/*      */           
/*      */ 
/*  940 */           this.topPanel.removeAll();
/*  941 */           this.topPanel.add(this.equPanel);
/*  942 */           this.nextbutt.setText("Next");
/*      */         }
/*      */         
/*  945 */         this.oldpoPanel.removeAll();
/*  946 */         this.newpoPanel.removeAll();
/*      */         
/*  948 */         this.oldpoPanel.add(new JLabel("Old Policy"));
/*  949 */         this.oldpoPanel.add(Box.createVerticalStrut(5));
/*  950 */         this.newpoPanel.add(new JLabel("New Policy"));
/*  951 */         this.newpoPanel.add(Box.createVerticalStrut(5));
/*  952 */         for (int i = 0; i < this.nState; i++) {
/*  953 */           this.oldpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt).append(") = ").append(this.decfm.format(this.controller.data.old_decision[i]))))));
/*  954 */           this.newpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt + 1).append(") = ").append(this.decfm.format(this.controller.data.new_decision[i]))))));
/*      */         }
/*      */         
/*  957 */         this.yornPanel.removeAll();
/*  958 */         this.yornPanel.add(new JLabel("Is the current policy optimal?"));
/*  959 */         this.yornPanel.add(this.yorn);
/*  960 */         this.yornPanel.add(Box.createRigidArea(new Dimension(30, 50)));
/*  961 */         this.yornPanel.add(this.oldpoPanel);
/*  962 */         this.yornPanel.add(Box.createRigidArea(new Dimension(10, 50)));
/*  963 */         this.yornPanel.add(this.newpoPanel);
/*      */         
/*  965 */         this.centerPanel.removeAll();
/*  966 */         this.centerPanel.add(this.yornPanel);
/*  967 */         this.step = 6;
/*      */         
/*  969 */         revalidate();
/*  970 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  976 */         break;
/*      */       default: 
/*  973 */         System.out.println("Markov decision cannot back here.");break;
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void doNext()
/*      */   {
/*  983 */     Vector par = new Vector();
/*  984 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*  986 */     if (this.backbutt.isEnabled() == false)
/*  987 */       this.backbutt.setEnabled(true);
/*  988 */     switch (this.step) {
/*      */     case 1: 
/*  990 */       doEnter();
/*  991 */       revalidate();
/*  992 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1225 */       break;
/*      */     case 2: 
/*  996 */       opr.parameters = par;
/*  997 */       opr.operation_code = 40202;
/*  998 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  999 */         String err = this.controller.solver.getErrInfo();
/* 1000 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1005 */         this.state.addStep(opr);
/*      */         
/* 1007 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1010 */         this.soluPanel.removeAll();
/* 1011 */         this.soluPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("g(R").append(this.itcnt).append(")    =  ").append(this.fltfm.format(this.controller.data.gr))))));
/* 1012 */         for (int i = 0; i < this.nState; i++) {
/* 1013 */           this.soluPanel.add(Box.createVerticalStrut(5));
/* 1014 */           this.soluPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(")  =  ").append(this.fltfm.format(this.controller.data.vr[i]))))));
/*      */         }
/* 1016 */         this.stendPanel.removeAll();
/* 1017 */         this.stendPanel.add(this.soluPanel);
/* 1018 */         this.stendPanel.add(Box.createRigidArea(new Dimension(80, 10)));
/* 1019 */         this.stendPanel.add(new JLabel("Press the NEXT button to begin Step 2 : Policy Improvement"));
/* 1020 */         this.centerPanel.removeAll();
/* 1021 */         this.centerPanel.add(this.vdlabel);
/* 1022 */         this.centerPanel.add(Box.createVerticalStrut(10));
/* 1023 */         this.centerPanel.add(this.stendPanel);
/* 1024 */         this.step = 3;
/*      */         
/* 1026 */         revalidate();
/* 1027 */         repaint();
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
/*      */ 
/*      */ 
/* 1225 */       break;
/*      */     case 3: 
/* 1031 */       opr.parameters = par;
/* 1032 */       opr.operation_code = 40203;
/* 1033 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1034 */         String err = this.controller.solver.getErrInfo();
/* 1035 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1040 */         this.state.addStep(opr);
/*      */         
/* 1042 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1045 */         if (this.itcnt == 1) {
/* 1046 */           this.current_state = 0;
/*      */           
/* 1048 */           BuildEquPanel();
/* 1049 */           this.exprcolabel[0][0].setBackground(Color.cyan);
/* 1050 */           this.current_eq = 0;
/* 1051 */           this.current_pos = 0;
/*      */           
/* 1053 */           this.topPanel.removeAll();
/* 1054 */           this.topPanel.add(this.equPanel);
/*      */           
/* 1056 */           this.vdlabel.setText("Step 2 : Policy Improvement    STATE = ".concat(String.valueOf(String.valueOf(this.current_state))));
/* 1057 */           String vrs = " ";
/* 1058 */           for (int i = 0; i < this.nState; i++) {
/* 1059 */             if (i < this.nState - 1) {
/* 1060 */               vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.vr[i])).append(" , "))))));
/*      */             } else
/* 1062 */               vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("and  V").append(i).append("(R").append(this.itcnt).append(") = 0 ."))))));
/*      */           }
/* 1064 */           this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.SIGMA).append("j Pij(k) Vj(R").append(this.itcnt).append(") - Vi(R").append(this.itcnt).append(") , k=1, ...,").append(this.nDecision).append("\n").append("to be evaluated for i = ").append(this.current_state).append(" where \n").append(vrs).append("\n\n").append(this.histr))));
/*      */           
/*      */ 
/*      */ 
/* 1068 */           this.centerPanel.removeAll();
/* 1069 */           this.centerPanel.add(this.vdlabel);
/* 1070 */           this.centerPanel.add(Box.createVerticalStrut(10));
/* 1071 */           this.centerPanel.add(this.statetext);
/* 1072 */           this.nextbutt.setText("Enter");
/* 1073 */           this.step = 4;
/*      */         }
/*      */         else {
/* 1076 */           this.current_state = 0;
/*      */           
/* 1078 */           BuildEquPanel();
/* 1079 */           this.current_eq = 0;
/* 1080 */           this.current_pos = 0;
/*      */           
/* 1082 */           this.topPanel.removeAll();
/* 1083 */           this.topPanel.add(this.equPanel);
/*      */           
/* 1085 */           this.vdlabel.setText("Step 2 : Policy Improvement    STATE = ".concat(String.valueOf(String.valueOf(this.current_state))));
/* 1086 */           this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press the NEXT button to have the computer evaluate the expression for state ").append(this.current_state).append("."))));
/* 1087 */           this.centerPanel.removeAll();
/* 1088 */           this.centerPanel.add(this.vdlabel);
/* 1089 */           this.centerPanel.add(Box.createVerticalStrut(10));
/* 1090 */           this.centerPanel.add(this.statetext);
/* 1091 */           this.step = 4;
/*      */         }
/*      */         
/* 1094 */         revalidate();
/* 1095 */         repaint();
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
/* 1225 */       break;
/*      */     case 4: 
/* 1098 */       doExpress();
/* 1099 */       revalidate();
/* 1100 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1225 */       break;
/*      */     case 5: 
/* 1104 */       par.addElement(new Integer(this.current_state));
/* 1105 */       par.addElement(new Integer(this.newdifd.getValue()));
/* 1106 */       opr.parameters = par;
/* 1107 */       opr.operation_code = 40206;
/* 1108 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1109 */         String err = this.controller.solver.getErrInfo();
/* 1110 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1115 */         this.state.addStep(opr);
/*      */         
/* 1117 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1120 */         if (this.current_state < this.nState - 1) {
/* 1121 */           this.current_state += 1;
/* 1122 */           this.vdlabel.setText("Step 2 : Policy Improvement    STATE = ".concat(String.valueOf(String.valueOf(this.current_state))));
/* 1123 */           this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press the NEXT button to have the computer evaluate the expression for state ").append(this.current_state).append("."))));
/* 1124 */           this.centerPanel.removeAll();
/* 1125 */           this.centerPanel.add(this.vdlabel);
/* 1126 */           this.centerPanel.add(Box.createVerticalStrut(10));
/* 1127 */           this.centerPanel.add(this.statetext);
/* 1128 */           this.step = 4;
/*      */         }
/*      */         else {
/* 1131 */           this.oldpoPanel.removeAll();
/* 1132 */           this.newpoPanel.removeAll();
/*      */           
/* 1134 */           this.oldpoPanel.add(new JLabel("Old Policy"));
/* 1135 */           this.oldpoPanel.add(Box.createVerticalStrut(5));
/* 1136 */           this.newpoPanel.add(new JLabel("New Policy"));
/* 1137 */           this.newpoPanel.add(Box.createVerticalStrut(5));
/* 1138 */           for (int i = 0; i < this.nState; i++) {
/* 1139 */             this.oldpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt).append(") = ").append(this.decfm.format(this.controller.data.old_decision[i]))))));
/* 1140 */             this.newpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt + 1).append(") = ").append(this.decfm.format(this.controller.data.new_decision[i]))))));
/*      */           }
/* 1142 */           this.yornPanel.removeAll();
/* 1143 */           this.yornPanel.add(new JLabel("Is the current policy optimal?"));
/* 1144 */           this.yornPanel.add(this.yorn);
/* 1145 */           this.yornPanel.add(Box.createRigidArea(new Dimension(30, 50)));
/* 1146 */           this.yornPanel.add(this.oldpoPanel);
/* 1147 */           this.yornPanel.add(Box.createRigidArea(new Dimension(10, 50)));
/* 1148 */           this.yornPanel.add(this.newpoPanel);
/*      */           
/* 1150 */           this.centerPanel.removeAll();
/* 1151 */           this.centerPanel.add(this.yornPanel);
/* 1152 */           this.step = 6;
/*      */         }
/*      */         
/* 1155 */         revalidate();
/* 1156 */         repaint();
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
/* 1225 */       break;
/*      */     case 6: 
/* 1160 */       opr.parameters = par;
/* 1161 */       opr.operation_code = 40207;
/* 1162 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1163 */         String err = this.controller.solver.getErrInfo();
/* 1164 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1169 */         this.state.addStep(opr);
/*      */         
/* 1171 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1174 */         if (this.yorn.getSelectedIndex() == 0) {
/* 1175 */           this.itcnt += 1;
/*      */           
/* 1177 */           this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt).append(")"))));
/* 1178 */           BuildFuncPanel();
/* 1179 */           this.topPanel.removeAll();
/* 1180 */           this.topPanel.add(this.funcPanel);
/*      */           
/* 1182 */           this.vdlabel.setText("Step 1 :  Value Determination. ");
/* 1183 */           this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the equations : g(R").append(this.itcnt).append(") = Cik + ").append(this.SIGMA).append("j Pij(k)Vj(R").append(this.itcnt).append(") - Vi(R").append(this.itcnt).append(") , i=0,...,").append(this.nState - 1).append("\n").append("to be solved for g(R").append(this.itcnt).append(") and the Vi(R").append(this.itcnt).append(") \n\n").append(this.histr))));
/*      */           
/*      */ 
/* 1186 */           this.centerPanel.removeAll();
/* 1187 */           this.centerPanel.add(this.vdlabel);
/* 1188 */           this.centerPanel.add(Box.createVerticalStrut(10));
/* 1189 */           this.centerPanel.add(this.statetext);
/* 1190 */           this.nextbutt.setText("Enter");
/* 1191 */           this.step = 1;
/*      */         }
/*      */         else {
/* 1194 */           this.oldpoPanel.removeAll();
/* 1195 */           this.newpoPanel.removeAll();
/*      */           
/* 1197 */           this.oldpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("g(R").append(this.itcnt).append(")   = ").append(this.fltfm.format(this.controller.data.gr))))));
/* 1198 */           for (int i = 0; i < this.nState; i++) {
/* 1199 */             this.newpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt).append(") = ").append(this.decfm.format(this.controller.data.new_decision[i]))))));
/* 1200 */             this.oldpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.vr[i]))))));
/*      */           }
/*      */           
/* 1203 */           this.yornPanel.removeAll();
/* 1204 */           this.yornPanel.add(new JLabel("Optimal policy : "));
/* 1205 */           this.yornPanel.add(Box.createRigidArea(new Dimension(20, 50)));
/* 1206 */           this.yornPanel.add(this.newpoPanel);
/* 1207 */           this.yornPanel.add(Box.createRigidArea(new Dimension(20, 50)));
/* 1208 */           this.yornPanel.add(this.oldpoPanel);
/*      */           
/* 1210 */           this.centerPanel.removeAll();
/* 1211 */           this.centerPanel.add(this.yornPanel);
/* 1212 */           this.step = 7;
/* 1213 */           this.nextbutt.setEnabled(false);
/*      */         }
/*      */         
/* 1216 */         revalidate();
/* 1217 */         repaint();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1225 */       break;
/*      */     case 7: 
/*      */     default: 
/* 1222 */       System.out.println("Markov decision cannot next here.");
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
/*      */   public void LoadFile(ObjectInputStream in)
/*      */   {
/* 1235 */     int[] para = new int[11];
/*      */     try
/*      */     {
/* 1238 */       this.state = ((IORState)in.readObject());
/* 1239 */       para = (int[])in.readObject();
/* 1240 */       this.opseq = ((Vector)in.readObject());
/* 1241 */       this.state.setSolverStepVector();
/* 1242 */       in.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1245 */       e1.printStackTrace();
/* 1246 */       System.out.println("Open fails");
/*      */     }
/*      */     
/* 1249 */     this.step = para[0];
/* 1250 */     this.itcnt = para[1];
/* 1251 */     this.selected_k = para[2];
/* 1252 */     this.current_i = para[3];
/* 1253 */     this.loc = para[4];
/* 1254 */     this.selected_table = para[5];
/* 1255 */     this.selected_row = para[6];
/* 1256 */     this.selected_col = para[7];
/* 1257 */     this.current_state = para[8];
/* 1258 */     this.current_eq = para[9];
/* 1259 */     this.current_pos = para[10];
/*      */     
/* 1261 */     if (this.selected_k > 1) {
/* 1262 */       this.decbutt.setEnabled(true);
/*      */     } else
/* 1264 */       this.decbutt.setEnabled(false);
/* 1265 */     if (this.selected_k < this.nDecision) {
/* 1266 */       this.incbutt.setEnabled(true);
/*      */     } else
/* 1268 */       this.incbutt.setEnabled(false);
/* 1269 */     this.prlabel.setText("Pij(k) , k=".concat(String.valueOf(String.valueOf(this.selected_k))));
/* 1270 */     this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt).append(")"))));
/* 1271 */     this.probModel.fireTableDataChanged();
/* 1272 */     this.backbutt.setEnabled(true);
/*      */     
/* 1274 */     if ((this.step >= 1) && (this.step <= 3)) {
/* 1275 */       for (int i = 0; i < this.current_i; i++) {
/* 1276 */         for (int j = 0; j < this.nState + 1; j++) {
/* 1277 */           this.grcolabel[i][j].setText(this.decfm.format(this.controller.data.grcoef[i][j]));
/* 1278 */           if (Math.abs(this.controller.data.grcoef[i][j] - 'ϧ') < 0.01D)
/* 1279 */             this.grcolabel[i][j].setText(" --- ");
/*      */         }
/*      */       }
/* 1282 */       for (int j = 0; j < this.loc; j++) {
/* 1283 */         this.grcolabel[this.current_i][j].setText(this.decfm.format(this.controller.data.grcoef[this.current_i][j]));
/* 1284 */         if (Math.abs(this.controller.data.grcoef[this.current_i][j] - 'ϧ') < 0.01D)
/* 1285 */           this.grcolabel[this.current_i][j].setText(" --- ");
/*      */       }
/* 1287 */       this.grcolabel[0][0].setBackground(getBackground());
/* 1288 */       this.grcolabel[this.current_i][this.loc].setBackground(Color.cyan);
/*      */     }
/* 1290 */     if ((this.step >= 2) && (this.step <= 3)) {
/* 1291 */       this.grcolabel[(this.nState - 1)][this.nState].setText(this.decfm.format(this.controller.data.grcoef[(this.nState - 1)][this.nState]));
/* 1292 */       if (Math.abs(this.controller.data.grcoef[(this.nState - 1)][this.nState] - 'ϧ') < 0.01D)
/* 1293 */         this.grcolabel[(this.nState - 1)][this.nState].setText(" --- ");
/* 1294 */       this.grcolabel[(this.nState - 1)][this.nState].setBackground(getBackground());
/* 1295 */       String vrs = " ";
/* 1296 */       for (int i = 0; i < this.nState - 1; i++) {
/* 1297 */         vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") ,"))))));
/*      */       }
/* 1299 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press the NEXT button to solve the set of equations displayed above for the unknown \nvalues : g(R").append(this.itcnt).append(") and ").append(vrs).append(" with V").append(this.nState - 1).append("(R").append(this.itcnt).append(") set equal to zero."))));
/*      */       
/*      */ 
/* 1302 */       this.nextbutt.setText("Next");
/*      */     }
/* 1304 */     if (this.step == 3) {
/* 1305 */       this.soluPanel.removeAll();
/* 1306 */       this.soluPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("g(R").append(this.itcnt).append(")    =  ").append(this.fltfm.format(this.controller.data.gr))))));
/* 1307 */       for (int i = 0; i < this.nState; i++) {
/* 1308 */         this.soluPanel.add(Box.createVerticalStrut(5));
/* 1309 */         this.soluPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(")  =  ").append(this.fltfm.format(this.controller.data.vr[i]))))));
/*      */       }
/* 1311 */       this.stendPanel.removeAll();
/* 1312 */       this.stendPanel.add(this.soluPanel);
/* 1313 */       this.stendPanel.add(Box.createRigidArea(new Dimension(80, 10)));
/* 1314 */       this.stendPanel.add(new JLabel("Press the NEXT button to begin Step 2 : Policy Improvement"));
/* 1315 */       this.centerPanel.removeAll();
/* 1316 */       this.centerPanel.add(this.vdlabel);
/* 1317 */       this.centerPanel.add(Box.createVerticalStrut(10));
/* 1318 */       this.centerPanel.add(this.stendPanel);
/*      */     }
/* 1320 */     if (this.step >= 4) {
/* 1321 */       if ((this.itcnt == 1) && (this.current_state == 0))
/*      */       {
/* 1323 */         BuildEquPanel();
/* 1324 */         this.topPanel.removeAll();
/* 1325 */         this.topPanel.add(this.equPanel);
/*      */         
/*      */ 
/* 1328 */         for (int i = 0; i < this.current_eq; i++) {
/* 1329 */           for (int j = 0; j < this.nState + 1; j++) {
/* 1330 */             this.exprcolabel[i][j].setText(this.decfm.format(this.controller.data.exprcoef[i][j]));
/* 1331 */             if (Math.abs(this.controller.data.exprcoef[i][j] - 'ϧ') < 0.01D)
/* 1332 */               this.exprcolabel[i][j].setText(" --- ");
/*      */           }
/* 1334 */           if (Math.abs(this.controller.data.expr[this.current_state][i]) > 10000.0D) {
/* 1335 */             this.exprcolabel[i][(this.nState + 1)].setText(this.expfm.format(this.controller.data.expr[this.current_state][i]));
/*      */           } else {
/* 1337 */             this.exprcolabel[i][(this.nState + 1)].setText(this.fltfm.format(this.controller.data.expr[this.current_state][i]));
/*      */           }
/* 1339 */           if (Math.abs(this.controller.data.expr[this.current_state][i] - 'ϧ') < 0.01D)
/* 1340 */             this.exprcolabel[i][(this.nState + 1)].setText(" --- ");
/*      */         }
/* 1342 */         for (int j = 0; j < this.current_pos; j++) {
/* 1343 */           this.exprcolabel[this.current_eq][j].setText(this.decfm.format(this.controller.data.exprcoef[this.current_eq][j]));
/* 1344 */           if (Math.abs(this.controller.data.exprcoef[this.current_eq][j] - 'ϧ') < 0.01D)
/* 1345 */             this.exprcolabel[this.current_eq][j].setText(" --- ");
/*      */         }
/* 1347 */         this.exprcolabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*      */         
/* 1349 */         this.vdlabel.setText("Step 2 : Policy Improvement    STATE = ".concat(String.valueOf(String.valueOf(this.current_state))));
/* 1350 */         String vrs = " ";
/* 1351 */         for (i = 0; i < this.nState; i++) {
/* 1352 */           if (i < this.nState - 1) {
/* 1353 */             vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.vr[i])).append(" , "))))));
/*      */           } else
/* 1355 */             vrs = String.valueOf(String.valueOf(vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("and  V").append(i).append("(R").append(this.itcnt).append(") = 0 ."))))));
/*      */         }
/* 1357 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.SIGMA).append("j Pij(k) Vj(R").append(this.itcnt).append(") - Vi(R").append(this.itcnt).append(") , k=1, ...,").append(this.nDecision).append("\n").append("to be evaluated for i = ").append(this.current_state).append(" where \n").append(vrs).append("\n\n").append(this.histr))));
/*      */         
/*      */ 
/*      */ 
/* 1361 */         this.centerPanel.removeAll();
/* 1362 */         this.centerPanel.add(this.vdlabel);
/* 1363 */         this.centerPanel.add(Box.createVerticalStrut(10));
/* 1364 */         this.centerPanel.add(this.statetext);
/* 1365 */         this.nextbutt.setText("Enter");
/*      */       }
/*      */       else
/*      */       {
/* 1369 */         BuildEquPanel();
/* 1370 */         this.topPanel.removeAll();
/* 1371 */         this.topPanel.add(this.equPanel);
/*      */         
/* 1373 */         this.vdlabel.setText("Step 2 : Policy Improvement    STATE = ".concat(String.valueOf(String.valueOf(this.current_state))));
/* 1374 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press the NEXT button to have the computer evaluate the expression for state ").append(this.current_state).append("."))));
/* 1375 */         this.centerPanel.removeAll();
/* 1376 */         this.centerPanel.add(this.vdlabel);
/* 1377 */         this.centerPanel.add(Box.createVerticalStrut(10));
/* 1378 */         this.centerPanel.add(this.statetext);
/*      */       }
/*      */     }
/* 1381 */     if (this.step >= 5) {
/* 1382 */       for (int i = 0; i < this.nDecision; i++) {
/* 1383 */         for (int j = 0; j < this.nState + 1; j++) {
/* 1384 */           this.exprcolabel[i][j].setText(this.decfm.format(this.controller.data.exprcoef[i][j]));
/* 1385 */           if (Math.abs(this.controller.data.exprcoef[i][j] - 'ϧ') < 0.01D)
/* 1386 */             this.exprcolabel[i][j].setText(" --- ");
/*      */         }
/* 1388 */         if (Math.abs(this.controller.data.expr[this.current_state][i]) > 10000.0D) {
/* 1389 */           this.exprcolabel[i][(this.nState + 1)].setText(this.expfm.format(this.controller.data.expr[this.current_state][i]));
/*      */         } else {
/* 1391 */           this.exprcolabel[i][(this.nState + 1)].setText(this.fltfm.format(this.controller.data.expr[this.current_state][i]));
/*      */         }
/* 1393 */         if (Math.abs(this.controller.data.expr[this.current_state][i] - 'ϧ') < 0.01D) {
/* 1394 */           this.exprcolabel[i][(this.nState + 1)].setText(" --- ");
/*      */         }
/*      */       }
/* 1397 */       this.exprcolabel[(this.nDecision - 1)][this.nState].setBackground(getBackground());
/*      */       
/* 1399 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.SIGMA).append("j Pij(k) Vj(R").append(this.itcnt).append(") - Vi(R").append(this.itcnt).append(") , k=1, ...,").append(this.nDecision).append("\n").append("evaluated for i = ").append(this.current_state).append(" and for each possible value of k.\n"))));
/*      */       
/*      */ 
/* 1402 */       this.newdesPanel.removeAll();
/* 1403 */       this.newdesPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Enter the value of d").append(this.current_state).append("(R").append(this.itcnt + 1).append(") : ")))));
/* 1404 */       this.newdesPanel.add(this.newdifd);
/* 1405 */       this.newdesPanel.setAlignmentX(0.0F);
/* 1406 */       this.centerPanel.removeAll();
/* 1407 */       this.centerPanel.add(this.vdlabel);
/* 1408 */       this.centerPanel.add(Box.createVerticalStrut(10));
/* 1409 */       this.centerPanel.add(this.statetext);
/* 1410 */       this.centerPanel.add(Box.createVerticalStrut(10));
/* 1411 */       this.centerPanel.add(this.newdesPanel);
/* 1412 */       this.nextbutt.setText("Next");
/*      */     }
/* 1414 */     if (this.step >= 6) {
/* 1415 */       this.oldpoPanel.removeAll();
/* 1416 */       this.newpoPanel.removeAll();
/*      */       
/* 1418 */       this.oldpoPanel.add(new JLabel("Old Policy"));
/* 1419 */       this.oldpoPanel.add(Box.createVerticalStrut(5));
/* 1420 */       this.newpoPanel.add(new JLabel("New Policy"));
/* 1421 */       this.newpoPanel.add(Box.createVerticalStrut(5));
/* 1422 */       for (int i = 0; i < this.nState; i++) {
/* 1423 */         this.oldpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt).append(") = ").append(this.decfm.format(this.controller.data.old_decision[i]))))));
/* 1424 */         this.newpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt + 1).append(") = ").append(this.decfm.format(this.controller.data.new_decision[i]))))));
/*      */       }
/* 1426 */       this.yornPanel.removeAll();
/* 1427 */       this.yornPanel.add(new JLabel("Is the current policy optimal?"));
/* 1428 */       this.yornPanel.add(this.yorn);
/* 1429 */       this.yornPanel.add(Box.createRigidArea(new Dimension(30, 50)));
/* 1430 */       this.yornPanel.add(this.oldpoPanel);
/* 1431 */       this.yornPanel.add(Box.createRigidArea(new Dimension(10, 50)));
/* 1432 */       this.yornPanel.add(this.newpoPanel);
/*      */       
/* 1434 */       this.centerPanel.removeAll();
/* 1435 */       this.centerPanel.add(this.yornPanel);
/*      */     }
/* 1437 */     if (this.step == 7) {
/* 1438 */       this.oldpoPanel.removeAll();
/* 1439 */       this.newpoPanel.removeAll();
/*      */       
/* 1441 */       this.oldpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("g(R").append(this.itcnt).append(")   = ").append(this.fltfm.format(this.controller.data.gr))))));
/* 1442 */       for (int i = 0; i < this.nState; i++) {
/* 1443 */         this.newpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt).append(") = ").append(this.decfm.format(this.controller.data.new_decision[i]))))));
/* 1444 */         this.oldpoPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(R").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.vr[i]))))));
/*      */       }
/*      */       
/* 1447 */       this.yornPanel.removeAll();
/* 1448 */       this.yornPanel.add(new JLabel("Optimal policy : "));
/* 1449 */       this.yornPanel.add(Box.createRigidArea(new Dimension(20, 50)));
/* 1450 */       this.yornPanel.add(this.newpoPanel);
/* 1451 */       this.yornPanel.add(Box.createRigidArea(new Dimension(20, 50)));
/* 1452 */       this.yornPanel.add(this.oldpoPanel);
/*      */       
/* 1454 */       this.centerPanel.removeAll();
/* 1455 */       this.centerPanel.add(this.yornPanel);
/* 1456 */       this.nextbutt.setEnabled(false);
/*      */     }
/* 1458 */     revalidate();
/* 1459 */     repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFile(ObjectOutputStream out)
/*      */   {
/* 1468 */     int[] interpara = new int[11];
/*      */     
/* 1470 */     interpara[0] = this.step;
/* 1471 */     interpara[1] = this.itcnt;
/* 1472 */     interpara[2] = this.selected_k;
/* 1473 */     interpara[3] = this.current_i;
/* 1474 */     interpara[4] = this.loc;
/* 1475 */     interpara[5] = this.selected_table;
/* 1476 */     interpara[6] = this.selected_row;
/* 1477 */     interpara[7] = this.selected_col;
/* 1478 */     interpara[8] = this.current_state;
/* 1479 */     interpara[9] = this.current_eq;
/* 1480 */     interpara[10] = this.current_pos;
/*      */     try
/*      */     {
/* 1483 */       out.writeObject(this.state);
/* 1484 */       out.writeObject(interpara);
/* 1485 */       out.writeObject(this.opseq);
/* 1486 */       out.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1489 */       e1.printStackTrace();
/* 1490 */       System.out.println("Save fails");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void addSelectionListener(JTable table)
/*      */   {
/* 1497 */     String tablename = table.getColumnName(0);
/* 1498 */     JTable tmptable = table;
/*      */     
/* 1500 */     ListSelectionModel rowSM = table.getSelectionModel();
/*      */     
/* 1502 */     rowSM.addListSelectionListener(new ListSelectionListener() {
/*      */       private final String val$tablename;
/*      */       private final JTable val$tmptable;
/*      */       
/* 1506 */       public void valueChanged(ListSelectionEvent e) { ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/*      */         
/* 1508 */         if (!lsm.isSelectionEmpty())
/*      */         {
/*      */ 
/* 1511 */           if (this.val$tablename == "i \\ k") {
/* 1512 */             IORMKDAveragePanel.this.selected_table = 0;
/*      */           } else {
/* 1514 */             IORMKDAveragePanel.this.selected_table = IORMKDAveragePanel.this.selected_k;
/*      */           }
/* 1516 */           int selrow = this.val$tmptable.getSelectedRow();
/* 1517 */           int selcol = this.val$tmptable.getSelectedColumn();
/* 1518 */           if (selcol == 0) {
/* 1519 */             lsm.clearSelection();
/*      */           }
/* 1521 */           else if ((selrow >= 0) && (selcol >= 1)) {
/* 1522 */             IORMKDAveragePanel.this.selected_row = selrow;
/* 1523 */             IORMKDAveragePanel.this.selected_col = selcol;
/*      */           }
/*      */           
/*      */         }
/*      */       }
/* 1528 */     });
/* 1529 */     ListSelectionModel colSM = table.getColumnModel().getSelectionModel();
/*      */     
/* 1531 */     colSM.addListSelectionListener(new ListSelectionListener() {
/*      */       private final String val$tablename;
/*      */       private final JTable val$tmptable;
/*      */       
/* 1535 */       public void valueChanged(ListSelectionEvent e) { ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/*      */         
/* 1537 */         if (!lsm.isSelectionEmpty())
/*      */         {
/*      */ 
/* 1540 */           if (this.val$tablename == "i \\ k") {
/* 1541 */             IORMKDAveragePanel.this.selected_table = 0;
/*      */           } else {
/* 1543 */             IORMKDAveragePanel.this.selected_table = IORMKDAveragePanel.this.selected_k;
/*      */           }
/* 1545 */           int selrow = this.val$tmptable.getSelectedRow();
/* 1546 */           int selcol = this.val$tmptable.getSelectedColumn();
/* 1547 */           if (selcol == 0) {
/* 1548 */             lsm.clearSelection();
/*      */           }
/* 1550 */           else if ((selrow >= 0) && (selcol >= 1)) {
/* 1551 */             IORMKDAveragePanel.this.selected_row = selrow;
/* 1552 */             IORMKDAveragePanel.this.selected_col = selcol;
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
/* 1564 */       return IORMKDAveragePanel.this.controller.data.num_decisions + 1;
/*      */     }
/*      */     
/*      */     public int getRowCount()
/*      */     {
/* 1569 */       return IORMKDAveragePanel.this.controller.data.num_states;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col)
/*      */     {
/* 1574 */       if (col == 0) {
/* 1575 */         return "i \\ k";
/*      */       }
/* 1577 */       return "   ".concat(String.valueOf(String.valueOf(col)));
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col) {
/* 1581 */       String str = new String();
/* 1582 */       DecimalFormat dfm = new DecimalFormat("#.##");
/* 1583 */       if (col == 0) {
/* 1584 */         return "    ".concat(String.valueOf(String.valueOf(row)));
/*      */       }
/* 1586 */       if (Math.abs(IORMKDAveragePanel.this.controller.data.cost[row][(col - 1)] - 'ϧ') < 0.01D) {
/* 1587 */         return " --- ";
/*      */       }
/* 1589 */       return dfm.format(IORMKDAveragePanel.this.controller.data.cost[row][(col - 1)]);
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c)
/*      */     {
/* 1594 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1600 */       return false;
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
/* 1612 */       return IORMKDAveragePanel.this.controller.data.num_states + 1;
/*      */     }
/*      */     
/*      */     public int getRowCount()
/*      */     {
/* 1617 */       return IORMKDAveragePanel.this.controller.data.num_states;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col)
/*      */     {
/* 1622 */       if (col == 0) {
/* 1623 */         return "i \\ j";
/*      */       }
/* 1625 */       return "   ".concat(String.valueOf(String.valueOf(col - 1)));
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col) {
/* 1629 */       String str = new String();
/* 1630 */       DecimalFormat dfm = new DecimalFormat("#.###");
/* 1631 */       if (col == 0) {
/* 1632 */         return "    ".concat(String.valueOf(String.valueOf(row)));
/*      */       }
/* 1634 */       return dfm.format(IORMKDAveragePanel.this.controller.data.p[(IORMKDAveragePanel.this.selected_k - 1)][row][(col - 1)]);
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c) {
/* 1638 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1644 */     public boolean isCellEditable(int row, int col) { return false; }
/*      */   }
/*      */   
/*      */   class IORDesTableModel extends AbstractTableModel {
/*      */     IORDesTableModel() {}
/*      */     
/* 1650 */     private int[] des = new int[4];
/*      */     
/*      */     public int getColumnCount() {
/* 1653 */       return 2;
/*      */     }
/*      */     
/*      */     public int getRowCount() {
/* 1657 */       return IORMKDAveragePanel.this.controller.data.num_states;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col)
/*      */     {
/* 1662 */       if (col == 0) {
/* 1663 */         return "i";
/*      */       }
/* 1665 */       return "di";
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col) {
/* 1669 */       String str = new String();
/* 1670 */       if (col == 0) {
/* 1671 */         return "    ".concat(String.valueOf(String.valueOf(row)));
/*      */       }
/* 1673 */       return " ".concat(String.valueOf(String.valueOf(IORMKDAveragePanel.this.controller.data.old_decision[row])));
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c) {
/* 1677 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1683 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/* 1690 */     String str = "Interactive Policy Improvement Algorithm-Average Cost\n\n";
/* 1691 */     str = String.valueOf(String.valueOf(str)).concat("This algorithm is used to determine an optimal policy for a Markov decision ");
/* 1692 */     str = String.valueOf(String.valueOf(str)).concat("process under the criterion of minimizing expected average cost per unit ");
/* 1693 */     str = String.valueOf(String.valueOf(str)).concat("time. There are two major steps: \n");
/* 1694 */     str = String.valueOf(String.valueOf(str)).concat("    1.  value determination and\n");
/* 1695 */     str = String.valueOf(String.valueOf(str)).concat("    2.  policy improvement,\n");
/* 1696 */     str = String.valueOf(String.valueOf(str)).concat("performed in each iteration until an optimal solution is found. \n\n");
/* 1697 */     str = String.valueOf(String.valueOf(str)).concat("Choosing the coefficients in the value determination phase\n\n");
/* 1698 */     str = String.valueOf(String.valueOf(str)).concat("In the value determination phase, a set of equations is solved to determine ");
/* 1699 */     str = String.valueOf(String.valueOf(str)).concat("the v(i). Your job is to enter the coefficients of the v(i) in these equations. ");
/* 1700 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 1701 */     this.help_content_step.setText(str);
/* 1702 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/* 1709 */     String str = "Interactive Policy Improvement Algorithm-Average Cost\n\n";
/* 1710 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to interactively execute the average cost policy improvement ");
/* 1711 */     str = String.valueOf(String.valueOf(str)).concat("algorithm, presented in Section 4 of the \"Markov Decision Processes\" ");
/* 1712 */     str = String.valueOf(String.valueOf(str)).concat("chapter.\n\n");
/* 1713 */     str = String.valueOf(String.valueOf(str)).concat("Your role is to apply the logic of the algorithm, and the computer will do the number ");
/* 1714 */     str = String.valueOf(String.valueOf(str)).concat("crunching. The computer also will inform you if you make a mistake on the first iteration.\n");
/* 1715 */     str = String.valueOf(String.valueOf(str)).concat("When you finish a problem, you can print out all your work by choosing Print under the ");
/* 1716 */     str = String.valueOf(String.valueOf(str)).concat("File menu. If you are interrupted before you finish, you can save your work (choose Save ");
/* 1717 */     str = String.valueOf(String.valueOf(str)).concat("in the File menu) and return later (choose Open).\n\n");
/* 1718 */     str = String.valueOf(String.valueOf(str)).concat("Before executing this procedure for the first time, we recommend that you go through the");
/* 1719 */     str = String.valueOf(String.valueOf(str)).concat(" demonstration example entitled \"Policy Improvement Algorithm -- Average Cost Case\" in OR Tutor.\n");
/* 1720 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 1721 */     this.help_content_procedure.setText(str);
/* 1722 */     this.help_content_procedure.revalidate();
/*      */   }
/*      */   
/* 1725 */   private String str0 = "Interactive Policy Improvement Algorithm-Average Cost\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1732 */   private String str1 = "Choosing the coefficients in the value determination phase\n\n";
/*      */   
/*      */ 
/* 1735 */   private String str2 = "To continue\n\n";
/*      */   
/*      */ 
/* 1738 */   private String str3 = "To continue\n\n";
/*      */   
/*      */ 
/* 1741 */   private String str4 = "Choosing the coefficients in the policy improvement phase\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1751 */   private String str41 = "Press the NEXT button to have computer automatically evaluate the expressions for this state.";
/* 1752 */   private String str5 = "Entering the new decision for a given state\n\n";
/*      */   
/*      */ 
/*      */ 
/* 1756 */   private String str6 = "Selecting whether or not the current policy is optimal\n\n";
/*      */   
/*      */ 
/* 1759 */   private String str7 = "The solution\n\n";
/*      */   
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1803 */       break;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1803 */       break;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1803 */       break;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1803 */       break;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1803 */       break;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1803 */       break;
/*      */     case 7: 
/* 1792 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str7).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1803 */       break;
/*      */     default: 
/* 1795 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORMKDAveragePanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */