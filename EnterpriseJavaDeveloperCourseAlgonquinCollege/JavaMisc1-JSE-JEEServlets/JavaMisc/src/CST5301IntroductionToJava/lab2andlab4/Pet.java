package CST5301IntroductionToJava.lab2andlab4;

/**
 * @author      Firstname Lastname <address @ example.com>
 * @version     1.6                 (the version of the package this class was first added to)                   
 * @since       2010-03-31          (a date or the version number of this program)
 */
public class Pet extends Animal {
 String owner = null;
 
 public Pet()
 {
	 this("");
 }
 
 
 /**
  * non-default constructor
  *
  * @param  s name          
  */
 public Pet( String s ){
	 super(s);
 }
 /**
  * @param  s owner
  * @return void
  */
 public void setowner(String s ){ // set owner
	 owner = s;
 }
 /**
  *
  * @return String owner
  */
 public String getowner(){ // get owner
	 return owner;
 }
 /**
  * 
  * print the owner of the pet
  * 
  * @return void
  */
 public void printowner(){
	 System.out.println("My owner is: " + getowner());
 }
 /**
  * 
  * print out that this is the Pet object speaking
  * 
  * @return void
  */
public void speak(){
	 super.speak();
	 System.out.println("I am the pet speaking");
	 this.printowner();
 }
}
