/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class graphData
/*    */   implements Serializable
/*    */ {
/* 14 */   public float[] xcoef = { 0.0F, 0.0F };
/*    */   
/* 16 */   public String objfunc = "Max Z = 0x1 + 0x2";
/* 17 */   public allConstraints lp = null;
/*    */   
/* 19 */   public boolean showresult = false;
/* 20 */   public String result = "";
/*    */   
/* 22 */   public boolean showsens = false;
/* 23 */   public SensData senalysData = null;
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\graphData.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */