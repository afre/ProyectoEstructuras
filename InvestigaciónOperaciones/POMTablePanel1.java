/*     */ import java.awt.Color;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionAdapter;
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
/*     */ public class POMTablePanel1
/*     */   extends JPanel
/*     */   implements TextFieldNotify
/*     */ {
/*     */   class Cell
/*     */   {
/*     */     String name;
/*     */     int x;
/*     */     int y;
/*     */     int width;
/*     */     int height;
/*     */     int y2;
/*     */     int[] x2;
/*     */     int row;
/*     */     int column;
/*  54 */     boolean canInput = false;
/*  55 */     boolean varyBackground = false;
/*     */     
/*  57 */     public Cell(String s1, int row, int column) { this.name = s1;this.row = row;this.column = column;
/*     */     } }
/*     */   
/*  60 */   private Vector header = new Vector();
/*  61 */   private Vector data = new Vector();
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
/*     */   private POMTableDataNotify1 applet;
/*     */   
/*  78 */   private boolean inited = false;
/*     */   private Image backbuffer;
/*     */   private Graphics g;
/*  81 */   private int fontHeight = 14;
/*  82 */   private int fontWidth = 6;
/*     */   private Color border;
/*     */   private Color text;
/*     */   private Color HeadBackground;
/*     */   private Color DataBackground;
/*  87 */   private int top; private int left; private NumberTextField tf = new NumberTextField(this);
/*  88 */   private int movecurrent = -1;
/*  89 */   private int inputcurrent = -1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public POMTablePanel1(POMTableDataNotify1 applet, int col, int row, int num)
/*     */   {
/*     */     try
/*     */     {
/*  98 */       jbInit();
/*     */     }
/*     */     catch (Exception e) {
/* 101 */       e.printStackTrace();
/*     */     }
/* 103 */     this.SerialNum = num;
/* 104 */     this.applet = applet;
/* 105 */     this.row = row;this.column = col;this.headrow = 1;
/* 106 */     this.CellWidth = new int[col];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHeader(String[] head)
/*     */     throws Exception
/*     */   {
/* 115 */     if (head.length > this.column) return;
/* 116 */     int tcol = 0;
/* 117 */     for (int i = 0; i < head.length; i++)
/*     */     {
/* 119 */       String s = head[i];
/* 120 */       int pos2; int pos1 = pos2 = 0;
/* 121 */       int b; int a = b = 0;
/* 122 */       int max; int tmax = max = 0;
/* 124 */       for (; 
/* 124 */           (pos2 = s.indexOf('\n', pos1)) != -1; 
/* 125 */           a++) pos1 = pos2 + 1;
/* 127 */       for (; 
/* 127 */           (pos2 = s.indexOf('\t', pos1)) != -1; 
/* 128 */           b++) { pos1 = pos2 + 1;
/*     */       }
/* 130 */       pos1 = pos2 = 0;
/* 131 */       if ((a == 0) && (b == 0)) {
/* 132 */         adjustColumnWidth(tcol, s.length());
/* 133 */         tcol++;
/* 134 */         POMTablePanel1.Cell cell = new POMTablePanel1.Cell(s, 1, 1);this.header.addElement(cell);
/* 135 */       } else if ((a != 0) && (b == 0)) {
/* 136 */         int row = 0;
/* 137 */         while ((pos2 = s.indexOf('\n', pos1)) != -1) {
/* 138 */           String str = s.substring(pos1, pos2);
/* 139 */           tmax = str.length() > tmax ? str.length() : tmax;
/* 140 */           row++;
/* 141 */           pos1 = pos2 + 1;
/*     */         }
/* 143 */         String str = s.substring(pos1);
/* 144 */         tmax = str.length() > tmax ? str.length() : tmax;
/* 145 */         row++;
/* 146 */         adjustColumnWidth(tcol, tmax);
/* 147 */         tcol++;
/* 148 */         POMTablePanel1.Cell cell = new POMTablePanel1.Cell(s, row, 1);this.header.addElement(cell);
/* 149 */         this.headrow = (this.headrow > row ? this.headrow : row);
/* 150 */       } else { if ((a == 0) && (b != 0))
/*     */         {
/* 152 */           throw new Exception("string cann't match pattern"); }
/* 153 */         if ((a != 0) && (b != 0)) {
/* 154 */           int col = 0;int row = 0;
/* 155 */           while ((pos2 = s.indexOf('\n', pos1)) != -1) {
/* 156 */             String str = s.substring(pos1, pos2);
/* 157 */             tmax = str.length() > tmax ? str.length() : tmax;
/* 158 */             pos1 = pos2 + 1;
/* 159 */             row++;
/*     */           }
/* 161 */           pos2 = s.indexOf('\t', pos1);
/* 162 */           String str = s.substring(pos1, pos2);
/* 163 */           tmax = str.length() > tmax ? str.length() : tmax;
/* 164 */           pos1 = pos2 + 1;
/* 165 */           row++;
/* 166 */           while ((pos2 = s.indexOf('\t', pos1)) != -1) {
/* 167 */             str = s.substring(pos1, pos2);
/* 168 */             adjustColumnWidth(col++ + tcol, str.length());
/* 169 */             pos1 = pos2 + 1;
/*     */           }
/* 171 */           str = s.substring(pos1);
/* 172 */           adjustColumnWidth(col++ + tcol, str.length());
/* 173 */           row++;
/* 174 */           adjustColumnWidth(tcol, col, tmax);
/* 175 */           tcol += col;
/* 176 */           POMTablePanel1.Cell cell = new POMTablePanel1.Cell(s, row, col);this.header.addElement(cell);
/* 177 */           this.headrow = (this.headrow > row ? this.headrow : row);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void setData(String[] d)
/*     */   {
/* 186 */     if (d.length != this.row * this.column) return;
/* 187 */     for (int j = 0; j < this.row; j++) {
/* 188 */       for (int i = 0; i < this.column; i++) {
/* 189 */         int n = j * this.column + i;
/* 190 */         POMTablePanel1.Cell temp = new POMTablePanel1.Cell(d[n], 1, 1);
/* 191 */         this.data.addElement(temp);
/* 192 */         adjustColumnWidth(i, d[n].length());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void setDataInputState(int row, int column, boolean state)
/*     */   {
/* 200 */     if ((row > this.row) || (column > this.column)) return;
/* 201 */     POMTablePanel1.Cell temp = (POMTablePanel1.Cell)this.data.elementAt(column + row * this.column);
/* 202 */     temp.canInput = state;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setDataBackgroundState(int row, int column, boolean state)
/*     */   {
/* 208 */     if ((row > this.row) || (column > this.column)) return;
/* 209 */     POMTablePanel1.Cell temp = (POMTablePanel1.Cell)this.data.elementAt(column + row * this.column);
/* 210 */     temp.varyBackground = state;
/*     */   }
/*     */   
/* 213 */   public void setForeground(Color border, Color text) { this.border = border;this.text = text;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setBackground(Color head, Color data)
/*     */   {
/* 219 */     this.HeadBackground = head;this.DataBackground = data;
/*     */   }
/*     */   
/* 222 */   public void setTopLeft(int y, int x) { this.left = x;this.top = y;
/*     */   }
/*     */   
/* 225 */   private void adjustColumnWidth(int column, int width) { this.CellWidth[column] = (this.CellWidth[column] > width ? this.CellWidth[column] : width); }
/*     */   
/*     */   private void adjustColumnWidth(int column, int count, int width) {
/* 228 */     int w = 0;
/* 229 */     for (int i = column; i < column + count; i++) w += this.CellWidth[i];
/* 230 */     if (w >= width) return;
/* 231 */     int add = (int)Math.ceil((width - w) / count);
/* 232 */     for (int i = column; i < column + count; i++) { this.CellWidth[i] += add;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void InitEnd()
/*     */   {
/* 239 */     this.CellHeight = (this.fontHeight + 2);
/* 240 */     int[] sumCellWidth = new int[this.column];int sum = 0;
/*     */     
/* 242 */     for (int i = 0; i < this.CellWidth.length; i++) this.CellWidth[i] = (6 + this.CellWidth[i] * (this.fontWidth + 3));
/* 243 */     sumCellWidth[0] = 0;
/* 244 */     for (int i = 1; i < this.CellWidth.length; i++) { sumCellWidth[i] = (sum += this.CellWidth[(i - 1)]);
/*     */     }
/* 246 */     int startx = this.left;int starty = this.top + this.headrow * this.CellHeight;
/* 247 */     for (int j = 0; j < this.row; j++) {
/* 248 */       for (int i = 0; i < this.column; i++) {
/* 249 */         POMTablePanel1.Cell cell = (POMTablePanel1.Cell)this.data.elementAt(i + j * this.column);
/* 250 */         cell.x = (sumCellWidth[i] + startx);cell.y = (j * this.CellHeight + starty);
/* 251 */         cell.width = this.CellWidth[i];cell.height = this.CellHeight;
/*     */       }
/*     */     }
/*     */     
/* 255 */     startx = this.left;starty = this.top;int col = 0;int headheight = this.CellHeight * this.headrow;
/* 256 */     for (int i = 0; i < this.header.size(); i++) {
/* 257 */       POMTablePanel1.Cell cell = (POMTablePanel1.Cell)this.header.elementAt(i);
/* 258 */       if ((cell.row == 1) && (cell.column == 1)) {
/* 259 */         cell.x = (sumCellWidth[col] + startx);cell.y = starty;
/* 260 */         cell.width = this.CellWidth[col];cell.height = headheight;
/* 261 */         col++;
/* 262 */       } else if ((cell.row != 1) && (cell.column == 1)) {
/* 263 */         cell.x = (sumCellWidth[col] + startx);cell.y = starty;
/* 264 */         cell.width = this.CellWidth[col];cell.height = headheight;
/* 265 */         col++;
/* 266 */       } else if ((cell.row != 1) && (cell.column != 1)) {
/* 267 */         int x = sumCellWidth[col] + startx;int y = starty;
/* 268 */         cell.x = x;cell.y = y;sum = 0;
/* 269 */         for (int j = 0; j < cell.column; j++) sum += this.CellWidth[(col + j)];
/* 270 */         cell.width = sum;cell.height = headheight;
/* 271 */         cell.y2 = (headheight - this.CellHeight + starty);
/* 272 */         cell.x2 = new int[cell.column - 1];sum = x;
/* 273 */         for (int j = 0; j < cell.column - 1; j++) {
/* 274 */           sum += this.CellWidth[(col + j)];
/* 275 */           cell.x2[j] = sum;
/*     */         }
/* 277 */         col += cell.column;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void draw()
/*     */   {
/* 285 */     if (!this.inited) return;
/* 286 */     int pos1 = 0;int pos2 = 0;int col = 0;
/*     */     
/* 288 */     POMTablePanel1.Cell first = (POMTablePanel1.Cell)this.header.elementAt(0);POMTablePanel1.Cell end = (POMTablePanel1.Cell)this.header.elementAt(this.header.size() - 1);
/* 289 */     this.g.setColor(this.HeadBackground);
/* 290 */     this.g.fillRect(first.x, first.y, end.x + end.width - first.x, end.y + end.height - first.y);
/*     */     
/* 292 */     for (int n = 0; n < this.header.size(); n++) {
/* 293 */       POMTablePanel1.Cell cell = (POMTablePanel1.Cell)this.header.elementAt(n);
/* 294 */       if ((cell.row == 1) && (cell.column == 1)) {
/* 295 */         this.g.setColor(this.border);
/* 296 */         this.g.drawRect(cell.x, cell.y, cell.width, cell.height);
/* 297 */         Point p = centerText(cell.x, cell.y, cell.width, cell.height, cell.name);
/* 298 */         this.g.setColor(this.text);
/* 299 */         this.g.drawString(cell.name, p.x, p.y);
/* 300 */         col++;
/* 301 */       } else if ((cell.row != 1) && (cell.column == 1)) {
/* 302 */         this.g.setColor(this.border);
/* 303 */         this.g.drawRect(cell.x, cell.y, cell.width, cell.height);
/* 304 */         String str = cell.name;int h = 0;pos1 = 0;
/* 305 */         this.g.setColor(this.text);
/* 306 */         for (int i = 0; i < cell.row - 1; i++) {
/* 307 */           pos2 = str.indexOf('\n', pos1);
/* 308 */           String s = str.substring(pos1, pos2);
/* 309 */           h = i * this.CellHeight + cell.y;
/* 310 */           Point p = centerText(cell.x, h, cell.width, this.CellHeight, s);
/* 311 */           this.g.drawString(s, p.x, p.y);
/* 312 */           pos1 = pos2 + 1;
/*     */         }
/* 314 */         String s = str.substring(pos1);
/* 315 */         h = (cell.row - 1) * this.CellHeight + cell.y;
/* 316 */         Point p = centerText(cell.x, h, cell.width, this.CellHeight, s);
/* 317 */         this.g.drawString(s, p.x, p.y);
/* 318 */         col++;
/* 319 */       } else if ((cell.row != 1) && (cell.column != 1)) {
/* 320 */         this.g.setColor(this.border);
/* 321 */         this.g.drawRect(cell.x, cell.y, cell.width, cell.height);
/* 322 */         this.g.drawLine(cell.x, cell.y2, cell.x + cell.width, cell.y2);
/* 323 */         for (int i = 0; i < cell.x2.length; i++) this.g.drawLine(cell.x2[i], cell.y2, cell.x2[i], cell.y + cell.height);
/* 324 */         this.g.setColor(this.text);
/* 325 */         String str = cell.name;int h = 0;pos1 = 0;
/* 326 */         for (i = 0; i < cell.row - 2; i++) {
/* 327 */           pos2 = str.indexOf('\n', pos1);
/* 328 */           String s = str.substring(pos1, pos2);
/* 329 */           h = i * this.CellHeight + cell.y;
/* 330 */           Point p = centerText(cell.x, h, cell.width, this.CellHeight, s);
/* 331 */           this.g.drawString(s, p.x, p.y);
/* 332 */           pos1 = pos2 + 1;
/*     */         }
/* 334 */         pos2 = str.indexOf('\t', pos1);
/* 335 */         String s = str.substring(pos1, pos2);
/* 336 */         h = (cell.row - 2) * this.CellHeight + cell.y;
/* 337 */         Point p = centerText(cell.x, h, cell.width, this.CellHeight, s);
/* 338 */         this.g.drawString(s, p.x, p.y);
/* 339 */         pos1 = pos2 + 1;
/* 340 */         pos2 = str.indexOf('\t', pos1);
/* 341 */         s = str.substring(pos1, pos2);
/* 342 */         p = centerText(cell.x, cell.y2, this.CellWidth[col], this.CellHeight, s);
/* 343 */         this.g.drawString(s, p.x, p.y);
/* 344 */         pos1 = pos2 + 1;
/* 345 */         for (i = 0; i < cell.column - 2; i++) {
/* 346 */           pos2 = str.indexOf('\t', pos1);
/* 347 */           s = str.substring(pos1, pos2);
/* 348 */           p = centerText(cell.x2[i], cell.y2, this.CellWidth[(col + i)], this.CellHeight, s);
/* 349 */           this.g.drawString(s, p.x, p.y);
/* 350 */           pos1 = pos2 + 1;
/*     */         }
/* 352 */         s = str.substring(pos1);
/* 353 */         p = centerText(cell.x2[(cell.column - 2)], cell.y2, this.CellWidth[(col + cell.column - 1)], this.CellHeight, s);
/* 354 */         this.g.drawString(s, p.x, p.y);
/* 355 */         col += cell.column;
/*     */       }
/*     */     }
/*     */     
/* 359 */     for (int i = 0; i < this.data.size(); i++) {
/* 360 */       POMTablePanel1.Cell cell = (POMTablePanel1.Cell)this.data.elementAt(i);
/* 361 */       this.g.setColor(this.border);
/* 362 */       this.g.drawRect(cell.x, cell.y, cell.width, cell.height);
/* 363 */       if (cell.varyBackground) {
/* 364 */         this.g.setColor(this.DataBackground);
/* 365 */         this.g.fillRect(cell.x + 1, cell.y + 1, cell.width - 1, cell.height - 1);
/*     */       }
/* 367 */       this.g.setColor(this.text);
/* 368 */       Point p = centerText(cell.x, cell.y, cell.width, cell.height, cell.name);
/* 369 */       this.g.drawString(cell.name, p.x, p.y);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateData(String str, int row, int column)
/*     */   {
/* 376 */     POMTablePanel1.Cell cell = (POMTablePanel1.Cell)this.data.elementAt(row * this.column + column);
/* 377 */     cell.name = str;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getData(int row, int column)
/*     */   {
/* 383 */     POMTablePanel1.Cell cell = (POMTablePanel1.Cell)this.data.elementAt(row * this.column + column);
/* 384 */     return cell.name;
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] getData()
/*     */   {
/* 390 */     String[] ret = new String[this.row * this.column];
/* 391 */     for (int j = 0; j < this.row; j++)
/* 392 */       for (int i = 0; i < this.column; i++) {
/* 393 */         int num = j * this.row + i;
/* 394 */         POMTablePanel1.Cell cell = (POMTablePanel1.Cell)this.data.elementAt(num);
/* 395 */         ret[num] = cell.name;
/*     */       }
/* 397 */     return ret;
/*     */   }
/*     */   
/* 400 */   private Point centerText(int x, int y, int width, int height, String s) { Point p = new Point();FontMetrics fm = this.g.getFontMetrics();
/* 401 */     p.x = ((width - fm.stringWidth(s) >>> 1) + x);
/* 402 */     p.y = ((height - fm.getHeight() >> 1) + y + fm.getHeight() - fm.getDescent());
/* 403 */     return p;
/*     */   }
/*     */   
/*     */ 
/*     */   public void dealWithValue(String str)
/*     */   {
/* 409 */     this.applet.updateData(this.SerialNum, str, this.inputcurrent % this.column, this.inputcurrent / this.column);
/* 410 */     String temp = ((POMTablePanel1.Cell)this.data.elementAt(this.inputcurrent)).name;
/* 411 */     if (!temp.equals(str)) { this.tf.setText(temp);
/*     */     }
/*     */   }
/*     */   
/*     */   public void update(Graphics g)
/*     */   {
/* 417 */     paint(g);
/*     */   }
/*     */   
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/* 423 */     if (!this.inited) {
/* 424 */       this.backbuffer = createImage(getSize().width, getSize().height);
/* 425 */       this.g = this.backbuffer.getGraphics();
/* 426 */       this.inited = true;
/*     */     }
/* 428 */     this.g.setColor(new Color(204, 204, 204));
/* 429 */     this.g.fillRect(0, 0, getSize().width, getSize().height);
/* 430 */     draw();
/* 431 */     g.drawImage(this.backbuffer, 0, 0, null);
/*     */   }
/*     */   
/* 434 */   private void jbInit() throws Exception { setFont(new Font("Dialog", 0, 12));
/* 435 */     addMouseListener(new MouseAdapter() {
/*     */       public void mouseClicked(MouseEvent e) {
/* 437 */         POMTablePanel1.this.this_mouseClicked(e);
/*     */       }
/* 439 */     });
/* 440 */     addMouseMotionListener(new MouseMotionAdapter() {
/*     */       public void mouseMoved(MouseEvent e) {
/* 442 */         POMTablePanel1.this.this_mouseMoved(e);
/*     */       }
/* 444 */     });
/* 445 */     this.tf.setVisible(false);
/* 446 */     add(this.tf);
/*     */   }
/*     */   
/* 449 */   void this_mouseMoved(MouseEvent e) { for (int i = 0; i < this.data.size(); i++) {
/* 450 */       POMTablePanel1.Cell cell = (POMTablePanel1.Cell)this.data.elementAt(i);
/* 451 */       if (cell.canInput) {
/* 452 */         Rectangle rect = new Rectangle(cell.x, cell.y, cell.width, cell.height);
/* 453 */         if (rect.contains(e.getX(), e.getY())) {
/* 454 */           setCursor(Cursor.getPredefinedCursor(2));
/* 455 */           this.movecurrent = i;
/* 456 */           return;
/*     */         }
/*     */       }
/*     */     }
/* 460 */     this.movecurrent = -1;
/* 461 */     setCursor(Cursor.getPredefinedCursor(0));
/*     */   }
/*     */   
/* 464 */   void this_mouseClicked(MouseEvent e) { if ((this.movecurrent == -1) && (this.inputcurrent == -1)) {
/* 465 */       this.applet.focusOn(this.SerialNum);
/* 466 */       return; }
/* 467 */     if ((this.movecurrent != -1) && (this.inputcurrent == -1)) {
/* 468 */       this.applet.focusOn(this.SerialNum);
/* 469 */       this.inputcurrent = this.movecurrent;
/* 470 */       POMTablePanel1.Cell cell = (POMTablePanel1.Cell)this.data.elementAt(this.inputcurrent);
/* 471 */       this.tf.setBounds(cell.x, cell.y, cell.width, cell.height);
/* 472 */       this.tf.setText(cell.name);
/* 473 */       this.tf.setVisible(true);
/* 474 */       this.tf.requestFocus();
/* 475 */     } else if ((this.movecurrent == -1) && (this.inputcurrent != -1)) {
/* 476 */       this.tf.setVisible(false);
/* 477 */       this.applet.updateData(this.SerialNum, this.tf.getText(), this.inputcurrent % this.column, this.inputcurrent / this.column);
/* 478 */       this.inputcurrent = -1;
/*     */     } else {
/* 480 */       this.tf.setVisible(false);
/* 481 */       POMTablePanel1.Cell cell = (POMTablePanel1.Cell)this.data.elementAt(this.inputcurrent);
/* 482 */       this.applet.updateData(this.SerialNum, this.tf.getText(), this.inputcurrent % this.column, this.inputcurrent / this.column);
/* 483 */       this.inputcurrent = this.movecurrent;
/* 484 */       cell = (POMTablePanel1.Cell)this.data.elementAt(this.inputcurrent);
/* 485 */       this.tf.setBounds(cell.x, cell.y, cell.width, cell.height);
/* 486 */       this.tf.setText(cell.name);
/* 487 */       this.tf.setVisible(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void leave()
/*     */   {
/* 494 */     if (this.inputcurrent != -1) {
/* 495 */       this.applet.updateData(this.SerialNum, this.tf.getText(), this.inputcurrent % this.column, this.inputcurrent / this.column);
/* 496 */       this.inputcurrent = -1;
/*     */     }
/* 498 */     this.movecurrent = -1;
/* 499 */     this.tf.setVisible(false);
/*     */   }
/*     */   
/* 502 */   public Dimension getPreferredSize() { POMTablePanel1.Cell a = (POMTablePanel1.Cell)this.header.elementAt(0);POMTablePanel1.Cell b = (POMTablePanel1.Cell)this.data.elementAt(this.data.size() - 1);
/* 503 */     int w = b.x + b.width - a.x + 2 * this.left + 1;int h = b.y + b.height - a.y + 2 * this.top + 1;
/* 504 */     return new Dimension(w, h);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\POMTablePanel1.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */