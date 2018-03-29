package coreservlets;

import java.awt.*;
import javax.swing.*;

/** A small JPanel that includes a JLabel to the left
 *  of a designated component. Also puts a titled border
 *  around the panel.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class LabelPanel extends JPanel {
  public LabelPanel(String labelMessage, String title,
                    Color bgColor, Font font,
                    JComponent component) {
    setBackground(bgColor);
    setFont(font);
    setBorder(BorderFactory.createTitledBorder(title));
    JLabel label = new JLabel(labelMessage);
    label.setFont(font);
    add(label);
    component.setFont(font);
    add(component);
  }
}
