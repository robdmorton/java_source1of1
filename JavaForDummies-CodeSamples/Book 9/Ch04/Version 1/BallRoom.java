import java.applet.*;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class BallRoom extends JApplet
{
	private final int WIDTH = 350;
	private final int HEIGHT = 300;

	private PaintSurface canvas;

	public void init()
	{
		this.setSize(WIDTH, HEIGHT);
		canvas = new PaintSurface();
		this.add(canvas, BorderLayout.CENTER);
		Thread t = new AnimationThread(this);
		t.start();
	}
}

class AnimationThread extends Thread
{

	JApplet c;

	public AnimationThread(JApplet c)
	{
		this.c = c;
	}

	public void run()
	{
		while (true)
		{
			c.repaint();
			try
			{
				Thread.sleep(20);
			}
			catch (InterruptedException ex)
			{
				// swallow the exception
			}
		}
	}
}

class PaintSurface extends JComponent
{
	int x_pos = 0;     // the starting X position
	int y_pos = 150;   // the starting Y position
	int d = 20;        // the diameter of the ball

	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

		x_pos += 1;    // move the ball right one pixel

		Shape ball = new Ellipse2D.Float(
			x_pos, y_pos, d, d);
		g2.setColor(Color.RED);
		g2.fill(ball);
	}
}
