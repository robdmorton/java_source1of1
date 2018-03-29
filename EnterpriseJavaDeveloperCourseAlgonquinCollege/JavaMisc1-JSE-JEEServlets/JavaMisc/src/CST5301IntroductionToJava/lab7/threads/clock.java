package CST5301IntroductionToJava.lab7.threads;
/**
 * The clock class demonstrates animation with threads
 */
import java.awt.*; import java.applet.*; import java.util.*;

public class clock extends Applet implements Runnable
{
	Font f = new Font("TimesRoman",Font.BOLD,24);
	Date d; Thread runner;

    public void init()
    {resize(300,100);}

    public void paint (Graphics g)
    {g.setFont(f);g.drawString(d.toString(),10,50);}

    public void start()
    {
	  while (runner == null)
      {
		  runner = new Thread(this); runner.start();
	  }
    }

    public void run()
    {
	   while (true)
       {
		   d = new Date(); repaint();
		   try {Thread.sleep(1000);}
		   catch(InterruptedException e){};
	   }
    }
}
