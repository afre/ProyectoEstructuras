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
/*     */ class IORTPEnterPanel$TPTableModel
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private int i;
/*     */   private int j;
/*     */   private final IORTPEnterPanel this$0;
/*     */   
/* 298 */   private IORTPEnterPanel$TPTableModel(IORTPEnterPanel this$0) { this.this$0 = this$0; } IORTPEnterPanel$TPTableModel(IORTPEnterPanel x$0, IORTPEnterPanel..3 x$1) { this(x$0); }
/*     */   
/*     */ 
/*     */   public int getColumnCount()
/*     */   {
/* 303 */     return IORTPEnterPanel.access$4(this.this$0) + 2;
/*     */   }
/*     */   
/*     */   public int getRowCount()
/*     */   {
/* 308 */     return IORTPEnterPanel.access$5(this.this$0) + 1;
/*     */   }
/*     */   
/*     */   public String getColumnName(int col)
/*     */   {
/* 313 */     String str = "";
/* 314 */     if (col == 0) {
/* 315 */       str = String.valueOf(String.valueOf(str)).concat("Source");
/* 316 */     } else if (col == IORTPEnterPanel.access$4(this.this$0) + 1) {
/* 317 */       str = String.valueOf(String.valueOf(str)).concat("Supply");
/*     */     }
/*     */     else {
/* 320 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(col)));
/*     */     }
/* 322 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object getValueAt(int row, int col)
/*     */   {
/* 328 */     String str = "";
/* 329 */     int r = -1;int c = -1;
/*     */     
/* 331 */     if ((col == 0) && (row == IORTPEnterPanel.access$5(this.this$0))) {
/* 332 */       str = String.valueOf(String.valueOf(str)).concat("Demand");
/*     */ 
/*     */     }
/* 335 */     else if ((col != IORTPEnterPanel.access$4(this.this$0) + 1) || (row != IORTPEnterPanel.access$5(this.this$0)))
/*     */     {
/*     */ 
/* 338 */       if (col == 0) {
/* 339 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(row + 1)));
/*     */ 
/*     */       }
/* 342 */       else if ((row == IORTPEnterPanel.access$5(this.this$0)) && (col >= 1)) {
/* 343 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTPEnterPanel.access$6(this.this$0)[(col - 1)])));
/*     */ 
/*     */       }
/* 346 */       else if (col == IORTPEnterPanel.access$4(this.this$0) + 1) {
/* 347 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTPEnterPanel.access$7(this.this$0)[row])));
/*     */       }
/*     */       else
/*     */       {
/* 351 */         r = row;
/* 352 */         c = col - 1;
/* 353 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTPActionPanel.getCellString(IORTPEnterPanel.access$8(this.this$0)[r][c], IORTPEnterPanel.access$9(this.this$0)[r][c]))));
/*     */       }
/*     */     }
/* 356 */     return str;
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int c)
/*     */   {
/* 361 */     return getValueAt(0, c).getClass();
/*     */   }
/*     */   
/*     */   public boolean isCellEditable(int row, int col)
/*     */   {
/* 366 */     if (!IORTPEnterPanel.access$10(this.this$0)) {
/* 367 */       return false;
/*     */     }
/* 369 */     if (col == 0)
/* 370 */       return false;
/* 371 */     if ((col == IORTPEnterPanel.access$4(this.this$0) + 1) && (row == IORTPEnterPanel.access$5(this.this$0))) {
/* 372 */       return false;
/*     */     }
/* 374 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setValueAt(Object value, int row, int col)
/*     */   {
/* 380 */     String input = (String)value;
/*     */     
/* 382 */     if ((row == IORTPEnterPanel.access$5(this.this$0)) || (col == IORTPEnterPanel.access$4(this.this$0) + 1)) {
/*     */       try {
/* 384 */         int r = Integer.parseInt(input);
/* 385 */         if (row == IORTPEnterPanel.access$5(this.this$0)) {
/* 386 */           IORTPEnterPanel.access$6(this.this$0)[(col - 1)] = r;
/*     */         } else {
/* 388 */           IORTPEnterPanel.access$7(this.this$0)[row] = r;
/*     */         }
/*     */       } catch (NumberFormatException e) {
/* 391 */         return;
/*     */       }
/*     */     }
/*     */     else {
/* 395 */       double[] d = IORTPActionPanel.parseStringInput(input);
/* 396 */       IORTPEnterPanel.access$8(this.this$0)[row][(col - 1)] = d[0];
/* 397 */       IORTPEnterPanel.access$9(this.this$0)[row][(col - 1)] = d[1];
/*     */     }
/*     */     
/* 400 */     fireTableCellUpdated(row, col);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTPEnterPanel$TPTableModel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */