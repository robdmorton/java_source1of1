package intermediateJavaProgramming.lab7.WOS1;
public class smartplayer extends player {
	int trailStart, trailEnd, Current;

	public smartplayer(String n, int st, int sp, int w, trail Trail) {
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
