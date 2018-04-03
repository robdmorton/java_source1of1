package coreservlets;

public class TravelCustomerTest {
  public static void main(String[] args) {
    FrequentFlyerInfo[] ffData =
      { new FrequentFlyerInfo("American", "123A"),
        new FrequentFlyerInfo("Delta", "234D") };
    RentalCarInfo[] rcData =
      { new RentalCarInfo("Alamo", "345A"),
        new RentalCarInfo("Hertz", "456H") };
    HotelInfo[] hData =
      { new HotelInfo("Marriot", "567M"),
        new HotelInfo("Holiday Inn", "678H") };
    TravelCustomer marty =
      new TravelCustomer("hall@coreservlets.com",
                         "martyspassword",
                         "Marty",
                         "Hall",
                         "Visa",
                         "123-456-7890",
                         "(443) 778-6221",
                         "Johns Hopkins Rd.\nLaurel, MD",
                         "2/2/00",
                         "2/4/00",
                         ffData,
                         rcData,
                         hData);
  }
}
