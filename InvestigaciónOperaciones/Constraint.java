/*     */ import java.awt.Point;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.DecimalFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Constraint
/*     */   implements Serializable
/*     */ {
/*  28 */   private static int YSHIFT = 320;
/*  29 */   private static int XSHIFT = 20;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  34 */   private static int WIDTH = 320;
/*  35 */   private static int MARGIN = 20;
/*  36 */   private static int CENTERX = WIDTH / 2;
/*  37 */   private static int CENTERY = WIDTH / 2;
/*  38 */   private static int SMALLSIZE = 2;
/*     */   
/*     */ 
/*  41 */   public static final int LESSEQUAL = 1;
/*     */   
/*  43 */   public static final int GREATEQUAL = 2;
/*     */   
/*  45 */   public static final int EQUAL = 3;
/*     */   
/*     */   public float x1_const;
/*     */   
/*     */   public float x2_const;
/*     */   
/*     */   public float right_const;
/*     */   
/*     */   public int inequating;
/*     */   
/*  55 */   public String consString = null;
/*     */   
/*     */   private float slope;
/*     */   private float NormalSlope;
/*     */   private float intercept;
/*  60 */   private int selfzoom = 1000;
/*     */   
/*     */   private float maxcept;
/*  63 */   public Point startp = null;
/*     */   
/*  65 */   public Point endp = null;
/*  66 */   private float margin = 5.0E-4F;
/*     */   
/*  68 */   private DecimalFormat decfm = new DecimalFormat();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Constraint(float c1, float c2, float rightc, int eqsymb)
/*     */   {
/*  80 */     int temp = 1;
/*  81 */     this.x1_const = c1;
/*  82 */     this.x2_const = c2;
/*  83 */     this.right_const = rightc;
/*  84 */     this.inequating = eqsymb;
/*     */     
/*  86 */     this.decfm.setGroupingUsed(false);
/*  87 */     this.decfm.setMaximumFractionDigits(2);
/*     */     
/*  89 */     this.consString = "    ";
/*     */     
/*     */ 
/*     */ 
/*  93 */     if (Math.abs(this.x1_const) > 0.001D)
/*     */     {
/*  95 */       if (Math.abs(this.x1_const + 1) < 0.001D) {
/*  96 */         this.consString = String.valueOf(String.valueOf(this.consString)).concat("- x1");
/*  97 */       } else if (Math.abs(this.x1_const - 1) < 0.001D) {
/*  98 */         this.consString = String.valueOf(String.valueOf(this.consString)).concat(" x1");
/*     */       } else {
/* 100 */         this.consString = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.consString))).append(this.decfm.format(this.x1_const)).append(" x1")));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 105 */     if ((Math.abs(this.x1_const) > 0.001D) && (Math.abs(this.x2_const) > 0.001D))
/*     */     {
/* 107 */       if (this.x2_const > 0.001D) {
/* 108 */         this.consString = String.valueOf(String.valueOf(this.consString)).concat("  +  ");
/*     */       }
/*     */       else {
/* 111 */         this.consString = String.valueOf(String.valueOf(this.consString)).concat("  -  ");
/* 112 */         temp = -1;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 118 */     if (Math.abs(this.x2_const) > 0.001D)
/*     */     {
/* 120 */       if (Math.abs(this.x2_const * temp + 1) < 0.001D) {
/* 121 */         this.consString = String.valueOf(String.valueOf(this.consString)).concat("- x2");
/* 122 */       } else if (Math.abs(this.x2_const * temp - 1) < 0.001D) {
/* 123 */         this.consString = String.valueOf(String.valueOf(this.consString)).concat(" x2");
/*     */       } else {
/* 125 */         this.consString = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.consString))).append(this.decfm.format(this.x2_const * temp)).append(" x2")));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 132 */     if (eqsymb == 1) {
/* 133 */       this.consString = String.valueOf(String.valueOf(this.consString)).concat(String.valueOf(String.valueOf(" <= ".concat(String.valueOf(String.valueOf(this.decfm.format(this.right_const)))))));
/*     */     }
/* 135 */     else if (eqsymb == 2) {
/* 136 */       this.consString = String.valueOf(String.valueOf(this.consString)).concat(String.valueOf(String.valueOf(" >= ".concat(String.valueOf(String.valueOf(this.decfm.format(this.right_const)))))));
/*     */     }
/*     */     else {
/* 139 */       this.consString = String.valueOf(String.valueOf(this.consString)).concat(String.valueOf(String.valueOf(" = ".concat(String.valueOf(String.valueOf(this.decfm.format(this.right_const)))))));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 145 */     if (Math.abs(this.x2_const) < 0.001D)
/*     */     {
/*     */ 
/*     */ 
/* 149 */       this.slope = 99999.0F;
/* 150 */       this.maxcept = (this.right_const / this.x1_const);
/* 151 */       if (Math.abs(this.maxcept) > 0.1D) {
/* 152 */         this.selfzoom = Math.abs((int)((YSHIFT - XSHIFT) * 0.9D / this.maxcept));
/*     */       }
/*     */       
/*     */ 
/*     */     }
/* 157 */     else if (Math.abs(this.x1_const) < 0.001D)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 163 */       this.slope = 0.0F;
/* 164 */       this.maxcept = (this.right_const / this.x2_const);
/* 165 */       if (Math.abs(this.maxcept) > 0.1D) {
/* 166 */         this.selfzoom = Math.abs((int)((YSHIFT - XSHIFT) * 0.9D / this.maxcept));
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/* 175 */       this.slope = ((float)(-1.0D * this.x1_const / this.x2_const));
/* 176 */       this.intercept = (this.right_const / this.x2_const);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 182 */     if (Math.abs(this.slope) < 0.001D)
/*     */     {
/* 184 */       this.NormalSlope = 99999.0F;
/*     */     }
/* 186 */     else if (this.slope >= 99999)
/*     */     {
/* 188 */       this.NormalSlope = 0.0F;
/*     */     }
/*     */     else
/*     */     {
/* 192 */       this.NormalSlope = (1.0F / this.slope);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 200 */       this.maxcept = Math.abs(this.right_const / this.x1_const);
/* 201 */       if (Math.abs(this.right_const / this.x2_const) > this.maxcept) {
/* 202 */         this.maxcept = Math.abs(this.right_const / this.x2_const);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 207 */       if (Math.abs(this.maxcept) > 0.1D) {
/* 208 */         this.selfzoom = Math.abs((int)((YSHIFT - XSHIFT) * 0.9D / this.maxcept));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void withZoom(int zoom)
/*     */   {
/* 225 */     if (Math.abs(this.right_const) > 0.001D) {
/* 226 */       if (Math.abs(this.x2_const) > 0.001D)
/*     */       {
/*     */ 
/* 229 */         this.slope = ((float)(-1.0D * this.x1_const / this.x2_const));
/* 230 */         this.intercept = (zoom * (this.right_const / this.x2_const));
/*     */         
/*     */ 
/*     */ 
/* 234 */         if (Math.abs(this.x1_const) > 0.001D) {
/* 235 */           float xcept = zoom * (this.right_const / this.x1_const);
/* 236 */           this.startp = new Point(XSHIFT, YSHIFT - (int)this.intercept);
/* 237 */           this.endp = new Point(XSHIFT + (int)xcept, YSHIFT);
/*     */         }
/*     */         else {
/* 240 */           this.startp = new Point(XSHIFT, YSHIFT - (int)this.intercept);
/* 241 */           this.endp = new Point(YSHIFT, YSHIFT - (int)this.intercept);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 246 */         this.startp = new Point(XSHIFT + (int)(zoom * (this.right_const / this.x1_const)), YSHIFT);
/* 247 */         this.endp = new Point(XSHIFT + (int)(zoom * (this.right_const / this.x1_const)), XSHIFT);
/*     */       }
/*     */       
/* 250 */       if ((Math.abs(this.NormalSlope) < 99999.0D) && (Math.abs(this.NormalSlope) > 0.0D))
/*     */       {
/* 252 */         if (this.startp.y <= 320)
/*     */         {
/* 254 */           int y = this.startp.y;
/* 255 */           int x = (int)(y * this.NormalSlope);
/* 256 */           this.startp.x += x;
/* 257 */           this.startp.y = 0;
/*     */         }
/*     */         else
/*     */         {
/* 261 */           int y = 660 - this.startp.y;
/* 262 */           int x = (int)(y * this.NormalSlope);
/* 263 */           this.startp.x -= x;
/* 264 */           this.startp.y = 660;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 282 */         if (this.endp.x >= 20) {
/* 283 */           int x = 340 - this.endp.x;
/* 284 */           int y = (int)(x / this.NormalSlope);
/* 285 */           this.endp.y -= y;
/* 286 */           this.endp.x = 340;
/*     */ 
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */     }
/* 297 */     else if (Math.abs(this.NormalSlope) <= 1) {
/* 298 */       int y = WIDTH;
/* 299 */       int x = (int)(y * this.NormalSlope);
/* 300 */       this.startp = new Point(x + 20, 0);
/* 301 */       this.endp = new Point(20, 320);
/*     */     }
/*     */     else
/*     */     {
/* 305 */       int x = WIDTH;
/* 306 */       int y = (int)(x / this.NormalSlope);
/* 307 */       this.startp = new Point(340, 320 - y);
/* 308 */       this.endp = new Point(20, 320);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean feasible(float x1, float x2)
/*     */   {
/* 324 */     switch (this.inequating)
/*     */     {
/*     */     case 1: 
/* 327 */       if ((this.x1_const * x1 + this.x2_const * x2 <= this.right_const) && (Math.abs(x1) > -0.001D) && (Math.abs(x2) > -0.001D)) {
/* 328 */         return true;
/*     */       }
/* 330 */       return false;
/*     */     case 2: 
/* 332 */       if ((this.x1_const * x1 + this.x2_const * x2 >= this.right_const) && (Math.abs(x1) > -0.001D) && (Math.abs(x2) > -0.001D)) {
/* 333 */         return true;
/*     */       }
/* 335 */       return false;
/*     */     case 3: 
/* 337 */       if ((Math.abs(this.x1_const * x1 + this.x2_const * x2 - this.right_const) < 0.001D) && (Math.abs(x1) > -0.001D) && (Math.abs(x2) > -0.001D)) {
/* 338 */         return true;
/*     */       }
/* 340 */       return false;
/*     */     }
/* 342 */     System.out.println("Unkonwn symbol of equation");
/* 343 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getZoom()
/*     */   {
/* 353 */     return this.selfzoom;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 358 */     return this.consString;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\Constraint.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */