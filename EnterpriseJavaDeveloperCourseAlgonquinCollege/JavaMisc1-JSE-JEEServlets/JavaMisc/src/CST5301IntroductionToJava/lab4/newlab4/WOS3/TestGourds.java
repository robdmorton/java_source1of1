package CST5301IntroductionToJava.lab4.newlab4.WOS3;

public class TestGourds  {

	public static void main(String[] args) {
		Trail dummy = new Trail(0,100);
		for (int i=0; i< 100; i++) {
			System.out.println(dummy.distributeGourds(0,100));
			System.out.println(dummy.sGourds);
			dummy.sGourds.clear();
		}
	}
}
