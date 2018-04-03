package lecture2;  // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;  // Note for exercise

/** Registration servlet. Solution to exercise from lecture 2.
 */

public class Registration extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    String firstName = checkVal(request.getParameter("firstName"),
                                "Missing first name");
    String lastName = checkVal(request.getParameter("lastName"),
                                "Missing last name");
    String emailAddress = checkVal(request.getParameter("emailAddress"),
                                   "Missing email address");
    PrintWriter out = response.getWriter();
    String title = "Registering";
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                "<UL>\n" +
                "  <LI><B>First Name</B>: " +
                firstName + "\n" +
                "  <LI><B>Last Name</B>: " +
                lastName + "\n" +
                "  <LI><B>Email address</B>: " +
                emailAddress + "\n" +
                "</UL>\n" +
                "</BODY></HTML>");
  }

  /** Returns error message if value is missing or is empty string. */
  
  private String checkVal(String orig, String replacement) {
    if ((orig == null) || (orig.equals(""))) {
      return("<FONT COLOR=RED><B>" + replacement + "</B></FONT>");
    } else {
      return(orig);
    }
  }
}
