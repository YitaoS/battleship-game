package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_check_my_rules() {
    RectangleShip<Character> rs1 = new RectangleShip<Character>("RtShip", new Coordinate(0, 2), 2, 1, 's', '*','v');
    RectangleShip<Character> rs2 = new RectangleShip<Character>("RtShip", new Coordinate(2, 9), 2, 2, 's', '*','v');
    BattleShipBoard<Character> bsb = new BattleShipBoard<>(10, 20, 'X');
    assertEquals("", bsb.getPlacementRuleChecker().checkPlacement(rs1, bsb));
    assertNotEquals("", bsb.getPlacementRuleChecker().checkPlacement(rs2, bsb));
  }

}
