<!doctype html public "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="java.util.*" %>
<%@ page import="movie.*" %>
<html>
  <head>
    <title>List Movies: The Servlet</title>
  </head>
  <body>
    <h1>Some of My Favoritess</h1>
    <h3>
      <%= getMovieList() %>
    </h3>
  </body>
</html>

<%! 
private String getMovieList()
{
    String msg = "";
    ArrayList movies = MovieIO.getMovies();
    for (int i = 0; i < movies.size(); i++)
    {
    	Movie m = (Movie)movies.get(i);
        msg += m.year + ": ";
        msg += m.title + "<br>";
    }
    return msg;
}


%>
