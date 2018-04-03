package coreservlets;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

/** An interactive database query viewer. Connects to
 *  the specified Oracle or Sybase database, executes a query,
 *  and presents the results in a JTable.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class QueryViewer extends JFrame
                         implements ActionListener{
  public static void main(String[] args) {
    new QueryViewer();
  }

  private JTextField hostField, dbNameField,
                     queryField, usernameField;
  private JRadioButton oracleButton, sybaseButton;
  private JPasswordField passwordField;
  private JButton showResultsButton;
  private Container contentPane;
  private JPanel tablePanel;
  
  public QueryViewer () {
    super("Database Query Viewer");
    WindowUtilities.setNativeLookAndFeel();
    addWindowListener(new ExitListener());
    contentPane = getContentPane();
    contentPane.add(makeControlPanel(), BorderLayout.NORTH);
    pack();
    setVisible(true);
  }

  /** When the "Show Results" button is pressed or
   *  RETURN is hit while the query textfield has the
   *  keyboard focus, a database lookup is performed,
   *  the results are placed in a JTable, and the window
   *  is resized to accommodate the table.
   */
  
  public void actionPerformed(ActionEvent event) {
    String host = hostField.getText();
    String dbName = dbNameField.getText();
    String username = usernameField.getText();
    String password =
      String.valueOf(passwordField.getPassword());
    String query = queryField.getText();
    int vendor;
    if (oracleButton.isSelected()) {
      vendor = DriverUtilities.ORACLE;
    } else {
      vendor = DriverUtilities.SYBASE;
    }
    if (tablePanel != null) {
      contentPane.remove(tablePanel);
    }
    tablePanel = makeTablePanel(host, dbName, vendor,
                                username, password,
                                query);
    contentPane.add(tablePanel, BorderLayout.CENTER);
    pack();
  }

  // Executes a query and places the result in a
  // JTable that is, in turn, inside a JPanel.
  
  private JPanel makeTablePanel(String host,
                                String dbName,
                                int vendor,
                                String username,
                                String password,
                                String query) {
    String driver = DriverUtilities.getDriver(vendor);
    String url = DriverUtilities.makeURL(host, dbName, vendor);
    DBResults results =
      DatabaseUtilities.getQueryResults(driver, url,
                                        username, password,
                                        query, true);
    JPanel panel = new JPanel(new BorderLayout());
    if (results == null) {
      panel.add(makeErrorLabel());
      return(panel);
    }
    DBResultsTableModel model =
      new DBResultsTableModel(results);
    JTable table = new JTable(model);
    table.setFont(new Font("Serif", Font.PLAIN, 17));
    table.setRowHeight(28);
    JTableHeader header = table.getTableHeader();
    header.setFont(new Font("SansSerif", Font.BOLD, 13));
    panel.add(table, BorderLayout.CENTER);
    panel.add(header, BorderLayout.NORTH);
    panel.setBorder
      (BorderFactory.createTitledBorder("Query Results"));
    return(panel);
  }

  // The panel that contains the textfields, check boxes,
  // and button.
  
  private JPanel makeControlPanel() {
    JPanel panel = new JPanel(new GridLayout(0, 1));
    panel.add(makeHostPanel());
    panel.add(makeUsernamePanel());
    panel.add(makeQueryPanel());
    panel.add(makeButtonPanel());
    panel.setBorder
      (BorderFactory.createTitledBorder("Query Data"));
    return(panel);
  }

  // The panel that has the host and db name textfield and
  // the driver radio buttons. Placed in control panel.
  
  private JPanel makeHostPanel() {
    JPanel panel = new JPanel();
    panel.add(new JLabel("Host:"));
    hostField = new JTextField(15);
    panel.add(hostField);
    panel.add(new JLabel("    DB Name:"));
    dbNameField = new JTextField(15);
    panel.add(dbNameField);
    panel.add(new JLabel("    Driver:"));
    ButtonGroup vendorGroup = new ButtonGroup();
    oracleButton = new JRadioButton("Oracle", true);
    vendorGroup.add(oracleButton);
    panel.add(oracleButton);
    sybaseButton = new JRadioButton("Sybase");
    vendorGroup.add(sybaseButton);
    panel.add(sybaseButton);
    return(panel);
  }

  // The panel that has the username and password textfields.
  // Placed in control panel.
  
  private JPanel makeUsernamePanel() {
    JPanel panel = new JPanel();
    usernameField = new JTextField(10);
    passwordField = new JPasswordField(10);
    panel.add(new JLabel("Username: "));
    panel.add(usernameField);
    panel.add(new JLabel("    Password:"));
    panel.add(passwordField);
    return(panel);
  }

  // The panel that has textfield for entering queries.
  // Placed in control panel.
  
  private JPanel makeQueryPanel() {
    JPanel panel = new JPanel();
    queryField = new JTextField(40);
    queryField.addActionListener(this);
    panel.add(new JLabel("Query:"));
    panel.add(queryField);
    return(panel);
  }

  // The panel that has the "Show Results" button.
  // Placed in control panel.
  
  private JPanel makeButtonPanel() {
    JPanel panel = new JPanel();
    showResultsButton = new JButton("Show Results");
    showResultsButton.addActionListener(this);
    panel.add(showResultsButton);
    return(panel);
  }

  // Shows warning when bad query sent.
  
  private JLabel makeErrorLabel() {
    JLabel label = new JLabel("No Results", JLabel.CENTER);
    label.setFont(new Font("Serif", Font.BOLD, 36));
    return(label);
  }
}
