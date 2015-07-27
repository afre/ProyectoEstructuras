/*      */ import java.awt.Color;
/*      */ import java.awt.Point;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.print.Printable;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.Vector;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IORHungarianPanel
/*      */   extends IORHuangarianActionPanel
/*      */   implements POMTableDataNotify, Printable
/*      */ {
/*   25 */   int phalanx = 4;
/*      */   
/*   27 */   boolean isStandalone = false;
/*      */   int[][] step42;
/*      */   int[][] step41;
/*   30 */   static final int xWitdth = 85;
/*   31 */   static final int bWidth = 75;
/*   32 */   static final int xSpace = 20;
/*   33 */   static final int bHeight = 23;
/*      */   int step;
/*   35 */   String helpContent = "";
/*      */   POMTablePanel table;
/*   37 */   boolean auto = false;
/*   38 */   JButton Next = new JButton("Next");
/*   39 */   JButton Prev = new JButton("Prev");
/*   40 */   JButton Reset = new JButton("Start");
/*   41 */   JButton Yes = new JButton("Yes");
/*   42 */   JButton No = new JButton("No");
/*   43 */   JButton DemoButton = new JButton("Auto");
/*   44 */   JButton auto_reset = new JButton("Reset");
/*   45 */   MessageArea Message = new MessageArea(this);
/*      */   
/*      */ 
/*   48 */   JButton Add = new JButton(); JButton Delete = new JButton();
/*   49 */   JComboBox choice = new JComboBox();
/*      */   
/*   51 */   String[] header = new String[3];
/*   52 */   String[] data = new String[30];
/*   53 */   String[] defaultdata = new String[30];
/*   54 */   Robot robot = new Robot(this, this.phalanx);
/*   55 */   Demo demo = new Demo(this, this.phalanx);
/*      */   
/*      */   Robot.HistoryItem currentItem;
/*      */   int current;
/*      */   boolean prevProcess;
/*   60 */   boolean[] answer0 = { false, false, false, false };
/*      */   
/*   62 */   boolean[] answer1 = { false, false, false, false };
/*      */   
/*   64 */   Vector answer3 = new Vector();
/*   65 */   Vector answer3_backup = new Vector();
/*   66 */   int[] answer4 = { -1, -1 };
/*      */   
/*   68 */   Vector answer6 = new Vector();
/*      */   
/*      */ 
/*      */   String[] rowMin;
/*      */   
/*      */ 
/*      */   String[] colMin;
/*      */   
/*      */   IORHungarianProcessController.HungarianData hungarianData;
/*      */   
/*      */   NumberFormat nf;
/*      */   
/*      */ 
/*      */   public IORHungarianPanel(IORHungarianProcessController c, boolean auto)
/*      */   {
/*   83 */     super(c);
/*   84 */     this.auto = auto;
/*   85 */     this.table = new POMTablePanel(this, 5, 6, 1);
/*   86 */     init();
/*   87 */     this.controller.solver.emptyPrintData();
/*   88 */     this.nf = NumberFormat.getInstance();
/*   89 */     this.nf.setMaximumFractionDigits(2);
/*   90 */     this.nf.setMinimumFractionDigits(0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void init()
/*      */   {
/*      */     try
/*      */     {
/*   99 */       this.header[0] = " Assignee ";
/*  100 */       this.header[1] = "Task\t  A  \t  B  \t  C  \t  D  ";
/*  101 */       this.header[2] = "Row Min.";
/*  102 */       this.data[0] = "0";
/*  103 */       this.data[1] = "0";
/*  104 */       this.data[2] = "0";
/*  105 */       this.data[3] = "0";
/*  106 */       this.data[4] = "0";
/*  107 */       this.data[5] = "0";
/*  108 */       this.data[6] = "0";
/*  109 */       this.data[7] = "0";
/*  110 */       this.data[8] = "0";
/*  111 */       this.data[9] = "0";
/*  112 */       this.data[10] = "0";
/*  113 */       this.data[11] = "0";
/*  114 */       this.data[12] = "0";
/*  115 */       this.data[13] = "0";
/*  116 */       this.data[14] = "0";
/*  117 */       this.data[15] = "0";
/*  118 */       this.data[16] = "0";
/*  119 */       this.data[17] = "0";
/*  120 */       this.data[18] = "0";
/*  121 */       this.data[19] = "0";
/*  122 */       this.data[20] = "0";
/*  123 */       this.data[21] = "0";
/*  124 */       this.data[22] = "0";
/*  125 */       this.data[23] = "0";
/*  126 */       this.data[24] = "0";
/*  127 */       this.data[25] = "0";
/*  128 */       this.data[26] = "0";
/*  129 */       this.data[27] = "0";
/*  130 */       this.data[28] = "0";
/*  131 */       this.data[29] = "0";
/*  132 */       System.arraycopy(this.data, 0, this.defaultdata, 0, this.data.length);
/*  133 */       this.table.setTopLeft(0, 0);
/*  134 */       this.table.setForeground(Color.black, Color.blue);
/*  135 */       this.table.setBackground(new Color(120, 200, 20), Color.lightGray, Color.cyan);
/*      */       
/*  137 */       jbInit();
/*  138 */       this.Next.setEnabled(false);
/*  139 */       this.DemoButton.setEnabled(false);
/*  140 */       this.Prev.setEnabled(false);
/*  141 */       reset();
/*  142 */       this.table.repaint();
/*  143 */       this.Reset.setText("Start");
/*  144 */       step(-1);
/*      */     }
/*      */     catch (Exception e) {
/*  147 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private void jbInit() throws Exception
/*      */   {
/*  153 */     this.Yes.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  155 */         IORHungarianPanel.this.Yes_actionPerformed(e);
/*      */       }
/*  157 */     });
/*  158 */     this.No.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  160 */         IORHungarianPanel.this.No_actionPerformed(e);
/*      */       }
/*  162 */     });
/*  163 */     this.Next.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  165 */         IORHungarianPanel.this.Next_actionPerformed(e);
/*      */       }
/*  167 */     });
/*  168 */     this.Prev.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  170 */         IORHungarianPanel.this.Prev_actionPerformed(e);
/*      */       }
/*  172 */     });
/*  173 */     this.Reset.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  175 */         IORHungarianPanel.this.Reset_actionPerformed(e);
/*      */       }
/*  177 */     });
/*  178 */     this.auto_reset.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  180 */         IORHungarianPanel.this.auto_Reset_actionPerformed(e);
/*      */       }
/*      */       
/*  183 */     });
/*  184 */     this.DemoButton.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  186 */         IORHungarianPanel.this.DemoButton_actionPerformed(e);
/*      */       }
/*  188 */     });
/*  189 */     this.Add.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  191 */         IORHungarianPanel.this.Add_actionPerformed(e);
/*      */       }
/*  193 */     });
/*  194 */     this.Delete.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  196 */         IORHungarianPanel.this.Delete_actionPerformed(e);
/*      */       }
/*  198 */     });
/*  199 */     Color back = new Color(212, 212, 212);
/*  200 */     setLayout(null);
/*      */     
/*      */ 
/*  203 */     this.Prev.setEnabled(false);
/*  204 */     this.Next.setEnabled(false);
/*  205 */     this.DemoButton.setEnabled(false);
/*  206 */     this.Prev.setText("Prev");
/*  207 */     this.Next.setText("Next");
/*  208 */     this.Reset.setText("Reset");
/*  209 */     this.DemoButton.setText("Auto");
/*      */     
/*  211 */     changeDemoState(this.auto);
/*      */     
/*  213 */     this.Yes.setText("Yes");
/*  214 */     this.No.setText("No");
/*  215 */     this.Yes.setVisible(false);
/*  216 */     this.No.setVisible(false);
/*      */     
/*  218 */     add(this.Yes);
/*  219 */     add(this.No);
/*      */     
/*  221 */     this.Message.setBounds(85, 30, 470, 100);
/*  222 */     add(this.Message);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  230 */     this.table.setBounds(85, 141, 500, 150);
/*  231 */     add(this.table);
/*      */     
/*      */ 
/*  234 */     this.Add.setText("Add");
/*  235 */     this.Delete.setText("Delete");
/*  236 */     add(this.Add);
/*  237 */     add(this.Delete);
/*      */     
/*  239 */     add();
/*  240 */     add();
/*      */   }
/*      */   
/*      */ 
/*      */   public void changeDemoState(boolean auto)
/*      */   {
/*  246 */     this.Reset.setText("Start");
/*  247 */     this.Prev.setEnabled(false);
/*  248 */     this.Next.setEnabled(false);
/*  249 */     this.DemoButton.setEnabled(false);
/*      */     
/*  251 */     if (!auto) {
/*  252 */       this.Reset.setBounds(85, 305, 75, 23);
/*  253 */       this.Prev.setBounds(180, 305, 75, 23);
/*  254 */       this.Next.setBounds(275, 305, 75, 23);
/*  255 */       this.DemoButton.setBounds(370, 305, 80, 23);
/*  256 */       this.Yes.setBounds(220, 80, 75, 23);
/*  257 */       this.No.setBounds(315, 80, 75, 23);
/*  258 */       this.Add.setBounds(370, 305, 75, 23);
/*  259 */       this.Delete.setBounds(465, 305, 75, 23);
/*  260 */       this.choice.setBounds(560, 305, 75, 23);
/*  261 */       add(this.Prev);
/*  262 */       add(this.Next);
/*  263 */       add(this.Reset);
/*      */       
/*  265 */       this.Prev.setVisible(true);
/*  266 */       this.Next.setVisible(true);
/*  267 */       this.DemoButton.setVisible(false);
/*      */     }
/*      */     else {
/*  270 */       this.Reset.setBounds(85, 305, 75, 23);
/*      */       
/*      */ 
/*      */ 
/*  274 */       this.DemoButton.setBounds(180, 305, 75, 23);
/*  275 */       this.Yes.setBounds(220, 80, 75, 23);
/*  276 */       this.No.setBounds(315, 80, 75, 23);
/*  277 */       this.Add.setBounds(390, 305, 75, 23);
/*  278 */       this.Delete.setBounds(485, 305, 75, 23);
/*  279 */       this.choice.setBounds(580, 305, 75, 23);
/*  280 */       add(this.DemoButton);
/*  281 */       add(this.auto_reset);
/*  282 */       add(this.Reset);
/*      */       
/*  284 */       this.Prev.setVisible(false);
/*  285 */       this.Next.setVisible(false);
/*  286 */       this.DemoButton.setVisible(true);
/*      */     }
/*  288 */     setAdd_Delete(true);
/*  289 */     this.Next.setEnabled(false);
/*  290 */     this.Prev.setEnabled(false);
/*      */     
/*  292 */     if (this.demo.isAlive()) {
/*  293 */       this.demo.stop();
/*      */     }
/*  295 */     this.DemoButton.setEnabled(true);
/*  296 */     reset();
/*  297 */     this.table.repaint();
/*  298 */     this.Reset.setText("Start");
/*  299 */     step(-1);
/*  300 */     this.controller.solver.emptyPrintData();
/*  301 */     this.DemoButton.setEnabled(false);
/*      */   }
/*      */   
/*      */   public void LoadFile(ObjectInputStream in) {
/*      */     try {
/*  306 */       this.current = ((Integer)in.readObject()).intValue();
/*  307 */       this.currentItem = ((Robot.HistoryItem)in.readObject());
/*  308 */       this.data = ((String[])in.readObject());
/*  309 */       this.defaultdata = ((String[])in.readObject());
/*  310 */       this.demo.pauseAndContinue = in.readBoolean();
/*  311 */       this.answer0 = ((boolean[])in.readObject());
/*  312 */       this.answer1 = ((boolean[])in.readObject());
/*  313 */       this.answer3 = ((Vector)in.readObject());
/*  314 */       this.answer3_backup = ((Vector)in.readObject());
/*  315 */       this.answer4 = ((int[])in.readObject());
/*  316 */       this.answer6 = ((Vector)in.readObject());
/*  317 */       this.header = ((String[])in.readObject());
/*  318 */       this.isStandalone = ((Boolean)in.readObject()).booleanValue();
/*  319 */       this.Message.message((String)in.readObject());
/*  320 */       this.phalanx = ((Integer)in.readObject()).intValue();
/*  321 */       this.prevProcess = ((Boolean)in.readObject()).booleanValue();
/*      */       
/*  323 */       this.table.leave();
/*  324 */       this.table.modifyTable(this.phalanx + 1, this.phalanx + 2);
/*  325 */       this.table.setData((String[])in.readObject());
/*  326 */       this.table.setHeader(this.header);
/*  327 */       this.table.InitEnd();
/*      */       
/*      */ 
/*      */ 
/*  331 */       this.Reset.setText((String)in.readObject());
/*  332 */       this.Next.setEnabled(((Boolean)in.readObject()).booleanValue());
/*  333 */       this.Prev.setEnabled(((Boolean)in.readObject()).booleanValue());
/*  334 */       this.Add.setEnabled(((Boolean)in.readObject()).booleanValue());
/*  335 */       this.Delete.setEnabled(((Boolean)in.readObject()).booleanValue());
/*  336 */       this.choice.setEnabled(((Boolean)in.readObject()).booleanValue());
/*      */       
/*  338 */       this.robot.getStatus(in);
/*  339 */       this.controller.solver.readObject(in);
/*  340 */       in.close();
/*  341 */       if (this.demo.pauseAndContinue) {
/*  342 */         step(this.current);
/*  343 */         this.demo.start();
/*  344 */         this.demo.pause = true;
/*      */       } else {
/*  346 */         step(this.current); }
/*  347 */       this.table.repaint();
/*      */     }
/*      */     catch (Exception e1) {
/*  350 */       e1.printStackTrace();
/*  351 */       System.out.println("Load fails");
/*      */     }
/*  353 */     this.Reset.setText("Start");
/*  354 */     this.Reset.setEnabled(true);
/*      */   }
/*      */   
/*      */   public void SaveFile(ObjectOutputStream out) {
/*  358 */     this.controller.hungarianAuto = this.auto;
/*  359 */     System.out.println("Save hungarianAuto is :".concat(String.valueOf(String.valueOf(this.controller.hungarianAuto))));
/*      */     try {
/*  361 */       out.writeObject(new Integer(this.current));
/*  362 */       out.writeObject(this.currentItem);
/*  363 */       out.writeObject(this.data);
/*  364 */       out.writeObject(this.defaultdata);
/*  365 */       out.writeBoolean(this.demo.pauseAndContinue);
/*      */       
/*  367 */       out.writeObject(this.answer0);
/*  368 */       out.writeObject(this.answer1);
/*  369 */       out.writeObject(this.answer3);
/*  370 */       out.writeObject(this.answer3_backup);
/*  371 */       out.writeObject(this.answer4);
/*  372 */       out.writeObject(this.answer6);
/*  373 */       out.writeObject(this.header);
/*      */       
/*  375 */       out.writeObject(new Boolean(this.isStandalone));
/*      */       
/*  377 */       out.writeObject(this.Message.mes);
/*  378 */       out.writeObject(new Integer(this.phalanx));
/*  379 */       out.writeObject(new Boolean(this.prevProcess));
/*      */       
/*  381 */       String[] temp = this.table.getData();
/*  382 */       out.writeObject(this.table.getData());
/*  383 */       out.writeObject(this.Reset.getText());
/*  384 */       out.writeObject(new Boolean(this.Next.isEnabled()));
/*  385 */       out.writeObject(new Boolean(this.Prev.isEnabled()));
/*  386 */       out.writeObject(new Boolean(this.Add.isEnabled()));
/*  387 */       out.writeObject(new Boolean(this.Delete.isEnabled()));
/*  388 */       out.writeObject(new Boolean(this.choice.isEnabled()));
/*  389 */       this.robot.saveStatus(out);
/*  390 */       this.controller.solver.writeObject(out);
/*  391 */       out.close();
/*      */     }
/*      */     catch (Exception e1) {
/*  394 */       e1.printStackTrace();
/*  395 */       System.out.println("Save fails");
/*      */     }
/*      */   }
/*      */   
/*      */   public void initTable() {
/*  400 */     this.answer0 = new boolean[this.phalanx];
/*  401 */     this.answer1 = new boolean[this.phalanx];
/*  402 */     this.rowMin = new String[this.phalanx];
/*  403 */     this.colMin = new String[this.phalanx];
/*      */     
/*  405 */     this.answer3.clear();
/*  406 */     this.answer3_backup.clear();
/*  407 */     for (int i = 0; i < 2; i++) {
/*  408 */       this.answer4[i] = -1;
/*      */     }
/*  410 */     this.answer6.removeAllElements();
/*  411 */     this.data = new String[(this.phalanx + 2) * (this.phalanx + 1)];
/*  412 */     System.arraycopy(this.defaultdata, 0, this.data, 0, this.defaultdata.length);
/*  413 */     this.header[1] = "Task\t  A  \t  B  \t";
/*  414 */     switch (this.phalanx) {
/*      */     case 3: 
/*  416 */       int tmp169_168 = 1; String[] tmp169_165 = this.header;tmp169_165[tmp169_168] = String.valueOf(String.valueOf(tmp169_165[tmp169_168])).concat("  C  ");
/*  417 */       break;
/*      */     case 4: 
/*  419 */       int tmp191_190 = 1; String[] tmp191_187 = this.header;tmp191_187[tmp191_190] = String.valueOf(String.valueOf(tmp191_187[tmp191_190])).concat("  C  \t  D  ");
/*  420 */       break;
/*      */     case 5: 
/*  422 */       int tmp213_212 = 1; String[] tmp213_209 = this.header;tmp213_209[tmp213_212] = String.valueOf(String.valueOf(tmp213_209[tmp213_212])).concat("  C  \t  D  \t  E  ");
/*  423 */       break;
/*      */     case 6: 
/*  425 */       int tmp235_234 = 1; String[] tmp235_231 = this.header;tmp235_231[tmp235_234] = String.valueOf(String.valueOf(tmp235_231[tmp235_234])).concat("  C  \t  D  \t  E  \t  F  ");
/*      */     }
/*  427 */     this.table.leave();
/*  428 */     this.table.modifyTable(this.phalanx + 1, this.phalanx + 2);
/*  429 */     this.table.setHeader(this.header);
/*      */     
/*  431 */     String[] tempData = new String[this.data.length];
/*  432 */     for (int i = 0; i < this.data.length; i++) {
/*  433 */       if ((this.data[i].equalsIgnoreCase("1.7976931348623157E308")) || (this.data[i].equalsIgnoreCase("Infinity")))
/*      */       {
/*  435 */         tempData[i] = "M";
/*      */       } else {
/*  437 */         tempData[i] = this.data[i];
/*      */       }
/*      */     }
/*      */     
/*  441 */     this.table.setData(tempData);
/*  442 */     this.table.InitEnd();
/*  443 */     this.robot = new Robot(this, this.phalanx);
/*  444 */     this.choice.removeAllItems();
/*  445 */     for (int i = 1; i < this.phalanx + 1; i++) {
/*  446 */       this.choice.addItem("Job ".concat(String.valueOf(String.valueOf(i))));
/*      */     }
/*  448 */     this.demo = new Demo(this, this.phalanx);
/*  449 */     this.current = 0;
/*      */   }
/*      */   
/*      */   private void reset()
/*      */   {
/*  454 */     this.answer0 = new boolean[this.phalanx];
/*  455 */     this.answer1 = new boolean[this.phalanx];
/*  456 */     this.rowMin = new String[this.phalanx];
/*  457 */     this.colMin = new String[this.phalanx];
/*      */     
/*  459 */     this.answer3.clear();
/*  460 */     this.answer3_backup.clear();
/*  461 */     for (int i = 0; i < 2; i++) {
/*  462 */       this.answer4[i] = -1;
/*      */     }
/*  464 */     this.answer6.removeAllElements();
/*  465 */     this.data = new String[(this.phalanx + 2) * (this.phalanx + 1)];
/*  466 */     System.arraycopy(this.defaultdata, 0, this.data, 0, this.defaultdata.length);
/*  467 */     this.header[1] = "Task\t  A  \t  B  \t";
/*  468 */     switch (this.phalanx) {
/*      */     case 3: 
/*  470 */       int tmp169_168 = 1; String[] tmp169_165 = this.header;tmp169_165[tmp169_168] = String.valueOf(String.valueOf(tmp169_165[tmp169_168])).concat("  C  ");
/*  471 */       break;
/*      */     case 4: 
/*  473 */       int tmp191_190 = 1; String[] tmp191_187 = this.header;tmp191_187[tmp191_190] = String.valueOf(String.valueOf(tmp191_187[tmp191_190])).concat("  C  \t  D  ");
/*  474 */       break;
/*      */     case 5: 
/*  476 */       int tmp213_212 = 1; String[] tmp213_209 = this.header;tmp213_209[tmp213_212] = String.valueOf(String.valueOf(tmp213_209[tmp213_212])).concat("  C  \t  D  \t  E  ");
/*  477 */       break;
/*      */     case 6: 
/*  479 */       int tmp235_234 = 1; String[] tmp235_231 = this.header;tmp235_231[tmp235_234] = String.valueOf(String.valueOf(tmp235_231[tmp235_234])).concat("  C  \t  D  \t  E  \t  F  ");
/*      */     }
/*  481 */     this.table.leave();
/*  482 */     this.table.modifyTable(this.phalanx + 1, this.phalanx + 2);
/*  483 */     this.table.setHeader(this.header);
/*      */     
/*  485 */     String[] tempData = new String[this.data.length];
/*  486 */     for (int i = 0; i < this.data.length; i++) {
/*  487 */       if ((this.data[i].equalsIgnoreCase("1.7976931348623157E308")) || (this.data[i].equalsIgnoreCase("Infinity")))
/*      */       {
/*  489 */         tempData[i] = "M";
/*      */       }
/*  491 */       else if ((i % (this.phalanx + 2) == 0) && (i / (this.phalanx + 2) < this.phalanx)) {
/*  492 */         tempData[i] = String.valueOf(String.valueOf(new StringBuffer("").append(1 + i / (this.phalanx + 2))));
/*      */       } else {
/*  494 */         tempData[i] = this.data[i];
/*      */       }
/*      */       
/*  497 */       System.out.println(String.valueOf(String.valueOf(new StringBuffer("i= ").append(i).append("   tempData = ").append(tempData[i]))));
/*      */     }
/*  499 */     this.table.setData(tempData);
/*  500 */     this.table.InitEnd();
/*  501 */     this.robot = new Robot(this, this.phalanx);
/*  502 */     this.choice.removeAllItems();
/*  503 */     for (int i = 1; i < this.phalanx + 1; i++) {
/*  504 */       this.choice.addItem("Job ".concat(String.valueOf(String.valueOf(i))));
/*      */     }
/*  506 */     this.demo = new Demo(this, this.phalanx);
/*  507 */     this.current = 0;
/*      */   }
/*      */   
/*      */ 
/*      */   public void focusOn(int num) {}
/*      */   
/*      */ 
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/*      */     String str;
/*  517 */     if (this.auto) {
/*  518 */       String str = "Solve an Assignment Problem Automatically.";
/*  519 */       str = String.valueOf(String.valueOf(str)).concat("\n\nThis procedure automatically solves your assignment problem step by step. Please pay attention to ");
/*  520 */       str = String.valueOf(String.valueOf(str)).concat("\ninstructions on the screen for each step.");
/*      */       
/*  522 */       str = String.valueOf(String.valueOf(str)).concat("\n\nPress the Enter key to continue the current procedure.");
/*      */     } else {
/*  524 */       str = "Solve an Assignment Problem Interactively.";
/*  525 */       str = String.valueOf(String.valueOf(str)).concat("\n\nPlease follow instructions on the screen for this step to proceed.");
/*      */       
/*  527 */       str = String.valueOf(String.valueOf(str)).concat("\n\nPress the Enter key to continue the current procedure.");
/*      */     }
/*      */     
/*      */ 
/*  531 */     this.help_content_step.setText(str);
/*  532 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/*      */     String str;
/*  538 */     if (this.auto) {
/*  539 */       String str = "Solve an Assignment Problem Automatically.";
/*  540 */       str = String.valueOf(String.valueOf(str)).concat("\n\nThis procedure automatically solves your assignment problem step by step.");
/*      */       
/*  542 */       str = String.valueOf(String.valueOf(str)).concat("\n\nPress the Enter key to continue the current procedure.");
/*      */     } else {
/*  544 */       str = "Solve an Assignment Problem Interactively.";
/*  545 */       str = String.valueOf(String.valueOf(str)).concat("\n\nThis procedure helps you to solve an assignment problem interactively step by step. Please follow instructions on the screen for each step.");
/*      */       
/*  547 */       str = String.valueOf(String.valueOf(str)).concat("\n\nPress the Enter key to continue the current procedure.");
/*      */     }
/*      */     
/*  550 */     this.help_content_procedure.setText(str);
/*  551 */     this.help_content_procedure.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clickData(int num, int row, int column)
/*      */   {
/*  559 */     for (int j = 0; j < this.phalanx; j++) {
/*  560 */       for (int i = 1; i <= this.phalanx; i++) {
/*  561 */         if ((this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("1.7976931348623157E308")) || (this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("Infinity")))
/*      */         {
/*  563 */           this.table.updateData("M", i, j + 1);
/*      */         }
/*      */       }
/*      */     }
/*  567 */     this.table.repaint();
/*      */     int i;
/*  569 */     switch (this.current) {
/*      */     case 0: 
/*  571 */       if (this.data[(row * (this.phalanx + 2) + column)].equals(String.valueOf(this.currentItem.result[row][this.phalanx]))) {
/*  572 */         this.table.setDataBackgroundState(row, this.phalanx + 1, 1);
/*  573 */         if ((this.data[(row * (this.phalanx + 2) + column)].equalsIgnoreCase("1.7976931348623157E308")) || (this.data[(row * (this.phalanx + 2) + column)].equalsIgnoreCase("Infinity")))
/*      */         {
/*      */ 
/*      */ 
/*  577 */           this.table.updateData("M", row, this.phalanx + 1);
/*      */         } else {
/*  579 */           this.table.updateData(this.data[(row * (this.phalanx + 2) + column)], row, this.phalanx + 1);
/*      */         }
/*  581 */         this.answer0[row] = true;
/*  582 */         this.rowMin[row] = this.data[(row * (this.phalanx + 2) + column)];
/*      */       }
/*      */       else {
/*  585 */         Toolkit.getDefaultToolkit().beep();
/*  586 */         this.table.setDataBackgroundState(row, this.phalanx + 1, -1);
/*  587 */         this.table.updateData("", row, this.phalanx + 1);
/*  588 */         this.answer0[row] = false;
/*      */       }
/*  590 */       break;
/*      */     case 10: 
/*  592 */       if (this.data[(row * (this.phalanx + 2) + column)].equals(String.valueOf(this.currentItem.result[this.phalanx][(column - 1)])))
/*      */       {
/*      */ 
/*  595 */         this.table.setDataBackgroundState(this.phalanx, column, 1);
/*  596 */         if ((this.data[(row * (this.phalanx + 2) + column)].equalsIgnoreCase("1.7976931348623157E308")) || (this.data[(row * (this.phalanx + 2) + column)].equalsIgnoreCase("Infinity")))
/*      */         {
/*      */ 
/*  599 */           this.table.updateData("M", row, this.phalanx + 1);
/*      */         } else {
/*  601 */           this.table.updateData(this.data[(row * (this.phalanx + 2) + column)], this.phalanx, column);
/*      */         }
/*  603 */         this.answer1[(column - 1)] = true;
/*  604 */         this.colMin[(column - 1)] = this.data[(row * (this.phalanx + 2) + column)];
/*      */       }
/*      */       else {
/*  607 */         Toolkit.getDefaultToolkit().beep();
/*  608 */         this.table.setDataBackgroundState(this.phalanx, column, -1);
/*  609 */         this.table.updateData("", this.phalanx, column);
/*  610 */         this.answer1[(column - 1)] = false;
/*      */       }
/*  612 */       break;
/*      */     case 31:  int newanswer;
/*      */       int newanswer;
/*  615 */       if (column != this.phalanx + 1) {
/*  616 */         newanswer = column - 1;
/*      */       }
/*      */       else {
/*  619 */         newanswer = row + this.phalanx;
/*      */       }
/*  621 */       int flag = 0;
/*      */       
/*  623 */       for (i = 0; i < this.answer3.size(); i++) {
/*  624 */         int temp = ((Integer)this.answer3.elementAt(i)).intValue();
/*  625 */         if (temp >= newanswer)
/*      */         {
/*      */ 
/*  628 */           if (temp == newanswer) {
/*  629 */             flag = 1;
/*  630 */             break;
/*      */           }
/*  632 */           if (temp > newanswer) {
/*  633 */             flag = 2;
/*  634 */             break;
/*      */           }
/*      */         } }
/*  637 */       switch (flag) {
/*      */       case 0: 
/*  639 */         this.answer3.addElement(new Integer(newanswer));
/*  640 */         break;
/*      */       case 1: 
/*  642 */         this.answer3.removeElementAt(i);
/*  643 */         break;
/*      */       case 2: 
/*  645 */         this.answer3.insertElementAt(new Integer(newanswer), i);
/*      */       }
/*      */       
/*      */       
/*  649 */       for (i = 0; i < this.answer3.size();) {
/*  650 */         int temp = ((Integer)this.answer3.elementAt(i)).intValue();
/*  651 */         for (int j = 0; j < this.phalanx + 1; j++) {
/*  652 */           if (temp < this.phalanx) {
/*  653 */             this.table.setDataBackgroundState(j, temp + 1, 1);
/*      */           }
/*      */           else {
/*  656 */             this.table.setDataBackgroundState(temp - this.phalanx, j + 1, 1);
/*      */           }
/*      */         }
/*  649 */         i++; continue;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  663 */         float min = this.currentItem.result[this.phalanx][this.phalanx];
/*  664 */         if (this.data[(row * (this.phalanx + 2) + column)].equals(String.valueOf(min)))
/*      */         {
/*  666 */           if (this.answer4[0] != -1) {
/*  667 */             this.table.setDataBackgroundState(this.answer4[0], this.answer4[1], 1);
/*      */           }
/*  669 */           this.table.setDataBackgroundState(row, column, 2);
/*  670 */           this.answer4[0] = row;
/*  671 */           this.answer4[1] = column;
/*      */         }
/*      */         else {
/*  674 */           this.answer4[0] = -1;
/*  675 */           this.answer4[1] = -1;
/*  676 */           if (this.answer4[0] != -1) {
/*  677 */             this.table.setDataBackgroundState(this.answer4[0], this.answer4[1], 1);
/*      */           }
/*  679 */           Toolkit.getDefaultToolkit().beep();
/*      */           
/*  681 */           break;
/*      */           
/*  683 */           for (int i = 0; i < this.answer6.size(); i += 2) {
/*  684 */             if ((((Integer)this.answer6.elementAt(i)).intValue() == row) || (((Integer)this.answer6.elementAt(i + 1)).intValue() == column)) {
/*      */               break;
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*  690 */           if (i != this.answer6.size()) {
/*  691 */             Toolkit.getDefaultToolkit().beep();
/*      */           }
/*      */           else {
/*  694 */             this.answer6.addElement(new Integer(row));
/*  695 */             this.answer6.addElement(new Integer(column));
/*  696 */             for (i = 0; i < this.phalanx; i++) {
/*  697 */               this.table.setDataBackgroundState(row, i + 1, 1);
/*  698 */               this.table.setDataBackgroundState(i, column, 1);
/*      */             }
/*  700 */             this.table.setDataBackgroundState(row, column, 2);
/*      */           }
/*      */         }
/*      */       } }
/*  704 */     formatTableData();
/*  705 */     this.table.repaint();
/*      */   }
/*      */   
/*      */   void Prev_actionPerformed(ActionEvent e) {
/*  709 */     this.prevProcess = true;
/*  710 */     this.Next.setEnabled(true);
/*  711 */     if ((this.currentItem.cur == 60) && 
/*  712 */       (this.answer6.size() != 0)) {
/*  713 */       int n = this.answer6.size() - 2;
/*  714 */       this.answer6.removeElementAt(n);
/*  715 */       this.answer6.removeElementAt(n);
/*  716 */       for (int i = 0; i < this.phalanx; i++) {
/*  717 */         for (int j = 1; j < this.phalanx + 1; j++) {
/*  718 */           this.table.setDataBackgroundState(i, j, 0);
/*      */         }
/*      */       }
/*  721 */       for (int i = 0; i < this.answer6.size(); i += 2) {
/*  722 */         int row = ((Integer)this.answer6.elementAt(i)).intValue();
/*  723 */         int column = ((Integer)this.answer6.elementAt(i + 1)).intValue();
/*  724 */         for (int j = 0; j < this.phalanx; j++) {
/*  725 */           this.table.setDataBackgroundState(row, j + 1, 1);
/*  726 */           this.table.setDataBackgroundState(j, column, 1);
/*      */         }
/*  728 */         this.table.setDataBackgroundState(row, column, 2);
/*      */       }
/*  730 */       this.table.repaint();
/*  731 */       for (int i = 0; i < this.phalanx; i++) {
/*  732 */         for (int j = 0; j < this.phalanx; j++) {
/*  733 */           this.table.setDataClickState(i, j + 1, true);
/*      */         }
/*      */       }
/*  736 */       return;
/*      */     }
/*      */     
/*  739 */     if (this.currentItem.prev == -1) {
/*  740 */       this.Prev.setEnabled(false);
/*  741 */       return;
/*      */     }
/*  743 */     if (this.current == 42) {
/*  744 */       this.current = 41;
/*      */     }
/*  746 */     else if (this.current == 32) {
/*  747 */       this.current = 31;
/*      */     }
/*      */     else {
/*  750 */       this.robot.prev(0);
/*  751 */       this.current = this.currentItem.cur;
/*      */     }
/*  753 */     if ((this.current == 0) || (this.current == 31) || (this.current == 60))
/*  754 */       this.controller.solver.removePrintData();
/*  755 */     if ((this.current == 41) || (this.current == 42) || (this.current == 10)) {
/*  756 */       this.controller.solver.removePrintData();
/*  757 */       this.controller.solver.removePrintData();
/*      */     }
/*  759 */     if (this.current == 31) {
/*  760 */       this.answer3 = ((Vector)this.answer3_backup.elementAt(this.answer3_backup.size() - 1));
/*  761 */       this.answer3_backup.remove(this.answer3_backup.size() - 1);
/*      */     }
/*  763 */     step(this.current);
/*  764 */     formatTableData();
/*      */   }
/*      */   
/*      */   void DemoButton_actionPerformed(ActionEvent e)
/*      */   {
/*  769 */     if (this.demo.isAlive()) {
/*  770 */       this.demo.pause = (!this.demo.pause);
/*  771 */       if (this.demo.pause) {
/*  772 */         this.DemoButton.setText("Auto");
/*  773 */         this.demo.pauseAndContinue = true;
/*  774 */         this.auto_reset.setEnabled(true);
/*      */       }
/*      */       else {
/*  777 */         this.auto_reset.setEnabled(false);
/*  778 */         this.DemoButton.setText("Pause");
/*      */       }
/*      */     }
/*      */     else {
/*  782 */       if (this.demo.finish) {
/*  783 */         this.demo = new Demo(this, this.phalanx);
/*  784 */         this.controller.solver.emptyPrintData();
/*  785 */         setAdd_Delete(true);
/*  786 */         this.Next.setEnabled(false);
/*  787 */         this.DemoButton.setEnabled(false);
/*  788 */         this.Prev.setEnabled(false);
/*  789 */         if (this.demo.isAlive()) {
/*  790 */           this.demo.stop();
/*      */         }
/*  792 */         this.DemoButton.setEnabled(true);
/*  793 */         reset();
/*  794 */         this.table.repaint();
/*  795 */         this.auto_reset.setEnabled(true);
/*  796 */         step(-1);
/*      */       }
/*  798 */       this.DemoButton.setText("Pause");
/*  799 */       if (!this.demo.pauseAndContinue)
/*  800 */         reset();
/*  801 */       this.auto_reset.setEnabled(false);
/*      */       
/*  803 */       this.demo.start();
/*      */     }
/*      */   }
/*      */   
/*      */   void Add_actionPerformed(ActionEvent e) {
/*  808 */     add();
/*      */   }
/*      */   
/*      */   public void add() {
/*  812 */     this.phalanx += 1;
/*  813 */     this.defaultdata = this.data;
/*  814 */     int num = (this.phalanx + 1) * (this.phalanx + 2);
/*  815 */     String[] temp = new String[num];
/*  816 */     for (int i = 0; i < num; i++) {
/*  817 */       temp[i] = "";
/*      */     }
/*  819 */     for (int i = 0; i < this.phalanx - 1; i++) {
/*  820 */       for (int j = 0; j < this.phalanx; j++) {
/*  821 */         temp[(i * (this.phalanx + 2) + j)] = this.defaultdata[(i * (this.phalanx + 1) + j)];
/*      */       }
/*      */     }
/*  824 */     for (int i = 0; i < this.phalanx; i++) {
/*  825 */       temp[(i * (this.phalanx + 2) + this.phalanx)] = "1";
/*  826 */       temp[((this.phalanx - 1) * (this.phalanx + 2) + i)] = "1";
/*      */     }
/*  828 */     temp[((this.phalanx - 1) * (this.phalanx + 2))] = String.valueOf(this.phalanx);
/*  829 */     this.defaultdata = temp;
/*  830 */     reset();
/*  831 */     this.table.repaint();
/*  832 */     step(-1);
/*  833 */     setAdd_Delete(true);
/*      */   }
/*      */   
/*      */   void Delete_actionPerformed(ActionEvent e)
/*      */   {
/*  838 */     this.defaultdata = this.data;
/*  839 */     this.phalanx -= 1;
/*  840 */     int delete = this.choice.getSelectedIndex();
/*  841 */     int num = (this.phalanx + 1) * (this.phalanx + 2);
/*  842 */     String[] temp = new String[num];
/*  843 */     for (int i = 0; i < num; i++) {
/*  844 */       temp[i] = "";
/*      */     }
/*  846 */     for (int i = 0; i < this.phalanx; i++) {
/*  847 */       temp[(i * (this.phalanx + 2))] = String.valueOf(i + 1);
/*      */     }
/*  849 */     int a = -1;int b = 0;
/*  850 */     for (int i = 0; i < this.phalanx + 1; i++)
/*  851 */       if (i != delete)
/*      */       {
/*      */ 
/*  854 */         a++;
/*  855 */         b = 0;
/*  856 */         for (int j = 1; j <= this.phalanx + 1; j++)
/*  857 */           if (j != delete + 1)
/*      */           {
/*      */ 
/*  860 */             b++;
/*  861 */             temp[(a * (this.phalanx + 2) + b)] = this.defaultdata[(i * (this.phalanx + 3) + j)];
/*      */           }
/*      */       }
/*  864 */     this.defaultdata = temp;
/*  865 */     reset();
/*      */     
/*      */ 
/*  868 */     this.table.repaint();
/*  869 */     step(-1);
/*  870 */     setAdd_Delete(true);
/*      */   }
/*      */   
/*      */   void Yes_actionPerformed(ActionEvent e) {
/*  874 */     if ((this.currentItem.next == 60) && (this.robot.examine3(this.answer3))) {
/*  875 */       Next_actionPerformed(null);
/*      */     }
/*      */     else {
/*  878 */       new MessageDialog(MessageDialog.getFrame(this), "Sorry, your answer is wrong, please try again.", null).setVisible(true);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   void No_actionPerformed(ActionEvent e)
/*      */   {
/*  885 */     if ((this.answer3.size() != this.phalanx) && (this.robot.examine3(this.answer3))) {
/*  886 */       Next_actionPerformed(null);
/*      */     }
/*      */     else {
/*  889 */       new MessageDialog(MessageDialog.getFrame(this), "Sorry, your answer is wrong, please try again.", null).setVisible(true);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private String[][] transferDataToArray()
/*      */   {
/*  896 */     String[][] arrayData = new String[this.phalanx + 1][this.phalanx + 2];
/*  897 */     for (int i = 0; i < this.phalanx + 1; i++) {
/*  898 */       for (int j = 0; j < this.phalanx + 2; j++) {
/*  899 */         arrayData[i][j] = this.data[(i * (this.phalanx + 2) + j)];
/*      */       }
/*      */     }
/*  902 */     return arrayData;
/*      */   }
/*      */   
/*      */   void Next_actionPerformed(ActionEvent e) {
/*  906 */     boolean ok = true;
/*  907 */     this.prevProcess = false;
/*  908 */     this.hungarianData = this.controller.getDataInstance();
/*  909 */     switch (this.current) {
/*      */     case 0: 
/*  911 */       for (int i = 0; i < this.phalanx; i++) {
/*  912 */         ok &= this.answer0[i];
/*      */       }
/*  914 */       if (!ok) {
/*  915 */         new MessageDialog(MessageDialog.getFrame(this), "Sorry, your answer is wrong, please try again.", null).setVisible(true);
/*      */         
/*      */ 
/*  918 */         return;
/*      */       }
/*  920 */       this.hungarianData.data = transferDataToArray();
/*  921 */       for (int i = 0; i < this.rowMin.length; i++) {
/*  922 */         this.hungarianData.data[i][(this.phalanx + 1)] = this.rowMin[i];
/*      */       }
/*  924 */       this.hungarianData.currentStep = this.current;
/*  925 */       this.hungarianData.taskNumber = this.phalanx;
/*  926 */       this.controller.solver.insertPrintData(this.hungarianData);
/*  927 */       break;
/*      */     case 10: 
/*  929 */       for (int i = 0; i < this.phalanx; i++) {
/*  930 */         ok &= this.answer1[i];
/*      */       }
/*  932 */       if (!ok) {
/*  933 */         new MessageDialog(MessageDialog.getFrame(this), "Sorry, your answer is wrong, please try again.", null).setVisible(true);
/*      */         
/*      */ 
/*  936 */         return;
/*      */       }
/*  938 */       this.hungarianData.data = transferDataToArray();
/*  939 */       this.hungarianData.data[this.phalanx][0] = "Col Min";
/*  940 */       for (int i = 0; i < this.colMin.length; i++) {
/*  941 */         this.hungarianData.data[this.phalanx][(i + 1)] = this.colMin[i];
/*      */       }
/*  943 */       this.hungarianData.currentStep = this.current;
/*  944 */       this.hungarianData.taskNumber = this.phalanx;
/*  945 */       this.controller.solver.insertPrintData(this.hungarianData);
/*  946 */       break;
/*      */     case 31: 
/*  948 */       if (!this.robot.examine3(this.answer3)) {
/*  949 */         new MessageDialog(MessageDialog.getFrame(this), "Sorry, your answer is wrong, please try again.", null).setVisible(true);
/*      */         
/*      */ 
/*  952 */         return;
/*      */       }
/*  954 */       this.hungarianData.data = transferDataToArray();
/*  955 */       this.hungarianData.currentStep = this.current;
/*  956 */       this.hungarianData.taskNumber = this.phalanx;
/*  957 */       this.hungarianData.selectLines = new int[this.answer3.size()];
/*  958 */       for (int i = 0; i < this.answer3.size(); i++) {
/*  959 */         this.hungarianData.selectLines[i] = ((Integer)this.answer3.elementAt(i)).intValue();
/*      */       }
/*  961 */       this.controller.solver.insertPrintData(this.hungarianData);
/*  962 */       break;
/*      */     case 41: 
/*  964 */       float min = this.currentItem.result[this.phalanx][this.phalanx];
/*      */       try {
/*  966 */         ok &= this.data[(this.answer4[0] * (this.phalanx + 2) + this.answer4[1])].equals(String.valueOf(min));
/*      */       }
/*      */       catch (Exception ex) {
/*  969 */         new MessageDialog(MessageDialog.getFrame(this), "Sorry, your answer is wrong, please try again.", null).setVisible(true);
/*      */         
/*      */ 
/*  972 */         return;
/*      */       }
/*  974 */       if (!ok) {
/*  975 */         new MessageDialog(MessageDialog.getFrame(this), "Sorry, your answer is wrong, please try again.", null).setVisible(true);
/*      */         
/*      */ 
/*  978 */         return;
/*      */       }
/*  980 */       int temp1 = this.answer4[0] * (this.phalanx + 2) + this.answer4[1];
/*  981 */       this.hungarianData.min = new Point(temp1 / (this.phalanx + 2), temp1 % (this.phalanx + 2));
/*  982 */       this.hungarianData.data = transferDataToArray();
/*  983 */       this.hungarianData.currentStep = this.current;
/*  984 */       this.hungarianData.taskNumber = this.phalanx;
/*  985 */       this.hungarianData.selectPoint = this.step41;
/*  986 */       this.controller.solver.insertPrintData(this.hungarianData);
/*  987 */       break;
/*      */     case 60: 
/*  989 */       if (!this.robot.examine6(this.answer6)) {
/*  990 */         new MessageDialog(MessageDialog.getFrame(this), "Sorry, your answer is wrong, please try again.", null).setVisible(true);
/*      */         
/*      */ 
/*  993 */         return;
/*      */       }
/*  995 */       this.hungarianData.data = transferDataToArray();
/*  996 */       this.hungarianData.currentStep = this.current;
/*  997 */       this.hungarianData.taskNumber = this.phalanx;
/*  998 */       this.hungarianData.bestAssign = new Point[this.phalanx];
/*  999 */       for (int i = 0; i < this.phalanx; i++) {
/* 1000 */         Point temp = new Point();
/* 1001 */         temp.x = ((Integer)this.answer6.elementAt(i * 2)).intValue();
/* 1002 */         temp.y = ((Integer)this.answer6.elementAt(i * 2 + 1)).intValue();
/* 1003 */         this.hungarianData.bestAssign[i] = temp;
/*      */       }
/* 1005 */       this.controller.solver.insertPrintData(this.hungarianData);
/* 1006 */       break;
/*      */     }
/* 1008 */     this.Prev.setEnabled(true);
/* 1009 */     if (this.currentItem.next == 80) {
/* 1010 */       this.Next.setEnabled(false);
/* 1011 */       for (int i = 0; i < this.phalanx + 1; i++) {
/* 1012 */         for (int j = 0; j < this.phalanx + 2; j++) {
/* 1013 */           this.table.setDataClickState(i, j, false);
/* 1014 */           this.table.setDataInputState(i, j, false);
/*      */         }
/*      */       }
/* 1017 */       return;
/*      */     }
/*      */     
/* 1020 */     if (this.current == 31) {
/* 1021 */       this.current = 32;
/*      */     }
/* 1023 */     else if (this.current == 41) {
/* 1024 */       this.current = 42;
/*      */     }
/*      */     else {
/* 1027 */       this.robot.next(0);
/* 1028 */       if (this.current == 42) {
/* 1029 */         this.hungarianData.data = transferDataToArray();
/* 1030 */         this.hungarianData.currentStep = this.current;
/* 1031 */         this.hungarianData.taskNumber = this.phalanx;
/* 1032 */         this.hungarianData.selectPoint = this.step42;
/* 1033 */         this.controller.solver.insertPrintData(this.hungarianData);
/*      */       }
/* 1035 */       if (this.current == 10) {
/* 1036 */         IORHungarianProcessController.HungarianData hungarianData1 = this.controller.getDataInstance();
/* 1037 */         hungarianData1.data = transferDataToArray();
/* 1038 */         hungarianData1.currentStep = 11;
/* 1039 */         hungarianData1.taskNumber = this.phalanx;
/* 1040 */         this.controller.solver.insertPrintData(hungarianData1);
/*      */       }
/* 1042 */       this.current = this.currentItem.cur;
/*      */     }
/* 1044 */     if (this.current == 32) {
/* 1045 */       this.answer3_backup.addElement(this.answer3.clone());
/*      */     }
/* 1047 */     step(this.current);
/*      */     
/* 1049 */     formatTableData();
/*      */   }
/*      */   
/*      */   private void formatTableData()
/*      */   {
/* 1054 */     for (int j = 0; j < this.phalanx; j++) {
/* 1055 */       for (int i = 0; i <= this.phalanx; i++) {
/* 1056 */         if ((this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("1.7976931348623157E308")) || (this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("Infinity")))
/*      */         {
/* 1058 */           this.table.updateData("M", i, j + 1);
/*      */         }
/* 1060 */         else if (this.data[(i * (this.phalanx + 2) + j + 1)].length() >= 6) {
/*      */           try {
/* 1062 */             double temp = Double.valueOf(this.data[(i * (this.phalanx + 2) + j + 1)]).doubleValue();
/* 1063 */             this.table.updateData(this.nf.format(temp), i, j + 1);
/*      */           } catch (Exception e1) {
/* 1065 */             System.out.println("NumberFormat Exception!");
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1073 */     this.table.repaint();
/*      */   }
/*      */   
/*      */   void auto_Reset_actionPerformed(ActionEvent e) {
/* 1077 */     this.table.leave();
/* 1078 */     setAdd_Delete(true);
/* 1079 */     this.Next.setEnabled(false);
/* 1080 */     this.Prev.setEnabled(false);
/* 1081 */     this.DemoButton.setEnabled(true);
/* 1082 */     if (this.demo.isAlive()) {
/* 1083 */       this.demo.stop();
/*      */     }
/* 1085 */     this.DemoButton.setEnabled(true);
/* 1086 */     reset();
/* 1087 */     this.table.repaint();
/* 1088 */     step(-1);
/* 1089 */     this.table.repaint();
/* 1090 */     this.controller.solver.emptyPrintData();
/*      */   }
/*      */   
/*      */   void Reset_actionPerformed(ActionEvent e)
/*      */   {
/* 1095 */     this.table.leave();
/* 1096 */     if (this.Reset.getText().equals("Start")) {
/* 1097 */       setAdd_Delete(false);
/* 1098 */       this.Next.setEnabled(true);
/* 1099 */       this.Prev.setEnabled(false);
/* 1100 */       this.DemoButton.setEnabled(true);
/*      */       
/* 1102 */       this.controller.tableData.initData = new String[this.data.length];
/* 1103 */       System.arraycopy(this.data, 0, this.defaultdata, 0, this.data.length);
/* 1104 */       System.arraycopy(this.data, 0, this.controller.tableData.initData, 0, this.data.length);
/* 1105 */       this.robot.init(this.data);
/* 1106 */       step(0);
/* 1107 */       this.robot.next(0);
/* 1108 */       this.current = this.currentItem.cur;
/* 1109 */       this.Reset.setText("Reset");
/*      */     }
/*      */     else
/*      */     {
/* 1113 */       setAdd_Delete(true);
/* 1114 */       this.Next.setEnabled(false);
/* 1115 */       this.Prev.setEnabled(false);
/* 1116 */       this.DemoButton.setEnabled(false);
/* 1117 */       if (this.demo.isAlive()) {
/* 1118 */         this.demo.stop();
/*      */       }
/* 1120 */       this.DemoButton.setEnabled(true);
/* 1121 */       reset();
/* 1122 */       this.table.repaint();
/* 1123 */       this.Reset.setText("Start");
/* 1124 */       step(-1);
/* 1125 */       this.controller.solver.emptyPrintData();
/* 1126 */       this.DemoButton.setEnabled(false);
/*      */     }
/* 1128 */     formatTableData();
/* 1129 */     this.table.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void step(int current)
/*      */   {
/* 1138 */     this.table.renameHead("");
/* 1139 */     this.table.updateData("", this.phalanx, 0);
/* 1140 */     this.Yes.setVisible(false);
/* 1141 */     this.No.setVisible(false);
/* 1142 */     for (int j = 0; j < this.phalanx + 1; j++) {
/* 1143 */       for (int i = 0; i < this.phalanx + 2; i++) {
/* 1144 */         this.table.setDataClickState(j, i, false);
/* 1145 */         this.table.setDataInputState(j, i, false);
/* 1146 */         this.table.setDataBackgroundState(j, i, new Color(250, 240, 185));
/*      */       }
/*      */     }
/* 1149 */     switch (current) {
/*      */     case -1: 
/* 1151 */       step_1();
/* 1152 */       break;
/*      */     case 0: 
/* 1154 */       step0();
/* 1155 */       break;
/*      */     case 10: 
/* 1157 */       step10();
/* 1158 */       break;
/*      */     case 31: 
/* 1160 */       step31();
/* 1161 */       break;
/*      */     case 32: 
/* 1163 */       step32();
/* 1164 */       break;
/*      */     case 41: 
/* 1166 */       step41();
/* 1167 */       break;
/*      */     case 42: 
/* 1169 */       step42();
/* 1170 */       break;
/*      */     case 60: 
/* 1172 */       step60();
/* 1173 */       break;
/*      */     case 70: 
/* 1175 */       step70();
/*      */     }
/*      */     
/*      */     
/* 1179 */     for (int j = 0; j < this.phalanx; j++) {
/* 1180 */       for (int i = 1; i <= this.phalanx; i++) {
/* 1181 */         if ((this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("1.7976931348623157E308")) || (this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("Infinity")))
/*      */         {
/* 1183 */           this.table.updateData("M", i, j + 1);
/*      */         }
/*      */       }
/*      */     }
/* 1187 */     this.table.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void step_1()
/*      */   {
/* 1194 */     this.Message.message("To use the Hungarian algorithm to solve an assignment problem, enter the\nproblem by using the Add or Delete button to adjust the number of assignees\nand tasks (maximum of 6) as needed and then edit the values in the gray \ncells. (Each cell can only accept either M or a value between 0 and 999.)\nThen click the Start button and follow the new instructions to solve the problem.");
/* 1195 */     this.helpContent = "To use the Hungarian algorithm to solve an assignment problem, enter the\nproblem by using the Add or Delete button to adjust the number of assignees\nand tasks (maximum of 6) as needed and then edit the values in the gray \ncells. (Each cell can only accept either M or a value between 0 and 999.)\nThen click the Start button and follow the new instructions to solve the problem.";
/* 1196 */     this.step = 1;
/* 1197 */     for (int j = 0; j < this.phalanx; j++) {
/* 1198 */       for (int i = 1; i <= this.phalanx; i++) {
/* 1199 */         if ((this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("1.7976931348623157E308")) || (this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("Infinity")))
/*      */         {
/*      */ 
/* 1202 */           this.table.updateData("M", i, j + 1);
/*      */         }
/* 1204 */         this.table.setDataBackgroundState(j, i, 1);
/* 1205 */         this.table.setDataInputState(j, i, true, 1);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void step0() {
/* 1211 */     this.table.renameHead("Row Min.");
/* 1212 */     this.step = 0;
/* 1213 */     this.Message.message("Select the smallest number in each row by clicking on the \ncorresponding gray cell in that row.");
/* 1214 */     this.helpContent = "Select the smallest number in each row by clicking on the \ncorresponding gray cell in that row.";
/* 1215 */     for (int i = 0; i < this.phalanx; i++) {
/* 1216 */       for (int j = 0; j < this.phalanx; j++) {
/* 1217 */         this.table.setDataClickState(i, j + 1, true);
/*      */         
/* 1219 */         if ((this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("1.7976931348623157E308")) || (this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("Infinity")))
/*      */         {
/* 1221 */           this.table.updateData("M", i, j + 1);
/*      */         } else {
/* 1223 */           this.table.updateData(this.data[(i * (this.phalanx + 2) + j + 1)], i, j + 1);
/*      */         }
/* 1225 */         this.table.setDataBackgroundState(i, j + 1, 1);
/*      */       }
/* 1227 */       this.answer0[i] = false;
/* 1228 */       this.table.updateData("", this.phalanx, i + 1);
/* 1229 */       this.table.updateData("", i, this.phalanx + 1);
/*      */     }
/*      */   }
/*      */   
/*      */   private void step10() {
/* 1234 */     this.step = 10;
/* 1235 */     this.table.updateData("Col. Min.", this.phalanx, 0);
/* 1236 */     this.Message.message("Select the smallest number in each column by clicking on the \ncorresponding gray cell in that column.");
/* 1237 */     this.helpContent = "Select the smallest number in each column by clicking on the \ncorresponding gray cell in that column.";
/* 1238 */     for (int i = 0; i < this.phalanx; i++) {
/* 1239 */       for (int j = 0; j < this.phalanx; j++) {
/* 1240 */         this.table.setDataClickState(i, j + 1, true);
/* 1241 */         if ((this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("1.7976931348623157E308")) || (this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("Infinity")))
/*      */         {
/* 1243 */           this.table.updateData("M", i, j + 1);
/*      */         } else {
/* 1245 */           this.table.updateData(this.data[(i * (this.phalanx + 2) + j + 1)], i, j + 1);
/*      */         }
/* 1247 */         this.table.setDataBackgroundState(i, j + 1, 1);
/*      */       }
/* 1249 */       this.answer1[i] = false;
/* 1250 */       this.table.updateData("", this.phalanx, i + 1);
/* 1251 */       this.table.updateData("", i, this.phalanx + 1);
/*      */     }
/*      */   }
/*      */   
/*      */   private void step31() {
/* 1256 */     this.step = 31;
/* 1257 */     this.table.updateData("Col. Sel.", this.phalanx, 0);
/* 1258 */     this.table.renameHead("Row Sel.");
/* 1259 */     this.Next.setEnabled(true);
/* 1260 */     this.Message.message("Select the minimum number of lines needed to cover \n(i.e.,cross out) all zeros in the table by clicking \nthe corresponding 'S' cell.");
/* 1261 */     this.helpContent = "Select the minimum number of lines needed to cover \n(i.e.,cross out) all zeros in the table by clicking \nthe corresponding 'S' cell.";
/* 1262 */     this.Yes.setVisible(false);
/* 1263 */     this.No.setVisible(false);
/* 1264 */     if (!this.prevProcess) {
/* 1265 */       this.answer3.clear();
/*      */     }
/* 1267 */     for (int i = 0; i < this.phalanx; i++) {
/* 1268 */       for (int j = 0; j < this.phalanx; j++) {
/* 1269 */         if ((this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("1.7976931348623157E308")) || (this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("Infinity")))
/*      */         {
/* 1271 */           this.table.updateData("M", i, j + 1);
/*      */         } else {
/* 1273 */           this.table.updateData(this.data[(i * (this.phalanx + 2) + j + 1)], i, j + 1);
/*      */         }
/*      */       }
/* 1276 */       this.table.updateData("S", this.phalanx, i + 1);
/* 1277 */       this.table.updateData("S", i, this.phalanx + 1);
/* 1278 */       this.table.setDataClickState(this.phalanx, i + 1, true);
/* 1279 */       this.table.setDataClickState(i, this.phalanx + 1, true);
/* 1280 */       if (this.prevProcess) {
/* 1281 */         if (this.currentItem.result[i][this.phalanx] == -1) {
/* 1282 */           for (int j = 1; j <= this.phalanx + 1; j++) {
/* 1283 */             this.table.setDataBackgroundState(i, j, 1);
/*      */           }
/*      */         }
/* 1286 */         if (this.currentItem.result[this.phalanx][i] == -1) {
/* 1287 */           for (int j = 0; j < this.phalanx + 1; j++) {
/* 1288 */             this.table.setDataBackgroundState(j, i + 1, 1);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void step32() {
/* 1296 */     this.step = 32;
/* 1297 */     this.table.updateData("Col. Sel.", this.phalanx, 0);
/* 1298 */     this.table.renameHead("Row Sel.");
/* 1299 */     this.Next.setEnabled(false);
/* 1300 */     this.Message.message("Does the number of selected lines equal number of \nrows for assignees in the table?");
/* 1301 */     this.helpContent = "Does the number of selected lines equal number of \nrows for assigness in the table?";
/* 1302 */     this.Yes.setVisible(true);
/* 1303 */     this.No.setVisible(true);
/* 1304 */     for (int i = 0; i < this.answer3.size(); i++) {
/* 1305 */       int temp = ((Integer)this.answer3.elementAt(i)).intValue();
/* 1306 */       if (temp < this.phalanx) {
/* 1307 */         for (int j = 0; j < this.phalanx + 1; j++) {
/* 1308 */           this.table.setDataBackgroundState(j, temp + 1, 1);
/*      */         }
/*      */       }
/*      */       
/* 1312 */       for (int j = 1; j <= this.phalanx + 1; j++) {
/* 1313 */         this.table.setDataBackgroundState(temp - this.phalanx, j, 1);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void step41()
/*      */   {
/* 1320 */     this.step = 41;
/* 1321 */     this.Next.setEnabled(true);
/* 1322 */     this.Message.message("Select the smallest number from all the uncovered numbers\n(i.e., the gray cells) in the table.");
/* 1323 */     this.helpContent = "Select the smallest number from all the uncovered numbers\n(i.e., the gray cells) in the table.";
/* 1324 */     this.Yes.setVisible(false);
/* 1325 */     this.No.setVisible(false);
/* 1326 */     this.answer4[0] = (this.answer4[1] = -1);
/* 1327 */     this.step41 = new int[this.phalanx + 1][this.phalanx + 2];
/* 1328 */     for (int i = 0; i < this.phalanx; i++) {
/* 1329 */       for (int j = 0; j < this.phalanx; j++) {
/* 1330 */         if ((this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("1.7976931348623157E308")) || (this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("Infinity")))
/*      */         {
/* 1332 */           this.table.updateData("M", i, j + 1);
/*      */         } else {
/* 1334 */           this.table.updateData(this.data[(i * (this.phalanx + 2) + j + 1)], i, j + 1);
/*      */         }
/* 1336 */         if (this.currentItem.result[i][j] == 0) {
/* 1337 */           this.step41[i][(j + 1)] = 1;
/* 1338 */           this.table.setDataBackgroundState(i, j + 1, 1);
/* 1339 */           this.table.setDataClickState(i, j + 1, true);
/*      */         }
/*      */       }
/* 1342 */       this.table.updateData("", i, this.phalanx + 1);
/* 1343 */       this.table.updateData("", this.phalanx, i + 1);
/*      */     }
/*      */   }
/*      */   
/*      */   private void step42() {
/* 1348 */     this.step = 42;
/* 1349 */     this.step42 = new int[this.phalanx + 1][this.phalanx + 2];
/* 1350 */     this.Message.message("When you click the Next button, the number selected at the\npreceding step will be subtracted from every uncovered number\n(the gray cells) and added to every number at\n the intersection of covering lines (the cyan cells) \nautomatically. Click the Next button to continue.");
/* 1351 */     this.helpContent = "When you click the Next button, the number selected at the\npreceding step will be subtracted from every uncovered number\n(the gray cells) and added to every number at\n the intersection of covering lines (the cyan cells) \nautomatically. Click the Next button to continue.";
/* 1352 */     for (int i = 0; i < this.phalanx; i++) {
/* 1353 */       for (int j = 0; j < this.phalanx; j++) {
/* 1354 */         if ((this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("1.7976931348623157E308")) || (this.data[(i * (this.phalanx + 2) + j + 1)].equalsIgnoreCase("Infinity")))
/*      */         {
/* 1356 */           this.table.updateData("M", i, j + 1);
/*      */         } else {
/* 1358 */           this.table.updateData(this.data[(i * (this.phalanx + 2) + j + 1)], i, j + 1);
/*      */         }
/* 1360 */         if (this.currentItem.result[i][j] == 0) {
/* 1361 */           this.table.setDataBackgroundState(i, j + 1, 1);
/* 1362 */           this.step42[i][(j + 1)] = 1;
/*      */         }
/* 1364 */         else if (this.currentItem.result[i][j] == 2) {
/* 1365 */           this.table.setDataBackgroundState(i, j + 1, 2);
/* 1366 */           this.step42[i][(j + 1)] = 2;
/*      */         }
/*      */       }
/* 1369 */       this.table.updateData("", i, this.phalanx + 1);
/* 1370 */       this.table.updateData("", this.phalanx, i + 1);
/*      */     }
/*      */   }
/*      */   
/*      */   private void step60() {
/* 1375 */     this.step = 60;
/*      */     
/*      */ 
/*      */ 
/* 1379 */     this.Message.message("Make the assignments by clicking on the corresponding zero cells. Begin with \nrows or columns that have only one zero. Select only one zero cell for each row\nand each column.");
/* 1380 */     this.helpContent = "Make the assignments by clicking on the corresponding zero cells. Begin with \nrows or columns that have only one zero. Select only one zero cell for each row\nand each column.";
/*      */     
/* 1382 */     this.Yes.setVisible(false);
/* 1383 */     this.No.setVisible(false);
/* 1384 */     this.Next.setEnabled(true);
/* 1385 */     this.answer6.removeAllElements();
/* 1386 */     for (int i = 0; i < this.phalanx; i++) {
/* 1387 */       for (int j = 0; j < this.phalanx; j++) {
/* 1388 */         this.table.setDataClickState(i, j + 1, true);
/*      */       }
/* 1390 */       this.table.updateData("", i, this.phalanx + 1);
/* 1391 */       this.table.updateData("", this.phalanx, i + 1);
/*      */     }
/*      */   }
/*      */   
/*      */   private void step70() {
/* 1396 */     this.step = 70;
/* 1397 */     String str = "You get a correct answer: \n";
/* 1398 */     this.hungarianData.bestSolution = new String[this.phalanx];
/* 1399 */     for (int i = 0; i < this.phalanx; i++) {
/* 1400 */       for (int j = 0; j < this.phalanx; j++) {
/* 1401 */         if (this.currentItem.result[i][j] == -1) {
/* 1402 */           String job = "";String machine = "";
/* 1403 */           switch (j) {
/*      */           case 0: 
/* 1405 */             machine = "A";
/* 1406 */             break;
/*      */           case 1: 
/* 1408 */             machine = "B";
/* 1409 */             break;
/*      */           case 2: 
/* 1411 */             machine = "C";
/* 1412 */             break;
/*      */           case 3: 
/* 1414 */             machine = "D";
/* 1415 */             break;
/*      */           case 4: 
/* 1417 */             machine = "E";
/* 1418 */             break;
/*      */           case 5: 
/* 1420 */             machine = "F";
/*      */           }
/*      */           
/* 1423 */           switch (i) {
/*      */           case 0: 
/* 1425 */             job = "1";
/* 1426 */             break;
/*      */           case 1: 
/* 1428 */             job = "2";
/* 1429 */             break;
/*      */           case 2: 
/* 1431 */             job = "3";
/* 1432 */             break;
/*      */           case 3: 
/* 1434 */             job = "4";
/* 1435 */             break;
/*      */           case 4: 
/* 1437 */             job = "5";
/* 1438 */             break;
/*      */           case 5: 
/* 1440 */             job = "6";
/*      */           }
/*      */           
/* 1443 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("Task ").append(machine).append(" is assigned to Assignee ").append(job).append(",\n"))))));
/*      */           
/* 1445 */           this.table.setDataBackgroundState(i, j + 1, 2);
/* 1446 */           this.hungarianData.bestSolution[i] = String.valueOf(String.valueOf(new StringBuffer("Task ").append(machine).append(" is assigned to Assignee ").append(job)));
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1451 */     str = str.substring(0, str.length() - 2);
/* 1452 */     str = String.valueOf(String.valueOf(str)).concat(".");
/* 1453 */     this.Message.message(str);
/* 1454 */     this.helpContent = str;
/* 1455 */     this.Next.setEnabled(false);
/*      */   }
/*      */   
/*      */   public void updateData(int num, String value, int row, int column) {
/*      */     try {
/* 1460 */       float temp = Float.valueOf(value).floatValue();
/* 1461 */       if ((temp < 0) || (temp > 'ϧ')) {
/* 1462 */         return;
/*      */       }
/* 1464 */       this.data[(row * (this.phalanx + 2) + column)] = value;
/* 1465 */       this.table.updateData(value, row, column);
/* 1466 */       this.table.repaint();
/*      */     } catch (Exception e) {
/* 1468 */       if (value.toLowerCase().equalsIgnoreCase("m")) {
/* 1469 */         this.table.updateData(value, row, column);
/* 1470 */         this.data[(row * (this.phalanx + 2) + column)] = "1.7976931348623157E308";
/* 1471 */         this.table.repaint();
/*      */       }
/* 1473 */       return;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updateData(int num, float value, int row, int column) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updateData(int num, int value, int row, int column) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void updateData(Robot.HistoryItem item)
/*      */   {
/* 1494 */     for (int j = 0; j < this.phalanx; j++) {
/* 1495 */       for (int i = 0; i < this.phalanx; i++) {
/* 1496 */         this.data[(j * (this.phalanx + 2) + i + 1)] = String.valueOf(item.data[j][i]);
/*      */       }
/*      */     }
/*      */     
/* 1500 */     this.currentItem = item;
/*      */   }
/*      */   
/*      */   void setAdd_Delete(boolean flag) {
/* 1504 */     this.Add.setEnabled(false);
/* 1505 */     this.Delete.setEnabled(false);
/* 1506 */     this.choice.setEnabled(flag);
/* 1507 */     if (flag) {
/* 1508 */       if (this.phalanx != 3) {
/* 1509 */         this.Delete.setEnabled(true);
/*      */       }
/* 1511 */       if (this.phalanx != 6) {
/* 1512 */         this.Add.setEnabled(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORHungarianPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */