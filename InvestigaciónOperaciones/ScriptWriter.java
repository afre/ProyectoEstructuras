/*     */ import java.awt.Font;
/*     */ import java.awt.GridLayout;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JComponent;
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
/*     */ public class ScriptWriter
/*     */   extends JComponent
/*     */ {
/*     */   public ScriptWriter(JLabel X, String s1, String s2)
/*     */   {
/*  53 */     Font f = X.getFont();
/*  54 */     int size = f.getSize() * 2 / 3;
/*  55 */     int style = f.getStyle();
/*  56 */     String name = f.getFontName();
/*  57 */     f = new Font(name, style, size);
/*     */     
/*  59 */     setLayout(new BoxLayout(this, 0));
/*  60 */     add(X);
/*     */     
/*  62 */     JPanel p = new JPanel();
/*  63 */     p.setLayout(new GridLayout(2, 1));
/*  64 */     JLabel lb = new JLabel(s1);
/*  65 */     lb.setFont(f);
/*  66 */     p.add(lb);
/*  67 */     lb = new JLabel(s2);
/*  68 */     lb.setFont(f);
/*  69 */     p.add(lb);
/*  70 */     add(p);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ScriptWriter(JLabel X, String s1, String s2, int multiple, int divide)
/*     */   {
/*  84 */     Font f = X.getFont();
/*  85 */     int size = f.getSize() * multiple / divide;
/*  86 */     int style = f.getStyle();
/*  87 */     String name = f.getFontName();
/*  88 */     f = new Font(name, style, size);
/*     */     
/*  90 */     setLayout(new BoxLayout(this, 0));
/*  91 */     add(X);
/*     */     
/*  93 */     JPanel p = new JPanel();
/*  94 */     p.setLayout(new GridLayout(2, 1));
/*  95 */     JLabel lb = new JLabel(s1);
/*  96 */     lb.setFont(f);
/*  97 */     p.add(lb);
/*  98 */     lb = new JLabel(s2);
/*  99 */     lb.setFont(f);
/* 100 */     p.add(lb);
/* 101 */     add(p);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ScriptWriter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */