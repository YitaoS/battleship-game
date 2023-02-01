package edu.duke.ys386.battleship;

/** the ship's display info display on board */
public interface ShipDisplayInfo<T> {
  /** get the information of specific position on board */
  public T getInfo(Coordinate where, boolean hit);
}
