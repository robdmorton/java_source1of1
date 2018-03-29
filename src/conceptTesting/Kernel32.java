package conceptTesting;

import com.sun.jna.Library;

public interface Kernel32 extends Library
{
  boolean Beep(int frequency, int duration);
}
