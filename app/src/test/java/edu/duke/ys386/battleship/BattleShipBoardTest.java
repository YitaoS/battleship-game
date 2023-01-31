package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20));
  }

  @Test
  public void test_try_add_ship() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);

    RectangleShip<Character> s1 = new RectangleShip<Character>(new Coordinate(1, 2), 's', '*');
    RectangleShip<Character> s2 = new RectangleShip<Character>(new Coordinate(3, 4), 's', '*');
    RectangleShip<Character> s3 = new RectangleShip<Character>(new Coordinate(0, 0), 's', '*');
    RectangleShip<Character> s4 = new RectangleShip<Character>(new Coordinate(-5, 0), 's', '*');
    RectangleShip<Character> s5 = new RectangleShip<Character>(new Coordinate(0, -5), 's', '*');

    assertEquals("", b1.tryAddShip(s1));
    assertNotEquals("", b1.tryAddShip(s1));
    assertEquals("", b1.tryAddShip(s2));
    assertEquals("", b1.tryAddShip(s3));
    assertNotEquals("", b1.tryAddShip(s4));
    assertNotEquals("", b1.tryAddShip(s5));
  }

  @Test
  void test_what_ls_at() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10, 20);
    RectangleShip<Character> s1 = new RectangleShip<Character>(new Coordinate(1, 2), 's', '*');
    RectangleShip<Character> s2 = new RectangleShip<Character>(new Coordinate(3, 4), 's', '*');
    RectangleShip<Character> s3 = new RectangleShip<Character>(new Coordinate(0, 0), 's', '*');

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
    assertEquals(b1.whatIsAt(new Coordinate(1, 2)), 's');
    assertEquals(b1.whatIsAt(new Coordinate(3, 4)), 's');
    assertEquals(b1.whatIsAt(new Coordinate(0, 0)), 's');
    assertEquals(b1.whatIsAt(new Coordinate(1, 5)), null);

  }

  private <T> void checkWhatIsAtBoard(Board<T> b, T[][] expected) {
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[0].length; j++) {
        Coordinate where = new Coordinate(i, j);
        assertEquals("", b.checkIfWithinBorder(where));
        assertEquals(b.whatIsAt(where), expected[i][j]);
      }
    }
  }
}
