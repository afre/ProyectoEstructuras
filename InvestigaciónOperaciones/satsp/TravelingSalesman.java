/*     */ package satsp;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TravelingSalesman
/*     */   extends JFrame
/*     */ {
/*     */   protected JLabel status;
/*  21 */   public SimulateAnnealing worker = null;
/*     */   
/*  23 */   protected boolean started = false;
/*     */   
/*     */ 
/*  26 */   double[][] customers = { { 0.0D, 12.0D, 10.0D, 2.147483647E9D, 2.147483647E9D, 2.147483647E9D, 12.0D }, { 12.0D, 0.0D, 8.0D, 12.0D, 2.147483647E9D, 2.147483647E9D, 2.147483647E9D }, { 10.0D, 8.0D, 0.0D, 11.0D, 3.0D, 2.147483647E9D, 9.0D }, { 2.147483647E9D, 12.0D, 11.0D, 0.0D, 11.0D, 10.0D, 2.147483647E9D }, { 2.147483647E9D, 2.147483647E9D, 3.0D, 11.0D, 0.0D, 6.0D, 7.0D }, { 2.147483647E9D, 2.147483647E9D, 2.147483647E9D, 10.0D, 6.0D, 0.0D, 9.0D }, { 12.0D, 2.147483647E9D, 9.0D, 2.147483647E9D, 7.0D, 9.0D, 0.0D } };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TravelingSalesman()
/*     */   {
/*  44 */     setSize(300, 300);
/*  45 */     setTitle("Traveling Salesman Problem2");
/*  46 */     getContentPane().setLayout(new BorderLayout());
/*  47 */     start();
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
/*     */   public void start()
/*     */   {
/*  62 */     this.started = true;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  67 */     if (this.worker != null)
/*     */     {
/*  69 */       this.worker = null; }
/*  70 */     TspObjectiveFunction objFuc = new TspObjectiveFunction(this.customers);
/*  71 */     int[] temp = { 0, 1, 2, 3, 4, 5, 6 };
/*  72 */     this.worker = new SimulateAnnealing(this.customers);
/*  73 */     this.worker.setInitSolution(temp);
/*  74 */     ArrayList ddd = new ArrayList();
/*  75 */     while (this.worker.hasNextIteration()) {
/*  76 */       ArrayList tt = this.worker.nextIteration();
/*  77 */       ddd.add(tt);
/*     */     }
/*  79 */     this.worker.clean();
/*  80 */     ddd = this.worker.startIteration();
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
/*     */   public void paint() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setStatus(String status) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 129 */     new TravelingSalesman().show();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\satsp\TravelingSalesman.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */