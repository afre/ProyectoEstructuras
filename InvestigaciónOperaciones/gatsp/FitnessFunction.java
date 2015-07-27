/*    */ package gatsp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FitnessFunction
/*    */ {
/*    */   protected double[][] matrix;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public FitnessFunction(double[][] customers)
/*    */   {
/* 17 */     this.matrix = customers;
/*    */   }
/*    */   
/*    */   public boolean feasible(int[] tours)
/*    */   {
/* 22 */     if (evaluate(tours) > Integer.MAX_VALUE)
/* 23 */       return false;
/* 24 */     return true;
/*    */   }
/*    */   
/*    */   public double evaluate(int[] tours) {
/* 28 */     int[] tour = tours;
/* 29 */     int len = tour.length;
/*    */     
/*    */ 
/* 32 */     double dist = 0.0D;
/* 33 */     for (int i = 0; i < len; i++)
/* 34 */       dist += this.matrix[tour[i]][tour[(i + 1)]];
/* 35 */     return dist;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\gatsp\FitnessFunction.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */