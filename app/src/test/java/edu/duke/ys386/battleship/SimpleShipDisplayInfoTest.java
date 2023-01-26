package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_get_info() {
    SimpleShipDisplayInfo<String> ssdi = new SimpleShipDisplayInfo<String>("s", "X");
    assertEquals(ssdi.getInfo(null, false), "s");
    assertEquals(ssdi.getInfo(null, true), "X");
  }

}
