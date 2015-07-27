/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimulateAnnealing
/*     */   extends ORNLPSolver
/*     */   implements Serializable
/*     */ {
/*     */   public ObjectiveFunction objectFunction;
/*     */   private double[] temperature;
/*     */   private double currentObj;
/*     */   private double[] currentSolution;
/*     */   private double[] initSolution;
/*  21 */   private int iteration = 0;
/*  22 */   private int Tnum = 0;
/*  23 */   protected ArrayList auto_output = new ArrayList();
/*  24 */   private boolean MAX_MIN = true;
/*     */   private ORSolverBase.SimplexModelType sModel;
/*     */   private ORSolverBase.PolynomialType polynomial;
/*  27 */   private int bestCount = 0;
/*  28 */   private int littleCount = 0;
/*  29 */   private boolean tooLittle = false;
/*  30 */   protected double bestObj = Double.MAX_VALUE;
/*     */   
/*     */ 
/*     */   protected double[] bestSolution;
/*     */   
/*     */ 
/*     */   protected int currentPos;
/*     */   
/*     */ 
/*     */   public SimulateAnnealing() {}
/*     */   
/*     */ 
/*     */   public SimulateAnnealing(ORSolverBase.PolynomialType rType, ORSolverBase.SimplexModelType sType)
/*     */   {
/*  44 */     this.currentSolution = new double[sType.NumVariables];
/*     */     
/*     */ 
/*  47 */     if (sType.Objective == 0) this.MAX_MIN = true; else {
/*  48 */       this.MAX_MIN = false;
/*     */     }
/*  50 */     this.initSolution = new double[sType.NumVariables];
/*  51 */     this.bestSolution = new double[sType.NumVariables];
/*  52 */     this.objectFunction = new ObjectiveFunction(rType, sType);
/*  53 */     this.polynomial = rType;
/*  54 */     this.sModel = sType;
/*  55 */     this.objectFunction.variableArea();
/*  56 */     this.currentSolution = this.objectFunction.initSolution();
/*  57 */     this.initSolution = this.objectFunction.initSolution();
/*  58 */     this.currentObj = this.objectFunction.evaluate(this.currentSolution);
/*  59 */     this.bestObj = this.currentObj;
/*  60 */     Tschedule ts = new Tschedule(5, this.currentObj);
/*  61 */     this.temperature = ts.getT();
/*  62 */     ArrayList per = new ArrayList();
/*  63 */     per.add(new Double(this.temperature[this.Tnum]));
/*  64 */     per.add(this.initSolution);
/*  65 */     per.add(new Double(this.currentObj));
/*  66 */     per.add(new Double(1.0D));
/*  67 */     per.add(new Boolean(true));
/*  68 */     this.auto_output.add(per);
/*     */   }
/*     */   
/*     */   public void clean() {
/*  72 */     this.currentSolution = ((double[])this.initSolution.clone());
/*  73 */     this.objectFunction = new ObjectiveFunction(this.polynomial, this.sModel);
/*  74 */     this.currentObj = this.objectFunction.evaluate(this.currentSolution);
/*  75 */     Tschedule ts = new Tschedule(5, this.currentObj);
/*  76 */     this.temperature = ts.getT();
/*  77 */     this.Tnum = 0;
/*  78 */     this.iteration = 0;
/*  79 */     this.bestObj = this.currentObj;
/*  80 */     this.bestCount = 0;
/*  81 */     this.littleCount = 0;
/*  82 */     this.tooLittle = false;
/*  83 */     this.auto_output.clear();
/*  84 */     ArrayList per = new ArrayList();
/*  85 */     per.add(new Double(this.temperature[this.Tnum]));
/*  86 */     per.add(this.initSolution);
/*  87 */     per.add(new Double(this.currentObj));
/*  88 */     per.add(new Double(1.0D));
/*  89 */     per.add(new Boolean(true));
/*  90 */     this.auto_output.add(per);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasNextIteration()
/*     */   {
/*  97 */     if (this.iteration == 5) {
/*  98 */       this.Tnum += 1;
/*  99 */       this.iteration = 0;
/*     */     }
/* 101 */     if (this.Tnum < 5) return true;
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   public boolean setInitSolution(double[] trial) {
/* 106 */     if (this.objectFunction.feasible(trial)) {
/* 107 */       this.currentSolution = trial;
/* 108 */       this.initSolution = trial;
/* 109 */       this.currentObj = this.objectFunction.evaluate(this.currentSolution);
/* 110 */       Tschedule ts = new Tschedule(5, this.currentObj);
/* 111 */       this.temperature = ts.getT();
/* 112 */       return true; }
/* 113 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean anneal(double d)
/*     */   {
/* 123 */     double temp = Math.random();
/* 124 */     if (temp < probability(d)) {
/* 125 */       return true;
/*     */     }
/*     */     
/* 128 */     return false;
/*     */   }
/*     */   
/*     */   private double probability(double p)
/*     */   {
/* 133 */     double temp = Math.exp(p / this.temperature[this.Tnum]);
/* 134 */     return temp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList startIteration()
/*     */   {
/* 144 */     while (this.iteration < 5)
/* 145 */       if (this.tooLittle) {
/* 146 */         System.out.println("The probability is too little!");
/*     */       }
/*     */       else {
/* 149 */         this.currentSolution = nextTrialSolution();
/* 150 */         this.iteration += 1;
/* 151 */         if (this.iteration == 5) {
/* 152 */           this.Tnum += 1;
/* 153 */           this.iteration = 0;
/* 154 */           if (this.Tnum == 5) break;
/*     */         }
/*     */       }
/* 157 */     return this.auto_output;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private double getMaxOrMin(double newValue, double currentValue)
/*     */   {
/* 167 */     if (this.MAX_MIN)
/* 168 */       return newValue - currentValue;
/* 169 */     return currentValue - newValue;
/*     */   }
/*     */   
/*     */   private boolean betterThanCurrent(double newValue, double currentValue) {
/* 173 */     if (this.MAX_MIN) {
/* 174 */       if (newValue > currentValue) return true;
/* 175 */       return false;
/*     */     }
/* 177 */     if (newValue < currentValue) return true;
/* 178 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ArrayList nextIteration()
/*     */   {
/* 187 */     double[] trial = (double[])this.currentSolution.clone();
/* 188 */     double[] temp = (double[])this.currentSolution.clone();
/* 189 */     double newObjVal = 0.0D;
/* 190 */     boolean anneal = false;
/*     */     
/* 192 */     temp = randomSelectImNei(trial);
/* 193 */     newObjVal = this.objectFunction.evaluate(temp);
/* 194 */     ArrayList per = new ArrayList();
/* 195 */     per.add(new Double(this.temperature[this.Tnum]));
/* 196 */     per.add(temp);
/* 197 */     per.add(new Double(newObjVal));
/* 198 */     if (betterThanCurrent(newObjVal, this.currentObj)) {
/* 199 */       this.currentObj = newObjVal;
/* 200 */       per.add(new Double(1.0D));
/* 201 */       per.add(new Boolean(true));
/* 202 */       this.auto_output.add(per);
/* 203 */       this.currentSolution = temp;
/* 204 */       this.iteration += 1;
/* 205 */       return per;
/*     */     }
/* 207 */     anneal = anneal(getMaxOrMin(newObjVal, this.currentObj));
/* 208 */     per.add(new Double(probability(getMaxOrMin(newObjVal, this.currentObj))));
/* 209 */     per.add(new Boolean(anneal));
/* 210 */     if (anneal) {
/* 211 */       this.iteration += 1;
/*     */     }
/* 213 */     return per;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private double[] nextTrialSolution()
/*     */   {
/* 220 */     double[] trial = (double[])this.currentSolution.clone();
/* 221 */     double[] temp = (double[])this.currentSolution.clone();
/* 222 */     double newObjVal = 0.0D;
/* 223 */     boolean anneal = false;
/*     */     do {
/* 225 */       temp = randomSelectImNei(trial);
/* 226 */       newObjVal = this.objectFunction.evaluate(temp);
/* 227 */       ArrayList per = new ArrayList();
/* 228 */       per.add(new Double(this.temperature[this.Tnum]));
/* 229 */       per.add(temp);
/* 230 */       per.add(new Double(newObjVal));
/* 231 */       if (betterThanCurrent(newObjVal, this.currentObj)) {
/* 232 */         this.currentObj = newObjVal;
/* 233 */         per.add(new Double(1.0D));
/* 234 */         per.add(new Boolean(true));
/* 235 */         this.currentPos = this.auto_output.size();
/* 236 */         this.auto_output.add(per);
/* 237 */         this.bestObj = newObjVal;
/* 238 */         this.bestCount = 0;
/* 239 */         this.littleCount = 0;
/* 240 */         this.bestSolution = temp;
/* 241 */         return temp;
/*     */       }
/* 243 */       anneal = anneal(getMaxOrMin(newObjVal, this.currentObj));
/* 244 */       per.add(new Double(probability(getMaxOrMin(newObjVal, this.currentObj))));
/* 245 */       per.add(new Boolean(anneal));
/* 246 */       if (anneal) this.bestCount += 1; else {
/* 247 */         this.littleCount += 1;
/*     */       }
/* 249 */       this.auto_output.add(per);
/* 250 */     } while ((this.littleCount <= 2000) && 
/* 251 */       (!anneal));
/* 252 */     return temp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private double[] randomSelectImNei(double[] trial)
/*     */   {
/* 261 */     double[] next = (double[])trial.clone();
/* 262 */     double[] sigma = new double[trial.length];
/* 263 */     double[] ND = new double[trial.length];
/* 264 */     double[] temp = this.objectFunction.variableArea();
/* 265 */     NormalDistribution nDis = new NormalDistribution();
/* 266 */     for (int i = 0; i < temp.length; i++) {
/* 267 */       temp[i] /= 6;
/*     */     }
/*     */     for (;;) {
/* 270 */       for (int i = 0; i < sigma.length; i++) {
/* 271 */         nDis.setParameters(0.0D, sigma[i]);
/* 272 */         next[i] += nDis.simulate();
/*     */       }
/* 274 */       if (this.objectFunction.feasible(ND))
/*     */         break;
/*     */     }
/* 277 */     return ND;
/*     */   }
/*     */   
/*     */   public String toString(double[] solution) {
/* 281 */     String temp = "";
/* 282 */     for (int i = 0; i < solution.length; i++) {
/* 283 */       temp = String.valueOf(String.valueOf(temp)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(solution[i])).concat("-"))));
/*     */     }
/* 285 */     return temp;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\SimulateAnnealing.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */