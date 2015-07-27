/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Population
/*     */   extends ORNLPSolver
/*     */ {
/*  15 */   public static final int POPULATION_SIZES = 10;
/*  16 */   public static final double MUTATION_PERCENT = 0.1D;
/*     */   private Chromosome[] individual;
/*     */   private Chromosome[] storePopulation;
/*     */   private HashMap parentSelect;
/*  20 */   private int matingPopulationSize = 6;
/*  21 */   private int favoredPopulationSize = 4;
/*  22 */   private int noFavoredPopulationSize = 2;
/*     */   private Chromosome[][] allParent;
/*     */   private byte[][] mutations;
/*     */   private ArrayList childMutation;
/*     */   private double bestFitness;
/*     */   private int[] variableBound;
/*     */   private FitnessFunction fitness;
/*     */   private ArrayList all_output;
/*     */   private static double penalty;
/*  31 */   private boolean MAX_MIN = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Population(ORSolverBase.PolynomialType rType, ORSolverBase.SimplexModelType sType)
/*     */   {
/*  40 */     this.fitness = new FitnessFunction(rType, sType);
/*  41 */     if (sType.Objective == 0) this.MAX_MIN = true; else
/*  42 */       this.MAX_MIN = false;
/*  43 */     this.variableBound = this.fitness.variableArea();
/*  44 */     initPopulation();
/*     */   }
/*     */   
/*     */ 
/*     */   private void initPopulation()
/*     */   {
/*  50 */     this.individual = new Chromosome[10];
/*  51 */     for (int i = 0; i < 10; i++) {
/*  52 */       int[] vairables = new int[this.variableBound.length];
/*  53 */       for (int j = 0; j < this.variableBound.length; j++) {
/*  54 */         vairables[j] = ((int)(this.variableBound[j] * Math.random()));
/*     */       }
/*  56 */       while (!this.fitness.feasible(vairables)) {
/*  57 */         for (int j = 0; j < this.variableBound.length; j++) {
/*  58 */           vairables[j] = ((int)(this.variableBound[j] * Math.random()));
/*     */         }
/*     */       }
/*  61 */       this.individual[i] = new Chromosome(this.fitness, vairables, this.variableBound);
/*     */     }
/*  63 */     sortChromosomes();
/*     */     
/*     */ 
/*  66 */     for (int i = 0; i < this.individual.length; i++) {
/*  67 */       if (!this.individual[i].getFeasible()) {
/*  68 */         this.individual[i].setFitness(this.individual[i].getFitness() - penalty);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void sortChromosomes()
/*     */   {
/*  75 */     Chromosome.sortChromosomes(this.individual, this.MAX_MIN);
/*  76 */     penalty = this.individual[0].getFitness();
/*     */   }
/*     */   
/*     */   private Chromosome[] storeCurrentPopulation()
/*     */   {
/*  81 */     this.parentSelect = new HashMap();
/*  82 */     Chromosome[] lastIteration = new Chromosome[this.individual.length];
/*  83 */     for (int i = 0; i < this.individual.length; i++) {
/*  84 */       lastIteration[i] = this.individual[i];
/*  85 */       this.parentSelect.put(this.individual[i], new Integer(i));
/*     */     }
/*  87 */     return lastIteration;
/*     */   }
/*     */   
/*     */   public ArrayList nextIteration() {
/*  91 */     this.storePopulation = storeCurrentPopulation();
/*  92 */     ArrayList generation = generation();
/*     */     
/*  94 */     Chromosome.sortChromosomes(this.individual, this.MAX_MIN);
/*  95 */     Chromosome best = this.individual[0];
/*  96 */     if (!best.getFeasible()) {
/*  97 */       penalty = best.getFitness();
/*     */     }
/*  99 */     this.all_output = new ArrayList();
/* 100 */     this.all_output.add(best);
/* 101 */     this.all_output.add(this.storePopulation);
/* 102 */     this.all_output.add(generation);
/* 103 */     return this.all_output;
/*     */   }
/*     */   
/*     */   public boolean isFeasible() {
/* 107 */     Chromosome best = this.individual[0];
/* 108 */     boolean isFeasible = best.getFeasible();
/* 109 */     return isFeasible;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Chromosome[] randomGetFavored()
/*     */   {
/* 117 */     Chromosome[] favored = new Chromosome[this.favoredPopulationSize];
/* 118 */     int size = 10 / 2;
/* 119 */     HashMap map = new HashMap();
/*     */     
/* 121 */     for (int i = 0; i < this.favoredPopulationSize; i++) {
/*     */       int temp;
/* 123 */       while (map.containsKey(new Integer(temp = (int)(Math.random() * size)))) {}
/* 124 */       map.put(new Integer(temp), new Integer(temp));
/* 125 */       favored[i] = this.individual[temp];
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 130 */     return favored;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private Chromosome[] randomGetNoFavored()
/*     */   {
/* 137 */     Chromosome[] favored = new Chromosome[this.noFavoredPopulationSize];
/* 138 */     int size = 10 / 2;
/* 139 */     HashMap map = new HashMap();
/*     */     
/* 141 */     for (int i = 0; i < this.noFavoredPopulationSize; i++) {
/*     */       int temp;
/* 143 */       while (map.containsKey(new Integer(temp = (int)(Math.random() * size)))) {}
/*     */       
/* 145 */       map.put(new Integer(temp), new Integer(temp));
/* 146 */       favored[i] = this.individual[(size + temp)];
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 151 */     return favored;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Chromosome[][] randomChooseParents()
/*     */   {
/* 159 */     Chromosome[] favored = randomGetFavored();
/* 160 */     Chromosome[] nofavored = randomGetNoFavored();
/* 161 */     int size = this.favoredPopulationSize + this.noFavoredPopulationSize - 1;
/* 162 */     Chromosome[] allSelected = new Chromosome[this.favoredPopulationSize + this.noFavoredPopulationSize];
/* 163 */     for (int i = 0; i < favored.length; i++)
/* 164 */       allSelected[i] = favored[i];
/* 165 */     for (int i = 0; i < nofavored.length; i++) {
/* 166 */       allSelected[(favored.length + i)] = nofavored[i];
/*     */     }
/* 168 */     Chromosome.sortChromosomes(allSelected, this.MAX_MIN);
/* 169 */     HashMap map = new HashMap();
/* 170 */     int[] last = new int[2];
/* 171 */     Chromosome[][] parents = new Chromosome[(this.favoredPopulationSize + this.noFavoredPopulationSize) / 2][2];
/* 172 */     for (int i = 0; i < (this.favoredPopulationSize + this.noFavoredPopulationSize) / 2; i++) {
/*     */       int temp;
/*     */       int temp1;
/* 175 */       do { if (i == (this.favoredPopulationSize + this.noFavoredPopulationSize) / 2 - 1) {
/* 176 */           for (int j = 0; j < last.length; j++) {
/* 177 */             for (int k = 0; k < allSelected.length; k++)
/* 178 */               if (!map.containsKey(new Integer(k))) {
/* 179 */                 last[j] = k;
/* 180 */                 map.put(new Integer(k), new Integer(k));
/* 181 */                 break;
/*     */               }
/*     */           }
/* 184 */           parents[i][0] = allSelected[last[0]];
/* 185 */           parents[i][1] = allSelected[last[1]];
/* 186 */           break;
/*     */         }
/*     */         
/*     */ 
/* 190 */         temp = (int)(Math.random() * size);
/* 191 */         temp1 = (int)(Math.random() * size);
/* 192 */       } while ((temp == temp1) || (map.containsKey(new Integer(temp))) || (map.containsKey(new Integer(temp1))));
/* 193 */       map.put(new Integer(temp), new Integer(temp));
/* 194 */       map.put(new Integer(temp1), new Integer(temp1));
/* 195 */       parents[i][0] = allSelected[temp];
/* 196 */       parents[i][1] = allSelected[temp1];
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 201 */     return parents;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Chromosome[] crossover(Chromosome[] parent)
/*     */   {
/* 209 */     Chromosome father = (Chromosome)parent[0].clone();
/* 210 */     Chromosome mother = (Chromosome)parent[1].clone();
/* 211 */     byte[][] fGene = (byte[][])father.getGene().clone();
/* 212 */     byte[][] mGene = (byte[][])mother.getGene().clone();
/* 213 */     this.childMutation = new ArrayList();
/*     */     
/* 215 */     byte[][] children = passDown(fGene, mGene);
/* 216 */     children = mutation(children);
/* 217 */     while (!this.fitness.feasible(getVariables(children))) {
/* 218 */       children = passDown(fGene, mGene);
/* 219 */       children = mutation(children);
/*     */     }
/*     */     
/* 222 */     this.childMutation.add(this.mutations);
/* 223 */     Chromosome child = new Chromosome(this.fitness, children);
/*     */     
/*     */ 
/*     */ 
/* 227 */     byte[][] children1 = passDown(fGene, mGene);
/* 228 */     children1 = mutation(children1);
/* 229 */     while (!this.fitness.feasible(getVariables(children1))) {
/* 230 */       children1 = passDown(fGene, mGene);
/* 231 */       children1 = mutation(children1);
/*     */     }
/*     */     
/* 234 */     this.childMutation.add(this.mutations);
/*     */     
/* 236 */     Chromosome child1 = new Chromosome(this.fitness, children1);
/*     */     
/* 238 */     if (!child.getFeasible()) child.setFitness(child.getFitness() - penalty);
/* 239 */     if (!child1.getFeasible()) { child1.setFitness(child1.getFitness() - penalty);
/*     */     }
/* 241 */     Chromosome[] allChildren = new Chromosome[2];
/* 242 */     allChildren[0] = child;
/* 243 */     allChildren[1] = child1;
/* 244 */     return allChildren;
/*     */   }
/*     */   
/*     */   private int[] getVariables(byte[][] children)
/*     */   {
/* 249 */     int[] variables = new int[children.length];
/*     */     
/* 251 */     for (int i = 0; i < children.length; i++) {
/* 252 */       String temp = "";
/* 253 */       for (int j = 0; j < children[0].length; j++) {
/* 254 */         temp = String.valueOf(String.valueOf(temp)).concat(String.valueOf(String.valueOf(Byte.toString(children[i][j]))));
/*     */       }
/* 256 */       variables[i] = Integer.valueOf(temp, 2).intValue();
/*     */     }
/* 258 */     return variables;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private byte[][] mutation(byte[][] children)
/*     */   {
/* 267 */     this.mutations = new byte[children.length][children[0].length];
/* 268 */     for (int i = 0; i < children.length; i++) {
/* 269 */       for (int j = 0; j < children[0].length; j++) {
/* 270 */         if (Math.random() < 0.1D) {
/* 271 */           if (children[i][j] - 1 == 0) {
/* 272 */             children[i][j] = 0;
/*     */           }
/*     */           else
/* 275 */             children[i][j] = 1;
/* 276 */           this.mutations[i][j] = 1;
/*     */         }
/*     */       }
/*     */     }
/* 280 */     return children;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private byte[][] passDown(byte[][] father, byte[][] mother)
/*     */   {
/* 290 */     byte[][] children = new byte[father.length][father[0].length];
/* 291 */     for (int i = 0; i < father.length; i++) {
/* 292 */       for (int j = 0; j < father[0].length; j++) {
/* 293 */         int a = father[i][j];
/* 294 */         int b = mother[i][j];
/*     */         int c;
/* 296 */         int c; if (a == b) { c = a;
/*     */         } else {
/* 298 */           int temp = (int)(Math.random() * 2);
/* 299 */           int c; if (temp > 0) c = 1; else
/* 300 */             c = 0;
/*     */         }
/* 302 */         children[i][j] = ((byte)c);
/*     */       }
/*     */     }
/* 305 */     return children;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private ArrayList generation()
/*     */   {
/* 313 */     ArrayList next = new ArrayList();
/* 314 */     ArrayList thisMutations = new ArrayList();
/* 315 */     this.allParent = randomChooseParents();
/* 316 */     Chromosome[][] childrens = new Chromosome[this.allParent.length][2];
/* 317 */     Chromosome[] generation = new Chromosome[this.matingPopulationSize];
/* 318 */     for (int i = 0; i < this.allParent.length; i++) {
/* 319 */       childrens[i] = crossover(this.allParent[i]);
/* 320 */       thisMutations.add(this.childMutation);
/*     */     }
/*     */     
/*     */ 
/* 324 */     int[][] selectParent = new int[this.allParent.length][this.allParent[0].length];
/* 325 */     for (int i = 0; i < this.allParent.length; i++) {
/* 326 */       for (int j = 0; j < this.allParent[0].length; j++) {
/* 327 */         if (this.parentSelect.containsKey(this.allParent[i][j])) {
/* 328 */           Integer temp = (Integer)this.parentSelect.get(this.allParent[i][j]);
/* 329 */           selectParent[i][j] = temp.intValue();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 335 */     next.add(this.allParent);
/* 336 */     next.add(selectParent);
/* 337 */     next.add(childrens);
/* 338 */     next.add(thisMutations);
/*     */     
/*     */ 
/* 341 */     for (int i = 0; i < childrens.length; i++) {
/* 342 */       for (int j = 0; j < childrens[0].length; j++) {
/* 343 */         generation[(i * 2 + j)] = childrens[i][j];
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 349 */     for (int i = 0; i < generation.length; i++) {
/* 350 */       this.individual[(this.favoredPopulationSize + i)] = generation[i];
/*     */     }
/* 352 */     return next;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\Population.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */