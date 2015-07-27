/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.Timer;
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
/*     */ public class graphCanvasInv
/*     */   extends JPanel
/*     */   implements ActionListener
/*     */ {
/*  28 */   private static int CANVASIZE = 340;
/*  29 */   private static int MARGIN = 20;
/*  30 */   private static int WIDTH = 320;
/*     */   
/*     */ 
/*  33 */   private static int CENTERX = (WIDTH + MARGIN) / 2;
/*  34 */   private static int CENTERY = (WIDTH + MARGIN) / 2;
/*  35 */   private static int SMALLSIZE = 2;
/*     */   
/*     */ 
/*     */   public int globalZoom;
/*     */   
/*  40 */   private allConstraints currentCons = null;
/*  41 */   private Objective objFunction = null;
/*  42 */   private Intersection MaxIntersection = null;
/*  43 */   private Polygon optimalPolygon = null;
/*  44 */   private Segment lastSegment = null;
/*     */   
/*     */ 
/*  47 */   private boolean wantOptimal = false;
/*  48 */   private boolean needStepDraw = true;
/*  49 */   private int autostep = 4;
/*  50 */   private Timer t = new Timer(500, this);
/*  51 */   public boolean doAction = false;
/*  52 */   private boolean showOptimum = false;
/*     */   
/*     */   private IORLPLinearProgPane mainPane;
/*     */   
/*  56 */   Point opmapt1 = new Point(); Point opmapt2 = new Point();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public graphCanvasInv(IORLPLinearProgPane pane)
/*     */   {
/*  63 */     this.mainPane = pane;
/*     */     
/*  65 */     this.globalZoom = 1;
/*  66 */     this.currentCons = new allConstraints();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/*  74 */     g.setColor(Color.white);
/*  75 */     g.fillRect(0, 0, 340, 340);
/*  76 */     g.setColor(Color.cyan);
/*  77 */     g.fillRect(MARGIN, MARGIN, WIDTH - MARGIN, WIDTH - MARGIN);
/*  78 */     drawConstraints(g);
/*  79 */     drawFeaCones(g);
/*  80 */     if (this.wantOptimal)
/*  81 */       drawOptimal(g);
/*  82 */     drawAxis(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawAxis(Graphics g)
/*     */   {
/*  92 */     g.setColor(Color.black);
/*     */     
/*  94 */     g.drawLine(MARGIN, MARGIN, MARGIN, WIDTH);
/*  95 */     g.drawLine(MARGIN - 3, MARGIN + 5, MARGIN, MARGIN);
/*  96 */     g.drawLine(MARGIN + 3, MARGIN + 5, MARGIN, MARGIN);
/*     */     
/*  98 */     g.drawLine(MARGIN, WIDTH, WIDTH, WIDTH);
/*  99 */     g.drawLine(WIDTH - 5, WIDTH - 3, WIDTH, WIDTH);
/* 100 */     g.drawLine(WIDTH - 5, WIDTH + 3, WIDTH, WIDTH);
/* 101 */     g.drawString("x1", WIDTH + 5, WIDTH + 12);
/* 102 */     g.drawString("x2", MARGIN - 15, MARGIN - 3);
/* 103 */     g.drawString("0", MARGIN - 5, WIDTH + 12);
/*     */     
/* 105 */     Font origFont = g.getFont();
/* 106 */     g.setFont(new Font(origFont.getName(), origFont.getStyle(), 9));
/* 107 */     drawUnit(g);
/* 108 */     g.setFont(origFont);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawUnit(Graphics g)
/*     */   {
/* 117 */     float span = (float)(1.0D * (WIDTH - MARGIN) / (10.0D * this.globalZoom));
/* 118 */     int unit; int unit; if (span < 1.9D) {
/* 119 */       unit = 1; } else { int unit;
/* 120 */       if (span < 3.0D) {
/* 121 */         unit = 2; } else { int unit;
/* 122 */         if (span < 5.9D) {
/* 123 */           unit = 5; } else { int unit;
/* 124 */           if (span < 14.9D) {
/* 125 */             unit = 10;
/*     */           } else { int unit;
/* 127 */             if (span < 24.9D) {
/* 128 */               unit = 20;
/*     */             }
/*     */             else
/* 131 */               unit = 50;
/*     */           }
/*     */         } } }
/* 134 */     int xstep = unit * this.globalZoom;
/* 135 */     int ystep = unit * this.globalZoom;
/* 136 */     while ((xstep < WIDTH - MARGIN) && (ystep < WIDTH - MARGIN))
/*     */     {
/* 138 */       g.drawLine(MARGIN + xstep, WIDTH - 1, MARGIN + xstep, WIDTH + 1);
/* 139 */       if (unit < 10) {
/* 140 */         g.drawString("".concat(String.valueOf(String.valueOf(xstep / this.globalZoom))), MARGIN + xstep - 3, WIDTH + 12);
/* 141 */       } else if (unit < 50) {
/* 142 */         g.drawString("".concat(String.valueOf(String.valueOf(xstep / this.globalZoom))), MARGIN + xstep - 6, WIDTH + 12);
/*     */       } else {
/* 144 */         g.drawString("".concat(String.valueOf(String.valueOf(xstep / this.globalZoom))), MARGIN + xstep - 8, WIDTH + 12);
/*     */       }
/*     */       
/* 147 */       g.drawLine(MARGIN - 1, WIDTH - ystep, MARGIN + 1, WIDTH - ystep);
/* 148 */       if (unit < 10) {
/* 149 */         g.drawString("".concat(String.valueOf(String.valueOf(ystep / this.globalZoom))), 7, WIDTH - ystep + 5);
/*     */       } else {
/* 151 */         g.drawString("".concat(String.valueOf(String.valueOf(ystep / this.globalZoom))), 3, WIDTH - ystep + 5);
/*     */       }
/* 153 */       xstep += unit * this.globalZoom;
/* 154 */       ystep += unit * this.globalZoom;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setConstraints(allConstraints theConstraints)
/*     */   {
/* 163 */     this.currentCons = theConstraints;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void dispOptimal(Objective objf, Intersection optimalIntersection)
/*     */   {
/* 171 */     this.objFunction = objf;
/* 172 */     this.wantOptimal = true;
/* 173 */     this.showOptimum = false;
/* 174 */     this.needStepDraw = true;
/* 175 */     this.MaxIntersection = optimalIntersection;
/* 176 */     this.optimalPolygon = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clearOptimal()
/*     */   {
/* 184 */     this.objFunction = null;
/* 185 */     this.wantOptimal = false;
/* 186 */     this.MaxIntersection = null;
/* 187 */     this.optimalPolygon = null;
/* 188 */     this.lastSegment = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawConstraints(Graphics g)
/*     */   {
/* 196 */     int zoom = 1000;
/* 197 */     Constraint tempCons = null;
/*     */     
/* 199 */     if (this.currentCons == null) {
/* 200 */       this.globalZoom = 1;
/* 201 */       return;
/*     */     }
/*     */     
/* 204 */     Enumeration e = this.currentCons.vConstraints.elements();
/* 205 */     while (e.hasMoreElements())
/*     */     {
/* 207 */       tempCons = (Constraint)e.nextElement();
/* 208 */       int tranzoom = tempCons.getZoom();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 214 */       if ((tranzoom < zoom) && (tranzoom >= 1)) {
/* 215 */         zoom = tranzoom;
/*     */       }
/*     */     }
/* 218 */     zoom = decidezoom(zoom);
/*     */     
/* 220 */     if (zoom == 1000) {
/* 221 */       zoom = 1;
/*     */     }
/*     */     
/* 224 */     drawConstraints(g, zoom, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawConstraints(Graphics g, int zoom, boolean shade)
/*     */   {
/* 234 */     Constraint tempCons = null;
/* 235 */     int i = 3;
/* 236 */     Enumeration e = this.currentCons.vConstraints.elements();
/*     */     
/* 238 */     Enumeration fbx = this.currentCons.vFeasibleIntersections.elements();
/* 239 */     Intersection tempIntersection = null;
/* 240 */     Point stpt = null;Point edpt = null;
/*     */     
/*     */ 
/* 243 */     Polygon tempPolygon = null;
/* 244 */     Polygon tempPolygon2 = null;
/* 245 */     this.globalZoom = zoom;
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
/* 256 */     while (e.hasMoreElements())
/*     */     {
/* 258 */       tempPolygon = new Polygon();
/* 259 */       tempCons = (Constraint)e.nextElement();
/* 260 */       tempCons.withZoom(zoom);
/*     */       
/* 262 */       float tempslope = (float)(-1.0D * tempCons.x1_const / tempCons.x2_const);
/*     */       
/* 264 */       if (tempCons.inequating == 1)
/*     */       {
/* 266 */         if (tempslope >= 0) {
/* 267 */           if (tempCons.startp.y < tempCons.endp.y)
/*     */           {
/*     */ 
/* 270 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 271 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 272 */             tempPolygon.addPoint(0, 340);
/* 273 */             tempPolygon.addPoint(0, 0);
/* 274 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 276 */           else if (tempCons.startp.y > tempCons.endp.y) {
/* 277 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 278 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 279 */             tempPolygon.addPoint(0, 340);
/* 280 */             tempPolygon.addPoint(340, 340);
/* 281 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 283 */           else if (tempCons.startp.y == tempCons.endp.y)
/*     */           {
/*     */ 
/*     */ 
/* 287 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 288 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 289 */             tempPolygon.addPoint(0, 340);
/* 290 */             tempPolygon.addPoint(0, 0);
/* 291 */             tempPolygon.addPoint(340, 0);
/*     */           }
/*     */           
/*     */         }
/* 295 */         else if (tempCons.startp.y <= tempCons.endp.y)
/*     */         {
/* 297 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 298 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 299 */           tempPolygon.addPoint(340, 340);
/* 300 */           tempPolygon.addPoint(340, 0);
/* 301 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */         else {
/* 304 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 305 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 306 */           tempPolygon.addPoint(340, 340);
/* 307 */           tempPolygon.addPoint(340, 0);
/* 308 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */         
/*     */       }
/* 312 */       else if (tempCons.inequating == 2)
/*     */       {
/* 314 */         if (tempslope >= 0) {
/* 315 */           if (tempCons.startp.y < tempCons.endp.y) {
/* 316 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 317 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 318 */             tempPolygon.addPoint(0, 340);
/* 319 */             tempPolygon.addPoint(340, 340);
/* 320 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 322 */           else if (tempCons.startp.y > tempCons.endp.y) {
/* 323 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 324 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 325 */             tempPolygon.addPoint(0, 340);
/* 326 */             tempPolygon.addPoint(0, 0);
/* 327 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 329 */           else if (tempCons.startp.y == tempCons.endp.y) {
/* 330 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 331 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 332 */             tempPolygon.addPoint(0, 340);
/* 333 */             tempPolygon.addPoint(340, 340);
/* 334 */             tempPolygon.addPoint(340, 0);
/*     */           }
/*     */           
/*     */         }
/* 338 */         else if (tempCons.startp.y <= tempCons.endp.y)
/*     */         {
/* 340 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 341 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 342 */           tempPolygon.addPoint(340, 340);
/* 343 */           tempPolygon.addPoint(0, 340);
/* 344 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */         else
/*     */         {
/* 348 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 349 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 350 */           tempPolygon.addPoint(340, 340);
/* 351 */           tempPolygon.addPoint(0, 340);
/* 352 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 359 */       g.setColor(Color.white);
/* 360 */       g.fillPolygon(tempPolygon);
/* 361 */       tempPolygon = null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 366 */     g.setColor(Color.blue);
/* 367 */     e = this.currentCons.vConstraints.elements();
/* 368 */     while (e.hasMoreElements())
/*     */     {
/* 370 */       tempCons = (Constraint)e.nextElement();
/* 371 */       tempCons.withZoom(zoom);
/*     */       
/* 373 */       g.drawLine(tempCons.startp.x, tempCons.startp.y, tempCons.endp.x, tempCons.endp.y);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 381 */     if ((stpt != null) && (edpt != null)) {
/* 382 */       g.setColor(Color.magenta);
/* 383 */       g.drawLine(stpt.x, stpt.y, edpt.x, edpt.y);
/*     */     }
/* 385 */     g.setColor(Color.white);
/*     */     
/* 387 */     g.fillRect(0, 0, 20, 340);
/* 388 */     g.fillRect(320, 0, 22, 340);
/* 389 */     g.fillRect(0, 320, 340, 20);
/* 390 */     g.fillRect(0, 0, 340, 20);
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
/*     */   public void drawPoint(Graphics g, Point p, Color c)
/*     */   {
/* 404 */     g.setColor(c);
/* 405 */     g.fillOval(p.x - 3, p.y - 3, 6, 6);
/* 406 */     g.setColor(Color.black);
/* 407 */     g.drawOval(p.x - 3, p.y - 3, 6, 6);
/*     */   }
/*     */   
/*     */   public int decidezoom(int z)
/*     */   {
/* 412 */     if (this.currentCons == null)
/* 413 */       return z;
/* 414 */     Intersection MaxIntersection = null;
/* 415 */     Intersection tempIntersection = null;
/* 416 */     Enumeration e = this.currentCons.vFeasibleIntersections.elements();
/* 417 */     float maxvalue = 0.0F;
/*     */     
/* 419 */     while (e.hasMoreElements())
/*     */     {
/* 421 */       if (MaxIntersection == null)
/*     */       {
/* 423 */         MaxIntersection = (Intersection)e.nextElement();
/* 424 */         if ((MaxIntersection.x1 >= 0) && (MaxIntersection.x2 >= 0)) {
/* 425 */           maxvalue = Math.abs(MaxIntersection.x1) > Math.abs(MaxIntersection.x2) ? Math.abs(MaxIntersection.x1) : Math.abs(MaxIntersection.x2);
/*     */         }
/*     */         else {
/* 428 */           MaxIntersection = null;
/*     */         }
/*     */         
/*     */       }
/*     */       else
/*     */       {
/* 434 */         tempIntersection = (Intersection)e.nextElement();
/* 435 */         if ((tempIntersection.x1 >= 0) && (tempIntersection.x2 >= 0)) {
/* 436 */           float tempmaxvalue = tempIntersection.x1 > tempIntersection.x2 ? tempIntersection.x1 : tempIntersection.x2;
/* 437 */           if (tempmaxvalue > maxvalue) {
/* 438 */             MaxIntersection = tempIntersection;
/* 439 */             maxvalue = tempmaxvalue;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 448 */     int tempzoom = z / 2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 456 */     while (maxvalue * tempzoom > ' ')
/*     */     {
/* 458 */       tempzoom /= 2;
/*     */     }
/*     */     
/* 461 */     return tempzoom * 2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawFeaCones(Graphics g)
/*     */   {
/* 469 */     if (this.currentCons == null) {
/* 470 */       return;
/*     */     }
/* 472 */     Intersection tempIntersection = null;
/* 473 */     Enumeration e = this.currentCons.vFeasibleIntersections.elements();
/*     */     
/* 475 */     while (e.hasMoreElements())
/*     */     {
/* 477 */       tempIntersection = (Intersection)e.nextElement();
/* 478 */       Point conept = new Point(MARGIN + (int)(tempIntersection.x1 * this.globalZoom), WIDTH - (int)(tempIntersection.x2 * this.globalZoom));
/*     */       
/* 480 */       drawPoint(g, conept, Color.orange);
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
/*     */ 
/*     */   public void drawOptimal(Graphics g)
/*     */   {
/* 494 */     if (this.MaxIntersection != null)
/*     */     {
/* 496 */       float optz = 0.0F;
/* 497 */       while (this.doAction) {
/* 498 */         Point OptimalPoint = new Point((int)(MARGIN + this.MaxIntersection.x1 * this.globalZoom), (int)(WIDTH - this.MaxIntersection.x2 * this.globalZoom));
/* 499 */         drawPoint(g, OptimalPoint, Color.red);
/* 500 */         this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/* 501 */         optz = this.mainPane.input_Z;
/*     */         
/*     */ 
/* 504 */         this.doAction = false;
/* 505 */         repaint();
/* 506 */         this.needStepDraw = false;
/*     */         
/*     */ 
/* 509 */         if (Math.round(optz * 100) == Math.round(this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2) * 100)) {
/* 510 */           this.showOptimum = true;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 516 */       if (!this.needStepDraw)
/*     */       {
/* 518 */         approachOptimal(g, optz);
/* 519 */         drawAxis(g);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 524 */         float recurval = optz * (1.0F - this.autostep / 10.0F);
/*     */         
/* 526 */         if (Math.abs(optz) < 0.001D) {
/* 527 */           recurval = this.autostep / 10.0F;
/* 528 */           if (this.globalZoom == 1) {
/* 529 */             this.globalZoom = 160;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 540 */         drawAxis(g);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 547 */       int opxpix = (int)(this.globalZoom * this.MaxIntersection.x1);
/* 548 */       int opypix = (int)(this.globalZoom * this.MaxIntersection.x2);
/*     */       
/*     */ 
/* 551 */       if (this.optimalPolygon == null) {
/* 552 */         this.optimalPolygon = new Polygon();
/*     */         
/*     */ 
/* 555 */         this.optimalPolygon.addPoint(MARGIN + opxpix + 10, WIDTH - (opypix + 5));
/* 556 */         this.optimalPolygon.addPoint(MARGIN + opxpix + 5, WIDTH - (opypix + 10));
/* 557 */         this.optimalPolygon.addPoint(MARGIN + opxpix + 2, WIDTH - (opypix + 2));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 567 */       if (this.showOptimum) {
/* 568 */         g.setColor(Color.red);
/* 569 */         g.fillPolygon(this.optimalPolygon);
/* 570 */         g.drawLine(MARGIN + opxpix, WIDTH - opypix, MARGIN + opxpix + 20, WIDTH - (opypix + 20));
/* 571 */         g.drawString("Optimum", MARGIN + opxpix + 23, WIDTH - (opypix + 20));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 576 */       g.setColor(Color.blue);
/* 577 */       Enumeration e = this.currentCons.vConstraints.elements();
/*     */       
/* 579 */       while (e.hasMoreElements())
/*     */       {
/* 581 */         Constraint tempCons = (Constraint)e.nextElement();
/* 582 */         tempCons.withZoom(this.globalZoom);
/* 583 */         g.drawLine(tempCons.startp.x, tempCons.startp.y, tempCons.endp.x, tempCons.endp.y);
/*     */       }
/*     */       
/* 586 */       g.setColor(Color.white);
/* 587 */       g.fillRect(0, 0, 20, 340);
/* 588 */       g.fillRect(320, 0, 22, 340);
/* 589 */       g.fillRect(0, 320, 340, 20);
/* 590 */       g.fillRect(0, 0, 340, 20);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void approachOptimal(Graphics g, float critval)
/*     */   {
/* 602 */     float opcept1x = critval / this.objFunction.x1_coef;
/* 603 */     float opcept2y = critval / this.objFunction.x2_coef;
/*     */     
/* 605 */     System.out.println(String.valueOf(String.valueOf(new StringBuffer("X1 ==").append(this.objFunction.x1_coef).append("    X2 ==").append(this.objFunction.x2_coef))));
/* 606 */     float NormalSlope = -opcept2y / opcept1x;
/* 607 */     int w = WIDTH;
/*     */     
/* 609 */     if ((opcept1x > 0) && (opcept2y > 0)) {
/* 610 */       this.opmapt1 = new Point((int)(MARGIN + opcept1x * this.globalZoom), WIDTH);
/* 611 */       this.opmapt2 = new Point(MARGIN, (int)(WIDTH - opcept2y * this.globalZoom));
/*     */     }
/* 613 */     else if ((opcept1x > 0) && (opcept2y < 0)) {
/* 614 */       float wx = w;
/* 615 */       float wy = wx * NormalSlope;
/* 616 */       this.opmapt1 = new Point((int)(MARGIN + opcept1x * this.globalZoom) + (int)wx, WIDTH - (int)wy);
/* 617 */       this.opmapt2 = new Point(MARGIN, (int)(WIDTH - opcept2y * this.globalZoom));
/*     */ 
/*     */     }
/* 620 */     else if ((opcept1x < 0) && (opcept2y > 0)) {
/* 621 */       float wx = w;
/* 622 */       float wy = wx * NormalSlope;
/* 623 */       this.opmapt1 = new Point((int)(MARGIN + opcept1x * this.globalZoom), WIDTH);
/* 624 */       this.opmapt2 = new Point(MARGIN + (int)wx, (int)(WIDTH - opcept2y * this.globalZoom) - (int)wy);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 631 */     System.out.println("mainPane.isShowLine==".concat(String.valueOf(String.valueOf(this.mainPane.isShowLine))));
/* 632 */     System.out.println();
/* 633 */     if (this.lastSegment != null)
/*     */     {
/* 635 */       if (this.mainPane.isShowLine)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 642 */         this.mainPane.startPoint.x = this.opmapt1.x;
/* 643 */         this.mainPane.startPoint.y = this.opmapt1.y;
/*     */         
/* 645 */         this.mainPane.stopPoint.x = this.opmapt2.x;
/* 646 */         this.mainPane.stopPoint.y = this.opmapt2.y;
/*     */       }
/*     */     }
/* 649 */     System.out.println(String.valueOf(String.valueOf(new StringBuffer("test ==").append(this.mainPane.startPoint).append("    test 1 ==").append(this.mainPane.stopPoint))));
/* 650 */     if (this.showOptimum) {
/* 651 */       g.setColor(Color.red);
/*     */     } else {
/* 653 */       g.setColor(Color.black);
/*     */     }
/* 655 */     g.drawLine(this.mainPane.startPoint.x, this.mainPane.startPoint.y, this.mainPane.stopPoint.x, this.mainPane.stopPoint.y);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 660 */     g.setColor(Color.yellow);
/* 661 */     g.fillRect(0, 0, 20, 340);
/* 662 */     g.fillRect(320, 0, 22, 340);
/* 663 */     g.fillRect(0, 320, 340, 20);
/* 664 */     g.fillRect(0, 0, 340, 20);
/*     */     
/* 666 */     this.lastSegment = null;
/* 667 */     this.lastSegment = new Segment(this.opmapt1, this.opmapt2);
/* 668 */     g.setColor(Color.blue);
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {}
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\graphCanvasInv.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */