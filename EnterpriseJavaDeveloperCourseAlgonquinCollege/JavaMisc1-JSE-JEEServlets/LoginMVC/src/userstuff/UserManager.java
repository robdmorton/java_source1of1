/*
 * Created on Aug 3, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package userstuff;

/**
 * @author wce00035
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UserManager {

	/**
	 * This method receives a UserFormBean
	 * and checks for the secret password.	 
	 * @param ufb
	 * @return null if user is not found else returns a User
	 */
	public static User login(LoginFormBean ufb) {
		User user = null;
		if (ufb.getPassword().equals("secret")) {
			user = new User();
			user.setUserID("jdoe");
			user.setPassword("secret");
			user.setContactName("Jane Doe");
			user.setCity("Ottawa");			
			user.setCountry("Canada");			
		}			
		return user;
	}
}
