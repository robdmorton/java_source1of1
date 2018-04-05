package intermediateJavaProgramming.lab7.WOS2;

public class testGourds {

  public static void main(String[] args) {
    trail dummy = new trail(0, 100);
    for (int i = 0; i < 100; i++) {
      System.out.println(dummy.distributeGourds(0, 100));
      System.out.println(dummy.sGourds);
      dummy.sGourds.clear();
    }
  }
}
