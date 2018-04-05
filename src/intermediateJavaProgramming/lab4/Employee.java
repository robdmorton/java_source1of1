package intermediateJavaProgramming.lab4;

import java.util.Date;
import java.util.GregorianCalendar;

class Employee {
  private String name;
  private double salary;
  private Date hireDate;

  public Employee(String name, double salary, int year, int month, int day) {
    this.name = name;
    this.salary = salary;
    GregorianCalendar calendar = new GregorianCalendar(year, month, day);
    hireDate = calendar.getTime();
  }

  public double getSalary() {
    System.out.println("--- Employee getSalary()");
    return salary;
  }

  public String getName() {
    System.out.println("--- Employee getName()");
    return name;
  }

  public Date getHiredate() {
    System.out.println("--- Employee gethireDate()");
    return hireDate;
  }

}

