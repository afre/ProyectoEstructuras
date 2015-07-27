/*     */ import java.awt.Color;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class sensCanvas extends JPanel implements MouseMotionListener
/*     */ {
/*  17 */   private static int CANVASIZE = 340;
/*  18 */   private static int MARGIN = 20;
/*  19 */   private static int WIDTH = 320;
/*     */   
/*     */   private Image bufzone;
/*     */   
/*  23 */   private Color tranColor = new Color(0, 160, 0);
/*     */   
/*     */   private LinearProg mainApp;
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
/*     */   public sensCanvas(LinearProg main, int zoom, allConstraints cons, Objective objfunc, Intersection opints, float[][] coefsp)
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
/*     */ 
/*     */ 
/* 368 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 369 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 370 */             tempPolygon.addPoint(0, 340);
/* 371 */             tempPolygon.addPoint(0, 0);
/* 372 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 374 */           else if (tempCons.startp.y > tempCons.endp.y) {
/* 375 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 376 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 377 */             tempPolygon.addPoint(0, 340);
/* 378 */             tempPolygon.addPoint(340, 340);
/* 379 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 381 */           else if (tempCons.startp.y == tempCons.endp.y)
/*     */           {
/*     */ 
/*     */ 
/* 385 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 386 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 387 */             tempPolygon.addPoint(0, 340);
/* 388 */             tempPolygon.addPoint(0, 0);
/* 389 */             tempPolygon.addPoint(340, 0);
/*     */           }
/*     */           
/*     */         }
/* 393 */         else if (tempCons.startp.y <= tempCons.endp.y)
/*     */         {
/* 395 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 396 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 397 */           tempPolygon.addPoint(340, 340);
/* 398 */           tempPolygon.addPoint(340, 0);
/* 399 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */         else {
/* 402 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 403 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 404 */           tempPolygon.addPoint(340, 340);
/* 405 */           tempPolygon.addPoint(340, 0);
/* 406 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */         
/*     */       }
/* 410 */       else if (tempCons.inequating == 2)
/*     */       {
/* 412 */         if (tempslope >= 0) {
/* 413 */           if (tempCons.startp.y < tempCons.endp.y) {
/* 414 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 415 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 416 */             tempPolygon.addPoint(0, 340);
/* 417 */             tempPolygon.addPoint(340, 340);
/* 418 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 420 */           else if (tempCons.startp.y > tempCons.endp.y) {
/* 421 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 422 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 423 */             tempPolygon.addPoint(0, 340);
/* 424 */             tempPolygon.addPoint(0, 0);
/* 425 */             tempPolygon.addPoint(340, 0);
/*     */           }
/* 427 */           else if (tempCons.startp.y == tempCons.endp.y) {
/* 428 */             tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 429 */             tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 430 */             tempPolygon.addPoint(0, 340);
/* 431 */             tempPolygon.addPoint(340, 340);
/* 432 */             tempPolygon.addPoint(340, 0);
/*     */           }
/*     */           
/*     */         }
/* 436 */         else if (tempCons.startp.y <= tempCons.endp.y)
/*     */         {
/* 438 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 439 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 440 */           tempPolygon.addPoint(340, 340);
/* 441 */           tempPolygon.addPoint(0, 340);
/* 442 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */         else
/*     */         {
/* 446 */           tempPolygon.addPoint(tempCons.endp.x, tempCons.endp.y);
/* 447 */           tempPolygon.addPoint(tempCons.startp.x, tempCons.startp.y);
/* 448 */           tempPolygon.addPoint(340, 340);
/* 449 */           tempPolygon.addPoint(0, 340);
/* 450 */           tempPolygon.addPoint(0, 0);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 457 */       g.setColor(Color.white);
/* 458 */       g.fillPolygon(tempPolygon);
/* 459 */       tempPolygon = null;
/*     */     }
/*     */     
/* 462 */     g.setColor(Color.blue);
/* 463 */     e = this.currentCons.vConstraints.elements();
/* 464 */     while (e.hasMoreElements())
/*     */     {
/* 466 */       tempCons = (Constraint)e.nextElement();
/* 467 */       tempCons.withZoom(this.globalZoom);
/* 468 */       g.drawLine(tempCons.startp.x, tempCons.startp.y, tempCons.endp.x, tempCons.endp.y);
/*     */     }
/* 470 */     if ((stpt != null) && (edpt != null)) {
/* 471 */       g.setColor(Color.magenta);
/* 472 */       g.drawLine(stpt.x, stpt.y, edpt.x, edpt.y);
/*     */     }
/* 474 */     g.setColor(Color.white);
/*     */     
/* 476 */     g.fillRect(0, 0, 20, 340);
/* 477 */     g.fillRect(320, 0, 22, 340);
/* 478 */     g.fillRect(0, 320, 340, 20);
/* 479 */     g.fillRect(0, 0, 340, 20);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawFeaCones(Graphics g)
/*     */   {
/* 487 */     if (this.currentCons == null) {
/* 488 */       return;
/*     */     }
/* 490 */     Intersection tempIntersection = null;
/* 491 */     Enumeration e = this.currentCons.vFeasibleIntersections.elements();
/*     */     
/* 493 */     while (e.hasMoreElements())
/*     */     {
/* 495 */       tempIntersection = (Intersection)e.nextElement();
/* 496 */       Point conept = new Point(MARGIN + (int)(tempIntersection.x1 * this.globalZoom + 1.0E-4D), WIDTH - (int)(tempIntersection.x2 * this.globalZoom + 1.0E-4D));
/*     */       
/* 498 */       drawPoint(g, conept, Color.orange);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawPoint(Graphics g, Point p, Color c)
/*     */   {
/* 507 */     g.setColor(c);
/* 508 */     g.fillOval(p.x - 3, p.y - 3, 6, 6);
/* 509 */     g.setColor(Color.black);
/* 510 */     g.drawOval(p.x - 3, p.y - 3, 6, 6);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawOptimal(Graphics g)
/*     */   {
/* 519 */     Point opmapt1 = null;Point opmapt2 = null;
/*     */     
/*     */ 
/* 522 */     Point OptimalPoint = new Point((int)(MARGIN + this.MaxIntersection.x1 * this.globalZoom + 1.0E-4D), (int)(WIDTH - this.MaxIntersection.x2 * this.globalZoom + 1.0E-4D));
/* 523 */     drawPoint(g, OptimalPoint, Color.red);
/*     */     
/* 525 */     this.trian1 = null;
/* 526 */     this.trian2 = null;
/*     */     
/* 528 */     if (((this.currentZ >= Float.MAX_VALUE) && (this.objFunction.x2_coef >= Float.MAX_VALUE)) || ((this.currentZ < Float.MAX_VALUE) && (Math.abs(this.objFunction.x1_coef) < 0.001D)))
/*     */     {
/* 530 */       opmapt1 = new Point(WIDTH, (int)(WIDTH - this.MaxIntersection.x2 * this.globalZoom + 1.0E-4D));
/* 531 */       opmapt2 = new Point(MARGIN, (int)(WIDTH - this.MaxIntersection.x2 * this.globalZoom + 1.0E-4D));
/*     */     }
/* 533 */     else if (((this.currentZ >= Float.MAX_VALUE) && (this.objFunction.x1_coef >= Float.MAX_VALUE)) || ((this.currentZ < Float.MAX_VALUE) && (Math.abs(this.objFunction.x2_coef) < 0.001D)))
/*     */     {
/* 535 */       opmapt1 = new Point((int)(MARGIN + this.MaxIntersection.x1 * this.globalZoom + 1.0E-4D), WIDTH);
/* 536 */       opmapt2 = new Point((int)(MARGIN + this.MaxIntersection.x1 * this.globalZoom + 1.0E-4D), MARGIN);
/*     */     }
/*     */     else {
/* 539 */       float opcept1x = this.currentZ / this.objFunction.x1_coef;
/* 540 */       float opcept2y = this.currentZ / this.objFunction.x2_coef;
/* 541 */       if (opcept1x > 0.001D) {
/* 542 */         float opcept1y = 0.0F;
/* 543 */         if (Math.abs(opcept1x * this.globalZoom) > WIDTH - MARGIN) {
/* 544 */           opcept1x = (WIDTH - MARGIN) / this.globalZoom;
/* 545 */           opcept1y = (this.currentZ - opcept1x * this.objFunction.x1_coef) / this.objFunction.x2_coef;
/*     */         }
/* 547 */         opmapt1 = new Point((int)(MARGIN + opcept1x * this.globalZoom + 1.0E-4D), (int)(WIDTH - opcept1y * this.globalZoom + 1.0E-4D));
/*     */       }
/*     */       else {
/* 550 */         float opcept1y = this.currentZ / this.objFunction.x2_coef;
/* 551 */         opmapt1 = new Point((int)(MARGIN + 1.0E-4D), (int)(WIDTH - opcept1y * this.globalZoom + 1.0E-4D));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 556 */       this.lastxpt = opmapt1;
/*     */       
/* 558 */       float opcept2x = this.currentZ / this.objFunction.x1_coef;
/*     */       
/*     */ 
/* 561 */       if (opcept2x > 0.001D) {
/* 562 */         opcept2x = 0.0F;
/* 563 */         opcept2y = this.currentZ / this.objFunction.x2_coef;
/*     */         
/* 565 */         if (opcept2y > 0.001D) {
/* 566 */           if (Math.abs(opcept2y * this.globalZoom) > WIDTH - MARGIN)
/*     */           {
/* 568 */             opcept2y = (WIDTH - MARGIN) / this.globalZoom;
/* 569 */             opcept2x = (this.currentZ - opcept2y * this.objFunction.x2_coef) / this.objFunction.x1_coef;
/*     */           }
/* 571 */           opmapt2 = new Point((int)(MARGIN + opcept2x * this.globalZoom + 1.0E-4D), (int)(WIDTH - opcept2y * this.globalZoom + 1.0E-4D));
/*     */         }
/*     */         else
/*     */         {
/* 575 */           opcept2y = -opcept2y;
/* 576 */           float twidth = (WIDTH - MARGIN) / this.globalZoom;
/* 577 */           opcept2x = (twidth + opcept2y) * opcept1x / opcept2y;
/* 578 */           opcept2y = 0.0F;
/* 579 */           opmapt2 = new Point((int)(MARGIN + opcept2x * this.globalZoom + 1.0E-4D), MARGIN);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 584 */         float twidth = (WIDTH - MARGIN) / this.globalZoom;
/* 585 */         opcept2x = -1 * opcept2x * (twidth - opcept2y) / opcept2y;
/* 586 */         opmapt2 = new Point((int)(MARGIN + opcept2x * this.globalZoom + 1.0E-4D), (int)(MARGIN + 1.0E-4D));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 592 */       this.lastypt = opmapt2;
/*     */       
/*     */ 
/*     */ 
/* 596 */       float NormalSlope = -1 * this.objFunction.x1_coef / this.objFunction.x2_coef;
/*     */       
/* 598 */       if ((Math.abs(NormalSlope) < 99999.0D) && (NormalSlope > 0.001D)) {
/* 599 */         Point temp = new Point();
/* 600 */         temp = opmapt1;
/* 601 */         opmapt1 = opmapt2;
/* 602 */         opmapt2 = temp;
/*     */       }
/*     */     }
/*     */     
/* 606 */     if (this.changed) {
/* 607 */       this.topmapt1 = opmapt1;
/* 608 */       this.topmapt2 = opmapt2;
/*     */     }
/* 610 */     if (this.xmaxpt.x > 0)
/*     */     {
/* 612 */       this.trian1 = new Polygon();
/* 613 */       if (opmapt1.x >= WIDTH) {
/* 614 */         this.trian1.addPoint(WIDTH, this.topmapt1.y - 3);
/* 615 */         this.trian1.addPoint(WIDTH, this.topmapt1.y + 3);
/* 616 */         this.trian1.addPoint(WIDTH - 6, this.topmapt1.y);
/*     */       }
/*     */       else {
/* 619 */         this.trian1.addPoint(this.topmapt1.x - 3, this.topmapt1.y);
/* 620 */         this.trian1.addPoint(this.topmapt1.x + 3, this.topmapt1.y);
/* 621 */         this.trian1.addPoint(this.topmapt1.x, this.topmapt1.y - 6);
/*     */       }
/* 623 */       g.setColor(this.tranColor);
/* 624 */       g.fillPolygon(this.trian1);
/* 625 */       g.drawString("x1", this.topmapt1.x + 5, this.topmapt1.y - 5);
/* 626 */       drawTriangle(g, this.xmaxpt, 0, Color.magenta);
/* 627 */       drawTriangle(g, this.xminpt, 0, Color.black);
/*     */     }
/*     */     
/*     */ 
/* 631 */     if (this.ymaxpt.y > 0) {
/* 632 */       this.trian2 = new Polygon();
/* 633 */       if (opmapt2.y <= MARGIN) {
/* 634 */         this.trian2.addPoint(this.topmapt2.x - 3, MARGIN);
/* 635 */         this.trian2.addPoint(this.topmapt2.x + 3, MARGIN);
/* 636 */         this.trian2.addPoint(this.topmapt2.x, MARGIN + 6);
/*     */       }
/*     */       else {
/* 639 */         this.trian2.addPoint(this.topmapt2.x, this.topmapt2.y - 3);
/* 640 */         this.trian2.addPoint(this.topmapt2.x, this.topmapt2.y + 3);
/* 641 */         this.trian2.addPoint(this.topmapt2.x + 6, this.topmapt2.y);
/*     */       }
/* 643 */       g.setColor(this.tranColor);
/* 644 */       g.fillPolygon(this.trian2);
/* 645 */       drawTriangle(g, this.ymaxpt, 1, Color.magenta);
/* 646 */       drawTriangle(g, this.yminpt, 1, Color.black);
/*     */     }
/*     */     
/*     */ 
/* 650 */     g.setColor(Color.red);
/* 651 */     g.drawLine(this.topmapt1.x, this.topmapt1.y, this.topmapt2.x, this.topmapt2.y);
/*     */     
/*     */ 
/*     */ 
/* 655 */     int opxpix = (int)(this.globalZoom * this.MaxIntersection.x1);
/* 656 */     int opypix = (int)(this.globalZoom * this.MaxIntersection.x2);
/* 657 */     if (this.optimalPolygon == null) {
/* 658 */       this.optimalPolygon = new Polygon();
/*     */       
/* 660 */       this.optimalPolygon.addPoint(MARGIN + opxpix + 10, WIDTH - (opypix + 5));
/* 661 */       this.optimalPolygon.addPoint(MARGIN + opxpix + 5, WIDTH - (opypix + 10));
/* 662 */       this.optimalPolygon.addPoint(MARGIN + opxpix + 2, WIDTH - (opypix + 2));
/*     */     }
/*     */     
/* 665 */     g.setColor(Color.red);
/* 666 */     g.fillPolygon(this.optimalPolygon);
/*     */     
/*     */ 
/* 669 */     g.drawLine(MARGIN + opxpix, WIDTH - opypix, MARGIN + opxpix + 20, WIDTH - (opypix + 20));
/*     */     
/* 671 */     g.drawString("Optimum", MARGIN + opxpix + 23, WIDTH - (opypix + 20));
/*     */     
/*     */ 
/* 674 */     g.setColor(Color.white);
/* 675 */     g.fillRect(0, 0, 20, 340);
/* 676 */     g.fillRect(320, 0, 22, 340);
/* 677 */     g.fillRect(0, 320, 340, 20);
/* 678 */     g.fillRect(0, 0, 340, 20);
/*     */     
/*     */ 
/* 681 */     g.setColor(Color.blue);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mouseMoved(MouseEvent e)
/*     */   {
/* 690 */     if ((this.trian1 != null) && (this.trian1.contains(e.getPoint())))
/*     */     {
/* 692 */       this.selXEnd = true;
/* 693 */       this.selYEnd = false;
/* 694 */       if (this.trian1.xpoints[0] == WIDTH) {
/* 695 */         this.vertMove = true;
/* 696 */         this.horzMove = false;
/* 697 */         setCursor(Cursor.getPredefinedCursor(8));
/*     */       }
/*     */       else {
/* 700 */         this.vertMove = false;
/* 701 */         this.horzMove = true;
/* 702 */         setCursor(Cursor.getPredefinedCursor(11));
/*     */       }
/*     */     }
/* 705 */     else if ((this.trian2 != null) && (this.trian2.contains(e.getPoint())))
/*     */     {
/* 707 */       this.selXEnd = false;
/* 708 */       this.selYEnd = true;
/* 709 */       if (this.trian2.ypoints[0] == MARGIN) {
/* 710 */         this.vertMove = false;
/* 711 */         this.horzMove = true;
/* 712 */         setCursor(Cursor.getPredefinedCursor(11));
/*     */       }
/*     */       else {
/* 715 */         this.vertMove = true;
/* 716 */         this.horzMove = false;
/* 717 */         setCursor(Cursor.getPredefinedCursor(8));
/*     */       }
/*     */     }
/*     */     else {
/* 721 */       setCursor(Cursor.getPredefinedCursor(0));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mouseDragged(MouseEvent e)
/*     */   {
/* 730 */     float xcept = 1.0F;float ycept = 1.0F;
/*     */     
/* 732 */     if (this.selXEnd) {
/* 733 */       int currX = e.getX();
/* 734 */       int currY = e.getY();
/*     */       
/*     */ 
/* 737 */       if ((this.objFunction.x1_coef <= this.cflimit[0][0]) || (this.objFunction.x1_coef >= this.cflimit[0][1])) {
/* 738 */         this.changed = false;
/* 739 */         if (this.objFunction.x1_coef <= this.cflimit[0][0]) {
/* 740 */           this.objFunction.x1_coef = this.cflimit[0][0];
/* 741 */           this.currentZ = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/*     */         }
/* 743 */         else if (this.objFunction.x1_coef >= this.cflimit[0][1]) {
/* 744 */           this.objFunction.x1_coef = this.cflimit[0][1];
/* 745 */           this.currentZ = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 750 */         this.changed = true;
/*     */       }
/*     */       
/* 753 */       if (this.horzMove) {
/* 754 */         if ((currY < WIDTH - 6) && (this.vertMove))
/* 755 */           this.horzMove = false;
/* 756 */         if (currY >= 170) {
/* 757 */           xcept = (currX - MARGIN) / this.globalZoom;
/* 758 */           if (Math.abs(xcept) > 0.001D) {
/* 759 */             ycept = xcept * this.MaxIntersection.x2 / (xcept - this.MaxIntersection.x1);
/*     */           } else
/* 761 */             ycept = this.MaxIntersection.x2;
/*     */         } else {
/* 763 */           float txcept = (currX - MARGIN) / this.globalZoom;
/* 764 */           float tbase = (WIDTH - MARGIN) / this.globalZoom;
/* 765 */           float tx1 = this.MaxIntersection.x1;
/* 766 */           float tx2 = this.MaxIntersection.x2;
/* 767 */           xcept = -(txcept * tx2 - tbase * tx1) / (tbase - tx2);
/*     */           
/* 769 */           if (xcept > 999999.0D) {
/* 770 */             ycept = this.MaxIntersection.x2;
/*     */           } else
/* 772 */             ycept = xcept * this.MaxIntersection.x2 / (xcept - this.MaxIntersection.x1);
/*     */         }
/*     */       }
/* 775 */       if (this.vertMove) {
/* 776 */         if ((currX < WIDTH - 6) && (this.horzMove))
/* 777 */           this.vertMove = false;
/* 778 */         xcept = ((WIDTH - MARGIN) * this.MaxIntersection.x2 - (WIDTH - currY) * this.MaxIntersection.x1) / (this.MaxIntersection.x2 * this.globalZoom - (WIDTH - currY));
/* 779 */         if (xcept > 999999.0D) {
/* 780 */           ycept = this.MaxIntersection.x2;
/*     */         } else
/* 782 */           ycept = xcept * this.MaxIntersection.x2 / (xcept - this.MaxIntersection.x1);
/*     */       }
/* 784 */       if ((WIDTH - currX <= 3) && (WIDTH - currY <= 3)) {
/* 785 */         this.vertMove = true;
/* 786 */         this.horzMove = true;
/* 787 */         setCursor(Cursor.getPredefinedCursor(13));
/*     */         
/* 789 */         xcept = (currX - MARGIN) / this.globalZoom;
/* 790 */         ycept = xcept * this.MaxIntersection.x2 / (xcept - this.MaxIntersection.x1);
/*     */       }
/* 792 */       this.objFunction.x1_coef = this.old_x1coef;
/* 793 */       this.objFunction.x2_coef = this.old_x2coef;
/* 794 */       float t = ycept * this.objFunction.x2_coef;
/*     */       
/* 796 */       if (xcept > 999999.0D) {
/* 797 */         this.objFunction.x1_coef = 0.0F;
/* 798 */         this.currentZ = (this.MaxIntersection.x2 * this.objFunction.x2_coef);
/*     */ 
/*     */       }
/* 801 */       else if (Math.abs(xcept) < 0.001D)
/*     */       {
/* 803 */         if ((xcept == -0.0F) && (Math.abs(ycept) < 0.001D)) {
/* 804 */           this.objFunction.x1_coef = (-this.old_x1coef);
/*     */         }
/* 806 */         else if ((xcept == 0.001F) && (Math.abs(ycept) < 0.001D)) {
/* 807 */           this.objFunction.x1_coef = this.old_x1coef;
/*     */         }
/*     */         else
/*     */         {
/* 811 */           this.currentZ = (this.MaxIntersection.x2 * this.objFunction.x2_coef);
/*     */         }
/*     */       }
/* 814 */       else if (Math.abs(xcept - this.MaxIntersection.x1) < 0.001D) {
/* 815 */         this.objFunction.x1_coef = Float.POSITIVE_INFINITY;
/* 816 */         this.currentZ = Float.POSITIVE_INFINITY;
/*     */       }
/*     */       else {
/* 819 */         this.objFunction.x1_coef = (t / xcept);
/*     */         
/* 821 */         this.currentZ = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/*     */       }
/*     */       
/* 824 */       if (this.changed) { this.mainApp.changeZ(this.objFunction.x1_coef, this.objFunction.x2_coef, this.currentZ);
/*     */       }
/* 826 */       repaint();
/*     */ 
/*     */ 
/*     */     }
/* 830 */     else if (this.selYEnd) {
/* 831 */       int currX = e.getX();
/* 832 */       int currY = e.getY();
/* 833 */       if ((this.objFunction.x2_coef <= this.cflimit[1][0]) || (this.objFunction.x2_coef >= this.cflimit[1][1])) {
/* 834 */         this.changed = false;
/* 835 */         if (this.objFunction.x2_coef <= this.cflimit[1][0]) {
/* 836 */           this.objFunction.x2_coef = this.cflimit[1][0];
/* 837 */           this.currentZ = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/*     */         }
/* 839 */         else if (this.objFunction.x2_coef >= this.cflimit[1][1]) {
/* 840 */           this.objFunction.x2_coef = this.cflimit[1][1];
/* 841 */           this.currentZ = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/*     */         }
/*     */       }
/*     */       else {
/* 845 */         this.changed = true;
/*     */       }
/* 847 */       if ((currX > MARGIN + 6) && (currY > MARGIN + 6))
/*     */       {
/* 849 */         return;
/*     */       }
/* 851 */       if (this.vertMove) {
/* 852 */         if ((currX > MARGIN + 6) && (this.horzMove))
/* 853 */           this.vertMove = false;
/* 854 */         ycept = (WIDTH - currY) / this.globalZoom;
/* 855 */         if (ycept > 0.001D) {
/* 856 */           xcept = ycept * this.MaxIntersection.x1 / (ycept - this.MaxIntersection.x2);
/*     */         } else
/* 858 */           xcept = this.MaxIntersection.x1;
/*     */       }
/* 860 */       if (this.horzMove) {
/* 861 */         if ((currY > MARGIN + 6) && (this.vertMove))
/* 862 */           this.horzMove = false;
/* 863 */         ycept = ((WIDTH - MARGIN) * this.MaxIntersection.x1 - (currX - MARGIN) * this.MaxIntersection.x2) / (this.MaxIntersection.x1 * this.globalZoom - (currX - MARGIN));
/* 864 */         if (ycept > 999999.0D) {
/* 865 */           xcept = this.MaxIntersection.x1;
/*     */         } else {
/* 867 */           xcept = ycept * this.MaxIntersection.x1 / (ycept - this.MaxIntersection.x2);
/*     */         }
/*     */       }
/* 870 */       if ((currX <= MARGIN + 3) && (currY <= MARGIN + 3)) {
/* 871 */         this.vertMove = true;
/* 872 */         this.horzMove = true;
/* 873 */         setCursor(Cursor.getPredefinedCursor(13));
/*     */         
/* 875 */         ycept = (WIDTH - currY) / this.globalZoom;
/* 876 */         xcept = ycept * this.MaxIntersection.x1 / (ycept - this.MaxIntersection.x2);
/*     */       }
/* 878 */       this.objFunction.x1_coef = this.old_x1coef;
/* 879 */       this.objFunction.x2_coef = this.old_x2coef;
/*     */       
/* 881 */       float t = xcept * this.objFunction.x1_coef;
/*     */       
/* 883 */       if (ycept > 999999.0D) {
/* 884 */         this.objFunction.x2_coef = 0.0F;
/* 885 */         this.currentZ = (this.MaxIntersection.x1 * this.objFunction.x1_coef);
/*     */       }
/* 887 */       else if (Math.abs(ycept) < 0.001D) {
/* 888 */         if ((ycept == -0.0F) && (Math.abs(xcept) < 0.001D)) {
/* 889 */           this.objFunction.x2_coef = (-this.old_x2coef);
/*     */         }
/* 891 */         else if ((ycept == 0.001F) && (Math.abs(xcept) < 0.001D)) {
/* 892 */           this.objFunction.x2_coef = this.old_x2coef;
/*     */         }
/*     */         else {
/* 895 */           this.currentZ = (this.MaxIntersection.x1 * this.objFunction.x1_coef);
/*     */         }
/*     */         
/*     */       }
/* 899 */       else if (Math.abs(ycept - this.MaxIntersection.x2) < 0.001D) {
/* 900 */         this.objFunction.x2_coef = Float.POSITIVE_INFINITY;
/* 901 */         this.currentZ = Float.POSITIVE_INFINITY;
/*     */       }
/*     */       else {
/* 904 */         this.objFunction.x2_coef = (t / ycept);
/* 905 */         this.currentZ = this.objFunction.calcZ(this.MaxIntersection.x1, this.MaxIntersection.x2);
/*     */       }
/* 907 */       if (this.changed) { this.mainApp.changeZ(this.objFunction.x1_coef, this.objFunction.x2_coef, this.currentZ);
/*     */       }
/* 909 */       repaint();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\sensCanvas.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */