package coreservlets;

public class FilterTest {
  public static void main(String[] args) {
    String input;
    if (args.length > 0) {
      input = args[0];
    } else {
      input = "<TABLE>blah blah \"blah\"</TABLE>";
    }
    System.out.println("Orig: " + input);
    System.out.println("Filtered: " +
                       ServletUtilities.filter(input));
  }
}
