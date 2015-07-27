/*    */ import java.awt.BorderLayout;
/*    */ import java.io.ObjectInputStream;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JLabel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IORMInfoPanel
/*    */   extends IORTSPActionPanel
/*    */ {
/* 33 */   private JLabel testLab = new JLabel("Now go to the Option menu to select the type of problem to be addressed.");
/* 34 */   private String mothod = "tu";
/*    */   
/*    */ 
/*    */   public void updatePanel() {}
/*    */   
/*    */ 
/*    */   protected void backStep() {}
/*    */   
/*    */   protected void finishProcedure() {}
/*    */   
/*    */   public IORMInfoPanel(IORTSPProcessController c, String mothod)
/*    */   {
/* 46 */     super(c);
/* 47 */     this.mothod = mothod;
/* 48 */     this.testLab.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
/* 49 */     setLayout(new BorderLayout());
/* 50 */     add(this.testLab, "North");
/*    */   }
/*    */   
/*    */   protected void initializeCurrentStepHelpPanel() {
/* 54 */     String str = "";
/*    */     
/* 56 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 57 */     this.help_content_step.setText(str);
/* 58 */     this.help_content_step.revalidate();
/*    */   }
/*    */   
/*    */   protected void initializeCurrentProcedureHelpPanel() {
/* 62 */     String str = "";
/*    */     
/* 64 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 65 */     this.help_content_procedure.setText(str);
/* 66 */     this.help_content_procedure.revalidate();
/*    */   }
/*    */   
/*    */   public void SaveProblem() {}
/*    */   
/*    */   public void LoadFile(ObjectInputStream in) {}
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORMInfoPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */