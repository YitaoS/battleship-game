package edu.duke.ys386.battleship;

public interface Board<T> {
  /**
   * width of the boardg
   * 
   * @return the width of the board
   */
  public int getWidth();

  /**
   * heighth of the board
   * 
   * @return the height of the board
   */
  public int getHeight();

  /**
   * try to add a ship in the board
   * 
   * @param toAdd the ship to add
   * @return true if success. Otherwise, false
   */
  public String tryAddShip(Ship<T> toAdd);

  /**
   * look at what is in a position of the board
   * 
   * @param where the position
   * @return the ship in the position. Otherwise, null
   */
  public T whatIsAtForSelf(Coordinate where);

  public T whatIsAtForEnemy(Coordinate where);

  /**
   * check if a position is out of the board
   * 
   * @param where the coordinate of a position
   * @return Null if with in border. Otherwise, out of border.
   */
  public String checkIfWithinBorder(Coordinate where);

  public Ship<T> fireAt(Coordinate c);

}
