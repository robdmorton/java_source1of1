package CST6445WebServices.lab1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyFirstServlet
 */
public class MyFirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyFirstServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(
			HttpServletRequest request, 
			HttpServletResponse response) 
		    throws IOException, ServletException
    {
    	response.setContentType("application/xml");
    	PrintWriter out = response.getWriter();
    	out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
    	out.println("<hello>");
    	out.println("  <who>");
    	out.println("    <name>Rob Morton</name>");
    	out.println("    <title>Senior Support Engineer</title>");
    	out.println("    <work>Alcatel-Lucent</work>");
    	out.println("  </who>");
    	out.println("  <text>Blah Blah Blah</text>");
    	out.println("</hello>");
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
