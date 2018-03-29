package CST5301IntroductionToJava.lab7.express;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

//		BounceExpress Demo
//
//		This demonstrates the behaviour of Threads at
//		differing priorities

public class BounceExpress extends JPanel implements ActionListener{  
    

   private Canvas canvas;
   private JButton b1,b2;
   
    public static void main( String[] args ){
        JFrame f = new JFrame("Express Thread");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BounceExpress b = new BounceExpress();
        b.init();
        f.getContentPane().add(b);
        f.pack();
        f.setVisible(true);
     }
   public void init() {
      canvas = new Canvas();
      canvas.setSize(280, 150);
      setLayout(new BorderLayout() );
      add(canvas, BorderLayout.CENTER);
      
      b1 = new JButton("Start");
      b1.setActionCommand("Start");
      b1.addActionListener(this);
      
      b2 = new JButton("Express");
      b2.setActionCommand("Express");
      b2.addActionListener(this);
      
      JPanel bp = new JPanel();
      bp.add(b1);
      bp.add(b2);
      add(bp, BorderLayout.SOUTH); 
   }
   
   public void actionPerformed(ActionEvent evt){ 
      if (evt.getActionCommand().equals("Start")) {  
         Ball b = new Ball(canvas, Color.black);
         b.setPriority(Thread.MIN_PRIORITY);
         b.start();
      }
      else if (evt.getActionCommand().equals("Express")) {  
         Ball b = new Ball(canvas, Color.red);
         b.setPriority(Thread.MAX_PRIORITY);
         b.start();
      }
   }
}
