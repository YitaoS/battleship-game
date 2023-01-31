package edu.duke.ys386.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> ans = new HashSet<>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        ans.add(new Coordinate(upperLeft.getRow() + i, upperLeft.getColumn() + j));
      }
    }
    return ans;
  }

  final String name;

  public RectangleShip(
      String name,
      Coordinate upperLeft,
      int width,
      int height,
      ShipDisplayInfo<T> sdi,
      ShipDisplayInfo<T> edi) {
    super(makeCoords(upperLeft, width, height), sdi, edi);
    this.name = name;
  }

  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));
  }

  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testShip", upperLeft, 1, 1, data, onHit);
  }

  @Override
  public String getName() {
    return name;
  }
}
