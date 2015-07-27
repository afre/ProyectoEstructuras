/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
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
/*     */ public class IORLPObjectiveFunction
/*     */   extends JPanel
/*     */ {
/*  28 */   private final String[] functionStrings = { "Max", "Min" };
/*  29 */   private final int MAX = 0;
/*  30 */   private final int MIN = 1;
/*     */   
/*  32 */   private DecimalField[] coefficient = null;
/*  33 */   private int objective = 0;
/*  34 */   private JComboBox cb_function = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORLPObjectiveFunction(int numVar)
/*     */   {
/*  43 */     this.cb_function = new JComboBox(this.functionStrings);
/*  44 */     this.cb_function.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  46 */         IORLPObjectiveFunction.this.objective = IORLPObjectiveFunction.this.cb_function.getSelectedIndex();
/*     */       }
/*     */       
/*  49 */     });
/*  50 */     setNumVariable(numVar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORLPObjectiveFunction(int numVar, boolean isMax, double[] c)
/*     */   {
/*  62 */     this(numVar);
/*     */     
/*  64 */     if (isMax) {
/*  65 */       this.objective = 0;
/*     */     } else {
/*  67 */       this.objective = 1;
/*     */     }
/*  69 */     this.cb_function.setSelectedIndex(this.objective);
/*     */     
/*  71 */     setNumVariable(numVar);
/*     */     
/*     */ 
/*  74 */     for (int i = 0; i < numVar; i++) {
/*  75 */       if (Math.abs(c[(i + 1)]) < 1.0E-6D) {
/*  76 */         this.coefficient[i].setValue(0.0D);
/*     */       } else {
/*  78 */         this.coefficient[i].setValue(-c[(i + 1)]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Boolean isMax()
/*     */   {
/*  86 */     this.objective = this.cb_function.getSelectedIndex();
/*  87 */     if (this.objective == 0) {
/*  88 */       return new Boolean(true);
/*     */     }
/*  90 */     return new Boolean(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void getCoefficient(double[] c)
/*     */   {
/*  97 */     for (int i = 0; i < c.length; i++) {
/*  98 */       c[i] = this.coefficient[i].getValue();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEditable(boolean b)
/*     */   {
/* 107 */     for (int i = 0; i < this.coefficient.length; i++) {
/* 108 */       this.coefficient[i].setEditable(b);
/*     */     }
/* 110 */     this.cb_function.setEnabled(b);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setNumVariable(int numVar)
/*     */   {
/* 117 */     removeAll();
/* 118 */     add(this.cb_function);
/* 119 */     this.cb_function.setSelectedIndex(this.objective);
/*     */     
/* 121 */     add(new JLabel(" Z = "));
/*     */     
/* 123 */     this.coefficient = new DecimalField[numVar];
/*     */     
/* 125 */     for (int i = 0; i < numVar; i++) {
/* 126 */       this.coefficient[i] = new DecimalField(0.0D, new DecimalFormat());
/* 127 */       int j = i + 1;
/* 128 */       add(this.coefficient[i]);
/* 129 */       if (i == numVar - 1) {
/* 130 */         add(new JLabel("x".concat(String.valueOf(String.valueOf(j)))));
/*     */       } else {
/* 132 */         add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(j).append(" + ")))));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLPObjectiveFunction.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */