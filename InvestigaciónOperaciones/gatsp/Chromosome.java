/*     */ package gatsp;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Chromosome
/*     */   implements Serializable
/*     */ {
/*     */   private HashMap gene;
/*     */   private double fitness;
/*     */   private int[] tours;
/*     */   
/*     */   public Chromosome(FitnessFunction fitnessFunc, int[] tour)
/*     */   {
/*  22 */     this.fitness = fitnessFunc.evaluate(tour);
/*  23 */     this.tours = ((int[])tour.clone());
/*  24 */     this.gene = setGene();
/*     */   }
/*     */   
/*     */   public Chromosome() {}
/*     */   
/*     */   public int[] getTours() {
/*  30 */     return this.tours;
/*     */   }
/*     */   
/*     */   public void setFitness(double fit) {
/*  34 */     this.fitness = fit;
/*     */   }
/*     */   
/*     */   public double getFitness() {
/*  38 */     return this.fitness;
/*     */   }
/*     */   
/*     */   protected HashMap setGene() {
/*  42 */     HashMap map = new HashMap();
/*  43 */     for (int i = 0; i < this.tours.length; i++) {
/*  44 */       ArrayList check = new ArrayList();
/*  45 */       int position = 0;
/*  46 */       for (int j = 0; j < this.tours.length; j++) {
/*  47 */         if (i == this.tours[j]) {
/*  48 */           position = j;
/*  49 */           break;
/*     */         }
/*     */       }
/*  52 */       if (position == 0) {
/*  53 */         check.add(new Integer(this.tours[(position + 1)]));
/*  54 */         check.add(new Integer(this.tours[(this.tours.length - 1)]));
/*  55 */       } else if (position == this.tours.length - 1) {
/*  56 */         check.add(new Integer(0));
/*  57 */         check.add(new Integer(this.tours[(position - 1)]));
/*     */       } else {
/*  59 */         check.add(new Integer(this.tours[(position - 1)]));
/*  60 */         check.add(new Integer(this.tours[(position + 1)]));
/*     */       }
/*     */       
/*  63 */       map.put(new Integer(i), check);
/*     */     }
/*  65 */     return map;
/*     */   }
/*     */   
/*     */   protected ArrayList getGenes(int startCity, HashMap inTour) {
/*  69 */     ArrayList check = (ArrayList)this.gene.get(new Integer(startCity));
/*  70 */     ArrayList result = new ArrayList();
/*  71 */     for (int i = 0; i < check.size(); i++) {
/*  72 */       Integer temp = (Integer)check.get(i);
/*  73 */       if (!inTour.containsKey(temp)) {
/*  74 */         result.add(temp);
/*     */       }
/*     */     }
/*  77 */     return result;
/*     */   }
/*     */   
/*     */   public Object clone() {
/*  81 */     Chromosome copy = new Chromosome();
/*  82 */     copy.gene = setGene();
/*  83 */     copy.fitness = this.fitness;
/*  84 */     copy.tours = ((int[])this.tours.clone());
/*  85 */     return copy;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void sortChromosomes(Chromosome[] chromosomes)
/*     */   {
/*  94 */     boolean swapped = true;
/*  95 */     while (swapped) {
/*  96 */       swapped = false;
/*  97 */       for (int i = 0; i < chromosomes.length - 1; i++) {
/*  98 */         if (chromosomes[i].getFitness() > chromosomes[(i + 1)].getFitness()) {
/*  99 */           Chromosome ctemp = chromosomes[i];
/* 100 */           chromosomes[i] = chromosomes[(i + 1)];
/* 101 */           chromosomes[(i + 1)] = ctemp;
/* 102 */           swapped = true;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\gatsp\Chromosome.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */