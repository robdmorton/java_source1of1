/*
 * Created on Aug 3, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package userstuff;

import java.io.IOException;

import javax.servlet.http.*;

/**
 * @author wce00035
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LoginServlet extends HttpServlet {
	
    private static final long serialVersionUID = 1L;

    public void doPost( HttpServletRequest req, HttpServletResponse res ) throws IOException
	{
		LoginFormBean loginfb = new LoginFormBean();
        // Populate the form bean using the request parameters
        
        // TODO 03 uncomment the line below, finish it, and rebuild the project
        loginfb.setUser_id(req.getParameter("user_id"));
        
        // TODO 04 set the password in the form bean
        loginfb.setPassword(req.getParameter("password"));
 
        // TODO 05 finish web.xml - put in the servlet mapping for this servlet
        
        // Check if the the user is one we know about.
        // If so add the user into session scope.
        // If not then send them to an error page
        // and ask them to login again.     
        User user = UserManager.authenticate(loginfb);
        HttpSession session = req.getSession(true);
        session.setAttribute("userBean", user);
        
        res.sendRedirect("displayUser.jsp");

        // TODO 06 build, deploy and test the application
        // Enter valid and invalid user passwords (you will have to look thru the coce)
        
    }
}
