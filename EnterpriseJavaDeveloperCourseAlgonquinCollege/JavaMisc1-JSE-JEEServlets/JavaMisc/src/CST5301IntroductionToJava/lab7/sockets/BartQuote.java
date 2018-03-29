
package CST5301IntroductionToJava.lab7.sockets;

import java.util.ArrayList;

public class BartQuote
{
	ArrayList<String> q = new ArrayList<String>();

	public BartQuote()
	{
		q.add("I will not waste chalk.");
		q.add("I will not skateboard in the halls.");
		q.add("I will not burp in class.");
		q.add("I will not instigate a revolution.");
		q.add("It's potato, not potatoe.");
		q.add("I will not encourage others to fly.");
		q.add("Tar is not a plaything.");
		q.add("I will not sell school property.");
		q.add("I will not get very far with this attitude.");
		q.add("I will not sell land in Florida.");
		q.add("I will not grease the monkey bars.");
		q.add("I will not hide behind the Fifth Amendment.");
		q.add("I am not a dentist.");
		q.add("I will finish what I sta");
		q.add("Hamsters cannot fly.");
		q.add("I will not aim for the head.");
		q.add("I will not expose the ignorance of the faculty.");
		q.add("I will not conduct my own fire drills.");
		q.add("I will not fake seizures.");
		q.add("This punishment is not boring and meaningless.");
	}


	public String getQuote()
	{
		int i = (int)(Math.random() * q.size());
		return q.get(i);
	}
}