/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Vector;
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
/*     */ public class IORLPAutoSolverPane$LPTablePanel1$LPTableModel1
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private int i;
/*     */   private int j;
/*     */   private final IORLPAutoSolverPane.LPTablePanel1 this$1;
/*     */   
/*     */   public IORLPAutoSolverPane$LPTablePanel1$LPTableModel1(IORLPAutoSolverPane.LPTablePanel1 this$1)
/*     */   {
/* 233 */     this.this$1 = this$1;
/*     */   }
/*     */   
/*     */   public int getColumnCount() {
/* 237 */     return IORLPAutoSolverPane.LPTablePanel1.access$12(this.this$1);
/*     */   }
/*     */   
/*     */   public int getRowCount()
/*     */   {
/* 242 */     return IORLPAutoSolverPane.LPTablePanel1.access$11(this.this$1);
/*     */   }
/*     */   
/*     */   public String getColumnName(int col)
/*     */   {
/* 247 */     String str = "";
/*     */     
/* 249 */     if (col == 0) {
/* 250 */       str = String.valueOf(String.valueOf(str)).concat("Variable");
/* 251 */     } else if (col == 1) {
/* 252 */       str = String.valueOf(String.valueOf(str)).concat("Value");
/*     */     }
/* 254 */     return str;
/*     */   }
/*     */   
/*     */   public Object getValueAt(int row, int col)
/*     */   {
/* 259 */     String str = "";
/* 260 */     int r = -1;int c = -1;
/* 261 */     if (col == 0) {
/* 262 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("X").append(row + 1))))));
/* 263 */     } else if (col == 1) {
/* 264 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.access$14(IORLPAutoSolverPane.LPTablePanel1.access$13(this.this$1)).format((Double)IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel1.access$13(this.this$1)).data.variableValeV.elementAt(row)))));
/*     */     }
/*     */     
/* 267 */     return str;
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int c)
/*     */   {
/* 272 */     return getValueAt(0, c).getClass();
/*     */   }
/*     */   
/*     */   public boolean isCellEditable(int row, int col) {
/* 276 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPAutoSolverPane$LPTablePanel1$LPTableModel1.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */