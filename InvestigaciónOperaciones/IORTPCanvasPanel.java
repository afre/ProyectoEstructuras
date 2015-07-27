/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GridLayout;
/*      */ import java.awt.Point;
/*      */ import java.awt.Stroke;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Vector;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IORTPCanvasPanel
/*      */   extends JPanel
/*      */ {
/*   31 */   private static final int DEFAULT_HEIGHT = 360;
/*   32 */   private static final int DEFAULT_WIDTH = 430;
/*      */   
/*   34 */   private Color default_color = Color.lightGray;
/*   35 */   private Color select_color = Color.white;
/*      */   
/*      */ 
/*   38 */   public static final int STATE_NONE = -1;
/*      */   
/*   40 */   public static final int STATE_FIND_BASIC = 0;
/*      */   
/*   42 */   public static final int STATE_SOLVE_PROBLEM = 1;
/*      */   
/*      */ 
/*   45 */   private final int LOOP_INVALID = -1;
/*   46 */   private final int LOOP_VERTICAL = 0;
/*   47 */   private final int LOOP_HORIZONTAL = 1;
/*   48 */   private final int LOOP_EW = 2;
/*   49 */   private final int LOOP_WE = 3;
/*   50 */   private final int LOOP_NS = 4;
/*   51 */   private final int LOOP_SN = 5;
/*      */   
/*      */ 
/*   54 */   public static final int NO_CURRENT_CELL = -2;
/*      */   
/*   56 */   public static final int INVALID_CELL = -1;
/*      */   
/*   58 */   private static int count = 0;
/*      */   
/*   60 */   public IORTPCanvasPanel.TPCanvas canvas = null;
/*   61 */   private IORTPCanvasPanel.TPCell current_cell = null;
/*      */   
/*      */ 
/*   64 */   private int state = -1;
/*      */   
/*   66 */   private IORTPProcessController controller = null;
/*   67 */   private IORState iorstate = null;
/*      */   
/*      */ 
/*   70 */   private JLabel lb_vj_cd = null;
/*   71 */   private JLabel lb_ui_rd = null;
/*      */   
/*   73 */   private int source_row = -1;
/*   74 */   private int demand_column = -1;
/*   75 */   private int cell_size = -1;
/*      */   
/*   77 */   private IORTPActionPanel actionPanel = null;
/*      */   
/*   79 */   private final String[] functionStrings = { "Find Basic Variable", "Find Loops" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IORTPCanvasPanel(IORTPActionPanel p, int s)
/*      */   {
/*   91 */     this.state = s;
/*      */     
/*   93 */     this.actionPanel = p;
/*   94 */     this.controller = p.getController();
/*   95 */     this.iorstate = p.getState();
/*      */     
/*   97 */     this.source_row = this.controller.data.num_supply;
/*   98 */     this.demand_column = this.controller.data.num_demand;
/*      */     
/*  100 */     setSize(new Dimension(430, 360));
/*      */     
/*      */ 
/*  103 */     Dimension d = getSize();
/*      */     
/*      */ 
/*      */ 
/*  107 */     int w = d.width / this.demand_column;
/*  108 */     int h = d.height / this.source_row;
/*  109 */     this.cell_size = (w < h ? w : h);
/*      */     
/*      */ 
/*  112 */     this.canvas = new IORTPCanvasPanel.TPCanvas();
/*  113 */     d = new Dimension(this.cell_size * this.demand_column, this.cell_size * this.source_row);
/*      */     
/*  115 */     this.canvas.setMaximumSize(d);
/*  116 */     this.canvas.setMinimumSize(d);
/*      */     
/*  118 */     setLayout(new BoxLayout(this, 1));
/*  119 */     add(this.canvas);
/*      */     
/*  121 */     add(Box.createVerticalStrut(20));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setResultVisible(boolean b)
/*      */   {
/*  128 */     this.canvas.setResultVisible(b);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean setInitialUV()
/*      */   {
/*  139 */     double[] du = null;
/*  140 */     double[] ui = new double[this.controller.data.num_supply];
/*  141 */     double[] M_ui = new double[this.controller.data.num_supply];
/*  142 */     for (int i = 0; i < this.controller.data.num_supply; i++) {
/*  143 */       du = IORTPActionPanel.parseStringInput(this.canvas.u[i].getText());
/*  144 */       if (du == null) break;
/*  145 */       ui[i] = du[0];
/*  146 */       M_ui[i] = du[1];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  154 */     double[] dv = null;
/*  155 */     double[] vj = new double[this.controller.data.num_demand];
/*  156 */     double[] M_vj = new double[this.controller.data.num_demand];
/*  157 */     for (i = 0; i < this.controller.data.num_demand; i++) {
/*  158 */       dv = IORTPActionPanel.parseStringInput(this.canvas.v[i].getText());
/*  159 */       if (dv == null) break;
/*  160 */       vj[i] = dv[0];
/*  161 */       M_vj[i] = dv[1];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  170 */     if ((du != null) && (dv != null)) {
/*  171 */       Vector p = new Vector();
/*      */       
/*  173 */       p.addElement(ui);
/*      */       
/*  175 */       p.addElement(M_ui);
/*      */       
/*  177 */       p.addElement(vj);
/*      */       
/*  179 */       p.addElement(M_vj);
/*      */       
/*  181 */       IOROperation operation = new IOROperation(20301, p);
/*      */       
/*      */ 
/*      */ 
/*  185 */       if (!this.controller.solver.doWork(operation, this.controller.data)) {
/*  186 */         JOptionPane.showMessageDialog(this, "Error in setting the initial ui and vj");
/*  187 */         return false;
/*      */       }
/*  189 */       repaint();
/*      */     }
/*      */     else {
/*  192 */       JOptionPane.showMessageDialog(this, "Please check the input formats for ui and vj");
/*  193 */       return false;
/*      */     }
/*      */     
/*  196 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setUVEditable(boolean b)
/*      */   {
/*  203 */     for (int i = 0; i < this.source_row; i++) {
/*  204 */       this.canvas.u[i].setEditable(b);
/*      */     }
/*      */     
/*  207 */     for (int j = 0; j < this.demand_column; j++) {
/*  208 */       this.canvas.v[j].setEditable(b);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setUVDefaultColor()
/*      */   {
/*  216 */     for (int i = 0; i < this.source_row; i++) {
/*  217 */       this.canvas.u[i].setBackground(this.default_color);
/*      */     }
/*      */     
/*  220 */     for (int j = 0; j < this.demand_column; j++) {
/*  221 */       this.canvas.v[j].setBackground(this.default_color);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setUVSelectColor()
/*      */   {
/*  229 */     for (int i = 0; i < this.source_row; i++) {
/*  230 */       this.canvas.u[i].setBackground(this.select_color);
/*      */     }
/*      */     
/*  233 */     for (int j = 0; j < this.demand_column; j++) {
/*  234 */       this.canvas.v[j].setBackground(this.select_color);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setUVLabelValue()
/*      */   {
/*  242 */     IORTableCell t = null;
/*  243 */     for (int i = 0; i < this.source_row; i++) {
/*  244 */       t = new IORTableCell(this.controller.data.M_u[i], this.controller.data.u[i]);
/*  245 */       this.canvas.u[i].setText(t.toString().toUpperCase());
/*      */     }
/*      */     
/*  248 */     for (int j = 0; j < this.demand_column; j++) {
/*  249 */       t = new IORTableCell(this.controller.data.M_v[j], this.controller.data.v[j]);
/*  250 */       this.canvas.v[j].setText(t.toString().toUpperCase());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setCellEnabled(boolean b)
/*      */   {
/*  258 */     for (int i = 0; i < this.source_row; i++) {
/*  259 */       for (int j = 0; j < this.demand_column; j++) {
/*  260 */         this.canvas.cell[i][j].setEnabled(b);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isLastRowOrColumn()
/*      */   {
/*  270 */     int n = 0;
/*      */     
/*  272 */     for (int i = 0; i < this.source_row; i++) {
/*  273 */       for (int j = 0; j < this.demand_column; j++) {
/*  274 */         if (this.controller.data.is_deleted[i][j] == 0) {
/*  275 */           n++;
/*  276 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  281 */     int m = 0;
/*  282 */     boolean is_last_col = true;
/*      */     
/*  284 */     for (int j = 0; j < this.demand_column; j++) {
/*  285 */       for (int i = 0; i < this.source_row; i++) {
/*  286 */         if (this.controller.data.is_deleted[i][j] == 0) {
/*  287 */           m++;
/*  288 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  293 */     if ((n == 1) || (m == 1)) {
/*  294 */       return true;
/*      */     }
/*  296 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void updateLoop()
/*      */   {
/*  303 */     this.canvas.updateLoop();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void updatePanel()
/*      */   {
/*  310 */     this.canvas.updatePanelForOption();
/*  311 */     setUVLabelValue();
/*  312 */     repaint();
/*      */   }
/*      */   
/*      */   public class TPCanvas extends JPanel
/*      */   {
/*  317 */     private IORTPCanvasPanel.TPCell[][] cell = null;
/*  318 */     private ButtonGroup group = null;
/*      */     
/*  320 */     private JLabel[] supply = null;
/*  321 */     private JLabel[] demand = null;
/*      */     
/*  323 */     private JTextField[] u = null;
/*  324 */     private JTextField[] v = null;
/*      */     
/*      */ 
/*  327 */     private MultilineLabel tf_Z = null;
/*      */     
/*  329 */     private MultilineLabel tf_result = null;
/*      */     
/*      */ 
/*      */     public TPCanvas()
/*      */     {
/*  334 */       setLayout(new GridLayout(IORTPCanvasPanel.this.source_row + 2, IORTPCanvasPanel.this.demand_column + 3));
/*      */       
/*  336 */       this.cell = new IORTPCanvasPanel.TPCell[IORTPCanvasPanel.this.source_row][IORTPCanvasPanel.this.demand_column];
/*      */       
/*  338 */       this.supply = new JLabel[IORTPCanvasPanel.this.source_row];
/*  339 */       this.demand = new JLabel[IORTPCanvasPanel.this.demand_column];
/*      */       
/*  341 */       this.u = new JTextField[IORTPCanvasPanel.this.source_row];
/*  342 */       this.v = new JTextField[IORTPCanvasPanel.this.demand_column];
/*      */       
/*      */ 
/*  345 */       JLabel lb = new JLabel();
/*  346 */       lb.setEnabled(false);
/*  347 */       add(lb);
/*      */       
/*  349 */       setDoubleBuffered(false);
/*  350 */       JPanel p = null;
/*      */       
/*  352 */       int i = -1;int j = -1;
/*  353 */       for (j = 1; j <= IORTPCanvasPanel.this.demand_column; j++) {
/*  354 */         p = new JPanel(new GridLayout(2, 1));
/*  355 */         p.setDoubleBuffered(false);
/*  356 */         p.add(new JLabel());
/*  357 */         lb = new JLabel("D".concat(String.valueOf(String.valueOf(j))));
/*  358 */         lb.setHorizontalAlignment(0);
/*  359 */         lb.setEnabled(false);
/*  360 */         p.add(lb);
/*  361 */         add(p);
/*      */       }
/*      */       
/*  364 */       p = new JPanel(new BorderLayout());
/*  365 */       p.setDoubleBuffered(false);
/*  366 */       switch (IORTPCanvasPanel.this.state) {
/*      */       case 0: 
/*  368 */         MultilineLabel mll = new MultilineLabel("\nRemain\nSupply");
/*  369 */         mll.setEnabled(false);
/*  370 */         p.add(mll, "Center");
/*  371 */         break;
/*      */       
/*      */       case 1: 
/*  374 */         p = new JPanel(new GridLayout(2, 1));
/*  375 */         p.setDoubleBuffered(false);
/*  376 */         p.add(new JLabel());
/*  377 */         p.add(lb = new JLabel("Supply"));
/*  378 */         lb.setEnabled(false);
/*  379 */         break;
/*      */       
/*      */       default: 
/*  382 */         System.err.println("Error in setting up Supply labels");
/*      */       }
/*  384 */       add(p);
/*      */       
/*  386 */       p = new JPanel(new GridLayout(2, 1));
/*  387 */       p.setDoubleBuffered(false);
/*  388 */       p.add(new JLabel());
/*  389 */       IORTPCanvasPanel.this.lb_ui_rd = new JLabel("u(i)");
/*  390 */       IORTPCanvasPanel.this.lb_ui_rd.setHorizontalAlignment(2);
/*  391 */       IORTPCanvasPanel.this.lb_ui_rd.setEnabled(false);
/*  392 */       p.add(IORTPCanvasPanel.this.lb_ui_rd);
/*  393 */       add(p);
/*      */       
/*      */ 
/*  396 */       double r = -1.0D;
/*      */       
/*  398 */       for (i = 1; i <= IORTPCanvasPanel.this.source_row; i++) {
/*  399 */         p = new JPanel(new GridLayout(1, 2));
/*  400 */         p.setDoubleBuffered(false);
/*  401 */         p.add(new JLabel());
/*  402 */         lb = new JLabel("S".concat(String.valueOf(String.valueOf(i))));
/*  403 */         lb.setEnabled(false);
/*  404 */         p.add(lb);
/*  405 */         add(p);
/*      */         
/*  407 */         this.group = new ButtonGroup();
/*  408 */         for (j = 0; j < IORTPCanvasPanel.this.demand_column; j++) {
/*  409 */           IORTPCanvasPanel tmp615_614 = IORTPCanvasPanel.this;tmp615_614.getClass();this.cell[(i - 1)][j] = new IORTPCanvasPanel.TPCell(tmp615_614, i - 1, j, null);
/*  410 */           this.group.add(this.cell[(i - 1)][j]);
/*  411 */           add(this.cell[(i - 1)][j]);
/*      */         }
/*  413 */         String str = "0";
/*  414 */         this.supply[(i - 1)] = new JLabel();
/*  415 */         this.supply[(i - 1)].setText(str);
/*  416 */         this.supply[(i - 1)].setHorizontalAlignment(0);
/*  417 */         add(this.supply[(i - 1)]);
/*      */         
/*  419 */         this.u[(i - 1)] = new JTextField(str);
/*  420 */         this.u[(i - 1)].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*  421 */         this.u[(i - 1)].setBackground(IORTPCanvasPanel.this.default_color);
/*  422 */         add(this.u[(i - 1)]);
/*      */       }
/*      */       
/*      */ 
/*  426 */       p = new JPanel(new GridLayout(2, 1));
/*  427 */       p.setDoubleBuffered(false);
/*  428 */       switch (IORTPCanvasPanel.this.state) {
/*      */       case 0: 
/*  430 */         lb = new JLabel("R.D.");
/*  431 */         break;
/*      */       
/*      */       case 1: 
/*  434 */         lb = new JLabel("Demand");
/*  435 */         break;
/*      */       
/*      */       default: 
/*  438 */         System.err.println("Error in setting up Demand labels");
/*      */       }
/*      */       
/*  441 */       lb.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
/*  442 */       lb.setHorizontalAlignment(4);
/*  443 */       lb.setEnabled(false);
/*  444 */       p.add(lb);
/*      */       
/*  446 */       IORTPCanvasPanel.this.lb_vj_cd = new JLabel("v(j)");
/*  447 */       IORTPCanvasPanel.this.lb_vj_cd.setHorizontalAlignment(0);
/*  448 */       IORTPCanvasPanel.this.lb_vj_cd.setEnabled(false);
/*  449 */       p.add(IORTPCanvasPanel.this.lb_vj_cd);
/*      */       
/*  451 */       add(p);
/*      */       
/*  453 */       for (j = 1; j <= IORTPCanvasPanel.this.demand_column; j++) {
/*  454 */         String str = "0";
/*  455 */         this.demand[(j - 1)] = new JLabel();
/*  456 */         this.demand[(j - 1)].setHorizontalAlignment(0);
/*  457 */         this.demand[(j - 1)].setText(str);
/*      */         
/*  459 */         this.v[(j - 1)] = new JTextField(str);
/*  460 */         this.v[(j - 1)].setBackground(IORTPCanvasPanel.this.default_color);
/*  461 */         this.v[(j - 1)].setHorizontalAlignment(0);
/*  462 */         this.v[(j - 1)].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*      */         
/*  464 */         p = new JPanel(new GridLayout(2, 1));
/*  465 */         p.setDoubleBuffered(false);
/*  466 */         p.add(this.demand[(j - 1)]);
/*  467 */         p.add(this.v[(j - 1)]);
/*      */         
/*  469 */         add(p);
/*      */       }
/*      */       
/*  472 */       switch (IORTPCanvasPanel.this.state) {
/*      */       case 0: 
/*  474 */         lb = new JLabel();
/*  475 */         lb.setEnabled(false);
/*  476 */         add(lb);
/*  477 */         break;
/*      */       
/*      */       case 1: 
/*  480 */         this.tf_Z = new MultilineLabel("\n Z = \n ");
/*  481 */         this.tf_Z.setEditable(false);
/*  482 */         this.tf_Z.setForeground(Color.red);
/*      */         
/*  484 */         this.tf_Z.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*  485 */         add(this.tf_Z);
/*      */         
/*      */ 
/*  488 */         this.tf_result = new MultilineLabel("100");
/*  489 */         this.tf_result.setEditable(false);
/*  490 */         this.tf_result.setForeground(Color.red);
/*      */         
/*  492 */         this.tf_result.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*  493 */         add(this.tf_result);
/*  494 */         break;
/*      */       default: 
/*  496 */         System.out.println("Wrong state input in canvas constructor");
/*      */       }
/*      */       
/*  499 */       updatePanelForOption();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private void updatePanelForOption()
/*      */     {
/*  506 */       String str = "";
/*      */       
/*  508 */       if (IORTPCanvasPanel.this.state == 0) {
/*  509 */         switch (IORTPCanvasPanel.this.controller.getOption()) {
/*      */         case 1: 
/*  511 */           IORTPCanvasPanel.this.lb_ui_rd.setText("row dif.");
/*  512 */           IORTPCanvasPanel.this.lb_vj_cd.setText("col. dif.");
/*  513 */           break;
/*      */         case 2: 
/*  515 */           IORTPCanvasPanel.this.lb_ui_rd.setText("U(I)");
/*  516 */           IORTPCanvasPanel.this.lb_vj_cd.setText("V(J)");
/*  517 */           break;
/*      */         }
/*      */         
/*      */       }
/*  521 */       for (int i = 1; i <= IORTPCanvasPanel.this.source_row; i++) {
/*  522 */         switch (IORTPCanvasPanel.this.state) {
/*      */         case 0: 
/*  524 */           str = IORTPActionPanel.getCellString(IORTPCanvasPanel.this.controller.data.u[(i - 1)], IORTPCanvasPanel.this.controller.data.M_u[(i - 1)]);
/*  525 */           this.u[(i - 1)].setText(str);
/*  526 */           if ((IORTPCanvasPanel.this.controller.getOption() == 1) && 
/*  527 */             (isRowDeleted(i - 1))) {
/*  528 */             this.u[(i - 1)].setText(" ");
/*      */           }
/*  530 */           str = "".concat(String.valueOf(String.valueOf(IORTPCanvasPanel.this.controller.data.remain_supply[(i - 1)])));
/*  531 */           break;
/*      */         case 1: 
/*  533 */           str = "".concat(String.valueOf(String.valueOf(IORTPCanvasPanel.this.controller.data.supply[(i - 1)])));
/*  534 */           break;
/*      */         }
/*  536 */         this.supply[(i - 1)].setText(IORActionPanel.trim(str));
/*      */       }
/*  538 */       for (int j = 1; j <= IORTPCanvasPanel.this.demand_column; j++) {
/*  539 */         switch (IORTPCanvasPanel.this.state) {
/*      */         case 0: 
/*  541 */           str = IORTPActionPanel.getCellString(IORTPCanvasPanel.this.controller.data.v[(j - 1)], IORTPCanvasPanel.this.controller.data.M_v[(j - 1)]);
/*  542 */           this.v[(j - 1)].setText(str);
/*  543 */           if ((IORTPCanvasPanel.this.controller.getOption() == 1) && 
/*  544 */             (isColumnDeleted(j - 1))) {
/*  545 */             this.v[(j - 1)].setText(" ");
/*      */           }
/*  547 */           str = "".concat(String.valueOf(String.valueOf(IORTPCanvasPanel.this.controller.data.remain_demand[(j - 1)])));
/*  548 */           break;
/*      */         case 1: 
/*  550 */           str = "".concat(String.valueOf(String.valueOf(IORTPCanvasPanel.this.controller.data.demand[(j - 1)])));
/*  551 */           break;
/*      */         }
/*  553 */         this.demand[(j - 1)].setText(IORActionPanel.trim(str));
/*      */       }
/*      */       
/*  556 */       if (IORTPCanvasPanel.this.state == 1) {
/*  557 */         str = "";
/*  558 */         if (this.tf_result.isVisible()) {
/*  559 */           if ((Math.abs(IORTPCanvasPanel.this.controller.data.M_z) > 1.0E-6D) && (Math.abs(IORTPCanvasPanel.this.controller.data.z) > 1.0E-6D)) {
/*  560 */             str = "\n ".concat(String.valueOf(String.valueOf(IORTPCanvasPanel.this.controller.data.M_z)));
/*  561 */             str = String.valueOf(String.valueOf(IORActionPanel.trim(str))).concat("M");
/*  562 */             if (IORTPCanvasPanel.this.controller.data.z > 0) {
/*  563 */               str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("\n+ ".concat(String.valueOf(String.valueOf(IORTPCanvasPanel.this.controller.data.z))))));
/*      */             } else {
/*  565 */               str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("\n- ".concat(String.valueOf(String.valueOf(IORTPCanvasPanel.this.controller.data.z))))));
/*      */             }
/*      */           } else {
/*  568 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("\n ".concat(String.valueOf(String.valueOf(IORTPActionPanel.getCellString(IORTPCanvasPanel.this.controller.data.z, IORTPCanvasPanel.this.controller.data.M_z)))))));
/*  569 */             str = String.valueOf(String.valueOf(str)).concat("\n ");
/*      */           }
/*  571 */           this.tf_result.setText(IORActionPanel.trim(str));
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     private boolean isRowDeleted(int r)
/*      */     {
/*  578 */       boolean is_deleted = true;
/*  579 */       for (int j = 0; j < IORTPCanvasPanel.this.demand_column; j++) {
/*  580 */         if (IORTPCanvasPanel.this.controller.data.is_deleted[r][j] == 0) {
/*  581 */           is_deleted = false;
/*  582 */           break;
/*      */         }
/*      */       }
/*  585 */       return is_deleted;
/*      */     }
/*      */     
/*      */     private boolean isColumnDeleted(int c)
/*      */     {
/*  590 */       boolean is_deleted = true;
/*  591 */       for (int i = 0; i < IORTPCanvasPanel.this.source_row; i++) {
/*  592 */         if (IORTPCanvasPanel.this.controller.data.is_deleted[i][c] == 0) {
/*  593 */           is_deleted = false;
/*  594 */           break;
/*      */         }
/*      */       }
/*  597 */       return is_deleted;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public int deleteRow()
/*      */     {
/*  604 */       if (IORTPCanvasPanel.this.actionPanel.getStep() != 2) {
/*  605 */         return -2;
/*      */       }
/*  607 */       if (IORTPCanvasPanel.this.current_cell == null) {
/*  608 */         return -2;
/*      */       }
/*  610 */       if (IORTPCanvasPanel.this.current_cell.isBasicVariable()) {
/*  611 */         int row = IORTPCanvasPanel.this.current_cell.getRow();
/*      */         
/*  613 */         Vector p = new Vector();
/*      */         
/*  615 */         p.addElement(new Integer(row));
/*      */         
/*  617 */         IOROperation operation = new IOROperation(20202, p);
/*      */         
/*      */ 
/*  620 */         if (IORTPCanvasPanel.this.controller.solver.doWork(operation, IORTPCanvasPanel.this.controller.data)) {
/*  621 */           IORTPCanvasPanel.this.iorstate.addStep(operation);
/*  622 */           IORTPCanvasPanel.this.actionPanel.setStep(1);
/*  623 */           IORTPCanvasPanel.this.actionPanel.addStep();
/*  624 */           IORTPCanvasPanel.this.actionPanel.updatePanel();
/*      */         }
/*      */         else {
/*  627 */           JOptionPane.showMessageDialog(this, IORTPCanvasPanel.this.controller.solver.getErrInfo());
/*      */         }
/*      */         
/*  630 */         repaint();
/*  631 */         return row + 1;
/*      */       }
/*      */       
/*  634 */       return -1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private void resetLoop()
/*      */     {
/*  641 */       int i = -1;int j = -1;
/*  642 */       for (i = 0; i < IORTPCanvasPanel.this.source_row; i++) {
/*  643 */         for (j = 0; j < IORTPCanvasPanel.this.demand_column; j++) {
/*  644 */           this.cell[i][j].resetLoopState();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public void updateLoop() {
/*  650 */       resetLoop();
/*  651 */       updateCellLoopState();
/*      */     }
/*      */     
/*      */     private void setResultVisible(boolean b)
/*      */     {
/*  656 */       if (this.tf_Z != null) {
/*  657 */         this.tf_Z.setVisible(b);
/*      */       }
/*  659 */       if (this.tf_result != null) {
/*  660 */         this.tf_result.setVisible(b);
/*      */       }
/*      */     }
/*      */     
/*      */     private void updateCellLoopState() {
/*  665 */       int n = IORTPCanvasPanel.this.controller.data.loop.size();
/*  666 */       for (int i = 1; i < n; i++) {
/*  667 */         IORTPCanvasPanel.TPCell start = getLoopNode(i - 1);
/*  668 */         IORTPCanvasPanel.TPCell end = getLoopNode(i);
/*  669 */         setLoopSegment(start, end);
/*      */       }
/*      */       
/*  672 */       for (int i = 0; i < n; i++) {
/*  673 */         IORTPCanvasPanel.TPCell node = getLoopNode(i);
/*  674 */         node.setOnLoop(true);
/*  675 */         if (i % 2 == 1) {
/*  676 */           node.setDirection(false);
/*      */         } else {
/*  678 */           node.setDirection(true);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     private IORTPCanvasPanel.TPCell getLoopNode(int i)
/*      */     {
/*  686 */       if (IORTPCanvasPanel.this.controller.data.loop.size() > i)
/*      */       {
/*  688 */         Point p = (Point)IORTPCanvasPanel.this.controller.data.loop.elementAt(i);
/*  689 */         return this.cell[p.x][p.y];
/*      */       }
/*      */       
/*  692 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private void buildLoop(IORTPCanvasPanel.TPCell node)
/*      */     {
/*  699 */       node.setOnLoop(true);
/*      */       
/*      */ 
/*      */ 
/*  703 */       int n = IORTPCanvasPanel.this.controller.data.loop.size();
/*  704 */       if (n % 2 == 0) {
/*  705 */         node.setDirection(false);
/*      */       } else {
/*  707 */         node.setDirection(true);
/*      */       }
/*  709 */       if (n > 1) {
/*  710 */         IORTPCanvasPanel.TPCell start = getLoopNode(n - 2);
/*  711 */         IORTPCanvasPanel.TPCell end = getLoopNode(n - 1);
/*  712 */         setLoopSegment(start, end);
/*      */       }
/*      */     }
/*      */     
/*      */     private void setLoopSegment(IORTPCanvasPanel.TPCell start, IORTPCanvasPanel.TPCell end)
/*      */     {
/*  718 */       int i = -1;
/*  719 */       switch (getSegmentDirection(start, end)) {
/*      */       case 4: 
/*  721 */         this.cell[start.row][start.column].addLoopState(4);
/*  722 */         this.cell[end.row][end.column].addLoopState(5);
/*  723 */         for (i = start.row + 1; i <= end.row - 1;) {
/*  724 */           this.cell[i][start.column].addLoopState(0);i++; continue;
/*      */           
/*      */ 
/*      */ 
/*  728 */           this.cell[start.row][start.column].addLoopState(5);
/*  729 */           this.cell[end.row][end.column].addLoopState(4);
/*  730 */           for (i = end.row + 1; i <= start.row - 1;) {
/*  731 */             this.cell[i][start.column].addLoopState(0);i++; continue;
/*      */             
/*      */ 
/*      */ 
/*  735 */             this.cell[start.row][start.column].addLoopState(2);
/*  736 */             this.cell[end.row][end.column].addLoopState(3);
/*  737 */             for (i = end.column + 1; i <= start.column - 1;) {
/*  738 */               this.cell[start.row][i].addLoopState(1);i++; continue;
/*      */               
/*      */ 
/*      */ 
/*  742 */               this.cell[start.row][start.column].addLoopState(3);
/*  743 */               this.cell[end.row][end.column].addLoopState(2);
/*  744 */               for (i = start.column + 1; i <= end.column - 1; i++) {
/*  745 */                 this.cell[start.row][i].addLoopState(1);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */     }
/*      */     
/*      */ 
/*      */     private int getSegmentDirection(IORTPCanvasPanel.TPCell start, IORTPCanvasPanel.TPCell end)
/*      */     {
/*  757 */       if ((start.row > end.row) && (start.column == end.column)) {
/*  758 */         return 5;
/*      */       }
/*  760 */       if ((start.row < end.row) && (start.column == end.column)) {
/*  761 */         return 4;
/*      */       }
/*  763 */       if ((start.row == end.row) && (start.column > end.column)) {
/*  764 */         return 2;
/*      */       }
/*  766 */       if ((start.row == end.row) && (start.column < end.column)) {
/*  767 */         return 3;
/*      */       }
/*  769 */       return -1;
/*      */     }
/*      */     
/*      */ 
/*      */     private boolean isValidNode(IORTPCanvasPanel.TPCell node)
/*      */     {
/*  775 */       boolean valid = true;
/*      */       
/*      */ 
/*  778 */       if (!IORTPCanvasPanel.this.controller.data.loop.isEmpty())
/*      */       {
/*  780 */         int n = IORTPCanvasPanel.this.controller.data.loop.size();
/*  781 */         IORTPCanvasPanel.TPCell last = getLoopNode(n - 1);
/*      */         
/*  783 */         if (last.equals(node)) {
/*  784 */           valid = false;
/*      */         }
/*  786 */         else if ((last.row == node.row) || (last.column == node.column)) {
/*  787 */           valid = true;
/*      */         } else {
/*  789 */           valid = false;
/*      */         }
/*      */       }
/*      */       
/*  793 */       return valid;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public int deleteColumn()
/*      */     {
/*  800 */       if (IORTPCanvasPanel.this.actionPanel.getStep() != 2) {
/*  801 */         return -2;
/*      */       }
/*  803 */       if (IORTPCanvasPanel.this.current_cell == null) {
/*  804 */         return -2;
/*      */       }
/*  806 */       if (IORTPCanvasPanel.this.current_cell.isBasicVariable()) {
/*  807 */         int column = IORTPCanvasPanel.this.current_cell.getColumn();
/*  808 */         Vector p = new Vector();
/*      */         
/*  810 */         p.addElement(new Integer(column));
/*      */         
/*  812 */         IOROperation operation = new IOROperation(20203, p);
/*      */         
/*      */ 
/*      */ 
/*  816 */         if (IORTPCanvasPanel.this.controller.solver.doWork(operation, IORTPCanvasPanel.this.controller.data)) {
/*  817 */           IORTPCanvasPanel.this.iorstate.addStep(operation);
/*  818 */           IORTPCanvasPanel.this.actionPanel.setStep(1);
/*  819 */           IORTPCanvasPanel.this.actionPanel.addStep();
/*  820 */           IORTPCanvasPanel.this.actionPanel.updatePanel();
/*      */         }
/*      */         else {
/*  823 */           JOptionPane.showMessageDialog(this, IORTPCanvasPanel.this.controller.solver.getErrInfo());
/*      */         }
/*      */         
/*  826 */         repaint();
/*  827 */         return column + 1;
/*      */       }
/*  829 */       return -1;
/*      */     }
/*      */     
/*      */     public void getSelCell(int[] sel) {
/*  833 */       if (IORTPCanvasPanel.this.current_cell == null) {
/*  834 */         sel[0] = -1;
/*  835 */         sel[1] = -1;
/*      */       }
/*      */       else {
/*  838 */         sel[0] = IORTPCanvasPanel.this.current_cell.getRow();
/*  839 */         sel[1] = IORTPCanvasPanel.this.current_cell.getColumn();
/*      */       }
/*      */     }
/*      */     
/*      */     public void setSelCell(int row, int column)
/*      */     {
/*  845 */       this.cell[row][column].setCurrent();
/*      */     }
/*      */     
/*      */     public void setState() {
/*  849 */       IORTPCanvasPanel.this.iorstate = IORTPCanvasPanel.this.actionPanel.getState();
/*      */     }
/*      */     
/*      */     public Vector getCellInnerVariables(int row, int col) {
/*  853 */       return this.cell[row][col].getInnerVariables();
/*      */     }
/*      */     
/*      */     public void setCellInnerVariables(int row, int col, Vector v) {
/*  857 */       this.cell[row][col].setInnerVariables(v);
/*      */     }
/*      */   }
/*      */   
/*      */   private class TPCell extends JButton {
/*  862 */     TPCell(int x$1, int x$2, IORTPCanvasPanel..4 x$3) { this(x$1, x$2); }
/*      */     
/*  864 */     private int id = -1;
/*  865 */     private int row = -1;
/*  866 */     private int column = -1;
/*      */     
/*      */ 
/*  869 */     private TPCell me = null;
/*  870 */     private Vector loop_state = null;
/*      */     
/*      */ 
/*  873 */     private int oval_x = -1;
/*  874 */     private int oval_y = -1;
/*  875 */     private int oval_h = -1;
/*  876 */     private int oval_w = -1;
/*      */     
/*  878 */     private boolean is_on_loop = false;
/*  879 */     private boolean is_plus = false;
/*      */     
/*  881 */     private final Font signFont = new Font("SansSerif", 1, 15);
/*      */     
/*      */     private TPCell(int r, int c)
/*      */     {
/*  885 */       setBorder(new EmptyBorder(0, 0, 0, 0));
/*  886 */       this.row = r;
/*  887 */       this.column = c;
/*      */       
/*  889 */       this.loop_state = new Vector();
/*      */       
/*  891 */       setBackground(IORTPCanvasPanel.this.default_color);
/*      */       
/*  893 */       this.me = this;
/*  894 */       this.id = IORTPCanvasPanel.access$26();
/*      */       
/*  896 */       addActionListener(new IORTPCanvasPanel.1((TPCell)this));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  908 */       addMouseListener(new IORTPCanvasPanel.2((TPCell)this));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  928 */       addKeyListener(new IORTPCanvasPanel.3((TPCell)this));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void doDoubleClick()
/*      */     {
/*  956 */       switch (IORTPCanvasPanel.this.state) {
/*      */       case 0: 
/*  958 */         setBasicVariable();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  981 */         break;
/*      */       case 1: 
/*  962 */         switch (IORTPCanvasPanel.this.actionPanel.getStep()) {
/*      */         case 1: 
/*  964 */           setEnteringBasicVariable();
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  981 */           break;
/*      */         case 2: 
/*  968 */           addNode();
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  981 */           break;
/*      */         case 3: 
/*  972 */           setLeavingBasicVariable();
/*      */         }
/*      */         
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  981 */         break; }
/*      */     }
/*      */     
/*      */     private void setBasicVariable() {
/*  985 */       if (IORTPCanvasPanel.this.actionPanel.getStep() != 1) {
/*  986 */         return;
/*      */       }
/*  988 */       if (isBasicVariable()) {
/*  989 */         return;
/*      */       }
/*  991 */       String input = JOptionPane.showInputDialog(this, "Please input the flow for the selected cell:");
/*      */       try
/*      */       {
/*  994 */         d = Double.parseDouble(input);
/*      */       } catch (Exception e) {
/*      */         double d;
/*      */         return;
/*      */       }
/*      */       double d;
/* 1000 */       Vector p = new Vector();
/*      */       
/* 1002 */       p.addElement(new Integer(this.row));
/*      */       
/* 1004 */       p.addElement(new Integer(this.column));
/*      */       
/* 1006 */       p.addElement(new Double(d));
/*      */       
/* 1008 */       p.addElement(new Integer(IORTPCanvasPanel.this.actionPanel.getStep()));
/*      */       
/* 1010 */       IOROperation operation = new IOROperation(20201, p);
/*      */       
/*      */ 
/* 1013 */       if (IORTPCanvasPanel.this.controller.solver.doWork(operation, IORTPCanvasPanel.this.controller.data)) {
/* 1014 */         setCurrent();
/* 1015 */         IORTPCanvasPanel.this.iorstate.addStep(operation);
/* 1016 */         if (!IORTPCanvasPanel.this.isLastRowOrColumn()) {
/* 1017 */           IORTPCanvasPanel.this.actionPanel.setStep(2);
/*      */         }
/* 1019 */         IORTPCanvasPanel.this.actionPanel.addStep();
/* 1020 */         IORTPCanvasPanel.this.actionPanel.updatePanel();
/*      */       }
/*      */       else {
/* 1023 */         JOptionPane.showMessageDialog(this, IORTPCanvasPanel.this.controller.solver.getErrInfo());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private void setLeavingBasicVariable()
/*      */     {
/* 1031 */       if (!IORTPCanvasPanel.this.controller.data.loop.contains(new Point(this.row, this.column))) {
/* 1032 */         return;
/*      */       }
/* 1034 */       String input = JOptionPane.showInputDialog(this, "Please input the new value for the entering basic variable:");
/*      */       try
/*      */       {
/* 1037 */         d = Double.parseDouble(input);
/*      */       } catch (Exception e) {
/*      */         double d;
/*      */         return;
/*      */       }
/*      */       double d;
/* 1043 */       Vector p = new Vector();
/*      */       
/* 1045 */       p.addElement(new Integer(this.row));
/*      */       
/* 1047 */       p.addElement(new Integer(this.column));
/*      */       
/* 1049 */       p.addElement(new Double(d));
/*      */       
/* 1051 */       IOROperation operation = new IOROperation(20304, p);
/*      */       
/*      */ 
/* 1054 */       if (IORTPCanvasPanel.this.controller.solver.doWork(operation, IORTPCanvasPanel.this.controller.data)) {
/* 1055 */         setCurrent();
/* 1056 */         IORTPCanvasPanel.this.iorstate.addStep(operation);
/* 1057 */         IORTPCanvasPanel.this.actionPanel.setStep(1);
/* 1058 */         IORTPCanvasPanel.this.actionPanel.addStep();
/* 1059 */         IORTPCanvasPanel.this.actionPanel.updatePanel();
/* 1060 */         IORTPCanvasPanel.this.actionPanel.revalidate();
/* 1061 */         IORTPCanvasPanel.this.actionPanel.repaint();
/*      */       }
/*      */       else {
/* 1064 */         JOptionPane.showMessageDialog(this, IORTPCanvasPanel.this.controller.solver.getErrInfo());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     private void addNode()
/*      */     {
/* 1071 */       if (!IORTPCanvasPanel.TPCanvas.access$30(IORTPCanvasPanel.this.canvas, this)) {
/* 1072 */         return;
/*      */       }
/* 1074 */       Vector p = new Vector();
/*      */       
/* 1076 */       p.addElement(new Integer(this.row));
/*      */       
/* 1078 */       p.addElement(new Integer(this.column));
/*      */       
/* 1080 */       IOROperation operation = new IOROperation(20303, p);
/*      */       
/*      */ 
/* 1083 */       if (IORTPCanvasPanel.this.controller.solver.doWork(operation, IORTPCanvasPanel.this.controller.data)) {
/* 1084 */         setCurrent();
/* 1085 */         IORTPCanvasPanel.this.iorstate.addStep(operation);
/* 1086 */         IORTPCanvasPanel.TPCanvas.access$31(IORTPCanvasPanel.this.canvas, this);
/*      */         
/*      */ 
/* 1089 */         Vector loop = IORTPCanvasPanel.this.controller.data.loop;
/* 1090 */         if (loop.firstElement().equals(loop.lastElement())) {
/* 1091 */           IORTPCanvasPanel.this.actionPanel.setStep(3);
/*      */         }
/* 1093 */         IORTPCanvasPanel.this.actionPanel.addStep();
/* 1094 */         IORTPCanvasPanel.this.actionPanel.updatePanel();
/* 1095 */         IORTPCanvasPanel.this.actionPanel.revalidate();
/* 1096 */         IORTPCanvasPanel.this.actionPanel.repaint();
/*      */       }
/*      */       else {
/* 1099 */         System.out.println("doWork for adding nodes failed");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private boolean setEnteringBasicVariable()
/*      */     {
/* 1107 */       if ((isBasicVariable()) || (isEnteringBasicVariable())) {
/* 1108 */         return false;
/*      */       }
/* 1110 */       int option = JOptionPane.showConfirmDialog(this, "Set this cell as an entering basic variable?");
/* 1111 */       if (option != 0) {
/* 1112 */         System.err.println("Entering basic variable is not continued");
/* 1113 */         return false;
/*      */       }
/*      */       
/* 1116 */       Vector p = new Vector();
/*      */       
/* 1118 */       p.addElement(new Integer(this.row));
/*      */       
/* 1120 */       p.addElement(new Integer(this.column));
/*      */       
/* 1122 */       IOROperation operation = new IOROperation(20302, p);
/*      */       
/*      */ 
/* 1125 */       if (IORTPCanvasPanel.this.controller.solver.doWork(operation, IORTPCanvasPanel.this.controller.data)) {
/* 1126 */         setCurrent();
/* 1127 */         IORTPCanvasPanel.this.iorstate.addStep(operation);
/*      */         
/* 1129 */         if (!IORTPCanvasPanel.this.controller.data.loop.isEmpty()) {
/* 1130 */           IORTPCanvasPanel.TPCanvas.access$32(IORTPCanvasPanel.this.canvas);
/*      */         }
/*      */         
/* 1133 */         IORTPCanvasPanel.this.controller.data.loop.addElement(new Point(this.row, this.column));
/*      */         
/* 1135 */         IORTPCanvasPanel.this.actionPanel.setStep(2);
/* 1136 */         IORTPCanvasPanel.this.actionPanel.addStep();
/* 1137 */         IORTPCanvasPanel.this.actionPanel.updatePanel();
/* 1138 */         IORTPCanvasPanel.this.actionPanel.revalidate();
/* 1139 */         IORTPCanvasPanel.this.actionPanel.repaint();
/* 1140 */         return true;
/*      */       }
/*      */       
/* 1143 */       System.out.println("doWork for setting entering basic variables failed");
/* 1144 */       JOptionPane.showMessageDialog(this, IORTPCanvasPanel.this.controller.solver.getErrInfo());
/* 1145 */       return false;
/*      */     }
/*      */     
/*      */     private boolean isBasicVariable()
/*      */     {
/* 1150 */       return IORTPCanvasPanel.this.controller.data.is_basic[this.row][this.column];
/*      */     }
/*      */     
/*      */     private boolean isEnteringBasicVariable() {
/* 1154 */       return IORTPCanvasPanel.this.controller.data.is_entering_basic[this.row][this.column];
/*      */     }
/*      */     
/*      */     public int getRow() {
/* 1158 */       return this.row;
/*      */     }
/*      */     
/*      */     public int getColumn() {
/* 1162 */       return this.column;
/*      */     }
/*      */     
/*      */     private void setCurrent()
/*      */     {
/* 1167 */       if (IORTPCanvasPanel.this.current_cell != null) {
/* 1168 */         IORTPCanvasPanel.this.current_cell.repaint();
/*      */       }
/*      */       
/* 1171 */       IORTPCanvasPanel.this.current_cell = this;
/*      */       
/* 1173 */       IORTPCanvasPanel.this.current_cell.setSelected(true);
/* 1174 */       IORTPCanvasPanel.this.current_cell.requestFocus();
/*      */       
/* 1176 */       IORTPCanvasPanel.this.current_cell.repaint();
/*      */     }
/*      */     
/*      */     private void addLoopState(int state) {
/* 1180 */       Integer s = new Integer(state);
/* 1181 */       this.loop_state.addElement(s);
/* 1182 */       revalidate();
/* 1183 */       repaint();
/*      */     }
/*      */     
/*      */     private void setDirection(boolean b) {
/* 1187 */       this.is_plus = b;
/*      */     }
/*      */     
/*      */     private void setOnLoop(boolean b) {
/* 1191 */       this.is_on_loop = b;
/*      */     }
/*      */     
/*      */     private void resetLoopState() {
/* 1195 */       this.loop_state.removeAllElements();
/* 1196 */       this.is_on_loop = false;
/* 1197 */       this.is_plus = false;
/*      */       
/* 1199 */       revalidate();
/* 1200 */       repaint();
/*      */     }
/*      */     
/*      */     private boolean isCurrent() {
/* 1204 */       return IORTPCanvasPanel.this.current_cell == this;
/*      */     }
/*      */     
/*      */     private Color getSelectedColor() {
/* 1208 */       return IORTPCanvasPanel.this.select_color;
/*      */     }
/*      */     
/*      */ 
/*      */     public void paintComponent(Graphics g)
/*      */     {
/* 1214 */       super.paintComponent(g);
/*      */       
/* 1216 */       setBackground();
/*      */       
/* 1218 */       Graphics2D g2 = (Graphics2D)g;
/* 1219 */       Dimension d = getSize();
/*      */       
/*      */ 
/* 1222 */       g2.setColor(Color.blue);
/* 1223 */       String str = IORTPActionPanel.getCellString(IORTPCanvasPanel.this.controller.data.cost[this.row][this.column], IORTPCanvasPanel.this.controller.data.M_cost[this.row][this.column]);
/* 1224 */       g2.drawString(str, 2, d.height / 4);
/* 1225 */       g2.drawRect(0, 0, d.width / 2, d.height * 5 / 16);
/*      */       
/* 1227 */       this.oval_x = (d.width * 2 / 8);
/* 1228 */       this.oval_y = (d.height / 2);
/* 1229 */       this.oval_w = (d.width * 11 / 16);
/* 1230 */       this.oval_h = (d.height * 7 / 16);
/*      */       
/* 1232 */       if ((IORTPCanvasPanel.this.state == 1) && (
/* 1233 */         (IORTPCanvasPanel.this.actionPanel.getStep() == 2) || (IORTPCanvasPanel.this.actionPanel.getStep() == 3))) {
/* 1234 */         drawLoop(g2);
/*      */       }
/*      */       
/* 1237 */       drawCell(g2);
/*      */       
/* 1239 */       g2.setColor(Color.black);
/* 1240 */       g2.drawRect(0, 0, d.width, d.height);
/*      */     }
/*      */     
/*      */     private void drawCell(Graphics2D g2)
/*      */     {
/* 1245 */       Dimension d = getSize();
/*      */       
/*      */ 
/* 1248 */       if (isBasicVariable())
/*      */       {
/* 1250 */         g2.setColor(Color.yellow);
/* 1251 */         g2.fillOval(this.oval_x, this.oval_y, this.oval_w, this.oval_h);
/*      */         
/* 1253 */         g2.setColor(Color.black);
/* 1254 */         g2.drawString(IORActionPanel.trim(IORTPCanvasPanel.this.controller.data.flow[this.row][this.column]), d.width * 3 / 8, d.height * 6 / 8);
/*      */       }
/*      */       else {
/* 1257 */         switch (IORTPCanvasPanel.this.state)
/*      */         {
/*      */         case 0: 
/* 1260 */           boolean is_deleted = IORTPCanvasPanel.this.controller.data.is_deleted[this.row][this.column];
/* 1261 */           if ((IORTPCanvasPanel.this.controller.getOption() == 2) && (!is_deleted)) {
/* 1262 */             g2.setColor(Color.black);
/* 1263 */             int n0 = (int)IORTPCanvasPanel.this.controller.data.M_cuv[this.row][this.column];
/* 1264 */             int n1 = (int)IORTPCanvasPanel.this.controller.data.cuv[this.row][this.column];
/* 1265 */             String str = IORTPActionPanel.getCellString(n0, n1);
/* 1266 */             g2.drawString(str, d.width / 8, d.height * 6 / 8);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1288 */           break;
/*      */         case 1: 
/* 1271 */           if (IORTPCanvasPanel.this.controller.data.is_entering_basic[this.row][this.column] != 0)
/*      */           {
/* 1273 */             g2.setColor(Color.yellow);
/* 1274 */             g2.fillRect(d.width * 6 / 16, d.height * 8 / 16, d.width * 6 / 16, d.width * 6 / 16);
/*      */           }
/*      */           
/* 1277 */           g2.setColor(Color.black);
/* 1278 */           int n0 = (int)IORTPCanvasPanel.this.controller.data.M_cuv[this.row][this.column];
/* 1279 */           int n1 = (int)IORTPCanvasPanel.this.controller.data.cuv[this.row][this.column];
/* 1280 */           String str = IORTPActionPanel.getCellString(n0, n1);
/* 1281 */           g2.drawString(str, d.width * 2 / 8, d.height * 6 / 8);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1288 */           break;
/*      */         default: 
/* 1285 */           System.err.println("Invalid canvas state in draw russel or entering basic");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     private void setBackground()
/*      */     {
/* 1292 */       switch (IORTPCanvasPanel.this.state) {
/*      */       case 0: 
/* 1294 */         if (IORTPCanvasPanel.this.controller.data.is_deleted[this.row][this.column] == 0) {
/* 1295 */           if (isCurrent()) {
/* 1296 */             setBackground(IORTPCanvasPanel.this.select_color);
/*      */           } else {
/* 1298 */             setBackground(IORTPCanvasPanel.this.default_color);
/*      */           }
/*      */         } else {
/* 1301 */           setBackground(Color.gray);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1314 */         break;
/*      */       case 1: 
/* 1305 */         if (isCurrent()) {
/* 1306 */           setBackground(IORTPCanvasPanel.this.select_color);
/*      */         } else {
/* 1308 */           setBackground(IORTPCanvasPanel.this.default_color);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1314 */         break;
/*      */       default: 
/* 1312 */         System.err.println("Invalid canvas state in setting up the background color");
/*      */       }
/*      */       
/*      */     }
/*      */     
/*      */ 
/*      */     private void drawLoop(Graphics2D g2)
/*      */     {
/* 1320 */       if (this.is_on_loop) { String sign;
/* 1321 */         String sign; if (this.is_plus) {
/* 1322 */           sign = "+";
/*      */         } else {
/* 1324 */           sign = "-";
/*      */         }
/* 1326 */         int x = this.oval_x + this.oval_w * 3 / 4;
/* 1327 */         int y = this.oval_y * 3 / 4;
/* 1328 */         g2.setColor(Color.red);
/*      */         
/* 1330 */         Font font = g2.getFont();
/* 1331 */         g2.setFont(this.signFont);
/* 1332 */         g2.drawString(sign, x, y);
/* 1333 */         g2.setFont(font);
/*      */       }
/*      */       
/*      */ 
/* 1337 */       Stroke stroke = g2.getStroke();
/*      */       
/* 1339 */       if (!this.loop_state.isEmpty()) {
/* 1340 */         Stroke s = new BasicStroke(4.0F, 0, 1);
/* 1341 */         g2.setStroke(s);
/* 1342 */         g2.setColor(Color.yellow);
/*      */       }
/*      */       
/* 1345 */       int center_x = (2 * this.oval_x + this.oval_w) / 2;
/* 1346 */       int center_y = (2 * this.oval_y + this.oval_h) / 2;
/* 1347 */       Dimension d = getSize();
/*      */       
/* 1349 */       for (int i = 0; i < this.loop_state.size(); i++)
/*      */       {
/* 1351 */         Integer s = (Integer)this.loop_state.elementAt(i);
/* 1352 */         int st = s.intValue();
/*      */         
/* 1354 */         switch (st)
/*      */         {
/*      */         case 0: 
/* 1357 */           g2.drawLine(center_x, 0, center_x, d.height);
/* 1358 */           break;
/*      */         
/*      */         case 1: 
/* 1361 */           g2.drawLine(0, center_y, d.width, center_y);
/* 1362 */           break;
/*      */         
/*      */         case 2: 
/* 1365 */           g2.drawLine(center_x, center_y, 0, center_y);
/* 1366 */           break;
/*      */         
/*      */         case 3: 
/* 1369 */           g2.drawLine(center_x, center_y, d.width, center_y);
/* 1370 */           break;
/*      */         
/*      */         case 5: 
/* 1373 */           g2.drawLine(center_x, center_y, center_x, 0);
/* 1374 */           break;
/*      */         
/*      */         case 4: 
/* 1377 */           g2.drawLine(center_x, center_y, center_x, d.height);
/* 1378 */           break;
/*      */         
/*      */         default: 
/* 1381 */           System.err.println("ERROR IN DRAWING LOOP");
/*      */         }
/*      */         
/*      */       }
/*      */       
/* 1386 */       g2.setStroke(stroke);
/*      */     }
/*      */     
/*      */     public Vector getInnerVariables() {
/* 1390 */       Vector v = new Vector();
/* 1391 */       v.addElement(new Boolean(this.is_on_loop));
/* 1392 */       v.addElement(new Boolean(this.is_plus));
/* 1393 */       v.addElement(this.loop_state);
/* 1394 */       return v;
/*      */     }
/*      */     
/*      */     public void setInnerVariables(Vector v) {
/* 1398 */       this.is_on_loop = ((Boolean)v.elementAt(0)).booleanValue();
/* 1399 */       this.is_plus = ((Boolean)v.elementAt(1)).booleanValue();
/* 1400 */       this.loop_state = ((Vector)v.elementAt(2));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setInitFocus()
/*      */   {
/* 1409 */     switch (this.state) {
/*      */     case 0: 
/* 1411 */       this.canvas.cell[0][0].setCurrent();
/* 1412 */       this.current_cell.requestFocus();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1423 */       break;
/*      */     case 1: 
/* 1416 */       this.current_cell = null;
/* 1417 */       this.canvas.u[0].requestFocus();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1423 */       break;
/*      */     default: 
/* 1421 */       System.err.println("Invalid canvas state in requesting the initial focus");
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTPCanvasPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */