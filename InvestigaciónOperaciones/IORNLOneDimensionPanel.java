/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Vector;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORNLOneDimensionPanel
/*     */   extends IORActionPanel
/*     */ {
/*  31 */   private static final int MAXTERMS = 10;
/*  32 */   private final String LESSTHAN = "<=";
/*     */   
/*  34 */   public IORNLPProcessController controller = null;
/*     */   
/*  36 */   public JPanel mainPanel = new JPanel();
/*     */   
/*  38 */   private JPanel setupPanel = null;
/*  39 */   private JPanel upPanel = null;
/*  40 */   private JPanel bottomPanel = null;
/*  41 */   private JPanel tranPanel = new JPanel();
/*  42 */   private JPanel funcPanel = new JPanel();
/*  43 */   private JScrollPane scrlPane = null;
/*  44 */   private JPanel boundPanel = new JPanel();
/*  45 */   private JPanel buttPanel = new JPanel();
/*     */   
/*  47 */   private JPanel namePanel = new JPanel();
/*  48 */   private JPanel fieldPanel = new JPanel();
/*  49 */   private JPanel fxPanel = new JPanel();
/*  50 */   private JPanel dfPanel = new JPanel();
/*  51 */   private JPanel numPanel = new JPanel();
/*  52 */   private JPanel fexpPanel = new JPanel();
/*  53 */   private JPanel dexpPanel = new JPanel();
/*     */   
/*  55 */   private IORNLOneDimensionPanel.IOROneDTableModel itModel = null;
/*  56 */   private JTable table = null;
/*     */   
/*  58 */   private JButton backbutt = new JButton("Back");
/*  59 */   private JButton nextbutt = new JButton("Next");
/*     */   
/*  61 */   private JLabel itemlabel = new JLabel("Number of Terms in f(x) (<=10) : ");
/*  62 */   private JLabel lowlabel = new JLabel("lower bound :");
/*  63 */   private JLabel uplabel = new JLabel("upper bound :");
/*     */   
/*  65 */   private JLabel comtlabel = new JLabel("(To make a new number above take effect, you must press the ENTER key.)");
/*  66 */   private JLabel instructionlabel = new JLabel("For each term, enter the coefficient on the left and the exponent on the right.");
/*  67 */   private JLabel islabel = new JLabel("Is new x' the lower or upper bound");
/*     */   private DecimalField[] fxcoef;
/*     */   private DecimalField[] dfcoef;
/*     */   private DecimalField lowbound;
/*     */   private DecimalField upbound;
/*  72 */   private WholeNumberField[] fxpow; private WholeNumberField[] dfpow; private WholeNumberField numTerm = new WholeNumberField(2, 2);
/*  73 */   private JComboBox sel = new JComboBox();
/*  74 */   private JComboBox maxmin = new JComboBox();
/*     */   
/*  76 */   private int step = 1;
/*  77 */   private int itercount = 0;
/*  78 */   private int nTerm = 2;
/*  79 */   private DecimalFormat decfm = new DecimalFormat();
/*     */   
/*  81 */   private Vector opseq = new Vector();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORNLOneDimensionPanel(IORNLPProcessController c)
/*     */   {
/*  91 */     super(c);
/*  92 */     this.controller = c;
/*  93 */     this.state = new IORState(this.controller.solver);
/*  94 */     add(this.mainPanel);
/*  95 */     this.actionStatus.setVisible(false);
/*  96 */     this.bn_back.setVisible(false);
/*  97 */     this.bn_finish.setVisible(false);
/*     */     
/*  99 */     this.decfm.setGroupingUsed(false);
/* 100 */     this.comtlabel.setAlignmentX(0.5F);
/* 101 */     this.instructionlabel.setAlignmentX(0.5F);
/*     */     
/* 103 */     this.lowbound = new DecimalField(0.0D, this.decfm);
/* 104 */     this.upbound = new DecimalField(0.0D, this.decfm);
/* 105 */     this.boundPanel.add(this.lowlabel);
/* 106 */     this.boundPanel.add(this.lowbound);
/* 107 */     this.boundPanel.add(Box.createHorizontalStrut(20));
/* 108 */     this.boundPanel.add(this.uplabel);
/* 109 */     this.boundPanel.add(this.upbound);
/*     */     
/* 111 */     this.backbutt.setEnabled(false);
/* 112 */     this.buttPanel.add(this.backbutt);
/* 113 */     this.buttPanel.add(Box.createHorizontalStrut(10));
/* 114 */     this.buttPanel.add(this.nextbutt);
/* 115 */     this.buttPanel.setPreferredSize(new Dimension(300, 50));
/* 116 */     this.buttPanel.setMaximumSize(new Dimension(300, 50));
/*     */     
/* 118 */     this.maxmin.addItem("Max");
/* 119 */     this.maxmin.addItem("Min");
/*     */     
/* 121 */     this.numPanel.add(this.itemlabel);
/* 122 */     this.numPanel.add(this.numTerm);
/*     */     
/* 124 */     JPanel fnmPanel = new JPanel();
/* 125 */     JPanel dfnmPanel = new JPanel();
/* 126 */     fnmPanel.add(this.maxmin);
/* 127 */     fnmPanel.add(new JLabel("f(x) = "));
/* 128 */     dfnmPanel.add(new JLabel("df(x)/dx  = "));
/*     */     
/* 130 */     this.namePanel.setLayout(new BoxLayout(this.namePanel, 1));
/* 131 */     this.namePanel.add(fnmPanel);
/* 132 */     this.namePanel.add(Box.createVerticalStrut(10));
/* 133 */     this.namePanel.add(dfnmPanel);
/*     */     
/* 135 */     this.fieldPanel.setLayout(new BoxLayout(this.fieldPanel, 1));
/* 136 */     this.fieldPanel.add(this.fxPanel);
/* 137 */     this.fieldPanel.add(Box.createVerticalStrut(10));
/* 138 */     this.fieldPanel.add(this.dfPanel);
/*     */     
/* 140 */     this.funcPanel.add(this.namePanel);
/* 141 */     this.funcPanel.add(this.fieldPanel);
/*     */     
/* 143 */     this.setupPanel = new JPanel();
/* 144 */     this.setupPanel.setLayout(new BoxLayout(this.setupPanel, 1));
/* 145 */     this.setupPanel.add(this.numPanel);
/* 146 */     this.setupPanel.add(Box.createVerticalStrut(20));
/* 147 */     this.setupPanel.add(this.comtlabel);
/* 148 */     this.setupPanel.add(Box.createVerticalStrut(30));
/* 149 */     this.setupPanel.add(this.instructionlabel);
/* 150 */     this.setupPanel.add(Box.createVerticalStrut(30));
/* 151 */     this.setupPanel.add(this.funcPanel);
/* 152 */     this.setupPanel.add(Box.createVerticalStrut(20));
/* 153 */     this.setupPanel.add(this.boundPanel);
/*     */     
/* 155 */     BuildSetupPanel(2);
/*     */     
/* 157 */     this.tranPanel.setLayout(new BorderLayout());
/* 158 */     this.tranPanel.add(this.setupPanel, "North");
/*     */     
/* 160 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/* 161 */     this.mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
/* 162 */     this.mainPanel.add(this.tranPanel);
/* 163 */     this.mainPanel.add(Box.createVerticalStrut(10));
/* 164 */     this.mainPanel.add(this.buttPanel);
/*     */     
/* 166 */     this.itModel = new IORNLOneDimensionPanel.IOROneDTableModel();
/* 167 */     this.table = new JTable(this.itModel);
/* 168 */     this.table.setCellSelectionEnabled(true);
/* 169 */     this.table.setEnabled(false);
/* 170 */     this.scrlPane = new JScrollPane(this.table);
/* 171 */     this.scrlPane.setPreferredSize(new Dimension(400, 250));
/* 172 */     this.scrlPane.setMaximumSize(new Dimension(500, 300));
/*     */     
/* 174 */     this.upPanel = new JPanel();
/* 175 */     this.upPanel.setLayout(new BoxLayout(this.upPanel, 1));
/* 176 */     this.upPanel.setAlignmentX(0.5F);
/*     */     
/* 178 */     this.bottomPanel = new JPanel();
/* 179 */     this.sel.addItem("upper");
/* 180 */     this.sel.addItem("lower");
/* 181 */     this.bottomPanel.add(this.islabel);
/* 182 */     this.bottomPanel.add(this.sel);
/*     */     
/*     */ 
/* 185 */     this.nextbutt.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 187 */         IORNLOneDimensionPanel.this.doNext();
/*     */       }
/* 189 */     });
/* 190 */     this.backbutt.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 192 */         IORNLOneDimensionPanel.this.doBack();
/*     */       }
/*     */       
/* 195 */     });
/* 196 */     this.numTerm.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 198 */         int numterm = IORNLOneDimensionPanel.this.numTerm.getValue();
/* 199 */         if (numterm < 1) {
/* 200 */           numterm = 1;
/* 201 */         } else if (numterm > 10)
/* 202 */           numterm = 10;
/* 203 */         IORNLOneDimensionPanel.this.numTerm.setValue(numterm);
/* 204 */         IORNLOneDimensionPanel.this.BuildSetupPanel(numterm);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void BuildSetupPanel(int numterm)
/*     */   {
/* 214 */     this.nTerm = numterm;
/*     */     
/* 216 */     this.fxcoef = new DecimalField[numterm];
/* 217 */     this.dfcoef = new DecimalField[numterm];
/* 218 */     this.fxpow = new WholeNumberField[numterm];
/* 219 */     this.dfpow = new WholeNumberField[numterm];
/*     */     
/* 221 */     this.fxPanel.removeAll();
/* 222 */     this.dfPanel.removeAll();
/* 223 */     for (int i = 0; i < numterm; i++) {
/* 224 */       this.fxcoef[i] = new DecimalField(0.0D, 5, this.decfm);
/* 225 */       this.dfcoef[i] = new DecimalField(0.0D, 5, this.decfm);
/* 226 */       this.fxpow[i] = new WholeNumberField(0, 1);
/* 227 */       this.dfpow[i] = new WholeNumberField(0, 1);
/*     */     }
/*     */     
/*     */ 
/* 231 */     for (i = 0; i < numterm; i++) {
/* 232 */       if (i > 0) {
/* 233 */         this.fxPanel.add(new JLabel(" + "));
/* 234 */         this.dfPanel.add(new JLabel(" + "));
/*     */       }
/*     */       
/* 237 */       this.fxPanel.add(this.fxcoef[i]);
/* 238 */       this.fxPanel.add(new JLabel(" x"));
/* 239 */       this.fxPanel.add(this.fxpow[i]);
/*     */       
/* 241 */       this.dfPanel.add(this.dfcoef[i]);
/* 242 */       this.dfPanel.add(new JLabel(" x"));
/* 243 */       this.dfPanel.add(this.dfpow[i]);
/*     */     }
/*     */     
/* 246 */     revalidate();
/* 247 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   private void BuildUpPanel()
/*     */   {
/*     */     String str;
/*     */     
/*     */     String str;
/*     */     
/* 257 */     if (this.controller.data.is_max) {
/* 258 */       str = "Max  f(x) =   ";
/*     */     }
/*     */     else {
/* 261 */       str = "Min  f(x) =   ";
/*     */     }
/*     */     
/* 264 */     this.fexpPanel.removeAll();
/* 265 */     this.fexpPanel.add(new JLabel(str));
/* 266 */     JPanel tmpexPanel = Xfunc2Panel(this.nTerm, this.controller.data.fx_coef, this.controller.data.fx_power);
/* 267 */     this.fexpPanel.add(tmpexPanel);
/*     */     
/* 269 */     this.dexpPanel.removeAll();
/* 270 */     this.dexpPanel.add(new JLabel(" df(x)/dx =   "));
/* 271 */     tmpexPanel = Xfunc2Panel(this.nTerm, this.controller.data.df_coef, this.controller.data.df_power);
/* 272 */     this.dexpPanel.add(tmpexPanel);
/*     */     
/*     */ 
/* 275 */     String bds = String.valueOf(String.valueOf(new StringBuffer("lower bound : ").append(this.decfm.format(this.lowbound.getValue())).append("       upper bound : ").append(this.decfm.format(this.upbound.getValue()))));
/*     */     
/* 277 */     JLabel boundlabel = new JLabel(bds);
/* 278 */     boundlabel.setAlignmentX(0.5F);
/*     */     
/* 280 */     this.upPanel.removeAll();
/* 281 */     this.upPanel.add(this.fexpPanel);
/* 282 */     this.upPanel.add(this.dexpPanel);
/* 283 */     this.upPanel.add(boundlabel);
/* 284 */     this.upPanel.add(Box.createVerticalStrut(10));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private JPanel Xfunc2Panel(int numtm, double[] coef, int[] expnt)
/*     */   {
/* 292 */     JPanel exPanel = new JPanel();
/* 293 */     exPanel.setAlignmentX(0.0F);
/*     */     
/*     */ 
/* 296 */     for (int i = 0; i < numtm; i++)
/*     */     {
/* 298 */       if ((i > 0) && (coef[i] > 0.01D)) {
/* 299 */         exPanel.add(new JLabel(" + "));
/*     */       } else
/* 301 */         exPanel.add(new JLabel(" "));
/* 302 */       if (Math.abs(coef[i]) > 0.01D) {
/* 303 */         exPanel.add(new JLabel(this.decfm.format(coef[i])));
/* 304 */         if (expnt[i] > 1) {
/* 305 */           JLabel xlabel = new JLabel("x");
/* 306 */           xlabel.setFont(new Font("Default", 1, 14));
/* 307 */           exPanel.add(new ScriptWriter(xlabel, "".concat(String.valueOf(String.valueOf(expnt[i]))), ""));
/*     */         }
/* 309 */         else if (expnt[i] == 1) {
/* 310 */           JLabel xlabel = new JLabel("x");
/* 311 */           xlabel.setFont(new Font("Default", 1, 14));
/* 312 */           exPanel.add(new ScriptWriter(xlabel, "", ""));
/*     */         }
/*     */       }
/*     */     }
/* 316 */     return exPanel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finishProcedure() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updatePanel() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 342 */     String str = "Interactive One-Dimensional Search Procedure\n\n";
/* 343 */     str = String.valueOf(String.valueOf(str)).concat("This procedure (approximately) maximizes a concave polynomial function of one variable ");
/* 344 */     str = String.valueOf(String.valueOf(str)).concat("(or minimizes such a convex function) by using the one-dimensional search procedure ");
/* 345 */     str = String.valueOf(String.valueOf(str)).concat("presented in Sec. 13.4. You enter the function, its derivative, and the initial ");
/* 346 */     str = String.valueOf(String.valueOf(str)).concat("upper and lower bounds. At each iteration, the computer calculates the midpoint of the ");
/* 347 */     str = String.valueOf(String.valueOf(str)).concat("two current bounds as the new trial solution, and then calculates the derivative there. ");
/* 348 */     str = String.valueOf(String.valueOf(str)).concat("Using this information, you will decide whether the new trial solution should be the ");
/* 349 */     str = String.valueOf(String.valueOf(str)).concat("new upper bound or the new lower bound. This process is repeated until you conclude that you ");
/* 350 */     str = String.valueOf(String.valueOf(str)).concat("are sufficiently close to an optimal solution.\n\n");
/* 351 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of terms in f(x)\n\n");
/* 352 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of terms (a positive integer〈 10) in f(x), and then press the ENTER key to make the new number take effect.\n\n");
/* 353 */     str = String.valueOf(String.valueOf(str)).concat("Selecting the objective\n\n");
/* 354 */     str = String.valueOf(String.valueOf(str)).concat("Select either Max or Min, depending on whether the objective is to maximize or minimize ");
/* 355 */     str = String.valueOf(String.valueOf(str)).concat("the function.\n\n");
/* 356 */     str = String.valueOf(String.valueOf(str)).concat("Entering the objective function\n\n");
/* 357 */     str = String.valueOf(String.valueOf(str)).concat("The objective function must be a polynomial where the exponent for each term is a nonnegative ");
/* 358 */     str = String.valueOf(String.valueOf(str)).concat("integer less than ten. For each term, you need to enter the coefficient (an integer or a decimal ");
/* 359 */     str = String.valueOf(String.valueOf(str)).concat("number), and then the exponent (a nonnegative integer) for the variable.\n\n");
/* 360 */     str = String.valueOf(String.valueOf(str)).concat("Entering the derivative of the objective function\n\n");
/* 361 */     str = String.valueOf(String.valueOf(str)).concat("For each term of the derivative of the objective function, you need to enter the coefficient (");
/* 362 */     str = String.valueOf(String.valueOf(str)).concat("an integer or a decimal number), and then the exponent (a nonnegative integer) for ");
/* 363 */     str = String.valueOf(String.valueOf(str)).concat("the variable. If there are no more terms, then enter 0 for the coefficient.\n\n");
/* 364 */     str = String.valueOf(String.valueOf(str)).concat("Entering the initial lower/upper bound\n\n");
/* 365 */     str = String.valueOf(String.valueOf(str)).concat("Enter the initial lower/upper bound (an integer or a decimal number) for the optimal ");
/* 366 */     str = String.valueOf(String.valueOf(str)).concat("solution, and then press the NEXT button to continue.");
/*     */     
/* 368 */     this.help_content_step.setText(str);
/* 369 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 377 */     String str = "Interactive One-Dimensional Search Procedure\n\n";
/* 378 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to execute the One-Dimensional Search Procedure as presented ");
/* 379 */     str = String.valueOf(String.valueOf(str)).concat("in Sec. 13.4. Your role is to apply the logic of the algorithm, and the computer will ");
/* 380 */     str = String.valueOf(String.valueOf(str)).concat("do the number crunching. The computer also will inform you if you make a mistake on ");
/* 381 */     str = String.valueOf(String.valueOf(str)).concat("the first iteration.\n\n");
/* 382 */     str = String.valueOf(String.valueOf(str)).concat("This procedure can handle up to 10 terms in a polynomial objective function, and only ");
/* 383 */     str = String.valueOf(String.valueOf(str)).concat("nonnegative integer exponents less than 10 (i.e., a single digit), which encompasses ");
/* 384 */     str = String.valueOf(String.valueOf(str)).concat("all relevant problems at the end of the Chap. 13.\n\n");
/* 385 */     str = String.valueOf(String.valueOf(str)).concat("When you finish a problem, you can print out all your work by choosing Print to file under ");
/* 386 */     str = String.valueOf(String.valueOf(str)).concat("the File menu. If you are interrupted before you finish, you can save your work (choose ");
/* 387 */     str = String.valueOf(String.valueOf(str)).concat("Save under the File menu) and return later (choose Open).\n\n");
/* 388 */     str = String.valueOf(String.valueOf(str)).concat("At any step, detailed computer instructions are available (Help menu). To ");
/* 389 */     str = String.valueOf(String.valueOf(str)).concat("undo a mistake, backtrack by pressing the BACK button.");
/* 390 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 392 */     this.help_content_procedure.setText(str);
/* 393 */     this.help_content_procedure.revalidate(); }
/*     */   
/* 395 */   private String str0 = "Interactive One-Dimensional Search Procedure\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 404 */   private String str1 = "Entering the number of terms in f(x)\n\n";
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
/* 421 */   private String str2 = "Should the trial solution be the new lower bound or the new upper bound?\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void updateStepHelpPanel()
/*     */   {
/* 431 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/* 432 */     switch (this.step) {
/*     */     case 1: 
/* 434 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str1).append(str))));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 443 */       break;
/*     */     case 2: 
/* 437 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str2).append(str))));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 443 */       break;
/*     */     default: 
/* 440 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void doBack()
/*     */   {
/* 450 */     if (this.opseq.isEmpty() == false) {
/* 451 */       int last = ((Integer)this.opseq.lastElement()).intValue();
/* 452 */       this.opseq.removeElementAt(this.opseq.size() - 1);
/*     */       
/* 454 */       switch (last)
/*     */       {
/*     */       case 1: 
/* 457 */         this.controller.solver.reset();
/* 458 */         this.state.back();
/* 459 */         this.controller.solver.getData(this.controller.data);
/*     */         
/* 461 */         this.tranPanel.removeAll();
/* 462 */         this.tranPanel.add(this.setupPanel, "North");
/* 463 */         this.backbutt.setEnabled(false);
/* 464 */         this.step = 1;
/*     */         
/* 466 */         revalidate();
/* 467 */         repaint();
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
/* 487 */         break;
/*     */       case 2: 
/* 471 */         this.controller.solver.reset();
/* 472 */         this.state.back();
/* 473 */         this.controller.solver.getData(this.controller.data);
/*     */         
/* 475 */         this.itModel.fireTableRowsDeleted(this.itercount, this.itercount);
/* 476 */         this.itercount -= 1;
/*     */         
/* 478 */         this.nextbutt.setEnabled(true);
/* 479 */         revalidate();
/* 480 */         repaint();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 487 */         break;
/*     */       default: 
/* 483 */         System.out.println("cannot here");
/*     */       }
/*     */       
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void doNext()
/*     */   {
/* 494 */     double[] fxco = new double[this.nTerm];double[] dfco = new double[this.nTerm];double[] bound = new double[2];
/* 495 */     int[] xp = new int[this.nTerm];int[] dp = new int[this.nTerm];
/* 496 */     Vector par = new Vector();
/* 497 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/* 499 */     switch (this.step) {
/*     */     case 1: 
/* 501 */       this.numTerm.setValue(this.nTerm);
/*     */       boolean ismax;
/* 503 */       boolean ismax; if (this.maxmin.getSelectedIndex() == 0) {
/* 504 */         ismax = true;
/*     */       } else {
/* 506 */         ismax = false;
/*     */       }
/* 508 */       for (int i = 0; i < this.nTerm; i++) {
/* 509 */         fxco[i] = this.fxcoef[i].getValue();
/* 510 */         dfco[i] = this.dfcoef[i].getValue();
/* 511 */         xp[i] = this.fxpow[i].getValue();
/* 512 */         dp[i] = this.dfpow[i].getValue();
/*     */       }
/* 514 */       bound[0] = this.lowbound.getValue();
/* 515 */       bound[1] = this.upbound.getValue();
/* 516 */       par.addElement(new Boolean(ismax));
/* 517 */       par.addElement(new Integer(this.nTerm));
/* 518 */       par.addElement(fxco);
/* 519 */       par.addElement(xp);
/* 520 */       par.addElement(dfco);
/* 521 */       par.addElement(dp);
/* 522 */       par.addElement(bound);
/*     */       
/* 524 */       opr.operation_code = 50101;
/* 525 */       opr.parameters = par;
/* 526 */       this.controller.solver.doWork(opr, this.controller.data);
/*     */       
/*     */ 
/* 529 */       this.state.addStep(opr);
/*     */       
/* 531 */       this.opseq.addElement(new Integer(this.step));
/*     */       
/*     */ 
/* 534 */       BuildUpPanel();
/* 535 */       this.tranPanel.removeAll();
/* 536 */       this.tranPanel.add(this.upPanel, "North");
/* 537 */       this.tranPanel.add(this.scrlPane);
/* 538 */       this.tranPanel.add(this.bottomPanel, "South");
/* 539 */       this.backbutt.setEnabled(true);
/* 540 */       this.step = 2;
/*     */       
/* 542 */       revalidate();
/* 543 */       repaint();
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
/* 583 */       break;
/*     */     case 2: 
/*     */       int uplow;
/*     */       int uplow;
/* 547 */       if (this.sel.getSelectedIndex() == 0) {
/* 548 */         uplow = 1;
/*     */       } else {
/* 550 */         uplow = 2;
/*     */       }
/* 552 */       par.addElement(new Integer(this.itercount + 1));
/* 553 */       par.addElement(new Integer(uplow));
/* 554 */       opr.operation_code = 50102;
/* 555 */       opr.parameters = par;
/* 556 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 557 */         String err = this.controller.solver.getErrInfo();
/* 558 */         JOptionPane.showMessageDialog(this, err);
/*     */       }
/*     */       else
/*     */       {
/* 562 */         this.itercount += 1;
/*     */         
/* 564 */         this.state.addStep(opr);
/*     */         
/* 566 */         this.opseq.addElement(new Integer(this.step));
/*     */         
/*     */ 
/* 569 */         if (this.itercount >= 20) {
/* 570 */           String err = String.valueOf(String.valueOf(new StringBuffer("You have completed maximum").append(this.decfm.format(20L)).append(" iterations.")));
/* 571 */           JOptionPane.showMessageDialog(this, err);
/* 572 */           this.nextbutt.setEnabled(false);
/*     */         }
/* 574 */         this.itModel.fireTableRowsInserted(this.itercount, this.itercount);
/* 575 */         revalidate();
/* 576 */         repaint();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 583 */       break;
/*     */     default: 
/* 579 */       System.out.println("cannot here");
/*     */       
/*     */ 
/*     */ 
/* 583 */       break;
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/* 593 */     int[] para = new int[3];
/*     */     try {
/* 595 */       this.state = ((IORState)in.readObject());
/* 596 */       para = (int[])in.readObject();
/* 597 */       this.opseq = ((Vector)in.readObject());
/* 598 */       this.state.setSolverStepVector();
/* 599 */       in.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 602 */       e1.printStackTrace();
/* 603 */       System.out.println("Open fails");
/*     */     }
/* 605 */     this.step = para[0];
/* 606 */     this.itercount = para[1];
/* 607 */     this.nTerm = para[2];
/* 608 */     this.numTerm.setValue(this.nTerm);
/*     */     
/* 610 */     if (this.step >= 1) {
/* 611 */       BuildSetupPanel(this.nTerm);
/* 612 */       if (this.controller.data.is_max) {
/* 613 */         this.maxmin.setSelectedIndex(0);
/*     */       } else {
/* 615 */         this.maxmin.setSelectedIndex(1);
/*     */       }
/* 617 */       for (int i = 0; i < this.nTerm; i++) {
/* 618 */         this.fxcoef[i].setValue(this.controller.data.fx_coef[i]);
/* 619 */         this.dfcoef[i].setValue(this.controller.data.df_coef[i]);
/* 620 */         this.fxpow[i].setValue(this.controller.data.fx_power[i]);
/* 621 */         this.dfpow[i].setValue(this.controller.data.df_power[i]);
/*     */       }
/* 623 */       this.lowbound.setValue(this.controller.data.low_bound[0]);
/* 624 */       this.upbound.setValue(this.controller.data.up_bound[0]);
/*     */     }
/* 626 */     if (this.step >= 2) {
/* 627 */       BuildUpPanel();
/* 628 */       this.tranPanel.removeAll();
/* 629 */       this.tranPanel.add(this.upPanel, "North");
/* 630 */       this.tranPanel.add(this.scrlPane);
/* 631 */       this.tranPanel.add(this.bottomPanel, "South");
/* 632 */       this.backbutt.setEnabled(true);
/* 633 */       this.itModel.fireTableStructureChanged();
/*     */     }
/* 635 */     if (this.step >= 3) {
/* 636 */       System.out.println("NL OneDimension has no this step.");
/*     */     }
/* 638 */     this.table.clearSelection();
/* 639 */     revalidate();
/* 640 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/* 650 */     int[] interpara = new int[3];
/*     */     
/* 652 */     interpara[0] = this.step;
/* 653 */     interpara[1] = this.itercount;
/* 654 */     interpara[2] = this.nTerm;
/*     */     try {
/* 656 */       out.writeObject(this.state);
/* 657 */       out.writeObject(interpara);
/* 658 */       out.writeObject(this.opseq);
/* 659 */       out.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 662 */       e1.printStackTrace();
/* 663 */       System.out.println("Save fails");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFunction()
/*     */   {
/* 675 */     double[] fxco = new double[this.nTerm];double[] dfco = new double[this.nTerm];double[] bound = new double[2];
/* 676 */     int[] xp = new int[this.nTerm];int[] dp = new int[this.nTerm];
/* 677 */     Vector par = new Vector();
/* 678 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/* 680 */     if (this.step == 1) { boolean ismax;
/*     */       boolean ismax;
/* 682 */       if (this.maxmin.getSelectedIndex() == 0) {
/* 683 */         ismax = true;
/*     */       } else {
/* 685 */         ismax = false;
/*     */       }
/* 687 */       for (int i = 0; i < this.nTerm; i++) {
/* 688 */         fxco[i] = this.fxcoef[i].getValue();
/* 689 */         dfco[i] = this.dfcoef[i].getValue();
/* 690 */         xp[i] = this.fxpow[i].getValue();
/* 691 */         dp[i] = this.dfpow[i].getValue();
/*     */       }
/* 693 */       bound[0] = this.lowbound.getValue();
/* 694 */       bound[1] = this.upbound.getValue();
/* 695 */       par.addElement(new Boolean(ismax));
/* 696 */       par.addElement(new Integer(this.nTerm));
/* 697 */       par.addElement(fxco);
/* 698 */       par.addElement(xp);
/* 699 */       par.addElement(dfco);
/* 700 */       par.addElement(dp);
/* 701 */       par.addElement(bound);
/*     */       
/* 703 */       opr.operation_code = 50101;
/* 704 */       opr.parameters = par;
/* 705 */       this.controller.solver.doWork(opr, this.controller.data);
/*     */     } }
/*     */   
/*     */   class IOROneDTableModel extends AbstractTableModel { private int i;
/*     */     private int j;
/*     */     
/*     */     IOROneDTableModel() {}
/*     */     
/* 713 */     public int getColumnCount() { return 6; }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 717 */       return IORNLOneDimensionPanel.this.itercount + 1;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col) {
/* 721 */       switch (col) {
/*     */       case 0: 
/* 723 */         return "Iteration";
/*     */       case 1: 
/* 725 */         return "x-";
/*     */       case 2: 
/* 727 */         return "x+";
/*     */       case 3: 
/* 729 */         return "New x'";
/*     */       case 4: 
/* 731 */         return "f(x')";
/*     */       case 5: 
/* 733 */         return "df(x)/d(x)";
/*     */       }
/* 735 */       return "err";
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 740 */       String str = new String();
/* 741 */       DecimalFormat decfm = new DecimalFormat("#.####");
/* 742 */       switch (col) {
/*     */       case 0: 
/* 744 */         return new Integer(row).toString();
/*     */       case 1: 
/* 746 */         return decfm.format(IORNLOneDimensionPanel.this.controller.data.low_bound[row]);
/*     */       case 2: 
/* 748 */         return decfm.format(IORNLOneDimensionPanel.this.controller.data.up_bound[row]);
/*     */       case 3: 
/* 750 */         return decfm.format(IORNLOneDimensionPanel.this.controller.data.newx[row]);
/*     */       case 4: 
/* 752 */         return decfm.format(IORNLOneDimensionPanel.this.controller.data.fx[row]);
/*     */       case 5: 
/* 754 */         return decfm.format(IORNLOneDimensionPanel.this.controller.data.df[row]);
/*     */       }
/* 756 */       return "err";
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c)
/*     */     {
/* 761 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean isCellEditable(int row, int col)
/*     */     {
/* 767 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNLOneDimensionPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */