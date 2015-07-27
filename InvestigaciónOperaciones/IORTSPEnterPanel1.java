/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.TableCellEditor;
/*     */ 
/*     */ public class IORTSPEnterPanel1 extends IORTSPActionPanel
/*     */ {
/*  20 */   private static final Color editTableBackCol = Color.white;
/*  21 */   private static final Color tableBackCol = new Color(208, 208, 208);
/*  22 */   private int num_city = 7;
/*  23 */   private WholeNumberField wnf_num_city = null;
/*  24 */   private IORWholeNumberPanel wnp_num_city = null;
/*     */   private WholeNumberField[] tempField;
/*  26 */   private JLabel testLab = null;
/*     */   
/*  28 */   private IORWholeNumberPanel[] initSolPane = null;
/*  29 */   private JPanel mainPanel = null;
/*  30 */   private JPanel dataPanel = null;
/*  31 */   private JPanel initSolutionPane = null;
/*  32 */   private JPanel dimensionPanel = null;
/*     */   
/*  34 */   private JPanel canPanel = null;
/*  35 */   private static final int widCan = 600;
/*  36 */   private static final int heiCan = 220;
/*     */   
/*  38 */   private IORTSPCanvas canvas = null;
/*     */   
/*  40 */   private IORTSPEnterPanel1.TSPTableModel tspModel = null;
/*  41 */   private JTable table = null;
/*  42 */   private JScrollPane scrlPane = null;
/*  43 */   private boolean actionListener = false;
/*     */   
/*     */ 
/*     */ 
/*  47 */   public double[][] matrix = { { 0.0D, 12.0D, 10.0D, 0.0D, 0.0D, 0.0D, 12.0D }, { 12.0D, 0.0D, 8.0D, 12.0D, 0.0D, 0.0D, 0.0D }, { 10.0D, 8.0D, 0.0D, 11.0D, 3.0D, 0.0D, 9.0D }, { 0.0D, 12.0D, 11.0D, 0.0D, 11.0D, 10.0D, 0.0D }, { 0.0D, 0.0D, 3.0D, 11.0D, 0.0D, 6.0D, 7.0D }, { 0.0D, 0.0D, 0.0D, 10.0D, 6.0D, 0.0D, 9.0D }, { 12.0D, 0.0D, 9.0D, 0.0D, 7.0D, 9.0D, 0.0D } };
/*     */   
/*     */ 
/*     */ 
/*     */   public int[] initSolution;
/*     */   
/*     */ 
/*     */ 
/*  55 */   private final String[] matrixName = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
/*     */   
/*  57 */   private boolean is_panel_enabled = true;
/*     */   
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */   private int getMaxNumCity()
/*     */   {
/*  64 */     return this.controller.data.max_num_city;
/*     */   }
/*     */   
/*     */   private int getNumCity() {
/*  68 */     return this.controller.data.num_city;
/*     */   }
/*     */   
/*     */   private void setNumCity() {
/*  72 */     int max = getMaxNumCity();
/*  73 */     if (this.wnf_num_city.getValue() > max) {
/*  74 */       this.wnf_num_city.setValue(max);
/*     */     }
/*     */     
/*  77 */     int last = this.num_city;
/*  78 */     int s = this.wnf_num_city.getValue();
/*  79 */     if (s <= max) {
/*  80 */       this.num_city = s;
/*     */     }
/*  82 */     if (last != this.num_city) {
/*  83 */       this.wnf_num_city.setValue(this.num_city);
/*  84 */       repaint();
/*     */     }
/*  86 */     this.controller.data.num_city = this.wnf_num_city.getValue();
/*  87 */     this.controller.data.isDrawLine = false;
/*     */   }
/*     */   
/*     */   private void initializeData()
/*     */   {
/*  92 */     this.num_city = getNumCity();
/*  93 */     this.matrix = getMatrix();
/*  94 */     this.initSolution = getInitSolution();
/*  95 */     this.initSolPane = new IORWholeNumberPanel[this.num_city - 1];
/*  96 */     this.tempField = getField();
/*  97 */     this.tspModel.fireTableStructureChanged();
/*  98 */     this.actionListener = false;
/*     */   }
/*     */   
/* 101 */   private WholeNumberField[] getField() { return this.controller.data.tempField = new WholeNumberField[this.controller.data.num_city - 1]; }
/*     */   
/*     */   private void changedData()
/*     */   {
/* 105 */     int max_city = getMaxNumCity();
/* 106 */     int numCity = this.num_city;
/* 107 */     this.matrix = new double[numCity][numCity];
/* 108 */     this.initSolPane = new IORWholeNumberPanel[numCity - 1];
/* 109 */     this.tempField = getField();
/* 110 */     this.initSolution = new int[numCity];
/*     */     
/* 112 */     this.controller.data.saveSolution = new int[numCity];
/* 113 */     for (int i = 0; i < this.controller.data.saveSolution.length; i++) {
/* 114 */       this.controller.data.saveSolution[i] = (i + 1);
/*     */     }
/*     */   }
/*     */   
/*     */   private double[][] getMatrix() {
/* 119 */     return this.controller.data.matrix;
/*     */   }
/*     */   
/*     */   private int[] getInitSolution() {
/* 123 */     return this.controller.data.initSolution;
/*     */   }
/*     */   
/*     */   public void updatePanel() {}
/*     */   
/*     */   public void setPanelEnable(boolean b)
/*     */   {
/* 130 */     this.wnf_num_city.setEditable(b);
/* 131 */     this.is_panel_enabled = b;
/*     */   }
/*     */   
/*     */   public void initPrintData() {
/* 135 */     for (int i = 0; i < this.num_city; i++) {
/* 136 */       for (int j = 0; j < this.num_city; j++) {
/* 137 */         if ((this.matrix[i][j] == 0) && (i != j)) {
/* 138 */           this.matrix[i][j] = 2.147483647E9D;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 144 */     this.controller.data.num_city = this.wnf_num_city.getValue();
/* 145 */     this.controller.data.matrix = this.matrix;
/* 146 */     this.controller.data.initSolution = this.initSolution;
/* 147 */     IOROperation operation = new IOROperation(60008, new Vector());
/* 148 */     this.controller.solver.initPrintData(operation, this.controller.data);
/*     */   }
/*     */   
/*     */   protected void finishProcedure() {
/* 152 */     this.table.repaint();
/* 153 */     if (this.table.isEditing())
/* 154 */       this.table.getDefaultEditor(String.class).stopCellEditing();
/* 155 */     this.table.clearSelection();
/* 156 */     if (this.actionListener) {
/* 157 */       for (int i = 0; i < this.initSolution.length; i++) {
/* 158 */         if (i == 0) {
/* 159 */           this.initSolution[0] = 1;
/*     */ 
/*     */         }
/* 162 */         else if (this.tempField[(i - 1)] != null) {
/* 163 */           this.initSolution[i] = this.tempField[(i - 1)].getValue();
/*     */         }
/*     */       }
/* 166 */       for (int i = 0; i < this.initSolution.length; i++) {
/* 167 */         this.initSolution[i] -= 1;
/*     */       }
/*     */     }
/* 170 */     if (!checkoutInitSolution(this.initSolution)) {
/* 171 */       JOptionPane.showMessageDialog(this, "Not a feasible solution. Try again!");
/* 172 */       return;
/*     */     }
/*     */     
/* 175 */     for (int i = 0; i < this.num_city; i++) {
/* 176 */       for (int j = 0; j < this.num_city; j++) {
/* 177 */         if ((this.matrix[i][j] == 0) && (i != j)) {
/* 178 */           this.matrix[i][j] = 2.147483647E9D;
/*     */         }
/*     */       }
/*     */     }
/* 182 */     Vector p = new Vector();
/* 183 */     p.addElement(new Integer(this.num_city));
/* 184 */     p.addElement(this.matrix);
/* 185 */     p.addElement(this.initSolution);
/*     */     
/* 187 */     IOROperation operation = new IOROperation(60008, p);
/*     */     
/* 189 */     if (this.controller.solver.setOperation(operation, this.controller.data)) {
/* 190 */       JOptionPane.showMessageDialog(this, "Not a feasible solution. Try again!");
/* 191 */       return;
/*     */     }
/*     */     
/* 194 */     this.controller.setMenuState(10);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 199 */     initPrintData();
/*     */     
/* 201 */     setPanelEnable(false);
/* 202 */     setFinishEnabled(false);
/* 203 */     this.controller.genetic();
/*     */   }
/*     */   
/*     */   class textAction extends javax.swing.AbstractAction {
/*     */     int fildNum;
/*     */     WholeNumberField textfield;
/*     */     
/*     */     textAction(int textNum, WholeNumberField text) {
/* 211 */       this.fildNum = textNum;
/* 212 */       this.textfield = text;
/* 213 */       IORTSPEnterPanel1.this.actionListener = true;
/*     */     }
/*     */     
/* 216 */     public void actionPerformed(ActionEvent e) { int column = this.textfield.getValue();
/* 217 */       IORTSPEnterPanel1.this.initSolution[(this.fildNum + 1)] = column;
/* 218 */       IORTSPEnterPanel1.this.initPrintData();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean checkoutInitSolution(int[] value) {
/* 223 */     boolean isRight = true;
/* 224 */     for (int i = 0; i < value.length; i++) {
/* 225 */       if ((value[i] > this.num_city - 1) || (value[i] < 0)) {
/* 226 */         isRight = false;
/* 227 */       } else if ((value[i] == 0) && (i != 0)) {
/* 228 */         isRight = false;
/*     */       }
/*     */       
/* 231 */       for (int j = 0; j < value.length; j++) {
/* 232 */         if ((i != j) && (value[i] == value[j])) {
/* 233 */           isRight = false;
/*     */         }
/*     */       }
/*     */     }
/* 237 */     return isRight;
/*     */   }
/*     */   
/*     */   public void renewInitSolutionInput() {
/* 241 */     if (this.initSolutionPane != null) {
/* 242 */       this.dimensionPanel.remove(this.initSolutionPane);
/* 243 */       this.initSolutionPane.removeAll();
/*     */     }
/*     */     
/* 246 */     this.testLab = new JLabel("  --> 1   ");
/* 247 */     this.initSolution[0] = 0;
/*     */     
/* 249 */     this.initSolutionPane = new JPanel();
/* 250 */     for (int i = 0; i < this.initSolPane.length; i++) {
/* 251 */       if (i != 0) {
/* 252 */         this.initSolPane[i] = new IORWholeNumberPanel(i + 2, 2, "-->");
/*     */       }
/*     */       else {
/* 255 */         this.initSolPane[i] = new IORWholeNumberPanel(2, 2, " Initial Trial Solution : 1 -->  ");
/*     */       }
/* 257 */       this.initSolutionPane.add(this.initSolPane[i]);
/*     */       
/* 259 */       this.tempField[i] = this.initSolPane[i].getWholeNumberField();
/*     */       
/* 261 */       this.tempField[i].addActionListener(new IORTSPEnterPanel1.textAction(i, this.tempField[i]));
/* 262 */       this.initSolution[(i + 1)] = (i + 1);
/*     */     }
/* 264 */     this.initSolutionPane.add(this.testLab);
/*     */     
/* 266 */     this.initSolutionPane.revalidate();
/* 267 */     this.initSolutionPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
/*     */   }
/*     */   
/*     */   public IORTSPEnterPanel1(IORTSPProcessController c)
/*     */   {
/* 272 */     super(c);
/* 273 */     this.bn_back.setVisible(false);
/*     */     
/* 275 */     this.mainPanel = new JPanel();
/* 276 */     add(this.mainPanel, "Center");
/*     */     
/* 278 */     this.wnp_num_city = new IORWholeNumberPanel(getNumCity(), 3, "Number of Cities (≤ 10):        ");
/* 279 */     this.wnf_num_city = this.wnp_num_city.getWholeNumberField();
/* 280 */     this.wnf_num_city.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 282 */         int column = IORTSPEnterPanel1.this.table.getSelectedColumn();
/* 283 */         if ((IORTSPEnterPanel1.this.table != null) && (column > 0) && (column < IORTSPEnterPanel1.this.num_city + 2)) {
/* 284 */           IORTSPEnterPanel1.this.table.clearSelection();
/* 285 */           IORTSPEnterPanel1.this.table.getDefaultEditor(IORTSPEnterPanel1.this.tspModel.getColumnClass(column)).stopCellEditing();
/*     */         }
/* 287 */         IORTSPEnterPanel1.this.setNumCity();
/* 288 */         IORTSPEnterPanel1.this.changedData();
/* 289 */         IORTSPEnterPanel1.this.renewInitSolutionInput();
/* 290 */         IORTSPEnterPanel1.this.canvas.netList.drawMap();
/* 291 */         IORTSPEnterPanel1.this.tspModel.fireTableStructureChanged();
/* 292 */         IORTSPEnterPanel1.this.table.sizeColumnsToFit(-1);
/* 293 */         IORTSPEnterPanel1.this.revalidate();
/* 294 */         IORTSPEnterPanel1.this.repaint();
/* 295 */         IORTSPEnterPanel1.this.initPrintData();
/*     */         
/* 297 */         IORTSPEnterPanel1.this.setTableBackColor();
/*     */       }
/* 299 */     });
/* 300 */     this.wnp_num_city.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
/* 301 */     this.tspModel = new IORTSPEnterPanel1.TSPTableModel(null);
/* 302 */     this.table = new JTable(this.tspModel);
/* 303 */     this.table.setCellSelectionEnabled(true);
/* 304 */     this.table.setSelectionMode(0);
/* 305 */     this.table.setEnabled(true);
/* 306 */     this.scrlPane = new JScrollPane(this.table);
/* 307 */     this.scrlPane.setPreferredSize(new Dimension(380, 160));
/* 308 */     this.scrlPane.setMaximumSize(new Dimension(380, 200));
/* 309 */     this.scrlPane.setAlignmentX(0.5F);
/* 310 */     this.scrlPane.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
/*     */     
/* 312 */     this.canvas = new IORTSPCanvas(600, 220, this.controller);
/* 313 */     this.canPanel = new JPanel();
/* 314 */     this.canPanel.setLayout(new BorderLayout());
/* 315 */     this.canPanel.add(this.scrlPane, "Center");
/* 316 */     this.canPanel.setBorder(null);
/*     */     
/* 318 */     initializeData();
/*     */     
/* 320 */     this.dimensionPanel = new JPanel();
/* 321 */     this.dimensionPanel.setLayout(new javax.swing.BoxLayout(this.dimensionPanel, 1));
/* 322 */     this.dimensionPanel.add(this.wnp_num_city);
/*     */     
/* 324 */     this.dimensionPanel.add(this.wnp_num_city);
/* 325 */     renewInitSolutionInput();
/* 326 */     this.dimensionPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
/*     */     
/* 328 */     this.dataPanel = new JPanel();
/* 329 */     this.dataPanel.setLayout(new java.awt.GridLayout(2, 1));
/* 330 */     this.dataPanel.add(this.scrlPane, "1");
/* 331 */     this.dataPanel.add(this.canPanel, "2");
/*     */     
/* 333 */     this.mainPanel.setLayout(new BorderLayout());
/* 334 */     this.mainPanel.add(this.dimensionPanel, "North");
/* 335 */     this.mainPanel.add(Box.createRigidArea(new Dimension(2, 10)), "East");
/* 336 */     this.mainPanel.add(Box.createRigidArea(new Dimension(2, 10)), "West");
/* 337 */     this.mainPanel.add(this.dataPanel, "Center");
/* 338 */     this.mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
/*     */     
/*     */ 
/* 341 */     initPrintData();
/* 342 */     setTableBackColor();
/*     */   }
/*     */   
/*     */   public void setTableBackColor()
/*     */   {
/* 347 */     DefaultTableCellRenderer tcr = new DefaultTableCellRenderer()
/*     */     {
/*     */ 
/*     */       public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
/*     */       {
/*     */ 
/* 353 */         Color[][] tempCol = new Color[table.getRowCount()][table.getColumnCount()];
/*     */         
/* 355 */         for (int i = 0; i < table.getRowCount(); i++) {
/* 356 */           for (int j = 0; j < table.getColumnCount(); j++) {
/* 357 */             if ((j == 0) || (i >= j - 1)) {
/* 358 */               tempCol[i][j] = IORTSPEnterPanel1.tableBackCol;
/*     */             } else {
/* 360 */               tempCol[i][j] = IORTSPEnterPanel1.editTableBackCol;
/*     */             }
/*     */           }
/*     */         }
/* 364 */         setBackground(tempCol[row][column]);
/* 365 */         return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
/*     */       }
/*     */       
/* 368 */     };
/* 369 */     String[] identifier = new String[this.table.getColumnCount()];
/* 370 */     for (int i = 0; i < identifier.length; i++) {
/* 371 */       if (i == 0) {
/* 372 */         identifier[i] = "City";
/*     */       } else {
/* 374 */         identifier[i] = "".concat(String.valueOf(String.valueOf(i)));
/*     */       }
/*     */     }
/*     */     
/* 378 */     for (int i = 0; i < identifier.length; i++) {
/* 379 */       this.table.getColumn(identifier[i]).setCellRenderer(tcr);
/*     */     }
/* 381 */     this.table.repaint(); }
/*     */   
/*     */   private class TSPTableModel extends javax.swing.table.AbstractTableModel { private int i;
/*     */     private int j;
/*     */     
/* 386 */     TSPTableModel(IORTSPEnterPanel1..3 x$1) { this(); }
/*     */     
/*     */     public int getColumnCount()
/*     */     {
/* 390 */       return IORTSPEnterPanel1.this.getNumCity() + 1;
/*     */     }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 395 */       return IORTSPEnterPanel1.this.getNumCity();
/*     */     }
/*     */     
/*     */     public String getColumnName(int col)
/*     */     {
/* 400 */       String str = "";
/* 401 */       if (col == 0) {
/* 402 */         str = String.valueOf(String.valueOf(str)).concat("City");
/*     */       } else
/* 404 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTSPEnterPanel1.this.matrixName[(col - 1)])));
/* 405 */       return str;
/*     */     }
/*     */     
/*     */ 
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 411 */       String str = "";
/* 412 */       int r = -1;int c = -1;
/* 413 */       if ((col == 0) && (row == 0)) {
/* 414 */         str = String.valueOf(String.valueOf(str)).concat("    1");
/*     */       }
/* 416 */       else if (col == 0) {
/* 417 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("    ".concat(String.valueOf(String.valueOf(IORTSPEnterPanel1.this.matrixName[row]))))));
/*     */       }
/* 419 */       else if (col - 1 < row) {
/* 420 */         str = "";
/*     */       }
/*     */       else {
/* 423 */         r = row;
/* 424 */         c = col - 1;
/* 425 */         if (r != IORTSPEnterPanel1.this.num_city) {
/* 426 */           if (r == c) {
/* 427 */             str = String.valueOf(String.valueOf(str)).concat("");
/*     */           }
/* 429 */           else if ((IORTSPEnterPanel1.this.matrix[r][c] == Integer.MAX_VALUE) || (IORTSPEnterPanel1.this.matrix[r][c] == 0)) {
/* 430 */             str = "";
/*     */           }
/*     */           else
/*     */           {
/* 434 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(IORTSPEnterPanel1.this.matrix[r][c]))))));
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 439 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c) {
/* 443 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int col)
/*     */     {
/* 448 */       if (!IORTSPEnterPanel1.this.is_panel_enabled)
/* 449 */         return false;
/* 450 */       if (col == 0) {
/* 451 */         return false;
/*     */       }
/* 453 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */     public void setValueAt(Object value, int row, int col)
/*     */     {
/* 459 */       String input = (String)value;
/* 460 */       if ((col != 0) && (row < col - 1)) {
/* 461 */         if (input.equalsIgnoreCase("")) {
/* 462 */           IORTSPEnterPanel1.this.matrix[row][(col - 1)] = 0.0D;
/* 463 */           IORTSPEnterPanel1.this.matrix[(col - 1)][row] = 0.0D;
/* 464 */           fireTableCellUpdated(row, col - 1);
/* 465 */           return;
/*     */         }
/*     */         try {
/* 468 */           double tempInput = Double.valueOf(input).doubleValue();
/* 469 */           if (tempInput == 0) {
/* 470 */             return;
/*     */           }
/*     */           
/* 473 */           double d = Double.valueOf(input).doubleValue();
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 482 */           IORTSPEnterPanel1.this.matrix[row][(col - 1)] = d;
/* 483 */           IORTSPEnterPanel1.this.matrix[(col - 1)][row] = d;
/*     */         }
/*     */         catch (NumberFormatException e) {
/* 486 */           return;
/*     */         }
/*     */         
/* 489 */         fireTableCellUpdated(row, col - 1);
/* 490 */         IORTSPEnterPanel1.this.initPrintData();
/*     */       }
/*     */       
/* 493 */       for (int i = 0; i < IORTSPEnterPanel1.this.num_city; i++) {
/* 494 */         for (int j = 0; j < IORTSPEnterPanel1.this.num_city; j++) {}
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 501 */       IORTSPEnterPanel1.this.initPrintData();
/*     */     }
/*     */     
/*     */     private TSPTableModel() {}
/*     */   }
/*     */   
/* 507 */   protected void initializeCurrentStepHelpPanel() { String str = "Enter or Revise a TraveLing Salesman Problem";
/* 508 */     str = String.valueOf(String.valueOf(str)).concat("\n\nEnter the number of cities:");
/* 509 */     str = String.valueOf(String.valueOf(str)).concat("\nEnter the number of cities (a positive integer) in the problem, and then press the Enter key. \nNote: the number of cities cannot exceed 10.");
/* 510 */     str = String.valueOf(String.valueOf(str)).concat("\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/*     */ 
/* 513 */     this.help_content_step.setText(str);
/* 514 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */   protected void initializeCurrentProcedureHelpPanel() {
/* 518 */     String str = "Enter or Revise a TraveLing Salesman Problem";
/* 519 */     str = String.valueOf(String.valueOf(str)).concat("\n\nEnter the number of cities:");
/* 520 */     str = String.valueOf(String.valueOf(str)).concat("\nEnter the number of cities (a positive integer) in the problem, and then press the Enter key. \nNote: the number of cities cannot exceed 10.");
/* 521 */     str = String.valueOf(String.valueOf(str)).concat("\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 523 */     this.help_content_procedure.setText(str);
/* 524 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void SaveProblem()
/*     */   {
/*     */     try
/*     */     {
/* 533 */       System.out.println("Save Problem");
/* 534 */       Vector p = new Vector();
/* 535 */       p.addElement(new Integer(this.num_city));
/* 536 */       p.addElement(this.matrix);
/* 537 */       p.addElement(this.initSolution);
/*     */       
/* 539 */       IOROperation operation = new IOROperation(60008, p);
/*     */       
/* 541 */       this.controller.solver.doWork(operation);
/*     */     }
/*     */     catch (Exception e1) {
/* 544 */       System.out.println("Save fails");
/*     */     }
/*     */   }
/*     */   
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/* 550 */     initializeData();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTSPEnterPanel1.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */