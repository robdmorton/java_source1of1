public class TestEquality1
{
    public static void main(String[] args)
    {
		Employee emp1 = new Employee("Martinez", "Anthony");
		Employee emp2 = new Employee("Martinez", "Anthony");
		if (emp1 == emp2)
		    System.out.println("These employees are the same.");
		else
		    System.out.println("These are two different employees.");
    }
}

class Employee
{
	private String lastName;
	private String firstName;

	public Employee(String lastName, String firstName)
	{
		this.lastName = lastName;
		this.firstName = firstName;
	}
}
