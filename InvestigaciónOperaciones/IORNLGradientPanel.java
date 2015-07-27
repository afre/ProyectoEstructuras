/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Dimension;
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
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.table.AbstractTableModel;
/*      */ import javax.swing.table.TableColumn;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IORNLGradientPanel
/*      */   extends IORActionPanel
/*      */ {
/*   29 */   private static final int MAXTERMS = 10;
/*   30 */   private final String LESSTHAN = "<=";
/*      */   
/*   32 */   private IORNLPProcessController controller = null;
/*      */   
/*   34 */   private JPanel mainPanel = new JPanel();
/*      */   
/*   36 */   private JPanel setupPanel = null;
/*   37 */   private JPanel functionPanel = null;
/*   38 */   private JPanel upPanel = null;
/*   39 */   private JPanel bottomPanel = null;
/*   40 */   private JPanel transtPanel = new JPanel();
/*   41 */   private JScrollPane scrlPane = null;
/*   42 */   private JPanel trialPanel = new JPanel();
/*   43 */   private JPanel curptPanel = new JPanel();
/*   44 */   private JPanel buttPanel = new JPanel();
/*      */   private JPanel a1Panel;
/*      */   private JPanel a2Panel;
/*   47 */   private JPanel b1Panel; private JPanel b2Panel; private JPanel inputPanel; private JLabel indilabel = new JLabel();
/*   48 */   private JLabel solulabel = new JLabel();
/*   49 */   private JLabel grad1label = new JLabel();
/*   50 */   private JLabel grad2label = new JLabel();
/*   51 */   private JLabel ftlabel = new JLabel();
/*   52 */   private JLabel xvlabel = new JLabel();
/*   53 */   private JLabel xtlabel = new JLabel();
/*      */   
/*   55 */   private JPanel htexPanel = new JPanel();
/*   56 */   private JPanel fxPanel = new JPanel();
/*   57 */   private JPanel dfx1Panel = new JPanel();
/*   58 */   private JPanel dfx2Panel = new JPanel();
/*   59 */   private JPanel numPanel = new JPanel();
/*      */   
/*   61 */   private IORNLGradientPanel.IORGradTableModel gradModel = null;
/*   62 */   private JTable table = null;
/*      */   
/*   64 */   private JButton backbutt = new JButton("Back");
/*   65 */   private JButton nextbutt = new JButton("Next");
/*      */   private DecimalField[] fxcoef;
/*      */   private DecimalField[] dfx1coef;
/*      */   private DecimalField[] dfx2coef;
/*      */   private DecimalField inix1fd;
/*   70 */   private DecimalField inix2fd; private DecimalField a1fd; private DecimalField b1fd; private DecimalField a2fd; private DecimalField b2fd; private DecimalField newx1fd; private DecimalField newx2fd; private WholeNumberField[] fx1exp; private WholeNumberField[] fx2exp; private WholeNumberField[] dfx1_x1exp; private WholeNumberField[] dfx1_x2exp; private WholeNumberField[] dfx2_x1exp; private WholeNumberField[] dfx2_x2exp; private WholeNumberField numTerm = new WholeNumberField(2, 2);
/*      */   
/*   72 */   private JLabel itemlabel = new JLabel("Number of Terms in f(X) (<=10) : ");
/*   73 */   private JLabel comtlabel = new JLabel("(To make a new number above take effect, you must press the ENTER key.)");
/*   74 */   private JLabel instructionlabel = new JLabel("For each term, enter the coefficient on the left and the exponents on the right.");
/*      */   
/*   76 */   private int step = 1;
/*   77 */   private int itercount = 1;
/*   78 */   private int nTerm = 2;
/*   79 */   private DecimalFormat decfm = new DecimalFormat();
/*      */   
/*   81 */   private Vector opseq = new Vector();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IORNLGradientPanel(IORNLPProcessController c)
/*      */   {
/*   90 */     super(c);
/*   91 */     this.controller = c;
/*   92 */     this.state = new IORState(this.controller.solver);
/*   93 */     add(this.mainPanel);
/*   94 */     this.actionStatus.setVisible(false);
/*   95 */     this.bn_back.setVisible(false);
/*   96 */     this.bn_finish.setVisible(false);
/*      */     
/*   98 */     this.decfm.setGroupingUsed(false);
/*   99 */     this.comtlabel.setAlignmentX(0.5F);
/*  100 */     this.instructionlabel.setAlignmentX(0.5F);
/*      */     
/*      */ 
/*  103 */     this.inix1fd = new DecimalField(0.0D, 4, this.decfm);
/*  104 */     this.inix2fd = new DecimalField(0.0D, 4, this.decfm);
/*  105 */     this.a1fd = new DecimalField(0.0D, 4, this.decfm);
/*  106 */     this.b1fd = new DecimalField(0.0D, 4, this.decfm);
/*  107 */     this.a2fd = new DecimalField(0.0D, 4, this.decfm);
/*  108 */     this.b2fd = new DecimalField(0.0D, 4, this.decfm);
/*  109 */     this.newx1fd = new DecimalField(0.0D, 4, this.decfm);
/*  110 */     this.newx2fd = new DecimalField(0.0D, 4, this.decfm);
/*      */     
/*  112 */     this.backbutt.setEnabled(false);
/*  113 */     this.buttPanel.add(this.backbutt);
/*  114 */     this.buttPanel.add(Box.createHorizontalStrut(10));
/*  115 */     this.buttPanel.add(this.nextbutt);
/*  116 */     this.buttPanel.setPreferredSize(new Dimension(300, 50));
/*  117 */     this.buttPanel.setMaximumSize(new Dimension(300, 50));
/*      */     
/*  119 */     this.setupPanel = new JPanel();
/*  120 */     this.setupPanel.setLayout(new BoxLayout(this.setupPanel, 1));
/*  121 */     this.numPanel.add(this.itemlabel);
/*  122 */     this.numPanel.add(this.numTerm);
/*  123 */     this.trialPanel.add(new JLabel("Initial Trial Solution : (x1, x2) = ("));
/*  124 */     this.trialPanel.add(this.inix1fd);
/*  125 */     this.trialPanel.add(new JLabel(" , "));
/*  126 */     this.trialPanel.add(this.inix2fd);
/*  127 */     this.trialPanel.add(new JLabel(" )"));
/*      */     
/*  129 */     this.setupPanel.add(this.numPanel);
/*  130 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*  131 */     this.setupPanel.add(this.comtlabel);
/*  132 */     this.setupPanel.add(Box.createVerticalStrut(30));
/*  133 */     this.setupPanel.add(this.instructionlabel);
/*  134 */     this.setupPanel.add(Box.createVerticalStrut(30));
/*  135 */     this.setupPanel.add(this.fxPanel);
/*  136 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*  137 */     this.setupPanel.add(this.dfx1Panel);
/*  138 */     this.setupPanel.add(Box.createVerticalStrut(20));
/*  139 */     this.setupPanel.add(this.dfx2Panel);
/*  140 */     this.setupPanel.add(Box.createVerticalStrut(30));
/*  141 */     this.setupPanel.add(this.trialPanel);
/*      */     
/*  143 */     BuildSetupPanel(this.nTerm);
/*      */     
/*  145 */     this.transtPanel.setLayout(new BorderLayout());
/*  146 */     this.transtPanel.add(this.setupPanel, "North");
/*      */     
/*  148 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*  149 */     this.mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
/*  150 */     this.mainPanel.add(this.transtPanel);
/*  151 */     this.mainPanel.add(Box.createVerticalStrut(10));
/*  152 */     this.mainPanel.add(this.buttPanel);
/*      */     
/*  154 */     this.functionPanel = new JPanel();
/*  155 */     this.functionPanel.setPreferredSize(new Dimension(500, 70));
/*  156 */     this.functionPanel.setMinimumSize(new Dimension(500, 70));
/*  157 */     this.functionPanel.setMaximumSize(new Dimension(500, 70));
/*      */     
/*  159 */     this.curptPanel.add(this.solulabel);
/*  160 */     this.upPanel = new JPanel();
/*  161 */     this.upPanel.setLayout(new BorderLayout());
/*  162 */     this.upPanel.add(this.curptPanel, "North");
/*  163 */     this.upPanel.add(this.functionPanel, "Center");
/*      */     
/*  165 */     this.a1Panel = new JPanel();
/*  166 */     this.a1Panel.add(new JLabel("a = "));
/*  167 */     this.a1Panel.add(this.a1fd);
/*      */     
/*  169 */     this.a2Panel = new JPanel();
/*  170 */     this.a2Panel.add(new JLabel("a = "));
/*  171 */     this.a2Panel.add(this.a2fd);
/*      */     
/*  173 */     this.b1Panel = new JPanel();
/*  174 */     this.b1Panel.add(new JLabel("b = "));
/*  175 */     this.b1Panel.add(this.b1fd);
/*      */     
/*  177 */     this.b2Panel = new JPanel();
/*  178 */     this.b2Panel.add(new JLabel("b = "));
/*  179 */     this.b2Panel.add(this.b2fd);
/*      */     
/*  181 */     this.inputPanel = new JPanel(new GridLayout(3, 2));
/*  182 */     this.inputPanel.setAlignmentX(0.5F);
/*  183 */     this.inputPanel.setPreferredSize(new Dimension(400, 70));
/*  184 */     this.inputPanel.setMinimumSize(new Dimension(400, 70));
/*  185 */     this.inputPanel.setMaximumSize(new Dimension(400, 70));
/*  186 */     JLabel atlabel = new JLabel("x1 = a + bt");
/*  187 */     JLabel btlabel = new JLabel("x2 = a + bt");
/*  188 */     atlabel.setAlignmentX(0.5F);
/*  189 */     btlabel.setAlignmentX(0.5F);
/*  190 */     JPanel atpanel = new JPanel();
/*  191 */     JPanel btpanel = new JPanel();
/*  192 */     atpanel.add(atlabel);
/*  193 */     btpanel.add(btlabel);
/*  194 */     this.inputPanel.add(atpanel);
/*  195 */     this.inputPanel.add(btpanel);
/*  196 */     this.inputPanel.add(this.a1Panel);
/*  197 */     this.inputPanel.add(this.a2Panel);
/*  198 */     this.inputPanel.add(this.b1Panel);
/*  199 */     this.inputPanel.add(this.b2Panel);
/*      */     
/*  201 */     this.bottomPanel = new JPanel();
/*  202 */     this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel, 1));
/*  203 */     this.xtlabel.setText("For the column, X'+t[grad f(X')], express xj as a function of t.");
/*  204 */     this.xtlabel.setAlignmentX(0.5F);
/*  205 */     this.bottomPanel.add(this.xtlabel);
/*  206 */     this.bottomPanel.add(Box.createVerticalStrut(10));
/*  207 */     this.bottomPanel.add(this.inputPanel);
/*      */     
/*  209 */     this.gradModel = new IORNLGradientPanel.IORGradTableModel();
/*  210 */     this.table = new JTable(this.gradModel);
/*  211 */     this.table.setCellSelectionEnabled(true);
/*  212 */     this.table.setEnabled(false);
/*  213 */     this.table.getColumn("Iter.").setPreferredWidth(10);
/*  214 */     this.table.getColumn("X'+t[grad f(X')]").setPreferredWidth(100);
/*  215 */     this.table.getColumn("t*").setPreferredWidth(20);
/*  216 */     this.scrlPane = new JScrollPane(this.table);
/*  217 */     this.scrlPane.setPreferredSize(new Dimension(600, 200));
/*      */     
/*      */ 
/*      */ 
/*  221 */     this.nextbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  223 */         IORNLGradientPanel.this.doNext();
/*      */       }
/*  225 */     });
/*  226 */     this.backbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  228 */         IORNLGradientPanel.this.doBack();
/*      */       }
/*      */       
/*  231 */     });
/*  232 */     this.numTerm.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  234 */         int numterm = IORNLGradientPanel.this.numTerm.getValue();
/*  235 */         if (numterm < 1) {
/*  236 */           numterm = 1;
/*  237 */         } else if (numterm > 10)
/*  238 */           numterm = 10;
/*  239 */         IORNLGradientPanel.this.numTerm.setValue(numterm);
/*  240 */         IORNLGradientPanel.this.BuildSetupPanel(numterm);
/*      */       }
/*      */       
/*      */ 
/*  244 */     });
/*  245 */     this.ftlabel.setAlignmentX(0.5F);
/*  246 */     this.indilabel.setAlignmentX(0.5F);
/*  247 */     this.xvlabel.setAlignmentX(0.5F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildSetupPanel(int numterm)
/*      */   {
/*  256 */     this.nTerm = numterm;
/*  257 */     this.numTerm.setValue(numterm);
/*      */     
/*  259 */     JLabel fxlabel = new JLabel("Max  f(X)   = ");
/*  260 */     JLabel dfx1label = new JLabel("df(X)/dx1  = ");
/*  261 */     JLabel dfx2label = new JLabel("df(X)/dx2  = ");
/*  262 */     this.fxcoef = new DecimalField[numterm];
/*  263 */     this.dfx1coef = new DecimalField[numterm];
/*  264 */     this.dfx2coef = new DecimalField[numterm];
/*  265 */     this.fx1exp = new WholeNumberField[numterm];
/*  266 */     this.fx2exp = new WholeNumberField[numterm];
/*  267 */     this.dfx1_x1exp = new WholeNumberField[numterm];
/*  268 */     this.dfx1_x2exp = new WholeNumberField[numterm];
/*  269 */     this.dfx2_x1exp = new WholeNumberField[numterm];
/*  270 */     this.dfx2_x2exp = new WholeNumberField[numterm];
/*      */     
/*  272 */     this.fxPanel.removeAll();
/*  273 */     this.dfx1Panel.removeAll();
/*  274 */     this.dfx2Panel.removeAll();
/*  275 */     for (int i = 0; i < numterm; i++) {
/*  276 */       this.fxcoef[i] = new DecimalField(0.0D, 4, this.decfm);
/*  277 */       this.dfx1coef[i] = new DecimalField(0.0D, 4, this.decfm);
/*  278 */       this.dfx2coef[i] = new DecimalField(0.0D, 4, this.decfm);
/*  279 */       this.fx1exp[i] = new WholeNumberField(0, 1);
/*  280 */       this.fx2exp[i] = new WholeNumberField(0, 1);
/*  281 */       this.dfx1_x1exp[i] = new WholeNumberField(0, 1);
/*  282 */       this.dfx1_x2exp[i] = new WholeNumberField(0, 1);
/*  283 */       this.dfx2_x1exp[i] = new WholeNumberField(0, 1);
/*  284 */       this.dfx2_x2exp[i] = new WholeNumberField(0, 1);
/*      */     }
/*      */     
/*      */ 
/*  288 */     this.fxPanel.add(fxlabel);
/*  289 */     this.dfx1Panel.add(dfx1label);
/*  290 */     this.dfx2Panel.add(dfx2label);
/*  291 */     for (i = 0; i < numterm; i++) {
/*  292 */       if (i > 0) {
/*  293 */         this.fxPanel.add(new JLabel(" + "));
/*  294 */         this.dfx1Panel.add(new JLabel(" + "));
/*  295 */         this.dfx2Panel.add(new JLabel(" + "));
/*      */       }
/*      */       
/*  298 */       this.fxPanel.add(this.fxcoef[i]);
/*  299 */       this.fxPanel.add(new JLabel(" x1"));
/*  300 */       this.fxPanel.add(this.fx1exp[i]);
/*  301 */       this.fxPanel.add(new JLabel(" x2"));
/*  302 */       this.fxPanel.add(this.fx2exp[i]);
/*      */       
/*  304 */       this.dfx1Panel.add(this.dfx1coef[i]);
/*  305 */       this.dfx1Panel.add(new JLabel(" x1"));
/*  306 */       this.dfx1Panel.add(this.dfx1_x1exp[i]);
/*  307 */       this.dfx1Panel.add(new JLabel(" x2"));
/*  308 */       this.dfx1Panel.add(this.dfx1_x2exp[i]);
/*      */       
/*  310 */       this.dfx2Panel.add(this.dfx2coef[i]);
/*  311 */       this.dfx2Panel.add(new JLabel(" x1"));
/*  312 */       this.dfx2Panel.add(this.dfx2_x1exp[i]);
/*  313 */       this.dfx2Panel.add(new JLabel(" x2"));
/*  314 */       this.dfx2Panel.add(this.dfx2_x2exp[i]);
/*      */     }
/*  316 */     revalidate();
/*  317 */     repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildUpPanel()
/*      */   {
/*  326 */     JPanel fnamePanel = new JPanel();JPanel df1namePanel = new JPanel();JPanel df2namePanel = new JPanel();
/*  327 */     JPanel empPanel = new JPanel();JPanel df1valPanel = new JPanel();JPanel df2valPanel = new JPanel();
/*      */     
/*  329 */     JPanel fexpPanel = Function2Panel(this.nTerm, this.controller.data.fx1x2_coef, this.controller.data.fx_x1exp, this.controller.data.fx_x2exp);
/*  330 */     JPanel df1exPanel = Function2Panel(this.nTerm, this.controller.data.dfx1_coef, this.controller.data.dfx1_x1exp, this.controller.data.dfx1_x2exp);
/*  331 */     JPanel df2exPanel = Function2Panel(this.nTerm, this.controller.data.dfx2_coef, this.controller.data.dfx2_x1exp, this.controller.data.dfx2_x2exp);
/*      */     
/*  333 */     JPanel nmpanel = new JPanel();
/*  334 */     JPanel tmpanel = new JPanel();
/*  335 */     JPanel eqpanel = new JPanel();
/*      */     
/*  337 */     nmpanel.setLayout(new GridLayout(3, 1));
/*  338 */     tmpanel.setLayout(new GridLayout(3, 1));
/*  339 */     eqpanel.setLayout(new GridLayout(3, 1));
/*      */     
/*  341 */     fnamePanel.setPreferredSize(new Dimension(70, 30));
/*  342 */     df1namePanel.setPreferredSize(new Dimension(70, 30));
/*  343 */     df2namePanel.setPreferredSize(new Dimension(70, 30));
/*  344 */     empPanel.setPreferredSize(new Dimension(70, 30));
/*  345 */     df1valPanel.setPreferredSize(new Dimension(70, 30));
/*  346 */     df2valPanel.setPreferredSize(new Dimension(70, 30));
/*      */     
/*  348 */     fnamePanel.add(new JLabel("Max  f(X) =  "));
/*  349 */     df1namePanel.add(new JLabel("df(X)/dx1 = "));
/*  350 */     df2namePanel.add(new JLabel("df(X)/dx2 = "));
/*      */     
/*  352 */     nmpanel.add(fnamePanel);
/*  353 */     nmpanel.add(df1namePanel);
/*  354 */     nmpanel.add(df2namePanel);
/*      */     
/*  356 */     tmpanel.add(fexpPanel);
/*  357 */     tmpanel.add(df1exPanel);
/*  358 */     tmpanel.add(df2exPanel);
/*      */     
/*  360 */     String grs1 = " = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.gradx1[(this.itercount - 1)]))));
/*  361 */     this.grad1label.setText(grs1);
/*  362 */     String grs2 = " = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.gradx2[(this.itercount - 1)]))));
/*  363 */     this.grad2label.setText(grs2);
/*  364 */     empPanel.add(new JLabel(" "));
/*  365 */     df1valPanel.add(this.grad1label, 0);
/*  366 */     df2valPanel.add(this.grad2label, 0);
/*      */     
/*  368 */     eqpanel.add(empPanel);
/*  369 */     eqpanel.add(df1valPanel);
/*  370 */     eqpanel.add(df2valPanel);
/*      */     
/*  372 */     this.functionPanel.setPreferredSize(new Dimension(this.nTerm * 80 + 200, 120));
/*  373 */     this.functionPanel.setMaximumSize(new Dimension(this.nTerm * 80 + 200, 130));
/*  374 */     this.functionPanel.removeAll();
/*  375 */     this.functionPanel.add(nmpanel);
/*  376 */     this.functionPanel.add(tmpanel);
/*  377 */     this.functionPanel.add(eqpanel);
/*      */     
/*  379 */     String trs = new String("Current Trial Solution : (x1, x2) = ( ");
/*  380 */     trs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(trs))).append(this.decfm.format(this.controller.data.oldx1[(this.itercount - 1)])).append(" , ")));
/*  381 */     trs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(trs))).append(this.decfm.format(this.controller.data.oldx2[(this.itercount - 1)])).append(" )")));
/*  382 */     this.solulabel.setText(trs);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private JPanel Function2Panel(int numtm, double[] coef, int[] expnx1, int[] expnx2)
/*      */   {
/*  391 */     JPanel rtPanel = new JPanel();
/*  392 */     rtPanel.setPreferredSize(new Dimension(numtm * 80, 30));
/*  393 */     rtPanel.setMaximumSize(new Dimension(numtm * 80, 30));
/*      */     
/*  395 */     for (int i = 0; i < numtm; i++) {
/*  396 */       if ((i > 0) && (coef[i] > 0.01D)) {
/*  397 */         rtPanel.add(new JLabel(" + "));
/*      */       }
/*      */       else
/*  400 */         rtPanel.add(new JLabel(" "));
/*  401 */       if (Math.abs(coef[i]) > 0.01D) {
/*  402 */         rtPanel.add(new JLabel(this.decfm.format(coef[i])));
/*  403 */         if (expnx1[i] > 1) {
/*  404 */           JLabel xlabel = new JLabel("x");
/*  405 */           xlabel.setFont(new Font("Default", 1, 14));
/*  406 */           rtPanel.add(new ScriptWriter(xlabel, "".concat(String.valueOf(String.valueOf(expnx1[i]))), "1"));
/*      */         }
/*  408 */         else if (expnx1[i] == 1) {
/*  409 */           JLabel xlabel = new JLabel("x");
/*  410 */           xlabel.setFont(new Font("Default", 1, 14));
/*  411 */           rtPanel.add(new ScriptWriter(xlabel, " ", "1"));
/*      */         }
/*      */         
/*  414 */         if (expnx2[i] > 1) {
/*  415 */           JLabel xlabel = new JLabel("x");
/*  416 */           xlabel.setFont(new Font("Default", 1, 14));
/*  417 */           rtPanel.add(new ScriptWriter(xlabel, "".concat(String.valueOf(String.valueOf(expnx2[i]))), "2"));
/*      */         }
/*  419 */         else if (expnx2[i] == 1) {
/*  420 */           JLabel xlabel = new JLabel("x");
/*  421 */           xlabel.setFont(new Font("Default", 1, 14));
/*  422 */           rtPanel.add(new ScriptWriter(xlabel, " ", "2"));
/*      */         }
/*      */       }
/*      */     }
/*  426 */     return rtPanel;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BuildTFunction()
/*      */   {
/*  434 */     int ntf = this.controller.data.t_function_num_term;
/*  435 */     this.htexPanel.setPreferredSize(new Dimension(ntf * 80 + 150, 30));
/*  436 */     this.htexPanel.setMaximumSize(new Dimension(ntf * 80 + 160, 30));
/*      */     
/*  438 */     this.htexPanel.removeAll();
/*  439 */     this.htexPanel.add(new JLabel("f( X'+t[grad f(X')] )  =  "));
/*  440 */     for (int i = 0; i < this.controller.data.t_function_num_term; i++)
/*      */     {
/*  442 */       if ((i > 0) && (this.controller.data.ft_coef[i] > 0.01D)) {
/*  443 */         this.htexPanel.add(new JLabel(" + "));
/*      */       } else
/*  445 */         this.htexPanel.add(new JLabel("  "));
/*  446 */       if (Math.abs(this.controller.data.ft_coef[i]) > 0.01D) {
/*  447 */         this.htexPanel.add(new JLabel(this.decfm.format(this.controller.data.ft_coef[i])));
/*  448 */         if (this.controller.data.ft_texp[i] > 1) {
/*  449 */           JLabel tlabel = new JLabel("t");
/*  450 */           tlabel.setFont(new Font("Default", 1, 14));
/*  451 */           this.htexPanel.add(new ScriptWriter(tlabel, "".concat(String.valueOf(String.valueOf(this.controller.data.ft_texp[i]))), ""));
/*      */         }
/*  453 */         else if (this.controller.data.ft_texp[i] == 1) {
/*  454 */           JLabel tlabel = new JLabel("t");
/*  455 */           tlabel.setFont(new Font("Default", 1, 14));
/*  456 */           this.htexPanel.add(new ScriptWriter(tlabel, "", ""));
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  461 */     this.bottomPanel.removeAll();
/*  462 */     this.bottomPanel.add(this.htexPanel);
/*  463 */     this.bottomPanel.add(Box.createVerticalStrut(10));
/*  464 */     this.indilabel.setText("Press the NEXT button to solve for t* using one-dimensional search procedure.");
/*  465 */     this.bottomPanel.add(this.indilabel);
/*  466 */     this.bottomPanel.add(Box.createVerticalStrut(10));
/*      */   }
/*      */   
/*      */ 
/*      */   private void BuildNewxPanel()
/*      */   {
/*  472 */     String xvs = new String();
/*      */     
/*  474 */     xvs = "x1' = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.a1[(this.itercount - 1)]))));
/*  475 */     if (this.controller.data.b1[(this.itercount - 1)] > -0.01D) {
/*  476 */       xvs = String.valueOf(String.valueOf(xvs)).concat(" + ");
/*      */     }
/*  478 */     xvs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(xvs))).append(this.decfm.format(this.controller.data.b1[(this.itercount - 1)])).append(" t* ")));
/*  479 */     xvs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(xvs))).append("  = ").append(this.decfm.format(this.controller.data.newx1[(this.itercount - 1)]))));
/*  480 */     xvs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(xvs))).append("     x2' = ").append(this.decfm.format(this.controller.data.a2[(this.itercount - 1)]))));
/*  481 */     if (this.controller.data.b2[(this.itercount - 1)] > -0.01D) {
/*  482 */       xvs = String.valueOf(String.valueOf(xvs)).concat(" + ");
/*      */     }
/*  484 */     xvs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(xvs))).append(this.decfm.format(this.controller.data.b2[(this.itercount - 1)])).append("  t* ")));
/*  485 */     xvs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(xvs))).append("  = ").append(this.decfm.format(this.controller.data.newx2[(this.itercount - 1)]))));
/*      */     
/*  487 */     JPanel xPanel = new JPanel();
/*  488 */     xPanel.add(new JLabel("Enter the new trial solution :      (x1, x2) = ( "));
/*  489 */     xPanel.add(this.newx1fd);
/*  490 */     xPanel.add(new JLabel(" , "));
/*  491 */     xPanel.add(this.newx2fd);
/*  492 */     xPanel.add(new JLabel(" )"));
/*      */     
/*  494 */     this.bottomPanel.removeAll();
/*  495 */     this.xvlabel.setText(xvs);
/*  496 */     this.bottomPanel.add(this.xvlabel);
/*  497 */     this.bottomPanel.add(Box.createVerticalStrut(10));
/*  498 */     this.bottomPanel.add(xPanel);
/*  499 */     this.bottomPanel.add(Box.createVerticalStrut(10));
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
/*  526 */     String str = "Interactive Gradient Search Procedure\n\n";
/*  527 */     str = String.valueOf(String.valueOf(str)).concat("This procedure (approximately) maximizes a concave polynomial function of two ");
/*  528 */     str = String.valueOf(String.valueOf(str)).concat("variables by using the gradient search procedure presented in Sec. 13.5. ");
/*  529 */     str = String.valueOf(String.valueOf(str)).concat("You will enter the polynomial to maximize and its derivative. You and the ");
/*  530 */     str = String.valueOf(String.valueOf(str)).concat("computer will interactively perform as many iterations as desired by identifying ");
/*  531 */     str = String.valueOf(String.valueOf(str)).concat("the entries in a table like Table 13.2. \n\n");
/*  532 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of terms in f(X)\n\n");
/*  533 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of terms(a positive integer <= 10) in f(X), and then press the ENTER key to make the new number take effect.\n\n");
/*  534 */     str = String.valueOf(String.valueOf(str)).concat("Entering the objective function\n\n");
/*  535 */     str = String.valueOf(String.valueOf(str)).concat("The objective function must be a polynomial, where the exponent for each variable in ");
/*  536 */     str = String.valueOf(String.valueOf(str)).concat("each term is a nonnegative integer less than ten. For each term, you need to enter ");
/*  537 */     str = String.valueOf(String.valueOf(str)).concat("the coefficient (an integer or a decimal number), and then the exponent (a ");
/*  538 */     str = String.valueOf(String.valueOf(str)).concat("nonnegative integer) for each variable. If a variable does not appear in the term, ");
/*  539 */     str = String.valueOf(String.valueOf(str)).concat("enter 0 for its exponent.\n\n");
/*  540 */     str = String.valueOf(String.valueOf(str)).concat("Entering the derivative of the objective function\n\n");
/*  541 */     str = String.valueOf(String.valueOf(str)).concat("For each term of the derivative of the objective function, you need to enter the ");
/*  542 */     str = String.valueOf(String.valueOf(str)).concat("coefficient (an integer or a decimal number), and then the exponent (a ");
/*  543 */     str = String.valueOf(String.valueOf(str)).concat("nonnegative integer <= 10) for each variable. If a variable does not appear in the term, ");
/*  544 */     str = String.valueOf(String.valueOf(str)).concat("enter 0 for its exponent. If there are no more terms, then enter 0 for the coefficient.\n\n");
/*  545 */     str = String.valueOf(String.valueOf(str)).concat("Entering the initial trial solution\n\n");
/*  546 */     str = String.valueOf(String.valueOf(str)).concat("Enter the initial values (integers or decimal numbers) for x1 and x2.");
/*  547 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/*  549 */     this.help_content_step.setText(str);
/*  550 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*  553 */   private String str0 = "Interactive Gradient Search Procedure\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  559 */   private String str1 = "Entering the number of terms in f(X)\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  574 */   private String str2 = "Entering the a's and the b's\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  581 */   private String str3 = "To continue\n\n";
/*      */   
/*  583 */   private String str4 = "To continue\n\n";
/*      */   
/*  585 */   private String str5 = "Entering the new trial solution\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void updateStepHelpPanel()
/*      */   {
/*  594 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/*  595 */     switch (this.step) {
/*      */     case 1: 
/*  597 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str1).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  615 */       break;
/*      */     case 2: 
/*  600 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str2).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  615 */       break;
/*      */     case 3: 
/*  603 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str3).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  615 */       break;
/*      */     case 4: 
/*  606 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str4).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  615 */       break;
/*      */     case 5: 
/*  609 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str5).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  615 */       break;
/*      */     default: 
/*  612 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/*  621 */     String str = "Interactive Gradient Search Procedure\n\n";
/*  622 */     str = String.valueOf(String.valueOf(str)).concat("This procedure (approximately) maximizes a concave polynomial function of two ");
/*  623 */     str = String.valueOf(String.valueOf(str)).concat("variables by using the gradient search procedure presented in Sec. 13.5. ");
/*  624 */     str = String.valueOf(String.valueOf(str)).concat("You will enter the polynomial to maximize and its derivative. You and the ");
/*  625 */     str = String.valueOf(String.valueOf(str)).concat("computer will interactively perform as many iterations as desired by identifying ");
/*  626 */     str = String.valueOf(String.valueOf(str)).concat("the entries in a table like Table 13.2. \n\n");
/*  627 */     str = String.valueOf(String.valueOf(str)).concat("When you finish a problem, you can print out all your work by choosing Print to file under the File ");
/*  628 */     str = String.valueOf(String.valueOf(str)).concat("menu. If you are interrupted before you finish, you can save your work (choose Save under the ");
/*  629 */     str = String.valueOf(String.valueOf(str)).concat("File menu) and return later (choose Open).\n\n");
/*  630 */     str = String.valueOf(String.valueOf(str)).concat("At any step, detailed computer instructions are available (Help menu). To ");
/*  631 */     str = String.valueOf(String.valueOf(str)).concat("undo a mistake, backtrack by pressing the BACK button.");
/*  632 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/*  634 */     this.help_content_procedure.setText(str);
/*  635 */     this.help_content_procedure.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */   private void doBack()
/*      */   {
/*  641 */     String tvs = new String("");String trs = new String();
/*      */     
/*  643 */     if (this.opseq.isEmpty() == false) {
/*  644 */       int last = ((Integer)this.opseq.lastElement()).intValue();
/*  645 */       this.opseq.removeElementAt(this.opseq.size() - 1);
/*      */       
/*  647 */       switch (last)
/*      */       {
/*      */       case 1: 
/*  650 */         this.controller.solver.reset();
/*  651 */         this.state.back();
/*  652 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  654 */         this.transtPanel.removeAll();
/*  655 */         this.transtPanel.add(this.setupPanel, "North");
/*  656 */         this.backbutt.setEnabled(false);
/*  657 */         this.step = 1;
/*      */         
/*  659 */         revalidate();
/*  660 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  739 */         break;
/*      */       case 2: 
/*  664 */         this.controller.solver.reset();
/*  665 */         this.state.back();
/*  666 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  668 */         this.gradModel.fireTableDataChanged();
/*  669 */         this.bottomPanel.removeAll();
/*  670 */         this.bottomPanel.add(this.xtlabel);
/*  671 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/*  672 */         this.bottomPanel.add(this.inputPanel);
/*  673 */         this.step = 2;
/*      */         
/*  675 */         revalidate();
/*  676 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  739 */         break;
/*      */       case 3: 
/*  680 */         this.controller.solver.reset();
/*  681 */         this.state.back();
/*  682 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  684 */         this.gradModel.fireTableDataChanged();
/*  685 */         BuildTFunction();
/*  686 */         this.step = 3;
/*      */         
/*  688 */         revalidate();
/*  689 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  739 */         break;
/*      */       case 4: 
/*  693 */         this.controller.solver.reset();
/*  694 */         this.state.back();
/*  695 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  697 */         this.gradModel.fireTableDataChanged();
/*  698 */         BuildTFunction();
/*  699 */         this.bottomPanel.remove(this.indilabel);
/*  700 */         tvs = "Applying the one-dimensional search procedure, f is maximized at :  t* = ";
/*  701 */         tvs = String.valueOf(String.valueOf(tvs)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.tt[(this.itercount - 1)]))));
/*  702 */         JLabel tvlabel = new JLabel(tvs);
/*  703 */         tvlabel.setAlignmentX(0.5F);
/*  704 */         this.bottomPanel.add(tvlabel);
/*  705 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/*  706 */         this.indilabel.setText("Press the NEXT button to determine the new trial solution.");
/*  707 */         this.bottomPanel.add(this.indilabel);
/*  708 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/*  709 */         this.step = 4;
/*      */         
/*  711 */         revalidate();
/*  712 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  739 */         break;
/*      */       case 5: 
/*  716 */         this.controller.solver.reset();
/*  717 */         this.state.back();
/*  718 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  720 */         this.gradModel.fireTableRowsDeleted(this.itercount, this.itercount);
/*  721 */         this.itercount -= 1;
/*  722 */         BuildNewxPanel();
/*  723 */         trs = new String("Current Trial Solution : (x1, x2) = (");
/*  724 */         trs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(trs))).append(this.decfm.format(this.controller.data.oldx1[(this.itercount - 1)])).append(" , ")));
/*  725 */         trs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(trs))).append(this.decfm.format(this.controller.data.oldx2[(this.itercount - 1)])).append(" )")));
/*  726 */         this.solulabel.setText(trs);
/*  727 */         this.grad1label.setText(" = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.gradx1[(this.itercount - 1)])))));
/*  728 */         this.grad2label.setText(" = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.gradx2[(this.itercount - 1)])))));
/*  729 */         this.step = 5;
/*      */         
/*  731 */         revalidate();
/*  732 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  739 */         break;
/*      */       default: 
/*  735 */         System.out.println("gradient cannot get here");
/*      */         
/*      */ 
/*      */ 
/*  739 */         break; }
/*      */     }
/*      */   }
/*      */   
/*      */   private void doNext() {
/*  744 */     double[] fco = new double[this.nTerm];double[] grad1co = new double[this.nTerm];double[] grad2co = new double[this.nTerm];
/*  745 */     double[] inix = new double[2];double[] av = new double[2];double[] bv = new double[2];
/*  746 */     int[] fx1ex = new int[this.nTerm];int[] fx2ex = new int[this.nTerm];int[] gr1x1ex = new int[this.nTerm];int[] gr1x2ex = new int[this.nTerm];
/*  747 */     int[] gr2x1ex = new int[this.nTerm];int[] gr2x2ex = new int[this.nTerm];
/*      */     
/*      */ 
/*  750 */     Vector par = new Vector();
/*  751 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*  753 */     switch (this.step) {
/*      */     case 1: 
/*  755 */       this.numTerm.setValue(this.nTerm);
/*      */       
/*  757 */       for (int i = 0; i < this.nTerm; i++) {
/*  758 */         fco[i] = this.fxcoef[i].getValue();
/*  759 */         fx1ex[i] = this.fx1exp[i].getValue();
/*  760 */         fx2ex[i] = this.fx2exp[i].getValue();
/*  761 */         grad1co[i] = this.dfx1coef[i].getValue();
/*  762 */         gr1x1ex[i] = this.dfx1_x1exp[i].getValue();
/*  763 */         gr1x2ex[i] = this.dfx1_x2exp[i].getValue();
/*  764 */         grad2co[i] = this.dfx2coef[i].getValue();
/*  765 */         gr2x1ex[i] = this.dfx2_x1exp[i].getValue();
/*  766 */         gr2x2ex[i] = this.dfx2_x2exp[i].getValue();
/*      */       }
/*  768 */       inix[0] = this.inix1fd.getValue();
/*  769 */       inix[1] = this.inix2fd.getValue();
/*      */       
/*  771 */       par.add(new Integer(this.nTerm));
/*  772 */       par.add(fco);
/*  773 */       par.add(fx1ex);
/*  774 */       par.add(fx2ex);
/*  775 */       par.add(grad1co);
/*  776 */       par.add(gr1x1ex);
/*  777 */       par.add(gr1x2ex);
/*  778 */       par.add(grad2co);
/*  779 */       par.add(gr2x1ex);
/*  780 */       par.add(gr2x2ex);
/*  781 */       par.add(inix);
/*      */       
/*  783 */       opr.parameters = par;
/*  784 */       opr.operation_code = 50201;
/*  785 */       this.controller.solver.doWork(opr, this.controller.data);
/*      */       
/*      */ 
/*  788 */       this.state.addStep(opr);
/*      */       
/*  790 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*      */ 
/*  793 */       this.gradModel.fireTableDataChanged();
/*  794 */       BuildUpPanel();
/*  795 */       this.transtPanel.removeAll();
/*  796 */       this.transtPanel.add(this.upPanel, "North");
/*  797 */       this.transtPanel.add(this.scrlPane);
/*  798 */       this.transtPanel.add(this.bottomPanel, "South");
/*  799 */       this.backbutt.setEnabled(true);
/*  800 */       this.step = 2;
/*      */       
/*  802 */       revalidate();
/*  803 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  931 */       break;
/*      */     case 2: 
/*  807 */       av[0] = this.a1fd.getValue();
/*  808 */       bv[0] = this.b1fd.getValue();
/*  809 */       av[1] = this.a2fd.getValue();
/*  810 */       bv[1] = this.b2fd.getValue();
/*      */       
/*  812 */       par.add(new Integer(this.itercount));
/*  813 */       par.add(av);
/*  814 */       par.add(bv);
/*      */       
/*  816 */       opr.parameters = par;
/*  817 */       opr.operation_code = 50202;
/*  818 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  819 */         String err = this.controller.solver.getErrInfo();
/*  820 */         JOptionPane.showMessageDialog(this, err);
/*      */       }
/*      */       else
/*      */       {
/*  824 */         this.state.addStep(opr);
/*      */         
/*  826 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/*  829 */         this.gradModel.fireTableDataChanged();
/*  830 */         BuildTFunction();
/*  831 */         this.step = 3;
/*      */         
/*  833 */         revalidate();
/*  834 */         repaint();
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
/*  931 */       break;
/*      */     case 3: 
/*  838 */       par.add(new Integer(this.itercount));
/*  839 */       opr.parameters = par;
/*  840 */       opr.operation_code = 50203;
/*  841 */       this.controller.solver.doWork(opr, this.controller.data);
/*      */       
/*      */ 
/*  844 */       this.state.addStep(opr);
/*      */       
/*  846 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*      */ 
/*  849 */       this.gradModel.fireTableDataChanged();
/*  850 */       this.bottomPanel.remove(this.indilabel);
/*  851 */       String tvs = "Applying the one-dimensional search procedure, f is maximized at :  t* = ";
/*  852 */       tvs = String.valueOf(String.valueOf(tvs)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.tt[(this.itercount - 1)]))));
/*  853 */       JLabel tvlabel = new JLabel(tvs);
/*  854 */       tvlabel.setAlignmentX(0.5F);
/*  855 */       this.bottomPanel.add(tvlabel);
/*  856 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/*  857 */       this.indilabel.setText("Press the NEXT button to determine the new trial solution.");
/*  858 */       this.bottomPanel.add(this.indilabel);
/*  859 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/*  860 */       this.step = 4;
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
/*  931 */       break;
/*      */     case 4: 
/*  867 */       par.add(new Integer(this.itercount));
/*  868 */       opr.parameters = par;
/*  869 */       opr.operation_code = 50204;
/*  870 */       this.controller.solver.doWork(opr, this.controller.data);
/*      */       
/*      */ 
/*  873 */       this.state.addStep(opr);
/*      */       
/*  875 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*      */ 
/*      */ 
/*  879 */       this.gradModel.fireTableDataChanged();
/*  880 */       BuildNewxPanel();
/*  881 */       this.step = 5;
/*      */       
/*  883 */       revalidate();
/*  884 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  931 */       break;
/*      */     case 5: 
/*  888 */       inix[0] = this.newx1fd.getValue();
/*  889 */       inix[1] = this.newx2fd.getValue();
/*      */       
/*  891 */       par.add(new Integer(this.itercount));
/*  892 */       par.add(inix);
/*  893 */       opr.parameters = par;
/*  894 */       opr.operation_code = 50205;
/*  895 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  896 */         String err = this.controller.solver.getErrInfo();
/*  897 */         JOptionPane.showMessageDialog(this, err);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  902 */         this.state.addStep(opr);
/*      */         
/*  904 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/*  907 */         this.itercount += 1;
/*  908 */         this.gradModel.fireTableRowsInserted(this.itercount - 1, this.itercount - 1);
/*  909 */         String trs = new String("Current Trial Solution : (x1, x2) = (");
/*  910 */         trs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(trs))).append(this.decfm.format(this.controller.data.oldx1[(this.itercount - 1)])).append(" , ")));
/*  911 */         trs = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(trs))).append(this.decfm.format(this.controller.data.oldx2[(this.itercount - 1)])).append(" )")));
/*  912 */         this.solulabel.setText(trs);
/*  913 */         this.grad1label.setText(" = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.gradx1[(this.itercount - 1)])))));
/*  914 */         this.grad2label.setText(" = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.gradx2[(this.itercount - 1)])))));
/*  915 */         this.bottomPanel.removeAll();
/*  916 */         this.xtlabel.setText("For the column , X'+t[grad f(X')] , express xj as a function of t.");
/*  917 */         this.bottomPanel.add(this.xtlabel);
/*  918 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/*  919 */         this.bottomPanel.add(this.inputPanel);
/*  920 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/*  921 */         this.step = 2;
/*      */         
/*  923 */         revalidate();
/*  924 */         repaint();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  931 */       break;
/*      */     default: 
/*  927 */       System.out.println("gradient cannot get here");
/*      */       
/*      */ 
/*      */ 
/*  931 */       break;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void LoadFile(ObjectInputStream in)
/*      */   {
/*  941 */     int[] para = new int[3];
/*      */     
/*      */     try
/*      */     {
/*  945 */       this.state = ((IORState)in.readObject());
/*  946 */       para = (int[])in.readObject();
/*  947 */       this.opseq = ((Vector)in.readObject());
/*  948 */       this.state.setSolverStepVector();
/*  949 */       in.close();
/*      */     }
/*      */     catch (Exception e1) {
/*  952 */       e1.printStackTrace();
/*  953 */       System.out.println("Open fails");
/*      */     }
/*  955 */     this.step = para[0];
/*  956 */     this.itercount = para[1];
/*  957 */     this.nTerm = para[2];
/*  958 */     this.numTerm.setValue(this.nTerm);
/*      */     
/*  960 */     if (this.step >= 1) {
/*  961 */       BuildSetupPanel(this.nTerm);
/*  962 */       for (int i = 0; i < this.nTerm; i++) {
/*  963 */         this.fxcoef[i].setValue(this.controller.data.fx1x2_coef[i]);
/*  964 */         this.fx1exp[i].setValue(this.controller.data.fx_x1exp[i]);
/*  965 */         this.fx2exp[i].setValue(this.controller.data.fx_x2exp[i]);
/*  966 */         this.dfx1coef[i].setValue(this.controller.data.dfx1_coef[i]);
/*  967 */         this.dfx1_x1exp[i].setValue(this.controller.data.dfx1_x1exp[i]);
/*  968 */         this.dfx1_x2exp[i].setValue(this.controller.data.dfx1_x2exp[i]);
/*  969 */         this.dfx2coef[i].setValue(this.controller.data.dfx2_coef[i]);
/*  970 */         this.dfx2_x1exp[i].setValue(this.controller.data.dfx2_x1exp[i]);
/*  971 */         this.dfx2_x2exp[i].setValue(this.controller.data.dfx2_x2exp[i]);
/*      */       }
/*  973 */       this.inix1fd.setValue(this.controller.data.oldx1[0]);
/*  974 */       this.inix2fd.setValue(this.controller.data.oldx2[0]);
/*      */     }
/*  976 */     if (this.step >= 2) {
/*  977 */       this.gradModel.fireTableDataChanged();
/*  978 */       BuildUpPanel();
/*  979 */       this.transtPanel.removeAll();
/*  980 */       this.transtPanel.add(this.upPanel, "North");
/*  981 */       this.transtPanel.add(this.scrlPane);
/*  982 */       this.transtPanel.add(this.bottomPanel, "South");
/*  983 */       this.backbutt.setEnabled(true);
/*      */     }
/*  985 */     if (this.step >= 3) {
/*  986 */       this.gradModel.fireTableDataChanged();
/*  987 */       BuildTFunction();
/*      */     }
/*  989 */     if (this.step >= 4) {
/*  990 */       this.gradModel.fireTableDataChanged();
/*  991 */       String tvs = "Applying the one-dimensional search procedure, f is maximized at :  t* = ";
/*  992 */       tvs = String.valueOf(String.valueOf(tvs)).concat(String.valueOf(String.valueOf(this.decfm.format(this.controller.data.tt[(this.itercount - 1)]))));
/*  993 */       JLabel tvlabel = new JLabel(tvs);
/*  994 */       tvlabel.setAlignmentX(0.5F);
/*  995 */       this.bottomPanel.removeAll();
/*  996 */       this.bottomPanel.add(this.htexPanel);
/*  997 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/*  998 */       this.bottomPanel.add(tvlabel);
/*  999 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/* 1000 */       this.indilabel.setText("Press the NEXT button to determine the new trial solution.");
/* 1001 */       this.bottomPanel.add(this.indilabel);
/* 1002 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/*      */     }
/* 1004 */     if (this.step >= 5) {
/* 1005 */       this.gradModel.fireTableDataChanged();
/* 1006 */       BuildNewxPanel();
/*      */     }
/* 1008 */     if (this.step >= 6) {
/* 1009 */       System.out.println("NL Interactive Gradient has no this step.");
/*      */     }
/* 1011 */     this.table.clearSelection();
/* 1012 */     revalidate();
/* 1013 */     repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFile(ObjectOutputStream out)
/*      */   {
/* 1023 */     int[] interpara = new int[3];
/*      */     
/* 1025 */     interpara[0] = this.step;
/* 1026 */     interpara[1] = this.itercount;
/* 1027 */     interpara[2] = this.nTerm;
/*      */     try {
/* 1029 */       out.writeObject(this.state);
/* 1030 */       out.writeObject(interpara);
/* 1031 */       out.writeObject(this.opseq);
/* 1032 */       out.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1035 */       e1.printStackTrace();
/* 1036 */       System.out.println("Save fails");
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
/* 1047 */     double[] fco = new double[this.nTerm];double[] grad1co = new double[this.nTerm];double[] grad2co = new double[this.nTerm];
/* 1048 */     double[] inix = new double[2];double[] av = new double[2];double[] bv = new double[2];
/* 1049 */     int[] fx1ex = new int[this.nTerm];int[] fx2ex = new int[this.nTerm];int[] gr1x1ex = new int[this.nTerm];int[] gr1x2ex = new int[this.nTerm];
/* 1050 */     int[] gr2x1ex = new int[this.nTerm];int[] gr2x2ex = new int[this.nTerm];
/*      */     
/* 1052 */     Vector par = new Vector();
/* 1053 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/* 1055 */     if (this.step == 1) {
/* 1056 */       for (int i = 0; i < this.nTerm; i++) {
/* 1057 */         fco[i] = this.fxcoef[i].getValue();
/* 1058 */         fx1ex[i] = this.fx1exp[i].getValue();
/* 1059 */         fx2ex[i] = this.fx2exp[i].getValue();
/* 1060 */         grad1co[i] = this.dfx1coef[i].getValue();
/* 1061 */         gr1x1ex[i] = this.dfx1_x1exp[i].getValue();
/* 1062 */         gr1x2ex[i] = this.dfx1_x2exp[i].getValue();
/* 1063 */         grad2co[i] = this.dfx2coef[i].getValue();
/* 1064 */         gr2x1ex[i] = this.dfx2_x1exp[i].getValue();
/* 1065 */         gr2x2ex[i] = this.dfx2_x2exp[i].getValue();
/*      */       }
/* 1067 */       inix[0] = this.inix1fd.getValue();
/* 1068 */       inix[1] = this.inix2fd.getValue();
/*      */       
/* 1070 */       par.add(new Integer(this.nTerm));
/* 1071 */       par.add(fco);
/* 1072 */       par.add(fx1ex);
/* 1073 */       par.add(fx2ex);
/* 1074 */       par.add(grad1co);
/* 1075 */       par.add(gr1x1ex);
/* 1076 */       par.add(gr1x2ex);
/* 1077 */       par.add(grad2co);
/* 1078 */       par.add(gr2x1ex);
/* 1079 */       par.add(gr2x2ex);
/* 1080 */       par.add(inix);
/*      */       
/* 1082 */       opr.parameters = par;
/* 1083 */       opr.operation_code = 50201;
/* 1084 */       this.controller.solver.doWork(opr, this.controller.data);
/*      */     } }
/*      */   
/*      */   class IORGradTableModel extends AbstractTableModel { private int i;
/*      */     private int j;
/*      */     
/*      */     IORGradTableModel() {}
/*      */     
/* 1092 */     public int getColumnCount() { return 6; }
/*      */     
/*      */     public int getRowCount()
/*      */     {
/* 1096 */       return IORNLGradientPanel.this.itercount;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col) {
/* 1100 */       switch (col) {
/*      */       case 0: 
/* 1102 */         return "Iter.";
/*      */       case 1: 
/* 1104 */         return "X'";
/*      */       case 2: 
/* 1106 */         return "grad f(X')";
/*      */       case 3: 
/* 1108 */         return "X'+t[grad f(X')]";
/*      */       case 4: 
/* 1110 */         return "t*";
/*      */       case 5: 
/* 1112 */         return "X'+t*[grad f(X')]";
/*      */       }
/* 1114 */       return "err";
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col)
/*      */     {
/* 1119 */       String str = new String();
/* 1120 */       DecimalFormat decfm = new DecimalFormat("#.###");
/* 1121 */       switch (col) {
/*      */       case 0: 
/* 1123 */         return new Integer(row + 1).toString();
/*      */       case 1: 
/* 1125 */         str = String.valueOf(String.valueOf(new StringBuffer("( ").append(decfm.format(IORNLGradientPanel.this.controller.data.oldx1[row])).append(" , ").append(decfm.format(IORNLGradientPanel.this.controller.data.oldx2[row])).append(" )")));
/* 1126 */         return str;
/*      */       case 2: 
/* 1128 */         str = String.valueOf(String.valueOf(new StringBuffer("( ").append(decfm.format(IORNLGradientPanel.this.controller.data.gradx1[row])).append(" , ").append(decfm.format(IORNLGradientPanel.this.controller.data.gradx2[row])).append(" )")));
/* 1129 */         return str;
/*      */       case 3: 
/* 1131 */         String lstr = decfm.format(IORNLGradientPanel.this.controller.data.a1[row]);
/* 1132 */         if (IORNLGradientPanel.this.controller.data.b1[row] > -0.01D) {
/* 1133 */           lstr = String.valueOf(String.valueOf(lstr)).concat("+");
/*      */         }
/* 1135 */         lstr = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(lstr))).append(decfm.format(IORNLGradientPanel.this.controller.data.b1[row])).append(" t")));
/*      */         
/* 1137 */         String rstr = decfm.format(IORNLGradientPanel.this.controller.data.a2[row]);
/* 1138 */         if (IORNLGradientPanel.this.controller.data.b2[row] > -0.01D) {
/* 1139 */           rstr = String.valueOf(String.valueOf(rstr)).concat("+");
/*      */         }
/* 1141 */         rstr = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(rstr))).append(decfm.format(IORNLGradientPanel.this.controller.data.b2[row])).append(" t")));
/* 1142 */         str = String.valueOf(String.valueOf(new StringBuffer("( ").append(lstr).append(" , ").append(rstr).append(" )")));
/* 1143 */         return str;
/*      */       case 4: 
/* 1145 */         return decfm.format(IORNLGradientPanel.this.controller.data.tt[row]);
/*      */       case 5: 
/* 1147 */         str = String.valueOf(String.valueOf(new StringBuffer("( ").append(decfm.format(IORNLGradientPanel.this.controller.data.newx1[row])).append(" , ").append(decfm.format(IORNLGradientPanel.this.controller.data.newx2[row])).append(" )")));
/* 1148 */         return str;
/*      */       }
/* 1150 */       return "err";
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c)
/*      */     {
/* 1155 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1161 */       return false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNLGradientPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */