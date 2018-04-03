package lecture3;  // Note for exercise

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;  // Note for exercise

/** Servlet that displays referer-specific image.
 *  Solution to exercise from lecture 3.
 */

public class BannerAds extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String referer = request.getHeader("Referer");
    if (referer == null) {
      referer = "<I>none</I>";
    }
    String title = "Referring page: " + referer;
    String image;
    if (contains(referer, "Tomcat")) {
      image = "/banner-ads/tomcat.gif";
    } else if (contains(referer, "Sun")) {
      image = "/banner-ads/Sun-Logo-250x125.gif";
    } else {
      image = "/banner-ads/CSAJSP-Cover-125x164.jpg";
    }
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                "<IMG SRC=\"" + image + "\">\n" +
                "</BODY></HTML>");
  }

  private boolean contains(String mainString, String subString) {
    return(mainString.indexOf(subString) != -1);
  }
}
