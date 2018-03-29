package lectureN13;  

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class PrimeServlet extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    String length = request.getParameter("primeLength");
    synchronized(this) {
      if (length != null) {
        PrimeBean primeBean = new PrimeBean(length);
        getServletContext().setAttribute("primeBean", primeBean);
      }
      RequestDispatcher dispatcher =
        getServletContext().getRequestDispatcher("/lectureN13/ShowPrime.jsp");
      dispatcher.forward(request, response);
    }
  }
}
