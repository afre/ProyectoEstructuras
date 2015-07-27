/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Dimension;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Vector;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextArea;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.table.AbstractTableModel;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IORSASolvePanel
/*      */   extends IORSAActionPanel
/*      */ {
/*   33 */   private static final int MODE_1CLASS_1SERVER = 1;
/*   34 */   private static final int MODE_2CLASS_1SERVER = 2;
/*   35 */   private static final int MODE_1CLASS_2SERVER = 3;
/*   36 */   private static final int MODE_2CLASS_2SERVER = 4;
/*      */   
/*      */ 
/*   39 */   private static final int STEP_GENERATE_RANDOM_TIME = 1;
/*   40 */   private static final int STEP_SELECT_EVENT = 2;
/*   41 */   private static final int STEP_CUSTOMER_ACTION = 3;
/*   42 */   private static final int STEP_SERVER_ACTION = 4;
/*      */   
/*      */ 
/*   45 */   private IORSASolvePanel.SATableModel tpModel = null;
/*   46 */   private JTable table = null;
/*   47 */   private JScrollPane scrlPane = null;
/*      */   
/*   49 */   private IORSASolvePanel.ControlPanel randomPanel = null;
/*   50 */   private IORSASolvePanel.ControlPanel eventPanel = null;
/*   51 */   private IORSASolvePanel.ControlPanel customerPanel = null;
/*   52 */   private IORSASolvePanel.ControlPanel serverPanel = null;
/*   53 */   private IORSASolvePanel.ControlPanel currentControlPanel = null;
/*      */   
/*   55 */   private JPanel mainPanel = null;
/*      */   
/*      */ 
/*      */   private int mode;
/*      */   
/*   60 */   public boolean is_result = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IORSASolvePanel(IORSAProcessController c)
/*      */   {
/*   69 */     super(c);
/*   70 */     this.state = new IORState(this.controller.solver);
/*      */     
/*   72 */     this.controller.solver.getData(this.controller.data);
/*   73 */     setMode();
/*      */     
/*   75 */     this.tpModel = new IORSASolvePanel.SATableModel(null);
/*   76 */     this.table = new JTable(this.tpModel);
/*      */     
/*   78 */     this.scrlPane = new JScrollPane(this.table);
/*   79 */     this.scrlPane.setPreferredSize(new Dimension(180, 120));
/*   80 */     this.scrlPane.setMaximumSize(new Dimension(210, 150));
/*   81 */     this.scrlPane.setAlignmentX(0.5F);
/*   82 */     this.scrlPane.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
/*      */     
/*   84 */     generateControlPanels();
/*      */     
/*   86 */     JPanel p = new JPanel(new BorderLayout());
/*   87 */     JPanel p1 = new JPanel();
/*   88 */     p1.add(new JLabel("Refer to the Help menu and choose Specific Help for the table column notations."));
/*      */     
/*   90 */     p.add(this.scrlPane, "North");
/*   91 */     p.add(p1, "South");
/*      */     
/*   93 */     this.mainPanel = new JPanel(new BorderLayout());
/*   94 */     this.mainPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
/*   95 */     this.mainPanel.add(p, "North");
/*   96 */     setCurrentControlPanel(this.randomPanel);
/*      */     
/*   98 */     add(this.mainPanel, "Center");
/*      */     
/*  100 */     setStep(1);
/*  101 */     updatePanel();
/*      */   }
/*      */   
/*      */ 
/*      */   private void generateControlPanels()
/*      */   {
/*  107 */     generateRandomPanel();
/*  108 */     generateEventPanel();
/*  109 */     generateCustomerPanel();
/*  110 */     generateServerPanel();
/*      */   }
/*      */   
/*      */   private void setCurrentControlPanel(IORSASolvePanel.ControlPanel cp) {
/*  114 */     if (this.currentControlPanel == cp) {
/*  115 */       return;
/*      */     }
/*  117 */     if (this.currentControlPanel != null) {
/*  118 */       this.mainPanel.remove(this.currentControlPanel);
/*      */     }
/*  120 */     if (cp != null) {
/*  121 */       this.mainPanel.add(cp, "South");
/*      */     }
/*      */     
/*  124 */     this.currentControlPanel = cp;
/*      */   }
/*      */   
/*  127 */   private static final String random_promt = "For any interarrival and service times that need to be randomly generated, \nselect the corresponding action, and press the OK button.";
/*      */   
/*  129 */   private static final String[] random_ops_1c1s = { "Randomly generate an interarrival time", "Randomly generate a service time" };
/*      */   
/*      */ 
/*  132 */   private static final String[] random_ops_2c1s = { "Randomly generate an interarrival time in priority Class 1", "Randomly generate an interarrival time in priority Class 2", "Randomly generate a service time" };
/*      */   
/*      */ 
/*      */ 
/*  136 */   private static final String[] random_ops_1c2s = { "Randomly generate an interarrival time", "Randomly generate a service time for Server 1", "Randomly generate a service time for Server 2" };
/*      */   
/*      */ 
/*      */ 
/*  140 */   private static final String[] random_ops_2c2s = { "Randomly generate an interarrival time for priority Class 1", "Randomly generate an interarrival time for priority Class 2", "Randomly generate a service time for Server 1", "Randomly generate a service time for Server 2" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  145 */   private static final String random_result = "Generating random times is finished. Press the NEXT button to advance the current time.";
/*      */   
/*      */   private void generateRandomPanel()
/*      */   {
/*  149 */     switch (this.mode) {
/*      */     case 1: 
/*  151 */       this.randomPanel = new IORSASolvePanel.ControlPanel("For any interarrival and service times that need to be randomly generated, \nselect the corresponding action, and press the OK button.", random_ops_1c1s, "Generating random times is finished. Press the NEXT button to advance the current time.", null);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  166 */       break;
/*      */     case 2: 
/*  154 */       this.randomPanel = new IORSASolvePanel.ControlPanel("For any interarrival and service times that need to be randomly generated, \nselect the corresponding action, and press the OK button.", random_ops_2c1s, "Generating random times is finished. Press the NEXT button to advance the current time.", null);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  166 */       break;
/*      */     case 3: 
/*  157 */       this.randomPanel = new IORSASolvePanel.ControlPanel("For any interarrival and service times that need to be randomly generated, \nselect the corresponding action, and press the OK button.", random_ops_1c2s, "Generating random times is finished. Press the NEXT button to advance the current time.", null);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  166 */       break;
/*      */     case 4: 
/*  160 */       this.randomPanel = new IORSASolvePanel.ControlPanel("For any interarrival and service times that need to be randomly generated, \nselect the corresponding action, and press the OK button.", random_ops_2c2s, "Generating random times is finished. Press the NEXT button to advance the current time.", null);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  166 */       break;
/*      */     default: 
/*  163 */       System.err.println("ERROR IN GENERATING THE RANDOM PANEL");
/*      */     }
/*      */     
/*      */   }
/*      */   
/*  168 */   private static final String event_promt = "The current time is ready to be advanced to the time of the next event. Identify which event is the next event \nby selecting from the following events and then pressing the OK button.";
/*      */   
/*  170 */   private static final String[] event_ops_1c1s = { "The next event is an arrival of a customer", "The next event is a service completion" };
/*      */   
/*      */ 
/*  173 */   private static final String[] event_ops_2c1s = { "The next event is an arrival of a customer in priority Class 1", "The next event is an arrival of a customer in priority Class 2", "The next event is a service completion" };
/*      */   
/*      */ 
/*      */ 
/*  177 */   private static final String[] event_ops_1c2s = { "The next event is an arrival of a customer", "The next event is a service completion for Server 1", "The next event is a service completion for Server 2" };
/*      */   
/*      */ 
/*      */ 
/*  181 */   private static final String[] event_ops_2c2s = { "The next event is an arrival of a customer in priority Class 1", "The next event is an arrival of a customer in priority Class 2", "The next event is a service completion for Server 1", "The next event is a service completion for Server 2" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void generateEventPanel()
/*      */   {
/*  189 */     switch (this.mode) {
/*      */     case 1: 
/*  191 */       this.eventPanel = new IORSASolvePanel.ControlPanel("The current time is ready to be advanced to the time of the next event. Identify which event is the next event \nby selecting from the following events and then pressing the OK button.", event_ops_1c1s, null, null);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  206 */       break;
/*      */     case 2: 
/*  194 */       this.eventPanel = new IORSASolvePanel.ControlPanel("The current time is ready to be advanced to the time of the next event. Identify which event is the next event \nby selecting from the following events and then pressing the OK button.", event_ops_2c1s, null, null);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  206 */       break;
/*      */     case 3: 
/*  197 */       this.eventPanel = new IORSASolvePanel.ControlPanel("The current time is ready to be advanced to the time of the next event. Identify which event is the next event \nby selecting from the following events and then pressing the OK button.", event_ops_1c2s, null, null);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  206 */       break;
/*      */     case 4: 
/*  200 */       this.eventPanel = new IORSASolvePanel.ControlPanel("The current time is ready to be advanced to the time of the next event. Identify which event is the next event \nby selecting from the following events and then pressing the OK button.", event_ops_2c2s, null, null);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  206 */       break;
/*      */     default: 
/*  203 */       System.err.println("ERROR IN GENERATING THE EVENT PANEL");
/*      */     }
/*      */     
/*      */   }
/*      */   
/*  208 */   private static final String customer_promt = "A customer has just arrived to the system. Specify what happens to this customer \nby selecting from the following events and then pressing the OK button.";
/*      */   
/*  210 */   private static final String[] customer_ops_1c1s = { "Customer joins queue", "Customer starts service" };
/*      */   
/*      */ 
/*  213 */   private static final String[] customer_ops_2c1s = { "Customer joins queue", "Customer starts service" };
/*      */   
/*      */ 
/*  216 */   private static final String[] customer_ops_1c2s = { "Customer joins queue", "Customer starts service at server 1", "Customer starts service at server 2" };
/*      */   
/*      */ 
/*      */ 
/*  220 */   private static final String[] customer_ops_2c2s = { "Customer joins queue", "Customer starts service at server 1", "Customer starts service at server 2" };
/*      */   
/*      */ 
/*      */ 
/*  224 */   private static final String customer_result = "Press the NEXT button to continue.";
/*      */   
/*      */   private void generateCustomerPanel()
/*      */   {
/*  228 */     switch (this.mode) {
/*      */     case 1: 
/*  230 */       this.customerPanel = new IORSASolvePanel.ControlPanel("A customer has just arrived to the system. Specify what happens to this customer \nby selecting from the following events and then pressing the OK button.", customer_ops_1c1s, "Press the NEXT button to continue.", null);
/*  231 */       break;
/*      */     case 2: 
/*  233 */       this.customerPanel = new IORSASolvePanel.ControlPanel("A customer has just arrived to the system. Specify what happens to this customer \nby selecting from the following events and then pressing the OK button.", customer_ops_2c1s, "Press the NEXT button to continue.", null);
/*  234 */       break;
/*      */     case 3: 
/*  236 */       this.customerPanel = new IORSASolvePanel.ControlPanel("A customer has just arrived to the system. Specify what happens to this customer \nby selecting from the following events and then pressing the OK button.", customer_ops_1c2s, "Press the NEXT button to continue.", null);
/*  237 */       break;
/*      */     case 4: 
/*  239 */       this.customerPanel = new IORSASolvePanel.ControlPanel("A customer has just arrived to the system. Specify what happens to this customer \nby selecting from the following events and then pressing the OK button.", customer_ops_2c2s, "Press the NEXT button to continue.", null);
/*  240 */       break;
/*      */     default: 
/*  242 */       System.err.println("ERROR IN GENERATING THE EVENT PANEL");
/*      */     }
/*      */     
/*  245 */     this.customerPanel.setResultVisible(false);
/*      */   }
/*      */   
/*  248 */   private static final String server_promt = "The server has just completed a service. Specify what the server does next \nby selecting from the following events and then pressing the OK button.";
/*      */   
/*  250 */   private static final String[] server_ops_1c1s = { "Server remains idle for now", "Server starts serving a customer" };
/*      */   
/*      */ 
/*  253 */   private static final String[] server_ops_2c1s = { "Server remains idle for now", "Server starts serving a customer from priority class 1", "Server starts serving a customer from priority class 2" };
/*      */   
/*      */ 
/*      */ 
/*  257 */   private static final String[] server_ops_1c2s = { "Server remains idle for now", "Server starts serving a customer" };
/*      */   
/*      */ 
/*  260 */   private static final String[] server_ops_2c2s = { "Server remains idle for now", "Server starts serving a customer from priority class 1", "Server starts serving a customer from priority class 2" };
/*      */   
/*      */ 
/*      */ 
/*  264 */   private static final String server_result = "Press the NEXT button to continue.";
/*      */   
/*      */   private void generateServerPanel()
/*      */   {
/*  268 */     switch (this.mode) {
/*      */     case 1: 
/*  270 */       this.serverPanel = new IORSASolvePanel.ControlPanel("The server has just completed a service. Specify what the server does next \nby selecting from the following events and then pressing the OK button.", server_ops_1c1s, "Press the NEXT button to continue.", null);
/*  271 */       break;
/*      */     case 2: 
/*  273 */       this.serverPanel = new IORSASolvePanel.ControlPanel("The server has just completed a service. Specify what the server does next \nby selecting from the following events and then pressing the OK button.", server_ops_2c1s, "Press the NEXT button to continue.", null);
/*  274 */       break;
/*      */     case 3: 
/*  276 */       this.serverPanel = new IORSASolvePanel.ControlPanel("The server has just completed a service. Specify what the server does next \nby selecting from the following events and then pressing the OK button.", server_ops_1c2s, "Press the NEXT button to continue.", null);
/*  277 */       break;
/*      */     case 4: 
/*  279 */       this.serverPanel = new IORSASolvePanel.ControlPanel("The server has just completed a service. Specify what the server does next \nby selecting from the following events and then pressing the OK button.", server_ops_2c2s, "Press the NEXT button to continue.", null);
/*  280 */       break;
/*      */     default: 
/*  282 */       System.err.println("ERROR IN GENERATING THE EVENT PANEL");
/*      */     }
/*      */     
/*  285 */     this.serverPanel.setResultVisible(false);
/*      */   }
/*      */   
/*      */   private void setMode()
/*      */   {
/*  290 */     this.mode = -1;
/*  291 */     if ((this.controller.data.num_class == 1) && (this.controller.data.num_server == 1))
/*  292 */       this.mode = 1;
/*  293 */     if ((this.controller.data.num_class == 2) && (this.controller.data.num_server == 1))
/*  294 */       this.mode = 2;
/*  295 */     if ((this.controller.data.num_class == 1) && (this.controller.data.num_server == 2))
/*  296 */       this.mode = 3;
/*  297 */     if ((this.controller.data.num_class == 2) && (this.controller.data.num_server == 2)) {
/*  298 */       this.mode = 4;
/*      */     }
/*  300 */     if (this.mode == -1) {
/*  301 */       System.err.println("ERROR IN DECIDING mode");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected void backStep()
/*      */   {
/*  308 */     if (this.state != null) {
/*  309 */       this.controller.solver.reset();
/*  310 */       this.state.back();
/*      */       
/*  312 */       this.step = ((Integer)this.operation_sequence.lastElement()).intValue();
/*  313 */       int len = this.operation_sequence.size();
/*  314 */       this.operation_sequence.removeElementAt(len - 1);
/*      */       
/*  316 */       switch (this.step) {
/*      */       case 1: 
/*  318 */         setCurrentControlPanel(this.randomPanel);
/*  319 */         break;
/*      */       case 2: 
/*  321 */         setCurrentControlPanel(this.eventPanel);
/*  322 */         break;
/*      */       case 3: 
/*  324 */         setCurrentControlPanel(this.customerPanel);
/*  325 */         this.customerPanel.setResultVisible(false);
/*  326 */         break;
/*      */       case 4: 
/*  328 */         setCurrentControlPanel(this.serverPanel);
/*  329 */         this.serverPanel.setResultVisible(false);
/*  330 */         break;
/*      */       default: 
/*  332 */         System.err.println("ERROR IN BACK STEP");
/*      */       }
/*      */       
/*  335 */       updatePanel();
/*  336 */       revalidate();
/*  337 */       resetActionStatus();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void resetActionStatus()
/*      */   {
/*  345 */     this.actionStatus.setText(" ");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updatePanel()
/*      */   {
/*  353 */     this.controller.solver.getData(this.controller.data);
/*      */     
/*  355 */     if (this.state.isEmpty()) {
/*  356 */       this.bn_back.setEnabled(false);
/*      */     } else {
/*  358 */       this.bn_back.setEnabled(true);
/*      */     }
/*  360 */     revalidate();
/*  361 */     repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doOK()
/*      */   {
/*  369 */     Vector p = new Vector();
/*      */     
/*      */ 
/*  372 */     switch (getStep())
/*      */     {
/*      */     case 1: 
/*  375 */       p.addElement(new Boolean(true));
/*      */       
/*  377 */       int i = this.randomPanel.getSelectedIndex();
/*  378 */       p.addElement(new Integer(i));
/*  379 */       IOROperation operation = new IOROperation(50201, p);
/*      */       
/*      */ 
/*  382 */       if (this.controller.solver.doWork(operation, this.controller.data)) {
/*  383 */         Vector v = new Vector();
/*      */         
/*      */ 
/*  386 */         v.addElement(new Boolean(false));
/*      */         
/*  388 */         v.addElement(new Double(this.controller.data.random_time));
/*      */         
/*  390 */         v.addElement(new Integer(i));
/*      */         
/*      */ 
/*  393 */         IOROperation operation_back = new IOROperation(50201, v);
/*  394 */         this.state.addStep(operation_back);
/*  395 */         addStep();
/*  396 */         updatePanel();
/*      */       }
/*      */       else {
/*  399 */         JOptionPane.showMessageDialog(this, "Incorrect input. Please try again!");
/*      */       }
/*  401 */       break;
/*      */     
/*      */     case 2: 
/*  404 */       p.addElement(new Integer(this.eventPanel.getSelectedIndex()));
/*  405 */       IOROperation operation = new IOROperation(50203, p);
/*      */       
/*  407 */       if (this.controller.solver.doWork(operation, this.controller.data)) {
/*  408 */         this.state.addStep(operation);
/*  409 */         addStep();
/*  410 */         updatePanel();
/*  411 */         doNext();
/*      */       }
/*      */       else {
/*  414 */         JOptionPane.showMessageDialog(this, "Incorrect input. Please try again!");
/*      */       }
/*  416 */       break;
/*      */     
/*      */     case 3: 
/*  419 */       p.addElement(new Integer(this.customerPanel.getSelectedIndex()));
/*  420 */       IOROperation operation = new IOROperation(50204, p);
/*      */       
/*  422 */       if (this.controller.solver.doWork(operation, this.controller.data)) {
/*  423 */         this.state.addStep(operation);
/*  424 */         addStep();
/*  425 */         updatePanel();
/*  426 */         doNext();
/*      */       }
/*      */       else {
/*  429 */         JOptionPane.showMessageDialog(this, "Incorrect input. Please try again!");
/*      */       }
/*  431 */       break;
/*      */     
/*      */     case 4: 
/*  434 */       p.addElement(new Integer(this.serverPanel.getSelectedIndex()));
/*  435 */       IOROperation operation = new IOROperation(50205, p);
/*      */       
/*  437 */       if (this.controller.solver.doWork(operation, this.controller.data)) {
/*  438 */         this.state.addStep(operation);
/*  439 */         addStep();
/*  440 */         updatePanel();
/*  441 */         doNext();
/*      */       }
/*      */       else {
/*  444 */         JOptionPane.showMessageDialog(this, "Incorrect input. Please try again!");
/*      */       }
/*  446 */       break;
/*      */     default: 
/*  448 */       System.err.println("ERROR IN DO NEXT");
/*  449 */       break;
/*      */     }
/*  451 */     this.mainPanel.revalidate();
/*  452 */     this.mainPanel.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */   private void doNext()
/*      */   {
/*  458 */     switch (getStep())
/*      */     {
/*      */     case 1: 
/*  461 */       Vector p = new Vector();
/*  462 */       IOROperation operation = new IOROperation(50202, p);
/*      */       
/*  464 */       if (this.controller.solver.doWork(operation, this.controller.data)) {
/*  465 */         setStep(2);
/*  466 */         setCurrentControlPanel(this.eventPanel);
/*      */       }
/*      */       else {
/*  469 */         JOptionPane.showMessageDialog(this, "Incorrect input. Please try again!");
/*      */       }
/*      */       
/*  472 */       break;
/*      */     
/*      */     case 2: 
/*  475 */       if (this.controller.data.is_customer_event) {
/*  476 */         setStep(3);
/*  477 */         setCurrentControlPanel(this.customerPanel);
/*      */       }
/*      */       else {
/*  480 */         setStep(4);
/*  481 */         setCurrentControlPanel(this.serverPanel);
/*      */       }
/*  483 */       break;
/*      */     case 3: 
/*  485 */       if (this.controller.data.is_customer_done) {
/*  486 */         this.customerPanel.setResultVisible(true);
/*  487 */         this.controller.data.is_customer_done = false;
/*  488 */         this.actionStatus.setText(String.valueOf(String.valueOf(new StringBuffer("For the customer just entering service, its waiting time has been ").append((float)this.controller.data.customer_wait_time).append(" ."))));
/*      */       }
/*      */       else {
/*  491 */         setStep(1);
/*  492 */         this.customerPanel.setResultVisible(false);
/*  493 */         setCurrentControlPanel(this.randomPanel);
/*  494 */         resetActionStatus();
/*      */       }
/*  496 */       break;
/*      */     case 4: 
/*  498 */       if (this.controller.data.is_server_done) {
/*  499 */         this.serverPanel.setResultVisible(true);
/*  500 */         this.controller.data.is_server_done = false;
/*  501 */         this.actionStatus.setText(String.valueOf(String.valueOf(new StringBuffer("For the customer just entering service, its waiting time has been ").append((float)this.controller.data.customer_wait_time).append(" ."))));
/*      */       }
/*      */       else {
/*  504 */         setStep(1);
/*  505 */         this.serverPanel.setResultVisible(false);
/*  506 */         setCurrentControlPanel(this.randomPanel);
/*  507 */         resetActionStatus();
/*      */       }
/*  509 */       break;
/*      */     default: 
/*  511 */       System.err.println("ERROR IN DO NEXT");
/*  512 */       break;
/*      */     }
/*  514 */     this.mainPanel.revalidate();
/*  515 */     this.mainPanel.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void finishProcedure()
/*      */   {
/*  523 */     Vector v = new Vector();
/*  524 */     IOROperation op = new IOROperation(50302, v);
/*  525 */     if (this.controller.solver.doWork(op, this.controller.data)) {
/*  526 */       IORSASolvePanel.ResultPanel resutPanel = new IORSASolvePanel.ResultPanel(this, null);
/*  527 */       this.controller.setView(resutPanel);
/*  528 */       this.is_result = true;
/*  529 */       resutPanel.revalidate();
/*  530 */       resutPanel.repaint();
/*      */     }
/*      */     else {
/*  533 */       JOptionPane.showMessageDialog(this, "Incorrect input. Please try again!");
/*      */     }
/*      */   }
/*      */   
/*  537 */   private class ResultPanel extends JPanel { ResultPanel(IORSASolvePanel x$1, IORSASolvePanel..4 x$2) { this(x$1); }
/*  538 */     private IORSASolvePanel parent = null;
/*      */     
/*      */     private ResultPanel(IORSASolvePanel p)
/*      */     {
/*  542 */       this.parent = p;
/*      */       
/*  544 */       JTextArea mll = new JTextArea(getContent());
/*  545 */       mll.setBorder(BorderFactory.createEmptyBorder(20, 20, 30, 20));
/*  546 */       mll.setHighlighter(null);
/*  547 */       mll.setEditable(false);
/*  548 */       LookAndFeel.installBorder(mll, "Label.border");
/*  549 */       LookAndFeel.installColorsAndFont(mll, "Label.background", "Label.foreground", "Label.font");
/*      */       
/*  551 */       JButton bn_continue = new JButton("Continue Simulation");
/*  552 */       bn_continue.addActionListener(new IORSASolvePanel.1((ResultPanel)this));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  559 */       JPanel panel = new JPanel();
/*  560 */       panel.add(bn_continue);
/*      */       
/*  562 */       JPanel p2 = new JPanel();
/*  563 */       p2.setLayout(new BoxLayout(p2, 1));
/*  564 */       p2.add(mll);
/*  565 */       p2.add(Box.createRigidArea(new Dimension(15, 15)));
/*  566 */       p2.add(panel);
/*      */       
/*  568 */       setLayout(new BorderLayout());
/*  569 */       add(p2, "North");
/*      */     }
/*      */     
/*      */     private String getContent() {
/*  573 */       String str = "The results from the simulation run are as follows:\n\n\n";
/*  574 */       switch (IORSASolvePanel.this.mode) {
/*      */       case 1: 
/*      */       case 3: 
/*  577 */         str = String.valueOf(String.valueOf(str)).concat("\tAverage waiting time (excluding service)\t");
/*  578 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.average_wait_time_exclude_service[0])).concat("\n"))));
/*  579 */         str = String.valueOf(String.valueOf(str)).concat("\tAverage waiting time (including service)\t");
/*  580 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.average_wait_time_include_service[0])).concat("\n"))));
/*  581 */         str = String.valueOf(String.valueOf(str)).concat("\tAverage number waiting to begin service\t");
/*  582 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.average_num_wait_to_begin_service[0])).concat("\n"))));
/*  583 */         str = String.valueOf(String.valueOf(str)).concat("\tAverage number waiting or in service\t");
/*  584 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.average_num_wait_or_in_service[0])).concat("\n"))));
/*  585 */         break;
/*      */       case 2: 
/*      */       case 4: 
/*  588 */         str = String.valueOf(String.valueOf(str)).concat("Class 1 customers:\n");
/*  589 */         str = String.valueOf(String.valueOf(str)).concat("\tAverage waiting time (excluding service)\t");
/*  590 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.average_wait_time_exclude_service[0])).concat("\n"))));
/*  591 */         str = String.valueOf(String.valueOf(str)).concat("\tAverage waiting time (including service)\t");
/*  592 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.average_wait_time_include_service[0])).concat("\n"))));
/*  593 */         str = String.valueOf(String.valueOf(str)).concat("\tAverage number waiting to begin service\t");
/*  594 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.average_num_wait_to_begin_service[0])).concat("\n"))));
/*  595 */         str = String.valueOf(String.valueOf(str)).concat("\tAverage number waiting or in service\t");
/*  596 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.average_num_wait_or_in_service[0])).concat("\n\n"))));
/*  597 */         str = String.valueOf(String.valueOf(str)).concat("Class 2 customers:\n");
/*  598 */         str = String.valueOf(String.valueOf(str)).concat("\tAverage waiting time (excluding service)\t");
/*  599 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.average_wait_time_exclude_service[1])).concat("\n"))));
/*  600 */         str = String.valueOf(String.valueOf(str)).concat("\tAverage waiting time (including service)\t");
/*  601 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.average_wait_time_include_service[1])).concat("\n"))));
/*  602 */         str = String.valueOf(String.valueOf(str)).concat("\tAverage number waiting to begin service\t");
/*  603 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.average_num_wait_to_begin_service[1])).concat("\n"))));
/*  604 */         str = String.valueOf(String.valueOf(str)).concat("\tAverage number waiting or in service\t");
/*  605 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.average_num_wait_or_in_service[1])).concat("\n"))));
/*  606 */         break;
/*      */       default: 
/*  608 */         System.err.println("ERROR IN GENERATING THE RANDOM PANEL");
/*      */       }
/*      */       
/*  611 */       return str;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void LoadFile(ObjectInputStream in)
/*      */   {
/*  622 */     int current_step = 0;
/*  623 */     Vector v = new Vector();
/*  624 */     IOROperation op = new IOROperation(50302, v);
/*      */     try
/*      */     {
/*  627 */       this.state = ((IORState)in.readObject());
/*  628 */       current_step = ((Integer)in.readObject()).intValue();
/*  629 */       this.is_result = ((Boolean)in.readObject()).booleanValue();
/*  630 */       this.operation_sequence = ((Vector)in.readObject());
/*  631 */       this.state.setSolverStepVector();
/*  632 */       in.close();
/*      */     }
/*      */     catch (Exception e1) {
/*  635 */       e1.printStackTrace();
/*  636 */       System.out.println("Open fails");
/*      */     }
/*  638 */     setStep(current_step);
/*  639 */     updatePanel();
/*      */     
/*  641 */     if (this.is_result) {
/*  642 */       this.controller.solver.doWork(op, this.controller.data);
/*  643 */       IORSASolvePanel.ResultPanel resutPanel = new IORSASolvePanel.ResultPanel(this, null);
/*  644 */       this.controller.setView(resutPanel);
/*  645 */       resutPanel.revalidate();
/*  646 */       resutPanel.repaint();
/*  647 */       return;
/*      */     }
/*  649 */     switch (getStep()) {
/*      */     case 1: 
/*      */       break;
/*      */     case 2: 
/*  653 */       setCurrentControlPanel(this.eventPanel);
/*  654 */       break;
/*      */     case 3: 
/*  656 */       setCurrentControlPanel(this.customerPanel);
/*  657 */       if (this.controller.data.is_customer_done) {
/*  658 */         this.customerPanel.setResultVisible(true);
/*  659 */         this.controller.data.is_customer_done = false;
/*  660 */         this.actionStatus.setText(String.valueOf(String.valueOf(new StringBuffer("For the customer just entering service, its waiting time has been ").append((float)this.controller.data.customer_wait_time).append(" ."))));
/*      */       }
/*  662 */       break;
/*      */     case 4: 
/*  664 */       setCurrentControlPanel(this.serverPanel);
/*  665 */       if (this.controller.data.is_server_done) {
/*  666 */         this.serverPanel.setResultVisible(true);
/*  667 */         this.controller.data.is_server_done = false;
/*  668 */         this.actionStatus.setText(String.valueOf(String.valueOf(new StringBuffer("For the customer just entering service, its waiting time has been ").append((float)this.controller.data.customer_wait_time).append(" ."))));
/*      */       }
/*  670 */       break;
/*      */     default: 
/*  672 */       System.out.println("Simulation has no this step.");
/*      */     }
/*  674 */     this.tpModel.fireTableDataChanged();
/*  675 */     revalidate();
/*  676 */     repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFile(ObjectOutputStream out)
/*      */   {
/*  686 */     int current_step = getStep();
/*      */     try
/*      */     {
/*  689 */       out.writeObject(this.state);
/*  690 */       out.writeObject(new Integer(current_step));
/*  691 */       out.writeObject(new Boolean(this.is_result));
/*  692 */       out.writeObject(this.operation_sequence);
/*  693 */       out.close();
/*      */     }
/*      */     catch (Exception e1) {
/*  696 */       e1.printStackTrace();
/*  697 */       System.out.println("Save fails");
/*      */     }
/*      */   }
/*      */   
/*  701 */   private class SATableModel extends AbstractTableModel { SATableModel(IORSASolvePanel..4 x$1) { this(); }
/*      */     
/*      */     public int getColumnCount() {
/*  704 */       int col = -1;
/*  705 */       switch (IORSASolvePanel.this.mode) {
/*      */       case 1: 
/*  707 */         col = 5;
/*  708 */         break;
/*      */       case 2: 
/*  710 */         col = 7;
/*  711 */         break;
/*      */       case 3: 
/*  713 */         col = 7;
/*  714 */         break;
/*      */       case 4: 
/*  716 */         col = 9;
/*      */       }
/*      */       
/*  719 */       return col;
/*      */     }
/*      */     
/*      */     public int getRowCount()
/*      */     {
/*  724 */       return 2;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col)
/*      */     {
/*  729 */       String str = "";
/*  730 */       switch (IORSASolvePanel.this.mode) {
/*      */       case 1: 
/*  732 */         switch (col) {
/*      */         case 0: 
/*  734 */           str = "C.T.";
/*  735 */           break;
/*      */         case 1: 
/*  737 */           str = "N.C.Q.";
/*  738 */           break;
/*      */         case 2: 
/*  740 */           str = "C.B.S.";
/*  741 */           break;
/*      */         case 3: 
/*  743 */           str = "N.A.";
/*  744 */           break;
/*      */         case 4: 
/*  746 */           str = "N.S.C.";
/*      */         }
/*      */         
/*  749 */         break;
/*      */       case 2: 
/*  751 */         switch (col) {
/*      */         case 0: 
/*  753 */           str = "C.T.";
/*  754 */           break;
/*      */         case 1: 
/*  756 */           str = "N.C.Q.C1.";
/*  757 */           break;
/*      */         case 2: 
/*  759 */           str = "N.C.Q.C2.";
/*  760 */           break;
/*      */         case 3: 
/*  762 */           str = "C.C.B.S.";
/*  763 */           break;
/*      */         case 4: 
/*  765 */           str = "N.A.C1.";
/*  766 */           break;
/*      */         case 5: 
/*  768 */           str = "N.A.C2.";
/*  769 */           break;
/*      */         case 6: 
/*  771 */           str = "N.S.C.";
/*      */         }
/*      */         
/*  774 */         break;
/*      */       case 3: 
/*  776 */         switch (col) {
/*      */         case 0: 
/*  778 */           str = "C.T.";
/*  779 */           break;
/*      */         case 1: 
/*  781 */           str = "N.C.Q.";
/*  782 */           break;
/*      */         case 2: 
/*  784 */           str = "C.B.S.S1";
/*  785 */           break;
/*      */         case 3: 
/*  787 */           str = "C.B.S.S2";
/*  788 */           break;
/*      */         case 4: 
/*  790 */           str = "N.A.";
/*  791 */           break;
/*      */         case 5: 
/*  793 */           str = "N.S.C.S1";
/*  794 */           break;
/*      */         case 6: 
/*  796 */           str = "N.S.C.S2";
/*      */         }
/*      */         
/*  799 */         break;
/*      */       case 4: 
/*  801 */         switch (col) {
/*      */         case 0: 
/*  803 */           str = "C.T.";
/*  804 */           break;
/*      */         case 1: 
/*  806 */           str = "N.C.Q.C1.";
/*  807 */           break;
/*      */         case 2: 
/*  809 */           str = "N.C.Q.C2.";
/*  810 */           break;
/*      */         case 3: 
/*  812 */           str = "C.C.B.S.S1";
/*  813 */           break;
/*      */         case 4: 
/*  815 */           str = "C.C.B.S.S2";
/*  816 */           break;
/*      */         case 5: 
/*  818 */           str = "N.A.C1.";
/*  819 */           break;
/*      */         case 6: 
/*  821 */           str = "N.A.C2.";
/*  822 */           break;
/*      */         case 7: 
/*  824 */           str = "N.S.C.S1";
/*  825 */           break;
/*      */         case 8: 
/*  827 */           str = "N.S.C.S2";
/*      */         }
/*      */         
/*      */         break;
/*      */       }
/*      */       
/*  833 */       return str;
/*      */     }
/*      */     
/*      */ 
/*      */     public Object getValueAt(int row, int col)
/*      */     {
/*  839 */       String str = "";
/*  840 */       if (Math.abs(IORSASolvePanel.this.controller.data.current_time[row] + 1.0D) < 1.0E-6D) {
/*  841 */         return str;
/*      */       }
/*  843 */       switch (IORSASolvePanel.this.mode) {
/*      */       case 1: 
/*  845 */         switch (col) {
/*      */         case 0: 
/*  847 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.current_time[row])));
/*  848 */           break;
/*      */         case 1: 
/*  850 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORSASolvePanel.this.controller.data.num_customer1_in_queue[row])));
/*  851 */           break;
/*      */         case 2: 
/*  853 */           if (IORSASolvePanel.this.controller.data.customer_being_served[row] != 0) {
/*  854 */             str = String.valueOf(String.valueOf(str)).concat("YES");
/*      */           } else
/*  856 */             str = String.valueOf(String.valueOf(str)).concat("NO");
/*  857 */           break;
/*      */         case 3: 
/*  859 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(getTime(IORSASolvePanel.this.controller.data.customer1_arrive_time[row]))));
/*  860 */           break;
/*      */         case 4: 
/*  862 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(getTime(IORSASolvePanel.this.controller.data.server1_finish_time[row]))));
/*      */         }
/*      */         
/*  865 */         break;
/*      */       case 2: 
/*  867 */         switch (col) {
/*      */         case 0: 
/*  869 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.current_time[row])));
/*  870 */           break;
/*      */         case 1: 
/*  872 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORSASolvePanel.this.controller.data.num_customer1_in_queue[row])));
/*  873 */           break;
/*      */         case 2: 
/*  875 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORSASolvePanel.this.controller.data.num_customer2_in_queue[row])));
/*  876 */           break;
/*      */         case 3: 
/*  878 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORSASolvePanel.this.controller.data.class_of_customer_being_served_by_server1[row])));
/*  879 */           break;
/*      */         case 4: 
/*  881 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(getTime(IORSASolvePanel.this.controller.data.customer1_arrive_time[row]))));
/*  882 */           break;
/*      */         case 5: 
/*  884 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(getTime(IORSASolvePanel.this.controller.data.customer2_arrive_time[row]))));
/*  885 */           break;
/*      */         case 6: 
/*  887 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(getTime(IORSASolvePanel.this.controller.data.server1_finish_time[row]))));
/*      */         }
/*      */         
/*  890 */         break;
/*      */       case 3: 
/*  892 */         switch (col) {
/*      */         case 0: 
/*  894 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.current_time[row])));
/*  895 */           break;
/*      */         case 1: 
/*  897 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORSASolvePanel.this.controller.data.num_customer1_in_queue[row])));
/*  898 */           break;
/*      */         case 2: 
/*  900 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORSASolvePanel.this.controller.data.class_of_customer_being_served_by_server1[row])));
/*  901 */           break;
/*      */         case 3: 
/*  903 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORSASolvePanel.this.controller.data.class_of_customer_being_served_by_server2[row])));
/*  904 */           break;
/*      */         case 4: 
/*  906 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(getTime(IORSASolvePanel.this.controller.data.customer1_arrive_time[row]))));
/*  907 */           break;
/*      */         case 5: 
/*  909 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(getTime(IORSASolvePanel.this.controller.data.server1_finish_time[row]))));
/*  910 */           break;
/*      */         case 6: 
/*  912 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(getTime(IORSASolvePanel.this.controller.data.server2_finish_time[row]))));
/*      */         }
/*      */         
/*  915 */         break;
/*      */       case 4: 
/*  917 */         switch (col) {
/*      */         case 0: 
/*  919 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((float)IORSASolvePanel.this.controller.data.current_time[row])));
/*  920 */           break;
/*      */         case 1: 
/*  922 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORSASolvePanel.this.controller.data.num_customer1_in_queue[row])));
/*  923 */           break;
/*      */         case 2: 
/*  925 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORSASolvePanel.this.controller.data.num_customer2_in_queue[row])));
/*  926 */           break;
/*      */         case 3: 
/*  928 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORSASolvePanel.this.controller.data.class_of_customer_being_served_by_server1[row])));
/*  929 */           break;
/*      */         case 4: 
/*  931 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(IORSASolvePanel.this.controller.data.class_of_customer_being_served_by_server2[row])));
/*  932 */           break;
/*      */         case 5: 
/*  934 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(getTime(IORSASolvePanel.this.controller.data.customer1_arrive_time[row]))));
/*  935 */           break;
/*      */         case 6: 
/*  937 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(getTime(IORSASolvePanel.this.controller.data.customer2_arrive_time[row]))));
/*  938 */           break;
/*      */         case 7: 
/*  940 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(getTime(IORSASolvePanel.this.controller.data.server1_finish_time[row]))));
/*  941 */           break;
/*      */         case 8: 
/*  943 */           str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf(getTime(IORSASolvePanel.this.controller.data.server2_finish_time[row]))));
/*      */         }
/*      */         
/*      */         break;
/*      */       }
/*      */       
/*  949 */       return str;
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c)
/*      */     {
/*  954 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/*  959 */       return false;
/*      */     }
/*      */     
/*      */     private String getTime(double d) {
/*  963 */       String str = "";
/*  964 */       if (Math.abs(d + 1.0D) < 1.0E-6D) {
/*  965 */         str = String.valueOf(String.valueOf(str)).concat("---");
/*      */       } else {
/*  967 */         str = String.valueOf(String.valueOf(str)).concat(String.valueOf(String.valueOf((float)d)));
/*      */       }
/*  969 */       return str;
/*      */     }
/*      */     
/*      */     private SATableModel() {} }
/*      */   
/*  974 */   private class ControlPanel extends JPanel { ControlPanel(String x$1, String[] x$2, String x$3, IORSASolvePanel..4 x$4) { this(x$1, x$2, x$3); }
/*      */     
/*  976 */     private JTextArea mll_prompt = null;
/*      */     
/*  978 */     private JComboBox cb_operation = null;
/*  979 */     private JButton bn_OK = null;
/*      */     
/*  981 */     private JTextArea mll_result = null;
/*  982 */     private JButton bn_Next = null;
/*      */     
/*      */ 
/*      */     private ControlPanel(String prompt, String[] ops, String result)
/*      */     {
/*  987 */       this.cb_operation = new JComboBox(ops);
/*  988 */       this.bn_OK = new JButton("OK");
/*  989 */       this.bn_OK.addActionListener(new IORSASolvePanel.2((ControlPanel)this));
/*      */       
/*      */ 
/*      */ 
/*  993 */       JPanel p1 = new JPanel();
/*  994 */       p1.add(this.cb_operation);
/*  995 */       p1.add(this.bn_OK);
/*      */       
/*      */ 
/*  998 */       this.mll_prompt = new JTextArea(prompt);
/*  999 */       this.mll_prompt.setBorder(BorderFactory.createEmptyBorder(30, 15, 20, 15));
/* 1000 */       this.mll_prompt.setHighlighter(null);
/* 1001 */       this.mll_prompt.setEditable(false);
/* 1002 */       LookAndFeel.installBorder(this.mll_prompt, "Label.border");
/* 1003 */       LookAndFeel.installColorsAndFont(this.mll_prompt, "Label.background", "Label.foreground", "Label.font");
/*      */       
/* 1005 */       JPanel p2 = new JPanel(new BorderLayout());
/* 1006 */       p2.add(this.mll_prompt, "Center");
/* 1007 */       p2.add(p1, "South");
/*      */       
/* 1009 */       setLayout(new BoxLayout(this, 1));
/* 1010 */       add(p2);
/*      */       
/*      */ 
/* 1013 */       if (result != null) {
/* 1014 */         this.mll_result = new JTextArea("\n\n".concat(String.valueOf(String.valueOf(result))));
/* 1015 */         this.mll_result.setBorder(BorderFactory.createEmptyBorder(30, 15, 20, 15));
/* 1016 */         this.mll_result.setHighlighter(null);
/* 1017 */         this.mll_result.setEditable(false);
/* 1018 */         LookAndFeel.installBorder(this.mll_result, "Label.border");
/* 1019 */         LookAndFeel.installColorsAndFont(this.mll_result, "Label.background", "Label.foreground", "Label.font");
/*      */         
/* 1021 */         this.bn_Next = new JButton("Next");
/* 1022 */         this.bn_Next.addActionListener(new IORSASolvePanel.3((ControlPanel)this));
/*      */         
/*      */ 
/* 1025 */         JPanel p3 = new JPanel();
/* 1026 */         p3.add(this.bn_Next);
/*      */         
/* 1028 */         JPanel p4 = new JPanel(new BorderLayout());
/* 1029 */         p4.add(this.mll_result, "Center");
/* 1030 */         p4.add(p3, "South");
/*      */         
/* 1032 */         add(p4);
/*      */       }
/*      */     }
/*      */     
/*      */     private int getSelectedIndex() {
/* 1037 */       return this.cb_operation.getSelectedIndex();
/*      */     }
/*      */     
/*      */     private void setResultVisible(boolean b) {
/* 1041 */       if (this.mll_result != null)
/* 1042 */         this.mll_result.setVisible(b);
/* 1043 */       if (this.bn_Next != null) {
/* 1044 */         this.bn_Next.setVisible(b);
/*      */       }
/* 1046 */       if (this.bn_OK != null)
/* 1047 */         this.bn_OK.setEnabled(!b);
/* 1048 */       if (this.cb_operation != null) {
/* 1049 */         this.cb_operation.setEnabled(!b);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 1054 */   private String str0 = "";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1064 */   private String str1 = "";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1071 */   private String str2 = "";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1077 */   private String str3 = "";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1084 */   private String str4 = "";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1091 */   private String str5 = "";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/* 1103 */     String str00 = "";
/* 1104 */     switch (this.mode) {
/*      */     case 1: 
/* 1106 */       str00 = "The current procedure is for the case of one class of customers and one server.\n";
/* 1107 */       str00 = String.valueOf(String.valueOf(str00)).concat("\nThe notations are:\n\n");
/* 1108 */       str00 = String.valueOf(String.valueOf(str00)).concat("       1. C.T.:\tCurrent Time\n");
/* 1109 */       str00 = String.valueOf(String.valueOf(str00)).concat("       2. N.C.Q.:\tNumber of Customers in Queue\n");
/* 1110 */       str00 = String.valueOf(String.valueOf(str00)).concat("       3. C.B.S.:\tCustomer Being Served\n");
/* 1111 */       str00 = String.valueOf(String.valueOf(str00)).concat("       4. N.A.:\tNext Arrival of a customer\n");
/* 1112 */       str00 = String.valueOf(String.valueOf(str00)).concat("       5. N.S.C.:\tNext Service Completion\n");
/* 1113 */       break;
/*      */     case 2: 
/* 1115 */       str00 = "The current procedure is for the case of two classes of customers and one server.\n";
/* 1116 */       str00 = String.valueOf(String.valueOf(str00)).concat("\nThe notations are:\n\n");
/* 1117 */       str00 = String.valueOf(String.valueOf(str00)).concat("       1. C.T.:\tCurrent Time\n");
/* 1118 */       str00 = String.valueOf(String.valueOf(str00)).concat("       2. N.C.Q.C1:\tNumber of Customers in Queue from Class 1\n");
/* 1119 */       str00 = String.valueOf(String.valueOf(str00)).concat("       3. N.C.Q.C2:\tNumber of Customers in Queue from Class 2\n");
/* 1120 */       str00 = String.valueOf(String.valueOf(str00)).concat("       4. C.C.B.S.:\tClass of Customer Being Served\n");
/* 1121 */       str00 = String.valueOf(String.valueOf(str00)).concat("       5. N.A.C1.:\tNext Arrival of a customer from Class 1\n");
/* 1122 */       str00 = String.valueOf(String.valueOf(str00)).concat("       6. N.A.C2.:\tNext Arrival of a customer from Class 2\n");
/* 1123 */       str00 = String.valueOf(String.valueOf(str00)).concat("       7. N.S.C.:\tNext Service Completion\n");
/* 1124 */       break;
/*      */     case 3: 
/* 1126 */       str00 = "The current procedure is for the case of one class of customers and two servers.\n";
/* 1127 */       str00 = String.valueOf(String.valueOf(str00)).concat("\nThe notations are:\n\n");
/* 1128 */       str00 = String.valueOf(String.valueOf(str00)).concat("       1. C.T.:\tCurrent Time\n");
/* 1129 */       str00 = String.valueOf(String.valueOf(str00)).concat("       2. N.C.Q.:\tNumber of Customers in Queue\n");
/* 1130 */       str00 = String.valueOf(String.valueOf(str00)).concat("       3. C.B.S.S1:\tCustomer Being Served by Server 1\n");
/* 1131 */       str00 = String.valueOf(String.valueOf(str00)).concat("       4. C.B.S.S2:\tCustomer Being Served by Server 2\n");
/* 1132 */       str00 = String.valueOf(String.valueOf(str00)).concat("       5. N.A.:\tNext Arrival of a customer\n");
/* 1133 */       str00 = String.valueOf(String.valueOf(str00)).concat("       6. N.S.C.S1:\tNext Service Completion by Server 1\n");
/* 1134 */       str00 = String.valueOf(String.valueOf(str00)).concat("       7. N.S.C.S2:\tNext Service Completion by Server 2\n");
/* 1135 */       break;
/*      */     case 4: 
/* 1137 */       str00 = "The current procedure is for the case of two classes of customers and two servers.\n";
/* 1138 */       str00 = String.valueOf(String.valueOf(str00)).concat("\nThe notations are:\n\n");
/* 1139 */       str00 = String.valueOf(String.valueOf(str00)).concat("       1. C.T.:\t\tCurrent Time\n");
/* 1140 */       str00 = String.valueOf(String.valueOf(str00)).concat("       2. N.C.Q.C1:\t\tNumber of Customers in Queue from Class 1\n");
/* 1141 */       str00 = String.valueOf(String.valueOf(str00)).concat("       3. N.C.Q.C2:\t\tNumber of Customers in Queue from Class 2\n");
/* 1142 */       str00 = String.valueOf(String.valueOf(str00)).concat("       4. C.C.B.S.S1:\tClass of Customer Being Served by Server 1\n");
/* 1143 */       str00 = String.valueOf(String.valueOf(str00)).concat("       5. C.C.B.S.S2:\tClass of Customer Being Served by Server 2\n");
/* 1144 */       str00 = String.valueOf(String.valueOf(str00)).concat("       6. N.A.C1.:\t\tNext Arrival of a customer from Class 1\n");
/* 1145 */       str00 = String.valueOf(String.valueOf(str00)).concat("       7. N.A.C2.:\t\tNext Arrival of a customer from Class 2\n");
/* 1146 */       str00 = String.valueOf(String.valueOf(str00)).concat("       8. N.S.C.S1:\t\tNext Service Completion by Server 1\n");
/* 1147 */       str00 = String.valueOf(String.valueOf(str00)).concat("       9. N.S.C.S2:\t\tNext Service Completion by Server 2\n");
/* 1148 */       break;
/*      */     }
/*      */     
/*      */     
/*      */ 
/* 1153 */     str00 = String.valueOf(String.valueOf(new StringBuffer(" ").append(str00).append("\n\n\nPress the ENTER key to continue the current procedure.")));
/*      */     
/* 1155 */     String str = "";
/* 1156 */     if (this.is_result) {
/* 1157 */       str = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str5).append("\n\n\nPress the ENTER key to continue the current procedure.")));
/*      */     } else {
/* 1159 */       switch (getStep()) {
/*      */       case 1: 
/* 1161 */         str = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str1).append(str00)));
/* 1162 */         break;
/*      */       case 2: 
/* 1164 */         str = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str2).append(str00)));
/* 1165 */         break;
/*      */       case 3: 
/* 1167 */         str = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str3).append(str00)));
/* 1168 */         break;
/*      */       case 4: 
/* 1170 */         str = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str4).append(str00)));
/* 1171 */         break;
/*      */       default: 
/* 1173 */         str = String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str00)));
/*      */       }
/*      */       
/*      */     }
/*      */     
/* 1178 */     this.help_content_step.setText(str);
/* 1179 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/* 1186 */     String str = "Interactively Simulate a Queueing Problem\n\n";
/* 1187 */     str = String.valueOf(String.valueOf(str)).concat("The interactive routine repeats three steps over and over, for as long as you ");
/* 1188 */     str = String.valueOf(String.valueOf(str)).concat("would like to run the simulation. At each step, you are asked to make the key ");
/* 1189 */     str = String.valueOf(String.valueOf(str)).concat("decision. The three decisions that will need to be made are:\n\n");
/* 1190 */     str = String.valueOf(String.valueOf(str)).concat("       1.  Which times now need to be randomly generated?\n");
/* 1191 */     str = String.valueOf(String.valueOf(str)).concat("       2.  Which event occurs next?\n");
/* 1192 */     str = String.valueOf(String.valueOf(str)).concat("       3.  What is the result of the event?");
/* 1193 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/* 1195 */     this.help_content_procedure.setText(str);
/* 1196 */     this.help_content_procedure.revalidate();
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORSASolvePanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */