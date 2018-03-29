package CST5301IntroductionToJava.lab7.sock;
import java.io.*;
import java.net.*;
public class SockMain {
  private static int counter = 0;

  public synchronized static int getNextNumber() {
    counter++;
    System.out.println("Now serving "+counter);
    return counter;
  }

  public static void main(String args[]) {
    try {
      ServerSocket ss = new ServerSocket(7001);
      while (true) {
        Socket client = ss.accept();
        // do something with the client
        SockThread st= new SockThread(client);
        st.start();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
