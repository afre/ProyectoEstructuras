/*    */ package ior;
/*    */ 
/*    */ import org.coinor.opents.Solution;
/*    */ 
/*    */ public class MyObjectiveFunction implements org.coinor.opents.ObjectiveFunction
/*    */ {
/*    */   public double[][] matrix;
/*    */   
/*    */   public MyObjectiveFunction(double[][] customers) {
/* 10 */     this.matrix = customers;
/*    */   }
/*    */   
/*    */   public double[] evaluate(Solution solution, org.coinor.opents.Move move)
/*    */   {
/* 15 */     int[] tour = ((MySolution)solution).tour;
/* 16 */     int len = tour.length;
/*    */     
/*    */ 
/* 19 */     if (move == null) {
/* 20 */       double dist = 0.0D;
/* 21 */       for (int i = 0; i < len; i++) {
/* 22 */         dist += this.matrix[tour[i]][tour[(i + 1)]];
/*    */       }
/* 24 */       return new double[] { dist };
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 30 */     MySwapMove mv = (MySwapMove)move;
/* 31 */     int begin = mv.begin;
/* 32 */     int end = mv.end;
/* 33 */     int[] sub = (int[])tour.clone();
/*    */     
/* 35 */     for (int i = 0; i <= end - begin; i++) {
/* 36 */       sub[(i + begin)] = tour[(end - i)];
/*    */     }
/*    */     
/* 39 */     double dist = 0.0D;
/* 40 */     for (int i = 0; i < len; i++)
/* 41 */       dist += this.matrix[sub[i]][sub[(i + 1)]];
/* 42 */     return new double[] { dist };
/*    */   }
/*    */   
/*    */ 
/*    */   public double[] getValue(int[] tours)
/*    */   {
/* 48 */     int len = tours.length;
/* 49 */     double dist = 0.0D;
/* 50 */     for (int i = 0; i < len; i++)
/* 51 */       dist += this.matrix[tours[i]][tours[(i + 1)]];
/* 52 */     return new double[] { dist };
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isFeasibleLink(int[] link)
/*    */   {
/* 58 */     if (link.length != 2) return false;
/* 59 */     if (link[0] == link[1]) return false;
/* 60 */     if (this.matrix[link[0]][link[1]] == Integer.MAX_VALUE) return false;
/* 61 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isFeasibleSolution(int[] tours) {
/* 65 */     double dist = 0.0D;
/* 66 */     int len = tours.length;
/* 67 */     for (int i = 0; i < len; i++)
/* 68 */       dist += this.matrix[tours[i]][tours[(i + 1)]];
/* 69 */     if (dist >= Integer.MAX_VALUE) return false;
/* 70 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ior\MyObjectiveFunction.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */