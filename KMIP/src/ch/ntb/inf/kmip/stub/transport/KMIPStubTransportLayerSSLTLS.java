/**
 * KMIPStubTransportLayerSSLTLS.java
 * -----------------------------------------------------------------
 *     __ __ __  ___________ 
 *    / //_//  |/  /  _/ __ \	  .--.
 *   / ,<  / /|_/ // // /_/ /	 /.-. '----------.
 *  / /| |/ /  / // // ____/ 	 \'-' .--"--""-"-'
 * /_/ |_/_/  /_/___/_/      	  '--'
 * 
 * -----------------------------------------------------------------
 * Description for class
 * TODO
 *
 * @author     Stefanie Meile <stefaniemeile@gmail.com>
 * @author     Michael Guster <michael.guster@gmail.com>
 * @copyright  Copyright © 2013, Stefanie Meile, Michael Guster
 * @license    Simplified BSD License (see LICENSE.TXT)
 * @version    1.0, 2013/08/09
 * @since      Class available since Release 1.0
 *
 * 
 */
package ch.ntb.inf.kmip.stub.transport;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.concurrent.FutureTask;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

/**
 *
 */
public class KMIPStubTransportLayerSSLTLS implements KMIPStubTransportLayerInterface {
	
	private static final Logger logger = Logger.getLogger(KMIPStubTransportLayer.class);
	
	private int PORT = 5555;							// default values
	private String targetHostname = "172.20.23.160";	// default values

	private KMIPClientHandler clientHandler;

	private SSLSocketFactory factory;
    private SSLSocket socket = null;
	private String keyStoreFileName;
	private String keyStorePassword;
	private String alias = "rmortonclient";
	private ArrayList<Byte> kmip_al;

	public KMIPStubTransportLayerSSLTLS(){
		logger.info("KMIPStubTransportLayerSSLTLS initialized...");
	}
	
	/**
	 * Sends a KMIP-Request-Message as a TTLV-encoded hexadecimal string stored in an 
	 * <code>ArrayList{@literal <}Byte{@literal >}</code> to a defined target and returns 
	 * a corresponding KMIP-Response-Message.
	 * 
	 * @param al :     	the <code>ArrayList{@literal <}Byte{@literal >}</code> to be sent.
	 * @return			<code>ArrayList{@literal <}Byte{@literal >}</code>: the response message.
	 */
	public ArrayList<Byte> send(ArrayList<Byte> al){
		logger.info("KLMSClient Request Thread: " + Thread.currentThread());
		clientHandler = new KMIPClientHandler(targetHostname,PORT,al);

		try
		{
			kmip_al = al;

			// create key and trust managers
			keyStoreFileName = "/home/rmorton/dev/ds_datasecure/commands/internal/test/kmip_client/client2CertAndKey.keystore";
			keyStoreFileName = "/home/rmorton/dev/ds_datasecure/commands/internal/test/kmip_client/clientCertAndKey.keystore";
			keyStorePassword = "changeit";
			KeyManager[] keyManagers = createKeyManagers(keyStoreFileName, keyStorePassword, alias);
	        
	        TrustManager[] trustManagers = createTrustManagers();
	        		
	        // init context with managers data   
	        factory = initItAll(keyManagers, trustManagers);
	        socket = (SSLSocket)factory.createSocket(targetHostname,PORT);
//	        socket = (SSLSocket)factory.createSocket();
	        int connectTimeout = 100;
	        socket.setSoTimeout(connectTimeout);
//	        socket.connect(new InetSocketAddress(targetHostname,PORT), connectTimeout);
	        socket.startHandshake();
	        
			// Send to server
			logger.info("Write Data to Server...");  
			writeData(socket);
			logger.info("Data transmitted!");
			
			// Close output signalize EOF
//			socket.shutdownOutput();
			
			// Read from server
			ArrayList<Byte> responseFromServer = readData();
			
			// Close connection
			socket.close();
			
			return responseFromServer;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
       
	}

	/**
	 * Sets the target host name to the defined value
	 * 
	 * @param value :     	the target host name defined as <code>String</code> to be set. 
	 */
	public void setTargetHostname(String value) {
		int split = value.indexOf(":");
		this.targetHostname = value.substring(0,split);
		this.PORT = Integer.parseInt(value.substring(split+1, value.length()));
		logger.info("Connection to: "+targetHostname+":"+PORT);
	}

	/**
	 * Only for HTTPS support. HTTP: nothing to do here-> empty implementation.
	 * 
	 * @param property :     the key store location defined as <code>String</code> to be set. 
	 */
	public void setKeyStoreLocation(String property) {}

	/**
	 * Only for HTTPS support. HTTP: nothing to do here-> empty implementation.
	 * 
	 * @param property :     the key store password defined as <code>String</code> to be set.
	 */
	public void setKeyStorePW(String property) {}

	private SSLSocketFactory initItAll(KeyManager[] keyManagers, TrustManager[] trustManagers)
	        throws NoSuchAlgorithmException, KeyManagementException {
	        SSLContext context = SSLContext.getInstance("TLS");
	        context.init(keyManagers, trustManagers, null);
	        return context.getSocketFactory();
	    }
	 
    private KeyManager[] createKeyManagers(String keyStoreFileName, String keyStorePassword, String alias) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        java.io.InputStream inputStream = new java.io.FileInputStream(keyStoreFileName);
        //create keystore object, load it with keystorefile data
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, keyStorePassword == null ? null : keyStorePassword.toCharArray());
 
        KeyManager[] managers;
        if (alias != null) {
        	managers = new KeyManager[] {new AliasKeyManager(keyStore, alias, keyStorePassword)};
        } else {
            //create keymanager factory and load the keystore object in it 
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keyStorePassword == null ? null : keyStorePassword.toCharArray());
            managers = keyManagerFactory.getKeyManagers();
        }
        return managers;
    }
	    
	private TrustManager[] createTrustManagers() {
		return new TrustManager[] { 
        	    new X509TrustManager() {     
        	        public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
        	            return null;
        	        } 
        	        public void checkClientTrusted( 
        	            java.security.cert.X509Certificate[] certs, String authType) {
        	            } 
        	        public void checkServerTrusted( 
        	            java.security.cert.X509Certificate[] certs, String authType) {
        	        }
        	    } 
        	}; 
	}

	private void writeData(Socket clientSocket){
		try {
			// Get OutputStream from Socket
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			// Prepare data to send
			byte[] b = new byte[kmip_al.size()];
			for(int i=0; i<kmip_al.size();i++){
				b[i]=kmip_al.get(i);
			}
			// Send data
			outToServer.write(b);
			outToServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

	private ArrayList<Byte> readData(){
    	byte[] resultBuff = new byte[0];
        byte[] buff = new byte[1024];
        int k = -1;
        
        try {
    		InputStream is = socket.getInputStream();
    		int availableBytesInSocketFromServer = 1;
			while(availableBytesInSocketFromServer > 0 && (k = is.read(buff, 0, buff.length)) > -1) 
			{
			    byte[] tbuff = new byte[resultBuff.length + k]; // temp buffer size = bytes already read + bytes last read
			    System.arraycopy(resultBuff, 0, tbuff, 0, resultBuff.length); // copy previous bytes
			    System.arraycopy(buff, 0, tbuff, resultBuff.length, k);  // copy current lot
			    resultBuff = tbuff; // call the temp buffer as your result buff
			    availableBytesInSocketFromServer = is.available();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} // try
       
        logger.debug(resultBuff.length + " bytes read.");
        ArrayList<Byte> response = new ArrayList<Byte>();
        
        for(byte b:resultBuff){
        	response.add(b);
        }
        
        logger.info("");  
        return response;
	} 

	private class AliasKeyManager implements X509KeyManager {
    	 
        private KeyStore _ks;
        private String _alias;
        private String _password;
 
        public AliasKeyManager(KeyStore ks, String alias, String password) {
            _ks = ks;
            _alias = alias;
            _password = password;
        }
 
        public String chooseClientAlias(String[] str, Principal[] principal, Socket socket) {
            return _alias;
        }
 
        public String chooseServerAlias(String str, Principal[] principal, Socket socket) {
            return _alias;
        }
 
        public X509Certificate[] getCertificateChain(String alias) {
            try {
                java.security.cert.Certificate[] certificates = this._ks.getCertificateChain(alias);
                if(certificates == null){
                	throw new FileNotFoundException("no certificate found for alias:" + alias);
                }
                X509Certificate[] x509Certificates = new X509Certificate[certificates.length];
                System.arraycopy(certificates, 0, x509Certificates, 0, certificates.length);
                return x509Certificates;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
 
        public String[] getClientAliases(String str, Principal[] principal) {
            return new String[] { _alias };
        }
 
        public PrivateKey getPrivateKey(String alias) {
            try {
                return (PrivateKey) _ks.getKey(alias, _password == null ? null : _password.toCharArray());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
 
        public String[] getServerAliases(String str, Principal[] principal) {
            return new String[] { _alias };
        }
 
    }

}
