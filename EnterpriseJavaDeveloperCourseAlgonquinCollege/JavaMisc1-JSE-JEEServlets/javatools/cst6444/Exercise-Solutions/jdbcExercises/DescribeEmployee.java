package jdbcExercises;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DescribeEmployee extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    String lastName = request.getParameter("lastName");
    if ((lastName == null) || (lastName.equals(""))) {
      lastName = "Missing-Last-Name";
    }
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType +
                "<HTML>\n" +
                "<HEAD><TITLE>Employee Details</TITLE></HEAD>\n" +
                "<BODY BGCOLOR=\"#FDF5E6\"><CENTER>\n" +
                "<H1>Employee Details</H1>");
    String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
    String url = "jdbc:odbc:Northwind";
    String username = "";
    String password = "";
    showUser(driver, url, username, password, lastName, out);
    out.println("</CENTER></BODY></HTML>");
  }

  private void showUser(String driver,
                        String url,
                        String username,
                        String password,
                        String lastName,
                        PrintWriter out) {
    try {
      Class.forName(driver);
      Connection connection =
        DriverManager.getConnection(url, username, password);
      out.println("<UL>");
      Statement statement = connection.createStatement();
      String query =
        "SELECT * FROM employees WHERE lastname='" +
        lastName + "'";
      ResultSet resultSet = statement.executeQuery(query);
      if (resultSet.next()) {
        System.out.println("In resultSet.next()");
        out.println("<UL>\n" +
                    "  <LI>First name: " +
                    resultSet.getString("firstname") + "\n" +
                    "  <LI>Last name: " +
                    resultSet.getString("lastname") + "\n" +
                    "  <LI>Title: " +
                    resultSet.getString("title") + "\n" +
                    "  <LI>Date of Birth: " +
                    resultSet.getString("birthdate"));
      } else {
        out.println("<H2>No employee with the last name " +
                    "'" + lastName + "'.");
      }
      connection.close();
    } catch(ClassNotFoundException cnfe) {
      System.err.println("Error loading driver: " + cnfe);
    } catch(SQLException sqle) {
      System.err.println("Error connecting: " + sqle);
    } 
  }
}
