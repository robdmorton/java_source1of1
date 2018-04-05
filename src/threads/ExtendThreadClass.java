package threads;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ExtendThreadClass extends JFrame {

  // declare a thread object
  MyThread t = new MyThread();
  int number = 0;

  public ExtendThreadClass() {
    // register a window event
    this.addWindowListener(new Window());
  }

  // override the paint method to draw a number
  public void paint(Graphics g) {
    g.clearRect(0, 0, this.getWidth(), this.getHeight());
    g.drawString("" + number, this.getWidth() / 2, this.getHeight() / 2);
  }

  // window event
  class Window extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
      System.exit(1);
    }
  }
  // use an inner class to extend the Thread class
  class MyThread extends Thread {
    // start the thread in the inner class constructor
    MyThread() {
      this.start();
    }

    // Thread code
    public void run() {
      while (true) {
        try {
          this.sleep(1000);
        } catch (Exception e) {
        } finally {
          ExtendThreadClass.this.number++;
          ExtendThreadClass.this.repaint();
        }
      }
    }
  }

  // main method
  public static void main(String[] args) {
    ExtendThreadClass app = new ExtendThreadClass();
    app.setBounds(200, 200, 100, 100);
    app.setTitle("extends Thread");
    app.setVisible(true);
  }
}
