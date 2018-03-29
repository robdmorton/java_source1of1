package coreservlets;

import java.awt.*;
import java.awt.event.*;

/** A listener that you attach to the top-level Frame
 *  or JFrame of your application, so quitting the
 *  frame exits the application.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class ExitListener extends WindowAdapter {
  public void windowClosing(WindowEvent event) {
    System.exit(0);
  }
}
