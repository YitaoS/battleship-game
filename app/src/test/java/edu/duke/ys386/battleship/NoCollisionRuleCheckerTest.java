package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_my_rule() {
    V1ShipFactory v1sf = new V1ShipFactory();
    Ship<Character> rs = v1sf.makeBattleship(new Placement("A2v"));
    BattleShipBoard<Character> b = new BattleShipBoard<>(10, 20, new NoCollisionRuleChecker<>(null));
    assertTrue(b.getPlacementRuleChecker().checkPlacement(rs, b));
    b.tryAddShip(rs);
    assertFalse(b.getPlacementRuleChecker().checkPlacement(rs, b));
  }

  @Test
  public void test_check_placement() {
    V1ShipFactory v1sf = new V1ShipFactory();
    Ship<Character> rs1 = v1sf.makeBattleship(new Placement("A2v"));
    Ship<Character> rs2 = v1sf.makeBattleship(new Placement("B3v"));
    Ship<Character> rs3 = v1sf.makeBattleship(new Placement("D2h"));
    Ship<Character> rs4 = v1sf.makeBattleship(new Placement("A7h"));
    BattleShipBoard<Character> b = new BattleShipBoard<>(10, 20,
        new NoCollisionRuleChecker<>(new InBoundsRuleChecker<>(null)));
    assertTrue(b.getPlacementRuleChecker().checkPlacement(rs1, b));
    b.tryAddShip(rs1);
    assertFalse(b.getPlacementRuleChecker().checkPlacement(rs1, b));
    assertTrue(b.getPlacementRuleChecker().checkPlacement(rs2, b));
    assertFalse(b.getPlacementRuleChecker().checkPlacement(rs3, b));
    assertFalse(b.getPlacementRuleChecker().checkPlacement(rs4, b));

  }

}
