package CST5301IntroductionToJava.lab7.suspend;

import java.awt.*;
import java.applet.*;
import javax.swing.*;
import java.awt.event.*;

//		BounceSuspend Demo
//
//		This demostrates the affect of Thread.suspend()
//		and Thread.resume()

public class BounceSuspend extends JApplet implements ActionListener{  
    JButton b1,b2;
    
    public static void main( String[] args ){
        JFrame f = new JFrame("Single Thread");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BounceSuspend b = new BounceSuspend();
        f.getContentPane().add(b.getContentPane());
        b.init();
        f.pack();
        f.setVisible(true);
        b.start();
     }

   Canvas canvas;
   private Ball ball;

   public void init() {
      getContentPane().setLayout(new BorderLayout() );
      canvas = new Canvas();
      canvas.setSize(280,150);
      getContentPane().add(canvas,BorderLayout.CENTER);
      b1 = new JButton("Suspend");
      b1.setActionCommand("Suspend");
      b1.addActionListener(this);
      
      b2 = new JButton("Resume");
      b2.setActionCommand("Resume");
      b2.addActionListener(this);
      
      JPanel bp = new JPanel();
      bp.add(b1);
      bp.add(b2);
      getContentPane().add(bp, BorderLayout.SOUTH );
   }
   
   
   public void paint(Graphics g) {
       System.out.println("implements painting");
      g.drawRect(0,0, 299, 199);
   }

   public void start() {
      System.out.println("start");
      repaint();
      ball = new Ball(canvas);
      ball.start();
   }

   public void actionPerformed(ActionEvent evt) {  
      if (evt.getActionCommand().equals("Suspend")) {
         ball.suspend();
      }
      else if (evt.getActionCommand().equals("Resume")) {
         ball.resume();
      }
     
   }
}
