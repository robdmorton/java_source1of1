package ws.client;

import java.net.URL;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

public class BookStoreClient {

    public static void main(String args[]) {
        if (args.length < 2) {
            String spec = "Usage: java %1$s url title [debug]";
            String name = BookStoreClient.class.getName();
            String usage = String.format(spec, name);
            System.err.println(usage);
            System.exit(1);
        }
        BookStoreClient c = new BookStoreClient();
        c.setUrlstring(args[0]);
        c.setTitle(args[1]);
        if (args.length > 2) {
            c.setDebug(args[2]);
        }
        c.submitRequest();
    }

    private String debug;

    private String title;

    private String urlstring;

    private String xmlfile;

    public BookStoreClient() {
    }

    public String getDebug() {
        return debug;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlstring() {
        return urlstring;
    }

    public String getXmlfile() {
        return xmlfile;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrlstring(String urlstring) {
        this.urlstring = urlstring;
    }

    public void setXmlfile(String xmlfile) {
        this.xmlfile = xmlfile;
    }

    public void submitRequest() {
        SOAPConnection connection = null;
        try {
            // Create connection and message factory
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory
                    .newInstance();
            MessageFactory messageFactory = MessageFactory.newInstance();

            // Create a message
            SOAPMessage message = messageFactory.createMessage();

            // Get the SOAP header and body from the message
            // and remove the header
            SOAPHeader header = message.getSOAPHeader();
            SOAPBody body = message.getSOAPBody();
            header.detachNode();

            // Create a SOAP factory
            SOAPFactory soapFactory = SOAPFactory.newInstance();

            // Create a SOAP message
            Name search = soapFactory.createName("search", "", "urn:cst6445");
            Name title = soapFactory.createName("title");
            SOAPBodyElement request = body.addBodyElement(search);
            SOAPElement element = request.addChildElement(title);

            SOAPElement echo = request.addChildElement(soapFactory
                    .createName("echo"));
            echo.addTextNode("Hello World!");
            element.addTextNode(this.getTitle());

            message.saveChanges();

            // Display the message you are sending
            System.out.println("\n--- Request Message ---\n");
            message.writeTo(System.out);

            if (debug == null) {

                // Create a SOAP connection
                connection = soapConnectionFactory.createConnection();

                // Retrieve the endpoint from the command line
                URL endpoint = new URL(this.getUrlstring());

                // Send message and get reply
                SOAPMessage reply = connection.call(message, endpoint);
                if (reply != null) {
                    System.out.println("\n\nReceived reply from: " + endpoint);
                    System.out.println("\n---- Reply Message ----\n");
                    // Display the reply
                    reply.writeTo(System.out);
                    // process the reply
                    // ...
                } else {
                    System.out.println("Book not found.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SOAPException e) {
                System.out.println("Couldn't close connection: "
                        + e.getMessage());
            }
        }
    }
}
