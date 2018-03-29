package cookies;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class PageTwo extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    boolean hasVisitedPageOne = false;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for(Cookie c: cookies) {
        if ((c.getName().equals("visitedPageOne")) &&
            // Could omit test and treat cookie name as a flag
            (c.getValue().equals("yes"))) {
          hasVisitedPageOne = true;
          break;
        }
      }
    }
    if (!hasVisitedPageOne) {
      response.sendRedirect("page-one");
    } else {
      String title = "Page Two";
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String docType =
        "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
        "Transitional//EN\">\n";
      out.println(docType +
                  "<HTML>\n" +
                  "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
                  "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                  "<H1 ALIGN=\"CENTER\">" + title + "</H1>\n" +
                  "</BODY></HTML>");
    }
  }
}
