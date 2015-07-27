/*      */ package org.coinor.opents;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SingleThreadedTabuSearch
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
/*   80 */   protected boolean chooseFirstImprovingMove = false;
/*      */   
/*      */ 
/*   83 */   protected static PrintStream err = System.err;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public SingleThreadedTabuSearch() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public SingleThreadedTabuSearch(Solution initialSolution, MoveManager moveManager, ObjectiveFunction objectiveFunction, TabuList tabuList, AspirationCriteria aspirationCriteria, boolean maximizing)
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
/*      */ 
/*      */   protected void performOneIteration()
/*      */     throws NoCurrentSolutionException, NoMovesGeneratedException
/*      */   {
/*  182 */     TabuList tabuList = getTabuList();
/*  183 */     MoveManager moveManager = getMoveManager();
/*  184 */     ObjectiveFunction objectiveFunction = getObjectiveFunction();
/*  185 */     AspirationCriteria aspirationCriteria = getAspirationCriteria();
/*  186 */     Solution currentSolution = getCurrentSolution();
/*  187 */     Solution bestSolution = getBestSolution();
/*  188 */     boolean chooseFirstImproving = isChooseFirstImprovingMove();
/*  189 */     boolean maximizing = isMaximizing();
/*      */     
/*      */ 
/*  192 */     if (currentSolution == null) {
/*  193 */       throw new NoCurrentSolutionException();
/*      */     }
/*  195 */     if (bestSolution == null)
/*      */     {
/*  197 */       bestSolution = (Solution)currentSolution.clone();
/*  198 */       internalSetBestSolution(bestSolution);
/*      */     }
/*      */     
/*      */ 
/*  202 */     this.fireNewCurrentSolution = false;
/*  203 */     this.fireNewBestSolution = false;
/*  204 */     this.fireUnimprovingMoveMade = false;
/*  205 */     this.fireImprovingMoveMade = false;
/*  206 */     this.fireNoChangeInValueMoveMade = false;
/*      */     
/*      */ 
/*  209 */     Move[] moves = moveManager.getAllMoves(currentSolution);
/*  210 */     if ((moves == null) || (moves.length == 0)) {
/*  211 */       throw new NoMovesGeneratedException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  219 */     Object[] bestMoveArr = getBestMove(currentSolution, moves, objectiveFunction, tabuList, aspirationCriteria, maximizing, chooseFirstImproving);
/*      */     
/*      */ 
/*  222 */     Move bestMove = (Move)bestMoveArr[0];
/*  223 */     double[] bestMoveVal = (double[])bestMoveArr[1];
/*  224 */     boolean bestMoveTabu = ((Boolean)bestMoveArr[2]).booleanValue();
/*      */     
/*      */ 
/*  227 */     tabuList.setTabu(currentSolution, bestMove);
/*      */     
/*      */ 
/*      */ 
/*  231 */     double[] oldVal = currentSolution.getObjectiveValue();
/*      */     
/*      */ 
/*      */ 
/*  235 */     if (isFirstBetterThanSecond(oldVal, bestMoveVal, maximizing)) {
/*  236 */       this.fireUnimprovingMoveMade = true;
/*  237 */     } else if (isFirstBetterThanSecond(bestMoveVal, oldVal, maximizing))
/*  238 */       this.fireImprovingMoveMade = true; else {
/*  239 */       this.fireNoChangeInValueMoveMade = true;
/*      */     }
/*      */     
/*  242 */     boolean newBestSoln = false;
/*  243 */     if ((this.fireImprovingMoveMade) && 
/*  244 */       (isFirstBetterThanSecond(bestMoveVal, bestSolution.getObjectiveValue(), maximizing))) {
/*  245 */       newBestSoln = true;
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  250 */       bestMove.operateOn(currentSolution);
/*      */     } catch (Exception e) {
/*  252 */       System.err.println(String.valueOf(String.valueOf(new StringBuffer("Error with ").append(bestMove).append(" on ").append(currentSolution))));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  257 */     currentSolution.setObjectiveValue((double[])bestMoveVal.clone());
/*      */     
/*      */ 
/*      */ 
/*  261 */     if (newBestSoln) {
/*  262 */       Solution newBest = (Solution)currentSolution.clone();
/*  263 */       internalSetBestSolution(newBest);
/*      */     }
/*      */     
/*      */ 
/*  267 */     internalSetCurrentSolution(currentSolution);
/*      */     
/*      */ 
/*      */ 
/*  271 */     fireQueuedEvents();
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
/*      */   protected Object[] getBestMove(Solution soln, Move[] moves, ObjectiveFunction objectiveFunction, TabuList tabuList, AspirationCriteria aspirationCriteria, boolean maximizing, boolean chooseFirstImprovingMove)
/*      */   {
/*  298 */     return getBestMove(soln, moves, objectiveFunction, tabuList, aspirationCriteria, maximizing, chooseFirstImprovingMove, this);
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
/*      */   protected static Object[] getBestMove(Solution soln, Move[] moves, ObjectiveFunction objectiveFunction, TabuList tabuList, AspirationCriteria aspirationCriteria, boolean maximizing, boolean chooseFirstImprovingMove, TabuSearch This)
/*      */   {
/*  322 */     Move bestMove = moves[0];
/*  323 */     double[] bestMoveVal = new double[0];
/*  324 */     boolean bestMoveTabu = false;
/*      */     
/*      */ 
/*  327 */     bestMoveVal = objectiveFunction.evaluate(soln, bestMove);
/*  328 */     bestMoveTabu = isTabu(soln, bestMove, bestMoveVal, tabuList, aspirationCriteria, This);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  335 */     double[] currSolnVal = null;
/*  336 */     if (chooseFirstImprovingMove)
/*      */     {
/*  338 */       currSolnVal = soln.getObjectiveValue();
/*  339 */       if ((!bestMoveTabu) && (isFirstBetterThanSecond(bestMoveVal, currSolnVal, maximizing))) {
/*  340 */         return new Object[] { bestMove, bestMoveVal, new Boolean(bestMoveTabu) };
/*      */       }
/*      */     }
/*      */     
/*  344 */     int movesLen = moves.length;
/*  345 */     for (int i = 1; i < movesLen; i++)
/*      */     {
/*      */ 
/*  348 */       for (i = 1; i < moves.length; i++)
/*      */       {
/*  350 */         Move move = moves[i];
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  356 */         double[] newObjVal = objectiveFunction.evaluate(soln, move);
/*  357 */         if (isFirstBetterThanSecond(newObjVal, bestMoveVal, maximizing))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*  362 */           boolean newIsTabu = isTabu(soln, move, newObjVal, tabuList, aspirationCriteria, This);
/*      */           
/*  364 */           if ((bestMoveTabu) || (!newIsTabu)) {
/*  365 */             bestMove = move;
/*  366 */             bestMoveVal = newObjVal;
/*  367 */             bestMoveTabu = newIsTabu;
/*      */             
/*      */ 
/*  370 */             if ((chooseFirstImprovingMove) && 
/*  371 */               (!bestMoveTabu) && (isFirstBetterThanSecond(bestMoveVal, currSolnVal, maximizing))) {
/*  372 */               return new Object[] { bestMove, bestMoveVal, new Boolean(bestMoveTabu) };
/*      */ 
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*  380 */         else if ((bestMoveTabu) && (!isTabu(soln, move, newObjVal, tabuList, aspirationCriteria, This))) {
/*  381 */           bestMove = move;
/*  382 */           bestMoveVal = newObjVal;
/*  383 */           bestMoveTabu = false;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  390 */     return new Object[] { bestMove, bestMoveVal, new Boolean(bestMoveTabu) };
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
/*      */   protected static boolean isTabu(Solution soln, Move move, double[] val, TabuList tabuList, AspirationCriteria aspirationCriteria, TabuSearch This)
/*      */   {
/*  409 */     boolean tabu = false;
/*      */     
/*      */ 
/*  412 */     if (tabuList.isTabu(soln, move))
/*      */     {
/*  414 */       tabu = true;
/*  415 */       if (aspirationCriteria != null)
/*      */       {
/*      */ 
/*      */ 
/*  419 */         if (aspirationCriteria.overrideTabu(soln, move, val, This)) {
/*  420 */           tabu = false;
/*      */         }
/*      */       }
/*      */     }
/*  424 */     return tabu;
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
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public static boolean firstIsBetterThanSecond(double[] first, double[] second, boolean maximizing)
/*      */   {
/*  444 */     return isFirstBetterThanSecond(first, second, maximizing);
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
/*      */   public static boolean isFirstBetterThanSecond(double[] first, double[] second, boolean maximizing)
/*      */   {
/*  466 */     int i = 0;
/*  467 */     int valLength = first.length;
/*      */     
/*      */ 
/*  470 */     for (i = 0; i < valLength; i++)
/*      */     {
/*  472 */       float first_f = (float)first[i];
/*  473 */       float second_f = (float)second[i];
/*      */       
/*  475 */       if (first_f > second_f) {
/*  476 */         return maximizing;
/*      */       }
/*  478 */       if (first_f < second_f) {
/*  479 */         return !maximizing;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  484 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void fireQueuedEvents()
/*      */   {
/*  496 */     if (this.fireNewCurrentSolution)
/*      */     {
/*  498 */       this.fireNewCurrentSolution = false;
/*  499 */       fireNewCurrentSolution();
/*      */     }
/*      */     
/*  502 */     if (this.fireNewBestSolution)
/*      */     {
/*  504 */       this.fireNewBestSolution = false;
/*  505 */       fireNewBestSolution();
/*      */     }
/*      */     
/*  508 */     if (this.fireUnimprovingMoveMade)
/*      */     {
/*  510 */       this.fireUnimprovingMoveMade = false;
/*  511 */       fireUnimprovingMoveMade();
/*      */     }
/*  513 */     else if (this.fireImprovingMoveMade)
/*      */     {
/*  515 */       this.fireImprovingMoveMade = false;
/*  516 */       fireImprovingMoveMade();
/*      */     }
/*      */     else
/*      */     {
/*  520 */       this.fireNoChangeInValueMoveMade = false;
/*  521 */       fireNoChangeInValueMoveMade();
/*      */     }
/*      */     
/*      */ 
/*  525 */     if ((this.fireNewCurrentSolution) || (this.fireNewBestSolution) || (this.fireUnimprovingMoveMade) || (this.fireImprovingMoveMade) || (this.fireNoChangeInValueMoveMade))
/*      */     {
/*      */ 
/*  528 */       fireQueuedEvents();
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
/*      */   protected void internalSetCurrentSolution(Solution solution)
/*      */   {
/*  542 */     this.currentSolution = solution;
/*  543 */     this.fireNewCurrentSolution = true;
/*  544 */     if (getCurrentSolution() == null) {
/*  545 */       internalSetCurrentSolution((Solution)solution.clone());
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
/*      */   protected void internalSetBestSolution(Solution solution)
/*      */   {
/*  559 */     this.bestSolution = solution;
/*  560 */     this.fireNewBestSolution = true;
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
/*      */   protected void setSolving(boolean solving)
/*      */   {
/*  573 */     this.solving = solving;
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
/*      */   protected void setKeepSolving(boolean keepSolving)
/*      */   {
/*  586 */     this.keepSolving = keepSolving;
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
/*      */   protected boolean isKeepSolving()
/*      */   {
/*  600 */     return this.keepSolving;
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
/*      */   protected void setFireNewCurrentSolution(boolean b)
/*      */   {
/*  615 */     this.fireNewCurrentSolution = b;
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
/*      */   protected void setFireNewBestSolution(boolean b)
/*      */   {
/*  628 */     this.fireNewBestSolution = b;
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
/*      */   protected void setFireUnimprovingMoveMade(boolean b)
/*      */   {
/*  641 */     this.fireUnimprovingMoveMade = b;
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
/*      */   protected void setFireImprovingMoveMade(boolean b)
/*      */   {
/*  654 */     this.fireImprovingMoveMade = b;
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
/*      */   protected void setFireNoChangeInValueMoveMade(boolean b)
/*      */   {
/*  667 */     this.fireNoChangeInValueMoveMade = b;
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
/*      */   protected boolean isFireNewCurrentSolution()
/*      */   {
/*  681 */     return this.fireNewCurrentSolution;
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
/*      */   protected boolean isFireNewBestSolution()
/*      */   {
/*  696 */     return this.fireNewBestSolution;
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
/*      */   protected boolean isFireUnimprovingMoveMade()
/*      */   {
/*  711 */     return this.fireUnimprovingMoveMade;
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
/*      */   protected boolean isFireImprovingMoveMade()
/*      */   {
/*  725 */     return this.fireImprovingMoveMade;
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
/*      */   protected boolean isFireNoChangeInValueMoveMade()
/*      */   {
/*  740 */     return this.fireNoChangeInValueMoveMade;
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
/*      */   public void startSolving()
/*      */   {
/*  754 */     setKeepSolving(true);
/*      */     
/*  756 */     setSolving(true);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  763 */     if (this.iterationsToGo > 0) {
/*  764 */       fireTabuSearchStarted();
/*      */     }
/*      */     
/*      */ 
/*  768 */     while ((this.keepSolving) && (this.iterationsToGo > 0))
/*      */     {
/*  770 */       Thread.yield();
/*  771 */       synchronized (this)
/*      */       {
/*  773 */         this.iterationsToGo -= 1;
/*      */         try
/*      */         {
/*  776 */           performOneIteration();
/*      */         }
/*      */         catch (NoMovesGeneratedException e) {
/*  779 */           if (err != null) {
/*  780 */             err.println(e);
/*      */           }
/*      */         } catch (NoCurrentSolutionException e) {
/*  783 */           if (err != null)
/*  784 */             err.println(e);
/*      */         }
/*  786 */         incrementIterationsCompleted();
/*      */       }
/*      */     }
/*      */     
/*  790 */     setSolving(false);
/*  791 */     fireTabuSearchStopped();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void stopSolving()
/*      */   {
/*  803 */     setKeepSolving(false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean isSolving()
/*      */   {
/*  813 */     return this.solving;
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
/*      */   public synchronized void setObjectiveFunction(ObjectiveFunction function)
/*      */   {
/*  827 */     this.objectiveFunction = function;
/*      */     
/*  829 */     if (this.currentSolution != null) {
/*  830 */       this.currentSolution.setObjectiveValue(function.evaluate(this.currentSolution, null));
/*      */     }
/*  832 */     if (this.bestSolution != null) {
/*  833 */       this.bestSolution.setObjectiveValue(function.evaluate(this.bestSolution, null));
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
/*      */   public synchronized void setMoveManager(MoveManager moveManager)
/*      */   {
/*  846 */     this.moveManager = moveManager;
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
/*      */   public synchronized void setTabuList(TabuList tabuList)
/*      */   {
/*  859 */     this.tabuList = tabuList;
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
/*      */   public synchronized void setAspirationCriteria(AspirationCriteria aspirationCriteria)
/*      */   {
/*  873 */     this.aspirationCriteria = aspirationCriteria;
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
/*      */   public synchronized void setBestSolution(Solution solution)
/*      */   {
/*  888 */     internalSetBestSolution(solution);
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
/*      */   public synchronized void setCurrentSolution(Solution solution)
/*      */   {
/*  903 */     internalSetCurrentSolution(solution);
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
/*      */   public synchronized void setIterationsToGo(int iterations)
/*      */   {
/*  922 */     this.iterationsToGo = iterations;
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
/*      */   public synchronized void setMaximizing(boolean maximizing)
/*      */   {
/*  938 */     this.maximizing = maximizing;
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
/*      */   public synchronized void setChooseFirstImprovingMove(boolean choose)
/*      */   {
/*  955 */     this.chooseFirstImprovingMove = choose;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized ObjectiveFunction getObjectiveFunction()
/*      */   {
/*  967 */     return this.objectiveFunction;
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
/*      */   public synchronized MoveManager getMoveManager()
/*      */   {
/*  982 */     return this.moveManager;
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
/*      */   public synchronized TabuList getTabuList()
/*      */   {
/*  997 */     return this.tabuList;
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
/*      */   public synchronized AspirationCriteria getAspirationCriteria()
/*      */   {
/* 1012 */     return this.aspirationCriteria;
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
/*      */   public synchronized Solution getBestSolution()
/*      */   {
/* 1026 */     return this.bestSolution;
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
/*      */   public synchronized Solution getCurrentSolution()
/*      */   {
/* 1040 */     return this.currentSolution;
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
/*      */   public synchronized int getIterationsToGo()
/*      */   {
/* 1053 */     return this.iterationsToGo;
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
/*      */   public synchronized boolean isMaximizing()
/*      */   {
/* 1066 */     return this.maximizing;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean isChooseFirstImprovingMove()
/*      */   {
/* 1078 */     return this.chooseFirstImprovingMove;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\SingleThreadedTabuSearch.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */