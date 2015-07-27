/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Vector;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextArea;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.ListSelectionModel;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.table.AbstractTableModel;
/*      */ import javax.swing.table.DefaultTableCellRenderer;
/*      */ import javax.swing.table.TableColumn;
/*      */ import javax.swing.table.TableModel;
/*      */ 
/*      */ public class IORLPModifyPanel extends IORLPActionPanel implements java.io.Serializable
/*      */ {
/*   30 */   private final int ALGEBRA_FORM = 1;
/*   31 */   private final int TABULAR_FORM = 2;
/*   32 */   private String GREATTHAN = ">=";
/*      */   
/*   34 */   private JPanel mainPanel = new JPanel();
/*   35 */   private IORLPModifyPanel.IORTableModel interModel = null;
/*   36 */   private IORLPModifyPanel.IORAlgebraModel algbModel = null;
/*   37 */   private JTable table = null;
/*      */   private DefaultTableCellRenderer selectedRenderer;
/*   39 */   private JScrollPane upscrollPane = null;
/*   40 */   private JPanel tablePanel = new JPanel();
/*   41 */   private JLabel zerolabel = new JLabel();
/*   42 */   private JLabel oklabel = new JLabel("Is the current feasible solution optimal? ");
/*      */   
/*   44 */   private JTextArea statetext = new JTextArea();
/*   45 */   private JPanel statePanel = new JPanel();
/*   46 */   private JPanel multiPanel = new JPanel();
/*   47 */   private JPanel dividPanel = new JPanel();
/*   48 */   private JPanel buttPanel = new JPanel();
/*   49 */   private JButton backbutt = new JButton("Back");
/*   50 */   private JButton nextbutt = new JButton("Next");
/*      */   
/*      */ 
/*   53 */   private int step = 1;
/*   54 */   private Vector opseq = new Vector();
/*   55 */   private Vector selcell = new Vector();
/*      */   
/*      */ 
/*   58 */   private JTextField yorn = new JTextField("N", 2);
/*   59 */   private JTextField factor = new JTextField(6);
/*   60 */   private JLabel[] inputlabel = null;
/*   61 */   private JTextField[] multiple = null;
/*   62 */   private JPanel[] inputPanel = null;
/*      */   private int nBasic;
/*      */   private int nAllvar;
/*   65 */   private int row; private int col; private int dispForm; private int equation = 0;
/*   66 */   private int result = 0;
/*      */   
/*   68 */   private String str_selbas = "Select the entering and the leaving basic variable by clicking on the corresponding column \nand row (pivot equation) above. Then enter the factor by which to divide the pivot equation.\nThen press the NEXT button.";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void backStep() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updatePanel() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public IORLPModifyPanel(IORLPProcessController c)
/*      */   {
/*   87 */     super(c);
/*      */     
/*   89 */     this.state = new IORState(c.solver);
/*   90 */     add(this.mainPanel, "Center");
/*   91 */     this.bn_back.setVisible(false);
/*   92 */     this.bn_finish.setVisible(true);
/*   93 */     this.nBasic = this.controller.data.num_constrain;
/*   94 */     this.nAllvar = this.controller.data.num_variable;
/*   95 */     this.dispForm = this.controller.data.DisplayForm;
/*      */     
/*      */ 
/*   98 */     String xstr = " ";
/*   99 */     this.tablePanel.setLayout(new BoxLayout(this.tablePanel, 1));
/*  100 */     for (int i = 1; i < this.controller.data.num_variable + 1; i++) {
/*  101 */       if (this.controller.data.is_artificial[i] != 0) {
/*  102 */         xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("X").append(i).append(this.GREATTHAN).append("0"))))));
/*      */       } else
/*  104 */         xstr = String.valueOf(String.valueOf(xstr)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("x").append(i).append(this.GREATTHAN).append("0"))))));
/*  105 */       if (i < this.controller.data.num_variable)
/*  106 */         xstr = String.valueOf(String.valueOf(xstr)).concat(" , ");
/*      */     }
/*  108 */     this.zerolabel.setText(xstr);
/*      */     
/*      */ 
/*  111 */     this.selectedRenderer = new DefaultTableCellRenderer()
/*      */     {
/*      */       public void setValue(Object value) {
/*  114 */         if ((value instanceof IORTableCell))
/*      */         {
/*  116 */           if (((IORTableCell)value).isBlock == true) {
/*  117 */             setBackground(Color.cyan);
/*      */           } else {
/*  119 */             setBackground(Color.white);
/*      */           }
/*      */         }
/*  122 */         super.setValue(value);
/*      */       }
/*      */       
/*  125 */     };
/*  126 */     this.interModel = new IORLPModifyPanel.IORTableModel();
/*  127 */     this.algbModel = new IORLPModifyPanel.IORAlgebraModel();
/*  128 */     if (this.dispForm == 2) {
/*  129 */       this.table = new JTable(this.interModel);
/*  130 */       this.upscrollPane = new JScrollPane(this.table);
/*  131 */       this.tablePanel.add(this.upscrollPane);
/*  132 */       for (i = 3; i < this.table.getColumnCount(); i++) {
/*  133 */         this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*  134 */         this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*      */       }
/*  136 */       for (i = 0; i < 3; i++) {
/*  137 */         this.table.getColumn(this.table.getColumnName(i)).setPreferredWidth(20);
/*      */       }
/*      */     }
/*      */     
/*  141 */     this.table = new JTable(this.algbModel);
/*  142 */     this.table.setShowGrid(false);
/*  143 */     this.tablePanel.setBorder(new javax.swing.border.EtchedBorder());
/*  144 */     this.tablePanel.add(this.table);
/*  145 */     this.tablePanel.add(Box.createVerticalStrut(10));
/*  146 */     this.tablePanel.add(this.zerolabel);
/*  147 */     this.tablePanel.add(Box.createVerticalStrut(30));
/*  148 */     for (i = 3; i < this.table.getColumnCount(); i++) {
/*  149 */       this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*  150 */       this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*      */     }
/*  152 */     this.table.getColumn(this.table.getColumnName(0)).setPreferredWidth(1);
/*  153 */     this.table.getColumn(this.table.getColumnName(1)).setPreferredWidth(20);
/*  154 */     this.table.getColumn(this.table.getColumnName(2)).setPreferredWidth(20);
/*      */     
/*      */ 
/*  157 */     this.table.setPreferredScrollableViewportSize(new Dimension(500, 100));
/*  158 */     this.table.setCellSelectionEnabled(true);
/*  159 */     this.table.setSelectionMode(0);
/*      */     
/*  161 */     this.tablePanel.setAlignmentX(0.5F);
/*  162 */     this.tablePanel.setPreferredSize(new Dimension(700, 20 * (this.nBasic + 1) + 40));
/*  163 */     this.tablePanel.setMaximumSize(new Dimension(700, 20 * (this.nBasic + 1) + 60));
/*      */     
/*  165 */     addSelectionListener();
/*      */     
/*      */ 
/*  168 */     this.statetext.setBackground(getBackground());
/*  169 */     this.statetext.setForeground(Color.blue);
/*  170 */     this.statetext.setEditable(false);
/*  171 */     this.statetext.setText(this.str_selbas);
/*  172 */     this.statetext.setPreferredSize(new Dimension(500, 60));
/*      */     
/*      */ 
/*  175 */     this.inputlabel = new JLabel[this.nBasic + 1];
/*  176 */     this.multiple = new JTextField[this.nBasic + 1];
/*  177 */     this.inputPanel = new JPanel[this.nBasic + 1];
/*  178 */     for (i = 0; i < this.nBasic + 1; i++) {
/*  179 */       this.inputlabel[i] = new JLabel();
/*  180 */       this.multiple[i] = new JTextField(6);
/*  181 */       this.inputPanel[i] = new JPanel();
/*      */     }
/*      */     
/*      */ 
/*  185 */     this.dividPanel.setLayout(new BoxLayout(this.dividPanel, 1));
/*  186 */     this.multiPanel.setLayout(new java.awt.BorderLayout());
/*  187 */     this.multiPanel.setPreferredSize(new Dimension(600, this.nBasic * 50));
/*  188 */     this.multiPanel.setMaximumSize(new Dimension(600, this.nBasic * 50));
/*  189 */     this.multiPanel.add(this.dividPanel, "North");
/*      */     
/*      */ 
/*  192 */     this.statePanel.setPreferredSize(new Dimension(660, 200));
/*  193 */     this.statePanel.setMaximumSize(new Dimension(660, 200));
/*  194 */     this.statePanel.add(this.oklabel);
/*  195 */     this.statePanel.add(this.yorn);
/*      */     
/*      */ 
/*  198 */     this.buttPanel.add(this.backbutt);
/*      */     
/*  200 */     this.buttPanel.add(Box.createRigidArea(new Dimension(10, 30)));
/*      */     
/*  202 */     this.buttPanel.add(this.nextbutt);
/*      */     
/*  204 */     this.buttPanel.setPreferredSize(new Dimension(300, 60));
/*  205 */     this.buttPanel.setMaximumSize(new Dimension(300, 60));
/*      */     
/*  207 */     this.nextbutt.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/*  210 */         IORLPModifyPanel.this.doNext();
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  216 */     });
/*  217 */     this.backbutt.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/*  220 */         IORLPModifyPanel.this.doBack();
/*      */       }
/*  222 */     });
/*  223 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*  224 */     this.mainPanel.setBorder(new EmptyBorder(0, 20, 0, 20));
/*  225 */     this.mainPanel.add(this.tablePanel);
/*  226 */     this.mainPanel.add(this.statePanel);
/*  227 */     this.mainPanel.add(this.buttPanel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void switchForm(int newform)
/*      */   {
/*  237 */     if ((newform < 1) || (newform > 2)) {
/*  238 */       return;
/*      */     }
/*  240 */     if (newform != this.dispForm) {
/*  241 */       this.dispForm = newform;
/*  242 */       if (newform == 2) {
/*  243 */         clrTableColor(this.table);
/*  244 */         this.table.setModel(this.interModel);
/*  245 */         this.table.setShowGrid(true);
/*  246 */         for (int i = 3; i < this.table.getColumnCount(); i++) {
/*  247 */           this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*  248 */           this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*      */         }
/*  250 */         for (i = 0; i < 3; i++) {
/*  251 */           this.table.getColumn(this.table.getColumnName(i)).setPreferredWidth(20);
/*      */         }
/*  253 */         this.table.revalidate();
/*  254 */         this.table.repaint();
/*  255 */         if ((this.step == 2) || (this.step == 3)) {
/*  256 */           SelBlock(this.table, this.row, this.col);
/*      */         }
/*  258 */         this.tablePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
/*  259 */         this.tablePanel.removeAll();
/*  260 */         this.upscrollPane = new JScrollPane(this.table);
/*  261 */         this.tablePanel.add(this.upscrollPane);
/*      */       }
/*  263 */       if (newform == 1) {
/*  264 */         clrTableColor(this.table);
/*  265 */         this.table.setModel(this.algbModel);
/*  266 */         this.table.setShowGrid(false);
/*  267 */         for (int i = 3; i < this.table.getColumnCount(); i++) {
/*  268 */           this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*  269 */           this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*      */         }
/*  271 */         this.table.getColumn(this.table.getColumnName(0)).setPreferredWidth(1);
/*  272 */         this.table.getColumn(this.table.getColumnName(1)).setPreferredWidth(20);
/*  273 */         this.table.getColumn(this.table.getColumnName(2)).setPreferredWidth(20);
/*  274 */         this.table.revalidate();
/*  275 */         this.table.repaint();
/*  276 */         if ((this.step == 2) || (this.step == 3)) {
/*  277 */           SelBlock(this.table, this.row, this.col);
/*      */         }
/*  279 */         this.tablePanel.setBorder(new javax.swing.border.EtchedBorder());
/*  280 */         this.tablePanel.removeAll();
/*  281 */         this.tablePanel.add(this.table);
/*  282 */         this.tablePanel.add(Box.createVerticalStrut(10));
/*  283 */         this.tablePanel.add(this.zerolabel);
/*  284 */         this.tablePanel.add(Box.createVerticalStrut(30));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void finishProcedure()
/*      */   {
/*  293 */     this.controller.setMenuState(12);
/*  294 */     this.actionStatus.setText("The current procedure is finished. Please go to the Procedure menu to continue!");
/*  295 */     setFinishEnabled(false);
/*      */   }
/*      */   
/*      */ 
/*      */   private void addInputPanel(int eq)
/*      */   {
/*  301 */     if (eq == 0) {
/*  302 */       return;
/*      */     }
/*  304 */     this.dividPanel.removeAll();
/*  305 */     for (int i = 0; i < this.nBasic + 1; i++) {
/*  306 */       if (i != eq) {
/*  307 */         this.inputPanel[i].removeAll();
/*  308 */         this.inputlabel[i].setText(String.valueOf(String.valueOf(new StringBuffer("Enter the multiple of Equation ").append(eq).append(" to subtract from Equation ").append(i).append(":"))));
/*  309 */         this.inputlabel[i].setLabelFor(this.multiple[i]);
/*  310 */         this.inputPanel[i].add(this.inputlabel[i]);
/*  311 */         this.inputPanel[i].add(this.multiple[i]);
/*  312 */         this.dividPanel.add(this.inputPanel[i]);
/*  313 */         this.dividPanel.add(Box.createRigidArea(new Dimension(10, 5)));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doBack()
/*      */   {
/*  324 */     this.actionStatus.setText(" ");
/*  325 */     this.nextbutt.setEnabled(true);
/*  326 */     if (this.opseq.isEmpty() == false) {
/*  327 */       int last = ((Integer)this.opseq.lastElement()).intValue();
/*  328 */       this.opseq.removeElementAt(this.opseq.size() - 1);
/*  329 */       setFinishEnabled(true);
/*      */       
/*  331 */       switch (last) {
/*      */       case 1: 
/*  333 */         clrTableColor(this.table);
/*      */         
/*  335 */         this.statePanel.removeAll();
/*  336 */         this.statePanel.add(this.oklabel);
/*  337 */         this.statePanel.add(this.yorn);
/*  338 */         this.step = 1;
/*      */         
/*  340 */         revalidate();
/*  341 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  403 */         break;
/*      */       case 2: 
/*  345 */         this.controller.solver.reset();
/*  346 */         this.state.back();
/*      */         
/*  348 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  351 */         this.mainPanel.remove(this.multiPanel);
/*  352 */         this.mainPanel.remove(this.buttPanel);
/*      */         
/*      */ 
/*  355 */         this.statetext.setText(this.str_selbas);
/*  356 */         this.statePanel.removeAll();
/*  357 */         this.statePanel.add(this.statetext);
/*  358 */         this.statePanel.add(this.factor);
/*  359 */         this.mainPanel.add(this.statePanel);
/*  360 */         this.mainPanel.add(this.buttPanel);
/*      */         
/*  362 */         this.step = 2;
/*      */         
/*  364 */         this.interModel.fireTableDataChanged();
/*  365 */         this.algbModel.fireTableDataChanged();
/*  366 */         revalidate();
/*  367 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  403 */         break;
/*      */       case 3: 
/*  371 */         this.controller.solver.reset();
/*  372 */         this.state.back();
/*      */         
/*  374 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  376 */         this.step = 3;
/*      */         
/*  378 */         this.interModel.fireTableDataChanged();
/*  379 */         this.algbModel.fireTableDataChanged();
/*  380 */         if (this.selcell.isEmpty() == false) {
/*  381 */           Object scel = this.selcell.elementAt(this.selcell.size() - 1);
/*  382 */           this.selcell.removeElementAt(this.selcell.size() - 1);
/*  383 */           this.row = ((int[])scel)[0];
/*  384 */           this.col = ((int[])scel)[1];
/*  385 */           SelBlock(this.table, this.row, this.col);
/*      */         }
/*      */         
/*  388 */         this.mainPanel.remove(this.statePanel);
/*  389 */         this.mainPanel.remove(this.buttPanel);
/*  390 */         addInputPanel(this.row);
/*  391 */         this.equation = this.row;
/*  392 */         this.mainPanel.add(this.multiPanel);
/*  393 */         this.mainPanel.add(this.buttPanel);
/*      */         
/*  395 */         revalidate();
/*  396 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  403 */         break;
/*      */       default: 
/*  399 */         System.out.println("The program should not be backed here");
/*      */         
/*      */ 
/*      */ 
/*  403 */         break; }
/*      */     }
/*      */   }
/*      */   
/*      */   private void doNext() {
/*  408 */     int[] selPos = new int[2];
/*  409 */     int[] remPos = new int[2];
/*      */     
/*      */ 
/*      */ 
/*  413 */     double[] eqc = new double[this.nBasic + 1];
/*  414 */     double[] eqm = new double[this.nBasic + 1];
/*      */     
/*  416 */     Vector par = new Vector();
/*  417 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*  419 */     switch (this.step) {
/*      */     case 1: 
/*  421 */       char sel = this.yorn.getText().charAt(0);
/*      */       
/*  423 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*  425 */       if ((sel != 'y') && (sel != 'Y'))
/*      */       {
/*      */ 
/*      */ 
/*  429 */         this.statetext.setText(this.str_selbas);
/*  430 */         this.statePanel.removeAll();
/*  431 */         this.statePanel.add(this.statetext);
/*  432 */         this.statePanel.add(this.factor);
/*  433 */         this.table.clearSelection();
/*      */         
/*  435 */         this.step = 2;
/*      */       }
/*      */       else {
/*  438 */         this.statePanel.removeAll();
/*  439 */         this.statetext.setText("You now have completed the Modified Simplex Method for this problem. \nYou may print your work by choosing Pint to File under the File menu.");
/*      */         
/*  441 */         this.statePanel.add(this.statetext);
/*  442 */         this.nextbutt.setEnabled(false);
/*  443 */         this.actionStatus.setText("After you are done, press the FINISH button, and then go to the Procedure menu to continue.");
/*  444 */         this.step = 4;
/*      */       }
/*  446 */       revalidate();
/*  447 */       repaint();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  543 */       break;
/*      */     case 2: 
/*  450 */       if ((this.row < 1) || (this.col < 3) || (this.col >= this.nAllvar + 3)) {
/*  451 */         JOptionPane.showMessageDialog(this, "This row or column cannot be selected!");
/*      */       }
/*      */       else {
/*  454 */         selPos[0] = this.row;
/*  455 */         selPos[1] = (this.col - 2);
/*  456 */         String input = this.factor.getText();
/*      */         try {
/*  458 */           dblPar = Double.parseDouble(input);
/*      */         } catch (NumberFormatException e) { double dblPar;
/*  460 */           JOptionPane.showMessageDialog(this, "Invalid Input. Please try again!");
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  543 */           break;
/*      */         }
/*      */         double dblPar;
/*  463 */         par.addElement(selPos);
/*  464 */         par.addElement(new Double(dblPar));
/*      */         
/*  466 */         opr.operation_code = 10401;
/*  467 */         opr.parameters = par;
/*  468 */         if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  469 */           String err = this.controller.solver.getErrInfo();
/*  470 */           JOptionPane.showMessageDialog(this, err);
/*      */         }
/*      */         else
/*      */         {
/*  474 */           this.state.addStep(opr);
/*      */           
/*  476 */           this.opseq.addElement(new Integer(this.step));
/*      */           
/*      */ 
/*  479 */           this.mainPanel.remove(this.statePanel);
/*  480 */           this.mainPanel.remove(this.buttPanel);
/*  481 */           addInputPanel(this.row);
/*  482 */           this.mainPanel.add(this.multiPanel);
/*  483 */           this.mainPanel.add(this.buttPanel);
/*  484 */           this.equation = this.row;
/*      */           
/*  486 */           this.step = 3;
/*  487 */           this.interModel.fireTableDataChanged();
/*  488 */           this.algbModel.fireTableDataChanged();
/*  489 */           revalidate();
/*  490 */           repaint();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  543 */       break;
/*      */     case 3: 
/*  496 */       if (prcMultiple(eqc, eqm) != false)
/*      */       {
/*      */ 
/*  499 */         par.addElement(eqm);
/*  500 */         par.addElement(eqc);
/*  501 */         opr.operation_code = 10402;
/*  502 */         opr.parameters = par;
/*      */         
/*  504 */         if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  505 */           String err = this.controller.solver.getErrInfo();
/*  506 */           JOptionPane.showMessageDialog(this, err);
/*      */         }
/*      */         else
/*      */         {
/*  510 */           this.state.addStep(opr);
/*      */           
/*  512 */           this.opseq.addElement(new Integer(this.step));
/*      */           
/*      */ 
/*  515 */           remPos[0] = this.row;
/*  516 */           remPos[1] = this.col;
/*  517 */           this.selcell.addElement(remPos);
/*      */           
/*      */ 
/*  520 */           this.statePanel.removeAll();
/*  521 */           this.statePanel.add(this.oklabel);
/*  522 */           this.statePanel.add(this.yorn);
/*  523 */           this.mainPanel.remove(this.multiPanel);
/*  524 */           this.mainPanel.remove(this.buttPanel);
/*  525 */           this.mainPanel.add(this.statePanel);
/*  526 */           this.mainPanel.add(this.buttPanel);
/*      */           
/*  528 */           this.step = 1;
/*      */           
/*      */ 
/*  531 */           clrTableColor(this.table);
/*  532 */           this.interModel.fireTableDataChanged();
/*  533 */           this.algbModel.fireTableDataChanged();
/*  534 */           revalidate();
/*  535 */           repaint();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  543 */       break;
/*      */     default: 
/*  539 */       System.out.println("should not arrive this");
/*      */       
/*      */ 
/*      */ 
/*  543 */       break;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void LoadFile(ObjectInputStream in)
/*      */   {
/*  553 */     int[] para = new int[5];
/*      */     
/*      */     try
/*      */     {
/*  557 */       this.state = ((IORState)in.readObject());
/*  558 */       para = (int[])in.readObject();
/*  559 */       this.opseq = ((Vector)in.readObject());
/*  560 */       this.selcell = ((Vector)in.readObject());
/*  561 */       this.state.setSolverStepVector();
/*      */     }
/*      */     catch (Exception e1) {
/*  564 */       e1.printStackTrace();
/*  565 */       System.out.println("Open fails");
/*      */     }
/*      */     
/*  568 */     this.step = para[0];
/*  569 */     this.row = para[1];
/*  570 */     this.col = para[2];
/*  571 */     this.equation = para[3];
/*  572 */     this.result = para[4];
/*      */     
/*  574 */     this.mainPanel.removeAll();
/*  575 */     switch (this.step) {
/*      */     case 1: 
/*  577 */       this.mainPanel.add(this.tablePanel);
/*  578 */       this.mainPanel.add(this.statePanel);
/*  579 */       this.mainPanel.add(this.buttPanel);
/*  580 */       break;
/*      */     
/*      */ 
/*      */     case 2: 
/*  584 */       this.statetext.setText(this.str_selbas);
/*  585 */       this.statePanel.removeAll();
/*  586 */       this.statePanel.add(this.statetext);
/*  587 */       this.statePanel.add(this.factor);
/*  588 */       this.mainPanel.add(this.tablePanel);
/*  589 */       this.mainPanel.add(this.statePanel);
/*  590 */       this.mainPanel.add(this.buttPanel);
/*      */       
/*  592 */       this.interModel.fireTableDataChanged();
/*  593 */       this.algbModel.fireTableDataChanged();
/*  594 */       SelBlock(this.table, this.row, this.col);
/*  595 */       break;
/*      */     case 3: 
/*  597 */       addInputPanel(this.row);
/*  598 */       this.mainPanel.add(this.tablePanel);
/*  599 */       this.mainPanel.add(this.multiPanel);
/*  600 */       this.mainPanel.add(this.buttPanel);
/*  601 */       this.equation = this.row;
/*  602 */       this.interModel.fireTableDataChanged();
/*  603 */       this.algbModel.fireTableDataChanged();
/*  604 */       SelBlock(this.table, this.row, this.col);
/*  605 */       break;
/*      */     case 4: 
/*  607 */       this.statePanel.removeAll();
/*  608 */       this.statetext.setText("You now have completed the Modified Simplex Method for this problem. \nYou may print your work by choosing Pint to File under the File menu.");
/*      */       
/*  610 */       this.statePanel.add(this.statetext);
/*  611 */       this.nextbutt.setEnabled(false);
/*  612 */       this.actionStatus.setText("After you are done, press the FINISH button, and then go to the Procedure menu to continue.");
/*  613 */       this.mainPanel.add(this.tablePanel);
/*  614 */       this.mainPanel.add(this.statePanel);
/*  615 */       this.mainPanel.add(this.buttPanel);
/*  616 */       break;
/*      */     default: 
/*  618 */       System.out.println("LP interactive panel has no this step.");
/*      */     }
/*  620 */     revalidate();
/*  621 */     repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFile(ObjectOutputStream out)
/*      */   {
/*  631 */     int[] interpara = new int[5];
/*      */     
/*  633 */     interpara[0] = this.step;
/*  634 */     interpara[1] = this.row;
/*  635 */     interpara[2] = this.col;
/*  636 */     interpara[3] = this.equation;
/*  637 */     interpara[4] = this.result;
/*      */     try {
/*  639 */       out.writeObject(this.state);
/*  640 */       out.writeObject(interpara);
/*  641 */       out.writeObject(this.opseq);
/*  642 */       out.writeObject(this.selcell);
/*      */     }
/*      */     catch (Exception e1) {
/*  645 */       e1.printStackTrace();
/*  646 */       System.out.println("Save fails");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void changeTableStrct()
/*      */   {
/*  655 */     this.interModel.fireTableStructureChanged();
/*  656 */     this.algbModel.fireTableStructureChanged();
/*  657 */     if (this.dispForm == 2) {
/*  658 */       this.table.setModel(this.interModel);
/*  659 */       for (int i = 3; i < this.table.getColumnCount(); i++) {
/*  660 */         this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*  661 */         this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*      */       }
/*  663 */       for (i = 0; i < 3; i++) {
/*  664 */         this.table.getColumn(this.table.getColumnName(i)).setPreferredWidth(20);
/*      */       }
/*      */     }
/*      */     
/*  668 */     this.table.setModel(this.algbModel);
/*  669 */     for (int i = 3; i < this.table.getColumnCount(); i++) {
/*  670 */       this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*  671 */       this.table.getColumn(this.table.getColumnName(i)).setCellRenderer(this.selectedRenderer);
/*      */     }
/*  673 */     this.table.getColumn(this.table.getColumnName(0)).setPreferredWidth(1);
/*  674 */     this.table.getColumn(this.table.getColumnName(1)).setPreferredWidth(20);
/*  675 */     this.table.getColumn(this.table.getColumnName(2)).setPreferredWidth(20);
/*      */     
/*  677 */     this.table.setPreferredScrollableViewportSize(new Dimension(500, 100));
/*  678 */     this.table.setCellSelectionEnabled(true);
/*  679 */     this.table.setSelectionMode(0);
/*  680 */     this.table.sizeColumnsToFit(-1);
/*  681 */     this.table.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean prcMultiple(double[] ec, double[] em)
/*      */   {
/*  689 */     for (int i = 0; i < this.nBasic + 1; i++) {
/*  690 */       if (i == this.equation) {
/*  691 */         ec[i] = 0.0D;
/*  692 */         em[i] = 0.0D;
/*      */       }
/*      */       else {
/*  695 */         String input = this.multiple[i].getText();
/*  696 */         if (input.length() <= 0) {
/*  697 */           JOptionPane.showMessageDialog(this, "Invalid input multiple!");
/*  698 */           return false;
/*      */         }
/*  700 */         int ps = input.indexOf('m');
/*  701 */         boolean bool; if (ps == -1) {
/*  702 */           int ls = input.indexOf('M');
/*  703 */           if (ls == -1) {
/*      */             try {
/*  705 */               ec[i] = Double.parseDouble(input);
/*      */             } catch (NumberFormatException e) {
/*  707 */               JOptionPane.showMessageDialog(this, "Invalid input number");
/*  708 */               return false;
/*      */             }
/*  710 */             em[i] = 0.0D;
/*      */           }
/*  712 */           else if (ls > 0) {
/*  713 */             if (ls >= input.length() - 1) {
/*  714 */               ec[i] = 0.0D;
/*      */             } else {
/*      */               try {
/*  717 */                 ec[i] = Double.parseDouble(input.substring(ls + 1));
/*      */               } catch (NumberFormatException e) {
/*  719 */                 JOptionPane.showMessageDialog(this, "Invalid input number");
/*  720 */                 return false;
/*      */               }
/*      */             }
/*      */             try {
/*  724 */               em[i] = Double.parseDouble(input.substring(0, ls));
/*      */             } catch (NumberFormatException e) {
/*  726 */               JOptionPane.showMessageDialog(this, "Invalid input number");
/*  727 */               return false;
/*      */             }
/*      */           }
/*      */           else {
/*  731 */             JOptionPane.showMessageDialog(this, "Invalid input multiple!");
/*  732 */             return false;
/*      */           }
/*      */         }
/*  735 */         else if (ps > 0) {
/*  736 */           if (ps >= input.length() - 1) {
/*  737 */             ec[i] = 0.0D;
/*      */           } else {
/*      */             try {
/*  740 */               ec[i] = Double.parseDouble(input.substring(ps + 1));
/*      */             } catch (NumberFormatException e) {
/*  742 */               JOptionPane.showMessageDialog(this, "Invalid input number");
/*  743 */               return false;
/*      */             }
/*      */           }
/*      */           try {
/*  747 */             em[i] = Double.parseDouble(input.substring(0, ps));
/*      */           } catch (NumberFormatException e) {
/*  749 */             JOptionPane.showMessageDialog(this, "Invalid input number");
/*  750 */             return false;
/*      */           }
/*      */         }
/*      */         else {
/*  754 */           JOptionPane.showMessageDialog(this, "Invalid input multiple!");
/*  755 */           return false;
/*      */         }
/*      */       }
/*      */     }
/*  759 */     return true;
/*      */   }
/*      */   
/*      */   private void addSelectionListener()
/*      */   {
/*  764 */     int span = this.nBasic;
/*      */     
/*      */ 
/*  767 */     ListSelectionModel rowSM = this.table.getSelectionModel();
/*  768 */     rowSM.addListSelectionListener(new ListSelectionListener()
/*      */     {
/*      */       public void valueChanged(ListSelectionEvent e)
/*      */       {
/*  772 */         ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/*  773 */         if (!lsm.isSelectionEmpty())
/*      */         {
/*  775 */           int selrow = IORLPModifyPanel.this.table.getSelectedRow();
/*  776 */           int selcol = IORLPModifyPanel.this.table.getSelectedColumn();
/*  777 */           if ((selrow >= 0) && (selcol >= 3) && (selcol < IORLPModifyPanel.this.nAllvar + 3) && (IORLPModifyPanel.this.step == 2)) {
/*  778 */             IORLPModifyPanel.this.row = selrow;
/*  779 */             IORLPModifyPanel.this.col = selcol;
/*  780 */             IORLPModifyPanel.this.SelBlock(IORLPModifyPanel.this.table, selrow, selcol);
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*  785 */     });
/*  786 */     ListSelectionModel colSM = this.table.getColumnModel().getSelectionModel();
/*      */     
/*  788 */     colSM.addListSelectionListener(new ListSelectionListener()
/*      */     {
/*      */       public void valueChanged(ListSelectionEvent e)
/*      */       {
/*  792 */         ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/*  793 */         if (!lsm.isSelectionEmpty())
/*      */         {
/*  795 */           int selrow = IORLPModifyPanel.this.table.getSelectedRow();
/*  796 */           int selcol = IORLPModifyPanel.this.table.getSelectedColumn();
/*  797 */           if ((selrow >= 0) && (selcol >= 3) && (selcol < IORLPModifyPanel.this.nAllvar + 3) && (IORLPModifyPanel.this.step == 2)) {
/*  798 */             IORLPModifyPanel.this.row = selrow;
/*  799 */             IORLPModifyPanel.this.col = selcol;
/*  800 */             IORLPModifyPanel.this.SelBlock(IORLPModifyPanel.this.table, selrow, selcol);
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void clrTableColor(JTable table)
/*      */   {
/*  814 */     for (int i = 0; i < table.getRowCount(); i++) {
/*  815 */       for (int j = 0; j < table.getColumnCount(); j++) {
/*  816 */         Object anycel = table.getModel().getValueAt(i, j);
/*  817 */         if ((anycel instanceof IORTableCell)) {
/*  818 */           ((IORTableCell)anycel).isBlock = false;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void SelBlock(JTable table, int selrow, int selcol) {
/*  825 */     JTable tmptable = table;
/*      */     
/*      */ 
/*      */ 
/*  829 */     if ((selrow > 0) && (selcol >= 3) && (selcol < this.nAllvar + 3) && ((this.step == 2) || (this.step == 3)))
/*      */     {
/*  831 */       clrTableColor(tmptable);
/*  832 */       for (int j = 3; j < this.nAllvar + 4; j++) {
/*  833 */         Object anycel = tmptable.getModel().getValueAt(selrow, j);
/*  834 */         if ((anycel instanceof IORTableCell)) {
/*  835 */           ((IORTableCell)anycel).isBlock = true;
/*      */         }
/*      */       }
/*  838 */       for (int i = 0; i < this.nBasic + 1; i++) {
/*  839 */         Object anycel = tmptable.getModel().getValueAt(i, selcol);
/*  840 */         if ((anycel instanceof IORTableCell))
/*  841 */           ((IORTableCell)anycel).isBlock = true;
/*      */       }
/*      */     }
/*  844 */     tmptable.repaint();
/*      */   }
/*      */   
/*      */   class IORTableModel extends AbstractTableModel {
/*      */     private int i;
/*      */     private int j;
/*      */     private String weirdstr;
/*  851 */     private IORTableCell[][] data = new IORTableCell[IORLPModifyPanel.this.controller.data.num_constrain + 2][IORLPModifyPanel.this.controller.data.num_variable + 2];
/*      */     
/*      */     public IORTableModel() {
/*  854 */       for (this.i = 0; this.i < IORLPModifyPanel.this.controller.data.num_constrain + 2; this.i += 1) {
/*  855 */         for (this.j = 0; this.j < IORLPModifyPanel.this.controller.data.num_variable + 2; this.j += 1) {
/*  856 */           this.data[this.i][this.j] = new IORTableCell(0.0D, 0.0D);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public int getColumnCount() {
/*  862 */       return IORLPModifyPanel.this.controller.data.num_variable + 4;
/*      */     }
/*      */     
/*      */     public int getRowCount() {
/*  866 */       return IORLPModifyPanel.this.controller.data.num_constrain + 1;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col) {
/*  870 */       if (col == 0)
/*  871 */         return "Bas Var";
/*  872 */       if (col == 1)
/*  873 */         return "Eq.";
/*  874 */       if (col == 2)
/*  875 */         return "Z";
/*  876 */       if ((col >= 3) && (col < 3 + IORLPModifyPanel.this.controller.data.num_variable)) {
/*  877 */         if (IORLPModifyPanel.this.controller.data.is_artificial[(col - 2)] != 0) {
/*  878 */           return "X".concat(String.valueOf(String.valueOf(col - 2)));
/*      */         }
/*      */         
/*  881 */         return "x".concat(String.valueOf(String.valueOf(col - 2)));
/*      */       }
/*      */       
/*  884 */       return "Right Side";
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col)
/*      */     {
/*  889 */       int xsub = 0;
/*      */       
/*      */ 
/*  892 */       if (col == 1)
/*  893 */         return new Integer(row);
/*  894 */       if (col == 2) {
/*  895 */         if ((row == 0) && (IORLPModifyPanel.this.controller.data.objective_is_max))
/*  896 */           return new Integer(1);
/*  897 */         if (row == 0) {
/*  898 */           return new Integer(-1);
/*      */         }
/*  900 */         return new Integer(0);
/*      */       }
/*  902 */       if (col == 0) {
/*  903 */         if (row == 0) {
/*  904 */           return "Z";
/*      */         }
/*  906 */         xsub = IORLPModifyPanel.this.controller.data.basic_variable_index[row];
/*  907 */         if (IORLPModifyPanel.this.controller.data.is_artificial[xsub] != 0) {
/*  908 */           return "X".concat(String.valueOf(String.valueOf(xsub)));
/*      */         }
/*  910 */         return "x".concat(String.valueOf(String.valueOf(xsub)));
/*      */       }
/*      */       
/*  913 */       if ((col >= 3) && (col < IORLPModifyPanel.this.controller.data.num_variable + 3)) {
/*  914 */         if (row == 0) {
/*  915 */           double mc = IORLPModifyPanel.this.controller.data.objective_M_coefficient[(col - 2)];
/*  916 */           double ec = IORLPModifyPanel.this.controller.data.objective_coefficient[(col - 2)];
/*  917 */           this.data[row][(col - 3)].setPara(mc, ec);
/*  918 */           return this.data[row][(col - 3)];
/*      */         }
/*      */         
/*  921 */         this.data[row][(col - 3)].setPara(0.0D, IORLPModifyPanel.this.controller.data.constrain_coefficient[row][(col - 2)]);
/*  922 */         return this.data[row][(col - 3)];
/*      */       }
/*      */       
/*      */ 
/*  926 */       if (row == 0) {
/*  927 */         double mc = IORLPModifyPanel.this.controller.data.objective_M_coefficient[0];
/*  928 */         double ec = IORLPModifyPanel.this.controller.data.objective_coefficient[0];
/*  929 */         this.data[row][(col - 3)].setPara(mc, ec);
/*  930 */         return this.data[row][(col - 3)];
/*      */       }
/*      */       
/*  933 */       this.data[row][(col - 3)].setPara(0.0D, IORLPModifyPanel.this.controller.data.constrain_coefficient[row][0]);
/*  934 */       return this.data[row][(col - 3)];
/*      */     }
/*      */     
/*      */ 
/*      */     public Class getColumnClass(int c)
/*      */     {
/*  940 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/*  946 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */   class IORAlgebraModel extends AbstractTableModel
/*      */   {
/*      */     private int i;
/*      */     private int j;
/*  954 */     private IORTableCell[][] data = new IORTableCell[IORLPModifyPanel.this.controller.data.num_constrain + 2][IORLPModifyPanel.this.controller.data.num_variable + 2];
/*      */     
/*      */     public IORAlgebraModel() {
/*  957 */       for (this.i = 0; this.i < IORLPModifyPanel.this.controller.data.num_constrain + 2; this.i += 1) {
/*  958 */         for (this.j = 0; this.j < IORLPModifyPanel.this.controller.data.num_variable + 2; this.j += 1) {
/*  959 */           this.data[this.i][this.j] = new IORTableCell(0.0D, 0.0D);
/*  960 */           this.data[this.i][this.j].setDisplayForm(1);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public int getColumnCount() {
/*  966 */       return IORLPModifyPanel.this.controller.data.num_variable + 4;
/*      */     }
/*      */     
/*      */     public int getRowCount() {
/*  970 */       return IORLPModifyPanel.this.controller.data.num_constrain + 1;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col) {
/*  974 */       if (col == 0)
/*  975 */         return "empty";
/*  976 */       if (col == 1)
/*  977 */         return "Eq.";
/*  978 */       if (col == 2)
/*  979 */         return "Z";
/*  980 */       if ((col >= 3) && (col < 3 + IORLPModifyPanel.this.controller.data.num_variable)) {
/*  981 */         return "x".concat(String.valueOf(String.valueOf(col - 2)));
/*      */       }
/*      */       
/*  984 */       return "Right Side";
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col) {
/*  988 */       int xsub = 0;
/*      */       
/*      */ 
/*  991 */       if (col == 0)
/*  992 */         return " ";
/*  993 */       if (col == 1) {
/*  994 */         return String.valueOf(String.valueOf(row)).concat(")");
/*      */       }
/*  996 */       if (col == 2) {
/*  997 */         if (row == 0) {
/*  998 */           if (IORLPModifyPanel.this.controller.data.original_objective_is_max) {
/*  999 */             return "Z";
/*      */           }
/* 1001 */           return "-Z";
/*      */         }
/*      */         
/* 1004 */         return " ";
/*      */       }
/*      */       
/* 1007 */       if ((col >= 3) && (col < IORLPModifyPanel.this.controller.data.num_variable + 3)) {
/* 1008 */         if (row == 0) {
/* 1009 */           double mc = IORLPModifyPanel.this.controller.data.objective_M_coefficient[(col - 2)];
/* 1010 */           double ec = IORLPModifyPanel.this.controller.data.objective_coefficient[(col - 2)];
/* 1011 */           this.data[row][(col - 3)].setPara(mc, ec);
/* 1012 */           if (IORLPModifyPanel.this.controller.data.is_artificial[(col - 2)] != 0) {
/* 1013 */             this.data[row][(col - 3)].setXstring("X".concat(String.valueOf(String.valueOf(col - 2))));
/*      */           } else
/* 1015 */             this.data[row][(col - 3)].setXstring("x".concat(String.valueOf(String.valueOf(col - 2))));
/* 1016 */           return this.data[row][(col - 3)];
/*      */         }
/*      */         
/* 1019 */         this.data[row][(col - 3)].setPara(0.0D, IORLPModifyPanel.this.controller.data.constrain_coefficient[row][(col - 2)]);
/* 1020 */         if (IORLPModifyPanel.this.controller.data.is_artificial[(col - 2)] != 0) {
/* 1021 */           this.data[row][(col - 3)].setXstring("X".concat(String.valueOf(String.valueOf(col - 2))));
/*      */         } else
/* 1023 */           this.data[row][(col - 3)].setXstring("x".concat(String.valueOf(String.valueOf(col - 2))));
/* 1024 */         return this.data[row][(col - 3)];
/*      */       }
/*      */       
/*      */ 
/* 1028 */       if (row == 0) {
/* 1029 */         double mc = IORLPModifyPanel.this.controller.data.objective_M_coefficient[0];
/* 1030 */         double ec = IORLPModifyPanel.this.controller.data.objective_coefficient[0];
/* 1031 */         this.data[row][(col - 3)].setPara(mc, ec);
/* 1032 */         this.data[row][(col - 3)].setXstring(" = ");
/* 1033 */         return this.data[row][(col - 3)];
/*      */       }
/*      */       
/* 1036 */       this.data[row][(col - 3)].setPara(0.0D, IORLPModifyPanel.this.controller.data.constrain_coefficient[row][0]);
/* 1037 */       this.data[row][(col - 3)].setXstring(" = ");
/* 1038 */       return this.data[row][(col - 3)];
/*      */     }
/*      */     
/*      */ 
/*      */     public Class getColumnClass(int c)
/*      */     {
/* 1044 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1050 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/* 1059 */     String str = "Solve Interactively by the Modified Simplex Method\n\n";
/* 1060 */     str = String.valueOf(String.valueOf(str)).concat("This procedure allows you to apply the Simplex Method interactively ");
/* 1061 */     str = String.valueOf(String.valueOf(str)).concat("to solve a linear programming problem. At each iteration, the computer ");
/* 1062 */     str = String.valueOf(String.valueOf(str)).concat("does the calculations while you make the following sequence of decisions:\n");
/* 1063 */     str = String.valueOf(String.valueOf(str)).concat("    1.  Decide whether or not the current solution is optimal\n");
/* 1064 */     str = String.valueOf(String.valueOf(str)).concat("    2.  Choose the entering basic variable\n");
/* 1065 */     str = String.valueOf(String.valueOf(str)).concat("    3.  Choose the pivot equation containing the leaving basic variable\n");
/* 1066 */     str = String.valueOf(String.valueOf(str)).concat("    4.  Choose the factor by which to divide the pivot equation\n");
/* 1067 */     str = String.valueOf(String.valueOf(str)).concat("    5.  Choose the multiple of the pivot equation to subtract from each of ");
/* 1068 */     str = String.valueOf(String.valueOf(str)).concat("the other equations in turn.\n\n");
/* 1069 */     str = String.valueOf(String.valueOf(str)).concat("Is the current solution optimal? \n");
/* 1070 */     str = String.valueOf(String.valueOf(str)).concat("Enter y or Y and then press the FINISH button if the current solution is optimal. ");
/* 1071 */     str = String.valueOf(String.valueOf(str)).concat("Otherwise, enter either n or N and then press the NEXT button. If there are multiple ");
/* 1072 */     str = String.valueOf(String.valueOf(str)).concat("optima, you can investigate the other optimal solutions. Just enter n or N, and ");
/* 1073 */     str = String.valueOf(String.valueOf(str)).concat("then press the NEXT button.");
/* 1074 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/* 1076 */     this.help_content_step.setText(str);
/* 1077 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/* 1084 */     String str = "Solve Interactively by the Modified Simplex Method\n\n";
/* 1085 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to interactively execute the Modified Simplex Method as ");
/* 1086 */     str = String.valueOf(String.valueOf(str)).concat("presented in Sec.13.7. Your role is to apply the logic of the algorithm, ");
/* 1087 */     str = String.valueOf(String.valueOf(str)).concat("and the computer will do the number crunching. Beware that this particular ");
/* 1088 */     str = String.valueOf(String.valueOf(str)).concat("procedure does not inform you if you make a mistake on the first iteration.\n\n");
/* 1089 */     str = String.valueOf(String.valueOf(str)).concat("This procedure can handle up to 6 functional constraints and 10 variables, ");
/* 1090 */     str = String.valueOf(String.valueOf(str)).concat("which encompasses all relevant problems at the end of Chap. 13.\n\n");
/* 1091 */     str = String.valueOf(String.valueOf(str)).concat("When you finish a problem, you can print out all your work by choosing Pint to File ");
/* 1092 */     str = String.valueOf(String.valueOf(str)).concat("under the File menu. If you are interrupted before you finish, you can ");
/* 1093 */     str = String.valueOf(String.valueOf(str)).concat("save your work (choose Save under the File menu) and return later (choose Open).");
/* 1094 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure. (Be sure to use the ");
/* 1095 */     str = String.valueOf(String.valueOf(str)).concat("Restricted-Entry Rule on Page 687 when you select the entering basic variable.)");
/*      */     
/* 1097 */     this.help_content_procedure.setText(str);
/* 1098 */     this.help_content_procedure.revalidate();
/*      */   }
/*      */   
/*      */ 
/* 1102 */   private String str0 = "Solve Interactively by the Modified Simplex Method\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1112 */   private String str1 = "Is the current solution optimal? \n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1117 */   private String str2 = "Selecting the entering and the leaving basic variable \n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1123 */   private String str3 = "Choosing the multiple of the pivot equation to subtract from another equation\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1128 */   private String str4 = "What to do once an optimal solution has been reached\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void updateStepHelpPanel()
/*      */   {
/* 1136 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/* 1137 */     switch (this.step) {
/*      */     case 1: 
/* 1139 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str1).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1154 */       break;
/*      */     case 2: 
/* 1142 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str2).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1154 */       break;
/*      */     case 3: 
/* 1145 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str3).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1154 */       break;
/*      */     case 4: 
/* 1148 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str4).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1154 */       break;
/*      */     default: 
/* 1151 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPModifyPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */