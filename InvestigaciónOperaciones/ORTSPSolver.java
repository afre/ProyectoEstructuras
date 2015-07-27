/*      */ import ior.MySolution;
/*      */ import ior.TSPproblem;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Vector;
/*      */ import org.coinor.opents.Move;
/*      */ import satsp.TspObjectiveFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ORTSPSolver
/*      */   extends ORLPSolver
/*      */ {
/*   33 */   private ORSolverBase.TSPInputModelType tspInputModel = new ORSolverBase.TSPInputModelType(this);
/*      */   
/*   35 */   private satsp.SimulateAnnealing worker = null;
/*      */   
/*   37 */   protected boolean started = false;
/*   38 */   private IOROperation opera = null;
/*   39 */   private Vector p = null;
/*      */   private double[][] d2;
/*      */   private int[] initSolution;
/*   42 */   private ArrayList ddd = new ArrayList();
/*      */   
/*      */   private String isSAAuto;
/*   45 */   private boolean SAIsError = false;
/*   46 */   private boolean isNew_SA = false;
/*      */   
/*      */ 
/*   49 */   private boolean isFinishGA = false;
/*   50 */   private Vector best_vec = null;
/*   51 */   private Vector pop_vec = null;
/*   52 */   private Vector child_vec = null;
/*   53 */   private double bestSolution = 2.147483647E9D;
/*   54 */   private int jumpTrem = 6;
/*   55 */   private int totalTimes = 0;
/*   56 */   private boolean isNew_Tabu = true;
/*      */   
/*      */   private TSPproblem tsp;
/*      */   private Vector interSolutionV;
/*      */   private Vector interDistanceV;
/*      */   private String isTBAuto;
/*   62 */   private boolean isTabuInter = false;
/*      */   private Move move;
/*      */   private MySolution mySolution;
/*   65 */   private Vector comBoxMessageV = new Vector();
/*   66 */   private Vector currentSolutionV = new Vector();
/*   67 */   private Vector comBoxDistanceV = new Vector();
/*   68 */   private Vector moveV = new Vector();
/*      */   
/*      */   private int TBTotalLine;
/*      */   
/*      */   private ArrayList perArray;
/*      */   private Vector auto_TotalSolutionV;
/*      */   private Vector auto_DistanceV;
/*      */   private Vector auto_TabuV;
/*   76 */   private ORSolverBase.PolynomialType[] TheDerivative = new ORSolverBase.PolynomialType[4];
/*      */   
/*      */ 
/*      */   public ORTSPSolver.AnnealingType currentAType;
/*      */   
/*      */ 
/*      */   public SimulateAnnealing anneal;
/*      */   
/*      */   public ORTSPSolver.GeneType getype;
/*      */   public Population pop;
/*      */   
/*      */   private class AnnealingType
/*      */     implements Serializable
/*      */   {
/*      */     private AnnealingType() {}
/*      */     
/*   92 */     AnnealingType(ORTSPSolver..1 x$1) { this(); }
/*      */     
/*   94 */     int currentindex = 0;
/*      */     double PolyResult;
/*      */     ArrayList[] An_list;
/*   97 */     int A_size = 20;
/*   98 */     boolean isPrintReady = false;
/*      */     String bestSolution;
/*      */     String[][] SAprintData;
/*      */   }
/*      */   
/*      */   private class GeneType implements Serializable
/*      */   {
/*      */     ArrayList[] ge_list;
/*      */     
/*      */     GeneType(ORTSPSolver..1 x$1) {
/*  108 */       this();
/*      */     }
/*      */     
/*  111 */     int ge_size = 0;
/*  112 */     int currentindex = 0;
/*  113 */     Vector best = new Vector();
/*  114 */     Vector storePopulation = new Vector();
/*  115 */     Vector generation = new Vector();
/*      */     
/*      */     public void setCurrentIndex(int n) {
/*  118 */       this.currentindex = n;
/*      */     }
/*      */     
/*      */ 
/*      */     private GeneType() {}
/*      */   }
/*      */   
/*      */ 
/*      */   public ORTSPSolver()
/*      */   {
/*  128 */     this.isNew_SA = false;
/*  129 */     this.currentAType = new ORTSPSolver.AnnealingType(null);
/*      */   }
/*      */   
/*      */   public void getData(IOROperation op, IORTSPProcessController.NLData data) {
/*      */     int i;
/*      */     int tempitertag;
/*  135 */     switch (op.operation_code)
/*      */     {
/*      */     case 50601: 
/*  138 */       data.is_max = (this.SimplexModel.Objective == 0);
/*  139 */       data.num_term = this.ThePolynomial.NumTerms;
/*  140 */       data.num_variable = this.SimplexModel.NumVariables;
/*  141 */       data.num_constraint = this.SimplexModel.NumConstraints;
/*      */       
/*  143 */       if (data.fw_oldx == null) data.fw_oldx = new double[20 + 1][3];
/*  144 */       if (data.fw_newx == null) data.fw_newx = new double[20 + 1][3];
/*  145 */       if (data.fw_a == null) data.fw_a = new double[20 + 1][3];
/*  146 */       if (data.fw_b == null) data.fw_b = new double[20 + 1][3];
/*  147 */       if (data.fw_xlp == null) data.fw_xlp = new double[20 + 1][3];
/*  148 */       if (data.fw_constraint_coef == null) data.fw_constraint_coef = new double[51][51];
/*  149 */       if (data.fw_constraint_operator == null) data.fw_constraint_operator = new int[51];
/*  150 */       if (data.fw_flp_coef == null) data.fw_flp_coef = new double[20 + 1][3];
/*  151 */       if (data.fw_gradx == null) data.fw_gradx = new double[20 + 1][3];
/*  152 */       if (data.fw_tt == null) data.fw_tt = new double[20 + 1];
/*  153 */       if (data.fw_ht_coef == null) data.fw_ht_coef = new double[20 + 1][11];
/*  154 */       if (data.fw_ht_texp == null) data.fw_ht_texp = new int[20 + 1][11];
/*  155 */       if (data.fw_ht_num_term == null) { data.fw_ht_num_term = new int[20 + 1];
/*      */       }
/*      */       
/*  158 */       for (i = 1; i <= this.SimplexModel.NumConstraints; i++) {
/*  159 */         data.fw_constraint_coef[(i - 1)][0] = this.SimplexModel.RightHandSide[i];
/*  160 */         data.fw_constraint_operator[(i - 1)] = this.SimplexModel.Constraint[i];
/*  161 */         for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/*  162 */           data.fw_constraint_coef[(i - 1)][j] = this.SimplexModel.A[i][j];
/*      */         }
/*      */       }
/*      */       
/*  166 */       data.fw_objective.num_term = data.num_term;
/*  167 */       data.fw_objective.num_var = data.num_variable;
/*  168 */       for (i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/*  169 */         data.fw_objective.fxcoef[(i - 1)] = this.ThePolynomial.Coefficient[i];
/*  170 */         for (int j = 1; j <= this.ThePolynomial.NumVariables; j++) {
/*  171 */           data.fw_objective.xexp[(i - 1)][(j - 1)] = this.ThePolynomial.Exponent[i][j];
/*      */         }
/*      */       }
/*  174 */       data.Temp = new double[this.currentAType.A_size];
/*  175 */       data.variable = new double[this.currentAType.A_size][2];
/*  176 */       data.function = new double[this.currentAType.A_size];
/*  177 */       data.probability = new double[this.currentAType.A_size];
/*  178 */       data.accept = new boolean[this.currentAType.A_size];
/*  179 */       data.itertag = new int[this.currentAType.A_size];
/*  180 */       data.bestvarible = new double[2];
/*      */       
/*      */ 
/*  183 */       data.pos = this.anneal.currentPos;
/*  184 */       data.bestresult = this.anneal.bestObj;
/*  185 */       data.bestvarible = this.anneal.bestSolution;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  195 */       data.totalresult = this.currentAType.A_size;
/*  196 */       tempitertag = 0;
/*      */       
/*      */ 
/*  199 */       for (i = 0; i < this.currentAType.An_list.length;) {
/*  200 */         data.itertag[i] = tempitertag;
/*  201 */         Double tt = (Double)this.currentAType.An_list[i].get(0);
/*  202 */         data.Temp[i] = tt.doubleValue();
/*      */         
/*  204 */         double[] ss = (double[])this.currentAType.An_list[i].get(1);
/*  205 */         for (int n = 0; n < data.num_variable; n++) {
/*      */           try
/*      */           {
/*  208 */             data.variable[i][n] = ss[n];
/*      */           } catch (Exception e) {
/*  210 */             System.out.println("error");
/*      */           }
/*      */         }
/*      */         
/*  214 */         tt = (Double)this.currentAType.An_list[i].get(2);
/*  215 */         data.function[i] = tt.doubleValue();
/*      */         
/*  217 */         tt = (Double)this.currentAType.An_list[i].get(3);
/*  218 */         data.probability[i] = tt.doubleValue();
/*      */         
/*  220 */         Boolean bb = (Boolean)this.currentAType.An_list[i].get(4);
/*  221 */         data.accept[i] = bb.booleanValue();
/*      */         
/*  223 */         if (data.accept[i] != 0) tempitertag++;
/*  199 */         i++; continue;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  246 */         data.is_max = (this.SimplexModel.Objective == 0);
/*  247 */         data.num_term = this.ThePolynomial.NumTerms;
/*  248 */         data.num_variable = this.SimplexModel.NumVariables;
/*  249 */         data.num_constraint = this.SimplexModel.NumConstraints;
/*      */         
/*  251 */         if (data.fw_oldx == null) data.fw_oldx = new double[20 + 1][3];
/*  252 */         if (data.fw_newx == null) data.fw_newx = new double[20 + 1][3];
/*  253 */         if (data.fw_a == null) data.fw_a = new double[20 + 1][3];
/*  254 */         if (data.fw_b == null) data.fw_b = new double[20 + 1][3];
/*  255 */         if (data.fw_xlp == null) data.fw_xlp = new double[20 + 1][3];
/*  256 */         if (data.fw_constraint_coef == null) data.fw_constraint_coef = new double[51][51];
/*  257 */         if (data.fw_constraint_operator == null) data.fw_constraint_operator = new int[51];
/*  258 */         if (data.fw_flp_coef == null) data.fw_flp_coef = new double[20 + 1][3];
/*  259 */         if (data.fw_gradx == null) data.fw_gradx = new double[20 + 1][3];
/*  260 */         if (data.fw_tt == null) data.fw_tt = new double[20 + 1];
/*  261 */         if (data.fw_ht_coef == null) data.fw_ht_coef = new double[20 + 1][11];
/*  262 */         if (data.fw_ht_texp == null) data.fw_ht_texp = new int[20 + 1][11];
/*  263 */         if (data.fw_ht_num_term == null) { data.fw_ht_num_term = new int[20 + 1];
/*      */         }
/*      */         
/*  266 */         for (i = 1; i <= this.SimplexModel.NumConstraints; i++) {
/*  267 */           data.fw_constraint_coef[(i - 1)][0] = this.SimplexModel.RightHandSide[i];
/*  268 */           data.fw_constraint_operator[(i - 1)] = this.SimplexModel.Constraint[i];
/*  269 */           for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/*  270 */             data.fw_constraint_coef[(i - 1)][j] = this.SimplexModel.A[i][j];
/*      */           }
/*      */         }
/*      */         
/*  274 */         data.fw_objective.num_term = data.num_term;
/*  275 */         data.fw_objective.num_var = data.num_variable;
/*  276 */         for (i = 1; i <= this.ThePolynomial.NumTerms;) {
/*  277 */           data.fw_objective.fxcoef[(i - 1)] = this.ThePolynomial.Coefficient[i];
/*  278 */           for (int j = 1; j <= this.ThePolynomial.NumVariables; j++) {
/*  279 */             data.fw_objective.xexp[(i - 1)][(j - 1)] = this.ThePolynomial.Exponent[i][j];
/*      */           }
/*  276 */           i++; continue;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  284 */           data.totalrecord = this.getype.ge_size;
/*  285 */           data.best = new Chromosome[data.totalrecord];
/*  286 */           data.store = new Chromosome[10];
/*  287 */           data.children = new Chromosome[6];
/*  288 */           data.parentindex = new int[10];
/*  289 */           data.bytag = new byte[10][10][10];
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  295 */           for (int i = 0; i < this.getype.currentindex; i++)
/*      */           {
/*  297 */             data.best[i] = ((Chromosome)this.getype.best.get(i));
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  302 */           data.store = ((Chromosome[])this.getype.storePopulation.get(this.getype.currentindex - 1));
/*      */           
/*      */ 
/*  305 */           ArrayList temp = (ArrayList)this.getype.generation.get(this.getype.currentindex - 1);
/*      */           
/*  307 */           Chromosome[][] aaa = new Chromosome[3][2];
/*  308 */           int[][] bbb = new int[3][2];
/*  309 */           ArrayList ccc = (ArrayList)temp.get(3);
/*      */           
/*  311 */           ArrayList ccc1 = (ArrayList)ccc.get(0);
/*  312 */           ArrayList ccc2 = (ArrayList)ccc.get(1);
/*  313 */           ArrayList ccc3 = (ArrayList)ccc.get(2);
/*      */           
/*  315 */           data.bytag[0] = ((byte[][])ccc1.get(0));
/*  316 */           data.bytag[1] = ((byte[][])ccc1.get(1));
/*  317 */           data.bytag[2] = ((byte[][])ccc2.get(0));
/*  318 */           data.bytag[3] = ((byte[][])ccc2.get(1));
/*  319 */           data.bytag[4] = ((byte[][])ccc3.get(0));
/*  320 */           data.bytag[5] = ((byte[][])ccc3.get(1));
/*      */           
/*  322 */           bbb = (int[][])temp.get(1);
/*  323 */           aaa = (Chromosome[][])temp.get(2);
/*      */           
/*  325 */           for (i = 0; i < 3; i++)
/*  326 */             for (int j = 0; j < 2; j++) {
/*  327 */               data.parentindex[(i * 2 + j)] = bbb[i][j];
/*  328 */               data.children[(i * 2 + j)] = aaa[i][j];
/*      */             }
/*      */         }
/*      */       }
/*      */     }
/*      */     int i;
/*      */   }
/*      */   
/*      */   public void getData(IOROperation op, IORTSPProcessController.TSPData d) {
/*      */     ArrayList temArr;
/*      */     int i;
/*  339 */     if ((this.ddd.size() != 0) || (this.isTabuInter))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  348 */       switch (op.operation_code) {
/*      */       case 60010: 
/*  350 */         if (this.isSAAuto.equalsIgnoreCase("true")) {
/*  351 */           if (d.auto_Accept_Vec != null) d.auto_Accept_Vec = null;
/*  352 */           d.auto_Accept_Vec = new Vector();
/*  353 */           if (d.auto_Distance_Vec != null) d.auto_Distance_Vec = null;
/*  354 */           d.auto_Distance_Vec = new Vector();
/*  355 */           if (d.auto_Probability_Vec != null) d.auto_Probability_Vec = null;
/*  356 */           d.auto_Probability_Vec = new Vector();
/*  357 */           if (d.auto_Solution_Vec != null) d.auto_Solution_Vec = null;
/*  358 */           d.auto_Solution_Vec = new Vector();
/*  359 */           if (d.auto_T_Vec != null) d.auto_T_Vec = null;
/*  360 */           d.auto_T_Vec = new Vector();
/*  361 */           d.totalLine = this.ddd.size();
/*  362 */           for (int i = 0; i < this.ddd.size(); i++) {
/*  363 */             ArrayList tempList = (ArrayList)this.ddd.get(i);
/*  364 */             for (int j = 0; j < tempList.size(); j++) {
/*  365 */               if (j == 0) {
/*  366 */                 Double type1 = (Double)tempList.get(j);
/*  367 */                 String tableStr = type1.toString();
/*  368 */                 d.auto_T_Vec.addElement(tableStr);
/*      */ 
/*      */               }
/*  371 */               else if (j == 1) {
/*  372 */                 int[] type2 = (int[])tempList.get(j);
/*  373 */                 d.auto_Solution_Vec.addElement(type2);
/*      */ 
/*      */               }
/*  376 */               else if (j == 2) {
/*  377 */                 Double type3 = (Double)tempList.get(j);
/*  378 */                 String tableStr = type3.toString();
/*  379 */                 d.auto_Distance_Vec.addElement(tableStr);
/*      */ 
/*      */               }
/*  382 */               else if (j == 3) {
/*  383 */                 Double type4 = (Double)tempList.get(j);
/*  384 */                 String tableStr = type4.toString();
/*  385 */                 d.auto_Probability_Vec.addElement(tableStr);
/*      */ 
/*      */               }
/*  388 */               else if (j == 4) {
/*  389 */                 Boolean type5 = (Boolean)tempList.get(j);
/*  390 */                 String tableStr = type5.toString();
/*  391 */                 d.auto_Accept_Vec.addElement(tableStr);
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*  397 */           d.saveAuto_T_Vec.addElement(d.auto_T_Vec);
/*  398 */           d.saveAuto_Solution_Vec.addElement(d.auto_Solution_Vec);
/*  399 */           d.saveAuto_Distance_Vec.addElement(d.auto_Distance_Vec);
/*  400 */           d.saveAuto_Probability_Vec.addElement(d.auto_Probability_Vec);
/*  401 */           d.saveAuto_Accept_Vec.addElement(d.auto_Accept_Vec);
/*  402 */           d.saveCurrentTotalLine.addElement("".concat(String.valueOf(String.valueOf(d.totalLine))));
/*      */         } else {
/*  404 */           Double type1 = (Double)this.ddd.get(0);
/*  405 */           String tableStr = type1.toString();
/*  406 */           d.step_T_Vec.addElement(tableStr);
/*      */           
/*  408 */           int[] type2 = (int[])this.ddd.get(1);
/*  409 */           d.step_Solution_Vec.addElement(type2);
/*      */           
/*      */ 
/*  412 */           Double type3 = (Double)this.ddd.get(2);
/*  413 */           tableStr = type3.toString();
/*  414 */           d.step_Distance_Vec.addElement(tableStr);
/*      */           
/*  416 */           Double type4 = (Double)this.ddd.get(3);
/*  417 */           tableStr = type4.toString();
/*  418 */           d.step_Probability_Vec.addElement(tableStr);
/*      */           
/*  420 */           Boolean type5 = (Boolean)this.ddd.get(4);
/*  421 */           tableStr = type5.toString();
/*  422 */           d.step_Accept_Vec.addElement(tableStr);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  557 */         break;
/*      */       case 60014: 
/*  428 */         d.GAtotalTimes = this.best_vec.size();
/*  429 */         d.GA_Fitness_Vec.clear();
/*  430 */         d.GA_Solution_Vec.clear();
/*      */         
/*      */ 
/*  433 */         for (int i = 0; i < this.best_vec.size(); i++) {
/*  434 */           gatsp.Chromosome temp = (gatsp.Chromosome)this.best_vec.elementAt(i);
/*  435 */           d.GA_Fitness_Vec.addElement(new Double(temp.getFitness()));
/*  436 */           d.GA_Solution_Vec.addElement(temp.getTours());
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  441 */         for (int i = 0; i < this.pop_vec.size(); i++) {
/*  442 */           gatsp.Chromosome[] popCh = (gatsp.Chromosome[])this.pop_vec.elementAt(i);
/*  443 */           d.GA_Pop_And_Child_TotalLine = popCh.length;
/*  444 */           d.GA_Pop_Vec = null;d.GA_Pop_Vec = new Vector();
/*  445 */           d.GA_PopFit_Vec = null;d.GA_PopFit_Vec = new Vector();
/*      */           
/*  447 */           for (int j = 0; j < popCh.length; j++) {
/*  448 */             d.GA_Pop_Vec.addElement(popCh[j].getTours());
/*      */             
/*  450 */             d.GA_PopFit_Vec.addElement(new Double(popCh[j].getFitness()));
/*      */           }
/*  452 */           d.GA_Total_Pop_Vec.addElement(d.GA_Pop_Vec);
/*  453 */           d.GA_Total_PopFit_Vec.addElement(d.GA_PopFit_Vec);
/*      */         }
/*      */         
/*      */ 
/*  457 */         for (int i = 0; i < this.pop_vec.size(); i++) {
/*  458 */           gatsp.Chromosome[] popCh = (gatsp.Chromosome[])this.pop_vec.elementAt(i);
/*  459 */           Vector tempp = (Vector)d.GA_Total_PopFit_Vec.elementAt(i);
/*  460 */           for (int j = 0; j < tempp.size(); j++) {}
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  467 */         temArr = new ArrayList();
/*      */         
/*      */ 
/*      */ 
/*  471 */         for (i = 0; i < this.child_vec.size();) {
/*  472 */           temArr = (ArrayList)this.child_vec.elementAt(i);
/*  473 */           int[][] selectParent = (int[][])temArr.get(1);
/*  474 */           gatsp.Chromosome[][] childrens = (gatsp.Chromosome[][])temArr.get(2);
/*      */           
/*  476 */           d.GA_Child_Vec = null;d.GA_Child_Vec = new Vector();
/*  477 */           d.GA_ChildFitn_Vec = null;d.GA_ChildFitn_Vec = new Vector();
/*  478 */           d.GA_SelectParent = null;d.GA_SelectParent = new Vector();
/*  479 */           d.GA_ChildBreak_Vec = null;d.GA_ChildBreak_Vec = new Vector();
/*      */           
/*  481 */           ArrayList breakArr = (ArrayList)temArr.get(3);
/*      */           
/*  483 */           for (int j = 0; j < breakArr.size(); j++) {
/*  484 */             int[][] breakChild = (int[][])breakArr.get(j);
/*  485 */             for (int z = 0; z < breakChild.length; z++) {
/*  486 */               int[] temCh = breakChild[z];
/*  487 */               d.GA_ChildBreak_Vec.addElement(temCh);
/*      */             }
/*      */           }
/*      */           
/*  491 */           for (int j = 0; j < childrens.length; j++) {
/*  492 */             d.GA_Child_Vec.addElement(childrens[j][0].getTours());
/*  493 */             d.GA_Child_Vec.addElement(childrens[j][1].getTours());
/*  494 */             d.GA_ChildFitn_Vec.addElement(new Double(childrens[j][0].getFitness()));
/*  495 */             d.GA_ChildFitn_Vec.addElement(new Double(childrens[j][1].getFitness()));
/*      */           }
/*  497 */           for (int j = 0; j < selectParent.length; j++) {
/*  498 */             d.GA_SelectParent.addElement(new Integer(selectParent[j][0]));
/*  499 */             d.GA_SelectParent.addElement(new Integer(selectParent[j][1]));
/*      */           }
/*      */           
/*  502 */           d.GA_Total_Child_Vec.addElement(d.GA_Child_Vec);
/*  503 */           d.GA_Total_ChildFitn_Vec.addElement(d.GA_ChildFitn_Vec);
/*  504 */           d.GA_Total_SelectParent.addElement(d.GA_SelectParent);
/*  505 */           d.GA_Total_ChildBreak_Vec.addElement(d.GA_ChildBreak_Vec);i++; continue;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  510 */           if (this.isTBAuto.equalsIgnoreCase("isInteractive")) {
/*  511 */             for (int i = 0; i < this.interSolutionV.size(); i++) {
/*  512 */               d.TB_InterSolution_Vec.addElement(this.interSolutionV.elementAt(i));
/*      */             }
/*  514 */             for (int i = 0; i < this.interDistanceV.size(); i++) {
/*  515 */               d.TB_InterDistance_Vec.addElement(this.interDistanceV.elementAt(i));
/*      */             }
/*  517 */             if (d.TB_comBoxMessageV != null) d.TB_comBoxMessageV = null;
/*  518 */             d.TB_comBoxMessageV = new Vector();
/*  519 */             if (d.TB_comBoxDistanceV != null) d.TB_comBoxDistanceV = null;
/*  520 */             d.TB_comBoxDistanceV = new Vector();
/*  521 */             if (d.TB_currentSolutionV != null) d.TB_currentSolutionV = null;
/*  522 */             d.TB_currentSolutionV = new Vector();
/*  523 */             if (d.TB_moveV != null) d.TB_moveV = null;
/*  524 */             d.TB_moveV = new Vector();
/*      */             
/*  526 */             d.TB_comBoxMessageV = this.comBoxMessageV;
/*  527 */             d.TB_comBoxDistanceV = this.comBoxDistanceV;
/*  528 */             d.TB_currentSolutionV = this.currentSolutionV;
/*  529 */             d.TB_moveV = this.moveV;
/*      */ 
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*  536 */             if (d.TB_Auto_DistanceV != null) d.TB_Auto_DistanceV = null;
/*  537 */             d.TB_Auto_DistanceV = new Vector();
/*  538 */             if (d.TB_Auto_TabuV != null) d.TB_Auto_TabuV = null;
/*  539 */             d.TB_Auto_TabuV = new Vector();
/*  540 */             if (d.TB_Auto_SolutionV != null) d.TB_Auto_SolutionV = null;
/*  541 */             d.TB_Auto_SolutionV = new Vector();
/*      */             
/*  543 */             d.TB_Auto_DistanceV = this.auto_DistanceV;
/*  544 */             d.TB_Auto_TabuV = this.auto_TabuV;
/*  545 */             d.TB_Auto_TotalLine = this.TBTotalLine;
/*  546 */             d.TB_Auto_SolutionV = this.auto_TotalSolutionV;
/*      */             
/*  548 */             d.TB_Auto_Total_DistanceV.addElement(d.TB_Auto_DistanceV);
/*  549 */             d.TB_Auto_Total_SolutionV.addElement(d.TB_Auto_SolutionV);
/*  550 */             d.TB_Auto_Total_TabuV.addElement(d.TB_Auto_TabuV);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  557 */             break;
/*      */           } } } } }
/*      */   
/*  560 */   public boolean setOperation(IOROperation op, IORTSPProcessController.TSPData d) { this.opera = op;
/*  561 */     this.p = op.parameters;
/*      */     
/*  563 */     this.d2 = ((double[][])this.p.elementAt(1));
/*  564 */     this.initSolution = ((int[])this.p.elementAt(2));
/*      */     
/*  566 */     if (this.worker != null)
/*  567 */       this.worker = null;
/*  568 */     TspObjectiveFunction objFuc = new TspObjectiveFunction(this.d2);
/*  569 */     this.worker = new satsp.SimulateAnnealing(this.d2);
/*  570 */     if (this.worker.setInitSolution(this.initSolution)) {
/*  571 */       d.SAisError = false;
/*      */     } else {
/*  573 */       d.SAisError = true;
/*      */     }
/*  575 */     this.worker = null;
/*  576 */     return d.SAisError;
/*      */   }
/*      */   
/*      */   public boolean doWork(IOROperation op, IORTSPProcessController.TSPData data) {
/*  580 */     boolean success = doWork(op);
/*  581 */     if (success) {
/*  582 */       getData(op, data);
/*      */     }
/*  584 */     return success;
/*      */   }
/*      */   
/*      */   public boolean doWork(IOROperation op, IORTSPProcessController.NLData data)
/*      */   {
/*  589 */     boolean success = doWork(op);
/*  590 */     if (success) {
/*  591 */       getData(op, data);
/*      */     }
/*  593 */     return success;
/*      */   }
/*      */   
/*      */   public void initPrintData(IOROperation op, IORTSPProcessController.NLData data) {
/*  597 */     switch (op.operation_code) {
/*      */     case 50601: 
/*  599 */       this.currentAType.bestSolution = data.bestSolution;
/*  600 */       this.currentAType.SAprintData = data.printdata;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  606 */       break;
/*      */     case 50701: 
/*  603 */       this.currentAType.bestSolution = data.bestSolution;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */   public void initPrintData(IOROperation op, IORTSPProcessController.TSPData data)
/*      */   {
/*  611 */     switch (op.operation_code) {
/*      */     case 60008: 
/*  613 */       this.procedure = 1;
/*  614 */       this.tspInputModel.num_city = data.num_city;
/*  615 */       this.tspInputModel.matrix = data.matrix;
/*  616 */       this.tspInputModel.initSolution = data.initSolution;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  653 */       break;
/*      */     case 60010: 
/*  620 */       this.procedure = 2;
/*  621 */       this.tspInputModel.MinDistance = data.MinDistance;
/*  622 */       this.tspInputModel.bestSolution = data.bestSolution;
/*  623 */       this.tspInputModel.isAuto = data.isAuto;
/*  624 */       this.tspInputModel.SA_Auto_PrintData = data.SA_Auto_PrintData;
/*  625 */       this.tspInputModel.SA_Next_PrintData = data.SA_Next_PrintData;
/*  626 */       this.tspInputModel.SA_BestSolutionInfoLab = data.SA_BestSolutionInfoLab;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  653 */       break;
/*      */     case 60014: 
/*  630 */       this.procedure = 3;
/*  631 */       this.tspInputModel.MinDistance = data.MinDistance;
/*  632 */       this.tspInputModel.bestSolution = data.bestSolution;
/*  633 */       this.tspInputModel.isAuto = data.isAuto;
/*  634 */       this.tspInputModel.GA_Print_Pop_Data = data.GA_Print_Pop_Data;
/*  635 */       this.tspInputModel.GA_Print_Child_Data = data.GA_Print_Child_Data;
/*  636 */       this.tspInputModel.GA_BestSolution_Data = data.GA_Print_BestSolution_Data;
/*  637 */       this.tspInputModel.GA_Print_P_Connection_C_Data = data.GA_Print_P_Connection_C_Data;
/*  638 */       this.tspInputModel.GA_Print_ChildNum = data.GA_Print_ChildNum;
/*  639 */       this.tspInputModel.SA_BestSolutionInfoLab = data.SA_BestSolutionInfoLab;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  653 */       break;
/*      */     case 60009: 
/*  643 */       this.procedure = 4;
/*  644 */       this.tspInputModel.MinDistance = data.MinDistance;
/*  645 */       this.tspInputModel.bestSolution = data.bestSolution;
/*  646 */       this.tspInputModel.TB_Print_TableV = data.TB_Print_TableV;
/*  647 */       this.tspInputModel.TB_Auto_Print_Table = data.TB_Auto_Print_Table;
/*  648 */       this.tspInputModel.isAuto = data.isAuto;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean doWork(IOROperation op)
/*      */   {
/*  657 */     Vector p = op.parameters;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  662 */     double[] Vector = new double[11];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  669 */     if (this.initSolution == null)
/*  670 */       System.out.println("temp is null");
/*  671 */     gatsp.Population po; int finishTrem; switch (op.operation_code)
/*      */     {
/*      */ 
/*      */     case 50601: 
/*  675 */       int[] ia = (int[])p.elementAt(0);
/*      */       
/*  677 */       this.SimplexModel.NumVariables = ia[0];
/*  678 */       this.SimplexModel.NumConstraints = ia[1];
/*  679 */       this.ThePolynomial.NumTerms = ia[2];
/*  680 */       float[] initSolu = (float[])p.elementAt(6);
/*  681 */       this.SimplexModel.initSolution = new float[this.SimplexModel.NumVariables];
/*  682 */       this.SimplexModel.Annealling = new int[this.SimplexModel.NumVariables];
/*  683 */       if (ia[0] == 1) {
/*  684 */         this.SimplexModel.Annealling[0] = ia[3];
/*  685 */         this.SimplexModel.initSolution[0] = initSolu[0];
/*      */       }
/*      */       else {
/*  688 */         this.SimplexModel.Annealling[0] = ia[3];
/*  689 */         this.SimplexModel.Annealling[1] = ia[4];
/*  690 */         this.SimplexModel.initSolution[0] = initSolu[0];
/*  691 */         this.SimplexModel.initSolution[1] = initSolu[1];
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  699 */       this.ThePolynomial.NumVariables = this.SimplexModel.NumVariables;
/*      */       
/*  701 */       for (int i = 1; i <= this.SimplexModel.NumVariables; i++) {
/*  702 */         this.TheDerivative[i] = new ORSolverBase.PolynomialType(this);
/*  703 */         this.TheDerivative[i].NumVariables = this.SimplexModel.NumVariables;
/*  704 */         this.TheDerivative[i].NumTerms = this.ThePolynomial.NumTerms;
/*      */       }
/*      */       
/*      */ 
/*  708 */       boolean Flag = ((Boolean)p.elementAt(1)).booleanValue();
/*  709 */       this.SimplexModel.Objective = (Flag ? 0 : 1);
/*      */       
/*  711 */       double[] fx_coefficient = (double[])p.elementAt(2);
/*  712 */       int[][] exponents = (int[][])p.elementAt(3);
/*      */       
/*      */ 
/*  715 */       for (i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/*  716 */         this.ThePolynomial.Coefficient[i] = fx_coefficient[(i - 1)];
/*  717 */         for (int j = 1; j <= this.ThePolynomial.NumVariables; j++) {
/*  718 */           this.ThePolynomial.Exponent[i][j] = exponents[(i - 1)][(j - 1)];
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  723 */       double[][] ConstraintCoef = (double[][])p.elementAt(4);
/*  724 */       ia = (int[])p.elementAt(5);
/*      */       
/*  726 */       for (i = 0; i < this.SimplexModel.NumConstraints; i++) {
/*  727 */         this.SimplexModel.Constraint[(i + 1)] = ia[i];
/*  728 */         this.SimplexModel.RightHandSide[(i + 1)] = ConstraintCoef[i][0];
/*  729 */         for (int j = 0; j < this.SimplexModel.NumVariables; j++) {
/*  730 */           this.SimplexModel.A[(i + 1)][(j + 1)] = ConstraintCoef[i][(j + 1)];
/*      */         }
/*      */       }
/*      */       
/*  734 */       if (this.anneal == null)
/*      */       {
/*  736 */         this.anneal = new SimulateAnnealing(this.ThePolynomial, this.SimplexModel);
/*      */         
/*      */ 
/*      */ 
/*  740 */         double[] checkSolu = new double[initSolu.length];
/*  741 */         for (i = 0; i < initSolu.length; i++) {
/*  742 */           checkSolu[i] = initSolu[i];
/*      */         }
/*      */         
/*  745 */         if (!this.anneal.objectFunction.feasible(checkSolu)) {
/*  746 */           System.out.println("=========Error========");
/*  747 */           return false;
/*      */         }
/*  749 */         ArrayList list = this.anneal.startIteration();
/*  750 */         int listlength = list.size();
/*  751 */         System.out.println("listLength = ".concat(String.valueOf(String.valueOf(listlength))));
/*  752 */         this.currentAType.A_size = listlength;
/*  753 */         this.currentAType.An_list = new ArrayList[listlength];
/*  754 */         for (i = 0; i < list.size(); i++) {
/*  755 */           this.currentAType.An_list[i] = ((ArrayList)list.get(i));
/*      */         }
/*      */       }
/*  758 */       this.currentAType.isPrintReady = true;
/*      */       
/*      */ 
/*      */ 
/*  762 */       break;
/*      */     
/*      */ 
/*      */     case 50701: 
/*  766 */       this.getype = new ORTSPSolver.GeneType(null);
/*      */       
/*  768 */       int[] ia = (int[])p.elementAt(0);
/*      */       
/*  770 */       this.SimplexModel.NumVariables = ia[0];
/*  771 */       this.SimplexModel.NumConstraints = ia[1];
/*  772 */       this.ThePolynomial.NumTerms = ia[2];
/*  773 */       this.SimplexModel.Annealling = new int[this.SimplexModel.NumVariables];
/*  774 */       if (ia[0] == 1) {
/*  775 */         this.SimplexModel.Annealling[0] = ia[3];
/*      */       }
/*      */       else {
/*  778 */         this.SimplexModel.Annealling[0] = ia[3];
/*  779 */         this.SimplexModel.Annealling[1] = ia[4];
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  789 */       this.ThePolynomial.NumVariables = this.SimplexModel.NumVariables;
/*  790 */       for (int i = 1; i <= this.SimplexModel.NumVariables; i++) {
/*  791 */         this.TheDerivative[i] = new ORSolverBase.PolynomialType(this);
/*  792 */         this.TheDerivative[i].NumVariables = this.SimplexModel.NumVariables;
/*  793 */         this.TheDerivative[i].NumTerms = this.ThePolynomial.NumTerms;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  798 */       boolean Flag = ((Boolean)p.elementAt(1)).booleanValue();
/*  799 */       this.SimplexModel.Objective = (Flag ? 0 : 1);
/*      */       
/*      */ 
/*      */ 
/*  803 */       double[] fx_coefficient = (double[])p.elementAt(2);
/*  804 */       int[][] exponents = (int[][])p.elementAt(3);
/*      */       
/*      */ 
/*  807 */       for (i = 1; i <= this.ThePolynomial.NumTerms; i++) {
/*  808 */         this.ThePolynomial.Coefficient[i] = fx_coefficient[(i - 1)];
/*  809 */         for (int j = 1; j <= this.ThePolynomial.NumVariables; j++) {
/*  810 */           this.ThePolynomial.Exponent[i][j] = exponents[(i - 1)][(j - 1)];
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  815 */       double[][] ConstraintCoef = (double[][])p.elementAt(4);
/*  816 */       ia = (int[])p.elementAt(5);
/*      */       
/*  818 */       for (i = 0; i < this.SimplexModel.NumConstraints; i++) {
/*  819 */         this.SimplexModel.Constraint[(i + 1)] = ia[i];
/*  820 */         this.SimplexModel.RightHandSide[(i + 1)] = ConstraintCoef[i][0];
/*  821 */         for (int j = 0; j < this.SimplexModel.NumVariables; j++) {
/*  822 */           this.SimplexModel.A[(i + 1)][(j + 1)] = ConstraintCoef[i][(j + 1)];
/*      */         }
/*      */       }
/*  825 */       if (this.pop == null)
/*      */       {
/*  827 */         this.pop = new Population(this.ThePolynomial, this.SimplexModel);
/*  828 */         return this.pop.isFeasible();
/*      */       }
/*      */       
/*      */ 
/*      */       break;
/*      */     case 50702: 
/*  834 */       if (this.pop == null)
/*      */       {
/*  836 */         this.pop = new Population(this.ThePolynomial, this.SimplexModel);
/*      */       }
/*      */       
/*  839 */       if (this.getype.currentindex == this.getype.ge_size) {
/*  840 */         ArrayList listg = this.pop.nextIteration();
/*  841 */         this.getype.ge_size += 1;
/*  842 */         this.getype.currentindex += 1;
/*  843 */         this.getype.currentindex = this.getype.ge_size;
/*      */         
/*      */ 
/*      */ 
/*  847 */         this.getype.best.addElement(listg.get(0));
/*  848 */         this.getype.storePopulation.addElement(listg.get(1));
/*  849 */         this.getype.generation.addElement(listg.get(2));
/*      */       }
/*      */       else
/*      */       {
/*  853 */         this.getype.currentindex += 1;
/*      */       }
/*      */       
/*  856 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 60010: 
/*  861 */       Vector SAdata = op.parameters;
/*  862 */       this.isSAAuto = ((String)SAdata.elementAt(0));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  872 */       TspObjectiveFunction objFuc = new TspObjectiveFunction(this.d2);
/*      */       
/*  874 */       this.worker = new satsp.SimulateAnnealing(this.d2);
/*  875 */       this.isNew_SA = true;
/*  876 */       if (!this.worker.setInitSolution(this.initSolution)) {
/*  877 */         this.SAIsError = true;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  882 */       if (this.isSAAuto.equalsIgnoreCase("true")) {
/*  883 */         this.ddd.clear();
/*  884 */         while (this.worker.hasNextIteration()) {
/*  885 */           ArrayList tt = this.worker.nextIteration();
/*  886 */           this.ddd.add(tt);
/*      */         }
/*  888 */         this.worker.clean();
/*  889 */         this.ddd = this.worker.startIteration();
/*      */       }
/*  891 */       else if (this.worker.hasNextIteration()) {
/*  892 */         this.ddd = this.worker.nextIteration();
/*      */       }
/*      */       
/*      */       break;
/*      */     case 60014: 
/*  897 */       po = new gatsp.Population(this.d2);
/*  898 */       po.initPopulation();
/*      */       
/*      */ 
/*  901 */       finishTrem = 0;
/*  902 */       resetGA();
/*      */     case 60009: 
/*  904 */       while (!this.isFinishGA) {
/*  905 */         this.ddd = po.nextIteration();
/*      */         
/*  907 */         gatsp.Chromosome best = (gatsp.Chromosome)this.ddd.get(0);
/*  908 */         double GAtemp = best.getFitness();
/*  909 */         if (this.bestSolution <= GAtemp) {
/*  910 */           finishTrem++;
/*      */         } else {
/*  912 */           finishTrem = 0;
/*  913 */           this.bestSolution = GAtemp;
/*      */         }
/*  915 */         if (finishTrem == this.jumpTrem) {
/*  916 */           this.isFinishGA = true;
/*      */         }
/*      */         else
/*      */         {
/*  920 */           this.totalTimes += 1;
/*  921 */           this.best_vec.addElement(this.ddd.get(0));
/*  922 */           this.pop_vec.addElement(this.ddd.get(1));
/*  923 */           this.child_vec.addElement(this.ddd.get(2));continue;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  929 */           ArrayList tempArr = new ArrayList();
/*  930 */           Vector solution = op.parameters;
/*  931 */           this.isTBAuto = ((String)solution.elementAt(0));
/*      */           
/*  933 */           this.interSolutionV = new Vector();
/*  934 */           this.interDistanceV = new Vector();
/*  935 */           if (this.isTBAuto.equalsIgnoreCase("isInteractive")) {
/*  936 */             this.isTabuInter = true;
/*  937 */             if (this.isNew_Tabu) {
/*  938 */               this.isNew_Tabu = false;
/*  939 */               int[] currentSolution = (int[])solution.elementAt(1);
/*  940 */               for (int i = 0; i < currentSolution.length; i++) {
/*  941 */                 currentSolution[i] -= 1;
/*      */               }
/*  943 */               if (this.tsp != null) this.tsp = null;
/*  944 */               this.tsp = new TSPproblem(this.d2, currentSolution);
/*  945 */               tempArr = this.tsp.getInitIteration();
/*  946 */               this.interSolutionV.addElement(tempArr.get(0));
/*  947 */               this.interDistanceV.addElement(tempArr.get(1));
/*      */               
/*  949 */               if (tempArr != null)
/*  950 */                 tempArr = null;
/*  951 */               if (this.comBoxMessageV != null) this.comBoxMessageV = null;
/*  952 */               this.comBoxMessageV = new Vector();
/*  953 */               if (this.currentSolutionV != null) this.currentSolutionV = null;
/*  954 */               this.currentSolutionV = new Vector();
/*  955 */               if (this.comBoxDistanceV != null) this.comBoxDistanceV = null;
/*  956 */               this.comBoxDistanceV = new Vector();
/*  957 */               if (this.moveV != null) this.moveV = null;
/*  958 */               this.moveV = new Vector();
/*      */               
/*  960 */               tempArr = this.tsp.getImmediateNeighbor();
/*      */               
/*  962 */               if (tempArr.size() == 0) {
/*  963 */                 return false;
/*      */               }
/*  965 */               for (i = 0; i < tempArr.size(); i++) {
/*  966 */                 ArrayList perArr = (ArrayList)tempArr.get(i);
/*  967 */                 int[] solu = (int[])perArr.get(0);
/*  968 */                 String s = "";
/*  969 */                 for (int j = 0; j < solu.length; j++) {
/*  970 */                   if (j != solu.length - 1) {
/*  971 */                     s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solu[j] + 1).append("-"))))));
/*      */                   } else {
/*  973 */                     s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solu[j] + 1))))));
/*      */                   }
/*      */                 }
/*  976 */                 this.currentSolutionV.addElement(s);
/*  977 */                 s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf("  Distance : ".concat(String.valueOf(String.valueOf((Double)perArr.get(1)))))));
/*  978 */                 this.comBoxMessageV.addElement(s);
/*  979 */                 this.comBoxDistanceV.addElement(perArr.get(1));
/*  980 */                 this.moveV.addElement((Move)perArr.get(2));
/*      */               }
/*      */             }
/*      */             
/*      */             ArrayList perArr;
/*  985 */             if (tempArr != null)
/*  986 */               tempArr = null;
/*  987 */             if (this.comBoxMessageV != null) this.comBoxMessageV = null;
/*  988 */             this.comBoxMessageV = new Vector();
/*  989 */             if (this.currentSolutionV != null) this.currentSolutionV = null;
/*  990 */             this.currentSolutionV = new Vector();
/*  991 */             if (this.comBoxDistanceV != null) this.comBoxDistanceV = null;
/*  992 */             this.comBoxDistanceV = new Vector();
/*  993 */             if (this.moveV != null) this.moveV = null;
/*  994 */             this.moveV = new Vector();
/*      */             
/*  996 */             tempArr = this.tsp.getImmediateNeighbor();
/*      */             
/*  998 */             for (int i = 0; i < tempArr.size(); i++) {
/*  999 */               ArrayList perArr = (ArrayList)tempArr.get(i);
/* 1000 */               int[] solu = (int[])perArr.get(0);
/* 1001 */               String s = "";
/* 1002 */               for (int j = 0; j < solu.length; j++) {
/* 1003 */                 if (j != solu.length - 1) {
/* 1004 */                   s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solu[j] + 1).append("-"))))));
/*      */                 } else {
/* 1006 */                   s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solu[j] + 1))))));
/*      */                 }
/*      */               }
/* 1009 */               this.currentSolutionV.addElement(s);
/* 1010 */               s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf("  Distance : ".concat(String.valueOf(String.valueOf((Double)perArr.get(1)))))));
/* 1011 */               this.comBoxMessageV.addElement(s);
/* 1012 */               this.comBoxDistanceV.addElement(perArr.get(1));
/* 1013 */               this.moveV.addElement((Move)perArr.get(2));
/*      */             } }
/*      */           ArrayList perArr;
/*      */           ArrayList perArr;
/* 1017 */           this.isTabuInter = true;
/* 1018 */           if (this.isNew_Tabu) {
/* 1019 */             this.isNew_Tabu = false;
/* 1020 */             int[] currentSolutionV = (int[])solution.elementAt(1);
/* 1021 */             for (int i = 0; i < currentSolutionV.length; i++) {
/* 1022 */               currentSolutionV[i] -= 1;
/*      */             }
/* 1024 */             if (this.tsp != null)
/* 1025 */               this.tsp = null;
/* 1026 */             this.tsp = new TSPproblem(this.d2, currentSolutionV);
/*      */             
/* 1028 */             ArrayList autoArry = this.tsp.startIteration();
/* 1029 */             this.TBTotalLine = autoArry.size();
/* 1030 */             this.auto_TabuV = new Vector();
/* 1031 */             this.auto_TotalSolutionV = new Vector();
/* 1032 */             this.auto_DistanceV = new Vector();
/* 1033 */             for (i = 0; i < this.TBTotalLine; i++) {
/* 1034 */               this.perArray = ((ArrayList)autoArry.get(i));
/* 1035 */               MySolution ms = (MySolution)this.perArray.get(0);
/* 1036 */               String test = (String)this.perArray.get(1);
/*      */               
/* 1038 */               int[] sol = ms.tour;
/*      */               
/* 1040 */               this.auto_TabuV.addElement(test);
/* 1041 */               this.auto_TotalSolutionV.addElement(sol);
/* 1042 */               this.auto_DistanceV.addElement(ms.getObjectiveValue());
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/*      */     
/* 1048 */     System.out.println("return");
/* 1049 */     return true;
/*      */   }
/*      */   
/*      */   public void getInitSolution(IORTSPProcessController.TSPData d) {
/* 1053 */     int[] currentSolution = d.initSolution;
/*      */     
/* 1055 */     if (this.tsp != null) this.tsp = null;
/* 1056 */     this.tsp = new TSPproblem(this.d2, currentSolution);
/*      */     
/* 1058 */     ArrayList initArr = this.tsp.getInitIteration();
/* 1059 */     d.initDistance = ((Double)initArr.get(1)).doubleValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 1064 */   public void tabuAutoclean() { this.tsp.clean(); }
/*      */   
/*      */   public boolean tabuSolutionCheck(int selectDis, IORTSPProcessController.TSPData d) {
/* 1067 */     Vector tempV = d.TB_moveV;
/* 1068 */     return this.tsp.isFeasibleSolution((Move)tempV.elementAt(selectDis));
/*      */   }
/*      */   
/*      */   public boolean tabuRuleCheck(int selectDis, int[][][] data, IORTSPProcessController.TSPData d) {
/* 1072 */     Vector tempV = d.TB_moveV;
/* 1073 */     return this.tsp.isFeasibleTabu((Move)tempV.elementAt(selectDis), data);
/*      */   }
/*      */   
/*      */   public void setNextCurrentSolution(int[] solution, int[][][] data) {
/* 1077 */     this.tsp.setTrialSolution(solution, data);
/*      */   }
/*      */   
/*      */   public void setBackCurrentSolution(int[] solution, int[][][] data, IOROperation op, IORTSPProcessController.TSPData d)
/*      */   {
/* 1082 */     this.tsp.setCurrent(solution, data);
/*      */     
/* 1084 */     ArrayList tempArr = new ArrayList();
/* 1085 */     tempArr = this.tsp.getImmediateNeighbor();
/*      */     
/* 1087 */     if (this.comBoxMessageV != null) this.comBoxMessageV = null;
/* 1088 */     this.comBoxMessageV = new Vector();
/* 1089 */     if (this.currentSolutionV != null) this.currentSolutionV = null;
/* 1090 */     this.currentSolutionV = new Vector();
/* 1091 */     if (this.comBoxDistanceV != null) this.comBoxDistanceV = null;
/* 1092 */     this.comBoxDistanceV = new Vector();
/* 1093 */     if (this.moveV != null) this.moveV = null;
/* 1094 */     this.moveV = new Vector();
/*      */     
/* 1096 */     for (int i = 0; i < tempArr.size(); i++) {
/* 1097 */       ArrayList perArr = (ArrayList)tempArr.get(i);
/* 1098 */       int[] solu = (int[])perArr.get(0);
/* 1099 */       String s = "";
/* 1100 */       for (int j = 0; j < solu.length; j++) {
/* 1101 */         if (j != solu.length - 1) {
/* 1102 */           s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solu[j] + 1).append("-"))))));
/*      */         }
/*      */         else {
/* 1105 */           s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solu[j] + 1))))));
/*      */         }
/*      */       }
/* 1108 */       this.currentSolutionV.addElement(s);
/* 1109 */       s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf("  Distance : ".concat(String.valueOf(String.valueOf((Double)perArr.get(1)))))));
/*      */       
/* 1111 */       this.comBoxMessageV.addElement(s);
/* 1112 */       this.comBoxDistanceV.addElement(perArr.get(1));
/* 1113 */       this.moveV.addElement((Move)perArr.get(2));
/*      */     }
/*      */     
/* 1116 */     getData(op, d);
/*      */   }
/*      */   
/*      */   private void resetGA()
/*      */   {
/* 1121 */     this.isFinishGA = false;
/* 1122 */     this.bestSolution = 2.147483647E9D;
/* 1123 */     this.totalTimes = 0;
/* 1124 */     if (this.best_vec == null)
/* 1125 */       this.best_vec = new Vector();
/* 1126 */     if (this.pop_vec == null)
/* 1127 */       this.pop_vec = new Vector();
/* 1128 */     if (this.child_vec == null)
/* 1129 */       this.child_vec = new Vector();
/* 1130 */     this.best_vec.clear();
/* 1131 */     this.pop_vec.clear();
/* 1132 */     this.child_vec.clear();
/*      */   }
/*      */   
/*      */   public void cleanOutput()
/*      */   {
/* 1137 */     if (this.worker != null) {
/* 1138 */       this.worker.clean();
/*      */     }
/*      */   }
/*      */   
/*      */   protected void ORPrint() {
/* 1143 */     if (this.procedure == 1) {
/* 1144 */       PrintLine("Traveling Salesman Problems:");
/*      */       
/*      */ 
/* 1147 */       PrintTSPModify();
/*      */     }
/* 1149 */     else if (this.procedure == 2) {
/* 1150 */       PrintLine("Traveling Salesman Problems:");
/* 1151 */       PrintTSPModify();
/* 1152 */       PrintAnnealingTSP();
/*      */     }
/* 1154 */     else if (this.procedure == 3) {
/* 1155 */       PrintLine("Traveling Salesman Problems:");
/* 1156 */       PrintTSPModify();
/* 1157 */       PrintGATSP();
/*      */     }
/* 1159 */     else if (this.procedure == 4)
/*      */     {
/* 1161 */       PrintLine("Traveling Salesman Problems:");
/* 1162 */       PrintTSPModify();
/* 1163 */       PrintTabuTSP();
/*      */     }
/* 1165 */     else if (this.procedure == 7) {
/* 1166 */       if (!this.currentAType.isPrintReady) { return;
/*      */       }
/* 1168 */       PrintLine("Simulated Annealing Algorithm and Nonlinear Programming:");
/* 1169 */       SkipLine();
/* 1170 */       PrintLine("Objective Function ");
/* 1171 */       if (this.SimplexModel.Objective == 0)
/* 1172 */         TabPrint(5, "Max f(x) = "); else
/* 1173 */         TabPrint(5, "Min f(x) = ");
/* 1174 */       PrintPolynomial(this.ThePolynomial);
/* 1175 */       printSubject();
/* 1176 */       SkipLine();
/* 1177 */       printNonlinearProgramming();
/*      */ 
/*      */     }
/* 1180 */     else if (this.procedure == 8) {
/* 1181 */       PrintLine("Genetic Algorithm and Integer Nonlinear Programming:");
/* 1182 */       SkipLine();
/* 1183 */       PrintLine("Objective Function ");
/* 1184 */       if (this.SimplexModel.Objective == 0)
/* 1185 */         TabPrint(5, "Max f(x) = "); else
/* 1186 */         TabPrint(5, "Min f(x) = ");
/* 1187 */       PrintPolynomial(this.ThePolynomial);
/* 1188 */       printSubject();
/* 1189 */       SkipLine();
/* 1190 */       printINLP();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void printNonlinearProgramming()
/*      */   {
/* 1198 */     SkipLine();
/* 1199 */     int Width = 6;
/*      */     
/* 1201 */     int pos = 0;
/* 1202 */     DecimalFormat format = new DecimalFormat("#.###");
/*      */     
/* 1204 */     int tempLeng = 0;
/* 1205 */     if (this.currentAType.currentindex <= this.currentAType.An_list.length) {
/* 1206 */       tempLeng = this.currentAType.currentindex;
/*      */     } else {
/* 1208 */       tempLeng = this.currentAType.An_list.length;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1251 */     String[] content = { "", "Iter.", "   T   ", " Trial ", "  f(x)  ", "Prob.", "Accept" };
/*      */     
/*      */ 
/* 1254 */     pos = 0;
/* 1255 */     for (int i = 1; i < content.length; i++) {
/* 1256 */       TabPrint(pos, content[i]);
/* 1257 */       TabPrint(pos + Width + content[i].length(), "|");
/* 1258 */       pos = pos + 2 * Width + content[i].length();
/*      */     }
/* 1260 */     TabPrintLine(0, "");
/*      */     
/* 1262 */     for (int i = 0; i < this.currentAType.SAprintData.length; i++) {
/* 1263 */       pos = 0;
/* 1264 */       for (int j = 0; j < this.currentAType.SAprintData[0].length; j++) {
/* 1265 */         TabPrint(pos, this.currentAType.SAprintData[i][j]);
/* 1266 */         TabPrint(pos + Width + content[(j + 1)].length(), "|");
/* 1267 */         pos = pos + 2 * Width + content[(j + 1)].length();
/*      */       }
/* 1269 */       TabPrintLine(0, "");
/*      */     }
/* 1271 */     SkipLine();
/*      */     
/* 1273 */     SkipLine();
/* 1274 */     TabPrintLine(0, this.currentAType.bestSolution);
/* 1275 */     SkipLine();
/*      */   }
/*      */   
/*      */   private String arrayToString(byte[][] array)
/*      */   {
/* 1280 */     String[] temp = new String[array.length];
/* 1281 */     for (int i = 0; i < array.length; i++)
/* 1282 */       if (array[i] != null) {
/* 1283 */         temp[i] = "";
/* 1284 */         for (int j = 0; j < array[i].length; j++) {
/* 1285 */           int tmp42_41 = i; String[] tmp42_40 = temp;tmp42_40[tmp42_41] = String.valueOf(String.valueOf(tmp42_40[tmp42_41])).concat(String.valueOf(String.valueOf(Byte.toString(array[i][j]))));
/*      */         }
/*      */       }
/* 1288 */     String result = "";
/* 1289 */     if (temp.length == 1) { result = String.valueOf(String.valueOf(new StringBuffer("(").append(temp[0]).append(")")));
/*      */     } else {
/* 1291 */       result = String.valueOf(String.valueOf(result)).concat("(");
/* 1292 */       for (int i = 0; i < temp.length; i++) {
/* 1293 */         result = String.valueOf(String.valueOf(result)).concat(String.valueOf(String.valueOf(temp[i])));
/* 1294 */         if (i != temp.length - 1) result = String.valueOf(String.valueOf(result)).concat(",");
/*      */       }
/* 1296 */       result = String.valueOf(String.valueOf(result)).concat(")");
/*      */     }
/* 1298 */     return result;
/*      */   }
/*      */   
/*      */   private void printINLP() {
/* 1302 */     int Width = 7;
/* 1303 */     if (this.getype.currentindex == 0) { return;
/*      */     }
/* 1305 */     SkipLine();
/* 1306 */     String[] content = { "", "Iter.", "Best Solution", "Fitness", "" };
/*      */     
/* 1308 */     String[][] printData = new String[this.getype.currentindex][3];
/* 1309 */     for (int i = 0; i < this.getype.currentindex; i++) {
/* 1310 */       Chromosome chr = (Chromosome)this.getype.best.elementAt(i);
/* 1311 */       printData[i][0] = Integer.toString(i + 1);
/* 1312 */       printData[i][1] = intArrayToString(chr.getVariables());
/* 1313 */       printData[i][2] = Double.toString(chr.getFitness());
/*      */     }
/* 1315 */     int pos = 0;
/* 1316 */     for (int i = 1; i < content.length - 1; i++) {
/* 1317 */       TabPrint(pos, content[i]);
/* 1318 */       TabPrint(pos + Width + content[i].length(), "|");
/* 1319 */       pos = pos + 2 * Width + content[i].length();
/*      */     }
/* 1321 */     TabPrintLine(0, "");
/*      */     
/* 1323 */     for (int i = 0; i < printData.length; i++) {
/* 1324 */       pos = 0;
/* 1325 */       for (int j = 0; j < printData[0].length; j++) {
/* 1326 */         TabPrint(pos, printData[i][j]);
/* 1327 */         TabPrint(pos + Width + content[(j + 1)].length(), "|");
/* 1328 */         pos = pos + 2 * Width + content[(j + 1)].length();
/*      */       }
/* 1330 */       TabPrintLine(0, "");
/*      */     }
/* 1332 */     SkipLine();
/* 1333 */     TabPrintLine(0, this.currentAType.bestSolution);
/* 1334 */     SkipLine();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1342 */     for (int i = 0; i < this.getype.currentindex; i++) {
/* 1343 */       Chromosome[] pop = (Chromosome[])this.getype.storePopulation.elementAt(i);
/* 1344 */       String[][] generation = new String[pop.length][content.length - 1];
/*      */       
/* 1346 */       content[0] = "";
/* 1347 */       content[1] = "Member";
/* 1348 */       content[2] = " Population";
/* 1349 */       content[3] = "Solution";
/* 1350 */       content[4] = "Fitness";
/*      */       
/* 1352 */       for (int j = 0; j < pop.length; j++) {
/* 1353 */         generation[j][0] = Integer.toString(j + 1);
/* 1354 */         generation[j][1] = arrayToString(pop[j].getGene());
/* 1355 */         generation[j][2] = intArrayToString(pop[j].getVariables());
/* 1356 */         generation[j][3] = Double.toString(pop[j].getFitness());
/*      */       }
/*      */       
/* 1359 */       pos = 0;
/* 1360 */       SkipLine();
/* 1361 */       TabPrintLine(0, String.valueOf(String.valueOf(new StringBuffer("Iteration ").append(i + 1))));
/* 1362 */       TabPrintLine(0, "Population:");
/* 1363 */       for (int k = 1; k < content.length; k++) {
/* 1364 */         TabPrint(pos, content[k]);
/* 1365 */         TabPrint(pos + Width + content[k].length(), "|");
/* 1366 */         pos = pos + 2 * Width + content[k].length();
/*      */       }
/* 1368 */       TabPrintLine(0, "");
/*      */       
/* 1370 */       for (int k = 0; k < generation.length; k++) {
/* 1371 */         pos = 0;
/* 1372 */         for (int j = 0; j < generation[0].length; j++) {
/* 1373 */           TabPrint(pos - Width, generation[k][j]);
/* 1374 */           TabPrint(pos + Width + content[(j + 1)].length(), "|");
/* 1375 */           pos = pos + 2 * Width + content[(j + 1)].length();
/*      */         }
/* 1377 */         TabPrintLine(0, "");
/*      */       }
/* 1379 */       SkipLine();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1384 */       String[] content1 = { "", "Member", "  Parents  ", "  Children  ", "Solution", "Fitness" };
/*      */       
/* 1386 */       pos = 0;
/* 1387 */       TabPrintLine(0, "Children:");
/* 1388 */       for (int k = 1; k < content1.length; k++) {
/* 1389 */         TabPrint(pos, content1[k]);
/* 1390 */         TabPrint(pos + Width + content1[k].length(), "|");
/* 1391 */         pos = pos + 2 * Width + content1[k].length();
/*      */       }
/* 1393 */       TabPrintLine(0, "");
/*      */       
/* 1395 */       String[][] children = new String[6][5];
/* 1396 */       ArrayList child = (ArrayList)this.getype.generation.get(i);
/*      */       
/* 1398 */       Chromosome[][] parents = new Chromosome[3][2];
/* 1399 */       parents = (Chromosome[][])child.get(0);
/*      */       
/* 1401 */       int[][] mut = new int[3][2];
/* 1402 */       mut = (int[][])child.get(1);
/*      */       
/* 1404 */       Chromosome[][] all_children = new Chromosome[3][2];
/* 1405 */       all_children = (Chromosome[][])child.get(2);
/*      */       
/* 1407 */       ArrayList ccc = (ArrayList)child.get(3);
/*      */       
/* 1409 */       for (int m = 0; m < children.length; m += 2) {
/* 1410 */         ArrayList ccc1 = (ArrayList)ccc.get(m / 2);
/* 1411 */         int num_variables = parents[(m / 2)][0].getVariables().length;
/* 1412 */         children[m][0] = Integer.toString(mut[(m / 2)][0] + 1);
/* 1413 */         children[m][1] = arrayToString(parents[(m / 2)][0].getGene());
/* 1414 */         children[m][2] = mutation((byte[][])ccc1.get(0), all_children[(m / 2)][0].getGene(), num_variables);
/* 1415 */         children[m][3] = intArrayToString(all_children[(m / 2)][0].getVariables());
/* 1416 */         children[m][4] = Double.toString(all_children[(m / 2)][0].getFitness());
/*      */         
/* 1418 */         children[(m + 1)][0] = Integer.toString(mut[(m / 2)][1] + 1);
/* 1419 */         children[(m + 1)][1] = arrayToString(parents[(m / 2)][1].getGene());
/* 1420 */         children[(m + 1)][2] = mutation((byte[][])ccc1.get(1), all_children[(m / 2)][1].getGene(), num_variables);
/* 1421 */         children[(m + 1)][3] = intArrayToString(all_children[(m / 2)][1].getVariables());
/* 1422 */         children[(m + 1)][4] = Double.toString(all_children[(m / 2)][1].getFitness());
/*      */       }
/*      */       
/* 1425 */       for (int k = 0; k < children.length; k++) {
/* 1426 */         pos = 0;
/* 1427 */         for (int j = 0; j < children[0].length; j++) {
/* 1428 */           TabPrint(pos - Width, children[k][j]);
/* 1429 */           TabPrint(pos + Width + content1[(j + 1)].length(), "|");
/* 1430 */           pos = pos + 2 * Width + content1[(j + 1)].length();
/*      */         }
/* 1432 */         TabPrintLine(0, "");
/*      */       }
/* 1434 */       SkipLine();
/*      */     }
/*      */   }
/*      */   
/*      */   private String mutation(byte[][] mu, byte[][] gene, int num_variable) {
/* 1439 */     String xstr = "( ";
/*      */     
/* 1441 */     byte[][] temp = mu;
/* 1442 */     byte[][] show = gene;
/*      */     
/*      */ 
/* 1445 */     for (int i = 0; i < num_variable; i++) {
/* 1446 */       for (int j = 0; j < temp[i].length; j++) {
/* 1447 */         String zstr = "".concat(String.valueOf(String.valueOf(temp[i][j])));
/* 1448 */         if ("1".equals(zstr)) {
/* 1449 */           xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("[").append(show[i][j]).append("]"))))));
/*      */         }
/*      */         else {
/* 1452 */           xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(show[i][j]))))));
/*      */         }
/*      */       }
/*      */       
/* 1456 */       if (i < num_variable - 1) {
/* 1457 */         xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*      */       }
/*      */     }
/* 1460 */     xstr = String.valueOf(String.valueOf(xstr)).concat(" )");
/* 1461 */     return xstr;
/*      */   }
/*      */   
/*      */   private String intArrayToString(int[] array)
/*      */   {
/* 1466 */     String temp = "";
/* 1467 */     if (array.length == 1) { temp = String.valueOf(String.valueOf(new StringBuffer("(").append(Integer.toString(array[0])).append(")")));
/*      */     } else {
/* 1469 */       temp = String.valueOf(String.valueOf(temp)).concat("(");
/* 1470 */       for (int i = 0; i < array.length; i++) {
/* 1471 */         temp = String.valueOf(String.valueOf(temp)).concat(String.valueOf(String.valueOf(Integer.toString(array[i]))));
/* 1472 */         if (i != array.length - 1) temp = String.valueOf(String.valueOf(temp)).concat(",");
/*      */       }
/* 1474 */       temp = String.valueOf(String.valueOf(temp)).concat(")");
/*      */     }
/* 1476 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void printSubject()
/*      */   {
/* 1483 */     boolean First = true;
/* 1484 */     int[] variable = this.SimplexModel.Annealling;
/*      */     
/*      */ 
/* 1487 */     SkipLine();
/* 1488 */     SkipLine();
/* 1489 */     PrintLine("subject to");
/* 1490 */     for (int i = 0; i < variable.length; i++) {
/* 1491 */       TabPrintLine("subject to".length(), String.valueOf(String.valueOf(new StringBuffer("X").append(i + 1).append(" <= ").append(variable[i]).append("   "))));
/*      */     }
/* 1493 */     SkipLine();
/* 1494 */     for (i = 1; i <= this.SimplexModel.NumConstraints; i++) {
/* 1495 */       if ((this.ApplicationNumber != 5) || (i <= this.NumOriginalConstraints1))
/*      */       {
/* 1497 */         Print(String.valueOf(String.valueOf(FormatInteger(i, 2, 0, 1))).concat(")     "));
/* 1498 */         First = true;
/* 1499 */         for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/* 1500 */           PrintSpace(12, 9);
/* 1501 */           if ((this.SimplexModel.NumVariables <= 6) || (Math.abs(this.SimplexModel.A[i][j]) > 1.0E-6D))
/*      */           {
/* 1503 */             if (First) {
/* 1504 */               Print(FormatReal(this.SimplexModel.A[i][j], 6, 0, 1));
/*      */             }
/*      */             else {
/* 1507 */               Print(FormatReal(this.SimplexModel.A[i][j], 6, 1, 1));
/*      */             }
/* 1509 */             First = false;
/* 1510 */             Print(String.valueOf(String.valueOf(new StringBuffer(" X").append(IntegerToString(j, 2)).append(" "))));
/*      */           }
/*      */         }
/* 1513 */         PrintSpace(9, 9);
/* 1514 */         switch (this.SimplexModel.Constraint[i]) {
/*      */         case 0: 
/* 1516 */           Print("<= ");
/* 1517 */           break;
/*      */         case 1: 
/* 1519 */           Print(" = ");
/* 1520 */           break;
/*      */         case 2: 
/* 1522 */           Print(">= ");
/* 1523 */           break;
/*      */         }
/* 1525 */         PrintLine(FormatReal(this.SimplexModel.RightHandSide[i], 7, 0, 1));
/*      */         
/* 1527 */         SkipLine();
/*      */       }
/*      */     }
/* 1530 */     PrintLine("and");
/* 1531 */     SkipLine();
/* 1532 */     TabPrint(8, " ");
/* 1533 */     for (int j = 1; j <= this.SimplexModel.NumVariables; j++) {
/* 1534 */       if (this.SimplexModel.Nonnegativity[j] != 0) {
/* 1535 */         PrintSpace(15, 9);
/* 1536 */         if ((this.ApplicationNumber == 5) && (j <= this.NumBinaryVariables))
/*      */         {
/* 1538 */           Print(String.valueOf(String.valueOf(new StringBuffer("X").append(IntegerToString(j, 2)).append(" = {0,1}"))));
/* 1539 */         } else if ((this.ApplicationNumber == 5) && (j <= this.NumBinaryVariables + this.NumIntegerVariables))
/*      */         {
/* 1541 */           Print(String.valueOf(String.valueOf(new StringBuffer("X").append(IntegerToString(j, 2)).append("= {0,1,...}"))));
/*      */         } else {
/* 1543 */           Print(String.valueOf(String.valueOf(new StringBuffer("X").append(IntegerToString(j, 2)).append(" >= ").append(RealToString(this.SimplexModel.LowerBound[j], 6)))));
/*      */         }
/* 1545 */         boolean Last = true;
/* 1546 */         for (int k = j + 1; k <= this.SimplexModel.NumVariables; k++) {
/* 1547 */           if (this.SimplexModel.Nonnegativity[k] != 0)
/* 1548 */             Last = false;
/*      */         }
/* 1550 */         if (Last) {
/* 1551 */           Print(".");
/*      */         } else {
/* 1553 */           Print(", ");
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void PrintTSPModify()
/*      */   {
/* 1561 */     int Width = 7;
/* 1562 */     PrintLine("Number of Cities:     ".concat(String.valueOf(String.valueOf(FormatInteger(this.tspInputModel.num_city, 3, 0, 1)))));
/*      */     
/* 1564 */     SkipLine();
/* 1565 */     TabPrint(4, "City");
/* 1566 */     TabPrint(11, "|");
/* 1567 */     for (int j = 1; j <= this.tspInputModel.num_city; j++) {
/* 1568 */       TabPrint(13 + (j - 1) * Width, FormatInteger(j, Width - 1, 0, 1));
/*      */     }
/* 1570 */     TabPrintLine(13 + this.tspInputModel.num_city * Width, "|");
/* 1571 */     Print("__________|");
/* 1572 */     for (j = 1; j <= this.tspInputModel.num_city; j++)
/* 1573 */       Print("__________".substring(0, Width));
/* 1574 */     PrintLine("_|_______");
/* 1575 */     int maxValue_length = FormatReal(0.0D, Width - 1, 0, 1).length();
/* 1576 */     StringBuffer maxValue = new StringBuffer();
/* 1577 */     for (int k = 0; k < maxValue_length - 2; k++) {
/* 1578 */       maxValue.append(" ");
/*      */     }
/* 1580 */     maxValue.append("--");
/* 1581 */     for (int i = 1; i <= this.tspInputModel.matrix.length; i++) {
/* 1582 */       TabPrint(9, String.valueOf(String.valueOf(FormatInteger(i, 2, 0, 1))).concat("|"));
/* 1583 */       for (j = 1; j <= this.tspInputModel.matrix.length; j++) {
/* 1584 */         double temp = this.tspInputModel.matrix[(i - 1)][(j - 1)];
/* 1585 */         if (temp != Integer.MAX_VALUE) {
/* 1586 */           TabPrint(13 + (j - 1) * Width, FormatReal(temp, Width - 1, 0, 1));
/*      */         }
/*      */         else
/*      */         {
/* 1590 */           TabPrint(13 + (j - 1) * Width, maxValue.toString());
/*      */         }
/*      */       }
/* 1593 */       TabPrintLine(13 + this.tspInputModel.num_city * Width, "| ");
/*      */     }
/* 1595 */     Print("__________|");
/* 1596 */     for (j = 1; j <= this.tspInputModel.num_city; j++)
/* 1597 */       Print("__________".substring(0, Width));
/* 1598 */     PrintLine("_|_______");
/*      */   }
/*      */   
/*      */   public void resetSolver() {
/* 1602 */     this.started = false;
/* 1603 */     this.SAIsError = false;
/* 1604 */     this.isNew_SA = false;
/* 1605 */     this.isFinishGA = false;
/* 1606 */     this.isNew_Tabu = true;
/* 1607 */     this.isTabuInter = false;
/*      */   }
/*      */   
/*      */   private void PrintAnnealingTSP() {
/* 1611 */     if (this.tspInputModel.isAuto == null) return;
/* 1612 */     int Width = 3;
/* 1613 */     SkipLine();
/* 1614 */     PrintLine("Simulated Annealing Algorithm:     ");
/* 1615 */     SkipLine();
/* 1616 */     String[] content = { "", "Iteration", " T  ", " Trial Solution", "Distance", "Probability of Acceptance", "Accept" };
/*      */     
/*      */ 
/*      */ 
/* 1620 */     int pos = 0;
/* 1621 */     for (int i = 1; i < content.length; i++) {
/* 1622 */       TabPrint(pos, content[i]);
/* 1623 */       TabPrint(pos + Width + content[i].length(), "|");
/* 1624 */       pos = pos + 2 * Width + content[i].length();
/*      */     }
/* 1626 */     TabPrintLine(0, "");
/* 1627 */     String[][] date = null;
/* 1628 */     if ((this.tspInputModel.isAuto != null) && (this.tspInputModel.isAuto.equalsIgnoreCase("true")))
/*      */     {
/* 1630 */       date = (String[][])this.tspInputModel.SA_Auto_PrintData.elementAt(0);
/*      */     } else {
/* 1632 */       date = this.tspInputModel.SA_Next_PrintData;
/*      */     }
/* 1634 */     for (int i = 0; i < date.length; i++) {
/* 1635 */       pos = 0;
/* 1636 */       for (int j = 0; j < date[0].length; j++) {
/* 1637 */         TabPrint(pos, date[i][j]);
/* 1638 */         TabPrint(pos + Width + content[(j + 1)].length(), "|");
/* 1639 */         pos = pos + 2 * Width + content[(j + 1)].length();
/*      */       }
/* 1641 */       TabPrintLine(0, "");
/*      */     }
/* 1643 */     SkipLine();
/* 1644 */     TabPrintLine(0, this.tspInputModel.SA_BestSolutionInfoLab);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void PrintTabuTSP()
/*      */   {
/* 1652 */     int Width = 3;
/* 1653 */     SkipLine();
/* 1654 */     PrintLine("Tabu Search Algorithm:     ");
/* 1655 */     SkipLine();
/* 1656 */     String[] content = null;
/* 1657 */     content = new String[] { "", "Iteration", "  Trial Solution ", "Distance", "  Tabu List  " };
/*      */     
/*      */ 
/* 1660 */     int pos = 0;
/* 1661 */     String[][] date = null;
/*      */     
/* 1663 */     if (this.tspInputModel.isAuto == null) return;
/*      */     int i;
/* 1665 */     if ((this.tspInputModel.isAuto != null) && (this.tspInputModel.isAuto.equalsIgnoreCase("true")))
/*      */     {
/* 1667 */       if (this.tspInputModel.TB_Auto_Print_Table == null)
/* 1668 */         return;
/* 1669 */       date = this.tspInputModel.TB_Auto_Print_Table;
/* 1670 */       for (int i = 1; i < content.length; i++) {
/* 1671 */         TabPrint(pos, content[i]);
/* 1672 */         TabPrint(pos + Width + content[i].length() + 1, "|");
/* 1673 */         pos = pos + 2 * Width + content[i].length();
/*      */       }
/* 1675 */       TabPrintLine(0, "");
/* 1676 */       for (i = 0; i < date.length;) {
/* 1677 */         pos = 0;
/* 1678 */         for (int j = 0; j < date[0].length; j++) {
/* 1679 */           TabPrint(pos, date[i][j]);
/* 1680 */           TabPrint(pos + Width + content[(j + 1)].length() + 1, "|");
/* 1681 */           pos = pos + 2 * Width + content[(j + 1)].length();
/*      */         }
/* 1683 */         TabPrintLine(0, "");i++; continue;
/*      */         
/*      */ 
/*      */ 
/* 1687 */         if (this.tspInputModel.TB_Print_TableV == null)
/* 1688 */           return;
/* 1689 */         for (int i = 0; i < this.tspInputModel.TB_Print_TableV.size(); i++) {
/* 1690 */           pos = 0;
/* 1691 */           TabPrintLine(0, String.valueOf(String.valueOf(new StringBuffer("Step ").append(i + 1).append(":"))));
/* 1692 */           for (int j = 1; j < content.length; j++) {
/* 1693 */             TabPrint(pos, content[j]);
/* 1694 */             TabPrint(pos + Width + content[j].length() + 1, "|");
/* 1695 */             pos = pos + 2 * Width + content[j].length();
/*      */           }
/* 1697 */           TabPrintLine(0, "");
/* 1698 */           date = (String[][])this.tspInputModel.TB_Print_TableV.elementAt(i);
/* 1699 */           for (int k = 0; k < date.length; k++) {
/* 1700 */             pos = 0;
/* 1701 */             for (int j = 0; j < date[0].length; j++) {
/* 1702 */               TabPrint(pos, date[k][j]);
/* 1703 */               TabPrint(pos + Width + content[(j + 1)].length() + 1, "|");
/* 1704 */               pos = pos + 2 * Width + content[(j + 1)].length();
/*      */             }
/* 1706 */             TabPrintLine(0, "");
/*      */           }
/*      */         }
/*      */       } }
/* 1710 */     SkipLine();
/* 1711 */     PrintLine(String.valueOf(String.valueOf(new StringBuffer("Best Distance = ").append(this.tspInputModel.MinDistance).append("      Best Solution = ").append(this.tspInputModel.bestSolution))));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void PrintGATSP()
/*      */   {
/* 1718 */     int Width = 3;
/* 1719 */     SkipLine();
/* 1720 */     PrintLine("Genetic Algorithm:     ");
/* 1721 */     SkipLine();
/* 1722 */     if ((this.tspInputModel.GA_BestSolution_Data == null) || (this.tspInputModel.GA_Print_Pop_Data == null) || (this.tspInputModel.GA_Print_Child_Data == null))
/*      */     {
/*      */ 
/* 1725 */       return; }
/* 1726 */     String[] content = null;
/* 1727 */     content = new String[] { "", "Iteration", "  Best Solution ", " Fitness " };
/*      */     
/*      */ 
/* 1730 */     int pos = 0;
/* 1731 */     for (int i = 1; i < content.length; i++) {
/* 1732 */       TabPrint(pos, content[i]);
/* 1733 */       TabPrint(pos + Width + content[i].length(), "|");
/* 1734 */       pos = pos + 2 * Width + content[i].length();
/*      */     }
/* 1736 */     TabPrintLine(0, "");
/* 1737 */     String[][] date = this.tspInputModel.GA_BestSolution_Data;
/* 1738 */     for (int i = 0; i < date.length; i++) {
/* 1739 */       pos = 0;
/* 1740 */       for (int j = 0; j < date[0].length; j++) {
/* 1741 */         TabPrint(pos, date[i][j]);
/* 1742 */         TabPrint(pos + Width + content[(j + 1)].length(), "|");
/* 1743 */         pos = pos + 2 * Width + content[(j + 1)].length();
/*      */       }
/* 1745 */       TabPrintLine(0, "");
/*      */     }
/* 1747 */     SkipLine();
/* 1748 */     TabPrintLine(0, this.tspInputModel.SA_BestSolutionInfoLab);
/* 1749 */     SkipLine();
/* 1750 */     content = new String[] { "", "Member", "Population   ", "Fitness" };
/*      */     
/* 1752 */     String[] content1 = { "", "Member", "       Children     ", "Fitness" };
/*      */     
/* 1754 */     Vector pop_data = this.tspInputModel.GA_Print_Pop_Data;
/* 1755 */     Vector child_data = this.tspInputModel.GA_Print_Child_Data;
/* 1756 */     Vector mutation = this.tspInputModel.GA_Print_ChildNum;
/* 1757 */     for (int k = 0; k < pop_data.size(); k++) {
/* 1758 */       Vector per_mutation = (Vector)mutation.get(k);
/* 1759 */       SkipLine();
/* 1760 */       TabPrintLine(0, String.valueOf(String.valueOf(new StringBuffer("Iteration ").append(k + 1).append(":"))));
/* 1761 */       String[][] pop = (String[][])pop_data.elementAt(k);
/* 1762 */       String[][] child = (String[][])child_data.elementAt(k);
/* 1763 */       pos = 0;
/* 1764 */       for (int i = 1; i < content.length; i++) {
/* 1765 */         TabPrint(pos, content[i]);
/* 1766 */         TabPrint(pos + Width + content[i].length(), "|");
/* 1767 */         pos = pos + 2 * Width + content[i].length();
/*      */       }
/* 1769 */       pos += 10;
/* 1770 */       TabPrint(pos, "|");
/* 1771 */       pos += Width;
/* 1772 */       for (int i = 1; i < content1.length; i++) {
/* 1773 */         TabPrint(pos, content1[i]);
/* 1774 */         TabPrint(pos + Width + content1[i].length(), "|");
/* 1775 */         pos = pos + 2 * Width + content1[i].length();
/*      */       }
/* 1777 */       TabPrintLine(0, "");
/* 1778 */       for (int i = 0; i < pop.length; i++) {
/* 1779 */         pos = 0;
/* 1780 */         TabPrint(pos, Integer.toString(i + 1));
/* 1781 */         TabPrint(pos + Width + content[1].length(), "|");
/* 1782 */         pos = pos + content[2].length() - 1;
/* 1783 */         for (int j = 0; j < pop[0].length; j++) {
/* 1784 */           TabPrint(pos, pop[i][j]);
/* 1785 */           TabPrint(pos + Width + content[(j + 2)].length(), "|");
/* 1786 */           pos = pos + 2 * Width + content[(j + 2)].length();
/*      */         }
/* 1788 */         if (i < child.length)
/*      */         {
/* 1790 */           pos += 10;
/* 1791 */           TabPrint(pos, "|");
/* 1792 */           pos += Width;
/* 1793 */           TabPrint(pos, Integer.toString(((Integer)per_mutation.get(i)).intValue() + 1));
/* 1794 */           TabPrint(pos + Width + content1[1].length(), "|");
/* 1795 */           pos = pos + 2 * Width + content1[1].length();
/*      */           
/* 1797 */           for (int j = 0; j < child[0].length; j++) {
/* 1798 */             TabPrint(pos, child[i][j]);
/* 1799 */             TabPrint(pos + Width + content1[(j + 2)].length(), "|");
/* 1800 */             pos = pos + 2 * Width + content1[(j + 2)].length();
/*      */           }
/*      */         }
/* 1803 */         TabPrintLine(0, "");
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORTSPSolver.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */