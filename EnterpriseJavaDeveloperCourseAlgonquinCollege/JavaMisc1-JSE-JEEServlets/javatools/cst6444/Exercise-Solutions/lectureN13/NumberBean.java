package lectureN13;

public class NumberBean {
  private double num = 0;

  public NumberBean() {}
  
  public NumberBean(double number) {
    setNumber(number);
  }
  
  public double getNumber() {
    return(num);
  }
  
  public void setNumber(double number) {
    num = number;
  }
}
