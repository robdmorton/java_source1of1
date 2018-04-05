package advancedJavaProgramming.lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {
  int defaultport = 1959;
  int portnum = defaultport;

  /** Server socket to bin to */
  ServerSocket ss;
  /** Socket to client side */
  Socket cs;

  /**
   * Basic Constructor
   * 
   * @throws IOException
   * @throws IOException
   */
  public MultiThreadServer() throws IOException {
    ss = new ServerSocket(1959);
  }

  public void runserver() throws IOException {
    debug(true, "Server Starting at port " + portnum);
    while (true) {
      cs = ss.accept();
      new serviceRequest(cs).start();
    }
  }

  public void debug(boolean b, String s) {
    if (b == true)
      System.out.println("MTSERVER: " + s);
  }

  /**
   * Command line entry point
   * 
   * @throws IOException
   */
  public static void main(String[] arg) throws IOException {
    new MultiThreadServer().runserver();
  }
}


/**
 * @author James
 *
 */
class serviceRequest extends Thread {
  /** Socket to client side */
  Socket cs;
  /** IO Channels */
  PrintWriter toClient;
  BufferedReader fromClient;

  public serviceRequest(Socket s) {
    cs = s;
  }

  /** Main work loop of server */
  public void run() {
    debug("Server Processing thread: " + getName());
    try {
      toClient = new PrintWriter(cs.getOutputStream(), true);
      fromClient = new BufferedReader(new InputStreamReader(cs.getInputStream()));
      toClient.println("Magic 8ball: All signs point to yes");
      cs.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    debug("Server Completed Processing thread: " + getName());
  }// end run

  public void debug(String s) {
    System.out.println("MTSERVER: " + s);
  }
}
