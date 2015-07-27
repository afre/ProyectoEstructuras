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
/*     */ public class phase1
/*     */   extends JPanel
/*     */ {
/*  22 */   BorderLayout borderLayout1 = new BorderLayout();
/*     */   
/*     */   IORWaitingLinePane mainApp;
/*     */   Image img;
/*     */   Graphics gs;
/*     */   Dimension dim;
/*     */   int circlex;
/*     */   int circley;
/*     */   int recx;
/*  31 */   int customnum1 = 0;
/*  32 */   int flagnum = 1;
/*  33 */   boolean[] flag = new boolean[this.flagnum];
/*  34 */   int[] midflag = new int[this.flagnum];
/*  35 */   decNum dec = new decNum();
/*  36 */   Vector threadvec = new Vector();
/*     */   
/*  38 */   double aveWaitNum = 0.0D; double avgNuminSys = 0.0D; double avgTimeWait = 0.0D; double avgTimeinSys = 0.0D;
/*     */   
/*  40 */   int arrivals = 0;
/*  41 */   double elapseTime = 0.0D;
/*     */   
/*  43 */   double talWaitTime = 0.0D;
/*  44 */   double talServTime = 0.0D;
/*     */   
/*     */   long startime;
/*     */   
/*     */   long pausetime;
/*  49 */   int MSEL = 2000;
/*  50 */   boolean stop = false;
/*     */   
/*  52 */   NumberFormat nf = NumberFormat.getNumberInstance();
/*     */   
/*     */   public String Lq;
/*     */   
/*     */   public String Ls;
/*     */   public String Ws;
/*     */   public String Wq;
/*     */   
/*     */   public phase1(IORWaitingLinePane app)
/*     */   {
/*  62 */     this.mainApp = app;
/*     */     try {
/*  64 */       jbInit();
/*     */     }
/*     */     catch (Exception ex) {
/*  67 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   void jbInit() throws Exception {
/*  72 */     setLayout(this.borderLayout1);
/*  73 */     setSize(500, 300);
/*  74 */     this.dim = getSize();
/*  75 */     this.recx = (this.dim.width / 3);
/*  76 */     setRecNum(1);
/*     */     
/*  78 */     this.nf.setMaximumFractionDigits(2);
/*     */   }
/*     */   
/*     */   public synchronized void paint(Graphics g)
/*     */   {
/*  83 */     if (this.img == null)
/*  84 */       this.img = createImage(getSize().width, getSize().height);
/*  85 */     this.gs = this.img.getGraphics();
/*     */     
/*  87 */     this.gs.clearRect(0, 0, this.dim.width, this.dim.height);
/*  88 */     this.gs.setColor(Color.black);
/*     */     
/*  90 */     drawCircle();
/*  91 */     drawRec();
/*  92 */     if (this.stop == true) {
/*  93 */       this.Lq = this.nf.format(this.aveWaitNum);
/*  94 */       this.gs.drawLine(120, this.dim.height - 40, 130, this.dim.height - 40);
/*  95 */       this.gs.drawString("Lq = ".concat(String.valueOf(String.valueOf(this.Lq))), 120, this.dim.height - 25);
/*  96 */       this.Ls = new String();
/*  97 */       if (this.customnum1 != 0)
/*  98 */         this.Ls = this.nf.format(this.avgNuminSys);
/*  99 */       this.gs.drawLine(117, this.dim.height - 20, 127, this.dim.height - 20);
/* 100 */       this.gs.drawString("L = ".concat(String.valueOf(String.valueOf(this.Ls))), 120, this.dim.height - 5);
/*     */       
/* 102 */       this.Ws = new String();
/* 103 */       if (!this.Ls.equals("")) {
/* 104 */         double u = this.mainApp.u;
/* 105 */         this.Ws = this.nf.format(this.avgTimeinSys);
/* 106 */         this.gs.drawLine(this.dim.width / 2, this.dim.height - 20, this.dim.width / 2 + 10, this.dim.height - 20);
/* 107 */         this.gs.drawString("W = ".concat(String.valueOf(String.valueOf(this.Ws))), this.dim.width / 2, this.dim.height - 5);
/*     */       }
/* 109 */       this.Wq = this.nf.format(this.avgTimeWait);
/* 110 */       this.gs.drawLine(this.dim.width / 2, this.dim.height - 40, this.dim.width / 2 + 10, this.dim.height - 40);
/* 111 */       this.gs.drawString("Wq = ".concat(String.valueOf(String.valueOf(this.Wq))), this.dim.width / 2, this.dim.height - 25);
/*     */       
/* 113 */       this.mainApp.savePrintInfo();
/*     */     }
/* 115 */     g.drawImage(this.img, 0, 0, this);
/* 116 */     this.gs.dispose();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void statics()
/*     */   {
/* 125 */     if (this.customnum1 > 1)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 133 */       this.aveWaitNum = (this.talWaitTime / this.elapseTime);
/*     */       
/* 135 */       this.avgNuminSys = (this.aveWaitNum + this.talServTime / this.elapseTime);
/* 136 */       this.avgTimeWait = (this.talWaitTime / this.customnum1);
/* 137 */       this.avgTimeinSys = ((this.talServTime + this.talWaitTime) / this.customnum1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void update(Graphics g) {
/* 142 */     paint(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void drawCircle()
/*     */   {
/* 149 */     this.circley = (this.dim.height / 2);
/* 150 */     this.circlex = 80;
/* 151 */     this.gs.setColor(Color.blue);
/* 152 */     int cNum = this.dec.getcusnum();
/* 153 */     if (cNum == 0) {
/* 154 */       this.gs.fillOval(this.circlex + 2, this.circley + 4, 5, 5);
/* 155 */       this.gs.fillOval(this.circlex - 15 + 2, this.circley + 4, 5, 5);
/*     */     } else {
/* 157 */       if (cNum <= 4) {
/* 158 */         for (int i = 0; i < cNum; i++) {
/* 159 */           this.gs.fillOval(this.circlex - 15 * i, this.circley, 10, 10);
/*     */         }
/*     */       }
/* 162 */       this.gs.fillOval(this.circlex, this.circley, 10, 10);
/* 163 */       this.gs.fillOval(this.circlex - 15 + 2, this.circley + 4, 5, 5);
/* 164 */       this.gs.fillOval(this.circlex - 30 + 2, this.circley + 4, 5, 5);
/* 165 */       this.gs.fillOval(this.circlex - 45, this.circley, 10, 10);
/*     */     }
/* 167 */     this.gs.drawString("L1: ".concat(String.valueOf(String.valueOf(Integer.toString(cNum)))), 55, this.circley + 20 + 10);
/*     */     
/*     */ 
/* 170 */     int circlex1 = this.circlex + 228;
/* 171 */     if (this.customnum1 == 0) {
/* 172 */       this.gs.fillOval(circlex1 + 2, this.circley + 4, 5, 5);
/* 173 */       this.gs.fillOval(circlex1 - 15 + 2, this.circley + 4, 5, 5);
/*     */     } else {
/* 175 */       if (this.customnum1 <= 3) {
/* 176 */         for (int i = 0; i < this.customnum1; i++) {
/* 177 */           this.gs.fillOval(circlex1 - 15 * i, this.circley, 10, 10);
/*     */         }
/*     */       }
/* 180 */       this.gs.fillOval(circlex1, this.circley, 10, 10);
/* 181 */       this.gs.fillOval(circlex1 - 15 + 2, this.circley + 4, 5, 5);
/* 182 */       this.gs.fillOval(circlex1 - 30, this.circley, 10, 10);
/*     */     }
/* 184 */     this.gs.drawString("L2: ".concat(String.valueOf(String.valueOf(Integer.toString(this.customnum1)))), circlex1 - 70 + 45, this.circley + 20 + 10);
/*     */     
/* 186 */     this.gs.setColor(Color.black);
/*     */   }
/*     */   
/*     */   public synchronized void addElapse(double span)
/*     */   {
/* 191 */     this.elapseTime += span;
/*     */   }
/*     */   
/*     */   public synchronized void addServTime(double rantm) {
/* 195 */     this.talServTime += rantm;
/*     */   }
/*     */   
/*     */   public synchronized void addWaitTime(double interval) {
/* 199 */     this.talWaitTime += interval;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void drawRec()
/*     */   {
/* 206 */     int recnum = this.flagnum;
/*     */     
/* 208 */     int recspace = this.dim.height / (recnum + 1);
/* 209 */     for (int i = 0; i < recnum; i++) {
/* 210 */       this.gs.drawLine(this.circlex + 13, this.circley + 5, this.recx - 2, recspace * (i + 1) + 5);
/*     */       
/* 212 */       float pointx = (this.circlex + this.recx + 11) / 2;
/* 213 */       float pointy = (this.recx + recspace * (i + 1)) / 2 + 5;
/* 214 */       if (i < recnum / 2) {
/* 215 */         pointy -= 2;
/*     */ 
/*     */       }
/* 218 */       else if (i >= (recnum + 1) / 2) {
/* 219 */         pointy += 3;
/*     */       }
/* 221 */       if (this.midflag[i] == 1)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 226 */         this.gs.setColor(Color.blue);
/* 227 */         this.gs.fillOval((int)pointx, (int)pointy - 13, 10, 10);
/* 228 */         this.gs.setColor(Color.black);
/*     */       }
/*     */       
/* 231 */       this.gs.drawRect(this.recx, recspace * (i + 1) - 10, 30, 30);
/* 232 */       if ((this.flag[i] == 0) && (this.dec.getcusnum() == 0)) {
/* 233 */         this.gs.setColor(Color.orange);
/*     */       }
/*     */       else {
/* 236 */         this.gs.setColor(Color.blue);
/*     */       }
/* 238 */       this.gs.fillRect(this.recx, recspace * (i + 1) - 10, 30, 30);
/* 239 */       this.gs.setColor(Color.black);
/* 240 */       this.gs.drawLine(this.recx + 30 + 2, recspace * (i + 1) + 5, this.recx + 30 + 2 + this.recx - this.circlex - 10, this.circley + 5);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRecNum(int count)
/*     */   {
/* 249 */     this.flagnum = count;
/* 250 */     this.midflag = new int[this.flagnum];
/* 251 */     this.flag = new boolean[this.flagnum];
/* 252 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   class producecustom
/*     */     extends Thread
/*     */   {
/*     */     double arrivaltime;
/*     */     
/*     */ 
/*     */     producecustom() {}
/*     */     
/*     */     public void run()
/*     */     {
/*     */       for (;;)
/*     */       {
/* 268 */         this.arrivaltime = (-Math.log(1 - Math.random()) / phase1.this.mainApp.u);
/* 269 */         phase1.this.arrivals += 1;
/* 270 */         phase1.this.addElapse(this.arrivaltime);
/*     */         try {
/* 272 */           sleep((int)(this.arrivaltime * phase1.this.MSEL));
/*     */         }
/*     */         catch (Exception e) {
/* 275 */           System.out.println("error");
/*     */         }
/*     */         
/* 278 */         phase1.this.dec.plugcusnum();
/*     */         
/* 280 */         phase1.this.repaint();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   class sevice extends Thread {
/*     */     int sevNum;
/*     */     double wholetime;
/*     */     double servetime;
/*     */     double sleeptm;
/*     */     long entertime;
/*     */     long interval;
/*     */     boolean wait;
/*     */     
/* 294 */     sevice(int num) { this.sevNum = num; }
/*     */     
/*     */     public void run()
/*     */     {
/*     */       for (;;) {
/* 299 */         this.wait = phase1.this.dec.deccusnum();
/* 300 */         this.entertime = phase1.this.dec.getEnterTime();
/* 301 */         this.interval = (System.currentTimeMillis() - this.entertime);
/*     */         
/* 303 */         phase1.this.addWaitTime(this.interval * 1.0D / phase1.this.MSEL);
/*     */         
/* 305 */         this.servetime = (-Math.log(1 - Math.random()) / phase1.this.mainApp.a);
/* 306 */         phase1.this.addServTime(this.servetime);
/*     */         try
/*     */         {
/* 309 */           this.wholetime = (this.servetime * phase1.this.MSEL);
/*     */           
/* 311 */           phase1.this.midflag[this.sevNum] = 1;
/* 312 */           if (this.wait == false)
/* 313 */             phase1.this.flag[this.sevNum] = true;
/* 314 */           phase1.this.repaint();
/*     */           
/* 316 */           if (this.wholetime * 0.25D > 300.0D) {
/* 317 */             this.sleeptm = 300.0D;
/*     */           } else
/* 319 */             this.sleeptm = (this.wholetime * 0.25D);
/* 320 */           sleep((int)this.sleeptm);
/*     */           
/* 322 */           phase1.this.midflag[this.sevNum] = 0;
/* 323 */           phase1.this.flag[this.sevNum] = true;
/* 324 */           phase1.this.repaint();
/*     */           
/* 326 */           sleep((int)(this.wholetime - this.sleeptm));
/*     */         }
/*     */         catch (Exception e) {
/* 329 */           e.printStackTrace();
/*     */         }
/* 331 */         phase1.this.flag[this.sevNum] = false;
/*     */         
/* 333 */         phase1.this.customnum1 += 1;
/*     */         
/* 335 */         phase1.this.repaint();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void start()
/*     */   {
/* 345 */     this.stop = false;
/* 346 */     this.threadvec = new Vector();
/* 347 */     this.dec = new decNum();
/* 348 */     this.customnum1 = 0;
/* 349 */     this.aveWaitNum = 0.0D;
/* 350 */     this.avgNuminSys = 0.0D;
/* 351 */     this.avgTimeWait = 0.0D;
/* 352 */     this.avgTimeinSys = 0.0D;
/*     */     
/* 354 */     this.arrivals = 0;
/* 355 */     this.elapseTime = 0.0D;
/*     */     
/* 357 */     this.talWaitTime = 0.0D;
/* 358 */     this.talServTime = 0.0D;
/*     */     
/* 360 */     this.pausetime = 0L;
/* 361 */     repaint();
/*     */     
/*     */ 
/*     */ 
/* 365 */     for (int i = 0; i < this.flagnum; i++)
/*     */     {
/* 367 */       Thread tem = new phase1.sevice(i);
/* 368 */       this.threadvec.addElement(tem);
/* 369 */       tem.start();
/*     */     }
/* 371 */     Thread tem = new phase1.producecustom();
/* 372 */     this.threadvec.addElement(tem);
/* 373 */     tem.start();
/* 374 */     this.startime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public void suspend()
/*     */   {
/* 379 */     this.pausetime = System.currentTimeMillis();
/*     */     
/* 381 */     this.stop = true;
/* 382 */     for (int i = 0; i < this.threadvec.size(); i++) {
/* 383 */       ((Thread)this.threadvec.elementAt(i)).suspend();
/*     */     }
/* 385 */     statics();
/* 386 */     repaint();
/*     */   }
/*     */   
/*     */   public void again()
/*     */   {
/* 391 */     long intmit = System.currentTimeMillis() - this.pausetime;
/* 392 */     this.dec.reduceBreak(intmit);
/*     */     
/* 394 */     this.stop = false;
/* 395 */     for (int i = 0; i < this.threadvec.size(); i++) {
/* 396 */       ((Thread)this.threadvec.elementAt(i)).resume();
/*     */     }
/* 398 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void stop()
/*     */   {
/* 407 */     for (int i = 0; i < this.threadvec.size(); i++) {
/* 408 */       ((Thread)this.threadvec.elementAt(i)).stop();
/*     */     }
/*     */     
/*     */ 
/* 412 */     for (int i = 0; i < this.flagnum; i++) {
/* 413 */       this.flag[i] = false;
/* 414 */       this.midflag[i] = 0;
/*     */     }
/*     */     
/* 417 */     this.stop = true;
/*     */     
/* 419 */     statics();
/*     */     
/* 421 */     this.threadvec = new Vector();
/* 422 */     this.dec = new decNum();
/*     */     
/* 424 */     repaint();
/*     */   }
/*     */   
/*     */   public void setMSEL(int value) {
/* 428 */     this.MSEL = value;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\phase1.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */