package advancedJavaProgramming.lab1.TODO;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Requests a lottery ticket from a lotto ticket server. Copyright (c) 2002
 * Objective Consulting
 * 
 * @author Phil "Da Java Man" Stang
 * @author Ian Hlavats
 * @version 1.2
 */

public class LottoClientTCP {

    private static int SERVER_PORT = 5123;

    private static final String TICKET_REQUEST_COMMAND = "GetTicket";

    public static void main(String[] args) {
        // TODO 01. Create a new instance of the LottoClient class.
        LottoClientTCP lottoClient1 = null;
        lottoClient1.startClient(args);
    }

    private InetAddress serverAddress;

    public LottoClientTCP() {
    }

    private String getTicketFromServer() {
        String lotteryTicket = null;
        BufferedReader reader = null; // to get response from server
        PrintWriter writer = null; // to send request to server
        try {
            // TODO 02. Create a new CONNECTED instance of the Socket class.
            Socket socket = null;
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            reader = new BufferedReader(new InputStreamReader(in));
            writer = new PrintWriter(out, true); // true for autoflush

            // TODO 03. Send (println) the ticket request command to the server.
            
            
            // TODO 04. Read the response from the server.
            lotteryTicket = null;
            
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
        return lotteryTicket;
    }

    /**
     * configData[0] is the name or IP address of the server
     */
    private void processConfigData(String configData[]) {
        try {
            // set address of server
            if (configData != null && configData.length >= 1) {
                serverAddress = InetAddress.getByName(configData[0]);
            } else {
                // default case is that the server is running on the same
                // machine which is local host (which is 127.0.0.1)
                serverAddress = InetAddress.getLocalHost();
            }
        } catch (java.net.UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void startClient(String args[]) {
        processConfigData(args);
        String lottoTicket = getTicketFromServer();
        System.out.println(lottoTicket);
    }
}