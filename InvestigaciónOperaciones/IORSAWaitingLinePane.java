/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JScrollPane;
/*     */ 
/*     */ public class IORSAWaitingLinePane extends IORActionPanel implements java.awt.event.ActionListener
/*     */ {
/*     */   IORSAProcessController controller;
/*  22 */   boolean isStandalone = false;
/*     */   JButton start;
/*     */   JButton stop;
/*     */   JButton pause;
/*     */   java.awt.Image img;
/*     */   Graphics gs;
/*  28 */   Dimension dim; public int sleepTime; int u = 4;
/*  29 */   JComboBox uValue = new JComboBox();
/*  30 */   int a = 4;
/*  31 */   JComboBox aValue = new JComboBox();
/*  32 */   JComboBox c = new JComboBox();
/*  33 */   JComboBox c2 = new JComboBox();
/*  34 */   JComboBox speed = new JComboBox();
/*     */   JRadioButton oneChanel;
/*     */   JRadioButton twoChanel;
/*  37 */   int phaseNum = 2;
/*  38 */   phaseSA phasePan1 = new phaseSA(this);
/*  39 */   phase2SA phasePan2 = new phase2SA(this);
/*  40 */   boolean flagS2 = true;
/*     */   
/*  42 */   JScrollPane AppPane = new JScrollPane();
/*  43 */   JPanel mainPan = new JPanel();
/*     */   
/*  45 */   JLabel ns1Lab = new JLabel("#Servers at 1:");
/*  46 */   JLabel ns2Lab = new JLabel("#Servers at 2:");
/*  47 */   JLabel speedLab = new JLabel("Speed:");
/*  48 */   JLabel arrivalRateLab = new JLabel("Arrival Rate:");
/*  49 */   JLabel serviceRateLab = new JLabel("Service Rate:");
/*  50 */   JLabel numberLab = new JLabel("Number of Stages:");
/*     */   
/*     */ 
/*  53 */   JPanel instructionPane = new JPanel();
/*     */   
/*     */   public IORSAWaitingLinePane(IORSAProcessController control) {
/*  56 */     super(control);
/*  57 */     this.controller = control;
/*     */     
/*  59 */     this.start = new JButton("Start");
/*  60 */     this.stop = new JButton("Stop");
/*  61 */     this.pause = new JButton("Pause");
/*  62 */     this.dim = new Dimension(600, 400);
/*     */     
/*     */ 
/*  65 */     this.c.addItem("1");
/*  66 */     this.c.addItem("2");
/*  67 */     this.c.addItem("3");
/*  68 */     this.c.addItem("4");
/*  69 */     this.c.setSelectedItem("1");
/*     */     
/*  71 */     this.c2.addItem("1");
/*  72 */     this.c2.addItem("2");
/*  73 */     this.c2.addItem("3");
/*  74 */     this.c2.addItem("4");
/*  75 */     this.c2.setSelectedItem("1");
/*     */     
/*  77 */     this.speed.addItem("slow");
/*  78 */     this.speed.addItem("normal");
/*  79 */     this.speed.addItem("fast");
/*  80 */     this.speed.setSelectedItem("normal");
/*     */     
/*  82 */     for (int i = 2; i < 12; i++) {
/*  83 */       this.uValue.addItem(Integer.toString(i));
/*  84 */       this.aValue.addItem(Integer.toString(i));
/*     */     }
/*  86 */     this.uValue.setSelectedItem("4");
/*  87 */     this.aValue.setSelectedItem("4");
/*     */     
/*  89 */     this.speed.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/*  91 */         String sp = (String)e.getItem();
/*  92 */         if (sp.equals("slow")) {
/*  93 */           IORSAWaitingLinePane.this.phasePan1.setMSEL(10000);
/*  94 */           IORSAWaitingLinePane.this.phasePan2.setMSEL(10000);
/*     */         }
/*  96 */         else if (sp.equals("normal")) {
/*  97 */           IORSAWaitingLinePane.this.phasePan1.setMSEL(2000);
/*  98 */           IORSAWaitingLinePane.this.phasePan2.setMSEL(2000);
/*     */         }
/*     */         else {
/* 101 */           IORSAWaitingLinePane.this.phasePan1.setMSEL(1000);
/* 102 */           IORSAWaitingLinePane.this.phasePan2.setMSEL(1000);
/*     */         }
/*     */         
/* 105 */         IORSAWaitingLinePane.this.savePrintInfo();
/*     */       }
/*     */       
/* 108 */     });
/* 109 */     this.uValue.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 111 */         IORSAWaitingLinePane.this.u = Integer.parseInt((String)e.getItem());
/* 112 */         IORSAWaitingLinePane.this.savePrintInfo();
/*     */       }
/* 114 */     });
/* 115 */     this.aValue.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 117 */         IORSAWaitingLinePane.this.a = Integer.parseInt((String)e.getItem());
/* 118 */         IORSAWaitingLinePane.this.savePrintInfo();
/*     */       }
/*     */       
/* 121 */     });
/* 122 */     this.c.addItemListener(new ItemListener()
/*     */     {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 125 */         int flagnum = Integer.parseInt((String)e.getItem());
/* 126 */         IORSAWaitingLinePane.this.phasePan1.setRecNum(flagnum);
/* 127 */         IORSAWaitingLinePane.this.phasePan2.setRecNum(flagnum);
/* 128 */         IORSAWaitingLinePane.this.repaint();
/* 129 */         IORSAWaitingLinePane.this.savePrintInfo();
/*     */       }
/*     */       
/* 132 */     });
/* 133 */     ButtonGroup group = new ButtonGroup();
/* 134 */     this.oneChanel = new JRadioButton("one", false);
/* 135 */     this.twoChanel = new JRadioButton("two", true);
/* 136 */     group.add(this.oneChanel);
/* 137 */     group.add(this.twoChanel);
/*     */     
/* 139 */     this.oneChanel.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 141 */         IORSAWaitingLinePane.this.phaseNum = 1;
/* 142 */         IORSAWaitingLinePane.this.flagS2 = false;
/* 143 */         IORSAWaitingLinePane.this.addSelectedPanel(IORSAWaitingLinePane.this.phaseNum);
/* 144 */         IORSAWaitingLinePane.this.savePrintInfo();
/*     */       }
/* 146 */     });
/* 147 */     this.twoChanel.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 149 */         IORSAWaitingLinePane.this.phaseNum = 2;
/* 150 */         IORSAWaitingLinePane.this.flagS2 = true;
/* 151 */         IORSAWaitingLinePane.this.addSelectedPanel(IORSAWaitingLinePane.this.phaseNum);
/* 152 */         IORSAWaitingLinePane.this.savePrintInfo();
/*     */       }
/*     */       
/* 155 */     });
/* 156 */     this.c2.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 158 */         int flagnum = Integer.parseInt((String)e.getItem());
/* 159 */         IORSAWaitingLinePane.this.phasePan2.setRecNum1(flagnum);
/* 160 */         IORSAWaitingLinePane.this.repaint();
/* 161 */         IORSAWaitingLinePane.this.savePrintInfo();
/*     */       }
/* 163 */     });
/* 164 */     this.start.addActionListener(this);
/* 165 */     this.stop.addActionListener(this);
/* 166 */     this.pause.addActionListener(this);
/*     */     
/* 168 */     this.speedLab.setBounds(50, 350, 80, 20);
/* 169 */     this.speed.setBounds(50, 370, 80, 20);
/*     */     
/* 171 */     this.arrivalRateLab.setBounds(160, 350, 80, 20);
/* 172 */     this.uValue.setBounds(160, 370, 80, 20);
/*     */     
/* 174 */     this.serviceRateLab.setBounds(270, 350, 100, 20);
/* 175 */     this.aValue.setBounds(270, 370, 80, 20);
/*     */     
/* 177 */     this.ns1Lab.setBounds(375, 350, 110, 20);
/* 178 */     this.c.setBounds(380, 370, 80, 20);
/*     */     
/* 180 */     this.ns2Lab.setBounds(485, 350, 110, 20);
/* 181 */     this.c2.setBounds(490, 370, 80, 20);
/*     */     
/* 183 */     this.phasePan2.setBounds(100, 0, 500, 300);
/*     */     
/* 185 */     this.numberLab.setBounds(50, 440, 120, 20);
/* 186 */     this.oneChanel.setBounds(170, 440, 60, 20);
/* 187 */     this.twoChanel.setBounds(230, 440, 60, 20);
/*     */     
/* 189 */     this.phasePan2.setBounds(100, 0, 500, 300);
/*     */     
/* 191 */     this.start.setBounds(300, 440, 72, 21);
/* 192 */     this.stop.setBounds(394, 440, 72, 21);
/* 193 */     this.pause.setBounds(488, 440, 84, 21);
/*     */     
/*     */ 
/* 196 */     this.AppPane.setLayout(null);
/* 197 */     this.AppPane.add(this.ns1Lab);
/* 198 */     this.AppPane.add(this.c);
/*     */     
/* 200 */     this.AppPane.add(this.ns2Lab);
/* 201 */     this.AppPane.add(this.c2);
/*     */     
/* 203 */     this.AppPane.add(this.arrivalRateLab);
/* 204 */     this.AppPane.add(this.uValue);
/*     */     
/*     */ 
/* 207 */     this.AppPane.add(this.serviceRateLab);
/* 208 */     this.AppPane.add(this.aValue);
/*     */     
/* 210 */     this.AppPane.add(this.speedLab);
/* 211 */     this.AppPane.add(this.speed);
/*     */     
/* 213 */     this.AppPane.add(this.phasePan2);
/*     */     
/* 215 */     this.AppPane.add(this.numberLab);
/* 216 */     this.AppPane.add(this.oneChanel);
/* 217 */     this.AppPane.add(this.twoChanel);
/*     */     
/*     */ 
/*     */ 
/* 221 */     this.AppPane.add(this.start);
/* 222 */     this.AppPane.add(this.stop);
/* 223 */     this.AppPane.add(this.pause);
/*     */     
/*     */ 
/* 226 */     this.AppPane.setPreferredSize(new Dimension(590, 480));
/* 227 */     this.AppPane.setBorder(null);
/* 228 */     this.mainPan.setLayout(new BorderLayout());
/* 229 */     this.mainPan.add(this.AppPane, "Center");
/*     */     
/* 231 */     this.instructionPane.setPreferredSize(new Dimension(590, 110));
/*     */     
/*     */ 
/* 234 */     addInfoPanel();
/*     */     
/* 236 */     setLayout(new BorderLayout());
/* 237 */     add(this.instructionPane, "North");
/* 238 */     add(this.mainPan, "Center");
/*     */     
/* 240 */     this.phasePan2.repaint();
/*     */   }
/*     */   
/*     */   public void addInfoPanel()
/*     */   {
/* 245 */     this.instructionPane.setLayout(new BorderLayout());
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
/* 263 */     ImageIcon test = new ImageIcon("image/waitlineInfo.gif");
/* 264 */     JLabel infoLab = new JLabel(test);
/*     */     
/*     */ 
/* 267 */     this.instructionPane.add(infoLab, "Center");
/*     */   }
/*     */   
/*     */ 
/*     */   public void addSelectedPanel(int type)
/*     */   {
/* 273 */     if (type == 1) {
/* 274 */       this.AppPane.remove(this.ns1Lab);
/* 275 */       this.AppPane.remove(this.c);
/*     */       
/* 277 */       this.AppPane.remove(this.ns2Lab);
/* 278 */       this.AppPane.remove(this.c2);
/*     */       
/* 280 */       this.AppPane.remove(this.arrivalRateLab);
/* 281 */       this.AppPane.remove(this.uValue);
/*     */       
/* 283 */       this.AppPane.remove(this.serviceRateLab);
/* 284 */       this.AppPane.remove(this.aValue);
/*     */       
/* 286 */       this.AppPane.remove(this.speedLab);
/* 287 */       this.AppPane.remove(this.speed);
/*     */       
/* 289 */       this.AppPane.add(this.phasePan2);
/*     */       
/*     */ 
/*     */ 
/* 293 */       this.speedLab.setBounds(110, 350, 80, 20);
/* 294 */       this.speed.setBounds(110, 370, 80, 20);
/*     */       
/* 296 */       this.arrivalRateLab.setBounds(220, 350, 80, 20);
/* 297 */       this.uValue.setBounds(220, 370, 80, 20);
/*     */       
/* 299 */       this.serviceRateLab.setBounds(330, 350, 100, 20);
/* 300 */       this.aValue.setBounds(330, 370, 80, 20);
/* 301 */       this.ns1Lab.setText("#Servers:");
/* 302 */       this.ns1Lab.setBounds(440, 350, 80, 20);
/* 303 */       this.c.setBounds(440, 370, 80, 20);
/*     */       
/* 305 */       this.phasePan2.setBounds(100, 0, 500, 300);
/*     */       
/* 307 */       this.AppPane.add(this.ns1Lab);
/* 308 */       this.AppPane.add(this.c);
/*     */       
/* 310 */       this.AppPane.add(this.arrivalRateLab);
/* 311 */       this.AppPane.add(this.uValue);
/*     */       
/* 313 */       this.AppPane.add(this.serviceRateLab);
/* 314 */       this.AppPane.add(this.aValue);
/*     */       
/* 316 */       this.AppPane.add(this.speedLab);
/* 317 */       this.AppPane.add(this.speed);
/*     */       
/* 319 */       this.AppPane.add(this.phasePan2);
/* 320 */       this.phasePan2.repaint();
/*     */     } else {
/* 322 */       this.AppPane.remove(this.ns1Lab);
/* 323 */       this.AppPane.remove(this.c);
/*     */       
/* 325 */       this.AppPane.remove(this.ns2Lab);
/* 326 */       this.AppPane.remove(this.c2);
/*     */       
/* 328 */       this.AppPane.remove(this.arrivalRateLab);
/* 329 */       this.AppPane.remove(this.uValue);
/*     */       
/* 331 */       this.AppPane.remove(this.serviceRateLab);
/* 332 */       this.AppPane.remove(this.aValue);
/*     */       
/* 334 */       this.AppPane.remove(this.speedLab);
/* 335 */       this.AppPane.remove(this.speed);
/*     */       
/* 337 */       this.AppPane.add(this.phasePan1);
/*     */       
/* 339 */       this.speedLab.setBounds(50, 350, 80, 20);
/* 340 */       this.speed.setBounds(50, 370, 80, 20);
/*     */       
/* 342 */       this.arrivalRateLab.setBounds(160, 350, 80, 20);
/* 343 */       this.uValue.setBounds(160, 370, 80, 20);
/*     */       
/* 345 */       this.serviceRateLab.setBounds(270, 350, 100, 20);
/* 346 */       this.aValue.setBounds(270, 370, 80, 20);
/* 347 */       this.ns1Lab.setText("#Servers at 1:");
/* 348 */       this.ns1Lab.setBounds(380, 350, 110, 20);
/* 349 */       this.c.setBounds(380, 370, 80, 20);
/*     */       
/* 351 */       this.ns2Lab.setBounds(490, 350, 110, 20);
/* 352 */       this.c2.setBounds(490, 370, 80, 20);
/*     */       
/* 354 */       this.phasePan1.setBounds(100, 0, 500, 300);
/*     */       
/* 356 */       this.AppPane.add(this.ns1Lab);
/* 357 */       this.AppPane.add(this.c);
/*     */       
/* 359 */       this.AppPane.add(this.ns2Lab);
/* 360 */       this.AppPane.add(this.c2);
/*     */       
/* 362 */       this.AppPane.add(this.arrivalRateLab);
/* 363 */       this.AppPane.add(this.uValue);
/*     */       
/* 365 */       this.AppPane.add(this.serviceRateLab);
/* 366 */       this.AppPane.add(this.aValue);
/*     */       
/* 368 */       this.AppPane.add(this.speedLab);
/* 369 */       this.AppPane.add(this.speed);
/*     */       
/* 371 */       this.AppPane.add(this.phasePan1);
/* 372 */       this.phasePan1.repaint();
/*     */     }
/* 374 */     this.AppPane.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void updatePanel() {}
/*     */   
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 387 */     String str = "This module simulates client/server relationships.\n\n";
/* 388 */     str = String.valueOf(String.valueOf(str)).concat("The random events for customer arrivals are generated according to Poisson ");
/* 389 */     str = String.valueOf(String.valueOf(str)).concat("distribution whose parameter l can be chosen from the pull-down menu ");
/* 390 */     str = String.valueOf(String.valueOf(str)).concat("\"Arrival Rate\"; the random events for server services are ");
/* 391 */     str = String.valueOf(String.valueOf(str)).concat("generated according to negative exponential distribution whose");
/* 392 */     str = String.valueOf(String.valueOf(str)).concat("parameter m can be chosen from the pull-down menu \"Service Rate\". ");
/* 393 */     str = String.valueOf(String.valueOf(str)).concat("Users also can choose one or two phases, and select the number ");
/* 394 */     str = String.valueOf(String.valueOf(str)).concat("of server for each phase from \"#Servers 1\" and \"#Servers 2\" drop list. ");
/*     */     
/* 396 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 398 */     this.help_content_step.setText(str);
/* 399 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */   protected void initializeCurrentProcedureHelpPanel() {
/* 403 */     String str = "This module simulates the process of customers arriving to a";
/* 404 */     str = String.valueOf(String.valueOf(str)).concat("queueing system, waiting in a queue (if all the servers are");
/* 405 */     str = String.valueOf(String.valueOf(str)).concat("busy), and then receiving service from one of the servers");
/* 406 */     str = String.valueOf(String.valueOf(str)).concat("before departing when the service is completed. The customers");
/* 407 */     str = String.valueOf(String.valueOf(str)).concat("arrive randomly, so the interarrival times (time between  ");
/* 408 */     str = String.valueOf(String.valueOf(str)).concat("consecutive arrivals) have an exponential distribution.The mean ");
/* 409 */     str = String.valueOf(String.valueOf(str)).concat("arrival rate l can be chosen from the pull-down list \"Arrival Rate\".");
/* 410 */     str = String.valueOf(String.valueOf(str)).concat("The service times have an exponential distribution, where the");
/* 411 */     str = String.valueOf(String.valueOf(str)).concat("mean service rate m can be chosen from the pull-down list \"Service");
/* 412 */     str = String.valueOf(String.valueOf(str)).concat(" Rate\". Users also can select the number of servers from the");
/* 413 */     str = String.valueOf(String.valueOf(str)).concat("pull-down list \"NS\".");
/*     */     
/*     */ 
/* 416 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 417 */     this.help_content_procedure.setText(str);
/* 418 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/*     */   protected void finishProcedure() {}
/*     */   
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/*     */     try {
/* 426 */       this.speed.setSelectedIndex(in.readInt());
/* 427 */       this.uValue.setSelectedIndex(in.readInt());
/* 428 */       this.aValue.setSelectedIndex(in.readInt());
/* 429 */       this.c.setSelectedIndex(in.readInt());
/* 430 */       this.c2.setSelectedIndex(in.readInt());
/* 431 */       this.phaseNum = in.readInt();
/* 432 */       if (this.phaseNum == 1) {
/* 433 */         this.oneChanel.setSelected(true);
/*     */       } else {
/* 435 */         this.twoChanel.setSelected(true);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 439 */       System.out.println("LOAD Fails");
/*     */     }
/*     */     
/* 442 */     repaint();
/*     */   }
/*     */   
/*     */   public void savePrintInfo() {
/* 446 */     this.controller.data.arrivalRate = "Arrival Rate: ".concat(String.valueOf(String.valueOf(this.uValue.getSelectedItem())));
/* 447 */     this.controller.data.serviceRate = "Service Rate: ".concat(String.valueOf(String.valueOf(this.aValue.getSelectedItem())));
/* 448 */     if (this.phaseNum == 1) {
/* 449 */       this.controller.data.ns1Num = "#Servers: ".concat(String.valueOf(String.valueOf(this.c.getSelectedItem())));
/* 450 */       if (this.phasePan1.stop) {
/* 451 */         this.controller.data.isShowData = true;
/* 452 */         this.controller.data.lqStr = "Lq : ".concat(String.valueOf(String.valueOf(this.phasePan1.Lq)));
/* 453 */         this.controller.data.lsStr = "Ls : ".concat(String.valueOf(String.valueOf(this.phasePan1.Ls)));
/* 454 */         this.controller.data.wqStr = "Wq : ".concat(String.valueOf(String.valueOf(this.phasePan1.Wq)));
/* 455 */         this.controller.data.wsStr = "Ws : ".concat(String.valueOf(String.valueOf(this.phasePan1.Ws)));
/*     */       }
/*     */       else {
/* 458 */         this.controller.data.isShowData = false;
/*     */       }
/*     */     } else {
/* 461 */       this.controller.data.ns1Num = "#Servers at 1: ".concat(String.valueOf(String.valueOf(this.c.getSelectedItem())));
/* 462 */       if (this.phasePan2.stop) {
/* 463 */         this.controller.data.isShowData = true;
/* 464 */         this.controller.data.lqStr = "Lq : ".concat(String.valueOf(String.valueOf(this.phasePan2.Lq)));
/* 465 */         this.controller.data.lsStr = "Ls : ".concat(String.valueOf(String.valueOf(this.phasePan2.Ls)));
/* 466 */         this.controller.data.wqStr = "Wq : ".concat(String.valueOf(String.valueOf(this.phasePan2.Wq)));
/* 467 */         this.controller.data.wsStr = "Ws : ".concat(String.valueOf(String.valueOf(this.phasePan2.Ws)));
/*     */       } else {
/* 469 */         this.controller.data.isShowData = false;
/*     */       }
/*     */     }
/* 472 */     this.controller.data.ns2Num = "Servers2: ".concat(String.valueOf(String.valueOf(this.c2.getSelectedItem())));
/* 473 */     this.controller.data.phaseNum = this.phaseNum;
/*     */     
/* 475 */     this.controller.solver.initWaitingLinePrintInfo(this.controller.data);
/*     */   }
/*     */   
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/*     */     try {
/* 481 */       out.writeInt(this.speed.getSelectedIndex());
/* 482 */       out.writeInt(this.uValue.getSelectedIndex());
/* 483 */       out.writeInt(this.aValue.getSelectedIndex());
/* 484 */       out.writeInt(this.c.getSelectedIndex());
/* 485 */       out.writeInt(this.c2.getSelectedIndex());
/* 486 */       out.writeInt(this.phaseNum);
/*     */     }
/*     */     catch (Exception localException) {}
/*     */   }
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
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 524 */     JButton btn = (JButton)e.getSource();
/* 525 */     if (btn == this.start) {
/* 526 */       this.pause.setLabel("Pause");
/* 527 */       if (this.phaseNum == 1) {
/* 528 */         this.phasePan1.start();
/*     */       } else {
/* 530 */         this.phasePan2.start();
/*     */       }
/* 532 */       this.c2.setEnabled(false);
/* 533 */       this.speed.setEnabled(false);
/*     */       
/* 535 */       this.c.setEnabled(false);
/* 536 */       this.uValue.setEnabled(false);
/* 537 */       this.aValue.setEnabled(false);
/* 538 */       this.oneChanel.setEnabled(false);
/* 539 */       this.twoChanel.setEnabled(false);
/* 540 */       this.start.setEnabled(false);
/*     */     }
/* 542 */     else if (btn == this.stop) {
/* 543 */       this.pause.setLabel("Pause");
/* 544 */       if (this.phaseNum == 1) {
/* 545 */         this.phasePan1.stop();
/*     */       } else {
/* 547 */         this.phasePan2.stop();
/*     */       }
/* 549 */       this.c2.setEnabled(true);
/* 550 */       this.speed.setEnabled(true);
/*     */       
/*     */ 
/* 553 */       this.start.setEnabled(true);
/* 554 */       this.c.setEnabled(true);
/* 555 */       this.aValue.setEnabled(true);
/* 556 */       this.uValue.setEnabled(true);
/* 557 */       this.oneChanel.setEnabled(true);
/* 558 */       this.twoChanel.setEnabled(true);
/* 559 */       this.start.setEnabled(true);
/*     */ 
/*     */     }
/* 562 */     else if (btn.getLabel().equalsIgnoreCase("Pause")) {
/* 563 */       btn.setLabel("Resume");
/* 564 */       if (this.phaseNum == 1) {
/* 565 */         this.phasePan1.suspend();
/*     */       } else {
/* 567 */         this.phasePan2.suspend();
/*     */       }
/*     */     } else {
/* 570 */       btn.setLabel("Pause");
/* 571 */       if (this.phaseNum == 1) {
/* 572 */         this.phasePan1.again();
/*     */       } else {
/* 574 */         this.phasePan2.again();
/*     */       }
/*     */     }
/* 577 */     savePrintInfo();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORSAWaitingLinePane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */