/*

ASSIGNMENT #3

Author: Rob Morton
Date:   2011-11-26 

Subscriber XSD Validator
*/
package cst6445.assignment3;

import java.io.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

public class  CST6445Assignment3Validator extends DefaultHandler {

	static private SAXParser saxParser = null;

	private StringBuffer elementCharacters = new StringBuffer();

	public CST6445Assignment3Validator() {
		// Use the validating parser to prove that the XSD is valid
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(true);
		
		SchemaFactory schemaFactory = 
			    SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

		try {
			factory.setSchema(schemaFactory.newSchema(
				new Source[] {new StreamSource("D:\\nms_build_root\\JavaMisc2-WS\\assignments\\CST6445Assignment3v2.xsd")})
			);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		try {
			// Parse the input
			saxParser = factory.newSAXParser();
			//get "Document is invalid: no grammar found" exception without following 2 rows of code...
			XMLReader xmlReader = saxParser.getXMLReader();
			xmlReader.setFeature("http://apache.org/xml/features/validation/schema", true);
		} catch (Throwable t) {
			t.printStackTrace();
		}
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
		if (qName.equals("imsi"))
			elementCharacters.delete(0, elementCharacters.length());
	}

	public void characters(char buf[], int offset, int len) throws SAXException {
		String s = new String(buf, offset, len);
		elementCharacters.append(s);
		System.out.print(s);
	}
	
	public void error(SAXParseException e)
			throws SAXException
	{
		//catch the validity errors here...
		e.printStackTrace();
	}
	
	public static void main(String argv[]) {
		
		DefaultHandler handler = new CST6445Assignment3Validator();
				
		try {
			saxParser.parse("D:\\nms_build_root\\JavaMisc2-WS\\assignments\\CST6445Assignment1.xml", handler);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
