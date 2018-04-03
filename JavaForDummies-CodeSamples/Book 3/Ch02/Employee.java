import java.text.NumberFormat;

public class Employee
{
	private String lastName;
	private String firstName;
	private double salary;

	private NumberFormat cf = NumberFormat.getCurrencyInstance();

	public Employee(String lastName,
		String firstName, double salary)
	{
		this.lastName = lastName;
		this.firstName = firstName;
		this.salary = salary;
	}

	public String getLastName()
	{
		return this.lastName;
	}

	public String getFirstName()
	{
		return this.firstName;
	}

	public String getFullName()
	{
		return this.firstName
			+ " " + this.lastName;
	}

	public double getSalary()
	{
		return this.salary;
	}

	public void setSalary(double salary)
	{
		this.salary = salary;
	}

	public String getFormattedSalary()
	{
		return cf.format(this.salary);
	}

	public void giveRaise(double percent)
	{
		this.salary *= (1.0 + percent);
	}

}