/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Distribution
/*     */ {
/*     */   private Domain domain;
/*     */   private int type;
/*  21 */   public static final int MIXED = 2; public static final int CONTINUOUS = 1; public static final int DISCRETE = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract double getDensity(double paramDouble);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setParameters(double a, double b, double w, int t)
/*     */   {
/*  38 */     if (t < 0) t = 0; else if (t > 2) t = 2;
/*  39 */     this.type = t;
/*  40 */     if (this.type == 0) this.domain = new Domain(a - 0.5D * w, b + 0.5D * w, w); else {
/*  41 */       this.domain = new Domain(a, b, w);
/*     */     }
/*     */   }
/*     */   
/*     */   public Domain getDomain() {
/*  46 */     return this.domain;
/*     */   }
/*     */   
/*     */   public final int getType()
/*     */   {
/*  51 */     return this.type;
/*     */   }
/*     */   
/*     */ 
/*     */   public double getMaxDensity()
/*     */   {
/*  57 */     double max = 0.0D;
/*  58 */     for (int i = 0; i < this.domain.getSize(); i++) {
/*  59 */       double d = getDensity(this.domain.getValue(i));
/*  60 */       if (((d > max ? 1 : 0) & (d < Double.POSITIVE_INFINITY ? 1 : 0)) != 0) max = d;
/*     */     }
/*  62 */     return max;
/*     */   }
/*     */   
/*     */   public double getMean()
/*     */   {
/*  67 */     double sum = 0.0D;
/*  68 */     double w; double w; if (this.type == 0) w = 1.0D; else w = this.domain.getWidth();
/*  69 */     for (int i = 0; i < this.domain.getSize(); i++) {
/*  70 */       double x = this.domain.getValue(i);
/*  71 */       sum += x * getDensity(x) * w;
/*     */     }
/*  73 */     return sum;
/*     */   }
/*     */   
/*     */   public double getVariance()
/*     */   {
/*  78 */     double sum = 0.0D;double mu = getMean();
/*  79 */     double w; double w; if (this.type == 0) w = 1.0D; else w = this.domain.getWidth();
/*  80 */     for (int i = 0; i < this.domain.getSize(); i++) {
/*  81 */       double x = this.domain.getValue(i);
/*  82 */       sum += (x - mu) * (x - mu) * getDensity(x) * w;
/*     */     }
/*  84 */     return sum;
/*     */   }
/*     */   
/*     */   public double getSD()
/*     */   {
/*  89 */     return Math.sqrt(getVariance());
/*     */   }
/*     */   
/*     */ 
/*     */   public double getCDF(double x)
/*     */   {
/*  95 */     double sum = 0.0D;
/*  96 */     double w; double w; if (this.type == 0) w = 1.0D; else w = this.domain.getWidth();
/*  97 */     int j = this.domain.getIndex(x);
/*  98 */     if (j < 0) return 0.0D;
/*  99 */     if (j >= this.domain.getSize()) { return 1.0D;
/*     */     }
/* 101 */     for (int i = 0; i <= j; i++) sum += getDensity(this.domain.getValue(i)) * w;
/* 102 */     if (this.type == 1) {
/* 103 */       double y = this.domain.getValue(j) - 0.5D * w;
/* 104 */       sum += getDensity((x + y) / 2) * (x - y);
/*     */     }
/*     */     
/* 107 */     return sum;
/*     */   }
/*     */   
/*     */ 
/*     */   public double getQuantile(double p)
/*     */   {
/* 113 */     double sum = 0.0D;
/* 114 */     double w; double w; if (this.type == 0) w = 1.0D; else w = this.domain.getWidth();
/* 115 */     if (p <= 0) return this.domain.getLowerValue();
/* 116 */     if (p >= 1) { return this.domain.getUpperValue();
/*     */     }
/* 118 */     int n = this.domain.getSize();int i = 0;
/* 119 */     double x = this.domain.getValue(i);
/* 120 */     sum = getDensity(x) * w;
/* 121 */     while (((sum < p ? 1 : 0) & (i < n ? 1 : 0)) != 0) {
/* 122 */       i++;
/* 123 */       x = this.domain.getValue(i);
/* 124 */       sum += getDensity(x) * w;
/*     */     }
/* 126 */     return x;
/*     */   }
/*     */   
/*     */ 
/*     */   public double simulate()
/*     */   {
/* 132 */     return getQuantile(Math.random());
/*     */   }
/*     */   
/*     */ 
/*     */   public double getMedian()
/*     */   {
/* 138 */     return getQuantile(0.5D);
/*     */   }
/*     */   
/*     */   public double getFailureRate(double x)
/*     */   {
/* 143 */     return getDensity(x) / (1 - getCDF(x));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double perm(double n, int k)
/*     */   {
/* 151 */     if (((k > n ? 1 : 0) | (k < 0 ? 1 : 0)) != 0) { return 0.0D;
/*     */     }
/* 153 */     double prod = 1.0D;
/* 154 */     for (int i = 1; i <= k; i++) prod *= (n - i + 1);
/* 155 */     return prod;
/*     */   }
/*     */   
/*     */ 
/*     */   public static double factorial(int k)
/*     */   {
/* 161 */     return perm(k, k);
/*     */   }
/*     */   
/*     */ 
/*     */   public static double comb(double n, int k)
/*     */   {
/* 167 */     return perm(n, k) / factorial(k);
/*     */   }
/*     */   
/*     */   public static double logGamma(double x)
/*     */   {
/* 172 */     double[] coef = { 76.18009173D, -86.50532033D, 24.01409822D, -1.231739516D, 0.00120858003D, -5.36382E-6D };
/* 173 */     double step = 2.50662827465D;double fpf = 5.5D;
/* 174 */     double t = x - 1;
/* 175 */     double tmp = t + fpf;
/* 176 */     tmp = (t + 0.5D) * Math.log(tmp) - tmp;
/* 177 */     double ser = 1.0D;
/* 178 */     for (int i = 1; i <= 6; i++) {
/* 179 */       t += 1;
/* 180 */       ser += coef[(i - 1)] / t;
/*     */     }
/* 182 */     return tmp + Math.log(step * ser);
/*     */   }
/*     */   
/*     */   public static double gamma(double x)
/*     */   {
/* 187 */     return Math.exp(logGamma(x));
/*     */   }
/*     */   
/*     */ 
/*     */   public static double gammaCDF(double x, double a)
/*     */   {
/* 193 */     if (x <= 0) return 0.0D;
/* 194 */     if (x < a + 1) return gammaSeries(x, a);
/* 195 */     return 1 - gammaCF(x, a);
/*     */   }
/*     */   
/*     */ 
/*     */   private static double gammaSeries(double x, double a)
/*     */   {
/* 201 */     int maxit = 100;
/* 202 */     double eps = 3.0E-7D;
/*     */     
/* 204 */     double sum = 1.0D / a;double ap = a;double gln = logGamma(a);double del = sum;
/* 205 */     for (int n = 1; n <= maxit; n++) {
/* 206 */       ap += 1.0D;
/* 207 */       del = del * x / ap;
/* 208 */       sum += del;
/* 209 */       if (Math.abs(del) < Math.abs(sum) * eps) break;
/*     */     }
/* 211 */     return sum * Math.exp(-x + a * Math.log(x) - gln);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static double gammaCF(double x, double a)
/*     */   {
/* 218 */     int maxit = 100;
/* 219 */     double eps = 3.0E-7D;
/*     */     
/* 221 */     double gln = logGamma(a);double g = 0.0D;double gOld = 0.0D;double a0 = 1.0D;double a1 = x;double b0 = 0.0D;double b1 = 1.0D;double fac = 1.0D;
/*     */     
/* 223 */     for (int n = 1; n <= maxit; n++) {
/* 224 */       double an = 1.0D * n;
/* 225 */       double ana = an - a;
/* 226 */       a0 = (a1 + a0 * ana) * fac;
/* 227 */       b0 = (b1 + b0 * ana) * fac;
/* 228 */       double anf = an * fac;
/* 229 */       a1 = x * a0 + anf * a1;
/* 230 */       b1 = x * b0 + anf * b1;
/* 231 */       if (a1 != 0) {
/* 232 */         fac = 1.0D / a1;
/* 233 */         g = b1 * fac;
/* 234 */         if (Math.abs((g - gOld) / g) < eps) break;
/* 235 */         gOld = g;
/*     */       }
/*     */     }
/* 238 */     return Math.exp(-x + a * Math.log(x) - gln) * g;
/*     */   }
/*     */   
/*     */   public static double betaCDF(double x, double a, double b) {
/*     */     double bt;
/*     */     double bt;
/* 244 */     if (((x == 0 ? 1 : 0) | (x == 1 ? 1 : 0)) != 0) bt = 0.0D; else
/* 245 */       bt = Math.exp(logGamma(a + b) - logGamma(a) - logGamma(b) + a * Math.log(x) + b * Math.log(1 - x));
/* 246 */     if (x < (a + 1) / (a + b + 2)) return bt * betaCF(x, a, b) / a;
/* 247 */     return 1 - bt * betaCF(1 - x, b, a) / b;
/*     */   }
/*     */   
/*     */   private static double betaCF(double x, double a, double b)
/*     */   {
/* 252 */     int maxit = 100;
/* 253 */     double eps = 3.0E-7D;double am = 1.0D;double bm = 1.0D;double az = 1.0D;double qab = a + b;
/* 254 */     double qap = a + 1;double qam = a - 1;double bz = 1 - qab * x / qap;
/* 255 */     for (int m = 1; m <= maxit; m++) {
/* 256 */       double em = m;
/* 257 */       double tem = em + em;
/* 258 */       double d = em * (b - m) * x / ((qam + tem) * (a + tem));
/* 259 */       double ap = az + d * am;
/* 260 */       double bp = bz + d * bm;
/* 261 */       d = -(a + em) * (qab + em) * x / ((a + tem) * (qap + tem));
/* 262 */       double app = ap + d * az;
/* 263 */       double bpp = bp + d * bz;
/* 264 */       double aOld = az;
/* 265 */       am = ap / bpp;
/* 266 */       bm = bp / bpp;
/* 267 */       az = app / bpp;
/* 268 */       bz = 1.0D;
/* 269 */       if (Math.abs(az - aOld) < eps * Math.abs(az)) break;
/*     */     }
/* 271 */     return az;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\Distribution.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */