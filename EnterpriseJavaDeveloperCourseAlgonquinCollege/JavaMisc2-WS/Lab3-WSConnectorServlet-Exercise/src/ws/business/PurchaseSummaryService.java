package ws.business;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PurchaseSummaryService extends DefaultHandler implements
        WSServiceInterface {

    // SAX parser for retreiving search criteria
    private SAXParser saxParser = null;

    // Collects search criteria data
    private StringBuffer elementCharacters = new StringBuffer();

    // Sales database document
    private Document salesDocument = null;

    // All the "custid" elements fromthe sales document.
    private NodeList idList = null;

    // Variables for the output document
    private int purchaseCount = 0;

    private float purchaseTotal = 0.0f;

    // Output from the SAX parser
    private String custid = null;

    private static final String PROLOG = "<?xml version='1.0' encoding='UTF-8'?>";

    /**
     * Create all parsers to be used.
     */
    public PurchaseSummaryService() {
        try {
            // TODO 04a. Finish the implementation of this method below.
            // See next to do task.
            createSaxParser(); // SAX parser for retrieving search criteria
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * WSServiceInterface implementation. This will be called for each request.
     */
    public String process(String url, InputStream inputStream) {

        String result = null;
        try {
            // TODO 05a. Finish the implementation of this method below.
            // See next to do task.
            this.salesDocument = loadSalesXMLintoDOM(url);

            // get all the customer id elements here instead of
            // doing it for each request
            this.idList = salesDocument.getElementsByTagName("custid");

            // Fail fast if no sales information.
            if (salesDocument == null || idList == null
                    || idList.getLength() == 0) {
                return PROLOG + "\n" + "<error>Sales unavailable.</error>";
            }

            if (inputStream != null) {
                // Parse the input document to get the search criteria

                // TODO 09. Create a new InputSource from the InputStream.
                InputSource source = new InputSource(inputStream);

                // TODO 10. Parse the InputSource using the SAX parser.
                saxParser.parse(source, this); // uncomment and complete this statement

                if (custid == null) {
                    result = PROLOG + "\n"
                            + "<error>No customer id provided.</error>";
                } else {
                    // We have a custid string - perform the search.
                    result = searchSales();
                }
            } else {
                result = PROLOG + "\n" + "<error>No search criteria.</error>";
            }
        } catch (Exception e) {
            // Probably a parsing error
            e.printStackTrace();
            result = PROLOG + "\n" + "<error>Search failed.</error>";
        }
        return result;
    }

    /**
     * OLD WSServiceInterface implementation. This will be called for each
     * request.
     */
    public String process(StringBuffer xml) {
        // Fail fast if no sales information.
        if (salesDocument == null || idList == null || idList.getLength() == 0) {
            return PROLOG + "\n" + "<error>Sales unavailable.</error>";
        }

        if (xml != null && xml.length() > 0) {
            try {
                // Parse the input document to get the search criteria
                saxParser.parse(new InputSource(
                        new StringReader(xml.toString())), this);

                if (custid == null)
                    return PROLOG + "\n"
                            + "<error>No customer id provided.</error>";

                // We have a custid string - perform the search.
                return searchSales();

            } catch (Exception ex) {
                // Probably a parsing error
                ex.printStackTrace();
                return PROLOG + "\n" + "<error>Search failed.</error>";
            }
        }

        return PROLOG + "\n" + "<error>No search criteria.</error>";
    }

    /**
     * Creates an instance of a SAX Parser.
     * 
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    private void createSaxParser() throws ParserConfigurationException,
            SAXException {
        // TODO 04b. Create a new SAXParserFactory.
        SAXParserFactory factory = SAXParserFactory.newInstance();

        // Set up parsing behaviour
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        // create the reusable parser
        this.saxParser = factory.newSAXParser();
    }

    private Document loadSalesXMLintoDOM(String salesURL)
            throws MalformedURLException, IOException,
            ParserConfigurationException, SAXException {
        // Load the sales DB document. Normally we
        // would utilize some other Business Object to
        // accomplish this task.

        // Create an URL so we can pass its InputStream to
        // the DOM parser.
        URL url = new URL(salesURL);
        URLConnection urlc = url.openConnection();

        // Create the document with default parsing behaviour

        // TODO 05b. Create a new DocumentBuilderFactory.
        DocumentBuilderFactory domfactory = DocumentBuilderFactory
                .newInstance();

        domfactory.setNamespaceAware(false);
        domfactory.setValidating(false);

        // Parse the xml document containing all customer
        // purchases

        // TODO 06. Get the InputStream from the URL connection.
        InputStream in = urlc.getInputStream();

        // TODO 07. Get a new DOM DocumentBuilder instance.
        DocumentBuilder documentBuilder = domfactory.newDocumentBuilder();

        // TODO 08. Construct a new DOM object using the DocumentBuilder.
        // Tip: Parse the InputStream.
        Document salesDocument = documentBuilder.parse(in);

        return salesDocument;
    }

    /**
     * Search the salesDocument for customers matching the input customer id and
     * calculate the total purchases. Produce the output document.
     * 
     * @return
     */
    private String searchSales() {
        // reset output variables
        purchaseCount = 0;
        purchaseTotal = 0.0f;

        // foreach custid element
        for (int i = 0, j = idList.getLength(); i < j; i++) {
            Element el = (Element) idList.item(i);
            // see if the element contains the text
            // we want.
            if (containsText(el, custid)) {
                // get the customer element that this custid
                // element is a descendant of
                Element customer = (Element) el.getParentNode().getParentNode();

                // do the calculation
                calculatePurchases(customer);

                if (purchaseCount == 0) {
                    return PROLOG + "\n" + "<error>No purchases.</error>";
                }

                // we have successfully calculated the purchases
                return PROLOG + "\n" + "<summary>" + "<count>" + purchaseCount
                        + "</count>" + "<total>" + purchaseTotal + "</total>"
                        + "</summary>";
            }
        }
        // no custid elements
        return PROLOG + "\n" + "<error>No customers.</error>";
    }

    /**
     * Checks an ELement if it contains a direct decendant text node that match
     * the text we are searching for.
     * 
     * @param element
     * @param text
     * @return
     */
    private boolean containsText(Element element, String text) {
        NodeList nodes = element.getChildNodes();

        for (int i = 0, j = nodes.getLength(); i < j; i++) {
            Node n = nodes.item(i);

            if (n.getNodeType() == Node.TEXT_NODE
                    && n.getNodeValue().trim().equals(text))
                return true;
        }
        return false;
    }

    /**
     * Get all the cost elements that are decendants of this customer element
     * and compute the values for the output document.
     * 
     * @param customer
     */
    private void calculatePurchases(Element customer) {
        NodeList nodes = customer.getElementsByTagName("cost");
        // The number of purchases is simply
        // the number of cost elements. We dont
        // care if there was a purchase value of 0.
        purchaseCount = nodes.getLength();

        // foreach cost element
        for (int i = 0, j = purchaseCount; i < j; i++) {
            Element cost = (Element) nodes.item(i);
            NodeList nl = cost.getChildNodes();
            for (int x = 0, y = nl.getLength(); x < y; x++) {
                Node n = nl.item(x);
                // to ensure correctness perform stringent
                // checks on the text value
                if (n.getNodeType() == Node.TEXT_NODE) {
                    String s = n.getNodeValue();
                    if (s != null) {
                        s = s.trim();
                        if (s.length() > 0)
                            purchaseTotal += Float.parseFloat(s);
                    }
                }
            }
        }
    }

    /**
     * Overrides DefaultHandler implementation of ContentHandler interface.
     */
    public void startDocument() throws SAXException {
        // reset values
        custid = null;
    }

    /**
     * Overrides DefaultHandler implementation of ContentHandler interface.
     */
    public void startElement(String namespaceURI, String sName, // simple name
            String qName, // qualified name
            Attributes attrs) throws SAXException {
        // clear character data for new element
        elementCharacters.delete(0, elementCharacters.length());
    }

    /**
     * Overrides DefaultHandler implementation of ContentHandler interface.
     */
    public void endElement(String namespaceURI, String sName, // simple name
            String qName // qualified name
    ) throws SAXException {
        // expects only simple name for now
        if (qName.equals("id")) {
            // get search criteria
            custid = elementCharacters.toString().trim();
        }
    }

    /**
     * Overrides DefaultHandler implementation of ContentHandler interface.
     */
    public void characters(char buf[], int offset, int len) throws SAXException {
        // collect character data
        String s = new String(buf, offset, len);
        elementCharacters.append(s);
    }

}
