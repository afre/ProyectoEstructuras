/*     */ import java.awt.Dimension;
/*     */ import javax.swing.BorderFactory;
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
/*     */ public class IORTSPTabuSearchPanel$IORTSPTabuTablePanel
/*     */   extends JPanel
/*     */ {
/*     */   private JTable table;
/*     */   private int tableRow;
/*     */   private int tableCol;
/*     */   private IORTSPTabuSearchPanel.IORTSPTabuTablePanel.TSPTableModel1 tspModel1;
/*     */   private JScrollPane canPanel;
/*     */   private final IORTSPTabuSearchPanel this$0;
/*     */   
/*     */   IORTSPTabuSearchPanel$IORTSPTabuTablePanel(IORTSPTabuSearchPanel this$0)
/*     */   {
/* 281 */     this.this$0 = this$0;this.table = null;this.tspModel1 = null;this.canPanel = null;
/* 282 */     this.tableRow = this$0.controller.data.num_city;
/* 283 */     this.tableCol = (this$0.controller.data.num_city + 1);
/* 284 */     this.tspModel1 = new IORTSPTabuSearchPanel.IORTSPTabuTablePanel.TSPTableModel1(null);
/* 285 */     this.table = new JTable(this.tspModel1);
/* 286 */     this.table.setCellSelectionEnabled(true);
/* 287 */     this.table.setSelectionMode(0);
/* 288 */     this.table.setEnabled(false);
/*     */     
/*     */ 
/* 291 */     this.canPanel = new JScrollPane(this.table);
/* 292 */     this.canPanel.setPreferredSize(new Dimension(600, 190));
/* 293 */     this.canPanel.setMaximumSize(new Dimension(480, 190));
/* 294 */     this.canPanel.setAlignmentX(0.5F);
/* 295 */     this.canPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
/*     */     
/* 297 */     add(this.canPanel); }
/*     */   
/*     */   private class TSPTableModel1 extends AbstractTableModel { private int i;
/*     */     
/* 301 */     TSPTableModel1(IORTSPTabuSearchPanel..6 x$1) { this(); }
/*     */     
/*     */ 
/*     */     private int j;
/*     */     public int getColumnCount()
/*     */     {
/* 307 */       return IORTSPTabuSearchPanel.IORTSPTabuTablePanel.this.tableCol;
/*     */     }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 312 */       return IORTSPTabuSearchPanel.IORTSPTabuTablePanel.this.tableRow;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col)
/*     */     {
/* 317 */       String str = "";
/* 318 */       if (col == 0) {
/* 319 */         str = String.valueOf(String.valueOf(str)).concat("City");
/*     */       } else
/* 321 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(col)));
/* 322 */       return str;
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 327 */       String str = "";
/* 328 */       int r = -1;int c = -1;
/* 329 */       if (col == 0) {
/* 330 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(row + 1)));
/*     */       }
/* 332 */       else if (col - 1 < row) {
/* 333 */         str = "";
/*     */       }
/*     */       else {
/* 336 */         r = row;
/* 337 */         c = col - 1;
/* 338 */         if (r != IORTSPTabuSearchPanel.IORTSPTabuTablePanel.this.this$0.controller.data.num_city) {
/* 339 */           if (IORTSPTabuSearchPanel.IORTSPTabuTablePanel.this.this$0.controller.data.matrix[r][c] == Integer.MAX_VALUE) {
/* 340 */             str = "-----";
/*     */           }
/* 342 */           else if (r == c) {
/* 343 */             str = "";
/*     */           }
/*     */           else {
/* 346 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(IORTSPTabuSearchPanel.IORTSPTabuTablePanel.this.this$0.controller.data.matrix[r][c]))))));
/*     */           }
/*     */         }
/*     */       }
/* 350 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c)
/*     */     {
/* 355 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int col) {
/* 359 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */     public void setValueAt(Object value, int row, int col)
/*     */     {
/* 365 */       String input = (String)value;
/* 366 */       fireTableCellUpdated(row, col - 1);
/*     */     }
/*     */     
/*     */     private TSPTableModel1() {}
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTSPTabuSearchPanel$IORTSPTabuTablePanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */