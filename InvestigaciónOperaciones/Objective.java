/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Objective
/*    */ {
/* 10 */   public static final int MAXIMIZE = 1;
/* 11 */   public static final int MINIMIZE = 2;
/*    */   
/*    */ 
/*    */   public float x1_coef;
/*    */   
/*    */ 
/*    */   public float x2_coef;
/*    */   
/*    */   public int target;
/*    */   
/*    */ 
/*    */   public Objective(float x1cf, float x2cf, int type)
/*    */   {
/* 24 */     this.x1_coef = x1cf;
/* 25 */     this.x2_coef = x2cf;
/* 26 */     this.target = type;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public float calcZ(float x1val, float x2val)
/*    */   {
/* 34 */     return this.x1_coef * x1val + this.x2_coef * x2val;
/*    */   }
/*    */   
/*    */   public float Slope() {
/* 38 */     return -1 * this.x1_coef / this.x2_coef;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\Objective.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */