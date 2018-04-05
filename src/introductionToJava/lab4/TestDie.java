package introductionToJava.lab4;

public class TestDie {

  /**
   * @param args
   */
  public static void main(String[] args) {
    die side8 = new die(8);

    for (int i = 0; i < 10; i++) {
      System.out.println(side8.roll());
    }
    System.out.println("\nTesting 3 sided die");
    die side3 = new die(3);

    for (int i = 0; i < 10; i++) {
      System.out.println(side3.roll());
    }

  }

}
