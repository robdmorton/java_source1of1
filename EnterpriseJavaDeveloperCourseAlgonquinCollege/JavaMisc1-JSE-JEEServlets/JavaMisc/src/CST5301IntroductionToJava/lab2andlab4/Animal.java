package CST5301IntroductionToJava.lab2andlab4;

/**
 * @author      Firstname Lastname <address @ example.com>
 * @version     1.6                 (the version of the package this class was first added to)                   
 * @since       2010-03-31          (a date or the version number of this program)
 */
public class Animal {
 private String name = null;
 private int numfeet = -1;
 public Animal(){}
 
/**
 * non-default constructor
 *
 * @param  s name
 * @param  i number of feet         
 */
public Animal( String s, int i){
	 setname(s);
	 setnumfeet(i);
 }
/**
 * non-default constructor
 *
 * @param  s name
 */
public Animal( String s ){
	setname(s);
}
/**
 *
 * @param  i number of feet
 * @return void
 */
private void setnumfeet(int i) {
	numfeet = i;	
}
/**
*
* @param  s name
* @return void
*/
private void setname(String s) {
	name = s;	
}
/**
*
* @return void
*/
public void speak(){
	 System.out.println("My name is: " + "\"" + getname() + "\"" + " and I have " + getnumfeet()+ " feet" );
 }
public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getNumfeet() {
	return numfeet;
}

public void setNumfeet(int numfeet) {
	this.numfeet = numfeet;
}

/**
*
* @return String name
*/
private String getname() {
	// TODO Auto-generated method stub
	return name;
}
/**
*
* @return int number of feet
*/
private int getnumfeet() {
	// TODO Auto-generated method stub
	return numfeet;
}
/**
 * main line of java
 *
 * @param  arg command line parameters
 * @return void
 */
public static void main( String[] arg){
	 Animal a = new Animal("hog",7 );
	 a.speak();
 }
}
