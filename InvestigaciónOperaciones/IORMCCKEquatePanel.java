/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORMCCKEquatePanel
/*     */   extends IORMCActionPanel
/*     */ {
/*     */   private int powernum;
/*     */   private int statenum;
/*  36 */   private WholeNumberField wnf_powernum = null;
/*     */   
/*     */   private JTextField[][] matrixLabel;
/*     */   
/*     */   private JTextField[][] raisedmatrixLabel;
/*  41 */   private JPanel mainPanel = new JPanel();
/*  42 */   private JPanel powerPanel = new JPanel();
/*  43 */   private IORMCActionPanel.MaxtrixPanel matrixPanel = new IORMCActionPanel.MaxtrixPanel(this);
/*  44 */   private IORMCActionPanel.MaxtrixPanel raisedmatrixPanel = new IORMCActionPanel.MaxtrixPanel(this);
/*  45 */   private JPanel firstPanel = new JPanel();
/*  46 */   private JPanel superscriptPanel = new JPanel();
/*  47 */   private JPanel secondPanel = new JPanel();
/*  48 */   private JLabel superscript = new JLabel();
/*  49 */   private JLabel theraisedmatrix = new JLabel("P");
/*  50 */   private JLabel theraisedmatrixequal = new JLabel(" =   ");
/*  51 */   private JPanel theraisedmatrixPanel = new JPanel();
/*     */   
/*  53 */   private DecimalFormat matreixformat = new DecimalFormat();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORMCCKEquatePanel(IORMCProcessController c)
/*     */   {
/*  63 */     super(c);
/*  64 */     add(this.mainPanel, "Center");
/*     */     
/*     */ 
/*  67 */     JLabel inputpowernumber = new JLabel("Enter the power to which you want to raise the transition matrix, P:");
/*  68 */     JLabel thematrixequal = new JLabel(" =  ");
/*  69 */     JLabel thematrix = new JLabel("P");
/*     */     
/*     */ 
/*  72 */     this.powerPanel.add(inputpowernumber);
/*  73 */     this.wnf_powernum = new WholeNumberField(1, 2);
/*     */     
/*  75 */     this.wnf_powernum.addActionListener(new ActionListener()
/*     */     {
/*  77 */       public void actionPerformed(ActionEvent e) { IORMCCKEquatePanel.this.ReFreshTheRaisedMatrix(); }
/*  78 */     });
/*  79 */     this.powerPanel.add(this.wnf_powernum);
/*     */     
/*  81 */     this.statenum = c.data.matrix_dimension;
/*     */     
/*     */ 
/*  84 */     this.matrixPanel.setLayout(new GridLayout(this.statenum, this.statenum));
/*  85 */     this.matrixLabel = new JTextField[this.statenum][this.statenum];
/*  86 */     for (int i = 0; i < this.statenum; i++)
/*     */     {
/*     */ 
/*  89 */       for (int j = 0; j < this.statenum; j++)
/*     */       {
/*  91 */         this.matrixLabel[i][j] = new JTextField(4);
/*  92 */         this.matrixLabel[i][j].setBackground(getBackground());
/*  93 */         this.matrixLabel[i][j].setEditable(false);
/*  94 */         this.matrixLabel[i][j].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*     */         
/*  96 */         this.matrixLabel[i][j].setHorizontalAlignment(0);
/*  97 */         this.matrixPanel.add(this.matrixLabel[i][j]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 102 */     this.raisedmatrixPanel.setLayout(new GridLayout(this.statenum, this.statenum));
/* 103 */     this.raisedmatrixLabel = new JTextField[this.statenum][this.statenum];
/* 104 */     for (int i = 0; i < this.statenum; i++)
/*     */     {
/*     */ 
/* 107 */       for (int j = 0; j < this.statenum; j++) {
/* 108 */         this.raisedmatrixLabel[i][j] = new JTextField(4);
/* 109 */         this.raisedmatrixLabel[i][j].setBackground(getBackground());
/* 110 */         this.raisedmatrixLabel[i][j].setEditable(false);
/* 111 */         this.raisedmatrixLabel[i][j].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*     */         
/* 113 */         this.raisedmatrixLabel[i][j].setHorizontalAlignment(0);
/* 114 */         this.raisedmatrixPanel.add(this.raisedmatrixLabel[i][j]);
/*     */       }
/*     */     }
/*     */     
/* 118 */     for (int i = 0; i < this.statenum; i++) {
/* 119 */       for (int j = 0; j < this.statenum; j++) {
/* 120 */         this.matrixLabel[i][j].setText(this.matreixformat.format(c.data.matrix[i][j]));
/* 121 */         this.raisedmatrixLabel[i][j].setText(this.matreixformat.format(c.data.matrix[i][j]));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 126 */     this.firstPanel.add(this.matrixPanel);
/* 127 */     this.firstPanel.add(thematrixequal);
/* 128 */     thematrix.setFont(new Font("Default", 0, 18));
/* 129 */     this.firstPanel.add(thematrix);
/*     */     
/*     */ 
/* 132 */     this.secondPanel.add(this.raisedmatrixPanel);
/*     */     
/*     */ 
/* 135 */     this.theraisedmatrixPanel.add(this.theraisedmatrixequal);
/* 136 */     this.theraisedmatrix.setFont(new Font("Default", 0, 18));
/* 137 */     this.theraisedmatrixPanel.add(new ScriptWriter(this.theraisedmatrix, "1", ""));
/*     */     
/* 139 */     this.secondPanel.add(this.theraisedmatrixPanel);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 144 */     this.mainPanel.setAlignmentY(0.0F);
/*     */     
/* 146 */     JLabel lb = new JLabel("(To make a new number above take effect, you must press the ENTER key.)");
/* 147 */     JPanel p0 = new JPanel();
/* 148 */     p0.add(lb);
/*     */     
/* 150 */     JPanel p = new JPanel();
/* 151 */     p.setLayout(new BoxLayout(p, 1));
/* 152 */     p.add(this.powerPanel);
/* 153 */     p.add(p0);
/* 154 */     p.add(Box.createVerticalStrut(40));
/*     */     
/* 156 */     JPanel p2 = new JPanel();
/* 157 */     p2.setLayout(new BorderLayout());
/* 158 */     p2.add(p, "North");
/*     */     
/* 160 */     this.mainPanel.setLayout(new BorderLayout());
/* 161 */     this.mainPanel.add(p2, "North");
/*     */     
/* 163 */     JPanel p3 = new JPanel();
/* 164 */     p3.setLayout(new GridLayout(2, 1));
/* 165 */     p3.add(this.firstPanel);
/* 166 */     p3.add(this.secondPanel);
/*     */     
/* 168 */     this.mainPanel.add(p3, "Center");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 173 */     setDoubleBuffered(false);
/* 174 */     this.mainPanel.setDoubleBuffered(false);
/* 175 */     this.powerPanel.setDoubleBuffered(false);
/* 176 */     this.matrixPanel.setDoubleBuffered(false);
/* 177 */     this.raisedmatrixPanel.setDoubleBuffered(false);
/* 178 */     this.firstPanel.setDoubleBuffered(false);
/* 179 */     this.superscriptPanel.setDoubleBuffered(false);
/* 180 */     this.secondPanel.setDoubleBuffered(false);
/* 181 */     this.theraisedmatrixPanel.setDoubleBuffered(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void ReFreshTheRaisedMatrix()
/*     */   {
/* 191 */     this.powernum = Integer.parseInt(this.wnf_powernum.getText());
/*     */     
/*     */ 
/* 194 */     if (this.powernum < 1) this.powernum = 1;
/* 195 */     if (this.powernum > this.controller.data.max_power_supply) {
/* 196 */       this.powernum = this.controller.data.max_power_supply;
/*     */     }
/*     */     
/* 199 */     this.wnf_powernum.setText(String.valueOf(this.powernum));
/*     */     
/* 201 */     Vector p = new Vector();
/*     */     
/* 203 */     p.addElement(new Integer(this.powernum));
/*     */     
/* 205 */     IOROperation operation = new IOROperation(10102, p);
/*     */     
/*     */ 
/*     */ 
/* 209 */     if (this.controller.solver.doWork(operation, this.controller.data) == false) {
/* 210 */       JOptionPane.showMessageDialog(this, this.controller.solver.getErrInfo());
/*     */     }
/*     */     else
/*     */     {
/* 214 */       for (int i = 0; i < this.statenum; i++) {
/* 215 */         for (int j = 0; j < this.statenum; j++) {
/* 216 */           this.raisedmatrixLabel[i][j].setText(this.matreixformat.format(this.controller.data.raised_matrix[i][j]));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 222 */     this.theraisedmatrixPanel.removeAll();
/* 223 */     this.theraisedmatrixPanel.add(this.theraisedmatrixequal);
/* 224 */     this.theraisedmatrix.setFont(new Font("Default", 0, 18));
/* 225 */     this.theraisedmatrixPanel.add(new ScriptWriter(this.theraisedmatrix, String.valueOf(this.powernum), ""));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 232 */     this.secondPanel.revalidate();
/* 233 */     this.secondPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finishProcedure() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/*     */     try
/*     */     {
/* 249 */       this.powernum = ((Integer)in.readObject()).intValue();
/* 250 */       in.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 253 */       e1.printStackTrace();
/* 254 */       System.out.println("Open fails");
/*     */     }
/* 256 */     this.wnf_powernum.setValue(this.powernum);
/* 257 */     ReFreshTheRaisedMatrix();
/* 258 */     revalidate();
/* 259 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/*     */     try
/*     */     {
/* 270 */       out.writeObject(new Integer(this.powernum));
/* 271 */       out.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 274 */       e1.printStackTrace();
/* 275 */       System.out.println("Save fails");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 283 */     String str = "Chapman-Kolmogorov Equations\n\n";
/* 284 */     str = String.valueOf(String.valueOf(str)).concat("The Chapman-Kolmogorov Equations are used to calculate the n-step transition ");
/* 285 */     str = String.valueOf(String.valueOf(str)).concat("probabilities. To print the results, choose Print to file from the File menu.\n\n");
/* 286 */     str = String.valueOf(String.valueOf(str)).concat("Entering the power by which to multiply the transition matrix\n\n");
/* 287 */     str = String.valueOf(String.valueOf(str)).concat("Enter the power by which to multiply the transition matrix (1 <= n <=99, an ");
/* 288 */     str = String.valueOf(String.valueOf(str)).concat("integer), and then press the ENTER key.");
/* 289 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 290 */     this.help_content_step.setText(str);
/* 291 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 297 */     String str = "Chapman-Kolmogorov Equations\n\n";
/* 298 */     str = String.valueOf(String.valueOf(str)).concat("Given that you have entered the one-step transition matrix P for a ");
/* 299 */     str = String.valueOf(String.valueOf(str)).concat("discrete time Markov Chain (Procedure 1), this procedure uses the Chapman-");
/* 300 */     str = String.valueOf(String.valueOf(str)).concat("Kolmogorov equations to computer the n-step transition probabilities, ");
/* 301 */     str = String.valueOf(String.valueOf(str)).concat("as described in Section 3 of the \"Markov Chains\" chapter. In particular, ");
/* 302 */     str = String.valueOf(String.valueOf(str)).concat("the procedure obtains the n-step transition matrix P(n) by calculating ");
/* 303 */     str = String.valueOf(String.valueOf(str)).concat("P(n) = P^n. You are allowed a maximum of 10 states and n <= 99. The results can ");
/* 304 */     str = String.valueOf(String.valueOf(str)).concat("be printed by choosing Print to file under the File menu.");
/* 305 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 306 */     this.help_content_procedure.setText(str);
/* 307 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORMCCKEquatePanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */