/*     */ package ior;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import org.coinor.opents.BestEverAspirationCriteria;
/*     */ import org.coinor.opents.Move;
/*     */ import org.coinor.opents.MoveManager;
/*     */ import org.coinor.opents.Solution;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TSPproblem
/*     */   implements Serializable
/*     */ {
/*     */   double[][] matrix;
/*  17 */   TSPTabuSearch tabuSearch = null;
/*  18 */   TSPTabuList tabuList = null;
/*  19 */   MyObjectiveFunction objFunc = null;
/*  20 */   MySolution current = null;
/*  21 */   MoveManager moveManager = null;
/*  22 */   Solution initialSolution = null;
/*     */   int[] tour;
/*     */   
/*     */   public TSPproblem(double[][] citys, int[] initSolution) {
/*  26 */     this.matrix = citys;
/*  27 */     this.tour = ((int[])initSolution.clone());
/*  28 */     this.objFunc = new MyObjectiveFunction(this.matrix);
/*  29 */     this.initialSolution = new MySolution(this.tour);
/*  30 */     this.current = ((MySolution)this.initialSolution.clone());
/*  31 */     this.moveManager = new MyMoveManager();
/*  32 */     this.tabuList = new TSPTabuList(2);
/*     */     
/*     */ 
/*  35 */     this.tabuSearch = new TSPTabuSearch(this.initialSolution, this.moveManager, this.objFunc, this.tabuList, new BestEverAspirationCriteria(), false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList startIteration()
/*     */   {
/*  42 */     ArrayList all = new ArrayList();
/*  43 */     while (this.tabuSearch.hasMoreSearch()) {
/*  44 */       ArrayList per = new ArrayList();
/*  45 */       this.current = ((MySolution)this.tabuSearch.getCurrentSolution());
/*  46 */       this.tabuList = ((TSPTabuList)this.tabuSearch.getTabuList());
/*     */       
/*     */ 
/*  49 */       per.add((MySolution)this.current.clone());
/*  50 */       per.add(this.tabuList.getTabuListInfo());
/*  51 */       all.add(per);
/*  52 */       this.tabuSearch.setIterationsToGo(1);
/*  53 */       this.tabuSearch.startSolving();
/*     */     }
/*  55 */     return all;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList getInitIteration()
/*     */   {
/*  62 */     double[] obj = this.objFunc.evaluate(this.current, null);
/*  63 */     ArrayList init = new ArrayList();
/*  64 */     init.add(this.tour);
/*  65 */     init.add(new Double(obj[0]));
/*  66 */     return init;
/*     */   }
/*     */   
/*     */   public boolean isFeasibleSolution(Move move)
/*     */   {
/*  71 */     boolean temp = this.tabuList.isTabu(this.current, move);
/*  72 */     if (temp) {
/*  73 */       return false;
/*     */     }
/*  75 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isFeasibleSolution(int[] tours, int[][][] tabu)
/*     */   {
/*  85 */     for (int i = 0; i < tabu.length; i++) {
/*  86 */       for (int j = 0; j < tabu[i].length; j++)
/*  87 */         if (!this.objFunc.isFeasibleLink(tabu[i][j])) return false;
/*     */     }
/*  89 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isImmediateNeighbor(int[] tours)
/*     */   {
/*  98 */     if (this.current == null) return false;
/*  99 */     Move[] moves = this.moveManager.getAllMoves(this.current);
/* 100 */     for (int i = 0; i < moves.length; i++) {
/* 101 */       int[] temp = operateOn(this.current, (MySwapMove)moves[i]);
/* 102 */       if (isEqualArray(tours, temp)) return true;
/*     */     }
/* 104 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFeasibleTabu(Move move, int[][][] tabu) {
/* 108 */     int[] test = move.identifyMove();
/* 109 */     int begin = test[0];
/* 110 */     int end = test[1];
/* 111 */     int[] tours = (int[])this.current.tour.clone();
/* 112 */     int temp = end + 1;
/* 113 */     if (end == tours.length - 1)
/* 114 */       temp = 0;
/* 115 */     int[][] all = { { tours[(begin - 1)] + 1, tours[end] + 1 }, { tours[begin] + 1, tours[temp] + 1 } };
/*     */     
/*     */ 
/* 118 */     Queue list = this.tabuList.getList();
/* 119 */     int[][] inTabu = new int[0][];
/* 120 */     if (list.size() == 2)
/* 121 */       inTabu = (int[][])list.elementAt(1);
/* 122 */     if (list.size() == 1) {
/* 123 */       inTabu = (int[][])list.elementAt(0);
/*     */     }
/*     */     
/*     */     int[][][] result;
/* 127 */     if (inTabu.length == 0) {
/* 128 */       int[][][] result = new int[1][][];
/* 129 */       result[0] = all;
/*     */     }
/*     */     else {
/* 132 */       result = new int[2][][];
/* 133 */       result[0] = inTabu;
/* 134 */       result[1] = all;
/*     */     }
/*     */     
/* 137 */     for (int i = 0; i < result.length; i++) {
/* 138 */       boolean has = false;
/* 139 */       for (int j = 0; j < tabu.length; j++) {
/* 140 */         if (this.tabuList.judge(result[i], tabu[j])) {
/* 141 */           has = true;
/* 142 */           break;
/*     */         }
/*     */       }
/* 145 */       if (!has) { return false;
/*     */       }
/*     */     }
/* 148 */     return true;
/*     */   }
/*     */   
/*     */   public ArrayList getImmediateNeighbor() {
/* 152 */     ArrayList imm = new ArrayList();
/* 153 */     Move[] moves = this.moveManager.getAllMoves(this.current);
/* 154 */     for (int i = 0; i < moves.length; i++) {
/* 155 */       int[] temp = operateOn(this.current, (MySwapMove)moves[i]);
/* 156 */       if (this.objFunc.isFeasibleSolution(temp)) {
/* 157 */         ArrayList per = new ArrayList();
/* 158 */         per.add(temp);
/* 159 */         per.add(new Double(this.objFunc.evaluate(this.current, moves[i])[0]));
/* 160 */         per.add(moves[i]);
/* 161 */         imm.add(per);
/*     */       }
/*     */     }
/* 164 */     return imm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int[] operateOn(MySolution soln, MySwapMove move)
/*     */   {
/* 174 */     MySolution so = soln;
/* 175 */     int begin = move.begin;
/* 176 */     int end = move.end;
/* 177 */     int[] tour = soln.tour;
/* 178 */     int[] sub = (int[])tour.clone();
/*     */     
/* 180 */     for (int i = 0; i <= end - begin; i++) {
/* 181 */       sub[(i + begin)] = tour[(end - i)];
/*     */     }
/* 183 */     return sub;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isEqualArray(int[] first, int[] second)
/*     */   {
/* 193 */     if (first.length != second.length) return false;
/* 194 */     for (int i = 0; i < first.length; i++) {
/* 195 */       if (first[i] != second[i]) return false;
/*     */     }
/* 197 */     return true;
/*     */   }
/*     */   
/*     */   public void setCurrent(int[] tours, int[][][] tabu) {
/* 201 */     MySolution my = new MySolution(tours);
/* 202 */     this.tabuList.cleanTabuList();
/* 203 */     for (int i = 0; i < tabu.length; i++)
/* 204 */       this.tabuList.setTabuOnly(tabu[i]);
/* 205 */     my.setObjectiveValue(this.objFunc.getValue(tours));
/* 206 */     this.current = my;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTrialSolution(int[] tours, int[][][] tabu)
/*     */   {
/* 217 */     if (this.current == null) return;
/* 218 */     MySolution my = new MySolution(tours);
/* 219 */     Move[] moves = this.moveManager.getAllMoves(this.current);
/* 220 */     int position = 0;
/* 221 */     for (int i = 0; i < moves.length; i++) {
/* 222 */       int[] temp = operateOn(this.current, (MySwapMove)moves[i]);
/* 223 */       if (isEqualArray(tours, temp)) position = i;
/*     */     }
/* 225 */     this.tabuList.cleanTabuList();
/* 226 */     for (int i = 0; i < tabu.length; i++)
/* 227 */       this.tabuList.setTabuList(this.current, (MySwapMove)moves[position], tabu[i]);
/* 228 */     my.setObjectiveValue(this.objFunc.evaluate(my, moves[position]));
/* 229 */     this.tabuSearch.setCurrentSolution(my);
/* 230 */     this.current = my;
/*     */   }
/*     */   
/*     */   public void clean()
/*     */   {
/* 235 */     this.current = ((MySolution)this.initialSolution.clone());
/* 236 */     this.tabuList.cleanTabuList();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ior\TSPproblem.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */