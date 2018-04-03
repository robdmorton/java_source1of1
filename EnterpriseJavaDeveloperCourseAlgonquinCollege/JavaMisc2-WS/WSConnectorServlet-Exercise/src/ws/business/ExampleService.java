package ws.business;

import java.io.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;

/**
 * @author jlarstone
 *
 * This service counts the number of elements in any
 * XML document using a SAX parser.
 */
public class ExampleService extends DefaultHandler implements WSServiceInterface {

	// the parser
	private SAXParser saxParser = null;
	
	// collects element character data
	private StringBuffer elementCharacters = new StringBuffer();

	// accumlates element count
	private int elementCount = 0;

	/*
	 *  Create a SAX parser.
	 */	
	public ExampleService() {
		// Use the default (non-validating) parser
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// Not dealing with name spaces yet
			factory.setNamespaceAware(false);
			// not expecting a DTD or schema
			factory.setValidating(false);
			// create the parser
			saxParser = factory.newSAXParser();
		} catch (Throwable t) {
			t.printStackTrace();
		}		
	}

	/* 
	 * WSServiceInterfac e implementation. 
	 * Parse the input document and return a result document.
	 */
	public String process(StringBuffer xml) {
		// reset values
		elementCount = 0;
		
		if ( xml != null && xml.length() > 0 ) {
			try {
				// start parsing
				saxParser.parse(
					new InputSource(
						new StringReader(xml.toString())), this);
						
				return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
					   "<result>\n" +
					   "  <count>" + elementCount + "</count>\n" +
					   "</result>";
					   
			} catch ( Exception ex ) {
				ex.printStackTrace();
			}
		}
			
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			   "<error>\n" +
			   "  <message>Element count failed.</message>\n" +
			   "</error>";
	}

	/* 
	 * Prepare for element counting.
	 */
	public void startDocument() throws SAXException {
		// reset element counter
		elementCount = 0;
	}

	/* 
	 * Clean up.
	 */
	public void endDocument() throws SAXException {
		// delete accumulated character data if any
		elementCharacters.delete(0, elementCharacters.length());		
	}

	/*
	 * When a start element occurs all we want to do
	 * is remove the character data associated with the
	 * previous element.
	 */
	public void startElement(String namespaceURI,
							 String sName, // simple name
							 String qName, // qualified name
							 Attributes attrs)
	throws SAXException
	{
		// delete accumulated element character data
		elementCharacters.delete(0, elementCharacters.length());
	}

	/*
	 * When an end element occurs update the element count.
	 * This is also the time to use any character data
	 * collected for an element.
	 */
	public void endElement(String namespaceURI,
						   String sName, // simple name
						   String qName  // qualified name
						  )
	throws SAXException
	{
		// if we were looking for specific element names
		// we would use the qName parameter because
		// this is not namespace aware.
		elementCount++;
	}

	/*
	 * Called for each chunk of character data associated
	 * with an element.
	 */    
	public void characters(char buf[], int offset, int len)
	throws SAXException
	{
		// accumulate element character data
		String s = new String(buf, offset, len);
		elementCharacters.append(s);
	}



}
