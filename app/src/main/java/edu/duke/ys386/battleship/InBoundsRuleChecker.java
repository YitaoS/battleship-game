package edu.duke.ys386.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    for (Coordinate c : theShip.getCoordinates()) {
      if (!theBoard.checkIfWithinBorder(c) ){
        return false;
      }
    }
    return true;
  }

}
