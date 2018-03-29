package coreservlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

/** Dynamically generated variation of the
 *  SearchEngines.html front end that uses cookies
 *  to remember a user's preferences.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class SearchEnginesFrontEnd extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    Cookie[] cookies = request.getCookies();
    String searchString =
      ServletUtilities.getCookieValue(cookies,
                                      "searchString",
                                      "Java Programming");
    String numResults =
      ServletUtilities.getCookieValue(cookies,
                                      "numResults",
                                      "10");
    String searchEngine =
      ServletUtilities.getCookieValue(cookies,
                                      "searchEngine",
                                      "google");
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Searching the Web";
    out.println
      (ServletUtilities.headWithTitle(title) +
       "<BODY BGCOLOR=\"#FDF5E6\">\n" +
       "<H1 ALIGN=\"CENTER\">Searching the Web</H1>\n" +
       "\n" +
       "<FORM ACTION=\"/servlet/" +
         "coreservlets.CustomizedSearchEngines\">\n" +
       "<CENTER>\n" +
       "Search String:\n" +
       "<INPUT TYPE=\"TEXT\" NAME=\"searchString\"\n" +
       "       VALUE=\"" + searchString + "\"><BR>\n" +
       "Results to Show Per Page:\n" +
       "<INPUT TYPE=\"TEXT\" NAME=\"numResults\"\n" + 
       "       VALUE=" + numResults + " SIZE=3><BR>\n" +
       "<INPUT TYPE=\"RADIO\" NAME=\"searchEngine\"\n" +
       "       VALUE=\"google\"" +
       checked("google", searchEngine) + ">\n" +
       "Google |\n" +
       "<INPUT TYPE=\"RADIO\" NAME=\"searchEngine\"\n" +
                "       VALUE=\"infoseek\"" +
       checked("infoseek", searchEngine) + ">\n" +
       "Infoseek |\n" +
       "<INPUT TYPE=\"RADIO\" NAME=\"searchEngine\"\n" +
       "       VALUE=\"lycos\"" +
       checked("lycos", searchEngine) + ">\n" +
       "Lycos |\n" +
       "<INPUT TYPE=\"RADIO\" NAME=\"searchEngine\"\n" +
       "       VALUE=\"hotbot\"" +
       checked("hotbot", searchEngine) + ">\n" +
       "HotBot\n" +
       "<BR>\n" +
       "<INPUT TYPE=\"SUBMIT\" VALUE=\"Search\">\n" +
       "</CENTER>\n" +
       "</FORM>\n" +
       "\n" +
       "</BODY>\n" +
       "</HTML>\n");
  }

  private String checked(String name1, String name2) {
    if (name1.equals(name2))
      return(" CHECKED");
    else
      return("");
  }
}
