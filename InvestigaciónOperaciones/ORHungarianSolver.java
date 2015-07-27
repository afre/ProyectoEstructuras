/*     */ import java.awt.Point;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ORHungarianSolver
/*     */   extends ORSolverBase
/*     */   implements Serializable
/*     */ {
/*  21 */   private ArrayList printData = new ArrayList();
/*     */   private int printSize;
/*     */   private NumberFormat nf;
/*     */   
/*     */   public ORHungarianSolver() {
/*  26 */     this.nf = NumberFormat.getInstance();
/*  27 */     this.nf.setMaximumFractionDigits(2);
/*  28 */     this.nf.setMinimumFractionDigits(0);
/*     */   }
/*     */   
/*     */   public void writeObject(ObjectOutputStream out) {
/*     */     try {
/*  33 */       this.printSize = this.printData.size();
/*  34 */       out.write(this.printSize);
/*  35 */       for (int i = 0; i < this.printData.size(); i++) {
/*  36 */         out.writeObject(this.printData.get(i));
/*     */       }
/*     */     }
/*     */     catch (IOException io) {
/*  40 */       System.out.println("Write Hungarian Solver Eroor!");
/*     */     }
/*     */   }
/*     */   
/*     */   public void readObject(ObjectInputStream in) {
/*     */     try {
/*  46 */       this.printSize = in.read();
/*  47 */       for (int i = 0; i < this.printSize; i++) {
/*  48 */         this.printData.add(in.readObject());
/*     */       }
/*     */     }
/*     */     catch (Exception io) {
/*  52 */       System.out.println("Write Hungarian Solver Eroor!");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean doWork(IOROperation op)
/*     */   {
/*  58 */     throw new UnsupportedOperationException("Method doWork() not yet implemented.");
/*     */   }
/*     */   
/*     */ 
/*     */   protected void ORPrint()
/*     */   {
/*  64 */     if (this.procedure == 1) {
/*  65 */       PrintLine("Solve an Assignment Problem Interactively:");
/*  66 */       printHungarian();
/*  67 */     } else if (this.procedure == 2) {
/*  68 */       PrintLine("Solve an Assignment Problem Automatically:");
/*  69 */       printHungarian();
/*     */     }
/*  71 */     System.out.println("procedure = ".concat(String.valueOf(String.valueOf(this.procedure))));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void insertPrintData(IORHungarianProcessController.HungarianData data)
/*     */   {
/*  80 */     this.printData.add(data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removePrintData()
/*     */   {
/*  87 */     this.printData.remove(this.printData.size() - 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void printHungarian()
/*     */   {
/*  95 */     if (this.printData.size() == 0)
/*  96 */       return;
/*  97 */     int task = ((IORHungarianProcessController.HungarianData)this.printData.get(0)).taskNumber;
/*     */     
/*  99 */     PrintLine("Number of tasks:  ".concat(String.valueOf(String.valueOf(FormatInteger(task, 3, 0, 1)))));
/* 100 */     for (int i = 0; i < this.printData.size(); i++) {
/* 101 */       IORHungarianProcessController.HungarianData temp = (IORHungarianProcessController.HungarianData)this.printData.get(i);
/*     */       
/* 103 */       int step = temp.currentStep;
/* 104 */       switch (step) {
/*     */       case 0: 
/* 106 */         printTable(step, temp);
/* 107 */         break;
/*     */       case 10: 
/* 109 */         printTable(step, temp);
/* 110 */         break;
/*     */       case 11: 
/* 112 */         printTable(step, temp);
/* 113 */         break;
/*     */       case 31: 
/* 115 */         printTable(step, temp);
/* 116 */         break;
/*     */       case 41: 
/* 118 */         printTable(step, temp);
/* 119 */         break;
/*     */       case 42: 
/* 121 */         printTable(step, temp);
/* 122 */         break;
/*     */       case 60: 
/* 124 */         printTable(step, temp);
/*     */       }
/*     */       
/*     */     }
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
/*     */   private void printTable(int step, IORHungarianProcessController.HungarianData hungarian)
/*     */   {
/* 140 */     int Width = 7;
/* 141 */     int taskNum = hungarian.taskNumber;
/* 142 */     switch (step) {
/*     */     case 0: 
/* 144 */       SkipLine();
/* 145 */       TabPrintLine(0, "Step :");
/* 146 */       TabPrintLine(0, "Subtract the smallest number in each row from every number in the row.");
/*     */       
/* 148 */       TabPrintLine(0, "Enter the results in a new table.");
/* 149 */       printTableHead(step, taskNum);
/* 150 */       step0(hungarian);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 201 */       break;
/*     */     case 10: 
/* 153 */       SkipLine();
/* 154 */       TabPrintLine(0, "Step :");
/* 155 */       TabPrintLine(0, "Subtract the smallest number in each column of the new table from every number in the column.");
/* 156 */       printTableHead(step, taskNum);
/* 157 */       step10(hungarian);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 201 */       break;
/*     */     case 11: 
/* 160 */       SkipLine();
/* 161 */       TabPrintLine(0, "Step :");
/* 162 */       TabPrintLine(0, "Enter the results in a new table.");
/* 163 */       TabPrintLine(0, "");
/*     */       
/* 165 */       printTableHead(step, taskNum);
/* 166 */       step42(hungarian);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 201 */       break;
/*     */     case 31: 
/* 169 */       SkipLine();
/* 170 */       TabPrintLine(0, "Step :");
/* 171 */       TabPrintLine(0, "Determine the minimum number of lines needed to cross out all zeros.");
/*     */       
/* 173 */       printTableHead(step, taskNum);
/* 174 */       step31(hungarian);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 201 */       break;
/*     */     case 41: 
/* 177 */       SkipLine();
/* 178 */       TabPrintLine(0, "Step :");
/* 179 */       TabPrintLine(0, "Select the smallest number from all the uncovered numbers");
/*     */       
/* 181 */       printTableHead(step, taskNum);
/* 182 */       step41(hungarian);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 201 */       break;
/*     */     case 42: 
/* 185 */       SkipLine();
/* 186 */       TabPrintLine(0, "Step :");
/* 187 */       TabPrintLine(0, "The smallest number selected at the preceding step will be subtracted from every uncovered by ");
/*     */       
/* 189 */       TabPrintLine(0, "number and added to every number at the intersection of covering lines automatically. ");
/* 190 */       printTableHead(step, taskNum);
/* 191 */       step42(hungarian);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 201 */       break;
/*     */     case 60: 
/* 194 */       SkipLine();
/* 195 */       TabPrintLine(0, "Make the assignments.");
/* 196 */       printTableHead(step, taskNum);
/* 197 */       step60(hungarian);
/*     */       
/*     */ 
/*     */ 
/* 201 */       break;
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void printTableHead(int step, int taskNum)
/*     */   {
/* 210 */     int Width = 7;
/* 211 */     String[] taskName = { "", "A", "B", "C", "D", "E", "F" };
/*     */     
/* 213 */     int task = taskNum;
/* 214 */     SkipLine();
/* 215 */     TabPrintLine(Width * task / 2 + 10, "Task");
/* 216 */     TabPrint(3, "Assignee");
/* 217 */     TabPrint(11, "|");
/* 218 */     for (int j = 1; j <= task; j++) {
/* 219 */       TabPrint(13 + (j - 1) * Width, taskName[j]);
/*     */     }
/* 221 */     TabPrint(13 + task * Width, "|");
/* 222 */     if (step == 0) {
/* 223 */       TabPrintLine(15 + task * Width, "Row Min");
/*     */     } else
/* 225 */       TabPrintLine(0, "");
/* 226 */     Print("__________|");
/* 227 */     for (j = 1; j <= task; j++)
/* 228 */       Print("__________".substring(0, Width));
/* 229 */     PrintLine("_|________");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private String[][] formatData(String[][] data)
/*     */   {
/* 237 */     for (int i = 0; i < data.length; i++) {
/* 238 */       for (int j = 0; j < data[0].length; j++) {
/*     */         try {
/* 240 */           double temp = Double.valueOf(data[i][j]).doubleValue();
/* 241 */           data[i][j] = this.nf.format(temp);
/*     */         }
/*     */         catch (Exception localException) {}
/*     */       }
/*     */     }
/*     */     
/* 247 */     return data;
/*     */   }
/*     */   
/*     */ 
/*     */   private void step0(IORHungarianProcessController.HungarianData hungarian)
/*     */   {
/* 253 */     int Width = 7;
/* 254 */     String[][] data = hungarian.data;
/*     */     
/* 256 */     data = formatData(data);
/* 257 */     for (int i = 0; i < data.length; i++) {
/* 258 */       for (int j = 0; j < data[0].length; j++)
/* 259 */         if (j == 0) {
/* 260 */           TabPrint(13 + (j - 1) * Width, data[i][j]);
/* 261 */           TabPrint(11, "|");
/*     */         }
/* 263 */         else if (j == data[0].length - 1) {
/* 264 */           TabPrint(13 + (j - 1) * Width, "|");
/* 265 */           TabPrint(15 + (j - 1) * Width, data[i][j]);
/*     */         }
/*     */         else {
/* 268 */           TabPrint(13 + (j - 1) * Width, data[i][j]);
/*     */         }
/* 270 */       TabPrintLine(0, "");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void step10(IORHungarianProcessController.HungarianData hungarian)
/*     */   {
/* 281 */     int Width = 7;
/* 282 */     String[][] data = hungarian.data;
/* 283 */     data = formatData(data);
/* 284 */     for (int i = 0; i < data.length; i++) {
/* 285 */       for (int j = 0; j < data[0].length; j++)
/* 286 */         if (j == 0) {
/* 287 */           if (i == data.length - 1) {
/* 288 */             TabPrint(13 + (j - 1) * Width - data[i][j].length(), data[i][j]);
/*     */           }
/*     */           else
/* 291 */             TabPrint(13 + (j - 1) * Width, data[i][j]);
/* 292 */           TabPrint(11, "|");
/*     */         }
/* 294 */         else if (j == data[0].length - 1) {
/* 295 */           TabPrint(13 + (j - 1) * Width, "|");
/* 296 */           TabPrint(15 + (j - 1) * Width, data[i][j]);
/*     */         }
/*     */         else {
/* 299 */           TabPrint(13 + (j - 1) * Width, data[i][j]);
/*     */         }
/* 301 */       TabPrintLine(0, "");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void step31(IORHungarianProcessController.HungarianData hungarian)
/*     */   {
/* 314 */     int Width = 7;
/* 315 */     String[][] data = hungarian.data;
/* 316 */     data = formatData(data);
/* 317 */     int[] select = hungarian.selectLines;
/* 318 */     int task = hungarian.taskNumber;
/* 319 */     HashMap row = new HashMap();
/* 320 */     HashMap col = new HashMap();
/* 321 */     for (int i = 0; i < select.length; i++) {
/* 322 */       if (select[i] < task) {
/* 323 */         col.put(new Integer(select[i]), new Integer(select[i]));
/*     */       } else {
/* 325 */         row.put(new Integer(select[i] - task), new Integer(select[i] - task));
/*     */       }
/*     */     }
/*     */     
/* 329 */     for (int i = 0; i < data.length - 1; i++) {
/* 330 */       if (i == 0) {
/* 331 */         TabPrint(11, "|");
/* 332 */         for (int j = 0; j < data[0].length; j++) {
/* 333 */           if (col.containsKey(new Integer(j)))
/* 334 */             TabPrint(13 + j * Width, "|");
/*     */         }
/* 336 */         TabPrint(13 + (data.length - 1) * Width, "|");
/* 337 */         TabPrintLine(0, "");
/*     */       }
/*     */       
/* 340 */       for (int j = 0; j < data[0].length; j++) {
/* 341 */         if (!row.containsKey(new Integer(i))) {
/* 342 */           if (j == 0) {
/* 343 */             TabPrint(13 + (j - 1) * Width, data[i][j]);
/* 344 */             TabPrint(11, "|");
/*     */           }
/* 346 */           else if (j == data[0].length - 1) {
/* 347 */             TabPrint(13 + (j - 1) * Width, "|");
/*     */           }
/*     */           else {
/* 350 */             TabPrint(13 + (j - 1) * Width, data[i][j]);
/*     */           }
/*     */         }
/* 353 */         else if (j == 0) {
/* 354 */           TabPrint(13 + (j - 1) * Width, data[i][j]);
/* 355 */           TabPrint(11, "|");
/*     */         }
/* 357 */         else if (j == data[0].length - 1) {
/* 358 */           TabPrint(13 + (j - 2) * Width, "--------".substring(0, Width - 1));
/*     */           
/* 360 */           TabPrint(13 + (j - 1) * Width, "|");
/*     */         }
/*     */         else {
/* 363 */           if (j != 1) {
/* 364 */             TabPrint(13 + (j - 2) * Width, "--------".substring(0, Width - 1));
/*     */           }
/*     */           else {
/* 367 */             TabPrint(13 + (j - 2) * Width, "--------".substring(0, 1));
/*     */           }
/* 369 */           TabPrint(13 + (j - 1) * Width, data[i][j]);
/*     */         }
/*     */       }
/*     */       
/* 373 */       TabPrintLine(0, "");
/*     */       
/* 375 */       TabPrint(11, "|");
/* 376 */       for (int j = 0; j < data[0].length; j++) {
/* 377 */         if (col.containsKey(new Integer(j)))
/* 378 */           TabPrint(13 + j * Width, "|");
/*     */       }
/* 380 */       TabPrint(13 + (data.length - 1) * Width, "|");
/* 381 */       TabPrintLine(0, "");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void step41(IORHungarianProcessController.HungarianData hungarian)
/*     */   {
/* 388 */     int Width = 7;
/* 389 */     String[][] data = hungarian.data;
/* 390 */     data = formatData(data);
/* 391 */     Point p = hungarian.min;
/* 392 */     if (p == null) return;
/* 393 */     String temp = "";
/* 394 */     for (int i = 0; i < data.length; i++) {
/* 395 */       for (int j = 0; j < data[0].length; j++) {
/* 396 */         temp = data[i][j];
/* 397 */         if ((i == p.x) && (j == p.y))
/* 398 */           temp = String.valueOf(String.valueOf(new StringBuffer("[").append(temp).append("]")));
/* 399 */         if (j == 0) {
/* 400 */           if (i == data.length - 1) {
/* 401 */             TabPrint(13 + (j - 1) * Width - temp.length(), data[i][j]);
/*     */           }
/*     */           else
/* 404 */             TabPrint(13 + (j - 1) * Width, temp);
/* 405 */           TabPrint(11, "|");
/*     */         }
/* 407 */         else if (j == data[0].length - 1) {
/* 408 */           TabPrint(13 + (j - 1) * Width, "|");
/* 409 */           TabPrint(15 + (j - 1) * Width, temp);
/*     */         }
/*     */         else {
/* 412 */           TabPrint(13 + (j - 1) * Width, temp);
/*     */         } }
/* 414 */       TabPrintLine(0, "");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void step42(IORHungarianProcessController.HungarianData hungarian)
/*     */   {
/* 422 */     int Width = 7;
/* 423 */     String[][] data = hungarian.data;
/* 424 */     data = formatData(data);
/* 425 */     String temp = "";
/* 426 */     for (int i = 0; i < data.length; i++) {
/* 427 */       for (int j = 0; j < data[0].length; j++) {
/* 428 */         temp = data[i][j];
/* 429 */         if (j == 0) {
/* 430 */           if (i == data.length - 1) {
/* 431 */             TabPrint(13 + (j - 1) * Width - temp.length(), data[i][j]);
/*     */           }
/*     */           else
/* 434 */             TabPrint(13 + (j - 1) * Width, temp);
/* 435 */           TabPrint(11, "|");
/*     */         }
/* 437 */         else if (j == data[0].length - 1) {
/* 438 */           TabPrint(13 + (j - 1) * Width, "|");
/* 439 */           TabPrint(15 + (j - 1) * Width, temp);
/*     */         }
/*     */         else {
/* 442 */           TabPrint(13 + (j - 1) * Width, temp);
/*     */         } }
/* 444 */       TabPrintLine(0, "");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void step60(IORHungarianProcessController.HungarianData hungarian)
/*     */   {
/* 457 */     int Width = 7;
/* 458 */     String[][] data = hungarian.data;
/* 459 */     data = formatData(data);
/* 460 */     Point[] best = hungarian.bestAssign;
/* 461 */     for (int i = 0; i < data.length - 1; i++) {
/* 462 */       for (int j = 0; j < data[0].length; j++) {
/* 463 */         Point temp = best[i];
/* 464 */         String tempData = data[i][j];
/* 465 */         if ((temp.x == i) && (temp.y == j)) {
/* 466 */           tempData = String.valueOf(String.valueOf(new StringBuffer("[").append(tempData).append("]")));
/*     */         }
/* 468 */         if (j == 0) {
/* 469 */           TabPrint(13 + (j - 1) * Width, tempData);
/* 470 */           TabPrint(11, "|");
/*     */         }
/* 472 */         else if (j == data[0].length - 1) {
/* 473 */           TabPrint(13 + (j - 1) * Width, "|");
/* 474 */           TabPrint(15 + (j - 1) * Width, tempData);
/*     */         }
/*     */         else {
/* 477 */           TabPrint(13 + (j - 1) * Width, tempData);
/*     */         } }
/* 479 */       TabPrintLine(0, "");
/*     */     }
/*     */     
/* 482 */     SkipLine();
/*     */     
/* 484 */     for (int i = 0; i < hungarian.bestSolution.length; i++) {
/* 485 */       TabPrintLine(0, hungarian.bestSolution[i]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void emptyPrintData()
/*     */   {
/* 494 */     this.printData.clear();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\ORHungarianSolver.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */