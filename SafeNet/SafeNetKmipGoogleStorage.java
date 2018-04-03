package SafeNet;
//package com.safenet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.api.client.http.InputStreamContent;
import com.ingrian.security.nae.KMIPAttributes;
import com.ingrian.security.nae.KMIPNameAttribute;
import com.ingrian.security.nae.KMIPSession;
import com.ingrian.security.nae.NAEClientCertificate;
import com.ingrian.security.nae.NAEKey;
import com.ingrian.security.nae.NAEParameterSpec;
import com.ingrian.security.nae.NAEPrivateKey;
import com.ingrian.security.nae.NAEPublicKey;
import com.ingrian.security.nae.NAESecretKey;
import com.ingrian.security.nae.KMIPAttributeNames.KMIPAttribute;



public class SafeNetKmipGoogleStorage{
	
	/** Instance of InputStreamContent that hold the encrypted data to upload on google.*/
	InputStreamContent mediaContent;
	
	/**
	 * Creates an instance of  SafeNetKmipGoogleStorage.
	 * 
	 */	
	public SafeNetKmipGoogleStorage()
	{
		mediaContent = null;
	}
	
	private static boolean isKey( Object serverManagedObject ) {
	    return ( serverManagedObject instanceof NAEPublicKey  ) || 
                            ( serverManagedObject instanceof NAEPrivateKey ) || 
                            ( serverManagedObject instanceof NAESecretKey  );
	}
	/**
	* <p>
	 * Returns and instance of InputStreamContent which holds the encrypted data.  
	 * </p>
	 *
	 * @param ENCkeyName
	 *				Name of the key in form String that to be used to locate on key server.	
	 */
	public InputStreamContent getStreamContent(File filename,String ENCkeyName) throws IOException
	{
		NAEKey key = null;
	   	SecretKey enckey;
	   	byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		
	   	byte[] filebytes = new byte[(int) filename.length()]; 
        FileInputStream fis = new FileInputStream(filename);
        fis.read(filebytes);
        
        
   	 	IvParameterSpec ivspec = new IvParameterSpec(iv);
        try 
        {
   	      KMIPSession session  = KMIPSession.getSession(new NAEClientCertificate(null,null));
   	      KMIPNameAttribute KeyName  = new KMIPNameAttribute(ENCkeyName);
   	      // KMIPAttribute set to hold unique NAE server identifiers for located keys
   	      Set<String> managedObjectIdentifiers;

   	      // This instance of KMIPAttributes will be used as the KMIP attributes and 
   	      // values to be searched for
   	      KMIPAttributes locateAttributes = new KMIPAttributes(); 
   	      locateAttributes.add( KMIPAttribute.Name, KeyName );

   	      managedObjectIdentifiers = session.locate( locateAttributes );
   	      
   	      if (managedObjectIdentifiers != null ) {
   	          System.out.println("\n\nFound " + managedObjectIdentifiers.size() + 
   	                  " managed objects matching key Locate criteria.");
   	          
   	         
   	          // for each object found, query all the non-custom attributes
   	          for (String uid : managedObjectIdentifiers ) {
   	              System.out.println("\n\nManaged Object UniqueIdentifier: \t" + uid);
   	              Object serverManagedObject = session.getManagedObject(uid);
   	              if ( serverManagedObject == null ) continue; // not a key
   	              if ( isKey( serverManagedObject ) )
   	              {
   	                  // NAEKey is the superclass of public/private and secret keys
   	                  
   	                  if ( serverManagedObject instanceof NAEPublicKey )
   	                      key = (NAEPublicKey) serverManagedObject;
   	                  else if ( serverManagedObject instanceof NAEPrivateKey )
   	                      key = (NAEPrivateKey) serverManagedObject;
   	                  else
   	                      key = (NAESecretKey) serverManagedObject;
   	                  
   	                  System.out.println("\tName: \t" + key.getName());
   	                  
   	               
   	                      	                  
   	              }
   	              
   	          }

   	      }
   	      
   	      enckey = new SecretKeySpec(key.export(), "AES");
   	      Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
          aes.init(Cipher.ENCRYPT_MODE, enckey,ivspec);
             
          System.out.println("\n Provider:: " +aes.getProvider().getName());
             
          filebytes = aes.doFinal(filebytes);
          session.closeSession();
          
          mediaContent = new InputStreamContent("application/octet-stream",
	        		 new DataInputStream(new ByteArrayInputStream(filebytes)));
   	    	
   	    }catch (Exception e) {
   	      System.out.println("The Cause is " + e.getMessage() + ".");
   	      e.printStackTrace();
   	    }


		return mediaContent;
	}
	/**
	* <p>
	 * Decrypt the Data using the Keyname and saves the file at user defined location.  
	 * </p>
	 *
	 * @param out
	 *				Byte Array Stream which holds the data downloaded from google.	
	 * @param objectName
	 *				Name of the file which was downloaded from Google
	 * @param destfilepath
	 *				Path where file is to be saved
	 * @param ENCkeyName
	 *				Name of the key in form String that to be used to locate on key server.
	 */
	public void downloadMediaContent(ByteArrayOutputStream out,String objectName,String destfilepath,String ENCkeyName) throws IOException
	{
	   	NAEKey key = null;
	   	SecretKey enckey;
	   	byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	   	byte[] bytes = out.toByteArray();
		
   	    IvParameterSpec ivspec = new IvParameterSpec(iv);
   	    try 
   	    {
   	    	KMIPSession session  = KMIPSession.getSession(new NAEClientCertificate(null,null));
   	    	KMIPNameAttribute KeyName  = new KMIPNameAttribute(ENCkeyName);
   	    	// 	KMIPAttribute set to hold unique NAE server identifiers for located keys
   	    	Set<String> managedObjectIdentifiers;

   	    	// This instance of KMIPAttributes will be used as the KMIP attributes and 
   	    	// values to be searched for
   	    	KMIPAttributes locateAttributes = new KMIPAttributes(); 
   	    	locateAttributes.add( KMIPAttribute.Name, KeyName );

   	    	managedObjectIdentifiers = session.locate( locateAttributes );
	      
   	    	if (managedObjectIdentifiers != null ) {
	          System.out.println("\n\nFound " + managedObjectIdentifiers.size() + 
	                  " managed objects matching key Locate criteria.");
	          
	         
	          // for each object found, query all the non-custom attributes
	          for (String uid : managedObjectIdentifiers ) {
	              System.out.println("\n\nManaged Object UniqueIdentifier: \t" + uid);
	              Object serverManagedObject = session.getManagedObject(uid);
	              if ( serverManagedObject == null ) continue; // not a key
	              if ( isKey( serverManagedObject ) )
	              {
	                  // NAEKey is the superclass of public/private and secret keys
	                  
	                  if ( serverManagedObject instanceof NAEPublicKey )
	                      key = (NAEPublicKey) serverManagedObject;
	                  else if ( serverManagedObject instanceof NAEPrivateKey )
	                      key = (NAEPrivateKey) serverManagedObject;
	                  else
	                      key = (NAESecretKey) serverManagedObject;
	                  
	                  System.out.println("\tName: \t" + key.getName());
	                  
	                      	                  
	                      	                  
	              }
	              
	          }

   	    	}
	      
	      enckey = new SecretKeySpec(key.export(), "AES");
	      Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
          aes.init(Cipher.DECRYPT_MODE, enckey,ivspec);
          
           
          bytes = aes.doFinal(bytes);
          session.closeSession();
	    	
	    }catch (Exception e) {
	      System.out.println("The Cause is " + e.getMessage() + ".");
	      e.printStackTrace();
	    }

   	 char buff = destfilepath.charAt(destfilepath.length() -1);
	 
	 
	 if(buff == '\\')
	 {
		 destfilepath = destfilepath.concat(objectName);
	 
	 }
	 else
	 {
		 destfilepath = destfilepath.concat("\\");
		 destfilepath = destfilepath.concat(objectName);
	 }
	
	 FileOutputStream fos = new FileOutputStream(destfilepath);
	 fos.write(bytes);
	 fos.close();
	
	}
	/**
	* <p>
	 * Create a Key on key server using KMIP Protocol.  
	 * </p>
	 * @param keyName
	 *				Name of the key to be created
	 * @param keyLength
	 *				Length of the key.
	 */
	public void createSymKey(String keyName, int keyLength)
	{
		
		try
		{

			// create KMIP Session - specify client X.509 certificate and keystore password
			KMIPSession kmipSession = KMIPSession.getSession(new NAEClientCertificate(null, null));
			
			/* create a secret key using KMIP JCE key generator */
			NAEParameterSpec spec = new NAEParameterSpec(keyName, keyLength,
					(KMIPAttributes)null, kmipSession);
			KeyGenerator kg = KeyGenerator.getInstance("AES", "IngrianProvider");
			kg.init(spec);
			SecretKey secretKey = kg.generateKey();
			System.out.println("Created key " + ((NAEKey)secretKey).getName());
		}
		catch (Exception e)
		{
			System.out.println("The Cause is " + e.getMessage() + ".");
			e.printStackTrace();
		}

	}
}
