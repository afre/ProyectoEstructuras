/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.ObjectInputStream;
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
/*     */ import javax.swing.table.TableColumn;
/*     */ 
/*     */ public class IORTSPEnterPanel extends IORTSPActionPanel
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
/*  40 */   private IORTSPEnterPanel.TSPTableModel tspModel = null;
/*  41 */   private JTable table = null;
/*  42 */   private JScrollPane scrlPane = null;
/*  43 */   private boolean actionListener = false;
/*     */   
/*     */   private long firstClickTime;
/*  46 */   public double[][] matrix = { { 0.0D, 12.0D, 10.0D, 0.0D, 0.0D, 0.0D, 12.0D }, { 12.0D, 0.0D, 8.0D, 12.0D, 0.0D, 0.0D, 0.0D }, { 10.0D, 8.0D, 0.0D, 11.0D, 3.0D, 0.0D, 9.0D }, { 0.0D, 12.0D, 11.0D, 0.0D, 11.0D, 10.0D, 0.0D }, { 0.0D, 0.0D, 3.0D, 11.0D, 0.0D, 6.0D, 7.0D }, { 0.0D, 0.0D, 0.0D, 10.0D, 6.0D, 0.0D, 9.0D }, { 12.0D, 0.0D, 9.0D, 0.0D, 7.0D, 9.0D, 0.0D } };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int[] initSolution;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  61 */   private final String[] matrixName = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
/*     */   
/*  63 */   private boolean is_panel_enabled = true;
/*     */   
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */   private int getMaxNumCity()
/*     */   {
/*  70 */     return this.controller.data.max_num_city;
/*     */   }
/*     */   
/*     */   private int getNumCity() {
/*  74 */     return this.controller.data.num_city;
/*     */   }
/*     */   
/*     */   private void setNumCity() {
/*  78 */     int max = getMaxNumCity();
/*  79 */     if (this.wnf_num_city.getValue() > max) {
/*  80 */       this.wnf_num_city.setValue(max);
/*     */     }
/*     */     
/*  83 */     int last = this.num_city;
/*  84 */     int s = this.wnf_num_city.getValue();
/*  85 */     if (s <= max) {
/*  86 */       this.num_city = s;
/*     */     }
/*  88 */     if (last != this.num_city) {
/*  89 */       this.wnf_num_city.setValue(this.num_city);
/*  90 */       repaint();
/*     */     }
/*  92 */     this.controller.data.num_city = this.wnf_num_city.getValue();
/*  93 */     this.controller.data.isDrawLine = false;
/*     */   }
/*     */   
/*     */   private void initializeData()
/*     */   {
/*  98 */     this.num_city = getNumCity();
/*  99 */     this.matrix = getMatrix();
/* 100 */     this.initSolution = getInitSolution();
/* 101 */     this.initSolPane = new IORWholeNumberPanel[this.num_city - 1];
/* 102 */     this.tempField = getField();
/* 103 */     this.tspModel.fireTableStructureChanged();
/* 104 */     this.actionListener = false;
/*     */     
/* 106 */     for (int i = 0; i < this.tempField.length; i++) {
/* 107 */       this.tempField[i] = new WholeNumberField(this.controller.data.saveSolution[(i + 1)], 1);
/*     */     }
/*     */   }
/*     */   
/*     */   private WholeNumberField[] getField() {
/* 112 */     return this.controller.data.tempField = new WholeNumberField[this.controller.data.num_city - 1];
/*     */   }
/*     */   
/*     */   private void changedData() {
/* 116 */     int max_city = getMaxNumCity();
/* 117 */     int numCity = this.num_city;
/* 118 */     this.matrix = new double[numCity][numCity];
/* 119 */     this.initSolPane = new IORWholeNumberPanel[numCity - 1];
/* 120 */     this.tempField = getField();
/* 121 */     this.initSolution = new int[numCity];
/* 122 */     this.controller.data.saveSolution = new int[numCity];
/* 123 */     for (int i = 0; i < this.controller.data.saveSolution.length; i++) {
/* 124 */       this.controller.data.saveSolution[i] = (i + 1);
/*     */     }
/*     */   }
/*     */   
/*     */   private double[][] getMatrix()
/*     */   {
/* 130 */     return this.controller.data.matrix;
/*     */   }
/*     */   
/*     */   private int[] getInitSolution() {
/* 134 */     return this.controller.data.initSolution;
/*     */   }
/*     */   
/*     */   public void updatePanel() {}
/*     */   
/*     */   public void setPanelEnable(boolean b)
/*     */   {
/* 141 */     this.wnf_num_city.setEditable(b);
/* 142 */     this.is_panel_enabled = b;
/*     */   }
/*     */   
/*     */   public void initPrintData() {
/* 146 */     for (int i = 0; i < this.num_city; i++) {
/* 147 */       for (int j = 0; j < this.num_city; j++) {
/* 148 */         if ((this.matrix[i][j] == 0) && (i != j)) {
/* 149 */           this.matrix[i][j] = 2.147483647E9D;
/*     */         }
/*     */       }
/*     */     }
/* 153 */     this.controller.data.num_city = this.wnf_num_city.getValue();
/* 154 */     this.controller.data.matrix = this.matrix;
/* 155 */     this.controller.data.initSolution = this.initSolution;
/* 156 */     IOROperation operation = new IOROperation(60008, new Vector());
/* 157 */     this.controller.solver.initPrintData(operation, this.controller.data);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void finishProcedure()
/*     */   {
/* 163 */     this.table.repaint();
/* 164 */     if (this.table.isEditing())
/* 165 */       this.table.getDefaultEditor(String.class).stopCellEditing();
/* 166 */     this.table.clearSelection();
/* 167 */     if (this.actionListener) {
/* 168 */       for (int i = 0; i < this.initSolution.length; i++) {
/* 169 */         if (i == 0) {
/* 170 */           this.initSolution[0] = 1;
/*     */ 
/*     */         }
/* 173 */         else if (this.tempField[(i - 1)] != null) {
/* 174 */           this.initSolution[i] = this.tempField[(i - 1)].getValue();
/*     */         }
/*     */       }
/* 177 */       for (int i = 0; i < this.initSolution.length; i++) {
/* 178 */         this.initSolution[i] -= 1;
/*     */       }
/*     */     }
/* 181 */     if (!checkoutInitSolution(this.initSolution)) {
/* 182 */       JOptionPane.showMessageDialog(this, "Not a feasible solution. Try again!");
/* 183 */       return;
/*     */     }
/*     */     
/* 186 */     for (int i = 0; i < this.num_city; i++) {
/* 187 */       for (int j = 0; j < this.num_city; j++) {
/* 188 */         if ((this.matrix[i][j] == 0) && (i != j)) {
/* 189 */           this.matrix[i][j] = 2.147483647E9D;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 194 */     Vector p = new Vector();
/* 195 */     p.addElement(new Integer(this.num_city));
/* 196 */     p.addElement(this.matrix);
/* 197 */     p.addElement(this.initSolution);
/*     */     
/*     */ 
/* 200 */     IOROperation operation = new IOROperation(60008, p);
/*     */     
/* 202 */     if (this.controller.solver.setOperation(operation, this.controller.data)) {
/* 203 */       JOptionPane.showMessageDialog(this, "Not a feasible solution. Try again!");
/* 204 */       return;
/*     */     }
/*     */     
/* 207 */     this.controller.setMenuState(10);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 214 */     setPanelEnable(false);
/* 215 */     setFinishEnabled(false);
/*     */     
/* 217 */     initPrintData();
/* 218 */     if (this.controller.procedure.equalsIgnoreCase("Tabu Search")) {
/* 219 */       this.controller.tabuSearch();
/* 220 */     } else if (this.controller.procedure.equalsIgnoreCase("Simulated Annealing")) {
/* 221 */       this.controller.simulatedAnnealing();
/*     */     }
/*     */     
/* 224 */     for (int i = 0; i < this.tempField.length; i++) {
/* 225 */       this.controller.data.saveSolution[(i + 1)] = this.tempField[i].getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   class textAction extends javax.swing.AbstractAction
/*     */   {
/*     */     int fildNum;
/*     */     WholeNumberField textfield;
/*     */     
/*     */     textAction(int textNum, WholeNumberField text) {
/* 235 */       this.fildNum = textNum;
/* 236 */       this.textfield = text;
/* 237 */       IORTSPEnterPanel.this.actionListener = true;
/*     */     }
/*     */     
/* 240 */     public void actionPerformed(ActionEvent e) { int column = this.textfield.getValue();
/* 241 */       IORTSPEnterPanel.this.initSolution[(this.fildNum + 1)] = column;
/* 242 */       IORTSPEnterPanel.this.initPrintData();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean checkoutInitSolution(int[] value) {
/* 247 */     boolean isRight = true;
/* 248 */     for (int i = 0; i < value.length; i++) {
/* 249 */       if ((value[i] > this.num_city - 1) || (value[i] < 0)) {
/* 250 */         isRight = false;
/* 251 */       } else if ((value[i] == 0) && (i != 0)) {
/* 252 */         isRight = false;
/*     */       }
/*     */       
/* 255 */       for (int j = 0; j < value.length; j++) {
/* 256 */         if ((i != j) && (value[i] == value[j])) {
/* 257 */           isRight = false;
/*     */         }
/*     */       }
/*     */     }
/* 261 */     return isRight;
/*     */   }
/*     */   
/*     */   public void renewInitSolutionInput() {
/* 265 */     if (this.initSolutionPane != null) {
/* 266 */       this.dimensionPanel.remove(this.initSolutionPane);
/* 267 */       this.initSolutionPane.removeAll();
/*     */     }
/*     */     
/* 270 */     this.testLab = new JLabel("  --> 1   ");
/* 271 */     this.initSolution[0] = 0;
/*     */     
/* 273 */     this.initSolutionPane = new JPanel();
/* 274 */     for (int i = 0; i < this.initSolPane.length; i++) {
/* 275 */       if (i != 0) {
/* 276 */         this.initSolPane[i] = new IORWholeNumberPanel(i + 2, 2, "-->");
/*     */       }
/*     */       else {
/* 279 */         this.initSolPane[i] = new IORWholeNumberPanel(2, 2, " Initial Trial Solution : 1 -->  ");
/*     */       }
/* 281 */       this.initSolutionPane.add(this.initSolPane[i]);
/* 282 */       this.tempField[i] = this.initSolPane[i].getWholeNumberField();
/* 283 */       this.tempField[i].setValue(this.controller.data.saveSolution[(i + 1)]);
/* 284 */       this.tempField[i].addActionListener(new IORTSPEnterPanel.textAction(i, this.tempField[i]));
/* 285 */       this.initSolution[(i + 1)] = (i + 1);
/*     */     }
/* 287 */     this.initSolutionPane.add(this.testLab);
/*     */     
/* 289 */     this.initSolutionPane.revalidate();
/* 290 */     this.initSolutionPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
/* 291 */     this.dimensionPanel.add(this.initSolutionPane);
/*     */   }
/*     */   
/*     */   public IORTSPEnterPanel(IORTSPProcessController c) {
/* 295 */     super(c);
/* 296 */     this.bn_back.setVisible(false);
/*     */     
/* 298 */     this.mainPanel = new JPanel();
/* 299 */     add(this.mainPanel, "Center");
/*     */     
/* 301 */     this.wnp_num_city = new IORWholeNumberPanel(getNumCity(), 3, "Number of Cities (≤ 10):        ");
/*     */     
/*     */ 
/*     */ 
/* 305 */     this.wnf_num_city = this.wnp_num_city.getWholeNumberField();
/* 306 */     this.wnf_num_city.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 308 */         int column = IORTSPEnterPanel.this.table.getSelectedColumn();
/* 309 */         if ((IORTSPEnterPanel.this.table != null) && (column > 0) && (column < IORTSPEnterPanel.this.num_city + 2)) {
/* 310 */           IORTSPEnterPanel.this.table.clearSelection();
/* 311 */           IORTSPEnterPanel.this.table.getDefaultEditor(IORTSPEnterPanel.this.tspModel.getColumnClass(column)).stopCellEditing();
/*     */         }
/*     */         
/* 314 */         IORTSPEnterPanel.this.setNumCity();
/* 315 */         IORTSPEnterPanel.this.changedData();
/* 316 */         IORTSPEnterPanel.this.renewInitSolutionInput();
/* 317 */         IORTSPEnterPanel.this.canvas.netList.drawMap();
/* 318 */         IORTSPEnterPanel.this.tspModel.fireTableStructureChanged();
/* 319 */         IORTSPEnterPanel.this.table.sizeColumnsToFit(-1);
/* 320 */         IORTSPEnterPanel.this.revalidate();
/* 321 */         IORTSPEnterPanel.this.repaint();
/* 322 */         IORTSPEnterPanel.this.initPrintData();
/*     */         
/* 324 */         IORTSPEnterPanel.this.setTableBackColor();
/*     */       }
/*     */       
/* 327 */     });
/* 328 */     this.wnp_num_city.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
/* 329 */     this.tspModel = new IORTSPEnterPanel.TSPTableModel(null);
/* 330 */     this.table = new JTable(this.tspModel);
/*     */     
/*     */ 
/*     */ 
/* 334 */     this.table.setCellSelectionEnabled(true);
/* 335 */     this.table.setSelectionMode(0);
/* 336 */     this.table.setEnabled(true);
/* 337 */     this.scrlPane = new JScrollPane(this.table);
/* 338 */     this.scrlPane.setPreferredSize(new Dimension(380, 160));
/* 339 */     this.scrlPane.setMaximumSize(new Dimension(380, 200));
/* 340 */     this.scrlPane.setAlignmentX(0.5F);
/* 341 */     this.scrlPane.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
/*     */     
/* 343 */     this.canvas = new IORTSPCanvas(600, 220, this.controller);
/* 344 */     this.canPanel = new JPanel();
/* 345 */     this.canPanel.setLayout(new BorderLayout());
/* 346 */     this.canPanel.add(this.scrlPane, "Center");
/* 347 */     this.canPanel.setBorder(null);
/*     */     
/* 349 */     initializeData();
/*     */     
/* 351 */     this.dimensionPanel = new JPanel();
/* 352 */     this.dimensionPanel.setLayout(new javax.swing.BoxLayout(this.dimensionPanel, 1));
/* 353 */     this.dimensionPanel.add(this.wnp_num_city);
/*     */     
/* 355 */     this.dimensionPanel.add(this.wnp_num_city);
/* 356 */     renewInitSolutionInput();
/* 357 */     this.dimensionPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
/*     */     
/* 359 */     this.dataPanel = new JPanel();
/* 360 */     this.dataPanel.setLayout(new java.awt.GridLayout(2, 1));
/* 361 */     this.dataPanel.add(this.scrlPane, "1");
/* 362 */     this.dataPanel.add(this.canPanel, "2");
/*     */     
/* 364 */     this.mainPanel.setLayout(new BorderLayout());
/* 365 */     this.mainPanel.add(this.dimensionPanel, "North");
/* 366 */     this.mainPanel.add(Box.createRigidArea(new Dimension(2, 10)), "East");
/*     */     
/* 368 */     this.mainPanel.add(Box.createRigidArea(new Dimension(2, 10)), "West");
/*     */     
/* 370 */     this.mainPanel.add(this.dataPanel, "Center");
/* 371 */     this.mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
/* 372 */     initPrintData();
/*     */     
/* 374 */     setTableBackColor();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setTableBackColor()
/*     */   {
/* 380 */     DefaultTableCellRenderer tcr = new DefaultTableCellRenderer()
/*     */     {
/*     */ 
/*     */       public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
/*     */       {
/*     */ 
/* 386 */         Color[][] tempCol = new Color[table.getRowCount()][table.getColumnCount()];
/*     */         
/* 388 */         for (int i = 0; i < table.getRowCount(); i++) {
/* 389 */           for (int j = 0; j < table.getColumnCount(); j++) {
/* 390 */             if ((j == 0) || (i >= j - 1)) {
/* 391 */               tempCol[i][j] = IORTSPEnterPanel.tableBackCol;
/*     */             } else {
/* 393 */               tempCol[i][j] = IORTSPEnterPanel.editTableBackCol;
/*     */             }
/*     */           }
/*     */         }
/* 397 */         setBackground(tempCol[row][column]);
/* 398 */         return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
/*     */       }
/*     */       
/* 401 */     };
/* 402 */     String[] identifier = new String[this.table.getColumnCount()];
/* 403 */     for (int i = 0; i < identifier.length; i++) {
/* 404 */       if (i == 0) {
/* 405 */         identifier[i] = "City";
/*     */       } else {
/* 407 */         identifier[i] = "".concat(String.valueOf(String.valueOf(i)));
/*     */       }
/*     */     }
/*     */     
/* 411 */     for (int i = 0; i < identifier.length; i++) {
/* 412 */       this.table.getColumn(identifier[i]).setCellRenderer(tcr);
/*     */     }
/* 414 */     this.table.repaint(); }
/*     */   
/*     */   private class TSPTableModel extends javax.swing.table.AbstractTableModel { private int i;
/*     */     
/* 418 */     TSPTableModel(IORTSPEnterPanel..3 x$1) { this(); }
/*     */     
/*     */     public int getColumnCount()
/*     */     {
/* 422 */       return IORTSPEnterPanel.this.getNumCity() + 1;
/*     */     }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 427 */       return IORTSPEnterPanel.this.getNumCity();
/*     */     }
/*     */     
/*     */     public String getColumnName(int col)
/*     */     {
/* 432 */       String str = "";
/* 433 */       if (col == 0) {
/* 434 */         str = String.valueOf(String.valueOf(str)).concat("City");
/*     */       } else
/* 436 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORTSPEnterPanel.this.matrixName[(col - 1)])));
/* 437 */       return str;
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 442 */       String str = "";
/* 443 */       int r = -1;int c = -1;
/* 444 */       if ((col == 0) && (row == 0)) {
/* 445 */         str = String.valueOf(String.valueOf(str)).concat("    1");
/*     */       }
/* 447 */       else if (col == 0) {
/* 448 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("    ".concat(String.valueOf(String.valueOf(IORTSPEnterPanel.this.matrixName[row]))))));
/*     */       }
/* 450 */       else if (col - 1 < row) {
/* 451 */         str = "";
/*     */       }
/*     */       else {
/* 454 */         r = row;
/* 455 */         c = col - 1;
/* 456 */         if (r != IORTSPEnterPanel.this.num_city) {
/* 457 */           if (r == c) {
/* 458 */             str = String.valueOf(String.valueOf(str)).concat("");
/*     */           }
/* 460 */           else if ((IORTSPEnterPanel.this.matrix[r][c] == Integer.MAX_VALUE) || (IORTSPEnterPanel.this.matrix[r][c] == 0)) {
/* 461 */             str = "";
/*     */           }
/*     */           else
/*     */           {
/* 465 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("".concat(String.valueOf(String.valueOf(IORTSPEnterPanel.this.matrix[r][c]))))));
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 471 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c) {
/* 475 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */     public boolean isCellEditable(int row, int col)
/*     */     {
/* 480 */       if (!IORTSPEnterPanel.this.is_panel_enabled)
/* 481 */         return false;
/* 482 */       if ((col == 0) || (row >= col - 1)) {
/* 483 */         return false;
/*     */       }
/* 485 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private int j;
/*     */     
/*     */ 
/*     */     public void setValueAt(Object value, int row, int col)
/*     */     {
/* 495 */       String input = (String)value;
/* 496 */       if ((col != 0) && (row < col - 1)) {
/* 497 */         if (input.equalsIgnoreCase("")) {
/* 498 */           IORTSPEnterPanel.this.matrix[row][(col - 1)] = 0.0D;
/* 499 */           IORTSPEnterPanel.this.matrix[(col - 1)][row] = 0.0D;
/* 500 */           fireTableCellUpdated(row, col - 1);
/* 501 */           return;
/*     */         }
/*     */         try {
/* 504 */           double tempInput = Double.valueOf(input).doubleValue();
/* 505 */           if (tempInput == 0) {
/* 506 */             return;
/*     */           }
/*     */           
/* 509 */           double d = Double.valueOf(input).doubleValue();
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 518 */           IORTSPEnterPanel.this.matrix[row][(col - 1)] = d;
/* 519 */           IORTSPEnterPanel.this.matrix[(col - 1)][row] = d;
/*     */         }
/*     */         catch (NumberFormatException e) {
/* 522 */           return;
/*     */         }
/*     */         
/* 525 */         fireTableCellUpdated(row, col - 1);
/* 526 */         IORTSPEnterPanel.this.initPrintData();
/*     */       }
/*     */       
/* 529 */       for (int i = 0; i < IORTSPEnterPanel.this.num_city; i++) {
/* 530 */         for (int j = 0; j < IORTSPEnterPanel.this.num_city; j++) {}
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 536 */       IORTSPEnterPanel.this.initPrintData();
/*     */     }
/*     */     
/*     */     private TSPTableModel() {} }
/*     */   
/* 541 */   protected void initializeCurrentStepHelpPanel() { String str = "Enter or Revise a TraveLing Salesman Problem";
/* 542 */     str = String.valueOf(String.valueOf(str)).concat("\n\nEnter the number of cities:");
/* 543 */     str = String.valueOf(String.valueOf(str)).concat("\nEnter the number of cities (a positive integer) in the problem, and then press the Enter key. \nNote: the number of cities cannot exceed 10.");
/* 544 */     str = String.valueOf(String.valueOf(str)).concat("\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/*     */ 
/* 547 */     this.help_content_step.setText(str);
/* 548 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */   protected void initializeCurrentProcedureHelpPanel() {
/* 552 */     String str = "Enter or Revise a TraveLing Salesman Problem";
/* 553 */     str = String.valueOf(String.valueOf(str)).concat("\n\nEnter the number of cities:");
/* 554 */     str = String.valueOf(String.valueOf(str)).concat("\nEnter the number of cities (a positive integer) in the problem, and then press the Enter key. \nNote: the number of cities cannot exceed 10.");
/* 555 */     str = String.valueOf(String.valueOf(str)).concat("\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 557 */     this.help_content_procedure.setText(str);
/* 558 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void SaveProblem()
/*     */   {
/*     */     try
/*     */     {
/* 567 */       for (int i = 0; i < this.tempField.length; i++) {
/* 568 */         this.controller.data.saveSolution[(i + 1)] = this.tempField[i].getValue();
/*     */       }
/*     */       
/* 571 */       Vector p = new Vector();
/* 572 */       p.addElement(new Integer(this.num_city));
/* 573 */       p.addElement(this.matrix);
/* 574 */       p.addElement(this.initSolution);
/*     */       
/* 576 */       IOROperation operation = new IOROperation(60008, p);
/*     */       
/* 578 */       this.controller.solver.doWork(operation);
/*     */     }
/*     */     catch (Exception e1) {
/* 581 */       System.out.println("Save fails");
/*     */     }
/*     */   }
/*     */   
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/* 587 */     initializeData();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTSPEnterPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */