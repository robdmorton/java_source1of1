package CST5301IntroductionToJava.lab4.newlab4.WOS3;

public class Player {
	private int Strength;
	private int Speed;
	private int Weapons;
	private String Name;
	
	public Player(String n, int st, int sp, int w) {
		Name=n;
		Strength=st;
		Speed=sp;
		Weapons=w;
	}
	
	public int fight(Player monster) {
		int mythrow;
		int diff;
		
		Die sided3=new Die(3);
		
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
