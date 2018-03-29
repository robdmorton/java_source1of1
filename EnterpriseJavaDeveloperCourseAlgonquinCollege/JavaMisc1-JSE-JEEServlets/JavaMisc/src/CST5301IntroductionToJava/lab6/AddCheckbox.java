package CST5301IntroductionToJava.lab6;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JCheckBox;


/** Add a check box to your class, capture the event and insert different text into the text field. */
public class AddCheckbox extends AddButtons implements ItemListener {
    private JCheckBox smallcb, mediumcb, largecb ;
    private JPanel checkPanel;
    
    /** This is where the work goes on */    
    public AddCheckbox()  {
        super();
        getwdo().setTitle("Swing Lab 2");
        
        smallcb = new JCheckBox("Small" );
        smallcb.setActionCommand("small");
        
        
        mediumcb = new JCheckBox("Medium", true );
        mediumcb.setActionCommand("medium");
        
        largecb = new JCheckBox("Large");
        largecb.setActionCommand("large");
        
        //Register a listener for the check boxes.
        smallcb.addItemListener(this);
        mediumcb.addItemListener(this);
        largecb.addItemListener(this);
        
        //Put the check boxes in a column in a panel
        checkPanel = new JPanel(new GridLayout(0, 1));
        checkPanel.add(smallcb);
        checkPanel.add(mediumcb);
        checkPanel.add(largecb);
        getwdo().getContentPane().add(checkPanel, BorderLayout.WEST);
        getwdo().pack();
    }
    
    /** Create an instance
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new AddCheckbox();
    }
    
    /** Handle cb events
     * @param e ItemEvent
     */    
    public void itemStateChanged(ItemEvent e) {    
         Object source = e.getItemSelectable();
        if (source == smallcb ) {
            if( debug ) System.out.println("small cb");
            if (e.getStateChange() == ItemEvent.DESELECTED)
                gettf().setText("Small has been de-selected");
            else
                gettf().setText("Small has been selected");
        }else if( source == mediumcb ) {
            if( debug ) System.out.println("got medium cb");
            if (e.getStateChange() == ItemEvent.DESELECTED)
                gettf().setText("medium has been de-selected");
            else
                gettf().setText("medium has been selected");
        }else if( source == largecb ) {
            if( debug ) System.out.println("got large cb");
            if (e.getStateChange() == ItemEvent.DESELECTED)
                gettf().setText("large has been de-selected");
            else
                gettf().setText("large has been selected");
        }
        getwdo().pack();
    }
} // end class
