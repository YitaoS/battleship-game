package edu.duke.ys386.battleship;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Constructs a BattleShipBoard with the sp ecified width
 * and height
 * 
 * @param width is the width of the newly constructed bo
 * @param heigh tis the height of the newly constructed b
 * @throws IllegalArgumentException if the width or height are less than or
 *                                  equal to zero.
 */

public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  final ArrayList<Ship<T>> myShips = new ArrayList<Ship<T>>();

  public int getWidth() {
    return width;
  }

  private final int height;

  public int getHeight() {
    return height;
  }

  private final PlacementRuleChecker<T> placementChecker;

  public PlacementRuleChecker<T> getPlacementRuleChecker() {
    return placementChecker;
  }

  private final HashSet<Coordinate> enemyMisses;

  final T missInfo;
  // public HashSet<Coordinate> getEnemyMisses(){
  // return enemyMisses;
  // }

  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<>(null)), missInfo);
  }

  public BattleShipBoard(int width, int height, PlacementRuleChecker<T> prc, T missInfo) {
    if (width <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + width);
    }
    if (height <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + height);
    }
    this.width = width;
    this.height = height;
    this.placementChecker = prc;
    this.enemyMisses = new HashSet<>();
    this.missInfo = missInfo;
  }

  /**
   * Try to add ship on board
   * 
   * @param toAdd is the ship to be added
   * @return true if successully add. Otherwise, false
   * 
   */
  public String tryAddShip(Ship<T> toAdd) {
    String s = placementChecker.checkPlacement(toAdd, this);
    if (s != "") {
      return s;
    }
    myShips.add(toAdd);
    return "";
  }

  /**
   * return what is at the coordinate on board
   * 
   * @param where is the coordinate to check
   * @return null if there is no ship in where. Otherwise, return
   *         the information of the ship there
   * 
   */
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    if (!isSelf && enemyMisses.contains(where)) {
      return missInfo;
    }
    return null;
  }

  /**
   * return if a coordinate is within the board
   * 
   * @param where the coordinate to check
   * @return false if it's out of range. Otherwise, true
   * 
   */
  public String checkIfWithinBorder(Coordinate where) {
    String s = "";
    if (where.getRow() < 0) {
      s = "That placement is invalid: the ship goes off the top of the board.\n";
    } else if (where.getRow() >= height) {
      s = "That placement is invalid: the ship goes off the bottom of the board\n.";
    } else if (where.getColumn() < 0) {
      s = "That placement is invalid: the ship goes off the left of the board.\n";
    } else if (where.getColumn() >= width) {
      s = "That placement is invalid: the ship goes off the right of the board.\n";
    }
    return s;
  }

  public Ship<T> fireAt(Coordinate c) {
    for (Ship<T> ship : myShips) {
      if (ship.occupiesCoordinates(c)) {
        ship.recordHitAt(c);
        return ship;
      }
    }
    enemyMisses.add(c);
    return null;
  }

  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }
}
