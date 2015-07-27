/*    */ package gatsp;
/*    */ 
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JLabel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TravelingSalesman
/*    */   extends JFrame
/*    */ {
/* 13 */   public static final int CITY_COUNT = 50;
/*    */   
/* 15 */   public static final int POPULATION_SIZE = 1000;
/*    */   
/* 17 */   public static final double MUTATION_PERCENT = 0.1D;
/*    */   
/* 19 */   protected int matingPopulationSize = 500;
/*    */   
/* 21 */   protected int favoredPopulationSize = this.matingPopulationSize / 2;
/*    */   
/*    */ 
/*    */   protected JLabel status;
/*    */   
/* 26 */   protected int cutLength = 10;
/*    */   
/*    */   protected int generation;
/*    */   
/* 30 */   protected Thread worker = null;
/*    */   
/* 32 */   protected boolean started = false;
/*    */   
/*    */ 
/*    */   protected Chromosome[] chromosomes;
/*    */   
/* 37 */   double[][] customers = { { 0.0D, 12.0D, 10.0D, 2.147483647E9D, 2.147483647E9D, 2.147483647E9D, 12.0D }, { 12.0D, 0.0D, 8.0D, 12.0D, 2.147483647E9D, 2.147483647E9D, 2.147483647E9D }, { 10.0D, 8.0D, 0.0D, 11.0D, 3.0D, 2.147483647E9D, 9.0D }, { 2.147483647E9D, 12.0D, 11.0D, 0.0D, 11.0D, 10.0D, 2.147483647E9D }, { 2.147483647E9D, 2.147483647E9D, 3.0D, 11.0D, 0.0D, 6.0D, 7.0D }, { 2.147483647E9D, 2.147483647E9D, 2.147483647E9D, 10.0D, 6.0D, 0.0D, 9.0D }, { 12.0D, 2.147483647E9D, 9.0D, 2.147483647E9D, 7.0D, 9.0D, 0.0D } };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public TravelingSalesman()
/*    */   {
/* 47 */     setSize(300, 300);
/* 48 */     Population po = new Population(this.customers);
/* 49 */     po.initPopulation();
/*    */     
/* 51 */     po.nextIteration();
/* 52 */     setTitle("Traveling Salesman Problem");
/*    */   }
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
/*    */   public static void main(String[] args)
/*    */   {
/* 67 */     new TravelingSalesman().show();
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\gatsp\TravelingSalesman.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */