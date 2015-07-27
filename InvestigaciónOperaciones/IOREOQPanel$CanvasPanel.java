/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.AdjustmentEvent;
/*     */ import java.awt.event.AdjustmentListener;
/*     */ import java.text.DecimalFormat;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollBar;
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
/*     */ public class IOREOQPanel$CanvasPanel
/*     */   extends JPanel
/*     */   implements AdjustmentListener, ActionListener
/*     */ {
/*     */   private final IOREOQPanel this$0;
/*     */   
/*     */   IOREOQPanel$CanvasPanel(IOREOQPanel this$0)
/*     */   {
/* 223 */     this.this$0 = this$0;
/* 224 */     setSize(600, 380);
/* 225 */     init();
/*     */   }
/*     */   
/*     */   public void init() {
/*     */     try {
/* 230 */       jbInit();
/*     */     }
/*     */     catch (Exception e) {
/* 233 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private void jbInit() throws Exception
/*     */   {
/* 239 */     this.this$0.f = new Font("Arial", 1, 12);
/* 240 */     this.this$0.f1 = new Font("Arial", 0, 12);
/* 241 */     IOREOQPanel.access$0(this.this$0, new JScrollBar(0, 9, 1, 2, 21));
/* 242 */     IOREOQPanel.access$1(this.this$0, new JScrollBar(0, 36, 1, 20, 41));
/* 243 */     IOREOQPanel.access$2(this.this$0, new JScrollBar(0, 15, 1, 4, 21));
/* 244 */     IOREOQPanel.access$4(this.this$0).addAdjustmentListener(this);
/* 245 */     IOREOQPanel.access$6(this.this$0).addAdjustmentListener(this);
/* 246 */     IOREOQPanel.access$7(this.this$0).addAdjustmentListener(this);
/*     */     
/* 248 */     IOREOQPanel.access$8(this.this$0).addActionListener(this);
/*     */   }
/*     */   
/* 251 */   public void paint(Graphics g) { if (this.this$0.offscreen == null)
/* 252 */       this.this$0.offscreen = createImage(getSize().width, getSize().height);
/* 253 */     this.this$0.gS2 = this.this$0.offscreen.getGraphics();
/* 254 */     this.this$0.gS2.clearRect(0, 0, getSize().width, getSize().height);
/* 255 */     this.this$0.gS2.setColor(Color.black);
/* 256 */     this.this$0.gS2.draw3DRect(15, 15, 597, 384, true);
/*     */     
/* 258 */     this.this$0.gS2.drawLine(110, IOREOQPanel.access$9(this.this$0) - 20, 110 + 10 * IOREOQPanel.access$10(this.this$0) + 10, IOREOQPanel.access$9(this.this$0) - 20);
/*     */     
/* 260 */     for (int i = 0; i < 11; i++) {
/* 261 */       this.this$0.gS2.drawLine(110 + IOREOQPanel.access$10(this.this$0) * i, IOREOQPanel.access$9(this.this$0) - 20 - 5, 110 + IOREOQPanel.access$10(this.this$0) * i, IOREOQPanel.access$9(this.this$0) - 20);
/* 262 */       this.this$0.gS2.drawString("".concat(String.valueOf(String.valueOf(i * IOREOQPanel.access$11(this.this$0)))), 110 + IOREOQPanel.access$10(this.this$0) * i - 5, IOREOQPanel.access$9(this.this$0) - 20 + 15);
/*     */     }
/* 264 */     this.this$0.gS2.setFont(this.this$0.f);
/* 265 */     this.this$0.gS2.drawString("Quantity ", 110 + IOREOQPanel.access$10(this.this$0) * 10 + 25, IOREOQPanel.access$9(this.this$0) - 20 + 10);
/* 266 */     this.this$0.gS2.setFont(this.this$0.f1);
/*     */     
/*     */ 
/* 269 */     this.this$0.gS2.drawLine(110, IOREOQPanel.access$12(this.this$0), 110, IOREOQPanel.access$9(this.this$0) - 20);
/*     */     
/* 271 */     for (int i = 1; i < 11; i++) {
/* 272 */       this.this$0.gS2.drawLine(110, IOREOQPanel.access$9(this.this$0) - 20 - IOREOQPanel.access$13(this.this$0) * i, 115, IOREOQPanel.access$9(this.this$0) - 20 - IOREOQPanel.access$13(this.this$0) * i);
/* 273 */       this.this$0.gS2.drawString("".concat(String.valueOf(String.valueOf(i * IOREOQPanel.access$14(this.this$0)))), 80, IOREOQPanel.access$9(this.this$0) - 20 - IOREOQPanel.access$13(this.this$0) * i + 3);
/*     */     }
/*     */     
/* 276 */     this.this$0.gS2.setFont(this.this$0.f);
/* 277 */     this.this$0.gS2.drawString("($)The costs ", 30, 50);
/* 278 */     this.this$0.gS2.setFont(this.this$0.f1);
/*     */     
/* 280 */     this.this$0.gS2.setFont(this.this$0.f);
/* 281 */     this.this$0.gS2.drawString("TC = h*Q/2 + K*d/Q", 80, IOREOQPanel.access$9(this.this$0) + 40);
/* 282 */     this.this$0.gS2.drawString("HC = h*Q/2", 280, IOREOQPanel.access$9(this.this$0) + 40);
/* 283 */     this.this$0.gS2.drawString("OC = K*d/Q", 430, IOREOQPanel.access$9(this.this$0) + 40);
/*     */     
/* 285 */     drawnonlinear();
/* 286 */     g.drawImage(this.this$0.offscreen, 0, 0, this);
/* 287 */     this.this$0.gS2.dispose();
/*     */   }
/*     */   
/*     */ 
/*     */   private void drawnonlinear()
/*     */   {
/* 293 */     int qinit = 2;
/* 294 */     double ocinit = this.this$0.D * this.this$0.S * 1.0D / qinit;
/* 295 */     double tcinit = ocinit + 0.5D * this.this$0.H * qinit;
/*     */     
/* 297 */     Point ocprv = new Point(qinit * IOREOQPanel.access$10(this.this$0) / IOREOQPanel.access$11(this.this$0), IOREOQPanel.access$9(this.this$0) - 20 - (int)ocinit * IOREOQPanel.access$13(this.this$0) / IOREOQPanel.access$14(this.this$0));
/* 298 */     Point tcprv = new Point(qinit * IOREOQPanel.access$10(this.this$0) / IOREOQPanel.access$11(this.this$0), IOREOQPanel.access$9(this.this$0) - 20 - (int)tcinit * IOREOQPanel.access$13(this.this$0) / IOREOQPanel.access$14(this.this$0));
/*     */     
/*     */ 
/* 301 */     double hcend = 0.5D * this.this$0.H * 10 * IOREOQPanel.access$11(this.this$0);
/* 302 */     this.this$0.gS2.drawLine(110, IOREOQPanel.access$9(this.this$0) - 20, 110 + 10 * IOREOQPanel.access$10(this.this$0), IOREOQPanel.access$9(this.this$0) - 20 - (int)hcend * IOREOQPanel.access$13(this.this$0) / IOREOQPanel.access$14(this.this$0));
/* 303 */     this.this$0.gS2.drawString("H C", 110 + 10 * IOREOQPanel.access$10(this.this$0) - 20, IOREOQPanel.access$9(this.this$0) - 20 - (int)hcend * IOREOQPanel.access$13(this.this$0) / IOREOQPanel.access$14(this.this$0) - 5);
/*     */     
/* 305 */     boolean find = false;
/* 306 */     for (int q = 2; q <= 10 * IOREOQPanel.access$11(this.this$0); q++) {
/* 307 */       double ocval = this.this$0.D * this.this$0.S * 1.0D / q;
/* 308 */       double tcval = ocval + 0.5D * this.this$0.H * q;
/* 309 */       int x = q * IOREOQPanel.access$10(this.this$0) / IOREOQPanel.access$11(this.this$0);
/* 310 */       int yoc = IOREOQPanel.access$9(this.this$0) - 20 - (int)ocval * IOREOQPanel.access$13(this.this$0) / IOREOQPanel.access$14(this.this$0);
/* 311 */       int ytc = IOREOQPanel.access$9(this.this$0) - 20 - (int)tcval * IOREOQPanel.access$13(this.this$0) / IOREOQPanel.access$14(this.this$0);
/*     */       
/* 313 */       if ((ocprv.y > IOREOQPanel.access$12(this.this$0)) && (tcprv.y > IOREOQPanel.access$12(this.this$0)))
/*     */       {
/* 315 */         this.this$0.gS2.setColor(Color.red);
/* 316 */         this.this$0.gS2.drawLine(ocprv.x + 110, ocprv.y, x + 110, yoc);
/*     */         
/* 318 */         this.this$0.gS2.setColor(Color.blue);
/* 319 */         this.this$0.gS2.drawLine(tcprv.x + 110, tcprv.y, x + 110, ytc);
/*     */       }
/*     */       
/* 322 */       tcprv.x = x;
/* 323 */       tcprv.y = ytc;
/* 324 */       ocprv.x = x;
/* 325 */       ocprv.y = yoc;
/*     */     }
/*     */     
/* 328 */     this.this$0.Qstar = Math.round((float)Math.sqrt(2.0D * this.this$0.D * this.this$0.S / this.this$0.H));
/* 329 */     this.this$0.Qy = Math.round((float)(this.this$0.Qstar * 0.5D * this.this$0.H + this.this$0.D * this.this$0.S / this.this$0.Qstar));
/* 330 */     int optmx = 110 + this.this$0.Qstar * IOREOQPanel.access$10(this.this$0) / IOREOQPanel.access$11(this.this$0);
/* 331 */     int optmy = IOREOQPanel.access$9(this.this$0) - 20 - this.this$0.Qy * IOREOQPanel.access$13(this.this$0) / IOREOQPanel.access$14(this.this$0);
/*     */     
/* 333 */     this.this$0.gS2.clearRect(50, optmy - 10, 50, 15);
/*     */     
/* 335 */     this.this$0.gS2.setColor(Color.black);
/*     */     
/* 337 */     for (int temy = optmy; temy < IOREOQPanel.access$9(this.this$0) - 20; temy += 6) {
/* 338 */       this.this$0.gS2.drawLine(optmx, temy, optmx, temy + 3);
/*     */     }
/* 340 */     for (int temx = optmx; temx > 110; temx -= 6) {
/* 341 */       this.this$0.gS2.drawLine(temx, optmy, temx - 3, optmy);
/*     */     }
/* 343 */     this.this$0.gS2.setColor(Color.black);
/* 344 */     this.this$0.gS2.drawString("Q", 53, optmy);
/* 345 */     this.this$0.gS2.drawString("Q", optmx - 17, IOREOQPanel.access$9(this.this$0) - 20 + 30);
/* 346 */     this.this$0.gS2.setFont(new Font("", 1, 10));
/* 347 */     this.this$0.gS2.drawString("0", optmx - 8, IOREOQPanel.access$9(this.this$0) - 20 + 30 + 2);
/* 348 */     this.this$0.gS2.drawString("y", 60, optmy + 2);
/*     */     
/* 350 */     this.this$0.gS2.setFont(new Font("", 1, 12));
/* 351 */     this.this$0.gS2.drawString("=".concat(String.valueOf(String.valueOf(this.this$0.Qstar))), optmx, IOREOQPanel.access$9(this.this$0) - 20 + 30 + 2);
/* 352 */     this.this$0.gS2.drawString("=".concat(String.valueOf(String.valueOf(this.this$0.Qy))), 65, optmy + 2);
/*     */     
/* 354 */     this.this$0.gS2.setColor(Color.red);
/* 355 */     this.this$0.gS2.drawString("O C", ocprv.x + 110 - 20, ocprv.y - 5);
/* 356 */     this.this$0.gS2.setColor(Color.blue);
/* 357 */     this.this$0.gS2.drawString("T C", tcprv.x + 110 - 20, tcprv.y - 5);
/*     */   }
/*     */   
/*     */   public void update(Graphics g)
/*     */   {
/* 362 */     paint(g);
/*     */   }
/*     */   
/*     */   public void adjustmentValueChanged(AdjustmentEvent e) {
/* 366 */     JScrollBar src = (JScrollBar)e.getSource();
/*     */     
/* 368 */     if (src == IOREOQPanel.access$4(this.this$0)) {
/* 369 */       int value = IOREOQPanel.access$4(this.this$0).getValue();
/* 370 */       this.this$0.H = (value * 1.0D);
/* 371 */       IOREOQPanel.access$15(this.this$0).setText(" H = ".concat(String.valueOf(String.valueOf(this.this$0.decfm.format(this.this$0.H)))));
/* 372 */       invalidate();
/* 373 */       repaint();
/* 374 */     } else if (src == IOREOQPanel.access$6(this.this$0)) {
/* 375 */       int value = IOREOQPanel.access$6(this.this$0).getValue();
/* 376 */       this.this$0.D = (value * 10.0D);
/* 377 */       IOREOQPanel.access$16(this.this$0).setText(" D = ".concat(String.valueOf(String.valueOf(this.this$0.decfm.format(this.this$0.D)))));
/* 378 */       invalidate();
/* 379 */       repaint();
/*     */     } else {
/* 381 */       int value = IOREOQPanel.access$7(this.this$0).getValue();
/* 382 */       this.this$0.S = (value * 1.0D);
/* 383 */       IOREOQPanel.access$17(this.this$0).setText(" S = ".concat(String.valueOf(String.valueOf(this.this$0.decfm.format(this.this$0.S)))));
/* 384 */       invalidate();
/* 385 */       repaint();
/*     */     }
/* 387 */     this.this$0.savePrintInfo();
/* 388 */     IOREOQPanel.access$18(this.this$0).solver.initEOQPrintInfo(IOREOQPanel.access$18(this.this$0).data);
/*     */   }
/*     */   
/* 391 */   public void actionPerformed(ActionEvent evt) { this.this$0.H = 9.0D;
/* 392 */     IOREOQPanel.access$4(this.this$0).setValue(9);
/* 393 */     IOREOQPanel.access$15(this.this$0).setText(" H = ".concat(String.valueOf(String.valueOf(this.this$0.decfm.format(this.this$0.H)))));
/* 394 */     this.this$0.S = 15.0D;
/* 395 */     IOREOQPanel.access$7(this.this$0).setValue(15);
/* 396 */     IOREOQPanel.access$17(this.this$0).setText(" S = ".concat(String.valueOf(String.valueOf(this.this$0.decfm.format(this.this$0.S)))));
/* 397 */     this.this$0.D = 360.0D;
/* 398 */     IOREOQPanel.access$6(this.this$0).setValue(36);
/* 399 */     IOREOQPanel.access$16(this.this$0).setText(" D = ".concat(String.valueOf(String.valueOf(this.this$0.decfm.format(this.this$0.D)))));
/* 400 */     invalidate();
/* 401 */     repaint();
/* 402 */     this.this$0.savePrintInfo();
/* 403 */     IOREOQPanel.access$18(this.this$0).solver.initEOQPrintInfo(IOREOQPanel.access$18(this.this$0).data);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IOREOQPanel$CanvasPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */