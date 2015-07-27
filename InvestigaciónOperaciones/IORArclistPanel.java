/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORArclistPanel
/*     */   extends JPanel
/*     */   implements ListSelectionListener
/*     */ {
/*  42 */   private IORNAProcessController controller = null;
/*     */   
/*  44 */   private final int CHOSE_BASIC = 1;
/*  45 */   private final int CHOSE_REVERSE = 2;
/*     */   
/*  47 */   private JPanel mainPanel = new JPanel();
/*  48 */   private JLabel inditext = new JLabel();
/*     */   
/*     */   public JList arcList;
/*     */   
/*     */   public JList basicList;
/*     */   
/*     */   public JList reverseList;
/*     */   
/*     */   public DefaultListModel arcModel;
/*     */   
/*     */   public DefaultListModel basicModel;
/*     */   
/*     */   public DefaultListModel reverseModel;
/*     */   private JButton addBasicButt;
/*     */   private JButton delBasicButt;
/*     */   private JButton addReverseButt;
/*     */   private JButton delReverseButt;
/*  65 */   private String str_sel_basic = "Please select basic arcs to add to the right list:";
/*  66 */   private String str_sel_reverse = "Please select reverse arcs to add to the right list:";
/*     */   
/*     */ 
/*     */   private int nBasic;
/*     */   
/*     */ 
/*     */   private int nNonbasic;
/*     */   
/*     */ 
/*     */   public IORArclistPanel(IORNAProcessController c, int sel)
/*     */   {
/*  77 */     this.controller = c;
/*     */     
/*  79 */     this.nBasic = (this.controller.data.num_nodes - 1);
/*  80 */     this.nNonbasic = (this.controller.data.num_arcs - this.controller.data.num_nodes + 1);
/*     */     
/*  82 */     this.arcModel = new DefaultListModel();
/*     */     
/*  84 */     for (int i = 0; i < this.controller.data.num_arcs; i++) {
/*  85 */       this.arcModel.addElement(this.controller.data.arcs[i].getArcName());
/*     */     }
/*     */     
/*     */ 
/*  89 */     this.arcList = new JList(this.arcModel);
/*  90 */     this.arcList.setSelectionMode(2);
/*  91 */     this.arcList.setSelectedIndex(0);
/*  92 */     this.arcList.addListSelectionListener(this);
/*  93 */     JScrollPane arcScrollPane = new JScrollPane(this.arcList);
/*  94 */     arcScrollPane.setPreferredSize(new Dimension(80, 150));
/*  95 */     arcScrollPane.setAlignmentX(0.0F);
/*  96 */     JPanel arclistPane = new JPanel();
/*  97 */     JLabel arclabel = new JLabel("Arcs:");
/*  98 */     arclabel.setForeground(Color.blue);
/*  99 */     arclabel.setLabelFor(this.arcList);
/* 100 */     arclistPane.setLayout(new BoxLayout(arclistPane, 1));
/* 101 */     arclistPane.add(arclabel);
/* 102 */     arclistPane.add(Box.createVerticalStrut(5));
/* 103 */     arclistPane.add(arcScrollPane);
/*     */     
/*     */ 
/* 106 */     this.basicModel = new DefaultListModel();
/* 107 */     this.basicList = new JList(this.basicModel);
/* 108 */     this.basicList.setSelectionMode(2);
/* 109 */     this.basicList.addListSelectionListener(this);
/* 110 */     JScrollPane basicScrollPane = new JScrollPane(this.basicList);
/* 111 */     basicScrollPane.setPreferredSize(new Dimension(80, 150));
/* 112 */     basicScrollPane.setAlignmentX(0.0F);
/* 113 */     JPanel basiclistPane = new JPanel();
/* 114 */     JLabel basiclabel = new JLabel("Basic Arcs:");
/* 115 */     basiclabel.setForeground(Color.blue);
/* 116 */     basiclabel.setLabelFor(this.basicList);
/* 117 */     basiclistPane.setLayout(new BoxLayout(basiclistPane, 1));
/* 118 */     basiclistPane.add(basiclabel);
/* 119 */     basiclistPane.add(Box.createVerticalStrut(5));
/* 120 */     basiclistPane.add(basicScrollPane);
/*     */     
/*     */ 
/*     */ 
/* 124 */     this.reverseModel = new DefaultListModel();
/* 125 */     this.reverseList = new JList(this.reverseModel);
/* 126 */     this.reverseList.setSelectionMode(2);
/* 127 */     this.reverseList.addListSelectionListener(this);
/* 128 */     JScrollPane reverseScrollPane = new JScrollPane(this.reverseList);
/* 129 */     reverseScrollPane.setPreferredSize(new Dimension(80, 150));
/* 130 */     reverseScrollPane.setAlignmentX(0.0F);
/* 131 */     JPanel reverselistPane = new JPanel();
/* 132 */     JLabel reverselabel = new JLabel("Reverse Arcs:");
/* 133 */     reverselabel.setForeground(Color.blue);
/* 134 */     reverselabel.setLabelFor(this.reverseList);
/* 135 */     reverselistPane.setLayout(new BoxLayout(reverselistPane, 1));
/* 136 */     reverselistPane.add(reverselabel);
/* 137 */     reverselistPane.add(Box.createVerticalStrut(5));
/* 138 */     reverselistPane.add(reverseScrollPane);
/*     */     
/*     */ 
/* 141 */     JPanel buttPane = new JPanel();
/* 142 */     buttPane.setLayout(new GridLayout(3, 1));
/*     */     
/* 144 */     JPanel origPanel = new JPanel();
/* 145 */     this.inditext.setAlignmentX(0.0F);
/* 146 */     this.inditext.setLabelFor(origPanel);
/*     */     
/* 148 */     if (sel == 1) {
/* 149 */       this.inditext.setText(this.str_sel_basic);
/* 150 */       this.addBasicButt = new JButton("Add to Basic Arcs");
/* 151 */       this.addBasicButt.addActionListener(new IORArclistPanel.AddBasicListener());
/*     */       
/* 153 */       this.delBasicButt = new JButton("Delete from Basic Arcs");
/* 154 */       this.delBasicButt.addActionListener(new IORArclistPanel.DelBasicListener());
/* 155 */       this.delBasicButt.setEnabled(false);
/*     */       
/* 157 */       JPanel upbuttPane = new JPanel();
/* 158 */       buttPane.add(this.addBasicButt);
/* 159 */       buttPane.add(Box.createVerticalStrut(20));
/* 160 */       buttPane.add(this.delBasicButt);
/*     */       
/* 162 */       origPanel.add(arclistPane);
/* 163 */       origPanel.add(Box.createHorizontalStrut(30));
/* 164 */       origPanel.add(buttPane);
/* 165 */       origPanel.add(Box.createHorizontalStrut(30));
/* 166 */       origPanel.add(basiclistPane);
/*     */     }
/*     */     else {
/* 169 */       this.inditext.setText(this.str_sel_reverse);
/* 170 */       this.addReverseButt = new JButton("Add to Reverse Arcs");
/* 171 */       this.addReverseButt.addActionListener(new IORArclistPanel.AddReverseListener());
/*     */       
/* 173 */       this.delReverseButt = new JButton("Delete from Reverse Arcs");
/* 174 */       this.delReverseButt.addActionListener(new IORArclistPanel.DelReverseListener());
/* 175 */       this.delReverseButt.setEnabled(false);
/*     */       
/* 177 */       JPanel downbuttPane = new JPanel();
/* 178 */       buttPane.add(this.addReverseButt);
/* 179 */       buttPane.add(Box.createVerticalStrut(20));
/* 180 */       buttPane.add(this.delReverseButt);
/*     */       
/* 182 */       origPanel.add(arclistPane);
/* 183 */       origPanel.add(Box.createHorizontalStrut(30));
/* 184 */       origPanel.add(buttPane);
/* 185 */       origPanel.add(Box.createHorizontalStrut(30));
/* 186 */       origPanel.add(reverselistPane);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 191 */     this.inditext.setBackground(getBackground());
/* 192 */     this.inditext.setForeground(Color.blue);
/*     */     
/* 194 */     this.mainPanel.setLayout(new BorderLayout());
/* 195 */     this.mainPanel.add(this.inditext, "North");
/* 196 */     this.mainPanel.add(origPanel, "Center");
/* 197 */     add(this.mainPanel);
/*     */   }
/*     */   
/*     */   public void valueChanged(ListSelectionEvent e) {}
/*     */   
/*     */   class AddBasicListener implements ActionListener { AddBasicListener() {}
/*     */     
/* 204 */     public void actionPerformed(ActionEvent e) { ListSelectionModel lsm = IORArclistPanel.this.arcList.getSelectionModel();
/* 205 */       int firstSelected = lsm.getMinSelectionIndex();
/* 206 */       int lastSelected = lsm.getMaxSelectionIndex();
/* 207 */       if ((firstSelected == -1) || (lastSelected == -1)) {
/* 208 */         Toolkit.getDefaultToolkit().beep();
/* 209 */         System.out.println("no selection");
/* 210 */         return;
/*     */       }
/*     */       
/* 213 */       for (int i = firstSelected; i <= lastSelected; i++) {
/* 214 */         if (lsm.isSelectedIndex(i))
/* 215 */           IORArclistPanel.this.basicModel.addElement(IORArclistPanel.this.arcModel.getElementAt(i));
/* 216 */         if (IORArclistPanel.this.basicModel.size() >= IORArclistPanel.this.nBasic) {
/* 217 */           IORArclistPanel.this.addBasicButt.setEnabled(false);
/* 218 */           break;
/*     */         }
/*     */       }
/* 221 */       if (IORArclistPanel.this.basicModel.size() > 0)
/* 222 */         IORArclistPanel.this.delBasicButt.setEnabled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   class DelBasicListener implements ActionListener {
/*     */     DelBasicListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/* 230 */       ListSelectionModel lsm = IORArclistPanel.this.basicList.getSelectionModel();
/* 231 */       int firstSelected = lsm.getMinSelectionIndex();
/* 232 */       int lastSelected = lsm.getMaxSelectionIndex();
/* 233 */       if ((firstSelected == -1) || (lastSelected == -1)) {
/* 234 */         Toolkit.getDefaultToolkit().beep();
/* 235 */         System.out.println("no selection");
/* 236 */         return;
/*     */       }
/*     */       
/* 239 */       for (int i = lastSelected; i >= firstSelected; i--) {
/* 240 */         if (lsm.isSelectedIndex(i))
/* 241 */           IORArclistPanel.this.basicModel.removeElementAt(i);
/*     */       }
/* 243 */       if (IORArclistPanel.this.basicModel.size() == 0) {
/* 244 */         IORArclistPanel.this.delBasicButt.setEnabled(false);
/*     */       }
/*     */       else {
/* 247 */         IORArclistPanel.this.basicList.setSelectedIndex(0);
/* 248 */         IORArclistPanel.this.addBasicButt.setEnabled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   class AddReverseListener implements ActionListener {
/*     */     AddReverseListener() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/* 257 */       ListSelectionModel lsm = IORArclistPanel.this.arcList.getSelectionModel();
/* 258 */       int firstSelected = lsm.getMinSelectionIndex();
/* 259 */       int lastSelected = lsm.getMaxSelectionIndex();
/* 260 */       if ((firstSelected == -1) || (lastSelected == -1)) {
/* 261 */         Toolkit.getDefaultToolkit().beep();
/* 262 */         System.out.println("no selection");
/* 263 */         return;
/*     */       }
/*     */       
/*     */ 
/* 267 */       for (int i = firstSelected; i <= lastSelected; i++) {
/* 268 */         if (lsm.isSelectedIndex(i)) {
/* 269 */           String newname = "";
/* 270 */           String arcname = (String)IORArclistPanel.this.arcModel.getElementAt(i);
/* 271 */           newname = String.valueOf(String.valueOf(newname)).concat(String.valueOf(String.valueOf(arcname.charAt(1))));
/* 272 */           newname = String.valueOf(String.valueOf(newname)).concat(String.valueOf(String.valueOf(arcname.charAt(0))));
/* 273 */           IORArclistPanel.this.reverseModel.addElement(newname);
/*     */         }
/* 275 */         if (IORArclistPanel.this.reverseModel.size() >= IORArclistPanel.this.nNonbasic) {
/* 276 */           IORArclistPanel.this.addReverseButt.setEnabled(false);
/* 277 */           break;
/*     */         }
/*     */       }
/*     */       
/* 281 */       if (IORArclistPanel.this.reverseModel.size() > 0)
/* 282 */         IORArclistPanel.this.delReverseButt.setEnabled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   class DelReverseListener implements ActionListener {
/*     */     DelReverseListener() {}
/*     */     
/* 289 */     public void actionPerformed(ActionEvent e) { ListSelectionModel lsm = IORArclistPanel.this.reverseList.getSelectionModel();
/* 290 */       int firstSelected = lsm.getMinSelectionIndex();
/* 291 */       int lastSelected = lsm.getMaxSelectionIndex();
/* 292 */       if ((firstSelected == -1) || (lastSelected == -1)) {
/* 293 */         Toolkit.getDefaultToolkit().beep();
/* 294 */         System.out.println("no selection");
/* 295 */         return;
/*     */       }
/*     */       
/* 298 */       for (int i = lastSelected; i >= firstSelected; i--) {
/* 299 */         if (lsm.isSelectedIndex(i))
/* 300 */           IORArclistPanel.this.reverseModel.removeElementAt(i);
/*     */       }
/* 302 */       if (IORArclistPanel.this.reverseModel.size() == 0) {
/* 303 */         IORArclistPanel.this.delReverseButt.setEnabled(false);
/*     */       }
/*     */       else {
/* 306 */         IORArclistPanel.this.reverseList.setSelectedIndex(0);
/* 307 */         IORArclistPanel.this.addReverseButt.setEnabled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORArclistPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */