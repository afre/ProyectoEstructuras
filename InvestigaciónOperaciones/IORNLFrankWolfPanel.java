/*      */ import java.awt.Dimension;
/*      */ import java.awt.FlowLayout;
/*      */ import java.awt.Font;
/*      */ import java.awt.GridLayout;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
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
/*      */ import javax.swing.table.TableColumn;
/*      */ 
/*      */ public class IORNLFrankWolfPanel
/*      */   extends IORActionPanel
/*      */ {
/*   29 */   private final String LESSTHAN = "<=";
/*   30 */   private final String GREATTHAN = ">=";
/*      */   
/*   32 */   private static final int MAXVARS = 3;
/*   33 */   private static final int MAXCONTS = 5;
/*   34 */   private static final int MAXTERMS = 10;
/*      */   
/*   36 */   private IORNLPProcessController controller = null;
/*      */   
/*   38 */   private JPanel mainPanel = new JPanel();
/*      */   
/*   40 */   private JPanel setupPanel = null;
/*   41 */   private JPanel gradPanel = null;
/*   42 */   private JPanel lpfuncPanel = null;
/*   43 */   private JPanel upPanel = null;
/*   44 */   private JPanel bottomPanel = null;
/*   45 */   private JPanel xtPanel = null;
/*   46 */   private JPanel newxPanel = null;
/*   47 */   private JPanel abinPanel = null;
/*   48 */   private JScrollPane scrlPane = null;
/*   49 */   private JPanel trialPanel = new JPanel();
/*   50 */   private JPanel buttPanel = new JPanel();
/*   51 */   private JPanel namePanel = new JPanel();
/*   52 */   private JPanel fieldPanel = new JPanel();
/*   53 */   private JPanel[] dfxPanel = new JPanel[3];
/*   54 */   private JPanel dervPanel = new JPanel();
/*   55 */   private JPanel fexPanel = new JPanel();
/*   56 */   private JPanel expPanel = new JPanel();
/*      */   
/*   58 */   private JPanel inputPanel = null;
/*   59 */   private JPanel numPanel = null;
/*   60 */   private JPanel objectivePanel = null;
/*   61 */   private JPanel constraintPanel = null;
/*      */   
/*   63 */   private IORLinearEquation[] linear_equations = null;
/*      */   
/*   65 */   private JPanel[] aPanel = new JPanel[3]; private JPanel[] bPanel = new JPanel[3]; private JPanel[] abtPanel = new JPanel[3];
/*   66 */   private JLabel boundlabel = new JLabel();
/*   67 */   private JTextArea inditext = new JTextArea();
/*   68 */   private JLabel indilabel = new JLabel();
/*      */   
/*   70 */   private JLabel[] gradlabel = new JLabel[3];
/*   71 */   private JLabel trialabel = new JLabel();
/*   72 */   private JLabel zlabel = new JLabel();
/*   73 */   private JLabel trsulabel = new JLabel();
/*   74 */   private JLabel lpsulabel = new JLabel();
/*   75 */   private JLabel htlabel = new JLabel();
/*      */   
/*   77 */   private JLabel subjlabel = new JLabel("Subject to :");
/*   78 */   private JLabel comtlabel = new JLabel("(To make a new number above take effect, you must press the ENTER key.)");
/*   79 */   private JLabel instructionlabel = new JLabel("For each term, enter the coefficient on the left and the exponents on the right.");
/*      */   
/*   81 */   private IORNLFrankWolfPanel.IORFWTableModel fwModel = null;
/*   82 */   private JTable table = null;
/*      */   
/*   84 */   private JButton backbutt = new JButton("Back");
/*   85 */   private JButton nextbutt = new JButton("Next");
/*   86 */   private JComboBox maxmin = new JComboBox();
/*      */   private DecimalField[] fxcoef;
/*      */   private DecimalField[] dfx1coef;
/*      */   private DecimalField[] dfx2coef;
/*      */   private DecimalField[] dfx3coef;
/*   91 */   private DecimalField[] inixfd; private DecimalField[] afd; private DecimalField[] bfd; private DecimalField[] xlpfd; private DecimalField[] xtafd; private DecimalField[] xtbfd; private DecimalField[] xtcfd; private WholeNumberField[][] fxexp; private WholeNumberField[][] dfx1_xexp; private WholeNumberField[][] dfx2_xexp; private WholeNumberField[][] dfx3_xexp; private WholeNumberField NumTerm = new WholeNumberField(4, 2);
/*   92 */   private WholeNumberField NumVariable = new WholeNumberField(2, 2);
/*   93 */   private WholeNumberField NumConstraint = new WholeNumberField(3, 2);
/*      */   
/*   95 */   private int step = 1;
/*   96 */   private int itercount = 1;
/*   97 */   private int nTerm = 4;
/*   98 */   private int nVariable = 2;
/*   99 */   private int nConstraint = 3;
/*  100 */   private DecimalFormat decfm = new DecimalFormat();
/*      */   
/*  102 */   private Vector opseq = new Vector();
/*      */   
/*  104 */   private String xname = ""; private String zname = ""; private String fname = "";
/*  105 */   private String theastr = ""; private String thebstr = ""; private String thexstr = ""; private String thelpstr = ""; private String newxstr = "";
/*  106 */   private String curstr = "Current Trial Solution :";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IORNLFrankWolfPanel(IORNLPProcessController c)
/*      */   {
/*  115 */     super(c);
/*  116 */     this.controller = c;
/*  117 */     this.state = new IORState(this.controller.solver);
/*  118 */     add(this.mainPanel);
/*  119 */     this.actionStatus.setVisible(false);
/*  120 */     this.bn_back.setVisible(false);
/*  121 */     this.bn_finish.setVisible(false);
/*      */     
/*  123 */     this.decfm.setGroupingUsed(false);
/*  124 */     this.inditext.setEditable(false);
/*      */     
/*      */ 
/*  127 */     this.inixfd = new DecimalField[3];
/*  128 */     this.xlpfd = new DecimalField[3];
/*  129 */     this.afd = new DecimalField[3];
/*  130 */     this.bfd = new DecimalField[3];
/*  131 */     this.xtafd = new DecimalField[3];
/*  132 */     this.xtbfd = new DecimalField[3];
/*  133 */     this.xtcfd = new DecimalField[3];
/*      */     
/*  135 */     for (int i = 0; i < 3; i++) {
/*  136 */       this.inixfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  137 */       this.xlpfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  138 */       this.afd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  139 */       this.bfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  140 */       this.xtafd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  141 */       this.xtbfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  142 */       this.xtcfd[i] = new DecimalField(0.0D, 4, this.decfm);
/*  143 */       this.gradlabel[i] = new JLabel();
/*      */     }
/*      */     
/*  146 */     this.buttPanel.setPreferredSize(new Dimension(300, 40));
/*  147 */     this.buttPanel.setMaximumSize(new Dimension(300, 40));
/*  148 */     this.buttPanel.add(this.backbutt);
/*  149 */     this.buttPanel.add(Box.createHorizontalStrut(10));
/*  150 */     this.buttPanel.add(this.nextbutt);
/*  151 */     this.backbutt.setEnabled(false);
/*      */     
/*  153 */     this.setupPanel = new JPanel();
/*  154 */     this.setupPanel.setLayout(new BoxLayout(this.setupPanel, 1));
/*      */     
/*  156 */     this.numPanel = new JPanel();
/*  157 */     this.numPanel.setPreferredSize(new Dimension(300, 80));
/*  158 */     this.numPanel.setMaximumSize(new Dimension(300, 80));
/*      */     
/*  160 */     this.namePanel.setLayout(new BoxLayout(this.namePanel, 1));
/*  161 */     this.namePanel.add(new JLabel("Number of Variables (<= 3) :"));
/*  162 */     this.namePanel.add(Box.createVerticalStrut(6));
/*  163 */     this.namePanel.add(new JLabel("Number of Constraints (<= 5) :"));
/*  164 */     this.namePanel.add(Box.createVerticalStrut(6));
/*  165 */     this.namePanel.add(new JLabel("Number of Terms in f(X) (<= 10) :"));
/*  166 */     this.fieldPanel.setLayout(new BoxLayout(this.fieldPanel, 1));
/*  167 */     this.fieldPanel.add(this.NumVariable);
/*  168 */     this.fieldPanel.add(Box.createVerticalStrut(4));
/*  169 */     this.fieldPanel.add(this.NumConstraint);
/*  170 */     this.fieldPanel.add(Box.createVerticalStrut(4));
/*  171 */     this.fieldPanel.add(this.NumTerm);
/*      */     
/*  173 */     this.numPanel.add(this.namePanel);
/*  174 */     this.numPanel.add(Box.createHorizontalStrut(10));
/*  175 */     this.numPanel.add(this.fieldPanel);
/*      */     
/*  177 */     this.maxmin.addItem("Max");
/*  178 */     this.maxmin.addItem("Min");
/*  179 */     this.objectivePanel = new JPanel();
/*      */     
/*  181 */     this.subjlabel.setAlignmentX(0.0F);
/*  182 */     this.boundlabel.setAlignmentX(0.0F);
/*  183 */     this.comtlabel.setAlignmentX(0.5F);
/*  184 */     this.instructionlabel.setAlignmentX(0.5F);
/*  185 */     this.constraintPanel = new JPanel();
/*  186 */     this.constraintPanel.setLayout(new BoxLayout(this.constraintPanel, 1));
/*  187 */     this.constraintPanel.setAlignmentX(0.5F);
/*  188 */     this.boundlabel.setBorder(new EmptyBorder(10, 0, 10, 0));
/*      */     
/*  190 */     this.setupPanel.add(this.numPanel);
/*  191 */     this.setupPanel.add(Box.createVerticalStrut(10));
/*  192 */     this.setupPanel.add(this.comtlabel);
/*  193 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*  194 */     this.setupPanel.add(this.instructionlabel);
/*  195 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*  196 */     this.setupPanel.add(this.objectivePanel);
/*  197 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*  198 */     this.setupPanel.add(this.constraintPanel);
/*  199 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*      */     
/*  201 */     BuildSetupPanel();
/*      */     
/*  203 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*  204 */     this.mainPanel.setBorder(new EmptyBorder(20, 10, 20, 10));
/*  205 */     this.mainPanel.add(this.setupPanel);
/*  206 */     this.mainPanel.add(this.buttPanel);
/*      */     
/*  208 */     this.gradPanel = new JPanel();
/*  209 */     this.gradPanel.setLayout(new BoxLayout(this.gradPanel, 1));
/*  210 */     this.gradPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  211 */     this.gradPanel.setAlignmentX(0.5F);
/*      */     
/*  213 */     this.lpfuncPanel = new JPanel();
/*  214 */     this.lpfuncPanel.setLayout(new BoxLayout(this.lpfuncPanel, 1));
/*  215 */     this.lpfuncPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  216 */     this.lpfuncPanel.setAlignmentX(0.5F);
/*      */     
/*  218 */     this.upPanel = new JPanel();
/*  219 */     this.upPanel.setLayout(new BoxLayout(this.upPanel, 1));
/*  220 */     this.upPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  221 */     this.upPanel.setAlignmentX(0.5F);
/*      */     
/*  223 */     this.bottomPanel = new JPanel();
/*  224 */     this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel, 1));
/*  225 */     this.bottomPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  226 */     this.bottomPanel.setAlignmentX(0.5F);
/*      */     
/*  228 */     this.xtPanel = new JPanel();
/*  229 */     this.xtPanel.setLayout(new BoxLayout(this.xtPanel, 1));
/*  230 */     this.xtPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  231 */     this.xtPanel.setAlignmentX(0.5F);
/*      */     
/*  233 */     this.newxPanel = new JPanel();
/*  234 */     this.newxPanel.setLayout(new BoxLayout(this.newxPanel, 1));
/*  235 */     this.newxPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
/*  236 */     this.newxPanel.setAlignmentX(0.5F);
/*      */     
/*  238 */     this.abinPanel = new JPanel();
/*  239 */     this.abinPanel.setLayout(new BoxLayout(this.abinPanel, 1));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  245 */     this.fwModel = new IORNLFrankWolfPanel.IORFWTableModel();
/*  246 */     this.table = new JTable(this.fwModel);
/*  247 */     this.scrlPane = new JScrollPane(this.table);
/*  248 */     this.table.setCellSelectionEnabled(true);
/*  249 */     this.table.setEnabled(false);
/*  250 */     this.table.getColumn("k").setPreferredWidth(10);
/*  251 */     this.table.getColumn("t*").setPreferredWidth(20);
/*      */     
/*      */ 
/*  254 */     this.nextbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  256 */         IORNLFrankWolfPanel.this.doNext();
/*      */       }
/*  258 */     });
/*  259 */     this.backbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  261 */         IORNLFrankWolfPanel.this.doBack();
/*      */       }
/*      */       
/*  264 */     });
/*  265 */     this.NumTerm.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  267 */         IORNLFrankWolfPanel.this.setNumTerm();
/*      */       }
/*      */       
/*  270 */     });
/*  271 */     this.NumVariable.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  273 */         IORNLFrankWolfPanel.this.setNumVariable();
/*      */       }
/*      */       
/*  276 */     });
/*  277 */     this.NumConstraint.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  279 */         IORNLFrankWolfPanel.this.setNumConstraint();
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void BuildSetupPanel()
/*      */   {
/*  288 */     double[] c = new double[10];
/*  289 */     String str = new String();
/*      */     
/*  291 */     this.nTerm = this.NumTerm.getValue();
/*  292 */     this.nVariable = this.NumVariable.getValue();
/*  293 */     this.nConstraint = this.NumConstraint.getValue();
/*      */     
/*  295 */     if (this.nTerm > 10) {
/*  296 */       this.nTerm = 10;
/*  297 */       this.NumTerm.setValue(this.nTerm);
/*      */     }
/*  299 */     if (this.nVariable > 3) {
/*  300 */       this.nVariable = 3;
/*  301 */       this.NumVariable.setValue(3);
/*      */     }
/*  303 */     if (this.nConstraint > 5) {
/*  304 */       this.nConstraint = 5;
/*  305 */       this.NumConstraint.setValue(5);
/*      */     }
/*      */     
/*  308 */     this.fxcoef = new DecimalField[this.nTerm];
/*  309 */     for (int i = 0; i < this.nTerm; i++) {
/*  310 */       this.fxcoef[i] = new DecimalField(0.0D, 3, this.decfm);
/*      */     }
/*      */     
/*  313 */     this.fxexp = new WholeNumberField[this.nTerm][3];
/*  314 */     for (i = 0; i < this.nTerm; i++) {
/*  315 */       for (int j = 0; j < 3; j++) {
/*  316 */         this.fxexp[i][j] = new WholeNumberField(0, 1);
/*      */       }
/*      */     }
/*      */     
/*  320 */     this.objectivePanel.removeAll();
/*  321 */     this.objectivePanel.add(this.maxmin);
/*  322 */     this.objectivePanel.add(new JLabel(" f(X) =  "));
/*      */     
/*  324 */     for (i = 0; i < this.nTerm; i++) {
/*  325 */       if (i > 0) this.objectivePanel.add(new JLabel(" + "));
/*  326 */       this.objectivePanel.add(this.fxcoef[i]);
/*  327 */       for (int j = 0; j < this.nVariable; j++) {
/*  328 */         this.objectivePanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  329 */         this.objectivePanel.add(this.fxexp[i][j]);
/*      */       }
/*      */     }
/*      */     
/*  333 */     this.constraintPanel.removeAll();
/*  334 */     this.constraintPanel.add(this.subjlabel);
/*  335 */     this.linear_equations = new IORLinearEquation[5];
/*  336 */     for (i = 0; i < this.nConstraint; i++) {
/*  337 */       this.linear_equations[i] = new IORLinearEquation(this.nVariable, 0, c);
/*  338 */       this.linear_equations[i].setMaximumSize(new Dimension(420, 50));
/*  339 */       this.linear_equations[i].setAlignmentX(0.0F);
/*  340 */       this.constraintPanel.add(this.linear_equations[i]);
/*      */     }
/*      */     
/*  343 */     str = " and    ";
/*  344 */     for (i = 0; i < this.nVariable; i++) {
/*  345 */       str = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(str))).append("x").append(i + 1).append(" ").append(">=").append(" 0     ")));
/*      */     }
/*  347 */     this.boundlabel.setText(str);
/*  348 */     this.constraintPanel.add(this.boundlabel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setNumVariable()
/*      */   {
/*  356 */     int oldv = this.nVariable;
/*  357 */     if ((this.NumVariable.getValue() <= 3) && (this.NumVariable.getValue() > 0)) {
/*  358 */       this.nVariable = this.NumVariable.getValue();
/*      */     }
/*      */     else {
/*  361 */       this.NumVariable.setValue(oldv);
/*      */     }
/*  363 */     if (oldv != this.nVariable) {
/*  364 */       this.NumVariable.setValue(this.nVariable);
/*      */       
/*  366 */       this.objectivePanel.removeAll();
/*  367 */       this.objectivePanel.add(this.maxmin);
/*  368 */       this.objectivePanel.add(new JLabel(" f(x) =  "));
/*  369 */       for (int i = 0; i < this.nTerm; i++) {
/*  370 */         if (i > 0) this.objectivePanel.add(new JLabel(" + "));
/*  371 */         this.objectivePanel.add(this.fxcoef[i]);
/*  372 */         for (int j = 0; j < this.nVariable; j++) {
/*  373 */           this.objectivePanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("X").append(j + 1)))));
/*  374 */           this.objectivePanel.add(this.fxexp[i][j]);
/*      */         }
/*      */       }
/*      */       
/*  378 */       for (i = 0; i < this.nConstraint; i++) {
/*  379 */         this.linear_equations[i].setNumVariable(this.nVariable);
/*      */       }
/*      */       
/*  382 */       String str = " and    ";
/*  383 */       for (i = 0; i < this.nVariable; i++) {
/*  384 */         str = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(str))).append("x").append(i + 1).append(" >= 0 .    ")));
/*      */       }
/*  386 */       this.boundlabel.setText(str);
/*      */       
/*  388 */       revalidate();
/*  389 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setNumConstraint()
/*      */   {
/*  398 */     int oldv = this.nConstraint;
/*  399 */     if ((this.NumConstraint.getValue() <= 5) && (this.NumConstraint.getValue() > 0)) {
/*  400 */       this.nConstraint = this.NumConstraint.getValue();
/*      */     }
/*      */     else {
/*  403 */       this.NumConstraint.setValue(oldv);
/*      */     }
/*  405 */     if (oldv != this.nConstraint) {
/*  406 */       this.NumConstraint.setValue(this.nConstraint);
/*      */       
/*  408 */       if (oldv < this.nConstraint) {
/*  409 */         this.constraintPanel.remove(this.boundlabel);
/*  410 */         for (int i = oldv; i < this.nConstraint; i++) {
/*  411 */           this.linear_equations[i] = new IORLinearEquation(this.nVariable);
/*  412 */           this.linear_equations[i].setMaximumSize(new Dimension(420, 50));
/*  413 */           this.linear_equations[i].setAlignmentX(0.0F);
/*  414 */           this.constraintPanel.add(this.linear_equations[i]);
/*      */         }
/*  416 */         this.constraintPanel.add(this.boundlabel);
/*      */       }
/*      */       else {
/*  419 */         for (int i = this.nConstraint; i < oldv; i++) {
/*  420 */           this.constraintPanel.remove(this.linear_equations[i]);
/*  421 */           this.linear_equations[i] = null;
/*      */         }
/*      */       }
/*  424 */       revalidate();
/*  425 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setNumTerm()
/*      */   {
/*  434 */     int oldv = this.nTerm;
/*  435 */     if ((this.NumTerm.getValue() <= 10) && (this.NumTerm.getValue() > 0)) {
/*  436 */       this.nTerm = this.NumTerm.getValue();
/*      */     }
/*      */     else {
/*  439 */       this.NumTerm.setValue(oldv);
/*      */     }
/*  441 */     if (oldv != this.nTerm) {
/*  442 */       this.NumTerm.setValue(this.nTerm);
/*      */       
/*  444 */       this.fxcoef = new DecimalField[this.nTerm];
/*  445 */       for (int i = 0; i < this.nTerm; i++) {
/*  446 */         this.fxcoef[i] = new DecimalField(0.0D, 3, this.decfm);
/*      */       }
/*      */       
/*  449 */       this.fxexp = new WholeNumberField[this.nTerm][3];
/*  450 */       for (i = 0; i < this.nTerm; i++) {
/*  451 */         for (int j = 0; j < 3; j++) {
/*  452 */           this.fxexp[i][j] = new WholeNumberField(0, 1);
/*      */         }
/*      */       }
/*      */       
/*  456 */       this.objectivePanel.removeAll();
/*  457 */       this.objectivePanel.add(this.maxmin);
/*  458 */       this.objectivePanel.add(new JLabel(" f(x) =  "));
/*  459 */       for (i = 0; i < this.nTerm; i++) {
/*  460 */         if (i > 0) this.objectivePanel.add(new JLabel(" + "));
/*  461 */         this.objectivePanel.add(this.fxcoef[i]);
/*  462 */         for (int j = 0; j < this.nVariable; j++) {
/*  463 */           this.objectivePanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  464 */           this.objectivePanel.add(this.fxexp[i][j]);
/*      */         }
/*      */       }
/*  467 */       revalidate();
/*  468 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildGradPanel()
/*      */   {
/*  477 */     this.dfx1coef = new DecimalField[this.nTerm];
/*  478 */     this.dfx2coef = new DecimalField[this.nTerm];
/*  479 */     this.dfx3coef = new DecimalField[this.nTerm];
/*      */     
/*  481 */     this.dfx1_xexp = new WholeNumberField[this.nTerm][3];
/*  482 */     this.dfx2_xexp = new WholeNumberField[this.nTerm][3];
/*  483 */     this.dfx3_xexp = new WholeNumberField[this.nTerm][3];
/*      */     
/*  485 */     for (int i = 0; i < this.nTerm; i++) {
/*  486 */       this.dfx1coef[i] = new DecimalField(0.0D, 3, this.decfm);
/*  487 */       for (int j = 0; j < 3; j++) {
/*  488 */         this.dfx1_xexp[i][j] = new WholeNumberField(0, 1);
/*      */       }
/*      */     }
/*  491 */     if (this.nVariable >= 2) {
/*  492 */       for (i = 0; i < this.nTerm; i++) {
/*  493 */         this.dfx2coef[i] = new DecimalField(0.0D, 3, this.decfm);
/*  494 */         for (int j = 0; j < 3; j++) {
/*  495 */           this.dfx2_xexp[i][j] = new WholeNumberField(0, 1);
/*      */         }
/*      */       }
/*      */     }
/*  499 */     if (this.nVariable == 3) {
/*  500 */       for (i = 0; i < this.nTerm; i++) {
/*  501 */         this.dfx3coef[i] = new DecimalField(0.0D, 3, this.decfm);
/*  502 */         for (int j = 0; j < 3; j++) {
/*  503 */           this.dfx3_xexp[i][j] = new WholeNumberField(0, 1);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  508 */     this.gradPanel.removeAll();
/*      */     
/*  510 */     if (this.controller.data.is_max) {
/*  511 */       this.fname = "Max  f(X) = ";
/*      */     } else {
/*  513 */       this.fname = "Min  f(X) = ";
/*      */     }
/*  515 */     this.fexPanel.setAlignmentX(0.0F);
/*  516 */     this.fexPanel.setMaximumSize(new Dimension(this.nTerm * this.nVariable * 40 + 200, 30));
/*  517 */     this.expPanel = Function2Panel(this.nVariable, this.nTerm, this.controller.data.fw_objective.fxcoef, this.controller.data.fw_objective.xexp);
/*  518 */     this.fexPanel.removeAll();
/*  519 */     this.fexPanel.add(new JLabel("   ".concat(String.valueOf(String.valueOf(this.fname)))));
/*  520 */     this.fexPanel.add(this.expPanel);
/*      */     
/*  522 */     JPanel titlePanel = new JPanel();
/*  523 */     titlePanel.setLayout(new BoxLayout(titlePanel, 1));
/*  524 */     titlePanel.add(new JLabel("Objective Function : "));
/*  525 */     titlePanel.add(Box.createVerticalStrut(5));
/*  526 */     titlePanel.add(this.fexPanel);
/*  527 */     titlePanel.add(Box.createVerticalStrut(10));
/*  528 */     titlePanel.add(new JLabel("Derivatives of the Objective function : "));
/*  529 */     titlePanel.add(Box.createVerticalStrut(10));
/*  530 */     this.gradPanel.add(titlePanel);
/*      */     
/*  532 */     this.dfxPanel[0] = new JPanel();
/*  533 */     this.dfxPanel[0].setAlignmentX(0.0F);
/*  534 */     this.dfxPanel[0].setPreferredSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  535 */     this.dfxPanel[0].setMaximumSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  536 */     this.dfxPanel[0].add(new JLabel("df(X)/dx1 =  "));
/*  537 */     for (i = 0; i < this.nTerm; i++) {
/*  538 */       if (i > 0) this.dfxPanel[0].add(new JLabel(" + "));
/*  539 */       this.dfxPanel[0].add(this.dfx1coef[i]);
/*  540 */       for (int j = 0; j < this.nVariable; j++) {
/*  541 */         this.dfxPanel[0].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  542 */         this.dfxPanel[0].add(this.dfx1_xexp[i][j]);
/*      */       }
/*      */     }
/*  545 */     this.gradPanel.add(this.dfxPanel[0]);
/*      */     
/*  547 */     if (this.nVariable >= 2) {
/*  548 */       this.dfxPanel[1] = new JPanel();
/*  549 */       this.dfxPanel[1].setAlignmentX(0.0F);
/*  550 */       this.dfxPanel[1].setPreferredSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  551 */       this.dfxPanel[1].setMaximumSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  552 */       this.dfxPanel[1].add(new JLabel("df(X)/dx2 =  "));
/*  553 */       for (i = 0; i < this.nTerm; i++) {
/*  554 */         if (i > 0) this.dfxPanel[1].add(new JLabel(" + "));
/*  555 */         this.dfxPanel[1].add(this.dfx2coef[i]);
/*  556 */         for (int j = 0; j < this.nVariable; j++) {
/*  557 */           this.dfxPanel[1].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  558 */           this.dfxPanel[1].add(this.dfx2_xexp[i][j]);
/*      */         }
/*      */       }
/*  561 */       this.gradPanel.add(this.dfxPanel[1]);
/*      */     }
/*      */     
/*  564 */     if (this.nVariable == 3) {
/*  565 */       this.dfxPanel[2] = new JPanel();
/*  566 */       this.dfxPanel[2].setAlignmentX(0.0F);
/*  567 */       this.dfxPanel[2].setPreferredSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  568 */       this.dfxPanel[2].setMaximumSize(new Dimension(50 * (this.nVariable + 1) * this.nTerm, 40));
/*  569 */       this.dfxPanel[2].add(new JLabel("df(X)/dx3 =  "));
/*  570 */       for (i = 0; i < this.nTerm; i++) {
/*  571 */         if (i > 0) this.dfxPanel[2].add(new JLabel(" + "));
/*  572 */         this.dfxPanel[2].add(this.dfx3coef[i]);
/*  573 */         for (int j = 0; j < this.nVariable; j++) {
/*  574 */           this.dfxPanel[2].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j + 1)))));
/*  575 */           this.dfxPanel[2].add(this.dfx3_xexp[i][j]);
/*      */         }
/*      */       }
/*  578 */       this.gradPanel.add(this.dfxPanel[2]);
/*      */     }
/*      */     
/*  581 */     this.trialPanel.removeAll();
/*  582 */     this.trialPanel.setAlignmentX(0.0F);
/*  583 */     this.trialPanel.add(new JLabel("Initial Trial Solution :   "));
/*  584 */     switch (this.nVariable) {
/*      */     case 1: 
/*  586 */       this.xname = "( x1 ) =  ";
/*  587 */       break;
/*      */     case 2: 
/*  589 */       this.xname = "( x1 , x2 ) =  ";
/*  590 */       break;
/*      */     case 3: 
/*  592 */       this.xname = "( x1 , x2 , x3 ) =  ";
/*  593 */       break;
/*      */     default: 
/*  595 */       this.xname = " xname ";
/*  596 */       System.out.println("num variable error");
/*      */     }
/*      */     
/*  599 */     this.trialPanel.add(new JLabel(this.xname));
/*  600 */     this.trialPanel.add(new JLabel("( "));
/*  601 */     for (i = 0; i < this.nVariable; i++) {
/*  602 */       this.trialPanel.add(this.inixfd[i]);
/*  603 */       if (i < this.nVariable - 1)
/*  604 */         this.trialPanel.add(new JLabel(" , "));
/*      */     }
/*  606 */     this.trialPanel.add(new JLabel(" )"));
/*      */     
/*  608 */     this.gradPanel.add(Box.createVerticalStrut(20));
/*  609 */     this.gradPanel.add(this.trialPanel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void BuildLpfuncPanel()
/*      */   {
/*  616 */     JPanel[] dervfPanel = new JPanel[3];JPanel[] drexPanel = new JPanel[3];JPanel[] dfresPanel = new JPanel[3];
/*  617 */     JPanel[] grdPanel = new JPanel[3];
/*      */     
/*  619 */     JPanel ofPanel = new JPanel();
/*  620 */     this.lpfuncPanel.removeAll();
/*      */     
/*  622 */     String xs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.curstr))).append("      ").append(this.xname).append(" ").append(this.thexstr)));
/*  623 */     this.trialabel.setText(xs);
/*  624 */     this.lpfuncPanel.add(this.trialabel);
/*  625 */     this.lpfuncPanel.add(Box.createVerticalStrut(20));
/*      */     
/*  627 */     ofPanel.setAlignmentX(0.0F);
/*  628 */     ofPanel.setMaximumSize(new Dimension(this.nTerm * this.nVariable * 40 + 200, 30));
/*  629 */     JPanel tmexPanel = Function2Panel(this.nVariable, this.nTerm, this.controller.data.fw_objective.fxcoef, this.controller.data.fw_objective.xexp);
/*  630 */     ofPanel.removeAll();
/*  631 */     ofPanel.add(new JLabel("   ".concat(String.valueOf(String.valueOf(this.fname)))));
/*  632 */     ofPanel.add(tmexPanel);
/*  633 */     this.lpfuncPanel.add(new JLabel("Objective Function : "));
/*  634 */     this.lpfuncPanel.add(ofPanel);
/*  635 */     this.lpfuncPanel.add(Box.createVerticalStrut(20));
/*  636 */     this.lpfuncPanel.add(new JLabel("Derivatives of the Objective Function :"));
/*      */     
/*  638 */     for (int i = 0; i < 3; i++) {
/*  639 */       grdPanel[i] = new JPanel();
/*  640 */       grdPanel[i].setLayout(new GridLayout(3, 1));
/*      */     }
/*  642 */     for (i = 0; i < this.nVariable; i++) {
/*  643 */       dervfPanel[i] = new JPanel();
/*  644 */       dervfPanel[i].setPreferredSize(new Dimension(70, 30));
/*  645 */       dervfPanel[i].setMaximumSize(new Dimension(70, 30));
/*  646 */       dervfPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer(" df(X)/dx").append(i + 1).append(" = ")))));
/*      */       
/*  648 */       drexPanel[i] = Function2Panel(this.nVariable, this.nTerm, this.controller.data.fw_derivative[i].fxcoef, this.controller.data.fw_derivative[i].xexp);
/*      */       
/*  650 */       String gds = " = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_gradx[(this.itercount - 1)][i]))));
/*  651 */       this.gradlabel[i].setText(gds);
/*  652 */       dfresPanel[i] = new JPanel();
/*  653 */       dfresPanel[i].setPreferredSize(new Dimension(70, 30));
/*  654 */       dfresPanel[i].setMaximumSize(new Dimension(70, 30));
/*  655 */       dfresPanel[i].add(this.gradlabel[i]);
/*      */       
/*  657 */       grdPanel[0].add(dervfPanel[i]);
/*  658 */       grdPanel[1].add(drexPanel[i]);
/*  659 */       grdPanel[2].add(dfresPanel[i]);
/*      */     }
/*      */     
/*  662 */     this.dervPanel.setAlignmentX(0.0F);
/*  663 */     this.dervPanel.setLayout(new BoxLayout(this.dervPanel, 0));
/*  664 */     this.dervPanel.removeAll();
/*  665 */     for (i = 0; i < 3; i++) {
/*  666 */       this.dervPanel.add(grdPanel[i]);
/*      */     }
/*      */     
/*  669 */     this.lpfuncPanel.add(this.dervPanel);
/*  670 */     this.lpfuncPanel.add(Box.createVerticalStrut(20));
/*  671 */     this.lpfuncPanel.add(new JLabel("Linear Objective Function : "));
/*      */     
/*  673 */     JPanel lpenterPanel = new JPanel();
/*  674 */     lpenterPanel.setAlignmentX(0.0F);
/*  675 */     if (this.controller.data.is_max) {
/*  676 */       this.zname = "        Max  Z =  ";
/*      */     } else {
/*  678 */       this.zname = "        Min  Z =  ";
/*      */     }
/*  680 */     lpenterPanel.add(new JLabel(this.zname));
/*  681 */     for (i = 0; i < this.nVariable; i++) {
/*  682 */       if (i > 0)
/*  683 */         lpenterPanel.add(new JLabel(" + "));
/*  684 */       lpenterPanel.add(this.xlpfd[i]);
/*  685 */       lpenterPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(i + 1)))));
/*      */     }
/*      */     
/*  688 */     this.lpfuncPanel.add(lpenterPanel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private JPanel Function2Panel(int numvar, int numtm, double[] coef, int[][] expnt)
/*      */   {
/*  697 */     JPanel rtPanel = new JPanel();
/*      */     
/*      */ 
/*  700 */     rtPanel.setAlignmentX(0.0F);
/*  701 */     rtPanel.setLayout(new FlowLayout(0));
/*      */     
/*  703 */     for (int i = 0; i < numtm; i++) {
/*  704 */       if ((i > 0) && (coef[i] > 0.01D)) {
/*  705 */         rtPanel.add(new JLabel(" + "));
/*      */       }
/*      */       else
/*  708 */         rtPanel.add(new JLabel("  "));
/*  709 */       if (Math.abs(coef[i]) > 0.01D) {
/*  710 */         rtPanel.add(new JLabel(this.decfm.format(coef[i])));
/*  711 */         for (int j = 0; j < numvar; j++) {
/*  712 */           if (expnt[i][j] > 1) {
/*  713 */             JLabel xlabel = new JLabel("x");
/*  714 */             xlabel.setFont(new Font("Default", 1, 14));
/*  715 */             rtPanel.add(new ScriptWriter(xlabel, "".concat(String.valueOf(String.valueOf(expnt[i][j]))), String.valueOf(String.valueOf(new StringBuffer("").append(j + 1)))));
/*      */           }
/*  717 */           else if (expnt[i][j] == 1) {
/*  718 */             JLabel xlabel = new JLabel("x");
/*  719 */             xlabel.setFont(new Font("Default", 1, 14));
/*  720 */             rtPanel.add(new ScriptWriter(xlabel, " ", String.valueOf(String.valueOf(new StringBuffer("").append(j + 1)))));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  725 */     return rtPanel;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private JPanel Tfunc2Panel()
/*      */   {
/*  733 */     JPanel htexPanel = new JPanel();
/*      */     
/*  735 */     htexPanel.setLayout(new FlowLayout(0));
/*  736 */     htexPanel.setAlignmentX(0.0F);
/*  737 */     int ntf = this.controller.data.fw_ht_num_term[(this.itercount - 1)];
/*      */     
/*      */ 
/*  740 */     htexPanel.add(new JLabel(" h(t) = "));
/*  741 */     for (int i = 0; i < this.controller.data.fw_ht_num_term[(this.itercount - 1)]; i++)
/*      */     {
/*  743 */       if ((i > 0) && (this.controller.data.fw_ht_coef[(this.itercount - 1)][i] > 0.01D)) {
/*  744 */         htexPanel.add(new JLabel(" + "));
/*      */       } else
/*  746 */         htexPanel.add(new JLabel("  "));
/*  747 */       if (Math.abs(this.controller.data.fw_ht_coef[(this.itercount - 1)][i]) > 0.01D) {
/*  748 */         htexPanel.add(new JLabel(this.decfm.format(this.controller.data.fw_ht_coef[(this.itercount - 1)][i])));
/*  749 */         if (this.controller.data.fw_ht_texp[(this.itercount - 1)][i] > 1) {
/*  750 */           JLabel tlabel = new JLabel("t");
/*  751 */           tlabel.setFont(new Font("Default", 1, 14));
/*  752 */           htexPanel.add(new ScriptWriter(tlabel, "".concat(String.valueOf(String.valueOf(this.controller.data.fw_ht_texp[(this.itercount - 1)][i]))), ""));
/*      */         }
/*  754 */         else if (this.controller.data.fw_ht_texp[(this.itercount - 1)][i] == 1) {
/*  755 */           JLabel tlabel = new JLabel("t");
/*  756 */           tlabel.setFont(new Font("Default", 1, 14));
/*  757 */           htexPanel.add(new ScriptWriter(tlabel, "", ""));
/*      */         }
/*      */       }
/*      */     }
/*  761 */     return htexPanel;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void BuildUpPanel()
/*      */   {
/*  768 */     String[] cts = new String[3];
/*      */     
/*  770 */     this.upPanel.removeAll();
/*  771 */     this.upPanel.add(new JLabel("The current linear programming approximation is :"));
/*  772 */     this.upPanel.add(Box.createVerticalStrut(10));
/*      */     
/*  774 */     String bds = " and  ";
/*  775 */     String zs = " ";
/*  776 */     for (int i = 0; i < this.nVariable; i++) {
/*  777 */       if ((i > 0) && (this.controller.data.fw_flp_coef[(this.itercount - 1)][i] > 0.01D))
/*  778 */         zs = String.valueOf(String.valueOf(zs)).concat(" + ");
/*  779 */       if (Math.abs(this.controller.data.fw_flp_coef[(this.itercount - 1)][i]) > 0.01D) {
/*  780 */         zs = String.valueOf(String.valueOf(zs)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_flp_coef[(this.itercount - 1)][i]))));
/*  781 */         zs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(zs))).append("  x").append(i + 1)));
/*      */       }
/*  783 */       bds = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(bds))).append("    x").append(i + 1).append(" ").append(">=").append(" 0 .")));
/*      */     }
/*  785 */     this.zlabel.setText(String.valueOf(String.valueOf(this.zname)).concat(String.valueOf(String.valueOf(zs))));
/*  786 */     this.upPanel.add(this.zlabel);
/*  787 */     this.upPanel.add(Box.createVerticalStrut(20));
/*  788 */     this.upPanel.add(new JLabel("Subject to : "));
/*  789 */     this.upPanel.add(Box.createVerticalStrut(10));
/*      */     
/*  791 */     for (i = 0; i < this.nConstraint; i++) {
/*  792 */       cts[i] = "";
/*  793 */       for (int j = 1; j <= this.nVariable; j++) {
/*  794 */         if ((j > 1) && (this.controller.data.fw_constraint_coef[i][j] > 0.01D))
/*  795 */           cts[i] = String.valueOf(String.valueOf(cts[i])).concat(" + ");
/*  796 */         if (Math.abs(this.controller.data.fw_constraint_coef[i][j]) > 0.01D) {
/*  797 */           cts[i] = String.valueOf(String.valueOf(cts[i])).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_constraint_coef[i][j]))));
/*  798 */           cts[i] = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(cts[i]))).append(" x").append(j)));
/*      */         }
/*      */       }
/*  801 */       if (this.controller.data.fw_constraint_operator[i] == 0) {
/*  802 */         cts[i] = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(cts[i]))).append(" ").append("<=").append(" ")));
/*  803 */       } else if (this.controller.data.fw_constraint_operator[i] == 1) {
/*  804 */         cts[i] = String.valueOf(String.valueOf(cts[i])).concat(" = ");
/*      */       } else
/*  806 */         cts[i] = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(cts[i]))).append(" ").append(">=").append(" ")));
/*  807 */       cts[i] = String.valueOf(String.valueOf(cts[i])).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_constraint_coef[i][0]))));
/*      */       
/*  809 */       this.upPanel.add(new JLabel("           ".concat(String.valueOf(String.valueOf(cts[i])))));
/*      */     }
/*      */     
/*  812 */     this.upPanel.add(Box.createVerticalStrut(10));
/*  813 */     this.upPanel.add(new JLabel(bds));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildXtPanel()
/*      */   {
/*  822 */     String trs = "Trial Solution :  ";
/*  823 */     String lps = "Solution to LP :  ";
/*  824 */     trs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(trs))).append(this.xname).append(this.thexstr)));
/*  825 */     lps = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(lps))).append(this.xname).append(this.thelpstr)));
/*      */     
/*  827 */     this.xtPanel.removeAll();
/*  828 */     this.trsulabel.setText(trs);
/*  829 */     this.lpsulabel.setText(lps);
/*  830 */     this.xtPanel.add(this.trsulabel);
/*  831 */     this.xtPanel.add(Box.createVerticalStrut(10));
/*  832 */     this.xtPanel.add(this.lpsulabel);
/*  833 */     this.xtPanel.add(Box.createVerticalStrut(10));
/*  834 */     this.xtPanel.add(new JLabel("Thus ,"));
/*      */     
/*  836 */     JPanel transPanel = new JPanel();
/*  837 */     transPanel.setAlignmentX(0.0F);
/*  838 */     JPanel taPanel = new JPanel();
/*  839 */     JPanel tbPanel = new JPanel();
/*  840 */     JPanel tcPanel = new JPanel();
/*      */     
/*  842 */     transPanel.add(new JLabel(this.xname));
/*  843 */     taPanel.add(new JLabel("( "));
/*  844 */     tbPanel.add(new JLabel("( "));
/*  845 */     tcPanel.add(new JLabel("( "));
/*      */     
/*  847 */     for (int i = 0; i < this.nVariable; i++) {
/*  848 */       taPanel.add(this.xtafd[i]);
/*  849 */       tbPanel.add(this.xtbfd[i]);
/*  850 */       tcPanel.add(this.xtcfd[i]);
/*  851 */       if (i < this.nVariable - 1) {
/*  852 */         taPanel.add(new JLabel(" , "));
/*  853 */         tbPanel.add(new JLabel(" , "));
/*  854 */         tcPanel.add(new JLabel(" , "));
/*      */       }
/*      */     }
/*  857 */     taPanel.add(new JLabel(" )"));
/*  858 */     tbPanel.add(new JLabel(" )"));
/*  859 */     tcPanel.add(new JLabel(" )"));
/*      */     
/*  861 */     transPanel.add(taPanel);
/*  862 */     transPanel.add(new JLabel("  +  t [ "));
/*  863 */     transPanel.add(tbPanel);
/*  864 */     transPanel.add(new JLabel(" - "));
/*  865 */     transPanel.add(tcPanel);
/*  866 */     transPanel.add(new JLabel(" ]"));
/*      */     
/*  868 */     this.xtPanel.add(transPanel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void BuildAbPanel()
/*      */   {
/*  875 */     JPanel htPanel = new JPanel();
/*      */     
/*  877 */     this.abinPanel.setAlignmentX(0.0F);
/*  878 */     this.abinPanel.removeAll();
/*      */     
/*  880 */     htPanel.setAlignmentX(0.0F);
/*  881 */     htPanel.setLayout(new FlowLayout(0));
/*      */     
/*      */ 
/*  884 */     htPanel.add(new JLabel(" h(t) =  f(X) = "));
/*  885 */     JPanel tmexPanel = Function2Panel(this.nVariable, this.nTerm, this.controller.data.fw_objective.fxcoef, this.controller.data.fw_objective.xexp);
/*  886 */     htPanel.add(tmexPanel);
/*      */     
/*  888 */     this.abinPanel.add(htPanel);
/*  889 */     this.abinPanel.add(Box.createVerticalStrut(10));
/*  890 */     this.abinPanel.add(new JLabel("where"));
/*      */     
/*  892 */     for (int i = 0; i < this.nVariable; i++) {
/*  893 */       this.abtPanel[i] = new JPanel();
/*  894 */       this.aPanel[i] = new JPanel();
/*  895 */       this.bPanel[i] = new JPanel();
/*  896 */       this.abtPanel[i].add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(i + 1).append(" = a + bt ")))));
/*  897 */       this.aPanel[i].add(new JLabel("a = "));
/*  898 */       this.aPanel[i].add(this.afd[i]);
/*  899 */       this.bPanel[i].add(new JLabel("b = "));
/*  900 */       this.bPanel[i].add(this.bfd[i]);
/*      */     }
/*      */     
/*  903 */     this.inputPanel = new JPanel();
/*  904 */     this.inputPanel.setLayout(new GridLayout(3, this.nVariable));
/*  905 */     this.inputPanel.setAlignmentX(0.0F);
/*      */     
/*      */ 
/*      */ 
/*  909 */     for (i = 0; i < this.nVariable; i++) {
/*  910 */       this.inputPanel.add(this.abtPanel[i]);
/*      */     }
/*  912 */     for (i = 0; i < this.nVariable; i++) {
/*  913 */       this.inputPanel.add(this.aPanel[i]);
/*      */     }
/*  915 */     for (i = 0; i < this.nVariable; i++) {
/*  916 */       this.inputPanel.add(this.bPanel[i]);
/*      */     }
/*  918 */     this.abinPanel.add(this.inputPanel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildNewxPanel()
/*      */   {
/*  927 */     String trs = "( ";
/*  928 */     String lps = "( ";
/*  929 */     this.theastr = "( ";
/*  930 */     this.thebstr = "( ";
/*  931 */     for (int i = 0; i < this.nVariable; i++) {
/*  932 */       trs = String.valueOf(String.valueOf(trs)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_oldx[(this.itercount - 1)][i]))));
/*  933 */       lps = String.valueOf(String.valueOf(lps)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_xlp[(this.itercount - 1)][i]))));
/*  934 */       this.theastr = String.valueOf(String.valueOf(this.theastr)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_a[(this.itercount - 1)][i]))));
/*  935 */       this.thebstr = String.valueOf(String.valueOf(this.thebstr)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_b[(this.itercount - 1)][i]))));
/*  936 */       if (i < this.nVariable - 1) {
/*  937 */         trs = String.valueOf(String.valueOf(trs)).concat(" , ");
/*  938 */         lps = String.valueOf(String.valueOf(lps)).concat(" , ");
/*  939 */         this.theastr = String.valueOf(String.valueOf(this.theastr)).concat(" , ");
/*  940 */         this.thebstr = String.valueOf(String.valueOf(this.thebstr)).concat(" , ");
/*      */       }
/*      */     }
/*  943 */     trs = String.valueOf(String.valueOf(trs)).concat(" )");
/*  944 */     lps = String.valueOf(String.valueOf(lps)).concat(" )");
/*  945 */     this.theastr = String.valueOf(String.valueOf(this.theastr)).concat(" )");
/*  946 */     this.thebstr = String.valueOf(String.valueOf(this.thebstr)).concat(" )");
/*      */     
/*  948 */     String ins = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.xname))).append(this.thexstr).append(" +  t [").append(this.thelpstr).append(" - ").append(this.thexstr).append("]")));
/*  949 */     String rts = String.valueOf(String.valueOf(new StringBuffer("           = ").append(this.theastr).append(" +  t ").append(this.thebstr)));
/*      */     
/*  951 */     this.newxPanel.removeAll();
/*      */     
/*  953 */     this.newxPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Trial Solution :  ").append(this.xname).append(this.thexstr)))));
/*  954 */     this.newxPanel.add(Box.createVerticalStrut(10));
/*  955 */     this.newxPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Solution to LP :  ").append(this.xname).append(this.thelpstr)))));
/*  956 */     this.newxPanel.add(Box.createVerticalStrut(10));
/*  957 */     this.newxPanel.add(new JLabel("Thus ,"));
/*  958 */     this.newxPanel.add(new JLabel(ins));
/*  959 */     this.newxPanel.add(new JLabel(rts));
/*  960 */     this.newxPanel.add(Box.createVerticalStrut(20));
/*  961 */     this.newxPanel.add(this.abinPanel);
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
/*      */ 
/*      */   protected void backStep() {}
/*      */   
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
/*  991 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/*  992 */     this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str1).append(str))));
/*  993 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/* 1000 */     String str = "Interactive Frank-Wolfe Algorithm\n\n";
/* 1001 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to quickly enter a linearly constrained convex programming ");
/* 1002 */     str = String.valueOf(String.valueOf(str)).concat("problem (see Secs. 13.3 and 13.9) and then interactively execute the Frank-Wolfe ");
/* 1003 */     str = String.valueOf(String.valueOf(str)).concat("Algorithm presented in Sec. 13.9. Your role is to apply the logic of the algorithm, ");
/* 1004 */     str = String.valueOf(String.valueOf(str)).concat("and the computer will do the number crunching. The computer also will inform you if you ");
/* 1005 */     str = String.valueOf(String.valueOf(str)).concat("make a mistake on the first iteration.\n\n");
/* 1006 */     str = String.valueOf(String.valueOf(str)).concat("This procedure can handle up to 3 variables (all with nonnegativity constraints) and 6 ");
/* 1007 */     str = String.valueOf(String.valueOf(str)).concat("functional constraints, which encompasses all relevant problems at the end of Chap. 13.\n\n");
/* 1008 */     str = String.valueOf(String.valueOf(str)).concat("When you finish a problem, you can print out all your work by choosing Print to file under the ");
/* 1009 */     str = String.valueOf(String.valueOf(str)).concat("File menu. If you are interrupted before you finish, you can save your work (choose ");
/* 1010 */     str = String.valueOf(String.valueOf(str)).concat("Save under the File menu) and return later (choose Open).\n\n");
/* 1011 */     str = String.valueOf(String.valueOf(str)).concat("At any step, detailed computer instructions are available (Help menu). To ");
/* 1012 */     str = String.valueOf(String.valueOf(str)).concat("undo a mistake, backtrack by pressing the BACK button.");
/* 1013 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/* 1015 */     this.help_content_procedure.setText(str);
/* 1016 */     this.help_content_procedure.revalidate();
/*      */   }
/*      */   
/* 1019 */   private String str0 = "Interactive Frank-Wolfe Algorithm\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1031 */   private String str1 = "Entering the number of variables\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1044 */   private String str2 = "Entering the derivative of the objective function\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1054 */   private String str3 = "Entering the linear objective function\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1059 */   private String str4 = "To continue\n\n";
/*      */   
/*      */ 
/* 1062 */   private String str5 = "To continue\n\n";
/*      */   
/*      */ 
/* 1065 */   private String str6 = "Entering the new trial solution\n\n";
/*      */   
/*      */ 
/*      */ 
/* 1069 */   private String str7 = "Entering the a's and the b's\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1077 */   private String str8 = "To continue\n\n";
/*      */   
/*      */ 
/*      */ 
/* 1081 */   private String str9 = "To continue\n\n";
/*      */   
/*      */ 
/* 1084 */   private String str10 = "To continue\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void updateStepHelpPanel()
/*      */   {
/* 1091 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/* 1092 */     switch (this.step) {
/*      */     case 1: 
/* 1094 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str1).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1127 */       break;
/*      */     case 2: 
/* 1097 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str2).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1127 */       break;
/*      */     case 3: 
/* 1100 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str3).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1127 */       break;
/*      */     case 4: 
/* 1103 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str4).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1127 */       break;
/*      */     case 5: 
/* 1106 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str5).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1127 */       break;
/*      */     case 6: 
/* 1109 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str6).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1127 */       break;
/*      */     case 7: 
/* 1112 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str7).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1127 */       break;
/*      */     case 8: 
/* 1115 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str8).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1127 */       break;
/*      */     case 9: 
/* 1118 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str9).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1127 */       break;
/*      */     case 10: 
/* 1121 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str10).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1127 */       break;
/*      */     default: 
/* 1124 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str0).append(str))));
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doBack()
/*      */   {
/* 1135 */     if (this.opseq.isEmpty() == false) {
/* 1136 */       int last = ((Integer)this.opseq.lastElement()).intValue();
/* 1137 */       this.opseq.removeElementAt(this.opseq.size() - 1);
/*      */       
/* 1139 */       switch (last)
/*      */       {
/*      */       case 1: 
/* 1142 */         this.controller.solver.reset();
/* 1143 */         this.state.back();
/* 1144 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/* 1147 */         this.mainPanel.removeAll();
/* 1148 */         this.mainPanel.add(this.setupPanel);
/* 1149 */         this.mainPanel.add(this.buttPanel);
/* 1150 */         this.backbutt.setEnabled(false);
/* 1151 */         this.step = 1;
/*      */         
/* 1153 */         revalidate();
/* 1154 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1351 */         break;
/*      */       case 2: 
/* 1158 */         this.controller.solver.reset();
/* 1159 */         this.state.back();
/* 1160 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/* 1163 */         this.mainPanel.removeAll();
/* 1164 */         this.mainPanel.add(this.gradPanel);
/* 1165 */         this.mainPanel.add(this.buttPanel);
/* 1166 */         this.step = 2;
/*      */         
/* 1168 */         revalidate();
/* 1169 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1351 */         break;
/*      */       case 3: 
/* 1173 */         this.controller.solver.reset();
/* 1174 */         this.state.back();
/* 1175 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*      */ 
/* 1179 */         this.trialabel.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.curstr))).append("      ").append(this.xname).append(" ").append(this.thexstr))));
/* 1180 */         for (int i = 0; i < this.nVariable; i++) {
/* 1181 */           this.gradlabel[i].setText(" = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_gradx[(this.itercount - 1)][i])))));
/*      */         }
/* 1183 */         this.mainPanel.removeAll();
/* 1184 */         this.mainPanel.add(this.lpfuncPanel);
/* 1185 */         this.mainPanel.add(this.buttPanel);
/* 1186 */         this.step = 3;
/*      */         
/* 1188 */         revalidate();
/* 1189 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1351 */         break;
/*      */       case 4: 
/* 1193 */         this.controller.solver.reset();
/* 1194 */         this.state.back();
/* 1195 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/* 1198 */         this.inditext.setText("Press the NEXT button to apply the Simplex Method to this model \nin order to determine its optimal solution");
/*      */         
/* 1200 */         this.bottomPanel.removeAll();
/* 1201 */         this.bottomPanel.add(this.inditext);
/* 1202 */         this.step = 4;
/*      */         
/* 1204 */         revalidate();
/* 1205 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1351 */         break;
/*      */       case 5: 
/* 1210 */         String zs = " ";
/* 1211 */         for (int i = 0; i < this.nVariable; i++) {
/* 1212 */           if ((i > 0) && (this.controller.data.fw_flp_coef[(this.itercount - 1)][i] > 0.01D))
/* 1213 */             zs = String.valueOf(String.valueOf(zs)).concat(" + ");
/* 1214 */           if (Math.abs(this.controller.data.fw_flp_coef[(this.itercount - 1)][i]) > 0.01D) {
/* 1215 */             zs = String.valueOf(String.valueOf(zs)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_flp_coef[(this.itercount - 1)][i]))));
/* 1216 */             zs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(zs))).append("  x").append(i + 1)));
/*      */           }
/*      */         }
/* 1219 */         this.zlabel.setText(String.valueOf(String.valueOf(this.zname)).concat(String.valueOf(String.valueOf(zs))));
/*      */         
/* 1221 */         this.mainPanel.removeAll();
/* 1222 */         this.mainPanel.add(this.upPanel);
/* 1223 */         this.mainPanel.add(Box.createVerticalStrut(30));
/* 1224 */         this.mainPanel.add(this.bottomPanel);
/* 1225 */         this.mainPanel.add(Box.createVerticalGlue());
/* 1226 */         this.mainPanel.add(this.buttPanel);
/*      */         
/* 1228 */         this.bottomPanel.removeAll();
/* 1229 */         this.bottomPanel.add(new JLabel("Optimal  Solution :     ".concat(String.valueOf(String.valueOf(this.thelpstr)))));
/* 1230 */         this.bottomPanel.add(Box.createVerticalStrut(20));
/* 1231 */         this.bottomPanel.add(new JLabel("Press the NEXT button to continue."));
/* 1232 */         this.step = 5;
/*      */         
/* 1234 */         revalidate();
/* 1235 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1351 */         break;
/*      */       case 6: 
/* 1239 */         this.controller.solver.reset();
/* 1240 */         this.state.back();
/* 1241 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/* 1244 */         this.mainPanel.removeAll();
/* 1245 */         this.mainPanel.add(this.xtPanel);
/* 1246 */         this.mainPanel.add(this.buttPanel);
/* 1247 */         this.step = 6;
/*      */         
/* 1249 */         revalidate();
/* 1250 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1351 */         break;
/*      */       case 7: 
/* 1254 */         this.controller.solver.reset();
/* 1255 */         this.state.back();
/* 1256 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/* 1259 */         this.mainPanel.remove(this.bottomPanel);
/* 1260 */         this.step = 7;
/*      */         
/* 1262 */         revalidate();
/* 1263 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1351 */         break;
/*      */       case 8: 
/* 1267 */         this.controller.solver.reset();
/* 1268 */         this.state.back();
/* 1269 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/* 1272 */         JPanel htfPanel = Tfunc2Panel();
/* 1273 */         this.bottomPanel.removeAll();
/* 1274 */         this.bottomPanel.add(htfPanel);
/* 1275 */         this.bottomPanel.add(Box.createVerticalStrut(20));
/* 1276 */         this.indilabel.setText("Press the NEXT button to apply the one-dimensional search procedure to this function.");
/* 1277 */         this.bottomPanel.add(this.indilabel);
/* 1278 */         this.step = 8;
/*      */         
/* 1280 */         revalidate();
/* 1281 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1351 */         break;
/*      */       case 9: 
/* 1286 */         this.thexstr = "( ";
/* 1287 */         this.thelpstr = "( ";
/* 1288 */         for (int i = 0; i < this.nVariable; i++) {
/* 1289 */           this.thexstr = String.valueOf(String.valueOf(this.thexstr)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_oldx[(this.itercount - 1)][i]))));
/* 1290 */           this.thelpstr = String.valueOf(String.valueOf(this.thelpstr)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_xlp[(this.itercount - 1)][i]))));
/* 1291 */           if (i < this.nVariable - 1) {
/* 1292 */             this.thexstr = String.valueOf(String.valueOf(this.thexstr)).concat(" , ");
/* 1293 */             this.thelpstr = String.valueOf(String.valueOf(this.thelpstr)).concat(" , ");
/*      */           }
/*      */         }
/* 1296 */         this.thexstr = String.valueOf(String.valueOf(this.thexstr)).concat(" )");
/* 1297 */         this.thelpstr = String.valueOf(String.valueOf(this.thelpstr)).concat(" )");
/*      */         
/*      */ 
/* 1300 */         BuildNewxPanel();
/* 1301 */         this.mainPanel.removeAll();
/* 1302 */         this.mainPanel.add(this.newxPanel);
/* 1303 */         this.mainPanel.add(Box.createVerticalStrut(20));
/* 1304 */         this.mainPanel.add(this.bottomPanel);
/* 1305 */         this.mainPanel.add(Box.createVerticalGlue());
/* 1306 */         this.mainPanel.add(this.buttPanel);
/*      */         
/* 1308 */         JPanel htfPanel = Tfunc2Panel();
/* 1309 */         this.bottomPanel.removeAll();
/* 1310 */         this.bottomPanel.add(htfPanel);
/* 1311 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/* 1312 */         String tvs = "h(t) is maximized at :     t* = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_tt[(this.itercount - 1)]))));
/* 1313 */         this.bottomPanel.add(new JLabel(tvs));
/* 1314 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/* 1315 */         String nxs = "So the new trial solution is :   X = ";
/* 1316 */         nxs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(nxs))).append(this.theastr).append(" + t* ").append(this.thebstr)));
/* 1317 */         this.newxstr = "( ";
/* 1318 */         for (i = 0; i < this.nVariable; i++) {
/* 1319 */           this.newxstr = String.valueOf(String.valueOf(this.newxstr)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_newx[(this.itercount - 1)][i]))));
/* 1320 */           if (i < this.nVariable - 1)
/* 1321 */             this.newxstr = String.valueOf(String.valueOf(this.newxstr)).concat(" , ");
/*      */         }
/* 1323 */         this.newxstr = String.valueOf(String.valueOf(this.newxstr)).concat(" )");
/*      */         
/* 1325 */         nxs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(nxs))).append(" = ").append(this.newxstr)));
/* 1326 */         this.bottomPanel.add(new JLabel(nxs));
/* 1327 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/* 1328 */         this.indilabel.setText("Press the NEXT button to continue.");
/* 1329 */         this.bottomPanel.add(this.indilabel);
/* 1330 */         this.step = 9;
/*      */         
/* 1332 */         revalidate();
/* 1333 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1351 */         break;
/*      */       case 10: 
/* 1337 */         this.itercount -= 1;
/* 1338 */         this.fwModel.fireTableRowsDeleted(this.itercount, this.itercount);
/* 1339 */         this.mainPanel.removeAll();
/* 1340 */         this.mainPanel.add(this.scrlPane);
/* 1341 */         this.mainPanel.add(this.buttPanel);
/* 1342 */         this.step = 10;
/*      */         
/* 1344 */         revalidate();
/* 1345 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1351 */         break;
/*      */       default: 
/* 1348 */         System.out.println("frank-wolf cannot back here");break;
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void doNext()
/*      */   {
/* 1357 */     int[] num = new int[3];int[] opert = new int[this.nConstraint];
/* 1358 */     double[] objcoef = new double[this.nTerm];double[] grad1coef = new double[this.nTerm];
/* 1359 */     double[] grad2coef = new double[this.nTerm];double[] grad3coef = new double[this.nTerm];
/* 1360 */     int[][] objexp = new int[this.nTerm][3];int[][] grad1exp = new int[this.nTerm][3];
/* 1361 */     int[][] grad2exp = new int[this.nTerm][3];int[][] grad3exp = new int[this.nTerm][3];
/* 1362 */     double[] inisolu = new double[3];double[] lpobjcoef = new double[3];
/* 1363 */     double[] aent = new double[3];double[] bent = new double[3];double[] cent = new double[3];
/* 1364 */     double[][] contcoef = new double[this.nConstraint][this.nVariable + 1];
/*      */     
/*      */ 
/*      */ 
/* 1368 */     Vector par = new Vector();
/* 1369 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/* 1371 */     switch (this.step)
/*      */     {
/*      */     case 1: 
/* 1374 */       num[0] = this.nVariable;
/* 1375 */       num[1] = this.nConstraint;
/* 1376 */       num[2] = this.nTerm;
/* 1377 */       par.addElement(num);
/* 1378 */       if (this.maxmin.getSelectedIndex() == 0) {
/* 1379 */         par.addElement(new Boolean(true));
/*      */       } else {
/* 1381 */         par.addElement(new Boolean(false));
/*      */       }
/* 1383 */       for (int i = 0; i < this.nTerm; i++) {
/* 1384 */         objcoef[i] = this.fxcoef[i].getValue();
/* 1385 */         for (int j = 0; j < this.nVariable; j++) {
/* 1386 */           objexp[i][j] = this.fxexp[i][j].getValue();
/*      */         }
/*      */       }
/* 1389 */       par.addElement(objcoef);
/* 1390 */       par.addElement(objexp);
/* 1391 */       for (i = 0; i < this.nConstraint; i++) {
/* 1392 */         opert[i] = this.linear_equations[i].getOperator();
/* 1393 */         this.linear_equations[i].getCoefficient(contcoef[i]);
/*      */       }
/* 1395 */       par.addElement(contcoef);
/* 1396 */       par.addElement(opert);
/*      */       
/* 1398 */       opr.parameters = par;
/* 1399 */       opr.operation_code = 50401;
/* 1400 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1401 */         String err = this.controller.solver.getErrInfo();
/* 1402 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1407 */         this.state.addStep(opr);
/*      */         
/* 1409 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1412 */         BuildGradPanel();
/* 1413 */         this.mainPanel.removeAll();
/* 1414 */         this.mainPanel.add(this.gradPanel);
/* 1415 */         this.mainPanel.add(this.buttPanel);
/* 1416 */         this.backbutt.setEnabled(true);
/* 1417 */         this.step = 2;
/*      */         
/* 1419 */         revalidate();
/* 1420 */         repaint();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1758 */       break;
/*      */     case 2: 
/* 1424 */       for (int i = 0; i < this.nTerm; i++) {
/* 1425 */         grad1coef[i] = this.dfx1coef[i].getValue();
/* 1426 */         if (this.nVariable >= 2) {
/* 1427 */           grad2coef[i] = this.dfx2coef[i].getValue();
/*      */         }
/* 1429 */         if (this.nVariable == 3) {
/* 1430 */           grad3coef[i] = this.dfx3coef[i].getValue();
/*      */         }
/* 1432 */         for (int j = 0; j < this.nVariable; j++) {
/* 1433 */           grad1exp[i][j] = this.dfx1_xexp[i][j].getValue();
/* 1434 */           if (this.nVariable >= 2) {
/* 1435 */             grad2exp[i][j] = this.dfx2_xexp[i][j].getValue();
/*      */           }
/* 1437 */           if (this.nVariable == 3) {
/* 1438 */             grad3exp[i][j] = this.dfx3_xexp[i][j].getValue();
/*      */           }
/*      */         }
/*      */       }
/* 1442 */       par.addElement(grad1coef);
/* 1443 */       par.addElement(grad1exp);
/* 1444 */       inisolu[0] = this.inixfd[0].getValue();
/* 1445 */       if (this.nVariable >= 2) {
/* 1446 */         par.addElement(grad2coef);
/* 1447 */         par.addElement(grad2exp);
/* 1448 */         inisolu[1] = this.inixfd[1].getValue();
/*      */       }
/* 1450 */       if (this.nVariable == 3) {
/* 1451 */         par.addElement(grad3coef);
/* 1452 */         par.addElement(grad3exp);
/* 1453 */         inisolu[2] = this.inixfd[2].getValue();
/*      */       }
/* 1455 */       par.addElement(inisolu);
/*      */       
/* 1457 */       opr.parameters = par;
/* 1458 */       opr.operation_code = 50402;
/* 1459 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1460 */         String err = this.controller.solver.getErrInfo();
/* 1461 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1466 */         this.state.addStep(opr);
/*      */         
/* 1468 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1471 */         this.thexstr = "( ";
/* 1472 */         for (i = 0; i < this.nVariable; i++) {
/* 1473 */           this.thexstr = String.valueOf(String.valueOf(this.thexstr)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_oldx[(this.itercount - 1)][i]))));
/* 1474 */           if (i < this.nVariable - 1) {
/* 1475 */             this.thexstr = String.valueOf(String.valueOf(this.thexstr)).concat(" , ");
/*      */           }
/*      */         }
/* 1478 */         this.thexstr = String.valueOf(String.valueOf(this.thexstr)).concat(" )");
/*      */         
/*      */ 
/* 1481 */         BuildLpfuncPanel();
/* 1482 */         BuildUpPanel();
/* 1483 */         BuildAbPanel();
/*      */         
/* 1485 */         this.mainPanel.removeAll();
/* 1486 */         this.mainPanel.add(this.lpfuncPanel);
/* 1487 */         this.mainPanel.add(this.buttPanel);
/* 1488 */         this.step = 3;
/*      */         
/* 1490 */         revalidate();
/* 1491 */         repaint();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1758 */       break;
/*      */     case 3: 
/* 1495 */       par.addElement(new Integer(this.itercount));
/* 1496 */       for (int i = 0; i < this.nVariable; i++) {
/* 1497 */         lpobjcoef[i] = this.xlpfd[i].getValue();
/*      */       }
/* 1499 */       par.addElement(lpobjcoef);
/*      */       
/* 1501 */       opr.parameters = par;
/* 1502 */       opr.operation_code = 50403;
/* 1503 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1504 */         String err = this.controller.solver.getErrInfo();
/* 1505 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1510 */         this.state.addStep(opr);
/*      */         
/* 1512 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1515 */         String zs = " ";
/* 1516 */         for (i = 0; i < this.nVariable; i++) {
/* 1517 */           if ((i > 0) && (this.controller.data.fw_flp_coef[(this.itercount - 1)][i] > 0.01D))
/* 1518 */             zs = String.valueOf(String.valueOf(zs)).concat(" + ");
/* 1519 */           if (Math.abs(this.controller.data.fw_flp_coef[(this.itercount - 1)][i]) > 0.01D) {
/* 1520 */             zs = String.valueOf(String.valueOf(zs)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_flp_coef[(this.itercount - 1)][i]))));
/* 1521 */             zs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(zs))).append("  x").append(i + 1)));
/*      */           }
/*      */         }
/* 1524 */         this.zlabel.setText(String.valueOf(String.valueOf(this.zname)).concat(String.valueOf(String.valueOf(zs))));
/* 1525 */         this.mainPanel.removeAll();
/* 1526 */         this.mainPanel.add(this.upPanel);
/* 1527 */         this.mainPanel.add(Box.createVerticalStrut(30));
/* 1528 */         this.mainPanel.add(this.bottomPanel);
/* 1529 */         this.mainPanel.add(Box.createVerticalGlue());
/* 1530 */         this.mainPanel.add(this.buttPanel);
/* 1531 */         this.inditext.setBackground(getBackground());
/* 1532 */         this.inditext.setText("Press the NEXT button to apply the Simplex Method to this model\nin order to determine its optimal solution. ");
/*      */         
/* 1534 */         this.bottomPanel.removeAll();
/* 1535 */         this.bottomPanel.add(this.inditext);
/* 1536 */         this.step = 4;
/*      */         
/* 1538 */         revalidate();
/* 1539 */         repaint();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1758 */       break;
/*      */     case 4: 
/* 1543 */       par.addElement(new Integer(this.itercount));
/*      */       
/* 1545 */       opr.parameters = par;
/* 1546 */       opr.operation_code = 50404;
/* 1547 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1548 */         String err = this.controller.solver.getErrInfo();
/* 1549 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1554 */         this.state.addStep(opr);
/*      */         
/* 1556 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1559 */         this.bottomPanel.removeAll();
/* 1560 */         String sols = "Optimal Solution :   ";
/* 1561 */         this.thelpstr = "( ";
/* 1562 */         for (int i = 0; i < this.nVariable; i++) {
/* 1563 */           this.thelpstr = String.valueOf(String.valueOf(this.thelpstr)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_xlp[(this.itercount - 1)][i]))));
/* 1564 */           if (i < this.nVariable - 1) {
/* 1565 */             this.thelpstr = String.valueOf(String.valueOf(this.thelpstr)).concat(" , ");
/*      */           }
/*      */         }
/* 1568 */         this.thelpstr = String.valueOf(String.valueOf(this.thelpstr)).concat(" )");
/* 1569 */         this.bottomPanel.add(new JLabel(String.valueOf(String.valueOf(sols)).concat(String.valueOf(String.valueOf(this.thelpstr)))));
/* 1570 */         this.bottomPanel.add(Box.createVerticalStrut(20));
/* 1571 */         this.bottomPanel.add(new JLabel("Press the NEXT button to continue."));
/* 1572 */         this.step = 5;
/*      */         
/* 1574 */         revalidate();
/* 1575 */         repaint();
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
/* 1758 */       break;
/*      */     case 5: 
/* 1579 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*      */ 
/* 1582 */       if (this.itercount == 1) {
/* 1583 */         BuildXtPanel();
/* 1584 */         this.mainPanel.removeAll();
/* 1585 */         this.mainPanel.add(this.xtPanel);
/* 1586 */         this.mainPanel.add(this.buttPanel);
/* 1587 */         this.step = 6;
/*      */       }
/*      */       else {
/* 1590 */         BuildNewxPanel();
/* 1591 */         this.mainPanel.removeAll();
/* 1592 */         this.mainPanel.add(this.newxPanel);
/* 1593 */         this.mainPanel.add(Box.createVerticalGlue());
/* 1594 */         this.mainPanel.add(this.buttPanel);
/* 1595 */         this.step = 7;
/*      */       }
/*      */       
/* 1598 */       revalidate();
/* 1599 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1758 */       break;
/*      */     case 6: 
/* 1603 */       for (int i = 0; i < this.nVariable; i++) {
/* 1604 */         aent[i] = this.xtafd[i].getValue();
/* 1605 */         bent[i] = this.xtbfd[i].getValue();
/* 1606 */         cent[i] = this.xtcfd[i].getValue();
/*      */       }
/* 1608 */       par.addElement(aent);
/* 1609 */       par.addElement(bent);
/* 1610 */       par.addElement(cent);
/*      */       
/* 1612 */       opr.parameters = par;
/* 1613 */       opr.operation_code = 50405;
/* 1614 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1615 */         String err = this.controller.solver.getErrInfo();
/* 1616 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1621 */         this.state.addStep(opr);
/*      */         
/* 1623 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1626 */         BuildNewxPanel();
/* 1627 */         this.mainPanel.removeAll();
/* 1628 */         this.mainPanel.add(this.newxPanel);
/* 1629 */         this.mainPanel.add(Box.createVerticalGlue());
/* 1630 */         this.mainPanel.add(this.buttPanel);
/* 1631 */         this.step = 7;
/*      */         
/* 1633 */         revalidate();
/* 1634 */         repaint();
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
/* 1758 */       break;
/*      */     case 7: 
/* 1638 */       for (int i = 0; i < this.nVariable; i++) {
/* 1639 */         aent[i] = this.afd[i].getValue();
/* 1640 */         bent[i] = this.bfd[i].getValue();
/*      */       }
/* 1642 */       par.addElement(new Integer(this.itercount));
/* 1643 */       par.addElement(aent);
/* 1644 */       par.addElement(bent);
/*      */       
/* 1646 */       opr.parameters = par;
/* 1647 */       opr.operation_code = 50406;
/* 1648 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1649 */         String err = this.controller.solver.getErrInfo();
/* 1650 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1655 */         this.state.addStep(opr);
/*      */         
/* 1657 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1660 */         JPanel htfPanel = Tfunc2Panel();
/* 1661 */         this.bottomPanel.removeAll();
/* 1662 */         this.bottomPanel.add(htfPanel);
/* 1663 */         this.bottomPanel.add(Box.createVerticalStrut(20));
/* 1664 */         this.indilabel.setText("Press the NEXT button to apply the one-dimensional search procedure to this function.");
/* 1665 */         this.bottomPanel.add(this.indilabel);
/* 1666 */         this.mainPanel.remove(this.buttPanel);
/* 1667 */         this.mainPanel.add(this.bottomPanel);
/* 1668 */         this.mainPanel.add(Box.createVerticalGlue());
/* 1669 */         this.mainPanel.add(this.buttPanel);
/* 1670 */         this.step = 8;
/*      */         
/* 1672 */         revalidate();
/* 1673 */         repaint();
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
/* 1758 */       break;
/*      */     case 8: 
/* 1677 */       par.addElement(new Integer(this.itercount));
/*      */       
/* 1679 */       opr.parameters = par;
/* 1680 */       opr.operation_code = 50407;
/* 1681 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1682 */         String err = this.controller.solver.getErrInfo();
/* 1683 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1688 */         this.state.addStep(opr);
/*      */         
/* 1690 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1693 */         String hts = "h(t) is maximized at :     t* = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_tt[(this.itercount - 1)]))));
/* 1694 */         JPanel htfPanel = Tfunc2Panel();
/* 1695 */         this.bottomPanel.removeAll();
/* 1696 */         this.bottomPanel.add(htfPanel);
/* 1697 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/* 1698 */         this.bottomPanel.add(new JLabel(hts));
/* 1699 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/* 1700 */         String nxs = "So the new trial solution is :   X = ";
/* 1701 */         nxs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(nxs))).append(this.theastr).append(" + t* ").append(this.thebstr)));
/* 1702 */         this.newxstr = "( ";
/* 1703 */         for (int i = 0; i < this.nVariable; i++) {
/* 1704 */           this.newxstr = String.valueOf(String.valueOf(this.newxstr)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_newx[(this.itercount - 1)][i]))));
/* 1705 */           if (i < this.nVariable - 1)
/* 1706 */             this.newxstr = String.valueOf(String.valueOf(this.newxstr)).concat(" , ");
/*      */         }
/* 1708 */         this.newxstr = String.valueOf(String.valueOf(this.newxstr)).concat(" )");
/* 1709 */         nxs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(nxs))).append(" = ").append(this.newxstr)));
/* 1710 */         this.bottomPanel.add(new JLabel(nxs));
/* 1711 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/* 1712 */         this.indilabel.setText("Press the NEXT button to continue.");
/* 1713 */         this.bottomPanel.add(this.indilabel);
/* 1714 */         this.step = 9;
/*      */         
/* 1716 */         revalidate();
/* 1717 */         repaint();
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
/* 1758 */       break;
/*      */     case 9: 
/* 1721 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*      */ 
/*      */ 
/* 1725 */       this.thexstr = this.newxstr;
/* 1726 */       this.fwModel.fireTableDataChanged();
/* 1727 */       this.mainPanel.removeAll();
/* 1728 */       this.mainPanel.add(this.scrlPane);
/* 1729 */       this.mainPanel.add(this.buttPanel);
/* 1730 */       this.step = 10;
/*      */       
/* 1732 */       revalidate();
/* 1733 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1758 */       break;
/*      */     case 10: 
/* 1737 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*      */ 
/* 1740 */       this.itercount += 1;
/* 1741 */       this.fwModel.fireTableRowsInserted(this.itercount - 1, this.itercount - 1);
/*      */       
/* 1743 */       this.trialabel.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.curstr))).append("      ").append(this.xname).append(" ").append(this.thexstr))));
/* 1744 */       for (int i = 0; i < this.nVariable; i++) {
/* 1745 */         this.gradlabel[i].setText(" = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_gradx[(this.itercount - 1)][i])))));
/*      */       }
/* 1747 */       this.mainPanel.removeAll();
/* 1748 */       this.mainPanel.add(this.lpfuncPanel);
/* 1749 */       this.mainPanel.add(this.buttPanel);
/* 1750 */       this.step = 3;
/*      */       
/* 1752 */       revalidate();
/* 1753 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1758 */       break;
/*      */     default: 
/* 1756 */       System.out.println("frank wolf cannot next here");
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
/* 1770 */     int[] para = new int[5];
/* 1771 */     double[][] xtpara = new double[3][3];
/* 1772 */     String[] glstr = new String[8];
/*      */     try
/*      */     {
/* 1775 */       this.state = ((IORState)in.readObject());
/* 1776 */       para = (int[])in.readObject();
/* 1777 */       xtpara = (double[][])in.readObject();
/* 1778 */       glstr = (String[])in.readObject();
/* 1779 */       this.opseq = ((Vector)in.readObject());
/* 1780 */       this.state.setSolverStepVector();
/* 1781 */       in.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1784 */       e1.printStackTrace();
/* 1785 */       System.out.println("Open fails");
/*      */     }
/* 1787 */     this.step = para[0];
/* 1788 */     this.itercount = para[1];
/* 1789 */     this.nTerm = para[2];
/* 1790 */     this.nVariable = para[3];
/* 1791 */     this.nConstraint = para[4];
/*      */     
/* 1793 */     this.xname = glstr[0];
/* 1794 */     this.zname = glstr[1];
/* 1795 */     this.fname = glstr[2];
/* 1796 */     this.theastr = glstr[3];
/* 1797 */     this.thebstr = glstr[4];
/* 1798 */     this.thexstr = glstr[5];
/* 1799 */     this.thelpstr = glstr[6];
/* 1800 */     this.newxstr = glstr[7];
/*      */     
/* 1802 */     if (this.step >= 1) {
/* 1803 */       this.NumTerm.setValue(this.nTerm);
/* 1804 */       this.NumVariable.setValue(this.nVariable);
/* 1805 */       this.NumConstraint.setValue(this.nConstraint);
/* 1806 */       BuildSetupPanel();
/*      */       
/* 1808 */       for (int i = 0; i < this.nTerm; i++) {
/* 1809 */         this.fxcoef[i].setValue(this.controller.data.fw_objective.fxcoef[i]);
/* 1810 */         for (int j = 0; j < this.nVariable; j++) {
/* 1811 */           this.fxexp[i][j].setValue(this.controller.data.fw_objective.xexp[i][j]);
/*      */         }
/*      */       }
/* 1814 */       for (i = 0; i < this.nConstraint; i++) {
/* 1815 */         this.linear_equations[i].setCoefficient(this.controller.data.fw_constraint_coef[i]);
/* 1816 */         this.linear_equations[i].setOperator(this.controller.data.fw_constraint_operator[i]);
/*      */       }
/*      */     }
/*      */     
/* 1820 */     if (this.step >= 2) {
/* 1821 */       BuildGradPanel();
/* 1822 */       this.mainPanel.removeAll();
/* 1823 */       this.mainPanel.add(this.gradPanel);
/* 1824 */       this.mainPanel.add(this.buttPanel);
/* 1825 */       this.backbutt.setEnabled(true);
/*      */       
/* 1827 */       for (int i = 0; i < this.nTerm; i++) {
/* 1828 */         this.dfx1coef[i].setValue(this.controller.data.fw_derivative[0].fxcoef[i]);
/* 1829 */         if (this.nVariable >= 2) {
/* 1830 */           this.dfx2coef[i].setValue(this.controller.data.fw_derivative[1].fxcoef[i]);
/*      */         }
/* 1832 */         if (this.nVariable == 3) {
/* 1833 */           this.dfx3coef[i].setValue(this.controller.data.fw_derivative[2].fxcoef[i]);
/*      */         }
/* 1835 */         for (int j = 0; j < this.nVariable; j++) {
/* 1836 */           this.dfx1_xexp[i][j].setValue(this.controller.data.fw_derivative[0].xexp[i][j]);
/* 1837 */           if (this.nVariable >= 2) {
/* 1838 */             this.dfx2_xexp[i][j].setValue(this.controller.data.fw_derivative[1].xexp[i][j]);
/*      */           }
/* 1840 */           if (this.nVariable == 3) {
/* 1841 */             this.dfx3_xexp[i][j].setValue(this.controller.data.fw_derivative[2].xexp[i][j]);
/*      */           }
/*      */         }
/*      */       }
/* 1845 */       this.inixfd[0].setValue(this.controller.data.fw_oldx[0][0]);
/* 1846 */       if (this.nVariable >= 2) {
/* 1847 */         this.inixfd[1].setValue(this.controller.data.fw_oldx[0][1]);
/*      */       }
/* 1849 */       if (this.nVariable == 3) {
/* 1850 */         this.inixfd[2].setValue(this.controller.data.fw_oldx[0][2]);
/*      */       }
/*      */     }
/*      */     
/* 1854 */     if (this.step >= 3) {
/* 1855 */       this.thexstr = "( ";
/* 1856 */       for (int i = 0; i < this.nVariable; i++) {
/* 1857 */         this.thexstr = String.valueOf(String.valueOf(this.thexstr)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_oldx[(this.itercount - 1)][i]))));
/* 1858 */         if (i < this.nVariable - 1) {
/* 1859 */           this.thexstr = String.valueOf(String.valueOf(this.thexstr)).concat(" , ");
/*      */         }
/*      */       }
/* 1862 */       this.thexstr = String.valueOf(String.valueOf(this.thexstr)).concat(" )");
/*      */       
/*      */ 
/* 1865 */       BuildLpfuncPanel();
/* 1866 */       BuildUpPanel();
/* 1867 */       BuildAbPanel();
/*      */       
/* 1869 */       this.mainPanel.removeAll();
/* 1870 */       this.mainPanel.add(this.lpfuncPanel);
/* 1871 */       this.mainPanel.add(this.buttPanel);
/*      */       
/* 1873 */       for (i = 0; i < this.nVariable; i++) {
/* 1874 */         this.xlpfd[i].setValue(this.controller.data.fw_flp_coef[(this.itercount - 1)][i]);
/* 1875 */         this.xtafd[i].setValue(xtpara[0][i]);
/* 1876 */         this.xtbfd[i].setValue(xtpara[1][i]);
/* 1877 */         this.xtcfd[i].setValue(xtpara[2][i]);
/*      */       }
/*      */     }
/* 1880 */     if (this.step >= 4) {
/* 1881 */       String zs = " ";
/* 1882 */       for (int i = 0; i < this.nVariable; i++) {
/* 1883 */         if ((i > 0) && (this.controller.data.fw_flp_coef[(this.itercount - 1)][i] > 0.01D))
/* 1884 */           zs = String.valueOf(String.valueOf(zs)).concat(" + ");
/* 1885 */         if (Math.abs(this.controller.data.fw_flp_coef[(this.itercount - 1)][i]) > 0.01D) {
/* 1886 */           zs = String.valueOf(String.valueOf(zs)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_flp_coef[(this.itercount - 1)][i]))));
/* 1887 */           zs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(zs))).append("  x").append(i + 1)));
/*      */         }
/*      */       }
/* 1890 */       this.zlabel.setText(String.valueOf(String.valueOf(this.zname)).concat(String.valueOf(String.valueOf(zs))));
/* 1891 */       this.mainPanel.removeAll();
/* 1892 */       this.mainPanel.add(this.upPanel);
/* 1893 */       this.mainPanel.add(Box.createVerticalStrut(30));
/* 1894 */       this.mainPanel.add(this.bottomPanel);
/* 1895 */       this.mainPanel.add(Box.createVerticalGlue());
/* 1896 */       this.mainPanel.add(this.buttPanel);
/* 1897 */       this.inditext.setBackground(getBackground());
/* 1898 */       this.inditext.setText("Press the NEXT button to apply the Simplex Method to this model \nin order to determine its optimal solution. ");
/*      */       
/* 1900 */       this.bottomPanel.removeAll();
/* 1901 */       this.bottomPanel.add(this.inditext);
/*      */     }
/* 1903 */     if (this.step >= 5) {
/* 1904 */       this.bottomPanel.removeAll();
/* 1905 */       String sols = "Optimal Solution :   ";
/* 1906 */       this.thelpstr = "( ";
/* 1907 */       for (int i = 0; i < this.nVariable; i++) {
/* 1908 */         this.thelpstr = String.valueOf(String.valueOf(this.thelpstr)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_xlp[(this.itercount - 1)][i]))));
/* 1909 */         if (i < this.nVariable - 1) {
/* 1910 */           this.thelpstr = String.valueOf(String.valueOf(this.thelpstr)).concat(" , ");
/*      */         }
/*      */       }
/* 1913 */       this.thelpstr = String.valueOf(String.valueOf(this.thelpstr)).concat(" )");
/* 1914 */       this.bottomPanel.add(new JLabel(String.valueOf(String.valueOf(sols)).concat(String.valueOf(String.valueOf(this.thelpstr)))));
/* 1915 */       this.bottomPanel.add(Box.createVerticalStrut(20));
/* 1916 */       this.bottomPanel.add(new JLabel("Press the NEXT button to continue."));
/*      */     }
/* 1918 */     if (this.step >= 6) {
/* 1919 */       BuildXtPanel();
/* 1920 */       this.mainPanel.removeAll();
/* 1921 */       this.mainPanel.add(this.xtPanel);
/* 1922 */       this.mainPanel.add(this.buttPanel);
/*      */     }
/* 1924 */     if (this.step >= 7) {
/* 1925 */       BuildNewxPanel();
/* 1926 */       this.mainPanel.removeAll();
/* 1927 */       this.mainPanel.add(this.newxPanel);
/* 1928 */       this.mainPanel.add(Box.createVerticalGlue());
/* 1929 */       this.mainPanel.add(this.buttPanel);
/*      */     }
/*      */     
/* 1932 */     if (this.step >= 8) {
/* 1933 */       JPanel htfPanel = Tfunc2Panel();
/* 1934 */       this.bottomPanel.removeAll();
/* 1935 */       this.bottomPanel.add(htfPanel);
/* 1936 */       this.bottomPanel.add(Box.createVerticalStrut(20));
/* 1937 */       this.indilabel.setText("Press the NEXT button to apply the one-dimensional search procedure to this function.");
/* 1938 */       this.bottomPanel.add(this.indilabel);
/* 1939 */       this.mainPanel.removeAll();
/* 1940 */       this.mainPanel.add(this.newxPanel);
/* 1941 */       this.mainPanel.add(Box.createVerticalStrut(10));
/* 1942 */       this.mainPanel.add(this.bottomPanel);
/* 1943 */       this.mainPanel.add(Box.createVerticalGlue());
/* 1944 */       this.mainPanel.add(this.buttPanel);
/* 1945 */       for (int i = 0; i < this.nVariable; i++) {
/* 1946 */         this.afd[i].setValue(this.controller.data.fw_a[(this.itercount - 1)][i]);
/* 1947 */         this.bfd[i].setValue(this.controller.data.fw_b[(this.itercount - 1)][i]);
/*      */       }
/*      */     }
/* 1950 */     if (this.step >= 9) {
/* 1951 */       String hts = "h(t) is maximized at :     t* = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_tt[(this.itercount - 1)]))));
/* 1952 */       JPanel htfPanel = Tfunc2Panel();
/* 1953 */       this.bottomPanel.removeAll();
/* 1954 */       this.bottomPanel.add(htfPanel);
/* 1955 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/* 1956 */       this.bottomPanel.add(new JLabel(hts));
/* 1957 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/* 1958 */       String nxs = "So the new trial solution is :   X = ";
/* 1959 */       nxs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(nxs))).append(this.theastr).append(" + t* ").append(this.thebstr)));
/* 1960 */       this.newxstr = "( ";
/* 1961 */       for (int i = 0; i < this.nVariable; i++) {
/* 1962 */         this.newxstr = String.valueOf(String.valueOf(this.newxstr)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.fw_newx[(this.itercount - 1)][i]))));
/* 1963 */         if (i < this.nVariable - 1)
/* 1964 */           this.newxstr = String.valueOf(String.valueOf(this.newxstr)).concat(" , ");
/*      */       }
/* 1966 */       this.newxstr = String.valueOf(String.valueOf(this.newxstr)).concat(" )");
/* 1967 */       nxs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(nxs))).append(" = ").append(this.newxstr)));
/* 1968 */       this.bottomPanel.add(new JLabel(nxs));
/* 1969 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/* 1970 */       this.indilabel.setText("Press the NEXT button to continue.");
/* 1971 */       this.bottomPanel.add(this.indilabel);
/*      */     }
/* 1973 */     if (this.step >= 10) {
/* 1974 */       this.thexstr = this.newxstr;
/* 1975 */       this.fwModel.fireTableDataChanged();
/* 1976 */       this.mainPanel.removeAll();
/* 1977 */       this.mainPanel.add(this.scrlPane);
/* 1978 */       this.mainPanel.add(this.buttPanel);
/*      */     }
/* 1980 */     this.table.clearSelection();
/* 1981 */     revalidate();
/* 1982 */     repaint();
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
/* 1993 */     int[] interpara = new int[5];
/* 1994 */     double[][] xtpara = new double[3][3];
/* 1995 */     String[] interstr = new String[8];
/*      */     
/* 1997 */     interpara[0] = this.step;
/* 1998 */     interpara[1] = this.itercount;
/* 1999 */     interpara[2] = this.nTerm;
/* 2000 */     interpara[3] = this.nVariable;
/* 2001 */     interpara[4] = this.nConstraint;
/*      */     
/* 2003 */     interstr[0] = this.xname;
/* 2004 */     interstr[1] = this.zname;
/* 2005 */     interstr[2] = this.fname;
/* 2006 */     interstr[3] = this.theastr;
/* 2007 */     interstr[4] = this.thebstr;
/* 2008 */     interstr[5] = this.thexstr;
/* 2009 */     interstr[6] = this.thelpstr;
/* 2010 */     interstr[7] = this.newxstr;
/*      */     
/* 2012 */     for (int i = 0; i < 3; i++) {
/* 2013 */       xtpara[0][i] = this.xtafd[i].getValue();
/* 2014 */       xtpara[1][i] = this.xtbfd[i].getValue();
/* 2015 */       xtpara[2][i] = this.xtcfd[i].getValue();
/*      */     }
/*      */     try
/*      */     {
/* 2019 */       out.writeObject(this.state);
/* 2020 */       out.writeObject(interpara);
/* 2021 */       out.writeObject(xtpara);
/* 2022 */       out.writeObject(interstr);
/* 2023 */       out.writeObject(this.opseq);
/* 2024 */       out.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 2027 */       e1.printStackTrace();
/* 2028 */       System.out.println("Save fails");
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
/* 2039 */     int[] num = new int[3];int[] opert = new int[this.nConstraint];
/* 2040 */     double[] objcoef = new double[this.nTerm];double[] grad1coef = new double[this.nTerm];
/* 2041 */     double[] grad2coef = new double[this.nTerm];double[] grad3coef = new double[this.nTerm];
/* 2042 */     int[][] objexp = new int[this.nTerm][3];int[][] grad1exp = new int[this.nTerm][3];
/* 2043 */     int[][] grad2exp = new int[this.nTerm][3];int[][] grad3exp = new int[this.nTerm][3];
/* 2044 */     double[] inisolu = new double[3];
/* 2045 */     double[][] contcoef = new double[this.nConstraint][this.nVariable + 1];
/*      */     
/* 2047 */     Vector par = new Vector();
/* 2048 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/* 2050 */     if (this.step == 1) {
/* 2051 */       num[0] = this.nVariable;
/* 2052 */       num[1] = this.nConstraint;
/* 2053 */       num[2] = this.nTerm;
/* 2054 */       par.addElement(num);
/* 2055 */       if (this.maxmin.getSelectedIndex() == 0) {
/* 2056 */         par.addElement(new Boolean(true));
/*      */       } else {
/* 2058 */         par.addElement(new Boolean(false));
/*      */       }
/* 2060 */       for (int i = 0; i < this.nTerm; i++) {
/* 2061 */         objcoef[i] = this.fxcoef[i].getValue();
/* 2062 */         for (int j = 0; j < this.nVariable; j++) {
/* 2063 */           objexp[i][j] = this.fxexp[i][j].getValue();
/*      */         }
/*      */       }
/* 2066 */       par.addElement(objcoef);
/* 2067 */       par.addElement(objexp);
/* 2068 */       for (i = 0; i < this.nConstraint; i++) {
/* 2069 */         opert[i] = this.linear_equations[i].getOperator();
/* 2070 */         this.linear_equations[i].getCoefficient(contcoef[i]);
/*      */       }
/* 2072 */       par.addElement(contcoef);
/* 2073 */       par.addElement(opert);
/*      */       
/* 2075 */       opr.parameters = par;
/* 2076 */       opr.operation_code = 50401;
/* 2077 */       this.controller.solver.doWork(opr, this.controller.data);
/*      */     }
/* 2079 */     if (this.step == 2) {
/* 2080 */       for (int i = 0; i < this.nTerm; i++) {
/* 2081 */         grad1coef[i] = this.dfx1coef[i].getValue();
/* 2082 */         if (this.nVariable >= 2) {
/* 2083 */           grad2coef[i] = this.dfx2coef[i].getValue();
/*      */         }
/* 2085 */         if (this.nVariable == 3) {
/* 2086 */           grad3coef[i] = this.dfx3coef[i].getValue();
/*      */         }
/* 2088 */         for (int j = 0; j < this.nVariable; j++) {
/* 2089 */           grad1exp[i][j] = this.dfx1_xexp[i][j].getValue();
/* 2090 */           if (this.nVariable >= 2) {
/* 2091 */             grad2exp[i][j] = this.dfx2_xexp[i][j].getValue();
/*      */           }
/* 2093 */           if (this.nVariable == 3) {
/* 2094 */             grad3exp[i][j] = this.dfx3_xexp[i][j].getValue();
/*      */           }
/*      */         }
/*      */       }
/* 2098 */       par.addElement(grad1coef);
/* 2099 */       par.addElement(grad1exp);
/* 2100 */       inisolu[0] = this.inixfd[0].getValue();
/* 2101 */       if (this.nVariable >= 2) {
/* 2102 */         par.addElement(grad2coef);
/* 2103 */         par.addElement(grad2exp);
/* 2104 */         inisolu[1] = this.inixfd[1].getValue();
/*      */       }
/* 2106 */       if (this.nVariable == 3) {
/* 2107 */         par.addElement(grad3coef);
/* 2108 */         par.addElement(grad3exp);
/* 2109 */         inisolu[2] = this.inixfd[2].getValue();
/*      */       }
/* 2111 */       par.addElement(inisolu);
/*      */       
/* 2113 */       opr.parameters = par;
/* 2114 */       opr.operation_code = 50402;
/* 2115 */       this.controller.solver.doWork(opr, this.controller.data);
/*      */     } }
/*      */   
/*      */   class IORFWTableModel extends AbstractTableModel { private int i;
/*      */     private int j;
/*      */     
/*      */     IORFWTableModel() {}
/*      */     
/* 2123 */     public int getColumnCount() { return 6; }
/*      */     
/*      */     public int getRowCount()
/*      */     {
/* 2127 */       return IORNLFrankWolfPanel.this.itercount;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col) {
/* 2131 */       switch (col) {
/*      */       case 0: 
/* 2133 */         return "k";
/*      */       case 1: 
/* 2135 */         return "X[k-1]";
/*      */       case 2: 
/* 2137 */         return "Xlp[k]";
/*      */       case 3: 
/* 2139 */         return "X for h(t)";
/*      */       case 4: 
/* 2141 */         return "t*";
/*      */       case 5: 
/* 2143 */         return "X[k]";
/*      */       }
/* 2145 */       return "err";
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col)
/*      */     {
/* 2150 */       String str = new String();
/* 2151 */       String[] astr = new String[3];String[] bstr = new String[3];
/* 2152 */       DecimalFormat decfm = new DecimalFormat("#.###");
/* 2153 */       switch (col) {
/*      */       case 0: 
/* 2155 */         return new Integer(row + 1).toString();
/*      */       case 1: 
/* 2157 */         String xstr = "( ";
/* 2158 */         for (this.i = 0; this.i < IORNLFrankWolfPanel.this.nVariable; this.i += 1) {
/* 2159 */           xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(decfm.format(IORNLFrankWolfPanel.this.controller.data.fw_oldx[row][this.i]))));
/* 2160 */           if (this.i < IORNLFrankWolfPanel.this.nVariable - 1) {
/* 2161 */             xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*      */           }
/*      */         }
/* 2164 */         xstr = String.valueOf(String.valueOf(xstr)).concat(" )");
/* 2165 */         return xstr;
/*      */       case 2: 
/* 2167 */         String lpstr = "( ";
/* 2168 */         for (this.i = 0; this.i < IORNLFrankWolfPanel.this.nVariable; this.i += 1) {
/* 2169 */           lpstr = String.valueOf(String.valueOf(lpstr)).concat(String.valueOf(String.valueOf(decfm.format(IORNLFrankWolfPanel.this.controller.data.fw_xlp[row][this.i]))));
/* 2170 */           if (this.i < IORNLFrankWolfPanel.this.nVariable - 1) {
/* 2171 */             lpstr = String.valueOf(String.valueOf(lpstr)).concat(" , ");
/*      */           }
/*      */         }
/* 2174 */         lpstr = String.valueOf(String.valueOf(lpstr)).concat(" )");
/* 2175 */         return lpstr;
/*      */       case 3: 
/* 2177 */         str = "( ";
/* 2178 */         for (this.i = 0; this.i < IORNLFrankWolfPanel.this.nVariable; this.i += 1) {
/* 2179 */           astr[this.i] = decfm.format(IORNLFrankWolfPanel.this.controller.data.fw_a[row][this.i]);
/* 2180 */           if (IORNLFrankWolfPanel.this.controller.data.fw_b[row][this.i] > 0.01D) {
/* 2181 */             astr[this.i] = String.valueOf(String.valueOf(astr[this.i])).concat("+");
/*      */           }
/* 2183 */           if (Math.abs(IORNLFrankWolfPanel.this.controller.data.fw_b[row][this.i]) > 0.01D) {
/* 2184 */             astr[this.i] = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(astr[this.i]))).append(decfm.format(IORNLFrankWolfPanel.this.controller.data.fw_b[row][this.i])).append(" t")));
/*      */           }
/*      */           
/* 2187 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(astr[this.i])));
/* 2188 */           if (this.i < IORNLFrankWolfPanel.this.nVariable - 1)
/* 2189 */             str = String.valueOf(String.valueOf(str)).concat(" , ");
/*      */         }
/* 2191 */         str = String.valueOf(String.valueOf(str)).concat(" )");
/* 2192 */         return str;
/*      */       case 4: 
/* 2194 */         return decfm.format(IORNLFrankWolfPanel.this.controller.data.fw_tt[row]);
/*      */       case 5: 
/* 2196 */         String nstr = "( ";
/* 2197 */         for (this.i = 0; this.i < IORNLFrankWolfPanel.this.nVariable; this.i += 1) {
/* 2198 */           nstr = String.valueOf(String.valueOf(nstr)).concat(String.valueOf(String.valueOf(decfm.format(IORNLFrankWolfPanel.this.controller.data.fw_newx[row][this.i]))));
/* 2199 */           if (this.i < IORNLFrankWolfPanel.this.nVariable - 1) {
/* 2200 */             nstr = String.valueOf(String.valueOf(nstr)).concat(" , ");
/*      */           }
/*      */         }
/* 2203 */         nstr = String.valueOf(String.valueOf(nstr)).concat(" )");
/* 2204 */         return nstr;
/*      */       }
/* 2206 */       return "err";
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c)
/*      */     {
/* 2211 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 2217 */       return false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNLFrankWolfPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */