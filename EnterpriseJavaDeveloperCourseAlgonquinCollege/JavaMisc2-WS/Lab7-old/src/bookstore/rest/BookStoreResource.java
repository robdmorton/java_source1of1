package bookstore.rest;

import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;

@Path("/bookstore")
public class BookStoreResource {
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("{title}")
	public String getBookByTitle(@PathParam("title") String title) {
		BookSearchEngine engine = null;
		try {
			engine = new BookSearchEngine(new URL("file:///D:/nms_build_root/JavaMisc2/Lab7-RS/bookstore.xml"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BookSearchResult result = engine.searchBookByTitle(title);
		StringBuilder sb = new StringBuilder();
		sb.append("<book-search-result>");
		if (result != null) {
			String bookTitle = result.getTitle();
			String bookAuthor = result.getAuthor();
			sb.append("<book>");
			sb.append("<title>");
			sb.append(bookTitle);
			sb.append("</title>");
			sb.append("<author>");
			sb.append(bookAuthor);
			sb.append("</author>");
			sb.append("</book>");
		} else {
			sb.append("<error>Book not found: ");
			sb.append(title);
			sb.append("</error>");
		}
		sb.append("</book-search-result>");
		return sb.toString();
	}
}
