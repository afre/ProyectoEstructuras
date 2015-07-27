/*    */ package org.coinor.opents;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Frame;
/*    */ import java.awt.GridLayout;
/*    */ import java.awt.Label;
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.event.WindowAdapter;
/*    */ import java.awt.event.WindowEvent;
/*    */ import java.io.PrintStream;
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
/*    */ public class Main
/*    */ {
/* 32 */   public static final String VERSION = "1.0 exp8";
/*    */   
/*    */ 
/* 35 */   private static final String[] credits = { "OpenTS - Java Tabu Search", "Version 1.0 exp8", "Robert Harder", "rharder@usa.net", "", "Use these classes to help build a ", "structured and efficient Java tabu search." };
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
/*    */   public static String getVersion()
/*    */   {
/* 52 */     return "1.0 exp8";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 59 */     System.out.println("");
/* 60 */     for (int i = 0; i < credits.length; i++)
/* 61 */       System.out.println(credits[i]);
/* 62 */     System.out.println("");
/*    */     try
/*    */     {
/* 65 */       Frame f = new Frame(credits[0]);
/* 66 */       f.setLayout(new GridLayout(credits.length, 1));
/* 67 */       for (int i = 0; i < credits.length; i++)
/* 68 */         f.add(new Label(credits[i], 1));
/* 69 */       f.pack();
/* 70 */       Toolkit t = f.getToolkit();
/* 71 */       f.setLocation((t.getScreenSize().width - f.getSize().width) / 2, (t.getScreenSize().height - f.getSize().height) / 2);
/* 72 */       f.addWindowListener(new WindowAdapter() {
/*    */         public void windowClosing(WindowEvent e) {
/* 74 */           System.exit(0);
/*    */         }
/* 76 */       });
/* 77 */       f.show();
/*    */     }
/*    */     catch (Exception localException) {}
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\Main.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */