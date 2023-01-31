package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  @Test
  void test_read_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);
    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');
    for (int i = 0; i < expected.length; i++) {
      Placement p = player.readPlacement(prompt);
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
      bytes.reset(); // clear out bytes for next time around
    }

  }

  @Test
  void test_do_one_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);
    V1ShipFactory shipFactory = new V1ShipFactory();
    player.doOnePlacement("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    AbstractShipFactory<Character> asf = new V1ShipFactory();
    Board<Character> tb = new BattleShipBoard<>(10, 20);
    Ship<Character> rts = asf.makeDestroyer(new Placement("B2V"));
    tb.tryAddShip(rts);
    BoardTextView btv = new BoardTextView(tb);
    String s = "Player A where do you want to place a Destroyer?\n" + btv.displayMyOwnBoard();
    assertEquals(bytes.toString(), s);
  }

  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h);
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }

  @Test
  void test_do_placement_phase() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\nA4v\nD0V\nH1V\n", bytes);
    player.doPlacementPhase();
    AbstractShipFactory<Character> asf = new V1ShipFactory();
    Board<Character> tb = new BattleShipBoard<>(10, 20);
    Ship<Character> rts1 = asf.makeSubmarine(new Placement("b2V"));
    Ship<Character> rts2 = asf.makeSubmarine(new Placement("C8h"));
    Ship<Character> rts3 = asf.makeDestroyer(new Placement("A4V"));
    Ship<Character> rts4 = asf.makeDestroyer(new Placement("D0V"));
    Ship<Character> rts5 = asf.makeDestroyer(new Placement("H1V"));
    BoardTextView btv = new BoardTextView(tb);
    String prompt = "--------------------------------------------------------------------------------\n" +
        "Player A: you are going to place the following ships (which are all\n" +
        "rectangular). For each ship, type the coordinate of the upper left\n" +
        "side of the ship, followed by either H (for horizontal) or V (for\n" +
        "vertical).  For example M4H would place a ship horizontally starting\n" +
        "at M4 and going to the right.  You have\n\n " +
        "2 \"Submarines\" ships that are 1x2\n" +
        "3 \"Destroyers\" that are 1x3\n" +
        "3 \"Battleships\" that are 1x4\n" +
        "2 \"Carriers\" that are 1x6\n" +
        "--------------------------------------------------------------------------------\n\n";

    tb.tryAddShip(rts1);
    String hint1 = "Player A where do you want to place a Destroyer?\n";
    String hint2 = "Player A where do you want to place a Submarine?\n";
    String s = prompt + hint2 + btv.displayMyOwnBoard();
    tb.tryAddShip(rts2);
    s += hint2 + btv.displayMyOwnBoard();
    tb.tryAddShip(rts3);
    s += hint1 + btv.displayMyOwnBoard();
    tb.tryAddShip(rts4);
    s += hint1 + btv.displayMyOwnBoard();
    tb.tryAddShip(rts5);
    s += hint1 + btv.displayMyOwnBoard();

    assertEquals(bytes.toString(), s);

  }

  @Test
  void test_unvalid_input_for_do_placement_phase() throws IOException {
    TextPlayer player = createTextPlayer(10, 20, "", new ByteArrayOutputStream());
    assertThrows(IOException.class, () -> player.doPlacementPhase());
    TextPlayer player1 = createTextPlayer(10, 20, "A0Q", new ByteArrayOutputStream());
    assertThrows(IllegalArgumentException.class, () -> player1.doPlacementPhase());
  }
}
