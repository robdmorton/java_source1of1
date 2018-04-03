import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class DrawingBoard extends JFrame
{

	public static void main(String [] args)
	{
		new DrawingBoard();
	}

	public DrawingBoard()
	{
		this.setSize(300, 300);
	    this.setTitle("The Drawing Board");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new PaintSurface(), BorderLayout.CENTER);
		this.setVisible(true);
	}

	private class PaintSurface extends JComponent
	{
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		Point startDrag, endDrag;

		public PaintSurface()
		{
			this.addMouseListener(new MouseAdapter()
			  {
			    public void mousePressed(MouseEvent e)
			    {
			  	  startDrag = new Point(e.getX(), e.getY());
				  endDrag = startDrag;
				  repaint();
				}

			    public void mouseReleased(MouseEvent e)
				{
			  	  Shape r = makeRectangle(
				   	          startDrag.x, startDrag.y,
  						      e.getX(), e.getY());
  				  shapes.add(r);
				  startDrag = null;
				  endDrag = null;
				  repaint();
				}
			  } );

			this.addMouseMotionListener(new MouseMotionAdapter()
			{
			  public void mouseDragged(MouseEvent e)
			  {
			    endDrag = new Point(e.getX(), e.getY());
				repaint();
			  }
			} );
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
			{
				Shape line = new Line2D.Float(
					i, 0, i, getSize().height);
				g2.draw(line);
			}

			for (int i = 0; i < getSize().height; i += 10)
			{
				Shape line = new Line2D.Float(
					0, i, getSize().width, i);
				g2.draw(line);
			}

			// draw the shapes
			Color[] colors = {Color.RED, Color.BLUE, Color.PINK,
			    Color.YELLOW, Color.MAGENTA, Color.CYAN };
			int colorIndex = 0;

			g2.setStroke(new BasicStroke(2));
			g2.setComposite(AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.50f));

			for (Shape s : shapes)
			{
				g2.setPaint(Color.BLACK);
				g2.draw(s);
				g2.setPaint(colors[(colorIndex++)%6]);
				g2.fill(s);
			}

			// paint the temporary rectangle
			if (startDrag != null && endDrag != null)
			{
				g2.setPaint(Color.LIGHT_GRAY);
				Shape r = makeRectangle(startDrag.x, startDrag.y,
					endDrag.x, endDrag.y);
				g2.draw(r);
			}
		}

		private Rectangle2D.Float makeRectangle(
			int x1, int y1, int x2, int y2)
		{
			int x = Math.min(x1, x2);
			int y = Math.min(y1, y2);
			int width = Math.abs(x1 - x2);
			int height = Math.abs(y1 - y2);

			return new Rectangle2D.Float(
				x, y, width, height);
		}
	}
}