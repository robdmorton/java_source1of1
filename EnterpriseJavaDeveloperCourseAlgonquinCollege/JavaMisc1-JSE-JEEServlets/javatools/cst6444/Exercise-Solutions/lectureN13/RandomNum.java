package lectureN13;  

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/** Servlet that generates a random number, sticks it in a bean,
 *  and forwards to JSP page to display it.
 */

public class RandomNum extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    NumberBean bean = new NumberBean(Math.random());
    request.setAttribute("randomNum", bean);
    RequestDispatcher dispatcher =
      getServletContext().getRequestDispatcher("/lectureN13/RandomNum.jsp");
    dispatcher.forward(request, response);
  }
}
