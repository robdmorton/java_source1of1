package CST5301IntroductionToJava.lab3;

import java.awt.BorderLayout;
/* Using swing classes */
import javax.swing.*;

public class SimpleApplet extends JApplet {

	public void init(){
		JButton okbutton = new JButton("ok");
		getContentPane().add(okbutton, BorderLayout.SOUTH);
	}
}
