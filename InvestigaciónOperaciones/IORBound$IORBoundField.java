/*     */ import java.text.DecimalFormat;
/*     */ import javax.swing.JComboBox;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IORBound$IORBoundField
/*     */ {
/*     */   private final String[] functionStrings;
/*     */   private final int GREATER = 0;
/*     */   private final int NONE = 1;
/*     */   private JComboBox cb_operator;
/*     */   private DecimalField bound;
/*     */   private final IORBound this$0;
/*     */   
/*     */   IORBound$IORBoundField(IORBound this$0)
/*     */   {
/* 127 */     this.this$0 = this$0;this.functionStrings = new String[] { " >=", "n/a" };this.GREATER = 0;this.NONE = 1;this.cb_operator = null;this.bound = null;
/* 128 */     this.cb_operator = new JComboBox(this.functionStrings);
/* 129 */     this.cb_operator.addActionListener(new IORBound.1((IORBoundField)this));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 140 */     this.bound = new DecimalField(0.0D, new DecimalFormat());
/*     */   }
/*     */   
/*     */   public void setEditable(boolean b) {
/* 144 */     this.cb_operator.setEnabled(b);
/* 145 */     boolean bounded = isBounded();
/* 146 */     if (b) {
/* 147 */       if (bounded) {
/* 148 */         this.bound.setEditable(true);
/*     */       }
/*     */       else {
/* 151 */         this.bound.setEditable(false);
/*     */       }
/*     */     }
/*     */     else {
/* 155 */       this.bound.setEditable(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isBounded() {
/* 160 */     boolean bounded = false;
/* 161 */     if (this.cb_operator.getSelectedIndex() == 0)
/* 162 */       bounded = true;
/* 163 */     return bounded;
/*     */   }
/*     */   
/*     */   private double getBound() {
/* 167 */     double b = 0.0D;
/* 168 */     if (this.bound != null) {
/* 169 */       b = this.bound.getValue();
/*     */     }
/* 171 */     return b;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORBound$IORBoundField.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */