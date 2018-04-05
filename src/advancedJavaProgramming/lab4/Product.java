package advancedJavaProgramming.lab4;

/**
 * @version 1.00 1996-09-07
 * @author Cay Horstmann
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The interface for remote product objects.
 */
public interface Product extends Remote {
  /**
   * Gets the description of this product.
   * 
   * @return the product description
   */
  String getDescription() throws RemoteException;
}
