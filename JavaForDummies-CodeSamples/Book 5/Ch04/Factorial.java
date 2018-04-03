import java.util.Scanner;
import java.text.NumberFormat;

public class Factorial
{

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args)
	{
		NumberFormat nf = NumberFormat.getNumberInstance();

		int n = 5;
		long fact;
		fact = factorial(n);
		System.out.println("The factorial of "
			+ n + " is " + nf.format(fact));

	}

	private static long factorial(int n)
	{
		if (n == 1)
			return 1;
		else
			return n * factorial(n-1);
	}

	private static long fact(int n)
	{
		long f = 1;
		for (int i = 1; i <=n; i++)
		    f = f * i;
		return f;
	}


	private static boolean askAgain()
	{
		System.out.print("Another? (Y or N) ");
		String reply = sc.nextLine();
		if (reply.equalsIgnoreCase("Y"))
			return true;
		return false;
	}

}