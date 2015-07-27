/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORNAProcessController$Arc
/*     */   implements Serializable
/*     */ {
/*     */   public int begin;
/*     */   public int end;
/*     */   public String arcName;
/*     */   public boolean is_basic;
/*     */   public boolean is_reversed;
/*     */   public double capacity;
/*     */   public double flow;
/*     */   public double cost;
/*     */   
/*     */   public String getArcName()
/*     */   {
/* 149 */     String name = new String("");
/*     */     
/*     */ 
/* 152 */     if ((this.begin <= 0) || (this.end <= 0) || (this.begin >= 25) || (this.end >= 25))
/* 153 */       return "UU";
/* 154 */     char first = (char)(65 + this.begin - 1);
/* 155 */     char second = (char)(65 + this.end - 1);
/*     */     
/* 157 */     if (this.is_reversed) {
/* 158 */       name = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(name))).append(second).append(first)));
/*     */     }
/*     */     else {
/* 161 */       name = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(name))).append(first).append(second)));
/*     */     }
/* 163 */     return name;
/*     */   }
/*     */   
/*     */   public boolean isEqualTo(int b, int e) {
/* 167 */     return (this.begin == b) && (this.end == e);
/*     */   }
/*     */   
/*     */   public int compareTo(Arc a) {
/* 171 */     return getArcName().compareToIgnoreCase(a.getArcName());
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNAProcessController$Arc.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */