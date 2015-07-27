/*     */ import java.awt.Point;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IORIPCanvas$2
/*     */   extends KeyAdapter
/*     */ {
/*     */   private final IORIPCanvas this$0;
/*     */   
/* 132 */   IORIPCanvas$2(IORIPCanvas this$0) { this.this$0 = this$0; }
/*     */   
/* 134 */   public void keyPressed(KeyEvent event) { Point p1 = new Point();
/* 135 */     switch (event.getKeyCode()) {
/*     */     case 37: 
/* 137 */       if (IORIPCanvas.access$5(this.this$0, IORIPCanvas.access$6(this.this$0, IORIPCanvas.access$7(this.this$0)), IORIPCanvas.access$8(this.this$0, IORIPCanvas.access$7(this.this$0)), IORIPCanvas.access$9(this.this$0), p1)) {
/* 138 */         IORIPCanvas.access$3(this.this$0, IORIPCanvas.access$10(this.this$0, IORIPCanvas.access$6(this.this$0, IORIPCanvas.access$7(this.this$0)) + 1, IORIPCanvas.access$8(this.this$0, IORIPCanvas.access$7(this.this$0)) * 2));
/* 139 */         this.this$0.actionPanel.updatePanel();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 161 */       break;
/*     */     case 39: 
/* 144 */       if (IORIPCanvas.access$11(this.this$0, IORIPCanvas.access$6(this.this$0, IORIPCanvas.access$7(this.this$0)), IORIPCanvas.access$8(this.this$0, IORIPCanvas.access$7(this.this$0)), IORIPCanvas.access$9(this.this$0), p1)) {
/* 145 */         IORIPCanvas.access$3(this.this$0, IORIPCanvas.access$10(this.this$0, IORIPCanvas.access$6(this.this$0, IORIPCanvas.access$7(this.this$0)) + 1, IORIPCanvas.access$8(this.this$0, IORIPCanvas.access$7(this.this$0)) * 2 + 1));
/* 146 */         this.this$0.actionPanel.updatePanel();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 161 */       break;
/*     */     case 38: 
/* 151 */       if (IORIPCanvas.access$7(this.this$0) > 0) {
/* 152 */         IORIPCanvas.access$3(this.this$0, IORIPCanvas.access$12(this.this$0, IORIPCanvas.access$6(this.this$0, IORIPCanvas.access$7(this.this$0)), IORIPCanvas.access$8(this.this$0, IORIPCanvas.access$7(this.this$0))));
/* 153 */         this.this$0.actionPanel.updatePanel();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 161 */       break;
/*     */     case 10: 
/* 158 */       IORIPCanvas.access$4(this.this$0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORIPCanvas$2.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */