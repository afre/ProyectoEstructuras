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
/*     */ public class ORQTSolver
/*     */   extends ORSolverBase
/*     */ {
/*  27 */   private double[][] Jackson = new double[7][7];
/*     */   private int JacksonSize;
/*  29 */   private double[] QLambda1 = new double[9];
/*     */   
/*     */   private String arrivalRate;
/*     */   private String serviceRate;
/*     */   private String ns1Num;
/*     */   private String ns2Num;
/*     */   private String lqStr;
/*     */   private String lsStr;
/*     */   private String wqStr;
/*     */   private String wsStr;
/*     */   private int phaseNum;
/*     */   private boolean isShowData;
/*     */   
/*     */   private void QueueComputeJackson()
/*     */   {
/*  44 */     double[][] A = new double[11][11];
/*  45 */     double[] b = new double[11];
/*     */     
/*     */ 
/*     */ 
/*  49 */     for (int i = 1; i <= this.JacksonSize; i++) {
/*  50 */       for (int j = 1; j <= this.JacksonSize; j++) {
/*  51 */         A[i][j] = this.Jackson[i][j];
/*  52 */         if (i == j)
/*  53 */           A[i][j] -= 1;
/*     */       }
/*     */     }
/*  56 */     for (i = 1; i <= this.JacksonSize; i++)
/*  57 */       b[i] = (-this.Jackson[i][0]);
/*  58 */     boolean Singular = SolveSystem(A, b, this.JacksonSize, b);
/*  59 */     for (i = 1; i <= this.JacksonSize; i++) {
/*  60 */       this.QLambda1[i] = b[i];
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean doWork(IOROperation op, IORQTProcessController.QTData data)
/*     */   {
/*  66 */     boolean success = doWork(op);
/*     */     
/*  68 */     if (success) {
/*  69 */       getData(data);
/*     */     }
/*  71 */     return success;
/*     */   }
/*     */   
/*     */   public void getData(IORQTProcessController.QTData data)
/*     */   {
/*  76 */     data.num_facty = this.JacksonSize;
/*  77 */     if (data.a == null)
/*  78 */       data.a = new double[6];
/*  79 */     if (data.p == null)
/*  80 */       data.p = new double[6][6];
/*  81 */     if (data.y == null) {
/*  82 */       data.y = new double[6];
/*     */     }
/*     */     
/*  85 */     for (int i = 0; i < this.JacksonSize; i++) {
/*  86 */       data.a[i] = this.Jackson[(i + 1)][0];
/*  87 */       data.y[i] = this.QLambda1[(i + 1)];
/*  88 */       for (int j = 0; j < this.JacksonSize; j++) {
/*  89 */         data.p[i][j] = this.Jackson[(i + 1)][(j + 1)];
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean doWork(IOROperation op) {
/*  95 */     Vector p = op.parameters;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 100 */     switch (op.operation_code) {
/*     */     case 20101: 
/* 102 */       this.JacksonSize = ((Integer)p.elementAt(0)).intValue();
/* 103 */       double[] d1 = (double[])p.elementAt(1);
/* 104 */       double[][] d2 = (double[][])p.elementAt(2);
/*     */       
/* 106 */       for (int i = 0; i < this.JacksonSize; i++) {
/* 107 */         this.Jackson[(i + 1)][0] = d1[i];
/* 108 */         for (int j = 0; j < this.JacksonSize; j++)
/* 109 */           this.Jackson[(i + 1)][(j + 1)] = d2[j][i];
/*     */       }
/* 111 */       QueueComputeJackson();
/* 112 */       break;
/*     */     }
/* 114 */     return true;
/*     */   }
/*     */   
/*     */   protected void ORPrint()
/*     */   {
/* 119 */     if (this.procedure == 1) {
/* 120 */       PrintQueue3();
/*     */     }
/* 122 */     else if (this.procedure == 2) {
/* 123 */       PrintWaitingLine();
/*     */     }
/*     */   }
/*     */   
/*     */   public void initWaitingLinePrintInfo(IORQTProcessController.QTData data)
/*     */   {
/* 129 */     this.arrivalRate = data.arrivalRate;
/* 130 */     this.serviceRate = data.serviceRate;
/* 131 */     this.ns1Num = data.ns1Num;
/* 132 */     this.ns2Num = data.ns2Num;
/* 133 */     this.phaseNum = data.phaseNum;
/* 134 */     this.lqStr = data.lqStr;
/* 135 */     this.lsStr = data.lsStr;
/* 136 */     this.wqStr = data.wqStr;
/* 137 */     this.wsStr = data.wsStr;
/* 138 */     this.isShowData = data.isShowData;
/*     */   }
/*     */   
/*     */   private void PrintWaitingLine() {
/* 142 */     PrintLine("Waiting Line:");
/* 143 */     SkipLine();
/* 144 */     if (this.phaseNum == 1) {
/* 145 */       PrintLine("One phase:");
/* 146 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.arrivalRate))).append("      ").append(this.serviceRate).append("       ").append(this.ns1Num))));
/* 147 */       SkipLine();
/* 148 */       if (this.isShowData)
/* 149 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.lqStr))).append("      ").append(this.lsStr).append("      ").append(this.wqStr).append("       ").append(this.wsStr))));
/*     */     } else {
/* 151 */       PrintLine("Two phases:");
/* 152 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.arrivalRate))).append("      ").append(this.serviceRate).append("       ").append(this.ns1Num).append("      ").append(this.ns2Num))));
/* 153 */       SkipLine();
/* 154 */       if (this.isShowData) {
/* 155 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.lqStr))).append("      ").append(this.lsStr).append("      ").append(this.wqStr).append("       ").append(this.wsStr))));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void PrintQueue3()
/*     */   {
/* 163 */     PrintLine("Jackson Network:");
/* 164 */     Skip(2);
/* 165 */     TabPrintLine(21 + (int)Math.round(3.5D * this.JacksonSize), "p(ij)");
/* 166 */     SkipLine();
/* 167 */     Print("Facility j |  a(j)   |");
/* 168 */     for (int i = 1; i <= this.JacksonSize; i++)
/* 169 */       TabPrint(18 + 7 * i, "i = ".concat(String.valueOf(String.valueOf(IntegerToString(i, 1)))));
/* 170 */     SkipLine();
/* 171 */     Print("___________|_________|__");
/* 172 */     for (i = 1; i <= this.JacksonSize; i++)
/* 173 */       Print("_______");
/* 174 */     SkipLine();
/* 175 */     PrintLine("           |         |");
/* 176 */     for (i = 1; i <= this.JacksonSize; i++) {
/* 177 */       Print(String.valueOf(String.valueOf(new StringBuffer("  j = ").append(IntegerToString(i, 1)).append("    |  ").append(FormatReal(this.Jackson[i][0], 5, 0, 0)).append("  |  "))));
/*     */       
/* 179 */       for (int j = 1; j <= this.JacksonSize; j++)
/* 180 */         Print(String.valueOf(String.valueOf(FormatReal(this.Jackson[i][j], 5, 0, 0))).concat("  "));
/* 181 */       SkipLine();
/*     */     }
/* 183 */     Skip(3);
/* 184 */     for (i = 1; i <= this.JacksonSize; i++) {
/* 185 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("Lambda(").append(IntegerToString(i, 1)).append(") = ").append(FormatReal(this.QLambda1[i], 8, 0, 0)))));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORQTSolver.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */