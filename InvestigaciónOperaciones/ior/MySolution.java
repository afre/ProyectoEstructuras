/*    */ package ior;
/*    */ 
/*    */ import org.coinor.opents.SolutionAdapter;
/*    */ 
/*    */ public class MySolution
/*    */   extends SolutionAdapter
/*    */ {
/*    */   public int[] tour;
/*    */   
/*    */   public MySolution() {}
/*    */   
/*    */   public MySolution(int[] my)
/*    */   {
/* 14 */     this.tour = ((int[])my.clone());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setTour(int[] tours)
/*    */   {
/* 21 */     this.tour = ((int[])tours.clone());
/*    */   }
/*    */   
/*    */   public Object clone()
/*    */   {
/* 26 */     MySolution copy = (MySolution)super.clone();
/* 27 */     copy.tour = ((int[])this.tour.clone());
/* 28 */     return copy;
/*    */   }
/*    */   
/*    */ 
/*    */   public String toString()
/*    */   {
/* 34 */     StringBuffer s = new StringBuffer();
/*    */     
/* 36 */     s.append("Solution value: ".concat(String.valueOf(String.valueOf(getObjectiveValue()[0]))));
/* 37 */     s.append("Sequence: [ ");
/*    */     
/* 39 */     for (int i = 0; i < this.tour.length - 1; i++) {
/* 40 */       s.append(this.tour[i]).append(", ");
/*    */     }
/* 42 */     s.append(this.tour[(this.tour.length - 1)]);
/* 43 */     s.append(" ]");
/*    */     
/* 45 */     return s.toString();
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ior\MySolution.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */