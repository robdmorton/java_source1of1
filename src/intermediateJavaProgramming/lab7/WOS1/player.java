package intermediateJavaProgramming.lab7.WOS1;

public class player {
	private int Strength;
	private int Speed;
	private int Weapons;
	private String Name;
	
	public player(String n, int st, int sp, int w) {
		Name=n;
		Strength=st;
		Speed=sp;
		Weapons=w;
	}
	
	public int fight(player monster) {
		int mythrow;
		int diff;
		
		die sided3=new die(3);
		
		mythrow=sided3.roll();
		switch (mythrow) {
			case 1: diff=Strength-monster.Strength;
					break;
			case 2: diff=Speed-monster.Speed;
					break;
			case 3: diff=Weapons-monster.Weapons;
					break;
			default: diff=0;
					 break;
		}
		return diff;
	}
}
