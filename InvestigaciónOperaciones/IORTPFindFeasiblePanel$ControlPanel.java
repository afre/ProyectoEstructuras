/*     */ import java.awt.BorderLayout;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.BoxLayout;
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
/*     */ class IORTPFindFeasiblePanel$ControlPanel
/*     */   extends JPanel
/*     */ {
/*     */   private JLabel prompt1;
/*     */   private JPanel p1;
/*     */   private JLabel prompt2;
/*     */   private JButton bn_delete_row;
/*     */   private JButton bn_delete_col;
/*     */   private JPanel p2;
/*     */   private final IORTPFindFeasiblePanel this$0;
/*     */   
/*     */   IORTPFindFeasiblePanel$ControlPanel(IORTPFindFeasiblePanel x$0, IORTPFindFeasiblePanel..3 x$1)
/*     */   {
/* 158 */     this(x$0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private IORTPFindFeasiblePanel$ControlPanel(IORTPFindFeasiblePanel this$0)
/*     */   {
/* 168 */     this.this$0 = this$0;this.prompt1 = null;this.p1 = null;this.prompt2 = null;this.bn_delete_row = null;this.bn_delete_col = null;this.p2 = null;
/*     */     
/*     */ 
/* 171 */     this.prompt1 = new JLabel("Double click a cell to set it as a basic variable.");
/* 172 */     this.p1 = new JPanel();
/*     */     
/* 174 */     this.p1.add(this.prompt1);
/*     */     
/* 176 */     this.bn_delete_row = new JButton("Delete Row");
/* 177 */     this.bn_delete_row.addActionListener(new IORTPFindFeasiblePanel.1((ControlPanel)this));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 191 */     this.bn_delete_col = new JButton("Delete Column");
/* 192 */     this.bn_delete_col.addActionListener(new IORTPFindFeasiblePanel.2((ControlPanel)this));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 206 */     JPanel p21 = new JPanel();
/* 207 */     p21.add(this.prompt2 = new JLabel("Select a row or column to delete"));
/*     */     
/* 209 */     JPanel p22 = new JPanel();
/* 210 */     p22.add(this.bn_delete_row);
/* 211 */     p22.add(this.bn_delete_col);
/* 212 */     this.p2 = new JPanel(new BorderLayout());
/* 213 */     this.p2.add(p21, "North");
/* 214 */     this.p2.add(p22, "South");
/*     */     
/* 216 */     setLayout(new BoxLayout(this, 1));
/* 217 */     add(this.p1);
/* 218 */     add(this.p2);
/*     */     
/* 220 */     updatePanel();
/*     */     
/* 222 */     this.p1.setDoubleBuffered(false);
/* 223 */     this.p2.setDoubleBuffered(false);
/* 224 */     p21.setDoubleBuffered(false);
/* 225 */     p22.setDoubleBuffered(false);
/*     */   }
/*     */   
/*     */ 
/*     */   private void updatePanel()
/*     */   {
/* 231 */     switch (this.this$0.step) {
/*     */     case 1: 
/* 233 */       this.prompt1.setVisible(true);
/* 234 */       this.prompt2.setVisible(false);
/* 235 */       this.bn_delete_row.setVisible(false);
/* 236 */       this.bn_delete_col.setVisible(false);
/* 237 */       break;
/*     */     
/*     */     case 2: 
/* 240 */       if (!IORTPFindFeasiblePanel.access$2(this.this$0).isLastRowOrColumn()) {
/* 241 */         this.prompt1.setVisible(false);
/* 242 */         this.prompt2.setVisible(true);
/* 243 */         this.bn_delete_row.setVisible(true);
/* 244 */         this.bn_delete_col.setVisible(true);
/*     */       }
/* 246 */       break;
/*     */     
/*     */     default: 
/* 249 */       System.err.println("Error in tracing panel steps");
/*     */     }
/*     */     
/*     */     
/* 253 */     revalidate();
/* 254 */     repaint();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTPFindFeasiblePanel$ControlPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */