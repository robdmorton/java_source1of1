package coreservlets;

import java.sql.*;

/** Make a simple "employees" table using DatabaseUtilities.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class EmployeeCreation {
  public static Connection createEmployees(String driver,
                                           String url,
                                           String username,
                                           String password,
                                           boolean close) {
    String format =
      "(id int, firstname varchar(32), lastname varchar(32), " +
      "language varchar(16), salary float)";
    String[] employees =
      {"(1, 'Wye', 'Tukay', 'COBOL', 42500)",
       "(2, 'Britt', 'Tell',   'C++',   62000)",
       "(3, 'Max',  'Manager', 'none',  15500)",
       "(4, 'Polly', 'Morphic', 'Smalltalk', 51500)",
       "(5, 'Frank', 'Function', 'Common Lisp', 51500)",
       "(6, 'Justin', 'Timecompiler', 'Java', 98000)",
       "(7, 'Sir', 'Vlet', 'Java', 114750)",
       "(8, 'Jay', 'Espy', 'Java', 128500)" };
    return(DatabaseUtilities.createTable(driver, url,
                                         username, password,
                                         "employees",
                                         format, employees,
                                         close));    
  }

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
    createEmployees(driver, url, username, password, true);
  }

  private static void printUsage() {
    System.out.println("Usage: EmployeeCreation host dbName " +
                       "username password oracle|sybase.");
  }
}
