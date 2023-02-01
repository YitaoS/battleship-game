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

  String getName() {
    return name;
  }

  Board<Character> getBoard() {
    return theBoard;
  }

  BoardTextView getView() {
    return view;
  }
  public TextPlayer(String n, Board<Character> board,
                    BufferedReader i,
                    PrintStream o,
                    AbstractShipFactory<Character> sf,
                    int s,int d,int b, int c
  ) {
    name = n;
    theBoard = board;
    view = new BoardTextView(board);
    inputReader = i;
    out = o;
    shipFactory = sf;
    shipsToPlace = new ArrayList<>();
    shipCreationFns = new HashMap<>();
    setupShipCreationList(s,d,b,c);
    setupShipCreationMap();
  }

  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));

  }

  protected void setupShipCreationList(int s,int d,int b, int c) {
    shipsToPlace.addAll(Collections.nCopies(s, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(d, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(b, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(c, "Carrier"));
  }

  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new IOException("Please enter valid string");
    }
    return new Placement(s);
  }

  public Coordinate readCoordinate(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new IOException("Input is null!");
    }
    return new Coordinate(s);
  }

  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
    Ship<Character> s = createFn.apply(p);
    String error = theBoard.tryAddShip(s);
    if(error!=""){
      throw new IOException(error);
    }
    out.print(view.displayMyOwnBoard());
  }

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
          out.println(e);
          out.println("Please input an valid placement!");
          continue;
        }
        break;
      }
    }
  }

  public void doOneAttack(Board<Character> enemyBoard) throws IOException {
    Coordinate p = readCoordinate("Player " + name + " where do you want to FIRE AT?");
    Ship<Character> s = enemyBoard.fireAt(p);
    String prompt = "You missed!";
    if (s != null) {
      prompt = "You hit a " + s.getName() + "!";
    }
    out.println(prompt);
  }

  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName) {
    out.println(name + "'s turn:");
    String myHeader = "Your ocean";
    String enemyHeader = enemyName + "'s ocean";
    out.print(view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));
    while (true) {
      try {
        doOneAttack(enemyBoard);
      } catch (Exception e) {
        out.println(e);
        out.println("Please input an valid coordinate!");
        continue;
      }
      break;
    }
  }

  public boolean loseTheGame() {
    return theBoard.shipAllSunk();
  }
}
