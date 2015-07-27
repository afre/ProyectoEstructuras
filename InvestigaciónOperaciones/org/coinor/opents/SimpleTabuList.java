/*     */ package org.coinor.opents;
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
/*     */ 
/*     */ 
/*     */ public class SimpleTabuList
/*     */   implements TabuList
/*     */ {
/*  53 */   public static final int DEFAULT_TENURE = 10;
/*     */   
/*     */ 
/*     */   private int tenure;
/*     */   
/*     */ 
/*     */   private int[] tabuList;
/*     */   
/*     */ 
/*     */   private int currentPos;
/*     */   
/*     */ 
/*     */   private int listLength;
/*     */   
/*     */ 
/*  68 */   private static final double LIST_GROW_FACTOR = 2.0D;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleTabuList()
/*     */   {
/*  78 */     this(10);
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
/*     */ 
/*     */   public SimpleTabuList(int tenure)
/*     */   {
/*  92 */     this.tenure = tenure;
/*  93 */     this.listLength = ((int)(tenure * 2.0D));
/*  94 */     this.tabuList = new int[this.listLength];
/*  95 */     this.currentPos = 0;
/*  96 */     for (int i = 0; i < this.listLength; i++) {
/*  97 */       this.tabuList[i] = Integer.MIN_VALUE;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isTabu(Solution fromSolution, Move move)
/*     */   {
/* 118 */     int attr = move.hashCode();
/*     */     
/* 120 */     for (int i = 1; i <= this.tenure; i++) {
/* 121 */       if (this.currentPos - i < 0) {
/* 122 */         return false;
/*     */       }
/* 124 */       if (attr == this.tabuList[((this.currentPos - i) % this.listLength)])
/* 125 */         return true; }
/* 126 */     return false;
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
/*     */   public void setTabu(Solution fromSolution, Move move)
/*     */   {
/* 149 */     this.tabuList[(this.currentPos++ % this.listLength)] = move.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getTenure()
/*     */   {
/* 160 */     return this.tenure;
/*     */   }
/*     */   
/*     */   public int[] getTabuList() {
/* 164 */     return this.tabuList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTenure(int tenure)
/*     */   {
/* 180 */     if (tenure < 0) {
/* 181 */       return;
/*     */     }
/* 183 */     if ((tenure > this.tenure) && (tenure > this.tabuList.length))
/*     */     {
/* 185 */       this.listLength = ((int)(tenure * 2.0D));
/* 186 */       int[] newTabuList = new int[this.listLength];
/* 187 */       System.arraycopy(this.tabuList, 0, newTabuList, 0, this.tabuList.length);
/* 188 */       this.tabuList = newTabuList;
/*     */     }
/*     */     
/* 191 */     this.tenure = tenure;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\SimpleTabuList.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */