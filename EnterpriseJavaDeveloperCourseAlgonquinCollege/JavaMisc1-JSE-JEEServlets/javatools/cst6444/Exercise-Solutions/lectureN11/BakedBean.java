package lectureN11;

public class BakedBean {
  private String level = "half-baked";
  private String goesWith = "hot dogs";

  public String getLevel() {
    return(level);
  }
  
  public void setLevel(String newLevel) {
    level = newLevel;
  }

  public String getGoesWith() {
    return(goesWith);
  }
  
  public void setGoesWith(String dish) {
    goesWith = dish;
  }
}
