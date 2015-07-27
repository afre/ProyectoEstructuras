/*     */ import java.awt.Font;
/*     */ import java.awt.GridLayout;
/*     */ import java.text.DecimalFormat;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JLabel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORMCSSProbaPanel
/*     */   extends IORMCActionPanel
/*     */ {
/*     */   private int statenum;
/*  33 */   private JPanel mainPanel = new JPanel();
/*  34 */   private JPanel firstPanel = new JPanel();
/*  35 */   private JPanel secondPanel = new JPanel();
/*  36 */   private JPanel thirdPanel = new JPanel();
/*     */   
/*     */ 
/*  39 */   private IORMCActionPanel.MaxtrixPanel matrixPanel = new IORMCActionPanel.MaxtrixPanel(this);
/*  40 */   private IORMCActionPanel.MaxtrixPanel coefficientPanel = new IORMCActionPanel.MaxtrixPanel(this);
/*  41 */   private IORMCActionPanel.MaxtrixPanel unknowPanel = new IORMCActionPanel.MaxtrixPanel(this);
/*  42 */   private IORMCActionPanel.MaxtrixPanel unknownPanel = new IORMCActionPanel.MaxtrixPanel(this);
/*  43 */   private IORMCActionPanel.MaxtrixPanel unitPanel = new IORMCActionPanel.MaxtrixPanel(this);
/*  44 */   private IORMCActionPanel.MaxtrixPanel resultPanel = new IORMCActionPanel.MaxtrixPanel(this);
/*     */   
/*     */   private JTextField[][] matrixLabel;
/*     */   
/*     */   private JTextField[][] coefficientLabel;
/*     */   
/*     */   private JPanel[] unknowsinglePanel;
/*     */   
/*     */   private JLabel[] unknowLabel;
/*     */   private JPanel[] unknownsinglePanel;
/*     */   private JLabel[] unknownLabel;
/*     */   private JTextField[] unitLabel;
/*     */   private JTextField[] resultLabel;
/*  57 */   private char PI = 'Π';
/*  58 */   private DecimalFormat keepthree = new DecimalFormat("#.###");
/*     */   
/*     */ 
/*  61 */   private DecimalFormat matreixformat = new DecimalFormat();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORMCSSProbaPanel(IORMCProcessController c)
/*     */   {
/*  71 */     super(c);
/*  72 */     add(this.mainPanel, "Center");
/*     */     
/*     */ 
/*  75 */     this.statenum = c.data.matrix_dimension;
/*     */     
/*     */ 
/*  78 */     JLabel thematrix = new JLabel("Transition Matrix");
/*  79 */     JLabel equation = new JLabel("Steady-state equations");
/*  80 */     JLabel solution = new JLabel("Solution");
/*  81 */     JLabel equat = new JLabel("=");
/*  82 */     JLabel equate = new JLabel("=");
/*  83 */     JLabel multiple = new JLabel("X");
/*     */     
/*     */ 
/*  86 */     this.firstPanel.add(thematrix);
/*  87 */     this.firstPanel.add(Box.createHorizontalStrut(30));
/*  88 */     this.matrixPanel.setLayout(new GridLayout(this.statenum, this.statenum));
/*  89 */     this.matrixLabel = new JTextField[this.statenum][this.statenum];
/*  90 */     for (int i = 0; i < this.statenum; i++)
/*     */     {
/*  92 */       for (int j = 0; j < this.statenum; j++) {
/*  93 */         this.matrixLabel[i][j] = new JTextField(4);
/*  94 */         this.matrixLabel[i][j].setBackground(getBackground());
/*  95 */         this.matrixLabel[i][j].setEditable(false);
/*  96 */         this.matrixLabel[i][j].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*  97 */         this.matrixLabel[i][j].setText(this.matreixformat.format(c.data.matrix[i][j]));
/*  98 */         this.matrixLabel[i][j].setHorizontalAlignment(0);
/*  99 */         this.matrixPanel.add(this.matrixLabel[i][j]);
/*     */       }
/*     */     }
/* 102 */     this.firstPanel.add(this.matrixPanel);
/*     */     
/*     */ 
/*     */ 
/* 106 */     this.secondPanel.add(equation);
/* 107 */     this.secondPanel.add(Box.createHorizontalStrut(30));
/*     */     
/* 109 */     this.coefficientPanel.setLayout(new GridLayout(this.statenum, this.statenum));
/* 110 */     this.coefficientLabel = new JTextField[this.statenum][this.statenum];
/* 111 */     for (int i = 0; i < this.statenum; i++)
/*     */     {
/* 113 */       for (int j = 0; j < this.statenum; j++) {
/* 114 */         this.coefficientLabel[i][j] = new JTextField(4);
/* 115 */         this.coefficientLabel[i][j].setBackground(getBackground());
/* 116 */         this.coefficientLabel[i][j].setEditable(false);
/* 117 */         this.coefficientLabel[i][j].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/* 118 */         this.coefficientLabel[i][j].setText(this.matreixformat.format(c.data.coefficient_matrix[i][j]));
/* 119 */         this.coefficientLabel[i][j].setHorizontalAlignment(0);
/* 120 */         this.coefficientPanel.add(this.coefficientLabel[i][j]);
/*     */       }
/*     */     }
/* 123 */     this.secondPanel.add(this.coefficientPanel);
/* 124 */     this.secondPanel.add(Box.createHorizontalStrut(10));
/*     */     
/* 126 */     this.secondPanel.add(multiple);
/* 127 */     this.secondPanel.add(Box.createHorizontalStrut(10));
/*     */     
/* 129 */     this.unknowPanel.setLayout(new BoxLayout(this.unknowPanel, 1));
/* 130 */     this.unknowLabel = new JLabel[this.statenum];
/* 131 */     this.unknowsinglePanel = new JPanel[this.statenum];
/* 132 */     for (int i = 0; i < this.statenum; i++) {
/* 133 */       this.unknowLabel[i] = new JLabel("".concat(String.valueOf(String.valueOf(this.PI))));
/* 134 */       this.unknowLabel[i].setFont(new Font("Default", 0, 12));
/* 135 */       this.unknowsinglePanel[i] = new JPanel();
/* 136 */       this.unknowsinglePanel[i].add(new ScriptWriter(this.unknowLabel[i], "", String.valueOf(i), 2, 3));
/* 137 */       this.unknowsinglePanel[i].setAlignmentX(0.5F);
/* 138 */       this.unknowPanel.add(this.unknowsinglePanel[i]);
/*     */     }
/*     */     
/* 141 */     this.secondPanel.add(this.unknowPanel);
/* 142 */     this.secondPanel.add(Box.createHorizontalStrut(10));
/*     */     
/*     */ 
/* 145 */     this.secondPanel.add(equate);
/* 146 */     this.secondPanel.add(Box.createHorizontalStrut(10));
/*     */     
/* 148 */     this.unitPanel.setLayout(new BoxLayout(this.unitPanel, 1));
/* 149 */     this.unitLabel = new JTextField[this.statenum];
/* 150 */     for (int i = 0; i < this.statenum; i++) {
/* 151 */       if (i != 0) this.unitPanel.add(Box.createVerticalStrut(14));
/* 152 */       this.unitLabel[i] = new JTextField(4);
/* 153 */       this.unitLabel[i].setBackground(getBackground());
/* 154 */       this.unitLabel[i].setEditable(false);
/* 155 */       this.unitLabel[i].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/* 156 */       this.unitLabel[i].setText(String.valueOf(c.data.unit_matrix[i]));
/* 157 */       this.unitLabel[i].setHorizontalAlignment(0);
/* 158 */       this.unitPanel.add(this.unitLabel[i]);
/* 159 */       this.unitPanel.add(Box.createVerticalStrut(3));
/*     */     }
/* 161 */     this.secondPanel.add(this.unitPanel);
/*     */     
/*     */ 
/* 164 */     this.thirdPanel.add(solution);
/* 165 */     this.thirdPanel.add(Box.createHorizontalStrut(30));
/* 166 */     this.unknownPanel.setLayout(new BoxLayout(this.unknownPanel, 1));
/* 167 */     this.unknownsinglePanel = new JPanel[this.statenum];
/* 168 */     this.unknownLabel = new JLabel[this.statenum];
/* 169 */     for (int i = 0; i < this.statenum; i++) {
/* 170 */       this.unknownLabel[i] = new JLabel("".concat(String.valueOf(String.valueOf(this.PI))));
/* 171 */       this.unknownLabel[i].setFont(new Font("Default", 0, 12));
/* 172 */       this.unknownsinglePanel[i] = new JPanel();
/* 173 */       this.unknownsinglePanel[i].add(new ScriptWriter(this.unknownLabel[i], "", String.valueOf(i), 2, 3));
/* 174 */       this.unknownsinglePanel[i].setAlignmentX(0.5F);
/* 175 */       this.unknownPanel.add(this.unknownsinglePanel[i]);
/*     */     }
/* 177 */     this.thirdPanel.add(this.unknownPanel);
/* 178 */     this.thirdPanel.add(Box.createHorizontalStrut(10));
/* 179 */     this.thirdPanel.add(equat);
/* 180 */     this.thirdPanel.add(Box.createHorizontalStrut(10));
/*     */     
/* 182 */     this.resultPanel.setLayout(new BoxLayout(this.resultPanel, 1));
/* 183 */     this.resultLabel = new JTextField[this.statenum];
/* 184 */     for (int i = 0; i < this.statenum; i++) {
/* 185 */       if (i != 0) this.resultPanel.add(Box.createVerticalStrut(14));
/* 186 */       this.resultLabel[i] = new JTextField(4);
/* 187 */       this.resultLabel[i].setBackground(getBackground());
/* 188 */       this.resultLabel[i].setEditable(false);
/* 189 */       this.resultLabel[i].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/*     */       
/* 191 */       if (c.data.result_matrix[i] == 0) c.data.result_matrix[i] = 0.0D;
/* 192 */       this.resultLabel[i].setText(this.keepthree.format(c.data.result_matrix[i]));
/* 193 */       this.resultLabel[i].setHorizontalAlignment(0);
/* 194 */       this.resultPanel.add(this.resultLabel[i]);
/* 195 */       this.resultPanel.add(Box.createVerticalStrut(3));
/*     */     }
/* 197 */     this.thirdPanel.add(this.resultPanel);
/*     */     
/* 199 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/* 200 */     this.mainPanel.add(this.firstPanel);
/* 201 */     this.mainPanel.add(this.secondPanel);
/* 202 */     this.mainPanel.add(this.thirdPanel);
/*     */     
/* 204 */     setDoubleBuffered(false);
/* 205 */     this.mainPanel.setDoubleBuffered(false);
/* 206 */     this.firstPanel.setDoubleBuffered(false);
/* 207 */     this.secondPanel.setDoubleBuffered(false);
/* 208 */     this.thirdPanel.setDoubleBuffered(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 216 */     String str = "Steady State Probabilities\n\n";
/* 217 */     str = String.valueOf(String.valueOf(str)).concat("Shown are the original transition matrix, the equations used to calculate the ");
/* 218 */     str = String.valueOf(String.valueOf(str)).concat("steady state probabilities, and the steady state probabilities themselves. ");
/* 219 */     str = String.valueOf(String.valueOf(str)).concat("To print the results, choose Print to File from the File menu.");
/* 220 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/*     */ 
/* 223 */     this.help_content_step.setText(str);
/* 224 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 231 */     String str = "Steady State Probabilities \n\n";
/* 232 */     str = String.valueOf(String.valueOf(str)).concat("Given that you have entered the one-step transition matrix (Procedure 1), ");
/* 233 */     str = String.valueOf(String.valueOf(str)).concat("this procedure calculates the steady state probabilities for the state of a ");
/* 234 */     str = String.valueOf(String.valueOf(str)).concat("discrete time Markov chain, as described at the beginning of Section 5 of the ");
/* 235 */     str = String.valueOf(String.valueOf(str)).concat("\"Markov Chains\" chapter. You can consider a maximum of 10 states. The results ");
/* 236 */     str = String.valueOf(String.valueOf(str)).concat("can be printed.");
/* 237 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/*     */ 
/* 240 */     this.help_content_procedure.setText(str);
/* 241 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORMCSSProbaPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */