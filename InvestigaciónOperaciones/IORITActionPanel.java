/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Vector;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
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
/*     */ public class IORITActionPanel
/*     */   extends IORActionPanel
/*     */ {
/*     */   private double lemta;
/*     */   private double purchasecost;
/*     */   private double holdcost;
/*     */   private double shortagecost;
/*     */   private double setupcost;
/*     */   private double discount;
/*     */   private double x0;
/*     */   private double x1;
/*     */   private double t;
/*     */   private double y0;
/*     */   private double S;
/*     */   private double s;
/*     */   private double y10;
/*     */   private double y20;
/*  51 */   private DecimalField df_lemta = null;
/*  52 */   private DecimalField df_purchasecost = null;
/*  53 */   private DecimalField df_holdcost = null;
/*  54 */   private DecimalField df_shortagecost = null;
/*  55 */   private DecimalField df_setupcostfield = null;
/*  56 */   private DecimalField df_discountfield = null;
/*     */   
/*  58 */   private DecimalField df_x0 = null;
/*  59 */   private DecimalField df_x1 = null;
/*  60 */   private DecimalField df_t = null;
/*     */   
/*  62 */   private JPanel mainPanel = null;
/*     */   
/*  64 */   private JPanel titlePanel = null;
/*  65 */   private JPanel inputPanel = null;
/*  66 */   private JPanel outputPanel = null;
/*     */   
/*     */ 
/*  69 */   private JLabel result = new JLabel("Results:");
/*  70 */   private JLabel y0equal = new JLabel("y");
/*  71 */   private JLabel Sequal = new JLabel();
/*  72 */   private JLabel sequal = new JLabel();
/*  73 */   private JLabel y10equal = new JLabel("y");
/*  74 */   private JLabel y20equal = new JLabel("y");
/*  75 */   private JLabel y00equal = new JLabel(" =");
/*  76 */   private JLabel y100equal = new JLabel(" =");
/*  77 */   private JLabel y200equal = new JLabel(" =");
/*     */   
/*  79 */   private JLabel y0resultlabel = new JLabel();
/*  80 */   private JLabel y10resultlabel = new JLabel();
/*  81 */   private JLabel y20resultlabel = new JLabel();
/*     */   
/*  83 */   private DecimalFormat keepthree = new DecimalFormat("#.###");
/*  84 */   private JPanel y0Panel = new JPanel();
/*  85 */   private JPanel y10Panel = new JPanel();
/*  86 */   private JPanel y20Panel = new JPanel();
/*  87 */   private char lamda = 'λ';
/*     */   
/*     */   private int whichtypeface;
/*     */   
/*  91 */   private int step = 1;
/*     */   
/*  93 */   private IORITProcessController controller = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORITActionPanel(IORITProcessController c)
/*     */   {
/* 102 */     super(c);
/* 103 */     this.controller = c;
/*     */     
/* 105 */     this.mainPanel = new JPanel();
/* 106 */     add(this.mainPanel, "Center");
/*     */     
/*     */ 
/* 109 */     this.lemta = this.controller.data.lemta;
/* 110 */     this.purchasecost = this.controller.data.purchasecost;
/* 111 */     this.holdcost = this.controller.data.holdcost;
/* 112 */     this.shortagecost = this.controller.data.shortagecost;
/* 113 */     this.setupcost = this.controller.data.setupcost;
/* 114 */     this.discount = this.controller.data.discount;
/* 115 */     this.x0 = this.controller.data.x0;
/* 116 */     this.x1 = this.controller.data.x1;
/* 117 */     this.t = this.controller.data.t;
/*     */     
/* 119 */     this.df_lemta = new DecimalField(1.0D, 5, new DecimalFormat());
/* 120 */     this.df_lemta.setText(String.valueOf(this.lemta));
/* 121 */     this.df_x0 = new DecimalField(1.0D, 5, new DecimalFormat());
/* 122 */     this.df_x0.setText(String.valueOf(this.x0));
/* 123 */     this.df_x1 = new DecimalField(1.0D, 5, new DecimalFormat());
/* 124 */     this.df_x1.setText(String.valueOf(this.x1));
/* 125 */     this.df_t = new DecimalField(1.0D, 5, new DecimalFormat());
/* 126 */     this.df_t.setText(String.valueOf(this.t));
/* 127 */     this.df_purchasecost = new DecimalField(1.0D, 5, new DecimalFormat());
/* 128 */     this.df_purchasecost.setText(String.valueOf(this.purchasecost));
/* 129 */     this.df_holdcost = new DecimalField(1.0D, 5, new DecimalFormat());
/* 130 */     this.df_holdcost.setText(String.valueOf(this.holdcost));
/* 131 */     this.df_shortagecost = new DecimalField(1.0D, 5, new DecimalFormat());
/* 132 */     this.df_shortagecost.setText(String.valueOf(this.shortagecost));
/* 133 */     this.df_setupcostfield = new DecimalField(1.0D, 5, new DecimalFormat());
/* 134 */     this.df_setupcostfield.setText(String.valueOf(this.setupcost));
/* 135 */     this.df_discountfield = new DecimalField(1.0D, 5, new DecimalFormat());
/* 136 */     this.df_discountfield.setText(String.valueOf(this.discount));
/*     */     
/*     */ 
/* 139 */     this.df_lemta.addActionListener(new ActionListener()
/*     */     {
/* 141 */       public void actionPerformed(ActionEvent e) { IORITActionPanel.this.checkallvalue(e); }
/* 142 */     });
/* 143 */     this.df_x0.addActionListener(new ActionListener()
/*     */     {
/* 145 */       public void actionPerformed(ActionEvent e) { IORITActionPanel.this.checkallvalue(e); }
/* 146 */     });
/* 147 */     this.df_x1.addActionListener(new ActionListener()
/*     */     {
/* 149 */       public void actionPerformed(ActionEvent e) { IORITActionPanel.this.checkallvalue(e); }
/* 150 */     });
/* 151 */     this.df_t.addActionListener(new ActionListener()
/*     */     {
/* 153 */       public void actionPerformed(ActionEvent e) { IORITActionPanel.this.checkallvalue(e); }
/* 154 */     });
/* 155 */     this.df_purchasecost.addActionListener(new ActionListener()
/*     */     {
/* 157 */       public void actionPerformed(ActionEvent e) { IORITActionPanel.this.checkallvalue(e); }
/* 158 */     });
/* 159 */     this.df_holdcost.addActionListener(new ActionListener()
/*     */     {
/* 161 */       public void actionPerformed(ActionEvent e) { IORITActionPanel.this.checkallvalue(e); }
/* 162 */     });
/* 163 */     this.df_shortagecost.addActionListener(new ActionListener()
/*     */     {
/* 165 */       public void actionPerformed(ActionEvent e) { IORITActionPanel.this.checkallvalue(e); }
/* 166 */     });
/* 167 */     this.df_setupcostfield.addActionListener(new ActionListener()
/*     */     {
/* 169 */       public void actionPerformed(ActionEvent e) { IORITActionPanel.this.checkallvalue(e); }
/* 170 */     });
/* 171 */     this.df_discountfield.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 173 */         IORITActionPanel.this.checkallvalue(e);
/*     */       }
/*     */       
/* 176 */     });
/* 177 */     JLabel addLab = new JLabel();
/* 178 */     JLabel inputlemta = new JLabel();
/* 179 */     addLab.setText(String.valueOf(String.valueOf(this.lamda)).concat("distribution, etc.:"));
/* 180 */     JLabel lemtaequal = new JLabel(String.valueOf(String.valueOf(this.lamda)).concat(" ="));
/* 181 */     JLabel purchasecostequal = new JLabel("Production or Purchasing cost, c =");
/* 182 */     JLabel holdcostequal = new JLabel("Inventory holding cost, h =");
/* 183 */     JLabel shortagecostequal = new JLabel("Unit shortage cost, p =");
/* 184 */     JLabel setupcostequal = new JLabel("Setup cost, K =");
/* 185 */     char alpha = 'α';
/* 186 */     JLabel discountequal = new JLabel(String.valueOf(String.valueOf(new StringBuffer("Discount Factor, ").append(alpha).append(" ="))));
/*     */     
/* 188 */     JLabel inputuniformtwofactor = new JLabel("The demand has a uniform distribution between x0 and x1, where");
/* 189 */     JLabel inputuniformonefactor = new JLabel("The demand has a uniform distribution between 0 and t, where");
/* 190 */     JLabel x0equal = new JLabel("x0 =");
/* 191 */     JLabel x1equal = new JLabel("x1 =");
/* 192 */     JLabel tequal = new JLabel("t =");
/*     */     
/*     */ 
/* 195 */     lemtaequal.setAlignmentX(1.0F);
/* 196 */     purchasecostequal.setAlignmentX(1.0F);
/* 197 */     holdcostequal.setAlignmentX(1.0F);
/* 198 */     shortagecostequal.setAlignmentX(1.0F);
/* 199 */     setupcostequal.setAlignmentX(1.0F);
/* 200 */     discountequal.setAlignmentX(1.0F);
/* 201 */     x0equal.setAlignmentX(1.0F);
/* 202 */     x1equal.setAlignmentX(1.0F);
/* 203 */     tequal.setAlignmentX(1.0F);
/*     */     
/*     */ 
/* 206 */     this.titlePanel = new JPanel();
/* 207 */     this.inputPanel = new JPanel();
/* 208 */     this.outputPanel = new JPanel();
/*     */     
/*     */ 
/* 211 */     JPanel firstPanel = new JPanel();
/* 212 */     firstPanel.setLayout(new BoxLayout(firstPanel, 1));
/* 213 */     JPanel secondPanel = new JPanel();
/* 214 */     secondPanel.setLayout(new BoxLayout(secondPanel, 1));
/*     */     
/*     */ 
/* 217 */     this.whichtypeface = c.problemno;
/*     */     
/* 219 */     switch (this.whichtypeface) {
/*     */     case 1: 
/*     */     case 2: 
/*     */     case 3: 
/*     */     case 4: 
/* 224 */       this.titlePanel.setLayout(null);
/* 225 */       this.titlePanel.setBounds(10, 10, 580, 50);
/*     */       
/*     */ 
/* 228 */       inputlemta.setBounds(10, 0, 580, 20);
/* 229 */       addLab.setBounds(10, 25, 580, 20);
/* 230 */       this.titlePanel.add(inputlemta);
/* 231 */       this.titlePanel.add(addLab);
/* 232 */       if ((this.whichtypeface == 1) || (this.whichtypeface == 2)) {
/* 233 */         inputlemta.setText(String.valueOf(String.valueOf(new StringBuffer("Using the notation of Section 18.7, enter the parameter ").append(this.lamda).append(", (the mean), of the exponential"))));
/* 234 */         addLab.setText("distribution, etc.:");
/*     */       } else {
/* 236 */         inputlemta.setText("Using the notation of the second supplement to Chapter 18 (on the CD-ROM), enter the");
/* 237 */         addLab.setText(String.valueOf(String.valueOf(new StringBuffer("parameter ").append(this.lamda).append(", (the mean),of the exponential distribution, etc.:"))));
/*     */       }
/*     */       
/* 240 */       firstPanel.add(lemtaequal);
/* 241 */       firstPanel.add(Box.createVerticalStrut(5));
/*     */       
/* 243 */       secondPanel.add(this.df_lemta);
/* 244 */       secondPanel.add(Box.createVerticalStrut(1));
/* 245 */       break;
/*     */     case 5: 
/*     */     case 6: 
/* 248 */       this.titlePanel.add(inputuniformtwofactor);
/* 249 */       firstPanel.add(x0equal);
/*     */       
/*     */ 
/* 252 */       firstPanel.add(Box.createVerticalStrut(5));
/* 253 */       firstPanel.add(x1equal);
/* 254 */       firstPanel.add(Box.createVerticalStrut(5));
/*     */       
/* 256 */       secondPanel.add(this.df_x0);
/* 257 */       secondPanel.add(Box.createVerticalStrut(1));
/* 258 */       secondPanel.add(this.df_x1);
/* 259 */       secondPanel.add(Box.createVerticalStrut(1));
/* 260 */       break;
/*     */     case 7: 
/*     */     case 8: 
/* 263 */       this.titlePanel.add(inputuniformonefactor);
/* 264 */       firstPanel.add(tequal);
/* 265 */       firstPanel.add(Box.createVerticalStrut(5));
/* 266 */       secondPanel.add(this.df_t);
/* 267 */       secondPanel.add(Box.createVerticalStrut(1));
/* 268 */       break;
/*     */     }
/*     */     
/*     */     
/*     */ 
/*     */ 
/* 274 */     firstPanel.add(purchasecostequal);
/* 275 */     firstPanel.add(Box.createVerticalStrut(5));
/* 276 */     firstPanel.add(holdcostequal);
/* 277 */     firstPanel.add(Box.createVerticalStrut(5));
/* 278 */     firstPanel.add(shortagecostequal);
/* 279 */     firstPanel.add(Box.createVerticalStrut(5));
/*     */     
/* 281 */     secondPanel.add(this.df_purchasecost);
/* 282 */     secondPanel.add(Box.createVerticalStrut(1));
/*     */     
/* 284 */     secondPanel.add(this.df_holdcost);
/* 285 */     secondPanel.add(Box.createVerticalStrut(1));
/*     */     
/* 287 */     secondPanel.add(this.df_shortagecost);
/* 288 */     secondPanel.add(Box.createVerticalStrut(1));
/*     */     
/*     */ 
/* 291 */     switch (this.whichtypeface)
/*     */     {
/*     */     case 2: 
/*     */     case 6: 
/* 295 */       firstPanel.add(setupcostequal);
/* 296 */       secondPanel.add(this.df_setupcostfield);
/* 297 */       break;
/*     */     
/*     */     case 4: 
/*     */     case 8: 
/* 301 */       firstPanel.add(discountequal);
/* 302 */       secondPanel.add(this.df_discountfield);
/* 303 */       break;
/*     */     }
/*     */     
/*     */     
/*     */ 
/*     */ 
/* 309 */     this.inputPanel.add(firstPanel);
/* 310 */     this.inputPanel.add(secondPanel);
/*     */     
/* 312 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*     */     
/* 314 */     this.mainPanel.add(this.titlePanel);
/* 315 */     this.mainPanel.add(this.inputPanel);
/* 316 */     this.mainPanel.add(this.outputPanel);
/*     */   }
/*     */   
/*     */   private void checkallvalue(ActionEvent e)
/*     */   {
/* 321 */     if (e.getSource() == this.df_lemta) {
/* 322 */       this.lemta = Double.parseDouble(this.df_lemta.getText());
/* 323 */       if (this.lemta < 0.001D) this.lemta = 0.001D;
/* 324 */       this.df_lemta.setText(String.valueOf(this.lemta));
/*     */     }
/* 326 */     if (e.getSource() == this.df_purchasecost) {
/* 327 */       this.purchasecost = Double.parseDouble(this.df_purchasecost.getText());
/* 328 */       if (this.purchasecost < 0.001D) this.purchasecost = 0.001D;
/* 329 */       this.df_purchasecost.setText(String.valueOf(this.purchasecost));
/*     */     }
/* 331 */     if (e.getSource() == this.df_shortagecost) {
/* 332 */       this.shortagecost = Double.parseDouble(this.df_shortagecost.getText());
/* 333 */       if (this.shortagecost < 0.001D) this.shortagecost = 0.001D;
/* 334 */       this.df_shortagecost.setText(String.valueOf(this.shortagecost));
/*     */     }
/* 336 */     if (e.getSource() == this.df_setupcostfield) {
/* 337 */       this.setupcost = Double.parseDouble(this.df_setupcostfield.getText());
/* 338 */       if (this.setupcost < 0) this.setupcost = 0.0D;
/* 339 */       this.df_setupcostfield.setText(String.valueOf(this.setupcost));
/*     */     }
/* 341 */     if (e.getSource() == this.df_discountfield) {
/* 342 */       this.discount = Double.parseDouble(this.df_discountfield.getText());
/* 343 */       if (this.discount < 0.001D) this.discount = 0.001D;
/* 344 */       this.df_discountfield.setText(String.valueOf(this.discount));
/*     */     }
/* 346 */     if (e.getSource() == this.df_x0) {
/* 347 */       this.x0 = Double.parseDouble(this.df_x0.getText());
/* 348 */       if (this.x0 < 0) this.x0 = 0.0D;
/* 349 */       this.df_x0.setText(String.valueOf(this.x0));
/*     */     }
/* 351 */     if (e.getSource() == this.df_x1) {
/* 352 */       this.x1 = Double.parseDouble(this.df_x1.getText());
/* 353 */       if (this.x1 < 0) this.x1 = 0.0D;
/* 354 */       this.df_x1.setText(String.valueOf(this.x1));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updatePanel() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finishProcedure()
/*     */   {
/* 373 */     boolean error = false;
/*     */     
/* 375 */     this.purchasecost = Double.parseDouble(this.df_purchasecost.getText());
/* 376 */     if (this.purchasecost < 0.001D) {
/* 377 */       this.purchasecost = 0.001D;
/* 378 */       this.df_purchasecost.setText(String.valueOf(this.purchasecost));
/* 379 */       error = true;
/*     */     }
/* 381 */     this.holdcost = Double.parseDouble(this.df_holdcost.getText());
/*     */     
/* 383 */     this.shortagecost = Double.parseDouble(this.df_shortagecost.getText());
/* 384 */     if (this.shortagecost < 0.001D) {
/* 385 */       this.shortagecost = 0.001D;
/* 386 */       this.df_shortagecost.setText(String.valueOf(this.shortagecost));
/* 387 */       error = true;
/*     */     }
/*     */     
/* 390 */     switch (this.whichtypeface) {
/*     */     case 4: 
/*     */     case 8: 
/* 393 */       this.discount = Double.parseDouble(this.df_discountfield.getText());
/* 394 */       if (this.discount < 0.001D) {
/* 395 */         this.discount = 0.001D;
/* 396 */         this.df_discountfield.setText(String.valueOf(this.discount));
/* 397 */         error = true;
/*     */       }
/* 399 */       if (this.discount > 1) {
/* 400 */         this.discount = 1.0D;
/* 401 */         this.df_discountfield.setText(String.valueOf(this.discount));
/* 402 */         error = true;
/*     */       }
/* 404 */       if (this.shortagecost < this.purchasecost * (1 - this.discount)) {
/* 405 */         JOptionPane.showMessageDialog(this, "Invalid model because p < c(1-alpha)."); return;
/*     */       }
/*     */       
/*     */       break;
/*     */     default: 
/* 410 */       if (this.shortagecost < this.purchasecost) {
/* 411 */         JOptionPane.showMessageDialog(this, "Invalid model because p < c."); return;
/*     */       }
/*     */       
/*     */       break;
/*     */     }
/*     */     
/* 417 */     switch (this.whichtypeface) {
/*     */     case 4: 
/*     */     case 5: 
/* 420 */       this.x0 = Double.parseDouble(this.df_x0.getText());
/* 421 */       this.x1 = Double.parseDouble(this.df_x1.getText());
/* 422 */       if (this.x1 <= this.x0) {
/* 423 */         JOptionPane.showMessageDialog(this, "Invalid model because x1 <= x0."); return;
/*     */       }
/*     */       
/*     */       break;
/*     */     case 7: 
/*     */     case 8: 
/* 429 */       this.t = Double.parseDouble(this.df_t.getText());
/* 430 */       if (this.t <= 0) {
/* 431 */         JOptionPane.showMessageDialog(this, "Invalid model because t <= 0."); return;
/*     */       }
/*     */       
/*     */ 
/*     */       break;
/*     */     }
/*     */     
/*     */     
/* 439 */     if (this.holdcost + this.shortagecost <= 0) {
/* 440 */       JOptionPane.showMessageDialog(this, "Invalid model because p + h <= 0.");
/* 441 */       return;
/*     */     }
/*     */     
/* 444 */     if (this.holdcost + this.purchasecost <= 0) {
/* 445 */       JOptionPane.showMessageDialog(this, "Invalid model because c + h <= 0.");
/* 446 */       return;
/*     */     }
/*     */     
/* 449 */     Vector p = new Vector();
/*     */     
/* 451 */     switch (this.whichtypeface) {
/*     */     case 1: 
/*     */     case 2: 
/*     */     case 3: 
/*     */     case 4: 
/* 456 */       this.lemta = Double.parseDouble(this.df_lemta.getText());
/* 457 */       if (this.lemta < 0.001D) {
/* 458 */         this.lemta = 0.001D;
/* 459 */         this.df_lemta.setText(String.valueOf(this.lemta));
/* 460 */         error = true;
/*     */       }
/* 462 */       p.addElement(new Double(this.lemta));
/* 463 */       p.addElement(new Double(this.purchasecost));
/* 464 */       p.addElement(new Double(this.holdcost));
/* 465 */       p.addElement(new Double(this.shortagecost));
/* 466 */       break;
/*     */     case 5: 
/*     */     case 6: 
/* 469 */       this.x0 = Double.parseDouble(this.df_x0.getText());
/* 470 */       if (this.x0 < 0) {
/* 471 */         this.x0 = 0.0D;
/* 472 */         this.df_x0.setText(String.valueOf(this.x0));
/* 473 */         error = true;
/*     */       }
/* 475 */       this.x1 = Double.parseDouble(this.df_x1.getText());
/* 476 */       if (this.x1 < 0) {
/* 477 */         this.x1 = 0.0D;
/* 478 */         this.df_x1.setText(String.valueOf(this.x1));
/* 479 */         error = true;
/*     */       }
/* 481 */       p.addElement(new Double(this.x0));
/* 482 */       p.addElement(new Double(this.x1));
/* 483 */       p.addElement(new Double(this.purchasecost));
/* 484 */       p.addElement(new Double(this.holdcost));
/* 485 */       p.addElement(new Double(this.shortagecost));
/* 486 */       break;
/*     */     case 7: 
/*     */     case 8: 
/* 489 */       this.t = Double.parseDouble(this.df_t.getText());
/* 490 */       p.addElement(new Double(this.t));
/* 491 */       p.addElement(new Double(this.purchasecost));
/* 492 */       p.addElement(new Double(this.holdcost));
/* 493 */       p.addElement(new Double(this.shortagecost));
/* 494 */       break;
/*     */     }
/*     */     
/*     */     
/* 498 */     switch (this.whichtypeface) {
/*     */     case 2: 
/*     */     case 6: 
/* 501 */       this.setupcost = Double.parseDouble(this.df_setupcostfield.getText());
/* 502 */       if (this.setupcost < 0) {
/* 503 */         this.setupcost = 0.0D;
/* 504 */         this.df_setupcostfield.setText(String.valueOf(this.setupcost));
/* 505 */         error = true;
/*     */       }
/* 507 */       p.addElement(new Double(this.setupcost));
/* 508 */       break;
/*     */     case 4: 
/*     */     case 8: 
/* 511 */       this.discount = Double.parseDouble(this.df_discountfield.getText());
/* 512 */       if (this.discount < 0.001D) {
/* 513 */         this.discount = 0.001D;
/* 514 */         this.df_discountfield.setText(String.valueOf(this.discount));
/* 515 */         error = true;
/*     */       }
/* 517 */       p.addElement(new Double(this.discount));
/* 518 */       break;
/*     */     }
/*     */     
/*     */     
/*     */ 
/* 523 */     IOROperation operation = null;
/*     */     
/* 525 */     switch (this.whichtypeface) {
/*     */     case 1: 
/* 527 */       operation = new IOROperation(30101, p);
/* 528 */       break;
/*     */     case 2: 
/* 530 */       operation = new IOROperation(30102, p);
/* 531 */       break;
/*     */     case 3: 
/* 533 */       operation = new IOROperation(30103, p);
/* 534 */       break;
/*     */     case 4: 
/* 536 */       operation = new IOROperation(30104, p);
/* 537 */       break;
/*     */     case 5: 
/* 539 */       operation = new IOROperation(30201, p);
/* 540 */       break;
/*     */     case 6: 
/* 542 */       operation = new IOROperation(30202, p);
/* 543 */       break;
/*     */     case 7: 
/* 545 */       operation = new IOROperation(30203, p);
/* 546 */       break;
/*     */     case 8: 
/* 548 */       operation = new IOROperation(30204, p);
/* 549 */       break;
/*     */     }
/*     */     
/*     */     
/* 553 */     if (error == true) {
/* 554 */       this.mainPanel.revalidate();
/* 555 */       this.mainPanel.repaint();
/* 556 */       return;
/*     */     }
/*     */     
/* 559 */     this.controller.data.lemta = this.lemta;
/* 560 */     this.controller.data.purchasecost = this.purchasecost;
/* 561 */     this.controller.data.holdcost = this.holdcost;
/* 562 */     this.controller.data.shortagecost = this.shortagecost;
/* 563 */     this.controller.data.setupcost = this.setupcost;
/* 564 */     this.controller.data.discount = this.discount;
/* 565 */     this.controller.data.x0 = this.x0;
/* 566 */     this.controller.data.x1 = this.x1;
/* 567 */     this.controller.data.t = this.t;
/*     */     
/* 569 */     if (this.controller.solver.doWork(operation, this.controller.data) == false)
/*     */     {
/* 571 */       JOptionPane.showMessageDialog(this, this.controller.solver.getErrInfo());
/*     */     }
/*     */     else {
/* 574 */       this.step = 2;
/*     */       
/*     */ 
/* 577 */       this.outputPanel.removeAll();
/* 578 */       this.outputPanel.setLayout(new BoxLayout(this.outputPanel, 1));
/* 579 */       JPanel resultPanel = new JPanel();
/* 580 */       resultPanel.setLayout(new BoxLayout(resultPanel, 1));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 585 */       switch (this.whichtypeface)
/*     */       {
/*     */       case 1: 
/*     */       case 4: 
/*     */       case 5: 
/*     */       case 8: 
/* 591 */         resultPanel.add(this.result);
/* 592 */         resultPanel.setAlignmentX(0.5F);
/* 593 */         this.y0equal.setFont(new Font("Default", 0, 18));
/* 594 */         this.y0Panel.removeAll();
/* 595 */         this.y0Panel.add(new ScriptWriter(this.y0equal, "0", ""));
/* 596 */         this.y0Panel.add(this.y00equal);
/* 597 */         if (this.controller.data.y0 == 0) this.controller.data.y0 = 0.0D;
/* 598 */         this.y0resultlabel.setText(this.keepthree.format(this.controller.data.y0));
/* 599 */         this.y0Panel.add(this.y0resultlabel);
/* 600 */         this.outputPanel.add(resultPanel);
/* 601 */         this.outputPanel.add(this.y0Panel);
/* 602 */         break;
/*     */       
/*     */ 
/*     */       case 2: 
/*     */       case 6: 
/* 607 */         if (this.controller.data.S == 0) this.controller.data.S = 0.0D;
/* 608 */         if (this.controller.data.s == 0) this.controller.data.s = 0.0D;
/* 609 */         this.Sequal.setText("S = ".concat(String.valueOf(String.valueOf(this.keepthree.format(this.controller.data.S)))));
/* 610 */         this.sequal.setText("s = ".concat(String.valueOf(String.valueOf(this.keepthree.format(this.controller.data.s)))));
/* 611 */         resultPanel.add(this.result);
/* 612 */         resultPanel.add(this.sequal);
/* 613 */         resultPanel.add(this.Sequal);
/* 614 */         this.outputPanel.add(resultPanel);
/* 615 */         break;
/*     */       
/*     */ 
/*     */       case 3: 
/*     */       case 7: 
/* 620 */         resultPanel.add(this.result);
/* 621 */         resultPanel.add(Box.createVerticalStrut(25));
/* 622 */         this.y10equal.setFont(new Font("Default", 0, 18));
/* 623 */         this.y10Panel.removeAll();
/* 624 */         this.y10Panel.add(new ScriptWriter(this.y10equal, "0", "1"));
/* 625 */         this.y10Panel.add(this.y100equal);
/* 626 */         if (this.controller.data.y10 == 0) this.controller.data.y10 = 0.0D;
/* 627 */         this.y10resultlabel.setText(this.keepthree.format(this.controller.data.y10));
/* 628 */         this.y10Panel.add(this.y10resultlabel);
/*     */         
/*     */ 
/*     */ 
/* 632 */         this.y20equal.setFont(new Font("Default", 0, 18));
/* 633 */         this.y20Panel.removeAll();
/* 634 */         this.y20Panel.add(new ScriptWriter(this.y20equal, "0", "2"));
/* 635 */         this.y20Panel.add(this.y200equal);
/* 636 */         if (this.controller.data.y20 == 0) this.controller.data.y20 = 0.0D;
/* 637 */         this.y20resultlabel.setText(this.keepthree.format(this.controller.data.y20));
/* 638 */         this.y20Panel.add(this.y20resultlabel);
/*     */         
/*     */ 
/* 641 */         resultPanel.add(this.y10Panel);
/* 642 */         resultPanel.add(this.y20Panel);
/* 643 */         this.outputPanel.add(resultPanel);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 648 */         break;
/*     */       }
/*     */       
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 656 */     this.outputPanel.setAlignmentX(0.5F);
/*     */     
/* 658 */     this.actionStatus.setText("The current procedure is finished. Please go to the Procedure menu to continue!");
/* 659 */     this.mainPanel.revalidate();
/* 660 */     this.mainPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/* 670 */     double[] para = new double[9];
/*     */     try
/*     */     {
/* 673 */       this.step = ((Integer)in.readObject()).intValue();
/* 674 */       para = (double[])in.readObject();
/* 675 */       in.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 678 */       e1.printStackTrace();
/* 679 */       System.out.println("Open fails");
/*     */     }
/*     */     
/* 682 */     this.controller.data.lemta = para[0];
/* 683 */     this.controller.data.purchasecost = para[1];
/* 684 */     this.controller.data.holdcost = para[2];
/* 685 */     this.controller.data.shortagecost = para[3];
/* 686 */     this.controller.data.setupcost = para[4];
/* 687 */     this.controller.data.discount = para[5];
/* 688 */     this.controller.data.x0 = para[6];
/* 689 */     this.controller.data.x1 = para[7];
/* 690 */     this.controller.data.t = para[8];
/*     */     
/* 692 */     this.df_lemta.setValue(para[0]);
/* 693 */     this.df_purchasecost.setValue(para[1]);
/* 694 */     this.df_holdcost.setValue(para[2]);
/* 695 */     this.df_shortagecost.setValue(para[3]);
/* 696 */     this.df_setupcostfield.setValue(para[4]);
/* 697 */     this.df_discountfield.setValue(para[5]);
/* 698 */     this.df_x0.setValue(para[6]);
/* 699 */     this.df_x1.setValue(para[7]);
/* 700 */     this.df_t.setValue(para[8]);
/*     */     
/* 702 */     if (this.step == 2) {
/* 703 */       finishProcedure();
/*     */     }
/*     */     
/* 706 */     updateHelp();
/*     */     
/* 708 */     revalidate();
/* 709 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/* 719 */     double[] interpara = new double[9];
/*     */     
/* 721 */     interpara[0] = this.df_lemta.getValue();
/* 722 */     interpara[1] = this.df_purchasecost.getValue();
/* 723 */     interpara[2] = this.df_holdcost.getValue();
/* 724 */     interpara[3] = this.df_shortagecost.getValue();
/* 725 */     interpara[4] = this.df_setupcostfield.getValue();
/* 726 */     interpara[5] = this.df_discountfield.getValue();
/* 727 */     interpara[6] = this.df_x0.getValue();
/* 728 */     interpara[7] = this.df_x1.getValue();
/* 729 */     interpara[8] = this.df_t.getValue();
/*     */     try
/*     */     {
/* 732 */       out.writeObject(new Integer(this.step));
/* 733 */       out.writeObject(interpara);
/* 734 */       out.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 737 */       e1.printStackTrace();
/* 738 */       System.out.println("Save fails");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 747 */     String str = "Current\nStep\nHelp\nFor ITActionPanel";
/* 748 */     this.help_content_step.setText(str);
/* 749 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 756 */     String str = "Current\nProcedure\nHelp\nFor ITActionPanel";
/* 757 */     this.help_content_procedure.setText(str);
/* 758 */     this.help_content_procedure.revalidate(); }
/*     */   
/* 760 */   private String sstr1 = "Single-Period Model With No Setup Costs and Linear Shortage and Holding Costs\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 766 */   private String str1 = "Entering the exponential distribution parameter\n\n";
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
/* 782 */   private String str01 = "The solution\n\n";
/*     */   
/*     */ 
/* 785 */   private String str11 = "Single-Period Model with No Setup Cost \n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 793 */   private String sstr2 = "Single-Period Model with Setup Cost and Linear Shortage and Holding Costs\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 799 */   private String str2 = "Entering the exponential distribution parameter\n\n";
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
/* 818 */   private String str02 = "The solution\n\n";
/*     */   
/*     */ 
/*     */ 
/* 822 */   private String str21 = "Single-Period Model with Setup Cost\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 830 */   private String sstr3 = " Two-Period Model with No Setup Cost and Linear Shortage and Holding Costs \n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 836 */   private String str3 = "Entering the exponential distribution parameter\n\n";
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
/* 852 */   private String str03 = "The solution\n\n";
/*     */   
/*     */ 
/* 855 */   private String str31 = "Two-Period Model with No Setup Cost and Linear Shortage and Holding Costs \n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 863 */   private String sstr4 = "Infinite Period Model with No Setup Cost\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 868 */   private String str4 = "Entering the exponential distribution parameter.\n\n ";
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
/* 887 */   private String str04 = "The solution\n\n";
/*     */   
/*     */ 
/* 890 */   private String str41 = "Infinite-Period Model with No Setup Cost\n\n";
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
/*     */   protected void updateStepHelpPanel()
/*     */   {
/* 905 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/*     */     
/* 907 */     switch (this.whichtypeface) {
/*     */     case 1: 
/*     */     case 5: 
/* 910 */       switch (this.step) {
/*     */       case 1: 
/* 912 */         this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.sstr1))).append(this.str1).append(str))));
/* 913 */         this.help_content_procedure.setText(String.valueOf(String.valueOf(this.str11)).concat(String.valueOf(String.valueOf(str))));
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
/* 966 */         break;
/*     */       case 2: 
/* 916 */         this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.sstr1))).append(this.str01).append(str))));
/* 917 */         this.help_content_procedure.setText(String.valueOf(String.valueOf(this.str11)).concat(String.valueOf(String.valueOf(str))));
/*     */       }
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
/* 966 */       break;
/*     */     case 2: 
/*     */     case 6: 
/* 923 */       switch (this.step) {
/*     */       case 1: 
/* 925 */         this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.sstr2))).append(this.str2).append(str))));
/* 926 */         this.help_content_procedure.setText(String.valueOf(String.valueOf(this.str21)).concat(String.valueOf(String.valueOf(str))));
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
/* 966 */         break;
/*     */       case 2: 
/* 929 */         this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.sstr2))).append(this.str02).append(str))));
/* 930 */         this.help_content_procedure.setText(String.valueOf(String.valueOf(this.str21)).concat(String.valueOf(String.valueOf(str))));
/*     */       }
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
/* 966 */       break;
/*     */     case 3: 
/*     */     case 7: 
/* 936 */       switch (this.step) {
/*     */       case 1: 
/* 938 */         this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.sstr3))).append(this.str3).append(str))));
/* 939 */         this.help_content_procedure.setText(String.valueOf(String.valueOf(this.str31)).concat(String.valueOf(String.valueOf(str))));
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
/* 966 */         break;
/*     */       case 2: 
/* 942 */         this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.sstr3))).append(this.str03).append(str))));
/* 943 */         this.help_content_procedure.setText(String.valueOf(String.valueOf(this.str31)).concat(String.valueOf(String.valueOf(str))));
/*     */       }
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
/* 966 */       break;
/*     */     case 4: 
/*     */     case 8: 
/* 949 */       switch (this.step) {
/*     */       case 1: 
/* 951 */         this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.sstr4))).append(this.str4).append(str))));
/* 952 */         this.help_content_procedure.setText(String.valueOf(String.valueOf(this.str41)).concat(String.valueOf(String.valueOf(str))));
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
/* 966 */         break;
/*     */       case 2: 
/* 955 */         this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.sstr4))).append(this.str04).append(str))));
/* 956 */         this.help_content_procedure.setText(String.valueOf(String.valueOf(this.str41)).concat(String.valueOf(String.valueOf(str))));
/*     */       }
/*     */       
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 966 */       break;
/*     */     default: 
/* 962 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str4)).concat(String.valueOf(String.valueOf(str))));
/* 963 */       this.help_content_procedure.setText(String.valueOf(String.valueOf(this.str41)).concat(String.valueOf(String.valueOf(str))));
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void updateHelp()
/*     */   {
/* 972 */     updateStepHelpPanel();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORITActionPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */