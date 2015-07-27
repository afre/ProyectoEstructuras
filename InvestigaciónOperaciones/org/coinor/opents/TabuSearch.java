package org.coinor.opents;

import java.io.Serializable;

public abstract interface TabuSearch
  extends Serializable
{
  public abstract void startSolving();
  
  public abstract void stopSolving();
  
  public abstract boolean isSolving();
  
  public abstract void addTabuSearchListener(TabuSearchListener paramTabuSearchListener);
  
  public abstract void removeTabuSearchListener(TabuSearchListener paramTabuSearchListener);
  
  public abstract void setObjectiveFunction(ObjectiveFunction paramObjectiveFunction);
  
  public abstract void setMoveManager(MoveManager paramMoveManager);
  
  public abstract void setTabuList(TabuList paramTabuList);
  
  public abstract void setAspirationCriteria(AspirationCriteria paramAspirationCriteria);
  
  public abstract void setBestSolution(Solution paramSolution);
  
  public abstract void setCurrentSolution(Solution paramSolution);
  
  public abstract void setIterationsToGo(int paramInt);
  
  public abstract void setMaximizing(boolean paramBoolean);
  
  public abstract void setChooseFirstImprovingMove(boolean paramBoolean);
  
  public abstract boolean isChooseFirstImprovingMove();
  
  public abstract ObjectiveFunction getObjectiveFunction();
  
  public abstract MoveManager getMoveManager();
  
  public abstract TabuList getTabuList();
  
  public abstract AspirationCriteria getAspirationCriteria();
  
  public abstract Solution getBestSolution();
  
  public abstract Solution getCurrentSolution();
  
  public abstract int getIterationsToGo();
  
  public abstract int getIterationsCompleted();
  
  public abstract boolean isMaximizing();
}


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\TabuSearch.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */