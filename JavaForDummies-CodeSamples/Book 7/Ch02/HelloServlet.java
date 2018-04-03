import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class HelloServlet extends HttpServlet
{
    public void doGet(HttpServletRequest request,
    	HttpServletResponse response)
    		throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String msg = getGreeting();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>HelloWorld Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>");
        out.println(msg);
        out.println("</h1>");
        out.println("</body>");
        out.println("</html>");
    }

    public void doPost(HttpServletRequest request,
    	HttpServletResponse response)
    		throws IOException, ServletException
    {
		doGet(request, response);
	}

    private String getGreeting()
    {
        String msg = "";
        int rand = (int)(Math.random() * (6)) + 1;
        switch (rand)
        {
			case 1:
				return "Hello, World!";
			case 2:
				return "Greetings!";
			case 3:
				return "Felicitations!";
			case 4:
				return "Yo, Dude!";
			case 5:
				return "Whasssuuuup?";
			case 6:
				return "Hark!";
		}
		return null;
	}
}
