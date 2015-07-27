/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
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
/*     */ public class IORIPCanvas
/*     */   extends JComponent
/*     */   implements ActionListener
/*     */ {
/*  33 */   public static final int WIDTH1 = 500;
/*     */   
/*  35 */   public static final int HEIGHT = 300;
/*     */   
/*  37 */   public static final int LEFT_MARGE = 20;
/*     */   
/*  39 */   public static final int RIGHT_MARGE = 20;
/*     */   
/*  41 */   public static final int WIDTH2 = 460;
/*     */   
/*     */ 
/*     */   private double widthRatio;
/*     */   
/*     */   private double heightRatio;
/*     */   
/*  48 */   static final int[] yPosition = { 35, 85, 135, 185, 235 };
/*  49 */   static final int[] yPosition2 = { 30, 75, 120, 165, 210, 255 };
/*     */   
/*     */ 
/*     */ 
/*  53 */   private int maxLevel = 0;
/*  54 */   private DecimalFormat df = new DecimalFormat("#.##");
/*     */   
/*  56 */   private Timer timer = new Timer(100, this);
/*  57 */   private int showAnimation = 0;
/*  58 */   private int animationFrameNo = 0;
/*  59 */   static final int MAX_ANIMATION_FRAMES = 10;
/*     */   
/*  61 */   private int focusNode = 0;
/*  62 */   private IORIPCanvas self = null;
/*     */   
/*     */   public IORIPProcessController.IPData data;
/*     */   
/*  66 */   public IORIPActionPanel actionPanel = null;
/*     */   
/*     */ 
/*  69 */   private JLabel[] tipLabel = { new JLabel("X subscript:"), new JLabel("Left Bound:"), new JLabel("Right Bound:") };
/*  70 */   private WholeNumberField[] inputField = { new WholeNumberField(1, 8), new WholeNumberField(0, 8), new WholeNumberField(1, 8) };
/*  71 */   private JPanel tipPanel = new JPanel();
/*  72 */   private JPanel inputPanel = new JPanel();
/*  73 */   private JPanel messagePanel = new JPanel();
/*  74 */   private Object[] butts = { "OK", "Cancel" };
/*     */   
/*  76 */   public Vector focusStep = new Vector();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORIPCanvas(int w, int h, IORIPProcessController.IPData d, IORIPActionPanel ap)
/*     */   {
/*  88 */     this.self = this;
/*  89 */     setPreferredSize(new Dimension(w, h));
/*  90 */     setBackground(Color.white);
/*  91 */     setForeground(Color.yellow);
/*     */     
/*  93 */     this.data = d;
/*  94 */     this.actionPanel = ap;
/*     */     
/*  96 */     this.tipPanel.setLayout(new BoxLayout(this.tipPanel, 1));
/*  97 */     this.inputPanel.setLayout(new BoxLayout(this.inputPanel, 1));
/*     */     
/*  99 */     this.messagePanel.setLayout(new BoxLayout(this.messagePanel, 1));
/* 100 */     JPanel p1 = new JPanel(new BorderLayout());
/* 101 */     p1.add(new MultilineLabel("Enter the subscript for the branching variable, and then enter the bounds for this variable for the two new sub-problems."), "Center");
/*     */     
/* 103 */     for (int i = 0; i < 3; i++) {
/* 104 */       this.tipPanel.add(this.tipLabel[i]);
/* 105 */       this.inputPanel.add(this.inputField[i]);
/*     */     }
/* 107 */     JPanel p2 = new JPanel();
/* 108 */     p2.add(this.tipPanel);
/* 109 */     p2.add(this.inputPanel);
/*     */     
/* 111 */     this.messagePanel.add(p1);
/* 112 */     this.messagePanel.add(p2);
/* 113 */     this.messagePanel.setPreferredSize(new Dimension(150, 150));
/*     */     
/* 115 */     addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mousePressed(MouseEvent event) {
/* 118 */         IORIPCanvas.this.self.requestFocus();
/*     */         
/* 120 */         if (IORIPCanvas.this.animationFrameNo > 0) return;
/* 121 */         int nodeNo = IORIPCanvas.this.getClickedNodeIndex(event.getPoint());
/* 122 */         if (nodeNo >= 0) {
/* 123 */           IORIPCanvas.this.focusNode = nodeNo;
/*     */           
/* 125 */           IORIPCanvas.this.actionPanel.updatePanel();
/* 126 */           if (event.getClickCount() >= 2)
/* 127 */             IORIPCanvas.this.branchNode();
/*     */         }
/*     */       }
/* 130 */     });
/* 131 */     addKeyListener(new KeyAdapter()
/*     */     {
/*     */       public void keyPressed(KeyEvent event) {
/* 134 */         Point p1 = new Point();
/* 135 */         switch (event.getKeyCode()) {
/*     */         case 37: 
/* 137 */           if (IORIPCanvas.this.getLeftChildNodePosition(IORIPCanvas.access$6(IORIPCanvas.this, IORIPCanvas.this.focusNode), IORIPCanvas.access$8(IORIPCanvas.this, IORIPCanvas.this.focusNode), IORIPCanvas.this.maxLevel, p1)) {
/* 138 */             IORIPCanvas.this.focusNode = IORIPCanvas.this.getNodeIndex(IORIPCanvas.access$6(IORIPCanvas.this, IORIPCanvas.this.focusNode) + 1, IORIPCanvas.access$8(IORIPCanvas.this, IORIPCanvas.this.focusNode) * 2);
/* 139 */             IORIPCanvas.this.actionPanel.updatePanel();
/*     */           }
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
/* 161 */           break;
/*     */         case 39: 
/* 144 */           if (IORIPCanvas.this.getRightChildNodePosition(IORIPCanvas.access$6(IORIPCanvas.this, IORIPCanvas.this.focusNode), IORIPCanvas.access$8(IORIPCanvas.this, IORIPCanvas.this.focusNode), IORIPCanvas.this.maxLevel, p1)) {
/* 145 */             IORIPCanvas.this.focusNode = IORIPCanvas.this.getNodeIndex(IORIPCanvas.access$6(IORIPCanvas.this, IORIPCanvas.this.focusNode) + 1, IORIPCanvas.access$8(IORIPCanvas.this, IORIPCanvas.this.focusNode) * 2 + 1);
/* 146 */             IORIPCanvas.this.actionPanel.updatePanel();
/*     */           }
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
/* 161 */           break;
/*     */         case 38: 
/* 151 */           if (IORIPCanvas.this.focusNode > 0) {
/* 152 */             IORIPCanvas.this.focusNode = IORIPCanvas.this.getParentNodeIndex(IORIPCanvas.access$6(IORIPCanvas.this, IORIPCanvas.this.focusNode), IORIPCanvas.access$8(IORIPCanvas.this, IORIPCanvas.this.focusNode));
/* 153 */             IORIPCanvas.this.actionPanel.updatePanel();
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 161 */           break;
/*     */         case 10: 
/* 158 */           IORIPCanvas.this.branchNode();
/*     */         }
/*     */         
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFocusTraversable()
/*     */   {
/* 168 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void branchNode()
/*     */   {
/* 175 */     if (getFocusNodeLevel() == getSupportedMaxLevel()) {
/* 176 */       if (this.data.isBIP) {
/* 177 */         JOptionPane.showMessageDialog(this, "You have already branched five levels (the maximum).");
/*     */       } else
/* 179 */         JOptionPane.showMessageDialog(this, "You have already branched four levels (the maximum).");
/* 180 */       return;
/*     */     }
/*     */     
/* 183 */     int nSubscript = 0;
/* 184 */     if ((this.data.nodes[this.focusNode].leafNode) && (this.data.nodes[this.focusNode].feasible) && (!this.data.nodes[this.focusNode].fathomed))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 193 */       if (this.data.isBIP == false) {
/* 194 */         if (!this.actionPanel.controller.solver.doWork(new IOROperation(40206, null))) {
/* 195 */           JOptionPane.showMessageDialog(this, this.actionPanel.controller.solver.errInfo);
/* 196 */           return;
/*     */         }
/* 198 */         int value = JOptionPane.showOptionDialog(this.self, this.messagePanel, "IORTutorial", -1, -1, null, this.butts, null);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 207 */         switch (value) {
/*     */         case -1: 
/*     */         case 1: 
/* 210 */           return;
/*     */         case 0: 
/* 212 */           nSubscript = this.inputField[0].getValue();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 217 */         nSubscript = getFocusNodeLevel() + 1;
/* 218 */         if (nSubscript > this.data.NumBinaryVariables) {
/* 219 */           JOptionPane.showMessageDialog(this, "You have already branched the maximum number of levels.");
/* 220 */           return;
/*     */         }
/*     */       }
/* 223 */       Vector par = new Vector();
/* 224 */       IOROperation opr = new IOROperation(40103, par);
/*     */       
/* 226 */       par.addElement(new Integer(getNodeLevel(this.focusNode)));
/* 227 */       par.addElement(new Integer(getNodeOffset(this.focusNode)));
/* 228 */       par.addElement(new Integer(nSubscript));
/* 229 */       if (this.data.isBIP == false) {
/* 230 */         opr.operation_code = 40203;
/* 231 */         par.addElement(new Integer(this.inputField[1].getValue()));
/* 232 */         par.addElement(new Integer(this.inputField[2].getValue()));
/*     */       }
/*     */       
/* 235 */       this.actionPanel.doStep(opr);
/* 236 */       if ((this.data.maxLevel > this.maxLevel) && ((this.maxLevel == 1) || (this.maxLevel == 2))) {
/* 237 */         this.showAnimation = 1;
/* 238 */         this.timer.start();
/*     */       }
/*     */       else {
/* 241 */         revalidate();
/* 242 */         repaint();
/*     */       }
/* 244 */       this.maxLevel = this.data.maxLevel;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setFocusNode(int newFocusNode)
/*     */   {
/* 252 */     if ((newFocusNode < 0) || (newFocusNode > this.data.MAX_NODE_NUMBER))
/* 253 */       this.focusNode = 0;
/* 254 */     this.focusNode = newFocusNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFocusNodeIndex()
/*     */   {
/* 261 */     if ((this.focusNode < 0) || (this.focusNode > this.data.MAX_NODE_NUMBER))
/* 262 */       this.focusNode = 0;
/* 263 */     return this.focusNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFocusNodeLevel()
/*     */   {
/* 270 */     if ((this.focusNode < 0) || (this.focusNode > this.data.MAX_NODE_NUMBER))
/* 271 */       this.focusNode = 0;
/* 272 */     return getNodeLevel(this.focusNode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFocusNodeOffset()
/*     */   {
/* 279 */     if ((this.focusNode < 0) || (this.focusNode > this.data.MAX_NODE_NUMBER))
/* 280 */       this.focusNode = 0;
/* 281 */     return getNodeOffset(this.focusNode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addNodeFocusStep()
/*     */   {
/* 288 */     this.focusStep.add(new Integer(this.focusNode));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void backNodeFocusStep()
/*     */   {
/* 295 */     if (this.focusStep.size() > 0) {
/* 296 */       this.focusNode = ((Integer)this.focusStep.elementAt(this.focusStep.size() - 1)).intValue();
/* 297 */       this.focusStep.removeElementAt(this.focusStep.size() - 1);
/*     */     }
/*     */     else {
/* 300 */       this.focusNode = 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void actionPerformed(ActionEvent evt)
/*     */   {
/* 307 */     if (this.showAnimation > 0) {
/* 308 */       this.animationFrameNo += 1;
/* 309 */       if (this.animationFrameNo > 10) {
/* 310 */         this.animationFrameNo = 0;
/* 311 */         this.showAnimation = 0;
/* 312 */         this.timer.stop();
/*     */       }
/*     */     }
/* 315 */     invalidate();
/* 316 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void paintComponent(Graphics g)
/*     */   {
/* 324 */     Graphics2D g2 = (Graphics2D)g;
/*     */     
/* 326 */     g2.setColor(getBackground());
/*     */     
/* 328 */     Dimension d = getSize();
/*     */     
/* 330 */     double w = d.getWidth();
/* 331 */     double h = d.getHeight();
/*     */     
/* 333 */     g2.fillRect(10, 10, (int)w - 20, (int)h - 20);
/* 334 */     g2.setColor(getForeground());
/*     */     
/* 336 */     this.widthRatio = ((w - 20 - 20) / 'ǌ');
/* 337 */     this.heightRatio = (h / 'Ĭ');
/*     */     
/*     */ 
/* 340 */     Point p1 = new Point();
/* 341 */     Point p2 = new Point();
/* 342 */     Point p3 = new Point();
/* 343 */     int k = 0;
/* 344 */     int temp = 1;
/* 345 */     for (int i = 0; i <= getSupportedMaxLevel(); i++) {
/* 346 */       for (int j = 0; j < temp; j++) {
/* 347 */         if (this.showAnimation > 0) {
/* 348 */           if (i == this.maxLevel) {
/* 349 */             if ((i <= 1) && 
/* 350 */               (getNodePosition(i, j, this.maxLevel, p1) != false)) {
/* 351 */               g2.drawOval(p1.x - 8, p1.y - 8, 16, 16);
/* 352 */               g2.fillOval(p1.x - 8, p1.y - 8, 16, 16);
/* 353 */               if ((getNodeLevel(this.focusNode) == i) && (getNodeOffset(this.focusNode) == j)) {
/* 354 */                 g2.drawOval(p1.x - 12, p1.y - 12, 24, 24);
/*     */               }
/*     */             }
/* 357 */           } else if (getNodePosition(i, j, this.maxLevel, p1) != false) {
/* 358 */             getNodePosition(i, j, this.maxLevel - 1, p3);
/*     */             
/* 360 */             p1.x = ((p1.x - p3.x) * this.animationFrameNo / 10 + p3.x);
/* 361 */             g2.drawOval(p1.x - 8, p1.y - 8, 16, 16);
/* 362 */             g2.fillOval(p1.x - 8, p1.y - 8, 16, 16);
/* 363 */             if ((getNodeLevel(this.focusNode) == i) && (getNodeOffset(this.focusNode) == j))
/* 364 */               g2.drawOval(p1.x - 12, p1.y - 12, 24, 24);
/* 365 */             if (i + 1 != this.maxLevel)
/*     */             {
/* 367 */               if ((getLeftChildNodePosition(i, j, this.maxLevel, p2)) && (getLeftChildNodePosition(i, j, this.maxLevel - 1, p3))) {
/* 368 */                 p3.x -= (p3.x - p2.x) * this.animationFrameNo / 10;
/* 369 */                 g2.drawLine(p1.x, p1.y, p2.x, p2.y);
/*     */               }
/*     */               
/* 372 */               if ((getRightChildNodePosition(i, j, this.maxLevel, p2)) && (getRightChildNodePosition(i, j, this.maxLevel - 1, p3))) {
/* 373 */                 p3.x += (p2.x - p3.x) * this.animationFrameNo / 10;
/* 374 */                 g2.drawLine(p1.x, p1.y, p2.x, p2.y);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 379 */         else if (getNodePosition(i, j, this.data.maxLevel, p1) != false) {
/* 380 */           g2.drawOval(p1.x - 8, p1.y - 8, 16, 16);
/* 381 */           g2.fillOval(p1.x - 8, p1.y - 8, 16, 16);
/* 382 */           g2.setColor(Color.blue);
/* 383 */           int index = getNodeIndex(i, j);
/* 384 */           if (this.data.nodes[index].feasible) { String text;
/*     */             String text;
/* 386 */             if (this.data.isBIP) { String text;
/* 387 */               if (this.data.objective_is_max) {
/* 388 */                 text = new Integer((int)this.data.nodes[index].Z).toString();
/*     */               } else {
/* 390 */                 text = new Integer((int)(this.data.nodes[index].Z + 0.999D)).toString();
/*     */               }
/*     */             } else {
/* 393 */               text = this.df.format(this.data.nodes[index].Z); }
/* 394 */             g2.drawString(text, p1.x - getNodeTextPosition(text.length()), p1.y + 24);
/* 395 */             if (this.data.nodes[index].fathomed) {
/* 396 */               g2.drawString("F", p1.x - 4, p1.y + 40);
/*     */             }
/*     */           } else {
/* 399 */             g2.drawString("Inf.", p1.x - 6, p1.y + 24);
/* 400 */             g2.drawString("F", p1.x - 2, p1.y + 40);
/*     */           }
/* 402 */           g2.setColor(Color.red);
/* 403 */           if ((getNodeLevel(this.focusNode) == i) && (getNodeOffset(this.focusNode) == j))
/* 404 */             g2.drawOval(p1.x - 12, p1.y - 12, 24, 24);
/* 405 */           if (getLeftChildNodePosition(i, j, this.data.maxLevel, p2)) {
/* 406 */             g2.drawLine(p1.x, p1.y, p2.x, p2.y);
/* 407 */             g2.setColor(Color.blue);
/* 408 */             index = getNodeIndex(i, j);
/* 409 */             if (this.data.isBIP) {
/* 410 */               g2.drawString(String.valueOf(String.valueOf(new StringBuffer("X").append(this.data.nodes[index].xSubscript).append("=").append(this.data.nodes[index].xLowerValue))), (p1.x + p2.x) / 2 - 32, (p1.y + p2.y) / 2 + 12);
/*     */             } else
/* 412 */               g2.drawString(String.valueOf(String.valueOf(new StringBuffer("X").append(this.data.nodes[index].xSubscript).append("<=").append(this.data.nodes[index].xLowerValue))), (p1.x + p2.x) / 2 - 38, (p1.y + p2.y) / 2 + 12);
/* 413 */             g2.setColor(Color.red);
/*     */           }
/* 415 */           if (getRightChildNodePosition(i, j, this.data.maxLevel, p2)) {
/* 416 */             g2.drawLine(p1.x, p1.y, p2.x, p2.y);
/* 417 */             g2.setColor(Color.blue);
/* 418 */             index = getNodeIndex(i, j);
/* 419 */             if (this.data.isBIP) {
/* 420 */               g2.drawString(String.valueOf(String.valueOf(new StringBuffer("X").append(this.data.nodes[index].xSubscript).append("=").append(this.data.nodes[index].xUpperValue))), (p1.x + p2.x) / 2 + 6, (p1.y + p2.y) / 2 + 12);
/*     */             } else
/* 422 */               g2.drawString(String.valueOf(String.valueOf(new StringBuffer("X").append(this.data.nodes[index].xSubscript).append(">=").append(this.data.nodes[index].xUpperValue))), (p1.x + p2.x) / 2 + 8, (p1.y + p2.y) / 2 + 12);
/* 423 */             g2.setColor(Color.red);
/*     */           }
/*     */         }
/*     */       }
/* 427 */       temp *= 2;
/*     */     }
/*     */   }
/*     */   
/*     */   private int getNodeTextPosition(int nTextLength)
/*     */   {
/* 433 */     if (nTextLength == 1) return 4;
/* 434 */     if (nTextLength == 2) return 6;
/* 435 */     if (nTextLength == 3) return 12;
/* 436 */     if (nTextLength == 4) return 16;
/* 437 */     return 16;
/*     */   }
/*     */   
/*     */   private boolean getNodePosition(int level, int offset, int cur_maxLevel, Point p)
/*     */   {
/* 442 */     double b = 0.0D;
/*     */     
/* 444 */     if (this.data.isBIP == false) {
/* 445 */       if (level > 4) return false;
/* 446 */       if (this.data.nodes[getNodeIndex(level, offset)].valid == false) return false;
/* 447 */       if (cur_maxLevel < 3) {
/* 448 */         if (level == 0) {
/* 449 */           p.x = ((int)('æ' * this.widthRatio) + 20);
/* 450 */           p.y = ((int)(yPosition[0] * this.heightRatio));
/* 451 */           return true;
/*     */         }
/* 453 */         double a = 14.375D;
/* 454 */         if (cur_maxLevel == 2) {
/* 455 */           if (level == 2) {
/* 456 */             b = ((offset + 2) * 2 + 1) * 2 * a;
/* 457 */           } else if (level == 1) {
/* 458 */             b = ((offset + 1) * 2 + 1) * 4 * a;
/*     */           }
/* 460 */         } else if (cur_maxLevel == 1) {
/* 461 */           b = ((offset + 3) * 2 + 1) * 2 * a;
/*     */         }
/*     */       }
/*     */       else {
/* 465 */         double a = 'ǌ' / Math.pow(2.0D, level + 1);
/* 466 */         b = (offset * 2 + 1) * a;
/*     */       }
/* 468 */       p.x = ((int)(b * this.widthRatio) + 20);
/* 469 */       p.y = ((int)(yPosition[level] * this.heightRatio));
/*     */     }
/*     */     else {
/* 472 */       if (level > 5) return false;
/* 473 */       if (this.data.nodes[getNodeIndex(level, offset)].valid == false) return false;
/* 474 */       if (cur_maxLevel < 3) {
/* 475 */         if (level == 0) {
/* 476 */           p.x = ((int)('æ' * this.widthRatio) + 20);
/* 477 */           p.y = ((int)(yPosition2[0] * this.heightRatio));
/* 478 */           return true;
/*     */         }
/* 480 */         double a = 14.375D;
/* 481 */         if (cur_maxLevel == 2) {
/* 482 */           if (level == 2) {
/* 483 */             b = ((offset + 2) * 2 + 1) * 2 * a;
/* 484 */           } else if (level == 1) {
/* 485 */             b = ((offset + 1) * 2 + 1) * 4 * a;
/*     */           }
/* 487 */         } else if (cur_maxLevel == 1) {
/* 488 */           b = ((offset + 3) * 2 + 1) * 2 * a;
/*     */         }
/*     */       }
/*     */       else {
/* 492 */         double a = 'ǌ' / Math.pow(2.0D, level + 1);
/* 493 */         b = (offset * 2 + 1) * a;
/*     */       }
/* 495 */       p.x = ((int)(b * this.widthRatio) + 20);
/* 496 */       p.y = ((int)(yPosition2[level] * this.heightRatio));
/*     */     }
/* 498 */     return true;
/*     */   }
/*     */   
/*     */   private boolean getLeftChildNodePosition(int level, int offset, int cur_maxLevel, Point p) {
/* 502 */     if (level >= getSupportedMaxLevel()) return false;
/* 503 */     return getNodePosition(level + 1, offset * 2, cur_maxLevel, p);
/*     */   }
/*     */   
/*     */   private boolean getRightChildNodePosition(int level, int offset, int cur_maxLevel, Point p) {
/* 507 */     if (level >= getSupportedMaxLevel()) return false;
/* 508 */     return getNodePosition(level + 1, offset * 2 + 1, cur_maxLevel, p);
/*     */   }
/*     */   
/*     */   private int getParentNodeIndex(int level, int offset) {
/* 512 */     if (level == 0) return -1;
/* 513 */     return getNodeIndex(level - 1, offset / 2);
/*     */   }
/*     */   
/*     */   private int getParentNodeIndex(int index) {
/* 517 */     return getParentNodeIndex(getNodeLevel(index), getNodeOffset(index));
/*     */   }
/*     */   
/*     */   private int getNodeIndex(int level, int offset)
/*     */   {
/* 522 */     int index = (int)(Math.pow(2.0D, level) + offset - 1);
/* 523 */     return index;
/*     */   }
/*     */   
/*     */   private int getNodeLevel(int index) {
/* 527 */     int sum = 0;
/* 528 */     for (int level = 0; level <= getSupportedMaxLevel(); level++) {
/* 529 */       sum += (int)Math.pow(2.0D, level);
/* 530 */       if (index < sum) return level;
/*     */     }
/* 532 */     return -1;
/*     */   }
/*     */   
/*     */   private int getNodeOffset(int index) {
/* 536 */     for (int level = 0; level <= getSupportedMaxLevel(); level++) {
/* 537 */       if (index >= (int)Math.pow(2.0D, level)) {
/* 538 */         index -= (int)Math.pow(2.0D, level);
/*     */       } else
/* 540 */         return index;
/*     */     }
/* 542 */     return -1;
/*     */   }
/*     */   
/*     */   private int getClickedNodeIndex(Point p) {
/* 546 */     Point p1 = new Point();
/* 547 */     for (int i = 0; i < this.data.MAX_NODE_NUMBER; i++) {
/* 548 */       if ((getNodeLevel(i) == -1) || (getNodeOffset(i) == -1)) return -1;
/* 549 */       if ((getNodePosition(getNodeLevel(i), getNodeOffset(i), this.data.maxLevel, p1) != false) && 
/* 550 */         (p.x <= p1.x + 12) && (p.x >= p1.x - 12) && (p.y <= p1.y + 12) && (p.y >= p1.y - 12)) return i;
/*     */     }
/* 552 */     return -1;
/*     */   }
/*     */   
/*     */   private int getSupportedMaxLevel() {
/* 556 */     if (this.data.isBIP) {
/* 557 */       return 5;
/*     */     }
/* 559 */     return 4;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORIPCanvas.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */