import java.net.*;
import java.io.*;

/** When this class is built, it returns a value
 *  immediately, but this value returns false for isDone
 *  and null for getQueries. Meanwhile, it starts a Thread
 *  to request an array of query strings from the server,
 *  reading them in one fell swoop by means of an
 *  ObjectInputStream. Once they've all arrived, they
 *  are placed in the location getQueries returns,
 *  and the isDone flag is switched to true.
 *  Used by the ShowQueries applet.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class QueryCollection implements Runnable {
  private String[] queries;
  private String[] tempQueries;
  private boolean isDone = false;
  private URL dataURL;

  public QueryCollection(String urlSuffix, URL currentPage) {
    try {
      // Only the URL suffix need be supplied, since
      // the rest of the URL is derived from the current page.
      String protocol = currentPage.getProtocol();
      String host = currentPage.getHost();
      int port = currentPage.getPort();
      dataURL = new URL(protocol, host, port, urlSuffix);
      Thread queryRetriever = new Thread(this);
      queryRetriever.start();
    } catch(MalformedURLException mfe) {
      isDone = true;
    }
  }

  public void run() {
    try {
      tempQueries = retrieveQueries();
      queries = tempQueries;
    } catch(IOException ioe) {
      tempQueries = null;
      queries = null;
    }
    isDone = true;
  }

  public String[] getQueries() {
    return(queries);
  }

  public boolean isDone() {
    return(isDone);
  }

  private String[] retrieveQueries() throws IOException {
    URLConnection connection = dataURL.openConnection();
    // Make sure browser doesn't cache this URL, since
    // I want different queries for each request.
    connection.setUseCaches(false);
    // Use ObjectInputStream so I can read a String[]
    // all at once.
    ObjectInputStream in =
      new ObjectInputStream(connection.getInputStream());
    try {
      // The return type of readObject is Object, so
      // I need a typecast to the actual type.
      String[] queryStrings = (String[])in.readObject();
      return(queryStrings);
    } catch(ClassNotFoundException cnfe) {
      return(null);
    }
  }
}
