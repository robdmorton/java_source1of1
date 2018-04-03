package advancedJavaProgramming.lab2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class SingleThreadServer {
	/** Server socket to bin to */
	ServerSocket ss;
	/** Socket to client side */
	Socket cs;
	/** IO Channels */
	PrintWriter toClient;
	BufferedReader fromClient;
	/** Basic Constructor 
	 * @throws IOException */
	public SingleThreadServer() throws IOException{
		ss = new ServerSocket(1959);
	}
	/** Main work loop of server */
	public void run(){
		debug("Server ready");
		try {
			cs = ss.accept();
			toClient = new PrintWriter(cs.getOutputStream(), true);
			fromClient = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			toClient.println("Magic 8ball: All signs point to yes");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// end run
	
	/** Command line entry point 
	 * @throws IOException */
	public static void main(String[] arg) throws IOException{
		new SingleThreadServer().run();
	}
	public void debug(String s){
		System.out.println("STSERVER: " + s );
	}
}
