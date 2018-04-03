package intermediateJavaProgramming.lab7.WOS1;

public class die {
	private int sides;

	public die(int s) {
		sides=s;
	}
	
	public int roll() {
		return 1 + (int)(sides*Math.random());
	}
}
