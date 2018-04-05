package intermediateJavaProgramming.lab2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ReadInput {
  public static void main(String args[]) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String input = null;
    System.out.print("> ");
    while ((input = br.readLine()) != null) {
      if (input.equals("bye"))
        break;
      // ...
      System.out.print("> ");
    }
  }
}
