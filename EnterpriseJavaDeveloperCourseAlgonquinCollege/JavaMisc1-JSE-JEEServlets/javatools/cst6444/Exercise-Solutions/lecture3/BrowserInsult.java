package lecture3;  // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;  // Note for exercise

/** Servlet that gives browser-specific insult.
 *  Solution to exercise from lecture 3.
 */

public class BrowserInsult extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title, message;
    // Assume for simplicity that Netscape and IE are only two
    // browsers
    String userAgent = request.getHeader("User-Agent");
    if ((userAgent != null) && (userAgent.indexOf("MSIE") != -1)) {
      title = "Microsoft Minion";
      message = "Welcome, O spineless slave to the mighty empire.";
    } else {
      title = "Hopeless Netscape Rebel";
      message = "Enjoy it while you can. You <I>will</I> be assimilated!";
    }
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                message + "\n" +
                "</BODY></HTML>");
  }
}
