package coreservlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.awt.*;

/** Servlet that generates GIF images representing
 *  a designated message with an oblique shadowed
 *  version behind it.
 *  <P>
 *  <B>Only runs on servers that support Java 2, since
 *  it relies on Java2D to build the images.</B>
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class ShadowedText extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    String message = request.getParameter("message");
    if ((message == null) || (message.length() == 0)) {
      message = "Missing 'message' parameter";
    }
    String fontName = request.getParameter("fontName");
    if (fontName == null) {
      fontName = "Serif";
    }
    String fontSizeString = request.getParameter("fontSize");
    int fontSize;
    try {
      fontSize = Integer.parseInt(fontSizeString);
    } catch(NumberFormatException nfe) {
      fontSize = 90;
    }
    response.setContentType("image/gif");
    OutputStream out = response.getOutputStream();
    Image messageImage =
      MessageImage.makeMessageImage(message,
                                    fontName,
                                    fontSize);
    MessageImage.sendAsGIF(messageImage, out);
  }

  /** Allow form to send data via either GET or POST. */
  
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}
