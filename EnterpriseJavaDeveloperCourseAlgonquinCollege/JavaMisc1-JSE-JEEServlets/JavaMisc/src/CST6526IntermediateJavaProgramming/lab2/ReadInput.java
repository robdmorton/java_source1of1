package CST6526IntermediateJavaProgramming.lab2;


import java.io.*;

class ReadInput {
  public static void main(String args[]) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String input = null;
    System.out.print("> ");
    while ( (input = br.readLine()) != null ) {
      if ( input.equals("bye") )
        break;
      // ...
      System.out.print("> ");
    }
  }
}
