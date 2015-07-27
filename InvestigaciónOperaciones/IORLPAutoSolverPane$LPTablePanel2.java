/*     */ import java.awt.Dimension;
/*     */ import java.text.DecimalFormat;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
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
/*     */ public class IORLPAutoSolverPane$LPTablePanel2
/*     */   extends JPanel
/*     */ {
/*     */   private int tableRow1;
/*     */   private int tableCol1;
/*     */   private IORLPAutoSolverPane.LPTablePanel2.LPTableModel1 LPtable1;
/*     */   private JTable table1;
/*     */   private JScrollPane canPanel;
/*     */   private final IORLPAutoSolverPane this$0;
/*     */   
/*     */   IORLPAutoSolverPane$LPTablePanel2(IORLPAutoSolverPane this$0)
/*     */   {
/* 289 */     this.this$0 = this$0;this.tableRow1 = 2;this.tableCol1 = 3;this.table1 = null;this.canPanel = null;
/* 290 */     this$0.tablePane1 = new JPanel();
/* 291 */     this.LPtable1 = new IORLPAutoSolverPane.LPTablePanel2.LPTableModel1();
/* 292 */     this.table1 = new JTable(this.LPtable1);
/* 293 */     this.table1.setCellSelectionEnabled(true);
/* 294 */     this.table1.setSelectionMode(1);
/* 295 */     this.table1.setEnabled(false);
/* 296 */     this.canPanel = new JScrollPane(this.table1);
/* 297 */     this.canPanel.setPreferredSize(new Dimension(270, 150));
/* 298 */     setPreferredSize(new Dimension(290, 160));
/* 299 */     add(this.canPanel); }
/*     */   
/*     */   public class LPTableModel1 extends AbstractTableModel { private int i;
/*     */     private int j;
/*     */     
/*     */     public LPTableModel1() {}
/*     */     
/* 306 */     public int getColumnCount() { return IORLPAutoSolverPane.LPTablePanel2.this.tableCol1; }
/*     */     
/*     */ 
/*     */     public int getRowCount()
/*     */     {
/* 311 */       return IORLPAutoSolverPane.LPTablePanel2.this.tableRow1;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col)
/*     */     {
/* 316 */       String str = "";
/* 317 */       if (col == 0) {
/* 318 */         str = String.valueOf(String.valueOf(str)).concat("Current Value");
/* 319 */       } else if (col == 1) {
/* 320 */         str = String.valueOf(String.valueOf(str)).concat("Minimum");
/* 321 */       } else if (col == 2)
/* 322 */         str = String.valueOf(String.valueOf(str)).concat("Maximum");
/* 323 */       return str;
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 328 */       String str = "";
/* 329 */       int r = -1;int c = -1;
/*     */       
/* 331 */       if (col == 0) {
/* 332 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.access$14(IORLPAutoSolverPane.LPTablePanel2.this.this$0).format(IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel2.this.this$0).data.coefficientsArr[(row + 1)]))));
/* 333 */       } else if (col == 1) {
/* 334 */         if (IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel2.this.this$0).data.minArr[(row + 1)] > Integer.MAX_VALUE) {
/* 335 */           str = String.valueOf(String.valueOf(str)).concat("infin");
/* 336 */         } else if (IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel2.this.this$0).data.minArr[(row + 1)] < Integer.MIN_VALUE) {
/* 337 */           str = String.valueOf(String.valueOf(str)).concat("-infin");
/*     */         } else {
/* 339 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.access$14(IORLPAutoSolverPane.LPTablePanel2.this.this$0).format(IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel2.this.this$0).data.minArr[(row + 1)]))));
/*     */         }
/* 341 */       } else if (col == 2) {
/* 342 */         if (IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel2.this.this$0).data.maxArr[(row + 1)] > Integer.MAX_VALUE) {
/* 343 */           str = String.valueOf(String.valueOf(str)).concat("infin");
/* 344 */         } else if (IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel2.this.this$0).data.maxArr[(row + 1)] < Integer.MIN_VALUE) {
/* 345 */           str = String.valueOf(String.valueOf(str)).concat("-infin");
/*     */         } else {
/* 347 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.access$14(IORLPAutoSolverPane.LPTablePanel2.this.this$0).format(IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel2.this.this$0).data.maxArr[(row + 1)]))));
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 352 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c)
/*     */     {
/* 357 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int col) {
/* 361 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPAutoSolverPane$LPTablePanel2.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */