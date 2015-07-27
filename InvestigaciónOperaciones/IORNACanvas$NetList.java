/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Point;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.Stroke;
/*      */ import java.io.PrintStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class IORNACanvas$NetList
/*      */ {
/*      */   private final int NE = 1;
/*      */   private final int NW = 2;
/*      */   private final int SW = 3;
/*      */   private final int SE = 4;
/*      */   private final int N = 11;
/*      */   private final int S = 12;
/*      */   private final int r = 32;
/*      */   private final int L0 = 6;
/*      */   private double rx;
/*      */   private double ry;
/*      */   int np;
/*      */   int nd;
/*      */   int na;
/*      */   Point[] p;
/*      */   int[][] arc;
/*      */   int[][] ap;
/*      */   int[] q;
/*      */   IORNAProcessController.NAData data;
/*      */   private final IORNACanvas this$0;
/*      */   
/*      */   public IORNACanvas$NetList(IORNACanvas this$0, IORNAProcessController.NAData d)
/*      */   {
/*  143 */     this.this$0 = this$0;this.NE = 1;this.NW = 2;this.SW = 3;this.SE = 4;this.N = 11;this.S = 12;this.r = 32;this.L0 = 6;this.rx = 1.0D;this.ry = 1.0D;this.np = -1;this.nd = -1;this.na = -1;this.data = null;
/*      */     
/*  145 */     this.data = d;
/*      */     
/*  147 */     switch (this.data.which_problem) {
/*      */     case 2: 
/*  149 */       initializeProblem2();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  172 */       break;
/*      */     case 3: 
/*  152 */       initializeProblem3();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  172 */       break;
/*      */     case 4: 
/*  155 */       initializeProblem4();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  172 */       break;
/*      */     case 5: 
/*  158 */       initializeProblem5();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  172 */       break;
/*      */     case 6: 
/*  161 */       initializeProblem6();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  172 */       break;
/*      */     case 7: 
/*  164 */       initializeProblem7();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  172 */       break;
/*      */     case 8: 
/*  167 */       initializeProblem8();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  172 */       break;
/*      */     default: 
/*  170 */       System.err.println("ERROR IN NETLIST");
/*      */     }
/*      */   }
/*      */   
/*      */   private void initializeProblem3()
/*      */   {
/*  176 */     this.np = 5;
/*  177 */     this.nd = 0;
/*  178 */     this.na = 6;
/*  179 */     this.p = new Point[this.np + this.nd];
/*      */     
/*      */ 
/*  182 */     this.p[0] = new Point(40, 40);
/*  183 */     this.p[1] = new Point(40, 245);
/*  184 */     this.p[2] = new Point(230, 145);
/*  185 */     this.p[3] = new Point(420, 40);
/*  186 */     this.p[4] = new Point(420, 245);
/*  187 */     this.q = new int[this.np];
/*  188 */     this.q[0] = 2;
/*  189 */     this.q[1] = 3;
/*  190 */     this.q[2] = 2;
/*  191 */     this.q[3] = 1;
/*  192 */     this.q[4] = 4;
/*      */     
/*  194 */     this.arc = new int[this.na][];
/*  195 */     this.ap = new int[this.na][2];
/*      */     
/*  197 */     this.arc[0] = new int[2];
/*  198 */     this.arc[0][0] = 0;
/*  199 */     this.arc[0][1] = 2;
/*  200 */     this.ap[0][0] = 4;
/*  201 */     this.ap[0][1] = 3;
/*      */     
/*  203 */     this.arc[1] = new int[2];
/*  204 */     this.arc[1][0] = 0;
/*  205 */     this.arc[1][1] = 3;
/*  206 */     this.ap[1][0] = 4;
/*  207 */     this.ap[1][1] = 3;
/*      */     
/*  209 */     this.arc[2] = new int[2];
/*  210 */     this.arc[2][0] = 1;
/*  211 */     this.arc[2][1] = 2;
/*  212 */     this.ap[2][0] = 3;
/*  213 */     this.ap[2][1] = 3;
/*      */     
/*  215 */     this.arc[3] = new int[2];
/*  216 */     this.arc[3][0] = 1;
/*  217 */     this.arc[3][1] = 4;
/*  218 */     this.ap[3][0] = 3;
/*  219 */     this.ap[3][1] = 3;
/*      */     
/*  221 */     this.arc[4] = new int[2];
/*  222 */     this.arc[4][0] = 2;
/*  223 */     this.arc[4][1] = 3;
/*  224 */     this.ap[4][0] = 4;
/*  225 */     this.ap[4][1] = 3;
/*      */     
/*  227 */     this.arc[5] = new int[2];
/*  228 */     this.arc[5][0] = 2;
/*  229 */     this.arc[5][1] = 4;
/*  230 */     this.ap[5][0] = 3;
/*  231 */     this.ap[5][1] = 3;
/*      */   }
/*      */   
/*      */   private void initializeProblem4()
/*      */   {
/*  236 */     this.np = 4;
/*  237 */     this.nd = 7;
/*  238 */     this.na = 6;
/*  239 */     this.p = new Point[this.np + this.nd];
/*      */     
/*  241 */     this.p[0] = new Point(25, 120);
/*  242 */     this.p[1] = new Point(165, 120);
/*  243 */     this.p[2] = new Point(305, 120);
/*  244 */     this.p[3] = new Point(445, 120);
/*      */     
/*  246 */     this.p[4] = new Point(35, 190);
/*  247 */     this.p[5] = new Point(305, 190);
/*  248 */     this.p[6] = new Point(25, 240);
/*  249 */     this.p[7] = new Point(445, 240);
/*  250 */     this.p[8] = new Point(165, 50);
/*  251 */     this.p[9] = new Point(445, 50);
/*  252 */     this.p[10] = new Point(35, 132);
/*      */     
/*  254 */     this.q = new int[this.np];
/*  255 */     this.q[0] = 2;
/*  256 */     this.q[1] = 11;
/*  257 */     this.q[2] = 12;
/*  258 */     this.q[3] = 1;
/*      */     
/*      */ 
/*  261 */     this.arc = new int[this.na][];
/*  262 */     this.ap = new int[this.na][2];
/*      */     
/*  264 */     this.arc[0] = new int[2];
/*  265 */     this.arc[0][0] = 0;
/*  266 */     this.arc[0][1] = 1;
/*  267 */     this.ap[0][0] = 3;
/*  268 */     this.ap[0][1] = 3;
/*      */     
/*  270 */     this.arc[1] = new int[5];
/*  271 */     this.arc[1][0] = 0;
/*  272 */     this.arc[1][1] = 10;
/*  273 */     this.arc[1][2] = 4;
/*  274 */     this.arc[1][3] = 5;
/*  275 */     this.arc[1][4] = 2;
/*  276 */     this.ap[1][0] = 3;
/*  277 */     this.ap[1][1] = 6;
/*      */     
/*  279 */     this.arc[2] = new int[4];
/*  280 */     this.arc[2][0] = 0;
/*  281 */     this.arc[2][1] = 6;
/*  282 */     this.arc[2][2] = 7;
/*  283 */     this.arc[2][3] = 3;
/*  284 */     this.ap[2][0] = 3;
/*  285 */     this.ap[2][1] = 6;
/*  286 */     this.arc[3] = new int[2];
/*  287 */     this.arc[3][0] = 1;
/*  288 */     this.arc[3][1] = 2;
/*  289 */     this.ap[3][0] = 3;
/*  290 */     this.ap[3][1] = 3;
/*  291 */     this.arc[4] = new int[4];
/*  292 */     this.arc[4][0] = 1;
/*  293 */     this.arc[4][1] = 8;
/*  294 */     this.arc[4][2] = 9;
/*  295 */     this.arc[4][3] = 3;
/*  296 */     this.ap[4][0] = 3;
/*  297 */     this.ap[4][1] = 6;
/*  298 */     this.arc[5] = new int[2];
/*  299 */     this.arc[5][0] = 2;
/*  300 */     this.arc[5][1] = 3;
/*  301 */     this.ap[5][0] = 3;
/*  302 */     this.ap[5][1] = 3;
/*      */   }
/*      */   
/*      */   private void initializeProblem5()
/*      */   {
/*  307 */     this.np = 7;
/*  308 */     this.nd = 0;
/*  309 */     this.na = 12;
/*  310 */     this.p = new Point[this.np];
/*      */     
/*  312 */     this.p[0] = new Point(235, 20);
/*  313 */     this.p[1] = new Point(235, 130);
/*  314 */     this.p[2] = new Point(235, 245);
/*  315 */     this.p[3] = new Point(25, 45);
/*  316 */     this.p[4] = new Point(25, 245);
/*  317 */     this.p[5] = new Point(445, 45);
/*  318 */     this.p[6] = new Point(445, 245);
/*      */     
/*  320 */     this.q = new int[this.np];
/*      */     
/*  322 */     this.q[0] = 11;
/*  323 */     this.q[1] = 3;
/*  324 */     this.q[2] = 12;
/*  325 */     this.q[3] = 2;
/*  326 */     this.q[4] = 3;
/*  327 */     this.q[5] = 1;
/*  328 */     this.q[6] = 4;
/*  329 */     this.arc = new int[this.na][];
/*  330 */     this.ap = new int[this.na][2];
/*  331 */     this.arc[0] = new int[2];
/*  332 */     this.arc[0][0] = 0;
/*  333 */     this.arc[0][1] = 3;
/*  334 */     this.ap[0][0] = 3;
/*  335 */     this.ap[0][1] = 4;
/*      */     
/*  337 */     this.arc[1] = new int[2];
/*  338 */     this.arc[1][0] = 0;
/*  339 */     this.arc[1][1] = 4;
/*  340 */     this.ap[1][0] = 5;
/*  341 */     this.ap[1][1] = 4;
/*      */     
/*  343 */     this.arc[2] = new int[2];
/*  344 */     this.arc[2][0] = 0;
/*  345 */     this.arc[2][1] = 5;
/*  346 */     this.ap[2][0] = 3;
/*  347 */     this.ap[2][1] = 5;
/*      */     
/*  349 */     this.arc[3] = new int[2];
/*  350 */     this.arc[3][0] = 0;
/*  351 */     this.arc[3][1] = 6;
/*  352 */     this.ap[3][0] = 5;
/*  353 */     this.ap[3][1] = 4;
/*      */     
/*  355 */     this.arc[4] = new int[2];
/*  356 */     this.arc[4][0] = 1;
/*  357 */     this.arc[4][1] = 3;
/*  358 */     this.ap[4][0] = 3;
/*  359 */     this.ap[4][1] = 2;
/*      */     
/*  361 */     this.arc[5] = new int[2];
/*  362 */     this.arc[5][0] = 1;
/*  363 */     this.arc[5][1] = 4;
/*  364 */     this.ap[5][0] = 3;
/*  365 */     this.ap[5][1] = 5;
/*      */     
/*  367 */     this.arc[6] = new int[2];
/*  368 */     this.arc[6][0] = 1;
/*  369 */     this.arc[6][1] = 5;
/*  370 */     this.ap[6][0] = 3;
/*  371 */     this.ap[6][1] = 2;
/*      */     
/*  373 */     this.arc[7] = new int[2];
/*  374 */     this.arc[7][0] = 1;
/*  375 */     this.arc[7][1] = 6;
/*  376 */     this.ap[7][0] = 3;
/*  377 */     this.ap[7][1] = 6;
/*      */     
/*  379 */     this.arc[8] = new int[2];
/*  380 */     this.arc[8][0] = 2;
/*  381 */     this.arc[8][1] = 3;
/*  382 */     this.ap[8][0] = 5;
/*  383 */     this.ap[8][1] = 5;
/*      */     
/*  385 */     this.arc[9] = new int[2];
/*  386 */     this.arc[9][0] = 2;
/*  387 */     this.arc[9][1] = 4;
/*  388 */     this.ap[9][0] = 3;
/*  389 */     this.ap[9][1] = 3;
/*      */     
/*  391 */     this.arc[10] = new int[2];
/*  392 */     this.arc[10][0] = 2;
/*  393 */     this.arc[10][1] = 5;
/*  394 */     this.ap[10][0] = 5;
/*  395 */     this.ap[10][1] = 4;
/*      */     
/*  397 */     this.arc[11] = new int[2];
/*  398 */     this.arc[11][0] = 2;
/*  399 */     this.arc[11][1] = 6;
/*  400 */     this.ap[11][0] = 3;
/*  401 */     this.ap[11][1] = 2;
/*      */   }
/*      */   
/*      */   private void initializeProblem6()
/*      */   {
/*  406 */     this.np = 9;
/*  407 */     this.nd = 0;
/*  408 */     this.na = 17;
/*  409 */     this.p = new Point[this.np + this.nd];
/*      */     
/*  411 */     this.p[0] = new Point(232, 30);
/*  412 */     this.p[1] = new Point(232, 89);
/*  413 */     this.p[2] = new Point(232, 166);
/*  414 */     this.p[3] = new Point(232, 229);
/*  415 */     this.p[4] = new Point(35, 30);
/*  416 */     this.p[5] = new Point(430, 30);
/*  417 */     this.p[6] = new Point(30, 132);
/*  418 */     this.p[7] = new Point(430, 229);
/*  419 */     this.p[8] = new Point(35, 229);
/*      */     
/*  421 */     this.q = new int[this.np];
/*  422 */     this.q[0] = 12;
/*  423 */     this.q[1] = 12;
/*  424 */     this.q[2] = 12;
/*  425 */     this.q[3] = 12;
/*  426 */     this.q[4] = 3;
/*  427 */     this.q[5] = 4;
/*  428 */     this.q[6] = 3;
/*  429 */     this.q[7] = 4;
/*  430 */     this.q[8] = 3;
/*  431 */     this.arc = new int[this.na][];
/*  432 */     this.ap = new int[this.na][2];
/*      */     
/*      */ 
/*      */ 
/*  436 */     this.arc[0] = new int[2];
/*  437 */     this.arc[0][0] = 0;
/*  438 */     this.arc[0][1] = 4;
/*  439 */     this.ap[0][0] = 4;
/*  440 */     this.ap[0][1] = 5;
/*      */     
/*  442 */     this.arc[1] = new int[2];
/*  443 */     this.arc[1][0] = 0;
/*  444 */     this.arc[1][1] = 5;
/*  445 */     this.ap[1][0] = 4;
/*  446 */     this.ap[1][1] = 5;
/*      */     
/*  448 */     this.arc[2] = new int[2];
/*  449 */     this.arc[2][0] = 0;
/*  450 */     this.arc[2][1] = 6;
/*  451 */     this.ap[2][0] = 4;
/*  452 */     this.ap[2][1] = 5;
/*      */     
/*      */ 
/*  455 */     this.arc[3] = new int[2];
/*  456 */     this.arc[3][0] = 0;
/*  457 */     this.arc[3][1] = 7;
/*  458 */     this.ap[3][0] = 6;
/*  459 */     this.ap[3][1] = 6;
/*      */     
/*  461 */     this.arc[4] = new int[2];
/*  462 */     this.arc[4][0] = 0;
/*  463 */     this.arc[4][1] = 8;
/*  464 */     this.ap[4][0] = 5;
/*  465 */     this.ap[4][1] = 5;
/*      */     
/*  467 */     this.arc[5] = new int[2];
/*  468 */     this.arc[5][0] = 1;
/*  469 */     this.arc[5][1] = 4;
/*  470 */     this.ap[5][0] = 3;
/*  471 */     this.ap[5][1] = 2;
/*      */     
/*  473 */     this.arc[6] = new int[2];
/*  474 */     this.arc[6][0] = 1;
/*  475 */     this.arc[6][1] = 5;
/*  476 */     this.ap[6][0] = 4;
/*  477 */     this.ap[6][1] = 3;
/*      */     
/*  479 */     this.arc[7] = new int[2];
/*  480 */     this.arc[7][0] = 1;
/*  481 */     this.arc[7][1] = 6;
/*  482 */     this.ap[7][0] = 3;
/*  483 */     this.ap[7][1] = 5;
/*      */     
/*  485 */     this.arc[8] = new int[2];
/*  486 */     this.arc[8][0] = 1;
/*  487 */     this.arc[8][1] = 7;
/*  488 */     this.ap[8][0] = 4;
/*  489 */     this.ap[8][1] = 5;
/*      */     
/*  491 */     this.arc[9] = new int[2];
/*  492 */     this.arc[9][0] = 1;
/*  493 */     this.arc[9][1] = 8;
/*  494 */     this.ap[9][0] = 3;
/*  495 */     this.ap[9][1] = 5;
/*      */     
/*  497 */     this.arc[10] = new int[2];
/*  498 */     this.arc[10][0] = 2;
/*  499 */     this.arc[10][1] = 4;
/*  500 */     this.ap[10][0] = 4;
/*  501 */     this.ap[10][1] = 5;
/*      */     
/*  503 */     this.arc[11] = new int[2];
/*  504 */     this.arc[11][0] = 2;
/*  505 */     this.arc[11][1] = 5;
/*  506 */     this.ap[11][0] = 4;
/*  507 */     this.ap[11][1] = 5;
/*      */     
/*  509 */     this.arc[12] = new int[2];
/*  510 */     this.arc[12][0] = 2;
/*  511 */     this.arc[12][1] = 6;
/*  512 */     this.ap[12][0] = 4;
/*  513 */     this.ap[12][1] = 5;
/*      */     
/*  515 */     this.arc[13] = new int[2];
/*  516 */     this.arc[13][0] = 2;
/*  517 */     this.arc[13][1] = 7;
/*  518 */     this.ap[13][0] = 3;
/*  519 */     this.ap[13][1] = 5;
/*      */     
/*  521 */     this.arc[14] = new int[2];
/*  522 */     this.arc[14][0] = 3;
/*  523 */     this.arc[14][1] = 5;
/*  524 */     this.ap[14][0] = 5;
/*  525 */     this.ap[14][1] = 6;
/*      */     
/*  527 */     this.arc[15] = new int[2];
/*  528 */     this.arc[15][0] = 3;
/*  529 */     this.arc[15][1] = 7;
/*  530 */     this.ap[15][0] = 3;
/*  531 */     this.ap[15][1] = 5;
/*      */     
/*  533 */     this.arc[16] = new int[2];
/*  534 */     this.arc[16][0] = 3;
/*  535 */     this.arc[16][1] = 8;
/*  536 */     this.ap[16][0] = 3;
/*  537 */     this.ap[16][1] = 5;
/*      */   }
/*      */   
/*      */ 
/*      */   private void initializeProblem7()
/*      */   {
/*  543 */     this.np = 5;
/*  544 */     this.nd = 2;
/*  545 */     this.na = 6;
/*  546 */     this.p = new Point[this.np + this.nd];
/*      */     
/*  548 */     this.p[0] = new Point(90, 100);
/*  549 */     this.p[1] = new Point(90, 200);
/*  550 */     this.p[2] = new Point(330, 60);
/*  551 */     this.p[3] = new Point(330, 150);
/*  552 */     this.p[4] = new Point(330, 230);
/*  553 */     this.p[5] = new Point(90, 60);
/*  554 */     this.p[6] = new Point(90, 230);
/*      */     
/*  556 */     this.q = new int[this.np];
/*  557 */     this.q[0] = 3;
/*  558 */     this.q[1] = 3;
/*  559 */     this.q[2] = 4;
/*  560 */     this.q[3] = 4;
/*  561 */     this.q[4] = 4;
/*      */     
/*  563 */     this.arc = new int[this.na][];
/*  564 */     this.ap = new int[this.na][2];
/*      */     
/*  566 */     this.arc[0] = new int[4];
/*  567 */     this.arc[0][0] = 0;
/*  568 */     this.arc[0][1] = 5;
/*  569 */     this.arc[0][2] = 5;
/*  570 */     this.arc[0][3] = 2;
/*  571 */     this.arc[0][1] = 1;
/*  572 */     this.ap[0][0] = 3;
/*  573 */     this.ap[0][1] = 4;
/*      */     
/*  575 */     this.arc[1] = new int[2];
/*  576 */     this.arc[1][0] = 0;
/*  577 */     this.arc[1][1] = 3;
/*  578 */     this.ap[1][0] = 4;
/*  579 */     this.ap[1][1] = 5;
/*      */     
/*  581 */     this.arc[2] = new int[2];
/*  582 */     this.arc[2][0] = 0;
/*  583 */     this.arc[2][1] = 4;
/*  584 */     this.ap[2][0] = 5;
/*  585 */     this.ap[2][1] = 4;
/*      */     
/*  587 */     this.arc[3] = new int[2];
/*  588 */     this.arc[3][0] = 1;
/*  589 */     this.arc[3][1] = 2;
/*  590 */     this.ap[3][0] = 4;
/*  591 */     this.ap[3][1] = 5;
/*      */     
/*      */ 
/*  594 */     this.arc[4] = new int[2];
/*  595 */     this.arc[4][0] = 1;
/*  596 */     this.arc[4][1] = 3;
/*  597 */     this.ap[4][0] = 4;
/*  598 */     this.ap[4][1] = 4;
/*      */     
/*  600 */     this.arc[5] = new int[4];
/*  601 */     this.arc[5][0] = 1;
/*  602 */     this.arc[5][1] = 6;
/*  603 */     this.arc[5][2] = 6;
/*  604 */     this.arc[5][3] = 4;
/*  605 */     this.ap[5][0] = 4;
/*  606 */     this.ap[5][1] = 4;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void initializeProblem8()
/*      */   {
/*  613 */     this.np = 5;
/*  614 */     this.nd = 0;
/*  615 */     this.na = 7;
/*      */     
/*  617 */     this.p = new Point[this.np + this.nd];
/*  618 */     this.p[0] = new Point(35, 40);
/*  619 */     this.p[1] = new Point(35, 240);
/*  620 */     this.p[2] = new Point(235, 140);
/*  621 */     this.p[3] = new Point(435, 40);
/*  622 */     this.p[4] = new Point(435, 240);
/*      */     
/*  624 */     this.q = new int[this.np];
/*  625 */     this.q[0] = 3;
/*  626 */     this.q[1] = 3;
/*  627 */     this.q[2] = 4;
/*  628 */     this.q[3] = 4;
/*  629 */     this.q[4] = 4;
/*      */     
/*  631 */     this.arc = new int[this.na][];
/*  632 */     this.ap = new int[this.na][2];
/*      */     
/*      */ 
/*  635 */     this.arc[0] = new int[2];
/*  636 */     this.arc[0][0] = 0;
/*  637 */     this.arc[0][1] = 2;
/*  638 */     this.ap[0][0] = 3;
/*  639 */     this.ap[0][1] = 3;
/*      */     
/*      */ 
/*  642 */     this.arc[1] = new int[2];
/*  643 */     this.arc[1][0] = 0;
/*  644 */     this.arc[1][1] = 3;
/*  645 */     this.ap[1][0] = 6;
/*  646 */     this.ap[1][1] = 4;
/*      */     
/*  648 */     this.arc[2] = new int[2];
/*  649 */     this.arc[2][0] = 1;
/*  650 */     this.arc[2][1] = 0;
/*  651 */     this.ap[2][0] = 3;
/*  652 */     this.ap[2][1] = 3;
/*      */     
/*  654 */     this.arc[3] = new int[2];
/*  655 */     this.arc[3][0] = 1;
/*  656 */     this.arc[3][1] = 2;
/*  657 */     this.ap[3][0] = 3;
/*  658 */     this.ap[3][1] = 3;
/*      */     
/*  660 */     this.arc[4] = new int[2];
/*  661 */     this.arc[4][0] = 1;
/*  662 */     this.arc[4][1] = 4;
/*  663 */     this.ap[4][0] = 5;
/*  664 */     this.ap[4][1] = 4;
/*      */     
/*  666 */     this.arc[5] = new int[2];
/*  667 */     this.arc[5][0] = 2;
/*  668 */     this.arc[5][1] = 3;
/*  669 */     this.ap[5][0] = 4;
/*  670 */     this.ap[5][1] = 4;
/*      */     
/*  672 */     this.arc[6] = new int[2];
/*  673 */     this.arc[6][0] = 2;
/*  674 */     this.arc[6][1] = 4;
/*  675 */     this.ap[6][0] = 3;
/*  676 */     this.ap[6][1] = 3;
/*      */   }
/*      */   
/*      */ 
/*      */   private void initializeProblem2()
/*      */   {
/*  682 */     this.np = 6;
/*  683 */     this.nd = 2;
/*  684 */     this.na = 10;
/*      */     
/*  686 */     this.p = new Point[this.np + this.nd];
/*      */     
/*  688 */     this.p[0] = new Point(25, 120);
/*  689 */     this.p[1] = new Point(155, 50);
/*  690 */     this.p[2] = new Point(155, 190);
/*  691 */     this.p[3] = new Point(285, 50);
/*  692 */     this.p[4] = new Point(285, 190);
/*  693 */     this.p[5] = new Point(415, 120);
/*      */     
/*  695 */     this.p[6] = new Point(25, 250);
/*  696 */     this.p[7] = new Point(415, 250);
/*      */     
/*  698 */     this.q = new int[this.np];
/*  699 */     this.q[0] = 2;
/*  700 */     this.q[1] = 2;
/*  701 */     this.q[2] = 3;
/*  702 */     this.q[3] = 1;
/*  703 */     this.q[4] = 4;
/*  704 */     this.q[5] = 1;
/*      */     
/*  706 */     this.arc = new int[this.na][];
/*  707 */     this.ap = new int[this.na][2];
/*      */     
/*  709 */     this.arc[0] = new int[2];
/*  710 */     this.arc[0][0] = 0;
/*  711 */     this.arc[0][1] = 1;
/*  712 */     this.ap[0][0] = 3;
/*  713 */     this.ap[0][1] = 3;
/*      */     
/*  715 */     this.arc[1] = new int[2];
/*  716 */     this.arc[1][0] = 0;
/*  717 */     this.arc[1][1] = 2;
/*  718 */     this.ap[1][0] = 3;
/*  719 */     this.ap[1][1] = 3;
/*      */     
/*  721 */     this.arc[2] = new int[4];
/*  722 */     this.arc[2][0] = 0;
/*  723 */     this.arc[2][1] = 6;
/*  724 */     this.arc[2][2] = 7;
/*  725 */     this.arc[2][3] = 5;
/*  726 */     this.ap[2][0] = 3;
/*  727 */     this.ap[2][1] = 3;
/*      */     
/*  729 */     this.arc[3] = new int[2];
/*  730 */     this.arc[3][0] = 1;
/*  731 */     this.arc[3][1] = 3;
/*  732 */     this.ap[3][0] = 3;
/*  733 */     this.ap[3][1] = 3;
/*      */     
/*  735 */     this.arc[4] = new int[2];
/*  736 */     this.arc[4][0] = 1;
/*  737 */     this.arc[4][1] = 4;
/*  738 */     this.ap[4][0] = 4;
/*  739 */     this.ap[4][1] = 4;
/*      */     
/*  741 */     this.arc[5] = new int[2];
/*  742 */     this.arc[5][0] = 2;
/*  743 */     this.arc[5][1] = 3;
/*  744 */     this.ap[5][0] = 4;
/*  745 */     this.ap[5][1] = 4;
/*      */     
/*  747 */     this.arc[6] = new int[2];
/*  748 */     this.arc[6][0] = 2;
/*  749 */     this.arc[6][1] = 4;
/*  750 */     this.ap[6][0] = 2;
/*  751 */     this.ap[6][1] = 4;
/*      */     
/*  753 */     this.arc[7] = new int[2];
/*  754 */     this.arc[7][0] = 3;
/*  755 */     this.arc[7][1] = 4;
/*  756 */     this.ap[7][0] = 3;
/*  757 */     this.ap[7][1] = 3;
/*      */     
/*  759 */     this.arc[8] = new int[2];
/*  760 */     this.arc[8][0] = 3;
/*  761 */     this.arc[8][1] = 5;
/*  762 */     this.ap[8][0] = 3;
/*  763 */     this.ap[8][1] = 3;
/*      */     
/*  765 */     this.arc[9] = new int[2];
/*  766 */     this.arc[9][0] = 4;
/*  767 */     this.arc[9][1] = 5;
/*  768 */     this.ap[9][0] = 3;
/*  769 */     this.ap[9][1] = 3;
/*      */   }
/*      */   
/*      */ 
/*      */   private void setCanvasRatio(double x, double y)
/*      */   {
/*  775 */     this.rx = x;
/*  776 */     this.ry = y;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void draw(Graphics g)
/*      */   {
/*  784 */     Graphics2D g2 = (Graphics2D)g;
/*      */     
/*      */ 
/*  787 */     Color c = g2.getColor();
/*  788 */     Stroke st = g2.getStroke();
/*      */     
/*      */ 
/*  791 */     int sx0 = 1;int sy0 = 1;int sx1 = 1;int sy1 = 1;
/*      */     
/*      */ 
/*  794 */     float[] dash = { 5.0F };
/*  795 */     Stroke s = new BasicStroke(1.0F, 0, 1, 0.0F, dash, 0.0F);
/*      */     
/*      */ 
/*  798 */     int[] x = new int[4];
/*  799 */     int[] y = new int[4];
/*  800 */     Point p0 = new Point(0, 0);
/*  801 */     Point p1 = new Point(0, 0);
/*      */     
/*      */ 
/*  804 */     int x0 = -1;int y0 = -1;
/*      */     
/*      */ 
/*  807 */     for (int i = 0; i < this.na; i++)
/*      */     {
/*  809 */       g2.setColor(Color.black);
/*  810 */       int n = this.arc[i].length;
/*      */       
/*      */ 
/*  813 */       int id = getArc(i);
/*      */       
/*      */ 
/*      */ 
/*  817 */       if (!this.data.arcs[id].is_basic) {
/*  818 */         g2.setStroke(s);
/*      */       } else {
/*  820 */         g2.setStroke(st);
/*      */       }
/*      */       
/*  823 */       for (int j = 0; j < n - 1; j++) {
/*  824 */         sx0 = (int)((this.p[this.arc[i][j]].x + 16) * this.rx);
/*  825 */         sy0 = (int)((this.p[this.arc[i][j]].y + 16) * this.ry);
/*  826 */         sx1 = (int)((this.p[this.arc[i][(j + 1)]].x + 16) * this.rx);
/*  827 */         sy1 = (int)((this.p[this.arc[i][(j + 1)]].y + 16) * this.ry);
/*  828 */         g2.drawLine(sx0, sy0, sx1, sy1);
/*      */       }
/*      */       
/*      */ 
/*  832 */       if (!this.data.arcs[id].is_reversed) {
/*  833 */         p0.x = (this.p[this.arc[i][(n - 2)]].x + 16);
/*  834 */         p0.y = (this.p[this.arc[i][(n - 2)]].y + 16);
/*      */         
/*  836 */         p1.x = ((this.p[this.arc[i][(n - 2)]].x + (this.ap[i][0] - 1) * this.p[this.arc[i][(n - 1)]].x) / this.ap[i][0] + 16);
/*  837 */         p1.y = ((this.p[this.arc[i][(n - 2)]].y + (this.ap[i][0] - 1) * this.p[this.arc[i][(n - 1)]].y) / this.ap[i][0] + 16);
/*      */         
/*  839 */         getArrow(x, y, p0, p1);
/*      */         
/*  841 */         x0 = ((this.ap[i][1] - 1) * this.p[this.arc[i][(n - 2)]].x + this.p[this.arc[i][(n - 1)]].x) / this.ap[i][1] + 16;
/*  842 */         y0 = ((this.ap[i][1] - 1) * this.p[this.arc[i][(n - 2)]].y + this.p[this.arc[i][(n - 1)]].y) / this.ap[i][1] + 16;
/*      */       }
/*      */       else {
/*  845 */         p0.x = (this.p[this.arc[i][(n - 1)]].x + 16);
/*  846 */         p0.y = (this.p[this.arc[i][(n - 1)]].y + 16);
/*      */         
/*  848 */         p1.x = (((this.ap[i][1] - 1) * this.p[this.arc[i][(n - 2)]].x + this.p[this.arc[i][(n - 1)]].x) / this.ap[i][1] + 16);
/*  849 */         p1.y = (((this.ap[i][1] - 1) * this.p[this.arc[i][(n - 2)]].y + this.p[this.arc[i][(n - 1)]].y) / this.ap[i][1] + 16);
/*      */         
/*  851 */         getArrow(x, y, p0, p1);
/*      */         
/*  853 */         x0 = (this.p[this.arc[i][(n - 2)]].x + (this.ap[i][0] - 1) * this.p[this.arc[i][(n - 1)]].x) / this.ap[i][0] + 16;
/*  854 */         y0 = (this.p[this.arc[i][(n - 2)]].y + (this.ap[i][0] - 1) * this.p[this.arc[i][(n - 1)]].y) / this.ap[i][0] + 16;
/*      */       }
/*      */       
/*      */ 
/*  858 */       for (int k = 0; k < 4; k++) {
/*  859 */         int tmp1038_1036 = k; int[] tmp1038_1034 = x;tmp1038_1034[tmp1038_1036] = ((int)(tmp1038_1034[tmp1038_1036] * this.rx)); int 
/*  860 */           tmp1052_1050 = k; int[] tmp1052_1048 = y;tmp1052_1048[tmp1052_1050] = ((int)(tmp1052_1048[tmp1052_1050] * this.ry));
/*      */       }
/*  862 */       Polygon pg = new Polygon(x, y, 4);
/*  863 */       g2.fillPolygon(pg);
/*      */       
/*  865 */       g2.setColor(Color.blue);
/*      */       
/*      */ 
/*      */ 
/*  869 */       x0 = (int)(x0 * this.rx);
/*  870 */       y0 = (int)(y0 * this.ry);
/*  871 */       if ((IORNACanvas.access$2(this.this$0)) && (!IORNACanvas.access$3(this.this$0)))
/*      */       {
/*  873 */         String sFlow = IORActionPanel.trim(getArcFlow(i));
/*  874 */         int j = 0;
/*  875 */         boolean b = false;
/*  876 */         if (IORNACanvas.access$4(this.this$0)) {
/*  877 */           while (this.data.cycle_arcs[j][0] > 0) {
/*  878 */             if (this.data.arcs[id].isEqualTo(this.data.cycle_arcs[j][0], this.data.cycle_arcs[j][1])) {
/*  879 */               sFlow = String.valueOf(String.valueOf(new StringBuffer("(").append(sFlow).append(this.data.arcs[id].is_reversed ? String.valueOf(String.valueOf(new StringBuffer("-").append(IORNACanvas.access$5(this.this$0)).append(")"))) : String.valueOf(String.valueOf(new StringBuffer("+").append(IORNACanvas.access$5(this.this$0)).append(")"))))));
/*  880 */               g2.setColor(Color.red);
/*      */               
/*  882 */               b = true;
/*  883 */               break;
/*      */             }
/*  885 */             if (this.data.arcs[id].isEqualTo(this.data.cycle_arcs[j][1], this.data.cycle_arcs[j][0])) {
/*  886 */               sFlow = String.valueOf(String.valueOf(new StringBuffer("(").append(sFlow).append(this.data.arcs[id].is_reversed ? String.valueOf(String.valueOf(new StringBuffer("+").append(IORNACanvas.access$5(this.this$0)).append(")"))) : String.valueOf(String.valueOf(new StringBuffer("-").append(IORNACanvas.access$5(this.this$0)).append(")"))))));
/*  887 */               g2.setColor(Color.red);
/*      */               
/*  889 */               b = true;
/*  890 */               break;
/*      */             }
/*  892 */             j++;
/*      */           }
/*      */         }
/*  895 */         if (!b) {
/*  896 */           sFlow = String.valueOf(String.valueOf(new StringBuffer("(").append(sFlow).append(")")));
/*      */         }
/*  898 */         g2.drawString(sFlow, x0, y0);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  904 */       g2.setColor(Color.blue);
/*      */       
/*  906 */       if (IORNACanvas.access$3(this.this$0)) {
/*  907 */         String sCost = IORActionPanel.trim(getArcCost(i));
/*      */         
/*      */ 
/*  910 */         if (IORNACanvas.access$4(this.this$0)) {
/*  911 */           int j = 0;
/*  912 */           while (this.data.cycle_arcs[j][0] > 0) {
/*  913 */             if (this.data.arcs[id].isEqualTo(this.data.cycle_arcs[j][0], this.data.cycle_arcs[j][1])) {
/*  914 */               sCost = String.valueOf(String.valueOf(sCost)).concat(String.valueOf(String.valueOf(this.data.arcs[id].is_reversed ? String.valueOf(String.valueOf(new StringBuffer("(-").append(IORNACanvas.access$5(this.this$0)).append(")"))) : String.valueOf(String.valueOf(new StringBuffer("(+").append(IORNACanvas.access$5(this.this$0)).append(")"))))));
/*  915 */               g2.setColor(Color.red);
/*      */               
/*  917 */               break;
/*      */             }
/*  919 */             if (this.data.arcs[id].isEqualTo(this.data.cycle_arcs[j][1], this.data.cycle_arcs[j][0])) {
/*  920 */               sCost = String.valueOf(String.valueOf(sCost)).concat(String.valueOf(String.valueOf(this.data.arcs[id].is_reversed ? String.valueOf(String.valueOf(new StringBuffer("(+").append(IORNACanvas.access$5(this.this$0)).append(")"))) : String.valueOf(String.valueOf(new StringBuffer("(-").append(IORNACanvas.access$5(this.this$0)).append(")"))))));
/*  921 */               g2.setColor(Color.red);
/*      */               
/*  923 */               break;
/*      */             }
/*  925 */             j++;
/*      */           }
/*      */         }
/*  928 */         g2.drawString(sCost, x0, y0);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  935 */     g2.setColor(Color.blue);
/*  936 */     g2.setStroke(st);
/*      */     
/*      */ 
/*  939 */     for (int i = 0; i < this.np; i++) {
/*  940 */       g2.setColor(this.this$0.getBackground());
/*  941 */       sx0 = (int)((this.p[i].x + 16) * this.rx) - 16;
/*  942 */       sy0 = (int)((this.p[i].y + 16) * this.ry) - 16;
/*  943 */       g2.fillOval(sx0 - 2, sy0 - 2, 36, 36);
/*  944 */       g2.setColor(Color.yellow);
/*  945 */       g2.fillOval(sx0, sy0, 32, 32);
/*      */     }
/*      */     
/*  948 */     g2.setColor(Color.black);
/*      */     
/*      */ 
/*  951 */     for (int i = 0; i < this.np; i++) {
/*  952 */       p0.x = ((int)(this.p[i].x * this.rx));
/*  953 */       p0.y = ((int)(this.p[i].y * this.ry));
/*      */       
/*  955 */       switch (this.q[i]) {
/*      */       case 1: 
/*  957 */         p0.x += (int)(18 * this.rx);
/*  958 */         p0.y -= (int)(2 * this.ry);
/*  959 */         break;
/*      */       case 2: 
/*  961 */         p0.x -= (int)(2 * this.rx);
/*  962 */         p0.y -= (int)(2 * this.ry);
/*  963 */         break;
/*      */       case 4: 
/*  965 */         p0.x += (int)(18 * this.rx);
/*  966 */         p0.y += (int)(40 * this.ry);
/*  967 */         break;
/*      */       case 3: 
/*  969 */         p0.x -= (int)(2 * this.rx);
/*  970 */         p0.y += (int)(40 * this.ry);
/*  971 */         break;
/*      */       case 11: 
/*  973 */         p0.x += (int)(10 * this.rx);
/*  974 */         break;
/*      */       case 12: 
/*  976 */         p0.x += (int)(10 * this.rx);
/*  977 */         p0.y += (int)(40 * this.ry);
/*  978 */         break;
/*      */       case 5: case 6: case 7: case 8: case 9: case 10: default: 
/*  980 */         System.err.println("ERROR IN CANVAS DRAW!");
/*      */       }
/*  982 */       sx0 = (int)((this.p[i].x + 16) * this.rx) - 16;
/*  983 */       sy0 = (int)((this.p[i].y + 16) * this.ry) - 16;
/*  984 */       sx0 += 12;
/*  985 */       sy0 += 20;
/*      */       
/*  987 */       g2.drawString(getNodeName(i + 1), sx0, sy0);
/*  988 */       g2.drawString(String.valueOf(String.valueOf(new StringBuffer("[").append(IORActionPanel.trim(this.data.node_flow[i])).append("]"))), p0.x, p0.y);
/*      */     }
/*      */     
/*      */ 
/*  992 */     g2.setColor(c);
/*      */   }
/*      */   
/*      */   private double getArcFlow(int arc_id)
/*      */   {
/*  997 */     int id = -1;
/*  998 */     for (int i = 0; i < this.data.num_arcs; i++) {
/*  999 */       int n = this.arc[arc_id].length;
/* 1000 */       if (this.data.arcs[i].isEqualTo(this.arc[arc_id][0] + 1, this.arc[arc_id][(n - 1)] + 1)) {
/* 1001 */         id = i;
/* 1002 */         break;
/*      */       }
/*      */     }
/* 1005 */     return this.data.arcs[id].flow;
/*      */   }
/*      */   
/*      */   private double getArcCost(int arc_id)
/*      */   {
/* 1010 */     int id = -1;
/* 1011 */     for (int i = 0; i < this.data.num_arcs; i++) {
/* 1012 */       int n = this.arc[arc_id].length;
/* 1013 */       if (this.data.arcs[i].isEqualTo(this.arc[arc_id][0] + 1, this.arc[arc_id][(n - 1)] + 1)) {
/* 1014 */         id = i;
/* 1015 */         break;
/*      */       }
/*      */     }
/* 1018 */     return this.data.arcs[id].cost;
/*      */   }
/*      */   
/*      */   private int getArc(int arc_id)
/*      */   {
/* 1023 */     int id = -1;
/* 1024 */     for (int i = 0; i < this.data.num_arcs; i++) {
/* 1025 */       int n = this.arc[arc_id].length;
/* 1026 */       if (this.data.arcs[i].isEqualTo(this.arc[arc_id][0] + 1, this.arc[arc_id][(n - 1)] + 1)) {
/* 1027 */         id = i;
/* 1028 */         break;
/*      */       }
/*      */     }
/* 1031 */     return id;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void getArrow(int[] x, int[] y, Point start, Point end)
/*      */   {
/* 1038 */     double x0 = end.x - start.x;
/* 1039 */     double y0 = end.y - start.y;
/* 1040 */     double z0 = Math.sqrt(x0 * x0 + y0 * y0);
/* 1041 */     double pi = 3.141592653589793D;
/*      */     
/* 1043 */     double theta = -1.0D;
/* 1044 */     if ((x0 == 0) || (y0 == 0)) {
/* 1045 */       if ((x0 == 0) && (y0 >= 0)) {
/* 1046 */         theta = pi / 2;
/*      */       }
/* 1048 */       if ((x0 == 0) && (y0 <= 0)) {
/* 1049 */         theta = 3 * pi / 2;
/*      */       }
/* 1051 */       if ((y0 == 0) && (x0 >= 0)) {
/* 1052 */         theta = 0.0D;
/*      */       }
/* 1054 */       if ((y0 == 0) && (x0 <= 0)) {
/* 1055 */         theta = pi;
/*      */       }
/*      */     }
/*      */     else {
/* 1059 */       double cos = x0 / z0;
/* 1060 */       double sin = y0 / z0;
/* 1061 */       if ((sin > 0) && (cos > 0)) {
/* 1062 */         theta = Math.asin(sin);
/*      */       }
/* 1064 */       if ((sin > 0) && (cos < 0)) {
/* 1065 */         theta = Math.acos(cos);
/*      */       }
/* 1067 */       if ((sin < 0) && (cos < 0)) {
/* 1068 */         theta = pi + Math.acos(-cos);
/*      */       }
/* 1070 */       if ((sin < 0) && (cos > 0)) {
/* 1071 */         theta = 2 * pi - Math.acos(cos);
/*      */       }
/*      */     }
/*      */     
/* 1075 */     x[2] = ((int)x0);
/* 1076 */     y[2] = ((int)y0);
/*      */     
/* 1078 */     double z = z0 - 4;
/* 1079 */     x[0] = ((int)(z * Math.cos(theta)));
/* 1080 */     y[0] = ((int)(z * Math.sin(theta)));
/*      */     
/* 1082 */     z = z0 - 6;
/* 1083 */     double alpha = Math.atan(6 / z);
/* 1084 */     x[1] = ((int)(z * Math.cos(theta + alpha)));
/* 1085 */     y[1] = ((int)(z * Math.sin(theta + alpha)));
/* 1086 */     x[3] = ((int)(z * Math.cos(theta - alpha)));
/* 1087 */     y[3] = ((int)(z * Math.sin(theta - alpha)));
/*      */     
/* 1089 */     for (int i = 0; i < 4; i++) {
/* 1090 */       x[i] += start.x;
/* 1091 */       y[i] += start.y;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public String getArcName(int begin, int end)
/*      */   {
/* 1098 */     String name = new String("");
/*      */     
/*      */ 
/* 1101 */     if ((begin < 0) || (end < 0) || (begin > 25) || (end > 25)) {
/* 1102 */       return null;
/*      */     }
/* 1104 */     char first = (char)(65 + begin - 1);
/* 1105 */     char second = (char)(65 + end - 1);
/*      */     
/* 1107 */     name = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(name))).append(first).append(second)));
/* 1108 */     return name;
/*      */   }
/*      */   
/*      */ 
/*      */   private String getNodeName(int id)
/*      */   {
/* 1114 */     if ((id <= 0) || (id >= 25)) {
/* 1115 */       return null;
/*      */     }
/* 1117 */     String name = new String("");
/* 1118 */     char c = (char)(65 + id - 1);
/* 1119 */     name = String.valueOf(String.valueOf(name)).concat(String.valueOf(String.valueOf(c)));
/* 1120 */     return name;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IORNACanvas$NetList.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */