
package conceptTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class BartClient {
  public static void main(String[] args) {
    int port = 1234;
    char[] readBuf = new char[1024 * 8];

    System.out.println("Welcome to the Bart Client\n");

    Socket s = getSocket(port);
    System.out.println("Client Socket: " + s.toString());

    try {
      System.out.println("Connected on port " + port);

      Scanner in = new Scanner(s.getInputStream());
      PrintWriter out;
      out = new PrintWriter(s.getOutputStream(), true);

      // discard the welcome message
      System.out.println(in.nextLine());

      // discard the exit instructions
      System.out.println(in.nextLine());

      // get a quote
      // out.println("get");
      out.print("get");
      out.flush();
      // Thread.sleep(1000);//in millis...
      String quote = null;
      BufferedReader is = new BufferedReader(new InputStreamReader(s.getInputStream()));
      int bytesRead = is.read(readBuf);
      if (bytesRead != -1) {
        quote = new String(readBuf);
      }
      // String quote = in.nextLine();
      Thread.sleep(60);

      // disconnect from the server
      out.println("bye");
      s.close();
      in.close();

      // write the quote on the chalkboard
      for (int i = 0; i < 20; i++)
        System.out.println(quote);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Socket getSocket(int port) {
    Socket s;
    String host;
    InetAddress ip;

    Scanner sc = new Scanner(System.in);

    while (true) {
      System.out.print("What server do you want to connect to?");
      host = sc.nextLine();
      try {
        ip = InetAddress.getByName(host);
        s = new Socket(ip, port);
        return s;
      } catch (UnknownHostException e) {
        System.out.println("The host is unknown.");
      } catch (IOException e) {
        System.out.println("Network error.");
      }
    }
  }
}
