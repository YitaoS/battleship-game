/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ys386.battleship;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

public class App {
  TextPlayer player1;
  TextPlayer player2;

  /**
   * create a game include the both side of the gamegame
   * 0
   * 
   * @param player1
   * @param player2
   */
  public App(TextPlayer player1, TextPlayer player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  /**
   * check if any player wins the gamegame
   * 0
   * 
   * @return null id
   */
  public String checkWhoWin() {
    if (player1.loseTheGame()) {
      return player2.getName();
    }
    if (player2.loseTheGame()) {
      return player1.getName();
    }
    return null;
  }

  public static TextPlayer createComputerPlayer(String name,int w, int h, OutputStream bytes){
    String inputData = getComputerInput();
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    return new TextPlayer(name, board, input, output, shipFactory, 2, 3, 3, 3,false);
  }

  public static String getComputerInput(){
    StringBuilder sb = new StringBuilder();
    String placePhaseString = "a1v\na2v\na3v\na5h\nd2v\nc0d\na4r\ng0u\nd3u\nh3u\nh4l\n";
    sb.append(placePhaseString);
    sb.append("m\na1\nj1v\n");
    for(int i = 0; i < 10; i++){
      sb.append("s\nd5\n");
      for(int j = 0; j < 20;j++){
        sb.append("f\n");
        sb.append((char)('a'+j));
        sb.append((char)('0'+i));
        sb.append('\n');
      }
    }
    return sb.toString();
  }

  public static String tryReadModeChoice(String prompt,BufferedReader input)throws IOException{
    System.out.print(prompt);
    String s = input.readLine();
    if(!(s.equals("1")||s.equals("2")||s.equals("3")||s.equals("4"))){
      throw new IOException("Please enter 1,2,3 or 4 to choose a valid mode!");
    }
    return s;
  }

  public static String readModeChoice(BufferedReader input)throws IOException{
    String choice = "";
    while(true){
      try{
        choice = tryReadModeChoice("Please enter a number to choose a mode\n\n1: human vs human\n2: human vs computer\n3: computer vs human\n4: computer vs computer\n\nYour choice is:\n", input);
      }catch(IOException e){
        System.out.println(e.getMessage());
        continue;
      }
      break;
    }
    return choice;
  }

  public static App setPlayers(String choice,BufferedReader input){
    TextPlayer p1 = new TextPlayer("A", new BattleShipBoard<Character>(10, 20, 'X'), input, System.out, new V2ShipFactory(), 2, 3, 3, 3,true);
    TextPlayer p2 = new TextPlayer("B", new BattleShipBoard<Character>(10, 20, 'X'), input, System.out, new V2ShipFactory(), 2, 3, 3, 3,true);
    TextPlayer c1 = createComputerPlayer("A",10, 20,  new ByteArrayOutputStream());
    TextPlayer c2 = createComputerPlayer("B",10, 20,  new ByteArrayOutputStream());

    if(choice.equals("1")){
      return new App(p1,p2);
    }else if(choice.equals("2")){
      return new App(p1,c2);
    }else if(choice.equals("3")){
      return new App(c1,p2);
    }
    return new App(c1,c2);
  }

  public static void main(String[] args) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    String choice = readModeChoice(input);
    App app = setPlayers(choice, input);
    app.player1.doPlacementPhase();
    app.player2.doPlacementPhase();
    String s;
    String[] lastTurnRes1 = new String[1];
    lastTurnRes1[0] = "";
    String[] lastTurnRes2 = new String[1];
    lastTurnRes2[0] = "";
    while (true) {
      System.out.println(lastTurnRes1[0]);
      app.player1.playOneTurn(app.player2.getBoard(), app.player2.getView(), app.player2.getName(),lastTurnRes2);
      s = app.checkWhoWin();
      if (s != null) {
        break;
      }
      System.out.println(lastTurnRes2[0]);
      app.player2.playOneTurn(app.player1.getBoard(), app.player1.getView(), app.player1.getName(),lastTurnRes1);
      s = app.checkWhoWin();
      if (s != null) {
        break;
      }
    }
    System.out.println(s + " win!");
  }
}
