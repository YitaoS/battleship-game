package edu.duke.ys386.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {
  String name;
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;



  public TextPlayer(String n, Board<Character> b,
      BufferedReader i,
      PrintStream o,
      AbstractShipFactory<Character> s
    ) {
    name = n;
    theBoard = b;
    view = new BoardTextView(b);
    inputReader = i;
    out = o;
    shipFactory = s;
    shipsToPlace = new ArrayList<>();
    shipCreationFns = new HashMap<>();
    setupShipCreationList();
    setupShipCreationMap();
  }

  protected void setupShipCreationMap(){
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));

  }

  protected void setupShipCreationList(){
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
  }

  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if(s == null){
        throw new IOException("Please enter valid string");
    }
    return new Placement(s);
  }

  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
    Ship<Character> s = createFn.apply(p);
    out.print(theBoard.tryAddShip(s));
    out.print(view.displayMyOwnBoard());
  }
  
  public void doPlacementPhase() throws IOException {
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
        "at M4 and going to the right.  You have\n\n " +
        "2 \"Submarines\" ships that are 1x2\n" +
        "3 \"Destroyers\" that are 1x3\n" +
        "3 \"Battleships\" that are 1x4\n" +
        "2 \"Carriers\" that are 1x6\n" +
        "--------------------------------------------------------------------------------\n";
    out.println(prompt);
    for(String sship:shipsToPlace){
        doOnePlacement(sship,shipCreationFns.get(sship));
    }
  }
}
