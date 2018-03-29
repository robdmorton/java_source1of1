package CST5301IntroductionToJava;

public class StaticVsDynamic 
{
	Container container;
//	static Container container;
	static int i;
	
	public static void main(String[] args)
	{
		i++;


		//This...
//		container = new Container();
//		container.addPet(new Worker());
		
		//vs this...
		Container container2 = new Container();
		container2.addPet(new Worker());
		
		System.out.println("Hello, world! i:"+i+" container2:"+
		  container2.getPet(0));
		
	}
}
