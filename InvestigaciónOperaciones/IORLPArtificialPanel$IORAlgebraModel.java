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
/*     */ class IORLPArtificialPanel$IORAlgebraModel
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private int i;
/*     */   private int j;
/*     */   private IORTableCell[][] data;
/*     */   private final IORLPArtificialPanel this$0;
/*     */   
/*     */   public IORLPArtificialPanel$IORAlgebraModel(IORLPArtificialPanel this$0)
/*     */   {
/* 292 */     this.this$0 = this$0;this.data = new IORTableCell[this.this$0.controller.data.num_constrain + 2][this.this$0.controller.data.num_variable + 2];
/* 293 */     for (this.i = 0; this.i < this$0.controller.data.num_constrain + 2; this.i += 1) {
/* 294 */       for (this.j = 0; this.j < this$0.controller.data.num_variable + 2; this.j += 1) {
/* 295 */         this.data[this.i][this.j] = new IORTableCell(0.0D, 0.0D);
/* 296 */         this.data[this.i][this.j].setDisplayForm(1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int getColumnCount() {
/* 302 */     return this.this$0.controller.data.num_variable + 4;
/*     */   }
/*     */   
/*     */   public int getRowCount() {
/* 306 */     return this.this$0.controller.data.num_constrain + 1;
/*     */   }
/*     */   
/*     */   public String getColumnName(int col) {
/* 310 */     if (col == 0)
/* 311 */       return "empty";
/* 312 */     if (col == 1)
/* 313 */       return "Eq.";
/* 314 */     if (col == 2)
/* 315 */       return "Z";
/* 316 */     if ((col >= 3) && (col < 3 + this.this$0.controller.data.num_variable)) {
/* 317 */       return "x".concat(String.valueOf(String.valueOf(col - 2)));
/*     */     }
/*     */     
/* 320 */     return "Right Side";
/*     */   }
/*     */   
/*     */   public Object getValueAt(int row, int col) {
/* 324 */     int xsub = 0;
/*     */     
/*     */ 
/* 327 */     if (col == 0)
/* 328 */       return " ";
/* 329 */     if (col == 1) {
/* 330 */       return String.valueOf(String.valueOf(row)).concat(")");
/*     */     }
/* 332 */     if (col == 2) {
/* 333 */       if (row == 0) {
/* 334 */         if (this.this$0.controller.data.original_objective_is_max) {
/* 335 */           return "Z";
/*     */         }
/* 337 */         return "-Z";
/*     */       }
/*     */       
/* 340 */       return " ";
/*     */     }
/*     */     
/* 343 */     if ((col >= 3) && (col < this.this$0.controller.data.num_variable + 3)) {
/* 344 */       if (row == 0) {
/* 345 */         double mc = this.this$0.controller.data.objective_M_coefficient[(col - 2)];
/* 346 */         double ec = this.this$0.controller.data.objective_coefficient[(col - 2)];
/* 347 */         this.data[row][(col - 3)].setPara(mc, ec);
/* 348 */         if (this.this$0.controller.data.is_artificial[(col - 2)] != 0) {
/* 349 */           this.data[row][(col - 3)].setXstring("X".concat(String.valueOf(String.valueOf(col - 2))));
/*     */         } else
/* 351 */           this.data[row][(col - 3)].setXstring("x".concat(String.valueOf(String.valueOf(col - 2))));
/* 352 */         return this.data[row][(col - 3)];
/*     */       }
/*     */       
/* 355 */       this.data[row][(col - 3)].setPara(0.0D, this.this$0.controller.data.constrain_coefficient[row][(col - 2)]);
/* 356 */       if (this.this$0.controller.data.is_artificial[(col - 2)] != 0) {
/* 357 */         this.data[row][(col - 3)].setXstring("X".concat(String.valueOf(String.valueOf(col - 2))));
/*     */       } else
/* 359 */         this.data[row][(col - 3)].setXstring("x".concat(String.valueOf(String.valueOf(col - 2))));
/* 360 */       return this.data[row][(col - 3)];
/*     */     }
/*     */     
/*     */ 
/* 364 */     if (row == 0) {
/* 365 */       double mc = this.this$0.controller.data.objective_M_coefficient[0];
/* 366 */       double ec = this.this$0.controller.data.objective_coefficient[0];
/* 367 */       this.data[row][(col - 3)].setPara(mc, ec);
/* 368 */       this.data[row][(col - 3)].setXstring(" = ");
/* 369 */       return this.data[row][(col - 3)];
/*     */     }
/*     */     
/* 372 */     this.data[row][(col - 3)].setPara(0.0D, this.this$0.controller.data.constrain_coefficient[row][0]);
/* 373 */     this.data[row][(col - 3)].setXstring(" = ");
/* 374 */     return this.data[row][(col - 3)];
/*     */   }
/*     */   
/*     */ 
/*     */   public Class getColumnClass(int c)
/*     */   {
/* 380 */     return getValueAt(0, c).getClass();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isCellEditable(int row, int col)
/*     */   {
/* 386 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPArtificialPanel$IORAlgebraModel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */