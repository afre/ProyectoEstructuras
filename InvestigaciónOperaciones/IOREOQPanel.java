/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.AdjustmentEvent;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DecimalFormat;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollBar;
/*     */ 
/*     */ public class IOREOQPanel extends IORActionPanel
/*     */ {
/*     */   private IORITProcessController controller;
/*  22 */   private int XSCALE = 10;
/*  23 */   private int YSCALE = 100;
/*     */   
/*  25 */   boolean isStandalone = false;
/*     */   
/*     */   Image offscreen;
/*     */   Graphics gS2;
/*  29 */   private int GRAPHEIGHT = 340;
/*  30 */   final int marginx = 110;
/*  31 */   final int marginy = 20;
/*  32 */   private int XSTEP = 40;
/*  33 */   private int YSTEP = 25;
/*  34 */   private int TOP = 60;
/*     */   
/*  36 */   double H = 9.0D;
/*  37 */   double D = 360.0D;
/*  38 */   double S = 15.0D;
/*     */   
/*     */   int Qstar;
/*     */   int Qy;
/*  42 */   DecimalFormat decfm = new DecimalFormat();
/*     */   Font f;
/*     */   Font f1;
/*  45 */   private JButton reset = new JButton("Reset");
/*     */   private JScrollBar scrolH;
/*     */   private JScrollBar scrolD;
/*     */   private JScrollBar scrolK;
/*  49 */   private JLabel hlab = new JLabel(" h = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.H)))));
/*  50 */   private JLabel dlab = new JLabel(" d = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.D)))));
/*  51 */   private JLabel slab = new JLabel(" K = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.S)))));
/*     */   
/*  53 */   private JPanel locationPane = new JPanel();
/*  54 */   private IOREOQPanel.CanvasPanel canv = new IOREOQPanel.CanvasPanel();
/*  55 */   private JPanel controllerPanel = new JPanel();
/*  56 */   private JPanel scrolHPan = new JPanel();
/*  57 */   private JPanel scrolDPan = new JPanel();
/*  58 */   private JPanel scrolKPan = new JPanel();
/*     */   
/*     */ 
/*  61 */   private JPanel infoPane = new JPanel();
/*     */   
/*     */   public IOREOQPanel(IORITProcessController c) {
/*  64 */     super(c);
/*  65 */     this.controller = c;
/*  66 */     this.controllerPanel.setLayout(new java.awt.FlowLayout());
/*     */     
/*  68 */     this.canv.setSize(600, 300);
/*  69 */     this.locationPane.setLayout(new java.awt.BorderLayout());
/*  70 */     this.locationPane.add(this.canv);
/*     */     
/*  72 */     this.scrolHPan.setLayout(new BoxLayout(this.scrolHPan, 1));
/*  73 */     this.scrolHPan.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
/*  74 */     this.hlab.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
/*  75 */     this.scrolH.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
/*  76 */     this.scrolHPan.add(this.hlab);
/*  77 */     this.scrolHPan.add(this.scrolH);
/*     */     
/*  79 */     this.scrolDPan.setLayout(new BoxLayout(this.scrolDPan, 1));
/*  80 */     this.scrolDPan.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
/*  81 */     this.dlab.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
/*  82 */     this.scrolD.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
/*  83 */     this.scrolDPan.add(this.dlab);
/*  84 */     this.scrolDPan.add(this.scrolD);
/*     */     
/*  86 */     this.scrolKPan.setLayout(new BoxLayout(this.scrolKPan, 1));
/*  87 */     this.scrolKPan.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
/*  88 */     this.slab.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
/*  89 */     this.scrolK.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
/*  90 */     this.scrolKPan.add(this.slab);
/*  91 */     this.scrolKPan.add(this.scrolK);
/*  92 */     this.locationPane.setBorder(null);
/*     */     
/*  94 */     this.controllerPanel.add(this.scrolHPan);
/*  95 */     this.controllerPanel.add(this.scrolDPan);
/*  96 */     this.controllerPanel.add(this.scrolKPan);
/*  97 */     this.controllerPanel.add(this.reset);
/*     */     
/*     */ 
/* 100 */     this.infoPane.setPreferredSize(new Dimension(600, 120));
/*     */     
/*     */ 
/* 103 */     addInfoPanel();
/*     */     
/* 105 */     setLayout(new java.awt.BorderLayout());
/* 106 */     add(this.infoPane, "North");
/* 107 */     add(this.locationPane, "Center");
/* 108 */     add(this.controllerPanel, "South");
/*     */   }
/*     */   
/*     */   public void addInfoPanel() {
/* 112 */     this.infoPane.setLayout(null);
/* 113 */     JLabel infoLab1 = new JLabel("(1). Q: Order quantity each time an order is placed;   (2). Qo: Optimal order quantity");
/* 114 */     JLabel infoLab2 = new JLabel("(3). K: Fixed cost associated with placing each order (excludes the acquisition cost);");
/* 115 */     JLabel infoLab3 = new JLabel("(4). h: Holding cost per unit held per unit time;  d: Demand rate");
/* 116 */     JLabel infoLab4 = new JLabel("(5). TC: The total-cost curve is U-shaped. TC=Q*h/2+d*K/Q ");
/* 117 */     JLabel infoLab5 = new JLabel("(6). HC: Holding cost per unit time is linearly related to order size. HC=Q*h/2 ");
/* 118 */     JLabel infoLab6 = new JLabel("(7). OC: Ordering cost per unit time because of K is inversely and nonlinearly related to order size. ");
/* 119 */     JLabel infoLab7 = new JLabel("(7). OC: OC=d*K/Q");
/*     */     
/* 121 */     infoLab1.setBounds(15, 0, 605, 17);
/* 122 */     infoLab2.setBounds(15, 17, 605, 17);
/* 123 */     infoLab3.setBounds(15, 34, 605, 17);
/* 124 */     infoLab4.setBounds(15, 51, 605, 17);
/* 125 */     infoLab5.setBounds(15, 68, 605, 17);
/* 126 */     infoLab6.setBounds(15, 85, 605, 17);
/* 127 */     infoLab7.setBounds(15, 102, 605, 17);
/*     */     
/* 129 */     this.infoPane.add(infoLab1);
/* 130 */     this.infoPane.add(infoLab2);
/* 131 */     this.infoPane.add(infoLab3);
/* 132 */     this.infoPane.add(infoLab4);
/* 133 */     this.infoPane.add(infoLab5);
/* 134 */     this.infoPane.add(infoLab6);
/* 135 */     this.infoPane.add(infoLab7);
/*     */   }
/*     */   
/*     */ 
/*     */   public void updatePanel() {}
/*     */   
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 147 */     String str = "EOQ Analysis\n\n";
/* 148 */     str = String.valueOf(String.valueOf(str)).concat("You can change the three parameters: H, D and K to see ");
/* 149 */     str = String.valueOf(String.valueOf(str)).concat("the impact of the curve.  ");
/*     */     
/* 151 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 153 */     this.help_content_step.setText(str);
/* 154 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */   protected void initializeCurrentProcedureHelpPanel() {
/* 158 */     String str = "EOQ Analysis\n\n";
/* 159 */     str = String.valueOf(String.valueOf(str)).concat("The EOQ Analysis implemented here is based on the basic EOQ model. This");
/* 160 */     str = String.valueOf(String.valueOf(str)).concat("model used to identify the order size that will minimize the sum of the ");
/* 161 */     str = String.valueOf(String.valueOf(str)).concat("annual costs of holding inventory (HC)\n and ordering cost (OC), excluding ");
/* 162 */     str = String.valueOf(String.valueOf(str)).concat("the acquisition cost. Users can change each parameter to\nsee the effect.");
/*     */     
/* 164 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 165 */     this.help_content_procedure.setText(str);
/* 166 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */   protected void finishProcedure() {}
/*     */   
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/* 174 */     double[] loadData = new double[3];
/*     */     try {
/* 176 */       loadData = (double[])in.readObject();
/*     */     } catch (Exception e) {
/* 178 */       System.out.println("LOAD Fails");
/*     */     }
/* 180 */     this.D = loadData[0];
/* 181 */     this.H = loadData[1];
/* 182 */     this.S = loadData[2];
/*     */     
/* 184 */     this.scrolD.setValue((int)(this.D / 10));
/* 185 */     this.scrolH.setValue((int)this.H);
/* 186 */     this.scrolK.setValue((int)this.S);
/*     */     
/*     */ 
/* 189 */     this.hlab.setText(" H = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.H)))));
/* 190 */     this.dlab.setText(" d = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.D)))));
/* 191 */     this.slab.setText(" K = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.S)))));
/*     */     
/* 193 */     invalidate();
/* 194 */     System.out.println(String.valueOf(String.valueOf(new StringBuffer("LOAD EOQd=").append(this.D).append(" h=").append(this.H).append("  k=").append(this.S))));
/* 195 */     repaint();
/*     */   }
/*     */   
/*     */   public void savePrintInfo() {
/* 199 */     this.controller.data.EOQ_D = this.D;
/* 200 */     this.controller.data.EOQ_H = this.H;
/* 201 */     this.controller.data.EOQ_S = this.S;
/* 202 */     this.controller.data.Qstar = this.Qstar;
/* 203 */     this.controller.data.Qy = this.Qy;
/*     */   }
/*     */   
/*     */   public void SaveFile(ObjectOutputStream out) {
/* 207 */     double[] saveData = new double[3];
/* 208 */     saveData[0] = this.D;
/* 209 */     saveData[1] = this.H;
/* 210 */     saveData[2] = this.S;
/*     */     try {
/* 212 */       out.writeObject(saveData);
/* 213 */       out.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 216 */       e1.printStackTrace();
/* 217 */       System.out.println("Save fails");
/*     */     }
/*     */   }
/*     */   
/*     */   public class CanvasPanel extends JPanel implements java.awt.event.AdjustmentListener, java.awt.event.ActionListener
/*     */   {
/*     */     CanvasPanel() {
/* 224 */       setSize(600, 380);
/* 225 */       init();
/*     */     }
/*     */     
/*     */     public void init() {
/*     */       try {
/* 230 */         jbInit();
/*     */       }
/*     */       catch (Exception e) {
/* 233 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*     */     private void jbInit() throws Exception
/*     */     {
/* 239 */       IOREOQPanel.this.f = new Font("Arial", 1, 12);
/* 240 */       IOREOQPanel.this.f1 = new Font("Arial", 0, 12);
/* 241 */       IOREOQPanel.this.scrolH = new JScrollBar(0, 9, 1, 2, 21);
/* 242 */       IOREOQPanel.this.scrolD = new JScrollBar(0, 36, 1, 20, 41);
/* 243 */       IOREOQPanel.this.scrolK = new JScrollBar(0, 15, 1, 4, 21);
/* 244 */       IOREOQPanel.this.scrolH.addAdjustmentListener(this);
/* 245 */       IOREOQPanel.this.scrolD.addAdjustmentListener(this);
/* 246 */       IOREOQPanel.this.scrolK.addAdjustmentListener(this);
/*     */       
/* 248 */       IOREOQPanel.this.reset.addActionListener(this);
/*     */     }
/*     */     
/* 251 */     public void paint(Graphics g) { if (IOREOQPanel.this.offscreen == null)
/* 252 */         IOREOQPanel.this.offscreen = createImage(getSize().width, getSize().height);
/* 253 */       IOREOQPanel.this.gS2 = IOREOQPanel.this.offscreen.getGraphics();
/* 254 */       IOREOQPanel.this.gS2.clearRect(0, 0, getSize().width, getSize().height);
/* 255 */       IOREOQPanel.this.gS2.setColor(Color.black);
/* 256 */       IOREOQPanel.this.gS2.draw3DRect(15, 15, 597, 384, true);
/*     */       
/* 258 */       IOREOQPanel.this.gS2.drawLine(110, IOREOQPanel.this.GRAPHEIGHT - 20, 110 + 10 * IOREOQPanel.this.XSTEP + 10, IOREOQPanel.this.GRAPHEIGHT - 20);
/*     */       
/* 260 */       for (int i = 0; i < 11; i++) {
/* 261 */         IOREOQPanel.this.gS2.drawLine(110 + IOREOQPanel.this.XSTEP * i, IOREOQPanel.this.GRAPHEIGHT - 20 - 5, 110 + IOREOQPanel.this.XSTEP * i, IOREOQPanel.this.GRAPHEIGHT - 20);
/* 262 */         IOREOQPanel.this.gS2.drawString("".concat(String.valueOf(String.valueOf(i * IOREOQPanel.this.XSCALE))), 110 + IOREOQPanel.this.XSTEP * i - 5, IOREOQPanel.this.GRAPHEIGHT - 20 + 15);
/*     */       }
/* 264 */       IOREOQPanel.this.gS2.setFont(IOREOQPanel.this.f);
/* 265 */       IOREOQPanel.this.gS2.drawString("Quantity ", 110 + IOREOQPanel.this.XSTEP * 10 + 25, IOREOQPanel.this.GRAPHEIGHT - 20 + 10);
/* 266 */       IOREOQPanel.this.gS2.setFont(IOREOQPanel.this.f1);
/*     */       
/*     */ 
/* 269 */       IOREOQPanel.this.gS2.drawLine(110, IOREOQPanel.this.TOP, 110, IOREOQPanel.this.GRAPHEIGHT - 20);
/*     */       
/* 271 */       for (int i = 1; i < 11; i++) {
/* 272 */         IOREOQPanel.this.gS2.drawLine(110, IOREOQPanel.this.GRAPHEIGHT - 20 - IOREOQPanel.this.YSTEP * i, 115, IOREOQPanel.this.GRAPHEIGHT - 20 - IOREOQPanel.this.YSTEP * i);
/* 273 */         IOREOQPanel.this.gS2.drawString("".concat(String.valueOf(String.valueOf(i * IOREOQPanel.this.YSCALE))), 80, IOREOQPanel.this.GRAPHEIGHT - 20 - IOREOQPanel.this.YSTEP * i + 3);
/*     */       }
/*     */       
/* 276 */       IOREOQPanel.this.gS2.setFont(IOREOQPanel.this.f);
/* 277 */       IOREOQPanel.this.gS2.drawString("($)The costs ", 30, 50);
/* 278 */       IOREOQPanel.this.gS2.setFont(IOREOQPanel.this.f1);
/*     */       
/* 280 */       IOREOQPanel.this.gS2.setFont(IOREOQPanel.this.f);
/* 281 */       IOREOQPanel.this.gS2.drawString("TC = h*Q/2 + K*d/Q", 80, IOREOQPanel.this.GRAPHEIGHT + 40);
/* 282 */       IOREOQPanel.this.gS2.drawString("HC = h*Q/2", 280, IOREOQPanel.this.GRAPHEIGHT + 40);
/* 283 */       IOREOQPanel.this.gS2.drawString("OC = K*d/Q", 430, IOREOQPanel.this.GRAPHEIGHT + 40);
/*     */       
/* 285 */       drawnonlinear();
/* 286 */       g.drawImage(IOREOQPanel.this.offscreen, 0, 0, this);
/* 287 */       IOREOQPanel.this.gS2.dispose();
/*     */     }
/*     */     
/*     */ 
/*     */     private void drawnonlinear()
/*     */     {
/* 293 */       int qinit = 2;
/* 294 */       double ocinit = IOREOQPanel.this.D * IOREOQPanel.this.S * 1.0D / qinit;
/* 295 */       double tcinit = ocinit + 0.5D * IOREOQPanel.this.H * qinit;
/*     */       
/* 297 */       Point ocprv = new Point(qinit * IOREOQPanel.this.XSTEP / IOREOQPanel.this.XSCALE, IOREOQPanel.this.GRAPHEIGHT - 20 - (int)ocinit * IOREOQPanel.this.YSTEP / IOREOQPanel.this.YSCALE);
/* 298 */       Point tcprv = new Point(qinit * IOREOQPanel.this.XSTEP / IOREOQPanel.this.XSCALE, IOREOQPanel.this.GRAPHEIGHT - 20 - (int)tcinit * IOREOQPanel.this.YSTEP / IOREOQPanel.this.YSCALE);
/*     */       
/*     */ 
/* 301 */       double hcend = 0.5D * IOREOQPanel.this.H * 10 * IOREOQPanel.this.XSCALE;
/* 302 */       IOREOQPanel.this.gS2.drawLine(110, IOREOQPanel.this.GRAPHEIGHT - 20, 110 + 10 * IOREOQPanel.this.XSTEP, IOREOQPanel.this.GRAPHEIGHT - 20 - (int)hcend * IOREOQPanel.this.YSTEP / IOREOQPanel.this.YSCALE);
/* 303 */       IOREOQPanel.this.gS2.drawString("H C", 110 + 10 * IOREOQPanel.this.XSTEP - 20, IOREOQPanel.this.GRAPHEIGHT - 20 - (int)hcend * IOREOQPanel.this.YSTEP / IOREOQPanel.this.YSCALE - 5);
/*     */       
/* 305 */       boolean find = false;
/* 306 */       for (int q = 2; q <= 10 * IOREOQPanel.this.XSCALE; q++) {
/* 307 */         double ocval = IOREOQPanel.this.D * IOREOQPanel.this.S * 1.0D / q;
/* 308 */         double tcval = ocval + 0.5D * IOREOQPanel.this.H * q;
/* 309 */         int x = q * IOREOQPanel.this.XSTEP / IOREOQPanel.this.XSCALE;
/* 310 */         int yoc = IOREOQPanel.this.GRAPHEIGHT - 20 - (int)ocval * IOREOQPanel.this.YSTEP / IOREOQPanel.this.YSCALE;
/* 311 */         int ytc = IOREOQPanel.this.GRAPHEIGHT - 20 - (int)tcval * IOREOQPanel.this.YSTEP / IOREOQPanel.this.YSCALE;
/*     */         
/* 313 */         if ((ocprv.y > IOREOQPanel.this.TOP) && (tcprv.y > IOREOQPanel.this.TOP))
/*     */         {
/* 315 */           IOREOQPanel.this.gS2.setColor(Color.red);
/* 316 */           IOREOQPanel.this.gS2.drawLine(ocprv.x + 110, ocprv.y, x + 110, yoc);
/*     */           
/* 318 */           IOREOQPanel.this.gS2.setColor(Color.blue);
/* 319 */           IOREOQPanel.this.gS2.drawLine(tcprv.x + 110, tcprv.y, x + 110, ytc);
/*     */         }
/*     */         
/* 322 */         tcprv.x = x;
/* 323 */         tcprv.y = ytc;
/* 324 */         ocprv.x = x;
/* 325 */         ocprv.y = yoc;
/*     */       }
/*     */       
/* 328 */       IOREOQPanel.this.Qstar = Math.round((float)Math.sqrt(2.0D * IOREOQPanel.this.D * IOREOQPanel.this.S / IOREOQPanel.this.H));
/* 329 */       IOREOQPanel.this.Qy = Math.round((float)(IOREOQPanel.this.Qstar * 0.5D * IOREOQPanel.this.H + IOREOQPanel.this.D * IOREOQPanel.this.S / IOREOQPanel.this.Qstar));
/* 330 */       int optmx = 110 + IOREOQPanel.this.Qstar * IOREOQPanel.this.XSTEP / IOREOQPanel.this.XSCALE;
/* 331 */       int optmy = IOREOQPanel.this.GRAPHEIGHT - 20 - IOREOQPanel.this.Qy * IOREOQPanel.this.YSTEP / IOREOQPanel.this.YSCALE;
/*     */       
/* 333 */       IOREOQPanel.this.gS2.clearRect(50, optmy - 10, 50, 15);
/*     */       
/* 335 */       IOREOQPanel.this.gS2.setColor(Color.black);
/*     */       
/* 337 */       for (int temy = optmy; temy < IOREOQPanel.this.GRAPHEIGHT - 20; temy += 6) {
/* 338 */         IOREOQPanel.this.gS2.drawLine(optmx, temy, optmx, temy + 3);
/*     */       }
/* 340 */       for (int temx = optmx; temx > 110; temx -= 6) {
/* 341 */         IOREOQPanel.this.gS2.drawLine(temx, optmy, temx - 3, optmy);
/*     */       }
/* 343 */       IOREOQPanel.this.gS2.setColor(Color.black);
/* 344 */       IOREOQPanel.this.gS2.drawString("Q", 53, optmy);
/* 345 */       IOREOQPanel.this.gS2.drawString("Q", optmx - 17, IOREOQPanel.this.GRAPHEIGHT - 20 + 30);
/* 346 */       IOREOQPanel.this.gS2.setFont(new Font("", 1, 10));
/* 347 */       IOREOQPanel.this.gS2.drawString("0", optmx - 8, IOREOQPanel.this.GRAPHEIGHT - 20 + 30 + 2);
/* 348 */       IOREOQPanel.this.gS2.drawString("y", 60, optmy + 2);
/*     */       
/* 350 */       IOREOQPanel.this.gS2.setFont(new Font("", 1, 12));
/* 351 */       IOREOQPanel.this.gS2.drawString("=".concat(String.valueOf(String.valueOf(IOREOQPanel.this.Qstar))), optmx, IOREOQPanel.this.GRAPHEIGHT - 20 + 30 + 2);
/* 352 */       IOREOQPanel.this.gS2.drawString("=".concat(String.valueOf(String.valueOf(IOREOQPanel.this.Qy))), 65, optmy + 2);
/*     */       
/* 354 */       IOREOQPanel.this.gS2.setColor(Color.red);
/* 355 */       IOREOQPanel.this.gS2.drawString("O C", ocprv.x + 110 - 20, ocprv.y - 5);
/* 356 */       IOREOQPanel.this.gS2.setColor(Color.blue);
/* 357 */       IOREOQPanel.this.gS2.drawString("T C", tcprv.x + 110 - 20, tcprv.y - 5);
/*     */     }
/*     */     
/*     */     public void update(Graphics g)
/*     */     {
/* 362 */       paint(g);
/*     */     }
/*     */     
/*     */     public void adjustmentValueChanged(AdjustmentEvent e) {
/* 366 */       JScrollBar src = (JScrollBar)e.getSource();
/*     */       
/* 368 */       if (src == IOREOQPanel.this.scrolH) {
/* 369 */         int value = IOREOQPanel.this.scrolH.getValue();
/* 370 */         IOREOQPanel.this.H = (value * 1.0D);
/* 371 */         IOREOQPanel.this.hlab.setText(" H = ".concat(String.valueOf(String.valueOf(IOREOQPanel.this.decfm.format(IOREOQPanel.this.H)))));
/* 372 */         invalidate();
/* 373 */         repaint();
/* 374 */       } else if (src == IOREOQPanel.this.scrolD) {
/* 375 */         int value = IOREOQPanel.this.scrolD.getValue();
/* 376 */         IOREOQPanel.this.D = (value * 10.0D);
/* 377 */         IOREOQPanel.this.dlab.setText(" D = ".concat(String.valueOf(String.valueOf(IOREOQPanel.this.decfm.format(IOREOQPanel.this.D)))));
/* 378 */         invalidate();
/* 379 */         repaint();
/*     */       } else {
/* 381 */         int value = IOREOQPanel.this.scrolK.getValue();
/* 382 */         IOREOQPanel.this.S = (value * 1.0D);
/* 383 */         IOREOQPanel.this.slab.setText(" S = ".concat(String.valueOf(String.valueOf(IOREOQPanel.this.decfm.format(IOREOQPanel.this.S)))));
/* 384 */         invalidate();
/* 385 */         repaint();
/*     */       }
/* 387 */       IOREOQPanel.this.savePrintInfo();
/* 388 */       IOREOQPanel.this.controller.solver.initEOQPrintInfo(IOREOQPanel.this.controller.data);
/*     */     }
/*     */     
/* 391 */     public void actionPerformed(java.awt.event.ActionEvent evt) { IOREOQPanel.this.H = 9.0D;
/* 392 */       IOREOQPanel.this.scrolH.setValue(9);
/* 393 */       IOREOQPanel.this.hlab.setText(" H = ".concat(String.valueOf(String.valueOf(IOREOQPanel.this.decfm.format(IOREOQPanel.this.H)))));
/* 394 */       IOREOQPanel.this.S = 15.0D;
/* 395 */       IOREOQPanel.this.scrolK.setValue(15);
/* 396 */       IOREOQPanel.this.slab.setText(" S = ".concat(String.valueOf(String.valueOf(IOREOQPanel.this.decfm.format(IOREOQPanel.this.S)))));
/* 397 */       IOREOQPanel.this.D = 360.0D;
/* 398 */       IOREOQPanel.this.scrolD.setValue(36);
/* 399 */       IOREOQPanel.this.dlab.setText(" D = ".concat(String.valueOf(String.valueOf(IOREOQPanel.this.decfm.format(IOREOQPanel.this.D)))));
/* 400 */       invalidate();
/* 401 */       repaint();
/* 402 */       IOREOQPanel.this.savePrintInfo();
/* 403 */       IOREOQPanel.this.controller.solver.initEOQPrintInfo(IOREOQPanel.this.controller.data);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IOREOQPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */