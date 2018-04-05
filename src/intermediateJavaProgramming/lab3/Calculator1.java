package intermediateJavaProgramming.lab3;


public class Calculator1 {
  public static int Calculate(String expression)
      throws NumberFormatException, ArrayIndexOutOfBoundsException {
    String[] s = expression.split(" ");
    int result = 0;
    for (int i = 0, k = s.length; i < k; i++)
      result += s[i].equals("-") ? -1 * Integer.parseInt(s[++i])
          : s[i].equals("+") ? Integer.parseInt(s[++i]) : Integer.parseInt(s[i]);
    return result;
  }
}
