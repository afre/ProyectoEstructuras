/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Insets;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
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
/*      */ public class IORMKDSuccessivePanel extends IORActionPanel
/*      */ {
/*   32 */   private final int MAXSTATES = 4;
/*   33 */   private char SIGMA = '∑';
/*   34 */   private char ALPHA = 'α';
/*      */   
/*   36 */   private IORMDProcessController controller = null;
/*      */   
/*   38 */   private JPanel mainPanel = new JPanel();
/*      */   
/*   40 */   private JPanel topPanel = null;
/*   41 */   private JPanel iniPanel = null;
/*   42 */   private JPanel equPanel = null;
/*   43 */   private JPanel chosePanel = null;
/*   44 */   private JPanel centerPanel = null;
/*   45 */   private JPanel bottomPanel = null;
/*   46 */   private JPanel tablePanel = new JPanel();
/*   47 */   private JPanel swtPanel = new JPanel();
/*   48 */   private JPanel transtPanel = new JPanel();
/*   49 */   private JPanel buttPanel = new JPanel();
/*   50 */   private JPanel cikPanel = new JPanel();
/*   51 */   private JPanel pijPanel = new JPanel();
/*   52 */   private JPanel diPanel = new JPanel();
/*   53 */   private JPanel resultPanel = new JPanel();
/*   54 */   private JPanel newdesPanel = new JPanel();
/*   55 */   private JPanel yornPanel = new JPanel();
/*   56 */   private JPanel ditPanel = new JPanel();
/*   57 */   private JPanel vitPanel = new JPanel();
/*      */   
/*   59 */   private JScrollPane costPane = null;
/*   60 */   private JScrollPane probPane = null;
/*   61 */   private JScrollPane desPane = null;
/*      */   
/*   63 */   private IORMKDSuccessivePanel.IORCostTableModel costModel = null;
/*      */   private JTable costTable;
/*   65 */   private IORMKDSuccessivePanel.IORProbTableModel probModel = null;
/*      */   private JTable probTable;
/*   67 */   private IORMKDSuccessivePanel.IORDesTableModel desModel = null;
/*      */   private JTable desTable;
/*      */   private JPanel[] ivPanel;
/*      */   private JPanel[] v0Panel;
/*      */   private JPanel[] exprPanel;
/*      */   private JTextField[][] v0ceflabel;
/*      */   private JTextField[][] exprcolabel;
/*   74 */   private JLabel[][] vrlabel; private JButton backbutt = new JButton("Back");
/*   75 */   private JButton nextbutt = new JButton("Next");
/*   76 */   private JButton enterbutt = new JButton("Enter");
/*      */   
/*   78 */   private JButton incbutt = new JButton("+");
/*   79 */   private JButton decbutt = new JButton("-");
/*   80 */   private JComboBox yorn = new JComboBox();
/*      */   
/*   82 */   private JLabel vdlabel = new JLabel();
/*   83 */   private JLabel ctlabel = new JLabel("Cik");
/*   84 */   private JLabel prlabel = new JLabel(); private JLabel deslabel = new JLabel();
/*   85 */   private JTextArea statetext = new JTextArea();
/*      */   
/*   87 */   private DecimalFormat decfm = new DecimalFormat();
/*   88 */   private DecimalFormat fltfm = new DecimalFormat();
/*   89 */   private DecimalFormat expfm = new DecimalFormat("##.#E0");
/*      */   
/*   91 */   private DecimalField[] inivrfd = new DecimalField[4];
/*   92 */   private WholeNumberField[] inikfd = new WholeNumberField[4];
/*   93 */   private WholeNumberField newdifd = new WholeNumberField(1, 2);
/*      */   private int nState;
/*      */   private int nDecision;
/*   96 */   private int itcnt = 1;
/*   97 */   private int step = 1;
/*      */   private int selected_k;
/*      */   private int selected_table;
/*      */   private int selected_row;
/*  101 */   private int selected_col; private int current_state; private int current_eq; private int current_pos; private double[] oldvr = new double[4];
/*      */   
/*  103 */   private Vector opseq = new Vector();
/*      */   
/*  105 */   private String vrs = new String();
/*  106 */   private String histr = "For the highlighted coefficient above , choose the appropriate value from the \ntables below and then press the Enter button. To display the transition matrix (Pij)\nfor a different k , press the \"+\" or \"-\" button. Press the Back button to backtrack.";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IORMKDSuccessivePanel(IORMDProcessController c)
/*      */   {
/*  117 */     super(c);
/*  118 */     this.controller = c;
/*  119 */     this.state = new IORState(this.controller.solver);
/*  120 */     add(this.mainPanel);
/*  121 */     this.bn_back.setVisible(false);
/*  122 */     this.bn_finish.setVisible(false);
/*  123 */     this.actionStatus.setVisible(false);
/*      */     
/*  125 */     this.nState = this.controller.data.num_states;
/*  126 */     this.nDecision = this.controller.data.num_decisions;
/*      */     
/*  128 */     this.selected_k = 1;
/*  129 */     this.decfm.setMaximumFractionDigits(3);
/*  130 */     this.fltfm.setMaximumFractionDigits(2);
/*  131 */     this.decfm.setGroupingUsed(false);
/*  132 */     this.fltfm.setGroupingUsed(false);
/*  133 */     this.expfm.setGroupingUsed(false);
/*      */     
/*  135 */     this.ivPanel = new JPanel[this.nState];
/*  136 */     this.v0Panel = new JPanel[this.nDecision];
/*  137 */     this.exprPanel = new JPanel[this.nDecision];
/*  138 */     this.v0ceflabel = new JTextField[this.nDecision][this.nState + 1];
/*  139 */     this.exprcolabel = new JTextField[this.nDecision][this.nState + 2];
/*  140 */     this.vrlabel = new JLabel[this.nDecision][this.nState];
/*      */     
/*  142 */     this.statetext.setEditable(false);
/*  143 */     this.statetext.setBackground(getBackground());
/*  144 */     this.statetext.setAlignmentX(0.0F);
/*      */     
/*      */ 
/*  147 */     for (int i = 0; i < this.nState; i++)
/*      */     {
/*  149 */       this.ivPanel[i] = new JPanel();
/*  150 */       this.inivrfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  151 */       this.inikfd[i] = new WholeNumberField(1, 2);
/*      */     }
/*  153 */     for (i = 0; i < this.nDecision; i++) {
/*  154 */       this.v0Panel[i] = new JPanel();
/*  155 */       this.v0Panel[i].setAlignmentX(0.0F);
/*  156 */       this.exprPanel[i] = new JPanel();
/*  157 */       this.v0ceflabel[i][0] = new JTextField(5);
/*  158 */       this.v0ceflabel[i][0].setEditable(false);
/*  159 */       this.v0ceflabel[i][0].setBackground(getBackground());
/*  160 */       this.exprcolabel[i][0] = new JTextField(5);
/*  161 */       this.exprcolabel[i][0].setEditable(false);
/*  162 */       this.exprcolabel[i][0].setBackground(getBackground());
/*      */       
/*  164 */       for (int j = 1; j < this.nState + 1; j++) {
/*  165 */         this.v0ceflabel[i][j] = new JTextField(4);
/*  166 */         this.v0ceflabel[i][j].setEditable(false);
/*  167 */         this.v0ceflabel[i][j].setBackground(getBackground());
/*  168 */         this.exprcolabel[i][j] = new JTextField(4);
/*  169 */         this.exprcolabel[i][j].setEditable(false);
/*  170 */         this.exprcolabel[i][j].setBackground(getBackground());
/*  171 */         this.vrlabel[i][(j - 1)] = new JLabel();
/*      */       }
/*  173 */       this.exprcolabel[i][(this.nState + 1)] = new JTextField(5);
/*  174 */       this.exprcolabel[i][(this.nState + 1)].setEditable(false);
/*  175 */       this.exprcolabel[i][(this.nState + 1)].setBackground(getBackground());
/*      */     }
/*      */     
/*  178 */     this.backbutt.setEnabled(false);
/*  179 */     this.buttPanel.add(this.backbutt);
/*  180 */     this.buttPanel.add(Box.createHorizontalStrut(20));
/*  181 */     this.buttPanel.add(this.nextbutt);
/*      */     
/*  183 */     this.decbutt.setEnabled(false);
/*  184 */     this.decbutt.setMargin(new Insets(0, 5, 0, 5));
/*  185 */     this.incbutt.setMargin(new Insets(0, 5, 0, 5));
/*  186 */     this.swtPanel.setLayout(new BoxLayout(this.swtPanel, 1));
/*  187 */     this.swtPanel.add(Box.createRigidArea(new Dimension(0, 12)));
/*  188 */     this.swtPanel.add(this.decbutt);
/*  189 */     this.swtPanel.add(new JLabel(" k "));
/*  190 */     this.swtPanel.add(this.incbutt);
/*      */     
/*  192 */     this.yorn.addItem("Yes");
/*  193 */     this.yorn.addItem("No");
/*  194 */     this.yornPanel.add(new JLabel("Would you like to perform another iteration?"));
/*  195 */     this.yornPanel.add(this.yorn);
/*  196 */     this.yornPanel.setAlignmentX(0.0F);
/*      */     
/*  198 */     this.topPanel = new JPanel();
/*      */     
/*  200 */     this.iniPanel = new JPanel();
/*  201 */     this.iniPanel.setLayout(new BoxLayout(this.iniPanel, 1));
/*  202 */     this.iniPanel.setPreferredSize(new Dimension(400, this.nState * 30));
/*  203 */     this.iniPanel.setMaximumSize(new Dimension(400, this.nState * 30));
/*      */     
/*  205 */     this.chosePanel = new JPanel();
/*  206 */     this.chosePanel.setLayout(new BoxLayout(this.chosePanel, 1));
/*  207 */     this.chosePanel.setPreferredSize(new Dimension(this.nState * 100 + 300, this.nDecision * 30));
/*  208 */     this.chosePanel.setMaximumSize(new Dimension(this.nState * 100 + 300, this.nDecision * 30));
/*      */     
/*  210 */     this.equPanel = new JPanel();
/*  211 */     this.equPanel.setLayout(new BoxLayout(this.equPanel, 1));
/*  212 */     this.equPanel.setPreferredSize(new Dimension(this.nState * 100 + 340, this.nDecision * 40));
/*  213 */     this.equPanel.setMaximumSize(new Dimension(this.nState * 100 + 340, this.nDecision * 40));
/*      */     
/*  215 */     this.bottomPanel = new JPanel();
/*  216 */     this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel, 1));
/*  217 */     this.cikPanel.setLayout(new BoxLayout(this.cikPanel, 1));
/*  218 */     this.pijPanel.setLayout(new BoxLayout(this.pijPanel, 1));
/*  219 */     this.diPanel.setLayout(new BoxLayout(this.diPanel, 1));
/*      */     
/*  221 */     this.newdesPanel.setAlignmentX(0.0F);
/*  222 */     this.newdesPanel.setMaximumSize(new Dimension(300, 30));
/*      */     
/*  224 */     this.ditPanel.setLayout(new BoxLayout(this.ditPanel, 1));
/*  225 */     this.vitPanel.setLayout(new BoxLayout(this.vitPanel, 1));
/*      */     
/*  227 */     this.ctlabel.setAlignmentX(0.5F);
/*  228 */     this.prlabel.setAlignmentX(0.5F);
/*  229 */     this.deslabel.setAlignmentX(0.5F);
/*      */     
/*  231 */     this.costModel = new IORMKDSuccessivePanel.IORCostTableModel();
/*  232 */     this.costTable = new JTable(this.costModel);
/*  233 */     this.costTable.setCellSelectionEnabled(true);
/*  234 */     this.costTable.setSelectionMode(0);
/*  235 */     this.costTable.setSelectionBackground(Color.cyan);
/*  236 */     this.costTable.setRowHeight(20);
/*  237 */     this.costPane = new JScrollPane(this.costTable);
/*  238 */     this.costPane.setAlignmentX(0.5F);
/*      */     
/*  240 */     this.probModel = new IORMKDSuccessivePanel.IORProbTableModel();
/*  241 */     this.probTable = new JTable(this.probModel);
/*  242 */     this.probTable.setCellSelectionEnabled(true);
/*  243 */     this.probTable.setSelectionMode(0);
/*  244 */     this.probTable.setSelectionBackground(Color.cyan);
/*  245 */     this.probTable.setRowHeight(20);
/*  246 */     this.probPane = new JScrollPane(this.probTable);
/*  247 */     this.probPane.setAlignmentX(0.5F);
/*      */     
/*  249 */     this.desModel = new IORMKDSuccessivePanel.IORDesTableModel();
/*  250 */     this.desTable = new JTable(this.desModel);
/*  251 */     this.desTable.setEnabled(false);
/*  252 */     this.desTable.setRowHeight(20);
/*  253 */     this.desPane = new JScrollPane(this.desTable);
/*  254 */     this.desPane.setAlignmentX(0.5F);
/*      */     
/*      */ 
/*  257 */     addSelectionListener(this.costTable);
/*  258 */     addSelectionListener(this.probTable);
/*      */     
/*  260 */     this.costPane.setPreferredSize(new Dimension(50 * (this.nDecision + 1), 24 + 20 * this.nState));
/*  261 */     this.costPane.setMaximumSize(new Dimension(50 * (this.nDecision + 1), 24 + 20 * this.nState));
/*  262 */     this.probPane.setPreferredSize(new Dimension(60 * this.nState, 24 + 20 * this.nState));
/*  263 */     this.probPane.setMaximumSize(new Dimension(60 * this.nState, 24 + 20 * this.nState));
/*  264 */     this.desPane.setPreferredSize(new Dimension(60, 24 + 20 * this.nState));
/*  265 */     this.desPane.setMaximumSize(new Dimension(60, 24 + 20 * this.nState));
/*      */     
/*  267 */     this.centerPanel = new JPanel();
/*  268 */     this.centerPanel.setLayout(new BoxLayout(this.centerPanel, 1));
/*  269 */     this.centerPanel.setAlignmentX(0.5F);
/*  270 */     this.centerPanel.setPreferredSize(new Dimension(500, 130));
/*  271 */     this.centerPanel.setMaximumSize(new Dimension(500, 130));
/*      */     
/*  273 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*  274 */     this.mainPanel.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
/*      */     
/*  276 */     InitialPanel();
/*      */     
/*      */ 
/*  279 */     this.nextbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  281 */         IORMKDSuccessivePanel.this.doNext();
/*      */       }
/*  283 */     });
/*  284 */     this.backbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  286 */         IORMKDSuccessivePanel.this.doBack();
/*      */       }
/*      */     });
/*      */     
/*  290 */     if (this.selected_k > 1) {
/*  291 */       this.decbutt.setEnabled(true);
/*      */     } else
/*  293 */       this.decbutt.setEnabled(false);
/*  294 */     if (this.selected_k < this.nDecision) {
/*  295 */       this.incbutt.setEnabled(true);
/*      */     } else {
/*  297 */       this.incbutt.setEnabled(false);
/*      */     }
/*  299 */     this.incbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  301 */         if (IORMKDSuccessivePanel.this.selected_k < IORMKDSuccessivePanel.this.nDecision) {
/*  302 */           IORMKDSuccessivePanel.access$4(IORMKDSuccessivePanel.this);
/*  303 */           IORMKDSuccessivePanel.this.decbutt.setEnabled(true);
/*  304 */           if (IORMKDSuccessivePanel.this.selected_k >= IORMKDSuccessivePanel.this.nDecision)
/*  305 */             IORMKDSuccessivePanel.this.incbutt.setEnabled(false);
/*      */         }
/*  307 */         IORMKDSuccessivePanel.this.probModel.fireTableDataChanged();
/*  308 */         IORMKDSuccessivePanel.this.probTable.getSelectionModel().clearSelection();
/*  309 */         IORMKDSuccessivePanel.this.probTable.getColumnModel().getSelectionModel().clearSelection();
/*  310 */         IORMKDSuccessivePanel.this.prlabel.setText("Pij(k) , k=".concat(String.valueOf(String.valueOf(IORMKDSuccessivePanel.this.selected_k))));
/*  311 */         IORMKDSuccessivePanel.this.pijPanel.revalidate();
/*  312 */         IORMKDSuccessivePanel.this.pijPanel.repaint();
/*      */       }
/*      */       
/*  315 */     });
/*  316 */     this.decbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  318 */         if (IORMKDSuccessivePanel.this.selected_k > 1) {
/*  319 */           IORMKDSuccessivePanel.access$11(IORMKDSuccessivePanel.this);
/*  320 */           IORMKDSuccessivePanel.this.incbutt.setEnabled(true);
/*  321 */           if (IORMKDSuccessivePanel.this.selected_k == 1)
/*  322 */             IORMKDSuccessivePanel.this.decbutt.setEnabled(false);
/*      */         }
/*  324 */         IORMKDSuccessivePanel.this.probModel.fireTableDataChanged();
/*  325 */         IORMKDSuccessivePanel.this.probTable.getSelectionModel().clearSelection();
/*  326 */         IORMKDSuccessivePanel.this.probTable.getColumnModel().getSelectionModel().clearSelection();
/*  327 */         IORMKDSuccessivePanel.this.prlabel.setText("Pij(k) , k=".concat(String.valueOf(String.valueOf(IORMKDSuccessivePanel.this.selected_k))));
/*  328 */         IORMKDSuccessivePanel.this.pijPanel.revalidate();
/*  329 */         IORMKDSuccessivePanel.this.pijPanel.repaint();
/*      */       }
/*      */       
/*  332 */     });
/*  333 */     this.costTable.addFocusListener(new FocusListener() {
/*      */       public void focusLost(FocusEvent e) {
/*  335 */         IORMKDSuccessivePanel.this.costTable.getSelectionModel().clearSelection();
/*  336 */         IORMKDSuccessivePanel.this.costTable.getColumnModel().getSelectionModel().clearSelection();
/*      */       }
/*      */       
/*      */ 
/*      */       public void focusGained(FocusEvent e) {}
/*  341 */     });
/*  342 */     this.probTable.addFocusListener(new FocusListener() {
/*      */       public void focusLost(FocusEvent e) {
/*  344 */         IORMKDSuccessivePanel.this.probTable.getSelectionModel().clearSelection();
/*  345 */         IORMKDSuccessivePanel.this.probTable.getColumnModel().getSelectionModel().clearSelection();
/*      */       }
/*      */       
/*      */ 
/*      */       public void focusGained(FocusEvent e) {}
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */   private void InitialPanel()
/*      */   {
/*  356 */     for (int i = 0; i < this.nState; i++) {
/*  357 */       this.ivPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(0) = 0,")))));
/*  358 */       this.ivPanel[i].add(Box.createRigidArea(new Dimension(30, 0)));
/*  359 */       this.ivPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(1) = min{ C").append(i).append("k } = ")))));
/*  360 */       this.ivPanel[i].add(this.inivrfd[i]);
/*  361 */       this.ivPanel[i].add(Box.createRigidArea(new Dimension(20, 0)));
/*  362 */       this.ivPanel[i].add(new JLabel("k = "));
/*  363 */       this.ivPanel[i].add(this.inikfd[i]);
/*      */       
/*  365 */       this.iniPanel.add(this.ivPanel[i]);
/*      */     }
/*      */     
/*  368 */     this.statetext.setText("Initially, Vi(0) = 0 for all i. For each state , i, enter the first approximation , Vi(1) \nand its corresponding decision , k.");
/*      */     
/*      */ 
/*  371 */     this.topPanel.removeAll();
/*  372 */     this.topPanel.add(this.iniPanel);
/*  373 */     this.centerPanel.removeAll();
/*  374 */     this.centerPanel.add(this.statetext);
/*      */     
/*  376 */     BuildBottomPanel();
/*      */     
/*  378 */     this.mainPanel.add(this.topPanel);
/*  379 */     this.mainPanel.add(Box.createVerticalStrut(10));
/*  380 */     this.mainPanel.add(this.centerPanel);
/*  381 */     this.mainPanel.add(Box.createVerticalStrut(10));
/*  382 */     this.mainPanel.add(this.bottomPanel);
/*  383 */     this.mainPanel.add(Box.createVerticalStrut(10));
/*  384 */     this.mainPanel.add(this.buttPanel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void BuildChosePanel()
/*      */   {
/*  391 */     JLabel minlabel = new JLabel("V0(2) = min(");
/*  392 */     minlabel.setPreferredSize(new Dimension(80, 20));
/*      */     
/*  394 */     this.chosePanel.removeAll();
/*      */     
/*  396 */     for (int i = 0; i < this.nDecision; i++) {
/*  397 */       this.v0Panel[i].removeAll();
/*  398 */       if (i == 0) {
/*  399 */         this.v0Panel[0].add(minlabel);
/*      */       } else {
/*  401 */         this.v0Panel[i].add(Box.createRigidArea(new Dimension(80, 20)));
/*      */       }
/*  403 */       this.v0ceflabel[i][0].setText(this.decfm.format(this.controller.data.exprcoef[i][0]));
/*  404 */       if (Math.abs(this.controller.data.exprcoef[i][0] - 'ϧ') < 0.01D) {
/*  405 */         this.exprcolabel[i][0].setText(" --- ");
/*      */       }
/*  407 */       this.v0Panel[i].add(this.v0ceflabel[i][0]);
/*  408 */       this.v0Panel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" + (").append(this.decfm.format(this.controller.data.discount)).append(")[")))));
/*  409 */       for (int j = 1; j < this.nState + 1; j++) {
/*  410 */         if (j > 1)
/*  411 */           this.v0Panel[i].add(new JLabel(" + "));
/*  412 */         this.v0ceflabel[i][j].setText(this.decfm.format(this.controller.data.exprcoef[i][j]));
/*  413 */         this.v0Panel[i].add(this.v0ceflabel[i][j]);
/*  414 */         if (Math.abs(this.controller.data.vr[(j - 1)]) > 10000.0D) {
/*  415 */           this.v0Panel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" (").append(this.expfm.format(this.controller.data.vr[(j - 1)])).append(")")))));
/*      */         } else {
/*  417 */           this.v0Panel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" (").append(this.fltfm.format(this.controller.data.vr[(j - 1)])).append(")")))));
/*      */         }
/*      */       }
/*  420 */       if (i < this.nDecision - 1) {
/*  421 */         this.v0Panel[i].add(new JLabel("] ,"));
/*      */       } else
/*  423 */         this.v0Panel[i].add(new JLabel("] )"));
/*  424 */       this.v0Panel[i].add(Box.createRigidArea(new Dimension(20, 0)));
/*  425 */       this.v0Panel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("k = ").append(i + 1)))));
/*      */       
/*  427 */       this.chosePanel.add(this.v0Panel[i]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildEquPanel()
/*      */   {
/*  436 */     this.equPanel.removeAll();
/*      */     
/*  438 */     for (int i = 0; i < this.nDecision; i++) {
/*  439 */       this.exprPanel[i].removeAll();
/*  440 */       this.exprcolabel[i][0].setText(this.decfm.format(this.controller.data.exprcoef[i][0]));
/*  441 */       if (Math.abs(this.controller.data.exprcoef[i][0] - 'ϧ') < 0.01D)
/*  442 */         this.exprcolabel[i][0].setText(" --- ");
/*  443 */       this.exprPanel[i].add(this.exprcolabel[i][0]);
/*  444 */       this.exprPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" + (").append(this.decfm.format(this.controller.data.discount)).append(")[")))));
/*  445 */       for (int j = 1; j < this.nState + 1; j++) {
/*  446 */         if (j > 1)
/*  447 */           this.exprPanel[i].add(new JLabel(" + "));
/*  448 */         this.exprcolabel[i][j].setText(this.decfm.format(this.controller.data.exprcoef[i][j]));
/*  449 */         this.exprPanel[i].add(this.exprcolabel[i][j]);
/*  450 */         if (Math.abs(this.controller.data.vr[(j - 1)]) > 10000.0D) {
/*  451 */           this.vrlabel[i][(j - 1)].setText(String.valueOf(String.valueOf(new StringBuffer(" (").append(this.expfm.format(this.controller.data.vr[(j - 1)])).append(")"))));
/*      */         } else
/*  453 */           this.vrlabel[i][(j - 1)].setText(String.valueOf(String.valueOf(new StringBuffer(" (").append(this.fltfm.format(this.controller.data.vr[(j - 1)])).append(")"))));
/*  454 */         this.exprPanel[i].add(this.vrlabel[i][(j - 1)]);
/*      */       }
/*  456 */       this.exprPanel[i].add(new JLabel("] = "));
/*  457 */       if (Math.abs(this.controller.data.expr[this.current_state][i]) > 10000.0D) {
/*  458 */         this.exprcolabel[i][(this.nState + 1)].setText(this.expfm.format(this.controller.data.expr[this.current_state][i]));
/*      */       } else {
/*  460 */         this.exprcolabel[i][(this.nState + 1)].setText(this.fltfm.format(this.controller.data.expr[this.current_state][i]));
/*      */       }
/*  462 */       if (Math.abs(this.controller.data.expr[this.current_state][i] - 'ϧ') < 0.01D)
/*  463 */         this.exprcolabel[i][(this.nState + 1)].setText(" --- ");
/*  464 */       this.exprPanel[i].add(this.exprcolabel[i][(this.nState + 1)]);
/*  465 */       this.exprPanel[i].add(Box.createHorizontalStrut(30));
/*  466 */       this.exprPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("k = ").append(i + 1)))));
/*      */       
/*  468 */       this.equPanel.add(this.exprPanel[i]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void BuildBottomPanel()
/*      */   {
/*  475 */     this.prlabel.setText("Pij(k) , k=".concat(String.valueOf(String.valueOf(this.selected_k))));
/*  476 */     this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt - 1).append(")"))));
/*      */     
/*  478 */     this.cikPanel.add(this.ctlabel);
/*  479 */     this.cikPanel.add(this.costPane);
/*  480 */     this.pijPanel.add(this.prlabel);
/*  481 */     this.pijPanel.add(this.probPane);
/*  482 */     this.transtPanel.add(this.pijPanel);
/*  483 */     this.transtPanel.add(this.swtPanel);
/*  484 */     this.diPanel.add(this.deslabel);
/*  485 */     this.diPanel.add(this.desPane);
/*      */     
/*  487 */     this.tablePanel.add(this.cikPanel);
/*  488 */     this.tablePanel.add(Box.createHorizontalStrut(20));
/*  489 */     this.tablePanel.add(this.transtPanel);
/*  490 */     this.tablePanel.add(Box.createHorizontalStrut(20));
/*  491 */     this.tablePanel.add(this.diPanel);
/*  492 */     this.bottomPanel.add(this.tablePanel);
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
/*      */   private void updateEqucoef()
/*      */   {
/*  518 */     for (int i = 0; i < this.nDecision; i++) {
/*  519 */       this.exprcolabel[i][0].setText(this.decfm.format(this.controller.data.exprcoef[i][0]));
/*  520 */       if (Math.abs(this.controller.data.exprcoef[i][0] - 'ϧ') < 0.01D) {
/*  521 */         this.exprcolabel[i][0].setText(" --- ");
/*      */       }
/*  523 */       for (int j = 1; j < this.nState + 1; j++) {
/*  524 */         this.exprcolabel[i][j].setText(this.decfm.format(this.controller.data.exprcoef[i][j]));
/*  525 */         if (Math.abs(this.controller.data.vr[(j - 1)]) > 10000.0D) {
/*  526 */           this.vrlabel[i][(j - 1)].setText(String.valueOf(String.valueOf(new StringBuffer(" (").append(this.expfm.format(this.controller.data.vr[(j - 1)])).append(")"))));
/*      */         } else {
/*  528 */           this.vrlabel[i][(j - 1)].setText(String.valueOf(String.valueOf(new StringBuffer(" (").append(this.fltfm.format(this.controller.data.vr[(j - 1)])).append(")"))));
/*      */         }
/*      */       }
/*  531 */       if (Math.abs(this.controller.data.expr[this.current_state][i]) > 10000.0D) {
/*  532 */         this.exprcolabel[i][(this.nState + 1)].setText(this.expfm.format(this.controller.data.expr[this.current_state][i]));
/*      */       } else {
/*  534 */         this.exprcolabel[i][(this.nState + 1)].setText(this.fltfm.format(this.controller.data.expr[this.current_state][i]));
/*      */       }
/*  536 */       if (Math.abs(this.controller.data.expr[this.current_state][i] - 'ϧ') < 0.01D) {
/*  537 */         this.exprcolabel[i][(this.nState + 1)].setText(" --- ");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doRetrace()
/*      */   {
/*  547 */     this.controller.solver.reset();
/*  548 */     this.state.back();
/*  549 */     this.controller.solver.getData(this.controller.data);
/*      */     
/*  551 */     if (this.step == 3) {
/*  552 */       this.current_eq = (this.nDecision - 1);
/*  553 */       this.current_pos = this.nState;
/*  554 */       this.v0ceflabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*  555 */       BuildChosePanel();
/*  556 */       this.topPanel.removeAll();
/*  557 */       this.topPanel.add(this.chosePanel);
/*  558 */       this.topPanel.revalidate();
/*  559 */       this.topPanel.repaint();
/*      */       
/*  561 */       this.vrs = " ";
/*  562 */       for (int i = 0; i < this.nState; i++) {
/*  563 */         this.vrs = String.valueOf(String.valueOf(this.vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(").append(this.itcnt - 1).append(") = ").append(this.fltfm.format(this.controller.data.vr[i])))))));
/*  564 */         if (i < this.nState - 1)
/*  565 */           this.vrs = String.valueOf(String.valueOf(this.vrs)).concat(" , ");
/*      */       }
/*  567 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k) Vj(1) , for k=1, ...,").append(this.nDecision).append("\n").append("for i = 0 , where \n").append(this.vrs).append("\n\n").append(this.histr))));
/*      */       
/*      */ 
/*  570 */       this.nextbutt.setText("Enter");
/*      */     }
/*  572 */     else if (this.current_pos == 0) {
/*  573 */       if (this.current_eq > 0) {
/*  574 */         this.current_eq -= 1;
/*  575 */         this.current_pos = this.nState;
/*  576 */         this.v0ceflabel[(this.current_eq + 1)][0].setBackground(getBackground());
/*  577 */         this.v0ceflabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*      */       }
/*      */     }
/*      */     else {
/*  581 */       this.current_pos -= 1;
/*  582 */       this.v0ceflabel[this.current_eq][(this.current_pos + 1)].setBackground(getBackground());
/*  583 */       this.v0ceflabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*      */     }
/*  585 */     this.step = 2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doBack()
/*      */   {
/*  593 */     if (this.opseq.isEmpty() == false) {
/*  594 */       this.nextbutt.setEnabled(true);
/*  595 */       int last = ((Integer)this.opseq.lastElement()).intValue();
/*  596 */       this.opseq.removeElementAt(this.opseq.size() - 1);
/*  597 */       if (this.opseq.isEmpty()) {
/*  598 */         this.backbutt.setEnabled(false);
/*      */       }
/*  600 */       switch (last)
/*      */       {
/*      */       case 1: 
/*  603 */         this.controller.solver.reset();
/*  604 */         this.state.back();
/*  605 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  608 */         this.itcnt = 1;
/*  609 */         this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt - 1).append(")"))));
/*      */         
/*  611 */         this.topPanel.removeAll();
/*  612 */         this.topPanel.add(this.iniPanel);
/*  613 */         this.topPanel.revalidate();
/*  614 */         this.topPanel.repaint();
/*  615 */         this.statetext.setText("Initially, Vi(0) = 0 for all i. For each state , i, enter the first approximation , Vi(1)\nand its corresponding decision , k.");
/*      */         
/*  617 */         this.centerPanel.removeAll();
/*  618 */         this.centerPanel.add(this.statetext);
/*  619 */         this.nextbutt.setText("Next");
/*  620 */         this.step = 1;
/*      */         
/*  622 */         revalidate();
/*  623 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  726 */         break;
/*      */       case 2: 
/*  626 */         doRetrace();
/*      */         
/*  628 */         revalidate();
/*  629 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  726 */         break;
/*      */       case 3: 
/*  633 */         this.controller.solver.reset();
/*  634 */         this.state.back();
/*  635 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  638 */         if (this.current_state == 0) {
/*  639 */           this.statetext.setText("Press Next to evaluate the expressions for state 0.");
/*  640 */           this.centerPanel.removeAll();
/*  641 */           this.centerPanel.add(this.statetext);
/*      */         }
/*      */         else {
/*  644 */           this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("d").append(this.current_state - 1).append("(R").append(this.itcnt).append(") = ").append(this.controller.data.new_decision[(this.current_state - 1)]).append(",  thus ,  V").append(this.current_state - 1).append("(").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.new_vr[(this.current_state - 1)])))));
/*  645 */           this.centerPanel.removeAll();
/*  646 */           this.centerPanel.add(this.statetext);
/*  647 */           this.centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
/*  648 */           this.centerPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Press Next to evaluate the expressions for state ").append(this.current_state).append(".")))));
/*  649 */           this.centerPanel.add(Box.createVerticalStrut(60));
/*      */         }
/*  651 */         this.step = 3;
/*      */         
/*  653 */         revalidate();
/*  654 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  726 */         break;
/*      */       case 4: 
/*  658 */         this.controller.solver.reset();
/*  659 */         this.state.back();
/*  660 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  663 */         if (this.step == 3) {
/*  664 */           this.current_state -= 1;
/*      */           
/*  666 */           updateEqucoef();
/*      */         }
/*      */         else {
/*  669 */           this.current_state = (this.nState - 1);
/*      */         }
/*      */         
/*  672 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k) Vj(1) , for k=1, ...,").append(this.nDecision).append("\n").append("evaluated at  i = ").append(this.current_state).append(" , where \n").append(this.vrs))));
/*      */         
/*      */ 
/*  675 */         this.newdesPanel.removeAll();
/*  676 */         this.newdesPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Enter the value of d").append(this.current_state).append("(R").append(this.itcnt).append(") = ")))));
/*  677 */         this.newdesPanel.add(this.newdifd);
/*  678 */         this.centerPanel.removeAll();
/*  679 */         this.centerPanel.add(this.statetext);
/*  680 */         this.centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
/*  681 */         this.centerPanel.add(this.newdesPanel);
/*  682 */         this.centerPanel.add(Box.createVerticalStrut(30));
/*  683 */         this.step = 4;
/*      */         
/*  685 */         revalidate();
/*  686 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  726 */         break;
/*      */       case 5: 
/*  690 */         this.controller.solver.reset();
/*  691 */         this.state.back();
/*  692 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  694 */         this.current_state = (this.nState - 1);
/*      */         
/*  696 */         if (this.step == 3) {
/*  697 */           this.itcnt -= 1;
/*      */           
/*  699 */           updateEqucoef();
/*      */           
/*  701 */           this.vrs = " ";
/*  702 */           for (int i = 0; i < this.nState; i++) {
/*  703 */             this.vrs = String.valueOf(String.valueOf(this.vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(").append(this.itcnt - 1).append(") = ").append(this.fltfm.format(this.controller.data.vr[i])))))));
/*  704 */             if (i < this.nState - 1) {
/*  705 */               this.vrs = String.valueOf(String.valueOf(this.vrs)).concat(" , ");
/*      */             }
/*      */           }
/*  708 */           this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt - 1).append(")"))));
/*      */         }
/*      */         
/*  711 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("d").append(this.current_state).append("(R").append(this.itcnt).append(") = ").append(this.controller.data.new_decision[this.current_state]).append(",  thus ,  V").append(this.current_state).append("(").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.new_vr[this.current_state])))));
/*  712 */         this.centerPanel.removeAll();
/*  713 */         this.centerPanel.add(this.statetext);
/*  714 */         this.centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
/*  715 */         this.centerPanel.add(this.yornPanel);
/*  716 */         this.centerPanel.add(Box.createVerticalStrut(50));
/*  717 */         this.step = 5;
/*      */         
/*  719 */         revalidate();
/*  720 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  726 */         break;
/*      */       default: 
/*  723 */         System.out.println("Markov Decision Successive cannot back here.");break;
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void doEnter()
/*      */   {
/*  732 */     int[] match = new int[5];
/*      */     
/*  734 */     Vector par = new Vector();
/*  735 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*  737 */     match[0] = (this.current_eq + 1);
/*  738 */     match[1] = this.current_pos;
/*  739 */     match[2] = this.selected_table;
/*  740 */     match[3] = this.selected_row;
/*  741 */     match[4] = (this.selected_col - 1);
/*      */     
/*  743 */     if (this.selected_col == 0) {
/*  744 */       JOptionPane.showMessageDialog(this, "this column cannot be selected");
/*  745 */       return;
/*      */     }
/*      */     
/*  748 */     par.addElement(match);
/*      */     
/*  750 */     opr.parameters = par;
/*  751 */     opr.operation_code = 40402;
/*  752 */     if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  753 */       String err = this.controller.solver.getErrInfo();
/*  754 */       JOptionPane.showMessageDialog(this, err);
/*  755 */       return;
/*      */     }
/*      */     
/*      */ 
/*  759 */     this.state.addStep(opr);
/*      */     
/*  761 */     this.opseq.addElement(new Integer(this.step));
/*      */     
/*      */ 
/*  764 */     this.v0ceflabel[this.current_eq][this.current_pos].setText(this.decfm.format(this.controller.data.exprcoef[this.current_eq][this.current_pos]));
/*  765 */     if (Math.abs(this.controller.data.exprcoef[this.current_eq][this.current_pos] - 'ϧ') < 0.01D) {
/*  766 */       this.v0ceflabel[this.current_eq][this.current_pos].setText(" --- ");
/*      */     }
/*  768 */     if ((this.current_eq == this.nDecision - 1) && (this.current_pos == this.nState)) {
/*  769 */       this.v0ceflabel[this.current_eq][this.current_pos].setBackground(getBackground());
/*  770 */       BuildEquPanel();
/*  771 */       this.topPanel.removeAll();
/*  772 */       this.topPanel.add(this.equPanel);
/*  773 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press Next to evaluate the expressions for state ").append(this.current_state).append("."))));
/*  774 */       this.centerPanel.removeAll();
/*  775 */       this.centerPanel.add(this.statetext);
/*  776 */       this.nextbutt.setText("Next");
/*  777 */       this.step = 3;
/*      */     }
/*  779 */     else if (this.current_pos >= this.nState) {
/*  780 */       if (this.current_eq < this.nDecision - 1) {
/*  781 */         this.current_eq += 1;
/*  782 */         this.current_pos = 0;
/*  783 */         this.v0ceflabel[(this.current_eq - 1)][this.nState].setBackground(getBackground());
/*  784 */         this.v0ceflabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*      */       }
/*  786 */       this.step = 2;
/*      */     }
/*      */     else {
/*  789 */       this.current_pos += 1;
/*  790 */       this.v0ceflabel[this.current_eq][(this.current_pos - 1)].setBackground(getBackground());
/*  791 */       this.v0ceflabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*  792 */       this.step = 2;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doNext()
/*      */   {
/*  801 */     int[] inides = new int[4];
/*  802 */     double[] inivr = new double[4];
/*      */     
/*  804 */     Vector par = new Vector();
/*  805 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*  807 */     if (this.backbutt.isEnabled() == false)
/*  808 */       this.backbutt.setEnabled(true);
/*  809 */     switch (this.step)
/*      */     {
/*      */     case 1: 
/*  812 */       for (int i = 0; i < this.nState; i++) {
/*  813 */         inides[i] = this.inikfd[i].getValue();
/*  814 */         inivr[i] = this.inivrfd[i].getValue();
/*      */       }
/*  816 */       par.addElement(inivr);
/*  817 */       par.addElement(inides);
/*  818 */       opr.parameters = par;
/*  819 */       opr.operation_code = 40401;
/*  820 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  821 */         String err = this.controller.solver.getErrInfo();
/*  822 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  827 */         this.state.addStep(opr);
/*      */         
/*  829 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/*  832 */         this.itcnt = 2;
/*  833 */         this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt - 1).append(")"))));
/*      */         
/*  835 */         BuildChosePanel();
/*  836 */         this.v0ceflabel[0][0].setBackground(Color.cyan);
/*  837 */         this.current_eq = 0;
/*  838 */         this.current_pos = 0;
/*      */         
/*  840 */         this.topPanel.removeAll();
/*  841 */         this.topPanel.add(this.chosePanel);
/*      */         
/*  843 */         this.vrs = " ";
/*  844 */         for (i = 0; i < this.nState; i++) {
/*  845 */           this.vrs = String.valueOf(String.valueOf(this.vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(").append(this.itcnt - 1).append(") = ").append(this.fltfm.format(this.controller.data.vr[i])))))));
/*  846 */           if (i < this.nState - 1)
/*  847 */             this.vrs = String.valueOf(String.valueOf(this.vrs)).concat(" , ");
/*      */         }
/*  849 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k) Vj(1) , for k=1, ...,").append(this.nDecision).append("\n").append("for i = 0 , where \n").append(this.vrs).append("\n\n").append(this.histr))));
/*      */         
/*      */ 
/*  852 */         this.nextbutt.setText("Enter");
/*  853 */         this.step = 2;
/*      */         
/*  855 */         revalidate();
/*  856 */         repaint();
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
/* 1010 */       break;
/*      */     case 2: 
/*  859 */       this.current_state = 0;
/*  860 */       doEnter();
/*      */       
/*  862 */       revalidate();
/*  863 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1010 */       break;
/*      */     case 3: 
/*  867 */       par.addElement(new Integer(this.current_state));
/*  868 */       opr.parameters = par;
/*  869 */       opr.operation_code = 40403;
/*  870 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  871 */         String err = this.controller.solver.getErrInfo();
/*  872 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  877 */         this.state.addStep(opr);
/*      */         
/*  879 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/*  882 */         updateEqucoef();
/*      */         
/*  884 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k) Vj(1) , for k=1, ...,").append(this.nDecision).append("\n").append("evaluated at  i = ").append(this.current_state).append(" , where \n").append(this.vrs))));
/*      */         
/*      */ 
/*  887 */         this.newdesPanel.removeAll();
/*  888 */         this.newdesPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Enter the value of d").append(this.current_state).append("(R").append(this.itcnt).append(") = ")))));
/*  889 */         this.newdesPanel.add(this.newdifd);
/*  890 */         this.centerPanel.removeAll();
/*  891 */         this.centerPanel.add(this.statetext);
/*  892 */         this.centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
/*  893 */         this.centerPanel.add(this.newdesPanel);
/*  894 */         this.centerPanel.add(Box.createVerticalStrut(30));
/*  895 */         this.step = 4;
/*      */         
/*  897 */         revalidate();
/*  898 */         repaint();
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
/* 1010 */       break;
/*      */     case 4: 
/*  902 */       par.addElement(new Integer(this.current_state));
/*  903 */       par.addElement(new Integer(this.newdifd.getValue()));
/*  904 */       opr.parameters = par;
/*  905 */       opr.operation_code = 40404;
/*  906 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  907 */         String err = this.controller.solver.getErrInfo();
/*  908 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  913 */         this.state.addStep(opr);
/*      */         
/*  915 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/*  918 */         if (this.current_state == this.nState - 1) {
/*  919 */           this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("d").append(this.current_state).append("(R").append(this.itcnt).append(") = ").append(this.controller.data.new_decision[this.current_state]).append(",  thus ,  V").append(this.current_state).append("(").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.new_vr[this.current_state])))));
/*  920 */           this.centerPanel.removeAll();
/*  921 */           this.centerPanel.add(this.statetext);
/*  922 */           this.centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
/*  923 */           this.centerPanel.add(this.yornPanel);
/*  924 */           this.centerPanel.add(Box.createVerticalStrut(50));
/*  925 */           this.step = 5;
/*      */         }
/*      */         else
/*      */         {
/*  929 */           this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("d").append(this.current_state).append("(R").append(this.itcnt).append(") = ").append(this.controller.data.new_decision[this.current_state]).append(",  thus ,  V").append(this.current_state).append("(").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.new_vr[this.current_state])))));
/*  930 */           this.current_state += 1;
/*  931 */           this.centerPanel.removeAll();
/*  932 */           this.centerPanel.add(this.statetext);
/*  933 */           this.centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
/*  934 */           this.centerPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Press Next to evaluate the expressions for state ").append(this.current_state).append(".")))));
/*  935 */           this.centerPanel.add(Box.createVerticalStrut(60));
/*  936 */           this.step = 3;
/*      */         }
/*      */         
/*  939 */         revalidate();
/*  940 */         repaint();
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
/* 1010 */       break;
/*      */     case 5: 
/*  943 */       for (int i = 0; i < this.nState; i++) {
/*  944 */         this.oldvr[i] = this.controller.data.vr[i];
/*      */       }
/*      */       
/*  947 */       opr.parameters = par;
/*  948 */       opr.operation_code = 40405;
/*  949 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  950 */         String err = this.controller.solver.getErrInfo();
/*  951 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  956 */         this.state.addStep(opr);
/*      */         
/*  958 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/*  961 */         if (this.yorn.getSelectedIndex() == 0) {
/*  962 */           this.itcnt += 1;
/*  963 */           this.current_state = 0;
/*      */           
/*  965 */           this.vrs = " ";
/*  966 */           for (i = 0; i < this.nState; i++) {
/*  967 */             this.vrs = String.valueOf(String.valueOf(this.vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(").append(this.itcnt - 1).append(") = ").append(this.fltfm.format(this.controller.data.vr[i])))))));
/*  968 */             if (i < this.nState - 1) {
/*  969 */               this.vrs = String.valueOf(String.valueOf(this.vrs)).concat(" , ");
/*      */             }
/*      */           }
/*  972 */           updateEqucoef();
/*      */           
/*  974 */           this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Press Next to evaluate the expressions for state ").append(this.current_state).append("."))));
/*  975 */           this.centerPanel.removeAll();
/*  976 */           this.centerPanel.add(this.statetext);
/*      */           
/*  978 */           this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt - 1).append(")"))));
/*      */           
/*  980 */           this.step = 3;
/*      */         }
/*      */         else {
/*  983 */           this.ditPanel.removeAll();
/*  984 */           this.vitPanel.removeAll();
/*  985 */           for (i = 0; i < this.nState; i++) {
/*  986 */             this.ditPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt).append(") = ").append(this.decfm.format(this.controller.data.new_decision[i]))))));
/*  987 */             this.vitPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.vr[i]))))));
/*      */           }
/*  989 */           this.resultPanel.removeAll();
/*  990 */           this.resultPanel.add(this.ditPanel);
/*  991 */           this.resultPanel.add(Box.createRigidArea(new Dimension(30, 0)));
/*  992 */           this.resultPanel.add(this.vitPanel);
/*      */           
/*  994 */           this.centerPanel.removeAll();
/*  995 */           this.centerPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Solution after ").append(this.itcnt).append(" iterations :")))));
/*  996 */           this.centerPanel.add(Box.createVerticalStrut(10));
/*  997 */           this.centerPanel.add(this.resultPanel);
/*  998 */           this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt).append(")"))));
/*  999 */           this.nextbutt.setEnabled(false);
/* 1000 */           this.step = 6;
/*      */         }
/*      */         
/* 1003 */         revalidate();
/* 1004 */         repaint();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1010 */       break;
/*      */     case 6: 
/*      */     default: 
/* 1008 */       System.out.println("Markov successive method cannot next here.");
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void LoadFile(ObjectInputStream in)
/*      */   {
/* 1020 */     int[] para = new int[9];
/* 1021 */     int[] inikv = new int[4];
/* 1022 */     double[] inivrv = new double[4];
/*      */     try
/*      */     {
/* 1025 */       this.state = ((IORState)in.readObject());
/* 1026 */       para = (int[])in.readObject();
/* 1027 */       this.vrs = ((String)in.readObject());
/* 1028 */       this.opseq = ((Vector)in.readObject());
/* 1029 */       inikv = (int[])in.readObject();
/* 1030 */       inivrv = (double[])in.readObject();
/* 1031 */       this.oldvr = ((double[])in.readObject());
/* 1032 */       this.state.setSolverStepVector();
/* 1033 */       in.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1036 */       e1.printStackTrace();
/* 1037 */       System.out.println("Open fails");
/*      */     }
/* 1039 */     this.step = para[0];
/* 1040 */     this.itcnt = para[1];
/* 1041 */     this.selected_k = para[2];
/* 1042 */     this.selected_table = para[3];
/* 1043 */     this.selected_row = para[4];
/* 1044 */     this.selected_col = para[5];
/* 1045 */     this.current_state = para[6];
/* 1046 */     this.current_eq = para[7];
/* 1047 */     this.current_pos = para[8];
/*      */     
/* 1049 */     for (int i = 0; i < this.nState; i++) {
/* 1050 */       this.inikfd[i].setValue(inikv[i]);
/* 1051 */       this.inivrfd[i].setValue(inivrv[i]);
/*      */     }
/* 1053 */     if (this.selected_k > 1) {
/* 1054 */       this.decbutt.setEnabled(true);
/*      */     } else
/* 1056 */       this.decbutt.setEnabled(false);
/* 1057 */     if (this.selected_k < this.nDecision) {
/* 1058 */       this.incbutt.setEnabled(true);
/*      */     } else
/* 1060 */       this.incbutt.setEnabled(false);
/* 1061 */     this.prlabel.setText("Pij(k) , k=".concat(String.valueOf(String.valueOf(this.selected_k))));
/* 1062 */     this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt - 1).append(")"))));
/* 1063 */     this.probModel.fireTableDataChanged();
/*      */     
/* 1065 */     if (this.step == 2) {
/* 1066 */       BuildChosePanel();
/* 1067 */       this.topPanel.removeAll();
/* 1068 */       this.topPanel.add(this.chosePanel);
/* 1069 */       for (i = 0; i < this.current_eq; i++) {
/* 1070 */         for (int j = 0; j < this.nState + 1; j++) {
/* 1071 */           this.v0ceflabel[i][j].setText(this.decfm.format(this.controller.data.exprcoef[i][j]));
/* 1072 */           if (Math.abs(this.controller.data.exprcoef[i][j] - 'ϧ') < 0.01D)
/* 1073 */             this.v0ceflabel[i][j].setText(" --- ");
/*      */         }
/*      */       }
/* 1076 */       for (int j = 0; j < this.current_pos; j++) {
/* 1077 */         this.v0ceflabel[this.current_eq][j].setText(this.decfm.format(this.controller.data.exprcoef[this.current_eq][j]));
/* 1078 */         if (Math.abs(this.controller.data.exprcoef[this.current_eq][j] - 'ϧ') < 0.01D)
/* 1079 */           this.v0ceflabel[this.current_eq][j].setText(" --- ");
/*      */       }
/* 1081 */       this.v0ceflabel[this.current_eq][this.current_pos].setBackground(Color.cyan);
/*      */       
/*      */ 
/* 1084 */       this.vrs = " ";
/* 1085 */       for (i = 0; i < this.nState; i++) {
/* 1086 */         this.vrs = String.valueOf(String.valueOf(this.vrs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(").append(this.itcnt - 1).append(") = ").append(this.fltfm.format(this.controller.data.vr[i])))))));
/* 1087 */         if (i < this.nState - 1)
/* 1088 */           this.vrs = String.valueOf(String.valueOf(this.vrs)).concat(" , ");
/*      */       }
/* 1090 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k) Vj(1) , for k=1, ...,").append(this.nDecision).append("\n").append("for i = 0 , where \n").append(this.vrs).append("\n\n").append(this.histr))));
/*      */       
/*      */ 
/* 1093 */       this.nextbutt.setText("Enter");
/* 1094 */       this.backbutt.setEnabled(true);
/* 1095 */       this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt - 1).append(")"))));
/*      */     }
/* 1097 */     if (this.step >= 3) {
/* 1098 */       BuildEquPanel();
/* 1099 */       this.topPanel.removeAll();
/* 1100 */       this.topPanel.add(this.equPanel);
/* 1101 */       if (this.current_state == 0) {
/* 1102 */         this.statetext.setText("Press Next to evaluate the expressions for state ".concat(String.valueOf(String.valueOf(this.current_state))));
/* 1103 */         this.centerPanel.removeAll();
/* 1104 */         this.centerPanel.add(this.statetext);
/*      */       }
/*      */       else {
/* 1107 */         this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("d").append(this.current_state - 1).append("(R").append(this.itcnt).append(") = ").append(this.controller.data.new_decision[(this.current_state - 1)]).append(",  thus ,  V").append(this.current_state - 1).append("(").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.new_vr[(this.current_state - 1)])))));
/* 1108 */         this.centerPanel.removeAll();
/* 1109 */         this.centerPanel.add(this.statetext);
/* 1110 */         this.centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
/* 1111 */         this.centerPanel.add(new JLabel("Press Next to evaluate the expressions for state ".concat(String.valueOf(String.valueOf(this.current_state)))));
/* 1112 */         this.centerPanel.add(Box.createVerticalStrut(60));
/*      */       }
/* 1114 */       this.nextbutt.setText("Next");
/* 1115 */       this.backbutt.setEnabled(true);
/*      */     }
/* 1117 */     if (this.step >= 4)
/*      */     {
/* 1119 */       updateEqucoef();
/*      */       
/* 1121 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("Shown above are the expressions :    Cik + ").append(this.ALPHA).append(this.SIGMA).append("j Pij(k) Vj(1) , for k=1, ...,").append(this.nDecision).append("\n").append("evaluated at  i = ").append(this.current_state).append(" , where \n").append(this.vrs))));
/*      */       
/*      */ 
/* 1124 */       this.newdesPanel.removeAll();
/* 1125 */       this.newdesPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Enter the value of d").append(this.current_state).append("(R").append(this.itcnt).append(") = ")))));
/* 1126 */       this.newdesPanel.add(this.newdifd);
/* 1127 */       this.centerPanel.removeAll();
/* 1128 */       this.centerPanel.add(this.statetext);
/* 1129 */       this.centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
/* 1130 */       this.centerPanel.add(this.newdesPanel);
/* 1131 */       this.centerPanel.add(Box.createVerticalStrut(30));
/*      */     }
/* 1133 */     if (this.step >= 5) {
/* 1134 */       this.statetext.setText(String.valueOf(String.valueOf(new StringBuffer("d").append(this.current_state).append("(R").append(this.itcnt).append(") = ").append(this.controller.data.new_decision[this.current_state]).append(",  thus ,  V").append(this.current_state).append("(").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.new_vr[this.current_state])))));
/* 1135 */       this.centerPanel.removeAll();
/* 1136 */       this.centerPanel.add(this.statetext);
/* 1137 */       this.centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
/* 1138 */       this.centerPanel.add(this.yornPanel);
/* 1139 */       this.centerPanel.add(Box.createVerticalStrut(50));
/*      */     }
/* 1141 */     if (this.step >= 6) {
/* 1142 */       for (i = 0; i < this.nDecision; i++) {
/* 1143 */         for (int j = 1; j < this.nState + 1; j++) {
/* 1144 */           if (Math.abs(this.controller.data.vr[(j - 1)]) > 10000.0D) {
/* 1145 */             this.vrlabel[i][(j - 1)].setText(String.valueOf(String.valueOf(new StringBuffer(" (").append(this.expfm.format(this.oldvr[(j - 1)])).append(")"))));
/*      */           } else
/* 1147 */             this.vrlabel[i][(j - 1)].setText(String.valueOf(String.valueOf(new StringBuffer(" (").append(this.fltfm.format(this.oldvr[(j - 1)])).append(")"))));
/*      */         }
/*      */       }
/* 1150 */       this.ditPanel.removeAll();
/* 1151 */       this.vitPanel.removeAll();
/* 1152 */       for (i = 0; i < this.nState; i++) {
/* 1153 */         this.ditPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("d").append(i).append("(R").append(this.itcnt).append(") = ").append(this.decfm.format(this.controller.data.new_decision[i]))))));
/* 1154 */         this.vitPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("V").append(i).append("(").append(this.itcnt).append(") = ").append(this.fltfm.format(this.controller.data.vr[i]))))));
/*      */       }
/* 1156 */       this.resultPanel.removeAll();
/* 1157 */       this.resultPanel.add(this.ditPanel);
/* 1158 */       this.resultPanel.add(Box.createRigidArea(new Dimension(30, 0)));
/* 1159 */       this.resultPanel.add(this.vitPanel);
/*      */       
/* 1161 */       this.centerPanel.removeAll();
/* 1162 */       this.centerPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Solution after ").append(this.itcnt).append(" iterations :")))));
/* 1163 */       this.centerPanel.add(Box.createVerticalStrut(10));
/* 1164 */       this.centerPanel.add(this.resultPanel);
/* 1165 */       this.deslabel.setText(String.valueOf(String.valueOf(new StringBuffer("di(R").append(this.itcnt).append(")"))));
/* 1166 */       this.nextbutt.setEnabled(false);
/*      */     }
/* 1168 */     if (this.step >= 7) {
/* 1169 */       System.out.println("Markov decision has no this step.");
/*      */     }
/* 1171 */     revalidate();
/* 1172 */     repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFile(ObjectOutputStream out)
/*      */   {
/* 1183 */     int[] interpara = new int[9];
/* 1184 */     int[] inikv = new int[4];
/* 1185 */     double[] inivrv = new double[4];
/*      */     
/* 1187 */     interpara[0] = this.step;
/* 1188 */     interpara[1] = this.itcnt;
/* 1189 */     interpara[2] = this.selected_k;
/* 1190 */     interpara[3] = this.selected_table;
/* 1191 */     interpara[4] = this.selected_row;
/* 1192 */     interpara[5] = this.selected_col;
/* 1193 */     interpara[6] = this.current_state;
/* 1194 */     interpara[7] = this.current_eq;
/* 1195 */     interpara[8] = this.current_pos;
/*      */     
/* 1197 */     for (int i = 0; i < this.nState; i++) {
/* 1198 */       inikv[i] = this.inikfd[i].getValue();
/* 1199 */       inivrv[i] = this.inivrfd[i].getValue();
/*      */     }
/*      */     try
/*      */     {
/* 1203 */       out.writeObject(this.state);
/* 1204 */       out.writeObject(interpara);
/* 1205 */       out.writeObject(this.vrs);
/* 1206 */       out.writeObject(this.opseq);
/* 1207 */       out.writeObject(inikv);
/* 1208 */       out.writeObject(inivrv);
/* 1209 */       out.writeObject(this.oldvr);
/* 1210 */       out.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1213 */       e1.printStackTrace();
/* 1214 */       System.out.println("Save fails");
/*      */     }
/*      */   }
/*      */   
/*      */   private void addSelectionListener(JTable table)
/*      */   {
/* 1220 */     String tablename = table.getColumnName(0);
/* 1221 */     JTable tmptable = table;
/*      */     
/* 1223 */     ListSelectionModel rowSM = table.getSelectionModel();
/*      */     
/* 1225 */     rowSM.addListSelectionListener(new ListSelectionListener() {
/*      */       private final String val$tablename;
/*      */       private final JTable val$tmptable;
/*      */       
/* 1229 */       public void valueChanged(ListSelectionEvent e) { ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/*      */         
/* 1231 */         if (!lsm.isSelectionEmpty())
/*      */         {
/*      */ 
/* 1234 */           if (this.val$tablename == "i \\ k") {
/* 1235 */             IORMKDSuccessivePanel.this.selected_table = 0;
/*      */           } else {
/* 1237 */             IORMKDSuccessivePanel.this.selected_table = IORMKDSuccessivePanel.this.selected_k;
/*      */           }
/* 1239 */           int selrow = this.val$tmptable.getSelectedRow();
/* 1240 */           int selcol = this.val$tmptable.getSelectedColumn();
/* 1241 */           if (selcol == 0) {
/* 1242 */             lsm.clearSelection();
/*      */           }
/* 1244 */           else if ((selrow >= 0) && (selcol >= 1)) {
/* 1245 */             IORMKDSuccessivePanel.this.selected_row = selrow;
/* 1246 */             IORMKDSuccessivePanel.this.selected_col = selcol;
/*      */           }
/*      */           
/*      */         }
/*      */       }
/* 1251 */     });
/* 1252 */     ListSelectionModel colSM = table.getColumnModel().getSelectionModel();
/*      */     
/* 1254 */     colSM.addListSelectionListener(new ListSelectionListener() {
/*      */       private final String val$tablename;
/*      */       private final JTable val$tmptable;
/*      */       
/* 1258 */       public void valueChanged(ListSelectionEvent e) { ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/*      */         
/* 1260 */         if (!lsm.isSelectionEmpty())
/*      */         {
/*      */ 
/* 1263 */           if (this.val$tablename == "i \\ k") {
/* 1264 */             IORMKDSuccessivePanel.this.selected_table = 0;
/*      */           } else {
/* 1266 */             IORMKDSuccessivePanel.this.selected_table = IORMKDSuccessivePanel.this.selected_k;
/*      */           }
/* 1268 */           int selrow = this.val$tmptable.getSelectedRow();
/* 1269 */           int selcol = this.val$tmptable.getSelectedColumn();
/* 1270 */           if (selcol == 0) {
/* 1271 */             lsm.clearSelection();
/*      */           }
/* 1273 */           else if ((selrow >= 0) && (selcol >= 1)) {
/* 1274 */             IORMKDSuccessivePanel.this.selected_row = selrow;
/* 1275 */             IORMKDSuccessivePanel.this.selected_col = selcol;
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
/*      */     public int getColumnCount()
/*      */     {
/* 1288 */       return IORMKDSuccessivePanel.this.controller.data.num_decisions + 1;
/*      */     }
/*      */     
/*      */     public int getRowCount()
/*      */     {
/* 1293 */       return IORMKDSuccessivePanel.this.controller.data.num_states;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col)
/*      */     {
/* 1298 */       if (col == 0) {
/* 1299 */         return "i \\ k";
/*      */       }
/* 1301 */       return "   ".concat(String.valueOf(String.valueOf(col)));
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col) {
/* 1305 */       String str = new String();
/* 1306 */       DecimalFormat dfm = new DecimalFormat("#.##");
/* 1307 */       if (col == 0) {
/* 1308 */         return "    ".concat(String.valueOf(String.valueOf(row)));
/*      */       }
/* 1310 */       if (Math.abs(IORMKDSuccessivePanel.this.controller.data.cost[row][(col - 1)] - 'ϧ') < 0.01D) {
/* 1311 */         return " --- ";
/*      */       }
/* 1313 */       return dfm.format(IORMKDSuccessivePanel.this.controller.data.cost[row][(col - 1)]);
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c)
/*      */     {
/* 1318 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1324 */       return false;
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
/* 1336 */       return IORMKDSuccessivePanel.this.controller.data.num_states + 1;
/*      */     }
/*      */     
/*      */     public int getRowCount()
/*      */     {
/* 1341 */       return IORMKDSuccessivePanel.this.controller.data.num_states;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col)
/*      */     {
/* 1346 */       if (col == 0) {
/* 1347 */         return "i \\ j";
/*      */       }
/* 1349 */       return "   ".concat(String.valueOf(String.valueOf(col - 1)));
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col) {
/* 1353 */       String str = new String();
/* 1354 */       DecimalFormat dfm = new DecimalFormat("#.###");
/* 1355 */       if (col == 0) {
/* 1356 */         return "    ".concat(String.valueOf(String.valueOf(row)));
/*      */       }
/* 1358 */       return dfm.format(IORMKDSuccessivePanel.this.controller.data.p[(IORMKDSuccessivePanel.this.selected_k - 1)][row][(col - 1)]);
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c) {
/* 1362 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1368 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */   class IORDesTableModel extends AbstractTableModel
/*      */   {
/*      */     IORDesTableModel() {}
/*      */     
/* 1376 */     private int[] des = new int[4];
/*      */     
/*      */     public int getColumnCount() {
/* 1379 */       return 2;
/*      */     }
/*      */     
/*      */     public int getRowCount() {
/* 1383 */       return IORMKDSuccessivePanel.this.controller.data.num_states;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col)
/*      */     {
/* 1388 */       if (col == 0) {
/* 1389 */         return "i";
/*      */       }
/* 1391 */       return "di";
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col) {
/* 1395 */       String str = new String();
/* 1396 */       if (col == 0) {
/* 1397 */         return "    ".concat(String.valueOf(String.valueOf(row)));
/*      */       }
/* 1399 */       return " ".concat(String.valueOf(String.valueOf(IORMKDSuccessivePanel.this.controller.data.old_decision[row])));
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c) {
/* 1403 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1409 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/* 1418 */     String str = "Interactive Method of Successive Approximations\n\n";
/* 1419 */     str = String.valueOf(String.valueOf(str)).concat("This algorithm is used to determine an optimal policy (or at least an approximation) ");
/* 1420 */     str = String.valueOf(String.valueOf(str)).concat("for a Markov decision process under the criterion of minimizing expected total ");
/* 1421 */     str = String.valueOf(String.valueOf(str)).concat("discounted cost. In each iteration, an improved approximation is made of the V for ");
/* 1422 */     str = String.valueOf(String.valueOf(str)).concat("the respective states i.\n\n");
/* 1423 */     str = String.valueOf(String.valueOf(str)).concat("Entering the value of V for state i at iteration 1\n\n");
/* 1424 */     str = String.valueOf(String.valueOf(str)).concat("Use the cost matrix to determine the value of V (subscript i, superscript 1), ");
/* 1425 */     str = String.valueOf(String.valueOf(str)).concat("and then enter this number. The entry can be an integer or a decimal number.\n\n");
/* 1426 */     str = String.valueOf(String.valueOf(str)).concat("Entering the decision k for a given state at iteration 1\n\n");
/* 1427 */     str = String.valueOf(String.valueOf(str)).concat("For the current state i, use the cost matrix to determine the corresponding decision for ");
/* 1428 */     str = String.valueOf(String.valueOf(str)).concat("iteration 1. Enter this value of k (an integer), and then press the NEXT button.\n\n");
/* 1429 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/*      */ 
/* 1432 */     this.help_content_step.setText(str);
/* 1433 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/* 1440 */     String str = "Interactive Method of Successive Approximations\n\n";
/* 1441 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to interactively execute the method of successive approximations, ");
/* 1442 */     str = String.valueOf(String.valueOf(str)).concat("presented in Section 5 of the \"Markov Decision Processes\" chapter.\n\n");
/* 1443 */     str = String.valueOf(String.valueOf(str)).concat("Your role is to apply the logic of the algorithm, and the computer will do the number ");
/* 1444 */     str = String.valueOf(String.valueOf(str)).concat("crunching. The computer also will inform you if you make a mistake on the first iteration.\n\n");
/* 1445 */     str = String.valueOf(String.valueOf(str)).concat("When you finish a problem, you can print out all your work by choosing Print under the ");
/* 1446 */     str = String.valueOf(String.valueOf(str)).concat("File menu. If you are interrupted before you finish, you can save your work (choose ");
/* 1447 */     str = String.valueOf(String.valueOf(str)).concat("Save in the File menu) and return later (choose Open). ");
/* 1448 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/* 1450 */     this.help_content_procedure.setText(str);
/* 1451 */     this.help_content_procedure.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1459 */   private String str0 = "Interactive Method of Successive Approximations\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1464 */   private String str1 = "Entering the value of V for state i at iteration 1\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1470 */   private String str2 = "Choosing the entries in the equation giving the new value for V for state 0\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1480 */   private String str3 = "To continue\n\n";
/*      */   
/*      */ 
/*      */ 
/* 1484 */   private String str4 = "Entering the new decision for a given state\n\n";
/*      */   
/*      */ 
/* 1487 */   private String str5 = "Selecting whether or not you would like to perform another iteration\n\n";
/*      */   
/*      */ 
/* 1490 */   private String str6 = "The solution\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void updateStepHelpPanel()
/*      */   {
/* 1497 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/* 1498 */     switch (this.step) {
/*      */     case 1: 
/* 1500 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str1).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1521 */       break;
/*      */     case 2: 
/* 1503 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str2).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1521 */       break;
/*      */     case 3: 
/* 1506 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str3).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1521 */       break;
/*      */     case 4: 
/* 1509 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str4).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1521 */       break;
/*      */     case 5: 
/* 1512 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str5).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1521 */       break;
/*      */     case 6: 
/* 1515 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str6).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1521 */       break;
/*      */     default: 
/* 1518 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORMKDSuccessivePanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */