package CST5301IntroductionToJava.lab5.Exception;

public class MyTester {
	
	public void test() throws FileSizeException
	{
		FileChecker fc = new FileChecker();
		fc.checkFileSize();
	}
	
	public static void main(String[] args) throws FileSizeException
	{
		MyTester mt = new MyTester();
		mt.test();
	}
}
