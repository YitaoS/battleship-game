package edu.duke.ys386.battleship;
/** a class for simple information to display the board */
public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  private T mydata;
  private T onhit;

  public SimpleShipDisplayInfo(T mydata, T onhit) {
    this.mydata = mydata;
    this.onhit = onhit;
  }
  /** get the information of specific position on board */
  @Override
  public T getInfo(Coordinate where, boolean hit) {
    if(hit){
      return onhit;
    }
    return mydata;
  }

}
