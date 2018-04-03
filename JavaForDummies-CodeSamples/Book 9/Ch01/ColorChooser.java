import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ColorChooser extends JFrame
{
	public static void main(String [] args)
	{
		new ColorChooser();
	}

	private JLabel sampleText;
	private JButton chooseButton;

	public ColorChooser()
	{
	    this.setSize(300,100);
	    this.setTitle("Color Chooser");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JPanel panel1 = new JPanel();

		sampleText = new JLabel("All work and no play makes Jack a dull boy");
		sampleText.setBackground(null);
		panel1.add(sampleText);

		chooseButton = new JButton("Choose Color");
		chooseButton.addActionListener(new ButtonListener());

		chooseButton.setBackground(SystemColor.info);
		panel1.add(chooseButton);

	    this.add(panel1);
	    this.setVisible(true);
	}

	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Color c = JColorChooser.showDialog(null, "Choose a Color",
			    sampleText.getForeground());
			if (c != null)
				sampleText.setForeground(c);
		}
	}
}