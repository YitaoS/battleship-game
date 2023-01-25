package edu.duke.ys386.battleship;

/**
 * This is a class that represent a coordinate, indicating 
 * the position in the board. 
 */
public class Coordinate {
  private final int row;
/**
 * row of the coordinate
 * @return row
 */
  public int getRow() {
    return row;
  }
  private final int column;
/**
 * column of the coordinate
 * @return column
 */
  public int getColumn() {
    return column;
  }
/**
 * construct a new coordinate
 * @param r set row
 * @param c set column
 */
  public Coordinate(int r, int c) {
    row = r;
    column = c;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }

  @Override
  public String toString() {
    return "(" + row + ", " + column + ")";
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
/**
 * costruct a new coordinate
 * @param descr text that first char shows row 'A' to 'Z'(Case matters) and second one shows col
 */
  public Coordinate(String descr) {
    String upperDescr = descr.toUpperCase();
    if (descr == null || descr.length() != 2) {
      throw new IllegalArgumentException("Coordinate takes 2 length string as input: an upper character and a number");
    }
    char rowChar = upperDescr.charAt(0);
    if (rowChar < 'A' || rowChar > 'Z') {
      throw new IllegalArgumentException("Coordinate row should between A and Z, please enter a valid letter");
    }
    this.row = rowChar - 'A';
    char colChar = upperDescr.charAt(1);
    if (colChar < '0' || colChar > '9') {
      throw new IllegalArgumentException("Coordinate column should between 0 and 9, please enter a valid letter");
    }
    this.column = colChar - '0';

  }
}
