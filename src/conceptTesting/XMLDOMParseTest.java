package conceptTesting;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XMLDOMParseTest {

  public static void mainMethod() 
  {
  	 
	  try 
	  {
	 
			File fXmlFile = new File("staff.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
		 
			System.out.println("\nRoot element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList nList = doc.getElementsByTagName("staff");
		 
			System.out.println("----------------------------");
		 
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Node nNode = nList.item(temp);
		 
				System.out.println("Current Element: " + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 				
					System.out.println("Staff id : " + eElement.getAttribute("id"));
					System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
					System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
					System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
					System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
				}
			}
			
			fXmlFile = new File("CST6445Assignment1.xml");
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("\nRoot element :" + doc.getDocumentElement().getNodeName());
			 
			nList = doc.getElementsByTagName("id");
		 
			System.out.println("----------------------------");
		 
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Node nNode = nList.item(temp);
		 
				System.out.println("Current Element: " + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 				
					System.out.println("imsi: " + eElement.getElementsByTagName("imsi").item(0).getTextContent());
					System.out.println("other3: " + eElement.getElementsByTagName("other3").item(0).getTextContent());
				}
			}
			
			nList = doc.getElementsByTagName("subscriptionstartdate");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				Node nNode = nList.item(temp);
		 
				System.out.println("Current Element: " + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 				
					System.out.println("subscriptionstartdate: " + eElement.getTextContent());
				}
			}

			nList = doc.getElementsByTagName("location");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				Node nNode = nList.item(temp);
		 
				System.out.println("Current Element: " + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 				
					System.out.println("logical: " + eElement.getElementsByTagName("logical").item(0).getTextContent());
					
					System.out.println("streetaddress: " + eElement.getElementsByTagName("streetaddress").item(0).getTextContent());
					System.out.println("city: " + eElement.getElementsByTagName("city").item(0).getTextContent());
					if(eElement.getElementsByTagName("stateorprov").item(0) != null)
					{
						System.out.println("stateorprov: " + eElement.getElementsByTagName("stateorprov").item(0).getTextContent());
					}
					System.out.println("code: " + eElement.getElementsByTagName("basicCode").item(0).getTextContent());
					
				}
			}

	    
	  } 
	  catch (Exception e) 
	  {
	  	e.printStackTrace();
	  }
  
  }
  
}
