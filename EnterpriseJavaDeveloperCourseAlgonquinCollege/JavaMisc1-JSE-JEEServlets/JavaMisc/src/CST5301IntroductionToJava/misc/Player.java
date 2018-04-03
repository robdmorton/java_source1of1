package CST5301IntroductionToJava.misc;

public class Player {
	private String name;
	private int strength, speed, weapons;
	
	public Player(String name, int speed, int strength, int weapons) {
		
		this.setName(name);
		this.setSpeed(speed);
		this.setStrength(strength);
		this.setWeapons(weapons);
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getWeapons() {
		return weapons;
	}

	public void setWeapons(int weapons) {
		this.weapons = weapons;
	}
	
	
	
	}
	
