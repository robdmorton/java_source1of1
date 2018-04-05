package intermediateJavaProgramming.lab1;



public class VerifyLogin {

  final int i = 0;

  public boolean verify(String[] args) throws ArgumentCountException {
    if (args.length == 2)
      return true;
    else
      throw new ArgumentCountException("The number of arguments should be 2...Blah Blah Blah");
  }

}
