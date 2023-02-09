package edu.duke.ys386.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  private T mydata;
  private T onhit;

  public SimpleShipDisplayInfo(T mydata, T onhit) {
    this.mydata = mydata;
    this.onhit = onhit;
  }

  @Override
  public T getInfo(Coordinate where, boolean hit) {
    if(hit){
      return onhit;
    }
    return mydata;
  }

}
