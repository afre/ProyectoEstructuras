/*    */ import java.awt.BorderLayout;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.border.EmptyBorder;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IORNLModifyPanel
/*    */   extends IORActionPanel
/*    */ {
/* 38 */   private JPanel mainPanel = new JPanel();
/* 39 */   private IORNLPProcessController controller = null;
/*    */   
/*    */ 
/*    */ 
/*    */   private String comtstr;
/*    */   
/*    */ 
/*    */ 
/*    */   public IORNLModifyPanel(IORNLPProcessController c)
/*    */   {
/* 49 */     super(c);
/* 50 */     this.controller = c;
/* 51 */     add(this.mainPanel);
/* 52 */     this.actionStatus.setVisible(false);
/* 53 */     this.bn_back.setVisible(false);
/* 54 */     this.bn_finish.setVisible(false);
/*    */     
/* 56 */     this.mainPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
/* 57 */     this.mainPanel.setLayout(new BorderLayout());
/*    */     
/* 59 */     this.comtstr = "Instructions for the Modified Simplex Method Procedure\n\nTo solve a quadratic programming problem interactively by the modified simplex \nmethod presented in Sec. 13.7, you will need to do the following(expressed in the \nmatrix notation of Sec. 13.7):\n\n1. Choose \"General Linear Programming\" under the Area menu.\n\n2. Choose \"Enter/Revise a Linear Programming Model\" under the Procedure menu. \n   Then enter a model with a vacuous objective function (all zero coefficients) \n   and with the following constraints: Qx + A'u >= c', Ax <= b, x >= 0, u >= 0 \n   (except denote the ui variables as additional xj variables).\n\n3. Choose \"Set Up for the Simplex Method\" under the Procedure menu. \n   Then introduce surplus variables (corresponding to y), slack variables \n   (corresponding to v), and artificial variables (corresponding to the zi) \n   as needed. (A maximum of six functional constraints and ten variables are \n   allowed at this point.) Choose the two-phase method for dealing with artificial \n   variables.\n\n4. After setting up for Phase 1, next choose \"Solve Interactively \n   by the Modified Simplex Method\" under the Procedure menu. When you finish \n   applying this routine to the Phase 1 problem, you are done. If all zi = 0 in the \n   Phase 1 solution, x (without u, v, zi) is the desired optimal solution for the \n   quadratic programming problem. \n";
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
/* 80 */     MultilineLabel statetext = new MultilineLabel(this.comtstr);
/*    */     
/* 82 */     this.mainPanel.add(statetext);
/* 83 */     revalidate();
/* 84 */     repaint();
/*    */   }
/*    */   
/*    */   protected void finishProcedure() {}
/*    */   
/*    */   protected void backStep() {}
/*    */   
/*    */   public void updatePanel() {}
/*    */   
/*    */   protected void initializeCurrentProcedureHelpPanel() {}
/*    */   
/*    */   protected void initializeCurrentStepHelpPanel() {}
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNLModifyPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */