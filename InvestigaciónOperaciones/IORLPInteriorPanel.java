/*     */ import java.awt.BorderLayout;
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
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORLPInteriorPanel
/*     */   extends IORLPActionPanel
/*     */ {
/*  32 */   private JPanel mainPanel = new JPanel();
/*  33 */   private IORLPInteriorPanel.IORINPTableModel interModel = null;
/*  34 */   private IORLPInteriorPanel.IORIteTableModel iteModel = null;
/*  35 */   private JTable table = null;
/*  36 */   private JTable itable = null;
/*  37 */   private JLabel[] indilabel = null;
/*  38 */   private JPanel tablePanel = new JPanel();
/*  39 */   private JScrollPane scrlPane = null;
/*  40 */   private JPanel indiPanel = new JPanel();
/*  41 */   private JPanel inputPanel = new JPanel();
/*  42 */   private JPanel transtPanel = new JPanel();
/*  43 */   private JTextArea statetext = new JTextArea("Press the NEXT button to perform an iteration of the interior-point algorithm.");
/*  44 */   private JButton okbutt = new JButton("OK");
/*  45 */   private JButton nextbutt = new JButton("Next");
/*  46 */   private DecimalField[] inifeasible = null;
/*     */   
/*  48 */   private int step = 1;
/*  49 */   private int itercount = 0;
/*     */   
/*     */ 
/*     */   private int nBasic;
/*     */   
/*     */   private int nAllvar;
/*     */   
/*     */ 
/*     */   public IORLPInteriorPanel(IORLPProcessController c)
/*     */   {
/*  59 */     super(c);
/*  60 */     add(this.mainPanel, "Center");
/*  61 */     this.bn_back.setVisible(false);
/*  62 */     this.bn_finish.setVisible(false);
/*  63 */     this.actionStatus.setVisible(false);
/*     */     
/*  65 */     this.nBasic = this.controller.data.num_constrain;
/*  66 */     this.nAllvar = this.controller.data.num_variable;
/*     */     
/*  68 */     this.interModel = new IORLPInteriorPanel.IORINPTableModel();
/*  69 */     this.table = new JTable(this.interModel);
/*  70 */     this.table.setShowGrid(false);
/*  71 */     this.table.setSelectionBackground(getBackground());
/*  72 */     this.tablePanel.setAlignmentX(0.0F);
/*  73 */     this.tablePanel.setPreferredSize(new Dimension((this.nAllvar + 1) * 90, this.nBasic * 30));
/*  74 */     this.tablePanel.setMaximumSize(new Dimension((this.nAllvar + 1) * 90, this.nBasic * 30));
/*  75 */     this.tablePanel.add(this.table);
/*  76 */     this.table.setBackground(getBackground());
/*     */     
/*  78 */     this.iteModel = new IORLPInteriorPanel.IORIteTableModel();
/*  79 */     this.itable = new JTable(this.iteModel);
/*  80 */     this.itable.setShowHorizontalLines(false);
/*  81 */     this.scrlPane = new JScrollPane(this.itable);
/*  82 */     this.scrlPane.setPreferredSize(new Dimension(500, 300));
/*  83 */     this.scrlPane.setMaximumSize(new Dimension(500, 300));
/*     */     
/*  85 */     this.indilabel = new JLabel[8];
/*  86 */     this.indilabel[0] = new JLabel("");
/*  87 */     this.indilabel[1] = new JLabel("Subject to");
/*  88 */     this.indilabel[2] = new JLabel("And xj >= 0 for all j");
/*  89 */     this.indilabel[3] = new JLabel("");
/*  90 */     this.indilabel[4] = new JLabel("Shown above is the original model with slack and surplus variables added.");
/*  91 */     this.indilabel[5] = new JLabel("Enter an initial feasible trial solution (with all xj > 0):");
/*  92 */     this.indilabel[6] = new JLabel("[X] = [");
/*  93 */     this.indilabel[7] = new JLabel("]");
/*     */     
/*  95 */     this.indilabel[1].setLabelFor(this.tablePanel);
/*     */     
/*  97 */     DecimalFormat decfm = new DecimalFormat();
/*     */     
/*  99 */     String zs = new String();
/* 100 */     String nms = new String();
/* 101 */     String bds = new String();
/* 102 */     for (int i = 0; i < this.nAllvar; i++) {
/* 103 */       nms = decfm.format(this.controller.data.objective_coefficient[(i + 1)]);
/* 104 */       if ((i > 0) && (this.controller.data.objective_coefficient[(i + 1)] > -0.001D)) {
/* 105 */         nms = "+".concat(String.valueOf(String.valueOf(nms)));
/*     */       }
/* 107 */       nms = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(nms))).append(" x").append(i + 1)));
/* 108 */       zs = String.valueOf(String.valueOf(zs)).concat(String.valueOf(String.valueOf(nms)));
/* 109 */       if (this.controller.data.bound_operator[(i + 1)] == 0) {
/* 110 */         bds = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(bds))).append("Orig. x").append(i + 1).append(" = x").append(i + 1).append("- x").append(i + 2).append(", ")));
/*     */       }
/* 112 */       else if (this.controller.data.bound[(i + 1)] > 0.001D) {
/* 113 */         bds = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(bds))).append("Orig. x").append(i + 1).append(" = x").append(i + 2).append("+").append(decfm.format(this.controller.data.bound[(i + 1)]))));
/*     */       }
/* 115 */       else if (this.controller.data.bound[(i + 1)] < -0.001D) {
/* 116 */         bds = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(bds))).append("Orig. x").append(i + 1).append(" = x").append(i + 2).append(decfm.format(this.controller.data.bound[(i + 1)]))));
/*     */       }
/*     */     }
/* 119 */     zs = "Max Z= ".concat(String.valueOf(String.valueOf(zs)));
/* 120 */     this.indilabel[0].setText(zs);
/* 121 */     this.indilabel[3].setText(bds);
/*     */     
/* 123 */     this.inifeasible = new DecimalField[this.nAllvar];
/* 124 */     for (i = 0; i < this.nAllvar; i++) {
/* 125 */       this.inifeasible[i] = new DecimalField(1.0D, decfm);
/*     */     }
/*     */     
/* 128 */     this.transtPanel.setLayout(new BorderLayout());
/*     */     
/* 130 */     this.inputPanel.setAlignmentX(0.0F);
/* 131 */     this.inputPanel.setPreferredSize(new Dimension(this.nAllvar * 40 + 300, 40));
/* 132 */     this.inputPanel.setMaximumSize(new Dimension(this.nAllvar * 40 + 300, 40));
/*     */     
/*     */ 
/* 135 */     this.inputPanel.add(this.indilabel[6], 0);
/* 136 */     for (i = 0; i < this.nAllvar; i++) {
/* 137 */       this.inputPanel.add(this.inifeasible[i]);
/*     */     }
/*     */     
/* 140 */     this.inputPanel.add(this.indilabel[7]);
/* 141 */     this.inputPanel.add(Box.createRigidArea(new Dimension(30, 20)));
/* 142 */     this.inputPanel.add(this.okbutt);
/*     */     
/* 144 */     this.statetext.setBackground(getBackground());
/* 145 */     this.statetext.setEditable(false);
/* 146 */     this.statetext.setForeground(Color.blue);
/* 147 */     this.statetext.setAlignmentX(0.5F);
/* 148 */     this.nextbutt.setAlignmentX(0.5F);
/* 149 */     this.indiPanel.setLayout(new BoxLayout(this.indiPanel, 1));
/* 150 */     this.indiPanel.setPreferredSize(new Dimension(500, 60));
/* 151 */     this.indiPanel.setMaximumSize(new Dimension(500, 60));
/* 152 */     this.indiPanel.add(this.statetext);
/* 153 */     this.indiPanel.add(Box.createVerticalStrut(10));
/* 154 */     this.indiPanel.add(this.nextbutt);
/*     */     
/*     */ 
/* 157 */     this.okbutt.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 160 */         IORLPInteriorPanel.this.doStart();
/*     */       }
/*     */       
/*     */ 
/* 164 */     });
/* 165 */     this.nextbutt.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 168 */         IORLPInteriorPanel.this.doIterate();
/*     */       }
/* 170 */     });
/* 171 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/* 172 */     this.mainPanel.setBorder(new EmptyBorder(20, 10, 10, 10));
/* 173 */     this.mainPanel.add(this.indilabel[0]);
/* 174 */     this.mainPanel.add(Box.createRigidArea(new Dimension(100, 10)));
/* 175 */     this.mainPanel.add(this.indilabel[1]);
/* 176 */     this.mainPanel.add(this.tablePanel);
/* 177 */     for (i = 2; i <= 5; i++) {
/* 178 */       this.mainPanel.add(this.indilabel[i]);
/* 179 */       this.mainPanel.add(Box.createRigidArea(new Dimension(100, 10)));
/*     */     }
/* 181 */     this.mainPanel.add(this.inputPanel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void finishProcedure()
/*     */   {
/* 189 */     setFinishEnabled(false);
/* 190 */     this.actionStatus.setText("The current procedure is finished. Please go to the Procedure menu to continue!");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void backStep() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void updatePanel() {}
/*     */   
/*     */ 
/*     */ 
/*     */   private void doStart()
/*     */   {
/* 205 */     double[] ipoint = new double[this.controller.data.num_variable];
/*     */     
/* 207 */     Vector par = new Vector();
/* 208 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/*     */ 
/*     */ 
/* 212 */     for (int i = 0; i < this.nAllvar; i++) {
/* 213 */       ipoint[i] = this.inifeasible[i].getValue();
/*     */     }
/* 215 */     par.addElement(ipoint);
/* 216 */     opr.operation_code = 10301;
/* 217 */     opr.parameters = par;
/* 218 */     if (this.controller.solver.doIPWork(opr, this.controller.data) == false) {
/* 219 */       String err = this.controller.solver.getErrInfo();
/* 220 */       JOptionPane.showMessageDialog(this, err);
/*     */     }
/*     */     else
/*     */     {
/* 224 */       this.mainPanel.removeAll();
/* 225 */       this.mainPanel.add(this.scrlPane);
/* 226 */       this.mainPanel.add(Box.createVerticalStrut(20));
/* 227 */       this.mainPanel.add(this.indiPanel);
/* 228 */       this.step = 2;
/*     */       
/* 230 */       revalidate();
/* 231 */       repaint();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void doIterate()
/*     */   {
/* 238 */     Vector par = new Vector();
/* 239 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/* 241 */     this.itercount += 1;
/* 242 */     if (this.itercount <= 15) {
/* 243 */       opr.operation_code = 10302;
/* 244 */       opr.parameters = par;
/* 245 */       this.controller.solver.doIPWork(opr, this.controller.data);
/*     */       
/* 247 */       if ((this.itercount > 3) && (Math.abs(this.controller.data.constrain_coefficient[this.itercount][0] - this.controller.data.constrain_coefficient[(this.itercount - 1)][0]) > Math.abs(this.controller.data.constrain_coefficient[(this.itercount - 1)][0] - this.controller.data.constrain_coefficient[(this.itercount - 2)][0]) + 10))
/*     */       {
/*     */ 
/* 250 */         this.statetext.setText("You've got the result, continue doing will cause degeneracy!");
/* 251 */         this.nextbutt.setEnabled(false);
/*     */       }
/*     */       else {
/* 254 */         this.iteModel.fireTableRowsInserted(this.itercount, this.itercount);
/*     */       }
/* 256 */       if (this.itercount == 15) {
/* 257 */         this.statetext.setText("You have now performed the maximum 15 iterations of the interior-point algorithm.");
/* 258 */         this.nextbutt.setEnabled(false);
/*     */       }
/* 260 */       revalidate();
/* 261 */       repaint();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/* 274 */     int[] para = new int[2];
/*     */     try {
/* 276 */       para = (int[])in.readObject();
/* 277 */       in.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 280 */       e1.printStackTrace();
/* 281 */       System.out.println("Open fails");
/*     */     }
/*     */     
/* 284 */     this.step = para[0];
/* 285 */     this.itercount = para[1];
/*     */     
/* 287 */     this.mainPanel.removeAll();
/* 288 */     switch (this.step) {
/*     */     case 1: 
/* 290 */       this.mainPanel.add(this.indilabel[0]);
/* 291 */       this.mainPanel.add(Box.createRigidArea(new Dimension(100, 10)));
/* 292 */       this.mainPanel.add(this.indilabel[1]);
/* 293 */       this.mainPanel.add(this.tablePanel);
/* 294 */       for (int i = 2; i <= 5; i++) {
/* 295 */         this.mainPanel.add(this.indilabel[i]);
/* 296 */         this.mainPanel.add(Box.createRigidArea(new Dimension(100, 10)));
/*     */       }
/* 298 */       this.mainPanel.add(this.inputPanel);
/* 299 */       revalidate();
/* 300 */       repaint();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 325 */       break;
/*     */     case 2: 
/* 303 */       if (this.itercount == 15) {
/* 304 */         this.statetext.setText("You have now performed the maximum 15 iterations of the interior-point algorithm.");
/* 305 */         this.nextbutt.setEnabled(false);
/*     */       }
/*     */       else {
/* 308 */         this.statetext.setText("Press the NEXT button to perform an iteration of the interior-point algorithm.");
/*     */       }
/* 310 */       this.indiPanel.removeAll();
/* 311 */       this.indiPanel.add(this.statetext);
/* 312 */       this.indiPanel.add(Box.createVerticalStrut(10));
/* 313 */       this.indiPanel.add(this.nextbutt);
/* 314 */       this.mainPanel.add(this.scrlPane);
/* 315 */       this.mainPanel.add(Box.createVerticalStrut(20));
/* 316 */       this.mainPanel.add(this.indiPanel);
/* 317 */       revalidate();
/* 318 */       repaint();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 325 */       break;
/*     */     default: 
/* 322 */       System.out.println("LP Interior has no this step.");
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/* 334 */     int[] interpara = new int[2];
/*     */     
/* 336 */     interpara[0] = this.step;
/* 337 */     interpara[1] = this.itercount;
/*     */     try {
/* 339 */       out.writeObject(interpara);
/* 340 */       out.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 343 */       e1.printStackTrace();
/* 344 */       System.out.println("Save fails");
/*     */     }
/*     */   }
/*     */   
/*     */   class IORINPTableModel extends AbstractTableModel {
/*     */     private int i;
/*     */     private int j;
/*     */     
/*     */     IORINPTableModel() {}
/*     */     
/* 354 */     public int getColumnCount() { return IORLPInteriorPanel.this.controller.data.num_variable + 1; }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 358 */       return IORLPInteriorPanel.this.controller.data.num_constrain;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col) {
/* 362 */       return "Col".concat(String.valueOf(String.valueOf(col)));
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col)
/*     */     {
/* 367 */       String str = new String();
/* 368 */       DecimalFormat decfm = new DecimalFormat();
/*     */       
/* 370 */       if (col < IORLPInteriorPanel.this.controller.data.num_variable) {
/* 371 */         if (Math.abs(IORLPInteriorPanel.this.controller.data.constrain_coefficient[(row + 1)][(col + 1)]) > 0.001D) {
/* 372 */           str = decfm.format(IORLPInteriorPanel.this.controller.data.constrain_coefficient[(row + 1)][(col + 1)]);
/* 373 */           if ((col > 0) && (IORLPInteriorPanel.this.controller.data.constrain_coefficient[(row + 1)][(col + 1)] > 0.001D)) {
/* 374 */             str = "+".concat(String.valueOf(String.valueOf(str)));
/*     */           }
/* 376 */           str = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(str))).append(" x").append(col + 1)));
/*     */         }
/*     */         else {
/* 379 */           str = "";
/*     */         }
/*     */       }
/*     */       else {
/* 383 */         str = " = ".concat(String.valueOf(String.valueOf(IORLPInteriorPanel.this.controller.data.constrain_coefficient[(row + 1)][0])));
/*     */       }
/* 385 */       return str;
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c) {
/* 389 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean isCellEditable(int row, int col)
/*     */     {
/* 395 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   class IORIteTableModel extends AbstractTableModel {
/*     */     private int i;
/*     */     private int j;
/*     */     
/*     */     IORIteTableModel() {}
/*     */     
/* 405 */     public int getColumnCount() { return IORLPInteriorPanel.this.controller.data.num_original_variable + 2; }
/*     */     
/*     */     public int getRowCount()
/*     */     {
/* 409 */       return IORLPInteriorPanel.this.itercount + 1;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col) {
/* 413 */       if (col == 0)
/* 414 */         return "Iter.";
/* 415 */       if (col == IORLPInteriorPanel.this.controller.data.num_original_variable + 1) {
/* 416 */         return "Z";
/*     */       }
/* 418 */       return "X".concat(String.valueOf(String.valueOf(col)));
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col) {
/* 422 */       String str = new String();
/* 423 */       DecimalFormat decfm = new DecimalFormat();
/*     */       
/* 425 */       if (col == 0)
/* 426 */         return new Integer(row).toString();
/* 427 */       if (col == IORLPInteriorPanel.this.controller.data.num_original_variable + 1) {
/* 428 */         return decfm.format(IORLPInteriorPanel.this.controller.data.constrain_coefficient[row][0]);
/*     */       }
/* 430 */       return decfm.format(IORLPInteriorPanel.this.controller.data.constrain_coefficient[row][col]);
/*     */     }
/*     */     
/*     */     public Class getColumnClass(int c) {
/* 434 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean isCellEditable(int row, int col)
/*     */     {
/* 440 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 449 */     String str = "Solve Automatically by the Interior-Point Algorithm\n\n";
/* 450 */     str = String.valueOf(String.valueOf(str)).concat("This procedure is used to apply the interior-point algorithm described in ");
/* 451 */     str = String.valueOf(String.valueOf(str)).concat("Sec.7.4 to the linear programming model that you have entered. You enter the ");
/* 452 */     str = String.valueOf(String.valueOf(str)).concat("initial trial solution, and then the computer will automatically perform up to ");
/* 453 */     str = String.valueOf(String.valueOf(str)).concat("15 iterations. Note that the Option menu provides you with a choice of two ");
/* 454 */     str = String.valueOf(String.valueOf(str)).concat("values (0.5 or 0.9) for alpha. You may print your results by choosing Print to file ");
/* 455 */     str = String.valueOf(String.valueOf(str)).concat("under the File menu.\n\n");
/* 456 */     str = String.valueOf(String.valueOf(str)).concat("Entering the initial trial solution\n\n");
/* 457 */     str = String.valueOf(String.valueOf(str)).concat("In order to start the interior-point algorithm, an initial trial solution is ");
/* 458 */     str = String.valueOf(String.valueOf(str)).concat("needed. This solution needs to be both feasible and in the INTERIOR of the feasible ");
/* 459 */     str = String.valueOf(String.valueOf(str)).concat("region, i.e., EVERY variable (including slack, surplus, and artificial variables) must be strictly ");
/* 460 */     str = String.valueOf(String.valueOf(str)).concat("greater than zero. Enter either an integer or a decimal number for ");
/* 461 */     str = String.valueOf(String.valueOf(str)).concat("the value of each variable and then press the OK button.\n\n");
/* 462 */     str = String.valueOf(String.valueOf(str)).concat("To backtrack, press the BACK button.");
/* 463 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 465 */     this.help_content_step.setText(str);
/* 466 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 474 */     String str = "Solve Automatically by the Interior-Point Algorithm\n\n";
/* 475 */     str = String.valueOf(String.valueOf(str)).concat("Once you have entered (or revised) a linear programming model with \"Enter ");
/* 476 */     str = String.valueOf(String.valueOf(str)).concat("or Revise a Linear Programming Model\" routine under the Procedure menu, ");
/* 477 */     str = String.valueOf(String.valueOf(str)).concat("this routine automatically sets up and solves (approximately) the model ");
/* 478 */     str = String.valueOf(String.valueOf(str)).concat("by the interior-point algorithm presented in Sec.7.4. You will need to ");
/* 479 */     str = String.valueOf(String.valueOf(str)).concat("enter an initial (feasible) trial solution, and then the procedure will perform ");
/* 480 */     str = String.valueOf(String.valueOf(str)).concat("as many iterations as desired (up to a maximum of 15). You will see the ");
/* 481 */     str = String.valueOf(String.valueOf(str)).concat("sequence of trial solutions displayed on the screen, which can then be ");
/* 482 */     str = String.valueOf(String.valueOf(str)).concat("printed under the File menu. These trial solutions eventually converge ");
/* 483 */     str = String.valueOf(String.valueOf(str)).concat("to an optimal solution. However, be aware that with a small number of ");
/* 484 */     str = String.valueOf(String.valueOf(str)).concat("iterations the final trial solution may not be very close to optimal.\n\n");
/* 485 */     str = String.valueOf(String.valueOf(str)).concat("This routine can handle up to six functional constraints and ten variables ");
/* 486 */     str = String.valueOf(String.valueOf(str)).concat("(including slack, surplus, and artificial variables).\n\n");
/* 487 */     str = String.valueOf(String.valueOf(str)).concat("Note that the Option menu provides you with a choice of two values (0.5 ");
/* 488 */     str = String.valueOf(String.valueOf(str)).concat("or 0.9) for alpha, with 0.5 as the default value.");
/* 489 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 491 */     this.help_content_procedure.setText(str);
/* 492 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void updateStepHelpPanel()
/*     */   {
/* 501 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/*     */     
/* 503 */     String str0 = "Solve Automatically by the Interior-Point Algorithm\n\n";
/* 504 */     str0 = String.valueOf(String.valueOf(str0)).concat("This procedure is used to apply the interior-point algorithm described in ");
/* 505 */     str0 = String.valueOf(String.valueOf(str0)).concat("Sec.7.4 to the linear programming model that you have entered. You enter the ");
/* 506 */     str0 = String.valueOf(String.valueOf(str0)).concat("initial trial solution, and then the computer will automatically perform up to ");
/* 507 */     str0 = String.valueOf(String.valueOf(str0)).concat("15 iterations. Note that the Option menu provides you with a choice of two ");
/* 508 */     str0 = String.valueOf(String.valueOf(str0)).concat("values (0.5 or 0.9) for alpha. You may print your results by choosing Print to file ");
/* 509 */     str0 = String.valueOf(String.valueOf(str0)).concat("under the File menu.");
/* 510 */     String str1 = "\n\nEntering the initial trial solution\n\n";
/* 511 */     str1 = String.valueOf(String.valueOf(str1)).concat("In order to start the interior-point algorithm, an initial trial solution is ");
/* 512 */     str1 = String.valueOf(String.valueOf(str1)).concat("needed. This solution needs to be both feasible and in the INTERIOR of the feasible ");
/* 513 */     str1 = String.valueOf(String.valueOf(str1)).concat("region, i.e., EVERY variable (including slack, surplus, and artificial variables) must be strictly ");
/* 514 */     str1 = String.valueOf(String.valueOf(str1)).concat("greater than zero. Enter either an integer or a decimal number for ");
/* 515 */     str1 = String.valueOf(String.valueOf(str1)).concat("the value of each variable and then press the OK button.\n\n");
/* 516 */     str1 = String.valueOf(String.valueOf(str1)).concat("To backtrack, click the BACK button.");
/* 517 */     String str2 = "\n\nPerforming another iteration\n\n";
/* 518 */     str2 = String.valueOf(String.valueOf(str2)).concat("Press the NEXT button to have the computer perform one more iteration of the ");
/* 519 */     str2 = String.valueOf(String.valueOf(str2)).concat("interior-point algorithm. A maximum of 15 iterations can be performed.");
/* 520 */     switch (this.step) {
/*     */     case 1: 
/* 522 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(str0))).append(str1).append(str))));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 532 */       break;
/*     */     case 2: 
/* 525 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(str0))).append(str2).append(str))));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 532 */       break;
/*     */     default: 
/* 528 */       this.help_content_step.setText(String.valueOf(String.valueOf(str0)).concat(String.valueOf(String.valueOf(str))));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPInteriorPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */