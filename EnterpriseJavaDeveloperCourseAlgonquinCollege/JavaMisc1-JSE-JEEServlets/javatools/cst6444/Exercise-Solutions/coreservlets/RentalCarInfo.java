package coreservlets;

/** Simple class describing a car company and associated
 *  frequent renter number, used from the TravelData class
 *  (where an array of RentalCarInfo is associated with
 *  each customer).
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class RentalCarInfo {
  private String rentalCarCompany, rentalCarNumber;

  public RentalCarInfo(String rentalCarCompany,
                       String rentalCarNumber) {
    this.rentalCarCompany = rentalCarCompany;
    this.rentalCarNumber = rentalCarNumber;
  }

  public String getRentalCarCompany() {
    return(rentalCarCompany);
  }

  public String getRentalCarNumber() {
    return(rentalCarNumber);
  }
}
