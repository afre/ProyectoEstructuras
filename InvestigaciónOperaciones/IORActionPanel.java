/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Printable;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.RepaintManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class IORActionPanel
/*     */   extends JPanel
/*     */   implements Printable
/*     */ {
/*  28 */   public JLabel actionStatus = null;
/*  29 */   protected IORState state = null;
/*  30 */   protected JButton bn_back = null;
/*  31 */   protected JButton bn_finish = null;
/*     */   
/*  33 */   private IORProcessController controller = null;
/*     */   
/*  35 */   protected IORActionPanel.ActionHelpPanel currentStepHelpPanel = null;
/*  36 */   protected MultilineLabel help_content_step = null;
/*     */   
/*  38 */   protected IORActionPanel.ActionHelpPanel currentProcedureHelpPanel = null;
/*  39 */   protected MultilineLabel help_content_procedure = null;
/*     */   
/*  41 */   private IORActionPanel self = null;
/*     */   
/*     */ 
/*     */   public IORActionPanel(IORProcessController c)
/*     */   {
/*  46 */     this.self = this;
/*  47 */     this.controller = c;
/*     */     
/*  49 */     setLayout(new BorderLayout());
/*     */     
/*  51 */     IORActionPanel.IORActionControlPanel control = new IORActionPanel.IORActionControlPanel();
/*     */     
/*  53 */     add(control, "South");
/*     */     
/*  55 */     this.actionStatus = new JLabel(" ");
/*  56 */     this.actionStatus.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
/*  57 */     this.actionStatus.setForeground(Color.blue);
/*  58 */     add(this.actionStatus, "North");
/*     */     
/*  60 */     initializeHelpPanels();
/*     */   }
/*     */   
/*     */   protected void setFinishEnabled(boolean b) {
/*  64 */     this.bn_finish.setEnabled(b);
/*  65 */     if (b)
/*  66 */       this.actionStatus.setText(" ");
/*     */   }
/*     */   
/*     */   public abstract void updatePanel();
/*     */   
/*     */   protected abstract void backStep();
/*     */   
/*     */   public static String trim(double d) {
/*  74 */     String s = new Double(d).toString();
/*  75 */     return trim(s);
/*     */   }
/*     */   
/*     */   public static String trim(String s) {
/*  79 */     if (s.endsWith(".0"))
/*  80 */       s = s.substring(0, s.length() - 2);
/*  81 */     if (s.endsWith(".00")) {
/*  82 */       s = s.substring(0, s.length() - 3);
/*     */     }
/*  84 */     return s;
/*     */   }
/*     */   
/*     */   protected JPanel getCurrentStepHelpPanel()
/*     */   {
/*  89 */     if (this.currentStepHelpPanel != null) {
/*  90 */       updateStepHelpPanel();
/*  91 */       return this.currentStepHelpPanel;
/*     */     }
/*     */     
/*  94 */     return null;
/*     */   }
/*     */   
/*     */   public IORState getState() {
/*  98 */     return this.state;
/*     */   }
/*     */   
/*     */ 
/* 102 */   protected JPanel getCurrentProcedureHelpPanel() { return this.currentProcedureHelpPanel; }
/*     */   
/*     */   protected abstract void initializeCurrentStepHelpPanel();
/*     */   
/*     */   protected abstract void initializeCurrentProcedureHelpPanel();
/*     */   
/*     */   protected void initializeHelpPanels() {
/* 109 */     this.currentStepHelpPanel = new IORActionPanel.ActionHelpPanel();
/* 110 */     this.help_content_step = this.currentStepHelpPanel.getContent();
/* 111 */     initializeCurrentStepHelpPanel();
/*     */     
/* 113 */     this.currentProcedureHelpPanel = new IORActionPanel.ActionHelpPanel();
/* 114 */     this.help_content_procedure = this.currentProcedureHelpPanel.getContent();
/* 115 */     initializeCurrentProcedureHelpPanel();
/*     */   }
/*     */   
/*     */   protected void updateStepHelpPanel() {
/* 119 */     initializeCurrentStepHelpPanel();
/*     */   }
/*     */   
/*     */   protected abstract void finishProcedure();
/*     */   
/*     */   private class IORActionControlPanel extends JPanel
/*     */   {
/*     */     public IORActionControlPanel() {
/* 127 */       IORActionPanel.this.bn_back = new JButton("Back");
/* 128 */       IORActionPanel.this.bn_back.addActionListener(new IORActionPanel.1((IORActionControlPanel)this));
/*     */       
/* 130 */       IORActionPanel.this.bn_back.setEnabled(false);
/*     */       
/* 132 */       IORActionPanel.this.bn_finish = new JButton("Finish");
/* 133 */       IORActionPanel.this.bn_finish.addActionListener(new IORActionPanel.2((IORActionControlPanel)this));
/*     */       
/* 135 */       IORActionPanel.this.bn_finish.setEnabled(true);
/*     */       
/* 137 */       setLayout(new BorderLayout());
/* 138 */       add(IORActionPanel.this.bn_back, "West");
/* 139 */       add(IORActionPanel.this.bn_finish, "East");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected class ActionHelpPanel
/*     */     extends JPanel
/*     */   {
/* 148 */     private MultilineLabel content = null;
/*     */     
/*     */     public ActionHelpPanel()
/*     */     {
/* 152 */       setLayout(new BorderLayout());
/* 153 */       setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
/*     */       
/* 155 */       String str = "Help\nContent";
/* 156 */       this.content = new MultilineLabel(str);
/*     */       
/* 158 */       this.content.addFocusListener(new IORActionPanel.3((ActionHelpPanel)this));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 163 */       add(this.content, "Center");
/*     */       
/* 165 */       addComponentListener(new IORActionPanel.4((ActionHelpPanel)this));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 174 */       addKeyListener(new IORActionPanel.5((ActionHelpPanel)this));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private MultilineLabel getContent()
/*     */     {
/* 190 */       return this.content;
/*     */     }
/*     */   }
/*     */   
/*     */   public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {
/* 195 */     if (pi >= 1) {
/* 196 */       return 1;
/*     */     }
/*     */     
/* 199 */     if ((!(this instanceof IORTPActionPanel)) && (!(this instanceof IORITActionPanel)) && (!(this instanceof IORMCCKEquatePanel)) && (!(this instanceof IORMCSSProbaPanel)))
/*     */     {
/* 201 */       RepaintManager.currentManager(this).setDoubleBufferingEnabled(false); }
/* 202 */     Graphics2D g2d = (Graphics2D)g;
/* 203 */     g2d.translate(pf.getImageableX(), pf.getImageableY());
/*     */     
/* 205 */     Dimension d = getSize();
/*     */     
/* 207 */     double scale = Math.min(pf.getImageableWidth() / d.width, pf.getImageableHeight() / d.height);
/*     */     
/* 209 */     if (scale < 1.0D) {
/* 210 */       g2d.scale(scale, scale);
/*     */     }
/* 212 */     print(g2d);
/* 213 */     g2d.dispose();
/* 214 */     RepaintManager.currentManager(this).setDoubleBufferingEnabled(true);
/* 215 */     return 0;
/*     */   }
/*     */   
/*     */   public void LoadFile(ObjectInputStream in) {
/*     */     try {
/* 220 */       this.state = ((IORState)in.readObject());
/*     */     }
/*     */     catch (Exception e) {
/* 223 */       System.out.println("Load file fails");
/* 224 */       return;
/*     */     }
/*     */     
/* 227 */     if (this.bn_back.isVisible()) {
/* 228 */       if ((this.state == null) || (this.state.isEmpty())) {
/* 229 */         this.bn_back.setEnabled(false);
/*     */       } else
/* 231 */         this.bn_back.setEnabled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void SaveFile(ObjectOutputStream out) {
/*     */     try {
/* 237 */       out.writeObject(this.state);
/*     */     }
/*     */     catch (Exception e) {
/* 240 */       System.out.println("Save file fails");
/* 241 */       return;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setprintData() {}
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORActionPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */