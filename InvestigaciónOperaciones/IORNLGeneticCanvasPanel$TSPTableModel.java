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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IORNLGeneticCanvasPanel$TSPTableModel
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private int i;
/*     */   private int j;
/*     */   private final IORNLGeneticCanvasPanel this$0;
/*     */   
/* 354 */   private IORNLGeneticCanvasPanel$TSPTableModel(IORNLGeneticCanvasPanel this$0) { this.this$0 = this$0; } IORNLGeneticCanvasPanel$TSPTableModel(IORNLGeneticCanvasPanel x$0, IORNLGeneticCanvasPanel..8 x$1) { this(x$0); }
/*     */   
/*     */ 
/*     */   public int getColumnCount()
/*     */   {
/* 359 */     return this.this$0.tableCol;
/*     */   }
/*     */   
/*     */   public int getRowCount()
/*     */   {
/* 364 */     return this.this$0.tableRow;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getColumnName(int col)
/*     */   {
/* 370 */     String str = "";
/* 371 */     str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(this.this$0.colName[col])));
/* 372 */     return str;
/*     */   }
/*     */   
/*     */   public Object getValueAt(int row, int col)
/*     */   {
/* 377 */     String str = "";String xstr = "";
/* 378 */     DecimalFormat decfm = new DecimalFormat("#.###");
/* 379 */     int r = -1;int c = -1;
/* 380 */     if ((col == 0) && (row == 0)) {
/* 381 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(1)));
/*     */     }
/* 383 */     else if (col == 0) {
/* 384 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(row + 1)));
/*     */     } else {
/* 386 */       if (col == 1) {
/* 387 */         xstr = "( ";
/* 388 */         int[] temp = IORNLGeneticCanvasPanel.access$3(this.this$0).controller.nldata.best[row].getVariables();
/* 389 */         for (this.i = 0; this.i < IORNLGeneticCanvasPanel.access$3(this.this$0).controller.nldata.num_variable; this.i += 1) {
/* 390 */           xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(decfm.format(temp[this.i]))));
/* 391 */           if (this.i < IORNLGeneticCanvasPanel.access$3(this.this$0).controller.nldata.num_variable - 1) {
/* 392 */             xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*     */           }
/*     */         }
/* 395 */         xstr = String.valueOf(String.valueOf(xstr)).concat(" )");
/* 396 */         return xstr;
/*     */       }
/* 398 */       if (col == 2) {
/* 399 */         return "".concat(String.valueOf(String.valueOf(IORNLGeneticCanvasPanel.access$3(this.this$0).controller.nldata.best[row].getFitness())));
/*     */       }
/*     */       
/* 402 */       if (col != 3)
/*     */       {
/*     */ 
/* 405 */         if (col != 4)
/*     */         {
/*     */ 
/* 408 */           if (col != 5) {} }
/*     */       }
/*     */     }
/* 411 */     return str;
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int c) {
/* 415 */     return getValueAt(0, c).getClass();
/*     */   }
/*     */   
/*     */   public boolean isCellEditable(int row, int col) {
/* 419 */     if (!IORNLGeneticCanvasPanel.access$4(this.this$0))
/* 420 */       return false;
/* 421 */     if (col == 0) {
/* 422 */       return false;
/*     */     }
/* 424 */     return true;
/*     */   }
/*     */   
/*     */   public void setValueAt(Object value, int row, int col)
/*     */   {
/* 429 */     String input = (String)value;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNLGeneticCanvasPanel$TSPTableModel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */