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
/*    */ public class Tschedule
/*    */ {
/* 13 */   protected int Tnum = 0;
/* 14 */   protected double Tinitparameter = 0.2D;
/* 15 */   protected double Tparameter = 0.5D;
/*    */   protected double[] T;
/* 17 */   protected int currentT = 0;
/*    */   
/*    */   public Tschedule(int num, double initObjectValue) {
/* 20 */     this.Tnum = num;
/* 21 */     this.T = new double[num];
/* 22 */     this.T[0] = (this.Tinitparameter * initObjectValue);
/* 23 */     for (int i = 1; i < this.Tnum; i++) {
/* 24 */       this.T[i] = (this.T[(i - 1)] * this.Tparameter);
/*    */     }
/*    */   }
/*    */   
/*    */   public double[] getT() {
/* 29 */     return this.T;
/*    */   }
/*    */   
/*    */   public double getTinitparameter() {
/* 33 */     return this.Tinitparameter;
/*    */   }
/*    */   
/*    */   public int getTnum() {
/* 37 */     return this.Tnum;
/*    */   }
/*    */   
/*    */   public double getTparameter() {
/* 41 */     return this.Tparameter;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\Tschedule.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */