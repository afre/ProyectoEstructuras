/*    */ package ior;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ 
/*    */ public class MyGreedyStartSolution
/*    */   extends MySolution
/*    */ {
/*    */   public MyGreedyStartSolution() {}
/*    */   
/*    */   public MyGreedyStartSolution(double[][] customers)
/*    */   {
/* 13 */     int[] avail = new int[customers.length];
/* 14 */     this.tour = new int[customers.length];
/* 15 */     for (int i = 0; i < avail.length; i++)
/* 16 */       avail[i] = i;
/* 17 */     for (int i = 1; i < this.tour.length; i++)
/*    */     {
/* 19 */       int closest = -1;
/* 20 */       double dist = Double.MAX_VALUE;
/* 21 */       for (int j = 1; j < avail.length; j++)
/* 22 */         if ((norm(customers, this.tour[(i - 1)], j) < dist) && (avail[j] >= 0))
/*    */         {
/* 24 */           dist = norm(customers, this.tour[(i - 1)], j);
/* 25 */           closest = j;
/*    */         }
/* 27 */       this.tour[i] = closest;
/* 28 */       avail[closest] = -1;
/*    */     }
/*    */     
/* 31 */     for (int i = 0; i < this.tour.length; i++) {
/* 32 */       System.out.println(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(customers[this.tour[i]][0]))).append("\t").append(customers[this.tour[i]][1]))));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private double norm(double[][] matr, int a, int b)
/*    */   {
/* 40 */     double xDiff = matr[b][0] - matr[a][0];
/* 41 */     double yDiff = matr[b][1] - matr[a][1];
/* 42 */     return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ior\MyGreedyStartSolution.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */