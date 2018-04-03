package lecture4;  // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/** Servlet that creates Excel spreadsheet.
 *  Solution to exercise from lecture 4.
 */

public class Excel extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
      response.setContentType("application/vnd.ms-excel");
      PrintWriter out = response.getWriter();
      out.println("Row1 Col1\tRow1 Col2\tRow1 Col3");
      out.println("Row2 Col1\tRow2 Col2\tRow2 Col3");
  }
}
