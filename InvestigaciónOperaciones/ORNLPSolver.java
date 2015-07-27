/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
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
/*      */ public class ORNLPSolver
/*      */   extends ORLPSolver
/*      */ {
/*      */   private double TempOneDSearchUpperBound;
/*      */   private double TempOneDSearchLowerBound;
/*      */   private double TempOneDSearchErrorTolerance;
/*   31 */   private ORSolverBase.PolynomialType OneDPolynomial = new ORSolverBase.PolynomialType(this);
/*   32 */   private ORSolverBase.PolynomialType OneDDerivative = new ORSolverBase.PolynomialType(this);
/*   33 */   private ORSolverBase.OneDSearchType CurrentOneDSearch = new ORSolverBase.OneDSearchType(this);
/*   34 */   private ORSolverBase.OneDSearchType FirstOneDSearch = new ORSolverBase.OneDSearchType(this);
/*      */   private double OneDAnswer;
/*      */   private double OneDValue;
/*      */   private int OneDObjective;
/*   38 */   private ORSolverBase.GradientSearchType CurrentGradientSearch = null;
/*   39 */   private ORSolverBase.GradientSearchType FirstGradientSearch = null;
/*   40 */   private ORSolverBase.PolynomialType[] TheDerivative = new ORSolverBase.PolynomialType[4];
/*      */   
/*      */   private double ErrorTol;
/*      */   
/*      */   private int CurrentIteration;
/*      */   
/*      */   private int CurrentAlgorithm;
/*      */   private boolean NewXCalculated;
/*      */   private ORNLPSolver.FrankWolfeType CurrentFrankWolfe;
/*   49 */   private ORNLPSolver.AnnealingType currentAType = new ORNLPSolver.AnnealingType(null);
/*      */   
/*      */   public SimulateAnnealing anneal;
/*      */   
/*      */   public ORNLPSolver.GeneType getype;
/*      */   
/*      */   public Population pop;
/*   56 */   private double[] GradientValue = new double[4];
/*      */   
/*   58 */   private ORNLPSolver.SUMTModelType SUMTModel = new ORNLPSolver.SUMTModelType();
/*   59 */   private ORNLPSolver.SUMTType CurrentSUMT = new ORNLPSolver.SUMTType(null);
/*      */   private class FrankWolfeType implements Serializable { private FrankWolfeType() {}
/*   61 */     FrankWolfeType(ORNLPSolver..1 x$1) { this(); }
/*   62 */     double[] Solution = new double[11];
/*   63 */     double[] ObjectiveFunction = new double[11];
/*   64 */     double[] FWLPSolution = new double[11];
/*   65 */     double[] NewSolution = new double[11];
/*   66 */     double[] Vector1 = new double[11];
/*   67 */     double[] Vector2 = new double[11];
/*   68 */     double[] Vector3 = new double[11];
/*   69 */     double[] a = new double[11];
/*   70 */     double[] b = new double[11];
/*      */     double t; }
/*      */   
/*      */   private class AnnealingType implements Serializable { double PolyResult;
/*      */     
/*      */     private AnnealingType() {}
/*      */     
/*   77 */     AnnealingType(ORNLPSolver..1 x$1) { this(); }
/*      */     
/*      */ 
/*      */     ArrayList[] An_list;
/*   81 */     int A_size = 20;
/*      */   }
/*      */   
/*      */ 
/*      */   private class GeneType
/*      */     implements Serializable
/*      */   {
/*      */     ArrayList[] ge_list;
/*      */     
/*      */     private GeneType() {}
/*      */     
/*   92 */     int ge_size = 0;
/*   93 */     int currentindex = 0;
/*   94 */     Vector best = new Vector();
/*   95 */     Vector storePopulation = new Vector();
/*   96 */     Vector generation = new Vector();
/*      */     
/*      */     public void setCurrentIndex(int n) {
/*   99 */       this.currentindex = n;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private class SUMTModelType
/*      */     implements Serializable
/*      */   {
/*      */     int NumVariableswlb;
/*      */     
/*      */     int NumVariableswolb;
/*      */     
/*      */     int NumVariables;
/*      */     int NumInequalityConstraints;
/*      */     int NumEqualityConstraints;
/*      */     int NumConstraints;
/*  115 */     ORSolverBase.PolynomialType[] B = new ORSolverBase.PolynomialType[6];
/*  116 */     ORSolverBase.PolynomialType[] L = new ORSolverBase.PolynomialType[6];
/*  117 */     ORSolverBase.PolynomialType f = new ORSolverBase.PolynomialType(ORNLPSolver.this);
/*      */     
/*      */     public SUMTModelType()
/*      */     {
/*  121 */       for (int i = 0; i < 6; i++) {
/*  122 */         ORNLPSolver tmp60_59 = ORNLPSolver.this;tmp60_59.getClass();this.B[i] = new ORSolverBase.PolynomialType(tmp60_59); ORNLPSolver 
/*  123 */           tmp79_78 = ORNLPSolver.this;tmp79_78.getClass();this.L[i] = new ORSolverBase.PolynomialType(tmp79_78);
/*      */       } } }
/*      */   
/*      */   private class SUMTType implements Serializable { private SUMTType() {}
/*      */     
/*  128 */     SUMTType(ORNLPSolver..1 x$1) { this(); }
/*  129 */     double[] InitialSolution = new double[11];
/*      */     double r;
/*  131 */     double[] FinalSolution = new double[11];
/*      */     
/*      */     SUMTType Last;
/*      */     
/*      */     SUMTType Next;
/*      */   }
/*      */   
/*      */ 
/*      */   private void DoAutoOneD()
/*      */   {
/*  141 */     this.CommandNumber = 1;
/*  142 */     my_InitializeOneD();
/*      */     
/*  144 */     double CurrentError = Math.abs((this.TempOneDSearchUpperBound - this.TempOneDSearchLowerBound) / 2);
/*  145 */     int NumIt = 0;
/*  146 */     while (CurrentError / Math.pow(2.0D, NumIt) > this.TempOneDSearchErrorTolerance)
/*  147 */       NumIt += 1;
/*  148 */     double Answer = DoOneDimensionalSearch(this.OneDPolynomial, this.TempOneDSearchLowerBound, this.TempOneDSearchUpperBound, NumIt);
/*  149 */     OutputOneDResult();
/*      */   }
/*      */   
/*      */   private void DoAutoGradient()
/*      */   {
/*  154 */     for (int i = 1; i < this.TheDerivative.length; i++) {
/*  155 */       this.TheDerivative[i] = new ORSolverBase.PolynomialType(this);
/*      */     }
/*  157 */     InitializeGradientModel();
/*  158 */     my_InitializeGradient();
/*  159 */     DoGradientSearch();
/*  160 */     OutputGradientResult();
/*      */   }
/*      */   
/*      */ 
/*      */   private void my_InitializeOneD()
/*      */   {
/*  166 */     this.OneDObjective = 0;
/*  167 */     this.OneDPolynomial.NumVariables = 1;
/*  168 */     this.OneDPolynomial.NumTerms = 3;
/*      */     
/*  170 */     this.OneDPolynomial.Coefficient[1] = 12.0D;
/*  171 */     this.OneDPolynomial.Exponent[1][1] = 1;
/*  172 */     this.OneDPolynomial.Coefficient[2] = -3.0D;
/*  173 */     this.OneDPolynomial.Exponent[2][1] = 4;
/*  174 */     this.OneDPolynomial.Coefficient[3] = -2.0D;
/*  175 */     this.OneDPolynomial.Exponent[3][1] = 6;
/*      */     
/*  177 */     this.TempOneDSearchLowerBound = 0.0D;
/*  178 */     this.TempOneDSearchUpperBound = 2.0D;
/*      */     
/*  180 */     this.TempOneDSearchErrorTolerance = 0.01D;
/*      */   }
/*      */   
/*      */   private void my_InitializeGradient()
/*      */   {
/*  185 */     this.FirstGradientSearch.InitialSolution[1] = 0.0D;
/*  186 */     this.FirstGradientSearch.InitialSolution[2] = 0.0D;
/*      */     
/*  188 */     this.ThePolynomial.NumVariables = 2;
/*  189 */     this.ThePolynomial.NumTerms = 4;
/*      */     
/*  191 */     this.ThePolynomial.Coefficient[1] = 2.0D;
/*  192 */     this.ThePolynomial.Exponent[1][1] = 1;
/*  193 */     this.ThePolynomial.Exponent[1][2] = 1;
/*  194 */     this.ThePolynomial.Coefficient[2] = 2.0D;
/*  195 */     this.ThePolynomial.Exponent[2][1] = 0;
/*  196 */     this.ThePolynomial.Exponent[2][2] = 1;
/*  197 */     this.ThePolynomial.Coefficient[3] = -1.0D;
/*  198 */     this.ThePolynomial.Exponent[3][1] = 2;
/*  199 */     this.ThePolynomial.Exponent[3][2] = 0;
/*  200 */     this.ThePolynomial.Coefficient[4] = -2.0D;
/*  201 */     this.ThePolynomial.Exponent[4][1] = 0;
/*  202 */     this.ThePolynomial.Exponent[4][2] = 2;
/*      */     
/*  204 */     this.ErrorTol = 0.01D;
/*      */   }
/*      */   
/*      */ 
/*      */   private void InitializeNonlinear()
/*      */   {
/*  210 */     this.CommandNumber = 2;
/*  211 */     InitializeOneDSearchList();
/*      */     
/*      */ 
/*      */ 
/*  215 */     this.OneDPolynomial = new ORSolverBase.PolynomialType(this);
/*  216 */     this.OneDDerivative = new ORSolverBase.PolynomialType(this);
/*  217 */     this.OneDPolynomial.NumVariables = 1;
/*  218 */     this.OneDDerivative.NumVariables = 1;
/*  219 */     this.TempOneDSearchUpperBound = 0.0D;
/*  220 */     this.TempOneDSearchLowerBound = 0.0D;
/*      */   }
/*      */   
/*      */   private void InitializeOneDSearchList()
/*      */   {
/*  225 */     this.FirstOneDSearch = null;
/*  226 */     this.CurrentOneDSearch = null;
/*  227 */     this.OneDAnswer = 0.0D;
/*  228 */     this.OneDValue = 0.0D;
/*      */   }
/*      */   
/*      */ 
/*      */   private double DoOneDimensionalSearch(ORSolverBase.PolynomialType Polynomial, double LowerBound, double UpperBound, int NumIterations)
/*      */   {
/*  234 */     double[] Vector = new double[11];
/*      */     
/*  236 */     this.OneDPolynomial = Polynomial;
/*  237 */     DifferentiatePolynomial(this.OneDPolynomial, 1, this.OneDDerivative);
/*  238 */     InitializeOneDSearchList();
/*  239 */     this.TempOneDSearchLowerBound = LowerBound;
/*  240 */     this.TempOneDSearchUpperBound = UpperBound;
/*  241 */     CreateFirstOneDSearch(LowerBound, UpperBound);
/*  242 */     int ItsMin; int ItsMin; if ((this.CommandNumber == 1) && (this.OneDObjective == 1)) {
/*  243 */       ItsMin = 1;
/*      */     } else
/*  245 */       ItsMin = 0;
/*  246 */     if (this.CommandNumber == 9)
/*      */     {
/*  248 */       if (this.SimplexModel.Objective == 1) {
/*  249 */         ItsMin = 1;
/*      */       } else
/*  251 */         ItsMin = 0;
/*      */     }
/*  253 */     for (int i = 1; i <= NumIterations; i++)
/*      */     {
/*  255 */       if (((this.CurrentOneDSearch.Derivative < 0) && (ItsMin == 0)) || ((this.CurrentOneDSearch.Derivative >= 0) && (ItsMin == 1))) {
/*  256 */         UpperBound = this.CurrentOneDSearch.NewBound;
/*      */       } else
/*  258 */         LowerBound = this.CurrentOneDSearch.NewBound;
/*  259 */       this.CurrentOneDSearch.NewBound = ((LowerBound + UpperBound) / 2);
/*  260 */       Vector[1] = this.CurrentOneDSearch.NewBound;
/*  261 */       this.CurrentOneDSearch.Derivative = EvaluatePolynomial(this.OneDDerivative, Vector);
/*      */     }
/*  263 */     this.CurrentOneDSearch.Value = EvaluatePolynomial(this.OneDPolynomial, Vector);
/*  264 */     return this.CurrentOneDSearch.NewBound;
/*      */   }
/*      */   
/*      */   private boolean JudgeConstraints(ORSolverBase.SimplexModelType SimplexModel, double[] temp)
/*      */   {
/*  269 */     for (int i = 0; i < SimplexModel.NumConstraints; i++) {
/*  270 */       int oneresult = 0;
/*  271 */       for (int j = 0; j < SimplexModel.NumVariables; j++) {
/*  272 */         oneresult = (int)(oneresult + SimplexModel.A[(i + 1)][(j + 1)] * temp[j]);
/*      */       }
/*      */       
/*  275 */       double judgeresult = oneresult - SimplexModel.RightHandSide[(i + 1)];
/*      */       
/*  277 */       if (SimplexModel.Constraint[(i + 1)] == 0) {
/*  278 */         if (judgeresult > 0) return false;
/*      */       }
/*  280 */       else if (SimplexModel.Constraint[(i + 1)] == 1) {
/*  281 */         if (judgeresult != 0) return false;
/*      */       }
/*  283 */       else if ((SimplexModel.Constraint[(i + 1)] == 2) && 
/*  284 */         (judgeresult < 0)) { return false;
/*      */       }
/*      */     }
/*  287 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private double EvaluatePolynomial(ORSolverBase.PolynomialType Polynomial, double[] Vector)
/*      */   {
/*  297 */     double TempSum = 0.0D;
/*  298 */     for (int i = 1; i <= Polynomial.NumTerms; i++)
/*      */     {
/*  300 */       double Temp = Polynomial.Coefficient[i];
/*  301 */       for (int j = 1; j <= Polynomial.NumVariables; j++)
/*      */       {
/*  303 */         if (Polynomial.Exponent[i][j] != 0)
/*  304 */           Temp *= Math.pow(Vector[j], Polynomial.Exponent[i][j]);
/*      */       }
/*  306 */       TempSum += Temp;
/*      */     }
/*  308 */     return TempSum;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void DifferentiatePolynomial(ORSolverBase.PolynomialType Polynomial, int WhichVar, ORSolverBase.PolynomialType Derivative)
/*      */   {
/*  317 */     Derivative.NumTerms = 0;
/*  318 */     Derivative.NumVariables = Polynomial.NumVariables;
/*  319 */     for (int i = 1; i <= Polynomial.NumTerms; i++)
/*      */     {
/*  321 */       if (Polynomial.Exponent[i][WhichVar] > 0)
/*      */       {
/*  323 */         Derivative.NumTerms += 1;
/*  324 */         Derivative.Coefficient[Derivative.NumTerms] = (Polynomial.Coefficient[i] * Polynomial.Exponent[i][WhichVar]);
/*  325 */         for (int j = 1; j <= Polynomial.NumVariables; j++)
/*      */         {
/*  327 */           if (j == WhichVar) {
/*  328 */             Derivative.Exponent[Derivative.NumTerms][j] = (Polynomial.Exponent[i][j] - 1);
/*      */           } else {
/*  330 */             Derivative.Exponent[Derivative.NumTerms][j] = Polynomial.Exponent[i][j];
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void CreateFirstOneDSearch(double LowerBound, double UpperBound)
/*      */   {
/*  339 */     double[] Vector = new double[11];
/*      */     
/*  341 */     ORSolverBase.OneDSearchType NewOneDSearch = new ORSolverBase.OneDSearchType(this);
/*  342 */     this.FirstOneDSearch = NewOneDSearch;
/*  343 */     this.CurrentOneDSearch = NewOneDSearch;
/*  344 */     this.FirstOneDSearch.LowerBound = LowerBound;
/*  345 */     this.FirstOneDSearch.UpperBound = UpperBound;
/*  346 */     this.FirstOneDSearch.NewBound = ((LowerBound + UpperBound) / 2);
/*  347 */     Vector[1] = this.FirstOneDSearch.NewBound;
/*  348 */     this.FirstOneDSearch.Value = EvaluatePolynomial(this.OneDPolynomial, Vector);
/*  349 */     this.FirstOneDSearch.Derivative = EvaluatePolynomial(this.OneDDerivative, Vector);
/*      */   }
/*      */   
/*      */ 
/*      */   private void InitializeGradientModel()
/*      */   {
/*  355 */     this.CommandNumber = 3;
/*  356 */     InitializeOneDSearchList();
/*  357 */     InitializeGradientSearchList();
/*      */     
/*      */ 
/*  360 */     CreateFirstGradientSearch();
/*  361 */     this.ThePolynomial = new ORSolverBase.PolynomialType(this);
/*      */   }
/*      */   
/*      */   private void InitializeGradientSearchList()
/*      */   {
/*  366 */     this.FirstGradientSearch = null;
/*  367 */     this.CurrentGradientSearch = null;
/*      */   }
/*      */   
/*      */   private void CreateFirstGradientSearch()
/*      */   {
/*  372 */     ORSolverBase.GradientSearchType NewGradientSearch = new ORSolverBase.GradientSearchType(this);
/*      */     
/*  374 */     this.FirstGradientSearch = NewGradientSearch;
/*  375 */     this.CurrentGradientSearch = NewGradientSearch;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void DoGradientSearch()
/*      */   {
/*  384 */     for (int i = 1; i <= 2; i++)
/*  385 */       DifferentiatePolynomial(this.ThePolynomial, i, this.TheDerivative[i]);
/*  386 */     for (i = 1; i <= 2; i++)
/*  387 */       this.CurrentGradientSearch.Gradient[i] = EvaluatePolynomial(this.TheDerivative[i], this.CurrentGradientSearch.InitialSolution);
/*  388 */     double TheMax = 0.0D;
/*  389 */     for (i = 1; i <= 2; i++)
/*      */     {
/*  391 */       if (Math.abs(this.CurrentGradientSearch.Gradient[i]) > TheMax)
/*  392 */         TheMax = Math.abs(this.CurrentGradientSearch.Gradient[i]);
/*      */     }
/*  394 */     int It = 0;
/*  395 */     while ((TheMax > this.ErrorTol * 1.01D) && (It < 30) && (Math.abs(this.CurrentGradientSearch.InitialSolution[1]) < 1.0E10D) && (Math.abs(this.CurrentGradientSearch.InitialSolution[2]) < 1.0E10D))
/*      */     {
/*  397 */       It += 1;
/*  398 */       for (i = 1; i <= 2; i++)
/*      */       {
/*  400 */         this.CurrentGradientSearch.a[i] = this.CurrentGradientSearch.InitialSolution[i];
/*  401 */         this.CurrentGradientSearch.b[i] = this.CurrentGradientSearch.Gradient[i];
/*      */       }
/*  403 */       ConvertPolyToOneD(this.ThePolynomial, this.CurrentGradientSearch.a, this.CurrentGradientSearch.b, this.OneDPolynomial);
/*  404 */       double UpperBd = 0.2D;
/*      */       do {
/*  406 */         UpperBd *= 10;
/*  407 */         this.CurrentGradientSearch.t = DoOneDimensionalSearch(this.OneDPolynomial, 0.0D, UpperBd, 20);
/*  408 */       } while ((this.CurrentGradientSearch.t >= 0.9D * UpperBd) && (UpperBd < 10000000));
/*  409 */       for (i = 1; i <= 2; i++)
/*  410 */         this.CurrentGradientSearch.NewSolution[i] = (this.CurrentGradientSearch.InitialSolution[i] + this.CurrentGradientSearch.t * this.CurrentGradientSearch.Gradient[i]);
/*  411 */       arraycopy1(this.CurrentGradientSearch.NewSolution, this.CurrentGradientSearch.InitialSolution);
/*      */       
/*  413 */       this.CurrentGradientSearch.Gradient[1] = EvaluatePolynomial(this.TheDerivative[1], this.CurrentGradientSearch.InitialSolution);
/*  414 */       this.CurrentGradientSearch.Gradient[2] = EvaluatePolynomial(this.TheDerivative[2], this.CurrentGradientSearch.InitialSolution);
/*  415 */       TheMax = 0.0D;
/*  416 */       for (i = 1; i <= 2; i++)
/*      */       {
/*  418 */         if (Math.abs(this.CurrentGradientSearch.Gradient[i]) > TheMax) {
/*  419 */           TheMax = Math.abs(this.CurrentGradientSearch.Gradient[i]);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void ConvertPolyToOneD(ORSolverBase.PolynomialType Polynomial, double[] a, double[] b, ORSolverBase.PolynomialType OneDPolynomial) {
/*  426 */     ORSolverBase.PolynomialType[] TempPoly = new ORSolverBase.PolynomialType[4];
/*  427 */     ORSolverBase.PolynomialType Temp = new ORSolverBase.PolynomialType(this);
/*  428 */     ORSolverBase.PolynomialType t = (ORSolverBase.PolynomialType)Polynomial.clone();
/*      */     
/*      */ 
/*  431 */     for (int i = 1; i < 4; i++)
/*  432 */       TempPoly[i] = new ORSolverBase.PolynomialType(this);
/*  433 */     OneDPolynomial.ZeroAll();
/*  434 */     OneDPolynomial.NumVariables = 1;
/*  435 */     OneDPolynomial.NumTerms = 0;
/*  436 */     for (i = 1; i <= t.NumTerms; i++)
/*      */     {
/*  438 */       for (int j = 1; j <= Polynomial.NumVariables; j++)
/*  439 */         TakeOneDToExponent(a[j], b[j], Polynomial.Exponent[i][j], TempPoly[j]);
/*  440 */       switch (Polynomial.NumVariables)
/*      */       {
/*      */       case 1: 
/*  443 */         Temp = (ORSolverBase.PolynomialType)TempPoly[1].clone();
/*  444 */         break;
/*      */       case 2: 
/*  446 */         MultiplyPolynomials(TempPoly[1], TempPoly[2], Temp);
/*  447 */         break;
/*      */       case 3: 
/*  449 */         MultiplyPolynomials(TempPoly[1], TempPoly[2], Temp);
/*  450 */         MultiplyPolynomials(Temp, TempPoly[3], Temp);
/*      */       }
/*      */       
/*  453 */       MultiplyPolynomialByConstant(Polynomial.Coefficient[i], Temp);
/*  454 */       AddPolynomials(OneDPolynomial, Temp, OneDPolynomial);
/*      */     }
/*      */   }
/*      */   
/*      */   private void TakeOneDToExponent(double a, double b, int Exponent, ORSolverBase.PolynomialType Polynomial)
/*      */   {
/*  460 */     ORSolverBase.PolynomialType TempPoly = new ORSolverBase.PolynomialType(this);
/*      */     
/*  462 */     Polynomial.ZeroAll();
/*  463 */     Polynomial.NumTerms = 1;
/*  464 */     Polynomial.NumVariables = 1;
/*  465 */     Polynomial.Coefficient[1] = 1.0D;
/*  466 */     Polynomial.Exponent[1][1] = 0;
/*      */     
/*  468 */     TempPoly.NumTerms = 2;
/*  469 */     TempPoly.NumVariables = 1;
/*  470 */     TempPoly.Coefficient[1] = a;
/*  471 */     TempPoly.Exponent[1][1] = 0;
/*  472 */     TempPoly.Coefficient[2] = b;
/*  473 */     TempPoly.Exponent[2][1] = 1;
/*  474 */     while (Exponent > 0)
/*      */     {
/*  476 */       Exponent -= 1;
/*  477 */       MultiplyPolynomials(Polynomial, TempPoly, Polynomial);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void MultiplyPolynomials(ORSolverBase.PolynomialType Polynomial1, ORSolverBase.PolynomialType Polynomial2, ORSolverBase.PolynomialType Polynomial3)
/*      */   {
/*  484 */     ORSolverBase.PolynomialType P1 = (ORSolverBase.PolynomialType)Polynomial1.clone();
/*  485 */     ORSolverBase.PolynomialType P2 = (ORSolverBase.PolynomialType)Polynomial2.clone();
/*      */     
/*  487 */     Polynomial3.ZeroAll();
/*  488 */     Polynomial3.NumVariables = Math.max(P1.NumVariables, P2.NumVariables);
/*  489 */     for (int i = 1; i <= P1.NumTerms; i++)
/*      */     {
/*  491 */       for (int j = 1; j <= P2.NumTerms; j++)
/*      */       {
/*  493 */         Polynomial3.NumTerms += 1;
/*  494 */         Polynomial3.Coefficient[Polynomial3.NumTerms] = (P1.Coefficient[i] * P2.Coefficient[j]);
/*  495 */         for (int k = 1; k <= Polynomial3.NumVariables; k++)
/*  496 */           Polynomial3.Exponent[Polynomial3.NumTerms][k] = (P1.Exponent[i][k] + P2.Exponent[j][k]);
/*      */       }
/*      */     }
/*  499 */     SimplifyPolynomial(Polynomial3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void MultiplyPolynomialByConstant(double Constant, ORSolverBase.PolynomialType ThePolynomial)
/*      */   {
/*  506 */     for (int i = 1; i <= ThePolynomial.NumTerms; i++) {
/*  507 */       ThePolynomial.Coefficient[i] *= Constant;
/*      */     }
/*      */   }
/*      */   
/*      */   private void AddPolynomials(ORSolverBase.PolynomialType Polynomial1, ORSolverBase.PolynomialType Polynomial2, ORSolverBase.PolynomialType Polynomial3)
/*      */   {
/*  513 */     ORSolverBase.PolynomialType P1 = (ORSolverBase.PolynomialType)Polynomial1.clone();
/*  514 */     ORSolverBase.PolynomialType P2 = (ORSolverBase.PolynomialType)Polynomial2.clone();
/*      */     
/*  516 */     Polynomial3.ZeroAll();
/*  517 */     Polynomial3.NumVariables = Math.max(P2.NumVariables, P1.NumVariables);
/*  518 */     P1.NumTerms += P2.NumTerms;
/*  519 */     for (int i = 1; i <= P1.NumTerms; i++)
/*      */     {
/*  521 */       Polynomial3.Coefficient[i] = P1.Coefficient[i];
/*  522 */       for (int j = 1; j <= P1.NumVariables; j++)
/*  523 */         Polynomial3.Exponent[i][j] = P1.Exponent[i][j];
/*      */     }
/*  525 */     for (i = 1; i <= P2.NumTerms; i++)
/*      */     {
/*  527 */       Polynomial3.Coefficient[(i + P1.NumTerms)] = P2.Coefficient[i];
/*  528 */       for (int j = 1; j <= P2.NumVariables; j++)
/*  529 */         Polynomial3.Exponent[(i + P1.NumTerms)][j] = P2.Exponent[i][j];
/*      */     }
/*  531 */     SimplifyPolynomial(Polynomial3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void SimplifyPolynomial(ORSolverBase.PolynomialType Polynomial)
/*      */   {
/*  540 */     int i = 1;
/*  541 */     while (i < Polynomial.NumTerms)
/*      */     {
/*  543 */       int j = i + 1;
/*  544 */       while (j <= Polynomial.NumTerms)
/*      */       {
/*  546 */         boolean CanSimplify = true;
/*  547 */         for (int k = 1; k <= Polynomial.NumVariables; k++)
/*      */         {
/*  549 */           if (Polynomial.Exponent[i][k] != Polynomial.Exponent[j][k])
/*  550 */             CanSimplify = false;
/*      */         }
/*  552 */         if (CanSimplify)
/*      */         {
/*  554 */           Polynomial.Coefficient[i] += Polynomial.Coefficient[j];
/*  555 */           for (k = j; k <= Polynomial.NumTerms - 1; k++)
/*      */           {
/*  557 */             Polynomial.Coefficient[k] = Polynomial.Coefficient[(k + 1)];
/*  558 */             for (int l = 1; l <= Polynomial.NumVariables; l++)
/*  559 */               Polynomial.Exponent[k][l] = Polynomial.Exponent[(k + 1)][l];
/*      */           }
/*  561 */           Polynomial.NumTerms -= 1;
/*      */         }
/*  563 */         j += 1;
/*      */       }
/*  565 */       i += 1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void OutputOneDResult()
/*      */   {
/*  575 */     System.out.print("\nResult of One-Dimensional Search:\n");
/*      */     
/*  577 */     System.out.print(ConvertStringToFixString("x = ", 4, 2));
/*  578 */     System.out.print(ConvertDoubleToFixString(this.CurrentOneDSearch.NewBound, 12, 8, 1, false));
/*  579 */     System.out.print(ConvertStringToFixString("value = ", 8, 2));
/*  580 */     System.out.print(ConvertDoubleToFixString(this.CurrentOneDSearch.Value, 12, 8, 1, false));
/*      */     
/*  582 */     System.out.print("\n");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void OutputGradientResult()
/*      */   {
/*  590 */     int width = 12;
/*  591 */     int dec = 8;
/*      */     
/*  593 */     System.out.print("\nResult of Gradient Search:\n");
/*      */     
/*  595 */     System.out.print(ConvertStringToFixString("The Solution is: (x1,x2) = (", 30, 2));
/*  596 */     System.out.print(ConvertDoubleToFixString(this.CurrentGradientSearch.NewSolution[1], width, dec, 2, false));
/*  597 */     System.out.print(", ");
/*  598 */     System.out.print(ConvertDoubleToFixString(this.CurrentGradientSearch.NewSolution[2], width, dec, 2, false));
/*  599 */     System.out.print(")\n");
/*      */     
/*  601 */     System.out.print(ConvertStringToFixString("grad f(x1,x2) = (", 30, 2));
/*  602 */     System.out.print(ConvertDoubleToFixString(this.CurrentGradientSearch.Gradient[1], width, dec, 1, false));
/*  603 */     System.out.print(", ");
/*  604 */     System.out.print(ConvertDoubleToFixString(this.CurrentGradientSearch.Gradient[2], width, dec, 1, false));
/*  605 */     System.out.print(")");
/*      */     
/*  607 */     System.out.print("\n");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private double dP(double[] X, int Which, double r)
/*      */   {
/*  615 */     ORSolverBase.PolynomialType TempPoly = new ORSolverBase.PolynomialType(this);
/*      */     
/*  617 */     DifferentiatePolynomial(this.SUMTModel.f, Which, TempPoly);
/*  618 */     double Temp = EvaluatePolynomial(TempPoly, X);
/*  619 */     for (int i = 1; i <= this.SUMTModel.NumInequalityConstraints; i++)
/*      */     {
/*  621 */       DifferentiatePolynomial(this.SUMTModel.B[i], Which, TempPoly);
/*  622 */       Temp -= r * D(-EvaluatePolynomial(TempPoly, X), Math.pow(EvaluatePolynomial(this.SUMTModel.B[i], X), 2.0D));
/*      */     }
/*  624 */     for (i = 1; i <= this.SUMTModel.NumEqualityConstraints; i++)
/*      */     {
/*  626 */       DifferentiatePolynomial(this.SUMTModel.B[(i + this.SUMTModel.NumInequalityConstraints)], Which, TempPoly);
/*  627 */       Temp -= 2 * EvaluatePolynomial(TempPoly, X) * D(EvaluatePolynomial(this.SUMTModel.B[(i + this.SUMTModel.NumInequalityConstraints)], X), Math.sqrt(r));
/*      */     }
/*  629 */     for (i = 1; i <= this.SUMTModel.NumVariableswlb; i++)
/*      */     {
/*  631 */       DifferentiatePolynomial(this.SUMTModel.L[i], Which, TempPoly);
/*  632 */       Temp -= r * D(-EvaluatePolynomial(TempPoly, X), Math.pow(EvaluatePolynomial(this.SUMTModel.L[i], X), 2.0D));
/*      */     }
/*  634 */     return Temp;
/*      */   }
/*      */   
/*      */   private double D(double a, double b) {
/*      */     double t;
/*      */     double t;
/*  640 */     if (Math.abs(b) < 1.0E-20D) {
/*  641 */       t = a * 1.0E20D;
/*      */     } else
/*  643 */       t = a / b;
/*  644 */     return t;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private double P(double[] X, double r)
/*      */   {
/*  652 */     double Temp = EvaluatePolynomial(this.SUMTModel.f, X);
/*  653 */     for (int i = 1; i <= this.SUMTModel.NumInequalityConstraints; i++)
/*      */     {
/*  655 */       double Temp2 = EvaluatePolynomial(this.SUMTModel.B[i], X);
/*  656 */       if (Temp2 < 0) {
/*  657 */         Temp = -1.0E20D;
/*      */       } else
/*  659 */         Temp -= D(r, Temp2);
/*      */     }
/*  661 */     for (i = 1; i <= this.SUMTModel.NumEqualityConstraints; i++)
/*  662 */       Temp -= D(Math.pow(EvaluatePolynomial(this.SUMTModel.B[(i + this.SUMTModel.NumInequalityConstraints)], X), 2.0D), Math.sqrt(r));
/*  663 */     for (i = 1; i <= this.SUMTModel.NumVariableswlb; i++)
/*      */     {
/*  665 */       double Temp2 = EvaluatePolynomial(this.SUMTModel.L[i], X);
/*  666 */       if (Temp2 < 0) {
/*  667 */         Temp = -1.0E20D;
/*      */       } else {
/*  669 */         Temp -= D(r, Temp2);
/*      */       }
/*      */     }
/*  672 */     return Temp;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void DoSUMT()
/*      */   {
/*  679 */     double[] BestSolution = new double[11];
/*      */     
/*  681 */     double[] Gradient = new double[11];
/*  682 */     double[] Gradient2 = new double[11];
/*  683 */     double Derivative2 = 0.0D;
/*  684 */     double[] DifferenceVector = new double[11];
/*  685 */     double[] dSolution = new double[11];
/*  686 */     double[] OriginalSolution = new double[11];
/*      */     
/*  688 */     ORSolverBase.PolynomialType[] OneDPolynomials = new ORSolverBase.PolynomialType[11];
/*      */     
/*  690 */     double[] my_InitialSolution = new double[11];
/*  691 */     arraycopy1(this.CurrentSUMT.InitialSolution, my_InitialSolution);
/*      */     
/*  693 */     for (int i = 0; i < 11; i++) {
/*  694 */       OneDPolynomials[i] = new ORSolverBase.PolynomialType(this);
/*      */     }
/*  696 */     int It2 = 0;
/*  697 */     double BestP = -1.0E20D;
/*  698 */     BestSolution[1] = 0.0D;
/*  699 */     BestSolution[2] = 0.0D;
/*  700 */     BestSolution[3] = 0.0D;
/*      */     do {
/*  702 */       It2 += 1;
/*  703 */       for (i = 1; i <= this.SUMTModel.NumVariables; i++)
/*  704 */         Gradient[i] = dP(this.CurrentSUMT.InitialSolution, i, this.CurrentSUMT.r);
/*  705 */       double TheMax = 0.0D;
/*  706 */       for (i = 1; i <= this.SUMTModel.NumVariables; i++) {
/*  707 */         if (Math.abs(Gradient[i]) > TheMax)
/*  708 */           TheMax = Math.abs(Gradient[i]);
/*      */       }
/*  710 */       arraycopy1(this.CurrentSUMT.InitialSolution, OriginalSolution);
/*  711 */       double tustart = 1.0D;
/*  712 */       double t = tustart;
/*      */       boolean TestOK;
/*  714 */       do { tustart = t;
/*  715 */         arraycopy1(OriginalSolution, this.CurrentSUMT.InitialSolution);
/*  716 */         double tu = tustart;
/*  717 */         double tl = 0.0D;
/*  718 */         t = (tu + tl) / 2;
/*      */         do {
/*  720 */           for (i = 1; i <= this.SUMTModel.NumVariables; i++)
/*      */           {
/*  722 */             this.CurrentSUMT.FinalSolution[i] = (this.CurrentSUMT.InitialSolution[i] + t * Gradient[i]);
/*  723 */             Derivative2 = 0.0D;
/*  724 */             for (int j = 1; j <= this.SUMTModel.NumVariables; j++) {
/*  725 */               Gradient2[j] = dP(this.CurrentSUMT.FinalSolution, j, this.CurrentSUMT.r);
/*  726 */               Derivative2 += Gradient[j] * Gradient2[j];
/*      */             }
/*      */           }
/*  729 */           double TheP = P(this.CurrentSUMT.FinalSolution, this.CurrentSUMT.r);
/*      */           
/*      */ 
/*  732 */           BestP = TheP;
/*  733 */           BestSolution[1] = this.CurrentSUMT.FinalSolution[1];
/*  734 */           BestSolution[2] = this.CurrentSUMT.FinalSolution[2];
/*  735 */           BestSolution[3] = this.CurrentSUMT.FinalSolution[3];
/*      */           
/*  737 */           if (TheP < -1.0E10D) {
/*  738 */             tl = tu;
/*      */ 
/*      */           }
/*  741 */           else if (Derivative2 > 0) {
/*  742 */             tl = t;
/*  743 */             t = (t + tu) / 2;
/*      */           }
/*      */           else {
/*  746 */             tu = t;
/*  747 */             t = (t + tl) / 2;
/*      */           }
/*      */           
/*  750 */         } while ((tu - tl >= 2.5E-7D) && (Math.abs(Derivative2) >= 1.0E-7D));
/*  751 */         TestOK = true;
/*  752 */         for (i = 1; i <= this.SUMTModel.NumInequalityConstraints; i++)
/*  753 */           if (EvaluatePolynomial(this.SUMTModel.B[i], this.CurrentSUMT.FinalSolution) < 0)
/*  754 */             TestOK = false;
/*  755 */         for (i = 1; i <= this.SUMTModel.NumVariableswlb; i++)
/*  756 */           if (EvaluatePolynomial(this.SUMTModel.L[i], this.CurrentSUMT.FinalSolution) < 0)
/*  757 */             TestOK = false;
/*  758 */       } while ((!TestOK) && (tustart >= 2.5E-7D));
/*  759 */       if (!TestOK)
/*  760 */         t = 0.0D;
/*  761 */       for (i = 1; i <= this.SUMTModel.NumVariables; i++)
/*  762 */         this.CurrentSUMT.InitialSolution[i] = (OriginalSolution[i] + t * Gradient[i]);
/*  763 */     } while (It2 < 15);
/*  764 */     arraycopy1(my_InitialSolution, this.CurrentSUMT.InitialSolution);
/*  765 */     arraycopy1(BestSolution, this.CurrentSUMT.FinalSolution);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean doWork(IOROperation op, IORNLPProcessController.NLData data)
/*      */   {
/*  772 */     if (doWork(op) == false) {
/*  773 */       return false;
/*      */     }
/*      */     
/*  776 */     getData(data);
/*  777 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public void getData(IORNLPProcessController.NLData data)
/*      */   {
/*  783 */     if (this.CurrentAlgorithm == 1) {
/*  784 */       data.is_max = (this.OneDObjective == 0);
/*      */       
/*  786 */       if (data.newx == null) data.newx = new double[20 + 1];
/*  787 */       if (data.fx == null) data.fx = new double[20 + 1];
/*  788 */       if (data.df == null) data.df = new double[20 + 1];
/*  789 */       if (data.low_bound == null) data.low_bound = new double[20 + 1];
/*  790 */       if (data.up_bound == null) data.up_bound = new double[20 + 1];
/*  791 */       if (data.fx_coef == null) data.fx_coef = new double[11];
/*  792 */       if (data.fx_power == null) data.fx_power = new int[11];
/*  793 */       if (data.df_coef == null) data.df_coef = new double[11];
/*  794 */       if (data.df_power == null) { data.df_power = new int[11];
/*      */       }
/*  796 */       data.newx[this.CurrentIteration] = this.CurrentOneDSearch.NewBound;
/*  797 */       data.fx[this.CurrentIteration] = this.CurrentOneDSearch.Value;
/*  798 */       data.df[this.CurrentIteration] = this.CurrentOneDSearch.Derivative;
/*  799 */       data.low_bound[this.CurrentIteration] = this.CurrentOneDSearch.LowerBound;
/*  800 */       data.up_bound[this.CurrentIteration] = this.CurrentOneDSearch.UpperBound;
/*      */       
/*  802 */       data.num_term = this.ThePolynomial.NumTerms;
/*  803 */       for (int i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/*  804 */         data.fx_coef[(i - 1)] = this.ThePolynomial.Coefficient[i];
/*  805 */         data.fx_power[(i - 1)] = this.ThePolynomial.Exponent[i][1];
/*  806 */         data.df_coef[(i - 1)] = this.OneDDerivative.Coefficient[i];
/*  807 */         data.df_power[(i - 1)] = this.OneDDerivative.Exponent[i][1];
/*      */       }
/*      */     }
/*  810 */     if (this.CurrentAlgorithm == 2)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  817 */       data.num_term = this.ThePolynomial.NumTerms;
/*  818 */       if (data.oldx1 == null) data.oldx1 = new double[20 + 1];
/*  819 */       if (data.oldx2 == null) data.oldx2 = new double[20 + 1];
/*  820 */       if (data.newx1 == null) data.newx1 = new double[20 + 1];
/*  821 */       if (data.newx2 == null) data.newx2 = new double[20 + 1];
/*  822 */       if (data.gradx1 == null) data.gradx1 = new double[20 + 1];
/*  823 */       if (data.gradx2 == null) data.gradx2 = new double[20 + 1];
/*  824 */       if (data.a1 == null) data.a1 = new double[20 + 1];
/*  825 */       if (data.b1 == null) data.b1 = new double[20 + 1];
/*  826 */       if (data.a2 == null) data.a2 = new double[20 + 1];
/*  827 */       if (data.b2 == null) data.b2 = new double[20 + 1];
/*  828 */       if (data.tt == null) data.tt = new double[20 + 1];
/*  829 */       if (data.fx1x2_coef == null) data.fx1x2_coef = new double[11];
/*  830 */       if (data.dfx1_coef == null) data.dfx1_coef = new double[11];
/*  831 */       if (data.dfx2_coef == null) data.dfx2_coef = new double[11];
/*  832 */       if (data.ft_coef == null) data.ft_coef = new double[11];
/*  833 */       if (data.fx_x1exp == null) data.fx_x1exp = new int[11];
/*  834 */       if (data.fx_x2exp == null) data.fx_x2exp = new int[11];
/*  835 */       if (data.dfx1_x1exp == null) data.dfx1_x1exp = new int[11];
/*  836 */       if (data.dfx1_x2exp == null) data.dfx1_x2exp = new int[11];
/*  837 */       if (data.dfx2_x1exp == null) data.dfx2_x1exp = new int[11];
/*  838 */       if (data.dfx2_x2exp == null) data.dfx2_x2exp = new int[11];
/*  839 */       if (data.ft_texp == null) { data.ft_texp = new int[11];
/*      */       }
/*  841 */       for (int i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/*  842 */         data.fx1x2_coef[(i - 1)] = this.ThePolynomial.Coefficient[i];
/*  843 */         data.fx_x1exp[(i - 1)] = this.ThePolynomial.Exponent[i][1];
/*  844 */         data.fx_x2exp[(i - 1)] = this.ThePolynomial.Exponent[i][2];
/*  845 */         data.dfx1_coef[(i - 1)] = this.TheDerivative[1].Coefficient[i];
/*  846 */         data.dfx1_x1exp[(i - 1)] = this.TheDerivative[1].Exponent[i][1];
/*  847 */         data.dfx1_x2exp[(i - 1)] = this.TheDerivative[1].Exponent[i][2];
/*  848 */         data.dfx2_coef[(i - 1)] = this.TheDerivative[2].Coefficient[i];
/*  849 */         data.dfx2_x1exp[(i - 1)] = this.TheDerivative[2].Exponent[i][1];
/*  850 */         data.dfx2_x2exp[(i - 1)] = this.TheDerivative[2].Exponent[i][2];
/*      */       }
/*      */       
/*  853 */       data.t_function_num_term = this.OneDPolynomial.NumTerms;
/*  854 */       for (i = 1; i <= this.OneDPolynomial.NumTerms; i++) {
/*  855 */         data.ft_coef[(i - 1)] = this.OneDPolynomial.Coefficient[i];
/*  856 */         data.ft_texp[(i - 1)] = this.OneDPolynomial.Exponent[i][1];
/*      */       }
/*      */       
/*  859 */       if (this.NewXCalculated) {
/*  860 */         data.oldx1[(this.CurrentIteration + 1)] = this.CurrentGradientSearch.InitialSolution[1];
/*  861 */         data.oldx2[(this.CurrentIteration + 1)] = this.CurrentGradientSearch.InitialSolution[2];
/*  862 */         data.gradx1[(this.CurrentIteration + 1)] = this.CurrentGradientSearch.Gradient[1];
/*  863 */         data.gradx2[(this.CurrentIteration + 1)] = this.CurrentGradientSearch.Gradient[2];
/*      */       }
/*      */       else {
/*  866 */         data.oldx1[this.CurrentIteration] = this.CurrentGradientSearch.InitialSolution[1];
/*  867 */         data.oldx2[this.CurrentIteration] = this.CurrentGradientSearch.InitialSolution[2];
/*  868 */         data.gradx1[this.CurrentIteration] = this.CurrentGradientSearch.Gradient[1];
/*  869 */         data.gradx2[this.CurrentIteration] = this.CurrentGradientSearch.Gradient[2];
/*      */       }
/*  871 */       data.newx1[this.CurrentIteration] = this.CurrentGradientSearch.NewSolution[1];
/*  872 */       data.newx2[this.CurrentIteration] = this.CurrentGradientSearch.NewSolution[2];
/*  873 */       data.a1[this.CurrentIteration] = this.CurrentGradientSearch.a[1];
/*  874 */       data.a2[this.CurrentIteration] = this.CurrentGradientSearch.a[2];
/*  875 */       data.b1[this.CurrentIteration] = this.CurrentGradientSearch.b[1];
/*  876 */       data.b2[this.CurrentIteration] = this.CurrentGradientSearch.b[2];
/*  877 */       data.a1[(this.CurrentIteration + 1)] = 0.0D;
/*  878 */       data.a2[(this.CurrentIteration + 1)] = 0.0D;
/*  879 */       data.b1[(this.CurrentIteration + 1)] = 0.0D;
/*  880 */       data.b2[(this.CurrentIteration + 1)] = 0.0D;
/*  881 */       data.tt[this.CurrentIteration] = this.CurrentGradientSearch.t;
/*      */     }
/*  883 */     else if (this.CurrentAlgorithm == 3)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  897 */       data.is_max = (this.SimplexModel.Objective == 0);
/*  898 */       data.num_term = this.ThePolynomial.NumTerms;
/*  899 */       data.num_variable = this.SimplexModel.NumVariables;
/*  900 */       data.num_constraint = this.SimplexModel.NumConstraints;
/*      */       
/*  902 */       if (data.fw_oldx == null) data.fw_oldx = new double[20 + 1][3];
/*  903 */       if (data.fw_newx == null) data.fw_newx = new double[20 + 1][3];
/*  904 */       if (data.fw_a == null) data.fw_a = new double[20 + 1][3];
/*  905 */       if (data.fw_b == null) data.fw_b = new double[20 + 1][3];
/*  906 */       if (data.fw_xlp == null) data.fw_xlp = new double[20 + 1][3];
/*  907 */       if (data.fw_constraint_coef == null) data.fw_constraint_coef = new double[51][51];
/*  908 */       if (data.fw_constraint_operator == null) data.fw_constraint_operator = new int[51];
/*  909 */       if (data.fw_flp_coef == null) data.fw_flp_coef = new double[20 + 1][3];
/*  910 */       if (data.fw_gradx == null) data.fw_gradx = new double[20 + 1][3];
/*  911 */       if (data.fw_tt == null) data.fw_tt = new double[20 + 1];
/*  912 */       if (data.fw_ht_coef == null) data.fw_ht_coef = new double[20 + 1][11];
/*  913 */       if (data.fw_ht_texp == null) data.fw_ht_texp = new int[20 + 1][11];
/*  914 */       if (data.fw_ht_num_term == null) { data.fw_ht_num_term = new int[20 + 1];
/*      */       }
/*      */       
/*  917 */       for (int i = 1; i <= this.SimplexModel.NumConstraints; i++) {
/*  918 */         data.fw_constraint_coef[(i - 1)][0] = this.SimplexModel.RightHandSide[i];
/*  919 */         data.fw_constraint_operator[(i - 1)] = this.SimplexModel.Constraint[i];
/*  920 */         for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/*  921 */           data.fw_constraint_coef[(i - 1)][j] = this.SimplexModel.A[i][j];
/*      */         }
/*      */       }
/*  924 */       data.fw_objective.num_term = data.num_term;
/*  925 */       data.fw_objective.num_var = data.num_variable;
/*  926 */       for (i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/*  927 */         data.fw_objective.fxcoef[(i - 1)] = this.ThePolynomial.Coefficient[i];
/*  928 */         for (int j = 1; j <= this.ThePolynomial.NumVariables; j++) {
/*  929 */           data.fw_objective.xexp[(i - 1)][(j - 1)] = this.ThePolynomial.Exponent[i][j];
/*      */         }
/*      */       }
/*  932 */       for (i = 1; i <= this.ThePolynomial.NumVariables; i++) {
/*  933 */         data.fw_derivative[(i - 1)].num_term = data.num_term;
/*  934 */         data.fw_derivative[(i - 1)].num_var = data.num_variable;
/*  935 */         for (int j = 1; j <= this.ThePolynomial.NumTerms; j++) {
/*  936 */           data.fw_derivative[(i - 1)].fxcoef[(j - 1)] = this.TheDerivative[i].Coefficient[j];
/*  937 */           for (int l = 1; l <= this.ThePolynomial.NumVariables; l++) {
/*  938 */             data.fw_derivative[(i - 1)].xexp[(j - 1)][(l - 1)] = this.TheDerivative[i].Exponent[j][l];
/*      */           }
/*      */         }
/*      */       }
/*  942 */       data.fw_ht_num_term[this.CurrentIteration] = this.OneDPolynomial.NumTerms;
/*  943 */       for (i = 1; i <= this.OneDPolynomial.NumTerms; i++) {
/*  944 */         data.fw_ht_coef[this.CurrentIteration][(i - 1)] = this.OneDPolynomial.Coefficient[i];
/*  945 */         data.fw_ht_texp[this.CurrentIteration][(i - 1)] = this.OneDPolynomial.Exponent[i][1];
/*      */       }
/*      */       
/*  948 */       for (i = 1; i <= this.ThePolynomial.NumVariables; i++) {
/*  949 */         data.fw_flp_coef[this.CurrentIteration][(i - 1)] = this.SimplexModel.ObjectiveFunction[i];
/*  950 */         if (this.NewXCalculated) {
/*  951 */           data.fw_gradx[(this.CurrentIteration + 1)][(i - 1)] = this.GradientValue[i];
/*      */         }
/*      */         else {
/*  954 */           data.fw_gradx[this.CurrentIteration][(i - 1)] = this.GradientValue[i];
/*      */         }
/*      */       }
/*      */       
/*  958 */       for (i = 1; i <= this.ThePolynomial.NumVariables; i++) {
/*  959 */         data.fw_newx[this.CurrentIteration][(i - 1)] = this.CurrentFrankWolfe.NewSolution[i];
/*  960 */         data.fw_a[this.CurrentIteration][(i - 1)] = this.CurrentFrankWolfe.a[i];
/*  961 */         data.fw_b[this.CurrentIteration][(i - 1)] = this.CurrentFrankWolfe.b[i];
/*  962 */         data.fw_xlp[this.CurrentIteration][(i - 1)] = this.CurrentFrankWolfe.FWLPSolution[i];
/*  963 */         if (this.NewXCalculated) {
/*  964 */           data.fw_oldx[(this.CurrentIteration + 1)][(i - 1)] = this.CurrentFrankWolfe.Solution[i];
/*      */         } else
/*  966 */           data.fw_oldx[this.CurrentIteration][(i - 1)] = this.CurrentFrankWolfe.Solution[i];
/*      */       }
/*  968 */       data.fw_tt[this.CurrentIteration] = this.CurrentFrankWolfe.t;
/*      */     } else {
/*  970 */       if (this.CurrentAlgorithm == 4) {
/*  971 */         data.sumt_num_variable_with_bound = this.SUMTModel.NumVariableswlb;
/*  972 */         data.sumt_num_variable_without_bound = this.SUMTModel.NumVariableswolb;
/*  973 */         data.sumt_num_inequality_constraint = this.SUMTModel.NumInequalityConstraints;
/*  974 */         data.sumt_num_equality_constraint = this.SUMTModel.NumEqualityConstraints;
/*      */         
/*  976 */         if (data.sumt_oldx == null) data.sumt_oldx = new double[10];
/*  977 */         if (data.sumt_newx == null) data.sumt_newx = new double[10];
/*  978 */         for (int i = 1; i <= this.SUMTModel.f.NumTerms; i++) {
/*  979 */           data.sumt_oldx[(i - 1)] = this.CurrentSUMT.InitialSolution[i];
/*  980 */           data.sumt_newx[(i - 1)] = this.CurrentSUMT.FinalSolution[i];
/*      */         }
/*  982 */         data.sumt_r = this.CurrentSUMT.r;
/*      */         
/*      */ 
/*  985 */         for (int j = 1; j <= this.SUMTModel.f.NumTerms; j++) {
/*  986 */           data.sumt_objective.fxcoef[(j - 1)] = this.SUMTModel.f.Coefficient[j];
/*  987 */           for (int l = 1; l <= this.SUMTModel.f.NumVariables; l++) {
/*  988 */             data.sumt_objective.xexp[(j - 1)][(l - 1)] = this.SUMTModel.f.Exponent[j][l];
/*      */           }
/*      */         }
/*  991 */         for (i = 1; i <= this.SUMTModel.NumEqualityConstraints + this.SUMTModel.NumInequalityConstraints; i++) {
/*  992 */           data.sumt_Bx[(i - 1)].num_term = this.SUMTModel.B[i].NumTerms;
/*  993 */           data.sumt_Bx[(i - 1)].num_var = this.SUMTModel.B[i].NumVariables;
/*  994 */           for (j = 1; j <= this.SUMTModel.B[i].NumTerms; j++) {
/*  995 */             data.sumt_Bx[(i - 1)].fxcoef[(j - 1)] = this.SUMTModel.B[i].Coefficient[j];
/*  996 */             for (int l = 1; l <= this.SUMTModel.B[i].NumVariables; l++) {
/*  997 */               data.sumt_Bx[(i - 1)].xexp[(j - 1)][(l - 1)] = this.SUMTModel.B[i].Exponent[j][l];
/*      */             }
/*      */           }
/*      */         }
/* 1001 */         for (i = 1; i <= this.SUMTModel.NumVariableswlb; i++) {
/* 1002 */           data.sumt_Lx[(i - 1)].num_term = this.SUMTModel.L[i].NumTerms;
/* 1003 */           data.sumt_Lx[(i - 1)].num_var = this.SUMTModel.L[i].NumVariables;
/* 1004 */           for (j = 1; j <= this.SUMTModel.L[i].NumTerms; j++) {
/* 1005 */             data.sumt_Lx[(i - 1)].fxcoef[(j - 1)] = this.SUMTModel.L[i].Coefficient[j];
/* 1006 */             for (int l = 1; l <= this.SUMTModel.L[i].NumVariables; l++) {
/* 1007 */               data.sumt_Lx[(i - 1)].xexp[(j - 1)][(l - 1)] = this.SUMTModel.L[i].Exponent[j][l];
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1014 */       if (this.CurrentAlgorithm == 5) {
/* 1015 */         if (data.oldx1 == null) data.oldx1 = new double[20 + 1];
/* 1016 */         if (data.oldx2 == null) data.oldx2 = new double[20 + 1];
/* 1017 */         if (data.newx1 == null) data.newx1 = new double[20 + 1];
/* 1018 */         if (data.newx2 == null) data.newx2 = new double[20 + 1];
/* 1019 */         if (data.gradx1 == null) data.gradx1 = new double[20 + 1];
/* 1020 */         if (data.gradx2 == null) data.gradx2 = new double[20 + 1];
/* 1021 */         if (data.fx1x2_coef == null) data.fx1x2_coef = new double[11];
/* 1022 */         if (data.fx_x1exp == null) data.fx_x1exp = new int[11];
/* 1023 */         if (data.fx_x2exp == null) { data.fx_x2exp = new int[11];
/*      */         }
/* 1025 */         for (int i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/* 1026 */           data.fx1x2_coef[(i - 1)] = this.ThePolynomial.Coefficient[i];
/* 1027 */           data.fx_x1exp[(i - 1)] = this.ThePolynomial.Exponent[i][1];
/* 1028 */           data.fx_x2exp[(i - 1)] = this.ThePolynomial.Exponent[i][2];
/*      */         }
/*      */         
/* 1031 */         data.errTolerance = this.ErrorTol;
/* 1032 */         data.oldx1[0] = this.FirstGradientSearch.InitialSolution[1];
/* 1033 */         data.oldx2[0] = this.FirstGradientSearch.InitialSolution[2];
/* 1034 */         data.newx1[0] = this.CurrentGradientSearch.NewSolution[1];
/* 1035 */         data.newx2[0] = this.CurrentGradientSearch.NewSolution[2];
/* 1036 */         data.gradx1[0] = this.CurrentGradientSearch.Gradient[1];
/* 1037 */         data.gradx2[0] = this.CurrentGradientSearch.Gradient[2];
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */ 
/* 1046 */         if (this.CurrentAlgorithm == 6)
/*      */         {
/*      */ 
/* 1049 */           data.is_max = (this.SimplexModel.Objective == 0);
/* 1050 */           data.num_term = this.ThePolynomial.NumTerms;
/* 1051 */           data.num_variable = this.SimplexModel.NumVariables;
/* 1052 */           data.num_constraint = this.SimplexModel.NumConstraints;
/*      */           
/* 1054 */           if (data.fw_oldx == null) data.fw_oldx = new double[20 + 1][3];
/* 1055 */           if (data.fw_newx == null) data.fw_newx = new double[20 + 1][3];
/* 1056 */           if (data.fw_a == null) data.fw_a = new double[20 + 1][3];
/* 1057 */           if (data.fw_b == null) data.fw_b = new double[20 + 1][3];
/* 1058 */           if (data.fw_xlp == null) data.fw_xlp = new double[20 + 1][3];
/* 1059 */           if (data.fw_constraint_coef == null) data.fw_constraint_coef = new double[51][51];
/* 1060 */           if (data.fw_constraint_operator == null) data.fw_constraint_operator = new int[51];
/* 1061 */           if (data.fw_flp_coef == null) data.fw_flp_coef = new double[20 + 1][3];
/* 1062 */           if (data.fw_gradx == null) data.fw_gradx = new double[20 + 1][3];
/* 1063 */           if (data.fw_tt == null) data.fw_tt = new double[20 + 1];
/* 1064 */           if (data.fw_ht_coef == null) data.fw_ht_coef = new double[20 + 1][11];
/* 1065 */           if (data.fw_ht_texp == null) data.fw_ht_texp = new int[20 + 1][11];
/* 1066 */           if (data.fw_ht_num_term == null) { data.fw_ht_num_term = new int[20 + 1];
/*      */           }
/*      */           
/* 1069 */           for (int i = 1; i <= this.SimplexModel.NumConstraints; i++) {
/* 1070 */             data.fw_constraint_coef[(i - 1)][0] = this.SimplexModel.RightHandSide[i];
/* 1071 */             data.fw_constraint_operator[(i - 1)] = this.SimplexModel.Constraint[i];
/* 1072 */             for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/* 1073 */               data.fw_constraint_coef[(i - 1)][j] = this.SimplexModel.A[i][j];
/*      */             }
/*      */           }
/*      */           
/* 1077 */           data.fw_objective.num_term = data.num_term;
/* 1078 */           data.fw_objective.num_var = data.num_variable;
/* 1079 */           for (i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/* 1080 */             data.fw_objective.fxcoef[(i - 1)] = this.ThePolynomial.Coefficient[i];
/* 1081 */             for (int j = 1; j <= this.ThePolynomial.NumVariables; j++) {
/* 1082 */               data.fw_objective.xexp[(i - 1)][(j - 1)] = this.ThePolynomial.Exponent[i][j];
/*      */             }
/*      */           }
/* 1085 */           data.Temp = new double[this.currentAType.A_size];
/* 1086 */           data.variable = new double[this.currentAType.A_size][2];
/* 1087 */           data.function = new double[this.currentAType.A_size];
/* 1088 */           data.probability = new double[this.currentAType.A_size];
/* 1089 */           data.accept = new boolean[this.currentAType.A_size];
/* 1090 */           data.itertag = new int[this.currentAType.A_size];
/* 1091 */           data.bestvarible = new double[2];
/*      */           
/*      */ 
/* 1094 */           data.pos = this.anneal.currentPos;
/* 1095 */           data.bestresult = this.anneal.bestObj;
/* 1096 */           data.bestvarible = this.anneal.bestSolution;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 1101 */           System.out.println("in getdata:data.pos".concat(String.valueOf(String.valueOf(data.pos))));
/* 1102 */           System.out.println("in getdata:data.bestresult".concat(String.valueOf(String.valueOf(data.bestresult))));
/* 1103 */           System.out.println("in getdata:data.bestvarible".concat(String.valueOf(String.valueOf(data.bestvarible))));
/*      */           
/*      */ 
/* 1106 */           data.totalresult = this.currentAType.A_size;
/* 1107 */           int tempitertag = 0;
/*      */           
/* 1109 */           for (i = 0; i < this.currentAType.A_size; i++)
/*      */           {
/* 1111 */             data.itertag[i] = tempitertag;
/*      */             
/* 1113 */             Double tt = (Double)this.currentAType.An_list[i].get(0);
/* 1114 */             data.Temp[i] = tt.doubleValue();
/*      */             
/* 1116 */             double[] ss = (double[])this.currentAType.An_list[i].get(1);
/* 1117 */             for (int n = 0; n < data.num_variable; n++) {
/* 1118 */               data.variable[i][n] = ss[n];
/*      */             }
/*      */             
/* 1121 */             tt = (Double)this.currentAType.An_list[i].get(2);
/* 1122 */             data.function[i] = tt.doubleValue();
/*      */             
/* 1124 */             tt = (Double)this.currentAType.An_list[i].get(3);
/* 1125 */             data.probability[i] = tt.doubleValue();
/*      */             
/* 1127 */             Boolean bb = (Boolean)this.currentAType.An_list[i].get(4);
/* 1128 */             data.accept[i] = bb.booleanValue();
/*      */             
/* 1130 */             if (data.accept[i] != 0) { tempitertag++;
/*      */             }
/* 1132 */             System.out.print(String.valueOf(String.valueOf(new StringBuffer("Temp: ").append(i).append(data.Temp[i]).append("    "))));
/* 1133 */             System.out.print(String.valueOf(String.valueOf(new StringBuffer("variable: ").append(i).append(data.variable[i][0]).append("    "))));
/* 1134 */             System.out.print(String.valueOf(String.valueOf(new StringBuffer("function: ").append(i).append(data.function[i]).append("    "))));
/* 1135 */             System.out.print(String.valueOf(String.valueOf(new StringBuffer("probility: ").append(i).append(data.probability[i]).append("    "))));
/* 1136 */             System.out.println(String.valueOf(String.valueOf(new StringBuffer("accept: ").append(i).append(data.accept[i]).append("    "))));
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1158 */         if (this.CurrentAlgorithm == 7) {
/* 1159 */           data.is_max = (this.SimplexModel.Objective == 0);
/* 1160 */           data.num_term = this.ThePolynomial.NumTerms;
/* 1161 */           data.num_variable = this.SimplexModel.NumVariables;
/* 1162 */           data.num_constraint = this.SimplexModel.NumConstraints;
/*      */           
/* 1164 */           if (data.fw_oldx == null) data.fw_oldx = new double[20 + 1][3];
/* 1165 */           if (data.fw_newx == null) data.fw_newx = new double[20 + 1][3];
/* 1166 */           if (data.fw_a == null) data.fw_a = new double[20 + 1][3];
/* 1167 */           if (data.fw_b == null) data.fw_b = new double[20 + 1][3];
/* 1168 */           if (data.fw_xlp == null) data.fw_xlp = new double[20 + 1][3];
/* 1169 */           if (data.fw_constraint_coef == null) data.fw_constraint_coef = new double[51][51];
/* 1170 */           if (data.fw_constraint_operator == null) data.fw_constraint_operator = new int[51];
/* 1171 */           if (data.fw_flp_coef == null) data.fw_flp_coef = new double[20 + 1][3];
/* 1172 */           if (data.fw_gradx == null) data.fw_gradx = new double[20 + 1][3];
/* 1173 */           if (data.fw_tt == null) data.fw_tt = new double[20 + 1];
/* 1174 */           if (data.fw_ht_coef == null) data.fw_ht_coef = new double[20 + 1][11];
/* 1175 */           if (data.fw_ht_texp == null) data.fw_ht_texp = new int[20 + 1][11];
/* 1176 */           if (data.fw_ht_num_term == null) { data.fw_ht_num_term = new int[20 + 1];
/*      */           }
/*      */           
/* 1179 */           for (int i = 1; i <= this.SimplexModel.NumConstraints; i++) {
/* 1180 */             data.fw_constraint_coef[(i - 1)][0] = this.SimplexModel.RightHandSide[i];
/* 1181 */             data.fw_constraint_operator[(i - 1)] = this.SimplexModel.Constraint[i];
/* 1182 */             for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/* 1183 */               data.fw_constraint_coef[(i - 1)][j] = this.SimplexModel.A[i][j];
/*      */             }
/*      */           }
/*      */           
/* 1187 */           data.fw_objective.num_term = data.num_term;
/* 1188 */           data.fw_objective.num_var = data.num_variable;
/* 1189 */           for (i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/* 1190 */             data.fw_objective.fxcoef[(i - 1)] = this.ThePolynomial.Coefficient[i];
/* 1191 */             for (int j = 1; j <= this.ThePolynomial.NumVariables; j++)
/* 1192 */               data.fw_objective.xexp[(i - 1)][(j - 1)] = this.ThePolynomial.Exponent[i][j];
/*      */           }
/*      */         }
/* 1195 */         if (this.CurrentAlgorithm == 8)
/*      */         {
/* 1197 */           data.totalrecord = this.getype.ge_size;
/* 1198 */           data.best = new Chromosome[data.totalrecord];
/* 1199 */           data.store = new Chromosome[10];
/* 1200 */           data.children = new Chromosome[6];
/* 1201 */           data.parentindex = new int[10];
/* 1202 */           data.bytag = new byte[10][10][10];
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1208 */           for (int i = 0; i < this.getype.currentindex; i++)
/*      */           {
/* 1210 */             data.best[i] = ((Chromosome)this.getype.best.get(i));
/* 1211 */             System.out.println("fitness in one table:".concat(String.valueOf(String.valueOf(data.best[i].getFitness()))));
/*      */           }
/*      */           
/*      */ 
/* 1215 */           data.store = ((Chromosome[])this.getype.storePopulation.get(this.getype.currentindex - 1));
/*      */           
/*      */ 
/* 1218 */           ArrayList temp = (ArrayList)this.getype.generation.get(this.getype.currentindex - 1);
/*      */           
/* 1220 */           Chromosome[][] aaa = new Chromosome[3][2];
/* 1221 */           int[][] bbb = new int[3][2];
/* 1222 */           ArrayList ccc = (ArrayList)temp.get(3);
/*      */           
/* 1224 */           ArrayList ccc1 = (ArrayList)ccc.get(0);
/* 1225 */           ArrayList ccc2 = (ArrayList)ccc.get(1);
/* 1226 */           ArrayList ccc3 = (ArrayList)ccc.get(2);
/*      */           
/* 1228 */           data.bytag[0] = ((byte[][])ccc1.get(0));
/* 1229 */           data.bytag[1] = ((byte[][])ccc1.get(1));
/* 1230 */           data.bytag[2] = ((byte[][])ccc2.get(0));
/* 1231 */           data.bytag[3] = ((byte[][])ccc2.get(1));
/* 1232 */           data.bytag[4] = ((byte[][])ccc3.get(0));
/* 1233 */           data.bytag[5] = ((byte[][])ccc3.get(1));
/*      */           
/* 1235 */           bbb = (int[][])temp.get(1);
/* 1236 */           aaa = (Chromosome[][])temp.get(2);
/*      */           
/* 1238 */           for (i = 0; i < 3; i++) {
/* 1239 */             for (int j = 0; j < 2; j++) {
/* 1240 */               data.parentindex[(i * 2 + j)] = bbb[i][j];
/* 1241 */               data.children[(i * 2 + j)] = aaa[i][j];
/*      */             }
/*      */           }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean doWork(IOROperation op)
/*      */   {
/* 1317 */     Vector p = op.parameters;
/*      */     
/*      */ 
/*      */ 
/* 1321 */     double[] Vector = new double[11];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1328 */     this.NewXCalculated = false;
/* 1329 */     int[] ia; int i; double[][] ConstraintCoef; switch (op.operation_code) {
/*      */     case 50103: 
/* 1331 */       this.CurrentIteration = 0;
/* 1332 */       this.CurrentAlgorithm = ((Integer)p.elementAt(0)).intValue();
/* 1333 */       if (this.CurrentAlgorithm == 1) {
/* 1334 */         this.CurrentOneDSearch = new ORSolverBase.OneDSearchType(this);
/*      */       }
/* 1336 */       else if (this.CurrentAlgorithm == 2) {
/* 1337 */         this.CurrentGradientSearch = new ORSolverBase.GradientSearchType(this);
/* 1338 */         this.TheDerivative[1] = new ORSolverBase.PolynomialType(this);
/* 1339 */         this.TheDerivative[2] = new ORSolverBase.PolynomialType(this);
/*      */       }
/* 1341 */       else if (this.CurrentAlgorithm == 3) {
/* 1342 */         this.CommandNumber = 9;
/* 1343 */         this.CurrentFrankWolfe = new ORNLPSolver.FrankWolfeType(null);
/* 1344 */         InitializeSimplexModel();
/*      */       }
/* 1346 */       else if (this.CurrentAlgorithm != 4)
/*      */       {
/* 1348 */         if (this.CurrentAlgorithm == 5) {
/* 1349 */           this.CurrentGradientSearch = new ORSolverBase.GradientSearchType(this);
/* 1350 */           this.FirstGradientSearch = new ORSolverBase.GradientSearchType(this);
/* 1351 */           this.TheDerivative[1] = new ORSolverBase.PolynomialType(this);
/* 1352 */           this.TheDerivative[2] = new ORSolverBase.PolynomialType(this);
/*      */ 
/*      */ 
/*      */         }
/* 1356 */         else if (this.CurrentAlgorithm == 66) {
/* 1357 */           System.out.println("current current Algorithm is :".concat(String.valueOf(String.valueOf(this.CurrentAlgorithm))));
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       break;
/*      */     case 50101: 
/* 1365 */       if (((Boolean)p.elementAt(0)).booleanValue()) {
/* 1366 */         this.OneDObjective = 0;
/*      */       } else {
/* 1368 */         this.OneDObjective = 1;
/*      */       }
/* 1370 */       this.ThePolynomial.NumVariables = 1;
/* 1371 */       this.OneDDerivative.NumVariables = 1;
/* 1372 */       this.ThePolynomial.NumTerms = ((Integer)p.elementAt(1)).intValue();
/* 1373 */       this.OneDDerivative.NumTerms = this.ThePolynomial.NumTerms;
/*      */       
/* 1375 */       double[] fx_coefficient = (double[])p.elementAt(2);
/* 1376 */       int[] fx_exponent = (int[])p.elementAt(3);
/* 1377 */       double[] dfx_coefficient = (double[])p.elementAt(4);
/* 1378 */       int[] dfx_exponent = (int[])p.elementAt(5);
/* 1379 */       for (int i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/* 1380 */         this.ThePolynomial.Coefficient[i] = fx_coefficient[(i - 1)];
/* 1381 */         this.ThePolynomial.Exponent[i][1] = fx_exponent[(i - 1)];
/* 1382 */         this.OneDDerivative.Coefficient[i] = dfx_coefficient[(i - 1)];
/* 1383 */         this.OneDDerivative.Exponent[i][1] = dfx_exponent[(i - 1)];
/*      */       }
/*      */       
/* 1386 */       double[] bounds = (double[])p.elementAt(6);
/* 1387 */       this.CurrentOneDSearch.LowerBound = bounds[0];
/* 1388 */       this.CurrentOneDSearch.UpperBound = bounds[1];
/*      */       
/* 1390 */       this.CurrentOneDSearch.NewBound = ((this.CurrentOneDSearch.LowerBound + this.CurrentOneDSearch.UpperBound) / 2);
/* 1391 */       Vector[1] = this.CurrentOneDSearch.NewBound;
/* 1392 */       this.CurrentOneDSearch.Value = EvaluatePolynomial(this.ThePolynomial, Vector);
/* 1393 */       this.CurrentOneDSearch.Derivative = EvaluatePolynomial(this.OneDDerivative, Vector);
/*      */       
/* 1395 */       this.CurrentIteration = 0;
/* 1396 */       break;
/*      */     
/*      */     case 50102: 
/* 1399 */       this.CurrentIteration = ((Integer)p.elementAt(0)).intValue();
/* 1400 */       int NewUpperOrLower = ((Integer)p.elementAt(1)).intValue();
/*      */       
/* 1402 */       boolean Flag = true;
/* 1403 */       if ((this.CurrentIteration == 1) && (this.OneDObjective == 0)) {
/* 1404 */         if (((this.CurrentOneDSearch.Derivative < 0) && (NewUpperOrLower == 2)) || ((this.CurrentOneDSearch.Derivative > 0) && (NewUpperOrLower == 1))) {
/* 1405 */           Flag = false;
/*      */         }
/* 1407 */       } else if ((this.CurrentIteration == 1) && (this.OneDObjective == 1) && (
/* 1408 */         ((this.CurrentOneDSearch.Derivative < 0) && (NewUpperOrLower == 1)) || ((this.CurrentOneDSearch.Derivative > 0) && (NewUpperOrLower == 2)))) {
/* 1409 */         Flag = false;
/*      */       }
/* 1411 */       if (Flag == false) {
/* 1412 */         this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1413 */         return false;
/*      */       }
/*      */       
/* 1416 */       if (NewUpperOrLower == 1) {
/* 1417 */         this.CurrentOneDSearch.UpperBound = this.CurrentOneDSearch.NewBound;
/*      */       } else
/* 1419 */         this.CurrentOneDSearch.LowerBound = this.CurrentOneDSearch.NewBound;
/* 1420 */       this.CurrentOneDSearch.NewBound = ((this.CurrentOneDSearch.LowerBound + this.CurrentOneDSearch.UpperBound) / 2);
/* 1421 */       Vector[1] = this.CurrentOneDSearch.NewBound;
/* 1422 */       this.CurrentOneDSearch.Value = EvaluatePolynomial(this.ThePolynomial, Vector);
/* 1423 */       this.CurrentOneDSearch.Derivative = EvaluatePolynomial(this.OneDDerivative, Vector);
/*      */       
/* 1425 */       break;
/*      */     
/*      */ 
/*      */     case 50201: 
/* 1429 */       this.ThePolynomial.NumVariables = 2;
/* 1430 */       this.TheDerivative[1].NumVariables = 2;
/* 1431 */       this.TheDerivative[2].NumVariables = 2;
/* 1432 */       this.ThePolynomial.NumTerms = ((Integer)p.elementAt(0)).intValue();
/* 1433 */       this.TheDerivative[1].NumTerms = this.ThePolynomial.NumTerms;
/* 1434 */       this.TheDerivative[2].NumTerms = this.ThePolynomial.NumTerms;
/*      */       
/*      */ 
/* 1437 */       double[] fx_coefficient = (double[])p.elementAt(1);
/* 1438 */       int[] fx_exponent = (int[])p.elementAt(2);
/* 1439 */       int[] fx2_exponent = (int[])p.elementAt(3);
/* 1440 */       for (int i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/* 1441 */         this.ThePolynomial.Coefficient[i] = fx_coefficient[(i - 1)];
/* 1442 */         this.ThePolynomial.Exponent[i][1] = fx_exponent[(i - 1)];
/* 1443 */         this.ThePolynomial.Exponent[i][2] = fx2_exponent[(i - 1)];
/*      */       }
/*      */       
/*      */ 
/* 1447 */       double[] dfx_coefficient = (double[])p.elementAt(4);
/* 1448 */       int[] dfx_exponent = (int[])p.elementAt(5);
/* 1449 */       int[] dfx2_exponent = (int[])p.elementAt(6);
/* 1450 */       for (i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/* 1451 */         this.TheDerivative[1].Coefficient[i] = dfx_coefficient[(i - 1)];
/* 1452 */         this.TheDerivative[1].Exponent[i][1] = dfx_exponent[(i - 1)];
/* 1453 */         this.TheDerivative[1].Exponent[i][2] = dfx2_exponent[(i - 1)];
/*      */       }
/*      */       
/*      */ 
/* 1457 */       dfx_coefficient = (double[])p.elementAt(7);
/* 1458 */       dfx_exponent = (int[])p.elementAt(8);
/* 1459 */       dfx2_exponent = (int[])p.elementAt(9);
/* 1460 */       for (i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/* 1461 */         this.TheDerivative[2].Coefficient[i] = dfx_coefficient[(i - 1)];
/* 1462 */         this.TheDerivative[2].Exponent[i][1] = dfx_exponent[(i - 1)];
/* 1463 */         this.TheDerivative[2].Exponent[i][2] = dfx2_exponent[(i - 1)];
/*      */       }
/*      */       
/*      */ 
/* 1467 */       double[] x_value = (double[])p.elementAt(10);
/* 1468 */       this.CurrentGradientSearch.InitialSolution[1] = x_value[0];
/* 1469 */       this.CurrentGradientSearch.InitialSolution[2] = x_value[1];
/*      */       
/* 1471 */       Vector[1] = x_value[0];
/* 1472 */       Vector[2] = x_value[1];
/* 1473 */       this.CurrentGradientSearch.Gradient[1] = EvaluatePolynomial(this.TheDerivative[1], Vector);
/* 1474 */       this.CurrentGradientSearch.Gradient[2] = EvaluatePolynomial(this.TheDerivative[2], Vector);
/*      */       
/* 1476 */       this.CurrentIteration = 0;
/* 1477 */       break;
/*      */     
/*      */     case 50202: 
/* 1480 */       this.CurrentIteration = (((Integer)p.elementAt(0)).intValue() - 1);
/* 1481 */       double[] a = (double[])p.elementAt(1);
/* 1482 */       double[] b = (double[])p.elementAt(2);
/* 1483 */       this.CurrentGradientSearch.a[1] = a[0];
/* 1484 */       this.CurrentGradientSearch.a[2] = a[1];
/* 1485 */       this.CurrentGradientSearch.b[1] = b[0];
/* 1486 */       this.CurrentGradientSearch.b[2] = b[1];
/*      */       
/* 1488 */       boolean Flag = true;
/* 1489 */       if (Math.abs(this.CurrentGradientSearch.a[1] - this.CurrentGradientSearch.InitialSolution[1]) > 0.1D)
/* 1490 */         Flag = false;
/* 1491 */       if (Math.abs(this.CurrentGradientSearch.b[1] - this.CurrentGradientSearch.Gradient[1]) > 0.1D)
/* 1492 */         Flag = false;
/* 1493 */       if (Math.abs(this.CurrentGradientSearch.a[2] - this.CurrentGradientSearch.InitialSolution[2]) > 0.1D)
/* 1494 */         Flag = false;
/* 1495 */       if (Math.abs(this.CurrentGradientSearch.b[2] - this.CurrentGradientSearch.Gradient[2]) > 0.1D) {
/* 1496 */         Flag = false;
/*      */       }
/* 1498 */       if ((Flag == false) && (this.CurrentIteration == 0)) {
/* 1499 */         this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1500 */         return false;
/*      */       }
/* 1502 */       ConvertPolyToOneD(this.ThePolynomial, this.CurrentGradientSearch.a, this.CurrentGradientSearch.b, this.OneDPolynomial);
/* 1503 */       this.CurrentGradientSearch.t = 0.0D;
/* 1504 */       this.CurrentGradientSearch.NewSolution[1] = 0.0D;
/* 1505 */       this.CurrentGradientSearch.NewSolution[2] = 0.0D;
/* 1506 */       break;
/*      */     case 50203: 
/* 1508 */       double UpperBd = 1.0D;
/*      */       double Answer;
/* 1510 */       do { UpperBd *= 10;
/* 1511 */         Answer = DoOneDimensionalSearch(this.OneDPolynomial, 0.0D, UpperBd, 25);
/* 1512 */       } while ((this.CurrentOneDSearch.NewBound >= 0.9D * UpperBd) && (UpperBd != 'Ϩ'));
/* 1513 */       this.CurrentGradientSearch.t = this.CurrentOneDSearch.NewBound;
/* 1514 */       break;
/*      */     case 50204: 
/* 1516 */       this.CurrentIteration = (((Integer)p.elementAt(0)).intValue() - 1);
/* 1517 */       this.CurrentGradientSearch.NewSolution[1] = (this.CurrentGradientSearch.a[1] + this.CurrentGradientSearch.b[1] * this.CurrentGradientSearch.t);
/* 1518 */       this.CurrentGradientSearch.NewSolution[2] = (this.CurrentGradientSearch.a[2] + this.CurrentGradientSearch.b[2] * this.CurrentGradientSearch.t);
/* 1519 */       break;
/*      */     case 50205: 
/* 1521 */       this.CurrentIteration = (((Integer)p.elementAt(0)).intValue() - 1);
/* 1522 */       double[] x_value = (double[])p.elementAt(1);
/* 1523 */       this.CurrentGradientSearch.NewSolution[1] = x_value[0];
/* 1524 */       this.CurrentGradientSearch.NewSolution[2] = x_value[1];
/* 1525 */       if (Math.abs(this.CurrentGradientSearch.NewSolution[1] - this.CurrentGradientSearch.a[1] - this.CurrentGradientSearch.b[1] * this.CurrentGradientSearch.t) < Math.max(0.05D, 0.05D * this.CurrentGradientSearch.NewSolution[1])) {
/* 1526 */         this.CurrentGradientSearch.NewSolution[1] = (this.CurrentGradientSearch.a[1] + this.CurrentGradientSearch.b[1] * this.CurrentGradientSearch.t);
/*      */       }
/* 1528 */       else if (this.CurrentIteration == 0) {
/* 1529 */         this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1530 */         return false;
/*      */       }
/* 1532 */       if (Math.abs(this.CurrentGradientSearch.NewSolution[2] - this.CurrentGradientSearch.a[2] - this.CurrentGradientSearch.b[2] * this.CurrentGradientSearch.t) < Math.max(0.05D, 0.05D * this.CurrentGradientSearch.NewSolution[2])) {
/* 1533 */         this.CurrentGradientSearch.NewSolution[2] = (this.CurrentGradientSearch.a[2] + this.CurrentGradientSearch.b[2] * this.CurrentGradientSearch.t);
/*      */       }
/* 1535 */       else if (this.CurrentIteration == 0) {
/* 1536 */         this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1537 */         return false;
/*      */       }
/* 1539 */       this.CurrentGradientSearch.InitialSolution[1] = this.CurrentGradientSearch.NewSolution[1];
/* 1540 */       this.CurrentGradientSearch.InitialSolution[2] = this.CurrentGradientSearch.NewSolution[2];
/* 1541 */       this.CurrentGradientSearch.Gradient[1] = EvaluatePolynomial(this.TheDerivative[1], this.CurrentGradientSearch.InitialSolution);
/* 1542 */       this.CurrentGradientSearch.Gradient[2] = EvaluatePolynomial(this.TheDerivative[2], this.CurrentGradientSearch.InitialSolution);
/* 1543 */       this.NewXCalculated = true;
/* 1544 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 50401: 
/* 1550 */       ia = (int[])p.elementAt(0);
/*      */       
/* 1552 */       this.SimplexModel.NumVariables = ia[0];
/* 1553 */       this.SimplexModel.NumConstraints = ia[1];
/*      */       
/* 1555 */       this.ThePolynomial.NumTerms = ia[2];
/* 1556 */       this.ThePolynomial.NumVariables = this.SimplexModel.NumVariables;
/*      */       
/* 1558 */       for (i = 1; i <= this.SimplexModel.NumVariables; i++) {
/* 1559 */         this.TheDerivative[i] = new ORSolverBase.PolynomialType(this);
/* 1560 */         this.TheDerivative[i].NumVariables = this.SimplexModel.NumVariables;
/* 1561 */         this.TheDerivative[i].NumTerms = this.ThePolynomial.NumTerms;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1566 */       boolean Flag = ((Boolean)p.elementAt(1)).booleanValue();
/* 1567 */       this.SimplexModel.Objective = (Flag ? 0 : 1);
/*      */       
/*      */ 
/*      */ 
/* 1571 */       double[] fx_coefficient = (double[])p.elementAt(2);
/* 1572 */       int[][] exponents = (int[][])p.elementAt(3);
/* 1573 */       for (i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/* 1574 */         this.ThePolynomial.Coefficient[i] = fx_coefficient[(i - 1)];
/* 1575 */         for (int j = 1; j <= this.ThePolynomial.NumVariables; j++) {
/* 1576 */           this.ThePolynomial.Exponent[i][j] = exponents[(i - 1)][(j - 1)];
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1581 */       ConstraintCoef = (double[][])p.elementAt(4);
/* 1582 */       ia = (int[])p.elementAt(5);
/* 1583 */       for (i = 0; i < this.SimplexModel.NumConstraints;) {
/* 1584 */         this.SimplexModel.Constraint[(i + 1)] = ia[i];
/* 1585 */         this.SimplexModel.RightHandSide[(i + 1)] = ConstraintCoef[i][0];
/* 1586 */         for (int j = 0; j < this.SimplexModel.NumVariables; j++) {
/* 1587 */           this.SimplexModel.A[(i + 1)][(j + 1)] = ConstraintCoef[i][(j + 1)];
/*      */         }
/* 1583 */         i++; continue;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1749 */         this.CurrentAlgorithm = 8;
/* 1750 */         if (this.pop == null)
/*      */         {
/* 1752 */           this.pop = new Population(this.ThePolynomial, this.SimplexModel);
/*      */         }
/*      */         
/* 1755 */         if (this.getype.currentindex == this.getype.ge_size) {
/* 1756 */           ArrayList listg = this.pop.nextIteration();
/* 1757 */           this.getype.ge_size += 1;
/* 1758 */           this.getype.currentindex += 1;
/* 1759 */           this.getype.currentindex = this.getype.ge_size;
/* 1760 */           System.out.println("tttt:".concat(String.valueOf(String.valueOf(this.getype.currentindex))));
/*      */           
/*      */ 
/* 1763 */           this.getype.best.addElement(listg.get(0));
/* 1764 */           this.getype.storePopulation.addElement(listg.get(1));
/* 1765 */           this.getype.generation.addElement(listg.get(2));
/*      */         }
/*      */         else
/*      */         {
/* 1769 */           this.getype.currentindex += 1;
/* 1770 */           System.out.println("eeeetttt:".concat(String.valueOf(String.valueOf(this.getype.currentindex))));
/*      */           
/* 1772 */           break;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1782 */           for (i = 0; i < this.ThePolynomial.NumVariables; i++) {
/* 1783 */             double[] fx_coefficient = (double[])p.elementAt(2 * i);
/* 1784 */             int[][] exponents = (int[][])p.elementAt(2 * i + 1);
/* 1785 */             for (int j = 0; j < this.ThePolynomial.NumTerms; j++) {
/* 1786 */               this.TheDerivative[(i + 1)].Coefficient[(j + 1)] = fx_coefficient[j];
/* 1787 */               for (int l = 0; l < this.ThePolynomial.NumVariables; l++)
/* 1788 */                 this.TheDerivative[(i + 1)].Exponent[(j + 1)][(l + 1)] = exponents[j][l];
/*      */             }
/*      */           }
/* 1791 */           x_value = (double[])p.elementAt(this.ThePolynomial.NumVariables * 2);
/* 1792 */           for (i = 0; i < this.ThePolynomial.NumVariables;) {
/* 1793 */             this.CurrentFrankWolfe.Solution[(i + 1)] = x_value[i];
/* 1794 */             this.GradientValue[(i + 1)] = EvaluatePolynomial(this.TheDerivative[(i + 1)], this.CurrentFrankWolfe.Solution);i++; continue;
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1799 */             this.CurrentIteration = (((Integer)p.elementAt(0)).intValue() - 1);
/* 1800 */             double[] fx_coefficient = (double[])p.elementAt(1);
/* 1801 */             boolean Flag = true;
/* 1802 */             for (int i = 1; i <= this.ThePolynomial.NumVariables; i++)
/* 1803 */               this.CurrentFrankWolfe.ObjectiveFunction[i] = fx_coefficient[(i - 1)];
/* 1804 */             if (this.CurrentIteration == 0) {
/* 1805 */               for (i = 1; i <= this.ThePolynomial.NumVariables; i++) {
/* 1806 */                 if (Math.abs(this.CurrentFrankWolfe.ObjectiveFunction[i] - EvaluatePolynomial(this.TheDerivative[i], this.CurrentFrankWolfe.Solution)) > 0.1D)
/* 1807 */                   Flag = false;
/*      */               }
/*      */             }
/* 1810 */             if (Flag) {
/* 1811 */               for (i = 1; i <= 10; i++) {
/* 1812 */                 this.SimplexModel.ObjectiveFunction[i] = this.CurrentFrankWolfe.ObjectiveFunction[i];
/*      */               }
/*      */             }
/* 1815 */             this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1816 */             return false;
/*      */             
/*      */ 
/*      */ 
/* 1820 */             this.CurrentIteration = (((Integer)p.elementAt(0)).intValue() - 1);
/* 1821 */             RevisedSimplexMethod(false);
/*      */             
/*      */ 
/* 1824 */             for (i = 1; i <= this.ThePolynomial.NumVariables;) {
/* 1825 */               this.CurrentFrankWolfe.FWLPSolution[i] = this.LPSolution.Solution[i];
/* 1826 */               this.CurrentFrankWolfe.Vector1[i] = 0.0D;
/* 1827 */               this.CurrentFrankWolfe.Vector2[i] = 0.0D;
/* 1828 */               this.CurrentFrankWolfe.Vector3[i] = 0.0D;
/*      */               
/* 1830 */               this.CurrentFrankWolfe.a[i] = this.CurrentFrankWolfe.Solution[i];
/* 1831 */               this.CurrentFrankWolfe.b[i] = (this.CurrentFrankWolfe.FWLPSolution[i] - this.CurrentFrankWolfe.Solution[i]);i++; continue;
/*      */               
/*      */ 
/*      */ 
/*      */ 
/* 1836 */               double[] a = (double[])p.elementAt(0);
/* 1837 */               double[] b = (double[])p.elementAt(1);
/* 1838 */               double[] c = (double[])p.elementAt(2);
/* 1839 */               boolean Flag = true;
/* 1840 */               for (int i = 1; i <= this.ThePolynomial.NumVariables; i++) {
/* 1841 */                 this.CurrentFrankWolfe.Vector1[i] = a[(i - 1)];
/* 1842 */                 if (Math.abs(this.CurrentFrankWolfe.Vector1[i] - this.CurrentFrankWolfe.Solution[i]) > Math.max(0.01D * this.CurrentFrankWolfe.Solution[i], 0.01D))
/* 1843 */                   Flag = false;
/* 1844 */                 this.CurrentFrankWolfe.Vector2[i] = b[(i - 1)];
/* 1845 */                 if (Math.abs(this.CurrentFrankWolfe.Vector2[i] - this.CurrentFrankWolfe.FWLPSolution[i]) > Math.max(0.01D * this.CurrentFrankWolfe.FWLPSolution[i], 0.01D))
/* 1846 */                   Flag = false;
/* 1847 */                 this.CurrentFrankWolfe.Vector3[i] = c[(i - 1)];
/* 1848 */                 if (Math.abs(this.CurrentFrankWolfe.Vector3[i] - this.CurrentFrankWolfe.Solution[i]) > Math.max(0.01D * this.CurrentFrankWolfe.Solution[i], 0.01D))
/* 1849 */                   Flag = false;
/*      */               }
/* 1851 */               if (Flag == false) {
/* 1852 */                 this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1853 */                 return false;
/*      */                 
/*      */ 
/*      */ 
/* 1857 */                 this.CurrentIteration = (((Integer)p.elementAt(0)).intValue() - 1);
/* 1858 */                 double[] a = (double[])p.elementAt(1);
/* 1859 */                 double[] b = (double[])p.elementAt(2);
/* 1860 */                 boolean Flag = true;
/* 1861 */                 for (int i = 1; i <= this.ThePolynomial.NumVariables; i++) {
/* 1862 */                   this.CurrentFrankWolfe.a[i] = a[(i - 1)];
/* 1863 */                   if (Math.abs(this.CurrentFrankWolfe.a[i] - this.CurrentFrankWolfe.Solution[i]) > Math.max(0.01D * this.CurrentFrankWolfe.Solution[i], 0.01D))
/* 1864 */                     Flag = false;
/* 1865 */                   this.CurrentFrankWolfe.b[i] = b[(i - 1)];
/* 1866 */                   if (Math.abs(this.CurrentFrankWolfe.b[i] - this.CurrentFrankWolfe.FWLPSolution[i] + this.CurrentFrankWolfe.Solution[i]) > Math.max(0.01D * this.CurrentFrankWolfe.b[i], 0.01D))
/* 1867 */                     Flag = false;
/*      */                 }
/* 1869 */                 if ((Flag == false) && (this.CurrentIteration == 0)) {
/* 1870 */                   this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1871 */                   return false;
/*      */                 }
/* 1873 */                 ConvertPolyToOneD(this.ThePolynomial, this.CurrentFrankWolfe.a, this.CurrentFrankWolfe.b, this.OneDPolynomial);
/* 1874 */                 break;
/*      */                 
/* 1876 */                 this.CurrentIteration = (((Integer)p.elementAt(0)).intValue() - 1);
/* 1877 */                 this.CurrentFrankWolfe.t = DoOneDimensionalSearch(this.OneDPolynomial, 0.0D, 1.0D, 25);
/* 1878 */                 for (int i = 1; i <= this.ThePolynomial.NumVariables; i++) {
/* 1879 */                   this.CurrentFrankWolfe.NewSolution[i] = (this.CurrentFrankWolfe.Solution[i] + this.CurrentFrankWolfe.t * (this.CurrentFrankWolfe.FWLPSolution[i] - this.CurrentFrankWolfe.Solution[i]));
/* 1880 */                   this.CurrentFrankWolfe.Solution[i] = this.CurrentFrankWolfe.NewSolution[i];
/* 1881 */                   this.GradientValue[i] = EvaluatePolynomial(this.TheDerivative[i], this.CurrentFrankWolfe.Solution);
/*      */                 }
/* 1883 */                 this.NewXCalculated = true;
/* 1884 */                 break;
/*      */                 
/*      */ 
/* 1887 */                 int[] ia = (int[])p.elementAt(0);
/* 1888 */                 this.SUMTModel.NumVariableswlb = ia[0];
/* 1889 */                 this.SUMTModel.NumVariableswolb = ia[1];
/* 1890 */                 this.SUMTModel.NumInequalityConstraints = ia[2];
/* 1891 */                 this.SUMTModel.NumEqualityConstraints = ia[3];
/* 1892 */                 this.SUMTModel.f.NumTerms = ia[4];
/* 1893 */                 this.SUMTModel.NumVariables = (this.SUMTModel.NumVariableswlb + this.SUMTModel.NumVariableswolb);
/* 1894 */                 this.SUMTModel.f.NumVariables = this.SUMTModel.NumVariables;
/* 1895 */                 this.SUMTModel.NumConstraints = (this.SUMTModel.NumInequalityConstraints + this.SUMTModel.NumEqualityConstraints);
/* 1896 */                 break;
/*      */                 
/* 1898 */                 double[][] coefficient = (double[][])p.elementAt(0);
/* 1899 */                 int[][][] exponent = (int[][][])p.elementAt(1);
/* 1900 */                 double[] xValue = (double[])p.elementAt(2);
/* 1901 */                 this.CurrentSUMT.r = ((Double)p.elementAt(3)).doubleValue();
/*      */                 
/*      */ 
/* 1904 */                 for (int i = 1; i <= this.SUMTModel.f.NumTerms; i++) {
/* 1905 */                   this.SUMTModel.f.Coefficient[i] = coefficient[0][(i - 1)];
/* 1906 */                   for (int j = 1; j <= this.SUMTModel.f.NumVariables; j++) {
/* 1907 */                     this.SUMTModel.f.Exponent[i][j] = exponent[0][(i - 1)][(j - 1)];
/*      */                   }
/*      */                 }
/* 1910 */                 for (int k = 1; k <= this.SUMTModel.NumEqualityConstraints + this.SUMTModel.NumInequalityConstraints; k++) {
/* 1911 */                   this.SUMTModel.B[k].NumTerms = this.SUMTModel.f.NumTerms;
/* 1912 */                   this.SUMTModel.B[k].NumVariables = this.SUMTModel.f.NumVariables;
/* 1913 */                   for (i = 1; i <= this.SUMTModel.f.NumTerms; i++) {
/* 1914 */                     this.SUMTModel.B[k].Coefficient[i] = coefficient[k][(i - 1)];
/* 1915 */                     for (int j = 1; j <= this.SUMTModel.f.NumVariables; j++) {
/* 1916 */                       this.SUMTModel.B[k].Exponent[i][j] = exponent[k][(i - 1)][(j - 1)];
/*      */                     }
/*      */                   }
/*      */                 }
/* 1920 */                 int l = this.SUMTModel.NumEqualityConstraints + this.SUMTModel.NumInequalityConstraints;
/* 1921 */                 for (k = 1; k <= this.SUMTModel.NumVariableswlb; k++) {
/* 1922 */                   this.SUMTModel.L[k].NumTerms = this.SUMTModel.f.NumTerms;
/* 1923 */                   this.SUMTModel.L[k].NumVariables = this.SUMTModel.f.NumVariables;
/* 1924 */                   for (i = 1; i <= this.SUMTModel.f.NumTerms; i++) {
/* 1925 */                     this.SUMTModel.L[k].Coefficient[i] = coefficient[(k + l)][(i - 1)];
/* 1926 */                     for (int j = 1; j <= this.SUMTModel.f.NumVariables; j++) {
/* 1927 */                       this.SUMTModel.L[k].Exponent[i][j] = exponent[(k + l)][(i - 1)][(j - 1)];
/*      */                     }
/*      */                   }
/*      */                 }
/* 1931 */                 for (i = 1; i <= this.SUMTModel.f.NumVariables; i++) {
/* 1932 */                   this.CurrentSUMT.InitialSolution[i] = xValue[(i - 1)];
/*      */                 }
/*      */                 
/* 1935 */                 DoSUMT();
/* 1936 */                 break;
/*      */                 
/* 1938 */                 arraycopy1(this.CurrentSUMT.FinalSolution, this.CurrentSUMT.InitialSolution);
/* 1939 */                 break;
/*      */                 
/* 1941 */                 this.CurrentSUMT.r = ((Double)p.elementAt(0)).doubleValue();
/* 1942 */                 DoSUMT();
/* 1943 */                 break;
/*      */                 
/* 1945 */                 this.ThePolynomial.NumVariables = 2;
/* 1946 */                 this.ThePolynomial.NumTerms = ((Integer)p.elementAt(0)).intValue();
/* 1947 */                 double[] fx_coefficient = (double[])p.elementAt(1);
/* 1948 */                 int[] fx_exponent = (int[])p.elementAt(2);
/* 1949 */                 int[] fx2_exponent = (int[])p.elementAt(3);
/* 1950 */                 double[] x_value = (double[])p.elementAt(4);
/*      */                 
/* 1952 */                 this.FirstGradientSearch.InitialSolution[1] = x_value[0];
/* 1953 */                 this.FirstGradientSearch.InitialSolution[2] = x_value[1];
/* 1954 */                 this.CurrentGradientSearch.InitialSolution[1] = x_value[0];
/* 1955 */                 this.CurrentGradientSearch.InitialSolution[2] = x_value[1];
/* 1956 */                 for (int i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/* 1957 */                   this.ThePolynomial.Coefficient[i] = fx_coefficient[(i - 1)];
/* 1958 */                   this.ThePolynomial.Exponent[i][1] = fx_exponent[(i - 1)];
/* 1959 */                   this.ThePolynomial.Exponent[i][2] = fx2_exponent[(i - 1)];
/*      */                 }
/* 1961 */                 this.ErrorTol = ((Double)p.elementAt(5)).doubleValue();
/*      */                 
/* 1963 */                 DoGradientSearch(); } } } } } }
/*      */     int i;
/*      */     double[] x_value;
/* 1966 */     int i; return true;
/*      */   }
/*      */   
/*      */   protected void ORPrint()
/*      */   {
/* 1971 */     if (this.procedure == 1) {
/* 1972 */       PrintNonlinearInteractiveOneDSearch();
/* 1973 */     } else if (this.procedure == 2) {
/* 1974 */       PrintNonlinearAutoGradientSearch();
/* 1975 */     } else if (this.procedure == 3) {
/* 1976 */       PrintNonlinearGradientSearch();
/* 1977 */     } else if (this.procedure == 4) {
/* 1978 */       PrintNonlinearModifiedSimplex();
/* 1979 */     } else if (this.procedure == 5) {
/* 1980 */       PrintNonlinearFrankWolfe();
/* 1981 */     } else if (this.procedure == 6) {
/* 1982 */       PrintNonlinearSUMT();
/* 1983 */     } else if (this.procedure == 7) {
/* 1984 */       PrintNonlinearAnnealing();
/* 1985 */     } else if (this.procedure == 8) {
/* 1986 */       PrintNonlinearGenetic();
/*      */     }
/*      */   }
/*      */   
/*      */   private void PrintNonlinearGenetic() {
/* 1991 */     PrintPolynomial(this.ThePolynomial);
/* 1992 */     Skip(2);
/*      */   }
/*      */   
/*      */   private void PrintNonlinearAnnealing()
/*      */   {
/* 1997 */     PrintPolynomial(this.ThePolynomial);
/* 1998 */     Skip(2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void PrintNonlinearInteractiveOneDSearch()
/*      */   {
/* 2005 */     PrintLine("Interactive One-Dimensional Search Procedure:");
/* 2006 */     SkipLine();
/* 2007 */     if (this.OneDObjective == 0) {
/* 2008 */       Print("Max f(X) = ");
/*      */     } else
/* 2010 */       Print("Min f(X) = ");
/* 2011 */     PrintPolynomial(this.ThePolynomial);
/* 2012 */     Skip(2);
/* 2013 */     Print("df(X)/dX = ");
/* 2014 */     PrintPolynomial(this.OneDDerivative);
/* 2015 */     double LastSolution = 0.0D;
/* 2016 */     Skip(2);
/*      */     
/* 2018 */     int n = this.steps.size();
/* 2019 */     int i = 0;
/* 2020 */     IOROperation op = null;
/* 2021 */     while (i < n) {
/* 2022 */       op = (IOROperation)this.steps.elementAt(i);
/* 2023 */       switch (op.operation_code) {
/*      */       case 50101: 
/* 2025 */         doWork(op);
/* 2026 */         Print("Lower Bound: ".concat(String.valueOf(String.valueOf(FormatReal(this.CurrentOneDSearch.LowerBound, 5, 0, 0)))));
/* 2027 */         PrintLine("      Upper Bound: ".concat(String.valueOf(String.valueOf(FormatReal(this.CurrentOneDSearch.UpperBound, 5, 0, 0)))));
/* 2028 */         Skip(2);
/* 2029 */         PrintLine("Iteration | df(X)/dX |   X(L)   |   X(U)   |  New X'  |  f(X')");
/* 2030 */         PrintLine("__________|__________|__________|__________|__________|__________");
/* 2031 */         Print(String.valueOf(String.valueOf(new StringBuffer("    ").append(FormatInteger(i, 2, 0, 1)).append("    |"))));
/* 2032 */         Print("          |");
/* 2033 */         Print(String.valueOf(String.valueOf(new StringBuffer("  ").append(FormatReal(this.CurrentOneDSearch.LowerBound, 6, 0, 1)).append("  |"))));
/* 2034 */         Print(String.valueOf(String.valueOf(new StringBuffer("  ").append(FormatReal(this.CurrentOneDSearch.UpperBound, 6, 0, 1)).append("  |"))));
/* 2035 */         Print(String.valueOf(String.valueOf(new StringBuffer("  ").append(FormatReal(this.CurrentOneDSearch.NewBound, 6, 0, 1)).append("  |"))));
/* 2036 */         LastSolution = (this.CurrentOneDSearch.UpperBound + this.CurrentOneDSearch.LowerBound) / 2;
/* 2037 */         PrintLine("  ".concat(String.valueOf(String.valueOf(FormatReal(this.CurrentOneDSearch.Value, 6, 0, 1)))));
/* 2038 */         break;
/*      */       case 50102: 
/* 2040 */         Print(String.valueOf(String.valueOf(new StringBuffer("    ").append(FormatInteger(i, 2, 0, 1)).append("    |"))));
/* 2041 */         Print(String.valueOf(String.valueOf(new StringBuffer("  ").append(FormatReal(this.CurrentOneDSearch.Derivative, 6, 1, 1)).append("  |"))));
/* 2042 */         doWork(op);
/* 2043 */         Print(String.valueOf(String.valueOf(new StringBuffer("  ").append(FormatReal(this.CurrentOneDSearch.LowerBound, 6, 0, 1)).append("  |"))));
/* 2044 */         Print(String.valueOf(String.valueOf(new StringBuffer("  ").append(FormatReal(this.CurrentOneDSearch.UpperBound, 6, 0, 1)).append("  |"))));
/* 2045 */         Print(String.valueOf(String.valueOf(new StringBuffer("  ").append(FormatReal(this.CurrentOneDSearch.NewBound, 6, 0, 1)).append("  |"))));
/* 2046 */         LastSolution = (this.CurrentOneDSearch.UpperBound + this.CurrentOneDSearch.LowerBound) / 2;
/* 2047 */         PrintLine("  ".concat(String.valueOf(String.valueOf(FormatReal(this.CurrentOneDSearch.Value, 6, 0, 1)))));
/* 2048 */         break;
/*      */       default: 
/* 2050 */         doWork(op);
/*      */       }
/*      */       
/* 2053 */       i++;
/*      */     }
/* 2055 */     if (n > 0)
/* 2056 */       PrintLine("  Stop    |          |          |          |          |");
/* 2057 */     SkipLine();
/* 2058 */     PrintLine("Solution: X = ".concat(String.valueOf(String.valueOf(FormatReal(LastSolution, 6, 0, 0)))));
/*      */   }
/*      */   
/*      */   private void PrintNonlinearAutoGradientSearch() {
/* 2062 */     PrintLine("Automatic Gradient Search Procedure: ");
/* 2063 */     SkipLine();
/* 2064 */     if (this.steps.size() > 0) {
/* 2065 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("Initial trial solution: (X1, X2) = (").append(RealToString(this.FirstGradientSearch.InitialSolution[1], 6)).append(", ").append(RealToString(this.FirstGradientSearch.InitialSolution[2], 6)).append(")."))));
/*      */       
/*      */ 
/* 2068 */       SkipLine();
/* 2069 */       Print("Max f(X1,X2) = ");
/* 2070 */       PrintPolynomial(this.ThePolynomial);
/* 2071 */       Skip(2);
/* 2072 */       PrintLine("Error Tolerance:");
/* 2073 */       SkipLine();
/* 2074 */       PrintLine("   abs(df/dXj) <= ".concat(String.valueOf(String.valueOf(FormatReal(this.ErrorTol, 6, 0, 0)))));
/* 2075 */       Skip(2);
/*      */       
/* 2077 */       if ((Math.abs(this.CurrentGradientSearch.InitialSolution[1]) > 1.0E10D) || (Math.abs(this.CurrentGradientSearch.InitialSolution[2]) > 1.0E10D)) {
/* 2078 */         PrintLine("Out of bounds error");
/* 2079 */         SkipLine();
/* 2080 */         PrintLine("The problem appears to be unbounded.");
/*      */       }
/*      */       else {
/* 2083 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("Final solution: (X1, X2) = (").append(RealToString(this.CurrentGradientSearch.InitialSolution[1], 6)).append(",").append(RealToString(this.CurrentGradientSearch.InitialSolution[2], 6)).append(")"))));
/* 2084 */         SkipLine();
/* 2085 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("Final gradient = (").append(RealToString(this.CurrentGradientSearch.Gradient[1], 6)).append(",").append(RealToString(this.CurrentGradientSearch.Gradient[2], 6)).append(")"))));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void PrintNonlinearGradientSearch()
/*      */   {
/* 2093 */     PrintLine("Interactive Gradient Search Procedure:");
/* 2094 */     SkipLine();
/*      */     
/* 2096 */     int n = this.steps.size();
/* 2097 */     int iStep = 0;
/* 2098 */     int It = 0;
/* 2099 */     IOROperation op = null;
/* 2100 */     while (iStep < n) {
/* 2101 */       op = (IOROperation)this.steps.elementAt(iStep);
/* 2102 */       switch (op.operation_code) {
/*      */       case 50201: 
/* 2104 */         doWork(op);
/* 2105 */         Print("Max f(X1,X2) = ");
/* 2106 */         PrintPolynomial(this.ThePolynomial);
/* 2107 */         SkipLine();
/* 2108 */         for (int i = 1; i <= 2; i++) {
/* 2109 */           Print(String.valueOf(String.valueOf(new StringBuffer("df/dX").append(IntegerToString(i, 1)).append(" = "))));
/* 2110 */           PrintPolynomial(this.TheDerivative[i]);
/* 2111 */           SkipLine();
/*      */         }
/* 2113 */         SkipLine();
/* 2114 */         PrintLine("It.|  X'         | grad f(X')  |  X' + t[grad f(X')]     |  t* | X'+t[grad f]");
/* 2115 */         PrintLine("___|_____________|_____________|_________________________|_____|_____________");
/* 2116 */         break;
/*      */       case 50203: 
/* 2118 */         doWork(op);
/* 2119 */         It += 1;
/* 2120 */         Print(String.valueOf(String.valueOf(FormatInteger(It, 3, 0, 0))).concat("|"));
/* 2121 */         Print(String.valueOf(String.valueOf(new StringBuffer("(").append(FormatReal(this.CurrentGradientSearch.InitialSolution[1], 5, 0, 1)).append(",").append(FormatReal(this.CurrentGradientSearch.InitialSolution[2], 5, 0, 1)).append(")|"))));
/* 2122 */         Print(String.valueOf(String.valueOf(new StringBuffer("(").append(FormatReal(this.CurrentGradientSearch.Gradient[1], 5, 0, 1)).append(",").append(FormatReal(this.CurrentGradientSearch.Gradient[2], 5, 0, 1)).append(")|"))));
/* 2123 */         Print(String.valueOf(String.valueOf(new StringBuffer("(").append(FormatReal(this.CurrentGradientSearch.a[1], 5, 0, 1)).append(FormatReal(this.CurrentGradientSearch.b[1], 5, 1, 1)).append("t,").append(FormatReal(this.CurrentGradientSearch.a[2], 5, 0, 1)).append(FormatReal(this.CurrentGradientSearch.b[2], 5, 1, 1)).append("t)|"))));
/* 2124 */         Print(String.valueOf(String.valueOf(FormatReal(this.CurrentGradientSearch.t, 5, 0, 1))).concat("|"));
/* 2125 */         break;
/*      */       case 50205: 
/* 2127 */         doWork(op);
/* 2128 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("(").append(FormatReal(this.CurrentGradientSearch.NewSolution[1], 5, 0, 1)).append(",").append(FormatReal(this.CurrentGradientSearch.NewSolution[2], 5, 0, 1)).append(")"))));
/* 2129 */         break;
/*      */       case 50202: case 50204: default: 
/* 2131 */         doWork(op);
/* 2132 */         break;
/*      */       }
/* 2134 */       iStep++;
/* 2135 */       if (iStep == n) {
/* 2136 */         Skip(3);
/* 2137 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("Final solution: (X1, X2) = (").append(RealToString(this.CurrentGradientSearch.InitialSolution[1], 6)).append(",").append(RealToString(this.CurrentGradientSearch.InitialSolution[2], 6)).append(")"))));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void PrintNonlinearModifiedSimplex() {
/* 2143 */     PrintLine("    To solve a quadratic programming problem interactively by the modified");
/* 2144 */     PrintLine("simplex method presented in Sec. 14.7, you will need to do the following");
/* 2145 */     PrintLine("(expressed in the matrix notation of Sec. 14.6):");
/* 2146 */     SkipLine();
/* 2147 */     PrintLine("1. Choose \"General Linear Programming\" under the Area menu.");
/* 2148 */     SkipLine();
/* 2149 */     PrintLine("2. Choose \"Enter/Revise a Linear Programming Model\" under the Procedure menu.");
/* 2150 */     PrintLine("   Then enter a model with a vacuous objective function (all zero");
/* 2151 */     PrintLine("   coefficients) and with the following constraints:  Qx + A'u >= c', Ax <= b,");
/* 2152 */     PrintLine("   x >= 0, u >= 0 (except denote the u[i] variables as additional x[j]");
/* 2153 */     PrintLine("   variables).");
/* 2154 */     SkipLine();
/* 2155 */     PrintLine("3. Choose \"Set Up for the Simplex Method\" under the Procedure menu.  Then");
/* 2156 */     PrintLine("   introduce surplus variables (corresponding to y), slack variables");
/* 2157 */     PrintLine("   (corresponding to v), and artificial variables (corresponding to z[i])");
/* 2158 */     PrintLine("   as needed.  (A maximum of six functional constraints and ten variables");
/* 2159 */     PrintLine("   are allowed at this point.)  Choose the two-phase method for dealing");
/* 2160 */     PrintLine("   with artificial variables.");
/* 2161 */     SkipLine();
/* 2162 */     PrintLine("4. After setting up for Phase 1, ignore the instructions to choose \"Solve");
/* 2163 */     PrintLine("   Interactively by the Simplex Method\" and instead choose \"Solve");
/* 2164 */     PrintLine("   Interactively by the Modified Simplex Method\" under the Procedure menu.");
/* 2165 */     PrintLine("   When you finish applying this routine to the Phase 1 problem, you are");
/* 2166 */     PrintLine("   done.  If all z[i] = 0 in the Phase 1 solution, x (without u, v, z[i]) is");
/* 2167 */     PrintLine("   the desired optimal solution for the quadratic programming problem.");
/* 2168 */     Skip(2);
/* 2169 */     PrintLine("Note:   A' = A transpose");
/* 2170 */     PrintLine("        c' = c transpose");
/*      */   }
/*      */   
/*      */   private void PrintNonlinearFrankWolfe()
/*      */   {
/* 2175 */     int n = this.steps.size();
/* 2176 */     int iStep = 0;
/* 2177 */     IOROperation op = null;
/*      */     
/* 2179 */     this.ApplicationNumber = 6;
/* 2180 */     this.CommandNumber = 8;
/* 2181 */     PrintLine("Nonlinear Programming Model:");
/* 2182 */     SkipLine();
/* 2183 */     int k = 1;
/* 2184 */     while (iStep < n) {
/* 2185 */       op = (IOROperation)this.steps.elementAt(iStep);
/* 2186 */       switch (op.operation_code) {
/*      */       case 50401: 
/* 2188 */         doWork(op);
/* 2189 */         PrintSimplexModel();
/* 2190 */         Skip(2);
/* 2191 */         break;
/*      */       case 50402: 
/* 2193 */         doWork(op);
/* 2194 */         PrintLine("Frank-Wolfe Algorithm Solution:");
/* 2195 */         SkipLine();
/* 2196 */         for (int i = 1; i <= this.SimplexModel.NumVariables; i++) {
/* 2197 */           Print(String.valueOf(String.valueOf(new StringBuffer("df/dX").append(IntegerToString(i, 1)).append(" = "))));
/* 2198 */           PrintPolynomial(this.TheDerivative[i]);
/* 2199 */           Skip(2);
/*      */         }
/* 2201 */         switch (this.SimplexModel.NumVariables) {
/*      */         case 1: 
/* 2203 */           PrintLine("k | X^(k-1) |   c1  | X[LP]^(k) |   t*  |  X^(k)");
/* 2204 */           break;
/*      */         case 2: 
/* 2206 */           PrintLine("k |    X^(k-1)    |   c1  |   c2  |    X[LP]^k    |   t*  |      X^k");
/* 2207 */           break;
/*      */         case 3: 
/* 2209 */           PrintLine("k |     X^(k-1)    | c1 | c2 | c3 |      X[LP]^k      | t* |        X^k");
/*      */         }
/*      */         
/* 2212 */         switch (this.SimplexModel.NumVariables) {
/*      */         case 1: 
/* 2214 */           PrintLine("__|_________|_______|___________|_______|_________");
/* 2215 */           break;
/*      */         case 2: 
/* 2217 */           PrintLine("__|_______________|_______|_______|_______________|_______|_______________");
/* 2218 */           break;
/*      */         case 3: 
/* 2220 */           PrintLine("__|________________|____|____|____|___________________|____|___________________");
/*      */         }
/*      */         
/* 2223 */         break;
/*      */       case 50407: 
/* 2225 */         Print(String.valueOf(String.valueOf(FormatInteger(k, 2, 0, 0))).concat("|"));
/* 2226 */         switch (this.SimplexModel.NumVariables) {
/*      */         case 1: 
/* 2228 */           Print(String.valueOf(String.valueOf(new StringBuffer(" (").append(FormatReal(this.CurrentFrankWolfe.Solution[1], 5, 0, 1)).append(") |"))));
/* 2229 */           doWork(op);
/* 2230 */           Print(String.valueOf(String.valueOf(new StringBuffer(" ").append(FormatReal(this.CurrentFrankWolfe.ObjectiveFunction[1], 5, 0, 1)).append(" |"))));
/* 2231 */           Print(String.valueOf(String.valueOf(new StringBuffer("  (").append(FormatReal(this.CurrentFrankWolfe.FWLPSolution[1], 5, 0, 1)).append(")  |"))));
/* 2232 */           Print(String.valueOf(String.valueOf(new StringBuffer(" ").append(FormatReal(this.CurrentFrankWolfe.t, 5, 0, 1)).append(" |"))));
/* 2233 */           PrintLine(String.valueOf(String.valueOf(new StringBuffer(" (").append(FormatReal(this.CurrentFrankWolfe.NewSolution[1], 5, 0, 1)).append(")"))));
/* 2234 */           break;
/*      */         case 2: 
/* 2236 */           Print(String.valueOf(String.valueOf(new StringBuffer(" (").append(FormatReal(this.CurrentFrankWolfe.Solution[1], 5, 0, 1)).append(",").append(FormatReal(this.CurrentFrankWolfe.Solution[2], 5, 0, 1)).append(") |"))));
/* 2237 */           doWork(op);
/* 2238 */           Print(String.valueOf(String.valueOf(new StringBuffer(" ").append(FormatReal(this.CurrentFrankWolfe.ObjectiveFunction[1], 5, 0, 1)).append(" | ").append(FormatReal(this.CurrentFrankWolfe.ObjectiveFunction[2], 5, 0, 1)).append(" |"))));
/* 2239 */           Print(String.valueOf(String.valueOf(new StringBuffer(" (").append(FormatReal(this.CurrentFrankWolfe.FWLPSolution[1], 5, 0, 1)).append(",").append(FormatReal(this.CurrentFrankWolfe.FWLPSolution[2], 5, 0, 1)).append(") |"))));
/* 2240 */           Print(String.valueOf(String.valueOf(new StringBuffer(" ").append(FormatReal(this.CurrentFrankWolfe.t, 5, 0, 1)).append(" |"))));
/* 2241 */           PrintLine(String.valueOf(String.valueOf(new StringBuffer(" (").append(FormatReal(this.CurrentFrankWolfe.NewSolution[1], 5, 0, 1)).append(",").append(FormatReal(this.CurrentFrankWolfe.NewSolution[2], 5, 0, 1)).append(")"))));
/* 2242 */           break;
/*      */         case 3: 
/* 2244 */           Print(String.valueOf(String.valueOf(new StringBuffer("(").append(FormatReal(this.CurrentFrankWolfe.Solution[1], 4, 0, 1)).append(",").append(FormatReal(this.CurrentFrankWolfe.Solution[2], 4, 0, 1)).append(",").append(FormatReal(this.CurrentFrankWolfe.Solution[3], 4, 0, 1)).append(")|"))));
/* 2245 */           doWork(op);
/* 2246 */           for (int i = 1; i <= 3; i++)
/* 2247 */             Print(String.valueOf(String.valueOf(FormatReal(this.CurrentFrankWolfe.ObjectiveFunction[i], 4, 0, 1))).concat("|"));
/* 2248 */           Print(String.valueOf(String.valueOf(new StringBuffer("(").append(FormatReal(this.CurrentFrankWolfe.FWLPSolution[1], 5, 0, 1)).append(",").append(FormatReal(this.CurrentFrankWolfe.FWLPSolution[2], 5, 0, 1)).append(",").append(FormatReal(this.CurrentFrankWolfe.FWLPSolution[3], 5, 0, 1)).append(")|"))));
/* 2249 */           Print(String.valueOf(String.valueOf(FormatReal(this.CurrentFrankWolfe.t, 4, 0, 1))).concat("|"));
/* 2250 */           PrintLine(String.valueOf(String.valueOf(new StringBuffer("(").append(FormatReal(this.CurrentFrankWolfe.NewSolution[1], 5, 0, 1)).append(",").append(FormatReal(this.CurrentFrankWolfe.NewSolution[2], 5, 0, 1)).append(",").append(FormatReal(this.CurrentFrankWolfe.NewSolution[3], 5, 0, 1)).append(")"))));
/*      */         }
/*      */         
/* 2253 */         k++;
/* 2254 */         break;
/*      */       default: 
/* 2256 */         doWork(op);
/* 2257 */         break;
/*      */       }
/* 2259 */       iStep++;
/* 2260 */       if ((iStep == n) && (n > 1)) {
/* 2261 */         Skip(2);
/* 2262 */         Print("Final solution: (");
/* 2263 */         for (int i = 1; i <= this.SimplexModel.NumVariables; i++) {
/* 2264 */           Print(FormatReal(this.CurrentFrankWolfe.Solution[i], 6, 0, 1));
/* 2265 */           if (i < this.SimplexModel.NumVariables) {
/* 2266 */             Print(",");
/*      */           } else {
/* 2268 */             PrintLine(").");
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void PrintNonlinearSUMT() {
/* 2276 */     int n = this.steps.size();
/* 2277 */     int iStep = 0;
/* 2278 */     IOROperation op = null;
/*      */     
/* 2280 */     PrintLine("Sequential Unconstrained Minimization Technique:");
/* 2281 */     SkipLine();
/*      */     
/* 2283 */     if (n == 0) return;
/* 2284 */     op = (IOROperation)this.steps.elementAt(0);
/* 2285 */     doWork(op);
/* 2286 */     PrintLine("Number of variables with lower bound constraints: ".concat(String.valueOf(String.valueOf(FormatInteger(this.SUMTModel.NumVariableswlb, 2, 0, 0)))));
/* 2287 */     SkipLine();
/* 2288 */     PrintLine("Number of variables without lower bound constraints: ".concat(String.valueOf(String.valueOf(FormatInteger(this.SUMTModel.NumVariableswolb, 2, 0, 0)))));
/* 2289 */     SkipLine();
/* 2290 */     PrintLine("Number of inequality constraints: ".concat(String.valueOf(String.valueOf(FormatInteger(this.SUMTModel.NumInequalityConstraints, 2, 0, 0)))));
/* 2291 */     SkipLine();
/* 2292 */     PrintLine("Number of equality constraints: ".concat(String.valueOf(String.valueOf(FormatInteger(this.SUMTModel.NumEqualityConstraints, 2, 0, 0)))));
/* 2293 */     Skip(2);
/*      */     
/* 2295 */     if (n == 1) return;
/* 2296 */     op = (IOROperation)this.steps.elementAt(1);
/* 2297 */     doWork(op);
/* 2298 */     Print("P(X, r) = f(X) ");
/* 2299 */     for (int i = 1; i <= this.SUMTModel.NumInequalityConstraints; i++)
/* 2300 */       Print("-   r   ");
/* 2301 */     for (i = 1; i <= this.SUMTModel.NumEqualityConstraints; i++)
/* 2302 */       Print(String.valueOf(String.valueOf(new StringBuffer("- (B").append(FormatInteger(i + this.SUMTModel.NumInequalityConstraints, 1, 0, 0)).append(")^2 "))));
/* 2303 */     for (i = 1; i <= this.SUMTModel.NumVariableswlb; i++)
/* 2304 */       Print("-   r   ");
/* 2305 */     SkipLine();
/* 2306 */     Print("               ");
/* 2307 */     for (i = 1; i <= this.SUMTModel.NumInequalityConstraints; i++)
/* 2308 */       Print("  ----- ");
/* 2309 */     for (i = 1; i <= this.SUMTModel.NumEqualityConstraints; i++)
/* 2310 */       Print("  ------- ");
/* 2311 */     for (i = 1; i <= this.SUMTModel.NumVariableswlb; i++)
/* 2312 */       Print("  ----- ");
/* 2313 */     SkipLine();
/* 2314 */     Print("               ");
/* 2315 */     for (i = 1; i <= this.SUMTModel.NumInequalityConstraints; i++)
/* 2316 */       Print(String.valueOf(String.valueOf(new StringBuffer("  B").append(FormatInteger(i, 1, 0, 0)).append("(X) "))));
/* 2317 */     for (i = 1; i <= this.SUMTModel.NumEqualityConstraints; i++)
/* 2318 */       Print("  r^(1/2) ");
/* 2319 */     for (i = 1; i <= this.SUMTModel.NumVariableswlb; i++)
/* 2320 */       Print(String.valueOf(String.valueOf(new StringBuffer("  L").append(FormatInteger(i, 1, 0, 0)).append("(X) "))));
/* 2321 */     Skip(2);
/* 2322 */     PrintLine("where");
/* 2323 */     SkipLine();
/* 2324 */     Print("   f(X) = ");
/* 2325 */     PrintPolynomial(this.SUMTModel.f);
/* 2326 */     Skip(2);
/* 2327 */     for (i = 1; i <= this.SUMTModel.NumInequalityConstraints; i++) {
/* 2328 */       Print(String.valueOf(String.valueOf(new StringBuffer("  B").append(FormatInteger(i, 1, 0, 0)).append("(X) = "))));
/* 2329 */       PrintPolynomial(this.SUMTModel.B[i]);
/* 2330 */       SkipLine();
/*      */     }
/* 2332 */     for (i = 1; i <= this.SUMTModel.NumEqualityConstraints; i++) {
/* 2333 */       Print(String.valueOf(String.valueOf(new StringBuffer("  B").append(FormatInteger(i + this.SUMTModel.NumInequalityConstraints, 1, 0, 0)).append("(X) = "))));
/* 2334 */       PrintPolynomial(this.SUMTModel.B[(i + this.SUMTModel.NumInequalityConstraints)]);
/* 2335 */       SkipLine();
/*      */     }
/* 2337 */     for (i = 1; i <= this.SUMTModel.NumVariableswlb; i++) {
/* 2338 */       Print(String.valueOf(String.valueOf(new StringBuffer("  L").append(FormatInteger(i, 1, 0, 0)).append("(X) = "))));
/* 2339 */       PrintPolynomial(this.SUMTModel.L[i]);
/* 2340 */       SkipLine();
/*      */     }
/* 2342 */     SkipLine();
/* 2343 */     PrintLine("Sequential Unconstrained Minimization Technique Solution:");
/* 2344 */     SkipLine();
/* 2345 */     Print("k  |       r    | ");
/* 2346 */     for (i = 1; i <= this.SUMTModel.NumVariables; i++)
/* 2347 */       Print(String.valueOf(String.valueOf(new StringBuffer("  X").append(FormatInteger(i, 1, 0, 0)).append("  | "))));
/* 2348 */     PrintLine("f(X)");
/* 2349 */     Print("___|____________|_");
/* 2350 */     for (i = 1; i <= this.SUMTModel.NumVariables; i++)
/* 2351 */       Print("______|_");
/* 2352 */     PrintLine("_______");
/*      */     
/* 2354 */     int It = 0;
/* 2355 */     iStep = 2;
/* 2356 */     Print(String.valueOf(String.valueOf(FormatInteger(It, 3, 0, 0))).concat("| "));
/* 2357 */     Print("           | ");
/* 2358 */     for (i = 1; i <= this.SUMTModel.NumVariables; i++)
/* 2359 */       Print(String.valueOf(String.valueOf(FormatReal(this.CurrentSUMT.InitialSolution[i], 5, 0, 0))).concat(" | "));
/* 2360 */     PrintLine(FormatReal(EvaluatePolynomial(this.SUMTModel.f, this.CurrentSUMT.InitialSolution), 6, 0, 1));
/*      */     
/* 2362 */     It = 1;
/* 2363 */     Print(String.valueOf(String.valueOf(FormatInteger(It, 3, 0, 0))).concat("| "));
/* 2364 */     Print(String.valueOf(String.valueOf(FormatReal(this.CurrentSUMT.r, 10, 0, 1))).concat(" | "));
/* 2365 */     for (i = 1; i <= this.SUMTModel.NumVariables; i++)
/* 2366 */       Print(String.valueOf(String.valueOf(FormatReal(this.CurrentSUMT.FinalSolution[i], 5, 0, 0))).concat(" | "));
/* 2367 */     PrintLine(FormatReal(EvaluatePolynomial(this.SUMTModel.f, this.CurrentSUMT.FinalSolution), 6, 0, 1));
/*      */     
/* 2369 */     It = 2;
/* 2370 */     while (iStep < n) {
/* 2371 */       op = (IOROperation)this.steps.elementAt(iStep);
/* 2372 */       switch (op.operation_code) {
/*      */       case 50504: 
/* 2374 */         doWork(op);
/* 2375 */         Print(String.valueOf(String.valueOf(FormatInteger(It, 3, 0, 0))).concat("| "));
/* 2376 */         Print(String.valueOf(String.valueOf(FormatReal(this.CurrentSUMT.r, 10, 0, 1))).concat(" | "));
/* 2377 */         for (i = 1; i <= this.SUMTModel.NumVariables; i++)
/* 2378 */           Print(String.valueOf(String.valueOf(FormatReal(this.CurrentSUMT.FinalSolution[i], 5, 0, 0))).concat(" | "));
/* 2379 */         PrintLine(FormatReal(EvaluatePolynomial(this.SUMTModel.f, this.CurrentSUMT.FinalSolution), 6, 0, 1));
/* 2380 */         It += 1;
/* 2381 */         break;
/*      */       default: 
/* 2383 */         doWork(op);
/*      */       }
/*      */       
/* 2386 */       iStep++;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORNLPSolver.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */