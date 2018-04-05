package introductionToJava;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MyGUI extends JFrame {
  static final long serialVersionUID = 1;
  JLabel msgLabel;

  public MyGUI() {
    super("Java is fun !");
    setupGUI();
  }

  public void setupGUI() {
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    ActionListener al = new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        msgLabel.setText(((JButton) ae.getSource()).getText());
      }
    };

    JPanel buttonPanel = new JPanel();
    // buttonPanel.setBorder(new TitledBorder("Click a button"));

    JButton OKbutton = new JButton("OK");
    OKbutton.addActionListener(al);
    buttonPanel.add(OKbutton);

    JButton CancelButton = new JButton("Cancel");
    CancelButton.addActionListener(al);
    buttonPanel.add(CancelButton);

    JPanel p = new JPanel(new BorderLayout());
    p.setBorder(new EmptyBorder(8, 8, 8, 8));
    msgLabel = new JLabel("No button pressed!");
    p.add(msgLabel, BorderLayout.NORTH);
    p.add(buttonPanel, BorderLayout.SOUTH);
    setContentPane(p);
    // pack();
    setVisible(true);
    this.setSize(200, 200);
    // this.show();
  }

  public static void main(String[] args) {
    new MyGUI();
  }
}
