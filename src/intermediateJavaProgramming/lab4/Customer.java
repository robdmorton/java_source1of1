package intermediateJavaProgramming.lab4;

public class Customer extends Person {

	private String accountNumber;
	
	public Customer() {
	}
	
	public Customer(String personData) {
		super(personData);
	}
	
	public Customer(String firstName, String lastName) {
		super(firstName, lastName);
	}
	
	public Customer(String firstName, String lastName, String accountNumber) {
		this(firstName, lastName);
		this.accountNumber = accountNumber;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public void parseCustomer() {
		if ( getPersonData() == null )
			return;
		
		/*
		StringTokenizer st = new StringTokenizer(getPersonData(), ",");
		setFirstName(st.nextToken());
		setLastName(st.nextToken());
		setAccountNumber(st.nextToken());
		*/
		String[] st = getPersonData().split(",");
		setFirstName(st[0]);
		setLastName(st[1]);
		setAccountNumber(st[2]);
		// format: first,last,account#
	}
	
	public void parseCustomer(String customerData) {
		setPersonData(customerData);
		parseCustomer();
	}
	
	public String toString() {
		return super.toString() + ": " + accountNumber;
	}
	
	public boolean equals(Object o) {
		if ( o == this ) 
			return true;
		if ( ! ( o instanceof Customer) )
			return false;
		return super.equals(o) && 
		       ((Customer)o).getAccountNumber().equals(accountNumber);
	}
	
	public void privateTestCaller()
	{
	   privateTest();
	}
	
	//This is a test to ensure that the private scope of Person class's 
	//privateTest method is respected in a derived class of the Person class
//	@Override
	private void privateTest()
  {
    char a = 0;
    System.out.println(a);
  }

}

