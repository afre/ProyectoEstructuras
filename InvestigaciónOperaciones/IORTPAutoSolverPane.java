/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class IORTPAutoSolverPane extends IORTPActionPanel
/*     */ {
/*     */   private IORTPProcessController controller;
/*     */   private Font f;
/*     */   private Font f1;
/*     */   private Image offscreen;
/*     */   private Graphics gS2;
/*  22 */   private IORTPAutoSolverPane.CanvasPanel canvas = new IORTPAutoSolverPane.CanvasPanel();
/*  23 */   private JPanel controlPane = new JPanel();
/*  24 */   private JButton autoBut = new JButton("Auto");
/*  25 */   private JButton resetBut = new JButton("Reset");
/*  26 */   private int leftScape = 100;
/*  27 */   private int topScape = 100;
/*  28 */   private int tableLineWid = 380;
/*  29 */   private int tableRightSpace = 90;
/*  30 */   private boolean showResult = false;
/*     */   
/*     */   public IORTPAutoSolverPane(IORTPProcessController c) {
/*  33 */     super(c);
/*  34 */     this.controller = c;
/*  35 */     this.canvas.setSize(600, 400);
/*     */     
/*  37 */     this.controlPane.setLayout(new java.awt.FlowLayout());
/*  38 */     this.controlPane.add(this.autoBut);
/*  39 */     this.controlPane.add(new javax.swing.JLabel("      "));
/*  40 */     this.controlPane.add(this.resetBut);
/*  41 */     this.controlPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 30, 60, 30));
/*  42 */     setLayout(new java.awt.BorderLayout());
/*  43 */     add(this.canvas, "Center");
/*     */     
/*     */ 
/*  46 */     this.autoBut.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  48 */         IORTPAutoSolverPane.this.showResult = true;
/*  49 */         IORTPAutoSolverPane.this.controller.data.auto_ShowResult = true;
/*  50 */         IORTPAutoSolverPane.this.repaint();
/*     */       }
/*     */       
/*  53 */     });
/*  54 */     this.resetBut.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  56 */         IORTPAutoSolverPane.this.showResult = false;
/*  57 */         IORTPAutoSolverPane.this.controller.data.auto_ShowResult = false;
/*  58 */         IORTPAutoSolverPane.this.repaint();
/*     */       }
/*  60 */     });
/*  61 */     this.showResult = true;
/*  62 */     this.controller.data.auto_ShowResult = true;
/*  63 */     repaint();
/*     */   }
/*     */   
/*  66 */   public void updatePanel() { this.showResult = this.controller.data.auto_ShowResult; }
/*     */   
/*     */ 
/*     */   protected void backStep() {}
/*     */   
/*     */ 
/*     */   protected void initializeCurrentStepHelpPanel()
/*     */   {
/*  74 */     String str = "Solve Automatically by the Transportation Simplex Method.";
/*     */     
/*  76 */     str = String.valueOf(String.valueOf(str)).concat("\n\nThis procedure gives out the solution to the transportation problem that you just entered.");
/*     */     
/*  78 */     str = String.valueOf(String.valueOf(str)).concat("\n\nPress the Enter key to continue the current procedure.");
/*     */     
/*  80 */     this.help_content_step.setText(str);
/*  81 */     this.help_content_step.revalidate();
/*     */   }
/*     */   
/*     */   protected void initializeCurrentProcedureHelpPanel()
/*     */   {
/*  86 */     String str = "Solve Automatically by the Transportation Simplex Method.";
/*  87 */     str = String.valueOf(String.valueOf(str)).concat("\n\nThis procedure gives out the solution to the transportation problem that you just entered.");
/*     */     
/*     */ 
/*  90 */     str = String.valueOf(String.valueOf(str)).concat("\n\nPress the Enter key to continue the current procedure.");
/*     */     
/*  92 */     this.help_content_procedure.setText(str);
/*  93 */     this.help_content_procedure.revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void finishProcedure() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void savePrintInfo() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public class CanvasPanel
/*     */     extends JPanel
/*     */   {
/*     */     CanvasPanel()
/*     */     {
/* 119 */       init();
/*     */     }
/*     */     
/*     */     public void init() {
/*     */       try {
/* 124 */         jbInit();
/*     */       }
/*     */       catch (Exception e) {
/* 127 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*     */     private void jbInit() throws Exception
/*     */     {
/* 133 */       IORTPAutoSolverPane.this.f = new Font("Courier", 0, 12);
/* 134 */       IORTPAutoSolverPane.this.f1 = new Font("Courier", 1, 12);
/* 135 */       setSize(600, 400);
/* 136 */       repaint();
/*     */     }
/*     */     
/* 139 */     public void paint(Graphics g) { if (IORTPAutoSolverPane.this.offscreen == null)
/* 140 */         IORTPAutoSolverPane.this.offscreen = createImage(getSize().width, getSize().height);
/* 141 */       IORTPAutoSolverPane.this.gS2 = IORTPAutoSolverPane.this.offscreen.getGraphics();
/* 142 */       IORTPAutoSolverPane.this.gS2.clearRect(0, 0, getSize().width, getSize().height);
/* 143 */       IORTPAutoSolverPane.this.gS2.setColor(Color.black);
/* 144 */       IORTPAutoSolverPane.this.gS2.setFont(IORTPAutoSolverPane.this.f1);
/* 145 */       if (IORTPAutoSolverPane.this.showResult) {
/* 146 */         switch (IORTPAutoSolverPane.this.controller.data.num_demand) {
/*     */         case 1: 
/* 148 */           IORTPAutoSolverPane.this.leftScape = 160;
/* 149 */           IORTPAutoSolverPane.this.tableLineWid = 260;
/* 150 */           IORTPAutoSolverPane.this.tableRightSpace = 152;
/* 151 */           IORTPAutoSolverPane.this.gS2.drawString("Destination", getSize().width / 2 - 90, IORTPAutoSolverPane.this.topScape - 20);
/* 152 */           break;
/*     */         case 2: 
/* 154 */           IORTPAutoSolverPane.this.leftScape = 145;
/* 155 */           IORTPAutoSolverPane.this.tableLineWid = 270;
/* 156 */           IORTPAutoSolverPane.this.tableRightSpace = 120;
/* 157 */           IORTPAutoSolverPane.this.gS2.drawString("Destination", getSize().width / 2 - 90, IORTPAutoSolverPane.this.topScape - 20);
/* 158 */           break;
/*     */         case 3: 
/* 160 */           IORTPAutoSolverPane.this.leftScape = 130;
/* 161 */           IORTPAutoSolverPane.this.tableLineWid = 310;
/* 162 */           IORTPAutoSolverPane.this.tableRightSpace = 115;
/* 163 */           IORTPAutoSolverPane.this.gS2.drawString("Destination", getSize().width / 2 - 80, IORTPAutoSolverPane.this.topScape - 20);
/* 164 */           break;
/*     */         case 4: 
/* 166 */           IORTPAutoSolverPane.this.leftScape = 115;
/* 167 */           IORTPAutoSolverPane.this.tableLineWid = 360;
/* 168 */           IORTPAutoSolverPane.this.tableRightSpace = 120;
/* 169 */           IORTPAutoSolverPane.this.gS2.drawString("Destination", getSize().width / 2 - 70, IORTPAutoSolverPane.this.topScape - 20);
/* 170 */           break;
/*     */         case 5: 
/* 172 */           IORTPAutoSolverPane.this.leftScape = 100;
/* 173 */           IORTPAutoSolverPane.this.tableLineWid = 380;
/* 174 */           IORTPAutoSolverPane.this.tableRightSpace = 90;
/* 175 */           IORTPAutoSolverPane.this.gS2.drawString("Destination", getSize().width / 2 - 70, IORTPAutoSolverPane.this.topScape - 20);
/* 176 */           break;
/*     */         case 6: 
/* 178 */           IORTPAutoSolverPane.this.leftScape = 90;
/* 179 */           IORTPAutoSolverPane.this.tableLineWid = 440;
/* 180 */           IORTPAutoSolverPane.this.tableRightSpace = 107;
/* 181 */           IORTPAutoSolverPane.this.gS2.drawString("Destination", getSize().width / 2 - 50, IORTPAutoSolverPane.this.topScape - 20);
/* 182 */           break;
/*     */         case 7: 
/* 184 */           IORTPAutoSolverPane.this.leftScape = 80;
/* 185 */           IORTPAutoSolverPane.this.tableLineWid = 485;
/* 186 */           IORTPAutoSolverPane.this.tableRightSpace = 110;
/* 187 */           IORTPAutoSolverPane.this.gS2.drawString("Destination", getSize().width / 2 - 50, IORTPAutoSolverPane.this.topScape - 20);
/*     */         }
/*     */         
/*     */         
/*     */ 
/*     */ 
/* 193 */         java.awt.FontMetrics testF = IORTPAutoSolverPane.this.gS2.getFontMetrics();
/*     */         
/* 195 */         int characterWid = 6;
/*     */         
/* 197 */         IORTPAutoSolverPane.this.gS2.setFont(IORTPAutoSolverPane.this.f);
/*     */         
/* 199 */         for (int i = 0; i < IORTPAutoSolverPane.this.controller.data.outputV.size(); i++) {
/* 200 */           String temp = (String)IORTPAutoSolverPane.this.controller.data.outputV.elementAt(i);
/* 201 */           int length = 0;
/* 202 */           for (int j = 0; j < temp.length(); j++) {
/* 203 */             String tem = temp.substring(j, j + 1);
/*     */             
/* 205 */             characterWid = 8;
/* 206 */             length += characterWid;
/* 207 */             IORTPAutoSolverPane.this.gS2.drawString(tem, IORTPAutoSolverPane.this.leftScape + length, IORTPAutoSolverPane.this.topScape + i * 20);
/*     */           }
/*     */           
/* 210 */           System.out.println(temp);
/*     */           
/*     */ 
/* 213 */           if (i == 0) {
/* 214 */             IORTPAutoSolverPane.this.gS2.drawLine(IORTPAutoSolverPane.this.leftScape, IORTPAutoSolverPane.this.topScape + i * 20 + 4, IORTPAutoSolverPane.this.leftScape + IORTPAutoSolverPane.this.tableLineWid, IORTPAutoSolverPane.this.topScape + i * 20 + 4);
/* 215 */           } else if (i == IORTPAutoSolverPane.this.controller.data.outputV.size() - 2) {
/* 216 */             IORTPAutoSolverPane.this.gS2.drawLine(IORTPAutoSolverPane.this.leftScape, IORTPAutoSolverPane.this.topScape + i * 20 + 4, IORTPAutoSolverPane.this.leftScape + IORTPAutoSolverPane.this.tableLineWid, IORTPAutoSolverPane.this.topScape + i * 20 + 4);
/* 217 */           } else if (i == IORTPAutoSolverPane.this.controller.data.outputV.size() - 1) {
/* 218 */             IORTPAutoSolverPane.this.gS2.drawLine(IORTPAutoSolverPane.this.leftScape + 60, IORTPAutoSolverPane.this.topScape - 15, IORTPAutoSolverPane.this.leftScape + 60, IORTPAutoSolverPane.this.topScape + i * 20);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 223 */       g.drawImage(IORTPAutoSolverPane.this.offscreen, 0, 0, this);
/* 224 */       IORTPAutoSolverPane.this.gS2.dispose();
/*     */     }
/*     */     
/*     */     public void update(Graphics g) {
/* 228 */       paint(g);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTPAutoSolverPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */