package lecture6; // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*; // Note for exercise

/** Servlet that says "Welcome aboard" to first-time visitors and
 *  "Welcome back" to repeat visitors. Solution to exercise from
 *  lecture 6. Variation of version from lecture 5 that used
 *  cookies explicitly. This approach is a bit easier.
 */

public class RepeatVisitor extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    boolean newbie = false;
    HttpSession session = request.getSession(true);
    String repeatStatus = (String)session.getAttribute("repeatVisitor");
    if (repeatStatus == null) {
      newbie = true;
      session.setAttribute("repeatVisitor", "yes");
    }
    String title;
    if (newbie) {
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
