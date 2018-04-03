package conceptTesting;

import java.io.*;

import org.w3c.dom.*;

import javax.xml.parsers.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class CreateXml{

    /**
     * Create an xml xtring
     */
    public CreateXml() {
    }
    
    public void createXMLString()
    {
      try {

        //Creating an empty XML Document
        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        //Creating the XML tree

        //create the root element and add it to the document
        Element root = doc.createElement("root");
        doc.appendChild(root);

        //create a comment and put it in the root element
        Comment comment = doc.createComment("This is a comment");
        root.appendChild(comment);

        //create child element, add an attribute, and add to root
        Element child = doc.createElement("test");
        child.setAttribute("name", "value");
        root.appendChild(child);

        //add a text element to the child
        Text text = doc.createTextNode("My node value");
        child.appendChild(text);

        //create child element, add an attribute, and add to root
        child = doc.createElement("test");
        child.setAttribute("name", "value");
        root.appendChild(child);

        //add a text element to the child
        text = doc.createTextNode("My second node value");
        child.appendChild(text);

        //Output the XML to a string

        //set up a transformer
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        trans.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "CDATA");

        //create string from xml tree
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(doc);
        trans.transform(source, result);
        String xmlString = sw.toString();

        //print xml
        System.out.println("xml output :\n\n" + xmlString);

    } catch (Exception e) {
        System.out.println(e);
    }
    }
    
}
	
