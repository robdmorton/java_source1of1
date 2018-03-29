package CST6526IntermediateJavaProgramming.lab4;

public class Lab4 
{
  
  public static void main(String args[]) 
  { 
    System.out.println("Person.class: " + Person.class.toString());
    System.out.println("Customer.class: " + Customer.class);  
    
    String[] s = {"customers.txt"};
    CustomerReadTest.main(s);
  }
  
}
