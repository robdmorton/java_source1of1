package CST5301IntroductionToJava.lab4.newlab4.WOS1;

public class Trail {
	private int start;
	private int end;
	// private int current;
	
	public Trail(int s, int e) {
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
	
//	public void move(int amount) {
//		current=current+amount; 
//							//check boundaries
//		if (current < start)
//			current=start;
//		if (current > end)
//			current=end;
//	}
//	
//	public int pos() {
//		return current;
//	}
}
