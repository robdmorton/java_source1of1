package jdbcExercises;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ShippingCompanies extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType +
                "<HTML>\n" +
                "<HEAD><TITLE>Shipping Companies</TITLE></HEAD>\n" +
                "<BODY BGCOLOR=\"#FDF5E6\"><CENTER>\n" +
                "<H1>Shipping Companies</H1>");
    String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
    String url = "jdbc:odbc:Northwind";
    String username = "";
    String password = "";
    String tableName = "shippers";
    String columnName = "CompanyName";
    showList(driver, url, username, password,
             tableName, columnName, out);
    out.println("</CENTER></BODY></HTML>");
  }

  private void showList(String driver,
                        String url,
                        String username,
                        String password,
                        String tableName,
                        String columnName,
                        PrintWriter out) {
    try {
      Class.forName(driver);
      Connection connection =
        DriverManager.getConnection(url, username, password);
      out.println("<UL>");
      Statement statement = connection.createStatement();
      String query =
        "SELECT CompanyName FROM " + tableName;
      ResultSet resultSet = statement.executeQuery(query);
      out.println("<UL>");
      while(resultSet.next()) {
        out.print("<LI>" + resultSet.getString(columnName));
      }
      out.println("</UL>");
      connection.close();
    } catch(ClassNotFoundException cnfe) {
      System.err.println("Error loading driver: " + cnfe);
    } catch(SQLException sqle) {
      System.err.println("Error connecting: " + sqle);
    } 
  }
}
