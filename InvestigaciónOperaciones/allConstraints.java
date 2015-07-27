/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class allConstraints
/*     */   implements Serializable
/*     */ {
/*  26 */   private static int ADD_INTERSECT = 1;
/*  27 */   private static int DEL_INTERSECT = 2;
/*     */   
/*  29 */   public Vector vConstraints = new Vector();
/*     */   
/*  31 */   public Vector vIntersections = new Vector();
/*     */   
/*  33 */   public Vector vFeasibleIntersections = new Vector();
/*     */   
/*     */   public int num_of_intersections;
/*     */   
/*     */   public int num_of_constraints;
/*     */   
/*  39 */   public String allConstraintsString = null;
/*     */   
/*  41 */   public boolean feasibility = true;
/*     */   
/*     */ 
/*  44 */   public Vector vExtremum = new Vector();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public allConstraints()
/*     */   {
/*  51 */     this.num_of_constraints = 0;
/*  52 */     this.num_of_intersections = 0;
/*  53 */     this.vExtremum.add(new Intersection(1000000.0F, 1000000.0F));
/*  54 */     this.vExtremum.add(new Intersection(-1000000.0F, 1000000.0F));
/*  55 */     this.vExtremum.add(new Intersection(1000000.0F, -1000000.0F));
/*  56 */     this.vExtremum.add(new Intersection(-1000000.0F, -1000000.0F));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean checkFeasibility(float x1, float x2)
/*     */   {
/*  64 */     if (this.num_of_constraints == 0)
/*     */     {
/*     */ 
/*     */ 
/*  68 */       this.feasibility = true;
/*  69 */       return true;
/*     */     }
/*  71 */     Enumeration e = this.vConstraints.elements();
/*  72 */     Constraint tempCons = null;
/*  73 */     while (e.hasMoreElements())
/*     */     {
/*     */ 
/*     */ 
/*  77 */       tempCons = (Constraint)e.nextElement();
/*  78 */       if (!tempCons.feasible(x1, x2))
/*     */       {
/*  80 */         this.feasibility = false;
/*  81 */         return false;
/*     */       }
/*     */     }
/*  84 */     this.feasibility = true;
/*  85 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean checkExtremum(float x1, float x2)
/*     */   {
/*  95 */     Enumeration e = this.vConstraints.elements();
/*  96 */     Constraint tempCons = null;
/*  97 */     while (e.hasMoreElements())
/*     */     {
/*     */ 
/*     */ 
/* 101 */       tempCons = (Constraint)e.nextElement();
/* 102 */       if (!tempCons.feasible(x1, x2))
/*     */       {
/* 104 */         this.feasibility = false;
/* 105 */         return false;
/*     */       }
/*     */     }
/* 108 */     this.feasibility = true;
/* 109 */     return true;
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
/*     */   public boolean checkFeasibility(int x1, int x2)
/*     */   {
/* 122 */     return checkFeasibility(x1, x2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addConstraint(Constraint newCons)
/*     */   {
/* 131 */     float x1coef = newCons.x1_const;
/* 132 */     float x2coef = newCons.x2_const;
/* 133 */     float rightside = newCons.right_const;
/* 134 */     int ineqsym = newCons.inequating;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 140 */     computeIntersections(newCons, ADD_INTERSECT);
/* 141 */     this.vConstraints.addElement(newCons);
/* 142 */     computeFeasibleIntersections();
/* 143 */     updateString();
/* 144 */     this.num_of_constraints += 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeConstraint(int remCons)
/*     */   {
/* 153 */     if (remCons == 0)
/*     */     {
/* 155 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 160 */     this.vConstraints.removeElementAt(remCons - 1);
/* 161 */     updateString();
/* 162 */     this.num_of_constraints -= 1;
/*     */     
/*     */ 
/* 165 */     Enumeration e = this.vConstraints.elements();
/* 166 */     Constraint tempCons = null;
/* 167 */     this.vIntersections.removeAllElements();
/* 168 */     this.num_of_constraints = 0;
/* 169 */     this.num_of_intersections = 0;
/* 170 */     while (e.hasMoreElements())
/*     */     {
/* 172 */       tempCons = (Constraint)e.nextElement();
/* 173 */       computeIntersections(tempCons, DEL_INTERSECT);
/* 174 */       this.num_of_constraints += 1;
/*     */     }
/* 176 */     computeFeasibleIntersections();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateString()
/*     */   {
/* 185 */     Enumeration e = this.vConstraints.elements();
/* 186 */     Constraint temp = null;
/*     */     
/* 188 */     this.allConstraintsString = "";
/* 189 */     for (int i = 1; i <= this.vConstraints.size(); i++)
/*     */     {
/* 191 */       temp = (Constraint)e.nextElement();
/* 192 */       this.allConstraintsString = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.allConstraintsString))).append("(").append(i).append(")").append(temp.consString)));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void computeIntersections(Constraint newConstraint, int type)
/*     */   {
/* 204 */     System.out.println("in compute computeIntersection");
/*     */     
/*     */ 
/*     */ 
/* 208 */     Enumeration e = this.vConstraints.elements();
/* 209 */     Constraint tempCons = null;
/* 210 */     for (int i = 0; i < this.vConstraints.size(); i++)
/*     */     {
/*     */ 
/* 213 */       tempCons = (Constraint)e.nextElement();
/* 214 */       if ((tempCons != newConstraint) || (type != DEL_INTERSECT))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 248 */         if (Math.abs(newConstraint.x1_const) < 0.001D)
/*     */         {
/* 250 */           if (Math.abs(tempCons.x1_const) > 0.001D)
/*     */           {
/* 252 */             float x2 = newConstraint.right_const / newConstraint.x2_const;
/* 253 */             float x1 = (tempCons.right_const - tempCons.x2_const * x2) / tempCons.x1_const;
/*     */             
/* 255 */             Intersection newIntersection = new Intersection(x1, x2);
/* 256 */             this.vIntersections.addElement(newIntersection);
/* 257 */             this.num_of_intersections += 1;
/*     */           }
/*     */           
/*     */ 
/*     */         }
/* 262 */         else if (Math.abs(newConstraint.x2_const) < 0.001D)
/*     */         {
/* 264 */           if (Math.abs(tempCons.x2_const) > 0.001D)
/*     */           {
/* 266 */             float x1 = newConstraint.right_const / newConstraint.x1_const;
/* 267 */             float x2 = (tempCons.right_const - tempCons.x1_const * x1) / tempCons.x2_const;
/*     */             
/* 269 */             Intersection newIntersection = new Intersection(x1, x2);
/* 270 */             this.vIntersections.addElement(newIntersection);
/* 271 */             this.num_of_intersections += 1;
/*     */           }
/*     */           
/*     */         }
/* 275 */         else if (Math.abs(tempCons.x1_const) < 0.001D)
/*     */         {
/* 277 */           float x2 = tempCons.right_const / tempCons.x2_const;
/* 278 */           float x1 = (newConstraint.right_const - newConstraint.x2_const * x2) / newConstraint.x1_const;
/*     */           
/*     */ 
/* 281 */           Intersection newIntersection = new Intersection(x1, x2);
/* 282 */           this.vIntersections.addElement(newIntersection);
/* 283 */           this.num_of_intersections += 1;
/*     */ 
/*     */         }
/* 286 */         else if (Math.abs(tempCons.x2_const) < 0.001D)
/*     */         {
/* 288 */           float x1 = tempCons.right_const / tempCons.x1_const;
/* 289 */           float x2 = (newConstraint.right_const - newConstraint.x1_const * x1) / newConstraint.x2_const;
/*     */           
/* 291 */           Intersection newIntersection = new Intersection(x1, x2);
/* 292 */           this.vIntersections.addElement(newIntersection);
/* 293 */           this.num_of_intersections += 1;
/*     */ 
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/*     */ 
/* 301 */           float const1 = newConstraint.right_const / newConstraint.x2_const;
/* 302 */           float constx = -1 * newConstraint.x1_const / newConstraint.x2_const;
/*     */           
/*     */ 
/* 305 */           float const2 = tempCons.x1_const + tempCons.x2_const * constx;
/* 306 */           if (const2 != 0)
/*     */           {
/*     */ 
/* 309 */             float const3 = tempCons.right_const - tempCons.x2_const * const1;
/*     */             
/* 311 */             float x1 = const3 / const2;
/* 312 */             float x2 = const1 + constx * x1;
/*     */             
/* 314 */             Intersection newIntersection = new Intersection(x1, x2);
/* 315 */             this.vIntersections.addElement(newIntersection);
/* 316 */             this.num_of_intersections += 1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 321 */     if (type != ADD_INTERSECT) {}
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void computeFeasibleIntersections()
/*     */   {
/* 364 */     Enumeration e = this.vIntersections.elements();
/* 365 */     Intersection tempIntersection = null;
/* 366 */     this.vFeasibleIntersections.removeAllElements();
/*     */     
/* 368 */     while (e.hasMoreElements())
/*     */     {
/* 370 */       tempIntersection = (Intersection)e.nextElement();
/*     */       
/*     */ 
/*     */ 
/* 374 */       System.out.println("tempIntersection.x1:".concat(String.valueOf(String.valueOf(tempIntersection.x1))));
/* 375 */       System.out.println("tempIntersection.x2:".concat(String.valueOf(String.valueOf(tempIntersection.x2))));
/*     */       
/* 377 */       if ((Math.abs(tempIntersection.x1) >= 0.001D) || (Math.abs(tempIntersection.x2) >= 0.001D))
/*     */       {
/*     */ 
/*     */ 
/* 381 */         System.out.println("".concat(String.valueOf(String.valueOf(checkFeasibility(tempIntersection.x1, tempIntersection.x2)))));
/*     */         
/* 383 */         if (checkFeasibility(tempIntersection.x1, tempIntersection.x2))
/*     */         {
/* 385 */           this.vFeasibleIntersections.addElement(tempIntersection);
/* 386 */           System.out.println(String.valueOf(String.valueOf(new StringBuffer("(").append(tempIntersection.x1).append(",").append(tempIntersection.x2).append(")"))));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 391 */     Vector vtemp = new Vector();
/* 392 */     for (int i = 0; i < this.vExtremum.size(); i++) {
/* 393 */       Intersection tmpIntersection = (Intersection)this.vExtremum.get(i);
/* 394 */       if (checkExtremum(tmpIntersection.x1, tmpIntersection.x2))
/*     */       {
/* 396 */         vtemp.addElement(tmpIntersection);
/* 397 */         System.out.println(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(i))).append("In checkExtremum:right:(").append(tmpIntersection.x1).append(",").append(tmpIntersection.x2).append(")"))));
/*     */       }
/*     */       else {
/* 400 */         System.out.println(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(i))).append("remove extremum:(").append(tmpIntersection.x1).append(",").append(tmpIntersection.x2).append(")"))));
/*     */       }
/*     */     }
/* 403 */     this.vExtremum = ((Vector)vtemp.clone());
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\allConstraints.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */