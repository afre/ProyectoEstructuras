/*    */ import java.awt.Dimension;
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Image;
/*    */ import java.awt.event.MouseAdapter;
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageArea
/*    */   extends JPanel
/*    */ {
/* 17 */   boolean init = false;
/*    */   String[] str;
/*    */   Graphics g;
/* 20 */   String mes = "";
/*    */   IORHungarianPanel applet;
/*    */   
/* 23 */   public MessageArea(IORHungarianPanel applet) { this.str = new String[10];
/* 24 */     this.applet = applet;
/* 25 */     addMouseListener(new MouseAdapter() {
/*    */       public void mouseClicked(MouseEvent e) {
/* 27 */         MessageArea.this.this_mouseClicked(e);
/*    */       }
/*    */     });
/*    */   }
/*    */   
/*    */   void this_mouseClicked(MouseEvent e) {
/* 33 */     this.applet.table.leave();
/*    */   }
/*    */   
/*    */   void message(String s) {
/* 37 */     this.mes = s;
/* 38 */     if (!this.init) {
/* 39 */       setFont(new Font("Dialog", 0, 12));
/*    */       
/* 41 */       this.init = true;
/*    */     }
/* 43 */     for (int i = 0; i < 10; i++)
/* 44 */       this.str[i] = null;
/* 45 */     int next = s.indexOf('\n');
/* 46 */     int i = 0;
/* 47 */     int prev = 0;
/* 48 */     while (next != -1) {
/* 49 */       this.str[(i++)] = s.substring(prev == 0 ? prev : prev + 1, next);
/* 50 */       prev = next;
/* 51 */       next = s.indexOf('\n', next + 1);
/*    */     }
/* 53 */     this.str[i] = s.substring(prev == 0 ? prev : prev + 1);
/* 54 */     repaint();
/*    */   }
/*    */   
/*    */   public void paint(Graphics g) {
/* 58 */     Image Rzone = createImage(getSize().width, getSize().height);
/* 59 */     Graphics gs = Rzone.getGraphics();
/* 60 */     gs.setClip(0, 0, getSize().width, getSize().height);
/* 61 */     super.paint(g);
/* 62 */     for (int i = 0; i < 10; i++) {
/* 63 */       if (this.str[i] == null)
/*    */         break;
/* 65 */       gs.drawString(this.str[i], 10, 14 + i * 14);
/*    */     }
/* 67 */     g.drawImage(Rzone, 0, 0, this);
/* 68 */     gs.dispose();
/*    */   }
/*    */   
/*    */   public void update(Graphics g) {
/* 72 */     paint(g);
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\MessageArea.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */