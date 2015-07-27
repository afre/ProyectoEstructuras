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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORLPAutoSolverPane$LPTablePanel4
/*     */   extends JPanel
/*     */ {
/*     */   private int tableRow1;
/*     */   private int tableCol1;
/*     */   private IORLPAutoSolverPane.LPTablePanel4.LPTableModel1 LPtable1;
/*     */   private JTable table1;
/*     */   private JScrollPane canPanel;
/*     */   private final IORLPAutoSolverPane this$0;
/*     */   
/*     */   IORLPAutoSolverPane$LPTablePanel4(IORLPAutoSolverPane this$0)
/*     */   {
/* 449 */     this.this$0 = this$0;this.tableRow1 = 2;this.tableCol1 = 3;this.table1 = null;this.canPanel = null;
/* 450 */     this$0.tablePane1 = new JPanel();
/* 451 */     this.LPtable1 = new IORLPAutoSolverPane.LPTablePanel4.LPTableModel1();
/* 452 */     this.table1 = new JTable(this.LPtable1);
/* 453 */     this.table1.setCellSelectionEnabled(true);
/* 454 */     this.table1.setSelectionMode(1);
/* 455 */     this.table1.setEnabled(false);
/* 456 */     this.canPanel = new JScrollPane(this.table1);
/* 457 */     this.canPanel.setPreferredSize(new Dimension(290, 150));
/* 458 */     setPreferredSize(new Dimension(290, 160));
/* 459 */     add(this.canPanel);
/*     */   }
/*     */   
/*     */   public class LPTableModel1 extends AbstractTableModel { private int i;
/*     */     private int j;
/*     */     
/*     */     public LPTableModel1() {}
/*     */     
/* 467 */     public int getColumnCount() { return IORLPAutoSolverPane.LPTablePanel4.this.tableCol1; }
/*     */     
/*     */ 
/*     */     public int getRowCount()
/*     */     {
/* 472 */       return IORLPAutoSolverPane.LPTablePanel4.this.tableRow1;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col)
/*     */     {
/* 477 */       String str = "";
/* 478 */       if (col == 0) {
/* 479 */         str = String.valueOf(String.valueOf(str)).concat("Current Value");
/* 480 */       } else if (col == 1) {
/* 481 */         str = String.valueOf(String.valueOf(str)).concat("Minimum");
/* 482 */       } else if (col == 2)
/* 483 */         str = String.valueOf(String.valueOf(str)).concat("Maximum");
/* 484 */       return str;
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 489 */       String str = "";
/* 490 */       int r = -1;int c = -1;
/* 491 */       if (col == 0) {
/* 492 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.access$14(IORLPAutoSolverPane.LPTablePanel4.this.this$0).format(IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel4.this.this$0).data.RHSCurrentValueArr[(row + 1)]))));
/* 493 */       } else if (col == 1) {
/* 494 */         if (IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel4.this.this$0).data.minRHSArr[(row + 1)] > Integer.MAX_VALUE) {
/* 495 */           str = String.valueOf(String.valueOf(str)).concat("infin");
/* 496 */         } else if (IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel4.this.this$0).data.minRHSArr[(row + 1)] < Integer.MIN_VALUE) {
/* 497 */           str = String.valueOf(String.valueOf(str)).concat("-infin");
/*     */         } else {
/* 499 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.access$14(IORLPAutoSolverPane.LPTablePanel4.this.this$0).format(IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel4.this.this$0).data.minRHSArr[(row + 1)]))));
/*     */         }
/*     */       }
/* 502 */       else if (col == 2) {
/* 503 */         if (IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel4.this.this$0).data.maxRHSArr[(row + 1)] > Integer.MAX_VALUE) {
/* 504 */           str = String.valueOf(String.valueOf(str)).concat("infin");
/* 505 */         } else if (IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel4.this.this$0).data.maxRHSArr[(row + 1)] < Integer.MIN_VALUE) {
/* 506 */           str = String.valueOf(String.valueOf(str)).concat("-infin");
/*     */         } else {
/* 508 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.access$14(IORLPAutoSolverPane.LPTablePanel4.this.this$0).format(IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel4.this.this$0).data.maxRHSArr[(row + 1)]))));
/*     */         }
/*     */       }
/*     */       
/* 512 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c)
/*     */     {
/* 517 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int col) {
/* 521 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPAutoSolverPane$LPTablePanel4.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */