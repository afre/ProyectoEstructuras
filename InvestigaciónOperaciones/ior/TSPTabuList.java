/*     */ package ior;
/*     */ 
/*     */ import org.coinor.opents.Move;
/*     */ import org.coinor.opents.Solution;
/*     */ 
/*     */ public class TSPTabuList implements org.coinor.opents.TabuList
/*     */ {
/*   8 */   public static final int DEFAULT_TENURE = 10;
/*     */   
/*     */   private int tenure;
/*     */   
/*     */   private Queue list;
/*     */   private Queue stringList;
/*     */   private int currentPos;
/*     */   private int listLength;
/*  16 */   private static final double LIST_GROW_FACTOR = 2.0D;
/*     */   
/*     */   public TSPTabuList() {
/*  19 */     this(10);
/*     */   }
/*     */   
/*     */   public TSPTabuList(int tenure)
/*     */   {
/*  24 */     this.tenure = tenure;
/*  25 */     this.listLength = ((int)(tenure * 2.0D));
/*  26 */     this.currentPos = 0;
/*  27 */     this.list = new Queue();
/*  28 */     this.stringList = new Queue();
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
/*     */   public boolean isTabu(Solution fromSolution, Move move)
/*     */   {
/*  46 */     int[] test = move.identifyMove();
/*  47 */     int begin = test[0];
/*  48 */     int end = test[1];
/*     */     
/*  50 */     MySolution sol = (MySolution)fromSolution;
/*  51 */     int[] tours = (int[])sol.tour.clone();
/*     */     
/*  53 */     int temp = end + 1;
/*  54 */     if (end == tours.length - 1) {
/*  55 */       temp = 0;
/*     */     }
/*  57 */     int[][] all = { { tours[(begin - 1)] + 1, tours[begin] + 1 }, { tours[end] + 1, tours[temp] + 1 } };
/*     */     
/*     */ 
/*  60 */     int[][] result = new int[4][2];
/*  61 */     if (this.list.size() == 0) { return false;
/*     */     }
/*  63 */     Object obj1 = this.list.elementAt(0);
/*  64 */     int[][] t1 = (int[][])obj1;
/*  65 */     result[0] = t1[0];
/*  66 */     result[1] = t1[1];
/*  67 */     if (this.list.size() > 1) {
/*  68 */       Object obj2 = this.list.elementAt(1);
/*  69 */       int[][] t2 = (int[][])obj2;
/*  70 */       result[2] = t2[0];
/*  71 */       result[3] = t2[1];
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  77 */     if (judge(all, result)) { return true;
/*     */     }
/*  79 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean judge(int[][] first, int[][] second)
/*     */   {
/*  84 */     for (int i = 0; i < first.length; i++) {
/*  85 */       boolean hasOneLink = false;
/*  86 */       for (int j = 0; j < second.length; j++) {
/*  87 */         if (judgeArrayContains(first[i], second[j])) {
/*  88 */           hasOneLink = true;
/*     */         }
/*     */       }
/*  91 */       if (!hasOneLink) return false;
/*     */     }
/*  93 */     return true;
/*     */   }
/*     */   
/*     */   private boolean judgeArrayContains(int[] begin, int[] end) {
/*  97 */     for (int i = 0; i < begin.length; i++) {
/*  98 */       boolean has = false;
/*  99 */       for (int j = 0; j < end.length; j++) {
/* 100 */         if (begin[i] == end[j]) {
/* 101 */           has = true;
/* 102 */           break;
/*     */         }
/*     */       }
/* 105 */       if (!has) return false;
/*     */     }
/* 107 */     return true;
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
/*     */   public void setTabu(Solution fromSolution, Move move)
/*     */   {
/* 129 */     int[] test = move.identifyMove();
/* 130 */     int begin = test[0];
/* 131 */     int end = test[1];
/* 132 */     MySolution sol = (MySolution)fromSolution;
/*     */     
/* 134 */     int[] tours = (int[])sol.tour.clone();
/*     */     
/*     */ 
/* 137 */     int temp = end + 1;
/* 138 */     if (end == tours.length - 1) {
/* 139 */       temp = 0;
/*     */     }
/* 141 */     int[][] all = { { tours[(begin - 1)] + 1, tours[end] + 1 }, { tours[begin] + 1, tours[temp] + 1 } };
/*     */     
/*     */ 
/* 144 */     String link1 = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(tours[(begin - 1)] + 1))).append("-").append(tours[end] + 1)));
/* 145 */     String link2 = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(tours[begin] + 1))).append("-").append(tours[temp] + 1)));
/*     */     
/*     */ 
/* 148 */     if (this.list.size() >= this.tenure) {
/* 149 */       this.list.dequeue();
/* 150 */       this.stringList.dequeue();
/*     */     }
/*     */     
/*     */ 
/* 154 */     this.stringList.enqueue(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(link1))).append(",").append(link2))));
/* 155 */     this.list.enqueue(all);
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
/* 166 */     return this.tenure;
/*     */   }
/*     */   
/*     */   public void setTenure(int tenure) {
/* 170 */     if (tenure < 0)
/* 171 */       return;
/* 172 */     this.tenure = tenure;
/*     */   }
/*     */   
/*     */   protected void cleanTabuList() {
/* 176 */     this.list.removeAllElements();
/* 177 */     this.stringList.removeAllElements();
/*     */   }
/*     */   
/*     */   public Queue getList() {
/* 181 */     return this.list;
/*     */   }
/*     */   
/*     */   public void setTabuOnly(int[][] link) {
/* 185 */     String link1 = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(link[0][0]))).append("-").append(link[0][1])));
/* 186 */     String link2 = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(link[1][0]))).append("-").append(link[1][1])));
/* 187 */     this.stringList.enqueue(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(link1))).append(",").append(link2))));
/* 188 */     int[][] all = (int[][])link.clone();
/* 189 */     this.list.enqueue(all);
/*     */   }
/*     */   
/*     */   public void setTabuList(Solution sol, MySwapMove move, int[][] link)
/*     */   {
/* 194 */     int begin = move.begin;
/* 195 */     int end = move.end;
/* 196 */     int temp = end + 1;
/* 197 */     MySolution my = (MySolution)sol;
/* 198 */     int[] tours = (int[])my.tour.clone();
/* 199 */     if (end == tours.length - 1) {
/* 200 */       temp = 0;
/*     */     }
/* 202 */     int[][] all = { { tours[(begin - 1)] + 1, tours[end] + 1 }, { tours[begin] + 1, tours[temp] + 1 } };
/*     */     
/*     */ 
/*     */ 
/* 206 */     String link1 = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(tours[(begin - 1)] + 1))).append("-").append(tours[end] + 1)));
/* 207 */     String link2 = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(tours[begin] + 1))).append("-").append(tours[temp] + 1)));
/* 208 */     this.stringList.enqueue(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(link1))).append(",").append(link2))));
/* 209 */     this.list.enqueue(all);
/*     */   }
/*     */   
/*     */   public String getTabuListInfo() {
/* 213 */     String temp = "";
/* 214 */     for (int i = 0; i < this.stringList.size(); i++) {
/* 215 */       Object obj = this.stringList.elementAt(i);
/* 216 */       if (obj != null) {
/* 217 */         String ss = (String)obj;
/* 218 */         temp = String.valueOf(String.valueOf(temp)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(ss)).concat(","))));
/*     */       }
/*     */     }
/* 221 */     if (temp.length() > 0) temp = temp.substring(0, temp.length() - 1);
/* 222 */     return temp;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 226 */     for (int i = 0; i < this.stringList.size(); i++) {
/* 227 */       Object obj = this.stringList.elementAt(i);
/* 228 */       if (obj != null) {
/* 229 */         String str = (String)obj;
/*     */       }
/*     */     }
/* 232 */     return "";
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ior\TSPTabuList.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */