package DefaultNamespace;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

public class Lab5Client {

	public static void main(String[] args) throws ServiceException, RemoteException 
	{
		// TODO Auto-generated method stub

		Lab5ServiceLocator lab5L = new Lab5ServiceLocator();
		Lab5 l5 = lab5L.getLab5();
//		l5.setStuff(l5.getStuff());
		System.out.println(l5.getStuff("'s your uncle"));
	}

}
