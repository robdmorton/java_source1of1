package intermediateJavaProgramming.lab6;

//import intermediateJavaProgramming.lab6.ArrayList.CustomerReadTest;
//import intermediateJavaProgramming.lab6.HashMap.CustomerReadTest;
//import intermediateJavaProgramming.lab6.HashMapSortedOutput.CustomerReadTest;

public class Lab6 
{
  
  public static void main(String args[]) 
  { 
    String[] s = {"customers.txt"};
    
    //PWD
    String curDir = System.getProperty("user.dir");
    System.out.println("pwd: " + curDir);
    
//    intermediateJavaProgramming.lab6.ArrayList.CustomerReadTest.main(s);
//    System.out.println("*******");
    
//    intermediateJavaProgramming.lab6.HashMap.CustomerReadTest.main(s);
//    System.out.println("*******");
    
    intermediateJavaProgramming.lab6.HashMapSortedOutput.CustomerReadTest.main(s);
  }
}
