import java.util.Scanner;

public class CountWords
{
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args)
	{
		System.out.print("Enter a string: ");
		String s = sc.nextLine();

		String[] word = s.split("\\s+");

		for (String w : word)
			System.out.println(w);

	}
}