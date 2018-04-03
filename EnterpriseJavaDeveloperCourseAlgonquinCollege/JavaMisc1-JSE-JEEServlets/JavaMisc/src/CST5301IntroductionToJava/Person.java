package CST5301IntroductionToJava;

public class Person {
  private String name; 


  Person()
  {
    
  }
  
  Person(String name)   
  { 
    this.name = name; 
  }

  void test()
  {
    int i = name.length();
    i++;
  }
  
  public static void main(String a[])
  {
    Person p = new Person(a[0]);
    p.toString();
  }
}
