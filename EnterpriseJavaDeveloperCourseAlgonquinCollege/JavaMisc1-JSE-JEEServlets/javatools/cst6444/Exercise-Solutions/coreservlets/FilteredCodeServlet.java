package coreservlets;

/** Subclass of BadCodeServlet that keeps the same doGet method
 *  but filters the code fragment for HTML-specific characters.
 *  You should filter strings that are likely to contain
 *  special characters (like program excerpts) or strings
 *  that are derived from user input.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class FilteredCodeServlet extends BadCodeServlet {
  public String getCodeFragment() {
    return(ServletUtilities.filter(super.getCodeFragment()));
  }
}
