package CST6526IntermediateJavaProgramming.lab3;

import java.util.StringTokenizer;

public class StringTokenizerVsSplit {

  public static void main(String[] args) {
    String url = "http://www.skilltop.com/index.html";
    String delim = null;
    delim = ":/.";
    //delim = "[:/.].";
    //delim = "[:/.]*";
    //delim = "[:/.]+";
    //delim = "[:/\\.].";
    //delim = "tp|om";
    //delim = "t.p";

    System.out.println("url: " + url);
    System.out.println("delimiter: " + delim + "\n");

    StringTokenizer st = new StringTokenizer(url,delim);
    int i=0;
    while (st.hasMoreElements()) {
      System.out.println("token[" + i + "]: " + st.nextToken());
      i++;
    }

    System.out.println();

    String parts[] = url.split(delim);
    for (i=0; i<parts.length; i++) {
      System.out.println("split[" + i + "]: " + parts[i]);
    }
  }
}