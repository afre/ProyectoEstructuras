/*    */ import java.awt.Toolkit;
/*    */ import java.io.PrintStream;
/*    */ import java.text.NumberFormat;
/*    */ import java.text.ParseException;
/*    */ import javax.swing.JTextField;
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
/*    */ public class DecimalField
/*    */   extends JTextField
/*    */ {
/* 28 */   public static int DEFAULT_FIELD_LENGTH = 4;
/*    */   
/*    */ 
/*    */ 
/*    */   private NumberFormat format;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public DecimalField(double value, int columns, NumberFormat f)
/*    */   {
/* 39 */     super(columns);
/* 40 */     setDocument(new FormattedDocument(f));
/* 41 */     this.format = f;
/* 42 */     setValue(value);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DecimalField(double value, NumberFormat f)
/*    */   {
/* 52 */     super(DEFAULT_FIELD_LENGTH);
/* 53 */     setDocument(new FormattedDocument(f));
/* 54 */     this.format = f;
/* 55 */     setValue(value);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public double getValue()
/*    */   {
/* 62 */     double retVal = 0.0D;
/*    */     try {
/* 64 */       retVal = this.format.parse(getText()).doubleValue();
/*    */     }
/*    */     catch (ParseException e) {
/*    */       double d1;
/* 68 */       if (getText().equals("-"))
/* 69 */         return -1.0D;
/* 70 */       if (getText().equals("."))
/* 71 */         return 0.1D;
/* 72 */       Toolkit.getDefaultToolkit().beep();
/* 73 */       System.err.println("getValue: could not parse the input: ".concat(String.valueOf(String.valueOf(getText()))));
/*    */     }
/* 75 */     return retVal;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setValue(double value)
/*    */   {
/* 83 */     setText(this.format.format(value));
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\DecimalField.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */