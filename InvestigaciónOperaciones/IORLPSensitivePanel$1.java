/*     */ import java.awt.Color;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IORLPSensitivePanel$1
/*     */   extends DefaultTableCellRenderer
/*     */ {
/*     */   public void setValue(Object value)
/*     */   {
/* 122 */     if ((value instanceof IORTableCell)) {
/* 123 */       if (((IORTableCell)value).isChanged == true) {
/* 124 */         setForeground(Color.red);
/*     */       }
/*     */       else {
/* 127 */         setForeground(Color.black);
/*     */       }
/*     */       
/* 130 */       if (((IORTableCell)value).isBlock == true) {
/* 131 */         setBackground(Color.cyan);
/* 132 */       } else if (((IORTableCell)value).isChanged == true) {
/* 133 */         setBackground(Color.lightGray);
/*     */       }
/*     */       else {
/* 136 */         setBackground(Color.white);
/*     */       }
/*     */     }
/* 139 */     super.setValue(value);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPSensitivePanel$1.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */