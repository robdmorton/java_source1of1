package coreservlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/** Simple servlet that reads three parameters from the
 *  form data.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class RegistrationPost extends HttpServlet {

  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException
  {
	doGet(request,response);
  }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Registration";
	String firstName = request.getParameter("firstName");
	if(firstName == "")
	{
	  firstName = "John";
	}
	String secondName = request.getParameter("secondName");
	if(secondName == "")
	{
	  secondName = "Doe";
	}
	String emailAddress = request.getParameter("emailAddress");
	if(emailAddress == "")
	{
	  emailAddress = "whereever@mail.com";
	}
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                "<UL>\n" +
                "  <LI><B>First Name</B>: "
                + firstName + "\n" +
                "  <LI><B>Second Name</B>: "
                + secondName + "\n" +
                "  <LI><B>email Address</B>: "
                + emailAddress + "\n" +
                "</UL>\n" +
                "</BODY></HTML>");
  }
}
