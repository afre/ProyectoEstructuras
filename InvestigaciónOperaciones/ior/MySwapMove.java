/*    */ package ior;
/*    */ 
/*    */ import org.coinor.opents.Solution;
/*    */ 
/*    */ public class MySwapMove implements org.coinor.opents.Move
/*    */ {
/*    */   public int begin;
/*    */   public int end;
/*    */   
/*    */   public MySwapMove(int begin, int end)
/*    */   {
/* 12 */     this.begin = begin;
/* 13 */     this.end = end;
/*    */   }
/*    */   
/*    */   public void operateOn(Solution soln) {
/* 17 */     MySolution so = (MySolution)soln;
/* 18 */     int[] tour = ((MySolution)soln).tour;
/* 19 */     int[] sub = (int[])tour.clone();
/*    */     
/* 21 */     for (int i = 0; i <= this.end - this.begin; i++) {
/* 22 */       sub[(i + this.begin)] = tour[(this.end - i)];
/*    */     }
/* 24 */     so.setTour(sub);
/*    */   }
/*    */   
/*    */ 
/*    */   public int[] identifyMove()
/*    */   {
/* 30 */     int[] test = new int[2];
/* 31 */     test[0] = this.begin;
/* 32 */     test[1] = this.end;
/* 33 */     return test;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     return "customer is ";
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ior\MySwapMove.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */