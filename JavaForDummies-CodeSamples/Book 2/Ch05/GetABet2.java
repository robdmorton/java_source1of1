import java.util.Scanner;

public class GetABet2
{

	static Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
    {
		int bank = 1000;   // we'll assume the user has $1,000
		int bet;

		System.out.println("You can bet between 1 and " + bank);
		do
		{
			System.out.print("Enter your bet: ");
			bet = sc.nextInt();
			if ( (bet <= 0) || (bet > bank) )
			    System.out.println("What, are you crazy?");
		} while ( (bet <= 0) || (bet > bank) );
		System.out.println("Your money's good here.");


    }
}
