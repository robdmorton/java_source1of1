package CST5301IntroductionToJava.lab7.single;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

//		Bounce Demo:
//
//		This demonstrates the affect of not using
//		Threads on the Applet

public class Bounce extends JApplet implements ActionListener{  

   private Canvas canvas;
   private JButton b1,b2;
   
   public static void main( String[] args ){
       JFrame f = new JFrame("Single Thread");
       f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       Bounce b = new Bounce();
       f.getContentPane().add(b.getContentPane());
       b.init();
       f.pack();
       f.setVisible(true);
   }
       
   public void init() { 
      getContentPane().setLayout(new BorderLayout());
      canvas = new Canvas();
      b1 = new JButton("Start Red");
      b1.setActionCommand("Start Red");
      b2 = new JButton("Start Green");
      b2.setActionCommand("Start Green");
      
      JPanel bp = new JPanel();
      bp.add(b1);
      bp.add(b2);
      getContentPane().add("South", bp);
      
      b1.addActionListener(this);
      b2.addActionListener(this);
      canvas.setSize(280,150);
      getContentPane().add( "Center", canvas );

   }
   
   public void paint(Graphics g) {
      g.drawRect(0,0, 299, 199);
   }

   public void actionPerformed(ActionEvent evt) { 
      if (evt.getActionCommand().equals("Start Red")) {  
         Ball b = new Ball(canvas, Color.red);
         b.bounce();
      }
      else if ( evt.getActionCommand().equals("Start Green")) {
         Ball b = new Ball(canvas, Color.green);
         b.bounce();
      }
   } 
}
