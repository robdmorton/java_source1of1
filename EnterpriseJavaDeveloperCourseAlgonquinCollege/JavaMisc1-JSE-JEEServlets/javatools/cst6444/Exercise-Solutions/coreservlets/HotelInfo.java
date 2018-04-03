package coreservlets;

/** Simple class describing a hotel name and associated
 *  frequent guest number, used from the TravelData class
 *  (where an array of HotelInfo is associated with
 *  each customer).
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class HotelInfo {
  private String hotelName, frequentGuestNumber;

  public HotelInfo(String hotelName,
                   String frequentGuestNumber) {
    this.hotelName = hotelName;
    this.frequentGuestNumber = frequentGuestNumber;
  }

  public String getHotelName() {
    return(hotelName);
  }

  public String getfrequentGuestNumber() {
    return(frequentGuestNumber);
  }
}
