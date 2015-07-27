/*     */ package org.coinor.opents;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.beans.BeanDescriptor;
/*     */ import java.beans.EventSetDescriptor;
/*     */ 
/*     */ public class MultiThreadedTabuSearchBeanInfo extends java.beans.SimpleBeanInfo
/*     */ {
/*   9 */   private static BeanDescriptor beanDescriptor = new BeanDescriptor(MultiThreadedTabuSearch.class, null);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  18 */   private static java.beans.PropertyDescriptor[] properties = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  25 */   private static EventSetDescriptor[] eventSets = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  32 */   private static java.beans.MethodDescriptor[] methods = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  38 */   private static Image iconColor16 = null;
/*  39 */   private static Image iconColor32 = null;
/*  40 */   private static Image iconMono16 = null;
/*  41 */   private static Image iconMono32 = null;
/*  42 */   private static String iconNameC16 = "oTSSc16.gif";
/*  43 */   private static String iconNameC32 = "oTSSc32.gif";
/*  44 */   private static String iconNameM16 = "oTSSbw16.gif";
/*  45 */   private static String iconNameM32 = "oTSSbw32.gif";
/*     */   
/*  47 */   private static final int defaultPropertyIndex = -1;
/*  48 */   private static final int defaultEventIndex = -1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BeanDescriptor getBeanDescriptor()
/*     */   {
/*  59 */     return beanDescriptor;
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
/*     */   public java.beans.PropertyDescriptor[] getPropertyDescriptors()
/*     */   {
/*  75 */     return properties;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EventSetDescriptor[] getEventSetDescriptors()
/*     */   {
/*  86 */     return eventSets;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public java.beans.MethodDescriptor[] getMethodDescriptors()
/*     */   {
/*  97 */     return methods;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDefaultPropertyIndex()
/*     */   {
/* 109 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDefaultEventIndex()
/*     */   {
/* 120 */     return -1;
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
/*     */   public Image getIcon(int iconKind)
/*     */   {
/* 145 */     switch (iconKind) {
/*     */     case 1: 
/* 147 */       if (iconNameC16 == null) {
/* 148 */         return null;
/*     */       }
/* 150 */       if (iconColor16 == null)
/* 151 */         iconColor16 = loadImage(iconNameC16);
/* 152 */       return iconColor16;
/*     */     
/*     */     case 2: 
/* 155 */       if (iconNameC32 == null) {
/* 156 */         return null;
/*     */       }
/* 158 */       if (iconColor32 == null)
/* 159 */         iconColor32 = loadImage(iconNameC32);
/* 160 */       return iconColor32;
/*     */     
/*     */     case 3: 
/* 163 */       if (iconNameM16 == null) {
/* 164 */         return null;
/*     */       }
/* 166 */       if (iconMono16 == null)
/* 167 */         iconMono16 = loadImage(iconNameM16);
/* 168 */       return iconMono16;
/*     */     
/*     */     case 4: 
/* 171 */       if (iconNameM32 == null) {
/* 172 */         return null;
/*     */       }
/* 174 */       if (iconMono32 == null)
/* 175 */         iconMono32 = loadImage(iconNameM32);
/* 176 */       return iconMono32;
/*     */     }
/* 178 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\MultiThreadedTabuSearchBeanInfo.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */