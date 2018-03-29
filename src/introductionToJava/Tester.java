package introductionToJava;

public class Tester{
  
  public static void main(String[] args)
  {
    GenericFruit gf = new GenericFruit(5);
    System.out.println("GenericFruit sweetness = " + gf.getSweetness());
    
    Orange o = new Orange(10,true);
    System.out.println("Orange sweetness = " + o.getSweetness() + " " +
    		"seedless = " + o.isSeedless());
    
  }
     
}

