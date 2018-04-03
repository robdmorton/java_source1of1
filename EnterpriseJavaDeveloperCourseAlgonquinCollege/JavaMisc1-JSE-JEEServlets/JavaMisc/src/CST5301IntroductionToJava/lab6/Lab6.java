package CST5301IntroductionToJava.lab6;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Lab6 implements ActionListener
{
// member variables for my gui
        JFrame frame;
        JButton copyButton;
        JButton clearButton;
        JLabel label;
        JTextField textfield;

        public static void main(String[] args)
        {
             Lab6 lab = new Lab6();
             lab.buildAndShowGUI();
        }

        public void buildAndShowGUI()
        {
                frame = new JFrame("Lab 6");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                copyButton = new JButton("Copy");
                clearButton = new JButton("Clear");

                copyButton.addActionListener(this);
                clearButton.addActionListener(this);

                label = new JLabel("will be replaced");
                textfield = new JTextField(10);


                JPanel p1 = new JPanel();
                p1.setLayout(new BorderLayout());

                JPanel p2 = new JPanel();
                p2.setLayout(new BorderLayout());
                p2.add(textfield,BorderLayout.NORTH);
                p2.add(label,BorderLayout.SOUTH);

                JPanel p3 = new JPanel();
                p3.add(copyButton);
                p3.add(clearButton);

                p1.add(p2,BorderLayout.CENTER);
                p1.add(p3,BorderLayout.SOUTH);

                frame.add(p1);
                frame.pack();
                frame.setVisible(true);
        }


        public void actionPerformed(ActionEvent e)
        {
                System.out.println("a button was pushed - but which one?");
                Object source =  e.getSource();
                if (source.equals(copyButton)) {
                  System.out.println("was copy");
                  label.setText(textfield.getText());
                }
                else {
                  System.out.println("was clear");
                  textfield.setText("");
                                    }
        }
}