package advancedJavaProgramming.lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class simpleclient {
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

  public static void main(String[] arg) throws IOException {
    simpleclient sc = new simpleclient();
    sc.checkargs(arg);
    sc.runclient();
  }

  public void runclient() throws UnknownHostException, IOException {
    // try {
    socket = new Socket(host, portnum);
    in = socket.getInputStream();
    out = socket.getOutputStream();

    reader = new BufferedReader(new InputStreamReader(in));
    writer = new PrintWriter(out, true); // true for autoflush
    // }catch()
    while ((msg = reader.readLine()) != null)
      debug(true, "RECEIVED: " + msg);
  }

  public void checkargs(String[] cmdline) throws UnknownHostException {
    if (cmdline != null && cmdline.length >= 1) {
      debug(false, "arg0: " + cmdline[0] + " arg1: " + cmdline[1]);
      host = cmdline[0];
      if (cmdline.length > 1) {
        portnum = Integer.parseInt(cmdline[1]);
      }
    } else {
      // TODO 01. Use the default host/port from the LottoProtocol.
      host = defaulthost;
      portnum = defaultport;
    }
    debug(true, "host: " + host + " port: " + portnum);

  }

  public void debug(boolean b, String s) {
    if (b == true)
      System.out.println("SIMPLECLIENT: " + s);
  }
}
