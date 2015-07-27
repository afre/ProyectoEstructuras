/*      */ import java.awt.Dimension;
/*      */ import java.awt.FlowLayout;
/*      */ import java.awt.Font;
/*      */ import java.awt.GridLayout;
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
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IORNLSumtPanel
/*      */   extends IORActionPanel
/*      */ {
/*   31 */   private final String LESSTHAN = "<=";
/*   32 */   private final char SQUR = '√';
/*      */   
/*   34 */   private static final int MAXVARS = 3;
/*   35 */   private static final int MAXCONSTRAINT = 3;
/*   36 */   private static final int MAXTERMS = 10;
/*      */   
/*   38 */   private IORNLPProcessController controller = null;
/*      */   
/*   40 */   private JPanel mainPanel = new JPanel();
/*      */   
/*   42 */   private JPanel numPanel = null;
/*   43 */   private JPanel funcPanel = null;
/*   44 */   private JPanel upPanel = null;
/*   45 */   private JPanel bottomPanel = null;
/*   46 */   private JPanel buttPanel = new JPanel();
/*   47 */   private JPanel trialPanel = new JPanel();
/*   48 */   private JPanel rvalPanel = new JPanel();
/*   49 */   private JPanel namePanel = new JPanel();
/*   50 */   private JPanel fieldPanel = new JPanel();
/*   51 */   private JPanel capPanel = new JPanel();
/*   52 */   private JPanel eqtPanel = new JPanel();
/*   53 */   private JPanel expPanel = new JPanel();
/*      */   
/*      */ 
/*   56 */   private JLabel indilabel = new JLabel("Using the gradient search procedure, P(X, r) is maximized at : ");
/*   57 */   private JLabel nxtlabel = new JLabel("Press the NEXT button to perform another iteration.");
/*   58 */   private JLabel trialabel = new JLabel();
/*   59 */   private JLabel xlabel = new JLabel();
/*   60 */   private JLabel pxlabel = new JLabel();
/*      */   
/*   62 */   private JButton backbutt = new JButton("Back");
/*   63 */   private JButton nextbutt = new JButton("Next");
/*      */   
/*   65 */   private DecimalField[] inixfd = new DecimalField[3];
/*      */   private DecimalField rfd;
/*   67 */   private WholeNumberField NumTerm = new WholeNumberField(4, 2);
/*   68 */   private WholeNumberField NumVariableWithBound = new WholeNumberField(2, 2);
/*   69 */   private WholeNumberField NumVariableNoBound = new WholeNumberField(0, 2);
/*   70 */   private WholeNumberField NumInequality = new WholeNumberField(1, 2);
/*   71 */   private WholeNumberField NumEquality = new WholeNumberField(0, 2);
/*      */   
/*      */   private IORNLSumtPanel.IORPolynomial ffunction;
/*      */   private IORNLSumtPanel.IORPolynomial[] bfunction;
/*      */   private IORNLSumtPanel.IORPolynomial[] lfunction;
/*   76 */   private int step = 1;
/*   77 */   private int nTerm = 4;
/*   78 */   private int nVariableWithBound = 2;
/*   79 */   private int nVariableNoBound = 0;
/*   80 */   private int nAllVariable = 2;
/*   81 */   private int nInequality = 1;
/*   82 */   private int nEquality = 0;
/*   83 */   private int nConstraint = 1;
/*      */   
/*   85 */   private DecimalFormat decfm = new DecimalFormat();
/*   86 */   private DecimalFormat highresfm = new DecimalFormat();
/*   87 */   private Vector opseq = new Vector();
/*   88 */   private Vector rvseq = new Vector();
/*      */   
/*   90 */   private double[] inixval = new double[3];
/*      */   
/*      */   private String pxs;
/*      */   
/*      */   private String xname;
/*      */   private String oldxstr;
/*      */   private String newxstr;
/*      */   
/*      */   public IORNLSumtPanel(IORNLPProcessController c)
/*      */   {
/*  100 */     super(c);
/*  101 */     this.controller = c;
/*  102 */     this.state = new IORState(this.controller.solver);
/*  103 */     add(this.mainPanel);
/*  104 */     this.actionStatus.setVisible(false);
/*  105 */     this.bn_back.setVisible(false);
/*  106 */     this.bn_finish.setVisible(false);
/*      */     
/*  108 */     this.decfm.setGroupingUsed(false);
/*      */     
/*  110 */     this.highresfm.setGroupingUsed(false);
/*  111 */     this.highresfm.setMaximumFractionDigits(10);
/*  112 */     this.rfd = new DecimalField(0.0D, 10, this.highresfm);
/*      */     
/*  114 */     for (int i = 0; i < 3; i++) {
/*  115 */       this.inixfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*      */     }
/*      */     
/*  118 */     this.pxlabel.setAlignmentX(0.0F);
/*  119 */     this.trialPanel.setAlignmentX(0.0F);
/*  120 */     this.rvalPanel.setAlignmentX(0.0F);
/*      */     
/*      */ 
/*      */ 
/*  124 */     this.buttPanel.setAlignmentX(0.0F);
/*  125 */     this.backbutt.setEnabled(false);
/*  126 */     this.buttPanel.add(this.backbutt);
/*  127 */     this.buttPanel.add(Box.createHorizontalStrut(10));
/*  128 */     this.buttPanel.add(this.nextbutt);
/*      */     
/*  130 */     this.numPanel = new JPanel();
/*  131 */     this.numPanel.setAlignmentX(0.0F);
/*      */     
/*  133 */     this.namePanel.setLayout(new BoxLayout(this.namePanel, 1));
/*  134 */     this.namePanel.add(Box.createVerticalStrut(5));
/*  135 */     this.namePanel.add(new JLabel("Number of variables with lower bound constraints :"));
/*  136 */     this.namePanel.add(Box.createVerticalStrut(5));
/*  137 */     this.namePanel.add(new JLabel("Number of variables without lower bound constraints :"));
/*  138 */     this.namePanel.add(Box.createVerticalStrut(10));
/*  139 */     this.namePanel.add(new JLabel("(The sum of these two numbers must be <= 3 )"));
/*  140 */     this.namePanel.add(Box.createVerticalStrut(15));
/*  141 */     this.namePanel.add(new JLabel("Number of inequality functional constraints :"));
/*  142 */     this.namePanel.add(Box.createVerticalStrut(5));
/*  143 */     this.namePanel.add(new JLabel("Number of equality functional constraints :"));
/*  144 */     this.namePanel.add(Box.createVerticalStrut(10));
/*  145 */     this.namePanel.add(new JLabel("(The sum of these two numbers must be <= 3 )"));
/*  146 */     this.namePanel.add(Box.createVerticalStrut(30));
/*  147 */     this.namePanel.add(new JLabel("Maximum of the number of terms in any function (<=10) : "));
/*      */     
/*  149 */     this.fieldPanel.setLayout(new BoxLayout(this.fieldPanel, 1));
/*      */     
/*  151 */     this.fieldPanel.add(this.NumVariableWithBound);
/*  152 */     this.fieldPanel.add(Box.createVerticalStrut(4));
/*  153 */     this.fieldPanel.add(this.NumVariableNoBound);
/*  154 */     this.fieldPanel.add(Box.createVerticalStrut(38));
/*  155 */     this.fieldPanel.add(this.NumInequality);
/*  156 */     this.fieldPanel.add(Box.createVerticalStrut(4));
/*  157 */     this.fieldPanel.add(this.NumEquality);
/*  158 */     this.fieldPanel.add(Box.createVerticalStrut(46));
/*  159 */     this.fieldPanel.add(this.NumTerm);
/*      */     
/*  161 */     this.numPanel.add(this.namePanel);
/*  162 */     this.numPanel.add(Box.createHorizontalStrut(10));
/*  163 */     this.numPanel.add(this.fieldPanel);
/*      */     
/*  165 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*  166 */     this.mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
/*  167 */     this.mainPanel.add(this.numPanel);
/*  168 */     this.mainPanel.add(Box.createVerticalStrut(30));
/*  169 */     this.mainPanel.add(this.buttPanel);
/*      */     
/*  171 */     this.funcPanel = new JPanel();
/*  172 */     this.funcPanel.setLayout(new BoxLayout(this.funcPanel, 1));
/*      */     
/*  174 */     this.upPanel = new JPanel();
/*  175 */     this.upPanel.setLayout(new BoxLayout(this.upPanel, 1));
/*      */     
/*  177 */     this.bottomPanel = new JPanel();
/*  178 */     this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel, 1));
/*  179 */     this.bottomPanel.setPreferredSize(new Dimension(450, 120));
/*  180 */     this.bottomPanel.setMaximumSize(new Dimension(450, 120));
/*      */     
/*      */ 
/*  183 */     this.capPanel.setAlignmentX(0.0F);
/*  184 */     this.eqtPanel.setAlignmentX(0.0F);
/*  185 */     this.expPanel.setAlignmentX(0.0F);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  190 */     this.NumVariableWithBound.addFocusListener(new FocusListener() {
/*      */       public void focusLost(FocusEvent e) {
/*  192 */         int nvarbound = IORNLSumtPanel.this.NumVariableWithBound.getValue();
/*  193 */         int nvarnobound = IORNLSumtPanel.this.NumVariableNoBound.getValue();
/*  194 */         if ((nvarbound == 0) && (nvarnobound == 0)) {
/*  195 */           IORNLSumtPanel.this.NumVariableWithBound.setValue(1);
/*      */         }
/*  197 */         else if (nvarbound + nvarnobound > 3) {
/*  198 */           IORNLSumtPanel.this.NumVariableWithBound.setValue(3 - nvarnobound);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       public void focusGained(FocusEvent e) {}
/*  205 */     });
/*  206 */     this.NumVariableNoBound.addFocusListener(new FocusListener() {
/*      */       public void focusLost(FocusEvent e) {
/*  208 */         int nvarbound = IORNLSumtPanel.this.NumVariableWithBound.getValue();
/*  209 */         int nvarnobound = IORNLSumtPanel.this.NumVariableNoBound.getValue();
/*  210 */         if ((nvarbound == 0) && (nvarnobound == 0)) {
/*  211 */           IORNLSumtPanel.this.NumVariableNoBound.setValue(1);
/*      */         }
/*  213 */         else if (nvarbound + nvarnobound > 3) {
/*  214 */           IORNLSumtPanel.this.NumVariableNoBound.setValue(3 - nvarbound);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       public void focusGained(FocusEvent e) {}
/*  221 */     });
/*  222 */     this.NumInequality.addFocusListener(new FocusListener() {
/*      */       public void focusLost(FocusEvent e) {
/*  224 */         int nineq = IORNLSumtPanel.this.NumInequality.getValue();
/*  225 */         int neq = IORNLSumtPanel.this.NumEquality.getValue();
/*  226 */         if (nineq + neq > 3) {
/*  227 */           IORNLSumtPanel.this.NumInequality.setValue(3 - neq);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */       public void focusGained(FocusEvent e) {}
/*  233 */     });
/*  234 */     this.NumEquality.addFocusListener(new FocusListener() {
/*      */       public void focusLost(FocusEvent e) {
/*  236 */         int nineq = IORNLSumtPanel.this.NumInequality.getValue();
/*  237 */         int neq = IORNLSumtPanel.this.NumEquality.getValue();
/*  238 */         if (nineq + neq > 3) {
/*  239 */           IORNLSumtPanel.this.NumEquality.setValue(3 - nineq);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       public void focusGained(FocusEvent e) {}
/*  246 */     });
/*  247 */     this.nextbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  249 */         IORNLSumtPanel.this.doNext();
/*      */       }
/*  251 */     });
/*  252 */     this.backbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  254 */         IORNLSumtPanel.this.doBack();
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildFuncPanel()
/*      */   {
/*  264 */     this.ffunction = new IORNLSumtPanel.IORPolynomial(this.nAllVariable, this.nTerm, "  f(X) ");
/*  265 */     this.bfunction = new IORNLSumtPanel.IORPolynomial[this.nConstraint];
/*  266 */     for (int i = 0; i < this.nConstraint; i++) {
/*  267 */       this.bfunction[i] = new IORNLSumtPanel.IORPolynomial(this.nAllVariable, this.nTerm, String.valueOf(String.valueOf(new StringBuffer("B").append(i + 1).append("(X)"))));
/*      */     }
/*  269 */     this.lfunction = new IORNLSumtPanel.IORPolynomial[this.nVariableWithBound];
/*  270 */     for (i = 0; i < this.nVariableWithBound; i++) {
/*  271 */       this.lfunction[i] = new IORNLSumtPanel.IORPolynomial(this.nAllVariable, this.nTerm, String.valueOf(String.valueOf(new StringBuffer("L").append(i + 1).append("(X)"))));
/*      */     }
/*      */     
/*  274 */     this.pxs = "P(X , r) =  f(X) ";
/*  275 */     for (i = 0; i < this.nInequality; i++) {
/*  276 */       this.pxs = String.valueOf(String.valueOf(this.pxs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("  - r / B").append(i + 1).append("(X)"))))));
/*      */     }
/*  278 */     for (i = 0; i < this.nEquality; i++) {
/*  279 */       this.pxs = String.valueOf(String.valueOf(this.pxs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("  - B").append(this.nInequality + 1 + i).append("(X)^2 / ").append('√').append("r"))))));
/*      */     }
/*  281 */     for (i = 0; i < this.nVariableWithBound; i++) {
/*  282 */       this.pxs = String.valueOf(String.valueOf(this.pxs)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("  - r / L").append(i + 1).append("(X)"))))));
/*      */     }
/*  284 */     this.pxlabel.setText(this.pxs);
/*  285 */     this.funcPanel.removeAll();
/*  286 */     this.funcPanel.add(this.pxlabel);
/*  287 */     this.funcPanel.add(Box.createVerticalStrut(10));
/*  288 */     this.funcPanel.add(this.ffunction);
/*  289 */     this.funcPanel.add(Box.createVerticalStrut(5));
/*  290 */     for (i = 0; i < this.nConstraint; i++) {
/*  291 */       this.funcPanel.add(this.bfunction[i]);
/*  292 */       this.funcPanel.add(Box.createVerticalStrut(5));
/*      */     }
/*  294 */     for (i = 0; i < this.nVariableWithBound; i++) {
/*  295 */       this.funcPanel.add(this.lfunction[i]);
/*  296 */       this.funcPanel.add(Box.createVerticalStrut(5));
/*      */     }
/*      */     
/*  299 */     this.trialPanel.removeAll();
/*  300 */     this.trialPanel.add(new JLabel("Initial Trial Solution :   "));
/*  301 */     switch (this.nAllVariable) {
/*      */     case 1: 
/*  303 */       this.xname = "( x1 ) =  ";
/*  304 */       break;
/*      */     case 2: 
/*  306 */       this.xname = "( x1 , x2 ) =  ";
/*  307 */       break;
/*      */     case 3: 
/*  309 */       this.xname = "( x1 , x2 , x3 ) =  ";
/*  310 */       break;
/*      */     default: 
/*  312 */       this.xname = " xname ";
/*      */     }
/*  314 */     this.trialPanel.add(new JLabel(this.xname));
/*  315 */     this.trialPanel.add(new JLabel("( "));
/*  316 */     for (i = 0; i < this.nAllVariable; i++) {
/*  317 */       this.trialPanel.add(this.inixfd[i]);
/*  318 */       if (i < this.nAllVariable - 1)
/*  319 */         this.trialPanel.add(new JLabel(" , "));
/*      */     }
/*  321 */     this.trialPanel.add(new JLabel(" )"));
/*      */     
/*  323 */     this.rvalPanel.removeAll();
/*  324 */     this.rvalPanel.add(new JLabel(" r = "));
/*  325 */     this.rvalPanel.add(this.rfd);
/*      */     
/*  327 */     this.bottomPanel.removeAll();
/*  328 */     this.bottomPanel.add(this.trialPanel);
/*  329 */     this.bottomPanel.add(Box.createVerticalStrut(10));
/*  330 */     this.bottomPanel.add(this.rvalPanel);
/*  331 */     revalidate();
/*  332 */     repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildUpPanel()
/*      */   {
/*  340 */     JPanel[] bxPanel = new JPanel[3];JPanel[] lxPanel = new JPanel[3];
/*      */     
/*  342 */     this.upPanel.removeAll();
/*  343 */     this.upPanel.add(new JLabel(this.pxs));
/*  344 */     this.upPanel.add(Box.createVerticalStrut(10));
/*      */     
/*  346 */     this.expPanel.setPreferredSize(new Dimension(this.nTerm * this.nAllVariable * 40 + 80, (this.nConstraint + this.nVariableWithBound + 1) * 30 + 20));
/*  347 */     this.expPanel.setMaximumSize(new Dimension(this.nTerm * this.nAllVariable * 40 + 100, (this.nConstraint + this.nVariableWithBound + 1) * 30 + 20));
/*  348 */     this.eqtPanel.setLayout(new GridLayout(this.nConstraint + this.nVariableWithBound + 1, 1));
/*  349 */     this.capPanel.setLayout(new GridLayout(this.nConstraint + this.nVariableWithBound + 1, 1));
/*  350 */     this.capPanel.removeAll();
/*  351 */     this.eqtPanel.removeAll();
/*      */     
/*  353 */     JPanel fxPanel = new JPanel();
/*  354 */     fxPanel.setPreferredSize(new Dimension(60, 30));
/*  355 */     fxPanel.setMaximumSize(new Dimension(60, 30));
/*      */     
/*  357 */     fxPanel.add(new JLabel(" f(X)   =  "));
/*  358 */     this.capPanel.add(fxPanel);
/*      */     
/*  360 */     JPanel tmpexPanel = Function2Panel(this.nAllVariable, this.nTerm, this.controller.data.sumt_objective.fxcoef, this.controller.data.sumt_objective.xexp);
/*  361 */     this.eqtPanel.add(tmpexPanel);
/*  362 */     for (int i = 0; i < this.nConstraint; i++) {
/*  363 */       bxPanel[i] = new JPanel();
/*  364 */       bxPanel[i].setPreferredSize(new Dimension(60, 30));
/*  365 */       bxPanel[i].setMaximumSize(new Dimension(60, 30));
/*  366 */       bxPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" B").append(i + 1).append("(X) = ")))));
/*  367 */       this.capPanel.add(bxPanel[i]);
/*  368 */       tmpexPanel = Function2Panel(this.nAllVariable, this.nTerm, this.controller.data.sumt_Bx[i].fxcoef, this.controller.data.sumt_Bx[i].xexp);
/*  369 */       this.eqtPanel.add(tmpexPanel);
/*      */     }
/*  371 */     for (i = 0; i < this.nVariableWithBound; i++) {
/*  372 */       lxPanel[i] = new JPanel();
/*  373 */       lxPanel[i].setPreferredSize(new Dimension(60, 30));
/*  374 */       lxPanel[i].setMaximumSize(new Dimension(60, 30));
/*  375 */       lxPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" L").append(i + 1).append("(X) = ")))));
/*  376 */       this.capPanel.add(lxPanel[i]);
/*  377 */       tmpexPanel = Function2Panel(this.nAllVariable, this.nTerm, this.controller.data.sumt_Lx[i].fxcoef, this.controller.data.sumt_Lx[i].xexp);
/*  378 */       this.eqtPanel.add(tmpexPanel);
/*      */     }
/*  380 */     this.expPanel.removeAll();
/*  381 */     this.expPanel.add(this.capPanel, 0);
/*  382 */     this.expPanel.add(this.eqtPanel);
/*  383 */     this.upPanel.add(this.expPanel);
/*      */     
/*  385 */     this.upPanel.revalidate();
/*  386 */     this.upPanel.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private JPanel Function2Panel(int numvar, int numtm, double[] coef, int[][] expnt)
/*      */   {
/*  395 */     JPanel rtPanel = new JPanel();
/*  396 */     rtPanel.setLayout(new FlowLayout(0));
/*  397 */     rtPanel.setAlignmentX(0.0F);
/*  398 */     rtPanel.setPreferredSize(new Dimension(numtm * numvar * 40, 30));
/*  399 */     rtPanel.setMaximumSize(new Dimension(numtm * numvar * 40, 30));
/*  400 */     for (int i = 0; i < numtm; i++) {
/*  401 */       if ((i > 0) && (coef[i] > 0.01D)) {
/*  402 */         rtPanel.add(new JLabel(" + "));
/*      */       }
/*      */       else
/*  405 */         rtPanel.add(new JLabel(" "));
/*  406 */       if (Math.abs(coef[i]) > 0.01D) {
/*  407 */         rtPanel.add(new JLabel(this.decfm.format(coef[i])));
/*  408 */         for (int j = 0; j < numvar; j++) {
/*  409 */           if (expnt[i][j] > 1) {
/*  410 */             JLabel xlabel = new JLabel("x");
/*  411 */             xlabel.setFont(new Font("Default", 1, 14));
/*  412 */             rtPanel.add(new ScriptWriter(xlabel, "".concat(String.valueOf(String.valueOf(expnt[i][j]))), String.valueOf(String.valueOf(new StringBuffer("").append(j + 1)))));
/*      */           }
/*  414 */           else if (expnt[i][j] == 1) {
/*  415 */             JLabel xlabel = new JLabel("x");
/*  416 */             xlabel.setFont(new Font("Default", 1, 14));
/*  417 */             rtPanel.add(new ScriptWriter(xlabel, " ", String.valueOf(String.valueOf(new StringBuffer("").append(j + 1)))));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  422 */     return rtPanel;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildBottomPanel()
/*      */   {
/*  430 */     this.oldxstr = "( ";
/*  431 */     this.newxstr = "( ";
/*  432 */     for (int i = 0; i < this.nAllVariable; i++) {
/*  433 */       this.oldxstr = String.valueOf(String.valueOf(this.oldxstr)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.sumt_oldx[i]))));
/*  434 */       this.newxstr = String.valueOf(String.valueOf(this.newxstr)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.sumt_newx[i]))));
/*  435 */       if (i < this.nAllVariable - 1) {
/*  436 */         this.oldxstr = String.valueOf(String.valueOf(this.oldxstr)).concat(" , ");
/*  437 */         this.newxstr = String.valueOf(String.valueOf(this.newxstr)).concat(" , ");
/*      */       }
/*      */     }
/*  440 */     this.oldxstr = String.valueOf(String.valueOf(this.oldxstr)).concat(" )");
/*  441 */     this.newxstr = String.valueOf(String.valueOf(this.newxstr)).concat(" )");
/*  442 */     String trs = String.valueOf(String.valueOf(new StringBuffer("Trial Solution : ").append(this.xname).append(this.oldxstr)));
/*  443 */     this.trialabel.setText(trs);
/*  444 */     this.xlabel.setText(String.valueOf(String.valueOf(this.xname)).concat(String.valueOf(String.valueOf(this.newxstr))));
/*      */     
/*  446 */     this.bottomPanel.removeAll();
/*  447 */     this.bottomPanel.add(this.trialabel);
/*  448 */     this.bottomPanel.add(Box.createVerticalStrut(10));
/*  449 */     this.bottomPanel.add(this.indilabel);
/*  450 */     this.bottomPanel.add(Box.createVerticalStrut(10));
/*  451 */     this.bottomPanel.add(this.xlabel);
/*  452 */     this.bottomPanel.add(Box.createVerticalStrut(10));
/*  453 */     this.bottomPanel.add(this.nxtlabel);
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
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/*  480 */     String str = "Sequential Unconstrained Minimization Technique-SUMT\n\n";
/*  481 */     str = String.valueOf(String.valueOf(str)).concat("This procedure applies the Sequential Unconstrained Minimization Technique (SUMT) ");
/*  482 */     str = String.valueOf(String.valueOf(str)).concat("as presented in Sec. 13.10. The nonlinear programming problem is assumed to be in ");
/*  483 */     str = String.valueOf(String.valueOf(str)).concat("maximization form, with functional constraints, and perhaps with lower-bound ");
/*  484 */     str = String.valueOf(String.valueOf(str)).concat("constraints of the form xj >= Kj for some or all of the individual variables xj, where ");
/*  485 */     str = String.valueOf(String.valueOf(str)).concat("Kj can be 0 or any other value. The objective function and each constraint function ");
/*  486 */     str = String.valueOf(String.valueOf(str)).concat("must be a polynomial where the exponent for each variable in each term is a ");
/*  487 */     str = String.valueOf(String.valueOf(str)).concat("nonnegative integer less than ten. You need to enter the size of the problem, an ");
/*  488 */     str = String.valueOf(String.valueOf(str)).concat("initial trial solution, and P(X; r) = f(X)-r B(X), where f(X) is the objective function and ");
/*  489 */     str = String.valueOf(String.valueOf(str)).concat("B(X) is the barrier function. Each term of B(X) id the boundary repulsion ");
/*  490 */     str = String.valueOf(String.valueOf(str)).concat("term for one of the constraints. For each iteration, you specify the value of r and ");
/*  491 */     str = String.valueOf(String.valueOf(str)).concat("the computer does the rest.\n\n");
/*  492 */     str = String.valueOf(String.valueOf(str)).concat("Number of variables with lower bounds\n\n");
/*  493 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of variable xj that have a lower-bound constraint, xj >= Kj (as described ");
/*  494 */     str = String.valueOf(String.valueOf(str)).concat("above), as a nonnegative integer. The total number of variables (");
/*  495 */     str = String.valueOf(String.valueOf(str)).concat("with or without lower bounds) cannot exceed three.\n\n");
/*  496 */     str = String.valueOf(String.valueOf(str)).concat("Number of variable without lower bounds\n\n");
/*  497 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of variables that do not have a lower-bound constraint, ");
/*  498 */     str = String.valueOf(String.valueOf(str)).concat("xj  >= Kj (as described above, as a nonnegative integer. The total number of ");
/*  499 */     str = String.valueOf(String.valueOf(str)).concat("variables (with or without lower bounds) cannot exceed three.\n\n");
/*  500 */     str = String.valueOf(String.valueOf(str)).concat("Number of inequality constraints\n\n");
/*  501 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of functional constraints (excluding lower-bound constraints, ");
/*  502 */     str = String.valueOf(String.valueOf(str)).concat("xj >= Kj) that are in inequality form. This number must be a nonnegative integer. ");
/*  503 */     str = String.valueOf(String.valueOf(str)).concat("The total number of functional constraints of either type (inequality or equality) ");
/*  504 */     str = String.valueOf(String.valueOf(str)).concat("must be 1, 2, or 3.\n\n");
/*  505 */     str = String.valueOf(String.valueOf(str)).concat("Number of equality constraints\n\n");
/*  506 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of functional constraints (a nonnegative integer) that are in ");
/*  507 */     str = String.valueOf(String.valueOf(str)).concat("equality form. This number must be a nonnegative integer. The total number of ");
/*  508 */     str = String.valueOf(String.valueOf(str)).concat("functional constraints of either type (inequality or equality) must be 1, 2, or 3.\n\n");
/*  509 */     str = String.valueOf(String.valueOf(str)).concat("Maximum of the number of terms in any function\n\n");
/*  510 */     str = String.valueOf(String.valueOf(str)).concat("Enter the maximum of the number of terms in any function. This number must be an integer no greater ");
/*  511 */     str = String.valueOf(String.valueOf(str)).concat("than 10.");
/*  512 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/*  514 */     this.help_content_step.setText(str);
/*  515 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/*  522 */     String str = "Sequential Unconstrained Minimization Technique-SUMT\n\n";
/*  523 */     str = String.valueOf(String.valueOf(str)).concat("This procedure automatically applies the Sequential Unconstrained Optimization Technique ");
/*  524 */     str = String.valueOf(String.valueOf(str)).concat("(SUMT) presented in Sec. 13.10. The upper limits on problem size are three variables ");
/*  525 */     str = String.valueOf(String.valueOf(str)).concat("and three functional constraints. Every function must be a polynomial where the exponent ");
/*  526 */     str = String.valueOf(String.valueOf(str)).concat("for each variable in each term is a nonnegative integer less than ten.\n");
/*  527 */     str = String.valueOf(String.valueOf(str)).concat("You will begin by entering once the unconstrained function P(X; r) to be maximized at ");
/*  528 */     str = String.valueOf(String.valueOf(str)).concat("every iteration, and an initial trial solution. For each iteration, you also will ");
/*  529 */     str = String.valueOf(String.valueOf(str)).concat("specify the value of r to be used. When as many iterations as desired have been executed, ");
/*  530 */     str = String.valueOf(String.valueOf(str)).concat("you can print out both P(X; r) and the sequence of trial solutions (one for each r) by ");
/*  531 */     str = String.valueOf(String.valueOf(str)).concat("choosing Print to file under the File menu.");
/*  532 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/*  534 */     this.help_content_procedure.setText(str);
/*  535 */     this.help_content_procedure.revalidate(); }
/*      */   
/*  537 */   private String str0 = "Sequential Unconstrained Minimization Technique-SUMT\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  549 */   private String str1 = "Number of variables with lower bounds\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  570 */   private String str2 = "Entering the objective function\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  594 */   private String str3 = "To continue\n\nPress the NEXT button.";
/*  595 */   private String str4 = "Entering the value of r\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void updateStepHelpPanel()
/*      */   {
/*  604 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/*  605 */     switch (this.step) {
/*      */     case 1: 
/*  607 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str1)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  627 */       break;
/*      */     case 2: 
/*  610 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str2)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  627 */       break;
/*      */     case 3: 
/*  613 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str3)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  627 */       break;
/*      */     case 4: 
/*  616 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str4)).concat(String.valueOf(String.valueOf(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  627 */       break;
/*      */     default: 
/*  619 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
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
/*      */   private void doBack()
/*      */   {
/*  634 */     if (this.opseq.isEmpty() == false) {
/*  635 */       int last = ((Integer)this.opseq.lastElement()).intValue();
/*  636 */       this.opseq.removeElementAt(this.opseq.size() - 1);
/*      */       
/*  638 */       switch (last)
/*      */       {
/*      */       case 1: 
/*  641 */         this.controller.solver.reset();
/*  642 */         this.state.back();
/*  643 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  646 */         this.mainPanel.removeAll();
/*  647 */         this.mainPanel.add(this.numPanel);
/*  648 */         this.mainPanel.add(Box.createVerticalStrut(30));
/*  649 */         this.mainPanel.add(this.buttPanel);
/*  650 */         this.backbutt.setEnabled(false);
/*  651 */         this.step = 1;
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
/*  723 */         break;
/*      */       case 2: 
/*  658 */         this.controller.solver.reset();
/*  659 */         this.state.back();
/*  660 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  662 */         if (this.rvseq.size() > 0) {
/*  663 */           double rval = ((Double)this.rvseq.lastElement()).doubleValue();
/*  664 */           this.rvseq.removeElementAt(this.rvseq.size() - 1);
/*  665 */           this.rfd.setValue(rval);
/*      */         }
/*      */         
/*  668 */         this.bottomPanel.removeAll();
/*  669 */         this.bottomPanel.add(this.trialPanel);
/*  670 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/*  671 */         this.bottomPanel.add(this.rvalPanel);
/*  672 */         this.mainPanel.removeAll();
/*  673 */         this.mainPanel.add(this.funcPanel);
/*  674 */         this.mainPanel.add(Box.createVerticalStrut(20));
/*  675 */         this.mainPanel.add(this.bottomPanel);
/*  676 */         this.mainPanel.add(Box.createVerticalStrut(20));
/*  677 */         this.mainPanel.add(this.buttPanel);
/*  678 */         this.step = 2;
/*      */         
/*  680 */         revalidate();
/*  681 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  723 */         break;
/*      */       case 3: 
/*  685 */         this.controller.solver.reset();
/*  686 */         this.state.back();
/*  687 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  690 */         BuildBottomPanel();
/*  691 */         this.step = 3;
/*      */         
/*  693 */         revalidate();
/*  694 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  723 */         break;
/*      */       case 4: 
/*  698 */         this.controller.solver.reset();
/*  699 */         this.state.back();
/*  700 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  702 */         if (this.rvseq.size() > 0) {
/*  703 */           double rval = ((Double)this.rvseq.lastElement()).doubleValue();
/*  704 */           this.rvseq.removeElementAt(this.rvseq.size() - 1);
/*  705 */           this.rfd.setValue(rval);
/*      */         }
/*      */         
/*  708 */         this.trialabel.setText(String.valueOf(String.valueOf(new StringBuffer("Trial Solution : ").append(this.xname).append(this.newxstr))));
/*  709 */         this.bottomPanel.removeAll();
/*  710 */         this.bottomPanel.add(this.trialabel);
/*  711 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/*  712 */         this.bottomPanel.add(this.rvalPanel);
/*  713 */         this.step = 4;
/*      */         
/*  715 */         revalidate();
/*  716 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  723 */         break;
/*      */       default: 
/*  719 */         System.out.println("SUMT cannot back here.");break;
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void doNext()
/*      */   {
/*  729 */     int[] number = new int[5];
/*  730 */     double[][] fxcoef = new double[7][this.nTerm];
/*  731 */     int[][][] fxexp = new int[7][this.nTerm][3];
/*      */     
/*      */ 
/*  734 */     Vector par = new Vector();
/*  735 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*  737 */     switch (this.step)
/*      */     {
/*      */     case 1: 
/*  740 */       this.nVariableWithBound = this.NumVariableWithBound.getValue();
/*  741 */       this.nVariableNoBound = this.NumVariableNoBound.getValue();
/*  742 */       this.nAllVariable = (this.nVariableWithBound + this.nVariableNoBound);
/*  743 */       this.nInequality = this.NumInequality.getValue();
/*  744 */       this.nEquality = this.NumEquality.getValue();
/*  745 */       this.nConstraint = (this.nInequality + this.nEquality);
/*  746 */       this.nTerm = this.NumTerm.getValue();
/*  747 */       if (this.nTerm > 10) {
/*  748 */         this.nTerm = 10;
/*  749 */         this.NumTerm.setValue(this.nTerm);
/*      */       }
/*      */       
/*  752 */       number[0] = this.nVariableWithBound;
/*  753 */       number[1] = this.nVariableNoBound;
/*  754 */       number[2] = this.nInequality;
/*  755 */       number[3] = this.nEquality;
/*  756 */       number[4] = this.nTerm;
/*  757 */       par.addElement(number);
/*      */       
/*  759 */       opr.parameters = par;
/*  760 */       opr.operation_code = 50501;
/*  761 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  762 */         String err = this.controller.solver.getErrInfo();
/*  763 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  768 */         this.state.addStep(opr);
/*      */         
/*  770 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/*  773 */         BuildFuncPanel();
/*  774 */         this.mainPanel.removeAll();
/*  775 */         this.mainPanel.add(this.funcPanel);
/*  776 */         this.mainPanel.add(Box.createVerticalStrut(20));
/*  777 */         this.mainPanel.add(this.bottomPanel);
/*  778 */         this.mainPanel.add(Box.createVerticalStrut(20));
/*  779 */         this.mainPanel.add(this.buttPanel);
/*  780 */         this.backbutt.setEnabled(true);
/*  781 */         this.step = 2;
/*      */         
/*  783 */         revalidate();
/*  784 */         repaint();
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
/*  902 */       break;
/*      */     case 2: 
/*  788 */       for (int k = 0; k < this.nConstraint + this.nAllVariable + 1; k++) {
/*  789 */         for (int i = 0; i < this.nTerm; i++) {
/*  790 */           if (k == 0) {
/*  791 */             fxcoef[0][i] = this.ffunction.poly_coef[i].getValue();
/*  792 */           } else if ((k > 0) && (k < this.nConstraint + 1)) {
/*  793 */             fxcoef[k][i] = this.bfunction[(k - 1)].poly_coef[i].getValue();
/*      */           } else {
/*  795 */             fxcoef[k][i] = this.lfunction[(k - this.nConstraint - 1)].poly_coef[i].getValue();
/*      */           }
/*  797 */           for (int j = 0; j < this.nAllVariable; j++) {
/*  798 */             if (k == 0) {
/*  799 */               fxexp[0][i][j] = this.ffunction.poly_exp[i][j].getValue();
/*  800 */             } else if ((k > 0) && (k < this.nConstraint + 1)) {
/*  801 */               fxexp[k][i][j] = this.bfunction[(k - 1)].poly_exp[i][j].getValue();
/*      */             } else {
/*  803 */               fxexp[k][i][j] = this.lfunction[(k - this.nConstraint - 1)].poly_exp[i][j].getValue();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  808 */       for (int i = 0; i < this.nAllVariable; i++) {
/*  809 */         this.inixval[i] = this.inixfd[i].getValue();
/*      */       }
/*  811 */       double rval = this.rfd.getValue();
/*      */       
/*  813 */       par.addElement(fxcoef);
/*  814 */       par.addElement(fxexp);
/*  815 */       par.addElement(this.inixval);
/*  816 */       par.add(new Double(rval));
/*      */       
/*  818 */       opr.parameters = par;
/*  819 */       opr.operation_code = 50502;
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
/*  831 */         this.rvseq.addElement(new Double(rval));
/*      */         
/*  833 */         BuildUpPanel();
/*  834 */         BuildBottomPanel();
/*  835 */         this.mainPanel.removeAll();
/*  836 */         this.mainPanel.add(this.upPanel);
/*  837 */         this.mainPanel.add(Box.createVerticalStrut(20));
/*  838 */         this.mainPanel.add(this.bottomPanel);
/*  839 */         this.mainPanel.add(Box.createVerticalStrut(20));
/*  840 */         this.mainPanel.add(this.buttPanel);
/*  841 */         this.step = 3;
/*      */         
/*  843 */         revalidate();
/*  844 */         repaint();
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
/*  902 */       break;
/*      */     case 3: 
/*  848 */       opr.parameters = par;
/*  849 */       opr.operation_code = 50503;
/*  850 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  851 */         String err = this.controller.solver.getErrInfo();
/*  852 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  857 */         this.state.addStep(opr);
/*      */         
/*  859 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/*  862 */         this.trialabel.setText(String.valueOf(String.valueOf(new StringBuffer("Trial Solution : ").append(this.xname).append(this.newxstr))));
/*  863 */         this.bottomPanel.removeAll();
/*  864 */         this.bottomPanel.add(this.trialabel);
/*  865 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/*  866 */         this.bottomPanel.add(this.rvalPanel);
/*  867 */         this.step = 4;
/*      */         
/*  869 */         revalidate();
/*  870 */         repaint();
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
/*  902 */       break;
/*      */     case 4: 
/*  874 */       double rval = this.rfd.getValue();
/*  875 */       par.addElement(new Double(rval));
/*      */       
/*  877 */       opr.parameters = par;
/*  878 */       opr.operation_code = 50504;
/*  879 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  880 */         String err = this.controller.solver.getErrInfo();
/*  881 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  886 */         this.state.addStep(opr);
/*      */         
/*  888 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*  890 */         this.rvseq.addElement(new Double(rval));
/*      */         
/*  892 */         BuildBottomPanel();
/*  893 */         this.step = 3;
/*      */         
/*  895 */         revalidate();
/*  896 */         repaint();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  902 */       break;
/*      */     default: 
/*  899 */       System.out.println("SUMT cannot next here.");
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
/*  911 */     int[] para = new int[8];
/*  912 */     String[] glstr = new String[4];
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  917 */       this.state = ((IORState)in.readObject());
/*  918 */       para = (int[])in.readObject();
/*  919 */       glstr = (String[])in.readObject();
/*  920 */       this.inixval = ((double[])in.readObject());
/*  921 */       this.opseq = ((Vector)in.readObject());
/*  922 */       this.rvseq = ((Vector)in.readObject());
/*  923 */       this.state.setSolverStepVector();
/*  924 */       in.close();
/*      */     }
/*      */     catch (Exception e1) {
/*  927 */       e1.printStackTrace();
/*  928 */       System.out.println("Open fails");
/*      */     }
/*  930 */     this.step = para[0];
/*  931 */     this.nTerm = para[1];
/*  932 */     this.nVariableWithBound = para[2];
/*  933 */     this.nVariableNoBound = para[3];
/*  934 */     this.nAllVariable = para[4];
/*  935 */     this.nInequality = para[5];
/*  936 */     this.nEquality = para[6];
/*  937 */     this.nConstraint = para[7];
/*      */     
/*  939 */     this.pxs = glstr[0];
/*  940 */     this.xname = glstr[1];
/*  941 */     this.oldxstr = glstr[2];
/*  942 */     this.newxstr = glstr[3];
/*      */     
/*  944 */     if (this.step >= 1) {
/*  945 */       this.NumTerm.setValue(this.nTerm);
/*  946 */       this.NumVariableWithBound.setValue(this.nVariableWithBound);
/*  947 */       this.NumVariableNoBound.setValue(this.nVariableNoBound);
/*  948 */       this.NumInequality.setValue(this.nInequality);
/*  949 */       this.NumEquality.setValue(this.nEquality);
/*      */     }
/*  951 */     if (this.step >= 2) {
/*  952 */       BuildFuncPanel();
/*      */       
/*  954 */       for (int k = 0; k < this.nConstraint + this.nAllVariable + 1; k++) {
/*  955 */         for (int i = 0; i < this.nTerm; i++) {
/*  956 */           if (k == 0) {
/*  957 */             this.ffunction.poly_coef[i].setValue(this.controller.data.sumt_objective.fxcoef[i]);
/*  958 */           } else if ((k > 0) && (k < this.nConstraint + 1)) {
/*  959 */             this.bfunction[(k - 1)].poly_coef[i].setValue(this.controller.data.sumt_Bx[(k - 1)].fxcoef[i]);
/*      */           } else {
/*  961 */             this.lfunction[(k - this.nConstraint - 1)].poly_coef[i].setValue(this.controller.data.sumt_Lx[(k - this.nConstraint - 1)].fxcoef[i]);
/*      */           }
/*  963 */           for (int j = 0; j < this.nAllVariable; j++) {
/*  964 */             if (k == 0) {
/*  965 */               this.ffunction.poly_exp[i][j].setValue(this.controller.data.sumt_objective.xexp[i][j]);
/*  966 */             } else if ((k > 0) && (k < this.nConstraint + 1)) {
/*  967 */               this.bfunction[(k - 1)].poly_exp[i][j].setValue(this.controller.data.sumt_Bx[(k - 1)].xexp[i][j]);
/*      */             } else {
/*  969 */               this.lfunction[(k - this.nConstraint - 1)].poly_exp[i][j].setValue(this.controller.data.sumt_Lx[(k - this.nConstraint - 1)].xexp[i][j]);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  974 */       for (int i = 0; i < this.nAllVariable; i++) {
/*  975 */         this.inixfd[i].setValue(this.inixval[i]);
/*      */       }
/*  977 */       this.rfd.setValue(this.controller.data.sumt_r);
/*      */       
/*  979 */       this.mainPanel.removeAll();
/*  980 */       this.mainPanel.add(this.funcPanel);
/*  981 */       this.mainPanel.add(Box.createVerticalStrut(20));
/*  982 */       this.mainPanel.add(this.bottomPanel);
/*  983 */       this.mainPanel.add(Box.createVerticalStrut(20));
/*  984 */       this.mainPanel.add(this.buttPanel);
/*  985 */       this.backbutt.setEnabled(true);
/*      */     }
/*  987 */     if (this.step >= 3) {
/*  988 */       BuildUpPanel();
/*  989 */       BuildBottomPanel();
/*  990 */       this.mainPanel.removeAll();
/*  991 */       this.mainPanel.add(this.upPanel);
/*  992 */       this.mainPanel.add(Box.createVerticalStrut(20));
/*  993 */       this.mainPanel.add(this.bottomPanel);
/*  994 */       this.mainPanel.add(Box.createVerticalStrut(20));
/*  995 */       this.mainPanel.add(this.buttPanel);
/*      */     }
/*  997 */     if (this.step >= 4) {
/*  998 */       this.trialabel.setText(String.valueOf(String.valueOf(new StringBuffer("Trial Solution : ").append(this.xname).append(this.newxstr))));
/*  999 */       this.bottomPanel.removeAll();
/* 1000 */       this.bottomPanel.add(this.trialabel);
/* 1001 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/* 1002 */       this.bottomPanel.add(this.rvalPanel);
/*      */     }
/* 1004 */     if (this.step >= 5) {
/* 1005 */       System.out.println("NL SUMT has no this step.");
/*      */     }
/* 1007 */     revalidate();
/* 1008 */     repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFile(ObjectOutputStream out)
/*      */   {
/* 1018 */     int[] interpara = new int[8];
/* 1019 */     String[] interstr = new String[4];
/*      */     
/* 1021 */     interpara[0] = this.step;
/* 1022 */     interpara[1] = this.nTerm;
/* 1023 */     interpara[2] = this.nVariableWithBound;
/* 1024 */     interpara[3] = this.nVariableNoBound;
/* 1025 */     interpara[4] = this.nAllVariable;
/* 1026 */     interpara[5] = this.nInequality;
/* 1027 */     interpara[6] = this.nEquality;
/* 1028 */     interpara[7] = this.nConstraint;
/*      */     
/* 1030 */     interstr[0] = this.pxs;
/* 1031 */     interstr[1] = this.xname;
/* 1032 */     interstr[2] = this.oldxstr;
/* 1033 */     interstr[3] = this.newxstr;
/*      */     try {
/* 1035 */       out.writeObject(this.state);
/* 1036 */       out.writeObject(interpara);
/* 1037 */       out.writeObject(interstr);
/* 1038 */       out.writeObject(this.inixval);
/* 1039 */       out.writeObject(this.opseq);
/* 1040 */       out.writeObject(this.rvseq);
/* 1041 */       out.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1044 */       e1.printStackTrace();
/* 1045 */       System.out.println("Save fails");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFunction()
/*      */   {
/* 1057 */     double[][] fxcoef = new double[7][this.nTerm];
/* 1058 */     int[][][] fxexp = new int[7][this.nTerm][3];
/*      */     
/*      */ 
/* 1061 */     Vector par = new Vector();
/* 1062 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/* 1064 */     if (this.step == 2) {
/* 1065 */       for (int k = 0; k < this.nConstraint + this.nAllVariable + 1; k++) {
/* 1066 */         for (int i = 0; i < this.nTerm; i++) {
/* 1067 */           if (k == 0) {
/* 1068 */             fxcoef[0][i] = this.ffunction.poly_coef[i].getValue();
/* 1069 */           } else if ((k > 0) && (k < this.nConstraint + 1)) {
/* 1070 */             fxcoef[k][i] = this.bfunction[(k - 1)].poly_coef[i].getValue();
/*      */           } else {
/* 1072 */             fxcoef[k][i] = this.lfunction[(k - this.nConstraint - 1)].poly_coef[i].getValue();
/*      */           }
/* 1074 */           for (int j = 0; j < this.nAllVariable; j++) {
/* 1075 */             if (k == 0) {
/* 1076 */               fxexp[0][i][j] = this.ffunction.poly_exp[i][j].getValue();
/* 1077 */             } else if ((k > 0) && (k < this.nConstraint + 1)) {
/* 1078 */               fxexp[k][i][j] = this.bfunction[(k - 1)].poly_exp[i][j].getValue();
/*      */             } else {
/* 1080 */               fxexp[k][i][j] = this.lfunction[(k - this.nConstraint - 1)].poly_exp[i][j].getValue();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1085 */       for (int i = 0; i < this.nAllVariable; i++) {
/* 1086 */         this.inixval[i] = this.inixfd[i].getValue();
/*      */       }
/* 1088 */       double rval = this.rfd.getValue();
/*      */       
/* 1090 */       par.addElement(fxcoef);
/* 1091 */       par.addElement(fxexp);
/* 1092 */       par.addElement(this.inixval);
/* 1093 */       par.add(new Double(rval));
/*      */       
/* 1095 */       opr.parameters = par;
/* 1096 */       opr.operation_code = 50502;
/* 1097 */       this.controller.solver.doWork(opr, this.controller.data);
/*      */     }
/*      */   }
/*      */   
/*      */   public class IORPolynomial extends JPanel
/*      */   {
/*      */     public int num_term;
/*      */     public int num_variable;
/*      */     public DecimalField[] poly_coef;
/*      */     public WholeNumberField[][] poly_exp;
/* 1107 */     private DecimalFormat dfm = new DecimalFormat();
/*      */     
/*      */     public IORPolynomial(int numvar, int numterm, String polyname) {
/* 1110 */       this.num_variable = numvar;
/* 1111 */       this.num_term = numterm;
/*      */       
/* 1113 */       setAlignmentX(0.0F);
/*      */       
/*      */ 
/*      */ 
/* 1117 */       this.poly_coef = new DecimalField[numterm];
/* 1118 */       this.poly_exp = new WholeNumberField[numterm][3];
/*      */       
/* 1120 */       for (int i = 0; i < numterm; i++) {
/* 1121 */         this.poly_coef[i] = new DecimalField(0.0D, 4, this.dfm);
/* 1122 */         for (int j = 0; j < 3; j++) {
/* 1123 */           this.poly_exp[i][j] = new WholeNumberField(0, 1);
/*      */         }
/*      */       }
/* 1126 */       add(new JLabel(String.valueOf(String.valueOf(polyname)).concat(" =  ")));
/* 1127 */       for (i = 0; i < numterm; i++) {
/* 1128 */         if (i > 0)
/* 1129 */           add(new JLabel(" + "));
/* 1130 */         add(this.poly_coef[i]);
/* 1131 */         for (int j = 0; j < numvar; j++) {
/* 1132 */           add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" x").append(j + 1)))));
/* 1133 */           add(this.poly_exp[i][j]);
/*      */         }
/*      */       }
/*      */       
/* 1137 */       revalidate();
/* 1138 */       repaint();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNLSumtPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */