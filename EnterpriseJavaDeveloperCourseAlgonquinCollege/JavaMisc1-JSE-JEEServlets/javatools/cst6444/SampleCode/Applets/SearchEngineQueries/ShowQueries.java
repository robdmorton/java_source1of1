import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

/** Applet reads arrays of strings packaged inside
 *  a QueryCollection and places them in a scrolling
 *  TextArea. The QueryCollection obtains the strings
 *  by means of a serialized object input stream
 *  connected to the QueryGenerator servlet.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class ShowQueries extends Applet
                         implements ActionListener, Runnable {
  private TextArea queryArea;
  private Button startButton, stopButton, clearButton;
  private QueryCollection currentQueries;
  private QueryCollection nextQueries;
  private boolean isRunning = false;
  private String address =
    "/servlet/coreservlets.QueryGenerator";
  private URL currentPage;
  
  public void init() {
    setBackground(Color.white);
    setLayout(new BorderLayout());
    queryArea = new TextArea();
    queryArea.setFont(new Font("Serif", Font.PLAIN, 14));
    add(queryArea, BorderLayout.CENTER);
    Panel buttonPanel = new Panel();
    Font buttonFont = new Font("SansSerif", Font.BOLD, 16);
    startButton = new Button("Start");
    startButton.setFont(buttonFont);
    startButton.addActionListener(this);
    buttonPanel.add(startButton);
    stopButton = new Button("Stop");
    stopButton.setFont(buttonFont);
    stopButton.addActionListener(this);
    buttonPanel.add(stopButton);
    clearButton = new Button("Clear TextArea");
    clearButton.setFont(buttonFont);
    clearButton.addActionListener(this);
    buttonPanel.add(clearButton);
    add(buttonPanel, BorderLayout.SOUTH);
    currentPage = getCodeBase();
    // Request a set of sample queries. They
    // are loaded in a background thread, and
    // the applet checks to see if they have finished
    // loading before trying to extract the strings.
    currentQueries = new QueryCollection(address, currentPage);
    nextQueries = new QueryCollection(address, currentPage);
  }

  /** If you press the "Start" button, the system
   *  starts a background thread that displays
   *  the queries in the TextArea. Pressing "Stop"
   *  halts the process, and "Clear" empties the
   *  TextArea.
   */
 
  public void actionPerformed(ActionEvent event) {
    if (event.getSource() == startButton) {
      if (!isRunning) {
        Thread queryDisplayer = new Thread(this);
        isRunning = true;
        queryArea.setText("");
        queryDisplayer.start();
        showStatus("Started display thread...");
      } else {
        showStatus("Display thread already running...");
      }
    } else if (event.getSource() == stopButton) {
      isRunning = false;
      showStatus("Stopped display thread...");
    } else if (event.getSource() == clearButton) {
      queryArea.setText("");
    }
  }

  /** The background thread takes the currentQueries
   *  object and every half-second places one of the queries
   *  the object holds into the bottom of the TextArea. When
   *  all of the queries have been shown, the thread copies
   *  the value of the nextQueries object into
   *  currentQueries, sends a new request to the server
   *  in order to repopulate nextQueries, and repeats
   *  the process.
   */

  public void run() {
    while(isRunning) {
      showQueries(currentQueries);
      currentQueries = nextQueries;
      nextQueries = new QueryCollection(address, currentPage);
    }
  }

  private void showQueries(QueryCollection queryEntry) {
    // If request has been sent to server but the result
    // isn't back yet, poll every second. This should
    // happen rarely but is possible with a slow network
    // connection or an overloaded server.
    while(!queryEntry.isDone()) {
      showStatus("Waiting for data from server...");
      pause(1);
    }
    showStatus("Received data from server...");
    String[] queries = queryEntry.getQueries();
    String linefeed = "\n";
    // Put a string into TextArea every half-second.
    for(int i=0; i<queries.length; i++) {
      if (!isRunning) {
        return;
      }
      queryArea.append(queries[i]);
      queryArea.append(linefeed);
      pause(0.5);
    }
  }

  public void pause(double seconds) {
    try {
      Thread.sleep((long)(seconds*1000));
    } catch(InterruptedException ie) {}
  }
}
