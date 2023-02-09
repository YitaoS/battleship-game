package edu.duke.ys386.battleship;

/**
 * This is a class represents an place operation
 */
public class Placement {
  /**
   * where is the position of the board to place
   */
  private final Coordinate where;

  public Coordinate getWhere() {
    return where;
  }

  /**
   * the orientation to place the ship
   */
  private final char orientation;

  public char getOrientation() {
    return orientation;
  }

  /**
   * create a new placement operation
   * 
   * @param w the position to place
   * @param o the orientation to place the ship
   */
  public Placement(Coordinate w, char o) {
    if (o >= 'a' && o <= 'z') {
      o = (char) (o + 'A' - 'a');
    }
    if (o != 'U' && o != 'D' && o != 'L' && o!= 'R' && o!= 'V' && o != 'H') {
      throw new IllegalArgumentException(
          "Orientation should be U,L,R,D or V,H  ,but is :" + o + " !");
    }
    where = w;
    orientation = o;
  }

  /**
   * create a new placement
   * 
   * @param descr a string describe where and how to place the ship
   */
  public Placement(String descr) {
    if (descr == null || descr.length() != 3) {
      throw new IllegalArgumentException(
          " takes a 3 length string as input: an uppercharacter and a number indicating the coordinate, and another character indicating the orientation, but your input is:"
              + descr);
    }
    char o = descr.charAt(2);
    if (o >= 'a' && o <= 'z') {
      o = (char) (o + 'A' - 'a');
    }
    if (o != 'U' && o != 'D' && o != 'L' && o!= 'R' && o!= 'V' && o != 'H') {
      throw new IllegalArgumentException(
          "Orientation should be U,L,R,D or V,H ,but is :" + o + " !");
    }
    orientation = o;
    String coorString = descr.substring(0, 2);
    where = new Coordinate(coorString);
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Placement p = (Placement) o;
      return p.where.equals(where) && p.orientation == orientation;
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("(");
    sb.append(where.toString());
    sb.append(", ");
    sb.append(orientation);
    sb.append(')');
    return sb.toString();
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
