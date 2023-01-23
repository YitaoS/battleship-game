package edu.duke.ys386.battleship;

public class Placement {
  private final Coordinate where;

  public Coordinate getWhere() {
    return where;
  }

  private final char orientation;

  public char getOrientation() {
    return orientation;
  }

  public Placement(Coordinate w, char o) {
    if (o >= 'a' && o <= 'z') {
      o = (char) (o + 'A' - 'a');
    }
    if (o != 'V' && o != 'H') {
      throw new IllegalArgumentException("Orientation should be vertical or horizonal(v or V)");
    }
    where = w;
    orientation = o;
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
