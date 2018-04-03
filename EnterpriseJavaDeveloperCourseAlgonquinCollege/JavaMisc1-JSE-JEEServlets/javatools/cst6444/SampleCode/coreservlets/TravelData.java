package coreservlets;

/** This class simply sets up some static data to
 *  describe some supposed preexisting customers.
 *  Use a database call in a real application. See
 *  CSAJSP Chapter 18 for many examples of the use
 *  of JDBC from servlets.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class TravelData {
  private static FrequentFlyerInfo[] janeFrequentFlyerData =
    { new FrequentFlyerInfo("Java Airways", "123-4567-J"),
      new FrequentFlyerInfo("Delta", "234-6578-D") };
  private static RentalCarInfo[] janeRentalCarData =
    { new RentalCarInfo("Alamo", "345-AA"),
      new RentalCarInfo("Hertz", "456-QQ-H"),
      new RentalCarInfo("Avis", "V84-N8699") };
  private static HotelInfo[] janeHotelData =
   { new HotelInfo("Marriot", "MAR-666B"),
     new HotelInfo("Holiday Inn", "HI-228-555") };
  private static FrequentFlyerInfo[] joeFrequentFlyerData =
    { new FrequentFlyerInfo("Java Airways", "321-9299-J"),
      new FrequentFlyerInfo("United", "442-2212-U"),
      new FrequentFlyerInfo("Southwest", "1A345") };
  private static RentalCarInfo[] joeRentalCarData =
    { new RentalCarInfo("National", "NAT00067822") };
  private static HotelInfo[] joeHotelData =
   { new HotelInfo("Red Roof Inn", "RRI-PREF-236B"),
     new HotelInfo("Ritz Carlton", "AA0012") };
  private static TravelCustomer[] travelData =
    { new TravelCustomer("jane@somehost.com",
                         "tarzan52",
                         "Jane",
                         "Programmer",
                         "Visa",
                         "1111-2222-3333-6755",
                         "(123) 555-1212",
                         "6 Cherry Tree Lane\n" +
                           "Sometown, CA 22118",
                         janeFrequentFlyerData,
                         janeRentalCarData,
                         janeHotelData),
      new TravelCustomer("joe@somehost.com",
                         "qWeRtY",
                         "Joe",
                         "Hacker",
                         "JavaSmartCard",
                         "000-1111-2222-3120",
                         "(999) 555-1212",
                         "55 25th St., Apt 2J\n" +
                           "New York, NY 12345",
                         joeFrequentFlyerData,
                         joeRentalCarData,
                         joeHotelData)
    };

  public static TravelCustomer[] getTravelData() {
    return(travelData);
  }
}
