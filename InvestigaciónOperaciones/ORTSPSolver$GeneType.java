/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ORTSPSolver$GeneType
/*     */   implements Serializable
/*     */ {
/*     */   ArrayList[] ge_list;
/*     */   
/*     */   ORTSPSolver$GeneType(ORTSPSolver x$0, ORTSPSolver..1 x$1)
/*     */   {
/* 108 */     this(x$0);
/*     */   }
/*     */   
/* 111 */   int ge_size = 0;
/* 112 */   int currentindex = 0;
/* 113 */   Vector best = new Vector();
/* 114 */   Vector storePopulation = new Vector();
/* 115 */   Vector generation = new Vector();
/*     */   
/*     */   public void setCurrentIndex(int n) {
/* 118 */     this.currentindex = n;
/*     */   }
/*     */   
/*     */   private ORTSPSolver$GeneType(ORTSPSolver this$0) {}
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORTSPSolver$GeneType.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */