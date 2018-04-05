package introductionToJava.lab2;

public class pet extends animal {
  String owner = null;

  public pet(String s) {
    super(s);
  }

  public void setowner(String s) { // set owner
    owner = s;
  }

  public String getowner() { // get owner
    return owner;
  }

  public void printowner() {
    System.out.println("The owner is: " + getowner());
  }

  public void speak() {
    System.out.println(" I am the pet speaking");
    super.speak();

  }
}
