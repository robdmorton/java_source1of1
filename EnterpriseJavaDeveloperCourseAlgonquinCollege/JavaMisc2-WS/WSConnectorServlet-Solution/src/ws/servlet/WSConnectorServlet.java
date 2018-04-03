package ws.servlet;

import ws.business.*;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        // TODO 07. Rename init param class in web.xml to PurchaseSummaryService
        
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

        // get a BuffereReader for the input document
        BufferedReader br = new BufferedReader(request.getReader());

        // collect the input lines in a StringBuffer
        StringBuffer xml = new StringBuffer();
        String s = null;
        while ((s = br.readLine()) != null)
            xml.append(s);

        // invoke the service to process the xml
        if (theService != null) {
            s = theService.process(xml);
            if (s != null) {
                // send the response document
                response.setContentType("text/xml");
                response.getWriter().println(s);
            } else
                response.sendError(HttpServletResponse.SC_NO_CONTENT,
                        "Request Failed.");
        } else
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Service Unavailable.");
    }
}
