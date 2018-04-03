package genericsinheritanceinterfacesetc;

public class GenericsMethods
{
	
  //Generics in method
  public static <T> boolean isEqual(GenericsType<T> g1, GenericsType<T> g2){
      return g1.get().equals(g2.get());
  }
  
  public static <T extends Comparable<T>> int compare(T t1, T t2)
  {
  	return t1.compareTo(t2);
  }

}
