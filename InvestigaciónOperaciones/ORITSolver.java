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
/*     */ public class ORITSolver
/*     */   extends ORSolverBase
/*     */ {
/*     */   private int InventoryDensity;
/*     */   private double Inventoryc;
/*     */   private double Inventoryp;
/*     */   private double Inventoryh;
/*     */   private double InventoryDF;
/*     */   private double InventoryK;
/*     */   private double InventoryX0;
/*     */   private double InventoryX1;
/*     */   private double Inventoryt;
/*     */   private double InventoryLambda;
/*     */   private double InventoryBigS;
/*     */   private double InventoryLittleS;
/*     */   private double InventoryY0;
/*     */   private double InventoryY1;
/*     */   private double Inventorya;
/*     */   private double InventoryS;
/*     */   private double InventoryQ;
/*  34 */   private double EOQ_H = 9.0D;
/*  35 */   private double EOQ_D = 360.0D;
/*  36 */   private double EOQ_S = 15.0D;
/*  37 */   private int Qstar = 312;
/*  38 */   private int Qy = 35;
/*     */   
/*     */ 
/*     */   public ORITSolver()
/*     */   {
/*  43 */     InitializeInventory();
/*     */   }
/*     */   
/*     */   private void InitializeInventory() {
/*  47 */     this.Inventoryc = 1.0D;
/*  48 */     this.Inventoryp = 1.0D;
/*  49 */     this.Inventoryh = 1.0D;
/*  50 */     this.InventoryDF = 0.9D;
/*  51 */     this.Inventorya = 1.0D;
/*  52 */     this.InventoryK = 1.0D;
/*  53 */     this.InventoryX0 = 0.0D;
/*  54 */     this.InventoryX1 = 1.0D;
/*  55 */     this.Inventoryt = 1.0D;
/*  56 */     this.InventoryLambda = 1.0D;
/*  57 */     this.InventoryBigS = 0.0D;
/*  58 */     this.InventoryLittleS = 0.0D;
/*  59 */     this.InventoryY0 = 0.0D;
/*  60 */     this.InventoryY1 = 0.0D;
/*  61 */     this.InventoryS = 0.0D;
/*  62 */     this.InventoryQ = 0.0D;
/*     */   }
/*     */   
/*     */   private double D(double a, double b) {
/*  66 */     if (Math.abs(b) < 1.0E-6D) {
/*  67 */       return a * 10000000;
/*     */     }
/*  69 */     return a / b;
/*     */   }
/*     */   
/*     */   private void InventoryComputeSinglePeriodNoSetup() {
/*  73 */     if (this.InventoryDensity == 0) {
/*  74 */       this.InventoryY0 = (-this.InventoryLambda * Math.log(D(this.Inventoryh + this.Inventoryc, this.Inventoryp + this.Inventoryh)));
/*     */     }
/*     */     else {
/*  77 */       this.InventoryY0 = (D(this.Inventoryp - this.Inventoryc, this.Inventoryp + this.Inventoryh) * this.Inventoryt + this.InventoryX0);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void InventoryComputeSinglePeriodWithSetup()
/*     */   {
/*  86 */     if (this.InventoryDensity == 0) {
/*  87 */       this.InventoryBigS = (this.InventoryLambda * Math.log(D(this.Inventoryh + this.Inventoryp, this.Inventoryh + this.Inventoryc)));
/*     */       
/*  89 */       double Delta = Math.sqrt(2 * this.InventoryLambda * D(this.InventoryK, this.Inventoryc + this.Inventoryh));
/*     */       
/*  91 */       int i = 1;
/*     */       double Value1;
/*  93 */       do { Value1 = Math.exp(D(Delta, this.InventoryLambda)) - D(this.InventoryK, this.InventoryLambda * (this.Inventoryc + this.Inventoryh)) - D(Delta, this.InventoryLambda) - 1;
/*     */         
/*     */ 
/*  96 */         Delta += 0.01D;
/*  97 */         double Value2 = Math.exp(D(Delta, this.InventoryLambda)) - D(this.InventoryK, this.InventoryLambda * (this.Inventoryc + this.Inventoryh)) - D(Delta, this.InventoryLambda) - 1;
/*     */         
/*     */ 
/* 100 */         double Derivative = D(Value2 - Value1, 0.01D);
/* 101 */         Delta -= D(Value1, Derivative);
/* 102 */         i += 1;
/*     */       }
/* 104 */       while ((Value1 >= 0.01D) && (i != 30));
/* 105 */       this.InventoryLittleS = (this.InventoryBigS - Delta);
/*     */     }
/*     */     else {
/* 108 */       this.InventoryBigS = (D(this.Inventoryp - this.Inventoryc, this.Inventoryp + this.Inventoryh) * this.Inventoryt + this.InventoryX0);
/*     */       
/*     */ 
/* 111 */       double D1 = this.InventoryK + this.Inventoryc * (D(this.Inventoryp - this.Inventoryc, this.Inventoryp + this.Inventoryh) * this.Inventoryt + this.InventoryX0) + D(0.5D * (this.Inventoryp * Math.pow(this.InventoryX1 - this.InventoryBigS, 2.0D) + this.Inventoryh * Math.pow(this.InventoryBigS - this.InventoryX0, 2.0D)), this.Inventoryt);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 119 */       double E = D(this.Inventoryp * Math.pow(this.InventoryX1, 2.0D) + this.Inventoryh * Math.pow(this.InventoryX0, 2.0D), 2 * this.Inventoryt) - D1;
/*     */       
/*     */ 
/* 122 */       this.InventoryLittleS = (this.InventoryBigS - Math.sqrt(Math.pow(this.InventoryBigS, 2.0D) - 2 * E * D(this.Inventoryt, this.Inventoryp + this.Inventoryh)));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void InventoryComputeTwoPeriod()
/*     */   {
/* 133 */     this.InventoryX0 = 0.0D;
/*     */     
/* 135 */     InventoryComputeSinglePeriodNoSetup();
/* 136 */     this.InventoryY1 = this.InventoryY0;
/* 137 */     if (this.InventoryDensity == 0) {
/* 138 */       double Z = 0.0D;
/* 139 */       for (int i = 1; i <= 10; i++) {
/* 140 */         double A = this.Inventoryh + this.Inventoryc + (this.Inventoryp + this.Inventoryh) * exp1(-D(this.InventoryY1, this.InventoryLambda));
/*     */         
/*     */ 
/* 143 */         double B = (this.Inventoryp + this.Inventoryh) * exp1(-D(this.InventoryY1, this.InventoryLambda));
/*     */         
/* 145 */         double C = 2 * this.Inventoryh + this.Inventoryc;
/* 146 */         double Derivative = -A * exp1(-Z) - B * Z * exp1(-Z) + B * exp1(-Z);
/* 147 */         double Value = exp1(-Z) * (A + B * Z) - C;
/* 148 */         Z -= D(Value, Derivative);
/*     */       }
/* 150 */       this.InventoryY0 = (this.InventoryLambda * Z + this.InventoryY1);
/*     */     }
/*     */     else {
/* 153 */       this.InventoryY0 = (Math.sqrt(Math.pow(this.InventoryY1, 2.0D) + D(2 * this.Inventoryt * (this.Inventoryc - this.Inventoryp), this.Inventoryp + this.Inventoryh) * this.InventoryY1 + D(Math.pow(this.Inventoryt, 2.0D) * (2 * this.Inventoryp * (this.Inventoryp + this.Inventoryh) + Math.pow(this.Inventoryh + this.Inventoryc, 2.0D)), Math.pow(this.Inventoryp + this.Inventoryh, 2.0D))) - D(this.Inventoryt * (this.Inventoryh + this.Inventoryc), this.Inventoryp + this.Inventoryh));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void InventoryComputeInfinitePeriod()
/*     */   {
/* 167 */     if (this.InventoryDensity == 0) {
/* 168 */       this.InventoryY0 = (-this.InventoryLambda * Math.log(D(this.Inventoryh + this.Inventoryc * (1 - this.InventoryDF), this.Inventoryp + this.Inventoryh)));
/*     */     }
/*     */     else
/*     */     {
/* 172 */       this.InventoryY0 = (D(this.Inventoryp - this.Inventoryc * (1 - this.InventoryDF), this.Inventoryp + this.Inventoryh) * this.Inventoryt);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean doWork(IOROperation op, IORITProcessController.ITData data)
/*     */   {
/* 181 */     boolean success = doWork(op);
/*     */     
/* 183 */     if (success) {
/* 184 */       getData(data);
/*     */     }
/* 186 */     return success;
/*     */   }
/*     */   
/*     */ 
/*     */   public void reset()
/*     */   {
/* 192 */     InitializeInventory();
/*     */   }
/*     */   
/*     */   public void getData(IORITProcessController.ITData data)
/*     */   {
/* 197 */     data.y0 = this.InventoryY0;
/* 198 */     data.S = this.InventoryBigS;
/* 199 */     data.s = this.InventoryLittleS;
/* 200 */     data.y10 = this.InventoryY0;
/* 201 */     data.y20 = this.InventoryY1;
/*     */   }
/*     */   
/*     */   public boolean doWork(IOROperation op)
/*     */   {
/* 206 */     Vector p = op.parameters;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 211 */     switch (op.operation_code) {
/*     */     case 30101: 
/* 213 */       this.InventoryDensity = 0;
/* 214 */       this.InventoryLambda = ((Double)p.elementAt(0)).doubleValue();
/* 215 */       this.Inventoryc = ((Double)p.elementAt(1)).doubleValue();
/* 216 */       this.Inventoryh = ((Double)p.elementAt(2)).doubleValue();
/* 217 */       this.Inventoryp = ((Double)p.elementAt(3)).doubleValue();
/* 218 */       InventoryComputeSinglePeriodNoSetup();
/* 219 */       break;
/*     */     case 30102: 
/* 221 */       this.InventoryDensity = 0;
/* 222 */       this.InventoryLambda = ((Double)p.elementAt(0)).doubleValue();
/* 223 */       this.Inventoryc = ((Double)p.elementAt(1)).doubleValue();
/* 224 */       this.Inventoryh = ((Double)p.elementAt(2)).doubleValue();
/* 225 */       this.Inventoryp = ((Double)p.elementAt(3)).doubleValue();
/* 226 */       this.InventoryK = ((Double)p.elementAt(4)).doubleValue();
/* 227 */       InventoryComputeSinglePeriodWithSetup();
/* 228 */       break;
/*     */     case 30103: 
/* 230 */       this.InventoryDensity = 0;
/* 231 */       this.InventoryLambda = ((Double)p.elementAt(0)).doubleValue();
/* 232 */       this.Inventoryc = ((Double)p.elementAt(1)).doubleValue();
/* 233 */       this.Inventoryh = ((Double)p.elementAt(2)).doubleValue();
/* 234 */       this.Inventoryp = ((Double)p.elementAt(3)).doubleValue();
/* 235 */       InventoryComputeTwoPeriod();
/* 236 */       break;
/*     */     case 30104: 
/* 238 */       this.InventoryDensity = 0;
/* 239 */       this.InventoryLambda = ((Double)p.elementAt(0)).doubleValue();
/* 240 */       this.Inventoryc = ((Double)p.elementAt(1)).doubleValue();
/* 241 */       this.Inventoryh = ((Double)p.elementAt(2)).doubleValue();
/* 242 */       this.Inventoryp = ((Double)p.elementAt(3)).doubleValue();
/* 243 */       this.InventoryDF = ((Double)p.elementAt(4)).doubleValue();
/* 244 */       InventoryComputeInfinitePeriod();
/* 245 */       break;
/*     */     case 30201: 
/* 247 */       this.InventoryDensity = 1;
/* 248 */       this.InventoryX0 = ((Double)p.elementAt(0)).doubleValue();
/* 249 */       this.InventoryX1 = ((Double)p.elementAt(1)).doubleValue();
/* 250 */       this.Inventoryc = ((Double)p.elementAt(2)).doubleValue();
/* 251 */       this.Inventoryh = ((Double)p.elementAt(3)).doubleValue();
/* 252 */       this.Inventoryp = ((Double)p.elementAt(4)).doubleValue();
/*     */       
/* 254 */       this.Inventoryt = (this.InventoryX1 - this.InventoryX0);
/* 255 */       InventoryComputeSinglePeriodNoSetup();
/* 256 */       break;
/*     */     case 30202: 
/* 258 */       this.InventoryDensity = 1;
/* 259 */       this.InventoryX0 = ((Double)p.elementAt(0)).doubleValue();
/* 260 */       this.InventoryX1 = ((Double)p.elementAt(1)).doubleValue();
/* 261 */       this.Inventoryc = ((Double)p.elementAt(2)).doubleValue();
/* 262 */       this.Inventoryh = ((Double)p.elementAt(3)).doubleValue();
/* 263 */       this.Inventoryp = ((Double)p.elementAt(4)).doubleValue();
/* 264 */       this.InventoryK = ((Double)p.elementAt(5)).doubleValue();
/*     */       
/* 266 */       this.Inventoryt = (this.InventoryX1 - this.InventoryX0);
/* 267 */       InventoryComputeSinglePeriodWithSetup();
/* 268 */       break;
/*     */     case 30203: 
/* 270 */       this.InventoryDensity = 1;
/* 271 */       this.Inventoryt = ((Double)p.elementAt(0)).doubleValue();
/* 272 */       this.Inventoryc = ((Double)p.elementAt(1)).doubleValue();
/* 273 */       this.Inventoryh = ((Double)p.elementAt(2)).doubleValue();
/* 274 */       this.Inventoryp = ((Double)p.elementAt(3)).doubleValue();
/* 275 */       InventoryComputeTwoPeriod();
/* 276 */       break;
/*     */     case 30204: 
/* 278 */       this.InventoryDensity = 1;
/* 279 */       this.Inventoryt = ((Double)p.elementAt(0)).doubleValue();
/* 280 */       this.Inventoryc = ((Double)p.elementAt(1)).doubleValue();
/* 281 */       this.Inventoryh = ((Double)p.elementAt(2)).doubleValue();
/* 282 */       this.Inventoryp = ((Double)p.elementAt(3)).doubleValue();
/* 283 */       this.InventoryDF = ((Double)p.elementAt(4)).doubleValue();
/* 284 */       InventoryComputeInfinitePeriod();
/* 285 */       break;
/*     */     }
/* 287 */     return true;
/*     */   }
/*     */   
/*     */   protected void initEOQPrintInfo(IORITProcessController.ITData data)
/*     */   {
/* 292 */     this.EOQ_H = data.EOQ_H;
/* 293 */     this.EOQ_D = data.EOQ_D;
/* 294 */     this.EOQ_S = data.EOQ_S;
/* 295 */     this.Qstar = data.Qstar;
/* 296 */     this.Qy = data.Qy;
/*     */   }
/*     */   
/*     */   protected void ORPrint()
/*     */   {
/* 301 */     PrintInventory();
/*     */   }
/*     */   
/*     */   private void PrintInventory() {
/* 305 */     switch (this.procedure) {
/*     */     case 1: 
/* 307 */       PrintLine("Single Period Model With No Setup Cost:");
/* 308 */       break;
/*     */     case 2: 
/* 310 */       PrintLine("Single Period Model With A Setup Cost:");
/* 311 */       break;
/*     */     case 3: 
/* 313 */       PrintLine("Two Period Model With No Setup Cost:");
/* 314 */       break;
/*     */     case 4: 
/* 316 */       PrintLine("Infinite Period Model With No Setup Cost:");
/* 317 */       break;
/*     */     
/*     */     case 5: 
/* 320 */       PrintLine("Economic Order Quantity");
/*     */     }
/*     */     
/* 323 */     Skip(2);
/* 324 */     if (this.procedure != 5) {
/* 325 */       if (this.InventoryDensity == 0) {
/* 326 */         PrintLine(String.valueOf(String.valueOf(new StringBuffer("Demand density is exponential, with lambda = ").append(RealToString(this.InventoryLambda, 5)).append("."))));
/*     */       }
/*     */       else
/*     */       {
/* 330 */         switch (this.procedure) {
/*     */         case 1: 
/*     */         case 2: 
/* 333 */           PrintLine(String.valueOf(String.valueOf(new StringBuffer("Demand density is uniform between ").append(RealToString(this.InventoryX0, 5)).append(" and ").append(RealToString(this.InventoryX1, 5)).append("."))));
/*     */           
/*     */ 
/* 336 */           break;
/*     */         case 3: 
/*     */         case 4: 
/* 339 */           PrintLine(String.valueOf(String.valueOf(new StringBuffer("Demand density is uniform between 0 and ").append(RealToString(this.Inventoryt, 5)).append("."))));
/*     */           
/* 341 */           break;
/*     */         }
/*     */         
/*     */       }
/*     */       
/* 346 */       SkipLine();
/*     */       
/* 348 */       PrintLine("Unit production or purchasing cost, c = ".concat(String.valueOf(String.valueOf(RealToString(this.Inventoryc, 5)))));
/*     */       
/* 350 */       SkipLine();
/* 351 */       PrintLine("Unit inventory holding cost, h = ".concat(String.valueOf(String.valueOf(RealToString(this.Inventoryh, 5)))));
/*     */       
/* 353 */       SkipLine();
/* 354 */       PrintLine("Unit shortage cost, p = ".concat(String.valueOf(String.valueOf(RealToString(this.Inventoryp, 5)))));
/* 355 */       SkipLine();
/* 356 */       if (this.procedure == 2) {
/* 357 */         PrintLine("Setup Cost, K = ".concat(String.valueOf(String.valueOf(RealToString(this.InventoryK, 5)))));
/* 358 */         SkipLine();
/*     */       }
/* 360 */       if (this.procedure == 4) {
/* 361 */         PrintLine("Discount Factor = ".concat(String.valueOf(String.valueOf(RealToString(this.InventoryDF, 5)))));
/* 362 */         SkipLine();
/*     */       }
/* 364 */       Skip(3);
/* 365 */       PrintLine("Results:");
/* 366 */       SkipLine();
/* 367 */       switch (this.procedure) {
/*     */       case 1: 
/*     */       case 4: 
/* 370 */         PrintLine("y(0) = ".concat(String.valueOf(String.valueOf(RealToString(this.InventoryY0, 7)))));
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 390 */         break;
/*     */       case 2: 
/* 373 */         PrintLine("s = ".concat(String.valueOf(String.valueOf(RealToString(this.InventoryLittleS, 7)))));
/* 374 */         SkipLine();
/* 375 */         PrintLine("S = ".concat(String.valueOf(String.valueOf(RealToString(this.InventoryBigS, 7)))));
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 390 */         break;
/*     */       case 3: 
/* 378 */         PrintLine("y1(0) = ".concat(String.valueOf(String.valueOf(RealToString(this.InventoryY0, 7)))));
/* 379 */         SkipLine();
/* 380 */         PrintLine("y2(0) = ".concat(String.valueOf(String.valueOf(RealToString(this.InventoryY1, 7)))));
/*     */       }
/*     */     }
/*     */     else {
/* 384 */       PrintLine("TC = H * Q/2 + S * D/Q");
/* 385 */       PrintLine("HC = H * Q/2");
/* 386 */       PrintLine("OC = S * D/Q");
/* 387 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("H = ").append(this.EOQ_H).append("    D = ").append((int)this.EOQ_D / 10).append("    S = ").append(this.EOQ_S))));
/* 388 */       PrintLine(String.valueOf(String.valueOf(new StringBuffer("Qo = ").append(this.Qstar).append("    Qy = ").append(this.Qy))));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORITSolver.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */