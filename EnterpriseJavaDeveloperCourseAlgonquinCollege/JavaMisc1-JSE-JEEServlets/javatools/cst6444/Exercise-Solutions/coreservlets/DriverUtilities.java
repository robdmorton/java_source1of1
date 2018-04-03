package coreservlets;

/** Some simple utilities for building Oracle and Sybase
 *  JDBC connections. This is <I>not</I> general-purpose
 *  code -- it is specific to my local setup.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class DriverUtilities {
  public static final int ORACLE = 1;
  public static final int SYBASE = 2;
  public static final int UNKNOWN = -1;

  /** Build a URL in the format needed by the
   *  Oracle and Sybase drivers I am using.
   */
  
  public static String makeURL(String host, String dbName,
                               int vendor) {
    if (vendor == ORACLE) {
      return("jdbc:oracle:thin:@" + host + ":1521:" + dbName);
    } else if (vendor == SYBASE) {
      return("jdbc:sybase:Tds:" + host  + ":1521" +
             "?SERVICENAME=" + dbName);
    } else {
      return(null);
    }
  }

  /** Get the fully qualified name of a driver. */
  
  public static String getDriver(int vendor) {
    if (vendor == ORACLE) {
      return("oracle.jdbc.driver.OracleDriver");
    } else if (vendor == SYBASE) {
      return("com.sybase.jdbc.SybDriver");
    } else {
      return(null);
    }
  }

  /** Map name to int value. */

  public static int getVendor(String vendorName) {
    if (vendorName.equalsIgnoreCase("oracle")) {
      return(ORACLE);
    } else if (vendorName.equalsIgnoreCase("sybase")) {
      return(SYBASE);
    } else {
      return(UNKNOWN);
    }
  }
}
