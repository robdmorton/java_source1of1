package CST5301IntroductionToJava.lab7.sockets;
// imports
import java.io.*;
import java.net.*;

public class TicketBooth extends Thread
{

// instance variables
   InputStream in;
   OutputStream out;
   static Tickets t =null;
// methods

  public TicketBooth(InputStream i,OutputStream o)
  {
     this.in = i;
     this.out = o;
  }

  public void run()
  {
    try {
    if (t == null) {
      t = new Tickets();
    }
    t.handOutTickets(in,out);
    in.close();
    out.close();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

// thread code

// main method
  public static void main(String[] args)
  {
    try {
     ServerSocket ss = new ServerSocket(6000);
     while (true) {
       Socket client = ss.accept();
       InputStream in = client.getInputStream();
       OutputStream out = client.getOutputStream();
       TicketBooth t = new TicketBooth(in,out);
       t.start();
     }
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

}
