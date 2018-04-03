/*
 * Created on Mar 22, 2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package ws.business;

import java.io.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;

/**
 * @author jlarstone
 * @author pstang
 * @author ihlavats
 * @author you!
 * 
 * <h1 style="font-size:14px;padding-top:20px">Lab Instructions:</h1>
 * 
 * <p>
 * TODO 01. Read this in the Javadoc view (Window > Show View > Java > Javadoc)
 * </p>
 * 
 * <p>
 * In the WSServiceConnector project create a new class called
 * <code>PurchaseSummaryService</code> in the <code>ws.business</code>
 * package.
 * </p>
 * 
 * <p>
 * This class should be modeled after the <code>ExampleService</code> class
 * which is also in the <code>ws.business</code> package.
 * </p>
 * 
 * <p>
 * The new class should assume it is receiving a document that is structured
 * like the purchases.xml downloaded in Part 2.
 * </p>
 * 
 * <p>
 * Upon receiving a purchases XML document, the
 * <code>PurchaseSummaryService</code> class must calculate:
 * </p>
 * 
 * <ol>
 * <li>the total number of purchases 2. the sum of all purchases in the input
 * document</li>
 * <li>The <code>PurchaseSummaryService</code> must then produce an output
 * XML document in the following format:
 * <p>
 * 
 * <pre>
 *  &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt; 
 *  &lt;summary&gt;
 *    &lt;count&gt;total number of purchases&lt;/count&gt; 
 *    &lt;total&gt;the sum of all purchases in the input document&lt;/total&gt; 
 *  &lt;/summary&gt;
 * </pre>
 * 
 * </p>
 * </li>
 * </ol>
 * 
 */
public class PurchaseSummaryService extends DefaultHandler implements
        WSServiceInterface {

    private SAXParser saxParser = null;

    private StringBuffer elementCharacters = new StringBuffer();

    private int purchaseCount = 0;

    private float purchaseTotal = 0.0f;

    public PurchaseSummaryService() {
        // Use the default (non-validating) parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            // Parse the input
            factory.setNamespaceAware(false);
            factory.setValidating(false);
            saxParser = factory.newSAXParser();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see ws.business.WSServiceInterface#process(java.io.Reader)
     */
    public String process(StringBuffer xml) {

        // TODO 02. Reset the purchaseCount and purchaseTotal variables to zero.
        purchaseCount = 0;
        purchaseTotal = 0.0f;

        if (xml != null && xml.length() > 0) {
            try {

                // parse the XML using SAX
                String value = xml.toString();
                DefaultHandler handler = this;
                StringReader reader = new StringReader(value);
                InputSource input = new InputSource(reader);

                // TODO 03. Call the parse() method on the SAX parser.
                // Hint: Pass the InputSource and DefaultHandler as arguments.
                saxParser.parse(input, handler);

                // give variables shorter names
                // for more concise formatting below
                float a = purchaseCount;
                float b = purchaseTotal;

                // here we use the method-chaining technique
                // to assemble the XML output string
                StringBuilder output = new StringBuilder();
                output.append("<?xml version='1.0' encoding='UTF-8'?>\n");
                output.append("<summary>\n");
                output.append("  <count>").append(a).append("</count>\n");
                output.append("  <total>").append(b).append("</total>\n");
                output.append("</summary>");

                return output.toString();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return "<?xml version='1.0' encoding='UTF-8'?>\n"
                + "<error>Purchase count failed.</error>";
    }

    /*
     * This method is called by the SAX parser every time
     * 
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String namespaceURI, String sName, // simple name
            String qName, // qualified name
            Attributes attrs) throws SAXException {
        if (qName.equals("cost"))
            elementCharacters.delete(0, elementCharacters.length());
    }

    /*
     * This method is called by the SAX parser every time it reaches the end tag
     * for a particular element.
     * 
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void endElement(String namespaceURI, String sName, // simple name
            String qName // qualified name
    ) throws SAXException {

        // Here we're looking for a purchase element in the document.

        // TODO 04. Check if the element's qualified name equals "purchase".
        if (qName.equals("purchase")) {
            // TODO 05. Increment the purchaseCount variable.
            purchaseCount++;
        } else if (qName.equals("cost")) {
            // TODO 06. Increment the purchaseTotal variable.
            purchaseTotal += Float.parseFloat(elementCharacters.toString());
        }
    }

    public void characters(char buf[], int offset, int len) throws SAXException {
        String s = new String(buf, offset, len);
        elementCharacters.append(s);
    }
}
