/*     */ import java.awt.Color;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IORTPSolvePanel$ControlPanel
/*     */   extends JPanel
/*     */ {
/*     */   private IORTPSolvePanel.ControlPanel.UVInputPanel uvInputPanel;
/*     */   private JLabel prompt1;
/*     */   private JLabel prompt2;
/*     */   private JLabel prompt3;
/*     */   private JPanel p;
/*     */   private final IORTPSolvePanel this$0;
/*     */   
/*     */   IORTPSolvePanel$ControlPanel(IORTPSolvePanel x$0, IORTPSolvePanel..2 x$1)
/*     */   {
/* 167 */     this(x$0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private IORTPSolvePanel$ControlPanel(IORTPSolvePanel this$0)
/*     */   {
/* 176 */     this.this$0 = this$0;this.uvInputPanel = null;this.prompt1 = null;this.prompt2 = null;this.prompt3 = null;this.p = null;
/* 177 */     this.uvInputPanel = new IORTPSolvePanel.ControlPanel.UVInputPanel(null);
/*     */     
/* 179 */     this.p = new JPanel();
/* 180 */     this.prompt1 = new JLabel("Indicate your choice for the entering basic variable. Select a cell and then double click it.");
/* 181 */     this.prompt1.setForeground(Color.red);
/* 182 */     this.prompt2 = new JLabel("Show the chain reaction caused by increasing the entering basic variable by double clicking along the path.");
/* 183 */     this.prompt2.setForeground(Color.black);
/* 184 */     this.prompt3 = new JLabel("Indicate your choice for the leaving basic variable. Select a cell and then double click it.");
/* 185 */     this.prompt3.setForeground(Color.blue);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 191 */     add(this.uvInputPanel);
/* 192 */     add(this.p);
/*     */     
/* 194 */     this.uvInputPanel.setDoubleBuffered(false);
/* 195 */     this.p.setDoubleBuffered(false);
/*     */   }
/*     */   
/*     */   public void showUV() {
/* 199 */     this.uvInputPanel.showUV();
/* 200 */     revalidate();
/* 201 */     repaint();
/*     */   }
/*     */   
/*     */   public void hideUV() {
/* 205 */     this.uvInputPanel.hideUV();
/* 206 */     revalidate();
/* 207 */     repaint();
/*     */   }
/*     */   
/*     */   public void updatePanel() {
/* 211 */     switch (this.this$0.step) {
/*     */     case 0: 
/* 213 */       this.p.removeAll();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 231 */       break;
/*     */     case 1: 
/* 217 */       this.p.removeAll();
/* 218 */       this.p.add(this.prompt1);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 231 */       break;
/*     */     case 2: 
/* 222 */       this.p.removeAll();
/* 223 */       this.p.add(this.prompt2);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 231 */       break;
/*     */     case 3: 
/* 227 */       this.p.removeAll();
/* 228 */       this.p.add(this.prompt3); }
/*     */   }
/*     */   
/*     */   private class UVInputPanel
/*     */     extends JPanel {
/* 233 */     UVInputPanel(IORTPSolvePanel..2 x$1) { this(); }
/* 234 */     private JButton bn_OK = null;
/* 235 */     private JLabel prompt = null;
/*     */     
/*     */     private UVInputPanel() {
/* 238 */       this.prompt = new JLabel("Press OK when you finish entering the values for u(i) and v(j) ");
/* 239 */       this.bn_OK = new JButton("OK");
/* 240 */       this.bn_OK.addActionListener(new IORTPSolvePanel.1((UVInputPanel)this));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 250 */       add(this.prompt);
/* 251 */       add(this.bn_OK);
/*     */     }
/*     */     
/*     */     private void hideUV() {
/* 255 */       this.prompt.setVisible(false);
/* 256 */       this.bn_OK.setVisible(false);
/*     */     }
/*     */     
/*     */     private void showUV() {
/* 260 */       this.bn_OK.setVisible(true);
/* 261 */       this.prompt.setVisible(true);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTPSolvePanel$ControlPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */