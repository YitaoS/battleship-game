package edu.duke.ys386.battleship;

import java.util.ArrayList;
import java.util.HashMap;
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
  final HashSet<Coordinate> scanArea = new HashSet<>();
  final HashMap<Coordinate,T> whatIsAtForEnemy;

  public int getWidth() {
    return width;
  }

  private final int height;

  /**
   * get height of the board
   * 
   * @return height
   */
  public int getHeight() {
    return height;
  }

  private final PlacementRuleChecker<T> placementChecker;

  /**
   * get placement rule checker of the board
   * 
   * @return placement rule checker
   */
  public PlacementRuleChecker<T> getPlacementRuleChecker() {
    return placementChecker;
  }

  private final HashSet<Coordinate> enemyMisses;

  final T missInfo;
  // public HashSet<Coordinate> getEnemyMisses(){
  // return enemyMisses;
  // }

  /**
   * create a battleship board
   * 
   * @param w width
   * @param h height
   * @param missInfo the information to display when opponent missed somewhere
   */
  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<>(null)), missInfo);
  }

  /**
   * create a battleship board
   * 
   * @param width
   * @param height
   * @param prc the placement rule checker
   * @param missInfo the information to display when opponent missed somewhere
   */
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
    this.whatIsAtForEnemy = new HashMap<>();
    setupScanArea();
  }

  /** set up the area to scan*/
  public void setupScanArea(){
    for(int i = 0; i < 4;i++){
      for(int j = -3 + i; j < 4 - i; j++){
        scanArea.add(new Coordinate(i,j));
      }
    }
    for(int i = -3; i < 0; i++){
      for(int j = -i-3; j < 4+i;j++){
        scanArea.add(new Coordinate(i,j));
      }
    }
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

  /**
   * return what is at the coordinate on board
   * 
   * @param where is the coordinate to check
   * @param isSelf true if display for self Or false
   * @return null if there is no ship in where. Otherwise, return
   *         the information of the ship there
   */
  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    // if (!isSelf && enemyMisses.contains(where)) {
    //   return missInfo;
    // }
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

  /**
   * fire at a coorfinate within the board
   * 
   * @param c the coordinate is shooted
   * @return the ship is shooted,or null if missed
   */

  public Ship<T> fireAt(Coordinate c) {
    for (Ship<T> ship : myShips) {
      if (ship.occupiesCoordinates(c)) {
        ship.recordHitAt(c);
        whatIsAtForEnemy.put(c, ship.getDisplayInfoAt(c, false));
        return ship;
      }
    }
    whatIsAtForEnemy.put(c, missInfo);
    enemyMisses.add(c);
    return null;
  }

  /**
   * Scan a area around c and report the squares took up by all kinds of ship in the area
   * 
   * @param c the central coordinate 
   * @return report about
   */
  @Override
  public HashMap<String,Integer> sonarScan(Coordinate c){
    HashMap<String,Integer> appearTimes= new HashMap<>();
    for(Coordinate scanningSquare: scanArea){
      Ship<T> s = getShip(new Coordinate(scanningSquare.getRow()+c.getRow(),scanningSquare.getColumn()+c.getColumn()));
      if(s != null){
        appearTimes.put(s.getName(), appearTimes.getOrDefault(s.getName(), 0)+1);
      }
    }
    return appearTimes;
  }

  /**
   * look at what is in a position of the board from enemy view
   * 
   * @param where the position
   * @return the ship in the position. Otherwise, null
   */
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAtForEnemy.getOrDefault(where,null);
  }

  /**
   * check if a ship is sunksunk
   * 
   * @return true if sunk, or not sunk
   */
  public boolean shipAllSunk() {
    for (Ship<T> s : myShips) {
      if (!s.isSunk()) {
        return false;
      }
    }
    return true;
  }

  /**
   * get the ship occupy position c
   * 
   * @param c
   * @return ship if exsit, Otherwise null
   */
  @Override
  public Ship<T> getShip(Coordinate c) {
    for (Ship<T> ship : myShips) {
      if (ship.occupiesCoordinates(c)) {
        return ship;
      }
    }
    return null;
  }

  /**
   * move the ship to a new position
   * 
   * @param c the position to move
   */
  @Override
  public String moveShip(Ship<T> toMove, Placement p)throws IllegalArgumentException {
    myShips.remove(toMove);
    Coordinate originPosition = toMove.getPosition();
    char originOrientation = toMove.getOrientation();
    try{toMove.rotateShip(p.getOrientation());}
    catch(Exception e){
      toMove.rotateShip(originOrientation);
      myShips.add(toMove);
      throw e;
    }
    toMove.moveTo(p.getWhere());
    String s = placementChecker.checkPlacement(toMove, this);
    if (s != "") {
      toMove.moveTo(originPosition);
      toMove.rotateShip(originOrientation);
    }
    myShips.add(toMove);
    return s;
  }

}
