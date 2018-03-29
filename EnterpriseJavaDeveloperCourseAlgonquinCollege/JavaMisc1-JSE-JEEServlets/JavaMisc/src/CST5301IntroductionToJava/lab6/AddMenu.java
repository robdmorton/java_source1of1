package CST5301IntroductionToJava.lab6;

import java.awt.event.*;
import javax.swing.*;



/** Add a menu that changes the text field. */
public class AddMenu extends AddRadio implements ActionListener, ItemListener{
    private JMenuBar mb;
    private JMenu menu;
    private JMenuItem mi1, mi2, mi3;
    private JCheckBoxMenuItem cb1, cb2;
    private JRadioButtonMenuItem rb1, rb2;
    private JSeparator sep1,sep2;
    private String newline = "\n";
    
    
    
    /** This is where the work goes on */    
    public AddMenu()  {
        super();
        getwdo().setTitle("Swing Lab 4");
        
        sep1 = new JSeparator();
        sep2 = new JSeparator();
        mb = new JMenuBar();
        menu = new JMenu("File");
        mi1 = new JMenuItem("New");
        mi2 = new JMenuItem("Open");
        mi3 = new JMenuItem("Save");
        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        
       
        cb1 = new JCheckBoxMenuItem("Auto-backup", true );
        cb2 = new JCheckBoxMenuItem("Recover");
        cb1.addItemListener(this);
        cb2.addItemListener(this);
        
        rb1 = new JRadioButtonMenuItem("AM");
        rb2 = new JRadioButtonMenuItem("FM", true );
        rb1.addActionListener(this);
        rb2.addActionListener(this);

        
        menu.add(mi1);
        menu.add(mi2);
        menu.add(mi3);
        menu.add(sep1);
        menu.add(cb1);
        menu.add(cb2);
        menu.add(sep2);
        menu.add(rb1);
        menu.add(rb2);
        
        mb.add(menu);
        getwdo().setJMenuBar(mb);
        
        
        
        
      //  getwdo().getContentPane().add(menup, BorderLayout.CENTER);
      getwdo().pack();
    }
    
    /** Create an instance
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new AddMenu();
    }
    
    public void itemStateChanged(ItemEvent e) { 
        super.itemStateChanged(e);
        Object source = e.getSource();
        if( source instanceof JMenuItem ) {
            String s = "Item event detected."
                   + newline
                   + "    Event source: " + ((JMenuItem)source).getText()
                   + " (an instance of " + getClassName(source) + ")"
                   + newline
                   + "    New state: "
                   + ((e.getStateChange() == ItemEvent.SELECTED) ?
                     "selected":"unselected");
            gettf().setText(s);
            getwdo().pack();
        }
    }
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        Object source = e.getSource();
        if( source instanceof JMenuItem ){
            String s = "Action event detected."
                   + newline
                   + "    Event source: " + ((JMenuItem)source).getText()
                   + " (an instance of " + getClassName(source) + ")";
            gettf().setText(s);
            getwdo().pack();
        }
    }
    
    // Returns just the class name -- no package info.
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }
     
} // end class
