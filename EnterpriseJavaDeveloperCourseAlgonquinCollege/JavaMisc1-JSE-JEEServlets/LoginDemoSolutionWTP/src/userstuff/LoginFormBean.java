/*
 * Created on Aug 3, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package userstuff;

import java.io.Serializable;

/**
 * @author wce00035
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LoginFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String user_id;
	private String password;

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return
	 */
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}


	// TODO 01 Add the missing setter for the User_id
	
	// TODO 02 After adding the missing setter, Go fix login.html	
	

}
