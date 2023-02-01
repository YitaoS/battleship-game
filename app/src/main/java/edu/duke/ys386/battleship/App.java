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

  public String checkWhoWin(){
    if(player1.loseTheGame()){
      return player2.getName();
    }
    if(player2.loseTheGame()){
      return player1.getName();
    }
    return null;
  }

  public static void main(String[] args) throws IOException {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    V1ShipFactory factory = new V1ShipFactory();
    TextPlayer p1 = new TextPlayer("A", b1, input, System.out, factory,2,1,0,0);
    TextPlayer p2 = new TextPlayer("B", b2, input, System.out, factory,2,1,0,0);
    App app = new App(p1, p2);
    app.player1.doPlacementPhase();
    app.player2.doPlacementPhase();
    String s;
    while(true){
      app.player1.playOneTurn(app.player2.getBoard(),app.player2.getView(),app.player2.getName());
      s = app.checkWhoWin();
      if(s!=null) {
        break;
      }
      app.player2.playOneTurn(app.player1.getBoard(),app.player1.getView(),app.player1.getName());
      s = app.checkWhoWin();
      if(s!=null){
        break;
      }
    }
    System.out.println(s+ " win!");
  }
}
