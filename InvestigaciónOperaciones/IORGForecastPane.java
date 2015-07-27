/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.AdjustmentEvent;
/*      */ import java.awt.event.AdjustmentListener;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.text.DecimalFormat;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollBar;
/*      */ import javax.swing.JTextField;
/*      */ 
/*      */ public class IORGForecastPane extends IORActionPanel implements POMTableDataNotify
/*      */ {
/*   23 */   public static int EXP_SMOOTH = 1;
/*      */   
/*      */ 
/*      */ 
/*   27 */   public static int TREND_ADJUST_SMOOTH = 2;
/*      */   
/*      */ 
/*      */ 
/*   31 */   public static int LINEAR_TREND = 3;
/*      */   
/*      */ 
/*      */ 
/*   35 */   public static int LINEAR_REGRESSION = 4;
/*      */   
/*      */ 
/*      */ 
/*   39 */   public static int LAST_VALUE = 5;
/*      */   
/*      */ 
/*      */ 
/*   43 */   public static int AVERAGING = 6;
/*      */   
/*      */ 
/*      */ 
/*   47 */   public static int MOVING_AVERAGE = 7;
/*      */   
/*   49 */   private static int MAX_ROWS = 20;
/*      */   
/*      */   private java.awt.Frame schFrame;
/*      */   
/*   53 */   private Color bkcolor = new Color(219, 219, 219);
/*   54 */   private Color headcolor = new Color(160, 180, 220);
/*   55 */   private Color[] datcolor = new Color[1];
/*      */   
/*      */   private POMTablePanel dataTable;
/*      */   
/*      */   private POMTablePanel xyTable;
/*      */   
/*      */   private JPanel dataPanel;
/*      */   
/*      */   private IORGForecastGraphCanvas gCanvas;
/*      */   
/*      */   private IORGForecastAddGraphCanvas gAddCanvas;
/*      */   
/*      */   private JButton fcastButt;
/*      */   
/*      */   private JButton resetButt;
/*      */   
/*      */   private JScrollBar alphaBar;
/*      */   
/*      */   private JScrollBar betaBar;
/*      */   private JLabel madLabel;
/*      */   private JLabel dragLabel;
/*      */   private JLabel tipLabel;
/*      */   private JLabel mseLabel;
/*      */   private JLabel alphaLabel;
/*      */   private JLabel betaLabel;
/*      */   private JLabel aLabel;
/*      */   private JLabel bLabel;
/*      */   private JLabel equtLabel;
/*      */   private JLabel x0Label;
/*      */   private JLabel t1Label;
/*      */   private JTextField x0TextField;
/*      */   private JTextField t1TextField;
/*   87 */   private float currX0 = 40.0F; private float currT1 = 0.0F;
/*      */   
/*      */   public JComboBox methodComb;
/*      */   
/*   91 */   private int lastLooknum = 4;
/*      */   private JComboBox lastnumList;
/*   93 */   private JLabel lastavgLabel = new JLabel("Fetch average of last ");
/*   94 */   private JLabel vaLabel = new JLabel("values");
/*      */   
/*   96 */   private boolean canDo = false;
/*      */   private float[] actualDat;
/*      */   private float[] forecastDat;
/*      */   private float[] errDat;
/*      */   private float[] intmS;
/*  101 */   private float[] intmT; private float[] predX; private float aincept; private float bincept; private float coef_x; private float coef_y; private float currAlpha = 0.4F; private float currBeta = 0.5F;
/*  102 */   private float errMAD; private float errMFE; private DecimalFormat decfm = new DecimalFormat();
/*      */   
/*      */ 
/*  105 */   private GridBagLayout dg = new GridBagLayout();
/*  106 */   private GridBagConstraints dbc = new GridBagConstraints();
/*      */   
/*      */ 
/*      */   private IORGraphicalProcessController controller;
/*      */   
/*      */ 
/*      */   public IORGForecastPane(IORGraphicalProcessController c)
/*      */   {
/*  114 */     super(c);
/*  115 */     this.controller = c;
/*  116 */     setLayout(new BorderLayout(20, 10));
/*  117 */     this.decfm.setGroupingUsed(false);
/*  118 */     this.decfm.setMaximumFractionDigits(2);
/*  119 */     init();
/*      */   }
/*      */   
/*      */ 
/*      */   public void updatePanel() {}
/*      */   
/*      */ 
/*      */   protected void backStep() {}
/*      */   
/*      */ 
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/*  131 */     String str = "Enter or Revise a Forecast Problem\n\n";
/*  132 */     str = String.valueOf(String.valueOf(str)).concat("This module implements four widely used forecasting techniques,");
/*  133 */     str = String.valueOf(String.valueOf(str)).concat("Exponential Smoothing, Trend Adjusted Exponential Smoothing, ");
/*  134 */     str = String.valueOf(String.valueOf(str)).concat("Linear Trend Equation, and Simple Linear Regression. Users ");
/*  135 */     str = String.valueOf(String.valueOf(str)).concat("can employ one out of the four techniques on different problems, or");
/*  136 */     str = String.valueOf(String.valueOf(str)).concat("apply different techniques on the same problem to study various aspects ");
/*  137 */     str = String.valueOf(String.valueOf(str)).concat("of the problem. ");
/*      */     
/*      */ 
/*  140 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/*  142 */     this.help_content_step.setText(str);
/*  143 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/*  148 */     String str = "Enter or Revise a Forecast Problem\n\n";
/*      */     
/*  150 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*  151 */     this.help_content_procedure.setText(str);
/*  152 */     this.help_content_procedure.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void finishProcedure() {}
/*      */   
/*      */ 
/*      */   public void LoadFile(ObjectInputStream in)
/*      */   {
/*  162 */     String[][] tableData = new String[MAX_ROWS][4];
/*      */     try
/*      */     {
/*  165 */       tableData = (String[][])in.readObject();
/*  166 */       int forecastingNum = in.readInt();
/*  167 */       this.methodComb.setSelectedIndex(forecastingNum);
/*      */       
/*  169 */       this.aincept = in.readFloat();
/*  170 */       this.bincept = in.readFloat();
/*  171 */       this.coef_x = in.readFloat();
/*  172 */       this.coef_y = in.readFloat();
/*  173 */       this.currAlpha = in.readFloat();
/*  174 */       this.currBeta = in.readFloat();
/*  175 */       this.errMAD = in.readFloat();
/*  176 */       this.errMFE = in.readFloat();
/*  177 */       this.lastLooknum = in.readInt();
/*      */       
/*      */ 
/*  180 */       this.alphaBar.setValue((int)(this.currAlpha * 10));
/*  181 */       this.betaBar.setValue((int)(this.currBeta * 10));
/*  182 */       recalcForeData();
/*  183 */       this.gCanvas.repaint();
/*  184 */       this.lastnumList.setSelectedItem("".concat(String.valueOf(String.valueOf(this.lastnumList))));
/*      */       
/*  186 */       for (int i = 0; i < MAX_ROWS; i++) {
/*  187 */         for (int j = 0; j < 4; j++) {
/*  188 */           if (this.methodComb.getSelectedIndex() != 3) {
/*  189 */             this.dataTable.updateData(tableData[i][j], i, j);
/*  190 */             this.dataTable.repaint();
/*      */           } else {
/*  192 */             this.xyTable.updateData(tableData[i][j], i, j);
/*  193 */             this.xyTable.repaint();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  198 */       doForecast();
/*      */     }
/*      */     catch (Exception e) {
/*  201 */       System.out.println("LOAD Fails");
/*      */     }
/*      */     
/*  204 */     repaint();
/*      */   }
/*      */   
/*      */   public void savePrintInfo()
/*      */   {
/*  209 */     String[][] tableData = new String[MAX_ROWS][4];
/*      */     
/*  211 */     for (int i = 0; i < MAX_ROWS; i++) {
/*  212 */       for (int j = 0; j < 4; j++) {
/*  213 */         if (this.methodComb.getSelectedIndex() != 3) {
/*  214 */           tableData[i][j] = this.dataTable.getData(i, j);
/*      */         } else {
/*  216 */           tableData[i][j] = this.xyTable.getData(i, j);
/*      */         }
/*      */       }
/*      */     }
/*  220 */     this.controller.data.forecastingNum = this.methodComb.getSelectedIndex();
/*  221 */     this.controller.data.tableData = tableData;
/*  222 */     this.controller.data.aincept = this.aincept;
/*  223 */     this.controller.data.bincept = this.bincept;
/*  224 */     this.controller.data.coef_x = this.coef_x;
/*  225 */     this.controller.data.coef_y = this.coef_y;
/*  226 */     this.controller.data.currAlpha = this.currAlpha;
/*  227 */     this.controller.data.currBeta = this.currBeta;
/*  228 */     this.controller.data.errMAD = this.errMAD;
/*  229 */     this.controller.data.errMFE = this.errMFE;
/*  230 */     this.controller.data.lastLooknum = this.lastLooknum;
/*  231 */     this.controller.solver.InitializeForecast(this.controller.data);
/*      */   }
/*      */   
/*      */   public void SaveFile(ObjectOutputStream out) {
/*  235 */     int forecastingNum = this.methodComb.getSelectedIndex();
/*  236 */     String[][] tableData = new String[MAX_ROWS][4];
/*      */     
/*  238 */     for (int i = 0; i < MAX_ROWS; i++) {
/*  239 */       for (int j = 0; j < 4; j++) {
/*  240 */         if (this.methodComb.getSelectedIndex() != 3) {
/*  241 */           tableData[i][j] = this.dataTable.getData(i, j);
/*      */         } else {
/*  243 */           tableData[i][j] = this.xyTable.getData(i, j);
/*      */         }
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/*  249 */       out.writeObject(tableData);
/*  250 */       out.writeInt(forecastingNum);
/*      */       
/*  252 */       out.writeFloat(this.aincept);
/*  253 */       out.writeFloat(this.bincept);
/*  254 */       out.writeFloat(this.coef_x);
/*  255 */       out.writeFloat(this.coef_y);
/*  256 */       out.writeFloat(this.currAlpha);
/*  257 */       out.writeFloat(this.currBeta);
/*  258 */       out.writeFloat(this.errMAD);
/*  259 */       out.writeFloat(this.errMFE);
/*  260 */       out.writeInt(this.lastLooknum);
/*      */     }
/*      */     catch (Exception localException) {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void init()
/*      */   {
/*      */     try
/*      */     {
/*  273 */       jbInit();
/*  274 */       doForecast();
/*  275 */       validate();
/*  276 */       repaint();
/*      */     } catch (Exception e) {
/*  278 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void jbInit()
/*      */     throws Exception
/*      */   {
/*  287 */     this.datcolor[0] = this.bkcolor;
/*      */     
/*  289 */     this.actualDat = new float[MAX_ROWS];
/*  290 */     this.forecastDat = new float[MAX_ROWS];
/*  291 */     this.errDat = new float[MAX_ROWS];
/*  292 */     this.intmS = new float[MAX_ROWS];
/*  293 */     this.intmT = new float[MAX_ROWS];
/*  294 */     this.predX = new float[MAX_ROWS];
/*      */     
/*  296 */     for (int i = 0; i < MAX_ROWS; i++) {
/*  297 */       this.actualDat[i] = 0.0F;
/*  298 */       this.forecastDat[i] = 0.0F;
/*  299 */       this.errDat[i] = 0.0F;
/*  300 */       this.intmS[i] = 0.0F;
/*  301 */       this.intmT[i] = 0.0F;
/*  302 */       this.predX[i] = 0.0F;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  307 */     this.madLabel = new JLabel("        MAD = ");
/*  308 */     this.mseLabel = new JLabel("MSE = ");
/*      */     
/*  310 */     this.dataTable = new POMTablePanel(this, MAX_ROWS, 4, 0);
/*  311 */     this.xyTable = new POMTablePanel(this, MAX_ROWS, 4, 0);
/*      */     
/*  313 */     buildDataPanel();
/*      */     
/*  315 */     this.tipLabel = new JLabel("    You can edit the values in the table");
/*  316 */     this.dragLabel = new JLabel("Drag the blue dots with mouse to see what happens.");
/*  317 */     this.tipLabel.setForeground(Color.blue);
/*  318 */     this.dragLabel.setForeground(Color.blue);
/*  319 */     this.gCanvas = new IORGForecastGraphCanvas(this);
/*  320 */     this.gAddCanvas = new IORGForecastAddGraphCanvas(this);
/*  321 */     this.gCanvas.setSize(340, 340);
/*      */     
/*      */ 
/*  324 */     this.methodComb = new JComboBox();
/*      */     
/*  326 */     this.methodComb.addItem("Exponential Smoothing");
/*  327 */     this.methodComb.addItem("Trend Adjusted Exponential Smoothing");
/*  328 */     this.methodComb.addItem("Linear Trend Equation");
/*  329 */     this.methodComb.addItem("Simple Linear Regression");
/*  330 */     this.methodComb.addItem("Last Value");
/*  331 */     this.methodComb.addItem("Averaging");
/*  332 */     this.methodComb.addItem("Moving Average");
/*      */     
/*  334 */     this.lastnumList = new JComboBox();
/*      */     
/*  336 */     this.lastnumList.addItem("2");
/*  337 */     this.lastnumList.addItem("3");
/*  338 */     this.lastnumList.addItem("4");
/*  339 */     this.lastnumList.addItem("5");
/*  340 */     this.lastnumList.addItem("6");
/*  341 */     this.lastnumList.setSelectedIndex(0);
/*  342 */     this.lastLooknum = (this.lastnumList.getSelectedIndex() + 2);
/*      */     
/*  344 */     this.lastnumList.addItemListener(new java.awt.event.ItemListener() {
/*      */       public void itemStateChanged(ItemEvent e) {
/*  346 */         if (e.getStateChange() == 1) {
/*  347 */           IORGForecastPane.this.lastLooknum = (IORGForecastPane.this.lastnumList.getSelectedIndex() + 2);
/*  348 */           if (IORGForecastPane.this.canDo) {
/*  349 */             IORGForecastPane.this.recalcForeData();
/*  350 */             IORGForecastPane.this.resetProb();
/*  351 */             IORGForecastPane.this.savePrintInfo();
/*  352 */             IORGForecastPane.this.gCanvas.repaint();
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*  358 */     });
/*  359 */     this.fcastButt = new JButton("Forecast");
/*  360 */     this.fcastButt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  362 */         IORGForecastPane.this.x0TextField.postActionEvent();
/*  363 */         IORGForecastPane.this.t1TextField.postActionEvent();
/*  364 */         IORGForecastPane.this.doForecast();
/*  365 */         IORGForecastPane.this.savePrintInfo();
/*      */       }
/*      */       
/*  368 */     });
/*  369 */     this.resetButt = new JButton("Reset");
/*  370 */     this.resetButt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  372 */         IORGForecastPane.this.lastnumList.setSelectedIndex(0);
/*  373 */         IORGForecastPane.this.lastLooknum = (IORGForecastPane.this.lastnumList.getSelectedIndex() + 2);
/*  374 */         IORGForecastPane.this.resetProb();
/*  375 */         IORGForecastPane.this.savePrintInfo();
/*      */       }
/*      */       
/*  378 */     });
/*  379 */     this.x0Label = new JLabel("x0 = ".concat(String.valueOf(String.valueOf(this.currX0))));
/*  380 */     this.t1Label = new JLabel("T1 = ".concat(String.valueOf(String.valueOf(this.currT1))));
/*  381 */     this.x0TextField = new JTextField("".concat(String.valueOf(String.valueOf(this.currX0))), 10);
/*  382 */     this.t1TextField = new JTextField("".concat(String.valueOf(String.valueOf(this.currT1))), 10);
/*      */     
/*  384 */     this.x0TextField.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent e) {
/*      */         try {
/*  388 */           if ((Float.parseFloat(IORGForecastPane.this.x0TextField.getText()) >= 0) && (Float.parseFloat(IORGForecastPane.this.x0TextField.getText()) < 100000)) {
/*  389 */             IORGForecastPane.this.currX0 = Float.parseFloat(IORGForecastPane.this.x0TextField.getText());
/*  390 */             if (IORGForecastPane.this.methodComb.getSelectedIndex() == 1) {
/*  391 */               IORGForecastPane.this.x0Label.setText("x0 = ".concat(String.valueOf(String.valueOf(IORGForecastPane.this.currX0))));
/*  392 */             } else if (IORGForecastPane.this.methodComb.getSelectedIndex() == 0)
/*  393 */               IORGForecastPane.this.x0Label.setText("init = ".concat(String.valueOf(String.valueOf(IORGForecastPane.this.currX0))));
/*      */           } else {
/*  395 */             IORGForecastPane.this.x0TextField.setText("".concat(String.valueOf(String.valueOf(IORGForecastPane.this.currX0))));
/*      */           }
/*  397 */         } catch (NumberFormatException ee) { IORGForecastPane.this.x0TextField.setText("".concat(String.valueOf(String.valueOf(IORGForecastPane.this.currX0))));
/*      */         }
/*      */         
/*      */       }
/*      */       
/*  402 */     });
/*  403 */     this.t1TextField.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*      */         try {
/*  406 */           if ((Float.parseFloat(IORGForecastPane.this.t1TextField.getText()) >= 0) && (Float.parseFloat(IORGForecastPane.this.t1TextField.getText()) < 100000)) {
/*  407 */             IORGForecastPane.this.currT1 = Float.parseFloat(IORGForecastPane.this.t1TextField.getText());
/*  408 */             IORGForecastPane.this.t1Label.setText("T1 = ".concat(String.valueOf(String.valueOf(IORGForecastPane.this.currT1))));
/*      */           } else {
/*  410 */             IORGForecastPane.this.t1TextField.setText("".concat(String.valueOf(String.valueOf(IORGForecastPane.this.currT1))));
/*      */           }
/*  412 */         } catch (NumberFormatException ee) { IORGForecastPane.this.t1TextField.setText("".concat(String.valueOf(String.valueOf(IORGForecastPane.this.currT1))));
/*      */         }
/*      */         
/*      */       }
/*  416 */     });
/*  417 */     this.alphaLabel = new JLabel("alpha = ".concat(String.valueOf(String.valueOf(this.currAlpha))));
/*  418 */     this.alphaBar = new JScrollBar(0, 4, 1, 1, 10);
/*  419 */     this.alphaBar.setBlockIncrement(1);
/*  420 */     this.alphaBar.addAdjustmentListener(new AdjustmentListener() {
/*      */       public void adjustmentValueChanged(AdjustmentEvent e) {
/*  422 */         IORGForecastPane.this.currAlpha = ((float)(1.0D * IORGForecastPane.this.alphaBar.getValue() / 10));
/*  423 */         IORGForecastPane.this.alphaLabel.setText("alpha = ".concat(String.valueOf(String.valueOf(IORGForecastPane.this.currAlpha))));
/*  424 */         if (IORGForecastPane.this.canDo) {
/*  425 */           IORGForecastPane.this.recalcForeData();
/*  426 */           IORGForecastPane.this.gCanvas.repaint();
/*  427 */           IORGForecastPane.this.savePrintInfo();
/*      */         }
/*      */         
/*      */       }
/*      */       
/*  432 */     });
/*  433 */     addPublicPanel();
/*      */     
/*      */ 
/*  436 */     this.alphaLabel.setBounds(130, 450, 80, 18);
/*  437 */     this.alphaBar.setBounds(210, 450, 100, 18);
/*  438 */     this.x0Label.setBounds(350, 450, 80, 18);
/*  439 */     this.x0TextField.setBounds(450, 450, 80, 18);
/*  440 */     this.x0Label.setText("init = ".concat(String.valueOf(String.valueOf(this.currX0))));
/*      */     
/*      */ 
/*  443 */     this.dataPanel.add(this.alphaLabel);
/*  444 */     this.dataPanel.add(this.alphaBar);
/*  445 */     this.dataPanel.add(this.x0Label);
/*  446 */     this.dataPanel.add(this.x0TextField);
/*      */     
/*      */ 
/*  449 */     this.betaLabel = new JLabel("beta = ".concat(String.valueOf(String.valueOf(this.currBeta))));
/*  450 */     this.betaBar = new JScrollBar(0, 5, 1, 1, 10);
/*  451 */     this.betaBar.setBlockIncrement(1);
/*  452 */     this.betaBar.addAdjustmentListener(new AdjustmentListener() {
/*      */       public void adjustmentValueChanged(AdjustmentEvent e) {
/*  454 */         IORGForecastPane.this.currBeta = ((float)(1.0D * IORGForecastPane.this.betaBar.getValue() / 10));
/*  455 */         IORGForecastPane.this.betaLabel.setText("beta = ".concat(String.valueOf(String.valueOf(IORGForecastPane.this.currBeta))));
/*  456 */         if (IORGForecastPane.this.canDo) {
/*  457 */           IORGForecastPane.this.recalcForeData();
/*  458 */           IORGForecastPane.this.gCanvas.repaint();
/*  459 */           IORGForecastPane.this.savePrintInfo();
/*      */         }
/*      */         
/*      */       }
/*  463 */     });
/*  464 */     this.aLabel = new JLabel(" a = ");
/*  465 */     this.bLabel = new JLabel(" b = ");
/*  466 */     this.equtLabel = new JLabel("  ");
/*      */     
/*  468 */     this.methodComb.addItemListener(new java.awt.event.ItemListener() {
/*      */       public void itemStateChanged(ItemEvent e) {
/*  470 */         if (e.getStateChange() == 1) {
/*  471 */           IORGForecastPane.this.changeMethod();
/*  472 */           int sel = IORGForecastPane.this.methodComb.getSelectedIndex();
/*  473 */           if (sel == 0) {
/*  474 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.aLabel);
/*  475 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.bLabel);
/*  476 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.equtLabel);
/*      */             
/*  478 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.betaLabel);
/*  479 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.betaBar);
/*  480 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.xyTable);
/*      */             
/*  482 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.lastavgLabel);
/*  483 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.vaLabel);
/*  484 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.lastnumList);
/*      */             
/*      */ 
/*      */ 
/*  488 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.t1Label);
/*  489 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.t1TextField);
/*      */             
/*  491 */             IORGForecastPane.this.x0Label.setText("init = ".concat(String.valueOf(String.valueOf(IORGForecastPane.this.currX0))));
/*      */             
/*      */ 
/*  494 */             IORGForecastPane.this.dataTable.setBounds(335, 25, 291, 350);
/*  495 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.dataTable);
/*  496 */             IORGForecastPane.this.alphaLabel.setBounds(130, 450, 80, 18);
/*  497 */             IORGForecastPane.this.alphaBar.setBounds(210, 450, 100, 18);
/*  498 */             IORGForecastPane.this.x0Label.setBounds(350, 450, 80, 18);
/*  499 */             IORGForecastPane.this.x0TextField.setBounds(450, 450, 80, 18);
/*      */             
/*  501 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.gAddCanvas);
/*  502 */             IORGForecastPane.this.gCanvas.setBounds(10, 35, 340, 340);
/*  503 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.gCanvas);
/*      */             
/*  505 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.alphaLabel);
/*  506 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.alphaBar);
/*      */             
/*  508 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.x0Label);
/*  509 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.x0TextField);
/*      */             
/*      */ 
/*  512 */             IORGForecastPane.this.dataPanel.validate();
/*  513 */             IORGForecastPane.this.dataPanel.repaint();
/*  514 */           } else if (sel == 1) {
/*  515 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.aLabel);
/*  516 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.bLabel);
/*  517 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.equtLabel);
/*      */             
/*  519 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.lastavgLabel);
/*  520 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.vaLabel);
/*  521 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.lastnumList);
/*      */             
/*  523 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.xyTable);
/*      */             
/*  525 */             IORGForecastPane.this.dataTable.setBounds(335, 25, 291, 350);
/*  526 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.dataTable);
/*      */             
/*  528 */             IORGForecastPane.this.alphaLabel.setBounds(80, 420, 80, 18);
/*  529 */             IORGForecastPane.this.alphaBar.setBounds(160, 420, 100, 18);
/*      */             
/*  531 */             IORGForecastPane.this.betaLabel.setBounds(80, 460, 80, 18);
/*  532 */             IORGForecastPane.this.betaBar.setBounds(160, 460, 100, 18);
/*      */             
/*  534 */             IORGForecastPane.this.x0Label.setBounds(400, 420, 80, 18);
/*  535 */             IORGForecastPane.this.x0TextField.setBounds(490, 420, 80, 18);
/*  536 */             IORGForecastPane.this.x0Label.setText("x0 = ".concat(String.valueOf(String.valueOf(IORGForecastPane.this.currX0))));
/*      */             
/*      */ 
/*  539 */             IORGForecastPane.this.t1Label.setBounds(400, 460, 80, 18);
/*  540 */             IORGForecastPane.this.t1TextField.setBounds(490, 460, 80, 18);
/*      */             
/*  542 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.gAddCanvas);
/*  543 */             IORGForecastPane.this.gCanvas.setBounds(10, 35, 340, 340);
/*  544 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.gCanvas);
/*      */             
/*      */ 
/*  547 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.alphaLabel);
/*  548 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.alphaBar);
/*      */             
/*  550 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.betaLabel);
/*  551 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.betaBar);
/*      */             
/*  553 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.x0Label);
/*  554 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.x0TextField);
/*      */             
/*  556 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.t1Label);
/*  557 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.t1TextField);
/*      */             
/*  559 */             IORGForecastPane.this.dataPanel.validate();
/*  560 */             IORGForecastPane.this.dataPanel.repaint();
/*      */ 
/*      */           }
/*  563 */           else if (sel == 2) {
/*  564 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.betaLabel);
/*  565 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.betaBar);
/*      */             
/*  567 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.alphaLabel);
/*  568 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.alphaBar);
/*  569 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.xyTable);
/*      */             
/*  571 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.lastavgLabel);
/*  572 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.vaLabel);
/*  573 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.lastnumList);
/*      */             
/*  575 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.x0Label);
/*  576 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.x0TextField);
/*  577 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.t1Label);
/*  578 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.t1TextField);
/*      */             
/*  580 */             IORGForecastPane.this.dataTable.setBounds(335, 25, 291, 350);
/*  581 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.dataTable);
/*      */             
/*  583 */             IORGForecastPane.this.aLabel.setBounds(30, 380, 70, 23);
/*  584 */             IORGForecastPane.this.bLabel.setBounds(105, 380, 70, 23);
/*  585 */             IORGForecastPane.this.equtLabel.setBounds(180, 380, 180, 23);
/*      */             
/*  587 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.gAddCanvas);
/*  588 */             IORGForecastPane.this.gCanvas.setBounds(10, 35, 340, 340);
/*  589 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.gCanvas);
/*      */             
/*      */ 
/*  592 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.aLabel);
/*  593 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.bLabel);
/*  594 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.equtLabel);
/*      */             
/*  596 */             IORGForecastPane.this.dataPanel.validate();
/*  597 */             IORGForecastPane.this.dataPanel.repaint();
/*  598 */           } else if (sel == 3) {
/*  599 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.betaLabel);
/*  600 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.betaBar);
/*      */             
/*  602 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.alphaLabel);
/*  603 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.alphaBar);
/*  604 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.dataTable);
/*      */             
/*  606 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.lastavgLabel);
/*  607 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.vaLabel);
/*  608 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.lastnumList);
/*      */             
/*  610 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.x0Label);
/*  611 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.x0TextField);
/*  612 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.t1Label);
/*  613 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.t1TextField);
/*      */             
/*  615 */             IORGForecastPane.this.xyTable.setBounds(335, 25, 291, 350);
/*  616 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.xyTable);
/*      */             
/*  618 */             IORGForecastPane.this.aLabel.setBounds(30, 380, 70, 23);
/*  619 */             IORGForecastPane.this.bLabel.setBounds(105, 380, 70, 23);
/*  620 */             IORGForecastPane.this.equtLabel.setBounds(180, 380, 180, 23);
/*      */             
/*  622 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.gAddCanvas);
/*  623 */             IORGForecastPane.this.gCanvas.setBounds(10, 35, 340, 340);
/*  624 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.gCanvas);
/*      */             
/*  626 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.aLabel);
/*  627 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.bLabel);
/*  628 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.equtLabel);
/*      */             
/*  630 */             IORGForecastPane.this.dataPanel.validate();
/*  631 */             IORGForecastPane.this.dataPanel.repaint();
/*  632 */           } else if (sel == 4) {
/*  633 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.aLabel);
/*  634 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.bLabel);
/*  635 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.equtLabel);
/*      */             
/*  637 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.betaLabel);
/*  638 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.betaBar);
/*  639 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.xyTable);
/*      */             
/*  641 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.lastavgLabel);
/*  642 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.vaLabel);
/*  643 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.lastnumList);
/*      */             
/*  645 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.alphaLabel);
/*  646 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.alphaBar);
/*      */             
/*  648 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.x0Label);
/*  649 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.x0TextField);
/*  650 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.t1Label);
/*  651 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.t1TextField);
/*      */             
/*  653 */             IORGForecastPane.this.dataTable.setBounds(335, 25, 291, 350);
/*  654 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.dataTable);
/*      */             
/*      */ 
/*  657 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.gCanvas);
/*  658 */             IORGForecastPane.this.gAddCanvas.setBounds(10, 35, 340, 340);
/*  659 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.gAddCanvas);
/*      */             
/*  661 */             IORGForecastPane.this.dataPanel.validate();
/*  662 */             IORGForecastPane.this.dataPanel.repaint();
/*      */           }
/*  664 */           else if (sel == 5) {
/*  665 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.aLabel);
/*  666 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.bLabel);
/*  667 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.equtLabel);
/*      */             
/*  669 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.betaLabel);
/*  670 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.betaBar);
/*  671 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.xyTable);
/*      */             
/*  673 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.lastavgLabel);
/*  674 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.vaLabel);
/*  675 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.lastnumList);
/*      */             
/*      */ 
/*  678 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.alphaLabel);
/*  679 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.alphaBar);
/*      */             
/*  681 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.x0Label);
/*  682 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.x0TextField);
/*  683 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.t1Label);
/*  684 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.t1TextField);
/*      */             
/*  686 */             IORGForecastPane.this.dataTable.setBounds(335, 25, 291, 350);
/*  687 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.dataTable);
/*      */             
/*      */ 
/*  690 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.gCanvas);
/*  691 */             IORGForecastPane.this.gAddCanvas.setBounds(10, 35, 340, 340);
/*  692 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.gAddCanvas);
/*      */             
/*  694 */             IORGForecastPane.this.dataPanel.validate();
/*  695 */             IORGForecastPane.this.dataPanel.repaint();
/*  696 */           } else if (sel == 6) {
/*  697 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.aLabel);
/*  698 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.bLabel);
/*  699 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.equtLabel);
/*      */             
/*  701 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.betaLabel);
/*  702 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.betaBar);
/*  703 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.xyTable);
/*      */             
/*  705 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.alphaLabel);
/*  706 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.alphaBar);
/*      */             
/*  708 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.x0Label);
/*  709 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.x0TextField);
/*  710 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.t1Label);
/*  711 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.t1TextField);
/*      */             
/*  713 */             IORGForecastPane.this.dataTable.setBounds(335, 25, 291, 350);
/*  714 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.dataTable);
/*      */             
/*      */ 
/*  717 */             IORGForecastPane.this.dataPanel.remove(IORGForecastPane.this.gCanvas);
/*  718 */             IORGForecastPane.this.gAddCanvas.setBounds(10, 35, 340, 340);
/*  719 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.gAddCanvas);
/*      */             
/*  721 */             IORGForecastPane.this.lastavgLabel.setBounds(140, 450, 180, 18);
/*  722 */             IORGForecastPane.this.lastnumList.setBounds(300, 450, 100, 18);
/*  723 */             IORGForecastPane.this.vaLabel.setBounds(420, 450, 100, 18);
/*  724 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.lastavgLabel);
/*  725 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.vaLabel);
/*  726 */             IORGForecastPane.this.dataPanel.add(IORGForecastPane.this.lastnumList);
/*      */             
/*  728 */             IORGForecastPane.this.dataPanel.validate();
/*  729 */             IORGForecastPane.this.dataPanel.repaint();
/*      */           }
/*      */         }
/*      */         
/*  733 */         IORGForecastPane.this.doForecast();
/*  734 */         IORGForecastPane.this.savePrintInfo();
/*  735 */         IORGForecastPane.this.dataPanel.repaint();
/*      */       }
/*      */       
/*      */ 
/*  739 */     });
/*  740 */     initProb();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  747 */     JPanel contentPane = new JPanel();
/*  748 */     contentPane.setLayout(new BorderLayout());
/*  749 */     contentPane.add(this.dataPanel, "Center");
/*      */     
/*      */ 
/*  752 */     setLayout(new BorderLayout());
/*  753 */     add(contentPane, "Center");
/*  754 */     setVisible(true);
/*  755 */     doForecast();
/*  756 */     savePrintInfo();
/*      */   }
/*      */   
/*      */   public void addPublicPanel() {
/*  760 */     this.tipLabel.setBounds(350, 10, 251, 20);
/*  761 */     this.dataTable.setBounds(335, 25, 291, 350);
/*      */     
/*  763 */     this.dragLabel.setBounds(10, 10, 320, 20);
/*  764 */     this.gCanvas.setBounds(10, 35, 340, 340);
/*      */     
/*      */ 
/*  767 */     this.fcastButt.setBounds(180, 510, 95, 23);
/*  768 */     this.resetButt.setBounds(330, 510, 95, 23);
/*      */     
/*      */ 
/*      */ 
/*  772 */     this.madLabel.setBounds(360, 380, 120, 20);
/*  773 */     this.mseLabel.setBounds(510, 380, 120, 20);
/*      */     
/*  775 */     this.dataPanel = new JPanel();
/*  776 */     this.dataPanel.setLayout(null);
/*  777 */     this.dataPanel.add(this.dataTable);
/*  778 */     this.dataPanel.add(this.tipLabel);
/*  779 */     this.dataPanel.add(this.dragLabel);
/*  780 */     this.dataPanel.add(this.gCanvas);
/*      */     
/*  782 */     this.dataPanel.add(this.fcastButt);
/*  783 */     this.dataPanel.add(this.resetButt);
/*      */     
/*  785 */     this.dataPanel.add(this.methodComb);
/*      */     
/*  787 */     this.dataPanel.add(this.madLabel);
/*  788 */     this.dataPanel.add(this.mseLabel);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void buildDataPanel()
/*      */   {
/*      */     try
/*      */     {
/*  798 */       String[] tabHeader = new String[4];
/*  799 */       tabHeader[0] = "Period";
/*  800 */       tabHeader[1] = " Data ";
/*  801 */       tabHeader[2] = "Forecast";
/*  802 */       tabHeader[3] = "Error";
/*  803 */       this.dataTable.setHeader(tabHeader);
/*      */       
/*  805 */       String[] xyHeader = new String[4];
/*  806 */       xyHeader[0] = "  x  ";
/*  807 */       xyHeader[1] = "   y   ";
/*  808 */       xyHeader[2] = "Forecast";
/*  809 */       xyHeader[3] = "Error";
/*  810 */       this.xyTable.setHeader(xyHeader);
/*      */       
/*  812 */       String[] tabData = new String[4 * MAX_ROWS];
/*  813 */       String[] xyData = new String[4 * MAX_ROWS];
/*  814 */       for (int i = 0; i < MAX_ROWS; i++) {
/*  815 */         tabData[(i * 4 + 0)] = String.valueOf(String.valueOf(new StringBuffer("").append(i + 1)));
/*  816 */         tabData[(i * 4 + 1)] = "0";
/*  817 */         tabData[(i * 4 + 2)] = " ";
/*  818 */         tabData[(i * 4 + 3)] = " ";
/*      */         
/*  820 */         xyData[(i * 4 + 0)] = "0";
/*  821 */         xyData[(i * 4 + 1)] = "0";
/*  822 */         xyData[(i * 4 + 2)] = " ";
/*  823 */         xyData[(i * 4 + 3)] = " ";
/*      */       }
/*      */       
/*  826 */       this.dataTable.setData(tabData);
/*  827 */       this.xyTable.setData(xyData);
/*      */       
/*  829 */       this.dataTable.setGap(10, 0, 30, 0);
/*  830 */       this.dataTable.setForeground(Color.black, Color.black);
/*  831 */       this.dataTable.setBackground(getBackground(), this.headcolor, this.datcolor);
/*      */       
/*  833 */       this.xyTable.setGap(10, 0, 30, 0);
/*  834 */       this.xyTable.setForeground(Color.black, Color.black);
/*  835 */       this.xyTable.setBackground(getBackground(), this.headcolor, this.datcolor);
/*      */       
/*  837 */       for (i = 0; i < MAX_ROWS; i++) {
/*  838 */         this.dataTable.setDataBackgroundState(i, 0, this.headcolor);
/*  839 */         this.dataTable.setDataBackgroundState(i, 1, Color.white);
/*  840 */         this.dataTable.setDataBackgroundState(i, 2, this.bkcolor);
/*  841 */         this.dataTable.setDataBackgroundState(i, 3, this.bkcolor);
/*      */         
/*  843 */         this.xyTable.setDataBackgroundState(i, 2, this.bkcolor);
/*  844 */         this.xyTable.setDataBackgroundState(i, 3, this.bkcolor);
/*  845 */         this.xyTable.setDataBackgroundState(i, 0, Color.white);
/*  846 */         this.xyTable.setDataBackgroundState(i, 1, Color.white);
/*      */         
/*  848 */         this.dataTable.setDataInputState(i, 1, true, 1);
/*  849 */         this.xyTable.setDataInputState(i, 0, true, 1);
/*  850 */         this.xyTable.setDataInputState(i, 1, true, 1);
/*      */       }
/*      */       
/*  853 */       this.dataTable.InitEnd();
/*  854 */       this.xyTable.InitEnd();
/*      */     }
/*      */     catch (Exception e) {
/*  857 */       e.printStackTrace();
/*  858 */       System.out.println(e);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void changeMethod()
/*      */   {
/*  867 */     this.canDo = false;
/*      */     
/*  869 */     this.currT1 = 0.0F;
/*  870 */     this.currX0 = 40.0F;
/*      */     
/*  872 */     this.x0TextField.setText("".concat(String.valueOf(String.valueOf(this.currX0))));
/*  873 */     this.t1TextField.setText("".concat(String.valueOf(String.valueOf(this.currT1))));
/*      */     
/*  875 */     this.dataTable.leave();
/*  876 */     this.xyTable.leave();
/*  877 */     for (int i = 0; i < MAX_ROWS; i++) {
/*  878 */       this.forecastDat[i] = 0.0F;
/*  879 */       this.errDat[i] = 0.0F;
/*  880 */       this.intmS[i] = 0.0F;
/*  881 */       this.intmT[i] = 0.0F;
/*      */       
/*  883 */       this.dataTable.updateData(String.valueOf(String.valueOf(new StringBuffer("").append(i + 1))), i, 0);
/*  884 */       this.dataTable.updateData(" ", i, 2);
/*  885 */       this.dataTable.updateData(" ", i, 3);
/*      */       
/*  887 */       this.xyTable.updateData(" ", i, 2);
/*  888 */       this.xyTable.updateData(" ", i, 3);
/*      */     }
/*      */     
/*  891 */     this.dataTable.repaint();
/*  892 */     this.xyTable.repaint();
/*      */     
/*  894 */     this.currAlpha = 0.4F;
/*  895 */     this.alphaBar.setValue(4);
/*  896 */     this.alphaLabel.setText("alpha = ".concat(String.valueOf(String.valueOf(this.currAlpha))));
/*  897 */     this.currBeta = 0.5F;
/*  898 */     this.betaBar.setValue(5);
/*  899 */     this.betaLabel.setText("beta = ".concat(String.valueOf(String.valueOf(this.currBeta))));
/*      */     
/*  901 */     this.aLabel.setText(" a = ");
/*  902 */     this.bLabel.setText(" b = ");
/*  903 */     this.equtLabel.setText("  ");
/*      */     
/*  905 */     this.madLabel.setText("        MAD = ");
/*  906 */     this.mseLabel.setText("MSE = ");
/*      */     
/*      */ 
/*  909 */     this.gCanvas.resetPlot();
/*  910 */     this.gCanvas.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void resetProb()
/*      */   {
/*  919 */     this.dataTable.leave();
/*  920 */     this.xyTable.leave();
/*      */     
/*  922 */     this.canDo = false;
/*      */     
/*  924 */     for (int i = 0; i < MAX_ROWS; i++) {
/*  925 */       this.actualDat[i] = 0.0F;
/*  926 */       this.forecastDat[i] = 0.0F;
/*  927 */       this.errDat[i] = 0.0F;
/*  928 */       this.intmS[i] = 0.0F;
/*  929 */       this.intmT[i] = 0.0F;
/*  930 */       this.predX[i] = 0.0F;
/*      */       
/*  932 */       this.dataTable.updateData(String.valueOf(String.valueOf(new StringBuffer("").append(i + 1))), i, 0);
/*  933 */       this.dataTable.updateData("0", i, 1);
/*  934 */       this.dataTable.updateData(" ", i, 2);
/*  935 */       this.dataTable.updateData(" ", i, 3);
/*      */       
/*  937 */       this.xyTable.updateData("0", i, 0);
/*  938 */       this.xyTable.updateData("0", i, 1);
/*  939 */       this.xyTable.updateData(" ", i, 2);
/*  940 */       this.xyTable.updateData(" ", i, 3);
/*      */     }
/*      */     
/*  943 */     initProb();
/*  944 */     this.dataTable.repaint();
/*  945 */     this.xyTable.repaint();
/*      */     
/*  947 */     this.currAlpha = 0.4F;
/*  948 */     this.alphaBar.setValue(4);
/*  949 */     this.alphaLabel.setText("alpha = ".concat(String.valueOf(String.valueOf(this.currAlpha))));
/*  950 */     this.currBeta = 0.5F;
/*  951 */     this.betaBar.setValue(5);
/*  952 */     this.betaLabel.setText("beta = ".concat(String.valueOf(String.valueOf(this.currBeta))));
/*  953 */     this.currX0 = 40.0F;
/*  954 */     if (this.methodComb.getSelectedIndex() == 1) {
/*  955 */       this.x0Label.setText("x0 = ".concat(String.valueOf(String.valueOf(this.currX0))));
/*  956 */     } else if (this.methodComb.getSelectedIndex() == 0)
/*  957 */       this.x0Label.setText("init = ".concat(String.valueOf(String.valueOf(this.currX0))));
/*  958 */     this.x0TextField.setText("".concat(String.valueOf(String.valueOf(this.currX0))));
/*  959 */     this.currT1 = 0.0F;
/*  960 */     this.t1Label.setText("T1 = ".concat(String.valueOf(String.valueOf(this.currT1))));
/*  961 */     this.t1TextField.setText("".concat(String.valueOf(String.valueOf(this.currT1))));
/*      */     
/*  963 */     this.aLabel.setText(" a = ");
/*  964 */     this.bLabel.setText(" b = ");
/*  965 */     this.equtLabel.setText("  ");
/*      */     
/*  967 */     this.madLabel.setText("        MAD = ");
/*  968 */     this.mseLabel.setText("MSE = ");
/*      */     
/*      */ 
/*  971 */     doForecast();
/*      */     
/*      */ 
/*  974 */     this.gCanvas.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void initProb()
/*      */   {
/*  981 */     this.dataTable.updateData("44", 0, 1);
/*  982 */     this.dataTable.updateData("52", 1, 1);
/*  983 */     this.dataTable.updateData("50", 2, 1);
/*  984 */     this.dataTable.updateData("54", 3, 1);
/*  985 */     this.dataTable.updateData("55", 4, 1);
/*  986 */     this.dataTable.updateData("55", 5, 1);
/*  987 */     this.dataTable.updateData("60", 6, 1);
/*  988 */     this.dataTable.updateData("56", 7, 1);
/*  989 */     this.dataTable.updateData("62", 8, 1);
/*      */     
/*  991 */     this.xyTable.updateData("9", 0, 0);
/*  992 */     this.xyTable.updateData("3", 1, 0);
/*  993 */     this.xyTable.updateData("3", 2, 0);
/*  994 */     this.xyTable.updateData("5", 3, 0);
/*  995 */     this.xyTable.updateData("4", 4, 0);
/*  996 */     this.xyTable.updateData("7", 5, 0);
/*  997 */     this.xyTable.updateData("2", 6, 0);
/*  998 */     this.xyTable.updateData("6", 7, 0);
/*  999 */     this.xyTable.updateData("4", 8, 0);
/*      */     
/* 1001 */     this.xyTable.updateData("46", 0, 1);
/* 1002 */     this.xyTable.updateData("18", 1, 1);
/* 1003 */     this.xyTable.updateData("20", 2, 1);
/* 1004 */     this.xyTable.updateData("22", 3, 1);
/* 1005 */     this.xyTable.updateData("27", 4, 1);
/* 1006 */     this.xyTable.updateData("34", 5, 1);
/* 1007 */     this.xyTable.updateData("14", 6, 1);
/* 1008 */     this.xyTable.updateData("37", 7, 1);
/* 1009 */     this.xyTable.updateData("30", 8, 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doForecast()
/*      */   {
/* 1020 */     this.dataTable.leave();
/* 1021 */     this.xyTable.leave();
/*      */     
/*      */ 
/* 1024 */     for (int i = 0; i < MAX_ROWS; i++) {
/* 1025 */       this.forecastDat[i] = 0.0F;
/* 1026 */       this.errDat[i] = 0.0F;
/* 1027 */       this.intmS[i] = 0.0F;
/* 1028 */       this.intmT[i] = 0.0F;
/* 1029 */       this.dataTable.updateData(" ", i, 2);
/* 1030 */       this.dataTable.updateData(" ", i, 3);
/*      */       
/* 1032 */       this.xyTable.updateData(" ", i, 2);
/* 1033 */       this.xyTable.updateData(" ", i, 3);
/*      */     }
/* 1035 */     if (!getOrigData()) {
/* 1036 */       javax.swing.JOptionPane.showMessageDialog(this, "Read raw data error.");
/*      */       
/* 1038 */       return;
/*      */     }
/* 1040 */     this.canDo = true;
/*      */     
/*      */ 
/* 1043 */     int activenum = 0;
/* 1044 */     int nonzero = 0;
/* 1045 */     for (i = 0; i < MAX_ROWS; i++)
/* 1046 */       if ((this.actualDat[i] > 0.001D) || (this.actualDat[i] < -0.001D))
/* 1047 */         nonzero = i;
/* 1048 */     activenum = nonzero + 1;
/* 1049 */     if (activenum >= MAX_ROWS) {
/* 1050 */       activenum = MAX_ROWS - 1;
/*      */     }
/* 1052 */     if (activenum < 5) {
/* 1053 */       javax.swing.JOptionPane.showMessageDialog(this, "No enough valid data to do forecasting!");
/* 1054 */       return;
/*      */     }
/*      */     
/* 1057 */     if (this.methodComb.getSelectedIndex() == 0)
/*      */     {
/* 1059 */       expo_smooth(activenum);
/* 1060 */       this.gCanvas.beginPlot(this.actualDat, this.forecastDat, 0.0F, 0.0F, EXP_SMOOTH);
/* 1061 */       this.gCanvas.repaint();
/* 1062 */       this.madLabel.setText("        MAD = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMAD)))));
/* 1063 */       this.mseLabel.setText("MSE = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMFE)))));
/* 1064 */     } else if (this.methodComb.getSelectedIndex() == 1)
/*      */     {
/* 1066 */       trend_adjust_expo_smooth(activenum);
/*      */       
/* 1068 */       this.gCanvas.beginPlot(this.actualDat, this.forecastDat, 0.0F, 0.0F, TREND_ADJUST_SMOOTH);
/* 1069 */       this.gCanvas.repaint();
/* 1070 */       this.madLabel.setText("        MAD = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMAD)))));
/* 1071 */       this.mseLabel.setText("MSE = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMFE)))));
/* 1072 */     } else if (this.methodComb.getSelectedIndex() == 2)
/*      */     {
/* 1074 */       linear_trend(activenum);
/*      */       
/* 1076 */       this.gCanvas.beginPlot(this.actualDat, this.forecastDat, this.aincept, this.bincept, LINEAR_TREND);
/* 1077 */       this.gCanvas.repaint();
/* 1078 */       this.madLabel.setText("        MAD = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMAD)))));
/* 1079 */       this.mseLabel.setText("MSE = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMFE)))));
/* 1080 */     } else if (this.methodComb.getSelectedIndex() == 3)
/*      */     {
/* 1082 */       linear_regression(activenum);
/*      */       
/* 1084 */       this.gCanvas.beginPlot(this.actualDat, this.predX, this.coef_x, this.coef_y, LINEAR_REGRESSION);
/* 1085 */       this.gCanvas.repaint();
/*      */       
/* 1087 */       this.madLabel.setText("        MAD = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMAD)))));
/* 1088 */       this.mseLabel.setText("MSE = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMFE)))));
/* 1089 */     } else if (this.methodComb.getSelectedIndex() == 4) {
/* 1090 */       last_value();
/*      */       
/* 1092 */       this.madLabel.setText("        MAD = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMAD)))));
/* 1093 */       this.mseLabel.setText("MSE = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMFE)))));
/* 1094 */     } else if (this.methodComb.getSelectedIndex() == 5) {
/* 1095 */       averaging();
/*      */       
/* 1097 */       this.madLabel.setText("        MAD = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMAD)))));
/* 1098 */       this.mseLabel.setText("MSE = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMFE)))));
/* 1099 */     } else if (this.methodComb.getSelectedIndex() == 6) {
/* 1100 */       moving_average();
/*      */       
/* 1102 */       this.madLabel.setText("        MAD = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMAD)))));
/* 1103 */       this.mseLabel.setText("MSE = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMFE)))));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void recalcForeData()
/*      */   {
/* 1114 */     int activenum = 0;int nonzero = 0;
/* 1115 */     for (int i = 0; i < MAX_ROWS; i++)
/* 1116 */       if ((this.actualDat[i] > 0.001D) || (this.actualDat[i] < -0.001D))
/* 1117 */         nonzero = i;
/* 1118 */     activenum = nonzero + 1;
/* 1119 */     if (activenum >= MAX_ROWS) {
/* 1120 */       activenum = MAX_ROWS - 1;
/*      */     }
/*      */     
/* 1123 */     if (this.methodComb.getSelectedIndex() == 0)
/*      */     {
/* 1125 */       expo_smooth(activenum);
/* 1126 */       this.madLabel.setText("        MAD = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMAD)))));
/* 1127 */       this.mseLabel.setText("MSE = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMFE)))));
/* 1128 */     } else if (this.methodComb.getSelectedIndex() == 1)
/*      */     {
/* 1130 */       trend_adjust_expo_smooth(activenum);
/* 1131 */       this.madLabel.setText("        MAD = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMAD)))));
/* 1132 */       this.mseLabel.setText("MSE = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMFE)))));
/* 1133 */     } else if (this.methodComb.getSelectedIndex() == 2)
/*      */     {
/* 1135 */       linear_trend(activenum);
/* 1136 */       this.gCanvas.adjustSlope(this.aincept, this.bincept);
/* 1137 */       this.madLabel.setText("        MAD = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMAD)))));
/* 1138 */       this.mseLabel.setText("MSE = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMFE)))));
/* 1139 */     } else if (this.methodComb.getSelectedIndex() == 3)
/*      */     {
/* 1141 */       linear_regression(activenum);
/* 1142 */       this.gCanvas.adjustSlope(this.coef_x, this.coef_y);
/* 1143 */       this.madLabel.setText("        MAD = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMAD)))));
/* 1144 */       this.mseLabel.setText("MSE = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMFE)))));
/* 1145 */     } else if (this.methodComb.getSelectedIndex() == 4)
/*      */     {
/* 1147 */       last_value();
/* 1148 */       this.madLabel.setText("        MAD = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMAD)))));
/* 1149 */       this.mseLabel.setText("MSE = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMFE)))));
/* 1150 */     } else if (this.methodComb.getSelectedIndex() == 5)
/*      */     {
/* 1152 */       averaging();
/* 1153 */       this.madLabel.setText("        MAD = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMAD)))));
/* 1154 */       this.mseLabel.setText("MSE = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMFE)))));
/* 1155 */     } else if (this.methodComb.getSelectedIndex() == 6)
/*      */     {
/* 1157 */       moving_average();
/* 1158 */       this.madLabel.setText("        MAD = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMAD)))));
/* 1159 */       this.mseLabel.setText("MSE = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.errMFE)))));
/*      */     }
/*      */     
/*      */ 
/* 1163 */     savePrintInfo();
/*      */   }
/*      */   
/*      */ 
/*      */   private void expo_smooth(int activenum)
/*      */   {
/* 1169 */     int validnum = 2;
/* 1170 */     float tmpmfe = 0.0F;float tmpmad = 0.0F;
/*      */     
/* 1172 */     this.forecastDat[0] = this.currX0;
/* 1173 */     this.forecastDat[1] = (this.currAlpha * this.actualDat[0] + (1 - this.currAlpha) * this.currX0);
/*      */     
/* 1175 */     this.errDat[0] = Math.abs(this.actualDat[0] - this.forecastDat[0]);
/* 1176 */     this.errDat[1] = Math.abs(this.actualDat[1] - this.forecastDat[1]);
/* 1177 */     tmpmad = Math.abs(this.errDat[0]) + Math.abs(this.errDat[1]);
/* 1178 */     tmpmfe = this.errDat[0] * this.errDat[0] + this.errDat[1] * this.errDat[1];
/*      */     
/* 1180 */     for (int i = 2; i < activenum + 1; i++) {
/* 1181 */       this.forecastDat[i] = (this.forecastDat[(i - 1)] + this.currAlpha * (this.actualDat[(i - 1)] - this.forecastDat[(i - 1)]));
/* 1182 */       if (i < activenum) {
/* 1183 */         this.errDat[i] = Math.abs(this.actualDat[i] - this.forecastDat[i]);
/* 1184 */         tmpmad += Math.abs(this.errDat[i]);
/* 1185 */         tmpmfe += this.errDat[i] * this.errDat[i];
/*      */       }
/*      */     }
/* 1188 */     validnum = activenum;
/* 1189 */     this.errMAD = (tmpmad / validnum);
/* 1190 */     this.errMFE = (tmpmfe / validnum);
/*      */     
/* 1192 */     for (i = 0; i <= validnum; i++) {
/* 1193 */       this.dataTable.updateData(this.decfm.format(this.forecastDat[i]), i, 2);
/* 1194 */       if (i < validnum)
/* 1195 */         this.dataTable.updateData(this.decfm.format(this.errDat[i]), i, 3);
/*      */     }
/* 1197 */     this.dataTable.repaint();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void trend_adjust_expo_smooth(int activenum)
/*      */   {
/* 1237 */     int validnum = 2;
/*      */     
/* 1239 */     float tmpmfe = 0.0F;float tmpmad = 0.0F;
/*      */     
/* 1241 */     this.intmT[0] = this.currT1;
/* 1242 */     this.forecastDat[0] = (this.currX0 + this.currT1);
/* 1243 */     this.intmS[1] = (this.currAlpha * (this.actualDat[0] - this.currX0) + (1 - this.currAlpha) * (this.forecastDat[0] - this.currX0));
/* 1244 */     this.intmT[1] = (this.currBeta * this.intmS[1] + (1 - this.currBeta) * this.intmT[0]);
/* 1245 */     this.forecastDat[1] = (this.currAlpha * this.actualDat[0] + (1 - this.currAlpha) * this.forecastDat[0] + this.intmT[1]);
/*      */     
/* 1247 */     this.errDat[0] = Math.abs(this.actualDat[0] - this.forecastDat[0]);
/* 1248 */     this.errDat[1] = Math.abs(this.actualDat[1] - this.forecastDat[1]);
/*      */     
/* 1250 */     tmpmad = this.errDat[0] + this.errDat[1];
/* 1251 */     tmpmfe = this.errDat[0] * this.errDat[0] + this.errDat[1] * this.errDat[1];
/*      */     
/* 1253 */     for (int i = 2; i <= activenum + 1; i++)
/*      */     {
/* 1255 */       this.intmS[i] = (this.currAlpha * (this.actualDat[(i - 1)] - this.actualDat[(i - 2)]) + (1 - this.currAlpha) * (this.forecastDat[(i - 1)] - this.forecastDat[(i - 2)]));
/* 1256 */       this.intmT[i] = (this.currBeta * this.intmS[i] + (1 - this.currBeta) * this.intmT[(i - 1)]);
/* 1257 */       this.forecastDat[i] = (this.currAlpha * this.actualDat[(i - 1)] + (1 - this.currAlpha) * this.forecastDat[(i - 1)] + this.intmT[i]);
/*      */       
/* 1259 */       this.errDat[i] = Math.abs(this.actualDat[i] - this.forecastDat[i]);
/* 1260 */       if (i < activenum) {
/* 1261 */         tmpmad += Math.abs(this.errDat[i]);
/* 1262 */         tmpmfe += this.errDat[i] * this.errDat[i];
/*      */       }
/*      */     }
/*      */     
/* 1266 */     validnum = activenum + 1;
/* 1267 */     this.errMAD = (tmpmad / activenum);
/* 1268 */     this.errMFE = (tmpmfe / activenum);
/*      */     
/* 1270 */     for (i = 0; i < activenum + 1; i++) {
/* 1271 */       this.dataTable.updateData(this.decfm.format(this.forecastDat[i]), i, 2);
/* 1272 */       if (i < activenum)
/* 1273 */         this.dataTable.updateData(this.decfm.format(this.errDat[i]), i, 3);
/*      */     }
/* 1275 */     this.dataTable.repaint();
/*      */   }
/*      */   
/*      */   private void linear_trend(int activenum)
/*      */   {
/* 1280 */     int validnum = 2;
/* 1281 */     int sigma_t = 0;int sigma_t2 = 0;
/*      */     
/* 1283 */     float tmpmfe = 0.0F;float tmpmad = 0.0F;
/*      */     
/* 1285 */     float sigma_y = 0.0F;
/* 1286 */     float sigma_ty = 0.0F;
/* 1287 */     for (int i = 1; i < activenum + 1; i++) {
/* 1288 */       sigma_t += i;
/* 1289 */       sigma_t2 += i * i;
/* 1290 */       sigma_y += this.actualDat[(i - 1)];
/* 1291 */       sigma_ty += i * this.actualDat[(i - 1)];
/*      */     }
/* 1293 */     validnum = activenum;
/*      */     
/* 1295 */     this.bincept = ((validnum * sigma_ty - sigma_t * sigma_y) / (validnum * sigma_t2 - sigma_t * sigma_t));
/* 1296 */     this.aincept = ((sigma_y - this.bincept * sigma_t) / validnum);
/* 1297 */     this.aLabel.setText("a = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.aincept)))));
/* 1298 */     this.bLabel.setText("b = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.bincept)))));
/* 1299 */     if (this.bincept > 0.001D) {
/* 1300 */       this.equtLabel.setText(String.valueOf(String.valueOf(new StringBuffer(" y = ").append(this.decfm.format(this.aincept)).append(" + ").append(this.decfm.format(this.bincept)).append("t "))));
/*      */     } else {
/* 1302 */       this.equtLabel.setText(String.valueOf(String.valueOf(new StringBuffer(" y = ").append(this.decfm.format(this.aincept)).append(" ").append(this.decfm.format(this.bincept)).append("t "))));
/*      */     }
/*      */     
/* 1305 */     for (i = 0; i < MAX_ROWS; i++) {
/* 1306 */       this.forecastDat[i] = (this.aincept + (i + 1) * this.bincept);
/* 1307 */       if (i < activenum) {
/* 1308 */         this.errDat[i] = Math.abs(this.actualDat[i] - this.forecastDat[i]);
/* 1309 */         tmpmad += Math.abs(this.errDat[i]);
/* 1310 */         tmpmfe += this.errDat[i] * this.errDat[i];
/*      */       }
/*      */     }
/* 1313 */     this.errMAD = (tmpmad / validnum);
/* 1314 */     this.errMFE = (tmpmfe / validnum);
/*      */     
/* 1316 */     savePrintInfo();
/* 1317 */     for (i = 0; i < MAX_ROWS; i++) {
/* 1318 */       this.dataTable.updateData(this.decfm.format(this.forecastDat[i]), i, 2);
/* 1319 */       if (i < validnum)
/* 1320 */         this.dataTable.updateData(this.decfm.format(this.errDat[i]), i, 3);
/*      */     }
/* 1322 */     this.dataTable.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */   private void linear_regression(int activenum)
/*      */   {
/* 1328 */     int validnum = 2;
/*      */     
/* 1330 */     float tmpmfe = 0.0F;float tmpmad = 0.0F;
/*      */     
/* 1332 */     float sigma_y = 0.0F;
/* 1333 */     float sigma_xy = 0.0F;
/* 1334 */     float sigma_x = 0.0F;
/* 1335 */     float sigma_x2 = 0.0F;
/* 1336 */     for (int i = 0; i < activenum; i++) {
/* 1337 */       sigma_x += this.predX[i];
/* 1338 */       sigma_x2 += this.predX[i] * this.predX[i];
/* 1339 */       sigma_y += this.actualDat[i];
/* 1340 */       sigma_xy += this.predX[i] * this.actualDat[i];
/*      */     }
/* 1342 */     validnum = activenum;
/*      */     
/* 1344 */     this.coef_y = ((validnum * sigma_xy - sigma_x * sigma_y) / (validnum * sigma_x2 - sigma_x * sigma_x));
/* 1345 */     this.coef_x = ((sigma_y - this.coef_y * sigma_x) / validnum);
/* 1346 */     this.aLabel.setText("a = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.coef_x)))));
/* 1347 */     this.bLabel.setText("b = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.coef_y)))));
/* 1348 */     if (this.coef_y > 0.001D) {
/* 1349 */       this.equtLabel.setText(String.valueOf(String.valueOf(new StringBuffer(" y = ").append(this.decfm.format(this.coef_x)).append(" + ").append(this.decfm.format(this.coef_y)).append("x "))));
/*      */     } else {
/* 1351 */       this.equtLabel.setText(String.valueOf(String.valueOf(new StringBuffer(" y = ").append(this.decfm.format(this.coef_x)).append(" + ").append(this.decfm.format(this.coef_y)).append("x "))));
/*      */     }
/*      */     
/* 1354 */     for (i = 0; i < validnum; i++) {
/* 1355 */       this.forecastDat[i] = (this.coef_x + this.predX[i] * this.coef_y);
/* 1356 */       this.errDat[i] = Math.abs(this.actualDat[i] - this.forecastDat[i]);
/* 1357 */       tmpmad += Math.abs(this.errDat[i]);
/* 1358 */       tmpmfe += this.errDat[i] * this.errDat[i];
/*      */     }
/*      */     
/* 1361 */     this.errMAD = (tmpmad / validnum);
/* 1362 */     this.errMFE = (tmpmfe / validnum);
/*      */     
/* 1364 */     for (i = 0; i < validnum; i++) {
/* 1365 */       this.xyTable.updateData(this.decfm.format(this.forecastDat[i]), i, 2);
/* 1366 */       this.xyTable.updateData(this.decfm.format(this.errDat[i]), i, 3);
/*      */     }
/* 1368 */     savePrintInfo();
/* 1369 */     this.xyTable.repaint();
/*      */   }
/*      */   
/*      */   private void last_value()
/*      */   {
/* 1374 */     int validnum = 2;
/* 1375 */     float tmpmse = 0.0F;float tmpmad = 0.0F;
/* 1376 */     for (int i = 1; i < MAX_ROWS; i++) {
/* 1377 */       if (this.actualDat[(i - 1)] < 0.001D)
/*      */         break;
/* 1379 */       this.forecastDat[i] = this.actualDat[(i - 1)];
/* 1380 */       if (this.actualDat[i] > 0.001D) {
/* 1381 */         this.errDat[i] = Math.abs(this.actualDat[i] - this.forecastDat[i]);
/* 1382 */         tmpmad += Math.abs(this.errDat[i]);
/* 1383 */         tmpmse += this.errDat[i] * this.errDat[i];
/*      */       }
/*      */     }
/* 1386 */     this.errMAD = (tmpmad / (i - 2));
/* 1387 */     this.errMFE = (tmpmse / (i - 2));
/*      */     
/* 1389 */     validnum = i - 1;
/*      */     
/* 1391 */     for (i = 1; i <= validnum; i++) {
/* 1392 */       this.dataTable.updateData(this.decfm.format(this.forecastDat[i]), i, 2);
/* 1393 */       if (i < validnum)
/* 1394 */         this.dataTable.updateData(this.decfm.format(this.errDat[i]), i, 3);
/*      */     }
/* 1396 */     this.dataTable.repaint();
/*      */     
/*      */ 
/*      */ 
/* 1400 */     this.gAddCanvas.beginPlot(this.actualDat, this.forecastDat, 0.0F, 0.0F, LAST_VALUE);
/* 1401 */     this.gAddCanvas.repaint();
/*      */   }
/*      */   
/*      */   private void averaging() {
/* 1405 */     int validnum = 2;
/* 1406 */     float avghead = 0.0F;
/* 1407 */     float tmpmse = 0.0F;float tmpmad = 0.0F;
/*      */     
/* 1409 */     this.forecastDat[1] = this.actualDat[0];
/* 1410 */     this.errDat[1] = (this.actualDat[1] - this.forecastDat[1]);
/* 1411 */     tmpmad += Math.abs(this.errDat[1]);
/* 1412 */     tmpmse += this.errDat[1] * this.errDat[1];
/* 1413 */     for (int i = 2; i < MAX_ROWS; i++) {
/* 1414 */       if (this.actualDat[(i - 1)] < 0.001D)
/*      */         break;
/* 1416 */       avghead = 0.0F;
/* 1417 */       for (int j = 0; j < i; j++) {
/* 1418 */         avghead += this.actualDat[j];
/*      */       }
/* 1420 */       this.forecastDat[i] = (avghead / i);
/* 1421 */       if (this.actualDat[i] > 0.001D) {
/* 1422 */         this.errDat[i] = Math.abs(this.actualDat[i] - this.forecastDat[i]);
/* 1423 */         tmpmad += Math.abs(this.errDat[i]);
/* 1424 */         tmpmse += this.errDat[i] * this.errDat[i];
/*      */       }
/*      */     }
/*      */     
/* 1428 */     this.errMAD = (tmpmad / (i - 2));
/* 1429 */     this.errMFE = (tmpmse / (i - 2));
/*      */     
/* 1431 */     validnum = i - 1;
/*      */     
/* 1433 */     for (i = 1; i <= validnum; i++) {
/* 1434 */       this.dataTable.updateData(this.decfm.format(this.forecastDat[i]), i, 2);
/* 1435 */       if (i < validnum)
/* 1436 */         this.dataTable.updateData(this.decfm.format(this.errDat[i]), i, 3);
/*      */     }
/* 1438 */     this.dataTable.repaint();
/*      */     
/* 1440 */     this.gAddCanvas.beginPlot(this.actualDat, this.forecastDat, 0.0F, 0.0F, AVERAGING);
/* 1441 */     this.gAddCanvas.repaint();
/*      */   }
/*      */   
/*      */   public void moving_average() {
/* 1445 */     int validnum = 2;
/* 1446 */     float avghead = 0.0F;
/* 1447 */     float tmpmse = 0.0F;float tmpmad = 0.0F;
/*      */     
/*      */ 
/* 1450 */     for (int i = this.lastLooknum; i < MAX_ROWS; i++) {
/* 1451 */       if (this.actualDat[(i - 1)] < 0.001D)
/*      */         break;
/* 1453 */       avghead = 0.0F;
/* 1454 */       for (int j = i - this.lastLooknum; j < i; j++) {
/* 1455 */         avghead += this.actualDat[j];
/*      */       }
/* 1457 */       this.forecastDat[i] = (avghead / this.lastLooknum);
/* 1458 */       if (this.actualDat[i] > 0.001D) {
/* 1459 */         this.errDat[i] = Math.abs(this.actualDat[i] - this.forecastDat[i]);
/* 1460 */         tmpmad += Math.abs(this.errDat[i]);
/* 1461 */         tmpmse += this.errDat[i] * this.errDat[i];
/*      */       }
/*      */     }
/* 1464 */     this.errMAD = (tmpmad / (i - this.lastLooknum - 1));
/* 1465 */     this.errMFE = (tmpmse / (i - this.lastLooknum - 1));
/*      */     
/* 1467 */     validnum = i - this.lastLooknum - 1;
/* 1468 */     for (i = 0; i <= validnum; i++) {
/* 1469 */       this.dataTable.updateData(this.decfm.format(this.forecastDat[(i + this.lastLooknum)]), i + this.lastLooknum, 2);
/* 1470 */       if (i < validnum)
/* 1471 */         this.dataTable.updateData(this.decfm.format(this.errDat[(i + this.lastLooknum)]), i + this.lastLooknum, 3);
/*      */     }
/* 1473 */     this.dataTable.repaint();
/*      */     
/* 1475 */     this.gAddCanvas.beginPlot(this.actualDat, this.forecastDat, 0.0F, 0.0F, MOVING_AVERAGE);
/* 1476 */     this.gAddCanvas.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean getOrigData()
/*      */   {
/*      */     Float tflt;
/*      */     
/*      */     String cellstr;
/*      */     
/* 1487 */     for (int i = 0; i < MAX_ROWS; i++) {
/*      */       try {
/* 1489 */         if (this.methodComb.getSelectedIndex() == 3) {
/* 1490 */           String xstr = this.xyTable.getData(i, 0);
/* 1491 */           Float xf = Float.valueOf(xstr);
/*      */           
/* 1493 */           this.predX[i] = xf.floatValue();
/*      */           
/*      */ 
/*      */ 
/* 1497 */           String cellstr = this.xyTable.getData(i, 1);
/* 1498 */           Float tflt = Float.valueOf(cellstr);
/*      */           
/* 1500 */           this.actualDat[i] = tflt.floatValue();
/*      */         }
/*      */         else
/*      */         {
/* 1504 */           String cellstr = this.dataTable.getData(i, 1);
/* 1505 */           Float tflt = Float.valueOf(cellstr);
/*      */           
/* 1507 */           this.actualDat[i] = tflt.floatValue();
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1513 */         System.out.println(e);
/* 1514 */         e.printStackTrace();
/* 1515 */         return false;
/*      */       }
/*      */     }
/* 1518 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void changeDat(int index, float value)
/*      */   {
/* 1529 */     this.actualDat[index] = value;
/* 1530 */     if (this.methodComb.getSelectedIndex() < 3) {
/* 1531 */       this.dataTable.updateData(this.decfm.format(value), index, 1);
/*      */     } else {
/* 1533 */       this.xyTable.updateData(this.decfm.format(value), index, 1);
/*      */     }
/* 1535 */     recalcForeData();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void leaveTable()
/*      */   {
/* 1543 */     this.dataTable.leave();
/* 1544 */     this.xyTable.leave();
/*      */   }
/*      */   
/*      */ 
/*      */   public void updateData(int num, String value, int row, int column)
/*      */   {
/*      */     try
/*      */     {
/* 1552 */       if (!value.equals("")) {
/* 1553 */         Float.valueOf(value);
/*      */       }
/* 1555 */       if (this.methodComb.getSelectedIndex() != 3) {
/* 1556 */         this.dataTable.updateData(value, row, column);
/* 1557 */         this.dataTable.repaint();
/*      */       } else {
/* 1559 */         this.xyTable.updateData(value, row, column);
/* 1560 */         this.xyTable.repaint();
/*      */       }
/*      */     } catch (NumberFormatException e) {
/* 1563 */       System.out.println(e);
/*      */     }
/*      */   }
/*      */   
/*      */   public void updateData(int num, int value, int row, int column) {}
/*      */   
/*      */   public void updateData(int num, float value, int row, int column) {}
/*      */   
/*      */   public void clickData(int num, int row, int column) {}
/*      */   
/*      */   public void focusOn(int num) {}
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORGForecastPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */