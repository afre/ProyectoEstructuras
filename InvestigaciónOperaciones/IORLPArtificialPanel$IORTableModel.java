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
/*     */ class IORLPArtificialPanel$IORTableModel
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private int numVars;
/*     */   private String weirdstr;
/*     */   private final IORLPArtificialPanel this$0;
/*     */   
/*     */   public IORLPArtificialPanel$IORTableModel(IORLPArtificialPanel this$0)
/*     */   {
/* 200 */     this.this$0 = this$0;
/* 201 */     this.numVars = this$0.controller.data.num_variable;
/*     */   }
/*     */   
/*     */   public int getColumnCount() {
/* 205 */     return this.this$0.controller.data.num_variable + 4;
/*     */   }
/*     */   
/*     */   public int getRowCount() {
/* 209 */     return this.this$0.controller.data.num_constrain + 1;
/*     */   }
/*     */   
/*     */   public String getColumnName(int col) {
/* 213 */     if (col == 0)
/* 214 */       return "Bas Var";
/* 215 */     if (col == 1)
/* 216 */       return "Eq.";
/* 217 */     if (col == 2)
/* 218 */       return "Z";
/* 219 */     if ((col >= 3) && (col < 3 + this.numVars)) {
/* 220 */       if (this.this$0.controller.data.is_artificial[(col - 2)] != 0) {
/* 221 */         this.weirdstr = "X".concat(String.valueOf(String.valueOf(col - 2)));
/* 222 */         return this.weirdstr;
/*     */       }
/*     */       
/* 225 */       return "x".concat(String.valueOf(String.valueOf(col - 2)));
/*     */     }
/*     */     
/* 228 */     return "Right Side";
/*     */   }
/*     */   
/*     */   public Object getValueAt(int row, int col) {
/* 232 */     int xsub = 0;
/*     */     
/*     */ 
/* 235 */     if (col == 1)
/* 236 */       return Integer.toString(row);
/* 237 */     if (col == 2) {
/* 238 */       if ((row == 0) && (this.this$0.controller.data.objective_is_max))
/* 239 */         return "1";
/* 240 */       if (row == 0) {
/* 241 */         return "-1";
/*     */       }
/* 243 */       return "0";
/*     */     }
/* 245 */     if (col == 0) {
/* 246 */       if (row == 0) {
/* 247 */         return "Z";
/*     */       }
/* 249 */       xsub = this.this$0.controller.data.basic_variable_index[row];
/* 250 */       if (this.this$0.controller.data.is_artificial[xsub] != 0) {
/* 251 */         return "X".concat(String.valueOf(String.valueOf(xsub)));
/*     */       }
/* 253 */       return "x".concat(String.valueOf(String.valueOf(xsub)));
/*     */     }
/*     */     
/* 256 */     if ((col >= 3) && (col < this.numVars + 3)) {
/* 257 */       if (row == 0) {
/* 258 */         double mc = this.this$0.controller.data.objective_M_coefficient[(col - 2)];
/* 259 */         double ec = this.this$0.controller.data.objective_coefficient[(col - 2)];
/* 260 */         return new IORTableCell(mc, ec);
/*     */       }
/*     */       
/* 263 */       return new IORTableCell(this.this$0.controller.data.constrain_coefficient[row][(col - 2)]);
/*     */     }
/*     */     
/* 266 */     if (row == 0) {
/* 267 */       double mc = this.this$0.controller.data.objective_M_coefficient[0];
/* 268 */       double ec = this.this$0.controller.data.objective_coefficient[0];
/* 269 */       return new IORTableCell(mc, ec);
/*     */     }
/*     */     
/* 272 */     return new IORTableCell(this.this$0.controller.data.constrain_coefficient[row][0]);
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int c)
/*     */   {
/* 277 */     return getValueAt(0, c).getClass();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isCellEditable(int row, int col)
/*     */   {
/* 283 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPArtificialPanel$IORTableModel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */