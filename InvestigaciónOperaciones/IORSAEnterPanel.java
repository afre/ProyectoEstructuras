/*      */ import java.awt.Dimension;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.Vector;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JRadioButtonMenuItem;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IORSAEnterPanel
/*      */   extends IORSAActionPanel
/*      */ {
/*      */   private int servernum;
/*      */   private int classnum;
/*   34 */   private JLabel class1arrivestring1 = null;
/*   35 */   private JLabel class1arrivestring2 = null;
/*   36 */   private JLabel class1servicestring1 = null;
/*   37 */   private JLabel class1servicestring2 = null;
/*   38 */   private JLabel class2arrivestring1 = null;
/*   39 */   private JLabel class2arrivestring2 = null;
/*   40 */   private JLabel class2servicestring1 = null;
/*   41 */   private JLabel class2servicestring2 = null;
/*      */   
/*      */ 
/*   44 */   private WholeNumberField wnf_servernum = null;
/*   45 */   private WholeNumberField wnf_classnum = null;
/*      */   
/*   47 */   private JPanel mainPanel = new JPanel();
/*   48 */   private JPanel serverPanel = new JPanel();
/*   49 */   private JPanel classPanel = new JPanel();
/*   50 */   private JPanel wholePanel = new JPanel();
/*   51 */   private JPanel class1arrivePanel = new JPanel();
/*   52 */   private JPanel class1servicePanel = new JPanel();
/*   53 */   private JPanel class2arrivePanel = new JPanel();
/*   54 */   private JPanel class2servicePanel = new JPanel();
/*      */   
/*   56 */   private DecimalField df_class1arriveparameter1 = null;
/*   57 */   private DecimalField df_class1arriveparameter2 = null;
/*   58 */   private DecimalField df_class1serviceparameter1 = null;
/*   59 */   private DecimalField df_class1serviceparameter2 = null;
/*      */   
/*   61 */   private DecimalField df_class2arriveparameter1 = null;
/*   62 */   private DecimalField df_class2arriveparameter2 = null;
/*   63 */   private DecimalField df_class2serviceparameter1 = null;
/*   64 */   private DecimalField df_class2serviceparameter2 = null;
/*      */   
/*   66 */   private WholeNumberField wnf_class1arriveparameter = null;
/*   67 */   private WholeNumberField wnf_class1serviceparameter = null;
/*   68 */   private WholeNumberField wnf_class2arriveparameter = null;
/*   69 */   private WholeNumberField wnf_class2serviceparameter = null;
/*      */   
/*   71 */   private JComboBox combox1 = new JComboBox();
/*   72 */   private JComboBox combox2 = new JComboBox();
/*   73 */   private JComboBox combox3 = new JComboBox();
/*   74 */   private JComboBox combox4 = new JComboBox();
/*      */   
/*   76 */   private JLabel inputserver = new JLabel("Number of Servers:");
/*   77 */   private JLabel inputclass = new JLabel("Number of Priority Classes:");
/*   78 */   private JLabel class1 = new JLabel("Priority Class 1 (higher priority) customers:");
/*   79 */   private JLabel class2 = new JLabel("Priority Class 2 (lower priority) customers:");
/*   80 */   private JLabel class1arrive = new JLabel("Distr. of interarrival times:");
/*   81 */   private JLabel class1service = new JLabel("     Distr. of service times:");
/*   82 */   private JLabel class2arrive = new JLabel("Distr. of interarrival times:");
/*   83 */   private JLabel class2service = new JLabel("     Distr. of service times:");
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean addclass1;
/*      */   
/*      */ 
/*      */   private boolean addclass2;
/*      */   
/*      */ 
/*      */ 
/*      */   public IORSAEnterPanel(IORSAProcessController c)
/*      */   {
/*   96 */     super(c);
/*   97 */     add(this.mainPanel, "Center");
/*      */     
/*      */ 
/*  100 */     this.servernum = 1;
/*  101 */     this.classnum = 1;
/*  102 */     this.addclass1 = false;
/*  103 */     this.addclass2 = false;
/*      */     
/*  105 */     this.class1.setAlignmentX(0.5F);
/*  106 */     this.class2.setAlignmentX(0.5F);
/*      */     
/*  108 */     this.serverPanel.add(this.inputserver);
/*  109 */     this.serverPanel.add(Box.createHorizontalStrut(40));
/*  110 */     this.wnf_servernum = new WholeNumberField(1, 3);
/*  111 */     this.wnf_servernum.addActionListener(new ActionListener()
/*      */     {
/*  113 */       public void actionPerformed(ActionEvent e) { IORSAEnterPanel.this.InputServerNum(); }
/*  114 */     });
/*  115 */     this.serverPanel.add(this.wnf_servernum);
/*  116 */     this.serverPanel.setMaximumSize(new Dimension(400, 40));
/*      */     
/*  118 */     this.classPanel.add(this.inputclass);
/*  119 */     this.wnf_classnum = new WholeNumberField(1, 3);
/*  120 */     this.wnf_classnum.addActionListener(new ActionListener()
/*      */     {
/*  122 */       public void actionPerformed(ActionEvent e) { IORSAEnterPanel.this.Inputallparameters(); }
/*  123 */     });
/*  124 */     this.classPanel.add(this.wnf_classnum);
/*  125 */     this.classPanel.setMaximumSize(new Dimension(400, 40));
/*      */     
/*  127 */     this.serverPanel.setAlignmentX(0.5F);
/*  128 */     this.classPanel.setAlignmentX(0.5F);
/*      */     
/*  130 */     this.mainPanel.setLayout(new BoxLayout(this.mainPanel, 1));
/*  131 */     this.wholePanel.setLayout(new BoxLayout(this.wholePanel, 1));
/*  132 */     this.mainPanel.add(this.serverPanel);
/*  133 */     this.mainPanel.add(this.classPanel);
/*      */     
/*  135 */     JLabel lb = new JLabel("(To make a new number above take effect, you must press the ENTER key.)");
/*  136 */     JPanel p = new JPanel();
/*  137 */     p.add(lb);
/*  138 */     this.mainPanel.add(Box.createVerticalStrut(20));
/*  139 */     this.mainPanel.add(p);
/*  140 */     this.mainPanel.add(Box.createVerticalStrut(20));
/*      */     
/*  142 */     this.combox1.addItem("Constant");
/*  143 */     this.combox1.addItem("Erlang");
/*  144 */     this.combox1.addItem("Exponential");
/*  145 */     this.combox1.addItem("Translated Exp.");
/*  146 */     this.combox1.addItem("Uniform");
/*  147 */     this.combox1.setSelectedIndex(2);
/*      */     
/*  149 */     this.combox1.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  151 */         IORSAEnterPanel.this.changedistribution(e);
/*      */       }
/*  153 */     });
/*  154 */     this.combox2.addItem("Constant");
/*  155 */     this.combox2.addItem("Erlang");
/*  156 */     this.combox2.addItem("Exponential");
/*  157 */     this.combox2.addItem("Translated Exp.");
/*  158 */     this.combox2.addItem("Uniform");
/*  159 */     this.combox2.setSelectedIndex(2);
/*      */     
/*  161 */     this.combox2.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  163 */         IORSAEnterPanel.this.changedistribution(e);
/*      */       }
/*  165 */     });
/*  166 */     this.class1arrivePanel.add(this.class1arrive);
/*  167 */     this.class1arrivePanel.add(this.combox1);
/*  168 */     this.class1arrivePanel.add(this.class1arrivestring1 = new JLabel("Mean = "));
/*  169 */     this.class1arrivePanel.add(this.df_class1arriveparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  170 */     this.combox1.setSelectedIndex(2);
/*      */     
/*  172 */     this.class1servicePanel.add(this.class1service);
/*  173 */     this.class1servicePanel.add(this.combox2);
/*  174 */     this.class1servicePanel.add(this.class1servicestring1 = new JLabel("Mean = "));
/*  175 */     this.class1servicePanel.add(this.df_class1serviceparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  176 */     this.combox2.setSelectedIndex(2);
/*      */     
/*  178 */     this.addclass1 = true;
/*      */     
/*  180 */     this.wholePanel.add(this.class1arrivePanel);
/*  181 */     this.wholePanel.add(this.class1servicePanel);
/*      */     
/*  183 */     this.mainPanel.add(this.wholePanel);
/*      */   }
/*      */   
/*      */   private void InputServerNum() {
/*  187 */     this.servernum = Integer.parseInt(this.wnf_servernum.getText());
/*      */     
/*      */ 
/*  190 */     if (this.servernum < 1) this.servernum = 1;
/*  191 */     if (this.servernum > 2) { this.servernum = 2;
/*      */     }
/*      */     
/*  194 */     this.wnf_servernum.setText(String.valueOf(this.servernum));
/*      */   }
/*      */   
/*      */   private void Inputallparameters()
/*      */   {
/*  199 */     this.class1arrivePanel.removeAll();
/*  200 */     this.class1servicePanel.removeAll();
/*  201 */     this.class2arrivePanel.removeAll();
/*  202 */     this.class2servicePanel.removeAll();
/*  203 */     this.wholePanel.removeAll();
/*      */     
/*  205 */     this.classnum = Integer.parseInt(this.wnf_classnum.getText());
/*      */     
/*      */ 
/*  208 */     if (this.classnum < 1) this.classnum = 1;
/*  209 */     if (this.classnum > 2) { this.classnum = 2;
/*      */     }
/*      */     
/*  212 */     this.wnf_classnum.setText(String.valueOf(this.classnum));
/*      */     
/*  214 */     if (this.classnum == 2) {
/*  215 */       this.wholePanel.add(this.class1);
/*  216 */       this.wholePanel.add(Box.createVerticalStrut(15));
/*      */     }
/*      */     
/*  219 */     if (this.addclass1 == false) {
/*  220 */       this.addclass1 = true;
/*  221 */       this.combox1.addItem("Constant");
/*  222 */       this.combox1.addItem("Erlang");
/*  223 */       this.combox1.addItem("Exponential");
/*  224 */       this.combox1.addItem("Translated Exp.");
/*  225 */       this.combox1.addItem("Uniform");
/*  226 */       this.combox1.setSelectedIndex(2);
/*      */       
/*  228 */       this.combox1.addActionListener(new ActionListener() {
/*      */         public void actionPerformed(ActionEvent e) {
/*  230 */           IORSAEnterPanel.this.changedistribution(e);
/*      */         }
/*  232 */       });
/*  233 */       this.combox2.addItem("Constant");
/*  234 */       this.combox2.addItem("Erlang");
/*  235 */       this.combox2.addItem("Exponential");
/*  236 */       this.combox2.addItem("Translated Exp.");
/*  237 */       this.combox2.addItem("Uniform");
/*  238 */       this.combox2.setSelectedIndex(2);
/*      */       
/*  240 */       this.combox2.addActionListener(new ActionListener() {
/*      */         public void actionPerformed(ActionEvent e) {
/*  242 */           IORSAEnterPanel.this.changedistribution(e);
/*      */         }
/*      */       });
/*      */     }
/*  246 */     this.class1arrivePanel.add(this.class1arrive);
/*  247 */     this.class1arrivePanel.add(this.combox1);
/*  248 */     this.class1arrivePanel.add(this.class1arrivestring1 = new JLabel("Mean = "));
/*  249 */     this.class1arrivePanel.add(this.df_class1arriveparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  250 */     this.combox1.setSelectedIndex(2);
/*      */     
/*  252 */     this.class1servicePanel.add(this.class1service);
/*  253 */     this.class1servicePanel.add(this.combox2);
/*  254 */     this.class1servicePanel.add(this.class1servicestring1 = new JLabel("Mean = "));
/*  255 */     this.class1servicePanel.add(this.df_class1serviceparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  256 */     this.combox2.setSelectedIndex(2);
/*      */     
/*  258 */     this.wholePanel.add(this.class1arrivePanel);
/*  259 */     this.wholePanel.add(this.class1servicePanel);
/*      */     
/*  261 */     if (this.classnum == 2) {
/*  262 */       this.wholePanel.add(this.class2);
/*  263 */       this.wholePanel.add(Box.createVerticalStrut(15));
/*      */       
/*  265 */       if (this.addclass2 == false) {
/*  266 */         this.addclass2 = true;
/*  267 */         this.combox3.addItem("Constant");
/*  268 */         this.combox3.addItem("Erlang");
/*  269 */         this.combox3.addItem("Exponential");
/*  270 */         this.combox3.addItem("Translated Exp.");
/*  271 */         this.combox3.addItem("Uniform");
/*  272 */         this.combox3.setSelectedIndex(2);
/*      */         
/*  274 */         this.combox3.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent e) {
/*  276 */             IORSAEnterPanel.this.changedistribution(e);
/*      */           }
/*  278 */         });
/*  279 */         this.combox4.addItem("Constant");
/*  280 */         this.combox4.addItem("Erlang");
/*  281 */         this.combox4.addItem("Exponential");
/*  282 */         this.combox4.addItem("Translated Exp.");
/*  283 */         this.combox4.addItem("Uniform");
/*  284 */         this.combox4.setSelectedIndex(2);
/*      */         
/*  286 */         this.combox4.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent e) {
/*  288 */             IORSAEnterPanel.this.changedistribution(e);
/*      */           }
/*      */         });
/*      */       }
/*  292 */       this.class2arrivePanel.add(this.class2arrive);
/*  293 */       this.class2arrivePanel.add(this.combox3);
/*  294 */       this.class2arrivePanel.add(this.class2arrivestring1 = new JLabel("Mean = "));
/*  295 */       this.class2arrivePanel.add(this.df_class2arriveparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  296 */       this.combox3.setSelectedIndex(2);
/*      */       
/*  298 */       this.wholePanel.add(this.class2arrivePanel);
/*      */       
/*  300 */       this.class2servicePanel.add(this.class2service);
/*  301 */       this.class2servicePanel.add(this.combox4);
/*  302 */       this.class2servicePanel.add(this.class2servicestring1 = new JLabel("Mean = "));
/*  303 */       this.class2servicePanel.add(this.df_class2serviceparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  304 */       this.combox4.setSelectedIndex(2);
/*      */       
/*  306 */       this.wholePanel.add(this.class2servicePanel);
/*      */     }
/*      */     
/*  309 */     this.mainPanel.revalidate();
/*  310 */     this.mainPanel.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */   private void changedistribution(ActionEvent e)
/*      */   {
/*  316 */     if (e.getSource() == this.combox1) {
/*  317 */       int selectitem = this.combox1.getSelectedIndex();
/*  318 */       if (this.df_class1arriveparameter2 != null) this.class1arrivePanel.remove(this.df_class1arriveparameter2);
/*  319 */       if (this.class1arrivestring2 != null) this.class1arrivePanel.remove(this.class1arrivestring2);
/*  320 */       if (this.df_class1arriveparameter1 != null) this.class1arrivePanel.remove(this.df_class1arriveparameter1);
/*  321 */       if (this.class1arrivestring1 != null) this.class1arrivePanel.remove(this.class1arrivestring1);
/*  322 */       if (this.wnf_class1arriveparameter != null) { this.class1arrivePanel.remove(this.wnf_class1arriveparameter);
/*      */       }
/*  324 */       switch (selectitem) {
/*      */       case 0: 
/*  326 */         this.class1arrivePanel.add(this.class1arrivestring1 = new JLabel("Value = "));
/*  327 */         this.class1arrivePanel.add(this.df_class1arriveparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  328 */         break;
/*      */       case 1: 
/*  330 */         this.class1arrivePanel.add(this.class1arrivestring1 = new JLabel("Mean = "));
/*  331 */         this.class1arrivePanel.add(this.df_class1arriveparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  332 */         this.class1arrivePanel.add(this.class1arrivestring2 = new JLabel("K = "));
/*  333 */         this.class1arrivePanel.add(this.wnf_class1arriveparameter = new WholeNumberField(1, 3));
/*  334 */         break;
/*      */       case 2: 
/*  336 */         this.class1arrivePanel.add(this.class1arrivestring1 = new JLabel("Mean = "));
/*  337 */         this.class1arrivePanel.add(this.df_class1arriveparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  338 */         break;
/*      */       case 3: 
/*  340 */         this.class1arrivePanel.add(this.class1arrivestring1 = new JLabel("Min = "));
/*  341 */         this.class1arrivePanel.add(this.df_class1arriveparameter1 = new DecimalField(0.0D, 4, new DecimalFormat()));
/*  342 */         this.class1arrivePanel.add(this.class1arrivestring2 = new JLabel("Mean = "));
/*  343 */         this.class1arrivePanel.add(this.df_class1arriveparameter2 = new DecimalField(1.0D, 4, new DecimalFormat("#.####")));
/*  344 */         break;
/*      */       case 4: 
/*  346 */         this.class1arrivePanel.add(this.class1arrivestring1 = new JLabel("Min = "));
/*  347 */         this.class1arrivePanel.add(this.df_class1arriveparameter1 = new DecimalField(0.0D, 4, new DecimalFormat()));
/*  348 */         this.class1arrivePanel.add(this.class1arrivestring2 = new JLabel("Max = "));
/*  349 */         this.class1arrivePanel.add(this.df_class1arriveparameter2 = new DecimalField(1.0D, 4, new DecimalFormat("#.####")));
/*      */       }
/*      */       
/*  352 */       this.class1arrivePanel.revalidate();
/*  353 */       this.class1arrivePanel.repaint();
/*      */     }
/*      */     
/*  356 */     if (e.getSource() == this.combox2) {
/*  357 */       int selectitem = this.combox2.getSelectedIndex();
/*      */       
/*  359 */       if (this.df_class1serviceparameter2 != null) this.class1servicePanel.remove(this.df_class1serviceparameter2);
/*  360 */       if (this.class1servicestring2 != null) this.class1servicePanel.remove(this.class1servicestring2);
/*  361 */       if (this.df_class1serviceparameter1 != null) this.class1servicePanel.remove(this.df_class1serviceparameter1);
/*  362 */       if (this.class1servicestring1 != null) this.class1servicePanel.remove(this.class1servicestring1);
/*  363 */       if (this.wnf_class1serviceparameter != null) { this.class1servicePanel.remove(this.wnf_class1serviceparameter);
/*      */       }
/*  365 */       switch (selectitem) {
/*      */       case 0: 
/*  367 */         this.class1servicePanel.add(this.class1servicestring1 = new JLabel("Value = "));
/*  368 */         this.class1servicePanel.add(this.df_class1serviceparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  369 */         break;
/*      */       case 1: 
/*  371 */         this.class1servicePanel.add(this.class1servicestring1 = new JLabel("Mean = "));
/*  372 */         this.class1servicePanel.add(this.df_class1serviceparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  373 */         this.class1servicePanel.add(this.class1servicestring2 = new JLabel("K = "));
/*  374 */         this.class1servicePanel.add(this.wnf_class1serviceparameter = new WholeNumberField(1, 3));
/*  375 */         break;
/*      */       case 2: 
/*  377 */         this.class1servicePanel.add(this.class1servicestring1 = new JLabel("Mean = "));
/*  378 */         this.class1servicePanel.add(this.df_class1serviceparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  379 */         break;
/*      */       case 3: 
/*  381 */         this.class1servicePanel.add(this.class1servicestring1 = new JLabel("Min = "));
/*  382 */         this.class1servicePanel.add(this.df_class1serviceparameter1 = new DecimalField(0.0D, 4, new DecimalFormat()));
/*  383 */         this.class1servicePanel.add(this.class1servicestring2 = new JLabel("Mean = "));
/*  384 */         this.class1servicePanel.add(this.df_class1serviceparameter2 = new DecimalField(1.0D, 4, new DecimalFormat("#.####")));
/*  385 */         break;
/*      */       case 4: 
/*  387 */         this.class1servicePanel.add(this.class1servicestring1 = new JLabel("Min = "));
/*  388 */         this.class1servicePanel.add(this.df_class1serviceparameter1 = new DecimalField(0.0D, 4, new DecimalFormat()));
/*  389 */         this.class1servicePanel.add(this.class1servicestring2 = new JLabel("Max = "));
/*  390 */         this.class1servicePanel.add(this.df_class1serviceparameter2 = new DecimalField(1.0D, 4, new DecimalFormat("#.####")));
/*      */       }
/*      */       
/*  393 */       this.class1servicePanel.revalidate();
/*  394 */       this.class1servicePanel.repaint();
/*      */     }
/*      */     
/*  397 */     if (e.getSource() == this.combox3) {
/*  398 */       int selectitem = this.combox3.getSelectedIndex();
/*      */       
/*  400 */       if (this.df_class2arriveparameter2 != null) this.class2arrivePanel.remove(this.df_class2arriveparameter2);
/*  401 */       if (this.class2arrivestring2 != null) this.class2arrivePanel.remove(this.class2arrivestring2);
/*  402 */       if (this.df_class2arriveparameter1 != null) this.class2arrivePanel.remove(this.df_class2arriveparameter1);
/*  403 */       if (this.class2arrivestring1 != null) this.class2arrivePanel.remove(this.class2arrivestring1);
/*  404 */       if (this.wnf_class2arriveparameter != null) { this.class2arrivePanel.remove(this.wnf_class2arriveparameter);
/*      */       }
/*  406 */       switch (selectitem) {
/*      */       case 0: 
/*  408 */         this.class2arrivePanel.add(this.class2arrivestring1 = new JLabel("Value = "));
/*  409 */         this.class2arrivePanel.add(this.df_class2arriveparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  410 */         break;
/*      */       case 1: 
/*  412 */         this.class2arrivePanel.add(this.class2arrivestring1 = new JLabel("Mean = "));
/*  413 */         this.class2arrivePanel.add(this.df_class2arriveparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  414 */         this.class2arrivePanel.add(this.class2arrivestring2 = new JLabel("K = "));
/*  415 */         this.class2arrivePanel.add(this.wnf_class2arriveparameter = new WholeNumberField(1, 3));
/*  416 */         break;
/*      */       case 2: 
/*  418 */         this.class2arrivePanel.add(this.class2arrivestring1 = new JLabel("Mean = "));
/*  419 */         this.class2arrivePanel.add(this.df_class2arriveparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  420 */         break;
/*      */       case 3: 
/*  422 */         this.class2arrivePanel.add(this.class2arrivestring1 = new JLabel("Min = "));
/*  423 */         this.class2arrivePanel.add(this.df_class2arriveparameter1 = new DecimalField(0.0D, 4, new DecimalFormat()));
/*  424 */         this.class2arrivePanel.add(this.class2arrivestring2 = new JLabel("Mean = "));
/*  425 */         this.class2arrivePanel.add(this.df_class2arriveparameter2 = new DecimalField(1.0D, 4, new DecimalFormat("#.####")));
/*  426 */         break;
/*      */       case 4: 
/*  428 */         this.class2arrivePanel.add(this.class2arrivestring1 = new JLabel("Min = "));
/*  429 */         this.class2arrivePanel.add(this.df_class2arriveparameter1 = new DecimalField(0.0D, 4, new DecimalFormat()));
/*  430 */         this.class2arrivePanel.add(this.class2arrivestring2 = new JLabel("Max = "));
/*  431 */         this.class2arrivePanel.add(this.df_class2arriveparameter2 = new DecimalField(1.0D, 4, new DecimalFormat("#.####")));
/*      */       }
/*      */       
/*  434 */       this.class2arrivePanel.revalidate();
/*  435 */       this.class2arrivePanel.repaint();
/*      */     }
/*      */     
/*  438 */     if (e.getSource() == this.combox4) {
/*  439 */       int selectitem = this.combox4.getSelectedIndex();
/*      */       
/*  441 */       if (this.df_class2serviceparameter2 != null) this.class2servicePanel.remove(this.df_class2serviceparameter2);
/*  442 */       if (this.class2servicestring2 != null) this.class2servicePanel.remove(this.class2servicestring2);
/*  443 */       if (this.df_class2serviceparameter1 != null) this.class2servicePanel.remove(this.df_class2serviceparameter1);
/*  444 */       if (this.class2servicestring1 != null) this.class2servicePanel.remove(this.class2servicestring1);
/*  445 */       if (this.wnf_class2serviceparameter != null) { this.class2servicePanel.remove(this.wnf_class2serviceparameter);
/*      */       }
/*  447 */       switch (selectitem) {
/*      */       case 0: 
/*  449 */         this.class2servicePanel.add(this.class2servicestring1 = new JLabel("Value = "));
/*  450 */         this.class2servicePanel.add(this.df_class2serviceparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  451 */         break;
/*      */       case 1: 
/*  453 */         this.class2servicePanel.add(this.class2servicestring1 = new JLabel("Mean = "));
/*  454 */         this.class2servicePanel.add(this.df_class2serviceparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  455 */         this.class2servicePanel.add(this.class2servicestring2 = new JLabel("K = "));
/*  456 */         this.class2servicePanel.add(this.wnf_class2serviceparameter = new WholeNumberField(1, 3));
/*  457 */         break;
/*      */       case 2: 
/*  459 */         this.class2servicePanel.add(this.class2servicestring1 = new JLabel("Mean = "));
/*  460 */         this.class2servicePanel.add(this.df_class2serviceparameter1 = new DecimalField(1.0D, 4, new DecimalFormat()));
/*  461 */         break;
/*      */       case 3: 
/*  463 */         this.class2servicePanel.add(this.class2servicestring1 = new JLabel("Min = "));
/*  464 */         this.class2servicePanel.add(this.df_class2serviceparameter1 = new DecimalField(0.0D, 4, new DecimalFormat()));
/*  465 */         this.class2servicePanel.add(this.class2servicestring2 = new JLabel("Mean = "));
/*  466 */         this.class2servicePanel.add(this.df_class2serviceparameter2 = new DecimalField(1.0D, 4, new DecimalFormat("#.####")));
/*  467 */         break;
/*      */       case 4: 
/*  469 */         this.class2servicePanel.add(this.class2servicestring1 = new JLabel("Min = "));
/*  470 */         this.class2servicePanel.add(this.df_class2serviceparameter1 = new DecimalField(0.0D, 4, new DecimalFormat()));
/*  471 */         this.class2servicePanel.add(this.class2servicestring2 = new JLabel("Max = "));
/*  472 */         this.class2servicePanel.add(this.df_class2serviceparameter2 = new DecimalField(1.0D, 4, new DecimalFormat("#.####")));
/*      */       }
/*      */       
/*  475 */       this.class2servicePanel.revalidate();
/*  476 */       this.class2servicePanel.repaint();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void finishProcedure()
/*      */   {
/*  490 */     setPanelEnable(false);
/*  491 */     this.controller.mi_interactively_siumlate_queueing_problem.setEnabled(true);
/*      */     
/*  493 */     this.servernum = Integer.parseInt(this.wnf_servernum.getText());
/*      */     
/*      */ 
/*  496 */     if (this.servernum < 1) this.servernum = 1;
/*  497 */     if (this.servernum > 2) { this.servernum = 2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  502 */     this.wnf_servernum.setText(String.valueOf(this.servernum));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  507 */     this.wnf_classnum.setText(String.valueOf(this.classnum));
/*      */     
/*  509 */     this.actionStatus.setText("The current procedure is finished. Please go to the Procedure menu to continue!");
/*      */     
/*  511 */     Vector p = new Vector();
/*  512 */     p.addElement(new Integer(this.servernum));
/*  513 */     p.addElement(new Integer(this.classnum));
/*      */     
/*  515 */     int classindex = this.combox1.getSelectedIndex();
/*  516 */     p.addElement(new Integer(classindex));
/*  517 */     switch (classindex) {
/*      */     case 0: 
/*      */     case 2: 
/*  520 */       double parameter1 = this.df_class1arriveparameter1.getValue();
/*  521 */       if (parameter1 <= 0) {
/*  522 */         parameter1 = 0.001D;
/*  523 */         this.df_class1arriveparameter1.setValue(parameter1);
/*      */       }
/*  525 */       p.addElement(new Double(parameter1));
/*  526 */       break;
/*      */     case 1: 
/*  528 */       double parameter1 = this.df_class1arriveparameter1.getValue();
/*  529 */       if (parameter1 <= 0) {
/*  530 */         parameter1 = 0.001D;
/*  531 */         this.df_class1arriveparameter1.setValue(parameter1);
/*      */       }
/*  533 */       p.addElement(new Double(parameter1));
/*  534 */       int K = this.wnf_class1arriveparameter.getValue();
/*  535 */       if (K < 1) {
/*  536 */         K = 1;
/*  537 */         this.wnf_class1arriveparameter.setValue(1);
/*      */       }
/*  539 */       p.addElement(new Integer(K));
/*  540 */       break;
/*      */     case 3: 
/*      */     case 4: 
/*  543 */       double parameter1 = this.df_class1arriveparameter1.getValue();
/*  544 */       if (parameter1 <= 0) {
/*  545 */         parameter1 = 0.0D;
/*  546 */         this.df_class1arriveparameter1.setValue(parameter1);
/*  547 */         parameter1 = this.df_class1arriveparameter1.getValue();
/*      */       }
/*  549 */       double parameter2 = this.df_class1arriveparameter2.getValue();
/*  550 */       if (parameter2 <= parameter1) {
/*  551 */         parameter2 = parameter1 + 0.001D;
/*  552 */         this.df_class1arriveparameter2.setValue(parameter2);
/*      */       }
/*  554 */       p.addElement(new Double(parameter1));
/*  555 */       p.addElement(new Double(parameter2));
/*  556 */       break;
/*      */     default: 
/*      */       return;
/*      */     }
/*      */     double parameter1;
/*  561 */     classindex = this.combox2.getSelectedIndex();
/*  562 */     p.addElement(new Integer(classindex));
/*  563 */     switch (classindex) {
/*      */     case 0: 
/*      */     case 2: 
/*  566 */       parameter1 = this.df_class1serviceparameter1.getValue();
/*  567 */       if (parameter1 <= 0) {
/*  568 */         parameter1 = 0.001D;
/*  569 */         this.df_class1serviceparameter1.setValue(parameter1);
/*      */       }
/*  571 */       p.addElement(new Double(parameter1));
/*  572 */       break;
/*      */     case 1: 
/*  574 */       parameter1 = this.df_class1serviceparameter1.getValue();
/*  575 */       if (parameter1 <= 0) {
/*  576 */         parameter1 = 0.001D;
/*  577 */         this.df_class1serviceparameter1.setValue(parameter1);
/*      */       }
/*  579 */       p.addElement(new Double(parameter1));
/*  580 */       int K = this.wnf_class1serviceparameter.getValue();
/*  581 */       if (K < 1) {
/*  582 */         K = 1;
/*  583 */         this.wnf_class1serviceparameter.setValue(1);
/*      */       }
/*  585 */       p.addElement(new Integer(K));
/*  586 */       break;
/*      */     case 3: 
/*      */     case 4: 
/*  589 */       parameter1 = this.df_class1serviceparameter1.getValue();
/*  590 */       if (parameter1 <= 0) {
/*  591 */         parameter1 = 0.0D;
/*  592 */         this.df_class1serviceparameter1.setValue(parameter1);
/*  593 */         parameter1 = this.df_class1serviceparameter1.getValue();
/*      */       }
/*  595 */       double parameter2 = this.df_class1serviceparameter2.getValue();
/*  596 */       if (parameter2 <= parameter1) {
/*  597 */         parameter2 = parameter1 + 0.001D;
/*  598 */         this.df_class1serviceparameter2.setValue(parameter2);
/*      */       }
/*  600 */       p.addElement(new Double(parameter1));
/*  601 */       p.addElement(new Double(parameter2));
/*  602 */       break;
/*      */     default: 
/*  604 */       return;
/*      */     }
/*      */     
/*  607 */     if (this.classnum == 2) {
/*  608 */       classindex = this.combox3.getSelectedIndex();
/*  609 */       p.addElement(new Integer(classindex));
/*  610 */       switch (classindex) {
/*      */       case 0: 
/*      */       case 2: 
/*  613 */         parameter1 = this.df_class2arriveparameter1.getValue();
/*  614 */         if (parameter1 <= 0) {
/*  615 */           parameter1 = 0.001D;
/*  616 */           this.df_class2arriveparameter1.setValue(parameter1);
/*      */         }
/*  618 */         p.addElement(new Double(parameter1));
/*  619 */         break;
/*      */       case 1: 
/*  621 */         parameter1 = this.df_class2arriveparameter1.getValue();
/*  622 */         if (parameter1 <= 0) {
/*  623 */           parameter1 = 0.001D;
/*  624 */           this.df_class2arriveparameter1.setValue(parameter1);
/*      */         }
/*  626 */         p.addElement(new Double(parameter1));
/*  627 */         int K = this.wnf_class2arriveparameter.getValue();
/*  628 */         if (K < 1) {
/*  629 */           K = 1;
/*  630 */           this.wnf_class2arriveparameter.setValue(1);
/*      */         }
/*  632 */         p.addElement(new Integer(K));
/*  633 */         break;
/*      */       case 3: 
/*      */       case 4: 
/*  636 */         parameter1 = this.df_class2arriveparameter1.getValue();
/*  637 */         if (parameter1 <= 0) {
/*  638 */           parameter1 = 0.0D;
/*  639 */           this.df_class2arriveparameter1.setValue(parameter1);
/*  640 */           parameter1 = this.df_class2arriveparameter1.getValue();
/*      */         }
/*  642 */         double parameter2 = this.df_class2arriveparameter2.getValue();
/*  643 */         if (parameter2 <= parameter1) {
/*  644 */           parameter2 = parameter1 + 0.001D;
/*  645 */           this.df_class2arriveparameter2.setValue(parameter2);
/*      */         }
/*  647 */         p.addElement(new Double(parameter1));
/*  648 */         p.addElement(new Double(parameter2));
/*  649 */         break;
/*      */       default: 
/*  651 */         return;
/*      */       }
/*      */       
/*  654 */       classindex = this.combox4.getSelectedIndex();
/*  655 */       p.addElement(new Integer(classindex));
/*  656 */       switch (classindex) {
/*      */       case 0: 
/*      */       case 2: 
/*  659 */         parameter1 = this.df_class2serviceparameter1.getValue();
/*  660 */         if (parameter1 <= 0) {
/*  661 */           parameter1 = 0.001D;
/*  662 */           this.df_class2serviceparameter1.setValue(parameter1);
/*      */         }
/*  664 */         p.addElement(new Double(parameter1));
/*  665 */         break;
/*      */       case 1: 
/*  667 */         parameter1 = this.df_class2serviceparameter1.getValue();
/*  668 */         if (parameter1 <= 0) {
/*  669 */           parameter1 = 0.001D;
/*  670 */           this.df_class2serviceparameter1.setValue(parameter1);
/*      */         }
/*  672 */         p.addElement(new Double(parameter1));
/*  673 */         int K = this.wnf_class2serviceparameter.getValue();
/*  674 */         if (K < 1) {
/*  675 */           K = 1;
/*  676 */           this.wnf_class2serviceparameter.setValue(1);
/*      */         }
/*  678 */         p.addElement(new Integer(K));
/*  679 */         break;
/*      */       case 3: 
/*      */       case 4: 
/*  682 */         parameter1 = this.df_class2serviceparameter1.getValue();
/*  683 */         if (parameter1 <= 0) {
/*  684 */           parameter1 = 0.0D;
/*  685 */           this.df_class2serviceparameter1.setValue(parameter1);
/*  686 */           parameter1 = this.df_class2serviceparameter1.getValue();
/*      */         }
/*  688 */         double parameter2 = this.df_class2serviceparameter2.getValue();
/*  689 */         if (parameter2 <= parameter1) {
/*  690 */           parameter2 = parameter1 + 0.001D;
/*  691 */           this.df_class2serviceparameter2.setValue(parameter2);
/*      */         }
/*  693 */         p.addElement(new Double(parameter1));
/*  694 */         p.addElement(new Double(parameter2));
/*  695 */         break;
/*      */       default: 
/*  697 */         return;
/*      */       }
/*      */       
/*      */     }
/*  701 */     IOROperation operation = new IOROperation(50101, p);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  706 */     if (this.controller.solver.doWork(operation, this.controller.data) == false)
/*      */     {
/*  708 */       JOptionPane.showMessageDialog(this, this.controller.solver.getErrInfo());
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
/*  719 */     int[] para = new int[2];int[] comindex = new int[4];int[] kval = new int[4];
/*  720 */     double[] dblpar = new double[8];
/*      */     
/*      */     try
/*      */     {
/*  724 */       para = (int[])in.readObject();
/*  725 */       comindex = (int[])in.readObject();
/*  726 */       dblpar = (double[])in.readObject();
/*  727 */       kval = (int[])in.readObject();
/*  728 */       in.close();
/*      */     }
/*      */     catch (Exception e1) {
/*  731 */       e1.printStackTrace();
/*  732 */       System.out.println("Open fails");
/*      */     }
/*  734 */     this.servernum = para[0];
/*  735 */     this.classnum = para[1];
/*  736 */     this.wnf_servernum.setValue(this.servernum);
/*  737 */     this.wnf_classnum.setValue(this.classnum);
/*      */     
/*      */ 
/*  740 */     this.combox1.setSelectedIndex(comindex[0]);
/*  741 */     this.class1arrivePanel.removeAll();
/*  742 */     this.class1arrivePanel.add(this.class1arrive);
/*  743 */     this.class1arrivePanel.add(this.combox1);
/*  744 */     switch (comindex[0]) {
/*      */     case 0: 
/*  746 */       this.class1arrivePanel.add(this.class1arrivestring1 = new JLabel("Value = "));
/*  747 */       this.class1arrivePanel.add(this.df_class1arriveparameter1 = new DecimalField(dblpar[0], 4, new DecimalFormat()));
/*  748 */       break;
/*      */     case 1: 
/*  750 */       this.class1arrivePanel.add(this.class1arrivestring1 = new JLabel("Mean = "));
/*  751 */       this.class1arrivePanel.add(this.df_class1arriveparameter1 = new DecimalField(dblpar[0], 4, new DecimalFormat()));
/*  752 */       this.class1arrivePanel.add(this.class1arrivestring2 = new JLabel("K = "));
/*  753 */       this.class1arrivePanel.add(this.wnf_class1arriveparameter = new WholeNumberField(kval[0], 3));
/*  754 */       break;
/*      */     case 2: 
/*  756 */       this.class1arrivePanel.add(this.class1arrivestring1 = new JLabel("Mean = "));
/*  757 */       this.class1arrivePanel.add(this.df_class1arriveparameter1 = new DecimalField(dblpar[0], 4, new DecimalFormat()));
/*  758 */       break;
/*      */     case 3: 
/*  760 */       this.class1arrivePanel.add(this.class1arrivestring1 = new JLabel("Min = "));
/*  761 */       this.class1arrivePanel.add(this.df_class1arriveparameter1 = new DecimalField(dblpar[0], 4, new DecimalFormat()));
/*  762 */       this.class1arrivePanel.add(this.class1arrivestring2 = new JLabel("Mean = "));
/*  763 */       this.class1arrivePanel.add(this.df_class1arriveparameter2 = new DecimalField(dblpar[1], 4, new DecimalFormat("#.####")));
/*  764 */       break;
/*      */     case 4: 
/*  766 */       this.class1arrivePanel.add(this.class1arrivestring1 = new JLabel("Min = "));
/*  767 */       this.class1arrivePanel.add(this.df_class1arriveparameter1 = new DecimalField(dblpar[0], 4, new DecimalFormat()));
/*  768 */       this.class1arrivePanel.add(this.class1arrivestring2 = new JLabel("Max = "));
/*  769 */       this.class1arrivePanel.add(this.df_class1arriveparameter2 = new DecimalField(dblpar[1], 4, new DecimalFormat("#.####")));
/*  770 */       break;
/*      */     }
/*      */     
/*  773 */     this.combox2.setSelectedIndex(comindex[1]);
/*  774 */     this.class1servicePanel.removeAll();
/*  775 */     this.class1servicePanel.add(this.class1service);
/*  776 */     this.class1servicePanel.add(this.combox2);
/*  777 */     switch (comindex[1]) {
/*      */     case 0: 
/*  779 */       this.class1servicePanel.add(this.class1servicestring1 = new JLabel("Value = "));
/*  780 */       this.class1servicePanel.add(this.df_class1serviceparameter1 = new DecimalField(dblpar[2], 4, new DecimalFormat()));
/*  781 */       break;
/*      */     case 1: 
/*  783 */       this.class1servicePanel.add(this.class1servicestring1 = new JLabel("Mean = "));
/*  784 */       this.class1servicePanel.add(this.df_class1serviceparameter1 = new DecimalField(dblpar[2], 4, new DecimalFormat()));
/*  785 */       this.class1servicePanel.add(this.class1servicestring2 = new JLabel("K = "));
/*  786 */       this.class1servicePanel.add(this.wnf_class1serviceparameter = new WholeNumberField(kval[1], 3));
/*  787 */       break;
/*      */     case 2: 
/*  789 */       this.class1servicePanel.add(this.class1servicestring1 = new JLabel("Mean = "));
/*  790 */       this.class1servicePanel.add(this.df_class1serviceparameter1 = new DecimalField(dblpar[2], 4, new DecimalFormat()));
/*  791 */       break;
/*      */     case 3: 
/*  793 */       this.class1servicePanel.add(this.class1servicestring1 = new JLabel("Min = "));
/*  794 */       this.class1servicePanel.add(this.df_class1serviceparameter1 = new DecimalField(dblpar[2], 4, new DecimalFormat()));
/*  795 */       this.class1servicePanel.add(this.class1servicestring2 = new JLabel("Mean = "));
/*  796 */       this.class1servicePanel.add(this.df_class1serviceparameter2 = new DecimalField(dblpar[3], 4, new DecimalFormat("#.####")));
/*  797 */       break;
/*      */     case 4: 
/*  799 */       this.class1servicePanel.add(this.class1servicestring1 = new JLabel("Min = "));
/*  800 */       this.class1servicePanel.add(this.df_class1serviceparameter1 = new DecimalField(dblpar[2], 4, new DecimalFormat()));
/*  801 */       this.class1servicePanel.add(this.class1servicestring2 = new JLabel("Max = "));
/*  802 */       this.class1servicePanel.add(this.df_class1serviceparameter2 = new DecimalField(dblpar[3], 4, new DecimalFormat("#.####")));
/*  803 */       break;
/*      */     }
/*      */     
/*  806 */     this.wholePanel.removeAll();
/*  807 */     if (this.classnum == 2) {
/*  808 */       this.wholePanel.add(this.class1);
/*  809 */       this.wholePanel.add(Box.createVerticalStrut(15));
/*      */     }
/*  811 */     this.wholePanel.add(this.class1arrivePanel);
/*  812 */     this.wholePanel.add(this.class1servicePanel);
/*      */     
/*  814 */     if (this.classnum == 2) {
/*  815 */       this.combox3.addItem("Constant");
/*  816 */       this.combox3.addItem("Erlang");
/*  817 */       this.combox3.addItem("Exponential");
/*  818 */       this.combox3.addItem("Translated Exp.");
/*  819 */       this.combox3.addItem("Uniform");
/*      */       
/*  821 */       this.combox3.addActionListener(new ActionListener() {
/*      */         public void actionPerformed(ActionEvent e) {
/*  823 */           IORSAEnterPanel.this.changedistribution(e);
/*      */         }
/*  825 */       });
/*  826 */       this.combox4.addItem("Constant");
/*  827 */       this.combox4.addItem("Erlang");
/*  828 */       this.combox4.addItem("Exponential");
/*  829 */       this.combox4.addItem("Translated Exp.");
/*  830 */       this.combox4.addItem("Uniform");
/*      */       
/*  832 */       this.combox4.addActionListener(new ActionListener() {
/*      */         public void actionPerformed(ActionEvent e) {
/*  834 */           IORSAEnterPanel.this.changedistribution(e);
/*      */         }
/*  836 */       });
/*  837 */       this.combox3.setSelectedIndex(comindex[2]);
/*  838 */       this.class2arrivePanel.removeAll();
/*  839 */       this.class2arrivePanel.add(this.class2arrive);
/*  840 */       this.class2arrivePanel.add(this.combox3);
/*  841 */       switch (comindex[2]) {
/*      */       case 0: 
/*  843 */         this.class2arrivePanel.add(this.class2arrivestring1 = new JLabel("Value = "));
/*  844 */         this.class2arrivePanel.add(this.df_class2arriveparameter1 = new DecimalField(dblpar[4], 4, new DecimalFormat()));
/*  845 */         break;
/*      */       case 1: 
/*  847 */         this.class2arrivePanel.add(this.class2arrivestring1 = new JLabel("Mean = "));
/*  848 */         this.class2arrivePanel.add(this.df_class2arriveparameter1 = new DecimalField(dblpar[4], 4, new DecimalFormat()));
/*  849 */         this.class2arrivePanel.add(this.class2arrivestring2 = new JLabel("K = "));
/*  850 */         this.class2arrivePanel.add(this.wnf_class2arriveparameter = new WholeNumberField(kval[2], 3));
/*  851 */         break;
/*      */       case 2: 
/*  853 */         this.class2arrivePanel.add(this.class2arrivestring1 = new JLabel("Mean = "));
/*  854 */         this.class2arrivePanel.add(this.df_class2arriveparameter1 = new DecimalField(dblpar[4], 4, new DecimalFormat()));
/*  855 */         break;
/*      */       case 3: 
/*  857 */         this.class2arrivePanel.add(this.class2arrivestring1 = new JLabel("Min = "));
/*  858 */         this.class2arrivePanel.add(this.df_class2arriveparameter1 = new DecimalField(dblpar[4], 4, new DecimalFormat()));
/*  859 */         this.class2arrivePanel.add(this.class2arrivestring2 = new JLabel("Mean = "));
/*  860 */         this.class2arrivePanel.add(this.df_class2arriveparameter2 = new DecimalField(dblpar[5], 4, new DecimalFormat("#.####")));
/*  861 */         break;
/*      */       case 4: 
/*  863 */         this.class2arrivePanel.add(this.class2arrivestring1 = new JLabel("Min = "));
/*  864 */         this.class2arrivePanel.add(this.df_class2arriveparameter1 = new DecimalField(dblpar[4], 4, new DecimalFormat()));
/*  865 */         this.class2arrivePanel.add(this.class2arrivestring2 = new JLabel("Max = "));
/*  866 */         this.class2arrivePanel.add(this.df_class2arriveparameter2 = new DecimalField(dblpar[5], 4, new DecimalFormat("#.####")));
/*  867 */         break;
/*      */       }
/*      */       
/*  870 */       this.combox4.setSelectedIndex(comindex[3]);
/*  871 */       this.class2servicePanel.removeAll();
/*  872 */       this.class2servicePanel.add(this.class2service);
/*  873 */       this.class2servicePanel.add(this.combox4);
/*  874 */       switch (comindex[3]) {
/*      */       case 0: 
/*  876 */         this.class2servicePanel.add(this.class2servicestring1 = new JLabel("Value = "));
/*  877 */         this.class2servicePanel.add(this.df_class2serviceparameter1 = new DecimalField(dblpar[6], 4, new DecimalFormat()));
/*  878 */         break;
/*      */       case 1: 
/*  880 */         this.class2servicePanel.add(this.class2servicestring1 = new JLabel("Mean = "));
/*  881 */         this.class2servicePanel.add(this.df_class2serviceparameter1 = new DecimalField(dblpar[6], 4, new DecimalFormat()));
/*  882 */         this.class2servicePanel.add(this.class2servicestring2 = new JLabel("K = "));
/*  883 */         this.class2servicePanel.add(this.wnf_class2serviceparameter = new WholeNumberField(kval[3], 3));
/*  884 */         break;
/*      */       case 2: 
/*  886 */         this.class2servicePanel.add(this.class2servicestring1 = new JLabel("Mean = "));
/*  887 */         this.class2servicePanel.add(this.df_class2serviceparameter1 = new DecimalField(dblpar[6], 4, new DecimalFormat()));
/*  888 */         break;
/*      */       case 3: 
/*  890 */         this.class2servicePanel.add(this.class2servicestring1 = new JLabel("Min = "));
/*  891 */         this.class2servicePanel.add(this.df_class2serviceparameter1 = new DecimalField(dblpar[6], 4, new DecimalFormat()));
/*  892 */         this.class2servicePanel.add(this.class2servicestring2 = new JLabel("Mean = "));
/*  893 */         this.class2servicePanel.add(this.df_class2serviceparameter2 = new DecimalField(dblpar[7], 4, new DecimalFormat("#.####")));
/*  894 */         break;
/*      */       case 4: 
/*  896 */         this.class2servicePanel.add(this.class2servicestring1 = new JLabel("Min = "));
/*  897 */         this.class2servicePanel.add(this.df_class2serviceparameter1 = new DecimalField(dblpar[6], 4, new DecimalFormat()));
/*  898 */         this.class2servicePanel.add(this.class2servicestring2 = new JLabel("Max = "));
/*  899 */         this.class2servicePanel.add(this.df_class2serviceparameter2 = new DecimalField(dblpar[7], 4, new DecimalFormat("#.####")));
/*  900 */         break;
/*      */       }
/*  902 */       this.wholePanel.add(this.class2);
/*  903 */       this.wholePanel.add(Box.createVerticalStrut(15));
/*  904 */       this.wholePanel.add(this.class2arrivePanel);
/*  905 */       this.wholePanel.add(this.class2servicePanel);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void SaveFile(ObjectOutputStream out)
/*      */   {
/*  918 */     int[] interpara = new int[2];int[] comindex = new int[4];int[] kval = new int[4];
/*  919 */     double[] dblpar = new double[8];
/*      */     
/*  921 */     interpara[0] = this.servernum;
/*  922 */     interpara[1] = this.classnum;
/*      */     
/*  924 */     comindex[0] = this.combox1.getSelectedIndex();
/*  925 */     switch (comindex[0]) {
/*      */     case 0: 
/*      */     case 2: 
/*  928 */       dblpar[0] = this.df_class1arriveparameter1.getValue();
/*  929 */       break;
/*      */     case 1: 
/*  931 */       dblpar[0] = this.df_class1arriveparameter1.getValue();
/*  932 */       kval[0] = this.wnf_class1arriveparameter.getValue();
/*  933 */       break;
/*      */     case 3: 
/*      */     case 4: 
/*  936 */       dblpar[0] = this.df_class1arriveparameter1.getValue();
/*  937 */       dblpar[1] = this.df_class1arriveparameter2.getValue();
/*  938 */       break;
/*      */     }
/*      */     
/*      */     
/*  942 */     comindex[1] = this.combox2.getSelectedIndex();
/*  943 */     switch (comindex[1]) {
/*      */     case 0: 
/*      */     case 2: 
/*  946 */       dblpar[2] = this.df_class1serviceparameter1.getValue();
/*  947 */       break;
/*      */     case 1: 
/*  949 */       dblpar[2] = this.df_class1serviceparameter1.getValue();
/*  950 */       kval[1] = this.wnf_class1serviceparameter.getValue();
/*  951 */       break;
/*      */     case 3: 
/*      */     case 4: 
/*  954 */       dblpar[2] = this.df_class1serviceparameter1.getValue();
/*  955 */       dblpar[3] = this.df_class1serviceparameter2.getValue();
/*  956 */       break;
/*      */     }
/*      */     
/*      */     
/*  960 */     if (this.classnum == 2) {
/*  961 */       comindex[2] = this.combox3.getSelectedIndex();
/*  962 */       switch (comindex[2]) {
/*      */       case 0: 
/*      */       case 2: 
/*  965 */         dblpar[4] = this.df_class2arriveparameter1.getValue();
/*  966 */         break;
/*      */       case 1: 
/*  968 */         dblpar[4] = this.df_class2arriveparameter1.getValue();
/*  969 */         kval[2] = this.wnf_class2arriveparameter.getValue();
/*  970 */         break;
/*      */       case 3: 
/*      */       case 4: 
/*  973 */         dblpar[4] = this.df_class2arriveparameter1.getValue();
/*  974 */         dblpar[5] = this.df_class2arriveparameter2.getValue();
/*  975 */         break;
/*      */       }
/*      */       
/*      */       
/*  979 */       comindex[3] = this.combox4.getSelectedIndex();
/*  980 */       switch (comindex[3]) {
/*      */       case 0: 
/*      */       case 2: 
/*  983 */         dblpar[6] = this.df_class2serviceparameter1.getValue();
/*  984 */         break;
/*      */       case 1: 
/*  986 */         dblpar[6] = this.df_class2serviceparameter1.getValue();
/*  987 */         kval[3] = this.wnf_class2serviceparameter.getValue();
/*  988 */         break;
/*      */       case 3: 
/*      */       case 4: 
/*  991 */         dblpar[6] = this.df_class2serviceparameter1.getValue();
/*  992 */         dblpar[7] = this.df_class2serviceparameter2.getValue();
/*  993 */         break;
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/*  998 */       out.writeObject(interpara);
/*  999 */       out.writeObject(comindex);
/* 1000 */       out.writeObject(dblpar);
/* 1001 */       out.writeObject(kval);
/* 1002 */       out.close();
/*      */     }
/*      */     catch (Exception e1) {
/* 1005 */       e1.printStackTrace();
/* 1006 */       System.out.println("Save fails");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPanelEnable(boolean b)
/*      */   {
/* 1015 */     this.wnf_servernum.setEditable(b);
/* 1016 */     this.wnf_classnum.setEditable(b);
/*      */     
/* 1018 */     if (this.df_class1arriveparameter1 != null) this.df_class1arriveparameter1.setEditable(b);
/* 1019 */     if (this.df_class1arriveparameter2 != null) this.df_class1arriveparameter2.setEditable(b);
/* 1020 */     if (this.df_class1serviceparameter1 != null) this.df_class1serviceparameter1.setEditable(b);
/* 1021 */     if (this.df_class1serviceparameter2 != null) { this.df_class1serviceparameter2.setEditable(b);
/*      */     }
/* 1023 */     if (this.df_class2arriveparameter1 != null) this.df_class2arriveparameter1.setEditable(b);
/* 1024 */     if (this.df_class2arriveparameter2 != null) this.df_class2arriveparameter2.setEditable(b);
/* 1025 */     if (this.df_class2serviceparameter1 != null) this.df_class2serviceparameter1.setEditable(b);
/* 1026 */     if (this.df_class2serviceparameter2 != null) { this.df_class2serviceparameter2.setEditable(b);
/*      */     }
/* 1028 */     if (this.wnf_class1arriveparameter != null) this.wnf_class1arriveparameter.setEditable(b);
/* 1029 */     if (this.wnf_class1serviceparameter != null) this.wnf_class1serviceparameter.setEditable(b);
/* 1030 */     if (this.wnf_class2arriveparameter != null) this.wnf_class2arriveparameter.setEditable(b);
/* 1031 */     if (this.wnf_class2serviceparameter != null) { this.wnf_class2serviceparameter.setEditable(b);
/*      */     }
/* 1033 */     if (this.combox1 != null) this.combox1.setEnabled(b);
/* 1034 */     if (this.combox2 != null) this.combox2.setEnabled(b);
/* 1035 */     if (this.combox3 != null) this.combox3.setEnabled(b);
/* 1036 */     if (this.combox4 != null) this.combox4.setEnabled(b);
/* 1037 */     setFinishEnabled(b);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentStepHelpPanel()
/*      */   {
/* 1044 */     String str = "Enter a Queueing Problem\n\n";
/* 1045 */     str = String.valueOf(String.valueOf(str)).concat("Entering a simulation model for a queueing problem, including one with nonpreemptive priority classes, requires entering the number of servers, the ");
/* 1046 */     str = String.valueOf(String.valueOf(str)).concat("number of priority classes, and the distributions of the interarrival times ");
/* 1047 */     str = String.valueOf(String.valueOf(str)).concat("and service times for each class of customer. The maximum number of servers and ");
/* 1048 */     str = String.valueOf(String.valueOf(str)).concat("priority classes ");
/* 1049 */     str = String.valueOf(String.valueOf(str)).concat("are as follows:\n\n");
/* 1050 */     str = String.valueOf(String.valueOf(str)).concat("     Maximum number of servers:           2.\n");
/* 1051 */     str = String.valueOf(String.valueOf(str)).concat("     Maximum number of priority classes:  2.(Choosing 1 means no priorities.)\n\n");
/* 1052 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of servers \n\n");
/* 1053 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of servers (a positive integer) in the queueing system, and ");
/* 1054 */     str = String.valueOf(String.valueOf(str)).concat("then press the ENTER key. The maximum number of servers allowed is 2.\n\n");
/* 1055 */     str = String.valueOf(String.valueOf(str)).concat("Entering the number of priority classes\n\n");
/* 1056 */     str = String.valueOf(String.valueOf(str)).concat("Enter the number of nonpreemptive priority classes (a positive integer) for customers in the ");
/* 1057 */     str = String.valueOf(String.valueOf(str)).concat("queueing system. The maximum number of nonpreemptive priority classes allowed is 2.");
/* 1058 */     str = String.valueOf(String.valueOf(str)).concat("If your queueing problem does not invole priorities, enter 1 for the number of priority classes. ");
/* 1059 */     str = String.valueOf(String.valueOf(str)).concat("See Sec. 17.8 for a discussion of priority classes.\n\n");
/* 1060 */     str = String.valueOf(String.valueOf(str)).concat("Selecting the distribution for the interarrival time\n\n");
/* 1061 */     str = String.valueOf(String.valueOf(str)).concat("The five distributions which are allowed for the interarrival time are:\n");
/* 1062 */     str = String.valueOf(String.valueOf(str)).concat("     1.  Constant(discussed in Secs. 17.2 and 17.7)\n");
/* 1063 */     str = String.valueOf(String.valueOf(str)).concat("     2.  Erlang (described in Sec. 17.7)\n");
/* 1064 */     str = String.valueOf(String.valueOf(str)).concat("     3.  Exponential (described in Sec. 17.4)\n");
/* 1065 */     str = String.valueOf(String.valueOf(str)).concat("     4.  Translated Exponential (an exponential distribution plus a positive minimum value) \n");
/* 1066 */     str = String.valueOf(String.valueOf(str)).concat("     5.  Uniform (described in Sec 22.3) \n\n");
/* 1067 */     str = String.valueOf(String.valueOf(str)).concat("Entering the value of the distribution\n\n");
/* 1068 */     str = String.valueOf(String.valueOf(str)).concat("Note that the requirements for the five distributions are as follows:\n");
/* 1069 */     str = String.valueOf(String.valueOf(str)).concat("     1.  Constant: constant > 0\n");
/* 1070 */     str = String.valueOf(String.valueOf(str)).concat("     2.  Erlang: mean > 0, k (the shape parameter) is a strictly positive integer (See Sec. 17.7)\n");
/* 1071 */     str = String.valueOf(String.valueOf(str)).concat("     3.  Exponential: mean > 0\n");
/* 1072 */     str = String.valueOf(String.valueOf(str)).concat("     4.  Translated Exponential: min > 0, mean > min\n");
/* 1073 */     str = String.valueOf(String.valueOf(str)).concat("     5.  Uniform: min > 0, max > min\n\n");
/* 1074 */     str = String.valueOf(String.valueOf(str)).concat("Selecting the distribution for the service time\n\n");
/* 1075 */     str = String.valueOf(String.valueOf(str)).concat("The five distributions which are allowed for the service time are:\n");
/* 1076 */     str = String.valueOf(String.valueOf(str)).concat("     1.  Constant (discussed in Secs. 17.2 and 17.7)\n");
/* 1077 */     str = String.valueOf(String.valueOf(str)).concat("     2.  Erlang (described in Sec. 17.7)\n");
/* 1078 */     str = String.valueOf(String.valueOf(str)).concat("     3.  Exponential (described in Sec. 17.4)\n");
/* 1079 */     str = String.valueOf(String.valueOf(str)).concat("     4.  Translated Exponential (an exponential distribution plus a positive minimum value)\n");
/* 1080 */     str = String.valueOf(String.valueOf(str)).concat("     5.  Uniform (described in Sec.22.3) \n\n");
/* 1081 */     str = String.valueOf(String.valueOf(str)).concat("Entering the value for the service time\n\n");
/* 1082 */     str = String.valueOf(String.valueOf(str)).concat("     1. Constant: constant > 0\n");
/* 1083 */     str = String.valueOf(String.valueOf(str)).concat("     2.  Erlang: mean > 0, k (the shape parameter) is a strictly positive integer (see Sec. 17.7)\n");
/* 1084 */     str = String.valueOf(String.valueOf(str)).concat("     3.  Exponential: mean > 0\n");
/* 1085 */     str = String.valueOf(String.valueOf(str)).concat("     4.  Translated Exponential: min > 0, mean > 0, mean > min\n");
/* 1086 */     str = String.valueOf(String.valueOf(str)).concat("     5.  Uniform: min > 0, max > min");
/* 1087 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/* 1089 */     this.help_content_step.setText(str);
/* 1090 */     this.help_content_step.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void initializeCurrentProcedureHelpPanel()
/*      */   {
/* 1097 */     String str = " ";
/* 1098 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the ENTER key to continue the current procedure.");
/*      */     
/* 1100 */     String str0 = "Enter a Queueing Problem\n\n";
/* 1101 */     str0 = String.valueOf(String.valueOf(str0)).concat("This procedure enables you to quickly enter a queueing problem, or to revise a previously ");
/* 1102 */     str0 = String.valueOf(String.valueOf(str0)).concat("entered problem, for the purposes of performing a simulation run. You will need to enter ");
/* 1103 */     str0 = String.valueOf(String.valueOf(str0)).concat("the number of servers, the number of nonpreemptive priority classes, and the distributions of the interarrival ");
/* 1104 */     str0 = String.valueOf(String.valueOf(str0)).concat("times and service times for each class of customer. The maximum number of servers is 2, and the maximum ");
/* 1105 */     str0 = String.valueOf(String.valueOf(str0)).concat("number of nonpreemptive priority classes is 2 (where 1 means there are no priorities).\n\n");
/* 1106 */     str0 = String.valueOf(String.valueOf(str0)).concat("At any step, detailed computer instructions are available (Help menu). To undo a mistake, backtrack by pressing ");
/* 1107 */     str0 = String.valueOf(String.valueOf(str0)).concat("the BACK button.\n\n");
/* 1108 */     str0 = String.valueOf(String.valueOf(str0)).concat("When you finish entering the model, press the FINISH button, and then choose the other procedure under the Procedure menu to continue.\n\n");
/* 1109 */     str0 = String.valueOf(String.valueOf(str0)).concat("When you press the FINISH button, make sure that none of your numbers for the distributions changed. If any did, ");
/* 1110 */     str0 = String.valueOf(String.valueOf(str0)).concat("it is because you had entered an invalid number (for example, a negative mean). In each such case, you should go ");
/* 1111 */     str0 = String.valueOf(String.valueOf(str0)).concat("back and re-enter a valid number after referring to Specific Help on Current Step in the Help menu for instructions on the requirements to be a valid number.");
/* 1112 */     str0 = String.valueOf(String.valueOf(str0)).concat("\n\n");
/*      */     
/* 1114 */     this.help_content_procedure.setText(String.valueOf(String.valueOf(str0)).concat(String.valueOf(String.valueOf(str))));
/* 1115 */     this.help_content_procedure.revalidate();
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORSAEnterPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */