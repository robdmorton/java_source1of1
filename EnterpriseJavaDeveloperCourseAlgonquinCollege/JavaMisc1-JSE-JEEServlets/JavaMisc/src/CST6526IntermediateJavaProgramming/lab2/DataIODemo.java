package CST6526IntermediateJavaProgramming.lab2;

/*
 * Created on Feb 23, 2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

/**
 * @author wce00053
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataIODemo {
	public static void main(String[] args) throws IOException {
		// write the data out
		DataOutputStream out =
			new DataOutputStream(new FileOutputStream("invoice1.txt"));
		double[] prices = { 19.99, 9.99, 15.99, 3.99, 4.99 };
		int[] units = { 12, 8, 13, 29, 50 };
		String[] descs =
			{
				"Java T-shirt",
				"Java Mug",
				"Duke Juggling Dolls",
				"Java Pin",
				"Java Key Chain" };

		for (int i = 0; i < prices.length; i++) {
			out.writeDouble(prices[i]);
			out.writeChar('\t');
			out.writeInt(units[i]);
			out.writeChar('\t');
			out.writeChars(descs[i]);
			out.writeChar('\n');
		}
		out.close();

		DataInputStream in =
			new DataInputStream(new FileInputStream("invoice1.txt"));
		double price;
		int unit;
		String desc = "";
		double total = 0.0;
		char theDesc[] = new char[1024];
		try {
			while (true) {
				price = in.readDouble();
				in.readChar(); // throws out the tab
				unit = in.readInt();
				in.readChar(); // throws out the tab
				char ch = 0;
				int i = 0;
				while (i < 1024 && (ch = in.readChar()) != '\n') {
					theDesc[i] = ch;
					i++;
				}
				desc = new String(theDesc, 0, i);
				System.out.println(
					"You've ordered "
						+ unit
						+ " units of "
						+ desc
						+ " at $"
						+ price);
				total = total + unit * price;
			}
		} catch (EOFException e) {
		}
		System.out.println("For a TOTAL of: $" + total);
		in.close();
	}
}
