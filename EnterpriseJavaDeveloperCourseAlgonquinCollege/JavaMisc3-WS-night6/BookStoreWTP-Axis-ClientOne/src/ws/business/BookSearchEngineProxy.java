package ws.business;

public class BookSearchEngineProxy implements ws.business.BookSearchEngine {
  private String _endpoint = null;
  private ws.business.BookSearchEngine bookSearchEngine = null;
  
  public BookSearchEngineProxy() {
    _initBookSearchEngineProxy();
  }
  
  public BookSearchEngineProxy(String endpoint) {
    _endpoint = endpoint;
    _initBookSearchEngineProxy();
  }
  
  private void _initBookSearchEngineProxy() {
    try {
      bookSearchEngine = (new ws.business.BookSearchEngineServiceLocator()).getBookSearchEngine();
      if (bookSearchEngine != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bookSearchEngine)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bookSearchEngine)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bookSearchEngine != null)
      ((javax.xml.rpc.Stub)bookSearchEngine)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ws.business.BookSearchEngine getBookSearchEngine() {
    if (bookSearchEngine == null)
      _initBookSearchEngineProxy();
    return bookSearchEngine;
  }
  
  public ws.business.BookSearchResult searchBookByTitle(java.lang.String xml) throws java.rmi.RemoteException{
    if (bookSearchEngine == null)
      _initBookSearchEngineProxy();
    return bookSearchEngine.searchBookByTitle(xml);
  }
  
  public ws.business.BookSearchResult searchBookByGenre(java.lang.String genre) throws java.rmi.RemoteException{
    if (bookSearchEngine == null)
      _initBookSearchEngineProxy();
    return bookSearchEngine.searchBookByGenre(genre);
  }
  
  public ws.business.BookSearchResult[] findAllBooksByGenre(java.lang.String genre) throws java.rmi.RemoteException{
    if (bookSearchEngine == null)
      _initBookSearchEngineProxy();
    return bookSearchEngine.findAllBooksByGenre(genre);
  }
  
  
}