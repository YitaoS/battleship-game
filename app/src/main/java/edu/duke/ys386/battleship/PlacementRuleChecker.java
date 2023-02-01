package edu.duke.ys386.battleship;

public abstract class PlacementRuleChecker<T> {
  /**
   * create a placement rule checker chain
   * 
   * @param next the next placement rule checker
   *
   */
  private final PlacementRuleChecker<T> next;

  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }

  /**
   * check if the ship obey the placement rulerule
   * 0
   * 
   * @param theShip  to place
   * @param theBoard to be placed
   * @return emptystring if obey the rulw,otherwise return the error message
   */
  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);

  public String checkPlacement(Ship<T> theShip, Board<T> theBoard) {
    // if we fail our own rule: stop the placement is not legal
    String s = checkMyRule(theShip, theBoard);
    if (s != "")
      return s;
    // other wise, ask the rest of the chain.
    if (next != null) {
      return next.checkPlacement(theShip, theBoard);
    }
    // if there are no more rules, then the placement is legal
    return "";
  }

}
