import java.io.*;

public class WriteFile
{
	public static void main(String[] args)
	{
		Movie[] movies = getMovies();

		PrintWriter out = openWriter("movies.txt");
		for (Movie m : movies)
			writeMovie(m, out);
		out.close();
	}

	private static Movie[] getMovies()

	{
		Movie[] movies = new Movie[10];

		movies[0] = new Movie("It's a Wonderful Life", 1946, 14.95);
		movies[1] = new Movie("The Great Race", 1965, 12.95);
		movies[2] = new Movie("Young Frankenstein", 1974, 16.95);
		movies[3] = new Movie("The Return of the Pink Panther", 1975, 11.95);
		movies[4] = new Movie("Star Wars", 1977, 17.95);
		movies[5] = new Movie("The Princess Bride", 1987, 16.95);
		movies[6] = new Movie("Glory", 1989, 14.95);
		movies[7] = new Movie("Apollo 13", 1995, 19.95);
		movies[8] = new Movie("The Game", 1997, 14.95);
		movies[9] = new Movie("The Lord of the Rings: Fellowship of the Rings", 2001, 19.95);

		return movies;
	}



	private static PrintWriter openWriter(String name)
	{
		try
		{
			File file = new File(name);
			PrintWriter out =
			    new PrintWriter(
					new BufferedWriter(
						new FileWriter(file) ), true ); // flush
			return out;
		}
		catch (IOException e)
		{
			System.out.println("I/O Error");
			return null;
		}

	}

	private static void writeMovie(Movie m, PrintWriter out)
	{
		String line = m.title;
		line += "\t" + Integer.toString(m.year);
		line += "\t" + Double.toString(m.price);
		out.println(line);
	}


	private static class Movie
	{
		public String title;
		public int year;
		public double price;

		public Movie(String title, int year, double price)
		{
			this.title = title;
			this.year = year;
			this.price = price;
		}
	}
}