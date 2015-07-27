/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.TableCellEditor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORTPEnterPanel
/*     */   extends IORTPActionPanel
/*     */ {
/*     */   private int num_supply;
/*     */   private int num_demand;
/*  42 */   private WholeNumberField wnf_num_supply = null;
/*     */   
/*     */ 
/*  45 */   private WholeNumberField wnf_num_demand = null;
/*     */   
/*  47 */   private JPanel mainPanel = null;
/*  48 */   private JPanel dataPanel = null;
/*     */   
/*  50 */   private IORTPEnterPanel.TPTableModel tpModel = null;
/*  51 */   private JTable table = null;
/*  52 */   private JScrollPane scrlPane = null;
/*     */   
/*     */ 
/*  55 */   private double[][] matrix = null;
/*  56 */   private double[][] matrix_M = null;
/*     */   
/*     */ 
/*  59 */   private int[] supply_column = null;
/*     */   
/*     */ 
/*  62 */   private int[] demand_row = null;
/*     */   
/*     */ 
/*  65 */   private boolean is_panel_enabled = true;
/*     */   
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */ 
/*     */   private int getMaxNumSupply()
/*     */   {
/*  73 */     return this.controller.data.max_num_supply;
/*     */   }
/*     */   
/*     */ 
/*     */   private int getMaxNumDemand()
/*     */   {
/*  79 */     return this.controller.data.max_num_demand;
/*     */   }
/*     */   
/*     */ 
/*     */   private void initializeData()
/*     */   {
/*  85 */     int max_supply = getMaxNumSupply();
/*  86 */     int max_demand = getMaxNumDemand();
/*     */     
/*  88 */     this.matrix = new double[max_supply][max_demand];
/*  89 */     this.matrix_M = new double[max_supply][max_demand];
/*  90 */     this.demand_row = new int[max_demand];
/*  91 */     this.supply_column = new int[max_supply];
/*     */     
/*     */ 
/*     */ 
/*  95 */     for (int i = 0; i < max_supply; i++) {
/*  96 */       this.supply_column[i] = this.controller.data.supply[i];
/*     */     }
/*     */     
/*  99 */     for (int j = 0; j < max_demand; j++) {
/* 100 */       this.demand_row[j] = this.controller.data.demand[j];
/*     */     }
/*     */     
/* 103 */     for (i = 0; i < max_supply; i++) {
/* 104 */       for (j = 0; j < max_demand; j++) {
/* 105 */         this.matrix[i][j] = this.controller.data.cost[i][j];
/* 106 */         this.matrix_M[i][j] = this.controller.data.M_cost[i][j];
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void setNumSupply()
/*     */   {
/* 115 */     int max = getMaxNumSupply();
/* 116 */     if (this.wnf_num_supply.getValue() > max) {
/* 117 */       this.wnf_num_supply.setValue(max);
/*     */     }
/*     */     
/* 120 */     int last = this.num_supply;
/*     */     
/* 122 */     int s = this.wnf_num_supply.getValue();
/* 123 */     if (s <= max) {
/* 124 */       this.num_supply = s;
/*     */     }
/* 126 */     if (last != this.num_supply) {
/* 127 */       this.wnf_num_supply.setValue(this.num_supply);
/* 128 */       repaint();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void setNumDemand()
/*     */   {
/* 135 */     int max = getMaxNumDemand();
/* 136 */     if (this.wnf_num_demand.getValue() > max) {
/* 137 */       this.wnf_num_demand.setValue(max);
/*     */     }
/*     */     
/* 140 */     int last = this.num_demand;
/* 141 */     int d = this.wnf_num_demand.getValue();
/* 142 */     if (d <= max) {
/* 143 */       this.num_demand = d;
/*     */     }
/* 145 */     if (last != this.num_demand) {
/* 146 */       this.wnf_num_demand.setValue(this.num_demand);
/* 147 */       repaint();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updatePanel() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPanelEnable(boolean b)
/*     */   {
/* 160 */     this.wnf_num_demand.setEditable(b);
/* 161 */     this.wnf_num_supply.setEditable(b);
/* 162 */     this.is_panel_enabled = b;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finishProcedure()
/*     */   {
/* 170 */     if (this.table.getEditorComponent() != null) {
/* 171 */       this.table.getDefaultEditor(String.class).stopCellEditing();
/* 172 */       this.table.clearSelection();
/*     */     }
/*     */     
/* 175 */     Vector p = new Vector();
/*     */     
/* 177 */     p.addElement(new Integer(this.num_supply));
/*     */     
/* 179 */     p.addElement(new Integer(this.num_demand));
/*     */     
/* 181 */     p.addElement(this.matrix);
/*     */     
/* 183 */     p.addElement(this.matrix_M);
/*     */     
/* 185 */     p.addElement(this.supply_column);
/*     */     
/* 187 */     p.addElement(this.demand_row);
/*     */     
/* 189 */     IOROperation operation = new IOROperation(20101, p);
/*     */     
/*     */ 
/* 192 */     this.controller.solver.doWork(operation);
/* 193 */     this.controller.setMenuState(10);
/*     */     
/* 195 */     this.actionStatus.setText("The current procedure is finished. Please go to the Procedure menu to continue!");
/* 196 */     this.actionStatus.setVisible(true);
/*     */     
/* 198 */     setPanelEnable(false);
/*     */     
/* 200 */     setFinishEnabled(false);
/* 201 */     this.controller.solver.setBookmark();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORTPEnterPanel(IORTPProcessController c)
/*     */   {
/* 212 */     super(c);
/* 213 */     this.bn_back.setVisible(false);
/*     */     
/* 215 */     this.mainPanel = new JPanel();
/* 216 */     add(this.mainPanel, "Center");
/*     */     
/*     */ 
/* 219 */     this.num_supply = this.controller.data.num_supply;
/* 220 */     IORWholeNumberPanel wnp_num_supply = new IORWholeNumberPanel(this.num_supply, 2, "Number of Sources (<= 7):      ");
/*     */     
/*     */ 
/* 223 */     this.wnf_num_supply = wnp_num_supply.getWholeNumberField();
/* 224 */     this.wnf_num_supply.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 227 */         int column = IORTPEnterPanel.this.table.getSelectedColumn();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 232 */         IORTPEnterPanel.this.setNumSupply();
/*     */         
/* 234 */         IORTPEnterPanel.this.tpModel.fireTableStructureChanged();
/* 235 */         IORTPEnterPanel.this.table.sizeColumnsToFit(-1);
/* 236 */         IORTPEnterPanel.this.revalidate();
/* 237 */         IORTPEnterPanel.this.repaint();
/*     */       }
/*     */       
/* 240 */     });
/* 241 */     this.num_demand = this.controller.data.num_demand;
/* 242 */     IORWholeNumberPanel wnp_num_demand = new IORWholeNumberPanel(this.num_demand, 2, "Number of Destinations (<= 7): ");
/*     */     
/*     */ 
/* 245 */     this.wnf_num_demand = wnp_num_demand.getWholeNumberField();
/* 246 */     this.wnf_num_demand.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 248 */         int column = IORTPEnterPanel.this.table.getSelectedColumn();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 253 */         IORTPEnterPanel.this.setNumDemand();
/*     */         
/* 255 */         IORTPEnterPanel.this.tpModel.fireTableStructureChanged();
/* 256 */         IORTPEnterPanel.this.table.sizeColumnsToFit(-1);
/* 257 */         IORTPEnterPanel.this.revalidate();
/* 258 */         IORTPEnterPanel.this.repaint();
/*     */       }
/*     */       
/* 261 */     });
/* 262 */     initializeData();
/*     */     
/* 264 */     this.tpModel = new IORTPEnterPanel.TPTableModel(null);
/* 265 */     this.table = new JTable(this.tpModel);
/* 266 */     this.table.setCellSelectionEnabled(true);
/*     */     
/* 268 */     this.scrlPane = new JScrollPane(this.table);
/* 269 */     this.scrlPane.setPreferredSize(new Dimension(400, 160));
/* 270 */     this.scrlPane.setMaximumSize(new Dimension(500, 200));
/* 271 */     this.scrlPane.setAlignmentX(0.5F);
/* 272 */     this.scrlPane.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
/*     */     
/*     */ 
/* 275 */     JPanel dimensionPanel = new JPanel();
/* 276 */     dimensionPanel.setLayout(new BoxLayout(dimensionPanel, 1));
/* 277 */     dimensionPanel.add(wnp_num_supply);
/* 278 */     dimensionPanel.add(wnp_num_demand);
/* 279 */     JLabel lb1 = new JLabel("(To make a new number above take effect, you must press the ENTER key.)");
/* 280 */     dimensionPanel.add(Box.createRigidArea(new Dimension(15, 10)));
/* 281 */     dimensionPanel.add(lb1);
/* 282 */     dimensionPanel.add(Box.createRigidArea(new Dimension(15, 10)));
/* 283 */     dimensionPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 30, 15));
/*     */     
/*     */ 
/* 286 */     this.dataPanel = new JPanel();
/* 287 */     this.dataPanel.setLayout(new BorderLayout());
/* 288 */     this.dataPanel.add(this.scrlPane);
/*     */     
/* 290 */     this.mainPanel.setLayout(new BorderLayout());
/* 291 */     this.mainPanel.add(dimensionPanel, "North");
/* 292 */     this.mainPanel.add(Box.createRigidArea(new Dimension(15, 15)), "East");
/* 293 */     this.mainPanel.add(Box.createRigidArea(new Dimension(15, 15)), "West");
/* 294 */     this.mainPanel.add(this.dataPanel, "Center"); }
/*     */   
/*     */   private class TPTableModel extends AbstractTableModel { private int i;
/*     */     
/* 298 */     TPTableModel(IORTPEnterPanel..3 x$1) { this(); }
/*     */     
/*     */     private int j;
/*     */     public int getColumnCount()
/*     */     {
/* 303 */       return IORTPEnterPanel.this.num_demand + 2;
/*     */     }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 308 */       return IORTPEnterPanel.this.num_supply + 1;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col)
/*     */     {
/* 313 */       String str = "";
/* 314 */       if (col == 0) {
/* 315 */         str = String.valueOf(String.valueOf(str)).concat("Source");
/* 316 */       } else if (col == IORTPEnterPanel.this.num_demand + 1) {
/* 317 */         str = String.valueOf(String.valueOf(str)).concat("Supply");
/*     */       }
/*     */       else {
/* 320 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(col)));
/*     */       }
/* 322 */       return str;
/*     */     }
/*     */     
/*     */ 
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 328 */       String str = "";
/* 329 */       int r = -1;int c = -1;
/*     */       
/* 331 */       if ((col == 0) && (row == IORTPEnterPanel.this.num_supply)) {
/* 332 */         str = String.valueOf(String.valueOf(str)).concat("Demand");
/*     */ 
/*     */       }
/* 335 */       else if ((col != IORTPEnterPanel.this.num_demand + 1) || (row != IORTPEnterPanel.this.num_supply))
/*     */       {
/*     */ 
/* 338 */         if (col == 0) {
/* 339 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(row + 1)));
/*     */ 
/*     */         }
/* 342 */         else if ((row == IORTPEnterPanel.this.num_supply) && (col >= 1)) {
/* 343 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTPEnterPanel.this.demand_row[(col - 1)])));
/*     */ 
/*     */         }
/* 346 */         else if (col == IORTPEnterPanel.this.num_demand + 1) {
/* 347 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTPEnterPanel.this.supply_column[row])));
/*     */         }
/*     */         else
/*     */         {
/* 351 */           r = row;
/* 352 */           c = col - 1;
/* 353 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTPActionPanel.getCellString(IORTPEnterPanel.this.matrix[r][c], IORTPEnterPanel.this.matrix_M[r][c]))));
/*     */         }
/*     */       }
/* 356 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c)
/*     */     {
/* 361 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int col)
/*     */     {
/* 366 */       if (!IORTPEnterPanel.this.is_panel_enabled) {
/* 367 */         return false;
/*     */       }
/* 369 */       if (col == 0)
/* 370 */         return false;
/* 371 */       if ((col == IORTPEnterPanel.this.num_demand + 1) && (row == IORTPEnterPanel.this.num_supply)) {
/* 372 */         return false;
/*     */       }
/* 374 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */     public void setValueAt(Object value, int row, int col)
/*     */     {
/* 380 */       String input = (String)value;
/*     */       
/* 382 */       if ((row == IORTPEnterPanel.this.num_supply) || (col == IORTPEnterPanel.this.num_demand + 1)) {
/*     */         try {
/* 384 */           int r = Integer.parseInt(input);
/* 385 */           if (row == IORTPEnterPanel.this.num_supply) {
/* 386 */             IORTPEnterPanel.this.demand_row[(col - 1)] = r;
/*     */           } else {
/* 388 */             IORTPEnterPanel.this.supply_column[row] = r;
/*     */           }
/*     */         } catch (NumberFormatException e) {
/* 391 */           return;
/*     */         }
/*     */       }
/*     */       else {
/* 395 */         double[] d = IORTPActionPanel.parseStringInput(input);
/* 396 */         IORTPEnterPanel.this.matrix[row][(col - 1)] = d[0];
/* 397 */         IORTPEnterPanel.this.matrix_M[row][(col - 1)] = d[1];
/*     */       }
/*     */       
/* 400 */       fireTableCellUpdated(row, col);
/*     */     }
/*     */     
/*     */     private TPTableModel() {}
/*     */   }
/*     */   
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 408 */     String str = "Enter or Revise a Transportation Problem\n\n";
/* 409 */     str = String.valueOf(String.valueOf(str)).concat("Enter the transportation problem model by filling out the ");
/* 410 */     str = String.valueOf(String.valueOf(str)).concat("parameter table (including specifying its size) displayed on the ");
/* 411 */     str = String.valueOf(String.valueOf(str)).concat("screen. The sum of the supplies at the sources must equal the sum of ");
/* 412 */     str = String.valueOf(String.valueOf(str)).concat("the demands at the destinations. After entering the model, you can ");
/* 413 */     str = String.valueOf(String.valueOf(str)).concat("prepare to solve it interactively (with the \"Find Initial Basic ");
/* 414 */     str = String.valueOf(String.valueOf(str)).concat("Feasible Solution\" command under the Procedure menu).\n\n");
/* 415 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of sources\n\n");
/* 416 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of sources (a positive integer) in the transportation ");
/* 417 */     str = String.valueOf(String.valueOf(str)).concat("problem, and then press the ENTER Key. Note: The number of sources cannot ");
/* 418 */     str = String.valueOf(String.valueOf(str)).concat("exceed 7.\n\n");
/* 419 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of destinations\n\n");
/* 420 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of destinations (a positive integer) in the transportation ");
/* 421 */     str = String.valueOf(String.valueOf(str)).concat("problem. Note: The number of destinations cannot exceed 7.\n\n");
/* 422 */     str = String.valueOf(String.valueOf(str)).concat("Entering a unit cost\n\n");
/* 423 */     str = String.valueOf(String.valueOf(str)).concat("Enter the cost per unit distributed from Source i to Destination j. The entry ");
/* 424 */     str = String.valueOf(String.valueOf(str)).concat("should be an integer, a decimal number, or M (Big M).\n\n");
/* 425 */     str = String.valueOf(String.valueOf(str)).concat("Entering the demand at a destination\n\n");
/* 426 */     str = String.valueOf(String.valueOf(str)).concat("Enter the demand (a positive integer) at this destination, and then press the ENTER key.\n\n");
/* 427 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of supplies\n\n");
/* 428 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of supplies (a positive integer) in the transportation ");
/* 429 */     str = String.valueOf(String.valueOf(str)).concat("problem, and then press the ENTER Key.");
/* 430 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 432 */     this.help_content_step.setText(str);
/* 433 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 440 */     String str = "Enter or Revise a Transportation Problem\n\n";
/* 441 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to quickly enter (or revise) a transportation ");
/* 442 */     str = String.valueOf(String.valueOf(str)).concat("problem model by entering the elements of a parameter table ");
/* 443 */     str = String.valueOf(String.valueOf(str)).concat("(see Sec. 8.1). The maximum size is 7 sources and 7 destinations. The sum of the supplies from the sources must equal the sum of the ");
/* 444 */     str = String.valueOf(String.valueOf(str)).concat("demands at the destinations. The individual supplies and demands must be integers.\n\n");
/* 445 */     str = String.valueOf(String.valueOf(str)).concat("If you simply wish to revise the model you entered last (or one you ");
/* 446 */     str = String.valueOf(String.valueOf(str)).concat("saved and just recalled with the Save and Open commands under the File ");
/* 447 */     str = String.valueOf(String.valueOf(str)).concat("menu), you do not need to reenter the entire model. Instead, just reenter ");
/* 448 */     str = String.valueOf(String.valueOf(str)).concat("the new numbers.\n\nWhen you finish entering the model, choose the \"Find Initial ");
/* 449 */     str = String.valueOf(String.valueOf(str)).concat("Basic Feasible Solution for Interactive Method\" procedure under the Procedure ");
/* 450 */     str = String.valueOf(String.valueOf(str)).concat("menu before using the \"Solve Interactively by the Transportation Simplex ");
/* 451 */     str = String.valueOf(String.valueOf(str)).concat("Method\" procedure.\n\n");
/* 452 */     str = String.valueOf(String.valueOf(str)).concat("At any step, detailed computer instructions are available (Help menu).");
/* 453 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 455 */     this.help_content_procedure.setText(str);
/* 456 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveProblem()
/*     */   {
/* 465 */     Vector p = new Vector();
/*     */     
/* 467 */     p.addElement(new Integer(this.num_supply));
/*     */     
/* 469 */     p.addElement(new Integer(this.num_demand));
/*     */     
/* 471 */     p.addElement(this.matrix);
/*     */     
/* 473 */     p.addElement(this.matrix_M);
/*     */     
/* 475 */     p.addElement(this.supply_column);
/*     */     
/* 477 */     p.addElement(this.demand_row);
/*     */     
/* 479 */     IOROperation operation = new IOROperation(20101, p);
/*     */     
/*     */ 
/* 482 */     this.controller.solver.doWork(operation);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTPEnterPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */