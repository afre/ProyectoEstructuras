/*    */ import java.io.Serializable;
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
/*    */ class ORNLPSolver$FrankWolfeType
/*    */   implements Serializable
/*    */ {
/*    */   private ORNLPSolver$FrankWolfeType(ORNLPSolver this$0) {}
/*    */   
/* 61 */   ORNLPSolver$FrankWolfeType(ORNLPSolver x$0, ORNLPSolver..1 x$1) { this(x$0); }
/* 62 */   double[] Solution = new double[11];
/* 63 */   double[] ObjectiveFunction = new double[11];
/* 64 */   double[] FWLPSolution = new double[11];
/* 65 */   double[] NewSolution = new double[11];
/* 66 */   double[] Vector1 = new double[11];
/* 67 */   double[] Vector2 = new double[11];
/* 68 */   double[] Vector3 = new double[11];
/* 69 */   double[] a = new double[11];
/* 70 */   double[] b = new double[11];
/*    */   double t;
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORNLPSolver$FrankWolfeType.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */