package lectureN12;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;

/** Generates a random number between 0 and 1.
 *  Solution to exercise from lecture 12.
 */

public class RandomNumberTag extends TagSupport {
  public int doStartTag() {
    try {
      JspWriter out = pageContext.getOut();
      out.print(Math.random());
    } catch(IOException ioe) {
      System.out.println("Error generating random number: " + ioe);
    }
    return(SKIP_BODY);
  }
}
