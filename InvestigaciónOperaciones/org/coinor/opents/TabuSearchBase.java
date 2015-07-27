/*     */ package org.coinor.opents;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TabuSearchBase
/*     */   implements TabuSearch
/*     */ {
/*     */   private int iterationsCompleted;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int getIterationsCompleted()
/*     */   {
/*  93 */     return this.iterationsCompleted;
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
/*     */   protected void incrementIterationsCompleted()
/*     */   {
/* 117 */     this.iterationsCompleted += 1;
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
/* 145 */   private final TabuSearchEvent tabuEvent = new TabuSearchEvent(this);
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
/* 173 */   private TabuSearchListener[] tabuSearchListenerList = new TabuSearchListener[0];
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
/*     */   public final synchronized void addTabuSearchListener(TabuSearchListener listener)
/*     */   {
/* 211 */     TabuSearchListener[] list = new TabuSearchListener[this.tabuSearchListenerList.length + 1];
/*     */     
/*     */ 
/*     */ 
/* 215 */     for (int i = 0; i < list.length - 1; i++)
/*     */     {
/* 217 */       list[i] = this.tabuSearchListenerList[i];
/*     */     }
/*     */     
/*     */ 
/* 221 */     list[(list.length - 1)] = listener;
/*     */     
/* 223 */     this.tabuSearchListenerList = list;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final synchronized void removeTabuSearchListener(TabuSearchListener listener)
/*     */   {
/* 259 */     int index = -1;
/*     */     
/* 261 */     int j = 0;
/*     */     
/* 263 */     while ((index < 0) && (j < this.tabuSearchListenerList.length))
/*     */     {
/* 265 */       if (this.tabuSearchListenerList[j] == listener)
/*     */       {
/* 267 */         index = j;
/*     */       } else {
/* 269 */         j++;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 277 */     if (index >= 0)
/*     */     {
/* 279 */       TabuSearchListener[] list = new TabuSearchListener[this.tabuSearchListenerList.length - 1];
/*     */       
/*     */ 
/*     */ 
/* 283 */       for (int i = 0; i < list.length; i++)
/*     */       {
/* 285 */         list[i] = this.tabuSearchListenerList[(i + 1)];
/*     */       }
/* 287 */       this.tabuSearchListenerList = list;
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final synchronized void fireNewBestSolution()
/*     */   {
/* 327 */     int len = this.tabuSearchListenerList.length;
/*     */     
/* 329 */     for (int i = 0; i < len; i++)
/*     */     {
/* 331 */       this.tabuSearchListenerList[i].newBestSolutionFound(this.tabuEvent);
/*     */     }
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
/*     */   protected final synchronized void fireNewCurrentSolution()
/*     */   {
/* 359 */     int len = this.tabuSearchListenerList.length;
/*     */     
/* 361 */     for (int i = 0; i < len; i++)
/*     */     {
/* 363 */       this.tabuSearchListenerList[i].newCurrentSolutionFound(this.tabuEvent);
/*     */     }
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
/*     */   protected final synchronized void fireUnimprovingMoveMade()
/*     */   {
/* 391 */     int len = this.tabuSearchListenerList.length;
/*     */     
/* 393 */     for (int i = 0; i < len; i++)
/*     */     {
/* 395 */       this.tabuSearchListenerList[i].unimprovingMoveMade(this.tabuEvent);
/*     */     }
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
/*     */   protected final synchronized void fireImprovingMoveMade()
/*     */   {
/* 412 */     int len = this.tabuSearchListenerList.length;
/* 413 */     for (int i = 0; i < len; i++) {
/* 414 */       this.tabuSearchListenerList[i].improvingMoveMade(this.tabuEvent);
/*     */     }
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
/*     */   protected final synchronized void fireNoChangeInValueMoveMade()
/*     */   {
/* 431 */     int len = this.tabuSearchListenerList.length;
/* 432 */     for (int i = 0; i < len; i++) {
/* 433 */       this.tabuSearchListenerList[i].noChangeInValueMoveMade(this.tabuEvent);
/*     */     }
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
/*     */   protected final synchronized void fireTabuSearchStopped()
/*     */   {
/* 457 */     int len = this.tabuSearchListenerList.length;
/*     */     
/* 459 */     for (int i = 0; i < len; i++)
/*     */     {
/* 461 */       this.tabuSearchListenerList[i].tabuSearchStopped(this.tabuEvent);
/*     */     }
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
/*     */   protected final synchronized void fireTabuSearchStarted()
/*     */   {
/* 489 */     int len = this.tabuSearchListenerList.length;
/*     */     
/* 491 */     for (int i = 0; i < len; i++)
/*     */     {
/* 493 */       this.tabuSearchListenerList[i].tabuSearchStarted(this.tabuEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   public abstract boolean isMaximizing();
/*     */   
/*     */   public abstract int getIterationsToGo();
/*     */   
/*     */   public abstract Solution getCurrentSolution();
/*     */   
/*     */   public abstract Solution getBestSolution();
/*     */   
/*     */   public abstract AspirationCriteria getAspirationCriteria();
/*     */   
/*     */   public abstract TabuList getTabuList();
/*     */   
/*     */   public abstract MoveManager getMoveManager();
/*     */   
/*     */   public abstract ObjectiveFunction getObjectiveFunction();
/*     */   
/*     */   public abstract boolean isChooseFirstImprovingMove();
/*     */   
/*     */   public abstract void setChooseFirstImprovingMove(boolean paramBoolean);
/*     */   
/*     */   public abstract void setMaximizing(boolean paramBoolean);
/*     */   
/*     */   public abstract void setIterationsToGo(int paramInt);
/*     */   
/*     */   public abstract void setCurrentSolution(Solution paramSolution);
/*     */   
/*     */   public abstract void setBestSolution(Solution paramSolution);
/*     */   
/*     */   public abstract void setAspirationCriteria(AspirationCriteria paramAspirationCriteria);
/*     */   
/*     */   public abstract void setTabuList(TabuList paramTabuList);
/*     */   
/*     */   public abstract void setMoveManager(MoveManager paramMoveManager);
/*     */   
/*     */   public abstract void setObjectiveFunction(ObjectiveFunction paramObjectiveFunction);
/*     */   
/*     */   public abstract boolean isSolving();
/*     */   
/*     */   public abstract void stopSolving();
/*     */   
/*     */   public abstract void startSolving();
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\TabuSearchBase.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */