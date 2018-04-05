package conceptTesting;

public class ClassForCustomLoading {
  private static B b = null;

  static {
    TestUtility.printer("static{}:Creating an instance of B: b...");
    b = new B();
  }

  public ClassForCustomLoading() {
    if (b == null) {
      TestUtility.printer("ClassForCustomLoading's constructor:Creating an instance of B: b...");
      b = new B();
    }
  }

  public void f1() {
    b.func();
  }
}
