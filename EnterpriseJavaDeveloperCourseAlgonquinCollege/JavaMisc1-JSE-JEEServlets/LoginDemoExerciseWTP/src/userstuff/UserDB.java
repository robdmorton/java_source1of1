package userstuff;

import java.util.List;
import java.util.ArrayList;

public class UserDB {
   private static UserDB uniqueInstance = null; // singleton    

   public static UserDB getInstance(){
       if (uniqueInstance == null)
           uniqueInstance = new UserDB();
       return uniqueInstance;
   }
   
  public  List getUsers(){
      return uniqueInstance.users;
  }
  
  //=====================  private details ==========
  
  private List users = new ArrayList();

  private UserDB() { // singleton 
      populate();
  }
  
  private void populate(){
      User tempUser;
      
      tempUser = new User();
      tempUser.setUserID("1234567");
      tempUser.setPassword("secret");
      tempUser.setContactName("Jane Doe");
      tempUser.setCity("Ottawa");         
      tempUser.setCountry("Canada");
      users.add(tempUser);

      tempUser = new User();
      tempUser.setUserID("345678");
      tempUser.setPassword("mypassword");
      tempUser.setContactName("Frank Smith");
      tempUser.setCity("Montreal");         
      tempUser.setCountry("Canada");        
      users.add(tempUser);        
  }
    
  
}
