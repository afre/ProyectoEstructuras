/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JButton;
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
/*     */ public class IORTPSolvePanel
/*     */   extends IORTPActionPanel
/*     */ {
/*  33 */   private JPanel mainPanel = null;
/*  34 */   private IORTPCanvasPanel canvasPanel = null;
/*  35 */   private IORTPSolvePanel.ControlPanel controlPanel = null;
/*  36 */   private boolean enter_uv = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORTPSolvePanel(IORTPProcessController c)
/*     */   {
/*  46 */     super(c);
/*     */     
/*  48 */     this.state = new IORState(c.solver);
/*     */     
/*  50 */     setStep(0);
/*     */     
/*  52 */     createCanvasPanel();
/*  53 */     this.controlPanel = new IORTPSolvePanel.ControlPanel(null);
/*     */     
/*  55 */     this.mainPanel = new JPanel();
/*  56 */     add(this.mainPanel, "Center");
/*     */     
/*  58 */     this.mainPanel.setLayout(new BorderLayout());
/*  59 */     this.mainPanel.add(this.canvasPanel, "Center");
/*  60 */     this.mainPanel.add(this.controlPanel, "South");
/*     */     
/*  62 */     setDoubleBuffered(false);
/*  63 */     this.mainPanel.setDoubleBuffered(false);
/*     */     
/*  65 */     this.controlPanel.setDoubleBuffered(false);
/*     */   }
/*     */   
/*     */   private void createCanvasPanel() {
/*  69 */     this.canvasPanel = new IORTPCanvasPanel(this, 1);
/*  70 */     this.canvasPanel.setUVEditable(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEnterUV(boolean b)
/*     */   {
/*  79 */     this.enter_uv = b;
/*     */     
/*  81 */     if (this.enter_uv) {
/*  82 */       if (this.controlPanel != null) {
/*  83 */         this.controlPanel.showUV();
/*     */       }
/*  85 */       if (this.canvasPanel != null) {
/*  86 */         this.canvasPanel.setResultVisible(false);
/*  87 */         this.canvasPanel.setUVEditable(true);
/*  88 */         this.canvasPanel.setUVSelectColor();
/*  89 */         this.canvasPanel.setCellEnabled(false);
/*     */       }
/*     */     }
/*     */     else {
/*  93 */       if (this.controlPanel != null) {
/*  94 */         this.controlPanel.hideUV();
/*     */       }
/*     */       
/*  97 */       if (this.canvasPanel != null) {
/*  98 */         this.canvasPanel.setResultVisible(true);
/*  99 */         this.canvasPanel.setUVEditable(false);
/* 100 */         this.canvasPanel.setUVDefaultColor();
/* 101 */         this.canvasPanel.setCellEnabled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void backStep()
/*     */   {
/* 110 */     if (this.state != null) {
/* 111 */       this.controller.solver.reset();
/* 112 */       this.state.back();
/*     */       
/* 114 */       int len = this.operation_sequence.size();
/* 115 */       this.operation_sequence.removeElementAt(len - 1);
/* 116 */       this.step = ((Integer)this.operation_sequence.lastElement()).intValue();
/*     */       
/* 118 */       this.controller.solver.getData(this.controller.data);
/* 119 */       this.canvasPanel.updateLoop();
/* 120 */       updatePanel();
/*     */     }
/* 122 */     setFinishEnabled(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finishProcedure()
/*     */   {
/* 130 */     updatePanel();
/* 131 */     this.controller.setMenuState(11);
/*     */     
/* 133 */     setFinishEnabled(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInitFocus()
/*     */   {
/* 142 */     this.canvasPanel.setInitFocus();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updatePanel()
/*     */   {
/* 153 */     this.controller.solver.getData(this.controller.data);
/*     */     
/* 155 */     if (this.state.isEmpty()) {
/* 156 */       this.bn_back.setEnabled(false);
/*     */     } else {
/* 158 */       this.bn_back.setEnabled(true);
/*     */     }
/* 160 */     this.canvasPanel.updatePanel();
/*     */     
/* 162 */     this.controlPanel.updatePanel();
/*     */     
/* 164 */     repaint();
/*     */   }
/*     */   
/* 167 */   private class ControlPanel extends JPanel { ControlPanel(IORTPSolvePanel..2 x$1) { this(); }
/*     */     
/* 169 */     private IORTPSolvePanel.ControlPanel.UVInputPanel uvInputPanel = null;
/* 170 */     private JLabel prompt1 = null;
/* 171 */     private JLabel prompt2 = null;
/* 172 */     private JLabel prompt3 = null;
/*     */     
/* 174 */     private JPanel p = null;
/*     */     
/*     */     private ControlPanel() {
/* 177 */       this.uvInputPanel = new IORTPSolvePanel.ControlPanel.UVInputPanel(null);
/*     */       
/* 179 */       this.p = new JPanel();
/* 180 */       this.prompt1 = new JLabel("Indicate your choice for the entering basic variable. Select a cell and then double click it.");
/* 181 */       this.prompt1.setForeground(Color.red);
/* 182 */       this.prompt2 = new JLabel("Show the chain reaction caused by increasing the entering basic variable by double clicking along the path.");
/* 183 */       this.prompt2.setForeground(Color.black);
/* 184 */       this.prompt3 = new JLabel("Indicate your choice for the leaving basic variable. Select a cell and then double click it.");
/* 185 */       this.prompt3.setForeground(Color.blue);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 191 */       add(this.uvInputPanel);
/* 192 */       add(this.p);
/*     */       
/* 194 */       this.uvInputPanel.setDoubleBuffered(false);
/* 195 */       this.p.setDoubleBuffered(false);
/*     */     }
/*     */     
/*     */     public void showUV() {
/* 199 */       this.uvInputPanel.showUV();
/* 200 */       revalidate();
/* 201 */       repaint();
/*     */     }
/*     */     
/*     */     public void hideUV() {
/* 205 */       this.uvInputPanel.hideUV();
/* 206 */       revalidate();
/* 207 */       repaint();
/*     */     }
/*     */     
/*     */     public void updatePanel() {
/* 211 */       switch (IORTPSolvePanel.this.step) {
/*     */       case 0: 
/* 213 */         this.p.removeAll();
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
/* 231 */         break;
/*     */       case 1: 
/* 217 */         this.p.removeAll();
/* 218 */         this.p.add(this.prompt1);
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
/* 231 */         break;
/*     */       case 2: 
/* 222 */         this.p.removeAll();
/* 223 */         this.p.add(this.prompt2);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 231 */         break;
/*     */       case 3: 
/* 227 */         this.p.removeAll();
/* 228 */         this.p.add(this.prompt3); }
/*     */     }
/*     */     
/*     */     private class UVInputPanel
/*     */       extends JPanel {
/* 233 */       UVInputPanel(IORTPSolvePanel..2 x$1) { this(); }
/* 234 */       private JButton bn_OK = null;
/* 235 */       private JLabel prompt = null;
/*     */       
/*     */       private UVInputPanel() {
/* 238 */         this.prompt = new JLabel("Press OK when you finish entering the values for u(i) and v(j) ");
/* 239 */         this.bn_OK = new JButton("OK");
/* 240 */         this.bn_OK.addActionListener(new IORTPSolvePanel.1((UVInputPanel)this));
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 250 */         add(this.prompt);
/* 251 */         add(this.bn_OK);
/*     */       }
/*     */       
/*     */       private void hideUV() {
/* 255 */         this.prompt.setVisible(false);
/* 256 */         this.bn_OK.setVisible(false);
/*     */       }
/*     */       
/*     */       private void showUV() {
/* 260 */         this.bn_OK.setVisible(true);
/* 261 */         this.prompt.setVisible(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 271 */     String str = "Solve Interactively by the Transportation Simplex Method\n\n";
/* 272 */     str = String.valueOf(String.valueOf(str)).concat("This procedure allows you to solve a transportation problem model interactively ");
/* 273 */     str = String.valueOf(String.valueOf(str)).concat("by using the transportation simplex method presented in Sec. 8.2. At each ");
/* 274 */     str = String.valueOf(String.valueOf(str)).concat("iteration, the computer does the calculations while you make the following ");
/* 275 */     str = String.valueOf(String.valueOf(str)).concat("sequence of decisions:\n");
/* 276 */     str = String.valueOf(String.valueOf(str)).concat("     1.  Select the entering basic variable.\n");
/* 277 */     str = String.valueOf(String.valueOf(str)).concat("     2.  Determine the chain reaction caused by increasing the entering basic variable.\n");
/* 278 */     str = String.valueOf(String.valueOf(str)).concat("     3.  Determine the leaving basic variable.\n");
/* 279 */     str = String.valueOf(String.valueOf(str)).concat("     4.  Identify the new value of the entering basic variable.\n\n");
/* 280 */     str = String.valueOf(String.valueOf(str)).concat("Deriving and entering u(i) and v(j)\n\n");
/* 281 */     str = String.valueOf(String.valueOf(str)).concat("Move the mouse until the pointer lies on a u(i) or v(j) cell that you have just derived, ");
/* 282 */     str = String.valueOf(String.valueOf(str)).concat("and then enter the value that you have derived. Keep doing this until all the u(i)'s ");
/* 283 */     str = String.valueOf(String.valueOf(str)).concat("and v(j)'s are entered. As soon as they are all entered AND correct, the computer will ");
/* 284 */     str = String.valueOf(String.valueOf(str)).concat("move on to the next step (determining the entering basic variable for the first iteration). ");
/* 285 */     str = String.valueOf(String.valueOf(str)).concat("If you enter a complete set of values and the computer does NOT move on to the next step, ");
/* 286 */     str = String.valueOf(String.valueOf(str)).concat("you have made a mistake and should recheck the values. For subsequent iterations, the ");
/* 287 */     str = String.valueOf(String.valueOf(str)).concat("computer will derive the u(i) and v(j) for you.");
/* 288 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 289 */     this.help_content_step.setText(str);
/* 290 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 297 */     String str = "Solve Interactively by the Transportation Simplex Method\n\n";
/* 298 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to execute the Transportation Simplex Method as ");
/* 299 */     str = String.valueOf(String.valueOf(str)).concat("presented in Sec. 8.2. Your role is to apply the logic of the algorithm, ");
/* 300 */     str = String.valueOf(String.valueOf(str)).concat("and the computer will do the number crunching. The computer also will inform ");
/* 301 */     str = String.valueOf(String.valueOf(str)).concat("you if you make a mistake on the first iteration.\n\n");
/* 302 */     str = String.valueOf(String.valueOf(str)).concat("This procedure can handle up to 7 sources and 7 destinations, which encompasses ");
/* 303 */     str = String.valueOf(String.valueOf(str)).concat("all relevant problems at the end of Chap. 8.\n\n");
/* 304 */     str = String.valueOf(String.valueOf(str)).concat("When you finish a problem, you can print out all your work by choosing ");
/* 305 */     str = String.valueOf(String.valueOf(str)).concat("Print to file under the File menu. If you are interrupted before you finish, you ");
/* 306 */     str = String.valueOf(String.valueOf(str)).concat("can save your work (choose Save under the File menu) and return later (choose ");
/* 307 */     str = String.valueOf(String.valueOf(str)).concat("Open).\n\n At any step, detailed computer instructions are available (Help menu). ");
/* 308 */     str = String.valueOf(String.valueOf(str)).concat("To undo a mistake, backtrack by clicking the BACK button.");
/* 309 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 310 */     this.help_content_procedure.setText(str);
/* 311 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/* 314 */   private String str1 = "Solve Interactively by the Transportation Simplex Method\n\n";
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
/* 332 */   private String str2 = "Selecting the entering basic variable\n\n";
/*     */   
/*     */ 
/*     */ 
/* 336 */   private String str3 = "Determining the chain reaction caused by increasing the entering basic variable\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 343 */   private String str4 = "Determining the leaving basic variable\n\n";
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
/* 357 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/* 358 */     switch (this.step) {
/*     */     case 1: 
/* 360 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str1))).append(this.str2).append(str))));
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
/* 372 */       break;
/*     */     case 2: 
/* 363 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str1))).append(this.str3).append(str))));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 372 */       break;
/*     */     case 3: 
/* 366 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str1))).append(this.str4).append(str))));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 372 */       break;
/*     */     default: 
/* 369 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str1)).concat(String.valueOf(String.valueOf(str))));
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/*     */     try
/*     */     {
/* 385 */       out.writeObject(this.state);
/* 386 */       out.writeObject(this.operation_sequence);
/* 387 */       out.writeObject(new Integer(this.step));
/* 388 */       out.writeObject(new Boolean(this.enter_uv));
/* 389 */       int[] sel = new int[2];
/* 390 */       this.canvasPanel.canvas.getSelCell(sel);
/* 391 */       out.writeObject(sel);
/*     */       
/*     */ 
/* 394 */       for (int i = 0; i < this.controller.data.num_supply; i++) {
/* 395 */         for (int j = 0; j < this.controller.data.num_demand; j++) {
/* 396 */           out.writeObject(this.canvasPanel.canvas.getCellInnerVariables(i, j));
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e1) {
/*     */       int j;
/* 402 */       System.out.println("Save fails");
/*     */     }
/*     */   }
/*     */   
/*     */ 
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
/* 417 */       this.state = ((IORState)in.readObject());
/* 418 */       this.operation_sequence = ((Vector)in.readObject());
/* 419 */       this.step = ((Integer)in.readObject()).intValue();
/* 420 */       this.enter_uv = ((Boolean)in.readObject()).booleanValue();
/* 421 */       this.state.setSolverStepVector();
/* 422 */       int[] sel = (int[])in.readObject();
/* 423 */       if ((sel[0] >= 0) && (sel[1] >= 0))
/* 424 */         this.canvasPanel.canvas.setSelCell(sel[0], sel[1]);
/* 425 */       this.canvasPanel.canvas.setState();
/* 426 */       setEnterUV(this.enter_uv);
/*     */       
/*     */ 
/* 429 */       for (int i = 0; i < this.controller.data.num_supply; i++) {
/* 430 */         for (int j = 0; j < this.controller.data.num_demand; j++) {
/* 431 */           this.canvasPanel.canvas.setCellInnerVariables(i, j, (Vector)in.readObject());
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e1) {
/*     */       int j;
/* 437 */       System.out.println("Load fails");
/* 438 */       return;
/*     */     }
/*     */     
/* 441 */     updatePanel();
/*     */     
/* 443 */     if (this.bn_back.isVisible()) {
/* 444 */       if ((this.state == null) || (this.state.isEmpty())) {
/* 445 */         this.bn_back.setEnabled(false);
/*     */       } else {
/* 447 */         this.bn_back.setEnabled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTPSolvePanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */