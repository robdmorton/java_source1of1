package DefaultNamespace;

public class Lab5Proxy implements DefaultNamespace.Lab5 {
  private String _endpoint = null;
  private DefaultNamespace.Lab5 lab5 = null;
  
  public Lab5Proxy() {
    _initLab5Proxy();
  }
  
  public Lab5Proxy(String endpoint) {
    _endpoint = endpoint;
    _initLab5Proxy();
  }
  
  private void _initLab5Proxy() {
    try {
      lab5 = (new DefaultNamespace.Lab5ServiceLocator()).getLab5();
      if (lab5 != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)lab5)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)lab5)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (lab5 != null)
      ((javax.xml.rpc.Stub)lab5)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public DefaultNamespace.Lab5 getLab5() {
    if (lab5 == null)
      _initLab5Proxy();
    return lab5;
  }
  
  public void setStuff(java.lang.String aInStuff) throws java.rmi.RemoteException{
    if (lab5 == null)
      _initLab5Proxy();
    lab5.setStuff(aInStuff);
  }
  
  public java.lang.String getStuff(java.lang.String aInString) throws java.rmi.RemoteException{
    if (lab5 == null)
      _initLab5Proxy();
    return lab5.getStuff(aInString);
  }
  
  
}