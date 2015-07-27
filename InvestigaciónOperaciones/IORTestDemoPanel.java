/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.Toolkit;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ 
/*     */ public class IORTestDemoPanel extends JPanel
/*     */ {
/*     */   protected JLabel backLabel;
/*  19 */   private int sleepTime = 300;
/*  20 */   private static final int sleepSlowTime = 500;
/*  21 */   private static final int sleepFastTime = 300;
/*     */   
/*  23 */   public Graphics refg = null;
/*     */   public java.awt.image.BufferedImage buffer;
/*  25 */   private Dimension dim = new Dimension();
/*     */   
/*     */ 
/*  28 */   public Point startPoint = new Point(20, 20);
/*  29 */   public Point finishPoint = new Point(250, 250);
/*     */   
/*  31 */   public Point drawPoint = new Point();
/*  32 */   public java.awt.Image mouseImg = null;
/*  33 */   public boolean showMouse = false;
/*     */   
/*  35 */   private final int demoControllerWid = 475;
/*  36 */   private final int demoControllerHei = 60;
/*  37 */   public playThread test = null;
/*  38 */   public IORTestDemoPanel.TestDialog testDialog = null;
/*     */   
/*  40 */   public Vector menuShowV = null;
/*  41 */   public Vector demoImageV = null;
/*  42 */   public Vector startPointV = null;
/*  43 */   public Vector finishPointV = null;
/*  44 */   public Vector stepNumV = null;
/*     */   
/*  46 */   public String menuStr = "";
/*     */   
/*     */   public boolean isAuto;
/*     */   public int nextNum;
/*     */   public IORTutorial tutor;
/*     */   public ImageIcon backIcon;
/*  52 */   public Hashtable cache = null;
/*     */   
/*  54 */   public int optionEnabledLocation = Integer.MAX_VALUE;
/*     */   
/*  56 */   java.awt.MediaTracker tracker = null;
/*     */   
/*  58 */   public IORTestDemoPanel(String demoName, IORTutorial tutor) { this.cache = new Hashtable();
/*  59 */     this.tutor = tutor;
/*  60 */     System.out.println("Demo   :".concat(String.valueOf(String.valueOf(demoName))));
/*  61 */     setDoubleBuffered(true);
/*     */     
/*  63 */     this.mouseImg = createImage("demo/win_mouse.gif", this);
/*  64 */     this.backLabel = new JLabel();
/*  65 */     this.backLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, -2, -6));
/*     */     
/*     */ 
/*  68 */     this.testDialog = new IORTestDemoPanel.TestDialog(demoName, this);
/*  69 */     this.testDialog.setSize(475, 60);
/*  70 */     this.testDialog.setVisible(true);
/*  71 */     this.testDialog.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 237, Toolkit.getDefaultToolkit().getScreenSize().height - 100);
/*  72 */     this.testDialog.show();
/*  73 */     setBorder(javax.swing.BorderFactory.createEmptyBorder(-8, -8, -8, 2));
/*  74 */     add(this.backLabel);
/*     */   }
/*     */   
/*     */   public void setPanelSize(int wid, int hei)
/*     */   {
/*  79 */     this.dim.setSize(wid, hei);
/*  80 */     System.out.println(String.valueOf(String.valueOf(new StringBuffer("wid =").append(wid).append("   hei=").append(hei))));
/*     */   }
/*     */   
/*     */ 
/*     */   public void apprchTarget()
/*     */   {
/*  86 */     this.refg = this.backLabel.getGraphics();
/*     */     try {
/*  88 */       if (this.isAuto) {
/*  89 */         if (this.menuStr.equalsIgnoreCase("null")) {
/*  90 */           this.tutor.procedure.setSelected(false);
/*  91 */           this.tutor.option.setSelected(false);
/*  92 */         } else if (this.menuStr.equalsIgnoreCase("Procedure")) {
/*  93 */           this.tutor.procedure.setSelected(true);
/*  94 */           this.tutor.option.setSelected(false);
/*     */         }
/*  96 */         else if (this.menuStr.equalsIgnoreCase("Option")) {
/*  97 */           this.tutor.procedure.setSelected(false);
/*  98 */           this.tutor.option.setSelected(true);
/*     */         } else {
/* 100 */           this.tutor.procedure.setSelected(false);
/* 101 */           this.tutor.option.setSelected(false);
/*     */         }
/*     */         
/* 104 */         if ((this.startPoint.x != this.finishPoint.x) || (this.startPoint.y != this.finishPoint.y)) {
/* 105 */           this.test.moveTimes = ((Integer)this.stepNumV.elementAt(this.nextNum)).intValue();
/* 106 */           for (int i = 0; i <= this.test.moveTimes; i++) {
/* 107 */             Thread.sleep(this.sleepTime);
/* 108 */             paintImmediately(this.drawPoint.x - 2, this.drawPoint.y - 3, 14, 22);
/* 109 */             int temp = (int)((this.finishPoint.x - this.startPoint.x) * 1.0D / this.test.moveTimes);
/* 110 */             this.drawPoint.x = (this.startPoint.x + temp * i);
/* 111 */             temp = (int)((this.finishPoint.y - this.startPoint.y) * 1.0D / this.test.moveTimes);
/* 112 */             this.drawPoint.y = (temp * i + this.startPoint.y);
/* 113 */             this.refg.drawImage(this.mouseImg, this.drawPoint.x + 1, this.drawPoint.y + 1, this);
/*     */           }
/* 115 */           Thread.sleep(this.sleepTime);
/*     */         }
/*     */         else {
/* 118 */           Thread.sleep(500L);
/*     */         }
/*     */       } else {
/* 121 */         if (this.menuStr.equalsIgnoreCase("null")) {
/* 122 */           this.tutor.procedure.setSelected(false);
/* 123 */           this.tutor.option.setSelected(false);
/* 124 */         } else if (this.menuStr.equalsIgnoreCase("Procedure")) {
/* 125 */           this.tutor.procedure.setSelected(true);
/* 126 */           this.tutor.option.setSelected(false);
/*     */         }
/* 128 */         else if (this.menuStr.equalsIgnoreCase("Option")) {
/* 129 */           this.tutor.procedure.setSelected(false);
/* 130 */           this.tutor.option.setSelected(true);
/*     */         } else {
/* 132 */           this.tutor.procedure.setSelected(false);
/* 133 */           this.tutor.option.setSelected(false);
/*     */         }
/* 135 */         if ((this.startPoint.x != this.finishPoint.x) || (this.startPoint.y != this.finishPoint.y)) {
/* 136 */           this.test.moveTimes = ((Integer)this.stepNumV.elementAt(this.nextNum)).intValue();
/* 137 */           for (int i = 0; i <= this.test.moveTimes; i++) {
/* 138 */             Thread.sleep(this.sleepTime);
/* 139 */             paintImmediately(this.drawPoint.x - 2, this.drawPoint.y - 3, 14, 22);
/* 140 */             if (this.test.moveTimes != 0) {
/* 141 */               int temp = (int)((this.finishPoint.x - this.startPoint.x) * 1.0D / this.test.moveTimes);
/* 142 */               this.drawPoint.x = (this.startPoint.x + temp * i);
/* 143 */               temp = (int)((this.finishPoint.y - this.startPoint.y) * 1.0D / this.test.moveTimes);
/* 144 */               this.drawPoint.y = (temp * i + this.startPoint.y);
/* 145 */               this.refg.drawImage(this.mouseImg, this.drawPoint.x + 1, this.drawPoint.y + 1, this);
/*     */             }
/*     */             else {
/* 148 */               this.drawPoint.x = this.startPoint.x;
/* 149 */               this.drawPoint.y = this.finishPoint.y;
/*     */             }
/*     */           }
/* 152 */           Thread.sleep(this.sleepTime);
/*     */         }
/*     */         else {
/* 155 */           this.refg.drawImage(this.mouseImg, this.drawPoint.x + 1, this.drawPoint.y + 1, this);
/* 156 */           Thread.sleep(500L);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (InterruptedException localInterruptedException) {}
/*     */   }
/*     */   
/*     */ 
/*     */   public void paintComponents(Graphics refg)
/*     */   {
/* 166 */     super.paintComponents(refg);
/* 167 */     refg.drawImage(this.mouseImg, this.drawPoint.x + 1, this.drawPoint.y + 1, this);
/*     */   }
/*     */   
/*     */   public void update(Graphics refg)
/*     */   {
/* 172 */     super.paintComponents(refg);
/* 173 */     refg.drawImage(this.mouseImg, this.drawPoint.x + 1, this.drawPoint.y + 1, this);
/*     */   }
/*     */   
/* 176 */   public void showMouse() { this.refg.drawImage(this.mouseImg, this.drawPoint.x + 1, this.drawPoint.y + 1, this); }
/*     */   
/*     */ 
/*     */   public ImageIcon createImageIcon(String filename, String description)
/*     */   {
/*     */     ImageIcon img;
/*     */     
/* 183 */     if ((this.cache != null) && 
/* 184 */       ((img = (ImageIcon)this.cache.get(filename)) != null)) {
/* 185 */       return img;
/*     */     }
/*     */     
/*     */ 
/* 189 */     String path = "".concat(String.valueOf(String.valueOf(filename)));
/* 190 */     ImageIcon img = new ImageIcon(getClass().getResource(path));
/* 191 */     this.cache.put(filename, img);
/* 192 */     return img;
/*     */   }
/*     */   
/*     */ 
/*     */   public java.awt.Image createImage(String name, java.awt.Component cmp)
/*     */   {
/* 198 */     java.awt.Image img = null;
/*     */     try {
/* 200 */       System.out.println("cache is null");
/* 201 */       java.net.URLClassLoader urlLoader = (java.net.URLClassLoader)cmp.getClass().getClassLoader();
/* 202 */       java.net.URL fileLoc = urlLoader.findResource("".concat(String.valueOf(String.valueOf(name))));
/* 203 */       img = cmp.getToolkit().createImage(fileLoc);
/*     */     } catch (Exception e) {
/* 205 */       System.out.println("URL WRONG!");
/*     */     }
/* 207 */     return img;
/*     */   }
/*     */   
/*     */   public class TestDialog
/*     */     extends javax.swing.JFrame implements java.awt.event.ActionListener
/*     */   {
/* 213 */     private JPanel confirmPanel = new JPanel();
/* 214 */     public JButton nextButt = new JButton("Next");
/* 215 */     public JButton autoButt = new JButton("Auto");
/* 216 */     public JButton backButt = new JButton("Back");
/* 217 */     public JButton pauseButt = new JButton("Pause");
/* 218 */     public JButton stopButt = new JButton("Stop");
/*     */     
/* 220 */     public JRadioButton slowRadio = new JRadioButton("Slow", true);
/* 221 */     public JRadioButton fastRadio = new JRadioButton("Fast", false);
/*     */     
/*     */     private IORTestDemoPanel demoPanel;
/* 224 */     private boolean isPause = false;
/*     */     
/* 226 */     public TestDialog(String demoName, IORTestDemoPanel demoPane) { this.demoPanel = demoPane;
/*     */       
/* 228 */       setTitle(demoName);
/*     */       
/* 230 */       setResizable(false);
/* 231 */       this.backButt.setEnabled(false);
/* 232 */       this.stopButt.setEnabled(false);
/* 233 */       this.pauseButt.setEnabled(false);
/*     */       
/* 235 */       cavPanelInit();
/* 236 */       this.nextButt.addActionListener(this);
/* 237 */       this.autoButt.addActionListener(this);
/* 238 */       this.backButt.addActionListener(this);
/* 239 */       this.stopButt.addActionListener(this);
/* 240 */       this.pauseButt.addActionListener(this);
/*     */       
/* 242 */       javax.swing.ButtonGroup group = new javax.swing.ButtonGroup();
/* 243 */       group.add(this.slowRadio);
/* 244 */       group.add(this.fastRadio);
/* 245 */       this.fastRadio.setSelected(true);
/* 246 */       this.slowRadio.addItemListener(new IORTestDemoPanel.1((TestDialog)this));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 253 */       this.fastRadio.addItemListener(new IORTestDemoPanel.2((TestDialog)this));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     void cavPanelInit()
/*     */     {
/* 262 */       this.confirmPanel.add(this.slowRadio);
/* 263 */       this.confirmPanel.add(this.fastRadio);
/*     */       
/* 265 */       this.confirmPanel.add(this.autoButt);
/* 266 */       this.confirmPanel.add(this.backButt);
/* 267 */       this.confirmPanel.add(this.nextButt);
/*     */       
/*     */ 
/* 270 */       this.confirmPanel.add(this.pauseButt);
/* 271 */       this.confirmPanel.add(this.stopButt);
/* 272 */       getContentPane().add(this.confirmPanel);
/*     */     }
/*     */     
/* 275 */     public void actionPerformed(java.awt.event.ActionEvent e) { Object obj = e.getSource();
/* 276 */       if (obj == this.nextButt) {
/* 277 */         findOptionEnabledLocation();
/*     */         
/* 279 */         IORTestDemoPanel.this.isAuto = false;
/* 280 */         if (IORTestDemoPanel.this.test != null) IORTestDemoPanel.this.test = null;
/* 281 */         IORTestDemoPanel.this.test = new playThread(this.demoPanel);
/* 282 */         IORTestDemoPanel.this.test.setSleepTime(IORTestDemoPanel.this.sleepTime);
/* 283 */         IORTestDemoPanel.this.test.start();
/* 284 */         this.nextButt.setEnabled(false);
/* 285 */         this.backButt.setEnabled(false);
/* 286 */         this.stopButt.setEnabled(true);
/* 287 */         this.slowRadio.setEnabled(false);
/* 288 */         this.fastRadio.setEnabled(false);
/* 289 */         this.pauseButt.setText("Pause");
/* 290 */         this.isPause = false;
/* 291 */         this.pauseButt.setEnabled(false);
/*     */       }
/* 293 */       if (obj == this.pauseButt) {
/* 294 */         if (!this.isPause) {
/* 295 */           this.pauseButt.setText("Resume");
/* 296 */           this.isPause = true;
/* 297 */           IORTestDemoPanel.this.test.pauseSimulate();
/*     */         } else {
/* 299 */           this.pauseButt.setText("Pause");
/* 300 */           this.isPause = false;
/* 301 */           IORTestDemoPanel.this.test.resumeSimulate();
/*     */         }
/*     */       }
/* 304 */       if (obj == this.autoButt) {
/* 305 */         IORTestDemoPanel.this.isAuto = true;
/* 306 */         findOptionEnabledLocation();
/* 307 */         if (IORTestDemoPanel.this.test != null) IORTestDemoPanel.this.test = null;
/* 308 */         IORTestDemoPanel.this.test = new playThread(this.demoPanel);
/* 309 */         IORTestDemoPanel.this.test.setSleepTime(IORTestDemoPanel.this.sleepTime);
/* 310 */         IORTestDemoPanel.this.test.start();
/* 311 */         this.autoButt.setEnabled(false);
/* 312 */         this.nextButt.setEnabled(false);
/* 313 */         this.backButt.setEnabled(false);
/* 314 */         this.stopButt.setEnabled(true);
/* 315 */         this.slowRadio.setEnabled(false);
/* 316 */         this.fastRadio.setEnabled(false);
/* 317 */         this.pauseButt.setEnabled(true);
/*     */       }
/* 319 */       if (obj == this.backButt) {
/* 320 */         IORTestDemoPanel.this.showMouse = false;
/*     */         
/* 322 */         if (IORTestDemoPanel.this.nextNum == 1) {
/* 323 */           this.backButt.setEnabled(false);
/*     */         }
/* 325 */         IORTestDemoPanel.this.nextNum -= 1;
/* 326 */         IORTestDemoPanel.this.test.moveTimes = ((Integer)IORTestDemoPanel.this.stepNumV.elementAt(IORTestDemoPanel.this.nextNum)).intValue();
/* 327 */         if (IORTestDemoPanel.this.nextNum != 0) {
/* 328 */           IORTestDemoPanel.this.menuStr = ((String)IORTestDemoPanel.this.menuShowV.elementAt(IORTestDemoPanel.this.nextNum - 1));
/*     */         } else
/* 330 */           IORTestDemoPanel.this.menuStr = "";
/* 331 */         IORTestDemoPanel.this.backIcon = IORTestDemoPanel.this.createImageIcon((String)IORTestDemoPanel.this.demoImageV.elementAt(IORTestDemoPanel.this.nextNum), "");
/*     */         
/* 333 */         IORTestDemoPanel.this.backLabel.setIcon(IORTestDemoPanel.this.backIcon);
/*     */         
/* 335 */         System.out.println("BACK");
/* 336 */         this.nextButt.setEnabled(true);
/*     */         
/* 338 */         if (IORTestDemoPanel.this.nextNum < IORTestDemoPanel.this.optionEnabledLocation) {
/* 339 */           IORTestDemoPanel.this.tutor.option.setEnabled(false);
/*     */         } else {
/* 341 */           IORTestDemoPanel.this.tutor.option.setEnabled(true);
/*     */         }
/*     */       }
/*     */       
/* 345 */       if (obj == this.stopButt) {
/* 346 */         IORTestDemoPanel.this.test.stop();
/* 347 */         reset();
/* 348 */         IORTestDemoPanel.this.menuStr = "";
/* 349 */         IORTestDemoPanel.this.test.moveTimes = ((Integer)IORTestDemoPanel.this.stepNumV.elementAt(IORTestDemoPanel.this.nextNum)).intValue();
/*     */         
/* 351 */         IORTestDemoPanel.this.backIcon = IORTestDemoPanel.this.createImageIcon((String)IORTestDemoPanel.this.demoImageV.elementAt(IORTestDemoPanel.this.nextNum), "");
/* 352 */         IORTestDemoPanel.this.backLabel.setIcon(IORTestDemoPanel.this.backIcon);
/*     */         
/* 354 */         System.out.println("STOP");
/*     */       }
/*     */     }
/*     */     
/*     */     public void exit() {
/*     */       try {
/* 360 */         exit();
/*     */       }
/*     */       catch (Exception localException) {}
/*     */     }
/*     */     
/*     */     public void findOptionEnabledLocation()
/*     */     {
/* 367 */       IORTestDemoPanel.this.optionEnabledLocation = Integer.MAX_VALUE;
/* 368 */       for (int i = 0; i < IORTestDemoPanel.this.menuShowV.size(); i++) {
/* 369 */         String temp = (String)IORTestDemoPanel.this.menuShowV.elementAt(i);
/* 370 */         if (temp.equalsIgnoreCase("Option")) {
/* 371 */           IORTestDemoPanel.this.optionEnabledLocation = i;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public void reset() {
/* 377 */       this.pauseButt.setText("Pause");
/* 378 */       this.isPause = false;
/* 379 */       IORTestDemoPanel.this.showMouse = false;
/* 380 */       this.autoButt.setEnabled(true);
/* 381 */       this.nextButt.setEnabled(true);
/* 382 */       this.backButt.setEnabled(false);
/* 383 */       this.stopButt.setEnabled(false);
/* 384 */       this.pauseButt.setEnabled(false);
/* 385 */       this.slowRadio.setEnabled(true);
/* 386 */       this.fastRadio.setEnabled(true);
/* 387 */       IORTestDemoPanel.this.nextNum = 0;
/* 388 */       IORTestDemoPanel.this.drawPoint.x = ((Point)IORTestDemoPanel.this.startPointV.elementAt(IORTestDemoPanel.this.nextNum)).x;
/* 389 */       IORTestDemoPanel.this.drawPoint.y = ((Point)IORTestDemoPanel.this.startPointV.elementAt(IORTestDemoPanel.this.nextNum)).y;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void loadDemoImg(String txt)
/*     */   {
/* 396 */     if (this.menuShowV != null) this.menuShowV = null;
/* 397 */     this.menuShowV = new Vector();
/* 398 */     if (this.demoImageV != null) this.demoImageV = null;
/* 399 */     this.demoImageV = new Vector();
/* 400 */     if (this.startPointV != null) this.startPointV = null;
/* 401 */     this.startPointV = new Vector();
/* 402 */     if (this.finishPointV != null) this.finishPointV = null;
/* 403 */     this.finishPointV = new Vector();
/* 404 */     if (this.stepNumV != null) this.stepNumV = null;
/* 405 */     this.stepNumV = new Vector();
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 410 */       java.net.URL url = getClass().getResource(txt);
/* 411 */       java.io.InputStream butS = url.openStream();
/* 412 */       java.io.InputStreamReader butSR = new java.io.InputStreamReader(butS);
/* 413 */       BufferedReader butB = new BufferedReader(butSR);
/* 414 */       String name = butB.readLine();
/* 415 */       while (name != null)
/*     */       {
/* 417 */         char linetype = name.charAt(0);
/* 418 */         switch (linetype) {
/*     */         case '0': 
/* 420 */           this.menuShowV.addElement("null");
/* 421 */           break;
/*     */         case '1': 
/* 423 */           this.menuShowV.addElement("Procedure");
/* 424 */           break;
/*     */         case '2': 
/* 426 */           this.menuShowV.addElement("Option");
/* 427 */           break;
/*     */         default: 
/* 429 */           this.menuShowV.addElement("null");
/* 430 */           break;
/*     */         }
/*     */         String imgName;
/*     */         try
/*     */         {
/* 435 */           int temp = Integer.valueOf(name.substring(0, 1)).intValue();
/* 436 */           imgName = filtrateString(name.substring(2));
/*     */         } catch (Exception e) { String imgName;
/* 438 */           System.out.println("Format Error");
/* 439 */           imgName = name;
/*     */         }
/*     */         
/*     */ 
/* 443 */         this.demoImageV.addElement(imgName);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 448 */         name = butB.readLine();
/* 449 */         if (name == null) {
/* 450 */           System.out.println("Point Line is Null !");
/*     */         } else {
/* 452 */           name = filtrateString(name);
/* 453 */           numFiltrate(name.substring(0, name.length() - 2));
/*     */           try
/*     */           {
/* 456 */             int temp = Integer.valueOf(name.substring(name.length() - 1, name.length())).intValue();
/* 457 */             this.stepNumV.addElement(new Integer(temp));
/*     */           } catch (Exception e) {
/* 459 */             this.stepNumV.addElement(new Integer(5));
/* 460 */             System.out.println("Format Error");
/*     */           }
/*     */         }
/*     */         
/* 464 */         name = butB.readLine();
/*     */       }
/* 466 */       this.backIcon = createImageIcon((String)this.demoImageV.elementAt(this.nextNum), "");
/* 467 */       this.backLabel.setIcon(this.backIcon);
/* 468 */       apprchTarget();
/*     */     }
/*     */     catch (Exception localException1) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String filtrateString(String str)
/*     */   {
/* 481 */     String filtS = "";
/* 482 */     for (int i = 0; i < str.length(); i++) {
/* 483 */       if (!str.substring(i, i + 1).equalsIgnoreCase(" ")) {
/* 484 */         filtS = String.valueOf(String.valueOf(filtS)).concat(String.valueOf(String.valueOf(str.substring(i, i + 1))));
/*     */       }
/*     */     }
/* 487 */     return filtS;
/*     */   }
/*     */   
/*     */   private void numFiltrate(String str) {
/* 491 */     String filtS = str;
/*     */     try {
/* 493 */       int x = Integer.valueOf(filtS.substring(filtS.indexOf("(") + 1, filtS.indexOf(","))).intValue();
/* 494 */       int y = Integer.valueOf(filtS.substring(filtS.indexOf(",") + 1, filtS.indexOf(")"))).intValue();
/* 495 */       this.startPoint.x = x;
/* 496 */       this.startPoint.y = y;
/*     */       
/* 498 */       filtS = str.substring(str.indexOf(")") + 1);
/* 499 */       x = Integer.valueOf(filtS.substring(filtS.indexOf("(") + 1, filtS.lastIndexOf(","))).intValue();
/* 500 */       y = Integer.valueOf(filtS.substring(filtS.lastIndexOf(",") + 1, filtS.indexOf(")"))).intValue();
/* 501 */       this.finishPoint.x = x;
/* 502 */       this.finishPoint.y = y;
/*     */       
/* 504 */       this.startPointV.addElement(new Point(this.startPoint.x, this.startPoint.y));
/* 505 */       this.finishPointV.addElement(new Point(this.finishPoint.x, this.finishPoint.y));
/*     */     }
/*     */     catch (Exception e) {
/* 508 */       System.out.println("Point is not Integer ! file name is :".concat(String.valueOf(String.valueOf(str))));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTestDemoPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */