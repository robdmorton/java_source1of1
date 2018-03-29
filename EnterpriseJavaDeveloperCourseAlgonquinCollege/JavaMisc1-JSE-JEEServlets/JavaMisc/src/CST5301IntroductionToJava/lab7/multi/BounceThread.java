package CST5301IntroductionToJava.lab7.multi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

//		BounceThread Demo
//
//		This demonstrates the advantage of using multiple
//		Threads over a single Thread

public class BounceThread extends JApplet implements ActionListener{  

   public int endpos = 0;
   private Canvas canvas;
   private JButton b1;
   
   public static void main( String[] args ){
       JFrame f = new JFrame("Multi Thread");
       f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       BounceThread bt = new BounceThread();
       f.getContentPane().add(bt.getContentPane() );
       bt.init();
       f.pack();
       f.setVisible(true);
   }
   public void init() {
      getContentPane().setLayout(new BorderLayout() );
      canvas = new Canvas();
      getContentPane().add(canvas, BorderLayout.CENTER);
      b1 = new JButton("Start");
      b1.setActionCommand("Start");
      b1.addActionListener(this);
      
      JPanel bp = new JPanel();
      bp.add(b1);
      getContentPane().add( bp, BorderLayout.SOUTH );
      canvas.setSize(280,150);
   }
   
   public void paint(Graphics g) {
      g.drawRect(0,0, 299, 199);
   }

   public void actionPerformed(ActionEvent evt) {  
       if(evt.getActionCommand().equals("Start")) {
           if ( endpos >= 300 ) {
               endpos = 0; 
           }
           Ball b = new Ball(canvas, endpos);
           endpos += 20;
           b.start();
      }    
   }
}
