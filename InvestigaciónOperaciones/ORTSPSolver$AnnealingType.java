/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
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
/*    */ class ORTSPSolver$AnnealingType
/*    */   implements Serializable
/*    */ {
/*    */   private ORTSPSolver$AnnealingType(ORTSPSolver this$0) {}
/*    */   
/* 92 */   ORTSPSolver$AnnealingType(ORTSPSolver x$0, ORTSPSolver..1 x$1) { this(x$0); }
/*    */   
/* 94 */   int currentindex = 0;
/*    */   double PolyResult;
/*    */   ArrayList[] An_list;
/* 97 */   int A_size = 20;
/* 98 */   boolean isPrintReady = false;
/*    */   String bestSolution;
/*    */   String[][] SAprintData;
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORTSPSolver$AnnealingType.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */