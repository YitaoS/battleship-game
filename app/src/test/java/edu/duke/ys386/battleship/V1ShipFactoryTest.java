package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
  @Test
  public void test_make_destroyer() {
    V1ShipFactory f = new V1ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
    Ship<Character> dst = f.makeDestroyer(v1_2);
    checkShip(dst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));
  }

  @Test
  public void test_make_Submarine() {
    V1ShipFactory f = new V1ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
    Ship<Character> dst = f.makeSubmarine(v1_2);
    checkShip(dst, "Submarine", 's', new Coordinate(1, 2), new Coordinate(2, 2));
    Placement h1_2 = new Placement(new Coordinate(1, 2), 'h');
    Ship<Character> dst1 = f.makeSubmarine(h1_2);
    checkShip(dst1, "Submarine", 's', new Coordinate(1, 2), new Coordinate(1, 3));

  }

  @Test
  public void test_make_battleship() {
    V1ShipFactory f = new V1ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
    Ship<Character> dst = f.makeBattleship(v1_2);
    checkShip(dst, "Battleship", 'b', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2),
        new Coordinate(4, 2));
  }

  @Test
  public void test_make_carrier() {
    V1ShipFactory f = new V1ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
    Ship<Character> dst = f.makeCarrier(v1_2);
    checkShip(dst, "Carrier", 'c', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2),
        new Coordinate(4, 2), new Coordinate(5, 2), new Coordinate(6, 2));
  }

  private void checkShip(Ship<Character> testShip, String expectedName,
      char expectedLetter, Coordinate... expectedLocs) {
    assertEquals(testShip.getName(), expectedName);
    for (int i = 0; i < expectedLocs.length; i++) {
      assertEquals(testShip.getDisplayInfoAt(expectedLocs[i]), expectedLetter);
    }
  }

}
