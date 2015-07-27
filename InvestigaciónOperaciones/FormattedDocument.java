/*    */ import java.awt.Toolkit;
/*    */ import java.io.PrintStream;
/*    */ import java.text.Format;
/*    */ import java.text.ParseException;
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
/*    */ public class FormattedDocument
/*    */   extends PlainDocument
/*    */ {
/*    */   private Format format;
/*    */   
/*    */   public FormattedDocument(Format f)
/*    */   {
/* 34 */     this.format = f;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Format getFormat()
/*    */   {
/* 41 */     return this.format;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void insertString(int offs, String str, AttributeSet a)
/*    */     throws BadLocationException
/*    */   {
/* 53 */     String currentText = getText(0, getLength());
/* 54 */     String beforeOffset = currentText.substring(0, offs);
/* 55 */     String afterOffset = currentText.substring(offs, currentText.length());
/* 56 */     String proposedResult = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(beforeOffset))).append(str).append(afterOffset)));
/*    */     try
/*    */     {
/* 59 */       this.format.parseObject(proposedResult);
/* 60 */       super.insertString(offs, str, a);
/*    */     } catch (ParseException e) {
/* 62 */       if ((proposedResult.equals("-") == false) && (proposedResult.equals(".") == false)) {
/* 63 */         Toolkit.getDefaultToolkit().beep();
/* 64 */         System.err.println("insertString: could not parse: ".concat(String.valueOf(String.valueOf(proposedResult))));
/*    */       }
/*    */       else
/*    */       {
/* 68 */         super.insertString(offs, str, a);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void remove(int offs, int len)
/*    */     throws BadLocationException
/*    */   {
/* 78 */     String currentText = getText(0, getLength());
/* 79 */     String beforeOffset = currentText.substring(0, offs);
/* 80 */     String afterOffset = currentText.substring(len + offs, currentText.length());
/*    */     
/* 82 */     String proposedResult = String.valueOf(String.valueOf(beforeOffset)).concat(String.valueOf(String.valueOf(afterOffset)));
/*    */     try
/*    */     {
/* 85 */       if (proposedResult.length() != 0)
/* 86 */         this.format.parseObject(proposedResult);
/* 87 */       super.remove(offs, len);
/*    */     } catch (ParseException e) {
/* 89 */       if (proposedResult.equals("-") == false) {
/* 90 */         Toolkit.getDefaultToolkit().beep();
/* 91 */         System.err.println("remove: could not parse: ".concat(String.valueOf(String.valueOf(proposedResult))));
/*    */       }
/*    */       else {
/* 94 */         super.remove(offs, len);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\FormattedDocument.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */