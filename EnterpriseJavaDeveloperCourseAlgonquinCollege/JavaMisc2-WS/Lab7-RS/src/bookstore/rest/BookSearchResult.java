package bookstore.rest;

public class BookSearchResult {

    private String author;

    private String title;

    private String genre;

    private int quantity;

    public BookSearchResult() {
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTitle() {
        return this.title;
    }

    public String getGenre() {
        return this.genre;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
