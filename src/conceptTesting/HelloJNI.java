package conceptTesting;

import com.sun.jna.Native;

public class HelloJNI {
  private static Kernel32 kernel32 = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);

  static {
    // how to point to where the dll is located...see run configurations->arguments
    // e.g.
    // -Djava.library.path=I:\eclipseWorkspace\java_source1of1\jni\
    System.loadLibrary("hello"); // hello.dll (Windows) or libhello.so (Unixes)
    TestUtility.printer("hello.dll loaded successfully...");
    System.loadLibrary("convert"); // hello.dll (Windows) or libhello.so (Unixes)
    TestUtility.printer("convert.dll loaded successfully...");
  }

  // A native method that receives nothing and returns void
  // private native void sayHello();
  private native void sayHello();

  private native void convert(int argc, String incmd, String inoperand, String infile);

  private static void toMorseCode(String letter) throws Exception {
    for (byte b : letter.getBytes()) {
      kernel32.Beep(1200, ((b == '.') ? 50 : 150));
      Thread.sleep(1);
    }
  }

  public static void mainJNICall(int argc, String incmd, String inoperand, String infile) {
    TestUtility.printer("calling native method(via JNI)...Java_conceptTesting_HelloJNI_sayHello");
    new HelloJNI().sayHello(); // invoke the native method
    TestUtility.printer("called native method...");

    TestUtility.printer("calling native method(via JNI)...Java_conceptTesting_HelloJNI_convert");
    new HelloJNI().convert(argc, incmd, inoperand, infile); // invoke the native method
    TestUtility.printer("called native method...");

    TestUtility.printer("calling native method(via JNA)...hello");
    HelloJNA helloJNA = (HelloJNA) Native.loadLibrary("helloJNA", HelloJNA.class);
    helloJNA.hello(); // invoke the native method
    TestUtility.printer("called native method(via JNA)...");
    //
    // TestUtility.printer("calling native method(via JNA)...MessageBeep");
    // User32 user32 = (User32)Native.loadLibrary("user32", User32.class);
    // user32.MessageBeep(2); // invoke the native method
    // TestUtility.printer("called native method(via JNA)...");

    TestUtility.printer("calling native method(via JNA)...Beep");
    String helloWorld[][] = {{"....", ".", ".-..", ".-..", "---"}, // HELLO
        {".--", "---", ".-.", ".-..", "-.."} // WORLD
    };

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      for (String word[] : helloWorld) {
        for (String letter : word) {
          toMorseCode(letter);
          Thread.sleep(100);
        }
        Thread.sleep(500);
      }
    } catch (Throwable t) {
    }
    TestUtility.printer("called native method(via JNA)...");
  }
}
