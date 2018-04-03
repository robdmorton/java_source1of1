
package conceptTesting;

import java.net.*;
import java.io.*;
import java.util.*;
//import sockets.*;

public class BartServer2
{
	public static void main(String[] args)
	{
		int port = 1234;

		Quote bart = new Quote();

		try(ServerSocket ss = new ServerSocket(port,1);)
		{
			System.out.println("BartServer 2.0");
			System.out.println("Listening on port " + port);			
			System.out.println(
					"Server Socket: " + ss.toString());
			
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
		finally
		{
		}
	}
}


class BartThread implements Runnable
{
	private Socket s;
	private Quote bart;

	public BartThread(Socket socket, Quote bart)
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