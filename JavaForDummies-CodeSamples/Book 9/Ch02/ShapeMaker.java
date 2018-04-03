import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class ShapeMaker extends JFrame
{

	public static void main(String [] args)
	{
		new ShapeMaker();
	}

	public ShapeMaker()
	{
		this.setSize(300, 300);
	    this.setTitle("Shape Maker");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new PaintSurface(), BorderLayout.CENTER);
		this.setVisible(true);
	}

	private class PaintSurface extends JComponent
	{
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		Point startDrag, endDrag;
		Shape found = null;

		public PaintSurface()
		{
			Shape s;

			// a rectangle
			s = new Rectangle2D.Float(10, 10, 60, 80);
			shapes.add(s);

			// a rounded rectangle
			s = new RoundRectangle2D.Float(110, 10, 80, 80,
						10, 10);
			shapes.add(s);

			// a rounded rectangle
			s = new RoundRectangle2D.Float(210, 10, 60, 80,
						50, 75);
			shapes.add(s);

			// a circle
			s = new Ellipse2D.Float(10, 110, 80, 80);
			shapes.add(s);

			// an ellipse
			s = new Ellipse2D.Float(110, 110, 80, 40);
			shapes.add(s);

			// another ellipse
			s = new Ellipse2D.Float(210, 110, 40, 80);
			shapes.add(s);

			// an arc
			s = new Arc2D.Float(10, 210, 80, 80, 90, 90,
				Arc2D.OPEN);
			shapes.add(s);

			// another arc
			s = new Arc2D.Float(110, 210, 80, 80, 0, 180,
				Arc2D.CHORD);
			shapes.add(s);

			// another arc
			s = new Arc2D.Float(210, 210, 80, 80, 45, 90,
				Arc2D.PIE);
			shapes.add(s);
		}


		public void paint(Graphics g)
		{
			Graphics2D g2 = (Graphics2D)g;

			// turn on antialiasing
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

			// draw background grid
			g2.setPaint(Color.LIGHT_GRAY);
			for (int i = 0; i < getSize().width; i += 10)
				g2.draw(new Line2D.Float(i, 0, i, getSize().height));
			for (int i = 0; i < getSize().height; i += 10)
				g2.draw(new Line2D.Float(0, i, getSize().width, i));

			// draw all the shapes in the array list
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(2));
			for (Shape s : shapes)
				g2.draw(s);
		}
	}
}