package advancedJavaProgramming.lab7;


public class BiblioDocument {
  private String author;
  private String title;
  private String year;

  public BiblioDocument() {}

  public BiblioDocument(String author, String title, String year) {
    this.author = author;
    this.title = title;
    this.year = year;
  }

  public String getAuthor() {
    return author;
  }

  public String getTitle() {
    return title;
  }

  public String getYear() {
    return year;
  }
}
