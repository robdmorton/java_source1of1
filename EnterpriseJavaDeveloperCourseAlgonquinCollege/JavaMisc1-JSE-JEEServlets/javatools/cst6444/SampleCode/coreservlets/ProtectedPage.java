package coreservlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Properties;
import sun.misc.BASE64Decoder;

/** Example of password-protected pages handled directly
 *  by servlets.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class ProtectedPage extends HttpServlet {
  private Properties passwords;
  private String passwordFile;

  /** Read the password file from the location specified
   *  by the passwordFile initialization parameter.
   */
  
  public void init(ServletConfig config)
      throws ServletException {
    super.init(config);
    try {
      passwordFile = config.getInitParameter("passwordFile");
      passwords = new Properties();
      passwords.load(new FileInputStream(passwordFile));
    } catch(IOException ioe) {}
  }
  
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String authorization = request.getHeader("Authorization");
    if (authorization == null) {
      askForPassword(response);
    } else {
      String userInfo = authorization.substring(6).trim();
      BASE64Decoder decoder = new BASE64Decoder();
      String nameAndPassword =
        new String(decoder.decodeBuffer(userInfo));
      int index = nameAndPassword.indexOf(":");
      String user = nameAndPassword.substring(0, index);
      String password = nameAndPassword.substring(index+1);
      String realPassword = passwords.getProperty(user);
      if ((realPassword != null) &&
          (realPassword.equals(password))) {
        String title = "Welcome to the Protected Page";
        out.println(ServletUtilities.headWithTitle(title) +
                    "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                    "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                    "Congratulations. You have accessed a\n" +
                    "highly proprietary company document.\n" +
                    "Shred or eat all hardcopies before\n" +
                    "going to bed tonight.\n" +
                    "</BODY></HTML>");
      } else {
        askForPassword(response);
      }
    }
  }

  // If no Authorization header was supplied in the request.
  
  private void askForPassword(HttpServletResponse response) {
    response.setStatus(response.SC_UNAUTHORIZED); // Ie 401
    response.setHeader("WWW-Authenticate",
                       "BASIC realm=\"privileged-few\"");
  }

  /** Handle GET and POST identically. */
  
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}

    
