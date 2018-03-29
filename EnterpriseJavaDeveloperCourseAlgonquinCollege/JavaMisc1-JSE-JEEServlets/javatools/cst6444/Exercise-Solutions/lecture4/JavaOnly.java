package lecture4;  // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;  // Note for exercise

/** Servlet that returns error page if no favoriteLanguage param
 *  with the value Java. Solution to exercise from lecture 4.
 */

public class JavaOnly extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    String lang = request.getParameter("favoriteLanguage");
    if ((lang == null) || (!lang.equalsIgnoreCase("java"))) {
      response.sendError(response.SC_NOT_FOUND,
                         "Cannot find page on that bogus language.");
    } else {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "The Real Language";
      out.println(ServletUtilities.headWithTitle(title) +
                  "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                  "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                  "Thanks for using Java on the server.\n" +
                  "</BODY></HTML>");
    }
  }
}
