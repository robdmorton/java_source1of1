package coreservlets;

import java.sql.*;

/** Connect to Oracle or Sybase and print "employees" table
 *  as an HTML table.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class EmployeeTest2 {
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
    String query = "SELECT * FROM employees";
    DBResults results =
      DatabaseUtilities.getQueryResults(driver, url,
                                        username, password,
                                        query, true);
    System.out.println(results.toHTMLTable("CYAN"));
  }

  private static void printUsage() {
    System.out.println("Usage: EmployeeTest2 host dbName " +
                       "username password oracle|sybase.");
  }
}
