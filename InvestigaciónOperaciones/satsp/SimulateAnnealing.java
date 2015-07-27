/*     */ package satsp;
/*     */ 
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
/*     */ 
/*     */ public class SimulateAnnealing
/*     */   implements Serializable
/*     */ {
/*     */   protected TspObjectiveFunction objectFunction;
/*     */   protected double[] temperature;
/*     */   protected double pathlength;
/*     */   protected double currentObj;
/*     */   protected double minimallength;
/*     */   protected int[] currentSolution;
/*     */   protected int[] initSolution;
/*     */   protected double[][] customers;
/*  25 */   protected int iteration = 0;
/*  26 */   protected int Tnum = 0;
/*  27 */   protected ArrayList auto_output = new ArrayList();
/*  28 */   protected double bestObj = 2.147483647E9D;
/*  29 */   protected int bestCount = 0;
/*  30 */   protected boolean tooLittle = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimulateAnnealing(double[][] citys)
/*     */   {
/*  40 */     this.customers = ((double[][])citys.clone());
/*  41 */     this.currentSolution = new int[citys.length];
/*  42 */     this.initSolution = new int[citys.length];
/*  43 */     this.objectFunction = new TspObjectiveFunction(this.customers);
/*     */   }
/*     */   
/*     */ 
/*     */   public void clean()
/*     */   {
/*  49 */     this.currentSolution = ((int[])this.initSolution.clone());
/*  50 */     this.objectFunction = new TspObjectiveFunction(this.customers);
/*  51 */     this.currentObj = this.objectFunction.evaluate(this.currentSolution);
/*  52 */     Tschedule ts = new Tschedule(5, this.currentObj);
/*  53 */     this.temperature = ts.getT();
/*  54 */     this.Tnum = 0;
/*  55 */     this.iteration = 0;
/*  56 */     this.bestObj = 2.147483647E9D;
/*  57 */     this.bestCount = 0;
/*  58 */     this.tooLittle = false;
/*  59 */     this.auto_output.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean setInitSolution(int[] init)
/*     */   {
/*  68 */     this.currentSolution = ((int[])init.clone());
/*     */     
/*  70 */     this.initSolution = ((int[])init.clone());
/*  71 */     double temp = this.objectFunction.evaluate(this.currentSolution);
/*  72 */     if (temp < Integer.MAX_VALUE) {
/*  73 */       this.currentObj = temp;
/*  74 */       Tschedule ts = new Tschedule(5, this.currentObj);
/*  75 */       this.temperature = ts.getT();
/*  76 */       return true;
/*     */     }
/*  78 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean anneal(double d)
/*     */   {
/*  88 */     double temp = Math.random();
/*  89 */     if (temp < probability(d)) {
/*  90 */       return true;
/*     */     }
/*     */     
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   private double probability(double p)
/*     */   {
/*  98 */     double temp = Math.exp(p / this.temperature[this.Tnum]);
/*     */     
/* 100 */     return temp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList startIteration()
/*     */   {
/* 109 */     while (this.iteration < 5)
/*     */     {
/* 111 */       this.currentSolution = nextTrialSolution();
/* 112 */       this.iteration += 1;
/* 113 */       if (this.iteration == 5) {
/* 114 */         this.Tnum += 1;
/* 115 */         this.iteration = 0;
/* 116 */         if (this.Tnum == 5) break;
/*     */       }
/*     */     }
/* 119 */     return this.auto_output;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasNextIteration()
/*     */   {
/* 127 */     if (this.iteration == 5) {
/* 128 */       this.Tnum += 1;
/* 129 */       this.iteration = 0;
/*     */     }
/* 131 */     if (this.Tnum < 5) return true;
/* 132 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList nextIteration()
/*     */   {
/* 140 */     int[] sub_tour = (int[])this.currentSolution.clone();
/* 141 */     int[] temp = (int[])this.currentSolution.clone();
/* 142 */     double newObjVal = 0.0D;
/* 143 */     boolean anneal = false;
/*     */     do {
/* 145 */       temp = subTour(sub_tour);
/* 146 */       newObjVal = this.objectFunction.evaluate(temp);
/* 147 */     } while (newObjVal > Integer.MAX_VALUE);
/* 148 */     ArrayList per = new ArrayList();
/* 149 */     per.add(new Double(this.temperature[this.Tnum]));
/* 150 */     per.add(temp);
/* 151 */     per.add(new Double(newObjVal));
/* 152 */     if (newObjVal < this.currentObj) {
/* 153 */       this.currentObj = newObjVal;
/* 154 */       per.add(new Double(1.0D));
/* 155 */       per.add(new Boolean(true));
/* 156 */       this.auto_output.add(per);
/* 157 */       this.currentSolution = temp;
/* 158 */       this.iteration += 1;
/* 159 */       return per;
/*     */     }
/* 161 */     anneal = anneal(this.currentObj - newObjVal);
/* 162 */     per.add(new Double(probability(this.currentObj - newObjVal)));
/* 163 */     per.add(new Boolean(anneal));
/* 164 */     if (anneal) {
/* 165 */       this.iteration += 1;
/*     */     }
/* 167 */     return per;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private int[] nextTrialSolution()
/*     */   {
/* 174 */     int[] sub_tour = (int[])this.currentSolution.clone();
/* 175 */     int[] temp = (int[])this.currentSolution.clone();
/* 176 */     double newObjVal = 0.0D;
/* 177 */     boolean anneal = false;
/* 178 */     boolean isSame = true;
/* 179 */     int upperLimit = 10000;
/* 180 */     int loopNum = 0;
/*     */     
/*     */     do
/*     */     {
/* 184 */       if (this.auto_output.size() != 0) {
/* 185 */         ArrayList tempList = (ArrayList)this.auto_output.get(this.auto_output.size() - 1);
/* 186 */         int[] compareArr = (int[])tempList.get(1);
/* 187 */         temp = subTour(compareArr);
/*     */       } else {
/* 189 */         temp = subTour(sub_tour);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       do
/*     */       {
/* 197 */         loopNum++;
/* 198 */         isSame = true;
/* 199 */         if (this.auto_output.size() != 0) {
/* 200 */           ArrayList tempList = (ArrayList)this.auto_output.get(this.auto_output.size() - 1);
/* 201 */           int[] compareArr = (int[])tempList.get(1);
/* 202 */           for (int i = 0; i < compareArr.length; i++) {
/* 203 */             if (compareArr[i] != temp[i]) {
/* 204 */               isSame = false;
/*     */             }
/*     */           }
/* 207 */           if (isSame) {
/* 208 */             temp = subTour(compareArr);
/*     */           }
/*     */         } else {
/* 211 */           isSame = false;
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 217 */       while (isSame);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 222 */       for (int i = 0; i < temp.length; i++) {}
/*     */       
/*     */ 
/*     */ 
/* 226 */       newObjVal = this.objectFunction.evaluate(temp);
/* 227 */       if (newObjVal <= Integer.MAX_VALUE)
/*     */       {
/*     */ 
/* 230 */         ArrayList per = new ArrayList();
/* 231 */         per.add(new Double(this.temperature[this.Tnum]));
/* 232 */         per.add(temp);
/* 233 */         per.add(new Double(newObjVal));
/* 234 */         if (newObjVal < this.currentObj) {
/* 235 */           this.currentObj = newObjVal;
/* 236 */           per.add(new Double(1.0D));
/* 237 */           per.add(new Boolean(true));
/* 238 */           this.auto_output.add(per);
/* 239 */           this.bestObj = newObjVal;
/* 240 */           this.bestCount = 0;
/* 241 */           return temp;
/*     */         }
/* 243 */         anneal = anneal(this.currentObj - newObjVal);
/* 244 */         per.add(new Double(probability(this.currentObj - newObjVal)));
/* 245 */         per.add(new Boolean(anneal));
/* 246 */         if (anneal) { this.bestCount += 1;
/*     */         }
/*     */         
/* 249 */         this.auto_output.add(per);
/*     */       }
/* 251 */     } while (!anneal);
/* 252 */     return temp;
/*     */   }
/*     */   
/*     */   private int[] subTour(int[] tour) {
/* 256 */     int[] sub = (int[])tour.clone();
/* 257 */     int begin = 1;
/* 258 */     int end = 0;
/* 259 */     int maxBeginPos = sub.length - 2;
/* 260 */     int maxEndPos = sub.length - 1;
/*     */     
/*     */     do
/*     */     {
/* 264 */       double accept = maxBeginPos * Math.random();
/* 265 */       for (int i = 0; i < sub.length - 2; i++) {
/* 266 */         if ((i < accept) && (accept < i + 1)) {
/* 267 */           begin = i + 1;
/* 268 */           break;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 273 */       int minEndPos = maxEndPos - begin;
/* 274 */       accept = minEndPos * Math.random() + begin;
/* 275 */       for (int i = begin; i < sub.length - 1; i++) {
/* 276 */         if ((i < accept) && (accept < i + 1)) {
/* 277 */           end = i + 1;
/* 278 */           break;
/*     */         }
/*     */       }
/* 281 */     } while ((begin == 1) && (end == sub.length - 1));
/*     */     
/* 283 */     for (int i = 0; i <= end - begin; i++) {
/* 284 */       sub[(i + begin)] = tour[(end - i)];
/*     */     }
/* 286 */     return sub;
/*     */   }
/*     */   
/*     */   public String toString(int[] solution) {
/* 290 */     String temp = "";
/* 291 */     for (int i = 0; i < solution.length; i++) {
/* 292 */       temp = String.valueOf(String.valueOf(temp)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(solution[i])).concat("-"))));
/*     */     }
/* 294 */     return temp;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\satsp\SimulateAnnealing.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */