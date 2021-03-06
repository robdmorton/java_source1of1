package lecture5;  // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;  // Note for exercise

/** Servlet that uses request parameters for foreground and
 *  background colors, falling back to cookies if no request
 *  parameters are present. Solution to exercise from lecture 5.
 */

public class ColorsWithCookies extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    Cookie[] cookies = request.getCookies();
    String bgColor =
      checkVal(request.getParameter("bgColor"),
               ServletUtilities.getCookieValue(cookies, "bgColor", null),
               "WHITE");
    String fgColor =
      checkVal(request.getParameter("fgColor"),
               ServletUtilities.getCookieValue(cookies, "fgColor", null),
               "BLACK");
    Cookie c1 = new LongLivedCookie("bgColor", bgColor);
    response.addCookie(c1);
    Cookie c2 = new LongLivedCookie("fgColor", fgColor);
    response.addCookie(c2);
    PrintWriter out = response.getWriter();
    String title = "Setting Colors Interactively (with Cookies)";
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY BGCOLOR=\"" + bgColor + "\"" +
                      "TEXT=\"" + fgColor + "\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                "</BODY></HTML>");
  }

  /** Returns error message if value is missing or is empty string. */
  
  private String checkVal(String orig,
                          String cookieVal,
                          String replacement) {
    if ((orig != null) && (!orig.equals(""))) {
      return(orig);
    } else if (cookieVal != null) {
      return(cookieVal);
    } else {
      return(replacement);
    }
  }
}
