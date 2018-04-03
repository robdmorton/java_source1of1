import java.util.Scanner;

public class DogCatcher
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a string: ");
		String s = sc.nextLine();

		s = s.replaceAll("cat", "dog");

		System.out.println(s);
	}
}