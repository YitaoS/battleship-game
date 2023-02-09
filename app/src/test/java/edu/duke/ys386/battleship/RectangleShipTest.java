package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_make_coords() {
    Set<Coordinate> set = RectangleShip.makeCoords(new Coordinate(3, 4), 7, 5);
    for (int i = 3; i < 8; i++) {
      for (int j = 4; j < 11; j++) {
        assertTrue(set.contains(new Coordinate(i, j)));
      }
    }
    assertEquals(set.size(), 35);
  }

  @Test
  public void test_constructor() {
    RectangleShip<Character> rts = new RectangleShip<Character>("RtShip", new Coordinate(2, 1), 5, 3, 's', '*','v');

    for (int i = 2; i < 5; i++) {
      for (int j = 1; j < 6; j++) {
        assertTrue(rts.occupiesCoordinates(new Coordinate(i, j)));
      }
    }
  }

}
