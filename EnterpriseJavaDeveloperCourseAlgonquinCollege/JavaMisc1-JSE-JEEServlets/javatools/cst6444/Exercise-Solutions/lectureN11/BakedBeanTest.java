package lectureN11;

public class BakedBeanTest {
  public static void main(String[] args) {
    BakedBean bean = new BakedBean();
    System.out.println("Original bean: " +
                       "level=" + bean.getLevel() +
                       ", goesWith=" + bean.getGoesWith());
    if (args.length>1) {
      bean.setLevel(args[0]);
      bean.setGoesWith(args[1]);
      System.out.println("Updated bean: " +
                         "level=" + bean.getLevel() +
                         ", goesWith=" + bean.getGoesWith());
    }
  }
}
      
