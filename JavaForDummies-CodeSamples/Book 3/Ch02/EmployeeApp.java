import java.util.Scanner;

public class EmployeeApp
{
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args)
	{
		String lastName;
		String firstName;
		double salary;
		String answer;
		double percent;

		System.out.println("Welcome to the Employee Application\n");

		System.out.print("Enter employee's last name: ");
		lastName = sc.next();

		System.out.print("Enter employee's first name: ");
		firstName = sc.next();

		System.out.print("Enter employee's salary: ");
		salary = sc.nextDouble();

		// create the employee object
		Employee emp = new Employee(lastName, firstName, salary);
		System.out.println();
		System.out.println("Employee: " + emp.getFullName());
		System.out.println("Salary:   " + emp.getFormattedSalary());

		System.out.println();
		System.out.print("Would you like to give this employee a raise? ");
		answer = sc.next();
		if (answer.equalsIgnoreCase("Y"))
			{
				System.out.print("What percentage? ");
				percent = sc.nextDouble();
				percent *= 0.01;
				emp.giveRaise(percent);
				System.out.println("The employee's new salary is "
					+ emp.getFormattedSalary());
			}

		System.out.println("\nThank you for using the Employee Application.");

	}
}
