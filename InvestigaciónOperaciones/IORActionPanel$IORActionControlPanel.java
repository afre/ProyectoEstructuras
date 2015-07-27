/*     */ import java.awt.BorderLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IORActionPanel$IORActionControlPanel
/*     */   extends JPanel
/*     */ {
/*     */   private final IORActionPanel this$0;
/*     */   
/*     */   public IORActionPanel$IORActionControlPanel(IORActionPanel this$0)
/*     */   {
/* 126 */     this.this$0 = this$0;
/* 127 */     this$0.bn_back = new JButton("Back");
/* 128 */     this$0.bn_back.addActionListener(new IORActionPanel.1((IORActionControlPanel)this));
/*     */     
/* 130 */     this$0.bn_back.setEnabled(false);
/*     */     
/* 132 */     this$0.bn_finish = new JButton("Finish");
/* 133 */     this$0.bn_finish.addActionListener(new IORActionPanel.2((IORActionControlPanel)this));
/*     */     
/* 135 */     this$0.bn_finish.setEnabled(true);
/*     */     
/* 137 */     setLayout(new BorderLayout());
/* 138 */     add(this$0.bn_back, "West");
/* 139 */     add(this$0.bn_finish, "East");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORActionPanel$IORActionControlPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */