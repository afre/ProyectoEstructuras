/*    */ import java.awt.Point;
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
/*    */ public class Segment
/*    */ {
/*    */   Point st;
/*    */   Point fn;
/*    */   
/*    */   public Segment(Point p1, Point p2)
/*    */   {
/* 20 */     this.st = p1;
/* 21 */     this.fn = p2;
/*    */   }
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
/*    */   public Point tail()
/*    */   {
/* 56 */     return this.st;
/*    */   }
/*    */   
/*    */   public Point head() {
/* 60 */     return this.fn;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\Segment.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */