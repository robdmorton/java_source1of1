package coreservlets;

import java.sql.*;

/** An example to test the timing differences resulting
 *  from repeated raw queries vs. repeated calls to
 *  prepared statements. These results will vary dramatically
 *  among database servers and drivers. With my setup
 *  and drivers, Oracle prepared statements took only half
 *  the time that raw queries required when using a modem
 *  connection, and took only 70% of the time that
 *  raw queries required when using a fast LAN connection.
 *  Sybase times were identical in both cases.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class PreparedStatements {
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
    String url =
      DriverUtilities.makeURL(host, dbName, vendor);
    String username = args[2];
    String password = args[3];
    // Use "print" only to confirm it works properly,
    // not when getting timing results.
    boolean print = false;
    if ((args.length > 5) && (args[5].equals("print"))) {
      print = true;
    }
    Connection connection =
      getConnection(driver, url, username, password);
    if (connection != null) {
      doPreparedStatements(connection, print);
      doRawQueries(connection, print);
    }
  }

  private static void doPreparedStatements(Connection conn,
                                           boolean print) {
    try {
      String queryFormat =
        "SELECT lastname FROM employees WHERE salary > ?";
      PreparedStatement statement =
        conn.prepareStatement(queryFormat);
      long startTime = System.currentTimeMillis();
      for(int i=0; i<40; i++) {
        statement.setFloat(1, i*5000);
        ResultSet results = statement.executeQuery();
        if (print) {
          showResults(results);
        }
      }
      long stopTime = System.currentTimeMillis();
      double elapsedTime = (stopTime - startTime)/1000.0;
      System.out.println("Executing prepared statement " +
                         "40 times took " +
                         elapsedTime + " seconds.");
    } catch(SQLException sqle) {
      System.out.println("Error executing statement: " + sqle);
    }
  }

  public static void doRawQueries(Connection conn,
                                  boolean print) {
    try {
      String queryFormat =
        "SELECT lastname FROM employees WHERE salary > ";
      Statement statement = conn.createStatement();
      long startTime = System.currentTimeMillis();
      for(int i=0; i<40; i++) {
        ResultSet results =
          statement.executeQuery(queryFormat + (i*5000));
        if (print) {
          showResults(results);
        }
      }
      long stopTime = System.currentTimeMillis();
      double elapsedTime = (stopTime - startTime)/1000.0;
      System.out.println("Executing raw query " +
                         "40 times took " +
                         elapsedTime + " seconds.");
    } catch(SQLException sqle) {
      System.out.println("Error executing query: " + sqle);
    }
  } 

  private static void showResults(ResultSet results)
      throws SQLException {
    while(results.next()) {
      System.out.print(results.getString(1) + " ");
    }
    System.out.println();
  }
    
  private static Connection getConnection(String driver,
                                          String url,
                                          String username,
                                          String password) {
    try {
      Class.forName(driver);
      Connection connection =
        DriverManager.getConnection(url, username, password);
      return(connection);
    } catch(ClassNotFoundException cnfe) {
      System.err.println("Error loading driver: " + cnfe);
      return(null);
    } catch(SQLException sqle) {
      System.err.println("Error connecting: " + sqle);
      return(null);
    }
  }

  private static void printUsage() {
    System.out.println("Usage: PreparedStatements host " +
                       "dbName username password " +
                       "oracle|sybase [print].");
  }
}
