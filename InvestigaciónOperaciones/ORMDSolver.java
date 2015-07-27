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
/*      */ public class ORMDSolver
/*      */   extends ORSolverBase
/*      */ {
/*      */   private int MarkovCurrentIteration;
/*      */   private int MarkovDCurrentIteration;
/*      */   private int MarkovSCurrentIteration;
/*      */   private int MarkovNumStates;
/*      */   private int MarkovNumDecisions;
/*      */   private double MarkovDiscountFactor;
/*   31 */   private double[][][] MarkovP = new double[5][5][6];
/*   32 */   private double[][] MarkovC = new double[5][6];
/*   33 */   private int[] Markovd = new int[5];
/*   34 */   private double[][] MarkovA = new double[11][11];
/*   35 */   private double[] Markovb = new double[11];
/*      */   
/*   37 */   private ORSolverBase.MarkovPIType CurrentMarkov = new ORSolverBase.MarkovPIType(this);
/*   38 */   private ORSolverBase.MarkovPIType CurrentMarkovD = new ORSolverBase.MarkovPIType(this);
/*   39 */   private ORSolverBase.MarkovPIType CurrentMarkovS = new ORSolverBase.MarkovPIType(this);
/*      */   
/*      */   public ORMDSolver()
/*      */   {
/*   43 */     my_Initialize();
/*      */   }
/*      */   
/*      */   private void my_Initialize() {
/*   47 */     this.MarkovNumStates = 4;
/*   48 */     this.MarkovNumDecisions = 3;
/*   49 */     this.MarkovC[1][1] = 0.0D;
/*   50 */     this.MarkovC[1][2] = 999.0D;
/*   51 */     this.MarkovC[1][3] = 999.0D;
/*   52 */     this.MarkovC[2][1] = 1.0D;
/*   53 */     this.MarkovC[2][2] = 999.0D;
/*   54 */     this.MarkovC[2][3] = 6.0D;
/*   55 */     this.MarkovC[3][1] = 3.0D;
/*   56 */     this.MarkovC[3][2] = 4.0D;
/*   57 */     this.MarkovC[3][3] = 6.0D;
/*   58 */     this.MarkovC[4][1] = 999.0D;
/*   59 */     this.MarkovC[4][2] = 999.0D;
/*   60 */     this.MarkovC[4][3] = 6.0D;
/*      */     
/*   62 */     this.MarkovP[1][1][1] = 0.0D;
/*   63 */     this.MarkovP[1][2][1] = 0.875D;
/*   64 */     this.MarkovP[1][3][1] = 0.0625D;
/*   65 */     this.MarkovP[1][4][1] = 0.0625D;
/*   66 */     this.MarkovP[2][1][1] = 0.0D;
/*   67 */     this.MarkovP[2][2][1] = 0.75D;
/*   68 */     this.MarkovP[2][3][1] = 0.125D;
/*   69 */     this.MarkovP[2][4][1] = 0.125D;
/*   70 */     this.MarkovP[3][1][1] = 0.0D;
/*   71 */     this.MarkovP[3][2][1] = 0.0D;
/*   72 */     this.MarkovP[3][3][1] = 0.5D;
/*   73 */     this.MarkovP[3][4][1] = 0.5D;
/*   74 */     this.MarkovP[4][1][1] = 0.0D;
/*   75 */     this.MarkovP[4][2][1] = 0.0D;
/*   76 */     this.MarkovP[4][3][1] = 0.0D;
/*   77 */     this.MarkovP[4][4][1] = 1.0D;
/*      */     
/*   79 */     this.MarkovP[1][1][2] = 1.0D;
/*   80 */     this.MarkovP[1][2][2] = 0.0D;
/*   81 */     this.MarkovP[1][3][2] = 0.0D;
/*   82 */     this.MarkovP[1][4][2] = 0.0D;
/*   83 */     this.MarkovP[2][1][2] = 0.0D;
/*   84 */     this.MarkovP[2][2][2] = 1.0D;
/*   85 */     this.MarkovP[2][3][2] = 0.0D;
/*   86 */     this.MarkovP[2][4][2] = 0.0D;
/*   87 */     this.MarkovP[3][1][2] = 0.0D;
/*   88 */     this.MarkovP[3][2][2] = 1.0D;
/*   89 */     this.MarkovP[3][3][2] = 0.0D;
/*   90 */     this.MarkovP[3][4][2] = 0.0D;
/*   91 */     this.MarkovP[4][1][2] = 0.0D;
/*   92 */     this.MarkovP[4][2][2] = 0.0D;
/*   93 */     this.MarkovP[4][3][2] = 0.0D;
/*   94 */     this.MarkovP[4][4][2] = 1.0D;
/*      */     
/*   96 */     this.MarkovP[1][1][3] = 1.0D;
/*   97 */     this.MarkovP[1][2][3] = 0.0D;
/*   98 */     this.MarkovP[1][3][3] = 0.0D;
/*   99 */     this.MarkovP[1][4][3] = 0.0D;
/*  100 */     this.MarkovP[2][1][3] = 1.0D;
/*  101 */     this.MarkovP[2][2][3] = 0.0D;
/*  102 */     this.MarkovP[2][3][3] = 0.0D;
/*  103 */     this.MarkovP[2][4][3] = 0.0D;
/*  104 */     this.MarkovP[3][1][3] = 1.0D;
/*  105 */     this.MarkovP[3][2][3] = 0.0D;
/*  106 */     this.MarkovP[3][3][3] = 0.0D;
/*  107 */     this.MarkovP[3][4][3] = 0.0D;
/*  108 */     this.MarkovP[4][1][3] = 1.0D;
/*  109 */     this.MarkovP[4][2][3] = 0.0D;
/*  110 */     this.MarkovP[4][3][3] = 0.0D;
/*  111 */     this.MarkovP[4][4][3] = 0.0D;
/*      */     
/*  113 */     this.Markovd[1] = 3;
/*  114 */     this.Markovd[2] = 1;
/*  115 */     this.Markovd[3] = 1;
/*  116 */     this.Markovd[4] = 2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  121 */     this.MarkovDiscountFactor = 0.8D;
/*      */   }
/*      */   
/*      */   public boolean doWork(IOROperation op, IORMDProcessController.MKData data)
/*      */   {
/*  126 */     boolean success = doWork(op);
/*      */     
/*  128 */     if (success) {
/*  129 */       getData(data);
/*      */     }
/*  131 */     return success;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void reset()
/*      */   {
/*  138 */     this.MarkovCurrentIteration = 1;
/*  139 */     this.MarkovDCurrentIteration = 1;
/*  140 */     this.MarkovSCurrentIteration = 1;
/*  141 */     this.CurrentMarkov = new ORSolverBase.MarkovPIType(this);
/*  142 */     this.CurrentMarkovD = new ORSolverBase.MarkovPIType(this);
/*  143 */     this.CurrentMarkovS = new ORSolverBase.MarkovPIType(this);
/*  144 */     arraycopy1(this.Markovd, this.CurrentMarkov.OldPolicy);
/*  145 */     arraycopy1(this.Markovd, this.CurrentMarkov.NewPolicy);
/*  146 */     arraycopy1(this.Markovd, this.CurrentMarkovD.OldPolicy);
/*  147 */     arraycopy1(this.Markovd, this.CurrentMarkovD.NewPolicy);
/*  148 */     arraycopy1(this.Markovd, this.CurrentMarkovS.OldPolicy);
/*  149 */     arraycopy1(this.Markovd, this.CurrentMarkovS.NewPolicy);
/*      */   }
/*      */   
/*      */   public void getData(IORMDProcessController.MKData data)
/*      */   {
/*  154 */     if (data.cost == null) data.cost = new double[5][6];
/*  155 */     if (data.p == null) data.p = new double[6][5][5];
/*  156 */     if (data.old_decision == null) data.old_decision = new int[5];
/*  157 */     if (data.new_decision == null) data.new_decision = new int[5];
/*  158 */     if (data.vr == null) data.vr = new double[11];
/*  159 */     if (data.new_vr == null) data.new_vr = new double[11];
/*  160 */     if (data.expr == null) data.expr = new double[11][11];
/*  161 */     if (data.grcoef == null) data.grcoef = new double[11][11];
/*  162 */     if (data.exprcoef == null) { data.exprcoef = new double[11][11];
/*      */     }
/*  164 */     data.num_states = this.MarkovNumStates;
/*  165 */     data.num_decisions = this.MarkovNumDecisions;
/*  166 */     data.discount = this.MarkovDiscountFactor;
/*      */     
/*      */ 
/*  169 */     for (int i = 0; i < 4; i++) {
/*  170 */       for (int j = 0; j < 5; j++)
/*  171 */         data.cost[i][j] = this.MarkovC[(i + 1)][(j + 1)];
/*      */     }
/*  173 */     for (i = 0; i < 5; i++) {
/*  174 */       for (int j = 0; j < 4; j++) {
/*  175 */         for (int k = 0; k < 4; k++)
/*  176 */           data.p[i][j][k] = this.MarkovP[(j + 1)][(k + 1)][(i + 1)];
/*      */       }
/*      */     }
/*  179 */     if (this.CommandNumber == 1) {
/*  180 */       for (i = 0; i < 4; i++) {
/*  181 */         data.old_decision[i] = this.CurrentMarkov.OldPolicy[(i + 1)];
/*  182 */         data.new_decision[i] = this.CurrentMarkov.NewPolicy[(i + 1)];
/*      */       }
/*      */       
/*  185 */       data.gr = this.CurrentMarkov.cCompute[1];
/*  186 */       for (i = 0; i < 4; i++) {
/*  187 */         data.vr[i] = this.CurrentMarkov.cCompute[(i + 2)];
/*      */       }
/*  189 */       for (i = 0; i < 4; i++) {
/*  190 */         for (int j = 0; j < 5; j++)
/*  191 */           data.expr[i][j] = this.CurrentMarkov.Value[(i + 1)][(j + 1)];
/*      */       }
/*  193 */       for (i = 0; i < 4; i++) {
/*  194 */         for (int j = 0; j < 5; j++)
/*  195 */           data.grcoef[i][j] = this.CurrentMarkov.A[(i + 1)][(j + 1)];
/*      */       }
/*  197 */       for (i = 0; i < 5; i++) {
/*  198 */         for (int j = 0; j < 5; j++)
/*  199 */           data.exprcoef[i][j] = this.CurrentMarkov.A2[(i + 1)][(j + 1)];
/*      */       }
/*      */     }
/*  202 */     if (this.CommandNumber == 2) {
/*  203 */       for (i = 0; i < 4; i++) {
/*  204 */         data.old_decision[i] = this.CurrentMarkovD.OldPolicy[(i + 1)];
/*  205 */         data.new_decision[i] = this.CurrentMarkovD.NewPolicy[(i + 1)];
/*      */       }
/*      */       
/*  208 */       for (i = 0; i < 4; i++) {
/*  209 */         data.vr[i] = this.CurrentMarkovD.cCompute[(i + 1)];
/*      */       }
/*  211 */       for (i = 0; i < 4; i++) {
/*  212 */         for (int j = 0; j < 5; j++)
/*  213 */           data.expr[i][j] = this.CurrentMarkovD.Value[(i + 1)][(j + 1)];
/*      */       }
/*  215 */       for (i = 0; i < 4; i++) {
/*  216 */         for (int j = 0; j < 5; j++)
/*  217 */           data.grcoef[i][j] = this.CurrentMarkovD.A[(i + 1)][(j + 1)];
/*      */       }
/*  219 */       for (i = 0; i < 5; i++) {
/*  220 */         for (int j = 0; j < 5; j++)
/*  221 */           data.exprcoef[i][j] = this.CurrentMarkovD.A2[(i + 1)][(j + 1)];
/*      */       }
/*      */     }
/*  224 */     if (this.CommandNumber == 3) {
/*  225 */       for (i = 0; i < 4; i++) {
/*  226 */         data.old_decision[i] = this.CurrentMarkovS.OldPolicy[(i + 1)];
/*  227 */         data.new_decision[i] = this.CurrentMarkovS.NewPolicy[(i + 1)];
/*      */       }
/*      */       
/*  230 */       for (i = 0; i < 4; i++) {
/*  231 */         data.vr[i] = this.CurrentMarkovS.bCompute[(i + 1)];
/*  232 */         data.new_vr[i] = this.CurrentMarkovS.cCompute[(i + 1)];
/*      */       }
/*      */       
/*  235 */       for (i = 0; i < 4; i++) {
/*  236 */         for (int j = 0; j < 5; j++)
/*  237 */           data.expr[i][j] = this.CurrentMarkovS.Value[(i + 1)][(j + 1)];
/*      */       }
/*  239 */       for (i = 0; i < 5; i++) {
/*  240 */         for (int j = 0; j < 5; j++) {
/*  241 */           data.exprcoef[i][j] = this.CurrentMarkovS.A2[(i + 1)][(j + 1)];
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
/*      */   public boolean doWork(IOROperation op)
/*      */   {
/*  254 */     Vector p = op.parameters;
/*      */     
/*      */     double[][] coefficient;
/*      */     int i;
/*  258 */     switch (op.operation_code)
/*      */     {
/*      */     case 40101: 
/*  261 */       this.MarkovNumStates = ((Integer)p.elementAt(0)).intValue();
/*  262 */       this.MarkovNumDecisions = ((Integer)p.elementAt(1)).intValue();
/*  263 */       break;
/*      */     case 40102: 
/*  265 */       coefficient = (double[][])p.elementAt(0);
/*  266 */       for (i = 0; i < this.MarkovNumStates;) {
/*  267 */         for (int j = 0; j < this.MarkovNumDecisions; j++) {
/*  268 */           this.MarkovC[(i + 1)][(j + 1)] = coefficient[i][j];
/*      */         }
/*  266 */         i++; continue;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  272 */         k = ((Integer)p.elementAt(0)).intValue();
/*  273 */         coefficient = (double[][])p.elementAt(1);
/*  274 */         for (i = 0; i < this.MarkovNumStates;) {
/*  275 */           for (int j = 0; j < this.MarkovNumStates; j++) {
/*  276 */             this.MarkovP[(i + 1)][(j + 1)][k] = coefficient[i][j];
/*      */           }
/*  274 */           i++; continue;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  281 */           int[] ia = (int[])p.elementAt(0);
/*  282 */           for (int i = 0; i < this.MarkovNumStates; i++) {
/*  283 */             this.Markovd[(i + 1)] = ia[i];
/*  284 */             this.CurrentMarkov.OldPolicy[(i + 1)] = ia[i];
/*  285 */             this.CurrentMarkov.NewPolicy[(i + 1)] = ia[i];
/*  286 */             this.CurrentMarkovD.OldPolicy[(i + 1)] = ia[i];
/*  287 */             this.CurrentMarkovD.NewPolicy[(i + 1)] = ia[i];
/*  288 */             this.CurrentMarkovS.OldPolicy[(i + 1)] = ia[i];
/*  289 */             this.CurrentMarkovS.NewPolicy[(i + 1)] = ia[i];
/*      */           }
/*  291 */           this.MarkovDiscountFactor = ((Double)p.elementAt(1)).doubleValue();
/*  292 */           break;
/*      */           
/*  294 */           this.CommandNumber = ((Integer)p.elementAt(0)).intValue();
/*  295 */           if (this.CommandNumber == 1) {
/*  296 */             this.MarkovCurrentIteration = 1;
/*  297 */             this.CurrentMarkov = new ORSolverBase.MarkovPIType(this);
/*  298 */             arraycopy1(this.Markovd, this.CurrentMarkov.OldPolicy);
/*  299 */             arraycopy1(this.Markovd, this.CurrentMarkov.NewPolicy);
/*      */           }
/*  301 */           else if (this.CommandNumber == 2) {
/*  302 */             this.MarkovDCurrentIteration = 1;
/*  303 */             this.CurrentMarkovD = new ORSolverBase.MarkovPIType(this);
/*  304 */             arraycopy1(this.Markovd, this.CurrentMarkovD.OldPolicy);
/*  305 */             arraycopy1(this.Markovd, this.CurrentMarkovD.NewPolicy);
/*      */           }
/*      */           else {
/*  308 */             this.MarkovSCurrentIteration = 1;
/*  309 */             this.CurrentMarkovS = new ORSolverBase.MarkovPIType(this);
/*  310 */             arraycopy1(this.Markovd, this.CurrentMarkovS.OldPolicy);
/*  311 */             arraycopy1(this.Markovd, this.CurrentMarkovS.NewPolicy);
/*      */             
/*  313 */             break;
/*      */             
/*  315 */             int[] ia = (int[])p.elementAt(0);
/*  316 */             boolean Correct = true;
/*  317 */             if (this.MarkovCurrentIteration == 1) {
/*  318 */               if (ia[1] == 0) {
/*  319 */                 if (ia[2] != 0)
/*  320 */                   Correct = false;
/*  321 */                 if (ia[0] != ia[3])
/*  322 */                   Correct = false;
/*  323 */                 if (ia[4] + 1 != this.CurrentMarkov.OldPolicy[(ia[0] + 1)]) {
/*  324 */                   Correct = false;
/*      */                 }
/*      */               } else {
/*  327 */                 if (ia[2] != this.CurrentMarkov.OldPolicy[(ia[0] + 1)])
/*  328 */                   Correct = false;
/*  329 */                 if (ia[0] != ia[3])
/*  330 */                   Correct = false;
/*  331 */                 if (ia[4] + 1 != ia[1])
/*  332 */                   Correct = false;
/*      */               }
/*      */             }
/*  335 */             if (!Correct) {
/*  336 */               this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  337 */               return false;
/*      */             }
/*      */             
/*  340 */             if (ia[2] > 0) {
/*  341 */               this.CurrentMarkov.A[(ia[0] + 1)][(ia[1] + 1)] = this.MarkovP[(ia[3] + 1)][(ia[4] + 1)][ia[2]];
/*      */             } else {
/*  343 */               this.CurrentMarkov.A[(ia[0] + 1)][(ia[1] + 1)] = this.MarkovC[(ia[3] + 1)][(ia[4] + 1)];
/*      */               
/*  345 */               break;
/*      */               
/*  347 */               for (int i = 1; i <= this.MarkovNumStates; i++) {
/*  348 */                 for (int j = 1; j <= this.MarkovNumStates; j++) {
/*  349 */                   if (j == 1) {
/*  350 */                     this.CurrentMarkov.ACompute[i][j] = -1.0D;
/*      */                   } else {
/*  352 */                     this.CurrentMarkov.ACompute[i][j] = this.CurrentMarkov.A[i][j];
/*  353 */                     if (i == j - 1)
/*  354 */                       this.CurrentMarkov.ACompute[i][j] -= 1;
/*      */                   }
/*      */                 }
/*      */               }
/*  358 */               for (i = 1; i <= this.MarkovNumStates; i++) {
/*  359 */                 this.CurrentMarkov.bCompute[i] = (-this.CurrentMarkov.A[i][1]);
/*      */               }
/*  361 */               boolean Singular = SolveSystem(this.CurrentMarkov.ACompute, this.CurrentMarkov.bCompute, this.MarkovNumStates, this.CurrentMarkov.cCompute);
/*  362 */               break;
/*      */               
/*  364 */               this.CurrentMarkov.A2 = new double[11][11];
/*  365 */               for (i = 1; i <= this.MarkovNumDecisions;) {
/*  366 */                 this.CurrentMarkov.Value[1][i] = 0.0D;i++; continue;
/*      */                 
/*      */ 
/*  369 */                 int[] ia = (int[])p.elementAt(0);
/*  370 */                 boolean Correct = true;
/*  371 */                 if (ia[1] == 0) {
/*  372 */                   if (ia[2] != 0)
/*  373 */                     Correct = false;
/*  374 */                   if (ia[3] != 0)
/*  375 */                     Correct = false;
/*  376 */                   if (ia[4] + 1 != ia[0]) {
/*  377 */                     Correct = false;
/*      */                   }
/*      */                 } else {
/*  380 */                   if (ia[2] != ia[0])
/*  381 */                     Correct = false;
/*  382 */                   if (ia[3] != 0)
/*  383 */                     Correct = false;
/*  384 */                   if (ia[4] + 1 != ia[1])
/*  385 */                     Correct = false;
/*      */                 }
/*  387 */                 if (!Correct) {
/*  388 */                   this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  389 */                   return false;
/*      */                 }
/*      */                 
/*  392 */                 if (ia[2] > 0) {
/*  393 */                   this.CurrentMarkov.A2[ia[0]][(ia[1] + 1)] = this.MarkovP[(ia[3] + 1)][(ia[4] + 1)][ia[2]];
/*      */                 } else
/*  395 */                   this.CurrentMarkov.A2[ia[0]][1] = this.MarkovC[(ia[3] + 1)][(ia[4] + 1)];
/*  396 */                 double Temp = this.CurrentMarkov.A2[ia[0]][1];
/*  397 */                 for (int i = 1; i <= this.MarkovNumStates - 1; i++)
/*  398 */                   Temp += this.CurrentMarkov.cCompute[(i + 1)] * this.CurrentMarkov.A2[ia[0]][(i + 1)];
/*  399 */                 this.CurrentMarkov.CurrentState = 1;
/*  400 */                 if (this.CurrentMarkov.CurrentState < this.MarkovNumStates) {
/*  401 */                   Temp -= this.CurrentMarkov.cCompute[(this.CurrentMarkov.CurrentState + 1)];
/*      */                 }
/*  403 */                 if (this.CurrentMarkov.A2[ia[0]][1] == 'ϧ') {
/*  404 */                   this.CurrentMarkov.Value[1][ia[0]] = 999.0D;
/*      */                 } else {
/*  406 */                   this.CurrentMarkov.Value[1][ia[0]] = Temp;
/*      */                   
/*  408 */                   break;
/*      */                   
/*  410 */                   this.CurrentMarkov.CurrentState = (((Integer)p.elementAt(0)).intValue() + 1);
/*  411 */                   for (i = 1; i <= this.MarkovNumDecisions; i++) {
/*  412 */                     this.CurrentMarkov.A2[i][1] = this.MarkovC[this.CurrentMarkov.CurrentState][i];
/*  413 */                     for (int j = 2; j <= this.MarkovNumStates + 1; j++)
/*  414 */                       this.CurrentMarkov.A2[i][j] = this.MarkovP[this.CurrentMarkov.CurrentState][(j - 1)][i];
/*      */                   }
/*  416 */                   for (i = 1; i <= this.MarkovNumDecisions;) {
/*  417 */                     double Temp = this.CurrentMarkov.A2[i][1];
/*  418 */                     for (int j = 1; j <= this.MarkovNumStates - 1; j++)
/*  419 */                       Temp += this.CurrentMarkov.cCompute[(j + 1)] * this.CurrentMarkov.A2[i][(j + 1)];
/*  420 */                     if (this.CurrentMarkov.CurrentState < this.MarkovNumStates)
/*  421 */                       Temp -= this.CurrentMarkov.cCompute[(this.CurrentMarkov.CurrentState + 1)];
/*  422 */                     if (this.CurrentMarkov.A2[i][1] == 'ϧ') {
/*  423 */                       this.CurrentMarkov.Value[this.CurrentMarkov.CurrentState][i] = 999.0D;
/*      */                     } else {
/*  425 */                       this.CurrentMarkov.Value[this.CurrentMarkov.CurrentState][i] = Temp;
/*      */                     }
/*  416 */                     i++; continue;
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  429 */                     this.CurrentMarkov.CurrentState = (((Integer)p.elementAt(0)).intValue() + 1);
/*  430 */                     int newDicision = ((Integer)p.elementAt(1)).intValue();
/*  431 */                     boolean Correct = true;
/*  432 */                     if (this.MarkovCurrentIteration == 1) {
/*  433 */                       double MinVal = this.CurrentMarkov.Value[this.CurrentMarkov.CurrentState][1];
/*  434 */                       if (MinVal == 'ϧ')
/*  435 */                         MinVal = 9.0E12D;
/*  436 */                       int Which = 1;
/*  437 */                       for (int i = 2; i <= this.MarkovNumDecisions; i++) {
/*  438 */                         if ((this.CurrentMarkov.Value[this.CurrentMarkov.CurrentState][i] < MinVal) && (this.CurrentMarkov.Value[this.CurrentMarkov.CurrentState][i] != 'ϧ')) {
/*  439 */                           Which = i;
/*  440 */                           MinVal = this.CurrentMarkov.Value[this.CurrentMarkov.CurrentState][i];
/*      */                         }
/*      */                       }
/*  443 */                       if (newDicision != Which)
/*  444 */                         Correct = false;
/*      */                     }
/*  446 */                     if (!Correct)
/*      */                     {
/*  448 */                       this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  449 */                       return false;
/*      */                     }
/*      */                     
/*  452 */                     this.CurrentMarkov.NewPolicy[this.CurrentMarkov.CurrentState] = newDicision;
/*      */                     
/*      */ 
/*  455 */                     break;
/*      */                     
/*  457 */                     this.MarkovCurrentIteration += 1;
/*  458 */                     this.CurrentMarkov.A = new double[11][11];
/*  459 */                     arraycopy1(this.CurrentMarkov.NewPolicy, this.CurrentMarkov.OldPolicy);
/*  460 */                     break;
/*      */                     
/*      */ 
/*  463 */                     int[] ia = (int[])p.elementAt(0);
/*  464 */                     boolean Correct = true;
/*  465 */                     if (this.MarkovDCurrentIteration == 1) {
/*  466 */                       if (ia[1] == 0) {
/*  467 */                         if (ia[2] != 0)
/*  468 */                           Correct = false;
/*  469 */                         if (ia[0] != ia[3])
/*  470 */                           Correct = false;
/*  471 */                         if (ia[4] + 1 != this.CurrentMarkovD.OldPolicy[(ia[0] + 1)]) {
/*  472 */                           Correct = false;
/*      */                         }
/*      */                       } else {
/*  475 */                         if (ia[2] != this.CurrentMarkovD.OldPolicy[(ia[0] + 1)])
/*  476 */                           Correct = false;
/*  477 */                         if (ia[0] != ia[3])
/*  478 */                           Correct = false;
/*  479 */                         if (ia[4] + 1 != ia[1])
/*  480 */                           Correct = false;
/*      */                       }
/*      */                     }
/*  483 */                     if (!Correct) {
/*  484 */                       this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  485 */                       return false;
/*      */                     }
/*      */                     
/*  488 */                     if (ia[2] > 0) {
/*  489 */                       this.CurrentMarkovD.A[(ia[0] + 1)][(ia[1] + 1)] = this.MarkovP[(ia[3] + 1)][(ia[4] + 1)][ia[2]];
/*      */                     } else {
/*  491 */                       this.CurrentMarkovD.A[(ia[0] + 1)][(ia[1] + 1)] = this.MarkovC[(ia[3] + 1)][(ia[4] + 1)];
/*      */                       
/*  493 */                       break;
/*      */                       
/*  495 */                       for (int i = 1; i <= this.MarkovNumStates; i++) {
/*  496 */                         for (int j = 1; j <= this.MarkovNumStates; j++) {
/*  497 */                           this.CurrentMarkovD.ACompute[i][j] = (this.MarkovDiscountFactor * this.CurrentMarkovD.A[i][(j + 1)]);
/*  498 */                           if (i == j)
/*  499 */                             this.CurrentMarkovD.ACompute[i][j] -= 1;
/*      */                         }
/*      */                       }
/*  502 */                       for (i = 1; i <= this.MarkovNumStates; i++) {
/*  503 */                         this.CurrentMarkovD.bCompute[i] = (-this.CurrentMarkovD.A[i][1]);
/*      */                       }
/*  505 */                       boolean Singular = SolveSystem(this.CurrentMarkovD.ACompute, this.CurrentMarkovD.bCompute, this.MarkovNumStates, this.CurrentMarkovD.cCompute);
/*  506 */                       break;
/*      */                       
/*  508 */                       this.CurrentMarkovD.A2 = new double[11][11];
/*  509 */                       for (i = 1; i <= this.MarkovNumDecisions;) {
/*  510 */                         this.CurrentMarkovD.Value[1][i] = 0.0D;i++; continue;
/*      */                         
/*      */ 
/*  513 */                         int[] ia = (int[])p.elementAt(0);
/*  514 */                         boolean Correct = true;
/*  515 */                         if (ia[1] == 0) {
/*  516 */                           if (ia[2] != 0)
/*  517 */                             Correct = false;
/*  518 */                           if (ia[3] != 0)
/*  519 */                             Correct = false;
/*  520 */                           if (ia[4] + 1 != ia[0]) {
/*  521 */                             Correct = false;
/*      */                           }
/*      */                         } else {
/*  524 */                           if (ia[2] != ia[0])
/*  525 */                             Correct = false;
/*  526 */                           if (ia[3] != 0)
/*  527 */                             Correct = false;
/*  528 */                           if (ia[4] + 1 != ia[1])
/*  529 */                             Correct = false;
/*      */                         }
/*  531 */                         if (!Correct) {
/*  532 */                           this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  533 */                           return false;
/*      */                         }
/*      */                         
/*  536 */                         if (ia[2] > 0) {
/*  537 */                           this.CurrentMarkovD.A2[ia[0]][(ia[1] + 1)] = this.MarkovP[(ia[3] + 1)][(ia[4] + 1)][ia[2]];
/*      */                         } else
/*  539 */                           this.CurrentMarkovD.A2[ia[0]][1] = this.MarkovC[(ia[3] + 1)][(ia[4] + 1)];
/*  540 */                         double Temp = this.CurrentMarkovD.A2[ia[0]][1];
/*  541 */                         for (int i = 1; i <= this.MarkovNumStates; i++)
/*  542 */                           Temp += this.MarkovDiscountFactor * this.CurrentMarkovD.cCompute[i] * this.CurrentMarkovD.A2[ia[0]][(i + 1)];
/*  543 */                         this.CurrentMarkovD.CurrentState = 1;
/*  544 */                         if (this.CurrentMarkovD.A2[ia[0]][1] == 'ϧ') {
/*  545 */                           this.CurrentMarkovD.Value[this.CurrentMarkovD.CurrentState][ia[0]] = 999.0D;
/*      */                         } else {
/*  547 */                           this.CurrentMarkovD.Value[this.CurrentMarkovD.CurrentState][ia[0]] = Temp;
/*      */                           
/*  549 */                           break;
/*      */                           
/*  551 */                           this.CurrentMarkovD.CurrentState = (((Integer)p.elementAt(0)).intValue() + 1);
/*  552 */                           for (i = 1; i <= this.MarkovNumDecisions; i++) {
/*  553 */                             this.CurrentMarkovD.A2[i][1] = this.MarkovC[this.CurrentMarkovD.CurrentState][i];
/*  554 */                             for (int j = 2; j <= this.MarkovNumStates + 1; j++)
/*  555 */                               this.CurrentMarkovD.A2[i][j] = this.MarkovP[this.CurrentMarkovD.CurrentState][(j - 1)][i];
/*      */                           }
/*  557 */                           for (i = 1; i <= this.MarkovNumDecisions;) {
/*  558 */                             double Temp = this.CurrentMarkovD.A2[i][1];
/*  559 */                             for (int j = 1; j <= this.MarkovNumStates; j++)
/*  560 */                               Temp += this.MarkovDiscountFactor * this.CurrentMarkovD.cCompute[j] * this.CurrentMarkovD.A2[i][(j + 1)];
/*  561 */                             if (this.CurrentMarkovD.A2[i][1] == 'ϧ') {
/*  562 */                               this.CurrentMarkovD.Value[this.CurrentMarkovD.CurrentState][i] = 999.0D;
/*      */                             } else {
/*  564 */                               this.CurrentMarkovD.Value[this.CurrentMarkovD.CurrentState][i] = Temp;
/*      */                             }
/*  557 */                             i++; continue;
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  568 */                             this.CurrentMarkovD.CurrentState = (((Integer)p.elementAt(0)).intValue() + 1);
/*  569 */                             int newDicision = ((Integer)p.elementAt(1)).intValue();
/*  570 */                             boolean Correct = true;
/*  571 */                             if (this.MarkovDCurrentIteration == 1) {
/*  572 */                               double MinVal = this.CurrentMarkovD.Value[this.CurrentMarkovD.CurrentState][1];
/*  573 */                               if (MinVal == 'ϧ')
/*  574 */                                 MinVal = 9.0E12D;
/*  575 */                               int Which = 1;
/*  576 */                               for (int i = 2; i <= this.MarkovNumDecisions; i++) {
/*  577 */                                 if ((this.CurrentMarkovD.Value[this.CurrentMarkovD.CurrentState][i] < MinVal) && (this.CurrentMarkovD.Value[this.CurrentMarkovD.CurrentState][i] != 'ϧ')) {
/*  578 */                                   Which = i;
/*  579 */                                   MinVal = this.CurrentMarkovD.Value[this.CurrentMarkovD.CurrentState][i];
/*      */                                 }
/*      */                               }
/*  582 */                               if (newDicision != Which)
/*  583 */                                 Correct = false;
/*      */                             }
/*  585 */                             if (!Correct)
/*      */                             {
/*  587 */                               this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  588 */                               return false;
/*      */                             }
/*      */                             
/*  591 */                             this.CurrentMarkovD.NewPolicy[this.CurrentMarkovD.CurrentState] = newDicision;
/*      */                             
/*      */ 
/*  594 */                             break;
/*      */                             
/*  596 */                             this.MarkovDCurrentIteration += 1;
/*  597 */                             this.CurrentMarkovD.A = new double[11][11];
/*  598 */                             arraycopy1(this.CurrentMarkovD.NewPolicy, this.CurrentMarkovD.OldPolicy);
/*  599 */                             break;
/*      */                             
/*  601 */                             double[] da = (double[])p.elementAt(0);
/*  602 */                             int[] ia = (int[])p.elementAt(1);
/*  603 */                             for (int i = 0; i < this.MarkovNumStates; i++) {
/*  604 */                               this.CurrentMarkovS.bCompute[(i + 1)] = da[i];
/*  605 */                               this.CurrentMarkovS.NewPolicy[(i + 1)] = ia[i];
/*  606 */                               this.CurrentMarkovS.OldPolicy[(i + 1)] = this.CurrentMarkovS.NewPolicy[(i + 1)];
/*      */                             }
/*  608 */                             boolean Correct = true;
/*  609 */                             for (int k = 1; k <= this.MarkovNumStates; k++) { double Temp;
/*  610 */                               double Temp; if (Math.abs(this.MarkovC[k][1] - 'ϧ') < 0.1D) {
/*  611 */                                 Temp = 1.0E12D;
/*      */                               } else
/*  613 */                                 Temp = this.MarkovC[k][1];
/*  614 */                               for (i = 2; i <= this.MarkovNumDecisions; i++) {
/*  615 */                                 if ((Math.abs(this.MarkovC[k][i] - 'ϧ') > 0.1D) && 
/*  616 */                                   (this.MarkovC[k][i] < Temp)) {
/*  617 */                                   Temp = this.MarkovC[k][i];
/*      */                                 }
/*      */                               }
/*  620 */                               if ((Temp < 1.0E11D) && (Math.abs(this.CurrentMarkovS.bCompute[k] - Temp) > Math.min(0.1D, 0.01D * Temp))) {
/*  621 */                                 Correct = false;
/*      */                               }
/*      */                               
/*      */ 
/*  625 */                               if (Math.abs(this.MarkovC[k][1] - 'ϧ') < 0.1D) {
/*  626 */                                 Temp = 1.0E12D;
/*      */                               } else
/*  628 */                                 Temp = this.MarkovC[k][1];
/*  629 */                               for (i = 2; i <= this.MarkovNumDecisions; i++) {
/*  630 */                                 if ((Math.abs(this.MarkovC[k][i] - 'ϧ') > 0.1D) && 
/*  631 */                                   (this.MarkovC[k][i] < Temp))
/*  632 */                                   Temp = this.MarkovC[k][i];
/*      */                               }
/*  634 */                               if ((Temp < 1.0E11D) && (Math.abs(this.MarkovC[k][this.CurrentMarkovS.NewPolicy[k]] - Temp) > Math.min(0.1D, 0.01D * Temp)))
/*  635 */                                 Correct = false;
/*      */                             }
/*  637 */                             if (!Correct) {
/*  638 */                               this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  639 */                               return false;
/*      */                             }
/*      */                             
/*  642 */                             this.MarkovSCurrentIteration = 2;
/*      */                             
/*  644 */                             break;
/*      */                             
/*  646 */                             int[] ia = (int[])p.elementAt(0);
/*  647 */                             boolean Correct = true;
/*  648 */                             if (this.MarkovSCurrentIteration == 2) {
/*  649 */                               if (ia[1] == 0) {
/*  650 */                                 if (ia[2] != 0)
/*  651 */                                   Correct = false;
/*  652 */                                 if (ia[3] != 0)
/*  653 */                                   Correct = false;
/*  654 */                                 if (ia[4] + 1 != ia[0]) {
/*  655 */                                   Correct = false;
/*      */                                 }
/*      */                               } else {
/*  658 */                                 if (ia[2] != ia[0])
/*  659 */                                   Correct = false;
/*  660 */                                 if (ia[3] != 0)
/*  661 */                                   Correct = false;
/*  662 */                                 if (ia[4] + 1 != ia[1])
/*  663 */                                   Correct = false;
/*      */                               }
/*      */                             }
/*  666 */                             if (!Correct) {
/*  667 */                               this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  668 */                               return false;
/*      */                             }
/*      */                             
/*  671 */                             if (ia[2] > 0) {
/*  672 */                               this.CurrentMarkovS.A2[ia[0]][(ia[1] + 1)] = this.MarkovP[(ia[3] + 1)][(ia[4] + 1)][ia[2]];
/*      */                             } else {
/*  674 */                               this.CurrentMarkovS.A2[ia[0]][1] = this.MarkovC[(ia[3] + 1)][(ia[4] + 1)];
/*      */                               
/*      */ 
/*  677 */                               break;
/*      */                               
/*  679 */                               this.CurrentMarkovS.CurrentState = (((Integer)p.elementAt(0)).intValue() + 1);
/*  680 */                               if ((this.MarkovSCurrentIteration != 2) || (this.CurrentMarkovS.CurrentState != 1))
/*      */                               {
/*      */ 
/*  683 */                                 for (int i = 1; i <= this.MarkovNumDecisions; i++) {
/*  684 */                                   this.CurrentMarkovS.A2[i][1] = this.MarkovC[this.CurrentMarkovS.CurrentState][i];
/*  685 */                                   for (int j = 1; j <= this.MarkovNumStates; j++)
/*  686 */                                     this.CurrentMarkovS.A2[i][(j + 1)] = this.MarkovP[this.CurrentMarkovS.CurrentState][j][i];
/*      */                                 }
/*      */                               }
/*  689 */                               for (i = 1; i <= this.MarkovNumDecisions;) {
/*  690 */                                 double Temp = this.CurrentMarkovS.A2[i][1];
/*  691 */                                 for (int j = 1; j <= this.MarkovNumStates; j++)
/*  692 */                                   Temp += this.MarkovDiscountFactor * this.CurrentMarkovS.A2[i][(j + 1)] * this.CurrentMarkovS.bCompute[j];
/*  693 */                                 if (this.CurrentMarkovS.A2[i][1] == 'ϧ') {
/*  694 */                                   this.CurrentMarkovS.Value[this.CurrentMarkovS.CurrentState][i] = 999.0D;
/*      */                                 } else {
/*  696 */                                   this.CurrentMarkovS.Value[this.CurrentMarkovS.CurrentState][i] = Temp;
/*      */                                 }
/*  689 */                                 i++; continue;
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  700 */                                 this.CurrentMarkovS.CurrentState = (((Integer)p.elementAt(0)).intValue() + 1);
/*  701 */                                 this.CurrentMarkovS.NewPolicy[this.CurrentMarkovS.CurrentState] = ((Integer)p.elementAt(1)).intValue();
/*  702 */                                 this.CurrentMarkovS.cCompute[this.CurrentMarkovS.CurrentState] = this.CurrentMarkovS.Value[this.CurrentMarkovS.CurrentState][this.CurrentMarkovS.NewPolicy[this.CurrentMarkovS.CurrentState]];
/*  703 */                                 break;
/*      */                                 
/*  705 */                                 this.MarkovSCurrentIteration += 1;
/*  706 */                                 arraycopy1(this.CurrentMarkovS.NewPolicy, this.CurrentMarkovS.OldPolicy);
/*  707 */                                 arraycopy1(this.CurrentMarkovS.cCompute, this.CurrentMarkovS.bCompute); } } } } } } } } } } } } } }
/*      */     int k;
/*      */     double[][] coefficient;
/*  710 */     int i; int i; int i; int i; int i; int i; return true;
/*      */   }
/*      */   
/*      */   protected void ORPrint()
/*      */   {
/*  715 */     if (this.procedure == 1) {
/*  716 */       PrintMarkovModel();
/*  717 */     } else if (this.procedure == 2) {
/*  718 */       PrintMarkovAverageCost();
/*  719 */     } else if (this.procedure == 3) {
/*  720 */       PrintMarkovDiscountedCost();
/*  721 */     } else if (this.procedure == 4) {
/*  722 */       PrintMarkovSuccessiveApproximations();
/*      */     }
/*      */   }
/*      */   
/*      */   private void PrintMarkovModel() {
/*  727 */     double[][] A = new double[11][11];
/*      */     
/*      */ 
/*  730 */     PrintLine("Markov Decision Processes Model:");
/*  731 */     Skip(2);
/*  732 */     PrintLine("Number of states = ".concat(String.valueOf(String.valueOf(FormatInteger(this.MarkovNumStates, 1, 0, 0)))));
/*  733 */     SkipLine();
/*  734 */     PrintLine("Number of decisions = ".concat(String.valueOf(String.valueOf(FormatInteger(this.MarkovNumDecisions, 1, 0, 0)))));
/*  735 */     Skip(2);
/*  736 */     PrintLine("Cost Matrix, C(ik):");
/*  737 */     for (int i = 1; i <= this.MarkovNumStates; i++) {
/*  738 */       for (int j = 1; j <= this.MarkovNumDecisions; j++)
/*  739 */         A[i][j] = this.MarkovC[i][j];
/*      */     }
/*  741 */     PrintMatrix(A, this.MarkovNumStates, this.MarkovNumDecisions, 5);
/*  742 */     Skip(2);
/*  743 */     for (int k = 1; k <= this.MarkovNumDecisions; k++) {
/*  744 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("Transition Matrix, p(ij)[").append(FormatInteger(k, 1, 0, 0)).append("]:"))));
/*  745 */       for (i = 1; i <= this.MarkovNumStates; i++) {
/*  746 */         for (int j = 1; j <= this.MarkovNumStates; j++)
/*  747 */           A[i][j] = this.MarkovP[i][j][k];
/*      */       }
/*  749 */       PrintMatrix(A, this.MarkovNumStates, this.MarkovNumStates, 5);
/*  750 */       Skip(2);
/*      */     }
/*  752 */     PrintLine("Initial Policy:");
/*  753 */     SkipLine();
/*  754 */     for (k = 1; k <= this.MarkovNumStates; k++)
/*  755 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("d").append(FormatInteger(k - 1, 1, 0, 0)).append("(R1) = ").append(FormatInteger(this.Markovd[k], 1, 0, 0)))));
/*  756 */     Skip(2);
/*  757 */     PrintLine("Discount Factor = ".concat(String.valueOf(String.valueOf(FormatReal(this.MarkovDiscountFactor, 5, 0, 0)))));
/*      */   }
/*      */   
/*      */ 
/*      */   private void PrintMarkovAverageCost()
/*      */   {
/*  763 */     PrintMarkovModel();
/*  764 */     PrintLine("Average Cost Policy Improvement Algorithm:");
/*  765 */     Skip(2);
/*  766 */     int Iteration = 1;
/*      */     
/*  768 */     reset();
/*  769 */     IOROperation op = null;
/*  770 */     int iStep = 0;
/*  771 */     int n = this.steps.size();
/*      */     
/*  773 */     while (iStep < n) {
/*  774 */       op = (IOROperation)this.steps.elementAt(iStep);
/*  775 */       iStep++;
/*  776 */       if ((op.operation_code == 40207) || (iStep == n)) {
/*  777 */         if (op.operation_code != 40207)
/*  778 */           doWork(op);
/*  779 */         PrintLines(4);
/*  780 */         PrintLine("ITERATION # ".concat(String.valueOf(String.valueOf(FormatInteger(Iteration, 2, 0, 0)))));
/*  781 */         SkipLine();
/*  782 */         PrintLine("Value Determination:");
/*  783 */         SkipLine();
/*  784 */         PrintLines(this.MarkovNumStates);
/*  785 */         for (int i = 1; i <= this.MarkovNumStates; i++) {
/*  786 */           Print(String.valueOf(String.valueOf(new StringBuffer("g(R").append(FormatInteger(Iteration, 1, 0, 0)).append(") = "))));
/*  787 */           if (this.CurrentMarkov.A[i][1] == 'ϧ') {
/*  788 */             Print("---- +");
/*      */           } else
/*  790 */             Print(String.valueOf(String.valueOf(FormatReal(this.CurrentMarkov.A[i][1], 5, 0, 0))).concat("+"));
/*  791 */           for (int j = 1; j <= this.MarkovNumStates; j++) {
/*  792 */             Print(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(FormatReal(this.CurrentMarkov.A[i][(j + 1)], 5, 0, 1)))).append("v").append(FormatInteger(j - 1, 1, 0, 0)).append("(R").append(FormatInteger(Iteration, 1, 0, 0)).append(") "))));
/*  793 */             if (j < this.MarkovNumStates)
/*  794 */               Print("+ ");
/*      */           }
/*  796 */           PrintLine(String.valueOf(String.valueOf(new StringBuffer("- v").append(FormatInteger(i - 1, 1, 0, 0)).append("(R").append(FormatInteger(Iteration, 1, 0, 0)).append(")"))));
/*      */         }
/*  798 */         SkipLine();
/*  799 */         PrintLines(2 + this.MarkovNumStates);
/*  800 */         PrintLine("Solution of Value Determination Equations:");
/*  801 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("g(R").append(FormatInteger(Iteration, 1, 0, 0)).append(")  = ").append(FormatReal(this.CurrentMarkov.cCompute[1], 5, 0, 0)))));
/*  802 */         for (i = 1; i <= this.MarkovNumStates - 1; i++)
/*  803 */           PrintLine(String.valueOf(String.valueOf(new StringBuffer("v").append(FormatInteger(i - 1, 1, 0, 0)).append("(R").append(FormatInteger(Iteration, 1, 0, 0)).append(") = ").append(FormatReal(this.CurrentMarkov.cCompute[(i + 1)], 5, 0, 0)))));
/*  804 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("v").append(FormatInteger(this.MarkovNumStates - 1, 1, 0, 0)).append("(R").append(FormatInteger(Iteration, 1, 0, 0)).append(") = 0"))));
/*  805 */         SkipLine();
/*  806 */         PrintLines(2 + this.MarkovNumStates * (2 + this.MarkovNumDecisions));
/*  807 */         PrintLine("Policy Improvement:");
/*  808 */         SkipLine();
/*  809 */         for (i = 1; i <= this.MarkovNumStates; i++) {
/*  810 */           PrintLine(String.valueOf(String.valueOf(new StringBuffer("State ").append(FormatInteger(i - 1, 1, 0, 0)).append(":"))));
/*  811 */           for (int j = 1; j <= this.MarkovNumDecisions; j++) {
/*  812 */             if (this.MarkovC[i][j] == 'ϧ') {
/*  813 */               Print("---  ");
/*      */             } else
/*  815 */               Print(FormatReal(this.MarkovC[i][j], 5, 0, 0));
/*  816 */             Print("+ ");
/*  817 */             for (int k = 1; k <= this.MarkovNumStates - 1; k++)
/*  818 */               Print(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(FormatReal(this.MarkovP[i][k][j], 5, 0, 0)))).append("(").append(FormatReal(this.CurrentMarkov.cCompute[(k + 1)], 5, 0, 1)).append(") + "))));
/*  819 */             Print(String.valueOf(String.valueOf(FormatReal(this.MarkovP[i][this.MarkovNumStates][j], 5, 0, 0))).concat("(0) - ("));
/*  820 */             if (i < this.MarkovNumStates) {
/*  821 */               Print(String.valueOf(String.valueOf(FormatReal(this.CurrentMarkov.cCompute[(i + 1)], 5, 0, 0))).concat(") = "));
/*      */             } else
/*  823 */               Print("0) = ");
/*  824 */             if (this.CurrentMarkov.Value[i][j] == 'ϧ') {
/*  825 */               PrintLine("---  ");
/*      */             } else
/*  827 */               PrintLine(FormatReal(this.CurrentMarkov.Value[i][j], 5, 0, 0));
/*      */           }
/*  829 */           SkipLine();
/*      */         }
/*  831 */         PrintLines(1 + this.MarkovNumStates);
/*  832 */         if (iStep == n) {
/*  833 */           PrintLine("Optimal Policy:");
/*      */         } else
/*  835 */           PrintLine("New Policy:");
/*  836 */         for (i = 1; i <= this.MarkovNumStates; i++)
/*  837 */           PrintLine(String.valueOf(String.valueOf(new StringBuffer("  d").append(FormatInteger(i - 1, 1, 0, 0)).append("(R").append(FormatInteger(Iteration + 1, 1, 0, 0)).append(") = ").append(FormatInteger(this.CurrentMarkov.NewPolicy[i], 1, 0, 0)))));
/*  838 */         if (iStep == n) {
/*  839 */           Skip(2);
/*  840 */           PrintLines(1 + this.MarkovNumStates);
/*  841 */           Print(String.valueOf(String.valueOf(new StringBuffer("g(R").append(IntegerToString(Iteration + 1, 2)).append(")  = "))));
/*  842 */           PrintLine(FormatReal(this.CurrentMarkov.cCompute[1], 5, 0, 0));
/*  843 */           for (i = 2; i <= this.MarkovNumStates + 1; i++) {
/*  844 */             Print(String.valueOf(String.valueOf(new StringBuffer("v").append(FormatInteger(i - 2, 1, 0, 0)).append("(R").append(IntegerToString(Iteration + 1, 2)).append(") = "))));
/*  845 */             if (i == this.MarkovNumStates + 1) {
/*  846 */               PrintLine("0");
/*      */             } else {
/*  848 */               PrintLine(FormatReal(this.CurrentMarkov.cCompute[i], 5, 0, 0));
/*      */             }
/*      */           }
/*      */         }
/*  852 */         Skip(3);
/*      */         
/*  854 */         Iteration += 1;
/*  855 */         if (op.operation_code == 40207) {
/*  856 */           doWork(op);
/*      */         }
/*      */       } else {
/*  859 */         doWork(op);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void PrintMarkovDiscountedCost()
/*      */   {
/*  867 */     PrintMarkovModel();
/*  868 */     PrintLines(6);
/*  869 */     PrintLine("Discounted Cost Policy Improvement Algorithm:");
/*  870 */     Skip(2);
/*  871 */     int Iteration = 1;
/*      */     
/*  873 */     reset();
/*  874 */     IOROperation op = null;
/*  875 */     int iStep = 0;
/*  876 */     int n = this.steps.size();
/*      */     
/*  878 */     while (iStep < n) {
/*  879 */       op = (IOROperation)this.steps.elementAt(iStep);
/*  880 */       iStep++;
/*  881 */       if ((op.operation_code == 40307) || (iStep == n)) {
/*  882 */         if (op.operation_code != 40307)
/*  883 */           doWork(op);
/*  884 */         PrintLines(4 + this.MarkovNumStates);
/*  885 */         PrintLine("ITERATION # ".concat(String.valueOf(String.valueOf(FormatInteger(Iteration, 2, 0, 0)))));
/*  886 */         SkipLine();
/*  887 */         PrintLine("Value Determination:");
/*  888 */         SkipLine();
/*  889 */         for (int i = 1; i <= this.MarkovNumStates; i++) {
/*  890 */           Print(String.valueOf(String.valueOf(new StringBuffer("g(R").append(FormatInteger(Iteration, 1, 0, 0)).append(") = "))));
/*  891 */           if (this.CurrentMarkovD.A[i][1] == 'ϧ') {
/*  892 */             Print("---- ");
/*      */           } else
/*  894 */             Print(FormatReal(this.CurrentMarkovD.A[i][1], 5, 0, 0));
/*  895 */           Print(String.valueOf(String.valueOf(new StringBuffer("+ (").append(RealToString(this.MarkovDiscountFactor, 5)).append(") ["))));
/*  896 */           for (int j = 1; j <= this.MarkovNumStates; j++) {
/*  897 */             Print(FormatReal(this.CurrentMarkovD.A[i][(j + 1)], 5, 0, 1));
/*  898 */             Print(String.valueOf(String.valueOf(new StringBuffer("V").append(FormatInteger(j - 1, 1, 0, 0)).append("(R").append(FormatInteger(Iteration, 1, 0, 0)).append(") "))));
/*  899 */             if (j < this.MarkovNumStates)
/*  900 */               Print("+ ");
/*      */           }
/*  902 */           PrintLine("]");
/*      */         }
/*  904 */         PrintLines(3 + this.MarkovNumStates);
/*  905 */         SkipLine();
/*  906 */         PrintLine("Solution of Value Determination Equations:");
/*  907 */         for (i = 1; i <= this.MarkovNumStates; i++)
/*  908 */           PrintLine(String.valueOf(String.valueOf(new StringBuffer("V").append(FormatInteger(i, 1, 0, 0)).append("(R").append(FormatInteger(Iteration, 1, 0, 0)).append(") = ").append(FormatReal(this.CurrentMarkovD.cCompute[i], 5, 0, 0)))));
/*  909 */         SkipLine();
/*  910 */         PrintLines(3 + this.MarkovNumDecisions);
/*  911 */         PrintLine("Policy Improvement:");
/*  912 */         SkipLine();
/*  913 */         for (i = 1; i <= this.MarkovNumStates; i++) {
/*  914 */           PrintLines(1 + this.MarkovNumDecisions);
/*  915 */           PrintLine(String.valueOf(String.valueOf(new StringBuffer("State ").append(FormatInteger(i - 1, 1, 0, 0)).append(":"))));
/*  916 */           for (int j = 1; j <= this.MarkovNumDecisions; j++) {
/*  917 */             if (this.MarkovC[i][j] == 'ϧ') {
/*  918 */               Print("---  ");
/*      */             } else
/*  920 */               Print(FormatReal(this.MarkovC[i][j], 5, 0, 0));
/*  921 */             Print(String.valueOf(String.valueOf(new StringBuffer("+ (").append(RealToString(this.MarkovDiscountFactor, 5)).append(") ["))));
/*  922 */             for (int k = 1; k <= this.MarkovNumStates; k++) {
/*  923 */               Print(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(FormatReal(this.MarkovP[i][k][j], 5, 0, 0)))).append("(").append(FormatReal(this.CurrentMarkovD.cCompute[k], 5, 0, 1)).append(") "))));
/*  924 */               if (k < this.MarkovNumStates)
/*  925 */                 Print("+ ");
/*      */             }
/*  927 */             Print("] = ");
/*  928 */             if (this.CurrentMarkovD.Value[i][j] == 'ϧ') {
/*  929 */               PrintLine("---");
/*      */             } else
/*  931 */               PrintLine(FormatReal(this.CurrentMarkovD.Value[i][j], 5, 0, 0));
/*      */           }
/*  933 */           SkipLine();
/*      */         }
/*  935 */         PrintLines(1 + this.MarkovNumStates);
/*  936 */         if (iStep == n) {
/*  937 */           PrintLine("Optimal Policy:");
/*      */         } else
/*  939 */           PrintLine("New Policy:");
/*  940 */         for (i = 1; i <= this.MarkovNumStates; i++)
/*  941 */           PrintLine(String.valueOf(String.valueOf(new StringBuffer("  d").append(FormatInteger(i - 1, 1, 0, 0)).append("(R").append(FormatInteger(Iteration + 1, 1, 0, 0)).append(") = ").append(FormatInteger(this.CurrentMarkovD.NewPolicy[i], 1, 0, 0)))));
/*  942 */         if (iStep == n) {
/*  943 */           Skip(2);
/*  944 */           PrintLines(this.MarkovNumStates);
/*  945 */           for (i = 1; i <= this.MarkovNumStates; i++) {
/*  946 */             PrintLine(String.valueOf(String.valueOf(new StringBuffer("V").append(FormatInteger(i - 1, 1, 0, 0)).append("(R").append(IntegerToString(Iteration + 1, 1)).append(") = ").append(FormatReal(this.CurrentMarkovD.cCompute[i], 5, 0, 0)))));
/*      */           }
/*      */         }
/*  949 */         Skip(3);
/*  950 */         Iteration += 1;
/*  951 */         if (op.operation_code == 40307) {
/*  952 */           doWork(op);
/*      */         }
/*      */       } else {
/*  955 */         doWork(op);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void PrintMarkovSuccessiveApproximations()
/*      */   {
/*  963 */     PrintMarkovModel();
/*  964 */     PrintLines(4 + this.MarkovNumStates);
/*  965 */     PrintLine("Method of Successive Approximations:");
/*  966 */     SkipLine();
/*  967 */     PrintLine("Initial V(i):");
/*  968 */     SkipLine();
/*  969 */     for (int i = 1; i <= this.MarkovNumStates; i++)
/*  970 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("  v(").append(FormatInteger(i, 1, 0, 0)).append(") = 0"))));
/*  971 */     SkipLine();
/*  972 */     PrintLines(3 + this.MarkovNumStates);
/*  973 */     PrintLine("ITERATION #1");
/*  974 */     SkipLine();
/*  975 */     int Iteration = 1;
/*      */     
/*  977 */     reset();
/*  978 */     IOROperation op = null;
/*  979 */     int iStep = 0;
/*  980 */     int n = this.steps.size();
/*      */     
/*  982 */     while (iStep < n) {
/*  983 */       op = (IOROperation)this.steps.elementAt(iStep);
/*  984 */       iStep++;
/*  985 */       if (op.operation_code == 40401) {
/*  986 */         doWork(op);
/*  987 */         PrintLines(1 + this.MarkovNumStates);
/*  988 */         PrintLine("New Policy and New V(i):");
/*  989 */         for (i = 1; i <= this.MarkovNumStates; i++) {
/*  990 */           Print(String.valueOf(String.valueOf(new StringBuffer("  d").append(FormatInteger(i - 1, 1, 0, 0)).append("(R").append(FormatInteger(1, 1, 0, 0)).append(") = ").append(FormatInteger(this.CurrentMarkovS.NewPolicy[i], 1, 0, 0)).append(","))));
/*  991 */           TabPrintLine(24, String.valueOf(String.valueOf(new StringBuffer("V(").append(FormatInteger(i - 1, 1, 0, 0)).append(") = ").append(FormatReal(this.CurrentMarkovS.bCompute[i], 5, 0, 0)))));
/*      */         }
/*  993 */         SkipLine();
/*      */ 
/*      */ 
/*      */       }
/*  997 */       else if ((op.operation_code == 40405) || (iStep == n)) {
/*  998 */         if (op.operation_code != 40405) {
/*  999 */           doWork(op);
/*      */         }
/* 1001 */         PrintLines(2 + this.MarkovNumDecisions);
/* 1002 */         PrintLine("ITERATION # ".concat(String.valueOf(String.valueOf(FormatInteger(Iteration + 1, 2, 0, 0)))));
/* 1003 */         SkipLine();
/* 1004 */         for (i = 1; i <= this.MarkovNumStates; i++) {
/* 1005 */           PrintLine(String.valueOf(String.valueOf(new StringBuffer("State ").append(FormatInteger(i - 1, 1, 0, 0)).append(":"))));
/* 1006 */           for (int j = 1; j <= this.MarkovNumDecisions; j++) {
/* 1007 */             if (this.MarkovC[i][j] == 'ϧ') {
/* 1008 */               Print("---  ");
/*      */             } else
/* 1010 */               Print(FormatReal(this.MarkovC[i][j], 5, 0, 0));
/* 1011 */             Print(String.valueOf(String.valueOf(new StringBuffer("+ (").append(RealToString(this.MarkovDiscountFactor, 5)).append(") ["))));
/* 1012 */             for (int k = 1; k <= this.MarkovNumStates; k++) {
/* 1013 */               Print(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(FormatReal(this.MarkovP[i][k][j], 5, 0, 0)))).append("(").append(FormatReal(this.CurrentMarkovS.bCompute[k], 5, 0, 1)).append(") "))));
/* 1014 */               if (k < this.MarkovNumStates)
/* 1015 */                 Print("+ ");
/*      */             }
/* 1017 */             Print("] = ");
/* 1018 */             if (this.CurrentMarkovS.Value[i][j] == 'ϧ') {
/* 1019 */               PrintLine("---");
/*      */             } else
/* 1021 */               PrintLine(FormatReal(this.CurrentMarkovS.Value[i][j], 5, 0, 0));
/*      */           }
/* 1023 */           SkipLine();
/*      */         }
/* 1025 */         PrintLines(1 + this.MarkovNumStates);
/* 1026 */         PrintLine("New Policy and New V(i):");
/* 1027 */         for (i = 1; i <= this.MarkovNumStates; i++) {
/* 1028 */           Print(String.valueOf(String.valueOf(new StringBuffer("  d").append(FormatInteger(i - 1, 1, 0, 0)).append("(R").append(FormatInteger(Iteration + 1, 1, 0, 0)).append(") = ").append(FormatInteger(this.CurrentMarkovS.NewPolicy[i], 1, 0, 0)).append(","))));
/* 1029 */           TabPrintLine(24, String.valueOf(String.valueOf(new StringBuffer("V(").append(FormatInteger(i - 1, 1, 0, 0)).append(") = ").append(FormatReal(this.CurrentMarkovS.cCompute[i], 5, 0, 0)))));
/*      */         }
/* 1031 */         Skip(3);
/* 1032 */         Iteration += 1;
/* 1033 */         if (op.operation_code == 40405) {
/* 1034 */           doWork(op);
/*      */         }
/*      */       } else {
/* 1037 */         doWork(op);
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORMDSolver.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */