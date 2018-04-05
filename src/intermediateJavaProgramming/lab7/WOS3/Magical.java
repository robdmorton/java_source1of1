package intermediateJavaProgramming.lab7.WOS3;

import java.util.HashSet;
import java.util.Set;

public interface Magical {
  public final int gourdsMin = 4;
  public final int gourdsMax = 10; // game constants

  // fields in an interface must be initialized and are implicitly public, static, and final.
  Set<Integer> sGourds = new HashSet<Integer>();

  public int distributeGourds(int start, int end); // returns how many were created

  public boolean checkForGourds();

  public void pickUpGourd();

  public void drinkFromGourd();
}
