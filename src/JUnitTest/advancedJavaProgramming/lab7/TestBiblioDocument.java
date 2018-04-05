package JUnitTest.advancedJavaProgramming.lab7;


import advancedJavaProgramming.lab7.BiblioDocument;
import org.junit.Test;
import static org.junit.Assert.*;

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
