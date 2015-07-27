/*    */ package org.coinor.opents;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BestEverAspirationCriteria
/*    */   implements AspirationCriteria
/*    */ {
/*    */   public boolean overrideTabu(Solution solution, Move proposedMove, double[] proposedValue, TabuSearch tabuSearch)
/*    */   {
/* 52 */     return SingleThreadedTabuSearch.isFirstBetterThanSecond(proposedValue, tabuSearch.getBestSolution().getObjectiveValue(), tabuSearch.isMaximizing());
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\BestEverAspirationCriteria.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */