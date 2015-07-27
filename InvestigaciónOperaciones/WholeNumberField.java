/*    */ import java.awt.Toolkit;
/*    */ import java.io.PrintStream;
/*    */ import java.text.NumberFormat;
/*    */ import java.text.ParseException;
/*    */ import java.util.Locale;
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.text.AttributeSet;
/*    */ import javax.swing.text.BadLocationException;
/*    */ import javax.swing.text.Document;
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
/*    */ public class WholeNumberField
/*    */   extends JTextField
/*    */ {
/*    */   private Toolkit toolkit;
/*    */   private NumberFormat integerFormatter;
/*    */   
/*    */   public WholeNumberField(int value, int columns)
/*    */   {
/* 38 */     super(columns);
/* 39 */     this.toolkit = Toolkit.getDefaultToolkit();
/* 40 */     this.integerFormatter = NumberFormat.getNumberInstance(Locale.US);
/* 41 */     this.integerFormatter.setParseIntegerOnly(true);
/* 42 */     setValue(value);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getValue()
/*    */   {
/* 49 */     int retVal = 0;
/*    */     try {
/* 51 */       retVal = this.integerFormatter.parse(getText()).intValue();
/*    */     }
/*    */     catch (ParseException e)
/*    */     {
/* 55 */       this.toolkit.beep();
/*    */     }
/* 57 */     return retVal;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setValue(int value)
/*    */   {
/* 65 */     setText(this.integerFormatter.format(value));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected Document createDefaultModel()
/*    */   {
/* 72 */     return new WholeNumberField.WholeNumberDocument();
/*    */   }
/*    */   
/*    */   protected class WholeNumberDocument extends PlainDocument
/*    */   {
/*    */     protected WholeNumberDocument() {}
/*    */     
/*    */     public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
/* 80 */       char[] source = str.toCharArray();
/* 81 */       char[] result = new char[source.length];
/* 82 */       int j = 0;
/*    */       
/* 84 */       for (int i = 0; i < result.length; i++) {
/* 85 */         if (Character.isDigit(source[i])) {
/* 86 */           result[(j++)] = source[i];
/*    */         } else {
/* 88 */           WholeNumberField.this.toolkit.beep();
/* 89 */           System.err.println("insertString: ".concat(String.valueOf(String.valueOf(source[i]))));
/*    */         }
/*    */       }
/* 92 */       super.insertString(offs, new String(result, 0, j), a);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\WholeNumberField.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */