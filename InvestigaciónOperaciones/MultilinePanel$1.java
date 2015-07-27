/*    */ import java.awt.Dimension;
/*    */ import java.awt.event.ComponentAdapter;
/*    */ import java.awt.event.ComponentEvent;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JScrollPane;
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
/*    */ class MultilinePanel$1
/*    */   extends ComponentAdapter
/*    */ {
/*    */   private final MultilinePanel this$0;
/*    */   
/* 46 */   MultilinePanel$1(MultilinePanel this$0) { this.this$0 = this$0; }
/*    */   
/* 48 */   public void componentResized(ComponentEvent e) { Dimension d = new Dimension(this.this$0.scrollPane.getWidth() - 16, this.this$0.scrollPane.getHeight() - 50);
/*    */     
/* 50 */     this.this$0.mll.setPreferredSize(d);
/* 51 */     this.this$0.mll.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
/*    */     
/*    */ 
/* 54 */     this.this$0.revalidate();
/* 55 */     this.this$0.repaint();
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\MultilinePanel$1.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */