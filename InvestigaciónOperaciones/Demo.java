/*     */ import javax.swing.JButton;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Demo
/*     */   extends Thread
/*     */ {
/*     */   private IORHungarianPanel applet;
/*     */   int phalanx;
/*     */   POMTablePanel table;
/*  14 */   int interval = 1000;
/*  15 */   boolean pause = false;
/*  16 */   float x = 22.0F;
/*  17 */   boolean finish = false;
/*  18 */   boolean pauseAndContinue = false;
/*     */   
/*     */   public Demo(IORHungarianPanel sm, int phalanx) {
/*  21 */     this.applet = sm;
/*  22 */     this.phalanx = phalanx;
/*     */   }
/*     */   
/*     */   public void run() {
/*  26 */     this.table = this.applet.table;
/*  27 */     this.applet.setAdd_Delete(false);
/*  28 */     this.applet.Next.setEnabled(false);
/*  29 */     this.applet.Prev.setEnabled(false);
/*  30 */     if (!this.pauseAndContinue) {
/*  31 */       System.arraycopy(this.applet.data, 0, this.applet.defaultdata, 0, this.applet.data.length);
/*  32 */       this.applet.robot.init(this.applet.data);
/*     */       
/*  34 */       this.applet.robot.next(0);
/*  35 */       this.applet.current = this.applet.currentItem.cur;
/*     */     }
/*  37 */     this.applet.Reset.setLabel("Reset");
/*  38 */     this.applet.Reset.setEnabled(false);
/*  39 */     this.table.leave();
/*  40 */     boolean user_has_participate = false;
/*     */     for (;;) {
/*  42 */       InvalidTable();
/*     */       
/*  44 */       switch (this.applet.current)
/*     */       {
/*     */       case 0: 
/*  47 */         if (!step0())
/*  48 */           user_has_participate = true;
/*  49 */         break;
/*     */       case 10: 
/*  51 */         if (!step10())
/*  52 */           user_has_participate = true;
/*  53 */         break;
/*     */       case 31: 
/*  55 */         if (!step31())
/*  56 */           user_has_participate = true;
/*  57 */         break;
/*     */       case 32: 
/*  59 */         if (!step32())
/*  60 */           user_has_participate = true;
/*  61 */         break;
/*     */       case 41: 
/*  63 */         if (!step41())
/*  64 */           user_has_participate = true;
/*  65 */         break;
/*     */       case 42: 
/*  67 */         if (!step42())
/*  68 */           user_has_participate = true;
/*  69 */         break;
/*     */       case 60: 
/*  71 */         if (!step60())
/*  72 */           user_has_participate = true;
/*  73 */         break;
/*     */       case 70: 
/*  75 */         if (!step70())
/*  76 */           user_has_participate = true;
/*  77 */         this.applet.Reset.setEnabled(true);
/*     */       }
/*     */       
/*  80 */       if (user_has_participate) {
/*  81 */         user_has_participate = false;
/*     */       } else {
/*  83 */         if (this.applet.current == 70) {
/*     */           break;
/*     */         }
/*  86 */         if (this.applet.current != 31) {
/*     */           try {
/*  88 */             Thread.sleep(this.interval);
/*     */           } catch (InterruptedException localInterruptedException) {}
/*     */         }
/*  91 */         if (this.applet.current == 32) {
/*  92 */           if (this.applet.currentItem.next == 41) {
/*  93 */             this.applet.No_actionPerformed(null);
/*     */           } else {
/*  95 */             this.applet.Yes_actionPerformed(null);
/*     */           }
/*     */         } else
/*  98 */           this.applet.Next_actionPerformed(null);
/*     */       }
/* 100 */       this.applet.Next.setEnabled(false);
/* 101 */       this.applet.Prev.setEnabled(false);
/*     */     }
/* 103 */     this.finish = true;
/* 104 */     this.applet.auto_reset.setEnabled(true);
/* 105 */     this.applet.DemoButton.setText("Auto");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void InvalidTable()
/*     */   {
/* 113 */     for (int j = 0; j < this.phalanx + 1; j++) {
/* 114 */       for (int i = 0; i < this.phalanx + 2; i++) {
/* 115 */         this.table.setDataClickState(j, i, false);
/* 116 */         this.table.setDataInputState(j, i, false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   boolean pause(int step) {
/* 122 */     while (this.pause) {
/*     */       try {
/* 124 */         Thread.sleep(100L);
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {}
/* 127 */       this.applet.Reset.setEnabled(true);
/* 128 */       this.x *= 222.22F;
/*     */     }
/* 130 */     this.applet.Reset.setEnabled(false);
/* 131 */     this.applet.Next.setEnabled(false);
/* 132 */     this.applet.Prev.setEnabled(false);
/* 133 */     return this.applet.current == step;
/*     */   }
/*     */   
/*     */   private boolean step0() {
/* 137 */     float[][] result = this.applet.currentItem.result;
/* 138 */     float[][] data = this.applet.currentItem.data;
/* 139 */     for (int i = 0; i < this.phalanx; i++) {
/*     */       try {
/* 141 */         Thread.sleep(this.interval);
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {}
/* 144 */       if (!pause(0))
/* 145 */         return false;
/* 146 */       float min = result[i][this.phalanx];
/* 147 */       int j = 0;
/* 148 */       for (j = 0; j < this.phalanx; j++)
/* 149 */         if (data[i][j] == min)
/*     */           break;
/* 151 */       this.table.setDataBackgroundState(i, j + 1, 1);
/* 152 */       this.applet.clickData(0, i, j + 1);
/*     */     }
/* 154 */     return true;
/*     */   }
/*     */   
/*     */   private boolean step10() {
/* 158 */     float[][] result = this.applet.currentItem.result;
/* 159 */     float[][] data = this.applet.currentItem.data;
/* 160 */     for (int i = 0; i < this.phalanx; i++) {
/*     */       try {
/* 162 */         Thread.sleep(this.interval);
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {}
/* 165 */       if (!pause(10))
/* 166 */         return false;
/* 167 */       float min = result[this.phalanx][i];
/* 168 */       int j = 0;
/* 169 */       for (j = 0; j < this.phalanx; j++)
/* 170 */         if (data[j][i] == min)
/*     */           break;
/* 172 */       this.table.setDataBackgroundState(j, i + 1, 1);
/* 173 */       this.applet.clickData(0, j, i + 1);
/*     */     }
/* 175 */     return true;
/*     */   }
/*     */   
/*     */   private boolean step31() {
/* 179 */     float[][] result = this.applet.currentItem.result;
/* 180 */     float[][] data = this.applet.currentItem.data;
/* 181 */     float count = result[this.phalanx][this.phalanx];
/* 182 */     boolean empty = false;
/* 183 */     int i = 0; for (int j = 0; i <= count; j++) {
/* 184 */       if (!empty) {
/*     */         try {
/* 186 */           Thread.sleep(this.interval);
/*     */         }
/*     */         catch (InterruptedException localInterruptedException) {}
/* 189 */         if (!pause(31))
/* 190 */           return false;
/* 191 */         i++;
/*     */       }
/*     */       else {
/* 194 */         empty = false; }
/* 195 */       if (j < this.phalanx) {
/* 196 */         if (result[this.phalanx][j] == -1) {
/* 197 */           this.applet.clickData(0, this.phalanx, j + 1);
/*     */         } else {
/* 199 */           empty = true;
/*     */         }
/*     */       }
/* 202 */       else if (result[(j - this.phalanx)][this.phalanx] == -1) {
/* 203 */         this.applet.clickData(0, j - this.phalanx, this.phalanx + 1);
/*     */       } else {
/* 205 */         empty = true;
/*     */       }
/*     */     }
/* 208 */     return true;
/*     */   }
/*     */   
/*     */   private boolean step32() {
/* 212 */     return true;
/*     */   }
/*     */   
/*     */   private boolean step41() {
/*     */     try {
/* 217 */       Thread.sleep(this.interval);
/*     */     }
/*     */     catch (InterruptedException localInterruptedException) {}
/* 220 */     if (!pause(41))
/* 221 */       return false;
/* 222 */     float[][] result = this.applet.currentItem.result;
/* 223 */     float[][] data = this.applet.currentItem.data;
/* 224 */     int i = 0;int j = 0;
/* 225 */     float value = result[this.phalanx][this.phalanx];
/* 226 */     boolean ok = false;
/* 227 */     for (i = 0; (i < this.phalanx) && (!ok); i++)
/* 228 */       for (j = 0; (j < this.phalanx) && (!ok); j++)
/* 229 */         if ((result[i][j] == 0) && (data[i][j] == value))
/* 230 */           ok = true;
/* 231 */     this.table.setDataBackgroundState(i - 1, j, 2);
/* 232 */     this.applet.clickData(0, i - 1, j);
/* 233 */     return true;
/*     */   }
/*     */   
/*     */   private boolean step42() {
/* 237 */     return true;
/*     */   }
/*     */   
/*     */   private boolean step60() {
/* 241 */     float[][] result = this.applet.currentItem.result;
/* 242 */     float[][] data = this.applet.currentItem.data;
/* 243 */     int j = 0;
/* 244 */     for (int i = 0; i < this.phalanx; i++) {
/*     */       try {
/* 246 */         Thread.sleep(this.interval);
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {}
/* 249 */       if (!pause(60))
/* 250 */         return false;
/* 251 */       for (j = 0; j < this.phalanx; j++)
/* 252 */         if (result[i][j] == -1)
/*     */           break;
/* 254 */       this.applet.clickData(0, i, j + 1);
/*     */     }
/* 256 */     return true;
/*     */   }
/*     */   
/*     */   private boolean step70() {
/* 260 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\Demo.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */