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
      int s, int d, int b, int c) {
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
   * ask the player to do one placement of current ship to place
   * 0
   * 
   * @param shipName the name of the ship to create
   * @param createFn the creator function name of the ship
   * @throws IOException happens if invalid input
   */
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
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
        "Player A: you are going to place the following ships (which are all\n" +
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
  public void doOneAttack(Board<Character> enemyBoard) throws IOException {
    Coordinate p = readCoordinate("Player " + name + " where do you want to FIRE AT?");
    String error = theBoard.checkIfWithinBorder(p);
    if (error != "") {
      throw new IOException(error);
    }
    Ship<Character> s = enemyBoard.fireAt(p);
    String prompt = "You missed!";
    if (s != null) {
      prompt = "You hit a " + s.getName() + "!";
    }
    out.println(prompt);
  }

  /**
   * ask a player to play a turn
   * 0
   * 
   * @param enemyBoard opposite board
   * @param enemyView  opposite view
   * @param enemyName  oppsite name
   */
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName) {
    out.println(name + "'s turn:");
    String myHeader = "Your ocean";
    String enemyHeader = enemyName + "'s ocean";
    out.print(view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));
    while (true) {
      try {
        doOneAttack(enemyBoard);
      } catch (Exception e) {
        out.println(e.getMessage());
        out.println("Please input an valid coordinate!");
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
