package intermediateJavaProgramming.lab5;

import java.util.Date;

public abstract class Person {
	
	// variables
	private String firstName;
	private String lastName;
	private Date birthDate;
	
	// constructors
	public Person() {
	}

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	//methods
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
	
	//abstract method
	public abstract void setConfigurationData(Object configurationData);
}
