/*    */ import java.awt.Toolkit;
/*    */ import java.io.PrintStream;
/*    */ import javax.swing.text.AttributeSet;
/*    */ import javax.swing.text.BadLocationException;
/*    */ import javax.swing.text.PlainDocument;
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
/*    */ public class WholeNumberField$WholeNumberDocument
/*    */   extends PlainDocument
/*    */ {
/*    */   private final WholeNumberField this$0;
/*    */   
/*    */   protected WholeNumberField$WholeNumberDocument(WholeNumberField this$0)
/*    */   {
/* 75 */     this.this$0 = this$0;
/*    */   }
/*    */   
/*    */   public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
/*    */   {
/* 80 */     char[] source = str.toCharArray();
/* 81 */     char[] result = new char[source.length];
/* 82 */     int j = 0;
/*    */     
/* 84 */     for (int i = 0; i < result.length; i++) {
/* 85 */       if (Character.isDigit(source[i])) {
/* 86 */         result[(j++)] = source[i];
/*    */       } else {
/* 88 */         WholeNumberField.access$0(this.this$0).beep();
/* 89 */         System.err.println("insertString: ".concat(String.valueOf(String.valueOf(source[i]))));
/*    */       }
/*    */     }
/* 92 */     super.insertString(offs, new String(result, 0, j), a);
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\WholeNumberField$WholeNumberDocument.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */