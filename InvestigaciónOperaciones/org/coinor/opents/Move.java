package org.coinor.opents;

import java.io.Serializable;

public abstract interface Move
  extends Serializable
{
  public abstract void operateOn(Solution paramSolution);
  
  public abstract int[] identifyMove();
}


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\Move.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */