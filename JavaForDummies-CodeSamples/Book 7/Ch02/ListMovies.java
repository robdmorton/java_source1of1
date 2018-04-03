import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class ListMovies extends HttpServlet
{
    public void doGet(HttpServletRequest request,
    	HttpServletResponse response)
    		throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String msg = getMovieList();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>List Movies Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Some of My Favorites</h1>");
        out.println("<h3>");
        out.println(msg);
        out.println("</h3>");
        out.println("</body>");
	    out.println("</html>");
    }

    public void doPost(HttpServletRequest request,
    	HttpServletResponse response)
    		throws IOException, ServletException
    {
		doGet(request, response);
	}

    private String getMovieList()
    {
		String msg = "";
		ArrayList<Movie> movies = MovieIO.getMovies();
		for (Movie m : movies)
		{
			msg += m.year + ": ";
			msg += m.title + "<br>";
		}
		return msg;
	}


}
