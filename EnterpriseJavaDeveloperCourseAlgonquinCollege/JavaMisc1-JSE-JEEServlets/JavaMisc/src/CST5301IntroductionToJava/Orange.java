package CST5301IntroductionToJava;

public class Orange extends GenericFruit {
  
  boolean seedless;
  
  Orange()
  {
    
  }
  
  Orange(int s, boolean sl)
  {
    super(s);
    seedless = sl;
  }

  boolean isSeedless()
  {
    return seedless;
  }
  
}
