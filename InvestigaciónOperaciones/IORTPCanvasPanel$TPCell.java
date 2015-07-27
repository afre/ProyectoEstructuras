/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Point;
/*      */ import java.awt.Stroke;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Vector;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class IORTPCanvasPanel$TPCell
/*      */   extends JButton
/*      */ {
/*      */   private int id;
/*      */   private int row;
/*      */   private int column;
/*      */   private TPCell me;
/*      */   private Vector loop_state;
/*      */   private int oval_x;
/*      */   private int oval_y;
/*      */   private int oval_h;
/*      */   private int oval_w;
/*      */   private boolean is_on_loop;
/*      */   private boolean is_plus;
/*      */   private final Font signFont;
/*      */   private final IORTPCanvasPanel this$0;
/*      */   
/*      */   IORTPCanvasPanel$TPCell(IORTPCanvasPanel x$0, int x$1, int x$2, IORTPCanvasPanel..4 x$3)
/*      */   {
/*  862 */     this(x$0, x$1, x$2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private IORTPCanvasPanel$TPCell(IORTPCanvasPanel this$0, int r, int c)
/*      */   {
/*  883 */     this.this$0 = this$0;this.id = -1;this.row = -1;this.column = -1;this.me = null;this.loop_state = null;this.oval_x = -1;this.oval_y = -1;this.oval_h = -1;this.oval_w = -1;this.is_on_loop = false;this.is_plus = false;this.signFont = new Font("SansSerif", 1, 15);
/*      */     
/*  885 */     setBorder(new EmptyBorder(0, 0, 0, 0));
/*  886 */     this.row = r;
/*  887 */     this.column = c;
/*      */     
/*  889 */     this.loop_state = new Vector();
/*      */     
/*  891 */     setBackground(IORTPCanvasPanel.access$10(this$0));
/*      */     
/*  893 */     this.me = this;
/*  894 */     this.id = IORTPCanvasPanel.access$26();
/*      */     
/*  896 */     addActionListener(new IORTPCanvasPanel.1((TPCell)this));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  908 */     addMouseListener(new IORTPCanvasPanel.2((TPCell)this));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  928 */     addKeyListener(new IORTPCanvasPanel.3((TPCell)this));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void doDoubleClick()
/*      */   {
/*  956 */     switch (IORTPCanvasPanel.access$7(this.this$0)) {
/*      */     case 0: 
/*  958 */       setBasicVariable();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  981 */       break;
/*      */     case 1: 
/*  962 */       switch (IORTPCanvasPanel.access$14(this.this$0).getStep()) {
/*      */       case 1: 
/*  964 */         setEnteringBasicVariable();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  981 */         break;
/*      */       case 2: 
/*  968 */         addNode();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  981 */         break;
/*      */       case 3: 
/*  972 */         setLeavingBasicVariable();
/*      */       }
/*      */       
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  981 */       break; }
/*      */   }
/*      */   
/*      */   private void setBasicVariable() {
/*  985 */     if (IORTPCanvasPanel.access$14(this.this$0).getStep() != 1) {
/*  986 */       return;
/*      */     }
/*  988 */     if (isBasicVariable()) {
/*  989 */       return;
/*      */     }
/*  991 */     String input = JOptionPane.showInputDialog(this, "Please input the flow for the selected cell:");
/*      */     try
/*      */     {
/*  994 */       d = Double.parseDouble(input);
/*      */     } catch (Exception e) {
/*      */       double d;
/*      */       return;
/*      */     }
/*      */     double d;
/* 1000 */     Vector p = new Vector();
/*      */     
/* 1002 */     p.addElement(new Integer(this.row));
/*      */     
/* 1004 */     p.addElement(new Integer(this.column));
/*      */     
/* 1006 */     p.addElement(new Double(d));
/*      */     
/* 1008 */     p.addElement(new Integer(IORTPCanvasPanel.access$14(this.this$0).getStep()));
/*      */     
/* 1010 */     IOROperation operation = new IOROperation(20201, p);
/*      */     
/*      */ 
/* 1013 */     if (IORTPCanvasPanel.access$13(this.this$0).solver.doWork(operation, IORTPCanvasPanel.access$13(this.this$0).data)) {
/* 1014 */       setCurrent();
/* 1015 */       IORTPCanvasPanel.access$17(this.this$0).addStep(operation);
/* 1016 */       if (!this.this$0.isLastRowOrColumn()) {
/* 1017 */         IORTPCanvasPanel.access$14(this.this$0).setStep(2);
/*      */       }
/* 1019 */       IORTPCanvasPanel.access$14(this.this$0).addStep();
/* 1020 */       IORTPCanvasPanel.access$14(this.this$0).updatePanel();
/*      */     }
/*      */     else {
/* 1023 */       JOptionPane.showMessageDialog(this, IORTPCanvasPanel.access$13(this.this$0).solver.getErrInfo());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void setLeavingBasicVariable()
/*      */   {
/* 1031 */     if (!IORTPCanvasPanel.access$13(this.this$0).data.loop.contains(new Point(this.row, this.column))) {
/* 1032 */       return;
/*      */     }
/* 1034 */     String input = JOptionPane.showInputDialog(this, "Please input the new value for the entering basic variable:");
/*      */     try
/*      */     {
/* 1037 */       d = Double.parseDouble(input);
/*      */     } catch (Exception e) {
/*      */       double d;
/*      */       return;
/*      */     }
/*      */     double d;
/* 1043 */     Vector p = new Vector();
/*      */     
/* 1045 */     p.addElement(new Integer(this.row));
/*      */     
/* 1047 */     p.addElement(new Integer(this.column));
/*      */     
/* 1049 */     p.addElement(new Double(d));
/*      */     
/* 1051 */     IOROperation operation = new IOROperation(20304, p);
/*      */     
/*      */ 
/* 1054 */     if (IORTPCanvasPanel.access$13(this.this$0).solver.doWork(operation, IORTPCanvasPanel.access$13(this.this$0).data)) {
/* 1055 */       setCurrent();
/* 1056 */       IORTPCanvasPanel.access$17(this.this$0).addStep(operation);
/* 1057 */       IORTPCanvasPanel.access$14(this.this$0).setStep(1);
/* 1058 */       IORTPCanvasPanel.access$14(this.this$0).addStep();
/* 1059 */       IORTPCanvasPanel.access$14(this.this$0).updatePanel();
/* 1060 */       IORTPCanvasPanel.access$14(this.this$0).revalidate();
/* 1061 */       IORTPCanvasPanel.access$14(this.this$0).repaint();
/*      */     }
/*      */     else {
/* 1064 */       JOptionPane.showMessageDialog(this, IORTPCanvasPanel.access$13(this.this$0).solver.getErrInfo());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void addNode()
/*      */   {
/* 1071 */     if (!IORTPCanvasPanel.TPCanvas.access$30(this.this$0.canvas, this)) {
/* 1072 */       return;
/*      */     }
/* 1074 */     Vector p = new Vector();
/*      */     
/* 1076 */     p.addElement(new Integer(this.row));
/*      */     
/* 1078 */     p.addElement(new Integer(this.column));
/*      */     
/* 1080 */     IOROperation operation = new IOROperation(20303, p);
/*      */     
/*      */ 
/* 1083 */     if (IORTPCanvasPanel.access$13(this.this$0).solver.doWork(operation, IORTPCanvasPanel.access$13(this.this$0).data)) {
/* 1084 */       setCurrent();
/* 1085 */       IORTPCanvasPanel.access$17(this.this$0).addStep(operation);
/* 1086 */       IORTPCanvasPanel.TPCanvas.access$31(this.this$0.canvas, this);
/*      */       
/*      */ 
/* 1089 */       Vector loop = IORTPCanvasPanel.access$13(this.this$0).data.loop;
/* 1090 */       if (loop.firstElement().equals(loop.lastElement())) {
/* 1091 */         IORTPCanvasPanel.access$14(this.this$0).setStep(3);
/*      */       }
/* 1093 */       IORTPCanvasPanel.access$14(this.this$0).addStep();
/* 1094 */       IORTPCanvasPanel.access$14(this.this$0).updatePanel();
/* 1095 */       IORTPCanvasPanel.access$14(this.this$0).revalidate();
/* 1096 */       IORTPCanvasPanel.access$14(this.this$0).repaint();
/*      */     }
/*      */     else {
/* 1099 */       System.out.println("doWork for adding nodes failed");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean setEnteringBasicVariable()
/*      */   {
/* 1107 */     if ((isBasicVariable()) || (isEnteringBasicVariable())) {
/* 1108 */       return false;
/*      */     }
/* 1110 */     int option = JOptionPane.showConfirmDialog(this, "Set this cell as an entering basic variable?");
/* 1111 */     if (option != 0) {
/* 1112 */       System.err.println("Entering basic variable is not continued");
/* 1113 */       return false;
/*      */     }
/*      */     
/* 1116 */     Vector p = new Vector();
/*      */     
/* 1118 */     p.addElement(new Integer(this.row));
/*      */     
/* 1120 */     p.addElement(new Integer(this.column));
/*      */     
/* 1122 */     IOROperation operation = new IOROperation(20302, p);
/*      */     
/*      */ 
/* 1125 */     if (IORTPCanvasPanel.access$13(this.this$0).solver.doWork(operation, IORTPCanvasPanel.access$13(this.this$0).data)) {
/* 1126 */       setCurrent();
/* 1127 */       IORTPCanvasPanel.access$17(this.this$0).addStep(operation);
/*      */       
/* 1129 */       if (!IORTPCanvasPanel.access$13(this.this$0).data.loop.isEmpty()) {
/* 1130 */         IORTPCanvasPanel.TPCanvas.access$32(this.this$0.canvas);
/*      */       }
/*      */       
/* 1133 */       IORTPCanvasPanel.access$13(this.this$0).data.loop.addElement(new Point(this.row, this.column));
/*      */       
/* 1135 */       IORTPCanvasPanel.access$14(this.this$0).setStep(2);
/* 1136 */       IORTPCanvasPanel.access$14(this.this$0).addStep();
/* 1137 */       IORTPCanvasPanel.access$14(this.this$0).updatePanel();
/* 1138 */       IORTPCanvasPanel.access$14(this.this$0).revalidate();
/* 1139 */       IORTPCanvasPanel.access$14(this.this$0).repaint();
/* 1140 */       return true;
/*      */     }
/*      */     
/* 1143 */     System.out.println("doWork for setting entering basic variables failed");
/* 1144 */     JOptionPane.showMessageDialog(this, IORTPCanvasPanel.access$13(this.this$0).solver.getErrInfo());
/* 1145 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isBasicVariable()
/*      */   {
/* 1150 */     return IORTPCanvasPanel.access$13(this.this$0).data.is_basic[this.row][this.column];
/*      */   }
/*      */   
/*      */   private boolean isEnteringBasicVariable() {
/* 1154 */     return IORTPCanvasPanel.access$13(this.this$0).data.is_entering_basic[this.row][this.column];
/*      */   }
/*      */   
/*      */   public int getRow() {
/* 1158 */     return this.row;
/*      */   }
/*      */   
/*      */   public int getColumn() {
/* 1162 */     return this.column;
/*      */   }
/*      */   
/*      */   private void setCurrent()
/*      */   {
/* 1167 */     if (IORTPCanvasPanel.access$15(this.this$0) != null) {
/* 1168 */       IORTPCanvasPanel.access$15(this.this$0).repaint();
/*      */     }
/*      */     
/* 1171 */     IORTPCanvasPanel.access$33(this.this$0, this);
/*      */     
/* 1173 */     IORTPCanvasPanel.access$15(this.this$0).setSelected(true);
/* 1174 */     IORTPCanvasPanel.access$15(this.this$0).requestFocus();
/*      */     
/* 1176 */     IORTPCanvasPanel.access$15(this.this$0).repaint();
/*      */   }
/*      */   
/*      */   private void addLoopState(int state) {
/* 1180 */     Integer s = new Integer(state);
/* 1181 */     this.loop_state.addElement(s);
/* 1182 */     revalidate();
/* 1183 */     repaint();
/*      */   }
/*      */   
/*      */   private void setDirection(boolean b) {
/* 1187 */     this.is_plus = b;
/*      */   }
/*      */   
/*      */   private void setOnLoop(boolean b) {
/* 1191 */     this.is_on_loop = b;
/*      */   }
/*      */   
/*      */   private void resetLoopState() {
/* 1195 */     this.loop_state.removeAllElements();
/* 1196 */     this.is_on_loop = false;
/* 1197 */     this.is_plus = false;
/*      */     
/* 1199 */     revalidate();
/* 1200 */     repaint();
/*      */   }
/*      */   
/*      */   private boolean isCurrent() {
/* 1204 */     return IORTPCanvasPanel.access$15(this.this$0) == this;
/*      */   }
/*      */   
/*      */   private Color getSelectedColor() {
/* 1208 */     return IORTPCanvasPanel.access$34(this.this$0);
/*      */   }
/*      */   
/*      */ 
/*      */   public void paintComponent(Graphics g)
/*      */   {
/* 1214 */     super.paintComponent(g);
/*      */     
/* 1216 */     setBackground();
/*      */     
/* 1218 */     Graphics2D g2 = (Graphics2D)g;
/* 1219 */     Dimension d = getSize();
/*      */     
/*      */ 
/* 1222 */     g2.setColor(Color.blue);
/* 1223 */     String str = IORTPActionPanel.getCellString(IORTPCanvasPanel.access$13(this.this$0).data.cost[this.row][this.column], IORTPCanvasPanel.access$13(this.this$0).data.M_cost[this.row][this.column]);
/* 1224 */     g2.drawString(str, 2, d.height / 4);
/* 1225 */     g2.drawRect(0, 0, d.width / 2, d.height * 5 / 16);
/*      */     
/* 1227 */     this.oval_x = (d.width * 2 / 8);
/* 1228 */     this.oval_y = (d.height / 2);
/* 1229 */     this.oval_w = (d.width * 11 / 16);
/* 1230 */     this.oval_h = (d.height * 7 / 16);
/*      */     
/* 1232 */     if ((IORTPCanvasPanel.access$7(this.this$0) == 1) && (
/* 1233 */       (IORTPCanvasPanel.access$14(this.this$0).getStep() == 2) || (IORTPCanvasPanel.access$14(this.this$0).getStep() == 3))) {
/* 1234 */       drawLoop(g2);
/*      */     }
/*      */     
/* 1237 */     drawCell(g2);
/*      */     
/* 1239 */     g2.setColor(Color.black);
/* 1240 */     g2.drawRect(0, 0, d.width, d.height);
/*      */   }
/*      */   
/*      */   private void drawCell(Graphics2D g2)
/*      */   {
/* 1245 */     Dimension d = getSize();
/*      */     
/*      */ 
/* 1248 */     if (isBasicVariable())
/*      */     {
/* 1250 */       g2.setColor(Color.yellow);
/* 1251 */       g2.fillOval(this.oval_x, this.oval_y, this.oval_w, this.oval_h);
/*      */       
/* 1253 */       g2.setColor(Color.black);
/* 1254 */       g2.drawString(IORActionPanel.trim(IORTPCanvasPanel.access$13(this.this$0).data.flow[this.row][this.column]), d.width * 3 / 8, d.height * 6 / 8);
/*      */     }
/*      */     else {
/* 1257 */       switch (IORTPCanvasPanel.access$7(this.this$0))
/*      */       {
/*      */       case 0: 
/* 1260 */         boolean is_deleted = IORTPCanvasPanel.access$13(this.this$0).data.is_deleted[this.row][this.column];
/* 1261 */         if ((IORTPCanvasPanel.access$13(this.this$0).getOption() == 2) && (!is_deleted)) {
/* 1262 */           g2.setColor(Color.black);
/* 1263 */           int n0 = (int)IORTPCanvasPanel.access$13(this.this$0).data.M_cuv[this.row][this.column];
/* 1264 */           int n1 = (int)IORTPCanvasPanel.access$13(this.this$0).data.cuv[this.row][this.column];
/* 1265 */           String str = IORTPActionPanel.getCellString(n0, n1);
/* 1266 */           g2.drawString(str, d.width / 8, d.height * 6 / 8);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1288 */         break;
/*      */       case 1: 
/* 1271 */         if (IORTPCanvasPanel.access$13(this.this$0).data.is_entering_basic[this.row][this.column] != 0)
/*      */         {
/* 1273 */           g2.setColor(Color.yellow);
/* 1274 */           g2.fillRect(d.width * 6 / 16, d.height * 8 / 16, d.width * 6 / 16, d.width * 6 / 16);
/*      */         }
/*      */         
/* 1277 */         g2.setColor(Color.black);
/* 1278 */         int n0 = (int)IORTPCanvasPanel.access$13(this.this$0).data.M_cuv[this.row][this.column];
/* 1279 */         int n1 = (int)IORTPCanvasPanel.access$13(this.this$0).data.cuv[this.row][this.column];
/* 1280 */         String str = IORTPActionPanel.getCellString(n0, n1);
/* 1281 */         g2.drawString(str, d.width * 2 / 8, d.height * 6 / 8);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1288 */         break;
/*      */       default: 
/* 1285 */         System.err.println("Invalid canvas state in draw russel or entering basic");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void setBackground()
/*      */   {
/* 1292 */     switch (IORTPCanvasPanel.access$7(this.this$0)) {
/*      */     case 0: 
/* 1294 */       if (IORTPCanvasPanel.access$13(this.this$0).data.is_deleted[this.row][this.column] == 0) {
/* 1295 */         if (isCurrent()) {
/* 1296 */           setBackground(IORTPCanvasPanel.access$34(this.this$0));
/*      */         } else {
/* 1298 */           setBackground(IORTPCanvasPanel.access$10(this.this$0));
/*      */         }
/*      */       } else {
/* 1301 */         setBackground(Color.gray);
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
/* 1314 */       break;
/*      */     case 1: 
/* 1305 */       if (isCurrent()) {
/* 1306 */         setBackground(IORTPCanvasPanel.access$34(this.this$0));
/*      */       } else {
/* 1308 */         setBackground(IORTPCanvasPanel.access$10(this.this$0));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1314 */       break;
/*      */     default: 
/* 1312 */       System.err.println("Invalid canvas state in setting up the background color");
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */   private void drawLoop(Graphics2D g2)
/*      */   {
/* 1320 */     if (this.is_on_loop) { String sign;
/* 1321 */       String sign; if (this.is_plus) {
/* 1322 */         sign = "+";
/*      */       } else {
/* 1324 */         sign = "-";
/*      */       }
/* 1326 */       int x = this.oval_x + this.oval_w * 3 / 4;
/* 1327 */       int y = this.oval_y * 3 / 4;
/* 1328 */       g2.setColor(Color.red);
/*      */       
/* 1330 */       Font font = g2.getFont();
/* 1331 */       g2.setFont(this.signFont);
/* 1332 */       g2.drawString(sign, x, y);
/* 1333 */       g2.setFont(font);
/*      */     }
/*      */     
/*      */ 
/* 1337 */     Stroke stroke = g2.getStroke();
/*      */     
/* 1339 */     if (!this.loop_state.isEmpty()) {
/* 1340 */       Stroke s = new BasicStroke(4.0F, 0, 1);
/* 1341 */       g2.setStroke(s);
/* 1342 */       g2.setColor(Color.yellow);
/*      */     }
/*      */     
/* 1345 */     int center_x = (2 * this.oval_x + this.oval_w) / 2;
/* 1346 */     int center_y = (2 * this.oval_y + this.oval_h) / 2;
/* 1347 */     Dimension d = getSize();
/*      */     
/* 1349 */     for (int i = 0; i < this.loop_state.size(); i++)
/*      */     {
/* 1351 */       Integer s = (Integer)this.loop_state.elementAt(i);
/* 1352 */       int st = s.intValue();
/*      */       
/* 1354 */       switch (st)
/*      */       {
/*      */       case 0: 
/* 1357 */         g2.drawLine(center_x, 0, center_x, d.height);
/* 1358 */         break;
/*      */       
/*      */       case 1: 
/* 1361 */         g2.drawLine(0, center_y, d.width, center_y);
/* 1362 */         break;
/*      */       
/*      */       case 2: 
/* 1365 */         g2.drawLine(center_x, center_y, 0, center_y);
/* 1366 */         break;
/*      */       
/*      */       case 3: 
/* 1369 */         g2.drawLine(center_x, center_y, d.width, center_y);
/* 1370 */         break;
/*      */       
/*      */       case 5: 
/* 1373 */         g2.drawLine(center_x, center_y, center_x, 0);
/* 1374 */         break;
/*      */       
/*      */       case 4: 
/* 1377 */         g2.drawLine(center_x, center_y, center_x, d.height);
/* 1378 */         break;
/*      */       
/*      */       default: 
/* 1381 */         System.err.println("ERROR IN DRAWING LOOP");
/*      */       }
/*      */       
/*      */     }
/*      */     
/* 1386 */     g2.setStroke(stroke);
/*      */   }
/*      */   
/*      */   public Vector getInnerVariables() {
/* 1390 */     Vector v = new Vector();
/* 1391 */     v.addElement(new Boolean(this.is_on_loop));
/* 1392 */     v.addElement(new Boolean(this.is_plus));
/* 1393 */     v.addElement(this.loop_state);
/* 1394 */     return v;
/*      */   }
/*      */   
/*      */   public void setInnerVariables(Vector v) {
/* 1398 */     this.is_on_loop = ((Boolean)v.elementAt(0)).booleanValue();
/* 1399 */     this.is_plus = ((Boolean)v.elementAt(1)).booleanValue();
/* 1400 */     this.loop_state = ((Vector)v.elementAt(2));
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTPCanvasPanel$TPCell.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */