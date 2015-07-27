/*    */ package ior;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import org.coinor.opents.Move;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Main
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 15 */     double[][] customers = { { 0.0D, 12.0D, 10.0D, 2.147483647E9D, 2.147483647E9D, 2.147483647E9D, 12.0D }, { 12.0D, 0.0D, 8.0D, 12.0D, 2.147483647E9D, 2.147483647E9D, 2.147483647E9D }, { 10.0D, 8.0D, 0.0D, 11.0D, 3.0D, 2.147483647E9D, 9.0D }, { 2.147483647E9D, 12.0D, 11.0D, 0.0D, 11.0D, 10.0D, 2.147483647E9D }, { 2.147483647E9D, 2.147483647E9D, 3.0D, 11.0D, 0.0D, 6.0D, 7.0D }, { 2.147483647E9D, 2.147483647E9D, 2.147483647E9D, 10.0D, 6.0D, 0.0D, 9.0D }, { 12.0D, 2.147483647E9D, 9.0D, 2.147483647E9D, 7.0D, 9.0D, 0.0D } };
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
/* 34 */     int[] tour = new int[7];
/* 35 */     tour[0] = 0;
/* 36 */     tour[1] = 1;
/* 37 */     tour[2] = 2;
/* 38 */     tour[3] = 3;
/* 39 */     tour[4] = 4;
/* 40 */     tour[5] = 5;
/* 41 */     tour[6] = 6;
/*    */     
/* 43 */     TSPproblem tsp = new TSPproblem(customers, tour);
/* 44 */     ArrayList sss = tsp.getInitIteration();
/*    */     
/*    */ 
/*    */ 
/* 48 */     int[][][] temp = { { { 2, 4 }, { 3, 5 } } };
/* 49 */     ArrayList ss = tsp.getImmediateNeighbor();
/*    */     
/* 51 */     ArrayList eee = (ArrayList)ss.get(1);
/* 52 */     Move tt = (Move)eee.get(2);
/*    */     
/* 54 */     boolean ddd = tsp.isFeasibleTabu(tt, temp);
/* 55 */     tour = new int[7];
/* 56 */     tour[0] = 0;
/* 57 */     tour[1] = 1;
/* 58 */     tour[2] = 2;
/* 59 */     tour[3] = 3;
/* 60 */     tour[4] = 4;
/* 61 */     tour[5] = 5;
/* 62 */     tour[6] = 6;
/* 63 */     if (ddd) {}
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ior\Main.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */