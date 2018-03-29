import java.util.Scanner;

public class NumberPhobia
{
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args)
	{
		int number = 2;
		String input;

		while (true)
		{
			System.out.println(number + " ");
			System.out.print("Do you want keep counting?"
				+ " (Y or N)");
			input = sc.next();
			if (input.equalsIgnoreCase("N"))
				break;
			number += 2;
		}
		System.out.println("\nWhew! That was close.");
	}
}