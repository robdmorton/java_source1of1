package com.lowewriter.payroll;

/** Represents an employee.
 * @author Doug Lowe
 * @author www.LoweWriter.com
 * @version 1.5
 * @since 1.0
*/
public class Employee implements Cloneable
{
	private String lastName;
	private String firstName;
	private Double salary;

/** Represents the employee's address.
*/
	public Address address;

/** Creates an employee with the specified name.
 * @param lastName The employee's last name.
 * @param firstName The employee's first name.
*/
	public Employee(String lastName, String firstName)
	{
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = new Address();
	}

/** Gets the employee's last name.
 * @return A string representing the employee's last name.
*/
	public String getLastName()
	{
		return this.lastName;
	}

/** Sets the employee's last name.
 * @param lastName A String containing the employee's last name.
 * @return No return value.
*/
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

/** Gets the employee's first name.
 * @return A string representing the employee's first name.
*/
	public String getFirstName()
	{
		return this.firstName;
	}

/** Sets the employee's first name.
 * @param firstName A String containing the employee's first name.
 * @return No return value.
*/
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

/** Gets the employee's salary.
 * @return A double representing the employee's salary.
*/
	public double getSalary()
	{
		return this.salary;
	}

/** Sets the employee's salary.
 * @param lastName A double containing the employee's salary.
 * @return No return value.
*/
	public void setSalary(double salary)
	{
		this.salary = salary;
	}

/** Creates a deep copy of the employee object.
 * @return A cloned Employee object. Note that you must
 *         explicitly cast this object to Employee.
*/
	public Object clone()
	{
		Employee emp;
		try
		{
			emp = (Employee) super.clone();
			emp.address = (Address)address.clone();
		}
		catch (CloneNotSupportedException e)
		{
			return null;   // will never happen
		}
		return emp;
	}

/** Returns a String representation of the employee.
 *  that includes the name of the class, the employee's
 *  first and last name, and the salary.
 * @return A String representation of the employee.
*/
	public String toString()
	{
		return this.getClass().getName() + "["
		    + this.firstName + " "
		    + this.lastName + ", "
		    + this.salary + "]";
	}

/** Compares this employee with another employee.
 *  Two employee objects are considered equal if
 *  they have the same last name, first name,
 *  and salary.
 * @return A boolean that indicates whether the
 *         employees are equal.
*/
	public boolean equals(Object obj)
	{
		// an object must equal itself
		if (this == obj)
		    return true;

		// no object equals null
	    if (this == null)
		    return false;

		// objects of different types are never equal
		if (this.getClass() != obj.getClass())
		    return false;

		// cast to an Employee, then compare the fields
		Employee emp = (Employee) obj;
		return this.lastName.equals(emp.getLastName())
		    && this.firstName.equals(emp.getFirstName());
	}
}