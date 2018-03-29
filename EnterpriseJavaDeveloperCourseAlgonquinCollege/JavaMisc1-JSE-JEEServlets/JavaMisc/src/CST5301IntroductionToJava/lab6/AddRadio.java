package CST5301IntroductionToJava.lab6;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JRadioButton;


/** Add a set of radio buttons  which changes the text in the text field. */
public class AddRadio extends AddCheckbox {
    private JRadioButton fastrb, fasterrb, fastestrb;
    private JPanel radiop;
    
    
    /** This is where the work goes on */    
    public AddRadio()  {
        super();
        getwdo().setTitle("Swing Lab 3");
        
        fastrb = new JRadioButton("Fast");
        fastrb.setActionCommand("fast");
        fastrb.addActionListener(this);

        
        fasterrb = new JRadioButton("Faster");
        fasterrb.setActionCommand("faster");
        fasterrb.addActionListener(this);
        
        fastestrb = new JRadioButton("Fastest", true );
        fastestrb.setActionCommand("fastest");
        fastestrb.addActionListener(this);
        
        
        //Put the check boxes in a column in a panel
        radiop = new JPanel(new GridLayout(0, 1));
        radiop.add(fastrb);
        radiop.add(fasterrb);
        radiop.add(fastestrb);
        
        getwdo().getContentPane().add(radiop, BorderLayout.CENTER);
        getwdo().pack();
    }
    
    /** Create an instance
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new AddRadio();
    }
    
    
    /** Handle events
     * @param e ActionEvent
     */    
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if( e.getActionCommand().equals("fast") ) {
            gettf().setText("Fast Radio");
            if( debug ) System.out.println("got fastbutton");
        }else if( e.getActionCommand().equals("faster") ) {
            gettf().setText("Faster Radio");
            if( debug ) System.out.println("got fasterbutton");
        }else if( e.getActionCommand().equals("fastest") ) {
            gettf().setText("Fastest Radio");
            if( debug ) System.out.println("got fastestbutton");
        }
        //System.out.println("3 done action processing");
    }
    
} // end class
