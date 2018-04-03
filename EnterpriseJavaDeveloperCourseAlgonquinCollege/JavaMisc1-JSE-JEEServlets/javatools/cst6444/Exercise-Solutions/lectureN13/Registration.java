package lectureN13;  

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/** Reads firstName and lastName request parameters and forwards
 *  to JSP page to display them. Uses session-based bean sharing
 *  to remember previous values.
 */

public class Registration extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    NameBean nameBean = (NameBean)session.getAttribute("nameBean");
    if (nameBean == null) {
      nameBean = new NameBean();
      session.setAttribute("nameBean", nameBean);
    }
    String firstName = request.getParameter("firstName");
    if (firstName != null) {
      nameBean.setFirstName(firstName);
    }
    String lastName = request.getParameter("lastName");
    if (lastName != null) {
      nameBean.setLastName(lastName);
    }
    RequestDispatcher dispatcher =
      getServletContext().getRequestDispatcher("/lectureN13/ShowName.jsp");
    dispatcher.forward(request, response);
  }
}
