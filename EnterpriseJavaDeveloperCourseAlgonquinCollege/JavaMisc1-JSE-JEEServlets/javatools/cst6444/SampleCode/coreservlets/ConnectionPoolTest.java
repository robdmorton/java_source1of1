package coreservlets;

import java.sql.*;

public class ConnectionPoolTest implements Runnable {
  public static void main(String[] args) {
    if (args.length < 5) {
      printUsage();
      return;
    }
    String vendorName = args[4];
    int vendor = DriverUtilities.getVendor(vendorName);
    if (vendor == DriverUtilities.UNKNOWN) {
      printUsage();
      return;
    }
    String driver = DriverUtilities.getDriver(vendor);
    String host = args[0];
    String dbName = args[1];
    String url = DriverUtilities.makeURL(host, dbName, vendor);
    String username = args[2];
    String password = args[3];
    new ConnectionPoolTest(driver, url, username, password);
  }

  private ConnectionPool pool;
  
  public ConnectionPoolTest(String driver, String url,
                            String username, String password) {
    try {
      pool =
        new ConnectionPool(driver, url, username, password,
                           5, 20, true); 
      for(int i=0; i<25; i++) {
        Thread t = new Thread(this);
        t.start();
      }
    } catch(SQLException sqle) {
      System.err.println("Error building pool: " + sqle);
    }
  }

  public void run() {
    try {
      String query =
          "SELECT firstname, lastname, salary FROM employees " +
          "WHERE salary > 70000";
      for(int i=0; i<15; i++) {
        pause(5000);
        Connection connection = pool.getConnection();
        System.out.println("pool=" + pool);
        Statement statement = connection.createStatement();
        DBResults results =
          DatabaseUtilities.getQueryResults(connection,
                                            query, false);
        synchronized(pool) {
          DatabaseUtilities.printTableData("Rich Employees",
                                           results, 12, false);
        }
        pause(10000);
        //System.out.println("[run] about to free connection");
        pool.free(connection);
        //System.out.println("[run] freed connection, pool=" +
        //                   pool);
      }
    } catch(SQLException sqle) {
      System.err.println("Error: " + sqle);
    }
  }

  public void pause(int millis) {
    try {
      Thread.sleep((int)(Math.random()*millis));
    } catch(InterruptedException ie) {}
  }

  private static void printUsage() {
    System.out.println
      ("Usage: ConnectionPoolTest host dbName " +
       "username password oracle|sybase.");
  }
}
