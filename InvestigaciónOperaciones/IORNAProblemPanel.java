/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
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
/*     */ 
/*     */ 
/*     */ public class IORNAProblemPanel
/*     */   extends IORActionPanel
/*     */ {
/*  37 */   protected IORNAProcessController controller = null;
/*     */   
/*  39 */   public JPanel mainPanel = new JPanel();
/*  40 */   protected JPanel enterPanel = new JPanel();
/*  41 */   protected JPanel headPanel = new JPanel();
/*  42 */   protected JLabel problabel = new JLabel("This procedure can be used to solve Problem 9.7-2 through 9.7-8.");
/*  43 */   protected JLabel enterlabel = new JLabel("Select the last digit of the problem you would like to solve: 9.7- ");
/*  44 */   protected JComboBox problem = new JComboBox();
/*  45 */   protected JButton okbutt = new JButton("OK");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORNAProblemPanel(IORNAProcessController c)
/*     */   {
/*  55 */     super(c);
/*  56 */     this.controller = c;
/*  57 */     add(this.mainPanel);
/*     */     
/*     */ 
/*  60 */     for (int i = 2; i < 9; i++) {
/*  61 */       this.problem.addItem(new Integer(i));
/*     */     }
/*     */     
/*  64 */     this.enterlabel.setForeground(Color.blue);
/*  65 */     this.enterPanel.add(this.enterlabel);
/*  66 */     this.enterPanel.add(this.problem);
/*  67 */     this.enterPanel.setMaximumSize(new Dimension(500, 50));
/*  68 */     this.enterPanel.setMinimumSize(new Dimension(500, 30));
/*  69 */     this.problabel.setForeground(Color.blue);
/*  70 */     this.headPanel.add(this.problabel);
/*  71 */     this.headPanel.setMaximumSize(new Dimension(500, 20));
/*  72 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*  73 */     this.mainPanel.add(this.headPanel);
/*  74 */     this.mainPanel.add(this.enterPanel);
/*  75 */     this.mainPanel.add(this.okbutt);
/*     */     
/*  77 */     this.okbutt.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  79 */         IORNAProblemPanel.this.doProblem();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void doProblem()
/*     */   {
/*  89 */     Vector par = new Vector();
/*  90 */     IOROperation opr = new IOROperation(1, par);
/*     */     
/*  92 */     int which_prob = ((Integer)this.problem.getSelectedItem()).intValue();
/*  93 */     par.addElement((Integer)this.problem.getSelectedItem());
/*  94 */     opr.operation_code = 30110;
/*  95 */     opr.parameters = par;
/*  96 */     this.controller.solver.doWork(opr, this.controller.data);
/*     */     
/*  98 */     this.controller.solveInteractive();
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
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updatePanel() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 124 */     String str = "Solve Interactively by the Network Simplex Method\n\n";
/* 125 */     str = String.valueOf(String.valueOf(str)).concat("This routine enables you to execute the Network Simplex Method as presented ");
/* 126 */     str = String.valueOf(String.valueOf(str)).concat("in Sec. 9.7. Your role is to apply the logic of the algorithm, and the computer ");
/* 127 */     str = String.valueOf(String.valueOf(str)).concat("will do the number crunching. The computer also will inform you if you make ");
/* 128 */     str = String.valueOf(String.valueOf(str)).concat("a mistake on the first iteration.\n\n");
/* 129 */     str = String.valueOf(String.valueOf(str)).concat("This routine can only be used to solve problems 9.7-2 through 9.7-8 at the ");
/* 130 */     str = String.valueOf(String.valueOf(str)).concat("end of Chap. 9.\n\n");
/* 131 */     str = String.valueOf(String.valueOf(str)).concat("When you finish a problem, you can print out all your work by choosing ");
/* 132 */     str = String.valueOf(String.valueOf(str)).concat("Print to file under the File menu. If you are interrupted before you finish, ");
/* 133 */     str = String.valueOf(String.valueOf(str)).concat("you can save your work (choose Save under the File menu) and return later ");
/* 134 */     str = String.valueOf(String.valueOf(str)).concat("(choose Open).\n\n");
/* 135 */     str = String.valueOf(String.valueOf(str)).concat("At any step, detailed computer instructions are available (Help menu). To ");
/* 136 */     str = String.valueOf(String.valueOf(str)).concat("undo a mistake, backtrack by pressing the BACK button.");
/* 137 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 139 */     this.help_content_procedure.setText(str);
/* 140 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 147 */     String str = "Interactive Network Simplex Method\n\n";
/* 148 */     str = String.valueOf(String.valueOf(str)).concat("This procedure allows you apply the Network Simplex Method to interactively ");
/* 149 */     str = String.valueOf(String.valueOf(str)).concat("solve problems 9.7-2-8. First you will need to enter an initial ");
/* 150 */     str = String.valueOf(String.valueOf(str)).concat("basic feasible solution. Then, at each iteration you will make the following ");
/* 151 */     str = String.valueOf(String.valueOf(str)).concat("sequence of decisions:\n");
/* 152 */     str = String.valueOf(String.valueOf(str)).concat("    1.  Is the current basic feasible solution optimal? If not,\n");
/* 153 */     str = String.valueOf(String.valueOf(str)).concat("    2.  Choose the entering basic arc (from all potential nonbasic arcs)\n");
/* 154 */     str = String.valueOf(String.valueOf(str)).concat("    3.  Choose the leaving basic arc\n");
/* 155 */     str = String.valueOf(String.valueOf(str)).concat("    4.  Choose the amount of flow to add through the entering basic arc.\n\n");
/* 156 */     str = String.valueOf(String.valueOf(str)).concat("Choosing which problem you would like to solve\n\n");
/* 157 */     str = String.valueOf(String.valueOf(str)).concat("This procedure can only be used to solve problems 9.7-2 through 9.7-8. To ");
/* 158 */     str = String.valueOf(String.valueOf(str)).concat("solve one of these problems, enter the last digit of its problem number (an integer ");
/* 159 */     str = String.valueOf(String.valueOf(str)).concat("between 2 and 8) and then press the OK button.");
/* 160 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 162 */     this.help_content_step.setText(str);
/* 163 */     this.help_content_step.revalidate();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNAProblemPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */