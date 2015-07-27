/*     */ import java.awt.Dimension;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Vector;
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
/*     */ public class IORLPAutoSolverPane$LPTablePanel1
/*     */   extends JPanel
/*     */ {
/*     */   private int tableRow1;
/*     */   private int tableCol1;
/*     */   private IORLPAutoSolverPane.LPTablePanel1.LPTableModel1 LPtable1;
/*     */   private JTable table1;
/*     */   private JScrollPane canPanel;
/*     */   private final IORLPAutoSolverPane this$0;
/*     */   
/*     */   IORLPAutoSolverPane$LPTablePanel1(IORLPAutoSolverPane this$0)
/*     */   {
/* 219 */     this.this$0 = this$0;this.tableRow1 = 2;this.tableCol1 = 2;this.table1 = null;this.canPanel = null;
/* 220 */     this$0.tablePane1 = new JPanel();
/* 221 */     this.LPtable1 = new IORLPAutoSolverPane.LPTablePanel1.LPTableModel1();
/* 222 */     this.table1 = new JTable(this.LPtable1);
/* 223 */     this.table1.setCellSelectionEnabled(true);
/* 224 */     this.table1.setSelectionMode(1);
/* 225 */     this.table1.setEnabled(false);
/* 226 */     this.canPanel = new JScrollPane(this.table1);
/* 227 */     this.canPanel.setPreferredSize(new Dimension(155, 150));
/* 228 */     setPreferredSize(new Dimension(155, 160));
/* 229 */     add(this.canPanel);
/*     */   }
/*     */   
/*     */   public class LPTableModel1 extends AbstractTableModel { private int i;
/*     */     private int j;
/*     */     
/*     */     public LPTableModel1() {}
/*     */     
/* 237 */     public int getColumnCount() { return IORLPAutoSolverPane.LPTablePanel1.this.tableCol1; }
/*     */     
/*     */ 
/*     */     public int getRowCount()
/*     */     {
/* 242 */       return IORLPAutoSolverPane.LPTablePanel1.this.tableRow1;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col)
/*     */     {
/* 247 */       String str = "";
/*     */       
/* 249 */       if (col == 0) {
/* 250 */         str = String.valueOf(String.valueOf(str)).concat("Variable");
/* 251 */       } else if (col == 1) {
/* 252 */         str = String.valueOf(String.valueOf(str)).concat("Value");
/*     */       }
/* 254 */       return str;
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 259 */       String str = "";
/* 260 */       int r = -1;int c = -1;
/* 261 */       if (col == 0) {
/* 262 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("X").append(row + 1))))));
/* 263 */       } else if (col == 1) {
/* 264 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORLPAutoSolverPane.access$14(IORLPAutoSolverPane.LPTablePanel1.this.this$0).format((Double)IORLPAutoSolverPane.access$15(IORLPAutoSolverPane.LPTablePanel1.this.this$0).data.variableValeV.elementAt(row)))));
/*     */       }
/*     */       
/* 267 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c)
/*     */     {
/* 272 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int col) {
/* 276 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPAutoSolverPane$LPTablePanel1.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */