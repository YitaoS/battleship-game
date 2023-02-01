package edu.duke.ys386.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the
 * enemy's board.
 */

public class BoardTextView {
  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   * @throws IllegalArgumentException if the board is larger than 10x26.
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" +
              toDisplay.getHeight());
    }
  }

  /**
   * Display the board
   * 
   * @param toDisplay is the Board to display
   * @return a String that represent the board
   */
  protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
    String header = makeHeader();
    StringBuilder ans = new StringBuilder(header);
    makeBody(ans, getSquareFn);
    ans.append(header);
    return ans.toString(); // this is a placeholder for the moment
  }

  /**
   * Display my board with the enemy's board side by sideside
   * 0
   * 
   * @param enemyView
   * @param myHeader
   * @param enemyHeader
   * @return
   */
  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    int width = toDisplay.getWidth();
    StringBuffer sb = new StringBuffer();
    // header
    for (int i = 0; i < 5; i++)
      sb.append(' ');
    sb.append(myHeader);
    for (int i = sb.length(); i < 2 * width + 22; i++)
      sb.append(' ');
    sb.append(enemyHeader + '\n');
    // body
    String[] lines1 = displayMyOwnBoard().split("\n");
    String[] lines2 = enemyView.displayEnemyBoard().split("\n");
    for (int index = 0; index < lines1.length; index++) {
      StringBuilder tsb = new StringBuilder();
      tsb.append(lines1[index]);
      for (int i = lines1[index].length(); i < 2 * width + 19; i++) {
        tsb.append(' ');
      }
      tsb.append(lines2[index] + '\n');
      sb.append(tsb.toString());
    }
    // tail
    return sb.toString();
  }

  /** display my own board */
  public String displayMyOwnBoard() {
    return displayAnyBoard((p) -> toDisplay.whatIsAtForSelf(p));
  }

  public String displayEnemyBoard() {
    return displayAnyBoard((p) -> toDisplay.whatIsAtForEnemy(p));
  }

  /**
   * add the body to the stringbuilder
   * 
   * @param ans a stringbuilder that hold the header of the board
   * @return a stringbuilder that hold the header and the body of the board
   */
  private void makeBody(StringBuilder ans, Function<Coordinate, Character> getSquareFn) {
    for (int row = 0; row < toDisplay.getHeight(); row++) {
      ans.append((char) ('A' + row));
      ans.append(' ');
      String sep = "";
      for (int column = 0; column < toDisplay.getWidth(); column++) {
        ans.append(sep);
        Character curChar = getSquareFn.apply(new Coordinate(row, column));
        if (curChar != null) {
          ans.append(curChar);
        } else {
          ans.append(" ");
        }
        sep = "|";
      }
      ans.append(' ');
      ans.append((char) ('A' + row));
      ans.append('\n');
    }
  }

  /**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board
   */
  String makeHeader() {
    StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
    String sep = ""; // start with nothing to separate, then switch to | to separate
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append('\n');
    return ans.toString();
  }

}
