/*     */ import java.awt.BorderLayout;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORQTJacksonPanel
/*     */   extends IORActionPanel
/*     */ {
/*  33 */   private final String LESSTHAN = "<=";
/*     */   
/*  35 */   private static final int MAXFACILITY = 6;
/*     */   
/*  37 */   private IORQTProcessController controller = null;
/*     */   
/*  39 */   private JPanel mainPanel = new JPanel();
/*  40 */   private JPanel bottomPanel = null;
/*  41 */   private JPanel buttPanel = new JPanel();
/*  42 */   private JPanel numPanel = new JPanel();
/*  43 */   private JScrollPane scrlPane = null;
/*  44 */   private JPanel transtPanel = new JPanel();
/*  45 */   private JLabel numlabel = new JLabel("Number of facilities (<= 6) ,  m =  ");
/*     */   
/*  47 */   private JLabel indilabel = new JLabel("Press the FINISH button to solve for λ i");
/*     */   
/*  49 */   private IORQTJacksonPanel.IORQueueTableModel queueModel = null;
/*  50 */   private JTable table = null;
/*     */   
/*  52 */   private DecimalFormat decfm = new DecimalFormat();
/*  53 */   private int step = 1;
/*  54 */   private int num_facty = 2;
/*  55 */   private double[] a = new double[6];
/*  56 */   private double[][] p = new double[6][6];
/*     */   
/*  58 */   private WholeNumberField NumFacty = new WholeNumberField(2, 2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORQTJacksonPanel(IORQTProcessController c)
/*     */   {
/*  67 */     super(c);
/*  68 */     this.controller = c;
/*  69 */     add(this.mainPanel);
/*  70 */     this.bn_back.setVisible(false);
/*     */     
/*  72 */     this.decfm.setGroupingUsed(false);
/*     */     
/*  74 */     this.numPanel.add(this.numlabel);
/*  75 */     this.numPanel.add(this.NumFacty);
/*     */     
/*  77 */     this.queueModel = new IORQTJacksonPanel.IORQueueTableModel();
/*  78 */     this.table = new JTable(this.queueModel);
/*  79 */     this.table.setCellSelectionEnabled(true);
/*  80 */     this.table.setSelectionMode(0);
/*  81 */     this.scrlPane = new JScrollPane(this.table);
/*  82 */     this.scrlPane.setPreferredSize(new Dimension(400, 130));
/*  83 */     this.scrlPane.setMaximumSize(new Dimension(500, 130));
/*  84 */     this.scrlPane.setAlignmentX(0.5F);
/*  85 */     setupDecimalEditor();
/*     */     
/*  87 */     this.indilabel.setAlignmentX(0.5F);
/*  88 */     this.bottomPanel = new JPanel();
/*  89 */     this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel, 1));
/*  90 */     this.bottomPanel.add(this.indilabel);
/*     */     
/*  92 */     this.transtPanel.setLayout(new BoxLayout(this.transtPanel, 1));
/*  93 */     this.transtPanel.add(this.numPanel);
/*  94 */     this.transtPanel.add(Box.createVerticalStrut(20));
/*  95 */     JLabel lb = new JLabel("(To make a new number above take effect, you must press the ENTER key.)");
/*     */     
/*  97 */     JPanel p = new JPanel();
/*  98 */     p.add(lb);
/*  99 */     this.transtPanel.add(Box.createVerticalStrut(20));
/* 100 */     this.transtPanel.add(p);
/* 101 */     this.transtPanel.add(Box.createVerticalStrut(20));
/*     */     
/* 103 */     this.transtPanel.add(this.scrlPane);
/* 104 */     this.transtPanel.add(Box.createVerticalStrut(20));
/* 105 */     this.transtPanel.add(this.bottomPanel);
/*     */     
/* 107 */     this.mainPanel.setLayout(new BorderLayout());
/* 108 */     this.mainPanel.setBorder(new EmptyBorder(0, 20, 0, 20));
/* 109 */     this.mainPanel.add(this.transtPanel, "North");
/*     */     
/* 111 */     this.NumFacty.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 113 */         IORQTJacksonPanel.this.num_facty = IORQTJacksonPanel.this.NumFacty.getValue();
/* 114 */         if (IORQTJacksonPanel.this.num_facty > 6) {
/* 115 */           IORQTJacksonPanel.this.NumFacty.setValue(6);
/* 116 */           IORQTJacksonPanel.this.num_facty = 6;
/*     */         }
/* 118 */         if (IORQTJacksonPanel.this.num_facty <= 0) {
/* 119 */           IORQTJacksonPanel.this.NumFacty.setValue(1);
/* 120 */           IORQTJacksonPanel.this.num_facty = 1;
/*     */         }
/* 122 */         IORQTJacksonPanel.this.queueModel.fireTableStructureChanged();
/* 123 */         IORQTJacksonPanel.this.table.sizeColumnsToFit(-1);
/* 124 */         IORQTJacksonPanel.this.revalidate();
/* 125 */         IORQTJacksonPanel.this.repaint();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */   private void setupDecimalEditor()
/*     */   {
/* 133 */     DecimalField decField = new DecimalField(0.0D, 4, this.decfm);
/* 134 */     decField.setHorizontalAlignment(2);
/*     */     
/* 136 */     DefaultCellEditor decimalEditor = new DefaultCellEditor((IORQTJacksonPanel)this)
/*     */     {
/*     */       private final DecimalField val$decField;
/*     */       
/*     */       public Object getCellEditorValue() {
/* 141 */         return IORQTJacksonPanel.this.decfm.format(this.val$decField.getValue());
/*     */       }
/* 143 */     };
/* 144 */     this.table.setDefaultEditor(String.class, decimalEditor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finishProcedure()
/*     */   {
/* 153 */     Vector par = new Vector();
/* 154 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/* 156 */     this.table.getDefaultEditor(String.class).stopCellEditing();
/* 157 */     this.table.clearSelection();
/*     */     
/* 159 */     par.add(new Integer(this.num_facty));
/* 160 */     par.add(this.a);
/* 161 */     par.add(this.p);
/*     */     
/* 163 */     opr.parameters = par;
/* 164 */     opr.operation_code = 20101;
/* 165 */     this.controller.solver.doWork(opr, this.controller.data);
/* 166 */     this.step = 2;
/*     */     
/* 168 */     this.bottomPanel.removeAll();
/* 169 */     this.bottomPanel.add(new JLabel("Results"));
/* 170 */     this.bottomPanel.add(Box.createVerticalStrut(10));
/* 171 */     for (int i = 0; i < this.num_facty; i++) {
/* 172 */       this.bottomPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("λ").append(i + 1).append(" = ").append(this.decfm.format(this.controller.data.y[i]))))));
/*     */       
/* 174 */       this.bottomPanel.add(Box.createVerticalStrut(3));
/*     */     }
/*     */     
/* 177 */     revalidate();
/* 178 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/* 189 */     int[] para = new int[2];
/*     */     try
/*     */     {
/* 192 */       para = (int[])in.readObject();
/* 193 */       this.a = ((double[])in.readObject());
/* 194 */       this.p = ((double[][])in.readObject());
/* 195 */       in.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 198 */       e1.printStackTrace();
/* 199 */       System.out.println("Open fails");
/*     */     }
/* 201 */     this.step = para[0];
/* 202 */     this.num_facty = para[1];
/* 203 */     this.NumFacty.setValue(this.num_facty);
/* 204 */     this.queueModel.fireTableStructureChanged();
/* 205 */     this.table.sizeColumnsToFit(-1);
/* 206 */     if (this.step == 2) {
/* 207 */       this.bottomPanel.removeAll();
/* 208 */       this.bottomPanel.add(new JLabel("Results"));
/* 209 */       this.bottomPanel.add(Box.createVerticalStrut(10));
/* 210 */       for (int i = 0; i < this.num_facty; i++) {
/* 211 */         this.bottomPanel.add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("λ").append(i + 1).append(" = ").append(this.decfm.format(this.controller.data.y[i]))))));
/*     */         
/* 213 */         this.bottomPanel.add(Box.createVerticalStrut(3));
/*     */       }
/*     */     }
/* 216 */     revalidate();
/* 217 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/* 226 */     int[] interpara = new int[2];
/*     */     
/* 228 */     this.table.getDefaultEditor(String.class).stopCellEditing();
/* 229 */     this.table.clearSelection();
/*     */     
/* 231 */     interpara[0] = this.step;
/* 232 */     interpara[1] = this.num_facty;
/*     */     try {
/* 234 */       out.writeObject(interpara);
/* 235 */       out.writeObject(this.a);
/* 236 */       out.writeObject(this.p);
/* 237 */       out.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 240 */       e1.printStackTrace();
/* 241 */       System.out.println("Save fails");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */ 
/*     */   public void updatePanel() {}
/*     */   
/*     */ 
/*     */   class IORQueueTableModel
/*     */     extends AbstractTableModel
/*     */   {
/*     */     private int i;
/*     */     private int j;
/*     */     
/*     */     IORQueueTableModel() {}
/*     */     
/*     */     public int getColumnCount()
/*     */     {
/* 262 */       return IORQTJacksonPanel.this.num_facty + 2;
/*     */     }
/*     */     
/*     */     public int getRowCount() {
/* 266 */       return IORQTJacksonPanel.this.num_facty;
/*     */     }
/*     */     
/*     */     public String getColumnName(int col) {
/* 270 */       if (col == 0)
/* 271 */         return "Facility  j";
/* 272 */       if (col == 1) {
/* 273 */         return "aj";
/*     */       }
/* 275 */       return String.valueOf(String.valueOf(new StringBuffer("Pij (i = ").append(col - 1).append(")")));
/*     */     }
/*     */     
/*     */     public Object getValueAt(int row, int col) {
/* 279 */       String str = new String();
/* 280 */       DecimalFormat dfm = new DecimalFormat();
/* 281 */       if (col == 0)
/* 282 */         return String.valueOf(String.valueOf(new StringBuffer("   j = ").append(row + 1)));
/* 283 */       if (col == 1) {
/* 284 */         return dfm.format(IORQTJacksonPanel.this.a[row]);
/*     */       }
/*     */       
/* 287 */       return dfm.format(IORQTJacksonPanel.this.p[(col - 2)][row]);
/*     */     }
/*     */     
/*     */ 
/*     */     public Class getColumnClass(int c)
/*     */     {
/* 293 */       return getValueAt(0, c).getClass();
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean isCellEditable(int row, int col)
/*     */     {
/* 299 */       if (col == 0) {
/* 300 */         return false;
/*     */       }
/* 302 */       return true;
/*     */     }
/*     */     
/*     */     public void setValueAt(Object value, int row, int col)
/*     */     {
/* 307 */       if (col == 1)
/*     */       {
/* 309 */         IORQTJacksonPanel.this.a[row] = Double.parseDouble((String)value);
/*     */       }
/*     */       else
/*     */       {
/* 313 */         IORQTJacksonPanel.this.p[(col - 2)][row] = Double.parseDouble((String)value);
/*     */       }
/* 315 */       fireTableCellUpdated(row, col);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 325 */     String str = "Jackson Networks\n\n";
/* 326 */     str = String.valueOf(String.valueOf(str)).concat("This procedure is used to solve for the overall arrival rate at each of the facilities ");
/* 327 */     str = String.valueOf(String.valueOf(str)).concat("in a Jackson network.\n\n");
/* 328 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of facilities\n\n");
/* 329 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of facilities in the Jackson network, m (1 <= m <= 6, m an integer), and ");
/* 330 */     str = String.valueOf(String.valueOf(str)).concat("then press the ENTER key to make the number take effect.\n\n");
/* 331 */     str = String.valueOf(String.valueOf(str)).concat("Entering the external arrival rates\n\n");
/* 332 */     str = String.valueOf(String.valueOf(str)).concat("Enter the external arrival rate, a(j), for each facility, j (a(j) >= 0). To enter a number, ");
/* 333 */     str = String.valueOf(String.valueOf(str)).concat("enter an integer or a decimal number.\n\n");
/* 334 */     str = String.valueOf(String.valueOf(str)).concat("Entering the transition probabilities\n\n");
/* 335 */     str = String.valueOf(String.valueOf(str)).concat("Enter each of the probabilities, p(ij), that a customer completing service at facility i goes next to ");
/* 336 */     str = String.valueOf(String.valueOf(str)).concat("facility j (0 <= p(ij) <= 1). To enter a number, enter an integer or a decimal number.");
/* 337 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 339 */     this.help_content_step.setText(str);
/* 340 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 347 */     String str = "Jackson Network\n\n";
/* 348 */     str = String.valueOf(String.valueOf(str)).concat("This procedure solves a system of linear equations to find the overall arrival ");
/* 349 */     str = String.valueOf(String.valueOf(str)).concat("rate at each of the m facilities in a Jackson network, as described in Section 9 ");
/* 350 */     str = String.valueOf(String.valueOf(str)).concat("of the \"Queueing Theory\" chapter. The maximum number of facilities is m = 6. ");
/* 351 */     str = String.valueOf(String.valueOf(str)).concat("The results can be printed.");
/* 352 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 354 */     this.help_content_procedure.setText(str);
/* 355 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/* 358 */   private String str0 = "Jackson Networks\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 364 */   private String str1 = "Entering the number of facilities\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 379 */   private String str2 = "The solution\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void updateStepHelpPanel()
/*     */   {
/* 389 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/*     */     
/* 391 */     switch (this.step) {
/*     */     case 1: 
/* 393 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str1).append(str))));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 402 */       break;
/*     */     case 2: 
/* 396 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str2).append(str))));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 402 */       break;
/*     */     default: 
/* 399 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORQTJacksonPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */