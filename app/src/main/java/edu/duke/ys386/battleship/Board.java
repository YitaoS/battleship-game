
package edu.duke.ys386.battleship;

import java.util.HashMap;

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
   * look at what is in a position of the board from self view
   * 
   * @param where the position
   * @return the ship in the position. Otherwise, null
   */
  public T whatIsAtForSelf(Coordinate where);

  /**
   * look at what is in a position of the board from enemy view
   * 
   * @param where the position
   * @return the ship in the position. Otherwise, null
   */

  public T whatIsAtForEnemy(Coordinate where);

  /**
   * check if a position is out of the board form enemy view
   * 
   * @param where the coordinate of a position
   * @return "" if with in border. Otherwise, out of border.
   */
  public String checkIfWithinBorder(Coordinate where);

  
  /**
   * move a ship in the board to position p
   * 
   * @param s the ship to move
   * @param p the position to move
   * @return "" move successfully. Otherwise, return the reason why can not move
   */
  public String moveShip(Ship<T> s,Placement p);

  /**
   * fire at a coorfinate within the board
   * 
   * @param c the coordinate is shooted
   * @return the ship is shooted,or null if missed
   */
  public Ship<T> fireAt(Coordinate c);
   
  /**
   * Scan a area around c and report the squares took up by all kinds of ship in the area
   * 
   * @param c the central coordinate 
   * @return report about
   */
  public HashMap<String,Integer> sonarScan(Coordinate c);

  /**
   * get the ship occupy position c
   * 
   * @param c
   * @return ship if exsit, Otherwise null
   */
  public Ship<T> getShip(Coordinate c);

  /**
   * check if a ship is sunksunk
   * 
   * @return true if sunk, or not sunk
   */
  public boolean shipAllSunk();
}
