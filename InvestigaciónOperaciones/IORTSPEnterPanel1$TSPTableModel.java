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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IORTSPEnterPanel1$TSPTableModel
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private int i;
/*     */   private int j;
/*     */   private final IORTSPEnterPanel1 this$0;
/*     */   
/* 386 */   private IORTSPEnterPanel1$TSPTableModel(IORTSPEnterPanel1 this$0) { this.this$0 = this$0; } IORTSPEnterPanel1$TSPTableModel(IORTSPEnterPanel1 x$0, IORTSPEnterPanel1..3 x$1) { this(x$0); }
/*     */   
/*     */   public int getColumnCount()
/*     */   {
/* 390 */     return IORTSPEnterPanel1.access$11(this.this$0) + 1;
/*     */   }
/*     */   
/*     */   public int getRowCount()
/*     */   {
/* 395 */     return IORTSPEnterPanel1.access$11(this.this$0);
/*     */   }
/*     */   
/*     */   public String getColumnName(int col)
/*     */   {
/* 400 */     String str = "";
/* 401 */     if (col == 0) {
/* 402 */       str = String.valueOf(String.valueOf(str)).concat("City");
/*     */     } else
/* 404 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTSPEnterPanel1.access$12(this.this$0)[(col - 1)])));
/* 405 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object getValueAt(int row, int col)
/*     */   {
/* 411 */     String str = "";
/* 412 */     int r = -1;int c = -1;
/* 413 */     if ((col == 0) && (row == 0)) {
/* 414 */       str = String.valueOf(String.valueOf(str)).concat("    1");
/*     */     }
/* 416 */     else if (col == 0) {
/* 417 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("    ".concat(String.valueOf(String.valueOf(IORTSPEnterPanel1.access$12(this.this$0)[row]))))));
/*     */     }
/* 419 */     else if (col - 1 < row) {
/* 420 */       str = "";
/*     */     }
/*     */     else {
/* 423 */       r = row;
/* 424 */       c = col - 1;
/* 425 */       if (r != IORTSPEnterPanel1.access$2(this.this$0)) {
/* 426 */         if (r == c) {
/* 427 */           str = String.valueOf(String.valueOf(str)).concat("");
/*     */         }
/* 429 */         else if ((this.this$0.matrix[r][c] == Integer.MAX_VALUE) || (this.this$0.matrix[r][c] == 0)) {
/* 430 */           str = "";
/*     */         }
/*     */         else
/*     */         {
/* 434 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(this.this$0.matrix[r][c]))))));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 439 */     return str;
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int c) {
/* 443 */     return getValueAt(0, c).getClass();
/*     */   }
/*     */   
/*     */   public boolean isCellEditable(int row, int col)
/*     */   {
/* 448 */     if (!IORTSPEnterPanel1.access$13(this.this$0))
/* 449 */       return false;
/* 450 */     if (col == 0) {
/* 451 */       return false;
/*     */     }
/* 453 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setValueAt(Object value, int row, int col)
/*     */   {
/* 459 */     String input = (String)value;
/* 460 */     if ((col != 0) && (row < col - 1)) {
/* 461 */       if (input.equalsIgnoreCase("")) {
/* 462 */         this.this$0.matrix[row][(col - 1)] = 0.0D;
/* 463 */         this.this$0.matrix[(col - 1)][row] = 0.0D;
/* 464 */         fireTableCellUpdated(row, col - 1);
/* 465 */         return;
/*     */       }
/*     */       try {
/* 468 */         double tempInput = Double.valueOf(input).doubleValue();
/* 469 */         if (tempInput == 0) {
/* 470 */           return;
/*     */         }
/*     */         
/* 473 */         double d = Double.valueOf(input).doubleValue();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 482 */         this.this$0.matrix[row][(col - 1)] = d;
/* 483 */         this.this$0.matrix[(col - 1)][row] = d;
/*     */       }
/*     */       catch (NumberFormatException e) {
/* 486 */         return;
/*     */       }
/*     */       
/* 489 */       fireTableCellUpdated(row, col - 1);
/* 490 */       this.this$0.initPrintData();
/*     */     }
/*     */     
/* 493 */     for (int i = 0; i < IORTSPEnterPanel1.access$2(this.this$0); i++) {
/* 494 */       for (int j = 0; j < IORTSPEnterPanel1.access$2(this.this$0); j++) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 501 */     this.this$0.initPrintData();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTSPEnterPanel1$TSPTableModel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */