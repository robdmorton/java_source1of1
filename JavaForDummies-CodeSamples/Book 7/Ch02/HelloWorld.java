import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class HelloWorld1 extends HttpServlet
{
    public void doGet(HttpServletRequest request,
    	HttpServletResponse response)
    		throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>HelloWorld</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Hello, World!</h1>");
        out.println("</h1>");
        out.println("</body>");
        out.println("</html>");
    }
}
