package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    RectangleShip<Character> s1 = new RectangleShip<Character>(new Coordinate(1, 1), 's', '*','v');
    RectangleShip<Character> s2 = new RectangleShip<Character>(new Coordinate(1, 0), 's', '*','v');
    RectangleShip<Character> s3 = new RectangleShip<Character>(new Coordinate(0, 0), 's', '*','v');

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
  public void test_display_enemy_board() {
    Board<Character> b = new BattleShipBoard<Character>(4, 3, 'X');
    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> s1 = sf.makeSubmarine(new Placement("b0h"));
    Ship<Character> s2 = sf.makeDestroyer(new Placement("A3v"));
    b.tryAddShip(s1);
    b.tryAddShip(s2);
    BoardTextView btv = new BoardTextView(b);
    String myView = "  0|1|2|3\n" +
        "A  | | |d A\n" +
        "B s|s| |d B\n" +
        "C  | | |d C\n" +
        "  0|1|2|3\n";
    assertEquals(myView, btv.displayMyOwnBoard());
    b.fireAt(new Coordinate("a1"));
    b.fireAt(new Coordinate("b1"));
    b.fireAt(new Coordinate("c3"));
    String myView1 = "  0|1|2|3\n" +
        "A  | | |d A\n" +
        "B s|*| |d B\n" +
        "C  | | |* C\n" +
        "  0|1|2|3\n";
    assertEquals(myView1, btv.displayMyOwnBoard());
    String myView2 = "  0|1|2|3\n" +
        "A  |X| |  A\n" +
        "B  |s| |  B\n" +
        "C  | | |d C\n" +
        "  0|1|2|3\n";
    assertEquals(myView2, btv.displayEnemyBoard());

  }

  @Test
  public void test_display_my_board_with_enemy_next_to_it() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> s1 = sf.makeSubmarine(new Placement("a0v"));
    Ship<Character> s2 = sf.makeDestroyer(new Placement("b2v"));
    Ship<Character> s3 = sf.makeDestroyer(new Placement("f2v"));
    Ship<Character> s4 = sf.makeCarrier(new Placement("a8v"));
    Ship<Character> s5 = sf.makeBattleship(new Placement("g6v"));
    Ship<Character> s6 = sf.makeCarrier(new Placement("k0h"));
    Ship<Character> s7 = sf.makeSubmarine(new Placement("m4h"));
    Ship<Character> s8 = sf.makeBattleship(new Placement("o6v"));
    Ship<Character> s9 = sf.makeDestroyer(new Placement("t0H"));
    Ship<Character> s10 = sf.makeDestroyer(new Placement("d2h"));
    Ship<Character> s11 = sf.makeSubmarine(new Placement("o5h"));
    b1.tryAddShip(s1);
    b1.tryAddShip(s2);
    b1.tryAddShip(s3);
    b1.tryAddShip(s4);
    b1.tryAddShip(s5);
    b1.tryAddShip(s6);
    b1.tryAddShip(s7);
    b1.tryAddShip(s8);
    b1.tryAddShip(s9);
    b2.tryAddShip(s10);
    b2.tryAddShip(s11);
    b1.fireAt(new Coordinate("c2"));
    b2.fireAt(new Coordinate("C2"));
    b2.fireAt(new Coordinate("E2"));
    b2.fireAt(new Coordinate("D1"));
    b2.fireAt(new Coordinate("J3"));
    b2.fireAt(new Coordinate("L4"));
    b2.fireAt(new Coordinate("D2"));
    b2.fireAt(new Coordinate("D3"));
    b2.fireAt(new Coordinate("o5"));
    b2.fireAt(new Coordinate("O6"));
    BoardTextView mbtv = new BoardTextView(b1);
    BoardTextView ebtv = new BoardTextView(b2);
    String myView = "     Your ocean                           Player B's ocean\n" +
        "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
        "A s| | | | | | | |c|  A                A  | | | | | | | | |  A\n" +
        "B s| |d| | | | | |c|  B                B  | | | | | | | | |  B\n" +
        "C  | |*| | | | | |c|  C                C  | |X| | | | | | |  C\n" +
        "D  | |d| | | | | |c|  D                D  |X|d|d| | | | | |  D\n" +
        "E  | | | | | | | |c|  E                E  | |X| | | | | | |  E\n" +
        "F  | |d| | | | | |c|  F                F  | | | | | | | | |  F\n" +
        "G  | |d| | | |b| | |  G                G  | | | | | | | | |  G\n" +
        "H  | |d| | | |b| | |  H                H  | | | | | | | | |  H\n" +
        "I  | | | | | |b| | |  I                I  | | | | | | | | |  I\n" +
        "J  | | | | | |b| | |  J                J  | | |X| | | | | |  J\n" +
        "K c|c|c|c|c|c| | | |  K                K  | | | | | | | | |  K\n" +
        "L  | | | | | | | | |  L                L  | | | |X| | | | |  L\n" +
        "M  | | | |s|s| | | |  M                M  | | | | | | | | |  M\n" +
        "N  | | | | | | | | |  N                N  | | | | | | | | |  N\n" +
        "O  | | | | | |b| | |  O                O  | | | | |s|s| | |  O\n" +
        "P  | | | | | |b| | |  P                P  | | | | | | | | |  P\n" +
        "Q  | | | | | |b| | |  Q                Q  | | | | | | | | |  Q\n" +
        "R  | | | | | |b| | |  R                R  | | | | | | | | |  R\n" +
        "S  | | | | | | | | |  S                S  | | | | | | | | |  S\n" +
        "T d|d|d| | | | | | |  T                T  | | | | | | | | |  T\n" +
        "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n";
    assertEquals(myView, mbtv.displayMyBoardWithEnemyNextToIt(ebtv, "Your ocean", "Player B's ocean"));
  }

  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }
}
