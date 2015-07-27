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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ORSolverBase$SimplexTableauType
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   double[] Multiple;
/*     */   double MultipleM;
/*     */   int EnteringBasicVariable;
/*     */   int LeavingBasicVariableEquation;
/*     */   int Objective;
/*     */   double[] ObjectiveFunction;
/*     */   double[] ObjectiveFunctionM;
/*     */   boolean[] AnArtificial;
/*     */   double Z;
/*     */   double ZM;
/*     */   double[][] T;
/*     */   double[] RightHandSide;
/*     */   double[] BasicVariable;
/*     */   boolean[] Nonnegativity;
/*     */   double[] LowerBound;
/*     */   double[] UpperBound;
/*     */   boolean[] AnUpperBound;
/*     */   boolean[] Reversed;
/*     */   int NumConstraints;
/*     */   int NumVariables;
/*     */   boolean ContainsArtificials;
/*     */   boolean ContainsConstant;
/*     */   double Constant;
/*     */   int VariableWithConstant;
/*     */   int[] Constraint;
/*     */   int[] VariableLocation;
/*     */   boolean Optimal;
/*     */   boolean Unbounded;
/*     */   boolean Feasible;
/*     */   boolean[] ABasicVariable;
/*     */   int NumBasicArtificials;
/*     */   private final ORSolverBase this$0;
/*     */   
/*     */   protected ORSolverBase$SimplexTableauType(ORSolverBase this$0)
/*     */   {
/* 374 */     this.this$0 = this$0;
/*     */     
/* 376 */     this.Multiple = new double[51];
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 381 */     this.ObjectiveFunction = new double[''];
/* 382 */     this.ObjectiveFunctionM = new double[''];
/* 383 */     this.AnArtificial = new boolean[''];
/*     */     
/*     */ 
/* 386 */     this.T = new double[51][''];
/*     */     
/* 388 */     this.RightHandSide = new double[51];
/* 389 */     this.BasicVariable = new double[51];
/* 390 */     this.Nonnegativity = new boolean[''];
/* 391 */     this.LowerBound = new double[''];
/* 392 */     this.UpperBound = new double[''];
/* 393 */     this.AnUpperBound = new boolean[''];
/* 394 */     this.Reversed = new boolean[''];
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 401 */     this.Constraint = new int[51];
/* 402 */     this.VariableLocation = new int[''];
/*     */     
/*     */ 
/*     */ 
/* 406 */     this.ABasicVariable = new boolean[''];
/*     */   }
/*     */   
/*     */   public Object clone() {
/*     */     SimplexTableauType localSimplexTableauType1;
/* 411 */     try { SimplexTableauType s = (SimplexTableauType)super.clone();
/*     */       
/* 413 */       s.Multiple = new double[51];
/* 414 */       s.ObjectiveFunction = new double[''];
/* 415 */       s.ObjectiveFunctionM = new double[''];
/* 416 */       s.AnArtificial = new boolean[''];
/* 417 */       s.T = new double[51][''];
/*     */       
/* 419 */       s.RightHandSide = new double[51];
/* 420 */       s.BasicVariable = new double[51];
/* 421 */       s.Nonnegativity = new boolean[''];
/* 422 */       s.LowerBound = new double[''];
/* 423 */       s.UpperBound = new double[''];
/* 424 */       s.AnUpperBound = new boolean[''];
/* 425 */       s.Reversed = new boolean[''];
/* 426 */       s.Constraint = new int[51];
/* 427 */       s.VariableLocation = new int[''];
/* 428 */       s.ABasicVariable = new boolean[''];
/*     */       
/* 430 */       this.this$0.arraycopy1(this.Multiple, s.Multiple);
/* 431 */       this.this$0.arraycopy1(this.ObjectiveFunction, s.ObjectiveFunction);
/* 432 */       this.this$0.arraycopy1(this.ObjectiveFunctionM, s.ObjectiveFunctionM);
/* 433 */       this.this$0.arraycopy1(this.AnArtificial, s.AnArtificial);
/* 434 */       this.this$0.arraycopy2(this.T, s.T);
/* 435 */       this.this$0.arraycopy1(this.RightHandSide, s.RightHandSide);
/* 436 */       this.this$0.arraycopy1(this.BasicVariable, s.BasicVariable);
/* 437 */       this.this$0.arraycopy1(this.Nonnegativity, s.Nonnegativity);
/* 438 */       this.this$0.arraycopy1(this.LowerBound, s.LowerBound);
/* 439 */       this.this$0.arraycopy1(this.UpperBound, s.UpperBound);
/* 440 */       this.this$0.arraycopy1(this.AnUpperBound, s.AnUpperBound);
/* 441 */       this.this$0.arraycopy1(this.Reversed, s.Reversed);
/* 442 */       this.this$0.arraycopy1(this.Constraint, s.Constraint);
/* 443 */       this.this$0.arraycopy1(this.VariableLocation, s.VariableLocation);
/* 444 */       this.this$0.arraycopy1(this.ABasicVariable, s.ABasicVariable);
/* 445 */       return s;
/*     */     }
/*     */     catch (CloneNotSupportedException e) {
/* 448 */       localSimplexTableauType1 = null; } return localSimplexTableauType1;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORSolverBase$SimplexTableauType.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */