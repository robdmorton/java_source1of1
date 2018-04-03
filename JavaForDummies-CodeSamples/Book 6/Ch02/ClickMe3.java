import javax.swing.*;
import java.awt.event.*;

public class ClickMe3 extends JFrame
{
	public static void main(String [] args)
	{
		new ClickMe3();
	}

	private JButton button1, exitButton;

	public ClickMe3()
	{
	    this.setSize(275,100);
	    this.setTitle("I'm Listening");
	    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		ClickListener cl = new ClickListener();

	    JPanel panel1 = new JPanel();

	    addWindowListener(new WindowAdapter()
	    	{
				public void windowClosing(WindowEvent e)
				{
					exitButton.doClick();
				}
			} );

		button1 = new JButton("Click Me!");
		button1.addActionListener(cl);
	    panel1.add(button1);

	    exitButton = new JButton("Exit");
	    exitButton.addActionListener(cl);
	    panel1.add(exitButton);

	    this.add(panel1);

	    this.setVisible(true);
	}

	private class ClickListener implements ActionListener
	{
		private int clickCount = 0;

		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == button1)
			{
				clickCount++;
				if (clickCount == 1)
					button1.setText("I've been clicked!");
			    else
					button1.setText("I've been clicked "
						+ clickCount + " times!");
			}
			else if (e.getSource() == exitButton)
			{
				if (clickCount > 0)
					System.exit(0);
				else
				{
					JOptionPane.showMessageDialog(ClickMe3.this,
					    "You must click at least once!",
					    "Not so fast, buddy",
					    JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}