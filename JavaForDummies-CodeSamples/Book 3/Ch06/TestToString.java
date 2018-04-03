public class TestToString
{
    public static void main(String[] args)
    {
		Employee emp = new Employee("Martinez", "Anthony");
        System.out.println(emp.toString());

        int x = 50;
        String s = new Integer(x).toString();
        String s1 = "" + x;
        System.out.println(s);
        System.out.println(s1);
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

	public String toString()
	{
		return this.getClass().getName() + "["
		    + this.firstName + " "
		    + this.lastName + "]";
	}

}
