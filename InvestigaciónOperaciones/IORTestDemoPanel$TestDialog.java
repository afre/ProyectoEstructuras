/*     */ import java.awt.Container;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORTestDemoPanel$TestDialog
/*     */   extends JFrame
/*     */   implements ActionListener
/*     */ {
/*     */   private JPanel confirmPanel;
/*     */   public JButton nextButt;
/*     */   public JButton autoButt;
/*     */   public JButton backButt;
/*     */   public JButton pauseButt;
/*     */   public JButton stopButt;
/*     */   public JRadioButton slowRadio;
/*     */   public JRadioButton fastRadio;
/*     */   private IORTestDemoPanel demoPanel;
/*     */   private boolean isPause;
/*     */   private final IORTestDemoPanel this$0;
/*     */   
/*     */   public IORTestDemoPanel$TestDialog(IORTestDemoPanel this$0, String demoName, IORTestDemoPanel demoPane)
/*     */   {
/* 225 */     this.this$0 = this$0;this.confirmPanel = new JPanel();this.nextButt = new JButton("Next");this.autoButt = new JButton("Auto");this.backButt = new JButton("Back");this.pauseButt = new JButton("Pause");this.stopButt = new JButton("Stop");this.slowRadio = new JRadioButton("Slow", true);this.fastRadio = new JRadioButton("Fast", false);this.isPause = false;
/* 226 */     this.demoPanel = demoPane;
/*     */     
/* 228 */     setTitle(demoName);
/*     */     
/* 230 */     setResizable(false);
/* 231 */     this.backButt.setEnabled(false);
/* 232 */     this.stopButt.setEnabled(false);
/* 233 */     this.pauseButt.setEnabled(false);
/*     */     
/* 235 */     cavPanelInit();
/* 236 */     this.nextButt.addActionListener(this);
/* 237 */     this.autoButt.addActionListener(this);
/* 238 */     this.backButt.addActionListener(this);
/* 239 */     this.stopButt.addActionListener(this);
/* 240 */     this.pauseButt.addActionListener(this);
/*     */     
/* 242 */     ButtonGroup group = new ButtonGroup();
/* 243 */     group.add(this.slowRadio);
/* 244 */     group.add(this.fastRadio);
/* 245 */     this.fastRadio.setSelected(true);
/* 246 */     this.slowRadio.addItemListener(new IORTestDemoPanel.1((TestDialog)this));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 253 */     this.fastRadio.addItemListener(new IORTestDemoPanel.2((TestDialog)this));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void cavPanelInit()
/*     */   {
/* 262 */     this.confirmPanel.add(this.slowRadio);
/* 263 */     this.confirmPanel.add(this.fastRadio);
/*     */     
/* 265 */     this.confirmPanel.add(this.autoButt);
/* 266 */     this.confirmPanel.add(this.backButt);
/* 267 */     this.confirmPanel.add(this.nextButt);
/*     */     
/*     */ 
/* 270 */     this.confirmPanel.add(this.pauseButt);
/* 271 */     this.confirmPanel.add(this.stopButt);
/* 272 */     getContentPane().add(this.confirmPanel);
/*     */   }
/*     */   
/* 275 */   public void actionPerformed(ActionEvent e) { Object obj = e.getSource();
/* 276 */     if (obj == this.nextButt) {
/* 277 */       findOptionEnabledLocation();
/*     */       
/* 279 */       this.this$0.isAuto = false;
/* 280 */       if (this.this$0.test != null) this.this$0.test = null;
/* 281 */       this.this$0.test = new playThread(this.demoPanel);
/* 282 */       this.this$0.test.setSleepTime(IORTestDemoPanel.access$2(this.this$0));
/* 283 */       this.this$0.test.start();
/* 284 */       this.nextButt.setEnabled(false);
/* 285 */       this.backButt.setEnabled(false);
/* 286 */       this.stopButt.setEnabled(true);
/* 287 */       this.slowRadio.setEnabled(false);
/* 288 */       this.fastRadio.setEnabled(false);
/* 289 */       this.pauseButt.setText("Pause");
/* 290 */       this.isPause = false;
/* 291 */       this.pauseButt.setEnabled(false);
/*     */     }
/* 293 */     if (obj == this.pauseButt) {
/* 294 */       if (!this.isPause) {
/* 295 */         this.pauseButt.setText("Resume");
/* 296 */         this.isPause = true;
/* 297 */         this.this$0.test.pauseSimulate();
/*     */       } else {
/* 299 */         this.pauseButt.setText("Pause");
/* 300 */         this.isPause = false;
/* 301 */         this.this$0.test.resumeSimulate();
/*     */       }
/*     */     }
/* 304 */     if (obj == this.autoButt) {
/* 305 */       this.this$0.isAuto = true;
/* 306 */       findOptionEnabledLocation();
/* 307 */       if (this.this$0.test != null) this.this$0.test = null;
/* 308 */       this.this$0.test = new playThread(this.demoPanel);
/* 309 */       this.this$0.test.setSleepTime(IORTestDemoPanel.access$2(this.this$0));
/* 310 */       this.this$0.test.start();
/* 311 */       this.autoButt.setEnabled(false);
/* 312 */       this.nextButt.setEnabled(false);
/* 313 */       this.backButt.setEnabled(false);
/* 314 */       this.stopButt.setEnabled(true);
/* 315 */       this.slowRadio.setEnabled(false);
/* 316 */       this.fastRadio.setEnabled(false);
/* 317 */       this.pauseButt.setEnabled(true);
/*     */     }
/* 319 */     if (obj == this.backButt) {
/* 320 */       this.this$0.showMouse = false;
/*     */       
/* 322 */       if (this.this$0.nextNum == 1) {
/* 323 */         this.backButt.setEnabled(false);
/*     */       }
/* 325 */       this.this$0.nextNum -= 1;
/* 326 */       this.this$0.test.moveTimes = ((Integer)this.this$0.stepNumV.elementAt(this.this$0.nextNum)).intValue();
/* 327 */       if (this.this$0.nextNum != 0) {
/* 328 */         this.this$0.menuStr = ((String)this.this$0.menuShowV.elementAt(this.this$0.nextNum - 1));
/*     */       } else
/* 330 */         this.this$0.menuStr = "";
/* 331 */       this.this$0.backIcon = this.this$0.createImageIcon((String)this.this$0.demoImageV.elementAt(this.this$0.nextNum), "");
/*     */       
/* 333 */       this.this$0.backLabel.setIcon(this.this$0.backIcon);
/*     */       
/* 335 */       System.out.println("BACK");
/* 336 */       this.nextButt.setEnabled(true);
/*     */       
/* 338 */       if (this.this$0.nextNum < this.this$0.optionEnabledLocation) {
/* 339 */         this.this$0.tutor.option.setEnabled(false);
/*     */       } else {
/* 341 */         this.this$0.tutor.option.setEnabled(true);
/*     */       }
/*     */     }
/*     */     
/* 345 */     if (obj == this.stopButt) {
/* 346 */       this.this$0.test.stop();
/* 347 */       reset();
/* 348 */       this.this$0.menuStr = "";
/* 349 */       this.this$0.test.moveTimes = ((Integer)this.this$0.stepNumV.elementAt(this.this$0.nextNum)).intValue();
/*     */       
/* 351 */       this.this$0.backIcon = this.this$0.createImageIcon((String)this.this$0.demoImageV.elementAt(this.this$0.nextNum), "");
/* 352 */       this.this$0.backLabel.setIcon(this.this$0.backIcon);
/*     */       
/* 354 */       System.out.println("STOP");
/*     */     }
/*     */   }
/*     */   
/*     */   public void exit() {
/*     */     try {
/* 360 */       exit();
/*     */     }
/*     */     catch (Exception localException) {}
/*     */   }
/*     */   
/*     */   public void findOptionEnabledLocation()
/*     */   {
/* 367 */     this.this$0.optionEnabledLocation = Integer.MAX_VALUE;
/* 368 */     for (int i = 0; i < this.this$0.menuShowV.size(); i++) {
/* 369 */       String temp = (String)this.this$0.menuShowV.elementAt(i);
/* 370 */       if (temp.equalsIgnoreCase("Option")) {
/* 371 */         this.this$0.optionEnabledLocation = i;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void reset() {
/* 377 */     this.pauseButt.setText("Pause");
/* 378 */     this.isPause = false;
/* 379 */     this.this$0.showMouse = false;
/* 380 */     this.autoButt.setEnabled(true);
/* 381 */     this.nextButt.setEnabled(true);
/* 382 */     this.backButt.setEnabled(false);
/* 383 */     this.stopButt.setEnabled(false);
/* 384 */     this.pauseButt.setEnabled(false);
/* 385 */     this.slowRadio.setEnabled(true);
/* 386 */     this.fastRadio.setEnabled(true);
/* 387 */     this.this$0.nextNum = 0;
/* 388 */     this.this$0.drawPoint.x = ((Point)this.this$0.startPointV.elementAt(this.this$0.nextNum)).x;
/* 389 */     this.this$0.drawPoint.y = ((Point)this.this$0.startPointV.elementAt(this.this$0.nextNum)).y;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTestDemoPanel$TestDialog.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */