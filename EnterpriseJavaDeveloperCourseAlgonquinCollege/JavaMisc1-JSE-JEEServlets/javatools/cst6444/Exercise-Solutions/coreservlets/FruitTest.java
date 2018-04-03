package coreservlets;

import java.sql.*;

/** A JDBC example that connects to either an Oracle or
 *  a Sybase database and prints out the values of
 *  predetermined columns in the "fruits" table.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class FruitTest {

  /** Reads the hostname, database name, username, password,
   *  and vendor identifier from the command line. It
   *  uses the vendor identifier to determine which
   *  driver to load and how to format the URL. The
   *  driver, URL, username, host, and password are then
   *  passed to the showFruitTable method.
   */
  
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
    showFruitTable(driver, url, username, password);
  }

  /** Get the table and print all the values. */
  
  public static void showFruitTable(String driver,
                                    String url,
                                    String username,
                                    String password) {
    try {
      // Load database driver if not already loaded.
      Class.forName(driver);
      // Establish network connection to database.
      Connection connection =
        DriverManager.getConnection(url, username, password);
      // Look up info about the database as a whole.
      DatabaseMetaData dbMetaData = connection.getMetaData();
      String productName =
        dbMetaData.getDatabaseProductName();
      System.out.println("Database: " + productName);
      String productVersion =
        dbMetaData.getDatabaseProductVersion();
      System.out.println("Version: " + productVersion + "\n");
      System.out.println("Comparing Apples and Oranges\n" +
                         "============================");
      Statement statement = connection.createStatement();
      String query = "SELECT * FROM fruits";
      // Send query to database and store results.
      ResultSet resultSet = statement.executeQuery(query);
      // Look up information about a particular table.
      ResultSetMetaData resultsMetaData =
        resultSet.getMetaData();
      int columnCount = resultsMetaData.getColumnCount();
      // Column index starts at 1 (a la SQL) not 0 (a la Java).
      for(int i=1; i<columnCount+1; i++) {
        System.out.print(resultsMetaData.getColumnName(i) +
                         "  ");
      }
      System.out.println();
      // Print results.
      while(resultSet.next()) {
        // Quarter
        System.out.print("    " + resultSet.getInt(1));
        // Number of Apples
        System.out.print("     " + resultSet.getInt(2));
        // Apple Sales
        System.out.print("   $" + resultSet.getFloat(3));
        // Number of Oranges
        System.out.print("    " + resultSet.getInt(4));
        // Orange Sales
        System.out.print("    $" + resultSet.getFloat(5));
        // Top Salesman
        System.out.println("      " + resultSet.getString(6));
      }
    } catch(ClassNotFoundException cnfe) {
      System.err.println("Error loading driver: " + cnfe);
    } catch(SQLException sqle) {
      System.err.println("Error connecting: " + sqle);
    }
  }

  private static void printUsage() {
    System.out.println("Usage: FruitTest host dbName " +
                       "username password oracle|sybase.");
  }
}
