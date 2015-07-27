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
/*     */ public abstract class IORTSPActionPanel
/*     */   extends IORActionPanel
/*     */ {
/*     */   protected IORTSPProcessController controller;
/*     */   protected int step;
/*  36 */   public Vector operation_sequence = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORTSPActionPanel(IORTSPProcessController c)
/*     */   {
/*  46 */     super(c);
/*  47 */     this.controller = c;
/*  48 */     this.operation_sequence = new Vector();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getStep()
/*     */   {
/*  55 */     return this.step;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addStep()
/*     */   {
/*  62 */     this.operation_sequence.addElement(new Integer(getStep()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setStep(int s)
/*     */   {
/*  69 */     this.step = s;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IORTSPProcessController getController()
/*     */   {
/*  76 */     return this.controller;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int[] parseStringInput(String input)
/*     */   {
/* 141 */     int[] d = new int[2];
/* 142 */     if ((!parseCellInput(input, "+", d)) && 
/* 143 */       (!parseCellInput(input, "-", d)) && 
/* 144 */       (!parseString(input, d))) {
/* 145 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 149 */     return d;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static boolean parseString(String input, int[] c)
/*     */   {
/* 156 */     boolean success = false;
/* 157 */     StringTokenizer st = new StringTokenizer(input);
/*     */     
/* 159 */     if (st.countTokens() == 1) {
/* 160 */       String str0 = st.nextToken();
/*     */       boolean bool1;
/* 162 */       if ((str0.endsWith("M")) || (str0.endsWith("m"))) {
/* 163 */         str0 = removeLastChar(str0);
/* 164 */         c[0] = 0;
/*     */         try
/*     */         {
/* 167 */           c[1] = Integer.parseInt(str0);
/*     */         }
/*     */         catch (NumberFormatException e) {
/* 170 */           return false;
/*     */         }
/*     */         
/* 173 */         success = true;
/*     */       }
/*     */       else {
/* 176 */         c[1] = 0;
/*     */         try
/*     */         {
/* 179 */           c[0] = Integer.parseInt(str0);
/*     */         }
/*     */         catch (NumberFormatException e) {
/* 182 */           return false;
/*     */         }
/*     */         
/* 185 */         success = true;
/*     */       }
/*     */     }
/* 188 */     return success;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean validateStringPair(String[] str)
/*     */   {
/* 196 */     if ((str[0].endsWith("M")) || (str[0].endsWith("m"))) {
/* 197 */       String temp = null;
/* 198 */       temp = str[0];
/* 199 */       str[0] = str[1];
/* 200 */       str[1] = temp;
/*     */     }
/* 202 */     else if ((!str[1].endsWith("M")) && (!str[1].endsWith("m")))
/*     */     {
/*     */ 
/* 205 */       return false;
/*     */     }
/*     */     
/* 208 */     str[1] = removeLastChar(str[1]);
/* 209 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String removeLastChar(String str)
/*     */   {
/* 216 */     String str1 = null;
/* 217 */     int n = str.length();
/* 218 */     if (n > 1) {
/* 219 */       str1 = str.substring(0, n - 1);
/*     */     }
/*     */     else {
/* 222 */       str1 = "1";
/*     */     }
/* 224 */     return str1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean parseCellInput(String input, String delimiter, int[] c)
/*     */   {
/* 232 */     boolean success = false;
/* 233 */     String[] str = new String[2];
/* 234 */     StringTokenizer st = new StringTokenizer(input, delimiter);
/*     */     
/* 236 */     if (st.countTokens() == 2) {
/* 237 */       str[0] = st.nextToken();
/* 238 */       str[1] = String.valueOf(String.valueOf(delimiter)).concat(String.valueOf(String.valueOf(st.nextToken())));
/* 239 */       if (validateStringPair(str)) {
/*     */         try {
/* 241 */           c[0] = Integer.parseInt(str[0]);
/* 242 */           c[1] = Integer.parseInt(str[1]);
/*     */         }
/*     */         catch (NumberFormatException e) {
/* 245 */           return false;
/*     */         }
/* 247 */         success = true;
/*     */       }
/*     */     }
/*     */     
/* 251 */     return success;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTSPActionPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */