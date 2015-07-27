/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Vector;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButtonMenuItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORMCEnterPanel
/*     */   extends IORMCActionPanel
/*     */ {
/*     */   private int statenum;
/*  36 */   private WholeNumberField wnf_statenum = null;
/*     */   
/*     */ 
/*     */   private DecimalField[][] matrixField;
/*     */   
/*  41 */   private JPanel mainPanel = new JPanel();
/*  42 */   private JPanel wholePanel = new JPanel();
/*  43 */   private JPanel statePanel = new JPanel();
/*  44 */   private JPanel charPanel = new JPanel();
/*  45 */   private JPanel matrixPanel = new JPanel();
/*     */   
/*     */ 
/*  48 */   private JLabel inputstate = new JLabel("Number of states (<= 10):");
/*  49 */   private JLabel instruction = new JLabel("(To make a new number above take effect, you must press the ENTER key.)");
/*  50 */   private JLabel inputmatrix = new JLabel("Transition Matrix:");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORMCEnterPanel(IORMCProcessController c)
/*     */   {
/*  60 */     super(c);
/*  61 */     add(this.mainPanel, "Center");
/*     */     
/*     */ 
/*  64 */     this.statenum = 1;
/*     */     
/*     */ 
/*  67 */     this.statePanel.add(this.inputstate);
/*  68 */     this.wnf_statenum = new WholeNumberField(1, 3);
/*     */     
/*  70 */     this.wnf_statenum.addActionListener(new ActionListener()
/*     */     {
/*  72 */       public void actionPerformed(ActionEvent e) { IORMCEnterPanel.this.ReFreshTheMatrix(); }
/*  73 */     });
/*  74 */     this.statePanel.add(this.wnf_statenum);
/*     */     
/*     */ 
/*  77 */     this.charPanel.add(this.inputmatrix);
/*     */     
/*     */ 
/*  80 */     this.matrixPanel.setMaximumSize(new Dimension(this.statenum * 40, this.statenum * 25));
/*     */     
/*  82 */     this.matrixPanel.setLayout(new GridLayout(this.statenum, this.statenum));
/*  83 */     this.matrixField = new DecimalField[10][10];
/*     */     
/*  85 */     for (int i = 0; i < this.statenum; i++) {
/*  86 */       for (int j = 0; j < this.statenum; j++) {
/*  87 */         this.matrixField[i][j] = new DecimalField(0.0D, 0, new DecimalFormat());
/*  88 */         this.matrixPanel.add(this.matrixField[i][j]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  93 */     this.wholePanel.setLayout(new BoxLayout(this.wholePanel, 1));
/*  94 */     this.wholePanel.add(Box.createVerticalStrut(10));
/*  95 */     this.wholePanel.add(this.statePanel);
/*  96 */     this.wholePanel.add(Box.createVerticalStrut(20));
/*  97 */     JPanel p = new JPanel();
/*  98 */     p.add(this.instruction);
/*  99 */     this.wholePanel.add(p);
/* 100 */     this.wholePanel.add(Box.createVerticalStrut(20));
/* 101 */     this.wholePanel.add(this.charPanel);
/* 102 */     this.wholePanel.add(Box.createVerticalStrut(10));
/* 103 */     this.wholePanel.add(this.matrixPanel);
/*     */     
/* 105 */     this.mainPanel.add(this.wholePanel, "North");
/*     */     
/* 107 */     setDoubleBuffered(false);
/* 108 */     this.mainPanel.setDoubleBuffered(false);
/* 109 */     this.wholePanel.setDoubleBuffered(false);
/* 110 */     this.matrixPanel.setDoubleBuffered(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void ReFreshTheMatrix()
/*     */   {
/* 121 */     this.statenum = Integer.parseInt(this.wnf_statenum.getText());
/*     */     
/*     */ 
/* 124 */     if (this.statenum < 1) this.statenum = 1;
/* 125 */     if (this.statenum > this.controller.data.max_matrix_dimension_supply) {
/* 126 */       this.statenum = this.controller.data.max_matrix_dimension_supply;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 133 */     this.wnf_statenum.setText(String.valueOf(this.statenum));
/*     */     
/* 135 */     this.matrixPanel.removeAll();
/*     */     
/* 137 */     this.matrixPanel.setMaximumSize(new Dimension(this.statenum * 40, this.statenum * 25));
/*     */     
/* 139 */     this.matrixPanel.setLayout(new GridLayout(this.statenum, this.statenum));
/* 140 */     for (int i = 0; i < this.statenum; i++) {
/* 141 */       for (int j = 0; j < this.statenum; j++)
/*     */       {
/* 143 */         this.matrixField[i][j] = new DecimalField(0.0D, 0, new DecimalFormat());
/* 144 */         this.matrixPanel.add(this.matrixField[i][j]);
/*     */       }
/*     */     }
/*     */     
/* 148 */     this.matrixPanel.revalidate();
/* 149 */     this.matrixPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finishProcedure()
/*     */   {
/* 160 */     double[][] matrixdata = new double[this.statenum][this.statenum];
/*     */     
/*     */ 
/* 163 */     for (int i = 0; i < this.statenum; i++) {
/* 164 */       for (int j = 0; j < this.statenum; j++) {
/* 165 */         matrixdata[i][j] = Double.parseDouble(this.matrixField[i][j].getText());
/*     */       }
/*     */     }
/* 168 */     Vector p = new Vector();
/*     */     
/* 170 */     p.addElement(new Integer(this.statenum));
/*     */     
/* 172 */     p.addElement(matrixdata);
/*     */     
/* 174 */     IOROperation operation = new IOROperation(10101, p);
/*     */     
/*     */ 
/*     */ 
/* 178 */     if (this.controller.solver.doWork(operation, this.controller.data) == false)
/*     */     {
/* 180 */       JOptionPane.showMessageDialog(this, this.controller.solver.getErrInfo());
/*     */     }
/*     */     else {
/* 183 */       this.actionStatus.setText("You have entered a valid transition matrix. Please go to the Procedure menu to continue!");
/* 184 */       this.actionStatus.setVisible(true);
/*     */       
/*     */ 
/* 187 */       revalidate();
/* 188 */       repaint();
/* 189 */       setPanelEnable(false);
/* 190 */       this.controller.mi_chapman_kolmogorov_equations.setEnabled(true);
/* 191 */       this.controller.mi_steady_state_probabilities.setEnabled(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPanelEnable(boolean b)
/*     */   {
/* 200 */     this.wnf_statenum.setEditable(b);
/* 201 */     for (int i = 0; i < this.statenum; i++) {
/* 202 */       for (int j = 0; j < this.statenum; j++) {
/* 203 */         this.matrixField[i][j].setEditable(b);
/*     */       }
/*     */     }
/* 206 */     setFinishEnabled(b);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/* 217 */     double[][] matrixdata = new double[10][10];
/*     */     try
/*     */     {
/* 220 */       this.statenum = ((Integer)in.readObject()).intValue();
/* 221 */       matrixdata = (double[][])in.readObject();
/* 222 */       in.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 225 */       e1.printStackTrace();
/* 226 */       System.out.println("Open fails");
/*     */     }
/*     */     
/* 229 */     this.wnf_statenum.setValue(this.statenum);
/* 230 */     ReFreshTheMatrix();
/* 231 */     for (int i = 0; i < this.statenum; i++) {
/* 232 */       for (int j = 0; j < this.statenum; j++) {
/* 233 */         this.matrixField[i][j].setValue(matrixdata[i][j]);
/*     */       }
/*     */     }
/* 236 */     revalidate();
/* 237 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/* 247 */     double[][] matrixdata = new double[this.statenum][this.statenum];
/*     */     
/*     */ 
/* 250 */     for (int i = 0; i < this.statenum; i++) {
/* 251 */       for (int j = 0; j < this.statenum; j++) {
/* 252 */         matrixdata[i][j] = this.matrixField[i][j].getValue();
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 257 */       out.writeObject(new Integer(this.statenum));
/* 258 */       out.writeObject(matrixdata);
/* 259 */       out.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 262 */       e1.printStackTrace();
/* 263 */       System.out.println("Save fails");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 271 */     String str = "Entering the Transition Matrix\n\n";
/* 272 */     str = String.valueOf(String.valueOf(str)).concat("Entering the (one step) transition matrix requires entering the number of ");
/* 273 */     str = String.valueOf(String.valueOf(str)).concat("rows or columns (i.e., the number of states) and then entering each of the ");
/* 274 */     str = String.valueOf(String.valueOf(str)).concat("(one step) transition probabilities, p(ij).\n\n");
/* 275 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of states\n\n");
/* 276 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of states in the Markov chain, (1 <= number of states <= 10, ");
/* 277 */     str = String.valueOf(String.valueOf(str)).concat("an integer), and then press the ENTER key. In the notation of the book, this number is M+1.\n\n");
/* 278 */     str = String.valueOf(String.valueOf(str)).concat("Entering the transition probabilities\n\n");
/* 279 */     str = String.valueOf(String.valueOf(str)).concat("Enter each of the transition probabilities, p(ij), where i is the current row in the ");
/* 280 */     str = String.valueOf(String.valueOf(str)).concat("matrix and j is the current column (0 <= p(ij) <= 1).\n\n");
/* 281 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 282 */     this.help_content_step.setText(str);
/* 283 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 290 */     String str = "Entering the Transition Matrix\n\n";
/* 291 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to quickly enter the elements of a one-step ");
/* 292 */     str = String.valueOf(String.valueOf(str)).concat("transition matrix P for a discrete time Markov chain, as described in ");
/* 293 */     str = String.valueOf(String.valueOf(str)).concat("Section 2 of the \"Markov Chains\" chapter. You are allowed a maximum of 10 ");
/* 294 */     str = String.valueOf(String.valueOf(str)).concat("states.\n\n");
/* 295 */     str = String.valueOf(String.valueOf(str)).concat("At any step, detailed computer instructions are available (Help menu). To ");
/* 296 */     str = String.valueOf(String.valueOf(str)).concat("undo a mistake, backtrack by pressing the BACK button.");
/* 297 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 299 */     this.help_content_procedure.setText(str);
/* 300 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORMCEnterPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */