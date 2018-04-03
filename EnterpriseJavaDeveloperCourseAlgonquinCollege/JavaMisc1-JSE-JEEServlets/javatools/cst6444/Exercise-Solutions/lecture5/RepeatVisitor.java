package lecture5; // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*; // Note for exercise

/** Servlet that says "Welcome aboard" to first-time visitors and
 *  "Welcome back" to repeat visitors. Solution to exercise from
 *  lecture 5. Also see RepeatVisitor2 for variation that uses cookie utilities
 *  from ServletUtilities class.
 */

public class RepeatVisitor extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    boolean newbie = true;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for(int i=0; i<cookies.length; i++) {
        Cookie c = cookies[i];
        if ((c.getName().equals("repeatVisitor")) &&
            // Could omit following test and treat cookie name as a flag
            (c.getValue().equals("yes"))) {
          newbie = false;
          break;
        }
      }
    }
    String title;
    if (newbie) {
      Cookie returnVisitorCookie = new Cookie("repeatVisitor", "yes");
      returnVisitorCookie.setMaxAge(60*60*24*365);
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
