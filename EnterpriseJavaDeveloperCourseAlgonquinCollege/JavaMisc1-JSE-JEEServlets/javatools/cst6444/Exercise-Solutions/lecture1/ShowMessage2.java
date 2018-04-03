package lecture1;  // New for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;  // New for exercise

/** Variation of ShowMessage for lecture1 exercise. You need to
 *  install modified version of web.xml in the ROOT\WEB-INF directory
 *  and then use the URL http://localhost/servlet/ShowMsg2
 */

public class ShowMessage2 extends HttpServlet {
  private String message;
  private String defaultMessage = "No message.";
  private int repeats = 1;
  
  public void init(ServletConfig config)
      throws ServletException {
    // Always call super.init
    super.init(config);
    message = config.getInitParameter("message");
    if (message == null) {
      message = defaultMessage;
    }
    try {
      String repeatString = config.getInitParameter("repeats");
      repeats = Integer.parseInt(repeatString);
    } catch(NumberFormatException nfe) {
      // NumberFormatException handles case where repeatString
      // is null *and* case where it is something in an
      // illegal format. Either way, do nothing in catch,
      // as the previous value (1) for the repeats field will
      // remain valid because the Integer.parseInt throws
      // the exception *before* the value gets assigned
      // to repeats.
    }
  }

    public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
        throws ServletException, IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "The ShowMessage2 Servlet";
      out.println(ServletUtilities.headWithTitle(title) +
                  "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                  "<H1 ALIGN=CENTER>" + title + "</H1>");
      for(int i=0; i<repeats; i++) {
        out.println(message + "<BR>");
      }
      out.println("</BODY></HTML>");
    }
}
