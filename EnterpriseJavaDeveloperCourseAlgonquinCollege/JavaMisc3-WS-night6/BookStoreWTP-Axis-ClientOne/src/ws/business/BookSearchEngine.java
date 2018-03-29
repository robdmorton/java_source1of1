/**
 * BookSearchEngine.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.business;

public interface BookSearchEngine extends java.rmi.Remote {
    public ws.business.BookSearchResult searchBookByTitle(java.lang.String xml) throws java.rmi.RemoteException;
    public ws.business.BookSearchResult searchBookByGenre(java.lang.String genre) throws java.rmi.RemoteException;
    public ws.business.BookSearchResult[] findAllBooksByGenre(java.lang.String genre) throws java.rmi.RemoteException;
}
