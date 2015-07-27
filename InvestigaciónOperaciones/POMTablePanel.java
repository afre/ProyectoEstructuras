/*     */ import java.awt.Color;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.TextField;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import java.io.Serializable;
/*     */ import java.util.Vector;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class POMTablePanel
/*     */   extends JPanel
/*     */   implements Serializable
/*     */ {
/*  67 */   public static final int CELL_STRING_DATATYPE = 1;
/*     */   
/*  69 */   public static final int CELL_INT_DATATYPE = 2;
/*     */   
/*  71 */   public static final int CELL_FLOAT_DATATYPE = 3;
/*     */   
/*  73 */   public static final int CELL_NO_INPUT = 0;
/*     */   
/*     */   class Cell implements Serializable { String name;
/*     */     int x;
/*     */     int y;
/*     */     int width;
/*     */     int height;
/*     */     int y2;
/*     */     int[] x2;
/*     */     int row;
/*  83 */     int column; int datatype = 1;
/*  84 */     boolean canInput = false;
/*  85 */     boolean canClick = false;
/*  86 */     Color Background = Color.lightGray;
/*     */     
/*  88 */     public Cell(String s1, int row, int column) { this.name = s1;
/*  89 */       this.row = row;
/*  90 */       this.column = column;
/*     */     }
/*     */   }
/*     */   
/*  94 */   private Vector header = new Vector();
/*  95 */   private Vector data = new Vector();
/*     */   
/*     */ 
/*     */   private int[] CellWidth;
/*     */   
/*     */   private int CellHeight;
/*     */   
/*     */   private int headrow;
/*     */   
/*     */   private int row;
/*     */   
/*     */   private int column;
/*     */   
/*     */   private int SerialNum;
/*     */   
/*     */   private POMTableDataNotify applet;
/*     */   
/* 112 */   private boolean inited = false;
/*     */   private Image backbuffer;
/*     */   private Graphics g;
/* 115 */   private int fontHeight = 14;
/* 116 */   private int fontWidth = 6;
/*     */   private int width;
/*     */   private int height;
/*     */   private Color border;
/*     */   private Color text;
/*     */   private Color PanelBackground;
/*     */   private Color HeadBackground;
/* 123 */   private Color[] DataBackground; private int top; private int bottom; private int left; private int right; private TextField tf = new TextField();
/* 124 */   private boolean MouseFocus = false;
/* 125 */   private int movecurrent = -1;
/* 126 */   private int clickcurrent = -1;
/* 127 */   private int inputcurrent = -1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public POMTablePanel(POMTableDataNotify applet, int row, int col, int num)
/*     */   {
/* 135 */     this(row, col, num);
/* 136 */     this.applet = applet;
/*     */   }
/*     */   
/*     */   public POMTablePanel(int row, int col, int num) {
/*     */     try {
/* 141 */       jbInit();
/*     */     }
/*     */     catch (Exception e) {
/* 144 */       e.printStackTrace();
/*     */     }
/* 146 */     this.tf.addKeyListener(new KeyAdapter() {
/*     */       public void keyReleased(KeyEvent e) {
/* 148 */         POMTablePanel.this.dealWithValue(e);
/*     */       }
/* 150 */     });
/* 151 */     this.SerialNum = num;
/* 152 */     this.row = row;
/* 153 */     this.column = col;
/* 154 */     this.headrow = 1;
/* 155 */     this.CellWidth = new int[col];
/*     */   }
/*     */   
/*     */   public void addPOMTableListener(POMTableDataNotify applet) {
/* 159 */     this.applet = applet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHeader(String[] head)
/*     */   {
/* 170 */     if (head.length > this.column)
/* 171 */       return;
/* 172 */     int tcol = 0;
/* 173 */     for (int i = 0; i < head.length; i++)
/*     */     {
/* 175 */       String s = head[i];
/*     */       
/*     */       int pos2;
/* 178 */       int pos1 = pos2 = 0;
/*     */       int b;
/* 180 */       int a = b = 0;
/*     */       int max;
/* 182 */       int tmax = max = 0;
/*     */       
/*     */ 
/* 185 */       while ((pos2 = s.indexOf('\n', pos1)) != -1) {
/* 186 */         pos1 = pos2 + 1;
/* 187 */         a++;
/*     */       }
/* 189 */       while ((pos2 = s.indexOf('\t', pos1)) != -1) {
/* 190 */         pos1 = pos2 + 1;
/* 191 */         b++;
/*     */       }
/* 193 */       pos1 = pos2 = 0;
/* 194 */       if ((a == 0) && (b == 0)) {
/* 195 */         adjustColumnWidth(tcol, s.length());
/* 196 */         tcol++;
/* 197 */         POMTablePanel.Cell cell = new POMTablePanel.Cell(s, 1, 1);
/* 198 */         this.header.addElement(cell);
/*     */       }
/* 200 */       else if ((a != 0) && (b == 0)) {
/* 201 */         int row = 0;
/* 202 */         while ((pos2 = s.indexOf('\n', pos1)) != -1) {
/* 203 */           String str = s.substring(pos1, pos2);
/* 204 */           tmax = str.length() > tmax ? str.length() : tmax;
/* 205 */           row++;
/* 206 */           pos1 = pos2 + 1;
/*     */         }
/* 208 */         String str = s.substring(pos1);
/* 209 */         tmax = str.length() > tmax ? str.length() : tmax;
/* 210 */         row++;
/* 211 */         adjustColumnWidth(tcol, tmax);
/* 212 */         tcol++;
/* 213 */         POMTablePanel.Cell cell = new POMTablePanel.Cell(s, row, 1);
/* 214 */         this.header.addElement(cell);
/* 215 */         this.headrow = (this.headrow > row ? this.headrow : row);
/*     */       }
/* 217 */       else if ((a == 0) && (b != 0))
/*     */       {
/* 219 */         int col = 0;
/* 220 */         int row = 0;
/* 221 */         pos2 = s.indexOf('\t', pos1);
/* 222 */         String str = s.substring(pos1, pos2);
/* 223 */         tmax = str.length();
/*     */         
/* 225 */         pos1 = pos2 + 1;
/* 226 */         row++;
/* 227 */         while ((pos2 = s.indexOf('\t', pos1)) != -1) {
/* 228 */           str = s.substring(pos1, pos2);
/* 229 */           adjustColumnWidth(col++ + tcol, str.length());
/* 230 */           pos1 = pos2 + 1;
/*     */         }
/* 232 */         str = s.substring(pos1);
/* 233 */         adjustColumnWidth(col++ + tcol, str.length());
/* 234 */         row++;
/* 235 */         adjustColumnWidth(tcol, col, tmax);
/* 236 */         tcol += col;
/* 237 */         POMTablePanel.Cell cell = new POMTablePanel.Cell(s, row, col);
/* 238 */         this.header.addElement(cell);
/* 239 */         this.headrow = (this.headrow > row ? this.headrow : row);
/*     */       }
/* 241 */       else if ((a != 0) && (b != 0)) {
/* 242 */         int col = 0;
/* 243 */         int row = 0;
/* 244 */         while ((pos2 = s.indexOf('\n', pos1)) != -1) {
/* 245 */           String str = s.substring(pos1, pos2);
/* 246 */           tmax = str.length() > tmax ? str.length() : tmax;
/* 247 */           pos1 = pos2 + 1;
/* 248 */           row++;
/*     */         }
/* 250 */         pos2 = s.indexOf('\t', pos1);
/* 251 */         String str = s.substring(pos1, pos2);
/* 252 */         tmax = str.length() > tmax ? str.length() : tmax;
/* 253 */         pos1 = pos2 + 1;
/* 254 */         row++;
/* 255 */         while ((pos2 = s.indexOf('\t', pos1)) != -1) {
/* 256 */           str = s.substring(pos1, pos2);
/* 257 */           adjustColumnWidth(col++ + tcol, str.length());
/* 258 */           pos1 = pos2 + 1;
/*     */         }
/* 260 */         str = s.substring(pos1);
/* 261 */         adjustColumnWidth(col++ + tcol, str.length());
/* 262 */         row++;
/* 263 */         adjustColumnWidth(tcol, col, tmax);
/* 264 */         tcol += col;
/* 265 */         POMTablePanel.Cell cell = new POMTablePanel.Cell(s, row, col);
/* 266 */         this.header.addElement(cell);
/* 267 */         this.headrow = (this.headrow > row ? this.headrow : row);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setData(String[] d)
/*     */   {
/* 279 */     if (d.length != this.row * this.column)
/* 280 */       return;
/* 281 */     for (int j = 0; j < this.row; j++) {
/* 282 */       for (int i = 0; i < this.column; i++) {
/* 283 */         int n = j * this.column + i;
/* 284 */         POMTablePanel.Cell temp = new POMTablePanel.Cell(d[n], 1, 1);
/* 285 */         this.data.addElement(temp);
/* 286 */         adjustColumnWidth(i, d[n].length());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public void setDataInputState(int row, int column, boolean state) {
/* 295 */     setDataInputState(row, column, state, 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDataInputState(int row, int column, boolean state, int type)
/*     */   {
/* 302 */     if ((row > this.row) || (column > this.column))
/* 303 */       return;
/* 304 */     POMTablePanel.Cell temp = (POMTablePanel.Cell)this.data.elementAt(column + row * this.column);
/* 305 */     temp.canInput = state;
/* 306 */     switch (type) {
/*     */     case 0: 
/* 308 */       temp.datatype = 0;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 322 */       break;
/*     */     case 1: 
/* 311 */       temp.datatype = 1;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 322 */       break;
/*     */     case 2: 
/* 314 */       temp.datatype = 2;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 322 */       break;
/*     */     case 3: 
/* 317 */       temp.datatype = 3;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 322 */       break;
/*     */     default: 
/* 320 */       throw new IllegalArgumentException("This type isn't defined");
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */   public void setDataClickState(int row, int column, boolean state)
/*     */   {
/* 328 */     if ((row > this.row) || (column > this.column))
/* 329 */       return;
/* 330 */     POMTablePanel.Cell temp = (POMTablePanel.Cell)this.data.elementAt(column + row * this.column);
/* 331 */     temp.canClick = state;
/*     */   }
/*     */   
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public void setDataBackgroundState(int row, int column, Color color) {
/* 338 */     POMTablePanel.Cell temp = (POMTablePanel.Cell)this.data.elementAt(column + row * this.column);
/* 339 */     temp.Background = color;
/*     */   }
/*     */   
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public void setDataBackgroundState(int row, int column, boolean flag) {
/* 346 */     setDataBackgroundState(row, column, flag ? 1 : 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDataBackgroundState(int row, int column, int colornum)
/*     */   {
/* 353 */     if ((row > this.row) || (column > this.column))
/* 354 */       return;
/* 355 */     POMTablePanel.Cell temp = (POMTablePanel.Cell)this.data.elementAt(column + row * this.column);
/* 356 */     if ((colornum == -1) || (colornum == 0)) {
/* 357 */       temp.Background = this.PanelBackground;
/*     */     } else {
/* 359 */       temp.Background = this.DataBackground[colornum];
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void setForeground(Color border, Color text)
/*     */   {
/* 366 */     this.border = border;
/* 367 */     this.text = text;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setBackground(Color panel, Color head, Color[] data)
/*     */   {
/* 374 */     this.PanelBackground = panel;
/* 375 */     this.HeadBackground = head;
/* 376 */     this.DataBackground = data;
/*     */   }
/*     */   
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public void setBackground(Color head, Color data1, Color data2, Color data3) {
/* 383 */     Color[] data = new Color[4];
/* 384 */     data[1] = data1;
/* 385 */     data[2] = data2;
/* 386 */     data[3] = data3;
/* 387 */     setBackground(new Color(204, 204, 204), head, data);
/*     */   }
/*     */   
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public void setBackground(Color head, Color data1, Color data2) {
/* 394 */     Color[] data = new Color[3];
/* 395 */     data[1] = data1;
/* 396 */     data[2] = data2;
/* 397 */     setBackground(new Color(204, 204, 204), head, data);
/*     */   }
/*     */   
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public void setBackground(Color head, Color data1) {
/* 404 */     Color[] data = new Color[2];
/* 405 */     data[1] = data1;
/* 406 */     setBackground(new Color(204, 204, 204), head, data);
/*     */   }
/*     */   
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public void setTopLeft(int top, int bottom) {
/* 413 */     setGap(top, bottom, 0, 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setGap(int top, int bottom, int left, int right)
/*     */   {
/* 420 */     this.top = top;
/* 421 */     this.bottom = bottom;
/* 422 */     this.left = left;
/* 423 */     this.right = right;
/*     */   }
/*     */   
/*     */   private void adjustColumnWidth(int column, int width) {
/* 427 */     this.CellWidth[column] = (this.CellWidth[column] > width ? this.CellWidth[column] : width);
/*     */   }
/*     */   
/*     */   private void adjustColumnWidth(int column, int count, int width)
/*     */   {
/* 432 */     int w = 0;
/* 433 */     for (int i = column; i < column + count; i++)
/* 434 */       w += this.CellWidth[i];
/* 435 */     if (w >= width)
/* 436 */       return;
/* 437 */     int add = (int)Math.ceil((width - w) / count);
/* 438 */     for (int i = column; i < column + count; i++) {
/* 439 */       this.CellWidth[i] += add;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void InitEnd()
/*     */   {
/* 447 */     this.CellHeight = (this.fontHeight + 2);
/* 448 */     int[] sumCellWidth = new int[this.column];
/* 449 */     int sum = 0;
/*     */     
/* 451 */     for (int i = 0; i < this.CellWidth.length; i++)
/* 452 */       this.CellWidth[i] = (6 + this.CellWidth[i] * (this.fontWidth + 3));
/* 453 */     sumCellWidth[0] = 0;
/* 454 */     for (int i = 1; i < this.CellWidth.length; i++) {
/* 455 */       sumCellWidth[i] = (sum += this.CellWidth[(i - 1)]);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 465 */     int startx = this.left;
/* 466 */     int starty = this.top + this.headrow * this.CellHeight;
/* 467 */     for (int j = 0; j < this.row; j++) {
/* 468 */       for (int i = 0; i < this.column; i++) {
/* 469 */         POMTablePanel.Cell cell = (POMTablePanel.Cell)this.data.elementAt(i + j * this.column);
/* 470 */         cell.x = (sumCellWidth[i] + startx);
/* 471 */         cell.y = (j * this.CellHeight + starty);
/* 472 */         cell.width = this.CellWidth[i];
/* 473 */         cell.height = this.CellHeight;
/*     */       }
/*     */     }
/*     */     
/* 477 */     startx = this.left;
/* 478 */     starty = this.top;
/* 479 */     int col = 0;
/* 480 */     int headheight = this.CellHeight * this.headrow;
/* 481 */     for (int i = 0; i < this.header.size(); i++) {
/* 482 */       POMTablePanel.Cell cell = (POMTablePanel.Cell)this.header.elementAt(i);
/* 483 */       if ((cell.row == 1) && (cell.column == 1)) {
/* 484 */         cell.x = (sumCellWidth[col] + startx);
/* 485 */         cell.y = starty;
/* 486 */         cell.width = this.CellWidth[col];
/* 487 */         cell.height = headheight;
/* 488 */         col++;
/*     */       }
/* 490 */       else if ((cell.row != 1) && (cell.column == 1)) {
/* 491 */         cell.x = (sumCellWidth[col] + startx);
/* 492 */         cell.y = starty;
/* 493 */         cell.width = this.CellWidth[col];
/* 494 */         cell.height = headheight;
/* 495 */         col++;
/*     */       }
/* 497 */       else if ((cell.row == 1) && (cell.column != 1)) {
/* 498 */         int x = sumCellWidth[col] + startx;
/* 499 */         int y = starty;
/* 500 */         cell.x = x;
/* 501 */         cell.y = y;
/* 502 */         sum = 0;
/* 503 */         for (int j = 0; j < cell.column; j++)
/* 504 */           sum += this.CellWidth[(col + j)];
/* 505 */         cell.width = sum;
/* 506 */         cell.height = headheight;
/* 507 */         cell.y2 = (headheight - this.CellHeight + starty);
/* 508 */         cell.x2 = new int[cell.column - 1];
/* 509 */         sum = x;
/* 510 */         for (int j = 0; j < cell.column - 1; j++) {
/* 511 */           sum += this.CellWidth[(col + j)];
/* 512 */           cell.x2[j] = sum;
/*     */         }
/* 514 */         col += cell.column;
/*     */       }
/* 516 */       else if ((cell.row != 1) && (cell.column != 1)) {
/* 517 */         int x = sumCellWidth[col] + startx;
/* 518 */         int y = starty;
/* 519 */         cell.x = x;
/* 520 */         cell.y = y;
/* 521 */         sum = 0;
/* 522 */         for (int j = 0; j < cell.column; j++)
/* 523 */           sum += this.CellWidth[(col + j)];
/* 524 */         cell.width = sum;
/* 525 */         cell.height = headheight;
/* 526 */         cell.y2 = (headheight - this.CellHeight + starty);
/* 527 */         cell.x2 = new int[cell.column - 1];
/* 528 */         sum = x;
/* 529 */         for (int j = 0; j < cell.column - 1; j++) {
/* 530 */           sum += this.CellWidth[(col + j)];
/* 531 */           cell.x2[j] = sum;
/*     */         }
/* 533 */         col += cell.column;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void draw() {
/* 539 */     if (!this.inited)
/* 540 */       return;
/* 541 */     int pos1 = 0;
/* 542 */     int pos2 = 0;
/*     */     
/* 544 */     int col = 0;
/*     */     
/*     */ 
/*     */ 
/* 548 */     POMTablePanel.Cell first = (POMTablePanel.Cell)this.header.elementAt(0);
/* 549 */     POMTablePanel.Cell end = (POMTablePanel.Cell)this.header.elementAt(this.header.size() - 1);
/* 550 */     this.g.setColor(this.HeadBackground);
/* 551 */     this.g.fillRect(first.x, first.y, end.x + end.width - first.x, end.y + end.height - first.y);
/*     */     
/*     */ 
/* 554 */     for (int n = 0; n < this.header.size(); n++) {
/* 555 */       POMTablePanel.Cell cell = (POMTablePanel.Cell)this.header.elementAt(n);
/* 556 */       if ((cell.row == 1) && (cell.column == 1)) {
/* 557 */         this.g.setColor(this.border);
/* 558 */         this.g.drawRect(cell.x, cell.y, cell.width, cell.height);
/* 559 */         Point p = centerText(cell.x, cell.y, cell.width, cell.height, cell.name);
/*     */         
/* 561 */         this.g.setColor(this.text);
/* 562 */         this.g.drawString(cell.name, p.x, p.y);
/* 563 */         col++;
/*     */       }
/* 565 */       else if ((cell.row != 1) && (cell.column == 1)) {
/* 566 */         this.g.setColor(this.border);
/* 567 */         this.g.drawRect(cell.x, cell.y, cell.width, cell.height);
/* 568 */         String str = cell.name;
/* 569 */         int h = 0;
/* 570 */         pos1 = 0;
/* 571 */         this.g.setColor(this.text);
/* 572 */         for (int i = 0; i < cell.row - 1; i++) {
/* 573 */           pos2 = str.indexOf('\n', pos1);
/* 574 */           String s = str.substring(pos1, pos2);
/* 575 */           h = i * this.CellHeight + cell.y;
/* 576 */           Point p = centerText(cell.x, h, cell.width, this.CellHeight, s);
/* 577 */           this.g.drawString(s, p.x, p.y);
/* 578 */           pos1 = pos2 + 1;
/*     */         }
/* 580 */         String s = str.substring(pos1);
/* 581 */         h = (cell.row - 1) * this.CellHeight + cell.y;
/* 582 */         Point p = centerText(cell.x, h, cell.width, this.CellHeight, s);
/* 583 */         this.g.drawString(s, p.x, p.y);
/* 584 */         col++;
/*     */       }
/* 586 */       else if ((cell.row == 1) && (cell.column != 1)) {
/* 587 */         this.g.setColor(this.border);
/* 588 */         this.g.drawRect(cell.x, cell.y, cell.width, cell.height);
/* 589 */         this.g.drawLine(cell.x, cell.y2, cell.x + cell.width, cell.y2);
/* 590 */         for (int i = 0; i < cell.x2.length; i++) {
/* 591 */           this.g.drawLine(cell.x2[i], cell.y2, cell.x2[i], cell.y + cell.height);
/*     */         }
/* 593 */         this.g.setColor(this.text);
/* 594 */         String str = cell.name;
/* 595 */         int h = 0;
/* 596 */         pos1 = 0;
/* 597 */         pos2 = str.indexOf('\t', pos1);
/* 598 */         String s = str.substring(pos1, pos2);
/* 599 */         h = this.CellHeight + cell.y;
/* 600 */         Point p = centerText(cell.x, h, cell.width, this.CellHeight, s);
/* 601 */         this.g.drawString(s, p.x, p.y);
/* 602 */         pos1 = pos2 + 1;
/* 603 */         pos2 = str.indexOf('\t', pos1);
/* 604 */         s = str.substring(pos1, pos2);
/* 605 */         p = centerText(cell.x, cell.y2, this.CellWidth[col], this.CellHeight, s);
/* 606 */         this.g.drawString(s, p.x, p.y);
/* 607 */         pos1 = pos2 + 1;
/* 608 */         for (i = 0; i < cell.column - 2; i++) {
/* 609 */           pos2 = str.indexOf('\t', pos1);
/* 610 */           s = str.substring(pos1, pos2);
/* 611 */           p = centerText(cell.x2[i], cell.y2, this.CellWidth[(col + i)], this.CellHeight, s);
/*     */           
/* 613 */           this.g.drawString(s, p.x, p.y);
/* 614 */           pos1 = pos2 + 1;
/*     */         }
/* 616 */         s = str.substring(pos1);
/* 617 */         p = centerText(cell.x2[(cell.column - 2)], cell.y2, this.CellWidth[(col + cell.column - 1)], this.CellHeight, s);
/*     */         
/* 619 */         this.g.drawString(s, p.x, p.y);
/* 620 */         col += cell.column;
/*     */       }
/* 622 */       else if ((cell.row != 1) && (cell.column != 1)) {
/* 623 */         this.g.setColor(this.border);
/* 624 */         this.g.drawRect(cell.x, cell.y, cell.width, cell.height);
/* 625 */         this.g.drawLine(cell.x, cell.y2, cell.x + cell.width, cell.y2);
/* 626 */         for (int i = 0; i < cell.x2.length; i++) {
/* 627 */           this.g.drawLine(cell.x2[i], cell.y2, cell.x2[i], cell.y + cell.height);
/*     */         }
/* 629 */         this.g.setColor(this.text);
/* 630 */         String str = cell.name;
/* 631 */         int h = 0;
/* 632 */         pos1 = 0;
/* 633 */         for (i = 0; i < cell.row - 2; i++) {
/* 634 */           pos2 = str.indexOf('\n', pos1);
/* 635 */           String s = str.substring(pos1, pos2);
/* 636 */           h = i * this.CellHeight + cell.y;
/* 637 */           Point p = centerText(cell.x, h, cell.width, this.CellHeight, s);
/* 638 */           this.g.drawString(s, p.x, p.y);
/* 639 */           pos1 = pos2 + 1;
/*     */         }
/* 641 */         pos2 = str.indexOf('\t', pos1);
/* 642 */         String s = str.substring(pos1, pos2);
/* 643 */         h = (cell.row - 2) * this.CellHeight + cell.y;
/* 644 */         Point p = centerText(cell.x, h, cell.width, this.CellHeight, s);
/* 645 */         this.g.drawString(s, p.x, p.y);
/* 646 */         pos1 = pos2 + 1;
/* 647 */         pos2 = str.indexOf('\t', pos1);
/* 648 */         s = str.substring(pos1, pos2);
/* 649 */         p = centerText(cell.x, cell.y2, this.CellWidth[col], this.CellHeight, s);
/* 650 */         this.g.drawString(s, p.x, p.y);
/* 651 */         pos1 = pos2 + 1;
/* 652 */         for (i = 0; i < cell.column - 2; i++) {
/* 653 */           pos2 = str.indexOf('\t', pos1);
/* 654 */           s = str.substring(pos1, pos2);
/* 655 */           p = centerText(cell.x2[i], cell.y2, this.CellWidth[(col + i)], this.CellHeight, s);
/*     */           
/* 657 */           this.g.drawString(s, p.x, p.y);
/* 658 */           pos1 = pos2 + 1;
/*     */         }
/* 660 */         s = str.substring(pos1);
/* 661 */         p = centerText(cell.x2[(cell.column - 2)], cell.y2, this.CellWidth[(col + cell.column - 1)], this.CellHeight, s);
/*     */         
/* 663 */         this.g.drawString(s, p.x, p.y);
/* 664 */         col += cell.column;
/*     */       }
/*     */     }
/*     */     
/* 668 */     for (int i = 0; i < this.data.size(); i++) {
/* 669 */       POMTablePanel.Cell cell = (POMTablePanel.Cell)this.data.elementAt(i);
/* 670 */       this.g.setColor(this.border);
/* 671 */       this.g.drawRect(cell.x, cell.y, cell.width, cell.height);
/* 672 */       this.g.setColor(cell.Background);
/* 673 */       this.g.fillRect(cell.x + 1, cell.y + 1, cell.width - 1, cell.height - 1);
/* 674 */       this.g.setColor(this.text);
/* 675 */       Point p = centerText(cell.x, cell.y, cell.width, cell.height, cell.name);
/* 676 */       this.g.drawString(cell.name, p.x, p.y);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void updateData(String str, int row, int column)
/*     */   {
/* 684 */     POMTablePanel.Cell cell = (POMTablePanel.Cell)this.data.elementAt(row * this.column + column);
/* 685 */     cell.name = str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getData(int row, int column)
/*     */   {
/* 692 */     POMTablePanel.Cell cell = (POMTablePanel.Cell)this.data.elementAt(row * this.column + column);
/* 693 */     return cell.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String[] getData()
/*     */   {
/* 700 */     String[] ret = new String[this.row * this.column];
/* 701 */     for (int j = 0; j < this.row; j++)
/* 702 */       for (int i = 0; i < this.column; i++) {
/* 703 */         int num = j * this.column + i;
/* 704 */         POMTablePanel.Cell cell = (POMTablePanel.Cell)this.data.elementAt(num);
/* 705 */         ret[num] = cell.name;
/*     */       }
/* 707 */     return ret;
/*     */   }
/*     */   
/*     */   private Point centerText(int x, int y, int width, int height, String s) {
/* 711 */     Point p = new Point();
/* 712 */     FontMetrics fm = this.g.getFontMetrics();
/* 713 */     p.x = ((width - fm.stringWidth(s) >>> 1) + x);
/* 714 */     p.y = ((height - fm.getHeight() >> 1) + y + fm.getHeight() - fm.getDescent());
/*     */     
/* 716 */     return p;
/*     */   }
/*     */   
/*     */   private void dealWithValue(KeyEvent e) {
/* 720 */     int code = e.getKeyCode();
/* 721 */     if ((code == 37) || (code == 39))
/* 722 */       return;
/* 723 */     String str = this.tf.getText();
/* 724 */     if (str.equals(""))
/* 725 */       return;
/* 726 */     POMTablePanel.Cell temp = (POMTablePanel.Cell)this.data.elementAt(this.inputcurrent);
/* 727 */     switch (temp.datatype) {
/*     */     case 1: 
/* 729 */       this.applet.updateData(this.SerialNum, str, this.inputcurrent / this.column, this.inputcurrent % this.column);
/*     */       
/* 731 */       break;
/*     */     case 2: 
/* 733 */       int iresult = 0;
/* 734 */       if (str.equals("-"))
/* 735 */         return;
/*     */       try {
/* 737 */         iresult = Integer.parseInt(str);
/*     */       }
/*     */       catch (NumberFormatException ee) {
/* 740 */         this.tf.setText(temp.name);
/* 741 */         return;
/*     */       }
/* 743 */       this.applet.updateData(this.SerialNum, iresult, this.inputcurrent / this.column, this.inputcurrent % this.column);
/*     */       
/* 745 */       break;
/*     */     case 3: 
/* 747 */       float fresult = 0.0F;
/* 748 */       if (str.equals("-.")) {
/* 749 */         this.tf.setText(temp.name);
/* 750 */         return;
/*     */       }
/* 752 */       if ((str.equals("0")) || (str.equals(".")) || (str.equals("-")) || (str.equals("-0")) || (str.indexOf('.') == str.length() - 1))
/*     */       {
/* 754 */         return; }
/*     */       try {
/* 756 */         fresult = new Float(str).floatValue();
/*     */       }
/*     */       catch (NumberFormatException ee) {
/* 759 */         this.tf.setText(temp.name);
/* 760 */         return;
/*     */       }
/* 762 */       this.applet.updateData(this.SerialNum, fresult, this.inputcurrent / this.column, this.inputcurrent % this.column);
/*     */       
/* 764 */       break;
/*     */     }
/* 766 */     if ((!temp.name.equals(str)) && (
/* 767 */       (temp.datatype != 3) || (!str.endsWith("0")) || (str.indexOf('.') == -1)))
/*     */     {
/*     */ 
/*     */ 
/* 771 */       this.tf.setText(temp.name);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void modifyTable(int row, int column)
/*     */   {
/* 780 */     this.row = row;
/* 781 */     this.column = column;
/* 782 */     this.header.removeAllElements();
/* 783 */     this.data.removeAllElements();
/* 784 */     this.CellWidth = new int[column];
/*     */   }
/*     */   
/*     */   public void update(Graphics g) {
/* 788 */     paint(g);
/*     */   }
/*     */   
/*     */   public void paint(Graphics g) {
/* 792 */     if (!this.inited) {
/* 793 */       this.backbuffer = createImage(getSize().width, getSize().height);
/* 794 */       this.g = this.backbuffer.getGraphics();
/* 795 */       this.inited = true;
/*     */     }
/* 797 */     this.g.setColor(this.PanelBackground);
/* 798 */     this.g.fillRect(0, 0, getSize().width, getSize().height);
/* 799 */     draw();
/* 800 */     g.drawImage(this.backbuffer, 0, 0, null);
/*     */   }
/*     */   
/*     */   private void jbInit() throws Exception {
/* 804 */     setFont(new Font("Dialog", 0, 12));
/* 805 */     addMouseListener(new MouseAdapter() {
/*     */       public void mouseClicked(MouseEvent e) {
/* 807 */         POMTablePanel.this.this_mouseClicked(e);
/*     */       }
/* 809 */     });
/* 810 */     addMouseMotionListener(new MouseMotionAdapter() {
/*     */       public void mouseMoved(MouseEvent e) {
/* 812 */         POMTablePanel.this.this_mouseMoved(e);
/*     */       }
/* 814 */     });
/* 815 */     this.tf.setVisible(false);
/* 816 */     add(this.tf);
/*     */   }
/*     */   
/*     */   void this_mouseMoved(MouseEvent e) {
/* 820 */     for (int i = 0; i < this.data.size(); i++) {
/* 821 */       POMTablePanel.Cell cell = (POMTablePanel.Cell)this.data.elementAt(i);
/* 822 */       Rectangle rect = new Rectangle(cell.x, cell.y, cell.width, cell.height);
/*     */       
/* 824 */       if (rect.contains(e.getX(), e.getY())) {
/* 825 */         if (cell.canInput) {
/* 826 */           setCursor(Cursor.getPredefinedCursor(2));
/*     */         } else
/* 828 */           setCursor(Cursor.getPredefinedCursor(0));
/* 829 */         this.movecurrent = i;
/* 830 */         return;
/*     */       }
/*     */     }
/* 833 */     this.movecurrent = -1;
/* 834 */     setCursor(Cursor.getPredefinedCursor(0));
/*     */   }
/*     */   
/*     */   void this_mouseClicked(MouseEvent e) {
/* 838 */     if (!this.MouseFocus) {
/* 839 */       this.MouseFocus = true;
/* 840 */       this.applet.focusOn(this.SerialNum);
/*     */     }
/* 842 */     if (this.movecurrent != -1) {
/* 843 */       POMTablePanel.Cell cell = (POMTablePanel.Cell)this.data.elementAt(this.movecurrent);
/* 844 */       if (cell.canInput) {
/* 845 */         if (this.inputcurrent == -1) {
/* 846 */           this.inputcurrent = this.movecurrent;
/* 847 */           this.tf.setBounds(cell.x, cell.y, cell.width, cell.height);
/* 848 */           this.tf.setText(cell.name);
/* 849 */           this.tf.setVisible(true);
/* 850 */           this.tf.requestFocus();
/*     */         }
/*     */         else {
/* 853 */           this.tf.setVisible(false);
/*     */           
/*     */ 
/* 856 */           this.inputcurrent = this.movecurrent;
/* 857 */           cell = (POMTablePanel.Cell)this.data.elementAt(this.inputcurrent);
/* 858 */           this.tf.setBounds(cell.x, cell.y, cell.width, cell.height);
/* 859 */           this.tf.setText(cell.name);
/* 860 */           this.tf.setVisible(true);
/*     */         }
/* 862 */         return;
/*     */       }
/* 864 */       if (cell.canClick) {
/* 865 */         this.clickcurrent = this.movecurrent;
/* 866 */         this.applet.clickData(this.SerialNum, this.clickcurrent / this.column, this.clickcurrent % this.column);
/*     */         
/* 868 */         return;
/*     */       }
/*     */     }
/* 871 */     if (this.inputcurrent != -1)
/*     */     {
/* 873 */       this.tf.setVisible(false);
/* 874 */       this.inputcurrent = -1;
/*     */     }
/* 876 */     if (this.clickcurrent != -1) {
/* 877 */       this.clickcurrent = -1;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void leave()
/*     */   {
/* 885 */     if (this.inputcurrent != -1) {
/* 886 */       this.applet.updateData(this.SerialNum, this.tf.getText(), this.inputcurrent / this.column, this.inputcurrent % this.column);
/*     */       
/* 888 */       this.inputcurrent = -1;
/*     */     }
/* 890 */     this.movecurrent = -1;
/* 891 */     this.clickcurrent = -1;
/* 892 */     this.MouseFocus = false;
/* 893 */     this.tf.setVisible(false);
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 897 */     POMTablePanel.Cell a = (POMTablePanel.Cell)this.header.elementAt(0);
/* 898 */     POMTablePanel.Cell b = (POMTablePanel.Cell)this.data.elementAt(this.data.size() - 1);
/* 899 */     this.width = (b.x + b.width - a.x + this.right + this.left + 1);
/* 900 */     this.height = (b.y + b.height - a.y + this.bottom + this.top + 1);
/* 901 */     return new Dimension(this.width, this.height);
/*     */   }
/*     */   
/*     */   void renameHead(String name) {
/* 905 */     POMTablePanel.Cell cell = (POMTablePanel.Cell)this.header.elementAt(2);
/* 906 */     cell.name = name;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\POMTablePanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */