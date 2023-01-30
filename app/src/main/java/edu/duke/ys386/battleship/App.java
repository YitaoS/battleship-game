/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ys386.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
  TextPlayer player1;
  TextPlayer player2;

  public App(TextPlayer player1, TextPlayer player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  public static void main(String[] args) throws IOException {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20);
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    V1ShipFactory factory = new V1ShipFactory();
    TextPlayer p1 = new TextPlayer("A", b1, input, System.out, factory);
    TextPlayer p2 = new TextPlayer("B", b2, input, System.out, factory);
    App app = new App(p1, p2);
    p1.doPlacementPhase();
    p2.doPlacementPhase();
  }
}
