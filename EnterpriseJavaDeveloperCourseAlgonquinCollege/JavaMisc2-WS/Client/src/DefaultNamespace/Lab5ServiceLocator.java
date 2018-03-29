/**
 * Lab5ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package DefaultNamespace;

public class Lab5ServiceLocator extends org.apache.axis.client.Service implements DefaultNamespace.Lab5Service {

    public Lab5ServiceLocator() {
    }


    public Lab5ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Lab5ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Lab5
    private java.lang.String Lab5_address = "http://localhost:8080/Server/services/Lab5";

    public java.lang.String getLab5Address() {
        return Lab5_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Lab5WSDDServiceName = "Lab5";

    public java.lang.String getLab5WSDDServiceName() {
        return Lab5WSDDServiceName;
    }

    public void setLab5WSDDServiceName(java.lang.String name) {
        Lab5WSDDServiceName = name;
    }

    public DefaultNamespace.Lab5 getLab5() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Lab5_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLab5(endpoint);
    }

    public DefaultNamespace.Lab5 getLab5(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            DefaultNamespace.Lab5SoapBindingStub _stub = new DefaultNamespace.Lab5SoapBindingStub(portAddress, this);
            _stub.setPortName(getLab5WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLab5EndpointAddress(java.lang.String address) {
        Lab5_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (DefaultNamespace.Lab5.class.isAssignableFrom(serviceEndpointInterface)) {
                DefaultNamespace.Lab5SoapBindingStub _stub = new DefaultNamespace.Lab5SoapBindingStub(new java.net.URL(Lab5_address), this);
                _stub.setPortName(getLab5WSDDServiceName());
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
        if ("Lab5".equals(inputPortName)) {
            return getLab5();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://DefaultNamespace", "Lab5Service");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://DefaultNamespace", "Lab5"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Lab5".equals(portName)) {
            setLab5EndpointAddress(address);
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
