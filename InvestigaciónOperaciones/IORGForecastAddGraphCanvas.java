/*     */ import java.awt.Color;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class IORGForecastAddGraphCanvas extends JPanel implements MouseMotionListener
/*     */ {
/*  16 */   private static int CANVASIZE = 325;
/*  17 */   private static int MARGIN = 20;
/*  18 */   private static int WIDTH = 310;
/*  19 */   private static int MAX_PERIOD = 20;
/*     */   
/*     */   private Image bufzone;
/*     */   private int Xzoom;
/*     */   private float Zoom;
/*     */   private IORGForecastPane mainApp;
/*     */   private int ystart;
/*     */   private int yend;
/*     */   private Vector actPoints;
/*  28 */   private Point selPoint = null;
/*  29 */   private int selIndex = -1;
/*     */   private float[] actDat;
/*     */   private float[] foreDat;
/*     */   private float[] xDat;
/*     */   private float acept;
/*     */   private float bcept;
/*     */   private float maxval;
/*     */   private float minval;
/*  37 */   private float predxmin; private float predxmax; private int LineType = 0;
/*     */   
/*  39 */   private boolean isReady = false;
/*  40 */   private DecimalFormat decfm = new DecimalFormat();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORGForecastAddGraphCanvas(IORGForecastPane app)
/*     */   {
/*  48 */     this.mainApp = app;
/*  49 */     this.Zoom = 1.0F;
/*  50 */     this.Xzoom = 1;
/*  51 */     this.decfm.setGroupingUsed(false);
/*  52 */     this.decfm.setMaximumFractionDigits(2);
/*  53 */     addMouseMotionListener(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update(Graphics g)
/*     */   {
/*  61 */     paint(g);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/*  69 */     if (this.bufzone == null) {
/*  70 */       this.bufzone = createImage(getSize().width, getSize().height);
/*     */     }
/*  72 */     Graphics bfg = this.bufzone.getGraphics();
/*  73 */     bfg.setClip(0, 0, getSize().width, getSize().height);
/*  74 */     super.paint(bfg);
/*     */     
/*     */ 
/*  77 */     drawAxis(bfg);
/*     */     
/*  79 */     if (this.isReady)
/*     */     {
/*  81 */       drawZLine(bfg);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  87 */     Color oldc = bfg.getColor();
/*  88 */     bfg.setColor(new Color(212, 212, 212));
/*  89 */     bfg.drawRect(0, 0, CANVASIZE - 1, CANVASIZE - 1);
/*  90 */     bfg.drawRect(1, 1, CANVASIZE, CANVASIZE);
/*  91 */     bfg.setColor(oldc);
/*     */     
/*  93 */     g.drawImage(this.bufzone, 0, 0, null);
/*  94 */     bfg.dispose();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void drawAxis(Graphics g)
/*     */   {
/* 104 */     g.setColor(Color.white);
/* 105 */     g.fillRect(0, 0, CANVASIZE, CANVASIZE);
/* 106 */     g.setColor(Color.black);
/*     */     
/* 108 */     g.drawLine(MARGIN, WIDTH, WIDTH, WIDTH);
/* 109 */     g.drawLine(MARGIN, MARGIN, MARGIN, WIDTH);
/* 110 */     g.drawLine(MARGIN, MARGIN, WIDTH, MARGIN);
/* 111 */     g.drawLine(WIDTH, WIDTH, WIDTH, MARGIN);
/* 112 */     if (this.isReady) {
/* 113 */       Font origFont = g.getFont();
/* 114 */       g.setFont(new Font(origFont.getName(), origFont.getStyle(), 9));
/*     */       
/* 116 */       drawUnit(g);
/* 117 */       g.setFont(origFont);
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
/* 128 */     if (this.LineType != IORGForecastPane.LINEAR_REGRESSION) {
/* 129 */       int xstep = (WIDTH - MARGIN) / MAX_PERIOD;
/* 130 */       for (int i = 0; i < MAX_PERIOD; i++) {
/* 131 */         g.drawLine(MARGIN + i * xstep, WIDTH - 1, MARGIN + i * xstep, WIDTH + 1);
/* 132 */         g.drawString(String.valueOf(String.valueOf(new StringBuffer("").append(i + 1))), MARGIN + i * xstep - 3, WIDTH + 12);
/*     */       }
/*     */     }
/*     */     
/* 136 */     this.predxmax = this.xDat[0];
/* 137 */     this.predxmin = this.xDat[0];
/* 138 */     for (int i = 0; i < this.xDat.length; i++) {
/* 139 */       if (this.xDat[i] < 0.001D)
/*     */         break;
/* 141 */       if (this.xDat[i] > this.predxmax)
/* 142 */         this.predxmax = this.xDat[i];
/* 143 */       if (this.xDat[i] < this.predxmin)
/* 144 */         this.predxmin = this.xDat[i];
/*     */     }
/* 146 */     if (this.predxmax < 2)
/* 147 */       return;
/* 148 */     int xend = (int)(this.predxmax + 1.5D);
/* 149 */     this.Xzoom = ((WIDTH - MARGIN) / xend);
/* 150 */     for (i = 0; i <= xend; i++) {
/* 151 */       g.drawLine(MARGIN + i * this.Xzoom, WIDTH - 1, MARGIN + i * this.Xzoom, WIDTH + 1);
/* 152 */       g.drawString("".concat(String.valueOf(String.valueOf(i))), MARGIN + i * this.Xzoom - 3, WIDTH + 12);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 160 */     this.yend = ((int)(this.maxval + (this.maxval - this.minval) * 0.2D));
/* 161 */     this.ystart = ((int)(this.minval - (this.maxval - this.minval) * 0.2D));
/* 162 */     if (this.ystart < 0) {
/* 163 */       this.ystart = 0;
/*     */     }
/* 165 */     if (this.yend - this.ystart < 2) return;
/*     */     int ystep;
/* 167 */     int ystep; if (this.yend - this.ystart < 15) {
/* 168 */       ystep = 1; } else { int ystep;
/* 169 */       if (this.yend - this.ystart < 30) {
/* 170 */         ystep = 2; } else { int ystep;
/* 171 */         if (this.yend - this.ystart < 60) {
/* 172 */           ystep = 5; } else { int ystep;
/* 173 */           if (this.yend - this.ystart < 120) {
/* 174 */             ystep = 10; } else { int ystep;
/* 175 */             if (this.yend - this.ystart < 180) {
/* 176 */               ystep = 20; } else { int ystep;
/* 177 */               if (this.yend - this.ystart < 320) {
/* 178 */                 ystep = 40;
/*     */               } else
/* 180 */                 ystep = 50;
/*     */             }
/*     */           } } } }
/* 183 */     this.ystart = (this.ystart / ystep * ystep);
/* 184 */     this.yend = ((this.yend / ystep + 1) * ystep);
/*     */     
/*     */ 
/* 187 */     this.Zoom = ((float)(1.0D * (WIDTH - MARGIN) / (this.yend - this.ystart)));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 193 */     int niche = 0;
/* 194 */     int pxl = 0;
/* 195 */     while (pxl <= WIDTH - MARGIN) {
/* 196 */       g.drawLine(MARGIN - 1, WIDTH - pxl, MARGIN + 1, WIDTH - pxl);
/* 197 */       g.drawString(String.valueOf(String.valueOf(new StringBuffer("").append(this.ystart + niche))), 6, WIDTH - pxl + 5);
/* 198 */       niche += ystep;
/* 199 */       pxl += (int)(ystep * this.Zoom);
/*     */     }
/*     */     
/* 202 */     Font oldFont = g.getFont();
/* 203 */     g.setFont(new Font("Arial", 0, 10));
/*     */     
/* 205 */     g.setColor(Color.blue);
/* 206 */     g.drawLine(190, 10, 210, 10);
/* 207 */     g.setColor(Color.black);
/* 208 */     g.drawString("Data", 215, 14);
/* 209 */     g.setColor(Color.red);
/* 210 */     g.drawLine(250, 10, 270, 10);
/* 211 */     g.setColor(Color.black);
/* 212 */     g.drawString("Forecast", 275, 14);
/*     */     
/* 214 */     g.setFont(oldFont);
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
/*     */   public void beginPlot(float[] actual, float[] fcast, float line_a, float line_b, int method)
/*     */   {
/* 231 */     this.isReady = true;
/* 232 */     this.LineType = method;
/*     */     
/* 234 */     this.actDat = actual;
/* 235 */     if (method != IORGForecastPane.LINEAR_REGRESSION) {
/* 236 */       this.foreDat = fcast;
/*     */     } else {
/* 238 */       this.xDat = fcast;
/*     */     }
/* 240 */     this.acept = line_a;
/* 241 */     this.bcept = line_b;
/* 242 */     this.maxval = this.actDat[0];
/* 243 */     this.minval = this.actDat[0];
/*     */     
/*     */ 
/* 246 */     for (int i = 0; i < actual.length; i++) {
/* 247 */       if (actual[i] > this.maxval)
/* 248 */         this.maxval = actual[i];
/* 249 */       if ((actual[i] < this.minval) && (actual[i] > 0.001D))
/* 250 */         this.minval = actual[i];
/*     */     }
/* 252 */     if (this.LineType == IORGForecastPane.TREND_ADJUST_SMOOTH) {
/* 253 */       for (i = 0; i < fcast.length; i++) {
/* 254 */         if (fcast[i] > this.maxval)
/* 255 */           this.maxval = fcast[i];
/* 256 */         if ((fcast[i] < this.minval) && (fcast[i] > 0.001D))
/* 257 */           this.minval = fcast[i];
/*     */       }
/*     */     }
/* 260 */     if (this.LineType == IORGForecastPane.LINEAR_TREND) {
/* 261 */       if (line_a + line_b < this.minval)
/* 262 */         this.minval = (line_a + line_b);
/* 263 */       if (line_a + line_b * MAX_PERIOD > this.maxval) {
/* 264 */         this.maxval = (line_a + line_b * MAX_PERIOD);
/*     */       }
/*     */     }
/* 267 */     this.actPoints = new Vector();
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
/* 279 */     this.isReady = false;
/* 280 */     this.LineType = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void adjustSlope(float a, float b)
/*     */   {
/* 290 */     this.acept = a;
/* 291 */     this.bcept = b;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void drawZLine(Graphics g)
/*     */   {
/* 300 */     Point actCurPt = null;Point actLastPt = null;Point foreCurPt = null;Point foreLastPt = null;
/*     */     
/* 302 */     this.actPoints.removeAllElements();
/* 303 */     for (int i = 0; i < this.actDat.length; i++) {
/* 304 */       if (this.actDat[i] > 0.001D) {
/* 305 */         int actpos = (int)((this.actDat[i] - this.ystart) * this.Zoom);
/* 306 */         actCurPt = new Point(MARGIN + i * (WIDTH - MARGIN) / MAX_PERIOD, WIDTH - actpos);
/* 307 */         drawPoint(g, actCurPt, Color.blue);
/* 308 */         this.actPoints.addElement(actCurPt);
/* 309 */         if (actLastPt != null) {
/* 310 */           g.setColor(Color.blue);
/* 311 */           g.drawLine(actLastPt.x, actLastPt.y, actCurPt.x, actCurPt.y);
/*     */         }
/* 313 */         actLastPt = actCurPt;
/* 314 */         actCurPt = null;
/*     */       }
/* 316 */       if (this.foreDat[i] > 0.001D)
/*     */       {
/* 318 */         int forepos = (int)((this.foreDat[i] - this.ystart) * this.Zoom);
/* 319 */         foreCurPt = new Point(MARGIN + i * (WIDTH - MARGIN) / MAX_PERIOD, WIDTH - forepos);
/* 320 */         drawPoint(g, foreCurPt, Color.red);
/*     */         
/* 322 */         if (foreLastPt != null) {
/* 323 */           g.setColor(Color.red);
/* 324 */           g.drawLine(foreLastPt.x, foreLastPt.y, foreCurPt.x, foreCurPt.y);
/*     */         }
/* 326 */         foreLastPt = foreCurPt;
/* 327 */         foreCurPt = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void drawTrend(Graphics g)
/*     */   {
/* 339 */     Point actCurPt = null;Point actLastPt = null;
/*     */     
/* 341 */     this.actPoints.removeAllElements();
/* 342 */     for (int i = 0; i < this.actDat.length; i++) {
/* 343 */       if (this.actDat[i] > 0.001D) {
/* 344 */         int actpos = (int)((this.actDat[i] - this.ystart) * this.Zoom);
/* 345 */         actCurPt = new Point(MARGIN + i * (WIDTH - MARGIN) / MAX_PERIOD, WIDTH - actpos);
/* 346 */         drawPoint(g, actCurPt, Color.blue);
/* 347 */         this.actPoints.addElement(actCurPt);
/* 348 */         if (actLastPt != null) {
/* 349 */           g.setColor(Color.blue);
/* 350 */           g.drawLine(actLastPt.x, actLastPt.y, actCurPt.x, actCurPt.y);
/*     */         }
/* 352 */         actLastPt = actCurPt;
/* 353 */         actCurPt = null;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 358 */     int stpos = (int)((this.acept + this.bcept - this.ystart) * this.Zoom);
/* 359 */     int endpos = (int)((this.acept + this.bcept * MAX_PERIOD - this.ystart) * this.Zoom);
/* 360 */     g.setColor(Color.red);
/* 361 */     g.drawLine(MARGIN, WIDTH - stpos, WIDTH - MARGIN, WIDTH - endpos);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void drawRegression(Graphics g)
/*     */   {
/* 373 */     this.actPoints.removeAllElements();
/* 374 */     for (int i = 0; i < this.xDat.length; i++) {
/* 375 */       if (this.xDat[i] > 0.001D) {
/* 376 */         int ypos = (int)((this.actDat[i] - this.ystart) * this.Zoom);
/* 377 */         int xpos = (int)(this.xDat[i] * this.Xzoom);
/* 378 */         Point actCurPt = new Point(MARGIN + xpos, WIDTH - ypos);
/* 379 */         drawPoint(g, actCurPt, Color.blue);
/* 380 */         this.actPoints.addElement(actCurPt);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 385 */     int xstpos = (int)(this.predxmin * this.Xzoom);
/* 386 */     int xendpos = (int)(this.predxmax * this.Xzoom);
/* 387 */     int ystpos = (int)((this.acept + this.bcept * this.predxmin - this.ystart) * this.Zoom);
/* 388 */     int yendpos = (int)((this.acept + this.bcept * this.predxmax - this.ystart) * this.Zoom);
/* 389 */     g.setColor(Color.red);
/* 390 */     g.drawLine(MARGIN + xstpos, WIDTH - ystpos, MARGIN + xendpos, WIDTH - yendpos);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void drawPoint(Graphics g, Point p, Color c)
/*     */   {
/* 400 */     g.setColor(c);
/* 401 */     g.fillOval(p.x - 3, p.y - 3, 6, 6);
/*     */     
/* 403 */     g.drawOval(p.x - 3, p.y - 3, 6, 6);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mouseMoved(MouseEvent e)
/*     */   {
/* 412 */     if ((this.isReady == false) || (this.actPoints == null)) {
/* 413 */       return;
/*     */     }
/*     */     
/* 416 */     int px = e.getX();
/* 417 */     int py = e.getY();
/* 418 */     setCursor(Cursor.getPredefinedCursor(0));
/* 419 */     for (int i = 0; i < this.actPoints.size(); i++) {
/* 420 */       Point onePt = (Point)this.actPoints.elementAt(i);
/* 421 */       if ((px < onePt.x + 3) && (px > onePt.x - 3) && (py < onePt.y + 3) && (py > onePt.y - 3)) {
/* 422 */         this.selPoint = onePt;
/* 423 */         this.selIndex = i;
/*     */         
/* 425 */         setCursor(Cursor.getPredefinedCursor(8));
/* 426 */         this.mainApp.leaveTable();
/* 427 */         return;
/*     */       }
/*     */     }
/* 430 */     this.selIndex = -1;
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
/* 441 */     if (this.selIndex < 0)
/* 442 */       return;
/* 443 */     int top = (int)((this.yend - this.ystart) * this.Zoom);
/* 444 */     int px = e.getX();
/* 445 */     int py = e.getY();
/* 446 */     if ((px < this.selPoint.x + 10) && (px > this.selPoint.x - 10) && (py < WIDTH) && (py > MARGIN)) {
/* 447 */       this.actDat[this.selIndex] = (this.ystart + (float)(1.0D * (WIDTH - py) / this.Zoom));
/*     */       
/*     */ 
/* 450 */       this.mainApp.changeDat(this.selIndex, this.actDat[this.selIndex]);
/* 451 */       this.mainApp.savePrintInfo();
/* 452 */       repaint();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORGForecastAddGraphCanvas.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */