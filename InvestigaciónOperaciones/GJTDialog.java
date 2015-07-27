/*    */ import java.awt.Button;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Frame;
/*    */ import java.awt.Panel;
/*    */ 
/*    */ public class GJTDialog extends java.awt.Dialog
/*    */ {
/*  8 */   public static int ID_OK = 1;
/*  9 */   public static int ID_CANCEL = 2;
/* 10 */   public static int ID_YES = 3;
/* 11 */   public static int ID_NO = 4;
/*    */   
/*    */   protected int returnValue;
/*    */   protected boolean centered;
/*    */   private Panel buttonPanel;
/*    */   protected Panel workPanel;
/*    */   
/*    */   public GJTDialog(Frame frame, String title, boolean modal)
/*    */   {
/* 20 */     super(frame, title, modal);
/*    */     
/* 22 */     setLayout(new java.awt.BorderLayout());
/*    */     
/* 24 */     this.workPanel = new Panel();
/* 25 */     add(this.workPanel, "Center");
/* 26 */     this.buttonPanel = new Panel();
/* 27 */     add(this.buttonPanel, "South");
/*    */     
/* 29 */     addWindowListener(new java.awt.event.WindowAdapter() {
/*    */       public void windowClosing(java.awt.event.WindowEvent event) {
/* 31 */         GJTDialog.this.dispose();
/*    */       }
/*    */     });
/*    */   }
/*    */   
/*    */   public void setCentered(boolean centered) {
/* 37 */     this.centered = centered;
/*    */   }
/*    */   
/*    */   public int getReturnValue() {
/* 41 */     return this.returnValue;
/*    */   }
/*    */   
/*    */   public void setVisible(boolean visible) {
/* 45 */     pack();
/*    */     
/* 47 */     if (this.centered) {
/* 48 */       Dimension frameSize = getParent().getSize();
/* 49 */       java.awt.Point frameLoc = getParent().getLocation();
/* 50 */       Dimension mySize = getSize();
/*    */       
/*    */ 
/* 53 */       int x = frameLoc.x + frameSize.width / 2 - mySize.width / 2;
/*    */       
/* 55 */       int y = frameLoc.y + frameSize.height / 2 - mySize.height / 2;
/*    */       
/*    */ 
/* 58 */       setBounds(x, y, getSize().width, getSize().height);
/*    */     }
/* 60 */     super.setVisible(visible);
/*    */   }
/*    */   
/*    */   public Button addButton(String string) {
/* 64 */     Button bn = new Button(string);
/* 65 */     this.buttonPanel.add(bn);
/* 66 */     return bn;
/*    */   }
/*    */   
/*    */   public void addButton(Button button) {
/* 70 */     this.buttonPanel.add(button);
/*    */   }
/*    */   
/*    */   public static Frame getFrame(java.awt.Component c) {
/* 74 */     if ((c instanceof Frame)) {
/* 75 */       return (Frame)c;
/*    */     }
/* 77 */     while ((c = c.getParent()) != null) {
/* 78 */       if ((c instanceof Frame))
/* 79 */         return (Frame)c;
/*    */     }
/* 81 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\GJTDialog.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */