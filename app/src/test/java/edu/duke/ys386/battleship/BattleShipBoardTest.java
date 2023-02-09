package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
  }

  @Test
  public void test_try_add_ship() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');

    RectangleShip<Character> s1 = new RectangleShip<Character>(new Coordinate(1, 2), 's', '*','v');
    RectangleShip<Character> s2 = new RectangleShip<Character>(new Coordinate(3, 4), 's', '*','v');
    RectangleShip<Character> s3 = new RectangleShip<Character>(new Coordinate(0, 0), 's', '*','v');
    RectangleShip<Character> s4 = new RectangleShip<Character>(new Coordinate(-5, 0), 's', '*','v');
    RectangleShip<Character> s5 = new RectangleShip<Character>(new Coordinate(0, -5), 's', '*','v');

    assertEquals("", b1.tryAddShip(s1));
    assertNotEquals("", b1.tryAddShip(s1));
    assertEquals("", b1.tryAddShip(s2));
    assertEquals("", b1.tryAddShip(s3));
    assertNotEquals("", b1.tryAddShip(s4));
    assertNotEquals("", b1.tryAddShip(s5));
  }

  @Test
  void test_what_ls_at() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    RectangleShip<Character> s1 = new RectangleShip<Character>(new Coordinate(1, 2), 's', '*','v');
    RectangleShip<Character> s2 = new RectangleShip<Character>(new Coordinate(3, 4), 's', '*','v');
    RectangleShip<Character> s3 = new RectangleShip<Character>(new Coordinate(0, 0), 's', '*','v');

    Character[][] e1 = new Character[][] { { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
    };
    checkWhatIsAtBoard(b1, e1);
    assertEquals("", b1.tryAddShip(s1));
    assertEquals("", b1.tryAddShip(s2));
    assertEquals("", b1.tryAddShip(s3));
    Character[][] e2 = new Character[][] { { 's', null, null, null, null, null, null, null, null, null },
        { null, null, 's', null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, 's', null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null, null, null },
    };
    checkWhatIsAtBoard(b1, e2);
    assertNotEquals("", b1.checkIfWithinBorder(new Coordinate("Z9")));
    assertEquals(b1.whatIsAtForSelf(new Coordinate(1, 2)), 's');
    assertEquals(b1.whatIsAtForSelf(new Coordinate(3, 4)), 's');
    assertEquals(b1.whatIsAtForSelf(new Coordinate(0, 0)), 's');
    assertEquals(b1.whatIsAtForSelf(new Coordinate(1, 5)), null);
    assertEquals(b1.whatIsAtForEnemy(new Coordinate(1, 2)), null);
    b1.fireAt(new Coordinate(1, 2));
    assertEquals(b1.whatIsAtForEnemy(new Coordinate(1, 2)), 's');
    assertEquals(b1.whatIsAtForEnemy(new Coordinate(5, 5)), null);
    b1.fireAt(new Coordinate(5, 5));
    assertEquals(b1.whatIsAtForEnemy(new Coordinate(5, 5)), 'X');
  }

  private <T> void checkWhatIsAtBoard(Board<T> b, T[][] expected) {
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[0].length; j++) {
        Coordinate where = new Coordinate(i, j);
        assertEquals("", b.checkIfWithinBorder(where));
        assertEquals(b.whatIsAtForSelf(where), expected[i][j]);
      }
    }
  }

  @Test
  public void test_fire_at() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    Ship<Character> s1 = shipFactory.createShip(new Placement("a0h"), 1, 2, 'l', "testShip");
    b1.tryAddShip(s1);
    assertEquals(s1.wasHitAt(new Coordinate("A1")), false);
    assertEquals(s1.isSunk(), false);
    assertSame(s1, b1.fireAt(new Coordinate("A1")));
    assertEquals(null, b1.fireAt(new Coordinate("B3")));
    assertEquals(s1.wasHitAt(new Coordinate("A1")), true);
    assertEquals(s1.wasHitAt(new Coordinate("A0")), false);
    assertEquals(s1.isSunk(), false);
    assertSame(s1, b1.fireAt(new Coordinate("A0")));
    assertEquals(s1.wasHitAt(new Coordinate("A0")), true);
    assertEquals(s1.isSunk(), true);
  }

  @Test 
  public void test_check_all_sunk(){
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    Ship<Character> s1 = shipFactory.createShip(new Placement("a0h"), 1, 2, 'l', "testShip");
    b1.tryAddShip(s1);
    b1.fireAt(new Coordinate("A1"));
    assertFalse(b1.shipAllSunk());
    b1.fireAt(new Coordinate("A0"));
    assertTrue(b1.shipAllSunk());
  }

  @Test
  public void test_move_ship(){
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    Ship<Character> s1 = shipFactory.makeBattleship(new Placement(new Coordinate(5,6), 'U'));
    Ship<Character> s2 = shipFactory.makeBattleship(new Placement(new Coordinate(13,2), 'U'));
    b1.tryAddShip(s1);
    b1.fireAt(new Coordinate(6,6));
    b1.tryAddShip(s2);
    assertThrows(IllegalArgumentException.class, ()->b1.moveShip(s1,  new Placement("a0h")));
    b1.moveShip(s1,  new Placement("a0l"));
    assertEquals(s1.getDisplayInfoAt(new Coordinate(2, 1), true), '*');
    assertNotEquals("", b1.moveShip(s1, new Placement(new Coordinate(13,2), 'U')));
  }
}
