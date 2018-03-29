package cookies;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import coreservlets.*;

public class SetColors extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    String bgColor = request.getParameter("bgColor");
    if ((bgColor != null) && (!bgColor.trim().equals(""))) {
      Cookie bgColorCookie = new LongLivedCookie("bgColor", bgColor);
      response.addCookie(bgColorCookie);
    }
    String fgColor = request.getParameter("fgColor");
    if ((fgColor != null) && (!fgColor.trim().equals(""))) {
      Cookie fgColorCookie = new LongLivedCookie("fgColor", fgColor);
      response.addCookie(fgColorCookie);
    }
    response.sendRedirect("some-random-page");
  }
}
