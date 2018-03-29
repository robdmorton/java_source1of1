package CST5301IntroductionToJava.lab7.sock;
import java.io.*;
import java.net.*;

public class SockThread extends Thread{
  Socket client;
  public SockThread(Socket client) {
    this.client = client;
  }

  public void run() {
    boolean done = false;
    BufferedReader in = null;
    PrintStream out = null;
    String numString = null;
    int howMany = 0;
    try {
      in = new BufferedReader(new InputStreamReader(client.getInputStream()));
      out = new PrintStream(client.getOutputStream());
      numString = in.readLine();
      try {
        howMany = Integer.parseInt(numString);
        if(howMany < 0 || howMany > 1000) {
          howMany = 1;
        }
      }
      catch (Exception pe) {
        out.println("Ni!");
        done = true;
      }
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
      done = true;
    }
    while (!done) {
      for (int i = 0; i < howMany; i++ ) {
        out.println(SockMain.getNextNumber());
      }
      done = true;
    }
    try {
      in.close();
      out.close();
    }
    catch (IOException ioe2) {
      ioe2.printStackTrace();
    }
  }
}
