import java.text.*;

public class FormatTest {
  public static void main(String[] args) {
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    double[] nums = { 12.0, 13.45, 15.623, 12312312.954444 };
    for(int i=0; i<nums.length; i++) {
      System.out.println(formatter.format(nums[i]));
    }
  }
}
