package advancedJavaProgramming.lab2.TODO;

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
 * @version 1.3
 */

public class LottoClient {

    private InetAddress serverAddress;

    private int serverPort;

    public static void main(String[] args) {
        LottoClient lottoClient1 = null;
        lottoClient1.startClient(args);
    }

    public LottoClient() {
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

            // TODO 02. Send the ticket request command to the server
            String command = null;
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

        // TODO 03. Return the lottery ticket String.
        return null;
    }

    /**
     * configData[0] is the name or IP address of the server
     */
    private void processConfigData(String configData[]) {
        try {
            // set address of server
            if (configData != null && configData.length >= 1) {
                serverAddress = InetAddress.getByName(configData[0]);
                if (configData.length > 1) {
                    serverPort = Integer.parseInt(configData[2]);
                }
            } else {
                // TODO 01. Use the default host/port from the LottoProtocol.
                String host = null;
                serverAddress = InetAddress.getByName(host);
                serverPort = 0;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
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