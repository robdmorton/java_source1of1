// Book 1, Listing 4-1
/*
 * Created on Nov 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package introductionToJava;

/**
 * @author Doug Lowe
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HelloSayer {

    private String greeting;
   	private String addressee;

   	/**
   	 * @param greeting
   	 * @param addressee
   	 */
   	public HelloSayer(String greeting, String addressee) {
	        this.greeting = greeting;
       		this.addressee = addressee;
    }

   	public void sayHello() {
       		System.out.println(greeting + ", " + addressee + "!");
    }
}
