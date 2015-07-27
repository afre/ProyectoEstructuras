/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ 
/*     */ public class IORLPArtificialPanel extends IORLPActionPanel
/*     */ {
/*  30 */   private final int ALGEBRA_FORM = 1;
/*  31 */   private final int TABULAR_FORM = 2;
/*  32 */   private String GREATTHAN = ">=";
/*     */   
/*  34 */   private JTextField tb_coefficient = null;
/*     */   
/*  36 */   private JButton bn_OK = null;
/*     */   
/*  38 */   private JPanel mainPanel = null;
/*  39 */   private JPanel outputPanel = null;
/*  40 */   private JPanel inputPanel = null;
/*  41 */   private JTable tableView = null;
/*  42 */   private IORLPArtificialPanel.IORTableModel tabuModel = null;
/*  43 */   private IORLPArtificialPanel.IORAlgebraModel algbModel = null;
/*  44 */   private JScrollPane scrlPane = null;
/*  45 */   private JPanel tablePanel = new JPanel();
/*     */   private DefaultTableCellRenderer selectedRenderer;
/*  47 */   private JLabel zerolabel = new JLabel();
/*  48 */   private JTextArea statetext = new JTextArea();
/*  49 */   private JLabel inlabel = new JLabel();
/*  50 */   private int dispForm = 2;
/*  51 */   private int curSelRow = 1;
/*     */   
/*     */ 
/*     */ 
/*     */   protected void backStep()
/*     */   {
/*  57 */     if (this.state != null) {
/*  58 */       this.controller.solver.reset();
/*  59 */       this.state.back();
/*  60 */       updatePanel();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finishProcedure()
/*     */   {
/*  69 */     this.controller.setMenuState(11);
/*  70 */     this.actionStatus.setText("The current procedure is finished. Please go to the Procedure menu to continue!");
/*  71 */     this.controller.solver.setBookmark();
/*  72 */     Vector v = new Vector();
/*  73 */     IOROperation op = new IOROperation(10212, v);
/*  74 */     this.controller.solver.doWork(op, this.controller.data);
/*  75 */     setPanelEditable(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPanelEditable(boolean b)
/*     */   {
/*  84 */     this.tb_coefficient.setEditable(b);
/*  85 */     this.bn_OK.setEnabled(b);
/*  86 */     this.bn_back.setEnabled(b);
/*  87 */     setFinishEnabled(b);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updatePanel()
/*     */   {
/*  95 */     this.controller.solver.getData(this.controller.data);
/*     */     
/*  97 */     if (this.state.isEmpty()) {
/*  98 */       this.bn_back.setEnabled(false);
/*     */     } else {
/* 100 */       this.bn_back.setEnabled(true);
/*     */     }
/* 102 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORLPArtificialPanel(IORLPProcessController c)
/*     */   {
/* 113 */     super(c);
/*     */     
/* 115 */     this.state = new IORState(c.solver);
/*     */     
/* 117 */     this.mainPanel = new JPanel();
/* 118 */     add(this.mainPanel, "Center");
/*     */     
/* 120 */     JPanel inputPanel = new JPanel();
/* 121 */     inputPanel.setLayout(new BorderLayout());
/* 122 */     this.statetext.setAlignmentX(0.5F);
/* 123 */     this.statetext.setBackground(getBackground());
/* 124 */     this.statetext.setEditable(false);
/* 125 */     this.statetext.setRows(2);
/* 126 */     this.statetext.setText("Click on an equation which, after being multiplied by the appropriate multiple,\nneeds to be subtracted from Equation 0.");
/*     */     
/*     */ 
/* 129 */     JPanel indiPanel = new JPanel();
/* 130 */     indiPanel.setPreferredSize(new Dimension(500, 50));
/* 131 */     indiPanel.setMaximumSize(new Dimension(500, 50));
/* 132 */     indiPanel.add(this.statetext);
/* 133 */     JPanel statePanel = new JPanel();
/* 134 */     this.inlabel.setText("Please input the multiple: ");
/* 135 */     statePanel.add(this.inlabel);
/* 136 */     this.tb_coefficient = new JTextField(5);
/* 137 */     statePanel.add(this.tb_coefficient);
/*     */     
/* 139 */     this.bn_OK = new JButton("OK");
/* 140 */     statePanel.add(this.bn_OK, "South");
/* 141 */     this.bn_OK.addActionListener(new ActionListener() {
/* 142 */       public void actionPerformed(ActionEvent e) { IORLPArtificialPanel.this.doOK(); }
/* 143 */     });
/* 144 */     inputPanel.add(indiPanel, "Center");
/* 145 */     inputPanel.add(statePanel, "South");
/*     */     
/* 147 */     this.outputPanel = new JPanel();
/*     */     
/*     */ 
/* 150 */     this.dispForm = this.controller.data.DisplayForm;
/*     */     
/*     */ 
/* 153 */     String xstr = " ";
/* 154 */     for (int i = 1; i < this.controller.data.num_variable + 1; i++) {
/* 155 */       if (this.controller.data.is_artificial[i] != 0) {
/* 156 */         xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("X").append(i).append(this.GREATTHAN).append("0"))))));
/*     */       } else
/* 158 */         xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("x").append(i).append(this.GREATTHAN).append("0"))))));
/* 159 */       if (i < this.controller.data.num_variable)
/* 160 */         xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*     */     }
/* 162 */     this.zerolabel.setText(xstr);
/*     */     
/*     */ 
/* 165 */     this.selectedRenderer = new DefaultTableCellRenderer() {
/*     */       public void setValue(Object value) {
/* 167 */         setBackground(Color.white);
/* 168 */         super.setValue(value);
/*     */       }
/*     */       
/* 171 */     };
/* 172 */     this.tabuModel = new IORLPArtificialPanel.IORTableModel();
/* 173 */     this.algbModel = new IORLPArtificialPanel.IORAlgebraModel();
/* 174 */     this.tablePanel.setLayout(new BoxLayout(this.tablePanel, 1));
/* 175 */     this.tablePanel.setBorder(new javax.swing.border.EtchedBorder());
/* 176 */     if (this.dispForm == 2) {
/* 177 */       createTable();
/* 178 */       this.scrlPane = new JScrollPane(this.tableView);
/* 179 */       this.outputPanel.add(this.scrlPane);
/*     */     }
/*     */     else {
/* 182 */       createTable();
/* 183 */       this.tablePanel.add(this.tableView);
/* 184 */       this.tablePanel.add(Box.createVerticalStrut(10));
/* 185 */       this.tablePanel.add(this.zerolabel);
/* 186 */       this.tablePanel.add(Box.createVerticalStrut(20));
/* 187 */       this.outputPanel.add(this.tablePanel);
/*     */     }
/*     */     
/* 190 */     this.mainPanel.setLayout(new BorderLayout());
/* 191 */     this.mainPanel.add(inputPanel, "South");
/* 192 */     this.mainPanel.add(this.outputPanel, "Center");
/*     */   }
/*     */   
/*     */   class IORTableModel extends AbstractTableModel
/*     */   {
/*     */     private int numVars;
/*     */     private String weirdstr;
/*     */     
/*     */     public IORTableModel() {
/* 201 */       this.numVars = IORLPArtificialPanel.this.controller.data.num_variable;
/*     */     }
/*     */     
/*     */     public int getColumnCount() {
/* 205 */       return IORLPArtificialPanel.this.controller.data.num_variable + 4;
/*     */     }
/*     */     
/*     */     public int getRowCount() {
/* 209 */       return IORLPArtificialPanel.this.controller.data.num_constrain + 1;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col) {
/* 213 */       if (col == 0)
/* 214 */         return "Bas Var";
/* 215 */       if (col == 1)
/* 216 */         return "Eq.";
/* 217 */       if (col == 2)
/* 218 */         return "Z";
/* 219 */       if ((col >= 3) && (col < 3 + this.numVars)) {
/* 220 */         if (IORLPArtificialPanel.this.controller.data.is_artificial[(col - 2)] != 0) {
/* 221 */           this.weirdstr = "X".concat(String.valueOf(String.valueOf(col - 2)));
/* 222 */           return this.weirdstr;
/*     */         }
/*     */         
/* 225 */         return "x".concat(String.valueOf(String.valueOf(col - 2)));
/*     */       }
/*     */       
/* 228 */       return "Right Side";
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col) {
/* 232 */       int xsub = 0;
/*     */       
/*     */ 
/* 235 */       if (col == 1)
/* 236 */         return Integer.toString(row);
/* 237 */       if (col == 2) {
/* 238 */         if ((row == 0) && (IORLPArtificialPanel.this.controller.data.objective_is_max))
/* 239 */           return "1";
/* 240 */         if (row == 0) {
/* 241 */           return "-1";
/*     */         }
/* 243 */         return "0";
/*     */       }
/* 245 */       if (col == 0) {
/* 246 */         if (row == 0) {
/* 247 */           return "Z";
/*     */         }
/* 249 */         xsub = IORLPArtificialPanel.this.controller.data.basic_variable_index[row];
/* 250 */         if (IORLPArtificialPanel.this.controller.data.is_artificial[xsub] != 0) {
/* 251 */           return "X".concat(String.valueOf(String.valueOf(xsub)));
/*     */         }
/* 253 */         return "x".concat(String.valueOf(String.valueOf(xsub)));
/*     */       }
/*     */       
/* 256 */       if ((col >= 3) && (col < this.numVars + 3)) {
/* 257 */         if (row == 0) {
/* 258 */           double mc = IORLPArtificialPanel.this.controller.data.objective_M_coefficient[(col - 2)];
/* 259 */           double ec = IORLPArtificialPanel.this.controller.data.objective_coefficient[(col - 2)];
/* 260 */           return new IORTableCell(mc, ec);
/*     */         }
/*     */         
/* 263 */         return new IORTableCell(IORLPArtificialPanel.this.controller.data.constrain_coefficient[row][(col - 2)]);
/*     */       }
/*     */       
/* 266 */       if (row == 0) {
/* 267 */         double mc = IORLPArtificialPanel.this.controller.data.objective_M_coefficient[0];
/* 268 */         double ec = IORLPArtificialPanel.this.controller.data.objective_coefficient[0];
/* 269 */         return new IORTableCell(mc, ec);
/*     */       }
/*     */       
/* 272 */       return new IORTableCell(IORLPArtificialPanel.this.controller.data.constrain_coefficient[row][0]);
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c)
/*     */     {
/* 277 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean isCellEditable(int row, int col)
/*     */     {
/* 283 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   class IORAlgebraModel extends AbstractTableModel {
/*     */     private int i;
/*     */     private int j;
/* 290 */     private IORTableCell[][] data = new IORTableCell[IORLPArtificialPanel.this.controller.data.num_constrain + 2][IORLPArtificialPanel.this.controller.data.num_variable + 2];
/*     */     
/*     */     public IORAlgebraModel() {
/* 293 */       for (this.i = 0; this.i < IORLPArtificialPanel.this.controller.data.num_constrain + 2; this.i += 1) {
/* 294 */         for (this.j = 0; this.j < IORLPArtificialPanel.this.controller.data.num_variable + 2; this.j += 1) {
/* 295 */           this.data[this.i][this.j] = new IORTableCell(0.0D, 0.0D);
/* 296 */           this.data[this.i][this.j].setDisplayForm(1);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public int getColumnCount() {
/* 302 */       return IORLPArtificialPanel.this.controller.data.num_variable + 4;
/*     */     }
/*     */     
/*     */     public int getRowCount() {
/* 306 */       return IORLPArtificialPanel.this.controller.data.num_constrain + 1;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col) {
/* 310 */       if (col == 0)
/* 311 */         return "empty";
/* 312 */       if (col == 1)
/* 313 */         return "Eq.";
/* 314 */       if (col == 2)
/* 315 */         return "Z";
/* 316 */       if ((col >= 3) && (col < 3 + IORLPArtificialPanel.this.controller.data.num_variable)) {
/* 317 */         return "x".concat(String.valueOf(String.valueOf(col - 2)));
/*     */       }
/*     */       
/* 320 */       return "Right Side";
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col) {
/* 324 */       int xsub = 0;
/*     */       
/*     */ 
/* 327 */       if (col == 0)
/* 328 */         return " ";
/* 329 */       if (col == 1) {
/* 330 */         return String.valueOf(String.valueOf(row)).concat(")");
/*     */       }
/* 332 */       if (col == 2) {
/* 333 */         if (row == 0) {
/* 334 */           if (IORLPArtificialPanel.this.controller.data.original_objective_is_max) {
/* 335 */             return "Z";
/*     */           }
/* 337 */           return "-Z";
/*     */         }
/*     */         
/* 340 */         return " ";
/*     */       }
/*     */       
/* 343 */       if ((col >= 3) && (col < IORLPArtificialPanel.this.controller.data.num_variable + 3)) {
/* 344 */         if (row == 0) {
/* 345 */           double mc = IORLPArtificialPanel.this.controller.data.objective_M_coefficient[(col - 2)];
/* 346 */           double ec = IORLPArtificialPanel.this.controller.data.objective_coefficient[(col - 2)];
/* 347 */           this.data[row][(col - 3)].setPara(mc, ec);
/* 348 */           if (IORLPArtificialPanel.this.controller.data.is_artificial[(col - 2)] != 0) {
/* 349 */             this.data[row][(col - 3)].setXstring("X".concat(String.valueOf(String.valueOf(col - 2))));
/*     */           } else
/* 351 */             this.data[row][(col - 3)].setXstring("x".concat(String.valueOf(String.valueOf(col - 2))));
/* 352 */           return this.data[row][(col - 3)];
/*     */         }
/*     */         
/* 355 */         this.data[row][(col - 3)].setPara(0.0D, IORLPArtificialPanel.this.controller.data.constrain_coefficient[row][(col - 2)]);
/* 356 */         if (IORLPArtificialPanel.this.controller.data.is_artificial[(col - 2)] != 0) {
/* 357 */           this.data[row][(col - 3)].setXstring("X".concat(String.valueOf(String.valueOf(col - 2))));
/*     */         } else
/* 359 */           this.data[row][(col - 3)].setXstring("x".concat(String.valueOf(String.valueOf(col - 2))));
/* 360 */         return this.data[row][(col - 3)];
/*     */       }
/*     */       
/*     */ 
/* 364 */       if (row == 0) {
/* 365 */         double mc = IORLPArtificialPanel.this.controller.data.objective_M_coefficient[0];
/* 366 */         double ec = IORLPArtificialPanel.this.controller.data.objective_coefficient[0];
/* 367 */         this.data[row][(col - 3)].setPara(mc, ec);
/* 368 */         this.data[row][(col - 3)].setXstring(" = ");
/* 369 */         return this.data[row][(col - 3)];
/*     */       }
/*     */       
/* 372 */       this.data[row][(col - 3)].setPara(0.0D, IORLPArtificialPanel.this.controller.data.constrain_coefficient[row][0]);
/* 373 */       this.data[row][(col - 3)].setXstring(" = ");
/* 374 */       return this.data[row][(col - 3)];
/*     */     }
/*     */     
/*     */ 
/*     */     public Class getColumnClass(int c)
/*     */     {
/* 380 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean isCellEditable(int row, int col)
/*     */     {
/* 386 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private JTable createTable()
/*     */   {
/* 396 */     if (this.dispForm == 2) {
/* 397 */       this.tableView = new JTable(this.tabuModel);
/*     */     }
/*     */     else {
/* 400 */       this.tableView = new JTable(this.algbModel);
/* 401 */       this.tableView.setShowGrid(false);
/*     */     }
/* 403 */     for (int i = 0; i < 3; i++) {
/* 404 */       this.tableView.getColumn(this.tableView.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/* 405 */       this.tableView.getColumn(this.tableView.getColumnName(i)).setPreferredWidth(30);
/*     */     }
/* 407 */     this.tableView.setColumnSelectionAllowed(false);
/* 408 */     this.tableView.setRowSelectionAllowed(true);
/* 409 */     this.tableView.setCellSelectionEnabled(false);
/* 410 */     this.tableView.setSelectionMode(0);
/*     */     
/* 412 */     this.tableView.setRowSelectionInterval(this.curSelRow, this.curSelRow);
/*     */     
/*     */ 
/* 415 */     ListSelectionModel rowSM = this.tableView.getSelectionModel();
/* 416 */     rowSM.addListSelectionListener(new ListSelectionListener() {
/*     */       public void valueChanged(ListSelectionEvent e) {
/* 418 */         ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/* 419 */         if (!lsm.isSelectionEmpty())
/*     */         {
/*     */ 
/* 422 */           int selectedRow = IORLPArtificialPanel.this.tableView.getSelectedRow();
/* 423 */           if (selectedRow <= 0) {
/* 424 */             IORLPArtificialPanel.this.tableView.setRowSelectionInterval(IORLPArtificialPanel.this.curSelRow, IORLPArtificialPanel.this.curSelRow);
/*     */           } else {
/* 426 */             IORLPArtificialPanel.this.curSelRow = selectedRow;
/*     */           }
/*     */         }
/*     */       }
/* 430 */     });
/* 431 */     ListSelectionModel colSM = this.tableView.getColumnModel().getSelectionModel();
/* 432 */     colSM.addListSelectionListener(new ListSelectionListener() {
/*     */       public void valueChanged(ListSelectionEvent e) {
/* 434 */         ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/* 435 */         if (!lsm.isSelectionEmpty())
/*     */         {
/*     */ 
/* 438 */           if ((IORLPArtificialPanel.this.tableView.getSelectedColumn() <= 2) && 
/* 439 */             (IORLPArtificialPanel.this.dispForm == 2)) {
/* 440 */             IORLPArtificialPanel.this.tableView.setColumnSelectionInterval(3, 3);
/*     */           }
/*     */         }
/*     */       }
/* 444 */     });
/* 445 */     this.tableView.setPreferredScrollableViewportSize(new Dimension(500, 100));
/*     */     
/* 447 */     return this.tableView;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void switchForm(int newform)
/*     */   {
/* 457 */     if ((newform < 1) || (newform > 2)) {
/* 458 */       return;
/*     */     }
/* 460 */     if (newform != this.dispForm) {
/* 461 */       this.dispForm = newform;
/* 462 */       if (newform == 2) {
/* 463 */         this.tableView.setModel(this.tabuModel);
/* 464 */         this.tableView.setShowGrid(true);
/* 465 */         for (int i = 0; i < 3; i++) {
/* 466 */           this.tableView.getColumn(this.tableView.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/* 467 */           this.tableView.getColumn(this.tableView.getColumnName(i)).setPreferredWidth(30);
/*     */         }
/* 469 */         this.tableView.setRowSelectionInterval(this.curSelRow, this.curSelRow);
/* 470 */         this.tableView.revalidate();
/* 471 */         this.tableView.repaint();
/*     */         
/* 473 */         this.scrlPane = new JScrollPane(this.tableView);
/* 474 */         this.outputPanel.removeAll();
/* 475 */         this.outputPanel.add(this.scrlPane);
/*     */       }
/* 477 */       if (newform == 1) {
/* 478 */         this.tableView.setModel(this.algbModel);
/* 479 */         this.tableView.setShowGrid(false);
/* 480 */         for (int i = 0; i < 3; i++) {
/* 481 */           this.tableView.getColumn(this.tableView.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/* 482 */           this.tableView.getColumn(this.tableView.getColumnName(i)).setPreferredWidth(30);
/*     */         }
/* 484 */         this.tableView.setRowSelectionInterval(this.curSelRow, this.curSelRow);
/* 485 */         this.tableView.revalidate();
/* 486 */         this.tableView.repaint();
/* 487 */         this.tablePanel.removeAll();
/* 488 */         this.tablePanel.add(this.tableView);
/* 489 */         this.tablePanel.add(Box.createVerticalStrut(10));
/* 490 */         this.tablePanel.add(this.zerolabel);
/* 491 */         this.tablePanel.add(Box.createVerticalStrut(20));
/* 492 */         this.outputPanel.removeAll();
/* 493 */         this.outputPanel.add(this.tablePanel);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void doOK() {
/* 499 */     String s = this.tb_coefficient.getText();
/* 500 */     int nRow = this.tableView.getSelectedRow();
/*     */     
/* 502 */     double dm = 0.0D;double dc = 0.0D;
/*     */     
/* 504 */     if (nRow <= 0) {
/* 505 */       this.actionStatus.setText("Please select constraint equation.");
/* 506 */       return;
/*     */     }
/* 508 */     if (s.length() == 0) {
/* 509 */       this.actionStatus.setText("Please input multiple.");
/* 510 */       return;
/*     */     }
/*     */     
/* 513 */     s = s.toLowerCase();
/* 514 */     int mindex = s.indexOf('m');
/* 515 */     if (mindex == -1) {
/*     */       try {
/* 517 */         dc = Double.parseDouble(s);
/*     */       } catch (NumberFormatException e) {
/* 519 */         this.actionStatus.setText("Please input valid multiple.");
/* 520 */         return;
/*     */       }
/*     */     }
/* 523 */     else if (mindex == 0) {
/* 524 */       dm = 1.0D;
/* 525 */       if (s.length() > 1) {
/*     */         try {
/* 527 */           dc = Double.parseDouble(s.substring(1, s.length()));
/*     */         } catch (NumberFormatException e) {
/* 529 */           this.actionStatus.setText("Please input valid multiple.");
/* 530 */           return;
/*     */         }
/*     */       }
/*     */     }
/* 534 */     else if (mindex == s.length() - 1) {
/*     */       try {
/* 536 */         dm = Double.parseDouble(s.substring(0, s.length() - 1));
/*     */       } catch (NumberFormatException e) {
/* 538 */         this.actionStatus.setText("Please input valid multiple.");
/* 539 */         return;
/*     */       }
/*     */     }
/*     */     else {
/*     */       try {
/* 544 */         dm = Double.parseDouble(s.substring(0, mindex));
/*     */       } catch (NumberFormatException e) {
/* 546 */         this.actionStatus.setText("Please input valid multiple.");
/* 547 */         return;
/*     */       }
/*     */       try {
/* 550 */         dc = Double.parseDouble(s.substring(mindex + 1, s.length()));
/*     */       } catch (NumberFormatException e) {
/* 552 */         this.actionStatus.setText("Please input valid multiple.");
/* 553 */         return;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 558 */     Vector p = new Vector();
/*     */     
/* 560 */     p.addElement(new Integer(nRow));
/*     */     
/* 562 */     p.addElement(new Double(dm));
/*     */     
/* 564 */     p.addElement(new Double(dc));
/*     */     
/* 566 */     IOROperation operation = new IOROperation(10213, p);
/*     */     
/*     */ 
/* 569 */     if (operation != null) {
/* 570 */       if (this.controller.solver.doWork(operation)) {
/* 571 */         this.state.addStep(operation);
/* 572 */         updatePanel();
/*     */       }
/*     */       else
/*     */       {
/* 576 */         JOptionPane.showMessageDialog(this, this.controller.solver.getErrInfo());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 585 */     String str = "Set Up for the Simplex Method - Interactive Only\n\n";
/* 586 */     str = String.valueOf(String.valueOf(str)).concat("This procedure is used to implement the initialization step of the Simplex ");
/* 587 */     str = String.valueOf(String.valueOf(str)).concat("method as described in Secs. 4.2 and 4.6. The linear programming model is ");
/* 588 */     str = String.valueOf(String.valueOf(str)).concat("augmented as needed to be able to apply the Simplex Method as it is presented ");
/* 589 */     str = String.valueOf(String.valueOf(str)).concat("in Chapter 4. The converted model must have the following properties:\n");
/* 590 */     str = String.valueOf(String.valueOf(str)).concat("     1.  The objective needs to be to maximize Z or (-Z).\n");
/* 591 */     str = String.valueOf(String.valueOf(str)).concat("     2.  Each functional constraint needs to be in equality form.\n");
/* 592 */     str = String.valueOf(String.valueOf(str)).concat("     3.  Each functional constraint needs an initial basic variable.\n");
/* 593 */     str = String.valueOf(String.valueOf(str)).concat("     4.  Each functional constraint needs a nonnegative right-hand side.\n");
/* 594 */     str = String.valueOf(String.valueOf(str)).concat("     5.  Each variable needs to have a nonnegativity constraint.\n");
/* 595 */     str = String.valueOf(String.valueOf(str)).concat("If you introduce artificial variables, you will have the choice of dealing with ");
/* 596 */     str = String.valueOf(String.valueOf(str)).concat("them with either the Big M method or the Two-Phase method. To correct a mistake, ");
/* 597 */     str = String.valueOf(String.valueOf(str)).concat("press the BACK button to backtrack.\n\n");
/* 598 */     str = String.valueOf(String.valueOf(str)).concat("Converted to Proper Form from Gaussian Elimination\n\n");
/* 599 */     str = String.valueOf(String.valueOf(str)).concat("The simplex tableau needs to be converted to proper form from Gaussian ");
/* 600 */     str = String.valueOf(String.valueOf(str)).concat("elimination before it can be operated on by using the Simplex Method (i.e., the ");
/* 601 */     str = String.valueOf(String.valueOf(str)).concat("coefficients of the basic variables in Eq. 0 need to be equal to 0). If the ");
/* 602 */     str = String.valueOf(String.valueOf(str)).concat("tableau is in this form, you can go directly to applying the Simplex Method by ");
/* 603 */     str = String.valueOf(String.valueOf(str)).concat("choosing \"Solve Interactively by the Simplex Method\" under the Procedure menu. ");
/* 604 */     str = String.valueOf(String.valueOf(str)).concat("Otherwise, a multiple of some equation needs to be subtracted from Equation 0. ");
/* 605 */     str = String.valueOf(String.valueOf(str)).concat("The number you enter can be an integer or a decimal number. If you ");
/* 606 */     str = String.valueOf(String.valueOf(str)).concat("would like to enter a Big M number, simply type M or m (e.g., 2M+3 or 2m+3). ");
/* 607 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure. ");
/*     */     
/* 609 */     this.help_content_step.setText(str);
/* 610 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 617 */     String str = "Set up for the Simplex Method - Interactive Only\n\n";
/* 618 */     str = String.valueOf(String.valueOf(str)).concat("Once you have entered (or revised) a linear programming model, this routine ");
/* 619 */     str = String.valueOf(String.valueOf(str)).concat("enables you to quickly set up the model in preparation for solving it ");
/* 620 */     str = String.valueOf(String.valueOf(str)).concat("interactively by the Simplex Method. In particular, you will be introducing ");
/* 621 */     str = String.valueOf(String.valueOf(str)).concat("slack variables as described in Sec.4.2 and, if needed, adapting to nonstandard ");
/* 622 */     str = String.valueOf(String.valueOf(str)).concat("forms as described in Sec.4.6. When you finish adding slack variables, you will ");
/* 623 */     str = String.valueOf(String.valueOf(str)).concat("have a choice. If you introduced no artificial variables, then you should next ");
/* 624 */     str = String.valueOf(String.valueOf(str)).concat("choose the 'Solve Interactively by the Simples Method' routine. If one or more ");
/* 625 */     str = String.valueOf(String.valueOf(str)).concat("artificial variables have been introduced, then you will be asked whether you ");
/* 626 */     str = String.valueOf(String.valueOf(str)).concat("would like to use the Big M or Two-Phase method to handle them. In the latter case, ");
/* 627 */     str = String.valueOf(String.valueOf(str)).concat("you will be asked to enter a new objective function for Phase 1. In either case, ");
/* 628 */     str = String.valueOf(String.valueOf(str)).concat("you will then enter a routine used to eliminate basic variables from equation 0. ");
/* 629 */     str = String.valueOf(String.valueOf(str)).concat("Finally, when you have finished this process, you may choose the 'Solve Interactively ");
/* 630 */     str = String.valueOf(String.valueOf(str)).concat("by the Simplex Method' routine under the Procedure menu.");
/* 631 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 633 */     this.help_content_procedure.setText(str);
/* 634 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/*     */     try
/*     */     {
/* 645 */       this.state = ((IORState)in.readObject());
/* 646 */       rowSel = ((Integer)in.readObject()).intValue();
/*     */     } catch (Exception e) {
/*     */       int rowSel;
/* 649 */       System.out.println("Load file fails"); return;
/*     */     }
/*     */     
/*     */     int rowSel;
/* 653 */     if (rowSel > 0) {
/* 654 */       this.tableView.setRowSelectionInterval(rowSel, rowSel);
/*     */     }
/*     */     
/* 657 */     if (this.bn_back.isVisible()) {
/* 658 */       if ((this.state == null) || (this.state.isEmpty())) {
/* 659 */         this.bn_back.setEnabled(false);
/*     */       } else {
/* 661 */         this.bn_back.setEnabled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/*     */     try
/*     */     {
/* 672 */       out.writeObject(this.state);
/* 673 */       out.writeObject(new Integer(this.tableView.getSelectedRow()));
/*     */     }
/*     */     catch (Exception e) {
/* 676 */       System.out.println("Save file fails");
/* 677 */       return;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPArtificialPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */