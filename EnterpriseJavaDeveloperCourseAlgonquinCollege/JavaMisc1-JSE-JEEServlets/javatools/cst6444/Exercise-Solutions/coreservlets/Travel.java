package coreservlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/** Top-level travel-processing servlet. This servlet sets up
 *  the customer data as a bean, then forwards the request
 *  to the airline booking page, the rental car reservation
 *  page, the hotel page, the existing account modification
 *  page, or the new account page.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class Travel extends HttpServlet {
  private TravelCustomer[] travelData;

  public void init() {
    travelData = TravelData.getTravelData();
  }

  /** Since password is being sent, use POST only. However,
   *  the use of POST means that you cannot forward
   *  the request to a static HTML page, since the forwarded
   *  request uses the same request method as the original
   *  one, and static pages cannot handle POST. Solution:
   *  have the "static" page be a JSP file that contains
   *  HTML only. That's what accounts.jsp is. The other
   *  JSP files really need to be dynamically generated,
   *  since they make use of the customer data.
   */
  
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
    String emailAddress = request.getParameter("emailAddress");
    String password = request.getParameter("password");
    TravelCustomer customer =
      TravelCustomer.findCustomer(emailAddress, travelData);
    if ((customer == null) || (password == null) ||
        (!password.equals(customer.getPassword()))) {
      gotoPage("/travel/accounts.jsp", request, response);
    }
    // The methods that use the following parameters will
    // check for missing or malformed values.
    customer.setStartDate(request.getParameter("startDate"));
    customer.setEndDate(request.getParameter("endDate"));
    customer.setOrigin(request.getParameter("origin"));
    customer.setDestination(request.getParameter
                              ("destination"));
    HttpSession session = request.getSession(true);
    session.putValue("customer", customer);
    if (request.getParameter("flights") != null) {
      gotoPage("/travel/BookFlights.jsp",
               request, response);
    } else if (request.getParameter("cars") != null) {
      gotoPage("/travel/RentCars.jsp",
               request, response);
    } else if (request.getParameter("hotels") != null) {
      gotoPage("/travel/FindHotels.jsp",
               request, response);
    } else if (request.getParameter("cars") != null) {
      gotoPage("/travel/EditAccounts.jsp",
               request, response);
    } else {
      gotoPage("/travel/IllegalRequest.jsp",
               request, response);
    }
  }
  
  private void gotoPage(String address,
                        HttpServletRequest request,
                        HttpServletResponse response)
      throws ServletException, IOException {
    RequestDispatcher dispatcher =
      getServletContext().getRequestDispatcher(address);
    dispatcher.forward(request, response);
  }
}
