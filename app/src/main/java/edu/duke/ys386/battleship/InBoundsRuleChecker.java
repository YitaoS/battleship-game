package edu.duke.ys386.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    for (Coordinate c : theShip.getCoordinates()) {
      String check = theBoard.checkIfWithinBorder(c);
      if (check != ""){
        return check;
      }
    }
    return "";
  }

}
