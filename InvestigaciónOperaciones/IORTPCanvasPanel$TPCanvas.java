/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Point;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORTPCanvasPanel$TPCanvas
/*     */   extends JPanel
/*     */ {
/*     */   private IORTPCanvasPanel.TPCell[][] cell;
/*     */   private ButtonGroup group;
/*     */   private JLabel[] supply;
/*     */   private JLabel[] demand;
/*     */   private JTextField[] u;
/*     */   private JTextField[] v;
/*     */   private MultilineLabel tf_Z;
/*     */   private MultilineLabel tf_result;
/*     */   private final IORTPCanvasPanel this$0;
/*     */   
/*     */   public IORTPCanvasPanel$TPCanvas(IORTPCanvasPanel this$0)
/*     */   {
/* 331 */     this.this$0 = this$0;this.cell = null;this.group = null;this.supply = null;this.demand = null;this.u = null;this.v = null;this.tf_Z = null;this.tf_result = null;
/*     */     
/*     */ 
/* 334 */     setLayout(new GridLayout(IORTPCanvasPanel.access$5(this$0) + 2, IORTPCanvasPanel.access$6(this$0) + 3));
/*     */     
/* 336 */     this.cell = new IORTPCanvasPanel.TPCell[IORTPCanvasPanel.access$5(this$0)][IORTPCanvasPanel.access$6(this$0)];
/*     */     
/* 338 */     this.supply = new JLabel[IORTPCanvasPanel.access$5(this$0)];
/* 339 */     this.demand = new JLabel[IORTPCanvasPanel.access$6(this$0)];
/*     */     
/* 341 */     this.u = new JTextField[IORTPCanvasPanel.access$5(this$0)];
/* 342 */     this.v = new JTextField[IORTPCanvasPanel.access$6(this$0)];
/*     */     
/*     */ 
/* 345 */     JLabel lb = new JLabel();
/* 346 */     lb.setEnabled(false);
/* 347 */     add(lb);
/*     */     
/* 349 */     setDoubleBuffered(false);
/* 350 */     JPanel p = null;
/*     */     
/* 352 */     int i = -1;int j = -1;
/* 353 */     for (j = 1; j <= IORTPCanvasPanel.access$6(this$0); j++) {
/* 354 */       p = new JPanel(new GridLayout(2, 1));
/* 355 */       p.setDoubleBuffered(false);
/* 356 */       p.add(new JLabel());
/* 357 */       lb = new JLabel("D".concat(String.valueOf(String.valueOf(j))));
/* 358 */       lb.setHorizontalAlignment(0);
/* 359 */       lb.setEnabled(false);
/* 360 */       p.add(lb);
/* 361 */       add(p);
/*     */     }
/*     */     
/* 364 */     p = new JPanel(new BorderLayout());
/* 365 */     p.setDoubleBuffered(false);
/* 366 */     switch (IORTPCanvasPanel.access$7(this$0)) {
/*     */     case 0: 
/* 368 */       MultilineLabel mll = new MultilineLabel("\nRemain\nSupply");
/* 369 */       mll.setEnabled(false);
/* 370 */       p.add(mll, "Center");
/* 371 */       break;
/*     */     
/*     */     case 1: 
/* 374 */       p = new JPanel(new GridLayout(2, 1));
/* 375 */       p.setDoubleBuffered(false);
/* 376 */       p.add(new JLabel());
/* 377 */       p.add(lb = new JLabel("Supply"));
/* 378 */       lb.setEnabled(false);
/* 379 */       break;
/*     */     
/*     */     default: 
/* 382 */       System.err.println("Error in setting up Supply labels");
/*     */     }
/* 384 */     add(p);
/*     */     
/* 386 */     p = new JPanel(new GridLayout(2, 1));
/* 387 */     p.setDoubleBuffered(false);
/* 388 */     p.add(new JLabel());
/* 389 */     IORTPCanvasPanel.access$8(this$0, new JLabel("u(i)"));
/* 390 */     IORTPCanvasPanel.access$9(this$0).setHorizontalAlignment(2);
/* 391 */     IORTPCanvasPanel.access$9(this$0).setEnabled(false);
/* 392 */     p.add(IORTPCanvasPanel.access$9(this$0));
/* 393 */     add(p);
/*     */     
/*     */ 
/* 396 */     double r = -1.0D;
/*     */     
/* 398 */     for (i = 1; i <= IORTPCanvasPanel.access$5(this$0); i++) {
/* 399 */       p = new JPanel(new GridLayout(1, 2));
/* 400 */       p.setDoubleBuffered(false);
/* 401 */       p.add(new JLabel());
/* 402 */       lb = new JLabel("S".concat(String.valueOf(String.valueOf(i))));
/* 403 */       lb.setEnabled(false);
/* 404 */       p.add(lb);
/* 405 */       add(p);
/*     */       
/* 407 */       this.group = new ButtonGroup();
/* 408 */       for (j = 0; j < IORTPCanvasPanel.access$6(this$0); j++) {
/* 409 */         IORTPCanvasPanel tmp615_614 = this$0;tmp615_614.getClass();this.cell[(i - 1)][j] = new IORTPCanvasPanel.TPCell(tmp615_614, i - 1, j, null);
/* 410 */         this.group.add(this.cell[(i - 1)][j]);
/* 411 */         add(this.cell[(i - 1)][j]);
/*     */       }
/* 413 */       String str = "0";
/* 414 */       this.supply[(i - 1)] = new JLabel();
/* 415 */       this.supply[(i - 1)].setText(str);
/* 416 */       this.supply[(i - 1)].setHorizontalAlignment(0);
/* 417 */       add(this.supply[(i - 1)]);
/*     */       
/* 419 */       this.u[(i - 1)] = new JTextField(str);
/* 420 */       this.u[(i - 1)].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/* 421 */       this.u[(i - 1)].setBackground(IORTPCanvasPanel.access$10(this$0));
/* 422 */       add(this.u[(i - 1)]);
/*     */     }
/*     */     
/*     */ 
/* 426 */     p = new JPanel(new GridLayout(2, 1));
/* 427 */     p.setDoubleBuffered(false);
/* 428 */     switch (IORTPCanvasPanel.access$7(this$0)) {
/*     */     case 0: 
/* 430 */       lb = new JLabel("R.D.");
/* 431 */       break;
/*     */     
/*     */     case 1: 
/* 434 */       lb = new JLabel("Demand");
/* 435 */       break;
/*     */     
/*     */     default: 
/* 438 */       System.err.println("Error in setting up Demand labels");
/*     */     }
/*     */     
/* 441 */     lb.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
/* 442 */     lb.setHorizontalAlignment(4);
/* 443 */     lb.setEnabled(false);
/* 444 */     p.add(lb);
/*     */     
/* 446 */     IORTPCanvasPanel.access$11(this$0, new JLabel("v(j)"));
/* 447 */     IORTPCanvasPanel.access$12(this$0).setHorizontalAlignment(0);
/* 448 */     IORTPCanvasPanel.access$12(this$0).setEnabled(false);
/* 449 */     p.add(IORTPCanvasPanel.access$12(this$0));
/*     */     
/* 451 */     add(p);
/*     */     
/* 453 */     for (j = 1; j <= IORTPCanvasPanel.access$6(this$0); j++) {
/* 454 */       String str = "0";
/* 455 */       this.demand[(j - 1)] = new JLabel();
/* 456 */       this.demand[(j - 1)].setHorizontalAlignment(0);
/* 457 */       this.demand[(j - 1)].setText(str);
/*     */       
/* 459 */       this.v[(j - 1)] = new JTextField(str);
/* 460 */       this.v[(j - 1)].setBackground(IORTPCanvasPanel.access$10(this$0));
/* 461 */       this.v[(j - 1)].setHorizontalAlignment(0);
/* 462 */       this.v[(j - 1)].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*     */       
/* 464 */       p = new JPanel(new GridLayout(2, 1));
/* 465 */       p.setDoubleBuffered(false);
/* 466 */       p.add(this.demand[(j - 1)]);
/* 467 */       p.add(this.v[(j - 1)]);
/*     */       
/* 469 */       add(p);
/*     */     }
/*     */     
/* 472 */     switch (IORTPCanvasPanel.access$7(this$0)) {
/*     */     case 0: 
/* 474 */       lb = new JLabel();
/* 475 */       lb.setEnabled(false);
/* 476 */       add(lb);
/* 477 */       break;
/*     */     
/*     */     case 1: 
/* 480 */       this.tf_Z = new MultilineLabel("\n Z = \n ");
/* 481 */       this.tf_Z.setEditable(false);
/* 482 */       this.tf_Z.setForeground(Color.red);
/*     */       
/* 484 */       this.tf_Z.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/* 485 */       add(this.tf_Z);
/*     */       
/*     */ 
/* 488 */       this.tf_result = new MultilineLabel("100");
/* 489 */       this.tf_result.setEditable(false);
/* 490 */       this.tf_result.setForeground(Color.red);
/*     */       
/* 492 */       this.tf_result.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/* 493 */       add(this.tf_result);
/* 494 */       break;
/*     */     default: 
/* 496 */       System.out.println("Wrong state input in canvas constructor");
/*     */     }
/*     */     
/* 499 */     updatePanelForOption();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void updatePanelForOption()
/*     */   {
/* 506 */     String str = "";
/*     */     
/* 508 */     if (IORTPCanvasPanel.access$7(this.this$0) == 0) {
/* 509 */       switch (IORTPCanvasPanel.access$13(this.this$0).getOption()) {
/*     */       case 1: 
/* 511 */         IORTPCanvasPanel.access$9(this.this$0).setText("row dif.");
/* 512 */         IORTPCanvasPanel.access$12(this.this$0).setText("col. dif.");
/* 513 */         break;
/*     */       case 2: 
/* 515 */         IORTPCanvasPanel.access$9(this.this$0).setText("U(I)");
/* 516 */         IORTPCanvasPanel.access$12(this.this$0).setText("V(J)");
/* 517 */         break;
/*     */       }
/*     */       
/*     */     }
/* 521 */     for (int i = 1; i <= IORTPCanvasPanel.access$5(this.this$0); i++) {
/* 522 */       switch (IORTPCanvasPanel.access$7(this.this$0)) {
/*     */       case 0: 
/* 524 */         str = IORTPActionPanel.getCellString(IORTPCanvasPanel.access$13(this.this$0).data.u[(i - 1)], IORTPCanvasPanel.access$13(this.this$0).data.M_u[(i - 1)]);
/* 525 */         this.u[(i - 1)].setText(str);
/* 526 */         IORTPCanvasPanel.access$13(this.this$0); if ((IORTPCanvasPanel.access$13(this.this$0).getOption() == 1) && 
/* 527 */           (isRowDeleted(i - 1))) {
/* 528 */           this.u[(i - 1)].setText(" ");
/*     */         }
/* 530 */         str = "".concat(String.valueOf(String.valueOf(IORTPCanvasPanel.access$13(this.this$0).data.remain_supply[(i - 1)])));
/* 531 */         break;
/*     */       case 1: 
/* 533 */         str = "".concat(String.valueOf(String.valueOf(IORTPCanvasPanel.access$13(this.this$0).data.supply[(i - 1)])));
/* 534 */         break;
/*     */       }
/* 536 */       this.supply[(i - 1)].setText(IORActionPanel.trim(str));
/*     */     }
/* 538 */     for (int j = 1; j <= IORTPCanvasPanel.access$6(this.this$0); j++) {
/* 539 */       switch (IORTPCanvasPanel.access$7(this.this$0)) {
/*     */       case 0: 
/* 541 */         str = IORTPActionPanel.getCellString(IORTPCanvasPanel.access$13(this.this$0).data.v[(j - 1)], IORTPCanvasPanel.access$13(this.this$0).data.M_v[(j - 1)]);
/* 542 */         this.v[(j - 1)].setText(str);
/* 543 */         IORTPCanvasPanel.access$13(this.this$0); if ((IORTPCanvasPanel.access$13(this.this$0).getOption() == 1) && 
/* 544 */           (isColumnDeleted(j - 1))) {
/* 545 */           this.v[(j - 1)].setText(" ");
/*     */         }
/* 547 */         str = "".concat(String.valueOf(String.valueOf(IORTPCanvasPanel.access$13(this.this$0).data.remain_demand[(j - 1)])));
/* 548 */         break;
/*     */       case 1: 
/* 550 */         str = "".concat(String.valueOf(String.valueOf(IORTPCanvasPanel.access$13(this.this$0).data.demand[(j - 1)])));
/* 551 */         break;
/*     */       }
/* 553 */       this.demand[(j - 1)].setText(IORActionPanel.trim(str));
/*     */     }
/*     */     
/* 556 */     if (IORTPCanvasPanel.access$7(this.this$0) == 1) {
/* 557 */       str = "";
/* 558 */       if (this.tf_result.isVisible()) {
/* 559 */         if ((Math.abs(IORTPCanvasPanel.access$13(this.this$0).data.M_z) > 1.0E-6D) && (Math.abs(IORTPCanvasPanel.access$13(this.this$0).data.z) > 1.0E-6D)) {
/* 560 */           str = "\n ".concat(String.valueOf(String.valueOf(IORTPCanvasPanel.access$13(this.this$0).data.M_z)));
/* 561 */           str = String.valueOf(String.valueOf(IORActionPanel.trim(str))).concat("M");
/* 562 */           if (IORTPCanvasPanel.access$13(this.this$0).data.z > 0) {
/* 563 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("\n+ ".concat(String.valueOf(String.valueOf(IORTPCanvasPanel.access$13(this.this$0).data.z))))));
/*     */           } else {
/* 565 */             str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("\n- ".concat(String.valueOf(String.valueOf(IORTPCanvasPanel.access$13(this.this$0).data.z))))));
/*     */           }
/*     */         } else {
/* 568 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("\n ".concat(String.valueOf(String.valueOf(IORTPActionPanel.getCellString(IORTPCanvasPanel.access$13(this.this$0).data.z, IORTPCanvasPanel.access$13(this.this$0).data.M_z)))))));
/* 569 */           str = String.valueOf(String.valueOf(str)).concat("\n ");
/*     */         }
/* 571 */         this.tf_result.setText(IORActionPanel.trim(str));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isRowDeleted(int r)
/*     */   {
/* 578 */     boolean is_deleted = true;
/* 579 */     for (int j = 0; j < IORTPCanvasPanel.access$6(this.this$0); j++) {
/* 580 */       if (IORTPCanvasPanel.access$13(this.this$0).data.is_deleted[r][j] == 0) {
/* 581 */         is_deleted = false;
/* 582 */         break;
/*     */       }
/*     */     }
/* 585 */     return is_deleted;
/*     */   }
/*     */   
/*     */   private boolean isColumnDeleted(int c)
/*     */   {
/* 590 */     boolean is_deleted = true;
/* 591 */     for (int i = 0; i < IORTPCanvasPanel.access$5(this.this$0); i++) {
/* 592 */       if (IORTPCanvasPanel.access$13(this.this$0).data.is_deleted[i][c] == 0) {
/* 593 */         is_deleted = false;
/* 594 */         break;
/*     */       }
/*     */     }
/* 597 */     return is_deleted;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int deleteRow()
/*     */   {
/* 604 */     if (IORTPCanvasPanel.access$14(this.this$0).getStep() != 2) {
/* 605 */       return -2;
/*     */     }
/* 607 */     if (IORTPCanvasPanel.access$15(this.this$0) == null) {
/* 608 */       return -2;
/*     */     }
/* 610 */     if (IORTPCanvasPanel.TPCell.access$16(IORTPCanvasPanel.access$15(this.this$0))) {
/* 611 */       int row = IORTPCanvasPanel.access$15(this.this$0).getRow();
/*     */       
/* 613 */       Vector p = new Vector();
/*     */       
/* 615 */       p.addElement(new Integer(row));
/*     */       
/* 617 */       IOROperation operation = new IOROperation(20202, p);
/*     */       
/*     */ 
/* 620 */       if (IORTPCanvasPanel.access$13(this.this$0).solver.doWork(operation, IORTPCanvasPanel.access$13(this.this$0).data)) {
/* 621 */         IORTPCanvasPanel.access$17(this.this$0).addStep(operation);
/* 622 */         IORTPCanvasPanel.access$14(this.this$0).setStep(1);
/* 623 */         IORTPCanvasPanel.access$14(this.this$0).addStep();
/* 624 */         IORTPCanvasPanel.access$14(this.this$0).updatePanel();
/*     */       }
/*     */       else {
/* 627 */         JOptionPane.showMessageDialog(this, IORTPCanvasPanel.access$13(this.this$0).solver.getErrInfo());
/*     */       }
/*     */       
/* 630 */       repaint();
/* 631 */       return row + 1;
/*     */     }
/*     */     
/* 634 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void resetLoop()
/*     */   {
/* 641 */     int i = -1;int j = -1;
/* 642 */     for (i = 0; i < IORTPCanvasPanel.access$5(this.this$0); i++) {
/* 643 */       for (j = 0; j < IORTPCanvasPanel.access$6(this.this$0); j++) {
/* 644 */         IORTPCanvasPanel.TPCell.access$18(this.cell[i][j]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateLoop() {
/* 650 */     resetLoop();
/* 651 */     updateCellLoopState();
/*     */   }
/*     */   
/*     */   private void setResultVisible(boolean b)
/*     */   {
/* 656 */     if (this.tf_Z != null) {
/* 657 */       this.tf_Z.setVisible(b);
/*     */     }
/* 659 */     if (this.tf_result != null) {
/* 660 */       this.tf_result.setVisible(b);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateCellLoopState() {
/* 665 */     int n = IORTPCanvasPanel.access$13(this.this$0).data.loop.size();
/* 666 */     for (int i = 1; i < n; i++) {
/* 667 */       IORTPCanvasPanel.TPCell start = getLoopNode(i - 1);
/* 668 */       IORTPCanvasPanel.TPCell end = getLoopNode(i);
/* 669 */       setLoopSegment(start, end);
/*     */     }
/*     */     
/* 672 */     for (int i = 0; i < n; i++) {
/* 673 */       IORTPCanvasPanel.TPCell node = getLoopNode(i);
/* 674 */       IORTPCanvasPanel.TPCell.access$19(node, true);
/* 675 */       if (i % 2 == 1) {
/* 676 */         IORTPCanvasPanel.TPCell.access$20(node, false);
/*     */       } else {
/* 678 */         IORTPCanvasPanel.TPCell.access$20(node, true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private IORTPCanvasPanel.TPCell getLoopNode(int i)
/*     */   {
/* 686 */     if (IORTPCanvasPanel.access$13(this.this$0).data.loop.size() > i)
/*     */     {
/* 688 */       Point p = (Point)IORTPCanvasPanel.access$13(this.this$0).data.loop.elementAt(i);
/* 689 */       return this.cell[p.x][p.y];
/*     */     }
/*     */     
/* 692 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void buildLoop(IORTPCanvasPanel.TPCell node)
/*     */   {
/* 699 */     IORTPCanvasPanel.TPCell.access$19(node, true);
/*     */     
/*     */ 
/*     */ 
/* 703 */     int n = IORTPCanvasPanel.access$13(this.this$0).data.loop.size();
/* 704 */     if (n % 2 == 0) {
/* 705 */       IORTPCanvasPanel.TPCell.access$20(node, false);
/*     */     } else {
/* 707 */       IORTPCanvasPanel.TPCell.access$20(node, true);
/*     */     }
/* 709 */     if (n > 1) {
/* 710 */       IORTPCanvasPanel.TPCell start = getLoopNode(n - 2);
/* 711 */       IORTPCanvasPanel.TPCell end = getLoopNode(n - 1);
/* 712 */       setLoopSegment(start, end);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setLoopSegment(IORTPCanvasPanel.TPCell start, IORTPCanvasPanel.TPCell end)
/*     */   {
/* 718 */     int i = -1;
/* 719 */     switch (getSegmentDirection(start, end)) {
/*     */     case 4: 
/* 721 */       IORTPCanvasPanel.TPCell.access$23(this.cell[IORTPCanvasPanel.TPCell.access$21(start)][IORTPCanvasPanel.TPCell.access$22(start)], 4);
/* 722 */       IORTPCanvasPanel.TPCell.access$23(this.cell[IORTPCanvasPanel.TPCell.access$21(end)][IORTPCanvasPanel.TPCell.access$22(end)], 5);
/* 723 */       for (i = IORTPCanvasPanel.TPCell.access$21(start) + 1; i <= IORTPCanvasPanel.TPCell.access$21(end) - 1;) {
/* 724 */         IORTPCanvasPanel.TPCell.access$23(this.cell[i][IORTPCanvasPanel.TPCell.access$22(start)], 0);i++; continue;
/*     */         
/*     */ 
/*     */ 
/* 728 */         IORTPCanvasPanel.TPCell.access$23(this.cell[IORTPCanvasPanel.TPCell.access$21(start)][IORTPCanvasPanel.TPCell.access$22(start)], 5);
/* 729 */         IORTPCanvasPanel.TPCell.access$23(this.cell[IORTPCanvasPanel.TPCell.access$21(end)][IORTPCanvasPanel.TPCell.access$22(end)], 4);
/* 730 */         for (i = IORTPCanvasPanel.TPCell.access$21(end) + 1; i <= IORTPCanvasPanel.TPCell.access$21(start) - 1;) {
/* 731 */           IORTPCanvasPanel.TPCell.access$23(this.cell[i][IORTPCanvasPanel.TPCell.access$22(start)], 0);i++; continue;
/*     */           
/*     */ 
/*     */ 
/* 735 */           IORTPCanvasPanel.TPCell.access$23(this.cell[IORTPCanvasPanel.TPCell.access$21(start)][IORTPCanvasPanel.TPCell.access$22(start)], 2);
/* 736 */           IORTPCanvasPanel.TPCell.access$23(this.cell[IORTPCanvasPanel.TPCell.access$21(end)][IORTPCanvasPanel.TPCell.access$22(end)], 3);
/* 737 */           for (i = IORTPCanvasPanel.TPCell.access$22(end) + 1; i <= IORTPCanvasPanel.TPCell.access$22(start) - 1;) {
/* 738 */             IORTPCanvasPanel.TPCell.access$23(this.cell[IORTPCanvasPanel.TPCell.access$21(start)][i], 1);i++; continue;
/*     */             
/*     */ 
/*     */ 
/* 742 */             IORTPCanvasPanel.TPCell.access$23(this.cell[IORTPCanvasPanel.TPCell.access$21(start)][IORTPCanvasPanel.TPCell.access$22(start)], 3);
/* 743 */             IORTPCanvasPanel.TPCell.access$23(this.cell[IORTPCanvasPanel.TPCell.access$21(end)][IORTPCanvasPanel.TPCell.access$22(end)], 2);
/* 744 */             for (i = IORTPCanvasPanel.TPCell.access$22(start) + 1; i <= IORTPCanvasPanel.TPCell.access$22(end) - 1; i++) {
/* 745 */               IORTPCanvasPanel.TPCell.access$23(this.cell[IORTPCanvasPanel.TPCell.access$21(start)][i], 1);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */   private int getSegmentDirection(IORTPCanvasPanel.TPCell start, IORTPCanvasPanel.TPCell end)
/*     */   {
/* 757 */     if ((IORTPCanvasPanel.TPCell.access$21(start) > IORTPCanvasPanel.TPCell.access$21(end)) && (IORTPCanvasPanel.TPCell.access$22(start) == IORTPCanvasPanel.TPCell.access$22(end))) {
/* 758 */       return 5;
/*     */     }
/* 760 */     if ((IORTPCanvasPanel.TPCell.access$21(start) < IORTPCanvasPanel.TPCell.access$21(end)) && (IORTPCanvasPanel.TPCell.access$22(start) == IORTPCanvasPanel.TPCell.access$22(end))) {
/* 761 */       return 4;
/*     */     }
/* 763 */     if ((IORTPCanvasPanel.TPCell.access$21(start) == IORTPCanvasPanel.TPCell.access$21(end)) && (IORTPCanvasPanel.TPCell.access$22(start) > IORTPCanvasPanel.TPCell.access$22(end))) {
/* 764 */       return 2;
/*     */     }
/* 766 */     if ((IORTPCanvasPanel.TPCell.access$21(start) == IORTPCanvasPanel.TPCell.access$21(end)) && (IORTPCanvasPanel.TPCell.access$22(start) < IORTPCanvasPanel.TPCell.access$22(end))) {
/* 767 */       return 3;
/*     */     }
/* 769 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean isValidNode(IORTPCanvasPanel.TPCell node)
/*     */   {
/* 775 */     boolean valid = true;
/*     */     
/*     */ 
/* 778 */     if (!IORTPCanvasPanel.access$13(this.this$0).data.loop.isEmpty())
/*     */     {
/* 780 */       int n = IORTPCanvasPanel.access$13(this.this$0).data.loop.size();
/* 781 */       IORTPCanvasPanel.TPCell last = getLoopNode(n - 1);
/*     */       
/* 783 */       if (last.equals(node)) {
/* 784 */         valid = false;
/*     */       }
/* 786 */       else if ((IORTPCanvasPanel.TPCell.access$21(last) == IORTPCanvasPanel.TPCell.access$21(node)) || (IORTPCanvasPanel.TPCell.access$22(last) == IORTPCanvasPanel.TPCell.access$22(node))) {
/* 787 */         valid = true;
/*     */       } else {
/* 789 */         valid = false;
/*     */       }
/*     */     }
/*     */     
/* 793 */     return valid;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int deleteColumn()
/*     */   {
/* 800 */     if (IORTPCanvasPanel.access$14(this.this$0).getStep() != 2) {
/* 801 */       return -2;
/*     */     }
/* 803 */     if (IORTPCanvasPanel.access$15(this.this$0) == null) {
/* 804 */       return -2;
/*     */     }
/* 806 */     if (IORTPCanvasPanel.TPCell.access$16(IORTPCanvasPanel.access$15(this.this$0))) {
/* 807 */       int column = IORTPCanvasPanel.access$15(this.this$0).getColumn();
/* 808 */       Vector p = new Vector();
/*     */       
/* 810 */       p.addElement(new Integer(column));
/*     */       
/* 812 */       IOROperation operation = new IOROperation(20203, p);
/*     */       
/*     */ 
/*     */ 
/* 816 */       if (IORTPCanvasPanel.access$13(this.this$0).solver.doWork(operation, IORTPCanvasPanel.access$13(this.this$0).data)) {
/* 817 */         IORTPCanvasPanel.access$17(this.this$0).addStep(operation);
/* 818 */         IORTPCanvasPanel.access$14(this.this$0).setStep(1);
/* 819 */         IORTPCanvasPanel.access$14(this.this$0).addStep();
/* 820 */         IORTPCanvasPanel.access$14(this.this$0).updatePanel();
/*     */       }
/*     */       else {
/* 823 */         JOptionPane.showMessageDialog(this, IORTPCanvasPanel.access$13(this.this$0).solver.getErrInfo());
/*     */       }
/*     */       
/* 826 */       repaint();
/* 827 */       return column + 1;
/*     */     }
/* 829 */     return -1;
/*     */   }
/*     */   
/*     */   public void getSelCell(int[] sel) {
/* 833 */     if (IORTPCanvasPanel.access$15(this.this$0) == null) {
/* 834 */       sel[0] = -1;
/* 835 */       sel[1] = -1;
/*     */     }
/*     */     else {
/* 838 */       sel[0] = IORTPCanvasPanel.access$15(this.this$0).getRow();
/* 839 */       sel[1] = IORTPCanvasPanel.access$15(this.this$0).getColumn();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setSelCell(int row, int column)
/*     */   {
/* 845 */     IORTPCanvasPanel.TPCell.access$24(this.cell[row][column]);
/*     */   }
/*     */   
/*     */   public void setState() {
/* 849 */     IORTPCanvasPanel.access$25(this.this$0, IORTPCanvasPanel.access$14(this.this$0).getState());
/*     */   }
/*     */   
/*     */   public Vector getCellInnerVariables(int row, int col) {
/* 853 */     return this.cell[row][col].getInnerVariables();
/*     */   }
/*     */   
/*     */   public void setCellInnerVariables(int row, int col, Vector v) {
/* 857 */     this.cell[row][col].setInnerVariables(v);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTPCanvasPanel$TPCanvas.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */