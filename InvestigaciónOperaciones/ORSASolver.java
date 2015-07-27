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
/*      */ public class ORSASolver
/*      */   extends ORSolverBase
/*      */ {
/*      */   private int SimulationNumServers;
/*      */   private int SimulationNumPriorities;
/*   30 */   private ORSolverBase.SimulationDistributionType[] SimulationInterarrival = new ORSolverBase.SimulationDistributionType[4];
/*   31 */   private ORSolverBase.SimulationDistributionType[] SimulationService = new ORSolverBase.SimulationDistributionType[4];
/*   32 */   private ORSolverBase.SimulationType SimulationData = new ORSolverBase.SimulationType(this);
/*   33 */   private ORSolverBase.InteractiveSimulation FirstSimulation = null;
/*   34 */   private ORSolverBase.InteractiveSimulation CurrentSimulation = null;
/*      */   
/*   36 */   private double rndTime = 0.0D;
/*   37 */   private double customerWaitTime = 0.0D;
/*   38 */   private boolean isCustomerAction = false;
/*   39 */   private boolean isCustomerDone = false;
/*   40 */   private boolean isServerDone = false;
/*      */   
/*      */   private String arrivalRate;
/*      */   
/*      */   private String serviceRate;
/*      */   private String ns1Num;
/*      */   private String ns2Num;
/*      */   private String lqStr;
/*      */   private String lsStr;
/*      */   private String wqStr;
/*      */   private String wsStr;
/*      */   private int phaseNum;
/*      */   private boolean isShowData;
/*      */   
/*      */   public ORSASolver()
/*      */   {
/*   56 */     this.SimulationInterarrival[1] = new ORSolverBase.SimulationDistributionType(this);
/*   57 */     this.SimulationInterarrival[2] = new ORSolverBase.SimulationDistributionType(this);
/*   58 */     this.SimulationService[1] = new ORSolverBase.SimulationDistributionType(this);
/*   59 */     this.SimulationService[2] = new ORSolverBase.SimulationDistributionType(this);
/*   60 */     CreateFirstSimulation();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void CalculateInteractiveSimulation()
/*      */   {
/*   70 */     ORSolverBase.InteractiveSimulation Temp = this.FirstSimulation;
/*   71 */     for (int i = 1; i <= this.SimulationNumPriorities; i++)
/*      */     {
/*   73 */       this.SimulationData.L[i] = new ORSolverBase.EstimatorsType(this);
/*   74 */       this.SimulationData.Lq[i] = new ORSolverBase.EstimatorsType(this);
/*   75 */       this.SimulationData.W[i] = new ORSolverBase.EstimatorsType(this);
/*   76 */       this.SimulationData.Wq[i] = new ORSolverBase.EstimatorsType(this);
/*      */     }
/*   78 */     UpdateEstimator(this.SimulationData.Wq[1], 0.0D, 1.0D);
/*   79 */     UpdateEstimator(this.SimulationData.W[1], Temp.NextService[1], 1.0D);
/*   80 */     while (Temp.Next != null) {
/*   81 */       double ElapsedTime = Temp.Next.CurrentTime - Temp.CurrentTime;
/*   82 */       for (i = 1; i <= this.SimulationNumPriorities; i++)
/*      */       {
/*   84 */         int NumberInService = 0;
/*   85 */         for (int j = 1; j <= this.SimulationNumServers; j++) {
/*   86 */           if (Temp.ClassBeingServed[j] == i)
/*   87 */             NumberInService += 1;
/*      */         }
/*   89 */         UpdateEstimator(this.SimulationData.L[i], (Temp.NumberOfCustomers[i] + NumberInService) * ElapsedTime, ElapsedTime);
/*   90 */         UpdateEstimator(this.SimulationData.Lq[i], Temp.NumberOfCustomers[i] * ElapsedTime, ElapsedTime);
/*   91 */         if ((Temp.Answer2 <= 2) && (Temp.Answer3 >= 2)) {
/*   92 */           int WhichClass = Temp.Answer2;
/*   93 */           int WhichServer = Temp.Answer3 - 1;
/*   94 */           if (WhichClass == i) {
/*   95 */             UpdateEstimator(this.SimulationData.Wq[i], Temp.CurrentTime - Temp.WhenArrived, 1.0D);
/*   96 */             UpdateEstimator(this.SimulationData.W[i], Temp.NextService[WhichServer] - Temp.WhenArrived, 1.0D);
/*      */           }
/*      */         }
/*   99 */         else if ((Temp.Answer2 >= 3) && (Temp.Answer4 >= 2)) {
/*  100 */           int WhichServer = Temp.Answer2 - 2;
/*  101 */           int WhichClass = Temp.Answer4 - 1;
/*  102 */           if (WhichClass == i) {
/*  103 */             UpdateEstimator(this.SimulationData.Wq[i], Temp.CurrentTime - Temp.WhenArrived, 1.0D);
/*  104 */             UpdateEstimator(this.SimulationData.W[i], Temp.NextService[WhichServer] - Temp.WhenArrived, 1.0D);
/*      */           }
/*      */         }
/*      */       }
/*  108 */       Temp = Temp.Next;
/*      */     }
/*  110 */     CalculateAllEstimators();
/*      */   }
/*      */   
/*      */ 
/*      */   private void CreateFirstSimulation()
/*      */   {
/*  116 */     this.CurrentSimulation = new ORSolverBase.InteractiveSimulation(this);
/*  117 */     this.FirstSimulation = this.CurrentSimulation;
/*      */     
/*  119 */     this.CurrentSimulation.Iteration = 1;
/*  120 */     for (int i = 1; i <= 4; i++)
/*  121 */       this.CurrentSimulation.Answer1[i] = false;
/*  122 */     this.CurrentSimulation.Answer2 = 0;
/*  123 */     this.CurrentSimulation.Answer3 = 0;
/*  124 */     this.CurrentSimulation.Answer4 = 0;
/*  125 */     this.CurrentSimulation.CurrentTime = 0.0D;
/*  126 */     this.CurrentSimulation.NumberOfCustomers[1] = 0;
/*  127 */     this.CurrentSimulation.NumberOfCustomers[2] = 0;
/*  128 */     this.CurrentSimulation.ClassBeingServed[1] = 1;
/*  129 */     this.CurrentSimulation.ClassBeingServed[2] = 0;
/*  130 */     this.CurrentSimulation.NextArrival[1] = -1.0D;
/*  131 */     this.CurrentSimulation.NextArrival[2] = -1.0D;
/*  132 */     this.CurrentSimulation.NextService[1] = -1.0D;
/*  133 */     this.CurrentSimulation.NextService[2] = -1.0D;
/*      */     
/*      */ 
/*  136 */     for (i = 1; i <= 2; i++) {
/*  137 */       for (int j = 0; j <= 199; j++)
/*  138 */         this.SimulationData.WhenArrived[i][j] = 0.0D;
/*  139 */       this.SimulationData.NextInLine[i] = 0;
/*  140 */       this.SimulationData.LastInLine[i] = 199;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void CreateNextSimulation()
/*      */   {
/*  148 */     ORSolverBase.InteractiveSimulation Temp = (ORSolverBase.InteractiveSimulation)this.CurrentSimulation.clone();
/*  149 */     for (int i = 1; i <= 4; i++)
/*  150 */       Temp.Answer1[i] = false;
/*  151 */     Temp.Answer2 = 0;
/*  152 */     Temp.Answer3 = 0;
/*  153 */     Temp.Answer4 = 0;
/*  154 */     Temp.Iteration += 1;
/*  155 */     Temp.Last = this.CurrentSimulation;
/*  156 */     this.CurrentSimulation.Next = Temp;
/*  157 */     this.CurrentSimulation = Temp;
/*      */   }
/*      */   
/*      */   private void UpdateEstimator(ORSolverBase.EstimatorsType Estimator, double NewY, double NewZ) {
/*  161 */     Estimator.Y += NewY;
/*  162 */     Estimator.Z += NewZ;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void CalculateAnEstimator(ORSolverBase.EstimatorsType Estimator)
/*      */   {
/*  170 */     long n = Math.round(this.SimulationData.NumCycles);
/*  171 */     if (n < 2) {
/*  172 */       if (n == 0) {
/*  173 */         if (Estimator.Z < 1.0E-4D) {
/*  174 */           Estimator.PointEstimate = 0.0D;
/*      */         } else {
/*  176 */           Estimator.PointEstimate = (Estimator.Y / Estimator.Z);
/*      */         }
/*      */       }
/*  179 */       else if (Estimator.SumZ < 1.0E-4D) {
/*  180 */         Estimator.PointEstimate = 0.0D;
/*      */       } else {
/*  182 */         Estimator.PointEstimate = (Estimator.SumY / Estimator.SumZ);
/*      */       }
/*  184 */       Estimator.ConfidenceInterval[1] = 0.0D;
/*  185 */       Estimator.ConfidenceInterval[2] = 0.0D;
/*      */     }
/*      */     else {
/*  188 */       Estimator.PointEstimate = (Estimator.SumY / Estimator.SumZ);
/*  189 */       double s11 = 1 / (n - 1) * Estimator.SumY2 - 1 / (n * (n - 1)) * Math.pow(Estimator.SumY, 2.0D);
/*  190 */       double s22 = 1 / (n - 1) * Estimator.SumZ2 - 1 / (n * (n - 1)) * Math.pow(Estimator.SumZ, 2.0D);
/*  191 */       double s12 = 1 / (n - 1) * Estimator.SumYZ - 1 / (n * (n - 1)) * Estimator.SumY * Estimator.SumZ;
/*  192 */       double s = s11 - 2 * (Estimator.SumY / Estimator.SumZ) * s12 + Math.pow(Estimator.SumY / Estimator.SumZ, 2.0D) * s22;
/*  193 */       Estimator.ConfidenceInterval[1] = (Estimator.PointEstimate - 1.96D * Math.sqrt(s) / (Estimator.SumZ / Math.sqrt(n)));
/*  194 */       Estimator.ConfidenceInterval[2] = (Estimator.PointEstimate + 1.96D * Math.sqrt(s) / (Estimator.SumZ / Math.sqrt(n)));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void CalculateAllEstimators()
/*      */   {
/*  201 */     for (int i = 1; i <= this.SimulationNumPriorities; i++) {
/*  202 */       CalculateAnEstimator(this.SimulationData.L[i]);
/*  203 */       CalculateAnEstimator(this.SimulationData.Lq[i]);
/*  204 */       CalculateAnEstimator(this.SimulationData.W[i]);
/*  205 */       CalculateAnEstimator(this.SimulationData.Wq[i]);
/*  206 */       for (int j = 0; j <= 10; j++)
/*  207 */         CalculateAnEstimator(this.SimulationData.P[i][j]);
/*      */     }
/*      */   }
/*      */   
/*      */   private double GenerateArrival(int PriorityClass) {
/*  212 */     double rnd = 0.0D;
/*      */     
/*  214 */     switch (this.SimulationInterarrival[PriorityClass].Distribution)
/*      */     {
/*      */     case 1: 
/*  217 */       rnd = this.SimulationInterarrival[PriorityClass].MinValue;
/*  218 */       break;
/*      */     case 2: 
/*  220 */       rnd = GenerateErlang(this.SimulationInterarrival[PriorityClass].Mean, this.SimulationInterarrival[PriorityClass].k);
/*  221 */       break;
/*      */     case 3: 
/*  223 */       rnd = GenerateExponential(this.SimulationInterarrival[PriorityClass].Mean);
/*  224 */       break;
/*      */     case 4: 
/*  226 */       rnd = GenerateTranslatedExponential(this.SimulationInterarrival[PriorityClass].MinValue, this.SimulationInterarrival[PriorityClass].Mean);
/*  227 */       break;
/*      */     case 5: 
/*  229 */       rnd = GenerateUniform(this.SimulationInterarrival[PriorityClass].MinValue, this.SimulationInterarrival[PriorityClass].MaxValue);
/*      */     }
/*      */     
/*  232 */     return rnd;
/*      */   }
/*      */   
/*      */   private double GenerateService(int PriorityClass) {
/*  236 */     double rnd = 0.0D;
/*      */     
/*  238 */     switch (this.SimulationService[PriorityClass].Distribution)
/*      */     {
/*      */     case 1: 
/*  241 */       rnd = this.SimulationService[PriorityClass].MinValue;
/*  242 */       break;
/*      */     case 2: 
/*  244 */       rnd = GenerateErlang(this.SimulationService[PriorityClass].Mean, this.SimulationService[PriorityClass].k);
/*  245 */       break;
/*      */     case 3: 
/*  247 */       rnd = GenerateExponential(this.SimulationService[PriorityClass].Mean);
/*  248 */       break;
/*      */     case 4: 
/*  250 */       rnd = GenerateTranslatedExponential(this.SimulationService[PriorityClass].MinValue, this.SimulationService[PriorityClass].Mean);
/*  251 */       break;
/*      */     case 5: 
/*  253 */       rnd = GenerateUniform(this.SimulationService[PriorityClass].MinValue, this.SimulationService[PriorityClass].MaxValue);
/*      */     }
/*      */     
/*  256 */     return rnd;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private double GenerateErlang(double Mean, int k)
/*      */   {
/*  263 */     double Temp = 1.0D;
/*  264 */     for (int i = 1; i <= k; i++)
/*  265 */       Temp *= Math.random();
/*  266 */     return -(Mean / k) * Math.log(Temp);
/*      */   }
/*      */   
/*      */   private double GenerateExponential(double Mean) {
/*  270 */     return -Math.log(Math.random()) * Mean;
/*      */   }
/*      */   
/*      */   private double GenerateTranslatedExponential(double Min, double Mean) {
/*  274 */     return Min - Math.log(Math.random()) * (Mean - Min);
/*      */   }
/*      */   
/*      */   private double GenerateUniform(double Min, double Max) {
/*  278 */     return Min + (Max - Min) * Math.random();
/*      */   }
/*      */   
/*      */   private int setDistributionValue(ORSolverBase.SimulationDistributionType s, Vector p, int index) {
/*  282 */     s.Distribution = (((Integer)p.elementAt(index)).intValue() + 1);
/*  283 */     index++;
/*  284 */     switch (s.Distribution)
/*      */     {
/*      */     case 1: 
/*  287 */       s.Mean = ((Double)p.elementAt(index)).doubleValue();
/*  288 */       index++;
/*  289 */       s.MinValue = s.Mean;
/*  290 */       s.MaxValue = s.Mean;
/*  291 */       s.StandardDeviation = 0.0D;
/*  292 */       break;
/*      */     case 2: 
/*  294 */       s.Mean = ((Double)p.elementAt(index)).doubleValue();
/*  295 */       index++;
/*  296 */       s.k = ((Integer)p.elementAt(index)).intValue();
/*  297 */       index++;
/*  298 */       s.MaxValue = -1.0D;
/*  299 */       s.StandardDeviation = (s.Mean / Math.sqrt(s.k));
/*  300 */       break;
/*      */     case 3: 
/*  302 */       s.Mean = ((Double)p.elementAt(index)).doubleValue();
/*  303 */       index++;
/*  304 */       s.StandardDeviation = Math.sqrt(s.Mean);
/*  305 */       break;
/*      */     case 4: 
/*  307 */       s.MinValue = ((Double)p.elementAt(index)).doubleValue();
/*  308 */       index++;
/*  309 */       s.Mean = ((Double)p.elementAt(index)).doubleValue();
/*  310 */       index++;
/*  311 */       s.StandardDeviation = Math.sqrt(s.Mean);
/*  312 */       break;
/*      */     case 5: 
/*  314 */       s.MinValue = ((Double)p.elementAt(index)).doubleValue();
/*  315 */       index++;
/*  316 */       s.MaxValue = ((Double)p.elementAt(index)).doubleValue();
/*  317 */       index++;
/*  318 */       s.Mean = ((s.MaxValue - s.MinValue) / 2);
/*  319 */       s.StandardDeviation = s.Mean;
/*      */     }
/*      */     
/*  322 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean doWork(IOROperation op, IORSAProcessController.SAData data)
/*      */   {
/*  328 */     boolean success = doWork(op);
/*      */     
/*  330 */     if (success) {
/*  331 */       getData(data);
/*      */     }
/*  333 */     return success;
/*      */   }
/*      */   
/*      */ 
/*      */   public void reset()
/*      */   {
/*  339 */     CreateFirstSimulation();
/*      */   }
/*      */   
/*      */   public void getData(IORSAProcessController.SAData data)
/*      */   {
/*  344 */     data.num_server = this.SimulationNumServers;
/*  345 */     data.num_class = this.SimulationNumPriorities;
/*      */     
/*      */ 
/*      */ 
/*  349 */     for (int i = 0; i <= 1; i++) { ORSolverBase.InteractiveSimulation Temp;
/*  350 */       ORSolverBase.InteractiveSimulation Temp; if (i == 0) { ORSolverBase.InteractiveSimulation Temp;
/*  351 */         if (this.CurrentSimulation.Last == null) {
/*  352 */           Temp = this.CurrentSimulation;
/*      */         } else {
/*  354 */           Temp = this.CurrentSimulation.Last;
/*      */         }
/*      */       } else {
/*  357 */         if (this.CurrentSimulation.Last == null) {
/*  358 */           data.current_time[1] = -1.0D;
/*  359 */           break;
/*      */         }
/*      */         
/*  362 */         Temp = this.CurrentSimulation;
/*      */       }
/*      */       
/*  365 */       data.class_of_customer_being_served_by_server1[i] = Temp.ClassBeingServed[1];
/*  366 */       data.class_of_customer_being_served_by_server2[i] = Temp.ClassBeingServed[2];
/*  367 */       data.customer_being_served[i] = false;
/*  368 */       if ((this.SimulationNumServers == 1) && (this.SimulationNumPriorities == 1) && (Temp.ClassBeingServed[1] == 1)) {
/*  369 */         data.customer_being_served[i] = true;
/*      */       }
/*  371 */       data.current_time[i] = Temp.CurrentTime;
/*  372 */       data.num_customer1_in_queue[i] = Temp.NumberOfCustomers[1];
/*  373 */       data.num_customer2_in_queue[i] = Temp.NumberOfCustomers[2];
/*      */       
/*  375 */       data.customer1_arrive_time[i] = Temp.NextArrival[1];
/*  376 */       data.server1_finish_time[i] = Temp.NextService[1];
/*  377 */       data.customer2_arrive_time[i] = Temp.NextArrival[2];
/*  378 */       data.server2_finish_time[i] = Temp.NextService[2];
/*      */     }
/*      */     
/*  381 */     data.random_time = this.rndTime;
/*  382 */     data.customer_wait_time = this.customerWaitTime;
/*  383 */     data.is_customer_done = this.isCustomerDone;
/*  384 */     data.is_server_done = this.isServerDone;
/*  385 */     data.is_customer_event = this.isCustomerAction;
/*      */     
/*      */ 
/*  388 */     for (i = 0; i <= 1; i++) {
/*  389 */       data.average_wait_time_exclude_service[i] = this.SimulationData.Wq[(i + 1)].PointEstimate;
/*  390 */       data.average_wait_time_include_service[i] = this.SimulationData.W[(i + 1)].PointEstimate;
/*  391 */       data.average_num_wait_to_begin_service[i] = this.SimulationData.Lq[(i + 1)].PointEstimate;
/*  392 */       data.average_num_wait_or_in_service[i] = this.SimulationData.L[(i + 1)].PointEstimate;
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean doWork(IOROperation op)
/*      */   {
/*  398 */     Vector p = op.parameters;
/*      */     
/*      */ 
/*  401 */     int CorrectAnswer = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  406 */     int Problem = 10 * this.SimulationNumPriorities + this.SimulationNumServers;
/*  407 */     this.isCustomerDone = false;
/*  408 */     this.isServerDone = false;
/*  409 */     switch (op.operation_code)
/*      */     {
/*      */     case 50101: 
/*  412 */       this.SimulationNumServers = ((Integer)p.elementAt(0)).intValue();
/*  413 */       this.SimulationNumPriorities = ((Integer)p.elementAt(1)).intValue();
/*      */       
/*      */ 
/*  416 */       int index = 2;
/*  417 */       index = setDistributionValue(this.SimulationInterarrival[1], p, index);
/*  418 */       index = setDistributionValue(this.SimulationService[1], p, index);
/*  419 */       if (this.SimulationNumPriorities == 2) {
/*  420 */         index = setDistributionValue(this.SimulationInterarrival[2], p, index);
/*  421 */         index = setDistributionValue(this.SimulationService[2], p, index);
/*      */       }
/*      */       break;
/*      */     case 50301: 
/*  425 */       CreateFirstSimulation();
/*  426 */       break;
/*      */     case 50201: 
/*  428 */       boolean isGenerateTime = ((Boolean)p.elementAt(0)).booleanValue();
/*  429 */       int ch; int ch; if (!isGenerateTime) {
/*  430 */         this.rndTime = ((Double)p.elementAt(1)).doubleValue();
/*  431 */         ch = ((Integer)p.elementAt(2)).intValue() + 1;
/*      */       }
/*      */       else {
/*  434 */         ch = ((Integer)p.elementAt(1)).intValue() + 1; }
/*  435 */       if ((this.SimulationNumPriorities == 1) && 
/*  436 */         (ch > 1)) { ch += 1;
/*      */       }
/*  438 */       if (!isGenerateTime) {
/*  439 */         if ((ch == 1) || (ch == 2)) {
/*  440 */           this.CurrentSimulation.NextArrival[ch] = (this.CurrentSimulation.CurrentTime + this.rndTime);
/*  441 */           this.CurrentSimulation.Answer1[ch] = true;
/*      */         }
/*  443 */         else if (ch == 3) {
/*  444 */           this.CurrentSimulation.NextService[1] = (this.CurrentSimulation.CurrentTime + this.rndTime);
/*  445 */           this.CurrentSimulation.Answer1[3] = true;
/*      */         }
/*      */         else {
/*  448 */           this.CurrentSimulation.NextService[2] = (this.CurrentSimulation.CurrentTime + this.rndTime);
/*  449 */           this.CurrentSimulation.Answer1[4] = true;
/*      */         }
/*      */       }
/*      */       else {
/*  453 */         switch (ch)
/*      */         {
/*      */         case 1: 
/*  456 */           boolean Mistake = this.CurrentSimulation.NextArrival[1] >= 0;
/*  457 */           if ((this.CurrentSimulation.Iteration > 5) || (!Mistake))
/*      */           {
/*  459 */             this.rndTime = GenerateArrival(1);
/*  460 */             this.CurrentSimulation.NextArrival[1] = (this.CurrentSimulation.CurrentTime + this.rndTime);
/*  461 */             this.CurrentSimulation.Answer1[1] = true;
/*      */           }
/*      */           else {
/*  464 */             this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  465 */             return false;
/*      */           }
/*      */           break;
/*      */         case 2: 
/*  469 */           boolean Mistake = this.CurrentSimulation.NextArrival[2] > 0;
/*  470 */           if ((this.CurrentSimulation.Iteration > 5) || (!Mistake))
/*      */           {
/*  472 */             this.rndTime = GenerateArrival(2);
/*  473 */             this.CurrentSimulation.NextArrival[2] = (this.CurrentSimulation.CurrentTime + this.rndTime);
/*  474 */             this.CurrentSimulation.Answer1[2] = true;
/*      */           }
/*      */           else {
/*  477 */             this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  478 */             return false;
/*      */           }
/*      */           break;
/*      */         case 3: 
/*  482 */           boolean Mistake = (this.CurrentSimulation.NextService[1] > 0) || (this.CurrentSimulation.ClassBeingServed[1] == 0);
/*  483 */           if ((this.CurrentSimulation.Iteration > 5) || (!Mistake))
/*      */           {
/*  485 */             if (this.CurrentSimulation.ClassBeingServed[1] == 2) {
/*  486 */               this.rndTime = GenerateService(2);
/*      */             } else
/*  488 */               this.rndTime = GenerateService(1);
/*  489 */             this.CurrentSimulation.NextService[1] = (this.CurrentSimulation.CurrentTime + this.rndTime);
/*  490 */             this.CurrentSimulation.Answer1[3] = true;
/*      */           }
/*      */           else {
/*  493 */             this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  494 */             return false;
/*      */           }
/*      */           break;
/*      */         case 4: 
/*  498 */           boolean Mistake = (this.CurrentSimulation.NextService[2] > 0) || (this.CurrentSimulation.ClassBeingServed[2] == 0);
/*  499 */           if ((this.CurrentSimulation.Iteration > 5) || (!Mistake))
/*      */           {
/*  501 */             if (this.CurrentSimulation.ClassBeingServed[2] == 2) {
/*  502 */               this.rndTime = GenerateService(2);
/*      */             } else
/*  504 */               this.rndTime = GenerateService(1);
/*  505 */             this.CurrentSimulation.NextService[2] = (this.CurrentSimulation.CurrentTime + this.rndTime);
/*  506 */             this.CurrentSimulation.Answer1[4] = true;
/*      */           }
/*      */           else {
/*  509 */             this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  510 */             return false;
/*      */           }
/*      */           break;
/*      */         }
/*      */       }
/*  515 */       break;
/*      */     case 50202: 
/*  517 */       boolean Mistake = false;
/*  518 */       if (this.CurrentSimulation.NextArrival[1] < 0)
/*  519 */         Mistake = true;
/*  520 */       if ((this.SimulationNumPriorities == 2) && 
/*  521 */         (this.CurrentSimulation.NextArrival[2] < 0)) {
/*  522 */         Mistake = true;
/*      */       }
/*  524 */       if ((this.CurrentSimulation.ClassBeingServed[1] > 0) && (this.CurrentSimulation.NextService[1] < 0))
/*  525 */         Mistake = true;
/*  526 */       if ((this.SimulationNumServers == 2) && 
/*  527 */         (this.CurrentSimulation.ClassBeingServed[2] > 0) && (this.CurrentSimulation.NextService[2] < 0)) {
/*  528 */         Mistake = true;
/*      */       }
/*  530 */       if ((this.CurrentSimulation.Iteration <= 5) && (Mistake)) {
/*  531 */         this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  532 */         return false;
/*      */       }
/*      */       break;
/*      */     case 50203: 
/*  536 */       int ch = ((Integer)p.elementAt(0)).intValue() + 1;
/*  537 */       switch (Problem)
/*      */       {
/*      */       case 11: 
/*  540 */         if ((ch < 1) || (ch > 2)) {
/*  541 */           ch = 0;
/*  542 */         } else if (ch == 2)
/*  543 */           ch = 3;
/*  544 */         if ((this.CurrentSimulation.NextService[1] < this.CurrentSimulation.NextArrival[1]) && (this.CurrentSimulation.ClassBeingServed[1] == 1)) {
/*  545 */           CorrectAnswer = 3;
/*      */         } else
/*  547 */           CorrectAnswer = 1;
/*  548 */         break;
/*      */       case 21: 
/*  550 */         if ((ch < 1) || (ch > 3))
/*  551 */           ch = 0;
/*  552 */         if ((this.CurrentSimulation.NextService[1] < this.CurrentSimulation.NextArrival[1]) && (this.CurrentSimulation.NextService[1] < this.CurrentSimulation.NextArrival[2]) && (this.CurrentSimulation.NextService[1] >= 0)) {
/*  553 */           CorrectAnswer = 3;
/*  554 */         } else if (this.CurrentSimulation.NextArrival[1] < this.CurrentSimulation.NextArrival[2]) {
/*  555 */           CorrectAnswer = 1;
/*      */         } else
/*  557 */           CorrectAnswer = 2;
/*  558 */         break;
/*      */       case 12: 
/*  560 */         if ((ch < 1) || (ch > 3)) {
/*  561 */           ch = 0;
/*  562 */         } else if (ch == 2) {
/*  563 */           ch = 3;
/*  564 */         } else if (ch == 3)
/*  565 */           ch = 4;
/*  566 */         if ((this.CurrentSimulation.NextService[1] >= 0) && (this.CurrentSimulation.NextService[1] < this.CurrentSimulation.NextArrival[1]) && ((this.CurrentSimulation.NextService[2] < 0) || ((this.CurrentSimulation.NextService[2] >= 0) && (this.CurrentSimulation.NextService[1] < this.CurrentSimulation.NextService[2])))) {
/*  567 */           CorrectAnswer = 3;
/*  568 */         } else if ((this.CurrentSimulation.ClassBeingServed[2] > 0) && (this.CurrentSimulation.NextService[2] < this.CurrentSimulation.NextArrival[1])) {
/*  569 */           CorrectAnswer = 4;
/*      */         } else
/*  571 */           CorrectAnswer = 1;
/*  572 */         break;
/*      */       case 22: 
/*  574 */         if ((ch < 1) || (ch > 4))
/*  575 */           ch = 0;
/*  576 */         if ((this.CurrentSimulation.NextService[1] >= 0) && (this.CurrentSimulation.NextService[1] < this.CurrentSimulation.NextArrival[1]) && (this.CurrentSimulation.NextService[1] < this.CurrentSimulation.NextArrival[2]) && ((this.CurrentSimulation.NextService[2] < 0) || ((this.CurrentSimulation.NextService[2] >= 0) && (this.CurrentSimulation.NextService[1] < this.CurrentSimulation.NextService[2])))) {
/*  577 */           CorrectAnswer = 3;
/*  578 */         } else if ((this.CurrentSimulation.NextService[2] >= 0) && (this.CurrentSimulation.NextService[2] < this.CurrentSimulation.NextArrival[1]) && (this.CurrentSimulation.NextService[2] < this.CurrentSimulation.NextArrival[2])) {
/*  579 */           CorrectAnswer = 4;
/*  580 */         } else if (this.CurrentSimulation.NextArrival[1] < this.CurrentSimulation.NextArrival[2]) {
/*  581 */           CorrectAnswer = 1;
/*      */         } else
/*  583 */           CorrectAnswer = 2;
/*  584 */         break;
/*      */       }
/*  586 */       switch (ch)
/*      */       {
/*      */       case 1: 
/*  589 */         this.isCustomerAction = true;
/*  590 */         boolean Mistake = CorrectAnswer != 1;
/*  591 */         if ((this.CurrentSimulation.Iteration > 5) || (!Mistake))
/*      */         {
/*  593 */           CreateNextSimulation();
/*  594 */           if (this.CurrentSimulation.NextArrival[1] > 0)
/*  595 */             this.CurrentSimulation.CurrentTime = this.CurrentSimulation.NextArrival[1];
/*  596 */           this.CurrentSimulation.NextArrival[1] = -1.0D;
/*  597 */           this.CurrentSimulation.Answer2 = 1;
/*      */         }
/*      */         else {
/*  600 */           this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  601 */           return false;
/*      */         }
/*      */         break;
/*      */       case 2: 
/*  605 */         this.isCustomerAction = true;
/*  606 */         boolean Mistake = CorrectAnswer != 2;
/*  607 */         if ((this.CurrentSimulation.Iteration > 5) || (!Mistake))
/*      */         {
/*  609 */           CreateNextSimulation();
/*  610 */           if (this.CurrentSimulation.NextArrival[2] > 0)
/*  611 */             this.CurrentSimulation.CurrentTime = this.CurrentSimulation.NextArrival[2];
/*  612 */           this.CurrentSimulation.NextArrival[2] = -1.0D;
/*  613 */           this.CurrentSimulation.Answer2 = 2;
/*      */         }
/*      */         else {
/*  616 */           this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  617 */           return false;
/*      */         }
/*      */         break;
/*      */       case 3: 
/*  621 */         this.isCustomerAction = false;
/*  622 */         boolean Mistake = CorrectAnswer != 3;
/*  623 */         if ((this.CurrentSimulation.Iteration > 5) || (!Mistake))
/*      */         {
/*  625 */           CreateNextSimulation();
/*  626 */           if (this.CurrentSimulation.NextService[1] > 0)
/*  627 */             this.CurrentSimulation.CurrentTime = this.CurrentSimulation.NextService[1];
/*  628 */           this.CurrentSimulation.ClassBeingServed[1] = 0;
/*  629 */           this.CurrentSimulation.NextService[1] = -1.0D;
/*  630 */           this.CurrentSimulation.Answer2 = 3;
/*      */         }
/*      */         else {
/*  633 */           this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  634 */           return false;
/*      */         }
/*      */         break;
/*      */       case 4: 
/*  638 */         this.isCustomerAction = false;
/*  639 */         boolean Mistake = CorrectAnswer != 4;
/*  640 */         if ((this.CurrentSimulation.Iteration > 5) || (!Mistake))
/*      */         {
/*  642 */           CreateNextSimulation();
/*  643 */           if (this.CurrentSimulation.NextService[2] > 0)
/*  644 */             this.CurrentSimulation.CurrentTime = this.CurrentSimulation.NextService[2];
/*  645 */           this.CurrentSimulation.ClassBeingServed[2] = 0;
/*  646 */           this.CurrentSimulation.NextService[2] = -1.0D;
/*  647 */           this.CurrentSimulation.Answer2 = 4;
/*      */         }
/*      */         else {
/*  650 */           this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  651 */           return false;
/*      */         }
/*      */         break;
/*      */       }
/*  655 */       break;
/*      */     case 50204: 
/*  657 */       int ch = ((Integer)p.elementAt(0)).intValue() + 1;
/*  658 */       switch (ch) {
/*      */       case 1:  boolean Mistake;
/*      */         boolean Mistake;
/*  661 */         if (this.SimulationNumServers == 1) {
/*  662 */           Mistake = this.CurrentSimulation.ClassBeingServed[1] == 0;
/*      */         } else
/*  664 */           Mistake = (this.CurrentSimulation.ClassBeingServed[1] == 0) || (this.CurrentSimulation.ClassBeingServed[2] == 0);
/*  665 */         if ((this.CurrentSimulation.Iteration > 5) || (!Mistake))
/*      */         {
/*  667 */           this.CurrentSimulation.Answer3 = 1;
/*  668 */           this.CurrentSimulation.NumberOfCustomers[this.CurrentSimulation.Answer2] += 1;
/*  669 */           this.SimulationData.LastInLine[this.CurrentSimulation.Answer2] = ((this.SimulationData.LastInLine[this.CurrentSimulation.Answer2] + 1) % 200);
/*  670 */           this.SimulationData.WhenArrived[this.CurrentSimulation.Answer2][this.SimulationData.LastInLine[this.CurrentSimulation.Answer2]] = this.CurrentSimulation.CurrentTime;
/*      */         }
/*      */         else {
/*  673 */           this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  674 */           return false;
/*      */         }
/*      */         break;
/*      */       case 2: 
/*  678 */         this.isCustomerDone = true;
/*  679 */         boolean Mistake; boolean Mistake; if (this.CurrentSimulation.Answer2 == 1) {
/*  680 */           Mistake = this.CurrentSimulation.ClassBeingServed[1] > 0;
/*      */         } else
/*  682 */           Mistake = (this.CurrentSimulation.ClassBeingServed[1] > 0) || (this.CurrentSimulation.NumberOfCustomers[1] > 0);
/*  683 */         if ((this.CurrentSimulation.Iteration > 5) || (!Mistake))
/*      */         {
/*  685 */           this.customerWaitTime = 0.0D;
/*  686 */           this.CurrentSimulation.Answer3 = 2;
/*  687 */           this.CurrentSimulation.ClassBeingServed[1] = this.CurrentSimulation.Answer2;
/*  688 */           this.CurrentSimulation.NextService[1] = -1.0D;
/*  689 */           this.CurrentSimulation.WhenArrived = this.CurrentSimulation.CurrentTime;
/*      */         }
/*      */         else {
/*  692 */           this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  693 */           return false;
/*      */         }
/*      */         break;
/*      */       case 3: 
/*  697 */         this.isCustomerDone = true;
/*  698 */         boolean Mistake; boolean Mistake; if (this.CurrentSimulation.Answer2 == 1) {
/*  699 */           Mistake = this.CurrentSimulation.ClassBeingServed[2] > 0;
/*      */         } else
/*  701 */           Mistake = (this.CurrentSimulation.ClassBeingServed[2] > 0) || (this.CurrentSimulation.NumberOfCustomers[1] > 0);
/*  702 */         if ((this.CurrentSimulation.Iteration > 5) || (!Mistake))
/*      */         {
/*  704 */           this.customerWaitTime = 0.0D;
/*  705 */           this.CurrentSimulation.Answer3 = 3;
/*  706 */           this.CurrentSimulation.ClassBeingServed[2] = this.CurrentSimulation.Answer2;
/*  707 */           this.CurrentSimulation.NextService[2] = -1.0D;
/*  708 */           this.CurrentSimulation.WhenArrived = this.CurrentSimulation.CurrentTime;
/*      */         }
/*      */         else {
/*  711 */           this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  712 */           return false;
/*      */         }
/*      */         break; }
/*  715 */       break;
/*      */     case 50205: 
/*  717 */       int ch = ((Integer)p.elementAt(0)).intValue() + 1;
/*  718 */       switch (ch) {case 1:  boolean Mistake;
/*      */         boolean Mistake;
/*  720 */         if (this.SimulationNumPriorities == 1) {
/*  721 */           Mistake = this.CurrentSimulation.NumberOfCustomers[1] > 0;
/*      */         } else
/*  723 */           Mistake = (this.CurrentSimulation.NumberOfCustomers[1] > 0) || (this.CurrentSimulation.NumberOfCustomers[2] > 0);
/*  724 */         if ((this.CurrentSimulation.Iteration > 5) || (!Mistake))
/*      */         {
/*  726 */           this.CurrentSimulation.Answer4 = 1;
/*      */         }
/*      */         else {
/*  729 */           this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  730 */           return false;
/*      */         }
/*      */         break;
/*      */       case 2: 
/*  734 */         this.isServerDone = true;
/*  735 */         boolean Mistake = this.CurrentSimulation.NumberOfCustomers[1] == 0;
/*  736 */         if ((this.CurrentSimulation.Iteration > 5) || (!Mistake))
/*      */         {
/*  738 */           this.CurrentSimulation.Answer4 = 2;
/*  739 */           if (this.CurrentSimulation.NumberOfCustomers[1] != 0)
/*      */           {
/*  741 */             this.CurrentSimulation.NumberOfCustomers[1] -= 1;
/*  742 */             this.CurrentSimulation.WhenArrived = this.SimulationData.WhenArrived[1][this.SimulationData.NextInLine[1]];
/*  743 */             this.SimulationData.NextInLine[1] = ((this.SimulationData.NextInLine[1] + 1) % 200);
/*      */           }
/*      */           else {
/*  746 */             this.CurrentSimulation.WhenArrived = this.CurrentSimulation.CurrentTime; }
/*  747 */           this.CurrentSimulation.ClassBeingServed[(this.CurrentSimulation.Answer2 - 2)] = 1;
/*  748 */           this.customerWaitTime = (this.CurrentSimulation.CurrentTime - this.CurrentSimulation.WhenArrived);
/*      */         }
/*      */         else {
/*  751 */           this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  752 */           return false;
/*      */         }
/*      */         break;
/*      */       case 3: 
/*  756 */         this.isServerDone = true;
/*  757 */         boolean Mistake = (this.CurrentSimulation.NumberOfCustomers[1] > 0) || (this.CurrentSimulation.NumberOfCustomers[2] == 0);
/*  758 */         if ((this.CurrentSimulation.Iteration > 5) || (!Mistake))
/*      */         {
/*  760 */           this.CurrentSimulation.Answer4 = 3;
/*  761 */           if (this.CurrentSimulation.NumberOfCustomers[2] != 0)
/*      */           {
/*  763 */             this.CurrentSimulation.NumberOfCustomers[2] -= 1;
/*  764 */             this.CurrentSimulation.WhenArrived = this.SimulationData.WhenArrived[2][this.SimulationData.NextInLine[2]];
/*  765 */             this.SimulationData.NextInLine[2] = ((this.SimulationData.NextInLine[2] + 1) % 200);
/*      */           }
/*      */           else {
/*  768 */             this.CurrentSimulation.WhenArrived = this.CurrentSimulation.CurrentTime; }
/*  769 */           this.CurrentSimulation.ClassBeingServed[(this.CurrentSimulation.Answer2 - 2)] = 2;
/*  770 */           this.customerWaitTime = (this.CurrentSimulation.CurrentTime - this.CurrentSimulation.WhenArrived);
/*      */         }
/*      */         else {
/*  773 */           this.errInfo = "Sorry.  That is incorrect.  Please try again.";
/*  774 */           return false;
/*      */         }
/*      */         break;
/*      */       }
/*  778 */       break;
/*      */     case 50302: 
/*  780 */       CalculateInteractiveSimulation();
/*  781 */       break;
/*      */     }
/*  783 */     return true;
/*      */   }
/*      */   
/*      */ 
/*  787 */   private boolean showResult = false;
/*      */   
/*      */   public void setPrintShowResult(boolean bShow) {
/*  790 */     this.showResult = bShow;
/*      */   }
/*      */   
/*      */   protected void ORPrint() {
/*  794 */     if (this.procedure == 1) {
/*  795 */       PrintSimulationEnter();
/*  796 */     } else if (this.procedure == 2) {
/*  797 */       PrintSimulationInteractive();
/*  798 */     } else if (this.procedure == 3) {
/*  799 */       PrintWaitingLine();
/*      */     }
/*      */   }
/*      */   
/*      */   private void PrintSimulationEnter() {
/*  804 */     PrintSimulationModel();
/*      */   }
/*      */   
/*      */   public void initWaitingLinePrintInfo(IORSAProcessController.SAData data)
/*      */   {
/*  809 */     this.arrivalRate = data.arrivalRate;
/*  810 */     this.serviceRate = data.serviceRate;
/*  811 */     this.ns1Num = data.ns1Num;
/*  812 */     this.ns2Num = data.ns2Num;
/*  813 */     this.phaseNum = data.phaseNum;
/*  814 */     this.lqStr = data.lqStr;
/*  815 */     this.lsStr = data.lsStr;
/*  816 */     this.wqStr = data.wqStr;
/*  817 */     this.wsStr = data.wsStr;
/*  818 */     this.isShowData = data.isShowData;
/*      */   }
/*      */   
/*      */   private void PrintWaitingLine()
/*      */   {
/*  823 */     PrintLine("Waiting Line:");
/*  824 */     SkipLine();
/*  825 */     if (this.phaseNum == 1) {
/*  826 */       PrintLine("One phase:");
/*  827 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.arrivalRate))).append("      ").append(this.serviceRate).append("       ").append(this.ns1Num))));
/*  828 */       SkipLine();
/*  829 */       if (this.isShowData)
/*  830 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.lqStr))).append("      ").append(this.lsStr).append("      ").append(this.wqStr).append("       ").append(this.wsStr))));
/*      */     } else {
/*  832 */       PrintLine("Two phases:");
/*  833 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.arrivalRate))).append("      ").append(this.serviceRate).append("       ").append(this.ns1Num).append("      ").append(this.ns2Num))));
/*  834 */       SkipLine();
/*  835 */       if (this.isShowData) {
/*  836 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.lqStr))).append("      ").append(this.lsStr).append("      ").append(this.wqStr).append("       ").append(this.wsStr))));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void PrintSimulationModel()
/*      */   {
/*  844 */     PrintLine("Simulation Model:");
/*  845 */     SkipLine();
/*  846 */     PrintLine("Number of Servers:          ".concat(String.valueOf(String.valueOf(FormatInteger(this.SimulationNumServers, 2, 0, 0)))));
/*  847 */     SkipLine();
/*  848 */     PrintLine("Number of Priority Classes: ".concat(String.valueOf(String.valueOf(FormatInteger(this.SimulationNumPriorities, 2, 0, 0)))));
/*  849 */     SkipLine();
/*  850 */     for (int i = 1; i <= this.SimulationNumPriorities; i++) {
/*  851 */       SkipLine();
/*  852 */       switch (i) {
/*      */       case 1: 
/*  854 */         switch (this.SimulationNumPriorities) {
/*      */         case 1: 
/*      */           break;
/*      */         case 2: 
/*  858 */           PrintLine("Priority Class 1 (higher priority) customers");
/*  859 */           break;
/*      */         case 3: 
/*  861 */           PrintLine("Priority Class 1 (highest priority) customers");
/*      */         }
/*      */         
/*  864 */         break;
/*      */       case 2: 
/*  866 */         if (this.SimulationNumPriorities == 2) {
/*  867 */           PrintLine("Priority Class 2 (lower priority) customers");
/*      */         } else
/*  869 */           PrintLine("Priority Class 2 customers");
/*  870 */         break;
/*      */       case 3: 
/*  872 */         PrintLine("Priority Class 3 (lowest priority) customers");
/*      */       }
/*      */       
/*      */       
/*  876 */       Print("Distr. of interarrival times:   ");
/*  877 */       switch (this.SimulationInterarrival[i].Distribution) {
/*      */       case 1: 
/*  879 */         PrintLine("Constant        Value= ".concat(String.valueOf(String.valueOf(FormatReal(this.SimulationInterarrival[i].Mean, 6, 0, 0)))));
/*  880 */         break;
/*      */       case 2: 
/*  882 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("Erlang          Mean = ").append(FormatReal(this.SimulationInterarrival[i].Mean, 6, 0, 0)).append(" k    = ").append(FormatReal(this.SimulationInterarrival[i].k, 6, 0, 0)))));
/*  883 */         break;
/*      */       case 3: 
/*  885 */         PrintLine("Exponential     Mean = ".concat(String.valueOf(String.valueOf(FormatReal(this.SimulationInterarrival[i].Mean, 6, 0, 0)))));
/*  886 */         break;
/*      */       case 4: 
/*  888 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("Translated Exp.  Min = ").append(FormatReal(this.SimulationInterarrival[i].MinValue, 6, 0, 0)).append(" Mean = ").append(FormatReal(this.SimulationInterarrival[i].Mean, 6, 0, 0)))));
/*  889 */         break;
/*      */       case 5: 
/*  891 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("Uniform          Min = ").append(FormatReal(this.SimulationInterarrival[i].MinValue, 6, 0, 0)).append(" Max  = ").append(FormatReal(this.SimulationInterarrival[i].MaxValue, 6, 0, 0)))));
/*      */       }
/*      */       
/*      */       
/*  895 */       Print("Distr. of service times:        ");
/*  896 */       switch (this.SimulationService[i].Distribution) {
/*      */       case 1: 
/*  898 */         PrintLine("Constant        Value= ".concat(String.valueOf(String.valueOf(FormatReal(this.SimulationService[i].Mean, 6, 0, 0)))));
/*  899 */         break;
/*      */       case 2: 
/*  901 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("Erlang          Mean = ").append(FormatReal(this.SimulationService[i].Mean, 6, 0, 0)).append(" k    = ").append(FormatReal(this.SimulationService[i].k, 6, 0, 0)))));
/*  902 */         break;
/*      */       case 3: 
/*  904 */         PrintLine("Exponential     Mean = ".concat(String.valueOf(String.valueOf(FormatReal(this.SimulationService[i].Mean, 6, 0, 0)))));
/*  905 */         break;
/*      */       case 4: 
/*  907 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("Translated Exp.  Min = ").append(FormatReal(this.SimulationService[i].MinValue, 6, 0, 0)).append(" Mean = ").append(FormatReal(this.SimulationService[i].Mean, 6, 0, 0)))));
/*  908 */         break;
/*      */       case 5: 
/*  910 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("Uniform          Min = ").append(FormatReal(this.SimulationService[i].MinValue, 6, 0, 0)).append(" Max  = ").append(FormatReal(this.SimulationService[i].MaxValue, 6, 0, 0)))));
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void PrintSimulationTableHeading()
/*      */   {
/*  919 */     int Problem = this.SimulationNumPriorities * 10 + this.SimulationNumServers;
/*  920 */     switch (Problem) {
/*      */     case 11: 
/*  922 */       PrintLine("       |    Number of    |                  |                |");
/*  923 */       PrintLine("Current|    Customers    |    Customer      |     Next       |  Next Service");
/*  924 */       PrintLine(" Time  |    in Queue     |  Being Served    |    Arrival     |   Completion");
/*  925 */       PrintLine("_______|_________________|__________________|________________|_________________");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  946 */       break;
/*      */     case 21: 
/*  928 */       PrintLine("       | # of Customers  |                  |     Next       |");
/*  929 */       PrintLine("Current|    in Queue     |Class of Customer |    Arrival     |  Next Service");
/*  930 */       PrintLine(" Time  | Class 1 Class 2 |  Being Served    |Class 1 Class 2 |   Completion");
/*  931 */       PrintLine("_______|_________________|__________________|________________|_________________");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  946 */       break;
/*      */     case 12: 
/*  934 */       PrintLine("       |    Number of    |    Customer      |                |  Next Service");
/*  935 */       PrintLine("Current|    Customers    |  Being Served    |     Next       |   Completion");
/*  936 */       PrintLine(" Time  |    in Queue     |Server 1 Server 2 |    Arrival     |Server 1 Server 2");
/*  937 */       PrintLine("_______|_________________|__________________|________________|_________________");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  946 */       break;
/*      */     case 22: 
/*  940 */       PrintLine("       | # of Customers  |Class of Customer |     Next       |  Next Service");
/*  941 */       PrintLine("Current|    in Queue     |  Being Served    |    Arrival     |   Completion");
/*  942 */       PrintLine(" Time  | Class 1 Class 2 |Server 1 Server 2 |Class 1 Class 2 |Server 1 Server 2");
/*  943 */       PrintLine("_______|_________________|__________________|________________|_________________");
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */   private void PrintASimulationLine(ORSolverBase.InteractiveSimulation TheSimulation)
/*      */   {
/*  951 */     int Problem = this.SimulationNumPriorities * 10 + this.SimulationNumServers;
/*  952 */     if (TheSimulation != null)
/*      */     {
/*  954 */       Print(String.valueOf(String.valueOf(FormatReal(TheSimulation.CurrentTime, 7, 0, 1))).concat("|"));
/*  955 */       if (this.SimulationNumPriorities == 1) {
/*  956 */         TabPrint(15, FormatInteger(TheSimulation.NumberOfCustomers[1], 3, 0, 1));
/*      */       } else {
/*  958 */         TabPrint(11, FormatInteger(TheSimulation.NumberOfCustomers[1], 3, 0, 1));
/*  959 */         TabPrint(19, FormatInteger(TheSimulation.NumberOfCustomers[2], 3, 0, 1));
/*      */       }
/*  961 */       TabPrint(26, "|");
/*  962 */       switch (Problem) {
/*      */       case 11: 
/*  964 */         if (TheSimulation.ClassBeingServed[1] == 1) {
/*  965 */           TabPrint(34, "Yes");
/*      */         } else
/*  967 */           TabPrint(34, " No");
/*  968 */         break;
/*      */       case 12: 
/*  970 */         if (TheSimulation.ClassBeingServed[1] == 1) {
/*  971 */           TabPrint(29, "Yes");
/*      */         } else
/*  973 */           TabPrint(29, " No");
/*  974 */         if (TheSimulation.ClassBeingServed[2] == 1) {
/*  975 */           TabPrint(38, "Yes");
/*      */         } else
/*  977 */           TabPrint(38, " No");
/*  978 */         break;
/*      */       case 21: 
/*  980 */         if (TheSimulation.ClassBeingServed[1] > 0) {
/*  981 */           TabPrint(34, FormatInteger(TheSimulation.ClassBeingServed[1], 2, 0, 1));
/*      */         } else
/*  983 */           TabPrint(34, "idle");
/*  984 */         break;
/*      */       case 22: 
/*  986 */         if (TheSimulation.ClassBeingServed[1] > 0) {
/*  987 */           TabPrint(29, FormatInteger(TheSimulation.ClassBeingServed[1], 2, 0, 1));
/*      */         } else
/*  989 */           TabPrint(29, "idle");
/*  990 */         if (TheSimulation.ClassBeingServed[2] > 0) {
/*  991 */           TabPrint(38, FormatInteger(TheSimulation.ClassBeingServed[2], 2, 0, 1));
/*      */         } else
/*  993 */           TabPrint(38, "idle");
/*      */         break;
/*      */       }
/*  996 */       TabPrint(45, "|");
/*  997 */       if (this.SimulationNumPriorities == 1) {
/*  998 */         if (TheSimulation.NextArrival[1] < 0) {
/*  999 */           TabPrint(52, "---");
/*      */         } else {
/* 1001 */           TabPrint(50, FormatReal(TheSimulation.NextArrival[1], 7, 0, 1));
/*      */         }
/*      */       } else {
/* 1004 */         if (TheSimulation.NextArrival[1] < 0) {
/* 1005 */           TabPrint(48, "---");
/*      */         } else
/* 1007 */           TabPrint(46, FormatReal(TheSimulation.NextArrival[1], 7, 0, 1));
/* 1008 */         if (TheSimulation.NextArrival[2] < 0) {
/* 1009 */           TabPrint(56, "---");
/*      */         } else
/* 1011 */           TabPrint(54, FormatReal(TheSimulation.NextArrival[2], 7, 0, 1));
/*      */       }
/* 1013 */       TabPrint(62, "|");
/* 1014 */       if (this.SimulationNumServers == 1) {
/* 1015 */         if (TheSimulation.NextService[1] < 0) {
/* 1016 */           TabPrint(70, "---");
/*      */         } else {
/* 1018 */           TabPrint(68, FormatReal(TheSimulation.NextService[1], 7, 0, 1));
/*      */         }
/*      */       } else {
/* 1021 */         if (TheSimulation.NextService[1] < 0) {
/* 1022 */           TabPrint(65, "---");
/*      */         } else
/* 1024 */           TabPrint(63, FormatReal(TheSimulation.NextService[1], 7, 0, 1));
/* 1025 */         if (TheSimulation.NextService[2] < 0) {
/* 1026 */           TabPrint(74, "---");
/*      */         } else
/* 1028 */           TabPrint(72, FormatReal(TheSimulation.NextService[2], 7, 0, 1));
/*      */       }
/*      */     }
/* 1031 */     SkipLine();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void PrintSimulationInteractive()
/*      */   {
/* 1039 */     PrintSimulationModel();
/* 1040 */     boolean First = true;
/* 1041 */     Skip(2);
/* 1042 */     ORSolverBase.InteractiveSimulation Table = this.FirstSimulation;
/*      */     do {
/* 1044 */       if ((this.LineNumber <= 3) || (First)) {
/* 1045 */         PrintLines(3);
/* 1046 */         PrintSimulationTableHeading();
/*      */       }
/* 1048 */       PrintASimulationLine(Table);
/* 1049 */       PrintLines(1);
/* 1050 */       First = false;
/* 1051 */       Table = Table.Next;
/* 1052 */     } while (Table != null);
/* 1053 */     Skip(3);
/*      */     
/* 1055 */     if (this.showResult) {
/* 1056 */       PrintLines(15);
/* 1057 */       PrintLine("The results from the simulation run are as follows:");
/* 1058 */       SkipLine();
/* 1059 */       for (int i = 1; i <= this.SimulationNumPriorities; i++) {
/* 1060 */         PrintLines(13);
/* 1061 */         Table = this.FirstSimulation;
/* 1062 */         First = true;
/* 1063 */         while (Table.Next != null) {
/* 1064 */           if ((this.LineNumber == 1) || (First)) {
/* 1065 */             if (First) {
/* 1066 */               if (this.SimulationNumPriorities > 1)
/* 1067 */                 Print(String.valueOf(String.valueOf(new StringBuffer("Class ").append(FormatInteger(i, 1, 0, 0)).append(" customers:"))));
/* 1068 */               Skip(2);
/* 1069 */               PrintLine("Average number waiting to begin service:        ".concat(String.valueOf(String.valueOf(FormatReal(this.SimulationData.Lq[i].PointEstimate, 8, 0, 0)))));
/* 1070 */               SkipLine();
/* 1071 */               PrintLine("Average number waiting or in service:           ".concat(String.valueOf(String.valueOf(FormatReal(this.SimulationData.L[i].PointEstimate, 8, 0, 0)))));
/* 1072 */               SkipLine();
/*      */             }
/* 1074 */             TabPrintLine(20, "Waiting Times");
/* 1075 */             TabPrintLine(16, "Excluding | Including");
/* 1076 */             TabPrintLine(17, "Service  | Service");
/* 1077 */             if ((First) && (i == 1)) {
/* 1078 */               TabPrint(17, FormatReal(0.0D, 7, 0, 1));
/* 1079 */               TabPrint(26, "|");
/* 1080 */               TabPrintLine(29, FormatReal(Table.NextService[1], 7, 0, 1));
/*      */             }
/*      */           }
/* 1083 */           First = false;
/* 1084 */           if ((Table.Answer2 <= 2) && (Table.Answer3 >= 2)) {
/* 1085 */             int WhichClass = Table.Answer2;
/* 1086 */             int WhichServer = Table.Answer3 - 1;
/* 1087 */             if (WhichClass == i) {
/* 1088 */               TabPrint(17, FormatReal(Table.CurrentTime - Table.WhenArrived, 7, 0, 1));
/* 1089 */               TabPrint(26, "|");
/* 1090 */               TabPrintLine(29, FormatReal(Table.NextService[WhichServer] - Table.WhenArrived, 7, 0, 1));
/* 1091 */               PrintLines(1);
/*      */             }
/*      */           }
/* 1094 */           else if ((Table.Answer2 >= 3) && (Table.Answer4 >= 2)) {
/* 1095 */             int WhichServer = Table.Answer2 - 2;
/* 1096 */             int WhichClass = Table.Answer4 - 1;
/* 1097 */             if (WhichClass == i) {
/* 1098 */               TabPrint(17, FormatReal(Table.CurrentTime - Table.WhenArrived, 7, 0, 1));
/* 1099 */               TabPrint(26, "|");
/* 1100 */               TabPrintLine(29, FormatReal(Table.NextService[WhichServer] - Table.WhenArrived, 7, 0, 1));
/* 1101 */               PrintLines(1);
/*      */             }
/*      */           }
/* 1104 */           Table = Table.Next;
/*      */         }
/* 1106 */         TabPrintLine(26, "|");
/* 1107 */         TabPrintLine(8, String.valueOf(String.valueOf(new StringBuffer("Average: ").append(FormatReal(this.SimulationData.Wq[i].PointEstimate, 7, 0, 1)).append("  |  ").append(FormatReal(this.SimulationData.W[i].PointEstimate, 7, 0, 1)))));
/* 1108 */         Skip(2);
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORSASolver.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */