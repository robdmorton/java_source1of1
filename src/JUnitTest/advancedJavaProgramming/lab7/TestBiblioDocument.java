package JUnitTest.advancedJavaProgramming.lab7;


import static org.junit.Assert.assertEquals;
import org.junit.Test;
import advancedJavaProgramming.lab7.BiblioDocument;

public class TestBiblioDocument {
  BiblioDocument bibDoc;

  @Test
  public void documentCreationTest() {
    bibDoc = new BiblioDocument("a", "t", "1999");
    assertEquals("a", bibDoc.getAuthor());
    assertEquals("t", bibDoc.getTitle());
    assertEquals("1999", bibDoc.getYear());
    bibDoc = null;
  }
}
