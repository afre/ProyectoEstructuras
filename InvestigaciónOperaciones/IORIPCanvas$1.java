/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IORIPCanvas$1
/*     */   extends MouseAdapter
/*     */ {
/*     */   private final IORIPCanvas this$0;
/*     */   
/* 116 */   IORIPCanvas$1(IORIPCanvas this$0) { this.this$0 = this$0; }
/*     */   
/* 118 */   public void mousePressed(MouseEvent event) { IORIPCanvas.access$0(this.this$0).requestFocus();
/*     */     
/* 120 */     if (IORIPCanvas.access$1(this.this$0) > 0) return;
/* 121 */     int nodeNo = IORIPCanvas.access$2(this.this$0, event.getPoint());
/* 122 */     if (nodeNo >= 0) {
/* 123 */       IORIPCanvas.access$3(this.this$0, nodeNo);
/*     */       
/* 125 */       this.this$0.actionPanel.updatePanel();
/* 126 */       if (event.getClickCount() >= 2) {
/* 127 */         IORIPCanvas.access$4(this.this$0);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORIPCanvas$1.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */