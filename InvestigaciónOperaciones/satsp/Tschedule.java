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
/*    */ public class Tschedule
/*    */   implements Serializable
/*    */ {
/* 14 */   protected int Tnum = 0;
/* 15 */   protected double Tinitparameter = 0.2D;
/* 16 */   protected double Tparameter = 0.5D;
/*    */   protected double[] T;
/* 18 */   protected int currentT = 0;
/*    */   
/*    */   public Tschedule(int num, double initObjectValue) {
/* 21 */     this.Tnum = num;
/* 22 */     this.T = new double[num];
/* 23 */     this.T[0] = (this.Tinitparameter * initObjectValue);
/* 24 */     for (int i = 1; i < this.Tnum; i++) {
/* 25 */       this.T[i] = (this.T[(i - 1)] * this.Tparameter);
/*    */     }
/*    */   }
/*    */   
/*    */   public double[] getT() {
/* 30 */     return this.T;
/*    */   }
/*    */   
/*    */   public double getTinitparameter() {
/* 34 */     return this.Tinitparameter;
/*    */   }
/*    */   
/*    */   public int getTnum() {
/* 38 */     return this.Tnum;
/*    */   }
/*    */   
/*    */   public double getTparameter() {
/* 42 */     return this.Tparameter;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\satsp\Tschedule.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */