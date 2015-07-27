/*     */ import java.io.Serializable;
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
/*     */ public class IORNLPProcessController$NLData
/*     */   implements Serializable
/*     */ {
/* 363 */   final int MAX_NUM_VARIABLE = 3;
/* 364 */   final int MAX_NUM_ITERATION = 20;
/*     */   int num_term;
/*     */   boolean is_max;
/*     */   double[] newx;
/*     */   double[] fx;
/*     */   double[] df;
/*     */   double[] low_bound;
/*     */   double[] up_bound;
/*     */   double[] fx_coef;
/*     */   double[] df_coef;
/*     */   int[] fx_power;
/*     */   int[] df_power;
/*     */   double[] oldx1;
/*     */   double[] oldx2;
/*     */   double[] newx1;
/*     */   double[] newx2;
/*     */   double[] gradx1;
/*     */   double[] gradx2;
/* 382 */   double[] a1; double[] b1; double[] a2; double[] b2; double[] tt; double[] fx1x2_coef; double[] dfx1_coef; double[] dfx2_coef; double[] ft_coef; int[] fx_x1exp; int[] fx_x2exp; int[] dfx1_x1exp; int[] dfx1_x2exp; int[] dfx2_x1exp; int[] dfx2_x2exp; int[] ft_texp; int t_function_num_term; double errTolerance; int num_variable; int num_constraint; IORNLPProcessController.NLData.NLEquation fw_objective = new IORNLPProcessController.NLData.NLEquation();
/* 383 */   IORNLPProcessController.NLData.NLEquation[] fw_derivative = new IORNLPProcessController.NLData.NLEquation[3];
/*     */   
/*     */   double[][] fw_oldx;
/*     */   
/*     */   double[][] fw_newx;
/*     */   
/*     */   double[][] fw_a;
/*     */   
/*     */   double[][] fw_b;
/*     */   
/*     */   double[][] fw_xlp;
/*     */   
/*     */   double[][] fw_constraint_coef;
/*     */   
/*     */   int[] fw_constraint_operator;
/*     */   
/*     */   double[][] fw_flp_coef;
/*     */   
/*     */   double[][] fw_gradx;
/*     */   
/*     */   double[] fw_tt;
/*     */   
/*     */   double[][] fw_ht_coef;
/*     */   
/*     */   int[][] fw_ht_texp;
/*     */   
/*     */   int[] fw_ht_num_term;
/*     */   
/*     */   double[] Temp;
/*     */   double[][] variable;
/*     */   double[] function;
/*     */   double[] probability;
/*     */   boolean[] accept;
/*     */   int totalresult;
/*     */   int[] itertag;
/*     */   int pos;
/*     */   double bestresult;
/*     */   double[] bestvarible;
/*     */   int totalrecord;
/*     */   Chromosome[] best;
/*     */   Chromosome[] store;
/*     */   Chromosome[] children;
/*     */   int[] parentindex;
/*     */   byte[][][] bytag;
/*     */   int sumt_num_variable_with_bound;
/*     */   int sumt_num_variable_without_bound;
/*     */   int sumt_num_inequality_constraint;
/*     */   int sumt_num_equality_constraint;
/*     */   double[] sumt_oldx;
/*     */   double[] sumt_newx;
/*     */   double sumt_r;
/* 434 */   IORNLPProcessController.NLData.NLEquation sumt_objective = new IORNLPProcessController.NLData.NLEquation();
/* 435 */   IORNLPProcessController.NLData.NLEquation[] sumt_Bx = new IORNLPProcessController.NLData.NLEquation[3];
/* 436 */   IORNLPProcessController.NLData.NLEquation[] sumt_Lx = new IORNLPProcessController.NLData.NLEquation[3];
/*     */   
/*     */ 
/*     */   public IORNLPProcessController$NLData(IORNLPProcessController this$0)
/*     */   {
/* 441 */     for (int i = 0; i < 3; i++) {
/* 442 */       this.fw_derivative[i] = new IORNLPProcessController.NLData.NLEquation();
/* 443 */       this.sumt_Bx[i] = new IORNLPProcessController.NLData.NLEquation();
/* 444 */       this.sumt_Lx[i] = new IORNLPProcessController.NLData.NLEquation();
/*     */     }
/*     */   }
/*     */   
/*     */   public class NLEquation implements Serializable {
/* 449 */     final int MAX_EQ_TERM = 10;
/*     */     int num_term;
/* 451 */     int num_var; double[] fxcoef = new double[10];
/* 452 */     int[][] xexp = new int[10][3];
/*     */     
/*     */     public NLEquation() {}
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNLPProcessController$NLData.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */