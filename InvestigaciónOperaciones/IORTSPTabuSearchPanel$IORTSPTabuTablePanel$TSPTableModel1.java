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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IORTSPTabuSearchPanel$IORTSPTabuTablePanel$TSPTableModel1
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private int i;
/*     */   private int j;
/*     */   private final IORTSPTabuSearchPanel.IORTSPTabuTablePanel this$1;
/*     */   
/* 301 */   private IORTSPTabuSearchPanel$IORTSPTabuTablePanel$TSPTableModel1(IORTSPTabuSearchPanel.IORTSPTabuTablePanel this$1) { this.this$1 = this$1; } IORTSPTabuSearchPanel$IORTSPTabuTablePanel$TSPTableModel1(IORTSPTabuSearchPanel.IORTSPTabuTablePanel x$0, IORTSPTabuSearchPanel..6 x$1) { this(x$0); }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getColumnCount()
/*     */   {
/* 307 */     return IORTSPTabuSearchPanel.IORTSPTabuTablePanel.access$10(this.this$1);
/*     */   }
/*     */   
/*     */   public int getRowCount()
/*     */   {
/* 312 */     return IORTSPTabuSearchPanel.IORTSPTabuTablePanel.access$11(this.this$1);
/*     */   }
/*     */   
/*     */   public String getColumnName(int col)
/*     */   {
/* 317 */     String str = "";
/* 318 */     if (col == 0) {
/* 319 */       str = String.valueOf(String.valueOf(str)).concat("City");
/*     */     } else
/* 321 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(col)));
/* 322 */     return str;
/*     */   }
/*     */   
/*     */   public Object getValueAt(int row, int col)
/*     */   {
/* 327 */     String str = "";
/* 328 */     int r = -1;int c = -1;
/* 329 */     if (col == 0) {
/* 330 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(row + 1)));
/*     */     }
/* 332 */     else if (col - 1 < row) {
/* 333 */       str = "";
/*     */     }
/*     */     else {
/* 336 */       r = row;
/* 337 */       c = col - 1;
/* 338 */       if (r != IORTSPTabuSearchPanel.IORTSPTabuTablePanel.access$12(this.this$1).controller.data.num_city) {
/* 339 */         if (IORTSPTabuSearchPanel.IORTSPTabuTablePanel.access$12(this.this$1).controller.data.matrix[r][c] == Integer.MAX_VALUE) {
/* 340 */           str = "-----";
/*     */         }
/* 342 */         else if (r == c) {
/* 343 */           str = "";
/*     */         }
/*     */         else {
/* 346 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(IORTSPTabuSearchPanel.IORTSPTabuTablePanel.access$12(this.this$1).controller.data.matrix[r][c]))))));
/*     */         }
/*     */       }
/*     */     }
/* 350 */     return str;
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int c)
/*     */   {
/* 355 */     return getValueAt(0, c).getClass();
/*     */   }
/*     */   
/*     */   public boolean isCellEditable(int row, int col) {
/* 359 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setValueAt(Object value, int row, int col)
/*     */   {
/* 365 */     String input = (String)value;
/* 366 */     fireTableCellUpdated(row, col - 1);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTSPTabuSearchPanel$IORTSPTabuTablePanel$TSPTableModel1.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */