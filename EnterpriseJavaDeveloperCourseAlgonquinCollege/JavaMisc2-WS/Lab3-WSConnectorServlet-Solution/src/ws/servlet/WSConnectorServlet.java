package ws.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ws.business.WSServiceInterface;

/**
 * @author jlarstone
 * 
 * Simple generic connector approach for providing an XML input document to a
 * service. The service class is located using an initialization property
 * defined in web.xml and then instantiated so that it is part of the servlets
 * runtime context.
 * 
 * The input XML document is passed to the service object via the
 * WSServiceInterface for processing and a String representing the output XML
 * document is expected on return.
 * 
 */
public class WSConnectorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // generic test message
    private final static String DEFAULTRESPONSE = "WSConnectorServlet is responding.";

    // the service instance
    private WSServiceInterface theService = null;

    /*
     * Attempt to instantiate the service instance.
     */
    public void init() throws ServletException {
        // get the class name of the service from the
        // init parameters (see web.xml).

        String boClass = getServletConfig().getInitParameter("BusinessObject");
        if (boClass != null) {
            try {
                Class c = Class.forName(boClass);
                // create instance
                Object obj = c.newInstance();

                // ensure we have the correct interface type
                if (obj instanceof WSServiceInterface)
                    theService = (WSServiceInterface) obj;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /*
     * GET is implemented only to verify servlet operation.
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.getWriter().println(DEFAULTRESPONSE);
    }

    /*
     * XML documents arrive here.
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // ensure we get the content we expect
        if (!request.getContentType().equals("text/xml")) {
            response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Not an XML document.");
            return;
        }

        // TODO 01. Read the following comment about String vs. InputStream.

        // Note: the approach of passing an XML string to the parser
        // is discouraged because the XML string might contain a byte-
        // order mark (BOM) that confuses the SAX parser and results
        // in a SAXParseException. The solution is to pass an
        // InputStream instead, allowing the parser to process
        // the byte stream.

        // The approach using a String is discouraged.
        // BufferedReader br = new BufferedReader(request.getReader());
        // StringBuffer xml = new StringBuffer();
        // String s = null;
        // while ((s = br.readLine()) != null)
        // xml.append(s);

        // invoke the service to process the xml
        if (theService != null) {
            // Line below is not recommended - see above comment
            // String result = theService.process(xml);

            // TODO 02. Get the InputStream from the request.
            InputStream in = request.getInputStream();
            
            String salesURL = getSalesURL(request);
            
            // TODO 03. Pass the sales URL and InputStream to the service.            
            String result = theService.process(salesURL, in);

            if (result != null) {
                // send the response document
                response.setContentType("text/xml");
                response.getWriter().println(result);
            } else
                response.sendError(HttpServletResponse.SC_NO_CONTENT,
                        "Request Failed.");
        } else
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Service Unavailable.");
    }

    private String getSalesURL(HttpServletRequest request) {
        String context = request.getContextPath();
        if (!context.startsWith("/")) {
            context = "/" + context;
        }
        String salesURL = "http://localhost:8080" + context + "/sales.xml";
        return salesURL;
    }
}
