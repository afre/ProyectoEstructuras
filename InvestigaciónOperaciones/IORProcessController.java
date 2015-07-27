/*     */ import java.awt.Component;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPanel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class IORProcessController
/*     */ {
/*  29 */   public IORTutorial tutorial = null;
/*  30 */   protected MultilinePanel areaHelpPanel = null;
/*     */   
/*  32 */   protected IORActionPanel currentActionPanel = null;
/*  33 */   protected String procedure = null;
/*  34 */   protected String option = null;
/*     */   
/*     */   public IORProcessController(IORTutorial t) {
/*  37 */     this.tutorial = t;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JPanel getCurrentStepHelpPanel()
/*     */   {
/*  44 */     this.tutorial.closeDemoController();
/*  45 */     if (this.currentActionPanel != null) {
/*  46 */       return this.currentActionPanel.getCurrentStepHelpPanel();
/*     */     }
/*  48 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public JPanel getCurrentProcedureHelpPanel()
/*     */   {
/*  54 */     this.tutorial.closeDemoController();
/*  55 */     if (this.currentActionPanel != null) {
/*  56 */       return this.currentActionPanel.getCurrentProcedureHelpPanel();
/*     */     }
/*  58 */     return null;
/*     */   }
/*     */   
/*     */   protected abstract void initializeAreaHelpPanel();
/*     */   
/*     */   public void showAreaHelp() {
/*  64 */     setHelpEnabled(false);
/*     */     
/*     */ 
/*  67 */     this.tutorial.scrollPane.setViewportView(this.areaHelpPanel);
/*  68 */     this.areaHelpPanel.revalidate();
/*  69 */     this.areaHelpPanel.repaint();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHelpEnabled(boolean b)
/*     */   {
/*  95 */     this.tutorial.help.getItem(0).setEnabled(b);
/*  96 */     this.tutorial.help.getItem(1).setEnabled(b);
/*     */   }
/*     */   
/*     */   public void newFile() {
/* 100 */     this.tutorial.firstpage();
/* 101 */     this.currentActionPanel = null;
/* 102 */     this.tutorial.statusBar.setText("IORProcessController newFile()");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void print() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void printToFile() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void createAreaDependentMenus()
/*     */   {
/* 127 */     createMyMenus();
/* 128 */     setDefaultMenuState();
/*     */   }
/*     */   
/*     */   protected abstract void createMyMenus();
/*     */   
/*     */   protected abstract void setDefaultMenuState();
/*     */   
/*     */   protected abstract void setMenuState(int paramInt);
/*     */   
/*     */   public void setView(Component c) {
/* 138 */     this.tutorial.scrollPane.setViewportView(c);
/*     */   }
/*     */   
/*     */   public void removeAreaDependentMenus() {
/* 142 */     this.tutorial.procedure.removeAll();
/* 143 */     this.tutorial.option.removeAll();
/*     */   }
/*     */   
/*     */   public void setStatusBar() {
/* 147 */     String str = "  A: ".concat(String.valueOf(String.valueOf(getArea())));
/*     */     
/* 149 */     if (this.procedure != null) {
/* 150 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("  P: ".concat(String.valueOf(String.valueOf(this.procedure))))));
/*     */     }
/* 152 */     if (this.option != null) {
/* 153 */       str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf("  O: ".concat(String.valueOf(String.valueOf(this.option))))));
/*     */     }
/* 155 */     this.tutorial.statusBar.setText(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract String getArea();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SaveFile(ObjectOutputStream out) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void LoadFile(ObjectInputStream in)
/*     */   {
/* 173 */     System.out.println("in=".concat(String.valueOf(String.valueOf(in.toString()))));
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORProcessController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */