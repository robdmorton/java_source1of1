package coreservlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

/** Illustrates the value of persistent HTTP connections for
 *  pages that includes many images, applet classes, or
 *  other auxiliary content that would otherwise require
 *  a separate connection to retrieve.
 *  <P>
 *  Taken from Core Servlets and JSP,
 *  http://www.apl.jhu.edu/~hall/csajsp/.
 *  1999 Marty Hall; may be freely used or adapted.
 */

public class PersistentFrame extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    ByteArrayOutputStream byteStream =
      new ByteArrayOutputStream(2056);
    PrintWriter out = new PrintWriter(byteStream, true);
    String persistenceFlag =
      request.getParameter("usePersistence");
    boolean usePersistence =
      ((persistenceFlag == null) ||
       (!persistenceFlag.equals("no")));
    String title;
    if (usePersistence) {
      title = "Using Persistent Connection";
    } else {
      title = "Not Using Persistent Connection";
    }
    out.println
      ("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
         "Frameset//EN\">\n" +
       "<HTML>\n" +
       "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
       "<FRAMESET ROWS=\"*,*,*,*,*,*,*,*,*,*\">");
    for(int i=0; i<10; i++) {
      out.println(frameRow(i, usePersistence));
    }
    out.println("</FRAMESET></HTML>");
    if (usePersistence) {
      response.setContentLength(byteStream.size());
    }
    byteStream.writeTo(response.getOutputStream());
  }
  
  private String frameRow(int rowNum, boolean usePersistence) {
    int cellNum = rowNum * 10;
    String baseURL = "/servlet/coreservlets.FrameCell?cellNum=";
    String persistenceFlag;
    if (usePersistence)
      persistenceFlag="";
    else
      persistenceFlag="&usePersistence=no";
    String row =
      "  <FRAMESET COLS=\"*,*,*,*,*,*,*,*,*,*\">\n";
    for(int i=0; i<10; i++) {
      row = row + "    <FRAME SRC=\"" + baseURL + (cellNum++) +
            persistenceFlag + "\">\n";
    }
    row = row + "  </FRAMESET>";
    return(row);
  }    
    
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}
