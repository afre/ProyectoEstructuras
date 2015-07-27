/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Image;
/*    */ 
/*    */ public class ImageCanvas extends javax.swing.JComponent
/*    */ {
/*    */   private Image image;
/*    */   
/*    */   public ImageCanvas() {}
/*    */   
/*    */   public ImageCanvas(Image image)
/*    */   {
/* 13 */     setImage(image);
/*    */   }
/*    */   
/*    */   public void paint(Graphics g) {
/* 17 */     if (this.image != null) {
/* 18 */       g.drawImage(this.image, 0, 0, this);
/*    */     }
/*    */   }
/*    */   
/*    */   public void update(Graphics g) {
/* 23 */     paint(g);
/*    */   }
/*    */   
/*    */   public void setImage(Image image) {
/* 27 */     java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
/*    */     try {
/* 29 */       tracker.addImage(image, 0);
/* 30 */       tracker.waitForID(0);
/*    */     }
/*    */     catch (InterruptedException e) {
/* 33 */       e.printStackTrace();
/* 34 */       return;
/*    */     }
/*    */     
/* 37 */     this.image = image;
/*    */     
/* 39 */     setSize(image.getWidth(this), image.getHeight(this));
/*    */     
/* 41 */     if (isShowing()) {
/* 42 */       repaint();
/*    */     }
/*    */   }
/*    */   
/*    */   public Dimension getMinimumSize()
/*    */   {
/* 48 */     if (this.image != null) {
/* 49 */       return new Dimension(this.image.getWidth(this), this.image.getHeight(this));
/*    */     }
/*    */     
/*    */ 
/* 53 */     return new Dimension(0, 0);
/*    */   }
/*    */   
/*    */   public Dimension getPreferredSize()
/*    */   {
/* 58 */     return getMinimumSize();
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ImageCanvas.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */