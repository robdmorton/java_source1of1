import java.io.*;
import java.security.*;
import javax.net.ssl.*;

public class PlainServer 
{
  public static void main(String [] args) {
  SSLServerSocket serverSock = null;
  SSLSocket socket = null;
  PrintWriter out = null;
  try {
    //load server private key
    KeyStore serverKeys = KeyStore.getInstance("JKS");
    serverKeys.load(new FileInputStream("plainserver.jks"),"password".toCharArray());
    KeyManagerFactory serverKeyManager = KeyManagerFactory.getInstance("SunX509");
    //System.out.println(KeyManagerFactory.getDefaultAlgorithm());
    //System.out.println(serverKeyManager.getProvider());
    serverKeyManager.init(serverKeys,"password".toCharArray());
    
    //load client public key
    KeyStore clientPub = KeyStore.getInstance("JKS");
    clientPub.load(new FileInputStream("clientpub.jks"),"password".toCharArray());
    TrustManagerFactory trustManager = TrustManagerFactory.getInstance("SunX509");
    trustManager.init(clientPub);
    
    //use keys to create SSLSoket
    SSLContext ssl = SSLContext.getInstance("TLS");
    ssl.init(serverKeyManager.getKeyManagers(), trustManager.getTrustManagers(), SecureRandom.getInstance("SHA1PRNG"));
    serverSock = (SSLServerSocket)ssl.getServerSocketFactory().createServerSocket(8889);
    
    serverSock.setNeedClientAuth(true);
    
    //block on accept 
    socket = (SSLSocket)serverSock.accept();
    
    //send data
    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
    out.println("data from PlainServer");
    out.flush();
  } catch (Exception e) {
    e.printStackTrace();
  } finally {
    if(out!=null) out.close();
      try {
        if(serverSock!=null) serverSock.close();
        if(socket!=null) socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
  }
  }
}