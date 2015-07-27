/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.io.PrintStream;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class phase2SA
/*     */   extends JPanel
/*     */ {
/*  22 */   BorderLayout borderLayout1 = new BorderLayout();
/*     */   
/*     */   IORSAWaitingLinePane mainApp;
/*     */   Image img;
/*     */   Graphics gs;
/*     */   Dimension dim;
/*     */   int circlex;
/*     */   int circley;
/*     */   int circlex1;
/*     */   int recx;
/*  32 */   int customnum2 = 0;
/*  33 */   int flagnum = 1;
/*  34 */   boolean[] flag = new boolean[this.flagnum];
/*  35 */   int[] midflag = new int[this.flagnum];
/*  36 */   int flagnum1 = 1;
/*  37 */   boolean[] flag1 = new boolean[this.flagnum1];
/*  38 */   int[] midflag1 = new int[this.flagnum1];
/*     */   
/*  40 */   decNum dec = new decNum();
/*  41 */   decNum dec1 = new decNum();
/*  42 */   Vector threadvec = new Vector();
/*     */   
/*  44 */   double aveWaitNum = 0.0D; double avgNuminSys = 0.0D; double avgTimeWait = 0.0D; double avgTimeinSys = 0.0D;
/*     */   
/*  46 */   int arrivals = 0;
/*  47 */   double elapseTime = 0.0D;
/*     */   
/*  49 */   double talWaitTime = 0.0D;
/*  50 */   double talServTime = 0.0D;
/*     */   
/*     */   long startime;
/*     */   long pausetime;
/*  54 */   int MSEL = 2000;
/*  55 */   boolean stop = false;
/*     */   
/*  57 */   NumberFormat nf = NumberFormat.getNumberInstance();
/*     */   
/*     */   public String Lq;
/*     */   
/*     */   public String Ls;
/*     */   
/*     */   public String Ws;
/*     */   public String Wq;
/*     */   
/*     */   public phase2SA(IORSAWaitingLinePane app)
/*     */   {
/*  68 */     this.mainApp = app;
/*     */     try {
/*  70 */       jbInit();
/*     */     }
/*     */     catch (Exception ex) {
/*  73 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   void jbInit() throws Exception {
/*  78 */     setLayout(this.borderLayout1);
/*  79 */     setSize(500, 300);
/*  80 */     this.dim = getSize();
/*  81 */     this.recx = (this.dim.width / 4);
/*  82 */     setRecNum(1);
/*  83 */     setRecNum1(1);
/*     */     
/*  85 */     this.nf.setMaximumFractionDigits(2);
/*     */   }
/*     */   
/*     */   public synchronized void paint(Graphics g)
/*     */   {
/*  90 */     if (this.img == null)
/*  91 */       this.img = createImage(getSize().width, getSize().height);
/*  92 */     this.gs = this.img.getGraphics();
/*     */     
/*  94 */     this.gs.clearRect(0, 0, this.dim.width, this.dim.height);
/*  95 */     this.gs.setColor(Color.black);
/*  96 */     drawCircle();
/*  97 */     drawRec();
/*  98 */     if (this.stop == true) {
/*  99 */       this.Lq = this.nf.format(this.aveWaitNum);
/* 100 */       this.gs.drawLine(120, this.dim.height - 40, 130, this.dim.height - 40);
/* 101 */       this.gs.drawString("Lq = ".concat(String.valueOf(String.valueOf(this.Lq))), 120, this.dim.height - 25);
/*     */       
/* 103 */       this.Ls = new String();
/* 104 */       if (this.customnum2 != 0)
/* 105 */         this.Ls = this.nf.format(this.avgNuminSys);
/* 106 */       this.gs.drawLine(117, this.dim.height - 20, 127, this.dim.height - 20);
/* 107 */       this.gs.drawString("L = ".concat(String.valueOf(String.valueOf(this.Ls))), 120, this.dim.height - 5);
/*     */       
/* 109 */       this.Ws = new String();
/* 110 */       if (!this.Ls.equals("")) {
/* 111 */         double u = this.mainApp.u;
/* 112 */         this.Ws = this.nf.format(this.avgTimeinSys);
/* 113 */         this.gs.drawLine(this.dim.width / 2, this.dim.height - 20, this.dim.width / 2 + 10, this.dim.height - 20);
/* 114 */         this.gs.drawString("W = ".concat(String.valueOf(String.valueOf(this.Ws))), this.dim.width / 2, this.dim.height - 5);
/*     */       }
/* 116 */       this.Wq = this.nf.format(this.avgTimeWait);
/* 117 */       this.gs.drawLine(this.dim.width / 2, this.dim.height - 40, this.dim.width / 2 + 10, this.dim.height - 40);
/* 118 */       this.gs.drawString("Wq = ".concat(String.valueOf(String.valueOf(this.Wq))), this.dim.width / 2, this.dim.height - 25);
/*     */       
/* 120 */       this.mainApp.savePrintInfo();
/*     */     }
/* 122 */     g.drawImage(this.img, 0, 0, this);
/* 123 */     this.gs.dispose();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void statics()
/*     */   {
/* 132 */     if (this.customnum2 > 1)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 139 */       this.aveWaitNum = (this.talWaitTime / this.elapseTime);
/*     */       
/* 141 */       this.avgNuminSys = (this.aveWaitNum + this.talServTime / this.elapseTime);
/* 142 */       this.avgTimeWait = (this.talWaitTime / this.customnum2);
/* 143 */       this.avgTimeinSys = ((this.talServTime + this.talWaitTime) / this.customnum2);
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void addElapse(double span) {
/* 148 */     this.elapseTime += span;
/*     */   }
/*     */   
/*     */   public synchronized void addServTime(double tim) {
/* 152 */     this.talServTime += tim;
/*     */   }
/*     */   
/*     */   public synchronized void addWaitTime(double interval) {
/* 156 */     this.talWaitTime += interval;
/*     */   }
/*     */   
/*     */   public void update(Graphics g) {
/* 160 */     paint(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void drawCircle()
/*     */   {
/* 168 */     this.circley = (this.dim.height / 2);
/* 169 */     this.circlex = 80;
/* 170 */     this.gs.setColor(Color.blue);
/* 171 */     int cNum = this.dec.getcusnum();
/* 172 */     if (cNum == 0) {
/* 173 */       this.gs.fillOval(this.circlex + 2, this.circley + 4, 5, 5);
/* 174 */       this.gs.fillOval(this.circlex - 15 + 2, this.circley + 4, 5, 5);
/*     */     } else {
/* 176 */       if (cNum <= 4) {
/* 177 */         for (int i = 0; i < cNum; i++) {
/* 178 */           this.gs.fillOval(this.circlex - 15 * i, this.circley, 10, 10);
/*     */         }
/*     */       }
/* 181 */       this.gs.fillOval(this.circlex, this.circley, 10, 10);
/* 182 */       this.gs.fillOval(this.circlex - 15 + 2, this.circley + 4, 5, 5);
/* 183 */       this.gs.fillOval(this.circlex - 30 + 2, this.circley + 4, 5, 5);
/* 184 */       this.gs.fillOval(this.circlex - 45, this.circley, 10, 10);
/*     */     }
/* 186 */     this.gs.drawString("L1: ".concat(String.valueOf(String.valueOf(Integer.toString(cNum)))), 55, this.circley + 20 + 10);
/*     */     
/*     */ 
/* 189 */     this.circlex1 = (this.circlex + 140);
/* 190 */     int cNum1 = this.dec1.getcusnum();
/* 191 */     if (cNum1 == 0) {
/* 192 */       this.gs.fillOval(this.circlex1 + 2, this.circley + 4, 5, 5);
/* 193 */       this.gs.fillOval(this.circlex1 - 15 + 2, this.circley + 4, 5, 5);
/*     */     } else {
/* 195 */       if (cNum1 <= 3) {
/* 196 */         for (int i = 0; i < cNum1; i++) {
/* 197 */           this.gs.fillOval(this.circlex1 - 15 * i, this.circley, 10, 10);
/*     */         }
/*     */       }
/* 200 */       this.gs.fillOval(this.circlex1, this.circley, 10, 10);
/* 201 */       this.gs.fillOval(this.circlex1 - 15 + 2, this.circley + 4, 5, 5);
/*     */       
/* 203 */       this.gs.fillOval(this.circlex1 - 30, this.circley, 10, 10);
/*     */     }
/* 205 */     this.gs.drawString("L2: ".concat(String.valueOf(String.valueOf(Integer.toString(cNum1)))), this.circlex1 - 70 + 45, this.circley + 20 + 10);
/*     */     
/*     */ 
/* 208 */     int circlex2 = this.circlex + 280;
/* 209 */     if (this.customnum2 == 0) {
/* 210 */       this.gs.fillOval(circlex2 + 2, this.circley + 4, 5, 5);
/* 211 */       this.gs.fillOval(circlex2 - 15 + 2, this.circley + 4, 5, 5);
/*     */     } else {
/* 213 */       if (this.customnum2 <= 3) {
/* 214 */         for (int i = 0; i < this.customnum2; i++) {
/* 215 */           this.gs.fillOval(circlex2 - 15 * i, this.circley, 10, 10);
/*     */         }
/*     */       }
/* 218 */       this.gs.fillOval(circlex2, this.circley, 10, 10);
/* 219 */       this.gs.fillOval(circlex2 - 15 + 2, this.circley + 4, 5, 5);
/*     */       
/* 221 */       this.gs.fillOval(circlex2 - 30, this.circley, 10, 10);
/*     */     }
/* 223 */     this.gs.drawString("L3: ".concat(String.valueOf(String.valueOf(Integer.toString(this.customnum2)))), circlex2 - 70 + 45, this.circley + 20 + 10);
/*     */     
/* 225 */     this.gs.setColor(Color.black);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void drawRec()
/*     */   {
/* 232 */     int recnum = this.flagnum;
/*     */     
/* 234 */     int recspace = this.dim.height / (recnum + 1);
/* 235 */     for (int i = 0; i < recnum; i++) {
/* 236 */       this.gs.drawLine(this.circlex + 13, this.circley + 5, this.recx - 2, recspace * (i + 1) + 5);
/*     */       
/* 238 */       float pointx = (this.circlex + this.recx + 11) / 2;
/* 239 */       float pointy = (this.circley + recspace * (i + 1)) / 2 + 13;
/* 240 */       if (i < recnum / 2) {
/* 241 */         pointy -= 6;
/*     */ 
/*     */       }
/* 244 */       else if (i >= (recnum + 1) / 2) {
/* 245 */         pointy += 4;
/*     */       }
/*     */       
/* 248 */       if (this.midflag[i] == 1)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 253 */         this.gs.setColor(Color.blue);
/* 254 */         this.gs.fillOval((int)pointx, (int)pointy - 13, 10, 10);
/* 255 */         this.gs.setColor(Color.black);
/*     */       }
/*     */       
/* 258 */       this.gs.drawRect(this.recx, recspace * (i + 1) - 10, 30, 30);
/* 259 */       if ((this.flag[i] == 0) && (this.dec.getcusnum() == 0)) {
/* 260 */         this.gs.setColor(Color.orange);
/*     */       }
/*     */       else {
/* 263 */         this.gs.setColor(Color.blue);
/*     */       }
/* 265 */       this.gs.fillRect(this.recx, recspace * (i + 1) - 10, 30, 30);
/* 266 */       this.gs.setColor(Color.black);
/* 267 */       this.gs.drawLine(this.recx + 30 + 2, recspace * (i + 1) + 5, this.recx + 30 + 2 + this.recx - this.circlex - 10 - 5, this.circley + 5);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 272 */     int recspace1 = this.dim.height / (this.flagnum1 + 1);
/* 273 */     int recx1 = this.recx + 140;
/* 274 */     for (int i = 0; i < this.flagnum1; i++) {
/* 275 */       this.gs.drawLine(this.circlex1 + 13, this.circley + 5, recx1 - 2, recspace1 * (i + 1) + 5);
/*     */       
/* 277 */       float pointx = (this.circlex1 + recx1 + 11) / 2;
/* 278 */       float pointy = (this.circley + recspace1 * (i + 1)) / 2 + 13;
/* 279 */       if (i < this.flagnum1 / 2) {
/* 280 */         pointy -= 6;
/*     */ 
/*     */       }
/* 283 */       else if (i >= (this.flagnum1 + 1) / 2) {
/* 284 */         pointy += 4;
/*     */       }
/* 286 */       if (this.midflag1[i] == 1)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 291 */         this.gs.setColor(Color.blue);
/* 292 */         this.gs.fillOval((int)pointx, (int)pointy - 13, 10, 10);
/* 293 */         this.gs.setColor(Color.black);
/*     */       }
/*     */       
/*     */ 
/* 297 */       this.gs.drawRect(recx1, recspace1 * (i + 1) - 10, 30, 30);
/* 298 */       if ((this.flag1[i] == 0) && (this.dec1.getcusnum() == 0)) {
/* 299 */         this.gs.setColor(Color.orange);
/*     */       }
/*     */       else {
/* 302 */         this.gs.setColor(Color.blue);
/*     */       }
/* 304 */       this.gs.fillRect(recx1, recspace1 * (i + 1) - 10, 30, 30);
/* 305 */       this.gs.setColor(Color.black);
/* 306 */       int X = recx1 + 30 + 2 + recx1 - this.circlex1 - 10 - 5;
/* 307 */       int Y = this.circley + 5;
/* 308 */       this.gs.drawLine(recx1 + 30 + 2, recspace1 * (i + 1) + 5, X, Y);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setRecNum(int count)
/*     */   {
/* 316 */     this.flagnum = count;
/* 317 */     this.midflag = new int[this.flagnum];
/* 318 */     this.flag = new boolean[this.flagnum];
/* 319 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setRecNum1(int count)
/*     */   {
/* 326 */     this.flagnum1 = count;
/* 327 */     this.midflag1 = new int[this.flagnum1];
/* 328 */     this.flag1 = new boolean[this.flagnum1];
/* 329 */     repaint();
/*     */   }
/*     */   
/*     */   class producecustom extends Thread
/*     */   {
/*     */     double arrivaltime;
/*     */     
/*     */     producecustom() {}
/*     */     
/*     */     public void run()
/*     */     {
/*     */       for (;;) {
/* 341 */         this.arrivaltime = (-Math.log(1 - Math.random()) / phase2SA.this.mainApp.u);
/* 342 */         phase2SA.this.arrivals += 1;
/* 343 */         phase2SA.this.addElapse(this.arrivaltime);
/*     */         try {
/* 345 */           sleep((int)(this.arrivaltime * phase2SA.this.MSEL));
/*     */         }
/*     */         catch (Exception e) {
/* 348 */           System.out.println("error");
/*     */         }
/* 350 */         phase2SA.this.dec.plugcusnum();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   class sevice extends Thread { int sevNum;
/*     */     double wholetime;
/*     */     double servetime;
/*     */     double sleeptm;
/*     */     long entertime;
/*     */     long interval;
/*     */     boolean wait;
/*     */     
/* 363 */     sevice(int num) { this.sevNum = num; }
/*     */     
/*     */     public void run()
/*     */     {
/*     */       for (;;) {
/* 368 */         this.wait = phase2SA.this.dec.deccusnum();
/* 369 */         this.entertime = phase2SA.this.dec.getEnterTime();
/* 370 */         this.interval = (System.currentTimeMillis() - this.entertime);
/*     */         
/* 372 */         phase2SA.this.addWaitTime(this.interval * 1.0D / phase2SA.this.MSEL);
/*     */         
/* 374 */         this.servetime = (-Math.log(1 - Math.random()) / phase2SA.this.mainApp.a);
/* 375 */         phase2SA.this.addServTime(this.servetime);
/*     */         try
/*     */         {
/* 378 */           this.wholetime = (this.servetime * phase2SA.this.MSEL);
/*     */           
/* 380 */           phase2SA.this.midflag[this.sevNum] = 1;
/* 381 */           if (this.wait == false) {
/* 382 */             phase2SA.this.flag[this.sevNum] = true;
/*     */           }
/* 384 */           phase2SA.this.repaint();
/* 385 */           if (this.wholetime * 0.25D > 300.0D) {
/* 386 */             this.sleeptm = 300.0D;
/*     */           } else
/* 388 */             this.sleeptm = (this.wholetime * 0.25D);
/* 389 */           sleep((int)this.sleeptm);
/*     */           
/* 391 */           phase2SA.this.midflag[this.sevNum] = 0;
/* 392 */           phase2SA.this.flag[this.sevNum] = true;
/* 393 */           phase2SA.this.repaint();
/*     */           
/* 395 */           sleep((int)(this.wholetime - this.sleeptm));
/*     */         }
/*     */         catch (Exception e) {
/* 398 */           e.printStackTrace();
/*     */         }
/* 400 */         phase2SA.this.flag[this.sevNum] = false;
/* 401 */         phase2SA.this.dec1.plugcusnum();
/*     */         
/* 403 */         phase2SA.this.repaint();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   class sevice1 extends Thread {
/*     */     int sevNum;
/*     */     boolean wait;
/*     */     long entertime;
/*     */     long interval;
/*     */     double wholetime;
/*     */     double servetime;
/*     */     double sleeptm;
/*     */     
/* 417 */     sevice1(int num) { this.sevNum = num; }
/*     */     
/*     */     public void run()
/*     */     {
/*     */       for (;;)
/*     */       {
/* 423 */         this.wait = phase2SA.this.dec1.deccusnum();
/* 424 */         this.entertime = phase2SA.this.dec1.getEnterTime();
/* 425 */         this.interval = (System.currentTimeMillis() - this.entertime);
/*     */         
/* 427 */         phase2SA.this.addWaitTime(this.interval * 1.0D / phase2SA.this.MSEL);
/*     */         
/* 429 */         this.servetime = (-Math.log(1 - Math.random()) / phase2SA.this.mainApp.a);
/* 430 */         phase2SA.this.addServTime(this.servetime);
/*     */         try
/*     */         {
/* 433 */           this.wholetime = (this.servetime * phase2SA.this.MSEL);
/*     */           
/* 435 */           phase2SA.this.midflag1[this.sevNum] = 1;
/* 436 */           if (this.wait == false)
/* 437 */             phase2SA.this.flag1[this.sevNum] = true;
/* 438 */           phase2SA.this.repaint();
/*     */           
/* 440 */           if (this.wholetime * 0.25D > 300.0D) {
/* 441 */             this.sleeptm = 300.0D;
/*     */           } else
/* 443 */             this.sleeptm = (this.wholetime * 0.25D);
/* 444 */           sleep((int)this.sleeptm);
/*     */           
/* 446 */           phase2SA.this.midflag1[this.sevNum] = 0;
/* 447 */           phase2SA.this.flag1[this.sevNum] = true;
/* 448 */           phase2SA.this.repaint();
/*     */           
/* 450 */           sleep((int)(this.wholetime - this.sleeptm));
/*     */         }
/*     */         catch (Exception e) {
/* 453 */           e.printStackTrace();
/*     */         }
/* 455 */         phase2SA.this.flag1[this.sevNum] = false;
/* 456 */         phase2SA.this.customnum2 += 1;
/*     */         
/* 458 */         phase2SA.this.repaint();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void start()
/*     */   {
/* 468 */     this.stop = false;
/*     */     
/* 470 */     this.threadvec = new Vector();
/* 471 */     this.dec1 = new decNum();
/* 472 */     this.dec = new decNum();
/*     */     
/* 474 */     this.customnum2 = 0;
/*     */     
/* 476 */     this.aveWaitNum = 0.0D;
/* 477 */     this.avgNuminSys = 0.0D;
/* 478 */     this.avgTimeWait = 0.0D;
/* 479 */     this.avgTimeinSys = 0.0D;
/*     */     
/* 481 */     this.arrivals = 0;
/* 482 */     this.elapseTime = 0.0D;
/*     */     
/* 484 */     this.talWaitTime = 0.0D;
/* 485 */     this.talServTime = 0.0D;
/*     */     
/* 487 */     this.pausetime = 0L;
/* 488 */     repaint();
/*     */     
/*     */ 
/* 491 */     for (int i = 0; i < this.flagnum; i++)
/*     */     {
/* 493 */       Thread tem = new phase2SA.sevice(i);
/* 494 */       this.threadvec.addElement(tem);
/* 495 */       tem.start();
/*     */     }
/*     */     
/* 498 */     for (int i = 0; i < this.flagnum1; i++)
/*     */     {
/* 500 */       Thread tem = new phase2SA.sevice1(i);
/* 501 */       this.threadvec.addElement(tem);
/* 502 */       tem.start();
/*     */     }
/*     */     
/* 505 */     Thread tem = new phase2SA.producecustom();
/* 506 */     this.threadvec.addElement(tem);
/* 507 */     tem.start();
/*     */     
/* 509 */     this.startime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public void suspend()
/*     */   {
/* 514 */     this.pausetime = System.currentTimeMillis();
/*     */     
/* 516 */     this.stop = true;
/* 517 */     for (int i = 0; i < this.threadvec.size(); i++) {
/* 518 */       ((Thread)this.threadvec.elementAt(i)).suspend();
/*     */     }
/* 520 */     statics();
/* 521 */     repaint();
/*     */   }
/*     */   
/*     */   public void again()
/*     */   {
/* 526 */     long intmit = System.currentTimeMillis() - this.pausetime;
/* 527 */     this.dec.reduceBreak(intmit);
/* 528 */     this.dec1.reduceBreak(intmit);
/*     */     
/* 530 */     this.stop = false;
/* 531 */     for (int i = 0; i < this.threadvec.size(); i++) {
/* 532 */       ((Thread)this.threadvec.elementAt(i)).resume();
/*     */     }
/* 534 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void stop()
/*     */   {
/* 543 */     for (int i = 0; i < this.threadvec.size(); i++) {
/* 544 */       ((Thread)this.threadvec.elementAt(i)).stop();
/*     */     }
/*     */     
/* 547 */     for (int i = 0; i < this.flagnum; i++) {
/* 548 */       this.flag[i] = false;
/* 549 */       this.midflag[i] = 0;
/*     */     }
/* 551 */     for (int i = 0; i < this.flagnum1; i++) {
/* 552 */       this.flag1[i] = false;
/* 553 */       this.midflag1[i] = 0;
/*     */     }
/* 555 */     this.stop = true;
/*     */     
/* 557 */     statics();
/*     */     
/* 559 */     this.threadvec = new Vector();
/* 560 */     this.dec1 = new decNum();
/* 561 */     this.dec = new decNum();
/*     */     
/* 563 */     repaint();
/*     */   }
/*     */   
/*     */   public void setMSEL(int value) {
/* 567 */     this.MSEL = value;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\phase2SA.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */