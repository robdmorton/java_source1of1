package lecture6; // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector; // Note for exercise
import coreservlets.*; // Note for exercise

/** Servlet that displays a list of all previous referring pages
 *  from that client.
 */

public class ShowReferers extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    Vector previousReferers = (Vector)session.getAttribute("previousReferers");
    if (previousReferers == null) {
      previousReferers = new Vector();
      session.setAttribute("previousReferers", previousReferers);
    }
    String newReferer = request.getHeader("Referer");
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Referring Pages";
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY>\n" +
                "<H1>" + title + "</H1>");
    synchronized(this) {
      if (newReferer != null) {
        previousReferers.addElement(newReferer);
      }
      if (previousReferers.size() == 0) {
        out.println("<I>No referring pages</I>");
      } else {
        out.println("<UL>");
        for(int i=0; i<previousReferers.size(); i++) {
          out.println("<LI>" + (String)previousReferers.elementAt(i));
        }
        out.println("</UL>");
      }
    }
    out.println("</BODY></HTML>");
  }
}
