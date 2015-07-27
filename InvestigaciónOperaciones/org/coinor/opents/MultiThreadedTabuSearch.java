/*     */ package org.coinor.opents;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiThreadedTabuSearch
/*     */   extends SingleThreadedTabuSearch
/*     */ {
/*  45 */   private int threads = 1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  52 */   private int threadPriority = 5;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private NeighborhoodHelper[] helpers;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MultiThreadedTabuSearch() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MultiThreadedTabuSearch(Solution initialSolution, MoveManager moveManager, ObjectiveFunction objectiveFunction, TabuList tabuList, AspirationCriteria aspirationCriteria, boolean maximizing)
/*     */   {
/* 106 */     super(initialSolution, moveManager, objectiveFunction, tabuList, aspirationCriteria, maximizing);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setThreads(int threads)
/*     */   {
/* 122 */     if (threads <= 0) {
/* 123 */       return;
/*     */     }
/*     */     
/* 126 */     this.threads = threads;
/*     */     
/*     */ 
/* 129 */     if (this.helpers == null) {
/* 130 */       this.helpers = new NeighborhoodHelper[threads];
/* 131 */       for (int i = 0; i < threads; i++)
/*     */       {
/* 133 */         this.helpers[i] = new NeighborhoodHelper(null);
/* 134 */         this.helpers[i].start();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 140 */     if (this.helpers.length < threads)
/*     */     {
/*     */ 
/* 143 */       NeighborhoodHelper[] temp = new NeighborhoodHelper[threads];
/*     */       
/*     */ 
/* 146 */       for (int i = 0; i < threads; i++) {
/* 147 */         temp[i] = (i < this.helpers.length ? this.helpers[i] : new NeighborhoodHelper(null));
/*     */       }
/*     */       
/* 150 */       this.helpers = temp;
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/* 155 */     else if (this.helpers.length > threads)
/*     */     {
/*     */ 
/* 158 */       NeighborhoodHelper[] temp = new NeighborhoodHelper[threads];
/*     */       
/*     */ 
/* 161 */       for (int i = 0; i < threads; i++) {
/* 162 */         if (i < threads) {
/* 163 */           temp[i] = this.helpers[i];
/*     */         } else {
/* 165 */           this.helpers[i].dispose();
/*     */         }
/*     */       }
/* 168 */       this.helpers = temp;
/*     */     }
/* 170 */     notifyAll();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int getThreads()
/*     */   {
/* 181 */     return this.threads;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setThreadPriority(int threadPriority)
/*     */   {
/* 200 */     if (threadPriority < 1) {
/* 201 */       this.threadPriority = 1;
/*     */     }
/* 203 */     else if (threadPriority > 10) {
/* 204 */       this.threadPriority = 10;
/*     */     } else
/* 206 */       this.threadPriority = threadPriority;
/* 207 */     notifyAll();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int getThreadPriority()
/*     */   {
/* 219 */     return this.threadPriority;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private NeighborhoodHelper[] getHelpers()
/*     */   {
/* 230 */     return this.helpers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object[] getBestMove(Solution soln, Move[] moves, ObjectiveFunction objectiveFunction, TabuList tabuList, AspirationCriteria aspirationCriteria, boolean maximizing, boolean chooseFirstImprovingMove)
/*     */   {
/* 260 */     int threads = getThreads();
/* 261 */     if (threads == 1)
/*     */     {
/* 263 */       return SingleThreadedTabuSearch.getBestMove(soln, moves, objectiveFunction, tabuList, aspirationCriteria, maximizing, chooseFirstImprovingMove, this);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 271 */     Move bestMove = null;
/* 272 */     double[] bestMoveVal = null;
/* 273 */     boolean bestMoveTabu = false;
/* 274 */     NeighborhoodHelper[] helpers = getHelpers();
/*     */     
/*     */ 
/* 277 */     int numGroups = helpers.length;
/* 278 */     int nominalSize = moves.length / numGroups;
/* 279 */     Move[][] moveGroups = new Move[numGroups][];
/*     */     
/*     */ 
/* 282 */     for (int i = 0; i < numGroups - 1; i++)
/*     */     {
/* 284 */       moveGroups[i] = new Move[nominalSize];
/* 285 */       System.arraycopy(moves, i * nominalSize, moveGroups[i], 0, nominalSize);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 291 */     moveGroups[(numGroups - 1)] = new Move[nominalSize + moves.length % numGroups];
/* 292 */     System.arraycopy(moves, (numGroups - 1) * nominalSize, moveGroups[(numGroups - 1)], 0, moveGroups[(numGroups - 1)].length);
/*     */     
/*     */ 
/*     */ 
/* 296 */     for (int i = 0; i < numGroups; i++) {
/* 297 */       helpers[i].setWork(soln, moveGroups[i], objectiveFunction, tabuList, aspirationCriteria, maximizing, chooseFirstImprovingMove, this);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 304 */     Object[][] bestMoves = new Object[numGroups][];
/* 305 */     for (int i = 0; i < numGroups; i++)
/*     */     {
/*     */ 
/* 308 */       Object[] contender = helpers[i].getBestMove();
/*     */       
/*     */ 
/* 311 */       Move newMove = (Move)contender[0];
/* 312 */       double[] newObjVal = (double[])contender[1];
/* 313 */       boolean newMoveTabu = ((Boolean)contender[2]).booleanValue();
/*     */       
/*     */ 
/*     */ 
/* 317 */       if (bestMove == null)
/*     */       {
/* 319 */         bestMove = newMove;
/* 320 */         bestMoveVal = newObjVal;
/* 321 */         bestMoveTabu = newMoveTabu;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 327 */       else if (SingleThreadedTabuSearch.isFirstBetterThanSecond(newObjVal, bestMoveVal, maximizing))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 335 */         if ((bestMoveTabu) || (!newMoveTabu)) {
/* 336 */           bestMove = newMove;
/* 337 */           bestMoveVal = newObjVal;
/* 338 */           bestMoveTabu = newMoveTabu;
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 344 */       else if ((bestMoveTabu) && (newMoveTabu)) {
/* 345 */         bestMove = newMove;
/* 346 */         bestMoveVal = newObjVal;
/* 347 */         bestMoveTabu = newMoveTabu;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 355 */     return new Object[] { bestMove, bestMoveVal, new Boolean(bestMoveTabu) };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void startSolving()
/*     */   {
/* 373 */     setKeepSolving(true);
/*     */     
/*     */ 
/* 376 */     if ((!this.solving) && (this.iterationsToGo > 0))
/*     */     {
/*     */ 
/* 379 */       setSolving(true);
/* 380 */       setKeepSolving(true);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 387 */       fireTabuSearchStarted();
/*     */       
/*     */ 
/*     */ 
/* 391 */       TabuSearch This = this;
/*     */       
/* 393 */       Thread t = new Thread(new Runnable() {
/*     */         private final TabuSearch val$This;
/*     */         
/*     */         public void run() {
/* 397 */           while ((MultiThreadedTabuSearch.this.keepSolving) && (MultiThreadedTabuSearch.this.iterationsToGo > 0))
/*     */           {
/*     */ 
/* 400 */             synchronized (this.val$This)
/*     */             {
/* 402 */               MultiThreadedTabuSearch.this.iterationsToGo -= 1;
/*     */               try
/*     */               {
/* 405 */                 MultiThreadedTabuSearch.this.performOneIteration();
/*     */               }
/*     */               catch (NoMovesGeneratedException e)
/*     */               {
/* 409 */                 if (SingleThreadedTabuSearch.err != null)
/* 410 */                   SingleThreadedTabuSearch.err.println(e);
/* 411 */                 MultiThreadedTabuSearch.this.stopSolving();
/*     */               }
/*     */               catch (NoCurrentSolutionException e)
/*     */               {
/* 415 */                 if (SingleThreadedTabuSearch.err != null)
/* 416 */                   SingleThreadedTabuSearch.err.println(e);
/* 417 */                 MultiThreadedTabuSearch.this.stopSolving();
/*     */               }
/* 419 */               MultiThreadedTabuSearch.this.incrementIterationsCompleted();
/* 420 */               this.val$This.notifyAll();
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 425 */           synchronized (this.val$This)
/*     */           {
/*     */ 
/*     */ 
/* 429 */             MultiThreadedTabuSearch.this.setSolving(false);
/* 430 */             MultiThreadedTabuSearch.this.setKeepSolving(false);
/*     */             
/*     */ 
/* 433 */             if (MultiThreadedTabuSearch.this.helpers != null)
/* 434 */               for (int i = 0; i < MultiThreadedTabuSearch.this.helpers.length; i++)
/* 435 */                 MultiThreadedTabuSearch.this.helpers[i].dispose();
/* 436 */             MultiThreadedTabuSearch.this.helpers = null;
/*     */             
/*     */ 
/* 439 */             MultiThreadedTabuSearch.this.fireTabuSearchStopped();
/* 440 */             this.val$This.notifyAll(); } } }, "MultiThreadedTabuSearch-Master");
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 448 */       t.setPriority(this.threadPriority);
/* 449 */       t.start();
/*     */     }
/* 451 */     notifyAll();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void stopSolving()
/*     */   {
/* 463 */     setKeepSolving(false);
/* 464 */     notifyAll();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void waitToFinish()
/*     */   {
/* 473 */     synchronized (this) {
/* 474 */       while (getIterationsToGo() > 0) {
/*     */         try {
/* 476 */           wait();
/*     */         }
/*     */         catch (InterruptedException e) {
/* 479 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected static class NeighborhoodHelper
/*     */     extends Thread
/*     */   {
/*     */     NeighborhoodHelper(MultiThreadedTabuSearch..2 x$0)
/*     */     {
/* 491 */       this();
/*     */     }
/*     */     
/* 494 */     private boolean dispose = false;
/*     */     
/*     */ 
/* 497 */     private boolean workToDo = false;
/*     */     
/*     */ 
/*     */     private Object[] bestMove;
/*     */     
/* 502 */     private static int instanceNum = 0;
/*     */     
/*     */     private Solution soln;
/*     */     
/*     */     private Move[] moves;
/*     */     
/*     */     private ObjectiveFunction objectiveFunction;
/*     */     
/*     */     private TabuList tabuList;
/*     */     
/*     */     private AspirationCriteria aspirationCriteria;
/*     */     
/*     */     private boolean maximizing;
/*     */     
/*     */     private boolean chooseFirstImprovingMove;
/*     */     
/*     */     private TabuSearch tabuSearch;
/*     */     
/*     */     private NeighborhoodHelper()
/*     */     {
/* 522 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void run()
/*     */     {
/* 534 */       while (!this.dispose)
/*     */       {
/*     */ 
/* 537 */         if (!this.workToDo)
/*     */         {
/* 539 */           synchronized (this) {
/* 540 */             try { wait();
/*     */             } catch (InterruptedException e) {
/* 542 */               e.printStackTrace(SingleThreadedTabuSearch.err); } } } else { synchronized (this) { this.bestMove = SingleThreadedTabuSearch.getBestMove(this.soln, this.moves, this.objectiveFunction, this.tabuList, this.aspirationCriteria, this.maximizing, this.chooseFirstImprovingMove, this.tabuSearch);this.workToDo = false;notifyAll();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private synchronized Object[] getBestMove()
/*     */     {
/* 580 */       while (this.workToDo)
/* 581 */         synchronized (this) {
/* 582 */           try { wait();
/*     */           } catch (InterruptedException e) {
/* 584 */             e.printStackTrace(SingleThreadedTabuSearch.err); } } return this.bestMove;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private synchronized void setWork(Solution soln, Move[] moves, ObjectiveFunction objectiveFunction, TabuList tabuList, AspirationCriteria aspirationCriteria, boolean maximizing, boolean chooseFirstImprovingMove, TabuSearch tabuSearch)
/*     */     {
/* 604 */       this.soln = soln;
/* 605 */       this.moves = moves;
/* 606 */       this.objectiveFunction = objectiveFunction;
/* 607 */       this.tabuList = tabuList;
/* 608 */       this.aspirationCriteria = aspirationCriteria;
/* 609 */       this.maximizing = maximizing;
/* 610 */       this.chooseFirstImprovingMove = chooseFirstImprovingMove;
/* 611 */       this.tabuSearch = tabuSearch;
/*     */       
/*     */ 
/* 614 */       this.workToDo = true;
/* 615 */       this.bestMove = null;
/*     */       
/*     */ 
/* 618 */       notifyAll();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private synchronized void dispose()
/*     */     {
/* 625 */       this.dispose = true;
/* 626 */       notifyAll();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\MultiThreadedTabuSearch.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */