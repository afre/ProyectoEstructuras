/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Vector;
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
/*    */ class ORNLPSolver$GeneType
/*    */   implements Serializable
/*    */ {
/*    */   ArrayList[] ge_list;
/*    */   
/*    */   private ORNLPSolver$GeneType(ORNLPSolver this$0) {}
/*    */   
/* 92 */   int ge_size = 0;
/* 93 */   int currentindex = 0;
/* 94 */   Vector best = new Vector();
/* 95 */   Vector storePopulation = new Vector();
/* 96 */   Vector generation = new Vector();
/*    */   
/*    */   public void setCurrentIndex(int n) {
/* 99 */     this.currentindex = n;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORNLPSolver$GeneType.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */