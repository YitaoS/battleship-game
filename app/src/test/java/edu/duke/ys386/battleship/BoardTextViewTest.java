package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader +
        "A  |  A\n" +
        "B  |  B\n" +
        expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_9by3() {
    String expectedHeader = "  0|1|2|3|4|5|6|7|8\n";
    String expectedBody = "A  | | | | | | | |  A\nB  | | | | | | | |  B\nC  | | | | | | | |  C\n";
    emptyBoardHelper(9, 3, expectedHeader, expectedBody);
  };

  @Test
  public void test_display_empty_3by5() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\nB  | |  B\nC  | |  C\nD  | |  D\nE  | |  E\n";
    emptyBoardHelper(3, 5, expectedHeader, expectedBody);
  };

  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20);
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27);
    // you should write two assertThrows here
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  @Test
  public void test_display_by() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    Ship<Character> s1 = new BasicShip(new Coordinate(1, 1));
    Ship<Character> s2 = new BasicShip(new Coordinate(1, 0));
    Ship<Character> s3 = new BasicShip(new Coordinate(0, 0));
    assertTrue(b1.tryAddShip(s1));
    assertTrue(b1.tryAddShip(s2));
    assertTrue(b1.tryAddShip(s3));
    String expected = expectedHeader +
        "A s|  A\n" +
        "B s|s B\n" +
        expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h);
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

}
