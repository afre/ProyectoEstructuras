/*      */ import java.awt.Point;
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
/*      */ 
/*      */ public class ORTPSolver
/*      */   extends ORSolverBase
/*      */ {
/*   29 */   private ORSolverBase.TransportationModelType TransportationModel = new ORSolverBase.TransportationModelType(this);
/*      */   private int InitialSolutionOK;
/*   31 */   private ORSolverBase.TransportationTableauType CurrentTransportationTableau = new ORSolverBase.TransportationTableauType(this);
/*   32 */   private ORSolverBase.TransportationTableauType CurrentTransportationTableau_bak = new ORSolverBase.TransportationTableauType(this);
/*   33 */   private ORSolverBase.TransportationTableauType CurrentTransportationTableau_bak_for_solve = new ORSolverBase.TransportationTableauType(this);
/*      */   private int TransIBFSMethod;
/*   35 */   private boolean isFirstIteration = true;
/*      */   
/*   37 */   private double[] RussellU = new double[16];
/*   38 */   private double[] RussellUBigM = new double[16];
/*   39 */   private double[] RussellV = new double[16];
/*   40 */   private double[] RussellVBigM = new double[16];
/*   41 */   private double[][] RussellCell = new double[16][16];
/*   42 */   private double[][] RussellCellBigM = new double[16][16];
/*      */   
/*   44 */   private double[] VogelsColumnDiff = new double[16];
/*   45 */   private double[] VogelsColumnDiffM = new double[16];
/*   46 */   private double[] VogelsRowDiff = new double[16];
/*   47 */   private double[] VogelsRowDiffM = new double[16];
/*      */   
/*   49 */   private boolean hasSetInitialUV = false;
/*   50 */   private boolean bShowLoopInfo = false;
/*   51 */   private ORSolverBase.TransportationTableauType CurrentTransportationTableau_bak_for_print = null;
/*      */   
/*   53 */   private Vector output_AutoData = null;
/*   54 */   private String result_AutoS = null;
/*   55 */   private Vector autoPrintData = null;
/*      */   
/*      */   public ORTPSolver()
/*      */   {
/*   59 */     InitializeTransportationModel();
/*   60 */     my_InitializeTransportationModel();
/*   61 */     this.output_AutoData = null;
/*   62 */     this.result_AutoS = null;
/*      */   }
/*      */   
/*      */   private void DoAuto()
/*      */   {
/*   67 */     InitializeTransportationModel();
/*      */     
/*   69 */     DoTransportationSolveCommand();
/*      */     
/*   71 */     OutputResult();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void DoTransportationSolveCommand()
/*      */   {
/*   80 */     this.CommandNumber = 2;
/*   81 */     this.InitialSolutionOK = 0;
/*      */     
/*   83 */     double Sum = 0.0D;
/*   84 */     for (int i = 1; i <= this.TransportationModel.NumSources; i++)
/*   85 */       Sum += this.TransportationModel.Supply[i];
/*   86 */     for (i = 1; i <= this.TransportationModel.NumDestinations; i++)
/*   87 */       Sum -= this.TransportationModel.Demand[i];
/*   88 */     if (Math.abs(Sum) > 0.1D) {
/*   89 */       this.InitialSolutionOK = 3;
/*      */     }
/*   91 */     if (this.InitialSolutionOK == 0)
/*      */     {
/*   93 */       InitializeTransportationTableau();
/*   94 */       SolveTransportationProblem();
/*      */     }
/*      */   }
/*      */   
/*      */   private void InitializeTransportationModel()
/*      */   {
/*  100 */     this.TransIBFSMethod = 0;
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
/*      */   private void my_InitializeTransportationModel()
/*      */   {
/*  142 */     this.TransportationModel.NumSources = 4;
/*  143 */     this.TransportationModel.NumDestinations = 5;
/*  144 */     this.TransportationModel.NumBasicVariables = 8;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void InitializeTransportationTableau()
/*      */   {
/*  153 */     this.CurrentTransportationTableau = null;
/*  154 */     ORSolverBase.TransportationTableauType aTransportationTableau = new ORSolverBase.TransportationTableauType(this);
/*  155 */     if (this.TransportationModel.NumSources >= 6) {
/*  156 */       aTransportationTableau.Spacing = 2;
/*      */     } else
/*  158 */       aTransportationTableau.Spacing = 3;
/*  159 */     aTransportationTableau.Y = 1;
/*  160 */     if (this.TransportationModel.NumDestinations > 6) {
/*  161 */       aTransportationTableau.Crunch = 0;
/*      */     } else
/*  163 */       aTransportationTableau.Crunch = 0;
/*  164 */     for (int i = 1; i <= 30; i++)
/*      */     {
/*  166 */       aTransportationTableau.BasicVariable[i][1] = 0;
/*  167 */       aTransportationTableau.BasicVariable[i][2] = 0;
/*  168 */       aTransportationTableau.Path.Source[i] = 0;
/*  169 */       aTransportationTableau.Path.Destination[i] = 0;
/*      */     }
/*  171 */     aTransportationTableau.EnteringBasicVariable[1] = 0;
/*  172 */     aTransportationTableau.EnteringBasicVariable[2] = 0;
/*  173 */     aTransportationTableau.LeavingBasicVariable[1] = 0;
/*  174 */     aTransportationTableau.LeavingBasicVariable[2] = 0;
/*  175 */     aTransportationTableau.Marker[1] = 1;
/*  176 */     aTransportationTableau.Marker[2] = 1;
/*  177 */     for (i = 1; i <= 15; i++)
/*      */     {
/*  179 */       aTransportationTableau.U[i] = 0.0D;
/*  180 */       aTransportationTableau.UM[i] = 0.0D;
/*  181 */       aTransportationTableau.EliminatedSource[i] = false;
/*      */     }
/*  183 */     for (int j = 1; j <= 15; j++)
/*      */     {
/*  185 */       aTransportationTableau.EliminatedDestination[j] = false;
/*  186 */       aTransportationTableau.V[j] = 0.0D;
/*  187 */       aTransportationTableau.VM[j] = 0.0D;
/*      */     }
/*  189 */     for (i = 1; i <= 15; i++)
/*      */     {
/*  191 */       for (j = 1; j <= 15; j++)
/*      */       {
/*  193 */         aTransportationTableau.Cell[i][j] = 0.0D;
/*  194 */         aTransportationTableau.CellM[i][j] = 0.0D;
/*  195 */         aTransportationTableau.ABasicVariable[i][j] = 0;
/*      */       }
/*      */     }
/*  198 */     aTransportationTableau.Path.Number = 0;
/*  199 */     aTransportationTableau.LastTableau = null;
/*  200 */     aTransportationTableau.NextTableau = null;
/*      */     
/*  202 */     this.CurrentTransportationTableau = aTransportationTableau;
/*      */     
/*  204 */     this.InitialSolutionOK = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void SolveTransportationProblem()
/*      */   {
/*  211 */     int Iteration = 0;
/*  212 */     TransFindIBFS2();
/*  213 */     TransCalculateUV();
/*  214 */     while ((TransFindEnteringBasicVariable()) && (Iteration < 100))
/*      */     {
/*  216 */       TransFindPath();
/*  217 */       TransFindLeavingBasicVariable();
/*  218 */       TransIterate();
/*  219 */       TransCalculateUV();
/*  220 */       Iteration += 1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void TransFindIBFS2()
/*      */   {
/*  230 */     int[] WhichDestination = new int[16];
/*  231 */     int[] WhichSource = new int[16];
/*  232 */     int WhichRow = 0;int WhichColumn = 0;
/*  233 */     int TheSource = 0;int TheDestination = 0;
/*      */     
/*  235 */     int[] RemainingSupply = new int[16];
/*  236 */     int[] RemainingDemand = new int[16];
/*      */     
/*  238 */     for (int i = 1; i <= this.TransportationModel.NumSources; i++)
/*  239 */       RemainingSupply[i] = this.TransportationModel.Supply[i];
/*  240 */     for (i = 1; i <= this.TransportationModel.NumDestinations; i++)
/*  241 */       RemainingDemand[i] = this.TransportationModel.Demand[i];
/*  242 */     int NumRemainingSources = this.TransportationModel.NumSources;
/*  243 */     int NumRemainingDestinations = this.TransportationModel.NumDestinations;
/*  244 */     int BV = 0;
/*  245 */     while ((NumRemainingSources > 1) && (NumRemainingDestinations > 1))
/*      */     {
/*  247 */       BV += 1;
/*  248 */       double Max1 = -1.0D;
/*  249 */       double MaxM1 = -1.0D;
/*  250 */       for (i = 1; i <= this.TransportationModel.NumSources; i++)
/*      */       {
/*  252 */         if (this.CurrentTransportationTableau.EliminatedSource[i] == 0)
/*      */         {
/*  254 */           double Min1 = 1.0E20D;
/*  255 */           double Min2 = 1.0E20D;
/*  256 */           double MinM1 = 1.0E20D;
/*  257 */           double MinM2 = 1.0E20D;
/*  258 */           for (int j = 1; j <= this.TransportationModel.NumDestinations; j++)
/*      */           {
/*  260 */             if (this.CurrentTransportationTableau.EliminatedDestination[j] == 0)
/*      */             {
/*  262 */               if (this.TransportationModel.CostM[i][j] < MinM1 - 1.0E-5D)
/*      */               {
/*  264 */                 Min2 = Min1;
/*  265 */                 MinM2 = MinM1;
/*  266 */                 MinM1 = this.TransportationModel.CostM[i][j];
/*  267 */                 Min1 = this.TransportationModel.Cost[i][j];
/*  268 */                 WhichDestination[i] = j;
/*      */               }
/*  270 */               else if ((Math.abs(this.TransportationModel.CostM[i][j] - MinM1) < 1.0E-5D) && (this.TransportationModel.Cost[i][j] < Min1))
/*      */               {
/*  272 */                 Min2 = Min1;
/*  273 */                 MinM2 = MinM1;
/*  274 */                 MinM1 = this.TransportationModel.CostM[i][j];
/*  275 */                 Min1 = this.TransportationModel.Cost[i][j];
/*  276 */                 WhichDestination[i] = j;
/*      */               }
/*  278 */               else if (this.TransportationModel.CostM[i][j] < MinM2 - 1.0E-4D)
/*      */               {
/*  280 */                 MinM2 = this.TransportationModel.CostM[i][j];
/*  281 */                 Min2 = this.TransportationModel.Cost[i][j];
/*      */               }
/*  283 */               else if ((Math.abs(this.TransportationModel.CostM[i][j] - MinM2) < 1.0E-5D) && (this.TransportationModel.Cost[i][j] < Min2))
/*      */               {
/*  285 */                 MinM2 = this.TransportationModel.CostM[i][j];
/*  286 */                 Min2 = this.TransportationModel.Cost[i][j];
/*      */               }
/*      */             }
/*      */           }
/*  290 */           double TempM = MinM2 - MinM1;
/*  291 */           double Temp = Min2 - Min1;
/*  292 */           WhichRow = i;
/*  293 */           if (TempM > MaxM1)
/*      */           {
/*  295 */             MaxM1 = TempM;
/*  296 */             Max1 = Temp;
/*      */           }
/*  298 */           else if ((Math.abs(TempM - MaxM1) < 1.0E-4D) && (Temp > Max1))
/*      */           {
/*  300 */             MaxM1 = TempM;
/*  301 */             Max1 = Temp;
/*      */           }
/*      */         }
/*      */       }
/*  305 */       double Max2 = -1.0D;
/*  306 */       double MaxM2 = -1.0D;
/*  307 */       for (int j = 1; j <= this.TransportationModel.NumDestinations; j++)
/*      */       {
/*  309 */         if (this.CurrentTransportationTableau.EliminatedDestination[j] == 0)
/*      */         {
/*  311 */           double Min1 = 1.0E20D;
/*  312 */           double Min2 = 1.0E20D;
/*  313 */           double MinM1 = 1.0E20D;
/*  314 */           double MinM2 = 1.0E20D;
/*  315 */           for (i = 1; i <= this.TransportationModel.NumSources; i++)
/*      */           {
/*  317 */             if (this.CurrentTransportationTableau.EliminatedSource[i] == 0)
/*      */             {
/*  319 */               if (this.TransportationModel.CostM[i][j] < MinM1 - 1.0E-5D)
/*      */               {
/*  321 */                 Min2 = Min1;
/*  322 */                 MinM2 = MinM1;
/*  323 */                 MinM1 = this.TransportationModel.CostM[i][j];
/*  324 */                 Min1 = this.TransportationModel.Cost[i][j];
/*  325 */                 WhichSource[j] = i;
/*      */               }
/*  327 */               else if ((Math.abs(this.TransportationModel.CostM[i][j] - MinM1) < 1.0E-5D) && (this.TransportationModel.Cost[i][j] < Min1))
/*      */               {
/*  329 */                 Min2 = Min1;
/*  330 */                 MinM2 = MinM1;
/*  331 */                 MinM1 = this.TransportationModel.CostM[i][j];
/*  332 */                 Min1 = this.TransportationModel.Cost[i][j];
/*  333 */                 WhichSource[j] = i;
/*      */               }
/*  335 */               else if (this.TransportationModel.CostM[i][j] < MinM2 - 1.0E-4D)
/*      */               {
/*  337 */                 MinM2 = this.TransportationModel.CostM[i][j];
/*  338 */                 Min2 = this.TransportationModel.Cost[i][j];
/*      */               }
/*  340 */               else if ((Math.abs(this.TransportationModel.CostM[i][j] - MinM2) < 1.0E-5D) && (this.TransportationModel.Cost[i][j] < Min2))
/*      */               {
/*  342 */                 MinM2 = this.TransportationModel.CostM[i][j];
/*  343 */                 Min2 = this.TransportationModel.Cost[i][j];
/*      */               }
/*      */             }
/*      */           }
/*  347 */           double TempM = MinM2 - MinM1;
/*  348 */           double Temp = Min2 - Min1;
/*  349 */           WhichColumn = j;
/*  350 */           if (TempM > MaxM2)
/*      */           {
/*  352 */             MaxM2 = TempM;
/*  353 */             Max2 = Temp;
/*      */           }
/*  355 */           else if ((Math.abs(TempM - MaxM2) < 1.0E-4D) && (Temp > Max2))
/*      */           {
/*  357 */             MaxM2 = TempM;
/*  358 */             Max2 = Temp;
/*      */           }
/*      */         }
/*      */       }
/*  362 */       if (Math.abs(MaxM1 - MaxM2) < 1.0E-4D)
/*      */       {
/*  364 */         if (Max1 < Max2)
/*      */         {
/*  366 */           TheSource = WhichRow;
/*  367 */           TheDestination = WhichDestination[TheSource];
/*      */         }
/*      */         else
/*      */         {
/*  371 */           TheDestination = WhichColumn;
/*  372 */           TheSource = WhichSource[TheDestination];
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*  377 */       else if (MaxM1 < MaxM2)
/*      */       {
/*  379 */         TheSource = WhichRow;
/*  380 */         TheDestination = WhichDestination[TheSource];
/*      */       }
/*      */       else
/*      */       {
/*  384 */         TheDestination = WhichColumn;
/*  385 */         TheSource = WhichSource[TheDestination];
/*      */       }
/*      */       
/*  388 */       this.CurrentTransportationTableau.ABasicVariable[TheSource][TheDestination] = 1;
/*  389 */       this.CurrentTransportationTableau.BasicVariable[BV][1] = TheSource;
/*  390 */       this.CurrentTransportationTableau.BasicVariable[BV][2] = TheDestination;
/*  391 */       if (RemainingDemand[TheDestination] < RemainingSupply[TheSource])
/*      */       {
/*  393 */         this.CurrentTransportationTableau.CellM[TheSource][TheDestination] = 0.0D;
/*  394 */         this.CurrentTransportationTableau.Cell[TheSource][TheDestination] = RemainingDemand[TheDestination];
/*  395 */         RemainingSupply[TheSource] -= RemainingDemand[TheDestination];
/*  396 */         RemainingDemand[TheDestination] = 0;
/*  397 */         this.CurrentTransportationTableau.EliminatedDestination[TheDestination] = true;
/*  398 */         NumRemainingDestinations -= 1;
/*      */       }
/*      */       else
/*      */       {
/*  402 */         this.CurrentTransportationTableau.CellM[TheSource][TheDestination] = 0.0D;
/*  403 */         this.CurrentTransportationTableau.Cell[TheSource][TheDestination] = RemainingSupply[TheSource];
/*  404 */         RemainingDemand[TheDestination] -= RemainingSupply[TheSource];
/*  405 */         RemainingSupply[TheSource] = 0;
/*  406 */         this.CurrentTransportationTableau.EliminatedSource[TheSource] = true;
/*  407 */         NumRemainingSources -= 1;
/*      */       }
/*      */     }
/*  410 */     if (NumRemainingSources == 1)
/*      */     {
/*  412 */       for (i = 1; i <= this.TransportationModel.NumSources; i++)
/*      */       {
/*  414 */         if (this.CurrentTransportationTableau.EliminatedSource[i] == 0)
/*  415 */           TheSource = i;
/*      */       }
/*  417 */       for (i = 1; i <= this.TransportationModel.NumDestinations; i++)
/*      */       {
/*  419 */         if (this.CurrentTransportationTableau.EliminatedDestination[i] == 0)
/*      */         {
/*  421 */           BV += 1;
/*  422 */           this.CurrentTransportationTableau.CellM[TheSource][i] = 0.0D;
/*  423 */           this.CurrentTransportationTableau.Cell[TheSource][i] = RemainingDemand[i];
/*  424 */           this.CurrentTransportationTableau.ABasicVariable[TheSource][i] = 1;
/*  425 */           this.CurrentTransportationTableau.BasicVariable[BV][1] = TheSource;
/*  426 */           this.CurrentTransportationTableau.BasicVariable[BV][2] = i;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  432 */     for (i = 1; i <= this.TransportationModel.NumDestinations; i++)
/*      */     {
/*  434 */       if (this.CurrentTransportationTableau.EliminatedDestination[i] == 0)
/*  435 */         TheDestination = i;
/*      */     }
/*  437 */     for (i = 1; i <= this.TransportationModel.NumSources; i++)
/*      */     {
/*  439 */       if (this.CurrentTransportationTableau.EliminatedSource[i] == 0)
/*      */       {
/*  441 */         BV += 1;
/*  442 */         this.CurrentTransportationTableau.CellM[i][TheDestination] = 0.0D;
/*  443 */         this.CurrentTransportationTableau.Cell[i][TheDestination] = RemainingSupply[i];
/*  444 */         this.CurrentTransportationTableau.ABasicVariable[i][TheDestination] = 1;
/*  445 */         this.CurrentTransportationTableau.BasicVariable[BV][1] = i;
/*  446 */         this.CurrentTransportationTableau.BasicVariable[BV][2] = TheDestination;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void TransCalculateUV()
/*      */   {
/*  456 */     boolean[] UFilled = new boolean[16];
/*  457 */     boolean[] VFilled = new boolean[16];
/*      */     
/*  459 */     int MaxSource = 0;
/*  460 */     int MaxDestination = 0;
/*  461 */     int WhichSource = 0;
/*  462 */     int WhichDestination = 0;
/*  463 */     for (int Source = 1; Source <= 15; Source++)
/*  464 */       UFilled[Source] = false;
/*  465 */     for (int Destination = 1; Destination <= 15; Destination++) {
/*  466 */       VFilled[Destination] = false;
/*      */     }
/*  468 */     for (Source = 1; Source <= this.TransportationModel.NumSources; Source++)
/*      */     {
/*  470 */       int Number = 0;
/*  471 */       for (int i = 1; i <= this.TransportationModel.NumBasicVariables; i++)
/*      */       {
/*  473 */         if (this.CurrentTransportationTableau.BasicVariable[i][1] == Source)
/*  474 */           Number += 1;
/*      */       }
/*  476 */       if (Number > MaxSource)
/*      */       {
/*  478 */         MaxSource = Number;
/*  479 */         WhichSource = Source;
/*      */       }
/*      */     }
/*  482 */     for (Destination = 1; Destination <= this.TransportationModel.NumDestinations; Destination++)
/*      */     {
/*  484 */       int Number = 0;
/*  485 */       for (int i = 1; i <= this.TransportationModel.NumBasicVariables; i++)
/*      */       {
/*  487 */         if (this.CurrentTransportationTableau.BasicVariable[i][2] == Destination)
/*  488 */           Number += 1;
/*      */       }
/*  490 */       if (Number > MaxDestination)
/*      */       {
/*  492 */         MaxDestination = Number;
/*  493 */         WhichDestination = Destination;
/*      */       }
/*      */     }
/*  496 */     if (MaxDestination > MaxSource)
/*      */     {
/*  498 */       this.CurrentTransportationTableau.V[WhichDestination] = 0.0D;
/*  499 */       this.CurrentTransportationTableau.VM[WhichDestination] = 0.0D;
/*  500 */       VFilled[WhichDestination] = true;
/*      */     }
/*      */     else
/*      */     {
/*  504 */       this.CurrentTransportationTableau.U[WhichSource] = 0.0D;
/*  505 */       this.CurrentTransportationTableau.UM[WhichSource] = 0.0D;
/*  506 */       UFilled[WhichSource] = true;
/*      */     }
/*  508 */     int NumFilled = 1;
/*  509 */     int j = 1;
/*      */     do {
/*  511 */       for (Source = 1; Source <= this.TransportationModel.NumSources; Source++) {
/*  512 */         if (UFilled[Source] == 0) {
/*  513 */           for (int i = 1; i <= this.TransportationModel.NumBasicVariables; i++) {
/*  514 */             if ((this.CurrentTransportationTableau.BasicVariable[i][1] == Source) && 
/*  515 */               (VFilled[this.CurrentTransportationTableau.BasicVariable[i][2]] != 0) && 
/*  516 */               (UFilled[Source] == 0)) {
/*  517 */               UFilled[Source] = true;
/*  518 */               NumFilled += 1;
/*  519 */               this.CurrentTransportationTableau.U[Source] = (this.TransportationModel.Cost[Source][this.CurrentTransportationTableau.BasicVariable[i][2]] - this.CurrentTransportationTableau.V[this.CurrentTransportationTableau.BasicVariable[i][2]]);
/*  520 */               this.CurrentTransportationTableau.UM[Source] = (this.TransportationModel.CostM[Source][this.CurrentTransportationTableau.BasicVariable[i][2]] - this.CurrentTransportationTableau.VM[this.CurrentTransportationTableau.BasicVariable[i][2]]);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  527 */       for (Destination = 1; Destination <= this.TransportationModel.NumDestinations; Destination++) {
/*  528 */         if (VFilled[Destination] == 0) {
/*  529 */           for (int i = 1; i <= this.TransportationModel.NumBasicVariables; i++) {
/*  530 */             if ((this.CurrentTransportationTableau.BasicVariable[i][2] == Destination) && 
/*  531 */               (UFilled[this.CurrentTransportationTableau.BasicVariable[i][1]] != 0) && 
/*  532 */               (VFilled[Destination] == 0)) {
/*  533 */               VFilled[Destination] = true;
/*  534 */               NumFilled += 1;
/*  535 */               this.CurrentTransportationTableau.V[Destination] = (this.TransportationModel.Cost[this.CurrentTransportationTableau.BasicVariable[i][1]][Destination] - this.CurrentTransportationTableau.U[this.CurrentTransportationTableau.BasicVariable[i][1]]);
/*  536 */               this.CurrentTransportationTableau.VM[Destination] = (this.TransportationModel.CostM[this.CurrentTransportationTableau.BasicVariable[i][1]][Destination] - this.CurrentTransportationTableau.UM[this.CurrentTransportationTableau.BasicVariable[i][1]]);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  543 */       j += 1;
/*  544 */     } while ((NumFilled != this.TransportationModel.NumSources + this.TransportationModel.NumDestinations) && (j != 30));
/*  545 */     for (int i = 1; i <= this.TransportationModel.NumSources; i++) {
/*  546 */       for (j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/*  547 */         if (TransABasicVariable(i, j) == false) {
/*  548 */           this.CurrentTransportationTableau.Cell[i][j] = (this.TransportationModel.Cost[i][j] - this.CurrentTransportationTableau.U[i] - this.CurrentTransportationTableau.V[j]);
/*  549 */           this.CurrentTransportationTableau.CellM[i][j] = (this.TransportationModel.CostM[i][j] - this.CurrentTransportationTableau.UM[i] - this.CurrentTransportationTableau.VM[j]);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean TransABasicVariable(int Source, int Destination)
/*      */   {
/*  560 */     boolean Temp = false;
/*  561 */     for (int i = 1; i <= this.TransportationModel.NumBasicVariables; i++)
/*      */     {
/*  563 */       if ((this.CurrentTransportationTableau.BasicVariable[i][1] == Source) && (this.CurrentTransportationTableau.BasicVariable[i][2] == Destination))
/*  564 */         Temp = true;
/*      */     }
/*  566 */     return Temp;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean TransFindEnteringBasicVariable()
/*      */   {
/*  574 */     double MinM = 1.0E20D;
/*  575 */     double Min = 1.0E20D;
/*  576 */     for (int i = 1; i <= this.TransportationModel.NumSources; i++)
/*      */     {
/*  578 */       for (int j = 1; j <= this.TransportationModel.NumDestinations; j++)
/*      */       {
/*  580 */         if (this.CurrentTransportationTableau.ABasicVariable[i][j] == 0)
/*      */         {
/*  582 */           if (this.CurrentTransportationTableau.CellM[i][j] < MinM)
/*      */           {
/*  584 */             MinM = this.CurrentTransportationTableau.CellM[i][j];
/*  585 */             Min = this.CurrentTransportationTableau.Cell[i][j];
/*  586 */             this.CurrentTransportationTableau.EnteringBasicVariable[1] = i;
/*  587 */             this.CurrentTransportationTableau.EnteringBasicVariable[2] = j;
/*      */           }
/*  589 */           else if ((Math.abs(this.CurrentTransportationTableau.CellM[i][j] - MinM) < 1.0E-4D) && (this.CurrentTransportationTableau.Cell[i][j] < Min))
/*      */           {
/*  591 */             MinM = this.CurrentTransportationTableau.CellM[i][j];
/*  592 */             Min = this.CurrentTransportationTableau.Cell[i][j];
/*  593 */             this.CurrentTransportationTableau.EnteringBasicVariable[1] = i;
/*  594 */             this.CurrentTransportationTableau.EnteringBasicVariable[2] = j;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  599 */     return (MinM < -1.0E-5D) || ((MinM < 1.0E-5D) && (Min < -1.0E-5D));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void TransFindPath()
/*      */   {
/*  607 */     this.CurrentTransportationTableau.Path.Number = 1;
/*  608 */     this.CurrentTransportationTableau.Path.Source[1] = this.CurrentTransportationTableau.EnteringBasicVariable[1];
/*  609 */     this.CurrentTransportationTableau.Path.Destination[1] = this.CurrentTransportationTableau.EnteringBasicVariable[2];
/*  610 */     for (int i = 2; i <= 30; i++)
/*      */     {
/*  612 */       this.CurrentTransportationTableau.Path.Source[i] = 0;
/*  613 */       this.CurrentTransportationTableau.Path.Destination[i] = 0;
/*      */     }
/*      */     do {
/*  616 */       this.CurrentTransportationTableau.Path.Number += 1;
/*  617 */       this.CurrentTransportationTableau.Path.Source[(this.CurrentTransportationTableau.Path.Number + 1)] = 0;
/*  618 */       this.CurrentTransportationTableau.Path.Destination[(this.CurrentTransportationTableau.Path.Number + 1)] = 0;
/*  619 */       if (this.CurrentTransportationTableau.Path.Number % 2 == 0)
/*      */       {
/*  621 */         this.CurrentTransportationTableau.Path.Destination[this.CurrentTransportationTableau.Path.Number] = this.CurrentTransportationTableau.Path.Destination[(this.CurrentTransportationTableau.Path.Number - 1)];
/*  622 */         boolean Found = false;
/*      */         do {
/*  624 */           this.CurrentTransportationTableau.Path.Source[this.CurrentTransportationTableau.Path.Number] += 1;
/*  625 */           if (this.CurrentTransportationTableau.Path.Source[this.CurrentTransportationTableau.Path.Number] == this.CurrentTransportationTableau.Path.Source[(this.CurrentTransportationTableau.Path.Number - 1)])
/*  626 */             this.CurrentTransportationTableau.Path.Source[this.CurrentTransportationTableau.Path.Number] += 1;
/*  627 */           if (this.CurrentTransportationTableau.Path.Source[this.CurrentTransportationTableau.Path.Number] > this.TransportationModel.NumSources) {
/*  628 */             Found = true;
/*  629 */           } else if (this.CurrentTransportationTableau.ABasicVariable[this.CurrentTransportationTableau.Path.Source[this.CurrentTransportationTableau.Path.Number]][this.CurrentTransportationTableau.Path.Destination[this.CurrentTransportationTableau.Path.Number]] != 0)
/*  630 */             Found = true;
/*  631 */         } while (!Found);
/*  632 */         if (this.CurrentTransportationTableau.Path.Source[this.CurrentTransportationTableau.Path.Number] > this.TransportationModel.NumSources) {
/*  633 */           this.CurrentTransportationTableau.Path.Number -= 2;
/*      */         }
/*      */       }
/*      */       else {
/*  637 */         this.CurrentTransportationTableau.Path.Source[this.CurrentTransportationTableau.Path.Number] = this.CurrentTransportationTableau.Path.Source[(this.CurrentTransportationTableau.Path.Number - 1)];
/*  638 */         boolean Found = false;
/*      */         do {
/*  640 */           this.CurrentTransportationTableau.Path.Destination[this.CurrentTransportationTableau.Path.Number] += 1;
/*  641 */           if (this.CurrentTransportationTableau.Path.Destination[this.CurrentTransportationTableau.Path.Number] == this.CurrentTransportationTableau.Path.Destination[(this.CurrentTransportationTableau.Path.Number - 1)])
/*  642 */             this.CurrentTransportationTableau.Path.Destination[this.CurrentTransportationTableau.Path.Number] += 1;
/*  643 */           if (this.CurrentTransportationTableau.Path.Destination[this.CurrentTransportationTableau.Path.Number] > this.TransportationModel.NumDestinations) {
/*  644 */             Found = true;
/*  645 */           } else if ((this.CurrentTransportationTableau.ABasicVariable[this.CurrentTransportationTableau.Path.Source[this.CurrentTransportationTableau.Path.Number]][this.CurrentTransportationTableau.Path.Destination[this.CurrentTransportationTableau.Path.Number]] != 0) || ((this.CurrentTransportationTableau.Path.Source[this.CurrentTransportationTableau.Path.Number] == this.CurrentTransportationTableau.Path.Source[1]) && (this.CurrentTransportationTableau.Path.Destination[this.CurrentTransportationTableau.Path.Number] == this.CurrentTransportationTableau.Path.Destination[1])))
/*  646 */             Found = true;
/*  647 */         } while (!Found);
/*  648 */         if (this.CurrentTransportationTableau.Path.Destination[this.CurrentTransportationTableau.Path.Number] > this.TransportationModel.NumDestinations)
/*  649 */           this.CurrentTransportationTableau.Path.Number -= 2;
/*      */       }
/*  651 */     } while ((this.CurrentTransportationTableau.Path.Source[this.CurrentTransportationTableau.Path.Number] != this.CurrentTransportationTableau.Path.Source[1]) || (this.CurrentTransportationTableau.Path.Destination[this.CurrentTransportationTableau.Path.Number] != this.CurrentTransportationTableau.Path.Destination[1]) || (this.CurrentTransportationTableau.Path.Number <= 2));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void TransFindLeavingBasicVariable()
/*      */   {
/*  659 */     double Min = 1.0E20D;
/*  660 */     for (int i = 2; i <= this.CurrentTransportationTableau.Path.Number - 1; i++)
/*      */     {
/*  662 */       if (i % 2 == 0)
/*      */       {
/*  664 */         if (this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.Path.Source[i]][this.CurrentTransportationTableau.Path.Destination[i]] < Min)
/*      */         {
/*  666 */           Min = this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.Path.Source[i]][this.CurrentTransportationTableau.Path.Destination[i]];
/*  667 */           this.CurrentTransportationTableau.LeavingBasicVariable[1] = this.CurrentTransportationTableau.Path.Source[i];
/*  668 */           this.CurrentTransportationTableau.LeavingBasicVariable[2] = this.CurrentTransportationTableau.Path.Destination[i];
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void TransIterate()
/*      */   {
/*  676 */     int Which = 0;
/*      */     
/*      */ 
/*      */ 
/*  680 */     double Amount = this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.LeavingBasicVariable[1]][this.CurrentTransportationTableau.LeavingBasicVariable[2]];
/*  681 */     for (int i = 1; i <= this.TransportationModel.NumBasicVariables; i++)
/*      */     {
/*  683 */       if ((this.CurrentTransportationTableau.BasicVariable[i][1] == this.CurrentTransportationTableau.LeavingBasicVariable[1]) && (this.CurrentTransportationTableau.BasicVariable[i][2] == this.CurrentTransportationTableau.LeavingBasicVariable[2]))
/*  684 */         Which = i;
/*      */     }
/*  686 */     this.CurrentTransportationTableau.BasicVariable[Which][1] = this.CurrentTransportationTableau.Path.Source[1];
/*  687 */     this.CurrentTransportationTableau.BasicVariable[Which][2] = this.CurrentTransportationTableau.Path.Destination[1];
/*  688 */     this.CurrentTransportationTableau.ABasicVariable[this.CurrentTransportationTableau.LeavingBasicVariable[1]][this.CurrentTransportationTableau.LeavingBasicVariable[2]] = 0;
/*  689 */     this.CurrentTransportationTableau.ABasicVariable[this.CurrentTransportationTableau.Path.Source[1]][this.CurrentTransportationTableau.Path.Destination[2]] = 1;
/*  690 */     for (i = 2; i <= this.CurrentTransportationTableau.Path.Number - 1; i++)
/*      */     {
/*  692 */       if (i % 2 == 1) {
/*  693 */         this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.Path.Source[i]][this.CurrentTransportationTableau.Path.Destination[i]] += Amount;
/*      */       } else
/*  695 */         this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.Path.Source[i]][this.CurrentTransportationTableau.Path.Destination[i]] -= Amount;
/*      */     }
/*  697 */     this.CurrentTransportationTableau.CellM[this.CurrentTransportationTableau.Path.Source[1]][this.CurrentTransportationTableau.Path.Destination[1]] = 0.0D;
/*  698 */     this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.Path.Source[1]][this.CurrentTransportationTableau.Path.Destination[1]] = Amount;
/*      */   }
/*      */   
/*      */ 
/*      */   private void OutputResult()
/*      */   {
/*  704 */     this.output_AutoData = null;this.output_AutoData = new Vector();
/*  705 */     this.autoPrintData = null;this.autoPrintData = new Vector();
/*  706 */     this.output_AutoData = autoGetOutVec("auto");
/*  707 */     this.autoPrintData = autoGetOutVec("print");
/*      */   }
/*      */   
/*      */   private Vector autoGetOutVec(String type)
/*      */   {
/*  712 */     Vector tempV = new Vector();
/*  713 */     this.result_AutoS = "";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  720 */     if (type.equalsIgnoreCase("print")) {
/*  721 */       int cellwidth = 7;
/*  722 */       int dec = 5;
/*  723 */       double Z = 0.0D;
/*  724 */       double ZM = 0.0D;
/*  725 */       String tempS = "";
/*      */       
/*      */ 
/*  728 */       tempS = String.valueOf(String.valueOf(tempS)).concat(String.valueOf(String.valueOf(ConvertStringToFixString(" ", cellwidth, 2))));
/*  729 */       for (int j = 1; j <= this.TransportationModel.NumDestinations; j++)
/*      */       {
/*  731 */         String s = "D".concat(String.valueOf(String.valueOf(j)));
/*      */         
/*  733 */         tempS = String.valueOf(String.valueOf(tempS)).concat(String.valueOf(String.valueOf(ConvertStringToFixString(s, cellwidth, 2))));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  739 */       tempS = String.valueOf(String.valueOf(tempS)).concat(String.valueOf(String.valueOf(ConvertStringToFixString("Supply", cellwidth + 4, 2))));
/*  740 */       tempV.addElement(tempS);
/*  741 */       for (int i = 1; i <= this.TransportationModel.NumSources; i++)
/*      */       {
/*  743 */         tempS = "";
/*      */         
/*  745 */         String s = "S".concat(String.valueOf(String.valueOf(i)));
/*      */         
/*  747 */         tempS = String.valueOf(String.valueOf(tempS)).concat(String.valueOf(String.valueOf(ConvertStringToFixString(s, cellwidth, 2))));
/*  748 */         for (j = 1; j <= this.TransportationModel.NumDestinations; j++)
/*      */         {
/*  750 */           if ((this.CurrentTransportationTableau.ABasicVariable[i][j] != 0) && (this.CurrentTransportationTableau.Cell[i][j] > 1.0E-5D))
/*      */           {
/*      */ 
/*  753 */             tempS = String.valueOf(String.valueOf(tempS)).concat(String.valueOf(String.valueOf(ConvertDoubleToFixString(this.CurrentTransportationTableau.Cell[i][j], cellwidth, dec, 2, false))));
/*  754 */             Z += this.CurrentTransportationTableau.Cell[i][j] * this.TransportationModel.Cost[i][j];
/*  755 */             ZM += this.CurrentTransportationTableau.Cell[i][j] * this.TransportationModel.CostM[i][j];
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  760 */             tempS = String.valueOf(String.valueOf(tempS)).concat(String.valueOf(String.valueOf(ConvertStringToFixString(" ", cellwidth, 2))));
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  765 */         tempS = String.valueOf(String.valueOf(tempS)).concat(String.valueOf(String.valueOf(ConvertDoubleToFixString(this.TransportationModel.Supply[i], cellwidth + 5, dec, 2, false))));
/*  766 */         System.out.println(tempS);
/*  767 */         tempV.addElement(tempS);
/*      */       }
/*      */       
/*      */ 
/*  771 */       this.result_AutoS = String.valueOf(String.valueOf(this.result_AutoS)).concat(String.valueOf(String.valueOf(ConvertStringToFixString("Demand", cellwidth, 2))));
/*  772 */       for (j = 1; j <= this.TransportationModel.NumDestinations; j++)
/*      */       {
/*  774 */         this.result_AutoS = String.valueOf(String.valueOf(this.result_AutoS)).concat(String.valueOf(String.valueOf(ConvertDoubleToFixString(this.TransportationModel.Demand[j], cellwidth, dec, 2, false))));
/*      */       }
/*      */       
/*  777 */       this.result_AutoS = String.valueOf(String.valueOf(this.result_AutoS)).concat(String.valueOf(String.valueOf(ConvertStringToFixString("   Cost is ", cellwidth, 2))));
/*  778 */       System.out.print(ConvertDoubleToFixString(Z, cellwidth - 8, dec, 2, false));
/*  779 */       this.result_AutoS = String.valueOf(String.valueOf(this.result_AutoS)).concat(String.valueOf(String.valueOf(ConvertDoubleToFixString(Z, cellwidth - 8, dec, 2, false))));
/*  780 */       System.out.println(this.result_AutoS);
/*      */       
/*  782 */       tempV.addElement(this.result_AutoS);
/*      */     }
/*      */     else {
/*  785 */       int cellwidth = 6;
/*  786 */       int dec = 5;
/*  787 */       double Z = 0.0D;
/*  788 */       double ZM = 0.0D;
/*  789 */       String tempS = "";
/*      */       
/*      */ 
/*  792 */       tempS = String.valueOf(String.valueOf(tempS)).concat(String.valueOf(String.valueOf(ConvertStringToFixString(" ", cellwidth, 2))));
/*  793 */       for (int j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/*  794 */         String s = "D".concat(String.valueOf(String.valueOf(j)));
/*  795 */         tempS = String.valueOf(String.valueOf(tempS)).concat(String.valueOf(String.valueOf(ConvertStringToFixString(s, cellwidth, 2))));
/*      */       }
/*      */       
/*  798 */       tempS = String.valueOf(String.valueOf(tempS)).concat(String.valueOf(String.valueOf(ConvertStringToFixString("Supply", cellwidth + 4, 2))));
/*  799 */       System.out.println(tempS);
/*  800 */       tempV.addElement(tempS);
/*  801 */       for (int i = 1; i <= this.TransportationModel.NumSources; i++) {
/*  802 */         tempS = "";
/*  803 */         String s = "S".concat(String.valueOf(String.valueOf(i)));
/*  804 */         tempS = String.valueOf(String.valueOf(tempS)).concat(String.valueOf(String.valueOf(ConvertStringToFixString(s, cellwidth, 2))));
/*  805 */         for (j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/*  806 */           if ((this.CurrentTransportationTableau.ABasicVariable[i][j] != 0) && (this.CurrentTransportationTableau.Cell[i][j] > 1.0E-5D))
/*      */           {
/*  808 */             tempS = String.valueOf(String.valueOf(tempS)).concat(String.valueOf(String.valueOf(ConvertDoubleToFixString(this.CurrentTransportationTableau.Cell[i][j], cellwidth, dec, 2, false))));
/*      */             
/*      */ 
/*  811 */             Z += this.CurrentTransportationTableau.Cell[i][j] * this.TransportationModel.Cost[i][j];
/*      */             
/*      */ 
/*  814 */             ZM += this.CurrentTransportationTableau.Cell[i][j] * this.TransportationModel.CostM[i][j];
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  819 */             tempS = String.valueOf(String.valueOf(tempS)).concat(String.valueOf(String.valueOf(ConvertStringToFixString(" ", cellwidth, 2))));
/*      */           }
/*      */         }
/*  822 */         tempS = String.valueOf(String.valueOf(tempS)).concat(String.valueOf(String.valueOf(ConvertDoubleToFixString(this.TransportationModel.Supply[i], cellwidth, dec, 2, false))));
/*      */         
/*      */ 
/*  825 */         tempV.addElement(tempS);
/*  826 */         System.out.println(tempS);
/*      */       }
/*  828 */       this.result_AutoS = String.valueOf(String.valueOf(this.result_AutoS)).concat(String.valueOf(String.valueOf(ConvertStringToFixString("Demand", cellwidth, 2))));
/*  829 */       for (j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/*  830 */         this.result_AutoS = String.valueOf(String.valueOf(this.result_AutoS)).concat(String.valueOf(String.valueOf(ConvertDoubleToFixString(this.TransportationModel.Demand[j], cellwidth, dec, 2, false))));
/*      */       }
/*      */       
/*      */ 
/*  834 */       this.result_AutoS = String.valueOf(String.valueOf(this.result_AutoS)).concat(String.valueOf(String.valueOf(ConvertStringToFixString("Cost is", cellwidth + 2, 2))));
/*  835 */       this.result_AutoS = String.valueOf(String.valueOf(this.result_AutoS)).concat(String.valueOf(String.valueOf(ConvertDoubleToFixString(Z, cellwidth - 1, dec, 2, false))));
/*  836 */       tempV.addElement(this.result_AutoS);
/*  837 */       System.out.println(this.result_AutoS);
/*      */     }
/*      */     
/*  840 */     return tempV;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void TransCalculateRowDifference(int Row)
/*      */   {
/*  850 */     double Min = 1.0E15D;
/*  851 */     double Min2 = 1.0E15D;
/*  852 */     double MinM = 1.0E15D;
/*  853 */     double Min2M = 1.0E15D;
/*  854 */     int MinCell = 0;
/*  855 */     int MinCell2 = 0;
/*  856 */     for (int i = 1; i <= this.TransportationModel.NumDestinations; i++) {
/*  857 */       if (this.CurrentTransportationTableau.EliminatedDestination[i] == 0) {
/*  858 */         if (this.TransportationModel.CostM[Row][i] < MinM) {
/*  859 */           MinM = this.TransportationModel.CostM[Row][i];
/*  860 */           Min = this.TransportationModel.Cost[Row][i];
/*  861 */           MinCell = i;
/*      */         }
/*  863 */         if ((Math.abs(this.TransportationModel.CostM[Row][i] - MinM) < 0.001D) && (this.TransportationModel.Cost[Row][i] < Min)) {
/*  864 */           Min = this.TransportationModel.Cost[Row][i];
/*  865 */           MinCell = i;
/*      */         }
/*      */       }
/*      */     }
/*  869 */     for (i = 1; i <= this.TransportationModel.NumDestinations; i++)
/*  870 */       if ((this.CurrentTransportationTableau.EliminatedDestination[i] == 0) && (i != MinCell)) {
/*  871 */         if (this.TransportationModel.CostM[Row][i] < Min2M) {
/*  872 */           Min2M = this.TransportationModel.CostM[Row][i];
/*  873 */           Min2 = this.TransportationModel.Cost[Row][i];
/*  874 */           MinCell2 = i;
/*      */         }
/*  876 */         if ((Math.abs(this.TransportationModel.CostM[Row][i] - Min2M) < 0.001D) && (this.TransportationModel.Cost[Row][i] < Min2)) {
/*  877 */           Min2 = this.TransportationModel.Cost[Row][i];
/*  878 */           MinCell2 = i; } }
/*      */     double DiffM;
/*      */     double Diff;
/*      */     double DiffM;
/*  882 */     if ((MinCell2 == 0) || (MinCell == 0)) {
/*  883 */       double Diff = 0.0D;
/*  884 */       DiffM = 0.0D;
/*      */     }
/*      */     else {
/*  887 */       Diff = Min2 - Min;
/*  888 */       DiffM = Min2M - MinM;
/*      */     }
/*  890 */     if (Math.abs(DiffM) < 0.001D) {
/*  891 */       DiffM = 0.0D;
/*      */     }
/*  893 */     this.VogelsRowDiff[Row] = Diff;
/*  894 */     this.VogelsRowDiffM[Row] = DiffM;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void TransCalculateColumnDifference(int Column)
/*      */   {
/*  903 */     double Min = 1.0E15D;
/*  904 */     double Min2 = 1.0E15D;
/*  905 */     double MinM = 1.0E15D;
/*  906 */     double Min2M = 1.0E15D;
/*  907 */     int MinCell = 0;
/*  908 */     int MinCell2 = 0;
/*  909 */     for (int i = 1; i <= this.TransportationModel.NumSources; i++) {
/*  910 */       if (this.CurrentTransportationTableau.EliminatedSource[i] == 0) {
/*  911 */         if (this.TransportationModel.CostM[i][Column] < MinM) {
/*  912 */           MinM = this.TransportationModel.CostM[i][Column];
/*  913 */           Min = this.TransportationModel.Cost[i][Column];
/*  914 */           MinCell = i;
/*      */         }
/*  916 */         if ((Math.abs(this.TransportationModel.CostM[i][Column] - MinM) < 0.001D) && (this.TransportationModel.Cost[i][Column] < Min)) {
/*  917 */           Min = this.TransportationModel.Cost[i][Column];
/*  918 */           MinCell = i;
/*      */         }
/*      */       }
/*      */     }
/*  922 */     for (i = 1; i <= this.TransportationModel.NumSources; i++)
/*  923 */       if ((this.CurrentTransportationTableau.EliminatedSource[i] == 0) && (i != MinCell)) {
/*  924 */         if (this.TransportationModel.CostM[i][Column] < Min2M) {
/*  925 */           Min2M = this.TransportationModel.CostM[i][Column];
/*  926 */           Min2 = this.TransportationModel.Cost[i][Column];
/*  927 */           MinCell2 = i;
/*      */         }
/*  929 */         if ((Math.abs(this.TransportationModel.CostM[i][Column] - Min2M) < 0.001D) && (this.TransportationModel.Cost[i][Column] < Min2)) {
/*  930 */           Min2 = this.TransportationModel.Cost[i][Column];
/*  931 */           MinCell2 = i; } }
/*      */     double DiffM;
/*      */     double Diff;
/*      */     double DiffM;
/*  935 */     if ((MinCell2 == 0) || (MinCell == 0)) {
/*  936 */       double Diff = 0.0D;
/*  937 */       DiffM = 0.0D;
/*      */     }
/*      */     else {
/*  940 */       Diff = Min2 - Min;
/*  941 */       DiffM = Min2M - MinM;
/*      */     }
/*  943 */     if (Math.abs(DiffM) < 0.001D) {
/*  944 */       DiffM = 0.0D;
/*      */     }
/*  946 */     this.VogelsColumnDiff[Column] = Diff;
/*  947 */     this.VogelsColumnDiffM[Column] = DiffM;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void TransCalculateRussellU(int Source)
/*      */   {
/*  954 */     this.RussellU[Source] = 0.0D;
/*  955 */     this.RussellUBigM[Source] = 0.0D;
/*  956 */     for (int i = 1; i <= this.TransportationModel.NumDestinations; i++) {
/*  957 */       if (this.CurrentTransportationTableau.EliminatedDestination[i] == 0) {
/*  958 */         if (this.TransportationModel.CostM[Source][i] > this.RussellUBigM[Source] + 0.01D) {
/*  959 */           this.RussellUBigM[Source] = this.TransportationModel.CostM[Source][i];
/*  960 */           this.RussellU[Source] = this.TransportationModel.Cost[Source][i];
/*      */         }
/*  962 */         if ((this.TransportationModel.CostM[Source][i] > this.RussellUBigM[Source] - 0.01D) && (this.TransportationModel.Cost[Source][i] > this.RussellU[Source] + 0.01D)) {
/*  963 */           this.RussellU[Source] = this.TransportationModel.Cost[Source][i];
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void TransCalculateRussellV(int Destination)
/*      */   {
/*  972 */     this.RussellV[Destination] = 0.0D;
/*  973 */     this.RussellVBigM[Destination] = 0.0D;
/*  974 */     for (int i = 1; i <= this.TransportationModel.NumSources; i++) {
/*  975 */       if (this.CurrentTransportationTableau.EliminatedSource[i] == 0) {
/*  976 */         if (this.TransportationModel.CostM[i][Destination] > this.RussellVBigM[Destination] + 0.01D) {
/*  977 */           this.RussellVBigM[Destination] = this.TransportationModel.CostM[i][Destination];
/*  978 */           this.RussellV[Destination] = this.TransportationModel.Cost[i][Destination];
/*      */         }
/*  980 */         if ((this.TransportationModel.CostM[i][Destination] > this.RussellVBigM[Destination] - 0.01D) && (this.TransportationModel.Cost[i][Destination] > this.RussellV[Destination] + 0.01D)) {
/*  981 */           this.RussellV[Destination] = this.TransportationModel.Cost[i][Destination];
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean doWork(IOROperation op, IORTPProcessController.TPData data) {
/*  988 */     boolean success = doWork(op);
/*      */     
/*  990 */     if (success) {
/*  991 */       getData(data);
/*      */     }
/*  993 */     return success;
/*      */   }
/*      */   
/*      */   public void getData(IORTPProcessController.TPData d)
/*      */   {
/*  998 */     d.method_option = this.TransIBFSMethod;
/*      */     
/* 1000 */     d.max_num_supply = 7;
/* 1001 */     d.max_num_demand = 7;
/*      */     
/* 1003 */     d.num_supply = this.TransportationModel.NumSources;
/* 1004 */     d.num_demand = this.TransportationModel.NumDestinations;
/*      */     
/* 1006 */     if (d.cost == null) d.cost = new double[15][15];
/* 1007 */     if (d.M_cost == null) { d.M_cost = new double[15][15];
/*      */     }
/* 1009 */     if (d.supply == null) d.supply = new int[15];
/* 1010 */     if (d.demand == null) { d.demand = new int[15];
/*      */     }
/* 1012 */     if (d.remain_supply == null) d.remain_supply = new double[15];
/* 1013 */     if (d.remain_demand == null) { d.remain_demand = new double[15];
/*      */     }
/* 1015 */     if (d.u == null) d.u = new double[15];
/* 1016 */     if (d.M_u == null) d.M_u = new double[15];
/* 1017 */     if (d.v == null) d.v = new double[15];
/* 1018 */     if (d.M_v == null) { d.M_v = new double[15];
/*      */     }
/* 1020 */     if (d.is_basic == null) { d.is_basic = new boolean[15][15];
/*      */     }
/*      */     
/* 1023 */     if (d.is_entering_basic == null) { d.is_entering_basic = new boolean[15][15];
/*      */     }
/* 1025 */     if (d.flow == null) { d.flow = new double[15][15];
/*      */     }
/* 1027 */     if (d.cuv == null) d.cuv = new double[15][15];
/* 1028 */     if (d.M_cuv == null) { d.M_cuv = new double[15][15];
/*      */     }
/* 1030 */     if (d.is_deleted == null) { d.is_deleted = new boolean[15][15];
/*      */     }
/* 1032 */     if (d.loop == null) { d.loop = new Vector();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1037 */     for (int i = 0; i < this.TransportationModel.NumSources; i++) {
/* 1038 */       for (int j = 0; j < this.TransportationModel.NumDestinations; j++) {
/* 1039 */         d.cost[i][j] = this.TransportationModel.Cost[(i + 1)][(j + 1)];
/* 1040 */         d.M_cost[i][j] = this.TransportationModel.CostM[(i + 1)][(j + 1)];
/*      */       }
/*      */     }
/*      */     
/* 1044 */     for (i = 0; i < this.TransportationModel.NumSources; i++) {
/* 1045 */       d.supply[i] = this.TransportationModel.Supply[(i + 1)];
/*      */     }
/*      */     
/* 1048 */     for (i = 0; i < this.TransportationModel.NumDestinations; i++) {
/* 1049 */       d.demand[i] = this.TransportationModel.Demand[(i + 1)];
/*      */     }
/*      */     
/* 1052 */     for (i = 0; i < this.TransportationModel.NumSources; i++) {
/* 1053 */       for (int j = 0; j < this.TransportationModel.NumDestinations; j++) {
/* 1054 */         if ((this.CurrentTransportationTableau.EliminatedSource[(i + 1)] != 0) || (this.CurrentTransportationTableau.EliminatedDestination[(j + 1)] != 0)) {
/* 1055 */           d.is_deleted[i][j] = 1;
/*      */         } else {
/* 1057 */           d.is_deleted[i][j] = 0;
/*      */         }
/*      */       }
/*      */     }
/* 1061 */     for (i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1062 */       for (int j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1063 */         d.is_basic[(i - 1)][(j - 1)] = TransABasicVariable(i, j);
/*      */       }
/*      */     }
/*      */     
/* 1067 */     d.is_entering_basic = new boolean[15][15];
/* 1068 */     if ((this.CurrentTransportationTableau.EnteringBasicVariable[1] > 0) && (this.CurrentTransportationTableau.EnteringBasicVariable[2] > 0)) {
/* 1069 */       d.is_entering_basic[(this.CurrentTransportationTableau.EnteringBasicVariable[1] - 1)][(this.CurrentTransportationTableau.EnteringBasicVariable[2] - 1)] = 1;
/*      */     }
/* 1071 */     for (i = 1; i <= 15; i++) {
/* 1072 */       for (int j = 1; j <= 15; j++) {
/* 1073 */         d.flow[(i - 1)][(j - 1)] = this.CurrentTransportationTableau.Cell[i][j];
/*      */       }
/*      */     }
/*      */     
/* 1077 */     if (this.CommandNumber == 3) {
/* 1078 */       if (this.TransIBFSMethod == 2) {
/* 1079 */         for (i = 0; i < this.TransportationModel.NumSources; i++) {
/* 1080 */           d.u[i] = this.RussellU[(i + 1)];
/* 1081 */           d.M_u[i] = this.RussellUBigM[(i + 1)];
/*      */         }
/* 1083 */         for (i = 0; i < this.TransportationModel.NumDestinations; i++) {
/* 1084 */           d.v[i] = this.RussellV[(i + 1)];
/* 1085 */           d.M_v[i] = this.RussellVBigM[(i + 1)];
/*      */         }
/* 1087 */         for (i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1088 */           for (int j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1089 */             d.cuv[(i - 1)][(j - 1)] = this.RussellCell[i][j];
/* 1090 */             d.M_cuv[(i - 1)][(j - 1)] = this.RussellCellBigM[i][j];
/*      */           }
/*      */         }
/*      */       }
/* 1094 */       if (this.TransIBFSMethod == 1) {
/* 1095 */         for (i = 0; i < this.TransportationModel.NumSources; i++) {
/* 1096 */           d.u[i] = this.VogelsRowDiff[(i + 1)];
/* 1097 */           d.M_u[i] = this.VogelsRowDiffM[(i + 1)];
/*      */         }
/* 1099 */         for (i = 0; i < this.TransportationModel.NumDestinations; i++) {
/* 1100 */           d.v[i] = this.VogelsColumnDiff[(i + 1)];
/* 1101 */           d.M_v[i] = this.VogelsColumnDiffM[(i + 1)];
/*      */         }
/*      */       }
/*      */       
/* 1105 */       for (i = 0; i < this.TransportationModel.NumSources; i++) {
/* 1106 */         d.u[i] = 0.0D;
/* 1107 */         d.M_u[i] = 0.0D;
/*      */       }
/* 1109 */       for (i = 0; i < this.TransportationModel.NumDestinations; i++) {
/* 1110 */         d.v[i] = 0.0D;
/* 1111 */         d.M_v[i] = 0.0D;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1116 */     for (i = 0; i < this.TransportationModel.NumSources; i++) {
/* 1117 */       d.u[i] = this.CurrentTransportationTableau.U[(i + 1)];
/* 1118 */       d.M_u[i] = this.CurrentTransportationTableau.UM[(i + 1)];
/*      */     }
/* 1120 */     for (i = 0; i < this.TransportationModel.NumDestinations; i++) {
/* 1121 */       d.v[i] = this.CurrentTransportationTableau.V[(i + 1)];
/* 1122 */       d.M_v[i] = this.CurrentTransportationTableau.VM[(i + 1)];
/*      */     }
/* 1124 */     for (i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1125 */       for (int j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1126 */         d.cuv[(i - 1)][(j - 1)] = this.CurrentTransportationTableau.Cell[i][j];
/* 1127 */         d.M_cuv[(i - 1)][(j - 1)] = this.CurrentTransportationTableau.CellM[i][j];
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1132 */     for (i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1133 */       double Temp = this.TransportationModel.Supply[i];
/* 1134 */       for (int k = 1; k <= this.TransportationModel.NumBasicVariables; k++) {
/* 1135 */         if ((this.CurrentTransportationTableau.BasicVariable[k][1] == i) && (this.CurrentTransportationTableau.BasicVariable[k][2] != 0))
/* 1136 */           Temp -= this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.BasicVariable[k][1]][this.CurrentTransportationTableau.BasicVariable[k][2]];
/*      */       }
/* 1138 */       d.remain_supply[(i - 1)] = Temp;
/*      */     }
/* 1140 */     for (int j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1141 */       double Temp = this.TransportationModel.Demand[j];
/* 1142 */       for (int k = 1; k <= this.TransportationModel.NumBasicVariables; k++) {
/* 1143 */         if ((this.CurrentTransportationTableau.BasicVariable[k][1] != 0) && (this.CurrentTransportationTableau.BasicVariable[k][2] == j))
/* 1144 */           Temp -= this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.BasicVariable[k][1]][this.CurrentTransportationTableau.BasicVariable[k][2]];
/*      */       }
/* 1146 */       d.remain_demand[(j - 1)] = Temp;
/*      */     }
/*      */     
/* 1149 */     d.loop.removeAllElements();
/* 1150 */     for (i = 1; i <= this.CurrentTransportationTableau.Path.Number; i++) {
/* 1151 */       d.loop.addElement(new Point(this.CurrentTransportationTableau.Path.Source[i] - 1, this.CurrentTransportationTableau.Path.Destination[i] - 1));
/*      */     }
/*      */     
/* 1154 */     d.M_z = 0.0D;
/* 1155 */     d.z = 0.0D;
/* 1156 */     for (i = 1; i <= this.TransportationModel.NumBasicVariables; i++) {
/* 1157 */       if ((this.CurrentTransportationTableau.BasicVariable[i][1] != 0) && (this.CurrentTransportationTableau.BasicVariable[i][2] != 0)) {
/* 1158 */         d.M_z += this.TransportationModel.CostM[this.CurrentTransportationTableau.BasicVariable[i][1]][this.CurrentTransportationTableau.BasicVariable[i][2]] * this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.BasicVariable[i][1]][this.CurrentTransportationTableau.BasicVariable[i][2]];
/* 1159 */         d.z += this.TransportationModel.Cost[this.CurrentTransportationTableau.BasicVariable[i][1]][this.CurrentTransportationTableau.BasicVariable[i][2]] * this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.BasicVariable[i][1]][this.CurrentTransportationTableau.BasicVariable[i][2]];
/*      */       }
/*      */     }
/*      */     
/* 1163 */     if (this.output_AutoData != null) {
/* 1164 */       d.outputV = autoGetOutVec("auto");
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean doWork(IOROperation op)
/*      */   {
/* 1170 */     Vector p = op.parameters;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1177 */     int selRow = 1;
/* 1178 */     int selColumn = 0;
/*      */     int i;
/* 1180 */     int[] d1; switch (op.operation_code) {
/*      */     case 20101: 
/* 1182 */       this.CommandNumber = 3;
/* 1183 */       this.CurrentTransportationTableau = new ORSolverBase.TransportationTableauType(this);
/*      */       
/* 1185 */       this.TransportationModel.NumSources = ((Integer)p.elementAt(0)).intValue();
/*      */       
/* 1187 */       this.TransportationModel.NumDestinations = ((Integer)p.elementAt(1)).intValue();
/* 1188 */       this.TransportationModel.NumBasicVariables = (this.TransportationModel.NumSources + this.TransportationModel.NumDestinations - 1);
/*      */       
/* 1190 */       double[][] d2 = (double[][])p.elementAt(2);
/* 1191 */       for (i = 0; i < this.TransportationModel.NumSources; i++) {
/* 1192 */         for (int j = 0; j < this.TransportationModel.NumDestinations; j++) {
/* 1193 */           this.TransportationModel.Cost[(i + 1)][(j + 1)] = d2[i][j];
/*      */         }
/*      */       }
/*      */       
/* 1197 */       d2 = (double[][])p.elementAt(3);
/* 1198 */       for (i = 0; i < this.TransportationModel.NumSources; i++) {
/* 1199 */         for (int j = 0; j < this.TransportationModel.NumDestinations; j++) {
/* 1200 */           this.TransportationModel.CostM[(i + 1)][(j + 1)] = d2[i][j];
/*      */         }
/*      */       }
/*      */       
/* 1204 */       d1 = (int[])p.elementAt(4);
/* 1205 */       for (i = 0; i < this.TransportationModel.NumSources; i++) {
/* 1206 */         this.TransportationModel.Supply[(i + 1)] = d1[i];
/*      */       }
/*      */       
/* 1209 */       d1 = (int[])p.elementAt(5);
/* 1210 */       for (i = 0; i < this.TransportationModel.NumDestinations;) {
/* 1211 */         this.TransportationModel.Demand[(i + 1)] = d1[i];i++; continue;
/*      */         
/*      */ 
/*      */ 
/* 1215 */         DoAuto();
/* 1216 */         break;
/*      */         
/*      */ 
/* 1219 */         this.TransIBFSMethod = ((Integer)p.elementAt(0)).intValue();
/*      */         
/* 1221 */         if (this.TransIBFSMethod == 1) {
/* 1222 */           for (int i = 1; i <= this.TransportationModel.NumDestinations; i++) {
/* 1223 */             if (this.CurrentTransportationTableau.EliminatedDestination[i] == 0)
/* 1224 */               TransCalculateColumnDifference(i);
/*      */           }
/* 1226 */           for (i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1227 */             if (this.CurrentTransportationTableau.EliminatedSource[i] == 0)
/* 1228 */               TransCalculateRowDifference(i);
/*      */           }
/*      */         }
/* 1231 */         if (this.TransIBFSMethod == 2) {
/* 1232 */           for (i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1233 */             if (this.CurrentTransportationTableau.EliminatedSource[i] == 0)
/* 1234 */               TransCalculateRussellU(i);
/*      */           }
/* 1236 */           for (i = 1; i <= this.TransportationModel.NumDestinations; i++) {
/* 1237 */             if (this.CurrentTransportationTableau.EliminatedDestination[i] == 0)
/* 1238 */               TransCalculateRussellV(i);
/*      */           }
/* 1240 */           for (i = 1; i <= this.TransportationModel.NumSources;) {
/* 1241 */             if (this.CurrentTransportationTableau.EliminatedSource[i] == 0) {
/* 1242 */               for (int j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1243 */                 if (this.CurrentTransportationTableau.EliminatedDestination[j] == 0) {
/* 1244 */                   this.RussellCell[i][j] = (this.TransportationModel.Cost[i][j] - this.RussellU[i] - this.RussellV[j]);
/* 1245 */                   this.RussellCellBigM[i][j] = (this.TransportationModel.CostM[i][j] - this.RussellUBigM[i] - this.RussellVBigM[j]);
/*      */                 }
/*      */               }
/*      */             }
/* 1240 */             i++; continue;
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1253 */             this.CommandNumber = 3;
/* 1254 */             InitializeTransportationTableau();
/* 1255 */             setBookmark();
/* 1256 */             this.isFirstIteration = true;
/* 1257 */             break;
/*      */             
/* 1259 */             this.CommandNumber = 3;
/*      */             
/* 1261 */             this.CurrentTransportationTableau.Marker[1] = (((Integer)p.elementAt(0)).intValue() + 1);
/*      */             
/* 1263 */             this.CurrentTransportationTableau.Marker[2] = (((Integer)p.elementAt(1)).intValue() + 1);
/* 1264 */             double delta = ((Double)p.elementAt(2)).doubleValue();
/* 1265 */             if ((this.CurrentTransportationTableau.EliminatedSource[this.CurrentTransportationTableau.Marker[1]] != 0) || (this.CurrentTransportationTableau.EliminatedDestination[this.CurrentTransportationTableau.Marker[2]] != 0)) {
/* 1266 */               this.errInfo = "Invalid selection";
/* 1267 */               return false;
/*      */             }
/* 1269 */             if (this.isFirstIteration) {
/* 1270 */               if (this.TransIBFSMethod == 1)
/*      */               {
/* 1272 */                 double maxDiffM = 0.0D;
/* 1273 */                 double maxDiff = 0.0D;
/* 1274 */                 selRow = 1;
/* 1275 */                 selColumn = 0;
/* 1276 */                 for (int i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1277 */                   if ((maxDiffM < this.VogelsRowDiffM[i] - 0.01D) || ((maxDiffM < this.VogelsRowDiffM[i] + 0.01D) && (maxDiff < this.VogelsRowDiff[i] - 0.01D))) {
/* 1278 */                     maxDiffM = this.VogelsRowDiffM[i];
/* 1279 */                     maxDiff = this.VogelsRowDiff[i];
/* 1280 */                     selRow = i;
/*      */                   }
/*      */                 }
/* 1283 */                 for (int j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1284 */                   if ((maxDiffM < this.VogelsColumnDiffM[j] - 0.01D) || ((maxDiffM < this.VogelsColumnDiffM[j] + 0.01D) && (maxDiff < this.VogelsColumnDiff[j] - 0.01D))) {
/* 1285 */                     maxDiffM = this.VogelsColumnDiffM[j];
/* 1286 */                     maxDiff = this.VogelsColumnDiff[j];
/* 1287 */                     selColumn = j;
/*      */                   }
/*      */                 }
/* 1290 */                 double minCostM = 1.0E10D;
/* 1291 */                 double minCost = 1.0E10D;
/* 1292 */                 if (selColumn == 0) {
/* 1293 */                   for (j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1294 */                     if ((minCostM > this.TransportationModel.CostM[selRow][j] + 0.01D) || ((minCostM > this.TransportationModel.CostM[selRow][j] - 0.01D) && (minCost > this.TransportationModel.Cost[selRow][j] + 0.01D))) {
/* 1295 */                       minCostM = this.TransportationModel.CostM[selRow][j];
/* 1296 */                       minCost = this.TransportationModel.Cost[selRow][j];
/* 1297 */                       selColumn = j;
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/* 1302 */                 for (i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1303 */                   if ((minCostM > this.TransportationModel.CostM[i][selColumn] + 0.01D) || ((minCostM > this.TransportationModel.CostM[i][selColumn] - 0.01D) && (minCost > this.TransportationModel.Cost[i][selColumn] + 0.01D))) {
/* 1304 */                     minCostM = this.TransportationModel.CostM[i][selColumn];
/* 1305 */                     minCost = this.TransportationModel.Cost[i][selColumn];
/* 1306 */                     selRow = i;
/*      */                   }
/*      */                 }
/*      */                 
/*      */ 
/* 1311 */                 if ((this.CurrentTransportationTableau.Marker[1] != selRow) || (this.CurrentTransportationTableau.Marker[2] != selColumn)) {
/* 1312 */                   this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1313 */                   return false;
/*      */                 }
/*      */               }
/* 1316 */               else if (this.TransIBFSMethod == 2) {
/* 1317 */                 double minCell = 1.0E10D;double minCellM = 1.0E10D;
/* 1318 */                 for (int i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1319 */                   for (int j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1320 */                     if ((minCellM > this.RussellCellBigM[i][j] + 0.01D) || ((minCellM > this.RussellCellBigM[i][j] - 0.01D) && (minCell > this.RussellCell[i][j] - 0.01D))) {
/* 1321 */                       minCellM = this.RussellCellBigM[i][j];
/* 1322 */                       minCell = this.RussellCell[i][j];
/* 1323 */                       selRow = i;
/* 1324 */                       selColumn = j;
/*      */                     }
/*      */                   }
/*      */                 }
/* 1328 */                 if ((Math.abs(this.RussellCellBigM[this.CurrentTransportationTableau.Marker[1]][this.CurrentTransportationTableau.Marker[2]] - this.RussellCellBigM[selRow][selColumn]) > 0.01D) || (Math.abs(this.RussellCell[this.CurrentTransportationTableau.Marker[1]][this.CurrentTransportationTableau.Marker[2]] - this.RussellCell[selRow][selColumn]) > 0.01D)) {
/* 1329 */                   this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1330 */                   return false;
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/* 1335 */               double remainSupply = this.TransportationModel.Supply[this.CurrentTransportationTableau.Marker[1]];
/* 1336 */               for (int k = 1; k <= this.TransportationModel.NumBasicVariables; k++) {
/* 1337 */                 if ((this.CurrentTransportationTableau.BasicVariable[k][1] == this.CurrentTransportationTableau.Marker[1]) && (this.CurrentTransportationTableau.BasicVariable[k][2] != 0))
/* 1338 */                   remainSupply -= this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.BasicVariable[k][1]][this.CurrentTransportationTableau.BasicVariable[k][2]];
/*      */               }
/* 1340 */               double remainDemand = this.TransportationModel.Demand[this.CurrentTransportationTableau.Marker[2]];
/* 1341 */               for (k = 1; k <= this.TransportationModel.NumBasicVariables; k++) {
/* 1342 */                 if ((this.CurrentTransportationTableau.BasicVariable[k][1] != 0) && (this.CurrentTransportationTableau.BasicVariable[k][2] == this.CurrentTransportationTableau.Marker[2]))
/* 1343 */                   remainDemand -= this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.BasicVariable[k][1]][this.CurrentTransportationTableau.BasicVariable[k][2]];
/*      */               }
/* 1345 */               if (Math.abs(delta - Math.min(remainSupply, remainDemand)) > 0.001D) {
/* 1346 */                 this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1347 */                 return false;
/*      */               }
/*      */             }
/*      */             
/* 1351 */             for (int i = 1; i <= this.TransportationModel.NumBasicVariables; i++) {
/* 1352 */               if ((this.CurrentTransportationTableau.BasicVariable[i][1] == 0) || (this.CurrentTransportationTableau.BasicVariable[i][2] == 0))
/*      */                 break;
/*      */             }
/* 1355 */             this.CurrentTransportationTableau.BasicVariable[i][1] = this.CurrentTransportationTableau.Marker[1];
/* 1356 */             this.CurrentTransportationTableau.BasicVariable[i][2] = this.CurrentTransportationTableau.Marker[2];
/* 1357 */             this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.Marker[1]][this.CurrentTransportationTableau.Marker[2]] = delta;
/* 1358 */             break;
/*      */             
/*      */ 
/* 1361 */             this.CommandNumber = 3;
/*      */             
/* 1363 */             i = ((Integer)p.elementAt(0)).intValue() + 1;
/* 1364 */             if (this.isFirstIteration)
/*      */             {
/*      */ 
/* 1367 */               double remainSupply = this.TransportationModel.Supply[this.CurrentTransportationTableau.Marker[1]];
/* 1368 */               for (int k = 1; k <= this.TransportationModel.NumBasicVariables; k++) {
/* 1369 */                 if ((this.CurrentTransportationTableau.BasicVariable[k][1] == this.CurrentTransportationTableau.Marker[1]) && (this.CurrentTransportationTableau.BasicVariable[k][2] != 0))
/* 1370 */                   remainSupply -= this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.BasicVariable[k][1]][this.CurrentTransportationTableau.BasicVariable[k][2]];
/*      */               }
/* 1372 */               double remainDemand = this.TransportationModel.Demand[this.CurrentTransportationTableau.Marker[2]];
/* 1373 */               for (k = 1; k <= this.TransportationModel.NumBasicVariables; k++) {
/* 1374 */                 if ((this.CurrentTransportationTableau.BasicVariable[k][1] != 0) && (this.CurrentTransportationTableau.BasicVariable[k][2] == this.CurrentTransportationTableau.Marker[2]))
/* 1375 */                   remainDemand -= this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.BasicVariable[k][1]][this.CurrentTransportationTableau.BasicVariable[k][2]];
/*      */               }
/* 1377 */               boolean Correct = true;
/* 1378 */               if ((op.operation_code == 20202) && (Math.abs(remainSupply) > 0.01D)) {
/* 1379 */                 Correct = false;
/* 1380 */               } else if ((op.operation_code == 20203) && (Math.abs(remainDemand) > 0.01D))
/* 1381 */                 Correct = false;
/* 1382 */               if (Correct == false) {
/* 1383 */                 this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1384 */                 return false;
/*      */               }
/*      */               
/* 1387 */               this.isFirstIteration = false;
/*      */             }
/*      */             
/* 1390 */             if (op.operation_code == 20202) {
/* 1391 */               this.CurrentTransportationTableau.EliminatedSource[i] = true;
/*      */             } else {
/* 1393 */               this.CurrentTransportationTableau.EliminatedDestination[i] = true;
/*      */             }
/* 1395 */             if (this.TransIBFSMethod == 1) {
/* 1396 */               for (i = 1; i <= this.TransportationModel.NumDestinations; i++) {
/* 1397 */                 if (this.CurrentTransportationTableau.EliminatedDestination[i] == 0)
/* 1398 */                   TransCalculateColumnDifference(i);
/*      */               }
/* 1400 */               for (i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1401 */                 if (this.CurrentTransportationTableau.EliminatedSource[i] == 0)
/* 1402 */                   TransCalculateRowDifference(i);
/*      */               }
/*      */             }
/* 1405 */             if (this.TransIBFSMethod == 2) {
/* 1406 */               for (i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1407 */                 if (this.CurrentTransportationTableau.EliminatedSource[i] == 0)
/* 1408 */                   TransCalculateRussellU(i);
/*      */               }
/* 1410 */               for (i = 1; i <= this.TransportationModel.NumDestinations; i++) {
/* 1411 */                 if (this.CurrentTransportationTableau.EliminatedDestination[i] == 0)
/* 1412 */                   TransCalculateRussellV(i);
/*      */               }
/* 1414 */               for (i = 1; i <= this.TransportationModel.NumSources;) {
/* 1415 */                 if (this.CurrentTransportationTableau.EliminatedSource[i] == 0) {
/* 1416 */                   for (int j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1417 */                     if (this.CurrentTransportationTableau.EliminatedDestination[j] == 0) {
/* 1418 */                       this.RussellCell[i][j] = (this.TransportationModel.Cost[i][j] - this.RussellU[i] - this.RussellV[j]);
/* 1419 */                       this.RussellCellBigM[i][j] = (this.TransportationModel.CostM[i][j] - this.RussellUBigM[i] - this.RussellVBigM[j]);
/*      */                     }
/*      */                   }
/*      */                 }
/* 1414 */                 i++; continue;
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1427 */                 this.CommandNumber = 3;
/* 1428 */                 this.InitialSolutionOK = 0;
/* 1429 */                 for (int i = 1; i <= this.TransportationModel.NumBasicVariables; i++) {
/* 1430 */                   if ((this.CurrentTransportationTableau.BasicVariable[i][1] == 0) || (this.CurrentTransportationTableau.BasicVariable[i][2] == 0))
/* 1431 */                     this.InitialSolutionOK = 1;
/*      */                 }
/* 1433 */                 if (this.InitialSolutionOK == 0) {
/* 1434 */                   for (i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1435 */                     double Sum = 0.0D;
/* 1436 */                     for (int j = 1; j <= this.TransportationModel.NumBasicVariables; j++) {
/* 1437 */                       if (this.CurrentTransportationTableau.BasicVariable[j][1] == i)
/* 1438 */                         Sum += this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.BasicVariable[j][1]][this.CurrentTransportationTableau.BasicVariable[j][2]];
/*      */                     }
/* 1440 */                     if (Math.abs(Sum - this.TransportationModel.Supply[i]) > 0.1D)
/* 1441 */                       this.InitialSolutionOK = 2;
/*      */                   }
/* 1443 */                   for (i = 1; i <= this.TransportationModel.NumDestinations; i++) {
/* 1444 */                     double Sum = 0.0D;
/* 1445 */                     for (int j = 1; j <= this.TransportationModel.NumBasicVariables; j++) {
/* 1446 */                       if (this.CurrentTransportationTableau.BasicVariable[j][2] == i)
/* 1447 */                         Sum += this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.BasicVariable[j][1]][this.CurrentTransportationTableau.BasicVariable[j][2]];
/*      */                     }
/* 1449 */                     if (Math.abs(Sum - this.TransportationModel.Demand[i]) > 0.1D)
/* 1450 */                       this.InitialSolutionOK = 2;
/*      */                   }
/*      */                 }
/* 1453 */                 if (this.InitialSolutionOK > 0) {
/* 1454 */                   if (this.InitialSolutionOK == 1) {
/* 1455 */                     this.errInfo = "error 1";
/*      */                   } else
/* 1457 */                     this.errInfo = "error 2";
/* 1458 */                   return false;
/*      */                 }
/* 1460 */                 this.CurrentTransportationTableau_bak_for_solve = ((ORSolverBase.TransportationTableauType)this.CurrentTransportationTableau.clone());
/* 1461 */                 break;
/*      */                 
/* 1463 */                 this.CommandNumber = 4;
/* 1464 */                 this.CurrentTransportationTableau = ((ORSolverBase.TransportationTableauType)this.CurrentTransportationTableau_bak_for_solve.clone());
/* 1465 */                 this.hasSetInitialUV = false;
/* 1466 */                 break;
/*      */                 
/* 1468 */                 this.CommandNumber = 4;
/* 1469 */                 this.isFirstIteration = true;
/* 1470 */                 double[] d = (double[])p.elementAt(0);
/* 1471 */                 for (int i = 0; i < this.TransportationModel.NumSources; i++)
/* 1472 */                   this.CurrentTransportationTableau.U[(i + 1)] = d[i];
/* 1473 */                 d = (double[])p.elementAt(1);
/* 1474 */                 for (i = 0; i < this.TransportationModel.NumSources; i++)
/* 1475 */                   this.CurrentTransportationTableau.UM[(i + 1)] = d[i];
/* 1476 */                 d = (double[])p.elementAt(2);
/* 1477 */                 for (i = 0; i < this.TransportationModel.NumDestinations; i++)
/* 1478 */                   this.CurrentTransportationTableau.V[(i + 1)] = d[i];
/* 1479 */                 d = (double[])p.elementAt(3);
/* 1480 */                 for (i = 0; i < this.TransportationModel.NumDestinations; i++) {
/* 1481 */                   this.CurrentTransportationTableau.VM[(i + 1)] = d[i];
/*      */                 }
/* 1483 */                 boolean Correct = true;
/* 1484 */                 for (i = 1; i <= this.TransportationModel.NumBasicVariables; i++) {
/* 1485 */                   if (Math.abs(this.TransportationModel.CostM[this.CurrentTransportationTableau.BasicVariable[i][1]][this.CurrentTransportationTableau.BasicVariable[i][2]] - this.CurrentTransportationTableau.UM[this.CurrentTransportationTableau.BasicVariable[i][1]] - this.CurrentTransportationTableau.VM[this.CurrentTransportationTableau.BasicVariable[i][2]]) > 0.5D)
/* 1486 */                     Correct = false;
/* 1487 */                   if (Math.abs(this.TransportationModel.Cost[this.CurrentTransportationTableau.BasicVariable[i][1]][this.CurrentTransportationTableau.BasicVariable[i][2]] - this.CurrentTransportationTableau.U[this.CurrentTransportationTableau.BasicVariable[i][1]] - this.CurrentTransportationTableau.V[this.CurrentTransportationTableau.BasicVariable[i][2]]) > 0.5D)
/* 1488 */                     Correct = false;
/*      */                 }
/* 1490 */                 if (Correct) {
/* 1491 */                   for (i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1492 */                     for (int j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1493 */                       if (TransABasicVariable(i, j) == false) {
/* 1494 */                         this.CurrentTransportationTableau.Cell[i][j] = (this.TransportationModel.Cost[i][j] - this.CurrentTransportationTableau.U[i] - this.CurrentTransportationTableau.V[j]);
/* 1495 */                         this.CurrentTransportationTableau.CellM[i][j] = (this.TransportationModel.CostM[i][j] - this.CurrentTransportationTableau.UM[i] - this.CurrentTransportationTableau.VM[j]);
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/* 1501 */                 this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1502 */                 return false;
/*      */                 
/* 1504 */                 this.hasSetInitialUV = true;
/* 1505 */                 this.CurrentTransportationTableau_bak_for_print = ((ORSolverBase.TransportationTableauType)this.CurrentTransportationTableau.clone());
/* 1506 */                 break;
/*      */                 
/* 1508 */                 this.CommandNumber = 4;
/*      */                 
/* 1510 */                 this.CurrentTransportationTableau.Marker[1] = (((Integer)p.elementAt(0)).intValue() + 1);
/* 1511 */                 this.CurrentTransportationTableau.Marker[2] = (((Integer)p.elementAt(1)).intValue() + 1);
/* 1512 */                 boolean Correct = true;
/* 1513 */                 if (TransABasicVariable(this.CurrentTransportationTableau.Marker[1], this.CurrentTransportationTableau.Marker[2]))
/* 1514 */                   Correct = false;
/* 1515 */                 for (int i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1516 */                   for (int j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1517 */                     if (this.CurrentTransportationTableau.CellM[i][j] < this.CurrentTransportationTableau.CellM[this.CurrentTransportationTableau.Marker[1]][this.CurrentTransportationTableau.Marker[2]] - 0.1D)
/* 1518 */                       Correct = false;
/* 1519 */                     if ((Math.abs(this.CurrentTransportationTableau.CellM[i][j] - this.CurrentTransportationTableau.CellM[this.CurrentTransportationTableau.Marker[1]][this.CurrentTransportationTableau.Marker[2]]) < 0.1D) && (this.CurrentTransportationTableau.Cell[i][j] < this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.Marker[1]][this.CurrentTransportationTableau.Marker[2]] - 0.1D))
/* 1520 */                       Correct = false;
/*      */                   }
/*      */                 }
/* 1523 */                 if ((this.CurrentTransportationTableau.CellM[this.CurrentTransportationTableau.Marker[1]][this.CurrentTransportationTableau.Marker[2]] >= 0) && (this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.Marker[1]][this.CurrentTransportationTableau.Marker[2]] > 0.1D))
/* 1524 */                   Correct = false;
/* 1525 */                 if ((TransABasicVariable(this.CurrentTransportationTableau.Marker[1], this.CurrentTransportationTableau.Marker[2])) || ((!Correct) && (this.isFirstIteration))) {
/* 1526 */                   this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1527 */                   return false;
/*      */                 }
/*      */                 
/* 1530 */                 this.CurrentTransportationTableau.EnteringBasicVariable[1] = this.CurrentTransportationTableau.Marker[1];
/* 1531 */                 this.CurrentTransportationTableau.EnteringBasicVariable[2] = this.CurrentTransportationTableau.Marker[2];
/* 1532 */                 this.CurrentTransportationTableau.Path.Number = 1;
/* 1533 */                 this.CurrentTransportationTableau.Path.Source[1] = this.CurrentTransportationTableau.Marker[1];
/* 1534 */                 this.CurrentTransportationTableau.Path.Destination[1] = this.CurrentTransportationTableau.Marker[2];
/*      */                 
/* 1536 */                 break;
/*      */                 
/* 1538 */                 this.CommandNumber = 4;
/* 1539 */                 this.CurrentTransportationTableau.Marker[1] = (((Integer)p.elementAt(0)).intValue() + 1);
/* 1540 */                 this.CurrentTransportationTableau.Marker[2] = (((Integer)p.elementAt(1)).intValue() + 1);
/* 1541 */                 if ((TransABasicVariable(this.CurrentTransportationTableau.Marker[1], this.CurrentTransportationTableau.Marker[2])) || ((this.CurrentTransportationTableau.Marker[1] == this.CurrentTransportationTableau.EnteringBasicVariable[1]) && (this.CurrentTransportationTableau.Marker[2] == this.CurrentTransportationTableau.EnteringBasicVariable[2])))
/*      */                 {
/* 1543 */                   this.CurrentTransportationTableau.Path.Number += 1;
/* 1544 */                   this.CurrentTransportationTableau.Path.Source[this.CurrentTransportationTableau.Path.Number] = this.CurrentTransportationTableau.Marker[1];
/* 1545 */                   this.CurrentTransportationTableau.Path.Destination[this.CurrentTransportationTableau.Path.Number] = this.CurrentTransportationTableau.Marker[2]; break;
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/* 1550 */                   this.CommandNumber = 4;
/* 1551 */                   this.CurrentTransportationTableau.Marker[1] = (((Integer)p.elementAt(0)).intValue() + 1);
/* 1552 */                   this.CurrentTransportationTableau.Marker[2] = (((Integer)p.elementAt(1)).intValue() + 1);
/* 1553 */                   double delta = ((Double)p.elementAt(2)).doubleValue();
/* 1554 */                   boolean Correct = true;
/* 1555 */                   int j = 0;
/* 1556 */                   if (this.isFirstIteration) {
/* 1557 */                     for (int i = 2; i <= this.CurrentTransportationTableau.Path.Number - 1; i++) {
/* 1558 */                       if (((i % 2 == 0) && (this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.Path.Source[i]][this.CurrentTransportationTableau.Path.Destination[i]] < this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.Marker[1]][this.CurrentTransportationTableau.Marker[2]] - 0.1D)) || ((i % 2 == 1) && (this.CurrentTransportationTableau.Path.Source[i] == this.CurrentTransportationTableau.Marker[1]) && (this.CurrentTransportationTableau.Path.Destination[i] == this.CurrentTransportationTableau.Marker[2])))
/* 1559 */                         Correct = false;
/*      */                     }
/* 1561 */                     i = 1;
/* 1562 */                     for (i = 2; i <= this.CurrentTransportationTableau.Path.Number - 1; i++) {
/* 1563 */                       if ((this.CurrentTransportationTableau.Path.Source[i] == this.CurrentTransportationTableau.Marker[1]) && (this.CurrentTransportationTableau.Path.Destination[i] == this.CurrentTransportationTableau.Marker[2]))
/* 1564 */                         j += 1;
/*      */                     }
/* 1566 */                     if (j == 0)
/* 1567 */                       Correct = false;
/*      */                   }
/* 1569 */                   if (Correct) {
/* 1570 */                     for (int i = 2; i <= this.CurrentTransportationTableau.Path.Number - 1; i++) {
/* 1571 */                       if ((this.CurrentTransportationTableau.Marker[1] == this.CurrentTransportationTableau.Path.Source[i]) && (this.CurrentTransportationTableau.Marker[2] == this.CurrentTransportationTableau.Path.Destination[i])) {
/* 1572 */                         this.CurrentTransportationTableau.LeavingBasicVariable[1] = this.CurrentTransportationTableau.Marker[1];
/* 1573 */                         this.CurrentTransportationTableau.LeavingBasicVariable[2] = this.CurrentTransportationTableau.Marker[2];
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   
/* 1578 */                   this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1579 */                   return false;
/*      */                   int i;
/* 1581 */                   if (this.isFirstIteration) {
/* 1582 */                     if (Math.abs(this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.LeavingBasicVariable[1]][this.CurrentTransportationTableau.LeavingBasicVariable[2]] - delta) > 0.1D)
/* 1583 */                       Correct = false;
/* 1584 */                     this.isFirstIteration = false;
/*      */                   }
/* 1586 */                   if (Correct) {
/* 1587 */                     i = 1;
/*      */                     do {
/* 1589 */                       if ((this.CurrentTransportationTableau.BasicVariable[i][1] == this.CurrentTransportationTableau.LeavingBasicVariable[1]) && (this.CurrentTransportationTableau.BasicVariable[i][2] == this.CurrentTransportationTableau.LeavingBasicVariable[2])) {
/* 1590 */                         this.CurrentTransportationTableau.BasicVariable[i][1] = this.CurrentTransportationTableau.EnteringBasicVariable[1];
/* 1591 */                         this.CurrentTransportationTableau.BasicVariable[i][2] = this.CurrentTransportationTableau.EnteringBasicVariable[2];
/* 1592 */                         for (j = 2; j <= this.CurrentTransportationTableau.Path.Number - 1; j++) {
/* 1593 */                           if (j % 2 == 1) {
/* 1594 */                             this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.Path.Source[j]][this.CurrentTransportationTableau.Path.Destination[j]] += delta;
/*      */                           } else
/* 1596 */                             this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.Path.Source[j]][this.CurrentTransportationTableau.Path.Destination[j]] -= delta;
/*      */                         }
/* 1598 */                         this.CurrentTransportationTableau.Cell[this.CurrentTransportationTableau.EnteringBasicVariable[1]][this.CurrentTransportationTableau.EnteringBasicVariable[2]] = delta;
/* 1599 */                         this.CurrentTransportationTableau.CellM[this.CurrentTransportationTableau.EnteringBasicVariable[1]][this.CurrentTransportationTableau.EnteringBasicVariable[2]] = 0.0D;
/* 1600 */                         TransCalculateUV();
/* 1601 */                         this.CurrentTransportationTableau.Path.Number = 0;
/* 1602 */                         this.CurrentTransportationTableau.EnteringBasicVariable[1] = 0;
/* 1603 */                         this.CurrentTransportationTableau.EnteringBasicVariable[2] = 0;
/*      */                         
/* 1605 */                         i = 1000;
/*      */                       }
/*      */                       else {
/* 1608 */                         i += 1;
/* 1609 */                       } } while (i <= this.TransportationModel.NumBasicVariables);
/*      */                   }
/*      */                   else {
/* 1612 */                     this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/* 1613 */                     return false;
/*      */                   } } } } } } } }
/*      */     int i;
/*      */     int i;
/* 1617 */     return true;
/*      */   }
/*      */   
/*      */   public void reset()
/*      */   {
/* 1622 */     this.CurrentTransportationTableau = ((ORSolverBase.TransportationTableauType)this.CurrentTransportationTableau_bak.clone());
/* 1623 */     this.isFirstIteration = true;
/*      */     
/* 1625 */     if (this.TransIBFSMethod == 1) {
/* 1626 */       for (int i = 1; i <= this.TransportationModel.NumDestinations; i++) {
/* 1627 */         TransCalculateColumnDifference(i);
/*      */       }
/* 1629 */       for (i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1630 */         TransCalculateRowDifference(i);
/*      */       }
/*      */     }
/* 1633 */     if (this.TransIBFSMethod == 2) {
/* 1634 */       for (int i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1635 */         TransCalculateRussellU(i);
/*      */       }
/* 1637 */       for (i = 1; i <= this.TransportationModel.NumDestinations; i++) {
/* 1638 */         TransCalculateRussellV(i);
/*      */       }
/* 1640 */       for (i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1641 */         for (int j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1642 */           this.RussellCell[i][j] = (this.TransportationModel.Cost[i][j] - this.RussellU[i] - this.RussellV[j]);
/* 1643 */           this.RussellCellBigM[i][j] = (this.TransportationModel.CostM[i][j] - this.RussellUBigM[i] - this.RussellVBigM[j]);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void setBookmark()
/*      */   {
/* 1651 */     this.CurrentTransportationTableau_bak = ((ORSolverBase.TransportationTableauType)this.CurrentTransportationTableau.clone());
/*      */   }
/*      */   
/*      */ 
/*      */   protected void ORPrint()
/*      */   {
/* 1657 */     if (this.procedure == 1) {
/* 1658 */       PrintTransportationModify();
/* 1659 */     } else if (this.procedure == 2) {
/* 1660 */       PrintTransportationFindIBFS();
/* 1661 */     } else if (this.procedure == 3) {
/* 1662 */       PrintTransportationSolveInteractively();
/* 1663 */     } else if (this.procedure == 4) {
/* 1664 */       PrintTransportaionSolveAuto();
/*      */     }
/*      */   }
/*      */   
/*      */   private void PrintTransportationModify() {
/* 1669 */     PrintLine("Transportation Problem Model:");
/* 1670 */     SkipLine();
/* 1671 */     PrintTransportationModel();
/*      */   }
/*      */   
/*      */   private void PrintTransportationFindIBFS() {
/* 1675 */     PrintLine("Transportation Problem Model:");
/* 1676 */     SkipLine();
/* 1677 */     PrintTransportationModel();
/* 1678 */     Skip(3);
/* 1679 */     if (this.InitialSolutionOK == 0) {
/* 1680 */       PrintLines(9 + this.TransportationModel.NumSources * 3);
/* 1681 */       Print("Method used to construct initial basic feasible solution:");
/* 1682 */       switch (this.TransIBFSMethod) {
/*      */       case 0: 
/* 1684 */         PrintLine("Northwest Corner Rule");
/* 1685 */         break;
/*      */       case 1: 
/* 1687 */         PrintLine("Vogel's Method");
/* 1688 */         break;
/*      */       case 2: 
/* 1690 */         PrintLine("Russell's Method");
/*      */       }
/*      */       
/* 1693 */       SkipLine();
/* 1694 */       PrintLine("Initial Basic Feasible Solution:");
/* 1695 */       Skip(2);
/* 1696 */       this.bShowLoopInfo = false;
/* 1697 */       PrintTransportationTableau();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void PrintTransportationSolveInteractively()
/*      */   {
/* 1705 */     PrintLine("Transportation Problem Model:");
/* 1706 */     SkipLine();
/* 1707 */     PrintTransportationModel();
/* 1708 */     Skip(3);
/* 1709 */     Print("Method used to construct initial basic feasible solution: ");
/* 1710 */     switch (this.TransIBFSMethod) {
/*      */     case 0: 
/* 1712 */       PrintLine("Northwest Corner Rule");
/* 1713 */       break;
/*      */     case 1: 
/* 1715 */       PrintLine("Vogel's Method");
/* 1716 */       break;
/*      */     case 2: 
/* 1718 */       PrintLine("Russell's Method");
/* 1719 */       break;
/*      */     }
/* 1721 */     SkipLine();
/* 1722 */     if (this.InitialSolutionOK == 0) {
/* 1723 */       PrintLine("Interactive Transportation Simplex Method:");
/* 1724 */       SkipLine();
/* 1725 */       PrintLine("Key:");
/* 1726 */       PrintLine("  B - A Basic Variable");
/* 1727 */       PrintLine("  E - Entering Basic Variable");
/* 1728 */       PrintLine("  L - Leaving Basic Variable");
/* 1729 */       PrintLine("  P - A Basic Variable in Path");
/* 1730 */       Skip(2);
/* 1731 */       ORSolverBase.TransportationTableauType TempCurrentTransp = this.CurrentTransportationTableau;
/* 1732 */       this.CurrentTransportationTableau = ((ORSolverBase.TransportationTableauType)this.CurrentTransportationTableau_bak_for_solve.clone());
/*      */       
/* 1734 */       this.bShowLoopInfo = false;
/* 1735 */       PrintLines(8 + 4 * this.TransportationModel.NumSources);
/* 1736 */       PrintTransportationTableau();
/* 1737 */       Skip(2);
/*      */       
/*      */ 
/* 1740 */       IOROperation op = null;
/* 1741 */       int i = 0;
/* 1742 */       int n = this.steps.size();
/* 1743 */       this.CurrentTransportationTableau = this.CurrentTransportationTableau_bak_for_print;
/* 1744 */       while (i < n) {
/* 1745 */         op = (IOROperation)this.steps.elementAt(i);
/* 1746 */         switch (op.operation_code) {
/*      */         case 20304: 
/* 1748 */           this.CurrentTransportationTableau.LeavingBasicVariable[1] = (((Integer)op.parameters.elementAt(0)).intValue() + 1);
/* 1749 */           this.CurrentTransportationTableau.LeavingBasicVariable[2] = (((Integer)op.parameters.elementAt(1)).intValue() + 1);
/*      */           
/* 1751 */           this.bShowLoopInfo = true;
/* 1752 */           PrintLines(8 + 4 * this.TransportationModel.NumSources);
/* 1753 */           PrintTransportationTableau();
/* 1754 */           Skip(2);
/*      */           
/* 1756 */           doWork(op);
/* 1757 */           break;
/*      */         default: 
/* 1759 */           doWork(op);
/* 1760 */           break;
/*      */         }
/* 1762 */         i++;
/*      */       }
/* 1764 */       if ((n > 0) || (this.hasSetInitialUV)) {
/* 1765 */         this.bShowLoopInfo = false;
/* 1766 */         PrintLines(8 + 4 * this.TransportationModel.NumSources);
/* 1767 */         PrintTransportationTableau();
/* 1768 */         Skip(2);
/*      */       }
/*      */       
/* 1771 */       this.CurrentTransportationTableau = TempCurrentTransp;
/*      */     }
/*      */   }
/*      */   
/*      */   private void PrintTransportaionSolveAuto()
/*      */   {
/* 1777 */     PrintLine("Solve Automatically by the Transportation Simplex Method");
/* 1778 */     SkipLine();
/* 1779 */     PrintTransportationModify();
/* 1780 */     SkipLine();
/* 1781 */     PrintLine("Optimal Solution:");
/* 1782 */     PrintLine("The main body of the table shows the optimal number of units (if not zero)");
/* 1783 */     PrintLine("to be sent from each source to each destination.");
/* 1784 */     SkipLine();
/* 1785 */     for (int i = 0; i < this.autoPrintData.size(); i++) {
/* 1786 */       PrintLine((String)this.autoPrintData.elementAt(i));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void PrintTransportationModel()
/*      */   {
/* 1795 */     int Width = 7;
/*      */     
/* 1797 */     PrintLine("Number of Sources:     ".concat(String.valueOf(String.valueOf(FormatInteger(this.TransportationModel.NumSources, 3, 0, 1)))));
/* 1798 */     SkipLine();
/* 1799 */     PrintLine("Number of Destinations:".concat(String.valueOf(String.valueOf(FormatInteger(this.TransportationModel.NumDestinations, 3, 0, 1)))));
/* 1800 */     SkipLine();
/* 1801 */     TabPrintLine(Width * this.TransportationModel.NumDestinations / 2, "Cost Per Unit Distributed");
/* 1802 */     TabPrint(11, "|");
/* 1803 */     TabPrint(Width * this.TransportationModel.NumDestinations / 2 + 7, "Destination");
/* 1804 */     TabPrintLine(13 + this.TransportationModel.NumDestinations * Width, "|");
/* 1805 */     TabPrint(11, "|");
/* 1806 */     for (int j = 1; j <= this.TransportationModel.NumDestinations; j++)
/* 1807 */       TabPrint(13 + (j - 1) * Width, FormatInteger(j, Width - 1, 0, 1));
/* 1808 */     TabPrintLine(13 + this.TransportationModel.NumDestinations * Width, "| Supply");
/* 1809 */     Print("__________|");
/* 1810 */     for (j = 1; j <= this.TransportationModel.NumDestinations; j++)
/* 1811 */       Print("__________".substring(0, Width));
/* 1812 */     PrintLine("_|_______");
/* 1813 */     for (int i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1814 */       if (i == (this.TransportationModel.NumSources + 1) / 2)
/* 1815 */         Print("Source");
/* 1816 */       TabPrint(9, String.valueOf(String.valueOf(FormatInteger(i, 2, 0, 1))).concat("|"));
/* 1817 */       for (j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1818 */         if (Math.abs(this.TransportationModel.CostM[i][j]) > 1.0E-4D) {
/* 1819 */           TabPrint(13 + (j - 1) * Width, String.valueOf(String.valueOf(FormatReal(this.TransportationModel.CostM[i][j], Width - 2, 0, 1))).concat("M"));
/*      */         } else
/* 1821 */           TabPrint(13 + (j - 1) * Width, FormatReal(this.TransportationModel.Cost[i][j], Width - 1, 0, 1));
/*      */       }
/* 1823 */       TabPrintLine(13 + this.TransportationModel.NumDestinations * Width, "| ".concat(String.valueOf(String.valueOf(FormatReal(this.TransportationModel.Supply[i], 6, 0, 1)))));
/*      */     }
/* 1825 */     Print("__________|");
/* 1826 */     for (j = 1; j <= this.TransportationModel.NumDestinations; j++)
/* 1827 */       Print("__________".substring(0, Width));
/* 1828 */     PrintLine("_|_______");
/* 1829 */     Print("Demand    |");
/* 1830 */     for (j = 1; j <= this.TransportationModel.NumDestinations; j++)
/* 1831 */       TabPrint(13 + (j - 1) * Width, FormatReal(this.TransportationModel.Demand[j], Width - 1, 0, 1));
/* 1832 */     TabPrintLine(13 + this.TransportationModel.NumDestinations * Width, "|");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void PrintTransportationTableau()
/*      */   {
/* 1842 */     double Obj = 0.0D;
/* 1843 */     double ObjM = 0.0D;
/* 1844 */     TabPrint(10, "|");
/* 1845 */     int Total = 7 * this.TransportationModel.NumDestinations;
/* 1846 */     TabPrint(Total / 2 + 5, "Destination");
/* 1847 */     TabPrintLine(Total + 10, "|");
/* 1848 */     TabPrint(10, "|");
/* 1849 */     for (int i = 1; i <= this.TransportationModel.NumDestinations; i++)
/* 1850 */       Print(String.valueOf(String.valueOf(new StringBuffer("   ").append(FormatInteger(i, 1, 0, 1)).append("  |"))));
/* 1851 */     PrintLine("Supply  u[i]");
/* 1852 */     Print("_________|");
/* 1853 */     for (i = 1; i <= this.TransportationModel.NumDestinations; i++)
/* 1854 */       Print("______|");
/* 1855 */     PrintLine("____________");
/* 1856 */     for (i = 1; i <= this.TransportationModel.NumSources; i++) {
/* 1857 */       TabPrint(10, "|");
/* 1858 */       for (int j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1859 */         if (Math.abs(this.TransportationModel.CostM[i][j]) > 0.01D) {
/* 1860 */           Print("   M");
/*      */         } else
/* 1862 */           Print(FormatReal(this.TransportationModel.Cost[i][j], 4, 0, 1));
/* 1863 */         Print("| |");
/*      */       }
/* 1865 */       PrintLine("      |");
/* 1866 */       TabPrint(8, String.valueOf(String.valueOf(FormatInteger(i, 1, 2, 1))).concat(" |"));
/* 1867 */       for (j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1868 */         Print("---- ");
/* 1869 */         boolean InPath = false;
/* 1870 */         for (int k = 1; k <= this.CurrentTransportationTableau.Path.Number; k++) {
/* 1871 */           if ((this.CurrentTransportationTableau.Path.Source[k] == i) && (this.CurrentTransportationTableau.Path.Destination[k] == j))
/* 1872 */             InPath = true;
/*      */         }
/* 1874 */         boolean ABasicVar = false;
/* 1875 */         for (k = 1; k <= this.TransportationModel.NumBasicVariables; k++) {
/* 1876 */           if ((this.CurrentTransportationTableau.BasicVariable[k][1] == i) && (this.CurrentTransportationTableau.BasicVariable[k][2] == j))
/* 1877 */             ABasicVar = true;
/*      */         }
/* 1879 */         if (ABasicVar) {
/* 1880 */           ObjM += this.TransportationModel.CostM[i][j] * this.CurrentTransportationTableau.Cell[i][j];
/* 1881 */           Obj += this.TransportationModel.Cost[i][j] * this.CurrentTransportationTableau.Cell[i][j];
/*      */         }
/* 1883 */         if (this.bShowLoopInfo == false) {
/* 1884 */           if (ABasicVar) {
/* 1885 */             Print("B|");
/*      */           } else {
/* 1887 */             Print(" |");
/*      */           }
/*      */         }
/* 1890 */         else if ((this.CurrentTransportationTableau.EnteringBasicVariable[1] == i) && (this.CurrentTransportationTableau.EnteringBasicVariable[2] == j)) {
/* 1891 */           Print("E|");
/* 1892 */         } else if ((this.CurrentTransportationTableau.LeavingBasicVariable[1] == i) && (this.CurrentTransportationTableau.LeavingBasicVariable[2] == j)) {
/* 1893 */           Print("L|");
/* 1894 */         } else if (InPath) {
/* 1895 */           Print("P|");
/* 1896 */         } else if (ABasicVar) {
/* 1897 */           Print("B|");
/*      */         } else {
/* 1899 */           Print(" |");
/*      */         }
/*      */       }
/* 1902 */       PrintLine("      |");
/* 1903 */       TabPrint(10, "|");
/* 1904 */       for (j = 1; j <= this.TransportationModel.NumDestinations; j++) {
/* 1905 */         if (Math.abs(this.CurrentTransportationTableau.CellM[i][j]) > 1.0E-5D) {
/* 1906 */           Print(String.valueOf(String.valueOf(FormatReal(this.CurrentTransportationTableau.CellM[i][j], 2, 0, 1))).concat("M"));
/* 1907 */           Print(FormatReal(this.CurrentTransportationTableau.Cell[i][j], 3, 1, 1));
/*      */         }
/*      */         else {
/* 1910 */           Print(FormatReal(this.CurrentTransportationTableau.Cell[i][j], 6, 0, 1)); }
/* 1911 */         Print("|");
/*      */       }
/* 1913 */       Print(" ".concat(String.valueOf(String.valueOf(FormatReal(this.TransportationModel.Supply[i], 5, 0, 1)))));
/* 1914 */       Print("| ");
/* 1915 */       if (Math.abs(this.CurrentTransportationTableau.UM[i]) > 1.0E-4D) {
/* 1916 */         PrintLine(String.valueOf(String.valueOf(FormatReal(this.CurrentTransportationTableau.UM[i], 4, 0, 1))).concat("M"));
/*      */       } else
/* 1918 */         PrintLine(FormatReal(this.CurrentTransportationTableau.U[i], 5, 0, 1));
/* 1919 */       TabPrint(10, "|");
/* 1920 */       for (int k = 1; k <= this.TransportationModel.NumDestinations; k++)
/* 1921 */         Print("______|");
/* 1922 */       if (i == this.TransportationModel.NumSources) {
/* 1923 */         Print("______|");
/*      */       } else
/* 1925 */         Print("      |");
/* 1926 */       if ((Math.abs(this.CurrentTransportationTableau.UM[i]) > 0.01D) && (Math.abs(this.CurrentTransportationTableau.U[i]) > 0.01D)) {
/* 1927 */         PrintLine(FormatReal(this.CurrentTransportationTableau.U[i], 5, 1, 1));
/*      */       }
/* 1929 */       else if (i == this.TransportationModel.NumSources) {
/* 1930 */         PrintLine("_____");
/*      */       } else {
/* 1932 */         SkipLine();
/*      */       }
/*      */     }
/* 1935 */     Print("  Demand |");
/* 1936 */     for (i = 1; i <= this.TransportationModel.NumDestinations; i++) {
/* 1937 */       Print(" ".concat(String.valueOf(String.valueOf(FormatReal(this.TransportationModel.Demand[i], 5, 0, 1)))));
/* 1938 */       if (i == this.TransportationModel.NumDestinations) {
/* 1939 */         PrintLine("|");
/*      */       } else
/* 1941 */         Print(" ");
/*      */     }
/* 1943 */     Print("_________|");
/* 1944 */     for (i = 1; i <= this.TransportationModel.NumDestinations; i++) {
/* 1945 */       Print("______");
/* 1946 */       if (i == this.TransportationModel.NumDestinations) {
/* 1947 */         PrintLine("|");
/*      */       } else
/* 1949 */         Print("_");
/*      */     }
/* 1951 */     Print("    v[j] |");
/* 1952 */     for (i = 1; i <= this.TransportationModel.NumDestinations; i++) {
/* 1953 */       if (Math.abs(this.CurrentTransportationTableau.VM[i]) > 0.01D) {
/* 1954 */         Print(String.valueOf(String.valueOf(new StringBuffer(" ").append(FormatReal(this.CurrentTransportationTableau.VM[i], 4, 0, 1)).append("M"))));
/*      */       } else
/* 1956 */         Print(" ".concat(String.valueOf(String.valueOf(FormatReal(this.CurrentTransportationTableau.V[i], 5, 0, 1)))));
/* 1957 */       if (i == this.TransportationModel.NumDestinations) {
/* 1958 */         PrintLine("|");
/*      */       } else
/* 1960 */         Print(" ");
/*      */     }
/* 1962 */     Print("         |");
/* 1963 */     for (i = 1; i <= this.TransportationModel.NumDestinations; i++) {
/* 1964 */       if ((Math.abs(this.CurrentTransportationTableau.VM[i]) > 0.01D) && (Math.abs(this.CurrentTransportationTableau.V[i]) > 0.01D)) {
/* 1965 */         Print(" ".concat(String.valueOf(String.valueOf(FormatReal(this.CurrentTransportationTableau.V[i], 5, 1, 1)))));
/*      */       } else
/* 1967 */         Print("      ");
/* 1968 */       if (i == this.TransportationModel.NumDestinations) {
/* 1969 */         Print("|");
/*      */       } else
/* 1971 */         Print(" ");
/*      */     }
/* 1973 */     Print(" Z = ");
/* 1974 */     if (Math.abs(ObjM) > 0.001D) {
/* 1975 */       Print(String.valueOf(String.valueOf(RealToString(ObjM, 4))).concat("M"));
/* 1976 */       PrintLine(FormatReal(Obj, 6, 1, 0));
/*      */     }
/*      */     else {
/* 1979 */       PrintLine(FormatReal(Obj, 8, 0, 0)); }
/* 1980 */     SkipLine();
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORTPSolver.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */