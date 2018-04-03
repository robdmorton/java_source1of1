package intermediateJavaProgramming.lab7.WOS3;

public class die {
	private int sides;
	private int min, max;
	
	
	
	public die(int s) {
		sides=s;
		min=1;
	}
	
	public die(int mymin, int mymax){
		min=mymin;
		max=mymax;
		sides=max-min+1;
		
	}
	
	public int roll() {
		return min + (int)(sides*Math.random());
	}
	
}
