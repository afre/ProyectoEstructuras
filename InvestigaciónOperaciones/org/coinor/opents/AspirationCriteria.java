package org.coinor.opents;

import java.io.Serializable;

public abstract interface AspirationCriteria
  extends Serializable
{
  public abstract boolean overrideTabu(Solution paramSolution, Move paramMove, double[] paramArrayOfDouble, TabuSearch paramTabuSearch);
}


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\AspirationCriteria.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */