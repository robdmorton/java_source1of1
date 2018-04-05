package threads;

/*
 * AniMain.java - animation demo - Java 1.5 version - 2006 11 22 revised version adds rules dialogue
 */
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

class Circle extends Thread {

  // Properties of a Circle Object
  Graphics2D g; // graphics object for drawing
  Color c; // the Circle's colour
  AniMain m; // reference to the Main Frame
  boolean okToRun = true; // flag to control Threads loop
  int width, height; // size of Main Frame
  final int RADIUS = 5; // size of Circle to draw
  int x, y; // location of Circle
  int speed = 4, dx = speed, dy = speed; // variables to control movement

  // Circle constructor
  Circle(AniMain m, Color c) {
    // assign the colour and main reference passed in
    this.c = c;
    this.m = m;
    // start the Circle Thread
    this.start();
    // obtain the width and height of Main Frame
    width = m.getWidth();
    height = m.getHeight();
    // generate random location to display Circle for the first time
    x = (int) (Math.random() * (width - 2 * RADIUS)) + RADIUS;
    y = (int) (Math.random() * (height - 2 * RADIUS)) + RADIUS;
  }

  public void run() {
    while (okToRun == true) {
      // create the graphics object for drawing on the Frame
      g = (Graphics2D) m.getGraphics();
      // erase the Circle first
      g.setColor(Color.white);
      g.drawOval(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
      // update the width and height of Frame in case user re-sizes
      width = m.getWidth();
      height = m.getHeight();
      // the following logic determines the movement/direction of Circle
      if (x <= RADIUS)
        dx = speed;
      if (x >= width - RADIUS)
        dx = -speed;
      if (y <= RADIUS)
        dy = speed;
      if (y >= height - RADIUS)
        dy = -speed;
      x += dx;
      y += dy;
      // draw the Circle in it's new position
      g.setColor(c);
      g.drawOval(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
      // the following logic checks to see if the Circle was drawn
      // at the current location of the mouse. If it was,the main
      // thread is stopped by setting the okToRun boolean to false
      if (((m.x >= (x - RADIUS)) && (m.x <= (x - RADIUS + 10)))
          && ((m.y >= (y - RADIUS)) && (m.y <= (y - RADIUS + 10)))) {
        m.okToRun = false;
        this.okToRun = false;
        // the circle that hit the mouse is re-drawn in solid black
        g.setColor(Color.black);
        g.fillOval(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
      }
      // sleep the thread for a wee while between iterations
      try {
        Thread.sleep(100);
      } catch (Exception e) {
      }
    }
  }
}


public class AniMain extends Frame implements Runnable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // the main controlling Thread object creates all Circle Threads
  Thread t;
  // the x & y coordinates of current mouse location
  // all Circle threads need to see these variables
  int x, y;
  // an ArrayList to hold all Circle Thread Objects
  ArrayList<Circle> threads = new ArrayList<Circle>();
  // a flag that controls the main thread's loop
  boolean okToRun = true;
  // a simple GUI menu structure
  MenuBar bar = new MenuBar();
  Menu game = new Menu("Game");
  MenuItem gameRules = new MenuItem("Rules");
  MenuItem newGame = new MenuItem("New Game");

  // Frame constructor
  public AniMain() { // build GUI
    super("Missing Circles");
    this.setBounds(100, 100, 400, 400);
    this.setBackground(Color.white);
    game.add(gameRules);
    game.add(newGame);
    bar.add(game);
    this.setMenuBar(bar);
    // register events
    this.addWindowListener(new Window());
    this.addMouseMotionListener(new Mouse());
    gameRules.addActionListener(new Rules());
    newGame.addActionListener(new Game());
    this.setVisible(true);
  }

  // the main thread's code
  public void run() {
    // the main thread will continue to execute until the mouse touches
    // a circle. The circle thread object will then set the okToRun
    // boolean to false causing the main thread's loop to end
    while (okToRun == true) {
      // sleep for a second before creating another circle thread
      try {
        t.sleep(1000);
      } catch (Exception e) {
      }
      // generate 3 random integer numbers
      int r = (int) (Math.random() * 200);
      int g = (int) (Math.random() * 200);
      int b = (int) (Math.random() * 200);
      // create a unique Color for each circle using the random numbers
      Color colour = new Color(r, g, b);
      // create a new Circle thread and add to the ArrayList
      // each Circle object is passed a reference to the frame
      // and a unique colour
      threads.add(new Circle(this, colour));
    }
    // call a private method at the end of each game to clean up
    gameOver();
  }

  // menu event fires each time the user clicks on
  // the New Game option from the pull-down menu
  class Game implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      // create a graphics object to clear the frame
      Graphics2D g = (Graphics2D) AniMain.this.getGraphics();
      // set the colour to white-same as Frame's background
      // draw a rectangle the size of the Frame
      g.setColor(Color.white);
      g.fillRect(0, 0, AniMain.this.getWidth(), AniMain.this.getHeight());
      // set okToRun to true to re-initialize main thread's loop
      okToRun = true;
      // construct and start the main thread
      t = new Thread(AniMain.this);
      t.start();
      // disable the New Game menu option while game is being played
      game.setEnabled(false);
    }
  }

  // menu event fires each time the user clicks on Rules option from pull-down
  class Rules implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String msg = "To play this game,touch a circle with the mouse!";
      JOptionPane.showMessageDialog(null, msg, "Message Dialog", JOptionPane.PLAIN_MESSAGE);
    }
  }

  // Frame is register to receive mouseMoved events
  // this event fires every time the mouse moves and the code updates
  // the x and y variables. Each Circle thread will look at these
  // variables to determine if they hit the mouse
  class Mouse extends MouseMotionAdapter {
    public void mouseMoved(MouseEvent e) {
      x = e.getX();
      y = e.getY();
    }
  }

  // end the program when the user hits the Frame's close button
  class Window extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
      System.exit(1);
    }
  }

  // a private method that is called at the end of each game
  private void gameOver() {
    // each thread object has okToRun boolean that controls its own loop.
    // Cycle through the ArrayList and terminate each thread's loop.
    int numberOfCircles = threads.size() - 1;
    for (int x = 0; x <= numberOfCircles; x++) {
      Circle c = (Circle) threads.get(x);
      c.okToRun = false;
    }
    // display a closing message to the user indicating how
    // many circles appeared before one of them hit the mouse.
    JOptionPane.showMessageDialog(null, "Game Over " + numberOfCircles + " Circles!");
    // re-enable the New Game menu option so the user can play again
    threads.clear();
    game.setEnabled(true);
  }

  // main starts the program
  public static void main(String[] args) {
    new AniMain();
  }
}
