/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Chromosome implements Serializable
/*     */ {
/*     */   private int[] variables;
/*     */   private byte[][] gene;
/*     */   private double fitness;
/*     */   private int[] geneLength;
/*     */   private boolean feasible;
/*     */   
/*     */   public Chromosome(FitnessFunction fitnessFunc, int[] variable, int[] varBound)
/*     */   {
/*  13 */     this.variables = ((int[])variable.clone());
/*  14 */     this.fitness = fitnessFunc.evaluate(this.variables);
/*  15 */     this.geneLength = new int[this.variables.length];
/*  16 */     setGeneLength(varBound);
/*  17 */     this.feasible = fitnessFunc.feasible(this.variables);
/*     */   }
/*     */   
/*     */   public Chromosome(FitnessFunction func, byte[][] genes) {
/*  21 */     this.gene = ((byte[][])genes.clone());
/*  22 */     this.variables = new int[this.gene.length];
/*  23 */     this.geneLength = new int[this.gene.length];
/*  24 */     twoToTen();
/*  25 */     this.feasible = func.feasible(this.variables);
/*  26 */     this.fitness = func.evaluate(this.variables);
/*  27 */     for (int i = 0; i < this.gene.length; i++) {
/*  28 */       this.geneLength[i] = this.gene[i].length;
/*     */     }
/*     */   }
/*     */   
/*     */   public Chromosome() {}
/*     */   
/*     */   public boolean getFeasible() {
/*  35 */     return this.feasible;
/*     */   }
/*     */   
/*  38 */   public void setFitness(double fit) { this.fitness = fit; }
/*     */   
/*     */   public int[] getVariables() {
/*  41 */     return this.variables;
/*     */   }
/*     */   
/*     */   public byte[][] getGene() {
/*  45 */     return this.gene;
/*     */   }
/*     */   
/*     */   public double getFitness() {
/*  49 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public Object clone() {
/*  53 */     Chromosome copy = new Chromosome();
/*  54 */     copy.variables = ((int[])this.variables.clone());
/*  55 */     copy.gene = ((byte[][])this.gene.clone());
/*  56 */     copy.fitness = this.fitness;
/*  57 */     copy.geneLength = ((int[])this.geneLength.clone());
/*  58 */     return copy;
/*     */   }
/*     */   
/*     */   private void setGeneLength(int[] varBound) {
/*  62 */     this.gene = new byte[varBound.length][];
/*  63 */     for (int i = 0; i < varBound.length; i++) {
/*  64 */       this.geneLength[i] = Integer.toBinaryString(varBound[i]).length();
/*  65 */       this.gene[i] = new byte[this.geneLength[i]];
/*     */     }
/*  67 */     tenToTwo();
/*     */   }
/*     */   
/*     */   private void twoToTen() {
/*  71 */     for (int i = 0; i < this.gene.length; i++) {
/*  72 */       String temp = "";
/*  73 */       for (int j = 0; j < this.gene[0].length; j++) {
/*  74 */         temp = String.valueOf(String.valueOf(temp)).concat(String.valueOf(String.valueOf(Byte.toString(this.gene[i][j]))));
/*     */       }
/*  76 */       this.variables[i] = Integer.valueOf(temp, 2).intValue();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void tenToTwo()
/*     */   {
/*  84 */     for (int i = 0; i < this.variables.length; i++) {
/*  85 */       String temp = Integer.toBinaryString(this.variables[i]);
/*  86 */       int length = temp.length();
/*  87 */       for (int k = 0; k < this.geneLength[i] - length; k++) {
/*  88 */         temp = "0".concat(String.valueOf(String.valueOf(temp)));
/*     */       }
/*  90 */       for (int j = 0; j < temp.length(); j++) {
/*  91 */         this.gene[i][j] = Byte.parseByte(new String(new char[] { temp.charAt(j) }));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void sortChromosomes(Chromosome[] chromosomes, boolean max_min)
/*     */   {
/*  98 */     boolean swapped = true;
/*  99 */     while (swapped) {
/* 100 */       swapped = false;
/* 101 */       for (int i = 0; i < chromosomes.length - 1; i++) {
/* 102 */         if (max_min) {
/* 103 */           if (chromosomes[i].getFitness() < chromosomes[(i + 1)].getFitness())
/*     */           {
/* 105 */             Chromosome ctemp = chromosomes[i];
/* 106 */             chromosomes[i] = chromosomes[(i + 1)];
/* 107 */             chromosomes[(i + 1)] = ctemp;
/* 108 */             swapped = true;
/*     */           }
/*     */         }
/* 111 */         else if (chromosomes[i].getFitness() > chromosomes[(i + 1)].getFitness())
/*     */         {
/* 113 */           Chromosome ctemp = chromosomes[i];
/* 114 */           chromosomes[i] = chromosomes[(i + 1)];
/* 115 */           chromosomes[(i + 1)] = ctemp;
/* 116 */           swapped = true;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\Chromosome.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */