package books;

import java.util.ArrayList;
import java.text.NumberFormat;

public class BookCart
{
    private ArrayList<Book> cart;

    private NumberFormat cf
    	= NumberFormat.getCurrencyInstance();

    public BookCart()
    {
		cart = new ArrayList<Book>();
	}

	public void setBook(String code)
	{
		boolean found = false;
		for (Book b : cart)
		    if (b.getCode().equals(code))
		    {
				b.addQuantity(1);
				found = true;
			}
		if (!found)
			cart.add(new Book(code));
	}

	public String getList()
	{
		String list = "<table border=2>";
		list +="<tr><td>Title</td><td>Qty</td>"
			 + "<td>Price</td><td>Total</td></tr>";
		double total = 0.0;
		for (Book b : cart)
		{
			list += "<tr><td>" + b.getTitle() + "</td>"
  				 + "<td>" + b.getQuantity()  + "</td>"
	   	  	     + "<td>" + cf.format(b.getPrice()) + "</td>"
				 + "<td>" + cf.format(b.getTotal()) + "</td>"
				 + "</tr>";
			total += b.getTotal();
		}
		list +="<tr><td></td><td></td><td>Total:</td>"
		     + "<td>" + cf.format(total) + "</td></tr>";
		list += "</table>";
		return list;
	}

	private class Book
	{
		private String code;
		private int quantity;

		public Book(String code)
		{
			this.code = code;
			this.quantity = 1;
		}

		public String getCode()
		{
			return this.code;
		}

		public String getTitle()
		{
			if (code.equals("netfd"))
				return "Networking For Dummies";
			else if (code.equals("netaio"))
			    return "Networking All-in-One Desk Reference "
			    	+ "For Dummies";
			else if (code.equals("wordaio"))
			    return "Word 2003 All-in-One Desk Reference "
			        + "For Dummies";
			else
				return "Unknown book";
		}

		public double getPrice()
		{
			if (code.equals("netfd"))
				return 24.99;
			else if (code.equals("netaio"))
			    return 34.99;
			else if (code.equals("wordaio"))
				return 29.99;
			else
				return 0.0;
		}

		public int getQuantity()
		{
			return this.quantity;
		}

		public void addQuantity(int qty)
		{
			this.quantity += qty;
		}

		public double getTotal()
		{
			return this.quantity * this.getPrice();
		}
	}
}
