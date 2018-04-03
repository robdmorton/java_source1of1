package intermediateJavaProgramming.lab7.WOS2;

public class trail implements Magical {
	private int start;
	private int end;
	// private int current;
	
	public trail(int s, int e) {
		if (s < 0)
			start=0;
		else
			start=s;
		
		if (s < e)
			end=e;
		else
			end=start+1; //check boundaries
		
		// current=start;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}
	public int distributeGourds(int start, int end){
		int numberGourds;
		die g =new die(gourdsMin, gourdsMax);
		numberGourds=g.roll();
		die t = new die(0,100);
		for (int i=0; i<numberGourds; i++) {
			int location=t.roll();
			while (sGourds.add(location) == false)
				location=t.roll();
		} // end of for
		
		return numberGourds;
	} // returns how many were created
	public boolean checkForGourds() {return false;}
	public void pickUpGourd(){}
	public void drinkFromGourd(){}
}
