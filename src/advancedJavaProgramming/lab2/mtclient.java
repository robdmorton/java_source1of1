package advancedJavaProgramming.lab2;
import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.OutputStream;
	import java.io.PrintWriter;
	import java.net.InetAddress;
	import java.net.Socket;
	import java.net.UnknownHostException;
	
public class mtclient implements Runnable{
		final String defaulthost = "localhost";
    final int defaultport = 1959;
		String host = defaulthost;
		int portnum = defaultport;
		Socket socket;
		InputStream in;
		OutputStream out;
		BufferedReader reader;
		PrintWriter writer;
		String msg;
		simpleclient sc;
		String tname;

		public static void main(String[] arg) throws IOException {
			new mtclient().runclient(arg);
			}
		public void runclient( String[] arg ) throws UnknownHostException{
			int loopcount=0;
			if( arg.length != 0)
				loopcount = Integer.parseInt(arg[0]);
			for( int i = 0 ; i < loopcount; i++){
				tname = "mythread-"+i;
				Thread t = new Thread(this, tname);
				t.start();
			}
		}
		public void run(){
			try{
			debug(true, "Connecting to server on thread: " + Thread.currentThread().getName());
			socket = new Socket(host, portnum);
			in = socket.getInputStream();
			out = socket.getOutputStream();

			reader = new BufferedReader(new InputStreamReader(in));
			writer = new PrintWriter(out, true); // true for autoflush
			
			while( (msg = reader.readLine())!= null)
				debug(true, "RECEIVED: " + msg );
			}catch( IOException ioe){
				System.out.println("IOERROR");
				ioe.printStackTrace();
			}
		}
		public void debug(boolean b, String s) {
			if (b == true)
				System.out.println("SIMPLECLIENT: " + s);
		}

}
