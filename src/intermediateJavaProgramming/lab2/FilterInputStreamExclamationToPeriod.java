package intermediateJavaProgramming.lab2;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FilterInputStreamExclamationToPeriod extends FilterInputStream {

  public FilterInputStreamExclamationToPeriod(InputStream in) {
    super(in);
  }

  public int read() throws IOException {
    int b;
    b = super.read();
    if (b == '!') {
      b = '.';
    }
    return b;
  }

}
