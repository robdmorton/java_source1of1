package coreservlets;

/** Simple class describing an airline and associated
 *  frequent flyer number, used from the TravelData class
 *  (where an array of FrequentFlyerInfo is associated with
 *  each customer).
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class FrequentFlyerInfo {
  private String airlineName, frequentFlyerNumber;

  public FrequentFlyerInfo(String airlineName,
                           String frequentFlyerNumber) {
    this.airlineName = airlineName;
    this.frequentFlyerNumber = frequentFlyerNumber;
  }

  public String getAirlineName() {
    return(airlineName);
  }

  public String getFrequentFlyerNumber() {
    return(frequentFlyerNumber);
  }
}
