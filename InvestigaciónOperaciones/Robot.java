/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Stack;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Robot
/*     */   implements Serializable
/*     */ {
/*     */   int phalanx;
/*     */   private IORHungarianPanel applet;
/*  43 */   private boolean cycle = false;
/*  44 */   private Stack history = new Stack();
/*     */   private boolean[][] step3data;
/*     */   
/*  47 */   public Robot(IORHungarianPanel applet, int phalanx) { this.applet = applet;
/*  48 */     this.phalanx = phalanx;
/*     */   }
/*     */   
/*     */   void prev(int step) {
/*  52 */     this.history.pop();
/*  53 */     Robot.HistoryItem data = (Robot.HistoryItem)this.history.peek();
/*  54 */     this.applet.updateData(data);
/*     */   }
/*     */   
/*     */   void next(int step) {
/*  58 */     Robot.HistoryItem item = (Robot.HistoryItem)this.history.peek();
/*  59 */     switch (item.next) {
/*     */     case 0: 
/*  61 */       step0();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  79 */       break;
/*     */     case 10: 
/*  64 */       step10();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  79 */       break;
/*     */     case 31: 
/*  67 */       step31();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  79 */       break;
/*     */     case 41: 
/*  70 */       step41();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  79 */       break;
/*     */     case 60: 
/*  73 */       step60();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  79 */       break;
/*     */     case 70: 
/*  76 */       step70();
/*     */     }
/*     */   }
/*     */   
/*     */   void init(String[] data)
/*     */   {
/*  82 */     Robot.HistoryItem item = new Robot.HistoryItem();
/*  83 */     item.cur = -1;
/*  84 */     item.next = 0;
/*  85 */     item.data = mallocArray(this.phalanx, 0);
/*  86 */     item.result = mallocArray(this.phalanx + 1, 0);
/*  87 */     for (int i = 0; i < this.phalanx; i++)
/*  88 */       for (int j = 0; j < this.phalanx; j++)
/*     */       {
/*  90 */         item.data[i][j] = Float.parseFloat(data[(i * (this.phalanx + 2) + j + 1)]); }
/*  91 */     this.history.push(item);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean examine3(Vector userlines)
/*     */   {
/*  98 */     Robot.HistoryItem item = (Robot.HistoryItem)this.history.peek();
/*  99 */     if (userlines.size() != item.result[this.phalanx][this.phalanx])
/* 100 */       return false;
/* 101 */     float[][] map = cloneArray(item.result);
/* 102 */     for (int i = 0; i <= this.phalanx; i++)
/* 103 */       for (int j = 0; j <= this.phalanx; j++)
/* 104 */         map[i][j] = 0.0F;
/* 105 */     for (int i = 0; i < userlines.size(); i++) {
/* 106 */       int temp = ((Integer)userlines.elementAt(i)).intValue();
/* 107 */       if (temp < this.phalanx) {
/* 108 */         for (int j = 0; j <= this.phalanx; j++) {
/* 109 */           map[j][temp] = -1.0F;
/*     */         }
/*     */       }
/* 112 */       for (int j = 0; j <= this.phalanx; j++) {
/* 113 */         map[(temp - this.phalanx)][j] = -1.0F;
/*     */       }
/*     */     }
/* 116 */     for (int i = 0; i < this.phalanx; i++)
/* 117 */       for (int j = 0; j < this.phalanx; j++)
/* 118 */         if ((item.data[i][j] == 0) && (map[i][j] == 0))
/* 119 */           return false;
/* 120 */     for (int i = 0; i < this.phalanx; i++) {
/* 121 */       item.result[i][this.phalanx] = 0.0F;
/* 122 */       item.result[this.phalanx][i] = 0.0F;
/*     */     }
/* 124 */     for (int i = 0; i < userlines.size(); i++) {
/* 125 */       int temp = ((Integer)userlines.elementAt(i)).intValue();
/* 126 */       if (temp < this.phalanx) {
/* 127 */         item.result[this.phalanx][temp] = -1.0F;
/*     */       } else
/* 129 */         item.result[(temp - this.phalanx)][this.phalanx] = -1.0F;
/*     */     }
/* 131 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean examine6(Vector usercells)
/*     */   {
/* 138 */     if (usercells.size() != this.phalanx * 2)
/* 139 */       return false;
/* 140 */     Robot.HistoryItem item = (Robot.HistoryItem)this.history.peek();
/* 141 */     boolean[][] map = mallocArray(this.phalanx, true);
/*     */     
/* 143 */     for (int i = 0; i < this.phalanx * 2; i += 2) {
/* 144 */       int row = ((Integer)usercells.elementAt(i)).intValue();
/* 145 */       int column = ((Integer)usercells.elementAt(i + 1)).intValue() - 1;
/* 146 */       if ((map[row][column] != 0) || (item.data[row][column] != 0))
/*     */         break;
/* 148 */       for (int m = 0; m < this.phalanx; m++) {
/* 149 */         map[row][m] = 1;
/* 150 */         map[m][column] = 1;
/*     */       }
/*     */     }
/* 153 */     if (i == this.phalanx * 2) {
/* 154 */       for (i = 0; i <= this.phalanx; i++)
/* 155 */         for (int j = 0; j <= this.phalanx; j++)
/* 156 */           item.result[i][j] = 0.0F;
/* 157 */       for (i = 0; i < this.phalanx * 2; i += 2) {
/* 158 */         int row = ((Integer)usercells.elementAt(i)).intValue();
/* 159 */         int column = ((Integer)usercells.elementAt(i + 1)).intValue() - 1;
/*     */         
/* 161 */         item.result[row][column] = -1.0F;
/*     */       }
/* 163 */       return true;
/*     */     }
/*     */     
/* 166 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   private int[] determine3(float[][] data)
/*     */   {
/* 172 */     this.step3data = mallocArray(this.phalanx, true);
/* 173 */     int TotalLines = this.phalanx * 2;
/* 174 */     int[] lines = new int[TotalLines];
/*     */     
/* 176 */     for (int x1 = 0; x1 < TotalLines; x1++) {
/* 177 */       lines[0] = x1;
/* 178 */       if (cover3(lines, 1, data)) {
/* 179 */         this.cycle = (this.phalanx != 1);
/* 180 */         int[] ret = new int[1];
/* 181 */         System.arraycopy(lines, 0, ret, 0, ret.length);
/* 182 */         return ret;
/*     */       }
/*     */     }
/* 185 */     for (int x1 = 0; x1 < TotalLines - 1; x1++) {
/* 186 */       lines[0] = x1;
/* 187 */       for (int x2 = x1 + 1; x2 < TotalLines; x2++) {
/* 188 */         lines[1] = x2;
/* 189 */         if (cover3(lines, 2, data)) {
/* 190 */           this.cycle = (this.phalanx != 2);
/* 191 */           int[] ret = new int[2];
/* 192 */           System.arraycopy(lines, 0, ret, 0, ret.length);
/* 193 */           return ret;
/*     */         }
/*     */       }
/*     */     }
/* 197 */     for (int x1 = 0; x1 < TotalLines - 2; x1++) {
/* 198 */       lines[0] = x1;
/* 199 */       for (int x2 = x1 + 1; x2 < TotalLines - 1; x2++) {
/* 200 */         lines[1] = x2;
/* 201 */         for (int x3 = x2 + 1; x3 < TotalLines; x3++) {
/* 202 */           lines[2] = x3;
/* 203 */           if (cover3(lines, 3, data)) {
/* 204 */             this.cycle = (this.phalanx != 3);
/* 205 */             int[] ret = new int[3];
/* 206 */             System.arraycopy(lines, 0, ret, 0, ret.length);
/* 207 */             return ret;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 212 */     for (int x1 = 0; x1 < TotalLines - 3; x1++) {
/* 213 */       lines[0] = x1;
/* 214 */       for (int x2 = x1 + 1; x2 < TotalLines - 2; x2++) {
/* 215 */         lines[1] = x2;
/* 216 */         for (int x3 = x2 + 1; x3 < TotalLines - 1; x3++) {
/* 217 */           lines[2] = x3;
/* 218 */           for (int x4 = x3 + 1; x4 < TotalLines; x4++) {
/* 219 */             lines[3] = x4;
/* 220 */             if (cover3(lines, 4, data)) {
/* 221 */               this.cycle = (this.phalanx != 4);
/* 222 */               int[] ret = new int[4];
/* 223 */               System.arraycopy(lines, 0, ret, 0, ret.length);
/* 224 */               return ret;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 230 */     for (int x1 = 0; x1 < TotalLines - 4; x1++) {
/* 231 */       lines[0] = x1;
/* 232 */       for (int x2 = x1 + 1; x2 < TotalLines - 3; x2++) {
/* 233 */         lines[1] = x2;
/* 234 */         for (int x3 = x2 + 1; x3 < TotalLines - 2; x3++) {
/* 235 */           lines[2] = x3;
/* 236 */           for (int x4 = x3 + 1; x4 < TotalLines - 1; x4++) {
/* 237 */             lines[3] = x4;
/* 238 */             for (int x5 = x4 + 1; x5 < TotalLines; x5++) {
/* 239 */               lines[4] = x5;
/* 240 */               if (cover3(lines, 5, data)) {
/* 241 */                 this.cycle = (this.phalanx != 5);
/* 242 */                 int[] ret = new int[5];
/* 243 */                 System.arraycopy(lines, 0, ret, 0, ret.length);
/* 244 */                 return ret;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 251 */     for (int x1 = 0; x1 < TotalLines - 5; x1++) {
/* 252 */       lines[0] = x1;
/* 253 */       for (int x2 = x1 + 1; x2 < TotalLines - 4; x2++) {
/* 254 */         lines[1] = x2;
/* 255 */         for (int x3 = x2 + 1; x3 < TotalLines - 3; x3++) {
/* 256 */           lines[2] = x3;
/* 257 */           for (int x4 = x3 + 1; x4 < TotalLines - 2; x4++) {
/* 258 */             lines[3] = x4;
/* 259 */             for (int x5 = x4 + 1; x5 < TotalLines - 1; x5++) {
/* 260 */               lines[4] = x5;
/* 261 */               for (int x6 = x5 + 1; x6 < TotalLines; x6++) {
/* 262 */                 lines[5] = x6;
/* 263 */                 if (cover3(lines, 6, data)) {
/* 264 */                   this.cycle = (this.phalanx != 6);
/* 265 */                   int[] ret = new int[6];
/* 266 */                   System.arraycopy(lines, 0, ret, 0, ret.length);
/*     */                   
/* 268 */                   return ret;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 276 */     return null;
/*     */   }
/*     */   
/*     */   private boolean cover3(int[] lines, int num, float[][] data) {
/* 280 */     for (int i = 0; i < this.phalanx; i++)
/* 281 */       for (int j = 0; j < this.phalanx; j++)
/* 282 */         this.step3data[i][j] = 0;
/* 283 */     for (int i = 0; i < num; i++) {
/* 284 */       if (lines[i] < this.phalanx) {
/* 285 */         for (int j = 0; j < this.phalanx; j++) {
/* 286 */           this.step3data[j][lines[i]] = 1;
/*     */         }
/*     */       }
/* 289 */       for (int j = 0; j < this.phalanx; j++) {
/* 290 */         this.step3data[(lines[i] - this.phalanx)][j] = 1;
/*     */       }
/*     */     }
/* 293 */     for (int i = 0; i < this.phalanx; i++)
/* 294 */       for (int j = 0; j < this.phalanx; j++)
/* 295 */         if ((this.step3data[i][j] == 0) && (data[i][j] == 0))
/* 296 */           return false;
/* 297 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   private void determine6(float[][] data, float[][] result)
/*     */   {
/*     */     int a;
/* 304 */     switch (this.phalanx) {
/*     */     case 3: 
/* 306 */       for (a = 0; a < this.phalanx;) {
/* 307 */         if (data[0][a] == 0) {
/* 308 */           determine6_fillcol(result, a, true);
/*     */           
/*     */ 
/* 311 */           for (int b = 0; b < this.phalanx; b++)
/* 312 */             if ((result[1][b] == 0) && (data[1][b] == 0)) {
/* 313 */               determine6_fillcol(result, b, true);
/*     */               
/*     */ 
/* 316 */               for (int c = 0; c < this.phalanx; c++) {
/* 317 */                 if ((result[2][c] == 0) && (data[2][c] == 0)) {
/* 318 */                   for (int i = 0; i < this.phalanx; i++)
/* 319 */                     for (int j = 0; j < this.phalanx; j++)
/* 320 */                       result[i][j] = 0.0F;
/* 321 */                   result[0][a] = (result[1][b] = result[2][c] = -1.0F);
/* 322 */                   return;
/*     */                 }
/*     */               }
/*     */               
/* 326 */               determine6_fillcol(result, b, false);
/*     */             }
/* 328 */           determine6_fillcol(result, a, false);
/*     */         }
/* 306 */         a++; continue;
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 332 */         for (a = 0; a < this.phalanx;) {
/* 333 */           if (data[0][a] == 0) {
/* 334 */             determine6_fillcol(result, a, true);
/*     */             
/*     */ 
/* 337 */             for (int b = 0; b < this.phalanx; b++)
/* 338 */               if ((result[1][b] == 0) && (data[1][b] == 0)) {
/* 339 */                 determine6_fillcol(result, b, true);
/*     */                 
/*     */ 
/* 342 */                 for (int c = 0; c < this.phalanx; c++)
/* 343 */                   if ((result[2][c] == 0) && (data[2][c] == 0)) {
/* 344 */                     determine6_fillcol(result, c, true);
/*     */                     
/*     */ 
/* 347 */                     for (int d = 0; d < this.phalanx; d++) {
/* 348 */                       if ((result[3][d] == 0) && (data[3][d] == 0)) {
/* 349 */                         for (int i = 0; i < this.phalanx; i++)
/* 350 */                           for (int j = 0; j < this.phalanx; j++)
/* 351 */                             result[i][j] = 0.0F;
/* 352 */                         result[0][a] = (result[1][b] = result[2][c] = result[3][d] = -1.0F);
/*     */                         
/* 354 */                         return;
/*     */                       }
/*     */                     }
/*     */                     
/* 358 */                     determine6_fillcol(result, c, false);
/*     */                   }
/* 360 */                 determine6_fillcol(result, b, false);
/*     */               }
/* 362 */             determine6_fillcol(result, a, false);
/*     */           }
/* 332 */           a++; continue;
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 366 */           for (a = 0; a < this.phalanx;) {
/* 367 */             if (data[0][a] == 0) {
/* 368 */               determine6_fillcol(result, a, true);
/*     */               
/*     */ 
/* 371 */               for (int b = 0; b < this.phalanx; b++)
/* 372 */                 if ((result[1][b] == 0) && (data[1][b] == 0)) {
/* 373 */                   determine6_fillcol(result, b, true);
/*     */                   
/*     */ 
/* 376 */                   for (int c = 0; c < this.phalanx; c++)
/* 377 */                     if ((result[2][c] == 0) && (data[2][c] == 0)) {
/* 378 */                       determine6_fillcol(result, c, true);
/*     */                       
/*     */ 
/* 381 */                       for (int d = 0; d < this.phalanx; d++)
/* 382 */                         if ((result[3][d] == 0) && (data[3][d] == 0)) {
/* 383 */                           determine6_fillcol(result, d, true);
/*     */                           
/*     */ 
/* 386 */                           for (int e = 0; e < this.phalanx; e++) {
/* 387 */                             if ((result[4][e] == 0) && (data[4][e] == 0)) {
/* 388 */                               for (int i = 0; i < this.phalanx; i++)
/* 389 */                                 for (int j = 0; j < this.phalanx; j++)
/* 390 */                                   result[i][j] = 0.0F;
/* 391 */                               result[0][a] = (result[1][b] = result[2][c] = result[3][d] = result[4][e] = -1.0F);
/*     */                               
/*     */ 
/* 394 */                               return;
/*     */                             }
/*     */                           }
/*     */                           
/* 398 */                           determine6_fillcol(result, d, false);
/*     */                         }
/* 400 */                       determine6_fillcol(result, c, false);
/*     */                     }
/* 402 */                   determine6_fillcol(result, b, false);
/*     */                 }
/* 404 */               determine6_fillcol(result, a, false);
/*     */             }
/* 366 */             a++; continue;
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 408 */             for (int a = 0; a < this.phalanx; a++)
/* 409 */               if (data[0][a] == 0) {
/* 410 */                 determine6_fillcol(result, a, true);
/*     */                 
/*     */ 
/* 413 */                 for (int b = 0; b < this.phalanx; b++)
/* 414 */                   if ((result[1][b] == 0) && (data[1][b] == 0)) {
/* 415 */                     determine6_fillcol(result, b, true);
/*     */                     
/*     */ 
/* 418 */                     for (int c = 0; c < this.phalanx; c++)
/* 419 */                       if ((result[2][c] == 0) && (data[2][c] == 0)) {
/* 420 */                         determine6_fillcol(result, c, true);
/*     */                         
/*     */ 
/* 423 */                         for (int d = 0; d < this.phalanx; d++)
/* 424 */                           if ((result[3][d] == 0) && (data[3][d] == 0)) {
/* 425 */                             determine6_fillcol(result, d, true);
/*     */                             
/*     */ 
/* 428 */                             for (int e = 0; e < this.phalanx; e++)
/* 429 */                               if ((result[4][e] == 0) && (data[4][e] == 0)) {
/* 430 */                                 determine6_fillcol(result, e, true);
/*     */                                 
/*     */ 
/* 433 */                                 for (int f = 0; f < this.phalanx; f++) {
/* 434 */                                   if ((result[5][f] == 0) && (data[5][f] == 0))
/*     */                                   {
/* 436 */                                     for (int i = 0; i < this.phalanx; i++)
/* 437 */                                       for (int j = 0; j < this.phalanx; j++)
/* 438 */                                         result[i][j] = 0.0F;
/* 439 */                                     result[0][a] = (result[1][b] = result[2][c] = result[3][d] = result[4][e] = result[5][f] = -1.0F);
/*     */                                     
/*     */ 
/*     */ 
/* 443 */                                     return;
/*     */                                   }
/*     */                                 }
/*     */                                 
/* 447 */                                 determine6_fillcol(result, e, false);
/*     */                               }
/* 449 */                             determine6_fillcol(result, d, false);
/*     */                           }
/* 451 */                         determine6_fillcol(result, c, false);
/*     */                       }
/* 453 */                     determine6_fillcol(result, b, false);
/*     */                   }
/* 455 */                 determine6_fillcol(result, a, false);
/*     */               }
/*     */           }
/*     */         } } }
/*     */     int a;
/*     */     int a; }
/*     */   
/* 462 */   private void determine6_fillcol(float[][] result, int column, boolean b) { for (int i = 0; i < this.phalanx; i++)
/* 463 */       result[i][column] = (b ? -1 : 0);
/*     */   }
/*     */   
/*     */   private void step0() {
/* 467 */     Robot.HistoryItem prev = (Robot.HistoryItem)this.history.peek();
/* 468 */     Robot.HistoryItem current = new Robot.HistoryItem();
/* 469 */     current.data = cloneArray(prev.data);
/* 470 */     current.result = mallocArray(this.phalanx + 1, 0);
/* 471 */     current.prev = prev.cur;
/* 472 */     current.cur = 0;
/* 473 */     current.next = 10;
/* 474 */     for (int i = 0; i < this.phalanx; i++) {
/* 475 */       float min = Float.MAX_VALUE;
/* 476 */       for (int j = 0; j < this.phalanx; j++)
/* 477 */         min = Math.min(current.data[i][j], min);
/* 478 */       current.result[i][this.phalanx] = min;
/*     */     }
/* 480 */     this.applet.updateData(current);
/* 481 */     this.history.push(current);
/*     */   }
/*     */   
/*     */   private void step10() {
/* 485 */     Robot.HistoryItem prev = (Robot.HistoryItem)this.history.peek();
/* 486 */     Robot.HistoryItem current = new Robot.HistoryItem();
/* 487 */     current.data = cloneArray(prev.data);
/* 488 */     current.result = mallocArray(this.phalanx + 1, 0);
/* 489 */     current.prev = prev.cur;
/* 490 */     current.cur = 10;
/* 491 */     current.next = 31;
/* 492 */     for (int i = 0; i < this.phalanx; i++)
/* 493 */       for (int j = 0; j < this.phalanx; j++)
/* 494 */         current.data[i][j] -= prev.result[i][this.phalanx];
/* 495 */     for (int i = 0; i < this.phalanx; i++) {
/* 496 */       float min = Float.MAX_VALUE;
/* 497 */       for (int j = 0; j < this.phalanx; j++)
/* 498 */         min = Math.min(current.data[j][i], min);
/* 499 */       current.result[this.phalanx][i] = min;
/*     */     }
/* 501 */     this.applet.updateData(current);
/* 502 */     this.history.push(current);
/*     */   }
/*     */   
/*     */   private void step31()
/*     */   {
/* 507 */     Robot.HistoryItem prev = (Robot.HistoryItem)this.history.peek();
/* 508 */     Robot.HistoryItem current = new Robot.HistoryItem();
/* 509 */     current.data = cloneArray(prev.data);
/* 510 */     current.result = mallocArray(this.phalanx + 1, 0);
/* 511 */     if (prev.cur == 10) {
/* 512 */       for (int i = 0; i < this.phalanx; i++)
/* 513 */         for (int j = 0; j < this.phalanx; j++)
/* 514 */           current.data[j][i] -= prev.result[this.phalanx][i];
/*     */     }
/* 516 */     if (prev.cur == 41) {
/* 517 */       float min = prev.result[this.phalanx][this.phalanx];
/* 518 */       for (int i = 0; i < this.phalanx; i++) {
/* 519 */         for (int j = 0; j < this.phalanx; j++)
/* 520 */           if (prev.result[i][j] == 2) {
/* 521 */             current.data[i][j] += min;
/* 522 */           } else if (prev.result[i][j] == 0)
/* 523 */             current.data[i][j] -= min;
/*     */       }
/*     */     }
/* 526 */     int[] lines = determine3(current.data);
/* 527 */     for (int i = 0; i < lines.length; i++) {
/* 528 */       if (lines[i] < this.phalanx) {
/* 529 */         current.result[this.phalanx][lines[i]] = -1.0F;
/*     */       } else
/* 531 */         current.result[(lines[i] - this.phalanx)][this.phalanx] = -1.0F;
/*     */     }
/* 533 */     current.result[this.phalanx][this.phalanx] = lines.length;
/* 534 */     current.prev = prev.cur;
/* 535 */     current.cur = 31;
/* 536 */     current.next = (this.cycle ? 41 : 60);
/* 537 */     this.applet.updateData(current);
/* 538 */     this.history.push(current);
/*     */   }
/*     */   
/*     */   private void step41()
/*     */   {
/* 543 */     Robot.HistoryItem prev = (Robot.HistoryItem)this.history.peek();
/* 544 */     Robot.HistoryItem current = new Robot.HistoryItem();
/* 545 */     current.data = cloneArray(prev.data);
/* 546 */     current.result = cloneArray(prev.result);
/* 547 */     current.prev = prev.cur;
/* 548 */     current.cur = 41;
/* 549 */     current.next = 31;
/* 550 */     for (int i = 0; i < this.phalanx; i++) {
/* 551 */       if (current.result[i][this.phalanx] == -1)
/* 552 */         for (int j = 0; j < this.phalanx; j++)
/* 553 */           current.result[i][j] += 1.0F;
/* 554 */       if (current.result[this.phalanx][i] == -1)
/* 555 */         for (int j = 0; j < this.phalanx; j++)
/* 556 */           current.result[j][i] += 1.0F;
/*     */     }
/* 558 */     float min = Float.MAX_VALUE;
/* 559 */     for (int i = 0; i < this.phalanx; i++)
/* 560 */       for (int j = 0; j < this.phalanx; j++)
/* 561 */         if (current.result[i][j] == 0)
/* 562 */           min = Math.min(min, current.data[i][j]);
/* 563 */     current.result[this.phalanx][this.phalanx] = min;
/* 564 */     this.applet.updateData(current);
/* 565 */     this.history.push(current);
/*     */   }
/*     */   
/*     */   private void step60() {
/* 569 */     Robot.HistoryItem prev = (Robot.HistoryItem)this.history.peek();
/* 570 */     Robot.HistoryItem current = new Robot.HistoryItem();
/* 571 */     current.data = cloneArray(prev.data);
/* 572 */     current.result = mallocArray(this.phalanx + 1, 0);
/* 573 */     current.prev = prev.cur;
/* 574 */     current.cur = 60;
/* 575 */     current.next = 70;
/* 576 */     determine6(current.data, current.result);
/* 577 */     this.applet.updateData(current);
/* 578 */     this.history.push(current);
/*     */   }
/*     */   
/*     */   private void step70() {
/* 582 */     Robot.HistoryItem prev = (Robot.HistoryItem)this.history.peek();
/* 583 */     Robot.HistoryItem current = new Robot.HistoryItem();
/* 584 */     current.data = cloneArray(prev.data);
/* 585 */     current.result = cloneArray(prev.result);
/* 586 */     current.prev = prev.cur;
/* 587 */     current.cur = 70;
/* 588 */     current.next = 80;
/* 589 */     this.applet.updateData(current);
/* 590 */     this.history.push(current);
/*     */   }
/*     */   
/*     */   private float[][] mallocArray(int num, int type) {
/* 594 */     float[][] temp = new float[num][num];
/* 595 */     for (int i = 0; i < num; i++)
/* 596 */       for (int j = 0; j < num; j++)
/* 597 */         temp[i][j] = 0.0F;
/* 598 */     return temp;
/*     */   }
/*     */   
/*     */   private boolean[][] mallocArray(int num, boolean type) {
/* 602 */     boolean[][] temp = new boolean[num][num];
/* 603 */     for (int i = 0; i < num; i++)
/* 604 */       for (int j = 0; j < num; j++)
/* 605 */         temp[i][j] = 0;
/* 606 */     return temp;
/*     */   }
/*     */   
/*     */   private float[][] cloneArray(float[][] data) {
/* 610 */     int num = data.length;
/* 611 */     float[][] temp = new float[num][num];
/* 612 */     for (int j = 0; j < num; j++)
/* 613 */       for (int i = 0; i < num; i++)
/* 614 */         temp[i][j] = data[i][j];
/* 615 */     return temp;
/*     */   }
/*     */   
/*     */   protected void saveStatus(ObjectOutputStream out) {
/*     */     try {
/* 620 */       out.writeBoolean(this.cycle);
/* 621 */       out.writeObject(this.step3data);
/* 622 */       out.writeObject(this.history);
/* 623 */       out.write(this.phalanx);
/*     */     } catch (Exception e) {
/* 625 */       System.out.println("write robot error@");
/*     */     }
/*     */   }
/*     */   
/*     */   protected void getStatus(ObjectInputStream in) {
/*     */     try {
/* 631 */       this.cycle = in.readBoolean();
/* 632 */       this.step3data = ((boolean[][])in.readObject());
/* 633 */       this.history = ((Stack)in.readObject());
/* 634 */       this.phalanx = in.read();
/*     */     } catch (Exception e) {
/* 636 */       System.out.println("get robot error@");
/*     */     }
/*     */   }
/*     */   
/*     */   class HistoryItem
/*     */     implements Serializable
/*     */   {
/*     */     float[][] data;
/*     */     float[][] result;
/*     */     int prev;
/*     */     int cur;
/*     */     int next;
/*     */     
/*     */     HistoryItem() {}
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\Robot.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */