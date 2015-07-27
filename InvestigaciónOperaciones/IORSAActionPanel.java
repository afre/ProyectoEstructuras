/*    */ import java.util.Vector;
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
/*    */ public abstract class IORSAActionPanel
/*    */   extends IORActionPanel
/*    */ {
/* 29 */   public IORSAProcessController controller = null;
/*    */   
/*    */ 
/*    */   protected int step;
/*    */   
/* 34 */   public Vector operation_sequence = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IORSAActionPanel(IORSAProcessController c)
/*    */   {
/* 44 */     super(c);
/* 45 */     this.controller = c;
/* 46 */     this.operation_sequence = new Vector();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getStep()
/*    */   {
/* 53 */     return this.step;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void addStep()
/*    */   {
/* 60 */     this.operation_sequence.addElement(new Integer(getStep()));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setStep(int s)
/*    */   {
/* 67 */     this.step = s;
/*    */   }
/*    */   
/*    */   protected void backStep() {}
/*    */   
/*    */   public void updatePanel() {}
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORSAActionPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */