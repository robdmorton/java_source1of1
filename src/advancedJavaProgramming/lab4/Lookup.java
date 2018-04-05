package advancedJavaProgramming.lab4;

import java.util.*;
import javax.naming.*;
import javax.naming.directory.*;

public class Lookup {
  // vars
  static final String DNS_SERVER = "192.168.0.1";
  static final String MX_TYPE = "MX";
  static final String A_TYPE = "A";
  static final String NS_TYPE = "NS";

  /**
   * <br />
   * Simple EXAMPLE of how getRecord can be implemented. Create return such as ArrayList for real
   * use.
   *
   **/
  public static void getRecord(String domainName, String recordType) {
    try {
      Hashtable env = new Hashtable();
      env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
      // env.put("java.naming.provider.url",
      // "dns://" + DNS_SERVER + "/");
      DirContext ictx = new InitialDirContext(env);
      Attributes a = ictx.getAttributes(domainName, new String[] {recordType});
      NamingEnumeration all = a.getAll();
      while (all.hasMore()) {
        Attribute attr = (Attribute) all.next();
        System.out.println("Attribute: " + attr.getID());
        NamingEnumeration values = attr.getAll();
        while (values.hasMore()) {
          // obviously you RETURN here
          // example print
          System.out.println("Value: " + values.next());
        }
      }
    } catch (Exception e) {
      System.out.println("Lookup.getRecord() ERROR " + e.getMessage());
    }

  }

  /**
   * <br />
   * Simple main() for test purposes. Args 1 = domain | Args 2 = record type
   *
   **/
  public static void main(String[] args) {
    System.out.println("calling getMX for " + args[0] + " and record type " + args[1]);
    Lookup.getRecord(args[0], args[1]);
  }
}
