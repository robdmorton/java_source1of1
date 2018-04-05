package advancedJavaProgramming.lab3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

/**
 * @author Ian
 * 
 *         TODO Create a simple GUI with a menu bar, status bar and toolbar. Add a JTable to the
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
    frame.getContentPane().setSize(new Dimension(500, 400));
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

    JTable table = new JTable(data, columnNames);
    JScrollPane scrollPane = new JScrollPane(table);
    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
  }

  /**
   *  
   */
  private void createToolBar() {
    JToolBar toolBar = new JToolBar();
    button1 = new JButton("Button 1");
    button2 = new JButton("Button 2");
    ActionListener theListener = new MyActionListener();
    button1.addActionListener(theListener);
    button2.addActionListener(theListener);
    toolBar.add(button1);
    toolBar.add(button2);
    frame.getContentPane().add(toolBar, BorderLayout.NORTH);
  }

  /**
   *  
   */
  private void createStatusBar() {
    statusBar = new JTextArea();
    statusBar.setText("Ready.");
    statusBar.setEditable(false);
    statusBar.setBackground(Color.getColor("#E0E0E0"));
    frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
  }

  /**
   *  
   */
  private void createMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu menu1 = new JMenu("File");
    menu1.add(new JMenuItem("Open"));
    menu1.add(new JMenuItem("Close"));
    menu1.add(new JSeparator());
    menu1.add(new JMenuItem("Exit"));
    menuBar.add(menu1);
    frame.setJMenuBar(menuBar);
  }

  public static void main(String[] args) {
    MyDemo demo = new MyDemo();
  }

  // inner classes have access to all instance vars of the containing class
  private class MyActionListener implements java.awt.event.ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == button1) {
        statusBar.setText("Button 1 pressed!");
      } else if (e.getSource() == button2) {
        statusBar.setText("Button 2 pressed!");
      }
    }
  }

}
