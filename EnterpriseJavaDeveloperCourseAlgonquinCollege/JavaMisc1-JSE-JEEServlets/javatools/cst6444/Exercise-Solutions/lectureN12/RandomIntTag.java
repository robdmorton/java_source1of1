package lectureN12;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;

/** Generates a random int between 1 and some specified number.
 *  Solution to exercise from lecture 12.
 */

public class RandomIntTag extends TagSupport {
  private int range = 10;

  public void setRange(String rangeString) {
    try {
      range = Integer.parseInt(rangeString);
    } catch(NumberFormatException nfe) {
      // Do nothing, since range already has default value
    }
  }
  
  public int doStartTag() {
    try {
      JspWriter out = pageContext.getOut();
      out.print(1 + (int)(Math.random() * range));
    } catch(IOException ioe) {
      System.out.println("Error generating random int: " + ioe);
    }
    return(SKIP_BODY);
  }
}
