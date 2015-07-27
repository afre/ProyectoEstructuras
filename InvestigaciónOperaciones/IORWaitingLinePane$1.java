/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IORWaitingLinePane$1
/*     */   implements ItemListener
/*     */ {
/*     */   private final IORWaitingLinePane this$0;
/*     */   
/*  89 */   IORWaitingLinePane$1(IORWaitingLinePane this$0) { this.this$0 = this$0; }
/*     */   
/*  91 */   public void itemStateChanged(ItemEvent e) { String sp = (String)e.getItem();
/*  92 */     if (sp.equals("slow")) {
/*  93 */       this.this$0.phasePan1.setMSEL(10000);
/*  94 */       this.this$0.phasePan2.setMSEL(10000);
/*     */     }
/*  96 */     else if (sp.equals("normal")) {
/*  97 */       this.this$0.phasePan1.setMSEL(2000);
/*  98 */       this.this$0.phasePan2.setMSEL(2000);
/*     */     }
/*     */     else {
/* 101 */       this.this$0.phasePan1.setMSEL(1000);
/* 102 */       this.this$0.phasePan2.setMSEL(1000);
/*     */     }
/*     */     
/* 105 */     this.this$0.savePrintInfo();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORWaitingLinePane$1.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */