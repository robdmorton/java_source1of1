package lecture6; // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector; // Note for exercise
import coreservlets.*; // Note for exercise

/** Servlet that displays a list of items being ordered.
 */

public class ShowItems extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    Vector previousItems = (Vector)session.getAttribute("previousItems");
    if (previousItems == null) {
      previousItems = new Vector();
      session.setAttribute("previousItems", previousItems);
    }
    String newItem = request.getParameter("newItem");
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Items Purchased";
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY>\n" +
                "<H1>" + title + "</H1>");
    synchronized(previousItems) {
      if (newItem != null) {
        previousItems.addElement(newItem);
      }
      if (previousItems.size() == 0) {
        out.println("<I>No items</I>");
      } else {
        out.println("<UL>");
        for(int i=0; i<previousItems.size(); i++) {
          out.println("<LI>" + (String)previousItems.elementAt(i));
        }
        out.println("</UL>");
      }
    }
    out.println("</BODY></HTML>");
  }
}
