package CST5301IntroductionToJava;

import CST6526IntermediateJavaProgramming.lab1.Lab1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException; //import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader; //import java.io.InputStreamReader;
import java.io.PrintWriter;

//import java.io.Console;
//import java.nio.CharBuffer;
import java.util.Scanner;

//import java.util.regex.Pattern;
//import java.util.regex.Matcher;

public class IntroJavaTest 
{
	
  static int noOfInstances=0;
  GenericFruit ivgf;
  int myInteger=0;
	
  IntroJavaTest()
  {
	  noOfInstances++; 
  }
  
  public static void main(String args[]) throws IOException {
  //public static void main(String[] args) throws IOException {
  
	System.out.printf("in main method: \n");

	//static method
	tryme(); 
//	tryThisNonStaticMethod();
	//Cannot make a static reference to the non-static field myInteger
//	myInteger=0;
	  
	//Cannot make a static reference to the non-static field ivgf
//	ivgf = new GenericFruit();
	//gf is a method variable and is on the stack for each method 
	//call static or otherwise...not an instance variable
	GenericFruit gf = new GenericFruit();
	gf.getSweetness();
	
	//Cannot use this in a static context
//	this.gf.getSweetness();
	
	IntroJavaTest ijt = new IntroJavaTest();
	ijt = new IntroJavaTest();
	ijt = new IntroJavaTest();
	ijt = new IntroJavaTest();
	ijt = new IntroJavaTest();
	ijt.tryThisNonStaticMethod();
	
	StaticVsDynamic.main(new String[1]);
	StaticVsDynamic.main(new String[1]);
	StaticVsDynamic.main(new String[1]);
	StaticVsDynamic.main(new String[1]);
	
	
//    initialTest();
//    copyBytes();
//    copyLines();
//    testException();
//    testIO();
  }

  public static void tryme()
  {
	for(int i = 5;i<5;i++)
	{
		System.out.println("i");
	}
	int ctr=0;
	for ( int i = 0; i <= 50; i += 5 ) 
	{
		ctr++;
	}
	System.out.println("for counter: " + ctr);	
  }
  
  public static void initialTest() {

	  
    ScopeTest test = new ScopeTest();
    test.testScope();
    
//    int a = Integer.parseInt("123.456");
    
    String s = new String("Frank");
    String t = new String("Frank");
    if (s == t)
      System.out.println("True; the object references are equal");
    else
      System.out.println("False; the object references aren't equal");

    int testInt = -1;
    System.out.println("Short.MIN_VALUE: " + Short.MIN_VALUE);
    System.out.println("Short.MAX_VALUE: " + Short.MAX_VALUE);
    System.out.println("testInt = " + testInt);
    System.out.println("testInt -1 >> 1 = " + (testInt >> 1));
    System.out.println("testInt -1 >>> 1 = " + (testInt >>> 1));
    System.out.println("testInt -1 << 1 = " + (testInt << 1));
    System.out.println("Integer.toHexString(testInt) " + testInt + " "
        + Integer.toHexString(testInt));
    testInt = Integer.MIN_VALUE;
    System.out.println("Integer.toHexString(Integer.MIN_VALUE) " + testInt
        + " " + Integer.toHexString(testInt));
    testInt = Integer.MAX_VALUE;
    System.out.println("Integer.toHexString(Integer.MAX_VALUE) " + testInt
        + " " + Integer.toHexString(testInt));
    Long testLong = Long.MIN_VALUE;
    System.out.println("Integer.toHexString(Long.MIN_VALUE) " + testLong + " "
        + Long.toHexString(testLong));
    testLong = Long.MAX_VALUE;
    System.out.println("Integer.toHexString(Long.MAX_VALUE) " + testLong + " "
        + Long.toHexString(testLong));

    Lab1 lab1 = new Lab1();
    System.out.println("******* lab1: " + lab1 + " *******");

    Person p = new Person();
    System.out.println("p is a Person");
    if (p instanceof Object) {
      System.out.println("p instanceof Object");
    }
    if (p instanceof Person) {
      System.out.println("p instanceof Person");
    }
    if (p instanceof Worker) {
      System.out.println("p instanceof Worker");
    }
    else
    {
      System.out.println("p !instanceof Worker");
    }
    Worker w = new Worker();
    p = w;
    System.out.println("p = w = new Worker");
    if (p instanceof Worker) {
      System.out.println("p NOW instanceof Worker");
    }
    Object o;
    o = new Worker();
    System.out.println("Object o = new Worker");
    if (o instanceof Worker) {
      System.out.println("o instanceof Worker");
    }

    Character.isDigit('1');
    
    String st = "abc";
    st.length();
    System.out.println(st);
  }

  public static void copyBytes() throws IOException {
    FileInputStream in = null;
    FileOutputStream out = null;
    try {
      in = new FileInputStream("xanadu.txt");
      out = new FileOutputStream("outagain.txt");
      byte c;

      while ((c = (byte) in.read()) != -1) {
        // System.out.println((char)c);
        char charToPrint = (Character.isLetterOrDigit(c)
            || Character.isSpaceChar(c) || !Character.isWhitespace(c)) ? (char) c
            : '#';
        System.out.println("c: " + c + " character: \'" + (charToPrint & 0x7F)
            + "\' \'0x" + Integer.toHexString(0x50) + "\'");
        // System.out.print((char)c);
        out.write(c);
      }

    } finally {
      if (in != null) {
        in.close();
      }
      if (out != null) {
        out.close();
      }
    }
  }

  public static void copyLines() throws IOException
{
  BufferedReader inputStream = null;
  PrintWriter outputStream = null;

  try {
    inputStream = 
      new BufferedReader(new FileReader("xanadu.txt"));
    outputStream = 
      new PrintWriter(new FileWriter("characteroutput.txt"));

    String l;
    byte byteArray[]=null;
    while ((l = inputStream.readLine()) != null) {
      byteArray = l.getBytes();
      System.out.printf("%c %c\n", l.charAt(0), byteArray[0]);
      outputStream.println(l);
    }
  } finally {
    if (inputStream != null) {
      inputStream.close();
    }
    if (outputStream != null) {
      outputStream.close();
    }
  }	
}

  public static void testException() {

	System.out.printf("in testException method: \n");
    ArithmeticException arithmeticException = new ArithmeticException();
    System.out.printf("ArithmeticException: %s\n", arithmeticException);

    try {

    } catch (ArithmeticException e) {
      RuntimeException rte = new RuntimeException();
      throw rte;
    } catch (Exception e) {

    } finally {

    }
  }

  public static void testIO() throws IOException {
    while (true) {
      Scanner scanner = new Scanner(System.in);

      System.out.println("Enter your regex: ");
      String str = scanner.nextLine();
      System.out.printf("%s\n", str);

      System.out.println("Enter input string to search: ");
      str = scanner.nextLine();
      System.out.printf("%s\n", str);

    }
  }
  
  public void tryThisNonStaticMethod()
  {
	  int i=3;
	  myInteger++;
	  System.err.println("i=" + i + " noOfInstances:" + noOfInstances
	    + " myInteger:" + myInteger
	  );
  }

}
