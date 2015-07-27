/*    */ import java.awt.Color;
/*    */ import java.io.Serializable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class POMTablePanel$Cell
/*    */   implements Serializable
/*    */ {
/*    */   String name;
/*    */   int x;
/*    */   int y;
/*    */   int width;
/*    */   int height;
/*    */   int y2;
/*    */   int[] x2;
/*    */   int row;
/*    */   int column;
/* 83 */   int datatype = 1;
/* 84 */   boolean canInput = false;
/* 85 */   boolean canClick = false;
/* 86 */   Color Background = Color.lightGray;
/*    */   
/* 88 */   public POMTablePanel$Cell(POMTablePanel this$0, String s1, int row, int column) { this.name = s1;
/* 89 */     this.row = row;
/* 90 */     this.column = column;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\POMTablePanel$Cell.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */