package CST6379AdvancedJavaProgramming.lab1;

import java.util.*;
import java.net.*;
import java.io.*;

/**
 * Serves up lottery tickets Copyright (c) 2002 Objective Consulting
 * 
 * @author Phil "The Java Man" Stang
 * @author Ian Hlavats
 * @version 1.2
 * 
 */
public class LottoServerTCP {

    private static int MAX_VALUE = 49;

    private static int NUM_OF_NUMBERS = 6;

    private static int SERVER_PORT = 5123;

    private static final String TICKET_REQUEST_COMMAND = "GetTicket";

    public static void main(String[] args) {
        LottoServerTCP lottoServer1 = new LottoServerTCP();
        lottoServer1.startServer();
        // lottoServer1.testServer();
    }

    private Random numberGenerator = new Random(System.currentTimeMillis());

    private ServerSocket serverSocket = null;

    public LottoServerTCP() {
    }

    /**
     * This method can be used for incremental testing.
     * 
     */
    public void testServer() {
        System.out.println(this.getTicket());
        System.out.println(this.getTicket());
        System.out.println(this.getTicket());
    }

    private void createServerSocket() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (java.io.IOException e) {
            String error = "Error setting up connection" + e.getMessage();
            System.err.println(error);
        }
    }

    /**
     * Generate a lotto ticket. Returns a formatted string containing 6 numbers
     * from 1 to 49.
     */
    private String getTicket() {
        int lotteryNumbers[] = new int[NUM_OF_NUMBERS];

        for (int i = 0; i < NUM_OF_NUMBERS; i++) {
            int newNumber;
            boolean isDuplicate = false;
            do {
                isDuplicate = false; // reset the duplicate flag

                // numbers should be in the range of 0 to 48
                // then add 1 to make it 1 to 49
                double randomNumber = numberGenerator.nextDouble();
                newNumber = (int) (randomNumber * (MAX_VALUE - 1) + 1);

                // Compare to the numbers entered previously to make sure it is
                // not a duplicate.
                // If it is, then ignore it, and get a new random number.
                for (int j = 0; j < i && !isDuplicate; j++) {
                    if (newNumber == lotteryNumbers[j]) {
                        isDuplicate = true;
                    }
                }

            } while (isDuplicate);

            lotteryNumbers[i] = newNumber;
        }

        // sort and build the result
        Arrays.sort(lotteryNumbers);
        StringBuilder sb = new StringBuilder();
        sb.append("Your numbers are: ");
        for (int k = 0; k < NUM_OF_NUMBERS; k++) {
            String value = String.valueOf(lotteryNumbers[k]);
            sb.append(value);
            sb.append(" ");
        }
        String result = sb.toString();
        return result;
    }

    private void handleClientRequests(Socket skt) {
        String request;
        BufferedReader reader = null; // to read client requests
        PrintWriter writer = null; // to send back response

        try {
            InputStream in = skt.getInputStream();
            OutputStream out = skt.getOutputStream();

            reader = new BufferedReader(new InputStreamReader(in));
            writer = new PrintWriter(out, true); // true for autoflush

            request = reader.readLine(); // read client request

            if (request.equalsIgnoreCase(TICKET_REQUEST_COMMAND)) {
                writer.println(getTicket()); // process the request
            }
        } catch (java.io.IOException e) {
            System.err.println("Error setting up connection:");
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
    }

    private void acceptConnections() {
        Socket clientSocket;
        while (true) {
            try {
                // accept() blocks until client connects
                clientSocket = serverSocket.accept();
                handleClientRequests(clientSocket);
                clientSocket.close();
            } catch (java.io.IOException e) {
                System.err.println("Error setting up connection");
                e.printStackTrace();
            }
        }
    }

    public void startServer() {
        createServerSocket();
        acceptConnections();
    }
}