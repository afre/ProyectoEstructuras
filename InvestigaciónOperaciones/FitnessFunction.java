/*    */ import java.io.PrintStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FitnessFunction
/*    */   extends ORTSPSolver
/*    */ {
/*    */   private ORSolverBase.SimplexModelType sModel;
/*    */   private ORSolverBase.PolynomialType polynomial;
/*    */   private int[] variable;
/*    */   
/*    */   public FitnessFunction(ORSolverBase.PolynomialType pType, ORSolverBase.SimplexModelType sType)
/*    */   {
/* 18 */     this.sModel = sType;
/* 19 */     this.polynomial = pType;
/*    */   }
/*    */   
/*    */   public int[] variableArea() {
/* 23 */     int[] temp = this.sModel.Annealling;
/* 24 */     int[] v = new int[temp.length];
/* 25 */     for (int i = 0; i < temp.length; i++) {
/* 26 */       v[i] = temp[i];
/*    */     }
/* 28 */     this.variable = v;
/* 29 */     return v;
/*    */   }
/*    */   
/*    */   public double[] initSolution() {
/* 33 */     double[] temp = new double[this.sModel.Annealling.length];
/* 34 */     for (int i = 0; i < this.variable.length; i++) {
/* 35 */       temp[i] = (this.variable[i] / 2);
/*    */     }
/* 37 */     return temp;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected double evaluate(int[] Vector)
/*    */   {
/* 45 */     double TempSum = 0.0D;
/* 46 */     for (int i = 1; i <= this.polynomial.NumTerms; i++)
/*    */     {
/* 48 */       double Temp = this.polynomial.Coefficient[i];
/* 49 */       for (int j = 1; j <= this.polynomial.NumVariables; j++)
/*    */       {
/* 51 */         if (this.polynomial.Exponent[i][j] != 0)
/* 52 */           Temp *= Math.pow(Vector[(j - 1)], this.polynomial.Exponent[i][j]);
/*    */       }
/* 54 */       TempSum += Temp;
/*    */     }
/* 56 */     return TempSum;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected boolean feasible(int[] temp)
/*    */   {
/* 65 */     for (int i = 0; i < this.sModel.NumVariables; i++)
/*    */     {
/* 67 */       if ((temp[i] > this.sModel.Annealling[i]) || (temp[i] < 0)) {
/* 68 */         System.out.println("=====================false=================");
/* 69 */         return false;
/*    */       }
/*    */     }
/* 72 */     for (int i = 0; i < this.sModel.NumConstraints; i++) {
/* 73 */       int oneresult = 0;
/* 74 */       for (int j = 0; j < this.sModel.NumVariables; j++) {
/* 75 */         oneresult = (int)(oneresult + this.sModel.A[(i + 1)][(j + 1)] * temp[j]);
/*    */       }
/*    */       
/* 78 */       double judgeresult = oneresult - this.sModel.RightHandSide[(i + 1)];
/*    */       
/* 80 */       if (this.sModel.Constraint[(i + 1)] == 0) {
/* 81 */         if (judgeresult > 0) return false;
/*    */       }
/* 83 */       else if (this.sModel.Constraint[(i + 1)] == 1) {
/* 84 */         if (judgeresult != 0) return false;
/*    */       }
/* 86 */       else if ((this.sModel.Constraint[(i + 1)] == 2) && 
/* 87 */         (judgeresult < 0)) { return false;
/*    */       }
/*    */     }
/* 90 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\FitnessFunction.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */