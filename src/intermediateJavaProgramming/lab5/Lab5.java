package intermediateJavaProgramming.lab5;

public class Lab5 
{
  public static void main(String args[]) 
  { 
    System.out.println("Person.class: " + Person.class.toString());
    System.out.println("Customer.class: " + Customer.class);  
    
    String[] s = {"customers.txt"};
    CustomerReadTest.main(s);
  }
}
