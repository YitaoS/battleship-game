package edu.duke.ys386.battleship;

import java.util.HashMap;
import java.util.Map;


/** This class represent a basic ship */
public abstract class BasicShip<T> implements Ship<T> {
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;
  protected ShipDisplayInfo<T> enemyDisplayInfo;
  protected Character orientation;

  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> eneDisplayInfo,Character orientation) {
    myPieces = new HashMap<>();
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = eneDisplayInfo;
    this.orientation = orientation;
  }

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

  protected void checkCoordinateInThisShip(Coordinate c) {
    if (myPieces.containsKey(c)) {
      return;
    }
    throw new IllegalArgumentException("The coordinate is out of ship!");
  }

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return myPieces.get(where) != null;
  }

  @Override
  public boolean isSunk() {
    for (Coordinate c : myPieces.keySet()) {
      if (!wasHitAt(c)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.put(where, true);
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  @Override
  public T getDisplayInfoAt(Coordinate where, boolean myShip) {
    if (myShip) {
      return myDisplayInfo.getInfo(where, wasHitAt(where));
    }
    return enemyDisplayInfo.getInfo(where, wasHitAt(where));
  }

  @Override
  public Iterable<Coordinate> getCoordinates() {
    return myPieces.keySet();
  }

  @Override
  public Character getOrientation(){
    return orientation;
  }

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
