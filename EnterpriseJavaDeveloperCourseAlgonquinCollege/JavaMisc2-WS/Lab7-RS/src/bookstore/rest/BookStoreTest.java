package bookstore.rest;

import java.net.MalformedURLException;
import java.net.URL;

public class BookStoreTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("usage: java BookStoreTest booktitle");
		}
		BookSearchEngine engine = null;
		try {
			engine = new BookSearchEngine(new URL("file:///D:/nms_build_root/JavaMisc2/Lab7-RS/bookstore.xml"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BookSearchResult result = engine.searchBookByTitle(args[0]);
		if (result != null) {
			String bookTitle = result.getTitle();
			String bookAuthor = result.getAuthor();
			System.out.println("Title: "+bookTitle);
			System.out.println("Author: "+bookAuthor);
		} else {
			System.out.println("Book not found: "+args[0]);
		}
	}

}
