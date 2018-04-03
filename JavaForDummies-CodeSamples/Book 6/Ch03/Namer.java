import javax.swing.*;
import java.awt.event.*;

public class Namer extends JFrame
{
	public static void main(String [] args)
	{
		new Namer();
	}

	private JButton buttonOK;
	private JTextField textName;

	public Namer()
	{
	    this.setSize(325,100);
	    this.setTitle("Who Are You?");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ButtonListener bl = new ButtonListener();

	    JPanel panel1 = new JPanel();

		panel1.add(new JLabel("Enter your name: "));

		textName = new JTextField(15);
		panel1.add(textName);

		buttonOK = new JButton("OK");
		buttonOK.addActionListener(bl);
	    panel1.add(buttonOK);

	    this.add(panel1);

	    this.setVisible(true);
	}

	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == buttonOK)
			{
				String name = textName.getText();
				if (name.length() == 0)
				{
					JOptionPane.showMessageDialog(Namer.this,
					    "You didn't enter anything!",
					    "Moron",
					    JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(Namer.this,
					    "Good morning " + textName.getText(),
					    "Salutations",
					    JOptionPane.INFORMATION_MESSAGE);
				}
				textName.requestFocus();
			}
		}
	}
}