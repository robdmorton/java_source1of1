package coreservlets;

import java.util.*;
import java.text.*;

/** Describes a travel services customer. Implemented
 *  as a bean with some methods that return data in HTML
 *  format, suitable for access from JSP.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class TravelCustomer {
  private String emailAddress, password, firstName, lastName;
  private String creditCardName, creditCardNumber;
  private String phoneNumber, homeAddress;
  private String startDate, endDate;
  private String origin, destination;
  private FrequentFlyerInfo[] frequentFlyerData;
  private RentalCarInfo[] rentalCarData;
  private HotelInfo[] hotelData;

  public TravelCustomer(String emailAddress,
                        String password,
                        String firstName,
                        String lastName,
                        String creditCardName,
                        String creditCardNumber,
                        String phoneNumber,
                        String homeAddress,
                        FrequentFlyerInfo[] frequentFlyerData,
                        RentalCarInfo[] rentalCarData,
                        HotelInfo[] hotelData) {
    setEmailAddress(emailAddress);
    setPassword(password);
    setFirstName(firstName);
    setLastName(lastName);
    setCreditCardName(creditCardName);
    setCreditCardNumber(creditCardNumber);
    setPhoneNumber(phoneNumber);
    setHomeAddress(homeAddress);
    setStartDate(startDate);
    setEndDate(endDate);
    setFrequentFlyerData(frequentFlyerData);
    setRentalCarData(rentalCarData);
    setHotelData(hotelData);
  }
  
  public String getEmailAddress() {
    return(emailAddress);
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPassword() {
    return(password);
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getFirstName() {
    return(firstName);
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return(lastName);
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFullName() {
    return(getFirstName() + " " + getLastName());
  }

  public String getCreditCardName() {
    return(creditCardName);
  }

  public void setCreditCardName(String creditCardName) {
    this.creditCardName = creditCardName;
  }

  public String getCreditCardNumber() {
    return(creditCardNumber);
  }

  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  public String getCreditCard() {
    String cardName = getCreditCardName();
    String cardNum = getCreditCardNumber();
    cardNum = cardNum.substring(cardNum.length() - 4);
    return(cardName + " (XXXX-XXXX-XXXX-" + cardNum + ")");
  }
  
  public String getPhoneNumber() {
    return(phoneNumber);
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getHomeAddress() {
    return(homeAddress);
  }

  public void setHomeAddress(String homeAddress) {
    this.homeAddress = homeAddress;
  }

  public String getStartDate() {
    return(startDate);
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return(endDate);
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getOrigin() {
    return(origin);
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getDestination() {
    return(destination);
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }
  
  public FrequentFlyerInfo[] getFrequentFlyerData() {
    return(frequentFlyerData);
  }

  public void setFrequentFlyerData(FrequentFlyerInfo[]
                                   frequentFlyerData) {
    this.frequentFlyerData = frequentFlyerData;
  }

  public String getFrequentFlyerTable() {
    FrequentFlyerInfo[] frequentFlyerData =
      getFrequentFlyerData();
    if (frequentFlyerData.length == 0) {
      return("<I>No frequent flyer data recorded.</I>");
    } else {
      String table =
        "<TABLE>\n" +
        "  <TR><TH>Airline<TH>Frequent Flyer Number\n";
      for(int i=0; i<frequentFlyerData.length; i++) {
        FrequentFlyerInfo info = frequentFlyerData[i];
        table = table +
                "<TR ALIGN=\"CENTER\">" +
                "<TD>" + info.getAirlineName() +
                "<TD>" + info.getFrequentFlyerNumber() + "\n";
      }
      table = table + "</TABLE>\n";
      return(table);
    }
  }

  public RentalCarInfo[] getRentalCarData() {
    return(rentalCarData);
  }

  public void setRentalCarData(RentalCarInfo[] rentalCarData) {
    this.rentalCarData = rentalCarData;
  }

  public HotelInfo[] getHotelData() {
    return(hotelData);
  }

  public void setHotelData(HotelInfo[] hotelData) {
    this.hotelData = hotelData;
  }

  // This would be replaced by a database lookup
  // in a real application.
  
  public String getFlights() {
    String flightOrigin =
      replaceIfMissing(getOrigin(), "Nowhere");
    String flightDestination =
      replaceIfMissing(getDestination(), "Nowhere");
    Date today = new Date();
    DateFormat formatter =
      DateFormat.getDateInstance(DateFormat.MEDIUM);
    String dateString = formatter.format(today);
    String flightStartDate =
      replaceIfMissing(getStartDate(), dateString);
    String flightEndDate =
      replaceIfMissing(getEndDate(), dateString);
    String [][] flights =
      { { "Java Airways", "1522", "455.95", "Java, Indonesia",
          "Sun Microsystems", "9:00", "3:15" },
        { "Servlet Express", "2622", "505.95", "New Atlanta",
          "New Atlanta", "9:30", "4:15" },
        { "Geek Airlines", "3.14159", "675.00", "JHU",
          "MIT", "10:02:37", "2:22:19" } };
    String flightString = "";
    for(int i=0; i<flights.length; i++) {
      String[] flightInfo = flights[i];
      flightString =
        flightString + getFlightDescription(flightInfo[0],
                                            flightInfo[1],
                                            flightInfo[2],
                                            flightInfo[3],
                                            flightInfo[4],
                                            flightInfo[5],
                                            flightInfo[6],
                                            flightOrigin,
                                            flightDestination,
                                            flightStartDate,
                                            flightEndDate);
    }
    return(flightString);
  }

  private String getFlightDescription(String airline,
                                      String flightNum,
                                      String price,
                                      String stop1,
                                      String stop2,
                                      String time1,
                                      String time2,
                                      String flightOrigin,
                                      String flightDestination,
                                      String flightStartDate,
                                      String flightEndDate) {
    String flight =
      "<P><BR>\n" +
      "<TABLE WIDTH=\"100%\"><TR><TH CLASS=\"COLORED\">\n" +
      "<B>" + airline + " Flight " + flightNum +
      " ($" + price + ")</B></TABLE><BR>\n" +
      "<B>Outgoing:</B> Leaves " + flightOrigin +
      " at " + time1 + " AM on " + flightStartDate +
      ", arriving in " + flightDestination +
      " at " + time2 + " PM (1 stop -- " + stop1 + ").\n" +
      "<BR>\n" +
      "<B>Return:</B> Leaves " + flightDestination +
      " at " + time1 + " AM on " + flightEndDate +
      ", arriving in " + flightOrigin +
      " at " + time2 + " PM (1 stop -- " + stop2 + ").\n";
    return(flight);
  }
  
  private String replaceIfMissing(String value,
                                  String defaultValue) {
    if ((value != null) && (value.length() > 0)) {
      return(value);
    } else {
      return(defaultValue);
    }
  }
  
  public static TravelCustomer findCustomer
                                 (String emailAddress,
                                  TravelCustomer[] customers) {
    if (emailAddress == null) {
      return(null);
    }
    for(int i=0; i<customers.length; i++) {
      String custEmail = customers[i].getEmailAddress();
      if (emailAddress.equalsIgnoreCase(custEmail)) {
        return(customers[i]);
      }
    }
    return(null);
  }    
}
