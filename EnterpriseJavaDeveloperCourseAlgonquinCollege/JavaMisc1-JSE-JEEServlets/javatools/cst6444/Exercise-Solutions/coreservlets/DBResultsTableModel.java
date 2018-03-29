package coreservlets;

import javax.swing.table.*;

/** Simple class that tells a JTable how to extract
 *  relevant data from a DBResults object (which is
 *  used to store the results from a database query).
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class DBResultsTableModel extends AbstractTableModel {
  private DBResults results;

  public DBResultsTableModel(DBResults results) {
    this.results = results;
  }

  public int getRowCount() {
    return(results.getRowCount());
  }

  public int getColumnCount() {
    return(results.getColumnCount());
  }

  public String getColumnName(int column) {
    return(results.getColumnNames()[column]);
  }

  public Object getValueAt(int row, int column) {
    return(results.getRow(row)[column]);
  }
}
