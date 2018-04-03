package lectureN12;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;

/** Makes a big, bold, red, blinking element. 
 */

public class AnnoyingTag extends TagSupport {
  public int doStartTag() {
    try {
      JspWriter out = pageContext.getOut();
      out.print("<H1><FONT COLOR=\"RED\"><BLINK>");
    } catch(IOException ioe) {
      System.out.println("Error in AnnoyingTag: " + ioe);
    }
    return(EVAL_BODY_INCLUDE); // Include tag body
  }

  public int doEndTag() {
    try {
      JspWriter out = pageContext.getOut();
      out.print("</BLINK></FONT></H1>");
    } catch(IOException ioe) {
      System.out.println("Error in AnnoyingTag: " + ioe);
    }
    return(EVAL_PAGE); // Continue with rest of JSP page
  }
}
