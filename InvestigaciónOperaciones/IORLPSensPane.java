/*     */ import java.awt.Color;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class IORLPSensPane extends JPanel implements java.awt.event.MouseMotionListener
/*     */ {
/*  17 */   private static int CANVASIZE = 340;
/*  18 */   private static int MARGIN = 20;
/*  19 */   private static int WIDTH = 320;
/*     */   
/*     */   private Image bufzone;
/*     */   
/*  23 */   private Color tranColor = new Color(0, 160, 0);
/*     */   
/*     */   private IORLPLinearProgPane mainApp;
/*     */   public int globalZoom;
/*  27 */   private allConstraints currentCons = null;
/*  28 */   private Objective objFunction = null;
/*  29 */   private Intersection MaxIntersection = null;
/*  30 */   private Polygon optimalPolygon = null;
/*     */   private Polygon trian1;
/*     */   private Polygon trian2;
/*     */   private Point lastxpt;
/*     */   private Point lastypt;
/*     */   private float currentZ;
/*     */   private float old_x1coef;
/*     */   private float old_x2coef;
/*     */   private Point xminpt;
/*  39 */   private Point xmaxpt; private Point yminpt; private Point ymaxpt; private float[][] cflimit; private boolean selXEnd = false; private boolean selYEnd = false;
/*  40 */   private boolean vertMove = false; private boolean horzMove = false;
/*     */   
/*     */ 
/*  43 */   Point topmapt1 = new Point();
/*  44 */   Point topmapt2 = new Point();
/*  45 */   private boolean changed = true;
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
/*     */   public IORLPSensPane(IORLPLinearProgPane main, int zoom, allConstraints cons, Objective objfunc, Intersection opints, float[][] coefsp)
/*     */   {
/*  58 */     this.mainApp = main;
/*  59 */     this.globalZoom = zoom;
/*  60 */     this.currentCons = cons;
/*  61 */     this.objFunction = objfunc;
/*  62 */     this.MaxIntersection = opints;
/*  63 */     this.cflimit = coefsp;
/*  64 */     this.currentZ = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/*  65 */     this.old_x1coef = this.objFunction.x1_coef;
/*  66 */     this.old_x2coef = this.objFunction.x2_coef;
/*     */     
/*  68 */     getMotion();
/*  69 */     addMouseMotionListener(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update(Graphics g)
/*     */   {
/*  77 */     paint(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/*  85 */     if (this.bufzone == null) {
/*  86 */       this.bufzone = createImage(getSize().width, getSize().height);
/*     */     }
/*  88 */     Graphics bfg = this.bufzone.getGraphics();
/*  89 */     bfg.setClip(0, 0, getSize().width, getSize().height);
/*  90 */     super.paint(bfg);
/*     */     
/*  92 */     bfg.setColor(Color.cyan);
/*  93 */     bfg.fillRect(MARGIN, MARGIN, WIDTH - MARGIN, WIDTH - MARGIN);
/*  94 */     drawConstraints(bfg);
/*  95 */     drawFeaCones(bfg);
/*  96 */     drawOptimal(bfg);
/*  97 */     drawAxis(bfg);
/*     */     
/*  99 */     g.drawImage(this.bufzone, 0, 0, null);
/* 100 */     bfg.dispose();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawAxis(Graphics g)
/*     */   {
/* 109 */     g.setColor(Color.black);
/*     */     
/* 111 */     g.drawLine(MARGIN, MARGIN, MARGIN, WIDTH);
/* 112 */     g.drawLine(MARGIN - 3, MARGIN + 5, MARGIN, MARGIN);
/* 113 */     g.drawLine(MARGIN + 3, MARGIN + 5, MARGIN, MARGIN);
/*     */     
/* 115 */     g.drawLine(MARGIN, WIDTH, WIDTH, WIDTH);
/* 116 */     g.drawLine(WIDTH - 5, WIDTH - 3, WIDTH, WIDTH);
/* 117 */     g.drawLine(WIDTH - 5, WIDTH + 3, WIDTH, WIDTH);
/* 118 */     g.drawString("x1", WIDTH + 5, WIDTH + 12);
/* 119 */     g.drawString("x2", MARGIN - 15, MARGIN - 3);
/* 120 */     g.drawString("0", MARGIN - 5, WIDTH + 12);
/*     */     
/* 122 */     g.drawLine(WIDTH, WIDTH, WIDTH, MARGIN);
/* 123 */     g.drawLine(MARGIN, MARGIN, WIDTH, MARGIN);
/*     */     
/* 125 */     Font origFont = g.getFont();
/* 126 */     g.setFont(new Font(origFont.getName(), origFont.getStyle(), 9));
/* 127 */     drawUnit(g);
/* 128 */     g.setFont(origFont);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawUnit(Graphics g)
/*     */   {
/* 137 */     float span = (float)(1.0D * (WIDTH - MARGIN) / (10.0D * this.globalZoom));
/* 138 */     int unit; int unit; if (span < 1.9D) {
/* 139 */       unit = 1; } else { int unit;
/* 140 */       if (span < 3.0D) {
/* 141 */         unit = 2; } else { int unit;
/* 142 */         if (span < 5.9D) {
/* 143 */           unit = 5; } else { int unit;
/* 144 */           if (span < 14.9D) {
/* 145 */             unit = 10;
/*     */           } else { int unit;
/* 147 */             if (span < 24.9D) {
/* 148 */               unit = 20;
/*     */             }
/*     */             else
/* 151 */               unit = 50;
/*     */           }
/*     */         } } }
/* 154 */     int xstep = unit * this.globalZoom;
/* 155 */     int ystep = unit * this.globalZoom;
/* 156 */     while ((xstep < WIDTH - MARGIN) && (ystep < WIDTH - MARGIN))
/*     */     {
/* 158 */       g.drawLine(MARGIN + xstep, WIDTH - 1, MARGIN + xstep, WIDTH + 1);
/* 159 */       if (unit < 10) {
/* 160 */         g.drawString("".concat(String.valueOf(String.valueOf(xstep / this.globalZoom))), MARGIN + xstep - 3, WIDTH + 12);
/* 161 */       } else if (unit < 50) {
/* 162 */         g.drawString("".concat(String.valueOf(String.valueOf(xstep / this.globalZoom))), MARGIN + xstep - 6, WIDTH + 12);
/*     */       } else {
/* 164 */         g.drawString("".concat(String.valueOf(String.valueOf(xstep / this.globalZoom))), MARGIN + xstep - 8, WIDTH + 12);
/*     */       }
/*     */       
/* 167 */       g.drawLine(MARGIN - 1, WIDTH - ystep, MARGIN + 1, WIDTH - ystep);
/* 168 */       if (unit < 10) {
/* 169 */         g.drawString("".concat(String.valueOf(String.valueOf(ystep / this.globalZoom))), 7, WIDTH - ystep + 5);
/*     */       } else {
/* 171 */         g.drawString("".concat(String.valueOf(String.valueOf(ystep / this.globalZoom))), 3, WIDTH - ystep + 5);
/*     */       }
/* 173 */       xstep += unit * this.globalZoom;
/* 174 */       ystep += unit * this.globalZoom;
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
/*     */   public void getMotion()
/*     */   {
/* 187 */     this.xmaxpt = new Point(0, 0);
/*     */     
/* 189 */     if ((Math.abs(this.cflimit[0][0]) < 0.001D) && (this.MaxIntersection.x2 > 0.001D)) {
/* 190 */       float zval = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/* 191 */       this.xmaxpt = new Point(WIDTH, (int)(WIDTH - this.MaxIntersection.x2 * this.globalZoom + 1.0E-4D));
/*     */     }
/* 193 */     else if (Math.abs(this.cflimit[0][0]) > 0.001D) {
/* 194 */       this.objFunction.x1_coef = this.cflimit[0][0];
/* 195 */       float zval = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/* 196 */       float x1cept = zval / this.objFunction.x1_coef;
/*     */       
/* 198 */       if (x1cept > 0.001D) {
/* 199 */         float x1cept_y = 0.0F;
/* 200 */         if (Math.abs(x1cept * this.globalZoom) > WIDTH - MARGIN) {
/* 201 */           x1cept = (WIDTH - MARGIN) / this.globalZoom;
/* 202 */           x1cept_y = (zval - x1cept * this.objFunction.x1_coef) / this.objFunction.x2_coef;
/*     */         }
/* 204 */         this.xmaxpt = new Point((int)(MARGIN + x1cept * this.globalZoom + 1.0E-4D), (int)(WIDTH - x1cept_y * this.globalZoom + 1.0E-4D));
/*     */       }
/*     */       else {
/* 207 */         float x1cept_y = zval / this.objFunction.x2_coef;
/* 208 */         float twidth = (WIDTH - MARGIN) / this.globalZoom;
/* 209 */         x1cept = -1 * x1cept * (twidth - x1cept_y) / x1cept_y;
/* 210 */         this.xmaxpt = new Point((int)(MARGIN + x1cept * this.globalZoom + 1.0E-4D), (int)(MARGIN + 1.0E-4D));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 215 */     this.objFunction.x1_coef = this.cflimit[0][1];
/* 216 */     float zval = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/* 217 */     float x1cept = zval / this.objFunction.x1_coef;
/* 218 */     float x1cept_y = 0.0F;
/* 219 */     if (x1cept * this.globalZoom > WIDTH - MARGIN) {
/* 220 */       x1cept = (WIDTH - MARGIN) / this.globalZoom;
/* 221 */       x1cept_y = (zval - x1cept * this.objFunction.x1_coef) / this.objFunction.x2_coef;
/*     */     }
/*     */     
/* 224 */     this.xminpt = new Point((int)(MARGIN + x1cept * this.globalZoom + 1.0E-4D), (int)(WIDTH - x1cept_y * this.globalZoom + 1.0E-4D));
/*     */     
/*     */ 
/* 227 */     this.objFunction.x1_coef = this.old_x1coef;
/*     */     
/* 229 */     this.ymaxpt = new Point(0, 0);
/*     */     
/* 231 */     if ((Math.abs(this.cflimit[1][0]) < 0.001D) && (this.MaxIntersection.x1 > 0.001D)) {
/* 232 */       zval = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/* 233 */       this.ymaxpt = new Point((int)(MARGIN + this.MaxIntersection.x1 * this.globalZoom + 1.0E-4D), MARGIN);
/*     */     }
/* 235 */     else if (Math.abs(this.cflimit[1][0]) > 0.001D) {
/* 236 */       this.objFunction.x2_coef = this.cflimit[1][0];
/* 237 */       zval = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/* 238 */       float x2cept = zval / this.objFunction.x2_coef;
/* 239 */       float x2cept_x = zval / this.objFunction.x1_coef;
/*     */       
/* 241 */       if (x2cept_x > 0.001D) {
/* 242 */         x2cept_x = 0.0F;
/* 243 */         if (x2cept * this.globalZoom > WIDTH - MARGIN) {
/* 244 */           x2cept = (WIDTH - MARGIN) / this.globalZoom;
/* 245 */           x2cept_x = (zval - x2cept * this.objFunction.x2_coef) / this.objFunction.x1_coef;
/*     */         }
/* 247 */         this.ymaxpt = new Point((int)(MARGIN + x2cept_x * this.globalZoom + 1.0E-4D), (int)(WIDTH - x2cept * this.globalZoom + 1.0E-4D));
/*     */       }
/*     */       else {
/* 250 */         float twidth = (WIDTH - MARGIN) / this.globalZoom;
/* 251 */         x2cept_x = -1 * x2cept_x * (twidth - x2cept) / x2cept;
/* 252 */         this.ymaxpt = new Point((int)(MARGIN + x2cept_x * this.globalZoom + 1.0E-4D), (int)(MARGIN + 1.0E-4D));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 259 */     this.objFunction.x2_coef = this.cflimit[1][1];
/* 260 */     zval = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/* 261 */     float x2cept = zval / this.objFunction.x2_coef;
/* 262 */     float x2cept_x = 0.0F;
/*     */     
/* 264 */     if (x2cept * this.globalZoom > WIDTH - MARGIN) {
/* 265 */       x2cept = (WIDTH - MARGIN) / this.globalZoom;
/* 266 */       x2cept_x = (zval - x2cept * this.objFunction.x2_coef) / this.objFunction.x1_coef;
/*     */     }
/* 268 */     this.yminpt = new Point((int)(MARGIN + x2cept_x * this.globalZoom + 1.0E-4D), (int)(WIDTH - x2cept * this.globalZoom + 1.0E-4D));
/*     */     
/*     */ 
/*     */ 
/* 272 */     this.objFunction.x1_coef = this.old_x1coef;
/* 273 */     this.objFunction.x2_coef = this.old_x2coef;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void drawTriangle(Graphics g, Point pt, int ort, Color c)
/*     */   {
/* 281 */     Polygon markTran = new Polygon();
/* 282 */     if ((pt.x == MARGIN) && (pt.y == WIDTH)) {
/* 283 */       if (ort == 0) {
/* 284 */         markTran.addPoint(MARGIN + 3, WIDTH);
/* 285 */         markTran.addPoint(MARGIN - 3, WIDTH);
/* 286 */         markTran.addPoint(MARGIN, WIDTH - 6);
/*     */       }
/*     */       else {
/* 289 */         markTran.addPoint(MARGIN, WIDTH - 3);
/* 290 */         markTran.addPoint(MARGIN, WIDTH + 3);
/* 291 */         markTran.addPoint(MARGIN + 6, WIDTH);
/*     */       }
/*     */     }
/* 294 */     else if ((pt.y == WIDTH) && (pt.x < WIDTH - 3)) {
/* 295 */       markTran.addPoint(pt.x - 3, pt.y);
/* 296 */       markTran.addPoint(pt.x + 3, pt.y);
/* 297 */       markTran.addPoint(pt.x, pt.y - 6);
/*     */     }
/* 299 */     else if ((pt.x == WIDTH) && (pt.y < WIDTH - 3)) {
/* 300 */       markTran.addPoint(pt.x, pt.y - 3);
/* 301 */       markTran.addPoint(pt.x, pt.y + 3);
/* 302 */       markTran.addPoint(pt.x - 6, pt.y);
/*     */     }
/* 304 */     else if ((Math.abs(pt.x - WIDTH) <= 3) && (Math.abs(pt.y - WIDTH) <= 3)) {
/* 305 */       markTran.addPoint(WIDTH - 3, WIDTH);
/* 306 */       markTran.addPoint(WIDTH, WIDTH);
/* 307 */       markTran.addPoint(WIDTH, WIDTH - 3);
/* 308 */       markTran.addPoint(WIDTH - 5, WIDTH - 5);
/*     */     }
/* 310 */     else if ((pt.x == MARGIN) && (pt.y > MARGIN + 3)) {
/* 311 */       markTran.addPoint(pt.x, pt.y - 3);
/* 312 */       markTran.addPoint(pt.x, pt.y + 3);
/* 313 */       markTran.addPoint(pt.x + 6, pt.y);
/*     */     }
/* 315 */     else if ((pt.y == MARGIN) && (pt.x > MARGIN + 3)) {
/* 316 */       markTran.addPoint(pt.x - 3, pt.y);
/* 317 */       markTran.addPoint(pt.x + 3, pt.y);
/* 318 */       markTran.addPoint(pt.x, pt.y + 6);
/*     */     }
/* 320 */     else if ((Math.abs(pt.x - MARGIN) <= 3) && (Math.abs(pt.y - MARGIN) <= 3)) {
/* 321 */       markTran.addPoint(MARGIN + 3, MARGIN);
/* 322 */       markTran.addPoint(MARGIN, MARGIN);
/* 323 */       markTran.addPoint(MARGIN, MARGIN + 3);
/* 324 */       markTran.addPoint(MARGIN + 5, MARGIN + 5);
/*     */     }
/* 326 */     g.setColor(c);
/* 327 */     g.fillPolygon(markTran);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawConstraints(Graphics g)
/*     */   {
/* 335 */     Constraint tempCons = null;
/* 336 */     int i = 3;
/* 337 */     Enumeration e = this.currentCons.vConstraints.elements();
/*     */     
/* 339 */     Enumeration fbx = this.currentCons.vFeasibleIntersections.elements();
/* 340 */     Intersection tempIntersection = null;
/* 341 */     Point stpt = null;Point edpt = null;
/*     */     
/*     */ 
/* 344 */     Polygon tempPolygon = null;
/* 345 */     Polygon tempPolygon2 = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 353 */     while (e.hasMoreElements())
/*     */     {
/* 355 */       tempPolygon = new Polygon();
/* 356 */       tempCons = (Constraint)e.nextElement();
/* 357 */       tempCons.withZoom(this.globalZoom);
/*     */       
/* 359 */       float tempslope = (float)(-1.0D * tempCons.x1_const / tempCons.x2_const);
/*     */       
/* 361 */       if (tempCons.inequating == 1)
/*     */       {
/* 363 */         if (tempslope >= 0) {
/* 364 */           if (tempCons.startp.y < tempCons.endp.y)
/*     */           {
/* 366 */             System.out.println("lntempCons.startp.x".concat(String.valueOf(String.valueOf(tempCons.startp.x))));
/* 367 */             System.out.println("lntempCons.end.x".concat(String.valueOf(String.valueOf(tempCons.endp.x))));
/*     */             
/* 369 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 370 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 371 */             tempPolygon.addPoint(0, 340);
/* 372 */             tempPolygon.addPoint(0, 0);
/* 373 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 375 */           else if (tempCons.startp.y > tempCons.endp.y) {
/* 376 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 377 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 378 */             tempPolygon.addPoint(0, 340);
/* 379 */             tempPolygon.addPoint(340, 340);
/* 380 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 382 */           else if (tempCons.startp.y == tempCons.endp.y)
/*     */           {
/*     */ 
/*     */ 
/* 386 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 387 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 388 */             tempPolygon.addPoint(0, 340);
/* 389 */             tempPolygon.addPoint(0, 0);
/* 390 */             tempPolygon.addPoint(340, 0);
/*     */           }
/*     */           
/*     */         }
/* 394 */         else if (tempCons.startp.y <= tempCons.endp.y)
/*     */         {
/* 396 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 397 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 398 */           tempPolygon.addPoint(340, 340);
/* 399 */           tempPolygon.addPoint(340, 0);
/* 400 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */         else {
/* 403 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 404 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 405 */           tempPolygon.addPoint(340, 340);
/* 406 */           tempPolygon.addPoint(340, 0);
/* 407 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */         
/*     */       }
/* 411 */       else if (tempCons.inequating == 2)
/*     */       {
/* 413 */         if (tempslope >= 0) {
/* 414 */           if (tempCons.startp.y < tempCons.endp.y) {
/* 415 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 416 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 417 */             tempPolygon.addPoint(0, 340);
/* 418 */             tempPolygon.addPoint(340, 340);
/* 419 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 421 */           else if (tempCons.startp.y > tempCons.endp.y) {
/* 422 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 423 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 424 */             tempPolygon.addPoint(0, 340);
/* 425 */             tempPolygon.addPoint(0, 0);
/* 426 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 428 */           else if (tempCons.startp.y == tempCons.endp.y) {
/* 429 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 430 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 431 */             tempPolygon.addPoint(0, 340);
/* 432 */             tempPolygon.addPoint(340, 340);
/* 433 */             tempPolygon.addPoint(340, 0);
/*     */           }
/*     */           
/*     */         }
/* 437 */         else if (tempCons.startp.y <= tempCons.endp.y)
/*     */         {
/* 439 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 440 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 441 */           tempPolygon.addPoint(340, 340);
/* 442 */           tempPolygon.addPoint(0, 340);
/* 443 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */         else
/*     */         {
/* 447 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 448 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 449 */           tempPolygon.addPoint(340, 340);
/* 450 */           tempPolygon.addPoint(0, 340);
/* 451 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 458 */       g.setColor(Color.white);
/* 459 */       g.fillPolygon(tempPolygon);
/* 460 */       tempPolygon = null;
/*     */     }
/*     */     
/* 463 */     g.setColor(Color.blue);
/* 464 */     e = this.currentCons.vConstraints.elements();
/* 465 */     while (e.hasMoreElements())
/*     */     {
/* 467 */       tempCons = (Constraint)e.nextElement();
/* 468 */       tempCons.withZoom(this.globalZoom);
/* 469 */       g.drawLine(tempCons.startp.x, tempCons.startp.y, tempCons.endp.x, tempCons.endp.y);
/*     */     }
/* 471 */     if ((stpt != null) && (edpt != null)) {
/* 472 */       g.setColor(Color.magenta);
/* 473 */       g.drawLine(stpt.x, stpt.y, edpt.x, edpt.y);
/*     */     }
/* 475 */     g.setColor(Color.white);
/*     */     
/* 477 */     g.fillRect(0, 0, 20, 340);
/* 478 */     g.fillRect(320, 0, 22, 340);
/* 479 */     g.fillRect(0, 320, 340, 20);
/* 480 */     g.fillRect(0, 0, 340, 20);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawFeaCones(Graphics g)
/*     */   {
/* 488 */     if (this.currentCons == null) {
/* 489 */       return;
/*     */     }
/* 491 */     Intersection tempIntersection = null;
/* 492 */     Enumeration e = this.currentCons.vFeasibleIntersections.elements();
/*     */     
/* 494 */     while (e.hasMoreElements())
/*     */     {
/* 496 */       tempIntersection = (Intersection)e.nextElement();
/* 497 */       Point conept = new Point(MARGIN + (int)(tempIntersection.x1 * this.globalZoom + 1.0E-4D), WIDTH - (int)(tempIntersection.x2 * this.globalZoom + 1.0E-4D));
/*     */       
/* 499 */       drawPoint(g, conept, Color.orange);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawPoint(Graphics g, Point p, Color c)
/*     */   {
/* 508 */     g.setColor(c);
/* 509 */     g.fillOval(p.x - 3, p.y - 3, 6, 6);
/* 510 */     g.setColor(Color.black);
/* 511 */     g.drawOval(p.x - 3, p.y - 3, 6, 6);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawOptimal(Graphics g)
/*     */   {
/* 520 */     Point opmapt1 = null;Point opmapt2 = null;
/*     */     
/*     */ 
/* 523 */     Point OptimalPoint = new Point((int)(MARGIN + this.MaxIntersection.x1 * this.globalZoom + 1.0E-4D), (int)(WIDTH - this.MaxIntersection.x2 * this.globalZoom + 1.0E-4D));
/* 524 */     drawPoint(g, OptimalPoint, Color.red);
/*     */     
/* 526 */     this.trian1 = null;
/* 527 */     this.trian2 = null;
/*     */     
/* 529 */     if (((this.currentZ >= Float.MAX_VALUE) && (this.objFunction.x2_coef >= Float.MAX_VALUE)) || ((this.currentZ < Float.MAX_VALUE) && (Math.abs(this.objFunction.x1_coef) < 0.001D)))
/*     */     {
/* 531 */       opmapt1 = new Point(WIDTH, (int)(WIDTH - this.MaxIntersection.x2 * this.globalZoom + 1.0E-4D));
/* 532 */       opmapt2 = new Point(MARGIN, (int)(WIDTH - this.MaxIntersection.x2 * this.globalZoom + 1.0E-4D));
/*     */     }
/* 534 */     else if (((this.currentZ >= Float.MAX_VALUE) && (this.objFunction.x1_coef >= Float.MAX_VALUE)) || ((this.currentZ < Float.MAX_VALUE) && (Math.abs(this.objFunction.x2_coef) < 0.001D)))
/*     */     {
/* 536 */       opmapt1 = new Point((int)(MARGIN + this.MaxIntersection.x1 * this.globalZoom + 1.0E-4D), WIDTH);
/* 537 */       opmapt2 = new Point((int)(MARGIN + this.MaxIntersection.x1 * this.globalZoom + 1.0E-4D), MARGIN);
/*     */     }
/*     */     else {
/* 540 */       float opcept1x = this.currentZ / this.objFunction.x1_coef;
/* 541 */       float opcept2y = this.currentZ / this.objFunction.x2_coef;
/* 542 */       System.out.println(String.valueOf(String.valueOf(new StringBuffer("opcept1x = ").append(opcept1x).append(";opcept2y = ").append(opcept2y))));
/* 543 */       if (opcept1x > 0.001D) {
/* 544 */         float opcept1y = 0.0F;
/* 545 */         if (Math.abs(opcept1x * this.globalZoom) > WIDTH - MARGIN) {
/* 546 */           opcept1x = (WIDTH - MARGIN) / this.globalZoom;
/* 547 */           opcept1y = (this.currentZ - opcept1x * this.objFunction.x1_coef) / this.objFunction.x2_coef;
/*     */         }
/* 549 */         opmapt1 = new Point((int)(MARGIN + opcept1x * this.globalZoom + 1.0E-4D), (int)(WIDTH - opcept1y * this.globalZoom + 1.0E-4D));
/*     */       }
/*     */       else {
/* 552 */         float opcept1y = this.currentZ / this.objFunction.x2_coef;
/* 553 */         opmapt1 = new Point((int)(MARGIN + 1.0E-4D), (int)(WIDTH - opcept1y * this.globalZoom + 1.0E-4D));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 558 */       this.lastxpt = opmapt1;
/*     */       
/* 560 */       float opcept2x = this.currentZ / this.objFunction.x1_coef;
/*     */       
/*     */ 
/* 563 */       if (opcept2x > 0.001D) {
/* 564 */         opcept2x = 0.0F;
/* 565 */         opcept2y = this.currentZ / this.objFunction.x2_coef;
/*     */         
/* 567 */         if (opcept2y > 0.001D) {
/* 568 */           if (Math.abs(opcept2y * this.globalZoom) > WIDTH - MARGIN)
/*     */           {
/* 570 */             opcept2y = (WIDTH - MARGIN) / this.globalZoom;
/* 571 */             opcept2x = (this.currentZ - opcept2y * this.objFunction.x2_coef) / this.objFunction.x1_coef;
/*     */           }
/* 573 */           opmapt2 = new Point((int)(MARGIN + opcept2x * this.globalZoom + 1.0E-4D), (int)(WIDTH - opcept2y * this.globalZoom + 1.0E-4D));
/*     */         }
/*     */         else
/*     */         {
/* 577 */           opcept2y = -opcept2y;
/* 578 */           float twidth = (WIDTH - MARGIN) / this.globalZoom;
/* 579 */           opcept2x = (twidth + opcept2y) * opcept1x / opcept2y;
/* 580 */           opcept2y = 0.0F;
/* 581 */           opmapt2 = new Point((int)(MARGIN + opcept2x * this.globalZoom + 1.0E-4D), MARGIN);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 586 */         float twidth = (WIDTH - MARGIN) / this.globalZoom;
/* 587 */         opcept2x = -1 * opcept2x * (twidth - opcept2y) / opcept2y;
/* 588 */         opmapt2 = new Point((int)(MARGIN + opcept2x * this.globalZoom + 1.0E-4D), (int)(MARGIN + 1.0E-4D));
/*     */       }
/*     */       
/*     */ 
/* 592 */       System.out.println(String.valueOf(String.valueOf(new StringBuffer("point 2--- x1=").append(opcept2x).append(", x2=").append(opcept2y))));
/*     */       
/* 594 */       this.lastypt = opmapt2;
/*     */       
/*     */ 
/*     */ 
/* 598 */       float NormalSlope = -1 * this.objFunction.x1_coef / this.objFunction.x2_coef;
/* 599 */       System.out.println("nor:".concat(String.valueOf(String.valueOf(NormalSlope))));
/* 600 */       if ((Math.abs(NormalSlope) < 99999.0D) && (NormalSlope > 0.001D)) {
/* 601 */         Point temp = new Point();
/* 602 */         temp = opmapt1;
/* 603 */         opmapt1 = opmapt2;
/* 604 */         opmapt2 = temp;
/*     */       }
/*     */     }
/*     */     
/* 608 */     if (this.changed) {
/* 609 */       this.topmapt1 = opmapt1;
/* 610 */       this.topmapt2 = opmapt2;
/*     */     }
/* 612 */     if (this.xmaxpt.x > 0)
/*     */     {
/* 614 */       this.trian1 = new Polygon();
/* 615 */       if (opmapt1.x >= WIDTH) {
/* 616 */         this.trian1.addPoint(WIDTH, this.topmapt1.y - 3);
/* 617 */         this.trian1.addPoint(WIDTH, this.topmapt1.y + 3);
/* 618 */         this.trian1.addPoint(WIDTH - 6, this.topmapt1.y);
/*     */       }
/*     */       else {
/* 621 */         this.trian1.addPoint(this.topmapt1.x - 3, this.topmapt1.y);
/* 622 */         this.trian1.addPoint(this.topmapt1.x + 3, this.topmapt1.y);
/* 623 */         this.trian1.addPoint(this.topmapt1.x, this.topmapt1.y - 6);
/*     */       }
/* 625 */       g.setColor(this.tranColor);
/* 626 */       g.fillPolygon(this.trian1);
/* 627 */       g.drawString("x1", this.topmapt1.x + 5, this.topmapt1.y - 5);
/* 628 */       drawTriangle(g, this.xmaxpt, 0, Color.magenta);
/* 629 */       drawTriangle(g, this.xminpt, 0, Color.black);
/*     */     }
/*     */     
/*     */ 
/* 633 */     if (this.ymaxpt.y > 0) {
/* 634 */       this.trian2 = new Polygon();
/* 635 */       if (opmapt2.y <= MARGIN) {
/* 636 */         this.trian2.addPoint(this.topmapt2.x - 3, MARGIN);
/* 637 */         this.trian2.addPoint(this.topmapt2.x + 3, MARGIN);
/* 638 */         this.trian2.addPoint(this.topmapt2.x, MARGIN + 6);
/*     */       }
/*     */       else {
/* 641 */         this.trian2.addPoint(this.topmapt2.x, this.topmapt2.y - 3);
/* 642 */         this.trian2.addPoint(this.topmapt2.x, this.topmapt2.y + 3);
/* 643 */         this.trian2.addPoint(this.topmapt2.x + 6, this.topmapt2.y);
/*     */       }
/* 645 */       g.setColor(this.tranColor);
/* 646 */       g.fillPolygon(this.trian2);
/* 647 */       drawTriangle(g, this.ymaxpt, 1, Color.magenta);
/* 648 */       drawTriangle(g, this.yminpt, 1, Color.black);
/*     */     }
/*     */     
/*     */ 
/* 652 */     g.setColor(Color.red);
/* 653 */     g.drawLine(this.topmapt1.x, this.topmapt1.y, this.topmapt2.x, this.topmapt2.y);
/*     */     
/*     */ 
/*     */ 
/* 657 */     int opxpix = (int)(this.globalZoom * this.MaxIntersection.x1);
/* 658 */     int opypix = (int)(this.globalZoom * this.MaxIntersection.x2);
/* 659 */     if (this.optimalPolygon == null) {
/* 660 */       this.optimalPolygon = new Polygon();
/*     */       
/* 662 */       this.optimalPolygon.addPoint(MARGIN + opxpix + 10, WIDTH - (opypix + 5));
/* 663 */       this.optimalPolygon.addPoint(MARGIN + opxpix + 5, WIDTH - (opypix + 10));
/* 664 */       this.optimalPolygon.addPoint(MARGIN + opxpix + 2, WIDTH - (opypix + 2));
/*     */     }
/*     */     
/* 667 */     g.setColor(Color.red);
/* 668 */     g.fillPolygon(this.optimalPolygon);
/*     */     
/*     */ 
/* 671 */     g.drawLine(MARGIN + opxpix, WIDTH - opypix, MARGIN + opxpix + 20, WIDTH - (opypix + 20));
/*     */     
/* 673 */     g.drawString("Optimum", MARGIN + opxpix + 23, WIDTH - (opypix + 20));
/*     */     
/*     */ 
/* 676 */     g.setColor(Color.white);
/* 677 */     g.fillRect(0, 0, 20, 340);
/* 678 */     g.fillRect(320, 0, 22, 340);
/* 679 */     g.fillRect(0, 320, 340, 20);
/* 680 */     g.fillRect(0, 0, 340, 20);
/*     */     
/*     */ 
/* 683 */     g.setColor(Color.blue);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mouseMoved(MouseEvent e)
/*     */   {
/* 692 */     if ((this.trian1 != null) && (this.trian1.contains(e.getPoint())))
/*     */     {
/* 694 */       this.selXEnd = true;
/* 695 */       this.selYEnd = false;
/* 696 */       if (this.trian1.xpoints[0] == WIDTH) {
/* 697 */         this.vertMove = true;
/* 698 */         this.horzMove = false;
/* 699 */         setCursor(Cursor.getPredefinedCursor(8));
/*     */       }
/*     */       else {
/* 702 */         this.vertMove = false;
/* 703 */         this.horzMove = true;
/* 704 */         setCursor(Cursor.getPredefinedCursor(11));
/*     */       }
/*     */     }
/* 707 */     else if ((this.trian2 != null) && (this.trian2.contains(e.getPoint())))
/*     */     {
/* 709 */       this.selXEnd = false;
/* 710 */       this.selYEnd = true;
/* 711 */       if (this.trian2.ypoints[0] == MARGIN) {
/* 712 */         this.vertMove = false;
/* 713 */         this.horzMove = true;
/* 714 */         setCursor(Cursor.getPredefinedCursor(11));
/*     */       }
/*     */       else {
/* 717 */         this.vertMove = true;
/* 718 */         this.horzMove = false;
/* 719 */         setCursor(Cursor.getPredefinedCursor(8));
/*     */       }
/*     */     }
/*     */     else {
/* 723 */       setCursor(Cursor.getPredefinedCursor(0));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mouseDragged(MouseEvent e)
/*     */   {
/* 732 */     float xcept = 1.0F;float ycept = 1.0F;
/*     */     
/* 734 */     if (this.selXEnd) {
/* 735 */       int currX = e.getX();
/* 736 */       int currY = e.getY();
/*     */       
/* 738 */       System.out.println("selfX");
/*     */       
/* 740 */       if ((this.objFunction.x1_coef <= this.cflimit[0][0]) || (this.objFunction.x1_coef >= this.cflimit[0][1])) {
/* 741 */         this.changed = false;
/* 742 */         if (this.objFunction.x1_coef <= this.cflimit[0][0]) {
/* 743 */           this.objFunction.x1_coef = this.cflimit[0][0];
/* 744 */           this.currentZ = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/*     */         }
/* 746 */         else if (this.objFunction.x1_coef >= this.cflimit[0][1]) {
/* 747 */           this.objFunction.x1_coef = this.cflimit[0][1];
/* 748 */           this.currentZ = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 753 */         this.changed = true;
/*     */       }
/*     */       
/* 756 */       if (this.horzMove) {
/* 757 */         if ((currY < WIDTH - 6) && (this.vertMove))
/* 758 */           this.horzMove = false;
/* 759 */         if (currY >= 170) {
/* 760 */           xcept = (currX - MARGIN) / this.globalZoom;
/* 761 */           if (Math.abs(xcept) > 0.001D) {
/* 762 */             ycept = xcept * this.MaxIntersection.x2 / (xcept - this.MaxIntersection.x1);
/*     */           } else
/* 764 */             ycept = this.MaxIntersection.x2;
/*     */         } else {
/* 766 */           float txcept = (currX - MARGIN) / this.globalZoom;
/* 767 */           float tbase = (WIDTH - MARGIN) / this.globalZoom;
/* 768 */           float tx1 = this.MaxIntersection.x1;
/* 769 */           float tx2 = this.MaxIntersection.x2;
/* 770 */           xcept = -(txcept * tx2 - tbase * tx1) / (tbase - tx2);
/*     */           
/* 772 */           if (xcept > 999999.0D) {
/* 773 */             ycept = this.MaxIntersection.x2;
/*     */           } else
/* 775 */             ycept = xcept * this.MaxIntersection.x2 / (xcept - this.MaxIntersection.x1);
/*     */         }
/*     */       }
/* 778 */       if (this.vertMove) {
/* 779 */         if ((currX < WIDTH - 6) && (this.horzMove))
/* 780 */           this.vertMove = false;
/* 781 */         xcept = ((WIDTH - MARGIN) * this.MaxIntersection.x2 - (WIDTH - currY) * this.MaxIntersection.x1) / (this.MaxIntersection.x2 * this.globalZoom - (WIDTH - currY));
/* 782 */         if (xcept > 999999.0D) {
/* 783 */           ycept = this.MaxIntersection.x2;
/*     */         } else
/* 785 */           ycept = xcept * this.MaxIntersection.x2 / (xcept - this.MaxIntersection.x1);
/*     */       }
/* 787 */       if ((WIDTH - currX <= 3) && (WIDTH - currY <= 3)) {
/* 788 */         this.vertMove = true;
/* 789 */         this.horzMove = true;
/* 790 */         setCursor(Cursor.getPredefinedCursor(13));
/*     */         
/* 792 */         xcept = (currX - MARGIN) / this.globalZoom;
/* 793 */         ycept = xcept * this.MaxIntersection.x2 / (xcept - this.MaxIntersection.x1);
/*     */       }
/* 795 */       this.objFunction.x1_coef = this.old_x1coef;
/* 796 */       this.objFunction.x2_coef = this.old_x2coef;
/* 797 */       float t = ycept * this.objFunction.x2_coef;
/*     */       
/* 799 */       if (xcept > 999999.0D) {
/* 800 */         this.objFunction.x1_coef = 0.0F;
/* 801 */         this.currentZ = (this.MaxIntersection.x2 * this.objFunction.x2_coef);
/*     */ 
/*     */       }
/* 804 */       else if (Math.abs(xcept) < 0.001D)
/*     */       {
/* 806 */         if ((xcept == -0.0F) && (Math.abs(ycept) < 0.001D)) {
/* 807 */           this.objFunction.x1_coef = (-this.old_x1coef);
/*     */         }
/* 809 */         else if ((xcept == 0.001F) && (Math.abs(ycept) < 0.001D)) {
/* 810 */           this.objFunction.x1_coef = this.old_x1coef;
/*     */         }
/*     */         else
/*     */         {
/* 814 */           this.currentZ = (this.MaxIntersection.x2 * this.objFunction.x2_coef);
/*     */         }
/*     */       }
/* 817 */       else if (Math.abs(xcept - this.MaxIntersection.x1) < 0.001D) {
/* 818 */         this.objFunction.x1_coef = Float.POSITIVE_INFINITY;
/* 819 */         this.currentZ = Float.POSITIVE_INFINITY;
/*     */       }
/*     */       else {
/* 822 */         this.objFunction.x1_coef = (t / xcept);
/*     */         
/* 824 */         this.currentZ = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/*     */       }
/*     */       
/* 827 */       if (this.changed) { this.mainApp.changeZ(this.objFunction.x1_coef, this.objFunction.x2_coef, this.currentZ);
/*     */       }
/* 829 */       repaint();
/*     */ 
/*     */ 
/*     */     }
/* 833 */     else if (this.selYEnd) {
/* 834 */       int currX = e.getX();
/* 835 */       int currY = e.getY();
/* 836 */       if ((this.objFunction.x2_coef <= this.cflimit[1][0]) || (this.objFunction.x2_coef >= this.cflimit[1][1])) {
/* 837 */         this.changed = false;
/* 838 */         if (this.objFunction.x2_coef <= this.cflimit[1][0]) {
/* 839 */           this.objFunction.x2_coef = this.cflimit[1][0];
/* 840 */           this.currentZ = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/*     */         }
/* 842 */         else if (this.objFunction.x2_coef >= this.cflimit[1][1]) {
/* 843 */           this.objFunction.x2_coef = this.cflimit[1][1];
/* 844 */           this.currentZ = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/*     */         }
/*     */       }
/*     */       else {
/* 848 */         this.changed = true;
/*     */       }
/* 850 */       if ((currX > MARGIN + 6) && (currY > MARGIN + 6))
/*     */       {
/* 852 */         return;
/*     */       }
/* 854 */       if (this.vertMove) {
/* 855 */         if ((currX > MARGIN + 6) && (this.horzMove))
/* 856 */           this.vertMove = false;
/* 857 */         ycept = (WIDTH - currY) / this.globalZoom;
/* 858 */         if (ycept > 0.001D) {
/* 859 */           xcept = ycept * this.MaxIntersection.x1 / (ycept - this.MaxIntersection.x2);
/*     */         } else
/* 861 */           xcept = this.MaxIntersection.x1;
/*     */       }
/* 863 */       if (this.horzMove) {
/* 864 */         if ((currY > MARGIN + 6) && (this.vertMove))
/* 865 */           this.horzMove = false;
/* 866 */         ycept = ((WIDTH - MARGIN) * this.MaxIntersection.x1 - (currX - MARGIN) * this.MaxIntersection.x2) / (this.MaxIntersection.x1 * this.globalZoom - (currX - MARGIN));
/* 867 */         if (ycept > 999999.0D) {
/* 868 */           xcept = this.MaxIntersection.x1;
/*     */         } else {
/* 870 */           xcept = ycept * this.MaxIntersection.x1 / (ycept - this.MaxIntersection.x2);
/*     */         }
/*     */       }
/* 873 */       if ((currX <= MARGIN + 3) && (currY <= MARGIN + 3)) {
/* 874 */         this.vertMove = true;
/* 875 */         this.horzMove = true;
/* 876 */         setCursor(Cursor.getPredefinedCursor(13));
/*     */         
/* 878 */         ycept = (WIDTH - currY) / this.globalZoom;
/* 879 */         xcept = ycept * this.MaxIntersection.x1 / (ycept - this.MaxIntersection.x2);
/*     */       }
/* 881 */       this.objFunction.x1_coef = this.old_x1coef;
/* 882 */       this.objFunction.x2_coef = this.old_x2coef;
/*     */       
/* 884 */       float t = xcept * this.objFunction.x1_coef;
/*     */       
/* 886 */       if (ycept > 999999.0D) {
/* 887 */         this.objFunction.x2_coef = 0.0F;
/* 888 */         this.currentZ = (this.MaxIntersection.x1 * this.objFunction.x1_coef);
/*     */       }
/* 890 */       else if (Math.abs(ycept) < 0.001D) {
/* 891 */         if ((ycept == -0.0F) && (Math.abs(xcept) < 0.001D)) {
/* 892 */           this.objFunction.x2_coef = (-this.old_x2coef);
/*     */         }
/* 894 */         else if ((ycept == 0.001F) && (Math.abs(xcept) < 0.001D)) {
/* 895 */           this.objFunction.x2_coef = this.old_x2coef;
/*     */         }
/*     */         else {
/* 898 */           this.currentZ = (this.MaxIntersection.x1 * this.objFunction.x1_coef);
/*     */         }
/*     */         
/*     */       }
/* 902 */       else if (Math.abs(ycept - this.MaxIntersection.x2) < 0.001D) {
/* 903 */         this.objFunction.x2_coef = Float.POSITIVE_INFINITY;
/* 904 */         this.currentZ = Float.POSITIVE_INFINITY;
/*     */       }
/*     */       else {
/* 907 */         this.objFunction.x2_coef = (t / ycept);
/* 908 */         this.currentZ = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/*     */       }
/* 910 */       if (this.changed) { this.mainApp.changeZ(this.objFunction.x1_coef, this.objFunction.x2_coef, this.currentZ);
/*     */       }
/* 912 */       repaint();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPSensPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */