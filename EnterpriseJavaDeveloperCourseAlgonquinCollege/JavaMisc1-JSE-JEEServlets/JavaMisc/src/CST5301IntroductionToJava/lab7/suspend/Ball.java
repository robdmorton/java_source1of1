package CST5301IntroductionToJava.lab7.suspend;
import java.awt.*;

// 		BounceSuspend Demo

class Ball extends Thread {  

   public Ball(Canvas c) { 
      box = c; 
   }

   public void draw(Color c) {  
      Graphics g = box.getGraphics();
      
      if ( c == null ) {
         g.setColor(box.getBackground());
      }
      else {
         g.setColor(c);
      }
      g.fillOval(x, y, XSIZE, YSIZE);
      g.dispose();
   }
   
   public void move(Color c) {  
      Graphics g = box.getGraphics();
      g.setXORMode(box.getBackground());
      g.setColor(c);
      g.fillOval(x, y, XSIZE, YSIZE);
      x += dx;
      y += dy;
      Dimension d = box.getSize();
      if (x < 0) { x = 0; dx = -dx; }
      if (x + XSIZE >= d.width) { x = d.width - XSIZE; dx = -dx; }
      if (y < 0) { y = 0; dy = -dy; }
      if (y + YSIZE >= d.height) { y = d.height - YSIZE; dy = -dy; }
      g.fillOval(x, y, XSIZE, YSIZE);
      g.dispose();
   }
   
   public void run() {  
      Color c;
      int i;

      draw(Color.red);
      while ( true ) {
         move(Color.red);
         try { 
            Thread.sleep(5); 
         } catch(InterruptedException ignored) {}
      }
   }
   
   private Canvas box;
   private static final int XSIZE = 20;
   private static final int YSIZE = 20;
   private int x = 0;
   private int y = 0;
   private int dx = 2;
   private int dy = 2;   
}


