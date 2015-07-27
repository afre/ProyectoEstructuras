/*    */ import java.awt.event.KeyAdapter;
/*    */ import java.awt.event.KeyEvent;
/*    */ import javax.swing.JTextField;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NumberTextField
/*    */   extends JTextField
/*    */ {
/*    */   TextFieldNotify consumer;
/*    */   
/*    */   public NumberTextField(TextFieldNotify consumer)
/*    */   {
/* 19 */     this.consumer = consumer;
/* 20 */     addKeyListener(new KeyAdapter() {
/*    */       public void keyReleased(KeyEvent e) {
/* 22 */         NumberTextField.this.this_keyReleased(e);
/*    */       }
/*    */       
/*    */       public void keyPressed(KeyEvent e) {
/* 26 */         NumberTextField.this.this_keyPressed(e);
/*    */       }
/*    */     });
/*    */   }
/*    */   
/*    */   void this_keyPressed(KeyEvent e) {
/* 32 */     int code = e.getKeyCode();
/* 33 */     char c = e.getKeyChar();
/* 34 */     if ((code == 127) || (code == 37) || (code == 39) || (code == 8))
/*    */     {
/* 36 */       return; }
/* 37 */     if (!Character.isDigit(c)) {
/* 38 */       e.consume();
/*    */     }
/*    */   }
/*    */   
/*    */   void this_keyReleased(KeyEvent e) {
/* 43 */     int code = e.getKeyCode();
/* 44 */     char c = e.getKeyChar();
/* 45 */     if ((code == 37) || (code == 39))
/* 46 */       return;
/*    */     try {
/* 48 */       this.consumer.dealWithValue(String.valueOf(Integer.parseInt(getText())));
/*    */     }
/*    */     catch (NumberFormatException localNumberFormatException) {}
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\NumberTextField.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */