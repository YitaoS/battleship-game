package edu.duke.ys386.battleship;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
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
    assertThrows(IOException.class, () -> player.readPlacement(prompt));
  }

  @Test
  void test_do_one_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);
    V1ShipFactory shipFactory = new V1ShipFactory();
    player.doOnePlacement("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    AbstractShipFactory<Character> asf = new V1ShipFactory();
    Board<Character> tb = new BattleShipBoard<>(10, 20, 'X');
    Ship<Character> rts = asf.makeDestroyer(new Placement("B2V"));
    tb.tryAddShip(rts);
    BoardTextView btv = new BoardTextView(tb);
    String s = "Player A where do you want to place a Destroyer?\n" + btv.displayMyOwnBoard();
    assertEquals(bytes.toString(), s);
  }

  @Test
  void test_null_input_do_one_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "Z9v\n", bytes);
    assertThrows(IOException.class, ()->player.doOnePlacement("Destroyer", (p) -> player.shipFactory.makeDestroyer(p)));
  }

  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory, 2, 3, 0, 0);
  }

  @Test
  void test_do_placement_phase() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "A0V\nA1V\nA4v\nA7V\nA8V\n", bytes);
    player.doPlacementPhase();
    AbstractShipFactory<Character> asf = new V1ShipFactory();
    Board<Character> tb = new BattleShipBoard<>(10, 20, 'X');
    Ship<Character> rts1 = asf.makeSubmarine(new Placement("A0V"));
    Ship<Character> rts2 = asf.makeSubmarine(new Placement("A1v"));
    Ship<Character> rts3 = asf.makeDestroyer(new Placement("A4V"));
    Ship<Character> rts4 = asf.makeDestroyer(new Placement("A7V"));
    Ship<Character> rts5 = asf.makeDestroyer(new Placement("A8V"));
    BoardTextView btv = new BoardTextView(tb);
    String prompt = "--------------------------------------------------------------------------------\n" +
        "Player A: you are going to place the following ships (which are all\n" +
        "rectangular). For each ship, type the coordinate of the upper left\n" +
        "side of the ship, followed by either H (for horizontal) or V (for\n" +
        "vertical).  For example M4H would place a ship horizontally starting\n" +
        "at M4 and going to the right.  You have\n\n" +
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
  void test_invalid_input_for_do_placement_phase() throws IOException {
    TextPlayer player = createTextPlayer(10, 20, "\nAw5\nA1V\nA2v\nA4v\nA5V\nA6V\n", new ByteArrayOutputStream());
    TextPlayer player1 = createTextPlayer(10, 20, "A0Q\nA0V\nA2v\nA7v\nA8V\nA9V\n", new ByteArrayOutputStream());
    assertDoesNotThrow(() -> player.doPlacementPhase());
    assertDoesNotThrow(() -> player1.doPlacementPhase());
  }

  @Test
  void test_read_coordinate() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2\nC8\na4\n", bytes);
    String prompt = "Please enter a location for a ship:";
    Coordinate[] expected = new Coordinate[3];
    expected[0] = new Coordinate(1, 2);
    expected[1] = new Coordinate(2, 8);
    expected[2] = new Coordinate(0, 4);
    for (int i = 0; i < expected.length; i++) {
      Coordinate p = player.readCoordinate(prompt);
      assertEquals(p, expected[i]); // did we get the right Coordinate back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
      bytes.reset(); // clear out bytes for next time around
    }

  }

  @Test
  void test_do_one_attack() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player1 = createTextPlayer(10, 20, "B2V\nC6\n", bytes);
    player1.doOnePlacement("TestShip", player1.shipCreationFns.get("Submarine"));
    TextPlayer player2 = createTextPlayer(10, 20, "B2V\nC6H\nA4v\nD0V\nH1V\n", bytes);
    player2.doOnePlacement("TestShip", player1.shipCreationFns.get("Submarine"));
    player2.doOnePlacement("TestShip", player1.shipCreationFns.get("Submarine"));
    player1.doOneAttack(player2.getBoard());
    assertEquals(player2.theBoard.whatIsAtForSelf(new Coordinate("C6")), '*');
    assertEquals(player2.theBoard.whatIsAtForEnemy(new Coordinate("C6")), 's');
    assertThrows(IOException.class, () -> {
      player1.doOneAttack(player2.getBoard());
    });
  }

  @Test
  void test_play_one_turn() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player1 = createTextPlayer(10, 20,
        "B2V\nC6H\nA4v\nD0V\nH1V\nc8v\n\nC6\nZ6\nA5\n", bytes);
    player1.doPlacementPhase();
    TextPlayer player2 = createTextPlayer(10, 20, "B2V\nC6H\nA4v\nD0V\nH1V\n",
        bytes);
    player2.doPlacementPhase();
    player1.playOneTurn(player2.getBoard(), player2.getView(),
        player2.getName());
    player1.playOneTurn(player2.getBoard(), player2.getView(),
        player2.getName());
    assertEquals(player2.getBoard().whatIsAtForSelf(new Coordinate("C6")), '*');
    assertEquals(player2.getBoard().whatIsAtForEnemy(new Coordinate("C6")), 's');
    assertEquals(player2.getBoard().whatIsAtForSelf(new Coordinate("Z6")), null);
    assertEquals(player2.getBoard().whatIsAtForEnemy(new Coordinate("Z6")), null);
  }

}
