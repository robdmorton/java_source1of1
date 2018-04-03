import java.applet.Applet;
import java.awt.*;

public class BlueApplet extends Applet {
  private String message;
  private int height;

  public void init() {
    setBackground(Color.blue);
    setForeground(Color.yellow);
    setFont(new Font("Serif", Font.BOLD, 36));
    message = getParameter("MESSAGE");
    if (message == null) {
      message = "No message";
    }
    height = getSize().height;
  }

  public void paint(Graphics g) {
    g.drawString(message, 10, height-10);
  }
}
