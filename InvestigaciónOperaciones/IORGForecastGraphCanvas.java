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
/*     */ import java.io.PrintStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORGForecastGraphCanvas
/*     */   extends JPanel
/*     */   implements MouseMotionListener
/*     */ {
/*  22 */   private static int CANVASIZE = 325;
/*  23 */   private static int MARGIN = 20;
/*  24 */   private static int WIDTH = 310;
/*  25 */   private static int MAX_PERIOD = 20;
/*     */   
/*     */   private Image bufzone;
/*     */   private int Xzoom;
/*     */   private float Zoom;
/*     */   private IORGForecastPane mainApp;
/*     */   private int ystart;
/*     */   private int yend;
/*  33 */   private Polygon optimalPolygon = null;
/*     */   private Vector actPoints;
/*  35 */   private Point selPoint = null;
/*  36 */   private int selIndex = -1;
/*     */   private float[] actDat;
/*     */   private float[] foreDat;
/*     */   private float[] xDat;
/*     */   private int activeNum;
/*     */   private float acept;
/*     */   private float bcept;
/*     */   private float maxval;
/*  44 */   private float minval; private float predxmin; private float predxmax; private int LineType = 0;
/*  45 */   private boolean isReady = false;
/*  46 */   private DecimalFormat decfm = new DecimalFormat();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORGForecastGraphCanvas(IORGForecastPane app)
/*     */   {
/*  54 */     this.mainApp = app;
/*  55 */     this.Zoom = 1.0F;
/*  56 */     this.Xzoom = 1;
/*  57 */     this.decfm.setGroupingUsed(false);
/*  58 */     this.decfm.setMaximumFractionDigits(2);
/*  59 */     addMouseMotionListener(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update(Graphics g)
/*     */   {
/*  67 */     paint(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/*  75 */     if (this.bufzone == null) {
/*  76 */       this.bufzone = createImage(getSize().width, getSize().height);
/*     */     }
/*  78 */     Graphics bfg = this.bufzone.getGraphics();
/*  79 */     bfg.setClip(0, 0, getSize().width, getSize().height);
/*  80 */     super.paint(bfg);
/*     */     
/*     */ 
/*  83 */     drawAxis(bfg);
/*  84 */     if (this.isReady) {
/*  85 */       if ((this.LineType == IORGForecastPane.EXP_SMOOTH) || (this.LineType == IORGForecastPane.TREND_ADJUST_SMOOTH)) {
/*  86 */         drawZLine(bfg);
/*  87 */       } else if (this.LineType == IORGForecastPane.LINEAR_TREND) {
/*  88 */         drawTrend(bfg);
/*     */       } else {
/*  90 */         drawRegression(bfg);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  99 */     g.drawImage(this.bufzone, 0, 0, null);
/* 100 */     bfg.dispose();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void drawAxis(Graphics g)
/*     */   {
/* 110 */     g.setColor(Color.white);
/* 111 */     g.fillRect(0, 0, CANVASIZE, CANVASIZE);
/* 112 */     g.setColor(Color.black);
/*     */     
/* 114 */     g.drawLine(MARGIN, WIDTH, WIDTH, WIDTH);
/* 115 */     g.drawLine(MARGIN, MARGIN, MARGIN, WIDTH);
/* 116 */     g.drawLine(MARGIN, MARGIN, WIDTH, MARGIN);
/* 117 */     g.drawLine(WIDTH, WIDTH, WIDTH, MARGIN);
/* 118 */     if (this.isReady) {
/* 119 */       Font origFont = g.getFont();
/* 120 */       g.setFont(new Font(origFont.getName(), origFont.getStyle(), 9));
/* 121 */       drawUnit(g);
/* 122 */       g.setFont(origFont);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawUnit(Graphics g)
/*     */   {
/* 133 */     if (this.LineType != IORGForecastPane.LINEAR_REGRESSION) {
/* 134 */       int xstep = (WIDTH - MARGIN) / MAX_PERIOD;
/* 135 */       for (int i = 0; i < MAX_PERIOD; i++) {
/* 136 */         g.drawLine(MARGIN + i * xstep, WIDTH - 1, MARGIN + i * xstep, WIDTH + 1);
/* 137 */         g.drawString(String.valueOf(String.valueOf(new StringBuffer("").append(i + 1))), MARGIN + i * xstep - 3, WIDTH + 12);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 142 */     this.predxmax = this.xDat[0];
/* 143 */     this.predxmin = this.xDat[0];
/* 144 */     for (int i = 0; i < this.activeNum; i++)
/*     */     {
/*     */ 
/* 147 */       if (this.xDat[i] > this.predxmax)
/* 148 */         this.predxmax = this.xDat[i];
/* 149 */       if (this.xDat[i] < this.predxmin)
/* 150 */         this.predxmin = this.xDat[i];
/*     */     }
/* 152 */     if (this.predxmax < 2)
/* 153 */       return;
/* 154 */     int xend = (int)(this.predxmax + 1.5D);
/* 155 */     this.Xzoom = ((WIDTH - MARGIN) / xend);
/* 156 */     for (i = 0; i <= xend; i++) {
/* 157 */       g.drawLine(MARGIN + i * this.Xzoom, WIDTH - 1, MARGIN + i * this.Xzoom, WIDTH + 1);
/* 158 */       g.drawString("".concat(String.valueOf(String.valueOf(i))), MARGIN + i * this.Xzoom - 3, WIDTH + 12);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 163 */     this.yend = ((int)(this.maxval + (this.maxval - this.minval) * 0.2D));
/* 164 */     this.ystart = ((int)(this.minval - (this.maxval - this.minval) * 0.2D));
/*     */     
/*     */ 
/*     */ 
/* 168 */     if (this.yend - this.ystart < 2) return;
/*     */     int ystep;
/* 170 */     int ystep; if (this.yend - this.ystart < 15) {
/* 171 */       ystep = 1; } else { int ystep;
/* 172 */       if (this.yend - this.ystart < 30) {
/* 173 */         ystep = 2; } else { int ystep;
/* 174 */         if (this.yend - this.ystart < 60) {
/* 175 */           ystep = 5; } else { int ystep;
/* 176 */           if (this.yend - this.ystart < 120) {
/* 177 */             ystep = 10; } else { int ystep;
/* 178 */             if (this.yend - this.ystart < 180) {
/* 179 */               ystep = 20; } else { int ystep;
/* 180 */               if (this.yend - this.ystart < 320) {
/* 181 */                 ystep = 40;
/*     */               } else
/* 183 */                 ystep = 50;
/*     */             } } } } }
/* 185 */     this.ystart = (this.ystart / ystep * ystep);
/* 186 */     this.yend = ((this.yend / ystep + 1) * ystep);
/*     */     
/* 188 */     this.Zoom = ((float)(1.0D * (WIDTH - MARGIN) / (this.yend - this.ystart)));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 194 */     int niche = 0;
/* 195 */     int pxl = 0;
/* 196 */     while (pxl <= WIDTH - MARGIN) {
/* 197 */       g.drawLine(MARGIN - 1, WIDTH - pxl, MARGIN + 1, WIDTH - pxl);
/* 198 */       g.drawString(String.valueOf(String.valueOf(new StringBuffer("").append(this.ystart + niche))), 6, WIDTH - pxl + 5);
/* 199 */       niche += ystep;
/* 200 */       pxl += (int)(ystep * this.Zoom);
/*     */     }
/*     */     
/* 203 */     Font oldFont = g.getFont();
/* 204 */     g.setFont(new Font("Arial", 0, 10));
/*     */     
/* 206 */     g.setColor(Color.blue);
/* 207 */     g.drawLine(190, 10, 210, 10);
/* 208 */     g.setColor(Color.black);
/* 209 */     g.drawString("Data", 215, 14);
/* 210 */     g.setColor(Color.red);
/* 211 */     g.drawLine(250, 10, 270, 10);
/* 212 */     g.setColor(Color.black);
/* 213 */     g.drawString("Forecast", 275, 14);
/*     */     
/* 215 */     g.setFont(oldFont);
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
/*     */   public void beginPlot(float[] actual, float[] fcast, float line_a, float line_b, int method)
/*     */   {
/* 230 */     this.isReady = true;
/* 231 */     this.LineType = method;
/*     */     
/* 233 */     this.actDat = actual;
/* 234 */     if (method != IORGForecastPane.LINEAR_REGRESSION) {
/* 235 */       this.foreDat = fcast;
/*     */     } else {
/* 237 */       this.xDat = fcast;
/*     */     }
/*     */     
/* 240 */     int nonzero = 0;
/* 241 */     for (int i = 0; i < this.actDat.length; i++)
/* 242 */       if ((this.actDat[i] > 0.001D) || (this.actDat[i] < -0.001D))
/* 243 */         nonzero = i;
/* 244 */     this.activeNum = (nonzero + 1);
/* 245 */     if (this.activeNum >= MAX_PERIOD)
/* 246 */       this.activeNum = (MAX_PERIOD - 1);
/* 247 */     System.out.println("begin plot, active number=".concat(String.valueOf(String.valueOf(this.activeNum))));
/*     */     
/* 249 */     this.acept = line_a;
/* 250 */     this.bcept = line_b;
/* 251 */     this.maxval = this.actDat[0];
/* 252 */     this.minval = this.actDat[0];
/* 253 */     for (i = 0; i < this.activeNum; i++) {
/* 254 */       if (actual[i] > this.maxval)
/* 255 */         this.maxval = actual[i];
/* 256 */       if (actual[i] < this.minval)
/* 257 */         this.minval = actual[i];
/*     */     }
/* 259 */     if (this.LineType == IORGForecastPane.EXP_SMOOTH) {
/* 260 */       for (i = 1; i < this.activeNum + 1; i++) {
/* 261 */         if (fcast[i] > this.maxval)
/* 262 */           this.maxval = fcast[i];
/* 263 */         if (fcast[i] < this.minval)
/* 264 */           this.minval = fcast[i];
/*     */       }
/*     */     }
/* 267 */     if (this.LineType == IORGForecastPane.TREND_ADJUST_SMOOTH) {
/* 268 */       for (i = 4; i < this.activeNum + 1; i++) {
/* 269 */         if (fcast[i] > this.maxval)
/* 270 */           this.maxval = fcast[i];
/* 271 */         if (fcast[i] < this.minval)
/* 272 */           this.minval = fcast[i];
/*     */       }
/*     */     }
/* 275 */     if (this.LineType == IORGForecastPane.LINEAR_TREND) {
/* 276 */       if (line_a + line_b < this.minval)
/* 277 */         this.minval = (line_a + line_b);
/* 278 */       if (line_a + line_b * MAX_PERIOD > this.maxval)
/* 279 */         this.maxval = (line_a + line_b * MAX_PERIOD);
/* 280 */     } else if (this.LineType != IORGForecastPane.LAST_VALUE) {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 285 */     this.actPoints = new Vector();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resetPlot()
/*     */   {
/* 297 */     this.isReady = false;
/* 298 */     this.LineType = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void adjustSlope(float a, float b)
/*     */   {
/* 308 */     this.acept = a;
/* 309 */     this.bcept = b;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void drawZLine(Graphics g)
/*     */   {
/* 317 */     int forepos = 0;
/* 318 */     Point actCurPt = null;Point actLastPt = null;Point foreCurPt = null;Point foreLastPt = null;
/*     */     
/* 320 */     this.actPoints.removeAllElements();
/* 321 */     for (int i = 0; i < this.activeNum + 1; i++)
/*     */     {
/* 323 */       int actpos = (int)((this.actDat[i] - this.ystart) * this.Zoom);
/* 324 */       actCurPt = new Point(MARGIN + (int)(i * (WIDTH - MARGIN) * 1.0D / MAX_PERIOD), WIDTH - actpos);
/* 325 */       drawPoint(g, actCurPt, Color.blue);
/* 326 */       this.actPoints.addElement(actCurPt);
/* 327 */       if ((actLastPt != null) && (i < this.activeNum)) {
/* 328 */         g.setColor(Color.blue);
/* 329 */         g.drawLine(actLastPt.x, actLastPt.y, actCurPt.x, actCurPt.y);
/*     */       }
/* 331 */       actLastPt = actCurPt;
/* 332 */       actCurPt = null;
/*     */       
/* 334 */       if (this.LineType == IORGForecastPane.EXP_SMOOTH)
/*     */       {
/* 336 */         forepos = (int)((this.foreDat[i] - this.ystart) * this.Zoom);
/* 337 */         foreCurPt = new Point(MARGIN + (int)(i * (WIDTH - MARGIN) * 1.0D / MAX_PERIOD), WIDTH - forepos);
/*     */         
/* 339 */         drawPoint(g, foreCurPt, Color.red);
/*     */         
/* 341 */         if (foreLastPt != null) {
/* 342 */           g.setColor(Color.red);
/* 343 */           g.drawLine(foreLastPt.x, foreLastPt.y, foreCurPt.x, foreCurPt.y);
/*     */         }
/*     */       }
/* 346 */       else if ((this.LineType == IORGForecastPane.TREND_ADJUST_SMOOTH) && (i <= this.activeNum)) {
/* 347 */         forepos = (int)((this.foreDat[i] - this.ystart) * this.Zoom);
/* 348 */         foreCurPt = new Point(MARGIN + (int)(i * (WIDTH - MARGIN) * 1.0D / MAX_PERIOD), WIDTH - forepos);
/*     */         
/* 350 */         drawPoint(g, foreCurPt, Color.red);
/*     */         
/* 352 */         if (foreLastPt != null) {
/* 353 */           g.setColor(Color.red);
/* 354 */           g.drawLine(foreLastPt.x, foreLastPt.y, foreCurPt.x, foreCurPt.y);
/*     */         }
/*     */       }
/*     */       
/* 358 */       foreLastPt = foreCurPt;
/* 359 */       foreCurPt = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void drawTrend(Graphics g)
/*     */   {
/* 371 */     Point actCurPt = null;Point actLastPt = null;
/*     */     
/* 373 */     this.actPoints.removeAllElements();
/* 374 */     for (int i = 0; i < this.activeNum; i++)
/*     */     {
/* 376 */       int actpos = (int)((this.actDat[i] - this.ystart) * this.Zoom);
/* 377 */       actCurPt = new Point(MARGIN + i * (WIDTH - MARGIN) / MAX_PERIOD, WIDTH - actpos);
/* 378 */       drawPoint(g, actCurPt, Color.blue);
/* 379 */       this.actPoints.addElement(actCurPt);
/* 380 */       if (actLastPt != null) {
/* 381 */         g.setColor(Color.blue);
/* 382 */         g.drawLine(actLastPt.x, actLastPt.y, actCurPt.x, actCurPt.y);
/*     */       }
/* 384 */       actLastPt = actCurPt;
/* 385 */       actCurPt = null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 390 */     int stpos = (int)((this.acept + this.bcept - this.ystart) * this.Zoom);
/* 391 */     int endpos = (int)((this.acept + this.bcept * MAX_PERIOD - this.ystart) * this.Zoom);
/* 392 */     g.setColor(Color.red);
/* 393 */     g.drawLine(MARGIN, WIDTH - stpos, WIDTH - MARGIN, WIDTH - endpos);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void drawRegression(Graphics g)
/*     */   {
/* 402 */     this.actPoints.removeAllElements();
/* 403 */     for (int i = 0; i < this.activeNum; i++)
/*     */     {
/* 405 */       int ypos = (int)((this.actDat[i] - this.ystart) * this.Zoom);
/* 406 */       int xpos = (int)(this.xDat[i] * this.Xzoom);
/* 407 */       Point actCurPt = new Point(MARGIN + xpos, WIDTH - ypos);
/* 408 */       drawPoint(g, actCurPt, Color.blue);
/* 409 */       this.actPoints.addElement(actCurPt);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 414 */     int xstpos = (int)(this.predxmin * this.Xzoom);
/* 415 */     int xendpos = (int)(this.predxmax * this.Xzoom);
/* 416 */     int ystpos = (int)((this.acept + this.bcept * this.predxmin - this.ystart) * this.Zoom);
/* 417 */     int yendpos = (int)((this.acept + this.bcept * this.predxmax - this.ystart) * this.Zoom);
/* 418 */     g.setColor(Color.red);
/* 419 */     g.drawLine(MARGIN + xstpos, WIDTH - ystpos, MARGIN + xendpos, WIDTH - yendpos);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void drawPoint(Graphics g, Point p, Color c)
/*     */   {
/* 429 */     g.setColor(c);
/* 430 */     g.fillOval(p.x - 3, p.y - 3, 6, 6);
/*     */     
/* 432 */     g.drawOval(p.x - 3, p.y - 3, 6, 6);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mouseMoved(MouseEvent e)
/*     */   {
/* 440 */     if ((this.isReady == false) || (this.actPoints == null)) {
/* 441 */       return;
/*     */     }
/*     */     
/* 444 */     int px = e.getX();
/* 445 */     int py = e.getY();
/* 446 */     setCursor(Cursor.getPredefinedCursor(0));
/* 447 */     for (int i = 0; i < this.actPoints.size(); i++) {
/* 448 */       Point onePt = (Point)this.actPoints.elementAt(i);
/* 449 */       if ((px < onePt.x + 3) && (px > onePt.x - 3) && (py < onePt.y + 3) && (py > onePt.y - 3)) {
/* 450 */         this.selPoint = onePt;
/* 451 */         this.selIndex = i;
/*     */         
/* 453 */         setCursor(Cursor.getPredefinedCursor(8));
/* 454 */         this.mainApp.leaveTable();
/* 455 */         return;
/*     */       }
/*     */     }
/*     */     
/* 459 */     this.selIndex = -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mouseDragged(MouseEvent e)
/*     */   {
/* 470 */     if (this.selIndex < 0)
/* 471 */       return;
/* 472 */     int top = (int)((this.yend - this.ystart) * this.Zoom);
/* 473 */     int px = e.getX();
/* 474 */     int py = e.getY();
/* 475 */     if ((px < this.selPoint.x + 10) && (px > this.selPoint.x - 10) && (py < WIDTH) && (py > MARGIN)) {
/* 476 */       this.actDat[this.selIndex] = (this.ystart + (float)(1.0D * (WIDTH - py) / this.Zoom));
/*     */       
/* 478 */       this.mainApp.changeDat(this.selIndex, this.actDat[this.selIndex]);
/* 479 */       this.mainApp.savePrintInfo();
/* 480 */       repaint();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORGForecastGraphCanvas.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */