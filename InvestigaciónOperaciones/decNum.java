/*    */ import java.io.PrintStream;
/*    */ import java.util.Vector;
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
/*    */ public class decNum
/*    */   extends Thread
/*    */ {
/* 18 */   int customnum = 0;
/* 19 */   Vector enterTime = new Vector();
/*    */   
/* 21 */   private Object csnumObj = new Object();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean deccusnum()
/*    */   {
/* 29 */     boolean wait = false;
/* 30 */     synchronized (this.csnumObj) {
/* 31 */       while (this.customnum < 1) {
/*    */         try {
/* 33 */           this.csnumObj.wait();
/* 34 */           wait = true;
/*    */         } catch (Exception e) {
/* 36 */           System.out.println("wait error!");
/* 37 */           e.printStackTrace();
/*    */         }
/*    */       }
/* 40 */       this.customnum -= 1;
/*    */     }
/* 42 */     return wait;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void plugcusnum()
/*    */   {
/* 49 */     synchronized (this.csnumObj) {
/* 50 */       this.enterTime.addElement(new Long(System.currentTimeMillis()));
/* 51 */       if (this.customnum == 0) {
/* 52 */         this.customnum += 1;
/* 53 */         this.csnumObj.notify();
/*    */       } else {
/* 55 */         this.customnum += 1;
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   synchronized int getcusnum()
/*    */   {
/* 63 */     return this.customnum;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getEnterTime()
/*    */   {
/* 70 */     Long timStr = (Long)this.enterTime.elementAt(0);
/* 71 */     this.enterTime.removeElementAt(0);
/* 72 */     return timStr.longValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void reduceBreak(long brktm)
/*    */   {
/* 81 */     if (this.enterTime.size() < 1)
/* 82 */       return;
/* 83 */     for (int i = 0; i < this.enterTime.size(); i++) {
/* 84 */       Long oldtime = (Long)this.enterTime.elementAt(i);
/* 85 */       this.enterTime.setElementAt(new Long(oldtime.longValue() + brktm), i);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\decNum.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */