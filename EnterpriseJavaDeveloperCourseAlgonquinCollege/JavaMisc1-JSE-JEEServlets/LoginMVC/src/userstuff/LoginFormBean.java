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

/**
 * @param string
 */
public void setPassword(String string) {
	password = string;
}

/**
 * @param string
 */
public void setUser_id(String string) {
	user_id = string;
}

}
