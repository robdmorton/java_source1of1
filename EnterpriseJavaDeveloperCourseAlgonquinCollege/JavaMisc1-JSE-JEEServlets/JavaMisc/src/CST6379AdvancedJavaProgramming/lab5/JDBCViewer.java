package CST6379AdvancedJavaProgramming.lab5;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This program display database tables using JDBC.
 * 
 * @author Phil "The Java Man" Stang
 * @author Ian
 * @version 1.1
 */
public class JDBCViewer extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel dbURLLabel = new JLabel("DB URL");

    private JTextField dbURLTF = new JTextField(20);

    private JLabel userLabel = new JLabel("user");

    private JTextField userTF = new JTextField(10);

    private JLabel passwordLabel = new JLabel("password");

    private JTextField passwordTF = new JTextField(10);

    private JButton connectBtn = new JButton("Connect");

    private JLabel tableLabel = new JLabel("tables");

    private JComboBox tableCombo = new JComboBox();

    private JButton getTableBtn = new JButton("Get Table");

    private JTextArea textArea = null;

    private JTextArea theStatusWindow = null;

    private String dbURL = "jdbc:odbc:Example_JavaCourseDB";

    private String user = "";

    private String password = "";

    private Connection connection;

    private JPanel dbConnectPanel = null;

    private JPanel tableSelectionPanel = null;

    public JDBCViewer() {
        setupGUI();
    }

    /**
     * GUI initialization routine.
     * 
     */
    private void setupGUI() {
        this.dbConnectPanel = getDBConnectPanel();
        this.tableSelectionPanel = getTableSelectionPanel();
        Box userInputPanel = Box.createVerticalBox();
        userInputPanel.add(this.dbConnectPanel);
        userInputPanel.add(this.tableSelectionPanel);
        // Display window
        textArea = new JTextArea(10, 40);
        JScrollPane displayScroller = new JScrollPane(textArea);
        // Status message window
        theStatusWindow = new JTextArea("status window", 4, 50);
        theStatusWindow.setEditable(false);
        JScrollPane statusScroller = new JScrollPane();
        statusScroller.getViewport().add(theStatusWindow);
        // Panel panel2 = new JPanel();
        // panel2.setLayout(new GridBagLayout());
        JSplitPane splitterA = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        splitterA.setOneTouchExpandable(true);
        splitterA.setTopComponent(displayScroller);
        splitterA.setBottomComponent(statusScroller);
        this.setLayout(new BorderLayout());
        this.add(userInputPanel, BorderLayout.NORTH);
        this.add(splitterA, BorderLayout.CENTER);
        // set initial values
        dbURLTF.setText(dbURL);
        userTF.setText(user);
        passwordTF.setText(password);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        connectBtn.addActionListener(new ConnectHandler());
        getTableBtn.addActionListener(new QueryHandler());
    }

    private JPanel getDBConnectPanel() {
        if (this.dbConnectPanel == null) {
            this.dbConnectPanel = new JPanel();
            this.dbConnectPanel.setLayout(new GridBagLayout());
            this.dbConnectPanel.add(dbURLLabel, new GridBagConstraints(0, 0, 1,
                    1, 0.0, 0.0, GridBagConstraints.EAST,
                    GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            this.dbConnectPanel.add(dbURLTF, new GridBagConstraints(1, 0, 1, 1,
                    0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                    new Insets(0, 0, 0, 0), 0, 0));
            this.dbConnectPanel.add(userLabel, new GridBagConstraints(0, 1, 1,
                    1, 0.0, 0.0, GridBagConstraints.EAST,
                    GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            this.dbConnectPanel.add(userTF, new GridBagConstraints(1, 1, 1, 1,
                    0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                    new Insets(0, 0, 0, 0), 0, 0));
            this.dbConnectPanel.add(passwordLabel, new GridBagConstraints(0, 2,
                    1, 1, 0.0, 0.0, GridBagConstraints.EAST,
                    GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            this.dbConnectPanel.add(passwordTF, new GridBagConstraints(1, 2, 1,
                    1, 0.0, 0.0, GridBagConstraints.WEST,
                    GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            this.dbConnectPanel.add(connectBtn, new GridBagConstraints(0, 3, 2,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        }
        return this.dbConnectPanel;
    }

    private JPanel getTableSelectionPanel() {
        if (this.tableSelectionPanel == null) {
            this.tableSelectionPanel = new JPanel();
            this.tableSelectionPanel.setLayout(new FlowLayout());
            this.tableSelectionPanel.add(tableLabel);
            this.tableSelectionPanel.add(tableCombo);
            this.tableSelectionPanel.add(getTableBtn);
        }
        return this.tableSelectionPanel;
    }

    private class ConnectHandler implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            connection = getConnection();
            if (connection != null) {
                getAndDisplayDBMetadata(connection, textArea);
                List tableNames = getDBTables(connection);
                tableCombo.removeAllItems();
                for (int i = 0; i < tableNames.size(); i++) {
                    tableCombo.addItem(tableNames.get(i));
                }
            } else {
                sendToStatusWindow("Error can't create connection");
            }
        }
    }

    private Connection getConnection() {
        if (this.connection == null) {
            String dbURL = dbURLTF.getText().trim();
            String user = userTF.getText().trim();
            String passwd = passwordTF.getText().trim();
            try {
                // TODO 01. Load the JDBC driver.
                String driverName = "sun.jdbc.odbc.JdbcOdbcDriver";
                Class.forName(driverName);
                // TODO 02. Create a database connection.
                this.connection = DriverManager.getConnection(dbURL, user, passwd);
            } catch (ClassNotFoundException ex1) {
                ex1.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this.connection;
    }

    /**
     * Gets and displays some DB metadata.
     */
    private void getAndDisplayDBMetadata(Connection conn, JTextArea display) {
        try {
            DatabaseMetaData dbMD = conn.getMetaData();
            display.append("Product Name:          "
                    + dbMD.getDatabaseProductName() + "\n");
            display.append("Product Version:       "
                    + dbMD.getDatabaseProductVersion() + "\n");
            display
                    .append("-----------------------------------------------------------\n");
            display.append("Driver Name:           " + dbMD.getDriverName()
                    + "\n");
            display.append("Driver Version:        " + dbMD.getDriverVersion()
                    + "\n");
            display.append("Driver Major Version:  "
                    + dbMD.getDriverMajorVersion() + "\n");
            display.append("Driver Minor Version:  "
                    + dbMD.getDriverMajorVersion() + "\n");
            display
                    .append("-----------------------------------------------------------\n");
            display.append("Maximum connections:   " + dbMD.getMaxConnections()
                    + "\n");
            display.append("SQL Keywords:          " + dbMD.getSQLKeywords()
                    + "\n");
            display.append("String Functions:      "
                    + dbMD.getStringFunctions() + "\n");
            display.append("System Functions:      "
                    + dbMD.getSystemFunctions() + "\n");
            display.append("Date Time Functions:   "
                    + dbMD.getTimeDateFunctions() + "\n");
            display
                    .append("-----------------------------------------------------------\n");
            display.append("URL:                   " + dbMD.getURL() + "\n");
            display.append("User Name:             " + dbMD.getUserName()
                    + "\n");
        } catch (java.sql.SQLException sqe) {
            sqe.printStackTrace();
        }
    }

    /**
     * Returns a list of table names in the DB.
     */
    @SuppressWarnings("unchecked")
    private java.util.List getDBTables(Connection conn) {
        ArrayList tableNameList = new ArrayList();
        try {
            DatabaseMetaData dbMD = conn.getMetaData();
            String[] tableTypes = { "TABLE" };
            ResultSet allTables = dbMD.getTables(null, null, null, tableTypes);
            while (allTables.next()) {
                String tableName = allTables.getString("TABLE_NAME");
                tableNameList.add(tableName);
            }
        } catch (java.sql.SQLException sqe) {
            sqe.printStackTrace();
        }
        return tableNameList;
    }

    class QueryHandler implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            try {
                String tableName = (String) tableCombo.getSelectedItem();
                String query = "select * from " + tableName;
                // TODO 03. Create a statement.
                Statement stmt = connection.createStatement();
                // TODO 04. Execute the statement and get a result set.
                ResultSet rs = stmt.executeQuery(query);
                displayResult(tableName, rs);
                // TODO 06. Close the JDBC resources.
                rs.close();
                stmt.close();
            } catch (SQLException ex) {
                fatalError(ex);
            }
        }

        public void displayResult(String tableName, ResultSet rs) {
            try {
                // TODO 05. Process the results.
                ResultSetMetaData rsmd = rs.getMetaData();
                int cols = rsmd.getColumnCount();
                textArea.setText(""); // clear text area
                // Display general info and the column names
                textArea.append("\t\t Contents of table " + tableName + '\n');
                StringBuffer sbuf = new StringBuffer();
                // Display column labels and column display size
                textArea.append("Column labels and column display size are:\n");
                int colSize;
                // column Label
                for (int i = 1; i <= cols; i++) {
                    colSize = rsmd.getColumnDisplaySize(i);
                    sbuf.append(rsmd.getColumnLabel(i));
                    sbuf.append(" " + colSize + " ");
                }
                sbuf.append('\n');
                textArea.append(sbuf.toString());
                // Display a formatted header and keep track of the width of the
                // columns.
                // This requires a few steps ...
                //
                int MAX_WIDTH = 20; // truncate columns more than this wide
                char blanks[] = new char[MAX_WIDTH];
                for (int i = 0; i < MAX_WIDTH; i++)
                    blanks[i] = ' ';

                class ColumnInfo {

                    String columnTitle;

                    int columnWidth;

                    ColumnInfo(String columnTitle, int columnWidth) {
                        this.columnTitle = columnTitle;
                        this.columnWidth = columnWidth;
                    }
                }

                ColumnInfo allColumnInfo[] = new ColumnInfo[cols];
                // Step 1 is to collect the column info
                int displaySize;
                int colWidth;
                for (int i = 1; i <= cols; i++) {
                    String colLabel = rsmd.getColumnLabel(i);
                    // Truncate the Column Label to MAX_WIDTH
                    StringBuffer titleBuf = new StringBuffer(colLabel);
                    if (titleBuf.length() > MAX_WIDTH) {
                        titleBuf.setLength(MAX_WIDTH);
                    }
                    // Truncate the display width to MAX_WIDTH
                    displaySize = rsmd.getColumnDisplaySize(i);
                    if (displaySize > MAX_WIDTH) {
                        colWidth = MAX_WIDTH;
                    } else {
                        colWidth = displaySize;
                    }
                    allColumnInfo[i - 1] = new ColumnInfo(titleBuf.toString(),
                            colWidth);
                }
                // Step 2 is to create the table header from the column info
                // Pad it with spaces up to the max col width
                StringBuffer tableHeader = new StringBuffer();
                for (int i = 0; i < allColumnInfo.length; i++) {
                    String colTitle = allColumnInfo[i].columnTitle;
                    tableHeader.append(colTitle + ' ');
                    if (colTitle.length() + 1 < allColumnInfo[i].columnWidth) {
                        for (int k = allColumnInfo[i].columnWidth; k > colTitle
                                .length() + 1; k--)
                            tableHeader.append(' ');
                    }
                }
                // Step 3 is to display the table header
                textArea.append(tableHeader.toString() + '\n');
                // Now display each row of the result set
                StringBuffer colData = new StringBuffer(); // column data
                StringBuffer rowData = new StringBuffer();
                while (rs.next()) {
                    textArea.append("\n");
                    for (int i = 1; i <= cols; i++) {
                        String text = rs.getString(i);
                        if (text == null)
                            text = "";
                        int maxColWidth = allColumnInfo[i - 1].columnWidth;
                        colData = new StringBuffer(text);
                        // Truncate the data if > max column width
                        if (colData.length() > maxColWidth) {
                            colData.setLength(maxColWidth - 1);
                            rowData.append(colData.toString() + ' ');
                            // rowData.append(colData.toString());
                        } else {
                            // Otherwise pad it up to max column width
                            rowData.append(colData.toString());
                            if (colData.length() < maxColWidth) {
                                for (int k = maxColWidth; k > colData.length(); k--)
                                    rowData.append(' ');
                            }
                        }
                        textArea.append(rowData.toString());
                        rowData.setLength(0); // erase buffer for next row
                    }
                }
            } catch (SQLException ex) {
                fatalError(ex);
            }
        }
    } // QueryHandler

    protected void fatalError(Exception e) {
        sendToStatusWindow(e.toString() + '\n');
        e.printStackTrace();
    }

    protected void sendToStatusWindow(String message) {
        theStatusWindow.append(message + '\n');
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("JDBC Viewer");
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.getContentPane().add(new JDBCViewer());
        frame.pack();
        frame.setVisible(true);
    }
}