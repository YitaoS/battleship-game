package edu.duke.ys386.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {
  final String name;
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  final HashMap<String, Integer> skillUses;
  final boolean isHuman;
  /**
   * get the textplayer's name
   * 
   * @return name
   */
  String getName() {
    return name;
  }

  /**
   * get the board of the playerplayer
   * 
   * @return board
   */
  Board<Character> getBoard() {
    return theBoard;
  }

  /**
   * get the view of the player
   *
   * @return view
   */
  BoardTextView getView() {
    return view;
  }

  /**
   * Create a testplayer
   *
   * @param name  of the player
   * @param board of the player
   * @param i     the player's input stream
   * @param o     the output of the stream
   * @param sf    the ship factory to create the ship
   * @param s     the number of submarine to create
   * @param d     the number of destroyer to create
   * @param b     the number of battleship to create
   * @param c     the number of carrier to create
   */
  public TextPlayer(String n, Board<Character> board,
      BufferedReader i,
      PrintStream o,
      AbstractShipFactory<Character> sf,
      int s, int d, int b, int c, boolean isHuman) {
    name = n;
    theBoard = board;
    view = new BoardTextView(board);
    inputReader = i;
    out = o;
    shipFactory = sf;
    shipsToPlace = new ArrayList<>();
    shipCreationFns = new HashMap<>();
    setupShipCreationList(s, d, b, c);
    setupShipCreationMap();
    skillUses = new HashMap<>();
    setupSkillUses();
    this.isHuman = isHuman;
  }

  /**
   * set up the skill uses time limits for the textplayer
   */
  protected void setupSkillUses(){
    skillUses.put("M",3);
    skillUses.put("S",3);
  }

  /** Create a map match the name of ship to its creator function */
  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));

  }

  /**
   * Set up a ship creation list for player to create
   *
   * @param s the number of submarine to create
   * @param d the number of destroyer to create
   * @param b the number of battleship to create
   * @param c the number of carrier to create
   */
  protected void setupShipCreationList(int s, int d, int b, int c) {
    shipsToPlace.addAll(Collections.nCopies(s, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(d, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(b, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(c, "Carrier"));
  }

  /**
   * read a {@link Placement} from input
   * 
   * @param prompt the instruction for player input
   * @return the placement on the board
   * @throws IOException happens if no input or invalid input
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new IOException("Please enter valid string");
    }
    return new Placement(s);
  }

  /**
   * read a {@link Coordinate} from input
   * 
   * @param prompt the instruction for player input
   * @return the coordinate on the board
   * @throws IOException happens if no input or invalid input
   */

  public Coordinate readCoordinate(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new IOException("Input is null!");
    }
    return new Coordinate(s);
  }
  /**
   * read a action choice for a turn
   * 
   * @param prompt the prompt to hint the player
   * @return  the string of choice
   * @throws IOException throw if the choice is invalid
   */
  public String readActionChoice(String prompt) throws IOException{
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new IOException("Input is null!");
    }
    s = s.toUpperCase();
    if (!(s.equals("F")||s.equals("M")||s.equals("S"))){
      throw new IOException("Choice should be among F, M and S!");
    }
    return s;
  }

  /**
   * ask the player to do one placement of current ship to place
   * 0
   * 
   * @param shipName the name of the ship to create
   * @param createFn the creator function name of the ship
   * @throws IOException happens if invalid input
   */
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException,IllegalArgumentException {
    Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
    Ship<Character> s = createFn.apply(p);
    String error = theBoard.tryAddShip(s);
    if (error != "") {
      throw new IOException(error);
    }
    out.print(view.displayMyOwnBoard());
  }

  /** ask the player to do placement phase */
  public void doPlacementPhase() {
    // (a) display the starting (empty) board
    // (b) print the instructions message (from the README,
    // but also shown again near the top of this file)
    // (c) call doOnePlacement to place one ship
    view.displayMyOwnBoard();
    String prompt = "--------------------------------------------------------------------------------\n" +
        "Player "+ getName() +": you are going to place the following ships (which are all\n" +
        "rectangular). For each ship, type the coordinate of the upper left\n" +
        "side of the ship, followed by either H (for horizontal) or V (for\n" +
        "vertical).  For example M4H would place a ship horizontally starting\n" +
        "at M4 and going to the right.  You have\n\n" +
        "2 \"Submarines\" ships that are 1x2\n" +
        "3 \"Destroyers\" that are 1x3\n" +
        "3 \"Battleships\" that are 1x4\n" +
        "2 \"Carriers\" that are 1x6\n" +
        "--------------------------------------------------------------------------------\n";
    out.println(prompt);
    for (String sship : shipsToPlace) {
      while (true) {
        try {
          doOnePlacement(sship, shipCreationFns.get(sship));
        } catch (Exception e) {
          out.println(e.getMessage());
          out.println("Please input an valid placement!");
          continue;
        }
        break;
      }
    }
  }

  /**
   * ask to player to do one attackattack
   * 0
   * 
   * @param enemyBoard
   * @throws IOException
   */
  public void doOneAttack(Board<Character> enemyBoard, String[] myTurnRes, boolean isHuman) throws IOException {
    Coordinate p = readCoordinate("Player " + name + " where do you want to FIRE AT?");
    String error = theBoard.checkIfWithinBorder(p);
    if (error != "") {
      throw new IOException(error);
    }
    Ship<Character> s = enemyBoard.fireAt(p);
    String prompt = "You missed!\n";
    if(isHuman){myTurnRes[0] = getName() + " missed!\n";}else{
      myTurnRes[0] = getName() + " missed.\n";
    }
    if (s != null) {
      prompt = "You hit a " + s.getName() + "!\n";
      if(isHuman){
        myTurnRes[0] = getName() + " hit your " + s.getName() + " at " + p.toString() + "!\n";
      }else{
        myTurnRes[0] = getName() + " hit " + s.getName() + " at " + p.toString()+".\n";
      }
    }
    out.println(prompt);
  }

  /**
   * do the action of moving a ship
   * 
   * @throws IOException any illegal input
   */
  public void moveOneShip()throws IOException{
    Coordinate coordiOfShip = readCoordinate("Player " + name + " Which ship do you want to move?\n Please enter a coordinate occupied by the ship");
    Ship<Character> s = theBoard.getShip(coordiOfShip);
    if(s == null){
      throw new IOException("There is no ship here! Please enter again!");
    }
    Placement newPlacement = readPlacement("Player " + name + "where do you want to move the ship?");
    String error = theBoard.moveShip(s,newPlacement);
    if (error != "") {
      throw new IOException(error);
    }
    String prompt = "Successfully move the ship!";
    out.println(prompt);
  }

  /**
   * do the action of sonar scan an area in opponent's board
   * 
   * @param enemyBoard the opponent board
   * @throws IOException any invalid input
   */
  public void doOneSonarScan(Board<Character> enemyBoard)throws IOException{
    Coordinate coordiOfShip = readCoordinate("Player " + name + "Which area do you want to scan?\nPlease enter a coordinate as the center of the sonar scan area");
    String s = enemyBoard.checkIfWithinBorder(coordiOfShip);
    if(s != ""){
      throw new IOException(s);
    }
    HashMap<String,Integer> appearTimes = enemyBoard.sonarScan(coordiOfShip);
    String result = "Submarines occupy "+ appearTimes.getOrDefault("Submarine", 0) +" squares\n"+
    "Destroyers occupy "+ appearTimes.getOrDefault("Destroyer", 0) +" squares\n"+
    "Battleships occupy "+ appearTimes.getOrDefault("Battleship", 0) +" squares\n"+
    "Carriers occupy "+ appearTimes.getOrDefault("Carrier", 0) +" square\n";
    out.println(result);
  }
  /**
   * ask a player to play a turn
   * 0
   * 
   * @param enemyBoard opposite board
   * @param enemyView  opposite view
   * @param enemyName  oppsite name
   */
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName,  String[] myTurnRes) {
    out.println(name + "'s turn:");
    String myHeader = "Your ocean";
    String enemyHeader = enemyName + "'s ocean";
    out.print(view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));
    while (true) {
      try {
        String choice = readActionChoice("Possible actions for Player "+getName()+":\n\n"+
        "F Fire at a square\n"+
        "M Move a ship to another square ("+ skillUses.get("M") +" remaining)\n"+
        "S Sonar scan ("+ skillUses.get("S") +" remaining)\n\n"+
        "Player "+ getName() +", what would you like to do?\n");
        if(choice.equals("F")){
          doOneAttack(enemyBoard, myTurnRes,isHuman);
        }else if(choice.equals("M")){
          int leftTimes = skillUses.get("M");
          if(leftTimes <= 0){
            throw new IllegalArgumentException("You've already used up Move ship opportunities!");
          }
          moveOneShip();
          skillUses.put("M",leftTimes-1);
          if(!isHuman){myTurnRes[0] = getName() + " used a special action!\n";}else{
            myTurnRes[0] = "";
          }
        }else{//Sonar scan cases
          int leftTimes = skillUses.get("S");
          if(leftTimes <= 0){
            throw new IllegalArgumentException("You've already used up Sonic scan opportunities!");
          }
          doOneSonarScan(enemyBoard);
          skillUses.put("S",leftTimes-1);
          if(!isHuman){myTurnRes[0] = getName() + " used a special action!\n";}else{
            myTurnRes[0] = "";
          }
        }        
      } catch (Exception e) {
        out.println(e.getMessage());
        continue;
      }
      break;
    }
  }

  /**
   * show if the player lose the gamegame
   * 0
   * 
   * @return true if lose, Or false
   */
  public boolean loseTheGame() {
    return theBoard.shipAllSunk();
  }
}
