package intermediateJavaProgramming.lab7.WOS1;

public class game {

	public static void main(String[] args) {
		die sided8 = new die(8);
		trail t = new trail(0,100);
		smartplayer sg= new smartplayer("SirGeorge", 5,5,5, t);
		player a= new player("Alvakast", 10,3,3);
		player b=new player("Bondercrast",3,3,3);
		player c=new player("Cryokast", 3,3,10);
		player d=new player("Deetokast", 3,10,3);
		
		int amount=0;
		int result;
		
		while (sg.pos() < 100) {
			result=sided8.roll();
			switch (result) {
			case 1: 
			case 2:
			case 3:
			case 4:	amount=result;
					break;
			case 5: amount=sg.fight(a);
					break;
			case 6: amount=sg.fight(b);
					break;
			case 7: amount=sg.fight(c);
					break;
			case 8: amount=sg.fight(d);
					break;
			}
			
			System.out.println("Move: " + amount);
			sg.move(amount);
			System.out.println("position: " + sg.pos());
						
		}
	}
}
