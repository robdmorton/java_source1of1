package CST5301IntroductionToJava.lab7.socket;
import java.io.*;
import java.net.*;

public class SockTest {
  private static int number = 0;

  public static synchronized int getNumber() {
    System.out.println("handing out number "+number);
    return number++;
  }
  public SockTest() {
    try {
      ServerSocket socket = new ServerSocket(6000);
      while (true) {
        Socket server = socket.accept();
        SockThread thread = new SockThread(server);
        thread.start();
      }
    }
    catch(IOException e) {
      e.printStackTrace();
    }

  }
  public static void main(String args[]) {
    SockTest test = new SockTest();
  }
}