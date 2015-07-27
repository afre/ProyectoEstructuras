/*     */ import java.util.StringTokenizer;
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
/*     */ public abstract class IORTPActionPanel
/*     */   extends IORActionPanel
/*     */ {
/*     */   protected IORTPProcessController controller;
/*     */   protected int step;
/*  38 */   public Vector operation_sequence = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORTPActionPanel(IORTPProcessController c)
/*     */   {
/*  48 */     super(c);
/*  49 */     this.controller = c;
/*  50 */     this.operation_sequence = new Vector();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getStep()
/*     */   {
/*  57 */     return this.step;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addStep()
/*     */   {
/*  64 */     this.operation_sequence.addElement(new Integer(getStep()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setStep(int s)
/*     */   {
/*  71 */     this.step = s;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IORTPProcessController getController()
/*     */   {
/*  78 */     return this.controller;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getCellString(double a, double b)
/*     */   {
/*  86 */     String str = "";
/*  87 */     if (a != 0) {
/*  88 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORActionPanel.trim(a))));
/*  89 */       if (b > 0) {
/*  90 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("+").append(IORActionPanel.trim(b)).append("M"))))));
/*     */       }
/*  92 */       else if (b < 0) {
/*  93 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("-").append(IORActionPanel.trim(-b)).append("M"))))));
/*     */       }
/*     */       
/*     */     }
/*  97 */     else if (b > 0) {
/*  98 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(IORActionPanel.trim(b))).concat("M"))));
/*     */     }
/* 100 */     else if (b < 0) {
/* 101 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(IORActionPanel.trim(b))).concat("M"))));
/*     */     }
/*     */     else {
/* 104 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(0)));
/*     */     }
/*     */     
/* 107 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getCellString(int a, int b)
/*     */   {
/* 115 */     String str = "";
/* 116 */     if (a != 0) {
/* 117 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(a)).concat("M"))));
/* 118 */       if (b > 0) {
/* 119 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("+".concat(String.valueOf(String.valueOf(b))))));
/*     */       }
/* 121 */       else if (b < 0) {
/* 122 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("-".concat(String.valueOf(String.valueOf(-b))))));
/*     */       }
/*     */       
/*     */     }
/* 126 */     else if (b > 0) {
/* 127 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(b)));
/*     */     }
/* 129 */     else if (b < 0) {
/* 130 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(b)));
/*     */     }
/*     */     else {
/* 133 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(0)));
/*     */     }
/*     */     
/* 136 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static double[] parseStringInput(String input)
/*     */   {
/* 143 */     double[] d = new double[2];
/* 144 */     if ((!parseCellInput(input, "+", d)) && 
/* 145 */       (!parseCellInput(input, "-", d)) && 
/* 146 */       (!parseString(input, d))) {
/* 147 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 151 */     return d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static boolean parseString(String input, double[] c)
/*     */   {
/* 158 */     boolean success = false;
/* 159 */     StringTokenizer st = new StringTokenizer(input);
/*     */     
/* 161 */     if (st.countTokens() == 1) {
/* 162 */       String str0 = st.nextToken();
/*     */       boolean bool1;
/* 164 */       if ((str0.endsWith("M")) || (str0.endsWith("m"))) {
/* 165 */         str0 = removeLastChar(str0);
/* 166 */         c[0] = 0.0D;
/*     */         try
/*     */         {
/* 169 */           c[1] = Double.parseDouble(str0);
/*     */         }
/*     */         catch (NumberFormatException e) {
/* 172 */           return false;
/*     */         }
/*     */         
/* 175 */         success = true;
/*     */       }
/*     */       else {
/* 178 */         c[1] = 0.0D;
/*     */         try
/*     */         {
/* 181 */           c[0] = Double.parseDouble(str0);
/*     */         }
/*     */         catch (NumberFormatException e) {
/* 184 */           return false;
/*     */         }
/*     */         
/* 187 */         success = true;
/*     */       }
/*     */     }
/* 190 */     return success;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean validateStringPair(String[] str)
/*     */   {
/* 198 */     if ((str[0].endsWith("M")) || (str[0].endsWith("m"))) {
/* 199 */       String temp = null;
/* 200 */       temp = str[0];
/* 201 */       str[0] = str[1];
/* 202 */       str[1] = temp;
/*     */     }
/* 204 */     else if ((!str[1].endsWith("M")) && (!str[1].endsWith("m")))
/*     */     {
/*     */ 
/* 207 */       return false;
/*     */     }
/*     */     
/* 210 */     str[1] = removeLastChar(str[1]);
/* 211 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String removeLastChar(String str)
/*     */   {
/* 218 */     String str1 = null;
/* 219 */     int n = str.length();
/* 220 */     if (n > 1) {
/* 221 */       str1 = str.substring(0, n - 1);
/*     */     }
/*     */     else {
/* 224 */       str1 = "1";
/*     */     }
/* 226 */     return str1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean parseCellInput(String input, String delimiter, double[] c)
/*     */   {
/* 234 */     boolean success = false;
/* 235 */     String[] str = new String[2];
/* 236 */     StringTokenizer st = new StringTokenizer(input, delimiter);
/*     */     
/* 238 */     if (st.countTokens() == 2) {
/* 239 */       str[0] = st.nextToken();
/* 240 */       str[1] = String.valueOf(String.valueOf(delimiter)).concat(String.valueOf(String.valueOf(st.nextToken())));
/* 241 */       if (validateStringPair(str)) {
/*     */         try {
/* 243 */           c[0] = Double.parseDouble(str[0]);
/* 244 */           c[1] = Double.parseDouble(str[1]);
/*     */         }
/*     */         catch (NumberFormatException e) {
/* 247 */           return false;
/*     */         }
/* 249 */         success = true;
/*     */       }
/*     */     }
/*     */     
/* 253 */     return success;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTPActionPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */