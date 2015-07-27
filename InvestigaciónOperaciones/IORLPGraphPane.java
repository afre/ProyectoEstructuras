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
/*     */ public class IORLPGraphPane
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORLPGraphPane()
/*     */   {
/*  60 */     this.globalZoom = 1;
/*  61 */     this.currentCons = new allConstraints();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/*  69 */     g.setColor(Color.white);
/*  70 */     g.fillRect(0, 0, 340, 340);
/*  71 */     g.setColor(Color.cyan);
/*  72 */     g.fillRect(MARGIN, MARGIN, WIDTH - MARGIN, WIDTH - MARGIN);
/*  73 */     drawConstraints(g);
/*  74 */     drawFeaCones(g);
/*  75 */     if (this.wantOptimal)
/*  76 */       drawOptimal(g);
/*  77 */     drawAxis(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawAxis(Graphics g)
/*     */   {
/*  87 */     g.setColor(Color.black);
/*     */     
/*  89 */     g.drawLine(MARGIN, MARGIN, MARGIN, WIDTH);
/*  90 */     g.drawLine(MARGIN - 3, MARGIN + 5, MARGIN, MARGIN);
/*  91 */     g.drawLine(MARGIN + 3, MARGIN + 5, MARGIN, MARGIN);
/*     */     
/*  93 */     g.drawLine(MARGIN, WIDTH, WIDTH, WIDTH);
/*  94 */     g.drawLine(WIDTH - 5, WIDTH - 3, WIDTH, WIDTH);
/*  95 */     g.drawLine(WIDTH - 5, WIDTH + 3, WIDTH, WIDTH);
/*  96 */     g.drawString("x1", WIDTH + 5, WIDTH + 12);
/*  97 */     g.drawString("x2", MARGIN - 15, MARGIN - 3);
/*  98 */     g.drawString("0", MARGIN - 5, WIDTH + 12);
/*     */     
/* 100 */     Font origFont = g.getFont();
/* 101 */     g.setFont(new Font(origFont.getName(), origFont.getStyle(), 9));
/* 102 */     drawUnit(g);
/* 103 */     g.setFont(origFont);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawUnit(Graphics g)
/*     */   {
/* 112 */     float span = (float)(1.0D * (WIDTH - MARGIN) / (10.0D * this.globalZoom));
/* 113 */     int unit; int unit; if (span < 1.9D) {
/* 114 */       unit = 1; } else { int unit;
/* 115 */       if (span < 3.0D) {
/* 116 */         unit = 2; } else { int unit;
/* 117 */         if (span < 5.9D) {
/* 118 */           unit = 5; } else { int unit;
/* 119 */           if (span < 14.9D) {
/* 120 */             unit = 10;
/*     */           } else { int unit;
/* 122 */             if (span < 24.9D) {
/* 123 */               unit = 20;
/*     */             }
/*     */             else
/* 126 */               unit = 50;
/*     */           }
/*     */         } } }
/* 129 */     int xstep = unit * this.globalZoom;
/* 130 */     int ystep = unit * this.globalZoom;
/* 131 */     while ((xstep < WIDTH - MARGIN) && (ystep < WIDTH - MARGIN))
/*     */     {
/* 133 */       g.drawLine(MARGIN + xstep, WIDTH - 1, MARGIN + xstep, WIDTH + 1);
/* 134 */       if (unit < 10) {
/* 135 */         g.drawString("".concat(String.valueOf(String.valueOf(xstep / this.globalZoom))), MARGIN + xstep - 3, WIDTH + 12);
/* 136 */       } else if (unit < 50) {
/* 137 */         g.drawString("".concat(String.valueOf(String.valueOf(xstep / this.globalZoom))), MARGIN + xstep - 6, WIDTH + 12);
/*     */       } else {
/* 139 */         g.drawString("".concat(String.valueOf(String.valueOf(xstep / this.globalZoom))), MARGIN + xstep - 8, WIDTH + 12);
/*     */       }
/*     */       
/* 142 */       g.drawLine(MARGIN - 1, WIDTH - ystep, MARGIN + 1, WIDTH - ystep);
/* 143 */       if (unit < 10) {
/* 144 */         g.drawString("".concat(String.valueOf(String.valueOf(ystep / this.globalZoom))), 7, WIDTH - ystep + 5);
/*     */       } else {
/* 146 */         g.drawString("".concat(String.valueOf(String.valueOf(ystep / this.globalZoom))), 3, WIDTH - ystep + 5);
/*     */       }
/* 148 */       xstep += unit * this.globalZoom;
/* 149 */       ystep += unit * this.globalZoom;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setConstraints(allConstraints theConstraints)
/*     */   {
/* 158 */     this.currentCons = theConstraints;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void dispOptimal(Objective objf, Intersection optimalIntersection)
/*     */   {
/* 166 */     this.objFunction = objf;
/* 167 */     this.wantOptimal = true;
/* 168 */     this.showOptimum = false;
/* 169 */     this.needStepDraw = true;
/* 170 */     this.MaxIntersection = optimalIntersection;
/* 171 */     this.optimalPolygon = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clearOptimal()
/*     */   {
/* 179 */     this.objFunction = null;
/* 180 */     this.wantOptimal = false;
/* 181 */     this.MaxIntersection = null;
/* 182 */     this.optimalPolygon = null;
/* 183 */     this.lastSegment = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawConstraints(Graphics g)
/*     */   {
/* 191 */     int zoom = 1000;
/* 192 */     Constraint tempCons = null;
/*     */     
/* 194 */     if (this.currentCons == null) {
/* 195 */       this.globalZoom = 1;
/* 196 */       return;
/*     */     }
/*     */     
/* 199 */     Enumeration e = this.currentCons.vConstraints.elements();
/* 200 */     while (e.hasMoreElements())
/*     */     {
/* 202 */       tempCons = (Constraint)e.nextElement();
/* 203 */       int tranzoom = tempCons.getZoom();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 209 */       if ((tranzoom < zoom) && (tranzoom >= 1)) {
/* 210 */         zoom = tranzoom;
/*     */       }
/*     */     }
/* 213 */     zoom = decidezoom(zoom);
/*     */     
/* 215 */     if (zoom == 1000) {
/* 216 */       zoom = 1;
/*     */     }
/*     */     
/* 219 */     drawConstraints(g, zoom, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawConstraints(Graphics g, int zoom, boolean shade)
/*     */   {
/* 229 */     System.out.println("zoom:".concat(String.valueOf(String.valueOf(zoom))));
/* 230 */     Constraint tempCons = null;
/* 231 */     int i = 3;
/* 232 */     Enumeration e = this.currentCons.vConstraints.elements();
/*     */     
/* 234 */     Enumeration fbx = this.currentCons.vFeasibleIntersections.elements();
/* 235 */     Intersection tempIntersection = null;
/* 236 */     Point stpt = null;Point edpt = null;
/*     */     
/*     */ 
/* 239 */     Polygon tempPolygon = null;
/* 240 */     Polygon tempPolygon2 = null;
/* 241 */     this.globalZoom = zoom;
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
/* 252 */     while (e.hasMoreElements())
/*     */     {
/* 254 */       tempPolygon = new Polygon();
/* 255 */       tempCons = (Constraint)e.nextElement();
/* 256 */       tempCons.withZoom(zoom);
/*     */       
/* 258 */       float tempslope = (float)(-1.0D * tempCons.x1_const / tempCons.x2_const);
/*     */       
/* 260 */       if (tempCons.inequating == 1)
/*     */       {
/* 262 */         if (tempslope >= 0) {
/* 263 */           if (tempCons.startp.y < tempCons.endp.y)
/*     */           {
/* 265 */             System.out.println("lntempCons.startp.x".concat(String.valueOf(String.valueOf(tempCons.startp.x))));
/* 266 */             System.out.println("lntempCons.end.x".concat(String.valueOf(String.valueOf(tempCons.endp.x))));
/*     */             
/* 268 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 269 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 270 */             tempPolygon.addPoint(0, 340);
/* 271 */             tempPolygon.addPoint(0, 0);
/* 272 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 274 */           else if (tempCons.startp.y > tempCons.endp.y) {
/* 275 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 276 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 277 */             tempPolygon.addPoint(0, 340);
/* 278 */             tempPolygon.addPoint(340, 340);
/* 279 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 281 */           else if (tempCons.startp.y == tempCons.endp.y)
/*     */           {
/*     */ 
/*     */ 
/* 285 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 286 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 287 */             tempPolygon.addPoint(0, 340);
/* 288 */             tempPolygon.addPoint(0, 0);
/* 289 */             tempPolygon.addPoint(340, 0);
/*     */           }
/*     */           
/*     */         }
/* 293 */         else if (tempCons.startp.y <= tempCons.endp.y)
/*     */         {
/* 295 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 296 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 297 */           tempPolygon.addPoint(340, 340);
/* 298 */           tempPolygon.addPoint(340, 0);
/* 299 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */         else {
/* 302 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 303 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 304 */           tempPolygon.addPoint(340, 340);
/* 305 */           tempPolygon.addPoint(340, 0);
/* 306 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */         
/*     */       }
/* 310 */       else if (tempCons.inequating == 2)
/*     */       {
/* 312 */         if (tempslope >= 0) {
/* 313 */           if (tempCons.startp.y < tempCons.endp.y) {
/* 314 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 315 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 316 */             tempPolygon.addPoint(0, 340);
/* 317 */             tempPolygon.addPoint(340, 340);
/* 318 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 320 */           else if (tempCons.startp.y > tempCons.endp.y) {
/* 321 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 322 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 323 */             tempPolygon.addPoint(0, 340);
/* 324 */             tempPolygon.addPoint(0, 0);
/* 325 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 327 */           else if (tempCons.startp.y == tempCons.endp.y) {
/* 328 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 329 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 330 */             tempPolygon.addPoint(0, 340);
/* 331 */             tempPolygon.addPoint(340, 340);
/* 332 */             tempPolygon.addPoint(340, 0);
/*     */           }
/*     */           
/*     */         }
/* 336 */         else if (tempCons.startp.y <= tempCons.endp.y)
/*     */         {
/* 338 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 339 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 340 */           tempPolygon.addPoint(340, 340);
/* 341 */           tempPolygon.addPoint(0, 340);
/* 342 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */         else
/*     */         {
/* 346 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 347 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 348 */           tempPolygon.addPoint(340, 340);
/* 349 */           tempPolygon.addPoint(0, 340);
/* 350 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 357 */       g.setColor(Color.white);
/* 358 */       g.fillPolygon(tempPolygon);
/* 359 */       tempPolygon = null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 364 */     g.setColor(Color.blue);
/* 365 */     e = this.currentCons.vConstraints.elements();
/* 366 */     while (e.hasMoreElements())
/*     */     {
/* 368 */       tempCons = (Constraint)e.nextElement();
/* 369 */       tempCons.withZoom(zoom);
/*     */       
/* 371 */       g.drawLine(tempCons.startp.x, tempCons.startp.y, tempCons.endp.x, tempCons.endp.y);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 379 */     if ((stpt != null) && (edpt != null)) {
/* 380 */       g.setColor(Color.magenta);
/* 381 */       g.drawLine(stpt.x, stpt.y, edpt.x, edpt.y);
/*     */     }
/* 383 */     g.setColor(Color.white);
/*     */     
/* 385 */     g.fillRect(0, 0, 20, 340);
/* 386 */     g.fillRect(320, 0, 22, 340);
/* 387 */     g.fillRect(0, 320, 340, 20);
/* 388 */     g.fillRect(0, 0, 340, 20);
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
/* 402 */     g.setColor(c);
/* 403 */     g.fillOval(p.x - 3, p.y - 3, 6, 6);
/* 404 */     g.setColor(Color.black);
/* 405 */     g.drawOval(p.x - 3, p.y - 3, 6, 6);
/*     */   }
/*     */   
/*     */   public int decidezoom(int z)
/*     */   {
/* 410 */     if (this.currentCons == null)
/* 411 */       return z;
/* 412 */     Intersection MaxIntersection = null;
/* 413 */     Intersection tempIntersection = null;
/* 414 */     Enumeration e = this.currentCons.vFeasibleIntersections.elements();
/* 415 */     float maxvalue = 0.0F;
/*     */     
/* 417 */     while (e.hasMoreElements())
/*     */     {
/* 419 */       if (MaxIntersection == null)
/*     */       {
/* 421 */         MaxIntersection = (Intersection)e.nextElement();
/* 422 */         if ((MaxIntersection.x1 >= 0) && (MaxIntersection.x2 >= 0)) {
/* 423 */           maxvalue = Math.abs(MaxIntersection.x1) > Math.abs(MaxIntersection.x2) ? Math.abs(MaxIntersection.x1) : Math.abs(MaxIntersection.x2);
/*     */         }
/*     */         else {
/* 426 */           MaxIntersection = null;
/*     */         }
/*     */         
/*     */       }
/*     */       else
/*     */       {
/* 432 */         tempIntersection = (Intersection)e.nextElement();
/* 433 */         if ((tempIntersection.x1 >= 0) && (tempIntersection.x2 >= 0)) {
/* 434 */           float tempmaxvalue = tempIntersection.x1 > tempIntersection.x2 ? tempIntersection.x1 : tempIntersection.x2;
/* 435 */           if (tempmaxvalue > maxvalue) {
/* 436 */             MaxIntersection = tempIntersection;
/* 437 */             maxvalue = tempmaxvalue;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 446 */     int tempzoom = z / 2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 454 */     while (maxvalue * tempzoom > ' ')
/*     */     {
/* 456 */       tempzoom /= 2;
/*     */     }
/*     */     
/* 459 */     return tempzoom * 2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawFeaCones(Graphics g)
/*     */   {
/* 467 */     if (this.currentCons == null) {
/* 468 */       return;
/*     */     }
/* 470 */     Intersection tempIntersection = null;
/* 471 */     Enumeration e = this.currentCons.vFeasibleIntersections.elements();
/*     */     
/* 473 */     while (e.hasMoreElements())
/*     */     {
/* 475 */       tempIntersection = (Intersection)e.nextElement();
/* 476 */       Point conept = new Point(MARGIN + (int)(tempIntersection.x1 * this.globalZoom), WIDTH - (int)(tempIntersection.x2 * this.globalZoom));
/*     */       
/* 478 */       drawPoint(g, conept, Color.orange);
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
/* 492 */     if (this.MaxIntersection != null)
/*     */     {
/* 494 */       System.out.println("yes, there is optimal");
/* 495 */       Point OptimalPoint = new Point((int)(MARGIN + this.MaxIntersection.x1 * this.globalZoom), (int)(WIDTH - this.MaxIntersection.x2 * this.globalZoom));
/* 496 */       drawPoint(g, OptimalPoint, Color.red);
/*     */       
/*     */ 
/* 499 */       float optz = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/* 500 */       System.out.println("z = ".concat(String.valueOf(String.valueOf(optz))));
/*     */       
/* 502 */       if (this.doAction) { this.t.restart();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 507 */       if (!this.needStepDraw) {
/* 508 */         approachOptimal(g, optz);
/* 509 */         drawAxis(g);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 514 */         float recurval = optz * (1.0F - this.autostep / 10.0F);
/*     */         
/* 516 */         if (Math.abs(optz) < 0.001D) {
/* 517 */           recurval = this.autostep / 10.0F;
/* 518 */           if (this.globalZoom == 1) {
/* 519 */             this.globalZoom = 160;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 529 */         approachOptimal(g, recurval);
/* 530 */         drawAxis(g);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 537 */       int opxpix = (int)(this.globalZoom * this.MaxIntersection.x1);
/* 538 */       int opypix = (int)(this.globalZoom * this.MaxIntersection.x2);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 544 */       if (this.optimalPolygon == null) {
/* 545 */         this.optimalPolygon = new Polygon();
/*     */         
/*     */ 
/* 548 */         this.optimalPolygon.addPoint(MARGIN + opxpix + 10, WIDTH - (opypix + 5));
/* 549 */         this.optimalPolygon.addPoint(MARGIN + opxpix + 5, WIDTH - (opypix + 10));
/* 550 */         this.optimalPolygon.addPoint(MARGIN + opxpix + 2, WIDTH - (opypix + 2));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 559 */       if (this.showOptimum) {
/* 560 */         g.setColor(Color.red);
/* 561 */         g.fillPolygon(this.optimalPolygon);
/* 562 */         g.drawLine(MARGIN + opxpix, WIDTH - opypix, MARGIN + opxpix + 20, WIDTH - (opypix + 20));
/*     */         
/* 564 */         g.drawString("Optimum", MARGIN + opxpix + 23, WIDTH - (opypix + 20));
/*     */       }
/*     */       
/* 567 */       g.setColor(Color.blue);
/* 568 */       Enumeration e = this.currentCons.vConstraints.elements();
/*     */       
/* 570 */       while (e.hasMoreElements())
/*     */       {
/* 572 */         Constraint tempCons = (Constraint)e.nextElement();
/* 573 */         tempCons.withZoom(this.globalZoom);
/* 574 */         g.drawLine(tempCons.startp.x, tempCons.startp.y, tempCons.endp.x, tempCons.endp.y);
/*     */       }
/*     */       
/* 577 */       g.setColor(Color.white);
/* 578 */       g.fillRect(0, 0, 20, 340);
/* 579 */       g.fillRect(320, 0, 22, 340);
/* 580 */       g.fillRect(0, 320, 340, 20);
/* 581 */       g.fillRect(0, 0, 340, 20);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void approachOptimal(Graphics g, float critval)
/*     */   {
/* 590 */     Point opmapt1 = null;Point opmapt2 = null;
/*     */     
/*     */ 
/* 593 */     float opcept1x = critval / this.objFunction.x1_coef;
/* 594 */     float opcept2y = critval / this.objFunction.x2_coef;
/*     */     
/* 596 */     System.out.println("opcept1x =".concat(String.valueOf(String.valueOf(opcept1x))));
/* 597 */     System.out.println("opcept2y =".concat(String.valueOf(String.valueOf(opcept2y))));
/*     */     
/* 599 */     float NormalSlope = -opcept2y / opcept1x;
/* 600 */     int w = WIDTH;
/*     */     
/* 602 */     if ((opcept1x > 0) && (opcept2y > 0))
/*     */     {
/* 604 */       opmapt1 = new Point((int)(MARGIN + opcept1x * this.globalZoom), WIDTH);
/* 605 */       opmapt2 = new Point(MARGIN, (int)(WIDTH - opcept2y * this.globalZoom));
/*     */     }
/* 607 */     else if ((opcept1x > 0) && (opcept2y < 0)) {
/* 608 */       float wx = w;
/* 609 */       float wy = wx * NormalSlope;
/* 610 */       opmapt1 = new Point((int)(MARGIN + opcept1x * this.globalZoom) + (int)wx, WIDTH - (int)wy);
/* 611 */       opmapt2 = new Point(MARGIN, (int)(WIDTH - opcept2y * this.globalZoom));
/*     */ 
/*     */     }
/* 614 */     else if ((opcept1x < 0) && (opcept2y > 0)) {
/* 615 */       float wx = w;
/* 616 */       float wy = wx * NormalSlope;
/* 617 */       opmapt1 = new Point((int)(MARGIN + opcept1x * this.globalZoom), WIDTH);
/* 618 */       opmapt2 = new Point(MARGIN + (int)wx, (int)(WIDTH - opcept2y * this.globalZoom) - (int)wy);
/*     */     }
/*     */     else
/*     */     {
/* 622 */       opmapt1 = new Point(20, 320);
/* 623 */       opmapt2 = new Point(20, 320);
/*     */     }
/*     */     
/*     */ 
/* 627 */     if (this.lastSegment != null) {
/* 628 */       g.setColor(Color.white);
/* 629 */       g.drawLine(this.lastSegment.head().x, this.lastSegment.head().y, this.lastSegment.tail().x, this.lastSegment.tail().y);
/*     */     }
/*     */     
/* 632 */     g.setColor(Color.red);
/* 633 */     g.drawLine(opmapt1.x, opmapt1.y, opmapt2.x, opmapt2.y);
/*     */     
/* 635 */     g.setColor(Color.yellow);
/* 636 */     g.fillRect(0, 0, 20, 340);
/* 637 */     g.fillRect(320, 0, 22, 340);
/* 638 */     g.fillRect(0, 320, 340, 20);
/* 639 */     g.fillRect(0, 0, 340, 20);
/*     */     
/* 641 */     this.lastSegment = null;
/* 642 */     this.lastSegment = new Segment(opmapt1, opmapt2);
/* 643 */     g.setColor(Color.blue);
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
/*     */ 
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 681 */     this.autostep -= 1;
/* 682 */     repaint();
/* 683 */     if (this.autostep == 0) {
/* 684 */       this.autostep = 4;
/* 685 */       this.needStepDraw = false;
/* 686 */       this.t.stop();
/* 687 */       this.doAction = false;
/* 688 */       this.showOptimum = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPGraphPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */