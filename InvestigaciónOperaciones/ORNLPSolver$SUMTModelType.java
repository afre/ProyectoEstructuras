/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ORNLPSolver$SUMTModelType
/*     */   implements Serializable
/*     */ {
/*     */   int NumVariableswlb;
/*     */   int NumVariableswolb;
/*     */   int NumVariables;
/*     */   int NumInequalityConstraints;
/*     */   int NumEqualityConstraints;
/*     */   int NumConstraints;
/*     */   ORSolverBase.PolynomialType[] B;
/*     */   ORSolverBase.PolynomialType[] L;
/*     */   ORSolverBase.PolynomialType f;
/*     */   private final ORNLPSolver this$0;
/*     */   
/*     */   public ORNLPSolver$SUMTModelType(ORNLPSolver this$0)
/*     */   {
/* 119 */     this.this$0 = this$0;this.B = new ORSolverBase.PolynomialType[6];this.L = new ORSolverBase.PolynomialType[6];this.f = new ORSolverBase.PolynomialType(this.this$0);
/*     */     
/* 121 */     for (int i = 0; i < 6; i++) {
/* 122 */       ORNLPSolver tmp60_59 = this$0;tmp60_59.getClass();this.B[i] = new ORSolverBase.PolynomialType(tmp60_59); ORNLPSolver 
/* 123 */         tmp79_78 = this$0;tmp79_78.getClass();this.L[i] = new ORSolverBase.PolynomialType(tmp79_78);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORNLPSolver$SUMTModelType.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */