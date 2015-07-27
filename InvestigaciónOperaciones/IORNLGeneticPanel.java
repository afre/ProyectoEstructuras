/*      */ import java.awt.Dimension;
/*      */ import java.awt.FlowLayout;
/*      */ import java.awt.Font;
/*      */ import java.awt.Scrollbar;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.AdjustmentEvent;
/*      */ import java.awt.event.AdjustmentListener;
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
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.table.AbstractTableModel;
/*      */ 
/*      */ public class IORNLGeneticPanel
/*      */   extends IORActionPanel
/*      */ {
/*   30 */   private final char LESSTHAN = '≤';
/*   31 */   private final char GREATTHAN = '≥';
/*      */   
/*   33 */   private static final int MAXVARS = 2;
/*   34 */   private static final int MAXCONTS = 2;
/*   35 */   private static final int MAXTERMS = 10;
/*   36 */   private static final int UPBOUND1 = 63;
/*   37 */   private static final int UPBOUND2 = 63;
/*      */   
/*      */ 
/*   40 */   public IORTSPProcessController controller = null;
/*      */   
/*   42 */   private JPanel mainPanel = new JPanel();
/*      */   
/*      */ 
/*   45 */   private IORNLGeneticCanvasPanel gepanel = new IORNLGeneticCanvasPanel(this);
/*   46 */   private JPanel setupPanel = null;
/*   47 */   private JPanel gradPanel = null;
/*   48 */   private JPanel lpfuncPanel = null;
/*   49 */   private JPanel upPanel = null;
/*   50 */   private JPanel bottomPanel = null;
/*   51 */   private JPanel xtPanel = null;
/*   52 */   private JPanel newxPanel = null;
/*   53 */   private JPanel abinPanel = null;
/*   54 */   private JScrollPane scrlPane = null;
/*   55 */   private JPanel trialPanel = new JPanel();
/*   56 */   private JPanel buttPanel = new JPanel();
/*   57 */   private JPanel namePanel = new JPanel();
/*   58 */   private JPanel fieldPanel = new JPanel();
/*   59 */   private JPanel[] dfxPanel = new JPanel[2];
/*   60 */   private JPanel dervPanel = new JPanel();
/*   61 */   private JPanel fexPanel = new JPanel();
/*   62 */   private JPanel expPanel = new JPanel();
/*      */   
/*   64 */   private JPanel inputPanel = null;
/*   65 */   private JPanel numPanel = null;
/*   66 */   private JPanel objectivePanel = null;
/*   67 */   private JPanel constraintPanel = null;
/*      */   
/*   69 */   private JPanel upCPanel = new JPanel();
/*      */   
/*   71 */   private IORLinearEquation[] linear_equations = null;
/*      */   
/*   73 */   private JPanel[] aPanel = new JPanel[2]; private JPanel[] bPanel = new JPanel[2]; private JPanel[] abtPanel = new JPanel[2];
/*   74 */   private JLabel boundlabel = new JLabel();
/*   75 */   private JTextArea inditext = new JTextArea();
/*   76 */   private JLabel indilabel = new JLabel();
/*      */   
/*   78 */   private JLabel[] gradlabel = new JLabel[2];
/*   79 */   private JLabel trialabel = new JLabel();
/*   80 */   private JLabel zlabel = new JLabel();
/*   81 */   private JLabel trsulabel = new JLabel();
/*   82 */   private JLabel lpsulabel = new JLabel();
/*   83 */   private JLabel htlabel = new JLabel();
/*      */   
/*      */   private JLabel subjlabel;
/*   86 */   private JLabel comtlabel = new JLabel("(To make a new number take effect, you must press the ENTER key.)");
/*   87 */   private JLabel comtlabel2 = new JLabel("(To make a new number take effect, you must press the ENTER key.)");
/*      */   
/*   89 */   private JLabel instructionlabel = new JLabel("For each term, enter the coefficient on the left and the exponents on the right.");
/*      */   
/*   91 */   private IORNLGeneticPanel.IORFWTableModel fwModel = null;
/*   92 */   private JTable table = null;
/*      */   
/*   94 */   private JButton backbutt = new JButton("Back");
/*   95 */   private JButton nextbutt = new JButton("Next");
/*   96 */   public JComboBox maxmin = new JComboBox();
/*      */   private DecimalField[] fxcoef;
/*      */   private DecimalField[] dfx1coef;
/*      */   private DecimalField[] dfx2coef;
/*      */   private DecimalField[] dfx3coef;
/*      */   private DecimalField[] inixfd;
/*  102 */   private DecimalField[] afd; private DecimalField[] bfd; private DecimalField[] xlpfd; private DecimalField[] xtafd; private DecimalField[] xtbfd; private DecimalField[] xtcfd; private WholeNumberField[][] fxexp; private WholeNumberField[][] dfx1_xexp; private WholeNumberField[][] dfx2_xexp; private WholeNumberField[][] dfx3_xexp; private WholeNumberField NumTerm = new WholeNumberField(5, 2);
/*  103 */   private WholeNumberField NumVariable = new WholeNumberField(1, 2);
/*  104 */   private WholeNumberField NumConstraint = new WholeNumberField(0, 2);
/*      */   private JLabel upCLabel1;
/*      */   private JLabel upCLabel2;
/*      */   private Scrollbar upCBar1;
/*      */   private Scrollbar upCBar2;
/*  109 */   private WholeNumberField upCField1 = new WholeNumberField(31, 2);
/*  110 */   private WholeNumberField upCField2 = new WholeNumberField(63, 2);
/*  111 */   private JPanel textP = new JPanel();
/*  112 */   private JPanel fieldP = new JPanel();
/*      */   
/*  114 */   private int step = 0;
/*  115 */   private int itercount = 1;
/*      */   
/*      */ 
/*  118 */   private int nTerm = 5;
/*  119 */   private int nVariable = 1;
/*  120 */   private int nConstraint = 0;
/*      */   
/*  122 */   private int nUpConstraint1 = 31;
/*  123 */   private int nUpConstraint2 = 63;
/*      */   
/*      */ 
/*      */ 
/*  127 */   private DecimalFormat decfm = new DecimalFormat();
/*  128 */   private int countresult = 0;
/*  129 */   private double fitness = 0.0D;
/*      */   
/*  131 */   private Vector opseq = new Vector();
/*      */   
/*  133 */   private String xname = ""; private String zname = ""; private String fname = "";
/*  134 */   private String theastr = ""; private String thebstr = ""; private String thexstr = ""; private String thelpstr = ""; private String newxstr = "";
/*  135 */   private String curstr = "Current Trial Solution :";
/*      */   
/*      */ 
/*      */ 
/*      */   IOROperation opr;
/*      */   
/*      */ 
/*      */   Vector par;
/*      */   
/*      */ 
/*      */ 
/*      */   public IORNLGeneticPanel(IORTSPProcessController c)
/*      */   {
/*  148 */     super(c);
/*  149 */     this.controller = c;
/*  150 */     this.state = new IORState(this.controller.solver);
/*  151 */     add(this.mainPanel);
/*  152 */     this.actionStatus.hide();
/*  153 */     this.bn_back.hide();
/*  154 */     this.bn_finish.hide();
/*      */     
/*  156 */     this.decfm.setGroupingUsed(false);
/*  157 */     this.inditext.setEditable(false);
/*      */     
/*      */ 
/*  160 */     this.inixfd = new DecimalField[3];
/*  161 */     this.xlpfd = new DecimalField[3];
/*  162 */     this.afd = new DecimalField[3];
/*  163 */     this.bfd = new DecimalField[3];
/*  164 */     this.xtafd = new DecimalField[3];
/*  165 */     this.xtbfd = new DecimalField[3];
/*  166 */     this.xtcfd = new DecimalField[3];
/*      */     
/*      */ 
/*  169 */     for (int i = 0; i < 2; i++) {
/*  170 */       this.inixfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  171 */       this.xlpfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  172 */       this.afd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  173 */       this.bfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  174 */       this.xtafd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  175 */       this.xtbfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  176 */       this.xtcfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  177 */       this.gradlabel[i] = new JLabel();
/*      */     }
/*      */     
/*  180 */     this.buttPanel.setPreferredSize(new Dimension(300, 40));
/*  181 */     this.buttPanel.setMaximumSize(new Dimension(300, 40));
/*  182 */     this.buttPanel.add(this.backbutt);
/*  183 */     this.buttPanel.add(Box.createHorizontalStrut(10));
/*  184 */     this.buttPanel.add(this.nextbutt);
/*  185 */     this.backbutt.setEnabled(false);
/*      */     
/*  187 */     this.setupPanel = new JPanel();
/*  188 */     this.setupPanel.setLayout(new BoxLayout(this.setupPanel, 1));
/*      */     
/*  190 */     this.numPanel = new JPanel();
/*  191 */     this.numPanel.setPreferredSize(new Dimension(300, 80));
/*  192 */     this.numPanel.setMaximumSize(new Dimension(300, 80));
/*      */     
/*  194 */     this.namePanel.setLayout(new BoxLayout(this.namePanel, 1));
/*  195 */     this.namePanel.add(new JLabel("Number of Variables (1 or 2)"));
/*  196 */     this.namePanel.add(Box.createVerticalStrut(6));
/*  197 */     this.namePanel.add(new JLabel("Number of Constraints (0,1 or 2)"));
/*  198 */     this.namePanel.add(Box.createVerticalStrut(6));
/*  199 */     this.namePanel.add(new JLabel("Number of Terms in f(X) (≤ 10) :"));
/*  200 */     this.fieldPanel.setLayout(new BoxLayout(this.fieldPanel, 1));
/*  201 */     this.fieldPanel.add(this.NumVariable);
/*  202 */     this.fieldPanel.add(Box.createVerticalStrut(4));
/*  203 */     this.fieldPanel.add(this.NumConstraint);
/*  204 */     this.fieldPanel.add(Box.createVerticalStrut(4));
/*  205 */     this.fieldPanel.add(this.NumTerm);
/*  206 */     this.NumConstraint.setEnabled(false);
/*      */     
/*      */ 
/*  209 */     this.numPanel.add(this.namePanel);
/*  210 */     this.numPanel.add(Box.createHorizontalStrut(10));
/*  211 */     this.numPanel.add(this.fieldPanel);
/*      */     
/*  213 */     this.maxmin.addItem("MAX");
/*  214 */     this.maxmin.addItem("MIN");
/*      */     
/*  216 */     this.subjlabel = new JLabel("Subject to : x1≤".concat(String.valueOf(String.valueOf(this.nUpConstraint1))));
/*  217 */     this.upCLabel1 = new JLabel("Upper bound constraint of x1 ≤");
/*  218 */     this.upCLabel2 = new JLabel("Upper bound constraint of x2 ≤");
/*  219 */     this.upCBar1 = new Scrollbar(0, 0, 1, 1, this.nUpConstraint1 + 1);
/*  220 */     this.upCBar1.setValue(this.nUpConstraint1);
/*  221 */     this.upCBar2 = new Scrollbar(0, 0, 1, 1, this.nUpConstraint1 + 1);
/*  222 */     this.upCBar2.setValue(this.nUpConstraint2);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  227 */     this.objectivePanel = new JPanel();
/*      */     
/*  229 */     this.subjlabel.setAlignmentX(0.0F);
/*  230 */     this.boundlabel.setAlignmentX(0.0F);
/*  231 */     this.comtlabel.setAlignmentX(0.5F);
/*  232 */     this.instructionlabel.setAlignmentX(0.5F);
/*  233 */     this.constraintPanel = new JPanel();
/*  234 */     this.constraintPanel.setLayout(new BoxLayout(this.constraintPanel, 1));
/*  235 */     this.constraintPanel.setAlignmentX(0.5F);
/*  236 */     this.boundlabel.setBorder(new EmptyBorder(10, 0, 10, 0));
/*      */     
/*  238 */     this.setupPanel.add(this.numPanel);
/*  239 */     this.setupPanel.add(Box.createVerticalStrut(10));
/*  240 */     this.setupPanel.add(this.comtlabel);
/*  241 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*  242 */     this.setupPanel.add(this.instructionlabel);
/*  243 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*  244 */     this.setupPanel.add(this.objectivePanel);
/*      */     
/*  246 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*  247 */     this.setupPanel.add(this.upCPanel);
/*      */     
/*  249 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*  250 */     this.setupPanel.add(this.constraintPanel);
/*  251 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*      */     
/*  253 */     BuildSetupPanel();
/*      */     
/*  255 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*  256 */     this.mainPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
/*  257 */     this.mainPanel.add(this.setupPanel);
/*  258 */     this.mainPanel.add(this.buttPanel);
/*      */     
/*  260 */     this.gradPanel = new JPanel();
/*  261 */     this.gradPanel.setLayout(new BoxLayout(this.gradPanel, 1));
/*  262 */     this.gradPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  263 */     this.gradPanel.setAlignmentX(0.5F);
/*      */     
/*  265 */     this.lpfuncPanel = new JPanel();
/*  266 */     this.lpfuncPanel.setLayout(new BoxLayout(this.lpfuncPanel, 1));
/*  267 */     this.lpfuncPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  268 */     this.lpfuncPanel.setAlignmentX(0.5F);
/*      */     
/*  270 */     this.upPanel = new JPanel();
/*  271 */     this.upPanel.setLayout(new BoxLayout(this.upPanel, 1));
/*  272 */     this.upPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  273 */     this.upPanel.setAlignmentX(0.5F);
/*      */     
/*  275 */     this.bottomPanel = new JPanel();
/*  276 */     this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel, 1));
/*  277 */     this.bottomPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  278 */     this.bottomPanel.setAlignmentX(0.5F);
/*      */     
/*  280 */     this.xtPanel = new JPanel();
/*  281 */     this.xtPanel.setLayout(new BoxLayout(this.xtPanel, 1));
/*  282 */     this.xtPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  283 */     this.xtPanel.setAlignmentX(0.5F);
/*      */     
/*  285 */     this.newxPanel = new JPanel();
/*  286 */     this.newxPanel.setLayout(new BoxLayout(this.newxPanel, 1));
/*  287 */     this.newxPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  288 */     this.newxPanel.setAlignmentX(0.5F);
/*      */     
/*  290 */     this.abinPanel = new JPanel();
/*  291 */     this.abinPanel.setLayout(new BoxLayout(this.abinPanel, 1));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  297 */     this.fwModel = new IORNLGeneticPanel.IORFWTableModel();
/*  298 */     this.table = new JTable(this.fwModel);
/*  299 */     this.scrlPane = new JScrollPane(this.table);
/*  300 */     this.table.setCellSelectionEnabled(true);
/*  301 */     this.table.setEnabled(false);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  306 */     this.nextbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  308 */         IORNLGeneticPanel.this.doNext();
/*      */       }
/*  310 */     });
/*  311 */     this.backbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  313 */         IORNLGeneticPanel.this.doBack();
/*      */       }
/*      */       
/*  316 */     });
/*  317 */     this.NumTerm.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  319 */         IORNLGeneticPanel.this.setNumTerm();
/*      */       }
/*      */       
/*  322 */     });
/*  323 */     this.NumVariable.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  325 */         IORNLGeneticPanel.this.setNumVariable();
/*      */       }
/*      */       
/*  328 */     });
/*  329 */     this.NumConstraint.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  331 */         IORNLGeneticPanel.this.setNumConstraint();
/*      */       }
/*      */       
/*      */ 
/*  335 */     });
/*  336 */     this.upCField1.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  338 */         if ((IORNLGeneticPanel.this.upCField1.getValue() <= 63) && (IORNLGeneticPanel.this.upCField1.getValue() >= 0)) {
/*  339 */           IORNLGeneticPanel.this.nUpConstraint1 = IORNLGeneticPanel.this.upCField1.getValue();
/*      */         }
/*      */         else {
/*  342 */           IORNLGeneticPanel.this.upCField1.setValue(IORNLGeneticPanel.this.nUpConstraint1);
/*      */         }
/*      */         
/*  345 */         IORNLGeneticPanel.this.upCLabel1.setText("Upper bound constraint of x1 ≤");
/*  346 */         if (IORNLGeneticPanel.this.nVariable == 2) {
/*  347 */           IORNLGeneticPanel.this.subjlabel.setText(String.valueOf(String.valueOf(new StringBuffer("Subject to : x1≤").append(IORNLGeneticPanel.this.nUpConstraint1).append("   x2").append('≤').append(IORNLGeneticPanel.this.nUpConstraint2))));
/*      */         }
/*  349 */         else if (IORNLGeneticPanel.this.nVariable == 1) {
/*  350 */           IORNLGeneticPanel.this.subjlabel.setText("Subject to : x1≤".concat(String.valueOf(String.valueOf(IORNLGeneticPanel.this.nUpConstraint1))));
/*      */         }
/*      */         
/*      */       }
/*  354 */     });
/*  355 */     this.upCField2.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  357 */         if ((IORNLGeneticPanel.this.upCField2.getValue() <= 63) && (IORNLGeneticPanel.this.upCField2.getValue() >= 0)) {
/*  358 */           IORNLGeneticPanel.this.nUpConstraint2 = IORNLGeneticPanel.this.upCField2.getValue();
/*      */         }
/*      */         else {
/*  361 */           IORNLGeneticPanel.this.upCField2.setValue(IORNLGeneticPanel.this.nUpConstraint2);
/*      */         }
/*  363 */         IORNLGeneticPanel.this.upCLabel2.setText("Upper bound constraint of x2 ≤");
/*  364 */         IORNLGeneticPanel.this.subjlabel.setText(String.valueOf(String.valueOf(new StringBuffer("Subject to : x1≤").append(IORNLGeneticPanel.this.nUpConstraint1).append("   x2").append('≤').append(IORNLGeneticPanel.this.nUpConstraint2))));
/*      */       }
/*      */       
/*      */ 
/*  368 */     });
/*  369 */     this.upCBar1.addAdjustmentListener(new AdjustmentListener() {
/*      */       public void adjustmentValueChanged(AdjustmentEvent e) {
/*  371 */         IORNLGeneticPanel.this.nUpConstraint1 = IORNLGeneticPanel.this.upCBar1.getValue();
/*  372 */         IORNLGeneticPanel.this.upCLabel1.setText("Upper bound constraint of x1 ≤");
/*  373 */         if (IORNLGeneticPanel.this.nVariable == 2) {
/*  374 */           IORNLGeneticPanel.this.subjlabel.setText(String.valueOf(String.valueOf(new StringBuffer("Subject to : x1≤").append(IORNLGeneticPanel.this.nUpConstraint1).append("   x2").append('≤').append(IORNLGeneticPanel.this.nUpConstraint2))));
/*      */         }
/*  376 */         else if (IORNLGeneticPanel.this.nVariable == 1) {
/*  377 */           IORNLGeneticPanel.this.subjlabel.setText("Subject to : x1≤".concat(String.valueOf(String.valueOf(IORNLGeneticPanel.this.nUpConstraint1))));
/*      */         }
/*      */         
/*      */       }
/*  381 */     });
/*  382 */     this.upCBar2.addAdjustmentListener(new AdjustmentListener() {
/*      */       public void adjustmentValueChanged(AdjustmentEvent e) {
/*  384 */         IORNLGeneticPanel.this.nUpConstraint2 = IORNLGeneticPanel.this.upCBar2.getValue();
/*  385 */         IORNLGeneticPanel.this.upCLabel2.setText("Upper bound constraint of x2 ≤");
/*  386 */         IORNLGeneticPanel.this.subjlabel.setText(String.valueOf(String.valueOf(new StringBuffer("Subject to : x1≤").append(IORNLGeneticPanel.this.nUpConstraint1).append("   x2").append('≤').append(IORNLGeneticPanel.this.nUpConstraint2))));
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildSetupPanel()
/*      */   {
/*  397 */     double[] c = new double[10];
/*  398 */     String str = new String();
/*      */     
/*      */ 
/*      */ 
/*  402 */     this.nTerm = this.NumTerm.getValue();
/*  403 */     this.nVariable = this.NumVariable.getValue();
/*  404 */     this.nConstraint = this.NumConstraint.getValue();
/*      */     
/*  406 */     if (this.nTerm > 10) {
/*  407 */       this.nTerm = 10;
/*  408 */       this.NumTerm.setValue(this.nTerm);
/*      */     }
/*  410 */     if (this.nVariable > 2) {
/*  411 */       this.nVariable = 2;
/*  412 */       this.NumVariable.setValue(2);
/*      */     }
/*  414 */     if (this.nConstraint > 2) {
/*  415 */       this.nConstraint = 2;
/*  416 */       this.NumConstraint.setValue(2);
/*      */     }
/*      */     
/*  419 */     this.fxcoef = new DecimalField[this.nTerm];
/*  420 */     for (int i = 0; i < this.nTerm; i++) {
/*  421 */       if (i == 0) {
/*  422 */         this.fxcoef[i] = new DecimalField(12.0D, 3, this.decfm);
/*      */       }
/*  424 */       else if (i == 1) {
/*  425 */         this.fxcoef[i] = new DecimalField(-975.0D, 3, this.decfm);
/*      */       }
/*  427 */       else if (i == 2) {
/*  428 */         this.fxcoef[i] = new DecimalField(28000.0D, 3, this.decfm);
/*      */       }
/*  430 */       else if (i == 3) {
/*  431 */         this.fxcoef[i] = new DecimalField(-345000.0D, 3, this.decfm);
/*      */       }
/*  433 */       else if (i == 4) {
/*  434 */         this.fxcoef[i] = new DecimalField(1800000.0D, 3, this.decfm);
/*      */       } else {
/*  436 */         this.fxcoef[i] = new DecimalField(0.0D, 3, this.decfm);
/*      */       }
/*      */     }
/*      */     
/*  440 */     this.fxexp = new WholeNumberField[this.nTerm][2];
/*  441 */     for (i = 0; i < this.nTerm; i++) {
/*  442 */       for (int j = 0; j < 2; j++) {
/*  443 */         this.fxexp[i][j] = new WholeNumberField(5 - i, 1);
/*      */       }
/*      */     }
/*      */     
/*  447 */     this.objectivePanel.removeAll();
/*  448 */     this.objectivePanel.add(this.maxmin);
/*  449 */     this.objectivePanel.add(new JLabel(" f(X) =  "));
/*      */     
/*  451 */     for (i = 0; i < this.nTerm; i++) {
/*  452 */       if (i > 0) this.objectivePanel.add(new JLabel(" + "));
/*  453 */       this.objectivePanel.add(this.fxcoef[i]);
/*  454 */       for (int j = 0; j < this.nVariable; j++) {
/*  455 */         this.objectivePanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  456 */         this.objectivePanel.add(this.fxexp[i][j]);
/*      */       }
/*      */     }
/*      */     
/*  460 */     this.constraintPanel.removeAll();
/*      */     
/*  462 */     this.upCPanel.setPreferredSize(new Dimension(500, 80));
/*  463 */     this.upCPanel.setMaximumSize(new Dimension(500, 80));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  472 */     this.textP.setLayout(new BoxLayout(this.textP, 1));
/*  473 */     this.textP.add(this.upCLabel1);
/*  474 */     this.textP.add(Box.createVerticalStrut(6));
/*  475 */     this.textP.add(this.upCLabel2);
/*  476 */     this.upCLabel2.setVisible(false);
/*      */     
/*      */ 
/*  479 */     this.fieldP.setLayout(new BoxLayout(this.fieldP, 1));
/*  480 */     this.fieldP.add(this.upCField1);
/*  481 */     this.fieldP.add(Box.createVerticalStrut(4));
/*  482 */     this.fieldP.add(this.upCField2);
/*  483 */     this.upCField2.setVisible(false);
/*      */     
/*  485 */     this.upCPanel.add(this.textP);
/*  486 */     this.upCPanel.add(Box.createHorizontalStrut(10));
/*  487 */     this.upCPanel.add(this.fieldP);
/*  488 */     this.upCPanel.add(this.comtlabel2);
/*      */     
/*      */ 
/*  491 */     this.constraintPanel.add(this.subjlabel);
/*      */     
/*      */ 
/*  494 */     this.linear_equations = new IORLinearEquation[2];
/*  495 */     for (i = 0; i < this.nConstraint; i++) {
/*  496 */       this.linear_equations[i] = new IORLinearEquation(this.nVariable, 0, c);
/*  497 */       this.linear_equations[i].setMaximumSize(new Dimension(420, 50));
/*  498 */       this.linear_equations[i].setAlignmentX(0.0F);
/*  499 */       this.constraintPanel.add(this.linear_equations[i]);
/*      */     }
/*      */     
/*  502 */     str = " and    ";
/*  503 */     for (i = 0; i < this.nVariable; i++) {
/*  504 */       str = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(str))).append("x").append(i + 1).append(" ").append('≥').append(" 0 .     ")));
/*      */     }
/*  506 */     this.boundlabel.setText(str);
/*  507 */     this.constraintPanel.add(this.boundlabel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setNumVariable()
/*      */   {
/*  515 */     int oldv = this.nVariable;
/*  516 */     if ((this.NumVariable.getValue() <= 2) && (this.NumVariable.getValue() > 0)) {
/*  517 */       this.nVariable = this.NumVariable.getValue();
/*      */     }
/*      */     else {
/*  520 */       this.NumVariable.setValue(oldv);
/*      */     }
/*      */     
/*  523 */     if (this.NumVariable.getValue() == 1) {
/*  524 */       this.NumConstraint.setValue(0);
/*  525 */       this.NumConstraint.setEnabled(false);
/*  526 */       setNumConstraint();
/*  527 */       this.upCLabel2.setVisible(false);
/*  528 */       this.upCBar2.setVisible(false);
/*  529 */       this.upCField2.setVisible(false);
/*  530 */       this.subjlabel.setText("Subject to : x1≤".concat(String.valueOf(String.valueOf(this.nUpConstraint1))));
/*      */     }
/*  532 */     else if (this.NumVariable.getValue() == 2) {
/*  533 */       this.NumConstraint.setValue(2);
/*  534 */       this.NumConstraint.setEnabled(true);
/*  535 */       setNumConstraint();
/*  536 */       this.upCLabel2.setVisible(true);
/*  537 */       this.upCBar2.setVisible(true);
/*  538 */       this.upCField2.setVisible(true);
/*  539 */       this.subjlabel.setText(String.valueOf(String.valueOf(new StringBuffer("Subject to : x1≤").append(this.nUpConstraint1).append("   x2").append('≤').append(this.nUpConstraint2))));
/*      */     }
/*      */     
/*  542 */     if (oldv != this.nVariable) {
/*  543 */       this.NumVariable.setValue(this.nVariable);
/*      */       
/*  545 */       this.objectivePanel.removeAll();
/*  546 */       this.objectivePanel.add(this.maxmin);
/*  547 */       this.objectivePanel.add(new JLabel(" f(x) =  "));
/*  548 */       for (int i = 0; i < this.nTerm; i++) {
/*  549 */         if (i > 0) this.objectivePanel.add(new JLabel(" + "));
/*  550 */         this.objectivePanel.add(this.fxcoef[i]);
/*  551 */         for (int j = 0; j < this.nVariable; j++) {
/*  552 */           this.objectivePanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("X").append(j + 1)))));
/*  553 */           this.objectivePanel.add(this.fxexp[i][j]);
/*      */         }
/*      */       }
/*      */       
/*  557 */       for (i = 0; i < this.nConstraint; i++) {
/*  558 */         this.linear_equations[i].setNumVariable(this.nVariable);
/*      */       }
/*      */       
/*  561 */       String str = " and    ";
/*  562 */       for (i = 0; i < this.nVariable; i++) {
/*  563 */         str = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(str))).append("x").append(i + 1).append('≥').append("0 .    ")));
/*      */       }
/*  565 */       this.boundlabel.setText(str);
/*      */       
/*  567 */       revalidate();
/*  568 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setNumConstraint()
/*      */   {
/*  577 */     int oldv = this.nConstraint;
/*  578 */     if ((this.NumConstraint.getValue() <= 2) && (this.NumConstraint.getValue() >= 0)) {
/*  579 */       this.nConstraint = this.NumConstraint.getValue();
/*      */     }
/*      */     else {
/*  582 */       this.NumConstraint.setValue(oldv);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  587 */     if (this.NumVariable.getValue() == 1) {
/*  588 */       this.subjlabel.setText("Subject to : x1≤".concat(String.valueOf(String.valueOf(this.nUpConstraint1))));
/*      */ 
/*      */     }
/*  591 */     else if (this.NumVariable.getValue() == 2) {
/*  592 */       this.subjlabel.setText(String.valueOf(String.valueOf(new StringBuffer("Subject to : x1≤").append(this.nUpConstraint1).append("   x2").append('≤').append(this.nUpConstraint2))));
/*      */     }
/*      */     
/*  595 */     if (oldv != this.nConstraint) {
/*  596 */       this.NumConstraint.setValue(this.nConstraint);
/*      */       
/*  598 */       if (oldv < this.nConstraint) {
/*  599 */         this.constraintPanel.remove(this.boundlabel);
/*  600 */         for (int i = oldv; i < this.nConstraint; i++) {
/*  601 */           this.linear_equations[i] = new IORLinearEquation(this.nVariable);
/*  602 */           this.linear_equations[i].setMaximumSize(new Dimension(420, 50));
/*  603 */           this.linear_equations[i].setAlignmentX(0.0F);
/*  604 */           this.constraintPanel.add(this.linear_equations[i]);
/*      */         }
/*  606 */         this.constraintPanel.add(this.boundlabel);
/*      */       }
/*      */       else {
/*  609 */         for (int i = this.nConstraint; i < oldv; i++) {
/*  610 */           this.constraintPanel.remove(this.linear_equations[i]);
/*  611 */           this.linear_equations[i] = null;
/*      */         }
/*      */       }
/*  614 */       revalidate();
/*  615 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setNumTerm()
/*      */   {
/*  624 */     int oldv = this.nTerm;
/*  625 */     if ((this.NumTerm.getValue() <= 10) && (this.NumTerm.getValue() > 0)) {
/*  626 */       this.nTerm = this.NumTerm.getValue();
/*      */     }
/*      */     else {
/*  629 */       this.NumTerm.setValue(oldv);
/*      */     }
/*  631 */     if (oldv != this.nTerm) {
/*  632 */       this.NumTerm.setValue(this.nTerm);
/*      */       
/*  634 */       this.fxcoef = new DecimalField[this.nTerm];
/*  635 */       for (int i = 0; i < this.nTerm; i++) {
/*  636 */         this.fxcoef[i] = new DecimalField(0.0D, 3, this.decfm);
/*      */       }
/*      */       
/*  639 */       this.fxexp = new WholeNumberField[this.nTerm][2];
/*  640 */       for (i = 0; i < this.nTerm; i++) {
/*  641 */         for (int j = 0; j < 2; j++) {
/*  642 */           this.fxexp[i][j] = new WholeNumberField(0, 1);
/*      */         }
/*      */       }
/*      */       
/*  646 */       this.objectivePanel.removeAll();
/*  647 */       this.objectivePanel.add(this.maxmin);
/*  648 */       this.objectivePanel.add(new JLabel(" f(x) =  "));
/*  649 */       for (i = 0; i < this.nTerm; i++) {
/*  650 */         if (i > 0) this.objectivePanel.add(new JLabel(" + "));
/*  651 */         this.objectivePanel.add(this.fxcoef[i]);
/*  652 */         for (int j = 0; j < this.nVariable; j++) {
/*  653 */           this.objectivePanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  654 */           this.objectivePanel.add(this.fxexp[i][j]);
/*      */         }
/*      */       }
/*  657 */       revalidate();
/*  658 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildGradPanel()
/*      */   {
/*  667 */     this.dfx1coef = new DecimalField[this.nTerm];
/*  668 */     this.dfx2coef = new DecimalField[this.nTerm];
/*  669 */     this.dfx3coef = new DecimalField[this.nTerm];
/*      */     
/*  671 */     this.dfx1_xexp = new WholeNumberField[this.nTerm][2];
/*  672 */     this.dfx2_xexp = new WholeNumberField[this.nTerm][2];
/*  673 */     this.dfx3_xexp = new WholeNumberField[this.nTerm][2];
/*      */     
/*  675 */     for (int i = 0; i < this.nTerm; i++) {
/*  676 */       this.dfx1coef[i] = new DecimalField(0.0D, 3, this.decfm);
/*  677 */       for (int j = 0; j < 2; j++) {
/*  678 */         this.dfx1_xexp[i][j] = new WholeNumberField(0, 1);
/*      */       }
/*      */     }
/*  681 */     if (this.nVariable >= 2) {
/*  682 */       for (i = 0; i < this.nTerm; i++) {
/*  683 */         this.dfx2coef[i] = new DecimalField(0.0D, 3, this.decfm);
/*  684 */         for (int j = 0; j < 2; j++) {
/*  685 */           this.dfx2_xexp[i][j] = new WholeNumberField(0, 1);
/*      */         }
/*      */       }
/*      */     }
/*  689 */     if (this.nVariable == 3) {
/*  690 */       for (i = 0; i < this.nTerm; i++) {
/*  691 */         this.dfx3coef[i] = new DecimalField(0.0D, 3, this.decfm);
/*  692 */         for (int j = 0; j < 2; j++) {
/*  693 */           this.dfx3_xexp[i][j] = new WholeNumberField(0, 1);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  698 */     this.gradPanel.removeAll();
/*      */     
/*  700 */     if (this.controller.nldata.is_max) {
/*  701 */       this.fname = "Max  f(X) = ";
/*      */     } else {
/*  703 */       this.fname = "Min  f(X) = ";
/*      */     }
/*  705 */     this.fexPanel.setAlignmentX(0.0F);
/*      */     
/*  707 */     this.expPanel = Function2Panel(this.nVariable, this.nTerm, this.controller.nldata.fw_objective.fxcoef, this.controller.nldata.fw_objective.xexp);
/*  708 */     this.fexPanel.removeAll();
/*  709 */     this.fexPanel.add(new JLabel(this.fname));
/*  710 */     this.fexPanel.add(this.expPanel);
/*      */     
/*  712 */     JPanel titlePanel = new JPanel();
/*  713 */     titlePanel.setLayout(new BoxLayout(titlePanel, 1));
/*  714 */     titlePanel.add(new JLabel("Objective Function : "));
/*  715 */     titlePanel.add(Box.createVerticalStrut(5));
/*  716 */     titlePanel.add(this.fexPanel);
/*  717 */     titlePanel.add(Box.createVerticalStrut(10));
/*  718 */     titlePanel.add(new JLabel("Derivatives of the Objective function : "));
/*  719 */     titlePanel.add(Box.createVerticalStrut(10));
/*  720 */     this.gradPanel.add(titlePanel);
/*      */     
/*  722 */     this.dfxPanel[0] = new JPanel();
/*  723 */     this.dfxPanel[0].setAlignmentX(0.0F);
/*  724 */     this.dfxPanel[0].setPreferredSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  725 */     this.dfxPanel[0].setMaximumSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  726 */     this.dfxPanel[0].add(new JLabel("df(X)/dx1 =  "));
/*  727 */     for (i = 0; i < this.nTerm; i++) {
/*  728 */       if (i > 0) this.dfxPanel[0].add(new JLabel(" + "));
/*  729 */       this.dfxPanel[0].add(this.dfx1coef[i]);
/*  730 */       for (int j = 0; j < this.nVariable; j++) {
/*  731 */         this.dfxPanel[0].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  732 */         this.dfxPanel[0].add(this.dfx1_xexp[i][j]);
/*      */       }
/*      */     }
/*  735 */     this.gradPanel.add(this.dfxPanel[0]);
/*      */     
/*  737 */     if (this.nVariable >= 2) {
/*  738 */       this.dfxPanel[1] = new JPanel();
/*  739 */       this.dfxPanel[1].setAlignmentX(0.0F);
/*  740 */       this.dfxPanel[1].setPreferredSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  741 */       this.dfxPanel[1].setMaximumSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  742 */       this.dfxPanel[1].add(new JLabel("df(X)/dx2 =  "));
/*  743 */       for (i = 0; i < this.nTerm; i++) {
/*  744 */         if (i > 0) this.dfxPanel[1].add(new JLabel(" + "));
/*  745 */         this.dfxPanel[1].add(this.dfx2coef[i]);
/*  746 */         for (int j = 0; j < this.nVariable; j++) {
/*  747 */           this.dfxPanel[1].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  748 */           this.dfxPanel[1].add(this.dfx2_xexp[i][j]);
/*      */         }
/*      */       }
/*  751 */       this.gradPanel.add(this.dfxPanel[1]);
/*      */     }
/*      */     
/*  754 */     if (this.nVariable == 3) {
/*  755 */       this.dfxPanel[2] = new JPanel();
/*  756 */       this.dfxPanel[2].setAlignmentX(0.0F);
/*  757 */       this.dfxPanel[2].setPreferredSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  758 */       this.dfxPanel[2].setMaximumSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  759 */       this.dfxPanel[2].add(new JLabel("df(X)/dx3 =  "));
/*  760 */       for (i = 0; i < this.nTerm; i++) {
/*  761 */         if (i > 0) this.dfxPanel[2].add(new JLabel(" + "));
/*  762 */         this.dfxPanel[2].add(this.dfx3coef[i]);
/*  763 */         for (int j = 0; j < this.nVariable; j++) {
/*  764 */           this.dfxPanel[2].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  765 */           this.dfxPanel[2].add(this.dfx3_xexp[i][j]);
/*      */         }
/*      */       }
/*  768 */       this.gradPanel.add(this.dfxPanel[2]);
/*      */     }
/*      */     
/*  771 */     this.trialPanel.removeAll();
/*  772 */     this.trialPanel.setAlignmentX(0.0F);
/*  773 */     this.trialPanel.add(new JLabel("Initial Trial Solution :   "));
/*  774 */     switch (this.nVariable) {
/*      */     case 1: 
/*  776 */       this.xname = "( x1 ) =  ";
/*  777 */       break;
/*      */     case 2: 
/*  779 */       this.xname = "( x1 , x2 ) =  ";
/*  780 */       break;
/*      */     case 3: 
/*  782 */       this.xname = "( x1 , x2 , x3 ) =  ";
/*  783 */       break;
/*      */     default: 
/*  785 */       this.xname = " xname ";
/*  786 */       System.out.println("num variable error");
/*      */     }
/*      */     
/*  789 */     this.trialPanel.add(new JLabel(this.xname));
/*  790 */     this.trialPanel.add(new JLabel("( "));
/*  791 */     for (i = 0; i < this.nVariable; i++) {
/*  792 */       this.trialPanel.add(this.inixfd[i]);
/*  793 */       if (i < this.nVariable - 1)
/*  794 */         this.trialPanel.add(new JLabel(" , "));
/*      */     }
/*  796 */     this.trialPanel.add(new JLabel(" )"));
/*      */     
/*  798 */     this.gradPanel.add(Box.createVerticalStrut(20));
/*  799 */     this.gradPanel.add(this.trialPanel);
/*      */   }
/*      */   
/*      */ 
/*      */   private void BuildLpfuncPanel()
/*      */   {
/*  805 */     this.lpfuncPanel.removeAll();
/*  806 */     this.lpfuncPanel.add(new JLabel("Objective Function : "));
/*  807 */     this.lpfuncPanel.add(this.fexPanel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private JPanel Function2Panel(int numvar, int numtm, double[] coef, int[][] expnt)
/*      */   {
/*  817 */     JPanel rtPanel = new JPanel();
/*      */     
/*      */ 
/*  820 */     rtPanel.setAlignmentX(0.0F);
/*  821 */     rtPanel.setLayout(new FlowLayout(0));
/*      */     
/*  823 */     for (int i = 0; i < numtm; i++) {
/*  824 */       if ((i > 0) && (coef[i] > 0.01D)) {
/*  825 */         rtPanel.add(new JLabel(" + "));
/*      */       }
/*      */       else
/*  828 */         rtPanel.add(new JLabel("  "));
/*  829 */       if (Math.abs(coef[i]) > 0.01D) {
/*  830 */         rtPanel.add(new JLabel(this.decfm.format(coef[i])));
/*  831 */         for (int j = 0; j < numvar; j++) {
/*  832 */           if (expnt[i][j] > 1) {
/*  833 */             JLabel xlabel = new JLabel("x");
/*  834 */             xlabel.setFont(new Font("Default", 1, 14));
/*  835 */             rtPanel.add(new ScriptWriter(xlabel, "".concat(String.valueOf(String.valueOf(expnt[i][j]))), String.valueOf(String.valueOf(new StringBuffer("").append(j + 1)))));
/*      */           }
/*  837 */           else if (expnt[i][j] == 1) {
/*  838 */             JLabel xlabel = new JLabel("x");
/*  839 */             xlabel.setFont(new Font("Default", 1, 14));
/*  840 */             rtPanel.add(new ScriptWriter(xlabel, " ", String.valueOf(String.valueOf(new StringBuffer("").append(j + 1)))));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  845 */     return rtPanel;
/*      */   }
/*      */   
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
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/*  873 */     String str = "Interactive Frank-Wolfe Algorithm\n\n";
/*  874 */     str = String.valueOf(String.valueOf(str)).concat("After entering a linearly constrained convex programming model (see Secs. 13.3 and 13.9), ");
/*  875 */     str = String.valueOf(String.valueOf(str)).concat("with a maximum of three variables (all with nonnegativity constraints) and six functional ");
/*  876 */     str = String.valueOf(String.valueOf(str)).concat("constraints, this routine (approximately) solve the linearly constrained convex programming ");
/*  877 */     str = String.valueOf(String.valueOf(str)).concat("problem by using the Frank-Wolfe algorithm as presented in Sec. 13.9. You need to specify a ");
/*  878 */     str = String.valueOf(String.valueOf(str)).concat("feasible initial trial solution and the partial derivatives of the objective function. ");
/*  879 */     str = String.valueOf(String.valueOf(str)).concat("For Part 1 of each iteration, you identify the linear approximation of the objective ");
/*  880 */     str = String.valueOf(String.valueOf(str)).concat("function by using the gradient calculated by the computer at the current trial solution. ");
/*  881 */     str = String.valueOf(String.valueOf(str)).concat("The computer then performs Part 2 by using the simplex method to solve the resulting ");
/*  882 */     str = String.valueOf(String.valueOf(str)).concat("linear programming problem. You and the computer next perform Part3 interactively to find ");
/*  883 */     str = String.valueOf(String.valueOf(str)).concat("the new trial solution. \n\n");
/*  884 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of variables\n");
/*  885 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of variables in the model as a positive integer (maximum of three).\n\n ");
/*  886 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of functional constraints\n");
/*  887 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of functional constraints (excluding nonnegativity constraints) in ");
/*  888 */     str = String.valueOf(String.valueOf(str)).concat("the model as a positive integer (maximum of six).\n\n");
/*  889 */     str = String.valueOf(String.valueOf(str)).concat("Selecting the objective\n");
/*  890 */     str = String.valueOf(String.valueOf(str)).concat("Select either Max or Min, depending on whether the objective is to maximize or minimize Z.\n\n");
/*  891 */     str = String.valueOf(String.valueOf(str)).concat("Entering the coefficient of a variable in a functional constraint and the type of functional constraint\n");
/*  892 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number as an integer or a decimal number. Entering these coefficients ");
/*  893 */     str = String.valueOf(String.valueOf(str)).concat("(and right-hand sides) completes the entering of the model (since nonnegativity constraints ");
/*  894 */     str = String.valueOf(String.valueOf(str)).concat("are assumed for the variables).\n");
/*  895 */     str = String.valueOf(String.valueOf(str)).concat("Select ≤, =, or ≥ , depending on the type of constraint, and then press the NEXT button. ");
/*  896 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/*  898 */     this.help_content_step.setText(str);
/*  899 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/*  906 */     String str = "Interactive Frank-Wolfe Algorithm\n\n";
/*  907 */     str = String.valueOf(String.valueOf(str)).concat("This routine enables you to quickly enter a linearly constrained conveys programming ");
/*  908 */     str = String.valueOf(String.valueOf(str)).concat("problem (see Secs. 13.3 and 13.9) and then interactively execute the Frank-Wolfe ");
/*  909 */     str = String.valueOf(String.valueOf(str)).concat("Algorithm presented in Sec. 13.9. Your role is to apply the logic of the algorithm, ");
/*  910 */     str = String.valueOf(String.valueOf(str)).concat("and the computer will do the number crunching. The computer also will inform you if you ");
/*  911 */     str = String.valueOf(String.valueOf(str)).concat("make a mistake on the first iteration.\n");
/*  912 */     str = String.valueOf(String.valueOf(str)).concat("This routine can handle up to 3 variables (all with nonnegativity constraints) and 6 ");
/*  913 */     str = String.valueOf(String.valueOf(str)).concat("functional constraints, which encompasses all relevant problems at the end of Chap. 13.\n");
/*  914 */     str = String.valueOf(String.valueOf(str)).concat("When you finish a problem, you can print out all your work by choosing Print under the ");
/*  915 */     str = String.valueOf(String.valueOf(str)).concat("File menu. If you are interrupted before you finish, you can save your work (choose ");
/*  916 */     str = String.valueOf(String.valueOf(str)).concat("Save under the File menu) and return later (choose Open).");
/*  917 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/*  919 */     this.help_content_procedure.setText(str);
/*  920 */     this.help_content_procedure.revalidate(); }
/*      */   
/*  922 */   private String str0 = "Interactive Frank-Wolfe Algorithm\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  933 */   private String str1 = "Interactive Frank-Wolfe Algorithm\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  958 */   private String str2 = "Entering the derivative of the objective function\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  967 */   private String str3 = "Entering the linear objective function\n";
/*      */   
/*      */ 
/*      */ 
/*  971 */   private String str4 = "To continue\n";
/*      */   
/*  973 */   private String str5 = "To continue\n";
/*      */   
/*  975 */   private String str6 = "Entering the new trial solution\n";
/*      */   
/*      */ 
/*  978 */   private String str7 = "Entering the a's and the b's\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  985 */   private String str8 = "To continue\n";
/*      */   
/*  987 */   private String str9 = "To continue\n";
/*      */   
/*  989 */   private String str10 = "To continue\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void updateStepHelpPanel()
/*      */   {
/*  996 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/*  997 */     switch (this.step) {
/*      */     case 1: 
/*  999 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str1)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1037 */       break;
/*      */     case 2: 
/* 1002 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str2)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1037 */       break;
/*      */     case 3: 
/* 1005 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str3)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1037 */       break;
/*      */     case 4: 
/* 1008 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str4)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1037 */       break;
/*      */     case 5: 
/* 1011 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str5)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1037 */       break;
/*      */     case 6: 
/* 1014 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str6)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1037 */       break;
/*      */     case 7: 
/* 1017 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str7)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1037 */       break;
/*      */     case 8: 
/* 1020 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str8)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1037 */       break;
/*      */     case 9: 
/* 1023 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str9)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1037 */       break;
/*      */     case 10: 
/* 1026 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str10)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1037 */       break;
/*      */     default: 
/* 1029 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
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
/*      */ 
/*      */ 
/*      */   private void doBack()
/*      */   {
/* 1045 */     if (!this.opseq.isEmpty()) {
/* 1046 */       int last = ((Integer)this.opseq.lastElement()).intValue();
/* 1047 */       this.opseq.removeElementAt(this.opseq.size() - 1);
/*      */       
/* 1049 */       switch (last)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       case 0: 
/* 1057 */         this.mainPanel.removeAll();
/* 1058 */         this.mainPanel.add(this.setupPanel);
/* 1059 */         this.mainPanel.add(this.buttPanel);
/* 1060 */         this.backbutt.setEnabled(false);
/* 1061 */         this.controller.solver.pop = null;
/* 1062 */         this.step = 0;
/* 1063 */         revalidate();
/* 1064 */         repaint();
/* 1065 */         break;
/*      */       
/*      */       case 1: 
/* 1068 */         this.controller.solver.getype.currentindex = 0;
/* 1069 */         this.gepanel.tableRow = 0;
/* 1070 */         this.gepanel.tableRow1 = 0;
/* 1071 */         this.gepanel.tableRow2 = 0;
/* 1072 */         this.gepanel.setColor();
/* 1073 */         this.mainPanel.removeAll();
/* 1074 */         this.mainPanel.add(this.lpfuncPanel);
/* 1075 */         this.mainPanel.add(this.gepanel);
/* 1076 */         this.mainPanel.add(this.buttPanel);
/* 1077 */         revalidate();
/* 1078 */         repaint();
/* 1079 */         this.countresult = 0;
/* 1080 */         this.step = 1;
/* 1081 */         break;
/*      */       case 2: 
/* 1083 */         Vector par = new Vector();
/* 1084 */         this.opr = new IOROperation(50702, par);
/* 1085 */         this.controller.solver.getype.currentindex = (this.opseq.size() - 1);
/* 1086 */         this.controller.solver.getData(this.opr, this.controller.nldata);
/* 1087 */         this.gepanel.tableRow = this.controller.solver.getype.currentindex;
/*      */         
/* 1089 */         this.gepanel.setColor();
/* 1090 */         this.mainPanel.removeAll();
/* 1091 */         this.mainPanel.add(this.lpfuncPanel);
/* 1092 */         this.mainPanel.add(this.gepanel);
/* 1093 */         this.mainPanel.add(this.buttPanel);
/* 1094 */         revalidate();
/* 1095 */         repaint();
/* 1096 */         if (this.countresult >= 0) {
/* 1097 */           this.countresult -= 1;
/* 1098 */           System.out.println("countresult:".concat(String.valueOf(String.valueOf(this.countresult))));
/*      */         }
/* 1100 */         if (this.countresult == 0) {
/* 1101 */           this.fitness = 0.0D;
/*      */         }
/*      */         
/*      */         break;
/*      */       default: 
/* 1106 */         System.out.println("Genetic cannot back here");break; }
/*      */       
/*      */     }
/* 1109 */     this.gepanel.countCurrentSolution();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void doNext()
/*      */   {
/* 1116 */     int[] num = new int[5];int[] opert = new int[this.nConstraint];
/* 1117 */     double[] objcoef = new double[this.nTerm];double[] grad1coef = new double[this.nTerm];
/* 1118 */     double[] grad2coef = new double[this.nTerm];double[] grad3coef = new double[this.nTerm];
/* 1119 */     int[][] objexp = new int[this.nTerm][3];int[][] grad1exp = new int[this.nTerm][3];
/* 1120 */     int[][] grad2exp = new int[this.nTerm][3];int[][] grad3exp = new int[this.nTerm][3];
/* 1121 */     double[] inisolu = new double[3];double[] lpobjcoef = new double[3];
/* 1122 */     double[] aent = new double[3];double[] bent = new double[3];double[] cent = new double[3];
/* 1123 */     double[][] contcoef = new double[this.nConstraint][this.nVariable + 1];
/*      */     
/*      */ 
/*      */ 
/* 1127 */     switch (this.step) {
/*      */     case 0: 
/* 1129 */       this.par = new Vector();
/* 1130 */       this.opr = new IOROperation(1, this.par);
/*      */       
/*      */ 
/* 1133 */       num[0] = this.nVariable;
/* 1134 */       num[1] = this.nConstraint;
/* 1135 */       num[2] = this.nTerm;
/* 1136 */       num[3] = this.nUpConstraint1;
/* 1137 */       num[4] = this.nUpConstraint2;
/*      */       
/* 1139 */       this.par.addElement(num);
/*      */       
/*      */ 
/* 1142 */       if (this.maxmin.getSelectedIndex() == 0) {
/* 1143 */         this.par.addElement(new Boolean(true));
/*      */       } else {
/* 1145 */         this.par.addElement(new Boolean(false));
/*      */       }
/*      */       
/* 1148 */       for (int i = 0; i < this.nTerm; i++) {
/* 1149 */         objcoef[i] = this.fxcoef[i].getValue();
/* 1150 */         for (int j = 0; j < this.nVariable; j++) {
/* 1151 */           objexp[i][j] = this.fxexp[i][j].getValue();
/*      */         }
/*      */       }
/* 1154 */       this.par.addElement(objcoef);
/* 1155 */       this.par.addElement(objexp);
/*      */       
/*      */ 
/* 1158 */       for (i = 0; i < this.nConstraint; i++) {
/* 1159 */         opert[i] = this.linear_equations[i].getOperator();
/* 1160 */         this.linear_equations[i].getCoefficient(contcoef[i]);
/*      */       }
/* 1162 */       this.par.addElement(contcoef);
/* 1163 */       this.par.addElement(opert);
/*      */       
/* 1165 */       this.opr.parameters = this.par;
/*      */       
/*      */ 
/* 1168 */       this.opr.operation_code = 50701;
/* 1169 */       if (this.controller.solver.doWork(this.opr, this.controller.nldata) == false) {
/* 1170 */         String err = this.controller.solver.getErrInfo();
/*      */         
/* 1172 */         JOptionPane.showMessageDialog(this, "Not a feasible solution. Try again!");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1177 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/* 1179 */         BuildGradPanel();
/* 1180 */         this.mainPanel.removeAll();
/* 1181 */         BuildLpfuncPanel();
/* 1182 */         this.mainPanel.add(this.lpfuncPanel);
/*      */         
/* 1184 */         this.gepanel.tableRow1 = 0;
/* 1185 */         this.gepanel.tableRow2 = 0;
/* 1186 */         this.gepanel.tableRow = 0;
/* 1187 */         this.mainPanel.add(this.gepanel);
/*      */         
/*      */ 
/* 1190 */         this.mainPanel.add(this.buttPanel);
/* 1191 */         this.backbutt.setEnabled(true);
/* 1192 */         this.step = 1;
/* 1193 */         revalidate();
/* 1194 */         repaint(); }
/* 1195 */       break;
/*      */     case 1: 
/* 1197 */       this.opr.operation_code = 50702;
/* 1198 */       if (this.controller.solver.doWork(this.opr, this.controller.nldata) == false) {
/* 1199 */         String err = this.controller.solver.getErrInfo();
/* 1200 */         JOptionPane.showMessageDialog(this, err);
/*      */       }
/*      */       else
/*      */       {
/* 1204 */         System.out.println(this.controller.nldata.best[0].getFitness());
/*      */         
/*      */ 
/*      */ 
/* 1208 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/* 1210 */         BuildGradPanel();
/* 1211 */         this.mainPanel.removeAll();
/* 1212 */         BuildLpfuncPanel();
/* 1213 */         this.mainPanel.add(this.lpfuncPanel);
/* 1214 */         this.gepanel.tableRow = this.controller.solver.getype.currentindex;
/*      */         
/* 1216 */         this.gepanel.tableRow1 = 10;
/* 1217 */         this.gepanel.tableRow2 = 6;
/* 1218 */         this.gepanel.setColor();
/* 1219 */         this.mainPanel.add(this.gepanel);
/*      */         
/*      */ 
/* 1222 */         this.mainPanel.add(this.buttPanel);
/* 1223 */         this.backbutt.setEnabled(true);
/* 1224 */         this.step = 2;
/* 1225 */         revalidate();
/* 1226 */         repaint(); }
/* 1227 */       break;
/*      */     
/*      */     case 2: 
/* 1230 */       double tempfitness = this.controller.nldata.best[(this.controller.solver.getype.currentindex - 1)].getFitness();
/*      */       
/* 1232 */       if (Math.abs(this.fitness - tempfitness) < 0.001D) {
/* 1233 */         this.countresult += 1;
/*      */       }
/*      */       else {
/* 1236 */         this.countresult = 1;
/* 1237 */         this.fitness = tempfitness;
/*      */       }
/*      */       
/* 1240 */       if (this.countresult >= 6) {
/* 1241 */         this.countresult = 5;
/*      */         
/* 1243 */         JOptionPane.showMessageDialog(this, "The algorithm is now finished because of 5 consecutive iterations without any improvement!");
/*      */ 
/*      */ 
/*      */       }
/* 1247 */       else if (this.controller.solver.doWork(this.opr, this.controller.nldata) == false) {
/* 1248 */         String err = this.controller.solver.getErrInfo();
/* 1249 */         JOptionPane.showMessageDialog(this, err);
/*      */       }
/*      */       else
/*      */       {
/* 1253 */         this.opseq.addElement(new Integer(this.step));
/* 1254 */         this.gepanel.tableRow = this.controller.solver.getype.currentindex;
/* 1255 */         this.gepanel.setColor();
/* 1256 */         this.gepanel.tableRow1 = 10;
/* 1257 */         this.gepanel.tableRow2 = 6;
/*      */         
/* 1259 */         this.mainPanel.removeAll();
/* 1260 */         this.mainPanel.add(this.lpfuncPanel);
/* 1261 */         this.mainPanel.add(this.gepanel);
/* 1262 */         this.mainPanel.add(this.buttPanel);
/* 1263 */         revalidate();
/* 1264 */         repaint();
/*      */       }
/*      */       
/* 1267 */       break;
/*      */     
/*      */     default: 
/* 1270 */       System.out.println("genetic cannot next here !");
/*      */     }
/* 1272 */     this.gepanel.scrollShow = true;
/*      */     
/* 1274 */     this.gepanel.countCurrentSolution();
/*      */     
/* 1276 */     System.out.println("countresult = ".concat(String.valueOf(String.valueOf(this.countresult))));
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
/* 1287 */     int[] para = new int[7];
/*      */     try
/*      */     {
/* 1290 */       this.state = ((IORState)in.readObject());
/* 1291 */       para = (int[])in.readObject();
/* 1292 */       this.opseq = ((Vector)in.readObject());
/* 1293 */       this.fitness = in.readDouble();
/* 1294 */       this.state.setSolverStepVector();
/* 1295 */       in.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1298 */       e1.printStackTrace();
/* 1299 */       System.out.println("aOpen fails");
/*      */     }
/*      */     
/* 1302 */     this.step = para[0];
/* 1303 */     this.nTerm = para[1];
/* 1304 */     this.nVariable = para[2];
/* 1305 */     this.nConstraint = para[3];
/* 1306 */     this.nUpConstraint1 = para[4];
/* 1307 */     this.nUpConstraint2 = para[5];
/* 1308 */     this.countresult = para[6];
/* 1309 */     this.NumTerm.setValue(this.nTerm);
/* 1310 */     this.NumVariable.setValue(this.nVariable);
/* 1311 */     this.NumConstraint.setValue(this.nConstraint);
/* 1312 */     this.upCField1.setValue(this.nUpConstraint1);
/* 1313 */     this.upCField2.setValue(this.nUpConstraint2);
/*      */     
/* 1315 */     BuildSetupPanel();
/* 1316 */     setNumConstraint();
/* 1317 */     setNumVariable();
/* 1318 */     setNumTerm();
/*      */     
/* 1320 */     for (int i = 0; i < this.nTerm; i++) {
/* 1321 */       this.fxcoef[i].setValue(this.controller.nldata.fw_objective.fxcoef[i]);
/* 1322 */       for (int j = 0; j < this.nVariable; j++) {
/* 1323 */         this.fxexp[i][j].setValue(this.controller.nldata.fw_objective.xexp[i][j]);
/*      */       }
/*      */     }
/* 1326 */     for (i = 0; i < this.nConstraint; i++) {
/* 1327 */       if (this.controller.nldata.fw_constraint_coef != null)
/* 1328 */         this.linear_equations[i].setCoefficient(this.controller.nldata.fw_constraint_coef[i]);
/* 1329 */       if (this.controller.nldata.fw_constraint_operator != null) {
/* 1330 */         this.linear_equations[i].setOperator(this.controller.nldata.fw_constraint_operator[i]);
/*      */       }
/*      */     }
/* 1333 */     this.opseq = null;this.opseq = new Vector();
/* 1334 */     if (this.step == 1) {
/* 1335 */       this.step = 0;
/* 1336 */       doNext();
/* 1337 */     } else if (this.step == 2) {
/* 1338 */       int doNum = this.controller.solver.getype.currentindex;
/* 1339 */       this.controller.solver.getype.currentindex = 0;
/* 1340 */       this.countresult = 0;
/* 1341 */       this.step = 0;
/* 1342 */       doNext();
/* 1343 */       this.step = 1;
/* 1344 */       doNext();
/* 1345 */       for (i = 0; i < doNum - 1; i++) {
/* 1346 */         doNext();
/*      */       }
/*      */     }
/* 1349 */     revalidate();
/* 1350 */     repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFile(ObjectOutputStream out)
/*      */   {
/* 1362 */     int[] interpara = new int[7];
/*      */     
/* 1364 */     interpara[0] = this.step;
/* 1365 */     interpara[1] = this.nTerm;
/* 1366 */     interpara[2] = this.nVariable;
/* 1367 */     interpara[3] = this.nConstraint;
/* 1368 */     interpara[4] = this.nUpConstraint1;
/* 1369 */     interpara[5] = this.nUpConstraint2;
/* 1370 */     interpara[6] = this.countresult;
/* 1371 */     System.out.println("currentindex  ===".concat(String.valueOf(String.valueOf(this.controller.solver.getype.currentindex))));
/*      */     try {
/* 1373 */       out.writeObject(this.state);
/* 1374 */       out.writeObject(interpara);
/* 1375 */       out.writeObject(this.opseq);
/* 1376 */       out.writeDouble(this.fitness);
/* 1377 */       out.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1380 */       e1.printStackTrace();
/* 1381 */       System.out.println("Save fails");
/*      */     }
/*      */     
/* 1384 */     SaveFunction();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFunction()
/*      */   {
/* 1394 */     int[] num = new int[5];int[] opert = new int[this.nConstraint];
/* 1395 */     double[] objcoef = new double[this.nTerm];double[] grad1coef = new double[this.nTerm];
/* 1396 */     double[] grad2coef = new double[this.nTerm];double[] grad3coef = new double[this.nTerm];
/* 1397 */     int[][] objexp = new int[this.nTerm][3];int[][] grad1exp = new int[this.nTerm][3];
/* 1398 */     int[][] grad2exp = new int[this.nTerm][3];int[][] grad3exp = new int[this.nTerm][3];
/* 1399 */     double[] inisolu = new double[3];
/* 1400 */     double[][] contcoef = new double[this.nConstraint][this.nVariable + 1];
/*      */     
/* 1402 */     Vector par = new Vector();
/* 1403 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/* 1405 */     if (this.step == 0) {
/* 1406 */       num[0] = this.nVariable;
/* 1407 */       num[1] = this.nConstraint;
/* 1408 */       num[2] = this.nTerm;
/* 1409 */       num[3] = this.nUpConstraint1;
/* 1410 */       num[4] = this.nUpConstraint2;
/*      */       
/* 1412 */       par.addElement(num);
/* 1413 */       if (this.maxmin.getSelectedIndex() == 0) {
/* 1414 */         par.addElement(new Boolean(true));
/*      */       } else {
/* 1416 */         par.addElement(new Boolean(false));
/*      */       }
/* 1418 */       for (int i = 0; i < this.nTerm; i++) {
/* 1419 */         objcoef[i] = this.fxcoef[i].getValue();
/* 1420 */         for (int j = 0; j < this.nVariable; j++) {
/* 1421 */           objexp[i][j] = this.fxexp[i][j].getValue();
/*      */         }
/*      */       }
/* 1424 */       par.addElement(objcoef);
/* 1425 */       par.addElement(objexp);
/* 1426 */       for (i = 0; i < this.nConstraint; i++) {
/* 1427 */         opert[i] = this.linear_equations[i].getOperator();
/* 1428 */         this.linear_equations[i].getCoefficient(contcoef[i]);
/*      */       }
/* 1430 */       par.addElement(contcoef);
/* 1431 */       par.addElement(opert);
/* 1432 */       opr.parameters = par;
/* 1433 */       opr.operation_code = 50701;
/* 1434 */       this.controller.solver.doWork(opr, this.controller.nldata);
/*      */     }
/* 1436 */     System.out.println("countresult 1 = =".concat(String.valueOf(String.valueOf(this.countresult))));
/*      */   }
/*      */   
/*      */   class IORFWTableModel extends AbstractTableModel {
/*      */     private int i;
/*      */     private int j;
/*      */     private int tablerow;
/*      */     
/*      */     IORFWTableModel() {}
/*      */     
/* 1446 */     public void setTableRow(int n) { this.tablerow = n; }
/*      */     
/*      */     public int getColumnCount()
/*      */     {
/* 1450 */       return 6;
/*      */     }
/*      */     
/*      */     public int getRowCount() {
/* 1454 */       return this.tablerow;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col) {
/* 1458 */       switch (col) {
/*      */       case 0: 
/* 1460 */         return "Iteration";
/*      */       case 1: 
/* 1462 */         return "T";
/*      */       case 2: 
/* 1464 */         return "Trial Solution";
/*      */       case 3: 
/* 1466 */         return "f(x)";
/*      */       case 4: 
/* 1468 */         return "Probalility";
/*      */       case 5: 
/* 1470 */         return "Accept?";
/*      */       }
/* 1472 */       return "err";
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col)
/*      */     {
/* 1477 */       String str = new String();
/* 1478 */       String[] astr = new String[3];String[] bstr = new String[3];
/* 1479 */       DecimalFormat decfm = new DecimalFormat("#.###");
/* 1480 */       switch (col) {
/*      */       case 0: 
/* 1482 */         return new Integer(row + 1).toString();
/*      */       case 1: 
/* 1484 */         return decfm.format(IORNLGeneticPanel.this.controller.nldata.Temp[row]);
/*      */       
/*      */       case 2: 
/* 1487 */         String xstr = "( ";
/* 1488 */         for (this.i = 0; this.i < IORNLGeneticPanel.this.nVariable; this.i += 1) {
/* 1489 */           xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(decfm.format(IORNLGeneticPanel.this.controller.nldata.variable[row][this.i]))));
/* 1490 */           if (this.i < IORNLGeneticPanel.this.nVariable - 1) {
/* 1491 */             xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*      */           }
/*      */         }
/* 1494 */         xstr = String.valueOf(String.valueOf(xstr)).concat(" )");
/* 1495 */         return xstr;
/*      */       case 3: 
/* 1497 */         return decfm.format(IORNLGeneticPanel.this.controller.nldata.function[row]);
/*      */       case 4: 
/* 1499 */         return decfm.format(IORNLGeneticPanel.this.controller.nldata.probability[row]);
/*      */       
/*      */       case 5: 
/* 1502 */         return "".concat(String.valueOf(String.valueOf(IORNLGeneticPanel.this.controller.nldata.accept[row])));
/*      */       }
/* 1504 */       return "err";
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c)
/*      */     {
/* 1509 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1515 */       return false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNLGeneticPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */