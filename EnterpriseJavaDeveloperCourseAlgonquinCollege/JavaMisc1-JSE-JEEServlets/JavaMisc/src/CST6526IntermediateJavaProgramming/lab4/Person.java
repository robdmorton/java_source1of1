package CST6526IntermediateJavaProgramming.lab4;

import java.util.Date;

public class Person {
	// fields
	private String personData;
	private String firstName;
	private String lastName;
	private Date birthDate;
	
	// default empty constructor
  public Person() {
	}

	public Person(String personData) {
	  privateTest();
		this.personData = personData;
	}
	
	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getPersonData() {
		return personData;
	}
	
	public void setPersonData(String personData) {
		this.personData = personData;
	}
	
	public String getFirstName() {
	  return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}
	
	public void setBirhDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getFullName() {
	  return firstName + " " + lastName;
	}
	
	public String toString() {
		return getFullName();
	}
	
	public boolean equals(Object o) {
		if ( o == this )
			return true;
		if ( ! (o instanceof Person) ) 
			return false;
		return ((Person)o).getFirstName().equals(firstName) &&
		       ((Person)o).getLastName().equals(lastName);
	}
	
	private void privateTest()
	{
	  personData = null;
	}

}
