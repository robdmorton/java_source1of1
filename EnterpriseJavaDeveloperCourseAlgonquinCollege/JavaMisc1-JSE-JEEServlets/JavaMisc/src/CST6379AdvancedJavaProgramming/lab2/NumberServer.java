package CST6379AdvancedJavaProgramming.lab2;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * A server that uses a single-threaded request handler class to process client
 * requests. It listens on port 9000 for incoming connections. To test this class
 * using telnet, run the following command from a shell: telnet localhost 9000
 * 
 */
public class NumberServer {

    private int nextNumber = 0;

    public int getNextNumber() {
        return nextNumber++;
    }

    /**
     * This method listens for incoming connections and hands off the client
     * sockets to another class for processing.
     * 
     */
    public void listen() {
        try {
            ServerSocket s = new ServerSocket(9000);
            while (true) {
                Socket client = s.accept();
                NumberHandler handler = new NumberHandler();
                handler.setClient(client);
                handler.setServer(this);
                Thread t = new Thread(handler);
                t.start();                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NumberServer server = new NumberServer();
        server.listen();
    }
}
