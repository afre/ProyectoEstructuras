/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NormalDistribution
/*     */   extends Distribution
/*     */ {
/*  21 */   public static final double C = Math.sqrt(6.283185307179586D);
/*     */   private double mu;
/*     */   private double sigma;
/*     */   private double cSigma;
/*     */   
/*     */   public NormalDistribution(double mu, double sigma) {
/*  27 */     setParameters(mu, sigma);
/*     */   }
/*     */   
/*     */   public NormalDistribution()
/*     */   {
/*  32 */     this(0.0D, 1.0D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setParameters(double m, double s)
/*     */   {
/*  39 */     if (s < 0) s = 1.0D;
/*  40 */     this.mu = m;this.sigma = s;
/*  41 */     this.cSigma = (C * this.sigma);
/*  42 */     double upper = this.mu + 4 * this.sigma;
/*  43 */     double lower = this.mu - 4 * this.sigma;
/*  44 */     double width = (upper - lower) / 100;
/*  45 */     super.setParameters(lower, upper, width, 1);
/*     */   }
/*     */   
/*     */   public double getDensity(double x)
/*     */   {
/*  50 */     double z = (x - this.mu) / this.sigma;
/*  51 */     return Math.exp(-z * z / 2) / this.cSigma;
/*     */   }
/*     */   
/*     */   public double getMaxDensity()
/*     */   {
/*  56 */     return getDensity(this.mu);
/*     */   }
/*     */   
/*     */   public double getMedian()
/*     */   {
/*  61 */     return this.mu;
/*     */   }
/*     */   
/*     */   public double getMean()
/*     */   {
/*  66 */     return this.mu;
/*     */   }
/*     */   
/*     */   public double getVariance()
/*     */   {
/*  71 */     return this.sigma * this.sigma;
/*     */   }
/*     */   
/*     */   public double simulate()
/*     */   {
/*  76 */     double r = Math.sqrt(-2 * Math.log(Math.random()));
/*  77 */     double theta = 6.283185307179586D * Math.random();
/*  78 */     return this.mu + this.sigma * r * Math.cos(theta);
/*     */   }
/*     */   
/*     */   public double getMu()
/*     */   {
/*  83 */     return this.mu;
/*     */   }
/*     */   
/*     */   public void setMu(double m)
/*     */   {
/*  88 */     setParameters(m, this.sigma);
/*     */   }
/*     */   
/*     */   public double getSigma()
/*     */   {
/*  93 */     return this.sigma;
/*     */   }
/*     */   
/*     */   public void setSigma(double s)
/*     */   {
/*  98 */     setParameters(this.mu, s);
/*     */   }
/*     */   
/*     */   public double getCDF(double x)
/*     */   {
/* 103 */     double z = (x - this.mu) / this.sigma;
/* 104 */     if (z >= 0) return 0.5D + 0.5D * Distribution.gammaCDF(z * z / 2, 0.5D);
/* 105 */     return 0.5D - 0.5D * Distribution.gammaCDF(z * z / 2, 0.5D);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\NormalDistribution.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */