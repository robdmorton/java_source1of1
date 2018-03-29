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

public class ThreeParamsPost extends HttpServlet {

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
	
	
	
    String title = "Reading Three Request Parameters RDM-2012-03-26-18:55";
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                "<UL>\n" +
                "  <LI><B>param1</B>: "
                + ServletUtilities.filter(request.getParameter("param1")) + "\n" +
                "  <LI><B>param2</B>: "
                + ServletUtilities.filter(request.getParameter("param2")) + "\n" +
                "  <LI><B>param3</B>: "
                + ServletUtilities.filter(request.getParameter("param3")) + "\n" +
                "</UL>\n" +
                "&lt; &gt; &quot; &amp;\n" +
                "</BODY></HTML>");
  }
}
