/*     */ import java.text.DecimalFormat;
/*     */ import javax.swing.JComboBox;
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
/*     */ public class IORBound
/*     */   extends JPanel
/*     */ {
/*  29 */   private int num_variable = 0;
/*  30 */   private IORBound.IORBoundField[] bounds = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORBound(int numVar)
/*     */   {
/*  37 */     setNumVariable(numVar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORBound(int numVar, boolean[] op, double[] b)
/*     */   {
/*  47 */     this(numVar);
/*     */     
/*  49 */     for (int i = 1; i <= numVar; i++) {
/*  50 */       int selected = 1;
/*  51 */       if (op[i] != 0) {
/*  52 */         selected = 0;
/*     */       }
/*  54 */       this.bounds[(i - 1)].cb_operator.setSelectedIndex(selected);
/*  55 */       this.bounds[(i - 1)].bound.setValue(b[i]);
/*     */       
/*  57 */       boolean bounded = this.bounds[(i - 1)].isBounded();
/*  58 */       if (bounded) {
/*  59 */         this.bounds[(i - 1)].bound.setEditable(true);
/*     */       }
/*     */       else {
/*  62 */         this.bounds[(i - 1)].bound.setEditable(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void getBound(boolean[] b, double[] rhs)
/*     */   {
/*  74 */     boolean bounded = false;
/*  75 */     for (int i = 0; i < b.length; i++) {
/*  76 */       b[i] = this.bounds[i].isBounded();
/*  77 */       if (b[i] != 0) {
/*  78 */         rhs[i] = this.bounds[i].getBound();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void setEditable(boolean b)
/*     */   {
/*  86 */     for (int i = 0; i < this.bounds.length; i++) {
/*  87 */       this.bounds[i].setEditable(b);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setNumVariable(int numVar)
/*     */   {
/*  95 */     if (this.num_variable == numVar) {
/*  96 */       return;
/*     */     }
/*  98 */     this.num_variable = numVar;
/*     */     
/* 100 */     removeAll();
/* 101 */     this.bounds = new IORBound.IORBoundField[numVar];
/*     */     
/* 103 */     for (int i = 0; i < numVar; i++) {
/* 104 */       this.bounds[i] = new IORBound.IORBoundField();
/* 105 */       int j = i + 1;
/* 106 */       if (i == 0) {
/* 107 */         add(new JLabel("x".concat(String.valueOf(String.valueOf(j)))));
/*     */       } else {
/* 109 */         add(new JLabel(",  x".concat(String.valueOf(String.valueOf(j)))));
/*     */       }
/* 111 */       add(this.bounds[i].cb_operator);
/* 112 */       if (this.bounds[i].bound != null) {
/* 113 */         add(this.bounds[i].bound);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   class IORBoundField {
/* 119 */     private final String[] functionStrings = { " >=", "n/a" };
/*     */     
/* 121 */     private final int GREATER = 0;
/* 122 */     private final int NONE = 1;
/*     */     
/* 124 */     private JComboBox cb_operator = null;
/* 125 */     private DecimalField bound = null;
/*     */     
/*     */     IORBoundField() {
/* 128 */       this.cb_operator = new JComboBox(this.functionStrings);
/* 129 */       this.cb_operator.addActionListener(new IORBound.1((IORBoundField)this));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 140 */       this.bound = new DecimalField(0.0D, new DecimalFormat());
/*     */     }
/*     */     
/*     */     public void setEditable(boolean b) {
/* 144 */       this.cb_operator.setEnabled(b);
/* 145 */       boolean bounded = isBounded();
/* 146 */       if (b) {
/* 147 */         if (bounded) {
/* 148 */           this.bound.setEditable(true);
/*     */         }
/*     */         else {
/* 151 */           this.bound.setEditable(false);
/*     */         }
/*     */       }
/*     */       else {
/* 155 */         this.bound.setEditable(false);
/*     */       }
/*     */     }
/*     */     
/*     */     private boolean isBounded() {
/* 160 */       boolean bounded = false;
/* 161 */       if (this.cb_operator.getSelectedIndex() == 0)
/* 162 */         bounded = true;
/* 163 */       return bounded;
/*     */     }
/*     */     
/*     */     private double getBound() {
/* 167 */       double b = 0.0D;
/* 168 */       if (this.bound != null) {
/* 169 */         b = this.bound.getValue();
/*     */       }
/* 171 */       return b;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORBound.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */