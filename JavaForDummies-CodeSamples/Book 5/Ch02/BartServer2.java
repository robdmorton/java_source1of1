import java.net.*;
import java.io.*;
import java.util.*;

public class BartServer2
{
	public static void main(String[] args)
	{
		int port = 1234;

		BartQuote bart = new BartQuote();

		try
		{
			System.out.println("BartServer 2.0");
			System.out.println("Listening on port " + port);
			ServerSocket ss = new ServerSocket(port);

			while (true)
			{
				Socket s = ss.accept();
				System.out.println(
					"Connection established!");
				Thread t =
					new Thread(new BartThread(s, bart));
				t.start();
			}
		}
		catch (Exception e)
		{
			System.out.println("System exception!");
		}
	}
}

class BartThread implements Runnable
{
	private Socket s;
	private BartQuote bart;

	public BartThread(Socket socket, BartQuote bart)
	{
		this.s = socket;
		this.bart = bart;
	}

	public void run()
	{
		String client = s.getInetAddress().toString();
		System.out.println("Connected to " + client);
		try
		{
			Scanner in = new Scanner(s.getInputStream());
			PrintWriter out;
			out = new PrintWriter(s.getOutputStream(),
				true);

			out.println("Welcome to the Bart Server");
			out.println("Enter BYE to exit.");

			while (true)
			{
				String input = in.nextLine();
				if (input.equalsIgnoreCase("bye"))
					break;
				else if (input.equalsIgnoreCase("get"))
				{
					out.println(bart.getQuote());
					System.out.println("Serving " + client);
				}
				else
					out.println("Huh?");
			}
			out.println("So long, suckers!");
			s.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		System.out.println("Closed connection to " + client);
	}
}