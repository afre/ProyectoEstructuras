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
/*      */ public class ORNASolver
/*      */   extends ORSolverBase
/*      */ {
/*   28 */   private ORSolverBase.NetworkType Network = new ORSolverBase.NetworkType(this);
/*   29 */   private ORSolverBase.NetworkType Network_original = null;
/*   30 */   private ORSolverBase.NetworkType Network_temp = null;
/*   31 */   private ORSolverBase.InteractiveNetworkType CurrentNetwork = new ORSolverBase.InteractiveNetworkType(this);
/*   32 */   private ORSolverBase.InteractiveNetworkType CurrentNetwork_temp = null;
/*   33 */   private String[] NodeStringArray = new String[17];
/*      */   
/*   35 */   private boolean isFirstSolverStep = true;
/*      */   
/*      */   private void my_InitializeNetworkModel()
/*      */   {
/*   39 */     this.Network.NumNodes = 5;
/*   40 */     this.Network.NetFlow[1] = 50.0D;
/*   41 */     this.Network.NetFlow[2] = 80.0D;
/*   42 */     this.Network.NetFlow[3] = 0.0D;
/*   43 */     this.Network.NetFlow[4] = -70.0D;
/*   44 */     this.Network.NetFlow[5] = -60.0D;
/*      */     
/*   46 */     this.Network.AnArc[1][3] = 1;
/*   47 */     this.Network.Cost[1][3] = 4.0D;
/*   48 */     this.Network.AnArc[1][4] = 1;
/*   49 */     this.Network.Cost[1][4] = 6.0D;
/*   50 */     this.Network.AnArc[2][1] = 1;
/*   51 */     this.Network.Cost[2][1] = 1.0D;
/*   52 */     this.Network.AnArc[2][3] = 1;
/*   53 */     this.Network.Cost[2][3] = 2.0D;
/*   54 */     this.Network.AnArc[2][5] = 1;
/*   55 */     this.Network.Cost[2][5] = 5.0D;
/*   56 */     this.Network.AnArc[3][4] = 1;
/*   57 */     this.Network.Cost[3][4] = 3.0D;
/*   58 */     this.Network.AnArc[3][5] = 1;
/*   59 */     this.Network.Cost[3][5] = 5.0D;
/*      */     
/*   61 */     this.Network.NetworkCapacitated = true;
/*      */     
/*   63 */     this.Network.Capacitated[1][4] = 1;
/*   64 */     this.Network.Capacity[1][4] = 40.0D;
/*   65 */     this.Network.Capacitated[2][5] = 1;
/*   66 */     this.Network.Capacity[2][5] = 40.0D;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void NetworkSimplexMethod()
/*      */   {
/*   73 */     int Iteration = 1;
/*   74 */     SetupPhaseOne();
/*      */     do {
/*   76 */       IterateNetworkSimplexMethod();
/*   77 */       Iteration += 1;
/*   78 */     } while (this.Network.Optimal == false);
/*   79 */     Iteration = 1;
/*   80 */     SetupPhaseTwo();
/*   81 */     if (this.Network.Feasible) {
/*      */       do
/*      */       {
/*   84 */         IterateNetworkSimplexMethod();
/*   85 */         Iteration += 1;
/*   86 */       } while ((!this.Network.Optimal) && (!this.Network.Unbounded));
/*      */     }
/*   88 */     FinishUp();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void SetupPhaseOne()
/*      */   {
/*   95 */     this.Network.NumNodes += 1;
/*   96 */     for (int i = 1; i <= this.Network.NumNodes; i++)
/*      */     {
/*   98 */       for (int j = 1; j <= this.Network.NumNodes; j++)
/*      */       {
/*  100 */         this.Network.OriginalCost[i][j] = this.Network.Cost[i][j];
/*  101 */         this.Network.Cost[i][j] = 0.0D;
/*  102 */         this.Network.Basic[i][j] = 0;
/*  103 */         this.Network.Flow[i][j] = 0.0D;
/*      */       }
/*      */     }
/*  106 */     this.Network.NetFlow[this.Network.NumNodes] = 0.0D;
/*  107 */     for (i = 1; i <= this.Network.NumNodes - 1; i++)
/*      */     {
/*  109 */       if (this.Network.NetFlow[i] < 0)
/*      */       {
/*  111 */         this.Network.Cost[this.Network.NumNodes][i] = 1.0D;
/*  112 */         this.Network.Flow[this.Network.NumNodes][i] = (-this.Network.NetFlow[i]);
/*  113 */         this.Network.Basic[this.Network.NumNodes][i] = 1;
/*  114 */         this.Network.AnArc[this.Network.NumNodes][i] = 1;
/*  115 */         this.Network.Capacitated[this.Network.NumNodes][i] = 0;
/*      */       }
/*      */       else
/*      */       {
/*  119 */         this.Network.Cost[i][this.Network.NumNodes] = 1.0D;
/*  120 */         this.Network.Flow[i][this.Network.NumNodes] = this.Network.NetFlow[i];
/*  121 */         this.Network.Basic[i][this.Network.NumNodes] = 1;
/*  122 */         this.Network.AnArc[i][this.Network.NumNodes] = 1;
/*  123 */         this.Network.Capacitated[i][this.Network.NumNodes] = 0;
/*      */       }
/*      */     }
/*  126 */     this.Network.Optimal = false;
/*  127 */     this.Network.Feasible = true;
/*  128 */     this.Network.Unbounded = false;
/*      */   }
/*      */   
/*      */   private void IterateNetworkSimplexMethod()
/*      */   {
/*  133 */     int[] Path = new int[17];
/*      */     
/*      */ 
/*  136 */     CalculateNodeNumbers();
/*  137 */     SelectEnteringArc();
/*  138 */     if (this.Network.Optimal == false)
/*      */     {
/*  140 */       int PathLength = SelectLeavingArc(Path);
/*  141 */       AdjustNetwork(Path, PathLength);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void CalculateNodeNumbers()
/*      */   {
/*  149 */     for (int i = 1; i <= this.Network.NumNodes; i++)
/*  150 */       this.Network.NodeNumbered[i] = false;
/*  151 */     this.Network.NodeNumbered[1] = true;
/*  152 */     this.Network.NodeNumber[1] = 0.0D;
/*  153 */     int NumWithNumbers = 1;
/*      */     do {
/*  155 */       for (i = 1; i <= this.Network.NumNodes; i++)
/*      */       {
/*  157 */         if (this.Network.NodeNumbered[i] != 0)
/*      */         {
/*  159 */           for (int j = 1; j <= this.Network.NumNodes; j++)
/*      */           {
/*  161 */             if ((i != j) && (this.Network.NodeNumbered[j] == 0))
/*      */             {
/*  163 */               if (this.Network.Basic[i][j] != 0)
/*      */               {
/*  165 */                 NumWithNumbers += 1;
/*  166 */                 this.Network.NodeNumbered[j] = true;
/*  167 */                 this.Network.NodeNumber[i] += this.Network.Cost[i][j];
/*      */               }
/*  169 */               else if (this.Network.Basic[j][i] != 0)
/*      */               {
/*  171 */                 NumWithNumbers += 1;
/*  172 */                 this.Network.NodeNumbered[j] = true;
/*  173 */                 this.Network.NodeNumber[i] -= this.Network.Cost[j][i];
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  179 */     } while (NumWithNumbers != this.Network.NumNodes);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void SelectEnteringArc()
/*      */   {
/*  187 */     double Min = 1.0D;
/*  188 */     for (int i = 1; i <= this.Network.NumNodes; i++)
/*      */     {
/*  190 */       for (int j = 1; j <= this.Network.NumNodes; j++)
/*      */       {
/*  192 */         if ((this.Network.AnArc[i][j] != 0) && (this.Network.Basic[i][j] == 0) && (this.Network.NodeNumber[i] + this.Network.Cost[i][j] - this.Network.NodeNumber[j] < Min))
/*      */         {
/*  194 */           Min = this.Network.NodeNumber[i] + this.Network.Cost[i][j] - this.Network.NodeNumber[j];
/*  195 */           this.Network.EnteringArc[1] = i;
/*  196 */           this.Network.EnteringArc[2] = j;
/*      */         }
/*      */       }
/*      */     }
/*  200 */     if (Min > -1.0E-6D) {
/*  201 */       this.Network.Optimal = true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int SelectLeavingArc(int[] Path)
/*      */   {
/*  209 */     Path[1] = this.Network.EnteringArc[1];
/*  210 */     Path[2] = this.Network.EnteringArc[2];
/*  211 */     for (int j = 3; j <= 16; j++)
/*  212 */       Path[j] = 0;
/*  213 */     j = 3;
/*      */     do {
/*  215 */       Path[j] += 1;
/*  216 */       while (((this.Network.Basic[Path[(j - 1)]][Path[j]] == 0) && (this.Network.Basic[Path[j]][Path[(j - 1)]] == 0)) || (((Path[j] == Path[(j - 1)]) || (Path[j] == Path[(j - 2)])) && (Path[j] <= this.Network.NumNodes)))
/*  217 */         Path[j] += 1;
/*  218 */       if (Path[j] > this.Network.NumNodes)
/*      */       {
/*  220 */         Path[j] = 0;
/*  221 */         j -= 1;
/*      */       }
/*  223 */       else if (Path[j] != Path[1])
/*      */       {
/*  225 */         j += 1;
/*      */       }
/*  227 */     } while (Path[j] != Path[1]);
/*  228 */     int PathLength = j;
/*  229 */     this.Network.EnteringArcFlow = 1.0E20D;
/*  230 */     for (j = 1; j <= PathLength - 1; j++)
/*      */     {
/*  232 */       if (this.Network.AnArc[Path[j]][Path[(j + 1)]] != 0)
/*      */       {
/*  234 */         if (this.Network.Capacitated[Path[j]][Path[(j + 1)]] != 0)
/*      */         {
/*  236 */           if (this.Network.Capacity[Path[j]][Path[(j + 1)]] - this.Network.Flow[Path[j]][Path[(j + 1)]] < this.Network.EnteringArcFlow)
/*      */           {
/*  238 */             this.Network.EnteringArcFlow = (this.Network.Capacity[Path[j]][Path[(j + 1)]] - this.Network.Flow[Path[j]][Path[(j + 1)]]);
/*  239 */             this.Network.LeavingArc[1] = Path[j];
/*  240 */             this.Network.LeavingArc[2] = Path[(j + 1)];
/*      */           }
/*      */         }
/*      */       }
/*  244 */       else if (this.Network.AnArc[Path[(j + 1)]][Path[j]] != 0)
/*      */       {
/*  246 */         if (this.Network.Flow[Path[(j + 1)]][Path[j]] < this.Network.EnteringArcFlow)
/*      */         {
/*  248 */           this.Network.LeavingArc[1] = Path[j];
/*  249 */           this.Network.LeavingArc[2] = Path[(j + 1)];
/*  250 */           this.Network.EnteringArcFlow = this.Network.Flow[Path[(j + 1)]][Path[j]];
/*      */         }
/*      */       }
/*      */     }
/*  254 */     if (this.Network.EnteringArcFlow > 1.0E19D) {
/*  255 */       this.Network.Unbounded = true;
/*      */     }
/*  257 */     return PathLength;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void AdjustNetwork(int[] Path, int PathLength)
/*      */   {
/*  264 */     for (int i = 1; i <= PathLength - 1; i++)
/*      */     {
/*  266 */       if (this.Network.AnArc[Path[i]][Path[(i + 1)]] != 0) {
/*  267 */         this.Network.Flow[Path[i]][Path[(i + 1)]] += this.Network.EnteringArcFlow;
/*      */       } else
/*  269 */         this.Network.Flow[Path[(i + 1)]][Path[i]] -= this.Network.EnteringArcFlow;
/*      */     }
/*  271 */     this.Network.Basic[this.Network.EnteringArc[1]][this.Network.EnteringArc[2]] = 1;
/*  272 */     if (this.Network.AnArc[this.Network.LeavingArc[1]][this.Network.LeavingArc[2]] != 0)
/*      */     {
/*  274 */       this.Network.AnArc[this.Network.LeavingArc[1]][this.Network.LeavingArc[2]] = 0;
/*  275 */       this.Network.Basic[this.Network.LeavingArc[1]][this.Network.LeavingArc[2]] = 0;
/*  276 */       this.Network.AnArc[this.Network.LeavingArc[2]][this.Network.LeavingArc[1]] = 1;
/*  277 */       this.Network.Basic[this.Network.LeavingArc[2]][this.Network.LeavingArc[1]] = 0;
/*  278 */       if (this.Network.Reversed[this.Network.LeavingArc[1]][this.Network.LeavingArc[2]] != 0) {
/*  279 */         this.Network.Reversed[this.Network.LeavingArc[2]][this.Network.LeavingArc[1]] = 0;
/*      */       } else
/*  281 */         this.Network.Reversed[this.Network.LeavingArc[2]][this.Network.LeavingArc[1]] = 1;
/*  282 */       this.Network.Cost[this.Network.LeavingArc[2]][this.Network.LeavingArc[1]] = (-this.Network.Cost[this.Network.LeavingArc[1]][this.Network.LeavingArc[2]]);
/*  283 */       this.Network.Capacity[this.Network.LeavingArc[2]][this.Network.LeavingArc[1]] = this.Network.Capacity[this.Network.LeavingArc[1]][this.Network.LeavingArc[2]];
/*  284 */       this.Network.Capacitated[this.Network.LeavingArc[2]][this.Network.LeavingArc[1]] = 1;
/*  285 */       this.Network.Flow[this.Network.LeavingArc[2]][this.Network.LeavingArc[1]] = 0.0D;
/*  286 */       this.Network.NetFlow[this.Network.LeavingArc[1]] -= this.Network.Capacity[this.Network.LeavingArc[1]][this.Network.LeavingArc[2]];
/*  287 */       this.Network.NetFlow[this.Network.LeavingArc[2]] += this.Network.Capacity[this.Network.LeavingArc[1]][this.Network.LeavingArc[2]];
/*      */     }
/*      */     else
/*      */     {
/*  291 */       this.Network.Basic[this.Network.LeavingArc[2]][this.Network.LeavingArc[1]] = 0;
/*  292 */       this.Network.Flow[this.Network.LeavingArc[2]][this.Network.LeavingArc[1]] = 0.0D;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void SetupPhaseTwo()
/*      */   {
/*  300 */     for (int i = 1; i <= this.Network.NumNodes - 1; i++)
/*      */     {
/*  302 */       if (((this.Network.AnArc[i][this.Network.NumNodes] != 0) && (this.Network.Flow[i][this.Network.NumNodes] > 0.001D)) || ((this.Network.AnArc[this.Network.NumNodes][i] != 0) && (this.Network.Flow[this.Network.NumNodes][i] > 0.001D)))
/*  303 */         this.Network.Feasible = false;
/*      */     }
/*  305 */     if (this.Network.Feasible)
/*      */     {
/*  307 */       this.Network.Optimal = false;
/*  308 */       for (i = 1; i <= this.Network.NumNodes - 1; i++)
/*      */       {
/*  310 */         if (this.Network.AnArc[i][this.Network.NumNodes] != 0)
/*      */         {
/*  312 */           if (this.Network.Basic[i][this.Network.NumNodes] == 0) {
/*  313 */             this.Network.AnArc[i][this.Network.NumNodes] = 0;
/*      */           } else {
/*  315 */             this.Network.Capacity[i][this.Network.NumNodes] = 0.0D;
/*      */           }
/*      */           
/*      */         }
/*  319 */         else if (this.Network.Basic[this.Network.NumNodes][i] == 0) {
/*  320 */           this.Network.AnArc[this.Network.NumNodes][i] = 0;
/*      */         } else {
/*  322 */           this.Network.Capacity[this.Network.NumNodes][i] = 0.0D;
/*      */         }
/*      */       }
/*  325 */       for (i = 1; i <= this.Network.NumNodes - 1; i++)
/*      */       {
/*  327 */         for (int j = 1; j <= this.Network.NumNodes - 1; j++)
/*      */         {
/*  329 */           if ((this.Network.AnArc[i][j] != 0) && (this.Network.Reversed[i][j] != 0)) {
/*  330 */             this.Network.Cost[i][j] = (-this.Network.OriginalCost[j][i]);
/*      */           } else {
/*  332 */             this.Network.Cost[i][j] = this.Network.OriginalCost[i][j];
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void FinishUp()
/*      */   {
/*  342 */     for (int i = 1; i <= this.Network.NumNodes - 1; i++)
/*      */     {
/*  344 */       for (int j = 1; j <= this.Network.NumNodes - 1; j++)
/*      */       {
/*  346 */         if ((this.Network.AnArc[i][j] != 0) && (this.Network.Reversed[i][j] != 0))
/*      */         {
/*  348 */           this.Network.AnArc[i][j] = 0;
/*  349 */           this.Network.AnArc[j][i] = 1;
/*  350 */           if (this.Network.Basic[i][j] != 0)
/*      */           {
/*  352 */             this.Network.Basic[i][j] = 0;
/*  353 */             this.Network.Basic[j][i] = 1;
/*      */           }
/*  355 */           this.Network.Reversed[j][i] = 0;
/*  356 */           this.Network.Flow[j][i] = (this.Network.Capacity[j][i] - this.Network.Flow[i][j]);
/*  357 */           this.Network.NetFlow[i] -= this.Network.Capacity[j][i];
/*  358 */           this.Network.NetFlow[j] += this.Network.Capacity[j][i];
/*      */         }
/*      */       }
/*      */     }
/*  362 */     this.Network.NumNodes -= 1;
/*  363 */     this.Network.Z = 0.0D;
/*  364 */     for (i = 1; i <= this.Network.NumNodes; i++)
/*      */     {
/*  366 */       for (int j = 1; j <= this.Network.NumNodes; j++)
/*      */       {
/*  368 */         if (this.Network.AnArc[i][j] != 0) {
/*  369 */           this.Network.Z += this.Network.Flow[i][j] * this.Network.Cost[i][j];
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
/*      */   private void OutputResult()
/*      */   {
/*  383 */     int cellwidth = 10;
/*  384 */     int dec = 3;
/*      */     
/*  386 */     System.out.print("\n");
/*  387 */     System.out.print("Total Cost = ");
/*  388 */     System.out.print(ConvertDoubleToFixString(this.Network.Z, cellwidth, dec, 1, false));
/*  389 */     System.out.print("\n");
/*      */     
/*  391 */     System.out.print("\n");
/*  392 */     System.out.print(ConvertStringToFixString("Flow", 4 * cellwidth - 6, 2));
/*  393 */     System.out.print("\n");
/*  394 */     System.out.print(ConvertStringToFixString(" ", cellwidth - 6, 2));
/*  395 */     for (int j = 1; j <= this.Network.NumNodes; j++)
/*  396 */       System.out.print(ConvertIntToFixString(j, cellwidth, 2));
/*  397 */     System.out.print("\n\n");
/*      */     
/*  399 */     for (int i = 1; i <= this.Network.NumNodes; i++)
/*      */     {
/*  401 */       System.out.print(ConvertIntToFixString(i, cellwidth - 6, 2));
/*  402 */       for (j = 1; j <= this.Network.NumNodes; j++)
/*      */       {
/*  404 */         if (this.Network.Flow[i][j] < 1.0E-6D) {
/*  405 */           System.out.print(ConvertStringToFixString(" ", cellwidth, 2));
/*      */         } else
/*  407 */           System.out.print(ConvertDoubleToFixString(this.Network.Flow[i][j], cellwidth, dec, 2, false));
/*      */       }
/*  409 */       System.out.print("\n");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void InitializeCurrentNetwork()
/*      */   {
/*  417 */     for (int i = 1; i <= 11; i++)
/*      */     {
/*  419 */       for (int j = 1; j <= 11; j++)
/*  420 */         this.CurrentNetwork.Path[i][j] = 0;
/*      */     }
/*  422 */     for (i = 1; i <= 10; i++)
/*      */     {
/*  424 */       this.CurrentNetwork.PathZ[i] = 0.0D;
/*      */     }
/*  426 */     this.CurrentNetwork.EnteringArc = 1;
/*  427 */     this.CurrentNetwork.Optimal = false;
/*  428 */     this.CurrentNetwork.LeavingArc = 1;
/*  429 */     this.CurrentNetwork.EnteringArcFlow = 0.0D;
/*  430 */     this.CurrentNetwork.ReverseLeavingArc = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void CalculateNetworkFlows()
/*      */   {
/*  438 */     for (int i = 1; i <= this.Network.NumNodes - 1; i++)
/*      */     {
/*  440 */       for (int j = 1; j <= this.Network.NumNodes - 1; j++)
/*  441 */         this.Network.FlowMatrix[i][j] = 0.0D;
/*      */     }
/*  443 */     for (i = 1; i <= this.Network.NumNodes - 1; i++)
/*  444 */       this.Network.FlowVector[i] = this.Network.NetFlow[i];
/*  445 */     for (i = 1; i <= this.Network.NumNodes - 1; i++)
/*      */     {
/*  447 */       if (this.Network.WhichBasic[i][1] < this.Network.NumNodes)
/*  448 */         this.Network.FlowMatrix[this.Network.WhichBasic[i][1]][i] = 1.0D;
/*  449 */       if (this.Network.WhichBasic[i][2] < this.Network.NumNodes)
/*  450 */         this.Network.FlowMatrix[this.Network.WhichBasic[i][2]][i] = -1.0D;
/*      */     }
/*  452 */     boolean Singular = SolveSystem(this.Network.FlowMatrix, this.Network.FlowVector, this.Network.NumNodes - 1, this.Network.FlowVector);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void NetworkSetupPath()
/*      */   {
/*  459 */     int k = 0;
/*  460 */     for (int i = 1; i <= this.Network.NumNodes; i++) {
/*  461 */       for (int j = 1; j <= this.Network.NumNodes; j++) {
/*  462 */         if ((this.Network.AnArc[i][j] != 0) && (this.Network.Basic[i][j] == 0)) {
/*  463 */           k += 1;
/*  464 */           this.CurrentNetwork.Path[k][1] = i;
/*  465 */           this.CurrentNetwork.Path[k][2] = j;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void CalcNetworkZ()
/*      */   {
/*  475 */     this.Network.Z = 0.0D;
/*  476 */     for (int i = 1; i <= this.Network.NumNodes; i++) {
/*  477 */       for (int j = 1; j <= this.Network.NumNodes; j++) {
/*  478 */         if ((this.Network.AnArc[i][j] != 0) && (this.Network.Basic[i][j] != 0)) {
/*  479 */           this.Network.Z += this.Network.Flow[i][j] * this.Network.Cost[i][j];
/*  480 */         } else if ((this.Network.AnArc[i][j] != 0) && (this.Network.Reversed[i][j] != 0)) {
/*  481 */           this.Network.Z += (this.Network.Capacity[i][j] - this.Network.Flow[i][j]) * this.Network.Cost[j][i];
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void setArcInfo(String Arc, double cost, double capacity) {
/*  488 */     int[] Result = new int[11];
/*  489 */     getIntResult(Arc, Result);
/*  490 */     this.Network.AnArc[Result[1]][Result[2]] = 1;
/*  491 */     this.Network.Cost[Result[1]][Result[2]] = cost;
/*  492 */     if (capacity > 0) {
/*  493 */       this.Network.Capacity[Result[1]][Result[2]] = capacity;
/*  494 */       this.Network.Capacitated[Result[1]][Result[2]] = 1;
/*      */     }
/*      */     else {
/*  497 */       this.Network.Capacitated[Result[1]][Result[2]] = 0;
/*      */     }
/*  499 */     this.Network.Reversed[Result[1]][Result[2]] = 0;
/*      */     
/*  501 */     this.Network.AnArc[Result[2]][Result[1]] = 0;
/*  502 */     if (cost > 0) {
/*  503 */       this.Network.Cost[Result[2]][Result[1]] = (-cost);
/*      */     } else
/*  505 */       this.Network.Cost[Result[2]][Result[1]] = 0.0D;
/*  506 */     if (capacity > 0) {
/*  507 */       this.Network.Capacity[Result[2]][Result[1]] = capacity;
/*  508 */       this.Network.Capacitated[Result[2]][Result[1]] = 1;
/*      */     }
/*      */     else {
/*  511 */       this.Network.Capacitated[Result[2]][Result[1]] = 0;
/*      */     }
/*  513 */     if (capacity > 0) {
/*  514 */       this.Network.Capacity[Result[2]][Result[1]] = capacity;
/*  515 */       this.Network.Capacitated[Result[2]][Result[1]] = 1;
/*      */     }
/*      */     else {
/*  518 */       this.Network.Capacitated[Result[2]][Result[1]] = 0;
/*      */     }
/*  520 */     this.Network.Reversed[Result[2]][Result[1]] = 1;
/*      */   }
/*      */   
/*      */   private void setNodeInfo(String Node, double NodeFlow, String NodeString, String[] NodeStringArray)
/*      */   {
/*  525 */     int[] Result = new int[11];
/*  526 */     getIntResult(Node, Result);
/*  527 */     this.Network.NetFlow[Result[1]] = NodeFlow;
/*  528 */     NodeStringArray[Result[1]] = NodeString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void InitializeProblem(int WhichProblem, IORNAProcessController.NAData data)
/*      */   {
/*  535 */     this.NodeStringArray = new String[17];
/*  536 */     this.Network = new ORSolverBase.NetworkType(this);
/*  537 */     InitializeCurrentNetwork();
/*  538 */     switch (WhichProblem)
/*      */     {
/*      */     case 2: 
/*  541 */       this.Network.NumNodes = 6;
/*  542 */       this.Network.NumArcs = 10;
/*  543 */       setArcInfo("AB", 0.0D, 9.0D);
/*  544 */       setArcInfo("AC", 0.0D, 7.0D);
/*  545 */       setArcInfo("AF", 1.0D, -1.0D);
/*  546 */       setArcInfo("BD", 0.0D, 7.0D);
/*  547 */       setArcInfo("BE", 0.0D, 2.0D);
/*  548 */       setArcInfo("CD", 0.0D, 4.0D);
/*  549 */       setArcInfo("CE", 0.0D, 6.0D);
/*  550 */       setArcInfo("DE", 0.0D, 3.0D);
/*  551 */       setArcInfo("DF", 0.0D, 6.0D);
/*  552 */       setArcInfo("EF", 0.0D, 9.0D);
/*  553 */       setNodeInfo("A", 20.0D, "", this.NodeStringArray);
/*  554 */       setNodeInfo("F", -20.0D, "", this.NodeStringArray);
/*  555 */       break;
/*      */     case 3: 
/*  557 */       this.Network.NumNodes = 5;
/*  558 */       this.Network.NumArcs = 6;
/*  559 */       setArcInfo("AC", 3.0D, 50.0D);
/*  560 */       setArcInfo("AD", 7.0D, -1.0D);
/*  561 */       setArcInfo("BC", 4.0D, 50.0D);
/*  562 */       setArcInfo("BE", 9.0D, -1.0D);
/*  563 */       setArcInfo("CD", 2.0D, 50.0D);
/*  564 */       setArcInfo("CE", 4.0D, 50.0D);
/*  565 */       setNodeInfo("A", 80.0D, "Fcty#1", this.NodeStringArray);
/*  566 */       setNodeInfo("B", 70.0D, "Fcty#2", this.NodeStringArray);
/*  567 */       setNodeInfo("C", 0.0D, "Dist.C", this.NodeStringArray);
/*  568 */       setNodeInfo("D", -60.0D, "Whse#1", this.NodeStringArray);
/*  569 */       setNodeInfo("E", -90.0D, "Whse#2", this.NodeStringArray);
/*  570 */       break;
/*      */     case 4: 
/*  572 */       this.Network.NumNodes = 4;
/*  573 */       this.Network.NumArcs = 6;
/*  574 */       setArcInfo("AB", 8.0D, -1.0D);
/*  575 */       setArcInfo("AC", 18.0D, -1.0D);
/*  576 */       setArcInfo("AD", 31.0D, -1.0D);
/*  577 */       setArcInfo("BC", 10.0D, -1.0D);
/*  578 */       setArcInfo("BD", 21.0D, -1.0D);
/*  579 */       setArcInfo("CD", 12.0D, -1.0D);
/*  580 */       setNodeInfo("A", 1.0D, "0", this.NodeStringArray);
/*  581 */       setNodeInfo("B", 0.0D, "1", this.NodeStringArray);
/*  582 */       setNodeInfo("C", 0.0D, "2", this.NodeStringArray);
/*  583 */       setNodeInfo("D", -1.0D, "3", this.NodeStringArray);
/*  584 */       break;
/*      */     case 5: 
/*  586 */       this.Network.NumNodes = 7;
/*  587 */       this.Network.NumArcs = 12;
/*  588 */       setArcInfo("AD", 464.0D, -1.0D);
/*  589 */       setArcInfo("AE", 513.0D, -1.0D);
/*  590 */       setArcInfo("AF", 654.0D, -1.0D);
/*  591 */       setArcInfo("AG", 867.0D, -1.0D);
/*  592 */       setArcInfo("BD", 352.0D, -1.0D);
/*  593 */       setArcInfo("BE", 416.0D, -1.0D);
/*  594 */       setArcInfo("BF", 690.0D, -1.0D);
/*  595 */       setArcInfo("BG", 791.0D, -1.0D);
/*  596 */       setArcInfo("CD", 995.0D, -1.0D);
/*  597 */       setArcInfo("CE", 682.0D, -1.0D);
/*  598 */       setArcInfo("CF", 388.0D, -1.0D);
/*  599 */       setArcInfo("CG", 685.0D, -1.0D);
/*  600 */       setNodeInfo("A", 75.0D, "Cnry#1", this.NodeStringArray);
/*  601 */       setNodeInfo("B", 125.0D, "Cnry#2", this.NodeStringArray);
/*  602 */       setNodeInfo("C", 100.0D, "Cnry#3", this.NodeStringArray);
/*  603 */       setNodeInfo("D", -80.0D, "Whse#1", this.NodeStringArray);
/*  604 */       setNodeInfo("E", -65.0D, "Whse#2", this.NodeStringArray);
/*  605 */       setNodeInfo("F", -70.0D, "Whse#3", this.NodeStringArray);
/*  606 */       setNodeInfo("G", -85.0D, "Whse#4", this.NodeStringArray);
/*  607 */       break;
/*      */     case 6: 
/*  609 */       this.Network.NumNodes = 9;
/*  610 */       this.Network.NumArcs = 17;
/*  611 */       setArcInfo("AE", 16.0D, -1.0D);
/*  612 */       setArcInfo("AF", 16.0D, -1.0D);
/*  613 */       setArcInfo("AG", 13.0D, -1.0D);
/*  614 */       setArcInfo("AH", 22.0D, -1.0D);
/*  615 */       setArcInfo("AI", 17.0D, -1.0D);
/*  616 */       setArcInfo("BE", 14.0D, -1.0D);
/*  617 */       setArcInfo("BF", 14.0D, -1.0D);
/*  618 */       setArcInfo("BG", 13.0D, -1.0D);
/*  619 */       setArcInfo("BH", 19.0D, -1.0D);
/*  620 */       setArcInfo("BI", 15.0D, -1.0D);
/*  621 */       setArcInfo("CE", 19.0D, -1.0D);
/*  622 */       setArcInfo("CF", 19.0D, -1.0D);
/*  623 */       setArcInfo("CG", 20.0D, -1.0D);
/*  624 */       setArcInfo("CH", 23.0D, -1.0D);
/*  625 */       setArcInfo("DF", 0.0D, -1.0D);
/*  626 */       setArcInfo("DH", 0.0D, -1.0D);
/*  627 */       setArcInfo("DI", 0.0D, -1.0D);
/*  628 */       setNodeInfo("A", 50.0D, "Col.R.", this.NodeStringArray);
/*  629 */       setNodeInfo("B", 60.0D, "Sac.R.", this.NodeStringArray);
/*  630 */       setNodeInfo("C", 50.0D, "Cal.R.", this.NodeStringArray);
/*  631 */       setNodeInfo("D", 50.0D, "Dummy", this.NodeStringArray);
/*  632 */       setNodeInfo("E", -30.0D, "B.Min.", this.NodeStringArray);
/*  633 */       setNodeInfo("F", -20.0D, "BExtra", this.NodeStringArray);
/*  634 */       setNodeInfo("G", -70.0D, "L.D.", this.NodeStringArray);
/*  635 */       setNodeInfo("H", -30.0D, "S.G.", this.NodeStringArray);
/*  636 */       setNodeInfo("I", -60.0D, "H", this.NodeStringArray);
/*  637 */       break;
/*      */     case 7: 
/*  639 */       this.Network.NumNodes = 5;
/*  640 */       this.Network.NumArcs = 6;
/*  641 */       setArcInfo("AC", 6.0D, -1.0D);
/*  642 */       setArcInfo("AD", 7.0D, -1.0D);
/*  643 */       setArcInfo("AE", 4.0D, -1.0D);
/*  644 */       setArcInfo("BC", 5.0D, -1.0D);
/*  645 */       setArcInfo("BD", 8.0D, -1.0D);
/*  646 */       setArcInfo("BE", 6.0D, -1.0D);
/*  647 */       setNodeInfo("A", 40.0D, "S1", this.NodeStringArray);
/*  648 */       setNodeInfo("B", 60.0D, "S2", this.NodeStringArray);
/*  649 */       setNodeInfo("C", -30.0D, "D1", this.NodeStringArray);
/*  650 */       setNodeInfo("D", -40.0D, "D2", this.NodeStringArray);
/*  651 */       setNodeInfo("E", -30.0D, "D3", this.NodeStringArray);
/*  652 */       break;
/*      */     case 8: 
/*  654 */       this.Network.NumNodes = 5;
/*  655 */       this.Network.NumArcs = 7;
/*  656 */       setArcInfo("AC", 4.0D, -1.0D);
/*  657 */       setArcInfo("AD", 6.0D, 40.0D);
/*  658 */       setArcInfo("BA", 1.0D, -1.0D);
/*  659 */       setArcInfo("BC", 2.0D, -1.0D);
/*  660 */       setArcInfo("BE", 5.0D, 40.0D);
/*  661 */       setArcInfo("CD", 3.0D, -1.0D);
/*  662 */       setArcInfo("CE", 5.0D, -1.0D);
/*  663 */       setNodeInfo("A", 50.0D, "", this.NodeStringArray);
/*  664 */       setNodeInfo("B", 80.0D, "", this.NodeStringArray);
/*  665 */       setNodeInfo("C", 0.0D, "", this.NodeStringArray);
/*  666 */       setNodeInfo("D", -70.0D, "", this.NodeStringArray);
/*  667 */       setNodeInfo("E", -60.0D, "", this.NodeStringArray);
/*  668 */       break;
/*      */     default: 
/*  670 */       WhichProblem = 8;
/*      */     }
/*      */     
/*  673 */     this.Network.WhichProblem = WhichProblem;
/*  674 */     this.Network.CurrentCycle = 0;
/*  675 */     this.Network_original = ((ORSolverBase.NetworkType)this.Network.clone());
/*      */     
/*  677 */     data.which_problem = this.Network.WhichProblem;
/*  678 */     data.num_nodes = this.Network.NumNodes;
/*  679 */     data.num_arcs = this.Network.NumArcs;
/*      */     
/*  681 */     data.node_flow = new double[17];
/*  682 */     data.segment_inc = new double[17];
/*  683 */     data.deltaZ = new double[this.Network.NumArcs - this.Network.NumNodes + 2];
/*  684 */     data.notation = new String[17];
/*  685 */     data.cycle_arcs = new int[12][2];
/*      */     
/*      */ 
/*  688 */     int k = 0;
/*  689 */     for (int i = 1; i <= this.Network.NumNodes; i++) {
/*  690 */       data.node_flow[(i - 1)] = this.Network.NetFlow[i];
/*  691 */       data.notation[(i - 1)] = this.NodeStringArray[i];
/*  692 */       for (int j = 1; j <= this.Network.NumNodes; j++) {
/*  693 */         if (this.Network.AnArc[i][j] != 0) {
/*  694 */           data.arcs[k].begin = i;
/*  695 */           data.arcs[k].end = j;
/*  696 */           data.arcs[k].cost = this.Network.Cost[i][j];
/*  697 */           if (this.Network.Capacitated[i][j] != 0) {
/*  698 */             data.arcs[k].capacity = this.Network.Capacity[i][j];
/*      */           } else
/*  700 */             data.arcs[k].capacity = -1.0D;
/*  701 */           k++;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void reset()
/*      */   {
/*  709 */     this.Network = ((ORSolverBase.NetworkType)this.Network_original.clone());
/*  710 */     InitializeCurrentNetwork();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean getIntResult(String s, int[] result)
/*      */   {
/*  717 */     for (int i = 0; i < result.length; i++) {
/*  718 */       result[i] = 0;
/*      */     }
/*  720 */     s = s.toUpperCase();
/*  721 */     for (i = 0; i < s.length(); i++)
/*      */     {
/*  723 */       result[(i + 1)] = (s.charAt(i) - 'A' + 1);
/*      */     }
/*  725 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void SortArcs(IORNAProcessController.Arc[] arcs)
/*      */   {
/*  732 */     for (int i = 0; i < this.Network.NumArcs; i++) {
/*  733 */       for (int j = i + 1; j < this.Network.NumArcs; j++) {
/*  734 */         if (arcs[i].compareTo(arcs[j]) > 0) {
/*  735 */           IORNAProcessController.Arc temp = arcs[i];
/*  736 */           arcs[i] = arcs[j];
/*  737 */           arcs[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean doWork(IOROperation op, IORNAProcessController.NAData data)
/*      */   {
/*  746 */     Vector p = op.parameters;
/*  747 */     if (op.operation_code == 30110)
/*      */     {
/*  749 */       int Which = ((Integer)p.elementAt(0)).intValue();
/*  750 */       InitializeProblem(Which, data);
/*  751 */       getData(data);
/*  752 */       return true;
/*      */     }
/*      */     
/*  755 */     this.Network_temp = ((ORSolverBase.NetworkType)this.Network.clone());
/*  756 */     this.CurrentNetwork_temp = ((ORSolverBase.InteractiveNetworkType)this.CurrentNetwork.clone());
/*  757 */     if (doWork(op) == false)
/*      */     {
/*  759 */       this.Network = ((ORSolverBase.NetworkType)this.Network_temp.clone());
/*  760 */       this.CurrentNetwork = ((ORSolverBase.InteractiveNetworkType)this.CurrentNetwork_temp.clone());
/*  761 */       return false;
/*      */     }
/*      */     
/*  764 */     getData(data);
/*  765 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void getData(IORNAProcessController.NAData data)
/*      */   {
/*  772 */     for (int i = 1; i <= this.Network.NumNodes; i++)
/*  773 */       data.node_flow[(i - 1)] = this.Network.NetFlow[i];
/*  774 */     data.Z = this.Network.Z;
/*      */     
/*  776 */     if (this.Network.CurrentCycle > 0) {
/*  777 */       this.CurrentNetwork.PathZ[this.Network.CurrentCycle] = 0.0D;
/*  778 */       data.num_segment = 0.0D;
/*  779 */       for (i = 1; i <= 9; i++) {
/*  780 */         data.cycle_arcs[(i - 1)][0] = 0;
/*  781 */         data.cycle_arcs[(i - 1)][1] = 0;
/*  782 */         if ((this.CurrentNetwork.Path[this.Network.CurrentCycle][i] > 0) && (this.CurrentNetwork.Path[this.Network.CurrentCycle][(i + 1)] > 0)) {
/*  783 */           data.cycle_arcs[(i - 1)][0] = this.CurrentNetwork.Path[this.Network.CurrentCycle][i];
/*  784 */           data.cycle_arcs[(i - 1)][1] = this.CurrentNetwork.Path[this.Network.CurrentCycle][(i + 1)];
/*  785 */           if (this.Network.AnArc[this.CurrentNetwork.Path[this.Network.CurrentCycle][i]][this.CurrentNetwork.Path[this.Network.CurrentCycle][(i + 1)]] != 0) {
/*  786 */             data.segment_inc[(i - 1)] = this.Network.Cost[this.CurrentNetwork.Path[this.Network.CurrentCycle][i]][this.CurrentNetwork.Path[this.Network.CurrentCycle][(i + 1)]];
/*      */           }
/*      */           else {
/*  789 */             data.segment_inc[(i - 1)] = (-this.Network.Cost[this.CurrentNetwork.Path[this.Network.CurrentCycle][(i + 1)]][this.CurrentNetwork.Path[this.Network.CurrentCycle][i]]);
/*      */           }
/*  791 */           this.CurrentNetwork.PathZ[this.Network.CurrentCycle] += data.segment_inc[(i - 1)];
/*  792 */           data.num_segment += 1.0D;
/*      */         }
/*      */       }
/*  795 */       data.total_inc = this.CurrentNetwork.PathZ[this.Network.CurrentCycle];
/*      */     }
/*      */     else {
/*  798 */       data.cycle_arcs[0][0] = 0;
/*  799 */       data.cycle_arcs[0][1] = 0;
/*      */     }
/*  801 */     for (i = 1; i <= this.Network.NumArcs - this.Network.NumNodes + 1; i++) {
/*  802 */       data.deltaZ[(i - 1)] = this.CurrentNetwork.PathZ[i];
/*      */     }
/*  804 */     SortArcs(data.arcs);
/*  805 */     for (i = 0; i < this.Network.NumArcs; i++) {
/*  806 */       if (this.Network.AnArc[data.arcs[i].begin][data.arcs[i].end] != 0) {
/*  807 */         data.arcs[i].is_reversed = false;
/*  808 */         data.arcs[i].is_basic = this.Network.Basic[data.arcs[i].begin][data.arcs[i].end];
/*  809 */         data.arcs[i].flow = this.Network.Flow[data.arcs[i].begin][data.arcs[i].end];
/*      */       }
/*  811 */       else if (this.Network.AnArc[data.arcs[i].end][data.arcs[i].begin] != 0) {
/*  812 */         data.arcs[i].is_reversed = true;
/*  813 */         data.arcs[i].is_basic = this.Network.Basic[data.arcs[i].end][data.arcs[i].begin];
/*  814 */         data.arcs[i].flow = this.Network.Flow[data.arcs[i].end][data.arcs[i].begin];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean doWork(IOROperation op)
/*      */   {
/*  823 */     Vector p = op.parameters;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  833 */     int[] Result = new int[11];
/*  834 */     int[] Cycle = new int[11];
/*      */     String[] ArcStringArray;
/*      */     int Arcs;
/*  837 */     int m; int i; switch (op.operation_code) {
/*      */     case 30101: 
/*  839 */       this.isFirstSolverStep = true;
/*      */       
/*  841 */       ArcStringArray = (String[])p.elementAt(0);
/*  842 */       Arcs = this.Network.NumNodes - 1;
/*  843 */       m = 1; case 30102: case 30103: case 30104: case 30105: case 30106:  for (;;) { if (m <= Arcs) {
/*  844 */           getIntResult(ArcStringArray[(m - 1)], Result);
/*      */           
/*  846 */           boolean Flag = false;
/*  847 */           if ((Result[1] > 0) && (Result[2] > 0) && 
/*  848 */             (this.Network.AnArc[Result[1]][Result[2]] != 0) && 
/*  849 */             (this.Network.Basic[Result[1]][Result[2]] == 0))
/*      */           {
/*  851 */             Cycle[1] = Result[1];
/*  852 */             Cycle[2] = Result[2];
/*  853 */             for (int i = 3; i <= 10; i++)
/*  854 */               Cycle[i] = 0;
/*  855 */             i = 3;
/*      */             do {
/*  857 */               Cycle[i] += 1;
/*  858 */               while ((Cycle[i] <= this.Network.NumNodes) && ((Cycle[i] == Cycle[(i - 1)]) || (Cycle[i] == Cycle[(i - 2)]) || ((this.Network.Basic[Cycle[(i - 1)]][Cycle[i]] == 0) && (this.Network.Basic[Cycle[i]][Cycle[(i - 1)]] == 0))))
/*  859 */                 Cycle[i] += 1;
/*  860 */               if (Cycle[i] > this.Network.NumNodes)
/*      */               {
/*  862 */                 Cycle[i] = 0;
/*  863 */                 i -= 1;
/*      */               }
/*      */               else {
/*  866 */                 i += 1;
/*  867 */               } } while (Cycle[(i - 1)] != Cycle[1]);
/*  868 */             if (i != 2) {
/*  869 */               this.errInfo = "Adding this arc to the basis would create a cycle.";
/*  870 */               return false;
/*      */             }
/*      */             
/*  873 */             this.Network.Basic[Result[1]][Result[2]] = 1;
/*  874 */             this.Network.WhichBasic[m][1] = Result[1];
/*  875 */             this.Network.WhichBasic[m][2] = Result[2];
/*  876 */             Flag = true;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  881 */           if (Flag == false) {
/*  882 */             if (this.Network.AnArc[Result[1]][Result[2]] == 0) {
/*  883 */               this.errInfo = "This is not a valid arc.";
/*      */             } else
/*  885 */               this.errInfo = "You already have entered this arc as a basic arc.";
/*  886 */             return false;
/*      */           }
/*  843 */           m++; continue;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  892 */           int Arcs = ((Integer)p.elementAt(0)).intValue();
/*  893 */           String[] ArcStringArray = (String[])p.elementAt(1);
/*  894 */           for (int m = 1; m <= Arcs; m++) {
/*  895 */             getIntResult(ArcStringArray[(m - 1)], Result);
/*      */             
/*  897 */             boolean Flag = false;
/*  898 */             if ((Result[1] > 0) && (Result[2] > 0) && 
/*  899 */               (this.Network.AnArc[Result[1]][Result[2]] != 0) && (this.Network.Capacitated[Result[1]][Result[2]] != 0) && (this.Network.Reversed[Result[1]][Result[2]] == 0) && 
/*  900 */               (this.Network.Basic[Result[1]][Result[2]] == 0)) {
/*  901 */               this.Network.WhichReversed[m][1] = Result[1];
/*  902 */               this.Network.WhichReversed[m][2] = Result[2];
/*  903 */               this.Network.NetFlow[Result[1]] -= this.Network.Capacity[Result[1]][Result[2]];
/*  904 */               this.Network.NetFlow[Result[2]] += this.Network.Capacity[Result[1]][Result[2]];
/*  905 */               this.Network.AnArc[Result[1]][Result[2]] = 0;
/*  906 */               this.Network.AnArc[Result[2]][Result[1]] = 1;
/*  907 */               Flag = true;
/*      */             }
/*      */             
/*      */ 
/*  911 */             if (Flag == false) {
/*  912 */               this.errInfo = "This is not a valid nonbasic arc.";
/*  913 */               return false;
/*      */             }
/*      */           }
/*  916 */           CalculateNetworkFlows();
/*  917 */           boolean Flag = true;
/*  918 */           for (int i = 1; i <= this.Network.NumNodes - 1; i++) {
/*  919 */             if ((this.Network.FlowVector[i] < -1.0E-4D) || ((this.Network.FlowVector[i] > this.Network.Capacity[this.Network.WhichBasic[i][1]][this.Network.WhichBasic[i][2]] + 1.0E-4D) && (this.Network.NetworkCapacitated) && (this.Network.Capacitated[this.Network.WhichBasic[i][1]][this.Network.WhichBasic[i][2]] != 0)))
/*  920 */               Flag = false;
/*      */           }
/*  922 */           if (Flag) {
/*  923 */             this.Network.HowManyReversed = Arcs;
/*      */           }
/*      */           else {
/*  926 */             this.errInfo = "This set of basic arcs do not allow a feasible solution.";
/*  927 */             return false;
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*  932 */             String[] ArcStringArray = (String[])p.elementAt(0);
/*  933 */             double[] FlowArray = (double[])p.elementAt(1);
/*  934 */             int Arcs = ArcStringArray.length;
/*  935 */             for (int m = 1; m <= Arcs; m++) {
/*  936 */               getIntResult(ArcStringArray[(m - 1)], Result);
/*      */               
/*  938 */               boolean Flag = false;
/*  939 */               if ((Result[1] > 0) && (Result[2] > 0))
/*      */               {
/*  941 */                 if ((this.Network.AnArc[Result[1]][Result[2]] != 0) && (this.Network.Basic[Result[1]][Result[2]] != 0))
/*      */                 {
/*  943 */                   Flag = true;
/*  944 */                   for (int i = 1; i <= m - 1; i++) {
/*  945 */                     if ((Result[1] == this.Network.WhichFlow[i][1]) && (Result[2] == this.Network.WhichFlow[i][2]))
/*  946 */                       Flag = false;
/*      */                   }
/*      */                 }
/*      */               }
/*  950 */               if (Flag == false) {
/*  951 */                 if ((this.Network.AnArc[Result[1]][Result[2]] != 0) && (this.Network.Basic[Result[1]][Result[2]] != 0)) {
/*  952 */                   this.errInfo = "The flow for this arc has already been entered.";
/*      */                 } else
/*  954 */                   this.errInfo = "This is not a valid basic arc.";
/*  955 */                 return false;
/*      */               }
/*      */               
/*  958 */               for (int i = 1; i <= this.Network.NumNodes - 1; i++) {
/*  959 */                 if ((Result[1] == this.Network.WhichBasic[i][1]) && (Result[2] == this.Network.WhichBasic[i][2]) && (Math.abs(FlowArray[(m - 1)] - this.Network.FlowVector[i]) > 0.05D))
/*  960 */                   Flag = false;
/*      */               }
/*  962 */               if (Flag) {
/*  963 */                 this.Network.WhichFlow[m][1] = Result[1];
/*  964 */                 this.Network.WhichFlow[m][2] = Result[2];
/*  965 */                 this.Network.Flow[Result[1]][Result[2]] = FlowArray[(m - 1)];
/*      */               }
/*      */               else {
/*  968 */                 this.errInfo = "Sorry. This is incorrect. Please try again.";
/*  969 */                 return false;
/*      */               }
/*      */             }
/*      */             
/*  973 */             boolean Flag = true;
/*  974 */             for (int i = 1; i <= this.Network.NumNodes - 1; i++) {
/*  975 */               if (Math.abs(this.Network.Flow[this.Network.WhichBasic[i][1]][this.Network.WhichBasic[i][2]] - this.Network.FlowVector[i]) > 0.05D)
/*  976 */                 Flag = false;
/*      */             }
/*  978 */             if (Flag) {
/*  979 */               for (i = 1; i <= this.Network.NumNodes; i++) {
/*  980 */                 for (int j = 1; j <= this.Network.NumNodes; j++) {
/*  981 */                   if ((this.Network.AnArc[i][j] != 0) && (this.Network.Basic[i][j] != 0)) {
/*  982 */                     Flag = false;
/*  983 */                     for (int k = 1; k <= Arcs; k++) {
/*  984 */                       if ((this.Network.WhichFlow[k][1] == i) && (this.Network.WhichFlow[k][2] == j))
/*  985 */                         Flag = true;
/*      */                     }
/*  987 */                     if (Flag == false)
/*      */                     {
/*  989 */                       Arcs += 1;
/*  990 */                       this.Network.WhichFlow[Arcs][1] = i;
/*  991 */                       this.Network.WhichFlow[Arcs][2] = j;
/*      */                     }
/*      */                   }
/*      */                 }
/*  995 */                 NetworkSetupPath();
/*      */               }
/*      */             }
/*      */             
/*  999 */             this.errInfo = "Sorry. This is incorrect. Please try again.";
/* 1000 */             return false;
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1006 */             int CycleNo = ((Integer)p.elementAt(0)).intValue();
/* 1007 */             String CycleString = (String)p.elementAt(1);
/* 1008 */             getIntResult(CycleString, Result);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1020 */             this.Network.CurrentCycle = CycleNo;
/*      */             
/* 1022 */             boolean Flag = true;
/* 1023 */             if ((Result[1] == 0) || (Result[2] == 0)) {
/* 1024 */               Flag = false;
/*      */             } else {
/* 1026 */               for (int i = 1; i <= 9; i++) {
/* 1027 */                 if ((i <= 2) && 
/* 1028 */                   (Result[i] != this.CurrentNetwork.Path[CycleNo][i])) {
/* 1029 */                   Flag = false;
/*      */                 }
/* 1031 */                 if ((i > 2) && (Result[i] != 0) && (Result[(i + 1)] == 0) && 
/* 1032 */                   (Result[i] != this.CurrentNetwork.Path[CycleNo][1])) {
/* 1033 */                   Flag = false;
/*      */                 }
/* 1035 */                 if ((i >= 2) && (Result[i] != 0) && (Result[(i + 1)] != 0) && 
/* 1036 */                   (this.Network.Basic[Result[i]][Result[(i + 1)]] == 0) && (this.Network.Basic[Result[(i + 1)]][Result[i]] == 0)) {
/* 1037 */                   Flag = false;
/*      */                 }
/*      */               }
/*      */             }
/* 1041 */             if (Flag) {
/* 1042 */               this.CurrentNetwork.PathZ[CycleNo] = 0.0D;
/* 1043 */               for (int i = 1; i <= 10; i++) {
/* 1044 */                 this.CurrentNetwork.Path[CycleNo][i] = Result[i];
/*      */               }
/*      */             }
/* 1047 */             this.errInfo = "Sorry. This is incorrect. Please try again.";
/* 1048 */             return false;
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1053 */             String ArcString = (String)p.elementAt(0);
/* 1054 */             getIntResult(ArcString, Result);
/*      */             
/*      */ 
/* 1057 */             double Temp = 1.0E20D;
/* 1058 */             boolean Flag = false;
/* 1059 */             for (int i = 1; i <= 9; i++) {
/* 1060 */               if ((this.CurrentNetwork.Path[i][1] > 0) && (this.CurrentNetwork.PathZ[i] < Temp))
/* 1061 */                 Temp = this.CurrentNetwork.PathZ[i];
/*      */             }
/* 1063 */             if ((Result[1] > 0) && (Result[2] > 0)) {
/* 1064 */               for (i = 1; i <= 9; i++) {
/* 1065 */                 if ((this.CurrentNetwork.Path[i][1] == Result[1]) && (this.CurrentNetwork.Path[i][2] == Result[2])) {
/* 1066 */                   this.CurrentNetwork.EnteringArc = i;
/* 1067 */                   if (this.CurrentNetwork.PathZ[i] < Temp + 0.05D) {
/* 1068 */                     Flag = true;
/* 1069 */                     break;
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/* 1074 */             if ((this.isFirstSolverStep == true) && (Flag == false)) {
/* 1075 */               this.errInfo = "Sorry, that is incorrect. Please try again.";
/* 1076 */               return false;
/*      */             }
/* 1078 */             this.Network.CurrentCycle = this.CurrentNetwork.EnteringArc;
/*      */             
/*      */             break label5156;
/*      */             
/* 1082 */             String ArcString = (String)p.elementAt(0);
/* 1083 */             this.CurrentNetwork.EnteringArcFlow = ((Double)p.elementAt(1)).doubleValue();
/* 1084 */             getIntResult(ArcString, Result);
/*      */             
/*      */ 
/* 1087 */             double Temp = 1.0E20D;
/* 1088 */             int Which = 0;
/* 1089 */             for (i = 1; i <= 9; i++) {
/* 1090 */               if (this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)] > 0) {
/* 1091 */                 if (this.Network.AnArc[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]] != 0) {
/* 1092 */                   if ((this.Network.Capacitated[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]] != 0) && 
/* 1093 */                     (this.Network.Capacity[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]] - this.Network.Flow[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]] < Temp)) {
/* 1094 */                     Temp = this.Network.Capacity[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]] - this.Network.Flow[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]];
/*      */                   }
/*      */                   
/*      */                 }
/* 1098 */                 else if (this.Network.Flow[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]] < Temp) {
/* 1099 */                   Temp = this.Network.Flow[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]];
/*      */                 }
/*      */               }
/*      */             }
/* 1103 */             if ((Result[1] > 0) && (Result[2] > 0)) {
/* 1104 */               if (this.Network.AnArc[Result[1]][Result[2]] != 0) {
/* 1105 */                 this.CurrentNetwork.LeavingArc = 0;
/* 1106 */                 for (i = 1; i <= 10; i++) {
/* 1107 */                   if (((this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i] == Result[1]) && (this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)] == Result[2])) || ((this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)] == Result[1]) && (this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i] == Result[2]))) {
/* 1108 */                     boolean Flag = true;
/* 1109 */                     if (this.Network.AnArc[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]] != 0) {
/* 1110 */                       if (this.Network.Capacitated[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]] != 0) {
/* 1111 */                         if (this.Network.Capacity[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]] - this.Network.Flow[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]] > Temp + 0.1D) {
/* 1112 */                           Flag = false;
/*      */                         }
/*      */                       } else {
/* 1115 */                         Flag = false;
/*      */                       }
/*      */                     }
/* 1118 */                     else if (this.Network.Flow[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]] > Temp + 0.1D) {
/* 1119 */                       Flag = false;
/*      */                     }
/* 1121 */                     if ((Flag) || (this.isFirstSolverStep == false)) {
/* 1122 */                       this.CurrentNetwork.LeavingArc = i;
/*      */                     }
/*      */                     else {
/* 1125 */                       this.errInfo = "Sorry, that is incorrect. Please try again.";
/* 1126 */                       return false;
/*      */                     }
/*      */                   }
/*      */                 }
/* 1130 */                 if (this.CurrentNetwork.LeavingArc == 0) {
/* 1131 */                   this.errInfo = "Sorry, that is incorrect. Please try again.";
/* 1132 */                   return false;
/*      */                 }
/*      */               }
/*      */               else {
/* 1136 */                 this.errInfo = "This is not a valid arc.";
/* 1137 */                 return false;
/*      */               }
/*      */             }
/*      */             else {
/* 1141 */               this.errInfo = "This is not a valid arc.";
/* 1142 */               return false;
/*      */             }
/*      */             
/*      */ 
/* 1146 */             if ((Math.abs(this.CurrentNetwork.EnteringArcFlow - Temp) < 0.05D) || (this.isFirstSolverStep == false)) {
/* 1147 */               this.Network.Basic[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][1]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][2]] = 1;
/* 1148 */               for (i = 1; i <= 10;) {
/* 1149 */                 if ((this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i] > 0) && (this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)] > 0)) {
/* 1150 */                   if (this.Network.AnArc[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]] != 0) {
/* 1151 */                     this.Network.Flow[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]] += this.CurrentNetwork.EnteringArcFlow;
/* 1152 */                     if (i == this.CurrentNetwork.LeavingArc) {
/* 1153 */                       this.Network.Basic[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]] = 0;
/*      */                     }
/*      */                   } else {
/* 1156 */                     this.Network.Flow[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]] -= this.CurrentNetwork.EnteringArcFlow;
/* 1157 */                     if (i == this.CurrentNetwork.LeavingArc) {
/* 1158 */                       this.Network.Basic[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(i + 1)]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][i]] = 0;
/*      */                     }
/*      */                   }
/*      */                 }
/* 1148 */                 i++; continue;
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1164 */                 this.errInfo = "Sorry, that is incorrect. Please try again.";
/* 1165 */                 return false;
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/* 1170 */                 this.CurrentNetwork.ReverseLeavingArc = ((Boolean)p.elementAt(0)).booleanValue();
/* 1171 */                 if ((this.isFirstSolverStep == false) || (this.CurrentNetwork.ReverseLeavingArc == this.Network.AnArc[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][this.CurrentNetwork.LeavingArc]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(this.CurrentNetwork.LeavingArc + 1)]])) {
/* 1172 */                   if (this.CurrentNetwork.ReverseLeavingArc) { int j;
/* 1173 */                     int i; int j; if (this.Network.AnArc[this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][this.CurrentNetwork.LeavingArc]][this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(this.CurrentNetwork.LeavingArc + 1)]] != 0) {
/* 1174 */                       int i = this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][this.CurrentNetwork.LeavingArc];
/* 1175 */                       j = this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(this.CurrentNetwork.LeavingArc + 1)];
/*      */                     }
/*      */                     else {
/* 1178 */                       i = this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(this.CurrentNetwork.LeavingArc + 1)];
/* 1179 */                       j = this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][this.CurrentNetwork.LeavingArc];
/*      */                     }
/* 1181 */                     this.Network.NetFlow[i] -= this.Network.Capacity[i][j];
/* 1182 */                     this.Network.NetFlow[j] += this.Network.Capacity[i][j];
/* 1183 */                     this.Network.Flow[j][i] = 0.0D;
/* 1184 */                     this.Network.AnArc[i][j] = 0;
/* 1185 */                     this.Network.AnArc[j][i] = 1;
/*      */                   }
/*      */                 }
/*      */                 else {
/* 1189 */                   this.errInfo = "Sorry, that is incorrect. Please try again.";
/* 1190 */                   return false;
/*      */                 }
/*      */                 
/* 1193 */                 this.isFirstSolverStep = false;
/* 1194 */                 break;
/*      */                 
/*      */ 
/* 1197 */                 int k = 0;
/* 1198 */                 for (i = 1; i <= this.Network.NumNodes; i++) {
/* 1199 */                   for (int j = 1; j <= this.Network.NumNodes; j++) {
/* 1200 */                     if ((this.Network.AnArc[i][j] != 0) && (this.Network.Basic[i][j] == 0)) {
/* 1201 */                       k += 1;
/* 1202 */                       this.CurrentNetwork.Path[k][1] = i;
/* 1203 */                       this.CurrentNetwork.Path[k][2] = j;
/* 1204 */                       for (int m = 3; m <= 10; m++)
/* 1205 */                         this.CurrentNetwork.Path[k][m] = 0;
/*      */                     }
/*      */                   }
/*      */                 }
/* 1209 */                 i = 1;
/* 1210 */                 while (this.CurrentNetwork.Path[i][1] > 0) {
/* 1211 */                   int j = 3;
/*      */                   do {
/* 1213 */                     this.CurrentNetwork.Path[i][j] += 1;
/* 1214 */                     while ((this.CurrentNetwork.Path[i][j] <= this.Network.NumNodes) && ((this.CurrentNetwork.Path[i][j] == this.CurrentNetwork.Path[i][(j - 1)]) || (this.CurrentNetwork.Path[i][j] == this.CurrentNetwork.Path[i][(j - 2)]) || ((this.Network.Basic[this.CurrentNetwork.Path[i][(j - 1)]][this.CurrentNetwork.Path[i][j]] == 0) && (this.Network.Basic[this.CurrentNetwork.Path[i][j]][this.CurrentNetwork.Path[i][(j - 1)]] == 0))))
/* 1215 */                       this.CurrentNetwork.Path[i][j] += 1;
/* 1216 */                     if (this.CurrentNetwork.Path[i][j] > this.Network.NumNodes) {
/* 1217 */                       this.CurrentNetwork.Path[i][j] = 0;
/* 1218 */                       j -= 1;
/*      */                     }
/* 1220 */                     else if (this.CurrentNetwork.Path[i][j] != this.CurrentNetwork.Path[i][1]) {
/* 1221 */                       j += 1;
/* 1222 */                     } } while (this.CurrentNetwork.Path[i][j] != this.CurrentNetwork.Path[i][1]);
/* 1223 */                   i += 1;
/*      */                 }
/* 1225 */                 for (j = 1; j <= 9;) {
/* 1226 */                   if (this.CurrentNetwork.Path[j][1] > 0) {
/* 1227 */                     this.CurrentNetwork.PathZ[j] = 0.0D;
/* 1228 */                     for (i = 1; i <= 9; i++) {
/* 1229 */                       if ((this.CurrentNetwork.Path[j][i] > 0) && (this.CurrentNetwork.Path[j][(i + 1)] > 0)) {
/* 1230 */                         if (this.Network.AnArc[this.CurrentNetwork.Path[j][i]][this.CurrentNetwork.Path[j][(i + 1)]] != 0) {
/* 1231 */                           this.CurrentNetwork.PathZ[j] += this.Network.Cost[this.CurrentNetwork.Path[j][i]][this.CurrentNetwork.Path[j][(i + 1)]];
/*      */                         } else {
/* 1233 */                           this.CurrentNetwork.PathZ[j] -= this.Network.Cost[this.CurrentNetwork.Path[j][(i + 1)]][this.CurrentNetwork.Path[j][i]];
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                   }
/* 1225 */                   j++; continue;
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1241 */                   int Arcs = ((Integer)p.elementAt(0)).intValue();
/* 1242 */                   ArcStringArray = (String[])p.elementAt(1);
/* 1243 */                   FlowArray = (double[])p.elementAt(2);
/*      */                   
/* 1245 */                   CalcNetworkZ();
/* 1246 */                   for (i = 0; i < Arcs;)
/*      */                   {
/* 1248 */                     getIntResult(ArcStringArray[i], Result);
/*      */                     
/* 1250 */                     if ((this.Network.AnArc[Result[1]][Result[2]] != 0) && (this.Network.Reversed[Result[1]][Result[2]] != 0)) {
/* 1251 */                       this.Network.AnArc[Result[1]][Result[2]] = 0;
/* 1252 */                       this.Network.AnArc[Result[2]][Result[1]] = 1;
/* 1253 */                       this.Network.Flow[Result[2]][Result[1]] = FlowArray[i];
/* 1254 */                       this.Network.NetFlow[Result[1]] -= FlowArray[i];
/* 1255 */                       this.Network.NetFlow[Result[2]] += FlowArray[i];
/*      */                     }
/* 1246 */                     i++; continue;
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1261 */                     CalcNetworkZ(); } } } } } } } }
/*      */     int i;
/*      */     int j;
/*      */     String[] ArcStringArray;
/* 1265 */     double[] FlowArray; int i; label5156: return true;
/*      */   }
/*      */   
/*      */ 
/*      */   protected void ORPrint()
/*      */   {
/* 1271 */     PrintNetworkSolveInteractively();
/*      */   }
/*      */   
/*      */ 
/*      */   private void PrintNetworkKey()
/*      */   {
/* 1277 */     PrintLine("Key:");
/* 1278 */     SkipLine();
/* 1279 */     for (int i = 1; i <= this.Network.NumNodes; i++) {
/* 1280 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("").append((char)(64 + i)).append(" = ").append(this.NodeStringArray[i]))));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void PrintNetwork(int CurrentEditRow)
/*      */   {
/* 1287 */     int CapacityY = 1;
/*      */     
/* 1289 */     int k = 0;
/* 1290 */     boolean AnyReversed = false;
/* 1291 */     for (int i = 1; i <= this.Network.NumNodes; i++) {
/* 1292 */       for (int j = 1; j <= this.Network.NumNodes; j++) {
/* 1293 */         if (this.Network.AnArc[i][j] != 0) {
/* 1294 */           k += 1;
/* 1295 */           if ((CapacityY > 0) && (this.Network.Reversed[i][j] != 0))
/* 1296 */             AnyReversed = true;
/*      */         }
/*      */       }
/*      */     }
/* 1300 */     if (AnyReversed) {
/* 1301 */       PrintLines(3 + k);
/*      */     } else
/* 1303 */       PrintLines(2 + k);
/* 1304 */     if ((AnyReversed) || (CurrentEditRow == 14))
/* 1305 */       TabPrintLine(62, "Adjusted");
/* 1306 */     Print("Arc | Cost | Flow | Basic ");
/* 1307 */     if (CapacityY > 0)
/* 1308 */       Print("| Capacity | Reversed");
/* 1309 */     TabPrintLine(55, "Node | Net Flow");
/* 1310 */     Print("____|______|______|_______");
/* 1311 */     if (CapacityY > 0)
/* 1312 */       Print("|__________|__________");
/* 1313 */     TabPrintLine(54, "______|__________");
/* 1314 */     k = 0;
/* 1315 */     for (i = 1; i <= this.Network.NumNodes; i++) {
/* 1316 */       for (int j = 1; j <= this.Network.NumNodes; j++) {
/* 1317 */         if (this.Network.AnArc[i][j] != 0) {
/* 1318 */           k += 1;
/* 1319 */           if ((CurrentEditRow == 14) && (this.Network.Reversed[i][j] != 0)) {
/* 1320 */             if (this.Network.Basic[i][j] != 0) {
/* 1321 */               Print(String.valueOf(String.valueOf(new StringBuffer(" ").append((char)(64 + j)).append((char)(64 + i)).append(" | ").append(FormatReal(this.Network.Cost[j][i], 4, 0, 1)).append(" | ").append(FormatReal(this.Network.Capacity[j][i] - this.Network.Flow[i][j], 4, 0, 1)).append(" | "))));
/*      */             } else {
/* 1323 */               Print(String.valueOf(String.valueOf(new StringBuffer(" ").append((char)(64 + j)).append((char)(64 + i)).append(" | ").append(FormatReal(this.Network.Cost[j][i], 4, 0, 1)).append(" | ").append(FormatReal(this.Network.Capacity[j][i], 4, 0, 1)).append(" | "))));
/*      */             }
/*      */           } else
/* 1326 */             Print(String.valueOf(String.valueOf(new StringBuffer(" ").append((char)(64 + i)).append((char)(64 + j)).append(" | ").append(FormatReal(this.Network.Cost[i][j], 4, 0, 1)).append(" | ").append(FormatReal(this.Network.Flow[i][j], 4, 0, 1)).append(" | "))));
/* 1327 */           if (this.Network.Basic[i][j] != 0) {
/* 1328 */             Print(" Yes");
/*      */           } else
/* 1330 */             Print(" No ");
/* 1331 */           if (CapacityY > 0) {
/* 1332 */             Print("  | ");
/* 1333 */             if (this.Network.Capacitated[i][j] != 0) {
/* 1334 */               Print(String.valueOf(String.valueOf(new StringBuffer("  ").append(FormatReal(this.Network.Capacity[i][j], 3, 0, 1)).append("    | "))));
/*      */             } else
/* 1336 */               Print("Infinity | ");
/* 1337 */             if ((this.Network.Reversed[i][j] != 0) && (CurrentEditRow < 14)) {
/* 1338 */               Print("  Yes");
/*      */             } else
/* 1340 */               Print("   No"); }
/*      */           double Temp;
/* 1342 */           double Temp; if (CurrentEditRow != 14) {
/* 1343 */             Temp = this.Network.NetFlow[k];
/*      */           } else {
/* 1345 */             Temp = this.Network.NetFlow[k];
/* 1346 */             for (int l = 1; l <= this.Network.NumNodes; l++) {
/* 1347 */               if ((this.Network.AnArc[k][l] != 0) && (this.Network.Reversed[k][l] != 0))
/* 1348 */                 Temp -= this.Network.Capacity[k][l];
/*      */             }
/* 1350 */             for (l = 1; l <= this.Network.NumNodes; l++) {
/* 1351 */               if ((this.Network.AnArc[l][k] != 0) && (this.Network.Reversed[l][k] != 0))
/* 1352 */                 Temp += this.Network.Capacity[l][k];
/*      */             }
/*      */           }
/* 1355 */           TabPrintLine(57, String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf((char)(64 + k)))).append("  |   ").append(FormatReal(Temp, 4, 0, 1)))));
/*      */         }
/*      */       }
/*      */     }
/* 1359 */     SkipLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void PrintNetworkSolveInteractively()
/*      */   {
/* 1367 */     double[][] TempFlow = new double[17][17];
/*      */     
/*      */ 
/*      */ 
/* 1371 */     int CurrentEditRow = 1;
/*      */     
/*      */ 
/* 1374 */     int n = this.steps.size();
/* 1375 */     if (n == 0) { return;
/*      */     }
/* 1377 */     int CER = CurrentEditRow;
/* 1378 */     CurrentEditRow = 1;
/* 1379 */     ORSolverBase.NetworkType TempNetwork = this.Network;
/* 1380 */     ORSolverBase.InteractiveNetworkType TempCurrentNetwork = (ORSolverBase.InteractiveNetworkType)this.CurrentNetwork.clone();
/* 1381 */     this.Network = ((ORSolverBase.NetworkType)this.Network_original.clone());
/* 1382 */     InitializeCurrentNetwork();
/*      */     
/* 1384 */     PrintLine("Solve Model Interactively by the Network Simplex Method:");
/* 1385 */     SkipLine();
/* 1386 */     PrintLine("Problem # 9.7-".concat(String.valueOf(String.valueOf(FormatInteger(this.Network.WhichProblem, 1, 0, 0)))));
/* 1387 */     SkipLine();
/* 1388 */     if (this.NodeStringArray[1].length() > 0)
/* 1389 */       PrintNetworkKey();
/* 1390 */     PrintLine("Initial Network:");
/* 1391 */     SkipLine();
/* 1392 */     PrintNetwork(CurrentEditRow);
/*      */     
/* 1394 */     IOROperation op = null;
/* 1395 */     int iStep = 0;
/* 1396 */     int Iteration = 1;
/* 1397 */     while (iStep < n) {
/* 1398 */       op = (IOROperation)this.steps.elementAt(iStep);
/* 1399 */       switch (op.operation_code) {
/*      */       case 30103: 
/* 1401 */         doWork(op);
/* 1402 */         PrintLines(17);
/* 1403 */         PrintLine("Iteration #1:");
/* 1404 */         SkipLine();
/* 1405 */         PrintNetwork(CurrentEditRow);
/* 1406 */         break;
/*      */       case 30105: 
/* 1408 */         doWork(op);
/* 1409 */         PrintLines(9);
/* 1410 */         PrintLine("Arc | Path     | dZ when Theta=1");
/* 1411 */         PrintLine("____|__________|________________");
/* 1412 */         for (int i = 1; i <= 9; i++) {
/* 1413 */           if (this.CurrentNetwork.Path[i][1] > 0) {
/* 1414 */             Print(String.valueOf(String.valueOf(new StringBuffer(" ").append((char)(this.CurrentNetwork.Path[i][1] + 64)).append((char)(this.CurrentNetwork.Path[i][2] + 64)).append(" | "))));
/* 1415 */             int j = 1;
/* 1416 */             while ((j < 9) && (this.CurrentNetwork.Path[i][j] > 0)) {
/* 1417 */               Print("".concat(String.valueOf(String.valueOf((char)(this.CurrentNetwork.Path[i][j] + 64)))));
/* 1418 */               j += 1;
/*      */             }
/* 1420 */             TabPrint(16, "| ".concat(String.valueOf(String.valueOf(FormatReal(this.CurrentNetwork.PathZ[i], 6, 0, 0)))));
/*      */           }
/* 1422 */           if (i == 1) {
/* 1423 */             TabPrint(36, "Is the current network optimal? ");
/* 1424 */             if (this.CurrentNetwork.Optimal) {
/* 1425 */               TabPrint(72, "Yes");
/*      */             } else {
/* 1427 */               TabPrint(72, "No");
/*      */             }
/* 1429 */           } else if (i == 2) {
/* 1430 */             TabPrint(36, "Entering basic arc:");
/* 1431 */             TabPrint(72, String.valueOf(String.valueOf(new StringBuffer("").append((char)(64 + this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][1])).append((char)(64 + this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][2])))));
/*      */           }
/* 1433 */           else if (i == 3) {
/* 1434 */             if (iStep + 1 < n)
/*      */             {
/* 1436 */               op = (IOROperation)this.steps.elementAt(iStep + 1);
/* 1437 */               doWork(op);
/* 1438 */               TabPrint(36, "Leaving basic arc:");
/* 1439 */               TabPrint(72, String.valueOf(String.valueOf(new StringBuffer("").append((char)(64 + this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][this.CurrentNetwork.LeavingArc])).append((char)(64 + this.CurrentNetwork.Path[this.CurrentNetwork.EnteringArc][(this.CurrentNetwork.LeavingArc + 1)])))));
/*      */             }
/*      */           }
/* 1442 */           else if (i == 4) {
/* 1443 */             if (iStep + 1 < n) {
/* 1444 */               if (iStep + 2 >= n) iStep++;
/* 1445 */               TabPrint(36, "Value of Theta:");
/* 1446 */               TabPrint(72, FormatReal(this.CurrentNetwork.EnteringArcFlow, 6, 0, 0));
/*      */             }
/*      */           }
/* 1449 */           else if ((i == 5) && 
/* 1450 */             (iStep + 2 < n)) {
/* 1451 */             iStep += 2;
/* 1452 */             op = (IOROperation)this.steps.elementAt(iStep);
/* 1453 */             doWork(op);
/* 1454 */             TabPrint(36, "Should the leaving arc be reversed?");
/* 1455 */             if (this.CurrentNetwork.ReverseLeavingArc) {
/* 1456 */               TabPrint(72, "Yes");
/*      */             } else {
/* 1458 */               TabPrint(72, "No");
/*      */             }
/*      */           }
/* 1461 */           if (this.PrinterX > 5)
/* 1462 */             SkipLine();
/*      */         }
/* 1464 */         Iteration++;
/* 1465 */         Skip(2);
/* 1466 */         PrintLines(14);
/* 1467 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("Iteration #").append(IntegerToString(Iteration, 2)).append(":"))));
/* 1468 */         SkipLine();
/* 1469 */         PrintNetwork(CurrentEditRow);
/* 1470 */         break;
/*      */       case 30109: 
/*      */       case 30111: 
/* 1473 */         PrintLines(9);
/* 1474 */         PrintLine("Arc | Path     | dZ when Theta=1");
/* 1475 */         PrintLine("____|__________|________________");
/* 1476 */         for (int i = 1; i <= 9; i++) {
/* 1477 */           if (this.CurrentNetwork.Path[i][1] > 0) {
/* 1478 */             Print(String.valueOf(String.valueOf(new StringBuffer(" ").append((char)(this.CurrentNetwork.Path[i][1] + 64)).append((char)(this.CurrentNetwork.Path[i][2] + 64)).append(" | "))));
/* 1479 */             int j = 1;
/* 1480 */             while ((j < 9) && (this.CurrentNetwork.Path[i][j] > 0)) {
/* 1481 */               Print("".concat(String.valueOf(String.valueOf((char)(this.CurrentNetwork.Path[i][j] + 64)))));
/* 1482 */               j += 1;
/*      */             }
/* 1484 */             TabPrint(16, "| ".concat(String.valueOf(String.valueOf(FormatReal(this.CurrentNetwork.PathZ[i], 6, 0, 0)))));
/*      */           }
/* 1486 */           if (i == 1) {
/* 1487 */             TabPrint(36, "Is the current network optimal? ");
/* 1488 */             TabPrint(72, "Yes");
/*      */           }
/* 1490 */           if (this.PrinterX > 5)
/* 1491 */             SkipLine();
/*      */         }
/* 1493 */         Iteration++;
/* 1494 */         Skip(2);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1499 */         doWork(op);
/* 1500 */         CurrentEditRow = 14;
/* 1501 */         break;
/*      */       case 30104: case 30106: case 30107: case 30108: case 30110: default: 
/* 1503 */         doWork(op);
/* 1504 */         break;
/*      */       }
/*      */       
/* 1507 */       iStep++;
/*      */     }
/* 1509 */     if (CurrentEditRow == 14) {
/* 1510 */       PrintLines(17);
/* 1511 */       PrintLine("Optimal Solution (with flows through all of the original arcs):");
/* 1512 */       SkipLine();
/* 1513 */       PrintNetwork(CurrentEditRow);
/* 1514 */       Print("Cost of the optimal solution: ".concat(String.valueOf(String.valueOf(FormatReal(this.Network.Z, 10, 0, 0)))));
/*      */     }
/* 1516 */     this.Network = TempNetwork;
/* 1517 */     this.CurrentNetwork = TempCurrentNetwork;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORNASolver.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */