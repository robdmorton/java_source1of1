package CST5301IntroductionToJava.lab2andlab4;

public class pizzaform extends form{
	private String pizzasize = "xlarge";
	
 public String getPizzasize() {
		return pizzasize;
	}
public void setPizzasize(String pizzasize) {
		this.pizzasize = pizzasize;
	}
public pizzaform(){
	 System.out.println(" I am the plain constructor");
 }
 public pizzaform( String name ){
	 setname(name);
 }
 public void setname( String newname){
	 System.out.println("pizzaform: setname");
	 super.setname(newname);
 }
 
 
 public static void main(String[] arg){
	 pizzaform pf = new pizzaform("james");
	 pf.setPizzasize("small");
	 
	 pizzaform pf2 = new pizzaform("sam");
	 pf.setname(  new String("superman"));

	 System.out.println("Name is: "+ pf.getname());
	 System.out.println("Name is: "+ pf2.getname());

 }
}
