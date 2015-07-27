/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
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
/*    */ public class IORWholeNumberPanel
/*    */   extends JPanel
/*    */ {
/* 25 */   private WholeNumberField wnf = null;
/* 26 */   private JLabel prompt = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IORWholeNumberPanel(int initial, int columns, String txt)
/*    */   {
/* 37 */     this.wnf = new WholeNumberField(initial, columns);
/* 38 */     this.prompt = new JLabel(txt);
/* 39 */     add(this.prompt);
/* 40 */     add(this.wnf);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public JLabel getPromptLabel()
/*    */   {
/* 47 */     return this.prompt;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public WholeNumberField getWholeNumberField()
/*    */   {
/* 54 */     return this.wnf;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORWholeNumberPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */