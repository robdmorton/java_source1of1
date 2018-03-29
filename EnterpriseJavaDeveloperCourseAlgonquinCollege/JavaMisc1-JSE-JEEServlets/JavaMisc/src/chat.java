

/*
 * Created on Aug 10, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Bill
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;

public class chat {
	
	public static void main(String[] args) throws IOException {
		
		chat myChat = new chat();
		myChat.client("127.0.0.1", 7000, "Bill was here");
//		myChat.client("10.50.21.33", 7000, "Bill was here");
		
	}
    public void client(String t_host_string, int t_port_int, String t_message) throws IOException {
		/** Declare some variables for a new socket, plus its input and output streams */
		Socket t_port = null;
		PrintWriter t_portOut = null;
        BufferedReader t_portIn = null;
		/** Get a handle to a socket, and catch any exceptions that may occur */
        try {
            t_port = new Socket(t_host_string, t_port_int);
            t_portOut = new PrintWriter(t_port.getOutputStream(), true);
            t_portIn = new BufferedReader(new InputStreamReader(
                                        t_port.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + t_host_string);
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + t_host_string);
            e.printStackTrace();
            System.exit(1);
    }
    
    /** Write to our socket */
    String response = new String();
    t_portOut.println(t_message);
    
    /** Get our response */
    response = t_portIn.readLine();
	
    /** Print it to console */
    System.out.println(response);

    /** Close our socket out gracefully */
	t_portOut.close();
	t_portIn.close();
	t_port.close();
}

}

