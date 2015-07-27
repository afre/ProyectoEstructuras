package org.coinor.opents;

import java.io.Serializable;
import java.util.EventListener;

public abstract interface TabuSearchListener
  extends Serializable, EventListener
{
  public abstract void tabuSearchStarted(TabuSearchEvent paramTabuSearchEvent);
  
  public abstract void tabuSearchStopped(TabuSearchEvent paramTabuSearchEvent);
  
  public abstract void newBestSolutionFound(TabuSearchEvent paramTabuSearchEvent);
  
  public abstract void newCurrentSolutionFound(TabuSearchEvent paramTabuSearchEvent);
  
  public abstract void unimprovingMoveMade(TabuSearchEvent paramTabuSearchEvent);
  
  public abstract void improvingMoveMade(TabuSearchEvent paramTabuSearchEvent);
  
  public abstract void noChangeInValueMoveMade(TabuSearchEvent paramTabuSearchEvent);
}


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\TabuSearchListener.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */