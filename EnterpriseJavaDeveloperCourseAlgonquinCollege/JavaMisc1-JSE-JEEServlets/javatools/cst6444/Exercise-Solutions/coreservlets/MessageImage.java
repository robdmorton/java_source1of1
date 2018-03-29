package coreservlets;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import Acme.JPM.Encoders.GifEncoder;

/** Utilities for building images showing shadowed messages.
 *  Includes a routine that uses Jef Poskanzer's GifEncoder
 *  to return the result as a GIF.
 *  <P>
 *  <B>Does not run in JDK 1.1, since it relies on Java2D
 *  to build the images.</B>
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class MessageImage {
  
  /** Creates an Image of a string with an oblique
   *  shadow behind it. Used by the ShadowedText servlet
   *  and the ShadowedTextFrame desktop application.
   */

  public static Image makeMessageImage(String message,
                                       String fontName,
                                       int fontSize) {
    Frame f = new Frame();
    // Connect to native screen resource for image creation.
    f.addNotify();
    // Make sure Java knows about local font names.
    GraphicsEnvironment env = 
      GraphicsEnvironment.getLocalGraphicsEnvironment();
    env.getAvailableFontFamilyNames(); 
    Font font = new Font(fontName, Font.PLAIN, fontSize);
    FontMetrics metrics = f.getFontMetrics(font);
    int messageWidth = metrics.stringWidth(message);
    int baselineX = messageWidth/10;
    int width = messageWidth+2*(baselineX + fontSize);
    int height = fontSize*7/2;
    int baselineY = height*8/10;
    Image messageImage = f.createImage(width, height);
    Graphics2D g2d =
      (Graphics2D)messageImage.getGraphics();
    g2d.setFont(font);
    g2d.translate(baselineX, baselineY);
    g2d.setPaint(Color.lightGray);
    AffineTransform origTransform = g2d.getTransform();
    g2d.shear(-0.95, 0);
    g2d.scale(1, 3);
    g2d.drawString(message, 0, 0);
    g2d.setTransform(origTransform);
    g2d.setPaint(Color.black);
    g2d.drawString(message, 0, 0);
    return(messageImage);
  }

  /** Uses GifEncoder to send the Image down output stream
   *  in GIF89A format. See http://www.acme.com/java/ for
   *  the GifEncoder class.
   */
  
  public static void sendAsGIF(Image image, OutputStream out) {
    try {
      new GifEncoder(image, out).encode();
    } catch(IOException ioe) {
      System.err.println("Error outputting GIF: " + ioe);
    }
  }
}
