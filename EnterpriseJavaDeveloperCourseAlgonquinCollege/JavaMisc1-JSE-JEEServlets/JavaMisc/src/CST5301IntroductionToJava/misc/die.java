package CST5301IntroductionToJava.misc;

public class die {
	private int sides;
	
	public die(int s) {
		if (s > 0) 
			sides=s;
		else
			System.out.println("Invalid value for sides");
	}
	
	public int roll() {
		return 1 + (int)(sides*Math.random());
	}
}
