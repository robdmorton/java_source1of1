package ws.client;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * @author jlarstone
 * 
 * Simple command line client program used to transmit a XML file to a service
 * and receive XML in return.
 */
public class WSClient {

    private final static String URLOPT = "-url";

    private final static String XMLOPT = "-xml";

    private final static String ECHOOPT = "-echo";

    private static DocumentBuilderFactory factory = null;

    public static void print(String s) {
        System.out.println(s);
    }

    // TODO 11a. Open query.xml and read the comment there.
    // TODO 12. Copy and paste the query.xml file to C:\Temp\query.xml.
    // TODO 13. Deploy the web application to the JWSDP Tomcat.
    // TODO 14. Run the client program using the arguments below.

    // The URL should be:
    // 
    // http://localhost:8080/CONTEXT_PATH/WSConnectorServlet
    // 
    // where CONTEXT_PATH is the context name of the Web application
    // (look it up in the Eclipse project properties under J2EE)

    // The XML file path should be:
    // C:\Temp\query.xml

    // Tip: You can create a run configuration in Eclipse and
    // specify arguments using the Arguments tab. Be sure to start
    // Tomcat before running the client.

    // The correct output of this program is:

    // XML Version: 1.0
    // XML Encoding: UTF-8
    // ---------
    // ELEMENT: summary
    // ELEMENT: count
    // TEXT: "2"
    // ELEMENT: total
    // TEXT: "17.33"

    public static void printUsage() {
        print("Usage: java ws.client.WSClient -url URL-string -xml XML-file");
        print("       java ws.client.WSClient -echo -xml XML-file");
    }

    private static void urlSend(URL url, File file) throws Exception {

        char[] buffer = new char[(int) file.length()];

        // get a FileReader for the input document
        FileReader fr = new FileReader(file);

        // read the file all in one go
        int bytes_read = 0;
        if ((bytes_read = fr.read(buffer)) != -1) {

            // open service connection
            URLConnection urlc = url.openConnection();

            // we assume its an xml document we are sending
            urlc.setRequestProperty("Content-Type", "text/xml");

            // indicate we are both sending and receiving
            urlc.setDoOutput(true);
            urlc.setDoInput(true);

            // get a PrintWriter for delivering character data
            PrintWriter pw = new PrintWriter(urlc.getOutputStream());

            // send the xml document to the service
            pw.write(buffer, 0, bytes_read);

            // close output
            pw.close();

            domEcho(urlc.getInputStream());
            urlc.getInputStream().close();

        }
    }

    /**
     * Generates a DOM from a input stream reference then echos the DOM to
     * System.out.
     * 
     * @param xml
     * @throws Exception
     */
    private static void domEcho(InputStream xml) throws Exception {

        Document document = factory.newDocumentBuilder().parse(xml);

        domEcho(document);
    }

    /**
     * Generates a DOM from a File reference then echos the DOM to System.out.
     * 
     * @param xml
     * @throws Exception
     */
    private static void domEcho(File xml) throws Exception {

        Document document = factory.newDocumentBuilder().parse(xml);

        domEcho(document);
    }

    /**
     * Echo a Document to System.out.
     * 
     * @param document
     * @throws Exception
     */
    private static void domEcho(Document document) throws Exception {

        print("XML Version:    " + document.getXmlVersion());
        print("XML Encoding:   " + document.getXmlEncoding());
        print("---------");

        DocumentType type = document.getDoctype();
        if (type != null) {
            print("DOCTYPE: " + type.getName());
            print("SYSTEM:  " + type.getSystemId());
            print("PUBLIC:  " + type.getPublicId());
            print("---------");
        }

        NodeList nodes = document.getChildNodes();
        for (int i = 0, j = nodes.getLength(); i < j; i++)
            domEcho(nodes.item(i));

    }

    /**
     * Echo a node and all its children.
     * 
     * @param node
     * @throws Exception
     */
    private static void domEcho(Node node) throws Exception {

        switch (node.getNodeType()) {
        case Document.COMMENT_NODE:
            print("COMMENT: \"" + node.getNodeValue() + "\"");
            break;

        case Document.ELEMENT_NODE:
            print("ELEMENT: " + node.getNodeName());
            NamedNodeMap attributes = node.getAttributes();
            for (int i = 0, j = attributes.getLength(); i < j; i++) {
                Attr item = (Attr) attributes.item(i);
                print("ATTRIBUTE: " + item.getName() + "=\"" + item.getValue()
                        + "\"");
            }
            break;

        case Document.TEXT_NODE:
            if (((Text) node).isElementContentWhitespace() == false)
                print("TEXT:    \"" + node.getNodeValue() + "\"");
            break;
        }

        NodeList children = node.getChildNodes();
        for (int i = 0, j = children.getLength(); i < j; i++) {
            domEcho(children.item(i));
        }
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            printUsage();
            System.exit(1);
        }

        try {
            int echo = 0;
            URL url = null;
            File xml = null;

            // That file represents the search criteria that this client
            // will sending to the web service. We are asking for
            // all sales transactions from a particular customer.

            for (int i = 0; i < args.length; i++) {
                if (args[i].equalsIgnoreCase(URLOPT)) {
                    i++;
                    url = new URL(args[i]);
                } else if (args[i].equalsIgnoreCase(XMLOPT)) {
                    i++;
                    xml = new File(args[i]);
                } else if (args[i].equalsIgnoreCase(ECHOOPT)) {
                    echo = 1;
                } else {
                    System.out.println("Unknown argument: " + args[i]);
                    printUsage();
                    System.exit(1);
                }
            }

            if (xml == null) {
                printUsage();
                System.exit(1);
            }

            if (echo == 0 && url == null) {
                printUsage();
                System.exit(1);
            }

            factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setValidating(false);

            if (echo == 0)
                urlSend(url, xml);
            else
                domEcho(xml);

        } catch (Exception e) {
            // if there's a document or server error it will show
            // up here as well as local IO errors.
            e.printStackTrace();
        }
    }
}
