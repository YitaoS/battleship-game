package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_where_and_orientation() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(2, 5);
    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c2, 'h');
    assertEquals(p1.getWhere(), c1);
    assertEquals(p2.getWhere(), c2);
    assertEquals(p1.getOrientation(), 'V');
    assertEquals(p2.getOrientation(), 'H');
    assertThrows(IllegalArgumentException.class, () -> new Placement(c1, '7'));
    assertThrows(IllegalArgumentException.class, () -> new Placement(c1, 'a'));
  }

  @Test
  public void test_equals() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(1, 3);
    Coordinate c4 = new Coordinate(3, 2);
    Coordinate c5 = new Coordinate(3, 2);

    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c2, 'v');
    Placement p3 = new Placement(c2, 'V');
    Placement p4 = new Placement(c3, 'v');
    Placement p5 = new Placement(c4, 'h');
    Placement p6 = new Placement(c4, 'H');
    Placement p7 = new Placement(c5, 'v');
    assertEquals(p1, p1);
    assertEquals(p1, p2);
    assertEquals(p3, p2);
    assertEquals(p1, p3);
    assertEquals(p5, p6);
    assertNotEquals(p1, p4);
    assertNotEquals(p5, p7);
    assertNotEquals(p6, p7);
    assertNotEquals(p7, c4);
    assertNotEquals(p7, '7');

  }

  @Test
  public void test_to_String() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(2, 5);
    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c2, 'h');
    assertEquals(p1.toString(), "((1, 2), V)");
    assertEquals(p2.toString(), "((2, 5), H)");
  }

  @Test
  public void test_hash_code() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(1, 3);
    Coordinate c4 = new Coordinate(3, 2);

    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c2, 'v');
    Placement p3 = new Placement(c2, 'V');
    Placement p4 = new Placement(c3, 'v');
    Placement p5 = new Placement(c4, 'h');
    Placement p6 = new Placement(c4, 'H');
    assertEquals(p1.hashCode(), p1.hashCode());
    assertEquals(p1.hashCode(), p2.hashCode());
    assertEquals(p3.hashCode(), p2.hashCode());
    assertEquals(p1.hashCode(), p3.hashCode());
    assertEquals(p5.hashCode(), p6.hashCode());
    assertNotEquals(p1.hashCode(), p4.hashCode());

  }
}
