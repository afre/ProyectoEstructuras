/*    */ import java.awt.Image;
/*    */ import java.awt.Panel;
/*    */ 
/*    */ public class MessageDialog extends GJTDialog
/*    */ {
/*    */   private java.awt.Button okButton;
/*    */   
/*    */   public MessageDialog(java.awt.Frame frame, String message, Image image)
/*    */   {
/* 10 */     this(frame, "Message", message, image, true);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public MessageDialog(java.awt.Frame frame, String title, String message, Image image, boolean modal)
/*    */   {
/* 17 */     super(frame, title, modal);
/*    */     
/* 19 */     setCentered(true);
/* 20 */     this.okButton = addButton(" OK ");
/* 21 */     this.okButton.addActionListener(new java.awt.event.ActionListener() {
/*    */       public void actionPerformed(java.awt.event.ActionEvent event) {
/* 23 */         MessageDialog.this.dispose();
/*    */       }
/*    */       
/* 26 */     });
/* 27 */     ImageCanvas canvas = new ImageCanvas();
/* 28 */     if (image != null)
/* 29 */       canvas.setImage(image);
/* 30 */     this.workPanel.setLayout(new java.awt.FlowLayout());
/* 31 */     this.workPanel.add(canvas);
/* 32 */     Panel p = new Panel();
/* 33 */     p.add(new java.awt.Label(message, 1));
/* 34 */     this.workPanel.add(p);
/*    */   }
/*    */   
/*    */   public void doLayout() {
/* 38 */     this.okButton.requestFocus();
/* 39 */     super.doLayout();
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\MessageDialog.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */