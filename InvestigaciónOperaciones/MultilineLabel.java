/*    */ import javax.swing.JTextArea;
/*    */ import javax.swing.LookAndFeel;
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
/*    */ public class MultilineLabel
/*    */   extends JTextArea
/*    */ {
/*    */   public MultilineLabel(String s)
/*    */   {
/* 31 */     super(s);
/* 32 */     setLineWrap(true);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void updateUI()
/*    */   {
/* 39 */     super.updateUI();
/* 40 */     setLineWrap(true);
/* 41 */     setHighlighter(null);
/* 42 */     setEditable(false);
/* 43 */     setColumns(20);
/* 44 */     setWrapStyleWord(true);
/*    */     
/* 46 */     LookAndFeel.installBorder(this, "Label.border");
/* 47 */     LookAndFeel.installColorsAndFont(this, "Label.background", "Label.foreground", "Label.font");
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\MultilineLabel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */