package ws.business;

import java.io.InputStream;

/**
 * @author jlarstone
 * 
 * Interface for services to accept and return XML.
 */
public interface WSServiceInterface {

    /** @deprecated * */
    public String process(StringBuffer xml);

    /**
     * The main web service method definition.
     * 
     * @param url
     *            A URL pointing to additional XML data to be used during the
     *            processing routine.
     * @param inputStream
     *            The InputStream instance to be processed.
     * @return A String.
     */
    public String process(String url, InputStream inputStream);
}
