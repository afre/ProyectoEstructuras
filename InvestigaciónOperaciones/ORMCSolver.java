/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ORMCSolver
/*     */   extends ORSolverBase
/*     */ {
/*  28 */   private ORSolverBase.TransitionMatrixType TransitionMatrix = new ORSolverBase.TransitionMatrixType(this);
/*     */   private int ChapmanExponent;
/*  30 */   private double[][] ChapmanMatrix = new double[11][11];
/*  31 */   private double[][] SteadyStateMatrix = new double[11][11];
/*  32 */   private double[] SteadyStateRHS = new double[11];
/*  33 */   private double[] SteadyStateVector = new double[11];
/*     */   private boolean SteadyStateSingular;
/*     */   
/*     */   public boolean doWork(IOROperation op, IORMCProcessController.MCData data)
/*     */   {
/*  38 */     boolean success = doWork(op);
/*     */     
/*  40 */     if (success) {
/*  41 */       getData(data);
/*     */     }
/*  43 */     return success;
/*     */   }
/*     */   
/*     */   public void getData(IORMCProcessController.MCData data)
/*     */   {
/*  48 */     data.max_matrix_dimension_supply = 10;
/*  49 */     data.max_power_supply = 99;
/*  50 */     data.matrix_dimension = this.TransitionMatrix.Size;
/*  51 */     data.power = this.ChapmanExponent;
/*     */     
/*  53 */     if (data.matrix == null) data.matrix = new double[11][11];
/*  54 */     if (data.raised_matrix == null) data.raised_matrix = new double[11][11];
/*  55 */     if (data.coefficient_matrix == null) data.coefficient_matrix = new double[11][11];
/*  56 */     if (data.unit_matrix == null) data.unit_matrix = new double[11];
/*  57 */     if (data.result_matrix == null) { data.result_matrix = new double[11];
/*     */     }
/*     */     
/*  60 */     for (int i = 0; i < this.TransitionMatrix.Size; i++) {
/*  61 */       data.unit_matrix[i] = this.SteadyStateRHS[(i + 1)];
/*  62 */       data.result_matrix[i] = this.SteadyStateVector[(i + 1)];
/*  63 */       for (int j = 0; j < this.TransitionMatrix.Size; j++) {
/*  64 */         data.matrix[i][j] = this.TransitionMatrix.Prob[(i + 1)][(j + 1)];
/*  65 */         data.raised_matrix[i][j] = this.ChapmanMatrix[(i + 1)][(j + 1)];
/*  66 */         data.coefficient_matrix[i][j] = this.SteadyStateMatrix[(i + 1)][(j + 1)];
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean doWork(IOROperation op)
/*     */   {
/*  75 */     Vector p = op.parameters;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  80 */     switch (op.operation_code)
/*     */     {
/*     */     case 10101: 
/*  83 */       this.TransitionMatrix.Size = ((Integer)p.elementAt(0)).intValue();
/*  84 */       double[][] daa = (double[][])p.elementAt(1);
/*  85 */       for (int i = 0; i < this.TransitionMatrix.Size; i++)
/*     */       {
/*  87 */         double Temp = 0.0D;
/*  88 */         for (int j = 0; j < this.TransitionMatrix.Size; j++) {
/*  89 */           Temp += daa[i][j];
/*  90 */           if (daa[i][j] < 0) {
/*  91 */             this.errInfo = "All entries have to be nonnegative!";
/*  92 */             return false;
/*     */           }
/*     */         }
/*     */         
/*  96 */         if (Math.abs(Temp - 1.0D) > 1.0E-4D) {
/*  97 */           this.errInfo = "The transition matrix is not valid.";
/*  98 */           return false;
/*     */         }
/*     */       }
/* 101 */       this.ChapmanExponent = 1;
/* 102 */       arraycopy2(this.TransitionMatrix.Prob, this.ChapmanMatrix);
/*     */       
/* 104 */       for (i = 0; i < this.TransitionMatrix.Size; i++) {
/* 105 */         for (int j = 0; j < this.TransitionMatrix.Size; j++) {
/* 106 */           this.TransitionMatrix.Prob[(i + 1)][(j + 1)] = daa[i][j];
/*     */         }
/*     */       }
/* 109 */       for (i = 1; i <= this.TransitionMatrix.Size - 1; i++) {
/* 110 */         for (int j = 1; j <= this.TransitionMatrix.Size; j++) {
/* 111 */           if (i == j) {
/* 112 */             this.SteadyStateMatrix[i][j] = (this.TransitionMatrix.Prob[j][i] - 1);
/*     */           } else
/* 114 */             this.SteadyStateMatrix[i][j] = this.TransitionMatrix.Prob[j][i];
/*     */         }
/*     */       }
/* 117 */       for (i = 1; i <= this.TransitionMatrix.Size; i++)
/* 118 */         this.SteadyStateMatrix[this.TransitionMatrix.Size][i] = 1.0D;
/* 119 */       for (i = 1; i <= this.TransitionMatrix.Size - 1; i++)
/* 120 */         this.SteadyStateRHS[i] = 0.0D;
/* 121 */       this.SteadyStateRHS[this.TransitionMatrix.Size] = 1.0D;
/* 122 */       this.SteadyStateVector = new double[11];
/* 123 */       this.SteadyStateSingular = SolveSystem(this.SteadyStateMatrix, this.SteadyStateRHS, this.TransitionMatrix.Size, this.SteadyStateVector);
/* 124 */       break;
/*     */     case 10102: 
/* 126 */       this.ChapmanExponent = ((Integer)p.elementAt(0)).intValue();
/* 127 */       arraycopy2(this.TransitionMatrix.Prob, this.ChapmanMatrix);
/* 128 */       for (int i = 1; i <= this.ChapmanExponent - 1; i++) {
/* 129 */         MultiplyMatrices(this.ChapmanMatrix, this.TransitionMatrix.Prob, this.TransitionMatrix.Size, this.TransitionMatrix.Size, this.TransitionMatrix.Size, this.ChapmanMatrix);
/*     */       }
/*     */     }
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   protected void ORPrint()
/*     */   {
/* 137 */     if (this.procedure == 1) {
/* 138 */       PrintStochasticEnter();
/* 139 */     } else if (this.procedure == 2) {
/* 140 */       PrintStochasticChapman();
/* 141 */     } else if (this.procedure == 3) {
/* 142 */       PrintStochasticSteadyState();
/*     */     }
/*     */   }
/*     */   
/*     */   private void PrintStochasticEnter() {
/* 147 */     PrintLine("Markov chains");
/* 148 */     SkipLine();
/* 149 */     PrintLine("Transition Matrix");
/* 150 */     SkipLine();
/* 151 */     PrintLine("P=");
/* 152 */     SkipLine();
/* 153 */     PrintMatrix(this.TransitionMatrix.Prob, this.TransitionMatrix.Size, this.TransitionMatrix.Size, 5);
/*     */   }
/*     */   
/*     */   private void PrintStochasticChapman() {
/* 157 */     PrintLine("Markov chains");
/* 158 */     SkipLine();
/* 159 */     PrintLine("Transition Matrix");
/* 160 */     SkipLine();
/* 161 */     PrintLine("P=");
/* 162 */     SkipLine();
/* 163 */     PrintMatrix(this.TransitionMatrix.Prob, this.TransitionMatrix.Size, this.TransitionMatrix.Size, 5);
/* 164 */     Skip(3);
/* 165 */     PrintLine("Chapman-Kolmogorov Equations");
/* 166 */     SkipLine();
/* 167 */     PrintLine(String.valueOf(String.valueOf(new StringBuffer("P^").append(IntegerToString(this.ChapmanExponent, 2)).append("="))));
/* 168 */     SkipLine();
/* 169 */     PrintMatrix(this.ChapmanMatrix, this.TransitionMatrix.Size, this.TransitionMatrix.Size, 5);
/*     */   }
/*     */   
/*     */   private void PrintStochasticSteadyState()
/*     */   {
/* 174 */     PrintLine("Markov chains");
/* 175 */     SkipLine();
/* 176 */     PrintLine("Transition Matrix");
/* 177 */     SkipLine();
/* 178 */     PrintLine("P=");
/* 179 */     SkipLine();
/* 180 */     PrintMatrix(this.TransitionMatrix.Prob, this.TransitionMatrix.Size, this.TransitionMatrix.Size, 5);
/* 181 */     Skip(3);
/* 182 */     PrintLine("Steady State Probabilities");
/* 183 */     SkipLine();
/* 184 */     for (int i = 1; i <= this.TransitionMatrix.Size; i++) {
/* 185 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("PI(").append(IntegerToString(i - 1, 1)).append(") = ").append(RealToString(this.SteadyStateVector[i], 5)))));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORMCSolver.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */