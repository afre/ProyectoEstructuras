/*      */ import java.io.PrintStream;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ORLPSolver
/*      */   extends ORSolverBase
/*      */ {
/*   30 */   public graphData gdata = new graphData();
/*   31 */   protected ORSolverBase.SimplexModelType SimplexModel = new ORSolverBase.SimplexModelType(this);
/*   32 */   protected ORSolverBase.LPSolutionType LPSolution = new ORSolverBase.LPSolutionType(this);
/*      */   protected ORSolverBase.SimplexTableauType CurrentSimplexTableau;
/*   34 */   protected double[][] MSMQ = new double[11][11];
/*      */   
/*      */ 
/*   37 */   private ORSolverBase.SimplexTableauType FirstSimplexTableau = new ORSolverBase.SimplexTableauType(this);
/*   38 */   private ORSolverBase.SimplexTableauType FirstSimplexTableau_bak = null;
/*   39 */   private ORSolverBase.SimplexTableauType CurrentSimplexTableau_bak = null;
/*   40 */   private ORSolverBase.SimplexTableauType FirstSimplexTableau_bak_for_Setup = null;
/*   41 */   private ORSolverBase.SimplexTableauType CurrentSimplexTableau_bak_for_Setup = null;
/*   42 */   private double[] XBasic = new double[51];
/*   43 */   private double[] CBasic = new double[51];
/*   44 */   private double[] CMBasic = new double[51];
/*   45 */   private double[][] BInverse = new double[51][51];
/*      */   
/*   47 */   private int[] ComplementaryVariable = new int[''];
/*      */   
/*      */   private ORSolverBase.InteriorPointType IP;
/*      */   private double Alpha;
/*      */   private double AlphaUsed;
/*   52 */   private boolean is_big_M = true;
/*      */   
/*      */   private ORSolverBase.SimplexSensitivityType CurrentSimplexSensitivity;
/*      */   protected int NumOriginalConstraints1;
/*      */   protected int NumOriginalConstraints2;
/*      */   protected int TempObjective;
/*      */   protected int Phase1Result;
/*   59 */   protected boolean isFirstSolverStep = true;
/*      */   
/*      */ 
/*   62 */   private boolean SensitivityConvertToProperFormPerformed = false;
/*   63 */   private boolean SensitivitySimplexPerformed = false;
/*   64 */   private boolean SensitivityDualSimplexPerformed = false;
/*   65 */   private ORSolverBase.SimplexTableauType CurrentSimplexTableau_bak_for_sen_print1 = null;
/*   66 */   private ORSolverBase.SimplexTableauType CurrentSimplexTableau_bak_for_sen_print2 = null;
/*      */   
/*      */   protected int NumBinaryVariables;
/*      */   protected int NumIntegerVariables;
/*      */   protected int NumRealVariables;
/*   71 */   protected ORSolverBase.PolynomialType ThePolynomial = new ORSolverBase.PolynomialType(this);
/*      */   
/*      */ 
/*   74 */   private Vector variableValeV = null;
/*   75 */   private Vector SlackV = null;
/*   76 */   private Vector ShadowPriceV = null;
/*      */   
/*   78 */   private double[] minArr = null;
/*   79 */   private double[] maxArr = null;
/*   80 */   private double[] minRHSArr = null;
/*   81 */   private double[] maxRHSArr = null;
/*   82 */   private double[] coefficientsArr = null;
/*   83 */   private double[] RHSCurrentValueArr = null;
/*   84 */   private String outputInfo = null;
/*   85 */   private DecimalFormat decfm = new DecimalFormat();
/*      */   
/*      */   private void DoSimplexIP() {
/*   88 */     InitializeSimplexModel();
/*   89 */     my_InitSimplexModel();
/*   90 */     this.decfm.setGroupingUsed(false);
/*   91 */     this.decfm.setMaximumFractionDigits(2);
/*   92 */     SetupIP();
/*   93 */     my_SetupIP();
/*   94 */     if (!CheckInitIPSolution()) {
/*   95 */       System.out.println("invalid initial IP solution.");
/*   96 */       return;
/*      */     }
/*   98 */     for (int i = 1; i <= 15; i++) {
/*   99 */       IterateIP();
/*      */     }
/*  101 */     OutputIPResult();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean autoSolve(Vector p, SensData data)
/*      */   {
/*  109 */     this.CommandNumber = 2;
/*  110 */     InitializeSimplexModel();
/*      */     
/*      */ 
/*      */ 
/*  114 */     this.SimplexModel.NumVariables = ((Integer)p.elementAt(0)).intValue();
/*      */     
/*  116 */     this.SimplexModel.NumConstraints = ((Integer)p.elementAt(1)).intValue();
/*      */     
/*  118 */     boolean isMax = ((Boolean)p.elementAt(2)).booleanValue();
/*  119 */     this.SimplexModel.Objective = (isMax ? 0 : 1);
/*      */     
/*  121 */     double[] c = (double[])p.elementAt(3);
/*  122 */     for (int i = 0; i < c.length; i++) {
/*  123 */       this.SimplexModel.ObjectiveFunction[(i + 1)] = c[i];
/*      */     }
/*  125 */     double[][] c2 = (double[][])p.elementAt(4);
/*  126 */     for (i = 0; i < c2.length; i++) {
/*  127 */       for (int j = 0; j < c2[i].length - 1; j++)
/*  128 */         this.SimplexModel.A[(i + 1)][(j + 1)] = c2[i][(j + 1)];
/*  129 */       this.SimplexModel.RightHandSide[(i + 1)] = c2[i][0];
/*      */     }
/*      */     
/*      */ 
/*  133 */     int[] o = (int[])p.elementAt(5);
/*  134 */     for (i = 0; i < o.length; i++) {
/*  135 */       this.SimplexModel.Constraint[(i + 1)] = o[i];
/*      */     }
/*      */     
/*  138 */     boolean[] b = (boolean[])p.elementAt(6);
/*  139 */     for (i = 0; i < b.length; i++) {
/*  140 */       this.SimplexModel.Nonnegativity[(i + 1)] = b[i];
/*      */     }
/*  142 */     System.out.println("RHS of the functional constrains:");
/*  143 */     double[] rhs = (double[])p.elementAt(7);
/*  144 */     for (i = 0; i < rhs.length; i++) {
/*  145 */       this.SimplexModel.LowerBound[(i + 1)] = rhs[i];
/*      */     }
/*  147 */     InitializeFirstSimplexTableau();
/*      */     
/*  149 */     RevisedSimplexMethod(true);
/*      */     
/*  151 */     getData(data);
/*      */     
/*  153 */     return true;
/*      */   }
/*      */   
/*      */   public void getData(SensData d)
/*      */   {
/*  158 */     d.unbounded = this.CurrentSimplexTableau.Unbounded;
/*  159 */     if (!this.CurrentSimplexTableau.Unbounded)
/*      */     {
/*  161 */       for (int i = 0; i < d.num_constraints; i++) {
/*  162 */         d.shadow_price[i] = ((float)this.LPSolution.ShadowPrice[(i + 1)]);
/*  163 */         d.slack_surplus[i] = ((float)this.LPSolution.Slack[(i + 1)]);
/*  164 */         d.cons_value[i] = ((float)this.SimplexModel.RightHandSide[(i + 1)]);
/*  165 */         d.range_rightside[i][0] = ((float)this.LPSolution.MinRHS[(i + 1)]);
/*  166 */         d.range_rightside[i][1] = ((float)this.LPSolution.MaxRHS[(i + 1)]);
/*      */       }
/*  168 */       for (i = 0; i < d.num_variable; i++) {
/*  169 */         d.range_objcoef[i][0] = ((float)this.LPSolution.MinObjective[(i + 1)]);
/*  170 */         d.range_objcoef[i][1] = ((float)this.LPSolution.MaxObjective[(i + 1)]);
/*  171 */         d.solution[i] = ((float)this.LPSolution.Solution[(i + 1)]);
/*      */       }
/*  173 */       d.z = ((float)this.CurrentSimplexTableau.Z);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void RevisedSimplexMethod(boolean SensitivityAnalysis)
/*      */   {
/*  182 */     InitializeAutoSolve();
/*  183 */     InitializeFirstSimplexTableau();
/*  184 */     if ((this.ApplicationNumber == 6) && (this.CommandNumber == 6))
/*      */     {
/*  186 */       SimplexDoSetupModifiedSimplex();
/*      */     } else
/*  188 */       SimplexDoConvertToEqualityForm();
/*  189 */     this.FirstSimplexTableau = ((ORSolverBase.SimplexTableauType)this.CurrentSimplexTableau.clone());
/*      */     
/*  191 */     SimplexDoPutSlacksInTheirProperPlace();
/*  192 */     SimplexDoConvertToProperForm();
/*  193 */     this.FirstSimplexTableau = ((ORSolverBase.SimplexTableauType)this.CurrentSimplexTableau.clone());
/*  194 */     InitializeABasicVariable();
/*      */     
/*  196 */     for (int Phase = 1; Phase <= 2; Phase++) {
/*  197 */       int It = 0;
/*  198 */       if (((Phase == 1) && (this.CurrentSimplexTableau.NumBasicArtificials > 0)) || ((Phase == 2) && (this.CurrentSimplexTableau.Feasible) && ((this.ApplicationNumber != 6) || (this.CommandNumber != 6))))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  204 */         if (!this.CurrentSimplexTableau.Unbounded) {
/*      */           do {
/*  206 */             if (Phase == 2) {
/*  207 */               SimplexDoFindEBV2();
/*      */             } else
/*  209 */               SimplexDoFindEBV1();
/*  210 */             It++;
/*  211 */             if (!this.CurrentSimplexTableau.Optimal) {
/*  212 */               SimplexDoCalcTMinimal();
/*  213 */               SimplexDoCalcRHSMinimal();
/*  214 */               SimplexDoFindLBVE();
/*  215 */               if (!this.CurrentSimplexTableau.Unbounded) {
/*  216 */                 SimplexDoUpdateBasicVariables();
/*  217 */                 SimplexDoCalcBInverse();
/*  218 */                 SimplexDoCalcCBasic();
/*  219 */                 if (Phase == 2) {
/*  220 */                   SimplexDoCalcObjectiveFunctionMinimal2();
/*      */                 } else {
/*  222 */                   SimplexDoCalcObjectiveFunctionMinimal();
/*      */                 }
/*      */               }
/*      */             }
/*  226 */           } while ((!this.CurrentSimplexTableau.Optimal) && (!this.CurrentSimplexTableau.Unbounded) && (It != 500));
/*      */         }
/*      */         
/*  229 */         if ((Phase == 1) && (!this.CurrentSimplexTableau.Unbounded)) {
/*  230 */           SimplexPrepareForPhase2();
/*  231 */           if (this.CurrentSimplexTableau.Feasible) {
/*  232 */             SimplexDoConvertToProperForm();
/*  233 */             SimplexDoCalcObjectiveFunctionMinimal2();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  238 */     if (!this.CurrentSimplexTableau.Unbounded) {
/*  239 */       SimplexDoCalcT();
/*  240 */       SimplexDoCalcRHS();
/*  241 */       SimplexDoCalcObjectiveFunction();
/*  242 */       SimplexDoCalcXBasic();
/*  243 */       SimplexDoCalcZ();
/*      */       
/*  245 */       FillSolution(this.CurrentSimplexTableau, this.LPSolution.Solution);
/*  246 */       this.CurrentSimplexTableau.Feasible = SolutionFeasible(this.SimplexModel, this.LPSolution.Solution);
/*      */       
/*  248 */       if (this.CurrentSimplexTableau.Feasible)
/*  249 */         this.CurrentSimplexTableau.ZM = 0.0D;
/*  250 */       if (SensitivityAnalysis) {
/*  251 */         FillSensitivityAnalysisInfo();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void InitializeSimplexModel()
/*      */   {
/*  258 */     this.NumOriginalConstraints1 = 3;
/*  259 */     this.NumOriginalConstraints2 = 3;
/*  260 */     this.TempObjective = 0;
/*      */     
/*  262 */     this.SimplexModel.NumVariables = 2;
/*  263 */     this.SimplexModel.NumConstraints = 3;
/*  264 */     this.SimplexModel.Objective = 0;
/*  265 */     for (int i = 1; i <= 50; i++) {
/*  266 */       this.SimplexModel.ObjectiveFunction[i] = 0.0D;
/*  267 */       this.SimplexModel.ObjectiveFunctionM[i] = 0.0D;
/*  268 */       this.SimplexModel.Nonnegativity[i] = true;
/*  269 */       this.SimplexModel.LowerBound[i] = 0.0D;
/*  270 */       this.SimplexModel.UpperBound[i] = 0.0D;
/*  271 */       this.SimplexModel.AnUpperBound[i] = false;
/*      */     }
/*  273 */     for (i = 1; i <= 50; i++) {
/*  274 */       for (int j = 1; j <= 50; j++)
/*  275 */         this.SimplexModel.A[i][j] = 0.0D;
/*  276 */       this.SimplexModel.Constraint[i] = 0;
/*  277 */       this.SimplexModel.RightHandSide[i] = 0.0D;
/*      */     }
/*      */   }
/*      */   
/*      */   private void my_InitSimplexModel() {
/*  282 */     this.SimplexModel.NumConstraints = 3;
/*  283 */     this.SimplexModel.NumVariables = 2;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void InitializeAutoSolve()
/*      */   {
/*  308 */     for (int j = 1; j <= 50; j++) {
/*  309 */       this.XBasic[j] = 0.0D;
/*  310 */       this.CBasic[j] = 0.0D;
/*  311 */       this.CMBasic[j] = 0.0D;
/*      */     }
/*  313 */     for (int i = 1; i <= 50; i++) {
/*  314 */       for (j = 1; j <= 50; j++) {
/*  315 */         if (i == j) {
/*  316 */           this.BInverse[i][j] = 1.0D;
/*      */         } else {
/*  318 */           this.BInverse[i][j] = 0.0D;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void InitializeFirstSimplexTableau()
/*      */   {
/*  326 */     this.Phase1Result = 0;
/*  327 */     for (int i = 1; i <= this.SimplexModel.NumConstraints + 1; i++)
/*  328 */       this.FirstSimplexTableau.BasicVariable[i] = 1.0D;
/*  329 */     for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/*  330 */       this.FirstSimplexTableau.LowerBound[j] = this.SimplexModel.LowerBound[j];
/*  331 */       this.FirstSimplexTableau.UpperBound[j] = this.SimplexModel.LowerBound[j];
/*  332 */       this.FirstSimplexTableau.AnUpperBound[j] = this.SimplexModel.AnUpperBound[j];
/*  333 */       this.FirstSimplexTableau.Reversed[j] = false;
/*  334 */       this.FirstSimplexTableau.Nonnegativity[j] = this.SimplexModel.Nonnegativity[j];
/*  335 */       this.FirstSimplexTableau.ObjectiveFunction[j] = (-this.SimplexModel.ObjectiveFunction[j]);
/*      */       
/*  337 */       this.FirstSimplexTableau.ObjectiveFunctionM[j] = (-this.SimplexModel.ObjectiveFunctionM[j]); }
/*      */     int Max;
/*      */     int Max;
/*  340 */     if ((this.ApplicationNumber == 6) && (this.CommandNumber == 6))
/*      */     {
/*  342 */       Max = 3 * this.SimplexModel.NumVariables + 2 * this.SimplexModel.NumConstraints;
/*      */     }
/*      */     else
/*  345 */       Max = 4 * this.SimplexModel.NumVariables;
/*  346 */     for (j = this.SimplexModel.NumVariables + 1; j <= Max; j++) {
/*  347 */       this.FirstSimplexTableau.LowerBound[j] = 0.0D;
/*  348 */       this.FirstSimplexTableau.UpperBound[j] = 0.0D;
/*  349 */       this.FirstSimplexTableau.AnUpperBound[j] = false;
/*  350 */       this.FirstSimplexTableau.Reversed[j] = false;
/*  351 */       this.FirstSimplexTableau.Nonnegativity[j] = true;
/*  352 */       this.FirstSimplexTableau.ObjectiveFunction[j] = 0.0D;
/*  353 */       this.FirstSimplexTableau.ObjectiveFunctionM[j] = 0.0D;
/*      */     }
/*  355 */     for (i = 1; i <= Max; i++)
/*  356 */       this.FirstSimplexTableau.AnArtificial[i] = false;
/*  357 */     this.FirstSimplexTableau.Z = 0.0D;
/*  358 */     this.FirstSimplexTableau.ZM = 0.0D;
/*  359 */     int Max2; int Max2; if ((this.ApplicationNumber == 6) && (this.CommandNumber == 6))
/*      */     {
/*  361 */       Max2 = 2 * this.SimplexModel.NumConstraints + this.SimplexModel.NumVariables;
/*      */     } else
/*  363 */       Max2 = this.SimplexModel.NumConstraints + 1;
/*  364 */     for (i = 1; i <= Max2; i++) {
/*  365 */       for (j = 1; j <= this.SimplexModel.NumVariables; j++)
/*  366 */         this.FirstSimplexTableau.T[i][j] = this.SimplexModel.A[i][j];
/*  367 */       for (j = this.SimplexModel.NumVariables + 1; j <= Max; j++)
/*  368 */         this.FirstSimplexTableau.T[i][j] = 0.0D;
/*  369 */       this.FirstSimplexTableau.Multiple[i] = 0.0D;
/*      */     }
/*  371 */     this.FirstSimplexTableau.Multiple[0] = 0.0D;
/*  372 */     this.FirstSimplexTableau.MultipleM = 0.0D;
/*  373 */     arraycopy1(this.SimplexModel.RightHandSide, this.FirstSimplexTableau.RightHandSide);
/*      */     
/*  375 */     this.FirstSimplexTableau.LeavingBasicVariableEquation = 0;
/*  376 */     this.FirstSimplexTableau.EnteringBasicVariable = 0;
/*  377 */     this.FirstSimplexTableau.NumConstraints = this.SimplexModel.NumConstraints;
/*  378 */     this.FirstSimplexTableau.NumVariables = this.SimplexModel.NumVariables;
/*  379 */     this.FirstSimplexTableau.ContainsArtificials = false;
/*  380 */     arraycopy1(this.SimplexModel.Constraint, this.FirstSimplexTableau.Constraint);
/*  381 */     this.FirstSimplexTableau.Objective = this.SimplexModel.Objective;
/*  382 */     this.FirstSimplexTableau.ContainsConstant = false;
/*  383 */     this.FirstSimplexTableau.Constant = 0.0D;
/*  384 */     this.FirstSimplexTableau.VariableWithConstant = 0;
/*  385 */     this.FirstSimplexTableau.Optimal = false;
/*  386 */     this.FirstSimplexTableau.Feasible = true;
/*  387 */     this.FirstSimplexTableau.Unbounded = false;
/*      */     
/*  389 */     this.CurrentSimplexTableau = ((ORSolverBase.SimplexTableauType)this.FirstSimplexTableau.clone());
/*      */   }
/*      */   
/*      */   private void SimplexDoSetupModifiedSimplex()
/*      */   {
/*  394 */     int[] y = new int[41];
/*  395 */     int[] u = new int[41];
/*  396 */     int[] v = new int[41];
/*      */     
/*  398 */     for (int i = 1; i <= 40; i++) {
/*  399 */       y[i] = 0;
/*  400 */       u[i] = 0;
/*  401 */       v[i] = 0;
/*      */     }
/*      */     
/*  404 */     if (this.SimplexModel.Objective == 0) {
/*  405 */       for (i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/*  406 */         this.CurrentSimplexTableau.ObjectiveFunction[i] = (-this.CurrentSimplexTableau.ObjectiveFunction[i]);
/*      */       }
/*      */     }
/*  409 */     int m = this.CurrentSimplexTableau.NumConstraints;
/*  410 */     for (i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/*  411 */       if (this.CurrentSimplexTableau.Constraint[i] == 2) {
/*  412 */         for (int k = 1; k <= this.CurrentSimplexTableau.NumVariables; k++) {
/*  413 */           this.CurrentSimplexTableau.T[i][k] = (-this.CurrentSimplexTableau.T[i][k]);
/*      */         }
/*  415 */         this.CurrentSimplexTableau.Constraint[i] = 0;
/*  416 */         this.CurrentSimplexTableau.RightHandSide[i] = (-this.CurrentSimplexTableau.RightHandSide[i]);
/*      */ 
/*      */       }
/*  419 */       else if (this.CurrentSimplexTableau.Constraint[i] == 1) {
/*  420 */         m += 1;
/*  421 */         this.CurrentSimplexTableau.Constraint[i] = 0;
/*  422 */         for (int k = 1; k <= this.CurrentSimplexTableau.NumVariables; k++) {
/*  423 */           this.CurrentSimplexTableau.T[m][k] = (-this.CurrentSimplexTableau.T[i][k]);
/*      */         }
/*  425 */         this.CurrentSimplexTableau.Constraint[m] = 0;
/*  426 */         this.CurrentSimplexTableau.RightHandSide[m] = (-this.CurrentSimplexTableau.RightHandSide[i]);
/*      */       }
/*      */     }
/*      */     
/*  430 */     this.CurrentSimplexTableau.NumConstraints = m;
/*      */     
/*  432 */     int n = this.CurrentSimplexTableau.NumVariables;
/*  433 */     for (i = 1; i <= m; i++) {
/*  434 */       this.CurrentSimplexTableau.NumVariables += 1;
/*      */       
/*  436 */       this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*  437 */       this.CurrentSimplexTableau.Constraint[i] = 1;
/*  438 */       v[i] = this.CurrentSimplexTableau.NumVariables;
/*      */     }
/*      */     
/*  441 */     for (i = 1; i <= m; i++)
/*  442 */       u[i] = (this.CurrentSimplexTableau.NumVariables + i);
/*  443 */     this.CurrentSimplexTableau.NumVariables += m;
/*      */     
/*  445 */     for (i = 1; i <= n; i++) {
/*  446 */       this.CurrentSimplexTableau.NumConstraints += 1;
/*      */       
/*  448 */       for (int k = 1; k <= n; k++) {
/*  449 */         this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.NumConstraints][k] = this.MSMQ[i][k];
/*      */       }
/*  451 */       for (k = 1; k <= m; k++) {
/*  452 */         this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.NumConstraints][u[k]] = this.CurrentSimplexTableau.T[k][i];
/*      */       }
/*  454 */       this.CurrentSimplexTableau.NumVariables += 1;
/*      */       
/*  456 */       this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.NumConstraints][this.CurrentSimplexTableau.NumVariables] = -1.0D;
/*      */       
/*  458 */       y[i] = this.CurrentSimplexTableau.NumVariables;
/*  459 */       this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.NumConstraints] = this.CurrentSimplexTableau.ObjectiveFunction[i];
/*      */     }
/*      */     
/*      */ 
/*  463 */     for (i = 1; i <= m; i++) {
/*  464 */       if (this.CurrentSimplexTableau.RightHandSide[i] >= 0) {
/*  465 */         this.CurrentSimplexTableau.BasicVariable[i] = v[i];
/*      */       }
/*      */       else {
/*  468 */         for (int k = 1; k <= this.CurrentSimplexTableau.NumVariables; k++) {
/*  469 */           this.CurrentSimplexTableau.T[i][k] = (-this.CurrentSimplexTableau.T[i][k]);
/*      */         }
/*  471 */         this.CurrentSimplexTableau.RightHandSide[i] = (-this.CurrentSimplexTableau.RightHandSide[i]);
/*      */         
/*  473 */         this.CurrentSimplexTableau.NumVariables += 1;
/*      */         
/*  475 */         this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */         
/*  477 */         this.CurrentSimplexTableau.AnArtificial[this.CurrentSimplexTableau.NumVariables] = true;
/*      */         
/*  479 */         this.CurrentSimplexTableau.ContainsArtificials = true;
/*  480 */         this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */         
/*  482 */         this.CurrentSimplexTableau.BasicVariable[i] = this.CurrentSimplexTableau.NumVariables;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  487 */     for (i = m + 1; i <= m + n; i++) {
/*  488 */       if (this.CurrentSimplexTableau.RightHandSide[i] >= 0) {
/*  489 */         this.CurrentSimplexTableau.NumVariables += 1;
/*      */         
/*  491 */         this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */         
/*  493 */         this.CurrentSimplexTableau.AnArtificial[this.CurrentSimplexTableau.NumVariables] = true;
/*      */         
/*  495 */         this.CurrentSimplexTableau.ContainsArtificials = true;
/*  496 */         this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */         
/*  498 */         this.CurrentSimplexTableau.BasicVariable[i] = this.CurrentSimplexTableau.NumVariables;
/*      */       }
/*      */       else
/*      */       {
/*  502 */         for (int k = 1; k <= this.CurrentSimplexTableau.NumVariables; k++) {
/*  503 */           this.CurrentSimplexTableau.T[i][k] = (-this.CurrentSimplexTableau.T[i][k]);
/*      */         }
/*  505 */         this.CurrentSimplexTableau.RightHandSide[i] = (-this.CurrentSimplexTableau.RightHandSide[i]);
/*      */         
/*  507 */         this.CurrentSimplexTableau.BasicVariable[i] = y[(i - m)];
/*      */       }
/*      */     }
/*      */     
/*  511 */     for (i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/*  512 */       this.ComplementaryVariable[i] = 0;
/*  513 */       this.CurrentSimplexTableau.ObjectiveFunction[i] = 0.0D;
/*      */     }
/*  515 */     for (i = 1; i <= n; i++) {
/*  516 */       this.ComplementaryVariable[i] = y[i];
/*  517 */       this.ComplementaryVariable[y[i]] = i;
/*      */     }
/*  519 */     for (i = 1; i <= m; i++) {
/*  520 */       this.ComplementaryVariable[u[i]] = v[i];
/*  521 */       this.ComplementaryVariable[v[i]] = u[i];
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void SimplexDoConvertToEqualityForm()
/*      */   {
/*  528 */     int Varbl = 1;
/*  529 */     int Varbl2 = 1;
/*      */     do {
/*  531 */       this.CurrentSimplexTableau.VariableLocation[Varbl2] = Varbl;
/*  532 */       if (this.CurrentSimplexTableau.Nonnegativity[Varbl] == 0) {
/*  533 */         this.CurrentSimplexTableau.NumVariables += 1;
/*      */         
/*  535 */         for (int Varbl3 = this.CurrentSimplexTableau.NumVariables; 
/*  536 */             Varbl3 >= Varbl + 2; Varbl3--) {
/*  537 */           this.CurrentSimplexTableau.ObjectiveFunction[Varbl3] = this.CurrentSimplexTableau.ObjectiveFunction[(Varbl3 - 1)];
/*      */           
/*  539 */           this.CurrentSimplexTableau.ObjectiveFunctionM[Varbl3] = this.CurrentSimplexTableau.ObjectiveFunctionM[(Varbl3 - 1)];
/*      */           
/*  541 */           for (int Con = 1; Con <= this.CurrentSimplexTableau.NumConstraints; 
/*  542 */               Con++) {
/*  543 */             this.CurrentSimplexTableau.T[Con][Varbl3] = this.CurrentSimplexTableau.T[Con][(Varbl3 - 1)];
/*      */           }
/*  545 */           this.CurrentSimplexTableau.Nonnegativity[Varbl3] = this.CurrentSimplexTableau.Nonnegativity[(Varbl3 - 1)];
/*      */           
/*  547 */           this.CurrentSimplexTableau.LowerBound[Varbl3] = this.CurrentSimplexTableau.LowerBound[(Varbl3 - 1)];
/*      */           
/*  549 */           this.CurrentSimplexTableau.UpperBound[Varbl3] = this.CurrentSimplexTableau.UpperBound[(Varbl3 - 1)];
/*      */           
/*  551 */           this.CurrentSimplexTableau.AnUpperBound[Varbl3] = this.CurrentSimplexTableau.AnUpperBound[(Varbl3 - 1)];
/*      */         }
/*      */         
/*  554 */         this.CurrentSimplexTableau.Nonnegativity[Varbl] = true;
/*  555 */         this.CurrentSimplexTableau.LowerBound[Varbl] = 0.0D;
/*  556 */         this.CurrentSimplexTableau.Nonnegativity[(Varbl + 1)] = true;
/*  557 */         this.CurrentSimplexTableau.LowerBound[(Varbl + 1)] = 0.0D;
/*  558 */         this.CurrentSimplexTableau.UpperBound[(Varbl + 1)] = 0.0D;
/*  559 */         this.CurrentSimplexTableau.AnUpperBound[(Varbl + 1)] = false;
/*  560 */         this.CurrentSimplexTableau.ObjectiveFunction[(Varbl + 1)] = (-this.CurrentSimplexTableau.ObjectiveFunction[Varbl]);
/*      */         
/*  562 */         this.CurrentSimplexTableau.ObjectiveFunctionM[(Varbl + 1)] = (-this.CurrentSimplexTableau.ObjectiveFunctionM[Varbl]);
/*      */         
/*  564 */         for (int Con = 1; Con <= this.CurrentSimplexTableau.NumConstraints; Con++) {
/*  565 */           this.CurrentSimplexTableau.T[Con][(Varbl + 1)] = (-this.CurrentSimplexTableau.T[Con][Varbl]);
/*      */         }
/*  567 */         Varbl += 2;
/*      */       }
/*  569 */       else if (this.CurrentSimplexTableau.LowerBound[Varbl] != 0) {
/*  570 */         this.CurrentSimplexTableau.Z -= this.CurrentSimplexTableau.LowerBound[Varbl] * this.CurrentSimplexTableau.ObjectiveFunction[Varbl];
/*      */         
/*      */ 
/*  573 */         this.CurrentSimplexTableau.ZM -= this.CurrentSimplexTableau.LowerBound[Varbl] * this.CurrentSimplexTableau.ObjectiveFunctionM[Varbl];
/*      */         
/*      */ 
/*  576 */         for (int Con = 1; Con <= this.CurrentSimplexTableau.NumConstraints; Con++) {
/*  577 */           this.CurrentSimplexTableau.RightHandSide[Con] -= this.CurrentSimplexTableau.LowerBound[Varbl] * this.CurrentSimplexTableau.T[Con][Varbl];
/*      */         }
/*      */         
/*      */ 
/*  581 */         this.CurrentSimplexTableau.LowerBound[Varbl] = 0.0D;
/*  582 */         Varbl += 1;
/*      */       }
/*      */       else {
/*  585 */         Varbl += 1;
/*      */       }
/*  587 */       Varbl2 += 1;
/*      */     }
/*  589 */     while (Varbl2 <= this.SimplexModel.NumVariables);
/*  590 */     for (int Con = 1; Con <= this.CurrentSimplexTableau.NumConstraints; Con++) {
/*  591 */       if (this.CurrentSimplexTableau.RightHandSide[Con] < 0) {
/*  592 */         for (Varbl = 1; Varbl <= this.CurrentSimplexTableau.NumVariables; 
/*  593 */             Varbl++) {
/*  594 */           this.CurrentSimplexTableau.T[Con][Varbl] = (-this.CurrentSimplexTableau.T[Con][Varbl]);
/*      */         }
/*  596 */         this.CurrentSimplexTableau.RightHandSide[Con] = (-this.CurrentSimplexTableau.RightHandSide[Con]);
/*      */         
/*  598 */         if (this.CurrentSimplexTableau.Constraint[Con] == 0) {
/*  599 */           this.CurrentSimplexTableau.Constraint[Con] = 2;
/*  600 */         } else if (this.CurrentSimplexTableau.Constraint[Con] == 2)
/*  601 */           this.CurrentSimplexTableau.Constraint[Con] = 0;
/*      */       }
/*      */     }
/*  604 */     if (this.SimplexModel.Objective == 1) {
/*  605 */       for (Varbl = 1; Varbl <= this.CurrentSimplexTableau.NumVariables; Varbl++) {
/*  606 */         this.CurrentSimplexTableau.ObjectiveFunction[Varbl] = (-this.CurrentSimplexTableau.ObjectiveFunction[Varbl]);
/*      */         
/*  608 */         this.CurrentSimplexTableau.ObjectiveFunctionM[Varbl] = (-this.CurrentSimplexTableau.ObjectiveFunctionM[Varbl]);
/*      */       }
/*      */       
/*  611 */       this.CurrentSimplexTableau.Z = (-this.CurrentSimplexTableau.Z);
/*  612 */       this.CurrentSimplexTableau.ZM = (-this.CurrentSimplexTableau.ZM);
/*      */     }
/*  614 */     for (Con = 1; Con <= this.CurrentSimplexTableau.NumConstraints; Con++) {
/*  615 */       switch (this.CurrentSimplexTableau.Constraint[Con]) {
/*      */       case 0: 
/*  617 */         this.CurrentSimplexTableau.Constraint[Con] = 1;
/*  618 */         this.CurrentSimplexTableau.NumVariables += 1;
/*      */         
/*  620 */         this.CurrentSimplexTableau.T[Con][this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */         
/*  622 */         break;
/*      */       case 1: 
/*  624 */         this.CurrentSimplexTableau.NumVariables += 1;
/*      */         
/*  626 */         this.CurrentSimplexTableau.T[Con][this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */         
/*  628 */         this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */         
/*  630 */         this.CurrentSimplexTableau.AnArtificial[this.CurrentSimplexTableau.NumVariables] = true;
/*      */         
/*  632 */         break;
/*      */       case 2: 
/*  634 */         this.CurrentSimplexTableau.Constraint[Con] = 1;
/*  635 */         if (Con != 1) {
/*  636 */           for (Varbl = this.CurrentSimplexTableau.NumVariables; 
/*  637 */               Varbl >= this.CurrentSimplexTableau.NumVariables - Con + 2; 
/*      */               
/*  639 */               Varbl--) {
/*  640 */             for (int Con2 = 1; 
/*  641 */                 Con2 <= this.CurrentSimplexTableau.NumConstraints; 
/*  642 */                 Con2++) {
/*  643 */               this.CurrentSimplexTableau.T[Con2][(Varbl + 1)] = this.CurrentSimplexTableau.T[Con2][Varbl];
/*      */             }
/*  645 */             this.CurrentSimplexTableau.ObjectiveFunction[(Varbl + 1)] = this.CurrentSimplexTableau.ObjectiveFunction[Varbl];
/*      */             
/*      */ 
/*  648 */             this.CurrentSimplexTableau.ObjectiveFunctionM[(Varbl + 1)] = this.CurrentSimplexTableau.ObjectiveFunctionM[Varbl];
/*      */             
/*      */ 
/*  651 */             this.CurrentSimplexTableau.AnArtificial[(Varbl + 1)] = this.CurrentSimplexTableau.AnArtificial[Varbl];
/*      */           }
/*      */         }
/*      */         
/*  655 */         this.CurrentSimplexTableau.NumVariables += 2;
/*      */         
/*  657 */         this.CurrentSimplexTableau.T[Con][this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */         
/*  659 */         this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */         
/*  661 */         this.CurrentSimplexTableau.AnArtificial[this.CurrentSimplexTableau.NumVariables] = true;
/*      */         
/*  663 */         for (int Con2 = 1; Con2 <= this.CurrentSimplexTableau.NumConstraints; 
/*  664 */             Con2++) {
/*  665 */           if (Con2 == Con) {
/*  666 */             this.CurrentSimplexTableau.T[Con2][(this.CurrentSimplexTableau.NumVariables - Con)] = -1.0D;
/*      */           }
/*      */           else {
/*  669 */             this.CurrentSimplexTableau.T[Con2][(this.CurrentSimplexTableau.NumVariables - Con)] = 0.0D;
/*      */           }
/*      */         }
/*  672 */         this.CurrentSimplexTableau.AnArtificial[(this.CurrentSimplexTableau.NumVariables - Con)] = false;
/*      */         
/*  674 */         this.CurrentSimplexTableau.ObjectiveFunction[(this.CurrentSimplexTableau.NumVariables - Con)] = 0.0D;
/*      */         
/*  676 */         this.CurrentSimplexTableau.ObjectiveFunctionM[(this.CurrentSimplexTableau.NumVariables - Con)] = 0.0D;
/*      */       }
/*      */       
/*      */     }
/*      */     
/*  681 */     for (Varbl = this.CurrentSimplexTableau.NumVariables - this.CurrentSimplexTableau.NumConstraints + 1; 
/*      */         
/*  683 */         Varbl <= this.CurrentSimplexTableau.NumVariables; Varbl++) {
/*  684 */       this.CurrentSimplexTableau.BasicVariable[(Varbl - this.CurrentSimplexTableau.NumVariables + this.CurrentSimplexTableau.NumConstraints)] = Varbl;
/*      */     }
/*      */     
/*  687 */     for (Varbl = 1; Varbl <= this.CurrentSimplexTableau.NumVariables; Varbl++) {
/*  688 */       this.ComplementaryVariable[Varbl] = 0;
/*      */     }
/*      */   }
/*      */   
/*      */   private void SimplexDoPutSlacksInTheirProperPlace()
/*      */   {
/*  694 */     for (int i = this.FirstSimplexTableau.NumConstraints; i >= 1; i--) {
/*  695 */       int j = (int)Math.round(this.FirstSimplexTableau.BasicVariable[i]);
/*  696 */       int k = this.FirstSimplexTableau.NumVariables + i - this.FirstSimplexTableau.NumConstraints;
/*      */       
/*  698 */       if (j != k) {
/*  699 */         SwapColumns(j, k);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void SimplexDoConvertToProperForm()
/*      */   {
/*  708 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/*  709 */       int Column = (int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]);
/*  710 */       if ((Math.abs(this.CurrentSimplexTableau.T[i][Column] - 1) > 0.01D) && 
/*  711 */         (Math.abs(this.CurrentSimplexTableau.T[i][Column]) > 0.01D)) {
/*  712 */         double Multiplier = this.CurrentSimplexTableau.T[i][Column];
/*  713 */         for (int j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/*  714 */           this.CurrentSimplexTableau.T[i][j] /= Multiplier;
/*      */         }
/*  716 */         this.CurrentSimplexTableau.RightHandSide[i] /= Multiplier;
/*      */       }
/*      */       
/*      */ 
/*  720 */       if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunction[Column]) > 0.01D)
/*      */       {
/*  722 */         double Multiplier = this.CurrentSimplexTableau.ObjectiveFunction[Column];
/*  723 */         for (int j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/*  724 */           this.CurrentSimplexTableau.ObjectiveFunction[j] -= Multiplier * this.CurrentSimplexTableau.T[i][j];
/*      */         }
/*      */         
/*  727 */         this.CurrentSimplexTableau.Z -= Multiplier * this.CurrentSimplexTableau.RightHandSide[i];
/*      */       }
/*      */       
/*  730 */       if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunctionM[Column]) > 0.01D)
/*      */       {
/*  732 */         double Multiplier = this.CurrentSimplexTableau.ObjectiveFunctionM[Column];
/*  733 */         for (int j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/*  734 */           this.CurrentSimplexTableau.ObjectiveFunctionM[j] -= Multiplier * this.CurrentSimplexTableau.T[i][j];
/*      */         }
/*      */         
/*  737 */         this.CurrentSimplexTableau.ZM -= Multiplier * this.CurrentSimplexTableau.RightHandSide[i];
/*      */       }
/*      */       
/*  740 */       for (int k = 1; k <= this.CurrentSimplexTableau.NumConstraints; k++) {
/*  741 */         if ((k != i) && 
/*  742 */           (Math.abs(this.CurrentSimplexTableau.T[k][Column]) > 0.01D)) {
/*  743 */           double Multiplier = this.CurrentSimplexTableau.T[k][Column];
/*  744 */           for (int j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/*  745 */             this.CurrentSimplexTableau.T[k][j] -= Multiplier * this.CurrentSimplexTableau.T[i][j];
/*      */           }
/*      */           
/*  748 */           this.CurrentSimplexTableau.RightHandSide[k] -= Multiplier * this.CurrentSimplexTableau.RightHandSide[i];
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void InitializeABasicVariable()
/*      */   {
/*  760 */     for (int i = 0; i <= this.CurrentSimplexTableau.NumVariables; i++)
/*  761 */       this.CurrentSimplexTableau.ABasicVariable[i] = false;
/*  762 */     for (i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/*  763 */       this.CurrentSimplexTableau.ABasicVariable[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))] = true;
/*      */     }
/*      */     
/*  766 */     this.CurrentSimplexTableau.NumBasicArtificials = 0;
/*  767 */     for (i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/*  768 */       if ((this.CurrentSimplexTableau.ABasicVariable[i] != 0) && (this.CurrentSimplexTableau.AnArtificial[i] != 0))
/*      */       {
/*  770 */         this.CurrentSimplexTableau.NumBasicArtificials += 1;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void SimplexDoFindEBV1()
/*      */   {
/*  779 */     double MnM = 1.0E20D;
/*  780 */     this.CurrentSimplexTableau.EnteringBasicVariable = 1;
/*  781 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/*  782 */       if ((this.CurrentSimplexTableau.ABasicVariable[i] == 0) && (this.CurrentSimplexTableau.ABasicVariable[this.ComplementaryVariable[i]] == 0))
/*      */       {
/*  784 */         if (this.CurrentSimplexTableau.ObjectiveFunctionM[i] < MnM) {
/*  785 */           MnM = this.CurrentSimplexTableau.ObjectiveFunctionM[i];
/*  786 */           this.CurrentSimplexTableau.EnteringBasicVariable = i;
/*      */         }
/*      */       }
/*      */     }
/*  790 */     if (MnM > -1.0E-8D) {
/*  791 */       this.CurrentSimplexTableau.Optimal = true;
/*      */     } else {
/*  793 */       this.CurrentSimplexTableau.Optimal = false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void SimplexDoFindEBV2()
/*      */   {
/*  800 */     double Mn = 1.0E20D;
/*  801 */     this.CurrentSimplexTableau.EnteringBasicVariable = 1;
/*  802 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/*  803 */       if ((this.CurrentSimplexTableau.ABasicVariable[i] == 0) && (this.CurrentSimplexTableau.AnArtificial[i] == 0))
/*      */       {
/*  805 */         if (this.CurrentSimplexTableau.ObjectiveFunction[i] < Mn) {
/*  806 */           Mn = this.CurrentSimplexTableau.ObjectiveFunction[i];
/*  807 */           this.CurrentSimplexTableau.EnteringBasicVariable = i;
/*      */         }
/*      */       }
/*      */     }
/*  811 */     if (Mn > -1.0E-8D) {
/*  812 */       this.CurrentSimplexTableau.Optimal = true;
/*      */     } else {
/*  814 */       this.CurrentSimplexTableau.Optimal = false;
/*      */     }
/*      */   }
/*      */   
/*      */   private void SimplexDoCalcTMinimal()
/*      */   {
/*  820 */     if (this.CurrentSimplexTableau.EnteringBasicVariable <= this.CurrentSimplexTableau.NumVariables - this.CurrentSimplexTableau.NumConstraints)
/*      */     {
/*      */ 
/*  823 */       for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/*  824 */         this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.EnteringBasicVariable] = 0.0D;
/*      */         
/*  826 */         for (int j = 1; j <= this.CurrentSimplexTableau.NumConstraints; j++) {
/*  827 */           this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.EnteringBasicVariable] += this.BInverse[i][j] * this.FirstSimplexTableau.T[j][this.CurrentSimplexTableau.EnteringBasicVariable];
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  836 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/*  837 */       this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.EnteringBasicVariable] = this.BInverse[i][(this.CurrentSimplexTableau.EnteringBasicVariable + this.CurrentSimplexTableau.NumConstraints - this.CurrentSimplexTableau.NumVariables)];
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void SimplexDoCalcRHSMinimal()
/*      */   {
/*  848 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/*  849 */       if (this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.EnteringBasicVariable] > 0)
/*      */       {
/*  851 */         this.CurrentSimplexTableau.RightHandSide[i] = 0.0D;
/*  852 */         for (int j = 1; j <= this.CurrentSimplexTableau.NumConstraints; j++) {
/*  853 */           this.CurrentSimplexTableau.RightHandSide[i] += this.BInverse[i][j] * this.FirstSimplexTableau.RightHandSide[j];
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void SimplexDoFindLBVE()
/*      */   {
/*  864 */     double Mn = 1.0E20D;
/*  865 */     int MinCon = 0;
/*  866 */     for (int Con = 1; Con <= this.CurrentSimplexTableau.NumConstraints; Con++) {
/*  867 */       if (this.CurrentSimplexTableau.T[Con][this.CurrentSimplexTableau.EnteringBasicVariable] > 1.0E-10D)
/*      */       {
/*  869 */         if (this.CurrentSimplexTableau.RightHandSide[Con] / this.CurrentSimplexTableau.T[Con][this.CurrentSimplexTableau.EnteringBasicVariable] < Mn)
/*      */         {
/*      */ 
/*  872 */           Mn = this.CurrentSimplexTableau.RightHandSide[Con] / this.CurrentSimplexTableau.T[Con][this.CurrentSimplexTableau.EnteringBasicVariable];
/*      */           
/*      */ 
/*  875 */           MinCon = Con;
/*      */         }
/*      */       }
/*      */     }
/*  879 */     this.CurrentSimplexTableau.LeavingBasicVariableEquation = MinCon;
/*  880 */     if (MinCon == 0)
/*  881 */       this.CurrentSimplexTableau.Unbounded = true;
/*      */   }
/*      */   
/*      */   private void SimplexDoUpdateBasicVariables() {
/*  885 */     if (this.CurrentSimplexTableau.AnArtificial[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.LeavingBasicVariableEquation]))] != 0)
/*      */     {
/*      */ 
/*  888 */       this.CurrentSimplexTableau.NumBasicArtificials -= 1;
/*      */     }
/*  890 */     if (this.CurrentSimplexTableau.AnArtificial[this.CurrentSimplexTableau.EnteringBasicVariable] != 0)
/*      */     {
/*  892 */       this.CurrentSimplexTableau.NumBasicArtificials += 1;
/*      */     }
/*  894 */     this.CurrentSimplexTableau.ABasicVariable[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.LeavingBasicVariableEquation]))] = false;
/*      */     
/*      */ 
/*  897 */     this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.LeavingBasicVariableEquation] = this.CurrentSimplexTableau.EnteringBasicVariable;
/*      */     
/*      */ 
/*  900 */     this.CurrentSimplexTableau.ABasicVariable[this.CurrentSimplexTableau.EnteringBasicVariable] = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void SimplexDoCalcBInverse()
/*      */   {
/*  907 */     for (int Column = 1; Column <= this.CurrentSimplexTableau.NumConstraints; Column++) {
/*  908 */       this.BInverse[this.CurrentSimplexTableau.LeavingBasicVariableEquation][Column] /= this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][this.CurrentSimplexTableau.EnteringBasicVariable];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  913 */     for (int Row = 1; Row <= this.CurrentSimplexTableau.NumConstraints; Row++) {
/*  914 */       if (Row != this.CurrentSimplexTableau.LeavingBasicVariableEquation) {
/*  915 */         for (Column = 1; Column <= this.CurrentSimplexTableau.NumConstraints; 
/*  916 */             Column++) {
/*  917 */           this.BInverse[Row][Column] -= this.CurrentSimplexTableau.T[Row][this.CurrentSimplexTableau.EnteringBasicVariable] * this.BInverse[this.CurrentSimplexTableau.LeavingBasicVariableEquation][Column];
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void SimplexDoCalcCBasic()
/*      */   {
/*  929 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/*  930 */       this.CBasic[i] = (-this.FirstSimplexTableau.ObjectiveFunction[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))]);
/*      */       
/*  932 */       this.CMBasic[i] = (-this.FirstSimplexTableau.ObjectiveFunctionM[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void SimplexDoCalcObjectiveFunctionMinimal()
/*      */   {
/*  939 */     double[] IntVector = new double[51];
/*      */     
/*  941 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/*  942 */       IntVector[i] = 0.0D;
/*  943 */       for (int j = 1; j <= this.CurrentSimplexTableau.NumConstraints; j++)
/*  944 */         IntVector[i] += this.CMBasic[j] * this.BInverse[j][i];
/*      */     }
/*  946 */     for (i = 1; 
/*      */         
/*  948 */         i <= this.CurrentSimplexTableau.NumVariables - this.CurrentSimplexTableau.NumConstraints; 
/*  949 */         i++) {
/*  950 */       if ((this.CurrentSimplexTableau.ABasicVariable[i] == 0) && (this.CurrentSimplexTableau.ABasicVariable[this.ComplementaryVariable[i]] == 0))
/*      */       {
/*  952 */         this.CurrentSimplexTableau.ObjectiveFunctionM[i] = 0.0D;
/*  953 */         for (int j = 1; j <= this.CurrentSimplexTableau.NumConstraints; j++) {
/*  954 */           this.CurrentSimplexTableau.ObjectiveFunctionM[i] += IntVector[j] * this.FirstSimplexTableau.T[j][i];
/*      */         }
/*      */         
/*  957 */         this.CurrentSimplexTableau.ObjectiveFunctionM[i] += this.FirstSimplexTableau.ObjectiveFunctionM[i];
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  962 */     for (i = this.CurrentSimplexTableau.NumVariables - this.CurrentSimplexTableau.NumConstraints + 1; 
/*      */         
/*  964 */         i <= this.CurrentSimplexTableau.NumVariables; i++) {
/*  965 */       if ((this.CurrentSimplexTableau.ABasicVariable[i] == 0) && (this.CurrentSimplexTableau.ABasicVariable[this.ComplementaryVariable[i]] == 0))
/*      */       {
/*  967 */         this.CurrentSimplexTableau.ObjectiveFunctionM[i] = IntVector[(i - this.CurrentSimplexTableau.NumVariables + this.CurrentSimplexTableau.NumConstraints)];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void SimplexPrepareForPhase2()
/*      */   {
/*  976 */     if (this.CurrentSimplexTableau.NumBasicArtificials > 0) {
/*  977 */       SimplexDoCalcZ();
/*  978 */       if (this.CurrentSimplexTableau.ZM > 0.01D) {
/*  979 */         this.CurrentSimplexTableau.Feasible = false;
/*  980 */         this.CurrentSimplexTableau.Optimal = true;
/*      */       }
/*      */       else {
/*  983 */         this.CurrentSimplexTableau.NumConstraints += 1;
/*      */         
/*  985 */         this.CurrentSimplexTableau.NumVariables += 1;
/*      */         
/*  987 */         this.FirstSimplexTableau.NumConstraints = this.CurrentSimplexTableau.NumConstraints;
/*      */         
/*  989 */         this.FirstSimplexTableau.NumVariables = this.CurrentSimplexTableau.NumVariables;
/*      */         
/*  991 */         for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/*  992 */           if (i == this.CurrentSimplexTableau.NumConstraints) {
/*  993 */             this.BInverse[i][i] = 1.0D;
/*      */           }
/*      */           else {
/*  996 */             this.BInverse[this.CurrentSimplexTableau.NumConstraints][i] = 0.0D;
/*  997 */             this.BInverse[i][this.CurrentSimplexTableau.NumConstraints] = 0.0D;
/*      */           }
/*      */         }
/* 1000 */         this.CurrentSimplexTableau.ABasicVariable[this.CurrentSimplexTableau.NumVariables] = true;
/*      */         
/* 1002 */         this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.NumConstraints] = this.CurrentSimplexTableau.NumVariables;
/*      */         
/* 1004 */         this.FirstSimplexTableau.BasicVariable[this.CurrentSimplexTableau.NumConstraints] = this.CurrentSimplexTableau.NumVariables;
/*      */         
/* 1006 */         for (i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 1007 */           this.FirstSimplexTableau.T[i][this.CurrentSimplexTableau.NumVariables] = 0.0D;
/*      */           
/* 1009 */           this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.NumVariables] = 0.0D;
/*      */         }
/*      */         
/* 1012 */         this.FirstSimplexTableau.ObjectiveFunction[this.CurrentSimplexTableau.NumVariables] = 0.0D;
/*      */         
/* 1014 */         this.CurrentSimplexTableau.ObjectiveFunction[this.CurrentSimplexTableau.NumVariables] = 0.0D;
/*      */         
/* 1016 */         this.FirstSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.NumVariables] = 0.0D;
/*      */         
/* 1018 */         this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.NumVariables] = 0.0D;
/*      */         
/* 1020 */         for (i = 1; i <= this.CurrentSimplexTableau.NumVariables - 1; i++) { int j;
/* 1021 */           if ((this.CurrentSimplexTableau.ABasicVariable[i] != 0) && (this.CurrentSimplexTableau.AnArtificial[i] != 0))
/*      */           {
/* 1023 */             this.FirstSimplexTableau.T[this.CurrentSimplexTableau.NumConstraints][i] = 1.0D;
/*      */             
/* 1025 */             this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.NumConstraints][i] = 1.0D;
/*      */             
/* 1027 */             j = 1; }
/* 1028 */           while (j <= this.CurrentSimplexTableau.NumConstraints - 1) {
/* 1029 */             if ((int)Math.round(this.CurrentSimplexTableau.BasicVariable[j]) == i)
/*      */             {
/* 1031 */               for (int k = 1; 
/* 1032 */                   k <= this.CurrentSimplexTableau.NumConstraints - 1; 
/*      */                   
/* 1034 */                   k++) {
/* 1035 */                 this.BInverse[this.CurrentSimplexTableau.NumConstraints][k] -= this.BInverse[j][k];
/*      */               }
/*      */             }
/* 1028 */             j++;continue;
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1043 */             this.FirstSimplexTableau.T[this.CurrentSimplexTableau.NumConstraints][i] = 0.0D;
/*      */             
/* 1045 */             this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.NumConstraints][i] = 0.0D;
/*      */           }
/*      */         }
/*      */         
/* 1049 */         this.FirstSimplexTableau.T[this.CurrentSimplexTableau.NumConstraints][this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */         
/* 1051 */         this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.NumConstraints][this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */         
/* 1053 */         this.FirstSimplexTableau.RightHandSide[this.CurrentSimplexTableau.NumConstraints] = 0.0D;
/*      */         
/* 1055 */         this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.NumConstraints] = 0.0D;
/*      */         
/* 1057 */         this.CurrentSimplexTableau.Feasible = true;
/* 1058 */         this.CurrentSimplexTableau.Optimal = false;
/*      */       }
/*      */     }
/*      */     else {
/* 1062 */       this.CurrentSimplexTableau.Feasible = true;
/* 1063 */       this.CurrentSimplexTableau.Optimal = false;
/*      */     }
/*      */   }
/*      */   
/*      */   private void SimplexDoCalcObjectiveFunctionMinimal2()
/*      */   {
/* 1069 */     double[] IntVector = new double[51];
/*      */     
/* 1071 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 1072 */       IntVector[i] = 0.0D;
/* 1073 */       for (int j = 1; j <= this.CurrentSimplexTableau.NumConstraints; j++)
/* 1074 */         IntVector[i] += this.CBasic[j] * this.BInverse[j][i];
/*      */     }
/* 1076 */     for (i = 1; 
/* 1077 */         i <= this.CurrentSimplexTableau.NumVariables - this.CurrentSimplexTableau.NumConstraints; 
/*      */         
/* 1079 */         i++) {
/* 1080 */       if ((this.CurrentSimplexTableau.ABasicVariable[i] == 0) && (this.CurrentSimplexTableau.AnArtificial[i] == 0))
/*      */       {
/* 1082 */         this.CurrentSimplexTableau.ObjectiveFunction[i] = 0.0D;
/* 1083 */         for (int j = 1; j <= this.CurrentSimplexTableau.NumConstraints; j++) {
/* 1084 */           this.CurrentSimplexTableau.ObjectiveFunction[i] += IntVector[j] * this.FirstSimplexTableau.T[j][i];
/*      */         }
/*      */         
/* 1087 */         this.CurrentSimplexTableau.ObjectiveFunction[i] += this.FirstSimplexTableau.ObjectiveFunction[i];
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1092 */     for (i = this.CurrentSimplexTableau.NumVariables - this.CurrentSimplexTableau.NumConstraints + 1; 
/*      */         
/* 1094 */         i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 1095 */       if ((this.CurrentSimplexTableau.ABasicVariable[i] == 0) && (this.CurrentSimplexTableau.AnArtificial[i] == 0))
/*      */       {
/* 1097 */         this.CurrentSimplexTableau.ObjectiveFunction[i] = IntVector[(i - this.CurrentSimplexTableau.NumVariables + this.CurrentSimplexTableau.NumConstraints)];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void SimplexDoCalcT()
/*      */   {
/* 1106 */     MultiplyMatrices3(this.BInverse, this.FirstSimplexTableau.T, this.CurrentSimplexTableau.NumConstraints, this.CurrentSimplexTableau.NumConstraints, this.CurrentSimplexTableau.NumVariables - this.CurrentSimplexTableau.NumConstraints, this.CurrentSimplexTableau.T);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1112 */     for (int i = this.CurrentSimplexTableau.NumVariables + 1 - this.CurrentSimplexTableau.NumConstraints; 
/*      */         
/* 1114 */         i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 1115 */       for (int j = 1; j <= this.CurrentSimplexTableau.NumConstraints; j++) {
/* 1116 */         this.CurrentSimplexTableau.T[j][i] = this.BInverse[j][(i - this.CurrentSimplexTableau.NumVariables + this.CurrentSimplexTableau.NumConstraints)];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void SimplexDoCalcRHS() {
/* 1122 */     MultiplyMatrixAndVector2(this.BInverse, this.FirstSimplexTableau.RightHandSide, this.CurrentSimplexTableau.NumConstraints, this.CurrentSimplexTableau.NumConstraints, this.CurrentSimplexTableau.RightHandSide);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void SimplexDoCalcObjectiveFunction()
/*      */   {
/* 1129 */     double[] IntVector1 = new double[51];
/* 1130 */     double[] IntVector2 = new double[''];
/*      */     
/*      */ 
/* 1133 */     MultiplyVectorAndMatrix2(this.CBasic, this.BInverse, this.CurrentSimplexTableau.NumConstraints, this.CurrentSimplexTableau.NumConstraints, IntVector1);
/*      */     
/*      */ 
/*      */ 
/* 1137 */     MultiplyVectorAndMatrix3(IntVector1, this.FirstSimplexTableau.T, this.CurrentSimplexTableau.NumConstraints, this.CurrentSimplexTableau.NumVariables - this.CurrentSimplexTableau.NumConstraints, IntVector2);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1142 */     AddVectors3(IntVector2, this.FirstSimplexTableau.ObjectiveFunction, this.CurrentSimplexTableau.NumVariables - this.CurrentSimplexTableau.NumConstraints, this.CurrentSimplexTableau.ObjectiveFunction);
/*      */     
/*      */ 
/*      */ 
/* 1146 */     for (int i = this.CurrentSimplexTableau.NumVariables - this.CurrentSimplexTableau.NumConstraints + 1; 
/*      */         
/* 1148 */         i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 1149 */       this.CurrentSimplexTableau.ObjectiveFunction[i] = IntVector1[(i - this.CurrentSimplexTableau.NumVariables + this.CurrentSimplexTableau.NumConstraints)];
/*      */     }
/*      */     
/* 1152 */     MultiplyVectorAndMatrix2(this.CMBasic, this.BInverse, this.CurrentSimplexTableau.NumConstraints, this.CurrentSimplexTableau.NumConstraints, IntVector1);
/*      */     
/*      */ 
/*      */ 
/* 1156 */     MultiplyVectorAndMatrix3(IntVector1, this.FirstSimplexTableau.T, this.CurrentSimplexTableau.NumConstraints, this.CurrentSimplexTableau.NumVariables - this.CurrentSimplexTableau.NumConstraints, IntVector2);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1161 */     AddVectors3(IntVector2, this.FirstSimplexTableau.ObjectiveFunctionM, this.CurrentSimplexTableau.NumVariables - this.CurrentSimplexTableau.NumConstraints, this.CurrentSimplexTableau.ObjectiveFunctionM);
/*      */     
/*      */ 
/*      */ 
/* 1165 */     for (i = this.CurrentSimplexTableau.NumVariables - this.CurrentSimplexTableau.NumConstraints + 1; 
/*      */         
/* 1167 */         i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 1168 */       this.CurrentSimplexTableau.ObjectiveFunctionM[i] = IntVector1[(i - this.CurrentSimplexTableau.NumVariables + this.CurrentSimplexTableau.NumConstraints)];
/*      */     }
/*      */   }
/*      */   
/*      */   private void SimplexDoCalcXBasic()
/*      */   {
/* 1174 */     MultiplyMatrixAndVector2(this.BInverse, this.FirstSimplexTableau.RightHandSide, this.CurrentSimplexTableau.NumConstraints, this.CurrentSimplexTableau.NumConstraints, this.XBasic);
/*      */   }
/*      */   
/*      */ 
/*      */   private void SimplexDoCalcZ()
/*      */   {
/* 1180 */     this.CurrentSimplexTableau.Z = (MultiplyVectors2(this.CBasic, this.XBasic, this.CurrentSimplexTableau.NumConstraints) + this.FirstSimplexTableau.Z);
/*      */     
/* 1182 */     this.CurrentSimplexTableau.ZM = (MultiplyVectors2(this.CMBasic, this.XBasic, this.CurrentSimplexTableau.NumConstraints) + this.FirstSimplexTableau.ZM);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void FillSolution(ORSolverBase.SimplexTableauType Tableau, double[] Solution)
/*      */   {
/* 1191 */     for (int i = 1; i <= 50; i++)
/* 1192 */       Solution[i] = this.SimplexModel.LowerBound[i];
/* 1193 */     int WhichVar = 0;
/* 1194 */     for (i = 1; i <= Tableau.NumConstraints; i++) {
/* 1195 */       WhichVar = 0;
/* 1196 */       int k = 0;
/* 1197 */       int l = 1;
/* 1198 */       double m = 0.0D;
/* 1199 */       for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/* 1200 */         if (j + k == Math.round(Tableau.BasicVariable[i])) {
/* 1201 */           WhichVar = j;
/* 1202 */           if (Math.abs(this.SimplexModel.LowerBound[j]) > 1.0E-5D)
/* 1203 */             m = this.SimplexModel.LowerBound[j];
/*      */         }
/* 1205 */         if (this.SimplexModel.Nonnegativity[j] == 0) {
/* 1206 */           if (j + k + 1 == Math.round(Tableau.BasicVariable[i])) {
/* 1207 */             WhichVar = j;
/* 1208 */             l = -1;
/*      */           }
/* 1210 */           k += 1;
/*      */         }
/*      */       }
/* 1213 */       if (WhichVar > 0) {
/* 1214 */         Solution[WhichVar] = (l * Tableau.RightHandSide[i] + m);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean SolutionFeasible(ORSolverBase.SimplexModelType Model, double[] Solution)
/*      */   {
/* 1223 */     boolean Temp = true;
/* 1224 */     for (int i = 1; i <= Model.NumConstraints; i++) {
/* 1225 */       double Sum = 0.0D;
/* 1226 */       for (int j = 1; j <= Model.NumVariables; j++)
/* 1227 */         Sum += Model.A[i][j] * Solution[j];
/* 1228 */       switch (Model.Constraint[i]) {
/*      */       case 0: 
/* 1230 */         if (Sum > Model.RightHandSide[i] + 0.01D)
/* 1231 */           Temp = false;
/* 1232 */         break;
/*      */       case 1: 
/* 1234 */         if (Math.abs(Sum - Model.RightHandSide[i]) > 0.01D)
/* 1235 */           Temp = false;
/* 1236 */         break;
/*      */       case 2: 
/* 1238 */         if (Sum < Model.RightHandSide[i] - 0.01D)
/* 1239 */           Temp = false;
/*      */         break;
/*      */       }
/*      */     }
/* 1243 */     for (i = 1; i <= Model.NumVariables; i++) {
/* 1244 */       if ((Solution[i] < Model.LowerBound[i] - 0.01D) && (Model.Nonnegativity[i] != 0))
/*      */       {
/* 1246 */         Temp = false;
/*      */       }
/*      */     }
/* 1249 */     return Temp;
/*      */   }
/*      */   
/*      */ 
/*      */   private void FillSensitivityAnalysisInfo()
/*      */   {
/*      */     int j;
/* 1256 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 1257 */       double MaxNeg = -1.0E10D;
/* 1258 */       double MinPos = 1.0E10D;
/* 1259 */       for (j = 1; j <= this.CurrentSimplexTableau.NumConstraints; j++) {
/* 1260 */         if (this.BInverse[j][i] > 1.0E-5D) {
/* 1261 */           MaxNeg = Math.max(MaxNeg, -this.CurrentSimplexTableau.RightHandSide[j] / this.BInverse[j][i]);
/*      */ 
/*      */         }
/* 1264 */         else if (this.BInverse[j][i] < -1.0E-5D) {
/* 1265 */           MinPos = Math.min(MinPos, -this.CurrentSimplexTableau.RightHandSide[j] / this.BInverse[j][i]);
/*      */         }
/*      */       }
/*      */       
/* 1269 */       MaxNeg = Math.min(0.0D, MaxNeg);
/* 1270 */       MinPos = Math.max(0.0D, MinPos);
/* 1271 */       if (this.SimplexModel.RightHandSide[i] < -1.0E-6D) {
/* 1272 */         this.LPSolution.MinRHS[i] = (-this.FirstSimplexTableau.RightHandSide[i] - MinPos);
/*      */         
/* 1274 */         this.LPSolution.MaxRHS[i] = (-this.FirstSimplexTableau.RightHandSide[i] - MaxNeg);
/*      */       }
/*      */       else
/*      */       {
/* 1278 */         this.LPSolution.MinRHS[i] = (this.FirstSimplexTableau.RightHandSide[i] + MaxNeg);
/*      */         
/* 1280 */         this.LPSolution.MaxRHS[i] = (this.FirstSimplexTableau.RightHandSide[i] + MinPos);
/*      */       }
/*      */       
/* 1283 */       if ((this.SimplexModel.Constraint[i] == 1) || ((this.SimplexModel.Constraint[i] == 0) && (this.SimplexModel.RightHandSide[i] > -1.0E-6D)) || ((this.SimplexModel.Constraint[i] == 2) && (this.SimplexModel.RightHandSide[i] <= -1.0E-6D)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1288 */         this.LPSolution.ShadowPrice[i] = this.CurrentSimplexTableau.ObjectiveFunction[((int)Math.round(this.FirstSimplexTableau.BasicVariable[i]))];
/*      */         
/*      */ 
/* 1291 */         this.LPSolution.Slack[i] = 0.0D;
/* 1292 */         for (j = 1; j <= this.CurrentSimplexTableau.NumConstraints;) {
/* 1293 */           if (this.CurrentSimplexTableau.BasicVariable[j] == Math.round(this.FirstSimplexTableau.BasicVariable[i]))
/*      */           {
/* 1295 */             this.LPSolution.Slack[i] = this.CurrentSimplexTableau.RightHandSide[j];
/*      */           }
/* 1292 */           j++; continue;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1300 */           j = this.FirstSimplexTableau.NumVariables;
/* 1301 */           while (Math.abs(this.FirstSimplexTableau.T[i][j] + 1) > 1.0E-4D)
/* 1302 */             j -= 1;
/* 1303 */           this.LPSolution.ShadowPrice[i] = (-this.CurrentSimplexTableau.ObjectiveFunction[j]);
/*      */           
/* 1305 */           this.LPSolution.Slack[i] = 0.0D;
/* 1306 */           for (int k = 1; k <= this.CurrentSimplexTableau.NumConstraints; k++) {
/* 1307 */             if (this.CurrentSimplexTableau.BasicVariable[k] == j)
/* 1308 */               this.LPSolution.Slack[i] = this.CurrentSimplexTableau.RightHandSide[k];
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1313 */     int j = 1;
/* 1314 */     for (i = 1; i <= this.SimplexModel.NumVariables; i++) {
/* 1315 */       double MaxNeg = -1.0E10D;
/* 1316 */       double MinPos = 1.0E10D;
/* 1317 */       if (this.CurrentSimplexTableau.ABasicVariable[j] != 0) {
/* 1318 */         int k = 1;
/* 1319 */         while (Math.round(this.CurrentSimplexTableau.BasicVariable[k]) != j)
/* 1320 */           k += 1;
/* 1321 */         for (int l = 1; l <= this.CurrentSimplexTableau.NumVariables; l++) {
/* 1322 */           if ((this.CurrentSimplexTableau.ABasicVariable[l] == 0) && (Math.abs(this.CurrentSimplexTableau.ObjectiveFunctionM[l]) < 1.0E-4D) && ((l != j + 1) || (this.SimplexModel.Nonnegativity[i] != 0)))
/*      */           {
/*      */ 
/*      */ 
/* 1326 */             if (this.SimplexModel.Objective == 0) {
/* 1327 */               if (this.CurrentSimplexTableau.T[k][l] < -1.0E-4D) {
/* 1328 */                 MinPos = Math.min(MinPos, -this.CurrentSimplexTableau.ObjectiveFunction[l] / this.CurrentSimplexTableau.T[k][l]);
/*      */               }
/*      */               
/*      */ 
/* 1332 */               if (this.CurrentSimplexTableau.T[k][l] > 1.0E-4D) {
/* 1333 */                 MaxNeg = Math.max(MaxNeg, -this.CurrentSimplexTableau.ObjectiveFunction[l] / this.CurrentSimplexTableau.T[k][l]);
/*      */               }
/*      */               
/*      */             }
/*      */             else
/*      */             {
/* 1339 */               if (this.CurrentSimplexTableau.T[k][l] > 1.0E-4D) {
/* 1340 */                 MinPos = Math.min(MinPos, this.CurrentSimplexTableau.ObjectiveFunction[l] / this.CurrentSimplexTableau.T[k][l]);
/*      */               }
/*      */               
/*      */ 
/* 1344 */               if (this.CurrentSimplexTableau.T[k][l] < -1.0E-4D) {
/* 1345 */                 MaxNeg = Math.max(MaxNeg, this.CurrentSimplexTableau.ObjectiveFunction[l] / this.CurrentSimplexTableau.T[k][l]);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1353 */       if (this.SimplexModel.Nonnegativity[i] != 0) {
/* 1354 */         if (this.SimplexModel.Objective == 0) {
/* 1355 */           if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunctionM[j]) < 1.0E-5D)
/*      */           {
/* 1357 */             MinPos = Math.min(MinPos, this.CurrentSimplexTableau.ObjectiveFunction[j]);
/*      */           }
/*      */           
/*      */ 
/*      */         }
/* 1362 */         else if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunctionM[j]) < 1.0E-5D)
/*      */         {
/* 1364 */           MaxNeg = Math.max(MaxNeg, -this.CurrentSimplexTableau.ObjectiveFunction[j]);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1369 */       j += 1;
/* 1370 */       if (this.SimplexModel.Nonnegativity[i] == 0) {
/* 1371 */         if (this.CurrentSimplexTableau.ABasicVariable[j] != 0) {
/* 1372 */           int k = 1;
/* 1373 */           while (Math.round(this.CurrentSimplexTableau.BasicVariable[k]) != j)
/*      */           {
/* 1375 */             k += 1; }
/* 1376 */           for (int l = 1; l <= this.CurrentSimplexTableau.NumVariables; l++) {
/* 1377 */             if ((this.CurrentSimplexTableau.ABasicVariable[l] == 0) && (Math.abs(this.CurrentSimplexTableau.ObjectiveFunctionM[l]) < 1.0E-4D) && ((l != j - 1) || (this.SimplexModel.Nonnegativity[i] != 0)))
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/* 1382 */               if (this.SimplexModel.Objective == 1) {
/* 1383 */                 if (this.CurrentSimplexTableau.T[k][l] < -1.0E-4D) {
/* 1384 */                   MinPos = Math.min(MinPos, -this.CurrentSimplexTableau.ObjectiveFunction[l] / this.CurrentSimplexTableau.T[k][l]);
/*      */                 }
/*      */                 
/*      */ 
/* 1388 */                 if (this.CurrentSimplexTableau.T[k][l] > 1.0E-4D) {
/* 1389 */                   MaxNeg = Math.max(MaxNeg, -this.CurrentSimplexTableau.ObjectiveFunction[l] / this.CurrentSimplexTableau.T[k][l]);
/*      */                 }
/*      */                 
/*      */               }
/*      */               else
/*      */               {
/* 1395 */                 if (this.CurrentSimplexTableau.T[k][l] > 1.0E-4D) {
/* 1396 */                   MinPos = Math.min(MinPos, this.CurrentSimplexTableau.ObjectiveFunction[l] / this.CurrentSimplexTableau.T[k][l]);
/*      */                 }
/*      */                 
/* 1399 */                 if (this.CurrentSimplexTableau.T[k][l] < -1.0E-4D) {
/* 1400 */                   MaxNeg = Math.max(MaxNeg, this.CurrentSimplexTableau.ObjectiveFunction[l] / this.CurrentSimplexTableau.T[k][l]);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1407 */         if (this.SimplexModel.Nonnegativity[i] != 0) {
/* 1408 */           if (this.SimplexModel.Objective == 1) {
/* 1409 */             if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunctionM[j]) < 1.0E-5D)
/*      */             {
/* 1411 */               MinPos = Math.min(MinPos, this.CurrentSimplexTableau.ObjectiveFunction[j]);
/*      */             }
/*      */             
/*      */ 
/*      */           }
/* 1416 */           else if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunctionM[j]) < 1.0E-5D)
/*      */           {
/* 1418 */             MaxNeg = Math.max(MaxNeg, -this.CurrentSimplexTableau.ObjectiveFunction[j]);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1423 */         j += 1;
/*      */       }
/* 1425 */       MaxNeg = Math.min(0.0D, MaxNeg);
/* 1426 */       MinPos = Math.max(0.0D, MinPos);
/* 1427 */       this.LPSolution.MinObjective[i] = (this.SimplexModel.ObjectiveFunction[i] + MaxNeg);
/*      */       
/* 1429 */       this.LPSolution.MaxObjective[i] = (this.SimplexModel.ObjectiveFunction[i] + MinPos);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void SwapColumns(int Col1, int Col2)
/*      */   {
/* 1441 */     double td = this.CurrentSimplexTableau.ObjectiveFunctionM[Col2];
/* 1442 */     this.CurrentSimplexTableau.ObjectiveFunctionM[Col2] = this.CurrentSimplexTableau.ObjectiveFunctionM[Col1];
/*      */     
/* 1444 */     this.CurrentSimplexTableau.ObjectiveFunctionM[Col1] = td;
/* 1445 */     td = this.FirstSimplexTableau.ObjectiveFunctionM[Col2];
/* 1446 */     this.FirstSimplexTableau.ObjectiveFunctionM[Col2] = this.FirstSimplexTableau.ObjectiveFunctionM[Col1];
/*      */     
/* 1448 */     this.FirstSimplexTableau.ObjectiveFunctionM[Col1] = td;
/*      */     
/*      */ 
/* 1451 */     td = this.CurrentSimplexTableau.ObjectiveFunction[Col2];
/* 1452 */     this.CurrentSimplexTableau.ObjectiveFunction[Col2] = this.CurrentSimplexTableau.ObjectiveFunction[Col1];
/*      */     
/* 1454 */     this.CurrentSimplexTableau.ObjectiveFunction[Col1] = td;
/* 1455 */     td = this.FirstSimplexTableau.ObjectiveFunction[Col2];
/* 1456 */     this.FirstSimplexTableau.ObjectiveFunction[Col2] = this.FirstSimplexTableau.ObjectiveFunction[Col1];
/*      */     
/* 1458 */     this.FirstSimplexTableau.ObjectiveFunction[Col1] = td;
/*      */     
/* 1460 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++)
/*      */     {
/* 1462 */       td = this.CurrentSimplexTableau.T[i][Col2];
/* 1463 */       this.CurrentSimplexTableau.T[i][Col2] = this.CurrentSimplexTableau.T[i][Col1];
/* 1464 */       this.CurrentSimplexTableau.T[i][Col1] = td;
/* 1465 */       td = this.FirstSimplexTableau.T[i][Col2];
/* 1466 */       this.FirstSimplexTableau.T[i][Col2] = this.FirstSimplexTableau.T[i][Col1];
/* 1467 */       this.FirstSimplexTableau.T[i][Col1] = td;
/*      */     }
/* 1469 */     for (i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 1470 */       if (Math.round(this.CurrentSimplexTableau.BasicVariable[i]) == Col1) {
/* 1471 */         this.CurrentSimplexTableau.BasicVariable[i] = Col2;
/*      */       }
/* 1473 */       else if (Math.round(this.CurrentSimplexTableau.BasicVariable[i]) == Col2) {
/* 1474 */         this.CurrentSimplexTableau.BasicVariable[i] = Col1;
/*      */       }
/* 1476 */       if (Math.round(this.FirstSimplexTableau.BasicVariable[i]) == Col1) {
/* 1477 */         this.FirstSimplexTableau.BasicVariable[i] = Col2;
/*      */       }
/* 1479 */       else if (Math.round(this.FirstSimplexTableau.BasicVariable[i]) == Col2) {
/* 1480 */         this.FirstSimplexTableau.BasicVariable[i] = Col1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1485 */     boolean tb = this.CurrentSimplexTableau.AnArtificial[Col2];
/* 1486 */     this.CurrentSimplexTableau.AnArtificial[Col2] = this.CurrentSimplexTableau.AnArtificial[Col1];
/*      */     
/* 1488 */     this.CurrentSimplexTableau.AnArtificial[Col1] = tb;
/* 1489 */     tb = this.FirstSimplexTableau.AnArtificial[Col2];
/* 1490 */     this.FirstSimplexTableau.AnArtificial[Col2] = this.FirstSimplexTableau.AnArtificial[Col1];
/*      */     
/* 1492 */     this.FirstSimplexTableau.AnArtificial[Col1] = tb;
/*      */     
/*      */ 
/* 1495 */     tb = this.CurrentSimplexTableau.Nonnegativity[Col2];
/* 1496 */     this.CurrentSimplexTableau.Nonnegativity[Col2] = this.CurrentSimplexTableau.Nonnegativity[Col1];
/*      */     
/* 1498 */     this.CurrentSimplexTableau.Nonnegativity[Col1] = tb;
/* 1499 */     tb = this.FirstSimplexTableau.Nonnegativity[Col2];
/* 1500 */     this.FirstSimplexTableau.Nonnegativity[Col2] = this.FirstSimplexTableau.Nonnegativity[Col1];
/*      */     
/* 1502 */     this.FirstSimplexTableau.Nonnegativity[Col1] = tb;
/*      */     
/*      */ 
/* 1505 */     td = this.CurrentSimplexTableau.LowerBound[Col2];
/* 1506 */     this.CurrentSimplexTableau.LowerBound[Col2] = this.CurrentSimplexTableau.LowerBound[Col1];
/*      */     
/* 1508 */     this.CurrentSimplexTableau.LowerBound[Col1] = td;
/* 1509 */     td = this.FirstSimplexTableau.LowerBound[Col2];
/* 1510 */     this.FirstSimplexTableau.LowerBound[Col2] = this.FirstSimplexTableau.LowerBound[Col1];
/*      */     
/* 1512 */     this.FirstSimplexTableau.LowerBound[Col1] = td;
/*      */     
/*      */ 
/* 1515 */     for (i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 1516 */       if (this.ComplementaryVariable[i] == Col1) {
/* 1517 */         this.ComplementaryVariable[i] = Col2;
/* 1518 */       } else if (this.ComplementaryVariable[i] == Col2) {
/* 1519 */         this.ComplementaryVariable[i] = Col1;
/*      */       }
/*      */     }
/* 1522 */     int ti = this.ComplementaryVariable[Col2];
/* 1523 */     this.ComplementaryVariable[Col2] = this.ComplementaryVariable[Col1];
/* 1524 */     this.ComplementaryVariable[Col1] = ti;
/*      */   }
/*      */   
/*      */   private void RemoveTheArtificials()
/*      */   {
/* 1529 */     int Column = 1;
/* 1530 */     while (Column <= this.CurrentSimplexTableau.NumVariables) {
/* 1531 */       if (this.CurrentSimplexTableau.AnArtificial[Column] != 0) {
/* 1532 */         if (Column == this.CurrentSimplexTableau.NumVariables) {
/* 1533 */           this.CurrentSimplexTableau.NumVariables -= 1;
/*      */         }
/*      */         else {
/* 1536 */           for (int i = Column; 
/* 1537 */               i <= this.CurrentSimplexTableau.NumVariables - 1; i++)
/* 1538 */             SwapColumns(i, i + 1);
/* 1539 */           this.CurrentSimplexTableau.NumVariables -= 1;
/*      */         }
/*      */         
/*      */       }
/*      */       else {
/* 1544 */         Column += 1;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void SetupIP()
/*      */   {
/* 1553 */     this.IP = new ORSolverBase.InteriorPointType(this);
/*      */     
/*      */ 
/*      */ 
/* 1557 */     for (int i = 1; i <= this.SimplexModel.NumConstraints; i++) {
/* 1558 */       for (int j = 1; j <= this.SimplexModel.NumVariables; j++)
/* 1559 */         this.IP.A[i][j] = this.SimplexModel.A[i][j];
/*      */     }
/* 1561 */     for (i = 1; i <= this.SimplexModel.NumVariables; i++) {
/* 1562 */       if (this.SimplexModel.Objective == 0) {
/* 1563 */         this.IP.c[i] = this.SimplexModel.ObjectiveFunction[i];
/*      */       } else
/* 1565 */         this.IP.c[i] = (-this.SimplexModel.ObjectiveFunction[i]);
/*      */     }
/* 1567 */     this.IP.NumVariables = this.SimplexModel.NumVariables;
/* 1568 */     this.IP.NumConstraints = this.SimplexModel.NumConstraints;
/* 1569 */     for (i = 1; i <= this.SimplexModel.NumConstraints; i++) { int j;
/* 1570 */       switch (this.SimplexModel.Constraint[i]) {
/*      */       case 0: 
/* 1572 */         this.IP.NumVariables += 1;
/* 1573 */         for (j = 1; j <= this.SimplexModel.NumConstraints;) {
/* 1574 */           if (i == j) {
/* 1575 */             this.IP.A[j][this.IP.NumVariables] = 1.0D;
/*      */           }
/* 1573 */           j++; continue;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1579 */           this.IP.NumVariables += 1;
/* 1580 */           for (int j = 1; j <= this.SimplexModel.NumConstraints; j++) {
/* 1581 */             if (i == j)
/* 1582 */               this.IP.A[j][this.IP.NumVariables] = -1.0D;
/*      */           }
/*      */         }
/*      */       }
/* 1586 */       this.IP.RightHandSide[i] = this.SimplexModel.RightHandSide[i];
/*      */     }
/* 1588 */     int Offset = 0;
/* 1589 */     for (i = 1; i <= this.SimplexModel.NumVariables; i++) {
/* 1590 */       if (this.SimplexModel.Nonnegativity[i] == 0) {
/* 1591 */         this.IP.NumVariables += 1;
/* 1592 */         for (int j = this.IP.NumVariables; j >= i + Offset + 2; j--) {
/* 1593 */           for (int k = 1; k <= this.SimplexModel.NumConstraints; k++)
/* 1594 */             this.IP.A[k][j] = this.IP.A[k][(j - 1)];
/* 1595 */           this.IP.c[j] = this.IP.c[(j - 1)];
/*      */         }
/* 1597 */         for (int k = 1; k <= this.SimplexModel.NumConstraints; k++)
/* 1598 */           this.IP.A[k][(i + Offset + 1)] = (-this.IP.A[k][(i + Offset)]);
/* 1599 */         this.IP.c[(i + Offset + 1)] = (-this.IP.c[(i + Offset)]);
/* 1600 */         Offset += 1;
/*      */       }
/* 1602 */       else if (Math.abs(this.SimplexModel.LowerBound[i]) > 0.01D) {
/* 1603 */         for (int j = 1; j <= this.SimplexModel.NumConstraints; j++) {
/* 1604 */           this.IP.RightHandSide[j] -= this.SimplexModel.LowerBound[i] * this.SimplexModel.A[j][i];
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void my_SetupIP() {
/* 1611 */     this.IP.Solution[0][1] = 0.5D;
/* 1612 */     this.IP.Solution[0][2] = 1.0D;
/* 1613 */     this.IP.Solution[0][3] = 1.0D;
/* 1614 */     this.IP.Solution[0][4] = 2.5D;
/* 1615 */     this.IP.Solution[0][5] = 1.0D;
/* 1616 */     this.IP.Solution[0][6] = 7.5D;
/* 1617 */     this.Alpha = 0.8999999761581421D;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean CheckInitIPSolution()
/*      */   {
/* 1625 */     boolean Correct1 = true;
/* 1626 */     boolean Correct2 = true;
/* 1627 */     for (int i = 1; i <= this.IP.NumConstraints; i++) {
/* 1628 */       double Temp = 0.0D;
/* 1629 */       for (int j = 1; j <= this.IP.NumVariables; j++)
/* 1630 */         Temp += this.IP.A[i][j] * this.IP.Solution[0][j];
/* 1631 */       if (Math.abs(Temp - this.IP.RightHandSide[i]) > 0.1D) {
/* 1632 */         Correct1 = false;
/* 1633 */         this.errInfo = "Sorry. The above solution violates one of the functional constraints. Please try again.";
/*      */       }
/*      */     }
/* 1636 */     for (int j = 1; j <= this.IP.NumVariables; j++) {
/* 1637 */       if (this.IP.Solution[0][j] <= 1.0E-4D) {
/* 1638 */         Correct2 = false;
/* 1639 */         this.errInfo = "The above solution is not in the INTERIOR of the feasible region. Please try again.";
/*      */       }
/*      */     }
/* 1642 */     return (Correct1) && (Correct2);
/*      */   }
/*      */   
/*      */   private void IterateIP() {
/* 1646 */     double[][] Temp1 = new double[11][11];
/* 1647 */     double[][] Temp2 = new double[11][11];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1653 */     for (int i = 1; i <= this.IP.NumVariables; i++)
/* 1654 */       this.IP.x[i] = this.IP.Solution[this.IP.It][i];
/* 1655 */     this.IP.It += 1;
/* 1656 */     for (i = 1; i <= this.IP.NumVariables; i++) {
/* 1657 */       for (int j = 1; j <= this.IP.NumVariables; j++) {
/* 1658 */         if (i == j) {
/* 1659 */           this.IP.D[i][j] = this.IP.x[i];
/*      */         } else {
/* 1661 */           this.IP.D[i][j] = 0.0D;
/*      */         }
/*      */       }
/*      */     }
/* 1665 */     MultiplyMatrices(this.IP.A, this.IP.D, this.IP.NumConstraints, this.IP.NumVariables, this.IP.NumVariables, this.IP.ATilde);
/*      */     
/* 1667 */     MultiplyMatrixAndVector(this.IP.D, this.IP.c, this.IP.NumVariables, this.IP.NumVariables, this.IP.cTilde);
/*      */     
/*      */ 
/* 1670 */     TransposeMatrix(this.IP.ATilde, this.IP.AT, Math.max(this.IP.NumVariables, this.IP.NumConstraints));
/*      */     
/* 1672 */     MultiplyMatrices(this.IP.ATilde, this.IP.AT, this.IP.NumConstraints, this.IP.NumVariables, this.IP.NumConstraints, Temp1);
/*      */     
/* 1674 */     boolean Singular = InvertMatrix(Temp1, Temp2, this.IP.NumConstraints);
/* 1675 */     MultiplyMatrices(this.IP.AT, Temp2, this.IP.NumVariables, this.IP.NumConstraints, this.IP.NumConstraints, Temp1);
/*      */     
/* 1677 */     MultiplyMatrices(Temp1, this.IP.ATilde, this.IP.NumVariables, this.IP.NumConstraints, this.IP.NumVariables, Temp2);
/*      */     
/* 1679 */     for (i = 1; i <= this.IP.NumVariables; i++) {
/* 1680 */       for (int j = 1; j <= this.IP.NumVariables; j++) {
/* 1681 */         if (i == j) {
/* 1682 */           this.IP.P[i][j] = (1 - Temp2[i][j]);
/*      */         } else
/* 1684 */           this.IP.P[i][j] = (-Temp2[i][j]);
/*      */       }
/*      */     }
/* 1687 */     MultiplyMatrixAndVector(this.IP.P, this.IP.cTilde, this.IP.NumVariables, this.IP.NumVariables, this.IP.cp);
/*      */     
/*      */ 
/* 1690 */     double v = 9.999999747378752E-6D;
/* 1691 */     for (i = 1; i <= this.IP.NumVariables; i++) {
/* 1692 */       if ((this.IP.cp[i] < 0) && (Math.abs(this.IP.cp[i]) > v))
/* 1693 */         v = Math.abs(this.IP.cp[i]);
/*      */     }
/* 1695 */     this.AlphaUsed = this.Alpha;
/* 1696 */     for (i = 1; i <= this.IP.NumVariables; i++) {
/* 1697 */       this.IP.xTilde[i] = (1 + this.Alpha / v * this.IP.cp[i]);
/*      */     }
/* 1699 */     MultiplyMatrixAndVector(this.IP.D, this.IP.xTilde, this.IP.NumVariables, this.IP.NumVariables, this.IP.x);
/*      */     
/* 1701 */     for (i = 1; i <= this.IP.NumVariables; i++) {
/* 1702 */       this.IP.Solution[this.IP.It][i] = RealBound(this.IP.x[i], -1.0E10D, 1.0E10D);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void MultiplyMatrices3(double[][] LeftMatrix, double[][] RightMatrix, int LeftNR, int LeftNC, int RightNC, double[][] ResultingMatrix)
/*      */   {
/* 1712 */     for (int Row = 1; Row <= LeftNR; Row++) {
/* 1713 */       for (int Column = 1; Column <= RightNC; Column++) {
/* 1714 */         ResultingMatrix[Row][Column] = 0.0D;
/* 1715 */         for (int ColRow = 1; ColRow <= LeftNC; ColRow++) {
/* 1716 */           ResultingMatrix[Row][Column] += LeftMatrix[Row][ColRow] * RightMatrix[ColRow][Column];
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void MultiplyMatrixAndVector2(double[][] Matrix, double[] Vector, int NumRows, int NumColumns, double[] ResultingVector)
/*      */   {
/* 1727 */     for (int Row = 1; Row <= NumRows; Row++) {
/* 1728 */       ResultingVector[Row] = 0.0D;
/* 1729 */       for (int Column = 1; Column <= NumColumns; Column++) {
/* 1730 */         ResultingVector[Row] += Matrix[Row][Column] * Vector[Column];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void MultiplyVectorAndMatrix2(double[] Vector, double[][] Matrix, int NumRows, int NumColumns, double[] ResultingVector)
/*      */   {
/* 1740 */     for (int Column = 1; Column <= NumColumns; Column++) {
/* 1741 */       ResultingVector[Column] = 0.0D;
/* 1742 */       for (int Row = 1; Row <= NumRows; Row++) {
/* 1743 */         ResultingVector[Column] += Vector[Row] * Matrix[Row][Column];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void MultiplyVectorAndMatrix3(double[] Vector, double[][] Matrix, int NumRows, int NumColumns, double[] ResultingVector)
/*      */   {
/* 1753 */     for (int Column = 1; Column <= NumColumns; Column++) {
/* 1754 */       ResultingVector[Column] = 0.0D;
/* 1755 */       for (int Row = 1; Row <= NumRows; Row++) {
/* 1756 */         ResultingVector[Column] += Vector[Row] * Matrix[Row][Column];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void AddVectors3(double[] Vector1, double[] Vector2, int Size, double[] ResultingVector)
/*      */   {
/* 1765 */     for (int Column = 1; Column <= Size; Column++) {
/* 1766 */       Vector1[Column] += Vector2[Column];
/*      */     }
/*      */   }
/*      */   
/*      */   private double MultiplyVectors2(double[] Vector1, double[] Vector2, int Size) {
/* 1771 */     double Temp = 0.0D;
/*      */     
/*      */ 
/* 1774 */     for (int i = 1; i <= Size; i++)
/* 1775 */       Temp += Vector1[i] * Vector2[i];
/* 1776 */     return Temp;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private double MultiplyVectors(double[] Vector1, double[] Vector2, int Size)
/*      */   {
/* 1783 */     double Temp = 0.0D;
/* 1784 */     for (int i = 1; i <= Size; i++)
/* 1785 */       Temp += Vector1[i] * Vector2[i];
/* 1786 */     return Temp;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void MultiplyMatrixAndVector(double[][] Matrix, double[] Vector, int NumRows, int NumColumns, double[] ResultingVector)
/*      */   {
/* 1793 */     for (int Row = 1; Row <= NumRows; Row++) {
/* 1794 */       ResultingVector[Row] = 0.0D;
/* 1795 */       for (int Column = 1; Column <= NumColumns; Column++) {
/* 1796 */         ResultingVector[Row] += Matrix[Row][Column] * Vector[Column];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void TransposeMatrix(double[][] Matrix, double[][] Transpose, int Size)
/*      */   {
/* 1804 */     for (int i = 1; i <= Size; i++) {
/* 1805 */       for (int j = 1; j <= Size; j++) {
/* 1806 */         Transpose[j][i] = Matrix[i][j];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean InvertMatrix(double[][] Matrix, double[][] Inverse, int Size) {
/* 1812 */     double[] b = new double[11];
/* 1813 */     double[] c = new double[11];
/*      */     
/*      */ 
/* 1816 */     int i = 1;
/*      */     boolean TempSingular;
/* 1818 */     do { for (int j = 1; j <= Size; j++) {
/* 1819 */         if (i != j) {
/* 1820 */           b[j] = 0.0D;
/*      */         } else
/* 1822 */           b[j] = 1.0D;
/*      */       }
/* 1824 */       TempSingular = SolveSystem(Matrix, b, Size, c);
/* 1825 */       for (j = 1; j <= Size; j++)
/* 1826 */         Inverse[j][i] = c[j];
/* 1827 */       i += 1;
/*      */     }
/* 1829 */     while ((!TempSingular) && (i <= Size));
/* 1830 */     return TempSingular;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void IterateSimplexMethod()
/*      */   {
/* 1837 */     SimplexDoFindEBV();
/* 1838 */     if (this.CurrentSimplexTableau.Optimal == false) {
/* 1839 */       SimplexDoFindLBVE();
/* 1840 */       if (this.CurrentSimplexTableau.LeavingBasicVariableEquation != 0) {
/* 1841 */         this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.LeavingBasicVariableEquation] = this.CurrentSimplexTableau.EnteringBasicVariable;
/*      */         
/*      */ 
/* 1844 */         double Factor = this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][this.CurrentSimplexTableau.EnteringBasicVariable];
/*      */         
/*      */ 
/* 1847 */         this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.LeavingBasicVariableEquation] /= Factor;
/*      */         
/*      */ 
/*      */ 
/* 1851 */         for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 1852 */           this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][i] /= Factor;
/*      */         }
/*      */         
/*      */ 
/* 1856 */         for (i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 1857 */           if (i != this.CurrentSimplexTableau.LeavingBasicVariableEquation) {
/* 1858 */             this.CurrentSimplexTableau.RightHandSide[i] -= this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.LeavingBasicVariableEquation] * this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.EnteringBasicVariable];
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1865 */         for (i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 1866 */           Factor = this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.EnteringBasicVariable];
/*      */           
/* 1868 */           for (int j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/* 1869 */             if (i != this.CurrentSimplexTableau.LeavingBasicVariableEquation)
/*      */             {
/* 1871 */               this.CurrentSimplexTableau.T[i][j] -= this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][j] * Factor;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1877 */         this.CurrentSimplexTableau.Z -= this.CurrentSimplexTableau.ObjectiveFunction[this.CurrentSimplexTableau.EnteringBasicVariable] * this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.LeavingBasicVariableEquation];
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1882 */         this.CurrentSimplexTableau.ZM -= this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.EnteringBasicVariable] * this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.LeavingBasicVariableEquation];
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1887 */         Factor = this.CurrentSimplexTableau.ObjectiveFunction[this.CurrentSimplexTableau.EnteringBasicVariable];
/*      */         
/* 1889 */         double FactorM = this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.EnteringBasicVariable];
/*      */         
/* 1891 */         for (int j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/* 1892 */           this.CurrentSimplexTableau.ObjectiveFunction[j] -= Factor * this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][j];
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 1897 */           this.CurrentSimplexTableau.ObjectiveFunctionM[j] -= FactorM * this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][j];
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1905 */       this.CurrentSimplexTableau.Unbounded = true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void SimplexDoFindEBV()
/*      */   {
/* 1913 */     double MnM = 1.0E20D;
/* 1914 */     double Mn = 0.0D;
/* 1915 */     this.CurrentSimplexTableau.EnteringBasicVariable = 1;
/* 1916 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 1917 */       if ((this.CurrentSimplexTableau.ABasicVariable[i] == 0) && (
/* 1918 */         (this.CurrentSimplexTableau.ObjectiveFunctionM[i] < MnM) || ((Math.abs(this.CurrentSimplexTableau.ObjectiveFunctionM[i] - MnM) < 1.0E-8D) && (this.CurrentSimplexTableau.ObjectiveFunction[i] < Mn))))
/*      */       {
/*      */ 
/*      */ 
/* 1922 */         MnM = this.CurrentSimplexTableau.ObjectiveFunctionM[i];
/* 1923 */         Mn = this.CurrentSimplexTableau.ObjectiveFunction[i];
/* 1924 */         this.CurrentSimplexTableau.EnteringBasicVariable = i;
/*      */       }
/*      */     }
/*      */     
/* 1928 */     if ((MnM > -1.0E-8D) && (Mn > -1.0E-8D)) {
/* 1929 */       this.CurrentSimplexTableau.Optimal = true;
/*      */     } else {
/* 1931 */       this.CurrentSimplexTableau.Optimal = false;
/*      */     }
/*      */   }
/*      */   
/*      */   private void DualSimplexDoCalcNextTableau()
/*      */   {
/* 1937 */     DualLeavingBasicVariable();
/* 1938 */     DualEnteringBasicVariable();
/* 1939 */     DualSimplexReduction();
/* 1940 */     this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.LeavingBasicVariableEquation] = this.CurrentSimplexTableau.EnteringBasicVariable;
/*      */     
/*      */ 
/* 1943 */     this.CurrentSimplexTableau.Feasible = true;
/* 1944 */     for (int k = 1; k <= this.CurrentSimplexTableau.NumConstraints; k++) {
/* 1945 */       if (this.CurrentSimplexTableau.RightHandSide[k] < 0) {
/* 1946 */         this.CurrentSimplexTableau.Feasible = false;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void DualLeavingBasicVariable()
/*      */   {
/* 1954 */     double Min = 32000.0D;
/* 1955 */     int Which = 0;
/* 1956 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 1957 */       if (this.CurrentSimplexTableau.RightHandSide[i] < Min) {
/* 1958 */         Min = this.CurrentSimplexTableau.RightHandSide[i];
/* 1959 */         Which = i;
/*      */       }
/*      */     }
/* 1962 */     this.CurrentSimplexTableau.LeavingBasicVariableEquation = Which;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void DualEnteringBasicVariable()
/*      */   {
/* 1969 */     double Min = 3200.0D;
/* 1970 */     int Which = 0;
/* 1971 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 1972 */       if (this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][i] < -1.0E-5D)
/*      */       {
/* 1974 */         if ((Math.round(this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.LeavingBasicVariableEquation]) != i) && (-this.CurrentSimplexTableau.ObjectiveFunction[i] / this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][i] < Min))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1980 */           Min = -this.CurrentSimplexTableau.ObjectiveFunction[i] / this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][i];
/*      */           
/*      */ 
/* 1983 */           Which = i;
/*      */         }
/*      */       }
/*      */     }
/* 1987 */     this.CurrentSimplexTableau.EnteringBasicVariable = Which;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void DualSimplexReduction()
/*      */   {
/* 1994 */     double TheMultiple = this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][this.CurrentSimplexTableau.EnteringBasicVariable];
/*      */     
/*      */ 
/* 1997 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 1998 */       this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][i] /= TheMultiple;
/*      */     }
/*      */     
/*      */ 
/* 2002 */     this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.LeavingBasicVariableEquation] /= TheMultiple;
/*      */     
/*      */ 
/* 2005 */     TheMultiple = this.CurrentSimplexTableau.ObjectiveFunction[this.CurrentSimplexTableau.EnteringBasicVariable];
/*      */     
/* 2007 */     for (i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 2008 */       this.CurrentSimplexTableau.ObjectiveFunction[i] -= TheMultiple * this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][i];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2013 */     this.CurrentSimplexTableau.Z -= TheMultiple * this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.LeavingBasicVariableEquation];
/*      */     
/*      */ 
/*      */ 
/* 2017 */     for (i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 2018 */       if (i != this.CurrentSimplexTableau.LeavingBasicVariableEquation) {
/* 2019 */         TheMultiple = this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.EnteringBasicVariable];
/*      */         
/* 2021 */         for (int j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/* 2022 */           this.CurrentSimplexTableau.T[i][j] -= TheMultiple * this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][j];
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2027 */         this.CurrentSimplexTableau.RightHandSide[i] -= TheMultiple * this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.LeavingBasicVariableEquation];
/*      */       }
/*      */     }
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
/*      */   private void OutputLPResult()
/*      */   {
/* 2043 */     if ((this.CurrentSimplexTableau.NumVariables <= 0) || (this.CurrentSimplexTableau.Unbounded) || (!this.CurrentSimplexTableau.Feasible))
/*      */     {
/*      */ 
/* 2046 */       if (this.CurrentSimplexTableau.Unbounded) {
/* 2047 */         System.out.print("\nUnbounded!\n");
/* 2048 */         this.outputInfo = "Unbounded";
/* 2049 */       } else if (!this.CurrentSimplexTableau.Feasible) {
/* 2050 */         System.out.print("\nNot Feasible!\n");
/* 2051 */         this.outputInfo = "Not Feasible!";
/*      */       } else {
/* 2053 */         System.out.print("\nNo Result!\n");
/* 2054 */         this.outputInfo = "No Result!";
/*      */       }
/*      */     }
/*      */     else {
/* 2058 */       this.outputInfo = "right";
/* 2059 */       this.variableValeV = null;this.variableValeV = new Vector();
/* 2060 */       for (int i = 1; i <= this.SimplexModel.NumVariables; i++) {
/* 2061 */         String s = String.valueOf(String.valueOf(new StringBuffer("   X").append(i).append("  =  ").append(ConvertDoubleToFixString(this.LPSolution.Solution[i], 10, 6, 1, false)).append("\n")));
/*      */         
/*      */ 
/* 2064 */         System.out.print(s);
/* 2065 */         this.variableValeV.addElement(new Double(this.LPSolution.Solution[i]));
/*      */       }
/* 2067 */       String s = String.valueOf(String.valueOf(new StringBuffer("   Z   =  ").append(ConvertDoubleToFixString(this.CurrentSimplexTableau.Z, 12, 6, 1, false)).append("\n")));
/*      */       
/*      */ 
/*      */ 
/* 2071 */       System.out.print(s);
/*      */       
/*      */ 
/* 2074 */       System.out.print("\n");
/* 2075 */       System.out.print(ConvertStringToFixString("  ", 14, 2));
/* 2076 */       System.out.print(ConvertStringToFixString("Slack or", 12, 2));
/* 2077 */       System.out.print(ConvertStringToFixString("Shadow", 14, 2));
/* 2078 */       System.out.print("\n");
/* 2079 */       System.out.print(ConvertStringToFixString("Constrains  ", 14, 2));
/* 2080 */       System.out.print(ConvertStringToFixString("Surplus", 12, 2));
/* 2081 */       System.out.print(ConvertStringToFixString("Price", 14, 2));
/* 2082 */       System.out.print("\n\n");
/* 2083 */       this.SlackV = null;this.SlackV = new Vector();
/* 2084 */       this.ShadowPriceV = null;this.ShadowPriceV = new Vector();
/* 2085 */       for (i = 1; i <= this.SimplexModel.NumConstraints; i++) {
/* 2086 */         s = String.valueOf(String.valueOf(new StringBuffer("  ").append(i).append("      ")));
/* 2087 */         s = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(ConvertStringToFixString(s, 14, 2)))).append(ConvertDoubleToFixString(this.LPSolution.Slack[i], 10, 6, 2, false)).append(ConvertDoubleToFixString(this.LPSolution.ShadowPrice[i], 16, 6, 2, false)).append("\n")));
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2094 */         this.SlackV.addElement(new Double(this.LPSolution.Slack[i]));
/* 2095 */         this.ShadowPriceV.addElement(new Double(this.LPSolution.ShadowPrice[i]));
/* 2096 */         System.out.print(s);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void OutputIPResult()
/*      */   {
/* 2105 */     System.out.print("\n");
/* 2106 */     for (int i = 0; i <= this.IP.It; i++) {
/* 2107 */       System.out.print(ConvertStringToFixString(" ", 6, 2));
/* 2108 */       System.out.print(ConvertIntToFixString(i, 8, 1));
/* 2109 */       double Z = 0.0D;
/* 2110 */       for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/* 2111 */         System.out.print(ConvertDoubleToFixString(this.IP.Solution[i][j], 14, 8, 1, false));
/*      */         
/* 2113 */         Z += this.IP.Solution[i][j] * this.SimplexModel.ObjectiveFunction[j];
/*      */       }
/* 2115 */       System.out.print(ConvertDoubleToFixString(Z, 14, 8, 1, false));
/* 2116 */       System.out.print("\n");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void OutputVariables(ORSolverBase.SimplexTableauType Tableau)
/*      */   {
/* 2123 */     int width = 10;int dec = 4;
/* 2124 */     boolean hasM = false;
/*      */     
/* 2126 */     System.out.print("\n");
/* 2127 */     for (int j = 1; j <= Tableau.NumVariables; j++) {
/* 2128 */       if (Math.abs(Tableau.ObjectiveFunctionM[j]) > 1.0E-6D)
/* 2129 */         hasM = true;
/*      */     }
/* 2131 */     if (Math.abs(Tableau.ZM) > 1.0E-6D)
/* 2132 */       hasM = true;
/* 2133 */     String sDouble1; if (hasM) {
/* 2134 */       for (j = 1; j <= Tableau.NumVariables; j++) { String sDouble1;
/* 2135 */         String sDouble1; if (Math.abs(Tableau.ObjectiveFunctionM[j]) < 1.0E-6D) {
/* 2136 */           sDouble1 = ConvertDoubleToFixString(Tableau.ObjectiveFunctionM[j], width, dec, 2, false);
/*      */         }
/*      */         else {
/*      */           String sDouble1;
/* 2140 */           if (Math.abs(Tableau.ObjectiveFunction[j]) < 1.0E-6D) {
/* 2141 */             sDouble1 = ConvertDoubleToFixString(Tableau.ObjectiveFunction[j], width, dec, 2, false);
/*      */           }
/*      */           else
/*      */           {
/* 2145 */             sDouble1 = ConvertDoubleToFixString(Tableau.ObjectiveFunctionM[j], width - 1, dec, 2, false);
/*      */             
/* 2147 */             sDouble1 = String.valueOf(String.valueOf(sDouble1)).concat("M");
/*      */           }
/*      */         }
/* 2150 */         System.out.print(sDouble1); }
/*      */       String sDouble1;
/*      */       String sDouble1;
/* 2153 */       if (Math.abs(Tableau.ZM) < 1.0E-6D) {
/* 2154 */         sDouble1 = ConvertDoubleToFixString(Tableau.ZM, width + 5, dec, 2, false);
/*      */       }
/*      */       else
/*      */       {
/*      */         String sDouble1;
/* 2159 */         if (Math.abs(Tableau.Z) < 1.0E-6D) {
/* 2160 */           sDouble1 = ConvertDoubleToFixString(Tableau.Z, width + 5, dec, 2, false);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/* 2166 */           sDouble1 = ConvertDoubleToFixString(Tableau.ZM, width + 4, dec, 2, false);
/*      */           
/* 2168 */           sDouble1 = String.valueOf(String.valueOf(sDouble1)).concat("M");
/*      */         }
/*      */       }
/*      */       
/* 2172 */       System.out.print(sDouble1);
/*      */       
/* 2174 */       System.out.print("\n");
/* 2175 */       for (j = 1; j <= Tableau.NumVariables; j++) {
/* 2176 */         if ((Math.abs(Tableau.ObjectiveFunction[j]) < 1.0E-6D) && (Math.abs(Tableau.ObjectiveFunctionM[j]) >= 1.0E-6D))
/*      */         {
/* 2178 */           sDouble1 = ConvertDoubleToFixString(Tableau.ObjectiveFunctionM[j], width - 1, dec, 2, false);
/*      */           
/* 2180 */           sDouble1 = String.valueOf(String.valueOf(sDouble1)).concat("M");
/*      */         }
/*      */         else {
/* 2183 */           sDouble1 = ConvertDoubleToFixString(Tableau.ObjectiveFunction[j], width, dec, 2, Math.abs(Tableau.ObjectiveFunctionM[j]) >= 1.0E-6D);
/*      */         }
/*      */         
/* 2186 */         System.out.print(sDouble1);
/*      */       }
/*      */       
/* 2189 */       if ((Math.abs(Tableau.Z) < 1.0E-6D) && (Math.abs(Tableau.ZM) >= 1.0E-6D)) {
/* 2190 */         sDouble1 = ConvertDoubleToFixString(Tableau.ZM, width + 4, dec, 2, false);
/*      */         
/* 2192 */         sDouble1 = String.valueOf(String.valueOf(sDouble1)).concat("M");
/*      */       }
/*      */       else {
/* 2195 */         sDouble1 = ConvertDoubleToFixString(Tableau.Z, width + 5, dec, 2, Math.abs(Tableau.ZM) >= 1.0E-6D);
/*      */       }
/* 2197 */       System.out.print(sDouble1);
/*      */       
/* 2199 */       System.out.print("\n");
/*      */     }
/*      */     else {
/* 2202 */       System.out.print("\n");
/* 2203 */       for (j = 1; j <= Tableau.NumVariables; j++) {
/* 2204 */         String sDouble1 = ConvertDoubleToFixString(Tableau.ObjectiveFunction[j], width, dec, 2, false);
/*      */         
/* 2206 */         System.out.print(sDouble1);
/*      */       }
/* 2208 */       sDouble1 = ConvertDoubleToFixString(Tableau.Z, width + 5, dec, 2, false);
/* 2209 */       System.out.print(sDouble1);
/*      */       
/* 2211 */       System.out.print("\n");
/*      */     }
/*      */     
/* 2214 */     for (int i = 1; i <= Tableau.NumConstraints; i++) {
/* 2215 */       System.out.print("\n");
/* 2216 */       for (j = 1; j <= Tableau.NumVariables; j++) {
/* 2217 */         sDouble1 = ConvertDoubleToFixString(Tableau.T[i][j], width, dec, 2, false);
/*      */         
/* 2219 */         System.out.print(sDouble1);
/*      */       }
/* 2221 */       sDouble1 = ConvertDoubleToFixString(Tableau.RightHandSide[i], width + 5, dec, 2, false);
/*      */       
/* 2223 */       System.out.print(sDouble1);
/*      */       
/* 2225 */       System.out.print("\n");
/*      */     }
/* 2227 */     System.out.print("\n\n");
/*      */   }
/*      */   
/*      */ 
/*      */   public ORLPSolver()
/*      */   {
/* 2233 */     this.ApplicationNumber = 1;
/* 2234 */     this.CommandNumber = 2;
/* 2235 */     this.Alpha = 0.5D;
/* 2236 */     InitializeSimplexModel();
/* 2237 */     my_InitSimplexModel();
/* 2238 */     InitializeFirstSimplexTableau();
/*      */   }
/*      */   
/*      */ 
/*      */   public void getData(IORLPProcessController.LPData d)
/*      */   {
/* 2244 */     if (d.objective_coefficient == null) {
/* 2245 */       d.objective_coefficient = new double[''];
/*      */     }
/* 2247 */     if (d.first_objective_coefficient == null) {
/* 2248 */       d.first_objective_coefficient = new double[''];
/*      */     }
/*      */     
/* 2251 */     if (d.objective_M_coefficient == null) {
/* 2252 */       d.objective_M_coefficient = new double[''];
/*      */     }
/* 2254 */     if (d.first_objective_M_coefficient == null) {
/* 2255 */       d.first_objective_M_coefficient = new double[''];
/*      */     }
/*      */     
/* 2258 */     if (d.constrain_operator == null) {
/* 2259 */       d.constrain_operator = new int[51];
/*      */     }
/* 2261 */     if (d.constrain_coefficient == null) {
/* 2262 */       d.constrain_coefficient = new double[51][''];
/*      */     }
/*      */     
/* 2265 */     if (d.first_constrain_coefficient == null) {
/* 2266 */       d.first_constrain_coefficient = new double[51][''];
/*      */     }
/*      */     
/* 2269 */     if (d.bound_operator == null) {
/* 2270 */       d.bound_operator = new boolean[''];
/*      */     }
/* 2272 */     if (d.bound == null) {
/* 2273 */       d.bound = new double[''];
/*      */     }
/* 2275 */     if (d.basic_variable_index == null) {
/* 2276 */       d.basic_variable_index = new int[51];
/*      */     }
/* 2278 */     if (d.first_basic_variable_index == null) {
/* 2279 */       d.first_basic_variable_index = new int[51];
/*      */     }
/*      */     
/* 2282 */     if (d.is_artificial == null) {
/* 2283 */       d.is_artificial = new boolean[''];
/*      */     }
/*      */     
/*      */ 
/* 2287 */     d.max_num_variable = 6;
/* 2288 */     d.max_num_constrain = 6;
/*      */     
/* 2290 */     d.num_original_variable = this.SimplexModel.NumVariables;
/* 2291 */     d.num_variable = this.CurrentSimplexTableau.NumVariables;
/* 2292 */     d.num_constrain = this.CurrentSimplexTableau.NumConstraints;
/*      */     
/* 2294 */     d.is_big_M = this.is_big_M;
/*      */     
/* 2296 */     d.objective_is_max = (this.CurrentSimplexTableau.Objective == 0);
/* 2297 */     d.original_objective_is_max = (this.SimplexModel.Objective == 0);
/* 2298 */     d.objective_coefficient[0] = this.CurrentSimplexTableau.Z;
/* 2299 */     d.objective_M_coefficient[0] = this.CurrentSimplexTableau.ZM;
/* 2300 */     d.first_objective_coefficient[0] = this.FirstSimplexTableau.Z;
/* 2301 */     d.first_objective_M_coefficient[0] = this.FirstSimplexTableau.ZM;
/* 2302 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 2303 */       d.objective_coefficient[i] = this.CurrentSimplexTableau.ObjectiveFunction[i];
/*      */       
/* 2305 */       d.objective_M_coefficient[i] = this.CurrentSimplexTableau.ObjectiveFunctionM[i];
/*      */       
/* 2307 */       d.first_objective_coefficient[i] = this.FirstSimplexTableau.ObjectiveFunction[i];
/*      */       
/* 2309 */       d.first_objective_M_coefficient[i] = this.FirstSimplexTableau.ObjectiveFunctionM[i];
/*      */       
/*      */ 
/* 2312 */       d.bound_operator[i] = this.CurrentSimplexTableau.Nonnegativity[i];
/* 2313 */       d.bound[i] = this.CurrentSimplexTableau.LowerBound[i];
/* 2314 */       d.is_artificial[i] = this.CurrentSimplexTableau.AnArtificial[i];
/*      */     }
/*      */     
/* 2317 */     for (i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 2318 */       d.basic_variable_index[i] = ((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]));
/*      */       
/* 2320 */       d.first_basic_variable_index[i] = ((int)Math.round(this.FirstSimplexTableau.BasicVariable[i]));
/*      */       
/* 2322 */       d.constrain_operator[i] = this.CurrentSimplexTableau.Constraint[i];
/* 2323 */       d.constrain_coefficient[i][0] = this.CurrentSimplexTableau.RightHandSide[i];
/*      */       
/* 2325 */       d.first_constrain_coefficient[i][0] = this.FirstSimplexTableau.RightHandSide[i];
/*      */       
/* 2327 */       for (int j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/* 2328 */         d.constrain_coefficient[i][j] = this.CurrentSimplexTableau.T[i][j];
/* 2329 */         d.first_constrain_coefficient[i][j] = this.FirstSimplexTableau.T[i][j];
/*      */       }
/*      */     }
/*      */     
/* 2333 */     d.Alpha = this.Alpha;
/*      */     
/*      */ 
/* 2336 */     d.variableValeV = null;d.variableValeV = new Vector();
/* 2337 */     d.variableValeV = this.variableValeV;
/*      */     
/*      */ 
/* 2340 */     d.SlackV = null;d.SlackV = new Vector();
/* 2341 */     d.SlackV = this.SlackV;
/* 2342 */     d.ShadowPriceV = null;d.ShadowPriceV = new Vector();
/* 2343 */     d.ShadowPriceV = this.ShadowPriceV;
/*      */     
/*      */ 
/* 2346 */     d.minArr = null;d.minArr = this.minArr;
/* 2347 */     d.maxArr = null;d.maxArr = this.maxArr;
/* 2348 */     d.minRHSArr = null;d.minRHSArr = this.minRHSArr;
/* 2349 */     d.maxRHSArr = null;d.maxRHSArr = this.maxRHSArr;
/* 2350 */     d.coefficientsArr = null;d.coefficientsArr = this.SimplexModel.ObjectiveFunction;
/* 2351 */     this.coefficientsArr = null;this.coefficientsArr = this.SimplexModel.ObjectiveFunction;
/*      */     
/* 2353 */     d.RHSCurrentValueArr = null;d.RHSCurrentValueArr = this.SimplexModel.RightHandSide;
/* 2354 */     this.RHSCurrentValueArr = null;this.RHSCurrentValueArr = this.SimplexModel.RightHandSide;
/* 2355 */     d.outputInfo = this.outputInfo;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setBookmark()
/*      */   {
/* 2362 */     this.FirstSimplexTableau = ((ORSolverBase.SimplexTableauType)this.CurrentSimplexTableau.clone());
/*      */   }
/*      */   
/*      */ 
/*      */   public void setBookmarkSens()
/*      */   {
/* 2368 */     this.CommandNumber = 7;
/* 2369 */     SimplexDoPutSlacksInTheirProperPlace();
/* 2370 */     this.CurrentSimplexSensitivity = new ORSolverBase.SimplexSensitivityType(this);
/*      */     
/* 2372 */     this.FirstSimplexTableau_bak = ((ORSolverBase.SimplexTableauType)this.FirstSimplexTableau.clone());
/*      */     
/* 2374 */     this.CurrentSimplexTableau_bak = ((ORSolverBase.SimplexTableauType)this.CurrentSimplexTableau.clone());
/*      */     
/*      */ 
/* 2377 */     this.SensitivityConvertToProperFormPerformed = false;
/* 2378 */     this.SensitivitySimplexPerformed = false;
/* 2379 */     this.SensitivityDualSimplexPerformed = false;
/*      */   }
/*      */   
/*      */ 
/*      */   public void reset()
/*      */   {
/* 2385 */     this.CurrentSimplexTableau = ((ORSolverBase.SimplexTableauType)this.FirstSimplexTableau.clone());
/*      */   }
/*      */   
/*      */ 
/*      */   public void resetSens()
/*      */   {
/* 2391 */     this.FirstSimplexTableau = ((ORSolverBase.SimplexTableauType)this.FirstSimplexTableau_bak.clone());
/*      */     
/* 2393 */     this.CurrentSimplexTableau = ((ORSolverBase.SimplexTableauType)this.CurrentSimplexTableau_bak.clone());
/*      */     
/* 2395 */     this.CurrentSimplexSensitivity = new ORSolverBase.SimplexSensitivityType(this);
/* 2396 */     this.SensitivityConvertToProperFormPerformed = false;
/* 2397 */     this.SensitivitySimplexPerformed = false;
/* 2398 */     this.SensitivityDualSimplexPerformed = false;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean doWork(IOROperation op)
/*      */   {
/* 2404 */     Vector p = op.parameters;
/*      */     
/*      */ 
/* 2407 */     double[][] Matrix = new double[11][11];
/* 2408 */     double[] Vector = new double[11];
/* 2409 */     double[] Result = new double[11];
/*      */     
/* 2411 */     double[] Vector2 = new double[11];
/*      */     
/*      */     int j;
/* 2414 */     switch (op.operation_code) {
/*      */     case 10101: 
/* 2416 */       this.CommandNumber = 2;
/* 2417 */       InitializeSimplexModel();
/*      */       
/*      */ 
/*      */ 
/* 2421 */       this.SimplexModel.NumVariables = ((Integer)p.elementAt(0)).intValue();
/*      */       
/*      */ 
/* 2424 */       this.SimplexModel.NumConstraints = ((Integer)p.elementAt(1)).intValue();
/*      */       
/*      */ 
/* 2427 */       boolean isMax = ((Boolean)p.elementAt(2)).booleanValue();
/* 2428 */       this.SimplexModel.Objective = (isMax ? 0 : 1);
/*      */       
/* 2430 */       double[] c = (double[])p.elementAt(3);
/* 2431 */       for (int i = 0; i < c.length; i++) {
/* 2432 */         this.SimplexModel.ObjectiveFunction[(i + 1)] = c[i];
/*      */       }
/* 2434 */       double[][] c2 = (double[][])p.elementAt(4);
/* 2435 */       for (i = 0; i < c2.length; i++) {
/* 2436 */         for (int j = 0; j < c2[i].length - 1; j++)
/* 2437 */           this.SimplexModel.A[(i + 1)][(j + 1)] = c2[i][(j + 1)];
/* 2438 */         this.SimplexModel.RightHandSide[(i + 1)] = c2[i][0];
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2443 */       int[] o = (int[])p.elementAt(5);
/* 2444 */       for (i = 0; i < o.length; i++) {
/* 2445 */         this.SimplexModel.Constraint[(i + 1)] = o[i];
/*      */       }
/*      */       
/* 2448 */       boolean[] b = (boolean[])p.elementAt(6);
/* 2449 */       for (i = 0; i < b.length; i++) {
/* 2450 */         this.SimplexModel.Nonnegativity[(i + 1)] = b[i];
/*      */       }
/*      */       
/* 2453 */       double[] rhs = (double[])p.elementAt(7);
/* 2454 */       for (i = 0; i < rhs.length; i++) {
/* 2455 */         this.SimplexModel.LowerBound[(i + 1)] = rhs[i];
/*      */       }
/* 2457 */       InitializeFirstSimplexTableau();
/* 2458 */       break;
/*      */     
/*      */ 
/*      */     case 10523: 
/* 2462 */       RevisedSimplexMethod(true);
/*      */       
/* 2464 */       OutputVariables(this.CurrentSimplexTableau);
/* 2465 */       OutputLPResult();
/*      */       
/* 2467 */       this.maxRHSArr = null;this.maxRHSArr = new double[this.LPSolution.MaxRHS.length];
/* 2468 */       this.minRHSArr = null;this.minRHSArr = new double[this.LPSolution.MinRHS.length];
/* 2469 */       for (int i = 0; i < this.maxRHSArr.length; i++) {
/* 2470 */         this.maxRHSArr[i] = this.LPSolution.MaxRHS[i];
/* 2471 */         this.minRHSArr[i] = this.LPSolution.MinRHS[i];
/*      */       }
/*      */       
/* 2474 */       this.minArr = null;this.minArr = new double[this.LPSolution.MinObjective.length];
/* 2475 */       this.maxArr = null;this.maxArr = new double[this.LPSolution.MaxObjective.length];
/* 2476 */       for (i = 0; i < this.maxRHSArr.length; i++) {
/* 2477 */         this.minArr[i] = this.LPSolution.MinObjective[i];
/* 2478 */         this.maxArr[i] = this.LPSolution.MaxObjective[i];
/*      */       }
/*      */       
/* 2481 */       System.out.println(String.valueOf(String.valueOf(new StringBuffer("Min(x1)=").append(this.LPSolution.MinObjective[1]).append(", Min(x2)=").append(this.LPSolution.MinObjective[2]).append(", Min(x3)=").append(this.LPSolution.MinObjective[3]))));
/*      */       
/*      */ 
/* 2484 */       System.out.println(String.valueOf(String.valueOf(new StringBuffer("Max(x1)=").append(this.LPSolution.MaxObjective[1]).append(", Max(x2)=").append(this.LPSolution.MaxObjective[2]).append(", Max(x3)=").append(this.LPSolution.MaxObjective[3]))));
/*      */       
/*      */ 
/* 2487 */       System.out.println(String.valueOf(String.valueOf(new StringBuffer("MinRHS(c1)=").append(this.LPSolution.MinRHS[1]).append(", MinRHS(c2)=").append(this.LPSolution.MinRHS[2]))));
/*      */       
/* 2489 */       System.out.println(String.valueOf(String.valueOf(new StringBuffer("MaxRHS(c1)=").append(this.LPSolution.MaxRHS[1]).append(", MaxRHS(c2)=").append(this.LPSolution.MaxRHS[2]))));
/*      */       
/*      */ 
/* 2492 */       break;
/*      */     
/*      */     case 10408: 
/* 2495 */       this.CommandNumber = 6;
/* 2496 */       break;
/*      */     
/*      */     case 10409: 
/* 2499 */       this.CommandNumber = 8;
/* 2500 */       break;
/*      */     
/*      */     case 10102: 
/* 2503 */       InitializeFirstSimplexTableau();
/* 2504 */       break;
/*      */     
/*      */     case 10211: 
/* 2507 */       this.CommandNumber = 5;
/* 2508 */       InitializeFirstSimplexTableau();
/* 2509 */       break;
/*      */     
/*      */     case 10212: 
/* 2512 */       this.FirstSimplexTableau_bak_for_Setup = ((ORSolverBase.SimplexTableauType)this.FirstSimplexTableau.clone());
/*      */       
/* 2514 */       this.CurrentSimplexTableau_bak_for_Setup = ((ORSolverBase.SimplexTableauType)this.CurrentSimplexTableau.clone());
/*      */       
/* 2516 */       break;
/*      */     
/*      */     case 10407: 
/* 2519 */       this.CommandNumber = 6;
/* 2520 */       this.FirstSimplexTableau = ((ORSolverBase.SimplexTableauType)this.FirstSimplexTableau_bak_for_Setup.clone());
/*      */       
/* 2522 */       this.CurrentSimplexTableau = ((ORSolverBase.SimplexTableauType)this.CurrentSimplexTableau_bak_for_Setup.clone());
/*      */       
/* 2524 */       this.isFirstSolverStep = true;
/* 2525 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 10201: 
/* 2531 */       int constraintNo = ((Integer)p.elementAt(0)).intValue();
/* 2532 */       if (constraintNo > 0) {
/* 2533 */         if (this.CurrentSimplexTableau.NumVariables < 10) {
/* 2534 */           this.CurrentSimplexTableau.NumVariables += 1;
/*      */           
/* 2536 */           this.CurrentSimplexTableau.Constraint[constraintNo] = 1;
/* 2537 */           for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; 
/* 2538 */               i++) {
/* 2539 */             if (i == constraintNo) {
/* 2540 */               this.CurrentSimplexTableau.T[constraintNo][this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */             }
/*      */             else {
/* 2543 */               this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.NumVariables] = 0.0D;
/*      */             }
/*      */           }
/* 2546 */           this.CurrentSimplexTableau.ObjectiveFunction[this.CurrentSimplexTableau.NumVariables] = 0.0D;
/*      */           
/* 2548 */           this.CurrentSimplexTableau.BasicVariable[constraintNo] = this.CurrentSimplexTableau.NumVariables;
/*      */         }
/*      */         else
/*      */         {
/* 2552 */           this.errInfo = "If you add another variables, then this problem will be too large to be solved by the Solve Interactively by the Simplex Method routine.";
/* 2553 */           return false;
/*      */         }
/*      */       }
/*      */       else {
/* 2557 */         this.errInfo = "Adding slack variable to objective function is invalid!";
/*      */         
/* 2559 */         return false;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       break;
/*      */     case 10202: 
/* 2567 */       int constraintNo = ((Integer)p.elementAt(0)).intValue();
/* 2568 */       if (constraintNo > 0) {
/* 2569 */         if (this.CurrentSimplexTableau.NumVariables < 10) {
/* 2570 */           this.CurrentSimplexTableau.NumVariables += 1;
/*      */           
/* 2572 */           this.CurrentSimplexTableau.Constraint[constraintNo] = 1;
/* 2573 */           for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; 
/* 2574 */               i++) {
/* 2575 */             if (i == constraintNo) {
/* 2576 */               this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.NumVariables] = -1.0D;
/*      */             }
/*      */             else {
/* 2579 */               this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.NumVariables] = 0.0D;
/*      */             }
/*      */           }
/* 2582 */           if (Math.round(this.CurrentSimplexTableau.BasicVariable[constraintNo]) == 0)
/*      */           {
/* 2584 */             this.CurrentSimplexTableau.BasicVariable[constraintNo] = this.CurrentSimplexTableau.NumVariables;
/*      */           }
/* 2586 */           this.CurrentSimplexTableau.ObjectiveFunction[this.CurrentSimplexTableau.NumVariables] = 0.0D;
/*      */         }
/*      */         else
/*      */         {
/* 2590 */           this.errInfo = "If you add another variables, then this problem will be too large to be solved by the Solve Interactively by the Simplex Method routine.";
/* 2591 */           return false;
/*      */         }
/*      */       }
/*      */       else {
/* 2595 */         this.errInfo = "Adding slack variable to objective function is invalid!";
/*      */         
/* 2597 */         return false;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       break;
/*      */     case 10203: 
/* 2605 */       int constraintNo = ((Integer)p.elementAt(0)).intValue();
/* 2606 */       if (constraintNo > 0) {
/* 2607 */         if (this.CurrentSimplexTableau.NumVariables < 10) {
/* 2608 */           this.CurrentSimplexTableau.NumVariables += 1;
/*      */           
/* 2610 */           this.CurrentSimplexTableau.Constraint[constraintNo] = 1;
/* 2611 */           for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; 
/* 2612 */               i++) {
/* 2613 */             if (i == constraintNo) {
/* 2614 */               this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */             }
/*      */             else {
/* 2617 */               this.CurrentSimplexTableau.T[i][this.CurrentSimplexTableau.NumVariables] = 0.0D;
/*      */             }
/*      */           }
/* 2620 */           if (this.CurrentSimplexTableau.Objective == 1) {
/* 2621 */             this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.NumVariables] = -1.0D;
/*      */           }
/*      */           else {
/* 2624 */             this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */           }
/* 2626 */           this.CurrentSimplexTableau.AnArtificial[this.CurrentSimplexTableau.NumVariables] = true;
/*      */           
/* 2628 */           this.CurrentSimplexTableau.ContainsArtificials = true;
/* 2629 */           this.CurrentSimplexTableau.BasicVariable[constraintNo] = this.CurrentSimplexTableau.NumVariables;
/*      */         }
/*      */         else
/*      */         {
/* 2633 */           this.errInfo = "If you add another variables, then this problem will be too large to be solved by the Solve Interactively by the Simplex Method routine.";
/* 2634 */           return false;
/*      */         }
/*      */       }
/*      */       else {
/* 2638 */         this.errInfo = "Adding slack variable to objective function is invalid!";
/*      */         
/* 2640 */         return false;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       break;
/*      */     case 10204: 
/* 2648 */       int constraintNo = ((Integer)p.elementAt(0)).intValue();
/* 2649 */       if (constraintNo == 0) {
/* 2650 */         if (this.CurrentSimplexTableau.Objective == 0) {
/* 2651 */           this.CurrentSimplexTableau.Objective = 1;
/*      */         } else
/* 2653 */           this.CurrentSimplexTableau.Objective = 0;
/* 2654 */         for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 2655 */           this.CurrentSimplexTableau.ObjectiveFunction[i] = (-this.CurrentSimplexTableau.ObjectiveFunction[i]);
/*      */           
/* 2657 */           this.CurrentSimplexTableau.ObjectiveFunctionM[i] = (-this.CurrentSimplexTableau.ObjectiveFunctionM[i]);
/*      */         }
/*      */         
/* 2660 */         this.CurrentSimplexTableau.Z = (-this.CurrentSimplexTableau.Z);
/* 2661 */         this.CurrentSimplexTableau.ZM = (-this.CurrentSimplexTableau.ZM);
/*      */       }
/*      */       else {
/* 2664 */         if (this.CurrentSimplexTableau.Constraint[constraintNo] == 0) {
/* 2665 */           this.CurrentSimplexTableau.Constraint[constraintNo] = 2;
/*      */         }
/* 2667 */         else if (this.CurrentSimplexTableau.Constraint[constraintNo] == 2)
/*      */         {
/* 2669 */           this.CurrentSimplexTableau.Constraint[constraintNo] = 0; }
/* 2670 */         for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 2671 */           this.CurrentSimplexTableau.T[constraintNo][i] = (-this.CurrentSimplexTableau.T[constraintNo][i]);
/*      */         }
/* 2673 */         this.CurrentSimplexTableau.RightHandSide[constraintNo] = (-this.CurrentSimplexTableau.RightHandSide[constraintNo]);
/*      */       }
/*      */       
/* 2676 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     case 10205: 
/* 2683 */       int WhichVar = ((Integer)p.elementAt(0)).intValue();
/* 2684 */       if (this.CurrentSimplexTableau.NumVariables < 10) {
/* 2685 */         this.CurrentSimplexTableau.NumVariables += 1;
/*      */         
/* 2687 */         if (WhichVar != this.CurrentSimplexTableau.NumVariables - 1) {
/* 2688 */           for (int i = this.CurrentSimplexTableau.NumVariables; 
/* 2689 */               i >= WhichVar + 2; i--) {
/* 2690 */             this.CurrentSimplexTableau.AnArtificial[i] = this.CurrentSimplexTableau.AnArtificial[(i - 1)];
/*      */             
/* 2692 */             this.CurrentSimplexTableau.ObjectiveFunction[i] = this.CurrentSimplexTableau.ObjectiveFunction[(i - 1)];
/*      */             
/* 2694 */             this.CurrentSimplexTableau.ObjectiveFunctionM[i] = this.CurrentSimplexTableau.ObjectiveFunctionM[(i - 1)];
/*      */             
/* 2696 */             for (int j = 1; 
/* 2697 */                 j <= this.CurrentSimplexTableau.NumConstraints; j++) {
/* 2698 */               this.CurrentSimplexTableau.T[j][i] = this.CurrentSimplexTableau.T[j][(i - 1)];
/*      */             }
/* 2700 */             this.CurrentSimplexTableau.Nonnegativity[i] = this.CurrentSimplexTableau.Nonnegativity[(i - 1)];
/*      */             
/* 2702 */             this.CurrentSimplexTableau.LowerBound[i] = this.CurrentSimplexTableau.LowerBound[(i - 1)];
/*      */             
/* 2704 */             for (j = 1; 
/* 2705 */                 j <= this.CurrentSimplexTableau.NumConstraints; j++) {
/* 2706 */               if (this.CurrentSimplexTableau.BasicVariable[j] == i - 1)
/*      */               {
/* 2708 */                 this.CurrentSimplexTableau.BasicVariable[j] = i; }
/*      */             }
/*      */           }
/*      */         }
/* 2712 */         this.CurrentSimplexTableau.AnArtificial[(WhichVar + 1)] = this.CurrentSimplexTableau.AnArtificial[WhichVar];
/*      */         
/* 2714 */         this.CurrentSimplexTableau.ObjectiveFunction[(WhichVar + 1)] = (-this.CurrentSimplexTableau.ObjectiveFunction[WhichVar]);
/*      */         
/* 2716 */         this.CurrentSimplexTableau.ObjectiveFunctionM[(WhichVar + 1)] = (-this.CurrentSimplexTableau.ObjectiveFunctionM[WhichVar]);
/*      */         
/* 2718 */         for (int j = 1; j <= this.CurrentSimplexTableau.NumConstraints; j++) {
/* 2719 */           this.CurrentSimplexTableau.T[j][(WhichVar + 1)] = (-this.CurrentSimplexTableau.T[j][WhichVar]);
/*      */         }
/* 2721 */         this.CurrentSimplexTableau.Nonnegativity[WhichVar] = true;
/* 2722 */         this.CurrentSimplexTableau.LowerBound[WhichVar] = 0.0D;
/* 2723 */         this.CurrentSimplexTableau.Nonnegativity[(WhichVar + 1)] = true;
/* 2724 */         this.CurrentSimplexTableau.LowerBound[(WhichVar + 1)] = 0.0D;
/*      */       }
/*      */       else {
/* 2727 */         this.errInfo = "If you add another variables, then this problem will be too large to be solved by the Solve Interactively by the Simplex Method routine.";
/* 2728 */         return false;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       break;
/*      */     case 10206: 
/* 2737 */       if (this.CurrentSimplexTableau.ContainsConstant) {
/* 2738 */         this.errInfo = "You must press 7 to carry the current constant over to the right-hand side before pressing 6 again to introduce a new constant.";
/* 2739 */         return false;
/*      */       }
/*      */       
/* 2742 */       this.CurrentSimplexTableau.VariableWithConstant = ((Integer)p.elementAt(0)).intValue();
/*      */       
/* 2744 */       this.CurrentSimplexTableau.Constant = ((Double)p.elementAt(1)).doubleValue();
/*      */       
/* 2746 */       this.CurrentSimplexTableau.LowerBound[this.CurrentSimplexTableau.VariableWithConstant] -= this.CurrentSimplexTableau.Constant;
/*      */       
/*      */ 
/*      */ 
/* 2750 */       this.CurrentSimplexTableau.ContainsConstant = true;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2756 */       if (this.CurrentSimplexTableau.ContainsConstant) {
/* 2757 */         this.CurrentSimplexTableau.ContainsConstant = false;
/* 2758 */         this.CurrentSimplexTableau.Z -= this.CurrentSimplexTableau.Constant * this.CurrentSimplexTableau.ObjectiveFunction[this.CurrentSimplexTableau.VariableWithConstant];
/*      */         
/*      */ 
/*      */ 
/* 2762 */         this.CurrentSimplexTableau.ZM -= this.CurrentSimplexTableau.Constant * this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.VariableWithConstant];
/*      */         
/*      */ 
/*      */ 
/* 2766 */         for (j = 1; j <= this.CurrentSimplexTableau.NumConstraints;) {
/* 2767 */           this.CurrentSimplexTableau.RightHandSide[j] -= this.CurrentSimplexTableau.Constant * this.CurrentSimplexTableau.T[j][this.CurrentSimplexTableau.VariableWithConstant];j++; continue;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2776 */           this.CurrentSimplexTableau.ContainsArtificials = false;
/* 2777 */           InitializeFirstSimplexTableau();
/* 2778 */           break;
/*      */           
/*      */ 
/* 2781 */           this.is_big_M = ((Boolean)p.elementAt(0)).booleanValue();
/* 2782 */           if (this.CurrentSimplexTableau.ContainsArtificials) {
/* 2783 */             if (this.is_big_M == false) {
/* 2784 */               for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 2785 */                 this.CurrentSimplexTableau.ObjectiveFunction[i] = 0.0D;
/*      */               }
/*      */             }
/*      */           } else {
/* 2789 */             this.errInfo = "There are no artificial variables, so can't select BigM or TwoPhase Method.";
/*      */             
/* 2791 */             return false;
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 2796 */             this.CurrentSimplexTableau.Objective = 0;
/* 2797 */             for (i = 1; i <= this.CurrentSimplexTableau.NumVariables;) {
/* 2798 */               this.CurrentSimplexTableau.ObjectiveFunctionM[i] = 0.0D;
/* 2799 */               if (this.CurrentSimplexTableau.AnArtificial[i] != 0) {
/* 2800 */                 this.CurrentSimplexTableau.ObjectiveFunction[i] = 1.0D;
/*      */               } else {
/* 2802 */                 this.CurrentSimplexTableau.ObjectiveFunction[i] = 0.0D;
/*      */               }
/* 2797 */               i++; continue;
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2807 */               RemoveTheArtificials();
/* 2808 */               break;
/*      */               
/*      */ 
/*      */ 
/* 2812 */               this.CurrentSimplexTableau.LeavingBasicVariableEquation = ((Integer)p.elementAt(0)).intValue();
/*      */               
/* 2814 */               this.CurrentSimplexTableau.MultipleM = ((Double)p.elementAt(1)).doubleValue();
/*      */               
/* 2816 */               this.CurrentSimplexTableau.Multiple[0] = ((Double)p.elementAt(2)).doubleValue();
/*      */               
/* 2818 */               if ((Math.abs(this.CurrentSimplexTableau.ObjectiveFunction[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.LeavingBasicVariableEquation]))]) < 0.01D) && (Math.abs(this.CurrentSimplexTableau.ObjectiveFunctionM[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.LeavingBasicVariableEquation]))]) < 0.01D))
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2828 */                 this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 2829 */                 return false;
/*      */               }
/* 2831 */               boolean Correct = true;
/* 2832 */               if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunctionM[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.LeavingBasicVariableEquation]))] - this.CurrentSimplexTableau.MultipleM * this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][((int)Math.round(this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.LeavingBasicVariableEquation]))]) > 0.01D)
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2841 */                 Correct = false; }
/* 2842 */               if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunction[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.LeavingBasicVariableEquation]))] - this.CurrentSimplexTableau.Multiple[0] * this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][((int)Math.round(this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.LeavingBasicVariableEquation]))]) > 0.01D)
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2851 */                 Correct = false;
/*      */               }
/* 2853 */               if (Correct) {
/* 2854 */                 for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 2855 */                   this.CurrentSimplexTableau.ObjectiveFunctionM[i] -= this.CurrentSimplexTableau.MultipleM * this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][i];
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/* 2860 */                   this.CurrentSimplexTableau.ObjectiveFunction[i] -= this.CurrentSimplexTableau.Multiple[0] * this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][i];
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/* 2866 */                 this.CurrentSimplexTableau.Z -= this.CurrentSimplexTableau.Multiple[0] * this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.LeavingBasicVariableEquation];
/*      */                 
/*      */ 
/*      */ 
/* 2870 */                 this.CurrentSimplexTableau.ZM -= this.CurrentSimplexTableau.MultipleM * this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.LeavingBasicVariableEquation];
/*      */ 
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/* 2877 */                 this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 2878 */                 return false;
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2888 */                 int[] ia = (int[])p.elementAt(0);
/*      */                 
/* 2890 */                 if ((this.isFirstSolverStep) && (this.CommandNumber != 8))
/*      */                 {
/* 2892 */                   boolean Correct = true;
/*      */                   
/* 2894 */                   SimplexDoFindEBV();
/* 2895 */                   if (this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.EnteringBasicVariable] < this.CurrentSimplexTableau.ObjectiveFunctionM[ia[1]] - 0.1D)
/*      */                   {
/*      */ 
/* 2898 */                     Correct = false;
/* 2899 */                   } else if ((this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.EnteringBasicVariable] < this.CurrentSimplexTableau.ObjectiveFunctionM[ia[1]] + 0.1D) && (this.CurrentSimplexTableau.ObjectiveFunction[this.CurrentSimplexTableau.EnteringBasicVariable] < this.CurrentSimplexTableau.ObjectiveFunction[ia[1]] - 0.1D))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2907 */                     Correct = false; }
/* 2908 */                   if (!Correct) {
/* 2909 */                     this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*      */                     
/* 2911 */                     return false;
/*      */                   }
/*      */                   
/*      */ 
/* 2915 */                   SimplexDoFindLBVE();
/* 2916 */                   if (this.CurrentSimplexTableau.LeavingBasicVariableEquation != ia[0])
/*      */                   {
/* 2918 */                     if (this.CurrentSimplexTableau.T[ia[0]][ia[1]] <= 0) {
/* 2919 */                       Correct = false;
/* 2920 */                     } else if (this.CurrentSimplexTableau.RightHandSide[ia[0]] / this.CurrentSimplexTableau.T[ia[0]][ia[1]] > this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.LeavingBasicVariableEquation] / this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][ia[1]] + 0.1D)
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2927 */                       Correct = false; }
/* 2928 */                     if (!Correct) {
/* 2929 */                       this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*      */                       
/* 2931 */                       return false;
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/* 2936 */                   this.CurrentSimplexTableau.Multiple[ia[0]] = ((Double)p.elementAt(1)).doubleValue();
/*      */                   
/* 2938 */                   if ((this.CurrentSimplexTableau.Multiple[ia[0]] < 0.01D) || (Math.abs(1 - this.CurrentSimplexTableau.T[ia[0]][ia[1]] / this.CurrentSimplexTableau.Multiple[ia[0]]) > 0.1D))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/* 2943 */                     this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*      */                     
/* 2945 */                     return false;
/*      */                   }
/*      */                 }
/*      */                 
/* 2949 */                 this.CurrentSimplexTableau.EnteringBasicVariable = ia[1];
/* 2950 */                 this.CurrentSimplexTableau.LeavingBasicVariableEquation = ia[0];
/* 2951 */                 this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.LeavingBasicVariableEquation] = this.CurrentSimplexTableau.EnteringBasicVariable;
/*      */                 
/*      */ 
/*      */ 
/* 2955 */                 int nRow = this.CurrentSimplexTableau.LeavingBasicVariableEquation;
/* 2956 */                 int nCol = this.CurrentSimplexTableau.EnteringBasicVariable;
/* 2957 */                 this.CurrentSimplexTableau.Multiple[nRow] = ((Double)p.elementAt(1)).doubleValue();
/*      */                 
/*      */ 
/* 2960 */                 if (Math.abs(this.CurrentSimplexTableau.Multiple[nRow] - this.CurrentSimplexTableau.T[nRow][nCol]) < 0.1D)
/*      */                 {
/* 2962 */                   this.CurrentSimplexTableau.Multiple[nRow] = this.CurrentSimplexTableau.T[nRow][nCol];
/*      */                 }
/* 2964 */                 if (Math.abs(this.CurrentSimplexTableau.Multiple[nRow]) > 1.0E-4D) {
/* 2965 */                   for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 2966 */                     this.CurrentSimplexTableau.T[nRow][i] /= this.CurrentSimplexTableau.Multiple[nRow];
/*      */                   }
/*      */                 }
/*      */                 
/* 2970 */                 if (Math.abs(this.CurrentSimplexTableau.Multiple[nRow]) > 1.0E-4D) {
/* 2971 */                   this.CurrentSimplexTableau.RightHandSide[nRow] /= this.CurrentSimplexTableau.Multiple[nRow]; break;
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2981 */                   double[] p0 = (double[])p.elementAt(0);
/* 2982 */                   double[] p1 = (double[])p.elementAt(1);
/*      */                   
/*      */ 
/* 2985 */                   this.CurrentSimplexTableau.Multiple[0] = p1[0];
/* 2986 */                   this.CurrentSimplexTableau.MultipleM = p0[0];
/* 2987 */                   if ((this.isFirstSolverStep) && (this.CommandNumber != 8) && ((Math.abs(this.CurrentSimplexTableau.MultipleM - this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.EnteringBasicVariable]) > 0.1D) || (Math.abs(this.CurrentSimplexTableau.Multiple[0] - this.CurrentSimplexTableau.ObjectiveFunction[this.CurrentSimplexTableau.EnteringBasicVariable]) > 0.1D)))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2998 */                     this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 2999 */                     return false;
/*      */                   }
/*      */                   
/*      */ 
/* 3003 */                   for (int nRow = 1; nRow <= this.CurrentSimplexTableau.NumConstraints; 
/* 3004 */                       nRow++) {
/* 3005 */                     if (nRow != this.CurrentSimplexTableau.LeavingBasicVariableEquation)
/*      */                     {
/*      */ 
/* 3008 */                       this.CurrentSimplexTableau.Multiple[nRow] = p1[nRow];
/* 3009 */                       int nCol = this.CurrentSimplexTableau.EnteringBasicVariable;
/* 3010 */                       if ((this.isFirstSolverStep) && (this.CommandNumber != 8) && (Math.abs(this.CurrentSimplexTableau.T[nRow][nCol] - this.CurrentSimplexTableau.Multiple[nRow] * this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][nCol]) > 0.1D))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3016 */                         this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*      */                         
/* 3018 */                         return false;
/*      */                       }
/*      */                     }
/*      */                   }
/* 3022 */                   if (this.CurrentSimplexTableau.MultipleM - this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.EnteringBasicVariable] < 0.1D)
/*      */                   {
/*      */ 
/* 3025 */                     this.CurrentSimplexTableau.MultipleM = this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexTableau.EnteringBasicVariable];
/*      */                   }
/*      */                   
/* 3028 */                   if (this.CurrentSimplexTableau.Multiple[0] - this.CurrentSimplexTableau.ObjectiveFunction[this.CurrentSimplexTableau.EnteringBasicVariable] < 0.1D)
/*      */                   {
/*      */ 
/* 3031 */                     this.CurrentSimplexTableau.Multiple[0] = this.CurrentSimplexTableau.ObjectiveFunction[this.CurrentSimplexTableau.EnteringBasicVariable];
/*      */                   }
/*      */                   
/* 3034 */                   for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 3035 */                     this.CurrentSimplexTableau.ObjectiveFunction[i] -= this.CurrentSimplexTableau.Multiple[0] * this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][i];
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 3040 */                     this.CurrentSimplexTableau.ObjectiveFunctionM[i] -= this.CurrentSimplexTableau.MultipleM * this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][i];
/*      */                   }
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/* 3046 */                   this.CurrentSimplexTableau.Z -= this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.LeavingBasicVariableEquation] * this.CurrentSimplexTableau.Multiple[0];
/*      */                   
/*      */ 
/*      */ 
/* 3050 */                   this.CurrentSimplexTableau.ZM -= this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.LeavingBasicVariableEquation] * this.CurrentSimplexTableau.MultipleM;
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/* 3055 */                   for (nRow = 1; nRow <= this.CurrentSimplexTableau.NumConstraints; 
/* 3056 */                       nRow++) {
/* 3057 */                     if (nRow != this.CurrentSimplexTableau.LeavingBasicVariableEquation)
/*      */                     {
/*      */ 
/* 3060 */                       this.CurrentSimplexTableau.Multiple[nRow] = p1[nRow];
/* 3061 */                       int nCol = this.CurrentSimplexTableau.EnteringBasicVariable;
/*      */                       
/* 3063 */                       if (Math.abs(this.CurrentSimplexTableau.Multiple[nRow] - this.CurrentSimplexTableau.T[nRow][nCol]) < 0.1D)
/*      */                       {
/* 3065 */                         this.CurrentSimplexTableau.Multiple[nRow] = this.CurrentSimplexTableau.T[nRow][nCol];
/*      */                       }
/* 3067 */                       for (i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 3068 */                         this.CurrentSimplexTableau.T[nRow][i] -= this.CurrentSimplexTableau.Multiple[nRow] * this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.LeavingBasicVariableEquation][i];
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/* 3073 */                       this.CurrentSimplexTableau.RightHandSide[nRow] -= this.CurrentSimplexTableau.Multiple[nRow] * this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.LeavingBasicVariableEquation];
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/* 3080 */                   this.isFirstSolverStep = false;
/* 3081 */                   break;
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/* 3086 */                   if ((this.CurrentSimplexTableau.NumVariables == 10) || (this.CurrentSimplexTableau.NumConstraints == 6))
/*      */                   {
/* 3088 */                     this.errInfo = "This option cannot be pursued because it requires adding a constraint and a variable, and this would make the problem too large to be solved by this procedure.";
/* 3089 */                     return false;
/*      */                   }
/*      */                   
/* 3092 */                   this.CurrentSimplexTableau.NumVariables += 1;
/*      */                   
/* 3094 */                   this.CurrentSimplexTableau.NumConstraints += 1;
/*      */                   
/* 3096 */                   this.CurrentSimplexTableau.AnArtificial[this.CurrentSimplexTableau.NumVariables] = false;
/*      */                   
/* 3098 */                   for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 3099 */                     if (this.CurrentSimplexTableau.AnArtificial[i] != 0) {
/* 3100 */                       this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.NumConstraints][i] = 1.0D;
/*      */                     }
/*      */                     else {
/* 3103 */                       this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.NumConstraints][i] = 0.0D;
/*      */                     }
/*      */                   }
/* 3106 */                   this.CurrentSimplexTableau.T[this.CurrentSimplexTableau.NumConstraints][this.CurrentSimplexTableau.NumVariables] = 1.0D;
/*      */                   
/* 3108 */                   this.CurrentSimplexTableau.RightHandSide[this.CurrentSimplexTableau.NumConstraints] = 0.0D;
/*      */                   
/* 3110 */                   this.CurrentSimplexTableau.BasicVariable[this.CurrentSimplexTableau.NumConstraints] = this.CurrentSimplexTableau.NumVariables;
/*      */                   
/*      */ 
/* 3113 */                   break;
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3119 */                   int Extra = 0;
/* 3120 */                   for (int i = 1; i <= this.SimplexModel.NumVariables; i++) {
/* 3121 */                     if (this.SimplexModel.Objective == 0) {
/* 3122 */                       this.CurrentSimplexTableau.ObjectiveFunction[(i + Extra)] = (-this.SimplexModel.ObjectiveFunction[i]);
/*      */                     }
/*      */                     else {
/* 3125 */                       this.CurrentSimplexTableau.ObjectiveFunction[(i + Extra)] = this.SimplexModel.ObjectiveFunction[i];
/*      */                     }
/* 3127 */                     this.CurrentSimplexTableau.ObjectiveFunctionM[(i + Extra)] = 0.0D;
/* 3128 */                     if (this.SimplexModel.Nonnegativity[i] == 0) {
/* 3129 */                       Extra += 1;
/* 3130 */                       if (this.SimplexModel.Objective == 0) {
/* 3131 */                         this.CurrentSimplexTableau.ObjectiveFunction[(i + Extra)] = this.SimplexModel.ObjectiveFunction[i];
/*      */                       }
/*      */                       else {
/* 3134 */                         this.CurrentSimplexTableau.ObjectiveFunction[(i + Extra)] = (-this.SimplexModel.ObjectiveFunction[i]);
/*      */                       }
/* 3136 */                       this.CurrentSimplexTableau.ObjectiveFunctionM[(i + Extra)] = 0.0D;
/*      */                     }
/*      */                   }
/* 3139 */                   this.CurrentSimplexTableau.Objective = 0;
/* 3140 */                   this.CurrentSimplexTableau.Z = 0.0D;
/* 3141 */                   this.CurrentSimplexTableau.ZM = 0.0D;
/* 3142 */                   i = this.SimplexModel.NumVariables + 1 + Extra;
/* 3143 */                   while (i <= this.CurrentSimplexTableau.NumVariables) {
/* 3144 */                     this.CurrentSimplexTableau.ObjectiveFunction[i] = 0.0D;
/* 3145 */                     this.CurrentSimplexTableau.ObjectiveFunctionM[i] = 0.0D;i++;continue;
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3152 */                     SimplexDoConvertToProperForm();
/* 3153 */                     this.CurrentSimplexTableau.Optimal = false;
/* 3154 */                     break;
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3161 */                     int[] ia = (int[])p.elementAt(0);
/* 3162 */                     if (ia[1] == this.FirstSimplexTableau.NumVariables + 1) {
/* 3163 */                       this.FirstSimplexTableau.RightHandSide[ia[0]] = ((Double)p.elementAt(1)).doubleValue();
/*      */ 
/*      */ 
/*      */                     }
/* 3167 */                     else if (ia[0] == 0) {
/* 3168 */                       this.FirstSimplexTableau.ObjectiveFunction[ia[1]] = ((Double)p.elementAt(1)).doubleValue();
/*      */                       
/* 3170 */                       this.FirstSimplexTableau.ObjectiveFunctionM[ia[1]] = 0.0D;
/*      */                     }
/*      */                     else {
/* 3173 */                       this.FirstSimplexTableau.T[ia[0]][ia[1]] = ((Double)p.elementAt(1)).doubleValue();
/*      */                       
/*      */ 
/* 3176 */                       break;
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3182 */                       int[] ia = (int[])p.elementAt(0);
/* 3183 */                       this.CurrentSimplexSensitivity.ChangeRow = ia[0];
/* 3184 */                       this.CurrentSimplexSensitivity.ChangeColumn = ia[1];
/* 3185 */                       break;
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/* 3190 */                       int[] ia = (int[])p.elementAt(0);
/* 3191 */                       this.CurrentSimplexSensitivity.MatrixRow = ia[0];
/* 3192 */                       this.CurrentSimplexSensitivity.MatrixColumn = ia[1];
/* 3193 */                       break;
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/* 3198 */                       int[] ia = (int[])p.elementAt(0);
/* 3199 */                       this.CurrentSimplexSensitivity.MatrixRow = ia[0];
/* 3200 */                       this.CurrentSimplexSensitivity.MatrixColumn = ia[1];
/* 3201 */                       break;
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/* 3206 */                       int[] ia = (int[])p.elementAt(0);
/* 3207 */                       this.CurrentSimplexSensitivity.VectorRow = ia[0];
/* 3208 */                       this.CurrentSimplexSensitivity.VectorColumn = ia[1];
/* 3209 */                       break;
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/* 3214 */                       int[] ia = (int[])p.elementAt(0);
/* 3215 */                       this.CurrentSimplexSensitivity.VectorRow = ia[0];
/* 3216 */                       this.CurrentSimplexSensitivity.VectorColumn = ia[1];
/*      */                       
/* 3218 */                       if (this.CurrentSimplexSensitivity.ChangeRow > 0) {
/* 3219 */                         for (i = 1; i <= this.FirstSimplexTableau.NumConstraints; i++) {
/* 3220 */                           for (int j = 1; j <= this.FirstSimplexTableau.NumConstraints; j++) {
/* 3221 */                             if (this.CurrentSimplexSensitivity.MatrixRow + i - 1 == 0)
/*      */                             {
/* 3223 */                               if (this.CurrentSimplexSensitivity.MatrixColumn + j - 1 > this.FirstSimplexTableau.NumVariables)
/*      */                               {
/* 3225 */                                 Matrix[i][j] = this.CurrentSimplexTableau.Z;
/*      */                               } else {
/* 3227 */                                 Matrix[i][j] = this.CurrentSimplexTableau.ObjectiveFunction[(this.CurrentSimplexSensitivity.MatrixColumn + j - 1)];
/*      */ 
/*      */                               }
/*      */                               
/*      */ 
/*      */                             }
/* 3233 */                             else if (this.CurrentSimplexSensitivity.MatrixColumn + j - 1 > this.FirstSimplexTableau.NumVariables)
/*      */                             {
/* 3235 */                               Matrix[i][j] = this.CurrentSimplexTableau.RightHandSide[i];
/*      */                             }
/*      */                             else {
/* 3238 */                               Matrix[i][j] = this.CurrentSimplexTableau.T[(this.CurrentSimplexSensitivity.MatrixRow + i - 1)][(this.CurrentSimplexSensitivity.MatrixColumn + j - 1)];
/*      */                             }
/*      */                           }
/*      */                         }
/*      */                         
/*      */ 
/*      */ 
/* 3245 */                         for (int j = 1; j <= this.FirstSimplexTableau.NumConstraints; j++) {
/* 3246 */                           if (this.CurrentSimplexSensitivity.VectorRow + j - 1 == 0) {
/* 3247 */                             if (this.CurrentSimplexSensitivity.VectorColumn > this.FirstSimplexTableau.NumVariables)
/*      */                             {
/* 3249 */                               Vector[j] = this.FirstSimplexTableau.Z;
/*      */                             } else {
/* 3251 */                               Vector[j] = this.FirstSimplexTableau.ObjectiveFunction[this.CurrentSimplexSensitivity.VectorColumn];
/*      */                             }
/*      */                             
/*      */ 
/*      */                           }
/* 3256 */                           else if (this.CurrentSimplexSensitivity.VectorColumn > this.FirstSimplexTableau.NumVariables)
/*      */                           {
/* 3258 */                             Vector[j] = this.FirstSimplexTableau.RightHandSide[j];
/*      */                           } else {
/* 3260 */                             Vector[j] = this.FirstSimplexTableau.T[(this.CurrentSimplexSensitivity.VectorRow + j - 1)][this.CurrentSimplexSensitivity.VectorColumn];
/*      */                           }
/*      */                         }
/*      */                         
/*      */ 
/* 3265 */                         MultiplyMatrixAndVector(Matrix, Vector, this.FirstSimplexTableau.NumConstraints, this.FirstSimplexTableau.NumConstraints, Result);
/*      */                         
/*      */ 
/*      */ 
/* 3269 */                         for (i = 1; i <= this.FirstSimplexTableau.NumConstraints;) {
/* 3270 */                           if (this.CurrentSimplexSensitivity.ChangeColumn > this.FirstSimplexTableau.NumVariables)
/*      */                           {
/* 3272 */                             this.CurrentSimplexTableau.RightHandSide[i] = Result[i];
/*      */                           } else {
/* 3274 */                             this.CurrentSimplexTableau.T[i][this.CurrentSimplexSensitivity.ChangeColumn] = Result[i];
/*      */                           }
/* 3269 */                           i++; continue;
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3284 */                           int[] ia = (int[])p.elementAt(0);
/* 3285 */                           this.CurrentSimplexSensitivity.ConstantRow = ia[0];
/* 3286 */                           this.CurrentSimplexSensitivity.ConstantColumn = ia[1];
/*      */                           
/* 3288 */                           for (int i = 1; i <= this.FirstSimplexTableau.NumConstraints; i++) {
/* 3289 */                             if (this.CurrentSimplexSensitivity.MatrixRow == 0) {
/* 3290 */                               Vector[i] = this.CurrentSimplexTableau.ObjectiveFunction[(i + this.CurrentSimplexSensitivity.MatrixColumn - 1)];
/*      */                             }
/*      */                             else {
/* 3293 */                               Vector[i] = this.CurrentSimplexTableau.T[this.CurrentSimplexSensitivity.MatrixRow][(i + this.CurrentSimplexSensitivity.MatrixColumn - 1)];
/*      */                             }
/*      */                             
/* 3296 */                             if (this.CurrentSimplexSensitivity.VectorRow + i - 1 == 0) {
/* 3297 */                               if (this.CurrentSimplexSensitivity.VectorColumn <= this.FirstSimplexTableau.NumVariables)
/*      */                               {
/* 3299 */                                 Vector2[i] = this.FirstSimplexTableau.ObjectiveFunction[this.CurrentSimplexSensitivity.VectorColumn];
/*      */                               }
/*      */                               else {
/* 3302 */                                 Vector2[i] = this.FirstSimplexTableau.Z;
/*      */                               }
/*      */                             }
/* 3305 */                             else if (this.CurrentSimplexSensitivity.VectorColumn <= this.FirstSimplexTableau.NumVariables)
/*      */                             {
/* 3307 */                               Vector2[i] = this.FirstSimplexTableau.T[(i + this.CurrentSimplexSensitivity.VectorRow - 1)][this.CurrentSimplexSensitivity.VectorColumn];
/*      */                             }
/*      */                             else
/*      */                             {
/* 3311 */                               Vector2[i] = this.FirstSimplexTableau.RightHandSide[(i + this.CurrentSimplexSensitivity.VectorRow - 1)]; }
/*      */                           }
/*      */                           double TheConstant;
/*      */                           double TheConstant;
/* 3315 */                           if (this.CurrentSimplexSensitivity.ConstantRow == 0) { double TheConstant;
/* 3316 */                             if (this.CurrentSimplexSensitivity.ConstantColumn <= this.FirstSimplexTableau.NumVariables)
/*      */                             {
/* 3318 */                               TheConstant = this.FirstSimplexTableau.ObjectiveFunction[this.CurrentSimplexSensitivity.ConstantColumn];
/*      */                             }
/*      */                             else
/* 3321 */                               TheConstant = this.FirstSimplexTableau.Z;
/*      */                           } else {
/*      */                             double TheConstant;
/* 3324 */                             if (this.CurrentSimplexSensitivity.ConstantColumn <= this.FirstSimplexTableau.NumVariables)
/*      */                             {
/* 3326 */                               TheConstant = this.FirstSimplexTableau.T[this.CurrentSimplexSensitivity.ConstantRow][this.CurrentSimplexSensitivity.ConstantColumn];
/*      */                             }
/*      */                             else
/*      */                             {
/* 3330 */                               TheConstant = this.FirstSimplexTableau.RightHandSide[this.CurrentSimplexSensitivity.ConstantRow];
/*      */                             }
/*      */                           }
/* 3333 */                           if (this.CurrentSimplexSensitivity.ChangeColumn <= this.FirstSimplexTableau.NumVariables)
/*      */                           {
/* 3335 */                             this.CurrentSimplexTableau.ObjectiveFunction[this.CurrentSimplexSensitivity.ChangeColumn] = (TheConstant + MultiplyVectors(Vector, Vector2, this.FirstSimplexTableau.NumConstraints));
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/* 3340 */                             this.CurrentSimplexTableau.Z = (TheConstant + MultiplyVectors(Vector, Vector2, this.FirstSimplexTableau.NumConstraints));
/*      */                           }
/*      */                           
/* 3343 */                           for (i = 1; i <= this.FirstSimplexTableau.NumConstraints; i++) {
/* 3344 */                             if (this.CurrentSimplexSensitivity.MatrixRow == 0) {
/* 3345 */                               Vector[i] = this.CurrentSimplexTableau.ObjectiveFunctionM[(i + this.CurrentSimplexSensitivity.MatrixColumn - 1)];
/*      */                             }
/*      */                             else
/* 3348 */                               Vector[i] = 0.0D;
/*      */                           }
/* 3350 */                           if (this.CurrentSimplexSensitivity.ConstantRow == 0) {
/* 3351 */                             if (this.CurrentSimplexSensitivity.ConstantColumn <= this.FirstSimplexTableau.NumVariables)
/*      */                             {
/* 3353 */                               TheConstant = this.FirstSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexSensitivity.ConstantColumn];
/*      */                             }
/*      */                             else {
/* 3356 */                               TheConstant = this.FirstSimplexTableau.ZM;
/*      */                             }
/*      */                           }
/* 3359 */                           else if (this.CurrentSimplexSensitivity.ConstantColumn <= this.FirstSimplexTableau.NumVariables)
/*      */                           {
/* 3361 */                             TheConstant = 0.0D;
/*      */                           } else {
/* 3363 */                             TheConstant = 0.0D;
/*      */                           }
/* 3365 */                           if (this.CurrentSimplexSensitivity.ChangeColumn <= this.FirstSimplexTableau.NumVariables)
/*      */                           {
/* 3367 */                             this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexSensitivity.ChangeColumn] = (TheConstant + MultiplyVectors(Vector, Vector2, this.FirstSimplexTableau.NumConstraints));
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/* 3372 */                             this.CurrentSimplexTableau.ZM = (TheConstant + MultiplyVectors(Vector, Vector2, this.FirstSimplexTableau.NumConstraints));
/*      */                           }
/*      */                           
/* 3375 */                           if ((this.CurrentSimplexSensitivity.VectorRow == 0) && (this.CurrentSimplexSensitivity.MatrixRow == 0))
/*      */                           {
/* 3377 */                             if (this.CurrentSimplexSensitivity.ChangeColumn <= this.FirstSimplexTableau.NumVariables)
/*      */                             {
/* 3379 */                               this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexSensitivity.ChangeColumn] = (this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexSensitivity.ChangeColumn] + this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexSensitivity.MatrixColumn] * this.FirstSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexSensitivity.VectorColumn] + this.CurrentSimplexTableau.ObjectiveFunction[this.CurrentSimplexSensitivity.MatrixColumn] * this.FirstSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexSensitivity.VectorColumn]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3392 */                               this.CurrentSimplexTableau.ZM = (this.CurrentSimplexTableau.ZM + this.CurrentSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexSensitivity.MatrixColumn] * this.FirstSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexSensitivity.VectorColumn] + this.CurrentSimplexTableau.ObjectiveFunction[this.CurrentSimplexSensitivity.MatrixColumn] * this.FirstSimplexTableau.ObjectiveFunctionM[this.CurrentSimplexSensitivity.VectorColumn]); break;
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3407 */                               resetSens();
/* 3408 */                               break;
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/* 3413 */                               if ((this.SensitivitySimplexPerformed == false) && (this.SensitivityDualSimplexPerformed == false))
/*      */                               {
/* 3415 */                                 boolean CanDo2 = true;
/* 3416 */                                 for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 3417 */                                   if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunction[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))]) > 0.001D)
/*      */                                   {
/*      */ 
/* 3420 */                                     CanDo2 = false; }
/* 3421 */                                   if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunction[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))]) > 0.001D)
/*      */                                   {
/*      */ 
/* 3424 */                                     CanDo2 = false; }
/* 3425 */                                   for (int j = 1; j <= this.CurrentSimplexTableau.NumConstraints; 
/* 3426 */                                       j++) {
/* 3427 */                                     if (i == j) {
/* 3428 */                                       if (Math.abs(this.CurrentSimplexTableau.T[j][((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))] - 1) > 0.001D)
/*      */                                       {
/*      */ 
/* 3431 */                                         CanDo2 = false;
/*      */                                       }
/*      */                                     }
/* 3434 */                                     else if (Math.abs(this.CurrentSimplexTableau.T[j][((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))]) > 0.001D)
/*      */                                     {
/*      */ 
/* 3437 */                                       CanDo2 = false;
/*      */                                     }
/*      */                                   }
/*      */                                 }
/* 3441 */                                 if (CanDo2) {
/* 3442 */                                   this.errInfo = "The tableau is already in proper form.";
/* 3443 */                                   return false;
/*      */                                 }
/*      */                                 
/* 3446 */                                 this.CurrentSimplexTableau_bak_for_sen_print1 = ((ORSolverBase.SimplexTableauType)this.CurrentSimplexTableau.clone());
/*      */                                 
/* 3448 */                                 this.SensitivityConvertToProperFormPerformed = true;
/* 3449 */                                 SimplexDoConvertToProperForm(); break;
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3457 */                                 boolean CanDo1 = true;
/* 3458 */                                 for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 3459 */                                   if (this.CurrentSimplexTableau.RightHandSide[i] < 0)
/* 3460 */                                     CanDo1 = false;
/*      */                                 }
/* 3462 */                                 boolean CanDo2 = true;
/* 3463 */                                 for (i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 3464 */                                   if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunction[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))]) > 0.001D)
/*      */                                   {
/*      */ 
/* 3467 */                                     CanDo2 = false; }
/* 3468 */                                   if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunction[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))]) > 0.001D)
/*      */                                   {
/*      */ 
/* 3471 */                                     CanDo2 = false; }
/* 3472 */                                   for (int j = 1; j <= this.CurrentSimplexTableau.NumConstraints; j++) {
/* 3473 */                                     if (i == j) {
/* 3474 */                                       if (Math.abs(this.CurrentSimplexTableau.T[j][((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))] - 1) > 0.001D)
/*      */                                       {
/*      */ 
/* 3477 */                                         CanDo2 = false;
/*      */                                       }
/*      */                                     }
/* 3480 */                                     else if (Math.abs(this.CurrentSimplexTableau.T[j][((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))]) > 0.001D)
/*      */                                     {
/*      */ 
/* 3483 */                                       CanDo2 = false;
/*      */                                     }
/*      */                                   }
/*      */                                 }
/* 3487 */                                 if ((CanDo1) && (CanDo2) && (this.SensitivitySimplexPerformed == false)) {
/* 3488 */                                   CanDo1 = false;
/* 3489 */                                   for (i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 3490 */                                     if (this.CurrentSimplexTableau.ObjectiveFunctionM[i] < -1.0E-4D)
/*      */                                     {
/* 3492 */                                       CanDo1 = true;
/* 3493 */                                     } else if ((Math.abs(this.CurrentSimplexTableau.ObjectiveFunctionM[i]) < 1.0E-4D) && (this.CurrentSimplexTableau.ObjectiveFunction[i] < -1.0E-4D))
/*      */                                     {
/*      */ 
/*      */ 
/* 3497 */                                       CanDo1 = true; }
/*      */                                   }
/* 3499 */                                   if (CanDo1) {
/* 3500 */                                     this.SensitivitySimplexPerformed = true;
/* 3501 */                                     this.CurrentSimplexTableau_bak_for_sen_print2 = ((ORSolverBase.SimplexTableauType)this.CurrentSimplexTableau.clone());
/*      */                                     do
/*      */                                     {
/* 3504 */                                       IterateSimplexMethod();
/*      */                                       
/* 3506 */                                       if (this.CurrentSimplexTableau.Optimal != false) break; } while (this.CurrentSimplexTableau.Unbounded == false);
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3510 */                                     this.errInfo = "There is no need to perform the simplex method.  The tableau is already optimal.";
/* 3511 */                                     return false;
/*      */                                   }
/*      */                                 }
/*      */                                 else {
/* 3515 */                                   if (CanDo1 == false) {
/* 3516 */                                     this.errInfo = "Can't perform simplex method, because there are negative right-hand sides.";
/*      */                                     
/* 3518 */                                     return false;
/*      */                                   }
/* 3520 */                                   if (CanDo2 == false) {
/* 3521 */                                     this.errInfo = "Can't perform simplex method, because the tableau is not in proper form.";
/*      */                                     
/* 3523 */                                     return false;
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3531 */                                     boolean CanDo1 = true;
/* 3532 */                                     for (int i = 1; i <= this.CurrentSimplexTableau.NumVariables; i++) {
/* 3533 */                                       if ((this.CurrentSimplexTableau.ObjectiveFunction[i] < -0.01D) || (this.CurrentSimplexTableau.ObjectiveFunctionM[i] < -0.01D))
/*      */                                       {
/* 3535 */                                         CanDo1 = false; }
/*      */                                     }
/* 3537 */                                     boolean CanDo2 = true;
/* 3538 */                                     for (i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 3539 */                                       if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunction[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))]) > 0.001D)
/*      */                                       {
/*      */ 
/* 3542 */                                         CanDo2 = false; }
/* 3543 */                                       if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunction[((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))]) > 0.001D)
/*      */                                       {
/*      */ 
/* 3546 */                                         CanDo2 = false; }
/* 3547 */                                       for (int j = 1; j <= this.CurrentSimplexTableau.NumConstraints; j++) {
/* 3548 */                                         if (i == j) {
/* 3549 */                                           if (Math.abs(this.CurrentSimplexTableau.T[j][((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))] - 1) > 0.001D)
/*      */                                           {
/*      */ 
/* 3552 */                                             CanDo2 = false;
/*      */                                           }
/*      */                                         }
/* 3555 */                                         else if (Math.abs(this.CurrentSimplexTableau.T[j][((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]))]) > 0.001D)
/*      */                                         {
/*      */ 
/* 3558 */                                           CanDo2 = false;
/*      */                                         }
/*      */                                       }
/*      */                                     }
/* 3562 */                                     if ((CanDo1) && (CanDo2) && (this.SensitivityDualSimplexPerformed == false)) {
/* 3563 */                                       CanDo1 = false;
/* 3564 */                                       for (i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 3565 */                                         if (this.CurrentSimplexTableau.RightHandSide[i] < -1.0E-4D)
/* 3566 */                                           CanDo1 = true;
/*      */                                       }
/* 3568 */                                       if (CanDo1 == false) {
/* 3569 */                                         this.errInfo = "There is no need to perform the dual simplex method.  The tableau is already feasible.";
/* 3570 */                                         return false;
/*      */                                       }
/*      */                                       
/* 3573 */                                       this.SensitivityDualSimplexPerformed = true;
/* 3574 */                                       this.CurrentSimplexTableau_bak_for_sen_print2 = ((ORSolverBase.SimplexTableauType)this.CurrentSimplexTableau.clone());
/*      */                                       
/* 3576 */                                       if (CanDo1) {
/* 3577 */                                         DualSimplexDoCalcNextTableau();
/* 3578 */                                         CanDo1 = false;
/* 3579 */                                         for (i = 1; 
/* 3580 */                                             i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 3581 */                                           if (this.CurrentSimplexTableau.RightHandSide[i] < -1.0E-4D)
/*      */                                           {
/* 3583 */                                             CanDo1 = true;
/*      */                                           }
/*      */                                         }
/*      */                                       }
/*      */                                     } else {
/* 3588 */                                       if (CanDo1 == false) {
/* 3589 */                                         this.errInfo = "Can't perform dual simplex method, because there are negative coefficients in the objective function.";
/* 3590 */                                         return false;
/*      */                                       }
/* 3592 */                                       if (CanDo2 == false) {
/* 3593 */                                         this.errInfo = "Can't perform dual simplex method, because the tableau is not in proper form.";
/*      */                                         
/* 3595 */                                         return false;
/*      */                                       }
/*      */                                     }
/*      */                                   } } } } } } } } } } } } } } }
/*      */       break; }
/*      */     int i;
/*      */     int i;
/* 3602 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean doWork(IOROperation op, IORLPProcessController.LPData data)
/*      */   {
/* 3608 */     boolean success = doWork(op);
/*      */     
/* 3610 */     if (success) {
/* 3611 */       getData(data);
/*      */     }
/* 3613 */     return success;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean doIPWork(IOROperation op, IORLPProcessController.LPData data)
/*      */   {
/* 3620 */     Vector p = op.parameters;
/*      */     
/*      */ 
/* 3623 */     switch (op.operation_code) {
/*      */     case 10301: 
/* 3625 */       double[] pa = (double[])p.elementAt(0);
/* 3626 */       for (int i = 1; i <= pa.length; i++)
/* 3627 */         this.IP.Solution[0][i] = pa[(i - 1)];
/* 3628 */       if (CheckInitIPSolution() == false)
/*      */       {
/* 3630 */         return false;
/*      */       }
/* 3632 */       this.IP.It = 0;
/* 3633 */       getIPData(data);
/* 3634 */       break;
/*      */     
/*      */     case 10302: 
/* 3637 */       IterateIP();
/* 3638 */       getIPData(data);
/* 3639 */       break;
/*      */     
/*      */     case 10303: 
/* 3642 */       this.Alpha = ((Double)p.elementAt(0)).doubleValue();
/* 3643 */       data.Alpha = this.Alpha;
/* 3644 */       break;
/*      */     }
/*      */     
/*      */     
/*      */ 
/* 3649 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void resetIP(IORLPProcessController.LPData data)
/*      */   {
/* 3656 */     SetupIP();
/*      */     
/* 3658 */     data.Alpha = this.Alpha;
/* 3659 */     data.num_original_variable = this.SimplexModel.NumVariables;
/* 3660 */     data.num_variable = this.IP.NumVariables;
/* 3661 */     data.num_constrain = this.IP.NumConstraints;
/* 3662 */     for (int i = 1; i <= this.IP.NumConstraints; i++) {
/* 3663 */       for (int j = 1; j <= this.IP.NumVariables; j++)
/* 3664 */         data.constrain_coefficient[i][j] = this.IP.A[i][j];
/* 3665 */       data.constrain_coefficient[i][0] = this.IP.RightHandSide[i];
/*      */     }
/* 3667 */     for (i = 1; i <= this.IP.NumVariables; i++) {
/* 3668 */       data.objective_coefficient[i] = this.IP.c[i];
/* 3669 */       data.bound_operator[i] = this.SimplexModel.Nonnegativity[i];
/* 3670 */       data.bound[i] = this.SimplexModel.LowerBound[i];
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void getIPData(IORLPProcessController.LPData data)
/*      */   {
/* 3678 */     data.Alpha = this.Alpha;
/* 3679 */     double Obj = 0.0D;
/* 3680 */     for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/* 3681 */       int k = 0;
/* 3682 */       for (int l = 1; l <= j - 1; l++) {
/* 3683 */         if (this.SimplexModel.Nonnegativity[l] == 0)
/* 3684 */           k += 1;
/*      */       }
/* 3686 */       double Temp = this.IP.Solution[this.IP.It][(j + k)];
/* 3687 */       if (this.SimplexModel.Nonnegativity[j] == 0) {
/* 3688 */         Temp -= this.IP.Solution[this.IP.It][(j + k + 1)];
/* 3689 */       } else if (Math.abs(this.SimplexModel.LowerBound[j]) > 0.01D)
/* 3690 */         Temp += this.SimplexModel.LowerBound[j];
/* 3691 */       data.constrain_coefficient[this.IP.It][j] = Temp;
/* 3692 */       Obj += this.SimplexModel.ObjectiveFunction[j] * Temp;
/*      */     }
/* 3694 */     data.constrain_coefficient[this.IP.It][0] = Obj;
/*      */   }
/*      */   
/*      */ 
/* 3698 */   private int SimplexPhase = 1;
/* 3699 */   private int CurrentEqForm = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setEqForm(int form)
/*      */   {
/* 3708 */     this.CurrentEqForm = form;
/*      */   }
/*      */   
/*      */   protected void ORPrint() {
/* 3712 */     if (this.procedure == 1) {
/* 3713 */       PrintSimplexModify();
/* 3714 */     } else if (this.procedure == 2) {
/* 3715 */       PrintSimplexIP();
/* 3716 */     } else if (this.procedure == 3) {
/* 3717 */       PrintSimplexSetup();
/* 3718 */     } else if (this.procedure == 4) {
/* 3719 */       this.CommandNumber = 6;
/* 3720 */       PrintSimplexInteractive();
/*      */     }
/* 3722 */     else if (this.procedure == 5) {
/* 3723 */       PrintSimplexSensitivityAnalysis();
/* 3724 */     } else if (this.procedure == 6) {
/* 3725 */       this.CommandNumber = 8;
/* 3726 */       PrintSimplexInteractive();
/* 3727 */     } else if (this.procedure == 7) {
/* 3728 */       PrintSimplexAuto();
/* 3729 */     } else if (this.procedure == 8) {
/* 3730 */       PrintGraph();
/* 3731 */     } else if (this.procedure == 9) {
/* 3732 */       interactivePrintGraph();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void interactivePrintGraph()
/*      */   {
/* 3741 */     PrintLine("Interactive Graphical Method");
/* 3742 */     SkipLine();
/* 3743 */     PrintLine("Objective Function :  ".concat(String.valueOf(String.valueOf(this.gdata.objfunc))));
/* 3744 */     SkipLine();
/* 3745 */     if (this.gdata.lp != null) {
/* 3746 */       PrintLine("Subject to  :");
/* 3747 */       for (int i = 0; i < this.gdata.lp.num_of_constraints; i++)
/*      */       {
/* 3749 */         Constraint thecons = (Constraint)this.gdata.lp.vConstraints.elementAt(i);
/* 3750 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("(").append(i + 1).append(") ").append(thecons.toString()))));
/*      */       }
/* 3752 */       PrintLine("And  :  x1 >= 0    x2 >= 0");
/*      */     }
/*      */     else {
/* 3755 */       PrintLine("Subject to  :  x1 >= 0    x2 >= 0");
/*      */     }
/*      */     
/*      */ 
/* 3759 */     SkipLine();
/* 3760 */     if (this.gdata.showresult) { PrintLine("Optimal solution: ".concat(String.valueOf(String.valueOf(this.gdata.result))));
/*      */     }
/*      */     
/* 3763 */     SkipLine();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3768 */     if (this.gdata.showsens)
/*      */     {
/* 3770 */       TabPrint(45, "|");
/* 3771 */       TabPrintLine(50, "Objective Function Coefficients");
/*      */       
/* 3773 */       Print(" Variable   |   Value   ");
/* 3774 */       TabPrint(45, "|");
/* 3775 */       TabPrintLine(50, " Current Value | Minimum | Maximum ");
/* 3776 */       Print("____________|___________");
/* 3777 */       TabPrint(45, "|");
/* 3778 */       TabPrintLine(50, "_______________|_________|_________");
/* 3779 */       for (int i = 0; i < 2; i++) {
/* 3780 */         Print(String.valueOf(String.valueOf(new StringBuffer("x").append(i + 1))));
/* 3781 */         TabPrint(13, "|");
/* 3782 */         Print("".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.solution[i])))));
/* 3783 */         TabPrint(45, "|");
/* 3784 */         TabPrint(50, this.decfm.format(this.gdata.xcoef[i]));
/* 3785 */         TabPrint(65, "|");
/* 3786 */         if (this.gdata.senalysData.range_objcoef[i][0] > 999999.9D) {
/* 3787 */           Print("infin");
/* 3788 */         } else if (this.gdata.senalysData.range_objcoef[i][0] < -999999.9D) {
/* 3789 */           Print("-infin");
/*      */         } else {
/* 3791 */           Print("".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.range_objcoef[i][0])))));
/*      */         }
/* 3793 */         TabPrint(75, "|");
/* 3794 */         if (this.gdata.senalysData.range_objcoef[i][1] > 999999.9D) {
/* 3795 */           PrintLine("infin");
/* 3796 */         } else if (this.gdata.senalysData.range_objcoef[i][1] < -999999.9D) {
/* 3797 */           PrintLine("-infin");
/*      */         } else {
/* 3799 */           PrintLine("".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.range_objcoef[i][1])))));
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 3806 */       SkipLine();
/* 3807 */       SkipLine();
/* 3808 */       TabPrint(45, "|");
/* 3809 */       TabPrintLine(55, "Right Hand Sides");
/*      */       
/* 3811 */       Print("Constraint|Slack or Surplus|Shadow Price");
/* 3812 */       TabPrint(45, "|");
/* 3813 */       TabPrintLine(50, "      RHS      | Minimum | Maximum ");
/* 3814 */       Print("__________|________________|____________");
/* 3815 */       TabPrint(45, "|");
/* 3816 */       TabPrintLine(50, "_______________|_________|_________");
/*      */       
/* 3818 */       for (i = 0; i < this.CurrentSimplexTableau.NumConstraints; i++) {
/* 3819 */         Print(String.valueOf(String.valueOf(new StringBuffer("").append(i + 1))));
/* 3820 */         TabPrint(11, "|");
/* 3821 */         Print("".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.slack_surplus[i])))));
/* 3822 */         TabPrint(28, "|");
/* 3823 */         Print("".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.shadow_price[i])))));
/* 3824 */         TabPrint(45, "|");
/*      */         
/* 3826 */         Print("     ".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.cons_value[i])))));
/* 3827 */         TabPrint(65, "|");
/* 3828 */         if (this.gdata.senalysData.range_rightside[i][0] > 999999.9D) {
/* 3829 */           Print("infin");
/* 3830 */         } else if (this.gdata.senalysData.range_rightside[i][0] < -999999.9D) {
/* 3831 */           Print("-infin");
/*      */         } else {
/* 3833 */           Print("".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.range_rightside[i][0])))));
/*      */         }
/* 3835 */         TabPrint(75, "|");
/* 3836 */         if (this.gdata.senalysData.range_rightside[i][1] > 999999.9D) {
/* 3837 */           PrintLine("infin");
/* 3838 */         } else if (this.gdata.senalysData.range_rightside[i][1] < -999999.9D) {
/* 3839 */           PrintLine("-infin");
/*      */         } else {
/* 3841 */           PrintLine("".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.range_rightside[i][1])))));
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void PrintGraph()
/*      */   {
/* 3855 */     PrintLine("Graphical Method and Sensitivity Analysis");
/* 3856 */     SkipLine();
/* 3857 */     PrintLine("Objective Function :  ".concat(String.valueOf(String.valueOf(this.gdata.objfunc))));
/* 3858 */     SkipLine();
/* 3859 */     if (this.gdata.lp != null) {
/* 3860 */       PrintLine("Subject to  :");
/* 3861 */       for (int i = 0; i < this.gdata.lp.num_of_constraints; i++)
/*      */       {
/* 3863 */         Constraint thecons = (Constraint)this.gdata.lp.vConstraints.elementAt(i);
/* 3864 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("(").append(i + 1).append(") ").append(thecons.toString()))));
/*      */       }
/* 3866 */       PrintLine("And  :  x1 >= 0    x2 >= 0");
/*      */     }
/*      */     else {
/* 3869 */       PrintLine("Subject to  :  x1 >= 0    x2 >= 0");
/*      */     }
/*      */     
/*      */ 
/* 3873 */     SkipLine();
/* 3874 */     if (this.gdata.showresult) { PrintLine("Optimal solution: ".concat(String.valueOf(String.valueOf(this.gdata.result))));
/*      */     }
/*      */     
/* 3877 */     SkipLine();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3882 */     if (this.gdata.showsens)
/*      */     {
/* 3884 */       TabPrint(45, "|");
/* 3885 */       TabPrintLine(50, "Objective Function Coefficients");
/*      */       
/* 3887 */       Print(" Variable   |   Value   ");
/* 3888 */       TabPrint(45, "|");
/* 3889 */       TabPrintLine(50, " Current Value | Minimum | Maximum ");
/* 3890 */       Print("____________|___________");
/* 3891 */       TabPrint(45, "|");
/* 3892 */       TabPrintLine(50, "_______________|_________|_________");
/* 3893 */       for (int i = 0; i < 2; i++) {
/* 3894 */         Print(String.valueOf(String.valueOf(new StringBuffer("x").append(i + 1))));
/* 3895 */         TabPrint(13, "|");
/* 3896 */         Print("".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.solution[i])))));
/* 3897 */         TabPrint(45, "|");
/* 3898 */         TabPrint(50, this.decfm.format(this.gdata.xcoef[i]));
/* 3899 */         TabPrint(65, "|");
/* 3900 */         if (this.gdata.senalysData.range_objcoef[i][0] > 999999.9D) {
/* 3901 */           Print("infin");
/* 3902 */         } else if (this.gdata.senalysData.range_objcoef[i][0] < -999999.9D) {
/* 3903 */           Print("-infin");
/*      */         } else {
/* 3905 */           Print("".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.range_objcoef[i][0])))));
/*      */         }
/* 3907 */         TabPrint(75, "|");
/* 3908 */         if (this.gdata.senalysData.range_objcoef[i][1] > 999999.9D) {
/* 3909 */           PrintLine("infin");
/* 3910 */         } else if (this.gdata.senalysData.range_objcoef[i][1] < -999999.9D) {
/* 3911 */           PrintLine("-infin");
/*      */         } else {
/* 3913 */           PrintLine("".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.range_objcoef[i][1])))));
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 3920 */       SkipLine();
/* 3921 */       SkipLine();
/* 3922 */       TabPrint(45, "|");
/* 3923 */       TabPrintLine(55, "Right Hand Sides");
/*      */       
/* 3925 */       Print("Constraint|Slack or Surplus|Shadow Price");
/* 3926 */       TabPrint(45, "|");
/* 3927 */       TabPrintLine(50, "      RHS      | Minimum | Maximum ");
/* 3928 */       Print("__________|________________|____________");
/* 3929 */       TabPrint(45, "|");
/* 3930 */       TabPrintLine(50, "_______________|_________|_________");
/*      */       
/* 3932 */       for (i = 0; i < this.CurrentSimplexTableau.NumConstraints; i++) {
/* 3933 */         Print(String.valueOf(String.valueOf(new StringBuffer("").append(i + 1))));
/* 3934 */         TabPrint(11, "|");
/* 3935 */         Print("".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.slack_surplus[i])))));
/* 3936 */         TabPrint(28, "|");
/* 3937 */         Print("".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.shadow_price[i])))));
/* 3938 */         TabPrint(45, "|");
/*      */         
/* 3940 */         Print("     ".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.cons_value[i])))));
/* 3941 */         TabPrint(65, "|");
/* 3942 */         if (this.gdata.senalysData.range_rightside[i][0] > 999999.9D) {
/* 3943 */           Print("infin");
/* 3944 */         } else if (this.gdata.senalysData.range_rightside[i][0] < -999999.9D) {
/* 3945 */           Print("-infin");
/*      */         } else {
/* 3947 */           Print("".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.range_rightside[i][0])))));
/*      */         }
/* 3949 */         TabPrint(75, "|");
/* 3950 */         if (this.gdata.senalysData.range_rightside[i][1] > 999999.9D) {
/* 3951 */           PrintLine("infin");
/* 3952 */         } else if (this.gdata.senalysData.range_rightside[i][1] < -999999.9D) {
/* 3953 */           PrintLine("-infin");
/*      */         } else {
/* 3955 */           PrintLine("".concat(String.valueOf(String.valueOf(this.decfm.format(this.gdata.senalysData.range_rightside[i][1])))));
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void PrintSimplexModify()
/*      */   {
/* 3967 */     PrintLine("Linear Programming Model:");
/* 3968 */     SkipLine();
/* 3969 */     PrintLine("Number of Decision Variables:     ".concat(String.valueOf(String.valueOf(FormatInteger(this.SimplexModel.NumVariables, 2, 0, 1)))));
/*      */     
/* 3971 */     SkipLine();
/* 3972 */     PrintLine("Number of Functional Constraints: ".concat(String.valueOf(String.valueOf(FormatInteger(this.SimplexModel.NumConstraints, 2, 0, 1)))));
/*      */     
/* 3974 */     SkipLine();
/* 3975 */     PrintSimplexModel();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void PrintSimplexIP()
/*      */   {
/* 3982 */     PrintLine("Linear Programming Model:");
/* 3983 */     SkipLine();
/* 3984 */     PrintLine("Number of Decision Variables:     ".concat(String.valueOf(String.valueOf(FormatInteger(this.SimplexModel.NumVariables, 2, 0, 1)))));
/*      */     
/* 3986 */     SkipLine();
/* 3987 */     PrintLine("Number of Functional Constraints: ".concat(String.valueOf(String.valueOf(FormatInteger(this.SimplexModel.NumConstraints, 2, 0, 1)))));
/*      */     
/* 3989 */     SkipLine();
/* 3990 */     PrintSimplexModel();
/* 3991 */     Skip(4);
/* 3992 */     Print("Solve Automatically by the Interior Point Algorithm: ");
/* 3993 */     if (Math.abs(this.AlphaUsed - 0.9D) < 0.1D) {
/* 3994 */       PrintLine("(Alpha = 0.9)");
/*      */     } else
/* 3996 */       PrintLine("(Alpha = 0.5)");
/* 3997 */     SkipLine();
/*      */     
/* 3999 */     Print("It. |");
/* 4000 */     for (int i = 1; i <= this.SimplexModel.NumVariables; i++)
/* 4001 */       TabPrint(10 * i + 2, String.valueOf(String.valueOf(new StringBuffer("X").append(IntegerToString(i, 1)).append(" |"))));
/* 4002 */     TabPrintLine(10 * this.SimplexModel.NumVariables + 12, "Z");
/* 4003 */     Print("____|_");
/* 4004 */     for (i = 1; i <= this.SimplexModel.NumVariables; i++)
/* 4005 */       Print("________|_");
/* 4006 */     PrintLine("________");
/* 4007 */     for (i = 0; i <= this.IP.It; i++) {
/* 4008 */       double Obj = 0.0D;
/* 4009 */       TabPrint(2, String.valueOf(String.valueOf(FormatInteger(i, 2, 0, 1))).concat(" | "));
/* 4010 */       for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/* 4011 */         int k = 0;
/* 4012 */         for (int l = 1; l <= j - 1; l++) {
/* 4013 */           if (this.SimplexModel.Nonnegativity[l] == 0)
/* 4014 */             k += 1;
/*      */         }
/* 4016 */         double Temp = this.IP.Solution[i][(j + k)];
/* 4017 */         if (this.SimplexModel.Nonnegativity[j] == 0) {
/* 4018 */           Temp -= this.IP.Solution[i][(j + k + 1)];
/*      */         } else
/* 4020 */           Temp += this.SimplexModel.LowerBound[j];
/* 4021 */         Print(String.valueOf(String.valueOf(FormatReal(Temp, 7, 0, 1))).concat(" | "));
/* 4022 */         Obj += this.SimplexModel.ObjectiveFunction[j] * Temp;
/*      */       }
/* 4024 */       TabPrintLine(10 * this.SimplexModel.NumVariables + 6, FormatReal(Obj, 7, 0, 1));
/*      */     }
/*      */   }
/*      */   
/*      */   private void PrintSimplexSetup()
/*      */   {
/* 4030 */     PrintLine("Linear Programming Model:");
/* 4031 */     SkipLine();
/* 4032 */     PrintLine("Number of Decision Variables:     ".concat(String.valueOf(String.valueOf(FormatInteger(this.SimplexModel.NumVariables, 2, 0, 1)))));
/*      */     
/* 4034 */     SkipLine();
/* 4035 */     PrintLine("Number of Functional Constraints: ".concat(String.valueOf(String.valueOf(FormatInteger(this.SimplexModel.NumConstraints, 2, 0, 1)))));
/*      */     
/* 4037 */     SkipLine();
/* 4038 */     PrintSimplexModel();
/* 4039 */     Skip(5);
/* 4040 */     PrintLine("Simplex Model set up for the Simplex Method:");
/* 4041 */     SkipLine();
/* 4042 */     PrintSimplexTableau();
/*      */   }
/*      */   
/* 4045 */   private void PrintSimplexAuto() { System.out.println("Print Auto");
/*      */     
/*      */ 
/*      */ 
/* 4049 */     this.SimplexPhase = 1;
/* 4050 */     PrintLine("Linear Programming Model:");
/* 4051 */     SkipLine();
/* 4052 */     PrintLine("Number of Decision Variables:     ".concat(String.valueOf(String.valueOf(FormatInteger(this.SimplexModel.NumVariables, 2, 0, 1)))));
/*      */     
/* 4054 */     SkipLine();
/* 4055 */     PrintLine("Number of Functional Constraints: ".concat(String.valueOf(String.valueOf(FormatInteger(this.SimplexModel.NumConstraints, 2, 0, 1)))));
/*      */     
/* 4057 */     SkipLine();
/* 4058 */     PrintSimplexModel();
/* 4059 */     Skip(4);
/* 4060 */     PrintLine("Solve Automatically by the Simplex Method:");
/* 4061 */     SkipLine();
/* 4062 */     if (this.outputInfo.equalsIgnoreCase("right"))
/*      */     {
/* 4064 */       Print("Optimal Solution");
/* 4065 */       TabPrint(45, "|");
/* 4066 */       TabPrintLine(53, "Sensitivity Analysis");
/*      */       
/*      */ 
/* 4069 */       Print("Objective Function: ".concat(String.valueOf(String.valueOf(this.decfm.format(this.CurrentSimplexTableau.Z)))));
/* 4070 */       TabPrint(45, "|");
/* 4071 */       TabPrintLine(50, "Objective Function Coefficients");
/*      */       
/*      */ 
/* 4074 */       Print(" Variable   |   Value   ");
/* 4075 */       TabPrint(45, "|");
/* 4076 */       TabPrintLine(50, " Current Value | Minimum | Maximum ");
/* 4077 */       Print("____________|___________");
/* 4078 */       TabPrint(45, "|");
/* 4079 */       TabPrintLine(50, "_______________|_________|_________");
/* 4080 */       for (int i = 0; i < this.SimplexModel.NumVariables; i++) {
/* 4081 */         Print(String.valueOf(String.valueOf(new StringBuffer("X").append(i + 1))));
/* 4082 */         TabPrint(13, "|");
/* 4083 */         Print("".concat(String.valueOf(String.valueOf(this.decfm.format((Double)this.variableValeV.elementAt(i))))));
/* 4084 */         TabPrint(45, "|");
/* 4085 */         TabPrint(50, this.decfm.format(this.coefficientsArr[(i + 1)]));
/* 4086 */         TabPrint(65, "|");
/* 4087 */         if (this.minArr[(i + 1)] > Integer.MAX_VALUE) {
/* 4088 */           Print("infin");
/* 4089 */         } else if (this.minArr[(i + 1)] < Integer.MIN_VALUE) {
/* 4090 */           Print("-infin");
/*      */         } else {
/* 4092 */           Print("".concat(String.valueOf(String.valueOf(this.decfm.format(this.minArr[(i + 1)])))));
/*      */         }
/* 4094 */         TabPrint(75, "|");
/* 4095 */         if (this.maxArr[(i + 1)] > Integer.MAX_VALUE) {
/* 4096 */           PrintLine("infin");
/* 4097 */         } else if (this.maxArr[(i + 1)] < Integer.MIN_VALUE) {
/* 4098 */           PrintLine("-infin");
/*      */         } else {
/* 4100 */           PrintLine("".concat(String.valueOf(String.valueOf(this.decfm.format(this.maxArr[(i + 1)])))));
/*      */         }
/*      */       }
/*      */       
/* 4104 */       TabPrintLine(45, "|");
/* 4105 */       TabPrintLine(45, "|");
/*      */       
/* 4107 */       TabPrint(45, "|");
/* 4108 */       TabPrintLine(55, "Right Hand Sides");
/*      */       
/* 4110 */       Print("Constraint|Slack or Surplus|Shadow Price");
/* 4111 */       TabPrint(45, "|");
/* 4112 */       TabPrintLine(50, " Current Value | Minimum | Maximum ");
/* 4113 */       Print("__________|________________|____________");
/* 4114 */       TabPrint(45, "|");
/* 4115 */       TabPrintLine(50, "_______________|_________|_________");
/*      */       
/* 4117 */       for (i = 0; i < this.CurrentSimplexTableau.NumConstraints; i++) {
/* 4118 */         Print(String.valueOf(String.valueOf(new StringBuffer("").append(i + 1))));
/* 4119 */         TabPrint(11, "|");
/* 4120 */         Print("".concat(String.valueOf(String.valueOf(this.decfm.format((Double)this.SlackV.elementAt(i))))));
/* 4121 */         TabPrint(28, "|");
/* 4122 */         Print("".concat(String.valueOf(String.valueOf(this.decfm.format((Double)this.ShadowPriceV.elementAt(i))))));
/* 4123 */         TabPrint(45, "|");
/*      */         
/* 4125 */         Print("     ".concat(String.valueOf(String.valueOf(this.decfm.format(this.RHSCurrentValueArr[(i + 1)])))));
/* 4126 */         TabPrint(65, "|");
/* 4127 */         if (this.minRHSArr[(i + 1)] > Integer.MAX_VALUE) {
/* 4128 */           Print("infin");
/* 4129 */         } else if (this.minRHSArr[(i + 1)] < Integer.MIN_VALUE) {
/* 4130 */           Print("-infin");
/*      */         } else {
/* 4132 */           Print("".concat(String.valueOf(String.valueOf(this.decfm.format(this.minRHSArr[(i + 1)])))));
/*      */         }
/* 4134 */         TabPrint(75, "|");
/* 4135 */         if (this.maxRHSArr[(i + 1)] > Integer.MAX_VALUE) {
/* 4136 */           PrintLine("infin");
/* 4137 */         } else if (this.maxRHSArr[(i + 1)] < Integer.MIN_VALUE) {
/* 4138 */           PrintLine("-infin");
/*      */         } else {
/* 4140 */           PrintLine("".concat(String.valueOf(String.valueOf(this.decfm.format(this.maxRHSArr[(i + 1)])))));
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void PrintSimplexInteractive()
/*      */   {
/* 4151 */     this.SimplexPhase = 1;
/* 4152 */     PrintLine("Linear Programming Model:");
/* 4153 */     SkipLine();
/* 4154 */     PrintLine("Number of Decision Variables:     ".concat(String.valueOf(String.valueOf(FormatInteger(this.SimplexModel.NumVariables, 2, 0, 1)))));
/*      */     
/* 4156 */     SkipLine();
/* 4157 */     PrintLine("Number of Functional Constraints: ".concat(String.valueOf(String.valueOf(FormatInteger(this.SimplexModel.NumConstraints, 2, 0, 1)))));
/*      */     
/* 4159 */     SkipLine();
/* 4160 */     PrintSimplexModel();
/* 4161 */     Skip(4);
/* 4162 */     if (this.CommandNumber == 6) {
/* 4163 */       PrintLine("Solve Interactively by the Simplex Method:");
/*      */     } else
/* 4165 */       PrintLine("Solve Interactively by the Modified Simplex Method:");
/* 4166 */     if ((this.is_big_M == false) && (this.CurrentSimplexTableau.ContainsArtificials)) {
/* 4167 */       SkipLine();
/* 4168 */       PrintLine("Phase 1:");
/*      */     }
/*      */     
/* 4171 */     ORSolverBase.SimplexTableauType TempHandle = this.CurrentSimplexTableau;
/* 4172 */     this.CurrentSimplexTableau = ((ORSolverBase.SimplexTableauType)this.CurrentSimplexTableau_bak_for_Setup.clone());
/*      */     
/*      */ 
/*      */ 
/* 4176 */     int n = this.steps.size();
/* 4177 */     IOROperation op = null;
/* 4178 */     int i = 0;
/* 4179 */     if (this.is_big_M) {
/* 4180 */       while (i < n) {
/* 4181 */         op = (IOROperation)this.steps.elementAt(i);
/* 4182 */         switch (op.operation_code) {
/*      */         case 10401: 
/* 4184 */           this.CurrentSimplexTableau.EnteringBasicVariable = ((int[])op.parameters.elementAt(0))[1];
/*      */           
/* 4186 */           this.CurrentSimplexTableau.LeavingBasicVariableEquation = ((int[])op.parameters.elementAt(0))[0];
/*      */           
/* 4188 */           PrintLines(this.SimplexModel.NumConstraints + 6);
/* 4189 */           Skip(2);
/* 4190 */           PrintSimplexTableau();
/* 4191 */           doWork(op);
/* 4192 */           break;
/*      */         default: 
/* 4194 */           doWork(op);
/*      */         }
/*      */         
/* 4197 */         i++;
/* 4198 */         if ((i == n) && (op.operation_code != 10401))
/*      */         {
/* 4200 */           this.CurrentSimplexTableau.EnteringBasicVariable = 0;
/* 4201 */           this.CurrentSimplexTableau.LeavingBasicVariableEquation = 0;
/* 4202 */           PrintLines(this.SimplexModel.NumConstraints + 6);
/* 4203 */           Skip(2);
/* 4204 */           PrintSimplexTableau();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 4209 */     while (i < n) {
/* 4210 */       PrintLines(this.SimplexModel.NumConstraints + 6);
/* 4211 */       op = (IOROperation)this.steps.elementAt(i);
/* 4212 */       switch (op.operation_code) {
/*      */       case 10401: 
/* 4214 */         this.CurrentSimplexTableau.EnteringBasicVariable = ((int[])op.parameters.elementAt(0))[1];
/*      */         
/* 4216 */         this.CurrentSimplexTableau.LeavingBasicVariableEquation = ((int[])op.parameters.elementAt(0))[0];
/*      */         
/* 4218 */         PrintLines(this.SimplexModel.NumConstraints + 6);
/* 4219 */         Skip(2);
/* 4220 */         PrintSimplexTableau();
/* 4221 */         doWork(op);
/* 4222 */         break;
/*      */       
/*      */ 
/*      */       case 10403: 
/*      */       case 10406: 
/* 4227 */         if (this.SimplexPhase == 1) {
/* 4228 */           this.CurrentSimplexTableau.EnteringBasicVariable = 0;
/* 4229 */           this.CurrentSimplexTableau.LeavingBasicVariableEquation = 0;
/*      */           
/* 4231 */           PrintLines(this.SimplexModel.NumConstraints + 6);
/* 4232 */           Skip(2);
/* 4233 */           PrintSimplexTableau();
/*      */           
/* 4235 */           this.SimplexPhase = 2;
/* 4236 */           PrintLines(8 + this.SimplexModel.NumConstraints);
/* 4237 */           Skip(2);
/* 4238 */           PrintLine("Phase 2:");
/*      */         }
/* 4240 */         doWork(op);
/* 4241 */         break;
/*      */       default: 
/* 4243 */         doWork(op);
/*      */       }
/*      */       
/* 4246 */       i++;
/* 4247 */       if (i == n) {
/* 4248 */         this.CurrentSimplexTableau.EnteringBasicVariable = 0;
/* 4249 */         this.CurrentSimplexTableau.LeavingBasicVariableEquation = 0;
/* 4250 */         PrintLines(this.SimplexModel.NumConstraints + 6);
/* 4251 */         Skip(2);
/* 4252 */         PrintSimplexTableau();
/*      */       }
/*      */     }
/*      */     
/* 4256 */     this.CurrentSimplexTableau = TempHandle;
/*      */   }
/*      */   
/*      */ 
/*      */   protected void PrintPolynomial(ORSolverBase.PolynomialType Polynomial)
/*      */   {
/* 4262 */     for (int i = 1; i <= Polynomial.NumTerms; i++) {
/* 4263 */       if (Math.abs(Polynomial.Coefficient[i]) >= 0.001D)
/*      */       {
/* 4265 */         PrintSpace(6 + 4 * Polynomial.NumVariables, 12);
/* 4266 */         if (i == 1) {
/* 4267 */           Print(String.valueOf(String.valueOf(FormatReal(Polynomial.Coefficient[i], 7, 0, 1))).concat(" "));
/*      */         }
/*      */         else {
/* 4270 */           Print(String.valueOf(String.valueOf(FormatReal(Polynomial.Coefficient[i], 7, 1, 1))).concat(" "));
/*      */         }
/* 4272 */         for (int j = 1; j <= Polynomial.NumVariables; j++) {
/* 4273 */           if (Polynomial.Exponent[i][j] > 0) {
/* 4274 */             Print("X");
/* 4275 */             if (Polynomial.NumVariables > 1)
/* 4276 */               Print(FormatInteger(j, 1, 0, 0));
/*      */           }
/* 4278 */           if (Polynomial.Exponent[i][j] > 1) {
/* 4279 */             Print("^".concat(String.valueOf(String.valueOf(FormatInteger(Polynomial.Exponent[i][j], 1, 0, 0)))));
/*      */           }
/*      */         }
/*      */         
/* 4283 */         Print(" ");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected void PrintSimplexModel()
/*      */   {
/* 4291 */     if (this.SimplexModel.Objective == 0) {
/* 4292 */       Print("Max Z = ");
/*      */     } else
/* 4294 */       Print("Min Z = ");
/* 4295 */     boolean First = true;
/* 4296 */     if ((this.ApplicationNumber == 6) && (this.CommandNumber >= 8))
/*      */     {
/* 4298 */       PrintPolynomial(this.ThePolynomial);
/*      */     } else {
/* 4300 */       for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/* 4301 */         if ((this.SimplexModel.NumVariables <= 6) || (Math.abs(this.SimplexModel.ObjectiveFunction[j]) > 1.0E-5D) || (Math.abs(this.SimplexModel.ObjectiveFunctionM[j]) > 1.0E-5D))
/*      */         {
/*      */ 
/* 4304 */           PrintSpace(11, 9);
/* 4305 */           if (First) {
/* 4306 */             if (this.SimplexModel.ObjectiveFunctionM[j] != 0) {
/* 4307 */               Print(String.valueOf(String.valueOf(FormatReal(this.SimplexModel.ObjectiveFunctionM[j], 6, 0, 1))).concat("M"));
/*      */               
/* 4309 */               if (this.SimplexModel.ObjectiveFunction[j] != 0) {
/* 4310 */                 Print(FormatReal(this.SimplexModel.ObjectiveFunction[j], 6, 1, 1));
/*      */               }
/*      */             }
/*      */             else {
/* 4314 */               Print(FormatReal(this.SimplexModel.ObjectiveFunction[j], 6, 0, 1));
/*      */             }
/*      */             
/*      */           }
/* 4318 */           else if (this.SimplexModel.ObjectiveFunctionM[j] != 0) {
/* 4319 */             Print(String.valueOf(String.valueOf(FormatReal(this.SimplexModel.ObjectiveFunctionM[j], 6, 1, 1))).concat("M"));
/*      */             
/* 4321 */             if (this.SimplexModel.ObjectiveFunction[j] != 0) {
/* 4322 */               Print(FormatReal(this.SimplexModel.ObjectiveFunction[j], 6, 1, 1));
/*      */             }
/*      */           }
/*      */           else {
/* 4326 */             Print(FormatReal(this.SimplexModel.ObjectiveFunction[j], 6, 1, 1));
/*      */           }
/*      */           
/* 4329 */           First = false;
/* 4330 */           Print(String.valueOf(String.valueOf(new StringBuffer(" X").append(IntegerToString(j, 2)).append(" "))));
/*      */         }
/*      */       }
/*      */     }
/* 4334 */     if ((this.ApplicationNumber == 6) && (this.CommandNumber == 6))
/*      */     {
/* 4336 */       PrintSpace(20, 9);
/* 4337 */       Print(" + (2nd order terms)");
/*      */     }
/* 4339 */     SkipLine();
/* 4340 */     SkipLine();
/* 4341 */     PrintLine("subject to");
/* 4342 */     SkipLine();
/* 4343 */     for (int i = 1; i <= this.SimplexModel.NumConstraints; i++) {
/* 4344 */       if ((this.ApplicationNumber != 5) || (i <= this.NumOriginalConstraints1))
/*      */       {
/* 4346 */         Print(String.valueOf(String.valueOf(FormatInteger(i, 2, 0, 1))).concat(")     "));
/* 4347 */         First = true;
/* 4348 */         for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/* 4349 */           PrintSpace(12, 9);
/* 4350 */           if ((this.SimplexModel.NumVariables <= 6) || (Math.abs(this.SimplexModel.A[i][j]) > 1.0E-6D))
/*      */           {
/* 4352 */             if (First) {
/* 4353 */               Print(FormatReal(this.SimplexModel.A[i][j], 6, 0, 1));
/*      */             }
/*      */             else {
/* 4356 */               Print(FormatReal(this.SimplexModel.A[i][j], 6, 1, 1));
/*      */             }
/* 4358 */             First = false;
/* 4359 */             Print(String.valueOf(String.valueOf(new StringBuffer(" X").append(IntegerToString(j, 2)).append(" "))));
/*      */           }
/*      */         }
/* 4362 */         PrintSpace(9, 9);
/* 4363 */         switch (this.SimplexModel.Constraint[i]) {
/*      */         case 0: 
/* 4365 */           Print("<= ");
/* 4366 */           break;
/*      */         case 1: 
/* 4368 */           Print(" = ");
/* 4369 */           break;
/*      */         case 2: 
/* 4371 */           Print(">= ");
/* 4372 */           break;
/*      */         }
/* 4374 */         PrintLine(FormatReal(this.SimplexModel.RightHandSide[i], 7, 0, 1));
/*      */         
/* 4376 */         SkipLine();
/*      */       }
/*      */     }
/* 4379 */     PrintLine("and");
/* 4380 */     SkipLine();
/* 4381 */     TabPrint(8, " ");
/* 4382 */     for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/* 4383 */       if (this.SimplexModel.Nonnegativity[j] != 0) {
/* 4384 */         PrintSpace(15, 9);
/* 4385 */         if ((this.ApplicationNumber == 5) && (j <= this.NumBinaryVariables))
/*      */         {
/* 4387 */           Print(String.valueOf(String.valueOf(new StringBuffer("X").append(IntegerToString(j, 2)).append(" = {0,1}"))));
/* 4388 */         } else if ((this.ApplicationNumber == 5) && (j <= this.NumBinaryVariables + this.NumIntegerVariables))
/*      */         {
/* 4390 */           Print(String.valueOf(String.valueOf(new StringBuffer("X").append(IntegerToString(j, 2)).append("= {0,1,...}"))));
/*      */         } else {
/* 4392 */           Print(String.valueOf(String.valueOf(new StringBuffer("X").append(IntegerToString(j, 2)).append(" >= ").append(RealToString(this.SimplexModel.LowerBound[j], 6)))));
/*      */         }
/* 4394 */         boolean Last = true;
/* 4395 */         for (int k = j + 1; k <= this.SimplexModel.NumVariables; k++) {
/* 4396 */           if (this.SimplexModel.Nonnegativity[k] != 0)
/* 4397 */             Last = false;
/*      */         }
/* 4399 */         if (Last) {
/* 4400 */           Print(".");
/*      */         } else
/* 4402 */           Print(", ");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void PrintSimplexTableau() {
/* 4408 */     int Crunch = 0;
/* 4409 */     int TwoPhaseOrBigM = this.is_big_M ? 0 : 1;
/*      */     
/*      */ 
/* 4412 */     if (this.CurrentEqForm == 0) {
/* 4413 */       if ((this.CurrentSimplexTableau.NumVariables >= 1) && (this.CurrentSimplexTableau.NumVariables <= 7))
/*      */       {
/* 4415 */         Crunch = 0;
/* 4416 */       } else if (this.CurrentSimplexTableau.NumVariables == 8) {
/* 4417 */         Crunch = 1;
/* 4418 */       } else if ((this.CurrentSimplexTableau.NumVariables >= 9) && (this.CurrentSimplexTableau.NumVariables <= 200))
/*      */       {
/* 4420 */         Crunch = 2; }
/* 4421 */       if (this.CurrentSimplexTableau.Objective == 0) {
/* 4422 */         if (this.CurrentSimplexTableau.Objective != this.SimplexModel.Objective) {
/* 4423 */           PrintLine("Maximize -Z, subject to");
/*      */         } else {
/* 4425 */           PrintLine("Maximize Z, subject to");
/*      */         }
/*      */       } else
/* 4428 */         PrintLine("Minimize Z, subject to");
/* 4429 */       for (int j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/* 4430 */         if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunctionM[j]) > 1.0E-6D)
/*      */         {
/* 4432 */           if (j == 1) {
/* 4433 */             TabPrint((j - 1) * (9 - Crunch) + 6, String.valueOf(String.valueOf(FormatReal(this.CurrentSimplexTableau.ObjectiveFunctionM[j], 4, 0, 1))).concat("M"));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 4438 */             TabPrint((j - 1) * (9 - Crunch) + 6, String.valueOf(String.valueOf(FormatReal(this.CurrentSimplexTableau.ObjectiveFunctionM[j], 4, 1, 1))).concat(String.valueOf(String.valueOf('M'))));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4444 */       if (Math.abs(this.CurrentSimplexTableau.ZM) > 1.0E-6D) {
/* 4445 */         TabPrintLine(this.CurrentSimplexTableau.NumVariables * (9 - Crunch) + 8, String.valueOf(String.valueOf(RealToString(this.CurrentSimplexTableau.ZM, 5))).concat("M"));
/*      */       }
/*      */       else
/*      */       {
/* 4449 */         SkipLine(); }
/* 4450 */       if (this.CurrentSimplexTableau.Objective != this.SimplexModel.Objective) {
/* 4451 */         Print("0)-Z ");
/*      */       } else
/* 4453 */         Print("0) Z ");
/* 4454 */       for (j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/* 4455 */         if (Crunch == 0) {
/* 4456 */           Print(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(FormatReal(this.CurrentSimplexTableau.ObjectiveFunction[j], 5, 1, 1)))).append(" X").append(FormatInteger(j, 2, 2, 0)))));
/*      */ 
/*      */         }
/* 4459 */         else if ((Crunch == 1) || (j == 10)) {
/* 4460 */           Print(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(FormatReal(this.CurrentSimplexTableau.ObjectiveFunction[j], 5, 1, 1)))).append("X").append(FormatInteger(j, 2, 2, 0)))));
/*      */ 
/*      */         }
/* 4463 */         else if ((Crunch == 2) && (j < 10)) {
/* 4464 */           Print(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(FormatReal(this.CurrentSimplexTableau.ObjectiveFunction[j], 5, 1, 1)))).append("X").append(FormatInteger(j, 1, 2, 0)))));
/*      */         }
/*      */       }
/*      */       
/* 4468 */       if (Math.abs(this.CurrentSimplexTableau.ZM) > 1.0E-5D) {
/* 4469 */         PrintLine("= ".concat(String.valueOf(String.valueOf(FormatReal(this.CurrentSimplexTableau.Z, 5, 1, 0)))));
/*      */       }
/*      */       else {
/* 4472 */         PrintLine("= ".concat(String.valueOf(String.valueOf(FormatReal(this.CurrentSimplexTableau.Z, 5, 0, 0)))));
/*      */       }
/* 4474 */       for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 4475 */         Print(String.valueOf(String.valueOf(FormatInteger(i, 1, 2, 0))).concat(")   "));
/* 4476 */         for (j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/* 4477 */           if (j == 1) {
/* 4478 */             Print(FormatReal(this.CurrentSimplexTableau.T[i][j], 5, 0, 1));
/*      */           }
/*      */           else {
/* 4481 */             Print(FormatReal(this.CurrentSimplexTableau.T[i][j], 5, 1, 1));
/*      */           }
/* 4483 */           if (Crunch == 0) {
/* 4484 */             Print(" X".concat(String.valueOf(String.valueOf(FormatInteger(j, 2, 2, 0)))));
/* 4485 */           } else if ((Crunch == 1) || (j == 10)) {
/* 4486 */             Print("X".concat(String.valueOf(String.valueOf(FormatInteger(j, 2, 2, 0)))));
/*      */           } else
/* 4488 */             Print("X".concat(String.valueOf(String.valueOf(FormatInteger(j, 1, 2, 0)))));
/*      */         }
/* 4490 */         switch (this.CurrentSimplexTableau.Constraint[i]) {
/*      */         case 0: 
/* 4492 */           Print("<= ");
/* 4493 */           break;
/*      */         case 1: 
/* 4495 */           Print("= ");
/* 4496 */           break;
/*      */         case 2: 
/* 4498 */           Print(">= ");
/*      */         }
/*      */         
/* 4501 */         PrintLine(FormatReal(this.CurrentSimplexTableau.RightHandSide[i], 5, 0, 0));
/*      */       }
/*      */       
/* 4504 */       SkipLine();
/* 4505 */       PrintLine("and");
/* 4506 */       Print("     ");
/* 4507 */       for (j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/* 4508 */         if (this.CurrentSimplexTableau.Nonnegativity[j] != 0) {
/* 4509 */           if (j >= 10) {
/* 4510 */             Print("Xe >= ".concat(String.valueOf(String.valueOf(RealToString(this.CurrentSimplexTableau.LowerBound[j], 5)))));
/*      */           }
/*      */           else
/*      */           {
/* 4514 */             Print(String.valueOf(String.valueOf(new StringBuffer("X").append(FormatInteger(j, 1, 2, 0)).append(" >= ").append(RealToString(this.CurrentSimplexTableau.LowerBound[j], 5)))));
/*      */           }
/*      */           
/* 4517 */           if (j == this.CurrentSimplexTableau.NumVariables) {
/* 4518 */             Print(".");
/*      */           } else {
/* 4520 */             Print(", ");
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 4525 */     Print("Bas|Eq|");
/* 4526 */     TabPrint(3 * this.CurrentSimplexTableau.NumVariables + 4, "Coefficient of");
/*      */     
/* 4528 */     TabPrintLine(6 * this.CurrentSimplexTableau.NumVariables + 12, "| Right");
/* 4529 */     Print("Var|No| Z|");
/* 4530 */     for (int j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++)
/* 4531 */       Print("   X".concat(String.valueOf(String.valueOf(FormatInteger(j, 2, 2, 0)))));
/* 4532 */     PrintLine(" |  side");
/* 4533 */     Print("___|__|__|");
/* 4534 */     for (j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++)
/* 4535 */       Print("______");
/* 4536 */     PrintLine("_|______");
/* 4537 */     Print("   |  |  |");
/* 4538 */     for (j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/* 4539 */       if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunctionM[j]) > 1.0E-6D)
/*      */       {
/* 4541 */         Print(String.valueOf(String.valueOf(FormatReal(this.CurrentSimplexTableau.ObjectiveFunctionM[j], 4, 0, 1))).concat("M "));
/*      */       }
/*      */       else
/* 4544 */         Print("      ");
/*      */     }
/* 4546 */     Print(" | ");
/* 4547 */     if (Math.abs(this.CurrentSimplexTableau.ZM) > 1.0E-6D) {
/* 4548 */       PrintLine(String.valueOf(String.valueOf(FormatReal(this.CurrentSimplexTableau.ZM, 4, 0, 1))).concat("M"));
/*      */     }
/*      */     else
/* 4551 */       SkipLine();
/* 4552 */     if (this.CurrentSimplexTableau.Objective != this.SimplexModel.Objective) {
/* 4553 */       Print(" Z | 0|-1|");
/*      */     } else
/* 4555 */       Print(" Z | 0| 1|");
/* 4556 */     for (j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/* 4557 */       if (Math.abs(this.CurrentSimplexTableau.ObjectiveFunctionM[j]) > 1.0E-4D)
/*      */       {
/* 4559 */         Print(FormatReal(this.CurrentSimplexTableau.ObjectiveFunction[j], 5, 1, 1));
/*      */       }
/*      */       else {
/* 4562 */         Print(FormatReal(this.CurrentSimplexTableau.ObjectiveFunction[j], 5, 0, 1));
/*      */       }
/* 4564 */       if ((this.ApplicationNumber != 1) || (this.CommandNumber != 7))
/*      */       {
/* 4566 */         Print(" ");
/*      */       } else
/* 4568 */         Print(" ");
/*      */     }
/* 4570 */     PrintLine(" | ".concat(String.valueOf(String.valueOf(FormatReal(this.CurrentSimplexTableau.Z, 5, 0, 1)))));
/*      */     
/* 4572 */     for (int i = 1; i <= this.CurrentSimplexTableau.NumConstraints; i++) {
/* 4573 */       if (Math.round(this.CurrentSimplexTableau.BasicVariable[i]) == 10) {
/* 4574 */         Print("X10");
/*      */       } else {
/* 4576 */         Print(" X".concat(String.valueOf(String.valueOf(FormatInteger((int)Math.round(this.CurrentSimplexTableau.BasicVariable[i]), 1, 0, 0)))));
/*      */       }
/*      */       
/* 4579 */       Print(String.valueOf(String.valueOf(new StringBuffer("| ").append(FormatInteger(i, 1, 0, 0)).append("| 0|"))));
/* 4580 */       for (j = 1; j <= this.CurrentSimplexTableau.NumVariables; j++) {
/* 4581 */         Print(FormatReal(this.CurrentSimplexTableau.T[i][j], 5, 0, 1));
/*      */         
/* 4583 */         if ((this.ApplicationNumber == 1) && ((this.CommandNumber == 6) || (this.CommandNumber == 8)) && (this.CurrentSimplexTableau.LeavingBasicVariableEquation == i) && (this.CurrentSimplexTableau.EnteringBasicVariable == j))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4589 */           Print("*");
/*      */         } else
/* 4591 */           Print(" ");
/*      */       }
/* 4593 */       PrintLine(" | ".concat(String.valueOf(String.valueOf(FormatReal(this.CurrentSimplexTableau.RightHandSide[i], 5, 0, 1)))));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void PrintSimplexSensitivityAnalysis()
/*      */   {
/* 4605 */     ORSolverBase.SimplexTableauType TempHandle = (ORSolverBase.SimplexTableauType)this.CurrentSimplexTableau.clone();
/*      */     
/* 4607 */     PrintLine("Linear Programming Model:");
/* 4608 */     SkipLine();
/* 4609 */     PrintLine("Number of Decision Variables:     ".concat(String.valueOf(String.valueOf(FormatInteger(this.SimplexModel.NumVariables, 2, 0, 1)))));
/*      */     
/* 4611 */     SkipLine();
/* 4612 */     PrintLine("Number of Functional Constraints: ".concat(String.valueOf(String.valueOf(FormatInteger(this.SimplexModel.NumConstraints, 2, 0, 1)))));
/*      */     
/* 4614 */     SkipLine();
/* 4615 */     PrintSimplexModel();
/* 4616 */     Skip(3);
/* 4617 */     PrintLine("Sensitivity Analysis:");
/* 4618 */     SkipLine();
/*      */     
/* 4620 */     PrintLines(this.SimplexModel.NumConstraints + 8);
/* 4621 */     PrintLine("Original Initial Tableau:");
/* 4622 */     SkipLine();
/* 4623 */     this.CurrentSimplexTableau = this.FirstSimplexTableau_bak;
/* 4624 */     PrintSimplexTableau();
/* 4625 */     SkipLine();
/*      */     
/* 4627 */     PrintLines(this.SimplexModel.NumConstraints + 8);
/* 4628 */     PrintLine("Original Final Tableau:");
/* 4629 */     SkipLine();
/* 4630 */     this.CurrentSimplexTableau = this.CurrentSimplexTableau_bak;
/* 4631 */     PrintSimplexTableau();
/* 4632 */     SkipLine();
/*      */     
/* 4634 */     PrintLines(this.SimplexModel.NumConstraints + 8);
/* 4635 */     PrintLine("New Initial Tableau:");
/* 4636 */     SkipLine();
/* 4637 */     this.CurrentSimplexTableau = this.FirstSimplexTableau;
/* 4638 */     PrintSimplexTableau();
/* 4639 */     SkipLine();
/*      */     
/* 4641 */     PrintLines(this.SimplexModel.NumConstraints + 8);
/* 4642 */     PrintLine("Revised Final Tableau:");
/* 4643 */     SkipLine();
/* 4644 */     if ((this.SensitivitySimplexPerformed == false) && (this.SensitivityDualSimplexPerformed == false) && (this.SensitivityConvertToProperFormPerformed == false))
/*      */     {
/*      */ 
/* 4647 */       this.CurrentSimplexTableau = TempHandle;
/* 4648 */     } else if (((this.SensitivitySimplexPerformed) || (this.SensitivityDualSimplexPerformed)) && (this.SensitivityConvertToProperFormPerformed == false))
/*      */     {
/*      */ 
/* 4651 */       this.CurrentSimplexTableau = this.CurrentSimplexTableau_bak_for_sen_print2;
/*      */     } else
/* 4653 */       this.CurrentSimplexTableau = this.CurrentSimplexTableau_bak_for_sen_print1;
/* 4654 */     PrintSimplexTableau();
/* 4655 */     SkipLine();
/*      */     
/* 4657 */     if (this.SensitivityConvertToProperFormPerformed) {
/* 4658 */       PrintLines(this.SimplexModel.NumConstraints + 8);
/* 4659 */       PrintLine("Revised Final Tableau After Converting to Proper Form:");
/* 4660 */       SkipLine();
/* 4661 */       if ((this.SensitivitySimplexPerformed) || (this.SensitivityDualSimplexPerformed)) {
/* 4662 */         this.CurrentSimplexTableau = this.CurrentSimplexTableau_bak_for_sen_print2;
/*      */       }
/*      */       else
/* 4665 */         this.CurrentSimplexTableau = TempHandle;
/* 4666 */       PrintSimplexTableau();
/* 4667 */       SkipLine();
/*      */     }
/*      */     
/* 4670 */     if ((this.SensitivitySimplexPerformed) || (this.SensitivityDualSimplexPerformed)) {
/* 4671 */       PrintLines(this.SimplexModel.NumConstraints + 8);
/* 4672 */       if (this.SensitivitySimplexPerformed) {
/* 4673 */         PrintLine("Revised Final Tableau After Reoptimization (Simplex Method):");
/*      */       }
/*      */       else {
/* 4676 */         PrintLine("Revised Final Tableau After Reoptimization (Dual Simplex Method):");
/*      */       }
/* 4678 */       SkipLine();
/* 4679 */       this.CurrentSimplexTableau = TempHandle;
/* 4680 */       PrintSimplexTableau();
/*      */     }
/* 4682 */     this.CurrentSimplexTableau = TempHandle;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORLPSolver.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */