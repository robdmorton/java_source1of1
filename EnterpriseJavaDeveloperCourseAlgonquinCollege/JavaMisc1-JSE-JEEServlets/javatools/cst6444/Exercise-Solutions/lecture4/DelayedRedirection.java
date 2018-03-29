package lecture4;  // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;  // Note for exercise

/** Servlet that gives browser-specific insult.
 *  Solution to exercise from lecture 3.
 */

public class DelayedRedirection extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    String url;
    // Assume for simplicity that Netscape and IE are only two
    // browsers
    String userAgent = request.getHeader("User-Agent");
    if ((userAgent != null) && (userAgent.indexOf("MSIE") != -1)) {
      url = "http://www.microsoft.com";
    } else {
      url = "http://home.netscape.com";
    }
    response.setHeader("Refresh", "10; URL=" + url);
    PrintWriter out = response.getWriter();
    String title = "Delayed Redirection";
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                "You will be sent to <B>" + url + "</B>\n" +
                "in 10 seconds.\n" +
                "</BODY></HTML>");
  }
}
