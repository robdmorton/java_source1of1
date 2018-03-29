package advancedJavaProgramming.lab2;


import java.util.*;
import java.net.*;
import java.io.*;


public class LottoServer {

    // TODO 04. Make the handler implement Runnable
    private class ClientRequestHandler implements Runnable{

        private Socket socket = null;

       // public ClientRequestHandler() {
      //  }

        public ClientRequestHandler(Socket clientSocket) {
            socket = clientSocket;
        }

        /**
         * Generate a lotto ticket. Returns a formatted string containing 6
         * numbers from 1 to 49.
         */
        private String getTicket() {
            int theNumbers[] = new int[LottoProtocol.NUM_OF_NUMBERS];

            for (int i = 0; i < LottoProtocol.NUM_OF_NUMBERS; i++) {
                int newNumber;
                boolean isDuplicate = false;
                do {
                    isDuplicate = false; // reset isDuplicate, in case it has
                    // been set to true

                    // we want the numbers to be in the range of 1 to maxValue
                    // 0 to 48, and then add 1 to make it 1 to 49
                    newNumber = (int) (numberGenerator.nextDouble()
                            * (LottoProtocol.MAX_VALUE - 1) + 1);

                    // Compare to the numbers entered previously, to make sure
                    // it is
                    // not a duplicate.
                    // If it is, then ignore it, and get a new random number.
                    for (int j = 0; j < i && !isDuplicate; j++) {
                        if (newNumber == theNumbers[j]) {
                            isDuplicate = true;
                        }
                    }
                } while (isDuplicate);

                theNumbers[i] = newNumber;
            }
            Arrays.sort(theNumbers);
            StringBuffer sb = new StringBuffer();
            sb.append("Your numbers are: ");
            for (int k = 0; k < LottoProtocol.NUM_OF_NUMBERS; k++) {
                sb.append(String.valueOf(theNumbers[k]) + " ");
            }
            return sb.toString();
        }

        private void handleClientRequests() {
            BufferedReader reader = null; // to read client requests
            PrintWriter writer = null; // to send back response
            String request;

            try {
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                reader = new BufferedReader(new InputStreamReader(in));
                writer = new PrintWriter(out, true); // true for autoflush

                request = reader.readLine(); // read client request

                if (request != null) {
                    String command = LottoProtocol.TICKET_REQUEST_COMMAND;
                    if (request.equalsIgnoreCase(command)) {
                        String ticket = getTicket();
                        // TODO 08. Fixed Send the ticket back to the client.
                        debug( true,Thread.currentThread().getName() );
                        writer.println(ticket); 
                    }
                }
            } catch (IOException ioe) {
                System.err.println("Error processing the request");
                ioe.printStackTrace();
            } finally {
                try {
                    if (reader != null) {
                        // TODO 09. Fixed Close the reader.
                    	reader.close();
                    }
                    if (writer != null) {
                        // TODO 10. Fixed Close the writer.
                    	writer.close();
                    }
                    if (socket != null) {
                        // TODO 11. Fixed Close the client socket.
                    	socket.close();
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

        public void run() {
            handleClientRequests();
        }

        public Socket getSocket() {
            return socket;
        }

        public void setSocket(Socket socket) {
            this.socket = socket;
        }
    }

    public static void main(String[] args) {
        LottoServer lottoServer1 = new LottoServer();
        lottoServer1.startServer();
    }

    private Random numberGenerator = new Random(System.currentTimeMillis());

    private ServerSocket serverSocket = null;

    public LottoServer() {
    }

    private void acceptConnections() {
        Socket clientSocket = null;
        System.out.println("LottoServer 1.2");
        while (true) {
            try {
                // TODO 05. Fixed Establish a socket connection with the client.
                clientSocket = serverSocket.accept();;
                // TODO 06. Fixed Create a new instance of the handler.
                ClientRequestHandler handler = new ClientRequestHandler(clientSocket);
                handler.setSocket(clientSocket);
                Thread thread = new Thread(handler);
                thread.setName("luckylotto-"+ thread.getName() );
                // TODO 07. Fixed Start the new thread.
                thread.start();
            } catch (java.io.IOException e) {
                System.err.println("Error setting up connection");
                e.printStackTrace();
            }
        }
    }

    private void createServerSocket() {
        try {
            serverSocket = new ServerSocket(LottoProtocol.SERVER_PORT);
        } catch (java.io.IOException e) {
            String error = "Error setting up connection" + e.getMessage();
            System.err.println(error);
        }
    }

    public void startServer() {
        createServerSocket();
        acceptConnections();
    }
    public void debug( boolean b , String s){
    	if( b == true )
    		System.out.println("SERVER: " + s);
    }
    

}