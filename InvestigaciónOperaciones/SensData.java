/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SensData
/*    */ {
/* 11 */   public int num_variable = 2;
/*    */   
/*    */ 
/*    */   public int num_constraints;
/*    */   
/*    */ 
/*    */   public float[] slack_surplus;
/*    */   
/*    */   public float[] shadow_price;
/*    */   
/*    */   public float[] cons_value;
/*    */   
/* 23 */   public float[][] range_objcoef = new float[2][2];
/*    */   
/*    */ 
/*    */   public float[][] range_rightside;
/*    */   
/*    */   public boolean unbounded;
/*    */   
/* 30 */   public float[] solution = new float[2];
/*    */   
/*    */ 
/*    */   public float z;
/*    */   
/*    */ 
/*    */   public SensData(int numcons)
/*    */   {
/* 38 */     this.num_constraints = numcons;
/* 39 */     this.slack_surplus = new float[this.num_constraints];
/* 40 */     this.shadow_price = new float[this.num_constraints];
/* 41 */     this.cons_value = new float[this.num_constraints];
/*    */     
/* 43 */     this.range_rightside = new float[this.num_constraints][2];
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\SensData.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */