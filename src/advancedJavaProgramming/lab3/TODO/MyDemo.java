package advancedJavaProgramming.lab3.TODO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

/**
 * @author Ian
 * 
 *         TODO 00. Create a simple GUI with a menu bar, status bar and toolbar. Add a JTable to the
 *         contentPane and register action listeners for the buttons.
 * 
 *         See the Sun Java Swing Tutorial:
 *         http://java.sun.com/docs/books/tutorial/uiswing/index.html
 * 
 */
public class MyDemo {

  private JFrame frame = null;

  private JTextArea statusBar = null;

  private JButton button1 = null;

  private JButton button2 = null;

  public MyDemo() {
    createAndShowGUI();
  }

  /**
   *  
   */
  private void createAndShowGUI() {
    frame = new JFrame();
    frame.getContentPane().setPreferredSize(new Dimension(500, 400));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JFrame.setDefaultLookAndFeelDecorated(true);
    createMenuBar();
    createStatusBar();
    createToolBar();
    createTable();
    frame.pack();
    frame.setVisible(true);
  }

  /**
   *  
   */
  private void createTable() {
    String[] columnNames =
        {"First Name", "Last Name", "Favourite Course", "# of Years", "Likes Sushi"};

    Object[][] data =
        {{"John", "Doe", "Advanced Java Programming", new Integer(5), new Boolean(false)},
            {"Alison", "Huml", "Advanced Java Programming", new Integer(3), new Boolean(true)},
            {"Kathy", "Walrath", "Advanced Java Programming", new Integer(2), new Boolean(false)},
            {"Sharon", "Zakhour", "Advanced Java Programming", new Integer(20), new Boolean(true)},
            {"Philip", "Milne", "Advanced Java Programming", new Integer(10), new Boolean(false)}};

    // TODO 13. Instantiate a new JTable, passing in the data and column names to its constructor.
    // TODO 14. Create a new JScrollPane instance, passing in the table to its constructor.
    JScrollPane scrollPane = null;
    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
  }

  /**
   *  
   */
  private void createToolBar() {
    // TODO 08. Create a new JToolBar instance.
    JToolBar toolBar = null;
    // TODO 09. Instantiate the two buttons declared above.
    // TODO 10. Register new instances of the inner class as the ActionListener for these buttons.
    // TODO 11. Add the buttons to the tool bar one by one.
    // TODO 12. Add the tool bar to the NORTH region of the frame's contentPane.
  }

  /**
   *  
   */
  private void createStatusBar() {
    // TODO 04. Create a new JTextArea for the status bar.
    // TODO 05. Disable the status bar to make it read-only.
    statusBar.setBackground(Color.getColor("#E0E0E0"));
    // TODO 06. Set the default text of the status bar to "Ready."
    // TODO 07. Place it in the SOUTH region of the frame's contentPane.

  }


  /**
   *  
   */
  private void createMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu menu1 = null;
    // TODO 01. Create a new JMenu instance and add some JMenuItems.
    // TODO 02. Add a JSeparator between the menu items.
    menuBar.add(menu1);
    // TODO 03. Set this JMenuBar instance as the frame's JMenuBar.
  }

  public static void main(String[] args) {
    MyDemo demo = new MyDemo();
  }

  private class MyActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == button1) {
        // TODO 15. Print a message to the status bar saying "Button 1 pressed!".
      } else if (e.getSource() == button2) {
        // TODO 16. Print a message to the status bar saying "Button 2 pressed!".
      }

    }

  }

}
