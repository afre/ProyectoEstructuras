package org.coinor.opents;

import java.io.Serializable;

public abstract interface ObjectiveFunction
  extends Serializable
{
  public abstract double[] evaluate(Solution paramSolution, Move paramMove);
}


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\ObjectiveFunction.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */