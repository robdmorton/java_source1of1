package introductionToJava.lab1;
import java.awt.BorderLayout;

import javax.swing.*;
/** Using swing classes */

public class simpleapplet extends JApplet {

	public void init(){
		JButton okbutton = new JButton("ok");
		getContentPane().add(okbutton, BorderLayout.SOUTH);
	}
}
