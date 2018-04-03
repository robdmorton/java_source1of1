


import java.net.*;
import java.io.*;

public class Serve {

  public static void main(String[] args) throws Exception{
    ServerSocket s = new ServerSocket(7000);
    while(true) {
      Socket c = s.accept();
      System.out.println("got connection");
      Client cl = new Client(c);
      Thread t = new Thread(cl);
      t.start();
    }
  }
}
