/*     */ import java.io.Externalizable;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORTSPProcessController$TSPData
/*     */   implements Externalizable
/*     */ {
/*     */   public int method_option;
/*     */   public int max_num_city;
/*     */   public double[][] matrix;
/*     */   public int num_city;
/*     */   public boolean isDrawLine;
/*     */   public int[] initSolution;
/*     */   public WholeNumberField[] tempField;
/*     */   public double initDistance;
/*     */   public int[] saveSolution;
/*     */   public Vector auto_T_Vec;
/*     */   public Vector auto_Solution_Vec;
/*     */   public Vector auto_Distance_Vec;
/*     */   public Vector auto_Probability_Vec;
/*     */   public Vector auto_Accept_Vec;
/*     */   public Vector saveAuto_T_Vec;
/*     */   public Vector saveAuto_Solution_Vec;
/*     */   public Vector saveAuto_Distance_Vec;
/*     */   public Vector saveAuto_Probability_Vec;
/*     */   public Vector saveAuto_Accept_Vec;
/*     */   public Vector saveCurrentTotalLine;
/*     */   public int autoTimes;
/*     */   public int totalLine;
/*     */   public boolean SAisError;
/*     */   public Vector step_Vec;
/*     */   public Vector step_T_Vec;
/*     */   public Vector step_Solution_Vec;
/*     */   public Vector step_Distance_Vec;
/*     */   public Vector step_Probability_Vec;
/*     */   public Vector step_Accept_Vec;
/*     */   public int currentLine;
/*     */   public boolean isFinish_SA;
/*     */   public String[][] SA_Next_PrintData;
/*     */   public Vector SA_Auto_PrintData;
/*     */   public String SA_BestSolutionInfoLab;
/*     */   public Vector GAsolution_Vec;
/*     */   public Vector fitness_Vec;
/*     */   public Vector population_Vec;
/*     */   public Vector popFitness_Vec;
/*     */   public Vector children_Vec;
/*     */   public Vector childFitness_Vec;
/*     */   public String[][] GA_Print_BestSolution_Data;
/*     */   public Vector GA_Print_Pop_Data;
/*     */   public Vector GA_Print_Child_Data;
/*     */   public Vector GA_Print_P_Connection_C_Data;
/*     */   public Vector GA_Print_ChildNum;
/*     */   public int GAtotalTimes;
/*     */   public int GACurrentTime;
/*     */   public Vector GA_Solution_Vec;
/*     */   public Vector GA_Fitness_Vec;
/*     */   public Vector GA_Pop_Vec;
/*     */   public Vector GA_Total_Pop_Vec;
/*     */   public Vector GA_PopFit_Vec;
/*     */   public Vector GA_Total_PopFit_Vec;
/*     */   public Vector GA_Child_Vec;
/*     */   public Vector GA_Total_Child_Vec;
/*     */   public Vector GA_ChildBreak_Vec;
/*     */   public Vector GA_Total_ChildBreak_Vec;
/*     */   public Vector GA_ChildFitn_Vec;
/*     */   public Vector GA_Total_ChildFitn_Vec;
/*     */   public Vector GA_Total_SelectParent;
/*     */   public Vector GA_SelectParent;
/*     */   public int GA_Pop_And_Child_TotalLine;
/*     */   public int GA_Children_TotalLine;
/*     */   public Vector TB_InterSolution_Vec;
/*     */   public Vector TB_InterDistance_Vec;
/*     */   public Vector TB_InterTabuList_Vec;
/*     */   public Vector TB_InterTabuArray_Vec;
/*     */   public Vector TB_comBoxMessageV;
/*     */   public Vector TB_comBoxDistanceV;
/*     */   public Vector TB_currentSolutionV;
/*     */   public Vector TB_moveV;
/*     */   public Vector TB_Auto_SolutionV;
/*     */   public Vector TB_Auto_DistanceV;
/*     */   public Vector TB_Auto_TabuV;
/*     */   public Vector TB_Auto_Total_SolutionV;
/*     */   public Vector TB_Auto_Total_DistanceV;
/*     */   public Vector TB_Auto_Total_TabuV;
/*     */   public int TB_Auto_totalTimes;
/*     */   public int TB_Auto_TotalLine;
/*     */   public int selectDistance;
/*     */   public int TB_Totaltimes;
/*     */   public int TB_currentTime;
/*     */   int[] currentSolution;
/*     */   public Vector TB_Print_TableV;
/*     */   public String[][] TB_Auto_Print_Table;
/*     */   public String isAuto;
/*     */   public double MinDistance;
/*     */   public String bestSolution;
/*     */   Vector bestSolutionNum;
/*     */   private final IORTSPProcessController this$0;
/*     */   
/*     */   IORTSPProcessController$TSPData(IORTSPProcessController this$0)
/*     */   {
/* 471 */     this.this$0 = this$0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 722 */     this.max_num_city = 10;
/* 723 */     this.matrix = new double[][] { { 0.0D, 12.0D, 10.0D, 0.0D, 0.0D, 0.0D, 12.0D }, { 12.0D, 0.0D, 8.0D, 12.0D, 0.0D, 0.0D, 0.0D }, { 10.0D, 8.0D, 0.0D, 11.0D, 3.0D, 0.0D, 9.0D }, { 0.0D, 12.0D, 11.0D, 0.0D, 11.0D, 10.0D, 0.0D }, { 0.0D, 0.0D, 3.0D, 11.0D, 0.0D, 6.0D, 7.0D }, { 0.0D, 0.0D, 0.0D, 10.0D, 6.0D, 0.0D, 9.0D }, { 12.0D, 0.0D, 9.0D, 0.0D, 7.0D, 9.0D, 0.0D } };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 741 */     this.num_city = 7;
/* 742 */     this.isDrawLine = false;
/* 743 */     this.initSolution = new int[] { 1, 2, 3, 4, 5, 6, 7 };
/*     */     
/* 745 */     this.tempField = new WholeNumberField[this.num_city - 1];
/*     */     
/*     */ 
/* 748 */     this.saveSolution = new int[] { 1, 2, 3, 4, 5, 6, 7 };
/*     */     
/*     */ 
/*     */ 
/* 752 */     this.auto_T_Vec = new Vector();
/* 753 */     this.auto_Solution_Vec = new Vector();
/* 754 */     this.auto_Distance_Vec = new Vector();
/* 755 */     this.auto_Probability_Vec = new Vector();
/* 756 */     this.auto_Accept_Vec = new Vector();
/*     */     
/* 758 */     this.saveAuto_T_Vec = new Vector();
/* 759 */     this.saveAuto_Solution_Vec = new Vector();
/* 760 */     this.saveAuto_Distance_Vec = new Vector();
/* 761 */     this.saveAuto_Probability_Vec = new Vector();
/* 762 */     this.saveAuto_Accept_Vec = new Vector();
/* 763 */     this.saveCurrentTotalLine = new Vector();
/* 764 */     this.autoTimes = 0;
/* 765 */     this.totalLine = 0;
/*     */     
/* 767 */     this.SAisError = true;
/*     */     
/* 769 */     this.step_Vec = new Vector();
/* 770 */     this.step_T_Vec = new Vector();
/* 771 */     this.step_Solution_Vec = new Vector();
/* 772 */     this.step_Distance_Vec = new Vector();
/* 773 */     this.step_Probability_Vec = new Vector();
/* 774 */     this.step_Accept_Vec = new Vector();
/*     */     
/* 776 */     this.currentLine = 1;
/*     */     
/* 778 */     this.isFinish_SA = false;
/*     */     
/* 780 */     this.SA_Next_PrintData = new String[][] { { "", "" }, { "", "" } };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 789 */     this.GAsolution_Vec = new Vector();
/* 790 */     this.fitness_Vec = new Vector();
/* 791 */     this.population_Vec = new Vector();
/* 792 */     this.popFitness_Vec = new Vector();
/* 793 */     this.children_Vec = new Vector();
/* 794 */     this.childFitness_Vec = new Vector();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 805 */     this.GA_Solution_Vec = new Vector();
/* 806 */     this.GA_Fitness_Vec = new Vector();
/*     */     
/* 808 */     this.GA_Pop_Vec = new Vector();
/* 809 */     this.GA_Total_Pop_Vec = new Vector();
/* 810 */     this.GA_PopFit_Vec = new Vector();
/* 811 */     this.GA_Total_PopFit_Vec = new Vector();
/*     */     
/* 813 */     this.GA_Child_Vec = new Vector();
/* 814 */     this.GA_Total_Child_Vec = new Vector();
/* 815 */     this.GA_ChildBreak_Vec = new Vector();
/* 816 */     this.GA_Total_ChildBreak_Vec = new Vector();
/* 817 */     this.GA_ChildFitn_Vec = new Vector();
/* 818 */     this.GA_Total_ChildFitn_Vec = new Vector();
/* 819 */     this.GA_Total_SelectParent = new Vector();
/* 820 */     this.GA_SelectParent = new Vector();
/*     */     
/* 822 */     this.GA_Pop_And_Child_TotalLine = 10;
/* 823 */     this.GA_Children_TotalLine = 6;
/*     */     
/*     */ 
/* 826 */     this.TB_InterSolution_Vec = new Vector();
/* 827 */     this.TB_InterDistance_Vec = new Vector();
/* 828 */     this.TB_InterTabuList_Vec = new Vector();
/* 829 */     this.TB_InterTabuArray_Vec = new Vector();
/*     */     
/* 831 */     this.TB_comBoxMessageV = new Vector();
/* 832 */     this.TB_comBoxDistanceV = new Vector();
/* 833 */     this.TB_currentSolutionV = new Vector();
/* 834 */     this.TB_moveV = new Vector();
/*     */     
/* 836 */     this.TB_Auto_SolutionV = new Vector();
/* 837 */     this.TB_Auto_DistanceV = new Vector();
/* 838 */     this.TB_Auto_TabuV = new Vector();
/*     */     
/* 840 */     this.TB_Auto_Total_SolutionV = new Vector();
/* 841 */     this.TB_Auto_Total_DistanceV = new Vector();
/* 842 */     this.TB_Auto_Total_TabuV = new Vector();
/* 843 */     this.TB_Auto_totalTimes = 0;
/*     */     
/* 845 */     this.TB_Auto_TotalLine = 0;
/*     */     
/* 847 */     this.selectDistance = 0;
/* 848 */     this.TB_Totaltimes = 0;
/* 849 */     this.TB_currentTime = 0;
/* 850 */     this.currentSolution = new int[] { 1, 2, 3, 4, 5, 6, 7 };
/*     */     
/*     */ 
/* 853 */     this.TB_Print_TableV = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 863 */     this.isAuto = "true";this.TB_InterTabuList_Vec.addElement("");
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput stream)
/*     */   {
/*     */     try {
/* 478 */       stream.writeObject(this.matrix);
/* 479 */       stream.writeInt(this.num_city);
/* 480 */       stream.writeObject(this.initSolution);
/* 481 */       stream.writeObject(this.saveSolution);
/* 482 */       stream.writeBoolean(this.isDrawLine);
/* 483 */       stream.writeDouble(this.initDistance);
/* 484 */       if ((this.this$0.currentActionPanel instanceof IORSimulateAnealingPanel))
/*     */       {
/* 486 */         stream.writeObject(this.auto_T_Vec);
/* 487 */         stream.writeObject(this.auto_Solution_Vec);
/* 488 */         stream.writeObject(this.auto_Distance_Vec);
/* 489 */         stream.writeObject(this.auto_Probability_Vec);
/* 490 */         stream.writeObject(this.auto_Accept_Vec);
/*     */         
/* 492 */         stream.writeObject(this.saveAuto_T_Vec);
/* 493 */         stream.writeObject(this.saveAuto_Solution_Vec);
/* 494 */         stream.writeObject(this.saveAuto_Distance_Vec);
/* 495 */         stream.writeObject(this.saveAuto_Probability_Vec);
/* 496 */         stream.writeObject(this.saveAuto_Accept_Vec);
/* 497 */         stream.writeObject(this.saveCurrentTotalLine);
/*     */         
/* 499 */         stream.writeInt(this.autoTimes);
/* 500 */         stream.writeInt(this.totalLine);
/*     */         
/* 502 */         stream.writeBoolean(this.SAisError);
/*     */         
/* 504 */         stream.writeObject(this.step_Vec);
/* 505 */         stream.writeObject(this.step_T_Vec);
/* 506 */         stream.writeObject(this.step_Solution_Vec);
/* 507 */         stream.writeObject(this.step_Distance_Vec);
/* 508 */         stream.writeObject(this.step_Probability_Vec);
/* 509 */         stream.writeObject(this.step_Accept_Vec);
/*     */         
/* 511 */         stream.writeInt(this.currentLine);
/* 512 */         stream.writeBoolean(this.isFinish_SA);
/*     */         
/* 514 */         stream.writeObject(this.SA_Next_PrintData);
/* 515 */         stream.writeObject(this.SA_Auto_PrintData);
/* 516 */         stream.writeUTF(this.SA_BestSolutionInfoLab);
/* 517 */         stream.writeObject(this.bestSolutionNum);
/*     */ 
/*     */       }
/* 520 */       else if ((this.this$0.currentActionPanel instanceof IORTSPTabuSearchPanel))
/*     */       {
/* 522 */         stream.writeObject(this.TB_InterSolution_Vec);
/* 523 */         stream.writeObject(this.TB_InterDistance_Vec);
/* 524 */         stream.writeObject(this.TB_InterTabuList_Vec);
/* 525 */         stream.writeObject(this.TB_InterTabuArray_Vec);
/* 526 */         stream.writeObject(this.TB_comBoxMessageV);
/* 527 */         stream.writeObject(this.TB_comBoxDistanceV);
/* 528 */         stream.writeObject(this.TB_currentSolutionV);
/* 529 */         stream.writeObject(this.TB_moveV);
/* 530 */         stream.writeObject(this.TB_Auto_SolutionV);
/* 531 */         stream.writeObject(this.TB_Auto_DistanceV);
/* 532 */         stream.writeObject(this.TB_Auto_TabuV);
/* 533 */         stream.writeObject(this.TB_Auto_Total_SolutionV);
/* 534 */         stream.writeObject(this.TB_Auto_Total_DistanceV);
/* 535 */         stream.writeObject(this.TB_Auto_Total_TabuV);
/* 536 */         stream.writeInt(this.TB_Auto_totalTimes);
/* 537 */         stream.writeInt(this.TB_Auto_TotalLine);
/* 538 */         stream.writeInt(this.selectDistance);
/* 539 */         stream.writeInt(this.TB_Totaltimes);
/* 540 */         stream.writeInt(this.TB_currentTime);
/* 541 */         stream.writeObject(this.currentSolution);
/* 542 */         stream.writeObject(this.TB_Print_TableV);
/* 543 */         stream.writeObject(this.TB_Auto_Print_Table);
/* 544 */         stream.writeObject(this.bestSolutionNum);
/*     */       }
/* 546 */       else if ((this.this$0.currentActionPanel instanceof IORTSPGeneticAlgorithmPanel))
/*     */       {
/* 548 */         stream.writeObject(this.GAsolution_Vec);
/* 549 */         stream.writeObject(this.fitness_Vec);
/* 550 */         stream.writeObject(this.population_Vec);
/* 551 */         stream.writeObject(this.popFitness_Vec);
/* 552 */         stream.writeObject(this.children_Vec);
/* 553 */         stream.writeObject(this.childFitness_Vec);
/*     */         
/* 555 */         stream.writeObject(this.GA_Print_BestSolution_Data);
/* 556 */         stream.writeObject(this.GA_Print_Pop_Data);
/* 557 */         stream.writeObject(this.GA_Print_Child_Data);
/* 558 */         stream.writeObject(this.GA_Print_P_Connection_C_Data);
/* 559 */         stream.writeObject(this.GA_Print_ChildNum);
/* 560 */         stream.writeInt(this.GAtotalTimes);
/* 561 */         stream.writeInt(this.GACurrentTime);
/* 562 */         stream.writeObject(this.GA_Solution_Vec);
/* 563 */         stream.writeObject(this.GA_Fitness_Vec);
/* 564 */         stream.writeObject(this.GA_Pop_Vec);
/* 565 */         stream.writeObject(this.GA_Total_Pop_Vec);
/* 566 */         stream.writeObject(this.GA_PopFit_Vec);
/* 567 */         stream.writeObject(this.GA_Total_PopFit_Vec);
/* 568 */         stream.writeObject(this.GA_Child_Vec);
/* 569 */         stream.writeObject(this.GA_Total_Child_Vec);
/* 570 */         stream.writeObject(this.GA_ChildBreak_Vec);
/* 571 */         stream.writeObject(this.GA_Total_ChildBreak_Vec);
/* 572 */         stream.writeObject(this.GA_ChildFitn_Vec);
/* 573 */         stream.writeObject(this.GA_Total_ChildFitn_Vec);
/* 574 */         stream.writeObject(this.GA_Total_SelectParent);
/* 575 */         stream.writeObject(this.GA_SelectParent);
/* 576 */         stream.writeInt(this.GA_Pop_And_Child_TotalLine);
/* 577 */         stream.writeInt(this.GA_Children_TotalLine);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 582 */         System.out.println("Panel Null Save file fails");
/* 583 */         return;
/*     */       }
/* 585 */       stream.writeUTF(this.isAuto);
/* 586 */       stream.writeDouble(this.MinDistance);
/* 587 */       stream.writeUTF(this.bestSolution);
/*     */     }
/*     */     catch (Exception localException) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readExternal(ObjectInput stream)
/*     */   {
/*     */     try
/*     */     {
/* 599 */       this.matrix = ((double[][])stream.readObject());
/* 600 */       this.num_city = stream.readInt();
/* 601 */       this.initSolution = ((int[])stream.readObject());
/* 602 */       this.saveSolution = ((int[])stream.readObject());
/* 603 */       this.isDrawLine = stream.readBoolean();
/* 604 */       this.initDistance = stream.readDouble();
/*     */       
/* 606 */       if (IORTSPProcessController.access$2(this.this$0).equalsIgnoreCase("IORSimulateAnealingPanel"))
/*     */       {
/* 608 */         this.auto_T_Vec = ((Vector)stream.readObject());
/* 609 */         this.auto_Solution_Vec = ((Vector)stream.readObject());
/* 610 */         this.auto_Distance_Vec = ((Vector)stream.readObject());
/* 611 */         this.auto_Probability_Vec = ((Vector)stream.readObject());
/* 612 */         this.auto_Accept_Vec = ((Vector)stream.readObject());
/*     */         
/* 614 */         this.saveAuto_T_Vec = ((Vector)stream.readObject());
/* 615 */         this.saveAuto_Solution_Vec = ((Vector)stream.readObject());
/* 616 */         this.saveAuto_Distance_Vec = ((Vector)stream.readObject());
/* 617 */         this.saveAuto_Probability_Vec = ((Vector)stream.readObject());
/* 618 */         this.saveAuto_Accept_Vec = ((Vector)stream.readObject());
/* 619 */         this.saveCurrentTotalLine = ((Vector)stream.readObject());
/*     */         
/* 621 */         this.autoTimes = stream.readInt();
/* 622 */         this.totalLine = stream.readInt();
/* 623 */         this.SAisError = stream.readBoolean();
/*     */         
/* 625 */         this.step_Vec = ((Vector)stream.readObject());
/* 626 */         this.step_T_Vec = ((Vector)stream.readObject());
/* 627 */         this.step_Solution_Vec = ((Vector)stream.readObject());
/* 628 */         this.step_Distance_Vec = ((Vector)stream.readObject());
/* 629 */         this.step_Probability_Vec = ((Vector)stream.readObject());
/* 630 */         this.step_Accept_Vec = ((Vector)stream.readObject());
/*     */         
/* 632 */         this.currentLine = stream.readInt();
/* 633 */         this.isFinish_SA = stream.readBoolean();
/*     */         
/* 635 */         this.SA_Next_PrintData = ((String[][])stream.readObject());
/* 636 */         this.SA_Auto_PrintData = ((Vector)stream.readObject());
/* 637 */         this.SA_BestSolutionInfoLab = stream.readUTF();
/*     */         
/* 639 */         this.bestSolutionNum = ((Vector)stream.readObject());
/*     */ 
/*     */       }
/* 642 */       else if (IORTSPProcessController.access$2(this.this$0).equalsIgnoreCase("IORTSPTabuSearchPanel")) {
/* 643 */         this.TB_InterSolution_Vec = ((Vector)stream.readObject());
/* 644 */         this.TB_InterDistance_Vec = ((Vector)stream.readObject());
/* 645 */         this.TB_InterTabuList_Vec = ((Vector)stream.readObject());
/* 646 */         this.TB_InterTabuArray_Vec = ((Vector)stream.readObject());
/* 647 */         this.TB_comBoxMessageV = ((Vector)stream.readObject());
/* 648 */         this.TB_comBoxDistanceV = ((Vector)stream.readObject());
/* 649 */         this.TB_currentSolutionV = ((Vector)stream.readObject());
/* 650 */         this.TB_moveV = ((Vector)stream.readObject());
/* 651 */         this.TB_Auto_SolutionV = ((Vector)stream.readObject());
/* 652 */         this.TB_Auto_DistanceV = ((Vector)stream.readObject());
/* 653 */         this.TB_Auto_TabuV = ((Vector)stream.readObject());
/* 654 */         this.TB_Auto_Total_SolutionV = ((Vector)stream.readObject());
/* 655 */         this.TB_Auto_Total_DistanceV = ((Vector)stream.readObject());
/* 656 */         this.TB_Auto_Total_TabuV = ((Vector)stream.readObject());
/* 657 */         this.TB_Auto_totalTimes = stream.readInt();
/* 658 */         this.TB_Auto_TotalLine = stream.readInt();
/* 659 */         this.selectDistance = stream.readInt();
/* 660 */         this.TB_Totaltimes = stream.readInt();
/* 661 */         this.TB_currentTime = stream.readInt();
/* 662 */         this.currentSolution = ((int[])stream.readObject());
/* 663 */         this.TB_Print_TableV = ((Vector)stream.readObject());
/* 664 */         this.TB_Auto_Print_Table = ((String[][])stream.readObject());
/*     */         
/* 666 */         this.bestSolutionNum = ((Vector)stream.readObject());
/*     */ 
/*     */       }
/* 669 */       else if (IORTSPProcessController.access$2(this.this$0).equalsIgnoreCase("IORTSPGeneticAlgorithmPanel"))
/*     */       {
/* 671 */         this.GAsolution_Vec = ((Vector)stream.readObject());
/* 672 */         this.fitness_Vec = ((Vector)stream.readObject());
/* 673 */         this.population_Vec = ((Vector)stream.readObject());
/* 674 */         this.popFitness_Vec = ((Vector)stream.readObject());
/* 675 */         this.children_Vec = ((Vector)stream.readObject());
/* 676 */         this.childFitness_Vec = ((Vector)stream.readObject());
/*     */         
/* 678 */         this.GA_Print_BestSolution_Data = ((String[][])stream.readObject());
/*     */         
/* 680 */         this.GA_Print_Pop_Data = ((Vector)stream.readObject());
/* 681 */         this.GA_Print_Child_Data = ((Vector)stream.readObject());
/* 682 */         this.GA_Print_P_Connection_C_Data = ((Vector)stream.readObject());
/*     */         
/* 684 */         this.GA_Print_ChildNum = ((Vector)stream.readObject());
/* 685 */         this.GAtotalTimes = stream.readInt();
/* 686 */         this.GACurrentTime = stream.readInt();
/*     */         
/* 688 */         this.GA_Solution_Vec = ((Vector)stream.readObject());
/* 689 */         this.GA_Fitness_Vec = ((Vector)stream.readObject());
/* 690 */         this.GA_Pop_Vec = ((Vector)stream.readObject());
/* 691 */         this.GA_Total_Pop_Vec = ((Vector)stream.readObject());
/* 692 */         this.GA_PopFit_Vec = ((Vector)stream.readObject());
/* 693 */         this.GA_Total_PopFit_Vec = ((Vector)stream.readObject());
/* 694 */         this.GA_Child_Vec = ((Vector)stream.readObject());
/* 695 */         this.GA_Total_Child_Vec = ((Vector)stream.readObject());
/* 696 */         this.GA_ChildBreak_Vec = ((Vector)stream.readObject());
/* 697 */         this.GA_Total_ChildBreak_Vec = ((Vector)stream.readObject());
/* 698 */         this.GA_ChildFitn_Vec = ((Vector)stream.readObject());
/* 699 */         this.GA_Total_ChildFitn_Vec = ((Vector)stream.readObject());
/* 700 */         this.GA_Total_SelectParent = ((Vector)stream.readObject());
/* 701 */         this.GA_SelectParent = ((Vector)stream.readObject());
/* 702 */         this.GA_Pop_And_Child_TotalLine = stream.readInt();
/* 703 */         this.GA_Children_TotalLine = stream.readInt();
/*     */       }
/*     */       else
/*     */       {
/* 707 */         return;
/*     */       }
/*     */       
/* 710 */       this.isAuto = stream.readUTF();
/* 711 */       this.MinDistance = stream.readDouble();
/* 712 */       this.bestSolution = stream.readUTF();
/*     */     }
/*     */     catch (Exception localException) {}
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTSPProcessController$TSPData.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */