package lecture4;  // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;  // Note for exercise

/** Servlet that sends IE users to Netscape home page and
 *  Netscape users to the Microsoft home page.
 *  Solution to exercise from lecture 4.
 */

public class WrongDestination extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    String userAgent = request.getHeader("User-Agent");
    if ((userAgent != null) && (userAgent.indexOf("MSIE") != -1)) {
      response.sendRedirect("http://home.netscape.com");
    } else {
      response.sendRedirect("http://www.microsoft.com");
    }
  }
}
