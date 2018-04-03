/*
 * Created on Aug 3, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package userstuff;

import javax.servlet.http.*;
import javax.servlet.*;

/**
 * @author wce00035
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LoginServlet extends HttpServlet {
	
	public void doPost( HttpServletRequest request, HttpServletResponse response )
	{
		String destinationURL;
		try {
			LoginFormBean login = new LoginFormBean();
			login.setUser_id(request.getParameter("user_id"));
			login.setPassword(request.getParameter("password"));
		
			User user = UserManager.login(login);
			
			if( user != null){
				HttpSession session = request.getSession(true);
				session.setAttribute("userBean", user);
				destinationURL = "../displayUser.jsp";
			}
			else{
				destinationURL = "../noUser.jsp";
				request.setAttribute("login", login);
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(destinationURL);
			dispatcher.forward(request, response);			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
