/*    */ package satsp;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TspObjectiveFunction
/*    */   implements Serializable
/*    */ {
/*    */   protected double[][] matrix;
/*    */   
/*    */   public TspObjectiveFunction(double[][] customers)
/*    */   {
/* 19 */     this.matrix = customers;
/*    */   }
/*    */   
/*    */   public double evaluate(int[] tours)
/*    */   {
/* 24 */     int[] tour = tours;
/* 25 */     int len = tour.length;
/*    */     
/*    */ 
/* 28 */     double dist = 0.0D;
/* 29 */     for (int i = 0; i < len; i++)
/* 30 */       dist += this.matrix[tour[i]][tour[(i + 1)]];
/* 31 */     return dist;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\satsp\TspObjectiveFunction.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */