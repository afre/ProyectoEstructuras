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
/*    */ public class ObjectiveFunction
/*    */   extends ORNLPSolver
/*    */ {
/*    */   private ORSolverBase.SimplexModelType sModel;
/*    */   private ORSolverBase.PolynomialType polynomial;
/*    */   private double[] variable;
/*    */   
/*    */   public ObjectiveFunction(ORSolverBase.PolynomialType pType, ORSolverBase.SimplexModelType sType)
/*    */   {
/* 20 */     this.sModel = sType;
/* 21 */     this.polynomial = pType;
/*    */   }
/*    */   
/*    */   public double[] variableArea() {
/* 25 */     int[] temp = this.sModel.Annealling;
/* 26 */     double[] v = new double[temp.length];
/* 27 */     for (int i = 0; i < temp.length; i++) {
/* 28 */       v[i] = (temp[i] / 1.0D);
/*    */     }
/* 30 */     this.variable = v;
/* 31 */     return v;
/*    */   }
/*    */   
/*    */   public double[] initSolution() {
/* 35 */     double[] temp = new double[this.sModel.Annealling.length];
/* 36 */     for (int i = 0; i < this.variable.length; i++) {
/* 37 */       temp[i] = this.sModel.initSolution[i];
/*    */     }
/* 39 */     return temp;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected double evaluate(double[] Vector)
/*    */   {
/* 47 */     double TempSum = 0.0D;
/* 48 */     for (int i = 1; i <= this.polynomial.NumTerms; i++)
/*    */     {
/* 50 */       double Temp = this.polynomial.Coefficient[i];
/* 51 */       for (int j = 1; j <= this.polynomial.NumVariables; j++)
/*    */       {
/* 53 */         if (this.polynomial.Exponent[i][j] != 0)
/* 54 */           Temp *= Math.pow(Vector[(j - 1)], this.polynomial.Exponent[i][j]);
/*    */       }
/* 56 */       TempSum += Temp;
/*    */     }
/* 58 */     return TempSum;
/*    */   }
/*    */   
/*    */   protected boolean feasible(double[] temp)
/*    */   {
/* 63 */     for (int i = 0; i < this.sModel.NumVariables; i++) {
/* 64 */       if ((temp[i] > this.sModel.Annealling[i]) || (temp[i] < 0)) {
/* 65 */         return false;
/*    */       }
/*    */     }
/*    */     
/* 69 */     for (int i = 0; i < this.sModel.NumConstraints; i++) {
/* 70 */       int oneresult = 0;
/* 71 */       for (int j = 0; j < this.sModel.NumVariables; j++) {
/* 72 */         oneresult = (int)(oneresult + this.sModel.A[(i + 1)][(j + 1)] * temp[j]);
/*    */       }
/*    */       
/* 75 */       double judgeresult = oneresult - this.sModel.RightHandSide[(i + 1)];
/*    */       
/* 77 */       if (this.sModel.Constraint[(i + 1)] == 0) {
/* 78 */         if (judgeresult > 0) return false;
/*    */       }
/* 80 */       else if (this.sModel.Constraint[(i + 1)] == 1) {
/* 81 */         if (judgeresult != 0) return false;
/*    */       }
/* 83 */       else if ((this.sModel.Constraint[(i + 1)] == 2) && 
/* 84 */         (judgeresult < 0)) { return false;
/*    */       }
/*    */     }
/* 87 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ObjectiveFunction.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */