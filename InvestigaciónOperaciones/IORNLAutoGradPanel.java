/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.border.EmptyBorder;
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
/*     */ public class IORNLAutoGradPanel
/*     */   extends IORActionPanel
/*     */ {
/*  31 */   private static final int MAXTERMS = 10;
/*  32 */   private final String LESSTHAN = "<=";
/*     */   
/*  34 */   private IORNLPProcessController controller = null;
/*  35 */   private JPanel mainPanel = new JPanel();
/*     */   
/*  37 */   private JPanel setupPanel = null;
/*  38 */   private JPanel transtPanel = new JPanel();
/*  39 */   private JPanel trialPanel = new JPanel();
/*  40 */   private JPanel errtpanel = new JPanel();
/*  41 */   private JPanel buttPanel = new JPanel();
/*  42 */   private JPanel resultPanel = new JPanel();
/*  43 */   private JPanel initPanel = new JPanel();
/*  44 */   private JPanel solutionPanel = new JPanel();
/*  45 */   private JPanel gradPanel = new JPanel();
/*  46 */   private JPanel errtoPanel = new JPanel();
/*  47 */   private JPanel fxPanel = new JPanel();
/*  48 */   private JPanel numPanel = new JPanel();
/*  49 */   private JButton okbutt = new JButton("OK");
/*     */   private DecimalField[] fxcoef;
/*     */   private DecimalField inix1fd;
/*     */   private DecimalField inix2fd;
/*  53 */   private DecimalField errtolerance; private WholeNumberField[] fx1exp; private WholeNumberField[] fx2exp; private WholeNumberField[] dfx1_x1exp; private WholeNumberField[] dfx1_x2exp; private WholeNumberField[] dfx2_x1exp; private WholeNumberField[] dfx2_x2exp; private WholeNumberField numTerm = new WholeNumberField(2, 2);
/*  54 */   private JLabel itemlabel = new JLabel("Number of Terms (<=10) : ");
/*  55 */   private JLabel comtlabel = new JLabel("(To make a new number above take effect, you must press the ENTER key.)");
/*  56 */   private JLabel instructionlabel = new JLabel("For each term, enter the coefficient on the left and the exponents on the right.");
/*  57 */   private DecimalFormat decfm = new DecimalFormat();
/*     */   
/*  59 */   private int step = 1;
/*  60 */   private int nTerm = 2;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORNLAutoGradPanel(IORNLPProcessController c)
/*     */   {
/*  69 */     super(c);
/*  70 */     this.controller = c;
/*  71 */     this.state = new IORState(this.controller.solver);
/*  72 */     add(this.mainPanel);
/*  73 */     this.actionStatus.setVisible(false);
/*  74 */     this.bn_back.setVisible(false);
/*  75 */     this.bn_finish.setVisible(false);
/*     */     
/*  77 */     this.decfm.setGroupingUsed(false);
/*  78 */     this.comtlabel.setAlignmentX(0.5F);
/*  79 */     this.instructionlabel.setAlignmentX(0.5F);
/*     */     
/*  81 */     this.inix1fd = new DecimalField(0.0D, 4, this.decfm);
/*  82 */     this.inix2fd = new DecimalField(0.0D, 4, this.decfm);
/*  83 */     this.errtolerance = new DecimalField(0.01D, 4, this.decfm);
/*     */     
/*  85 */     this.buttPanel.add(Box.createHorizontalStrut(10));
/*  86 */     this.buttPanel.add(this.okbutt);
/*     */     
/*  88 */     this.setupPanel = new JPanel();
/*  89 */     this.setupPanel.setLayout(new BoxLayout(this.setupPanel, 1));
/*  90 */     this.numPanel.add(this.itemlabel);
/*  91 */     this.numPanel.add(this.numTerm);
/*     */     
/*  93 */     this.trialPanel.add(new JLabel("Initial Trial Solution : (x1 , x2) = ("));
/*  94 */     this.trialPanel.add(this.inix1fd);
/*  95 */     this.trialPanel.add(new JLabel(" , "));
/*  96 */     this.trialPanel.add(this.inix2fd);
/*  97 */     this.trialPanel.add(new JLabel(" )"));
/*     */     
/*  99 */     this.errtpanel.setBorder(BorderFactory.createTitledBorder("Error Tolerance :  "));
/* 100 */     this.errtpanel.add(new JLabel("abs(df/dxj) <="));
/* 101 */     this.errtpanel.add(this.errtolerance);
/*     */     
/* 103 */     this.setupPanel.add(this.numPanel);
/* 104 */     this.setupPanel.add(Box.createVerticalStrut(10));
/* 105 */     this.setupPanel.add(this.comtlabel);
/* 106 */     this.setupPanel.add(Box.createVerticalStrut(20));
/* 107 */     this.setupPanel.add(this.instructionlabel);
/* 108 */     this.setupPanel.add(Box.createVerticalStrut(20));
/* 109 */     this.setupPanel.add(this.fxPanel);
/* 110 */     this.setupPanel.add(Box.createVerticalStrut(20));
/* 111 */     this.setupPanel.add(this.trialPanel);
/* 112 */     this.setupPanel.add(Box.createVerticalStrut(20));
/* 113 */     this.setupPanel.add(this.errtpanel);
/* 114 */     BuildSetupPanel(2);
/*     */     
/* 116 */     this.resultPanel.setLayout(new BoxLayout(this.resultPanel, 1));
/* 117 */     this.resultPanel.setPreferredSize(new Dimension(500, 140));
/* 118 */     this.resultPanel.setMaximumSize(new Dimension(500, 140));
/*     */     
/* 120 */     this.transtPanel.setLayout(new BorderLayout());
/* 121 */     this.transtPanel.add(this.setupPanel, "North");
/* 122 */     this.transtPanel.add(this.resultPanel, "South");
/*     */     
/* 124 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/* 125 */     this.mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
/* 126 */     this.mainPanel.add(this.transtPanel);
/* 127 */     this.mainPanel.add(Box.createVerticalStrut(20));
/* 128 */     this.mainPanel.add(this.buttPanel);
/*     */     
/*     */ 
/* 131 */     this.okbutt.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 133 */         IORNLAutoGradPanel.this.dookbutt();
/*     */       }
/*     */       
/* 136 */     });
/* 137 */     this.numTerm.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 139 */         int numterm = IORNLAutoGradPanel.this.numTerm.getValue();
/* 140 */         if (numterm < 1) {
/* 141 */           numterm = 1;
/* 142 */         } else if (numterm > 10)
/* 143 */           numterm = 10;
/* 144 */         IORNLAutoGradPanel.this.numTerm.setValue(numterm);
/* 145 */         IORNLAutoGradPanel.this.BuildSetupPanel(numterm);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void BuildSetupPanel(int numterm)
/*     */   {
/* 156 */     this.nTerm = numterm;
/* 157 */     this.numTerm.setValue(numterm);
/*     */     
/* 159 */     JLabel fxlabel = new JLabel("Max  f(X)   = ");
/* 160 */     this.fxcoef = new DecimalField[numterm];
/* 161 */     this.fx1exp = new WholeNumberField[numterm];
/* 162 */     this.fx2exp = new WholeNumberField[numterm];
/*     */     
/* 164 */     this.fxPanel.removeAll();
/* 165 */     for (int i = 0; i < numterm; i++) {
/* 166 */       this.fxcoef[i] = new DecimalField(0.0D, 4, this.decfm);
/* 167 */       this.fx1exp[i] = new WholeNumberField(0, 1);
/* 168 */       this.fx2exp[i] = new WholeNumberField(0, 1);
/*     */     }
/*     */     
/* 171 */     this.fxPanel.add(fxlabel);
/* 172 */     for (i = 0; i < numterm; i++) {
/* 173 */       if (i > 0) {
/* 174 */         this.fxPanel.add(new JLabel(" + "));
/*     */       }
/*     */       
/* 177 */       this.fxPanel.add(this.fxcoef[i]);
/* 178 */       this.fxPanel.add(new JLabel(" x1"));
/* 179 */       this.fxPanel.add(this.fx1exp[i]);
/* 180 */       this.fxPanel.add(new JLabel(" x2"));
/* 181 */       this.fxPanel.add(this.fx2exp[i]);
/*     */     }
/* 183 */     revalidate();
/* 184 */     repaint();
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
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 211 */     String str = "Automatic Gradient Search Procedure\n\n";
/* 212 */     str = String.valueOf(String.valueOf(str)).concat("This procedure (approximately) maximizes a concave polynomial function of two ");
/* 213 */     str = String.valueOf(String.valueOf(str)).concat("variables by using the gradient search procedure (presented in Sec. 13.5). ");
/* 214 */     str = String.valueOf(String.valueOf(str)).concat("You will enter the initial trial solution, the function to maximize, and an ");
/* 215 */     str = String.valueOf(String.valueOf(str)).concat("error tolerance (as defined in Sec. 13.5). The computer will then execute the procedure.\n\n");
/* 216 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of terms in f(X)\n\n");
/* 217 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of terms (a positive integer <= 10) in f(X), and then press the ENTER key to make the new number take effect.\n\n");
/* 218 */     str = String.valueOf(String.valueOf(str)).concat("Entering the objective function\n\n");
/* 219 */     str = String.valueOf(String.valueOf(str)).concat("The objective function must be a polynomial, where the exponent for each variable in ");
/* 220 */     str = String.valueOf(String.valueOf(str)).concat("each term is a nonnegative integer less than ten. For each term, you need to enter ");
/* 221 */     str = String.valueOf(String.valueOf(str)).concat("the coefficient (an integer or decimal number), and then the exponent (a ");
/* 222 */     str = String.valueOf(String.valueOf(str)).concat("nonnegative integer) for each variable. If a variable does not appear in the term, ");
/* 223 */     str = String.valueOf(String.valueOf(str)).concat("enter 0 for its exponent.\n\n");
/* 224 */     str = String.valueOf(String.valueOf(str)).concat("Entering the initial trial solution\n\n");
/* 225 */     str = String.valueOf(String.valueOf(str)).concat("Enter an integer or decimal number for the initial value of x1 and x2.\n\n");
/* 226 */     str = String.valueOf(String.valueOf(str)).concat("Entering the error tolerance\n\n");
/* 227 */     str = String.valueOf(String.valueOf(str)).concat("Enter the error tolerance (a positive integer or decimal number) as defined ");
/* 228 */     str = String.valueOf(String.valueOf(str)).concat("in Sec. 13.5 (where it is denoted by epsilon). Then press the OK button.");
/* 229 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 231 */     this.help_content_step.setText(str);
/* 232 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 239 */     String str = "Automatic Gradient Search Procedure\n\n";
/* 240 */     str = String.valueOf(String.valueOf(str)).concat("This procedure automatically applies the gradient search procedure presented in ");
/* 241 */     str = String.valueOf(String.valueOf(str)).concat("Sec. 13.5 for solving (approximately) a multivariable unconstrained optimization ");
/* 242 */     str = String.valueOf(String.valueOf(str)).concat("problem with exactly two variables. The function to be maximized is assumed to ");
/* 243 */     str = String.valueOf(String.valueOf(str)).concat("be concave. It also must be a polynomial where the exponent for each variable in ");
/* 244 */     str = String.valueOf(String.valueOf(str)).concat("each term is a nonnegative integer less than ten and where the number of terms is ");
/* 245 */     str = String.valueOf(String.valueOf(str)).concat("a positive integer <= 10.\n\n");
/* 246 */     str = String.valueOf(String.valueOf(str)).concat("You will begin by entering the function, an initial trial solution, and an error ");
/* 247 */     str = String.valueOf(String.valueOf(str)).concat("tolerance of at least 0.001 (as defined in Sec. 13.5). After solving, you can print ");
/* 248 */     str = String.valueOf(String.valueOf(str)).concat("out both the problem and solution by choosing Print to file under the File menu.");
/* 249 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 250 */     this.help_content_procedure.setText(str);
/* 251 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void dookbutt()
/*     */   {
/* 258 */     double[] fco = new double[this.nTerm];
/* 259 */     double[] inix = new double[2];
/* 260 */     int[] fx1ex = new int[this.nTerm];int[] fx2ex = new int[this.nTerm];
/*     */     
/* 262 */     Vector par = new Vector();
/* 263 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/*     */ 
/* 266 */     for (int i = 0; i < this.nTerm; i++) {
/* 267 */       fco[i] = this.fxcoef[i].getValue();
/* 268 */       fx1ex[i] = this.fx1exp[i].getValue();
/* 269 */       fx2ex[i] = this.fx2exp[i].getValue();
/*     */     }
/* 271 */     inix[0] = this.inix1fd.getValue();
/* 272 */     inix[1] = this.inix2fd.getValue();
/* 273 */     double errtol = this.errtolerance.getValue();
/*     */     
/* 275 */     par.add(new Integer(this.nTerm));
/* 276 */     par.add(fco);
/* 277 */     par.add(fx1ex);
/* 278 */     par.add(fx2ex);
/* 279 */     par.add(inix);
/* 280 */     par.add(new Double(errtol));
/*     */     
/* 282 */     opr.parameters = par;
/* 283 */     opr.operation_code = 50206;
/* 284 */     if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 285 */       this.resultPanel.removeAll();
/* 286 */       revalidate();
/* 287 */       repaint();
/* 288 */       JOptionPane.showMessageDialog(this, this.controller.solver.getErrInfo());
/* 289 */       return;
/*     */     }
/* 291 */     this.state.reset();
/* 292 */     this.state.addStep(opr);
/*     */     
/* 294 */     this.initPanel.removeAll();
/* 295 */     this.initPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Initial Trial Solution : (x1 , x2) = ( ").append(this.decfm.format(this.controller.data.oldx1[0])).append(" , ").append(this.decfm.format(this.controller.data.oldx2[0])).append(" )")))));
/* 296 */     this.solutionPanel.removeAll();
/* 297 */     this.solutionPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("The Solution is : (x1 , x2) = ( ").append(this.decfm.format(this.controller.data.newx1[0])).append(" , ").append(this.decfm.format(this.controller.data.newx2[0])).append(" )")))));
/* 298 */     this.gradPanel.removeAll();
/* 299 */     this.gradPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("grad f(x1, x2) =(").append(this.decfm.format(this.controller.data.gradx1[0])).append(" , ").append(this.decfm.format(this.controller.data.gradx2[0])).append(" )")))));
/* 300 */     this.errtoPanel.removeAll();
/* 301 */     this.errtoPanel.add(new JLabel("Error Tolerance is : ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.errTolerance))))));
/*     */     
/* 303 */     this.resultPanel.removeAll();
/* 304 */     this.resultPanel.add(this.initPanel);
/* 305 */     this.resultPanel.add(this.solutionPanel);
/* 306 */     this.resultPanel.add(this.gradPanel);
/* 307 */     this.resultPanel.add(this.errtoPanel);
/* 308 */     this.step = 2;
/*     */     
/* 310 */     revalidate();
/* 311 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/* 322 */     int[] para = new int[2];
/*     */     try
/*     */     {
/* 325 */       this.state = ((IORState)in.readObject());
/* 326 */       para = (int[])in.readObject();
/* 327 */       this.state.setSolverStepVector();
/* 328 */       in.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 331 */       e1.printStackTrace();
/* 332 */       System.out.println("Open fails");
/*     */     }
/* 334 */     this.step = para[0];
/* 335 */     this.nTerm = para[1];
/* 336 */     this.numTerm.setValue(this.nTerm);
/*     */     
/* 338 */     if (this.step >= 1) {
/* 339 */       BuildSetupPanel(this.nTerm);
/* 340 */       for (int i = 0; i < this.nTerm; i++) {
/* 341 */         this.fxcoef[i].setValue(this.controller.data.fx1x2_coef[i]);
/* 342 */         this.fx1exp[i].setValue(this.controller.data.fx_x1exp[i]);
/* 343 */         this.fx2exp[i].setValue(this.controller.data.fx_x2exp[i]);
/*     */       }
/* 345 */       this.inix1fd.setValue(this.controller.data.oldx1[0]);
/* 346 */       this.inix2fd.setValue(this.controller.data.oldx2[0]);
/* 347 */       this.errtolerance.setValue(this.controller.data.errTolerance);
/*     */     }
/*     */     
/* 350 */     if (this.step >= 2) {
/* 351 */       this.initPanel.removeAll();
/* 352 */       this.initPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("Initial Trial Solution : (x1, x2) = ( ").append(this.decfm.format(this.controller.data.oldx1[0])).append(" , ").append(this.decfm.format(this.controller.data.oldx2[0])).append(" )")))));
/* 353 */       this.solutionPanel.removeAll();
/* 354 */       this.solutionPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("The Solution is : (x1 x2) = ( ").append(this.decfm.format(this.controller.data.newx1[0])).append(" , ").append(this.decfm.format(this.controller.data.newx2[0])).append(" )")))));
/* 355 */       this.gradPanel.removeAll();
/* 356 */       this.gradPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("grad f(x1, x2) =(").append(this.decfm.format(this.controller.data.gradx1[0])).append(" , ").append(this.decfm.format(this.controller.data.gradx2[0])).append(" )")))));
/* 357 */       this.errtoPanel.removeAll();
/* 358 */       this.errtoPanel.add(new JLabel("Error Tolerance is : ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.errTolerance))))));
/*     */       
/* 360 */       this.resultPanel.removeAll();
/* 361 */       this.resultPanel.add(this.initPanel);
/* 362 */       this.resultPanel.add(this.solutionPanel);
/* 363 */       this.resultPanel.add(this.gradPanel);
/* 364 */       this.resultPanel.add(this.errtoPanel);
/*     */     }
/* 366 */     if (this.step >= 3) {
/* 367 */       System.out.println("NL Auto Gradient has no this step.");
/*     */     }
/* 369 */     revalidate();
/* 370 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/* 380 */     int[] interpara = new int[2];
/*     */     
/* 382 */     interpara[0] = this.step;
/* 383 */     interpara[1] = this.nTerm;
/*     */     try {
/* 385 */       out.writeObject(this.state);
/* 386 */       out.writeObject(interpara);
/* 387 */       out.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 390 */       e1.printStackTrace();
/* 391 */       System.out.println("Save fails");
/*     */     }
/*     */   }
/*     */   
/*     */   public void SaveFunction() {}
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNLAutoGradPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */