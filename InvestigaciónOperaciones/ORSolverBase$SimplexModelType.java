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
/*     */ public class ORSolverBase$SimplexModelType
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   int NumVariables;
/*     */   int NumConstraints;
/*     */   int Objective;
/*     */   int[] Annealling;
/*     */   float[] initSolution;
/*     */   double[] ObjectiveFunction;
/*     */   double[] ObjectiveFunctionM;
/*     */   double[][] A;
/*     */   double[] RightHandSide;
/*     */   int[] Constraint;
/*     */   boolean[] Nonnegativity;
/*     */   double[] LowerBound;
/*     */   double[] UpperBound;
/*     */   boolean[] AnUpperBound;
/*     */   private final ORSolverBase this$0;
/*     */   
/*     */   protected ORSolverBase$SimplexModelType(ORSolverBase this$0)
/*     */   {
/* 322 */     this.this$0 = this$0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 331 */     this.ObjectiveFunction = new double[51];
/* 332 */     this.ObjectiveFunctionM = new double[51];
/* 333 */     this.A = new double[51][51];
/*     */     
/* 335 */     this.RightHandSide = new double[51];
/* 336 */     this.Constraint = new int[51];
/* 337 */     this.Nonnegativity = new boolean[''];
/* 338 */     this.LowerBound = new double[51];
/* 339 */     this.UpperBound = new double[51];
/* 340 */     this.AnUpperBound = new boolean[51];
/*     */   }
/*     */   
/*     */   public Object clone() { SimplexModelType localSimplexModelType1;
/* 344 */     try { SimplexModelType s = (SimplexModelType)super.clone();
/*     */       
/* 346 */       s.ObjectiveFunction = new double[51];
/* 347 */       s.ObjectiveFunctionM = new double[51];
/* 348 */       s.A = new double[51][51];
/*     */       
/* 350 */       s.RightHandSide = new double[51];
/* 351 */       s.Constraint = new int[51];
/* 352 */       s.Nonnegativity = new boolean[''];
/* 353 */       s.LowerBound = new double[51];
/* 354 */       s.UpperBound = new double[51];
/* 355 */       s.AnUpperBound = new boolean[51];
/*     */       
/* 357 */       this.this$0.arraycopy1(this.ObjectiveFunction, s.ObjectiveFunction);
/* 358 */       this.this$0.arraycopy1(this.ObjectiveFunctionM, s.ObjectiveFunctionM);
/* 359 */       this.this$0.arraycopy2(this.A, s.A);
/* 360 */       this.this$0.arraycopy1(this.RightHandSide, s.RightHandSide);
/* 361 */       this.this$0.arraycopy1(this.Constraint, s.Constraint);
/* 362 */       this.this$0.arraycopy1(this.Nonnegativity, s.Nonnegativity);
/* 363 */       this.this$0.arraycopy1(this.LowerBound, s.LowerBound);
/* 364 */       this.this$0.arraycopy1(this.UpperBound, s.UpperBound);
/* 365 */       this.this$0.arraycopy1(this.AnUpperBound, s.AnUpperBound);
/* 366 */       return s;
/*     */     }
/*     */     catch (CloneNotSupportedException e) {
/* 369 */       localSimplexModelType1 = null; } return localSimplexModelType1;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORSolverBase$SimplexModelType.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */