import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class PizzaApplet extends JApplet
{
	private JButton buttonOK;
	private JRadioButton small, medium, large;

	private JCheckBox pepperoni, mushrooms, anchovies;

	public void init()
	{
	    this.setSize(320,200);

		ButtonListener bl = new ButtonListener();

		JPanel mainPanel = new JPanel();

	    JPanel sizePanel = new JPanel();
		Border b1 = BorderFactory.createTitledBorder("Size");
		sizePanel.setBorder(b1);

		ButtonGroup sizeGroup = new ButtonGroup();

		small = new JRadioButton("Small");
		small.setSelected(true);
		sizePanel.add(small);
		sizeGroup.add(small);

		medium = new JRadioButton("Medium");
		sizePanel.add(medium);
		sizeGroup.add(medium);

		large = new JRadioButton("Large");
		sizePanel.add(large);
		sizeGroup.add(large);

		mainPanel.add(sizePanel);

		JPanel topPanel = new JPanel();
		Border b2 = BorderFactory.createTitledBorder("Toppings");
		topPanel.setBorder(b2);

		pepperoni = new JCheckBox("Pepperoni");
		topPanel.add(pepperoni);

		mushrooms = new JCheckBox("Mushrooms");
		topPanel.add(mushrooms);

		anchovies = new JCheckBox("Anchovies");
		topPanel.add(anchovies);

		mainPanel.add(topPanel);

		buttonOK = new JButton("OK");
		buttonOK.addActionListener(bl);
	    mainPanel.add(buttonOK);

	    this.add(mainPanel);

	}

	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == buttonOK)
			{
				String tops = "";
				if (pepperoni.isSelected())
					tops += "Pepperoni\n";
				if (mushrooms.isSelected())
				    tops += "Mushrooms\n";
				if (anchovies.isSelected())
				    tops += "Anchovies\n";

				String msg = "You ordered a ";
				if (small.isSelected())
					msg += "small pizza with ";
				if (medium.isSelected())
					msg += "medium pizza with ";
				if (large.isSelected())
					msg += "large pizza with ";

				if (tops.equals(""))
					msg += "no toppings.";
				else
					msg += "the following toppings:\n" + tops;
				JOptionPane.showMessageDialog(buttonOK,
					msg, "Your Order",
					JOptionPane.INFORMATION_MESSAGE);

				pepperoni.setSelected(false);
				mushrooms.setSelected(false);
				anchovies.setSelected(false);
				small.setSelected(true);
			}
		}
	}
}