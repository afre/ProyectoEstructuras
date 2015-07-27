/*     */ import java.text.DecimalFormat;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IORQTJacksonPanel$IORQueueTableModel
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private int i;
/*     */   private int j;
/*     */   private final IORQTJacksonPanel this$0;
/*     */   
/*     */   IORQTJacksonPanel$IORQueueTableModel(IORQTJacksonPanel this$0)
/*     */   {
/* 257 */     this.this$0 = this$0;
/*     */   }
/*     */   
/*     */   public int getColumnCount()
/*     */   {
/* 262 */     return IORQTJacksonPanel.access$2(this.this$0) + 2;
/*     */   }
/*     */   
/*     */   public int getRowCount() {
/* 266 */     return IORQTJacksonPanel.access$2(this.this$0);
/*     */   }
/*     */   
/*     */   public String getColumnName(int col) {
/* 270 */     if (col == 0)
/* 271 */       return "Facility  j";
/* 272 */     if (col == 1) {
/* 273 */       return "aj";
/*     */     }
/* 275 */     return String.valueOf(String.valueOf(new StringBuffer("Pij (i = ").append(col - 1).append(")")));
/*     */   }
/*     */   
/*     */   public Object getValueAt(int row, int col) {
/* 279 */     String str = new String();
/* 280 */     DecimalFormat dfm = new DecimalFormat();
/* 281 */     if (col == 0)
/* 282 */       return String.valueOf(String.valueOf(new StringBuffer("   j = ").append(row + 1)));
/* 283 */     if (col == 1) {
/* 284 */       return dfm.format(IORQTJacksonPanel.access$6(this.this$0)[row]);
/*     */     }
/*     */     
/* 287 */     return dfm.format(IORQTJacksonPanel.access$7(this.this$0)[(col - 2)][row]);
/*     */   }
/*     */   
/*     */ 
/*     */   public Class getColumnClass(int c)
/*     */   {
/* 293 */     return getValueAt(0, c).getClass();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isCellEditable(int row, int col)
/*     */   {
/* 299 */     if (col == 0) {
/* 300 */       return false;
/*     */     }
/* 302 */     return true;
/*     */   }
/*     */   
/*     */   public void setValueAt(Object value, int row, int col)
/*     */   {
/* 307 */     if (col == 1)
/*     */     {
/* 309 */       IORQTJacksonPanel.access$6(this.this$0)[row] = Double.parseDouble((String)value);
/*     */     }
/*     */     else
/*     */     {
/* 313 */       IORQTJacksonPanel.access$7(this.this$0)[(col - 2)][row] = Double.parseDouble((String)value);
/*     */     }
/* 315 */     fireTableCellUpdated(row, col);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORQTJacksonPanel$IORQueueTableModel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */