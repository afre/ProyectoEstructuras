/*     */ import java.text.DecimalFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ORForecastSolver
/*     */   extends ORSolverBase
/*     */ {
/*  27 */   private float currAlpha = 0.4F; private float currBeta = 0.5F;
/*     */   
/*  29 */   public String[][] tableData = null;
/*  30 */   private DecimalFormat decfm = new DecimalFormat();
/*     */   private float aincept;
/*     */   private float bincept;
/*     */   
/*  34 */   public ORForecastSolver() { this.decfm.setGroupingUsed(false);
/*  35 */     this.decfm.setMaximumFractionDigits(2);
/*     */   }
/*     */   
/*     */   public void InitializeForecast(IORGraphicalProcessController.ForecastData data) {
/*  39 */     this.aincept = data.aincept;
/*  40 */     this.bincept = data.bincept;
/*  41 */     this.coef_x = data.coef_x;
/*  42 */     this.coef_y = data.coef_y;
/*  43 */     this.currAlpha = data.currAlpha;
/*  44 */     this.currBeta = data.currBeta;
/*  45 */     this.errMAD = data.errMAD;
/*  46 */     this.errMFE = data.errMFE;
/*  47 */     this.forecastingNum = data.forecastingNum;
/*  48 */     this.tableData = data.tableData;
/*  49 */     this.lastLooknum = data.lastLooknum;
/*     */   }
/*     */   
/*     */   private double D(double a, double b)
/*     */   {
/*  54 */     if (Math.abs(b) < 1.0E-6D) {
/*  55 */       return a * 10000000;
/*     */     }
/*  57 */     return a / b;
/*     */   }
/*     */   
/*  60 */   public boolean doWork(IOROperation op) { return true; }
/*     */   
/*     */ 
/*     */   private float coef_x;
/*     */   
/*     */   private float coef_y;
/*     */   
/*     */   private float errMAD;
/*     */   private float errMFE;
/*     */   public int forecastingNum;
/*     */   public int lastLooknum;
/*     */   public void reset() {}
/*     */   
/*     */   protected void initForecastPrintInfo(IORGraphicalProcessController.ForecastData data) {}
/*     */   
/*     */   protected void ORPrint()
/*     */   {
/*  77 */     PrintForecast();
/*     */   }
/*     */   
/*     */   private void PrintForecast() {
/*  81 */     int Width = 3;
/*  82 */     if (this.forecastingNum == 0) {
/*  83 */       PrintLine("Forecasting -- Exponential Smoothing Method");
/*     */       
/*  85 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("MAD = ").append(this.decfm.format(this.errMAD)).append("        MSE = ").append(this.decfm.format(this.errMFE)))));
/*  86 */       PrintLine("alpha = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.currAlpha)))));
/*  87 */     } else if (this.forecastingNum == 1) {
/*  88 */       PrintLine("Forecasting -- Exponential Smoothing with Trend");
/*     */       
/*  90 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("MAD = ").append(this.decfm.format(this.errMAD)).append("        MSE = ").append(this.decfm.format(this.errMFE)))));
/*  91 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("alpha = ").append(this.decfm.format(this.currAlpha)).append("       beta = ").append(this.decfm.format(this.currBeta)))));
/*  92 */     } else if (this.forecastingNum == 2) {
/*  93 */       PrintLine("Forecasting -- Linear Trend Method");
/*     */       
/*  95 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("MAD = ").append(this.decfm.format(this.errMAD)).append("        MSE = ").append(this.decfm.format(this.errMFE)))));
/*     */       
/*  97 */       if (this.bincept > 0.001D) {
/*  98 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("a = ").append(this.decfm.format(this.aincept)).append("      b = ").append(this.decfm.format(this.bincept)).append("       y = ").append(this.decfm.format(this.aincept)).append(" + ").append(this.decfm.format(this.bincept)).append("t "))));
/*     */       } else
/* 100 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("a = ").append(this.decfm.format(this.aincept)).append("      b = ").append(this.decfm.format(this.bincept)).append(" y = ").append(this.decfm.format(this.aincept)).append(" ").append(this.decfm.format(this.bincept)).append("t "))));
/* 101 */     } else if (this.forecastingNum == 3) {
/* 102 */       PrintLine("Forecasting -- Linear Regression Method");
/*     */       
/* 104 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("MAD = ").append(this.decfm.format(this.errMAD)).append("        MSE = ").append(this.decfm.format(this.errMFE)))));
/* 105 */       if (this.bincept > 0.001D) {
/* 106 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("a = ").append(this.decfm.format(this.coef_x)).append("      b = ").append(this.decfm.format(this.coef_y)).append("          y = ").append(this.decfm.format(this.coef_x)).append(" + ").append(this.decfm.format(this.coef_y)).append("x "))));
/*     */       } else
/* 108 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("a = ").append(this.decfm.format(this.coef_x)).append("      b = ").append(this.decfm.format(this.coef_y)).append("          y = ").append(this.decfm.format(this.coef_x)).append(" + ").append(this.decfm.format(this.coef_y)).append("x "))));
/* 109 */     } else if (this.forecastingNum == 4) {
/* 110 */       PrintLine("Forecasting -- Last Value Method");
/*     */       
/* 112 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("MAD = ").append(this.decfm.format(this.errMAD)).append("        MSE = ").append(this.decfm.format(this.errMFE)))));
/* 113 */     } else if (this.forecastingNum == 5) {
/* 114 */       PrintLine("Forecasting --Averaging Method");
/*     */       
/* 116 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("MAD = ").append(this.decfm.format(this.errMAD)).append("        MSE = ").append(this.decfm.format(this.errMFE)))));
/* 117 */     } else if (this.forecastingNum == 6) {
/* 118 */       PrintLine("Forecasting -- Moving-Average Method");
/*     */       
/* 120 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("MAD = ").append(this.decfm.format(this.errMAD)).append("        MSE = ").append(this.decfm.format(this.errMFE)))));
/* 121 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("Fetch average of last ").append(this.lastLooknum).append(" values"))));
/*     */     }
/*     */     
/*     */ 
/* 125 */     SkipLine();
/* 126 */     if (this.forecastingNum != 3) {
/* 127 */       String[] content = { "  Period  ", "  Data  ", " Forecast ", "  Error  " };
/* 128 */       int pos = 0;
/* 129 */       for (int i = 0; i < content.length; i++) {
/* 130 */         TabPrint(pos, content[i]);
/* 131 */         TabPrint(pos + Width + content[i].length(), "|");
/* 132 */         pos = pos + 2 * Width + content[i].length();
/*     */       }
/*     */       
/* 135 */       SkipLine();
/* 136 */       for (int i = 1; i <= this.tableData.length; i++) {
/* 137 */         pos = 0;
/* 138 */         for (int j = 0; j < this.tableData[0].length; j++) {
/* 139 */           TabPrint(pos, this.tableData[(i - 1)][j]);
/* 140 */           TabPrint(pos + Width + content[j].length(), "|");
/* 141 */           pos = pos + 2 * Width + content[j].length();
/*     */         }
/* 143 */         TabPrintLine(0, "");
/*     */       }
/*     */     }
/* 146 */     String[] content = { "      x    ", "    y    ", " Forecast ", "  Error  " };
/* 147 */     int pos = 0;
/* 148 */     for (int i = 0; i < content.length; i++) {
/* 149 */       TabPrint(pos, content[i]);
/* 150 */       TabPrint(pos + Width + content[i].length(), "|");
/* 151 */       pos = pos + 2 * Width + content[i].length();
/*     */     }
/*     */     
/* 154 */     SkipLine();
/*     */     
/* 156 */     for (int i = 1; i <= this.tableData.length; i++) {
/* 157 */       pos = 0;
/* 158 */       for (int j = 0; j < this.tableData[0].length; j++) {
/* 159 */         TabPrint(pos, this.tableData[(i - 1)][j]);
/* 160 */         TabPrint(pos + Width + content[j].length(), "|");
/* 161 */         pos = pos + 2 * Width + content[j].length();
/*     */       }
/* 163 */       TabPrintLine(0, "");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORForecastSolver.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */