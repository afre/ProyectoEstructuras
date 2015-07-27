/*     */ package gatsp;
/*     */ 
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
/*     */ {
/*  16 */   public static final int POPULATION_SIZES = 10;
/*  17 */   public static final double MUTATION_PERCENT = 0.1D;
/*  18 */   private int matingPopulationSize = 6;
/*  19 */   private int favoredPopulationSize = 4;
/*  20 */   private int noFavoredPopulationSize = 2;
/*     */   private Chromosome[][] allParent;
/*     */   private Chromosome[] individual;
/*     */   private Chromosome[] storePopulation;
/*     */   private FitnessFunction fitnessFunc;
/*     */   private double[][] customers;
/*  26 */   private HashMap linkMap = new HashMap();
/*     */   private HashMap parentSelect;
/*     */   private ArrayList all_output;
/*  29 */   private boolean mutation = false;
/*     */   
/*     */ 
/*     */   private int[] mutationTour;
/*     */   
/*     */   private int[][] mutationChildren;
/*     */   
/*     */ 
/*     */   public Population(double[][] citys)
/*     */   {
/*  39 */     this.customers = ((double[][])citys.clone());
/*  40 */     this.fitnessFunc = new FitnessFunction(this.customers);
/*  41 */     initLinks();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Chromosome[] storeCurrentPopulation()
/*     */   {
/*  50 */     this.parentSelect = new HashMap();
/*  51 */     Chromosome[] lastIteration = new Chromosome[this.individual.length];
/*  52 */     for (int i = 0; i < this.individual.length; i++) {
/*  53 */       lastIteration[i] = this.individual[i];
/*  54 */       this.parentSelect.put(this.individual[i], new Integer(i));
/*     */     }
/*  56 */     return lastIteration;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void initPopulation()
/*     */   {
/*  63 */     this.individual = new Chromosome[10];
/*  64 */     for (int i = 0; i < 10; i++) {
/*  65 */       this.individual[i] = new Chromosome(this.fitnessFunc, getRandomTrialTours());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void sortChromosomes()
/*     */   {
/*  73 */     Chromosome.sortChromosomes(this.individual);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList nextIteration()
/*     */   {
/*  81 */     this.storePopulation = storeCurrentPopulation();
/*  82 */     ArrayList generation = generation();
/*  83 */     Chromosome.sortChromosomes(this.individual);
/*  84 */     Chromosome best = this.individual[0];
/*  85 */     this.all_output = new ArrayList();
/*  86 */     this.all_output.add(best);
/*  87 */     this.all_output.add(this.storePopulation);
/*  88 */     this.all_output.add(generation);
/*  89 */     return this.all_output;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void initLinks()
/*     */   {
/*  96 */     for (int i = 0; i < this.customers.length; i++) {
/*  97 */       ArrayList link = new ArrayList();
/*  98 */       for (int j = 0; j < this.customers.length; j++) {
/*  99 */         if ((this.customers[i][j] < Integer.MAX_VALUE) && (this.customers[i][j] != 0)) {
/* 100 */           link.add(new Integer(j));
/*     */         }
/*     */       }
/* 103 */       this.linkMap.put(new Integer(i), link);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean hasFeasibleLink(HashMap has, ArrayList checkLink)
/*     */   {
/* 114 */     for (int i = 0; i < checkLink.size(); i++) {
/* 115 */       Integer temp = (Integer)checkLink.get(i);
/* 116 */       if (!has.containsKey(temp)) {
/* 117 */         return true;
/*     */       }
/*     */     }
/* 120 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   private int[] getRandomTrialTours()
/*     */   {
/*     */     int[] tour;
/*     */     
/*     */     for (;;)
/*     */     {
/* 130 */       tour = new int[this.customers.length];
/* 131 */       tour[0] = 0;
/* 132 */       int current = 0;
/* 133 */       HashMap hasLink = new HashMap();
/* 134 */       hasLink.put(new Integer(current), new Integer(current));
/* 135 */       boolean feasible = true;
/* 136 */       for (int i = 1; i < tour.length; i++) {
/* 137 */         ArrayList check = (ArrayList)this.linkMap.get(new Integer(current));
/* 138 */         if (i == tour.length - 1) {
/* 139 */           tour[i] = getLastCityNotInTour(tour);
/*     */         } else {
/*     */           Integer temp;
/*     */           do {
/* 143 */             if (!hasFeasibleLink(hasLink, check)) {
/* 144 */               feasible = false;
/* 145 */               break;
/*     */             }
/* 147 */             random = (int)(Math.random() * check.size());
/* 148 */             temp = (Integer)check.get(random);
/* 149 */           } while (hasLink.containsKey(temp));
/* 150 */           int random = temp.intValue();
/* 151 */           hasLink.put(new Integer(random), new Integer(random));
/* 152 */           tour[i] = random;
/* 153 */           current = random;
/*     */           
/*     */ 
/*     */ 
/* 157 */           if (!feasible)
/*     */             break;
/*     */         }
/*     */       }
/* 161 */       if ((feasible) && (this.fitnessFunc.feasible(tour))) {
/*     */         break;
/*     */       }
/*     */     }
/* 165 */     return tour;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int getLastCityNotInTour(int[] tour)
/*     */   {
/* 174 */     int size = this.customers.length - 1;
/* 175 */     for (int i = 1; i <= size; i++) {
/* 176 */       boolean last = true;
/* 177 */       for (int j = 0; j < tour.length; j++) {
/* 178 */         if (tour[j] == i) {
/* 179 */           last = false;
/* 180 */           break;
/*     */         }
/*     */       }
/* 183 */       if (last) {
/* 184 */         return i;
/*     */       }
/*     */     }
/* 187 */     return size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Chromosome[] randomGetFavored()
/*     */   {
/* 195 */     Chromosome[] favored = new Chromosome[this.favoredPopulationSize];
/* 196 */     int size = 10 / 2;
/* 197 */     HashMap map = new HashMap();
/*     */     
/* 199 */     for (int i = 0; i < this.favoredPopulationSize; i++) {
/*     */       int temp;
/* 201 */       while (map.containsKey(new Integer(temp = (int)(Math.random() * size)))) {}
/*     */       
/* 203 */       map.put(new Integer(temp), new Integer(temp));
/* 204 */       favored[i] = this.individual[temp];
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 209 */     return favored;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Chromosome[] randomGetNoFavored()
/*     */   {
/* 217 */     Chromosome[] favored = new Chromosome[this.noFavoredPopulationSize];
/* 218 */     int size = 10 / 2;
/* 219 */     HashMap map = new HashMap();
/*     */     
/* 221 */     for (int i = 0; i < this.noFavoredPopulationSize; i++) {
/*     */       int temp;
/* 223 */       while (map.containsKey(new Integer(temp = (int)(Math.random() * size)))) {}
/*     */       
/* 225 */       map.put(new Integer(temp), new Integer(temp));
/* 226 */       favored[i] = this.individual[(size + temp)];
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 231 */     return favored;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Chromosome[][] randomChooseParents()
/*     */   {
/* 239 */     Chromosome[] favored = randomGetFavored();
/* 240 */     Chromosome[] nofavored = randomGetNoFavored();
/* 241 */     int size = this.favoredPopulationSize + this.noFavoredPopulationSize - 1;
/*     */     
/* 243 */     Chromosome[] allSelected = new Chromosome[this.favoredPopulationSize + this.noFavoredPopulationSize];
/*     */     
/* 245 */     for (int i = 0; i < favored.length; i++) {
/* 246 */       allSelected[i] = favored[i];
/*     */     }
/* 248 */     for (int i = 0; i < nofavored.length; i++) {
/* 249 */       allSelected[(favored.length + i)] = nofavored[i];
/*     */     }
/*     */     
/* 252 */     Chromosome.sortChromosomes(allSelected);
/* 253 */     HashMap map = new HashMap();
/* 254 */     int[] last = new int[2];
/* 255 */     Chromosome[][] parents = new Chromosome[(this.favoredPopulationSize + this.noFavoredPopulationSize) / 2][2];
/*     */     
/*     */ 
/* 258 */     for (int i = 0; 
/*     */         
/* 260 */         i < (this.favoredPopulationSize + this.noFavoredPopulationSize) / 2; i++) {
/*     */       int temp;
/*     */       int temp1;
/* 263 */       do { if (i == (this.favoredPopulationSize + this.noFavoredPopulationSize) / 2 - 1)
/*     */         {
/*     */ 
/* 266 */           for (int j = 0; j < last.length; j++) {
/* 267 */             for (int k = 0; k < allSelected.length; k++) {
/* 268 */               if (!map.containsKey(new Integer(k))) {
/* 269 */                 last[j] = k;
/* 270 */                 map.put(new Integer(k), new Integer(k));
/* 271 */                 break;
/*     */               }
/*     */             }
/*     */           }
/* 275 */           parents[i][0] = allSelected[last[0]];
/* 276 */           parents[i][1] = allSelected[last[1]];
/* 277 */           break;
/*     */         }
/*     */         
/*     */ 
/* 281 */         temp = (int)(Math.random() * size);
/* 282 */         temp1 = (int)(Math.random() * size);
/* 283 */       } while ((temp == temp1) || (map.containsKey(new Integer(temp))) || (map.containsKey(new Integer(temp1))));
/*     */       
/*     */ 
/* 286 */       map.put(new Integer(temp), new Integer(temp));
/* 287 */       map.put(new Integer(temp1), new Integer(temp1));
/* 288 */       parents[i][0] = allSelected[temp];
/* 289 */       parents[i][1] = allSelected[temp1];
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 294 */     return parents;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Chromosome[] crossover(Chromosome[] parent)
/*     */   {
/* 303 */     Chromosome father = (Chromosome)parent[0].clone();
/* 304 */     Chromosome mother = (Chromosome)parent[1].clone();
/* 305 */     this.mutationChildren = new int[2][father.getTours().length];
/* 306 */     Chromosome[] allChildren = new Chromosome[2];
/* 307 */     allChildren[0] = new Chromosome(this.fitnessFunc, getChildrenTour(father, mother));
/*     */     
/* 309 */     this.mutationChildren[0] = this.mutationTour;
/* 310 */     allChildren[1] = new Chromosome(this.fitnessFunc, getChildrenTour(father, mother));
/*     */     
/* 312 */     this.mutationChildren[1] = this.mutationTour;
/* 313 */     return allChildren;
/*     */   }
/*     */   
/*     */ 
/*     */   private int[] getChildrenTour(Chromosome father, Chromosome mother)
/*     */   {
/*     */     int[] tour;
/*     */     
/*     */     boolean feasible;
/*     */     do
/*     */     {
/* 324 */       tour = new int[this.customers.length];
/* 325 */       this.mutationTour = new int[this.customers.length];
/* 326 */       tour[0] = 0;
/* 327 */       int current = 0;
/* 328 */       HashMap hasLink = new HashMap();
/* 329 */       hasLink.put(new Integer(current), new Integer(current));
/* 330 */       feasible = true;
/* 331 */       for (int i = 1; i < tour.length; i++) {
/* 332 */         if (i == tour.length - 1) {
/* 333 */           tour[i] = getLastCityNotInTour(tour);
/* 334 */           break;
/*     */         }
/* 336 */         ArrayList fGene = father.getGenes(current, hasLink);
/* 337 */         ArrayList mGene = mother.getGenes(current, hasLink);
/* 338 */         if (hasSubTour(father.getTours(), tour, i - 1)) {
/* 339 */           fGene.add(subTourReversal(father, tour[(i - 2)], current));
/*     */         }
/* 341 */         if (hasSubTour(mother.getTours(), tour, i - 1)) {
/* 342 */           mGene.add(subTourReversal(mother, tour[(i - 2)], current));
/*     */         }
/*     */         
/* 345 */         ArrayList all = getNoRepeat(fGene, mGene);
/* 346 */         if (all.size() == 0) {
/* 347 */           feasible = false;
/* 348 */           break;
/*     */         }
/* 350 */         if (!isFeasibleGene(current, all, hasLink)) {
/* 351 */           feasible = false;
/* 352 */           break;
/*     */         }
/*     */         do {
/* 355 */           random = (int)(Math.random() * all.size());
/* 356 */         } while (hasLink.containsKey(all.get(random)));
/* 357 */         Integer temp = (Integer)all.get(random);
/* 358 */         int random = mutation(current, hasLink, temp.intValue());
/* 359 */         if (this.mutation) {
/* 360 */           this.mutationTour[i] = 1;
/*     */           
/* 362 */           this.mutation = false;
/*     */         }
/* 364 */         current = random;
/* 365 */         hasLink.put(new Integer(random), new Integer(random));
/* 366 */         tour[i] = current;
/*     */ 
/*     */       }
/*     */       
/*     */     }
/* 371 */     while ((!feasible) || (!this.fitnessFunc.feasible(tour)));
/* 372 */     return tour;
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
/*     */   private boolean isFeasibleGene(int current, ArrayList allGene, HashMap hasLink)
/*     */   {
/* 385 */     if (allGene == null) return false;
/* 386 */     boolean hasSelect = false;
/* 387 */     for (int i = 0; i < allGene.size(); i++) {
/* 388 */       Integer select = (Integer)allGene.get(i);
/* 389 */       if (!hasLink.containsKey(select)) {
/* 390 */         hasSelect = true;
/*     */       }
/*     */     }
/* 393 */     return hasSelect;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int mutation(int current, HashMap hasLink, int random)
/*     */   {
/* 405 */     if (Math.random() < 0.1D) {
/* 406 */       ArrayList currentAll = (ArrayList)this.linkMap.get(new Integer(current));
/* 407 */       if (currentAll == null)
/* 408 */         return random;
/* 409 */       boolean hasSelect = false;
/* 410 */       for (int i = 0; i < currentAll.size(); i++) {
/* 411 */         Integer select = (Integer)currentAll.get(i);
/* 412 */         if ((select.intValue() != random) && (!hasLink.containsKey(select))) {
/* 413 */           hasSelect = true;
/*     */         }
/*     */       }
/* 416 */       this.mutation = true;
/* 417 */       if (!hasSelect) return random;
/*     */       Integer result;
/* 419 */       do { int temp; do { temp = (int)(Math.random() * currentAll.size());
/* 420 */         } while (hasLink.containsKey(currentAll.get(temp)));
/* 421 */         result = (Integer)currentAll.get(temp);
/* 422 */       } while (result.intValue() == random);
/* 423 */       return result.intValue();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 428 */     return random;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean hasSubTour(int[] parent, int[] tour, int sub_position)
/*     */   {
/* 439 */     if (sub_position < 1) {
/* 440 */       return false;
/*     */     }
/* 442 */     ArrayList allMoves = getAllMoves(parent);
/* 443 */     int[] temp = (int[])tour.clone();
/* 444 */     int portion = temp[sub_position];
/* 445 */     temp[sub_position] = temp[(sub_position - 1)];
/* 446 */     temp[(sub_position - 1)] = portion;
/* 447 */     boolean isSub = false;
/* 448 */     for (int i = 0; i <= sub_position; i++) {
/* 449 */       if (parent[i] == temp[i]) {
/* 450 */         isSub = true;
/*     */       }
/*     */       else
/*     */       {
/* 454 */         isSub = false;
/* 455 */         break;
/*     */       }
/*     */     }
/* 458 */     return isSub;
/*     */   }
/*     */   
/*     */   private ArrayList getAllMoves(int[] tours) {
/* 462 */     int[] tour = (int[])tours.clone();
/* 463 */     int[][] buffer = new int[tour.length * tour.length][2];
/* 464 */     int nextBufferPos = 0;
/* 465 */     ArrayList moves = new ArrayList();
/*     */     
/* 467 */     for (int i = 1; i < tour.length - 1; i++) {
/* 468 */       for (int j = i + 1; j <= tour.length - 1; j++) {
/* 469 */         if ((i != 1) || (j != tour.length - 1))
/*     */         {
/* 471 */           int[] sub = (int[])tour.clone();
/* 472 */           for (int k = 0; i <= i - j; i++) {
/* 473 */             sub[(k + i)] = tour[(j - k)];
/*     */           }
/* 475 */           if (this.fitnessFunc.feasible(sub))
/* 476 */             moves.add(sub);
/*     */         }
/*     */       }
/*     */     }
/* 480 */     return moves;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ArrayList getNoRepeat(ArrayList f, ArrayList m)
/*     */   {
/* 490 */     ArrayList all = new ArrayList();
/* 491 */     for (int i = 0; i < f.size(); i++) {
/* 492 */       all.add(f.get(i));
/*     */     }
/* 494 */     for (int i = 0; i < m.size(); i++) {
/* 495 */       if (!all.contains(m.get(i))) {
/* 496 */         all.add(m.get(i));
/*     */       }
/*     */     }
/* 499 */     return all;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Integer subTourReversal(Chromosome parent, int before, int last)
/*     */   {
/* 510 */     int[] tour = (int[])parent.getTours().clone();
/* 511 */     int temp = 0;
/* 512 */     int temp1 = 0;
/* 513 */     for (int i = 0; i < tour.length; i++) {
/* 514 */       if (before == tour[i]) {
/* 515 */         temp = i;
/*     */ 
/*     */       }
/* 518 */       else if (last == tour[i]) {
/* 519 */         temp1 = i;
/*     */       }
/*     */     }
/*     */     
/* 523 */     int result = tour[temp];
/* 524 */     tour[temp] = tour[temp1];
/* 525 */     tour[temp1] = result;
/* 526 */     return new Integer(tour[(temp1 + 1)]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private ArrayList generation()
/*     */   {
/* 533 */     ArrayList next = new ArrayList();
/* 534 */     ArrayList thisMutations = new ArrayList();
/* 535 */     this.allParent = randomChooseParents();
/* 536 */     Chromosome[][] childrens = new Chromosome[this.allParent.length][2];
/* 537 */     Chromosome[] generation = new Chromosome[this.matingPopulationSize];
/* 538 */     for (int i = 0; i < this.allParent.length; i++) {
/* 539 */       childrens[i] = crossover(this.allParent[i]);
/* 540 */       thisMutations.add(this.mutationChildren);
/*     */     }
/*     */     
/*     */ 
/* 544 */     int[][] selectParent = new int[this.allParent.length][this.allParent[0].length];
/* 545 */     for (int i = 0; i < this.allParent.length; i++) {
/* 546 */       for (int j = 0; j < this.allParent[0].length; j++) {
/* 547 */         if (this.parentSelect.containsKey(this.allParent[i][j])) {
/* 548 */           Integer temp = (Integer)this.parentSelect.get(this.allParent[i][j]);
/* 549 */           selectParent[i][j] = temp.intValue();
/*     */         }
/*     */       }
/*     */     }
/* 553 */     next.add(this.allParent);
/* 554 */     next.add(selectParent);
/* 555 */     next.add(childrens);
/* 556 */     next.add(thisMutations);
/*     */     
/*     */ 
/* 559 */     for (int i = 0; i < childrens.length; i++) {
/* 560 */       for (int j = 0; j < childrens[0].length; j++) {
/* 561 */         generation[(i * 2 + j)] = childrens[i][j];
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 567 */     for (int i = 0; i < generation.length; i++) {
/* 568 */       this.individual[(this.favoredPopulationSize + i)] = generation[i];
/*     */     }
/* 570 */     return next;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\gatsp\Population.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */