
package conceptTesting;

import java.net.*;
// import java.util.*;
import java.io.*;

public class BinaryTransferClient {
  public static void main(String[] args) {
    System.out.println("Welcome to the Binary Transfer Client\n");
    int port = 1234;
    Socket s = getSocket(port);
    System.out.println("Client Socket: " + s.toString());
    byte[] b = new byte[1024 * 8];
    String quote = "dummy";

    try {
      System.out.println("Connected on port " + port);
      s.setSoTimeout(3);

      // discard the welcome messages
      try {
        java.util.Arrays.fill(b, (byte) 0);
        int bytes_read = s.getInputStream().read(b);
        if (bytes_read != -1) {
          byte[] bc = new byte[bytes_read];
          System.arraycopy(b, 0, bc, 0, bytes_read);
          String str = new String(bc, "Cp1252");
          System.out.print(str);
        }
      } catch (Exception e) {
        System.out.println("******* 1-caught exception: " + e.getMessage());
        // e.printStackTrace();
      }

      try {
        java.util.Arrays.fill(b, (byte) 0);
        int bytes_read = s.getInputStream().read(b);
        if (bytes_read != -1) {
          byte[] bc = new byte[bytes_read];
          System.arraycopy(b, 0, bc, 0, bytes_read);
          String str = new String(bc, "UTF-8");
          System.out.print(str);
        }
      } catch (Exception e) {
        System.out.println("******* 2-caught exception: " + e.getMessage());
        // e.printStackTrace();
      }

      try {
        // get a quote
        System.out.println("requesting getint");
        s.getOutputStream().write("getint".getBytes("UTF-8"));
        s.getOutputStream().flush();
        java.util.Arrays.fill(b, (byte) 0);
        int bytes_read = s.getInputStream().read(b);
        if (bytes_read != -1) {
          System.out.println("getint bytes_read: " + bytes_read);
          byte[] bc = new byte[bytes_read];
          System.arraycopy(b, 0, bc, 0, bytes_read);
          // String str = new String(bc, "UTF-8");
          int i = java.nio.ByteBuffer.wrap(bc).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
          Integer I = new Integer(i);
          quote = I.toString();
        }
      } catch (Exception e) {
        System.out.println("******* 3-caught exception: " + e.getMessage());
        // e.printStackTrace();
      }

      // write the quote on the chalkboard
      for (int i = 0; i < 3; i++) {
        System.out.println(quote);
      }

      try {
        // get a quote
        System.out.println("requesting getstring");
        s.getOutputStream().write("getstring".getBytes("UTF-8"));
        s.getOutputStream().flush();
        java.util.Arrays.fill(b, (byte) 0);
        int bytes_read = s.getInputStream().read(b);
        if (bytes_read != -1) {
          System.out.println("getstring bytes_read: " + bytes_read);
          byte[] bc = new byte[bytes_read];
          System.arraycopy(b, 0, bc, 0, bytes_read);
          String str = new String(bc, "UTF-8");
          quote = str;
        }
      } catch (Exception e) {
        System.out.println("******* 4-caught exception: " + e.getMessage());
        // e.printStackTrace();
      }

      // write the quote on the chalkboard
      for (int i = 0; i < 3; i++) {
        System.out.println(quote);
      }

      try {
        // get a quote
        System.out.println("requesting getboolean");
        s.getOutputStream().write("getboolean".getBytes("UTF-8"));
        s.getOutputStream().flush();
        java.util.Arrays.fill(b, (byte) 0);
        // ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        // s.setSoTimeout(3);
        // Boolean aBoolIn = ois.readBoolean();
        // quote = aBoolIn.toString();
        int bytes_read = s.getInputStream().read(b);
        if (bytes_read != -1) {
          System.out.println("getboolean bytes_read: " + bytes_read);
          byte[] bc = new byte[bytes_read];
          System.arraycopy(b, 0, bc, 0, bytes_read);
          Boolean bool = new Boolean(((bc[0] == 0x01) ? true : false));
          quote = bool.toString();
        }
      } catch (Exception e) {
        System.out.println("******* 5-caught exception: " + e.getMessage());
        // e.printStackTrace();
      }

      // write the quote on the chalkboard
      for (int i = 0; i < 3; i++) {
        System.out.println(quote);
      }

      try {
        // get a quote
        System.out.println("requesting getshort");
        s.getOutputStream().write("getshort".getBytes("UTF-8"));
        s.getOutputStream().flush();
        java.util.Arrays.fill(b, (byte) 0);
        int bytes_read = s.getInputStream().read(b);
        if (bytes_read != -1) {
          System.out.println("getshort bytes_read: " + bytes_read);
          byte[] bc = new byte[bytes_read];
          System.arraycopy(b, 0, bc, 0, bytes_read);
          short i = java.nio.ByteBuffer.wrap(bc).order(java.nio.ByteOrder.LITTLE_ENDIAN).getShort();
          Short I = new Short(i);
          quote = I.toString();
        }
      } catch (Exception e) {
        System.out.println("******* 6-caught exception: " + e.getMessage());
        // e.printStackTrace();
      }

      // write the quote on the chalkboard
      for (int i = 0; i < 3; i++) {
        System.out.println(quote);
      }

      try {
        // get a quote
        System.out.println("requesting getfloat");
        s.getOutputStream().write("getfloat".getBytes("UTF-8"));
        s.getOutputStream().flush();
        java.util.Arrays.fill(b, (byte) 0);
        int bytes_read = s.getInputStream().read(b);
        if (bytes_read != -1) {
          System.out.println("getfloat bytes_read: " + bytes_read);
          byte[] bc = new byte[bytes_read];
          System.arraycopy(b, 0, bc, 0, bytes_read);
          float f = java.nio.ByteBuffer.wrap(bc).order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
          Float F = new Float(f);
          quote = F.toString();
        }
      } catch (Exception e) {
        System.out.println("******* 7-caught exception: " + e.getMessage());
        // e.printStackTrace();
      }

      // write the quote on the chalkboard
      for (int i = 0; i < 3; i++) {
        System.out.println(quote);
      }

      try {
        // get a quote
        System.out.println("requesting getdouble");
        s.getOutputStream().write("getdouble".getBytes("UTF-8"));
        s.getOutputStream().flush();
        java.util.Arrays.fill(b, (byte) 0);
        int bytes_read = s.getInputStream().read(b);
        if (bytes_read != -1) {
          System.out.println("getdouble bytes_read: " + bytes_read);
          byte[] bc = new byte[bytes_read];
          System.arraycopy(b, 0, bc, 0, bytes_read);
          double d =
              java.nio.ByteBuffer.wrap(bc).order(java.nio.ByteOrder.LITTLE_ENDIAN).getDouble();
          Double D = new Double(d);
          quote = D.toString();
        }
      } catch (Exception e) {
        System.out.println("******* 8-caught exception: " + e.getMessage());
        // e.printStackTrace();
      }

      // write the quote on the chalkboard
      for (int i = 0; i < 3; i++) {
        System.out.println(quote);
      }

      // disconnect from the server
      s.getOutputStream().write("disconnect".getBytes("UTF-8"));
      s.getOutputStream().flush();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        s.close();
      } catch (IOException e) {
      }
    }
  }

  private static Socket getSocket(int port) {
    Socket s;
    String host;
    InetAddress ip;

    // Scanner sc = new Scanner(System.in);

    while (true) {
      // System.out.print(
      // "What server do you want to connect to?");
      // host = sc.nextLine();
      host = "localhost";
      try {
        ip = InetAddress.getByName(host);
        s = new Socket(ip, port);
        return s;
      } catch (UnknownHostException e) {
        System.out.println("The host is unknown.");
      } catch (IOException e) {
        System.out.println("Network error.");
      }
    }
  }
}
