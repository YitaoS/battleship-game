package edu.duke.ys386.battleship;

import java.util.HashSet;

/**
 * this class is for the ships are in special shape
 * they all need special hashset to decide their ship
 * they have 4 orientations: up, down, left and right
 */
public class SpecialShip<T> extends BasicShip<T> {

  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, char orientation, String name)
      throws IllegalArgumentException {
    if (orientation != 'U' && orientation != 'D' && orientation != 'L' && orientation != 'R') {
      throw new IllegalArgumentException("This ship has special shape, please enter U,R,D or L!");
    }
    HashSet<Coordinate> ans = new HashSet<>();
    HashSet<Coordinate> set = getUpShipCoordsAt0_0(name);
    if (orientation == 'U') {
      for (Coordinate c : set) {
        ans.add(new Coordinate(upperLeft.getRow() + c.getRow(), upperLeft.getColumn() + c.getColumn()));
      }
    }
    int heightOfShip = 0;
    int widthOfShip = 0;
    for (Coordinate c : set) {
      heightOfShip = Math.max(heightOfShip, c.getRow());
      widthOfShip = Math.max(widthOfShip, c.getColumn());
    }
    if (orientation == 'D') {
      for (Coordinate c : set) {
        ans.add(new Coordinate(heightOfShip + upperLeft.getRow() - c.getRow(),
            widthOfShip + upperLeft.getColumn() - c.getColumn()));
      }
    } else if (orientation == 'L') {
      for (Coordinate c : set) {
        ans.add(new Coordinate(widthOfShip + upperLeft.getRow() - c.getColumn(), upperLeft.getColumn() + c.getRow()));
      }
    } else if (orientation == 'R') {
      for (Coordinate c : set) {
        ans.add(new Coordinate(upperLeft.getRow() + c.getColumn(), heightOfShip + upperLeft.getColumn() - c.getRow()));
      }
    }
    return ans;
  }

  static HashSet<Coordinate> getUpShipCoordsAt0_0(String name) {
    if (name == "Battleship")
      return getUpBattleShipCoordsAt0_0();
    return getUpCarrierShipCoordsAt0_0();// name == "Carrier" case
  }

  static HashSet<Coordinate> getUpBattleShipCoordsAt0_0() {
    HashSet<Coordinate> ans = new HashSet<>();
    ans.add(new Coordinate(0, 1));
    ans.add(new Coordinate(1, 0));
    ans.add(new Coordinate(1, 1));
    ans.add(new Coordinate(1, 2));
    return ans;
  }

  static HashSet<Coordinate> getUpCarrierShipCoordsAt0_0() {
    HashSet<Coordinate> ans = new HashSet<>();
    ans.add(new Coordinate(0, 0));
    ans.add(new Coordinate(1, 0));
    ans.add(new Coordinate(2, 0));
    ans.add(new Coordinate(3, 0));
    ans.add(new Coordinate(2, 1));
    ans.add(new Coordinate(3, 1));
    ans.add(new Coordinate(4, 1));
    return ans;
  }

  final String name;

  public SpecialShip(
      String name,
      Coordinate upperLeft,
      char orientation,
      ShipDisplayInfo<T> sdi,
      ShipDisplayInfo<T> edi) throws IllegalArgumentException {
    super(makeCoords(upperLeft, orientation, name), sdi, edi, orientation);
    this.name = name;
  }

  public SpecialShip(String name, Coordinate upperLeft, char orientation, T data, T onHit)
      throws IllegalArgumentException {
    this(name, upperLeft, orientation, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));
  }

  @Override
  public String getName() {
    return name;
  }

}
