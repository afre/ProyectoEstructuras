/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.JPanel;
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
/*    */ public class IORMCActionPanel$MaxtrixPanel
/*    */   extends JPanel
/*    */ {
/*    */   public IORMCActionPanel$MaxtrixPanel(IORMCActionPanel this$0) {}
/*    */   
/*    */   public void paint(Graphics g)
/*    */   {
/* 43 */     super.paint(g);
/* 44 */     Dimension d = getSize();
/* 45 */     g.drawLine(0, 2, 0, d.height - 2);
/* 46 */     g.drawLine(d.width - 1, 2, d.width - 1, d.height - 3);
/*    */     
/* 48 */     int width = d.width / 5;
/* 49 */     if (width > 10) {
/* 50 */       g.drawLine(0, 2, 10, 0);
/* 51 */       g.drawLine(d.width - 11, 0, d.width - 1, 2);
/* 52 */       g.drawLine(0, d.height - 3, 10, d.height - 1);
/* 53 */       g.drawLine(d.width - 11, d.height - 1, d.width - 1, d.height - 3);
/*    */     }
/*    */     else {
/* 56 */       g.drawLine(0, 2, width, 0);
/* 57 */       g.drawLine(d.width - width - 1, 0, d.width - 1, 2);
/* 58 */       g.drawLine(0, d.height - 3, width, d.height - 1);
/* 59 */       g.drawLine(d.width - width - 1, d.height - 1, d.width - 1, d.height - 3);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORMCActionPanel$MaxtrixPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */