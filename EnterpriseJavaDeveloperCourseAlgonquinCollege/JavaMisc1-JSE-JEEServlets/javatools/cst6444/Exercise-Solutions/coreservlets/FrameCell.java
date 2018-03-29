package coreservlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

/** Illustrates the value of persistent HTTP connections for
 *  pages that includes many images, applet classes, or
 *  other auxiliary content that would otherwise require
 *  a separate connection to retrieve.
 *  <P>
 *  Taken from Core Servlets and JSP,
 *  http://www.apl.jhu.edu/~hall/csajsp/.
 *  1999 Marty Hall; may be freely used or adapted.
 */

public class FrameCell extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    ByteArrayOutputStream byteStream =
      new ByteArrayOutputStream(512);
    PrintWriter out = new PrintWriter(byteStream, true);
    String persistenceFlag =
      request.getParameter("usePersistence");
    boolean usePersistence =
      ((persistenceFlag == null) ||
       (!persistenceFlag.equals("no")));
    String title;
    if (usePersistence) {
      title = "Persistent";
    } else {
      title = "Not Persistent";
    }
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<B>Cell " +
                request.getParameter("cellNum") +
                " (" + title + ")</B>\n" +
                "</BODY></HTML>");
    if (usePersistence) {
      response.setContentLength(byteStream.size());
    }
    byteStream.writeTo(response.getOutputStream());
  }
}
