package ws.business;


/**
 * @author jlarstone
 *
 * Interface for services to accept and return XML.
 */
public interface WSServiceInterface {
	public String process(StringBuffer xml);
}
