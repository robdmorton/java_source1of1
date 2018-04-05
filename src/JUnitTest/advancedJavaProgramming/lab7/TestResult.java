package JUnitTest.advancedJavaProgramming.lab7;


import advancedJavaProgramming.lab7.BiblioDocument;
import advancedJavaProgramming.lab7.Result;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestResult {

  public TestResult() {}

  @BeforeClass
  public static void setUpClass() throws Exception {}

  @AfterClass
  public static void tearDownClass() throws Exception {}

  // all tests above were created by NetBeans
  @Test
  public void EmptyResultTest() {
    Result r = new Result();
    assertEquals(0, r.getCount());
  }

  @Test
  public void ResultWithTwoDocumentsTest() {
    BiblioDocument d1 = new BiblioDocument("a1", "t1", "1999");
    BiblioDocument d2 = new BiblioDocument("a2", "t2", "2000");
    Result r = new Result(new BiblioDocument[] {d1, d2});
    assertSame(r.getItem(0), d1);
    assertSame(r.getItem(1), d2);
  }
}
