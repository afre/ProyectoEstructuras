/*     */ import java.awt.Point;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JRadioButton;
/*     */ 
/*     */ public class playThread extends Thread
/*     */ {
/*  11 */   public boolean isStopped = true;
/*  12 */   public boolean isSuspended = false;
/*  13 */   private int sleepTime = 500;
/*  14 */   public int moveTimes = 5;
/*  15 */   public static final int haltTime = 500;
/*  16 */   int test = 0;
/*     */   IORTestDemoPanel currentPanel;
/*     */   
/*  19 */   public playThread(IORTestDemoPanel currentP) { this.currentPanel = currentP; }
/*     */   
/*     */   public void stopSimulate()
/*     */   {
/*  23 */     if (this != null) {
/*  24 */       stop();
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void pauseSimulate() {
/*  29 */     this.isSuspended = true;
/*     */   }
/*     */   
/*     */   public synchronized void resumeSimulate()
/*     */   {
/*  34 */     this.isSuspended = false;
/*  35 */     notifyAll();
/*     */   }
/*     */   
/*     */   public void setSleepTime(int times) {
/*  39 */     this.sleepTime = times;
/*     */   }
/*     */   
/*     */   public void run()
/*     */   {
/*     */     try {
/*  45 */       if (this.currentPanel.isAuto) {
/*  46 */         for (??? = 0; ??? < this.currentPanel.demoImageV.size(); ???++) {
/*  47 */           this.test += 1;
/*  48 */           if (??? >= this.currentPanel.optionEnabledLocation - 1) {
/*  49 */             this.currentPanel.tutor.option.setEnabled(true);
/*     */           } else {
/*  51 */             this.currentPanel.tutor.option.setEnabled(false);
/*     */           }
/*  53 */           this.currentPanel.backIcon = this.currentPanel.createImageIcon((String)this.currentPanel.demoImageV.elementAt(???), "");
/*  54 */           this.currentPanel.backLabel.setIcon(this.currentPanel.backIcon);
/*  55 */           this.currentPanel.startPoint.x = ((Point)this.currentPanel.startPointV.elementAt(???)).x;
/*  56 */           this.currentPanel.startPoint.y = ((Point)this.currentPanel.startPointV.elementAt(???)).y;
/*  57 */           this.currentPanel.finishPoint.x = ((Point)this.currentPanel.finishPointV.elementAt(???)).x;
/*  58 */           this.currentPanel.finishPoint.y = ((Point)this.currentPanel.finishPointV.elementAt(???)).y;
/*  59 */           this.currentPanel.drawPoint.x = this.currentPanel.startPoint.x;
/*  60 */           this.currentPanel.drawPoint.y = this.currentPanel.startPoint.y;
/*  61 */           this.currentPanel.showMouse = true;
/*  62 */           this.currentPanel.nextNum = ???;
/*  63 */           this.currentPanel.apprchTarget();
/*     */           try
/*     */           {
/*  66 */             if (this.isSuspended) {
/*  67 */               synchronized (this) {
/*  68 */                 while (this.isSuspended) {
/*  69 */                   wait();
/*     */                 }
/*     */               }
/*     */             }
/*     */           } catch (InterruptedException e) {
/*  74 */             System.out.println(e);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 103 */           this.currentPanel.menuStr = ((String)this.currentPanel.menuShowV.elementAt(???));
/*     */         }
/* 105 */         this.currentPanel.testDialog.nextButt.setEnabled(true);
/* 106 */         this.currentPanel.testDialog.backButt.setEnabled(false);
/* 107 */         this.currentPanel.testDialog.stopButt.setEnabled(false);
/* 108 */         this.currentPanel.testDialog.autoButt.setEnabled(true);
/* 109 */         this.currentPanel.testDialog.slowRadio.setEnabled(true);
/* 110 */         this.currentPanel.testDialog.fastRadio.setEnabled(true);
/* 111 */         this.currentPanel.testDialog.pauseButt.setEnabled(false);
/* 112 */         Thread.sleep(this.sleepTime);
/* 113 */         this.currentPanel.showMouse = false;
/* 114 */         System.out.println("Thread End  !");
/*     */       } else {
/* 116 */         this.currentPanel.startPoint.x = ((Point)this.currentPanel.startPointV.elementAt(this.currentPanel.nextNum)).x;
/* 117 */         this.currentPanel.startPoint.y = ((Point)this.currentPanel.startPointV.elementAt(this.currentPanel.nextNum)).y;
/* 118 */         this.currentPanel.finishPoint.x = ((Point)this.currentPanel.finishPointV.elementAt(this.currentPanel.nextNum)).x;
/* 119 */         this.currentPanel.finishPoint.y = ((Point)this.currentPanel.finishPointV.elementAt(this.currentPanel.nextNum)).y;
/* 120 */         this.currentPanel.showMouse = true;
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         try
/*     */         {
/* 158 */           if (this.isSuspended) {
/* 159 */             synchronized (this) {
/* 160 */               while (this.isSuspended) {
/* 161 */                 wait();
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (InterruptedException e) {
/* 167 */           System.out.println(e);
/*     */         }
/*     */         
/* 170 */         this.currentPanel.apprchTarget();
/* 171 */         this.currentPanel.showMouse = false;
/* 172 */         this.currentPanel.nextNum += 1;
/*     */         
/* 174 */         this.currentPanel.menuStr = ((String)this.currentPanel.menuShowV.elementAt(this.currentPanel.nextNum - 1));
/* 175 */         if (this.currentPanel.nextNum < this.currentPanel.demoImageV.size()) {
/* 176 */           this.currentPanel.backIcon = this.currentPanel.createImageIcon((String)this.currentPanel.demoImageV.elementAt(this.currentPanel.nextNum), "");
/* 177 */           this.currentPanel.backLabel.setIcon(this.currentPanel.backIcon);
/*     */           
/*     */ 
/* 180 */           this.currentPanel.testDialog.nextButt.setEnabled(true);
/* 181 */           this.currentPanel.testDialog.backButt.setEnabled(true);
/*     */         } else {
/* 183 */           this.currentPanel.testDialog.nextButt.setEnabled(false);
/* 184 */           this.currentPanel.testDialog.backButt.setEnabled(true);
/*     */         }
/* 186 */         this.currentPanel.testDialog.slowRadio.setEnabled(true);
/* 187 */         this.currentPanel.testDialog.fastRadio.setEnabled(true);
/* 188 */         this.currentPanel.testDialog.pauseButt.setEnabled(false);
/* 189 */         this.currentPanel.showMouse = true;
/*     */         
/* 191 */         if (this.currentPanel.nextNum >= this.currentPanel.optionEnabledLocation - 1) {
/* 192 */           this.currentPanel.tutor.option.setEnabled(true);
/*     */         } else {
/* 194 */           this.currentPanel.tutor.option.setEnabled(false);
/*     */         }
/*     */         
/* 197 */         this.currentPanel.testDialog.backButt.setEnabled(true);
/*     */       }
/*     */     } catch (Exception temp) { Exception e;
/* 200 */       System.out.println(String.valueOf(String.valueOf(new StringBuffer("Exception : ").append(e).append("  num=").append(this.test))));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\playThread.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */