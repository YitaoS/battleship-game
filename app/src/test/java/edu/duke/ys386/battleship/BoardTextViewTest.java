package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2, 'X');
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
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');
    // you should write two assertThrows here
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  @Test
  public void test_display_my_own_board() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2, 'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    RectangleShip<Character> s1 = new RectangleShip<Character>(new Coordinate(1, 1), 's', '*');
    RectangleShip<Character> s2 = new RectangleShip<Character>(new Coordinate(1, 0), 's', '*');
    RectangleShip<Character> s3 = new RectangleShip<Character>(new Coordinate(0, 0), 's', '*');

    assertEquals("", b1.tryAddShip(s1));
    assertEquals("", b1.tryAddShip(s2));
    assertEquals("", b1.tryAddShip(s3));
    String expected = expectedHeader +
        "A s|  A\n" +
        "B s|s B\n" +
        expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_enemy_board(){
    Board b = new BattleShipBoard<Character>(4, 3, 'X');
    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> s1 = sf.makeSubmarine(new Placement("b0h"));
    Ship<Character> s2 = sf.makeDestroyer(new Placement("A3v"));
    b.tryAddShip(s1);
    b.tryAddShip(s2);
    BoardTextView btv = new BoardTextView(b);
    String myView =
    "  0|1|2|3\n" +
    "A  | | |d A\n" +
    "B s|s| |d B\n" +
    "C  | | |d C\n" +
    "  0|1|2|3\n";
    assertEquals(myView, btv.displayMyOwnBoard());
    b.fireAt(new Coordinate("a1"));
    b.fireAt(new Coordinate("b1"));
    b.fireAt(new Coordinate("c3"));
    String myView1 =
    "  0|1|2|3\n" +
    "A  | | |d A\n" +
    "B s|*| |d B\n" +
    "C  | | |* C\n" +
    "  0|1|2|3\n";
    assertEquals(myView1, btv.displayMyOwnBoard());
    String myView2 =
    "  0|1|2|3\n" +
    "A  |X| |  A\n" +
    "B  |s| |  B\n" +
    "C  | | |d C\n" +
    "  0|1|2|3\n";
    assertEquals(myView2, btv.displayEnemyBoard());
    
  }

  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

}
