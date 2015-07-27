/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORSAProcessController$SAData
/*     */   implements Serializable
/*     */ {
/*     */   public int num_server;
/*     */   public int num_class;
/*     */   public boolean is_customer_event;
/*     */   public boolean is_customer_done;
/*     */   public boolean is_server_done;
/* 199 */   public boolean[] customer_being_served = new boolean[2];
/*     */   
/*     */ 
/* 202 */   public int[] class_of_customer_being_served_by_server1 = new int[2];
/* 203 */   public int[] class_of_customer_being_served_by_server2 = new int[2];
/*     */   
/*     */ 
/*     */ 
/* 207 */   public double[] current_time = new double[2];
/* 208 */   public int[] num_customer1_in_queue = new int[2];
/* 209 */   public int[] num_customer2_in_queue = new int[2];
/*     */   
/*     */ 
/*     */ 
/*     */   public double random_time;
/*     */   
/*     */ 
/*     */   public double customer_wait_time;
/*     */   
/*     */ 
/* 219 */   public double[] customer1_arrive_time = new double[2];
/* 220 */   public double[] server1_finish_time = new double[2];
/* 221 */   public double[] customer2_arrive_time = new double[2];
/* 222 */   public double[] server2_finish_time = new double[2];
/*     */   
/*     */ 
/* 225 */   public double[] average_wait_time_exclude_service = new double[2];
/* 226 */   public double[] average_wait_time_include_service = new double[2];
/* 227 */   public double[] average_num_wait_to_begin_service = new double[2];
/* 228 */   public double[] average_num_wait_or_in_service = new double[2];
/*     */   public String arrivalRate;
/*     */   public String serviceRate;
/*     */   public String ns1Num;
/*     */   public String ns2Num;
/*     */   public String lqStr;
/*     */   public String lsStr;
/*     */   public String wqStr;
/*     */   public String wsStr;
/*     */   public int phaseNum;
/*     */   public boolean isShowData;
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORSAProcessController$SAData.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */