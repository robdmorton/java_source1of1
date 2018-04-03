/**
 * BookSearchEngineServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.business;

public class BookSearchEngineServiceLocator extends org.apache.axis.client.Service implements ws.business.BookSearchEngineService {

    public BookSearchEngineServiceLocator() {
    }


    public BookSearchEngineServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BookSearchEngineServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BookSearchEngine
    private java.lang.String BookSearchEngine_address = "http://localhost:8080/BookStoreWTP-Axis/services/BookSearchEngine";

    public java.lang.String getBookSearchEngineAddress() {
        return BookSearchEngine_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BookSearchEngineWSDDServiceName = "BookSearchEngine";

    public java.lang.String getBookSearchEngineWSDDServiceName() {
        return BookSearchEngineWSDDServiceName;
    }

    public void setBookSearchEngineWSDDServiceName(java.lang.String name) {
        BookSearchEngineWSDDServiceName = name;
    }

    public ws.business.BookSearchEngine getBookSearchEngine() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BookSearchEngine_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBookSearchEngine(endpoint);
    }

    public ws.business.BookSearchEngine getBookSearchEngine(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ws.business.BookSearchEngineSoapBindingStub _stub = new ws.business.BookSearchEngineSoapBindingStub(portAddress, this);
            _stub.setPortName(getBookSearchEngineWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBookSearchEngineEndpointAddress(java.lang.String address) {
        BookSearchEngine_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ws.business.BookSearchEngine.class.isAssignableFrom(serviceEndpointInterface)) {
                ws.business.BookSearchEngineSoapBindingStub _stub = new ws.business.BookSearchEngineSoapBindingStub(new java.net.URL(BookSearchEngine_address), this);
                _stub.setPortName(getBookSearchEngineWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BookSearchEngine".equals(inputPortName)) {
            return getBookSearchEngine();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://business.ws", "BookSearchEngineService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://business.ws", "BookSearchEngine"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BookSearchEngine".equals(portName)) {
            setBookSearchEngineEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
