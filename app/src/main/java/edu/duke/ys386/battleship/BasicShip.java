package edu.duke.ys386.battleship;

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T> {
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;

  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo) {
    myPieces = new HashMap<>();
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
  }

  protected void checkCoordinateInThisShip(Coordinate c) {
    if (myPieces.containsKey(c)) {
      return;
    }
    throw new IllegalArgumentException();
  }

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    // TODO Auto-generated method stub
    return myPieces.get(where) != null;
  }

  @Override
  public boolean isSunk() {
    // TODO Auto-generated method stub
    for (Coordinate c : myPieces.keySet()) {
      if (!wasHitAt(c)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    checkCoordinateInThisShip(where);
    myPieces.put(where, true);
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  @Override
  public T getDisplayInfoAt(Coordinate where) {
    // TODO Auto-generated method stub
    return myDisplayInfo.getInfo(where, wasHitAt(where));
  }

}
