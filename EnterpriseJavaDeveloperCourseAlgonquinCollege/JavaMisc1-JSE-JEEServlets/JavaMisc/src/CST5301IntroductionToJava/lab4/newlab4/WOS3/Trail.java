package CST5301IntroductionToJava.lab4.newlab4.WOS3;

public class Trail implements Magical {
	private int start;
	private int end;
	
	public Trail(int s, int e) {
		if (s < 0)
			start=0;
		else
			start=s;
		
		if (s < e)
			end=e;
		else
			end=start+1; //check boundaries
		
		// sprinkle gourds throughout
		distributeGourds(s,e);
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}
	public int distributeGourds(int start, int end){
		int numberGourds;
		Die g =new Die(gourdsMin, gourdsMax);
		numberGourds=g.roll();
		Die t = new Die(0,100);
		for (int i=0; i<numberGourds; i++) {
			int location=t.roll();
			while (sGourds.add(location) == false)
				location=t.roll();
		} // end of for
		System.out.println("created: "+numberGourds+ " at locations: "+sGourds);
		return numberGourds;
	} // returns how many were created
	public boolean checkForGourds() {return false;}
	public void pickUpGourd(){}
	public void drinkFromGourd(){}
}
