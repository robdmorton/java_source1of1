package advancedJavaProgramming.lab2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * A single-threaded client handler class.
 * 
 */
public class NumberHandler implements Runnable {

  private Socket client;

  private NumberServer server;

  /**
   * No-arg constructor for JavaBean compatibility.
   * 
   */
  public NumberHandler() {}

  public NumberHandler(NumberServer server, Socket client) {
    this.server = server;
    this.client = client;
  }

  public Socket getClient() {
    return client;
  }

  public NumberServer getServer() {
    return server;
  }

  public void handOutNumbers() {
    try {
      PrintStream out = new PrintStream(client.getOutputStream());
      BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
      out.println("How many numbers do you want?");
      String input = in.readLine();
      System.out.println("user asked for " + input + " numbers");
      int loop = 10; // default;
      try {
        int temp = Integer.parseInt(input);
        if (temp > 0 && temp < loop) {
          loop = temp;
        }
      } catch (NumberFormatException e1) {
        System.out.println("arggh");
        out.println("ask for a number between 1 and 10");
        out.flush();
        out.close();
        return;
      }

      for (int i = 0; i < loop; i++) {
        int num = server.getNextNumber();
        System.out.println("Handing out " + num + " to " + client.getRemoteSocketAddress());
        out.println(num);
        Thread.sleep(1000);
      }
      out.flush();
      out.close();
      in.close();
      client.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void run() {
    handOutNumbers();
  }

  public void setClient(Socket client) {
    this.client = client;
  }

  public void setServer(NumberServer server) {
    this.server = server;
  }
}
