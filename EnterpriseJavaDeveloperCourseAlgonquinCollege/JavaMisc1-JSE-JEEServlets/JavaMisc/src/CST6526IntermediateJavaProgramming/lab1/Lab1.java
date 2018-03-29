package CST6526IntermediateJavaProgramming.lab1;



public class Lab1 
{
	//main method
	public static void main(String[] args)
	{
	  
	  VerifyLogin vl = new VerifyLogin();
	  try 
	  {
  			    
	    if ( vl.verify(args) ) 
  	  {
  	    System.out.println("Args are good");
  	  }
  	  else 
  	  {
  	    System.out.println("Args are bad");
  	  }
  	  
  	} 
	  catch ( ArgumentCountException e ) 
	  {
		  System.err.println(e.getMessage());
		  System.err.printf("**********************************\n");
  	  e.printStackTrace();
  	  System.err.printf("**********************************\n");
	  }
	}
	
	void testMethod()
	{
	  try
	  {
	    throw new MyException();
	  }
    catch(MyException me)
    {
      System.err.println(me.getMessage());
    }
	}
}
