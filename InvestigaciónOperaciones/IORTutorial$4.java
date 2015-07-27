/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IORTutorial$4
/*     */   implements ActionListener
/*     */ {
/*     */   private final IORTutorial this$0;
/*     */   
/* 321 */   IORTutorial$4(IORTutorial this$0) { this.this$0 = this$0; }
/*     */   
/* 323 */   public void actionPerformed(ActionEvent e) { if ((IORTutorial.access$0(this.this$0) == null) || (IORTutorial.access$0(this.this$0).currentActionPanel == null)) {
/* 324 */       System.out.println("Can't save file. null");
/* 325 */       return;
/*     */     }
/* 327 */     String file = this.this$0.filenameChosenByUser(false);
/* 328 */     if (file == null)
/* 329 */       return;
/* 330 */     int fileType = IORTutorial.access$5(this.this$0, file);
/* 331 */     if (fileType == 1) {
/* 332 */       JOptionPane.showMessageDialog(IORTutorial.access$2(this.this$0), "Can't overwrite this file.", "Information", 1);
/* 333 */       return;
/*     */     }
/* 335 */     if (fileType == 2) {}
/*     */     try
/*     */     {
/* 338 */       ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
/* 339 */       out.writeObject(IORTutorial.access$6());
/* 340 */       if ((IORTutorial.access$0(this.this$0) instanceof IORLPProcessController)) {
/* 341 */         out.writeObject(new Integer(1));
/* 342 */       } else if ((IORTutorial.access$0(this.this$0) instanceof IORTPProcessController)) {
/* 343 */         out.writeObject(new Integer(2));
/* 344 */       } else if ((IORTutorial.access$0(this.this$0) instanceof IORNAProcessController)) {
/* 345 */         out.writeObject(new Integer(3));
/* 346 */       } else if ((IORTutorial.access$0(this.this$0) instanceof IORIPProcessController)) {
/* 347 */         out.writeObject(new Integer(4));
/* 348 */       } else if ((IORTutorial.access$0(this.this$0) instanceof IORNLPProcessController)) {
/* 349 */         out.writeObject(new Integer(5));
/* 350 */       } else if ((IORTutorial.access$0(this.this$0) instanceof IORMCProcessController)) {
/* 351 */         out.writeObject(new Integer(6));
/* 352 */       } else if ((IORTutorial.access$0(this.this$0) instanceof IORQTProcessController)) {
/* 353 */         out.writeObject(new Integer(7));
/* 354 */       } else if ((IORTutorial.access$0(this.this$0) instanceof IORITProcessController)) {
/* 355 */         out.writeObject(new Integer(8));
/* 356 */       } else if ((IORTutorial.access$0(this.this$0) instanceof IORMDProcessController)) {
/* 357 */         out.writeObject(new Integer(9));
/* 358 */       } else if ((IORTutorial.access$0(this.this$0) instanceof IORSAProcessController)) {
/* 359 */         out.writeObject(new Integer(10));
/* 360 */       } else if ((IORTutorial.access$0(this.this$0) instanceof IORTSPProcessController)) {
/* 361 */         out.writeObject(new Integer(12));
/* 362 */       } else if ((IORTutorial.access$0(this.this$0) instanceof IORHungarianProcessController)) {
/* 363 */         out.writeObject(new Integer(13));
/*     */       }
/* 365 */       else if ((IORTutorial.access$0(this.this$0) instanceof IORGraphicalProcessController)) {
/* 366 */         out.writeObject(new Integer(14));
/*     */       } else {
/* 368 */         System.out.println("Can't Save file.");
/* 369 */         return;
/*     */       }
/* 371 */       IORTutorial.access$0(this.this$0).SaveFile(out);
/* 372 */       out.close();
/*     */     }
/*     */     catch (Exception e1) {
/* 375 */       e1.printStackTrace();
/* 376 */       System.out.println("Save fails is".concat(String.valueOf(String.valueOf(e1))));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORTutorial$4.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */