/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Vector;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.DefaultCellEditor;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.TableCellEditor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORMKDEnterPanel
/*     */   extends IORActionPanel
/*     */ {
/*  31 */   private final int MAXSTATES = 4;
/*  32 */   private final int MAXDECISIONS = 5;
/*  33 */   private String LESSTHAN = "<=";
/*     */   
/*  35 */   private IORMDProcessController controller = null;
/*     */   
/*  37 */   private JPanel mainPanel = new JPanel();
/*     */   
/*  39 */   private JPanel numPanel = null;
/*  40 */   private JPanel namePanel = null;
/*  41 */   private JPanel fieldPanel = null;
/*  42 */   private JPanel buttPanel = new JPanel();
/*  43 */   private JPanel upPanel = null;
/*  44 */   private JPanel bottomPanel = null;
/*  45 */   private JPanel disctPanel = null;
/*     */   
/*  47 */   private JScrollPane costPane = null;
/*  48 */   private JScrollPane probPane = null;
/*  49 */   private JScrollPane desPane = null;
/*     */   
/*  51 */   private JButton backbutt = new JButton("Back");
/*  52 */   private JButton nextbutt = new JButton("Next");
/*     */   
/*  54 */   private IORMKDEnterPanel.IORCostTableModel costModel = null;
/*     */   private JTable costTable;
/*  56 */   private IORMKDEnterPanel.IORProbTableModel probModel = null;
/*     */   private JTable probTable;
/*  58 */   private IORMKDEnterPanel.IORDesTableModel desModel = null;
/*     */   
/*     */   private JTable desTable;
/*  61 */   private DecimalFormat decfm = new DecimalFormat();
/*     */   
/*  63 */   private DecimalField disctfd = new DecimalField(1.0D, 4, this.decfm);
/*  64 */   private WholeNumberField NumState = new WholeNumberField(1, 2);
/*  65 */   private WholeNumberField NumDecision = new WholeNumberField(1, 2);
/*     */   
/*  67 */   private JLabel stlabel = new JLabel(String.valueOf(String.valueOf(new StringBuffer("Enter the number of states ( ").append(this.LESSTHAN).append("4 ) : "))));
/*  68 */   private JLabel deslabel = new JLabel(String.valueOf(String.valueOf(new StringBuffer("Enter the number of possible decisions ( ").append(this.LESSTHAN).append("5 ) : "))));
/*  69 */   private JLabel indilabel = new JLabel("Enter \"1m\" to give an infinite cost , for infeasible combinations of i and k.");
/*  70 */   private JLabel ctlabel = new JLabel("Enter the cost matrix : ");
/*  71 */   private JLabel klabel = new JLabel();
/*  72 */   private JLabel polabel = new JLabel("Enter the initial trial policy : ");
/*  73 */   private JLabel nstlabel = new JLabel();
/*  74 */   private JLabel ndelabel = new JLabel();
/*     */   
/*  76 */   private double[][] cost = new double[4][5];
/*  77 */   private double[][][] p = new double[5][4][4];
/*  78 */   private int[] des = new int[4];
/*     */   
/*  80 */   private int step = 1;
/*  81 */   private int nState = 1; private int nDecision = 1;
/*     */   
/*     */   private int selected_k;
/*     */   private int current_k;
/*  85 */   private Vector opseq = new Vector();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORMKDEnterPanel(IORMDProcessController c)
/*     */   {
/*  95 */     super(c);
/*     */     
/*     */ 
/*  98 */     for (int i = 0; i < this.des.length; i++) {
/*  99 */       this.des[i] = 1;
/*     */     }
/* 101 */     this.controller = c;
/* 102 */     this.state = new IORState(this.controller.solver);
/* 103 */     add(this.mainPanel);
/* 104 */     this.bn_back.setVisible(false);
/* 105 */     this.actionStatus.setText(" ");
/*     */     
/* 107 */     this.decfm.setGroupingUsed(false);
/* 108 */     this.indilabel.setForeground(Color.blue);
/*     */     
/* 110 */     this.buttPanel.add(this.backbutt);
/* 111 */     this.buttPanel.add(Box.createHorizontalStrut(10));
/* 112 */     this.buttPanel.add(this.nextbutt);
/* 113 */     this.backbutt.setEnabled(false);
/*     */     
/* 115 */     this.namePanel = new JPanel();
/* 116 */     this.namePanel.setLayout(new BoxLayout(this.namePanel, 1));
/* 117 */     this.namePanel.add(this.stlabel);
/* 118 */     this.namePanel.add(Box.createVerticalStrut(5));
/* 119 */     this.namePanel.add(this.deslabel);
/*     */     
/* 121 */     this.fieldPanel = new JPanel();
/* 122 */     this.fieldPanel.setLayout(new BoxLayout(this.fieldPanel, 1));
/* 123 */     this.fieldPanel.add(this.NumState);
/* 124 */     this.fieldPanel.add(Box.createVerticalStrut(3));
/* 125 */     this.fieldPanel.add(this.NumDecision);
/*     */     
/* 127 */     this.numPanel = new JPanel();
/* 128 */     this.numPanel.add(this.namePanel);
/* 129 */     this.numPanel.add(this.fieldPanel);
/*     */     
/* 131 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/* 132 */     this.mainPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
/* 133 */     this.mainPanel.add(this.numPanel);
/* 134 */     this.mainPanel.add(Box.createVerticalGlue());
/* 135 */     this.mainPanel.add(this.buttPanel);
/*     */     
/* 137 */     this.ctlabel.setAlignmentX(0.5F);
/* 138 */     this.klabel.setAlignmentX(0.5F);
/* 139 */     this.indilabel.setAlignmentX(0.5F);
/* 140 */     this.polabel.setAlignmentX(0.5F);
/* 141 */     this.nstlabel.setAlignmentX(0.5F);
/* 142 */     this.ndelabel.setAlignmentX(0.5F);
/*     */     
/* 144 */     this.disctPanel = new JPanel();
/* 145 */     this.disctPanel.add(new JLabel("Discount Factor : "));
/* 146 */     this.disctPanel.add(this.disctfd);
/* 147 */     this.disctPanel.add(new JLabel("(ignored for average cost algorithm)"));
/*     */     
/* 149 */     this.upPanel = new JPanel();
/* 150 */     this.upPanel.setLayout(new BoxLayout(this.upPanel, 1));
/*     */     
/* 152 */     this.bottomPanel = new JPanel();
/* 153 */     this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel, 1));
/*     */     
/* 155 */     this.costModel = new IORMKDEnterPanel.IORCostTableModel();
/* 156 */     this.costTable = new JTable(this.costModel);
/* 157 */     this.costTable.setCellSelectionEnabled(true);
/* 158 */     this.costTable.setRowHeight(20);
/* 159 */     this.costPane = new JScrollPane(this.costTable);
/* 160 */     this.costPane.setAlignmentX(0.5F);
/*     */     
/* 162 */     this.probModel = new IORMKDEnterPanel.IORProbTableModel();
/* 163 */     this.probTable = new JTable(this.probModel);
/* 164 */     this.probTable.setCellSelectionEnabled(true);
/* 165 */     this.probTable.setRowHeight(20);
/* 166 */     this.probPane = new JScrollPane(this.probTable);
/* 167 */     this.probPane.setAlignmentX(0.5F);
/*     */     
/* 169 */     this.desModel = new IORMKDEnterPanel.IORDesTableModel();
/* 170 */     this.desTable = new JTable(this.desModel);
/* 171 */     this.desTable.setCellSelectionEnabled(true);
/* 172 */     this.desTable.setRowHeight(20);
/* 173 */     this.desPane = new JScrollPane(this.desTable);
/* 174 */     this.desPane.setAlignmentX(0.5F);
/*     */     
/* 176 */     setupTableEditor();
/*     */     
/*     */ 
/* 179 */     this.nextbutt.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 181 */         IORMKDEnterPanel.this.doNext();
/*     */       }
/* 183 */     });
/* 184 */     this.backbutt.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 186 */         IORMKDEnterPanel.this.doBack();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */   private void setupTableEditor()
/*     */   {
/* 194 */     DecimalField varField = new DecimalField(0.0D, 4, this.decfm);
/* 195 */     DecimalField decmField = new DecimalField(0.0D, 4, this.decfm);
/* 196 */     WholeNumberField intField = new WholeNumberField(1, 4);
/*     */     
/* 198 */     DefaultCellEditor varEditor = new DefaultCellEditor((IORMKDEnterPanel)this) { private final DecimalField val$varField;
/*     */       
/* 200 */       public Object getCellEditorValue() { if (this.val$varField.getText().equals("1m")) {
/* 201 */           return "1m";
/*     */         }
/* 203 */         return IORMKDEnterPanel.this.decfm.format(this.val$varField.getValue());
/*     */       }
/* 205 */     };
/* 206 */     this.costTable.setDefaultEditor(String.class, varEditor);
/*     */     
/* 208 */     DefaultCellEditor decmEditor = new DefaultCellEditor((IORMKDEnterPanel)this) { private final DecimalField val$decmField;
/*     */       
/* 210 */       public Object getCellEditorValue() { return IORMKDEnterPanel.this.decfm.format(this.val$decmField.getValue());
/*     */       }
/* 212 */     };
/* 213 */     this.probTable.setDefaultEditor(String.class, decmEditor);
/*     */     
/* 215 */     DefaultCellEditor intEditor = new DefaultCellEditor((IORMKDEnterPanel)this) { private final WholeNumberField val$intField;
/*     */       
/* 217 */       public Object getCellEditorValue() { return IORMKDEnterPanel.this.decfm.format(this.val$intField.getValue());
/*     */       }
/* 219 */     };
/* 220 */     this.desTable.setDefaultEditor(String.class, intEditor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finishProcedure()
/*     */   {
/* 231 */     Vector par = new Vector();
/* 232 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/*     */ 
/* 235 */     this.desTable.getDefaultEditor(String.class).stopCellEditing();
/* 236 */     this.desTable.clearSelection();
/*     */     
/*     */ 
/* 239 */     for (int i = 0; i < this.nState; i++) {
/* 240 */       if ((this.des[i] < 1) || (this.des[i] > this.nDecision)) {
/* 241 */         JOptionPane.showMessageDialog(this, "Invalid initial policy!");
/* 242 */         return;
/*     */       }
/*     */     }
/* 245 */     par.addElement(this.des);
/* 246 */     par.addElement(new Double(this.disctfd.getValue()));
/* 247 */     opr.parameters = par;
/* 248 */     opr.operation_code = 40104;
/* 249 */     if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 250 */       String err = this.controller.solver.getErrInfo();
/* 251 */       JOptionPane.showMessageDialog(this, err);
/* 252 */       return;
/*     */     }
/*     */     
/*     */ 
/* 256 */     this.bn_finish.setEnabled(false);
/*     */     
/* 258 */     this.controller.enableMenus();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updatePanel() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void doBack()
/*     */   {
/* 278 */     if (this.opseq.isEmpty() == false) {
/* 279 */       int last = ((Integer)this.opseq.lastElement()).intValue();
/* 280 */       this.opseq.removeElementAt(this.opseq.size() - 1);
/* 281 */       this.nextbutt.setEnabled(true);
/* 282 */       this.bn_finish.setEnabled(true);
/*     */       
/* 284 */       switch (last)
/*     */       {
/*     */       case 1: 
/* 287 */         this.controller.solver.reset();
/* 288 */         this.state.back();
/* 289 */         this.controller.solver.getData(this.controller.data);
/*     */         
/*     */ 
/* 292 */         this.costTable.getDefaultEditor(String.class).stopCellEditing();
/* 293 */         this.costTable.clearSelection();
/*     */         
/* 295 */         this.mainPanel.removeAll();
/* 296 */         this.mainPanel.add(this.numPanel);
/* 297 */         this.mainPanel.add(Box.createVerticalGlue());
/* 298 */         this.mainPanel.add(this.buttPanel);
/* 299 */         this.step = 1;
/* 300 */         this.backbutt.setEnabled(false);
/*     */         
/* 302 */         revalidate();
/* 303 */         repaint();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 355 */         break;
/*     */       case 2: 
/* 307 */         this.controller.solver.reset();
/* 308 */         this.state.back();
/* 309 */         this.controller.solver.getData(this.controller.data);
/*     */         
/*     */ 
/* 312 */         this.probTable.getDefaultEditor(String.class).stopCellEditing();
/* 313 */         this.probTable.clearSelection();
/* 314 */         this.bottomPanel.removeAll();
/* 315 */         this.bottomPanel.add(this.ctlabel);
/* 316 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/* 317 */         this.bottomPanel.add(this.costPane);
/* 318 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/* 319 */         this.indilabel.setText("Enter \"1m\" to give an infinite cost , for infeasible combinations of i and k.");
/* 320 */         this.bottomPanel.add(this.indilabel);
/* 321 */         this.step = 2;
/*     */         
/* 323 */         revalidate();
/* 324 */         repaint();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 355 */         break;
/*     */       case 3: 
/* 328 */         this.controller.solver.reset();
/* 329 */         this.state.back();
/* 330 */         this.controller.solver.getData(this.controller.data);
/*     */         
/*     */ 
/* 333 */         this.actionStatus.setText(" ");
/* 334 */         this.probTable.getDefaultEditor(String.class).stopCellEditing();
/* 335 */         this.probTable.clearSelection();
/* 336 */         this.current_k -= 1;
/* 337 */         this.selected_k -= 1;
/* 338 */         this.probModel.fireTableDataChanged();
/* 339 */         this.probTable.clearSelection();
/* 340 */         this.klabel.setText("Enter the transition matrix , Pij(k) , for k = ".concat(String.valueOf(String.valueOf(this.current_k))));
/* 341 */         this.bottomPanel.removeAll();
/* 342 */         this.bottomPanel.add(this.klabel);
/* 343 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/* 344 */         this.bottomPanel.add(this.probPane);
/* 345 */         this.step = 3;
/*     */         
/* 347 */         revalidate();
/* 348 */         repaint();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 355 */         break;
/*     */       default: 
/* 351 */         System.out.println("Markov decision cannot back here.");
/*     */       }
/*     */       
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void doNext()
/*     */   {
/* 362 */     Vector par = new Vector();
/* 363 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/* 365 */     switch (this.step)
/*     */     {
/*     */     case 1: 
/* 368 */       this.backbutt.setEnabled(true);
/*     */       
/* 370 */       this.nState = this.NumState.getValue();
/* 371 */       if ((this.nState > 4) || (this.nState <= 0)) {
/* 372 */         this.nState = 4;
/* 373 */         this.NumState.setValue(4);
/*     */       }
/* 375 */       this.nDecision = this.NumDecision.getValue();
/* 376 */       if ((this.nDecision > 5) || (this.nDecision <= 0)) {
/* 377 */         this.nDecision = 5;
/* 378 */         this.NumDecision.setValue(5);
/*     */       }
/*     */       
/*     */ 
/* 382 */       this.costPane.setPreferredSize(new Dimension(100 * (this.nDecision + 1), 24 + 20 * this.nState));
/* 383 */       this.costPane.setMaximumSize(new Dimension(100 * (this.nDecision + 1), 24 + 20 * this.nState));
/* 384 */       this.probPane.setPreferredSize(new Dimension(80 * this.nState, 24 + 20 * this.nState));
/* 385 */       this.probPane.setMaximumSize(new Dimension(80 * this.nState, 24 + 20 * this.nState));
/* 386 */       this.desPane.setPreferredSize(new Dimension(120, 24 + 20 * this.nState));
/* 387 */       this.desPane.setMaximumSize(new Dimension(120, 24 + 20 * this.nState));
/*     */       
/* 389 */       this.costModel.fireTableStructureChanged();
/* 390 */       this.costTable.sizeColumnsToFit(-1);
/* 391 */       this.probModel.fireTableStructureChanged();
/* 392 */       this.probTable.sizeColumnsToFit(-1);
/* 393 */       this.desModel.fireTableStructureChanged();
/* 394 */       this.desTable.sizeColumnsToFit(-1);
/*     */       
/*     */ 
/* 397 */       par.addElement(new Integer(this.nState));
/* 398 */       par.addElement(new Integer(this.nDecision));
/*     */       
/* 400 */       opr.parameters = par;
/* 401 */       opr.operation_code = 40101;
/* 402 */       this.controller.solver.doWork(opr, this.controller.data);
/*     */       
/*     */ 
/* 405 */       this.state.addStep(opr);
/*     */       
/* 407 */       this.opseq.addElement(new Integer(this.step));
/*     */       
/*     */ 
/* 410 */       this.bottomPanel.removeAll();
/* 411 */       this.bottomPanel.add(this.ctlabel);
/* 412 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/* 413 */       this.bottomPanel.add(this.costPane);
/* 414 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/* 415 */       this.bottomPanel.add(this.indilabel);
/*     */       
/* 417 */       this.upPanel.removeAll();
/* 418 */       this.nstlabel.setText("Number of states : ".concat(String.valueOf(String.valueOf(this.nState))));
/* 419 */       this.upPanel.add(this.nstlabel);
/* 420 */       this.upPanel.add(Box.createVerticalStrut(5));
/* 421 */       this.ndelabel.setText("Number of decisions : ".concat(String.valueOf(String.valueOf(this.nDecision))));
/* 422 */       this.upPanel.add(this.ndelabel);
/*     */       
/*     */ 
/* 425 */       this.mainPanel.removeAll();
/* 426 */       this.mainPanel.add(this.upPanel);
/* 427 */       this.mainPanel.add(Box.createVerticalStrut(20));
/* 428 */       this.mainPanel.add(this.bottomPanel);
/* 429 */       this.mainPanel.add(Box.createVerticalGlue());
/* 430 */       this.mainPanel.add(this.buttPanel);
/* 431 */       this.step = 2;
/*     */       
/* 433 */       revalidate();
/* 434 */       repaint();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 522 */       break;
/*     */     case 2: 
/* 438 */       this.costTable.getDefaultEditor(String.class).stopCellEditing();
/* 439 */       this.costTable.clearSelection();
/*     */       
/*     */ 
/* 442 */       par.addElement(this.cost);
/* 443 */       opr.parameters = par;
/* 444 */       opr.operation_code = 40102;
/* 445 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 446 */         String err = this.controller.solver.getErrInfo();
/* 447 */         JOptionPane.showMessageDialog(this, err);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 452 */         this.state.addStep(opr);
/*     */         
/* 454 */         this.opseq.addElement(new Integer(this.step));
/*     */         
/*     */ 
/* 457 */         this.current_k = 1;
/* 458 */         this.selected_k = 1;
/* 459 */         this.probModel.fireTableDataChanged();
/* 460 */         this.klabel.setText("Enter the transition matrix , Pij(k) , for k = ".concat(String.valueOf(String.valueOf(this.current_k))));
/* 461 */         this.bottomPanel.removeAll();
/* 462 */         this.bottomPanel.add(this.klabel);
/* 463 */         this.bottomPanel.add(Box.createVerticalStrut(10));
/* 464 */         this.bottomPanel.add(this.probPane);
/* 465 */         this.step = 3;
/*     */         
/* 467 */         revalidate();
/* 468 */         repaint();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 522 */       break;
/*     */     case 3: 
/* 472 */       this.probTable.getDefaultEditor(String.class).stopCellEditing();
/* 473 */       this.probTable.clearSelection();
/*     */       
/*     */ 
/* 476 */       par.addElement(new Integer(this.current_k));
/* 477 */       par.addElement(this.p[(this.current_k - 1)]);
/* 478 */       opr.parameters = par;
/* 479 */       opr.operation_code = 40103;
/* 480 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 481 */         String err = this.controller.solver.getErrInfo();
/* 482 */         JOptionPane.showMessageDialog(this, err);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 487 */         this.state.addStep(opr);
/*     */         
/* 489 */         this.opseq.addElement(new Integer(this.step));
/*     */         
/*     */ 
/* 492 */         if (this.current_k >= this.nDecision) {
/* 493 */           this.actionStatus.setText("When you are done, press the FINISH button, and then go to the Procedure menu to continue!");
/* 494 */           this.bottomPanel.removeAll();
/* 495 */           this.bottomPanel.add(this.polabel);
/* 496 */           this.bottomPanel.add(Box.createVerticalStrut(10));
/* 497 */           this.bottomPanel.add(this.desPane);
/* 498 */           this.bottomPanel.add(Box.createVerticalStrut(20));
/* 499 */           this.bottomPanel.add(this.disctPanel);
/* 500 */           this.bottomPanel.add(Box.createVerticalStrut(10));
/* 501 */           this.current_k += 1;
/* 502 */           this.selected_k += 1;
/* 503 */           this.step = 4;
/* 504 */           this.nextbutt.setEnabled(false);
/*     */         }
/*     */         else {
/* 507 */           this.current_k += 1;
/* 508 */           this.selected_k += 1;
/* 509 */           this.probModel.fireTableDataChanged();
/* 510 */           this.klabel.setText("Enter the transition matrix , Pij(k) , for k = ".concat(String.valueOf(String.valueOf(this.current_k))));
/* 511 */           this.step = 3;
/*     */         }
/*     */         
/* 514 */         revalidate();
/* 515 */         repaint();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 522 */       break;
/*     */     case 4: 
/*     */     default: 
/* 520 */       System.out.println("Markov Decision cannot next here.");
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/* 532 */     int[] para = new int[5];
/*     */     try {
/* 534 */       this.state = ((IORState)in.readObject());
/* 535 */       para = (int[])in.readObject();
/* 536 */       this.opseq = ((Vector)in.readObject());
/* 537 */       this.cost = ((double[][])in.readObject());
/* 538 */       this.p = ((double[][][])in.readObject());
/* 539 */       this.des = ((int[])in.readObject());
/* 540 */       this.state.setSolverStepVector();
/* 541 */       in.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 544 */       e1.printStackTrace();
/* 545 */       System.out.println("Open fails");
/*     */     }
/* 547 */     this.step = para[0];
/* 548 */     this.nState = para[1];
/* 549 */     this.nDecision = para[2];
/* 550 */     this.selected_k = para[3];
/* 551 */     this.current_k = para[4];
/* 552 */     this.NumState.setValue(this.nState);
/* 553 */     this.NumDecision.setValue(this.nDecision);
/*     */     
/* 555 */     if (this.step >= 2) {
/* 556 */       this.backbutt.setEnabled(true);
/*     */       
/* 558 */       this.costPane.setPreferredSize(new Dimension(100 * (this.nDecision + 1), 24 + 20 * this.nState));
/* 559 */       this.costPane.setMaximumSize(new Dimension(100 * (this.nDecision + 1), 24 + 20 * this.nState));
/* 560 */       this.probPane.setPreferredSize(new Dimension(80 * this.nState, 24 + 20 * this.nState));
/* 561 */       this.probPane.setMaximumSize(new Dimension(80 * this.nState, 24 + 20 * this.nState));
/* 562 */       this.desPane.setPreferredSize(new Dimension(120, 24 + 20 * this.nState));
/* 563 */       this.desPane.setMaximumSize(new Dimension(120, 24 + 20 * this.nState));
/*     */       
/* 565 */       this.costModel.fireTableStructureChanged();
/* 566 */       this.costTable.sizeColumnsToFit(-1);
/* 567 */       this.probModel.fireTableStructureChanged();
/* 568 */       this.probTable.sizeColumnsToFit(-1);
/* 569 */       this.desModel.fireTableStructureChanged();
/* 570 */       this.desTable.sizeColumnsToFit(-1);
/*     */       
/* 572 */       this.upPanel.removeAll();
/* 573 */       this.nstlabel.setText("Number of states : ".concat(String.valueOf(String.valueOf(this.nState))));
/* 574 */       this.upPanel.add(this.nstlabel);
/* 575 */       this.upPanel.add(Box.createVerticalStrut(5));
/* 576 */       this.ndelabel.setText("Number of decisions : ".concat(String.valueOf(String.valueOf(this.nDecision))));
/* 577 */       this.upPanel.add(this.ndelabel);
/*     */       
/* 579 */       this.mainPanel.removeAll();
/* 580 */       this.mainPanel.add(this.upPanel);
/* 581 */       this.mainPanel.add(Box.createVerticalStrut(20));
/* 582 */       this.mainPanel.add(this.bottomPanel);
/* 583 */       this.mainPanel.add(Box.createVerticalGlue());
/* 584 */       this.mainPanel.add(this.buttPanel);
/*     */     }
/* 586 */     switch (this.step) {
/*     */     case 1: 
/*     */       break;
/*     */     case 2: 
/* 590 */       this.bottomPanel.removeAll();
/* 591 */       this.bottomPanel.add(this.ctlabel);
/* 592 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/* 593 */       this.bottomPanel.add(this.costPane);
/* 594 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/* 595 */       this.bottomPanel.add(this.indilabel);
/* 596 */       break;
/*     */     case 3: 
/* 598 */       this.probModel.fireTableDataChanged();
/* 599 */       this.klabel.setText("Enter the transition matrix , Pij(k) , for k = ".concat(String.valueOf(String.valueOf(this.current_k))));
/* 600 */       this.bottomPanel.removeAll();
/* 601 */       this.bottomPanel.add(this.klabel);
/* 602 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/* 603 */       this.bottomPanel.add(this.probPane);
/* 604 */       break;
/*     */     case 4: 
/* 606 */       this.actionStatus.setText("When you have done, press Finish then choose a procedure from the procedure menu");
/* 607 */       this.bottomPanel.removeAll();
/* 608 */       this.bottomPanel.add(this.polabel);
/* 609 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/* 610 */       this.bottomPanel.add(this.desPane);
/* 611 */       this.bottomPanel.add(Box.createVerticalStrut(20));
/* 612 */       this.bottomPanel.add(this.disctPanel);
/* 613 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/* 614 */       this.nextbutt.setEnabled(false);
/* 615 */       this.disctfd.setValue(this.controller.data.discount);
/* 616 */       break;
/*     */     default: 
/* 618 */       System.out.println("Markov decision has no this step.");
/*     */     }
/* 620 */     revalidate();
/* 621 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/* 632 */     int[] interpara = new int[5];
/*     */     
/* 634 */     if (this.step == 2) {
/* 635 */       this.costTable.getDefaultEditor(String.class).stopCellEditing();
/* 636 */       this.costTable.clearSelection();
/*     */     }
/* 638 */     if (this.step == 3) {
/* 639 */       this.probTable.getDefaultEditor(String.class).stopCellEditing();
/* 640 */       this.probTable.clearSelection();
/*     */     }
/* 642 */     if (this.step == 4) {
/* 643 */       this.desTable.getDefaultEditor(String.class).stopCellEditing();
/* 644 */       this.desTable.clearSelection();
/* 645 */       for (int i = 0; i < this.nState; i++) {
/* 646 */         if ((this.des[i] < 1) || (this.des[i] > this.nDecision)) {
/* 647 */           JOptionPane.showMessageDialog(this, "Invalid initial policy!");
/* 648 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 653 */     interpara[0] = this.step;
/* 654 */     interpara[1] = this.nState;
/* 655 */     interpara[2] = this.nDecision;
/* 656 */     interpara[3] = this.selected_k;
/* 657 */     interpara[4] = this.current_k;
/*     */     try {
/* 659 */       out.writeObject(this.state);
/* 660 */       out.writeObject(interpara);
/* 661 */       out.writeObject(this.opseq);
/* 662 */       out.writeObject(this.cost);
/* 663 */       out.writeObject(this.p);
/* 664 */       out.writeObject(this.des);
/* 665 */       out.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 668 */       e1.printStackTrace();
/* 669 */       System.out.println("Save fails");
/*     */     }
/*     */   }
/*     */   
/*     */   class IORCostTableModel extends AbstractTableModel {
/*     */     IORCostTableModel() {}
/*     */     
/* 676 */     public int getColumnCount() { return IORMKDEnterPanel.this.nDecision + 1; }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 680 */       return IORMKDEnterPanel.this.nState;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col) {
/* 684 */       if (col == 0) {
/* 685 */         return "i \\ k";
/*     */       }
/* 687 */       return "   ".concat(String.valueOf(String.valueOf(col)));
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col) {
/* 691 */       String str = new String();
/* 692 */       DecimalFormat dfm = new DecimalFormat("#.##");
/* 693 */       if (col == 0) {
/* 694 */         return "    ".concat(String.valueOf(String.valueOf(row)));
/*     */       }
/* 696 */       if (Math.abs(IORMKDEnterPanel.this.cost[row][(col - 1)] - 'ϧ') < 0.01D) {
/* 697 */         return " --- ";
/*     */       }
/* 699 */       return dfm.format(IORMKDEnterPanel.this.cost[row][(col - 1)]);
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c)
/*     */     {
/* 704 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean isCellEditable(int row, int col)
/*     */     {
/* 710 */       if (col == 0) {
/* 711 */         return false;
/*     */       }
/* 713 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */     public void setValueAt(Object value, int row, int col)
/*     */     {
/* 719 */       if (((String)value).equals("1m"))
/*     */       {
/* 721 */         IORMKDEnterPanel.this.cost[row][(col - 1)] = 999.0D;
/*     */       }
/*     */       else
/*     */       {
/* 725 */         IORMKDEnterPanel.this.cost[row][(col - 1)] = Double.parseDouble((String)value);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   class IORProbTableModel extends AbstractTableModel
/*     */   {
/*     */     IORProbTableModel() {}
/*     */     
/*     */     public int getColumnCount()
/*     */     {
/* 736 */       return IORMKDEnterPanel.this.nState + 1;
/*     */     }
/*     */     
/*     */     public int getRowCount() {
/* 740 */       return IORMKDEnterPanel.this.nState;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col) {
/* 744 */       if (col == 0) {
/* 745 */         return "i \\ j";
/*     */       }
/* 747 */       return "   ".concat(String.valueOf(String.valueOf(col - 1)));
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col) {
/* 751 */       String str = new String();
/* 752 */       DecimalFormat dfm = new DecimalFormat("#.###");
/* 753 */       if (col == 0) {
/* 754 */         return "    ".concat(String.valueOf(String.valueOf(row)));
/*     */       }
/* 756 */       return dfm.format(IORMKDEnterPanel.this.p[(IORMKDEnterPanel.this.selected_k - 1)][row][(col - 1)]);
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c)
/*     */     {
/* 761 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean isCellEditable(int row, int col)
/*     */     {
/* 767 */       if (col == 0) {
/* 768 */         return false;
/*     */       }
/* 770 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */     public void setValueAt(Object value, int row, int col)
/*     */     {
/* 776 */       IORMKDEnterPanel.this.p[(IORMKDEnterPanel.this.selected_k - 1)][row][(col - 1)] = Double.parseDouble((String)value);
/*     */     }
/*     */   }
/*     */   
/*     */   class IORDesTableModel extends AbstractTableModel
/*     */   {
/*     */     IORDesTableModel() {}
/*     */     
/*     */     public int getColumnCount()
/*     */     {
/* 786 */       return 2;
/*     */     }
/*     */     
/*     */     public int getRowCount() {
/* 790 */       return IORMKDEnterPanel.this.nState;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col) {
/* 794 */       if (col == 0) {
/* 795 */         return "i";
/*     */       }
/* 797 */       return "di";
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col) {
/* 801 */       String str = new String();
/* 802 */       DecimalFormat dfm = new DecimalFormat();
/* 803 */       if (col == 0) {
/* 804 */         return "    ".concat(String.valueOf(String.valueOf(row)));
/*     */       }
/* 806 */       return dfm.format(IORMKDEnterPanel.this.des[row]);
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c) {
/* 810 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean isCellEditable(int row, int col)
/*     */     {
/* 816 */       if (col == 0) {
/* 817 */         return false;
/*     */       }
/* 819 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */     public void setValueAt(Object value, int row, int col)
/*     */     {
/* 825 */       IORMKDEnterPanel.this.des[row] = Integer.parseInt((String)value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 834 */     String str = " Entering a Markov Decision Model\n\n";
/* 835 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to quickly enter a Markov decision model, or to revise a ");
/* 836 */     str = String.valueOf(String.valueOf(str)).concat("previously entered model. The model is entered in the form and notation presented ");
/* 837 */     str = String.valueOf(String.valueOf(str)).concat("in Section 2 of the chapter, \"Markov Decision Processes\". You are ");
/* 838 */     str = String.valueOf(String.valueOf(str)).concat("allowed up to 4 states (M=3) and 5 decisions (K=5).\n");
/* 839 */     str = String.valueOf(String.valueOf(str)).concat("You also will be asked for a value of the discount factor introduced in Section 5. For ");
/* 840 */     str = String.valueOf(String.valueOf(str)).concat("the criterion of Sections 1-4 (minimizing expected average cost per unit time), set ");
/* 841 */     str = String.valueOf(String.valueOf(str)).concat("the discount factor equal to one.");
/* 842 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 843 */     this.help_content_procedure.setText(str);
/* 844 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 851 */     String str = "Entering a Markov Decision Model\n\n";
/* 852 */     str = String.valueOf(String.valueOf(str)).concat("This procedure is used to enter a Markov decision model to be solved by one ");
/* 853 */     str = String.valueOf(String.valueOf(str)).concat("of the policy improvement algorithms or the method of successive approximations. ");
/* 854 */     str = String.valueOf(String.valueOf(str)).concat("To enter a model, the number of states and decisions need to be entered, as well ");
/* 855 */     str = String.valueOf(String.valueOf(str)).concat("as the cost matrix, transition matrices, and, optionally, the discount factor.\n\n");
/* 856 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of states\n\n");
/* 857 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of states in the model (1 <= number of states <= 4, an integer). ");
/* 858 */     str = String.valueOf(String.valueOf(str)).concat("In the notation of the book, this number is M+1.\n\n");
/* 859 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of decisions\n\n");
/* 860 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of possible decisions, K (1 <= K <= 5, K an integer).");
/* 861 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 862 */     this.help_content_step.setText(str);
/* 863 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/* 866 */   private String str0 = "Entering a Markov Decision Model\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 871 */   private String str1 = "Entering the number of states\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 876 */   private String str2 = "Entering the cost matrix\n\n";
/*     */   
/*     */ 
/*     */ 
/* 880 */   private String str3 = "Entering the transition matrix\n\n";
/*     */   
/*     */ 
/* 883 */   private String str4 = "Entering the initial trial policy\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void updateStepHelpPanel()
/*     */   {
/* 898 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/* 899 */     switch (this.step) {
/*     */     case 1: 
/* 901 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str1).append(str))));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 920 */       break;
/*     */     case 2: 
/* 904 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str2).append(str))));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 920 */       break;
/*     */     case 3: 
/* 907 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str3).append(str))));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 920 */       break;
/*     */     case 4: 
/* 909 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str4).append(str))));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 920 */       break;
/*     */     default: 
/* 912 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORMKDEnterPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */