/*    */ package ior;
/*    */ 
/*    */ import org.coinor.opents.Move;
/*    */ 
/*    */ public class MyMoveManager implements org.coinor.opents.MoveManager
/*    */ {
/*    */   public Move[] getAllMoves(org.coinor.opents.Solution solution) {
/*  8 */     int[] tour = ((MySolution)solution).tour;
/*  9 */     Move[] buffer = new Move[tour.length * tour.length];
/* 10 */     int nextBufferPos = 0;
/*    */     
/*    */ 
/* 13 */     for (int i = 1; i < tour.length - 1; i++) {
/* 14 */       for (int j = i + 1; j <= tour.length - 1; j++) {
/* 15 */         if ((i != 1) || (j != tour.length - 1))
/*    */         {
/* 17 */           buffer[(nextBufferPos++)] = new MySwapMove(i, j); }
/*    */       }
/*    */     }
/* 20 */     Move[] moves = new Move[nextBufferPos];
/* 21 */     System.arraycopy(buffer, 0, moves, 0, nextBufferPos);
/*    */     
/* 23 */     return moves;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ior\MyMoveManager.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */