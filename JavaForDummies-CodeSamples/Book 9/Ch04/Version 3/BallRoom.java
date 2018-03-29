import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.Math;
import java.awt.geom.*;

public class BallRoom extends JApplet
{
	public static final int WIDTH = 350;
	public static final int HEIGHT = 300;

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
	public ArrayList<Ball> balls = new ArrayList<Ball>();

	public PaintSurface()
	{
		for (int i = 0; i < 10; i++)
			balls.add(new Ball(20));
	}

	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.RED);
		for (Ball ball : balls)
		{
			ball.move();
			g2.fill(ball);
		}
	}
}

class Ball extends Ellipse2D.Float
{
	private int x_speed, y_speed;
	private int d;
	private int width = BallRoom.WIDTH;
	private int height = BallRoom.HEIGHT;

	public Ball(int diameter)
	{
		super((int)(Math.random() * (BallRoom.WIDTH - 20) + 1),
		      (int)(Math.random() * (BallRoom.HEIGHT - 20) + 1),
		      diameter, diameter);
		this.d = diameter;
		this.x_speed = (int)(Math.random() * 5 + 1);
		this.y_speed = (int)(Math.random() * 5 + 1);
	}

	public void move()
	{
		if (super.x < 0 || super.x > width - d)
			x_speed = -x_speed;
		if (super.y < 0 || super.y > height - d)
			y_speed = -y_speed;
		super.x += x_speed;
		super.y += y_speed;
	}
}
