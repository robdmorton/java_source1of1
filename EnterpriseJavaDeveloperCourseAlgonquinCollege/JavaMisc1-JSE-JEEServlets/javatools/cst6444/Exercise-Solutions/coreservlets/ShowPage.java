package coreservlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/** Example of the include method of RequestDispatcher.
 *  Given a URI on the same system as this servlet, the
 *  servlet shows you its raw HTML output. 
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class ShowPage extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String url = request.getParameter("url");
    out.println(ServletUtilities.headWithTitle(url) +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + url + "</H1>\n" +
                "<FORM><CENTER>\n" +
                "<TEXTAREA ROWS=30 COLS=70>");
    if ((url == null) || (url.length() == 0)) {
      out.println("No URL specified.");
    } else {
      // Attaching data works only in version 2.2.
      String data = request.getParameter("data");
      if ((data != null) && (data.length() > 0)) {
        url = url + "?" + data;
      }
      RequestDispatcher dispatcher =
        getServletContext().getRequestDispatcher(url);
      dispatcher.include(request, response);
    }
    out.println("</TEXTAREA>\n" +
                "</CENTER></FORM>\n" +
                "</BODY></HTML>");
  }

  /** Handle GET and POST identically. */

  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}
