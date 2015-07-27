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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IORSimulateAnealingPanel$IORTSPSimulateAnealingCanvasPanel$TSPTableModel
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private int i;
/*     */   private int j;
/*     */   private final IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel this$1;
/*     */   
/* 752 */   private IORSimulateAnealingPanel$IORTSPSimulateAnealingCanvasPanel$TSPTableModel(IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel this$1) { this.this$1 = this$1; } IORSimulateAnealingPanel$IORTSPSimulateAnealingCanvasPanel$TSPTableModel(IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel x$0, IORSimulateAnealingPanel..8 x$1) { this(x$0); }
/*     */   
/*     */ 
/*     */   public int getColumnCount()
/*     */   {
/* 757 */     return this.this$1.tableCol;
/*     */   }
/*     */   
/*     */   public int getRowCount()
/*     */   {
/* 762 */     return this.this$1.tableRow;
/*     */   }
/*     */   
/*     */   public String getColumnName(int col)
/*     */   {
/* 767 */     String str = "";
/* 768 */     if (col == 0) {
/* 769 */       str = String.valueOf(String.valueOf(str)).concat("Iteration");
/* 770 */     } else if (col == 1) {
/* 771 */       str = String.valueOf(String.valueOf(str)).concat("T");
/* 772 */     } else if (col == 2) {
/* 773 */       str = String.valueOf(String.valueOf(str)).concat("Trial Solution");
/* 774 */     } else if (col == 3) {
/* 775 */       str = String.valueOf(String.valueOf(str)).concat("Distance");
/* 776 */     } else if (col == 4) {
/* 777 */       str = String.valueOf(String.valueOf(str)).concat("Acceptance Prob.");
/*     */     } else
/* 779 */       str = String.valueOf(String.valueOf(str)).concat("Accept");
/* 780 */     return str;
/*     */   }
/*     */   
/*     */   public Object getValueAt(int row, int col)
/*     */   {
/* 785 */     String str = "";
/* 786 */     int currentIteration = 1;
/* 787 */     int r = -1;int c = -1;
/* 788 */     if ((col == 0) && (row == 0)) {
/* 789 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(0)));
/*     */     }
/* 791 */     else if (col == 0) {
/* 792 */       if (row >= 2) {
/* 793 */         currentIteration = Integer.valueOf((String)getValueAt(row - 1, col)).intValue();
/* 794 */         String accept = (String)IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$11(this.this$1).controller.data.auto_Accept_Vec.elementAt(row - 2);
/* 795 */         if (accept.equalsIgnoreCase("true")) {
/* 796 */           currentIteration++;
/* 797 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(currentIteration)));
/*     */         } else {
/* 799 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(currentIteration)));
/*     */         }
/*     */       } else {
/* 802 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(currentIteration)));
/*     */       }
/*     */     }
/* 805 */     else if (col == 1) {
/* 806 */       if (row == 0) {
/* 807 */         str = String.valueOf(String.valueOf(str)).concat("");
/*     */       }
/* 809 */       else if (IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$11(this.this$1).controller.data.auto_T_Vec.size() != 0) {
/* 810 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((String)IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$11(this.this$1).controller.data.auto_T_Vec.elementAt(row - 1))));
/*     */       }
/*     */     }
/* 813 */     else if (col == 2) {
/* 814 */       if (row == 0) {
/* 815 */         int[] solution = IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$11(this.this$1).controller.data.initSolution;
/* 816 */         String s = "";
/* 817 */         for (int i = 0; i < solution.length; i++) {
/* 818 */           if (i != solution.length - 1) {
/* 819 */             s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1).append("-"))))));
/*     */           } else {
/* 821 */             s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1))))));
/*     */           }
/*     */         }
/* 824 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(s)));
/* 825 */         str = String.valueOf(String.valueOf(str)).concat("-1");
/*     */       }
/* 827 */       else if (IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$11(this.this$1).controller.data.auto_Solution_Vec.size() != 0) {
/* 828 */         int[] solution = (int[])IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$11(this.this$1).controller.data.auto_Solution_Vec.elementAt(row - 1);
/* 829 */         String s = "";
/* 830 */         for (int i = 0; i < solution.length; i++) {
/* 831 */           if (i != solution.length - 1) {
/* 832 */             s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1).append("-"))))));
/*     */           } else {
/* 834 */             s = String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("").append(solution[i] + 1))))));
/*     */           }
/*     */         }
/* 837 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(s)));
/* 838 */         str = String.valueOf(String.valueOf(str)).concat("-1");
/*     */       }
/*     */     }
/* 841 */     else if (col == 3) {
/* 842 */       if (row == 0) {
/* 843 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$11(this.this$1).controller.data.initDistance)));
/*     */       }
/* 845 */       else if (IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$11(this.this$1).controller.data.auto_Distance_Vec.size() != 0) {
/* 846 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((String)IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$11(this.this$1).controller.data.auto_Distance_Vec.elementAt(row - 1))));
/*     */       }
/*     */     }
/* 849 */     else if (col == 4)
/*     */     {
/* 851 */       if (row == 0) {
/* 852 */         str = String.valueOf(String.valueOf(str)).concat("");
/*     */       }
/* 854 */       else if (IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$11(this.this$1).controller.data.auto_Probability_Vec.size() != 0) {
/* 855 */         double tempD = Double.valueOf((String)IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$11(this.this$1).controller.data.auto_Probability_Vec.elementAt(row - 1)).doubleValue();
/* 856 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(IORSimulateAnealingPanel.access$15(IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$11(this.this$1)).format(tempD)))))));
/*     */       }
/*     */     } else { double tempD;
/* 859 */       if (col == 5) {
/* 860 */         if (row == 0) {
/* 861 */           str = String.valueOf(String.valueOf(str)).concat("");
/*     */         }
/* 863 */         else if (IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$11(this.this$1).controller.data.auto_Probability_Vec.size() != 0) {
/* 864 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((String)IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$11(this.this$1).controller.data.auto_Accept_Vec.elementAt(row - 1))));
/*     */         }
/*     */       }
/*     */     }
/* 868 */     return str;
/*     */   }
/*     */   
/*     */   public Class getColumnClass(int c) {
/* 872 */     return getValueAt(0, c).getClass();
/*     */   }
/*     */   
/*     */   public boolean isCellEditable(int row, int col) {
/* 876 */     if (!IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$16(this.this$1))
/* 877 */       return false;
/* 878 */     if (col == 0) {
/* 879 */       return false;
/*     */     }
/* 881 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValueAt(Object value, int row, int col)
/*     */   {
/* 889 */     String input = (String)value;
/*     */     
/* 891 */     if ((col != 0) && (row != col - 1)) {
/*     */       try {
/* 893 */         IORSimulateAnealingPanel.IORTSPSimulateAnealingCanvasPanel.access$17(this.this$1);int[] d = IORTSPActionPanel.parseStringInput(input);
/* 894 */         if (d == null) {
/* 895 */           return;
/*     */         }
/*     */         
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 901 */         return;
/*     */       }
/*     */       
/* 904 */       fireTableCellUpdated(row, col - 1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORSimulateAnealingPanel$IORTSPSimulateAnealingCanvasPanel$TSPTableModel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */