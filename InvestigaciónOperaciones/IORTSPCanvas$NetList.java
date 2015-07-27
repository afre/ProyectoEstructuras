/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.Stroke;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORTSPCanvas$NetList
/*     */ {
/*     */   private final int NE = 1;
/*     */   private final int NW = 2;
/*     */   private final int SW = 3;
/*     */   private final int SE = 4;
/*     */   private final int N = 11;
/*     */   private final int S = 12;
/*     */   private final int r = 32;
/*     */   private final int L0 = 6;
/*     */   private double rx;
/*     */   private double ry;
/*     */   int np;
/*     */   Point[] p;
/*     */   String[] lineP;
/*     */   private final IORTSPCanvas this$0;
/*     */   
/*     */   public IORTSPCanvas$NetList(IORTSPCanvas this$0, double w, double h)
/*     */   {
/* 112 */     this.this$0 = this$0;this.NE = 1;this.NW = 2;this.SW = 3;this.SE = 4;this.N = 11;this.S = 12;this.r = 32;this.L0 = 6;this.rx = 1.0D;this.ry = 1.0D;this.np = -1;
/* 113 */     setCanvasRatio(w, h);
/* 114 */     drawMap();
/* 115 */     initializeDes();
/*     */   }
/*     */   
/*     */   public void drawMap() {
/* 119 */     switch (IORTSPCanvas.access$1(this.this$0).data.num_city) {
/*     */     case 3: 
/* 121 */       initializeProblem3();
/* 122 */       break;
/*     */     case 4: 
/* 124 */       initializeProblem4();
/* 125 */       break;
/*     */     case 5: 
/* 127 */       initializeProblem5();
/* 128 */       break;
/*     */     case 6: 
/* 130 */       initializeProblem6();
/* 131 */       break;
/*     */     case 7: 
/* 133 */       initializeProblem7();
/* 134 */       break;
/*     */     case 8: 
/* 136 */       initializeProblem8();
/* 137 */       break;
/*     */     case 9: 
/* 139 */       initializeProblem9();
/* 140 */       break;
/*     */     case 10: 
/* 142 */       initializeProblem10();
/* 143 */       break;
/*     */     default: 
/* 145 */       System.err.println("ERROR IN NETLIST");
/*     */     }
/* 147 */     this.this$0.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   private void initializeProblem3()
/*     */   {
/* 153 */     this.np = 3;
/* 154 */     this.p = new Point[3];
/* 155 */     this.p[0] = new Point(300, 165);
/* 156 */     this.p[1] = new Point(150, 55);
/* 157 */     this.p[2] = new Point(450, 55);
/*     */   }
/*     */   
/*     */   private void initializeProblem4()
/*     */   {
/* 162 */     this.np = 4;
/* 163 */     this.p = new Point[this.np];
/* 164 */     this.p[0] = new Point(180, 110);
/* 165 */     this.p[1] = new Point(420, 110);
/* 166 */     this.p[2] = new Point(300, 40);
/* 167 */     this.p[3] = new Point(300, 180);
/*     */   }
/*     */   
/*     */ 
/*     */   private void initializeProblem5()
/*     */   {
/* 173 */     this.np = 5;
/* 174 */     this.p = new Point[this.np];
/* 175 */     this.p[0] = new Point(300, 35);
/* 176 */     this.p[1] = new Point(160, 85);
/* 177 */     this.p[2] = new Point(440, 85);
/* 178 */     this.p[3] = new Point(200, 180);
/* 179 */     this.p[4] = new Point(400, 180);
/*     */   }
/*     */   
/*     */   private void initializeProblem6()
/*     */   {
/* 184 */     this.np = 6;
/* 185 */     this.p = new Point[this.np];
/* 186 */     this.p[0] = new Point(300, 30);
/* 187 */     this.p[1] = new Point(150, 70);
/* 188 */     this.p[2] = new Point(150, 150);
/* 189 */     this.p[3] = new Point(450, 70);
/* 190 */     this.p[4] = new Point(450, 150);
/* 191 */     this.p[5] = new Point(300, 190);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void initializeProblem7()
/*     */   {
/* 198 */     this.np = 7;
/* 199 */     this.p = new Point[this.np];
/* 200 */     this.p[0] = new Point(300, 30);
/* 201 */     this.p[1] = new Point(150, 70);
/* 202 */     this.p[2] = new Point(150, 150);
/* 203 */     this.p[3] = new Point(450, 70);
/* 204 */     this.p[4] = new Point(450, 150);
/* 205 */     this.p[5] = new Point(300, 190);
/* 206 */     this.p[6] = new Point(300, 110);
/*     */   }
/*     */   
/*     */   private void initializeProblem8()
/*     */   {
/* 211 */     this.np = 8;
/* 212 */     this.p = new Point[8];
/* 213 */     this.p[0] = new Point(250, 30);
/* 214 */     this.p[1] = new Point(350, 30);
/* 215 */     this.p[2] = new Point(250, 190);
/* 216 */     this.p[3] = new Point(350, 190);
/* 217 */     this.p[4] = new Point(175, 75);
/* 218 */     this.p[5] = new Point(175, 145);
/* 219 */     this.p[6] = new Point(425, 75);
/* 220 */     this.p[7] = new Point(425, 145);
/*     */   }
/*     */   
/*     */   private void initializeProblem9()
/*     */   {
/* 225 */     this.np = 9;
/* 226 */     this.p = new Point[this.np];
/* 227 */     this.p[0] = new Point(40, 40);
/* 228 */     this.p[1] = new Point(40, 245);
/* 229 */     this.p[2] = new Point(230, 145);
/* 230 */     this.p[3] = new Point(420, 40);
/* 231 */     this.p[4] = new Point(420, 245);
/* 232 */     this.p[5] = new Point(40, 40);
/* 233 */     this.p[6] = new Point(40, 245);
/* 234 */     this.p[7] = new Point(230, 145);
/* 235 */     this.p[8] = new Point(420, 40);
/*     */   }
/*     */   
/*     */   private void initializeProblem10()
/*     */   {
/* 240 */     this.np = 10;
/* 241 */     this.p = new Point[this.np];
/* 242 */     this.p[0] = new Point(250, 30);
/* 243 */     this.p[1] = new Point(350, 30);
/* 244 */     this.p[2] = new Point(250, 190);
/* 245 */     this.p[3] = new Point(350, 190);
/* 246 */     this.p[4] = new Point(175, 75);
/* 247 */     this.p[5] = new Point(175, 145);
/* 248 */     this.p[6] = new Point(425, 75);
/* 249 */     this.p[7] = new Point(425, 145);
/* 250 */     this.p[8] = new Point(425, 145);
/* 251 */     this.p[9] = new Point(420, 40);
/*     */   }
/*     */   
/*     */   public void initializeDes() {
/* 255 */     this.lineP = new String[(IORTSPCanvas.access$1(this.this$0).data.num_city * IORTSPCanvas.access$1(this.this$0).data.num_city - IORTSPCanvas.access$1(this.this$0).data.num_city) / 2];
/* 256 */     int num = 0;
/* 257 */     for (int i = 0; i < IORTSPCanvas.access$1(this.this$0).data.num_city; i++) {
/* 258 */       for (int j = 0; j < IORTSPCanvas.access$1(this.this$0).data.num_city; j++) {
/* 259 */         if ((i != j) && (j > i)) {
/* 260 */           if (IORTSPCanvas.access$1(this.this$0).data.matrix[i][j] == Integer.MAX_VALUE)
/*     */           {
/* 262 */             this.lineP[num] = String.valueOf(String.valueOf(Integer.toString(i))).concat(String.valueOf(String.valueOf(Integer.toString(j))));
/*     */           }
/*     */           else
/*     */           {
/* 266 */             this.lineP[num] = "NULL";
/*     */           }
/* 268 */           num++;
/*     */         }
/*     */       }
/*     */     }
/* 272 */     this.this$0.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setCanvasRatio(double x, double y)
/*     */   {
/* 278 */     this.rx = x;
/* 279 */     this.ry = y;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void draw(Graphics g)
/*     */   {
/* 286 */     Graphics2D g2 = (Graphics2D)g;
/*     */     
/*     */ 
/* 289 */     Color c = g2.getColor();
/* 290 */     Stroke st = g2.getStroke();
/*     */     
/* 292 */     for (int i = 0; i < this.p.length; i++) {
/* 293 */       g2.drawOval((int)(this.p[i].x * this.rx - 12), (int)(this.p[i].y * this.ry - 12), 24, 24);
/* 294 */       g2.drawString(this.this$0.drawS[i], (int)(this.p[i].x * this.rx - 3), (int)(this.p[i].y * this.ry + 3));
/*     */     }
/* 296 */     String com = "";
/* 297 */     boolean isDrawLine = true;
/* 298 */     if (IORTSPCanvas.access$1(this.this$0).data.isDrawLine) {
/* 299 */       for (int i = 0; i < IORTSPCanvas.access$1(this.this$0).data.num_city; i++) {
/* 300 */         for (int j = 0; j < IORTSPCanvas.access$1(this.this$0).data.num_city; j++) {
/* 301 */           if ((i != j) && (j > i)) {
/* 302 */             com = String.valueOf(String.valueOf(Integer.toString(i))).concat(String.valueOf(String.valueOf(Integer.toString(j))));
/* 303 */             for (int z = 0; z < this.lineP.length; z++) {
/* 304 */               if (this.lineP[z].equalsIgnoreCase(com)) {
/* 305 */                 isDrawLine = false;
/*     */               }
/*     */             }
/* 308 */             if (isDrawLine) {
/* 309 */               g2.drawLine((int)(this.p[i].x * this.rx), (int)(this.p[i].y * this.ry), (int)(this.p[j].x * this.rx), (int)(this.p[j].y * this.ry));
/*     */             } else {
/* 311 */               isDrawLine = true;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 318 */     g2.setColor(c);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTSPCanvas$NetList.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */