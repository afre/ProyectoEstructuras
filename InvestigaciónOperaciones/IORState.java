/*    */ import java.io.Serializable;
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
/*    */ public class IORState
/*    */   implements Serializable
/*    */ {
/*    */   private Vector steps;
/* 27 */   private ORSolverBase solver = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IORState(ORSolverBase s)
/*    */   {
/* 34 */     this.steps = new Vector();
/* 35 */     this.solver = s;
/* 36 */     setSolverStepVector();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isEmpty()
/*    */   {
/* 43 */     return this.steps.isEmpty();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void back()
/*    */   {
/* 50 */     if (this.steps.isEmpty()) {
/* 51 */       return;
/*    */     }
/*    */     
/* 54 */     this.steps.remove(this.steps.size() - 1);
/* 55 */     for (int i = 0; i < this.steps.size(); i++) {
/* 56 */       this.solver.doWork((IOROperation)this.steps.elementAt(i));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void addStep(IOROperation op)
/*    */   {
/* 64 */     this.steps.addElement(op);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void reset()
/*    */   {
/* 71 */     this.steps.removeAllElements();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setSolverStepVector()
/*    */   {
/* 78 */     this.solver.setStepVector(this.steps);
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORState.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */