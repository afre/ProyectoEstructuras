/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
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
/*      */ import javax.swing.JScrollBar;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextArea;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.table.DefaultTableCellRenderer;
/*      */ import javax.swing.table.TableColumn;
/*      */ import javax.swing.table.TableColumnModel;
/*      */ 
/*      */ public class IORNLAnnealingPanel extends IORActionPanel
/*      */ {
/*   33 */   private final char LESSTHAN = '≤';
/*   34 */   private final char GREATTHAN = '≥';
/*      */   
/*   36 */   private static final int MAXVARS = 2;
/*   37 */   private static final int MAXCONTS = 2;
/*   38 */   private static final int MAXTERMS = 10;
/*   39 */   private static final int UPBOUND1 = 63;
/*   40 */   private static final int UPBOUND2 = 63;
/*      */   
/*   42 */   private IORTSPProcessController controller = null;
/*      */   
/*   44 */   private JPanel mainPanel = new JPanel();
/*      */   
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
/*   90 */   private IORNLAnnealingPanel.IORFWTableModel fwModel = null;
/*   91 */   private JTable table = null;
/*      */   
/*   93 */   private JButton backbutt = new JButton("Reset");
/*   94 */   private JButton nextbutt = new JButton("Next");
/*   95 */   private JComboBox maxmin = new JComboBox();
/*      */   private DecimalField[] fxcoef;
/*      */   private DecimalField[] dfx1coef;
/*      */   private DecimalField[] dfx2coef;
/*      */   private DecimalField[] dfx3coef;
/*      */   private DecimalField[] inixfd;
/*  101 */   private DecimalField[] afd; private DecimalField[] bfd; private DecimalField[] xlpfd; private DecimalField[] xtafd; private DecimalField[] xtbfd; private DecimalField[] xtcfd; private WholeNumberField[][] fxexp; private WholeNumberField[][] dfx1_xexp; private WholeNumberField[][] dfx2_xexp; private WholeNumberField[][] dfx3_xexp; private WholeNumberField NumTerm = new WholeNumberField(5, 2);
/*  102 */   private WholeNumberField NumVariable = new WholeNumberField(1, 2);
/*  103 */   private WholeNumberField NumConstraint = new WholeNumberField(0, 2);
/*      */   private JLabel upCLabel1;
/*      */   private JLabel upCLabel2;
/*      */   private JLabel initSoluLabX1;
/*      */   private JLabel initSoluLabX2;
/*  108 */   private JTextField initSoluTextX1 = new JTextField("15.5", 3);
/*  109 */   private JTextField initSoluTextX2 = new JTextField("31.5", 3);
/*      */   
/*      */   private Scrollbar upCBar1;
/*      */   private Scrollbar upCBar2;
/*  113 */   private WholeNumberField upCField1 = new WholeNumberField(31, 2);
/*  114 */   private WholeNumberField upCField2 = new WholeNumberField(63, 2);
/*  115 */   private JPanel textP = new JPanel();
/*  116 */   private JPanel fieldP = new JPanel();
/*      */   
/*  118 */   private int step = 1;
/*  119 */   private int itercount = 1;
/*      */   
/*      */ 
/*  122 */   private int nTerm = 5;
/*  123 */   private int nVariable = 1;
/*  124 */   private int nConstraint = 0;
/*      */   
/*  126 */   private int nUpConstraint1 = 31;
/*  127 */   private int nUpConstraint2 = 63;
/*      */   
/*      */ 
/*      */ 
/*  131 */   private JLabel resultLabel = new JLabel();
/*  132 */   private JPanel resultPanel = new JPanel();
/*  133 */   private int dataindex = 0;
/*  134 */   private boolean scrollShow = false;
/*      */   
/*      */ 
/*      */ 
/*  138 */   private DecimalFormat decfm = new DecimalFormat();
/*      */   
/*      */ 
/*  141 */   private DecimalFormat decfmt = new DecimalFormat("0.###");
/*      */   
/*      */ 
/*  144 */   private Vector opseq = new Vector();
/*      */   
/*  146 */   private String xname = ""; private String zname = ""; private String fname = "";
/*  147 */   private String theastr = ""; private String thebstr = ""; private String thexstr = ""; private String thelpstr = ""; private String newxstr = "";
/*  148 */   private String curstr = "Current Trial Solution :";
/*      */   
/*      */ 
/*  151 */   private boolean isFinished = false;
/*      */   
/*      */   private int pos;
/*      */   private double[] bestvarible;
/*      */   private String bestresult;
/*  156 */   private double bSolution = 0.0D;
/*      */   
/*  158 */   private float initSolutionX1 = 0.0F;
/*  159 */   private float initSolutionX2 = 0.0F;
/*      */   
/*  161 */   private boolean isDowork = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public IORNLAnnealingPanel(IORTSPProcessController c)
/*      */   {
/*  168 */     super(c);
/*  169 */     this.controller = c;
/*  170 */     this.state = new IORState(this.controller.solver);
/*  171 */     add(this.mainPanel);
/*  172 */     this.actionStatus.hide();
/*  173 */     this.bn_back.hide();
/*  174 */     this.bn_finish.hide();
/*      */     
/*  176 */     this.decfm.setGroupingUsed(false);
/*  177 */     this.inditext.setEditable(false);
/*      */     
/*      */ 
/*  180 */     this.inixfd = new DecimalField[3];
/*  181 */     this.xlpfd = new DecimalField[3];
/*  182 */     this.afd = new DecimalField[3];
/*  183 */     this.bfd = new DecimalField[3];
/*  184 */     this.xtafd = new DecimalField[3];
/*  185 */     this.xtbfd = new DecimalField[3];
/*  186 */     this.xtcfd = new DecimalField[3];
/*      */     
/*      */ 
/*  189 */     for (int i = 0; i < 2; i++) {
/*  190 */       this.inixfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  191 */       this.xlpfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  192 */       this.afd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  193 */       this.bfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  194 */       this.xtafd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  195 */       this.xtbfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  196 */       this.xtcfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  197 */       this.gradlabel[i] = new JLabel();
/*      */     }
/*      */     
/*  200 */     this.buttPanel.setPreferredSize(new Dimension(300, 40));
/*  201 */     this.buttPanel.setMaximumSize(new Dimension(300, 40));
/*  202 */     this.buttPanel.add(this.backbutt);
/*  203 */     this.buttPanel.add(Box.createHorizontalStrut(10));
/*  204 */     this.buttPanel.add(this.nextbutt);
/*  205 */     this.backbutt.setEnabled(false);
/*      */     
/*  207 */     this.setupPanel = new JPanel();
/*  208 */     this.setupPanel.setLayout(new BoxLayout(this.setupPanel, 1));
/*      */     
/*  210 */     this.numPanel = new JPanel();
/*  211 */     this.numPanel.setPreferredSize(new Dimension(300, 80));
/*  212 */     this.numPanel.setMaximumSize(new Dimension(300, 80));
/*      */     
/*  214 */     this.namePanel.setLayout(new BoxLayout(this.namePanel, 1));
/*  215 */     this.namePanel.add(new JLabel("Number of Variables (1 or 2)"));
/*  216 */     this.namePanel.add(Box.createVerticalStrut(6));
/*  217 */     this.namePanel.add(new JLabel("Number of Constraints (0,1 or 2)"));
/*  218 */     this.namePanel.add(Box.createVerticalStrut(6));
/*  219 */     this.namePanel.add(new JLabel("Number of Terms in f(X) (≤ 10) :"));
/*  220 */     this.fieldPanel.setLayout(new BoxLayout(this.fieldPanel, 1));
/*  221 */     this.fieldPanel.add(this.NumVariable);
/*  222 */     this.fieldPanel.add(Box.createVerticalStrut(4));
/*  223 */     this.fieldPanel.add(this.NumConstraint);
/*  224 */     this.fieldPanel.add(Box.createVerticalStrut(4));
/*  225 */     this.fieldPanel.add(this.NumTerm);
/*  226 */     this.NumConstraint.setEnabled(false);
/*      */     
/*  228 */     this.numPanel.add(this.namePanel);
/*  229 */     this.numPanel.add(Box.createHorizontalStrut(10));
/*  230 */     this.numPanel.add(this.fieldPanel);
/*      */     
/*  232 */     this.maxmin.addItem("MAX");
/*  233 */     this.maxmin.addItem("MIN");
/*      */     
/*  235 */     this.initSoluLabX1 = new JLabel("Initial trial solution of x1 :");
/*  236 */     this.initSoluLabX2 = new JLabel("Initial trial solution of x2 :");
/*      */     
/*  238 */     this.subjlabel = new JLabel("Subject to : x1≤".concat(String.valueOf(String.valueOf(this.nUpConstraint1))));
/*  239 */     this.upCLabel1 = new JLabel("Upper bound constraint of x1 ≤");
/*  240 */     this.upCLabel2 = new JLabel("Upper bound constraint of x2 ≤");
/*  241 */     this.upCBar1 = new Scrollbar(0, 0, 1, 1, this.nUpConstraint1 + 1);
/*  242 */     this.upCBar1.setValue(this.nUpConstraint1);
/*  243 */     this.upCBar2 = new Scrollbar(0, 0, 1, 1, this.nUpConstraint1 + 1);
/*  244 */     this.upCBar2.setValue(this.nUpConstraint2);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  249 */     this.objectivePanel = new JPanel();
/*  250 */     this.resultPanel.setPreferredSize(new Dimension(500, 30));
/*  251 */     this.resultPanel.setMaximumSize(new Dimension(500, 30));
/*  252 */     this.resultPanel.add(this.resultLabel);
/*      */     
/*      */ 
/*  255 */     this.subjlabel.setAlignmentX(0.0F);
/*  256 */     this.boundlabel.setAlignmentX(0.0F);
/*  257 */     this.comtlabel.setAlignmentX(0.5F);
/*  258 */     this.instructionlabel.setAlignmentX(0.5F);
/*  259 */     this.constraintPanel = new JPanel();
/*  260 */     this.constraintPanel.setLayout(new BoxLayout(this.constraintPanel, 1));
/*  261 */     this.constraintPanel.setAlignmentX(0.5F);
/*  262 */     this.boundlabel.setBorder(new EmptyBorder(10, 0, 10, 0));
/*      */     
/*  264 */     this.setupPanel.add(this.numPanel);
/*  265 */     this.setupPanel.add(Box.createVerticalStrut(10));
/*  266 */     this.setupPanel.add(this.comtlabel);
/*  267 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*  268 */     this.setupPanel.add(this.instructionlabel);
/*  269 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*  270 */     this.setupPanel.add(this.objectivePanel);
/*      */     
/*  272 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*  273 */     this.setupPanel.add(this.upCPanel);
/*      */     
/*  275 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*  276 */     this.setupPanel.add(this.constraintPanel);
/*  277 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*      */     
/*  279 */     BuildSetupPanel();
/*      */     
/*  281 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*  282 */     this.mainPanel.setBorder(new EmptyBorder(20, 10, 20, 10));
/*  283 */     this.mainPanel.add(this.setupPanel);
/*  284 */     this.mainPanel.add(this.buttPanel);
/*      */     
/*  286 */     this.gradPanel = new JPanel();
/*  287 */     this.gradPanel.setLayout(new BoxLayout(this.gradPanel, 1));
/*  288 */     this.gradPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  289 */     this.gradPanel.setAlignmentX(0.5F);
/*      */     
/*  291 */     this.lpfuncPanel = new JPanel();
/*  292 */     this.lpfuncPanel.setLayout(new BoxLayout(this.lpfuncPanel, 1));
/*  293 */     this.lpfuncPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  294 */     this.lpfuncPanel.setAlignmentX(0.5F);
/*      */     
/*  296 */     this.upPanel = new JPanel();
/*  297 */     this.upPanel.setLayout(new BoxLayout(this.upPanel, 1));
/*  298 */     this.upPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  299 */     this.upPanel.setAlignmentX(0.5F);
/*      */     
/*  301 */     this.bottomPanel = new JPanel();
/*  302 */     this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel, 1));
/*  303 */     this.bottomPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  304 */     this.bottomPanel.setAlignmentX(0.5F);
/*      */     
/*  306 */     this.xtPanel = new JPanel();
/*  307 */     this.xtPanel.setLayout(new BoxLayout(this.xtPanel, 1));
/*  308 */     this.xtPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  309 */     this.xtPanel.setAlignmentX(0.5F);
/*      */     
/*  311 */     this.newxPanel = new JPanel();
/*  312 */     this.newxPanel.setLayout(new BoxLayout(this.newxPanel, 1));
/*  313 */     this.newxPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  314 */     this.newxPanel.setAlignmentX(0.5F);
/*      */     
/*  316 */     this.abinPanel = new JPanel();
/*  317 */     this.abinPanel.setLayout(new BoxLayout(this.abinPanel, 1));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  323 */     this.fwModel = new IORNLAnnealingPanel.IORFWTableModel();
/*  324 */     this.table = new JTable(this.fwModel);
/*  325 */     this.scrlPane = new JScrollPane(this.table);
/*  326 */     this.table.setCellSelectionEnabled(true);
/*  327 */     this.table.setEnabled(false);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  341 */     this.nextbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  343 */         IORNLAnnealingPanel.this.doNext();
/*      */       }
/*  345 */     });
/*  346 */     this.backbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  348 */         IORNLAnnealingPanel.this.doBack();
/*      */       }
/*      */       
/*  351 */     });
/*  352 */     this.NumTerm.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  354 */         IORNLAnnealingPanel.this.setNumTerm();
/*      */       }
/*      */       
/*  357 */     });
/*  358 */     this.NumVariable.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  360 */         IORNLAnnealingPanel.this.setNumVariable();
/*      */       }
/*      */       
/*  363 */     });
/*  364 */     this.NumConstraint.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  366 */         IORNLAnnealingPanel.this.setNumConstraint();
/*      */       }
/*      */       
/*      */ 
/*  370 */     });
/*  371 */     this.scrlPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
/*      */       public void adjustmentValueChanged(AdjustmentEvent e) {
/*  373 */         if (IORNLAnnealingPanel.this.scrollShow) {
/*  374 */           IORNLAnnealingPanel.this.scrlPane.getVerticalScrollBar().setValue(IORNLAnnealingPanel.this.scrlPane.getVerticalScrollBar().getMaximum());
/*  375 */           IORNLAnnealingPanel.this.scrollShow = false;
/*      */         }
/*      */         
/*      */       }
/*      */       
/*  380 */     });
/*  381 */     this.upCField1.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  383 */         if ((IORNLAnnealingPanel.this.upCField1.getValue() <= 63) && (IORNLAnnealingPanel.this.upCField1.getValue() >= 0)) {
/*  384 */           IORNLAnnealingPanel.this.nUpConstraint1 = IORNLAnnealingPanel.this.upCField1.getValue();
/*      */         }
/*      */         else {
/*  387 */           IORNLAnnealingPanel.this.upCField1.setValue(IORNLAnnealingPanel.this.nUpConstraint1);
/*      */         }
/*      */         
/*  390 */         IORNLAnnealingPanel.this.upCLabel1.setText("Upper bound constraint of x1 ≤");
/*  391 */         if (IORNLAnnealingPanel.this.nVariable == 2) {
/*  392 */           IORNLAnnealingPanel.this.subjlabel.setText(String.valueOf(String.valueOf(new StringBuffer("Subject to : x1≤").append(IORNLAnnealingPanel.this.nUpConstraint1).append("   x2").append('≤').append(IORNLAnnealingPanel.this.nUpConstraint2))));
/*      */         }
/*  394 */         else if (IORNLAnnealingPanel.this.nVariable == 1) {
/*  395 */           IORNLAnnealingPanel.this.subjlabel.setText("Subject to : x1≤".concat(String.valueOf(String.valueOf(IORNLAnnealingPanel.this.nUpConstraint1))));
/*      */         }
/*      */         
/*      */       }
/*  399 */     });
/*  400 */     this.upCField2.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  402 */         if ((IORNLAnnealingPanel.this.upCField2.getValue() <= 63) && (IORNLAnnealingPanel.this.upCField2.getValue() >= 0)) {
/*  403 */           IORNLAnnealingPanel.this.nUpConstraint2 = IORNLAnnealingPanel.this.upCField2.getValue();
/*      */         }
/*      */         else {
/*  406 */           IORNLAnnealingPanel.this.upCField2.setValue(IORNLAnnealingPanel.this.nUpConstraint2);
/*      */         }
/*  408 */         IORNLAnnealingPanel.this.upCLabel2.setText("Upper bound constraint of x2 ≤");
/*  409 */         IORNLAnnealingPanel.this.subjlabel.setText(String.valueOf(String.valueOf(new StringBuffer("Subject to : x1≤").append(IORNLAnnealingPanel.this.nUpConstraint1).append("   x2").append('≤').append(IORNLAnnealingPanel.this.nUpConstraint2))));
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  415 */     });
/*  416 */     this.upCBar1.addAdjustmentListener(new AdjustmentListener() {
/*      */       public void adjustmentValueChanged(AdjustmentEvent e) {
/*  418 */         IORNLAnnealingPanel.this.nUpConstraint1 = IORNLAnnealingPanel.this.upCBar1.getValue();
/*  419 */         IORNLAnnealingPanel.this.upCLabel1.setText("Upper bound constraint of x1 ≤");
/*  420 */         if (IORNLAnnealingPanel.this.nVariable == 2) {
/*  421 */           IORNLAnnealingPanel.this.subjlabel.setText(String.valueOf(String.valueOf(new StringBuffer("Subject to : x1≤").append(IORNLAnnealingPanel.this.nUpConstraint1).append("   x2").append('≤').append(IORNLAnnealingPanel.this.nUpConstraint2))));
/*      */         }
/*  423 */         else if (IORNLAnnealingPanel.this.nVariable == 1) {
/*  424 */           IORNLAnnealingPanel.this.subjlabel.setText("Subject to : x1≤".concat(String.valueOf(String.valueOf(IORNLAnnealingPanel.this.nUpConstraint1))));
/*      */         }
/*      */         
/*      */       }
/*  428 */     });
/*  429 */     this.upCBar2.addAdjustmentListener(new AdjustmentListener() {
/*      */       public void adjustmentValueChanged(AdjustmentEvent e) {
/*  431 */         IORNLAnnealingPanel.this.nUpConstraint2 = IORNLAnnealingPanel.this.upCBar2.getValue();
/*  432 */         IORNLAnnealingPanel.this.upCLabel2.setText("Upper bound constraint of x2 ≤");
/*  433 */         IORNLAnnealingPanel.this.subjlabel.setText(String.valueOf(String.valueOf(new StringBuffer("Subject to : x1≤").append(IORNLAnnealingPanel.this.nUpConstraint1).append("   x2").append('≤').append(IORNLAnnealingPanel.this.nUpConstraint2))));
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setColor(int n, int size, int type)
/*      */   {
/*  445 */     int[] a = new int[size];
/*  446 */     int[] b = new int[size];
/*  447 */     String[] colName = { "Iter.", "T", "Trial", "f(x)", "Prob.", "Accept?" };
/*  448 */     Color[] tableBackCol = new Color[size];
/*      */     
/*  450 */     for (int i = 0; i < size; i++) {
/*  451 */       a[i] = i;
/*  452 */       b[i] = 0;
/*  453 */       if (type == 1) {
/*  454 */         if (i == n) tableBackCol[i] = Color.red; else {
/*  455 */           tableBackCol[i] = Color.magenta;
/*      */         }
/*      */       } else {
/*  458 */         tableBackCol[i] = Color.black;
/*      */       }
/*      */     }
/*      */     
/*  462 */     setTableBackColor(a, b, this.table, colName, tableBackCol);
/*      */   }
/*      */   
/*      */   public void setTableBackColor(int[] rowNum, int[] colNum, JTable currentTab, String[] currentS, Color[] cr) {
/*  466 */     DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
/*      */       private final Color[] val$cr;
/*      */       private final JTable val$currentTab;
/*      */       
/*  470 */       public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { for (int i = 0; i < IORNLAnnealingPanel.this.length; i++) {
/*  471 */           if (IORNLAnnealingPanel.this[i] == row) {
/*  472 */             setForeground(this.val$cr[i]);
/*      */           }
/*      */         }
/*  475 */         return super.getTableCellRendererComponent(this.val$currentTab, value, isSelected, hasFocus, row, column);
/*      */       }
/*      */       
/*      */ 
/*  479 */     };
/*  480 */     currentS[0] = "Iter.";
/*  481 */     currentS[1] = "T";
/*  482 */     currentS[2] = "Trial Solution";
/*  483 */     currentS[3] = "f(x)";
/*  484 */     currentS[4] = "Prob.";
/*  485 */     currentS[5] = "Accept?";
/*  486 */     for (int i = 0; i < currentS.length; i++) {
/*  487 */       currentTab.getColumn(currentS[i]).setCellRenderer(tcr);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildSetupPanel()
/*      */   {
/*  497 */     double[] c = new double[10];
/*  498 */     String str = new String();
/*      */     
/*      */ 
/*      */ 
/*  502 */     this.nTerm = this.NumTerm.getValue();
/*  503 */     this.nVariable = this.NumVariable.getValue();
/*  504 */     this.nConstraint = this.NumConstraint.getValue();
/*      */     
/*  506 */     if (this.nTerm > 10) {
/*  507 */       this.nTerm = 10;
/*  508 */       this.NumTerm.setValue(this.nTerm);
/*      */     }
/*  510 */     if (this.nVariable > 2) {
/*  511 */       this.nVariable = 2;
/*  512 */       this.NumVariable.setValue(2);
/*      */     }
/*  514 */     if (this.nConstraint > 2) {
/*  515 */       this.nConstraint = 2;
/*  516 */       this.NumConstraint.setValue(2);
/*      */     }
/*      */     
/*  519 */     this.fxcoef = new DecimalField[this.nTerm];
/*  520 */     for (int i = 0; i < this.nTerm; i++) {
/*  521 */       if (i == 0) {
/*  522 */         this.fxcoef[i] = new DecimalField(12.0D, 3, this.decfm);
/*      */       }
/*  524 */       else if (i == 1) {
/*  525 */         this.fxcoef[i] = new DecimalField(-975.0D, 3, this.decfm);
/*      */       }
/*  527 */       else if (i == 2) {
/*  528 */         this.fxcoef[i] = new DecimalField(28000.0D, 3, this.decfm);
/*      */       }
/*  530 */       else if (i == 3) {
/*  531 */         this.fxcoef[i] = new DecimalField(-345000.0D, 3, this.decfm);
/*      */       }
/*  533 */       else if (i == 4) {
/*  534 */         this.fxcoef[i] = new DecimalField(1800000.0D, 3, this.decfm);
/*      */       } else {
/*  536 */         this.fxcoef[i] = new DecimalField(0.0D, 3, this.decfm);
/*      */       }
/*      */     }
/*      */     
/*  540 */     this.fxexp = new WholeNumberField[this.nTerm][2];
/*  541 */     for (i = 0; i < this.nTerm; i++) {
/*  542 */       for (int j = 0; j < 2; j++) {
/*  543 */         this.fxexp[i][j] = new WholeNumberField(5 - i, 1);
/*      */       }
/*      */     }
/*      */     
/*  547 */     this.objectivePanel.removeAll();
/*  548 */     this.objectivePanel.add(this.maxmin);
/*  549 */     this.objectivePanel.add(new JLabel(" f(X) =  "));
/*      */     
/*      */ 
/*  552 */     for (i = 0; i < this.nTerm; i++) {
/*  553 */       if (i > 0) this.objectivePanel.add(new JLabel(" + "));
/*  554 */       this.objectivePanel.add(this.fxcoef[i]);
/*  555 */       for (int j = 0; j < this.nVariable; j++) {
/*  556 */         this.objectivePanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  557 */         this.objectivePanel.add(this.fxexp[i][j]);
/*      */       }
/*      */     }
/*      */     
/*  561 */     this.constraintPanel.removeAll();
/*      */     
/*  563 */     this.upCPanel.setPreferredSize(new Dimension(500, 100));
/*  564 */     this.upCPanel.setMaximumSize(new Dimension(500, 100));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  572 */     this.textP.setLayout(new BoxLayout(this.textP, 1));
/*  573 */     this.textP.add(this.upCLabel1);
/*  574 */     this.textP.add(Box.createVerticalStrut(6));
/*  575 */     this.textP.add(this.upCLabel2);
/*  576 */     this.upCLabel2.setVisible(false);
/*  577 */     this.initSoluTextX2.setVisible(false);
/*  578 */     this.initSoluLabX2.setVisible(false);
/*      */     
/*  580 */     this.textP.add(this.initSoluLabX1);
/*  581 */     this.textP.add(this.initSoluLabX2);
/*      */     
/*      */ 
/*      */ 
/*  585 */     this.fieldP.setLayout(new BoxLayout(this.fieldP, 1));
/*  586 */     this.fieldP.add(this.upCField1);
/*  587 */     this.fieldP.add(Box.createVerticalStrut(4));
/*  588 */     this.fieldP.add(this.upCField2);
/*  589 */     this.upCField2.setVisible(false);
/*      */     
/*  591 */     this.fieldP.add(this.initSoluTextX1);
/*  592 */     this.fieldP.add(this.initSoluTextX2);
/*      */     
/*  594 */     this.upCPanel.add(this.textP);
/*  595 */     this.upCPanel.add(Box.createHorizontalStrut(10));
/*  596 */     this.upCPanel.add(this.fieldP);
/*  597 */     this.upCPanel.add(this.comtlabel2);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  603 */     this.constraintPanel.add(this.subjlabel);
/*      */     
/*      */ 
/*  606 */     this.linear_equations = new IORLinearEquation[2];
/*  607 */     for (i = 0; i < this.nConstraint; i++) {
/*  608 */       this.linear_equations[i] = new IORLinearEquation(this.nVariable, 0, c);
/*  609 */       this.linear_equations[i].setMaximumSize(new Dimension(420, 50));
/*  610 */       this.linear_equations[i].setAlignmentX(0.0F);
/*  611 */       this.constraintPanel.add(this.linear_equations[i]);
/*      */     }
/*      */     
/*  614 */     str = " and    ";
/*  615 */     for (i = 0; i < this.nVariable; i++) {
/*  616 */       str = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(str))).append("x").append(i + 1).append(" ").append('≥').append(" 0 .     ")));
/*      */     }
/*  618 */     this.boundlabel.setText(str);
/*  619 */     this.constraintPanel.add(this.boundlabel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setNumVariable()
/*      */   {
/*  627 */     int oldv = this.nVariable;
/*  628 */     if ((this.NumVariable.getValue() <= 2) && (this.NumVariable.getValue() > 0)) {
/*  629 */       this.nVariable = this.NumVariable.getValue();
/*      */     }
/*      */     else {
/*  632 */       this.NumVariable.setValue(oldv);
/*      */     }
/*      */     
/*  635 */     if (this.NumVariable.getValue() == 1) {
/*  636 */       this.NumConstraint.setValue(0);
/*  637 */       this.NumConstraint.setEnabled(false);
/*  638 */       setNumConstraint();
/*  639 */       this.upCLabel2.setVisible(false);
/*  640 */       this.upCBar2.setVisible(false);
/*  641 */       this.upCField2.setVisible(false);
/*  642 */       this.subjlabel.setText("Subject to : x1≤".concat(String.valueOf(String.valueOf(this.nUpConstraint1))));
/*  643 */       this.initSoluTextX2.setVisible(false);
/*  644 */       this.initSoluLabX2.setVisible(false);
/*      */     }
/*  646 */     else if (this.NumVariable.getValue() == 2) {
/*  647 */       this.NumConstraint.setValue(2);
/*  648 */       this.NumConstraint.setEnabled(true);
/*  649 */       setNumConstraint();
/*  650 */       this.upCLabel2.setVisible(true);
/*  651 */       this.initSoluTextX2.setVisible(true);
/*  652 */       this.initSoluLabX2.setVisible(true);
/*  653 */       this.upCBar2.setVisible(true);
/*  654 */       this.upCField2.setVisible(true);
/*  655 */       this.subjlabel.setText(String.valueOf(String.valueOf(new StringBuffer("Subject to : x1≤").append(this.nUpConstraint1).append("   x2").append('≤').append(this.nUpConstraint2))));
/*      */     }
/*      */     
/*  658 */     if (oldv != this.nVariable) {
/*  659 */       this.NumVariable.setValue(this.nVariable);
/*      */       
/*  661 */       this.objectivePanel.removeAll();
/*  662 */       this.objectivePanel.add(this.maxmin);
/*  663 */       this.objectivePanel.add(new JLabel(" f(x) =  "));
/*  664 */       for (int i = 0; i < this.nTerm; i++) {
/*  665 */         if (i > 0) this.objectivePanel.add(new JLabel(" + "));
/*  666 */         this.objectivePanel.add(this.fxcoef[i]);
/*  667 */         for (int j = 0; j < this.nVariable; j++) {
/*  668 */           this.objectivePanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("X").append(j + 1)))));
/*  669 */           this.objectivePanel.add(this.fxexp[i][j]);
/*      */         }
/*      */       }
/*      */       
/*  673 */       for (i = 0; i < this.nConstraint; i++) {
/*  674 */         this.linear_equations[i].setNumVariable(this.nVariable);
/*      */       }
/*      */       
/*  677 */       String str = " and    ";
/*  678 */       for (i = 0; i < this.nVariable; i++) {
/*  679 */         str = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(str))).append("x").append(i + 1).append('≥').append("0 .    ")));
/*      */       }
/*  681 */       this.boundlabel.setText(str);
/*      */       
/*  683 */       revalidate();
/*  684 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setNumConstraint()
/*      */   {
/*  693 */     int oldv = this.nConstraint;
/*  694 */     if ((this.NumConstraint.getValue() <= 2) && (this.NumConstraint.getValue() >= 0)) {
/*  695 */       this.nConstraint = this.NumConstraint.getValue();
/*      */     }
/*      */     else {
/*  698 */       this.NumConstraint.setValue(oldv);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  703 */     if (this.NumVariable.getValue() == 1) {
/*  704 */       this.subjlabel.setText("Subject to : x1≤".concat(String.valueOf(String.valueOf(this.nUpConstraint1))));
/*      */ 
/*      */     }
/*  707 */     else if (this.NumVariable.getValue() == 2) {
/*  708 */       this.subjlabel.setText(String.valueOf(String.valueOf(new StringBuffer("Subject to : x1≤").append(this.nUpConstraint1).append("   x2").append('≤').append(this.nUpConstraint2))));
/*      */     }
/*      */     
/*  711 */     if (oldv != this.nConstraint) {
/*  712 */       this.NumConstraint.setValue(this.nConstraint);
/*      */       
/*  714 */       if (oldv < this.nConstraint) {
/*  715 */         this.constraintPanel.remove(this.boundlabel);
/*  716 */         for (int i = oldv; i < this.nConstraint; i++) {
/*  717 */           this.linear_equations[i] = new IORLinearEquation(this.nVariable);
/*  718 */           this.linear_equations[i].setMaximumSize(new Dimension(420, 50));
/*  719 */           this.linear_equations[i].setAlignmentX(0.0F);
/*  720 */           this.constraintPanel.add(this.linear_equations[i]);
/*      */         }
/*  722 */         this.constraintPanel.add(this.boundlabel);
/*      */       }
/*      */       else {
/*  725 */         for (int i = this.nConstraint; i < oldv; i++) {
/*  726 */           this.constraintPanel.remove(this.linear_equations[i]);
/*  727 */           this.linear_equations[i] = null;
/*      */         }
/*      */       }
/*  730 */       revalidate();
/*  731 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setNumTerm()
/*      */   {
/*  740 */     int oldv = this.nTerm;
/*  741 */     if ((this.NumTerm.getValue() <= 10) && (this.NumTerm.getValue() > 0)) {
/*  742 */       this.nTerm = this.NumTerm.getValue();
/*      */     }
/*      */     else {
/*  745 */       this.NumTerm.setValue(oldv);
/*      */     }
/*  747 */     if (oldv != this.nTerm) {
/*  748 */       this.NumTerm.setValue(this.nTerm);
/*      */       
/*  750 */       this.fxcoef = new DecimalField[this.nTerm];
/*  751 */       for (int i = 0; i < this.nTerm; i++) {
/*  752 */         this.fxcoef[i] = new DecimalField(0.0D, 3, this.decfm);
/*      */       }
/*      */       
/*  755 */       this.fxexp = new WholeNumberField[this.nTerm][2];
/*  756 */       for (i = 0; i < this.nTerm; i++) {
/*  757 */         for (int j = 0; j < 2; j++) {
/*  758 */           this.fxexp[i][j] = new WholeNumberField(0, 1);
/*      */         }
/*      */       }
/*      */       
/*  762 */       this.objectivePanel.removeAll();
/*  763 */       this.objectivePanel.add(this.maxmin);
/*  764 */       this.objectivePanel.add(new JLabel(" f(x) =  "));
/*  765 */       for (i = 0; i < this.nTerm; i++) {
/*  766 */         if (i > 0) this.objectivePanel.add(new JLabel(" + "));
/*  767 */         this.objectivePanel.add(this.fxcoef[i]);
/*  768 */         for (int j = 0; j < this.nVariable; j++) {
/*  769 */           this.objectivePanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  770 */           this.objectivePanel.add(this.fxexp[i][j]);
/*      */         }
/*      */       }
/*  773 */       revalidate();
/*  774 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildGradPanel()
/*      */   {
/*  783 */     this.dfx1coef = new DecimalField[this.nTerm];
/*  784 */     this.dfx2coef = new DecimalField[this.nTerm];
/*  785 */     this.dfx3coef = new DecimalField[this.nTerm];
/*      */     
/*  787 */     this.dfx1_xexp = new WholeNumberField[this.nTerm][2];
/*  788 */     this.dfx2_xexp = new WholeNumberField[this.nTerm][2];
/*  789 */     this.dfx3_xexp = new WholeNumberField[this.nTerm][2];
/*      */     
/*  791 */     for (int i = 0; i < this.nTerm; i++) {
/*  792 */       this.dfx1coef[i] = new DecimalField(0.0D, 3, this.decfm);
/*  793 */       for (int j = 0; j < 2; j++) {
/*  794 */         this.dfx1_xexp[i][j] = new WholeNumberField(0, 1);
/*      */       }
/*      */     }
/*  797 */     if (this.nVariable >= 2) {
/*  798 */       for (i = 0; i < this.nTerm; i++) {
/*  799 */         this.dfx2coef[i] = new DecimalField(0.0D, 3, this.decfm);
/*  800 */         for (int j = 0; j < 2; j++) {
/*  801 */           this.dfx2_xexp[i][j] = new WholeNumberField(0, 1);
/*      */         }
/*      */       }
/*      */     }
/*  805 */     if (this.nVariable == 3) {
/*  806 */       for (i = 0; i < this.nTerm; i++) {
/*  807 */         this.dfx3coef[i] = new DecimalField(0.0D, 3, this.decfm);
/*  808 */         for (int j = 0; j < 2; j++) {
/*  809 */           this.dfx3_xexp[i][j] = new WholeNumberField(0, 1);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  814 */     this.gradPanel.removeAll();
/*      */     
/*  816 */     if (this.controller.nldata.is_max) {
/*  817 */       this.fname = "Max  f(X) = ";
/*      */     } else {
/*  819 */       this.fname = "Min  f(X) = ";
/*      */     }
/*  821 */     this.fexPanel.setAlignmentX(0.0F);
/*      */     
/*  823 */     this.expPanel = Function2Panel(this.nVariable, this.nTerm, this.controller.nldata.fw_objective.fxcoef, this.controller.nldata.fw_objective.xexp);
/*  824 */     this.fexPanel.removeAll();
/*  825 */     this.fexPanel.add(new JLabel("   ".concat(String.valueOf(String.valueOf(this.fname)))));
/*  826 */     this.fexPanel.add(this.expPanel);
/*      */     
/*  828 */     JPanel titlePanel = new JPanel();
/*  829 */     titlePanel.setLayout(new BoxLayout(titlePanel, 1));
/*  830 */     titlePanel.add(new JLabel("Objective Function : "));
/*  831 */     titlePanel.add(Box.createVerticalStrut(5));
/*  832 */     titlePanel.add(this.fexPanel);
/*  833 */     titlePanel.add(Box.createVerticalStrut(10));
/*  834 */     titlePanel.add(new JLabel("Derivatives of the Objective function : "));
/*  835 */     titlePanel.add(Box.createVerticalStrut(10));
/*  836 */     this.gradPanel.add(titlePanel);
/*      */     
/*  838 */     this.dfxPanel[0] = new JPanel();
/*  839 */     this.dfxPanel[0].setAlignmentX(0.0F);
/*  840 */     this.dfxPanel[0].setPreferredSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  841 */     this.dfxPanel[0].setMaximumSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  842 */     this.dfxPanel[0].add(new JLabel("df(X)/dx1 =  "));
/*  843 */     for (i = 0; i < this.nTerm; i++) {
/*  844 */       if (i > 0) this.dfxPanel[0].add(new JLabel(" + "));
/*  845 */       this.dfxPanel[0].add(this.dfx1coef[i]);
/*  846 */       for (int j = 0; j < this.nVariable; j++) {
/*  847 */         this.dfxPanel[0].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  848 */         this.dfxPanel[0].add(this.dfx1_xexp[i][j]);
/*      */       }
/*      */     }
/*  851 */     this.gradPanel.add(this.dfxPanel[0]);
/*      */     
/*  853 */     if (this.nVariable >= 2) {
/*  854 */       this.dfxPanel[1] = new JPanel();
/*  855 */       this.dfxPanel[1].setAlignmentX(0.0F);
/*  856 */       this.dfxPanel[1].setPreferredSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  857 */       this.dfxPanel[1].setMaximumSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  858 */       this.dfxPanel[1].add(new JLabel("df(X)/dx2 =  "));
/*  859 */       for (i = 0; i < this.nTerm; i++) {
/*  860 */         if (i > 0) this.dfxPanel[1].add(new JLabel(" + "));
/*  861 */         this.dfxPanel[1].add(this.dfx2coef[i]);
/*  862 */         for (int j = 0; j < this.nVariable; j++) {
/*  863 */           this.dfxPanel[1].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  864 */           this.dfxPanel[1].add(this.dfx2_xexp[i][j]);
/*      */         }
/*      */       }
/*  867 */       this.gradPanel.add(this.dfxPanel[1]);
/*      */     }
/*      */     
/*  870 */     if (this.nVariable == 3) {
/*  871 */       this.dfxPanel[2] = new JPanel();
/*  872 */       this.dfxPanel[2].setAlignmentX(0.0F);
/*  873 */       this.dfxPanel[2].setPreferredSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  874 */       this.dfxPanel[2].setMaximumSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  875 */       this.dfxPanel[2].add(new JLabel("df(X)/dx3 =  "));
/*  876 */       for (i = 0; i < this.nTerm; i++) {
/*  877 */         if (i > 0) this.dfxPanel[2].add(new JLabel(" + "));
/*  878 */         this.dfxPanel[2].add(this.dfx3coef[i]);
/*  879 */         for (int j = 0; j < this.nVariable; j++) {
/*  880 */           this.dfxPanel[2].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  881 */           this.dfxPanel[2].add(this.dfx3_xexp[i][j]);
/*      */         }
/*      */       }
/*  884 */       this.gradPanel.add(this.dfxPanel[2]);
/*      */     }
/*      */     
/*  887 */     this.trialPanel.removeAll();
/*  888 */     this.trialPanel.setAlignmentX(0.0F);
/*  889 */     this.trialPanel.add(new JLabel("Initial Trial Solution :   "));
/*  890 */     switch (this.nVariable) {
/*      */     case 1: 
/*  892 */       this.xname = "( x1 ) =  ";
/*  893 */       break;
/*      */     case 2: 
/*  895 */       this.xname = "( x1 , x2 ) =  ";
/*  896 */       break;
/*      */     case 3: 
/*  898 */       this.xname = "( x1 , x2 , x3 ) =  ";
/*  899 */       break;
/*      */     default: 
/*  901 */       this.xname = " xname ";
/*  902 */       System.out.println("num variable error");
/*      */     }
/*      */     
/*  905 */     this.trialPanel.add(new JLabel(this.xname));
/*  906 */     this.trialPanel.add(new JLabel("( "));
/*  907 */     for (i = 0; i < this.nVariable; i++) {
/*  908 */       this.trialPanel.add(this.inixfd[i]);
/*  909 */       if (i < this.nVariable - 1)
/*  910 */         this.trialPanel.add(new JLabel(" , "));
/*      */     }
/*  912 */     this.trialPanel.add(new JLabel(" )"));
/*      */     
/*  914 */     this.gradPanel.add(Box.createVerticalStrut(20));
/*  915 */     this.gradPanel.add(this.trialPanel);
/*      */   }
/*      */   
/*      */ 
/*      */   private void BuildLpfuncPanel()
/*      */   {
/*  921 */     this.lpfuncPanel.removeAll();
/*  922 */     this.lpfuncPanel.setLayout(new BoxLayout(this.lpfuncPanel, 1));
/*  923 */     this.lpfuncPanel.add(new JLabel("Objective Function : "));
/*      */     
/*      */ 
/*  926 */     this.lpfuncPanel.add(this.fexPanel);
/*  927 */     this.lpfuncPanel.add(Box.createVerticalStrut(10));
/*      */     
/*  929 */     TableColumn column = this.table.getColumnModel().getColumn(0);
/*  930 */     column.setPreferredWidth(10);
/*  931 */     column = this.table.getColumnModel().getColumn(2);
/*  932 */     column.setPreferredWidth(100);
/*  933 */     column = this.table.getColumnModel().getColumn(4);
/*  934 */     column.setPreferredWidth(15);
/*  935 */     column = this.table.getColumnModel().getColumn(5);
/*  936 */     column.setPreferredWidth(10);
/*      */     
/*      */ 
/*  939 */     this.scrlPane.setPreferredSize(new Dimension(150, 390));
/*  940 */     this.lpfuncPanel.add(this.scrlPane);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private JPanel Function2Panel(int numvar, int numtm, double[] coef, int[][] expnt)
/*      */   {
/*  950 */     JPanel rtPanel = new JPanel();
/*      */     
/*      */ 
/*  953 */     rtPanel.setAlignmentX(0.0F);
/*  954 */     rtPanel.setLayout(new java.awt.FlowLayout(0));
/*      */     
/*  956 */     for (int i = 0; i < numtm; i++) {
/*  957 */       if ((i > 0) && (coef[i] > 0.01D)) {
/*  958 */         rtPanel.add(new JLabel(" + "));
/*      */       }
/*      */       else
/*  961 */         rtPanel.add(new JLabel("  "));
/*  962 */       if (Math.abs(coef[i]) > 0.01D) {
/*  963 */         rtPanel.add(new JLabel(this.decfm.format(coef[i])));
/*  964 */         for (int j = 0; j < numvar; j++) {
/*  965 */           if (expnt[i][j] > 1) {
/*  966 */             JLabel xlabel = new JLabel("x");
/*  967 */             xlabel.setFont(new Font("Default", 1, 14));
/*  968 */             rtPanel.add(new ScriptWriter(xlabel, "".concat(String.valueOf(String.valueOf(expnt[i][j]))), String.valueOf(String.valueOf(new StringBuffer("").append(j + 1)))));
/*      */           }
/*  970 */           else if (expnt[i][j] == 1) {
/*  971 */             JLabel xlabel = new JLabel("x");
/*  972 */             xlabel.setFont(new Font("Default", 1, 14));
/*  973 */             rtPanel.add(new ScriptWriter(xlabel, " ", String.valueOf(String.valueOf(new StringBuffer("").append(j + 1)))));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  978 */     return rtPanel;
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
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/* 1004 */     String str = "Interactive Frank-Wolfe Algorithm\n\n";
/* 1005 */     str = String.valueOf(String.valueOf(str)).concat("After entering a linearly constrained convex programming model (see Secs. 13.3 and 13.9), ");
/* 1006 */     str = String.valueOf(String.valueOf(str)).concat("with a maximum of three variables (all with nonnegativity constraints) and six functional ");
/* 1007 */     str = String.valueOf(String.valueOf(str)).concat("constraints, this routine (approximately) solve the linearly constrained convex programming ");
/* 1008 */     str = String.valueOf(String.valueOf(str)).concat("problem by using the Frank-Wolfe algorithm as presented in Sec. 13.9. You need to specify a ");
/* 1009 */     str = String.valueOf(String.valueOf(str)).concat("feasible initial trial solution and the partial derivatives of the objective function. ");
/* 1010 */     str = String.valueOf(String.valueOf(str)).concat("For Part 1 of each iteration, you identify the linear approximation of the objective ");
/* 1011 */     str = String.valueOf(String.valueOf(str)).concat("function by using the gradient calculated by the computer at the current trial solution. ");
/* 1012 */     str = String.valueOf(String.valueOf(str)).concat("The computer then performs Part 2 by using the simplex method to solve the resulting ");
/* 1013 */     str = String.valueOf(String.valueOf(str)).concat("linear programming problem. You and the computer next perform Part3 interactively to find ");
/* 1014 */     str = String.valueOf(String.valueOf(str)).concat("the new trial solution. \n\n");
/* 1015 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of variables\n");
/* 1016 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of variables in the model as a positive integer (maximum of three).\n\n ");
/* 1017 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of functional constraints\n");
/* 1018 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of functional constraints (excluding nonnegativity constraints) in ");
/* 1019 */     str = String.valueOf(String.valueOf(str)).concat("the model as a positive integer (maximum of six).\n\n");
/* 1020 */     str = String.valueOf(String.valueOf(str)).concat("Selecting the objective\n");
/* 1021 */     str = String.valueOf(String.valueOf(str)).concat("Select either Max or Min, depending on whether the objective is to maximize or minimize Z.\n\n");
/* 1022 */     str = String.valueOf(String.valueOf(str)).concat("Entering the coefficient of a variable in a functional constraint and the type of functional constraint\n");
/* 1023 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number as an integer or a decimal number. Entering these coefficients ");
/* 1024 */     str = String.valueOf(String.valueOf(str)).concat("(and right-hand sides) completes the entering of the model (since nonnegativity constraints ");
/* 1025 */     str = String.valueOf(String.valueOf(str)).concat("are assumed for the variables).\n");
/* 1026 */     str = String.valueOf(String.valueOf(str)).concat("Select ≤, =, or ≥ , depending on the type of constraint, and then press the NEXT button. ");
/* 1027 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/* 1029 */     this.help_content_step.setText(str);
/* 1030 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/* 1037 */     String str = "Interactive Frank-Wolfe Algorithm\n\n";
/* 1038 */     str = String.valueOf(String.valueOf(str)).concat("This routine enables you to quickly enter a linearly constrained conveys programming ");
/* 1039 */     str = String.valueOf(String.valueOf(str)).concat("problem (see Secs. 13.3 and 13.9) and then interactively execute the Frank-Wolfe ");
/* 1040 */     str = String.valueOf(String.valueOf(str)).concat("Algorithm presented \nin Sec. 13.9. Your role is to apply the logic of the algorithm, ");
/* 1041 */     str = String.valueOf(String.valueOf(str)).concat("and the computer will do the \nnumber crunching. The computer also will inform you if you ");
/* 1042 */     str = String.valueOf(String.valueOf(str)).concat("make a mistake on the first iteration.\n");
/* 1043 */     str = String.valueOf(String.valueOf(str)).concat("This routine can handle up to 3 variables (all with nonnegativity constraints) and 6 ");
/* 1044 */     str = String.valueOf(String.valueOf(str)).concat("functional constraints, which encompasses all relevant problems at the end of Chap. 13.\n");
/* 1045 */     str = String.valueOf(String.valueOf(str)).concat("When you finish a problem, you can print out all your work by choosing Print under the ");
/* 1046 */     str = String.valueOf(String.valueOf(str)).concat("File \nmenu. If you are interrupted before you finish, you can save your work (choose ");
/* 1047 */     str = String.valueOf(String.valueOf(str)).concat("Save under the File menu) and return later (choose Open).");
/* 1048 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/* 1050 */     this.help_content_procedure.setText(str);
/* 1051 */     this.help_content_procedure.revalidate(); }
/*      */   
/* 1053 */   private String str0 = "Interactive Frank-Wolfe Algorithm\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1064 */   private String str1 = "Interactive Frank-Wolfe Algorithm\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1089 */   private String str2 = "Entering the derivative of the objective function\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1098 */   private String str3 = "Entering the linear objective function\n";
/*      */   
/*      */ 
/*      */ 
/* 1102 */   private String str4 = "To continue\n";
/*      */   
/* 1104 */   private String str5 = "To continue\n";
/*      */   
/* 1106 */   private String str6 = "Entering the new trial solution\n";
/*      */   
/*      */ 
/* 1109 */   private String str7 = "Entering the a's and the b's\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1116 */   private String str8 = "To continue\n";
/*      */   
/* 1118 */   private String str9 = "To continue\n";
/*      */   
/* 1120 */   private String str10 = "To continue\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void updateStepHelpPanel()
/*      */   {
/* 1127 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/* 1128 */     switch (this.step) {
/*      */     case 1: 
/* 1130 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str1)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1168 */       break;
/*      */     case 2: 
/* 1133 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str2)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1168 */       break;
/*      */     case 3: 
/* 1136 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str3)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1168 */       break;
/*      */     case 4: 
/* 1139 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str4)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1168 */       break;
/*      */     case 5: 
/* 1142 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str5)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1168 */       break;
/*      */     case 6: 
/* 1145 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str6)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1168 */       break;
/*      */     case 7: 
/* 1148 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str7)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1168 */       break;
/*      */     case 8: 
/* 1151 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str8)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1168 */       break;
/*      */     case 9: 
/* 1154 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str9)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1168 */       break;
/*      */     case 10: 
/* 1157 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str10)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1168 */       break;
/*      */     default: 
/* 1160 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
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
/* 1176 */     this.isDowork = false;
/* 1177 */     if (this.opseq.isEmpty() == false) {
/* 1178 */       int last = ((Integer)this.opseq.lastElement()).intValue();
/* 1179 */       this.opseq.removeElementAt(this.opseq.size() - 1);
/*      */       
/* 1181 */       switch (last)
/*      */       {
/*      */       case 1: 
/* 1184 */         Vector par = new Vector();
/* 1185 */         IOROperation opr = new IOROperation(50601, par);
/* 1186 */         this.fwModel.setTableRow(0);
/* 1187 */         this.isFinished = false;
/* 1188 */         this.controller.solver.reset();
/* 1189 */         this.state.back();
/* 1190 */         this.controller.solver.getData(opr, this.controller.nldata);
/*      */         
/*      */ 
/* 1193 */         this.mainPanel.removeAll();
/* 1194 */         this.mainPanel.add(this.setupPanel);
/* 1195 */         this.mainPanel.add(this.buttPanel);
/* 1196 */         this.backbutt.setEnabled(false);
/* 1197 */         this.step = 1;
/*      */         
/*      */ 
/* 1200 */         this.controller.solver.anneal = null;
/* 1201 */         this.dataindex = 0;
/* 1202 */         this.nextbutt.setEnabled(true);
/*      */         
/* 1204 */         revalidate();
/* 1205 */         repaint();
/* 1206 */         break;
/*      */       default: 
/* 1208 */         System.out.println("frank-wolf cannot back here");break;
/*      */       }
/*      */       
/*      */     }
/* 1212 */     this.controller.nldata.isInputPanel = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void doNext()
/*      */   {
/* 1219 */     int[] num = new int[5];int[] opert = new int[this.nConstraint];
/* 1220 */     double[] objcoef = new double[this.nTerm];double[] grad1coef = new double[this.nTerm];
/* 1221 */     double[] grad2coef = new double[this.nTerm];double[] grad3coef = new double[this.nTerm];
/* 1222 */     int[][] objexp = new int[this.nTerm][3];int[][] grad1exp = new int[this.nTerm][3];
/* 1223 */     int[][] grad2exp = new int[this.nTerm][3];int[][] grad3exp = new int[this.nTerm][3];
/* 1224 */     double[] inisolu = new double[3];double[] lpobjcoef = new double[3];
/* 1225 */     double[] aent = new double[3];double[] bent = new double[3];double[] cent = new double[3];
/* 1226 */     double[][] contcoef = new double[this.nConstraint][this.nVariable + 1];
/*      */     
/*      */ 
/*      */ 
/* 1230 */     Vector par = new Vector();
/* 1231 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/* 1233 */     switch (this.step) {
/*      */     case 1: 
/*      */       try {
/* 1236 */         this.initSolutionX1 = Float.valueOf(this.initSoluTextX1.getText()).floatValue();
/* 1237 */         if (this.initSoluTextX2.isVisible()) {
/* 1238 */           this.initSolutionX2 = Float.valueOf(this.initSoluTextX2.getText()).floatValue();
/* 1239 */           if (this.initSolutionX2 > this.upCField2.getValue()) {
/* 1240 */             JOptionPane.showMessageDialog(this, "Not a feasible solution. Try again!");
/* 1241 */             return;
/*      */           }
/*      */         }
/*      */         
/* 1245 */         if (this.initSolutionX1 > this.upCField1.getValue()) {
/* 1246 */           JOptionPane.showMessageDialog(this, "Not a feasible solution. Try again!");
/* 1247 */           return;
/*      */         }
/*      */       }
/*      */       catch (Exception e1) {
/* 1251 */         JOptionPane.showMessageDialog(this, "Not a feasible solution. Try again!");
/* 1252 */         return;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1257 */       num[0] = this.nVariable;
/* 1258 */       num[1] = this.nConstraint;
/* 1259 */       num[2] = this.nTerm;
/* 1260 */       num[3] = this.nUpConstraint1;
/* 1261 */       num[4] = this.nUpConstraint2;
/*      */       
/*      */ 
/* 1264 */       par.addElement(num);
/*      */       
/*      */ 
/* 1267 */       if (this.maxmin.getSelectedIndex() == 0) {
/* 1268 */         par.addElement(new Boolean(true));
/*      */       }
/*      */       else {
/* 1271 */         par.addElement(new Boolean(false));
/*      */       }
/*      */       
/*      */ 
/* 1275 */       for (int i = 0; i < this.nTerm; i++) {
/* 1276 */         objcoef[i] = this.fxcoef[i].getValue();
/* 1277 */         for (int j = 0; j < this.nVariable; j++) {
/* 1278 */           objexp[i][j] = this.fxexp[i][j].getValue();
/*      */         }
/*      */       }
/* 1281 */       par.addElement(objcoef);
/* 1282 */       par.addElement(objexp);
/*      */       
/*      */ 
/* 1285 */       for (i = 0; i < this.nConstraint; i++) {
/* 1286 */         opert[i] = this.linear_equations[i].getOperator();
/* 1287 */         this.linear_equations[i].getCoefficient(contcoef[i]);
/*      */       }
/* 1289 */       par.addElement(contcoef);
/* 1290 */       par.addElement(opert);
/*      */       
/* 1292 */       float[] numF = new float[2];
/* 1293 */       numF[0] = this.initSolutionX1;
/* 1294 */       numF[1] = this.initSolutionX2;
/*      */       
/* 1296 */       par.addElement(numF);
/*      */       
/* 1298 */       opr.parameters = par;
/* 1299 */       opr.operation_code = 50601;
/*      */       
/* 1301 */       this.controller.solver.anneal = null;
/* 1302 */       if ((!this.isDowork) && 
/* 1303 */         (this.controller.solver.doWork(opr, this.controller.nldata) == false)) {
/* 1304 */         String err = this.controller.solver.getErrInfo();
/* 1305 */         JOptionPane.showMessageDialog(this, "Not a feasible solution. Try again!");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1310 */         this.state.addStep(opr);
/*      */         
/* 1312 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1315 */         BuildGradPanel();
/* 1316 */         this.mainPanel.removeAll();
/* 1317 */         BuildLpfuncPanel();
/*      */         
/*      */ 
/* 1320 */         for (i = this.dataindex; i < this.controller.nldata.accept.length; i++) {
/* 1321 */           if (this.controller.nldata.accept[this.dataindex] != 0) {
/*      */             break;
/*      */           }
/*      */           
/* 1325 */           this.dataindex += 1;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1330 */         this.dataindex += 1;
/*      */         
/*      */ 
/* 1333 */         if (this.controller.nldata.itertag.length < this.dataindex) {
/* 1334 */           this.fwModel.setTableRow(this.controller.nldata.itertag.length);
/* 1335 */           this.dataindex = this.controller.nldata.itertag.length;
/*      */         } else {
/* 1337 */           this.fwModel.setTableRow(this.dataindex);
/*      */         }
/*      */         
/* 1340 */         this.controller.solver.currentAType.currentindex = this.dataindex;
/*      */         
/* 1342 */         this.mainPanel.add(this.lpfuncPanel);
/*      */         
/* 1344 */         if (this.table.getRowCount() == this.controller.nldata.itertag.length) {
/* 1345 */           this.isFinished = true;
/*      */         } else {
/* 1347 */           this.isFinished = false;
/*      */         }
/* 1349 */         this.pos = this.controller.nldata.pos;
/* 1350 */         this.bestvarible = this.controller.nldata.bestvarible;
/* 1351 */         this.bestresult = "".concat(String.valueOf(String.valueOf(this.decfmt.format(this.controller.nldata.bestresult))));
/* 1352 */         if ((this.dataindex == this.controller.nldata.totalresult) || (this.isFinished)) {
/* 1353 */           setColor(this.controller.nldata.pos, this.controller.nldata.totalresult, 1);
/* 1354 */           this.nextbutt.setEnabled(false);
/*      */           
/*      */ 
/* 1357 */           this.mainPanel.add(this.resultPanel);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1362 */           setColor(this.controller.nldata.pos, this.controller.nldata.totalresult, 2);
/*      */         }
/*      */         
/* 1365 */         this.mainPanel.add(this.buttPanel);
/*      */         
/* 1367 */         this.backbutt.setEnabled(true);
/* 1368 */         this.step = 1;
/* 1369 */         countCurrentBestSolution();
/* 1370 */         revalidate();
/* 1371 */         repaint(); }
/* 1372 */       break;
/*      */     default: 
/* 1374 */       System.out.println("frank wolf cannot next here");
/*      */     }
/* 1376 */     this.controller.nldata.isInputPanel = false;
/* 1377 */     this.scrollShow = true;
/* 1378 */     this.isDowork = true;
/*      */   }
/*      */   
/* 1381 */   public void countCurrentBestSolution() { double bestSolution = 0.0D;
/* 1382 */     double bestX1 = 0.0D;
/* 1383 */     double bestX2 = 0.0D;
/* 1384 */     int countNum = 0;
/* 1385 */     if (this.table.getRowCount() == 0) {
/* 1386 */       if (this.nVariable == 1) {
/* 1387 */         this.resultLabel.setText("");
/*      */       }
/*      */       else {
/* 1390 */         this.resultLabel.setText("");
/*      */       }
/*      */       
/* 1393 */       this.mainPanel.add(this.resultPanel);
/* 1394 */       this.mainPanel.add(this.buttPanel);
/*      */     }
/* 1396 */     else if (this.table.getRowCount() == 1) {
/* 1397 */       if (this.nVariable == 1) {
/* 1398 */         this.resultLabel.setText(String.valueOf(String.valueOf(new StringBuffer("Best Solution x = ").append(this.decfmt.format(this.initSolutionX1)).append(" , Best f(x) = ").append(this.table.getValueAt(0, 3)))));
/* 1399 */         this.bSolution = Double.valueOf((String)this.table.getValueAt(0, 3)).doubleValue();
/* 1400 */         bestSolution = Double.valueOf((String)this.table.getValueAt(0, 3)).doubleValue();
/*      */       }
/*      */       else {
/* 1403 */         this.resultLabel.setText(String.valueOf(String.valueOf(new StringBuffer("Best Solution x1= ").append(this.decfmt.format(this.initSolutionX1)).append(", Best Solution x2 = ").append(this.decfmt.format(this.initSolutionX2)).append(", Best f(x) = ").append(this.table.getValueAt(0, 3)))));
/* 1404 */         this.bSolution = Double.valueOf((String)this.table.getValueAt(0, 3)).doubleValue();
/* 1405 */         bestSolution = Double.valueOf((String)this.table.getValueAt(0, 3)).doubleValue();
/*      */       }
/*      */     }
/*      */     else {
/* 1409 */       if (this.maxmin.getSelectedIndex() == 0) {
/* 1410 */         for (int i = 0; i < this.table.getRowCount(); i++) {
/* 1411 */           if (bestSolution < this.controller.nldata.function[i]) {
/* 1412 */             bestSolution = this.controller.nldata.function[i];
/* 1413 */             if (this.nVariable == 1) {
/* 1414 */               bestX1 = this.controller.nldata.variable[i][(this.nVariable - 1)];
/*      */             } else {
/* 1416 */               bestX1 = this.controller.nldata.variable[i][(this.nVariable - 2)];
/* 1417 */               bestX2 = this.controller.nldata.variable[i][(this.nVariable - 1)];
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1422 */         if (this.bSolution < bestSolution) {
/* 1423 */           this.bSolution = bestSolution;
/*      */         }
/*      */       } else {
/* 1426 */         for (int i = 0; i < this.table.getRowCount(); i++) {
/* 1427 */           if (bestSolution > this.controller.nldata.function[i]) {
/* 1428 */             bestSolution = this.controller.nldata.function[i];
/*      */             
/* 1430 */             if (this.nVariable == 1) {
/* 1431 */               bestX1 = this.controller.nldata.variable[i][(this.nVariable - 1)];
/*      */             } else {
/* 1433 */               bestX1 = this.controller.nldata.variable[i][(this.nVariable - 2)];
/* 1434 */               bestX2 = this.controller.nldata.variable[i][(this.nVariable - 1)];
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1439 */         if (this.bSolution > bestSolution) {
/* 1440 */           this.bSolution = bestSolution;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1445 */       if (this.nVariable == 1) {
/* 1446 */         this.resultLabel.setText(String.valueOf(String.valueOf(new StringBuffer("Best Solution x = ").append(this.decfmt.format(bestX1)).append(" , Best f(x) = ").append(this.decfmt.format(this.bSolution)))));
/*      */       }
/*      */       else {
/* 1449 */         this.resultLabel.setText(String.valueOf(String.valueOf(new StringBuffer("Best Solution x1= ").append(this.decfmt.format(bestX1)).append(", Best Solution x2 = ").append(this.decfmt.format(bestX2)).append(", Best f(x) = ").append(this.decfmt.format(this.bSolution)))));
/*      */       }
/*      */       
/* 1452 */       this.controller.nldata.bestSolution = this.resultLabel.getText();
/*      */       
/* 1454 */       this.controller.nldata.printdata = new String[this.table.getRowCount()][this.table.getColumnCount()];
/* 1455 */       for (int i = 0; i < this.table.getRowCount(); i++) {
/* 1456 */         for (int j = 0; j < this.table.getColumnCount(); j++) {
/* 1457 */           this.controller.nldata.printdata[i][j] = ((String)this.table.getValueAt(i, j));
/*      */         }
/* 1459 */         System.out.println(String.valueOf(String.valueOf(new StringBuffer("i = ").append(i).append("  inter.=").append(this.controller.nldata.printdata[i][0]))));
/*      */       }
/* 1461 */       this.controller.solver.initPrintData(getOperation(new Vector()), this.controller.nldata);
/*      */     }
/*      */     
/* 1464 */     this.mainPanel.add(this.resultPanel);
/* 1465 */     this.mainPanel.add(this.buttPanel);
/*      */   }
/*      */   
/*      */   public IOROperation getOperation(Vector vec) {
/* 1469 */     IOROperation operation = new IOROperation(50601, vec);
/*      */     
/* 1471 */     return operation;
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
/* 1483 */     int[] para = new int[7];
/* 1484 */     float[] numF = new float[2];
/*      */     try {
/* 1486 */       this.state = ((IORState)in.readObject());
/* 1487 */       para = (int[])in.readObject();
/* 1488 */       this.opseq = ((Vector)in.readObject());
/* 1489 */       this.state.setSolverStepVector();
/* 1490 */       this.pos = in.readInt();
/* 1491 */       this.bestvarible = ((double[])in.readObject());
/* 1492 */       this.bestresult = ((String)in.readObject());
/* 1493 */       numF = (float[])in.readObject();
/* 1494 */       in.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1497 */       e1.printStackTrace();
/* 1498 */       System.out.println("aOpen fails");
/*      */     }
/*      */     
/* 1501 */     this.step = para[0];
/* 1502 */     this.nTerm = para[1];
/* 1503 */     this.nVariable = para[2];
/* 1504 */     this.nConstraint = para[3];
/* 1505 */     this.nUpConstraint1 = para[4];
/* 1506 */     this.nUpConstraint2 = para[5];
/* 1507 */     this.dataindex = para[6];
/* 1508 */     this.initSolutionX1 = numF[0];
/* 1509 */     this.initSolutionX2 = numF[1];
/*      */     
/* 1511 */     if (this.step >= 1) {
/* 1512 */       this.NumTerm.setValue(this.nTerm);
/* 1513 */       this.NumVariable.setValue(this.nVariable);
/* 1514 */       this.NumConstraint.setValue(this.nConstraint);
/* 1515 */       this.upCField1.setValue(this.nUpConstraint1);
/* 1516 */       this.upCField2.setValue(this.nUpConstraint2);
/* 1517 */       this.initSoluTextX1.setText("".concat(String.valueOf(String.valueOf(numF[0]))));
/* 1518 */       this.initSoluTextX2.setText("".concat(String.valueOf(String.valueOf(numF[1]))));
/*      */       
/* 1520 */       BuildSetupPanel();
/* 1521 */       setNumConstraint();
/* 1522 */       setNumVariable();
/* 1523 */       setNumTerm();
/*      */       
/* 1525 */       for (int i = 0; i < this.nTerm; i++) {
/* 1526 */         this.fxcoef[i].setValue(this.controller.nldata.fw_objective.fxcoef[i]);
/* 1527 */         for (int j = 0; j < this.nVariable; j++) {
/* 1528 */           this.fxexp[i][j].setValue(this.controller.nldata.fw_objective.xexp[i][j]);
/*      */         }
/*      */       }
/* 1531 */       for (i = 0; i < this.nConstraint; i++) {
/* 1532 */         if (this.controller.nldata.fw_constraint_coef != null)
/* 1533 */           this.linear_equations[i].setCoefficient(this.controller.nldata.fw_constraint_coef[i]);
/* 1534 */         if (this.controller.nldata.fw_constraint_operator != null) {
/* 1535 */           this.linear_equations[i].setOperator(this.controller.nldata.fw_constraint_operator[i]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1547 */     if (!this.controller.nldata.isInputPanel) {
/* 1548 */       BuildGradPanel();
/* 1549 */       this.mainPanel.removeAll();
/* 1550 */       BuildLpfuncPanel();
/*      */       
/*      */ 
/* 1553 */       if (this.controller.nldata.accept != null) {
/* 1554 */         for (int i = this.dataindex; i < this.controller.nldata.accept.length; i++) {
/* 1555 */           if (this.controller.nldata.accept[this.dataindex] != 0) {
/*      */             break;
/*      */           }
/*      */           
/* 1559 */           this.dataindex += 1;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1565 */       this.fwModel.setTableRow(this.dataindex);
/* 1566 */       this.controller.solver.currentAType.currentindex = this.dataindex;
/*      */       
/* 1568 */       this.mainPanel.add(this.lpfuncPanel);
/*      */       
/*      */ 
/* 1571 */       if (this.table.getRowCount() - 1 == this.controller.nldata.itertag.length) {
/* 1572 */         this.isFinished = true;
/*      */       } else {
/* 1574 */         this.isFinished = false;
/*      */       }
/*      */       
/* 1577 */       if ((this.dataindex == this.controller.nldata.totalresult) || (this.isFinished)) {
/* 1578 */         setColor(this.pos, this.controller.nldata.totalresult, 1);
/* 1579 */         this.nextbutt.setEnabled(false);
/*      */         
/*      */ 
/* 1582 */         this.mainPanel.add(this.resultPanel);
/*      */       }
/*      */       else
/*      */       {
/* 1586 */         this.mainPanel.remove(this.resultPanel);
/* 1587 */         setColor(this.controller.nldata.pos, this.controller.nldata.totalresult, 2);
/*      */       }
/*      */       
/* 1590 */       countCurrentBestSolution();
/* 1591 */       this.backbutt.setEnabled(true);
/* 1592 */       this.step = 1;
/*      */     }
/*      */     
/*      */ 
/* 1596 */     revalidate();
/* 1597 */     repaint();
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
/* 1608 */     int[] interpara = new int[7];
/* 1609 */     float[] numF = new float[2];
/* 1610 */     numF[0] = Float.valueOf(this.initSoluTextX1.getText()).floatValue();
/* 1611 */     numF[1] = Float.valueOf(this.initSoluTextX2.getText()).floatValue();
/*      */     
/* 1613 */     interpara[0] = this.step;
/* 1614 */     interpara[1] = this.nTerm;
/* 1615 */     interpara[2] = this.nVariable;
/* 1616 */     interpara[3] = this.nConstraint;
/* 1617 */     interpara[4] = this.nUpConstraint1;
/* 1618 */     interpara[5] = this.nUpConstraint2;
/* 1619 */     interpara[6] = this.dataindex;
/*      */     try
/*      */     {
/* 1622 */       out.writeObject(this.state);
/* 1623 */       out.writeObject(interpara);
/* 1624 */       out.writeObject(this.opseq);
/* 1625 */       out.writeInt(this.pos);
/* 1626 */       out.writeObject(this.bestvarible);
/* 1627 */       out.writeObject(this.bestresult);
/* 1628 */       out.writeObject(numF);
/* 1629 */       out.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1632 */       e1.printStackTrace();
/* 1633 */       System.out.println("Save fails");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFunction()
/*      */   {
/* 1644 */     int[] num = new int[5];int[] opert = new int[this.nConstraint];
/* 1645 */     double[] objcoef = new double[this.nTerm];double[] grad1coef = new double[this.nTerm];
/* 1646 */     double[] grad2coef = new double[this.nTerm];double[] grad3coef = new double[this.nTerm];
/* 1647 */     int[][] objexp = new int[this.nTerm][3];int[][] grad1exp = new int[this.nTerm][3];
/* 1648 */     int[][] grad2exp = new int[this.nTerm][3];int[][] grad3exp = new int[this.nTerm][3];
/* 1649 */     double[] inisolu = new double[3];
/* 1650 */     double[][] contcoef = new double[this.nConstraint][this.nVariable + 1];
/*      */     
/* 1652 */     Vector par = new Vector();
/* 1653 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/* 1655 */     if (this.step == 1) {
/* 1656 */       num[0] = this.nVariable;
/* 1657 */       num[1] = this.nConstraint;
/* 1658 */       num[2] = this.nTerm;
/* 1659 */       num[3] = this.nUpConstraint1;
/* 1660 */       num[4] = this.nUpConstraint2;
/*      */       
/*      */ 
/* 1663 */       par.addElement(num);
/* 1664 */       if (this.maxmin.getSelectedIndex() == 0) {
/* 1665 */         par.addElement(new Boolean(true));
/*      */       } else {
/* 1667 */         par.addElement(new Boolean(false));
/*      */       }
/* 1669 */       for (int i = 0; i < this.nTerm; i++) {
/* 1670 */         objcoef[i] = this.fxcoef[i].getValue();
/* 1671 */         for (int j = 0; j < this.nVariable; j++) {
/* 1672 */           objexp[i][j] = this.fxexp[i][j].getValue();
/*      */         }
/*      */       }
/* 1675 */       par.addElement(objcoef);
/* 1676 */       par.addElement(objexp);
/* 1677 */       for (i = 0; i < this.nConstraint; i++) {
/* 1678 */         opert[i] = this.linear_equations[i].getOperator();
/* 1679 */         this.linear_equations[i].getCoefficient(contcoef[i]);
/*      */       }
/* 1681 */       par.addElement(contcoef);
/* 1682 */       par.addElement(opert);
/*      */       
/* 1684 */       float[] numF = new float[2];
/* 1685 */       numF[0] = this.initSolutionX1;
/* 1686 */       numF[1] = this.initSolutionX2;
/*      */       
/* 1688 */       par.addElement(numF);
/*      */       
/* 1690 */       if (!this.isDowork) {
/* 1691 */         this.controller.solver.anneal = new SimulateAnnealing();
/* 1692 */         opr.parameters = par;
/* 1693 */         opr.operation_code = 50601;
/* 1694 */         this.controller.solver.anneal = null;
/* 1695 */         this.controller.solver.doWork(opr, this.controller.nldata);
/*      */       }
/* 1697 */       this.controller.solver.anneal = null;
/*      */     }
/*      */   }
/*      */   
/*      */   class IORFWTableModel extends javax.swing.table.AbstractTableModel {
/*      */     private int i;
/*      */     private int j;
/*      */     private int tablerow;
/*      */     
/*      */     IORFWTableModel() {}
/*      */     
/*      */     public void setTableRow(int n) {
/* 1709 */       this.tablerow = n;
/*      */     }
/*      */     
/*      */     public int getColumnCount() {
/* 1713 */       return 6;
/*      */     }
/*      */     
/*      */     public int getRowCount() {
/* 1717 */       return this.tablerow;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col) {
/* 1721 */       switch (col) {
/*      */       case 0: 
/* 1723 */         return "Iter.";
/*      */       case 1: 
/* 1725 */         return "T";
/*      */       case 2: 
/* 1727 */         return "Trial Solution";
/*      */       case 3: 
/* 1729 */         return "f(x)";
/*      */       case 4: 
/* 1731 */         return "Prob.";
/*      */       case 5: 
/* 1733 */         return "Accept?";
/*      */       }
/* 1735 */       return "err";
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col)
/*      */     {
/* 1740 */       String str = new String();
/* 1741 */       String[] astr = new String[3];String[] bstr = new String[3];
/* 1742 */       DecimalFormat decfm = new DecimalFormat("#.###");
/*      */       
/* 1744 */       switch (col) {
/*      */       case 0: 
/* 1746 */         if (row < IORNLAnnealingPanel.this.controller.nldata.itertag.length) {
/* 1747 */           return "".concat(String.valueOf(String.valueOf(IORNLAnnealingPanel.this.controller.nldata.itertag[row])));
/*      */         }
/*      */       case 1: 
/* 1750 */         if (row < IORNLAnnealingPanel.this.controller.nldata.itertag.length) {
/* 1751 */           if (IORNLAnnealingPanel.this.controller.nldata.itertag[row] == 0) {
/* 1752 */             return "";
/*      */           }
/* 1754 */           if (row < IORNLAnnealingPanel.this.controller.nldata.Temp.length) {
/* 1755 */             return decfm.format(IORNLAnnealingPanel.this.controller.nldata.Temp[row]);
/*      */           }
/*      */         }
/*      */       case 2: 
/* 1759 */         String xstr = "( ";
/* 1760 */         for (this.i = 0; this.i < IORNLAnnealingPanel.this.nVariable; this.i += 1) {
/* 1761 */           if (row < IORNLAnnealingPanel.this.controller.nldata.variable.length)
/* 1762 */             xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(decfm.format(IORNLAnnealingPanel.this.controller.nldata.variable[row][this.i]))));
/* 1763 */           if (this.i < IORNLAnnealingPanel.this.nVariable - 1) {
/* 1764 */             xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*      */           }
/*      */         }
/* 1767 */         xstr = String.valueOf(String.valueOf(xstr)).concat(" )");
/* 1768 */         return xstr;
/*      */       case 3: 
/* 1770 */         if (row < IORNLAnnealingPanel.this.controller.nldata.function.length)
/* 1771 */           return decfm.format(IORNLAnnealingPanel.this.controller.nldata.function[row]);
/*      */       case 4: 
/* 1773 */         if (row < IORNLAnnealingPanel.this.controller.nldata.itertag.length) {
/* 1774 */           if (IORNLAnnealingPanel.this.controller.nldata.itertag[row] == 0) {
/* 1775 */             return "";
/*      */           }
/* 1777 */           if (row < IORNLAnnealingPanel.this.controller.nldata.probability.length) {
/* 1778 */             return decfm.format(IORNLAnnealingPanel.this.controller.nldata.probability[row]);
/*      */           }
/*      */         }
/*      */       
/*      */       case 5: 
/* 1783 */         if (row < IORNLAnnealingPanel.this.controller.nldata.itertag.length) {
/* 1784 */           if (IORNLAnnealingPanel.this.controller.nldata.itertag[row] == 0)
/* 1785 */             return "";
/* 1786 */           return "".concat(String.valueOf(String.valueOf(IORNLAnnealingPanel.this.controller.nldata.accept[row])));
/*      */         }
/*      */         break; }
/* 1789 */       return "err";
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c)
/*      */     {
/* 1794 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1800 */       return false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNLAnnealingPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */