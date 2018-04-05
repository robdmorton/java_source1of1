package advancedJavaProgramming.lab2;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class LottoClientMT extends Thread {

  private InetAddress serverAddress;
  private int serverPort;
  private static int ctr = 0;
  String[] myargs;

  public static void main(String[] args) {
    int numticketsrequested = Integer.parseInt("50");

    for (int i = 0; i < numticketsrequested; i++) {
      LottoClientMT lc = new LottoClientMT();
      lc.sethostandport(args);
      lc.start();
    }
  }

  public void run() {
    ctr++;
    String lottoTicket = getTicketFromServer();
    System.out.println(ctr + " Client Thread: " + getName() + " " + lottoTicket);
  }

  private String getTicketFromServer() {
    String lotteryTicket = null;
    BufferedReader reader = null; // to get response from server
    PrintWriter writer = null; // to send request to server
    try {
      Socket socket = new Socket(serverAddress, serverPort);
      InputStream in = socket.getInputStream();
      OutputStream out = socket.getOutputStream();

      reader = new BufferedReader(new InputStreamReader(in));
      writer = new PrintWriter(out, true); // true for autoflush

      // TODO 02. Fixed Send the ticket request command to the server
      String command = LottoProtocol.TICKET_REQUEST_COMMAND;
      writer.println(command);

      lotteryTicket = reader.readLine(); // read the response

    } catch (java.io.IOException e) {
      System.err.println("Error getting ticket");
      e.printStackTrace();
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
        if (writer != null) {
          writer.close();
        }
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    // TODO 03. Fixed Return the lottery ticket String.
    return lotteryTicket;
  }

  /**
   * configData[0] is the name or IP address of the server
   */
  private void sethostandport(String configData[]) {
    try {
      // set address of server
      if (configData != null && configData.length >= 1) {
        serverAddress = InetAddress.getByName(configData[0]);
        if (configData.length > 1) {
          serverPort = Integer.parseInt(configData[1]);
        }
      } else {
        // TODO 01. Fixed Use the default host/port from the LottoProtocol.
        String host = LottoProtocol.SERVER_HOST;
        serverAddress = InetAddress.getByName(host);
        serverPort = LottoProtocol.SERVER_PORT;
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (java.net.UnknownHostException e) {
      e.printStackTrace();
    }
  }
}
