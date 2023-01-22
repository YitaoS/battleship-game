package edu.duke.ys386.battleship;

/**
 * Constructs a BattleShipBoard with the sp ecified width
 * and height
 * 
 * @param width  is the width of the newly constructed bo
 * @param heigh tis the height of the newly constructed b
 * @throws IllegalArgumentException if the width or height are less than or
 *                                  equal to zero.
 */

public class BattleShipBoard implements Board {
  private final int width;

  public int getWidth() {
    return width;
  }

  private final int height;

  public int getHeight() {
    return height;
  }

  public BattleShipBoard(int width, int height) {
    if (width <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + width);
    }
    if (height <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + height);
    }

    this.width = width;
    this.height = height;
  }
}
