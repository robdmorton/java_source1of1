public class LaunchEvent implements Runnable
{
	private int start;
	private String message;

	public LaunchEvent(int start, String message)
	{
		this.start = start;
		this.message = message;
	}

	public void run()
	{
		try
		{
			Thread.sleep(20000 - (start * 1000));
		}
		catch (InterruptedException e)
		{}
		System.out.println(message);
	}
}







