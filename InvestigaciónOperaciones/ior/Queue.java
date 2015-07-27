/*    */ package ior;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class Queue extends Vector
/*    */ {
/*    */   public synchronized void enqueue(Object x)
/*    */   {
/*  9 */     super.addElement(x);
/*    */   }
/*    */   
/*    */   public synchronized Object dequeue() {
/* 13 */     if (emptyQueue()) {
/* 14 */       throw new EmptyQueueException();
/*    */     }
/* 16 */     Object x = super.elementAt(0);
/* 17 */     super.removeElementAt(0);
/* 18 */     return x;
/*    */   }
/*    */   
/*    */   public synchronized Object frontQueue() {
/* 22 */     if (emptyQueue()) {
/* 23 */       throw new EmptyQueueException();
/*    */     }
/* 25 */     return super.elementAt(0);
/*    */   }
/*    */   
/*    */   public boolean emptyQueue() {
/* 29 */     return super.isEmpty();
/*    */   }
/*    */   
/*    */   public synchronized void clearQueue() {
/* 33 */     super.removeAllElements();
/*    */   }
/*    */   
/*    */   public int search(Object x) {
/* 37 */     return super.indexOf(x);
/*    */   }
/*    */   
/*    */   public boolean fuuQueue()
/*    */   {
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ior\Queue.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */