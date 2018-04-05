package introductionToJava.lab1;

import java.awt.Color;
import java.awt.Graphics;

public class MyApplet extends java.applet.Applet {
  private int numberOfPaints = 0;
  private int numberOfInits = 0;
  private int numberOfStarts = 0;
  private int numberOfStops = 0;
  private int numberOfDestroys = 0;

  public void init() {
    System.out.println("in Init");
    System.out.println("Width:" + getWidth());
    System.out.println("Height:" + getHeight());
    numberOfInits++;
    numberOfPaints = 0;
  }

  public void paint(Graphics g) {
    numberOfPaints++;
    System.out.println("in Paint :" + numberOfPaints);
    g.setClip(0, 0, 300, 300);
    g.setColor(Color.lightGray);
    g.fillRect(0, 0, 300, 300);
    g.setColor(Color.red);
    g.drawString("Number of Paints:" + numberOfPaints, 10, 30);
    g.drawString("Number of Inits:" + numberOfInits, 10, 50);
    g.drawString("Number of Starts:" + numberOfStarts, 10, 70);
    g.drawString("Number of Stops:" + numberOfStops, 10, 90);
    g.drawString("Number of Destroys:" + numberOfDestroys, 10, 110);
  }

  public void start() {
    System.out.println("in Start");
    numberOfStarts++;
  }

  public void stop() {
    System.out.println("in Stop");
    numberOfStops++;
  }

  public void destroy() {
    System.out.println("in Destroy");
    numberOfDestroys++;
  }
}
