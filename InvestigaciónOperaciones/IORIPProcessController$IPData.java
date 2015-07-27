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
/*     */ public class IORIPProcessController$IPData
/*     */   implements Serializable
/*     */ {
/* 219 */   public int max_num_binary = 5;
/* 220 */   public int max_num_integer = 5;
/* 221 */   public int max_num_continuous = 5;
/* 222 */   public int max_num_constrain = 5;
/*     */   
/* 224 */   public boolean objective_is_max = true;
/* 225 */   public double[] objective_coefficient = null;
/* 226 */   public int[] constrain_operator = null;
/* 227 */   public double[][] constrain_coefficient = null;
/*     */   
/* 229 */   public int MAX_NODE_NUMBER = 63;
/*     */   
/*     */   public boolean isBIP;
/* 232 */   public int NumVariables = 3;
/* 233 */   public int NumBinaryVariables = 1;
/* 234 */   public int NumIntegerVariables = 1;
/* 235 */   public int NumRealVariables = 1;
/* 236 */   public int NumConstraints = 1;
/*     */   
/* 238 */   public boolean IncumbentExists = false;
/* 239 */   public double[] Incumbent = new double[this.MAX_NODE_NUMBER];
/* 240 */   public double IncumbentZ = 0.0D;
/* 241 */   public int IncumbentNodeIndex = -1;
/*     */   
/* 243 */   public int maxLevel = 0;
/* 244 */   public IORIPProcessController.NodeType[] nodes = new IORIPProcessController.NodeType[this.MAX_NODE_NUMBER];
/*     */   
/*     */   public IORIPProcessController$IPData() {
/* 247 */     for (int i = 0; i < this.MAX_NODE_NUMBER; i++) {
/* 248 */       this.nodes[i] = new IORIPProcessController.NodeType();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORIPProcessController$IPData.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */