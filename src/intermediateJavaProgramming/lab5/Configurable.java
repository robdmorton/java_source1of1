package intermediateJavaProgramming.lab5;

public interface Configurable {

  //can have constants in an interface
  final int a = 1;
  
  public boolean configure();
	public boolean configure(Object configurationData);
}
