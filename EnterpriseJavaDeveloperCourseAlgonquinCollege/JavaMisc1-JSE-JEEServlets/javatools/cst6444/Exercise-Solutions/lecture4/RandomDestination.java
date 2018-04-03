package lecture4;  // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;  // Note for exercise

/** Servlet that randomly sends users to either
 *  the Netscape or Microsoft home page.
 *  Solution to exercise from lecture 4.
 */

public class RandomDestination extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    if (Math.random() < 0.5) {
      response.sendRedirect("http://home.netscape.com");
    } else {
      response.sendRedirect("http://www.microsoft.com");
    }
  }
}
