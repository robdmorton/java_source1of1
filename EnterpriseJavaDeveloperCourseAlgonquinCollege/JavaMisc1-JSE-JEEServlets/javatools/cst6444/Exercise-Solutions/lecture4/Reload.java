package lecture4;  // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;  // Note for exercise
import java.util.Date;  // Note for exercise

/** Servlet that asks browser to reload it every 5 seconds.
 *  Solution to exercise from lecture 4.
 */

public class Reload extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setIntHeader("Refresh", 5);
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Reloading Servlet";
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                "The time is " + new Date() +
                "</BODY></HTML>");
  }
}
