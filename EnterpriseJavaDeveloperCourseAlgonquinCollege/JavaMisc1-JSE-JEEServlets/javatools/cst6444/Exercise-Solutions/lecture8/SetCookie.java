package lecture8; // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*; // Note for exercise

/** Servlet that sets a cookie called "testCookie".
 */

public class SetCookie extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    LongLivedCookie testCookie =
      new LongLivedCookie("testCookie", "Testing!");
    testCookie.setPath("/");
    response.addCookie(testCookie);
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Setting Cookie Called \"testCookie\"";
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY>\n" +
                "<H1>" + title + "</H1>\n" +
                "</BODY></HTML>");
  }
}
