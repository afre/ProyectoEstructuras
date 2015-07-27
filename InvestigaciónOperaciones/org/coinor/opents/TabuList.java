package org.coinor.opents;

import java.io.Serializable;

public abstract interface TabuList
  extends Serializable
{
  public abstract void setTabu(Solution paramSolution, Move paramMove);
  
  public abstract boolean isTabu(Solution paramSolution, Move paramMove);
}


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\TabuList.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */