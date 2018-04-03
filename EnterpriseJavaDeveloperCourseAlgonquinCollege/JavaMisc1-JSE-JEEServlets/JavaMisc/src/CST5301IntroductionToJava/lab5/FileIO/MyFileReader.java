package CST5301IntroductionToJava.lab5.FileIO;

import java.io.*;

class MyFileReader {
	public static void main(String[] args) 
	{
		if (args.length != 1) {
			System.err.println("usage: java fileReader <inputfile>");
		}

		FileReader in=null;
		try {
			in = new FileReader(args[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Writer out = new PrintWriter(System.out);
		char[] buf = new char[512];
		int howmany;
		try {
			while ((howmany = in.read(buf)) >= 0) {
				out.write(buf, 0, howmany);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}