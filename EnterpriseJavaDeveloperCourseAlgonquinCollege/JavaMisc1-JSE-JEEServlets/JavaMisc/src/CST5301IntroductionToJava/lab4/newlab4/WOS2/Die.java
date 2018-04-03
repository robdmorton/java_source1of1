package CST5301IntroductionToJava.lab4.newlab4.WOS2;

public class Die {
	private int sides;
	private int min, max;
	
	
	
	public Die(int s) {
		sides=s;
		min=1;
	}
	
	public Die(int mymin, int mymax){
		min=mymin;
		max=mymax;
		sides=max-min+1;
		
	}
	
	public int roll() {
		return min + (int)(sides*Math.random());
	}
	
}
