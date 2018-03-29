package CST5301IntroductionToJava.misc;

public class Trail {
	private int start, end;
	int pos;
	
	Trail(int st, int e) {
		pos=start=st;
		end=e;
		
	}
	
	public int move(int amount) {
		int tmp;
		tmp = pos + amount;
		if (tmp <start)
			tmp=start;
		if (tmp>end)
			tmp=end;
		
		return pos=tmp;
			
	}
	public int currentpos() {
		return pos;
	}
	
	public boolean endofTrail() {
		return pos >=end; 
	}
	
}
