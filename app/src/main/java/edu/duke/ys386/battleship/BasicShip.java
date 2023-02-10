package edu.duke.ys386.battleship;

import java.util.HashMap;
import java.util.Map;


/** This class represent a basic ship */
public abstract class BasicShip<T> implements Ship<T> {
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;
  protected ShipDisplayInfo<T> enemyDisplayInfo;
  protected Character orientation;

  /**
   * create a basicship
   * 
   * @param where the position
   * @param myDisplayInfo the display info for self
   * @param eneDisplayInfo the display info for enemy
   * @param orientation the orientation of the ship
   */
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> eneDisplayInfo,Character orientation) {
    myPieces = new HashMap<>();
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = eneDisplayInfo;
    this.orientation = orientation;
  }
  /**
   * get upperleft position of the ship
   * 
   * @return position
   */
  @Override
  public Coordinate getPosition(){
    int row = Integer.MAX_VALUE;
    int col = Integer.MAX_VALUE;
    for(Coordinate c: myPieces.keySet()){
      row = Math.min(c.getRow(),row);
      col = Math.min(c.getColumn(),col);
    }
    return new Coordinate(row,col);
  }
  /**
   * check if a coordinate is in the ship
   * 
   * @param c the coordinate
   */
  protected void checkCoordinateInThisShip(Coordinate c) throws IllegalArgumentException{
    if (myPieces.containsKey(c)) {
      return;
    }
    throw new IllegalArgumentException("The coordinate is out of ship!");
  }

  /**
   * Check if this ship occupies the given coordinate.
   * 
   * @param where is the Coordinate to check if this Ship occupies
   * @return true if where is inside this ship, false if not.
   */
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return myPieces.get(where) != null;
  }

  /**
   * Check if this ship has been hit in all of its locations meaning it has been
   * sunk.
   * 
   * @return true if this ship has been sunk, false otherwise.
   */
  @Override
  public boolean isSunk() {
    for (Coordinate c : myPieces.keySet()) {
      if (!wasHitAt(c)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Make this ship record that it has been hit at the given coordinate. The
   * specified coordinate must be part of the ship.
   * 
   * @param where specifies the coordinates that were hit.
   * @throws IllegalArgumentException if where is not part of the Ship
   */
  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.put(where, true);
  }

  /**
   * Check if this ship was hit at the specified coordinates. The coordinates must
   * be part of this Ship.
   * 
   * @param where is the coordinates to check.
   * @return true if this ship as hit at the indicated coordinates, and false
   *         otherwise.
   * @throws IllegalArgumentException if the coordinates are not part of this
   *                                  ship.
   */
  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  /**
   * Return the view-specific information at the given coordinate. This coordinate
   * must be part of the ship.
   * 
   * @param where  is the coordinate to return information for
   * @param myShip is the view of the information
   * @throws IllegalArgumentException if where is not part of the Ship
   * @return The view-specific information at that coordinate.
   */
  @Override
  public T getDisplayInfoAt(Coordinate where, boolean myShip) {
    if (myShip) {
      return myDisplayInfo.getInfo(where, wasHitAt(where));
    }
    return enemyDisplayInfo.getInfo(where, wasHitAt(where));
  }

  /**
   * Get all of the Coordinates that this Ship occupies.
   * 
   * @return An Iterable with the coordinates that this Ship occupies
   */
  @Override
  public Iterable<Coordinate> getCoordinates() {
    return myPieces.keySet();
  }

  /**
   * get orientation of a ship
   * 
   * @return orientation
   */
  @Override
  public Character getOrientation(){
    return orientation;
  }

  /**
   * get height of the ship
   * 
   * @return height
   */
  @Override
  public int getHeight(){
    int upOfShip = Integer.MAX_VALUE;
    int downOfShip = Integer.MIN_VALUE;
    for (Coordinate c :myPieces.keySet()) {
      downOfShip = Math.max(downOfShip, c.getRow());
      upOfShip = Math.min(upOfShip, c.getRow());
    }
    return downOfShip - upOfShip + 1;
  }

  /**
   * get width of the ship
   * 
   * @return width
   */
  @Override
  public int getWidth(){
    int rightOfShip = Integer.MIN_VALUE;
    int leftOfShip = Integer.MAX_VALUE;
    for (Coordinate c : myPieces.keySet()) {
      rightOfShip = Math.max(rightOfShip, c.getColumn());
      leftOfShip = Math.min(leftOfShip, c.getColumn());
    }
    return rightOfShip - leftOfShip + 1;
  }

  /**
   * move the ship to a new position
   * 
   * @param c the position to move
   */
  @Override
  public void moveTo(Coordinate c) {
    HashMap<Coordinate,Boolean> newPieces = new HashMap<>();
    Coordinate currentCoordinate = getPosition();
    int rowDiff = c.getRow() - currentCoordinate.getRow();
    int columnDiff = c.getColumn() - currentCoordinate.getColumn();
    for(Map.Entry<Coordinate,Boolean> entry:myPieces.entrySet()){
      newPieces.put(new Coordinate(entry.getKey().getRow()+rowDiff,entry.getKey().getColumn()+columnDiff),entry.getValue());
    }
    myPieces = newPieces;
  }

  /**
   * rotate the ship 90 degree clockwise
   * 
   */
  @Override
  public void rotateOneRightAngleClockwise() {
    Coordinate upperLeft = getPosition();
    moveTo(new Coordinate(0,0));
    int heightOfShip = getHeight();
    HashMap<Coordinate, Boolean> newPieces= new HashMap<>();
    for (Coordinate c : myPieces.keySet()) {
      Coordinate newCoordinate =  new Coordinate(c.getColumn(), heightOfShip - 1 - c.getRow());
      newPieces.put(newCoordinate, myPieces.get(c));
    }
     myPieces = newPieces;
     moveTo(upperLeft);
    orientation = getNextOrientation();
  }

  /**
   * get thr orientation of the ship
   * @return char represents the orientation
   */
  @Override 
  public char getNextOrientation(){
    if(orientation == 'U'){
      return 'R';
    }
    if(orientation == 'R'){
      return 'D';
    }
    if(orientation == 'D'){
      return 'L';
    }
    if(orientation == 'L'){
      return 'U';
    }
    if(orientation == 'V'){
      return 'H';
    }
    if(orientation == 'H'){
      return '1';
    }
    if(orientation == '1'){
      return '2';
    }
    return 'V';
  }

  /**
   * rotate the ship to a specific orientation
   * 
   * @param orientation
   * @throws IllegalArgumentException throws if the orientation is illegal
   */
  @Override
  public void rotateShip(char orientation) throws IllegalArgumentException{
    char originOrientation = this.orientation;
    for(int i =0; i < 4; i++){
      if(this.orientation == orientation){
        return;
      }
      rotateOneRightAngleClockwise();
    }
    throw new IllegalArgumentException("The new orientation:" + orientation+" is not the same type as" + "original orientation :"+ originOrientation);
  }
}
