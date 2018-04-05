package intermediateJavaProgramming.lab7.WOS3;

public class smartplayer extends player implements Magical {
  private int trailStart, trailEnd, Current;
  private int gourdCount = 0;

  public smartplayer(String n, int st, int sp, int w, trail Trail) {
    super(n, st, sp, w);
    trailStart = Trail.getStart();
    trailEnd = Trail.getEnd();
    Current = trailStart;
  }

  public void move(int amount) {
    Current = Current + amount;
    // check boundaries
    if (Current < trailStart)
      Current = trailStart;
    if (Current > trailEnd)
      Current = trailEnd;
  }

  public int pos() {
    return Current;
  }

  public int distributeGourds(int start, int end) {
    return 0;
  }

  public boolean checkForGourds() {
    // is current location in set of Gourd locations?
    System.out.println("Looking for: " + pos() + " in list: " + sGourds);
    return sGourds.contains(pos());
  }

  public void pickUpGourd() {
    // should really use a collection to represent SG's knapsack.
    // this would permit a future enhancements:
    // ... a knapsack of finite size: return boolean false when full
    // ... a knapsack that stores different objects or flavours

    // for now ... keep it simple
    gourdCount++;
  }

  public boolean haveGourds() {
    return gourdCount > 0; // any gourds in "knapsack" ?
  }

  // could change drinkFromGourd() to return boolean
  // false if there are no gourds to drink from, true otherwise
  // then haveGourds() could be removed
  // makes drinkFromGourd() "smart" & no need for game to use haveGourds()
  public void drinkFromGourd() {
    gourdCount = (gourdCount > 0) ? gourdCount - 1 : 0;
    // can't have a negative count
  }
}// end of class
