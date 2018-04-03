package coreservlets;

/** A variation of ConnectionPoolServlet that uses only
 *  a single connection, queueing up all requests to it.
 *  Used to compare timing results.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class ConnectionPoolServlet2
      extends ConnectionPoolServlet {
  
  protected int initialConnections() {
    return(1);
  }

  protected int maxConnections() {
    return(1);
  }
}
  
