package CST5301IntroductionToJava.misc;

public class TestTrail {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Trail myTrail = new Trail(0,100);
		die side8 = new die(8);
		die side3 = new die(3);
		
		System.out.println("Start of trail at " + myTrail.currentpos());
		while (!myTrail.endofTrail()) {
			myTrail.move(side8.roll());
			if (myTrail.endofTrail())
				break;
			myTrail.move(-side3.roll());

			System.out.println(myTrail.currentpos());
		}
		System.out.println(myTrail.currentpos());
		
		

	}

}
