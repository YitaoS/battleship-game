package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class BasicShipTest {
  @Test
  public void test_occupies_coordinates() {
    RectangleShip<Character> bs1 = new RectangleShip<Character>(new Coordinate(0, 3), 's', '*');
    assertDoesNotThrow(() -> bs1.checkCoordinateInThisShip(new Coordinate(0, 3)));
    assertThrows(IllegalArgumentException.class, () -> bs1.checkCoordinateInThisShip(new Coordinate(0, 8)));

  }

  @Test
  public void test_record_hit_at() {
    RectangleShip<Character> rs = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 5, 8, 's', '*');
    rs.recordHitAt(new Coordinate(3, 2));
    assertEquals(rs.wasHitAt(new Coordinate(3, 2)), true);
    assertEquals(rs.wasHitAt(new Coordinate(7, 4)), false);

  }

  @Test
  public void test_is_sunk() {
    RectangleShip<Character> rs = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 2, 1, 's', '*');
    assertEquals(false, rs.isSunk());
    rs.recordHitAt(new Coordinate(0, 2));
    assertEquals(false, rs.isSunk());
    rs.recordHitAt(new Coordinate(0, 3));
    assertEquals(true, rs.isSunk());
  }

  @Test
  public void test_get_display_info_at() {
    RectangleShip<Character> rs = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 2, 1, 's', '*');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(0, 2)), 's');
    rs.recordHitAt(new Coordinate(0, 2));
    assertEquals(rs.getDisplayInfoAt(new Coordinate(0, 2)), '*');

  }

  @Test
  public void test_get_name() {
    RectangleShip<Character> rs = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 2, 1, 's', '*');
    assertEquals(rs.getName(), "RtShip");
  }

}
