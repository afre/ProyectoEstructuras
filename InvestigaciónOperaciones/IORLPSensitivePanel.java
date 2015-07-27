/*      */ import java.awt.BorderLayout;
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
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextArea;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.ListSelectionModel;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.table.AbstractTableModel;
/*      */ import javax.swing.table.DefaultTableCellRenderer;
/*      */ import javax.swing.table.TableColumn;
/*      */ import javax.swing.table.TableColumnModel;
/*      */ import javax.swing.table.TableModel;
/*      */ 
/*      */ public class IORLPSensitivePanel extends IORLPActionPanel
/*      */ {
/*   32 */   private JPanel mainPanel = new JPanel();
/*   33 */   private IORLPSensitivePanel.IORFirstTableModel initModel = null;
/*   34 */   private IORLPSensitivePanel.IORTableModel finalModel = null;
/*   35 */   private JTable initable = null;
/*   36 */   private JTable finatable = null;
/*   37 */   private JPanel inlabPanel = new JPanel();
/*   38 */   private JPanel fnalabPanel = new JPanel();
/*   39 */   private JScrollPane upscrollPane = null;
/*   40 */   private JScrollPane lowscrollPane = null;
/*   41 */   private JPanel upperPanel = new JPanel();
/*   42 */   private JPanel lowerPanel = new JPanel();
/*   43 */   private JTextArea statetext = new JTextArea("Select one of the numbers you would like to change in the \ninitial tableau and change it to a new value.");
/*      */   
/*   45 */   private JPanel statePanel = new JPanel();
/*   46 */   private JPanel buttPanel = new JPanel();
/*   47 */   private JButton backbutt = new JButton("Back");
/*   48 */   private JButton nextbutt = new JButton("Next");
/*   49 */   private JLabel commlabel = new JLabel("Select a command");
/*      */   
/*      */ 
/*   52 */   private int step = 1;
/*   53 */   private Vector opseq = new Vector();
/*   54 */   private Vector chgcell = new Vector();
/*   55 */   private Vector selblock = new Vector();
/*      */   
/*   57 */   private JTextField yorn = new JTextField("Y", 2);
/*   58 */   private JLabel inputlabel = new JLabel("Input a new value:");
/*   59 */   private JTextField newval = new JTextField(6);
/*   60 */   private JComboBox commandbox = new JComboBox();
/*      */   private int rowStart;
/*      */   private int rowEnd;
/*   63 */   private int colStart; private int colEnd; private boolean hasSelected = false;
/*      */   private boolean isOnevalue;
/*   65 */   private int selmode = 0;
/*      */   
/*      */ 
/*      */ 
/*      */   private int nOrgvar;
/*      */   
/*      */ 
/*      */ 
/*      */   private int nBasic;
/*      */   
/*      */ 
/*      */   private int nAllvar;
/*      */   
/*      */ 
/*      */ 
/*      */   protected void backStep() {}
/*      */   
/*      */ 
/*      */ 
/*      */   public void updatePanel() {}
/*      */   
/*      */ 
/*      */ 
/*      */   public IORLPSensitivePanel(IORLPProcessController c)
/*      */   {
/*   90 */     super(c);
/*      */     
/*   92 */     this.state = new IORState(c.solver);
/*   93 */     add(this.mainPanel, "Center");
/*   94 */     this.bn_back.setVisible(false);
/*      */     
/*      */ 
/*   97 */     this.nOrgvar = this.controller.data.num_original_variable;
/*   98 */     this.nBasic = this.controller.data.num_constrain;
/*   99 */     this.nAllvar = this.controller.data.num_variable;
/*      */     
/*      */ 
/*  102 */     this.initModel = new IORLPSensitivePanel.IORFirstTableModel();
/*  103 */     this.finalModel = new IORLPSensitivePanel.IORTableModel();
/*      */     
/*  105 */     this.initable = new JTable(this.initModel);
/*  106 */     this.finatable = new JTable(this.finalModel);
/*  107 */     this.upscrollPane = new JScrollPane(this.initable);
/*  108 */     this.lowscrollPane = new JScrollPane(this.finatable);
/*      */     
/*      */ 
/*  111 */     this.initable.setPreferredScrollableViewportSize(new Dimension(700, (this.nBasic + 1) * 20 + 25));
/*  112 */     this.finatable.setPreferredScrollableViewportSize(new Dimension(700, (this.nBasic + 1) * 20 + 25));
/*  113 */     this.initable.setCellSelectionEnabled(true);
/*  114 */     this.finatable.setCellSelectionEnabled(true);
/*  115 */     this.initable.setRowSelectionAllowed(true);
/*  116 */     this.initable.setColumnSelectionAllowed(true);
/*      */     
/*      */ 
/*  119 */     DefaultTableCellRenderer selectedRenderer = new DefaultTableCellRenderer()
/*      */     {
/*      */       public void setValue(Object value) {
/*  122 */         if ((value instanceof IORTableCell)) {
/*  123 */           if (((IORTableCell)value).isChanged == true) {
/*  124 */             setForeground(Color.red);
/*      */           }
/*      */           else {
/*  127 */             setForeground(Color.black);
/*      */           }
/*      */           
/*  130 */           if (((IORTableCell)value).isBlock == true) {
/*  131 */             setBackground(Color.cyan);
/*  132 */           } else if (((IORTableCell)value).isChanged == true) {
/*  133 */             setBackground(Color.lightGray);
/*      */           }
/*      */           else {
/*  136 */             setBackground(Color.white);
/*      */           }
/*      */         }
/*  139 */         super.setValue(value);
/*      */       }
/*      */     };
/*      */     
/*      */ 
/*  144 */     for (int i = 3; i < this.initable.getColumnCount(); i++) {
/*  145 */       this.initable.getColumn(this.initable.getColumnName(i)).setCellRenderer(selectedRenderer);
/*  146 */       this.finatable.getColumn(this.finatable.getColumnName(i)).setCellRenderer(selectedRenderer);
/*      */     }
/*  148 */     for (i = 0; i < 3; i++) {
/*  149 */       this.initable.getColumn(this.initable.getColumnName(i)).setPreferredWidth(20);
/*  150 */       this.finatable.getColumn(this.finatable.getColumnName(i)).setPreferredWidth(20);
/*      */     }
/*      */     
/*      */ 
/*  154 */     this.initable.setSelectionMode(0);
/*  155 */     this.finatable.setSelectionMode(0);
/*      */     
/*      */ 
/*  158 */     addSelectionListener(this.initable);
/*  159 */     addSelectionListener(this.finatable);
/*      */     
/*  161 */     this.initable.setRowHeight(20);
/*  162 */     this.finatable.setRowHeight(20);
/*      */     
/*      */ 
/*  165 */     this.statetext.setBackground(getBackground());
/*  166 */     this.statetext.setForeground(Color.blue);
/*  167 */     this.statetext.setEditable(false);
/*  168 */     this.statetext.setPreferredSize(new Dimension(450, 50));
/*  169 */     this.statetext.setLineWrap(true);
/*  170 */     this.inputlabel.setLabelFor(this.newval);
/*      */     
/*  172 */     this.statePanel.add(this.statetext);
/*  173 */     this.statePanel.add(Box.createRigidArea(new Dimension(10, 20)));
/*      */     
/*  175 */     this.statePanel.add(this.inputlabel);
/*  176 */     this.statePanel.add(this.newval);
/*      */     
/*      */ 
/*  179 */     this.commandbox.addItem("Revert to the original tableau");
/*  180 */     this.commandbox.addItem("Convert the final tableau to its proper form");
/*  181 */     this.commandbox.addItem("Apply Simplex Method");
/*  182 */     this.commandbox.addItem("Apply dual Simplex Method");
/*  183 */     this.commandbox.addItem("The current solution is optimal");
/*      */     
/*  185 */     this.commandbox.setSelectedIndex(1);
/*  186 */     this.commlabel.setLabelFor(this.commandbox);
/*      */     
/*  188 */     this.inlabPanel.setLayout(new BoxLayout(this.inlabPanel, 1));
/*  189 */     this.inlabPanel.add(new JLabel("  Original "));
/*  190 */     this.inlabPanel.add(new JLabel("  Initial "));
/*  191 */     this.inlabPanel.add(new JLabel("  Tableau "));
/*  192 */     this.inlabPanel.add(new JLabel("(in Proper"));
/*  193 */     this.inlabPanel.add(new JLabel("   Form )"));
/*  194 */     this.upperPanel.setLayout(new BorderLayout());
/*  195 */     this.upperPanel.add(this.upscrollPane);
/*  196 */     this.upperPanel.add(this.inlabPanel, "East");
/*      */     
/*  198 */     this.fnalabPanel.setLayout(new BoxLayout(this.fnalabPanel, 1));
/*  199 */     this.fnalabPanel.add(new JLabel("  Original "));
/*  200 */     this.fnalabPanel.add(new JLabel("  Final "));
/*  201 */     this.fnalabPanel.add(new JLabel("  Tableau "));
/*  202 */     this.lowerPanel.setLayout(new BorderLayout());
/*  203 */     this.lowerPanel.add(this.lowscrollPane);
/*  204 */     this.lowerPanel.add(this.fnalabPanel, "East");
/*      */     
/*      */ 
/*      */ 
/*  208 */     this.buttPanel.add(this.backbutt);
/*      */     
/*  210 */     this.buttPanel.add(Box.createRigidArea(new Dimension(10, 30)));
/*      */     
/*  212 */     this.buttPanel.add(this.nextbutt);
/*      */     
/*      */ 
/*  215 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*      */     
/*  217 */     this.mainPanel.setBorder(new javax.swing.border.EmptyBorder(0, 10, 0, 10));
/*      */     
/*  219 */     this.mainPanel.add(this.upperPanel);
/*      */     
/*  221 */     this.mainPanel.add(Box.createVerticalStrut(20));
/*      */     
/*  223 */     this.mainPanel.add(this.lowerPanel);
/*  224 */     this.mainPanel.add(Box.createVerticalStrut(10));
/*  225 */     this.mainPanel.add(this.statePanel);
/*  226 */     this.mainPanel.add(Box.createVerticalStrut(20));
/*  227 */     this.mainPanel.add(this.buttPanel);
/*      */     
/*      */ 
/*      */ 
/*  231 */     this.nextbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  233 */         IORLPSensitivePanel.this.doNext();
/*      */       }
/*  235 */     });
/*  236 */     this.backbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  238 */         IORLPSensitivePanel.this.doBack();
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void finishProcedure()
/*      */   {
/*  248 */     setFinishEnabled(false);
/*      */   }
/*      */   
/*      */   private void doBack()
/*      */   {
/*  253 */     int comindex = 0;
/*      */     
/*  255 */     int[] cellpos = new int[2];
/*      */     
/*      */ 
/*  258 */     if (this.opseq.isEmpty() == false) {
/*  259 */       int last = ((Integer)this.opseq.lastElement()).intValue();
/*  260 */       this.opseq.removeElementAt(this.opseq.size() - 1);
/*      */       
/*  262 */       this.initable.clearSelection();
/*  263 */       this.finatable.clearSelection();
/*  264 */       switch (last)
/*      */       {
/*      */ 
/*      */       case 1: 
/*  268 */         this.controller.solver.resetSens();
/*  269 */         this.state.back();
/*      */         
/*  271 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */         int sz;
/*  274 */         if ((sz = this.chgcell.size()) > 0) {
/*  275 */           cellpos = (int[])this.chgcell.elementAt(sz - 1);
/*  276 */           Object rc = this.initModel.getValueAt(cellpos[0], cellpos[1]);
/*  277 */           ((IORTableCell)rc).isChanged = false;
/*  278 */           this.chgcell.removeElementAt(sz - 1);
/*      */         }
/*      */         
/*  281 */         this.statetext.setText("Select one of the numbers you would like to change in the initial\ntableau and change it to a new value.");
/*      */         
/*      */ 
/*  284 */         this.statePanel.remove(this.yorn);
/*      */         
/*  286 */         this.newval.setText("");
/*  287 */         this.statePanel.add(this.inputlabel);
/*  288 */         this.statePanel.add(this.newval);
/*      */         
/*      */ 
/*  291 */         this.selmode = 0;
/*      */         
/*      */ 
/*  294 */         this.step = 1;
/*  295 */         this.initModel.fireTableDataChanged();
/*  296 */         revalidate();
/*  297 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  483 */         break;
/*      */       case 2: 
/*  300 */         this.statetext.setText("\nWould you like to make additional changes to the initial tableau?");
/*      */         
/*  302 */         this.statePanel.add(this.yorn);
/*      */         
/*  304 */         this.statePanel.remove(this.newval);
/*  305 */         this.statePanel.remove(this.inputlabel);
/*  306 */         clrTableColor(this.initable);
/*  307 */         clrTableColor(this.finatable);
/*  308 */         this.step = 2;
/*  309 */         revalidate();
/*  310 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  483 */         break;
/*      */       case 3: 
/*  315 */         this.controller.solver.resetSens();
/*  316 */         this.state.back();
/*      */         
/*  318 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  320 */         clrTableColor(this.initable);
/*  321 */         clrTableColor(this.finatable);
/*      */         
/*  323 */         int sz = this.selblock.size();
/*  324 */         if (sz > 0) {
/*  325 */           int[] loc = (int[])this.selblock.elementAt(sz - 1);
/*      */           
/*  327 */           this.selblock.removeElementAt(sz - 1);
/*      */         }
/*      */         
/*      */ 
/*  331 */         this.selmode = 1;
/*      */         
/*  333 */         this.step = 3;
/*  334 */         this.statetext.setText("Select a location in the final tableau where a change might occur and \npress the NEXT button.");
/*      */         
/*  336 */         revalidate();
/*  337 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  483 */         break;
/*      */       case 4: 
/*  341 */         this.controller.solver.resetSens();
/*  342 */         this.state.back();
/*      */         
/*  344 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  346 */         this.step = 4;
/*      */         
/*      */ 
/*  349 */         clrTableColor(this.finatable);
/*  350 */         int sz = this.selblock.size();
/*  351 */         if (sz > 0) {
/*  352 */           int[] loc = (int[])this.selblock.elementAt(sz - 1);
/*      */           
/*  354 */           this.selblock.removeElementAt(sz - 1);
/*      */         }
/*      */         
/*  357 */         if (this.isOnevalue == true) {
/*  358 */           this.statetext.setText("To calculate a new value, we will need to multiply a row vector \ntimes a column vector plus a constant. Locate the row vector,\nby selecting its first element, and then press the NEXT button.");
/*      */           
/*      */ 
/*      */ 
/*  362 */           this.selmode = 2;
/*      */         }
/*      */         else {
/*  365 */           this.statetext.setText("To calculate new values for this column, we will need to multiply \na matrix times a column vector. Locate the matrix by selecting \nits elements, and then press the NEXT button.");
/*      */           
/*      */ 
/*      */ 
/*  369 */           this.selmode = 4;
/*      */         }
/*  371 */         revalidate();
/*  372 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  483 */         break;
/*      */       case 5: 
/*  376 */         this.controller.solver.resetSens();
/*  377 */         this.state.back();
/*      */         
/*  379 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  381 */         if (this.step == 6) {
/*  382 */           this.isOnevalue = true;
/*  383 */           this.statetext.setText("To calculate a new value, we will need to multiply a row vector \ntimes a column vector plus a constant. Locate the column vector,\nby selecting its elements, and then press the NEXT button.");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  388 */           this.isOnevalue = false;
/*      */           
/*  390 */           this.statetext.setText("To calculate new values for this column, we will need to multiply \na matrix times a column vector. Locate the column vector,\nby selecting its elements, and then press the NEXT button.");
/*      */           
/*      */ 
/*      */ 
/*  394 */           if (this.statePanel.isAncestorOf(this.yorn)) {
/*  395 */             this.statePanel.remove(this.yorn);
/*      */           }
/*      */           
/*  398 */           this.finalModel.fireTableDataChanged();
/*      */         }
/*      */         
/*      */ 
/*  402 */         clrTableColor(this.initable);
/*  403 */         int sz = this.selblock.size();
/*  404 */         if (sz > 0) {
/*  405 */           int[] loc = (int[])this.selblock.elementAt(sz - 1);
/*      */           
/*  407 */           this.selblock.removeElementAt(sz - 1);
/*      */         }
/*      */         
/*  410 */         this.selmode = 3;
/*      */         
/*  412 */         this.step = 5;
/*  413 */         revalidate();
/*  414 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  483 */         break;
/*      */       case 6: 
/*  418 */         this.controller.solver.resetSens();
/*  419 */         this.state.back();
/*      */         
/*  421 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  423 */         this.statetext.setText("To calculate a new value, we will need to multiply a row vector \ntimes a column vector plus a constant. Select the constant, \nand then press the NEXT button.");
/*      */         
/*      */ 
/*      */ 
/*  427 */         if (this.statePanel.isAncestorOf(this.yorn)) {
/*  428 */           this.statePanel.remove(this.yorn);
/*      */         }
/*      */         
/*      */ 
/*  432 */         clrTableColor(this.initable);
/*  433 */         int sz = this.selblock.size();
/*  434 */         if (sz > 0) {
/*  435 */           int[] loc = (int[])this.selblock.elementAt(sz - 1);
/*      */           
/*  437 */           this.selblock.removeElementAt(sz - 1);
/*      */         }
/*      */         
/*  440 */         this.selmode = 5;
/*      */         
/*  442 */         this.step = 6;
/*      */         
/*      */ 
/*  445 */         this.finalModel.fireTableDataChanged();
/*  446 */         revalidate();
/*  447 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  483 */         break;
/*      */       case 7: 
/*  450 */         this.statePanel.removeAll();
/*  451 */         this.statetext.setText("Are there more locations in the final tableau that might be \naffected by the change(s) in the initial tableau? ");
/*      */         
/*      */ 
/*  454 */         this.statePanel.add(this.statetext);
/*  455 */         this.statePanel.add(this.yorn);
/*      */         
/*  457 */         clrTableColor(this.initable);
/*  458 */         clrTableColor(this.finatable);
/*      */         
/*  460 */         this.selmode = 0;
/*  461 */         this.step = 7;
/*  462 */         revalidate();
/*  463 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  483 */         break;
/*      */       case 8: 
/*  467 */         this.controller.solver.resetSens();
/*  468 */         this.state.back();
/*      */         
/*  470 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  472 */         this.step = 8;
/*      */         
/*  474 */         this.finalModel.fireTableDataChanged();
/*  475 */         revalidate();
/*  476 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  483 */         break;
/*      */       default: 
/*  479 */         System.out.println("no this step");
/*      */         
/*      */ 
/*      */ 
/*  483 */         break; }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*  488 */   private void doNext() { int comindex = 0;
/*  489 */     int[] intPar = new int[2];int[] cellpos = new int[2];
/*  490 */     double dblPar = 1.0D;
/*      */     
/*      */ 
/*  493 */     int[] pos = new int[4];
/*      */     
/*  495 */     Vector par = new Vector();
/*  496 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*  498 */     switch (this.step) {
/*      */     case 1: 
/*  500 */       if (!this.hasSelected) {
/*  501 */         JOptionPane.showMessageDialog(this, "You must choose a number to change and then press the NEXT button.");
/*      */ 
/*      */       }
/*  504 */       else if (this.colStart < 3) {
/*  505 */         JOptionPane.showMessageDialog(this, "Invalid selection, you can only choose those allowed to be changed.");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  510 */         intPar[0] = this.rowStart;
/*  511 */         intPar[1] = (this.colStart - 2);
/*  512 */         String input = this.newval.getText();
/*      */         try {
/*  514 */           dblPar = Double.parseDouble(input);
/*      */         } catch (NumberFormatException e) {
/*  516 */           JOptionPane.showMessageDialog(this, "Invalid input. Please try again!");
/*  517 */           break;
/*      */         }
/*      */         
/*  520 */         par.addElement(intPar);
/*  521 */         par.addElement(new Double(dblPar));
/*  522 */         opr.operation_code = 10501;
/*  523 */         opr.parameters = par;
/*  524 */         if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  525 */           JOptionPane.showMessageDialog(this, "Error in changing the value.");
/*      */         }
/*      */         else
/*      */         {
/*  529 */           this.state.addStep(opr);
/*      */           
/*  531 */           this.opseq.addElement(new Integer(this.step));
/*      */           
/*      */ 
/*  534 */           Object ac = this.initable.getModel().getValueAt(this.rowStart, this.colStart);
/*  535 */           ((IORTableCell)ac).isChanged = true;
/*  536 */           ((IORTableCell)ac).isBlock = false;
/*  537 */           cellpos[0] = this.rowStart;
/*  538 */           cellpos[1] = this.colStart;
/*  539 */           this.chgcell.addElement(cellpos);
/*      */           
/*  541 */           this.step = 2;
/*      */           
/*      */ 
/*  544 */           this.initModel.fireTableDataChanged();
/*      */           
/*  546 */           this.statetext.setText("\nWould you like to make additional changes to the initial tableau?");
/*      */           
/*  548 */           this.statePanel.removeAll();
/*  549 */           this.statePanel.add(this.statetext);
/*  550 */           this.statePanel.add(this.yorn);
/*  551 */           revalidate();
/*  552 */           repaint();
/*      */         } }
/*  554 */       break;
/*      */     case 2: 
/*  556 */       char sel = this.yorn.getText().charAt(0);
/*      */       
/*      */ 
/*  559 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*  561 */       if ((sel == 'Y') || (sel == 'y')) {
/*  562 */         this.newval.setText("");
/*  563 */         this.statetext.setText("Select one of the numbers you would like to change in the initial \ntableau and change it to a new value.");
/*      */         
/*  565 */         this.statePanel.removeAll();
/*  566 */         this.statePanel.add(this.statetext);
/*  567 */         this.statePanel.add(Box.createRigidArea(new Dimension(10, 20)));
/*  568 */         this.statePanel.add(this.inputlabel);
/*  569 */         this.statePanel.add(this.newval);
/*  570 */         this.step = 1;
/*      */       }
/*      */       else
/*      */       {
/*  574 */         this.selmode = 1;
/*      */         
/*  576 */         this.statePanel.remove(this.yorn);
/*  577 */         this.statetext.setText("Select a location in the final tableau where a change might occur and\npress the NEXT button.");
/*      */         
/*  579 */         this.step = 3;
/*      */       }
/*  581 */       this.initable.clearSelection();
/*  582 */       this.finatable.clearSelection();
/*  583 */       revalidate();
/*  584 */       repaint();
/*  585 */       break;
/*      */     case 3: 
/*  587 */       if (!this.hasSelected) {
/*  588 */         JOptionPane.showMessageDialog(this, "You must choose an effective location and then press the NEXT button.");
/*      */ 
/*      */       }
/*  591 */       else if (this.colStart < 3) {
/*  592 */         JOptionPane.showMessageDialog(this, "Invalid selection. Please try again!");
/*      */       }
/*      */       else
/*      */       {
/*  596 */         intPar[0] = this.rowStart;
/*  597 */         intPar[1] = (this.colStart - 2);
/*  598 */         par.addElement(intPar);
/*  599 */         opr.operation_code = 10502;
/*  600 */         opr.parameters = par;
/*  601 */         if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  602 */           String err = this.controller.solver.getErrInfo();
/*  603 */           JOptionPane.showMessageDialog(this, err);
/*      */         }
/*      */         else
/*      */         {
/*  607 */           this.opseq.addElement(new Integer(this.step));
/*      */           
/*  609 */           this.state.addStep(opr);
/*      */           
/*  611 */           pos[0] = this.rowStart;
/*  612 */           pos[1] = this.rowEnd;
/*  613 */           pos[2] = this.colStart;
/*  614 */           pos[3] = this.colEnd;
/*  615 */           this.selblock.addElement(pos);
/*      */           
/*  617 */           if (this.rowStart == 0)
/*      */           {
/*  619 */             this.selmode = 2;
/*  620 */             this.isOnevalue = true;
/*  621 */             this.statetext.setText("To calculate a new value, we will need to multiply a row vector \ntimes a column vector plus a constant. Locate the row vector,\nby selecting its first element, and then press the NEXT button.");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*  627 */             this.selmode = 4;
/*  628 */             this.isOnevalue = false;
/*  629 */             this.statetext.setText("To calculate new values for this column, we will need to multiply\na matrix times a column vector. Locate the matrix by selecting \nits elements, and then press the NEXT button.");
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  634 */           this.step = 4;
/*  635 */           this.initable.clearSelection();
/*  636 */           this.finatable.clearSelection();
/*  637 */           revalidate();
/*  638 */           repaint();
/*      */         } }
/*  640 */       break;
/*      */     case 4: 
/*  642 */       if (!this.hasSelected) {
/*  643 */         JOptionPane.showMessageDialog(this, "You must choose a row vector or matrix and then press the NEXT button.");
/*      */ 
/*      */       }
/*  646 */       else if (this.colStart < 3) {
/*  647 */         JOptionPane.showMessageDialog(this, "Invalid selection. Please try again!");
/*      */       }
/*      */       else
/*      */       {
/*  651 */         intPar[0] = this.rowStart;
/*  652 */         intPar[1] = (this.colStart - 2);
/*  653 */         par.addElement(intPar);
/*  654 */         if (this.isOnevalue == true)
/*      */         {
/*  656 */           opr.operation_code = 10503;
/*  657 */           this.statetext.setText("To calculate a new value, we will need to multiply a row vector \ntimes a column vector plus a constant. Locate the column vector,\nby selecting its elements, and then press the NEXT button.");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  662 */           opr.operation_code = 10504;
/*  663 */           this.statetext.setText("To calculate new values for this column, we will need to multiply \na matrix times a column vector. Locate the column vector,\nby selecting its elements, and then press the NEXT button.");
/*      */         }
/*      */         
/*      */ 
/*  667 */         opr.parameters = par;
/*  668 */         this.controller.solver.doWork(opr, this.controller.data);
/*      */         
/*  670 */         this.state.addStep(opr);
/*      */         
/*  672 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/*  675 */         pos[0] = this.rowStart;
/*  676 */         pos[1] = this.rowEnd;
/*  677 */         pos[2] = this.colStart;
/*  678 */         pos[3] = this.colEnd;
/*  679 */         this.selblock.addElement(pos);
/*      */         
/*      */ 
/*  682 */         this.selmode = 3;
/*  683 */         this.step = 5;
/*  684 */         this.initable.clearSelection();
/*  685 */         this.finatable.clearSelection();
/*  686 */         invalidate();
/*  687 */         repaint(); }
/*  688 */       break;
/*      */     case 5: 
/*  690 */       if (!this.hasSelected) {
/*  691 */         JOptionPane.showMessageDialog(this, "You must choose a column vector and then press the NEXT button.");
/*      */ 
/*      */       }
/*  694 */       else if (this.colStart < 3) {
/*  695 */         JOptionPane.showMessageDialog(this, "Invalid selection. Please try again!");
/*      */       }
/*      */       else
/*      */       {
/*  699 */         if (this.isOnevalue == true)
/*      */         {
/*  701 */           intPar[0] = this.rowStart;
/*  702 */           intPar[1] = (this.colStart - 2);
/*  703 */           par.addElement(intPar);
/*  704 */           opr.operation_code = 10505;
/*  705 */           opr.parameters = par;
/*  706 */           this.controller.solver.doWork(opr, this.controller.data);
/*      */           
/*      */ 
/*  709 */           this.state.addStep(opr);
/*      */           
/*  711 */           this.opseq.addElement(new Integer(this.step));
/*      */           
/*  713 */           this.statetext.setText("To calculate a new value, we will need to multiply a row vector \ntimes a column vector plus a constant. Select the constant, \nand then press the NEXT button.");
/*      */           
/*      */ 
/*      */ 
/*  717 */           this.selmode = 5;
/*  718 */           this.step = 6;
/*      */         }
/*      */         else
/*      */         {
/*  722 */           intPar[0] = this.rowStart;
/*  723 */           intPar[1] = (this.colStart - 2);
/*  724 */           par.addElement(intPar);
/*  725 */           opr.operation_code = 10508;
/*  726 */           opr.parameters = par;
/*  727 */           this.controller.solver.doWork(opr, this.controller.data);
/*      */           
/*      */ 
/*  730 */           this.state.addStep(opr);
/*      */           
/*  732 */           this.opseq.addElement(new Integer(this.step));
/*      */           
/*  734 */           this.statetext.setText("Are there more locations in the final tableau that might be \naffected by the change(s) in the initial tableau? ");
/*      */           
/*      */ 
/*  737 */           this.statePanel.add(this.yorn);
/*      */           
/*      */ 
/*  740 */           this.selmode = 0;
/*  741 */           this.step = 7;
/*      */           
/*  743 */           clrTableColor(this.initable);
/*  744 */           clrTableColor(this.finatable);
/*      */           
/*  746 */           this.finalModel.fireTableDataChanged();
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  751 */         pos[0] = this.rowStart;
/*  752 */         pos[1] = this.rowEnd;
/*  753 */         pos[2] = this.colStart;
/*  754 */         pos[3] = this.colEnd;
/*  755 */         this.selblock.addElement(pos);
/*      */         
/*  757 */         this.initable.clearSelection();
/*  758 */         this.finatable.clearSelection();
/*  759 */         revalidate();
/*  760 */         repaint(); }
/*  761 */       break;
/*      */     case 6: 
/*  763 */       if (!this.hasSelected) {
/*  764 */         JOptionPane.showMessageDialog(this, "You must choose a constant and then press the NEXT button.");
/*      */ 
/*      */       }
/*  767 */       else if (this.colStart < 3) {
/*  768 */         JOptionPane.showMessageDialog(this, "Invalid selection. Please try again!");
/*      */       }
/*      */       else
/*      */       {
/*  772 */         intPar[0] = this.rowStart;
/*  773 */         intPar[1] = (this.colStart - 2);
/*  774 */         par.addElement(intPar);
/*  775 */         opr.operation_code = 10507;
/*  776 */         opr.parameters = par;
/*  777 */         this.controller.solver.doWork(opr, this.controller.data);
/*      */         
/*      */ 
/*  780 */         this.state.addStep(opr);
/*      */         
/*  782 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/*  785 */         this.finalModel.fireTableDataChanged();
/*      */         
/*  787 */         this.statetext.setText("Are there more locations in the final tableau that might be affected\nby the change(s) in the initial tableau? ");
/*      */         
/*      */ 
/*  790 */         this.statePanel.add(this.yorn);
/*      */         
/*      */ 
/*  793 */         pos[0] = this.rowStart;
/*  794 */         pos[1] = this.rowEnd;
/*  795 */         pos[2] = this.colStart;
/*  796 */         pos[3] = this.colEnd;
/*  797 */         this.selblock.addElement(pos);
/*      */         
/*      */ 
/*  800 */         this.selmode = 0;
/*      */         
/*  802 */         clrTableColor(this.initable);
/*  803 */         clrTableColor(this.finatable);
/*  804 */         this.step = 7;
/*  805 */         revalidate();
/*  806 */         repaint(); }
/*  807 */       break;
/*      */     case 7: 
/*  809 */       char sel2 = this.yorn.getText().charAt(0);
/*      */       
/*  811 */       this.opseq.addElement(new Integer(this.step));
/*  812 */       if ((sel2 == 'Y') || (sel2 == 'y')) {
/*  813 */         clrTableColor(this.initable);
/*  814 */         clrTableColor(this.finatable);
/*      */         
/*  816 */         this.selmode = 1;
/*  817 */         this.initable.clearSelection();
/*  818 */         this.finatable.clearSelection();
/*  819 */         this.step = 3;
/*      */         
/*  821 */         this.statePanel.remove(this.yorn);
/*  822 */         this.statetext.setText("Select a location in the final tableau where a change might occur and\npress the NEXT button.");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  827 */         this.statePanel.removeAll();
/*  828 */         this.statePanel.add(this.commlabel);
/*  829 */         this.statePanel.add(this.commandbox);
/*  830 */         this.step = 8;
/*      */       }
/*  832 */       revalidate();
/*  833 */       repaint();
/*  834 */       break;
/*      */     case 8: 
/*  836 */       comindex = this.commandbox.getSelectedIndex();
/*  837 */       if (comindex == 0)
/*      */       {
/*  839 */         opr.operation_code = 10509;
/*  840 */         opr.parameters = par;
/*  841 */         this.controller.solver.doWork(opr, this.controller.data);
/*      */         
/*  843 */         this.state.addStep(opr);
/*      */         
/*      */ 
/*  846 */         this.opseq.removeAllElements();
/*      */         
/*  848 */         this.step = 1;
/*  849 */         this.statetext.setText("Select one of the numbers you would like to change in the initial \ntableau and change it to a new value.");
/*      */         
/*  851 */         this.statePanel.removeAll();
/*  852 */         this.statePanel.add(this.statetext);
/*  853 */         this.statePanel.add(Box.createRigidArea(new Dimension(10, 20)));
/*  854 */         this.statePanel.add(this.inputlabel);
/*  855 */         this.statePanel.add(this.newval);
/*      */         
/*      */ 
/*  858 */         this.initModel.fireTableDataChanged();
/*  859 */         this.finalModel.fireTableDataChanged();
/*      */         int sz;
/*  861 */         while ((sz = this.chgcell.size()) > 0) {
/*  862 */           cellpos = (int[])this.chgcell.elementAt(sz - 1);
/*  863 */           Object rc = this.initModel.getValueAt(cellpos[0], cellpos[1]);
/*  864 */           ((IORTableCell)rc).isChanged = false;
/*  865 */           this.chgcell.removeElementAt(sz - 1);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  870 */       if (comindex == 1) {
/*  871 */         opr.operation_code = 105010;
/*  872 */       } else if (comindex == 2) {
/*  873 */         opr.operation_code = 10511;
/*  874 */       } else if (comindex == 3) {
/*  875 */         opr.operation_code = 10512;
/*      */       }
/*      */       
/*  878 */       opr.parameters = par;
/*  879 */       if (this.controller.solver.doWork(opr, this.controller.data) == true)
/*      */       {
/*  881 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*  883 */         this.state.addStep(opr);
/*      */         
/*      */ 
/*  886 */         this.finalModel.fireTableDataChanged();
/*      */       }
/*      */       else {
/*  889 */         String err = this.controller.solver.getErrInfo();
/*  890 */         JOptionPane.showMessageDialog(this, err);
/*      */       }
/*      */       
/*      */ 
/*  894 */       revalidate();
/*  895 */       repaint();
/*  896 */       break;
/*      */     default: 
/*  898 */       System.out.println("no this step");
/*      */     }
/*      */     
/*  901 */     this.hasSelected = false;
/*  902 */     this.initable.clearSelection();
/*  903 */     this.finatable.clearSelection();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void LoadFile(ObjectInputStream in)
/*      */   {
/*  916 */     int[] para = new int[5];int[] cellpos = new int[2];int[] blkrect = new int[4];
/*      */     try
/*      */     {
/*  919 */       this.state = ((IORState)in.readObject());
/*  920 */       para = (int[])in.readObject();
/*  921 */       this.isOnevalue = ((Boolean)in.readObject()).booleanValue();
/*  922 */       this.opseq = ((Vector)in.readObject());
/*  923 */       this.chgcell = ((Vector)in.readObject());
/*  924 */       this.selblock = ((Vector)in.readObject());
/*  925 */       in.close();
/*      */     }
/*      */     catch (Exception e1) {
/*  928 */       e1.printStackTrace();
/*  929 */       System.out.println("Open fails");
/*      */     }
/*      */     
/*  932 */     this.step = para[0];
/*  933 */     this.rowStart = para[1];
/*  934 */     this.rowEnd = para[2];
/*  935 */     this.colStart = para[3];
/*  936 */     this.colEnd = para[4];
/*      */     
/*  938 */     int sz = this.chgcell.size();
/*  939 */     for (int i = 0; i < sz; i++) {
/*  940 */       cellpos = (int[])this.chgcell.elementAt(i);
/*  941 */       Object rc = this.initModel.getValueAt(cellpos[0], cellpos[1]);
/*  942 */       ((IORTableCell)rc).isChanged = true;
/*      */     }
/*  944 */     this.initModel.fireTableDataChanged();
/*  945 */     this.initable.repaint();
/*      */     
/*  947 */     this.hasSelected = false;
/*      */     
/*  949 */     this.mainPanel.removeAll();
/*  950 */     switch (this.step) {
/*      */     case 1: 
/*  952 */       this.selmode = 0;
/*  953 */       break;
/*      */     case 2: 
/*  955 */       this.initModel.fireTableDataChanged();
/*  956 */       this.statetext.setText("\nWould you like to make additional changes to the initial tableau?");
/*      */       
/*  958 */       this.statePanel.removeAll();
/*  959 */       this.statePanel.add(this.statetext);
/*  960 */       this.statePanel.add(this.yorn);
/*  961 */       break;
/*      */     case 3: 
/*  963 */       this.selmode = 1;
/*  964 */       this.statetext.setText("Select a location in the final tableau where a change might occur and\npress the NEXT button.");
/*      */       
/*  966 */       this.statePanel.removeAll();
/*  967 */       this.statePanel.add(this.statetext);
/*  968 */       break;
/*      */     case 4: 
/*  970 */       if (this.selblock.size() > 0) {
/*  971 */         blkrect = (int[])this.selblock.elementAt(this.selblock.size() - 1);
/*  972 */         specBlock(this.finatable, blkrect);
/*      */       }
/*  974 */       if (this.rowStart == 0)
/*      */       {
/*  976 */         this.selmode = 2;
/*  977 */         this.isOnevalue = true;
/*  978 */         this.statetext.setText("To calculate a new value, we will need to multiply a row vector \ntimes a column vector plus a constant. Locate the row vector,\nby selecting its first element, and then press the NEXT button.");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*  984 */         this.selmode = 4;
/*  985 */         this.isOnevalue = false;
/*  986 */         this.statetext.setText("To calculate new values for this column, we will need to multiply\na matrix times a column vector. Locate the matrix by selecting \nits elements, and then press the NEXT button.");
/*      */       }
/*      */       
/*      */ 
/*  990 */       this.statePanel.removeAll();
/*  991 */       this.statePanel.add(this.statetext);
/*  992 */       break;
/*      */     case 5: 
/*  994 */       if (this.selblock.size() > 0) {
/*  995 */         blkrect = (int[])this.selblock.elementAt(this.selblock.size() - 1);
/*  996 */         specBlock(this.finatable, blkrect);
/*      */       }
/*  998 */       this.selmode = 3;
/*  999 */       if (this.isOnevalue == true)
/*      */       {
/* 1001 */         this.statetext.setText("To calculate a new value, we will need to multiply a row vector \ntimes a column vector plus a constant. Locate the column vector,\nby selecting its elements, and then press the NEXT button.");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1006 */         this.statetext.setText("To calculate new values for this column, we will need to multiply \na matrix times a column vector. Locate the column vector,\nby selecting its elements, and then press the NEXT button.");
/*      */       }
/*      */       
/*      */ 
/* 1010 */       this.statePanel.removeAll();
/* 1011 */       this.statePanel.add(this.statetext);
/* 1012 */       break;
/*      */     case 6: 
/* 1014 */       if (this.selblock.size() > 0) {
/* 1015 */         blkrect = (int[])this.selblock.elementAt(this.selblock.size() - 1);
/* 1016 */         specBlock(this.initable, blkrect);
/*      */       }
/* 1018 */       this.selmode = 5;
/* 1019 */       this.statetext.setText("To calculate a new value, we will need to multiply a row vector \ntimes a column vector plus a constant. Select the constant, \nand then press the NEXT button.");
/*      */       
/*      */ 
/* 1022 */       this.statePanel.removeAll();
/* 1023 */       this.statePanel.add(this.statetext);
/* 1024 */       break;
/*      */     case 7: 
/* 1026 */       this.selmode = 0;
/* 1027 */       this.finalModel.fireTableDataChanged();
/* 1028 */       clrTableColor(this.initable);
/* 1029 */       clrTableColor(this.finatable);
/*      */       
/* 1031 */       this.statetext.setText("Are there more locations in the final tableau that might be affected\nby the change(s) in the initial tableau? ");
/*      */       
/*      */ 
/* 1034 */       this.statePanel.removeAll();
/* 1035 */       this.statePanel.add(this.statetext);
/* 1036 */       this.statePanel.add(this.yorn);
/* 1037 */       break;
/*      */     case 8: 
/* 1039 */       this.statePanel.removeAll();
/* 1040 */       this.statePanel.add(this.commlabel);
/* 1041 */       this.statePanel.add(this.commandbox);
/* 1042 */       this.finalModel.fireTableDataChanged();
/* 1043 */       break;
/*      */     default: 
/* 1045 */       System.out.println("LP Sensitive has no this step.");
/*      */     }
/* 1047 */     this.mainPanel.add(this.upperPanel);
/* 1048 */     this.mainPanel.add(Box.createVerticalStrut(20));
/*      */     
/* 1050 */     this.mainPanel.add(this.lowerPanel);
/* 1051 */     this.mainPanel.add(Box.createVerticalStrut(10));
/* 1052 */     this.mainPanel.add(this.statePanel);
/* 1053 */     this.mainPanel.add(Box.createVerticalStrut(20));
/* 1054 */     this.mainPanel.add(this.buttPanel);
/* 1055 */     revalidate();
/* 1056 */     repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFile(ObjectOutputStream out)
/*      */   {
/* 1067 */     int[] interpara = new int[5];
/*      */     
/* 1069 */     interpara[0] = this.step;
/* 1070 */     interpara[1] = this.rowStart;
/* 1071 */     interpara[2] = this.rowEnd;
/* 1072 */     interpara[3] = this.colStart;
/* 1073 */     interpara[4] = this.colEnd;
/*      */     try {
/* 1075 */       out.writeObject(this.state);
/* 1076 */       out.writeObject(interpara);
/* 1077 */       out.writeObject(new Boolean(this.isOnevalue));
/* 1078 */       out.writeObject(this.opseq);
/* 1079 */       out.writeObject(this.chgcell);
/* 1080 */       out.writeObject(this.selblock);
/* 1081 */       out.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1084 */       e1.printStackTrace();
/* 1085 */       System.out.println("Save fails");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void specBlock(JTable table, int[] rec)
/*      */   {
/* 1097 */     int rb = rec[0];
/* 1098 */     int re = rec[1];
/* 1099 */     int cb = rec[2];
/* 1100 */     int ce = rec[3];
/*      */     
/* 1102 */     clrTableColor(table);
/* 1103 */     for (int i = rb; i <= re; i++) {
/* 1104 */       for (int j = cb; j <= ce; j++) {
/* 1105 */         Object anycel = table.getModel().getValueAt(i, j);
/* 1106 */         if ((anycel instanceof IORTableCell))
/* 1107 */           ((IORTableCell)anycel).isBlock = true;
/*      */       }
/*      */     }
/* 1110 */     table.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void clrTableColor(JTable table)
/*      */   {
/* 1119 */     for (int i = 0; i < table.getRowCount(); i++) {
/* 1120 */       for (int j = 0; j < table.getColumnCount(); j++) {
/* 1121 */         Object anycel = table.getModel().getValueAt(i, j);
/* 1122 */         if ((anycel instanceof IORTableCell)) {
/* 1123 */           ((IORTableCell)anycel).isBlock = false;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void addSelectionListener(JTable table)
/*      */   {
/* 1131 */     JTable tmptable = table;
/* 1132 */     int span = this.nBasic;
/*      */     
/*      */ 
/* 1135 */     ListSelectionModel rowSM = table.getSelectionModel();
/* 1136 */     rowSM.addListSelectionListener(new ListSelectionListener()
/*      */     {
/*      */       private final JTable val$tmptable;
/*      */       
/*      */       public void valueChanged(ListSelectionEvent e) {
/* 1141 */         ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/* 1142 */         if (!lsm.isSelectionEmpty())
/*      */         {
/*      */ 
/* 1145 */           int selrow = this.val$tmptable.getSelectedRow();
/* 1146 */           int selcol = this.val$tmptable.getSelectedColumn();
/* 1147 */           if ((selrow >= 0) && (selcol >= 0)) {
/* 1148 */             IORLPSensitivePanel.this.SelBlock(this.val$tmptable, selrow, selcol);
/* 1149 */             this.val$tmptable.repaint();
/*      */           }
/*      */           
/*      */         }
/*      */       }
/* 1154 */     });
/* 1155 */     ListSelectionModel colSM = table.getColumnModel().getSelectionModel();
/*      */     
/* 1157 */     colSM.addListSelectionListener(new ListSelectionListener() {
/*      */       private final JTable val$tmptable;
/*      */       
/*      */       public void valueChanged(ListSelectionEvent e) {
/* 1161 */         ListSelectionModel lsm = (ListSelectionModel)e.getSource();
/* 1162 */         if (!lsm.isSelectionEmpty())
/*      */         {
/*      */ 
/* 1165 */           int selrow = this.val$tmptable.getSelectedRow();
/* 1166 */           int selcol = this.val$tmptable.getSelectedColumn();
/* 1167 */           if ((selrow >= 0) && (selcol >= 0)) {
/* 1168 */             IORLPSensitivePanel.this.SelBlock(this.val$tmptable, selrow, selcol);
/* 1169 */             this.val$tmptable.repaint();
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */   private void SelBlock(JTable table, int selrow, int selcol)
/*      */   {
/* 1179 */     JTable tmptable = table;
/* 1180 */     int span = this.nBasic;
/*      */     
/*      */ 
/*      */ 
/* 1184 */     this.hasSelected = true;
/* 1185 */     clrTableColor(tmptable);
/*      */     int i;
/* 1187 */     switch (this.selmode) {
/*      */     case 0: 
/* 1189 */       this.rowStart = selrow;
/* 1190 */       this.rowEnd = selrow;
/* 1191 */       this.colStart = selcol;
/* 1192 */       this.colEnd = selcol;
/* 1193 */       if (selcol >= 3) {
/* 1194 */         Object anycel = tmptable.getModel().getValueAt(selrow, selcol);
/* 1195 */         if ((anycel instanceof IORTableCell)) {
/* 1196 */           ((IORTableCell)anycel).isBlock = true;
/*      */         }
/*      */       }
/*      */       break;
/*      */     case 1: 
/* 1201 */       if (tmptable == this.finatable)
/* 1202 */         if (selrow == 0) {
/* 1203 */           this.rowStart = selrow;
/* 1204 */           this.rowEnd = selrow;
/* 1205 */           this.colStart = selcol;
/* 1206 */           this.colEnd = selcol;
/* 1207 */           Object anycel = tmptable.getModel().getValueAt(selrow, selcol);
/* 1208 */           if ((anycel instanceof IORTableCell)) {
/* 1209 */             ((IORTableCell)anycel).isBlock = true;
/*      */           }
/*      */         } else {
/* 1212 */           this.rowStart = 1;
/* 1213 */           this.rowEnd = span;
/* 1214 */           this.colStart = selcol;
/* 1215 */           this.colEnd = selcol;
/* 1216 */           for (i = 1; i < 1 + span;) {
/* 1217 */             Object anycel = tmptable.getModel().getValueAt(i, selcol);
/* 1218 */             if ((anycel instanceof IORTableCell)) {
/* 1219 */               ((IORTableCell)anycel).isBlock = true;
/*      */             }
/* 1216 */             i++; continue;
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1225 */             if ((tmptable == this.finatable) && 
/* 1226 */               (selcol >= 3) && (selcol <= this.nAllvar - this.nBasic + 3)) {
/* 1227 */               this.rowStart = selrow;
/* 1228 */               this.rowEnd = selrow;
/* 1229 */               this.colStart = selcol;
/* 1230 */               this.colEnd = (selcol + span - 1);
/* 1231 */               for (j = selcol; j < selcol + span;) {
/* 1232 */                 Object anycel = tmptable.getModel().getValueAt(selrow, j);
/* 1233 */                 if ((anycel instanceof IORTableCell)) {
/* 1234 */                   ((IORTableCell)anycel).isBlock = true;
/*      */                 }
/* 1231 */                 j++; continue;
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1240 */                 if (tmptable == this.initable) {
/* 1241 */                   if (selrow == 0) {
/* 1242 */                     this.rowStart = 0;
/* 1243 */                     this.rowEnd = (span - 1);
/* 1244 */                     this.colStart = selcol;
/* 1245 */                     this.colEnd = selcol;
/* 1246 */                     for (int i = 0; i < span; i++) {
/* 1247 */                       Object anycel = tmptable.getModel().getValueAt(i, selcol);
/* 1248 */                       if ((anycel instanceof IORTableCell)) {
/* 1249 */                         ((IORTableCell)anycel).isBlock = true;
/*      */                       }
/*      */                     }
/*      */                   }
/* 1253 */                   this.rowStart = 1;
/* 1254 */                   this.rowEnd = span;
/* 1255 */                   this.colStart = selcol;
/* 1256 */                   this.colEnd = selcol;
/* 1257 */                   int i = 1; for (;;) { if (i < 1 + span) {
/* 1258 */                       Object anycel = tmptable.getModel().getValueAt(i, selcol);
/* 1259 */                       if ((anycel instanceof IORTableCell)) {
/* 1260 */                         ((IORTableCell)anycel).isBlock = true;
/*      */                       }
/* 1257 */                       i++; continue;
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1266 */                       if (tmptable == this.finatable)
/* 1267 */                         if ((selrow == 0) && (selcol <= this.nAllvar - span + 3)) {
/* 1268 */                           this.rowStart = 0;
/* 1269 */                           this.rowEnd = (span - 1);
/* 1270 */                           this.colStart = selcol;
/* 1271 */                           this.colEnd = (selcol + span - 1);
/* 1272 */                           for (i = 0; i < span;) {
/* 1273 */                             for (int j = selcol; j < selcol + span; j++) {
/* 1274 */                               Object anycel = tmptable.getModel().getValueAt(i, j);
/* 1275 */                               if ((anycel instanceof IORTableCell)) {
/* 1276 */                                 ((IORTableCell)anycel).isBlock = true;
/*      */                               }
/*      */                             }
/* 1272 */                             i++; continue;
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1280 */                             if ((selrow > 0) && (selcol <= this.nAllvar - span + 3)) {
/* 1281 */                               this.rowStart = 1;
/* 1282 */                               this.rowEnd = span;
/* 1283 */                               this.colStart = selcol;
/* 1284 */                               this.colEnd = (selcol + span - 1);
/* 1285 */                               for (i = 1; i < 1 + span;) {
/* 1286 */                                 for (int j = selcol; j < selcol + span; j++) {
/* 1287 */                                   Object anycel = tmptable.getModel().getValueAt(i, j);
/* 1288 */                                   if ((anycel instanceof IORTableCell)) {
/* 1289 */                                     ((IORTableCell)anycel).isBlock = true;
/*      */                                   }
/*      */                                 }
/* 1285 */                                 i++; continue;
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1296 */                                 if (tmptable == this.initable) {
/* 1297 */                                   this.rowStart = selrow;
/* 1298 */                                   this.rowEnd = selrow;
/* 1299 */                                   this.colStart = selcol;
/* 1300 */                                   this.colEnd = selcol;
/* 1301 */                                   Object anycel = tmptable.getModel().getValueAt(selrow, selcol);
/* 1302 */                                   if ((anycel instanceof IORTableCell)) {
/* 1303 */                                     ((IORTableCell)anycel).isBlock = true; break;
/*      */                                   } } } } } } } } } } } } }
/*      */       break; }
/*      */     int j;
/*      */     int i;
/*      */     int i;
/* 1309 */     tmptable.repaint();
/*      */   }
/*      */   
/*      */   class IORTableModel extends AbstractTableModel { private int numVars;
/*      */     private int i;
/*      */     private int j;
/*      */     private String weirdstr;
/* 1316 */     private IORTableCell[][] data = new IORTableCell[IORLPSensitivePanel.this.controller.data.num_constrain + 1][IORLPSensitivePanel.this.controller.data.num_variable + 1];
/*      */     
/*      */     public IORTableModel() {
/* 1319 */       this.numVars = IORLPSensitivePanel.this.controller.data.num_variable;
/* 1320 */       for (this.i = 0; this.i < IORLPSensitivePanel.this.controller.data.num_constrain + 1; this.i += 1) {
/* 1321 */         for (this.j = 0; this.j < IORLPSensitivePanel.this.controller.data.num_variable + 1; this.j += 1) {
/* 1322 */           this.data[this.i][this.j] = new IORTableCell(0.0D, 0.0D);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public int getColumnCount() {
/* 1328 */       return IORLPSensitivePanel.this.controller.data.num_variable + 4;
/*      */     }
/*      */     
/*      */     public int getRowCount() {
/* 1332 */       return IORLPSensitivePanel.this.controller.data.num_constrain + 1;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col) {
/* 1336 */       if (col == 0)
/* 1337 */         return "Bas Var";
/* 1338 */       if (col == 1)
/* 1339 */         return "Eq.";
/* 1340 */       if (col == 2)
/* 1341 */         return "Z";
/* 1342 */       if ((col >= 3) && (col < 3 + this.numVars)) {
/* 1343 */         if (IORLPSensitivePanel.this.controller.data.is_artificial[(col - 2)] != 0) {
/* 1344 */           return "X".concat(String.valueOf(String.valueOf(col - 2)));
/*      */         }
/*      */         
/* 1347 */         return "x".concat(String.valueOf(String.valueOf(col - 2)));
/*      */       }
/*      */       
/* 1350 */       return "Right Side";
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col)
/*      */     {
/* 1355 */       int xsub = 0;
/*      */       
/*      */ 
/* 1358 */       if (col == 1)
/* 1359 */         return new Integer(row);
/* 1360 */       if (col == 2) {
/* 1361 */         if ((row == 0) && (IORLPSensitivePanel.this.controller.data.objective_is_max))
/* 1362 */           return new Integer(1);
/* 1363 */         if (row == 0) {
/* 1364 */           return new Integer(-1);
/*      */         }
/* 1366 */         return new Integer(0);
/*      */       }
/* 1368 */       if (col == 0) {
/* 1369 */         if (row == 0) {
/* 1370 */           return "Z";
/*      */         }
/* 1372 */         xsub = IORLPSensitivePanel.this.controller.data.basic_variable_index[row];
/* 1373 */         if (IORLPSensitivePanel.this.controller.data.is_artificial[xsub] != 0) {
/* 1374 */           return "X".concat(String.valueOf(String.valueOf(xsub)));
/*      */         }
/* 1376 */         return "x".concat(String.valueOf(String.valueOf(xsub)));
/*      */       }
/*      */       
/* 1379 */       if ((col >= 3) && (col < this.numVars + 3)) {
/* 1380 */         if (row == 0) {
/* 1381 */           double mc = IORLPSensitivePanel.this.controller.data.objective_M_coefficient[(col - 2)];
/* 1382 */           double ec = IORLPSensitivePanel.this.controller.data.objective_coefficient[(col - 2)];
/* 1383 */           this.data[row][(col - 3)].setPara(mc, ec);
/* 1384 */           return this.data[row][(col - 3)];
/*      */         }
/*      */         
/* 1387 */         this.data[row][(col - 3)].setPara(0.0D, IORLPSensitivePanel.this.controller.data.constrain_coefficient[row][(col - 2)]);
/* 1388 */         return this.data[row][(col - 3)];
/*      */       }
/*      */       
/*      */ 
/* 1392 */       if (row == 0) {
/* 1393 */         double mc = IORLPSensitivePanel.this.controller.data.objective_M_coefficient[0];
/* 1394 */         double ec = IORLPSensitivePanel.this.controller.data.objective_coefficient[0];
/* 1395 */         this.data[row][(col - 3)].setPara(mc, ec);
/* 1396 */         return this.data[row][(col - 3)];
/*      */       }
/*      */       
/* 1399 */       this.data[row][(col - 3)].setPara(0.0D, IORLPSensitivePanel.this.controller.data.constrain_coefficient[row][0]);
/* 1400 */       return this.data[row][(col - 3)];
/*      */     }
/*      */     
/*      */ 
/*      */     public Class getColumnClass(int c)
/*      */     {
/* 1406 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1412 */     public boolean isCellEditable(int row, int col) { return false; }
/*      */   }
/*      */   
/*      */   class IORFirstTableModel extends AbstractTableModel {
/*      */     private int i;
/*      */     private int j;
/*      */     private int numVars;
/*      */     private String weirdstr;
/* 1420 */     private IORTableCell[][] data = new IORTableCell[IORLPSensitivePanel.this.controller.data.num_constrain + 1][IORLPSensitivePanel.this.controller.data.num_variable + 1];
/*      */     
/*      */     public IORFirstTableModel() {
/* 1423 */       this.numVars = IORLPSensitivePanel.this.controller.data.num_variable;
/*      */       
/* 1425 */       for (this.i = 0; this.i < IORLPSensitivePanel.this.controller.data.num_constrain + 1; this.i += 1) {
/* 1426 */         for (this.j = 0; this.j < IORLPSensitivePanel.this.controller.data.num_variable + 1; this.j += 1) {
/* 1427 */           this.data[this.i][this.j] = new IORTableCell(0.0D, 0.0D);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public int getColumnCount()
/*      */     {
/* 1434 */       return IORLPSensitivePanel.this.controller.data.num_variable + 4;
/*      */     }
/*      */     
/*      */     public int getRowCount() {
/* 1438 */       return IORLPSensitivePanel.this.controller.data.num_constrain + 1;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col) {
/* 1442 */       if (col == 0)
/* 1443 */         return "Bas Var";
/* 1444 */       if (col == 1)
/* 1445 */         return "Eq.";
/* 1446 */       if (col == 2)
/* 1447 */         return "Z";
/* 1448 */       if ((col >= 3) && (col < 3 + this.numVars)) {
/* 1449 */         if (IORLPSensitivePanel.this.controller.data.is_artificial[(col - 2)] != 0) {
/* 1450 */           return "X".concat(String.valueOf(String.valueOf(col - 2)));
/*      */         }
/*      */         
/* 1453 */         return "x".concat(String.valueOf(String.valueOf(col - 2)));
/*      */       }
/*      */       
/* 1456 */       return "Right Side";
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col)
/*      */     {
/* 1461 */       int xsub = 0;
/*      */       
/*      */ 
/* 1464 */       if (col == 1)
/* 1465 */         return new Integer(row);
/* 1466 */       if (col == 2) {
/* 1467 */         if ((row == 0) && (IORLPSensitivePanel.this.controller.data.objective_is_max))
/* 1468 */           return new Integer(1);
/* 1469 */         if (row == 0) {
/* 1470 */           return new Integer(-1);
/*      */         }
/* 1472 */         return new Integer(0);
/*      */       }
/* 1474 */       if (col == 0) {
/* 1475 */         if (row == 0) {
/* 1476 */           return "Z";
/*      */         }
/* 1478 */         xsub = IORLPSensitivePanel.this.controller.data.first_basic_variable_index[row];
/* 1479 */         if (IORLPSensitivePanel.this.controller.data.is_artificial[xsub] != 0) {
/* 1480 */           return "X".concat(String.valueOf(String.valueOf(xsub)));
/*      */         }
/* 1482 */         return "x".concat(String.valueOf(String.valueOf(xsub)));
/*      */       }
/*      */       
/* 1485 */       if ((col >= 3) && (col < this.numVars + 3)) {
/* 1486 */         if (row == 0) {
/* 1487 */           double mc = IORLPSensitivePanel.this.controller.data.first_objective_M_coefficient[(col - 2)];
/* 1488 */           double ec = IORLPSensitivePanel.this.controller.data.first_objective_coefficient[(col - 2)];
/* 1489 */           this.data[row][(col - 3)].setPara(mc, ec);
/* 1490 */           return this.data[row][(col - 3)];
/*      */         }
/*      */         
/* 1493 */         this.data[row][(col - 3)].setPara(0.0D, IORLPSensitivePanel.this.controller.data.first_constrain_coefficient[row][(col - 2)]);
/* 1494 */         return this.data[row][(col - 3)];
/*      */       }
/*      */       
/*      */ 
/* 1498 */       if (row == 0) {
/* 1499 */         double mc = IORLPSensitivePanel.this.controller.data.first_objective_M_coefficient[0];
/* 1500 */         double ec = IORLPSensitivePanel.this.controller.data.first_objective_coefficient[0];
/* 1501 */         this.data[row][(col - 3)].setPara(mc, ec);
/* 1502 */         return this.data[row][(col - 3)];
/*      */       }
/*      */       
/* 1505 */       this.data[row][(col - 3)].setPara(0.0D, IORLPSensitivePanel.this.controller.data.first_constrain_coefficient[row][0]);
/* 1506 */       return this.data[row][(col - 3)];
/*      */     }
/*      */     
/*      */ 
/*      */     public Class getColumnClass(int c)
/*      */     {
/* 1512 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1518 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/* 1527 */     String str = " Performing sensitivity analysis on the linear programming model\n\n";
/* 1528 */     str = String.valueOf(String.valueOf(str)).concat("This procedure allows you to determine the effect that one or more changes in ");
/* 1529 */     str = String.valueOf(String.valueOf(str)).concat("the original model may have on the final tableau by implementing the general ");
/* 1530 */     str = String.valueOf(String.valueOf(str)).concat("procedure for sensitivity analysis presented in Sec. 6.6. You first make the ");
/* 1531 */     str = String.valueOf(String.valueOf(str)).concat("changes in the initial tableau. Then you determine which parts of the final ");
/* 1532 */     str = String.valueOf(String.valueOf(str)).concat("tableau will be affected, and determine their new values. Finally, you convert ");
/* 1533 */     str = String.valueOf(String.valueOf(str)).concat("the final tableau to its proper form from Gaussian elimination (if necessary), and to see whether the solution is ");
/* 1534 */     str = String.valueOf(String.valueOf(str)).concat("optimal. If not, you can apply the Simplex Method or dual Simplex Method to the ");
/* 1535 */     str = String.valueOf(String.valueOf(str)).concat("final tableau to obtain the new optimal solution, if you wish. To correct ");
/* 1536 */     str = String.valueOf(String.valueOf(str)).concat("a previous mistake at any time, press the BACK button to backtrack.\n\n");
/* 1537 */     str = String.valueOf(String.valueOf(str)).concat("Locating where there is a change in the initial tableau\n\n");
/* 1538 */     str = String.valueOf(String.valueOf(str)).concat("Select a number in the initial tableau that changes because of the change ");
/* 1539 */     str = String.valueOf(String.valueOf(str)).concat("or changes made in the original model. If you decide instead that all needed ");
/* 1540 */     str = String.valueOf(String.valueOf(str)).concat("changes already have been made, then choose any entry in the tableau and enter ");
/* 1541 */     str = String.valueOf(String.valueOf(str)).concat("the same value that was there. \n\n");
/* 1542 */     str = String.valueOf(String.valueOf(str)).concat("When you are finished with the current procedure, click the FINISH button.");
/* 1543 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/* 1545 */     this.help_content_step.setText(str);
/* 1546 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/* 1553 */     String str = "Performing sensitivity analysis on the linear programming model\n\n";
/* 1554 */     str = String.valueOf(String.valueOf(str)).concat("This procedure interactively executes the procedure for \"Sensitivity Analysis\" ");
/* 1555 */     str = String.valueOf(String.valueOf(str)).concat("summarized in Sec. 6.6. The linear programming problem considered is the ");
/* 1556 */     str = String.valueOf(String.valueOf(str)).concat("last one solved by the Simplex Method with the interactive procedure. All the ");
/* 1557 */     str = String.valueOf(String.valueOf(str)).concat("sensitivity analysis cases presented in Sec. 6.7 (and combinations thereof) ");
/* 1558 */     str = String.valueOf(String.valueOf(str)).concat("can be performed. However, in order to perform operations on the final tableau ");
/* 1559 */     str = String.valueOf(String.valueOf(str)).concat("when a new variable (Case 2b) or a new constraint (Case 4) has just been introduced, ");
/* 1560 */     str = String.valueOf(String.valueOf(str)).concat("an extra variable or constraint with zero coefficients needs to be included in the ");
/* 1561 */     str = String.valueOf(String.valueOf(str)).concat("original model to create the needed row or column in the final tableau.\n");
/* 1562 */     str = String.valueOf(String.valueOf(str)).concat("When revising the final tableau, you specify how to perform the calculations ");
/* 1563 */     str = String.valueOf(String.valueOf(str)).concat("for applying the functional insight of Sec. 5.3. All actual calculations ");
/* 1564 */     str = String.valueOf(String.valueOf(str)).concat("(including any conversions to the proper form from Gaussian elimination and any ");
/* 1565 */     str = String.valueOf(String.valueOf(str)).concat("reoptimization) are performed by the computer. \n");
/* 1566 */     str = String.valueOf(String.valueOf(str)).concat("\nYou can restart the procedure to independently investigate different changes ");
/* 1567 */     str = String.valueOf(String.valueOf(str)).concat("in the model as often as desired. You also can print out the results each ");
/* 1568 */     str = String.valueOf(String.valueOf(str)).concat("time by choosing Print to file under the File menu.");
/* 1569 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/* 1571 */     this.help_content_procedure.setText(str);
/* 1572 */     this.help_content_procedure.revalidate();
/*      */   }
/*      */   
/* 1575 */   private String str0 = " Performing sensitivity analysis on the linear programming model\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1585 */   private String str1 = "Locating where there is a change in the initial tableau\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1594 */   private String str2 = "Are there any more changes in the initial tableau?\n\n";
/*      */   
/*      */ 
/* 1597 */   private String str3 = "Where is there a possible change in the final tableau?\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1603 */   private String str4 = "Choosing the matrix or row vector to determine the new column values\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1608 */   private String str5 = "Choosing the column vector to determine the new column values\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1613 */   private String str7 = "Are there any more possible changes in the final tableau?\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1619 */   private String str6 = "Choosing the constant to determine the new row 0 value\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1624 */   private String str8 = "Choosing the next command\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void updateStepHelpPanel()
/*      */   {
/* 1637 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/* 1638 */     switch (this.step) {
/*      */     case 1: 
/* 1640 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str1).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1667 */       break;
/*      */     case 2: 
/* 1643 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str2).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1667 */       break;
/*      */     case 3: 
/* 1646 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str3).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1667 */       break;
/*      */     case 4: 
/* 1649 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str4).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1667 */       break;
/*      */     case 5: 
/* 1652 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str5).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1667 */       break;
/*      */     case 6: 
/* 1655 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str6).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1667 */       break;
/*      */     case 7: 
/* 1658 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str7).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1667 */       break;
/*      */     case 8: 
/* 1661 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str8).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1667 */       break;
/*      */     default: 
/* 1664 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPSensitivePanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */