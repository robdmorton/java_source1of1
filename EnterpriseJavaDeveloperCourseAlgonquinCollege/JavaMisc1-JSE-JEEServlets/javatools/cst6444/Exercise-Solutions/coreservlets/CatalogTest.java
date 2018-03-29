package coreservlets;

public class CatalogTest {
  public static void main(String[] args) {
    String[] ids = { "hall001", "hall002",
                     "lewis001", "alexander001",
                     "rowling001" };
    Item item;
    for(int i=0; i<ids.length; i++) {
      item = Catalog.getItem(ids[i]);
      if (item == null) {
        System.out.println("No item matches " + ids[i]);
      } else {
        System.out.println
          ("ID: " + item.getItemID() + "\n" +
           "Short: " + item.getShortDescription() + "\n" +
           "Long: " + item.getLongDescription() + "\n" +
           "Cost: " + item.getCost());
      }
    }
  }
}
