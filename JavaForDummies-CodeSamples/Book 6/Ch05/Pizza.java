import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Pizza extends JFrame
{
	public static void main(String [] args)
	{
		new Pizza();
	}

	JTextField name, phone, address;
	JRadioButton small, medium, large, thick, thin;
	JCheckBox pepperoni, mushrooms, anchovies;
	JButton okButton, closeButton;

	public Pizza()
	{
	    this.setTitle("Pizza Order");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JPanel panel1 = new JPanel();
	    panel1.setLayout(new GridBagLayout());

		addItem(panel1, new JLabel("Name:"), 0, 0, 1, 1,
			GridBagConstraints.EAST);
		addItem(panel1, new JLabel("Phone:"), 0, 1, 1, 1,
			GridBagConstraints.EAST);
		addItem(panel1, new JLabel("Address:"), 0, 2, 1, 1,
			GridBagConstraints.EAST);

		name = new JTextField(20);
		phone = new JTextField(10);
		address = new JTextField(20);

		addItem(panel1, name, 1, 0, 2, 1,
			GridBagConstraints.WEST);
		addItem(panel1, phone, 1, 1, 1, 1,
			GridBagConstraints.WEST);
		addItem(panel1, address, 1, 2, 2, 1,
			GridBagConstraints.WEST);

		Box sizeBox = Box.createVerticalBox();
		small = new JRadioButton("Small");
		medium = new JRadioButton("Medium");
		large = new JRadioButton("Large");
		ButtonGroup sizeGroup = new ButtonGroup();
		sizeGroup.add(small);
		sizeGroup.add(medium);
		sizeGroup.add(large);
		sizeBox.add(small);
		sizeBox.add(medium);
		sizeBox.add(large);
		sizeBox.setBorder(
			BorderFactory.createTitledBorder("Size"));
		addItem(panel1, sizeBox, 0, 3, 1, 1,
			GridBagConstraints.NORTH);

		Box styleBox = Box.createVerticalBox();
		thin = new JRadioButton("Thin");
		thick = new JRadioButton("Thick");
		ButtonGroup styleGroup = new ButtonGroup();
		styleGroup.add(thin);
		styleGroup.add(thick);
		styleBox.add(thin);
		styleBox.add(thick);
		styleBox.setBorder(
			BorderFactory.createTitledBorder("Style"));
		addItem(panel1, styleBox, 1, 3, 1, 1,
			GridBagConstraints.NORTH);

		Box topBox = Box.createVerticalBox();
		pepperoni = new JCheckBox("Pepperoni");
		mushrooms = new JCheckBox("Mushrooms");
		anchovies = new JCheckBox("Anchovies");
		ButtonGroup topGroup = new ButtonGroup();
		topGroup.add(pepperoni);
		topGroup.add(mushrooms);
		topGroup.add(anchovies);
		topBox.add(pepperoni);
		topBox.add(mushrooms);
		topBox.add(anchovies);
		topBox.setBorder(
			BorderFactory.createTitledBorder("Toppings"));
		addItem(panel1, topBox, 2, 3, 1, 1,
			GridBagConstraints.NORTH);

		Box buttonBox = Box.createHorizontalBox();

		okButton = new JButton("OK");
		closeButton = new JButton("Close");

		buttonBox.add(okButton);
		buttonBox.add(Box.createHorizontalStrut(20));
		buttonBox.add(closeButton);

		addItem(panel1, buttonBox, 2, 4, 1, 1,
			GridBagConstraints.NORTH);

	    this.add(panel1);
	    this.pack();
	    this.setVisible(true);
	}

	private void addItem(JPanel p, JComponent c,
	    int x, int y, int width, int height, int align)
	{
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = x;
		gc.gridy = y;
		gc.gridwidth = width;
		gc.gridheight = height;
		gc.weightx = 100.0;
		gc.weighty = 100.0;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.anchor = align;
		gc.fill = GridBagConstraints.NONE;
		p.add(c, gc);
	}

}