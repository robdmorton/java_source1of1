package intermediateJavaProgramming.lab4;

class Manager extends Employee { 
  private double bonus;

  public Manager(String name, double salary, 
                 int year, int month, int day) {
    super(name, salary, year, month, day);
    bonus = 0.0;
  }
 
  public double getSalary() 
  {
	System.out.println("--- Manager getSalary()");	  
    return super.getSalary() + bonus;
  }
  
  public void setBonus(double aBonus) {
	  System.out.println("--- Manager setBonus()");	  
	  bonus = aBonus;
  }
}

