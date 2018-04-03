package lecture1;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;

/** Lecture 1: Solution for servlet that generates table.
 */

public class MakeTable extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println(ServletUtilities.headWithTitle("Creating Table") +
                "<BODY>\n" +
                "<H1>Creating Table</H1>\n" +
                "<TABLE BORDER=1>\n" +
                "<TR BGCOLOR=\"#9900FF\">\n" +
                "  <TH>Column 1<TH>Column 2<TH>Column 3");
    for(int row=1; row<26; row++) {
      out.println("<TR>");
      for(int col=1; col<4; col++) {
        out.println("<TD>Row " + row + ", col " + col);
      }
    }
    out.println("</TABLE></BODY></HTML>");
  }
}
