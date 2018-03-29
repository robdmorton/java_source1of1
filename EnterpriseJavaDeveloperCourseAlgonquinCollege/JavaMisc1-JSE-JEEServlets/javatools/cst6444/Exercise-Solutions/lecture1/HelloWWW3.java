package lecture1;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;

/** Lecture 1: Solution for simple servlet in new package.
 *
 *  Changes from original HelloWWW3:
 *   1) Changed "package coreservlets" to "package lecture1"
 *   2) Put this in the "lecture1" directory (both for development
 *      and within the tomcat classes directory for execution).
 *   3) Added "import coreservlet.*" so it could use ServletUtilities.
 */

public class HelloWWW3 extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println(ServletUtilities.headWithTitle("Hello WWW") +
                "<BODY>\n" +
                "<H1>Hello WWW</H1>\n" +
                "Modified for lecture1 exercise...\n" +
                "</BODY></HTML>");
  }
}
