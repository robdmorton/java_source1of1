import java.text.*;
import java.math.*;
import java.util.*;

public class GoodTax
{
	static Scanner sc = new Scanner(System.in);
	static NumberFormat cf = NumberFormat.getCurrencyInstance();

	public static void main (String[] args)
	{
		BigDecimal subTotal, salesTax, invoiceTotal;
		BigDecimal taxRate = new BigDecimal("0.05");

		double subD, taxD, totalD;

		System.out.println(
			"Welcome to the sales tax calculator.");
		do
		{
			System.out.print("\nEnter subtotal: ");
			subTotal = new BigDecimal(sc.nextLine());

			salesTax = subTotal.multiply(taxRate);
			salesTax = salesTax.setScale(2, RoundingMode.HALF_UP);
			invoiceTotal = subTotal.add(salesTax);

			subD = subTotal.doubleValue();
			taxD = salesTax.doubleValue();
			totalD = invoiceTotal.doubleValue();

			System.out.print("Subtotal:   ");
			System.out.println(cf.format(subD));
			System.out.print("Sales tax:  ");
			System.out.println(cf.format(taxD));
			System.out.print("Total:      ");
			System.out.println(cf.format(totalD));
		} while (getAnother());
	}

	static BigDecimal round(BigDecimal d)
	{
		return d.setScale(2, RoundingMode.HALF_UP);
	}

	static boolean getAnother()
	{
		System.out.print("\nAgain? (Y or N) ");
		if (sc.nextLine().equalsIgnoreCase("Y"))
			return true;
		else
			return false;
	}
}