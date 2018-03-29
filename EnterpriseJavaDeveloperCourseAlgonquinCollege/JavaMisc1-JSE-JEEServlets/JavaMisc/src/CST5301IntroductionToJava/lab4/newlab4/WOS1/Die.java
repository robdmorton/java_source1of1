package CST5301IntroductionToJava.lab4.newlab4.WOS1;

public class Die 
{
	private int sides;

	public Die(int s) 
	{
		sides=s;
	}
	
	public int roll() 
	{
		return 1 + (int)(sides*Math.random());
	}
}
