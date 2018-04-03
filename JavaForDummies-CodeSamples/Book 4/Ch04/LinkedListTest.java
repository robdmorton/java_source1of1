import java.util.*;

public class LinkedListTest
{
	public static void main(String[] args)
	{
		addTest();
		System.out.println();
		addFirstTest();
		System.out.println();
		insertAddTest();
		System.out.println();
		setTest();
	}

	public static void addTest()
	{
		System.out.println("add test\n");
		LinkedList<String> officers = new LinkedList<String>();
		officers.add("Blake");
		officers.add("Burns");
		officers.add("Houlihan");
		officers.add("Pierce");
		officers.add("McIntyre");
		for (String s : officers)
		    System.out.println(s);
	}

	public static void addFirstTest()
	{
		System.out.println("addFirst test\n");
		LinkedList<String> officers = new LinkedList<String>();
		officers.addFirst("Blake");
		officers.addFirst("Burns");
		officers.addFirst("Houlihan");
		officers.addFirst("Pierce");
		officers.addFirst("McIntyre");
		for (String s : officers)
		    System.out.println(s);
	}

	public static void insertAddTest()
	{
		System.out.println("add - insert test\n");
		LinkedList<String> officers = new LinkedList<String>();
		officers.add("Blake");
		officers.add("Burns");
		officers.add("Houlihan");
		officers.add("Pierce");
		officers.add("McIntyre");
		officers.add(2, "Tuttle");
		for (String s : officers)
		    System.out.println(s);
	}

	public static void setTest()
	{
		System.out.println("set test\n");
		LinkedList<String> officers = new LinkedList<String>();
		officers.add("Blake");
		officers.add("Burns");
		officers.add("Tuttle");
		officers.add("Houlihan");
		officers.add("Pierce");
		officers.add("McIntyre");
		System.out.println(officers);
		officers.set(2, "Murdock");
		System.out.println("\nTuttle is replaced:");
		System.out.println(officers);
	}
}