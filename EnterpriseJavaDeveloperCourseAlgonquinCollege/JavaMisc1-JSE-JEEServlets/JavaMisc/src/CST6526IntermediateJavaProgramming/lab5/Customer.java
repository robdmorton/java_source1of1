package CST6526IntermediateJavaProgramming.lab5;

public class Customer extends Person //implements Configurable
{

	//variables
	private String accountNumber;
	private Object configurationData;
	
	//constructors
	public Customer() {
	}
	
	public Customer(String configurationData) {
		setConfigurationData(configurationData);
	}
	public Customer(String firstName, String lastName) {
		super(firstName, lastName);
	}
	
	public Customer(String firstName, String lastName, String accountNumber) {
		this(firstName, lastName);
		this.accountNumber = accountNumber;
	}
	
	//methods
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public void setConfigurationData(Object configurationData) {
		this.configurationData = configurationData;
	}
	
	//added
	public boolean configure() {
		return configure(this.configurationData);
	}
	
	//added
	public boolean configure(Object configurationData)	 {
		boolean configSuccess = false;
		
		if ( configurationData instanceof String ) {
			// format: first,last,account#
			String[] st = ((String)configurationData).split(",");
			setFirstName(st[0]);
			setLastName(st[1]);
			setAccountNumber(st[2]);
			configSuccess = true;
		}
		return configSuccess;
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
}

