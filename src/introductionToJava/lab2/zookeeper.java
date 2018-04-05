package introductionToJava.lab2;

public class zookeeper {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    /*
     * animal a = new animal("unicorn"); a.speak(); animal b = new animal("cow"); b.speak();
     */
    pet p = new pet("hog");
    p.setowner("me");
    p.speak();
    p.printowner();
  }

}
