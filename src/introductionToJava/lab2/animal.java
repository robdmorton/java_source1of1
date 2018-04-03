package introductionToJava.lab2;

public class animal {
 String name = null;
 int numfeet = -1;
 public animal(){}
 
public animal( String s, int i){
	 setname(s);
	 setnumfeet(i);
 }
public animal( String s ){
	setname(s);
}
private void setnumfeet(int i) {
	numfeet = i;	
}
private void setname(String s) {
	name = s;	
}
public void speak(){
	 System.out.println("My name is: " + getname() + " and i have " + getnumfeet()+ " feet" );
 }
private String getname() {
	// TODO Auto-generated method stub
	return name;
}
private int getnumfeet() {
	// TODO Auto-generated method stub
	return numfeet;
}
public static void main( String[] arg){
	 animal a = new animal("hog",7 );
	 a.speak();
 }
}
