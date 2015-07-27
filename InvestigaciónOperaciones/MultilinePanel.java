/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.event.ComponentAdapter;
/*    */ import java.awt.event.ComponentEvent;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JPanel;
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
/*    */ public class MultilinePanel
/*    */   extends JPanel
/*    */ {
/* 30 */   protected MultilineLabel mll = null;
/*    */   
/* 32 */   protected JScrollPane scrollPane = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public MultilinePanel(MultilineLabel m, JScrollPane sp)
/*    */   {
/* 40 */     super(new BorderLayout());
/* 41 */     this.mll = m;
/* 42 */     this.scrollPane = sp;
/* 43 */     this.mll.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
/* 44 */     add(m, "Center");
/*    */     
/* 46 */     addComponentListener(new ComponentAdapter() {
/*    */       public void componentResized(ComponentEvent e) {
/* 48 */         Dimension d = new Dimension(MultilinePanel.this.scrollPane.getWidth() - 16, MultilinePanel.this.scrollPane.getHeight() - 50);
/*    */         
/* 50 */         MultilinePanel.this.mll.setPreferredSize(d);
/* 51 */         MultilinePanel.this.mll.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
/*    */         
/*    */ 
/* 54 */         MultilinePanel.this.revalidate();
/* 55 */         MultilinePanel.this.repaint();
/*    */       }
/*    */     });
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\MultilinePanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */