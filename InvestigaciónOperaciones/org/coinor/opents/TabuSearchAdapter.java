package org.coinor.opents;

public class TabuSearchAdapter
  implements TabuSearchListener
{
  public void tabuSearchStarted(TabuSearchEvent e) {}
  
  public void tabuSearchStopped(TabuSearchEvent e) {}
  
  public void newBestSolutionFound(TabuSearchEvent e) {}
  
  public void newCurrentSolutionFound(TabuSearchEvent e) {}
  
  public void unimprovingMoveMade(TabuSearchEvent e) {}
  
  public void improvingMoveMade(TabuSearchEvent e) {}
  
  public void noChangeInValueMoveMade(TabuSearchEvent e) {}
}


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\org\coinor\opents\TabuSearchAdapter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */