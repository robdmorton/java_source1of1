package CST5301IntroductionToJava;

//Ch01-Version 2

// This class creates a Greeter object
// that displays a hello message
// in a dialog box.

import javax.swing.JOptionPane;

public class Greeter
{
    public void sayHello()
    {
        JOptionPane.showMessageDialog(null, "Hello, World!",
            "Greeter", JOptionPane.INFORMATION_MESSAGE);
    }
}
