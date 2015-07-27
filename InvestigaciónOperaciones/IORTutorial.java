/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.net.URL;
/*      */ import java.net.URLClassLoader;
/*      */ import java.util.Hashtable;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuBar;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JRadioButtonMenuItem;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTextPane;
/*      */ import javax.swing.KeyStroke;
/*      */ 
/*      */ public class IORTutorial extends JFrame
/*      */ {
/*   46 */   public JScrollPane scrollPane = null;
/*      */   
/*   48 */   public JLabel statusBar = null;
/*      */   
/*   50 */   public JMenu procedure = null;
/*      */   
/*   52 */   public JMenu option = null;
/*      */   
/*      */ 
/*      */ 
/*   56 */   public JMenu play = null;
/*      */   
/*      */   private String demoDirectory;
/*   59 */   public ButtonGroup playG = new ButtonGroup();
/*      */   
/*      */   public IORTestDemoPanel demoPanel;
/*      */   
/*      */   private String DemoItemName1;
/*      */   
/*      */   private String DemoItemName2;
/*      */   private String DemoItemName3;
/*      */   private String DemoItemName4;
/*      */   private String DemoItemName5;
/*      */   private String DemoItemName6;
/*   70 */   public JMenu help = null;
/*      */   
/*   72 */   public JPanel helpPanel = null;
/*      */   
/*   74 */   public Font defaultFont = new Font("SansSerif", 0, 17);
/*      */   
/*   76 */   private IORProcessController controller = null;
/*   77 */   private IORTPCanvasPanel tp = null;
/*      */   
/*   79 */   private JMenuBar menuBar = null;
/*   80 */   private JFrame frame = null;
/*      */   
/*      */   public Hashtable cache;
/*   83 */   JTextPane jTextPane1 = new JTextPane();
/*   84 */   JPanel panel = null;
/*   85 */   int i = 0;
/*      */   
/*   87 */   private IORTutorial.TutorialHelpPanel tutorialHelpPanel = null;
/*      */   
/*      */   ImageIcon img;
/*      */   
/*      */   public IORTutorial()
/*      */   {
/*   93 */     this.frame = new JFrame("Interactive Operations Research Tutorial");
/*   94 */     this.controller = new IORLPProcessController(this);
/*   95 */     createMenus();
/*   96 */     this.frame.setJMenuBar(this.menuBar);
/*      */     
/*   98 */     this.scrollPane = new JScrollPane();
/*   99 */     this.frame.getContentPane().add(this.scrollPane, "Center");
/*      */     
/*  101 */     String str = "Other options\n\nIf you wish to quit, choose Quit under the File menu.\n\n\nChoosing an Area and Procedure\n\nAt the present moment, no particular procedure is being executed. To choose a procedure to execute, select one under the Procedure menu. If you wish to change the area you are in (\"General Linear Programming\", \"Transportation Problem\", \"Network Analysis\", etc.), then choose the new area under the Area menu.";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  108 */     MultilineLabel m = new MultilineLabel(str);
/*  109 */     this.tutorialHelpPanel = new IORTutorial.TutorialHelpPanel(m, this.scrollPane);
/*      */     
/*  111 */     firstpage();
/*      */     
/*  113 */     this.statusBar = new JLabel("Status Bar");
/*  114 */     this.frame.getContentPane().add(this.statusBar, "South");
/*      */     
/*      */ 
/*  117 */     this.controller.createAreaDependentMenus();
/*      */     
/*  119 */     this.frame.addWindowListener(new WindowAdapter() {
/*      */       public void windowClosing(WindowEvent e) {
/*  121 */         System.exit(0);
/*      */       }
/*  123 */     });
/*  124 */     Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
/*  125 */     int h = d.height * 7 / 8;
/*  126 */     int w = (int)(h * 0.95D);
/*  127 */     this.frame.setSize(new Dimension(w, h));
/*  128 */     this.frame.setLocation(d.width / 2 - w / 2, d.height / 2 - h / 2);
/*  129 */     this.frame.setVisible(true);
/*      */     try
/*      */     {
/*  132 */       jbInit();
/*      */     }
/*      */     catch (Exception e) {
/*  135 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*  139 */   private static String signature = "IORTutorial. Developed By Acceleon Corporation in October 2000.";
/*      */   
/*      */   private int getSavingFileType(String file)
/*      */   {
/*      */     try {
/*  144 */       ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
/*  145 */       String str = (String)in.readObject();
/*  146 */       in.close();
/*      */     }
/*      */     catch (FileNotFoundException e1) {
/*  149 */       return 0;
/*      */     }
/*      */     catch (StreamCorruptedException e2) {
/*  152 */       return 1;
/*      */     } catch (Exception e3) {
/*      */       int j;
/*  155 */       return 0; }
/*      */     String str;
/*      */     ObjectInputStream in;
/*  158 */     if (signature.equalsIgnoreCase(str)) {
/*  159 */       return 2;
/*      */     }
/*      */     
/*  162 */     return 1;
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean hasSignature(ObjectInputStream in)
/*      */   {
/*  168 */     System.out.println("signature=".concat(String.valueOf(String.valueOf(signature))));
/*      */     try {
/*  170 */       str = (String)in.readObject();
/*      */     } catch (Exception e) {
/*      */       String str;
/*  173 */       System.out.println("false");
/*  174 */       return false; }
/*      */     String str;
/*  176 */     return signature.equalsIgnoreCase(str);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String filenameChosenByUser(boolean forOpen)
/*      */   {
/*  184 */     JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
/*  185 */     int result = forOpen ? fc.showOpenDialog(this.frame) : fc.showSaveDialog(this.frame);
/*  186 */     File chosenFile = fc.getSelectedFile();
/*  187 */     if ((result == 0) && (chosenFile != null))
/*  188 */       return chosenFile.getPath();
/*  189 */     return null;
/*      */   }
/*      */   
/*      */   private void createMenus() {
/*  193 */     IORTutorial current = this;
/*  194 */     if (this.controller == null)
/*  195 */       return;
/*  196 */     this.menuBar = new JMenuBar();
/*      */     
/*      */ 
/*  199 */     int menuMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
/*      */     
/*      */ 
/*  202 */     JMenu m = new JMenu("File  ");
/*  203 */     m.setMnemonic('F');
/*      */     JMenuItem mi;
/*  205 */     m.add(mi = new JMenuItem("New"));
/*  206 */     mi.setMnemonic('N');
/*  207 */     mi.setEnabled(true);
/*  208 */     mi.setAccelerator(KeyStroke.getKeyStroke(78, menuMask));
/*  209 */     mi.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  211 */         IORTutorial.this.controller.newFile();
/*      */       }
/*      */       
/*  214 */     });
/*  215 */     m.add(mi = new JMenuItem("Open"));
/*  216 */     mi.setMnemonic('O');
/*  217 */     mi.setEnabled(true);
/*  218 */     mi.setAccelerator(KeyStroke.getKeyStroke(79, menuMask));
/*  219 */     mi.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  221 */         String file = IORTutorial.this.filenameChosenByUser(true);
/*  222 */         if (file == null) return;
/*      */         try {
/*  224 */           ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
/*  225 */           if (!IORTutorial.this.hasSignature(in)) {
/*  226 */             JOptionPane.showMessageDialog(IORTutorial.this.frame, "This is not a valid saving file.", "Information", 1);
/*  227 */             in.close();
/*  228 */             return;
/*      */           }
/*  230 */           IORTutorial.this.controller.removeAreaDependentMenus();
/*  231 */           int WhichArea = ((Integer)in.readObject()).intValue();
/*  232 */           switch (WhichArea) {
/*      */           case 1: 
/*  234 */             IORTutorial.this.controller = new IORLPProcessController(IORTutorial.this);
/*  235 */             IORTutorial.this.menuBar.getMenu(1).getItem(0).setSelected(true);
/*  236 */             break;
/*      */           case 2: 
/*  238 */             IORTutorial.this.controller = new IORTPProcessController(IORTutorial.this);
/*  239 */             IORTutorial.this.menuBar.getMenu(1).getItem(1).setSelected(true);
/*  240 */             break;
/*      */           case 3: 
/*  242 */             IORTutorial.this.controller = new IORNAProcessController(IORTutorial.this);
/*  243 */             IORTutorial.this.menuBar.getMenu(1).getItem(3).setSelected(true);
/*  244 */             break;
/*      */           case 4: 
/*  246 */             IORTutorial.this.controller = new IORIPProcessController(IORTutorial.this);
/*  247 */             IORTutorial.this.menuBar.getMenu(1).getItem(4).setSelected(true);
/*  248 */             break;
/*      */           case 5: 
/*  250 */             IORTutorial.this.controller = new IORNLPProcessController(IORTutorial.this);
/*  251 */             IORTutorial.this.menuBar.getMenu(1).getItem(5).setSelected(true);
/*  252 */             break;
/*      */           case 6: 
/*  254 */             IORTutorial.this.controller = new IORMCProcessController(IORTutorial.this);
/*  255 */             IORTutorial.this.menuBar.getMenu(1).getItem(8).setSelected(true);
/*  256 */             break;
/*      */           case 7: 
/*  258 */             IORTutorial.this.controller = new IORQTProcessController(IORTutorial.this);
/*  259 */             IORTutorial.this.menuBar.getMenu(1).getItem(9).setSelected(true);
/*  260 */             break;
/*      */           case 8: 
/*  262 */             IORTutorial.this.controller = new IORITProcessController(IORTutorial.this);
/*  263 */             IORTutorial.this.menuBar.getMenu(1).getItem(10).setSelected(true);
/*  264 */             break;
/*      */           case 9: 
/*  266 */             IORTutorial.this.controller = new IORMDProcessController(IORTutorial.this);
/*  267 */             IORTutorial.this.menuBar.getMenu(1).getItem(11).setSelected(true);
/*  268 */             break;
/*      */           case 10: 
/*  270 */             IORTutorial.this.controller = new IORSAProcessController(IORTutorial.this);
/*  271 */             IORTutorial.this.menuBar.getMenu(1).getItem(12).setSelected(true);
/*  272 */             break;
/*      */           case 11: 
/*  274 */             IORTutorial.this.controller = new IORSAProcessController(IORTutorial.this);
/*  275 */             IORTutorial.this.menuBar.getMenu(1).getItem(13).setSelected(true);
/*  276 */             break;
/*      */           case 12: 
/*  278 */             IORTutorial.this.controller = new IORTSPProcessController(IORTutorial.this);
/*  279 */             IORTutorial.this.menuBar.getMenu(1).getItem(13).setSelected(true);
/*  280 */             break;
/*      */           case 13: 
/*  282 */             IORTutorial.this.controller = new IORHungarianProcessController(IORTutorial.this);
/*  283 */             IORTutorial.this.menuBar.getMenu(1).getItem(2).setSelected(true);
/*  284 */             break;
/*      */           case 14: 
/*  286 */             IORTutorial.this.controller = new IORGraphicalProcessController(IORTutorial.this);
/*  287 */             IORTutorial.this.menuBar.getMenu(1).getItem(14).setSelected(true);
/*  288 */             break;
/*      */           default: 
/*  290 */             System.out.println("Open fails");
/*  291 */             return;
/*      */           }
/*      */           
/*  294 */           IORTutorial.this.controller.createAreaDependentMenus();
/*  295 */           IORTutorial.this.controller.LoadFile(in);
/*  296 */           in.close();
/*      */         }
/*      */         catch (FileNotFoundException e1) {
/*  299 */           e1.printStackTrace();
/*  300 */           System.out.println("Open fails Exception1 : ".concat(String.valueOf(String.valueOf(e1))));
/*      */         }
/*      */         catch (StreamCorruptedException e2) {
/*  303 */           JOptionPane.showMessageDialog(IORTutorial.this.frame, "This is not a valid saving file.", "Information", 1);
/*      */         } catch (IOException e3) {
/*  305 */           System.out.println("IOException : ".concat(String.valueOf(String.valueOf(e3))));
/*      */         } catch (ClassNotFoundException e3) {
/*  307 */           System.out.println("ClassNotFoundException : ".concat(String.valueOf(String.valueOf(e3))));
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  316 */     });
/*  317 */     m.add(mi = new JMenuItem("Save"));
/*  318 */     mi.setMnemonic('S');
/*  319 */     mi.setEnabled(true);
/*  320 */     mi.setAccelerator(KeyStroke.getKeyStroke(83, menuMask));
/*  321 */     mi.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  323 */         if ((IORTutorial.this.controller == null) || (IORTutorial.this.controller.currentActionPanel == null)) {
/*  324 */           System.out.println("Can't save file. null");
/*  325 */           return;
/*      */         }
/*  327 */         String file = IORTutorial.this.filenameChosenByUser(false);
/*  328 */         if (file == null)
/*  329 */           return;
/*  330 */         int fileType = IORTutorial.this.getSavingFileType(file);
/*  331 */         if (fileType == 1) {
/*  332 */           JOptionPane.showMessageDialog(IORTutorial.this.frame, "Can't overwrite this file.", "Information", 1);
/*  333 */           return;
/*      */         }
/*  335 */         if (fileType == 2) {}
/*      */         try
/*      */         {
/*  338 */           ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
/*  339 */           out.writeObject(IORTutorial.signature);
/*  340 */           if ((IORTutorial.this.controller instanceof IORLPProcessController)) {
/*  341 */             out.writeObject(new Integer(1));
/*  342 */           } else if ((IORTutorial.this.controller instanceof IORTPProcessController)) {
/*  343 */             out.writeObject(new Integer(2));
/*  344 */           } else if ((IORTutorial.this.controller instanceof IORNAProcessController)) {
/*  345 */             out.writeObject(new Integer(3));
/*  346 */           } else if ((IORTutorial.this.controller instanceof IORIPProcessController)) {
/*  347 */             out.writeObject(new Integer(4));
/*  348 */           } else if ((IORTutorial.this.controller instanceof IORNLPProcessController)) {
/*  349 */             out.writeObject(new Integer(5));
/*  350 */           } else if ((IORTutorial.this.controller instanceof IORMCProcessController)) {
/*  351 */             out.writeObject(new Integer(6));
/*  352 */           } else if ((IORTutorial.this.controller instanceof IORQTProcessController)) {
/*  353 */             out.writeObject(new Integer(7));
/*  354 */           } else if ((IORTutorial.this.controller instanceof IORITProcessController)) {
/*  355 */             out.writeObject(new Integer(8));
/*  356 */           } else if ((IORTutorial.this.controller instanceof IORMDProcessController)) {
/*  357 */             out.writeObject(new Integer(9));
/*  358 */           } else if ((IORTutorial.this.controller instanceof IORSAProcessController)) {
/*  359 */             out.writeObject(new Integer(10));
/*  360 */           } else if ((IORTutorial.this.controller instanceof IORTSPProcessController)) {
/*  361 */             out.writeObject(new Integer(12));
/*  362 */           } else if ((IORTutorial.this.controller instanceof IORHungarianProcessController)) {
/*  363 */             out.writeObject(new Integer(13));
/*      */           }
/*  365 */           else if ((IORTutorial.this.controller instanceof IORGraphicalProcessController)) {
/*  366 */             out.writeObject(new Integer(14));
/*      */           } else {
/*  368 */             System.out.println("Can't Save file.");
/*  369 */             return;
/*      */           }
/*  371 */           IORTutorial.this.controller.SaveFile(out);
/*  372 */           out.close();
/*      */         }
/*      */         catch (Exception e1) {
/*  375 */           e1.printStackTrace();
/*  376 */           System.out.println("Save fails is".concat(String.valueOf(String.valueOf(e1))));
/*      */ 
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  386 */     });
/*  387 */     m.add(mi = new JMenuItem("Print to File"));
/*  388 */     mi.setMnemonic('t');
/*  389 */     mi.setEnabled(true);
/*  390 */     mi.setAccelerator(KeyStroke.getKeyStroke(80, menuMask));
/*  391 */     mi.addActionListener(new ActionListener() {
/*  392 */       public void actionPerformed(ActionEvent e) { IORTutorial.this.controller.printToFile(); }
/*  393 */     });
/*  394 */     m.addSeparator();
/*  395 */     m.add(mi = new JMenuItem("Quit"));
/*  396 */     mi.setMnemonic('Q');
/*  397 */     mi.setAccelerator(KeyStroke.getKeyStroke(81, menuMask));
/*  398 */     mi.addActionListener(new ActionListener() {
/*  399 */       public void actionPerformed(ActionEvent e) { System.exit(0); }
/*  400 */     });
/*  401 */     this.menuBar.add(m);
/*      */     
/*  403 */     m = new JMenu("Area  ");
/*  404 */     m.setMnemonic('A');
/*  405 */     ButtonGroup group = new ButtonGroup();
/*  406 */     m.add(mi = new JRadioButtonMenuItem("General Linear Programming"));
/*  407 */     mi.setMnemonic('G');
/*  408 */     mi.setSelected(true);
/*      */     
/*  410 */     mi.setMnemonic(71);
/*  411 */     group.add(mi);
/*  412 */     m.add(mi);
/*  413 */     mi.addActionListener(new ActionListener() { private final IORTutorial val$current;
/*      */       
/*  415 */       public void actionPerformed(ActionEvent e) { IORTutorial.this.controller.removeAreaDependentMenus();
/*  416 */         IORTutorial.this.controller = new IORLPProcessController(this.val$current);
/*  417 */         IORTutorial.this.controller.createAreaDependentMenus();
/*  418 */         IORTutorial.this.controller.setStatusBar();
/*  419 */         IORTutorial.this.controller.showAreaHelp();
/*      */         
/*  421 */         IORTutorial.this.destroyDemoPanel();
/*  422 */         IORTutorial.this.readDemoItem("demo/GeneralLinear/demoName.txt", "GeneralLinear");
/*      */       }
/*      */       
/*  425 */     });
/*  426 */     m.add(mi = new JRadioButtonMenuItem("The Transportation Problem"));
/*  427 */     mi.setMnemonic('T');
/*  428 */     mi.setMnemonic(84);
/*  429 */     mi.setEnabled(true);
/*  430 */     group.add(mi);
/*  431 */     m.add(mi);
/*  432 */     mi.addActionListener(new ActionListener() { private final IORTutorial val$current;
/*      */       
/*  434 */       public void actionPerformed(ActionEvent e) { IORTutorial.this.controller.removeAreaDependentMenus();
/*  435 */         IORTutorial.this.controller = new IORTPProcessController(this.val$current);
/*  436 */         IORTutorial.this.controller.createAreaDependentMenus();
/*  437 */         IORTutorial.this.controller.setStatusBar();
/*  438 */         IORTutorial.this.controller.showAreaHelp();
/*  439 */         IORTutorial.this.destroyDemoPanel();
/*  440 */         IORTutorial.this.readDemoItem("demo/TransportationProblem/demoName.txt", "TransportationProblem");
/*      */       }
/*      */       
/*  443 */     });
/*  444 */     m.add(mi = new JRadioButtonMenuItem("The Assignment Problem"));
/*  445 */     mi.setMnemonic('H');
/*  446 */     mi.setMnemonic(72);
/*  447 */     mi.setEnabled(true);
/*  448 */     group.add(mi);
/*  449 */     m.add(mi);
/*  450 */     mi.addActionListener(new ActionListener() { private final IORTutorial val$current;
/*      */       
/*  452 */       public void actionPerformed(ActionEvent e) { IORTutorial.this.controller.removeAreaDependentMenus();
/*  453 */         IORTutorial.this.controller = new IORHungarianProcessController(this.val$current);
/*  454 */         IORTutorial.this.controller.createAreaDependentMenus();
/*  455 */         IORTutorial.this.controller.setStatusBar();
/*  456 */         IORTutorial.this.controller.showAreaHelp();
/*      */         
/*  458 */         IORTutorial.this.destroyDemoPanel();
/*  459 */         IORTutorial.this.readDemoItem("demo/HungarianMethod/demoName.txt", "HungarianMethod");
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*  464 */     });
/*  465 */     m.add(mi = new JRadioButtonMenuItem("Network Analysis"));
/*  466 */     mi.setMnemonic('N');
/*  467 */     mi.setMnemonic(78);
/*  468 */     mi.setEnabled(true);
/*  469 */     group.add(mi);
/*  470 */     m.add(mi);
/*  471 */     mi.addActionListener(new ActionListener() { private final IORTutorial val$current;
/*      */       
/*  473 */       public void actionPerformed(ActionEvent e) { IORTutorial.this.controller.removeAreaDependentMenus();
/*  474 */         IORTutorial.this.controller = new IORNAProcessController(this.val$current);
/*  475 */         IORTutorial.this.controller.createAreaDependentMenus();
/*  476 */         IORTutorial.this.controller.setStatusBar();
/*  477 */         IORTutorial.this.controller.showAreaHelp();
/*  478 */         IORTutorial.this.destroyDemoPanel();
/*  479 */         IORTutorial.this.readDemoItem("demo/NetworkAnalysis/demoName.txt", "NetworkAnalysis");
/*      */       }
/*      */       
/*  482 */     });
/*  483 */     m.add(mi = new JRadioButtonMenuItem("Integer Programming"));
/*  484 */     mi.setMnemonic('I');
/*  485 */     mi.setMnemonic(73);
/*  486 */     mi.setEnabled(true);
/*  487 */     group.add(mi);
/*  488 */     m.add(mi);
/*  489 */     mi.addActionListener(new ActionListener() { private final IORTutorial val$current;
/*      */       
/*  491 */       public void actionPerformed(ActionEvent e) { IORTutorial.this.controller.removeAreaDependentMenus();
/*  492 */         IORTutorial.this.controller = new IORIPProcessController(this.val$current);
/*  493 */         IORTutorial.this.controller.createAreaDependentMenus();
/*  494 */         IORTutorial.this.controller.setStatusBar();
/*  495 */         IORTutorial.this.controller.showAreaHelp();
/*      */         
/*  497 */         IORTutorial.this.destroyDemoPanel();
/*  498 */         IORTutorial.this.readDemoItem("demo/IntegerProgramming/demoName.txt", "IntegerProgramming");
/*      */       }
/*      */       
/*      */ 
/*  502 */     });
/*  503 */     m.add(mi = new JRadioButtonMenuItem("Nonlinear Programming"));
/*  504 */     mi.setMnemonic('P');
/*  505 */     mi.setMnemonic(80);
/*  506 */     mi.setEnabled(true);
/*  507 */     group.add(mi);
/*  508 */     m.add(mi);
/*  509 */     mi.addActionListener(new ActionListener() { private final IORTutorial val$current;
/*      */       
/*  511 */       public void actionPerformed(ActionEvent e) { IORTutorial.this.controller.removeAreaDependentMenus();
/*  512 */         IORTutorial.this.controller = new IORNLPProcessController(this.val$current);
/*  513 */         IORTutorial.this.controller.createAreaDependentMenus();
/*  514 */         IORTutorial.this.controller.setStatusBar();
/*  515 */         IORTutorial.this.controller.showAreaHelp();
/*      */         
/*  517 */         IORTutorial.this.destroyDemoPanel();
/*  518 */         IORTutorial.this.readDemoItem("demo/NonlinearProgramming/demoName.txt", "NonlinearProgramming");
/*      */       }
/*  520 */     });
/*  521 */     m.addSeparator();
/*  522 */     m.addSeparator();
/*      */     
/*      */ 
/*  525 */     m.add(mi = new JRadioButtonMenuItem("Markov Chains"));
/*  526 */     mi.setMnemonic('M');
/*  527 */     mi.setMnemonic(77);
/*  528 */     mi.setEnabled(true);
/*  529 */     group.add(mi);
/*  530 */     m.add(mi);
/*  531 */     mi.addActionListener(new ActionListener() { private final IORTutorial val$current;
/*      */       
/*  533 */       public void actionPerformed(ActionEvent e) { IORTutorial.this.controller.removeAreaDependentMenus();
/*  534 */         IORTutorial.this.controller = new IORMCProcessController(this.val$current);
/*  535 */         IORTutorial.this.controller.createAreaDependentMenus();
/*  536 */         IORTutorial.this.controller.setStatusBar();
/*  537 */         IORTutorial.this.controller.showAreaHelp();
/*      */         
/*  539 */         IORTutorial.this.destroyDemoPanel();
/*  540 */         IORTutorial.this.readDemoItem("demo/MarkovChains/demoName.txt", "MarkovChains");
/*      */       }
/*      */       
/*  543 */     });
/*  544 */     this.menuBar.add(m);
/*      */     
/*      */ 
/*  547 */     m.add(mi = new JRadioButtonMenuItem("Queueing Theory"));
/*  548 */     mi.setMnemonic('Q');
/*  549 */     mi.setMnemonic(81);
/*  550 */     mi.setEnabled(true);
/*  551 */     group.add(mi);
/*  552 */     m.add(mi);
/*  553 */     mi.addActionListener(new ActionListener() { private final IORTutorial val$current;
/*      */       
/*  555 */       public void actionPerformed(ActionEvent e) { IORTutorial.this.controller.removeAreaDependentMenus();
/*  556 */         IORTutorial.this.controller = new IORQTProcessController(this.val$current);
/*  557 */         IORTutorial.this.controller.createAreaDependentMenus();
/*  558 */         IORTutorial.this.controller.showAreaHelp();
/*  559 */         IORTutorial.this.controller.setStatusBar();
/*      */         
/*  561 */         IORTutorial.this.destroyDemoPanel();
/*  562 */         IORTutorial.this.readDemoItem("demo/QueueingTheory/demoName.txt", "QueueingTheory");
/*      */       }
/*      */       
/*  565 */     });
/*  566 */     this.menuBar.add(m);
/*      */     
/*      */ 
/*  569 */     m.add(mi = new JRadioButtonMenuItem("Inventory Theory"));
/*  570 */     mi.setMnemonic('V');
/*  571 */     mi.setMnemonic(86);
/*  572 */     mi.setEnabled(true);
/*  573 */     group.add(mi);
/*  574 */     m.add(mi);
/*  575 */     mi.addActionListener(new ActionListener() { private final IORTutorial val$current;
/*      */       
/*  577 */       public void actionPerformed(ActionEvent e) { IORTutorial.this.controller.removeAreaDependentMenus();
/*  578 */         IORTutorial.this.controller = new IORITProcessController(this.val$current);
/*  579 */         IORTutorial.this.controller.createAreaDependentMenus();
/*  580 */         IORTutorial.this.controller.setStatusBar();
/*  581 */         IORTutorial.this.controller.showAreaHelp();
/*      */         
/*  583 */         IORTutorial.this.destroyDemoPanel();
/*  584 */         IORTutorial.this.readDemoItem("demo/InventoryTheory/demoName.txt", "InventoryTheory");
/*      */       }
/*  586 */     });
/*  587 */     this.menuBar.add(m);
/*      */     
/*      */ 
/*  590 */     m.add(mi = new JRadioButtonMenuItem("Markov Decision Processes"));
/*  591 */     mi.setMnemonic('D');
/*  592 */     mi.setMnemonic(68);
/*  593 */     mi.setEnabled(true);
/*  594 */     group.add(mi);
/*  595 */     m.add(mi);
/*  596 */     mi.addActionListener(new ActionListener() { private final IORTutorial val$current;
/*      */       
/*  598 */       public void actionPerformed(ActionEvent e) { IORTutorial.this.controller.removeAreaDependentMenus();
/*  599 */         IORTutorial.this.controller = new IORMDProcessController(this.val$current);
/*  600 */         IORTutorial.this.controller.createAreaDependentMenus();
/*  601 */         IORTutorial.this.controller.setStatusBar();
/*  602 */         IORTutorial.this.controller.showAreaHelp();
/*      */         
/*  604 */         IORTutorial.this.destroyDemoPanel();
/*  605 */         IORTutorial.this.readDemoItem("demo/MarkovDecision/demoName.txt", "MarkovDecision");
/*      */       }
/*      */       
/*  608 */     });
/*  609 */     this.menuBar.add(m);
/*      */     
/*      */ 
/*  612 */     m.add(mi = new JRadioButtonMenuItem("Simulation"));
/*  613 */     mi.setMnemonic('S');
/*  614 */     mi.setMnemonic(83);
/*  615 */     mi.setEnabled(true);
/*  616 */     group.add(mi);
/*  617 */     m.add(mi);
/*  618 */     mi.addActionListener(new ActionListener() { private final IORTutorial val$current;
/*      */       
/*  620 */       public void actionPerformed(ActionEvent e) { IORTutorial.this.controller.removeAreaDependentMenus();
/*  621 */         IORTutorial.this.controller = new IORSAProcessController(this.val$current);
/*  622 */         IORTutorial.this.controller.createAreaDependentMenus();
/*      */         
/*  624 */         IORTutorial.this.controller.setStatusBar();
/*  625 */         IORTutorial.this.controller.showAreaHelp();
/*      */         
/*  627 */         IORTutorial.this.destroyDemoPanel();
/*  628 */         IORTutorial.this.readDemoItem("demo/Simulation/demoName.txt", "Simulation");
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*  633 */     });
/*  634 */     m.add(mi = new JRadioButtonMenuItem("Metaheuristics"));
/*  635 */     mi.setMnemonic('M');
/*  636 */     mi.setMnemonic(77);
/*  637 */     mi.setEnabled(true);
/*  638 */     group.add(mi);
/*  639 */     m.add(mi);
/*  640 */     mi.addActionListener(new ActionListener() { private final IORTutorial val$current;
/*      */       
/*  642 */       public void actionPerformed(ActionEvent e) { IORTutorial.this.controller.removeAreaDependentMenus();
/*  643 */         IORTutorial.this.controller = new IORTSPProcessController(this.val$current);
/*      */         
/*  645 */         IORTutorial.this.controller.createAreaDependentMenus();
/*  646 */         IORTutorial.this.controller.setStatusBar();
/*  647 */         IORTutorial.this.controller.showAreaHelp();
/*      */         
/*  649 */         IORTutorial.this.destroyDemoPanel();
/*      */         
/*  651 */         IORTutorial.this.readDemoItem("demo/Metaheuristics/demoName.txt", "Metaheuristics");
/*      */       }
/*      */       
/*      */ 
/*  655 */     });
/*  656 */     m.add(mi = new JRadioButtonMenuItem("Forecasting"));
/*  657 */     mi.setMnemonic('G');
/*  658 */     mi.setMnemonic(77);
/*  659 */     mi.setEnabled(true);
/*  660 */     group.add(mi);
/*  661 */     m.add(mi);
/*  662 */     mi.addActionListener(new ActionListener() { private final IORTutorial val$current;
/*      */       
/*  664 */       public void actionPerformed(ActionEvent e) { IORTutorial.this.controller.removeAreaDependentMenus();
/*  665 */         IORTutorial.this.controller = new IORGraphicalProcessController(this.val$current);
/*  666 */         IORTutorial.this.controller.createAreaDependentMenus();
/*  667 */         IORTutorial.this.controller.setStatusBar();
/*  668 */         IORTutorial.this.controller.showAreaHelp();
/*      */         
/*  670 */         IORTutorial.this.destroyDemoPanel();
/*      */         
/*  672 */         IORTutorial.this.readDemoItem("demo/Forecasting/demoName.txt", "Forecasting");
/*      */       }
/*      */       
/*      */ 
/*  676 */     });
/*  677 */     this.menuBar.add(m);
/*      */     
/*  679 */     this.procedure = new JMenu("Procedure  ");
/*  680 */     this.procedure.setMnemonic('P');
/*  681 */     this.menuBar.add(this.procedure);
/*      */     
/*  683 */     this.option = new JMenu("Option  ");
/*  684 */     this.option.setMnemonic('O');
/*  685 */     this.menuBar.add(this.option);
/*      */     
/*  687 */     this.play = new JMenu("Demo");
/*  688 */     this.play.setMnemonic('P');
/*  689 */     this.menuBar.add(this.play);
/*  690 */     this.help = new JMenu("Help  ");
/*  691 */     this.help.setMnemonic('H');
/*  692 */     this.help.setEnabled(true);
/*  693 */     this.help.add(mi = new JMenuItem("Specific Help on Current Step"));
/*  694 */     mi.setMnemonic('S');
/*  695 */     mi.setEnabled(true);
/*  696 */     mi.setAccelerator(KeyStroke.getKeyStroke(72, menuMask));
/*      */     
/*  698 */     mi.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  700 */         JPanel p = IORTutorial.this.controller.getCurrentStepHelpPanel();
/*  701 */         if (p == null) {
/*  702 */           p = IORTutorial.this.tutorialHelpPanel;
/*      */         }
/*  704 */         IORTutorial.this.scrollPane.setViewportView(p);
/*  705 */         IORTutorial.this.scrollPane.repaint();
/*  706 */         p.requestFocus();
/*  707 */         p.revalidate();
/*      */       }
/*  709 */     });
/*  710 */     this.help.add(mi = new JMenuItem("General Introduction on Current Procedure"));
/*  711 */     mi.setMnemonic('G');
/*  712 */     mi.setEnabled(true);
/*  713 */     mi.setAccelerator(KeyStroke.getKeyStroke(71, menuMask));
/*  714 */     mi.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  716 */         JPanel p = IORTutorial.this.controller.getCurrentProcedureHelpPanel();
/*  717 */         if (p == null) {
/*  718 */           p = IORTutorial.this.tutorialHelpPanel;
/*      */         }
/*      */         
/*  721 */         IORTutorial.this.scrollPane.setViewportView(p);
/*  722 */         IORTutorial.this.scrollPane.repaint();
/*  723 */         p.requestFocus();
/*  724 */         p.revalidate();
/*      */       }
/*  726 */     });
/*  727 */     this.menuBar.add(this.help);
/*      */     
/*  729 */     destroyDemoPanel();
/*      */     
/*      */ 
/*  732 */     readDemoItem("demo/GeneralLinear/demoName.txt", "GeneralLinear");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void main(String[] args)
/*      */   {
/*  742 */     new IORTutorial();
/*      */   }
/*      */   
/*      */ 
/*      */   private void jbInit()
/*      */     throws Exception
/*      */   {}
/*      */   
/*      */   public void Helpmenu()
/*      */   {
/*  752 */     this.helpPanel = new JPanel(new BorderLayout());
/*  753 */     this.scrollPane.setViewportView(this.helpPanel);
/*      */     
/*  755 */     String str = "IORTutorial has several distinct features. ";
/*  756 */     str = String.valueOf(String.valueOf(str)).concat("You will find demonstration examples with evolving algebraic and geometric displays ");
/*  757 */     str = String.valueOf(String.valueOf(str)).concat("to clarify what is really going on with the various algorithms and techniques.\n\n");
/*  758 */     str = String.valueOf(String.valueOf(str)).concat("For your homework, you will have the opportunity to execute the algorithms interactively - you make ");
/*  759 */     str = String.valueOf(String.valueOf(str)).concat("the decisions and the computer does the calculating. On the first iteration of most algorithms");
/*  760 */     str = String.valueOf(String.valueOf(str)).concat(", you will be told if you have made a mistake. When you finish the problem, ");
/*  761 */     str = String.valueOf(String.valueOf(str)).concat("you will be able to print out your work on all the iterations to turn in for homework. ");
/*  762 */     str = String.valueOf(String.valueOf(str)).concat("This tutorial software uses visual interaction to teach the how of algorithms.\n\n");
/*  763 */     str = String.valueOf(String.valueOf(str)).concat("Therefore, you should turn to other software for simply solving any larger problems.");
/*  764 */     str = String.valueOf(String.valueOf(str)).concat("\n\n\nPress the NEXT button to continue the introduction.");
/*  765 */     MultilineLabel multlineLabel = new MultilineLabel(str);
/*      */     
/*  767 */     multlineLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
/*      */     
/*  769 */     multlineLabel.setForeground(Color.black);
/*  770 */     multlineLabel.setBackground(Color.white);
/*  771 */     multlineLabel.setFont(this.defaultFont);
/*  772 */     JPanel multpanel = new MultilinePanel(multlineLabel, this.scrollPane);
/*  773 */     multpanel.setBackground(Color.white);
/*      */     
/*  775 */     JPanel nextpanel = new JPanel(new BorderLayout());
/*  776 */     JButton nextbutton = new JButton("    Next   ");
/*  777 */     JButton previousbutton = new JButton("Previous");
/*  778 */     previousbutton.setEnabled(false);
/*      */     
/*  780 */     nextbutton.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  782 */         IORTutorial.this.i += 1;
/*  783 */         IORTutorial.this.doall(IORTutorial.this.i);
/*      */       }
/*  785 */     });
/*  786 */     nextpanel.setBackground(Color.white);
/*  787 */     nextpanel.add(nextbutton, "East");
/*  788 */     nextpanel.add(previousbutton, "West");
/*      */     
/*  790 */     this.helpPanel.setBackground(Color.white);
/*  791 */     this.helpPanel.add(multpanel, "Center");
/*  792 */     this.helpPanel.add(nextpanel, "South");
/*  793 */     multpanel.revalidate();
/*  794 */     nextpanel.revalidate();
/*  795 */     this.helpPanel.revalidate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void firstpage()
/*      */   {
/*  805 */     if (this.panel != null) {
/*  806 */       this.scrollPane.setViewportView(this.panel);
/*  807 */       this.panel.revalidate();
/*  808 */       this.panel.repaint();
/*  809 */       return;
/*      */     }
/*      */     
/*  812 */     JPanel panel = new JPanel(new BorderLayout());
/*  813 */     this.scrollPane.setViewportView(panel);
/*      */     
/*  815 */     JPanel welpanel = new JPanel();
/*      */     
/*  817 */     JLabel label = new JLabel(new ImageIcon("image/welcome.gif"));
/*      */     
/*  819 */     label.setForeground(Color.blue);
/*  820 */     welpanel.setBackground(Color.white);
/*  821 */     welpanel.add(label, "South");
/*  822 */     panel.add(welpanel, "North");
/*      */     
/*  824 */     JPanel multpanel = new JPanel(new BorderLayout());
/*      */     
/*  826 */     String str = "This software is a fully integrated companion to your Hillier-Lieberman ";
/*  827 */     str = String.valueOf(String.valueOf(str)).concat("textbook. This particular program is called MathProg, and considers Chapter 3 through ");
/*  828 */     str = String.valueOf(String.valueOf(str)).concat("14. It has been designed to be your personal tutor, illustrating and illuminating key ");
/*  829 */     str = String.valueOf(String.valueOf(str)).concat("concepts in an interactive manner that cannot be duplicated on a printed page. It also ");
/*  830 */     str = String.valueOf(String.valueOf(str)).concat("should save you considerable time on your homework by enabling you to focus on concepts ");
/*  831 */     str = String.valueOf(String.valueOf(str)).concat("while the computer does the number crunching. Enjoy this modern approach to learning ");
/*  832 */     str = String.valueOf(String.valueOf(str)).concat("Operations Research!\n\nPress Menu HELP button to go through a general introduction to this program.");
/*  833 */     str = String.valueOf(String.valueOf(str)).concat("\n\nIf you wish to begin work now instead, choose an Area from the Area menu.");
/*      */     
/*  835 */     JLabel textLabel = new JLabel(new ImageIcon("image/text.gif"));
/*  836 */     multpanel.setBackground(Color.white);
/*  837 */     multpanel.add(textLabel, "Center");
/*      */     
/*  839 */     str = "* Copyright 2004 by McGraw-Hill, Inc. All rights reserved.";
/*  840 */     JLabel clabel = new JLabel(str);
/*  841 */     clabel.setFont(new Font("Monospaced", 2, 16));
/*  842 */     clabel.setForeground(Color.red);
/*  843 */     JPanel cpanel = new JPanel();
/*  844 */     cpanel.add(clabel);
/*  845 */     cpanel.setBackground(Color.white);
/*  846 */     multpanel.add(cpanel, "South");
/*  847 */     panel.add(multpanel, "Center");
/*  848 */     panel.setBackground(Color.white);
/*      */     
/*  850 */     JPanel bupanel = new JPanel();
/*  851 */     bupanel.setBackground(Color.white);
/*  852 */     JButton button = new JButton("Menu HELP!");
/*  853 */     bupanel.add(button, "South");
/*      */     
/*  855 */     welpanel.add(label, "South");
/*  856 */     panel.add(welpanel, "North");
/*      */     
/*  858 */     panel.add(bupanel, "South");
/*  859 */     button.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  861 */         IORTutorial.this.i = 1;
/*  862 */         IORTutorial.this.doall(IORTutorial.this.i);
/*      */       }
/*  864 */     });
/*  865 */     button.setEnabled(true);
/*      */     
/*  867 */     this.help.getItem(0).setEnabled(true);
/*  868 */     this.help.getItem(1).setEnabled(false);
/*  869 */     this.menuBar.repaint();
/*      */     
/*  871 */     panel.revalidate();
/*      */   }
/*      */   
/*      */   private void nextone() {
/*  875 */     this.helpPanel = new JPanel(new BorderLayout(2, 10));
/*  876 */     this.scrollPane.setViewportView(this.helpPanel);
/*  877 */     this.scrollPane.repaint();
/*  878 */     MultilineLabel multlineLabel = new MultilineLabel("At the top of the screen, up to five different menu options are available to you. (To activate a menu option, select the appropriate function by mouse or press Alt + F for the File menu). Once you have done this, you will be given a list of menu items. For example, if you choose the File menu, then the following will appear under the File menu title:\n\t1 New\n\t2 Open\n\t3 Save\n\t4 Print\n\t5 Quit\n\nTo perform one of these commands, select the appropriate menu item or press the hot key underlined for each item. If you decide not to perform any of these commands, press ESC key. For some menu options, one choice will have a check mark by it. This choice is either the default response or the most recently selected choice. If this is the one you currently want, you need do nothing and this choice will continue to be the active one. There may be times when a command or menu are unavailable, in which case that command or menu title will appear dimmed.\n\n\nPress the NEXT button to continue the introduction.");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  890 */     multlineLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*      */     
/*  892 */     multlineLabel.setForeground(Color.black);
/*  893 */     multlineLabel.setBackground(Color.white);
/*  894 */     multlineLabel.setFont(this.defaultFont);
/*  895 */     JPanel multpanel = new MultilinePanel(multlineLabel, this.scrollPane);
/*  896 */     multpanel.setBackground(Color.white);
/*      */     
/*  898 */     JPanel nextpanel = new JPanel(new BorderLayout());
/*  899 */     JButton nextbutton = new JButton("    Next   ");
/*  900 */     nextbutton.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  902 */         IORTutorial.this.i += 1;
/*  903 */         IORTutorial.this.doall(IORTutorial.this.i);
/*      */       }
/*      */       
/*  906 */     });
/*  907 */     JButton previousbutton = new JButton("Previous");
/*  908 */     previousbutton.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  910 */         IORTutorial.this.i -= 1;
/*  911 */         IORTutorial.this.doall(IORTutorial.this.i);
/*      */       }
/*      */       
/*      */ 
/*  915 */     });
/*  916 */     nextpanel.setBackground(Color.white);
/*  917 */     nextpanel.add(nextbutton, "East");
/*  918 */     nextpanel.add(previousbutton, "West");
/*  919 */     this.helpPanel.setBackground(Color.white);
/*  920 */     this.helpPanel.add(multpanel, "Center");
/*  921 */     this.helpPanel.add(nextpanel, "South");
/*  922 */     multpanel.revalidate();
/*  923 */     nextpanel.revalidate();
/*  924 */     this.helpPanel.revalidate();
/*      */   }
/*      */   
/*      */   private void nexttwo() {
/*  928 */     this.helpPanel = new JPanel(new BorderLayout(2, 10));
/*  929 */     this.scrollPane.setViewportView(this.helpPanel);
/*  930 */     this.scrollPane.repaint();
/*      */     
/*  932 */     MultilineLabel multlineLabel = new MultilineLabel("A description of each of the menus follows:\n\nFile: The File menu provides you with a choice of basic filing operations. These include New (to clear the program's memory and start fresh), Open (to load a previously saved file), Save (to save your work), Print (to print out your work), and Quit.\n\nArea: The Area menu provides you with a choice of ten areas of Operations Research, General Linear Programming, The Transportation Problem, Network Analysis, Integer Programming, Nonlinear Programming, Markov Chains, Queueing Theory, Inventory Theory, Markov Decision and Simulation.\n\nProcedure: Having chosen your general area, the Procedure menu provides you with a choice of procedures, such as enter a model, solve the model with a particular algorithm, etc. Once a procedure is chosen, you may choose \"Introduction to Current Procedure\" from the Help menu at any time.  You should do this immediately the first time you use a procedure.\n\n\nPress the NEXT button to continue the introduction.");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  947 */     multlineLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*      */     
/*  949 */     multlineLabel.setForeground(Color.black);
/*  950 */     multlineLabel.setBackground(Color.white);
/*  951 */     multlineLabel.setFont(this.defaultFont);
/*  952 */     JPanel multpanel = new MultilinePanel(multlineLabel, this.scrollPane);
/*  953 */     multpanel.setBackground(Color.white);
/*      */     
/*  955 */     JPanel nextpanel = new JPanel(new BorderLayout());
/*  956 */     JButton nextbutton = new JButton("    Next   ");
/*  957 */     nextbutton.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  959 */         IORTutorial.this.i += 1;
/*  960 */         IORTutorial.this.doall(IORTutorial.this.i);
/*      */       }
/*      */       
/*  963 */     });
/*  964 */     JButton previousbutton = new JButton("Previous");
/*  965 */     previousbutton.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  967 */         IORTutorial.this.i -= 1;
/*  968 */         IORTutorial.this.doall(IORTutorial.this.i);
/*      */       }
/*      */       
/*  971 */     });
/*  972 */     nextpanel.setBackground(Color.white);
/*  973 */     nextpanel.add(nextbutton, "East");
/*  974 */     nextpanel.add(previousbutton, "West");
/*  975 */     this.helpPanel.setBackground(Color.white);
/*  976 */     this.helpPanel.add(multpanel, "Center");
/*  977 */     this.helpPanel.add(nextpanel, "South");
/*  978 */     multpanel.revalidate();
/*  979 */     nextpanel.revalidate();
/*  980 */     this.helpPanel.revalidate();
/*      */   }
/*      */   
/*      */   private void nextthree() {
/*  984 */     this.helpPanel = new JPanel(new BorderLayout(2, 10));
/*  985 */     this.scrollPane.setViewportView(this.helpPanel);
/*  986 */     this.scrollPane.repaint();
/*      */     
/*  988 */     MultilineLabel multlineLabel = new MultilineLabel("A description of the menus continues below:\n\nOption: Having chosen your general area and procedure, this menu provides you with a choice of specific options for how to display or carry out that procedure.\n\nHelp: If at ANY time, you don't know how to execute the next computer step, choose \"Specific Help on Current Step\" from the Help menu. This menu choice displays specific instructions on the screen. Since only brief instructions (if any) are displayed on procedure screens, you probably will need to make frequent use of this menu option the first time you go through a procedure. To receive general information about the current procedure (including problem size limitations), choose \"Introduction to Current Procedure\".\n\n\nPress the NEXT button to continue the introduction.");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1001 */     multlineLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*      */     
/* 1003 */     multlineLabel.setForeground(Color.black);
/* 1004 */     multlineLabel.setBackground(Color.white);
/* 1005 */     multlineLabel.setFont(this.defaultFont);
/* 1006 */     JPanel multpanel = new MultilinePanel(multlineLabel, this.scrollPane);
/* 1007 */     multpanel.setBackground(Color.white);
/*      */     
/* 1009 */     JPanel nextpanel = new JPanel(new BorderLayout());
/* 1010 */     JButton nextbutton = new JButton("    Next   ");
/* 1011 */     nextbutton.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1013 */         IORTutorial.this.i += 1;
/* 1014 */         IORTutorial.this.doall(IORTutorial.this.i);
/*      */       }
/*      */       
/* 1017 */     });
/* 1018 */     JButton previousbutton = new JButton("Previous");
/* 1019 */     previousbutton.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1021 */         IORTutorial.this.i -= 1;
/* 1022 */         IORTutorial.this.doall(IORTutorial.this.i);
/*      */       }
/*      */       
/* 1025 */     });
/* 1026 */     nextpanel.setBackground(Color.white);
/* 1027 */     nextpanel.add(nextbutton, "East");
/* 1028 */     nextpanel.add(previousbutton, "West");
/* 1029 */     this.helpPanel.setBackground(Color.white);
/* 1030 */     this.helpPanel.add(multpanel, "Center");
/* 1031 */     this.helpPanel.add(nextpanel, "South");
/* 1032 */     multpanel.revalidate();
/* 1033 */     nextpanel.revalidate();
/* 1034 */     this.helpPanel.revalidate();
/*      */   }
/*      */   
/*      */   private void nextfour() {
/* 1038 */     this.helpPanel = new JPanel(new BorderLayout(2, 10));
/* 1039 */     this.scrollPane.setViewportView(this.helpPanel);
/* 1040 */     this.scrollPane.repaint();
/*      */     
/* 1042 */     MultilineLabel multlineLabel = new MultilineLabel("When you execute a procedure from the Procedure menu, if any input is required, there will be an editable text field displayed on the screen. The currently selected text field can be selected by mouse, or moved around the screen by the TAB key. The use of this text field is to signify that input is required. For example, if a number is being asked for (such as the number of variables in a linear programming model), then a number will appear inside the text field. This number is the default response. If you want to change this value, just type the number you want. Nothing more needs to be done to accept the last value appearing there. Pressing the TAB key will automatically move the selected text field to the next spot where input is required.\n\n\nPress the NEXT button to continue the introduction.");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1054 */     multlineLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*      */     
/* 1056 */     multlineLabel.setForeground(Color.black);
/* 1057 */     multlineLabel.setBackground(Color.white);
/* 1058 */     multlineLabel.setFont(this.defaultFont);
/* 1059 */     JPanel multpanel = new MultilinePanel(multlineLabel, this.scrollPane);
/* 1060 */     multpanel.setBackground(Color.white);
/*      */     
/* 1062 */     JPanel nextpanel = new JPanel(new BorderLayout());
/* 1063 */     JButton nextbutton = new JButton("    Next   ");
/* 1064 */     nextbutton.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1066 */         IORTutorial.this.i += 1;
/* 1067 */         IORTutorial.this.doall(IORTutorial.this.i);
/*      */       }
/*      */       
/* 1070 */     });
/* 1071 */     JButton previousbutton = new JButton("Previous");
/* 1072 */     previousbutton.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1074 */         IORTutorial.this.i -= 1;
/* 1075 */         IORTutorial.this.doall(IORTutorial.this.i);
/*      */       }
/*      */       
/*      */ 
/* 1079 */     });
/* 1080 */     nextpanel.setBackground(Color.white);
/* 1081 */     nextpanel.add(nextbutton, "East");
/* 1082 */     nextpanel.add(previousbutton, "West");
/* 1083 */     this.helpPanel.setBackground(Color.white);
/* 1084 */     this.helpPanel.add(multpanel, "Center");
/* 1085 */     this.helpPanel.add(nextpanel, "South");
/* 1086 */     multpanel.revalidate();
/* 1087 */     nextpanel.revalidate();
/* 1088 */     this.helpPanel.revalidate();
/*      */   }
/*      */   
/*      */   private void nextfive() {
/* 1092 */     this.helpPanel = new JPanel(new BorderLayout(2, 10));
/* 1093 */     this.scrollPane.setViewportView(this.helpPanel);
/* 1094 */     this.scrollPane.repaint();
/*      */     
/* 1096 */     MultilineLabel multlineLabel = new MultilineLabel("If you find that you made a mistake at a previous step, you can backtrack to make a correction as far back as you want simply by pressing the BACK button when it is enabled (repeatedly if necessary).\n\nThis ends the general introduction. You may reread it any time by choosing New under the File menu (be sure to save your work first, as this clears the computer's memory of anything you have done).\n\nNow choose an area from the Area menu in which to begin work.");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1105 */     multlineLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*      */     
/* 1107 */     multlineLabel.setForeground(Color.black);
/* 1108 */     multlineLabel.setBackground(Color.white);
/* 1109 */     multlineLabel.setFont(this.defaultFont);
/* 1110 */     JPanel multpanel = new MultilinePanel(multlineLabel, this.scrollPane);
/* 1111 */     multpanel.setBackground(Color.white);
/*      */     
/* 1113 */     JPanel nextpanel = new JPanel(new BorderLayout());
/* 1114 */     JButton nextbutton = new JButton("    Next   ");
/* 1115 */     nextbutton.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1117 */         IORTutorial.this.i += 1;
/* 1118 */         IORTutorial.this.doall(IORTutorial.this.i);
/*      */       }
/*      */       
/* 1121 */     });
/* 1122 */     JButton previousbutton = new JButton("Previous");
/* 1123 */     previousbutton.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1125 */         IORTutorial.this.i -= 1;
/* 1126 */         IORTutorial.this.doall(IORTutorial.this.i);
/*      */       }
/*      */       
/* 1129 */     });
/* 1130 */     nextpanel.setBackground(Color.white);
/* 1131 */     nextpanel.add(nextbutton, "East");
/* 1132 */     nextpanel.add(previousbutton, "West");
/* 1133 */     this.helpPanel.setBackground(Color.white);
/* 1134 */     this.helpPanel.add(multpanel, "Center");
/* 1135 */     this.helpPanel.add(nextpanel, "South");
/* 1136 */     multpanel.revalidate();
/* 1137 */     nextpanel.revalidate();
/* 1138 */     this.helpPanel.revalidate();
/*      */   }
/*      */   
/*      */   private void nextsix() {
/* 1142 */     this.helpPanel = new JPanel(new BorderLayout(2, 10));
/* 1143 */     this.scrollPane.setViewportView(this.helpPanel);
/* 1144 */     this.scrollPane.repaint();
/*      */     
/* 1146 */     String str = "The current area is \"General Linear Programming.\" You can select ";
/* 1147 */     str = String.valueOf(String.valueOf(str)).concat("a procedure from the Procedure menu to start. Before executing these ");
/* 1148 */     str = String.valueOf(String.valueOf(str)).concat("procedures for the first time, we recommend that you go through the ");
/* 1149 */     str = String.valueOf(String.valueOf(str)).concat("demonstration example entitled General Linear Programming in OR Tutor.");
/* 1150 */     str = String.valueOf(String.valueOf(str)).concat("\n\n");
/* 1151 */     str = String.valueOf(String.valueOf(str)).concat("If you would like to choose an area other than \"General Linear Programming\" within which ");
/* 1152 */     str = String.valueOf(String.valueOf(str)).concat("to work, select the Area menu.\n");
/* 1153 */     MultilineLabel multlineLabel = new MultilineLabel(str);
/*      */     
/* 1155 */     multlineLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 50, 20));
/*      */     
/* 1157 */     multlineLabel.setForeground(Color.black);
/* 1158 */     multlineLabel.setBackground(Color.white);
/* 1159 */     multlineLabel.setFont(this.defaultFont);
/* 1160 */     JPanel multpanel = new MultilinePanel(multlineLabel, this.scrollPane);
/* 1161 */     multpanel.setBackground(Color.white);
/*      */     
/* 1163 */     JPanel nextpanel = new JPanel(new BorderLayout());
/* 1164 */     JButton nextbutton = new JButton("    Next   ");
/* 1165 */     nextbutton.setEnabled(false);
/* 1166 */     nextbutton.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1168 */         IORTutorial.this.i += 1;
/* 1169 */         IORTutorial.this.doall(IORTutorial.this.i);
/*      */       }
/*      */       
/* 1172 */     });
/* 1173 */     JButton previousbutton = new JButton("Previous");
/* 1174 */     previousbutton.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/* 1176 */         IORTutorial.this.i -= 1;
/* 1177 */         IORTutorial.this.doall(IORTutorial.this.i);
/*      */       }
/* 1179 */     });
/* 1180 */     nextpanel.setBackground(Color.white);
/* 1181 */     nextpanel.add(nextbutton, "East");
/* 1182 */     nextpanel.add(previousbutton, "West");
/* 1183 */     this.helpPanel.setBackground(Color.white);
/* 1184 */     this.helpPanel.add(multpanel, "Center");
/* 1185 */     this.helpPanel.add(nextpanel, "South");
/* 1186 */     multpanel.revalidate();
/* 1187 */     nextpanel.revalidate();
/* 1188 */     this.helpPanel.revalidate();
/*      */   }
/*      */   
/*      */   private void doall(int i)
/*      */   {
/* 1193 */     switch (i) {
/*      */     case 1: 
/* 1195 */       Helpmenu();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1218 */       break;
/*      */     case 2: 
/* 1198 */       nextone();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1218 */       break;
/*      */     case 3: 
/* 1201 */       nexttwo();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1218 */       break;
/*      */     case 4: 
/* 1204 */       nextthree();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1218 */       break;
/*      */     case 5: 
/* 1207 */       nextfour();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1218 */       break;
/*      */     case 6: 
/* 1210 */       nextfive();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1218 */       break;
/*      */     case 7: 
/* 1213 */       nextsix();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1218 */       break; }
/*      */   }
/*      */   
/*      */   private class TutorialHelpPanel extends MultilinePanel {
/* 1222 */     public TutorialHelpPanel(MultilineLabel m, JScrollPane sp) { super(sp);
/* 1223 */       setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
/* 1224 */       addFocusListener(new IORTutorial.36((TutorialHelpPanel)this));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1230 */       addKeyListener(new IORTutorial.37((TutorialHelpPanel)this));
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
/*      */ 
/*      */ 
/*      */ 
/*      */   private void initDemoPanel(String demoName)
/*      */   {
/* 1247 */     if (this.demoPanel != null) {
/* 1248 */       this.demoPanel.testDialog.dispose();
/* 1249 */       this.demoPanel = null;
/*      */     }
/* 1251 */     this.demoPanel = new IORTestDemoPanel(demoName, this);
/* 1252 */     this.demoPanel.setPanelSize(this.scrollPane.getSize().width, this.scrollPane.getSize().height);
/* 1253 */     this.scrollPane.setViewportView(this.demoPanel);
/*      */   }
/*      */   
/*      */   public void destroyDemoPanel() {
/* 1257 */     if (this.play != null) {
/* 1258 */       this.play.removeAll();
/*      */     }
/* 1260 */     if (this.demoPanel != null) {
/* 1261 */       this.demoPanel.testDialog.dispose();
/*      */       
/* 1263 */       this.demoPanel = null;
/*      */     }
/* 1265 */     this.procedure.setSelected(false);
/* 1266 */     this.option.setSelected(false);
/* 1267 */     System.gc();
/*      */   }
/*      */   
/*      */   public void closeDemoController() {
/* 1271 */     if (this.demoPanel != null) {
/* 1272 */       this.demoPanel.testDialog.dispose();
/*      */     }
/*      */   }
/*      */   
/*      */   private void readDemoItem(String txt, String dirStr)
/*      */   {
/* 1278 */     this.play.removeAll();
/* 1279 */     this.demoDirectory = dirStr;
/*      */     
/*      */     try
/*      */     {
/* 1283 */       URL url = getClass().getResource(txt);
/* 1284 */       InputStream butS = url.openStream();
/* 1285 */       InputStreamReader butSR = new InputStreamReader(butS);
/* 1286 */       BufferedReader butB = new BufferedReader(butSR);
/* 1287 */       String name = butB.readLine();
/* 1288 */       boolean isWrite = false;
/*      */       
/* 1290 */       while (name != null) {
/* 1291 */         char linetype = name.charAt(0);
/* 1292 */         switch (linetype) {
/*      */         case '1': 
/* 1294 */           this.DemoItemName1 = name.substring(2);
/* 1295 */           System.out.println("demo Name==".concat(String.valueOf(String.valueOf(this.DemoItemName1))));
/* 1296 */           JRadioButtonMenuItem ItemDemo = new JRadioButtonMenuItem(this.DemoItemName1);
/* 1297 */           this.play.add(ItemDemo);
/* 1298 */           this.playG.add(ItemDemo);
/* 1299 */           ItemDemo.addActionListener(new ActionListener() {
/*      */             public void actionPerformed(ActionEvent e) {
/* 1301 */               IORTutorial.this.initDemoPanel(IORTutorial.this.DemoItemName1);
/* 1302 */               IORTutorial.this.demoPanel.loadDemoImg(String.valueOf(String.valueOf(new StringBuffer("demo/").append(IORTutorial.this.demoDirectory).append("/demo1.txt"))));
/* 1303 */               System.out.println("1");
/*      */             }
/* 1305 */           });
/* 1306 */           break;
/*      */         case '2': 
/* 1308 */           this.DemoItemName2 = name.substring(2);
/* 1309 */           JRadioButtonMenuItem ItemDemo = new JRadioButtonMenuItem(this.DemoItemName2);
/* 1310 */           this.play.add(ItemDemo);
/* 1311 */           this.playG.add(ItemDemo);
/* 1312 */           ItemDemo.addActionListener(new ActionListener() {
/*      */             public void actionPerformed(ActionEvent e) {
/* 1314 */               IORTutorial.this.initDemoPanel(IORTutorial.this.DemoItemName2);
/* 1315 */               IORTutorial.this.demoPanel.loadDemoImg(String.valueOf(String.valueOf(new StringBuffer("demo/").append(IORTutorial.this.demoDirectory).append("/demo2.txt"))));
/* 1316 */               System.out.println("2");
/*      */             }
/* 1318 */           });
/* 1319 */           break;
/*      */         case '3': 
/* 1321 */           this.DemoItemName3 = name.substring(2);
/* 1322 */           JRadioButtonMenuItem ItemDemo = new JRadioButtonMenuItem(this.DemoItemName3);
/* 1323 */           this.play.add(ItemDemo);
/* 1324 */           this.playG.add(ItemDemo);
/* 1325 */           ItemDemo.addActionListener(new ActionListener() {
/*      */             public void actionPerformed(ActionEvent e) {
/* 1327 */               IORTutorial.this.initDemoPanel(IORTutorial.this.DemoItemName3);
/* 1328 */               IORTutorial.this.demoPanel.loadDemoImg(String.valueOf(String.valueOf(new StringBuffer("demo/").append(IORTutorial.this.demoDirectory).append("/demo3.txt"))));
/*      */               
/* 1330 */               System.out.println("3");
/*      */             }
/* 1332 */           });
/* 1333 */           break;
/*      */         case '4': 
/* 1335 */           this.DemoItemName4 = name.substring(2);
/* 1336 */           JRadioButtonMenuItem ItemDemo = new JRadioButtonMenuItem(this.DemoItemName4);
/* 1337 */           this.play.add(ItemDemo);
/* 1338 */           this.playG.add(ItemDemo);
/* 1339 */           ItemDemo.addActionListener(new ActionListener() {
/*      */             public void actionPerformed(ActionEvent e) {
/* 1341 */               IORTutorial.this.initDemoPanel(IORTutorial.this.DemoItemName4);
/* 1342 */               IORTutorial.this.demoPanel.loadDemoImg(String.valueOf(String.valueOf(new StringBuffer("demo/").append(IORTutorial.this.demoDirectory).append("/demo4.txt"))));
/*      */               
/* 1344 */               System.out.println("4");
/*      */             }
/* 1346 */           });
/* 1347 */           break;
/*      */         case '5': 
/* 1349 */           this.DemoItemName5 = name.substring(2);
/* 1350 */           JRadioButtonMenuItem ItemDemo = new JRadioButtonMenuItem(this.DemoItemName5);
/* 1351 */           this.play.add(ItemDemo);
/* 1352 */           this.playG.add(ItemDemo);
/* 1353 */           ItemDemo.addActionListener(new ActionListener() {
/*      */             public void actionPerformed(ActionEvent e) {
/* 1355 */               IORTutorial.this.initDemoPanel(IORTutorial.this.DemoItemName5);
/* 1356 */               IORTutorial.this.demoPanel.loadDemoImg(String.valueOf(String.valueOf(new StringBuffer("demo/").append(IORTutorial.this.demoDirectory).append("/demo5.txt"))));
/*      */               
/* 1358 */               System.out.println("5");
/*      */             }
/* 1360 */           });
/* 1361 */           break;
/*      */         
/*      */         case '6': 
/* 1364 */           this.DemoItemName6 = name.substring(2);
/* 1365 */           JRadioButtonMenuItem ItemDemo = new JRadioButtonMenuItem(this.DemoItemName6);
/* 1366 */           this.play.add(ItemDemo);
/* 1367 */           this.playG.add(ItemDemo);
/* 1368 */           ItemDemo.addActionListener(new ActionListener() {
/*      */             public void actionPerformed(ActionEvent e) {
/* 1370 */               IORTutorial.this.initDemoPanel(IORTutorial.this.DemoItemName6);
/* 1371 */               IORTutorial.this.demoPanel.loadDemoImg(String.valueOf(String.valueOf(new StringBuffer("demo/").append(IORTutorial.this.demoDirectory).append("/demo6.txt"))));
/*      */               
/* 1373 */               System.out.println("6");
/*      */             }
/*      */           });
/*      */         }
/*      */         
/*      */         
/* 1379 */         name = butB.readLine();
/*      */       }
/*      */     }
/*      */     catch (Exception localException) {}
/*      */   }
/*      */   
/*      */ 
/*      */   public void readImgToCache()
/*      */   {
/* 1388 */     URL textUrl = null;
/* 1389 */     InputStream butS = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1397 */       URLClassLoader urlLoader = (URLClassLoader)getClass().getClassLoader();
/*      */       
/* 1399 */       textUrl = urlLoader.findResource("demo/imagecache.txt");
/* 1400 */       butS = textUrl.openStream();
/* 1401 */       InputStreamReader isr = new InputStreamReader(butS);
/* 1402 */       BufferedReader reader = new BufferedReader(isr);
/* 1403 */       String line = reader.readLine();
/* 1404 */       if (line.substring(0, 8).equals("length: ")) {
/* 1405 */         this.cache = new Hashtable(Integer.parseInt(line.substring(9)));
/*      */       }
/*      */       else {
/* 1408 */         return;
/*      */       }
/* 1410 */       line = reader.readLine();
/* 1411 */       while (line != null) {
/*      */         try {
/* 1413 */           this.cache.put(line, createImageIcon(line, this));
/*      */         }
/*      */         catch (Exception e) {
/* 1416 */           e.printStackTrace();
/*      */         }
/* 1418 */         line = reader.readLine();
/*      */       }
/*      */     }
/*      */     catch (Exception e1) {
/* 1422 */       System.out.println("e1=".concat(String.valueOf(String.valueOf(textUrl))));
/*      */     }
/*      */   }
/*      */   
/*      */   public ImageIcon createImageIcon(String name, Component cmp)
/*      */   {
/* 1428 */     this.img = null;
/* 1429 */     String path = "".concat(String.valueOf(String.valueOf(name)));
/* 1430 */     System.out.println("name == ".concat(String.valueOf(String.valueOf(name))));
/* 1431 */     return new ImageIcon(getClass().getResource(path));
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTutorial.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */