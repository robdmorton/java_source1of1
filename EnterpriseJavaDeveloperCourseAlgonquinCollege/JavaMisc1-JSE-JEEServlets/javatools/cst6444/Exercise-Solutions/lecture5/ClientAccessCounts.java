package lecture5; // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*; // Note for exercise

/** Servlet that prints per-client access counts.
 *  Solution to exercise from lecture 5. 
 */

public class ClientAccessCounts extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    Cookie[] cookies = request.getCookies();
    String countString = ServletUtilities.getCookieValue(cookies,
                                                         "accessCount",
                                                         "1");
    int count = 1;
    try {
      count = Integer.parseInt(countString);
    } catch(NumberFormatException nfe) { }
    LongLivedCookie c =
      new LongLivedCookie("accessCount", String.valueOf(count+1));
    response.addCookie(c);
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Access Count Servlet";
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY>\n" +
                "<H1>" + title + "</H1>\n" +
                "This is visit number " + count + " to this page\n" +
                "</BODY></HTML>");
  }
}
