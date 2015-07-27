/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
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
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextArea;
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
/*     */ public class IORIPActionPanel
/*     */   extends IORActionPanel
/*     */ {
/*  32 */   public IORIPProcessController controller = null;
/*     */   
/*  34 */   public IORIPCanvas canvas = null;
/*     */   
/*  36 */   private JPanel mainPanel = new JPanel();
/*  37 */   private JPanel downPanel = new JPanel();
/*  38 */   private JPanel upPanel = new JPanel();
/*  39 */   private JPanel comPanel = new JPanel();
/*  40 */   private JPanel operPanel = new JPanel();
/*  41 */   private JPanel incumberPanel = new JPanel();
/*  42 */   private JPanel tipPanel = new JPanel();
/*  43 */   private JPanel solutionPanel = new JPanel();
/*  44 */   private JPanel comentPanel = new JPanel();
/*     */   
/*  46 */   private JTextArea statetext = new JTextArea("");
/*     */   
/*  48 */   private JLabel solutionLabel = new JLabel();
/*  49 */   private JLabel[] incumbentLabels = { new JLabel("Incumbent:"), new JLabel("  X  =  (0,  0,  2,  0.5)"), new JLabel("  Z* =  13.5") };
/*  50 */   private JCheckBox[] operCheckBox = { new JCheckBox("Incumbent"), new JCheckBox("Fathomed") };
/*  51 */   private DecimalFormat df = new DecimalFormat("#.##");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORIPActionPanel(IORIPProcessController c)
/*     */   {
/*  59 */     super(c);
/*  60 */     this.controller = c;
/*  61 */     this.state = new IORState(this.controller.solver);
/*  62 */     add(this.mainPanel);
/*     */     
/*  64 */     this.actionStatus.setVisible(false);
/*     */     
/*     */ 
/*  67 */     this.canvas = new IORIPCanvas(50, 30, this.controller.data, this);
/*  68 */     this.canvas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
/*     */     
/*  70 */     this.upPanel.setLayout(new BorderLayout());
/*  71 */     this.upPanel.add(this.canvas, "Center");
/*     */     
/*  73 */     this.statetext.setBackground(getBackground());
/*  74 */     this.statetext.setEditable(false);
/*  75 */     this.statetext.setText("Use the mouse or the arrow keys to select the node you would like to investigate. \nTo branch on the current node, double click it or press the ENTER key.\nCheck the corresponding checkbox to fathom the current node or to declare its solution as the new incumbent.");
/*     */     
/*     */ 
/*  78 */     this.comentPanel.add(this.statetext);
/*  79 */     this.comentPanel.setAlignmentX(0.5F);
/*  80 */     this.comentPanel.setPreferredSize(new Dimension(600, 60));
/*  81 */     this.comentPanel.setMaximumSize(new Dimension(600, 60));
/*  82 */     this.downPanel.setLayout(new BoxLayout(this.downPanel, 1));
/*     */     
/*  84 */     this.downPanel.add(this.tipPanel);
/*  85 */     this.downPanel.add(Box.createVerticalStrut(10));
/*  86 */     this.downPanel.add(this.comentPanel);
/*  87 */     this.downPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
/*     */     
/*     */ 
/*  90 */     this.tipPanel.setLayout(new BoxLayout(this.tipPanel, 1));
/*     */     
/*  92 */     this.tipPanel.add(this.solutionLabel);
/*  93 */     this.tipPanel.add(this.comPanel);
/*     */     
/*  95 */     this.tipPanel.setPreferredSize(new Dimension(400, 90));
/*  96 */     this.tipPanel.setMaximumSize(new Dimension(400, 90));
/*  97 */     this.tipPanel.setAlignmentX(0.5F);
/*  98 */     this.solutionLabel.setAlignmentX(0.0F);
/*  99 */     this.comPanel.setAlignmentX(0.0F);
/*     */     
/* 101 */     this.comPanel.setLayout(new BoxLayout(this.comPanel, 0));
/* 102 */     this.comPanel.add(this.operPanel);
/* 103 */     this.comPanel.add(Box.createHorizontalStrut(100));
/* 104 */     this.comPanel.add(this.incumberPanel);
/*     */     
/* 106 */     this.operPanel.setLayout(new BoxLayout(this.operPanel, 1));
/* 107 */     this.operPanel.add(this.operCheckBox[0]);
/* 108 */     this.operPanel.add(this.operCheckBox[1]);
/*     */     
/* 110 */     this.incumberPanel.setLayout(new BoxLayout(this.incumberPanel, 1));
/* 111 */     this.incumberPanel.add(this.incumbentLabels[0]);
/* 112 */     this.incumberPanel.add(this.incumbentLabels[1]);
/* 113 */     this.incumberPanel.add(this.incumbentLabels[2]);
/*     */     
/* 115 */     this.mainPanel.setLayout(new BorderLayout());
/* 116 */     this.mainPanel.add(this.upPanel, "Center");
/* 117 */     this.mainPanel.add(this.downPanel, "South");
/*     */     
/*     */ 
/* 120 */     this.operCheckBox[0].addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 122 */         Vector par = new Vector();
/* 123 */         IOROperation opr = new IOROperation(40204, par);
/* 124 */         if (IORIPActionPanel.this.controller.data.isBIP)
/* 125 */           opr.operation_code = 40104;
/* 126 */         par.addElement(new Integer(IORIPActionPanel.this.canvas.getFocusNodeLevel()));
/* 127 */         par.addElement(new Integer(IORIPActionPanel.this.canvas.getFocusNodeOffset()));
/*     */         
/* 129 */         IORIPActionPanel.this.doStep(opr);
/*     */       }
/* 131 */     });
/* 132 */     this.operCheckBox[1].addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 134 */         Vector par = new Vector();
/* 135 */         IOROperation opr = new IOROperation(40205, par);
/* 136 */         if (IORIPActionPanel.this.controller.data.isBIP)
/* 137 */           opr.operation_code = 40105;
/* 138 */         par.addElement(new Integer(IORIPActionPanel.this.canvas.getFocusNodeLevel()));
/* 139 */         par.addElement(new Integer(IORIPActionPanel.this.canvas.getFocusNodeOffset()));
/*     */         
/* 141 */         IORIPActionPanel.this.doStep(opr);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finishProcedure() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void backStep()
/*     */   {
/* 156 */     this.controller.solver.reset();
/* 157 */     this.state.back();
/* 158 */     this.controller.solver.getData(this.controller.data);
/*     */     
/* 160 */     this.canvas.backNodeFocusStep();
/* 161 */     updatePanel();
/*     */     
/* 163 */     if (this.state.isEmpty()) {
/* 164 */       this.bn_back.setEnabled(false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void updatePanel()
/*     */   {
/* 171 */     updateDownPanel();
/* 172 */     revalidate();
/* 173 */     repaint();
/*     */   }
/*     */   
/*     */   private void updateDownPanel()
/*     */   {
/*     */     String text;
/* 179 */     if (this.controller.data.nodes[this.canvas.getFocusNodeIndex()].feasible) {
/* 180 */       String text = "LP - Relaxation Optimal Solution: ( ";
/* 181 */       for (int i = 1; i <= this.controller.data.NumVariables; i++) {
/* 182 */         text = String.valueOf(String.valueOf(text)).concat(String.valueOf(String.valueOf(fromDoubleorInttoString(this.controller.data.nodes[this.canvas.getFocusNodeIndex()].solution[i]))));
/* 183 */         if (i < this.controller.data.NumVariables) text = String.valueOf(String.valueOf(text)).concat(",  ");
/*     */       }
/* 185 */       text = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(text))).append(" ),   Z = ").append(this.df.format(this.controller.data.nodes[this.canvas.getFocusNodeIndex()].Z)).append(".")));
/*     */     }
/*     */     else {
/* 188 */       text = "LP - Relaxation is infeasible.";
/*     */     }
/* 190 */     this.solutionLabel.setText(text);
/*     */     
/* 192 */     if (this.controller.data.IncumbentExists) {
/* 193 */       this.incumbentLabels[0].setText("Incumbent:");
/*     */       
/* 195 */       text = "  X  =  (";
/* 196 */       for (int i = 1; i <= this.controller.data.NumVariables; i++) {
/* 197 */         text = String.valueOf(String.valueOf(text)).concat(String.valueOf(String.valueOf(this.df.format(this.controller.data.Incumbent[i]))));
/* 198 */         if (i < this.controller.data.NumVariables) text = String.valueOf(String.valueOf(text)).concat(",  ");
/*     */       }
/* 200 */       text = String.valueOf(String.valueOf(text)).concat(")");
/* 201 */       this.incumbentLabels[1].setText(text);
/* 202 */       this.incumbentLabels[2].setText("  Z* =  ".concat(String.valueOf(String.valueOf(this.df.format(this.controller.data.IncumbentZ)))));
/*     */     }
/*     */     else {
/* 205 */       this.incumbentLabels[0].setText("");
/* 206 */       this.incumbentLabels[1].setText("");
/* 207 */       this.incumbentLabels[2].setText("");
/*     */     }
/*     */     
/* 210 */     if ((this.controller.data.IncumbentExists) && (this.canvas.getFocusNodeIndex() == this.controller.data.IncumbentNodeIndex)) {
/* 211 */       this.operCheckBox[0].setSelected(true);
/*     */     } else
/* 213 */       this.operCheckBox[0].setSelected(false);
/* 214 */     this.operCheckBox[1].setSelected(this.controller.data.nodes[this.canvas.getFocusNodeIndex()].fathomed);
/* 215 */     if ((this.controller.data.nodes[this.canvas.getFocusNodeIndex()].leafNode) && (this.controller.data.nodes[this.canvas.getFocusNodeIndex()].feasible)) {
/* 216 */       this.operCheckBox[0].setEnabled(true);
/* 217 */       this.operCheckBox[1].setEnabled(true);
/*     */     }
/*     */     else {
/* 220 */       this.operCheckBox[0].setEnabled(false);
/* 221 */       this.operCheckBox[1].setEnabled(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private String fromDoubleorInttoString(double d) {
/* 226 */     if (Math.abs(d - Math.round(d)) < 0.001D) {
/* 227 */       Integer i = new Integer((int)Math.round(d));
/* 228 */       return i.toString();
/*     */     }
/*     */     
/* 231 */     return this.df.format(d);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void doStep(IOROperation opr)
/*     */   {
/* 241 */     if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 242 */       String err = this.controller.solver.getErrInfo();
/* 243 */       JOptionPane.showMessageDialog(this, err);
/* 244 */       if ((opr.operation_code == 40104) || (opr.operation_code == 40204))
/* 245 */         this.operCheckBox[0].setSelected(false);
/* 246 */       if ((opr.operation_code == 40105) || (opr.operation_code == 40205))
/* 247 */         this.operCheckBox[1].setSelected(false);
/* 248 */       return;
/*     */     }
/*     */     
/* 251 */     if ((opr.operation_code != 40102) && (opr.operation_code != 40202)) {
/* 252 */       this.state.addStep(opr);
/* 253 */       this.bn_back.setEnabled(true);
/*     */       
/* 255 */       this.canvas.addNodeFocusStep();
/*     */     }
/*     */     
/* 258 */     switch (opr.operation_code) {
/*     */     case 40104: 
/*     */     case 40204: 
/* 261 */       this.controller.data.IncumbentNodeIndex = this.canvas.getFocusNodeIndex();
/* 262 */       break;
/*     */     }
/* 264 */     updatePanel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 271 */     String str = "Current\nProcedure\nHelp\nFor IPActionPanel";
/*     */     
/* 273 */     this.help_content_procedure.setText(str);
/* 274 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/* 277 */   private final String str0 = "Current\nStep\nHelp\nFor IPActionPanel";
/* 278 */   private String str1 = "The Mixed Integer Programming Branch-and-Bound Algorithm\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 284 */   private String str11 = "Solve Mixed Integer Programming Interactively\n\n";
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
/* 296 */   private String str2 = "The Binary Integer Programming Branch-and-Bound Algorithm\n\n ";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 302 */   private String str21 = "Solve Binary Integer Program Interactively\n\n ";
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
/*     */   protected void updateStepHelpPanel()
/*     */   {
/* 321 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/*     */     
/* 323 */     if (this.controller.data.NumBinaryVariables == this.controller.data.NumVariables) {
/* 324 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str2)).concat(String.valueOf(String.valueOf(str))));
/* 325 */       this.help_content_procedure.setText(String.valueOf(String.valueOf(this.str21)).concat(String.valueOf(String.valueOf(str))));
/*     */     }
/*     */     else {
/* 328 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str1)).concat(String.valueOf(String.valueOf(str))));
/* 329 */       this.help_content_procedure.setText(String.valueOf(String.valueOf(this.str11)).concat(String.valueOf(String.valueOf(str))));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateHelp()
/*     */   {
/* 338 */     updateStepHelpPanel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 346 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/*     */     
/* 348 */     this.help_content_step.setText("Current\nStep\nHelp\nFor IPActionPanel".concat(String.valueOf(String.valueOf(str))));
/* 349 */     this.help_content_step.revalidate();
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
/* 360 */       out.writeObject(this.state);
/* 361 */       out.writeObject(this.canvas.focusStep);
/* 362 */       out.writeObject(new Integer(this.canvas.getFocusNodeIndex()));
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/* 366 */       System.out.println("Save fails");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/*     */     try
/*     */     {
/* 379 */       this.state = ((IORState)in.readObject());
/* 380 */       this.canvas.focusStep = ((Vector)in.readObject());
/* 381 */       this.canvas.setFocusNode(((Integer)in.readObject()).intValue());
/* 382 */       this.state.setSolverStepVector();
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/* 386 */       System.out.println("Load fails");
/*     */     }
/*     */     
/* 389 */     updatePanel();
/* 390 */     updateHelp();
/*     */     
/* 392 */     if (this.bn_back.isVisible()) {
/* 393 */       if ((this.state == null) || (this.state.isEmpty())) {
/* 394 */         this.bn_back.setEnabled(false);
/*     */       } else {
/* 396 */         this.bn_back.setEnabled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORIPActionPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */