package CST6526IntermediateJavaProgramming.lab4;


public class testEmpMgr {
	
  public static void main(String[] args) 
	{
    Employee john = new Employee("john", 12.50, 1957,01,01 );
    Manager susan = new Manager("susan", 22.50, 1980,12,12);
  
  	susan.setBonus(0.0);
  	
  	System.out.println("john's salary= " + john.getSalary());
  	System.out.println("susan's salary= " + susan.getSalary());
  	
  	Employee e = john;
  	System.out.println("e is now john");
  	System.out.println("e's salary= " + e.getSalary());	
  	e = susan;
  	System.out.println("e is now susan");	
  	System.out.println("e's salary= " + e.getSalary());
  	
  	Manager m;
  	m=(Manager)e;
  	m.setBonus(10);
  	
  	// e.setBonus(10); // compiler error.
	}
}
