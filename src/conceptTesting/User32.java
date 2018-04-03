package conceptTesting;

import com.sun.jna.Library;

public interface User32 extends Library
{
  boolean MessageBeep(int uType);  
}
