/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.File;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JRadioButtonMenuItem;
/*     */ import javax.swing.JScrollPane;
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
/*     */ public class IORMCProcessController
/*     */   extends IORProcessController
/*     */ {
/*     */   public JRadioButtonMenuItem mi_enter_transition_matrix;
/*     */   public JRadioButtonMenuItem mi_chapman_kolmogorov_equations;
/*     */   public JRadioButtonMenuItem mi_steady_state_probabilities;
/*  34 */   private IORMCEnterPanel enterPanel = null;
/*  35 */   private IORMCCKEquatePanel equatePanel = null;
/*  36 */   private IORMCSSProbaPanel probaPanel = null;
/*     */   
/*     */ 
/*  39 */   public IORMCProcessController.MCData data = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ORMCSolver solver;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IORMCProcessController(IORTutorial t)
/*     */   {
/*  51 */     super(t);
/*  52 */     initializeAreaHelpPanel();
/*  53 */     this.solver = new ORMCSolver();
/*  54 */     this.data = new IORMCProcessController.MCData();
/*     */     
/*  56 */     this.solver.getData(this.data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeAreaHelpPanel()
/*     */   {
/*  64 */     String str = "The current area is \"Markov Chains.\" You can select ";
/*  65 */     str = String.valueOf(String.valueOf(str)).concat("a procedure from the Procedure menu to start.");
/*  66 */     str = String.valueOf(String.valueOf(str)).concat("\n\n");
/*  67 */     str = String.valueOf(String.valueOf(str)).concat("If you would like to choose an area other than \"Markov Chains\" within which ");
/*  68 */     str = String.valueOf(String.valueOf(str)).concat("to work, select the Area menu.\n");
/*     */     
/*  70 */     MultilineLabel content = new MultilineLabel(str);
/*  71 */     content.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*     */     
/*  73 */     this.areaHelpPanel = new MultilinePanel(content, this.tutorial.scrollPane);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void setMenuState(int currentStep) {}
/*     */   
/*     */ 
/*     */ 
/*     */   protected void setDefaultMenuState() {}
/*     */   
/*     */ 
/*     */ 
/*     */   private void enterproblem()
/*     */   {
/*  88 */     this.enterPanel = new IORMCEnterPanel(this);
/*  89 */     setHelpEnabled(true);
/*     */     
/*  91 */     this.currentActionPanel = this.enterPanel;
/*  92 */     this.tutorial.scrollPane.setViewportView(this.enterPanel);
/*  93 */     this.enterPanel.setPanelEnable(true);
/*  94 */     this.enterPanel.bn_back.setVisible(false);
/*  95 */     this.mi_chapman_kolmogorov_equations.setEnabled(false);
/*  96 */     this.mi_steady_state_probabilities.setEnabled(false);
/*  97 */     this.enterPanel.revalidate();
/*  98 */     this.enterPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   private void equateproblem()
/*     */   {
/* 104 */     this.equatePanel = null;
/* 105 */     this.equatePanel = new IORMCCKEquatePanel(this);
/*     */     
/*     */ 
/*     */ 
/* 109 */     this.currentActionPanel = this.equatePanel;
/* 110 */     this.tutorial.scrollPane.setViewportView(this.equatePanel);
/* 111 */     this.equatePanel.bn_back.setVisible(false);
/* 112 */     this.equatePanel.bn_finish.setVisible(false);
/* 113 */     this.equatePanel.revalidate();
/* 114 */     this.equatePanel.repaint();
/*     */   }
/*     */   
/*     */   private void probaproblem()
/*     */   {
/* 119 */     this.probaPanel = null;
/* 120 */     this.probaPanel = new IORMCSSProbaPanel(this);
/*     */     
/*     */ 
/*     */ 
/* 124 */     this.currentActionPanel = this.probaPanel;
/* 125 */     this.tutorial.scrollPane.setViewportView(this.probaPanel);
/* 126 */     this.probaPanel.bn_back.setVisible(false);
/* 127 */     this.probaPanel.bn_finish.setVisible(false);
/* 128 */     this.probaPanel.revalidate();
/* 129 */     this.probaPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void createMyMenus()
/*     */   {
/* 136 */     ButtonGroup group = new ButtonGroup();
/*     */     
/*     */ 
/* 139 */     this.mi_enter_transition_matrix = new JRadioButtonMenuItem("Enter Transition Matrix");
/* 140 */     this.tutorial.procedure.add(this.mi_enter_transition_matrix);
/* 141 */     group.add(this.mi_enter_transition_matrix);
/* 142 */     this.mi_enter_transition_matrix.setMnemonic('E');
/* 143 */     this.mi_enter_transition_matrix.setEnabled(true);
/* 144 */     this.mi_enter_transition_matrix.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 146 */         IORMCProcessController.this.procedure = "Enter Transition Matrix";
/* 147 */         IORMCProcessController.this.setStatusBar();
/* 148 */         IORMCProcessController.this.enterproblem();
/* 149 */         IORMCProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 152 */     });
/* 153 */     this.mi_chapman_kolmogorov_equations = new JRadioButtonMenuItem("Chapman-Kolmogorov Equations");
/* 154 */     this.tutorial.procedure.add(this.mi_chapman_kolmogorov_equations);
/* 155 */     group.add(this.mi_chapman_kolmogorov_equations);
/* 156 */     this.mi_chapman_kolmogorov_equations.setMnemonic('C');
/* 157 */     this.mi_chapman_kolmogorov_equations.setEnabled(false);
/* 158 */     this.mi_chapman_kolmogorov_equations.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 160 */         IORMCProcessController.this.procedure = "Chapman-Kolmogorov Equations";
/* 161 */         IORMCProcessController.this.equateproblem();
/* 162 */         IORMCProcessController.this.setStatusBar();
/* 163 */         IORMCProcessController.this.tutorial.closeDemoController();
/*     */       }
/*     */       
/* 166 */     });
/* 167 */     this.mi_steady_state_probabilities = new JRadioButtonMenuItem("Steady State Probabilities");
/* 168 */     this.tutorial.procedure.add(this.mi_steady_state_probabilities);
/* 169 */     group.add(this.mi_steady_state_probabilities);
/* 170 */     this.mi_steady_state_probabilities.setMnemonic('S');
/* 171 */     this.mi_steady_state_probabilities.setEnabled(false);
/* 172 */     this.mi_steady_state_probabilities.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 174 */         IORMCProcessController.this.procedure = "Steady State Probabilities";
/* 175 */         IORMCProcessController.this.setStatusBar();
/* 176 */         IORMCProcessController.this.probaproblem();
/* 177 */         IORMCProcessController.this.tutorial.closeDemoController();
/*     */       }
/* 179 */     });
/* 180 */     this.tutorial.option.setEnabled(false);
/*     */   }
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
/*     */   protected String getArea()
/*     */   {
/* 203 */     return "Markov Chains";
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
/* 214 */       out.writeObject(this.solver);
/* 215 */       out.writeObject(this.data);
/* 216 */       out.writeObject(this.procedure);
/* 217 */       out.writeObject(this.option);
/* 218 */       out.writeObject(new Boolean(this.mi_enter_transition_matrix.isEnabled()));
/* 219 */       out.writeObject(new Boolean(this.mi_chapman_kolmogorov_equations.isEnabled()));
/* 220 */       out.writeObject(new Boolean(this.mi_steady_state_probabilities.isEnabled()));
/*     */       
/* 222 */       if ((this.currentActionPanel instanceof IORMCEnterPanel)) {
/* 223 */         out.writeObject("IORMCEnterPanel");
/*     */       }
/* 225 */       else if ((this.currentActionPanel instanceof IORMCCKEquatePanel)) {
/* 226 */         out.writeObject("IORMCCKEquatePanel");
/*     */       }
/* 228 */       else if ((this.currentActionPanel instanceof IORMCSSProbaPanel)) {
/* 229 */         out.writeObject("IORMCSSProbaPanel");
/*     */       }
/*     */       else {
/* 232 */         System.out.println("Save file fails");
/* 233 */         return;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 237 */       System.out.println("Save file fails");
/* 238 */       return;
/*     */     }
/*     */     
/* 241 */     this.currentActionPanel.SaveFile(out);
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
/* 254 */       this.solver = ((ORMCSolver)in.readObject());
/* 255 */       this.data = ((IORMCProcessController.MCData)in.readObject());
/* 256 */       this.procedure = ((String)in.readObject());
/* 257 */       this.option = ((String)in.readObject());
/* 258 */       this.mi_enter_transition_matrix.setEnabled(((Boolean)in.readObject()).booleanValue());
/* 259 */       this.mi_chapman_kolmogorov_equations.setEnabled(((Boolean)in.readObject()).booleanValue());
/* 260 */       this.mi_steady_state_probabilities.setEnabled(((Boolean)in.readObject()).booleanValue());
/*     */       
/* 262 */       ActionPanelName = (String)in.readObject();
/*     */     }
/*     */     catch (Exception e) {
/*     */       String ActionPanelName;
/* 266 */       System.out.println("Open file fails"); return;
/*     */     }
/*     */     
/*     */     String ActionPanelName;
/*     */     
/* 271 */     setHelpEnabled(true);
/* 272 */     if (ActionPanelName.equalsIgnoreCase("IORMCEnterPanel")) {
/* 273 */       this.currentActionPanel = new IORMCEnterPanel(this);
/* 274 */       this.mi_enter_transition_matrix.setSelected(true);
/*     */     }
/* 276 */     else if (ActionPanelName.equalsIgnoreCase("IORMCCKEquatePanel")) {
/* 277 */       this.currentActionPanel = new IORMCCKEquatePanel(this);
/* 278 */       this.mi_chapman_kolmogorov_equations.setSelected(true);
/*     */     }
/* 280 */     else if (ActionPanelName.equalsIgnoreCase("IORMCSSProbaPanel")) {
/* 281 */       this.currentActionPanel = new IORMCSSProbaPanel(this);
/* 282 */       this.mi_steady_state_probabilities.setSelected(true);
/*     */     }
/*     */     else {
/* 285 */       setHelpEnabled(false);
/* 286 */       System.out.println("Open file fails");
/* 287 */       return;
/*     */     }
/*     */     
/* 290 */     setStatusBar();
/* 291 */     this.currentActionPanel.LoadFile(in);
/*     */     
/*     */ 
/* 294 */     this.tutorial.scrollPane.setViewportView(this.currentActionPanel);
/* 295 */     this.currentActionPanel.revalidate();
/* 296 */     this.currentActionPanel.repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void print()
/*     */   {
/* 304 */     int p = 1;
/* 305 */     if (this.mi_enter_transition_matrix.isSelected()) {
/* 306 */       p = 1;
/* 307 */     } else if ((this.mi_chapman_kolmogorov_equations.isEnabled()) && (this.mi_chapman_kolmogorov_equations.isSelected())) {
/* 308 */       p = 2;
/* 309 */     } else if ((this.mi_steady_state_probabilities.isEnabled()) && (this.mi_steady_state_probabilities.isSelected()))
/* 310 */       p = 3;
/* 311 */     this.solver.setPrintProcedure(p);
/*     */     
/* 313 */     PrinterJob printJob = PrinterJob.getPrinterJob();
/* 314 */     printJob.setPrintable(this.solver);
/* 315 */     boolean pDialogState = true;
/* 316 */     pDialogState = printJob.printDialog();
/* 317 */     if (pDialogState) {
/*     */       try {
/* 319 */         printJob.print();
/*     */       } catch (Exception ex) {
/* 321 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void printToFile()
/*     */   {
/* 330 */     int p = 1;
/* 331 */     if (this.mi_enter_transition_matrix.isSelected()) {
/* 332 */       p = 1;
/* 333 */     } else if ((this.mi_chapman_kolmogorov_equations.isEnabled()) && (this.mi_chapman_kolmogorov_equations.isSelected())) {
/* 334 */       p = 2;
/* 335 */     } else if ((this.mi_steady_state_probabilities.isEnabled()) && (this.mi_steady_state_probabilities.isSelected())) {
/* 336 */       p = 3;
/*     */     }
/* 338 */     JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
/* 339 */     int result = fc.showSaveDialog(null);
/* 340 */     File chosenFile = fc.getSelectedFile();
/* 341 */     if ((result == 0) && (chosenFile != null)) {
/* 342 */       this.solver.PrintToFile(chosenFile.getPath(), p);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MCData
/*     */     implements Serializable
/*     */   {
/*     */     public int max_matrix_dimension_supply;
/*     */     public int max_power_supply;
/*     */     public int matrix_dimension;
/*     */     public int power;
/*     */     public double[][] matrix;
/*     */     public double[][] raised_matrix;
/*     */     public double[][] coefficient_matrix;
/*     */     public double[] unit_matrix;
/*     */     public double[] result_matrix;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORMCProcessController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */