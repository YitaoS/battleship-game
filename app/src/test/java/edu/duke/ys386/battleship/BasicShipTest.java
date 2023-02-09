package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class BasicShipTest {
  @Test
  public void test_occupies_coordinates() {
    RectangleShip<Character> bs1 = new RectangleShip<Character>(new Coordinate(0, 3), 's', '*','v');
    assertDoesNotThrow(() -> bs1.checkCoordinateInThisShip(new Coordinate(0, 3)));
    assertThrows(IllegalArgumentException.class, () -> bs1.checkCoordinateInThisShip(new Coordinate(0, 8)));

  }

  @Test
  public void test_record_hit_at() {
    RectangleShip<Character> rs = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 5, 8, 's', '*','v');
    rs.recordHitAt(new Coordinate(3, 2));
    assertEquals(rs.wasHitAt(new Coordinate(3, 2)), true);
    assertEquals(rs.wasHitAt(new Coordinate(7, 4)), false);

  }

  @Test
  public void test_is_sunk() {
    RectangleShip<Character> rs = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 2, 1, 's', '*','v');
    assertEquals(false, rs.isSunk());
    rs.recordHitAt(new Coordinate(0, 2));
    assertEquals(false, rs.isSunk());
    rs.recordHitAt(new Coordinate(0, 3));
    assertEquals(true, rs.isSunk());
  }

  @Test
  public void test_get_position(){
    RectangleShip<Character> rs = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 2, 1, 's', '*','v');
    SpecialShip<Character> ss = new SpecialShip<Character>("Carrier", new Coordinate(5,4), 'U', 'c', '*');
    assertEquals(rs.getPosition(), new Coordinate(0,2));
    assertEquals(ss.getPosition(), new Coordinate(5,4));
  }

  @Test
  public void test_get_display_info_at() {
    RectangleShip<Character> rs = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 2, 1, 's', '*','v');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(0, 2), true), 's');
    rs.recordHitAt(new Coordinate(0, 2));
    assertEquals(rs.getDisplayInfoAt(new Coordinate(0, 2), true), '*');

  }

  @Test
  public void test_move_to() {
    RectangleShip<Character> rs = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 2, 1, 's', '*','v');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(0, 2), true), 's');
    rs.recordHitAt(new Coordinate(0, 2));
    assertEquals(rs.getDisplayInfoAt(new Coordinate(0, 2), true), '*');
    rs.moveTo(new Coordinate(2,3));
    assertEquals(rs.getDisplayInfoAt(new Coordinate(2, 3), true), '*');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(2, 4), true), 's');
  }

  @Test
  public void test_get_name() {
    RectangleShip<Character> rs = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 2, 1, 's', '*','v');
    assertEquals(rs.getName(), "RtShip");
  }

  @Test
  public void test_get_coordinate() {
    RectangleShip<Character> rs = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 2, 1, 's', '*', 'v');
    assertEquals(rs.getName(), "RtShip");
    for (Coordinate s : rs.getCoordinates()) {
      assertDoesNotThrow(() -> rs.checkCoordinateInThisShip(s));
    }
  }

  @Test 
  public void test_rotate_one_right_angle_clockwise(){
    RectangleShip<Character> rs = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 2, 1, 's', '*','v');
    rs.recordHitAt(new Coordinate(0, 2));
    rs.moveTo(new Coordinate(2,3));
    rs.rotateOneRightAngleClockwise();
    assertEquals(rs.getDisplayInfoAt(new Coordinate(2, 3), true), '*');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(3, 3), true), 's');
  }

  @Test
  public void test_get_width(){
    RectangleShip<Character> rs = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 2, 1, 's', '*','V');
    assertEquals(rs.getWidth(), 2);
  }

  @Test
  public void test_rotate_ship(){
    RectangleShip<Character> rs = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 2, 1, 's', '*','V');
    rs.recordHitAt(new Coordinate(0, 3));
    rs.rotateShip('H');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(1, 2), true), '*');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(0, 2), true), 's');
    assertThrows(IllegalArgumentException.class, ()->rs.rotateShip('U'));
    V2ShipFactory v2f = new V2ShipFactory();
    Ship<Character> bs = v2f.makeBattleship(new Placement(new Coordinate(2,3),'u'));
    bs.recordHitAt(new Coordinate(3,5));
    bs.rotateShip('L');
    assertEquals(bs.getDisplayInfoAt(new Coordinate(2, 4), true), '*');
    assertEquals(bs.getDisplayInfoAt(new Coordinate(3, 3), true), 'b');
    assertEquals(bs.getDisplayInfoAt(new Coordinate(3, 4), true), 'b');
    assertEquals(bs.getDisplayInfoAt(new Coordinate(4, 4), true), 'b');
    bs.rotateShip('D');
    assertEquals(bs.getDisplayInfoAt(new Coordinate(2, 3), true), '*');
  }
}
