/*     */ import java.awt.BorderLayout;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
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
/*     */ public class IORTPFindFeasiblePanel
/*     */   extends IORTPActionPanel
/*     */ {
/*  34 */   private JPanel mainPanel = null;
/*  35 */   private IORTPCanvasPanel canvasPanel = null;
/*  36 */   private IORTPFindFeasiblePanel.ControlPanel controlPanel = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORTPFindFeasiblePanel(IORTPProcessController c)
/*     */   {
/*  46 */     super(c);
/*     */     
/*  48 */     setStep(1);
/*  49 */     addStep();
/*     */     
/*  51 */     this.controller.solver.getData(this.controller.data);
/*     */     
/*  53 */     this.state = new IORState(c.solver);
/*     */     
/*  55 */     createCanvasPanel();
/*  56 */     this.controlPanel = new IORTPFindFeasiblePanel.ControlPanel(null);
/*     */     
/*  58 */     this.mainPanel = new JPanel();
/*  59 */     add(this.mainPanel, "Center");
/*     */     
/*  61 */     this.mainPanel.setLayout(new BorderLayout());
/*  62 */     this.mainPanel.add(this.canvasPanel, "Center");
/*  63 */     this.mainPanel.add(this.controlPanel, "South");
/*     */     
/*  65 */     updatePanel();
/*     */     
/*  67 */     setDoubleBuffered(false);
/*  68 */     this.mainPanel.setDoubleBuffered(false);
/*  69 */     this.controlPanel.setDoubleBuffered(false);
/*     */   }
/*     */   
/*     */   private void createCanvasPanel() {
/*  73 */     this.canvasPanel = new IORTPCanvasPanel(this, 0);
/*  74 */     this.canvasPanel.setUVEditable(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void backStep()
/*     */   {
/*  81 */     if (this.state != null) {
/*  82 */       this.controller.solver.reset();
/*  83 */       this.state.back();
/*     */       
/*  85 */       int len = this.operation_sequence.size();
/*  86 */       this.operation_sequence.removeElementAt(len - 1);
/*  87 */       this.step = ((Integer)this.operation_sequence.lastElement()).intValue();
/*     */       
/*  89 */       updatePanel();
/*  90 */       revalidate();
/*  91 */       setFinishEnabled(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean checkFeasibleSolution()
/*     */   {
/*  99 */     Vector p = new Vector();
/*     */     
/* 101 */     IOROperation operation = new IOROperation(20204, p);
/*     */     
/*     */ 
/* 104 */     if (!this.controller.solver.doWork(operation, this.controller.data)) {
/* 105 */       JOptionPane.showMessageDialog(this, "Not a feasible solution. Try again!");
/* 106 */       return false;
/*     */     }
/*     */     
/* 109 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finishProcedure()
/*     */   {
/* 117 */     if (checkFeasibleSolution()) {
/* 118 */       updatePanel();
/*     */       
/* 120 */       this.actionStatus.setText("The current procedure is finished. Please go to the Procedure menu to continue!");
/* 121 */       this.controller.setMenuState(11);
/*     */       
/* 123 */       setFinishEnabled(false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInitFocus()
/*     */   {
/* 134 */     this.canvasPanel.setInitFocus();
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
/* 145 */     this.controller.solver.getData(this.controller.data);
/* 146 */     this.canvasPanel.updatePanel();
/*     */     
/* 148 */     this.controlPanel.updatePanel();
/*     */     
/* 150 */     if (this.state.isEmpty()) {
/* 151 */       this.bn_back.setEnabled(false);
/*     */     } else {
/* 153 */       this.bn_back.setEnabled(true);
/*     */     }
/* 155 */     repaint();
/*     */   }
/*     */   
/* 158 */   private class ControlPanel extends JPanel { ControlPanel(IORTPFindFeasiblePanel..3 x$1) { this(); }
/*     */     
/* 160 */     private JLabel prompt1 = null;
/* 161 */     private JPanel p1 = null;
/*     */     
/* 163 */     private JLabel prompt2 = null;
/* 164 */     private JButton bn_delete_row = null;
/* 165 */     private JButton bn_delete_col = null;
/* 166 */     private JPanel p2 = null;
/*     */     
/*     */ 
/*     */     private ControlPanel()
/*     */     {
/* 171 */       this.prompt1 = new JLabel("Double click a cell to set it as a basic variable.");
/* 172 */       this.p1 = new JPanel();
/*     */       
/* 174 */       this.p1.add(this.prompt1);
/*     */       
/* 176 */       this.bn_delete_row = new JButton("Delete Row");
/* 177 */       this.bn_delete_row.addActionListener(new IORTPFindFeasiblePanel.1((ControlPanel)this));
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
/* 191 */       this.bn_delete_col = new JButton("Delete Column");
/* 192 */       this.bn_delete_col.addActionListener(new IORTPFindFeasiblePanel.2((ControlPanel)this));
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
/* 206 */       JPanel p21 = new JPanel();
/* 207 */       p21.add(this.prompt2 = new JLabel("Select a row or column to delete"));
/*     */       
/* 209 */       JPanel p22 = new JPanel();
/* 210 */       p22.add(this.bn_delete_row);
/* 211 */       p22.add(this.bn_delete_col);
/* 212 */       this.p2 = new JPanel(new BorderLayout());
/* 213 */       this.p2.add(p21, "North");
/* 214 */       this.p2.add(p22, "South");
/*     */       
/* 216 */       setLayout(new BoxLayout(this, 1));
/* 217 */       add(this.p1);
/* 218 */       add(this.p2);
/*     */       
/* 220 */       updatePanel();
/*     */       
/* 222 */       this.p1.setDoubleBuffered(false);
/* 223 */       this.p2.setDoubleBuffered(false);
/* 224 */       p21.setDoubleBuffered(false);
/* 225 */       p22.setDoubleBuffered(false);
/*     */     }
/*     */     
/*     */ 
/*     */     private void updatePanel()
/*     */     {
/* 231 */       switch (IORTPFindFeasiblePanel.this.step) {
/*     */       case 1: 
/* 233 */         this.prompt1.setVisible(true);
/* 234 */         this.prompt2.setVisible(false);
/* 235 */         this.bn_delete_row.setVisible(false);
/* 236 */         this.bn_delete_col.setVisible(false);
/* 237 */         break;
/*     */       
/*     */       case 2: 
/* 240 */         if (!IORTPFindFeasiblePanel.this.canvasPanel.isLastRowOrColumn()) {
/* 241 */           this.prompt1.setVisible(false);
/* 242 */           this.prompt2.setVisible(true);
/* 243 */           this.bn_delete_row.setVisible(true);
/* 244 */           this.bn_delete_col.setVisible(true);
/*     */         }
/* 246 */         break;
/*     */       
/*     */       default: 
/* 249 */         System.err.println("Error in tracing panel steps");
/*     */       }
/*     */       
/*     */       
/* 253 */       revalidate();
/* 254 */       repaint();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/* 262 */     String str = " Find Initial Basic Feasible Solution - for Interactive Method\n\n";
/* 263 */     str = String.valueOf(String.valueOf(str)).concat("This procedure interactively implements the 4-step \"General Procedure for ");
/* 264 */     str = String.valueOf(String.valueOf(str)).concat("Constructing an Initial Basic Feasible Solution\" (for a transportation ");
/* 265 */     str = String.valueOf(String.valueOf(str)).concat("problem) presented in Sec. 8.2.  For step 1, any criterion can be used ");
/* 266 */     str = String.valueOf(String.valueOf(str)).concat("for selecting the next basic variable. To use either Vogel's or Russell's ");
/* 267 */     str = String.valueOf(String.valueOf(str)).concat("approximation method, choose that criterion under the Option menu. To use ");
/* 268 */     str = String.valueOf(String.valueOf(str)).concat("ANY other criterion, choose Northwest Corner Rule under the Option menu. ");
/* 269 */     str = String.valueOf(String.valueOf(str)).concat("\nWhen done, the solution should have (m+n-1) basic variables that exactly ");
/* 270 */     str = String.valueOf(String.valueOf(str)).concat("use up the supply from source and the demand at each destination. Use ");
/* 271 */     str = String.valueOf(String.valueOf(str)).concat("Print to file under the File menu to print out your work. To start the transportation ");
/* 272 */     str = String.valueOf(String.valueOf(str)).concat("with this initial basic feasible solution, choose the \"Solve Interactively ");
/* 273 */     str = String.valueOf(String.valueOf(str)).concat("by the Transportation Simplex Method\" command under the Procedure menu.\n\n");
/* 274 */     str = String.valueOf(String.valueOf(str)).concat("Selecting the next basic variable\n\n");
/* 275 */     str = String.valueOf(String.valueOf(str)).concat("Move the pointer by the mouse so that it lies on the Source row and Destination ");
/* 276 */     str = String.valueOf(String.valueOf(str)).concat("column that corresponds to your choice of the next basic variable, and then ");
/* 277 */     str = String.valueOf(String.valueOf(str)).concat("click the cell. You cannot use a row or column that has already been eliminated ");
/* 278 */     str = String.valueOf(String.valueOf(str)).concat("from consideration.");
/* 279 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 280 */     this.help_content_step.setText(str);
/* 281 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/* 288 */     String str = "Finding an Initial Basic Feasible Solution - for Interactive Method \n\n";
/* 289 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to interactively implement the \"General Procedure for ");
/* 290 */     str = String.valueOf(String.valueOf(str)).concat("Constructing an Initial Basic Feasible Solution\" (for a transportation ");
/* 291 */     str = String.valueOf(String.valueOf(str)).concat("problem) presented in the \"Initialization\" subsection of Sec. 8.2. The ");
/* 292 */     str = String.valueOf(String.valueOf(str)).concat("specific transportation problem considered is the last one you entered ");
/* 293 */     str = String.valueOf(String.valueOf(str)).concat("with the Entering procedure under the Procedure menu.\n\n");
/* 294 */     str = String.valueOf(String.valueOf(str)).concat("For step 1 of the General Procedure, you may use any ");
/* 295 */     str = String.valueOf(String.valueOf(str)).concat("criterion (including arbitrary selection) for selecting the next basic ");
/* 296 */     str = String.valueOf(String.valueOf(str)).concat("variable. However, if you wish to use either Vogel's approximation method ");
/* 297 */     str = String.valueOf(String.valueOf(str)).concat("or Russell's approximation method as your criterion, choose that one under ");
/* 298 */     str = String.valueOf(String.valueOf(str)).concat("the Option menu and the computer will compute the quantities needed to implement that criterion. For any other criterion (including the Northwest corner ");
/* 299 */     str = String.valueOf(String.valueOf(str)).concat("rule), choose the Northwest Corner Rule under the Option menu.\n\n");
/* 300 */     str = String.valueOf(String.valueOf(str)).concat("You may restart this procedure as often as desired in order to implement ");
/* 301 */     str = String.valueOf(String.valueOf(str)).concat("different criteria on the same problem. Each time you finish constructing ");
/* 302 */     str = String.valueOf(String.valueOf(str)).concat("an initial basic feasible solution, you can print out your work by choosing ");
/* 303 */     str = String.valueOf(String.valueOf(str)).concat("Print to file under the File menu. To use this initial basic feasible solution to ");
/* 304 */     str = String.valueOf(String.valueOf(str)).concat("start the transportation simplex method, next choose \"Solve Interactively by ");
/* 305 */     str = String.valueOf(String.valueOf(str)).concat("the Transportation Simplex Method\" under the Procedure menu.");
/* 306 */     str = String.valueOf(String.valueOf(str)).concat("\n\nNote: The abbreviation R.D. is used for Remaining Demand.");
/* 307 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*     */     
/* 309 */     this.help_content_procedure.setText(str);
/* 310 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/* 313 */   private String str0 = " Find Initial Basic Feasible Solution - for Interactive Method\n\n";
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
/* 326 */   private String str1 = "Selecting the next basic variable\n\n";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 336 */   private String str2 = "Determining whether the row or the column should be eliminated\n\n";
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
/* 348 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/* 349 */     switch (this.step) {
/*     */     case 1: 
/* 351 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str1).append(str))));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 360 */       break;
/*     */     case 2: 
/* 354 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str2).append(str))));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 360 */       break;
/*     */     default: 
/* 357 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
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
/*     */   public void SaveFile(ObjectOutputStream out)
/*     */   {
/*     */     try
/*     */     {
/* 372 */       out.writeObject(this.state);
/* 373 */       out.writeObject(this.operation_sequence);
/* 374 */       out.writeObject(new Integer(this.step));
/* 375 */       int[] sel = new int[2];
/* 376 */       this.canvasPanel.canvas.getSelCell(sel);
/* 377 */       out.writeObject(sel);
/*     */     }
/*     */     catch (Exception e1) {
/* 380 */       e1.printStackTrace();
/* 381 */       System.out.println("Save fails: ");
/*     */     }
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
/*     */     try
/*     */     {
/* 395 */       this.state = ((IORState)in.readObject());
/* 396 */       this.operation_sequence = ((Vector)in.readObject());
/* 397 */       this.step = ((Integer)in.readObject()).intValue();
/* 398 */       this.state.setSolverStepVector();
/* 399 */       int[] sel = (int[])in.readObject();
/* 400 */       if ((sel[0] >= 0) && (sel[1] >= 0))
/* 401 */         this.canvasPanel.canvas.setSelCell(sel[0], sel[1]);
/* 402 */       this.canvasPanel.canvas.setState();
/*     */     }
/*     */     catch (Exception e1) {
/* 405 */       System.out.println("Load fails");
/* 406 */       return;
/*     */     }
/* 408 */     updatePanel();
/*     */     
/* 410 */     if (this.bn_back.isVisible()) {
/* 411 */       if ((this.state == null) || (this.state.isEmpty())) {
/* 412 */         this.bn_back.setEnabled(false);
/*     */       } else {
/* 414 */         this.bn_back.setEnabled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTPFindFeasiblePanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */