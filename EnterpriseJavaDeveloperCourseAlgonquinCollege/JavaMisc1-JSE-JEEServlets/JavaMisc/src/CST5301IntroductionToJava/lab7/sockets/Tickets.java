package CST5301IntroductionToJava.lab7.sockets;
// imports
import java.io.*;

public class Tickets
{

// instance variables
int nextTicket = 1;

// methods
  public synchronized int getNext() {
    return nextTicket++;
  }

  public void handOutTickets(InputStream in, OutputStream out) {
    try {
      int howMany =1;
      BufferedReader i;
      PrintStream o;
      i = new BufferedReader(new InputStreamReader(in));
      o = new PrintStream(out);
      String s = i.readLine();
      howMany = Integer.parseInt(s);
      for (int n = 0; n < howMany; n++) {
        int nt = getNext();
        o.println("Ticket number " +nt);
        System.out.println( "Ticket number " + nt);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

// test code (internal verison of main()

  public static void main(String[] args)
  {
    Tickets t = new Tickets();
    t.handOutTickets(System.in,System.out);
  }
}
