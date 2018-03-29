package ws.business;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BookSearchEngine {

    // used to look up authors
    private Document document = null;

    // holds the title element value from the input document
    private String searchTitle = null;

    public BookSearchEngine()
    {
        try {
            DocumentBuilderFactory domBuilderFactory = null;
            domBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = domBuilderFactory.newDocumentBuilder();
            // get the external xml representing the book list
            document = builder.parse("d:\\tmp\\bookstore.xml");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public BookSearchEngine(URL bookstoreXML) {
        try {
            DocumentBuilderFactory domBuilderFactory = null;
            domBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = domBuilderFactory.newDocumentBuilder();
            // get the external xml representing the book list
            document = builder.parse(bookstoreXML.openStream());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private BookSearchResult buildBook(Node book) {
        BookSearchResult result = new BookSearchResult();
        ;
        if (book != null) {
            // get the element data
            book.normalize();
            NodeList nl = book.getChildNodes();
            for (int i = 0, j = nl.getLength(); i < j; i++) {
                Node el = nl.item(i);
                if (el.getNodeType() == Document.ELEMENT_NODE) {
                    if (el.getNodeName().equals("author"))
                        result.setAuthor(el.getFirstChild().getNodeValue());
                    else if (el.getNodeName().equals("title"))
                        result.setTitle(el.getFirstChild().getNodeValue());
                    else if (el.getNodeName().equals("genre"))
                        result.setGenre(el.getFirstChild().getNodeValue());
                    else if (el.getNodeName().equals("quantity"))
                        result.setQuantity(Integer.parseInt(el.getFirstChild()
                                .getNodeValue()));
                    else
                        ; // d'oh
                }
            }
        } else {
            // the following is for testing purposes only
            result.setAuthor("Sorry - Author Not Found");
            result.setTitle(searchTitle);
            result.setGenre("");
            result.setQuantity(-1);
        }
        return result;
    }

    private Node findBookByTitle(String title) {
        // look in the document for a node matching the <title> element data
        NodeList titles = document.getElementsByTagName("title");
        for (int i = 0, j = titles.getLength(); i < j; i++) {
            Node t = titles.item(i);
            t.normalize();

            NodeList tlist = t.getChildNodes();
            for (int k = 0, l = tlist.getLength(); k < l; k++) {
                if (tlist.item(k).getNodeType() == Document.TEXT_NODE) {
                    if (tlist.item(k).getNodeValue().equals(title)) {
                        // we have a match
                        return t.getParentNode();
                    }
                }
            }
        }
        return null;
    }

    private Node findBookByGenre(String genre) {
        // look in the document for a node matching the <title> element data
        NodeList titles = document.getElementsByTagName("genre");
        for (int i = 0, j = titles.getLength(); i < j; i++) {
            Node t = titles.item(i);
            t.normalize();

            NodeList tlist = t.getChildNodes();
            for (int k = 0, l = tlist.getLength(); k < l; k++) {
                if (tlist.item(k).getNodeType() == Document.TEXT_NODE) {
                    if (tlist.item(k).getNodeValue().equals(genre)) {
                        // we have a match
                        return t.getParentNode();
                    }
                }
            }
        }
        return null;
    }

    private Node[] getAllBooksByGenre(String genre) {
    	ArrayList<Node> bookList = null;
    	bookList = new ArrayList<Node>();
        // look in the document for a node matching the <title> element data
        NodeList titles = document.getElementsByTagName("genre");
        for (int i = 0, j = titles.getLength(); i < j; i++) {
            Node t = titles.item(i);
            t.normalize();

            NodeList tlist = t.getChildNodes();
            for (int k = 0, l = tlist.getLength(); k < l; k++) {
                if (tlist.item(k).getNodeType() == Document.TEXT_NODE) {
                    if (tlist.item(k).getNodeValue().equals(genre)) {
                        // we have a match
                    	bookList.add(t.getParentNode());
                    }
                }
            }
        }
        return (bookList.toArray(new Node[0]));
    }

    private SOAPBodyElement findTitleElement(SOAPMessage message) {
        SOAPBodyElement element = null;
        try {
            SOAPPart part = message.getSOAPPart();
            SOAPEnvelope envelope = part.getEnvelope();
            SOAPBody body = envelope.getBody();
            Iterator it1 = body.getChildElements();
            SOAPBodyElement search = (SOAPBodyElement) it1.next();
            Iterator it2 = search.getChildElements();
            element = (SOAPBodyElement) it2.next();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return element;
    }

    private SOAPMessage getSOAPMessage(InputStream input) throws SOAPException,
            IOException {
        MimeHeaders headers = new MimeHeaders();
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage(headers, input);
        return message;
    }

    private BookSearchResult searchBookByTitle(InputStream input) {
        BookSearchResult book = null;
        try {
            SOAPMessage message = getSOAPMessage(input);
            SOAPBodyElement element = findTitleElement(message);
            String title = element.getValue();
            Node node = findBookByTitle(title);
            if (node != null) {
                book = buildBook(node);
            } else {
                // XXX Bonus: Send back a SOAP fault message
                System.out.println("Book not found: " + title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

    public BookSearchResult searchBookByGenre(String genre)
    {
        BookSearchResult book = null;
        try {
            Node node = findBookByGenre(genre);
            if (node != null) 
            {
                book = buildBook(node);
            } else 
            {
                // XXX Bonus: Send back a SOAP fault message
                System.out.println("Books of genre \"" + genre + "\" not found" );
            }
        } catch (Exception ex) 
        {
            ex.printStackTrace();
        }
        return book;
    }
    
    public BookSearchResult[] findAllBooksByGenre(String genre) 
    {
    	Node[] result = null;
    	ArrayList<BookSearchResult> searchResult = null;
    	searchResult = new ArrayList<BookSearchResult>();
    	result = getAllBooksByGenre(genre);
    	for (Node node : result) {
    		searchResult.add(buildBook(node));
    	}
    	return searchResult.toArray(new BookSearchResult[0]);
    }
    
    public BookSearchResult searchBookByTitle(String title) {
        BookSearchResult book = null;
        try {
            Node node = findBookByTitle(title);
            if (node != null) 
            {
                book = buildBook(node);
            } else 
            {
                // XXX Bonus: Send back a SOAP fault message
                System.out.println("Book not found: " + title);
            }
        } catch (Exception ex) 
        {
            ex.printStackTrace();
        }
        return book;
    }

    public BookSearchResult searchBookByTitleSOAPMessage(String xml) {
        BookSearchResult book = null;
        try {
            byte[] bytes = xml.getBytes();
            InputStream input = new ByteArrayInputStream(bytes);
            book = searchBookByTitle(input);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return book;
    }

}
