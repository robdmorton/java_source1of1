package lecture5; // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*; // Note for exercise

/** Servlet that says "Welcome aboard" to first-time visitors and
 *  "Welcome back" to repeat visitors. This is a variation of
 *  the RepeatVisitor solution to exercise from lecture 5 --
 *  uses ServletUtilities to simplify cookie handling.
 */

public class RepeatVisitor2 extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    boolean newbie = true;
    String repeatStatus = ServletUtilities.getCookieValue(request.getCookies(),
                                                          "repeatVisitor2",
                                                          "no");
    if (repeatStatus.equals("yes")) {
      newbie = false;
    }
    String title;
    if (newbie) {
      LongLivedCookie returnVisitorCookie =
        new LongLivedCookie("repeatVisitor2", "yes");
      response.addCookie(returnVisitorCookie);
      title = "Welcome aboard";
    } else {
      title = "Welcome back";
    }
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY>\n" +
                "<H1>" + title + "</H1>\n" +
                "</BODY></HTML>");
  }
}
