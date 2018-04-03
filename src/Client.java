


import java.io.*;
import java.net.*;
import java.util.*;

public class Client implements Runnable{

  public static Vector strings = new Vector();
  Socket sock;

  Client(Socket sock) {
    this.sock = sock;
  }

  public void run(){
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
      PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())));
      int index = strings.size();
      String inString = in.readLine();
      while(true && inString != null) {
        System.out.println("got:"+inString);
        strings.add(inString);
        out.println("congrats "+inString+" you are number "+index+1);
        for (int i = 0; i < strings.size(); i++) {
          out.println(strings.elementAt(i));
        }
        out.flush();
        inString = in.readLine();
      }
    } catch(Exception e) {}
  }
}
