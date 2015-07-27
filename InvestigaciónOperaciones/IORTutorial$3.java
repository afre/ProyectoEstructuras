/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StreamCorruptedException;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JOptionPane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IORTutorial$3
/*     */   implements ActionListener
/*     */ {
/*     */   private final IORTutorial this$0;
/*     */   
/* 219 */   IORTutorial$3(IORTutorial this$0) { this.this$0 = this$0; }
/*     */   
/* 221 */   public void actionPerformed(ActionEvent e) { String file = this.this$0.filenameChosenByUser(true);
/* 222 */     if (file == null) return;
/*     */     try {
/* 224 */       ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
/* 225 */       if (!IORTutorial.access$1(this.this$0, in)) {
/* 226 */         JOptionPane.showMessageDialog(IORTutorial.access$2(this.this$0), "This is not a valid saving file.", "Information", 1);
/* 227 */         in.close();
/* 228 */         return;
/*     */       }
/* 230 */       IORTutorial.access$0(this.this$0).removeAreaDependentMenus();
/* 231 */       int WhichArea = ((Integer)in.readObject()).intValue();
/* 232 */       switch (WhichArea) {
/*     */       case 1: 
/* 234 */         IORTutorial.access$3(this.this$0, new IORLPProcessController(this.this$0));
/* 235 */         IORTutorial.access$4(this.this$0).getMenu(1).getItem(0).setSelected(true);
/* 236 */         break;
/*     */       case 2: 
/* 238 */         IORTutorial.access$3(this.this$0, new IORTPProcessController(this.this$0));
/* 239 */         IORTutorial.access$4(this.this$0).getMenu(1).getItem(1).setSelected(true);
/* 240 */         break;
/*     */       case 3: 
/* 242 */         IORTutorial.access$3(this.this$0, new IORNAProcessController(this.this$0));
/* 243 */         IORTutorial.access$4(this.this$0).getMenu(1).getItem(3).setSelected(true);
/* 244 */         break;
/*     */       case 4: 
/* 246 */         IORTutorial.access$3(this.this$0, new IORIPProcessController(this.this$0));
/* 247 */         IORTutorial.access$4(this.this$0).getMenu(1).getItem(4).setSelected(true);
/* 248 */         break;
/*     */       case 5: 
/* 250 */         IORTutorial.access$3(this.this$0, new IORNLPProcessController(this.this$0));
/* 251 */         IORTutorial.access$4(this.this$0).getMenu(1).getItem(5).setSelected(true);
/* 252 */         break;
/*     */       case 6: 
/* 254 */         IORTutorial.access$3(this.this$0, new IORMCProcessController(this.this$0));
/* 255 */         IORTutorial.access$4(this.this$0).getMenu(1).getItem(8).setSelected(true);
/* 256 */         break;
/*     */       case 7: 
/* 258 */         IORTutorial.access$3(this.this$0, new IORQTProcessController(this.this$0));
/* 259 */         IORTutorial.access$4(this.this$0).getMenu(1).getItem(9).setSelected(true);
/* 260 */         break;
/*     */       case 8: 
/* 262 */         IORTutorial.access$3(this.this$0, new IORITProcessController(this.this$0));
/* 263 */         IORTutorial.access$4(this.this$0).getMenu(1).getItem(10).setSelected(true);
/* 264 */         break;
/*     */       case 9: 
/* 266 */         IORTutorial.access$3(this.this$0, new IORMDProcessController(this.this$0));
/* 267 */         IORTutorial.access$4(this.this$0).getMenu(1).getItem(11).setSelected(true);
/* 268 */         break;
/*     */       case 10: 
/* 270 */         IORTutorial.access$3(this.this$0, new IORSAProcessController(this.this$0));
/* 271 */         IORTutorial.access$4(this.this$0).getMenu(1).getItem(12).setSelected(true);
/* 272 */         break;
/*     */       case 11: 
/* 274 */         IORTutorial.access$3(this.this$0, new IORSAProcessController(this.this$0));
/* 275 */         IORTutorial.access$4(this.this$0).getMenu(1).getItem(13).setSelected(true);
/* 276 */         break;
/*     */       case 12: 
/* 278 */         IORTutorial.access$3(this.this$0, new IORTSPProcessController(this.this$0));
/* 279 */         IORTutorial.access$4(this.this$0).getMenu(1).getItem(13).setSelected(true);
/* 280 */         break;
/*     */       case 13: 
/* 282 */         IORTutorial.access$3(this.this$0, new IORHungarianProcessController(this.this$0));
/* 283 */         IORTutorial.access$4(this.this$0).getMenu(1).getItem(2).setSelected(true);
/* 284 */         break;
/*     */       case 14: 
/* 286 */         IORTutorial.access$3(this.this$0, new IORGraphicalProcessController(this.this$0));
/* 287 */         IORTutorial.access$4(this.this$0).getMenu(1).getItem(14).setSelected(true);
/* 288 */         break;
/*     */       default: 
/* 290 */         System.out.println("Open fails");
/* 291 */         return;
/*     */       }
/*     */       
/* 294 */       IORTutorial.access$0(this.this$0).createAreaDependentMenus();
/* 295 */       IORTutorial.access$0(this.this$0).LoadFile(in);
/* 296 */       in.close();
/*     */     }
/*     */     catch (FileNotFoundException e1) {
/* 299 */       e1.printStackTrace();
/* 300 */       System.out.println("Open fails Exception1 : ".concat(String.valueOf(String.valueOf(e1))));
/*     */     }
/*     */     catch (StreamCorruptedException e2) {
/* 303 */       JOptionPane.showMessageDialog(IORTutorial.access$2(this.this$0), "This is not a valid saving file.", "Information", 1);
/*     */     } catch (IOException e3) {
/* 305 */       System.out.println("IOException : ".concat(String.valueOf(String.valueOf(e3))));
/*     */     } catch (ClassNotFoundException e3) {
/* 307 */       System.out.println("ClassNotFoundException : ".concat(String.valueOf(String.valueOf(e3))));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTutorial$3.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */