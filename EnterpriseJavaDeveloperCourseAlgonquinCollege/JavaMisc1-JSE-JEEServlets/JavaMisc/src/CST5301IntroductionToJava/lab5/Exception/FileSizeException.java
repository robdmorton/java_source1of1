package CST5301IntroductionToJava.lab5.Exception;

public class FileSizeException extends Exception //RuntimeException //<--unchecked
{
	public FileSizeException(String s)
	{
		super(s);
	}
}
