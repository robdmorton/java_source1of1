package CST5301IntroductionToJava.lab6;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.*;


/** When you press each button, make some different text appear in the text field. */
public class AddButtons implements ActionListener, WindowListener   {
    /** Handy boolean debug flag */
    boolean debug = false;
    /** The main window */
    private JFrame wdo;
    /** Text Window */
    private JTextField tf;
    
    /** A Button */
    private JButton okbutton;
    
    /** A button */
    private JButton cancelbutton;
    
    /** A Button */
    private JButton resetbutton;
    
    /** A Button */
    private JPanel buttonpanel;
    /** Rectangle describing window position and size */
    private Rectangle wdosize;
    /** Font for display text */
    private Font f;
    
    /** This is where the work goes on */
    public AddButtons()  {
        /** Create and Setup the main window */
        wdo = new JFrame("SwingLab 1");
        /** Set its position and size */
        wdosize = new Rectangle(100,100,300, 300);
        wdo.setBounds(wdosize);
        /** Tell the main window we are interested in it's events */
        wdo.addWindowListener(this);
        
        /** Create and Setup the TextField */
        tf = new JTextField();
        f = new Font("Courier", Font.ITALIC, 12);
        tf.setFont(f);
        
        
        /** Create and setup the buttons */
        okbutton = new JButton("OK");
        okbutton.setActionCommand("ok");
        
        cancelbutton = new JButton("Cancel");
        cancelbutton.setActionCommand("cancel");
        
        resetbutton = new JButton("Reset");
        resetbutton.setActionCommand("reset");
        
        /** Tell the buttons we are interested in it's events */
        okbutton.addActionListener(this);
        cancelbutton.addActionListener(this);
        resetbutton.addActionListener(this);
        
        /** Create and setup the button panel */
        buttonpanel = new JPanel();
        buttonpanel.add(okbutton);
        buttonpanel.add(cancelbutton);
        buttonpanel.add(resetbutton);
        
        /** Set the layout, add the buttonpanel and textfield to the main window */
        wdo.getContentPane().setLayout(new BorderLayout());
        wdo.getContentPane().add(buttonpanel, BorderLayout.SOUTH);
        wdo.getContentPane().add(tf, BorderLayout.NORTH);
        /** lastly make the main window visible */
        wdo.setVisible(true);
    }
    
    /** Create an instance
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new AddButtons();
    }
    
    /** Handle mouse events
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        if( e.getActionCommand().equals("ok") ) {
            tf.setText("OK Button");
            if( debug ) System.out.println("got ok button");
        }else if( e.getActionCommand().equals("cancel") ) {
            tf.setText("Cancel Button");
            if( debug ) System.out.println("got cancel button");
        }else if( e.getActionCommand().equals("reset") ) {
            tf.setText("Reset Button");
            if( debug ) System.out.println("got reset button");
        }
        //System.out.println("done action processing");
    }
    
    /** Listen for Window Events
     * @param e WindowEvent
     */
    public void windowActivated(WindowEvent e) {
    }
    
    /** Listen for Window Events
     * @param e WindowEvent
     */
    public void windowClosed(WindowEvent e) {
        if( debug )System.out.println("Window Closed");
    }
    
    /** Listen for Window Events
     * @param e WindowEvent
     */
    public void windowClosing(WindowEvent e) {
        System.out.println("Window Closing-Exiting Application");
        System.exit(0);
        
    }
    
    /** Listen for Window Events
     * @param e WindowEvent
     */
    public void windowDeactivated(WindowEvent e) {
        if( debug )System.out.println("Window Deactivsted");
    }
    
    /** Listen for Window Events
     * @param e WindowEvent
     */
    public void windowDeiconified(WindowEvent e) {
        if( debug )System.out.println("Window Deiconified");
    }
    
    /** Listen for Window Events
     * @param e Windowevent
     */
    public void windowIconified(WindowEvent e) {
        if( debug )System.out.println("Window Iconified");
    }
    
    /** Listen for Window Events
     * @param e WindowEvent
     */
    public void windowOpened(WindowEvent e) {
        if( debug )System.out.println("Window Opened");
    }
    
    public JFrame getwdo(){
        return wdo;
    }
    public JTextField gettf(){
        return tf;
    }
    
}
