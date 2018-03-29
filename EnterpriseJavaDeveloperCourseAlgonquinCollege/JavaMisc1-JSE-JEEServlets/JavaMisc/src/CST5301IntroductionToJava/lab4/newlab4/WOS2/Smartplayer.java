package CST5301IntroductionToJava.lab4.newlab4.WOS2;
public class Smartplayer extends Player {
	int trailStart, trailEnd, Current;

	public Smartplayer(String n, int st, int sp, int w, Trail Trail) {
		super(n, st, sp, w);
		trailStart = Trail.getStart();
		trailEnd = Trail.getEnd();
		Current = trailStart;
	}

	public void move(int amount) {
		Current = Current + amount;
		// check boundaries
		if (Current < trailStart)
			Current = trailStart;
		if (Current > trailEnd)
			Current = trailEnd;
	}

	public int pos() {
		return Current;

	}

}
