/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.Locale;
/*      */ import java.util.Vector;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.DefaultListModel;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextArea;
/*      */ import javax.swing.JTextField;
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
/*      */ public class IORNAActionPanel
/*      */   extends IORActionPanel
/*      */ {
/*   47 */   private final char DELTA = 'Δ';
/*   48 */   private final char THETA = 'θ';
/*      */   
/*   50 */   private final String INFI = "Inf.";
/*      */   
/*   52 */   private IORNAProcessController controller = null;
/*      */   
/*   54 */   private JPanel mainPanel = new JPanel();
/*   55 */   private IORNACanvas canvas = null;
/*   56 */   private JPanel boundPanel = null;
/*   57 */   private JPanel upPanel = null;
/*   58 */   private JPanel lowPanel = null;
/*   59 */   private JPanel operatePanel = null;
/*   60 */   private JPanel buttPanel = null;
/*   61 */   private IORArclistPanel basiclistPanel = null;
/*   62 */   private IORArclistPanel reverselistPanel = null;
/*      */   
/*   64 */   private IORNAActionPanel.IORDeltaTableModel deltaModel = null;
/*   65 */   private JTable deltaTable = null;
/*   66 */   private JScrollPane scrlPane = null;
/*      */   
/*   68 */   private JLabel indilabel = new JLabel();
/*   69 */   private JLabel resultlabel = new JLabel();
/*   70 */   private JLabel boundcaption = new JLabel("Capacity:");
/*   71 */   private JLabel[] caplabel = null; private JLabel[] keylabel = null;
/*      */   
/*   73 */   private JLabel oklabel = new JLabel("Is the current feasible solution optimal? ");
/*   74 */   private JLabel dylabel = new JLabel();
/*   75 */   private JLabel xlabel = new JLabel(" θ = ");
/*   76 */   private JTextArea statetext = new JTextArea();
/*      */   
/*   78 */   private JPanel statePanel = new JPanel();
/*   79 */   private JPanel[] flowPanel = null;
/*   80 */   private JButton backbutt = new JButton("Back");
/*   81 */   private JButton nextbutt = new JButton("Next");
/*      */   
/*   83 */   private JTextField yorn = new JTextField("N", 2);
/*   84 */   private JTextField cyclename = new JTextField(10);
/*   85 */   private JTextField selarc = new JTextField(3);
/*   86 */   private DecimalField xval = null;
/*   87 */   private DecimalField[] arcflow = null;
/*   88 */   private JLabel[] arcname = null;
/*      */   
/*   90 */   private String str_preface = null;
/*   91 */   private String str_enter_init_flow = "Please specify the initial flow for each basic arc:";
/*   92 */   private String str_enter_cycle = null;
/*   93 */   private String str_get_deltaz = null;
/*   94 */   private String str_choose_enter = "Please select an entering basic arc.";
/*   95 */   private String str_choose_leave = "Please select a leaving basic arc and enter the value of the incremental flow θ .";
/*   96 */   private String str_need_reverse = "Does the arc would be reversed?";
/*   97 */   private String str_iterate_complete = "This completes an iteration. The basic arcs for the new feasible spanning tree are the solid arrows \nshown above. The nonbasic arcs are the dashed arrows above. The flows for the new feasible solution \nare given next to the arcs.  Press the NEXT button to have the computer perform the calculations \nneeded to apply the optimality test and (if necessary) select the next entering basic arc.";
/*      */   
/*      */ 
/*      */ 
/*  101 */   private String str_enter_reverse_flow = "The following arcs have been reversed. Please enter their flows in the original direction.";
/*  102 */   private String str_problem_end = "Shown above is the optimal solution including the flows through all of the original arcs.";
/*      */   
/*  104 */   private int step = 1;
/*  105 */   private int next_select_nonbasic = 1;
/*  106 */   private Vector opseq = new Vector();
/*      */   private String current_nonbasic;
/*  108 */   private Vector leaving_arcs = new Vector();
/*      */   private int nProb;
/*      */   private int nBasic;
/*      */   private int nArcs;
/*  112 */   private int nDash; private DecimalFormat decfm = new DecimalFormat();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IORNAActionPanel(IORNAProcessController c)
/*      */   {
/*  121 */     super(c);
/*  122 */     this.controller = c;
/*      */     
/*  124 */     this.bn_back.setVisible(false);
/*      */     
/*  126 */     this.state = new IORState(this.controller.solver);
/*  127 */     add(this.mainPanel);
/*      */     
/*      */ 
/*  130 */     this.nProb = this.controller.data.which_problem;
/*  131 */     this.nBasic = (this.controller.data.num_nodes - 1);
/*  132 */     this.nArcs = this.controller.data.num_arcs;
/*  133 */     this.nDash = (this.nArcs - this.nBasic);
/*      */     
/*      */ 
/*  136 */     this.arcname = new JLabel[this.nArcs];
/*  137 */     this.arcflow = new DecimalField[this.nArcs];
/*  138 */     this.flowPanel = new JPanel[this.nArcs];
/*  139 */     this.caplabel = new JLabel[this.nArcs];
/*  140 */     this.keylabel = new JLabel[this.nBasic + 1];
/*  141 */     this.xval = new DecimalField(0.0D, this.decfm);
/*      */     
/*      */ 
/*  144 */     for (int i = 0; i < this.nArcs; i++) {
/*  145 */       this.arcflow[i] = new DecimalField(0.0D, this.decfm);
/*  146 */       this.arcname[i] = new JLabel();
/*  147 */       this.flowPanel[i] = new JPanel();
/*  148 */       this.caplabel[i] = new JLabel();
/*      */     }
/*  150 */     for (i = 0; i < this.nBasic + 1; i++) {
/*  151 */       this.keylabel[i] = new JLabel();
/*      */     }
/*      */     
/*  154 */     this.dylabel.setAlignmentX(0.5F);
/*      */     
/*      */ 
/*  157 */     this.canvas = new IORNACanvas(500, 300, this.controller.data);
/*  158 */     this.canvas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
/*      */     
/*  160 */     this.boundPanel = new JPanel();
/*  161 */     this.boundPanel.setLayout(new BoxLayout(this.boundPanel, 1));
/*  162 */     this.boundPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
/*      */     
/*  164 */     this.buttPanel = new JPanel();
/*  165 */     this.buttPanel.add(this.backbutt);
/*  166 */     this.buttPanel.add(Box.createRigidArea(new Dimension(20, 30)));
/*  167 */     this.buttPanel.add(this.nextbutt);
/*  168 */     this.buttPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
/*      */     
/*  170 */     this.operatePanel = new JPanel();
/*  171 */     this.operatePanel.setLayout(new BoxLayout(this.operatePanel, 1));
/*      */     
/*      */ 
/*      */ 
/*  175 */     this.str_preface = String.valueOf(String.valueOf(new StringBuffer("Above is the network for Problem 9.7-").append(this.nProb).append(". The number next to each arc is the cost ").append("per unit flow through that arc. \n(Later, parentheses will be placed around a number in ").append("the same location when it represents the flow through \nthat arc instead.) ").append("The number in square brackets next to each node is the flow generated at that node. \nArc capacities ").append("are given on the right.  Press the NEXT button to begin the Network Simplex Method.")));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  180 */     this.statetext.setText(this.str_preface);
/*  181 */     this.statetext.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
/*  182 */     this.statetext.setBackground(getBackground());
/*  183 */     this.statetext.setForeground(Color.blue);
/*  184 */     this.statetext.setEditable(false);
/*  185 */     this.statetext.setPreferredSize(new Dimension(600, 100));
/*  186 */     this.statetext.setMinimumSize(new Dimension(600, 60));
/*  187 */     this.operatePanel.add(this.statetext);
/*      */     
/*  189 */     this.basiclistPanel = new IORArclistPanel(c, 1);
/*  190 */     this.reverselistPanel = new IORArclistPanel(c, 2);
/*      */     
/*      */ 
/*  193 */     this.nextbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  195 */         IORNAActionPanel.this.doNext();
/*      */       }
/*  197 */     });
/*  198 */     this.backbutt.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  200 */         IORNAActionPanel.this.doBack();
/*      */       }
/*      */       
/*  203 */     });
/*  204 */     this.deltaModel = new IORNAActionPanel.IORDeltaTableModel();
/*  205 */     this.deltaTable = new JTable(this.deltaModel);
/*  206 */     this.scrlPane = new JScrollPane(this.deltaTable);
/*  207 */     this.scrlPane.setPreferredSize(new Dimension(110, 120));
/*  208 */     this.scrlPane.setMaximumSize(new Dimension(120, 150));
/*  209 */     this.scrlPane.setMinimumSize(new Dimension(110, 120));
/*  210 */     this.scrlPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
/*      */     
/*  212 */     buildBoundPanel();
/*      */     
/*  214 */     this.upPanel = new JPanel();
/*  215 */     this.upPanel.setLayout(new BorderLayout());
/*  216 */     this.upPanel.add(this.canvas, "Center");
/*  217 */     this.upPanel.add(this.boundPanel, "East");
/*      */     
/*  219 */     this.lowPanel = new JPanel();
/*  220 */     this.lowPanel.setLayout(new BoxLayout(this.lowPanel, 1));
/*  221 */     this.lowPanel.add(Box.createRigidArea(new Dimension(0, 10)));
/*  222 */     this.lowPanel.add(this.operatePanel);
/*  223 */     this.lowPanel.add(Box.createRigidArea(new Dimension(0, 20)));
/*  224 */     this.lowPanel.add(this.buttPanel);
/*      */     
/*  226 */     this.mainPanel.setLayout(new BorderLayout());
/*  227 */     this.mainPanel.add(this.upPanel, "Center");
/*  228 */     this.mainPanel.add(this.lowPanel, "South");
/*      */     
/*      */ 
/*  231 */     this.canvas.setDrawCostInfo(true);
/*  232 */     this.canvas.setDrawFlowInfo(false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void buildBoundPanel()
/*      */   {
/*  239 */     String str = new String("");
/*      */     
/*  241 */     JLabel restlabel = new JLabel();
/*      */     
/*  243 */     this.boundPanel.add(this.boundcaption);
/*  244 */     int k = 0;
/*  245 */     for (int i = 0; i < this.nArcs; i++) {
/*  246 */       double capty = this.controller.data.arcs[i].capacity;
/*  247 */       if (capty > 0) {
/*  248 */         this.caplabel[i].setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.controller.data.arcs[i].getArcName()))).append(":  ").append(this.decfm.format(capty)))));
/*  249 */         this.boundPanel.add(this.caplabel[i]);
/*  250 */         k++;
/*      */       }
/*      */     }
/*  253 */     if ((k < this.nArcs) && (k > 0)) {
/*  254 */       this.boundPanel.add(Box.createVerticalStrut(5));
/*  255 */       restlabel.setText("Rest:  Inf.");
/*  256 */       this.boundPanel.add(restlabel);
/*      */     }
/*  258 */     else if (k == 0) {
/*  259 */       this.boundPanel.add(Box.createVerticalStrut(5));
/*  260 */       restlabel.setText("All:  Inf.");
/*  261 */       this.boundPanel.add(restlabel);
/*      */     }
/*      */     
/*  264 */     if ((this.nProb < 8) && (this.nProb > 2)) {
/*  265 */       JLabel keycap = new JLabel("Key");
/*  266 */       this.boundPanel.add(Box.createVerticalStrut(10));
/*  267 */       this.boundPanel.add(keycap);
/*  268 */       this.boundPanel.add(Box.createVerticalStrut(5));
/*  269 */       for (i = 0; i < this.nBasic + 1; i++) {
/*  270 */         char node = (char)(65 + i);
/*  271 */         str = String.valueOf(String.valueOf(new StringBuffer("").append(node).append(" =  ").append(this.controller.data.notation[i])));
/*  272 */         this.keylabel[i].setText(str);
/*  273 */         this.boundPanel.add(this.keylabel[i]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void finishProcedure()
/*      */   {
/*  283 */     this.actionStatus.setText("The current procedure is finished. Please go to the Procedure menu to continue!");
/*  284 */     setFinishEnabled(false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void backStep() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updatePanel() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doBack()
/*      */   {
/*  302 */     String addarc = new String();String leavearc = new String("UU");
/*  303 */     String eqz = new String("ΔZ = ");
/*  304 */     JPanel inputPanel = null;
/*  305 */     JPanel enterPanel = null;
/*      */     
/*  307 */     if (this.opseq.isEmpty() == false) {
/*  308 */       int last = ((Integer)this.opseq.lastElement()).intValue();
/*  309 */       this.opseq.removeElementAt(this.opseq.size() - 1);
/*      */       
/*  311 */       switch (last) {
/*      */       case 1: 
/*  313 */         this.statetext.setText(this.str_preface);
/*  314 */         this.operatePanel.removeAll();
/*  315 */         this.operatePanel.add(this.statetext);
/*      */         
/*  317 */         this.canvas.setDrawCostInfo(true);
/*  318 */         this.canvas.setDrawFlowInfo(false);
/*  319 */         this.step = 1;
/*      */         
/*  321 */         revalidate();
/*  322 */         repaint();
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
/*      */ 
/*  632 */         break;
/*      */       case 2: 
/*  326 */         this.controller.solver.reset();
/*  327 */         this.state.back();
/*  328 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  330 */         this.basiclistPanel = new IORArclistPanel(this.controller, 1);
/*  331 */         this.operatePanel.removeAll();
/*  332 */         this.operatePanel.add(this.basiclistPanel);
/*  333 */         this.step = 2;
/*      */         
/*  335 */         revalidate();
/*  336 */         repaint();
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
/*      */ 
/*  632 */         break;
/*      */       case 3: 
/*  340 */         this.controller.solver.reset();
/*  341 */         this.controller.solver.getData(this.controller.data);
/*  342 */         this.state.back();
/*  343 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  345 */         this.reverselistPanel = new IORArclistPanel(this.controller, 2);
/*  346 */         this.operatePanel.removeAll();
/*  347 */         this.operatePanel.add(this.reverselistPanel);
/*  348 */         this.step = 3;
/*      */         
/*  350 */         revalidate();
/*  351 */         repaint();
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
/*  632 */         break;
/*      */       case 4: 
/*  355 */         this.controller.solver.reset();
/*  356 */         this.state.back();
/*  357 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  359 */         this.next_select_nonbasic = 1;
/*      */         
/*  361 */         inputPanel = new JPanel();
/*  362 */         this.dylabel.setText(this.str_enter_init_flow);
/*  363 */         this.dylabel.setAlignmentX(0.5F);
/*  364 */         inputPanel.setLayout(new BoxLayout(inputPanel, 1));
/*  365 */         inputPanel.add(this.dylabel);
/*  366 */         for (int i = 0; i < this.nArcs; i++) {
/*  367 */           if (this.controller.data.arcs[i].is_basic) {
/*  368 */             this.arcname[i].setText(String.valueOf(String.valueOf(new StringBuffer("The initial flow for ").append(this.controller.data.arcs[i].getArcName()).append(" = "))));
/*  369 */             this.arcname[i].setLabelFor(this.arcflow[i]);
/*  370 */             this.flowPanel[i].add(this.arcname[i]);
/*  371 */             this.flowPanel[i].add(this.arcflow[i]);
/*  372 */             this.flowPanel[i].setPreferredSize(new Dimension(300, 35));
/*  373 */             this.flowPanel[i].setMinimumSize(new Dimension(300, 35));
/*  374 */             this.flowPanel[i].setMaximumSize(new Dimension(300, 35));
/*  375 */             inputPanel.add(Box.createRigidArea(new Dimension(0, 2)));
/*  376 */             inputPanel.add(this.flowPanel[i]);
/*      */           }
/*      */         }
/*  379 */         this.operatePanel.removeAll();
/*  380 */         this.operatePanel.add(inputPanel);
/*  381 */         this.step = 4;
/*      */         
/*  383 */         revalidate();
/*  384 */         repaint();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  632 */         break;
/*      */       case 5: 
/*  388 */         this.controller.solver.reset();
/*  389 */         this.state.back();
/*  390 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*      */ 
/*  393 */         this.next_select_nonbasic -= 1;
/*      */         
/*  395 */         addarc = this.current_nonbasic;
/*  396 */         this.str_enter_cycle = String.valueOf(String.valueOf(new StringBuffer("Suppose arc ").append(addarc).append(" is added to the feasible spanning tree. Then identify the undirected cycle created, \n").append("beginning with node ").append(addarc.charAt(0)).append(" and ").append(addarc.charAt(1)).append(" and ending with node ").append(addarc.charAt(0)).append(".")));
/*      */         
/*      */ 
/*  399 */         this.statetext.setText(this.str_enter_cycle);
/*  400 */         this.indilabel.setText(String.valueOf(String.valueOf(new StringBuffer("Cycle created when arc ").append(addarc).append(" is added to the spanning tree:"))));
/*  401 */         this.indilabel.setLabelFor(this.cyclename);
/*  402 */         inputPanel = new JPanel();
/*      */         
/*  404 */         inputPanel.add(this.indilabel);
/*  405 */         inputPanel.add(this.cyclename);
/*  406 */         this.operatePanel.removeAll();
/*  407 */         this.operatePanel.add(this.statetext);
/*  408 */         this.operatePanel.add(Box.createVerticalStrut(20));
/*  409 */         this.operatePanel.add(inputPanel);
/*      */         
/*  411 */         this.canvas.setDrawCostInfo(false);
/*  412 */         this.canvas.setDrawFlowInfo(true);
/*  413 */         this.canvas.setDrawLoopInfo(false);
/*  414 */         this.step = 5;
/*      */         
/*  416 */         revalidate();
/*  417 */         repaint();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  632 */         break;
/*      */       case 6: 
/*  420 */         if (this.step == 7)
/*      */         {
/*  422 */           this.controller.solver.reset();
/*  423 */           this.state.back();
/*  424 */           this.controller.solver.getData(this.controller.data);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  429 */         this.next_select_nonbasic -= 1;
/*  430 */         int itcnt = 0;
/*  431 */         for (int i = 0; i < this.nArcs; i++) {
/*  432 */           if (this.controller.data.arcs[i].is_basic == false) {
/*  433 */             itcnt++;
/*      */             
/*  435 */             if (itcnt == this.next_select_nonbasic) {
/*  436 */               addarc = this.controller.data.arcs[i].getArcName();
/*  437 */               this.current_nonbasic = addarc;
/*  438 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  443 */         this.next_select_nonbasic += 1;
/*      */         
/*  445 */         this.str_get_deltaz = String.valueOf(String.valueOf(new StringBuffer("Suppose arc ").append(this.current_nonbasic).append(" with ").append('θ').append(" is added to the feasible spanning tree. The above network shows next to\n").append("each arc the resulting incremental flow (in parentheses) and the cost per unit flow.")));
/*      */         
/*  447 */         this.statetext.setText(this.str_get_deltaz);
/*  448 */         this.indilabel.setText("When θ = 1, the resulting overall increment in total cost (Z) is:");
/*      */         
/*  450 */         for (i = 0; i < this.controller.data.num_segment; i++) {
/*  451 */           String ns = this.decfm.format(this.controller.data.segment_inc[i]);
/*  452 */           if (this.controller.data.segment_inc[i] > -0.001D) {
/*  453 */             eqz = String.valueOf(String.valueOf(eqz)).concat(" + ");
/*      */           }
/*  455 */           eqz = String.valueOf(String.valueOf(eqz)).concat(String.valueOf(String.valueOf(ns)));
/*      */         }
/*  457 */         String ns = this.decfm.format(this.controller.data.total_inc);
/*  458 */         eqz = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(eqz))).append(" = ").append(ns)));
/*  459 */         this.resultlabel.setText(eqz);
/*      */         
/*  461 */         this.operatePanel.removeAll();
/*  462 */         this.indilabel.setAlignmentX(0.5F);
/*  463 */         this.resultlabel.setAlignmentX(0.5F);
/*  464 */         this.operatePanel.add(this.statetext);
/*  465 */         this.operatePanel.add(Box.createRigidArea(new Dimension(0, 20)));
/*  466 */         this.operatePanel.add(this.indilabel);
/*  467 */         this.operatePanel.add(Box.createRigidArea(new Dimension(0, 5)));
/*  468 */         this.operatePanel.add(this.resultlabel);
/*      */         
/*  470 */         this.canvas.setDrawCostInfo(true);
/*  471 */         this.canvas.setDrawFlowInfo(false);
/*  472 */         this.canvas.setDrawLoopInfo(true);
/*  473 */         this.step = 6;
/*      */         
/*  475 */         revalidate();
/*  476 */         repaint();
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
/*      */ 
/*  632 */         break;
/*      */       case 7: 
/*  479 */         this.deltaModel.fireTableDataChanged();
/*  480 */         this.operatePanel.removeAll();
/*  481 */         enterPanel = new JPanel();
/*  482 */         enterPanel.add(this.scrlPane);
/*  483 */         enterPanel.add(Box.createHorizontalStrut(30));
/*  484 */         enterPanel.add(this.oklabel);
/*  485 */         enterPanel.add(this.yorn);
/*  486 */         this.operatePanel.add(enterPanel);
/*      */         
/*  488 */         if (this.step == 13)
/*      */         {
/*  490 */           this.controller.solver.reset();
/*  491 */           this.state.back();
/*  492 */           this.controller.solver.getData(this.controller.data);
/*      */           
/*  494 */           this.nextbutt.setEnabled(true);
/*      */         }
/*  496 */         this.step = 7;
/*      */         
/*  498 */         revalidate();
/*  499 */         repaint();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  632 */         break;
/*      */       case 8: 
/*  503 */         this.controller.solver.reset();
/*  504 */         this.state.back();
/*  505 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  507 */         inputPanel = new JPanel();
/*  508 */         JPanel rightPanel = new JPanel();
/*  509 */         rightPanel.setLayout(new BoxLayout(rightPanel, 1));
/*  510 */         enterPanel = new JPanel();
/*  511 */         this.indilabel.setText(this.str_choose_enter);
/*  512 */         this.indilabel.setLabelFor(enterPanel);
/*  513 */         this.dylabel.setText("Entering basic arc:");
/*  514 */         enterPanel.add(this.dylabel);
/*  515 */         enterPanel.add(this.selarc);
/*  516 */         rightPanel.add(this.indilabel);
/*  517 */         rightPanel.add(enterPanel);
/*  518 */         inputPanel.add(this.scrlPane);
/*  519 */         inputPanel.add(Box.createHorizontalStrut(20));
/*  520 */         inputPanel.add(rightPanel);
/*  521 */         this.operatePanel.removeAll();
/*  522 */         this.operatePanel.add(inputPanel);
/*  523 */         this.canvas.setDrawLoopInfo(false);
/*  524 */         this.step = 8;
/*      */         
/*  526 */         revalidate();
/*  527 */         repaint();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  632 */         break;
/*      */       case 9: 
/*  531 */         this.controller.solver.reset();
/*  532 */         this.state.back();
/*  533 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  535 */         enterPanel = new JPanel();
/*  536 */         this.indilabel.setText(this.str_choose_leave);
/*  537 */         this.dylabel.setText(" Leaving basic arc:");
/*  538 */         enterPanel.add(this.dylabel);
/*  539 */         enterPanel.add(this.selarc);
/*  540 */         enterPanel.add(Box.createHorizontalStrut(20));
/*  541 */         enterPanel.add(this.xlabel);
/*  542 */         enterPanel.add(this.xval);
/*  543 */         this.operatePanel.removeAll();
/*  544 */         this.operatePanel.add(this.indilabel, "North");
/*  545 */         this.operatePanel.add(enterPanel, "Center");
/*  546 */         this.canvas.setDrawLoopInfo(true);
/*  547 */         this.step = 9;
/*      */         
/*      */ 
/*  550 */         this.canvas.setDrawFlowInfo(true);
/*  551 */         revalidate();
/*  552 */         repaint();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  632 */         break;
/*      */       case 10: 
/*  556 */         this.controller.solver.reset();
/*  557 */         this.state.back();
/*  558 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  560 */         if (this.leaving_arcs.isEmpty() == false) {
/*  561 */           leavearc = (String)this.leaving_arcs.lastElement();
/*  562 */           this.leaving_arcs.removeElementAt(this.leaving_arcs.size() - 1);
/*      */         }
/*  564 */         this.indilabel.setText(String.valueOf(String.valueOf(new StringBuffer("Does the arc ").append(leavearc).append(" need to be reversed?"))));
/*  565 */         this.operatePanel.removeAll();
/*  566 */         enterPanel = new JPanel();
/*  567 */         enterPanel.add(this.indilabel);
/*  568 */         enterPanel.add(this.yorn);
/*  569 */         this.operatePanel.add(enterPanel);
/*  570 */         this.step = 10;
/*      */         
/*  572 */         revalidate();
/*  573 */         repaint();
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
/*      */ 
/*      */ 
/*  632 */         break;
/*      */       case 11: 
/*  577 */         this.controller.solver.reset();
/*  578 */         this.state.back();
/*  579 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  581 */         this.statetext.setText(this.str_iterate_complete);
/*      */         
/*  583 */         this.operatePanel.removeAll();
/*  584 */         this.operatePanel.add(this.statetext);
/*  585 */         this.step = 11;
/*      */         
/*  587 */         revalidate();
/*  588 */         repaint();
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
/*      */ 
/*  632 */         break;
/*      */       case 12: 
/*  592 */         this.controller.solver.reset();
/*  593 */         this.state.back();
/*  594 */         this.controller.solver.getData(this.controller.data);
/*      */         
/*  596 */         inputPanel = new JPanel();
/*  597 */         this.dylabel = new JLabel(this.str_enter_reverse_flow);
/*  598 */         this.dylabel.setAlignmentX(0.5F);
/*  599 */         inputPanel.setLayout(new BoxLayout(inputPanel, 1));
/*  600 */         inputPanel.add(this.dylabel);
/*  601 */         for (int i = 0; i < this.nArcs; i++) {
/*  602 */           if (this.controller.data.arcs[i].is_reversed) {
/*  603 */             String orgname = "";
/*  604 */             String revname = this.controller.data.arcs[i].getArcName();
/*  605 */             orgname = String.valueOf(String.valueOf(orgname)).concat(String.valueOf(String.valueOf(revname.charAt(1))));
/*  606 */             orgname = String.valueOf(String.valueOf(orgname)).concat(String.valueOf(String.valueOf(revname.charAt(0))));
/*  607 */             this.arcname[i].setText(String.valueOf(String.valueOf(new StringBuffer("Flow through arc ").append(orgname).append(" = "))));
/*  608 */             this.arcname[i].setLabelFor(this.arcflow[i]);
/*  609 */             this.flowPanel[i].add(this.arcname[i]);
/*  610 */             this.flowPanel[i].add(this.arcflow[i]);
/*  611 */             this.flowPanel[i].setPreferredSize(new Dimension(300, 35));
/*  612 */             this.flowPanel[i].setMinimumSize(new Dimension(300, 35));
/*  613 */             this.flowPanel[i].setMaximumSize(new Dimension(300, 35));
/*  614 */             inputPanel.add(Box.createRigidArea(new Dimension(0, 2)));
/*  615 */             inputPanel.add(this.flowPanel[i]);
/*      */           }
/*      */         }
/*  618 */         this.operatePanel.removeAll();
/*  619 */         this.operatePanel.add(inputPanel);
/*  620 */         this.nextbutt.setEnabled(true);
/*  621 */         this.step = 12;
/*      */         
/*  623 */         revalidate();
/*  624 */         repaint();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  632 */         break;
/*      */       default: 
/*  627 */         System.out.println("system cannot back here");
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  632 */         break;
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */   private void doNext()
/*      */   {
/*  640 */     String addarc = new String();String eqz = new String("ΔZ = ");
/*      */     
/*      */ 
/*  643 */     String[] basicSelected = new String[this.nBasic];
/*  644 */     String[] reverseSelected = new String[this.nArcs];
/*  645 */     double[] initflow = new double[this.nBasic];
/*  646 */     double[] revsflow = new double[this.nArcs];
/*  647 */     JPanel inputPanel = null;
/*  648 */     JPanel enterPanel = null;
/*      */     
/*  650 */     Vector par = new Vector();
/*  651 */     IOROperation opr = new IOROperation(1, par);
/*      */     
/*  653 */     switch (this.step)
/*      */     {
/*      */     case 1: 
/*  656 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*  658 */       this.operatePanel.removeAll();
/*  659 */       this.operatePanel.add(this.basiclistPanel);
/*      */       
/*  661 */       this.canvas.setDrawCostInfo(false);
/*  662 */       this.canvas.setDrawFlowInfo(true);
/*  663 */       this.step = 2;
/*      */       
/*  665 */       revalidate();
/*  666 */       repaint();
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
/*      */ 
/*      */ 
/* 1215 */       break;
/*      */     case 2: 
/*  670 */       if (this.basiclistPanel.basicModel.size() != this.nBasic) {
/*  671 */         JOptionPane.showMessageDialog(this, "The number of basic arcs is not correct");
/*      */       }
/*      */       else {
/*  674 */         for (int i = 0; i < this.nBasic; i++) {
/*  675 */           basicSelected[i] = ((String)this.basiclistPanel.basicModel.getElementAt(i));
/*      */         }
/*  677 */         par.addElement(basicSelected);
/*  678 */         opr.operation_code = 30101;
/*  679 */         opr.parameters = par;
/*  680 */         if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  681 */           String err = this.controller.solver.getErrInfo();
/*  682 */           JOptionPane.showMessageDialog(this, err);
/*      */         }
/*      */         else
/*      */         {
/*  686 */           this.state.addStep(opr);
/*      */           
/*  688 */           this.opseq.addElement(new Integer(this.step));
/*      */           
/*      */ 
/*  691 */           this.operatePanel.remove(this.basiclistPanel);
/*  692 */           this.operatePanel.add(this.reverselistPanel);
/*      */           
/*  694 */           this.canvas.setDrawCostInfo(false);
/*  695 */           this.canvas.setDrawFlowInfo(true);
/*  696 */           this.step = 3;
/*      */           
/*  698 */           revalidate();
/*  699 */           repaint();
/*      */         }
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1215 */       break;
/*      */     case 3: 
/*  703 */       if (this.reverselistPanel.reverseModel.size() > this.nDash) {
/*  704 */         JOptionPane.showMessageDialog(this, "The number of reverse arcs is not correct");
/*      */       }
/*      */       else {
/*  707 */         for (int i = 0; i < this.reverselistPanel.reverseModel.size(); i++) {
/*  708 */           String orgname = "";
/*  709 */           String revname = (String)this.reverselistPanel.reverseModel.getElementAt(i);
/*  710 */           orgname = String.valueOf(String.valueOf(orgname)).concat(String.valueOf(String.valueOf(revname.charAt(1))));
/*  711 */           orgname = String.valueOf(String.valueOf(orgname)).concat(String.valueOf(String.valueOf(revname.charAt(0))));
/*  712 */           reverseSelected[i] = orgname;
/*      */         }
/*      */         
/*  715 */         par.addElement(new Integer(this.reverselistPanel.reverseModel.size()));
/*  716 */         par.addElement(reverseSelected);
/*  717 */         opr.operation_code = 30102;
/*  718 */         opr.parameters = par;
/*  719 */         if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  720 */           String err = this.controller.solver.getErrInfo();
/*  721 */           JOptionPane.showMessageDialog(this, err);
/*      */         }
/*      */         else
/*      */         {
/*  725 */           this.state.addStep(opr);
/*      */           
/*  727 */           this.opseq.addElement(new Integer(this.step));
/*      */           
/*      */ 
/*  730 */           inputPanel = new JPanel();
/*  731 */           this.dylabel.setText(this.str_enter_init_flow);
/*  732 */           this.dylabel.setAlignmentX(0.5F);
/*  733 */           inputPanel.setLayout(new BoxLayout(inputPanel, 1));
/*  734 */           inputPanel.add(this.dylabel);
/*  735 */           inputPanel.add(Box.createVerticalStrut(10));
/*  736 */           for (i = 0; i < this.nArcs; i++) {
/*  737 */             if (this.controller.data.arcs[i].is_basic) {
/*  738 */               this.arcname[i].setText(String.valueOf(String.valueOf(new StringBuffer("The initial flow for ").append(this.controller.data.arcs[i].getArcName()).append(" = "))));
/*  739 */               this.arcname[i].setLabelFor(this.arcflow[i]);
/*  740 */               this.arcflow[i].setValue(0.0D);
/*  741 */               this.flowPanel[i].add(this.arcname[i]);
/*  742 */               this.flowPanel[i].add(this.arcflow[i]);
/*  743 */               this.flowPanel[i].setPreferredSize(new Dimension(300, 35));
/*  744 */               this.flowPanel[i].setMinimumSize(new Dimension(300, 35));
/*  745 */               this.flowPanel[i].setMaximumSize(new Dimension(300, 35));
/*  746 */               inputPanel.add(Box.createRigidArea(new Dimension(0, 2)));
/*  747 */               inputPanel.add(this.flowPanel[i]);
/*      */             }
/*      */           }
/*  750 */           this.operatePanel.removeAll();
/*  751 */           this.operatePanel.add(inputPanel);
/*      */           
/*  753 */           this.canvas.setDrawCostInfo(false);
/*  754 */           this.canvas.setDrawFlowInfo(true);
/*  755 */           this.step = 4;
/*      */           
/*  757 */           revalidate();
/*  758 */           repaint();
/*      */         }
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1215 */       break;
/*      */     case 4: 
/*  762 */       int k = 0;
/*  763 */       for (int i = 0; i < this.nArcs; i++)
/*  764 */         if (this.controller.data.arcs[i].is_basic) {
/*  765 */           initflow[k] = this.arcflow[i].getValue();
/*  766 */           basicSelected[k] = this.controller.data.arcs[i].getArcName();
/*  767 */           k++;
/*  768 */           if (k == this.nBasic)
/*      */             break;
/*      */         }
/*  771 */       par.addElement(basicSelected);
/*  772 */       par.addElement(initflow);
/*  773 */       opr.operation_code = 30103;
/*  774 */       opr.parameters = par;
/*  775 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  776 */         String err = this.controller.solver.getErrInfo();
/*  777 */         JOptionPane.showMessageDialog(this, err);
/*      */       }
/*      */       else
/*      */       {
/*  781 */         this.state.addStep(opr);
/*      */         
/*  783 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/*  786 */         int itcnt = 0;
/*  787 */         for (i = 0; i < this.nArcs; i++) {
/*  788 */           if (this.controller.data.arcs[i].is_basic == false) {
/*  789 */             itcnt++;
/*      */             
/*  791 */             if (itcnt == this.next_select_nonbasic) {
/*  792 */               addarc = this.controller.data.arcs[i].getArcName();
/*  793 */               this.current_nonbasic = addarc;
/*  794 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  799 */         this.str_enter_cycle = String.valueOf(String.valueOf(new StringBuffer("Suppose arc ").append(addarc).append(" is added to the feasible spanning tree. Then identify the undirected cycle created, \n").append("beginning with node ").append(addarc.charAt(0)).append(" and ").append(addarc.charAt(1)).append(" and ending with node ").append(addarc.charAt(0)).append(".")));
/*      */         
/*  801 */         this.statetext.setText(this.str_enter_cycle);
/*  802 */         this.indilabel.setText(String.valueOf(String.valueOf(new StringBuffer("Cycle created when arc ").append(addarc).append(" is added to the spanning tree:"))));
/*  803 */         this.indilabel.setLabelFor(this.cyclename);
/*  804 */         inputPanel = new JPanel();
/*      */         
/*  806 */         inputPanel.add(this.indilabel);
/*  807 */         inputPanel.add(this.cyclename);
/*  808 */         this.operatePanel.removeAll();
/*  809 */         this.operatePanel.add(this.statetext);
/*  810 */         this.operatePanel.add(Box.createVerticalStrut(10));
/*  811 */         this.operatePanel.add(inputPanel);
/*      */         
/*  813 */         this.canvas.setDrawCostInfo(false);
/*  814 */         this.canvas.setDrawFlowInfo(true);
/*      */         
/*  816 */         this.step = 5;
/*      */         
/*  818 */         revalidate();
/*  819 */         repaint();
/*      */       }
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
/*      */ 
/*      */ 
/* 1215 */       break;
/*      */     case 5: 
/*  823 */       String loop = this.cyclename.getText();
/*  824 */       par.addElement(new Integer(this.next_select_nonbasic));
/*  825 */       par.addElement(loop);
/*  826 */       opr.operation_code = 30104;
/*  827 */       opr.parameters = par;
/*  828 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/*  829 */         String err = this.controller.solver.getErrInfo();
/*  830 */         JOptionPane.showMessageDialog(this, err);
/*      */       }
/*      */       else
/*      */       {
/*  834 */         this.state.addStep(opr);
/*      */         
/*  836 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/*  839 */         this.str_get_deltaz = String.valueOf(String.valueOf(new StringBuffer("Suppose arc ").append(this.current_nonbasic).append(" with ").append('θ').append(" is added to the feasible spanning tree. The above network shows next to\n").append("each arc the resulting incremental flow and the cost per unit flow.")));
/*      */         
/*  841 */         this.statetext.setText(this.str_get_deltaz);
/*  842 */         this.indilabel.setText("When θ = 1, the resulting overall increment in total cost (Z) is:");
/*      */         
/*  844 */         for (int i = 0; i < this.controller.data.num_segment; i++) {
/*  845 */           String ns = this.decfm.format(this.controller.data.segment_inc[i]);
/*  846 */           if (this.controller.data.segment_inc[i] > -0.001D) {
/*  847 */             eqz = String.valueOf(String.valueOf(eqz)).concat(" + ");
/*      */           }
/*  849 */           eqz = String.valueOf(String.valueOf(eqz)).concat(String.valueOf(String.valueOf(ns)));
/*      */         }
/*  851 */         String ns = this.decfm.format(this.controller.data.total_inc);
/*  852 */         eqz = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(eqz))).append(" = ").append(ns)));
/*  853 */         this.resultlabel.setText(eqz);
/*  854 */         this.operatePanel.removeAll();
/*  855 */         this.indilabel.setAlignmentX(0.5F);
/*  856 */         this.resultlabel.setAlignmentX(0.5F);
/*  857 */         this.operatePanel.add(this.statetext);
/*  858 */         this.operatePanel.add(Box.createRigidArea(new Dimension(0, 20)));
/*  859 */         this.operatePanel.add(this.indilabel);
/*  860 */         this.operatePanel.add(Box.createRigidArea(new Dimension(0, 5)));
/*  861 */         this.operatePanel.add(this.resultlabel);
/*      */         
/*  863 */         this.next_select_nonbasic += 1;
/*      */         
/*  865 */         this.canvas.setDrawCostInfo(true);
/*  866 */         this.canvas.setDrawFlowInfo(false);
/*  867 */         this.canvas.setDrawLoopInfo(true);
/*  868 */         this.step = 6;
/*      */         
/*  870 */         revalidate();
/*  871 */         repaint();
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1215 */       break;
/*      */     case 6: 
/*  874 */       if (this.next_select_nonbasic > this.nArcs - this.nBasic)
/*      */       {
/*  876 */         opr.operation_code = 30108;
/*  877 */         opr.parameters = par;
/*  878 */         this.controller.solver.doWork(opr, this.controller.data);
/*      */         
/*  880 */         this.state.addStep(opr);
/*      */         
/*  882 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/*  885 */         this.deltaModel.fireTableDataChanged();
/*  886 */         this.operatePanel.removeAll();
/*  887 */         enterPanel = new JPanel();
/*  888 */         enterPanel.add(this.scrlPane);
/*  889 */         enterPanel.add(Box.createHorizontalStrut(30));
/*  890 */         enterPanel.add(this.oklabel);
/*  891 */         enterPanel.add(this.yorn);
/*  892 */         this.operatePanel.add(enterPanel);
/*      */         
/*  894 */         this.canvas.setDrawCostInfo(false);
/*  895 */         this.canvas.setDrawFlowInfo(true);
/*  896 */         this.canvas.setDrawLoopInfo(false);
/*      */         
/*  898 */         this.step = 7;
/*      */       }
/*      */       else
/*      */       {
/*  902 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*  904 */         int itcnt = 0;
/*  905 */         for (int i = 0; i < this.nArcs; i++) {
/*  906 */           if (this.controller.data.arcs[i].is_basic == false) {
/*  907 */             itcnt++;
/*  908 */             if (itcnt == this.next_select_nonbasic) {
/*  909 */               addarc = this.controller.data.arcs[i].getArcName();
/*  910 */               this.current_nonbasic = addarc;
/*  911 */               break;
/*      */             }
/*      */           }
/*      */         }
/*  915 */         this.str_enter_cycle = String.valueOf(String.valueOf(new StringBuffer("Suppose arc ").append(addarc).append(" is added to the feasible spanning tree. Then identify the undirected cycle created,\n").append("beginning with node ").append(addarc.charAt(0)).append(" and ").append(addarc.charAt(1)).append(" and ending with node ").append(addarc.charAt(0)).append(".")));
/*      */         
/*  917 */         this.statetext.setText(this.str_enter_cycle);
/*  918 */         this.indilabel.setText(String.valueOf(String.valueOf(new StringBuffer("Cycle created when arc ").append(addarc).append(" is added to the spanning tree:"))));
/*  919 */         this.indilabel.setLabelFor(this.cyclename);
/*  920 */         inputPanel = new JPanel();
/*      */         
/*  922 */         inputPanel.add(this.indilabel);
/*  923 */         inputPanel.add(this.cyclename);
/*  924 */         this.operatePanel.removeAll();
/*  925 */         this.operatePanel.add(this.statetext);
/*  926 */         this.operatePanel.add(Box.createVerticalStrut(20));
/*  927 */         this.operatePanel.add(inputPanel);
/*      */         
/*  929 */         this.canvas.setDrawCostInfo(false);
/*  930 */         this.canvas.setDrawFlowInfo(true);
/*  931 */         this.canvas.setDrawLoopInfo(false);
/*      */         
/*  933 */         this.step = 5;
/*      */       }
/*      */       
/*  936 */       revalidate();
/*  937 */       repaint();
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
/* 1215 */       break;
/*      */     case 7: 
/*  941 */       this.opseq.addElement(new Integer(this.step));
/*      */       
/*  943 */       char sel = this.yorn.getText().charAt(0);
/*  944 */       if ((sel == 'y') || (sel == 'Y')) {
/*  945 */         int revnum = 0;
/*  946 */         inputPanel = new JPanel();
/*  947 */         this.dylabel.setText(this.str_enter_reverse_flow);
/*  948 */         this.dylabel.setAlignmentX(0.5F);
/*  949 */         inputPanel.setLayout(new BoxLayout(inputPanel, 1));
/*  950 */         inputPanel.add(this.dylabel);
/*  951 */         inputPanel.add(Box.createVerticalStrut(10));
/*  952 */         for (int i = 0; i < this.nArcs; i++) {
/*  953 */           if (this.controller.data.arcs[i].is_reversed) {
/*  954 */             revnum++;
/*  955 */             String orgname = "";
/*  956 */             String revname = this.controller.data.arcs[i].getArcName();
/*  957 */             orgname = String.valueOf(String.valueOf(orgname)).concat(String.valueOf(String.valueOf(revname.charAt(1))));
/*  958 */             orgname = String.valueOf(String.valueOf(orgname)).concat(String.valueOf(String.valueOf(revname.charAt(0))));
/*  959 */             this.arcname[i].setText(String.valueOf(String.valueOf(new StringBuffer("Flow through arc ").append(orgname).append(" = "))));
/*  960 */             this.arcname[i].setLabelFor(this.arcflow[i]);
/*  961 */             this.arcflow[i].setValue(0.0D);
/*  962 */             this.flowPanel[i].add(this.arcname[i]);
/*  963 */             this.flowPanel[i].add(this.arcflow[i]);
/*  964 */             this.flowPanel[i].setPreferredSize(new Dimension(300, 35));
/*  965 */             this.flowPanel[i].setMinimumSize(new Dimension(300, 35));
/*  966 */             this.flowPanel[i].setMaximumSize(new Dimension(300, 35));
/*  967 */             inputPanel.add(Box.createRigidArea(new Dimension(0, 2)));
/*  968 */             inputPanel.add(this.flowPanel[i]);
/*      */           }
/*      */         }
/*  971 */         if (revnum > 0) {
/*  972 */           this.operatePanel.removeAll();
/*  973 */           this.operatePanel.add(inputPanel);
/*      */           
/*  975 */           this.canvas.setDrawCostInfo(false);
/*  976 */           this.canvas.setDrawFlowInfo(true);
/*  977 */           this.step = 12;
/*      */         }
/*      */         else
/*      */         {
/*  981 */           opr.operation_code = 30111;
/*  982 */           opr.parameters = par;
/*  983 */           this.controller.solver.doWork(opr, this.controller.data);
/*      */           
/*      */ 
/*  986 */           this.state.addStep(opr);
/*      */           
/*      */ 
/*  989 */           this.indilabel.setText(this.str_problem_end);
/*  990 */           this.resultlabel.setText("Total Cost: Z = ".concat(String.valueOf(String.valueOf(this.controller.data.Z))));
/*  991 */           this.operatePanel.removeAll();
/*  992 */           this.operatePanel.add(this.indilabel);
/*  993 */           this.operatePanel.add(Box.createVerticalStrut(10));
/*  994 */           this.operatePanel.add(this.resultlabel);
/*  995 */           this.nextbutt.setEnabled(false);
/*      */           
/*  997 */           this.canvas.setDrawCostInfo(false);
/*  998 */           this.canvas.setDrawFlowInfo(true);
/*  999 */           this.step = 13;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1004 */         inputPanel = new JPanel();
/* 1005 */         JPanel rightPanel = new JPanel();
/* 1006 */         rightPanel.setLayout(new BoxLayout(rightPanel, 1));
/* 1007 */         enterPanel = new JPanel();
/* 1008 */         this.indilabel.setText(this.str_choose_enter);
/* 1009 */         this.indilabel.setLabelFor(enterPanel);
/* 1010 */         this.dylabel.setText("Entering basic arc:");
/* 1011 */         enterPanel.add(this.dylabel);
/* 1012 */         enterPanel.add(this.selarc);
/* 1013 */         rightPanel.add(this.indilabel);
/* 1014 */         rightPanel.add(enterPanel);
/* 1015 */         inputPanel.add(this.scrlPane);
/* 1016 */         inputPanel.add(Box.createHorizontalStrut(20));
/* 1017 */         inputPanel.add(rightPanel);
/* 1018 */         this.operatePanel.removeAll();
/* 1019 */         this.operatePanel.add(inputPanel);
/*      */         
/* 1021 */         this.canvas.setDrawCostInfo(false);
/* 1022 */         this.canvas.setDrawFlowInfo(true);
/*      */         
/* 1024 */         this.step = 8;
/*      */       }
/*      */       
/*      */ 
/* 1028 */       revalidate();
/* 1029 */       repaint();
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
/*      */ 
/*      */ 
/*      */ 
/* 1215 */       break;
/*      */     case 8: 
/* 1033 */       par.addElement(this.selarc.getText());
/* 1034 */       opr.operation_code = 30105;
/* 1035 */       opr.parameters = par;
/* 1036 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1037 */         String err = this.controller.solver.getErrInfo();
/* 1038 */         JOptionPane.showMessageDialog(this, err);
/*      */       }
/*      */       else
/*      */       {
/* 1042 */         this.state.addStep(opr);
/*      */         
/* 1044 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1047 */         enterPanel = new JPanel();
/* 1048 */         this.indilabel.setText(this.str_choose_leave);
/* 1049 */         this.dylabel.setText("Leaving basic arc:");
/* 1050 */         enterPanel.add(this.dylabel);
/* 1051 */         enterPanel.add(this.selarc);
/* 1052 */         enterPanel.add(Box.createHorizontalStrut(20));
/* 1053 */         enterPanel.add(this.xlabel);
/* 1054 */         enterPanel.add(this.xval);
/* 1055 */         this.operatePanel.removeAll();
/* 1056 */         this.operatePanel.add(this.indilabel);
/* 1057 */         this.operatePanel.add(enterPanel);
/*      */         
/* 1059 */         this.canvas.setDrawCostInfo(false);
/* 1060 */         this.canvas.setDrawFlowInfo(true);
/* 1061 */         this.canvas.setDrawLoopInfo(true);
/* 1062 */         this.step = 9;
/*      */         
/* 1064 */         revalidate();
/* 1065 */         repaint();
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1215 */       break;
/*      */     case 9: 
/* 1069 */       par.addElement(this.selarc.getText());
/* 1070 */       par.addElement(new Double(this.xval.getValue()));
/* 1071 */       opr.operation_code = 30106;
/* 1072 */       opr.parameters = par;
/* 1073 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1074 */         String err = this.controller.solver.getErrInfo();
/* 1075 */         JOptionPane.showMessageDialog(this, err);
/*      */       }
/*      */       else
/*      */       {
/* 1079 */         this.state.addStep(opr);
/*      */         
/* 1081 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/* 1083 */         String leavearc = new String(this.selarc.getText().toUpperCase(Locale.US));
/* 1084 */         this.leaving_arcs.addElement(leavearc);
/* 1085 */         this.indilabel.setText(String.valueOf(String.valueOf(new StringBuffer("Does the arc ").append(leavearc).append(" need to be reversed?"))));
/* 1086 */         this.operatePanel.removeAll();
/* 1087 */         enterPanel = new JPanel();
/* 1088 */         enterPanel.add(this.indilabel);
/* 1089 */         enterPanel.add(this.yorn);
/* 1090 */         this.operatePanel.add(enterPanel);
/*      */         
/* 1092 */         this.canvas.setDrawCostInfo(false);
/* 1093 */         this.canvas.setDrawFlowInfo(true);
/* 1094 */         this.canvas.setDrawLoopInfo(false);
/* 1095 */         this.step = 10;
/*      */         
/* 1097 */         revalidate();
/* 1098 */         repaint();
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/* 1215 */       break;
/*      */     case 10: 
/* 1101 */       char sel = this.yorn.getText().charAt(0);
/*      */       
/* 1103 */       if ((sel == 'y') || (sel == 'Y')) {
/* 1104 */         par.addElement(new Boolean(true));
/*      */       } else {
/* 1106 */         par.addElement(new Boolean(false));
/*      */       }
/* 1108 */       opr.operation_code = 30107;
/* 1109 */       opr.parameters = par;
/* 1110 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1111 */         String err = this.controller.solver.getErrInfo();
/* 1112 */         JOptionPane.showMessageDialog(this, err);
/*      */       }
/*      */       else
/*      */       {
/* 1116 */         this.state.addStep(opr);
/*      */         
/* 1118 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1121 */         this.statetext.setText(this.str_iterate_complete);
/* 1122 */         this.operatePanel.removeAll();
/* 1123 */         this.operatePanel.add(this.statetext);
/* 1124 */         this.operatePanel.add(Box.createVerticalGlue());
/*      */         
/* 1126 */         this.canvas.setDrawCostInfo(false);
/* 1127 */         this.canvas.setDrawFlowInfo(true);
/*      */         
/* 1129 */         this.step = 11;
/*      */         
/* 1131 */         revalidate();
/* 1132 */         repaint();
/*      */       }
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
/* 1215 */       break;
/*      */     case 11: 
/* 1136 */       opr.operation_code = 30108;
/* 1137 */       opr.parameters = par;
/* 1138 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1139 */         String err = this.controller.solver.getErrInfo();
/* 1140 */         JOptionPane.showMessageDialog(this, err);
/*      */       }
/*      */       else
/*      */       {
/* 1144 */         this.state.addStep(opr);
/*      */         
/* 1146 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1149 */         this.deltaModel.fireTableDataChanged();
/* 1150 */         this.operatePanel.removeAll();
/* 1151 */         enterPanel = new JPanel();
/* 1152 */         enterPanel.add(this.scrlPane);
/* 1153 */         enterPanel.add(Box.createHorizontalStrut(30));
/* 1154 */         enterPanel.add(this.oklabel);
/* 1155 */         enterPanel.add(this.yorn);
/* 1156 */         this.operatePanel.add(enterPanel);
/*      */         
/* 1158 */         this.canvas.setDrawCostInfo(false);
/* 1159 */         this.canvas.setDrawFlowInfo(true);
/*      */         
/* 1161 */         this.step = 7;
/*      */         
/* 1163 */         revalidate();
/* 1164 */         repaint();
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1215 */       break;
/*      */     case 12: 
/* 1168 */       int k = 0;
/* 1169 */       for (int i = 0; i < this.nArcs; i++) {
/* 1170 */         if (this.controller.data.arcs[i].is_reversed) {
/* 1171 */           reverseSelected[k] = this.controller.data.arcs[i].getArcName();
/* 1172 */           revsflow[k] = this.arcflow[i].getValue();
/* 1173 */           k++;
/*      */         }
/*      */       }
/* 1176 */       par.addElement(new Integer(k));
/* 1177 */       par.addElement(reverseSelected);
/* 1178 */       par.addElement(revsflow);
/* 1179 */       opr.operation_code = 30109;
/* 1180 */       opr.parameters = par;
/* 1181 */       if (this.controller.solver.doWork(opr, this.controller.data) == false) {
/* 1182 */         String err = this.controller.solver.getErrInfo();
/* 1183 */         JOptionPane.showMessageDialog(this, err);
/*      */       }
/*      */       else
/*      */       {
/* 1187 */         this.state.addStep(opr);
/*      */         
/* 1189 */         this.opseq.addElement(new Integer(this.step));
/*      */         
/*      */ 
/* 1192 */         this.indilabel.setText(this.str_problem_end);
/* 1193 */         this.resultlabel.setText("Total Cost: Z = ".concat(String.valueOf(String.valueOf(this.controller.data.Z))));
/* 1194 */         this.operatePanel.removeAll();
/* 1195 */         this.operatePanel.add(this.indilabel);
/* 1196 */         this.operatePanel.add(Box.createVerticalStrut(10));
/* 1197 */         this.operatePanel.add(this.resultlabel);
/* 1198 */         this.nextbutt.setEnabled(false);
/*      */         
/* 1200 */         this.canvas.setDrawCostInfo(false);
/* 1201 */         this.canvas.setDrawFlowInfo(true);
/*      */         
/* 1203 */         this.step = 13;
/*      */         
/* 1205 */         revalidate();
/* 1206 */         repaint();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1215 */       break;
/*      */     case 13: 
/* 1209 */       this.step += 1;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1215 */       break;
/*      */     default: 
/* 1212 */       System.out.println("program cannot reach here");
/*      */       
/*      */ 
/* 1215 */       break;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void LoadFile(ObjectInputStream in)
/*      */   {
/* 1226 */     int[] para = new int[2];
/* 1227 */     String eqz = new String("ΔZ = ");String addarc = " ";
/*      */     
/* 1229 */     JPanel inputPanel = null;JPanel enterPanel = null;JPanel rightPanel = null;
/*      */     try
/*      */     {
/* 1232 */       this.state = ((IORState)in.readObject());
/* 1233 */       para = (int[])in.readObject();
/* 1234 */       this.current_nonbasic = ((String)in.readObject());
/* 1235 */       this.opseq = ((Vector)in.readObject());
/* 1236 */       this.leaving_arcs = ((Vector)in.readObject());
/* 1237 */       this.state.setSolverStepVector();
/*      */     }
/*      */     catch (Exception e1) {
/* 1240 */       e1.printStackTrace();
/* 1241 */       System.out.println("Open fails");
/*      */     }
/*      */     
/* 1244 */     this.step = para[0];
/* 1245 */     this.next_select_nonbasic = para[1];
/*      */     
/* 1247 */     this.mainPanel.removeAll();
/* 1248 */     this.mainPanel.add(this.upPanel, "Center");
/* 1249 */     this.mainPanel.add(this.lowPanel, "South");
/* 1250 */     switch (this.step) {
/*      */     case 1: 
/*      */       break;
/*      */     case 2: 
/* 1254 */       this.operatePanel.removeAll();
/* 1255 */       this.operatePanel.add(this.basiclistPanel);
/*      */       
/* 1257 */       this.canvas.setDrawCostInfo(false);
/* 1258 */       this.canvas.setDrawFlowInfo(true);
/* 1259 */       break;
/*      */     case 3: 
/* 1261 */       this.operatePanel.remove(this.basiclistPanel);
/* 1262 */       this.operatePanel.add(this.reverselistPanel);
/*      */       
/* 1264 */       this.canvas.setDrawCostInfo(false);
/* 1265 */       this.canvas.setDrawFlowInfo(true);
/* 1266 */       break;
/*      */     case 4: 
/* 1268 */       this.dylabel.setText(this.str_enter_init_flow);
/* 1269 */       inputPanel = new JPanel();
/* 1270 */       inputPanel.setLayout(new BoxLayout(inputPanel, 1));
/* 1271 */       inputPanel.add(this.dylabel);
/* 1272 */       inputPanel.add(Box.createVerticalStrut(10));
/* 1273 */       for (int i = 0; i < this.nArcs; i++) {
/* 1274 */         if (this.controller.data.arcs[i].is_basic) {
/* 1275 */           this.arcname[i].setText(String.valueOf(String.valueOf(new StringBuffer("The initial flow for ").append(this.controller.data.arcs[i].getArcName()).append(" = "))));
/* 1276 */           this.arcname[i].setLabelFor(this.arcflow[i]);
/* 1277 */           this.flowPanel[i].add(this.arcname[i]);
/* 1278 */           this.flowPanel[i].add(this.arcflow[i]);
/* 1279 */           this.flowPanel[i].setPreferredSize(new Dimension(300, 35));
/* 1280 */           this.flowPanel[i].setMinimumSize(new Dimension(300, 35));
/* 1281 */           this.flowPanel[i].setMaximumSize(new Dimension(300, 35));
/* 1282 */           inputPanel.add(Box.createRigidArea(new Dimension(0, 2)));
/* 1283 */           inputPanel.add(this.flowPanel[i]);
/*      */         }
/*      */       }
/* 1286 */       this.operatePanel.removeAll();
/* 1287 */       this.operatePanel.add(inputPanel);
/*      */       
/* 1289 */       this.canvas.setDrawCostInfo(false);
/* 1290 */       this.canvas.setDrawFlowInfo(true);
/* 1291 */       break;
/*      */     
/*      */     case 5: 
/* 1294 */       int itcnt = 0;
/* 1295 */       for (int i = 0; i < this.nArcs; i++) {
/* 1296 */         if (this.controller.data.arcs[i].is_basic == false) {
/* 1297 */           itcnt++;
/*      */           
/* 1299 */           if (itcnt == this.next_select_nonbasic) {
/* 1300 */             addarc = this.controller.data.arcs[i].getArcName();
/* 1301 */             this.current_nonbasic = addarc;
/* 1302 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1307 */       this.str_enter_cycle = String.valueOf(String.valueOf(new StringBuffer("Suppose arc ").append(addarc).append(" is added to the feasible spanning tree. Then identify the undirected cycle created, \n").append("beginning with node ").append(addarc.charAt(0)).append(" and ").append(addarc.charAt(1)).append(" and ending with node ").append(addarc.charAt(0)).append(".")));
/*      */       
/* 1309 */       this.statetext.setText(this.str_enter_cycle);
/* 1310 */       this.indilabel.setText(String.valueOf(String.valueOf(new StringBuffer("Cycle created when arc ").append(addarc).append(" is added to the spanning tree:"))));
/* 1311 */       this.indilabel.setLabelFor(this.cyclename);
/* 1312 */       inputPanel = new JPanel();
/* 1313 */       inputPanel.add(this.indilabel);
/* 1314 */       inputPanel.add(this.cyclename);
/* 1315 */       this.operatePanel.removeAll();
/* 1316 */       this.operatePanel.add(this.statetext);
/* 1317 */       this.operatePanel.add(Box.createVerticalStrut(10));
/* 1318 */       this.operatePanel.add(inputPanel);
/*      */       
/* 1320 */       this.canvas.setDrawCostInfo(false);
/* 1321 */       this.canvas.setDrawFlowInfo(true);
/* 1322 */       break;
/*      */     
/*      */     case 6: 
/* 1325 */       this.str_get_deltaz = String.valueOf(String.valueOf(new StringBuffer("Suppose arc ").append(this.current_nonbasic).append(" with ").append('θ').append(" is added to the feasible spanning tree. The above network shows next to\n").append("each arc the resulting incremental flow and the cost per unit flow.")));
/*      */       
/* 1327 */       this.statetext.setText(this.str_get_deltaz);
/* 1328 */       this.indilabel.setText("When θ = 1, the resulting overall increment in total cost (Z) is:");
/* 1329 */       for (int i = 0; i < this.controller.data.num_segment; i++) {
/* 1330 */         String ns = this.decfm.format(this.controller.data.segment_inc[i]);
/* 1331 */         if (this.controller.data.segment_inc[i] > -0.001D) {
/* 1332 */           eqz = String.valueOf(String.valueOf(eqz)).concat(" + ");
/*      */         }
/* 1334 */         eqz = String.valueOf(String.valueOf(eqz)).concat(String.valueOf(String.valueOf(ns)));
/*      */       }
/* 1336 */       String ns = this.decfm.format(this.controller.data.total_inc);
/* 1337 */       eqz = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(eqz))).append(" = ").append(ns)));
/* 1338 */       this.resultlabel.setText(eqz);
/* 1339 */       this.indilabel.setAlignmentX(0.5F);
/* 1340 */       this.resultlabel.setAlignmentX(0.5F);
/* 1341 */       this.operatePanel.removeAll();
/* 1342 */       this.operatePanel.add(this.statetext);
/* 1343 */       this.operatePanel.add(Box.createRigidArea(new Dimension(0, 20)));
/* 1344 */       this.operatePanel.add(this.indilabel);
/* 1345 */       this.operatePanel.add(Box.createRigidArea(new Dimension(0, 5)));
/* 1346 */       this.operatePanel.add(this.resultlabel);
/*      */       
/* 1348 */       this.canvas.setDrawCostInfo(true);
/* 1349 */       this.canvas.setDrawFlowInfo(false);
/* 1350 */       this.canvas.setDrawLoopInfo(true);
/* 1351 */       break;
/*      */     
/*      */     case 7: 
/* 1354 */       this.deltaModel.fireTableDataChanged();
/* 1355 */       enterPanel = new JPanel();
/* 1356 */       enterPanel.add(this.scrlPane);
/* 1357 */       enterPanel.add(Box.createHorizontalStrut(30));
/* 1358 */       enterPanel.add(this.oklabel);
/* 1359 */       enterPanel.add(this.yorn);
/* 1360 */       this.operatePanel.removeAll();
/* 1361 */       this.operatePanel.add(enterPanel);
/*      */       
/* 1363 */       this.canvas.setDrawCostInfo(false);
/* 1364 */       this.canvas.setDrawFlowInfo(true);
/* 1365 */       this.canvas.setDrawLoopInfo(false);
/* 1366 */       break;
/*      */     
/*      */     case 8: 
/* 1369 */       inputPanel = new JPanel();
/* 1370 */       rightPanel = new JPanel();
/* 1371 */       rightPanel.setLayout(new BoxLayout(rightPanel, 1));
/* 1372 */       enterPanel = new JPanel();
/* 1373 */       this.indilabel.setText(this.str_choose_enter);
/* 1374 */       this.indilabel.setLabelFor(enterPanel);
/* 1375 */       this.dylabel.setText("Entering basic arc:");
/* 1376 */       enterPanel.add(this.dylabel);
/* 1377 */       enterPanel.add(this.selarc);
/* 1378 */       rightPanel.add(this.indilabel);
/* 1379 */       rightPanel.add(enterPanel);
/* 1380 */       inputPanel.add(this.scrlPane);
/* 1381 */       inputPanel.add(Box.createHorizontalStrut(20));
/* 1382 */       inputPanel.add(rightPanel);
/* 1383 */       this.operatePanel.removeAll();
/* 1384 */       this.operatePanel.add(inputPanel);
/*      */       
/* 1386 */       this.canvas.setDrawCostInfo(false);
/* 1387 */       this.canvas.setDrawFlowInfo(true);
/* 1388 */       break;
/*      */     
/*      */     case 9: 
/* 1391 */       enterPanel = new JPanel();
/* 1392 */       this.indilabel.setText(this.str_choose_leave);
/* 1393 */       this.dylabel.setText("Leaving basic arc:");
/* 1394 */       enterPanel.add(this.dylabel);
/* 1395 */       enterPanel.add(this.selarc);
/* 1396 */       enterPanel.add(Box.createHorizontalStrut(20));
/* 1397 */       enterPanel.add(this.xlabel);
/* 1398 */       enterPanel.add(this.xval);
/* 1399 */       this.operatePanel.removeAll();
/* 1400 */       this.operatePanel.add(this.indilabel);
/* 1401 */       this.operatePanel.add(enterPanel);
/*      */       
/* 1403 */       this.canvas.setDrawCostInfo(false);
/* 1404 */       this.canvas.setDrawFlowInfo(true);
/* 1405 */       this.canvas.setDrawLoopInfo(true);
/* 1406 */       break;
/*      */     case 10: 
/* 1408 */       String leavearc = new String(this.selarc.getText().toUpperCase(Locale.US));
/* 1409 */       this.leaving_arcs.addElement(leavearc);
/* 1410 */       this.indilabel.setText(String.valueOf(String.valueOf(new StringBuffer("Does the arc ").append(leavearc).append(" need to be reversed?"))));
/* 1411 */       enterPanel = new JPanel();
/* 1412 */       enterPanel.add(this.indilabel);
/* 1413 */       enterPanel.add(this.yorn);
/* 1414 */       this.operatePanel.removeAll();
/* 1415 */       this.operatePanel.add(enterPanel);
/*      */       
/* 1417 */       this.canvas.setDrawCostInfo(false);
/* 1418 */       this.canvas.setDrawFlowInfo(true);
/* 1419 */       this.canvas.setDrawLoopInfo(false);
/* 1420 */       break;
/*      */     
/*      */     case 11: 
/* 1423 */       this.statetext.setText(this.str_iterate_complete);
/* 1424 */       this.operatePanel.removeAll();
/* 1425 */       this.operatePanel.add(this.statetext);
/* 1426 */       this.operatePanel.add(Box.createVerticalStrut(10));
/*      */       
/* 1428 */       this.canvas.setDrawCostInfo(false);
/* 1429 */       this.canvas.setDrawFlowInfo(true);
/* 1430 */       break;
/*      */     case 12: 
/* 1432 */       this.dylabel.setText(this.str_enter_reverse_flow);
/* 1433 */       inputPanel = new JPanel();
/* 1434 */       inputPanel.setLayout(new BoxLayout(inputPanel, 1));
/* 1435 */       inputPanel.add(this.dylabel);
/* 1436 */       inputPanel.add(Box.createVerticalStrut(10));
/* 1437 */       for (int i = 0; i < this.nArcs; i++) {
/* 1438 */         if (this.controller.data.arcs[i].is_reversed) {
/* 1439 */           String orgname = "";
/* 1440 */           String revname = this.controller.data.arcs[i].getArcName();
/* 1441 */           orgname = String.valueOf(String.valueOf(orgname)).concat(String.valueOf(String.valueOf(revname.charAt(1))));
/* 1442 */           orgname = String.valueOf(String.valueOf(orgname)).concat(String.valueOf(String.valueOf(revname.charAt(0))));
/* 1443 */           this.arcname[i].setText(String.valueOf(String.valueOf(new StringBuffer("Flow through arc ").append(orgname).append(" = "))));
/* 1444 */           this.arcname[i].setLabelFor(this.arcflow[i]);
/* 1445 */           this.arcflow[i].setValue(0.0D);
/* 1446 */           this.flowPanel[i].add(this.arcname[i]);
/* 1447 */           this.flowPanel[i].add(this.arcflow[i]);
/* 1448 */           this.flowPanel[i].setPreferredSize(new Dimension(300, 35));
/* 1449 */           this.flowPanel[i].setMinimumSize(new Dimension(300, 35));
/* 1450 */           this.flowPanel[i].setMaximumSize(new Dimension(300, 35));
/* 1451 */           inputPanel.add(Box.createRigidArea(new Dimension(0, 2)));
/* 1452 */           inputPanel.add(this.flowPanel[i]);
/*      */         }
/*      */       }
/* 1455 */       this.operatePanel.removeAll();
/* 1456 */       this.operatePanel.add(inputPanel);
/*      */       
/* 1458 */       this.canvas.setDrawCostInfo(false);
/* 1459 */       this.canvas.setDrawFlowInfo(true);
/* 1460 */       break;
/*      */     
/*      */     case 13: 
/* 1463 */       this.indilabel.setText(this.str_problem_end);
/* 1464 */       this.resultlabel.setText("Total Cost: Z = ".concat(String.valueOf(String.valueOf(this.controller.data.Z))));
/* 1465 */       this.resultlabel.setAlignmentX(0.5F);
/* 1466 */       this.operatePanel.removeAll();
/* 1467 */       this.operatePanel.add(this.indilabel);
/* 1468 */       this.operatePanel.add(Box.createVerticalStrut(10));
/* 1469 */       this.operatePanel.add(this.resultlabel);
/* 1470 */       this.nextbutt.setEnabled(false);
/*      */       
/* 1472 */       this.canvas.setDrawCostInfo(false);
/* 1473 */       this.canvas.setDrawFlowInfo(true);
/* 1474 */       break;
/*      */     default: 
/* 1476 */       System.out.println("NA Action has no this step.");
/*      */     }
/* 1478 */     revalidate();
/* 1479 */     repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFile(ObjectOutputStream out)
/*      */   {
/* 1489 */     int[] interpara = new int[2];
/*      */     
/* 1491 */     interpara[0] = this.step;
/* 1492 */     interpara[1] = this.next_select_nonbasic;
/*      */     try
/*      */     {
/* 1495 */       out.writeObject(this.state);
/* 1496 */       out.writeObject(interpara);
/* 1497 */       out.writeObject(this.current_nonbasic);
/* 1498 */       out.writeObject(this.opseq);
/* 1499 */       out.writeObject(this.leaving_arcs);
/*      */     }
/*      */     catch (Exception e1) {
/* 1502 */       e1.printStackTrace();
/* 1503 */       System.out.println("Save fails");
/*      */     }
/*      */   }
/*      */   
/*      */   class IORDeltaTableModel extends AbstractTableModel {
/*      */     private int i;
/*      */     private int j;
/*      */     
/*      */     IORDeltaTableModel() {}
/*      */     
/* 1513 */     public int getColumnCount() { return 2; }
/*      */     
/*      */     public int getRowCount()
/*      */     {
/* 1517 */       return IORNAActionPanel.this.controller.data.num_arcs - IORNAActionPanel.this.controller.data.num_nodes + 1;
/*      */     }
/*      */     
/*      */     public String getColumnName(int col) {
/* 1521 */       if (col == 0) {
/* 1522 */         return "Arc";
/*      */       }
/* 1524 */       return "ΔZ (θ = 1)";
/*      */     }
/*      */     
/*      */     public Object getValueAt(int row, int col) {
/* 1528 */       String str = new String("UU");
/* 1529 */       DecimalFormat decfm = new DecimalFormat();
/*      */       
/*      */ 
/* 1532 */       if (col == 0) {
/* 1533 */         int find = 0;
/* 1534 */         for (this.i = 0; this.i < IORNAActionPanel.this.controller.data.num_arcs; this.i += 1) {
/* 1535 */           if (IORNAActionPanel.this.controller.data.arcs[this.i].is_basic == false) {
/* 1536 */             find++;
/* 1537 */             if (find == row + 1) {
/* 1538 */               str = IORNAActionPanel.this.controller.data.arcs[this.i].getArcName();
/* 1539 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1543 */         return str;
/*      */       }
/*      */       
/* 1546 */       return decfm.format(IORNAActionPanel.this.controller.data.deltaZ[row]);
/*      */     }
/*      */     
/*      */     public Class getColumnClass(int c) {
/* 1550 */       return getValueAt(0, c).getClass();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isCellEditable(int row, int col)
/*      */     {
/* 1556 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/* 1565 */     String str = "Solve Interactively by the Network Simplex Method\n\n";
/* 1566 */     str = String.valueOf(String.valueOf(str)).concat("This procedure enables you to execute the Network Simplex Method as presented ");
/* 1567 */     str = String.valueOf(String.valueOf(str)).concat("in Sec. 9.7. Your role is to apply the logic of the algorithm, and the computer ");
/* 1568 */     str = String.valueOf(String.valueOf(str)).concat("will do the number crunching. The computer also will inform you if you make ");
/* 1569 */     str = String.valueOf(String.valueOf(str)).concat("a mistake on the first iteration.\n\n");
/* 1570 */     str = String.valueOf(String.valueOf(str)).concat("This procedure can only be used to solve Problems 9.7-2 through 9.7-8 at the ");
/* 1571 */     str = String.valueOf(String.valueOf(str)).concat("end of Chapter 9.\n\n");
/* 1572 */     str = String.valueOf(String.valueOf(str)).concat("When you finish a problem, you can print out all your work by choosing ");
/* 1573 */     str = String.valueOf(String.valueOf(str)).concat("Print to file under the File menu. If you are interrupted before you finish, ");
/* 1574 */     str = String.valueOf(String.valueOf(str)).concat("you can save your work (choose Save under the File menu) and return later ");
/* 1575 */     str = String.valueOf(String.valueOf(str)).concat("(choose Open).\n\n");
/* 1576 */     str = String.valueOf(String.valueOf(str)).concat("At any step, detailed computer instructions are available (Help menu). To ");
/* 1577 */     str = String.valueOf(String.valueOf(str)).concat("undo a mistake, backtrack by pressing the BACK button.");
/* 1578 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/*      */ 
/* 1581 */     this.help_content_procedure.setText(str);
/* 1582 */     this.help_content_procedure.revalidate(); }
/*      */   
/* 1584 */   private String str0 = "Interactive Network Simplex Method\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1594 */   private String str1 = "\nPress the NEXT button to begin the Network Simplex Method\n\n";
/*      */   
/*      */ 
/*      */ 
/* 1598 */   private String str2 = "Specifying the basic arcs\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1605 */   private String str3 = "Specifying which (if any) nonbasic arcs should be reversed\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1613 */   private String str4 = "Specifying the flow in the basic arcs\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1618 */   private String str5 = "Identifying the cycle created when a nonbasic arc is added to the spanning tree\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1623 */   private String str6 = "Observing the incremental cost of adding flow through the cycle\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1628 */   private String str7 = "Specifying whether the current solution is optimal\n\n";
/*      */   
/*      */ 
/* 1631 */   private String str8 = "Selecting the entering basic arc\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1636 */   private String str9 = "Selecting the leaving basic arc\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1643 */   private String str10 = "Does the leaving basic arc need to be replaced by a reverse arc?\n\n";
/*      */   
/*      */ 
/* 1646 */   private String str11 = "Press the NEXT button to continue the Network Simplex Method\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1652 */   private String str12 = "Specifying the flow through the original arcs\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1657 */   private String str13 = "An optimal solution\n\n";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void updateStepHelpPanel()
/*      */   {
/* 1666 */     String str = "\n\n\nPress the ENTER key to continue the current procedure.";
/* 1667 */     switch (this.step) {
/*      */     case 1: 
/* 1669 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str1).append(str))));
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
/* 1711 */       break;
/*      */     case 2: 
/* 1672 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str2).append(str))));
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
/* 1711 */       break;
/*      */     case 3: 
/* 1675 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str3).append(str))));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1711 */       break;
/*      */     case 4: 
/* 1678 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str4).append(str))));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1711 */       break;
/*      */     case 5: 
/* 1681 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str5).append(str))));
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
/*      */ 
/* 1711 */       break;
/*      */     case 6: 
/* 1684 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str6).append(str))));
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
/* 1711 */       break;
/*      */     case 7: 
/* 1687 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str7).append(str))));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1711 */       break;
/*      */     case 8: 
/* 1690 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str8).append(str))));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1711 */       break;
/*      */     case 9: 
/* 1693 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str9).append(str))));
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
/*      */ 
/*      */ 
/*      */ 
/* 1711 */       break;
/*      */     case 10: 
/* 1696 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str10).append(str))));
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
/* 1711 */       break;
/*      */     case 11: 
/* 1699 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str11).append(str))));
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
/* 1711 */       break;
/*      */     case 12: 
/* 1702 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str12).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1711 */       break;
/*      */     case 13: 
/* 1705 */       this.help_content_step.setText(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.str0))).append(this.str13).append(str))));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1711 */       break;
/*      */     default: 
/* 1708 */       this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/* 1718 */     String str = "\nPress the NEXT button to begin the Network Simplex Method\n\n";
/* 1719 */     str = String.valueOf(String.valueOf(str)).concat("This screen presents the network for the problem you ");
/* 1720 */     str = String.valueOf(String.valueOf(str)).concat("have chosen to solve. If this is the correct problem, then press the NEXT button ");
/* 1721 */     str = String.valueOf(String.valueOf(str)).concat("to begin applying the Network Simplex Method to solve it. If this is not the ");
/* 1722 */     str = String.valueOf(String.valueOf(str)).concat("correct problem, then go back to the previous procedure and change the problem number.");
/* 1723 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/* 1724 */     this.help_content_step.setText(String.valueOf(String.valueOf(this.str0)).concat(String.valueOf(String.valueOf(str))));
/* 1725 */     this.help_content_step.revalidate();
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNAActionPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */