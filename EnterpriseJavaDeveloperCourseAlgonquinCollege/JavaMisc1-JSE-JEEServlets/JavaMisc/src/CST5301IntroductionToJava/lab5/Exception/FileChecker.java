package CST5301IntroductionToJava.lab5.Exception;

public class FileChecker 
{
	public void checkFileSize() throws FileSizeException
	{
		throw(new FileSizeException("TooBig"));
	}
}
