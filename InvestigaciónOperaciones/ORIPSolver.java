/*      */ import java.io.PrintStream;
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
/*      */ public class ORIPSolver
/*      */   extends ORLPSolver
/*      */ {
/*   27 */   private boolean IncumbentExists = false;
/*   28 */   private double[] Incumbent = new double[63];
/*   29 */   private double IncumbentZ = 0.0D;
/*      */   
/*   31 */   private ORSolverBase.DakinsType CurrentDakins = null;
/*   32 */   private ORSolverBase.DakinsType FirstDakins = null;
/*   33 */   private ORSolverBase.DakinsPivotType CurrentDakinsPivot = null;
/*   34 */   private ORSolverBase.DakinsPivotType FirstDakinsPivot = null;
/*   35 */   private ORSolverBase.DakinsType CurrentBalas = null;
/*   36 */   private ORSolverBase.DakinsType FirstBalas = null;
/*   37 */   private ORSolverBase.DakinsPivotType CurrentBalasPivot = null;
/*   38 */   private ORSolverBase.DakinsPivotType FirstBalasPivot = null;
/*      */   
/*   40 */   private ORSolverBase.SimplexModelType SimplexModel_bak = null;
/*      */   
/*      */ 
/*      */   public ORIPSolver()
/*      */   {
/*   45 */     this.ApplicationNumber = 5;
/*   46 */     this.NumBinaryVariables = 1;
/*      */     
/*      */ 
/*   49 */     this.NumIntegerVariables = 0;
/*   50 */     this.NumRealVariables = 0;
/*      */     
/*   52 */     InitializeSimplexModel();
/*   53 */     this.SimplexModel.NumConstraints = 1;
/*   54 */     this.NumOriginalConstraints1 = 1;
/*      */     
/*   56 */     this.SimplexModel.NumVariables = 1;
/*      */   }
/*      */   
/*      */   private void my_InitSimplexModel()
/*      */   {
/*   61 */     this.SimplexModel = new ORSolverBase.SimplexModelType(this);
/*      */     
/*   63 */     this.SimplexModel.NumConstraints = 4;
/*   64 */     this.SimplexModel.ObjectiveFunction[1] = 4.0D;
/*   65 */     this.SimplexModel.ObjectiveFunction[2] = -2.0D;
/*   66 */     this.SimplexModel.ObjectiveFunction[3] = 7.0D;
/*   67 */     this.SimplexModel.ObjectiveFunction[4] = -1.0D;
/*   68 */     this.SimplexModel.Objective = 0;
/*      */     
/*   70 */     this.SimplexModel.NumVariables = 4;
/*   71 */     this.NumBinaryVariables = 0;
/*   72 */     this.NumIntegerVariables = 3;
/*   73 */     this.NumRealVariables = 1;
/*      */     
/*   75 */     this.SimplexModel.A[1][1] = 1.0D;
/*   76 */     this.SimplexModel.A[1][2] = 0.0D;
/*   77 */     this.SimplexModel.A[1][3] = 5.0D;
/*   78 */     this.SimplexModel.A[1][4] = 0.0D;
/*   79 */     this.SimplexModel.A[2][1] = 1.0D;
/*   80 */     this.SimplexModel.A[2][2] = 1.0D;
/*   81 */     this.SimplexModel.A[2][3] = -1.0D;
/*   82 */     this.SimplexModel.A[2][4] = 0.0D;
/*   83 */     this.SimplexModel.A[3][1] = 6.0D;
/*   84 */     this.SimplexModel.A[3][2] = -5.0D;
/*   85 */     this.SimplexModel.A[3][3] = 0.0D;
/*   86 */     this.SimplexModel.A[3][4] = 0.0D;
/*   87 */     this.SimplexModel.A[4][1] = -1.0D;
/*   88 */     this.SimplexModel.A[4][2] = 0.0D;
/*   89 */     this.SimplexModel.A[4][3] = 2.0D;
/*   90 */     this.SimplexModel.A[4][4] = -2.0D;
/*      */     
/*   92 */     this.SimplexModel.Constraint[1] = 0;
/*   93 */     this.SimplexModel.Constraint[2] = 0;
/*   94 */     this.SimplexModel.Constraint[3] = 0;
/*   95 */     this.SimplexModel.Constraint[4] = 0;
/*      */     
/*   97 */     this.SimplexModel.RightHandSide[1] = 10.0D;
/*   98 */     this.SimplexModel.RightHandSide[2] = 1.0D;
/*   99 */     this.SimplexModel.RightHandSide[3] = 0.0D;
/*  100 */     this.SimplexModel.RightHandSide[4] = 3.0D;
/*      */     
/*  102 */     for (int i = 1; i <= 4; i++) {
/*  103 */       this.SimplexModel.Nonnegativity[i] = true;
/*      */     }
/*      */   }
/*      */   
/*      */   private void DoAutomaticBIPCommand()
/*      */   {
/*  109 */     this.CommandNumber = 2;
/*  110 */     InitializeBalasList();
/*  111 */     InitializeDakinsList();
/*  112 */     SolveMIP();
/*      */   }
/*      */   
/*      */   private void InitializeBalasList()
/*      */   {
/*  117 */     this.FirstBalas = null;
/*  118 */     this.CurrentBalas = null;
/*  119 */     this.FirstBalasPivot = null;
/*  120 */     this.CurrentBalasPivot = null;
/*      */   }
/*      */   
/*      */   private void InitializeDakinsList()
/*      */   {
/*  125 */     this.FirstDakins = null;
/*  126 */     this.CurrentDakins = null;
/*  127 */     this.FirstDakinsPivot = null;
/*  128 */     this.CurrentDakinsPivot = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void SolveMIP()
/*      */   {
/*  135 */     int Branch = 0;
/*  136 */     CreateFirstDakins();
/*  137 */     FathomMIP();
/*  138 */     while (BranchMIP())
/*      */     {
/*  140 */       Branch += 1;
/*  141 */       CreateNextDakins();
/*  142 */       this.CurrentDakins = this.CurrentDakins.RightChild;
/*  143 */       BoundMIP();
/*  144 */       FathomMIP();
/*  145 */       this.CurrentDakins = this.CurrentDakins.Parent.LeftChild;
/*  146 */       BoundMIP();
/*  147 */       FathomMIP();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void Walk(ORSolverBase.DakinsType Node)
/*      */   {
/*  154 */     if (Node != null)
/*      */     {
/*  156 */       if ((Node.RightChild == null) && (Node.LeftChild == null) && (!Node.Fathomed))
/*      */       {
/*  158 */         if (this.IncumbentExists)
/*      */         {
/*  160 */           if ((this.SimplexModel.Objective == 0) && (Node.Z <= this.IncumbentZ))
/*  161 */             Node.Fathomed = true;
/*  162 */           if ((this.SimplexModel.Objective == 1) && (Node.Z >= this.IncumbentZ))
/*  163 */             Node.Fathomed = true;
/*      */         }
/*  165 */         if (!Node.Fathomed)
/*      */         {
/*  167 */           if (this.CurrentDakins == null)
/*      */           {
/*  169 */             this.CurrentDakins = Node;
/*      */           }
/*      */           else
/*      */           {
/*  173 */             if ((this.SimplexModel.Objective == 0) && (this.CurrentDakins.Z < Node.Z))
/*  174 */               this.CurrentDakins = Node;
/*  175 */             if ((this.SimplexModel.Objective == 1) && (this.CurrentDakins.Z > Node.Z))
/*  176 */               this.CurrentDakins = Node;
/*      */           }
/*      */         }
/*      */       }
/*  180 */       Walk(Node.LeftChild);
/*  181 */       Walk(Node.RightChild);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean BranchMIP()
/*      */   {
/*  190 */     this.CurrentDakins = null;
/*  191 */     ORSolverBase.DakinsType TempDakins = this.FirstDakins;
/*  192 */     Walk(TempDakins);
/*  193 */     if (this.CurrentDakins != null)
/*      */     {
/*  195 */       for (int i = this.NumBinaryVariables + this.NumIntegerVariables; i >= 1; i--)
/*      */       {
/*  197 */         if (Math.abs(this.CurrentDakins.Solution[i] - Math.round(this.CurrentDakins.Solution[i])) > 1.0E-5D)
/*  198 */           this.CurrentDakins.Variable = i;
/*      */       }
/*  200 */       this.CurrentDakins.GreaterThanWhat = (this.CurrentDakins.Solution[this.CurrentDakins.Variable] + 1);
/*  201 */       this.CurrentDakins.LessThanWhat = this.CurrentDakins.Solution[this.CurrentDakins.Variable];
/*  202 */       return true;
/*      */     }
/*      */     
/*      */ 
/*  206 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void BoundMIP()
/*      */   {
/*  215 */     ORSolverBase.DakinsType TempDakins = this.CurrentDakins;
/*  216 */     this.SimplexModel.NumConstraints = this.NumOriginalConstraints2;
/*  217 */     for (int i = this.NumOriginalConstraints1 + 1; i <= this.NumOriginalConstraints2; i++)
/*      */     {
/*  219 */       this.SimplexModel.Constraint[i] = 0;
/*  220 */       this.SimplexModel.RightHandSide[i] = 1.0D;
/*      */     }
/*      */     do {
/*  223 */       if (TempDakins.Parent.Variable <= this.NumBinaryVariables)
/*      */       {
/*  225 */         if (TempDakins == TempDakins.Parent.LeftChild)
/*      */         {
/*  227 */           this.SimplexModel.Constraint[(this.NumOriginalConstraints1 + TempDakins.Parent.Variable)] = 1;
/*  228 */           this.SimplexModel.RightHandSide[(this.NumOriginalConstraints1 + TempDakins.Parent.Variable)] = 0.0D;
/*      */         }
/*      */         else
/*      */         {
/*  232 */           this.SimplexModel.Constraint[(this.NumOriginalConstraints1 + TempDakins.Parent.Variable)] = 1;
/*  233 */           this.SimplexModel.RightHandSide[(this.NumOriginalConstraints1 + TempDakins.Parent.Variable)] = 1.0D;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  238 */         this.SimplexModel.NumConstraints += 1;
/*  239 */         for (i = 1; i <= this.SimplexModel.NumVariables; i++)
/*  240 */           this.SimplexModel.A[this.SimplexModel.NumConstraints][i] = 0.0D;
/*  241 */         this.SimplexModel.A[this.SimplexModel.NumConstraints][TempDakins.Parent.Variable] = 1.0D;
/*  242 */         if (TempDakins == TempDakins.Parent.LeftChild)
/*      */         {
/*  244 */           this.SimplexModel.Constraint[this.SimplexModel.NumConstraints] = 0;
/*  245 */           this.SimplexModel.RightHandSide[this.SimplexModel.NumConstraints] = TempDakins.Parent.LessThanWhat;
/*      */         }
/*      */         else
/*      */         {
/*  249 */           this.SimplexModel.Constraint[this.SimplexModel.NumConstraints] = 2;
/*  250 */           this.SimplexModel.RightHandSide[this.SimplexModel.NumConstraints] = TempDakins.Parent.GreaterThanWhat;
/*      */         }
/*      */       }
/*  253 */       TempDakins = TempDakins.Parent;
/*  254 */     } while (TempDakins.Parent != null);
/*  255 */     RevisedSimplexMethod(false);
/*  256 */     for (i = 1; i <= this.SimplexModel.NumVariables; i++)
/*  257 */       this.CurrentDakins.Solution[i] = this.LPSolution.Solution[i];
/*  258 */     this.CurrentDakins.Feasible = this.CurrentSimplexTableau.Feasible;
/*  259 */     this.CurrentDakins.Z = this.CurrentSimplexTableau.Z;
/*  260 */     if (this.SimplexModel.Objective == 1) {
/*  261 */       this.CurrentDakins.Z = (-this.CurrentDakins.Z);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void FathomMIP()
/*      */   {
/*  269 */     this.CurrentDakins.Fathomed = (!this.CurrentDakins.Feasible);
/*  270 */     if (!this.CurrentDakins.Fathomed)
/*      */     {
/*  272 */       if (this.CurrentSimplexTableau.Unbounded)
/*  273 */         this.CurrentDakins.Fathomed = true;
/*      */     }
/*  275 */     if (!this.CurrentDakins.Fathomed)
/*      */     {
/*  277 */       boolean Temp = true;
/*  278 */       for (int i = 1; i <= this.NumBinaryVariables + this.NumIntegerVariables; i++)
/*      */       {
/*  280 */         if (Math.abs(this.CurrentDakins.Solution[i] - Math.round(this.CurrentDakins.Solution[i])) > 1.0E-7D)
/*  281 */           Temp = false;
/*      */       }
/*  283 */       if (Temp)
/*      */       {
/*  285 */         this.CurrentDakins.Fathomed = true;
/*  286 */         if (((this.SimplexModel.Objective == 1) && ((this.CurrentDakins.Z < this.IncumbentZ) || (!this.IncumbentExists))) || ((this.SimplexModel.Objective == 0) && ((this.CurrentDakins.Z > this.IncumbentZ) || (!this.IncumbentExists))))
/*      */         {
/*  288 */           this.IncumbentExists = true;
/*  289 */           for (i = 1; i <= this.SimplexModel.NumVariables; i++)
/*  290 */             this.Incumbent[i] = this.CurrentDakins.Solution[i];
/*  291 */           this.IncumbentZ = this.CurrentDakins.Z;
/*      */         }
/*      */       }
/*      */     }
/*  295 */     if (!this.CurrentDakins.Fathomed)
/*      */     {
/*  297 */       if ((this.SimplexModel.Objective == 1) && (this.CurrentDakins.Z >= this.IncumbentZ) && (this.IncumbentExists))
/*  298 */         this.CurrentDakins.Fathomed = true;
/*  299 */       if ((this.SimplexModel.Objective == 0) && (this.CurrentDakins.Z <= this.IncumbentZ) && (this.IncumbentExists)) {
/*  300 */         this.CurrentDakins.Fathomed = true;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void CreateFirstDakins()
/*      */   {
/*  308 */     this.SimplexModel.NumConstraints = this.NumOriginalConstraints1;
/*  309 */     this.IncumbentExists = false;
/*  310 */     for (int i = 1; i <= 30; i++)
/*  311 */       this.Incumbent[i] = 0.0D;
/*  312 */     this.IncumbentZ = 0.0D;
/*  313 */     for (i = 1; i <= this.NumBinaryVariables; i++)
/*      */     {
/*  315 */       this.SimplexModel.NumConstraints += 1;
/*  316 */       for (int j = 1; j <= this.SimplexModel.NumVariables; j++)
/*      */       {
/*  318 */         if (i == j) {
/*  319 */           this.SimplexModel.A[this.SimplexModel.NumConstraints][j] = 1.0D;
/*      */         } else
/*  321 */           this.SimplexModel.A[this.SimplexModel.NumConstraints][j] = 0.0D;
/*      */       }
/*  323 */       this.SimplexModel.RightHandSide[this.SimplexModel.NumConstraints] = 1.0D;
/*  324 */       this.SimplexModel.Constraint[this.SimplexModel.NumConstraints] = 0;
/*      */     }
/*  326 */     RevisedSimplexMethod(false);
/*  327 */     this.FirstDakins = new ORSolverBase.DakinsType(this);
/*  328 */     this.CurrentDakins = this.FirstDakins;
/*  329 */     if (this.SimplexModel.Objective == 1) {
/*  330 */       this.FirstDakins.Z = (-this.CurrentSimplexTableau.Z);
/*      */     } else
/*  332 */       this.FirstDakins.Z = this.CurrentSimplexTableau.Z;
/*  333 */     this.FirstDakins.Variable = 1;
/*  334 */     this.FirstDakins.LessThanWhat = 0.0D;
/*  335 */     this.FirstDakins.GreaterThanWhat = 0.0D;
/*  336 */     this.FirstDakins.IncumbentNumber = 0;
/*  337 */     this.FirstDakins.Parent = null;
/*  338 */     this.FirstDakins.RightChild = null;
/*  339 */     this.FirstDakins.LeftChild = null;
/*  340 */     for (i = 1; i <= this.SimplexModel.NumVariables; i++)
/*  341 */       this.FirstDakins.Solution[i] = this.LPSolution.Solution[i];
/*  342 */     this.FirstDakins.Feasible = this.CurrentSimplexTableau.Feasible;
/*  343 */     this.FirstDakins.Fathomed = (!this.FirstDakins.Feasible);
/*      */     
/*  345 */     this.FirstDakinsPivot = new ORSolverBase.DakinsPivotType(this);
/*  346 */     this.FirstDakinsPivot.Node = this.FirstDakins;
/*  347 */     this.FirstDakinsPivot.Next = null;
/*  348 */     this.FirstDakinsPivot.Last = null;
/*  349 */     this.CurrentDakinsPivot = this.FirstDakinsPivot;
/*  350 */     this.NumOriginalConstraints2 = this.SimplexModel.NumConstraints;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void CreateNextDakins()
/*      */   {
/*  358 */     this.CurrentDakins.RightChild = null;
/*  359 */     this.CurrentDakins.LeftChild = null;
/*  360 */     ORSolverBase.DakinsType TempDakins = new ORSolverBase.DakinsType(this);
/*  361 */     TempDakins.Parent = this.CurrentDakins;
/*  362 */     TempDakins.LeftChild = null;
/*  363 */     TempDakins.RightChild = null;
/*  364 */     this.CurrentDakins.RightChild = TempDakins;
/*  365 */     TempDakins = new ORSolverBase.DakinsType(this);
/*  366 */     TempDakins.Parent = this.CurrentDakins;
/*  367 */     TempDakins.LeftChild = null;
/*  368 */     TempDakins.RightChild = null;
/*  369 */     this.CurrentDakins.LeftChild = TempDakins;
/*      */     
/*  371 */     this.CurrentDakins.RightChild.Variable = 1;
/*  372 */     this.CurrentDakins.RightChild.LessThanWhat = 0.0D;
/*  373 */     this.CurrentDakins.RightChild.GreaterThanWhat = 0.0D;
/*  374 */     this.CurrentDakins.RightChild.IncumbentNumber = 0;
/*  375 */     this.CurrentDakins.RightChild.Fathomed = false;
/*      */     
/*  377 */     this.CurrentDakins.LeftChild.Variable = 1;
/*  378 */     this.CurrentDakins.LeftChild.LessThanWhat = 0.0D;
/*  379 */     this.CurrentDakins.LeftChild.GreaterThanWhat = 0.0D;
/*  380 */     this.CurrentDakins.LeftChild.IncumbentNumber = 0;
/*  381 */     this.CurrentDakins.LeftChild.Fathomed = false;
/*      */     
/*  383 */     ORSolverBase.DakinsPivotType TempDakinsPivot = new ORSolverBase.DakinsPivotType(this);
/*  384 */     TempDakinsPivot.Node = this.CurrentDakins;
/*  385 */     TempDakinsPivot.Next = null;
/*  386 */     TempDakinsPivot.Last = this.CurrentDakinsPivot;
/*      */     
/*  388 */     this.CurrentDakinsPivot.Next = TempDakinsPivot;
/*  389 */     this.CurrentDakinsPivot = TempDakinsPivot;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void OutputResult()
/*      */   {
/*  397 */     if (!this.IncumbentExists) {
/*  398 */       System.out.print("\nNo Feasible Solution!\n");
/*      */     }
/*      */     else {
/*  401 */       System.out.print("\n");
/*  402 */       for (int i = 1; i <= this.SimplexModel.NumVariables; i++)
/*      */       {
/*  404 */         String s = String.valueOf(String.valueOf(new StringBuffer("X").append(i).append("  =  ")));
/*  405 */         System.out.print(ConvertStringToFixString(s, 14, 2));
/*  406 */         System.out.print(ConvertDoubleToFixString(this.Incumbent[i], 10, 6, 1, false));
/*  407 */         System.out.print("\n");
/*      */       }
/*      */     }
/*  410 */     System.out.print(ConvertStringToFixString("Z   =  ", 14, 2));
/*  411 */     System.out.print(ConvertDoubleToFixString(this.IncumbentZ, 10, 6, 1, false));
/*  412 */     System.out.print("\n");
/*      */   }
/*      */   
/*      */ 
/*      */   private int MaxIncumbency(ORSolverBase.DakinsType Temp)
/*      */   {
/*      */     int Max;
/*      */     int Max;
/*  420 */     if (Temp == null) {
/*  421 */       Max = 0; } else { int Max;
/*  422 */       if (Temp.RightChild == null) {
/*  423 */         Max = Temp.IncumbentNumber;
/*      */       } else
/*  425 */         Max = Math.max(MaxIncumbency(Temp.LeftChild), MaxIncumbency(Temp.RightChild)); }
/*  426 */     return Max;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void ReviseBalasIncumbent()
/*      */   {
/*  434 */     int Max = MaxIncumbency(this.FirstBalas);
/*  435 */     if (Max != 0) {
/*  436 */       this.IncumbentExists = true;
/*  437 */       ORSolverBase.DakinsType Temp = WhichIncumbent(Max);
/*  438 */       this.IncumbentZ = Temp.Z;
/*  439 */       for (int i = 1; i <= this.SimplexModel.NumVariables; i++) {
/*  440 */         this.Incumbent[i] = Math.round(Temp.Solution[i]);
/*      */       }
/*      */     }
/*  443 */     this.IncumbentExists = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void ReviseDakinsIncumbent()
/*      */   {
/*  451 */     int Max = MaxIncumbency(this.FirstDakins);
/*  452 */     if (Max != 0) {
/*  453 */       this.IncumbentExists = true;
/*  454 */       ORSolverBase.DakinsType Temp = WhichIncumbent(Max);
/*  455 */       this.IncumbentZ = Temp.Z;
/*  456 */       for (int i = 1; i <= this.SimplexModel.NumVariables; i++) {
/*  457 */         this.Incumbent[i] = Temp.Solution[i];
/*      */       }
/*      */     }
/*  460 */     this.IncumbentExists = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private ORSolverBase.DakinsType WhichIncumbent(int Max)
/*      */   {
/*  468 */     ORSolverBase.DakinsType Temp = null;
/*  469 */     ORSolverBase.DakinsPivotType TempPP; ORSolverBase.DakinsPivotType TempPP; if (this.CommandNumber == 3) {
/*  470 */       TempPP = this.FirstBalasPivot;
/*      */     } else
/*  472 */       TempPP = this.FirstDakinsPivot;
/*  473 */     while (TempPP != null) {
/*  474 */       if (TempPP.Node.IncumbentNumber == Max)
/*  475 */         Temp = TempPP.Node;
/*  476 */       if (TempPP.Node.RightChild != null)
/*      */       {
/*  478 */         if (TempPP.Node.RightChild.IncumbentNumber == Max)
/*  479 */           Temp = TempPP.Node.RightChild;
/*  480 */         if (TempPP.Node.LeftChild.IncumbentNumber == Max)
/*  481 */           Temp = TempPP.Node.LeftChild;
/*      */       }
/*  483 */       TempPP = TempPP.Next;
/*      */     }
/*  485 */     return Temp;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void CreateNextBalas()
/*      */   {
/*  493 */     this.CurrentBalas.RightChild = null;
/*  494 */     this.CurrentBalas.LeftChild = null;
/*  495 */     ORSolverBase.DakinsType TempBalas = new ORSolverBase.DakinsType(this);
/*  496 */     TempBalas.Parent = this.CurrentBalas;
/*  497 */     TempBalas.LeftChild = null;
/*  498 */     TempBalas.RightChild = null;
/*  499 */     this.CurrentBalas.RightChild = TempBalas;
/*  500 */     TempBalas = new ORSolverBase.DakinsType(this);
/*  501 */     TempBalas.Parent = this.CurrentBalas;
/*  502 */     TempBalas.LeftChild = null;
/*  503 */     TempBalas.RightChild = null;
/*  504 */     this.CurrentBalas.LeftChild = TempBalas;
/*      */     
/*  506 */     this.CurrentBalas.RightChild.Variable = 1;
/*  507 */     this.CurrentBalas.RightChild.LessThanWhat = 0.0D;
/*  508 */     this.CurrentBalas.RightChild.GreaterThanWhat = 0.0D;
/*  509 */     this.CurrentBalas.RightChild.IncumbentNumber = 0;
/*  510 */     this.CurrentBalas.RightChild.Fathomed = false;
/*      */     
/*  512 */     this.CurrentBalas.LeftChild.Variable = 1;
/*  513 */     this.CurrentBalas.LeftChild.LessThanWhat = 0.0D;
/*  514 */     this.CurrentBalas.LeftChild.GreaterThanWhat = 0.0D;
/*  515 */     this.CurrentBalas.LeftChild.IncumbentNumber = 0;
/*  516 */     this.CurrentBalas.LeftChild.Fathomed = false;
/*      */     
/*  518 */     ORSolverBase.DakinsPivotType TempBalasPivot = new ORSolverBase.DakinsPivotType(this);
/*  519 */     TempBalasPivot.Node = this.CurrentBalas;
/*  520 */     TempBalasPivot.Next = null;
/*  521 */     TempBalasPivot.Last = this.CurrentBalasPivot;
/*  522 */     this.CurrentBalasPivot.Next = TempBalasPivot;
/*  523 */     this.CurrentBalasPivot = TempBalasPivot;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void CreateFirstBalas()
/*      */   {
/*  530 */     this.SimplexModel.NumConstraints = this.NumOriginalConstraints1;
/*  531 */     this.IncumbentExists = false;
/*  532 */     for (int i = 1; i <= 30; i++)
/*  533 */       this.Incumbent[i] = 0.0D;
/*  534 */     this.IncumbentZ = 0.0D;
/*  535 */     for (i = 1; i <= this.SimplexModel.NumVariables; i++)
/*      */     {
/*  537 */       this.SimplexModel.NumConstraints += 1;
/*  538 */       for (int j = 1; j <= this.SimplexModel.NumVariables; j++)
/*      */       {
/*  540 */         if (i == j) {
/*  541 */           this.SimplexModel.A[this.SimplexModel.NumConstraints][j] = 1.0D;
/*      */         } else
/*  543 */           this.SimplexModel.A[this.SimplexModel.NumConstraints][j] = 0.0D;
/*      */       }
/*  545 */       this.SimplexModel.RightHandSide[this.SimplexModel.NumConstraints] = 1.0D;
/*  546 */       this.SimplexModel.Constraint[this.SimplexModel.NumConstraints] = 0;
/*      */     }
/*  548 */     RevisedSimplexMethod(false);
/*  549 */     this.FirstBalas = new ORSolverBase.DakinsType(this);
/*  550 */     this.CurrentBalas = this.FirstBalas;
/*      */     
/*  552 */     if (this.SimplexModel.Objective == 1) {
/*  553 */       this.FirstBalas.Z = (-this.CurrentSimplexTableau.Z);
/*      */     } else
/*  555 */       this.FirstBalas.Z = this.CurrentSimplexTableau.Z;
/*  556 */     this.FirstBalas.Variable = 1;
/*  557 */     this.FirstBalas.LessThanWhat = 0.0D;
/*  558 */     this.FirstBalas.GreaterThanWhat = 0.0D;
/*  559 */     this.FirstBalas.IncumbentNumber = 0;
/*  560 */     this.FirstBalas.Parent = null;
/*  561 */     this.FirstBalas.RightChild = null;
/*  562 */     this.FirstBalas.LeftChild = null;
/*  563 */     for (i = 1; i <= this.SimplexModel.NumVariables; i++)
/*  564 */       this.FirstBalas.Solution[i] = this.LPSolution.Solution[i];
/*  565 */     this.FirstBalas.Feasible = this.CurrentSimplexTableau.Feasible;
/*  566 */     this.FirstBalas.Fathomed = (!this.FirstBalas.Feasible);
/*      */     
/*  568 */     this.FirstBalasPivot = new ORSolverBase.DakinsPivotType(this);
/*      */     
/*  570 */     this.FirstBalasPivot.Node = this.FirstBalas;
/*  571 */     this.FirstBalasPivot.Next = null;
/*  572 */     this.FirstBalasPivot.Last = null;
/*      */     
/*  574 */     this.NumOriginalConstraints2 = this.SimplexModel.NumConstraints;
/*  575 */     this.CurrentBalasPivot = this.FirstBalasPivot;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean doWork(IOROperation op, IORIPProcessController.IPData data)
/*      */   {
/*  582 */     boolean success = doWork(op);
/*      */     
/*  584 */     if (success) {
/*  585 */       getData(data);
/*      */     }
/*  587 */     return success;
/*      */   }
/*      */   
/*      */   private ORSolverBase.DakinsType findDakins(ORSolverBase.DakinsType fd, int level, int offset) {
/*  591 */     ORSolverBase.DakinsType TempBalas = fd;
/*  592 */     boolean[] bLeftChild = new boolean[5];
/*      */     
/*  594 */     if (level == 0) return TempBalas;
/*  595 */     for (int i = level - 1; i >= 0; i--) {
/*  596 */       bLeftChild[i] = (offset % 2 == 0 ? 1 : false);
/*  597 */       offset >>= 1;
/*      */     }
/*  599 */     for (i = 0; i <= level - 1; i++) {
/*  600 */       if (TempBalas == null) return null;
/*  601 */       if (bLeftChild[i] != 0) {
/*  602 */         TempBalas = TempBalas.LeftChild;
/*      */       } else
/*  604 */         TempBalas = TempBalas.RightChild;
/*      */     }
/*  606 */     return TempBalas;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setBookmark()
/*      */   {
/*  612 */     this.SimplexModel_bak = ((ORSolverBase.SimplexModelType)this.SimplexModel.clone());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void reset()
/*      */   {
/*  619 */     this.SimplexModel = ((ORSolverBase.SimplexModelType)this.SimplexModel_bak.clone());
/*  620 */     this.IncumbentExists = false;
/*  621 */     if (this.NumBinaryVariables == this.SimplexModel.NumVariables) {
/*  622 */       this.FirstBalas = null;
/*  623 */       this.CurrentBalas = null;
/*  624 */       CreateFirstBalas();
/*      */     }
/*      */     else {
/*  627 */       this.FirstDakins = null;
/*  628 */       this.CurrentDakins = null;
/*  629 */       CreateFirstDakins();
/*      */     }
/*      */   }
/*      */   
/*      */   public void getData(IORIPProcessController.IPData data)
/*      */   {
/*  635 */     ORSolverBase.DakinsType TempBalas = null;
/*      */     
/*  637 */     int level = 0;int index = 0;
/*  638 */     int maxLevel = 4;
/*      */     
/*  640 */     if (data.objective_coefficient == null)
/*  641 */       data.objective_coefficient = new double[''];
/*  642 */     if (data.constrain_operator == null)
/*  643 */       data.constrain_operator = new int[51];
/*  644 */     if (data.constrain_coefficient == null) {
/*  645 */       data.constrain_coefficient = new double[51][''];
/*      */     }
/*  647 */     data.max_num_binary = 5;
/*  648 */     data.max_num_integer = 5;
/*  649 */     data.max_num_continuous = 5;
/*  650 */     data.max_num_constrain = 5;
/*      */     
/*  652 */     data.NumBinaryVariables = this.NumBinaryVariables;
/*  653 */     data.NumIntegerVariables = this.NumIntegerVariables;
/*  654 */     data.NumRealVariables = this.NumRealVariables;
/*  655 */     data.NumVariables = (data.NumBinaryVariables + data.NumIntegerVariables + data.NumRealVariables);
/*  656 */     data.NumConstraints = this.NumOriginalConstraints1;
/*  657 */     data.isBIP = (this.NumBinaryVariables == this.SimplexModel.NumVariables);
/*  658 */     ORSolverBase.DakinsType fd = data.isBIP ? this.FirstBalas : this.FirstDakins;
/*      */     
/*  660 */     data.objective_is_max = (this.SimplexModel.Objective == 0);
/*  661 */     for (int i = 1; i <= this.SimplexModel.NumVariables; i++) {
/*  662 */       data.objective_coefficient[i] = (-this.SimplexModel.ObjectiveFunction[i]);
/*      */     }
/*  664 */     for (i = 1; i <= this.SimplexModel.NumConstraints; i++) {
/*  665 */       data.constrain_operator[i] = this.SimplexModel.Constraint[i];
/*  666 */       data.constrain_coefficient[i][0] = this.SimplexModel.RightHandSide[i];
/*  667 */       for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/*  668 */         data.constrain_coefficient[i][j] = this.SimplexModel.A[i][j];
/*      */       }
/*      */     }
/*      */     
/*  672 */     data.IncumbentExists = this.IncumbentExists;
/*  673 */     arraycopy1(this.Incumbent, data.Incumbent);
/*  674 */     data.IncumbentZ = this.IncumbentZ;
/*      */     
/*  676 */     int maxNodesPerLevel = 1;
/*  677 */     for (level = 0; level <= 5; level++) {
/*  678 */       for (int offset = 0; offset < maxNodesPerLevel; offset++) {
/*  679 */         TempBalas = findDakins(fd, level, offset);
/*  680 */         if (TempBalas == null) {
/*  681 */           data.nodes[index].valid = false;
/*      */         }
/*      */         else {
/*  684 */           data.nodes[index].valid = true;
/*  685 */           data.nodes[index].feasible = TempBalas.Feasible;
/*  686 */           data.nodes[index].fathomed = TempBalas.Fathomed;
/*  687 */           data.nodes[index].Z = TempBalas.Z;
/*  688 */           arraycopy1(TempBalas.Solution, data.nodes[index].solution);
/*  689 */           if ((TempBalas.RightChild == null) && (TempBalas.LeftChild == null)) {
/*  690 */             data.nodes[index].leafNode = true;
/*      */           }
/*      */           else {
/*  693 */             data.nodes[index].leafNode = false;
/*  694 */             data.nodes[index].xSubscript = TempBalas.Variable;
/*  695 */             if (this.NumIntegerVariables == 0) {
/*  696 */               data.nodes[index].xLowerValue = 0;
/*  697 */               data.nodes[index].xUpperValue = 1;
/*      */             }
/*      */             else {
/*  700 */               data.nodes[index].xLowerValue = ((int)TempBalas.LessThanWhat);
/*  701 */               data.nodes[index].xUpperValue = ((int)TempBalas.GreaterThanWhat);
/*      */             }
/*      */           }
/*  704 */           data.maxLevel = level;
/*      */         }
/*  706 */         index++;
/*      */       }
/*  708 */       maxNodesPerLevel *= 2;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean doWork(IOROperation op)
/*      */   {
/*  718 */     Vector p = op.parameters;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  723 */     switch (op.operation_code)
/*      */     {
/*      */     case 40101: 
/*  726 */       this.SimplexModel = new ORSolverBase.SimplexModelType(this);
/*  727 */       for (int i = 1; i <= 150; i++)
/*  728 */         this.SimplexModel.Nonnegativity[i] = true;
/*  729 */       this.NumBinaryVariables = ((Integer)p.elementAt(0)).intValue();
/*  730 */       this.NumIntegerVariables = ((Integer)p.elementAt(1)).intValue();
/*  731 */       this.NumRealVariables = ((Integer)p.elementAt(2)).intValue();
/*  732 */       this.SimplexModel.NumVariables = (this.NumBinaryVariables + this.NumIntegerVariables + this.NumRealVariables);
/*  733 */       this.SimplexModel.NumConstraints = ((Integer)p.elementAt(3)).intValue();
/*  734 */       this.SimplexModel.Objective = (((Boolean)p.elementAt(4)).booleanValue() ? 0 : 1);
/*  735 */       double[] da = (double[])p.elementAt(5);
/*  736 */       double[][] daa = (double[][])p.elementAt(6);
/*  737 */       int[] ia = (int[])p.elementAt(7);
/*      */       
/*  739 */       for (i = 1; i <= this.SimplexModel.NumVariables; i++) {
/*  740 */         this.SimplexModel.ObjectiveFunction[i] = da[(i - 1)];
/*      */       }
/*  742 */       for (i = 1; i <= this.SimplexModel.NumConstraints; i++) {
/*  743 */         this.SimplexModel.RightHandSide[i] = daa[(i - 1)][0];
/*  744 */         this.SimplexModel.Constraint[i] = ia[(i - 1)];
/*  745 */         for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/*  746 */           this.SimplexModel.A[i][j] = daa[(i - 1)][j];
/*      */         }
/*      */       }
/*      */       
/*  750 */       for (i = 1; i <= this.SimplexModel.NumVariables; i++) {
/*  751 */         this.SimplexModel.Nonnegativity[i] = true;
/*      */       }
/*  753 */       this.SimplexModel_bak = ((ORSolverBase.SimplexModelType)this.SimplexModel.clone());
/*  754 */       this.NumOriginalConstraints1 = this.SimplexModel.NumConstraints;
/*  755 */       break;
/*      */     case 40102: 
/*  757 */       this.CommandNumber = 3;
/*      */       
/*  759 */       InitializeDakinsList();
/*  760 */       InitializeBalasList();
/*      */       
/*  762 */       CreateFirstBalas();
/*  763 */       break;
/*      */     case 40104: 
/*  765 */       int currentNodeLevel = ((Integer)p.elementAt(0)).intValue();
/*  766 */       int currentNodeOffset = ((Integer)p.elementAt(1)).intValue();
/*  767 */       this.CurrentBalas = findDakins(this.FirstBalas, currentNodeLevel, currentNodeOffset);
/*      */       
/*  769 */       boolean Correct = true;
/*  770 */       if (this.CurrentBalas == this.FirstBalas) {
/*  771 */         for (int i = 1; i <= this.SimplexModel.NumVariables; i++) {
/*  772 */           if (Math.abs(this.CurrentBalas.Solution[i] - Math.round(this.CurrentBalas.Solution[i])) > 1.0E-4D)
/*  773 */             Correct = false;
/*      */         }
/*      */       }
/*  776 */       if (Correct) {
/*  777 */         this.CurrentBalas.IncumbentNumber = (MaxIncumbency(this.FirstBalas) + 1);
/*  778 */         ReviseBalasIncumbent();
/*      */       }
/*      */       else {
/*  781 */         this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  782 */         return false;
/*      */       }
/*      */       break;
/*      */     case 40105: 
/*  786 */       int currentNodeLevel = ((Integer)p.elementAt(0)).intValue();
/*  787 */       int currentNodeOffset = ((Integer)p.elementAt(1)).intValue();
/*  788 */       this.CurrentBalas = findDakins(this.FirstBalas, currentNodeLevel, currentNodeOffset);
/*      */       
/*  790 */       if ((this.CurrentBalas.LeftChild == null) && (this.CurrentBalas.RightChild == null) && (this.CurrentBalas.Feasible))
/*      */       {
/*  792 */         boolean Correct = true;
/*  793 */         if (this.CurrentBalas == this.FirstBalas) {
/*  794 */           for (int i = 1; i <= this.SimplexModel.NumVariables; i++) {
/*  795 */             if (Math.abs(this.CurrentBalas.Solution[i] - Math.round(this.CurrentBalas.Solution[i])) > 1.0E-4D)
/*  796 */               Correct = false;
/*      */           }
/*      */         }
/*  799 */         if (Correct) {
/*  800 */           this.CurrentBalas.Fathomed = (!this.CurrentBalas.Fathomed);
/*      */         }
/*      */         else {
/*  803 */           this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  804 */           return false;
/*      */         }
/*      */       }
/*      */       break;
/*      */     case 40103: 
/*  809 */       int currentNodeLevel = ((Integer)p.elementAt(0)).intValue();
/*  810 */       int currentNodeOffset = ((Integer)p.elementAt(1)).intValue();
/*  811 */       int Variable = ((Integer)p.elementAt(2)).intValue();
/*  812 */       this.CurrentBalas = findDakins(this.FirstBalas, currentNodeLevel, currentNodeOffset);
/*  813 */       if ((this.CurrentBalas.LeftChild == null) && (this.CurrentBalas.RightChild == null) && (!this.CurrentBalas.Fathomed))
/*      */       {
/*  815 */         boolean Correct = this.CurrentBalas != this.FirstBalas;
/*  816 */         for (int i = 1; i <= this.SimplexModel.NumVariables; i++) {
/*  817 */           if (Math.abs(this.CurrentBalas.Solution[i] - Math.round(this.CurrentBalas.Solution[i])) > 1.0E-4D)
/*  818 */             Correct = true;
/*      */         }
/*  820 */         if (Correct)
/*      */         {
/*  822 */           if (currentNodeLevel >= 5)
/*      */           {
/*  824 */             this.errInfo = "You have already branched five levels (the maximum).";
/*  825 */             return false;
/*      */           }
/*      */         }
/*      */         else {
/*  829 */           this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  830 */           return false;
/*      */         }
/*      */       }
/*      */       
/*  834 */       Variable = Math.max(1, Variable);
/*  835 */       Variable = Math.min(this.SimplexModel.NumVariables, Variable);
/*  836 */       this.CurrentBalas.Variable = Variable;
/*  837 */       if ((this.CurrentBalas != this.FirstBalas) || (Variable == 1) || (Math.abs(this.CurrentBalas.Solution[Variable] - Math.round(this.CurrentBalas.Solution[Variable])) > 0.001D))
/*      */       {
/*  839 */         CreateNextBalas();
/*      */         
/*  841 */         for (int i = this.NumOriginalConstraints1 + 1; i <= this.NumOriginalConstraints2; i++)
/*      */         {
/*  843 */           this.SimplexModel.RightHandSide[i] = 1.0D;
/*  844 */           this.SimplexModel.Constraint[i] = 0;
/*      */         }
/*  846 */         ORSolverBase.DakinsType TempBalas = this.CurrentBalas.LeftChild;
/*  847 */         boolean Infeasible = false;
/*  848 */         this.SimplexModel.NumConstraints = this.NumOriginalConstraints2;
/*      */         do {
/*  850 */           if (TempBalas == TempBalas.Parent.LeftChild)
/*      */           {
/*  852 */             if ((this.SimplexModel.Constraint[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] == 1) && (this.SimplexModel.RightHandSide[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] >= 0.99D))
/*  853 */               Infeasible = true;
/*  854 */             this.SimplexModel.Constraint[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] = 1;
/*  855 */             this.SimplexModel.RightHandSide[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] = 0.0D;
/*      */           }
/*      */           else
/*      */           {
/*  859 */             if ((this.SimplexModel.Constraint[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] == 1) && (this.SimplexModel.RightHandSide[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] <= 0.01D))
/*  860 */               Infeasible = true;
/*  861 */             this.SimplexModel.Constraint[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] = 1;
/*  862 */             this.SimplexModel.RightHandSide[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] = 1.0D;
/*      */           }
/*  864 */           TempBalas = TempBalas.Parent;
/*  865 */         } while (TempBalas.Parent != null);
/*  866 */         RevisedSimplexMethod(false);
/*  867 */         for (i = 1; i <= this.SimplexModel.NumVariables; i++)
/*  868 */           this.CurrentBalas.LeftChild.Solution[i] = this.LPSolution.Solution[i];
/*  869 */         this.CurrentBalas.LeftChild.Feasible = this.CurrentSimplexTableau.Feasible;
/*  870 */         if (Infeasible)
/*  871 */           this.CurrentBalas.LeftChild.Feasible = false;
/*  872 */         this.CurrentBalas.LeftChild.Fathomed = (!this.CurrentBalas.LeftChild.Feasible);
/*  873 */         this.CurrentBalas.LeftChild.Z = this.CurrentSimplexTableau.Z;
/*  874 */         if (this.SimplexModel.Objective == 1)
/*  875 */           this.CurrentBalas.LeftChild.Z = (-this.CurrentBalas.LeftChild.Z);
/*  876 */         this.CurrentBalas.LeftChild.RightChild = null;
/*  877 */         this.CurrentBalas.LeftChild.LeftChild = null;
/*      */         
/*  879 */         for (i = this.NumOriginalConstraints1 + 1; i <= this.NumOriginalConstraints2; i++)
/*      */         {
/*  881 */           this.SimplexModel.RightHandSide[i] = 1.0D;
/*  882 */           this.SimplexModel.Constraint[i] = 0;
/*      */         }
/*  884 */         TempBalas = this.CurrentBalas.RightChild;
/*  885 */         Infeasible = false;
/*      */         do {
/*  887 */           if (TempBalas == TempBalas.Parent.LeftChild)
/*      */           {
/*  889 */             if ((this.SimplexModel.Constraint[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] == 1) && (this.SimplexModel.RightHandSide[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] >= 0.99D))
/*  890 */               Infeasible = true;
/*  891 */             this.SimplexModel.Constraint[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] = 1;
/*  892 */             this.SimplexModel.RightHandSide[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] = 0.0D;
/*      */           }
/*      */           else
/*      */           {
/*  896 */             if ((this.SimplexModel.Constraint[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] == 1) && (this.SimplexModel.RightHandSide[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] <= 0.01D))
/*  897 */               Infeasible = true;
/*  898 */             this.SimplexModel.Constraint[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] = 1;
/*  899 */             this.SimplexModel.RightHandSide[(this.NumOriginalConstraints1 + TempBalas.Parent.Variable)] = 1.0D;
/*      */           }
/*  901 */           TempBalas = TempBalas.Parent;
/*  902 */         } while (TempBalas.Parent != null);
/*  903 */         RevisedSimplexMethod(false);
/*  904 */         for (i = 1; i <= this.SimplexModel.NumVariables; i++)
/*  905 */           this.CurrentBalas.RightChild.Solution[i] = this.LPSolution.Solution[i];
/*  906 */         this.CurrentBalas.RightChild.Feasible = this.CurrentSimplexTableau.Feasible;
/*  907 */         if (Infeasible)
/*  908 */           this.CurrentBalas.RightChild.Feasible = false;
/*  909 */         this.CurrentBalas.RightChild.Fathomed = (!this.CurrentBalas.RightChild.Feasible);
/*  910 */         this.CurrentBalas.RightChild.Z = this.CurrentSimplexTableau.Z;
/*  911 */         if (this.SimplexModel.Objective == 1)
/*  912 */           this.CurrentBalas.RightChild.Z = (-this.CurrentBalas.RightChild.Z);
/*  913 */         this.CurrentBalas.RightChild.RightChild = null;
/*  914 */         this.CurrentBalas.RightChild.LeftChild = null;
/*      */       }
/*      */       else
/*      */       {
/*  918 */         this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  919 */         return false;
/*      */       }
/*      */       break;
/*      */     case 40202: 
/*  923 */       this.CommandNumber = 5;
/*      */       
/*  925 */       InitializeBalasList();
/*  926 */       InitializeDakinsList();
/*      */       
/*  928 */       CreateFirstDakins();
/*  929 */       break;
/*      */     case 40204: 
/*  931 */       int currentNodeLevel = ((Integer)p.elementAt(0)).intValue();
/*  932 */       int currentNodeOffset = ((Integer)p.elementAt(1)).intValue();
/*  933 */       this.CurrentDakins = findDakins(this.FirstDakins, currentNodeLevel, currentNodeOffset);
/*      */       
/*  935 */       boolean Correct = true;
/*  936 */       if (this.CurrentDakins == this.FirstDakins) {
/*  937 */         for (int i = 1; i <= this.NumIntegerVariables + this.NumBinaryVariables; i++) {
/*  938 */           if (Math.abs(this.CurrentDakins.Solution[i] - Math.round(this.CurrentDakins.Solution[i])) > 1.0E-4D)
/*  939 */             Correct = false;
/*      */         }
/*      */       }
/*  942 */       if (Correct) {
/*  943 */         this.CurrentDakins.IncumbentNumber = (MaxIncumbency(this.FirstDakins) + 1);
/*  944 */         ReviseDakinsIncumbent();
/*      */       }
/*      */       else {
/*  947 */         this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  948 */         return false;
/*      */       }
/*      */       break;
/*      */     case 40205: 
/*  952 */       int currentNodeLevel = ((Integer)p.elementAt(0)).intValue();
/*  953 */       int currentNodeOffset = ((Integer)p.elementAt(1)).intValue();
/*  954 */       this.CurrentDakins = findDakins(this.FirstDakins, currentNodeLevel, currentNodeOffset);
/*      */       
/*  956 */       if ((this.CurrentDakins.LeftChild == null) && (this.CurrentDakins.RightChild == null) && (this.CurrentDakins.Feasible))
/*      */       {
/*  958 */         boolean Correct = true;
/*  959 */         if (this.CurrentDakins == this.FirstDakins) {
/*  960 */           for (int i = 1; i <= this.NumIntegerVariables + this.NumBinaryVariables; i++) {
/*  961 */             if (Math.abs(this.CurrentDakins.Solution[i] - Math.round(this.CurrentDakins.Solution[i])) > 1.0E-4D)
/*  962 */               Correct = false;
/*      */           }
/*      */         }
/*  965 */         if (Correct) {
/*  966 */           this.CurrentDakins.Fathomed = (!this.CurrentDakins.Fathomed);
/*      */         } else {
/*  968 */           this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  969 */           return false;
/*      */         }
/*      */       }
/*      */       break;
/*      */     case 40206: 
/*  974 */       if (this.CurrentDakins == this.FirstDakins) {
/*  975 */         for (int i = 1; i <= this.NumIntegerVariables + this.NumBinaryVariables; i++) {
/*  976 */           if (Math.abs(this.CurrentDakins.Solution[i] - Math.round(this.CurrentDakins.Solution[i])) > 1.0E-4D)
/*  977 */             return true;
/*      */         }
/*  979 */         this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  980 */         return false;
/*      */       }
/*      */       break;
/*      */     case 40203: 
/*  984 */       int currentNodeLevel = ((Integer)p.elementAt(0)).intValue();
/*  985 */       int currentNodeOffset = ((Integer)p.elementAt(1)).intValue();
/*  986 */       this.CurrentDakins = findDakins(this.FirstDakins, currentNodeLevel, currentNodeOffset);
/*  987 */       int Variable = ((Integer)p.elementAt(2)).intValue();
/*  988 */       Variable = Math.max(1, Variable);
/*  989 */       Variable = Math.min(this.SimplexModel.NumVariables, Variable);
/*  990 */       this.CurrentDakins.Variable = Variable;
/*  991 */       this.CurrentDakins.LessThanWhat = ((Integer)p.elementAt(3)).intValue();
/*  992 */       this.CurrentDakins.GreaterThanWhat = ((Integer)p.elementAt(4)).intValue();
/*      */       
/*  994 */       boolean Correct = true;
/*  995 */       if (this.CurrentDakins == this.FirstDakins)
/*      */       {
/*  997 */         if (Math.abs(this.CurrentDakins.Solution[Variable] - Math.round(this.CurrentDakins.Solution[Variable])) <= 0.001D)
/*  998 */           Correct = false;
/*  999 */         if (Math.abs(this.CurrentDakins.LessThanWhat - (int)this.CurrentDakins.Solution[Variable]) >= 0.001D)
/* 1000 */           Correct = false;
/* 1001 */         if (Math.abs(this.CurrentDakins.GreaterThanWhat - (int)(this.CurrentDakins.Solution[this.CurrentDakins.Variable] + 1)) >= 0.001D)
/* 1002 */           Correct = false;
/*      */       }
/* 1004 */       if (Correct == false) {
/* 1005 */         this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1006 */         return false;
/*      */       }
/*      */       
/* 1009 */       CreateNextDakins();
/*      */       
/* 1011 */       ORSolverBase.DakinsType TempDakins = this.CurrentDakins.LeftChild;
/* 1012 */       this.SimplexModel.NumConstraints = this.NumOriginalConstraints2;
/*      */       do {
/* 1014 */         this.SimplexModel.NumConstraints += 1;
/* 1015 */         for (i = 1; i <= this.SimplexModel.NumVariables; i++)
/* 1016 */           this.SimplexModel.A[this.SimplexModel.NumConstraints][i] = 0.0D;
/* 1017 */         this.SimplexModel.A[this.SimplexModel.NumConstraints][TempDakins.Parent.Variable] = 1.0D;
/* 1018 */         if (TempDakins == TempDakins.Parent.LeftChild) {
/* 1019 */           this.SimplexModel.Constraint[this.SimplexModel.NumConstraints] = 0;
/* 1020 */           this.SimplexModel.RightHandSide[this.SimplexModel.NumConstraints] = TempDakins.Parent.LessThanWhat;
/*      */         }
/*      */         else {
/* 1023 */           this.SimplexModel.Constraint[this.SimplexModel.NumConstraints] = 2;
/* 1024 */           this.SimplexModel.RightHandSide[this.SimplexModel.NumConstraints] = TempDakins.Parent.GreaterThanWhat;
/*      */         }
/* 1026 */         TempDakins = TempDakins.Parent;
/* 1027 */       } while (TempDakins.Parent != null);
/* 1028 */       RevisedSimplexMethod(false);
/* 1029 */       for (int i = 1; i <= this.SimplexModel.NumVariables; i++)
/* 1030 */         this.CurrentDakins.LeftChild.Solution[i] = this.LPSolution.Solution[i];
/* 1031 */       this.CurrentDakins.LeftChild.Feasible = this.CurrentSimplexTableau.Feasible;
/* 1032 */       this.CurrentDakins.LeftChild.Fathomed = (!this.CurrentDakins.LeftChild.Feasible);
/* 1033 */       this.CurrentDakins.LeftChild.Z = this.CurrentSimplexTableau.Z;
/* 1034 */       if (this.SimplexModel.Objective == 1)
/* 1035 */         this.CurrentDakins.LeftChild.Z = (-this.CurrentDakins.LeftChild.Z);
/* 1036 */       this.CurrentDakins.LeftChild.RightChild = null;
/* 1037 */       this.CurrentDakins.LeftChild.LeftChild = null;
/*      */       
/*      */ 
/* 1040 */       TempDakins = this.CurrentDakins.RightChild;
/* 1041 */       this.SimplexModel.NumConstraints = this.NumOriginalConstraints2;
/*      */       do {
/* 1043 */         this.SimplexModel.NumConstraints += 1;
/* 1044 */         for (i = 1; i <= this.SimplexModel.NumVariables; i++)
/* 1045 */           this.SimplexModel.A[this.SimplexModel.NumConstraints][i] = 0.0D;
/* 1046 */         this.SimplexModel.A[this.SimplexModel.NumConstraints][TempDakins.Parent.Variable] = 1.0D;
/* 1047 */         if (TempDakins == TempDakins.Parent.LeftChild) {
/* 1048 */           this.SimplexModel.Constraint[this.SimplexModel.NumConstraints] = 0;
/* 1049 */           this.SimplexModel.RightHandSide[this.SimplexModel.NumConstraints] = TempDakins.Parent.LessThanWhat;
/*      */         }
/*      */         else {
/* 1052 */           this.SimplexModel.Constraint[this.SimplexModel.NumConstraints] = 2;
/* 1053 */           this.SimplexModel.RightHandSide[this.SimplexModel.NumConstraints] = TempDakins.Parent.GreaterThanWhat;
/*      */         }
/* 1055 */         TempDakins = TempDakins.Parent;
/* 1056 */       } while (TempDakins.Parent != null);
/* 1057 */       RevisedSimplexMethod(false);
/* 1058 */       for (i = 1; i <= this.SimplexModel.NumVariables; i++)
/* 1059 */         this.CurrentDakins.RightChild.Solution[i] = this.LPSolution.Solution[i];
/* 1060 */       this.CurrentDakins.RightChild.Feasible = this.CurrentSimplexTableau.Feasible;
/* 1061 */       this.CurrentDakins.RightChild.Fathomed = (!this.CurrentDakins.RightChild.Feasible);
/* 1062 */       this.CurrentDakins.RightChild.Z = this.CurrentSimplexTableau.Z;
/* 1063 */       if (this.SimplexModel.Objective == 1)
/* 1064 */         this.CurrentDakins.RightChild.Z = (-this.CurrentDakins.RightChild.Z);
/* 1065 */       this.CurrentDakins.RightChild.RightChild = null;
/* 1066 */       this.CurrentDakins.RightChild.LeftChild = null;
/* 1067 */       break;
/*      */     }
/* 1069 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 1074 */   private ORSolverBase.DakinsType PrintNode = null;
/*      */   
/* 1076 */   protected void ORPrint() { if (this.procedure == 1) {
/* 1077 */       PrintIntegerEnterModel();
/* 1078 */     } else if (this.procedure == 2) {
/* 1079 */       PrintInteractiveBIP();
/* 1080 */     } else if (this.procedure == 3) {
/* 1081 */       PrintInteractiveMIP();
/*      */     }
/*      */   }
/*      */   
/*      */   private void PrintIntegerEnterModel() {
/* 1086 */     PrintLine("Integer Programming Model:");
/* 1087 */     SkipLine();
/* 1088 */     PrintSimplexModel();
/*      */   }
/*      */   
/*      */ 
/*      */   private void PrintInteractiveBIP()
/*      */   {
/* 1094 */     PrintLine("Interactive Binary Integer Programming Branch and Bound Algorithm");
/* 1095 */     SkipLine();
/* 1096 */     PrintLine("Model:");
/* 1097 */     SkipLine();
/* 1098 */     PrintSimplexModel();
/* 1099 */     Skip(2);
/* 1100 */     if (this.IncumbentExists) {
/* 1101 */       PrintLine("Optimal Solution:");
/* 1102 */       Print("  (");
/* 1103 */       for (int i = 1; i <= this.SimplexModel.NumVariables; i++) {
/* 1104 */         Print("X".concat(String.valueOf(String.valueOf(FormatInteger(i, 1, 0, 0)))));
/* 1105 */         if (i == this.SimplexModel.NumVariables) {
/* 1106 */           Print(") = (");
/*      */         } else
/* 1108 */           Print(", ");
/*      */       }
/* 1110 */       for (i = 1; i <= this.SimplexModel.NumVariables; i++) {
/* 1111 */         if (this.Incumbent[i] < 0.5D) {
/* 1112 */           Print("0");
/*      */         } else
/* 1114 */           Print("1");
/* 1115 */         if (i == this.SimplexModel.NumVariables) {
/* 1116 */           PrintLine(")");
/*      */         } else
/* 1118 */           Print(", ");
/*      */       }
/* 1120 */       PrintLine("  Z = ".concat(String.valueOf(String.valueOf(FormatReal(this.IncumbentZ, 10, 0, 0)))));
/* 1121 */       SkipLine();
/*      */     }
/* 1123 */     PrintLine("Solution Tree:");
/* 1124 */     SkipLine();
/* 1125 */     PrintIntegerTree(this.FirstBalas);
/*      */   }
/*      */   
/*      */ 
/*      */   private void PrintInteractiveMIP()
/*      */   {
/* 1131 */     PrintLine("Interactive Mixed Integer Programming Branch and Bound Algorithm");
/* 1132 */     SkipLine();
/* 1133 */     PrintLine("Model:");
/* 1134 */     SkipLine();
/* 1135 */     PrintSimplexModel();
/* 1136 */     Skip(2);
/* 1137 */     if (this.IncumbentExists) {
/* 1138 */       PrintLine("Optimal Solution:");
/* 1139 */       Print("  (");
/* 1140 */       for (int i = 1; i <= this.SimplexModel.NumVariables; i++) {
/* 1141 */         Print("X".concat(String.valueOf(String.valueOf(FormatInteger(i, 1, 0, 0)))));
/* 1142 */         if (i == this.SimplexModel.NumVariables) {
/* 1143 */           Print(") = (");
/*      */         } else
/* 1145 */           Print(", ");
/*      */       }
/* 1147 */       for (i = 1; i <= this.SimplexModel.NumVariables; i++) {
/* 1148 */         Print(RealToString(this.Incumbent[i], 6));
/* 1149 */         if (i == this.SimplexModel.NumVariables) {
/* 1150 */           PrintLine(")");
/*      */         } else
/* 1152 */           Print(", ");
/*      */       }
/* 1154 */       PrintLine("  Z = ".concat(String.valueOf(String.valueOf(FormatReal(this.IncumbentZ, 10, 0, 0)))));
/* 1155 */       SkipLine();
/*      */     }
/* 1157 */     if ((this.CurrentDakins == this.FirstDakins) && (this.CurrentSimplexTableau.Unbounded)) {
/* 1158 */       PrintLine("The integer programming model you have entered is unbounded.");
/*      */     } else {
/* 1160 */       PrintLine("Solution Tree:");
/* 1161 */       SkipLine();
/* 1162 */       PrintIntegerTree(this.FirstDakins);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean NodeExists(int Which, ORSolverBase.DakinsType Root)
/*      */   {
/* 1170 */     boolean bNodeExists = false;
/*      */     
/*      */ 
/* 1173 */     ORSolverBase.DakinsType TempNode = Root;
/* 1174 */     int Temp = 32;
/* 1175 */     int Factor = 16;
/* 1176 */     while ((TempNode != null) && (Temp != Which) && (Factor > 0)) {
/* 1177 */       if (Which < Temp) {
/* 1178 */         Temp -= Factor;
/* 1179 */         Factor /= 2;
/* 1180 */         TempNode = TempNode.RightChild;
/*      */       }
/*      */       else {
/* 1183 */         Temp += Factor;
/* 1184 */         Factor /= 2;
/* 1185 */         TempNode = TempNode.LeftChild;
/*      */       }
/*      */     }
/* 1188 */     if ((Temp == Which) && (TempNode != null)) {
/* 1189 */       bNodeExists = true;
/* 1190 */       this.PrintNode = TempNode;
/*      */     }
/*      */     else {
/* 1193 */       bNodeExists = false;
/* 1194 */       this.PrintNode = null;
/*      */     }
/* 1196 */     return bNodeExists;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void PrintANode(ORSolverBase.DakinsType Node, ORSolverBase.DakinsType Root)
/*      */   {
/* 1203 */     if (Node != Root) {
/* 1204 */       Print("X".concat(String.valueOf(String.valueOf(FormatInteger(Node.Parent.Variable, 1, 0, 0)))));
/* 1205 */       if (this.CommandNumber == 5) {
/* 1206 */         if (Node == Node.Parent.RightChild) {
/* 1207 */           String AString = RealToString(Node.Parent.GreaterThanWhat, 4);
/* 1208 */           Print(String.valueOf(String.valueOf(new StringBuffer(">=").append(AString).append(","))));
/*      */         }
/*      */         else {
/* 1211 */           String AString = RealToString(Node.Parent.LessThanWhat, 4);
/* 1212 */           Print(String.valueOf(String.valueOf(new StringBuffer("<=").append(AString).append(","))));
/*      */         }
/*      */         
/*      */       }
/* 1216 */       else if (Node == Node.Parent.RightChild) {
/* 1217 */         Print("=1,");
/*      */       } else {
/* 1219 */         Print("=0,");
/*      */       }
/*      */     }
/* 1222 */     if (Node.Feasible) {
/* 1223 */       Print("X=(");
/* 1224 */       for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/* 1225 */         String AString = RealToString(Node.Solution[j], 4);
/* 1226 */         Print(AString);
/* 1227 */         if (j == this.SimplexModel.NumVariables) {
/* 1228 */           Print(")");
/*      */         } else
/* 1230 */           Print(",");
/*      */       }
/* 1232 */       Print(",Z=");
/* 1233 */       String AString = RealToString(Node.Z, 6);
/* 1234 */       Print(AString);
/*      */     }
/*      */     else {
/* 1237 */       Print("Inf."); }
/* 1238 */     if (Node.Fathomed) {
/* 1239 */       Print(",F");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void PrintIntegerTree(ORSolverBase.DakinsType Root)
/*      */   {
/* 1246 */     for (int i = 1; i <= 32; i++)
/*      */     {
/* 1248 */       if ((i == 16) && (NodeExists(32, Root))) {
/* 1249 */         PrintANode(this.PrintNode, Root);
/*      */       }
/* 1251 */       if (i == 8) {
/* 1252 */         if (NodeExists(16, Root)) {
/* 1253 */           TabPrint(12, "--- ");
/* 1254 */           PrintANode(this.PrintNode, Root);
/*      */         }
/*      */       }
/* 1257 */       else if ((i >= 9) && (i <= 15)) {
/* 1258 */         if (NodeExists(16, Root)) {
/* 1259 */           TabPrint(19 - i, "/");
/*      */         }
/* 1261 */       } else if ((i >= 17) && (i <= 23)) {
/* 1262 */         if (NodeExists(48, Root)) {
/* 1263 */           TabPrint(i - 13, "\\");
/*      */         }
/* 1265 */       } else if ((i == 24) && 
/* 1266 */         (NodeExists(48, Root))) {
/* 1267 */         TabPrint(12, "--- ");
/* 1268 */         PrintANode(this.PrintNode, Root);
/*      */       }
/*      */       
/*      */ 
/* 1272 */       if (i == 4) {
/* 1273 */         if (NodeExists(8, Root)) {
/* 1274 */           TabPrint(23, "--- ");
/* 1275 */           PrintANode(this.PrintNode, Root);
/*      */         }
/*      */       }
/* 1278 */       else if ((i >= 5) && (i <= 7)) {
/* 1279 */         if (NodeExists(8, Root)) {
/* 1280 */           TabPrint(26 - i, "/");
/*      */         }
/* 1282 */       } else if ((i >= 9) && (i <= 11)) {
/* 1283 */         if (NodeExists(24, Root)) {
/* 1284 */           TabPrint(10 + i, "\\");
/*      */         }
/* 1286 */       } else if (i == 12) {
/* 1287 */         if (NodeExists(24, Root)) {
/* 1288 */           TabPrint(23, "--- ");
/* 1289 */           PrintANode(this.PrintNode, Root);
/*      */         }
/*      */       }
/* 1292 */       else if (i == 20) {
/* 1293 */         if (NodeExists(40, Root)) {
/* 1294 */           TabPrint(23, "--- ");
/* 1295 */           PrintANode(this.PrintNode, Root);
/*      */         }
/*      */       }
/* 1298 */       else if ((i >= 21) && (i <= 23)) {
/* 1299 */         if (NodeExists(40, Root)) {
/* 1300 */           TabPrint(42 - i, "/");
/*      */         }
/* 1302 */       } else if ((i >= 25) && (i <= 27)) {
/* 1303 */         if (NodeExists(56, Root)) {
/* 1304 */           TabPrint(i - 6, "\\");
/*      */         }
/* 1306 */       } else if ((i == 28) && 
/* 1307 */         (NodeExists(56, Root))) {
/* 1308 */         TabPrint(23, "--- ");
/* 1309 */         PrintANode(this.PrintNode, Root);
/*      */       }
/*      */       
/*      */ 
/* 1313 */       if ((i % 4 == 2) && 
/* 1314 */         (NodeExists(4 + 8 * (i * 2 / 8), Root))) {
/* 1315 */         TabPrint(32, "--- ");
/* 1316 */         PrintANode(this.PrintNode, Root);
/*      */       }
/*      */       
/* 1319 */       if ((i % 8 == 3) && 
/* 1320 */         (NodeExists(4 + 8 * (i * 2 / 8), Root))) {
/* 1321 */         TabPrint(30, "/");
/*      */       }
/* 1323 */       if ((i % 8 == 5) && 
/* 1324 */         (NodeExists(4 + 8 * (i * 2 / 8), Root))) {
/* 1325 */         TabPrint(30, "\\");
/*      */       }
/*      */       
/* 1328 */       if ((i % 2 == 1) && 
/* 1329 */         (NodeExists(i * 2, Root))) {
/* 1330 */         if (i % 4 == 1) {
/* 1331 */           TabPrint(38, ",-- ");
/*      */         } else
/* 1333 */           TabPrint(38, "`-- ");
/* 1334 */         PrintANode(this.PrintNode, Root);
/*      */       }
/*      */       
/*      */ 
/* 1338 */       if (NodeExists(i * 2 - 1, Root)) {
/* 1339 */         if (i % 2 == 1) {
/* 1340 */           TabPrint(62, "- ");
/* 1341 */           PrintANode(this.PrintNode, Root);
/*      */         }
/*      */         else {
/* 1344 */           TabPrint(62, "\\ ");
/* 1345 */           PrintANode(this.PrintNode, Root);
/*      */         }
/*      */       }
/* 1348 */       SkipLine();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORIPSolver.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */