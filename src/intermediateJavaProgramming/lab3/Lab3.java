package intermediateJavaProgramming.lab3;

public class Lab3 
{
  public static void main(String[] args) 
  {
    Calculator c = new Calculator();

    if(args.length==0)return;

    // make a string from the args
    StringBuffer b = new StringBuffer();
    for (int i = 0; i < args.length;i++) {
        b.append(args[i]);
        b.append(" ");
    }
    b.setLength(b.length()-1); // trim off trailing space
    // result = c.calculate(that string)
    int result = c.calculate(b.toString());
    // print out the result
    System.out.println("java Calculator "+b.toString());
    System.out.println("Result = "+result);
  }

  public static void main1(String[] args) {
    StringBuilder sb=new StringBuilder();
    if(args.length==0)return;
    for(int i=0,k=args.length;i<k;i++)
      sb.append(args[i]+" ");
    sb.setLength(sb.length()-1);
    try{
      System.out.println("result == "+Calculator1.Calculate(sb.toString()));
    }
    catch(NumberFormatException nfe){
      System.out.println("result == weird entries to calculate");
    }
    catch(ArrayIndexOutOfBoundsException aiobe){
      System.out.println("result == forgot a number friend!");
    }
  }

}
