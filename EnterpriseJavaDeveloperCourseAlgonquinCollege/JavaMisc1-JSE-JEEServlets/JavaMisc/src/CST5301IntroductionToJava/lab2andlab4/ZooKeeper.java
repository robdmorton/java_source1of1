package CST5301IntroductionToJava.lab2andlab4;

/**
 * @author      Firstname Lastname <address @ example.com>
 * @version     1.6                 (the version of the package this class was first added to)                   
 * @since       2010-03-31          (a date or the version number of this program)
 */
public class ZooKeeper {

	/**
	 * Short one line description.                           (1)
	 *
	 * Longer description. If there were any, it would be    [2]
	 * here.
	 *
	 * @param  variable Description text text text.           (3)
	 * @param  args command line parameters          
	 * @return Description text text text.
	 * @return void
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       Animal a = new Animal("unicorn");
       a.setNumfeet(4);
       a.speak();
       Animal b = new Animal("cow");
       b.setNumfeet(4);
       b.speak();
       Pet p = new Pet("l'il piggy");
       p.setNumfeet(4);
       p.setowner("me");
       p.speak();
	}

}
