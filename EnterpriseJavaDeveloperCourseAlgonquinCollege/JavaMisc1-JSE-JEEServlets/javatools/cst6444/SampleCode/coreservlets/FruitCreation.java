package coreservlets;

import java.sql.*;

/** Creates a simple table named "fruits" in either
 *  an Oracle or a Sybase database.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class FruitCreation {
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
    String format =
      "(quarter int, " +
      "apples int, applesales float, " +
      "oranges int, orangesales float, " +
      "topseller varchar(16))";
    String[] rows =
    { "(1, 32248, 3547.28, 18459, 3138.03, 'Maria')",
      "(2, 35009, 3850.99, 18722, 3182.74, 'Bob')",
      "(3, 39393, 4333.23, 18999, 3229.83, 'Joe')",
      "(4, 42001, 4620.11, 19333, 3286.61, 'Maria')" };
    Connection connection = 
      DatabaseUtilities.createTable(driver, url,
                                    username, password,
                                    "fruits", format, rows,
                                    false);
    // Test to verify table was created properly. Reuse
    // old connection for efficiency.
    DatabaseUtilities.printTable(connection, "fruits",
                                 11, true);
  }

  private static void printUsage() {
     System.out.println("Usage: FruitCreation host dbName " +
                        "username password oracle|sybase.");
  }
}
