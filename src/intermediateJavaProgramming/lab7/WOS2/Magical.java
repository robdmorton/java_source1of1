package intermediateJavaProgramming.lab7.WOS2;
import java.util.*;

public interface Magical {
	public final int gourdsMin=4;
	public final int gourdsMax=10; // game constants
	
	Set<Integer> sGourds = new  HashSet<Integer>();
	
	public int distributeGourds(int start, int end); // returns how many were created
	public boolean checkForGourds();
	public void pickUpGourd();
	public void drinkFromGourd();
}
