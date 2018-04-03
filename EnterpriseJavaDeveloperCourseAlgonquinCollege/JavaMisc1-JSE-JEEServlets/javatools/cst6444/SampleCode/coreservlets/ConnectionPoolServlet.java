package coreservlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/** A servlet that reads information from a database and
 *  presents it in an HTML table. It uses connection
 *  pooling to optimize the database retrieval. A good
 *  test case is ConnectionPool.html, which loads many
 *  copies of this servlet into different frame cells.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class ConnectionPoolServlet extends HttpServlet {
  private ConnectionPool connectionPool;
  
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    String table;
    try {
      String query =
        "SELECT firstname, lastname " +
        " FROM employees WHERE salary > 70000";
      Connection connection = connectionPool.getConnection();
      DBResults results =
          DatabaseUtilities.getQueryResults(connection,
                                            query, false);
      connectionPool.free(connection);
      table = results.toHTMLTable("#FFAD00");
    } catch(Exception e) {
      table = "Error: " + e;
    }
    response.setContentType("text/html");
    // Prevent the browser from caching the response. See
    // Section 7.2 of Core Servlets and JSP for details.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
    PrintWriter out = response.getWriter();
    String title = "Connection Pool Test";
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<CENTER>\n" +
                table + "\n" +
                "</CENTER>\n</BODY></HTML>");
  }

  /** Initialize the connection pool when servlet is
   *  initialized. To avoid a delay on first access, load
   *  the servlet ahead of time yourself or have the
   *  server automatically load it after reboot.
   */
  
  public void init() {
    int vendor = DriverUtilities.SYBASE;
    String driver = DriverUtilities.getDriver(vendor);
    String host = "128.220.101.65";
    String dbName = "605741";
    String url = DriverUtilities.makeURL(host, dbName, vendor);
    String username = "hall";
    String password = "hall";
    try {
      connectionPool =
        new ConnectionPool(driver, url, username, password,
                           initialConnections(),
                           maxConnections(),
                           true);
    } catch(SQLException sqle) {
      System.err.println("Error making pool: " + sqle);
      getServletContext().log("Error making pool: " + sqle);
      connectionPool = null;
    }
  }

  public void destroy() {
    connectionPool.closeAllConnections();
  }

  /** Override this in subclass to change number of initial
   *  connections.
   */
  
  protected int initialConnections() {
    return(10);
  }

  /** Override this in subclass to change maximum number of 
   *  connections.
   */

  protected int maxConnections() {
    return(50);
  }
}
