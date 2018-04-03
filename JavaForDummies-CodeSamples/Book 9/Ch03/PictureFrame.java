import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class PictureFrame extends JFrame implements ActionListener
{
	Image img;
	JButton getPictureButton;

	public static void main(String [] args)
	{
		new PictureFrame();
	}

	public PictureFrame()
	{
		this.setSize(300, 300);
	    this.setTitle("Picture Frame Application");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JPanel picPanel = new PicturePanel();
	    this.add(picPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		getPictureButton = new JButton("Get Picture");
		getPictureButton.addActionListener(this);
		buttonPanel.add(getPictureButton);
		this.add(buttonPanel, BorderLayout.SOUTH);

	    this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		String file = getImageFile();
		if (file != null)
		{
			Toolkit kit = Toolkit.getDefaultToolkit();
			img = kit.getImage(file);
			img = img.getScaledInstance(300, -1, Image.SCALE_SMOOTH);
			this.repaint();
		}
	}

	private String getImageFile()
	{
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new ImageFilter());
	    int result = fc.showOpenDialog(null);
	    File file = null;
	    if (result == JFileChooser.APPROVE_OPTION)
	    {
	        file = fc.getSelectedFile();
	    	return file.getPath();
		}
		else
			return null;
	}

	private class PicturePanel extends JPanel
	{
		public void paint(Graphics g)
		{
			g.drawImage(img, 0, 0, this);
		}
	}

	private class ImageFilter
	    extends javax.swing.filechooser.FileFilter
	{
	    public boolean accept(File f)
	    {
	        if (f.isDirectory())
	            return true;
	        String name = f.getName();
	        if (name.matches(".*((.jpg)|(.gif)|(.png))"))
	           return true;
	        else
	           return false;
	    }

	    public String getDescription()
	    {
	        return "Image files (*.jpg, *.gif, *.png)";
	    }
	}
}