/*      */ package ior;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import org.coinor.opents.AspirationCriteria;
/*      */ import org.coinor.opents.Move;
/*      */ import org.coinor.opents.MoveManager;
/*      */ import org.coinor.opents.NoCurrentSolutionException;
/*      */ import org.coinor.opents.NoMovesGeneratedException;
/*      */ import org.coinor.opents.ObjectiveFunction;
/*      */ import org.coinor.opents.Solution;
/*      */ import org.coinor.opents.TabuList;
/*      */ import org.coinor.opents.TabuSearch;
/*      */ import org.coinor.opents.TabuSearchBase;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TSPTabuSearch
/*      */   extends TabuSearchBase
/*      */ {
/*      */   protected ObjectiveFunction objectiveFunction;
/*      */   protected MoveManager moveManager;
/*      */   protected TabuList tabuList;
/*      */   protected AspirationCriteria aspirationCriteria;
/*      */   protected Solution currentSolution;
/*      */   protected Solution bestSolution;
/*      */   protected int iterationsToGo;
/*      */   protected boolean maximizing;
/*      */   protected boolean solving;
/*      */   protected boolean keepSolving;
/*      */   protected boolean fireNewCurrentSolution;
/*      */   protected boolean fireNewBestSolution;
/*      */   protected boolean fireUnimprovingMoveMade;
/*      */   protected boolean fireImprovingMoveMade;
/*      */   protected boolean fireNoChangeInValueMoveMade;
/*   79 */   protected boolean chooseFirstImprovingMove = false;
/*      */   
/*      */ 
/*   82 */   protected static PrintStream err = System.err;
/*      */   
/*   84 */   protected boolean searchEnd = true;
/*      */   protected double bestValue;
/*   86 */   protected int stopRule = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TSPTabuSearch() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasMoreSearch()
/*      */   {
/*  100 */     return this.searchEnd;
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
/*      */   public TSPTabuSearch(Solution initialSolution, MoveManager moveManager, ObjectiveFunction objectiveFunction, TabuList tabuList, AspirationCriteria aspirationCriteria, boolean maximizing)
/*      */   {
/*  131 */     this();
/*      */     
/*      */ 
/*  134 */     double[] val = objectiveFunction.evaluate(initialSolution, null);
/*  135 */     initialSolution.setObjectiveValue(val);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  141 */     this.currentSolution = initialSolution;
/*  142 */     this.bestSolution = ((Solution)initialSolution.clone());
/*      */     
/*      */ 
/*  145 */     this.objectiveFunction = objectiveFunction;
/*  146 */     this.moveManager = moveManager;
/*  147 */     this.tabuList = tabuList;
/*  148 */     this.aspirationCriteria = aspirationCriteria;
/*  149 */     this.maximizing = maximizing;
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
/*      */   protected void performOneIteration()
/*      */     throws NoCurrentSolutionException, NoMovesGeneratedException
/*      */   {
/*  181 */     TabuList tabuList = getTabuList();
/*  182 */     MoveManager moveManager = getMoveManager();
/*  183 */     ObjectiveFunction objectiveFunction = getObjectiveFunction();
/*  184 */     AspirationCriteria aspirationCriteria = getAspirationCriteria();
/*  185 */     Solution currentSolution = getCurrentSolution();
/*  186 */     Solution bestSolution = getBestSolution();
/*  187 */     boolean chooseFirstImproving = isChooseFirstImprovingMove();
/*  188 */     boolean maximizing = isMaximizing();
/*      */     
/*      */ 
/*  191 */     if (currentSolution == null) {
/*  192 */       throw new NoCurrentSolutionException();
/*      */     }
/*      */     
/*  195 */     if (bestSolution == null) {
/*  196 */       bestSolution = (Solution)currentSolution.clone();
/*  197 */       internalSetBestSolution(bestSolution);
/*      */     }
/*      */     
/*      */ 
/*  201 */     this.fireNewCurrentSolution = false;
/*  202 */     this.fireNewBestSolution = false;
/*  203 */     this.fireUnimprovingMoveMade = false;
/*  204 */     this.fireImprovingMoveMade = false;
/*  205 */     this.fireNoChangeInValueMoveMade = false;
/*      */     
/*      */ 
/*  208 */     Move[] moves = moveManager.getAllMoves(currentSolution);
/*  209 */     if ((moves == null) || (moves.length == 0)) {
/*  210 */       throw new NoMovesGeneratedException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  218 */     Object[] bestMoveArr = getBestMove(currentSolution, moves, objectiveFunction, tabuList, aspirationCriteria, maximizing, chooseFirstImproving);
/*      */     
/*      */ 
/*  221 */     Move bestMove = (Move)bestMoveArr[0];
/*  222 */     double[] bestMoveVal = (double[])bestMoveArr[1];
/*  223 */     if (bestMoveVal[0] > Integer.MAX_VALUE) {
/*  224 */       this.searchEnd = false;
/*  225 */       return;
/*      */     }
/*  227 */     boolean bestMoveTabu = ((Boolean)bestMoveArr[2]).booleanValue();
/*      */     
/*      */ 
/*  230 */     tabuList.setTabu(currentSolution, bestMove);
/*      */     
/*      */ 
/*  233 */     double[] oldVal = currentSolution.getObjectiveValue();
/*      */     
/*      */ 
/*  236 */     if (isFirstBetterThanSecond(oldVal, bestMoveVal, maximizing)) {
/*  237 */       this.fireUnimprovingMoveMade = true;
/*      */     }
/*  239 */     else if (isFirstBetterThanSecond(bestMoveVal, oldVal, maximizing)) {
/*  240 */       this.fireImprovingMoveMade = true;
/*      */     }
/*      */     else {
/*  243 */       this.fireNoChangeInValueMoveMade = true;
/*      */     }
/*      */     
/*      */ 
/*  247 */     boolean newBestSoln = false;
/*  248 */     if ((this.fireImprovingMoveMade) && 
/*  249 */       (isFirstBetterThanSecond(bestMoveVal, bestSolution.getObjectiveValue(), maximizing)))
/*      */     {
/*      */ 
/*  252 */       newBestSoln = true;
/*  253 */       this.stopRule = 0;
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  258 */       bestMove.operateOn(currentSolution);
/*      */     }
/*      */     catch (Exception e) {
/*  261 */       System.err.println(String.valueOf(String.valueOf(new StringBuffer("Error with ").append(bestMove).append(" on ").append(currentSolution))));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  267 */     currentSolution.setObjectiveValue((double[])bestMoveVal.clone());
/*      */     
/*      */ 
/*  270 */     if (newBestSoln) {
/*  271 */       Solution newBest = (Solution)currentSolution.clone();
/*  272 */       internalSetBestSolution(newBest);
/*      */     } else {
/*  274 */       this.stopRule += 1;
/*      */     }
/*      */     
/*  277 */     if (this.stopRule > 3) { this.searchEnd = false;return;
/*      */     }
/*      */     
/*  280 */     internalSetCurrentSolution(currentSolution);
/*      */     
/*      */ 
/*  283 */     fireQueuedEvents();
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
/*      */   protected Object[] getBestMove(Solution soln, Move[] moves, ObjectiveFunction objectiveFunction, TabuList tabuList, AspirationCriteria aspirationCriteria, boolean maximizing, boolean chooseFirstImprovingMove)
/*      */   {
/*  308 */     return getBestMove(soln, moves, objectiveFunction, tabuList, aspirationCriteria, maximizing, chooseFirstImprovingMove, this);
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
/*      */   protected static Object[] getBestMove(Solution soln, Move[] moves, ObjectiveFunction objectiveFunction, TabuList tabuList, AspirationCriteria aspirationCriteria, boolean maximizing, boolean chooseFirstImprovingMove, TabuSearch This)
/*      */   {
/*  330 */     Move bestMove = moves[0];
/*  331 */     double[] bestMoveVal = new double[0];
/*  332 */     boolean bestMoveTabu = false;
/*      */     
/*      */ 
/*  335 */     bestMoveVal = objectiveFunction.evaluate(soln, bestMove);
/*  336 */     bestMoveTabu = isTabu(soln, bestMove, bestMoveVal, tabuList, aspirationCriteria, This);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  344 */     double[] currSolnVal = null;
/*  345 */     if (chooseFirstImprovingMove) {
/*  346 */       currSolnVal = soln.getObjectiveValue();
/*  347 */       if ((!bestMoveTabu) && (isFirstBetterThanSecond(bestMoveVal, currSolnVal, maximizing)))
/*      */       {
/*  349 */         return new Object[] { bestMove, bestMoveVal, new Boolean(bestMoveTabu) };
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  355 */     int movesLen = moves.length;
/*  356 */     for (int i = 1; i < movesLen; i++)
/*      */     {
/*  358 */       for (i = 1; i < moves.length; i++) {
/*  359 */         Move move = moves[i];
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  365 */         double[] newObjVal = objectiveFunction.evaluate(soln, move);
/*  366 */         if (isFirstBetterThanSecond(newObjVal, bestMoveVal, maximizing))
/*      */         {
/*      */ 
/*      */ 
/*  370 */           boolean newIsTabu = isTabu(soln, move, newObjVal, tabuList, aspirationCriteria, This);
/*      */           
/*      */ 
/*  373 */           if ((bestMoveTabu) || (!newIsTabu)) {
/*  374 */             bestMove = move;
/*  375 */             bestMoveVal = newObjVal;
/*  376 */             bestMoveTabu = newIsTabu;
/*      */             
/*      */ 
/*  379 */             if ((chooseFirstImprovingMove) && 
/*  380 */               (!bestMoveTabu) && (isFirstBetterThanSecond(bestMoveVal, currSolnVal, maximizing)))
/*      */             {
/*      */ 
/*  383 */               return new Object[] { bestMove, bestMoveVal, new Boolean(bestMoveTabu) };
/*      */ 
/*      */ 
/*      */             }
/*      */             
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */         }
/*  394 */         else if ((bestMoveTabu) && (!isTabu(soln, move, newObjVal, tabuList, aspirationCriteria, This)))
/*      */         {
/*      */ 
/*  397 */           bestMove = move;
/*  398 */           bestMoveVal = newObjVal;
/*  399 */           bestMoveTabu = false;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  406 */     return new Object[] { bestMove, bestMoveVal, new Boolean(bestMoveTabu) };
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
/*      */   protected static boolean isTabu(Solution soln, Move move, double[] val, TabuList tabuList, AspirationCriteria aspirationCriteria, TabuSearch This)
/*      */   {
/*  423 */     boolean tabu = false;
/*      */     
/*      */ 
/*  426 */     if (tabuList.isTabu(soln, move)) {
/*  427 */       tabu = true;
/*  428 */       if (aspirationCriteria != null)
/*      */       {
/*      */ 
/*  431 */         if (aspirationCriteria.overrideTabu(soln, move, val, This)) {
/*  432 */           tabu = false;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  437 */     return tabu;
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
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public static boolean firstIsBetterThanSecond(double[] first, double[] second, boolean maximizing)
/*      */   {
/*  455 */     return isFirstBetterThanSecond(first, second, maximizing);
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
/*      */   public static boolean isFirstBetterThanSecond(double[] first, double[] second, boolean maximizing)
/*      */   {
/*  474 */     int i = 0;
/*  475 */     int valLength = first.length;
/*      */     
/*      */ 
/*  478 */     for (i = 0; i < valLength; i++) {
/*  479 */       float first_f = (float)first[i];
/*  480 */       float second_f = (float)second[i];
/*      */       
/*  482 */       if (first_f > second_f) {
/*  483 */         return maximizing;
/*      */       }
/*      */       
/*  486 */       if (first_f < second_f) {
/*  487 */         return !maximizing;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  493 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void fireQueuedEvents()
/*      */   {
/*  502 */     if (this.fireNewCurrentSolution) {
/*  503 */       this.fireNewCurrentSolution = false;
/*  504 */       fireNewCurrentSolution();
/*      */     }
/*      */     
/*  507 */     if (this.fireNewBestSolution) {
/*  508 */       this.fireNewBestSolution = false;
/*  509 */       fireNewBestSolution();
/*      */     }
/*      */     
/*  512 */     if (this.fireUnimprovingMoveMade) {
/*  513 */       this.fireUnimprovingMoveMade = false;
/*  514 */       fireUnimprovingMoveMade();
/*      */     }
/*  516 */     else if (this.fireImprovingMoveMade) {
/*  517 */       this.fireImprovingMoveMade = false;
/*  518 */       fireImprovingMoveMade();
/*      */     }
/*      */     else {
/*  521 */       this.fireNoChangeInValueMoveMade = false;
/*  522 */       fireNoChangeInValueMoveMade();
/*      */     }
/*      */     
/*      */ 
/*  526 */     if ((this.fireNewCurrentSolution) || (this.fireNewBestSolution) || (this.fireUnimprovingMoveMade) || (this.fireImprovingMoveMade) || (this.fireNoChangeInValueMoveMade))
/*      */     {
/*      */ 
/*  529 */       fireQueuedEvents();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void internalSetCurrentSolution(Solution solution)
/*      */   {
/*  540 */     this.currentSolution = solution;
/*  541 */     this.fireNewCurrentSolution = true;
/*  542 */     if (getCurrentSolution() == null) {
/*  543 */       internalSetCurrentSolution((Solution)solution.clone());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void internalSetBestSolution(Solution solution)
/*      */   {
/*  554 */     this.bestSolution = solution;
/*  555 */     this.fireNewBestSolution = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setSolving(boolean solving)
/*      */   {
/*  566 */     this.solving = solving;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setKeepSolving(boolean keepSolving)
/*      */   {
/*  577 */     this.keepSolving = keepSolving;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean isKeepSolving()
/*      */   {
/*  588 */     return this.keepSolving;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setFireNewCurrentSolution(boolean b)
/*      */   {
/*  599 */     this.fireNewCurrentSolution = b;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setFireNewBestSolution(boolean b)
/*      */   {
/*  610 */     this.fireNewBestSolution = b;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setFireUnimprovingMoveMade(boolean b)
/*      */   {
/*  621 */     this.fireUnimprovingMoveMade = b;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setFireImprovingMoveMade(boolean b)
/*      */   {
/*  632 */     this.fireImprovingMoveMade = b;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setFireNoChangeInValueMoveMade(boolean b)
/*      */   {
/*  643 */     this.fireNoChangeInValueMoveMade = b;
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
/*      */   protected boolean isFireNewCurrentSolution()
/*      */   {
/*  656 */     return this.fireNewCurrentSolution;
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
/*      */   protected boolean isFireNewBestSolution()
/*      */   {
/*  669 */     return this.fireNewBestSolution;
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
/*      */   protected boolean isFireUnimprovingMoveMade()
/*      */   {
/*  682 */     return this.fireUnimprovingMoveMade;
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
/*      */   protected boolean isFireImprovingMoveMade()
/*      */   {
/*  695 */     return this.fireImprovingMoveMade;
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
/*      */   protected boolean isFireNoChangeInValueMoveMade()
/*      */   {
/*  708 */     return this.fireNoChangeInValueMoveMade;
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
/*      */   public void startSolving()
/*      */   {
/*  721 */     setKeepSolving(true);
/*      */     
/*  723 */     setSolving(true);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  730 */     if (this.iterationsToGo > 0) {
/*  731 */       fireTabuSearchStarted();
/*      */     }
/*      */     
/*      */ 
/*  735 */     while ((this.keepSolving) && (this.iterationsToGo > 0)) {
/*  736 */       Thread.yield();
/*  737 */       synchronized (this) {
/*  738 */         this.iterationsToGo -= 1;
/*      */         try
/*      */         {
/*  741 */           performOneIteration();
/*      */         }
/*      */         catch (NoMovesGeneratedException e) {
/*  744 */           if (err != null) {
/*  745 */             err.println(e);
/*      */           }
/*      */         }
/*      */         catch (NoCurrentSolutionException e) {
/*  749 */           if (err != null) {
/*  750 */             err.println(e);
/*      */           }
/*      */         }
/*  753 */         incrementIterationsCompleted();
/*      */       }
/*      */     }
/*      */     
/*  757 */     setSolving(false);
/*  758 */     fireTabuSearchStopped();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void stopSolving()
/*      */   {
/*  769 */     setKeepSolving(false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean isSolving()
/*      */   {
/*  778 */     return this.solving;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void setObjectiveFunction(ObjectiveFunction function)
/*      */   {
/*  790 */     this.objectiveFunction = function;
/*      */     
/*  792 */     if (this.currentSolution != null) {
/*  793 */       this.currentSolution.setObjectiveValue(function.evaluate(this.currentSolution, null));
/*      */     }
/*      */     
/*      */ 
/*  797 */     if (this.bestSolution != null) {
/*  798 */       this.bestSolution.setObjectiveValue(function.evaluate(this.bestSolution, null));
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
/*      */   public synchronized void setMoveManager(MoveManager moveManager)
/*      */   {
/*  812 */     this.moveManager = moveManager;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void setTabuList(TabuList tabuList)
/*      */   {
/*  823 */     this.tabuList = tabuList;
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
/*      */   public synchronized void setAspirationCriteria(AspirationCriteria aspirationCriteria)
/*      */   {
/*  836 */     this.aspirationCriteria = aspirationCriteria;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void setBestSolution(Solution solution)
/*      */   {
/*  848 */     internalSetBestSolution(solution);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void setCurrentSolution(Solution solution)
/*      */   {
/*  860 */     internalSetCurrentSolution(solution);
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
/*      */   public synchronized void setIterationsToGo(int iterations)
/*      */   {
/*  876 */     this.iterationsToGo = iterations;
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
/*      */   public synchronized void setMaximizing(boolean maximizing)
/*      */   {
/*  889 */     this.maximizing = maximizing;
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
/*      */   public synchronized void setChooseFirstImprovingMove(boolean choose)
/*      */   {
/*  905 */     this.chooseFirstImprovingMove = choose;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized ObjectiveFunction getObjectiveFunction()
/*      */   {
/*  916 */     return this.objectiveFunction;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized MoveManager getMoveManager()
/*      */   {
/*  927 */     return this.moveManager;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized TabuList getTabuList()
/*      */   {
/*  938 */     return this.tabuList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized AspirationCriteria getAspirationCriteria()
/*      */   {
/*  950 */     return this.aspirationCriteria;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Solution getBestSolution()
/*      */   {
/*  962 */     return this.bestSolution;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Solution getCurrentSolution()
/*      */   {
/*  974 */     return this.currentSolution;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized int getIterationsToGo()
/*      */   {
/*  984 */     return this.iterationsToGo;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean isMaximizing()
/*      */   {
/*  994 */     return this.maximizing;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean isChooseFirstImprovingMove()
/*      */   {
/* 1005 */     return this.chooseFirstImprovingMove;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ior\TSPTabuSearch.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */