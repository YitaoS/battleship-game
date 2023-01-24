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
    Ship<Character> s1 = new BasicShip(new Coordinate(1, 2));
    Ship<Character> s2 = new BasicShip(new Coordinate(3, 4));
    Ship<Character> s3 = new BasicShip(new Coordinate(0, 0));
    assertTrue(b1.tryAddShip(s1));
    assertTrue(b1.tryAddShip(s2));
    assertTrue(b1.tryAddShip(s3));
  }

  @Test
  void test_what_ls_at() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10, 20);
    Ship<Character> s1 = new BasicShip(new Coordinate(1, 2));
    Ship<Character> s2 = new BasicShip(new Coordinate(3, 4));
    Ship<Character> s3 = new BasicShip(new Coordinate(0, 0));
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
    assertTrue(b1.tryAddShip(s1));
    assertTrue(b1.tryAddShip(s2));
    assertTrue(b1.tryAddShip(s3));
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
    assertFalse(b1.checkIfWithinBorder(new Coordinate("Z9")));
    assertEquals(b1.whatIsAt(new Coordinate(1, 2)), 's');
    assertEquals(b1.whatIsAt(new Coordinate(3, 4)), 's');
    assertEquals(b1.whatIsAt(new Coordinate(0, 0)), 's');
    assertEquals(b1.whatIsAt(new Coordinate(1, 5)), null);

  }

  private <T> void checkWhatIsAtBoard(Board<T> b, T[][] expected) {
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[0].length; j++) {
        Coordinate where = new Coordinate(i, j);
        assertTrue(b.checkIfWithinBorder(where));
        assertEquals(b.whatIsAt(where), expected[i][j]);
      }
    }
  }
}
