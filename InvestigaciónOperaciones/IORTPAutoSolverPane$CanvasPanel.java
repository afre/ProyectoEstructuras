/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JPanel;
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
/*     */ public class IORTPAutoSolverPane$CanvasPanel
/*     */   extends JPanel
/*     */ {
/*     */   private final IORTPAutoSolverPane this$0;
/*     */   
/*     */   IORTPAutoSolverPane$CanvasPanel(IORTPAutoSolverPane this$0)
/*     */   {
/* 118 */     this.this$0 = this$0;
/* 119 */     init();
/*     */   }
/*     */   
/*     */   public void init() {
/*     */     try {
/* 124 */       jbInit();
/*     */     }
/*     */     catch (Exception e) {
/* 127 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private void jbInit() throws Exception
/*     */   {
/* 133 */     IORTPAutoSolverPane.access$2(this.this$0, new Font("Courier", 0, 12));
/* 134 */     IORTPAutoSolverPane.access$4(this.this$0, new Font("Courier", 1, 12));
/* 135 */     setSize(600, 400);
/* 136 */     repaint();
/*     */   }
/*     */   
/* 139 */   public void paint(Graphics g) { if (IORTPAutoSolverPane.access$6(this.this$0) == null)
/* 140 */       IORTPAutoSolverPane.access$7(this.this$0, createImage(getSize().width, getSize().height));
/* 141 */     IORTPAutoSolverPane.access$8(this.this$0, IORTPAutoSolverPane.access$6(this.this$0).getGraphics());
/* 142 */     IORTPAutoSolverPane.access$9(this.this$0).clearRect(0, 0, getSize().width, getSize().height);
/* 143 */     IORTPAutoSolverPane.access$9(this.this$0).setColor(Color.black);
/* 144 */     IORTPAutoSolverPane.access$9(this.this$0).setFont(IORTPAutoSolverPane.access$10(this.this$0));
/* 145 */     if (IORTPAutoSolverPane.access$11(this.this$0)) {
/* 146 */       switch (IORTPAutoSolverPane.access$1(this.this$0).data.num_demand) {
/*     */       case 1: 
/* 148 */         IORTPAutoSolverPane.access$12(this.this$0, 160);
/* 149 */         IORTPAutoSolverPane.access$13(this.this$0, 260);
/* 150 */         IORTPAutoSolverPane.access$14(this.this$0, 152);
/* 151 */         IORTPAutoSolverPane.access$9(this.this$0).drawString("Destination", getSize().width / 2 - 90, IORTPAutoSolverPane.access$15(this.this$0) - 20);
/* 152 */         break;
/*     */       case 2: 
/* 154 */         IORTPAutoSolverPane.access$12(this.this$0, 145);
/* 155 */         IORTPAutoSolverPane.access$13(this.this$0, 270);
/* 156 */         IORTPAutoSolverPane.access$14(this.this$0, 120);
/* 157 */         IORTPAutoSolverPane.access$9(this.this$0).drawString("Destination", getSize().width / 2 - 90, IORTPAutoSolverPane.access$15(this.this$0) - 20);
/* 158 */         break;
/*     */       case 3: 
/* 160 */         IORTPAutoSolverPane.access$12(this.this$0, 130);
/* 161 */         IORTPAutoSolverPane.access$13(this.this$0, 310);
/* 162 */         IORTPAutoSolverPane.access$14(this.this$0, 115);
/* 163 */         IORTPAutoSolverPane.access$9(this.this$0).drawString("Destination", getSize().width / 2 - 80, IORTPAutoSolverPane.access$15(this.this$0) - 20);
/* 164 */         break;
/*     */       case 4: 
/* 166 */         IORTPAutoSolverPane.access$12(this.this$0, 115);
/* 167 */         IORTPAutoSolverPane.access$13(this.this$0, 360);
/* 168 */         IORTPAutoSolverPane.access$14(this.this$0, 120);
/* 169 */         IORTPAutoSolverPane.access$9(this.this$0).drawString("Destination", getSize().width / 2 - 70, IORTPAutoSolverPane.access$15(this.this$0) - 20);
/* 170 */         break;
/*     */       case 5: 
/* 172 */         IORTPAutoSolverPane.access$12(this.this$0, 100);
/* 173 */         IORTPAutoSolverPane.access$13(this.this$0, 380);
/* 174 */         IORTPAutoSolverPane.access$14(this.this$0, 90);
/* 175 */         IORTPAutoSolverPane.access$9(this.this$0).drawString("Destination", getSize().width / 2 - 70, IORTPAutoSolverPane.access$15(this.this$0) - 20);
/* 176 */         break;
/*     */       case 6: 
/* 178 */         IORTPAutoSolverPane.access$12(this.this$0, 90);
/* 179 */         IORTPAutoSolverPane.access$13(this.this$0, 440);
/* 180 */         IORTPAutoSolverPane.access$14(this.this$0, 107);
/* 181 */         IORTPAutoSolverPane.access$9(this.this$0).drawString("Destination", getSize().width / 2 - 50, IORTPAutoSolverPane.access$15(this.this$0) - 20);
/* 182 */         break;
/*     */       case 7: 
/* 184 */         IORTPAutoSolverPane.access$12(this.this$0, 80);
/* 185 */         IORTPAutoSolverPane.access$13(this.this$0, 485);
/* 186 */         IORTPAutoSolverPane.access$14(this.this$0, 110);
/* 187 */         IORTPAutoSolverPane.access$9(this.this$0).drawString("Destination", getSize().width / 2 - 50, IORTPAutoSolverPane.access$15(this.this$0) - 20);
/*     */       }
/*     */       
/*     */       
/*     */ 
/*     */ 
/* 193 */       FontMetrics testF = IORTPAutoSolverPane.access$9(this.this$0).getFontMetrics();
/*     */       
/* 195 */       int characterWid = 6;
/*     */       
/* 197 */       IORTPAutoSolverPane.access$9(this.this$0).setFont(IORTPAutoSolverPane.access$16(this.this$0));
/*     */       
/* 199 */       for (int i = 0; i < IORTPAutoSolverPane.access$1(this.this$0).data.outputV.size(); i++) {
/* 200 */         String temp = (String)IORTPAutoSolverPane.access$1(this.this$0).data.outputV.elementAt(i);
/* 201 */         int length = 0;
/* 202 */         for (int j = 0; j < temp.length(); j++) {
/* 203 */           String tem = temp.substring(j, j + 1);
/*     */           
/* 205 */           characterWid = 8;
/* 206 */           length += characterWid;
/* 207 */           IORTPAutoSolverPane.access$9(this.this$0).drawString(tem, IORTPAutoSolverPane.access$17(this.this$0) + length, IORTPAutoSolverPane.access$15(this.this$0) + i * 20);
/*     */         }
/*     */         
/* 210 */         System.out.println(temp);
/*     */         
/*     */ 
/* 213 */         if (i == 0) {
/* 214 */           IORTPAutoSolverPane.access$9(this.this$0).drawLine(IORTPAutoSolverPane.access$17(this.this$0), IORTPAutoSolverPane.access$15(this.this$0) + i * 20 + 4, IORTPAutoSolverPane.access$17(this.this$0) + IORTPAutoSolverPane.access$18(this.this$0), IORTPAutoSolverPane.access$15(this.this$0) + i * 20 + 4);
/* 215 */         } else if (i == IORTPAutoSolverPane.access$1(this.this$0).data.outputV.size() - 2) {
/* 216 */           IORTPAutoSolverPane.access$9(this.this$0).drawLine(IORTPAutoSolverPane.access$17(this.this$0), IORTPAutoSolverPane.access$15(this.this$0) + i * 20 + 4, IORTPAutoSolverPane.access$17(this.this$0) + IORTPAutoSolverPane.access$18(this.this$0), IORTPAutoSolverPane.access$15(this.this$0) + i * 20 + 4);
/* 217 */         } else if (i == IORTPAutoSolverPane.access$1(this.this$0).data.outputV.size() - 1) {
/* 218 */           IORTPAutoSolverPane.access$9(this.this$0).drawLine(IORTPAutoSolverPane.access$17(this.this$0) + 60, IORTPAutoSolverPane.access$15(this.this$0) - 15, IORTPAutoSolverPane.access$17(this.this$0) + 60, IORTPAutoSolverPane.access$15(this.this$0) + i * 20);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 223 */     g.drawImage(IORTPAutoSolverPane.access$6(this.this$0), 0, 0, this);
/* 224 */     IORTPAutoSolverPane.access$9(this.this$0).dispose();
/*     */   }
/*     */   
/*     */   public void update(Graphics g) {
/* 228 */     paint(g);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTPAutoSolverPane$CanvasPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */