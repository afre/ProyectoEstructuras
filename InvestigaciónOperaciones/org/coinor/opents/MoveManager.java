package org.coinor.opents;

import java.io.Serializable;

public abstract interface MoveManager
  extends Serializable
{
  public abstract Move[] getAllMoves(Solution paramSolution);
}


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\MoveManager.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */