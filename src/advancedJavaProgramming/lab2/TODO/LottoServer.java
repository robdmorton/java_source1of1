package advancedJavaProgramming.lab2.TODO;

import java.util.*;
import java.net.*;
import java.io.*;

/**
 * Serves up lottery tickets Copyright (c) 2002 Objective Consulting
 * 
 * @author Phil "The Java Man" Stang
 * @author Ian Hlavats
 * @version 1.3
 * 
 */
public class LottoServer {

  // TODO 04. Make the handler implement Runnable
  private class ClientRequestHandler {

    private Socket socket = null;

    public ClientRequestHandler() {}

    public ClientRequestHandler(Socket clientSocket) {
      socket = clientSocket;
    }

    /**
     * Generate a lotto ticket. Returns a formatted string containing 6 numbers from 1 to 49.
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
          newNumber = (int) (numberGenerator.nextDouble() * (LottoProtocol.MAX_VALUE - 1) + 1);

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
            // TODO 08. Send the ticket back to the client.
            writer.println();
          }
        }
      } catch (IOException ioe) {
        System.err.println("Error processing the request");
        ioe.printStackTrace();
      } finally {
        try {
          if (reader != null) {
            // TODO 09. Close the reader.
          }
          if (writer != null) {
            // TODO 10. Close the writer.
          }
          if (socket != null) {
            // TODO 11. Close the client socket.
          }
        }
        // catch (IOException ioe) {
        // ioe.printStackTrace();
        // }
        finally {
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

  public LottoServer() {}

  private void acceptConnections() {
    Socket clientSocket = null;
    System.out.println("LottoServer 1.2");
    while (true) {
      try {
        // TODO 05. Establish a socket connetion with the client.
        clientSocket = null;
        // TODO 06. Create a new instance of the handler.
        ClientRequestHandler handler = null;
        handler.setSocket(clientSocket);
        // Thread thread = new Thread(handler);
        Thread thread = new Thread();
        // TODO 07. Start the new thread.
      }
      // catch (java.io.IOException e) {
      // System.err.println("Error setting up connection");
      // e.printStackTrace();
      // }
      finally {
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

}
