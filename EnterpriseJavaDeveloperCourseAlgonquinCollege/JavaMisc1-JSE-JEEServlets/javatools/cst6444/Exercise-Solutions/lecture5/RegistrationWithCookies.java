package lecture5;  // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;  // Note for exercise

/** Registration servlet that uses cookies to fill in missing values.
 *  Solution to exercise from lecture 5 (based on previous solution
 *  from lecture 2).
 */

public class RegistrationWithCookies extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    Cookie[] cookies = request.getCookies();
    String firstName =
      checkVal(request.getParameter("firstName"),
               ServletUtilities.getCookieValue(cookies, "firstName", null),
               "Unknown first name");
    String lastName =
      checkVal(request.getParameter("lastName"),
               ServletUtilities.getCookieValue(cookies, "lastName", null),
               "Unknown last name");
    String emailAddress =
      checkVal(request.getParameter("emailAddress"),
               ServletUtilities.getCookieValue(cookies, "emailAddress", null),
               "Unknown email address");
    Cookie c1 = new LongLivedCookie("firstName", firstName);
    response.addCookie(c1);
    Cookie c2 = new LongLivedCookie("lastName", lastName);
    response.addCookie(c2);
    Cookie c3 = new LongLivedCookie("emailAddress", emailAddress);
    response.addCookie(c3);
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
                          String cookieVal,
                          String replacement) {
    if ((orig != null) && (!orig.equals(""))) {
      return(orig);
    } else if (cookieVal != null) {
      return(cookieVal);
    } else {
      return("<FONT COLOR=RED><B>" + replacement + "</B></FONT>");
    }
  }
}
