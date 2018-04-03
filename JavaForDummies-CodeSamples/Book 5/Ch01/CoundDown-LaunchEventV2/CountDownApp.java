import java.util.ArrayList;

public class CountDownApp
{
	public static void main(String[] args)
	{
		CountDownClock clock = new CountDownClock(20);

		ArrayList<Runnable> events
			= new ArrayList<Runnable>();
		events.add(new LaunchEvent(16, "Flood the pad!", clock));
		events.add(new LaunchEvent(6, "Start engines!", clock));
		events.add(new LaunchEvent(0, "Liftoff!", clock));

		clock.start();

		for (Runnable e : events)
			new Thread(e).start();
    }
}

interface TimeMonitor
{
	int getTime();
}

class CountDownClock extends Thread implements TimeMonitor
{
	private int t;

	public CountDownClock(int start)
	{
		this.t = start;
	}

	public void run()
	{
		for (; t >= 0; t--)
		{
            System.out.println("T minus " + t);
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {}
		}
	}

	public int getTime()
	{
		return t;
	}
}

class LaunchEvent implements Runnable
{
    private int start;
    private String message;
    TimeMonitor tm;

    public LaunchEvent(int start, String message,
    	TimeMonitor monitor)
    {
        this.start = start;
        this.message = message;
        this.tm = monitor;
    }

	public void run()
	{
		boolean eventDone = false;
		while (!eventDone)
		{
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{}
			if (tm.getTime() <= start)
			{
				System.out.println(this.message);
				eventDone = true;
			}
		}
	}
}



