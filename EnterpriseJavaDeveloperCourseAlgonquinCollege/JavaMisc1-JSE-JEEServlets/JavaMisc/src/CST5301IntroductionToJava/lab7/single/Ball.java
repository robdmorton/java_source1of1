package CST5301IntroductionToJava.lab7.single;

import java.awt.*;

//		Bounce Demo

public class Ball {  

   public Ball(Canvas c, Color col) { 
      box = c; 
      thecolor = col;
   }

   public void draw() {  
      Graphics g = box.getGraphics();
	  g.setColor(thecolor);
      g.fillOval(x, y, XSIZE, YSIZE);
      g.dispose();
   }
   
   public void move() {  
      Graphics g = box.getGraphics();
      g.setXORMode(box.getBackground());
	  g.setColor(thecolor);
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
   
   public void bounce() {  
      draw();
      for (int i = 1; i <= 400; i++) {  
         move();
         try { 
            Thread.sleep(10); 
         } 
         catch(InterruptedException ignored) {}
      }
   }
   
   private Canvas box;
   private Color thecolor;

   private static final int XSIZE = 20;
   private static final int YSIZE = 20;
   private int x = 0;
   private int y = 0;
   private int dx = 2;
   private int dy = 2;   
}


