package org.coinor.opents;

import java.io.Serializable;

public abstract interface Solution
  extends Cloneable, Serializable
{
  public abstract double[] getObjectiveValue();
  
  public abstract void setObjectiveValue(double[] paramArrayOfDouble);
  
  public abstract Object clone();
}


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\Solution.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */