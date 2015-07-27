/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.PrintStream;
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
/*     */ public class IORLinearEquation
/*     */   extends JPanel
/*     */ {
/*  28 */   private final String[] functionStrings = { "<=", "=", ">=" };
/*  29 */   private final int LESS = 0;
/*  30 */   private final int EQUAL = 1;
/*  31 */   private final int GREATER = 2;
/*     */   
/*  33 */   private DecimalField[] coefficient = null;
/*  34 */   private int operation = 0;
/*  35 */   private JComboBox cb_operator = null;
/*  36 */   private int num_variable = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORLinearEquation(int numVar)
/*     */   {
/*  43 */     this.cb_operator = new JComboBox(this.functionStrings);
/*  44 */     this.cb_operator.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  46 */         IORLinearEquation.this.operation = IORLinearEquation.this.cb_operator.getSelectedIndex();
/*     */       }
/*  48 */     });
/*  49 */     setNumVariable(numVar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORLinearEquation(int numVar, int op, double[] c)
/*     */   {
/*  59 */     this(numVar);
/*  60 */     this.cb_operator.setSelectedIndex(op);
/*  61 */     for (int i = 0; i < numVar + 1; i++) {
/*  62 */       this.coefficient[i].setValue(c[i]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void getCoefficient(double[] c)
/*     */   {
/*  71 */     for (int i = 0; i < c.length; i++) {
/*  72 */       c[i] = this.coefficient[i].getValue();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEditable(boolean b)
/*     */   {
/*  81 */     for (int i = 0; i < this.coefficient.length; i++) {
/*  82 */       this.coefficient[i].setEditable(b);
/*     */     }
/*  84 */     this.cb_operator.setEnabled(b);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCoefficient(double[] c)
/*     */   {
/*  92 */     for (int i = 0; i < this.num_variable + 1; i++) {
/*  93 */       this.coefficient[i].setValue(c[i]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOperator(int opt)
/*     */   {
/* 102 */     if ((opt < 0) || (opt > 2)) {
/* 103 */       System.out.println("invalide operator.");
/* 104 */       return;
/*     */     }
/* 106 */     this.operation = opt;
/* 107 */     this.cb_operator.setSelectedIndex(opt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getOperator()
/*     */   {
/* 115 */     int r = -1;
/* 116 */     this.operation = this.cb_operator.getSelectedIndex();
/* 117 */     switch (this.operation) {
/*     */     case 0: 
/* 119 */       r = 0;
/* 120 */       break;
/*     */     case 1: 
/* 122 */       r = 1;
/* 123 */       break;
/*     */     case 2: 
/* 125 */       r = 2;
/* 126 */       break;
/*     */     default: 
/* 128 */       System.out.println("Wrong operator selection!");
/*     */     }
/* 130 */     return r;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNumVariable(int numVar)
/*     */   {
/* 138 */     if (this.num_variable == numVar) {
/* 139 */       return;
/*     */     }
/* 141 */     this.num_variable = numVar;
/*     */     
/* 143 */     removeAll();
/* 144 */     this.coefficient = new DecimalField[numVar + 1];
/*     */     
/* 146 */     for (int i = 1; i <= numVar; i++) {
/* 147 */       this.coefficient[i] = new DecimalField(0.0D, new DecimalFormat());
/* 148 */       add(this.coefficient[i]);
/* 149 */       if (i == numVar) {
/* 150 */         add(new JLabel("x".concat(String.valueOf(String.valueOf(i)))));
/*     */       } else
/* 152 */         add(new JLabel(String.valueOf(String.valueOf(new StringBuffer("x").append(i).append(" + ")))));
/*     */     }
/* 154 */     add(this.cb_operator);
/* 155 */     this.cb_operator.setSelectedIndex(this.operation);
/* 156 */     this.coefficient[0] = new DecimalField(0.0D, new DecimalFormat());
/* 157 */     add(this.coefficient[0]);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORLinearEquation.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */