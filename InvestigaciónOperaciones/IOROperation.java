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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IOROperation
/*    */   implements Serializable
/*    */ {
/*    */   public int operation_code;
/*    */   public Vector parameters;
/*    */   
/*    */   public IOROperation(int code, Vector v)
/*    */   {
/* 37 */     this.operation_code = code;
/* 38 */     this.parameters = v;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IOROperation.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */