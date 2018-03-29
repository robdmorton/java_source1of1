package ws.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ws.business.BookSearchEngine;
import ws.business.BookSearchResult;

public class BookStoreServlet extends HttpServlet {

    static final private String HTML_CONTENT_TYPE = "text/html";

    private static final long serialVersionUID = 1L;

    static final private String XML_CONTENT_TYPE = "text/xml";

    private BookSearchEngine bookSearch = null;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(HTML_CONTENT_TYPE);

        // Uses the String.format() method - the parameter is %1$s
        // %[argument_index$][flags][width][.precision]conversion       
        String spec = "<a href=\"%1$s/bookstore.jsp\">bookstore.jsp</a>";
        String link = String.format(spec, request.getContextPath());
        
        // Output a simple HTML document
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>BookSearchServlet</title></head>");
        out.println("<body>");
        out.println("<h1>BookStore</h1>");
        out.println("<p>Use " + link + " to submit a search request:</p>");
        out.println("</body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("xml") != null) {
            handleFormSubmit(request, response);
        } else {
            handleClientRequest(request, response);
        }
    }

    @SuppressWarnings("unchecked")
    private void handleClientRequest(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            System.out.println("Handling SOAP message from client...");
            InputStream input = request.getInputStream();
            BookSearchResult book = bookSearch.searchBookByTitle(input);
            response.setContentType(XML_CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            sendBookInformation(out, book);
        } catch (Exception e) {
            System.err.println("Couldn't handle request");
            e.printStackTrace();
        }
    }

    private void handleFormSubmit(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String xml = request.getParameter("xml");
        BookSearchResult book = bookSearch.searchBookByTitle(xml);
        response.setContentType(XML_CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        sendBookInformation(out, book);
    }

    private void sendBookInformation(PrintWriter out, BookSearchResult bookEntry)
            throws IOException {
        if (bookEntry != null) {
            out.println("<?xml version=\"1.0\" encoding=\"us-ascii\"?>");
            out.println("<result>");
            out.println("  <book>");
            out.println("    <author>" + bookEntry.getAuthor() + "</author>");
            out.println("    <title>" + bookEntry.getTitle() + "</title>");
            out.println("    <genre>" + bookEntry.getGenre() + "</genre>");
            out.println("    <quantity>" + bookEntry.getQuantity()
                    + "</quantity>");
            out.println("  </book>");
            out.println("</result>");
        }
    }

    public void init() {
        try {
            ServletContext context = this.getServletContext();
            URL bookstoreXML = context.getResource("/bookstore.xml");
            bookSearch = new BookSearchEngine(bookstoreXML);
        } catch (Exception e) {
            System.err.println("Couldn't initialize!");
            e.printStackTrace();
        }
    }
}