package CST5301IntroductionToJava.lab7.socket;
import java.io.*;
import java.net.*;

public class SockThread extends Thread {
  private Socket sock;

  public SockThread(Socket sock) {
    this.sock = sock;
  }
    public void run() {
      try {
        OutputStream os = sock.getOutputStream();
        PrintStream ps = new PrintStream(os);
        InputStream is = sock.getInputStream();
        BufferedReader r = new BufferedReader(
            new InputStreamReader(is));
        String numAsString = r.readLine();
        System.out.println("User asked for :"+numAsString+" numbers");
        int num = 0;
        try {
          num = Integer.parseInt(numAsString);
        }
        catch(Exception f) {
          ps.println("go away - I want numbers");
          return;
        }
        for (int i = 0; i < num; i++) {
          ps.println(SockTest.getNumber());
        }
        ps.flush();
        ps.close();
        sock.close();
      }
      catch(IOException e){

      }

 }

}