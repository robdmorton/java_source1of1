package CST5301IntroductionToJava.lab4.newlab4.WOS3;

//import java.util.Set;

public class Game {

	public static void main(String[] args) {
		Die sided8 = new Die(8);
		Trail t = new Trail(0, 100);
    Smartplayer sg = new Smartplayer("SirGeorge", 5, 5, 5, t);
		Player a = new Player("Alvakast", 10, 3, 3);
		Player b = new Player("Bondercrast", 3, 3, 3);
		Player c = new Player("Cryokast", 3, 3, 10);
		Player d = new Player("Deetokast", 3, 10, 3);
		
		int amount = 0;
		int result;
		// check for gourds at the start
		if (sg.checkForGourds()) {
			System.out.println("Found gourd at: " + sg.pos());
			sg.pickUpGourd();
		}

		while (sg.pos() < 100) {
			result = sided8.roll();
			switch (result) {
			case 1:
			case 2:
			case 3:
			case 4:
				amount = result;
				break;
			case 5:
				amount = sg.fight(a);
				break;
			case 6:
				amount = sg.fight(b);
				break;
			case 7:
				amount = sg.fight(c);
				break;
			case 8:
				amount = sg.fight(d);
				break;
			}
			// check for a fight that is lost
			if (amount < 0) {
				System.out.println("--- Battle could be lost!");
				if (sg.haveGourds()) {
					sg.drinkFromGourd(); // drink-em if you have-em
					amount = 1;
					System.out.println("--- Lucky to have a gourd");
				} // (g.haveGourds())
			} // (amount<0)
			
			System.out.println("Move: " + amount);
			sg.move(amount);
			System.out.println("position: " + sg.pos());
			// check for gourds after we move
			if (sg.checkForGourds()) {
				System.out.println("Found gourd at: " + sg.pos());
				sg.pickUpGourd();
			}

		}
	}
}
