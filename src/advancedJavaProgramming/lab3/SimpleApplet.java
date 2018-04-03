package advancedJavaProgramming.lab3;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
/** Using swing classes */

public class SimpleApplet extends JApplet implements ActionListener {
	JLabel mystatus;
	public void init(){
		/** basic buttons with label */
		JButton okbutton = new JButton("OK");
		JButton cancelbutton = new JButton("Cancel");
		/** A panel to put my buttons on */
		JPanel buttonpanel = new JPanel();
		/** Use a label as a status window */
		mystatus = new JLabel("Status:");
		
		/** Register as a class that wants to get notified of events. */
		okbutton.addActionListener(this); 
		cancelbutton.addActionListener(this);
		
		/** Assign a command string , so we can identify which button is clicked */
		okbutton.setActionCommand("okcommand");
		cancelbutton.setActionCommand("cancelcommand");
		
		/** Place buttons on a panel*/
		buttonpanel.add(okbutton);
		buttonpanel.add(cancelbutton);
		
		/** Put the button panel at the top of the applet */
		getContentPane().add(mystatus, BorderLayout.NORTH);
		
		/** Put the button panel at the bottom of the applet */
		getContentPane().add(buttonpanel, BorderLayout.SOUTH);
	}
	public void actionPerformed( ActionEvent e ){
		String commandstring = e.getActionCommand();
		if( commandstring.equals("okcommand")){
			mystatus.setText("Status: Got ok Click");
			Toolkit.getDefaultToolkit().beep();
		}
		else if( commandstring.equals("cancelcommand")) {
			mystatus.setText("Status: Got cancel Click");
			Toolkit.getDefaultToolkit().beep();
		}
				
	}
}