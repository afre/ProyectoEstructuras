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
/*     */ public class IORTableCell
/*     */ {
/*  24 */   private final int ALGEBRA_FORM = 1;
/*  25 */   private final int TABULAR_FORM = 2;
/*     */   
/*     */   private String str;
/*     */   private double a;
/*     */   private double b;
/*  30 */   public boolean isChanged = false;
/*     */   
/*  32 */   public boolean isBlock = false;
/*     */   
/*  34 */   public int whichForm = 2;
/*     */   
/*  36 */   public String xname = "U";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORTableCell(double eqcoef)
/*     */   {
/*  43 */     this.a = 0.0D;
/*  44 */     this.b = eqcoef;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORTableCell(double mcoef, double eqcoef)
/*     */   {
/*  53 */     this.a = mcoef;
/*  54 */     this.b = eqcoef;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPara(double em, double ec)
/*     */   {
/*  63 */     this.a = em;
/*  64 */     this.b = ec;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDisplayForm(int dspform)
/*     */   {
/*  72 */     if ((dspform < 1) || (dspform > 2)) {
/*  73 */       this.whichForm = 2;
/*     */     } else {
/*  75 */       this.whichForm = dspform;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setXstring(String s)
/*     */   {
/*  83 */     this.xname = s;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static String trimPad(String ins)
/*     */   {
/*  91 */     if (ins.length() == 0)
/*  92 */       return " ";
/*  93 */     while (ins.endsWith("0")) {
/*  94 */       ins = ins.substring(0, ins.length() - 1);
/*     */     }
/*  96 */     if (ins.endsWith("."))
/*  97 */       ins = ins.substring(0, ins.length() - 1);
/*  98 */     return ins;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 106 */     String ret = outpCoef();
/* 107 */     if (this.whichForm == 2)
/* 108 */       return ret;
/* 109 */     if (this.xname.equals(" = ")) {
/* 110 */       return String.valueOf(String.valueOf(this.xname)).concat(String.valueOf(String.valueOf(ret)));
/*     */     }
/* 112 */     if (ret.charAt(0) != '-') {
/* 113 */       return String.valueOf(String.valueOf(new StringBuffer("+").append(ret).append(this.xname)));
/*     */     }
/* 115 */     return String.valueOf(String.valueOf(ret)).concat(String.valueOf(String.valueOf(this.xname)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String outpCoef()
/*     */   {
/* 124 */     DecimalFormat nf = new DecimalFormat();
/*     */     
/* 126 */     if (Math.abs(this.a) < 0.001D) {
/* 127 */       if (Math.abs(this.b - Math.round(this.b)) < 0.001D) {
/* 128 */         return Integer.toString((int)Math.round(this.b));
/*     */       }
/*     */       
/* 131 */       String s = nf.format(this.b);
/* 132 */       return trimPad(s);
/*     */     }
/*     */     
/*     */ 
/* 136 */     if (Math.abs(this.b) < 0.001D)
/*     */     {
/* 138 */       if (Math.abs(this.a - Math.round(this.a)) < 0.001D) {
/* 139 */         return String.valueOf(String.valueOf(Integer.toString((int)Math.round(this.a)))).concat("M");
/*     */       }
/*     */       
/* 142 */       String s = nf.format(this.a);
/* 143 */       return String.valueOf(String.valueOf(trimPad(s))).concat("M");
/*     */     }
/*     */     
/*     */ 
/*     */     String s;
/*     */     
/* 149 */     if (Math.abs(this.a - Math.round(this.a)) < 0.001D)
/*     */     {
/* 151 */       String s = String.valueOf(String.valueOf(Integer.toString((int)Math.round(this.a)))).concat("M ");
/* 152 */       if (this.b > 0) { s = String.valueOf(String.valueOf(s)).concat("+");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 157 */       s = String.valueOf(String.valueOf(trimPad(nf.format(this.a)))).concat("M");
/* 158 */       if (this.b > 0) s = String.valueOf(String.valueOf(s)).concat("+");
/*     */     }
/* 160 */     if (Math.abs(this.b - Math.round(this.b)) < 0.001D) {
/* 161 */       return String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(Integer.toString((int)Math.round(this.b)))));
/*     */     }
/*     */     
/* 164 */     String s2 = nf.format(this.b);
/* 165 */     return String.valueOf(String.valueOf(s)).concat(String.valueOf(String.valueOf(trimPad(s2))));
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTableCell.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */