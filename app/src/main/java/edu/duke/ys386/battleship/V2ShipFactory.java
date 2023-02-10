package edu.duke.ys386.battleship;

/**
 * this class is for the ship factory in version 2 battleship game
 */
public class V2ShipFactory implements AbstractShipFactory<Character> {

  protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) throws IllegalArgumentException{
    if(where.getOrientation() != 'V'&&where.getOrientation() != 'H'){
      throw new IllegalArgumentException("This is a rectangle Ship, orientation can only be V or H");
    }
    if (where.getOrientation() == 'V') {
      return new RectangleShip<Character>(name, where.getWhere(), w, h, letter, '*','V');
    }
    return new RectangleShip<Character>(name, where.getWhere(), h, w, letter, '*','H');
  }
  protected Ship<Character> createSpecialShip(Placement where, char letter, String name)throws IllegalArgumentException {
    return new SpecialShip<Character>(name, where.getWhere(), where.getOrientation(), letter, '*');
  }
  @Override
  public Ship<Character> makeSubmarine(Placement where) throws IllegalArgumentException{

    return createShip(where, 1, 2, 's', "Submarine");
  }

  @Override
  public Ship<Character> makeDestroyer(Placement where) throws IllegalArgumentException{
    return createShip(where, 1, 3, 'd', "Destroyer");
  }

  @Override
  public Ship<Character> makeBattleship(Placement where) throws IllegalArgumentException{
    return createSpecialShip(where, 'b', "Battleship");
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) throws IllegalArgumentException{
    return createSpecialShip(where, 'c', "Carrier");
  }

}
