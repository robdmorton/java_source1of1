package lecture6;  // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;  // Note for exercise

/** Registration servlet that uses session info to fill in missing values.
 *  Solution to exercise from lecture 6 (based on previous solution
 *  from lecture 5).
 */

public class RegistrationWithCookies extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession(true);
    String firstName =
      checkVal(request.getParameter("firstName"),
               session.getAttribute("firstName"),
               "Unknown first name");
    String lastName =
      checkVal(request.getParameter("lastName"),
               session.getAttribute("lastName"),
               "Unknown last name");
    String emailAddress =
      checkVal(request.getParameter("emailAddress"),
               session.getAttribute("emailAddress"),
               "Unknown email address");
    session.setAttribute("firstName", firstName);
    session.setAttribute("lastName", lastName);
    session.setAttribute("emailAddress", emailAddress);
    PrintWriter out = response.getWriter();
    String title = "Registering with Cookies";
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
  
  private String checkVal(String orig,
                          Object sessionVal,
                          String replacement) {
    if ((orig != null) && (!orig.equals(""))) {
      return(orig);
    } else if (sessionVal != null) {
      return((String)sessionVal);
    } else {
      return("<FONT COLOR=RED><B>" + replacement + "</B></FONT>");
    }
  }
}
