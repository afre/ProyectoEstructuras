/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Domain
/*    */ {
/*    */   private double lowerBound;
/*    */   private double upperBound;
/*    */   private double width;
/*    */   private double lowerValue;
/*    */   private double upperValue;
/*    */   private int size;
/*    */   
/*    */   public Domain(double a, double b, double w)
/*    */   {
/* 32 */     if (w <= 0) w = 1.0D;
/* 33 */     this.width = w;
/* 34 */     if (b < a + w) b = a + w;
/* 35 */     this.lowerBound = a;this.upperBound = b;
/* 36 */     this.lowerValue = (this.lowerBound + 0.5D * this.width);this.upperValue = (this.upperBound - 0.5D * this.width);
/* 37 */     this.size = ((int)Math.rint((b - a) / w));
/*    */   }
/*    */   
/*    */   public Domain(double b)
/*    */   {
/* 42 */     this(0.0D, b, 0.1D * b);
/*    */   }
/*    */   
/*    */   public Domain()
/*    */   {
/* 47 */     this(1.0D);
/*    */   }
/*    */   
/*    */   public int getIndex(double x)
/*    */   {
/* 52 */     if (x < this.lowerBound) return -1;
/* 53 */     if (x > this.upperBound) return this.size;
/* 54 */     return (int)Math.rint((x - this.lowerValue) / this.width);
/*    */   }
/*    */   
/*    */   public double getBound(int i)
/*    */   {
/* 59 */     return this.lowerBound + i * this.width;
/*    */   }
/*    */   
/*    */   public double getValue(int i)
/*    */   {
/* 64 */     return this.lowerValue + i * this.width;
/*    */   }
/*    */   
/*    */   public double getLowerBound()
/*    */   {
/* 69 */     return this.lowerBound;
/*    */   }
/*    */   
/*    */   public double getUpperBound()
/*    */   {
/* 74 */     return this.upperBound;
/*    */   }
/*    */   
/*    */   public double getLowerValue()
/*    */   {
/* 79 */     return this.lowerValue;
/*    */   }
/*    */   
/*    */   public double getUpperValue()
/*    */   {
/* 84 */     return this.upperValue;
/*    */   }
/*    */   
/*    */   public double getWidth()
/*    */   {
/* 89 */     return this.width;
/*    */   }
/*    */   
/*    */   public int getSize()
/*    */   {
/* 94 */     return this.size;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\Domain.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */