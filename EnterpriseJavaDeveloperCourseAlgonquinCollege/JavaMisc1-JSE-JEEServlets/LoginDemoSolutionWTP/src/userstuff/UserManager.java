/*
 * Created on Aug 3, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package userstuff;

import java.util.Iterator;
import java.util.List;

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
	 * @return
	 */
	public static User authenticate(LoginFormBean lfb) {
		User matchingUser = null;
		
        UserDB userDB = UserDB.getInstance();
        List allUsers = userDB.getUsers();
        for (Iterator iter = allUsers.iterator(); iter.hasNext();) {
            User aUser = (User) iter.next();
            if (lfb.getPassword().equals(aUser.getPassword())){
                matchingUser = aUser;
            }
        }
		return matchingUser;
	}
}
